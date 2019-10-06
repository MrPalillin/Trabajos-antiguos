/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Atencion;
import modelo.Cliente;
import modelo.Profesional;
import persistencia.AtencionBD;

/**
 *
 * @author miguel
 */
@WebServlet(name = "PeticPend", urlPatterns = {"/PeticPend"})
public class PeticPend extends HttpServlet {

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
        
        HttpSession sesion = request.getSession();
        String idAt = request.getParameter("idAt");
        Profesional p = (Profesional) sesion.getAttribute("profesional");
        Atencion atencion = AtencionBD.getAtencion(idAt);
        Cliente cl = AtencionBD.getClientePorAtencion(idAt);
        request.setAttribute("atencion", atencion);
        request.setAttribute("cliente", cl);
        request.setAttribute("profesional", p);
        String especializacion = p.getProfesion();/*
        String url = "";
        switch (especializacion) {
            case "atencion":
                url = "/PAtencion.jsp";
                break;
            case "comida":
                url = "/PComida.jsp";
                break;
            case "deporte":
                url = "/PDeportivo.jsp";
                break;
            case "hobbies":
                url = "/PHobbies.jsp";
                break;
            case "reformas":
                url = "/PReformas.jsp";
                break;
            case "medico":
                url = "/PMedico.jsp";
                break;
        }*/
        String url="/PAtencion.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
        
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
