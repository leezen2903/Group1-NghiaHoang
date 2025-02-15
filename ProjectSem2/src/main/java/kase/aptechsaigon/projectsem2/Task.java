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
        loadTaskStatuses();
    }
    
 public void loadTasks() {
    DefaultTableModel model = new DefaultTableModel(new Object[]{"TaskID", "TaskName", "TaskDate", "AssignedTo", "AssignmentID", "JobID", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tbTask.setModel(model);

    String sql = "SELECT TaskID, TaskName, TaskDate, AssignedTo, AssignmentID, JobID, Status FROM Tasks";

    try (Connection conn = ConnectDatabase.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            int taskId = rs.getInt("TaskID");
            String taskName = rs.getString("TaskName");
            java.sql.Date sqlTaskDate = rs.getDate("TaskDate");
            int assignedTo = rs.getInt("AssignedTo");
            int assignmentId = rs.getInt("AssignmentID");
            int jobId = rs.getInt("JobID");
            String status = rs.getString("Status");

            java.util.Date taskDate = (sqlTaskDate != null) ? new java.util.Date(sqlTaskDate.getTime()) : null;

            model.addRow(new Object[]{taskId, taskName, taskDate, assignedTo, assignmentId, jobId, status});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    tbTask.setDefaultEditor(Object.class, null);
    tbTask.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    if (tbTask.getRowCount() > 0) {
        tbTask.setRowSelectionInterval(0, 0);
        showSelectedTask(0);
    }

    tbTask.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting() && tbTask.getSelectedRow() != -1) {
            showSelectedTask(tbTask.getSelectedRow());
        }
    });

    setTaskFieldsEditable(false);
}

    private void showSelectedTask(int row) {
        txtTaskID.setText(tbTask.getValueAt(row, 0).toString()); 

        txtTaskName.setText(tbTask.getValueAt(row, 1).toString());

        Object dateObj = tbTask.getValueAt(row, 2);
        if (dateObj != null) {
            jcdDateTask.setDate((java.util.Date) dateObj);
        } else {
            jcdDateTask.setDate(null);
        }

        txtAssignedTo.setText(tbTask.getValueAt(row, 3).toString());
        txtAssignmentID.setText(tbTask.getValueAt(row, 4).toString());
        txtJobID.setText(tbTask.getValueAt(row, 5).toString());
        cbbStatusTask.setSelectedItem(tbTask.getValueAt(row, 6).toString());
        
        setTaskFieldsEditable(true); 
    }
   private void loadTaskStatuses() {
       cbbStatusTask.removeAllItems();
       String sql = "SELECT DISTINCT Status FROM Tasks";

       try (Connection conn = ConnectDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

           while (rs.next()) {
               cbbStatusTask.addItem(rs.getString("Status"));
           }

       } catch (SQLException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
       }
   }
   
    public void setTaskFieldsEditable(boolean editable) {
        txtTaskName.setEditable(editable);
        txtAssignedTo.setEditable(editable);
        txtAssignmentID.setEditable(editable);
        txtJobID.setEditable(editable);
        jcdDateTask.setEnabled(editable); 
        cbbStatusTask.setEnabled(editable);
    }
    
    private boolean isAdding = false;
    private boolean isEditing = false;
    private boolean isDelete = false;
    private boolean isReset = false;

    private void resetButtons() {
        btnAddTask.setEnabled(true);
        btnEditTask.setEnabled(true);
        btnDeleteTask.setEnabled(true);
    }

    private void addTask() {
        if (isAdding) {
            isAdding = false;
            resetButtons();
        } else {
            isAdding = true;
            isEditing = false;

            btnEditTask.setEnabled(false);
            btnDeleteTask.setEnabled(false);
            btnResetTask.setEnabled(false);
            btnSaveTask.setEnabled(true); 
            btnCancelTask.setEnabled(true); 

            txtTaskID.setText("");
            txtTaskName.setText("");
            txtAssignedTo.setText("");
            txtAssignmentID.setText("");
            txtJobID.setText("");
            cbbStatusTask.setSelectedIndex(0);
            jcdDateTask.setDate(null);

            txtTaskID.setEnabled(true);
            txtTaskName.setEnabled(true);
            txtAssignedTo.setEnabled(true);
            txtAssignmentID.setEnabled(true);
            txtJobID.setEnabled(true);
            cbbStatusTask.setEnabled(true);
            jcdDateTask.setEnabled(true);

            btnSaveTask.setEnabled(true);
            btnCancelTask.setEnabled(true);
        }
        loadTasks();
    }
    
    private void editTask() {
       int selectedRow = tbTask.getSelectedRow();
       if (selectedRow == -1) {
           JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhiệm vụ để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
           return;
       }

       isEditing = true;
       isAdding = false;

       btnAddTask.setEnabled(false);
       btnDeleteTask.setEnabled(false);
       btnResetTask.setEnabled(false);
       btnSaveTask.setEnabled(true);
       btnCancelTask.setEnabled(true);

       txtTaskName.setEnabled(true);
       txtAssignedTo.setEnabled(true);
       txtAssignmentID.setEnabled(true);
       txtJobID.setEnabled(true);
       cbbStatusTask.setEnabled(true);
       jcdDateTask.setEnabled(true);
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
        updateSaveButton();
        loadTasks();
        btnCancelTask.setEnabled(false);
    }

    private void cancelTask() {
        if (!isAdding && !isEditing) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức năng Thêm hoặc Sửa trước khi hủy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        txtTaskID.setText("");
        txtTaskName.setText("");
        txtAssignedTo.setText("");
        txtAssignmentID.setText("");
        txtJobID.setText("");
        cbbStatusTask.setSelectedIndex(0);
        jcdDateTask.setDate(null);

        isAdding = false;
        isEditing = false;

        resetButtons();
        updateSaveButton();

        btnCancelTask.setEnabled(false);

        JOptionPane.showMessageDialog(this, "Đã hủy thao tác!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void saveTask() {
        if (!isAdding && !isEditing) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức năng Thêm, Sửa, Xóa hoặc Đặt lại trước khi lưu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String taskName = txtTaskName.getText().trim();
        String assignedTo = txtAssignedTo.getText().trim();
        String assignmentID = txtAssignmentID.getText().trim();
        String jobID = txtJobID.getText().trim();
        String status = cbbStatusTask.getSelectedItem().toString();

        java.util.Date utilDate = jcdDateTask.getDate();
        java.sql.Date sqlTaskDate = (utilDate != null) ? new java.sql.Date(utilDate.getTime()) : null;

        if (taskName.isEmpty() || assignedTo.isEmpty() || assignmentID.isEmpty() || jobID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isAdding) {
            String sql = "INSERT INTO Tasks (TaskName, AssignedTo, AssignmentID, JobID, Status, TaskDate) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = ConnectDatabase.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, taskName);
                pstmt.setString(2, assignedTo);
                pstmt.setInt(3, Integer.parseInt(assignmentID));
                pstmt.setInt(4, Integer.parseInt(jobID));
                pstmt.setString(5, status);
                pstmt.setDate(6, sqlTaskDate);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            txtTaskID.setText(String.valueOf(generatedKeys.getInt(1)));
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Thêm nhiệm vụ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm dữ liệu vào database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } else if (isEditing) {
            try {
                int selectedRow = tbTask.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhiệm vụ để chỉnh sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int taskIDInt = Integer.parseInt(txtTaskID.getText().trim());
                String sql = "UPDATE Tasks SET TaskName = ?, AssignedTo = ?, AssignmentID = ?, JobID = ?, Status = ?, TaskDate = ? WHERE TaskID = ?";

                try (Connection conn = ConnectDatabase.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, taskName);
                    pstmt.setString(2, assignedTo);
                    pstmt.setInt(3, Integer.parseInt(assignmentID));
                    pstmt.setInt(4, Integer.parseInt(jobID));
                    pstmt.setString(5, status);
                    pstmt.setDate(6, sqlTaskDate);
                    pstmt.setInt(7, taskIDInt);

                    int updatedRows = pstmt.executeUpdate();
                    if (updatedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Cập nhật nhiệm vụ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy nhiệm vụ để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Lỗi: ID nhiệm vụ không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

        loadTasks();

        isAdding = false;
        isEditing = false;

        btnSaveTask.setEnabled(false);
        btnCancelTask.setEnabled(false);
        btnAddTask.setEnabled(true);
        btnEditTask.setEnabled(true);
        btnDeleteTask.setEnabled(true);
        btnResetTask.setEnabled(true);
    }

    private void resetTask() {
        if (isReset) {
            isReset = false;
            resetButtons();
        } else {
            isReset = true;
            isAdding = false;
            isEditing = false;
            isDelete = false;

            btnAddTask.setEnabled(false);
            btnEditTask.setEnabled(false);
            btnDeleteTask.setEnabled(false);

            txtTaskID.setText("");
            txtTaskName.setText("");
            txtAssignedTo.setText("");
            txtAssignmentID.setText("");
            txtJobID.setText("");
            cbbStatusTask.setSelectedIndex(0);
            jcdDateTask.setDate(null);

            btnCancelTask.setEnabled(true);
        }
        btnCancelTask.setEnabled(true);
        updateSaveButton();
    }

           
        private void updateSaveButton() {
    btnSaveTask.setEnabled(isAdding || isEditing);
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTaskID = new javax.swing.JTextField();
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
        jcdDateTask = new com.toedter.calendar.JDateChooser();
        btnCancelTask = new javax.swing.JButton();
        cbbStatusTask = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();

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

        jLabel5.setText("TaskID :");

        jLabel6.setText("TaskName :");

        jLabel7.setText("TaskDate :");

        jLabel8.setText("AssignedTo :");

        jLabel9.setText("AssignmentID :");

        jLabel10.setText("JobID :");

        jLabel11.setText("Status :");

        txtTaskID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTaskIDActionPerformed(evt);
            }
        });

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
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Task ID", "Task Name", "Task Date", "AssignedTo", "AssignmentID", "JobID", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, false
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

        cbbStatusTask.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSearch.setText("Search");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTaskName)
                                    .addComponent(txtTaskID)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAssignmentID)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtJobID, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 275, Short.MAX_VALUE))
                                    .addComponent(txtAssignedTo))))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcdDateTask, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbStatusTask, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(231, 231, 231)
                        .addComponent(btnSaveTask)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelTask)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                        .addComponent(btnDeleteTask)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetTask))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(291, 291, 291)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJobName, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)))
                .addGap(0, 0, Short.MAX_VALUE))
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
                    .addComponent(btnDeleteTask)
                    .addComponent(btnResetTask))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTaskID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
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
                            .addComponent(txtJobID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jcdDateTask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(btnSaveTask)
                    .addComponent(btnCancelTask)
                    .addComponent(cbbStatusTask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTaskIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTaskIDActionPerformed
        // TODO add your handling code here:
        
        loadTasks();
    }//GEN-LAST:event_txtTaskIDActionPerformed

    private void btnCancelTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelTaskActionPerformed
        // TODO add your handling code here:
        cancelTask();

    }//GEN-LAST:event_btnCancelTaskActionPerformed

    private void txtTaskNameHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_txtTaskNameHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTaskNameHierarchyChanged

    private void btnAddTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTaskActionPerformed
        // TODO add your handling code here:
        addTask();
        loadTasks();
    }//GEN-LAST:event_btnAddTaskActionPerformed

    private void btnEditTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditTaskActionPerformed
        // TODO add your handling code here:
        editTask();
        loadTasks();
    }//GEN-LAST:event_btnEditTaskActionPerformed

    private void btnDeleteTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTaskActionPerformed
        // TODO add your handling code here:
        deleteTask();
        loadTasks();
    }//GEN-LAST:event_btnDeleteTaskActionPerformed

    private void btnResetTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetTaskActionPerformed
        // TODO add your handling code here:
        resetTask();
    }//GEN-LAST:event_btnResetTaskActionPerformed

    private void btnSaveTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTaskActionPerformed
        // TODO add your handling code here:
        saveTask();
    }//GEN-LAST:event_btnSaveTaskActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTask;
    private javax.swing.JButton btnCancelTask;
    private javax.swing.JButton btnDeleteTask;
    private javax.swing.JButton btnEditTask;
    private javax.swing.JButton btnResetTask;
    private javax.swing.JButton btnSaveTask;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbbStatusTask;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private com.toedter.calendar.JDateChooser jcdDateTask;
    private javax.swing.JTable tbTask;
    private javax.swing.JTextField txtAssignedTo;
    private javax.swing.JTextField txtAssignmentID;
    private javax.swing.JTextField txtJobID;
    private javax.swing.JTextField txtJobName;
    private javax.swing.JTextField txtTaskID;
    private javax.swing.JTextField txtTaskName;
    // End of variables declaration//GEN-END:variables
}
