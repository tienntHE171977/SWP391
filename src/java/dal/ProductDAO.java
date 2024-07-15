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

/**
 *
 * @author FPT
 */
public class ProductDAO extends DBContext {
    //Lấy All Product
    

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

                    Product product = new Product(productId, productName, originalPrice, sale, salePrice,
                            productHighlights, productDescription, trademarkId, status, quantity,
                            guarantee, categoryId, updateDate, sole, avrRatedStar);
                    ProductImageDAO productImageDAO = new ProductImageDAO();
                    List<String> images = productImageDAO.getImagesByProductId(productId);
                    if (images != null) {
                        product.setImages(images);
                    }
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
        }
        return list;
    }

    // 
    public List<Product> getProductsByCategoryIdAndPage(int categoryId, int pageNumber, int pageSize) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, pi.image_url " + // Lấy cả hình ảnh từ bảng Products_Images
                 "FROM Product p " +
                 "LEFT JOIN Products_Images pi ON p.product_id = pi.product_id " +
                 "WHERE (p.category_id = ? OR ? = 0) " +
                 "ORDER BY p.product_id " +
                 "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, categoryId);
            st.setInt(2, categoryId);
            st.setInt(3, (pageNumber - 1) * pageSize);
            st.setInt(4, pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    String productName = rs.getString("product_name");
                    int originalPrice = rs.getInt("original_prices");
                    boolean sale = rs.getBoolean("sale");
                    int salePrice = rs.getInt("sale_prices");
                    String productHighlights = rs.getString("product_highlights");
                    String productDescription = rs.getString("product_description");
                    int trademarkId = rs.getInt("trademark_id");
                    boolean status = rs.getBoolean("status");
                    int quantity = rs.getInt("quantity");
                    int guarantee = rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0;
                    int categoryIdDB = rs.getInt("category_id");
                    Date updateDate = rs.getDate("update_date");
                    int sole = rs.getInt("sole");
                    int avrRatedStar = rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0;

                    String image = rs.getString("images"); // Lấy đường dẫn hình ảnh

                    // Tạo đối tượng Product và thêm vào danh sách
                    Product product = new Product(productId, productName, originalPrice, sale, salePrice, productHighlights, productDescription, trademarkId, status, quantity, guarantee, categoryIdDB, updateDate, sole, avrRatedStar);

                    // Thêm hình ảnh vào danh sách images của sản phẩm
                    if (image != null) {
                        if (product.getImages() == null) {
                            product.setImages(new ArrayList<>()); // Khởi tạo danh sách images nếu chưa có
                        }
                        product.getImages().add(image);
                    }

                    list.add(product);
                }
            }
        } catch (SQLException e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
        }
        return list;
    }

    //tính toán số trang cần thiết cho việc phân trang
    public int getTotalProductsByCategoryId(int categoryId) {
        String sql = "SELECT COUNT(*) FROM Product WHERE category_id = ? OR ? = 0";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, categoryId);
            st.setInt(2, categoryId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        // Chọn một categoryId hợp lệ từ bảng Category của bạn
        int categoryIdToTest = 1;

        ArrayList<Product> products = productDAO.getProductByCategoryId(categoryIdToTest);

        if (products.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm nào thuộc danh mục này.");
        } else {
            System.out.println("Danh sách sản phẩm thuộc danh mục " + categoryIdToTest + ":");
            for (Product product : products) {
                System.out.println("Product ID: " + product.getProductId());
                System.out.println("Product Name: " + product.getProductName());

                // Hiển thị danh sách hình ảnh (nếu có)
                List<String> images = product.getImages();
                if (images != null && !images.isEmpty()) {
                    System.out.println("Images:");
                    for (String image : images) {
                        System.out.println("  - " + image);
                    }
                } else {
                    System.out.println("  Không có hình ảnh.");
                }

                System.out.println("------------------------");
            }
        }
    }
}
