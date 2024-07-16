/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import dal.ProductImageDAO;
import model.Category;
import model.Trademark;

/**
 *
 * @author FPT
 */
public class ProductDAO extends DBContext {

    //Lấy All Product
    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product"; // Sửa lại câu truy vấn

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int originalPrice = rs.getInt("original_prices");
                boolean sale = rs.getBoolean("sale");
                int salePrice = rs.getInt("sale_prices");
                String productHighlights = rs.getString("product_highlights");
                String productDescription = rs.getString("product_description");
                int trademarkId = rs.getInt("trademark_id");
                boolean status = rs.getBoolean("status");
                int quantity = rs.getInt("quantity");

                // Kiểm tra null trước khi lấy giá trị
                int guarantee = rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0;
                int categoryId = rs.getInt("category_id");
                Date updateDate = rs.getDate("update_date");
                int sole = rs.getInt("sole");
                int avrRatedStar = rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0;
                String productName = rs.getString("product_name");

                // Lấy danh sách hình ảnh sản phẩm
                ProductImageDAO imageDAO = new ProductImageDAO();
                List<String> images = imageDAO.getImagesByProductId(productId);

                Product product = new Product(productId, productName, originalPrice, sale, salePrice, productHighlights, productDescription, trademarkId, status, quantity, guarantee, categoryId, updateDate, sole, avrRatedStar, images);
                list.add(product);
            }

        } catch (SQLException e) {
            // Xử lý ngoại lệ
            e.printStackTrace(); // Hoặc ghi log lỗi
        }
        return list;
    }

    //Lấy danh sách sản phẩm thuộc một danh mục cụ thể (categoryId)
    public ArrayList<Product> getProductByCategoryId(int idC) {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE category_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, idC);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    int originalPrice = rs.getInt("original_prices");
                    boolean sale = rs.getBoolean("sale");
                    int salePrice = rs.getInt("sale_prices");
                    String productHighlights = rs.getString("product_highlights");
                    String productDescription = rs.getString("product_description");
                    int trademarkId = rs.getInt("trademark_id");
                    boolean status = rs.getBoolean("status");
                    int quantity = rs.getInt("quantity");

                    // Kiểm tra null trước khi lấy giá trị
                    int guarantee = rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0;
                    int categoryId = rs.getInt("category_id");
                    Date updateDate = rs.getDate("update_date");
                    int sole = rs.getInt("sole");
                    int avrRatedStar = rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0;

                    String productName = rs.getString("product_name");

                    ProductImageDAO imageDAO = new ProductImageDAO();
                    List<String> images = imageDAO.getImagesByProductId(productId);

                    Product product = new Product(productId, productName, originalPrice, sale, salePrice, productHighlights, productDescription, trademarkId, status, quantity, guarantee, categoryId, updateDate, sole, avrRatedStar, images);
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
        }
        return list;
    }

   
    // Phương thức lấy tổng số sản phẩm theo categoryId or trademark
    public int getTotalProducts(int categoryId, int trademarkId) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Product"); // No WHERE 1=1 initially
    List<Integer> params = new ArrayList<>();

    if (categoryId != 0 || trademarkId != 0) {
        sql.append(" WHERE"); // Add WHERE only if there are filters
    }

    if (categoryId != 0) {
        sql.append(" category_id = ?");
        params.add(categoryId);
    }

    if (trademarkId != 0) {
        sql.append(categoryId != 0 ? " AND" : "").append(" trademark_id = ?"); // Add AND if category filter exists
        params.add(trademarkId);
    }

    try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
       for (int i = 0; i < params.size(); i++) {
            st.setInt(i + 1, params.get(i));
        }
        try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                int result = rs.getInt(1);
                System.out.println("getTotalProducts returning: " + result); // Log returned value
                return result;
            }
        }
    } catch (SQLException e) {
        System.err.println("Error in getTotalProducts: " + e.getMessage()); // Log error
        e.printStackTrace();
    }
    System.out.println("getTotalProducts returning 0 (default)"); // Log default return
    return 0;
}
   

// Phương thức lấy danh sách sản phẩm theo categoryId và phân trang
    // Phương thức lấy danh sách sản phẩm theo categoryId và phân trang
public List<Product> getProductsByCategoryId(int categoryId, int page, int pageSize) {
    List<Product> list = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM Product WHERE 1=1"); 
    List<Integer> params = new ArrayList<>();

    if (categoryId != 0) {
        sql.append(" AND category_id = ?");
        params.add(categoryId);
    }

    sql.append(" ORDER BY product_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

    try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            st.setInt(i + 1, params.get(i));
        }
        int offset = (page - 1) * pageSize;
        st.setInt(params.size() + 1, offset);
        st.setInt(params.size() + 2, pageSize);
        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int originalPrice = rs.getInt("original_prices");
                boolean sale = rs.getBoolean("sale");
                int salePrice = rs.getInt("sale_prices");
                String productHighlights = rs.getString("product_highlights");
                String productDescription = rs.getString("product_description");
                int trademarkId = rs.getInt("trademark_id");
                boolean status = rs.getBoolean("status");
                int quantity = rs.getInt("quantity");

                // Kiểm tra null trước khi lấy giá trị
                int guarantee = rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0;
                int cId = rs.getInt("category_id");
                Date updateDate = rs.getDate("update_date");
                int sole = rs.getInt("sole");
                int avrRatedStar = rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0;

                String productName = rs.getString("product_name");

                ProductImageDAO imageDAO = new ProductImageDAO();
                List<String> images = imageDAO.getImagesByProductId(productId);

                Product product = new Product(productId, productName, originalPrice, sale, salePrice, productHighlights, productDescription, trademarkId, status, quantity, guarantee, cId, updateDate, sole, avrRatedStar, images);
                list.add(product);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
    
    public List<Product> getLatestProducts(int numProducts) {
    List<Product> list = new ArrayList<>();
    String sql = "SELECT TOP (?) * FROM Product ORDER BY update_date DESC"; 
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, numProducts);
        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                    int originalPrice = rs.getInt("original_prices");
                    boolean sale = rs.getBoolean("sale");
                    int salePrice = rs.getInt("sale_prices");
                    String productHighlights = rs.getString("product_highlights");
                    String productDescription = rs.getString("product_description");
                    int trademarkId = rs.getInt("trademark_id");
                    boolean status = rs.getBoolean("status");
                    int quantity = rs.getInt("quantity");

                    // Kiểm tra null trước khi lấy giá trị
                    int guarantee = rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0;
                    int categoryId = rs.getInt("category_id");
                    Date updateDate = rs.getDate("update_date");
                    int sole = rs.getInt("sole");
                    int avrRatedStar = rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0;

                    String productName = rs.getString("product_name");

                    ProductImageDAO imageDAO = new ProductImageDAO();
                    List<String> images = imageDAO.getImagesByProductId(productId);

                    Product product = new Product(productId, productName, originalPrice, sale, salePrice, productHighlights, productDescription, trademarkId, status, quantity, guarantee, categoryId, updateDate, sole, avrRatedStar, images);
                    list.add(product);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
    
    public List<Product> getProductByTrademarkId(int trademarkId, int page, int pageSize) {
    List<Product> list = new ArrayList<>();

    String sql = "SELECT * FROM Product WHERE 1=1"; 

    if (trademarkId != 0) {
        sql += " AND trademark_id = ?"; 
    }

    sql += " ORDER BY product_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try (PreparedStatement st = connection.prepareStatement(sql)) {
        int paramIndex = 1;
        if (trademarkId != 0) {
            st.setInt(paramIndex++, trademarkId);
        }

        int offset = (page - 1) * pageSize;
        st.setInt(paramIndex++, offset);
        st.setInt(paramIndex, pageSize);

        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int originalPrice = rs.getInt("original_prices");
                boolean sale = rs.getBoolean("sale");
                int salePrice = rs.getInt("sale_prices");
                String productHighlights = rs.getString("product_highlights");
                String productDescription = rs.getString("product_description");
                int traId = rs.getInt("trademark_id");
                boolean status = rs.getBoolean("status");
                int quantity = rs.getInt("quantity");

                // Kiểm tra null trước khi lấy giá trị
                int guarantee = rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0;
                int cId = rs.getInt("category_id");
                Date updateDate = rs.getDate("update_date");
                int sole = rs.getInt("sole");
                int avrRatedStar = rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0;

                String productName = rs.getString("product_name");

                ProductImageDAO imageDAO = new ProductImageDAO();
                List<String> images = imageDAO.getImagesByProductId(productId);

                Product product = new Product(productId, productName, originalPrice, sale, salePrice, productHighlights, productDescription, traId, status, quantity, guarantee, cId, updateDate, sole, avrRatedStar, images);
                list.add(product);
            }
        }
    } catch (SQLException e) {
        // Xử lý ngoại lệ
        e.printStackTrace();
    }
    return list;
}
    
    
    
    
    
    

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

    // Test case 1: Không lọc (cả categoryId và trademarkId đều bằng 0)
    int totalProductsAll = productDAO.getTotalProducts(0, 0);
    System.out.println("Total products (all categories, all trademarks): " + totalProductsAll);

    // Test case 2: Lọc theo categoryId
    int categoryIdToTest = 1; // Thay thế bằng categoryId thực tế
    int totalProductsByCategory = productDAO.getTotalProducts(categoryIdToTest, 0);
    System.out.println("Total products (category " + categoryIdToTest + "): " + totalProductsByCategory);

    // Test case 3: Lọc theo trademarkId
    int trademarkIdToTest = 2; // Thay thế bằng trademarkId thực tế
    int totalProductsByTrademark = productDAO.getTotalProducts(0, trademarkIdToTest);
    System.out.println("Total products (trademark " + trademarkIdToTest + "): " + totalProductsByTrademark);

    // Test case 4: Lọc theo cả categoryId và trademarkId
    int totalProductsByBoth = productDAO.getTotalProducts(categoryIdToTest, trademarkIdToTest);
    System.out.println("Total products (category " + categoryIdToTest + ", trademark " + trademarkIdToTest + "): " + totalProductsByBoth);
    }
    
}
