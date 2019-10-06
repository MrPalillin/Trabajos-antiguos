<!DOCTYPE html>
<html lang="en-US">
    <head>
        <meta charset="utf-8">        
        <link rel="stylesheet" type="text/css" href="css/seg.css">
        <title>Login</title>
    </head>
    <body>

        <div class="left-input">
            <div id="feature-section">
                <h1 class="feature-section-title">Login</h1>
            </div>
        </div>
        <form method="post" action="Login">
            <div class="col" id = "uno">
                <div class="client">
                    <h4 class="feature-section-inicio">Usuario</h4>
                    <input class="feature-section-caja-login" type="text" name="name" placeholder="DNI/NIF" required ><br/>
                    <h4 class="feature-section-inicio">Contraseña</h4>
                    <input class="feature-section-caja-login" type="password" name="casillaContrasena" placeholder="Contraseña" required ><br/>                                  
                    <div class="center-input-login">
                        <button class ="botones" type="submit" value="submit">Enviar</button>
                        <a href="./Inicio.html">
                            <button class ="botones" type="button" value="submit">Atrás</button>
                        </a>
                    </div>         
                </div>       

            </div><!--End of col-2-->
        </form>

    </body>

</html>
