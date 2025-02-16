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
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Moiiii
 */
public class Task extends javax.swing.JPanel {

    /**
     * Creates new form Task
     */
    public Task() {
        initComponents();
        loadTasks();
    }
    
 public void loadTasks() {
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"TaskID", "TaskName", "TaskStartDate", "TaskEndDate", "AssignedTo", "AssignmentID", "JobID", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tbTask.setModel(model);

    String sql = "SELECT TaskID, TaskName, TaskStartDate, TaskEndDate, AssignedTo, AssignmentID, JobID, Status FROM Tasks";

    try (Connection conn = ConnectDatabase.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            int taskId = rs.getInt("TaskID");
            String taskName = rs.getString("TaskName");
            java.sql.Date sqlStartDate = rs.getDate("TaskStartDate");
            java.sql.Date sqlEndDate = rs.getDate("TaskEndDate");
            int assignedTo = rs.getInt("AssignedTo");
            int assignmentId = rs.getInt("AssignmentID");
            int jobId = rs.getInt("JobID");
            String status = rs.getString("Status"); // ENUM trả về dạng chuỗi

            // Chuyển đổi sang java.util.Date nếu cần thiết
            java.util.Date startDate = (sqlStartDate != null) ? new java.util.Date(sqlStartDate.getTime()) : null;
            java.util.Date endDate = (sqlEndDate != null) ? new java.util.Date(sqlEndDate.getTime()) : null;

            // Thêm dữ liệu vào bảng
            model.addRow(new Object[]{taskId, taskName, startDate, endDate, assignedTo, assignmentId, jobId, status});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    tbTask.setDefaultEditor(Object.class, null);
    tbTask.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    if (isEditMode) {
        setEditStatus(true);
    } else {
        setEditStatus(false);
    }

    if (tbTask.getRowCount() > 0 && tbTask.getSelectedRow() == -1) {
        tbTask.setRowSelectionInterval(0, 0);
        showSelectedTask(0);
    }

    tbTask.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting() && tbTask.getSelectedRow() != -1) {
            showSelectedTask(tbTask.getSelectedRow());

            if (isEditMode) {
                setEditStatus(true);
            }
        }
    });
}

 private void showSelectedTask(int row) {
    txtTaskName.setText(tbTask.getValueAt(row, 1).toString());

    Object startDateObj = tbTask.getValueAt(row, 2);
    if (startDateObj != null) {
        jcdTaskStartDate.setDate((java.util.Date) startDateObj);
    } else {
        jcdTaskStartDate.setDate(null);
    }

    Object endDateObj = tbTask.getValueAt(row, 3);
    if (endDateObj != null) {
        jcdTaskEndDate.setDate((java.util.Date) endDateObj);
    } else {
        jcdTaskEndDate.setDate(null);
    }

    txtAssignedTo.setText(tbTask.getValueAt(row, 4).toString());
    txtAssignmentID.setText(tbTask.getValueAt(row, 5).toString());
    txtJobID.setText(tbTask.getValueAt(row, 6).toString());

    String status = tbTask.getValueAt(row, 7).toString();

    setEditStatus(false);
}

     private boolean isEditMode = false; 
 
 public void setEditStatus(boolean editable) {
    isEditMode = editable;
    
    txtTaskName.setEnabled(editable);
    jcdTaskStartDate.setEnabled(editable);
    jcdTaskEndDate.setEnabled(editable);
    txtAssignedTo.setEnabled(editable);
    txtAssignmentID.setEnabled(editable);
    txtJobID.setEnabled(editable);

    btnSaveTask.setEnabled(editable);
    btnCancelTask.setEnabled(editable);
    btnResetTask.setEnabled(editable);

    btnAddTask.setEnabled(!editable);
    btnEditTask.setEnabled(!editable);
    btnDeleteTask.setEnabled(!editable);
}

 private void resetButtons() {
    btnAddTask.setEnabled(true);
    btnEditTask.setEnabled(true);
    btnDeleteTask.setEnabled(true);
    btnResetTask.setEnabled(true);
}

 private void deleteTask() {
    int selectedRow = tbTask.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhiệm vụ để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!btnDeleteTask.isEnabled()) {
        resetButtons();
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhiệm vụ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        String taskID = tbTask.getValueAt(selectedRow, 0).toString();

        String sql = "DELETE FROM Tasks WHERE TaskID = ?";
        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(taskID));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadTasks();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    loadTasks();
    btnCancelTask.setEnabled(false);
}

 private void cancelTask() {
    setEditStatus(false);
}

private boolean isEditing = false;
private int selectedTaskId = -1;

private void saveTask() {
    int selectedRow = tbTask.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhiệm vụ để lưu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Object taskIdObj = tbTask.getValueAt(selectedRow, 0);
    String taskName = txtTaskName.getText().trim();
    String assignedTo = txtAssignedTo.getText().trim();
    String assignmentID = txtAssignmentID.getText().trim();
    String jobID = txtJobID.getText().trim();
    java.util.Date startDate = jcdTaskStartDate.getDate();
    java.util.Date endDate = jcdTaskEndDate.getDate();

    if (taskName.isEmpty() || assignedTo.isEmpty() || assignmentID.isEmpty() || jobID.isEmpty() || startDate == null || endDate == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        return;
    }

    java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
    java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

    try (Connection conn = ConnectDatabase.getConnection()) {
        if (taskIdObj == null) { 
            String insertSQL = "INSERT INTO Tasks (TaskName, AssignedTo, AssignmentID, JobID, TaskStartDate, TaskEndDate) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, taskName);
                pstmt.setString(2, assignedTo);
                pstmt.setInt(3, Integer.parseInt(assignmentID));
                pstmt.setInt(4, Integer.parseInt(jobID));
                pstmt.setDate(5, sqlStartDate);
                pstmt.setDate(6, sqlEndDate);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            int newTaskId = rs.getInt(1);
                            tbTask.setValueAt(newTaskId, selectedRow, 0);
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Thêm nhiệm vụ mới thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else { 
            int taskId = (int) taskIdObj;
            String updateSQL = "UPDATE Tasks SET TaskName=?, AssignedTo=?, AssignmentID=?, JobID=?, TaskStartDate=?, TaskEndDate=? WHERE TaskID=?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setString(1, taskName);
                pstmt.setString(2, assignedTo);
                pstmt.setInt(3, Integer.parseInt(assignmentID));
                pstmt.setInt(4, Integer.parseInt(jobID));
                pstmt.setDate(5, sqlStartDate);
                pstmt.setDate(6, sqlEndDate);
                pstmt.setInt(7, taskId);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật nhiệm vụ thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        tbTask.setValueAt(taskName, selectedRow, 1);
        tbTask.setValueAt(assignedTo, selectedRow, 2);
        tbTask.setValueAt(assignmentID, selectedRow, 3);
        tbTask.setValueAt(jobID, selectedRow, 4);
        tbTask.setValueAt(sqlStartDate, selectedRow, 5);
        tbTask.setValueAt(sqlEndDate, selectedRow, 6);
        setEditStatus(false);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi lưu nhiệm vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


private void resetTask() {
    txtTaskName.setText("");
    txtAssignedTo.setText("");
    txtAssignmentID.setText("");
    txtJobID.setText("");
    jcdTaskStartDate.setDate(null);
    jcdTaskEndDate.setDate(null);
}

    
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btnSaveTask = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnAddTask = new javax.swing.JButton();
        btnEditTask = new javax.swing.JButton();
        btnDeleteTask = new javax.swing.JButton();
        btnResetTask = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTaskName = new javax.swing.JTextField();
        txtJobName = new javax.swing.JTextField();
        txtAssignedTo = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtAssignmentID = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTask = new javax.swing.JTable();
        txtJobID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jcdTaskStartDate = new com.toedter.calendar.JDateChooser();
        btnCancelTask = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jcdTaskEndDate = new com.toedter.calendar.JDateChooser();

        btnSaveTask.setText("Save");
        btnSaveTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTaskActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Data entry form :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Function :");

        btnAddTask.setText("Add");
        btnAddTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTaskActionPerformed(evt);
            }
        });

        btnEditTask.setText("Edit");
        btnEditTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTaskActionPerformed(evt);
            }
        });

        btnDeleteTask.setText("Delete");
        btnDeleteTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTaskActionPerformed(evt);
            }
        });

        btnResetTask.setText("Reset");
        btnResetTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetTaskActionPerformed(evt);
            }
        });

        jLabel6.setText("TaskName :");

        jLabel7.setText("TaskStartDate :");

        jLabel8.setText("AssignedTo :");

        jLabel9.setText("AssignmentID :");

        jLabel10.setText("JobID :");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("List task of job : ");

        txtTaskName.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                txtTaskNameHierarchyChanged(evt);
            }
        });

        txtJobName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        tbTask.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Task Name", "Task Date", "AssignedTo", "AssignmentID", "JobID", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbTask);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("List task :");

        btnCancelTask.setText("Cancel");
        btnCancelTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelTaskActionPerformed(evt);
            }
        });

        btnSearch.setText("Search");

        jLabel12.setText("TaskEndDate :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddTask)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditTask)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteTask))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(291, 291, 291)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJobName, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)))
                .addGap(0, 337, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTaskName))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAssignmentID)
                                    .addComponent(txtAssignedTo))))
                        .addGap(337, 337, 337))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(96, 96, 96)
                                    .addComponent(jcdTaskStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jcdTaskEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtJobID, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSaveTask)
                        .addGap(18, 18, 18)
                        .addComponent(btnResetTask)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelTask)
                        .addGap(173, 173, 173))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtJobName, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(btnSearch)))
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnAddTask)
                    .addComponent(btnEditTask)
                    .addComponent(btnDeleteTask))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTaskName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtAssignedTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtAssignmentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtJobID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jcdTaskStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13)
                                .addComponent(jLabel12))
                            .addComponent(jcdTaskEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSaveTask)
                            .addComponent(btnCancelTask)
                            .addComponent(btnResetTask))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelTaskActionPerformed
        // TODO add your handling code here:
            btnCancelTask.setEnabled(false);

    cancelTask();

    loadTasks();

    btnCancelTask.setEnabled(true);
    }//GEN-LAST:event_btnCancelTaskActionPerformed

    private void txtTaskNameHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_txtTaskNameHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTaskNameHierarchyChanged

    private void btnAddTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTaskActionPerformed
        // TODO add your handling code here:
            // Xóa dữ liệu cũ trong các ô nhập liệu
    txtTaskName.setText("");
    txtAssignedTo.setText("");
    txtAssignmentID.setText("");
    txtJobID.setText("");
    jcdTaskStartDate.setDate(null);
    jcdTaskEndDate.setDate(null);

    setEditStatus(true);
    isEditMode = true;

    // Xóa tất cả các sự kiện cũ trên btnSaveTask
    for (ActionListener al : btnSaveTask.getActionListeners()) {
        btnSaveTask.removeActionListener(al);
    }

    // Thêm sự kiện mới cho btnSaveTask
    btnSaveTask.addActionListener(e -> {
        String taskName = txtTaskName.getText().trim();
        String assignedTo = txtAssignedTo.getText().trim();
        String assignmentID = txtAssignmentID.getText().trim();
        String jobID = txtJobID.getText().trim();
        java.util.Date startDate = jcdTaskStartDate.getDate();
        java.util.Date endDate = jcdTaskEndDate.getDate();

        if (taskName.isEmpty() || assignedTo.isEmpty() || assignmentID.isEmpty() || jobID.isEmpty() || startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

        try (Connection conn = ConnectDatabase.getConnection()) {
            String insertSQL = "INSERT INTO Tasks (TaskName, AssignedTo, AssignmentID, JobID, TaskStartDate, TaskEndDate) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, taskName);
                pstmt.setString(2, assignedTo);
                pstmt.setString(3, assignmentID);
                pstmt.setString(4, jobID);
                pstmt.setDate(5, sqlStartDate);
                pstmt.setDate(6, sqlEndDate);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm nhiệm vụ thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadTasks();
                    setEditStatus(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhiệm vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhiệm vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    });
    }//GEN-LAST:event_btnAddTaskActionPerformed

    private void btnEditTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditTaskActionPerformed
        // TODO add your handling code here:
            int selectedRow = tbTask.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhiệm vụ để chỉnh sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    showSelectedTask(selectedRow);

    setEditStatus(true);
    isEditMode = true;

    for (ActionListener al : btnSaveTask.getActionListeners()) {
        btnSaveTask.removeActionListener(al);
    }

    btnSaveTask.addActionListener(e -> {
        int taskId = (int) tbTask.getValueAt(selectedRow, 0);
        String taskName = txtTaskName.getText().trim();
        String assignedTo = txtAssignedTo.getText().trim();
        String assignmentID = txtAssignmentID.getText().trim();
        String jobID = txtJobID.getText().trim();
        java.util.Date startDate = jcdTaskStartDate.getDate();
        java.util.Date endDate = jcdTaskEndDate.getDate();

        if (taskName.isEmpty() || assignedTo.isEmpty() || assignmentID.isEmpty() || jobID.isEmpty() || startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

        String updateSQL = "UPDATE Tasks SET TaskName=?, AssignedTo=?, AssignmentID=?, JobID=?, TaskStartDate=?, TaskEndDate=? WHERE TaskID=?";

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, taskName);
            pstmt.setString(2, assignedTo);
            pstmt.setString(3, assignmentID);
            pstmt.setString(4, jobID);
            pstmt.setDate(5, sqlStartDate);
            pstmt.setDate(6, sqlEndDate);
            pstmt.setInt(7, taskId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhiệm vụ thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadTasks();
                setEditStatus(false);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật nhiệm vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật nhiệm vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    });

    }//GEN-LAST:event_btnEditTaskActionPerformed

    private void btnDeleteTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTaskActionPerformed
        // TODO add your handling code here:
    deleteTask();
    
    for (ActionListener al : btnDeleteTask.getActionListeners()) {
        btnDeleteTask.removeActionListener(al);
    }

    btnDeleteTask.addActionListener(e -> {
        int selectedRow = tbTask.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhiệm vụ để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int taskId = (int) tbTask.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhiệm vụ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConnectDatabase.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Tasks WHERE TaskID = ?")) {
                pstmt.setInt(1, taskId);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa nhiệm vụ thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadTasks();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa nhiệm vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhiệm vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    }//GEN-LAST:event_btnDeleteTaskActionPerformed

    private void btnResetTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetTaskActionPerformed
        // TODO add your handling code here:
        resetTask();
    }//GEN-LAST:event_btnResetTaskActionPerformed

    private void btnSaveTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTaskActionPerformed
        // TODO add your handling code here:
           btnSaveTask.setEnabled(false);

    int selectedRow = tbTask.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Không có nhiệm vụ nào được chọn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        btnSaveTask.setEnabled(true); 
        return;
    }

    for (ActionListener al : btnSaveTask.getActionListeners()) {
        btnSaveTask.removeActionListener(al);
    }   

    int taskId = Integer.parseInt(tbTask.getValueAt(selectedRow, 0).toString()); 
    String taskName = txtTaskName.getText().trim();
    String assignedTo = txtAssignedTo.getText().trim();
    String assignmentID = txtAssignmentID.getText().trim();
    String jobID = txtJobID.getText().trim();
    java.util.Date startDate = jcdTaskStartDate.getDate();
    java.util.Date endDate = jcdTaskEndDate.getDate();

    if (taskName.isEmpty() || assignedTo.isEmpty() || assignmentID.isEmpty() || jobID.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        btnSaveTask.setEnabled(true); 
        return;
    }

    String sql = "UPDATE Tasks SET TaskName = ?, AssignedTo = ?, AssignmentID = ?, JobID = ?, TaskStartDate = ?, TaskEndDate = ? WHERE TaskID = ?";

    try (Connection conn = ConnectDatabase.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, taskName);
        pstmt.setString(2, assignedTo);
        pstmt.setString(3, assignmentID);
        pstmt.setString(4, jobID);
        pstmt.setDate(5, (startDate != null) ? new java.sql.Date(startDate.getTime()) : null);
        pstmt.setDate(6, (endDate != null) ? new java.sql.Date(endDate.getTime()) : null);
        pstmt.setInt(7, taskId);

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật nhiệm vụ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadTasks(); 
        } else {
            JOptionPane.showMessageDialog(this, "Không thể cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    } finally {
        btnSaveTask.setEnabled(true);
    }
    }//GEN-LAST:event_btnSaveTaskActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTask;
    private javax.swing.JButton btnCancelTask;
    private javax.swing.JButton btnDeleteTask;
    private javax.swing.JButton btnEditTask;
    private javax.swing.JButton btnResetTask;
    private javax.swing.JButton btnSaveTask;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private com.toedter.calendar.JDateChooser jcdTaskEndDate;
    private com.toedter.calendar.JDateChooser jcdTaskStartDate;
    private javax.swing.JTable tbTask;
    private javax.swing.JTextField txtAssignedTo;
    private javax.swing.JTextField txtAssignmentID;
    private javax.swing.JTextField txtJobID;
    private javax.swing.JTextField txtJobName;
    private javax.swing.JTextField txtTaskName;
    // End of variables declaration//GEN-END:variables
}
