/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

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
public class Job extends javax.swing.JPanel {


    /**
     * Creates new form Jobb
     */
    public Job() {
        
            initComponents();
            loadJobs();
        }

    public void loadJobs() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"JobID", "JobName", "Description", "EstimatedStartDate", "EstimatedEndDate", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbJob.setModel(model);

        String sql = "SELECT JobID, JobName, Description, EstimatedStartDate, EstimatedEndDate, Status FROM Jobs";

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int jobId = rs.getInt("JobID");
                String jobName = rs.getString("JobName");
                String description = rs.getString("Description");
                java.sql.Date sqlStartDate = rs.getDate("EstimatedStartDate");
                java.sql.Date sqlEndDate = rs.getDate("EstimatedEndDate");
                String status = rs.getString("Status");

                java.util.Date startDate = (sqlStartDate != null) ? new java.util.Date(sqlStartDate.getTime()) : null;
                java.util.Date endDate = (sqlEndDate != null) ? new java.util.Date(sqlEndDate.getTime()) : null;

                model.addRow(new Object[]{jobId, jobName, description, startDate, endDate, status});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        tbJob.setDefaultEditor(Object.class, null);
        tbJob.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (tbJob.getRowCount() > 0) {
            tbJob.setRowSelectionInterval(0, 0);
            showSelectedJob(0);
        }

        tbJob.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tbJob.getSelectedRow() != -1) {
                showSelectedJob(tbJob.getSelectedRow());
            }
        });

        setJobFieldsEditable(false);
    }


    private void showSelectedJob(int row) {
        txtJobID.setText(tbJob.getValueAt(row, 0).toString());
        txtJobName.setText(tbJob.getValueAt(row, 1).toString());
        txtDescription.setText(tbJob.getValueAt(row, 2).toString());

        Object startDateObj = tbJob.getValueAt(row, 3);
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
        }

        String status = tbJob.getValueAt(row, 5).toString();

        setJobFieldsEditable(true);
    }

    public void setJobFieldsEditable(boolean editable) {
        txtJobName.setEditable(editable);
        txtDescription.setEditable(editable);
        jcdEstimatedStartDate.setEnabled(editable);
        jcdEstimatedEndDate.setEnabled(editable);
    }


    private void resetButtons() {
        btnAddJob.setEnabled(true);
        btnEditJob.setEnabled(true);
        btnDeleteJob.setEnabled(true);
        btnResetJob.setEnabled(true);
    }
    
    private boolean isAdding = false;
    private boolean isEditing = false;
    private boolean isDelete = false;
    private boolean isReset = false;

    
    private void addJob() {
    if (isAdding) {
        isAdding = false;
        resetButtons();
    } else {
        isAdding = true;
        isEditing = false;

        btnEditJob.setEnabled(false);
        btnDeleteJob.setEnabled(false);
        btnResetJob.setEnabled(false);
        btnSaveJob.setEnabled(true);
        btnCancelJob.setEnabled(true);

        txtJobID.setText("");
        txtJobName.setText("");
        txtDescription.setText("");
        jcdEstimatedStartDate.setDate(null);
        jcdEstimatedEndDate.setDate(null);

        txtJobName.setEnabled(true);
        txtDescription.setEnabled(true);
        jcdEstimatedStartDate.setEnabled(true);
        jcdEstimatedEndDate.setEnabled(true);

        btnSaveJob.setEnabled(true);
        btnCancelJob.setEnabled(true);
    }
    loadJobs();
}

 
    private void editJob() {
    int selectedRow = tbJob.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một công việc để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    isEditing = true;
    isAdding = false;

    btnAddJob.setEnabled(false);
    btnDeleteJob.setEnabled(false);
    btnResetJob.setEnabled(false);
    btnSaveJob.setEnabled(true);
    btnCancelJob.setEnabled(true);

    txtJobName.setEnabled(true);
    txtDescription.setEnabled(true);
    jcdEstimatedStartDate.setEnabled(true);
    jcdEstimatedEndDate.setEnabled(true);

    txtJobID.setText(tbJob.getValueAt(selectedRow, 0).toString());
    txtJobName.setText(tbJob.getValueAt(selectedRow, 1).toString());
    txtDescription.setText(tbJob.getValueAt(selectedRow, 2).toString());

    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = tbJob.getValueAt(selectedRow, 3) != null ? tbJob.getValueAt(selectedRow, 3).toString() : null;
        String endDateStr = tbJob.getValueAt(selectedRow, 4) != null ? tbJob.getValueAt(selectedRow, 4).toString() : null;
        
        jcdEstimatedStartDate.setDate(startDateStr != null ? sdf.parse(startDateStr) : null);
        jcdEstimatedEndDate.setDate(endDateStr != null ? sdf.parse(endDateStr) : null);
    } catch (ParseException e) {
        e.printStackTrace();
    }
}
    
    private void deleteJob() {
    int selectedRow = tbJob.getSelectedRow();
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
        String jobID = tbJob.getValueAt(selectedRow, 0).toString();

        String sql = "DELETE FROM Jobs WHERE JobID = ?";
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
//    updateSaveButton();
//    loadJobs();
//    btnCancelJob.setEnabled(false);
}

    private void cancelJob() {
        if (!isAdding && !isEditing) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức năng Thêm hoặc Sửa trước khi hủy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        txtJobID.setText("");
        txtJobName.setText("");
        txtDescription.setText("");
        jcdEstimatedStartDate.setDate(null);
        jcdEstimatedEndDate.setDate(null);

        isAdding = false;
        isEditing = false;

        resetButtons();
        updateSaveButton();

        btnCancelJob.setEnabled(false);

        JOptionPane.showMessageDialog(this, "Đã hủy thao tác!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveJob() {
    if (!isAdding && !isEditing) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn chức năng Thêm, Sửa, Xóa hoặc Đặt lại trước khi lưu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String jobName = txtJobName.getText().trim();
    String description = txtDescription.getText().trim();
    
    java.util.Date utilStartDate = jcdEstimatedStartDate.getDate();
    java.util.Date utilEndDate = jcdEstimatedEndDate.getDate();
    
    java.sql.Date sqlStartDate = (utilStartDate != null) ? new java.sql.Date(utilStartDate.getTime()) : null;
    java.sql.Date sqlEndDate = (utilEndDate != null) ? new java.sql.Date(utilEndDate.getTime()) : null;
    
    if (jobName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (isAdding) {
        String sql = "INSERT INTO Jobs (JobName, Description, EstimatedStartDate, EstimatedEndDate, Status) VALUES (?, ?, ?, ?, 'NotStartedYet')";
        
        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, jobName);
            pstmt.setString(2, description);
            pstmt.setDate(3, sqlStartDate);
            pstmt.setDate(4, sqlEndDate);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        txtJobID.setText(String.valueOf(generatedKeys.getInt(1)));
                    }
                }
                JOptionPane.showMessageDialog(this, "Thêm công việc thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm dữ liệu vào database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    } else if (isEditing) {
        try {
            int selectedRow = tbJob.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một công việc để chỉnh sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int jobIDInt = Integer.parseInt(txtJobID.getText().trim());
            String sql = "UPDATE Jobs SET JobName = ?, Description = ?, EstimatedStartDate = ?, EstimatedEndDate = ? WHERE JobID = ?";
            
            try (Connection conn = ConnectDatabase.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, jobName);
                pstmt.setString(2, description);
                pstmt.setDate(3, sqlStartDate);
                pstmt.setDate(4, sqlEndDate);
                pstmt.setInt(5, jobIDInt);
                
                int updatedRows = pstmt.executeUpdate();
                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật công việc thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy công việc để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: ID công việc không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    loadJobs();
    
    isAdding = false;
    isEditing = false;
    
    btnSaveJob.setEnabled(false);
    btnCancelJob.setEnabled(false);
    btnAddJob.setEnabled(true);
    btnEditJob.setEnabled(true);
    btnDeleteJob.setEnabled(true);
    btnResetJob.setEnabled(true);
}
    
    private void resetJob() {
    if (isReset) {
        isReset = false;
        resetButtons();
    } else {
        isReset = true;
        isAdding = false;
        isEditing = false;
        isDelete = false;

        btnAddJob.setEnabled(false);
        btnEditJob.setEnabled(false);
        btnDeleteJob.setEnabled(false);

        txtJobID.setText("");
        txtJobName.setText("");
        txtDescription.setText("");
        jcdEstimatedStartDate.setDate(null);
        jcdEstimatedEndDate.setDate(null);

        btnCancelJob.setEnabled(true);
    }
    btnCancelJob.setEnabled(true);
    updateSaveButton();
}

private void updateSaveButton() {
    btnSaveJob.setEnabled(isAdding || isEditing);
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbJob = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnAddJob = new javax.swing.JToggleButton();
        txtJobID = new javax.swing.JTextField();
        btnDeleteJob = new javax.swing.JToggleButton();
        txtDescription = new javax.swing.JTextField();
        btnEditJob = new javax.swing.JToggleButton();
        btnSaveJob = new javax.swing.JButton();
        btnResetJob = new javax.swing.JToggleButton();
        txtJobName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        btnCancelJob = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcdEstimatedStartDate = new com.toedter.calendar.JDateChooser();
        jcdEstimatedEndDate = new com.toedter.calendar.JDateChooser();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();

        jLabel5.setText("- JobID ");

        tbJob.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "JobID", "JobName", "Description", "EstimatedStartDate", "EstimatedEndDate", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbJob);

        jLabel6.setText("- JobName");

        jLabel7.setText("- Description");

        jLabel8.setText("- EstimatedStartDate ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Function :");

        jLabel9.setText("- EstimatedEndDate ");

        btnAddJob.setText("Add");
        btnAddJob.setToolTipText("");
        btnAddJob.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddJobMouseClicked(evt);
            }
        });
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

        txtDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescriptionActionPerformed(evt);
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
        jLabel1.setText("Jobs");

        btnCancelJob.setText("Cancel");
        btnCancelJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelJobActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Data entry form :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Job list :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(370, 370, 370)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(423, 423, 423))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDeleteJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnResetJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(76, 76, 76)
                                .addComponent(txtJobID, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(txtJobName, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jcdEstimatedEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jcdEstimatedStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnSaveJob)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancelJob)
                                        .addGap(67, 67, 67)))))
                        .addGap(234, 234, 234))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4)
                        .addContainerGap())))
            .addComponent(jScrollPane1)
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
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(btnAddJob)
                            .addComponent(btnEditJob)
                            .addComponent(btnDeleteJob)
                            .addComponent(btnResetJob))
                        .addGap(28, 28, 28)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtJobID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtJobName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnCancelJob)
                                .addComponent(btnSaveJob))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jcdEstimatedStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(jcdEstimatedEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddJobMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddJobMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddJobMouseClicked

    private void btnAddJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddJobActionPerformed
        // TODO add your handling code here:
        addJob();

       loadJobs();
    }//GEN-LAST:event_btnAddJobActionPerformed

    private void btnDeleteJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteJobActionPerformed
        // TODO add your handling code here:
        deleteJob();
        loadJobs();
    }//GEN-LAST:event_btnDeleteJobActionPerformed

    private void txtDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescriptionActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDescriptionActionPerformed

    private void btnEditJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditJobActionPerformed
        // TODO add your handling code here:
        editJob();
        loadJobs();

    }//GEN-LAST:event_btnEditJobActionPerformed

    private void btnSaveJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveJobActionPerformed
        // TODO add your handling code here:
        saveJob();
        loadJobs();
    }//GEN-LAST:event_btnSaveJobActionPerformed

    private void btnResetJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetJobActionPerformed
        // TODO add your handling code here:
        resetJob();

    }//GEN-LAST:event_btnResetJobActionPerformed

    private void btnCancelJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelJobActionPerformed
        // TODO add your handling code here:
        cancelJob();
    }//GEN-LAST:event_btnCancelJobActionPerformed


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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private com.toedter.calendar.JDateChooser jcdEstimatedEndDate;
    private com.toedter.calendar.JDateChooser jcdEstimatedStartDate;
    private javax.swing.JTable tbJob;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtJobID;
    private javax.swing.JTextField txtJobName;
    // End of variables declaration//GEN-END:variables
}
