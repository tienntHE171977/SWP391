/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import model.Account;
import org.json.JSONObject;

/**
 *
 * @author pc
 */
@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
   
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
            out.println("<title>Servlet LoginServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath () + "</h1>");
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
    Cookie arr[] = request.getCookies();

        for (Cookie cookie : arr) {
            if (cookie.getName().equals("userC")) {
                request.setAttribute("userC", cookie.getValue());
            }
            if (cookie.getName().equals("passC")) {
                request.setAttribute("passC", cookie.getValue());
            }
        }

        request.getRequestDispatcher("login.jsp").forward(request, response);
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
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String remeber = request.getParameter("remember");
        String recaptchaResponse = request.getParameter("g-recaptcha-response");

        // Verify reCAPTCHA
        boolean isCaptchaValid = verifyRecaptcha(recaptchaResponse);

        if (!isCaptchaValid) {
            String errLogin = "Captcha verification failed. Please try again.";
            request.setAttribute("errLogin", errLogin);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        AccountDAO adao = new AccountDAO();

        Account account = adao.getAccountByUserAndPass(user, password);

        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("acc", account);
            Cookie u = new Cookie("userC", user);
            Cookie p = new Cookie("passC", password);

            u.setMaxAge(60 * 60 * 24 * 7);

            if (remeber != null) {
                p.setMaxAge(60 * 60 * 24 * 7);
            } else {
                p.setMaxAge(0);
            }

            response.addCookie(u);
            response.addCookie(p);
            if (account.getRoleID() == 3) {

                response.sendRedirect("homePage");
            }else{
                response.sendRedirect("adminPage");
                
            }

        } else {
            String errLogin = "user or password incorrect";
            request.setAttribute("errLogin", errLogin);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    private boolean verifyRecaptcha(String recaptchaResponse) {
        String verifyUrl = "https://www.google.com/recaptcha/api/siteverify";
        try {
            URL url = new URL(verifyUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String postParams = "secret=" + SECRET_KEY + "&response=" + recaptchaResponse;
            try (OutputStream output = connection.getOutputStream()) {
                output.write(postParams.getBytes(StandardCharsets.UTF_8));
            }

            InputStream inputStream = connection.getInputStream();
            StringBuilder response = new StringBuilder();
            int inputChar;
            while ((inputChar = inputStream.read()) != -1) {
                response.append((char) inputChar);
            }

            JSONObject json = new JSONObject(response.toString());
            return json.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private static final String SECRET_KEY = "6LenyvQpAAAAAL9Ub4IRnj-0A_ym5Jv5uuOS1vmq";

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
