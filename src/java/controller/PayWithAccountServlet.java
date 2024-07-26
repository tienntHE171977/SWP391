/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.OrderDAO;
import dal.OrderDetailDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Account;
import model.Cart;
import model.Item;
import model.Order;
import model.Product;

/**
 *
 * @author pc
 */
@WebServlet(name="PayWithAccountServlet", urlPatterns={"/payWithAccountController"})
public class PayWithAccountServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PayWithAccountServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PayWithAccountServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
PrintWriter out = response.getWriter();
        Account account = (Account) session.getAttribute("acc");
        int total_cost;
        OrderDetailDAO od = new OrderDetailDAO();
        ProductDAO pd = new ProductDAO();
       String payMethod = request.getParameter("payment");
       OrderDAO odd = new OrderDAO();
       Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fName = request.getParameter("fname");
        String lName = request.getParameter("lname");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        int total= Integer.parseInt(request.getParameter("total"));
        String dateString = simpleDateFormat.format(d);
        odd.insertOrderDetail(dateString, account.getUserId(), total, phone, fName+" "+lName, address);
        
        Cart cart = (Cart) session.getAttribute("cart");
         int orderIDnew = odd.getCountOrderDetail();
         
//         out.println(dateString);
Order order = odd.getOrderDetailByID(orderIDnew);
//out.println(order.getFullName());
 for (Item item : cart.getItems()) {
            Product product = pd.getElementProductByPid(item.getProduct().getProductId());
            od.insertOrderItem(product.getOriginalPrice(),orderIDnew , product.getProductId(),item.getQuantity(),total);
        }
 List<Item> listItem = od.getListItemByOrderDetailId(orderIDnew);
        
      
             if(Integer.parseInt(payMethod)==1){
           
      request.setAttribute("amount", total);
        request.getRequestDispatcher("payment/index.jsp").forward(request, response);
            
        } else{
           request.setAttribute("payID", payMethod);
        request.setAttribute("orderDetail", order);
        request.setAttribute("listItem", listItem);
        request.setAttribute("acc", account);
        session.removeAttribute("cart");
        request.getRequestDispatcher("/Bill/bill.jsp").forward(request, response);
       }
       
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
