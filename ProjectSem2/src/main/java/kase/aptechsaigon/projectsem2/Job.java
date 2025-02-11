/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
    package kase.aptechsaigon.projectsem2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Moiiii
 */
public class Job extends javax.swing.JFrame {

    /**
     * Creates new form Job
     */
    public Job() {
        initComponents();
        loadJobs(); 
        
       cbNotStartedYet = new JCheckBox("Not Started Yet");
       cbInProgress = new JCheckBox("In Progress");
       cbCompleted = new JCheckBox("Completed");
       cbNotCompleted = new JCheckBox("Not Completed");

       btgStatus = new ButtonGroup();
       btgStatus.add(cbNotStartedYet);
       btgStatus.add(cbInProgress);
       btgStatus.add(cbCompleted);
       btgStatus.add(cbNotCompleted);
    }

    public void loadJobs() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"JobID", "JobName", "Description", "EstimatedStartDate", "EstimatedEndDate", "Status"}, 0) {
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
                int id = rs.getInt("JobID");
                String jobName = rs.getString("JobName");
                String description = rs.getString("Description");
                String startDate = rs.getString("EstimatedStartDate");
                String endDate = rs.getString("EstimatedEndDate");
                String status = rs.getString("Status");

                model.addRow(new Object[]{id, jobName, description, startDate, endDate, status});
            }
            
            tbJob.getColumnModel().getColumn(0).setCellEditor(null);


        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        tbJob.setDefaultEditor(Object.class, null);
        tbJob.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tbJob.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tbJob.getSelectedRow() != -1) {
                int selectedRow = tbJob.getSelectedRow();

                txtJobID.setText(tbJob.getValueAt(selectedRow, 0).toString());
                txtJobName.setText(tbJob.getValueAt(selectedRow, 1).toString());
                txtDescription.setText(tbJob.getValueAt(selectedRow, 2).toString());
                txtEstimatedStartDate.setText(tbJob.getValueAt(selectedRow, 3).toString());
                txtEstimatedEndDate.setText(tbJob.getValueAt(selectedRow, 4).toString());

                String status = tbJob.getValueAt(selectedRow, 5).toString().trim();

                cbNotStartedYet.setSelected(false);
                cbInProgress.setSelected(false);
                cbCompleted.setSelected(false);
                cbNotCompleted.setSelected(false);

                if (status.equalsIgnoreCase("Not Started Yet")) {
                    cbNotStartedYet.setSelected(true);
                } else if (status.equalsIgnoreCase("In Progress")) {
                    cbInProgress.setSelected(true);
                } else if (status.equalsIgnoreCase("Completed")) {
                    cbCompleted.setSelected(true);
                } else if (status.equalsIgnoreCase("Not Completed")) {
                    cbNotCompleted.setSelected(true);
                }
            }
        });


    }   
        private boolean isAdding = false;
        private boolean isEditing = false;

        private void resetButtons() {
            btnAddJob.setEnabled(true);
            btnEditJob.setEnabled(true);
            btnDeleteJob.setEnabled(true);
        }

        private void addJob() {
            if (isAdding) {
                isAdding = false;
                resetButtons();
            } else {
                isAdding = true;
                isEditing = false;

                btnEditJob.setEnabled(false);
                btnDeleteJob.setEnabled(false);

                txtJobID.setText(""); 
                txtJobName.setText("");
                txtDescription.setText("");
                txtEstimatedStartDate.setText("");
                txtEstimatedEndDate.setText("");
                btgStatus.clearSelection();
            }
        }

        private void editJob() {
            if (isEditing) {
                isEditing = false;
                resetButtons();
            } else {
                int selectedRow = tbJob.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn một công việc để chỉnh sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                isEditing = true;
                isAdding = false;

                btnAddJob.setEnabled(false);
                btnDeleteJob.setEnabled(false);

                txtJobID.setText(tbJob.getValueAt(selectedRow, 0).toString());
                txtJobName.setText(tbJob.getValueAt(selectedRow, 1).toString());
                txtDescription.setText(tbJob.getValueAt(selectedRow, 2).toString());
                txtEstimatedStartDate.setText(tbJob.getValueAt(selectedRow, 3).toString());
                txtEstimatedEndDate.setText(tbJob.getValueAt(selectedRow, 4).toString());

                String status = tbJob.getValueAt(selectedRow, 5).toString();
                btgStatus.clearSelection();

                if (status.equalsIgnoreCase("Not Started Yet")) {
                    cbNotStartedYet.setSelected(true);
                } else if (status.equalsIgnoreCase("In Progress")) {
                    cbInProgress.setSelected(true);
                } else if (status.equalsIgnoreCase("Completed")) {
                    cbCompleted.setSelected(true);
                } else if (status.equalsIgnoreCase("Not Completed")) {
                    cbNotCompleted.setSelected(true);
                }
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
        }

        private void saveJob() {
            if (!isAdding && !isEditing) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chức năng Add hoặc Edit trước khi lưu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String jobID = txtJobID.getText().trim();
            String jobName = txtJobName.getText().trim();
            String description = txtDescription.getText().trim();
            String startDate = txtEstimatedStartDate.getText().trim();
            String endDate = txtEstimatedEndDate.getText().trim();

            String status = "";
            if (cbNotStartedYet.isSelected()) status = "Not Started Yet";
            else if (cbInProgress.isSelected()) status = "In Progress";
            else if (cbCompleted.isSelected()) status = "Completed";
            else if (cbNotCompleted.isSelected()) status = "Not Completed";

            if (jobName.isEmpty() || description.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isAdding) {
                String sql = "INSERT INTO Jobs (JobName, Description, EstimatedStartDate, EstimatedEndDate, Status) VALUES (?, ?, ?, ?, ?)";

                try (Connection conn = ConnectDatabase.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, jobName);
                    pstmt.setString(2, description);
                    pstmt.setString(3, startDate);
                    pstmt.setString(4, endDate);
                    pstmt.setString(5, status);
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Thêm công việc thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadJobs();

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi thêm dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } else if (isEditing) {
                String sql = "UPDATE Jobs SET JobName = ?, Description = ?, EstimatedStartDate = ?, EstimatedEndDate = ?, Status = ? WHERE JobID = ?";

                try (Connection conn = ConnectDatabase.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, jobName);
                    pstmt.setString(2, description);
                    pstmt.setString(3, startDate);
                    pstmt.setString(4, endDate);
                    pstmt.setString(5, status);
                    pstmt.setInt(6, Integer.parseInt(jobID));
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadJobs();

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            isAdding = false;
            isEditing = false;
            resetButtons();
        }

            private void resetJob() {
        txtJobID.setText("");
        txtJobName.setText("");
        txtDescription.setText("");
        txtEstimatedStartDate.setText("");
        txtEstimatedEndDate.setText("");
        btgStatus.clearSelection(); 
        }
            
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgStatus = new javax.swing.ButtonGroup();
        popupMenu1 = new java.awt.PopupMenu();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbJob = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        btnAddJob = new javax.swing.JToggleButton();
        btnDeleteJob = new javax.swing.JToggleButton();
        btnEditJob = new javax.swing.JToggleButton();
        btnResetJob = new javax.swing.JToggleButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtJobID = new javax.swing.JTextField();
        txtDescription = new javax.swing.JTextField();
        txtEstimatedStartDate = new javax.swing.JTextField();
        txtEstimatedEndDate = new javax.swing.JTextField();
        btnSaveJob = new javax.swing.JButton();
        txtJobName = new javax.swing.JTextField();
        cbNotStartedYet = new javax.swing.JCheckBox();
        cbInProgress = new javax.swing.JCheckBox();
        cbCompleted = new javax.swing.JCheckBox();
        cbNotCompleted = new javax.swing.JCheckBox();

        popupMenu1.setLabel("popupMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Jobs");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Job list :");

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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Function :");

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

        btnEditJob.setText("Edit");
        btnEditJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditJobActionPerformed(evt);
            }
        });

        btnResetJob.setText("Reset");
        btnResetJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetJobActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Data entry form :");

        jLabel5.setText("- JobID ");

        jLabel6.setText("- JobName");

        jLabel7.setText("- Description");

        jLabel8.setText("- EstimatedStartDate ");

        jLabel9.setText("- EstimatedEndDate ");

        jLabel10.setText("- Status ");

        txtDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescriptionActionPerformed(evt);
            }
        });

        btnSaveJob.setText("Save");
        btnSaveJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveJobActionPerformed(evt);
            }
        });

        btgStatus.add(cbNotStartedYet);
        cbNotStartedYet.setText("NotStartedYet");

        btgStatus.add(cbInProgress);
        cbInProgress.setText("InProgress");

        btgStatus.add(cbCompleted);
        cbCompleted.setText("Completed");

        btgStatus.add(cbNotCompleted);
        cbNotCompleted.setText("NotCompleted");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(370, 370, 370)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 15, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(txtEstimatedEndDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                                                                .addComponent(txtEstimatedStartDate))
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(cbNotStartedYet, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(cbInProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(cbCompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(cbNotCompleted)))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnSaveJob))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txtJobName, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGap(27, 27, 27))))
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
                                                .addComponent(txtJobID, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(14, 14, 14)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnAddJob)
                    .addComponent(btnEditJob)
                    .addComponent(btnDeleteJob)
                    .addComponent(btnResetJob))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtEstimatedStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtEstimatedEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(btnSaveJob)
                    .addComponent(cbNotStartedYet)
                    .addComponent(cbInProgress)
                    .addComponent(cbNotCompleted)
                    .addComponent(cbCompleted))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddJobActionPerformed
        // TODO add your handling code here:
        addJob();
        loadJobs(); 
    }//GEN-LAST:event_btnAddJobActionPerformed

    private void txtDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescriptionActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtDescriptionActionPerformed

    private void btnEditJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditJobActionPerformed
        // TODO add your handling code here:
        editJob();
        loadJobs(); 
    }//GEN-LAST:event_btnEditJobActionPerformed

    private void btnDeleteJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteJobActionPerformed
        // TODO add your handling code here:
        deleteJob();
        loadJobs();
    }//GEN-LAST:event_btnDeleteJobActionPerformed

    private void btnResetJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetJobActionPerformed
        // TODO add your handling code here:
        resetJob();
        
    }//GEN-LAST:event_btnResetJobActionPerformed

    private void btnSaveJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveJobActionPerformed
        // TODO add your handling code here:
        saveJob();
        loadJobs(); 
    }//GEN-LAST:event_btnSaveJobActionPerformed

    private void btnAddJobMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddJobMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddJobMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Job.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Job.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Job.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Job.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Job().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgStatus;
    private javax.swing.JToggleButton btnAddJob;
    private javax.swing.JToggleButton btnDeleteJob;
    private javax.swing.JToggleButton btnEditJob;
    private javax.swing.JToggleButton btnResetJob;
    private javax.swing.JButton btnSaveJob;
    private javax.swing.JCheckBox cbCompleted;
    private javax.swing.JCheckBox cbInProgress;
    private javax.swing.JCheckBox cbNotCompleted;
    private javax.swing.JCheckBox cbNotStartedYet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private java.awt.PopupMenu popupMenu1;
    private javax.swing.JTable tbJob;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtEstimatedEndDate;
    private javax.swing.JTextField txtEstimatedStartDate;
    private javax.swing.JTextField txtJobID;
    private javax.swing.JTextField txtJobName;
    // End of variables declaration//GEN-END:variables
}
