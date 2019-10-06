<%-- 
    Document   : registro
    Created on : 08-jun-2018, 18:02:13
    Author     : Enrique
--%>
<%@page import="controlador.Registro" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">        
        <link rel="stylesheet" type="text/css" href="css/seg.css">
        <title>Registro</title>
        <script>
            function validarFormulario() {
                var x = document.formre.Nombre.value;
                for (var i = 0, len = x.length; i < len; ++i) {
                    if (x.charAt(i) === ' ') {
                        alert('Nombre no puede contener espacios');
                        field.value = "";
                        field.focus();
                    }
                }
                if (x.length <= 2) {
                    alert('Nombre debe de tener mas de 3 elementos');
                    field.value = "";
                    field.focus();
                }

                var y = document.formre.Dni.value;
                for (var i = 0, len = y.length; i < len; ++i) {
                    if (y.charAt(i) === ' ') {
                        alert('Dni no puede contener espacios');
                        field.value = "";
                        field.focus();
                    }
                }
                if (y.length <= 5) {
                    alert('Dni debe de tener mas de 5 elementos');
                    field.value = "";
                    field.focus();
                }
                var z = document.formre.Correo.value;
                for (var i = 0, len = y.length; i < len; ++i) {
                    if (z.charAt(i) === ' ') {
                        alert('El correo no puede contener espacios');
                        field.value = "";
                        field.focus();
                    }
                }
                var t = document.formre.Telefono.value;
                for (var i = 0, len = y.length; i < len; ++i) {
                    if (t.charAt(i) === ' ') {
                        alert('El telefono no puede contener espacios');
                        field.value = "";
                        field.focus();
                    }
                }
            }
        </script>
    </head>
    <body  onload="mostrarRegistro">
        <h1 class="feature-section-title">Registro de cliente</h1>
        <form name = "formre" method = "post" action="Registro" onsubmit="return validarFormulario()">
            <%
                boolean error = false;
                String usuario = (String) request.getAttribute("usuario");
            %>
            <div class="client">
                <div id = "izq">
                    <h4 class="feature-section-inicio">Nombre</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="text" name="Nombre" placeholder="Nombre de usuario"  required><br/>
                    <h4 class="feature-section-inicio">Apellido 1</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="text" name="Apellido1" placeholder="Apellido 1" required><br/>
                    <h4 class="feature-section-inicio">Apellido 2</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="text" name="Apellido2" placeholder="Apellido 2"><br/>
                    <h4 class="feature-section-inicio">Correo electrónico</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="text" name="Correo" placeholder="Correo electrónico" required ><br/>
                    <h4 class="feature-section-inicio">Dirección</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="text" name="Direccion" placeholder="Dirección" required ><br/>
                    <h4 class="feature-section-inicio">Teléfono</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="text" name="Telefono" placeholder="Teléfono"  required><br/>
                    <h4 class="feature-section-inicio">Fecha de nacimiento</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="date" name="Fecha" placeholder="Fecha de nacimiento"  required><br/>
                </div>
                <div id="dcha">
                    <h4 class="feature-section-inicio">Contraseña</h4>
                    <input class="feature-section-caja-reg" id ="caja-der" type="password" name="Contrasena" placeholder="Contraseña" required password><br/>
                    <h4 class="feature-section-inicio">Minusvalia</h4>
                    <input class="feature-section-caja-reg" id ="caja-der" type="text" name="Minusvalia" placeholder="Introduzca su minusvalia" required ><br/>
                    <h4 class="feature-section-inicio">DNI</h4>
                    <input class="feature-section-caja-reg" id ="caja-der" type="text" name="Dni" placeholder="DNI" required ><br/>

                    <h4 class="feature-section-inicio">Género</h4>
                    <div class="genero">
                        <label class="sexo">Hombre<input class="radio-masc" type="radio" name="gender" value="Hombre"></label>
                        <label class="sexo">Mujer<input class="radio-gender" type="radio" name="gender" value="Mujer"></label>
                    </div>
                    <%
                        if (usuario != null) {
                    %><error class="wrongiz" onload="mostrarRegistro()"> Este usuario ya esta en uso </error><%
                            error = true;
                        }
                        %>
                </div>
                <div class="center-input">
                    <input class="botones" type="submit" value="Enviar" >
                    <button class="botones" type="reset" value="Restablecer">Restablecer</button>
                    <a href="Exit">
                        <input class="botones" type="button" value="Salir" name="Salir">
                    </a>
                </div>
            </div>
        </form>
    </body>
</html>
