package edu.metrostate.ics425.jtp307.prodmaint.servlets;

import edu.metrostate.ics425.jtp307.prodmaint.model.ProductBean;
import java.io.IOException;
import java.util.LinkedList;    
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.metrostate.ics425.prodmaint.model.Product;
import edu.metrostate.ics425.prodmaint.data.ProductCatalog;
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
    final String libraryFile = "productLibrary.bin";

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
        /* Initialise the Product Library */
        ProductCatalog.init(libraryFile);
        
        
        /* Allocate ErrMsg List */
        Collection<String> errMsgs = new LinkedList<>();

        
        /* Get Action from Form Data */
        final String action = request.getParameter("action");

        
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
        final String releaseDateStr = request.getParameter("releaseDate") != null
                ? request.getParameter("releaseDate").trim()
                : "";
        

        /* If The Servlet is Supposed to Alter the Catalogue...
         * (because it has an action and a form was submitted) */
        if (action != null
            && request.getParameter("formSubmitted") != null) {
            
            /* Validate Data Based on Action */
            if (!action.equals("edit") && !action.equals("add") && !action.equals("delete")) {
                errMsgs.add("An unsupported action '" + action + "' was encountered.");
            }            
            // Make Sure Code Either Does (when editing/deleting) Or Doesn't (when adding) Exist
            else if (action.equals("add")
                && ProductCatalog.getInstance().exists(codeStr)) {
                errMsgs.add("A product with the same code already exists.");
            }
            else if ((
                    action.equals("edit")
                    || action.equals("delete")
                ) && !ProductCatalog.getInstance().exists(codeStr)) {
                errMsgs.add("No product with that code exists to be edited.");
            }


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

            // Get Parameters Only Included with Non-Delete Requests
            if (!action.equals("delete")) {
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
            }


            /* Modify the Catalogue If No Errors */
            if (errMsgs.isEmpty()) {
                if (action.equals("add")) {
                    ProductCatalog.getInstance().insertProduct(product);
                                    
                    // Redirect Back to Main Catalog on Success
                    response.sendRedirect("./catalog");
                    return;
                }
                else { // actions that are still under construction
                    errMsgs.add("That action is still under construction, and cannot be completed at this time.");
                }
            }
            
        }


        /* Send Back Any Errors We Encountered */
        request.setAttribute("errMsgs", errMsgs);

        
        /* Send Information to JSP About All Products or Just One, Depending on Context */
        if (action == null) { // Send the whole catalog if an action is not present.
            request.setAttribute("products", ProductCatalog.getInstance().selectAllProducts());
        }
        else if (action != "add") { // Only send the relevant product if an action is present, and is not add (in which case we send nothing at all)
            request.setAttribute("product", ProductCatalog.getInstance().selectProduct(codeStr));
        }
        
        
        /* Send Our Response Back to the JSP */
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
