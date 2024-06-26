/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Cart;
import model.Item;
import model.Product;

/**
 *
 * @author Admin
 */
public class process extends HttpServlet {
   
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
            out.println("<title>Servlet process</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet process at " + request.getContextPath () + "</h1>");
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
        HttpSession session = request.getSession(true);
        Cart cart = null;
        Object o = session.getAttribute("cart");
        if (o != null) {
            cart = (Cart) o;
        } else {
            cart = new Cart();
        }
        String tnum = request.getParameter("num").trim();
        String tid = request.getParameter("id");
        int id, num;
        ProductDAO pdb = new ProductDAO();
        Product p = pdb.getProductById(Integer.parseInt(tid));
//                 int availableQuantity = p.getQuantity();
        try {
            id = Integer.parseInt(tid);
            num = Integer.parseInt(tnum);
            if ((num == -1) && (cart.getQuantityById(id) <= 1)) {
                cart.removeItem(id);
            } else {

                double price = p.getOriginal_prices();
                Item t = new Item(p, num, price);
                cart.addItem(t);
            }
        } catch (Exception e) {
        }

        List<Item> list = cart.getItems();
//        session.setAttribute("FullQuantity",availableQuantity );
        session.setAttribute("cart", cart);
        session.setAttribute("size", list.size());
        System.out.println();
//        request.getRequestDispatcher("Cart.jsp").forward(request, response);
        response.sendRedirect("cart1.jsp");

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
        String service = request.getParameter("service");
        if (service.equals("delete")) {
            HttpSession session = request.getSession();
            Cart cart = null;

            Object o = session.getAttribute("cart");

            if (o != null) {
                cart = (Cart) o;
            } else {
                cart = new Cart();
            }
            int id = Integer.parseInt(request.getParameter("id"));
            cart.removeItem(id);

            List<Item> list = cart.getItems();

            session.setAttribute("cart", cart);
            session.setAttribute("size", list.size());
            request.getRequestDispatcher("cart1.jsp").forward(request, response);
        }
        if (service.equals("deleteall")) {
            HttpSession session = request.getSession();
            Cart cart = null;

            Object o = session.getAttribute("cart");

            if (o != null) {
                cart = (Cart) o;
            } else {
                cart = new Cart();
            }
//            int id = Integer.parseInt(request.getParameter("id"));
            

            List<Item> list = cart.getItems();
            list.removeAll(list);
            session.setAttribute("cart", cart);
            session.setAttribute("size", list.size());
            request.getRequestDispatcher("cart1.jsp").forward(request, response);
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
