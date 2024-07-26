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

/**
 *
 * @author pc
 */
public class AccountDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Account> getAllAccount() {
        List<Account> list = new ArrayList<>();
        String query = "select * from Users";

        try {
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9));
                list.add(account);

            }
            return list;

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return null;
    }
  public Account getAccountByOrderDetailId(int orderDetailId) {
        String query = "  select Users.* from Order\n"
                + "  join Users on Order.userId = Users.UserID\n"
                + "  where order_id= ?";

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, orderDetailId);

            rs = ps.executeQuery();

            if (rs.next()) {
                Account a = new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9));
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return null;
    }
    public Account getAccountByUserAndPass(String user, String password) {

        String query = "select * from Users\n"
                + "  where Username = ? and Password = ?";

        try {
            ps = connection.prepareStatement(query);

            ps.setString(1, user);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9));

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
                return new Account(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9));
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
                return new Account(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9));
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
                account = new Account(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return account != null;
    }

    public void insertNewAccount(String firstName, String lastName, String email,
            String password, String phone, String address, int roleId, String username) {
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

    public void UpdateAccount(String fname, String lname, String address, int role,int userId) {

        String query = "UPDATE [dbo].[Users]\n"
                + "   SET [FirstName] = ?\n"
                + "      ,[LastName] = ?\n"
                + "      ,[Address] = ?\n"
                + "      ,[roleid] = ?\n"
                + " WHERE UserID = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, address);
            ps.setInt(4, role);
            ps.setInt(5, userId);
            ps.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }
  public void UpdateRoleAccount( int role,int userId) {

        String query = "UPDATE [dbo].[Users]\n"

                + "     SET [roleid] = ?\n"
                + " WHERE UserID = ?";

        try {
            ps = connection.prepareStatement(query);
       
            ps.setInt(1, role);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }
    public int countAccount() {
        int count = 0;

        String query = "SELECT COUNT(UserID) from Users";

        try {
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }
        return count;
    }

    public void updateAccount(int userId, String fName, String lName,
            String email, String phone, String address, String Username) {

        String query = "UPDATE [dbo].[Users]\n"
                + "   SET [FirstName] = ?\n"
                + "      ,[LastName] = ?\n"
                + "      ,[Email] = ?\n"
                + "      ,[PhoneNumber] = ?\n"
                + "      ,[Address] = ?\n"
                + " WHERE UserID =?";

        try {
            ps = connection.prepareStatement(query);

            ps.setString(1, fName);
            ps.setString(2, lName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, address);

            ps.setInt(6, userId);

            ps.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    public static void main(String[] args) {
        AccountDAO ad = new AccountDAO();
        List<Account> list = ad.getAllAccount();
        System.out.println(list.get(0).getFirstName());
    }
    //
}
