/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import javax.swing.ButtonGroup;

/**
 *
 * @author Moiiii
 */
public class Position extends javax.swing.JPanel {
    private ButtonGroup buttonGroupBaseSalary;
    /**
     * Creates new form Jobb
     */
    public Position() {
        initComponents();
        modifyJDateChoosers();
        loadPositions(); 
    }
    private void modifyJDateChoosers() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jdCreatedAt.setDateFormatString(sdf.toPattern());
        jdUpdatedAt.setDateFormatString(sdf.toPattern());
    }

    public void loadPositions() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng theo DB
        
        DefaultTableModel model = new DefaultTableModel(new Object[]{"PositionID", "PositionName", "RequiredExperienceYears",
                                                                     "BaseSalary", "MaxOvertimeHours", "BonusRate",
                                                                     "Responsibilities", "CreatedAt", "UpdatedAt" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbPosition.setModel(model);

        String sql = "SELECT PositionID, PositionName, RequiredExperienceYears, "
                + "BaseSalary, MaxOvertimeHours, BonusRate, "
                + "Responsibilities, CreatedAt, UpdatedAt FROM Positions";
        Connection conn = ConnectDatabase.getConnection();
        try (
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("PositionID");
                String PositionName = rs.getString("PositionName");
                String rEY = rs.getString("RequiredExperienceYears");
                String BaseSalary = rs.getString("BaseSalary");               
                String maxOT = rs.getString("MaxOvertimeHours");
                String BonusRate = rs.getString("BonusRate");
                String Responsibilities = rs.getString("Responsibilities");
                String CreatedAt = rs.getString("CreatedAt");
                String UpdatedAt = rs.getString("UpdatedAt");
                model.addRow(new Object[]{id, PositionName, rEY, BaseSalary, maxOT, BonusRate, Responsibilities, CreatedAt, UpdatedAt});
            }
            
            tbPosition.getColumnModel().getColumn(0).setCellEditor(null);


        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loi khi tai du lieu tu database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectDatabase.closeConnection(conn);
        }

        tbPosition.setDefaultEditor(Object.class, null);
        tbPosition.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tbPosition.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tbPosition.getSelectedRow() != -1) {
                int selectedRow = tbPosition.getSelectedRow();

                txtPositionID.setText(tbPosition.getValueAt(selectedRow, 0).toString());
                txtReqExpYears.setText(tbPosition.getValueAt(selectedRow, 1).toString());
                txtBonusRate.setText(tbPosition.getValueAt(selectedRow, 6).toString());

            }
        });
        // Nếu có dữ liệu, chọn dòng đầu tiên
    if (tbPosition.getRowCount() > 0) {
        tbPosition.setRowSelectionInterval(0, 0);
        showSelectedStaff(0);
    }

    tbPosition.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting() && tbPosition.getSelectedRow() != -1) {
            showSelectedStaff(tbPosition.getSelectedRow());
        }
    });
        
        setStaffFieldsEditable(false);
    } 
    
    // Hàm hiển thị chi tiết Staff khi chọn một dòng trên bảng:
    private void showSelectedStaff(int row) {
    
        txtPositionID.setText(tbPosition.getValueAt(row, 0).toString());   
        txtPositionName.setText(tbPosition.getValueAt(row, 1).toString());
        txtReqExpYears.setText(tbPosition.getValueAt(row, 2).toString()); 
        txtBaseSalary.setText(tbPosition.getValueAt(row, 3).toString());
        txtMaxOT.setText(tbPosition.getValueAt(row, 4).toString());
        txtBonusRate.setText(tbPosition.getValueAt(row, 5).toString());
        txtResponsibilities.setText(tbPosition.getValueAt(row, 6).toString());
              //Lấy dữ liệu ngày tháng
        Object createdAtObj = tbPosition.getValueAt(row, 7);
        if (createdAtObj != null) {
            String createdAtStr = createdAtObj.toString();           
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date createdAt = sdf.parse(createdAtStr);
                jdCreatedAt.setDate(createdAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } 
        //Xử lý ngày tháng
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Object updatedAtObj = tbPosition.getValueAt(row, 8);
        if (updatedAtObj != null) {
        String updatedAtStr = updatedAtObj.toString();        
        try {
            Date updatedAt = sdf.parse(updatedAtStr);
            jdUpdatedAt.setDate(updatedAt);            
            } catch (ParseException e) {
            e.printStackTrace();
            }      
        //setStaffFieldsEditable(true); 
        
        //Vô hiệu hóa các nút 
        txtPositionID.setEnabled(false);
        txtReqExpYears.setEnabled(false);
        txtBaseSalary.setEnabled(false);
        txtReqExpYears.setEnabled(false);
        txtMaxOT.setEnabled(false);
        txtBonusRate.setEnabled(false);
        txtResponsibilities.setEnabled(false);        
        jdUpdatedAt.setEnabled(false);        
        txtContractDuration.setEnabled(false);
 }
    }
    
    public void setStaffFieldsEditable(boolean editable) {
        txtReqExpYears.setEditable(editable);       
        txtBaseSalary.setEditable(editable);
        txtMaxOT.setEditable(editable);
        txtBonusRate.setEditable(editable);
        txtResponsibilities.setEditable(editable);
        txtContractDuration.setEditable(editable);
        txtReqExpYears.setEnabled(editable);
        jdUpdatedAt.setEnabled(editable);
    }
        private boolean isAdding = false;
        private boolean isEditing = false;

        private void resetButtons() {
            btnAddJob.setEnabled(true);
            btnEditJob.setEnabled(true);
            btnDeleteJob.setEnabled(true);
        }

        private void addEmployee() {
            if (isAdding) {
                isAdding = false;
                
            } else {
                isAdding = true;
                isEditing = false;

                btnEditJob.setEnabled(false);
                btnDeleteJob.setEnabled(false);
                txtPositionID.setText(""); 
                txtReqExpYears.setText("");
                txtBonusRate.setText("");}
        }

        private void editEmployee() {
            if (isEditing) {
                isEditing = false;
                
            } else {
                int selectedRow = tbPosition.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để chỉnh sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                isEditing = true;
                isAdding = false;
                showSelectedStaff(selectedRow);               
                txtReqExpYears.setEnabled(true);
                txtBaseSalary.setEnabled(true);                 
                txtMaxOT.setEnabled(true);
                txtBonusRate.setEnabled(true);
                txtResponsibilities.setEnabled(true); txtContractDuration.setEnabled(true);
                txtReqExpYears.setEnabled(true);
                jdUpdatedAt.setEnabled(true);
                btnAddJob.setEnabled(false);
                btnDeleteJob.setEnabled(false);
                setStaffFieldsEditable(true);
            }
        }
        
        private void cancelEdit() {
            //Đặt lại trạng thái ban đầu
            isEditing = false;
            isAdding = false;
            
            //Đưa về hàng đầu
             if (tbPosition.getRowCount() > 0) {
                tbPosition.setRowSelectionInterval(0, 0);
                showSelectedStaff(0);
                } else {
                    clearFields(); // Nếu không có dữ liệu, chỉ xóa input
            }
            btnAddJob.setEnabled(true);
            btnDeleteJob.setEnabled(true);
            setStaffFieldsEditable(false);
        }
        private void clearFields() {
            txtReqExpYears.setText("");
            txtBaseSalary.setText("");
            txtMaxOT.setText("");
            txtBonusRate.setText("");
            txtResponsibilities.setText("");
            txtContractDuration.setText("");
            txtReqExpYears.setText("");
            jdUpdatedAt.setDate(null);
            buttonGroupBaseSalary.clearSelection();
        }
        private void deleteEmployee() {
            int selectedRow = tbPosition.getSelectedRow();
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
                String PositionID = tbPosition.getValueAt(selectedRow, 0).toString();

                String sql = "DELETE FROM Employees WHERE PositionID = ?";
                try (Connection conn = ConnectDatabase.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, Integer.parseInt(PositionID));
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadPositions(); 

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void saveEmployee() {
            if (!isAdding && !isEditing) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chức năng Add hoặc Edit trước khi lưu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String positionID = txtPositionID.getText().trim();
            String PositionName = txtPositionName.getText().trim();
            String rEY = txtReqExpYears.getText().trim();
            String baseSalary = txtBaseSalary.getText().trim();            
            String maxOT = txtMaxOT.getText().trim();
            String bonusRate = txtBonusRate.getText().trim();
            String responsibilities = txtResponsibilities.getText().trim();
            
            Date utilCreatedAt = jdCreatedAt.getDate();
            Date utilUpdatedAt = jdUpdatedAt.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            /*if 
            (jobName.isEmpty() || description.isEmpty() || startDate == null || endDate == null) 
            { JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;}*/
            
            
            if (PositionName.isEmpty() || rEY.isEmpty() || baseSalary.isEmpty() ||
                maxOT.isEmpty() || bonusRate.isEmpty() || responsibilities.isEmpty()  
                || utilCreatedAt == null || utilUpdatedAt == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Chuyễn đổi từ java.util.Date sang java.sql.Date
            java.sql.Date createdAt = new java.sql.Date(utilCreatedAt.getTime());
            java.sql.Date updatedAt = new java.sql.Date(utilUpdatedAt.getTime());

            if (isAdding) {
                String sql = "INSERT INTO Positions "
                        + "(PositionName, RequiredExperienceYears, BaseSalary,"
                        + " MaxOvertimeHours, BonusRate, Responsibilities,"
                        + " CreatedAt, UpdatedAt)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (Connection conn = ConnectDatabase.getConnection();
//PreparedStatement pstmtJob = conn.prepareStatement(insertJobSQL, Statement.RETURN_GENERATED_KEYS)                        
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, PositionName);
                    pstmt.setString(2, rEY);
                    pstmt.setString(3, baseSalary);
                    pstmt.setString(4, maxOT);
                    pstmt.setString(5, bonusRate);
                    pstmt.setString(6, responsibilities);
                    pstmt.setDate(7, createdAt);
                    pstmt.setDate(8, updatedAt);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadPositions();

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi thêm dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } else if (isEditing) {
                String sql =  "UPDATE Positions SET "
                   + "PositionName = ?, RequiredExperienceYears = ?, BaseSalary = ?, "
                   + "MaxOvertimeHours = ?, BonusRate = ?, Responsibilities = ?, "
                   + "CreatedAt = ?, UpdatedAt = ? "
                   + "WHERE PositionID = ?";

                try (Connection conn = ConnectDatabase.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, PositionName);
                    pstmt.setString(2, rEY);
                    pstmt.setString(3, baseSalary);
                    pstmt.setString(4, maxOT);
                    pstmt.setString(5, bonusRate);
                    pstmt.setString(6, responsibilities);
                    pstmt.setDate(7, createdAt);
                    pstmt.setDate(8, updatedAt);
                    pstmt.setInt(9, Integer.parseInt(positionID));
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            isAdding = false;
            isEditing = false;
            resetButtons();
            loadPositions();
        }

        private void resetJob() {
            txtReqExpYears.setText("");
            txtBaseSalary.setText("");
            txtMaxOT.setText("");
            txtBonusRate.setText("");
            txtResponsibilities.setText("");
            txtContractDuration.setText("");
            txtReqExpYears.setText("");
            jdUpdatedAt.setDate(null);
            buttonGroupBaseSalary.clearSelection();
//        txtEstimatedUpdatedAt.setText("");
//        txtEstimatedEndDate.setText("");
        }
            
            //để a check lỗi này
            // cái này nãy em thêm đc á, emn làm cho khoan

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor. 
     */ 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPositionID = new javax.swing.JLabel();
        txtPositionID = new javax.swing.JTextField();
        jPositionName = new javax.swing.JLabel();
        txtPositionName = new javax.swing.JTextField();
        jReqExpYears = new javax.swing.JLabel();
        txtReqExpYears = new javax.swing.JTextField();
        jBaseSalary = new javax.swing.JLabel();
        txtBaseSalary = new javax.swing.JTextField();
        jMaxOT = new javax.swing.JLabel();
        txtMaxOT = new javax.swing.JTextField();
        jBonusRate = new javax.swing.JLabel();
        txtBonusRate = new javax.swing.JTextField();
        jResponsibilities = new javax.swing.JLabel();
        txtResponsibilities = new javax.swing.JTextField();
        jContractDuration = new javax.swing.JLabel();
        txtContractDuration = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPosition = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        btnAddJob = new javax.swing.JToggleButton();
        btnEditJob = new javax.swing.JToggleButton();
        btnDeleteJob = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jUpdatedAt = new javax.swing.JLabel();
        jdUpdatedAt = new com.toedter.calendar.JDateChooser();
        jCreatedAt = new javax.swing.JLabel();
        jdCreatedAt = new com.toedter.calendar.JDateChooser();
        btnSaveJob = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1000, 857));

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 300));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPositionID.setText("EmployeeID");
        jPanel1.add(jPositionID, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 9, 110, -1));

        txtPositionID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPositionIDActionPerformed(evt);
            }
        });
        jPanel1.add(txtPositionID, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 6, 369, -1));

        jPositionName.setText("PositionName");
        jPanel1.add(jPositionName, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 40, 110, -1));
        jPanel1.add(txtPositionName, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 37, 369, -1));

        jReqExpYears.setText("Require Exp. Years");
        jPanel1.add(jReqExpYears, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 71, 110, -1));
        jPanel1.add(txtReqExpYears, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 369, -1));

        jBaseSalary.setText("Base Salary");
        jPanel1.add(jBaseSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 110, -1));

        txtBaseSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBaseSalaryActionPerformed(evt);
            }
        });
        jPanel1.add(txtBaseSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 369, -1));

        jMaxOT.setText("Max Overtime Hours");
        jPanel1.add(jMaxOT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, -1));
        jPanel1.add(txtMaxOT, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 369, -1));

        jBonusRate.setText("Bonus Rate");
        jPanel1.add(jBonusRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 110, -1));
        jPanel1.add(txtBonusRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 369, -1));

        jResponsibilities.setText("Responsibilities");
        jPanel1.add(jResponsibilities, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 110, 20));
        jPanel1.add(txtResponsibilities, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 369, -1));

        jContractDuration.setText("Contract Duration");
        jPanel1.add(jContractDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 110, -1));

        txtContractDuration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContractDurationActionPerformed(evt);
            }
        });
        jPanel1.add(txtContractDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 369, -1));

        tbPosition.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PositionID", "PositionName", "ReqExpYears", "BaseSalary", "MaxOT", "BonusRate", "Responsibility", "CreatedAt", "UpdatedAt"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Float.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbPosition);

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Position");

        jUpdatedAt.setText("Updated At");

        jCreatedAt.setText("Created At");

        btnSaveJob.setText("Save");
        btnSaveJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveJobActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Position list:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jUpdatedAt)
                            .addComponent(jCreatedAt, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jdCreatedAt, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jdUpdatedAt, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 143, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSaveJob)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancel))))
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(79, 79, 79)
                                .addComponent(btnAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddJob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditJob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(btnDeleteJob)))
                .addGap(12, 12, 12)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jUpdatedAt, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdUpdatedAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCreatedAt)
                            .addComponent(jdCreatedAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSaveJob)
                            .addComponent(btnCancel))
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(51, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddJobMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddJobMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddJobMouseClicked

    private void btnAddJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddJobActionPerformed
        // TODO add your handling code here:
        addEmployee();
        loadPositions();
    }//GEN-LAST:event_btnAddJobActionPerformed

    private void btnDeleteJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteJobActionPerformed
        // TODO add your handling code here:
        deleteEmployee();
        loadPositions();
    }//GEN-LAST:event_btnDeleteJobActionPerformed

    private void btnEditJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditJobActionPerformed
      
        editEmployee();
        //loadStaffs();
    }//GEN-LAST:event_btnEditJobActionPerformed

    private void btnSaveJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveJobActionPerformed

        saveEmployee();
        loadPositions();
    }//GEN-LAST:event_btnSaveJobActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtBaseSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBaseSalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBaseSalaryActionPerformed

    private void txtPositionIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPositionIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPositionIDActionPerformed

    private void txtContractDurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContractDurationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContractDurationActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAddJob;
    private javax.swing.JButton btnCancel;
    private javax.swing.JToggleButton btnDeleteJob;
    private javax.swing.JToggleButton btnEditJob;
    private javax.swing.JButton btnSaveJob;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jBaseSalary;
    private javax.swing.JLabel jBonusRate;
    private javax.swing.JLabel jContractDuration;
    private javax.swing.JLabel jCreatedAt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jMaxOT;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jPositionID;
    private javax.swing.JLabel jPositionName;
    private javax.swing.JLabel jReqExpYears;
    private javax.swing.JLabel jResponsibilities;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jUpdatedAt;
    private com.toedter.calendar.JDateChooser jdCreatedAt;
    private com.toedter.calendar.JDateChooser jdUpdatedAt;
    private javax.swing.JTable tbPosition;
    private javax.swing.JTextField txtBaseSalary;
    private javax.swing.JTextField txtBonusRate;
    private javax.swing.JTextField txtContractDuration;
    private javax.swing.JTextField txtMaxOT;
    private javax.swing.JTextField txtPositionID;
    private javax.swing.JTextField txtPositionName;
    private javax.swing.JTextField txtReqExpYears;
    private javax.swing.JTextField txtResponsibilities;
    // End of variables declaration//GEN-END:variables
}
