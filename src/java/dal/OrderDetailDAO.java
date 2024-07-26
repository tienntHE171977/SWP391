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
import model.Item;
import model.Product;

/**
 *
 * @author pc
 */
public class OrderDetailDAO extends DBContext {

    protected PreparedStatement ps = null;
    protected ResultSet rs = null;

    public void insertOrderItem(int product_price, int orderDetailID, int productID, int quanity, int total_cost) {
        String query = "INSERT INTO [dbo].[Order_Detail]\n"
                + "           ([product_price]\n"
                + "           ,[quantity]\n"
                + "           ,[order_id]\n"
                + "           ,[product_id]\n"
                + "           ,[total_cost])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?)";

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, product_price);
            ps.setInt(2, quanity);
            ps.setInt(3, orderDetailID);
            ps.setInt(4, productID);
            ps.setInt(5, total_cost);

            ps.executeUpdate();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }

    public List<Item> getListItemByOrderDetailId(int orderIDnew) {
        String query = "select Product.*,Order_Detail.quantity from Order_Detail\n"
                + "join Product on Order_Detail.product_id =Product.product_id\n"
                + "where Order_Detail.order_id =?";

        List<Item> list = new ArrayList<>();

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderIDnew);

            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                ProductImageDAO imageDAO = new ProductImageDAO();
                List<String> images = imageDAO.getImagesByProductId(productId);
                Product p = new Product(rs.getInt(1), rs.getString(15), rs.getInt(2), rs.getBoolean(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getBoolean(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getDate(12), rs.getInt(13), rs.getInt(14), images);

                int quanity = rs.getInt(16);

                list.add(new Item(p, quanity, p.getOriginalPrice()));
            }
            return list;
        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return null;
    }

    public static void main(String[] args) {
        OrderDetailDAO odd = new OrderDetailDAO();
        odd.insertOrderItem(1000, 2, 4, 1, 1500000);
    }
}
