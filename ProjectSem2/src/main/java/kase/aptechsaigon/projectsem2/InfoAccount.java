/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Moiiii
 */
public class InfoAccount extends javax.swing.JPanel {
    public InfoAccount() {
        initComponents();
        loadInfo();
        editInfo(false);
    }
    
    private void loadInfo() {
        txtEmpID.setText(String.valueOf(Login.loggedEmployeeID));
        txtFullName.setText(Login.loggedFullName);
        txtPositionID.setText(String.valueOf(Login.loggedPositionID));
        txtIDCardNum.setText(Login.loggedIDCardNumber);
        txtAddress.setText(Login.loggedAddress);
        txtEmail.setText(Login.loggedEmail);
        txtPhoneNum.setText(Login.loggedPhoneNumber);
        txtCtrDuration.setText(Login.loggedContractDuration);
        txtExp.setText(Login.loggedExperience);
        txtUsername.setText(Login.loggedUsernameID);
        txtPassword.setText(Login.loggedPassword);
        txtExp.setText(Login.loggedExperience);
        
        //Ngày tháng năm sinh và ngày bắt đầu
        jdDoB.setDate(Login.loggedBirthDate);
        jdStartDate.setDate(Login.loggedStartDate);
        
        //Giới tính
        if (Login.loggedGender != null) {
            switch(Login.loggedGender.toLowerCase()) {
                case "male":
                    rbtnMale.setSelected(true);
                    break;
                case "female":
                    rbtnFemale.setSelected(true);
                    break;
                default:
                    rbtnOther.setSelected(true);
                    break;
            }
        }
       editInfo(false);

}
    
    private void editInfo(boolean editable) {
        txtEmpID.setEditable(false);
        txtFullName.setEditable(editable);
        txtIDCardNum.setEditable(editable);
        txtAddress.setEditable(editable);
        txtEmail.setEditable(editable);
        txtPhoneNum.setEditable(editable);
        txtCtrDuration.setEditable(editable);
        txtExp.setEditable(editable);
        txtPositionID.setEditable(false);
        txtUsername.setEditable(false);
        txtPassword.setEditable(false);
        
        //Show lại các nút để Edit
        txtFullName.setEnabled(true);
        txtIDCardNum.setEnabled(true);
        txtAddress.setEnabled(true);
        txtEmail.setEnabled(true);
        txtPhoneNum.setEnabled(true);
        txtCtrDuration.setEnabled(true);
        txtExp.setEnabled(true);
        txtPositionID.setEnabled(true);       
        rbtnMale.setEnabled(true);
        rbtnFemale.setEnabled(true);
        rbtnOther.setEnabled(true);
        jdDoB.setEnabled(true);
        jdStartDate.setEnabled(true);
    }
    private void submitInfo() {
        String employeeID = txtEmpID.getText().trim();
        String fullName = txtFullName.getText().trim();
        String idCard = txtIDCardNum.getText().trim();
        String address = txtAddress.getText().trim();            
        String email = txtEmail.getText().trim();
        String phone = txtPhoneNum.getText().trim();
        String contractDuration = txtCtrDuration.getText().trim();
        String workExperience = txtExp.getText().trim();
        String positionID = txtPositionID.getText().trim();
        String gender = "other";
                if (rbtnMale.isSelected()) {
                    gender = "male";
                } else if (rbtnFemale.isSelected()) {
                    gender = "female";
                }
            Date birthDate = jdDoB.getDate();
            Date startDate = jdStartDate.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            
            if (fullName.isEmpty() || idCard.isEmpty() || address.isEmpty() || 
                email.isEmpty() || phone.isEmpty() || birthDate == null || 
                positionID.isEmpty() || startDate == null || contractDuration.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String sql = "UPDATE Employees SET "
                        + "FullName = ?, BirthDate = ?, Gender = ?,"
                        + "IDCardNumber = ?, Address = ?, Email = ?,"
                        + "PhoneNumber = ?, StartDate = ?, ContractDuration = ?,"
                        + "WorkExperience = ?"
                        + " WHERE EmployeeID = ?";
                Connection conn = ConnectDatabase.getConnection();
                try (
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
                    
                    txtFullName.setEnabled(false);
                    txtIDCardNum.setEnabled(false);
                    txtAddress.setEnabled(false);
                    txtEmail.setEnabled(false);
                    txtPhoneNum.setEnabled(false);
                    txtCtrDuration.setEnabled(false);
                    txtExp.setEnabled(false);
                    txtPositionID.setEnabled(false);
                    rbtnMale.setEnabled(false);
                    rbtnFemale.setEnabled(false);
                    rbtnOther.setEnabled(false);
                    jdDoB.setEnabled(false);
                    jdStartDate.setEnabled(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ConnectDatabase.closeConnection(conn);
                }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jtInfo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jlEmpID = new javax.swing.JLabel();
        txtEmpID = new javax.swing.JTextField();
        jlFullname = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        jlGender = new javax.swing.JLabel();
        jlDoB = new javax.swing.JLabel();
        jdDoB = new com.toedter.calendar.JDateChooser();
        jlIDCardNum = new javax.swing.JLabel();
        txtIDCardNum = new javax.swing.JTextField();
        jlAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jlEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jlPhoneNum = new javax.swing.JLabel();
        txtPhoneNum = new javax.swing.JTextField();
        jlCtrDuration = new javax.swing.JLabel();
        txtCtrDuration = new javax.swing.JTextField();
        jlStartDate = new javax.swing.JLabel();
        jlExp = new javax.swing.JLabel();
        txtExp = new javax.swing.JTextField();
        jlPositionID = new javax.swing.JLabel();
        txtPositionID = new javax.swing.JTextField();
        jlUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jlPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();
        rbtnMale = new javax.swing.JRadioButton();
        rbtnFemale = new javax.swing.JRadioButton();
        rbtnOther = new javax.swing.JRadioButton();
        jdStartDate = new com.toedter.calendar.JDateChooser();
        Submit1 = new javax.swing.JButton();
        Submit2 = new javax.swing.JButton();
        btnChangePassword = new javax.swing.JButton();

        jtInfo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jtInfo.setText("Infomation Account");

        jlEmpID.setText("EmployeeID");

        jlFullname.setText("Fullname");

        jlGender.setText("Gender");

        jlDoB.setText("Date of Birth");

        jlIDCardNum.setText("ID Card Number");

        txtIDCardNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDCardNumActionPerformed(evt);
            }
        });

        jlAddress.setText("Address");

        jlEmail.setText("Email");

        jlPhoneNum.setText("Phone Number");

        jlCtrDuration.setText("Contract Duration");

        jlStartDate.setText("Start Date");

        jlExp.setText("Experience");

        jlPositionID.setText("PositionID");

        jlUsername.setText("UsernameID");

        jlPassword.setText("Password");

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnMale);
        rbtnMale.setText("Male");
        rbtnMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnMaleActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnFemale);
        rbtnFemale.setText("Female");
        rbtnFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnFemaleActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnOther);
        rbtnOther.setText("Other");
        rbtnOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnOtherActionPerformed(evt);
            }
        });

        Submit1.setText("Submit");
        Submit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Submit1ActionPerformed(evt);
            }
        });

        Submit2.setText("Cancel");

        btnChangePassword.setText("Change Password");
        btnChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(182, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCtrDuration)
                            .addComponent(jlEmail)
                            .addComponent(jlAddress)
                            .addComponent(jlIDCardNum)
                            .addComponent(jlEmpID)
                            .addComponent(jlFullname)
                            .addComponent(jlDoB)
                            .addComponent(jlStartDate)
                            .addComponent(jlExp, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlPositionID, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlGender))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIDCardNum)
                            .addComponent(txtAddress)
                            .addComponent(txtEmail)
                            .addComponent(txtCtrDuration)
                            .addComponent(txtExp)
                            .addComponent(txtPositionID)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword)
                            .addComponent(jdDoB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rbtnMale)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbtnFemale)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbtnOther))
                            .addComponent(txtEmpID)
                            .addComponent(txtFullName)
                            .addComponent(jdStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addComponent(btnChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Submit1)
                        .addGap(18, 18, 18)
                        .addComponent(Submit2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlPhoneNum)
                        .addGap(46, 46, 46)
                        .addComponent(txtPhoneNum)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlEmpID)
                    .addComponent(txtEmpID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlFullname)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlGender)
                    .addComponent(rbtnMale)
                    .addComponent(rbtnFemale)
                    .addComponent(rbtnOther))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlDoB)
                    .addComponent(jdDoB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlIDCardNum)
                    .addComponent(txtIDCardNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlAddress)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPhoneNum)
                    .addComponent(txtPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCtrDuration)
                    .addComponent(txtCtrDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlStartDate)
                    .addComponent(jdStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlExp)
                    .addComponent(txtExp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPositionID)
                    .addComponent(txtPositionID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Submit1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Submit2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(271, Short.MAX_VALUE)
                    .addComponent(jtInfo)
                    .addContainerGap(271, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jtInfo)
                    .addContainerGap(538, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(39, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(524, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void txtIDCardNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDCardNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDCardNumActionPerformed

    private void rbtnMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnMaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnMaleActionPerformed

    private void rbtnFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnFemaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnFemaleActionPerformed

    private void rbtnOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnOtherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnOtherActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editInfo(true);
        btnChangePassword.setEnabled(false);
    }//GEN-LAST:event_btnEditActionPerformed

    private void Submit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Submit1ActionPerformed
       submitInfo();
    }//GEN-LAST:event_Submit1ActionPerformed

    private void btnChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePasswordActionPerformed
        btnEdit.setEnabled(false);
    }//GEN-LAST:event_btnChangePasswordActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Submit1;
    private javax.swing.JButton Submit2;
    private javax.swing.JButton btnChangePassword;
    private javax.swing.JButton btnEdit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private com.toedter.calendar.JDateChooser jdDoB;
    private com.toedter.calendar.JDateChooser jdStartDate;
    private javax.swing.JLabel jlAddress;
    private javax.swing.JLabel jlCtrDuration;
    private javax.swing.JLabel jlDoB;
    private javax.swing.JLabel jlEmail;
    private javax.swing.JLabel jlEmpID;
    private javax.swing.JLabel jlExp;
    private javax.swing.JLabel jlFullname;
    private javax.swing.JLabel jlGender;
    private javax.swing.JLabel jlIDCardNum;
    private javax.swing.JLabel jlPassword;
    private javax.swing.JLabel jlPhoneNum;
    private javax.swing.JLabel jlPositionID;
    private javax.swing.JLabel jlStartDate;
    private javax.swing.JLabel jlUsername;
    private javax.swing.JLabel jtInfo;
    private javax.swing.JRadioButton rbtnFemale;
    private javax.swing.JRadioButton rbtnMale;
    private javax.swing.JRadioButton rbtnOther;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtCtrDuration;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmpID;
    private javax.swing.JTextField txtExp;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtIDCardNum;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPhoneNum;
    private javax.swing.JTextField txtPositionID;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
