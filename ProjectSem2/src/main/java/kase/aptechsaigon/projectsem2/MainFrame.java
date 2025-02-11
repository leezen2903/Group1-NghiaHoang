package kase.aptechsaigon.projectsem2;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
         setTitle("Ứng dụng Quản Lý Nhân Viên");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        //Tạo MENU Bar:
         // Tạo thanh menu bar
        JMenuBar menuBar = new JMenuBar();

    // Tạo menu "Account"
    JMenu mnuAcc = new JMenu("Account");
    JMenuItem mnuiViewAcc = new JMenuItem("Information");
    JMenuItem mnuiEditAcc = new JMenuItem("Edit");
    mnuAcc.add(mnuiViewAcc);
    mnuAcc.add(mnuiEditAcc);
    menuBar.add(mnuAcc);

    // Tạo menu "HR"
    JMenu mnuHR = new JMenu("HR");
    JMenu mnuDepartment = new JMenu("Department");
    JMenuItem mnuiViewDept = new JMenuItem("View Departments List");
    JMenuItem mnuiAddDept = new JMenuItem("Add Department");
    JMenuItem mnuiEditDept = new JMenuItem("Edit Department");
    JMenuItem mnuiDelDept = new JMenuItem("Delete Department");

    mnuDepartment.add(mnuiViewDept);
    mnuDepartment.add(mnuiAddDept);
    mnuDepartment.add(mnuiEditDept);
    mnuDepartment.add(mnuiDelDept);
    mnuHR.add(mnuDepartment);
    menuBar.add(mnuHR);

    // Tạo menu "Job"
    JMenu mnuJob = new JMenu("Job");
    JMenuItem mnuJobList = new JMenuItem("Job List");
    JMenuItem mnuJobAssignment = new JMenuItem("Job Assignment");
    JMenuItem mnuTaskManagement = new JMenuItem("Task Management");
    JMenuItem mnuTaskReport = new JMenuItem("Task Report");

    mnuJob.add(mnuJobList);
    mnuJob.add(mnuJobAssignment);
    mnuJob.add(mnuTaskManagement);
    mnuJob.add(mnuTaskReport);
    menuBar.add(mnuJob);

    // Tạo menu "Statistic"
    JMenu mnuStatistical = new JMenu("Statistic");
    JMenuItem mnuiS_Salary = new JMenuItem("Salary Statistic");
    JMenuItem mnuiS_Job = new JMenuItem("Job Statistic");
    JMenuItem mnuiS_HR = new JMenuItem("HR Statistic");

    mnuStatistical.add(mnuiS_Salary);
    mnuStatistical.add(mnuiS_Job);
    mnuStatistical.add(mnuiS_HR);
    menuBar.add(mnuStatistical);

    // Tạo menu "System"
        JMenu mnuSystem = new JMenu("System");
        JMenuItem mnuiInfo = new JMenuItem("Info");
        JMenuItem mnuiLogout = new JMenuItem("Logout");
        JMenuItem mnuiExit = new JMenuItem("Exit App");

    mnuSystem.add(mnuiInfo);
    mnuSystem.add(mnuiLogout);
    mnuSystem.add(mnuiExit);
    menuBar.add(mnuSystem);

    // Đặt menu bar cho MainFrame
    setJMenuBar(menuBar);
        
        JLabel companyName = new JLabel("SQUARE ENIX", SwingConstants.CENTER);
        companyName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        
        JLabel slogan = new JLabel("Welcome to SQUARE ENIX Company!", SwingConstants.CENTER);
        slogan.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));
        slogan.setForeground(new java.awt.Color(102, 102, 102));
        
         // Tạo panel chứa 2 dòng chữ này
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(companyName, BorderLayout.NORTH);
        headerPanel.add(slogan, BorderLayout.CENTER);

        // Thêm vào giao diện MainFrame (ở trên cùng)
        add(headerPanel, BorderLayout.NORTH);

    
        //Tạo 4 nút
        JButton btnNhanSu = new JButton("Nhân sự");
        JButton btnCongViec = new JButton("Công việc");
        JButton btnTask = new JButton("Task");
        JButton btnBoPhan = new JButton("Bộ phận");

        
        //Gán event cho từng nút khi click vào
//        btnNhanSu.addActionListener(e -> openFrame(new HRLayout()));
//        btnCongViec.addActionListener(e -> openFrame(new Job()));
//        btnTask.addActionListener(e -> openFrame(new Task()));
//        btnBoPhan.addActionListener(e -> openFrame(new AddEmployee()));
         
        
        //Thêm các nút vào panel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(btnNhanSu, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(btnCongViec, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(btnTask, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(btnBoPhan, gbc);
        
        //Thêm panel vào mainframe
        JPanel container = new JPanel(new GridBagLayout());
        container.add(panel);
        add(container, BorderLayout.CENTER);
        //HIển thị cửa sổ
        setVisible(true);
    }
     private void openFrame(JFrame frame) {
        frame.setVisible(true);
    }
    
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

 
    
    
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
     /*    SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
        /* Create and display the form */
       /* java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        }); */
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
