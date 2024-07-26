/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author FPT
 */
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductImageDAO extends DBContext {
      PreparedStatement ps = null;
    ResultSet rs = null;

    public List<String> getImagesByProductId(int productId) {
        List<String> images = new ArrayList<>();
        String sql = "SELECT images FROM Products_Images WHERE product_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, productId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    images.add(rs.getString("images"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    public void UpdateImage(String img, int productId) {

        String query = "UPDATE [dbo].[Products_Images]\n"
                + "   SET [status] = ?\n"
                + "      ,[images] = ?\n"
                + " WHERE product_id =?	";

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, 1);
            ps.setString(2, img);
            ps.setInt(3, productId);
            ps.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }
}
