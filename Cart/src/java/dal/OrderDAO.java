/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.Customer;
import model.Item;
import model.Order;

/**
 *
 * @author Admin
 */
public class OrderDAO extends DBContext{
        public List<Order> findAll() {
        List<Order> orderDetailList = new ArrayList<>();
        String sql = "SELECT *\n"
                + "  FROM [thachthat9999].[dbo].[Order] order by id desc";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order orders = new Order(
                        rs.getInt("order_id"),
                        rs.getString("orderDate"),
                        rs.getInt("total_cost"),
                        rs.getString("fullName"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("status_order"),
                        rs.getInt("userId"),
                        rs.getInt("saler_id"),
                        rs.getString("note")
                );
                orderDetailList.add(orders);
            }
        } catch (SQLException e) {
            // Xử lý exception tùy theo yêu cầu
        }
        return orderDetailList;

    }

    public List<Order> findAll1() {
        List<Order> orderDetailList = new ArrayList<>();
        String sql = "SELECT o.order_id, o.userId, u.fullName, o.orderDate, o.status_order, o.total_cost\n"
                + "FROM [thachthat9999].[dbo].[Order] o\n"
                + "JOIN [Customer] u ON o.userId = u.order_id";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order orders = new Order(
                        rs.getInt("order_id"),
                        rs.getString("orderDate"),
                        rs.getInt("total_cost"),
                        rs.getString("fullName"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("status_order"),
                        rs.getInt("userId"),
                        rs.getInt("saler_id"),
                        rs.getString("note")
                );
                orderDetailList.add(orders);
            }
        } catch (SQLException e) {
            // Xử lý exception tùy theo yêu cầu
        }

        return orderDetailList;
    }

    public void insertOrders(Customer u, Cart cart) {
        LocalDateTime curDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDateTime = curDateTime.format(formatter);

        String sql = "INSERT INTO [dbo].[Order]\n"
                + "           ([userId]\n"
                + "           ,[orderDate]\n"
                + "           ,[status_order]\n"
                + "           ,[total_cost]\n"
                + "     VALUES\n"
                + "           (?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, u.getUserId());
            st.setString(2, formattedDateTime);
            st.setInt(3, 1);
            st.setDouble(4, cart.getTotalMoney());
            st.setString(5, formattedDateTime);
            st.executeUpdate();

            String sql1 = "select top 1 id from [dbo].[Order] order by id desc";
            PreparedStatement st1 = connection.prepareStatement(sql1);
            ResultSet rs = st1.executeQuery();

            //add vảo bảng orders_detail
            if (rs.next()) {
                int order_id = rs.getInt(1);
                for (Item i : cart.getItems()) {
                    String sql2 = "INSERT INTO [dbo].[Order_Detail]\n"
                            + "           ([orderDetail_id]\n"
                            + "           ,[product_id]\n"
                            + "           ,[quantity]\n"
                            + "     VALUES\n"
                            + "           (?,?,?)";
                    PreparedStatement st2 = connection.prepareStatement(sql2);
                    st2.setInt(1, order_id);
                    st2.setInt(2, i.getProduct().getProduct_id());
                    st2.setInt(3, i.getQuantity());
                    st2.setString(4, formattedDateTime);
                    st2.executeUpdate();
                }
            }
        } catch (Exception e) {
        }
    }
    
}
