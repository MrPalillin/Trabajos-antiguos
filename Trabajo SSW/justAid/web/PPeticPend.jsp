<%@page import="persistencia.AtencionBD"%>
<%@page import="persistencia.ClienteBD"%>
<%@page import="modelo.Profesional"%>
<%@page import="modelo.Atencion"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%Profesional pro = (Profesional) session.getAttribute("profesional");%>
<!DOCTYPE html>
<html>
    <head>
        <title>JustAid: Solicitudes</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="./css/peticPend.css" rel ="stylesheet" type="text/css" />
    </head>
    <body>
        <div class = "recuadro" name = "recuadro">
            <div class="text-aligner">
                <h1 id="text-title">Solicitudes pendientes</h1>
                <h2 id="professional-name"><%=pro.getNombre()%> <%=pro.getAp1()%> <%=pro.getAp2()%></h2>
            </div>
            <div id="exit-box">
                <form method = "post" action="Exit">
                    <input class="boton-salir" type="submit" value="Salir" >
                </form>
            </div>

            <div id="solicitudes-pendientes" name="solPend">
                <%
                    ArrayList<ArrayList<String>> ats = (ArrayList<ArrayList<String>>) request.getAttribute("Petic");

                    String n, f, obs, idAt;
                    for (int i = 0; i < ats.size(); i++) {
                        n = ats.get(i).get(0) + " " + ats.get(i).get(1) + " " + ats.get(i).get(2);
                        f = ats.get(i).get(3);
                        obs = ats.get(i).get(4);
                        idAt = ats.get(i).get(5).toUpperCase();
                %>
                <form method = "post" action="PeticPend">
                    <div class="solicitud">
                        <div><b name="nombre"><%=n%></b></div>
                        <div><input type="hidden" name="idAt" value="<%=idAt%>"></div>
                        <div><p name="fecha"><%=f%></p></div>
                        <div><p name="observaciones"><%=obs%></p></div>
                        <input class="boton-solicitudes" type="submit" value="Atender">
                    </div>
                </form>
                <%
                    }
                %>
            </div>
        </div>
    </body>
</html>

