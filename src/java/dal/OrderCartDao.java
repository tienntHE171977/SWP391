/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.OrderCart;

/**
 *
 * @author admin
 */
public class OrderCartDao extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<OrderCart> getAllOrderCart() {

        List<OrderCart> list = new ArrayList<>();

        String query = "  select o.order_id,u.FirstName+u.LastName as FullName,o.address,sum(od.quantity*p.sale_prices) as [Total],o.status_order,o.note,o.userId from [Order] o\n"
                + "join Users u on o.userId=u.UserID\n"
                + "join Order_Detail od on od.order_id =o.order_id\n"
                + "join Product p on od.product_id=p.product_id\n"
                + "group by o.order_id,u.FirstName + u.LastName, \n"
                + "o.address,o.status_order,o.note,o.userId";

        try {
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                OrderCart oc = new OrderCart(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDouble(4), rs.getInt(5), rs.getString(6), rs.getInt(7));

                list.add(oc);

            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return list;
    }

    public List<OrderCart> getAllOrderCartbyDay(String date) {

        List<OrderCart> list = new ArrayList<>();

        String query = "  select o.order_id,u.FirstName+u.LastName as FullName,o.address,sum(od.quantity*p.sale_prices) as [Total],o.status_order,o.note,o.userId from [Order] o\n"
                + "join Users u on o.userId=u.UserID\n"
                + "join Order_Detail od on od.order_id =o.order_id\n"
                + "join Product p on od.product_id=p.product_id\n"
                + "where o.orderDate =?\n"
                + "group by o.order_id,u.FirstName + u.LastName, \n"
                + "o.address,o.status_order,o.note,o.userId";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, date);
            rs = ps.executeQuery();
                
            while (rs.next()) {
               

                OrderCart oc = new OrderCart(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDouble(4), rs.getInt(5), rs.getString(6), rs.getInt(7));

                list.add(oc);

            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return list;
    }

    public String getNameByUserID(int userID) {

        AccountDAO aO = new AccountDAO();

        Account account = aO.getAccountByUserID(userID);

        return account.getFirstName() + " " + account.getLastName();
    }

    public double getTotalProductByOrderID(int orderDetailID) {

        String query = "select sum(o.quantity*p.sale_prices) from Order_Detail o\n"
                + "join Product p on o.product_id=p.product_id\n"
                + "where o.order_id=?";

        double sum = 0;

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, orderDetailID);

            rs = ps.executeQuery();

            if (rs.next()) {
                sum = rs.getDouble(1);
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return sum;
    }

    public static void main(String[] args) {
        OrderCartDao cartDao = new OrderCartDao();

        List<OrderCart> list = cartDao.getAllOrderCart();

        for (OrderCart orderCart : list) {
            System.out.println(orderCart.toString());
        }

    }

    public List<OrderCart> getOrderCartByName(String fullname) {

        List<OrderCart> list = new ArrayList<>();

        String query = " select o.order_id,u.FirstName+u.LastName as FullName,o.address,sum(od.quantity*p.sale_prices) as [Total],o.status_order,o.note,o.userId from [Order] o\n"
                + "join Users u on o.userId=u.UserID\n"
                + "join Order_Detail od on od.order_id =o.order_id\n"
                + "join Product p on od.product_id=p.product_id\n"
                + "where fullName like ?\n"
                + "group by o.order_id,u.FirstName + u.LastName, \n"
                + "o.address,o.status_order,o.note,o.userId";

        try {
            ps = connection.prepareStatement(query);

            ps.setString(1, "%" + fullname + "%");

            rs = ps.executeQuery();

            while (rs.next()) {

                OrderCart oc = new OrderCart(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDouble(4), rs.getInt(5), rs.getString(6), rs.getInt(7));

                list.add(oc);

            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return list;
    }

    public List<OrderCart> getAllOrderCartByUserID(int userID) {
        List<OrderCart> list = new ArrayList<>();

        String query = "  select o.order_id,u.FirstName+u.LastName as FullName,o.address,sum(od.quantity*p.sale_prices) as [Total],o.status_order,o.note,o.userId from [Order] o\n"
                + "join Users u on o.userId=u.UserID\n"
                + "join Order_Detail od on od.order_id =o.order_id\n"
                + "join Product p on od.product_id=p.product_id\n"
                + "where u.UserID like ?\n"
                + "group by o.order_id,u.FirstName + u.LastName, \n"
                + "o.address,o.status_order,o.note,o.userId";

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, userID);

            rs = ps.executeQuery();

            while (rs.next()) {

                OrderCart oc = new OrderCart(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDouble(4), rs.getInt(5), rs.getString(6), rs.getInt(7));

                list.add(oc);

            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return list;
    }

    public List<OrderCart> getOrderCartBySatus(int statusSearch) {
        List<OrderCart> list = new ArrayList<>();

        String query = "select o.order_id,u.FirstName+u.LastName as FullName,o.address,sum(od.quantity*p.sale_prices) as [Total],o.status_order,o.note,o.userId from [Order] o\n"
                + "join Users u on o.userId=u.UserID\n"
                + "join Order_Detail od on od.order_id =o.order_id\n"
                + "join Product p on od.product_id=p.product_id\n"
                + "where o.status_order like ?\n"
                + "group by o.order_id,u.FirstName + u.LastName, \n"
                + "o.address,o.status_order,o.note,o.userId";

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, statusSearch);

            rs = ps.executeQuery();

            while (rs.next()) {

                OrderCart oc = new OrderCart(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDouble(4), rs.getInt(5), rs.getString(6), rs.getInt(7));

                list.add(oc);

            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return list;

    }

    public List<OrderCart> getAllOrderCartByUserIDAndStatus(int userID, int status) {
        List<OrderCart> list = new ArrayList<>();

        String query = "  select o.order_id,u.FirstName+u.LastName as FullName,o.address,sum(od.quantity*p.sale_prices) as [Total],o.status_order,o.note,o.userId from [Order] o\n"
                + "join Users u on o.userId=u.UserID\n"
                + "join Order_Detail od on od.order_id =o.order_id\n"
                + "join Product p on od.product_id=p.product_id\n"
                + "where o.status_order like ? and u.UserID=?\n"
                + "group by o.order_id,u.FirstName + u.LastName, \n"
                + "o.address,o.status_order,o.note,o.userId";

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, userID);

            ps.setInt(2, status);

            rs = ps.executeQuery();

            while (rs.next()) {

                OrderCart oc = new OrderCart(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDouble(4), rs.getInt(5), rs.getString(6), rs.getInt(7));

                list.add(oc);

            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return list;
    }

}
