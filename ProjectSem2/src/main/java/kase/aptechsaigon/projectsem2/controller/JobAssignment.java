package kase.aptechsaigon.projectsem2.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kase.aptechsaigon.projectsem2.ConnectDatabase;

public class JobAssignment {
    // thêm phân công công việc cho nhóm
    public static void addAssignment(int jobId, int teamId) {
        String sql = "INSERT INTO Assignments (JobID, TeamID) VALUES (?, ?)";
        Connection conn = ConnectDatabase.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jobId);
            pstmt.setInt(2, teamId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDatabase.closeConnection(conn);
        }
    }

    // sửa phân công công việc cho nhóm
    public static void editAssignment(int assignmentId, int newTeamId) {
        String sql = "UPDATE Assignments SET TeamID = ? WHERE AssignmentID = ?";
        Connection conn = ConnectDatabase.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newTeamId);
            pstmt.setInt(2, assignmentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDatabase.closeConnection(conn);
        }
    }

    // xóa phân công công việc
    public static void deleteAssignment(int assignmentId) {
        String sql = "DELETE FROM Assignments WHERE AssignmentID = ?";
        Connection conn = ConnectDatabase.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, assignmentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDatabase.closeConnection(conn);
        }
    }
}
