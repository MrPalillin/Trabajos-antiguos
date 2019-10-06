/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import persistencia.ClienteBD;

/**
 *
 * @author daniel
 */
@WebServlet(name = "BorraPeticion", urlPatterns = {"/BorraPeticion"})
public class BorraPeticion extends HttpServlet {

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

        HttpSession sesion = request.getSession();

        int idAt = Integer.parseInt(request.getParameter("idAt"));
        AtencionBD.borraAtencion(idAt);

        /*EEE*/
        Profesional prof = (Profesional) sesion.getAttribute("profesional");
        ArrayList<Atencion> atencsArray = AtencionBD.getAtencionesProfesional(prof.getDni());
        ArrayList<ArrayList<String>> atencs = new ArrayList<>();
        Cliente c;
        String d;
        Timestamp cal;

        /*atencs contiene:
                    [0] nombre de cliente
                    [1] ap1 de cliente
                    [2] ap2 de cliente
                    [3] fecha de la atencion
                    [4] observaciones de la atencion
                    [5] id de atencion
         */
        for (int i = 0; i < atencsArray.size(); i++) {
            atencs.add(new ArrayList<>());
            c = ClienteBD.getCliente(atencsArray.get(i).getIdCliente());
            cal = atencsArray.get(i).getHora();
            d = new java.text.SimpleDateFormat("HH:mm:ss - dd/MM/yyyy ").format(cal);

            atencs.get(i).add(c.getNombre());
            atencs.get(i).add(c.getAp1());
            atencs.get(i).add(c.getAp2());
            atencs.get(i).add(d);
            atencs.get(i).add(atencsArray.get(i).getObservaciones());
            atencs.get(i).add(Integer.toString(atencsArray.get(i).getIdAtencion()));
        }
        request.setAttribute("Petic", atencs);
        HttpSession session = request.getSession();
        session.setAttribute("profesional", prof);


        /*EEE*/
        sesion.removeAttribute("atencion");
        RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/PPeticPend.jsp");
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
