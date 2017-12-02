package edu.metrostate.ics425.jtp307.reversi.servlets;

import edu.metrostate.ics425.jtp307.reversi.model.Reversi;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A basic servlet that creates a new Reversi instance in the session.
 * Because of the simplicity of all Reversi actions, this uses sessions and redirects instead of responses and control forwarding. (As such, there is only one view; the different servlets operate on the model, and then redirect back to that one view.)
 * 
 * @author Joseph T. Parsons
 */
@WebServlet(name = "NewGameServlet", urlPatterns = {"/Reversi/NewGame"})
public class NewGameServlet extends HttpServlet {

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
        final String widthString = request.getParameter("width") != null
                ? request.getParameter("width").trim()
                : "";
        
        final String heightString = request.getParameter("height") != null
                ? request.getParameter("height").trim()
                : "";
        
        
        
        //**** PARSE FORM DATA ****//
        
        int width = 0;
        try {
            width = Integer.parseInt(widthString);
            
            // The minimum is to prevent obvious errors. The maximum is to ensure the game remains response. The even-ness requirement is simply to ensure that everything is displayed correctly; it is not really required, but helps.
            if (width < 4 || width > 16 || width % 2 != 0) {
                errMsgs.add("Width must be an even number between 4 and 16.");
            }
        } catch (NumberFormatException ex) {
            errMsgs.add("Invalid width.");
        }
        
        
        int height = 0;
        try {
            height = Integer.parseInt(heightString);
            
            // The minimum is to prevent obvious errors. The maximum is to ensure the game remains response. The even-ness requirement is simply to ensure that everything is displayed correctly; it is not really required, but helps.
            if (height < 4 || height > 16 || height % 2 != 0) {
                errMsgs.add("Height must be an even number between 4 and 16.");
            }
        } catch (NumberFormatException ex) {
            errMsgs.add("Invalid height.");
        }
        
        
        
        //**** CREATE NEW GAME ****//
        
        if (errMsgs.isEmpty()) {
            request.getSession().setAttribute("reversiInstance", new Reversi(width, height));
        }
        

        
        //**** REDIRECT BACK TO GAME ****//

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
