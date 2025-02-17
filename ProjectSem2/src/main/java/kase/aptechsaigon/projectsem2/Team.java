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
public class Team extends javax.swing.JPanel {


    /**
     * Creates new form Jobb
     */
    public Team() {
        
            initComponents();
            loadTeams();
        }

public void loadTeams() {
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"TeamID", "TeamName", "DepartmentID", "TeamLeaderID"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tbTeam.setModel(model);

    String sql = "SELECT TeamID, TeamName, DepartmentID, TeamLeaderID FROM Teams";

    try (Connection conn = ConnectDatabase.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            int teamID = rs.getInt("TeamID");
            String teamName = rs.getString("TeamName");
            String departmentID = rs.getString("DepartmentID");
            String teamLeaderID = rs.getString("TeamLeaderID"); 
            model.addRow(new Object[]{teamID, teamName, departmentID, teamLeaderID});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    tbTeam.setDefaultEditor(Object.class, null);
    tbTeam.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    if (isEditMode) {
        setEditStatus(true);
    } else {
        setEditStatus(false);
    }

    if (tbTeam.getRowCount() > 0 && tbTeam.getSelectedRow() == -1) {
        tbTeam.setRowSelectionInterval(0, 0);
        showSelectedJob(0);
    }

    tbTeam.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting() && tbTeam.getSelectedRow() != -1) {
            showSelectedJob(tbTeam.getSelectedRow());

            if (isEditMode) {
                setEditStatus(true);
            }
        }
    });
}

private void showSelectedJob(int row) {
    txtTeamName.setText(tbTeam.getValueAt(row, 1).toString());
    txtDeptID.setText(tbTeam.getValueAt(row, 2).toString());
    txtTeamLeadID.setText(tbTeam.getValueAt(row, 3).toString());
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
        
        txtTeamName.setEnabled(editable);
        txtDeptID.setEnabled(editable);
        txtTeamLeadID.setEnabled(editable);
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
    int selectedRow = tbTeam.getSelectedRow();
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
        String DeptID = tbTeam.getValueAt(selectedRow, 0).toString();

        String sql = "DELETE FROM Teams WHERE TeamID = ?";
        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(DeptID));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadTeams();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
        loadTeams();
        btnCancelJob.setEnabled(false);
}

    private void cancelJob() {
        setEditStatus(false);
    }

private boolean isEditing = false;
private int selectedDeptId = -1;

private void saveJob() {
    int selectedRow = tbTeam.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một công việc để lưu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Object teamIdObj = tbTeam.getValueAt(selectedRow, 0);
    String teamName = txtTeamName.getText().trim();
    String deptID = txtDeptID.getText().trim();
    String teamLeadID = txtTeamLeadID.getText().trim();
    /* java.util.Date startDate = jcdEstimatedStartDate.getDate();
    java.util.Date endDate = jcdEstimatedEndDate.getDate(); */

    if (teamName.isEmpty() || deptID.isEmpty() || teamLeadID.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        return;
    }

   /* java.sql.Date teamLeaderID = new java.sql.Date(startDate.getTime());
    java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime()); */

    try (Connection conn = ConnectDatabase.getConnection()) {
        if (teamIdObj == null) { 
            String insertSQL = "INSERT INTO Teams (TeamName, DepartmentID, TeamLeaderID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, teamName);
                pstmt.setString(2, deptID);
                /*pstmt.setDate(3, teamLeaderID);
                pstmt.setDate(4, sqlEndDate);*/
                pstmt.setString(3, teamLeadID);
               

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            int newDeptId = rs.getInt(1);
                            tbTeam.setValueAt(newDeptId, selectedRow, 0);
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Thêm cơ sở mới thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else { 
            int teamId = (int) teamIdObj;
            String updateSQL = "UPDATE Teams SET TeamName=?, DepartmentID=?, TeamLeaderID=? WHERE TeamID=?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setString(1, teamName);
                pstmt.setString(2, deptID);
                pstmt.setString(3, teamLeadID);              
                pstmt.setInt(4, teamId);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật công việc thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        tbTeam.setValueAt(teamName, selectedRow, 1);
        tbTeam.setValueAt(deptID, selectedRow, 2);
        tbTeam.setValueAt(teamLeadID, selectedRow, 3);
        tbTeam.setValueAt(teamIdObj, selectedRow, 4);
        setEditStatus(false);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi lưu công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}

private void resetJob() {
    txtTeamName.setText("");
    txtDeptID.setText("");
    txtTeamLeadID.setText("");
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTeam = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnAddJob = new javax.swing.JToggleButton();
        btnEditJob = new javax.swing.JToggleButton();
        btnDeleteJob = new javax.swing.JToggleButton();
        txtTeamName = new javax.swing.JTextField();
        txtDeptID = new javax.swing.JTextField();
        txtTeamLeadID = new javax.swing.JTextField();
        btnSaveJob = new javax.swing.JButton();
        btnResetJob = new javax.swing.JToggleButton();
        btnCancelJob = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Team");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Team list:");

        tbTeam.setModel(new javax.swing.table.DefaultTableModel(
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
                "TeamName", "DepartmentID", "TeamLeaderID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
        jScrollPane1.setViewportView(tbTeam);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Function :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Data entry form :");

        jLabel6.setText("Team Name");

        jLabel7.setText("DepartmentID");

        jLabel8.setText("TeamLeaderID");

        btnAddJob.setText("Add");
        btnAddJob.setToolTipText("");
        btnAddJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddJobActionPerformed(evt);
            }
        });

        btnEditJob.setText("Edit");
        btnEditJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditJobActionPerformed(evt);
            }
        });

        btnDeleteJob.setText("Delete");
        btnDeleteJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteJobActionPerformed(evt);
            }
        });

        txtTeamName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTeamNameActionPerformed(evt);
            }
        });

        txtDeptID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDeptIDActionPerformed(evt);
            }
        });

        txtTeamLeadID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTeamLeadIDActionPerformed(evt);
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

        btnCancelJob.setText("Cancel");
        btnCancelJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelJobActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jSeparator5)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDeleteJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDeptID, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTeamLeadID, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTeamName, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnSaveJob)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnResetJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(btnCancelJob))))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addContainerGap(393, Short.MAX_VALUE))
            .addComponent(jSeparator4)
            .addGroup(layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddJob)
                        .addComponent(btnEditJob)
                        .addComponent(btnDeleteJob)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTeamName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDeptID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTeamLeadID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveJob)
                    .addComponent(btnCancelJob)
                    .addComponent(btnResetJob))
                .addContainerGap(49, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddJobActionPerformed
        // TODO add your handling code here:
    // Xóa dữ liệu cũ trong các ô nhập liệu
    txtTeamName.setText("");
    txtDeptID.setText("");
    txtTeamLeadID.setText("");

    setEditStatus(true);
    isEditMode = true;

    for (ActionListener al : btnSaveJob.getActionListeners()) {
        btnSaveJob.removeActionListener(al);
    }

    btnSaveJob.addActionListener(e -> {
        String teamName = txtTeamName.getText().trim();
        String deptID = txtDeptID.getText().trim();
        String teamLeadID = txtTeamLeadID.getText().trim();
        

        if (teamName.isEmpty() || deptID.isEmpty() || teamLeadID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

      

        String insertSQL = "INSERT INTO Teams (TeamName, DepartmentID, TeamLeaderID) VALUES (?, ?, ?)";

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, teamName);
            pstmt.setString(2, deptID);
            pstmt.setString(3, teamLeadID);
            

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Thêm công việc thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadTeams();
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
        int selectedRow = tbTeam.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một công việc để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int DeptID = (int) tbTeam.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa công việc này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConnectDatabase.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Teams WHERE DeptID = ?")) {
                pstmt.setInt(1, DeptID);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa công việc thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadTeams();
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

    private void txtDeptIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDeptIDActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDeptIDActionPerformed

    private void btnEditJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditJobActionPerformed
        // TODO add your handling code here:
    int selectedRow = tbTeam.getSelectedRow();
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
        int DeptId = (int) tbTeam.getValueAt(selectedRow, 0);
        String teamName = txtTeamName.getText().trim();
        String deptID = txtDeptID.getText().trim();
        String teamLeadID = txtTeamLeadID.getText().trim();
        if (teamName.isEmpty() || deptID.isEmpty() || teamLeadID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String updateSQL = "UPDATE Teams SET TeamName=?, DepartmentID=?, TeamLeaderID=? WHERE TeamID=?";

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, teamName);
            pstmt.setString(2, deptID);
            pstmt.setString(3, teamLeadID);
            pstmt.setInt(4, DeptId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật công việc thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadTeams();
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

    btnSaveJob.setEnabled(false);

    int selectedRow = tbTeam.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Không có công việc nào được chọn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        btnSaveJob.setEnabled(true); 
        return;
    }
    for (ActionListener al : btnSaveJob.getActionListeners()) {
    btnSaveJob.removeActionListener(al);
    }   


    int deptID = Integer.parseInt(tbTeam.getValueAt(selectedRow, 0).toString()); 
    String teamName = txtTeamName.getText().trim();
    String txtdeptID = txtDeptID.getText().trim();
    String teamLeadID = txtTeamLeadID.getText().trim();

    if (teamName.isEmpty() || txtdeptID.isEmpty() || teamLeadID.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        btnSaveJob.setEnabled(true); 
        return;
    }

    String sql = "UPDATE Teams SET TeamName = ?, DepartmentID = ?, TeamLeaderID = ? WHERE TeamID = ?";

    try (Connection conn = ConnectDatabase.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, teamName);
        pstmt.setString(2, txtdeptID);      
        pstmt.setString(3, teamLeadID);

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadTeams(); 
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

    loadTeams();

    btnCancelJob.setEnabled(true);
    }//GEN-LAST:event_btnCancelJobActionPerformed

    private void txtTeamLeadIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTeamLeadIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeamLeadIDActionPerformed

    private void txtTeamNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTeamNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeamNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAddJob;
    private javax.swing.JButton btnCancelJob;
    private javax.swing.JToggleButton btnDeleteJob;
    private javax.swing.JToggleButton btnEditJob;
    private javax.swing.JToggleButton btnResetJob;
    private javax.swing.JButton btnSaveJob;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable tbTeam;
    private javax.swing.JTextField txtDeptID;
    private javax.swing.JTextField txtTeamLeadID;
    private javax.swing.JTextField txtTeamName;
    // End of variables declaration//GEN-END:variables
}
