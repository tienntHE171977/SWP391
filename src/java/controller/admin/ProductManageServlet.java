/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.CategoryDAO;
import dal.ProductDAO;
import dal.ProductImageDAO;
import dal.TrademarkDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Product;
import model.Trademark;

/**
 *
 * @author pc
 */
@WebServlet(name="ProductManageServlet", urlPatterns={"/productmanager"})
public class ProductManageServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ProductManageServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductManageServlet at " + request.getContextPath () + "</h1>");
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
        ProductDAO pd = new ProductDAO();
        
        TrademarkDAO td = new TrademarkDAO();
        List<Trademark> listT = td.getAllTrademark();
        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categories = categoryDAO.getAllCategory();
        List<Product> listP =pd.getAllProduct();
        request.setAttribute("listP", listP);
        request.setAttribute("listC", categories);
        request.setAttribute("listT", listT);
        request.getRequestDispatcher("admin/product.jsp").forward(request, response);
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
        ProductDAO pd = new ProductDAO();
        ProductImageDAO pi = new ProductImageDAO();
        String action = request.getParameter("action");
        int productId = Integer.parseInt(request.getParameter("product_id"));
                  String productname = request.getParameter("product_name");
                  int originalprice = Integer.parseInt(request.getParameter("originalprice"));
                  int saleprice = Integer.parseInt(request.getParameter("saleprice"));
                  String description = request.getParameter("product_describe");
                  String highlight = request.getParameter("highlights");
                  int trademarkId = Integer.parseInt(request.getParameter("trademark_id"));
                  int categoryId = Integer.parseInt(request.getParameter("category_id"));
                  int quantity = Integer.parseInt(request.getParameter("quantity"));
                  String productimage = request.getParameter("product_img");
          switch(action){
              case "update":
                  pd.UpdateProduct(originalprice, saleprice, highlight, description, trademarkId, quantity, categoryId, productname, productId);
                  pi.UpdateImage(productimage, productId);
                  break;
              case "insertproduct":
                  pd.UpdateProduct(originalprice, saleprice, highlight, description, trademarkId, quantity, categoryId, productname, productId);
                  
                      break;
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
