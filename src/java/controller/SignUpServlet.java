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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

/**
 *
 * @author pc
 */
@WebServlet(name="SignUpServlet", urlPatterns={"/signUp"})
public class SignUpServlet extends HttpServlet {
   
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
            out.println("<title>Servlet SignUpServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignUpServlet at " + request.getContextPath () + "</h1>");
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
           request.getRequestDispatcher("signUp.jsp").forward(request, response);
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
        String userName = request.getParameter("userName");
        String password = request.getParameter("pass");
        String re_pass = request.getParameter("re_pass");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        AccountDAO adao = new AccountDAO();
        
        Account account = adao.getAccountByEmail(email);
        
        if(account != null){
            String errDuplicateEmail = "Email exits!!!";
            request.setAttribute("errDuplicateEmail", errDuplicateEmail);
            request.getRequestDispatcher("signUp.jsp").forward(request, response);
        }
        
        if (adao.checkUserDupplicate(userName)) {
            String errDuplicateUser = "User exits!!!";
            request.setAttribute("errDuplicateUser", errDuplicateUser);
            request.getRequestDispatcher("signUp.jsp").forward(request, response);
        }
        
        
        else {
            if(!validateUsername(userName)){
                String msg = "user must be between 3 and 20 characters";
                  request.setAttribute("msg", msg);
                request.getRequestDispatcher("signUp.jsp").forward(request, response);
            }
            if(!validatePassword(password)){
                String msg = "password must be between 6 and 10 characters";
                  request.setAttribute("msg", msg);
                request.getRequestDispatcher("signUp.jsp").forward(request, response);
            }
            if(!validatePhone(phone)){
                String msg = "Phone is not valid";
                  request.setAttribute("msg", msg);
                request.getRequestDispatcher("signUp.jsp").forward(request, response);
            }
            if(!validateEmail(email)){
                String msg = "Email is not valid";
                  request.setAttribute("msg", msg);
                request.getRequestDispatcher("signUp.jsp").forward(request, response);
            }
            if (!password.equals(re_pass)) {
                String errNewPass = "Password new not duplicate!!!";
                request.setAttribute("errNewPass", errNewPass);
                request.getRequestDispatcher("signUp.jsp").forward(request, response);
            }else{
adao.insertNewAccount(fname, lname, email, password, phone, address, 3, userName);
                response.sendRedirect("login");
            }
        }
    }
     public static boolean validateUsername(String username) {
        if (username == null) return false;
        return username.length() >= 3 && username.length() <= 30 && username.matches("^[a-zA-Z0-9_]*$");
    }

    // Kiểm tra password: độ dài 6-10 ký tự, không có dấu
    public static boolean validatePassword(String password) {
        if (password == null) return false;
        return password.length() >= 6 && password.length() <= 10 && password.matches("^[a-zA-Z0-9]*$");
    }

    // Kiểm tra số điện thoại Việt Nam
    public static boolean validatePhone(String phone) {
        if (phone == null) return false;
        return phone.matches("^(0|\\+84)[0-9]{9}$");
    }

    // Kiểm tra email theo dạng xxx@xxx.xxx
    public static boolean validateEmail(String email) {
        if (email == null) return false;
        return email.matches("^[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,}$");
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
