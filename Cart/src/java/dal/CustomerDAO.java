/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Customer;

/**
 *
 * @author Admin
 */
public class CustomerDAO extends DBContext{
    public List<Customer> getAllUsers() {
        List<Customer> userList = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Customer]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("userId"),
                        rs.getString("fullName"),
                        rs.getString("avatar"),
                        rs.getBoolean("gender"),
                        rs.getString("password"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBoolean("roleId"),
                        rs.getBoolean("status")
                );
                userList.add(customer);
            }
        } catch (SQLException e) {
            // Xử lý exception tùy theo yêu cầu
        }
        return userList;
    }

    public Customer checkLogin(String name, String password) {
        String sql = "SELECT *\n"
                + "  FROM [dbo].[Customer]\n"
                + "  where user_name=? and password=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("userId"),
                        rs.getString("fullName"),
                        rs.getString("avatar"),
                        rs.getBoolean("gender"),
                        rs.getString("password"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBoolean("roleId"),
                        rs.getBoolean("status")
                );
            }
        } catch (Exception e) {
        }
        return null;
    }

}
