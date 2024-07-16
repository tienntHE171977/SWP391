/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.ProductDAO;
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
 * @author FPT
 */
@WebServlet(name = "ShopGridServlet", urlPatterns = {"/shopgrid"})
public class ShopGridServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<h1>Servlet ShopGridServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số categoryId và trademarkId từ request (nếu có)
    String categoryIdStr = request.getParameter("categoryId");
    String trademarkIdStr = request.getParameter("trademarkId");

    int categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : 0;
    int trademarkId = (trademarkIdStr != null && !trademarkIdStr.isEmpty()) ? Integer.parseInt(trademarkIdStr) : 0;

    // Khởi tạo DAO
    CategoryDAO categoryDAO = new CategoryDAO();
    ProductDAO productDAO = new ProductDAO();
    TrademarkDAO trademarkDAO = new TrademarkDAO();

    // Lấy danh sách tất cả các danh mục
    List<Category> categories = categoryDAO.getAllCategory();

    // Lấy danh sách tất cả các thương hiệu
    List<Trademark> trademarks = trademarkDAO.getAllTrademark();

    // Phân trang
    int pageSize = 9;
    String pageStr = request.getParameter("page");
    int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;

    // Lấy tổng số sản phẩm
    int totalProducts = productDAO.getTotalProducts(categoryId, trademarkId); // Gọi phương thức đã sửa

    int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

    // Lấy danh sách sản phẩm dựa trên categoryId và trademarkId
    List<Product> products;
    if (categoryId != 0) {
        products = productDAO.getProductsByCategoryId(categoryId, page, pageSize);
    } else if (trademarkId != 0) {
        products = productDAO.getProductByTrademarkId(trademarkId, page, pageSize);
    } else {
        products = productDAO.getAllProduct();
    }

    List<Product> latestProducts = productDAO.getLatestProducts(3);

    // Đặt các thuộc tính vào request
    request.setAttribute("categories", categories);
    request.setAttribute("products", products);
    request.setAttribute("trademarks", trademarks);
    request.setAttribute("categoryId", categoryId);
    request.setAttribute("trademarkId", trademarkId);
    request.setAttribute("page", page);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("latestProducts", latestProducts);

    // Chuyển hướng đến trang shop-grid.jsp
    request.getRequestDispatcher("shop_grid.jsp").forward(request, response);
}
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
