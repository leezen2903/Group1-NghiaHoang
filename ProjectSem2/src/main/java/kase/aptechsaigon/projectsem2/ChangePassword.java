/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

/**
 *
 * @author Admin
 */
public class ChangePassword extends javax.swing.JFrame {

    /**
     * Creates new form ChangePassword
     */
    public ChangePassword() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jChangePass = new javax.swing.JLabel();
        jNoti1 = new javax.swing.JLabel();
        jOldpass = new javax.swing.JTextField();
        jNoti2 = new javax.swing.JLabel();
        jNewpass = new javax.swing.JTextField();
        jNoti3 = new javax.swing.JLabel();
        jRetype = new javax.swing.JTextField();
        Submit = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuAcc = new javax.swing.JMenu();
        mnuiViewAcc = new javax.swing.JMenuItem();
        mnuiEditAcc = new javax.swing.JMenuItem();
        mnuHR = new javax.swing.JMenu();
        mnuDepartment = new javax.swing.JMenu();
        mnuiViewDept = new javax.swing.JMenuItem();
        mnuiAddDept = new javax.swing.JMenuItem();
        mnuiEditDept = new javax.swing.JMenuItem();
        mnuiDelDept = new javax.swing.JMenuItem();
        mnuTeam = new javax.swing.JMenu();
        mnuiViewStaff1 = new javax.swing.JMenuItem();
        mnuiAddStaff1 = new javax.swing.JMenuItem();
        mnuiEditStaff1 = new javax.swing.JMenuItem();
        mnuiDeleteStaff1 = new javax.swing.JMenuItem();
        mnuStaff = new javax.swing.JMenu();
        mnuiViewStaff = new javax.swing.JMenuItem();
        mnuiAddStaff = new javax.swing.JMenuItem();
        mnuiEditStaff = new javax.swing.JMenuItem();
        mnuiDeleteStaff = new javax.swing.JMenuItem();
        mnuSalary = new javax.swing.JMenu();
        mnuiViewSalary = new javax.swing.JMenuItem();
        mnuiCalSalaray = new javax.swing.JMenuItem();
        mnuJob = new javax.swing.JMenu();
        mnuJobList = new javax.swing.JMenuItem();
        mnuJobAssignment = new javax.swing.JMenuItem();
        mnuTaskManagement = new javax.swing.JMenuItem();
        mnuTaskReport = new javax.swing.JMenuItem();
        mnuStatistical = new javax.swing.JMenu();
        mnuiS_Salary = new javax.swing.JMenuItem();
        mnuiS_Job = new javax.swing.JMenuItem();
        mnuiS_HR = new javax.swing.JMenuItem();
        mnuSystem = new javax.swing.JMenu();
        mnuiInfo = new javax.swing.JMenuItem();
        mnuiLogout = new javax.swing.JMenuItem();
        mnuiExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel.setBackground(new java.awt.Color(255, 255, 255));
        jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jChangePass.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jChangePass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jChangePass.setText("Change Password");

        jNoti1.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jNoti1.setText("Wrong password");

        jOldpass.setText("Old password");

        jNoti2.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jNoti2.setText("Correct!");

        jNewpass.setText("New password");

        jNoti3.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jNoti3.setText("Correct!");

        jRetype.setText("Retype new password");

        Submit.setText("Submit");

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jOldpass, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jNoti1)
                    .addComponent(jNewpass, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jNoti2)
                    .addComponent(jRetype, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jNoti3)
                    .addComponent(Submit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jNoti1)
                .addGap(1, 1, 1)
                .addComponent(jOldpass, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jNoti2)
                .addGap(1, 1, 1)
                .addComponent(jNewpass, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jNoti3)
                .addGap(1, 1, 1)
                .addComponent(jRetype, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Submit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        mnuAcc.setText("Tài khoản");

        mnuiViewAcc.setText("Xem thông tin");
        mnuAcc.add(mnuiViewAcc);

        mnuiEditAcc.setText("Chỉnh sửa");
        mnuAcc.add(mnuiEditAcc);

        jMenuBar1.add(mnuAcc);

        mnuHR.setText("Nhân sự");

        mnuDepartment.setText("Phòng ban");

        mnuiViewDept.setText("Xem danh sách phòng ban");
        mnuDepartment.add(mnuiViewDept);

        mnuiAddDept.setText("Thêm phòng ban");
        mnuDepartment.add(mnuiAddDept);

        mnuiEditDept.setText("Điều chỉnh phòng ban");
        mnuDepartment.add(mnuiEditDept);

        mnuiDelDept.setText("Xóa thông tin nhân viên");
        mnuDepartment.add(mnuiDelDept);

        mnuHR.add(mnuDepartment);

        mnuTeam.setText("Nhóm");

        mnuiViewStaff1.setText("Xem danh sách nhóm");
        mnuTeam.add(mnuiViewStaff1);

        mnuiAddStaff1.setText("Thêm nhóm");
        mnuTeam.add(mnuiAddStaff1);

        mnuiEditStaff1.setText("Điều chỉnh thông tin nhóm");
        mnuiEditStaff1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuiEditStaff1ActionPerformed(evt);
            }
        });
        mnuTeam.add(mnuiEditStaff1);

        mnuiDeleteStaff1.setText("Xóa nhóm");
        mnuTeam.add(mnuiDeleteStaff1);

        mnuHR.add(mnuTeam);

        mnuStaff.setText("Nhân viên");

        mnuiViewStaff.setText("Xem danh sách nhân viên");
        mnuStaff.add(mnuiViewStaff);

        mnuiAddStaff.setText("Thêm nhân viên");
        mnuStaff.add(mnuiAddStaff);

        mnuiEditStaff.setText("Điều chỉnh thông tin nhân viên");
        mnuStaff.add(mnuiEditStaff);

        mnuiDeleteStaff.setText("Xóa thông tin nhân viên");
        mnuStaff.add(mnuiDeleteStaff);

        mnuHR.add(mnuStaff);

        mnuSalary.setText("Lương");

        mnuiViewSalary.setText("Xem bảng lương");
        mnuSalary.add(mnuiViewSalary);

        mnuiCalSalaray.setText("Tính lương");
        mnuSalary.add(mnuiCalSalaray);

        mnuHR.add(mnuSalary);

        jMenuBar1.add(mnuHR);

        mnuJob.setText("Công việc");

        mnuJobList.setText("Danh sách công việc");
        mnuJob.add(mnuJobList);

        mnuJobAssignment.setText("Phân công công việc");
        mnuJob.add(mnuJobAssignment);

        mnuTaskManagement.setText("Quản lý nhiệm vụ");
        mnuJob.add(mnuTaskManagement);

        mnuTaskReport.setText("Báo cáo nhiệm vụ");
        mnuJob.add(mnuTaskReport);

        jMenuBar1.add(mnuJob);

        mnuStatistical.setText("Thống kê");

        mnuiS_Salary.setText("Thống kê công việc");
        mnuStatistical.add(mnuiS_Salary);

        mnuiS_Job.setText("Thống kê nhân sự");
        mnuStatistical.add(mnuiS_Job);

        mnuiS_HR.setText("Thống kê lương");
        mnuStatistical.add(mnuiS_HR);

        jMenuBar1.add(mnuStatistical);

        mnuSystem.setText("Hệ thống");

        mnuiInfo.setText("Thông tin ứng dụng");
        mnuSystem.add(mnuiInfo);

        mnuiLogout.setText("Đăng xuất");
        mnuSystem.add(mnuiLogout);

        mnuiExit.setText("Thoát chương trình");
        mnuSystem.add(mnuiExit);

        jMenuBar1.add(mnuSystem);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuiEditStaff1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuiEditStaff1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuiEditStaff1ActionPerformed

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
            java.util.logging.Logger.getLogger(ChangePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChangePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChangePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChangePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChangePassword().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Submit;
    private javax.swing.JLabel jChangePass;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JTextField jNewpass;
    private javax.swing.JLabel jNoti1;
    private javax.swing.JLabel jNoti2;
    private javax.swing.JLabel jNoti3;
    private javax.swing.JTextField jOldpass;
    private javax.swing.JPanel jPanel;
    private javax.swing.JTextField jRetype;
    private javax.swing.JMenu mnuAcc;
    private javax.swing.JMenu mnuDepartment;
    private javax.swing.JMenu mnuHR;
    private javax.swing.JMenu mnuJob;
    private javax.swing.JMenuItem mnuJobAssignment;
    private javax.swing.JMenuItem mnuJobList;
    private javax.swing.JMenu mnuSalary;
    private javax.swing.JMenu mnuStaff;
    private javax.swing.JMenu mnuStatistical;
    private javax.swing.JMenu mnuSystem;
    private javax.swing.JMenuItem mnuTaskManagement;
    private javax.swing.JMenuItem mnuTaskReport;
    private javax.swing.JMenu mnuTeam;
    private javax.swing.JMenuItem mnuiAddDept;
    private javax.swing.JMenuItem mnuiAddStaff;
    private javax.swing.JMenuItem mnuiAddStaff1;
    private javax.swing.JMenuItem mnuiCalSalaray;
    private javax.swing.JMenuItem mnuiDelDept;
    private javax.swing.JMenuItem mnuiDeleteStaff;
    private javax.swing.JMenuItem mnuiDeleteStaff1;
    private javax.swing.JMenuItem mnuiEditAcc;
    private javax.swing.JMenuItem mnuiEditDept;
    private javax.swing.JMenuItem mnuiEditStaff;
    private javax.swing.JMenuItem mnuiEditStaff1;
    private javax.swing.JMenuItem mnuiExit;
    private javax.swing.JMenuItem mnuiInfo;
    private javax.swing.JMenuItem mnuiLogout;
    private javax.swing.JMenuItem mnuiS_HR;
    private javax.swing.JMenuItem mnuiS_Job;
    private javax.swing.JMenuItem mnuiS_Salary;
    private javax.swing.JMenuItem mnuiViewAcc;
    private javax.swing.JMenuItem mnuiViewDept;
    private javax.swing.JMenuItem mnuiViewSalary;
    private javax.swing.JMenuItem mnuiViewStaff;
    private javax.swing.JMenuItem mnuiViewStaff1;
    // End of variables declaration//GEN-END:variables
}
