/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import kase.aptechsaigon.projectsem2.ConnectDatabase;

/**
 *
 * @author Moiiii
 */
public class Department extends javax.swing.JPanel {


    /**
     * Creates new form Jobb
     */
    public Department() {
        
            initComponents();
            loadJobs();
        }

public void loadJobs() {
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"DepartmentID", "DepartmentName", "ManagerID", "DeputyManagerID"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tbDept.setModel(model);

    String sql = "SELECT DepartmentID, DepartmentName, ManagerID, DeputyManagerID FROM Departments";

    try (Connection conn = ConnectDatabase.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            int departmentID = rs.getInt("DepartmentID");
            String departmentName = rs.getString("DepartmentName");
            String managerID = rs.getString("ManagerID");
            String deputyManagerID = rs.getString("DeputyManagerID"); 
            model.addRow(new Object[]{departmentID, departmentName, managerID});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    tbDept.setDefaultEditor(Object.class, null);
    tbDept.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    if (isEditMode) {
        setEditStatus(true);
    } else {
        setEditStatus(false);
    }

    if (tbDept.getRowCount() > 0 && tbDept.getSelectedRow() == -1) {
        tbDept.setRowSelectionInterval(0, 0);
        showSelectedJob(0);
    }

    tbDept.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting() && tbDept.getSelectedRow() != -1) {
            showSelectedJob(tbDept.getSelectedRow());

            if (isEditMode) {
                setEditStatus(true);
            }
        }
    });
}

private void showSelectedJob(int row) {
    txtDeptName.setText(tbDept.getValueAt(row, 1).toString());
    txtManID.setText(tbDept.getValueAt(row, 2).toString());

    /*Object startDateObj = tbJob.getValueAt(row, 3);
    if (startDateObj != null) {
        jcdEstimatedStartDate.setDate((java.util.Date) startDateObj);
    } else {
        jcdEstimatedStartDate.setDate(null);
    }

    Object endDateObj = tbJob.getValueAt(row, 4);
    if (endDateObj != null) {
        jcdEstimatedEndDate.setDate((java.util.Date) endDateObj);
    } else {
        jcdEstimatedEndDate.setDate(null);
    }*/

    setEditStatus(false);
}

    private boolean isEditMode = false; 

    public void setEditStatus(boolean editable) {
        isEditMode = editable;
        
        txtDeptName.setEnabled(editable);
        txtManID.setEnabled(editable);
       /* jcdEstimatedStartDate.setEnabled(editable);
        jcdEstimatedEndDate.setEnabled(editable); */
        btnSaveJob.setEnabled(editable);
        btnCancelJob.setEnabled(editable);
        btnResetJob.setEnabled(editable);
        
        btnAddJob.setEnabled(!editable);
        btnEditJob.setEnabled(!editable);
        btnDeleteJob.setEnabled(!editable);

                
    }


    private void resetButtons() {
        btnAddJob.setEnabled(true);
        btnEditJob.setEnabled(true);
        btnDeleteJob.setEnabled(true);
        btnResetJob.setEnabled(true);
    }
    
    
    private void deleteJob() {
    int selectedRow = tbDept.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một công việc để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!btnDeleteJob.isEnabled()) {
        resetButtons();
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa công việc này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        String jobID = tbDept.getValueAt(selectedRow, 0).toString();

        String sql = "DELETE FROM Departments WHERE DepartmentID = ?";
        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(jobID));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadJobs();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
        loadJobs();
        btnCancelJob.setEnabled(false);
}

    private void cancelJob() {
        setEditStatus(false);
    }

private boolean isEditing = false;
private int selectedJobId = -1;

private void saveJob() {
    int selectedRow = tbDept.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một công việc để lưu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Object deptIdObj = tbDept.getValueAt(selectedRow, 0);
    String deptName = txtDeptName.getText().trim();
    String manID = txtManID.getText().trim();
    String dManID = txtDManID.getText().trim();
    /* java.util.Date startDate = jcdEstimatedStartDate.getDate();
    java.util.Date endDate = jcdEstimatedEndDate.getDate(); */

    if (deptName.isEmpty() || manID.isEmpty() || dManID.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        return;
    }

   /* java.sql.Date deputyManagerID = new java.sql.Date(startDate.getTime());
    java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime()); */

    try (Connection conn = ConnectDatabase.getConnection()) {
        if (deptIdObj == null) { 
            String insertSQL = "INSERT INTO Jobs (JobName, Description, EstimatedStartDate, EstimatedEndDate, Status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, deptName);
                pstmt.setString(2, manID);
                /*pstmt.setDate(3, deputyManagerID);
                pstmt.setDate(4, sqlEndDate);*/
                pstmt.setString(3, dManID);
               

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            int newDeptId = rs.getInt(1);
                            tbDept.setValueAt(newDeptId, selectedRow, 0);
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Thêm công việc mới thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else { 
            int deptId = (int) deptIdObj;
            String updateSQL = "UPDATE Departments SET DepartmentName=?, ManagerID=?, DeputyMangerID=? WHERE DepartmentID=?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setString(1, deptName);
                pstmt.setString(2, manID);
                pstmt.setString(3, dManID);              
                pstmt.setInt(4, deptId);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật công việc thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        tbDept.setValueAt(deptName, selectedRow, 1);
        tbDept.setValueAt(manID, selectedRow, 2);
        tbDept.setValueAt(dManID, selectedRow, 3);
        tbDept.setValueAt(deptIdObj, selectedRow, 4);
        setEditStatus(false);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi lưu công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}

private void resetJob() {
    txtDeptName.setText("");
    txtManID.setText("");
   
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbDept = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnAddJob = new javax.swing.JToggleButton();
        btnDeleteJob = new javax.swing.JToggleButton();
        txtManID = new javax.swing.JTextField();
        btnEditJob = new javax.swing.JToggleButton();
        btnSaveJob = new javax.swing.JButton();
        btnResetJob = new javax.swing.JToggleButton();
        txtDeptName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        btnCancelJob = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        txtDManID = new javax.swing.JTextField();

        tbDept.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "DepartentName", "ManagerID", "DeputyManagerID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbDept);

        jLabel6.setText("Department Name");

        jLabel7.setText("Manager ID");

        jLabel8.setText("Deputy Manager ID");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Function :");

        btnAddJob.setText("Add");
        btnAddJob.setToolTipText("");
        btnAddJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddJobActionPerformed(evt);
            }
        });

        btnDeleteJob.setText("Delete");
        btnDeleteJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteJobActionPerformed(evt);
            }
        });

        txtManID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtManIDActionPerformed(evt);
            }
        });

        btnEditJob.setText("Edit");
        btnEditJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditJobActionPerformed(evt);
            }
        });

        btnSaveJob.setText("Save");
        btnSaveJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveJobActionPerformed(evt);
            }
        });

        btnResetJob.setText("Reset");
        btnResetJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetJobActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Department");

        btnCancelJob.setText("Cancel");
        btnCancelJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelJobActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Data entry form :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Department list:");

        txtDManID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDManIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(180, 180, 180)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4))))
            .addGroup(layout.createSequentialGroup()
                .addGap(227, 227, 227)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDeleteJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDeptName, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtManID, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDManID, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(399, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(370, 370, 370)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnResetJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSaveJob)
                .addGap(18, 18, 18)
                .addComponent(btnCancelJob)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddJob)
                                        .addComponent(btnEditJob)
                                        .addComponent(btnDeleteJob))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDeptName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtManID, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDManID, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResetJob)
                    .addComponent(btnSaveJob)
                    .addComponent(btnCancelJob))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddJobActionPerformed
        // TODO add your handling code here:
    // Xóa dữ liệu cũ trong các ô nhập liệu
    txtDeptName.setText("");
    txtManID.setText("");
    

    setEditStatus(true);
    isEditMode = true;

    for (ActionListener al : btnSaveJob.getActionListeners()) {
        btnSaveJob.removeActionListener(al);
    }

    btnSaveJob.addActionListener(e -> {
        String deptName = txtDeptName.getText().trim();
        String manID = txtManID.getText().trim();
        String dManID = txtDManID.getText().trim();
        

        if (deptName.isEmpty() || manID.isEmpty() || dManID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

      

        String insertSQL = "INSERT INTO Departments (DepartmentName, ManagerID, DeputyManagerID) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, deptName);
            pstmt.setString(2, manID);
            pstmt.setString(3, dManID);
            

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Thêm công việc thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadJobs();
                setEditStatus(false);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    });
    }//GEN-LAST:event_btnAddJobActionPerformed

    private void btnDeleteJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteJobActionPerformed
        // TODO add your handling code here:
        deleteJob();
        
    for (ActionListener al : btnDeleteJob.getActionListeners()) {
        btnDeleteJob.removeActionListener(al);
    }

    btnDeleteJob.addActionListener(e -> {
        int selectedRow = tbDept.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một công việc để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int DeptID = (int) tbDept.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa công việc này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConnectDatabase.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Jobs WHERE JobID = ?")) {
                pstmt.setInt(1, DeptID);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa công việc thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadJobs();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    }//GEN-LAST:event_btnDeleteJobActionPerformed

    private void txtManIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtManIDActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtManIDActionPerformed

    private void btnEditJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditJobActionPerformed
        // TODO add your handling code here:
    int selectedRow = tbDept.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một công việc để chỉnh sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    showSelectedJob(selectedRow);

    setEditStatus(true);
    isEditMode = true;

    for (ActionListener al : btnSaveJob.getActionListeners()) {
        btnSaveJob.removeActionListener(al);
    }

    btnSaveJob.addActionListener(e -> {
        int jobId = (int) tbDept.getValueAt(selectedRow, 0);
        String deptName = txtDeptName.getText().trim();
        String manID = txtManID.getText().trim();
        String dManID = txtDManID.getText().trim();
        if (deptName.isEmpty() || manID.isEmpty() || dManID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String updateSQL = "UPDATE Departments SET DepartmentName=?, ManagerID=?, DeputyMangerID=? WHERE DepartmentID=?";

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, deptName);
            pstmt.setString(2, manID);
            pstmt.setString(3, dManID);
                        

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật công việc thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadJobs();
                setEditStatus(false);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    });
    }//GEN-LAST:event_btnEditJobActionPerformed

    private void btnSaveJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveJobActionPerformed
        // TODO add your handling code here:
    btnSaveJob.setEnabled(false);

    int selectedRow = tbDept.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Không có công việc nào được chọn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        btnSaveJob.setEnabled(true); 
        return;
    }
    for (ActionListener al : btnSaveJob.getActionListeners()) {
    btnSaveJob.removeActionListener(al);
    }   


    int deptID = Integer.parseInt(tbDept.getValueAt(selectedRow, 0).toString()); 
    String deptName = txtDeptName.getText().trim();
    String manID = txtManID.getText().trim();
    String dManID = txtDManID.getText().trim();

    if (deptName.isEmpty() || manID.isEmpty() || dManID.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        btnSaveJob.setEnabled(true); 
        return;
    }

    String sql = "UPDATE Departments SET DepartmentName = ?, ManagerID = ?, DeputyManagerID = ? WHERE JobID = ?";

    try (Connection conn = ConnectDatabase.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, deptName);
        pstmt.setString(2, manID);      
        pstmt.setString(3, dManID);

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadJobs(); 
        } else {
            JOptionPane.showMessageDialog(this, "Không thể cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    } finally {
        btnSaveJob.setEnabled(true);
    }
    }//GEN-LAST:event_btnSaveJobActionPerformed

    private void btnResetJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetJobActionPerformed
        resetJob();

    }//GEN-LAST:event_btnResetJobActionPerformed

    private void btnCancelJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelJobActionPerformed
        // TODO add your handling code here:
    btnCancelJob.setEnabled(false);

    cancelJob();

    loadJobs();

    btnCancelJob.setEnabled(true);
    }//GEN-LAST:event_btnCancelJobActionPerformed

    private void txtDManIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDManIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDManIDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAddJob;
    private javax.swing.JButton btnCancelJob;
    private javax.swing.JToggleButton btnDeleteJob;
    private javax.swing.JToggleButton btnEditJob;
    private javax.swing.JToggleButton btnResetJob;
    private javax.swing.JButton btnSaveJob;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable tbDept;
    private javax.swing.JTextField txtDManID;
    private javax.swing.JTextField txtDeptName;
    private javax.swing.JTextField txtManID;
    // End of variables declaration//GEN-END:variables
}
