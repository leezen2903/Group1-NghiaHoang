/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */


public class MainFrame extends javax.swing.JFrame {
    
    /**
     * Creates new form MainFrame
     */
    private JPanel defaultPanel;
    public MainFrame() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Full màn hình
        jpJob = new Job();
        jpTask = new Task();
        jpDept = new Department();
        jpStaff = new Employee();
        jpTeam = new Team();
        jpSalary = new SalaryManager();
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
        jpTask = new javax.swing.JPanel();
        jpHR = new javax.swing.JPanel();
        jpDept = new javax.swing.JPanel();
        jpStaff = new javax.swing.JPanel();
        jpSalary = new javax.swing.JPanel();
        jpAttTracking = new javax.swing.JPanel();
        jpTeam = new javax.swing.JPanel();
        jpJob1 = new javax.swing.JPanel();
        jpTask1 = new javax.swing.JPanel();
        jpHR1 = new javax.swing.JPanel();
        jpDept1 = new javax.swing.JPanel();
        jpStaff1 = new javax.swing.JPanel();
        jpSalary1 = new javax.swing.JPanel();
        jpAttTracking1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmSystem = new javax.swing.JMenu();
        jmiHome = new javax.swing.JMenuItem();
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
        jmiAttendanceTracking = new javax.swing.JMenuItem();
        jmHelp = new javax.swing.JMenu();
        jmiManual = new javax.swing.JMenuItem();
        jmiAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jpTaskLayout = new javax.swing.GroupLayout(jpTask);
        jpTask.setLayout(jpTaskLayout);
        jpTaskLayout.setHorizontalGroup(
            jpTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 856, Short.MAX_VALUE)
        );
        jpTaskLayout.setVerticalGroup(
            jpTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpJobLayout = new javax.swing.GroupLayout(jpJob);
        jpJob.setLayout(jpJobLayout);
        jpJobLayout.setHorizontalGroup(
            jpJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpJobLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpTask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpJobLayout.setVerticalGroup(
            jpJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpTask, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpDeptLayout = new javax.swing.GroupLayout(jpDept);
        jpDept.setLayout(jpDeptLayout);
        jpDeptLayout.setHorizontalGroup(
            jpDeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 852, Short.MAX_VALUE)
        );
        jpDeptLayout.setVerticalGroup(
            jpDeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpStaffLayout = new javax.swing.GroupLayout(jpStaff);
        jpStaff.setLayout(jpStaffLayout);
        jpStaffLayout.setHorizontalGroup(
            jpStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
        );
        jpStaffLayout.setVerticalGroup(
            jpStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpHRLayout = new javax.swing.GroupLayout(jpHR);
        jpHR.setLayout(jpHRLayout);
        jpHRLayout.setHorizontalGroup(
            jpHRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
            .addGroup(jpHRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpHRLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpDept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jpHRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpHRLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jpHRLayout.setVerticalGroup(
            jpHRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
            .addGroup(jpHRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpHRLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpDept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jpHRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpHRLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout jpAttTrackingLayout = new javax.swing.GroupLayout(jpAttTracking);
        jpAttTracking.setLayout(jpAttTrackingLayout);
        jpAttTrackingLayout.setHorizontalGroup(
            jpAttTrackingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 856, Short.MAX_VALUE)
        );
        jpAttTrackingLayout.setVerticalGroup(
            jpAttTrackingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpSalaryLayout = new javax.swing.GroupLayout(jpSalary);
        jpSalary.setLayout(jpSalaryLayout);
        jpSalaryLayout.setHorizontalGroup(
            jpSalaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 856, Short.MAX_VALUE)
            .addGroup(jpSalaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpAttTracking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpSalaryLayout.setVerticalGroup(
            jpSalaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
            .addGroup(jpSalaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpAttTracking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpTask1Layout = new javax.swing.GroupLayout(jpTask1);
        jpTask1.setLayout(jpTask1Layout);
        jpTask1Layout.setHorizontalGroup(
            jpTask1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 856, Short.MAX_VALUE)
        );
        jpTask1Layout.setVerticalGroup(
            jpTask1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpJob1Layout = new javax.swing.GroupLayout(jpJob1);
        jpJob1.setLayout(jpJob1Layout);
        jpJob1Layout.setHorizontalGroup(
            jpJob1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpJob1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpTask1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpJob1Layout.setVerticalGroup(
            jpJob1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpTask1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpDept1Layout = new javax.swing.GroupLayout(jpDept1);
        jpDept1.setLayout(jpDept1Layout);
        jpDept1Layout.setHorizontalGroup(
            jpDept1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 852, Short.MAX_VALUE)
        );
        jpDept1Layout.setVerticalGroup(
            jpDept1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpStaff1Layout = new javax.swing.GroupLayout(jpStaff1);
        jpStaff1.setLayout(jpStaff1Layout);
        jpStaff1Layout.setHorizontalGroup(
            jpStaff1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
        );
        jpStaff1Layout.setVerticalGroup(
            jpStaff1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpHR1Layout = new javax.swing.GroupLayout(jpHR1);
        jpHR1.setLayout(jpHR1Layout);
        jpHR1Layout.setHorizontalGroup(
            jpHR1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
            .addGroup(jpHR1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpHR1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpDept1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jpHR1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpHR1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpStaff1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jpHR1Layout.setVerticalGroup(
            jpHR1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
            .addGroup(jpHR1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpHR1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpDept1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jpHR1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpHR1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpStaff1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout jpAttTracking1Layout = new javax.swing.GroupLayout(jpAttTracking1);
        jpAttTracking1.setLayout(jpAttTracking1Layout);
        jpAttTracking1Layout.setHorizontalGroup(
            jpAttTracking1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 856, Short.MAX_VALUE)
        );
        jpAttTracking1Layout.setVerticalGroup(
            jpAttTracking1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpSalary1Layout = new javax.swing.GroupLayout(jpSalary1);
        jpSalary1.setLayout(jpSalary1Layout);
        jpSalary1Layout.setHorizontalGroup(
            jpSalary1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 856, Short.MAX_VALUE)
            .addGroup(jpSalary1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpAttTracking1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpSalary1Layout.setVerticalGroup(
            jpSalary1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
            .addGroup(jpSalary1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpAttTracking1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpTeamLayout = new javax.swing.GroupLayout(jpTeam);
        jpTeam.setLayout(jpTeamLayout);
        jpTeamLayout.setHorizontalGroup(
            jpTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpJob1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpHR1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpTeamLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpSalary1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jpTeamLayout.setVerticalGroup(
            jpTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpJob1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpHR1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpTeamLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpSalary1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout jpBackgroundLayout = new javax.swing.GroupLayout(jpBackground);
        jpBackground.setLayout(jpBackgroundLayout);
        jpBackgroundLayout.setHorizontalGroup(
            jpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpJob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpHR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpBackgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpSalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpBackgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpTeam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jpBackgroundLayout.setVerticalGroup(
            jpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpJob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpHR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpBackgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpSalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpBackgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpTeam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jmSystem.setText("System");

        jmiHome.setText("Home");
        jmiHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiHomeActionPerformed(evt);
            }
        });
        jmSystem.add(jmiHome);

        jmiLogout.setText("Log out");
        jmSystem.add(jmiLogout);

        jmiExit.setText("Exit");
        jmSystem.add(jmiExit);

        jMenuBar1.add(jmSystem);

        jmHR.setText("HR");

        jmiDept.setText("Dept");
        jmiDept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiDeptActionPerformed(evt);
            }
        });
        jmHR.add(jmiDept);

        jmiRole.setText("Role");
        jmHR.add(jmiRole);

        jmiTeam.setText("Team");
        jmiTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiTeamActionPerformed(evt);
            }
        });
        jmHR.add(jmiTeam);

        jmiStaff.setText("Staff");
        jmiStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiStaffActionPerformed(evt);
            }
        });
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
        jmiTaskManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiTaskManagementActionPerformed(evt);
            }
        });
        jmJob.add(jmiTaskManagement);

        jMenuBar1.add(jmJob);

        jmSalary.setText("Salary");

        jmiStaffSalary.setText("Staff Salary");
        jmSalary.add(jmiStaffSalary);

        jmiCalculateSalary.setText("Calculate Salary");
        jmSalary.add(jmiCalculateSalary);

        jmiAttendanceTracking.setText("Attendance Tracking");
        jmiAttendanceTracking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAttendanceTrackingActionPerformed(evt);
            }
        });
        jmSalary.add(jmiAttendanceTracking);

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

    private void jmiTaskManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTaskManagementActionPerformed
       showPanel(jpTask);
    }//GEN-LAST:event_jmiTaskManagementActionPerformed

    private void jmiHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiHomeActionPerformed
    jpBackground.removeAll();
    jpBackground.setLayout(new BorderLayout());
    
    // Nếu có panel mặc định ban đầu, bạn add nó lại
   
    jpBackground.add(new Home(), BorderLayout.CENTER);

    jpBackground.revalidate();
    jpBackground.repaint();
    }//GEN-LAST:event_jmiHomeActionPerformed

    private void jmiDeptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiDeptActionPerformed
        showPanel(jpDept);
    }//GEN-LAST:event_jmiDeptActionPerformed

    private void jmiStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiStaffActionPerformed
        showPanel(jpStaff);
    }//GEN-LAST:event_jmiStaffActionPerformed

    private void jmiAttendanceTrackingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAttendanceTrackingActionPerformed
        // TODO add your handling code here:
        showPanel(jpSalary);
    }//GEN-LAST:event_jmiAttendanceTrackingActionPerformed

    private void jmiTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTeamActionPerformed
       showPanel(jpTeam);
    }//GEN-LAST:event_jmiTeamActionPerformed

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
    private javax.swing.JMenuItem jmiAttendanceTracking;
    private javax.swing.JMenuItem jmiCalculateSalary;
    private javax.swing.JMenuItem jmiContract;
    private javax.swing.JMenuItem jmiDept;
    private javax.swing.JMenuItem jmiExit;
    private javax.swing.JMenuItem jmiHome;
    private javax.swing.JMenuItem jmiJobManagement;
    private javax.swing.JMenuItem jmiLogout;
    private javax.swing.JMenuItem jmiManual;
    private javax.swing.JMenuItem jmiRole;
    private javax.swing.JMenuItem jmiStaff;
    private javax.swing.JMenuItem jmiStaffSalary;
    private javax.swing.JMenuItem jmiTaskManagement;
    private javax.swing.JMenuItem jmiTeam;
    private javax.swing.JPanel jpAttTracking;
    private javax.swing.JPanel jpAttTracking1;
    private javax.swing.JPanel jpBackground;
    private javax.swing.JPanel jpDept;
    private javax.swing.JPanel jpDept1;
    private javax.swing.JPanel jpHR;
    private javax.swing.JPanel jpHR1;
    private javax.swing.JPanel jpJob;
    private javax.swing.JPanel jpJob1;
    private javax.swing.JPanel jpSalary;
    private javax.swing.JPanel jpSalary1;
    private javax.swing.JPanel jpStaff;
    private javax.swing.JPanel jpStaff1;
    private javax.swing.JPanel jpTask;
    private javax.swing.JPanel jpTask1;
    private javax.swing.JPanel jpTeam;
    // End of variables declaration//GEN-END:variables
}
