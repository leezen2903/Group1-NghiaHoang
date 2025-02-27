/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

import java.awt.Color;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;


/**
 *
 * @author Admin
 */
public class Login extends javax.swing.JFrame {
   
    public static int loggedEmployeeID;
    public static String loggedFullName;
    public static String loggedIDCardNumber;
    public static int loggedPositionID;
    public static String loggedAddress;
    public static String loggedEmail;
    public static String loggedPhoneNumber;
    public static String loggedContractDuration;
    public static String loggedExperience;
    public static String loggedUsernameID;
    public static String loggedGender;
    public static Date loggedBirthDate;
    public static Date loggedStartDate;
    public static String loggedPassword;
        public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLogin = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pLoginForm = new javax.swing.JPanel();
        jUserID = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jPassword = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        pwdPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLogin.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLogin.setText("Login");

        pLoginForm.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.darkGray, java.awt.Color.black, java.awt.Color.black, java.awt.Color.darkGray));

        jUserID.setText("UserID");

        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsernameFocusLost(evt);
            }
        });

        jPassword.setText("Password");

        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        pwdPassword.setText("Enter Password");
        pwdPassword.setToolTipText("");
        pwdPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pwdPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pwdPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pwdPasswordFocusLost(evt);
            }
        });

        javax.swing.GroupLayout pLoginFormLayout = new javax.swing.GroupLayout(pLoginForm);
        pLoginForm.setLayout(pLoginFormLayout);
        pLoginFormLayout.setHorizontalGroup(
            pLoginFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLoginFormLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pLoginFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jUserID)
                    .addComponent(jPassword))
                .addGap(18, 18, 18)
                .addGroup(pLoginFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pwdPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtUsername))
                .addContainerGap(41, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pLoginFormLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pLoginFormLayout.setVerticalGroup(
            pLoginFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLoginFormLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(pLoginFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jUserID)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(pLoginFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPassword)
                    .addComponent(pwdPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(btnLogin)
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pLoginForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(jLogin)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pLoginForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
       login();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void pwdPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pwdPasswordFocusGained
        if (String.valueOf(pwdPassword.getPassword()).equals("Enter Password")) {
            pwdPassword.setText("");            
            pwdPassword.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_pwdPasswordFocusGained

    private void pwdPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pwdPasswordFocusLost
        if (pwdPassword.getPassword().length == 0) {
            pwdPassword.setForeground(Color.GRAY);
            pwdPassword.setText("Enter Password");
            pwdPassword.setEchoChar((char) 0);
        }
    }//GEN-LAST:event_pwdPasswordFocusLost

    private void txtUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusGained
        if (txtUsername.getText().equals("Enter User ID Cardnumber")) {
            txtUsername.setText("");            
            txtUsername.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtUsernameFocusGained

    private void txtUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusLost
        if (txtUsername.getText().trim().isEmpty()) {
            txtUsername.setForeground(Color.GRAY);
            txtUsername.setText("Enter User ID Cardnumber");
        }
    }//GEN-LAST:event_txtUsernameFocusLost
    
    private void login() {
        String userID = txtUsername.getText().trim();
        String password = new String(pwdPassword.getPassword()).trim();
        
        if (userID.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Insert Username & Password!","ERROR!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (Connection conn = ConnectDatabase.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(
             "SELECT EmployeeID, FullName, IDCardNumber,"
                     + " Address, Email, PhoneNumber,"
                     + " ContractDuration, WorkExperience, PositionID,"
                     + " PasswordHash, Gender, BirthDate, StartDate"
                     + " FROM employees WHERE IDCardNumber = ? AND PasswordHash = ?")) {
        pstmt.setString(1, userID);
        pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {         
            loggedEmployeeID = rs.getInt("EmployeeID");
            loggedFullName = rs.getString("FullName");
            loggedIDCardNumber = rs.getString("IDCardNumber");
            loggedAddress = rs.getString("Address");
            loggedEmail = rs.getString("Email");
            loggedPhoneNumber = rs.getString("PhoneNumber");
            loggedContractDuration = rs.getString("ContractDuration");
            loggedExperience = rs.getString("WorkExperience");
            loggedUsernameID = userID;
            loggedPositionID = rs.getInt("PositionID");
            loggedGender = rs.getString("Gender");
            loggedBirthDate = rs.getDate("BirthDate");
            loggedStartDate = rs.getDate("StartDate");
            loggedPassword = rs.getString("PasswordHash");
            
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Xin chào, " + loggedFullName);
            MainFrame mainFrame = new MainFrame(loggedEmployeeID, loggedFullName, loggedPositionID);
            mainFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai UserID hoặc không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLogin;
    private javax.swing.JLabel jPassword;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel jUserID;
    private javax.swing.JPanel pLoginForm;
    private javax.swing.JPasswordField pwdPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
