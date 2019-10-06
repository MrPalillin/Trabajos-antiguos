
<%@page import="modelo.Profesional"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%Cliente cli = (Cliente) session.getAttribute("cliente");%>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8">
        <title>Servicios</title>
        <link rel="stylesheet" type="text/css" href="css/Servicios.css">
    </head>
    <body>
        <div class="bodyReformas">
            <div id="contenedor" class="contenedorclearfix">
                <form method = "post" action="Exit">
                    <div class="div-migas">
                        <span class = "estas">Se encuentra en:</span>
                        <a href="./listServicios.jsp" class="detalles">Pagina Personal ></a>
                        <strong class = "actual">Reformas</strong>
                        <label class = "jNombre"><%=cli.getNombre()%></label> 

                        <input class="botones" type="submit" value="Exit" >
                    </div>
                </form>
                <form method="post" action="GeneraAtencion">
                    <div class ="cuerpo">
                        <div class ="imagenprof"> 
                            <img class="imagenpro" src="imagenes/reforma.jpg"  alt="logo_inicio" >
                        </div>
                        <div class= "eleccion">
                            <span>Elija un profesional que le atienda:</span><br>
                            <select name=profesionales>
                                <%
                                    ArrayList<Profesional> profs = (ArrayList<Profesional>) request.getAttribute("profesionales");

                                    String nombre = "", id = "";
                                    for (int i = 0; i < profs.size(); i++) {
                                        nombre = profs.get(i).getNombre();
                                        id = profs.get(i).getDni();
                                %>
                                <option value="<%=id%>">  <%=nombre%></option>   
                                <%
                                    }
                                %>               
                            </select><br>
                            <span>Seleccione el dia y hora de la cita:</span>
                            <input type="date" name="fecha" required>
                            <input type="time" name="hora" required>
                        </div>
                        <div class="peticion">
                            <p>Escriba las reformas a solicitar,y el motivo</p>
                            <textarea name="observaciones" required></textarea>
                        </div>
                        <div class="center-input">
                            <a href="./listServicios.jsp">
                                <button style="trial" type="button" value="submit">Atrás</button>
                            </a>			
                            <button style="trial" type="submit" value="submit">Enviar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>    
    </body>
</html>
