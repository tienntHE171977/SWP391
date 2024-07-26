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
import model.BillDetail;

/**
 *
 * @author pc
 */
public class BillDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<BillDetail> getBillByID(int orderDetailId) {
        List<BillDetail> list = new ArrayList<>();

        String query = " select o.order_id,p.product_name,p.sale_prices,o.quantity,(p.sale_prices*o.quantity) as[Money],od.orderDate from Order_Detail o\n"
                + "join Product p on p.product_id=o.product_id\n"
                + "join [Order] od on od.order_id=o.order_id\n"
                + "where od.order_id=?";

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, orderDetailId);

            rs = ps.executeQuery();

            while (rs.next()) {
                BillDetail billDetail = new BillDetail(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5), rs.getString(6));

                list.add(billDetail);
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return list;
    }

}
