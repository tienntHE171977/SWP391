/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Account;

/**
 *
 * @author pc
 */
public class AccountDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public Account getAccountByUserAndPass(String user, String password) {

        String query = "select * from Users\n"
                + "  where email = ? and password = ?";

        try {
            ps = connection.prepareStatement(query);

            ps.setString(1, user);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getInt(8));

                return account;
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return null;
    }

    public Account getAccountByUserID(int userID) {
        String query = "select * from Users\n"
                + "  where UserID = ?;";

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, userID);

            rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return null;
    }

    public Account getAccountByEmail(String email) {
        String query = "  select * from Users where Users.Email = ?";

        try {
            ps = connection.prepareStatement(query);

            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return null;
    }

    public boolean checkUserDupplicate(String userName) {

        Account account = null;

        String query = "select * from Users\n"
                + "where Username = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, userName);
            rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return account != null;
    }

    public void insertNewAccount(String firstName, String lastName, String email,
            String password, String phone, String address, int roleId,String username) {
        String query = "INSERT INTO [dbo].[Users]\n"
                + "           ([FirstName]\n"
                + "           ,[LastName]\n"
                + "           ,[Email]\n"
                + "           ,[Password]\n"
                + "           ,[PhoneNumber]\n"
                + "           ,[Address]\n"
                + "           ,[roleid]\n"
                + "           ,[Username])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?)";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, phone);
            ps.setString(6, address);
            
            ps.setInt(7, roleId);
            ps.setString(8, username);
            ps.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }
       public void UpdatePassAccount(Account acc, String newPass) {
        int userID = acc.getUserId();

        String query = "UPDATE [dbo].[Users]\n"
                + "SET [Password] = ?\n"
                + "where Users.UserID = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, newPass);
            ps.setInt(2, userID);
            ps.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }
    public static void main(String[] args) {
        AccountDAO ad = new AccountDAO();
        ad.insertNewAccount("Tien", "manh", "truongk3703", "12345678", "0397139645", "ha noi", 2, "manh123");
    }
    //
}
