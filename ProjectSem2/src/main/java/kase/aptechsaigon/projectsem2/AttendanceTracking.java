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


public class AttendanceTracking extends javax.swing.JPanel {

    private boolean isEditMode = false;
    private int currentSalaryID = -1; // Lưu SalaryID của bản ghi đang được chọn

    public AttendanceTracking() {
        initComponents();
        loadSalaries();
        loadPositions(); // Load dữ liệu cho combobox PositionID
        setEditStatus(false); // Khởi tạo trạng thái không chỉnh sửa
        cbxMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTotalSalary();
            }
        });

        txtWorkDays.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTotalSalary();
            }
        });

        cbxPositionID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDailyRate();
                updateTotalSalary();
            }
        });

        txtYearsOfWork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTotalSalary();
            }
        });
        txtWorkDays.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                updateTotalSalary();
            }
        });
        txtYearsOfWork.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                updateTotalSalary();
            }
        });

    }

      private void loadPositions() {
        cbxPositionID.removeAllItems();
//        cbxPositionID.addItem(new ComboItem(-1, "Select Position"));

        String sql = "SELECT PositionID, PositionName FROM Positions";

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int positionID = rs.getInt("PositionID");
                String positionName = rs.getString("PositionName");
//                cbxPositionID.addItem(new ComboItem(positionID, positionName));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu chức vụ từ database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSelectedSalary(int row) {
        currentSalaryID = (int) tbSalary.getValueAt(row, 0);
        cbxMonth.setSelectedItem(tbSalary.getValueAt(row, 1).toString()); // Correct: setSelectedItem with String

        int positionID = (int) tbSalary.getValueAt(row, 3);

//        for (int i = 0; i < cbxPositionID.getItemCount(); i++) {
//            ComboItem item = cbxPositionID.getItemAt(i);
//            if (item.getId() == positionID) {
//                cbxPositionID.setSelectedItem(item);
//                break;
//            }
//        }

        txtDailyRate.setText(tbSalary.getValueAt(row, 5).toString());
        txtWorkDays.setText(tbSalary.getValueAt(row, 6).toString());
        txtTotalSalary.setText(tbSalary.getValueAt(row, 7).toString());
        txtYearsOfWork.setText(tbSalary.getValueAt(row, 8).toString());

        setEditStatus(false);
    }


    private class ComboItem {
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
            return name; // Trả về positionName
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
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
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
                        JOptionPane.showMessageDialog(this, "Cập nhật lương thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        loadSalaries(); // Tải lại dữ liệu sau khi cập nhật
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
                        JOptionPane.showMessageDialog(this, "Thêm mới lương thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                currentSalaryID = generatedKeys.getInt(1); // Lấy SalaryID mới
                            }
                        }
                        loadSalaries(); // Tải lại dữ liệu sau khi thêm mới
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            setEditStatus(false); // Chuyển về trạng thái không chỉnh sửa sau khi lưu
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
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bản ghi lương để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
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
            txtTotalSalary.setText("0"); // Handle invalid input
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

        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxMonth = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtYear = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbxPositionID = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSalary = new javax.swing.JTable();
        txtTotalSalary = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtYearsOfWork = new javax.swing.JTextField();
        txtWorkDays = new javax.swing.JTextField();
        txtDailyRate = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        jLabel5.setText("Position ID :");

        jLabel6.setText("Daily Rate :");

        cbxMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Work Days :");

        jLabel8.setText("Total Salary :");

        cbxPositionID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        jLabel2.setText("Month :");

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Attendance Tracking by Month");

        jLabel4.setText("Year :");

        jLabel9.setText("Years of work :");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                        .addComponent(txtYearsOfWork, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(91, 91, 91)
                                        .addComponent(jLabel3))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTotalSalary))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(cbxMonth, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtYear, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                            .addComponent(cbxPositionID, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtWorkDays, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDailyRate, javax.swing.GroupLayout.Alignment.LEADING))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(btnSave)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCancel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(74, 74, 74)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnAdd)
                                            .addComponent(btnEdit))))
                                .addGap(0, 280, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(313, 313, 313)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxPositionID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(btnAdd))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtDailyRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtWorkDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotalSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtYearsOfWork, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnCancel))))
                .addGap(12, 12, 12)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
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
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bản ghi lương để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String month = cbxMonth.getSelectedItem().toString();
        int year = Integer.parseInt(txtYear.getText());

        if (!(cbxPositionID.getSelectedItem() instanceof ComboItem)) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ComboItem selectedPosition = (ComboItem) cbxPositionID.getSelectedItem();
        int positionID = selectedPosition.getId();

        double dailyRate = Double.parseDouble(txtDailyRate.getText());
        int workDays = Integer.parseInt(txtWorkDays.getText());
        int yearsOfWork = Integer.parseInt(txtYearsOfWork.getText());

        if (month.isEmpty() || String.valueOf(year).isEmpty() || String.valueOf(positionID).isEmpty() || String.valueOf(dailyRate).isEmpty() || String.valueOf(workDays).isEmpty() || String.valueOf(yearsOfWork).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
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
                        JOptionPane.showMessageDialog(this, "Cập nhật lương thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
                        JOptionPane.showMessageDialog(this, "Thêm mới lương thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable tbSalary;
    private javax.swing.JTextField txtDailyRate;
    private javax.swing.JTextField txtTotalSalary;
    private javax.swing.JTextField txtWorkDays;
    private javax.swing.JTextField txtYear;
    private javax.swing.JTextField txtYearsOfWork;
    // End of variables declaration//GEN-END:variables
}
