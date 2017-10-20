package edu.metrostate.ics425.jtp307.fizzbuzz.controller;

import edu.metrostate.ics425.jtp307.fizzbuzz.model.FizzBuzzBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * A servlet that generates a FizzBuzzBean instance and returns its results, provided a n value is in form data.
 * 
 * @author Joseph T. Parsons
 */
public class FizzBuzzServlet extends HttpServlet {

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
        
        /**
         * Error messages we can return to the JSP if problems arise.
         */
        List<String> errMsgs = new LinkedList<String>();
        
        /**
         * The value of n, corresponding to {@link FizzBuzzBean#n}, assuming it was provided.
         */
        int nInt = 0;


        
        //**** RETRIEVE FORM DATA ****//
        
        /**
         * The n form value as a string. It will be put into nInt, if possible.
         */
        final String n = (String) request.getParameter("n");

        
    
        //**** ONLY PROCEED IF FORM SUBMITTED ****//

        if (n != null) {
            //**** VALIDATE FORM DATA ****//
            
            try {
                nInt = Integer.parseInt(n);
                
                if (nInt <= 0) { // Make sure n is positive.
                   errMsgs.add("n must be positive.");
                }
                else if (nInt > 50) { // Make sure n is positive.
                   errMsgs.add("n must be at most 50.");
                }
            } catch (Exception ex) {
                errMsgs.add("n cannot be parsed as an integer.");
            }
            
            
               
            //**** PREPARE RESPONSE ****//

            if (errMsgs.isEmpty()) {
                /* Create Bean */
                FizzBuzzBean fizzBuzzBean = new FizzBuzzBean();
                
                /* Set Bean Properties */
                System.out.println(nInt);
                fizzBuzzBean.setN(nInt);
                
                /* Set the catalogue's collection as the products attribute in response to the request. */
                request.setAttribute("fizzBuzzResults", fizzBuzzBean.getResults());
                for (String s : fizzBuzzBean.getResults()) {
                    System.out.println(s);
                }
            }
            
            else {
                request.setAttribute("errMsgs", errMsgs);
            }
        }
        
        
        
        //**** SEND RESPONSE TO JSP ****//

        /* Send our response to the JSP. */
        request.getRequestDispatcher("/FizzBuzz.jsp").forward(request, response);
        

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
        return "This computes FizzBuzz";
    }// </editor-fold>

}
