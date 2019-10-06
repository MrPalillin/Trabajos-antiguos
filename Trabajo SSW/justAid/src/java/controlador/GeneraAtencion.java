/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Atencion;
import modelo.Cliente;
import persistencia.AtencionBD;

/**
 *
 * @author miguel
 */
@WebServlet(name = "GeneraAtencion", urlPatterns = {"/GeneraAtencion"})
public class GeneraAtencion extends HttpServlet {

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
        
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Cliente c = (Cliente) session.getAttribute("cliente");
        
        String idPro = request.getParameter("profesionales");
        String[] f = request.getParameter("fecha").split("-");
        String[] h = request.getParameter("hora").split(":");
        String observaciones = request.getParameter("observaciones");
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(GregorianCalendar.YEAR, Integer.parseInt(f[0]));
        cal.set(GregorianCalendar.MONTH, (Integer.parseInt(f[1]))-1);
        cal.set(GregorianCalendar.DAY_OF_MONTH, (Integer.parseInt(f[2])));
        cal.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(h[0]));
        cal.set(GregorianCalendar.MINUTE, Integer.parseInt(h[1]));
        Timestamp fecha = new Timestamp(cal.getTimeInMillis());
        
        System.out.println("ESSO"+c.getDni());
        Atencion a = new Atencion(c.getDni(), idPro, fecha, observaciones);
        AtencionBD.insert(a);
        
        RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/listServicios.jsp");
        dispatch.forward(request, response);
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
