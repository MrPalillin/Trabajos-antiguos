<%@page import="controlador.Admin" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="css/seg.css">
        <title>Registrar Profesional</title>
        <script>
            function validarFormulario() {
                var x = document.form1.Nombre.value;
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

                var y = document.form1.Dni.value;
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
            }
        </script>
    </head>
    <body>
        <h1 class="feature-section-title">Registro de Profesionales</h1>
        <form name = "form1" method = "post" action="Admin" onsubmit="return validarFormulario()">
            <% boolean error = false;
                String usuario = (String) request.getAttribute("usuario");
            %>

            <div class="client">
                <div id="izq">
                    <h4 class="feature-section-inicio">Nombre</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="text" name="Nombre" placeholder="Nombre de usuario" required ><br/>

                    <h4 class="feature-section-inicio">Apellido1</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="text" name="Apellido1" placeholder="Apellido 1" required ><br/>

                    <h4 class="feature-section-inicio">Apellido2</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="text" name="Apellido2" placeholder="Apellido 2" required ><br/>

                    <h4 class="feature-section-inicio">Email</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="text" name="Email" placeholder="Correo electrónico" required ><br/>

                    <h4 class="feature-section-inicio">Contraseña</h4>
                    <input class="feature-section-caja-reg" id ="caja-iz" type="password" name="Contrasena" placeholder="Contraseña" required ><br/>

                </div>

                <div id="dcha">
                    <h4 class="feature-section-inicio">Fecha de nacimiento</h4>
                    <input class="feature-section-caja-reg" id ="caja-der" type="text" name="Fecha" placeholder="Fecha de nacimiento" required ><br/>
                    <h4 class="feature-section-inicio">Movil</h4>
                    <input class="feature-section-caja-reg" id ="caja-der" type="text" name="Movil" placeholder="Movil" required ><br/>
                    <h4 class="feature-section-inicio">Profesion</h4>
                    <select class="feature-section-caja-reg" id ="caja-der" name="Profesion">
                        <option value="medico" selected>Médico</option>
                        <option value="reformas">Reformas</option>
                        <option value="atencion">Acompañante</option>
                        <option value="comida">Repartidor</option>
                        <option value="deporte">Entrenador</option>
                        <option value="hobbies">Hobbies</option>
                    </select>                    
                    <h4 class="feature-section-inicio">DNI</h4>
                    <input class="feature-section-caja-reg" id ="caja-der" type="text" name="Dni" placeholder="Dni" required ><br/>
                    <%
                        if (usuario != null) {
                    %>
                    <error class="wrongiz" onload="mostrarRegistro()"> Este usuario ya esta en uso </error>
                        <%
                                error = true;
                            }
                        %>                    
                    <h4 class="feature-section-inicio">Genero</h4>
                    <div class="genero">
                        <label class="sexo">Hombre<input class="radio-masc" type="radio" name="gender" value="Hombre"></label>
                        <label class="sexo">Mujer<input class="radio-gender" type="radio" name="gender" value="Mujer"></label>
                    </div>
                </div>

                <div class="center-input">
                    <input class="botones" type="submit" value="Enviar" >
                    <button class="botones" type="reset" value="Restablecer">Restablecer</button>
                    <a href="datosProfesional?var1=borrar">
                        <input class="botones" type="button" value="Borrar" name="Borrar" />
                    </a>
                    <a href="Exit">
                        <input class="botones" type="button" value="Salir" name="Salir" />
                    </a>                
                </div>
            </div>
        </form>
    </body>
</html>

