package edu.metrostate.ics425.jtp307.prodmaint.servlets;

import java.io.IOException;
import java.util.LinkedList;    
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.metrostate.ics425.jtp307.prodmaint.model.ProductBean;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A servlet that processes submissions from ProductServlet.jsp, adding new ProductBean instances to a product list stored in the HTTP session. This will then forward the current session product list back to the JSP for disply.
 * 
 * @author Joseph T. Parsons
 */
public class ProductServlet extends HttpServlet {

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
        /* Allocate ErrMsg List */
        Collection<String> errMsgs = new LinkedList<>();


        /* Get Previous Products Stored in Session, Or Allocate New Product List */
        Set<ProductBean> products;
        if (request.getSession().getAttribute("products") != null
                && request.getSession().getAttribute("products") instanceof Set) {
            products = (Set<ProductBean>) request.getSession().getAttribute("products");
        }
        else {
            products = new HashSet<>();
        }
        
        
        /* Only Do This If a Request Variable "doAdd" is present,
         * indicating a form has actually been submitted. */
        if (request.getParameter("doAdd") != null) {
            /* Store Our POST Variables in New Final Variables,
             * Performing Trims and Making Nulls Empty Strings */
            final String codeStr = request.getParameter("code") != null
                    ? request.getParameter("code").trim()
                    : "";
            final String descriptionStr = request.getParameter("description") != null
                    ? request.getParameter("description").trim()
                    : "";
            final String priceStr = request.getParameter("price") != null
                    ? request.getParameter("price").trim()
                    : "";


            /* Create The Product Bean */
            ProductBean product = new ProductBean();


            /* Test Our Final Variables for Errors,
             * and Use Product Bean Setters If No Errors */
            // Code
            if (codeStr.length() > 0) {
                product.setCode(codeStr.trim());
            } else {
                errMsgs.add("Code is empty.");
            }

            // Description
            if (descriptionStr.length() > 0) {
                product.setDescription(descriptionStr.trim());
            } else {
                errMsgs.add("Description is empty.");
            }

            // Price
            if (priceStr.matches("^[\\d\\.]+$")) {
                product.setPrice(Double.parseDouble(priceStr));
            } else {
                errMsgs.add("Price is empty or invalid.");
            }

            // Release Date
            try {
                product.setReleaseDate(LocalDate.parse(request.getParameter("releaseDate")));
            } catch (Exception ex) {
                errMsgs.add("Unable to parse release date.");
            }
            
            
            /* Check for Conflicting Product Entry */
            if (products.contains(product)) {
                errMsgs.add("A product with the same code already exists.");
            }


            /* Add Our New Product If No Errors */
            if (errMsgs.isEmpty()) {
                products.add(product);
            } else {
                request.setAttribute("errMsgs", errMsgs);
            }
        }
        
        
        /* Update Our Product List In the Session */
        request.getSession().setAttribute("products", products);
        
        
        /* Send Our Updated Session Back to the JSP */
        request.getRequestDispatcher("/Product.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "This adds new ProductBean instances to the session list of ProductBeans, forwarding results to Product.jsp.";
    }// </editor-fold>

}
