package edu.metrostate.ics425.jtp307.reversi.servlets;

import edu.metrostate.ics425.jtp307.reversi.model.Reversi;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Processes "give up" requests, which end the game early by invoking {@link Reversi#giveUp()}.
 * Because of the simplicity of all Reversi actions, this uses sessions and redirects instead of responses and control forwarding. (As such, there is only one view; the different servlets operate on the model, and then redirect back to that one view.)
 * 
 * @author Joseph T. Parsons
 */
public class GiveUpServlet extends HttpServlet {

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

        
        //**** Invoke the give up method to end the game. ****//
        reversiInstance.giveUp();
        
        
        //**** REDIRECT BACK TO BOARD ****//
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
