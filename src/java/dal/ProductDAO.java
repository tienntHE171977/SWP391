/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Product;

/**
 *
 * @author FPT
 */
public class ProductDAO extends DBContext{
    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "select * from Product";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
//            private int productId;
//    private String productName;
//    private int originalPrice;
//    private boolean sale;
//    private int salePrice;
//    private String productHighlights;
//    private String productDescription;
//    private int trademarkId;
//    private boolean status;
//    private int quantity;
//    private int guarantee;
//    private int categoryId;
//    private Date updateDate;
//    private int sole; 
//    private int avrRatedStar;
            while (rs.next()) {
                int productId = rs.getInt(1);
                
                int originalPrice = rs.getInt(2);
                boolean sale = rs.getBoolean(3);
                int salePrice = rs.getInt(4);
                String productHighlights = rs.getString(5);
                String productDescription = rs.getString(6);
                int trademarkId = rs.getInt(7);
                boolean status = rs.getBoolean(8);
                int quantity = rs.getInt(9);
                int guarantee = rs.getInt(10);
                int categoryId = rs.getInt(11);
                Date updateDate = rs.getDate(12);
                int sole = rs.getInt(13);
                int avrRatedStar = rs.getInt(14);
                String productName = rs.getString(15);
                
                
                Product pt = new Product(productId, productName, originalPrice, sale, salePrice, productHighlights, productDescription, trademarkId, status, quantity, guarantee, categoryId, updateDate, sole, avrRatedStar);

                list.add(pt);

            }

        } catch (SQLException e) {

        }
        return list;
    }
}
