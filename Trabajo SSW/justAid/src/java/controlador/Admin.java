
package controlador;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Profesional;
import persistencia.ProfesionalBD;
/**
 *
 * @author Enrique
 */
@WebServlet(urlPatterns = {"/Admin"})
public class Admin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String url = "";
        String nombre = request.getParameter("Nombre");
        String ap1 = request.getParameter("Apellido1");
        String ap2 = request.getParameter("Apellido2");
        String correo = request.getParameter("Email");
        String telefono = request.getParameter("Movil");
        String fechaNacimiento = request.getParameter("Fecha");
        String contraseña = request.getParameter("Contrasena");
        String profesion = request.getParameter("Profesion");
        String dni = request.getParameter("Dni");
        String genero = request.getParameter("gender");
        boolean fallo = false;
        Profesional prof1 = new Profesional(nombre,ap1,ap2,contraseña,
                correo,fechaNacimiento,telefono,dni,genero,profesion);
        for (int i = 0 ; i < dni.length() ; i++){
            if (dni.charAt(i) == ' ') {
                fallo = true;
                request.setAttribute("spacesdni", "fail"); }
        }
        if (nombre.length() < 2){
            fallo = true;
            request.setAttribute("shortnombre", "fail");
        }
        
        if (dni.length() < 5){
            fallo = true;
            request.setAttribute("shortdni", "fail");
        }
        if (fallo) {
            url = "/Administrador.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }else if(!ProfesionalBD.compruebaNifProfesional(dni)){
                ProfesionalBD.insert(prof1);
                url = "/Administrador.jsp";
                RequestDispatcher dispatcher= getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("usuario", "fail");
                url = "/Administrador.jsp";
                RequestDispatcher dispatcher= getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
        /*if (request.getParameter("button1") != null) {
            
        } else if (request.getParameter("button2") != null) {
            ProfesionalBD.deleteProfesional(dni);
            RequestDispatcher dispatcher= getServletContext().getRequestDispatcher("/Administrador.jsp");
            dispatcher.forward(request, response);
        } 
        }*/
        
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
