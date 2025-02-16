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

/**
 *
 * @author Moiiii
 */
public class Employee extends javax.swing.JPanel {

    /**
     * Creates new form Jobb
     */
    public Employee() {
    
        initComponents();
        modifyJDateChoosers();
        loadStaffs(); 
    }
    private void modifyJDateChoosers() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jdBirthDate.setDateFormatString(sdf.toPattern());
        jdStartDate.setDateFormatString(sdf.toPattern());
    }

    public void loadStaffs() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng theo DB
        
        DefaultTableModel model = new DefaultTableModel(new Object[]{"EmployeeID", "FullName", "BirthDate", "Gender", "IDCardNumber", "Address", "Email", "PhoneNumber", "StartDate" ,"ContractDuration","WorkExperience"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbStaff.setModel(model);

        String sql = "SELECT EmployeeID, FullName, BirthDate, Gender, IDCardNumber, Address, Email, PhoneNumber, StartDate, ContractDuration, WorkExperience FROM Employees";

        try (Connection conn = ConnectDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("EmployeeID");
                String fullName = rs.getString("FullName");
                String BirthDate = rs.getString("BirthDate");
                String Gender = rs.getString("Gender");
                if (Gender == null) Gender = "other";
                String IDCardNumber = rs.getString("IDCardNumber");
                String Address = rs.getString("Address");
                String Email = rs.getString("Email");
                String PhoneNumber = rs.getString("PhoneNumber");
                String StartDate = rs.getString("StartDate");
                String ContractDuration = rs.getString("ContractDuration");
                String WorkExperience = rs.getString("WorkExperience");
                
                model.addRow(new Object[]{id, fullName, BirthDate, Gender, IDCardNumber, Address, Email, PhoneNumber, StartDate, ContractDuration, WorkExperience});
            }
            
            tbStaff.getColumnModel().getColumn(0).setCellEditor(null);


        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        tbStaff.setDefaultEditor(Object.class, null);
        tbStaff.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tbStaff.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tbStaff.getSelectedRow() != -1) {
                int selectedRow = tbStaff.getSelectedRow();

                txtEmployeeID.setText(tbStaff.getValueAt(selectedRow, 0).toString());
                txtFullName.setText(tbStaff.getValueAt(selectedRow, 1).toString());
                txtEmail.setText(tbStaff.getValueAt(selectedRow, 6).toString());
//                txtEstimatedStartDate.setText(tbJob.getValueAt(selectedRow, 3).toString());
//                txtEstimatedEndDate.setText(tbJob.getValueAt(selectedRow, 4).toString());
            }
        });
        // Nếu có dữ liệu, chọn dòng đầu tiên
    if (tbStaff.getRowCount() > 0) {
        tbStaff.setRowSelectionInterval(0, 0);
        showSelectedStaff(0);
    }

    tbStaff.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting() && tbStaff.getSelectedRow() != -1) {
            showSelectedStaff(tbStaff.getSelectedRow());
        }
    });
        
        setStaffFieldsEditable(false);
    } 
    
    // Hàm hiển thị chi tiết Staff khi chọn một dòng trên bảng:
    private void showSelectedStaff(int row) {
    
        txtEmployeeID.setText(tbStaff.getValueAt(row, 0).toString()); 
        txtFullName.setText(tbStaff.getValueAt(row, 1).toString());        
        //Lấy dữ liệu ngày tháng
        Object birthDateObj = tbStaff.getValueAt(row, 2);
        if (birthDateObj != null) {
            String birthDateStr = birthDateObj.toString();           
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date birthDate = sdf.parse(birthDateStr);
                jdBirthDate.setDate(birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //Gender - Row 3 
            //Reset tất cả các nút Gender rbtn
            rbtnMale.setSelected(false);
            rbtnFemale.setSelected(false);
            rbtnOther.setSelected(false);       
            //Ktra giá trị Gender
            Object genderObj = tbStaff.getValueAt(row, 3);
            String Gender = (genderObj != null) ? genderObj.toString().toLowerCase() : "other";
        
            if (Gender.equalsIgnoreCase("male")) {
                 rbtnMale.setSelected(true);
            } else if (Gender.equalsIgnoreCase("female")) {
                rbtnFemale.setSelected(true);
                } else {
                rbtnOther.setSelected(true);
                }
                
        txtIDCard.setText(tbStaff.getValueAt(row, 4).toString());
        txtAddress.setText(tbStaff.getValueAt(row, 5).toString());
        txtEmail.setText(tbStaff.getValueAt(row, 6).toString());
        txtPhoneNum.setText(tbStaff.getValueAt(row, 7).toString());
        
        //Xử lý ngày tháng
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Object startDateObj = tbStaff.getValueAt(row, 8);
        if (startDateObj != null) {
        String startDateStr = startDateObj.toString();        
        try {
            Date startDate = sdf.parse(startDateStr);
            jdStartDate.setDate(startDate);            
            } catch (ParseException e) {
            e.printStackTrace();
        }
        txtContractDuration.setText(tbStaff.getValueAt(row, 9).toString());
        txtWorkExp.setText(tbStaff.getValueAt(row, 10).toString());
        
        //setStaffFieldsEditable(true); 
        
        //Vô hiệu hóa các nút 
        txtEmployeeID.setEnabled(false);
        txtFullName.setEnabled(false);
        txtIDCard.setEnabled(false);
        jdBirthDate.setEnabled(false);
        txtAddress.setEnabled(false);
        txtEmail.setEnabled(false);
        txtPhoneNum.setEnabled(false);
        txtWorkExp.setEnabled(false);
        jdStartDate.setEnabled(false);
        rbtnMale.setEnabled(false);
        rbtnFemale.setEnabled(false);
        rbtnOther.setEnabled(false);
        
        }
    }
    
    public void setStaffFieldsEditable(boolean editable) {
        txtFullName.setEditable(editable);       
        txtIDCard.setEditable(editable);
        txtAddress.setEditable(editable);
        
        rbtnMale.setEnabled(editable);
        rbtnFemale.setEnabled(editable);
        rbtnOther.setEnabled(editable);
        
        txtEmail.setEditable(editable);
        txtPhoneNum.setEditable(editable);       
        txtWorkExp.setEditable(editable);
        txtContractDuration.setEditable(editable);
        
        jdBirthDate.setEnabled(editable);
        jdBirthDate.getDateEditor().setEnabled(editable);
        jdStartDate.setEnabled(editable);
        jdStartDate.getDateEditor().setEnabled(editable);
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
                resetButtons();
            } else {
                isAdding = true;
                isEditing = false;

                btnEditJob.setEnabled(false);
                btnDeleteJob.setEnabled(false);

                txtEmployeeID.setText(""); 
                txtFullName.setText("");
                txtEmail.setText("");
//                txtEstimatedStartDate.setText("");
//                txtEstimatedEndDate.setText("");
            }
        }

        private void editEmployee() {
            if (isEditing) {
                isEditing = false;
                resetButtons();
            } else {
                int selectedRow = tbStaff.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để chỉnh sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                isEditing = true;
                isAdding = false;
                showSelectedStaff(selectedRow);
                
                txtFullName.setEnabled(true);
                txtIDCard.setEnabled(true);                 
                txtAddress.setEnabled(true);
                txtEmail.setEnabled(true);
                txtPhoneNum.setEnabled(true);
                txtWorkExp.setEnabled(true);
                txtContractDuration.setEnabled(true);
                jdBirthDate.setEnabled(true);
                jdStartDate.setEnabled(true);
                rbtnMale.setEnabled(true);
                rbtnFemale.setEnabled(true);
                rbtnOther.setEnabled(true);
                
                //Tắt 2 nút add và delete
                btnAddJob.setEnabled(false);
                btnDeleteJob.setEnabled(false);
                
                //txtEmployeeID.setText(tbStaff.getValueAt(selectedRow, 0).toString());
                //txtFullName.setText(tbStaff.getValueAt(selectedRow, 1).toString());
                //txtEmail.setText(tbStaff.getValueAt(selectedRow, 6).toString());
//                txtEstimatedStartDate.setText(tbJob.getValueAt(selectedRow, 3).toString());
//                txtEstimatedEndDate.setText(tbJob.getValueAt(selectedRow, 4).toString());
                setStaffFieldsEditable(true);
            }
        }

        private void deleteEmployee() {
            int selectedRow = tbStaff.getSelectedRow();
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
                String EmployeeID = tbStaff.getValueAt(selectedRow, 0).toString();

                String sql = "DELETE FROM Employees WHERE EmployeeID = ?";
                try (Connection conn = ConnectDatabase.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, Integer.parseInt(EmployeeID));
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadStaffs(); 

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

            String employeeID = txtEmployeeID.getText().trim();
            String fullName = txtFullName.getText().trim();
            String idCard = txtIDCard.getText().trim();
            String address = txtAddress.getText().trim();            
            String email = jEmail.getText().trim();
            String phone = txtPhoneNum.getText().trim();
            String contractDuration = txtContractDuration.getText().trim();
            String workExperience = txtWorkExp.getText().trim();
            String gender = "other";
                if (rbtnMale.isSelected()) {
                    gender = "male";
                } else if (rbtnFemale.isSelected()) {
                    gender = "female";
                }
            Date birthDate = jdBirthDate.getDate();
            Date startDate = jdStartDate.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            
            if (fullName.isEmpty() || idCard.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty() || birthDate == null || startDate == null || contractDuration.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isAdding) {
                String sql = "INSERT INTO Employees "
                        + "(FullName, BirthDate, Gender,"
                        + " IDCardNumber, Address, Email,"
                        + " PhoneNumber, StartDate, ContracDuration, WorkExperience)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (Connection conn = ConnectDatabase.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, fullName);
                    pstmt.setString(2, sdf.format(birthDate));
                    pstmt.setString(3, gender);
                    pstmt.setString(4, idCard);
                    pstmt.setString(5, address);
                    pstmt.setString(6, email);
                    pstmt.setString(7, phone);
                    pstmt.setString(8, sdf.format(startDate));
                    pstmt.setString(10, workExperience);
                    pstmt.setString(9, contractDuration);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadStaffs();

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi thêm dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } else if (isEditing) {
                String sql = "UPDATE Employees SET "
                        + "FullName = ?, BirthDate = ?, Gender = ?,"
                        + "IDCardNumber = ?, Address = ?, Email = ?,"
                        + "PhoneNumber = ?, StartDate = ?, ContractDuration = ?,"
                        + "WorkExperience = ?"
                        + " WHERE EmployeeID = ?";

                try (Connection conn = ConnectDatabase.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, fullName);
                    pstmt.setString(2, sdf.format(birthDate));
                    pstmt.setString(3, gender);
                    pstmt.setString(4, idCard);
                    pstmt.setString(5, address);
                    pstmt.setString(6, email);
                    pstmt.setString(7, phone);
                    pstmt.setString(8, sdf.format(startDate));
                    pstmt.setString(9, contractDuration);
                    pstmt.setString(10, workExperience);
                    pstmt.setInt(11, Integer.parseInt(employeeID));
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
            loadStaffs();
        }

            private void resetJob() {
        txtEmployeeID.setText("");
        txtFullName.setText("");
        jEmail.setText("");
//        txtEstimatedStartDate.setText("");
//        txtEstimatedEndDate.setText("");
        }
            

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jEmployeeID = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        jFullName = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        jBirthdate = new javax.swing.JLabel();
        jdBirthDate = new com.toedter.calendar.JDateChooser();
        jGender = new javax.swing.JLabel();
        jIDCard = new javax.swing.JLabel();
        txtIDCard = new javax.swing.JTextField();
        jAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jWorkExp = new javax.swing.JLabel();
        txtPhoneNum = new javax.swing.JTextField();
        txtWorkExp = new javax.swing.JTextField();
        btnSaveJob = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        rbtnMale = new javax.swing.JRadioButton();
        rbtnFemale = new javax.swing.JRadioButton();
        rbtnOther = new javax.swing.JRadioButton();
        txtContractDuration = new javax.swing.JTextField();
        jContractDuration = new javax.swing.JLabel();
        jPhoneNum1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbStaff = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        btnAddJob = new javax.swing.JToggleButton();
        btnDeleteJob = new javax.swing.JToggleButton();
        btnEditJob = new javax.swing.JToggleButton();
        btnResetJob = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jStartDate = new javax.swing.JLabel();
        jdStartDate = new com.toedter.calendar.JDateChooser();

        setPreferredSize(new java.awt.Dimension(1000, 857));

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 300));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jEmployeeID.setText("EmployeeID");
        jPanel1.add(jEmployeeID, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 9, 110, -1));

        txtEmployeeID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmployeeIDActionPerformed(evt);
            }
        });
        jPanel1.add(txtEmployeeID, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 6, 369, -1));

        jFullName.setText("FullName");
        jPanel1.add(jFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 40, 110, -1));
        jPanel1.add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 37, 369, -1));

        jBirthdate.setText("BirthDate");
        jPanel1.add(jBirthdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 71, 110, -1));
        jPanel1.add(jdBirthDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 71, 369, -1));

        jGender.setText("Gender");
        jPanel1.add(jGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 99, 110, -1));

        jIDCard.setText("IDCardNumber");
        jPanel1.add(jIDCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 127, 110, -1));

        txtIDCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDCardActionPerformed(evt);
            }
        });
        jPanel1.add(txtIDCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 124, 369, -1));

        jAddress.setText("Address");
        jPanel1.add(jAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 158, 110, -1));
        jPanel1.add(txtAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 155, 369, -1));

        jEmail.setText("Email");
        jPanel1.add(jEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 189, 110, -1));
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 186, 369, -1));

        jWorkExp.setText("Work Experience");
        jPanel1.add(jWorkExp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 110, -1));
        jPanel1.add(txtPhoneNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 217, 369, -1));
        jPanel1.add(txtWorkExp, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 369, -1));

        btnSaveJob.setText("Save");
        btnSaveJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveJobActionPerformed(evt);
            }
        });
        jPanel1.add(btnSaveJob, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, -1, -1));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, -1, -1));

        rbtnMale.setText("Male");
        jPanel1.add(rbtnMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, -1, -1));

        rbtnFemale.setText("Female");
        rbtnFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnFemaleActionPerformed(evt);
            }
        });
        jPanel1.add(rbtnFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        rbtnOther.setText("Other");
        jPanel1.add(rbtnOther, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, -1, -1));
        jPanel1.add(txtContractDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 248, 369, -1));

        jContractDuration.setText("Contract Duration");
        jPanel1.add(jContractDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 251, 110, -1));

        jPhoneNum1.setText("Phone Number");
        jPanel1.add(jPhoneNum1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 220, 110, 20));

        tbStaff.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "EmployeeID", "FullName", "BirthDate", "Gender", "IDCardNumber", "Address", "Email", "PhoneNumber", "StartDate", "ContractDuration"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbStaff);

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Staff");

        jStartDate.setText("Start Date");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jStartDate)
                        .addGap(18, 18, 18)
                        .addComponent(jdStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 187, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(79, 79, 79)
                .addComponent(btnAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnResetJob, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditJob)
                    .addComponent(btnDeleteJob)
                    .addComponent(btnResetJob))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddJobMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddJobMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddJobMouseClicked

    private void btnAddJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddJobActionPerformed
        // TODO add your handling code here:
        addEmployee();
        loadStaffs();
    }//GEN-LAST:event_btnAddJobActionPerformed

    private void btnDeleteJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteJobActionPerformed
        // TODO add your handling code here:
        deleteEmployee();
        loadStaffs();
    }//GEN-LAST:event_btnDeleteJobActionPerformed

    private void btnEditJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditJobActionPerformed
      
        editEmployee();
        //loadStaffs();
    }//GEN-LAST:event_btnEditJobActionPerformed

    private void btnResetJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetJobActionPerformed
        // TODO add your handling code here:
        resetJob();

    }//GEN-LAST:event_btnResetJobActionPerformed

    private void btnSaveJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveJobActionPerformed

        saveEmployee();
        loadStaffs();
    }//GEN-LAST:event_btnSaveJobActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtIDCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDCardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDCardActionPerformed

    private void txtEmployeeIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmployeeIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmployeeIDActionPerformed

    private void rbtnFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnFemaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnFemaleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAddJob;
    private javax.swing.JButton btnCancel;
    private javax.swing.JToggleButton btnDeleteJob;
    private javax.swing.JToggleButton btnEditJob;
    private javax.swing.JToggleButton btnResetJob;
    private javax.swing.JButton btnSaveJob;
    private javax.swing.JLabel jAddress;
    private javax.swing.JLabel jBirthdate;
    private javax.swing.JLabel jContractDuration;
    private javax.swing.JLabel jEmail;
    private javax.swing.JLabel jEmployeeID;
    private javax.swing.JLabel jFullName;
    private javax.swing.JLabel jGender;
    private javax.swing.JLabel jIDCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jPhoneNum1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jStartDate;
    private javax.swing.JLabel jWorkExp;
    private com.toedter.calendar.JDateChooser jdBirthDate;
    private com.toedter.calendar.JDateChooser jdStartDate;
    private javax.swing.JRadioButton rbtnFemale;
    private javax.swing.JRadioButton rbtnMale;
    private javax.swing.JRadioButton rbtnOther;
    private javax.swing.JTable tbStaff;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtContractDuration;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmployeeID;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtIDCard;
    private javax.swing.JTextField txtPhoneNum;
    private javax.swing.JTextField txtWorkExp;
    // End of variables declaration//GEN-END:variables
}
