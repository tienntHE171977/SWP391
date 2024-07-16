/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Category;
import model.Trademark;

/**
 *
 * @author FPT
 */
public class TrademarkDAO extends DBContext{
    public ArrayList<Trademark> getAllTrademark() {
        ArrayList<Trademark> list = new ArrayList<>();
        String sql = "select * from Trademark";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int trademarkId = rs.getInt(1);
                
               
                String trademarktName = rs.getString(2);
                int trademarktStatus = rs.getInt(3);
                
                
                Trademark tra = new Trademark(trademarkId, trademarktName,trademarktStatus);

                list.add(tra);

            }

        } catch (SQLException e) {

        }
        return list;
    }
    
//    public List<Category> getCategoriesWithTrademarks() {
//    List<Category> categories = new ArrayList<>();
//    String sql = "SELECT c.category_id, c.category_name, t.trademark_id, t.trademark_name " +
//                 "FROM Category c " +
//                 "LEFT JOIN TrademarkCategory tc ON c.category_id = tc.category_id " +
//                 "LEFT JOIN Trademark t ON tc.trademark_id = t.trademark_id";
//
//    try (PreparedStatement stmt = connection.prepareStatement(sql);
//         ResultSet rs = stmt.executeQuery()) {
//
//        Map<Integer, Category> categoryMap = new HashMap<>();
//
//        while (rs.next()) {
//            int categoryId = rs.getInt("category_id");
//            String categoryName = rs.getString("category_name");
//            int trademarkId = rs.getInt("trademark_id");
//            String trademarkName = rs.getString("trademark_name");
//           
//
//            Category category = categoryMap.computeIfAbsent(categoryId, id -> new Category(id, categoryName)); // Kiểm tra đã có Category này chưa
//
//            if (!rs.wasNull()) {
//                category.getTrademarks().add(new Trademark(trademarkId, trademarkName));
//            }
//        }
//
//        return new ArrayList<>(categoryMap.values());
//
//    } catch (SQLException e) {
//        // Xử lý ngoại lệ
//        e.printStackTrace(); 
//        return Collections.emptyList(); 
//    }
//}



}
