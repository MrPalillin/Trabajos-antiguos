<%-- 
    Document   : listServicios
    Created on : 24-may-2018, 16:02:13
    Author     : Enrique
--%>
<%@page import="modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%Cliente cli = (Cliente) session.getAttribute("cliente");%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="css/lServicios.css">
        <title>Lista de servicios</title>
    </head>
    <body>

        <h1>Escoja un servicio</h1>
        <h4 class="jNombre">Usuario:</h4>
        <label class = "jNombre"><%=cli.getNombre()%></label>  
        <form method = "post" action="Exit">
            <input class="botones" type="submit" value="Exit" >
        </form>

        <div id ="serv1">
            <div class="serv">
                <form action="CServicio" method="post">
                    <input type="hidden" name="prof" value="reformas">
                    <input type="image" src="imagenes/reforma.jpg" alt="Reforma"
                           width="380" height="190">
                    <div class="desc"><b>Reformas a domicilio</b></div>
                </form>
            </div>
        </div>

        <div id ="serv2">
            <div class="serv">
                <form action="CServicio" method="post">
                    <input type="hidden" name="prof" value="deporte">
                    <input type="image" src="imagenes/deporte.jpg"
                           alt="Deporte" width="380" height="190">
                    <div class="desc"><b>Asistencia Deportiva</b></div>
                </form>
            </div>
        </div>

        <!-- EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE -->
        <div id ="serv3">
            <div class="serv">
                <form action="CServicio" method="post">
                    <input type="hidden" name="prof" value="atencion">
                    <input type="image" src="imagenes/compania.jpg"
                           alt="Compañia" width="380" height="190">
                    <div class="desc"><b>Atención Afectiva</b></div>
                </form>
            </div>
        </div>

        <div id ="serv4">
            <div class="serv">
                <form action="CServicio" method="post">
                    <input type="hidden" name="prof" value="comida">
                    <input type="image" src="imagenes/comida-a-domicilio.jpg"
                           alt="Comida" width="380" height="190">
                    <div class="desc"><b>Comida a Domicilio</b></div>
                </form>
            </div>
        </div>

        <div id ="serv5">
            <div class="serv">
                <form action="CServicio" method="post">
                    <input type="hidden" name="prof" value="medico">
                    <input type="image" src="imagenes/medico.jpg" alt="Médico"
                           width="380" height="190">
                    <div class="desc"><b>Servicio médico</b></div>
                </form>
            </div>
        </div>

        <div id ="serv6">
            <div class="serv">
                <form action="CServicio" method="post">
                    <input type="hidden" name="prof" value="hobbies">
                    <input type="image" src="imagenes/maqueta.jpg" alt="Hobbies"
                           width="380" height="190">
                    <div class="desc"><b>Acompañamiento Para Hobbies</b></div>
                </form>
            </div>
        </div>

    </body>
</html>
