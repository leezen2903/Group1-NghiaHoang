/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

import javax.swing.JFrame;

/**
 *
 * @author Moiiii
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
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

        jMenuBar1 = new javax.swing.JMenuBar();
        mnuAcc = new javax.swing.JMenu();
        mnuiViewAcc = new javax.swing.JMenuItem();
        mnuiEditAcc = new javax.swing.JMenuItem();
        mnuHR = new javax.swing.JMenu();
        mnuStaff = new javax.swing.JMenu();
        mnuViewStaff = new javax.swing.JMenuItem();
        mnuAddStaff = new javax.swing.JMenuItem();
        mnuEditStaff = new javax.swing.JMenuItem();
        mnuDeleteStaff = new javax.swing.JMenuItem();
        mnuiDepartmenjt = new javax.swing.JMenuItem();
        mnuiTeam = new javax.swing.JMenuItem();
        mnuiSalary1 = new javax.swing.JMenuItem();
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

        mnuAcc.setText("Tài khoản");

        mnuiViewAcc.setText("Xem thông tin");
        mnuAcc.add(mnuiViewAcc);

        mnuiEditAcc.setText("Chỉnh sửa");
        mnuAcc.add(mnuiEditAcc);

        jMenuBar1.add(mnuAcc);

        mnuHR.setText("Nhân sự");

        mnuStaff.setText("Nhân viên");

        mnuViewStaff.setText("Xem danh sách nhân viên");
        mnuStaff.add(mnuViewStaff);

        mnuAddStaff.setText("Thêm nhân viên");
        mnuStaff.add(mnuAddStaff);

        mnuEditStaff.setText("Điều chỉnh thông tin nhân viên");
        mnuStaff.add(mnuEditStaff);

        mnuDeleteStaff.setText("Xóa thông tin nhân viên");
        mnuStaff.add(mnuDeleteStaff);

        mnuHR.add(mnuStaff);

        mnuiDepartmenjt.setText("Phòng ban");
        mnuHR.add(mnuiDepartmenjt);

        mnuiTeam.setText("Nhóm");
        mnuHR.add(mnuiTeam);

        mnuiSalary1.setText("Lương");
        mnuHR.add(mnuiSalary1);

        jMenuBar1.add(mnuHR);

        mnuJob.setText("Công việc");

        mnuJobList.setText("Danh sách công việc");
        mnuJob.add(mnuJobList);

        mnuJobAssignment.setText("Phân công công việc");
        mnuJob.add(mnuJobAssignment);

        mnuTaskManagement.setText("Quản lý nhiệm vụ");
        mnuJob.add(mnuTaskManagement);

        mnuTaskReport.setText("jMenuItem1");
        mnuJob.add(mnuTaskReport);

        jMenuBar1.add(mnuJob);

        mnuStatistical.setText("jMenu1");

        mnuiS_Salary.setText("jMenuItem1");
        mnuStatistical.add(mnuiS_Salary);

        mnuiS_Job.setText("jMenuItem1");
        mnuStatistical.add(mnuiS_Job);

        mnuiS_HR.setText("jMenuItem1");
        mnuStatistical.add(mnuiS_HR);

        jMenuBar1.add(mnuStatistical);

        mnuSystem.setText("jMenu1");

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
            .addGap(0, 836, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 507, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
public static void main(String args[]) {
    try {
        // Set the Nimbus look and feel
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    // Create and display the Login form first
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            Login loginFrame = new Login(); // Hiển thị cửa sổ Login đầu tiên
            loginFrame.setVisible(true); // Hiển thị cửa sổ Login
        }
    });
}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu mnuAcc;
    private javax.swing.JMenuItem mnuAddStaff;
    private javax.swing.JMenuItem mnuDeleteStaff;
    private javax.swing.JMenuItem mnuEditStaff;
    private javax.swing.JMenu mnuHR;
    private javax.swing.JMenu mnuJob;
    private javax.swing.JMenuItem mnuJobAssignment;
    private javax.swing.JMenuItem mnuJobList;
    private javax.swing.JMenu mnuStaff;
    private javax.swing.JMenu mnuStatistical;
    private javax.swing.JMenu mnuSystem;
    private javax.swing.JMenuItem mnuTaskManagement;
    private javax.swing.JMenuItem mnuTaskReport;
    private javax.swing.JMenuItem mnuViewStaff;
    private javax.swing.JMenuItem mnuiDepartmenjt;
    private javax.swing.JMenuItem mnuiEditAcc;
    private javax.swing.JMenuItem mnuiExit;
    private javax.swing.JMenuItem mnuiInfo;
    private javax.swing.JMenuItem mnuiLogout;
    private javax.swing.JMenuItem mnuiS_HR;
    private javax.swing.JMenuItem mnuiS_Job;
    private javax.swing.JMenuItem mnuiS_Salary;
    private javax.swing.JMenuItem mnuiSalary1;
    private javax.swing.JMenuItem mnuiTeam;
    private javax.swing.JMenuItem mnuiViewAcc;
    // End of variables declaration//GEN-END:variables
}
