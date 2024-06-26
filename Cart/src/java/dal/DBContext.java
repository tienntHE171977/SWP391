/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Vu Luong Bao
 */
public class DBContext {

    protected Connection connection;

    public DBContext() {
        try {
// Edit URL , username, password to authenticate with your MS SQL Server
            String url = "jdbc:sqlserver://localhost:1433;databaseName= thachthat9999";
            String username = "sa";
            String password = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }
    
public static void main(String[] args) {
        // Thay đổi thông tin kết nối dựa trên cấu hình của cơ sở dữ liệu của bạn
        String url = "jdbc:sqlserver://localhost:1433;databaseName= thachthat9999";
        String username = "sa";
        String password = "123";

        Connection connection = null;

        try {
            // Thử kết nối đến cơ sở dữ liệu
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối đến cơ sở dữ liệu thành công!");
        } catch (SQLException e) {
            System.out.println("Kết nối đến cơ sở dữ liệu thất bại!");
            e.printStackTrace();
        } finally {
            // Đóng kết nối sau khi hoàn thành
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
