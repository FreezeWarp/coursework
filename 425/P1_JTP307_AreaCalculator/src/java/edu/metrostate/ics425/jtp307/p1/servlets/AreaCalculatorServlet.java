/*
 *  Copyright © 2017 Ralph A. Foy. All Rights Reserved.
 *  No part of this document may be reproduced without 
 *     written consent from the author.
 */
package edu.metrostate.ics425.jtp307.p1.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.metrostate.ics425.jtp307.p1.model.AreaCalculatorBean;

/**
 * A servlet that processes submissions from Product.jsp, using ProductBean to model the data.
 * This is copied from P1's AreaCalculatorServlet.java.
 * 
 * @author rfoy; modified by Joseph T. Parsons
 */
public class AreaCalculatorServlet extends HttpServlet {

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

        final String heightStr = request.getParameter("height");
        final String widthStr = request.getParameter("width");
        List<String> errMsgs = new LinkedList<>();
        
        AreaCalculatorBean calculator = new AreaCalculatorBean();
        
        if (heightStr != null && heightStr.trim().matches("\\d+")){
            calculator.setHeight(Integer.parseInt(heightStr.trim()));
        } else {
            errMsgs.add("Height is empty or not valid");
        }
        
        if (widthStr != null && widthStr.trim().matches("\\d+")){
            calculator.setWidth(Integer.parseInt(widthStr.trim()));
        } else {
            errMsgs.add("Width is empty or not valid");
        }
        
        if (errMsgs.isEmpty()) {
            request.setAttribute("areaCalculator", calculator);
        } else {
            request.setAttribute("errMsgs", errMsgs);
        }
        
        request.getRequestDispatcher("/AreaCalculator.jsp").forward(request, response);
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
        return "This computes rectangular area using AreaCalculatorBean given input height and width from AreaCalculator.jsp.";
    }// </editor-fold>

}
