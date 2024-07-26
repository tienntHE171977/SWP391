/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.AccountDAO;
import dal.OrderCartDao;
import dal.OrderDAO;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import model.Account;
import model.OrderCart;

/**
 *
 * @author pc
 */
@WebServlet(name="OrderManagerServlet", urlPatterns={"/ordermanager"})
public class OrderManagerServlet extends HttpServlet {
   
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
            out.println("<title>Servlet OrderManagerServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderManagerServlet at " + request.getContextPath () + "</h1>");
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
          request.setCharacterEncoding("UTF-8");
        OrderCartDao ocd = new OrderCartDao();

        List<OrderCart> orderCarts = new ArrayList<>();

        String fullname = (request.getParameter("username") == null
                || (request.getParameter("username").equals("")) ? "" : (request.getParameter("username")));

        if (fullname.equals("")) {
            orderCarts = ocd.getAllOrderCart();
        } else {
            orderCarts = ocd.getOrderCartByName(fullname);
            
        }

        request.setAttribute("listOrderCart", orderCarts);

        request.getRequestDispatcher("admin/order.jsp").forward(request, response);
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
        OrderDAO oddao = new OrderDAO();

        String action = request.getParameter("action");

        OrderCartDao ocd = new OrderCartDao();

        List<OrderCart> orderCarts = new ArrayList<>();

        switch (action) {
            case "edit": {

                int orderID = Integer.parseInt(request.getParameter("orderID"));

                String location = request.getParameter("location");

                int status = Integer.parseInt(request.getParameter("status"));

                String comment = (request.getParameter("comment") == null || request.getParameter("comment").equals(""))
                        ? "" : (request.getParameter("comment"));

                oddao.updateOrderDetaolByOrderID(orderID, location, status, comment);

                orderCarts = ocd.getAllOrderCart();
                break;
            }

            case "delete": {
                int orderDetailID = Integer.parseInt(request.getParameter("orderDetailID"));

                oddao.deleteOrderDetailByOrderDetailId(orderDetailID);

                orderCarts = ocd.getAllOrderCart();
                break;
            }

            case "changeSatus": {
                int orderDetailID = Integer.parseInt(request.getParameter("orderDetailID"));
                int status = Integer.parseInt(request.getParameter("status"));
               
                String id = request.getParameter("userName");
                AccountDAO d = new AccountDAO();
                Account a = d.getAccountByUserID(Integer.parseInt(id));
                
               
                String mess;
                if(status==1){
                    mess = " được chấp nhận";
                } else{
                    mess = " bị hủy";
                }
                sendOTP(a.getEmail(), "Thông tin đơn hàng", mess);
                oddao.updateStatusOrderDetaolByOrderID(orderDetailID, status);
                orderCarts = ocd.getAllOrderCart();

                break;
            }

            case "searchByStatus": {

                int statusSearch = Integer.parseInt(request.getParameter("searchByStatus"));

                orderCarts = ocd.getOrderCartBySatus(statusSearch);
                request.setAttribute("status", statusSearch);

                
                break;
            }
        }

        request.setAttribute("listOrderCart", orderCarts);

        request.getRequestDispatcher("admin/order.jsp").forward(request, response);
    }
    public boolean sendOTP(String to, String subject, String code) {
        // Get properties object
        Properties props = new Properties();
         props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.debug", "true");

        // get Session
        Session session = Session.getDefaultInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("truongk3703@gmail.com", "ayap cpqw mcxa pqbf");
            }
        });

        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText("Đơn hàng của bạn đã  " + code);

            // send message
            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
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
