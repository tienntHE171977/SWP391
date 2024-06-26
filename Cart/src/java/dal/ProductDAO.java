/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 *
 * @author Admin
 */
public class ProductDAO extends DBContext{
    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT *\n"
                + "  FROM [dbo].[Product]"; 
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getBoolean(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12), 
                        rs.getString(13),
                        rs.getInt(14),
                        rs.getInt(15)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Product getProductById(int id) {
        String sql = "select * from Product where product_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getBoolean(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12), 
                        rs.getString(13),
                        rs.getInt(14),
                        rs.getInt(15)                       
                );

            }
        } catch (Exception e) {
        }
        return null;
    }
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.getAllProduct();
        Product p = dao.getProductById(1);
        System.out.println(p.getProduct_name());
        for (Product o : list) {
            System.out.println(o);
        }
    }

}
