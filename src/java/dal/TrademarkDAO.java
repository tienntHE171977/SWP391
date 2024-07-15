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
                
                
                Trademark tra = new Trademark(trademarkId, trademarktName, trademarktStatus);

                list.add(tra);

            }

        } catch (SQLException e) {

        }
        return list;
    }
    
     public List<Trademark> getTrademarkByCategory(int categoryId) {
        List<Trademark> list = new ArrayList<>();
        String sql = "SELECT * FROM Trademark WHERE category_id = ? AND status = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            
            st.setInt(1, categoryId);
            ResultSet rs = st.executeQuery();
            
                while (rs.next()) {
                    int trademarkId = rs.getInt("trademark_id");
                    String trademarktName = rs.getString("trademark_name");
                    int trademarktStatus = rs.getInt("status");

                    Trademark tra = new Trademark(trademarkId, trademarktName, trademarktStatus);
                    list.add(tra);
                }
            

        } catch (SQLException e) {

        }
        return list;
    }
}
