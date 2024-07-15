/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.CategoryDAO;
import dal.ProductDAO;
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

/**
 *
 * @author FPT
 */
@WebServlet(name="ShopGridServlet", urlPatterns={"/shopgrid"})
public class ShopGridServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ShopGridServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShopGridServlet at " + request.getContextPath () + "</h1>");
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
        String categoryIdStr = request.getParameter("categoryId");
        int categoryId = 0; // Mặc định là 0 (tất cả danh mục)
        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
            categoryId = Integer.parseInt(categoryIdStr);
        }

        // Khởi tạo DAO
        CategoryDAO categoryDAO = new CategoryDAO();
        ProductDAO productDAO = new ProductDAO();

        // Lấy danh sách tất cả các danh mục
        ArrayList<Category> categories = categoryDAO.getAllCategory();

        // Lấy danh sách sản phẩm theo categoryId và phân trang
        final int PAGE_SIZE = 9; // Số lượng sản phẩm trên mỗi trang
        int pageNumber = 1; // Số trang hiện tại
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            pageNumber = Integer.parseInt(pageStr);
        }
        List<Product> products = productDAO.getProductsByCategoryIdAndPage(categoryId, pageNumber, PAGE_SIZE);
        int totalProducts = productDAO.getTotalProductsByCategoryId(categoryId);
        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        // Đặt các thuộc tính vào request
        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
        request.setAttribute("categoryId", categoryId); // Lưu categoryId để đánh dấu danh mục đang chọn
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", pageNumber);

        // Chuyển hướng đến trang shop-grid.jsp
        request.getRequestDispatcher("shop-grid.jsp").forward(request, response);
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
        processRequest(request, response);
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
