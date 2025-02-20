/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

/**
 *
 * @author Moiiii
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
//    private static final String URL = "jdbc:mysql://suv8q.h.filess.io:3307/companydata_mountainto"; 
//    private static final String USER = "companydata_mountainto";
//    private static final String PASSWORD = "2d26a5dedaad77c1f83b0e604581c9bb84d12ad9"; 
    
    private static final String URL = "jdbc:mysql://localhost:3306/companydata_mountainto";
    private static final String USER = "root";
    private static final String PASSWORD = "123123";


    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Ket noi CDSL thanh cong");
        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay Driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Ket noi CSDL that bai.");
            e.printStackTrace();
        }
        return connection;
    }

        public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Dong ket noi co so du lieu.");
            } catch (SQLException e) {
                System.out.println("Loi khi dong ket noi co so du lieu.");
                e.printStackTrace();
            }
        }
    } 
       
}

