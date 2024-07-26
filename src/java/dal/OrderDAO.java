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
import model.Order;

/**
 *
 * @author pc
 */
public class OrderDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public void insertOrderDetail(String orderDate, int userID, int total_cost, String phone, String fullName, String address) {
        String query = " INSERT INTO [dbo].[Order]\n"
                + "           ([orderDate]\n"
                + "           ,[total_cost]\n"
                + "           ,[fullName]\n"
                + "           ,[phone]\n"
                + "           ,[address]\n"
                + "           ,[userId])\n"
                + "           ,[status_order]\n"
                + "     VALUES (?, ?, ?, ?, ?, ?)";

        try {
            ps = connection.prepareStatement(query);

            ps.setString(1, orderDate);
            ps.setInt(2, total_cost);
            ps.setString(3, fullName);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setInt(6, userID);
            ps.setInt(7, 1);

            ps.executeUpdate();

        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    public int getCountOrderDetail() {
        int count = 0;

        String query = "SELECT top 1 order_id FROM [dbo].[Order] order by order_id desc";

        try {
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return count;
    }
        public int getCountOrder() {
        int count = 0;

        String query = "SELECT COUNT(order_id) from Order";

        try {
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return count;
    }

    public Order getOrderDetailByID(int orderIDnew) {
        String query = "SELECT *\n"
                + "  FROM [dbo].[Order]\n"
                + "  where order_id = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderIDnew);
            rs = ps.executeQuery();
            if (rs.next()) {
                Order od = new Order(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9));
                return od;
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }
        return null;
    }
       public List<Order> getOrderDByDate(String date) {
           List<Order> list = new ArrayList<>();
        String query = "SELECT *\n"
                + "  FROM [dbo].[Order]\n"
                + "  where orderDate = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, date);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order od = new Order(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9));
                list.add(od);
            }
            return list;

        } catch (SQLException exception) {
            System.out.println(exception);
        }
        return null;
    }

    public void updateOrderDetaolByOrderID(int orderID, String location, int status, String comment) {
        String query = "UPDATE [dbo].[Order]\n"
                + "   SET \n"
                + "      [address] = ?\n"
                + "      ,[status_order] = ?\n"
                + "      ,[note] = ?\n"
                + " WHERE order_id=?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, location);
            ps.setInt(2, status);
            ps.setString(3, comment);
            ps.setInt(4, orderID);

            ps.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    public void deleteOrderDetailByOrderDetailId(int orderDetailID) {

        String query1 = "  delete from Order_Detail\n"
                + "  where order_id = ?";

        try {
            ps = connection.prepareStatement(query1);

            ps.setInt(1, orderDetailID);

            ps.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
        String query2 = "  delete  from Order\n"
                + "  order_id = ?";

        try {
            ps = connection.prepareStatement(query2);

            ps.setInt(1, orderDetailID);

            ps.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    public void updateStatusOrderDetaolByOrderID(int orderDetailID, int status) {
        String query = "UPDATE [dbo].[Order]\n"
                + "   SET [status] = ?\n"
                + " WHERE order_id = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, status);
            ps.setInt(2, orderDetailID);
            ps.executeUpdate();

        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    public void updateLocationByID(int orderID, String location) {
        String query = "UPDATE [dbo].[Order]\n"
                + "   SET [LocationOrder] = ?\n"
                + " WHERE order_id = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, location);
            ps.setInt(2, orderID);
            ps.executeUpdate();

        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

}
