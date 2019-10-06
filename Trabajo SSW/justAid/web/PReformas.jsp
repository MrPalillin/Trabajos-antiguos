<%@page import="modelo.Atencion"%>
<%@page import="modelo.Cliente"%>
<!DOCTYPE html>
<%@page import="modelo.Profesional"%>
<%Profesional pr = (Profesional) session.getAttribute("profesional");%>
<%Cliente c = (Cliente) session.getAttribute("cliente");%>
<%Atencion at = (Atencion) session.getAttribute("atencion");%>
<html>
    <head>
        <title>JustAid</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="./css/sanitario.css" rel ="stylesheet" type="text/css" />


    </head>
    <body>
        <div class = "recuadro">
            <div class = "persona">
                <p>
                    Profesional: <%=pr.getNombre()%>&nbsp;<%=pr.getAp1()%>&nbsp;<%=pr.getAp2()%>
                </p>
            </div>
            <div class = "atendido">
                <p>
                   Cliente: <%=c.getNombre()%>&nbsp;<%=c.getAp1()%>&nbsp;<%=c.getAp2()%>
                </p>
            </div>
            <div class="persona">
                <p>
                   Fecha y hora: <%=at.getHora()%>
                </p>
            </div>
                <div class="persona">
                    <p>
                        Domicilio: <%=c.getDireccion()%>
                    </p>
                </div>
        <form method="post" action="AtiendePeticion">
            <a href="./CargaLista">
                <input class="botones" type="button" value="Atras" name="Atras" />
            </a>
            <a href="BorraPeticion">
                <input class="botones" type="button" value="Atender" name="Atender" />
            </a>
        </form>
        <div class="infoadicional">
            <p>
                <%=at.getObservaciones()%>
            </p>
        </div>
    </div>
</body>
</html>
