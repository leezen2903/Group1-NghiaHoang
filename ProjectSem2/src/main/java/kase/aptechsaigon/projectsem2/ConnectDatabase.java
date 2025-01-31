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
    private static final String URL = "jdbc:mysql://localhost:3306/companyData"; 
    private static final String USER = "root";
    private static final String PASSWORD = "123123"; 

    // Phương thức kết nối cơ sở dữ liệu
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy Driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Kết nối cơ sở dữ liệu thất bại.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                // Đóng kết nối
                connection.close();
                System.out.println("Đóng kết nối cơ sở dữ liệu.");
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng kết nối cơ sở dữ liệu.");
                e.printStackTrace();
            }
        }
    }
}

