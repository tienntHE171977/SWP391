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
import model.Account;
import model.Role;

/**
 *
 * @author pc
 */
public class RoleDAO extends DBContext{
      PreparedStatement ps = null;
    ResultSet rs = null;
     public List<Role> getAllAccount() {
            List<Role> list = new ArrayList<>();
        String query = "select * from Role";

        try {
            ps = connection.prepareStatement(query);

           

            rs = ps.executeQuery();

            while (rs.next()) {
                Role role = new Role(rs.getInt(1), rs.getString(2));
                list.add(role);

                
            }
            return list;

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return null;
    }
     public static void main(String[] args) {
         RoleDAO rd = new RoleDAO();
        List<Role> list = rd.getAllAccount();
         System.out.println(list.get(0).getRoleName());
    }
    
}
