package edu.metrostate.ics425.jtp307.reversi.servlets;

import edu.metrostate.ics425.jtp307.reversi.model.Reversi;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Processes a request to place a {@link Piece} on the {@link Reversi} board by the current player.
 * Because of the simplicity of all Reversi actions, this uses sessions and redirects instead of responses and control forwarding. (As such, there is only one view; the different servlets operate on the model, and then redirect back to that one view.)
 * 
 * @author joseph
 */
public class MoveServlet extends HttpServlet {

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
        
        
        //**** DEFINE RESOURCES ****//
        
        Reversi reversiInstance = (Reversi) request.getSession().getAttribute("reversiInstance");
        Collection<String> errMsgs = new LinkedList<>();
        
        
        
        //**** RETRIEVE FORM DATA ****//
        
        /* Get our parameter variables,
         * performing trims and making nulls empty strings */
        final String xPosString = request.getParameter("xPos") != null
                ? request.getParameter("xPos").trim()
                : "";
        
        final String yPosString = request.getParameter("yPos") != null
                ? request.getParameter("yPos").trim()
                : "";
        

        
        //**** PARSE FORM DATA ****//

        int xPos = 0;
        try {
            xPos = Integer.parseInt(xPosString);
        } catch (NumberFormatException ex) {
            errMsgs.add("Invalid x position.");
        }
        
        
        int yPos = 0;
        try {
            yPos = Integer.parseInt(yPosString);
        } catch (NumberFormatException ex) {
            errMsgs.add("Invalid y position.");
        }
        
        
        
        //**** SET PIECE ****//
    
        if (errMsgs.isEmpty()) {
            if (!reversiInstance.setPiece(xPos, yPos)) {
                errMsgs.add("That move is not legal.");
            }
        }
        

        
        //**** REDIRECT BACK TO BOARD ****//
        System.out.println(reversiInstance);
        request.getSession().setAttribute("errMsgs", errMsgs);
        response.sendRedirect("../Reversi");
    
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
        return "Short description";
    }// </editor-fold>

}
