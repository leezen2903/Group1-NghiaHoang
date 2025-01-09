/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package kase.aptechsaigon.projectsem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class ProjectSem2 {
    // Phương thức tạo người dùng
    public static void createUser(String username, String password, String fullName) {
        String sql = "INSERT INTO user (username, password, fullName) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, fullName);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Thêm người dùng thành công!" : "Thêm thất bại.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức đọc danh sách người dùng
    public static void readUsers() {
        String sql = "SELECT * FROM user";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.printf("%-5s %-20s %-20s\n", "ID", "Username", "Full Name");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-20s\n",
                        rs.getInt("id"), rs.getString("username"), rs.getString("fullName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức cập nhật người dùng
    public static void updateUser(int id, String fullName) {
        String sql = "UPDATE user SET fullName = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Cập nhật thành công!" : "Không tìm thấy người dùng.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức xóa người dùng
    public static void deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Xóa người dùng thành công!" : "Không tìm thấy người dùng.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức chính để chạy chương trình
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChọn chức năng:");
            System.out.println("1. Thêm người dùng");
            System.out.println("2. Xem danh sách người dùng");
            System.out.println("3. Cập nhật thông tin người dùng");
            System.out.println("4. Xóa người dùng");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Đọc bỏ dòng dư thừa

            switch (choice) {
                case 1:
                    System.out.print("Nhập username: ");
                    String username = scanner.nextLine();
                    System.out.print("Nhập password: ");
                    String password = scanner.nextLine();
                    System.out.print("Nhập fullName: ");
                    String fullName = scanner.nextLine();
                    createUser(username, password, fullName);
                    break;
                case 2:
                    readUsers();
                    break;
                case 3:
                    System.out.print("Nhập ID người dùng: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine();  // Đọc bỏ dòng dư thừa
                    System.out.print("Nhập tên đầy đủ mới: ");
                    String newFullName = scanner.nextLine();
                    updateUser(idToUpdate, newFullName);
                    break;
                case 4:
                    System.out.print("Nhập ID người dùng cần xóa: ");
                    int idToDelete = scanner.nextInt();
                    deleteUser(idToDelete);
                    break;
                case 5:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}