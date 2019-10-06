var angularApp = angular.module("moduloPrincipal", ["ui.bootstrap", 'ngFileUpload'])
        .constant("baseUrl", "http://localhost:8080/ProyectoPGPAngular/webresources/paraAngular")
        .controller("pgpCtrl", function ($scope, $http, $filter, $window, baseUrl) {
            
            $scope.displayMode = "login"; //mostrar vista inicial
            
            //Ajusta el formato de la fecha que se obtiene del calendario y de los insetrts en la base de datos
            $scope.processDate = function(dt){
                return $filter('date')(dt, 'yyyy-MM-dd');
            };
            
            // ============================Funciones adminPage============================
            
            $scope.getJefesProyectoDisponibles = function(){
                
                $http({
                    method: "GET",
                    url: baseUrl + "/jefesproyectodisp"
                }).then( // peticion correcta
                    function(response){
                    $scope.jefesproyectodisp = response.data;            
                    $scope.jefesArray = Object.keys($scope.jefesproyectodisp).map(function (value, index) {
                        return { values: $scope.jefesproyectodisp[value] };
                }
            );   
                }, // peticion incorrecta
                function(response){
                    console.log("getJefesProyectoDisponibles error: " + response.statusText);
                });
            };

            $scope.addUsuario = function (dni, psswd, nombre, apll, nivel) {
                
                var nuevoUsrJson = {
                        "dni": dni,
                        "psswd": psswd,
                        "nombre": nombre,
                        "apll": apll,
                        "tipoUsr": "Desarrollador",
                        "nivel": nivel
                    };
                    
                $http.post(baseUrl+"/nuevousuario/", nuevoUsrJson).then(function(response){
                    console.log("addUsuario exito: "+ dni);
                    console.log(response.statusText);
                    $scope.creaUsuario = 'Exito';
                    $scope.displayMode = "admin";
                    if(nivel === '1')
                        $scope.getJefesProyectoDisponibles();
                
                }, function(response){
                    $scope.creaUsuario = 'Error';
                    console.log("addUsuario error: " + response.statusText);
                });
            };
            
            $scope.addProyecto = function(jefeSeleccionado,fecha_ini, descrip){
                var proyectoJson = {
                        "jefeProyecto": jefeSeleccionado,
                        "fecha_inicio": fecha_ini,
                        "descripcion": descrip
                    };
                    
                $http.post(baseUrl+"/nuevoproyecto/", proyectoJson).then(function(response){
                    
                    console.log("Proyecto creado con exito");
                    $scope.creaProyecto = 'Exito';
                    $scope.displayMode = "admin";
                
                }, function(response){
                    $scope.creaProyecto = 'Error';
                    console.log("addProyecto error: " + response.statusText);
                });  
            };
            
            // ============================Funciones login============================
            
            $scope.getUsuario = function (id, psswd) {
                $http({
                    method: "GET",
                    url: baseUrl + "/usuario/" + id + "/" + psswd
                }).then(function (response) { //login correcto
                    $scope.persona = response.data;
                    
                    if ($scope.persona.nivelUsuario === 0 ) { //es admin
                        $scope.getJefesProyectoDisponibles();
                        $scope.displayMode = "admin"; //cambiar vista
                    } else if ($scope.persona.nivelUsuario === 1 && $scope.persona.tipoUsuario === "Jefe Proyecto") { //es jefe de proyecto
                        $scope.displayMode = "boss"; //cambiar vista
                        $scope.getProyecto();
                        $scope.hideConfirmarPlanProyecto = true;
                    } else if($scope.persona.nivelUsuario > 0 && $scope.persona.tipoUsuario === "Desarrollador"){ //es desarrollador
                        $scope.getActividadesProyectosActivDev(id);
                        
                        $scope.displayMode = "dev"; //cambiar vista
                        
                        
                    }
                    console.log("getUsuario exito: " + response.statusText);

                }, function (response) { //login erroneo
                    $scope.inicioSesion = 'Not Found';
                    console.log("getUsuario error: " + response.statusText);
                });
            };
            
            // ============================Funciones bossPage============================
            
            $scope.getProyecto = function(){
                $http({
                    method: "GET",
                    url: baseUrl + "/jefe/" + $scope.persona.dni + "/proyecto"
                }).then(
                    function(response){
                    $scope.proyecto = response.data;
                    
                    
                    $scope.getDevsDisponibles();
                    
                    $scope.getParticipaciones();    
                    
                    $scope.getParticipantesActividades();
                    
                    $scope.getActividadesProyecto($scope.proyecto.idProyecto);
                    $scope.getEsfuerzosSem($scope.proyecto.idProyecto);
                }, function (response) {
                   
                    console.log("getProyecto error: " + response.statusText);
                });
            };
            
            $scope.setPlanProyecto = function(planProyecto, idProyecto){                              
                var planIDProyectoJson = {
                        "planProyecto": planProyecto,
                        "idProyecto": idProyecto
                };
                
                $http.post(baseUrl+"/proyecto/planProyecto", planIDProyectoJson).then(function(response){
                    $scope.hideConfirmarPlanProyecto = true;
                    //
                    //actualizar 
                    $scope.getActividadesProyecto(idProyecto);
                    //$scope.getEsfuerzosSem(idProyecto);
                    $scope.annadirPlan = 'Exito';
                }, function(response){
                    $scope.annadirPlan = 'Error';
                    console.log("setPlanProyecto error: " + response.statusText);
                });
            };
            
            
            $scope.getDevsDisponibles = function(){
                
                $http({
                    method: "GET",
                    url: baseUrl + "/devsdisponibles/"+$scope.proyecto.idProyecto
                }).then(
                        function(response){
                            
                            $scope.devsDisponibles = response.data;
                            
                        }, function(response){
                            $scope.ErrorDevsDisponibles = 'Not found';
                            console.log("getDevsDisponibles error:" + response.statusText);
                        });
            };
            
            $scope.getParticipaciones = function(){ //DEPRECATED
                $http({
                    method: "GET",
                    url: baseUrl + "/proyecto/" + $scope.proyecto.idProyecto + "/participaciones"
                }).then(
                    function(response){  
                        $scope.particProyecto = response.data;
                    }, function (response) {
                        $scope.particProyecto = 'Not Found';
                        console.log("getParticipaciones error: " + response.statusText);
                    });
            };
            
            $scope.setParticipacionDev = function(dev, idProyecto, porcentaje, rol){
                
                $http.post(baseUrl+"/proyecto/"+idProyecto+"/pacticdev/"+dev.dni+"/"+porcentaje+"/"+rol).then(function(response){
                    //$scope.getProyecto();
                    //dev.dni,proyecto.idProyecto
                    var index =  $scope.devsDisponibles.indexOf(dev);
                    if(index > -1)
                        $scope.devsDisponibles.splice(index, 1);
                    
                    $scope.getParticipaciones();
                    $scope.getParticipantesActividades();
                    
                    $scope.getActividadesProyecto($scope.proyecto.idProyecto);
          
                    $scope.participacionCreada = "Exito";
                }, function(response){
                    $scope.participacionCreada = "Error";
                    console.log("setPaticipacionDev error: " + response.statusText);
                });
            };
            
            $scope.getActividadesProyecto = function(idProyecto){ 
                $http({
                    method: "GET",
                    url: baseUrl + "/proyecto/" + idProyecto + "/actividades"
                }).then(
                    function(response){  
                        
                        $scope.actividadesProyecto = response.data;
                    
                }, function (response) {
                    $scope.actividadesProyecto = 'Not Found';
                    console.log("getUsuario error: " + response.statusText);
                });
            };
            
            $scope.cerrarActividad = function(activ){ //TODO probar 
                
                $http({
                    method: "PUT",
                    url: baseUrl+"/actividad/fin/" + activ.actividadPK.idProyecto +"/"+ activ.actividadPK.idActividad
                }).then(function(response) {
                    $scope.getProyecto();
                    $scope.setActividadesRetrasadas();
                    //$scope.getActividadesProyecto(activ.actividadPK.idProyecto);
                    
                    //console.log(response.statusText);
                },function(response){
                    console.log("cerrarActividad error: " + response.statusText);
                });
            };
            
            $scope.aprobarEsfuerzo = function(idProyecto, idActividad, numSemana, dni){
                $http({
                    method: "PUT",
                    url: baseUrl+"/esfuerzo/aprobado/" + idProyecto +"/"+ idActividad +"/"+ numSemana +"/"+ dni
                }).then(function(response) {
                    console.log(response.statusText);                 
                    $scope.getProyecto();
                },function(response){
                    console.log("aprobarEsfuerzo error: " + response.statusText);
                });
            };
            
            $scope.getEsfuerzosSem = function(idProyecto){
                $http({
                    method: "GET",
                    url: baseUrl + "/proyecto/"+idProyecto+"/esfuerzos"
                }).then(
                    function(response){  
                    $scope.esfuerzosProyecto = response.data;            
                }, function (response) {
                    $scope.esfuerzosProyecto = 'Not Found';
                    console.log("getEsfuerzosSem error: " + response.statusText);
                });
            };
            
            $scope.cerrarProyecto = function(idProyecto){
                $http({
                    method: "PUT",
                    url: baseUrl+"/proyecto/"+idProyecto+"/cerrar"
                }).then(function(response) {
                    //$scope.displayMode = "login"; //cambiar a informe de proyecto
                    $scope.informeCierreProyecto(idProyecto);
                    for(var i=0; i<$scope.actividadesProyecto.length; i++){
                        
                        if($scope.actividadesProyecto[i].duracionReal === undefined){
                            
                            $scope.cerrarActividad($scope.actividadesProyecto[i]);
                        }
                    }
              
                    console.log(response.statusText);
                },function(response){
                    console.log("setFechaFinProyecto error: " + response.statusText);
                });
            };
            
            $scope.getParticipantesActividades = function(){
                
                $http({
                    method: "GET",
                    url: baseUrl + "/proyecto/actividad/partic/"+$scope.proyecto.idProyecto
                }).then(
                    function(response){  
                        
                    $scope.participantesActividades = response.data;            
                }, function (response) {
                    $scope.participantesActividades = 'Not Found';
                    console.log("getParticipantesActividades error: " + response.statusText);
                });
            };
            
            $scope.setActividadAsignada = function(idActividad,dni){
                
                $http.post(baseUrl+"/actividad/recurso/"+$scope.proyecto.idProyecto+"/"+idActividad+"/"+dni).then(function(response){
                    $scope.getParticipantesActividades();
                    $scope.getActividadesProyecto($scope.proyecto.idProyecto);
                    $scope.actividadasignada = "Exito";
                }, function(response){
                    $scope.actividadasignada = "Error";
                    console.log("setActividadAsignada error: " + response.statusText);
                });
            };
            
            $scope.capacitados = function(actividad){
              var capacitados = [];
              
              for(var i=0;i<$scope.particProyecto.length;i++){
                  if($scope.particProyecto[i].rol === actividad.rolRequerido ){
                      var partiActuales = $scope.getParticipantes(actividad.actividadPK.idActividad);
                      
                      if(!partiActuales.includes($scope.particProyecto[i].participacionPK.dni))
                        capacitados.push($scope.particProyecto[i].participacionPK.dni);
                        
                  }
              }
              
              return capacitados;
            };
            
            $scope.getParticipantes = function(idActividad){
                
                var dniAsignados = [];
                
                for(var i = 0;i<$scope.participantesActividades.length;i++){
                    if($scope.participantesActividades[i].personaActividadPK.idActividad === idActividad)
                        dniAsignados.push($scope.participantesActividades[i].personaActividadPK.dni);
                   
                }
                
                return dniAsignados;
            };
            
            
            $scope.setActividadesRetrasadas = function(){
                $scope.actividadesRetrasadas = [];
                for(var i = 0; i<$scope.actividadesProyecto.length; i++)
                    if($scope.actividadesProyecto[i].duracionReal !== undefined)
                        if($scope.actividadesProyecto[i].duracionEstimada < $scope.actividadesProyecto[i].duracionReal)
                            $scope.actividadesRetrasadas.push($scope.actividadesProyecto[i]);
                
                    
            };
            
            $scope.getRoles = function(nivel){
                
                if(nivel<3)
                    return ["Analista","Diseñador","Analista-programador","Responsable pruebas","Programador","Probador"];
                else if(nivel<4)
                    return ["Diseñador","Analista-programador","Responsable pruebas","Programador","Probador"];
                else if(nivel<5)
                    return ["Programador","Probador"];
            };
            
            // ============================ Funciones devPage ============================
            
            $scope.getActividadesProyectosActivDev = function(dni) {
                $http({
                    method: "GET",
                    url: baseUrl +"/developer/activProyectosActivos/"+dni
                }).then(
                    function(response){
                        $scope.activProyectosActivos = response.data;
                        $scope.actividadesActiv = [];
                        
                        for(var i=0; i<$scope.activProyectosActivos.length;i++){
                            
                            if($scope.activProyectosActivos[i].duracionReal === undefined){
                               
                                $scope.actividadesActiv.push($scope.activProyectosActivos[i]);
                            }                                
                        }                                                            
                }, function (response) {
                    
                    console.log("getActicidadesActivasDev error: " + response.statusText);
                });
            };
            
            //comprobar que es el año correcto
            $scope.addEsfuerzoSemanal = function(actividad, fecha, comentario, horas) {
                
                if(new Date(fecha).getTime() <= new Date().getTime() &&
                        new Date(actividad.proyecto.fechaInicio).getTime() <= new Date(fecha).getTime() ){
                    
                    var esfuerzoJson = {
                        "idActividad": actividad.actividadPK.idActividad,
                        "idProyecto": actividad.actividadPK.idProyecto,
                        "dni": $scope.persona.dni,
                        "numSemana": $scope.calcularNumSemana(fecha),
                        "comentario": comentario,
                        "horas": parseInt(horas)
                    };

                    $http.post(baseUrl+"/developer/esfuerzoSemanal", esfuerzoJson).then(function(response){
                       $scope.getEsfuerzosProyectosActivos();
                       $scope.annadirEsfuerzo = 'Exito';
                    }, function(response){
                        $scope.annadirEsfuerzo = 'Error crear';
                        console.log("addEsfuerzoSemanal error: " + response.statusText);
                    });
                }else{
                    console.log("bye");
                    $scope.annadirEsfuerzo = 'Error fecha';
                }
               
            };
            
            $scope.getEsfuerzosProyectosActivos = function (fechaInferior, fechaSuperior){
                if(new Date(fechaInferior).getTime() < new Date().getTime() && 
                        new Date(fechaInferior).getTime() < new Date(fechaSuperior).getTime()){
                    $http({
                        method: "GET",
                        url: baseUrl +"/developer/esfuerzoProyectosActivos/"+$scope.persona.dni+"/"+$scope.calcularNumSemana(fechaInferior)+"/"+$scope.calcularNumSemana(fechaSuperior)+"/"+$scope.processDate(fechaInferior)
                    }).then(
                        function(response){
                            $scope.esfuerzoProyectosActivos = null;
                            $scope.esfuerzoProyectosActivos = response.data; 
                            
                            $scope.getEsfu = 'Exito';

                    }, function (response) {
                        $scope.getEsfu = 'Error get';
                        console.log("getEsfuerzosProyectosActivos error: " + response.statusText);
                    });
                }else{

                    $scope.getEsfu = 'Error fecha';
                }
            };
            
            $scope.setModifEsfuerzo = function(esfuerzo, cambio, modificacion) {
                
                
                var jsonObject = {
                    "esfuerzo":esfuerzo,
                    "cambio":cambio,
                    "modificacion":modificacion
                };
                $http.put(baseUrl+"/developer/esfuerzosemanal/modif", jsonObject).then(function() {
                    for(var i=0; i<$scope.esfuerzoProyectosActivos.length;i++){
                        
                        if(JSON.stringify($scope.esfuerzoProyectosActivos[i]) === JSON.stringify(esfuerzo)){
                            if(modificacion === 'nuevoTiem')
                                $scope.esfuerzoProyectosActivos[i].horasInvertidas = cambio;
                            else if(modificacion === 'nuevoComentario')
                                $scope.esfuerzoProyectosActivos[i].comentario = cambio;
                            
                        }
                    }
                    
                },function(response){
                    console.log("setModifEsfuerzo error: " + response.statusText);
                });
            };
            
            
            //============================ Funciones informes ============================
            
           $scope.informesPendientesActualizar = function(){
               $scope.inforPendientes = [];
               for(var i=0; i < $scope.esfuerzosProyecto.length; i++){
                   console.log($scope.esfuerzosProyecto[i].aprobado);
                   if(!$scope.esfuerzosProyecto[i].aprobado){
                       $scope.inforPendientes.push($scope.esfuerzosProyecto[i]);
                   }
               }
               console.log($scope.inforPendientes);
           };
           
           $scope.informeCierreProyecto = function(){
                $scope.displayMode = "informeCierre";
            };
            
            $scope.informeGlobalProyectos = function(){
              $http({
                    method: "GET",
                    url: baseUrl + "/informeGlobalProyectos"
                }).then(
                    function(response){  
                    $scope.informeGlobal = response.data;   
                    
                    $scope.displayMode = "informeGlobal";
                }, function (response) {
                    $scope.informeGlobal = 'Not Found';
                    console.log("informeGlobal error: " + response.statusText);
                });  
            };
            
            $scope.informePendientes = function(){
                $scope.informesPendientesActualizar();
                $scope.displayMode = "informePendientes";
            };
            
            $scope.informeRetrasos = function(){
                $scope.displayMode = "informeRetrasos";
            };
                     
            //--------------------Cerrado de sesión--------------------
            //Se reinician los atributos 
            $scope.cerrarSesion = function () {
                $scope.persona = null;
                $scope.devsProyecto = null;
                $scope.jefesArray = null;
                $scope.inicioSesion = null;
                $scope.displayMode = "login";
                $scope.creaUsuario = null;
                $scope.particProyecto = null;
                $scope.planProyecto = null;
                $scope.esfuerzoProyectosActivos = null;
                $scope.infPendientes = null;
                $scope.actividadesRetrasadas = null;
            };
            
            
            //------------------ Funcion que calcula la semana a partir de la fecha ----------------------------
            $scope.calcularNumSemana = function (fecha) {
                var numSemana = $scope.processDate(fecha);
                return new Date(numSemana).getWeek();
            };
            
            Date.prototype.getWeek = function () {
                var onejan = new Date(this.getFullYear(), 0, 1);
                return Math.ceil((((this - onejan) / 86400000) + onejan.getDay() + 1) / 7);
            };
            //-------------------- Menus desplegables --------------------
            $scope.ngHide1=true;
            $scope.showProyectDevelopersDisp = function(flag){
                if(flag)
                    $scope.ngHide1=false;
                else
                    $scope.ngHide1=true;
            };
            
            $scope.ngHide2=true;
            $scope.showActividades = function(flag){
                
                if(flag)
                    $scope.ngHide2=false;
                else
                    $scope.ngHide2=true;
            };
            
            $scope.ngHide3=true;
            $scope.showInformesAct = function(flag){
                
                if(flag)
                    $scope.ngHide3=false;
                else
                    $scope.ngHide3=true;
            };
            
            $scope.ngHide4=true;
            $scope.showDarAltaNuevoUsuario = function(flag){  
                if(flag)
                    $scope.ngHide4=false;
                else
                    $scope.ngHide4=true;
            };
            
            $scope.ngHide5=true;
            $scope.showDarAltaNuevoProyecto = function(flag){   
                if(flag){
                    $scope.ngHide5=false;
                    
                    
                }else
                    $scope.ngHide5=true;
            };
            
            $scope.ngHide6=true;
            $scope.showCrearEsfuerzoSemanal = function(flag){
                if(flag){
                    $scope.ngHide6=false;
                }else
                    $scope.ngHide6=true;
            };
            
            $scope.ngHide7=true;
            $scope.showEsfuerzoSemanalCreados = function(flag){
                if(flag){
                    $scope.ngHide7=false;
                }else
                    $scope.ngHide7=true;
            };
            
            $scope.ngHide8=true;
            $scope.showProyectDevelopers = function(flag){
                if(flag){
                    $scope.ngHide8=false;
                }else
                    $scope.ngHide8=true;
            };
            
            $scope.atrasInf = function(){
                if($scope.persona.nivelUsuario === 0){
                    $scope.displayMode = 'admin';
                }else if($scope.persona.nivelUsuario === 1 && $scope.persona.tipoUsuario === "Jefe Proyecto"){
                    $scope.displayMode = 'boss';
                }else if($scope.persona.nivelUsuario > 1 && $scope.persona.tipoUsuario === "Desarrollador"){
                    $scope.displayMode = 'dev';
                }

            };
            
            //-------------------- Lectura Fichero --------------------
            $scope.SelectFile = function (file) {
                $scope.SelectedFile = file;
                $scope.hideConfirmarPlanProyecto = true;
            };
            $scope.Upload = function () {
                $scope.hideConfirmarPlanProyecto = false;
                var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.csv|.txt)$/;
                if (regex.test($scope.SelectedFile.name.toLowerCase())) {
                    if (typeof(FileReader) !== undefined) {
                        var reader = new FileReader();
                        
                        reader.onload = function (e) {
                            var planProyecto = new Array();
                            var rows = e.target.result.split("\n");
                            
                            for (var i = 0; i < rows.length; i++) {
                                
                                var cells = rows[i].split(";");
                                if (cells.length > 1) {
                                    var actividad = {};
                                    actividad.ActivId = cells[0];
                                    actividad.ActivDescripcion = cells[1];
                                    var predecesoras = cells[2].split(",");
                                    actividad.ActivPredecesora = predecesoras;
                                    actividad.HorasHombre = cells[3];
                                    actividad.Rol = cells[4];
                                    planProyecto.push(actividad);
                                    $scope.$apply(function () {
                                        $scope.planProyecto = planProyecto;
                                        $scope.IsVisible = true;
                                    });
                                }
                            }

                        };
                        reader.readAsText($scope.SelectedFile);
                    } else {
                        $window.alert("El navegador no soporta HTML5.");
                    }
                } else {
                    $window.alert("Por favor carga un archivo valido.");
                }
            };
        
            
            
        });
//para seleccionar el numero de horas
angularApp.filter('range', function() {
  return function(input, min, max) {
    min = parseInt(min); //Make string input int
    max = parseInt(max);
    for (var i=min; i<max; i++)
      input.push(i);
    return input;
  };
});