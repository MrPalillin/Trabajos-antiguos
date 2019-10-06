
package controlador;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Cliente;
import persistencia.ClienteBD;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
/**
 *
 * @author Enrique
 */
@WebServlet(urlPatterns = {"/Registro"})
public class Registro extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String url = "";
        String nombre = request.getParameter("Nombre");
        String ap1 = request.getParameter("Apellido1");
        String ap2 = request.getParameter("Apellido2");
        String correo = request.getParameter("Correo");
        String direccion = request.getParameter("Direccion");
        String telefono = request.getParameter("Telefono");
        String fechaNacimiento = request.getParameter("Fecha");
        String contraseña = request.getParameter("Contrasena");
        String minusvalia = request.getParameter("Minusvalia");
        String dni = request.getParameter("Dni");
        String genero = request.getParameter("gender");
        Cliente cliente1 = new Cliente(direccion,nombre,ap1,ap2,contraseña,
                correo,fechaNacimiento,telefono,minusvalia,dni,genero);
        boolean fallo = false;

        for (int i = 0 ; i < nombre.length() ; i++){
            if (nombre.charAt(i) == ' ') {
                fallo = true;
                request.setAttribute("spaces", "fail");}
        }
        for (int i = 0 ; i < ap1.length() ; i++){
            if (ap1.charAt(i) == ' ') {
                fallo = true;
                request.setAttribute("spaces", "fail"); }
        }
        for (int i = 0 ; i < ap2.length() ; i++){
            if (ap2.charAt(i) == ' ') {
                fallo = true;
                request.setAttribute("spaces", "fail");}
        }
        for (int i = 0 ; i < correo.length() ; i++){
            if (correo.charAt(i) == ' ') {
                fallo = true;
                request.setAttribute("spaces", "fail"); }
        }
       /* int contador = 0;
        for (int i = 0 ; i < correo.length() ; i++){
            if (correo.charAt(i) == '@') {
                contador++;}
        }
        if(contador == 0){
             fallo = true;
                request.setAttribute("chars", "fail");; 
            }*/
        for (int i = 0 ; i < telefono.length() ; i++){
            if (telefono.charAt(i) == ' ') {
                fallo = true;
                request.setAttribute("spaces", "fail"); }
        }
        for (int i = 0 ; i < contraseña.length() ; i++){
            if (contraseña.charAt(i) == ' ') {
                fallo = true;
                request.setAttribute("spaces", "fail"); }
        }
        for (int i = 0 ; i < dni.length() ; i++){
            if (dni.charAt(i) == ' ') {
                fallo = true;
                request.setAttribute("spaces", "fail"); }
        }
        if (nombre.length() < 3){
            fallo = true;
            request.setAttribute("shortnombre", "fail");
        }
        
        if (dni.length() < 5){
            fallo = true;
            request.setAttribute("shortdni", "fail");
        }

        /*if(ClienteBD.compruebaNifCliente(dni)){
            url = "/registro.html";
        }else{
            ClienteBD.insert(cliente1);
            HttpSession sesion = request.getSession();
            sesion.setAttribute("cliente",cliente1);
            url = "/listServicios.jsp";
        }*/
        //RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        //dispatcher.forward(request, response);
        
        if (fallo) {
            RequestDispatcher dispatcher= getServletContext().getRequestDispatcher("/registro.jsp");
            dispatcher.forward(request, response);
                   
        } else if(!ClienteBD.compruebaNifCliente(dni)){

                ClienteBD.insert(cliente1);
                HttpSession sesion = request.getSession();
                sesion.setAttribute("cliente",cliente1);
                url = "/listServicios.jsp";
                RequestDispatcher dispatcher= getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            } else {
                // hay que informar de que el nombre de usuario esta escogido 
                request.setAttribute("usuario", "fail");
                url = "/registro.jsp";
                RequestDispatcher dispatcher= getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
        }
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
    
    
}
