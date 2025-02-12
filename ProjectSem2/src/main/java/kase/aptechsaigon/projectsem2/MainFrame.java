/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */


public class MainFrame extends javax.swing.JFrame {
    //Định nghĩa biến panel
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Full màn hình
        jpJob = new Job(); //Khởi tạo từ clas Job
        
    }
    
      private void showPanel(JPanel panel) {
        jpBackground.removeAll(); 
        jpBackground.setLayout(new BorderLayout());
        jpBackground.add(panel, BorderLayout.CENTER);
        jpBackground.revalidate();
        jpBackground.repaint();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpBackground = new javax.swing.JPanel();
        jpJob = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmSystem = new javax.swing.JMenu();
        jmiLogout = new javax.swing.JMenuItem();
        jmiExit = new javax.swing.JMenuItem();
        jmHR = new javax.swing.JMenu();
        jmiDept = new javax.swing.JMenuItem();
        jmiRole = new javax.swing.JMenuItem();
        jmiTeam = new javax.swing.JMenuItem();
        jmiStaff = new javax.swing.JMenuItem();
        jmiContract = new javax.swing.JMenuItem();
        jmJob = new javax.swing.JMenu();
        jmiJobManagement = new javax.swing.JMenuItem();
        jmiTaskManagement = new javax.swing.JMenuItem();
        jmSalary = new javax.swing.JMenu();
        jmiStaffSalary = new javax.swing.JMenuItem();
        jmiCalculateSalary = new javax.swing.JMenuItem();
        jmHelp = new javax.swing.JMenu();
        jmiManual = new javax.swing.JMenuItem();
        jmiAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jpJobLayout = new javax.swing.GroupLayout(jpJob);
        jpJob.setLayout(jpJobLayout);
        jpJobLayout.setHorizontalGroup(
            jpJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 816, Short.MAX_VALUE)
        );
        jpJobLayout.setVerticalGroup(
            jpJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpBackgroundLayout = new javax.swing.GroupLayout(jpBackground);
        jpBackground.setLayout(jpBackgroundLayout);
        jpBackgroundLayout.setHorizontalGroup(
            jpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpJob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpBackgroundLayout.setVerticalGroup(
            jpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpJob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jmSystem.setText("System");

        jmiLogout.setText("Log out");
        jmSystem.add(jmiLogout);

        jmiExit.setText("Exit");
        jmSystem.add(jmiExit);

        jMenuBar1.add(jmSystem);

        jmHR.setText("HR");

        jmiDept.setText("Dept");
        jmHR.add(jmiDept);

        jmiRole.setText("Role");
        jmHR.add(jmiRole);

        jmiTeam.setText("Team");
        jmHR.add(jmiTeam);

        jmiStaff.setText("Staff");
        jmHR.add(jmiStaff);

        jmiContract.setText("Contract");
        jmHR.add(jmiContract);

        jMenuBar1.add(jmHR);

        jmJob.setText("Job");

        jmiJobManagement.setText("Job Management");
        jmiJobManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiJobManagementActionPerformed(evt);
            }
        });
        jmJob.add(jmiJobManagement);

        jmiTaskManagement.setText("Task Management");
        jmJob.add(jmiTaskManagement);

        jMenuBar1.add(jmJob);

        jmSalary.setText("Salary");

        jmiStaffSalary.setText("Staff Salary");
        jmSalary.add(jmiStaffSalary);

        jmiCalculateSalary.setText("Calculate Salary");
        jmSalary.add(jmiCalculateSalary);

        jMenuBar1.add(jmSalary);

        jmHelp.setText("Help");

        jmiManual.setText("Manual");
        jmHelp.add(jmiManual);

        jmiAbout.setText("About");
        jmHelp.add(jmiAbout);

        jMenuBar1.add(jmHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiJobManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiJobManagementActionPerformed
      //** Gọi panel công việc **
      showPanel(jpJob);
    }//GEN-LAST:event_jmiJobManagementActionPerformed

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
    
       
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jmHR;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenu jmJob;
    private javax.swing.JMenu jmSalary;
    private javax.swing.JMenu jmSystem;
    private javax.swing.JMenuItem jmiAbout;
    private javax.swing.JMenuItem jmiCalculateSalary;
    private javax.swing.JMenuItem jmiContract;
    private javax.swing.JMenuItem jmiDept;
    private javax.swing.JMenuItem jmiExit;
    private javax.swing.JMenuItem jmiJobManagement;
    private javax.swing.JMenuItem jmiLogout;
    private javax.swing.JMenuItem jmiManual;
    private javax.swing.JMenuItem jmiRole;
    private javax.swing.JMenuItem jmiStaff;
    private javax.swing.JMenuItem jmiStaffSalary;
    private javax.swing.JMenuItem jmiTaskManagement;
    private javax.swing.JMenuItem jmiTeam;
    private javax.swing.JPanel jpBackground;
    private javax.swing.JPanel jpJob;
    // End of variables declaration//GEN-END:variables
}
