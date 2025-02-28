/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class AttendanceTracking extends javax.swing.JPanel {

    private boolean isEditMode = false;
    private int currentSalaryID = -1;
    private DefaultComboBoxModel<String> cbxPositionModel; 
    private DefaultComboBoxModel<String> cbxMonthModel;

    public AttendanceTracking() {
        initComponents();
        cbxPositionModel = new DefaultComboBoxModel<>(); 
        cbxPositionID.setModel(cbxPositionModel);      

        loadPositions();
        loadSalaries();
        setEditStatus(false);
        
        cbxMonthModel = new DefaultComboBoxModel<>(); 
        cbxMonth.setModel(cbxMonthModel);

        loadMonths(); 

        cbxMonth.addActionListener(e -> updateTotalSalary());
        txtWorkDays.addActionListener(e -> updateTotalSalary());
        txtYearsOfWork.addActionListener(e -> updateTotalSalary());

        cbxPositionID.addActionListener(e -> {
            updateDailyRate();
            updateTotalSalary();
        });

        txtWorkDays.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                updateTotalSalary();
            }
        });
        txtYearsOfWork.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                updateTotalSalary();
            }
        });

    }

    private void loadMonths() {
        cbxMonthModel.removeAllElements(); 
        for (int i = 1; i <= 12; i++) {
            cbxMonthModel.addElement(String.valueOf(i));
        }
    }
        
    private void loadPositions() {
        cbxPositionModel.removeAllElements();
        cbxPositionModel.addElement("Select Position");

        String sql = "SELECT PositionID, PositionName FROM Positions";

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String positionName = rs.getString("PositionName");
                cbxPositionModel.addElement(positionName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading positions from database!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
//            ConnectDatabase.closeConnection(connection);
        }
    }
 
private void showSelectedSalary(int row) {
    currentSalaryID = (int) tbSalary.getValueAt(row, 0); 

    String month = tbSalary.getValueAt(row, 1).toString();
    cbxMonth.setSelectedItem(month);

    String positionName = tbSalary.getValueAt(row, 4).toString(); 

    for (int i = 0; i < cbxPositionID.getItemCount(); i++) {
        Object item = cbxPositionID.getItemAt(i); 

        if (item.toString().equals(positionName)) { 
            cbxPositionID.setSelectedIndex(i);
            break;
        }
    }

    txtDailyRate.setText(tbSalary.getValueAt(row, 5).toString());
    txtWorkDays.setText(tbSalary.getValueAt(row, 6).toString());
    txtTotalSalary.setText(tbSalary.getValueAt(row, 7).toString());
    txtYearsOfWork.setText(tbSalary.getValueAt(row, 8).toString());

    setEditStatus(false);
}

    private static class ComboItem {
        private int id;
        private String name;

        public ComboItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name; 
        }
    }


    private void updateDailyRate(){
        if(cbxPositionID.getSelectedItem() instanceof ComboItem){
            ComboItem selectedPosition = (ComboItem) cbxPositionID.getSelectedItem();
            int positionID = selectedPosition.getId();
            String sql = "SELECT BaseSalary FROM Positions WHERE PositionID = ?";
            try (Connection conn = ConnectDatabase.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, positionID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if(rs.next()){
                        double baseSalary = rs.getDouble("BaseSalary");
                        txtDailyRate.setText(String.valueOf(baseSalary/22));
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            } finally{
            }
        }
    }

    private void loadSalaries() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"SalaryID", "Month", "Year", "PositionID", "PositionName", "DailyRate", "WorkDays", "TotalSalary", "YearsOfWork"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbSalary.setModel(model);

        String sql = """
                SELECT s.SalaryID, s.Month, s.Year, s.PositionID, p.PositionName, s.DailyRate, s.WorkDays, s.TotalSalary, s.YearsOfWork
                FROM Salaries s
                INNER JOIN Positions p ON s.PositionID = p.PositionID
                """;

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int salaryID = rs.getInt("SalaryID");
                String month = rs.getString("Month");
                int year = rs.getInt("Year");
                int positionID = rs.getInt("PositionID");
                String positionName = rs.getString("PositionName");
                double dailyRate = rs.getDouble("DailyRate");
                int workDays = rs.getInt("WorkDays");
                double totalSalary = rs.getDouble("TotalSalary");
                int yearsOfWork = rs.getInt("YearsOfWork");

                model.addRow(new Object[]{salaryID, month, year, positionID, positionName, dailyRate, workDays, totalSalary, yearsOfWork});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data from database!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
        }

        tbSalary.setDefaultEditor(Object.class, null);
        tbSalary.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tbSalary.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tbSalary.getSelectedRow() != -1) {
                showSelectedSalary(tbSalary.getSelectedRow());
            }
        });
    }


    private void setEditStatus(boolean editable) {
        isEditMode = editable;

        cbxMonth.setEnabled(editable);
        txtYear.setEnabled(editable);
        cbxPositionID.setEnabled(editable);
        txtDailyRate.setEnabled(editable);
        txtWorkDays.setEnabled(editable);
        txtYearsOfWork.setEnabled(editable);

        btnSave.setEnabled(editable);
        btnCancel.setEnabled(editable);

        btnAdd.setEnabled(!editable);
        btnEdit.setEnabled(!editable);
    }

    private void saveSalary() {
        String month = cbxMonth.getSelectedItem().toString();
        int year = Integer.parseInt(txtYear.getText());

        ComboItem selectedPosition = (ComboItem) cbxPositionID.getSelectedItem();
        int positionID = selectedPosition.getId();

        double dailyRate = Double.parseDouble(txtDailyRate.getText());
        int workDays = Integer.parseInt(txtWorkDays.getText());
        int yearsOfWork = Integer.parseInt(txtYearsOfWork.getText());

        if (month.isEmpty() || String.valueOf(year).isEmpty() || String.valueOf(positionID).isEmpty() || String.valueOf(dailyRate).isEmpty() || String.valueOf(workDays).isEmpty() || String.valueOf(yearsOfWork).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter complete information!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql;
        try (Connection conn = ConnectDatabase.getConnection()) {
            if (isEditMode) {
                sql = "UPDATE Salaries SET Month=?, Year=?, PositionID=?, DailyRate=?, WorkDays=?, YearsOfWork=? WHERE SalaryID=?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, month);
                    pstmt.setInt(2, year);
                    pstmt.setInt(3, positionID);
                    pstmt.setDouble(4, dailyRate);
                    pstmt.setInt(5, workDays);
                    pstmt.setInt(6, yearsOfWork);
                    pstmt.setInt(7, currentSalaryID);

                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Salary updated successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                        loadSalaries(); 
                    }
                }
            } else {
                sql = "INSERT INTO Salaries (Month, Year, PositionID, DailyRate, WorkDays, YearsOfWork) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, month);
                    pstmt.setInt(2, year);
                    pstmt.setInt(3, positionID);
                    pstmt.setDouble(4, dailyRate);
                    pstmt.setInt(5, workDays);
                    pstmt.setInt(6, yearsOfWork);

                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "New salary added successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                currentSalaryID = generatedKeys.getInt(1); 
                            }
                        }
                        loadSalaries(); 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Error saving data!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            setEditStatus(false); 
        }
    }

    private void cancelEdit() {
        setEditStatus(false);
        tbSalary.clearSelection();
    }

    private void addNewSalary() {
        setEditStatus(true);
        clearInputFields();
        currentSalaryID = -1; 
        cbxMonth.setSelectedIndex(0);
        cbxPositionID.setSelectedIndex(0);

    }

    private void editSalary() {
        int selectedRow = tbSalary.getSelectedRow();
        if (selectedRow != -1) {
            setEditStatus(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a salary record to edit!", "Notification", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearInputFields() {
        txtYear.setText("");
        txtDailyRate.setText("");
        txtWorkDays.setText("");
        txtTotalSalary.setText("");
        txtYearsOfWork.setText("");
    }

    private void updateTotalSalary() {
        try {
            double dailyRate = Double.parseDouble(txtDailyRate.getText());
            int workDays = Integer.parseInt(txtWorkDays.getText());
            txtTotalSalary.setText(String.valueOf(dailyRate * workDays));
        } catch (NumberFormatException ex) {
            txtTotalSalary.setText("0");
        }
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
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSalary = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxMonth = new javax.swing.JComboBox<>();
        txtYear = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxPositionID = new javax.swing.JComboBox<>();
        txtDailyRate = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtWorkDays = new javax.swing.JTextField();
        txtTotalSalary = new javax.swing.JTextField();
        txtYearsOfWork = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Attendance Tracking by Month");

        tbSalary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Month", "Year", "Position ID", "Daily Rate", "Work Days", "Years Of Work", "Total Salary"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbSalary);

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel2.setText("Month :");

        cbxMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Year :");

        jLabel5.setText("Position ID :");

        cbxPositionID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Daily Rate :");

        jLabel7.setText("Work Days :");

        jLabel9.setText("Years of work :");

        jLabel8.setText("Total Salary :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtYearsOfWork, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDailyRate, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbxPositionID, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTotalSalary))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtWorkDays, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(26, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbxPositionID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDailyRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtWorkDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtYearsOfWork, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTotalSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Function :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Tracking list:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addGap(6, 6, 6))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(90, 90, 90)
                                .addComponent(btnAdd)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 553, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addGap(12, 12, 12)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        setEditStatus(true);
        clearInputFields();
        currentSalaryID = -1;
        cbxMonth.setSelectedIndex(0);
        cbxPositionID.setSelectedIndex(0);
        txtTotalSalary.setText("0");
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int selectedRow = tbSalary.getSelectedRow();
        if (selectedRow != -1) {
            setEditStatus(true);
        } else {
            JOptionPane.showMessageDialog(this,"Please select a salary record to edit!", "Notification", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
      String month = cbxMonth.getSelectedItem().toString(); 
    int year = Integer.parseInt(txtYear.getText()); 

    Object selectedItem = cbxPositionID.getSelectedItem();
    if (selectedItem == null) {
        JOptionPane.showMessageDialog(this, "Please select a position!", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int positionID;
    if (selectedItem instanceof ComboItem) {
        positionID = ((ComboItem) selectedItem).getId(); 
    } else {
        JOptionPane.showMessageDialog(this, "Invalid position data!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    double dailyRate = Double.parseDouble(txtDailyRate.getText());
    int workDays = Integer.parseInt(txtWorkDays.getText());
    int yearsOfWork = Integer.parseInt(txtYearsOfWork.getText());

    if (month.isEmpty() || year <= 0 || positionID <= 0 || dailyRate <= 0 || workDays <= 0 || yearsOfWork < 0) {
        JOptionPane.showMessageDialog(this, "Please enter valid information!", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String sql;
    try (Connection conn = ConnectDatabase.getConnection()) {
        if (isEditMode) {
            sql = "UPDATE Salaries SET Month=?, Year=?, PositionID=?, DailyRate=?, WorkDays=?, YearsOfWork=? WHERE SalaryID=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, month);
                pstmt.setInt(2, year);
                pstmt.setInt(3, positionID);
                pstmt.setDouble(4, dailyRate);
                pstmt.setInt(5, workDays);
                pstmt.setInt(6, yearsOfWork);
                pstmt.setInt(7, currentSalaryID);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Salary updated successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            sql = "INSERT INTO Salaries (Month, Year, PositionID, DailyRate, WorkDays, YearsOfWork) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, month);
                pstmt.setInt(2, year);
                pstmt.setInt(3, positionID);
                pstmt.setDouble(4, dailyRate);
                pstmt.setInt(5, workDays);
                pstmt.setInt(6, yearsOfWork);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "New salary added successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            currentSalaryID = generatedKeys.getInt(1);
                        }
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,"Error saving data!", "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        setEditStatus(false);
        loadSalaries();
        tbSalary.clearSelection();
    }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        setEditStatus(false);
        tbSalary.clearSelection();
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbxMonth;
    private javax.swing.JComboBox<String> cbxPositionID;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable tbSalary;
    private javax.swing.JTextField txtDailyRate;
    private javax.swing.JTextField txtTotalSalary;
    private javax.swing.JTextField txtWorkDays;
    private javax.swing.JTextField txtYear;
    private javax.swing.JTextField txtYearsOfWork;
    // End of variables declaration//GEN-END:variables
}
