/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import kase.aptechsaigon.projectsem2.controller.JobAssignment;
import kase.aptechsaigon.projectsem2.controller.JobValidation;

/**
 *
 * @author Moiiii
 */
public class Job extends javax.swing.JPanel {

    private boolean isCreate = true;
    private MainFrame mainFrame;
    private static Job instance;
    
    
    public static Job getInstance() {
        if (instance == null) {
            instance = new Job();
        }
        return instance;
    }

    public int getSelectedJobID() {
        // Trả về ID của công việc đã chọn (cần triển khai)
        return 0; 
    }


    

    /**
     * Creates new form Jobb
     */
    public Job() {
        
            initComponents();
            loadJobs();
            this.mainFrame = mainFrame;
            
        }
    
    private void openTaskPopup() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        JDialog taskDialog = new JDialog((Frame) parentWindow, "Task Manager", true);
        taskDialog.setSize(1400, 800);
        taskDialog.setLocationRelativeTo(this);
        taskDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        taskDialog.setContentPane(new Task()); 

        taskDialog.setVisible(true);
    }
        
        
    private JobAssignment jobAssignment = new JobAssignment();
    Set<String> teamNames = new HashSet<>();
    Set<String> assignedNames = new HashSet<>();



    public void loadJobs() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"JobID", "JobName", "Description", "EstimatedStartDate", "EstimatedEndDate", "Status", "TeamName"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbJob.setModel(model);

        String sql = """
            SELECT j.JobID, j.JobName, j.Description, j.EstimatedStartDate, j.EstimatedEndDate, j.Status,
                   a.TeamID, t.TeamName 
            FROM Jobs j
            LEFT JOIN Assignments a ON j.JobID = a.JobID
            LEFT JOIN Teams t ON a.TeamID = t.TeamID
            """;

            cbxTeamName.removeAllItems();
            cbxTeamName.addItem("Select Team");

        Connection conn = ConnectDatabase.getConnection();
        try {
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int jobId = rs.getInt("JobID");
                String jobName = rs.getString("JobName");
                String description = rs.getString("Description");
                Date startDate = rs.getDate("EstimatedStartDate");
                Date endDate = rs.getDate("EstimatedEndDate");
                String status = rs.getString("Status");
                String teamName = rs.getString("TeamName");

                model.addRow(new Object[]{jobId, jobName, description, startDate, endDate, status, teamName});
                
                if (teamName != null && !teamNames.contains(teamName)) {
                    teamNames.add(teamName);
                }
            }

            cbxTeamName.removeAllItems();
            cbxTeamName.addItem("Select Team");
            for (String name : teamNames) {
                cbxTeamName.addItem(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data from database!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectDatabase.closeConnection(conn);
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
    }

    private void showSelectedJob(int row) {
        txtJobName.setText(tbJob.getValueAt(row, 1).toString());
        txtDescription.setText(tbJob.getValueAt(row, 2).toString());

        Object startDateObj = tbJob.getValueAt(row, 3);
        jcdEstimatedStartDate.setDate(startDateObj != null ? (java.util.Date) startDateObj : null);

        Object endDateObj = tbJob.getValueAt(row, 4);
        jcdEstimatedEndDate.setDate(endDateObj != null ? (java.util.Date) endDateObj : null);

        String teamName = (String) tbJob.getValueAt(row, 6);
        cbxTeamName.setSelectedItem(teamName != null ? teamName : "Select Team");


        setEditStatus(false);
        cbxTeamName.setEnabled(false);

    }


    private boolean isEditMode = false; 

    public void setEditStatus(boolean editable) {
        isEditMode = editable;

        txtJobName.setEnabled(editable);
        txtDescription.setEnabled(editable);
        jcdEstimatedStartDate.setEnabled(editable);
        jcdEstimatedEndDate.setEnabled(editable);
        cbxTeamName.setEditable(editable);

        
        btnSaveJob.setEnabled(editable);
        btnCancelJob.setEnabled(editable);
        btnResetJob.setEnabled(editable);
                
        btnTaskManager.setEnabled(!editable);
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
    int selectedRow = tbJob.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a job to delete!", "Notification", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!btnDeleteJob.isEnabled()) {
        resetButtons();
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this job?", "Confirm", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        String jobID = tbJob.getValueAt(selectedRow, 0).toString();
        Connection conn = ConnectDatabase.getConnection();
        PreparedStatement pstmt = null;

        try {
            
            conn.setAutoCommit(false);

            String sqlDeleteAssignments = "DELETE FROM Assignments WHERE JobID = ?";
            pstmt = conn.prepareStatement(sqlDeleteAssignments);
            pstmt.setInt(1, Integer.parseInt(jobID));
            pstmt.executeUpdate();

            String sqlDeleteJob = "DELETE FROM Jobs WHERE JobID = ?";
            pstmt = conn.prepareStatement(sqlDeleteJob);
            pstmt.setInt(1, Integer.parseInt(jobID));
            pstmt.executeUpdate();

            conn.commit(); 
            JOptionPane.showMessageDialog(this, "Deletion successful!", "Notification", JOptionPane.INFORMATION_MESSAGE);

            loadJobs(); 

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting data!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    ConnectDatabase.closeConnection(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    private void cancelJob() {
        setEditStatus(false);
    }

    private void saveJob() {
        int selectedRow = tbJob.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a job to save!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object jobIdObj = tbJob.getValueAt(selectedRow, 0);
        String jobName = txtJobName.getText().trim();
        String description = txtDescription.getText().trim();
        java.util.Date startDate = jcdEstimatedStartDate.getDate();
        java.util.Date endDate = jcdEstimatedEndDate.getDate();
        String teamName = (String) cbxTeamName.getSelectedItem();

        if (jobName.isEmpty() || description.isEmpty() || startDate == null || endDate == null || teamName == null) {
            JOptionPane.showMessageDialog(this, "Please enter complete information!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
        Connection conn = ConnectDatabase.getConnection();
        try  {
            if (jobIdObj == null) { 
                String insertSQL = "INSERT INTO Jobs (JobName, Description, EstimatedStartDate, EstimatedEndDate, AssignedTo, TeamName, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, jobName);
                    pstmt.setString(2, description);
                    pstmt.setDate(3, sqlStartDate);
                    pstmt.setDate(4, sqlEndDate);
                    pstmt.setString(5, teamName);
                    pstmt.setString(6, "New");

                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        try (ResultSet rs = pstmt.getGeneratedKeys()) {
                            if (rs.next()) {
                                int newJobId = rs.getInt(1);
                                tbJob.setValueAt(newJobId, selectedRow, 0);
                            }
                        }
                        JOptionPane.showMessageDialog(this,"New job added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else { 
                int jobId = (int) jobIdObj;
                String updateSQL = "UPDATE Jobs SET JobName=?, Description=?, EstimatedStartDate=?, EstimatedEndDate=?, AssignedTo=?, TeamName=? WHERE JobID=?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                    pstmt.setString(1, jobName);
                    pstmt.setString(2, description);
                    pstmt.setDate(3, sqlStartDate);
                    pstmt.setDate(4, sqlEndDate);
                    pstmt.setString(5, teamName);
                    pstmt.setInt(6, jobId);

                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Job update successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }

            tbJob.setValueAt(jobName, selectedRow, 1);
            tbJob.setValueAt(description, selectedRow, 2);
            tbJob.setValueAt(sqlStartDate, selectedRow, 3);
            tbJob.setValueAt(sqlEndDate, selectedRow, 4);
            tbJob.setValueAt(teamName, selectedRow, 5);
            setEditStatus(false);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving work!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally{
            ConnectDatabase.closeConnection(conn);
        }
    }

    private void resetJob() {
        txtJobName.setText("");
        txtDescription.setText("");
        jcdEstimatedStartDate.setDate(null);
        jcdEstimatedEndDate.setDate(null);
        cbxTeamName.setSelectedIndex(-1);
    }

    public int getTeamIDByName(String teamName) {
        int teamID = -1;
        String sql = "SELECT TeamID FROM Teams WHERE TeamName = ?";

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            stmt.setString(1, teamName);

            if (rs.next()) {
                teamID = rs.getInt("TeamID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamID;
    }


    public void addAssignment(int jobID, String teamName) {
        int teamID = getTeamIDByName(teamName);

        if (teamID == -1) {
            System.out.println("No TeamID found for TeamName: " + teamName);
            return;
        }

        String sql = "INSERT INTO Assignments (JobID, TeamID) VALUES (?, ?)";

        try (Connection conn = ConnectDatabase.getConnection();  
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jobID);
            stmt.setInt(2, teamID);
            stmt.executeUpdate();
            System.out.println("Add assignment successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    private String getTeamNameByJobId(int jobId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String teamName = "";
        try {
            conn = ConnectDatabase.getConnection();
            String query = "SELECT t.TeamName FROM Teams t INNER JOIN Assignments a ON t.TeamID = a.TeamID WHERE a.JobID = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                teamName = rs.getString("TeamName");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return teamName;
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
        tbJob = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnAddJob = new javax.swing.JToggleButton();
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
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jLabel5 = new javax.swing.JLabel();
        cbxTeamName = new javax.swing.JComboBox<>();
        btnTaskManager = new javax.swing.JButton();

        tbJob.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "JobName", "Description", "EstimatedStartDate", "EstimatedEndDate", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbJob);
        if (tbJob.getColumnModel().getColumnCount() > 0) {
            tbJob.getColumnModel().getColumn(3).setHeaderValue("EstimatedEndDate");
            tbJob.getColumnModel().getColumn(4).setHeaderValue("Status");
        }

        jLabel6.setText("- JobName");

        jLabel7.setText("- Description");

        jLabel8.setText("- EstimatedStartDate ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Function :");

        jLabel9.setText("- EstimatedEndDate ");

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

        jLabel5.setText("- TeamName : ");

        cbxTeamName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTeamName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTeamNameActionPerformed(evt);
            }
        });

        btnTaskManager.setText("Task Management");
        btnTaskManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaskManagerActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 1032, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 136, Short.MAX_VALUE))
                            .addComponent(jSeparator5))
                        .addContainerGap())))
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(370, 370, 370)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEditJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDeleteJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnTaskManager))
                                            .addComponent(txtJobName, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jcdEstimatedEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcdEstimatedStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addComponent(cbxTeamName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btnSaveJob)
                                .addGap(18, 18, 18)
                                .addComponent(btnResetJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelJob)
                                .addGap(76, 76, 76)))))
                .addGap(0, 0, Short.MAX_VALUE))
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
                                        .addComponent(btnDeleteJob)
                                        .addComponent(btnTaskManager))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtJobName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jcdEstimatedStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jcdEstimatedEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbxTeamName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSaveJob)
                            .addComponent(btnResetJob)
                            .addComponent(btnCancelJob))))
                .addContainerGap(136, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddJobActionPerformed
        // TODO add your handling code here:
    txtJobName.setText("");
    txtDescription.setText("");
    jcdEstimatedStartDate.setDate(null);
    jcdEstimatedEndDate.setDate(null);
    cbxTeamName.setEnabled(true);
    setEditStatus(true);
    isEditMode = true;

    for (ActionListener al : btnSaveJob.getActionListeners()) {
        btnSaveJob.removeActionListener(al);
    }

    btnSaveJob.addActionListener(e -> {
        String jobName = txtJobName.getText().trim();
        String description = txtDescription.getText().trim();
        java.util.Date startDate = jcdEstimatedStartDate.getDate();
        java.util.Date endDate = jcdEstimatedEndDate.getDate();
        String selectedTeamName = (String) cbxTeamName.getSelectedItem();

        if (jobName.isEmpty() || description.isEmpty() || startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(null, "Please enter complete information!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JobValidation jobValidation = new JobValidation();
        if (!jobValidation.checkDate(startDate, endDate)) {
            JOptionPane.showMessageDialog(null, "Invalid date!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
        
        if (selectedTeamName.equals("Select Team")) {
            JOptionPane.showMessageDialog(null, "Please select a group!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = ConnectDatabase.getConnection();
        try {
            
            conn.setAutoCommit(false); 

            String insertJobSQL = "INSERT INTO Jobs (JobName, Description, EstimatedStartDate, EstimatedEndDate, Status) VALUES (?, ?, ?, ?, 'NotStartedYet')";
            int jobId = -1; 

            try (PreparedStatement pstmtJob = conn.prepareStatement(insertJobSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmtJob.setString(1, jobName);
                pstmtJob.setString(2, description);
                pstmtJob.setDate(3, sqlStartDate);
                pstmtJob.setDate(4, sqlEndDate);
                pstmtJob.executeUpdate();

                ResultSet generatedKeys = pstmtJob.getGeneratedKeys();
                if (generatedKeys.next()) {
                    jobId = generatedKeys.getInt(1);
                }
            }

            if (jobId == -1) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Error getting JobID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String getTeamIdSQL = "SELECT TeamID FROM Teams WHERE TeamName = ?";
            int teamId = -1;
            try (PreparedStatement pstmtTeam = conn.prepareStatement(getTeamIdSQL)) {
                pstmtTeam.setString(1, selectedTeamName);
                ResultSet rs = pstmtTeam.executeQuery();
                if (rs.next()) {
                    teamId = rs.getInt("TeamID");
                }
            }

            if (teamId == -1) {
                conn.rollback();
                JOptionPane.showMessageDialog(null,"Error getting TeamID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String insertAssignmentSQL = "INSERT INTO Assignments (JobID, TeamID, ActualStartDate, ActualEndDate) VALUES (?, ?, NULL, NULL)";
            try (PreparedStatement pstmtAssignment = conn.prepareStatement(insertAssignmentSQL)) {
                pstmtAssignment.setInt(1, jobId);
                pstmtAssignment.setInt(2, teamId);
                pstmtAssignment.executeUpdate();
            }

            conn.commit();
            JOptionPane.showMessageDialog(null, "Added job successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadJobs();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding job!", "Error", JOptionPane.ERROR_MESSAGE);
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    ConnectDatabase.closeConnection(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    });
    }//GEN-LAST:event_btnAddJobActionPerformed

    private void btnDeleteJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteJobActionPerformed
        // TODO add your handling code here:
    // Remove old action listeners
    for (ActionListener al : btnDeleteJob.getActionListeners()) {
        btnDeleteJob.removeActionListener(al);
    }

    // Add new action listener
    btnDeleteJob.addActionListener(e -> {
        int selectedRow = tbJob.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a job to delete!", "Notification", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int jobId = (int) tbJob.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this job?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = ConnectDatabase.getConnection();
            try {
                conn.setAutoCommit(false); 

                try (PreparedStatement pstmtAssignments = conn.prepareStatement("DELETE FROM Assignments WHERE JobID = ?")) {
                    pstmtAssignments.setInt(1, jobId);
                    pstmtAssignments.executeUpdate();
                }

                try (PreparedStatement pstmtJobs = conn.prepareStatement("DELETE FROM Jobs WHERE JobID = ?")) {
                    pstmtJobs.setInt(1, jobId);
                    int affectedRows = pstmtJobs.executeUpdate();

                    if (affectedRows > 0) {
                        conn.commit(); 
                        JOptionPane.showMessageDialog(this, "Job deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadJobs(); 
                    } else {
                        conn.rollback(); 
                        JOptionPane.showMessageDialog(this, "Cannot delete job!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (SQLException ex) {
                try {
                    conn.rollback(); 
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting job: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    conn.setAutoCommit(true); 
                    ConnectDatabase.closeConnection(conn); 
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    });
    }//GEN-LAST:event_btnDeleteJobActionPerformed

    private void txtDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescriptionActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDescriptionActionPerformed

    private void btnEditJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditJobActionPerformed
        // TODO add your handling code here:
    int selectedRow = tbJob.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a job to edit!", "Notification", JOptionPane.WARNING_MESSAGE);
        return;
    }

    showSelectedJob(selectedRow);

    int jobId = (int) tbJob.getValueAt(selectedRow, 0);
    String currentTeamName = getTeamNameByJobId(jobId);
    cbxTeamName.setSelectedItem(currentTeamName);

    setEditStatus(true);
    cbxTeamName.setEnabled(true);
    isEditMode = true;
    
    btnResetJob.setEnabled(false); 

    for (ActionListener al : btnSaveJob.getActionListeners()) {
        btnSaveJob.removeActionListener(al);
    }

    btnSaveJob.addActionListener(e -> {
        String jobName = txtJobName.getText().trim();
        String description = txtDescription.getText().trim();
        java.util.Date startDate = jcdEstimatedStartDate.getDate();
        java.util.Date endDate = jcdEstimatedEndDate.getDate();
        String newTeamName = (String) cbxTeamName.getSelectedItem();

        if (jobName.isEmpty() || description.isEmpty() || startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Please enter complete information!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
        
        JobValidation jobValidation = new JobValidation();
        boolean isValidDate = jobValidation.checkDate(startDate, endDate);
        
        if (!isValidDate) {
            JOptionPane.showMessageDialog(this, "Invalid date! Start date must be greater than current date and end date must be greater than start date.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = ConnectDatabase.getConnection();

        PreparedStatement pstmt = null;
        try {
            if (conn == null) {
                JOptionPane.showMessageDialog(this,"Unable to connect to database!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            conn.setAutoCommit(false);
            
            String updateJobSQL = "UPDATE Jobs SET JobName=?, Description=?, EstimatedStartDate=?, EstimatedEndDate=? WHERE JobID=?";
            pstmt = conn.prepareStatement(updateJobSQL);
            pstmt.setString(1, jobName);
            pstmt.setString(2, description);
            pstmt.setDate(3, sqlStartDate);
            pstmt.setDate(4, sqlEndDate);
            pstmt.setInt(5, jobId);
            pstmt.executeUpdate();
            pstmt.close();
            
            if (!currentTeamName.equals(newTeamName)) {
                String updateAssignmentSQL = "UPDATE Assignments SET TeamID = (SELECT TeamID FROM Teams WHERE TeamName = ?) WHERE JobID = ?";
                pstmt = conn.prepareStatement(updateAssignmentSQL);
                pstmt.setString(1, newTeamName);
                pstmt.setInt(2, jobId);
                pstmt.executeUpdate();
            }
            
            conn.commit();
            JOptionPane.showMessageDialog(this, "Job update successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadJobs();
        } catch (SQLException ex) {
            try { if (conn != null) conn.rollback(); } catch (SQLException e1) { e1.printStackTrace(); }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating job!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    });
    }//GEN-LAST:event_btnEditJobActionPerformed

    private void btnSaveJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveJobActionPerformed
        // TODO add your handling code here:
    btnSaveJob.setEnabled(false);

    }//GEN-LAST:event_btnSaveJobActionPerformed

    private void btnResetJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetJobActionPerformed
        resetJob();

    }//GEN-LAST:event_btnResetJobActionPerformed

    private void btnCancelJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelJobActionPerformed
        // TODO add your handling code here:
    btnCancelJob.setEnabled(false);

    cancelJob();

    loadJobs();

    btnCancelJob.setEnabled(false);
    }//GEN-LAST:event_btnCancelJobActionPerformed

    private void btnTaskManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaskManagerActionPerformed
        // TODO add your handling code here:
        openTaskPopup();
    }//GEN-LAST:event_btnTaskManagerActionPerformed

    private void cbxTeamNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTeamNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTeamNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAddJob;
    private javax.swing.JButton btnCancelJob;
    private javax.swing.JToggleButton btnDeleteJob;
    private javax.swing.JToggleButton btnEditJob;
    private javax.swing.JToggleButton btnResetJob;
    private javax.swing.JButton btnSaveJob;
    private javax.swing.JButton btnTaskManager;
    private javax.swing.JComboBox<String> cbxTeamName;
    private javax.swing.Box.Filler filler1;
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
    private javax.swing.JTextField txtJobName;
    // End of variables declaration//GEN-END:variables
}
