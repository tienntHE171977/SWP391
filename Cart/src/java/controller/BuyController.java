package controller;

import dal.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Cart;
import model.Customer;
import model.Item;
import model.Product;

public class BuyController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Process request logic (optional)
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer user = (Customer) session.getAttribute("acc");

        if (user != null) {
            Cart cart ;
            Object o = session.getAttribute("cart");
            if (o != null) {
                cart = (Cart) o;
            } else {
                cart = new Cart();
            }

            int quantity = 1;
            String productid = request.getParameter("id");

            try {
                if (request.getParameter("quantity") != null) {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                }

                ProductDAO dao = new ProductDAO();
                Product p = dao.getProductById(Integer.parseInt(productid));
                double price = p.getOriginal_prices();

                Item t = new Item(p, quantity, price);
                cart.addItem(t);

            } catch (Exception e) {
                e.printStackTrace(); 
            }

            List<Item> list = cart.getItems();
            session.setAttribute("cart", cart);
            session.setAttribute("size", list.size());
            //request.getRequestDispatcher("cart1.jsp").forward(request, response); 
            response.sendRedirect("shopgird");
        } else {
            response.sendRedirect("Login.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
