<%-- 
    Document   : borrarProfesional
    Created on : 12-jun-2018, 10:39:15
    Author     : Enrique
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlador.EliminarProfesional" %>
<%@page import="persistencia.ProfesionalBD" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="css/admin.css">
    </head>
    <body>
        <table>
            <th>DNI</th>
            <th>Nombre</th>
        <%
            
            int num = (int)request.getAttribute("numeroProfesionales");
            ArrayList<String> id = (ArrayList)request.getAttribute("dni");
            String iden;
        while (num > 0) {
            iden = id.get(num-1);
            String nombre = ProfesionalBD.getNombre(iden);
        %>
        <p> 
        
        <tr>
            <td><%= id.get(num-1) %></td>
            <td><%= nombre%></td>
            <td><a href="EliminarProfesional?var1=<%= id.get(num-1) %>">
                    <button>Borrar</button>
                </a></td>
        </tr>
        </p>
        <%
            num--;
        }
        %>
        </table>
        <p>
            <a href="Administrador.jsp">
                    <button class="botonesBorrar">Atras</button>
            </a>
            <a href="Exit">
                    <button class="botonesBorrar">Salir</button>
            </a>
        </p>
    </body>
</html>
