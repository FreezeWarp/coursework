package edu.metrostate.ics425.jtp307.prodmaint.servlets;

import edu.metrostate.ics425.jtp307.prodmaint.db.ProductCatalog;
import edu.metrostate.ics425.jtp307.prodmaint.model.Product;
import edu.metrostate.ics425.jtp307.prodmaint.model.ProductBean;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet does two things:
 * - First, it forwards a product's information to a JSP if the JSP sent a product code.
 * - Second, it updates, creates, or deletes a product on form submission.
 * 
 * You could argue this is a bit large, but I think this is best, since we only have
 * to validate information once.
 * 
 * @author Joseph T. Parsons
 */
public class ChangeProductServlet extends HttpServlet {
    
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
        
        //**** INITIALIZE REQUIREMENTS ****//
        
        /* Allocate ErrMsg List */
        Collection<String> errMsgs = new LinkedList<>();
        
        
        /* Get the catalogue from the servlet context. */
        ProductCatalog catalogue = ProductCatalog.getInstance();
        
        
        
        //**** RETRIEVE FORM DATA ****//
        
        /* Get Action from Form Data */
        final String action = request.getParameter("action");
        
        
        /* Get whether or not a form was submitted. */
        final Boolean formSubmitted = request.getParameter("formSubmitted") != null
                ? true
                : false;
        
        
        /* Get our parameter variables,
         * performing trims and making nulls empty strings */
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
        

        
        //**** VALIDATE FORM DATA ****//

        /* Make sure a valid action was specified. */
        if (!(action.equals("edit") || action.equals("add") || action.equals("delete"))) {
            errMsgs.add("An unsupported action '" + action + "' was encountered.");
        }
        
        
        /* Make sure product either does or doesn't exist, depending on action. */
        if ((action.equals("edit")
                || action.equals("delete")
            ) && !catalogue.exists(codeStr)) { // should exist when editing, deleting
            errMsgs.add("No product with that code exists to be edited/deleted.");
        }
        else if (action.equals("add")
            && catalogue.exists(codeStr)) { // should not exist when adding
            errMsgs.add("A product with the same code already exists.");
        }
        
        
        /* Validate Form Data When Form Submitted */
        if (formSubmitted && (action.equals("add") || action.equals("edit"))) {
            if (codeStr.length() == 0) {
                errMsgs.add("The product code must be non-empty.");
            }
            
            if (descriptionStr.length() == 0) {
                errMsgs.add("Description is empty.");
            }
            
            if (!priceStr.matches("^\\d+(\\.\\d+|)$")) {
                errMsgs.add("Price is empty or invalid.");
            }
            
            if (releaseDateStr.length() > 0) {
                try {
                    LocalDate.parse(releaseDateStr);
                } catch (Exception ex) {
                    errMsgs.add("Unable to parse release date. Please use YYYY-MM-DD (ISO-8601) format.");
                }
            }
        }
        
        
        
        //**** PROCESS SUBMISSION ****//

        /* If the servlet is supposed to alter the catalogue...
         * (because it has an action and a form was submitted) */
        if (formSubmitted && errMsgs.isEmpty()) {
            
            /* Get the Product Bean */
            Product product = null;
            if (action.equals("add")) {
                product = new ProductBean();
                product.setCode(codeStr.trim());
            }
            else if (action.equals("edit") || action.equals("delete")) {
                product = catalogue.selectProduct(codeStr);
            }
            
            
            /* Update the Product Bean */
            if (action.equals("add") || action.equals("edit")) {
                product.setDescription(descriptionStr);
                product.setPrice(Double.parseDouble(priceStr));
                
                if (releaseDateStr.length() > 0) {
                    product.setReleaseDate(LocalDate.parse(releaseDateStr));
                }
                else {
                    product.setReleaseDate(null);
                }
            }
            
            
            /* Manipulate the Catalog */
            switch (action) {
                case "add":
                    if (ProductCatalog.getInstance().insertProduct(product)) {
                        request.getSession().setAttribute("catalogueNotification", "You have successfully added product #" + product.getCode() + ", \"" + product.getDescription() + "\"");
                    }
                    else {
                        errMsgs.add("An unknown error occured trying to add your product.");
                    }
                    break;

                case "edit":
                    if (catalogue.updateProduct(product)) {
                        request.getSession().setAttribute("catalogueNotification", "You have successfully editted product #" + product.getCode() + ", \"" + product.getDescription() + "\"");
                    }
                    else {
                        errMsgs.add("An unknown error occured trying to edit your product.");
                    }
                    break;

                case "delete":
                    if (catalogue.deleteProduct(product)) {
                        request.getSession().setAttribute("catalogueNotification", "You have successfully deleted product #" + product.getCode() + ", \"" + product.getDescription() + "\"");
                    }
                    else {
                        errMsgs.add("An unknown error occured trying to add your product.");
                    }
                    break;

                default:
                    errMsgs.add("An unrecognised action was encountered. ...Not sure how, since we already checked this earlier.");
                    break;
            }
        }


        
        //**** PREPARE RESPONSE ****//

        /* Send Back Any Errors We Encountered */
        request.setAttribute("errMsgs", errMsgs);

        
        /* Populate the product request attribute, letting the JSP display
         * the product's information. */
        if (!codeStr.equals("")) {
            request.setAttribute("product", catalogue.selectProduct(codeStr));
        }

        
        
        //**** FORWARD RESPONSE TO JSP ****//
        
        /* If a form was submitted and no errors were encountered processing it,
         * redirect to the main catalog, instead of the product page. */
        if (formSubmitted && errMsgs.size() == 0) {
            response.sendRedirect("./catalog");
        }
        else {
            request.getRequestDispatcher("/ChangeProduct.jsp").forward(request, response);
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
        return "This sends the information about a single product to the ChangeProduct JSP, and, if requested to, perform product additions, edits, and deletions.";
    }// </editor-fold>

}
