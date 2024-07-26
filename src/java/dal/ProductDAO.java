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
      public void insertProduct(String productName, int originalPrice, int salePrice, String highlight, String description, int trademarkId, int quantity, int categoryId) {
        String sql = "INSERT INTO Product (product_name, original_prices, sale_prices, product_highlights, product_description, trademark_id, quantity, category_id, update_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, productName);
            st.setInt(2, originalPrice);
            st.setInt(3, salePrice);
            st.setString(4, highlight);
            st.setString(5, description);
            st.setInt(6, trademarkId);
            st.setInt(7, quantity);
            st.setInt(8, categoryId);
            st.setDate(9, new java.sql.Date(System.currentTimeMillis())); // Assuming update_date is the current date

            // Execute the insert statement
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product inserted successfully.");
            } else {
                System.out.println("Product insertion failed.");
            }

        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace(); // Or log the error
        }
    }
     public Product getBestSellingProduct() {
        String sql = "SELECT TOP 1 * "
                + // Lấy tất cả các cột của sản phẩm bán chạy nhất
                "FROM Product "
                + "ORDER BY sole DESC"; // Sắp xếp theo sole giảm dần

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            if (rs.next()) {
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

                return new Product(productId, productName, originalPrice, sale, salePrice, productHighlights, productDescription, trademarkId, status, quantity, guarantee, categoryId, updateDate, sole, avrRatedStar, images);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Hoặc xử lý ngoại lệ theo cách phù hợp với ứng dụng của bạn
        }

        return null; // Trả về null nếu không tìm thấy sản phẩm bán chạy nhất
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
    public List<Product> getProductsByTrademark(int Id) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE trademark_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, Id);

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
            // Ghi log lỗi hoặc ném ngoại lệ tùy chỉnh
            // Ví dụ: logger.error("Error getting products by trademark ID: " + trademarkId, e);
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
    PreparedStatement ps = null;
    ResultSet rs = null;

    public int getTotalProduct() {
        int count = 0;

        String sql = "select  COUNT(product_id) from Product";
        try {
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {  // Move the cursor to the first row
                count = rs.getInt(1);  // Retrieve the value from the first column
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }
        return count;
    }

    public void UpdateProduct(int originalprice,int saleprice,String highlight,String description,int trademarkId,int quantity,int categoryId,String productName,int productId) {

        String sql = "UPDATE [dbo].[Product]\n"
                + "   SET [original_prices] = ?\n"
                + "      ,[sale_prices] = ?\n"
                + "      ,[product_highlights] = ?\n"
                + "      ,[product_description] = ?\n"
                + "      ,[trademark_id] = ?\n"
                + "      ,[quantity] = ?\n"
                + "  ,[category_id] = ?\n"
                + ",[product_name] = ?\n"
                + " WHERE product_id =?";
       try {
            ps = connection.prepareStatement(sql);
       
            ps.setInt(1, originalprice);
            ps.setInt(2, saleprice);
            ps.setString(3, highlight);
            ps.setString(4, description);
            ps.setInt(5, trademarkId);
            ps.setInt(6, quantity);
            ps.setInt(7, categoryId);
            ps.setString(8, productName);
            ps.setInt(9, productId);
            ps.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }
    public ArrayList<Product> getProductId(int idC) {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE product_id = ?";
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
    public Product getProductById(int id) {
    String sql = "SELECT * FROM Product WHERE product_id = ?";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, id);
        try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                ProductImageDAO imageDAO = new ProductImageDAO();
                List<String> images = imageDAO.getImagesByProductId(id); // Corrected to use the actual product ID
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("original_prices"),
                        rs.getBoolean("sale"),
                        rs.getInt("sale_prices"),
                        rs.getString("product_highlights"),
                        rs.getString("product_description"),
                        rs.getInt("trademark_id"),
                        rs.getBoolean("status"),
                        rs.getInt("quantity"),
                        rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0,
                        rs.getInt("category_id"),
                        rs.getDate("update_date"),
                        rs.getInt("sole"),
                        rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0,
                        images
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    public int countProductLow() {
        int count = 0;

        String sql = "select  COUNT(product_id) from Product where quantity<5";
        try {
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {  // Move the cursor to the first row
                count = rs.getInt(1);  // Retrieve the value from the first column
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }
        return count;
    }
//    select Count(product_id) from Product
//where quantity<5

    public List<Product> getListPageByIndex(int index) {
        List<Product> list = new ArrayList<>();

        String query = "select * from Product\n"
                + "  order by product_id\n"
                + "  offset ? rows fetch next 12 rows only;";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, (index - 1) * 12);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                ProductImageDAO imageDAO = new ProductImageDAO();
                List<String> images = imageDAO.getImagesByProductId(productId);
                list.add(new Product(rs.getInt(1), rs.getString(15), rs.getInt(2), rs.getBoolean(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getBoolean(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getDate(12), rs.getInt(13), rs.getInt(14), images));
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return list;
    }
 public Product getElementProductByPid(int pid) {

        String query = "select * from Product\n"
                + "  where Product.product_id = ?";

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, pid);

            rs = ps.executeQuery();

            if (rs.next()) {
               int productId = rs.getInt("product_id");
                ProductImageDAO imageDAO = new ProductImageDAO();
                List<String> images = imageDAO.getImagesByProductId(productId);
                Product product = new Product(rs.getInt(1), rs.getString(15), rs.getInt(2), rs.getBoolean(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getBoolean(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getDate(12), rs.getInt(13), rs.getInt(14), images);
                return product;
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return null;
    }
 public Product getRandomDiscountedProduct() {
        String sql = "SELECT TOP 1 * FROM Product WHERE sale = 1 ORDER BY NEWID()"; // Chọn ngẫu nhiên 1 sản phẩm có sale = 1

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            if (rs.next()) {
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
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Trả về null nếu không tìm thấy sản phẩm giảm giá
    }

    

    public List<Product> getTopRatedProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE avr_rated_star IS NOT NULL ORDER BY avr_rated_star DESC";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                double originalPrice = rs.getDouble("original_prices");
                boolean sale = rs.getBoolean("sale");
                double salePrice = rs.getDouble("sale_prices");
                String productHighlights = rs.getString("product_highlights");
                String productDescription = rs.getString("product_description");
                int trademarkId = rs.getInt("trademark_id");
                boolean status = rs.getBoolean("status");
                int quantity = rs.getInt("quantity");
                int guarantee = rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0;
                int categoryId = rs.getInt("category_id");
                Date updateDate = rs.getDate("update_date");
                int sole = rs.getInt("sole");
                int avrRatedStar = rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0;
                String productName = rs.getString("product_name");

                // Lấy danh sách hình ảnh sản phẩm
                ProductImageDAO imageDAO = new ProductImageDAO();
                List<String> images = imageDAO.getImagesByProductId(productId);

                Product product = new Product(productId, productName, guarantee, sale, sole, productHighlights, productDescription, trademarkId, status, quantity, guarantee, categoryId, updateDate, sole, avrRatedStar, images);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc xử lý ngoại lệ theo cách khác
        }

        return products;
    }

    public List<Product> getSaleProducts(int numProducts) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT TOP (?) * FROM Product WHERE sale = 1"; // Lấy sản phẩm đang giảm giá

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, numProducts);

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    double originalPrice = rs.getDouble("original_prices");
                    boolean sale = rs.getBoolean("sale");
                    double salePrice = rs.getDouble("sale_prices");
                    String productHighlights = rs.getString("product_highlights");
                    String productDescription = rs.getString("product_description");
                    int trademarkId = rs.getInt("trademark_id");
                    boolean status = rs.getBoolean("status");
                    int quantity = rs.getInt("quantity");
                    int guarantee = rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0;
                    int categoryId = rs.getInt("category_id");
                    Date updateDate = rs.getDate("update_date");
                    int sole = rs.getInt("sole");
                    int avrRatedStar = rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0;
                    String productName = rs.getString("product_name");

                    // Lấy danh sách hình ảnh sản phẩm
                    ProductImageDAO imageDAO = new ProductImageDAO();
                    List<String> images = imageDAO.getImagesByProductId(productId);

                    Product product = new Product(productId, productName, guarantee, sale, sole, productHighlights, productDescription, trademarkId, status, quantity, guarantee, categoryId, updateDate, sole, avrRatedStar, images);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> getBestSellerProducts(int numProducts) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT TOP (?) * FROM Product ORDER BY sole DESC";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, numProducts); // Đặt số lượng sản phẩm muốn lấy
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    double originalPrice = rs.getDouble("original_prices");
                    boolean sale = rs.getBoolean("sale");
                    double salePrice = rs.getDouble("sale_prices");
                    String productHighlights = rs.getString("product_highlights");
                    String productDescription = rs.getString("product_description");
                    int trademarkId = rs.getInt("trademark_id");
                    boolean status = rs.getBoolean("status");
                    int quantity = rs.getInt("quantity");
                    int guarantee = rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0;
                    int categoryId = rs.getInt("category_id");
                    Date updateDate = rs.getDate("update_date");
                    int sole = rs.getInt("sole");
                    int avrRatedStar = rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0;
                    String productName = rs.getString("product_name");

                    // Lấy danh sách hình ảnh sản phẩm
                    ProductImageDAO imageDAO = new ProductImageDAO();
                    List<String> images = imageDAO.getImagesByProductId(productId);

                    Product product = new Product(productId, productName, guarantee, sale, sole, productHighlights, productDescription, trademarkId, status, quantity, guarantee, categoryId, updateDate, sole, avrRatedStar, images);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc xử lý ngoại lệ theo cách khác
        }

        return products; // Trả về danh sách sản phẩm
    }

    public List<Product> getTopRatedProducts(int numProducts) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT TOP (?) * FROM Product WHERE avr_rated_star IS NOT NULL ORDER BY avr_rated_star DESC";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, numProducts); // Đặt số lượng sản phẩm muốn lấy (3 trong trường hợp này)

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    double originalPrice = rs.getDouble("original_prices");
                    boolean sale = rs.getBoolean("sale");
                    double salePrice = rs.getDouble("sale_prices");
                    String productHighlights = rs.getString("product_highlights");
                    String productDescription = rs.getString("product_description");
                    int trademarkId = rs.getInt("trademark_id");
                    boolean status = rs.getBoolean("status");
                    int quantity = rs.getInt("quantity");
                    int guarantee = rs.getObject("guarantee") != null ? rs.getInt("guarantee") : 0;
                    int categoryId = rs.getInt("category_id");
                    Date updateDate = rs.getDate("update_date");
                    int sole = rs.getInt("sole");
                    int avrRatedStar = rs.getObject("avr_rated_star") != null ? rs.getInt("avr_rated_star") : 0;
                    String productName = rs.getString("product_name");

                    // Lấy danh sách hình ảnh sản phẩm
                    ProductImageDAO imageDAO = new ProductImageDAO();
                    List<String> images = imageDAO.getImagesByProductId(productId);

                    Product product = new Product(productId, productName, guarantee, sale, sole, productHighlights, productDescription, trademarkId, status, quantity, guarantee, categoryId, updateDate, sole, avrRatedStar, images);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc xử lý ngoại lệ theo cách khác
        }

        return products;
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
        int count;
        count = productDAO.getTotalProduct();
        System.out.println(count);
        List<Product> list = productDAO.getListPageByIndex(2);
        System.out.println(list.get(0).getProductName());
    }

}
