/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.util.ArrayList;
import model.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author FPT
 */
public class CategoryDAO extends DBContext{
    public ArrayList<Category> getAllCategory() {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "select * from Category";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int categoryId = rs.getInt(1);
                
               
                String categoryName = rs.getString(2);
                
                
                Category ca = new Category(categoryId, categoryName);

                list.add(ca);

            }

        } catch (SQLException e) {

        }
        return list;
    }
    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categories = categoryDAO.getAllCategory();

        // In danh sách category ra console để kiểm tra
        for (Category category : categories) {
            System.out.println("Category ID: " + category.getCategoryId() + ", Name: " + category.getCategoryName());
        }
    }
}
