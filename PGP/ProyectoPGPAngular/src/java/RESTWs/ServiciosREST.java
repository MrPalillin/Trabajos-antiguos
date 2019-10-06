package RESTWs;

import dominio.Actividad;
import dominio.ActividadPK;
import dominio.Esfuerzosemanal;
import dominio.EsfuerzosemanalPK;
import dominio.Participacion;
import dominio.ParticipacionPK;
import dominio.Persona;
import dominio.Proyecto;
import dominio.PersonaActividad;
import dominio.PersonaActividadPK;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import persistencia.ActividadFacadeLocal;
import persistencia.EsfuerzosemanalFacadeLocal;
import persistencia.ParticipacionFacadeLocal;
import persistencia.PersonaFacadeLocal;
import persistencia.ProyectoFacadeLocal;
import persistencia.PersonaActividadFacadeLocal;

/**
 * REST Web Service
 *
 * @author Mario Torbado
 * @author Jorge Aguilera
 */
@javax.ws.rs.ApplicationPath("webresources")
@Path("/paraAngular")
public class ServiciosREST {
    
    @EJB
    private PersonaActividadFacadeLocal personaActividadFacade;
    @EJB
    private ActividadFacadeLocal actividadFacade;
    @EJB
    private EsfuerzosemanalFacadeLocal esfuerzoFacade;
    @EJB
    private ParticipacionFacadeLocal participacionFacade;
    @EJB
    private PersonaFacadeLocal personaFacade;
    @EJB
    private ProyectoFacadeLocal proyectoFacade;

    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of ServiciosREST
     */
   public ServiciosREST() {
    }
    
    @GET
    @Path("/usuario/{dni}/{psswd}")
    @Produces("application/json")
    public Response comprobarUsuario(@PathParam("dni")String login, @PathParam("psswd")String psswd){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
        JsonObject persona;
        Persona p;
        
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        /*Proyecto pro = proyectoFacade.find(1);
        pro.setFechaFin(null);
        proyectoFacade.edit(pro);*/
        try{
            if(login.equals("") || psswd.equals("")){
                return respuesta.status(Response.Status.NO_CONTENT).build();
            }else{
                p = personaFacade.getUsuario(login, psswd);
                persona = Json.createObjectBuilder()
                        .add("nombre", p.getNombre())
                        .add("apellidos", p.getApellidos())
                        .add("dni", p.getDni())
                        .add("psswd", p.getPsswd())
                        .add("nivelUsuario", p.getNivel())
                        .add("tipoUsuario", p.getTipoUsuario())
                        .build();
                
                
                respuesta.entity(persona);
                return respuesta.build();
                
            }
            
        }catch(JsonException e){
            return respuesta.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/nuevousuario/")
    public Response creaUsuario(JsonObject nuevoUsuario) {
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
         
        try{
            Persona p =new Persona();
            p.setDni(nuevoUsuario.getString("dni"));
            p.setNombre(nuevoUsuario.getString("nombre"));
            p.setApellidos(nuevoUsuario.getString("apll"));
            p.setPsswd(nuevoUsuario.getString("psswd"));
            p.setTipoUsuario(nuevoUsuario.getString("tipoUsr"));
            p.setNivel(Integer.parseInt(nuevoUsuario.getString("nivel")));
            personaFacade.create(p);
            
            return respuesta.build();
        }catch(JsonException e){
            return respuesta.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GET
    @Path("/jefesproyectodisp")
    @Produces("application/json")
    public Response getJefesProyectoDisponibles(){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        List<Persona> jefesPro = personaFacade.getUsuariosPorNivel(1);
        List<Persona> jefesDisp = new ArrayList<>();
        List<Proyecto> proyectosDev = proyectoFacade.findAll();
        
        for(Persona jefe: jefesPro){
            boolean disponible = true;
            List<Proyecto> proyectos = jefe.getProyectoList();
            
            if (!proyectos.isEmpty()){ //No esta siendo jefe de proyecto de ningun proyecto activo
                for(Proyecto p: proyectos){
                    if(p.getFechaFin() == null ){ //tiene un proyecto en curso, luego no está disponible
                        disponible = false;
                        break;
                    }                   
                }
            }
            if(disponible){
                //comprueba que no este trabajando como 
                List<Participacion> parti = jefe.getParticipacionList();
                
                for(Participacion pa: parti){
                    
                    if(pa.getProyecto().getFechaFin() == null){
                        disponible=false;
                        break;
                    }
                }
                
                if(disponible)
                    jefesDisp.add(jefe);
            }
        }
        if(jefesDisp.isEmpty()) return respuesta.status(Response.Status.NO_CONTENT).build();
        
        Persona []disponibles = new Persona[jefesDisp.size()]; //ArrayList a Array, necesario para entity
        for(int i=0; i<jefesDisp.size();i++)
            disponibles[i]=jefesDisp.get(i);
       
        respuesta.entity(disponibles);
        
        return respuesta.build();
    }
    
    @GET
    @Path("/devsdisponibles/{idProyecto}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces("application/json")
    public Response getDevsDisponibles(@PathParam("idProyecto") String idProyecto){ 
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        
        List<Persona> devs = personaFacade.findAll();
        if(devs.isEmpty()) return respuesta.status(Response.Status.NO_CONTENT).build();
        List<Participacion> participaciones = participacionFacade.findAll();
        if(participaciones.isEmpty()) return respuesta.status(Response.Status.NO_CONTENT).build();
        
        List<Persona> devsDisp = new ArrayList<>();
        for(Persona pe : devs){
            if (pe.getNivel() > 0){
                int num = 0;
                
                List<Proyecto> proyectoActivos = proyectoFacade.getProyectosActivos();
                
                for(Participacion pa : participaciones){
                    if(pa.getPersona().getDni().equals(pe.getDni()) && proyectoActivos.contains(pa.getProyecto())) num++;
                }
                
                if(num<=2){
                    ParticipacionPK pk = new ParticipacionPK(Integer.parseInt(idProyecto), pe.getDni());
                    if(participacionFacade.find(pk)==null)
                        if(pe.getNivel()>1)
                            devsDisp.add(pe);
                        else{    
                            //controla que no este trabajando en ningun proyecto
                            boolean activo = false;
                            for(Proyecto pro: proyectoActivos){
                              //si el jefe de proyecto trabaja como jefe de proyecto
                              if(pro.getDniJefe().equals(pe))
                                activo=true;
                            }
                            if(!activo)
                                devsDisp.add(pe);
                        }   
                        
                }
            }
        }
        //if(devsDisp.isEmpty()) return respuesta.status(Response.Status.NO_CONTENT).build();
        
        Persona []disponibles = new Persona[devsDisp.size()]; //ArrayList a Array, necesario para entity
        for(int i=0; i<devsDisp.size();i++){
            disponibles[i]=devsDisp.get(i);
            //System.out.println(disponibles[i].getDni());
        }
        
        respuesta.entity(disponibles);
        return respuesta.build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/nuevoproyecto/")
    public Response addProyecto(JsonObject nuevoProyecto) throws ParseException {
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        try{
            Proyecto proyecto = new Proyecto();
            proyecto.setDniJefe(personaFacade.find(nuevoProyecto.getString("jefeProyecto")));
            
            Date date=new SimpleDateFormat("yyyy-MM-dd").parse(nuevoProyecto.getString("fecha_inicio")); 
            
            proyecto.setFechaInicio(date);
            proyecto.setDesripcion(nuevoProyecto.getString("descripcion"));
            
            proyectoFacade.create(proyecto);
            
            
            return respuesta.build();
        }catch(JsonException e){
            return respuesta.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GET
    @Path("/jefe/{dniJefe}/proyecto")
    @Produces("application/json")
    public Response getProyectoActual(@PathParam("dniJefe") String dniJefe){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        
        List<Proyecto> proyectosActivos = proyectoFacade.getProyectosActivos();
        for(Proyecto p : proyectosActivos){
            
            if(p.getDniJefe().getDni().equals(dniJefe)){ 
                
                respuesta.entity(p);
                return respuesta.build();
            }
        }
        return respuesta.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/proyecto/{idProyecto}/devs")
    @Produces("application/json")
    public Response getDevsProyecto(@PathParam("idProyecto") String idProyecto){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        
        List<Participacion> partcp = participacionFacade.findAll();
        if(partcp.isEmpty()) return respuesta.status(Response.Status.NO_CONTENT).build();
        
        List<Persona> devs = new ArrayList<>();
        for(Participacion p : partcp){
            
            if(p.getProyecto().getIdProyecto() == Integer.parseInt(idProyecto)){ 
                devs.add(p.getPersona());
            }
        }
        
        Persona []devProyecto = new Persona[devs.size()];
        for(int i=0;i<devs.size();i++)
            devProyecto[i]=devs.get(i);
        
        
        respuesta.entity(devProyecto);
        return respuesta.build();
    }
    
    @PUT
    @Path("/proyecto/{idProyecto}/cerrar")
    public Response cerrarProyecto(@PathParam("idProyecto") String idProyecto){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        Proyecto p = proyectoFacade.find(Integer.parseInt(idProyecto));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
        p.setFechaFin(date);
        
        Persona pers = p.getDniJefe();
        pers.setTipoUsuario(idProyecto);
        pers.setTipoUsuario("Desarrollador");
        personaFacade.edit(pers);
        
        proyectoFacade.edit(p);
        return respuesta.build();
    }

    @POST
    @Path("/proyecto/{idProyecto}/pacticdev/{dniDev}/{porcentaje}/{rol}")
    public Response setParticipacionDev(@PathParam("idProyecto") int idProyecto, 
                                        @PathParam("dniDev") String dniDev,
                                        @PathParam("porcentaje") String porcentaje,
                                        @PathParam("rol") String rol){

        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
            
            
            List<Participacion> participaciones = participacionFacade.findAll();
            int cont=0;
            boolean completo=false;
            for(Participacion p : participaciones){
                if(p.getParticipacionPK().getDni().equals(dniDev)){
                    cont++;
                    if(p.getPorcentaje()>50){
                        completo=true;
                    }
                }
            }
            if(cont<2 || !completo){ //trabaja en menos de 2 proyectos, ninguno con participacion >50%
                ParticipacionPK pk = new ParticipacionPK(idProyecto, dniDev); 
                Participacion p = new Participacion(pk);
                p.setPorcentaje(Double.parseDouble(porcentaje));
                p.setRol(rol);
                p.setPersona(personaFacade.find(dniDev));
                p.setProyecto(proyectoFacade.find(idProyecto));
                participacionFacade.create(p);
            }else
                return respuesta.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            
            return respuesta.build();
    }
    
    @POST
    @Path("/actividad/recurso/{idProyecto}/{idActividad}/{dni}")
    @Consumes()
    public Response setAsignarActividad(@PathParam("idProyecto") int idProyecto, 
            @PathParam("idActividad") int idActividad, @PathParam("dni") String dni){
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
        
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        try{
            ActividadPK actPk = new ActividadPK(idActividad, idProyecto);
            PersonaActividad pa= new PersonaActividad(dni, idActividad, idProyecto);
            pa.setActividad(actividadFacade.find(actPk));
            pa.setPersona(personaFacade.find(dni));
            personaActividadFacade.create(pa);
            
            return respuesta.status(Response.Status.ACCEPTED).build();
        }catch(InternalError e){
            return respuesta.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        
    }
    
    @GET
    @Path("/proyecto/{idProyecto}/participaciones")
    @Produces("application/json")
    public Response getParticipacionesProyecto(@PathParam("idProyecto") String idProyecto){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
       
        List<Participacion> particProyecto = participacionFacade.findParticipacionesByIdProyecto(Integer.parseInt(idProyecto));
        if(particProyecto.isEmpty()) return respuesta.status(Response.Status.NO_CONTENT).build();
        
        Participacion []prt = new Participacion[particProyecto.size()];
        for(int i=0;i<particProyecto.size();i++)
            prt[i]=particProyecto.get(i);
        
        respuesta.entity(prt);
        return respuesta.build();
    }
    
    @GET
    @Path("/proyecto/actividad/partic/{idProyecto}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getPersonaActividadProyect(@PathParam("idProyecto") int idProyecto){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        List<PersonaActividad> persactividad = personaActividadFacade.findAll();
        List<PersonaActividad> paEnProyecto=new ArrayList<>();
        for(PersonaActividad pa: persactividad){
            if(pa.getActividad().getProyecto().getIdProyecto()==idProyecto)
                paEnProyecto.add(pa);
        }
        
        PersonaActividad []persactProyecto = new PersonaActividad[paEnProyecto.size()];
        for(int i=0;i<paEnProyecto.size();i++)
            persactProyecto[i]=paEnProyecto.get(i);
        
        respuesta.entity(persactProyecto);
        return respuesta.build();
    }
    
    @GET
    @Path("/proyecto/{idProyecto}/esfuerzos")
    @Produces("application/json")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getEsfuerzosSem(@PathParam("idProyecto") String idProyecto){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        List<Esfuerzosemanal> esfuerzos = esfuerzoFacade.findByIdProyecto(Integer.parseInt(idProyecto));
        if(esfuerzos.isEmpty()) return respuesta.status(Response.Status.NO_CONTENT).build();
        
        Esfuerzosemanal []esf = new Esfuerzosemanal[esfuerzos.size()];
        for(int i=0;i<esfuerzos.size();i++)
            esf[i]=esfuerzos.get(i);
        
        respuesta.entity(esf);
        return respuesta.build();
    }
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces("application/json")
    @Path("/proyecto/{idProyecto}/actividades")
    public Response getActividadesProyecto(@PathParam("idProyecto") int idProyecto){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        List<Actividad> actividades = actividadFacade.findAll();
        List<Actividad> actProyecto = new ArrayList<>();
        for(Actividad a : actividades){
            if(a.getProyecto().getIdProyecto() == idProyecto)
                actProyecto.add(a);
        }
        
        Actividad []activ = new Actividad[actProyecto.size()];
        for(int i=0;i<actProyecto.size();i++)
            activ[i]=actProyecto.get(i);
        
        respuesta.entity(activ);
        return respuesta.build();
    }
    
    @PUT
    @Path("/actividad/fin/{idProyecto}/{idActividad}")
    public Response cerrarActividad(@PathParam("idProyecto") int idProyecto,
                                    @PathParam("idActividad") int idActividad){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
        
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        ActividadPK actK= new ActividadPK(idActividad, idProyecto);
        Actividad a = actividadFacade.find(actK);
        List<Esfuerzosemanal> esfuerzos = esfuerzoFacade.findByIdActividadIdProyecto(idActividad, idProyecto);
        int duracionReal = 0;
        
        if (esfuerzos.isEmpty()){
            System.out.println("Holaaaa");
            a.setDuracionReal(duracionReal);        
            actividadFacade.edit(a);
            return respuesta.status(Response.Status.NO_CONTENT).build();
        }
        
        for(Esfuerzosemanal esf : esfuerzos)
            if(esf.getHorasInvertidas() != null)
                duracionReal += (int)esf.getHorasInvertidas();
        
        a.setDuracionReal(duracionReal);
        
        actividadFacade.edit(a);
        return respuesta.build();
    }
    
    //Hay que pasar la fecha como numero de la semana usando la funcion que hay en el .js
    @PUT
    @Path("/esfuerzo/aprobado/{idProyecto}/{idActividad}/{numSemana}/{dni}")
    public Response aprobarEsfuerzo(@PathParam("idProyecto") int idProyecto,
                                    @PathParam("idActividad") int idActividad,
                                    @PathParam("numSemana") int numSemana,
                                    @PathParam("dni") String dni){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        EsfuerzosemanalPK esfK = new EsfuerzosemanalPK(numSemana, dni, idActividad, idProyecto);

        Esfuerzosemanal e = esfuerzoFacade.find(esfK);
        if (e==null) return respuesta.status(Response.Status.INTERNAL_SERVER_ERROR).build();
       
        e.setAprobado(true); //error
        esfuerzoFacade.edit(e);
        return respuesta.build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/proyecto/planProyecto")
    public Response addPlanProyecto(JsonObject planProyectoConIdProyecto){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        //{"ActivId":"1","ActivDescripcion":"\"texto1\"","ActivPredecesora":[" "],"HorasHombre":"10","PersonaAsignada":"persona 1"}
        // duracion real, rol los tiene q meter e jefe de proyectos
        try{
            JsonArray planProyecto = planProyectoConIdProyecto.getJsonArray("planProyecto");

            int idProyecto = planProyectoConIdProyecto.getInt("idProyecto");
            
            //List<Participacion> participPrevias = participacionFacade.findAll();
            
            for(int i=0;i<planProyecto.size();i++){

                JsonObject actividadJson = planProyecto.getJsonObject(i);
                
                /*if(!actividadJson.getString("PersonaAsignada").equals(" ")){
                    for(Participacion pa : participPrevias)
                        if(pa.getPersona().getDni().equals(actividadJson.getString("PersonaAsignada")) && pa.getParticipacionPK().getIdProyecto() != idProyecto)
                            numParticipaciones++;
                }*/
                //System.out.println("Num participaciones"+numParticipaciones+"  "+actividadJson.getString("PersonaAsignada")+" "+idProyecto);
                
                Actividad activ = new Actividad();

                activ.setActividadPK(new ActividadPK(Integer.parseInt(actividadJson.getString("ActivId")), idProyecto));
                activ.setDescripcion(actividadJson.getString("ActivDescripcion"));
                activ.setDuracionEstimada(Integer.parseInt(actividadJson.getString("HorasHombre")));
                activ.setProyecto(proyectoFacade.find(idProyecto));
                System.out.println("primera aprte");
                JsonArray predecesoras = actividadJson.getJsonArray("ActivPredecesora");
                //predecesoras de cada actividad (recorre la lista, act separadas por espacios
                List<Actividad> predList = new ArrayList<>();
                for(int j= 0;j<predecesoras.size();j++){
                    if(!predecesoras.getString(j).equals(" "))                 
                        predList.add(actividadFacade.find(new ActividadPK(Integer.parseInt(predecesoras.getString(j)), idProyecto)));
                }
                //comprueba si hay actividades predecesoras
                if(predList.size()>0)    
                    activ.setActividadList(predList);
                System.out.println("Segunda parte");
                //Se crea la participacion si no existia y si no es un hito
                if(!actividadJson.getString("Rol").equals(" ")){

                    System.out.println("Tercera parte");
                    activ.setRolRequerido(actividadJson.getString("Rol"));
                    //System.out.println("parte final"+activ.getActividadPK().getIdProyecto()+" "+activ.getDuracionEstimada()+" "+activ.getDescripcion());
                    /*ParticipacionPK pKey = new ParticipacionPK(idProyecto, actividadJson.getString("PersonaAsignada"));
                    if(participacionFacade.find(pKey) == null){
                        Participacion p = new Participacion(pKey);
                        //System.out.println("DNI"+pKey.getDni());
                        p.setPorcentaje(0.0);
                        participacionFacade.create(p);
                    }*/
                    //System.out.println("Cuarta parte");
                }
                System.out.println("parte final "+activ.getActividadPK().getIdProyecto()+" "+activ.getActividadPK().getIdActividad()+" "+activ.getDescripcion());
                actividadFacade.create(activ); 
            }
            return respuesta.build();
        }catch(JsonException e){
            return respuesta.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }  
    }
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces("application/json")
    @Path("/developer/activProyectosActivos/{dni}")
    public Response getActivAsignadasProyectosActivosDev(@PathParam("dni") String dni){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        Persona p= personaFacade.find(dni);
        
        List<PersonaActividad> actividades = p.getPersonaActividadList();
        
        List<Actividad> activEnProyectsActivos = new ArrayList<>();
        for(PersonaActividad pa: actividades){
            if(pa.getActividad().getProyecto().getFechaFin() == null)
                activEnProyectsActivos.add(pa.getActividad());
        }
        Actividad []activ = new Actividad[activEnProyectsActivos.size()];
        
        for(int i=0;i<activEnProyectsActivos.size();i++)
            activ[i] = activEnProyectsActivos.get(i);
        
        respuesta.entity(activ);
        return respuesta.build();
    }
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces("application/json")
    @Path("/developer/esfuerzoProyectosActivos/{dni}/{semanaInf}/{semanaSup}/{fechaInf}")
    public Response getEsfuerzosAsignadasProyectosActivosDev(@PathParam("dni") String dni, 
                                                             @PathParam("semanaInf") int semanaInf,
                                                             @PathParam("semanaSup") int semanaSup,
                                                             @PathParam("fechaInf") String fecha)
                                                             throws ParseException{
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        //para comprobar q no es inferior al proyecto
        Date dIni = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
       
        Persona p= personaFacade.find(dni);
        
        List<PersonaActividad> actividades = p.getPersonaActividadList();
        
        List<Esfuerzosemanal> esfuerzoEnProyectsActivos = new ArrayList<>();
        for(PersonaActividad pa: actividades){
            if(pa.getActividad().getProyecto().getFechaFin() == null && pa.getActividad().getProyecto().getFechaInicio().before(dIni))
                esfuerzoEnProyectsActivos.addAll(pa.getEsfuerzosemanalList());
        }
        
        List<Esfuerzosemanal> esfuerzoEnProyectsActivosFecha = new ArrayList<>();
        //solo las fechas seleccionadas, el resto se descarta
        for(Esfuerzosemanal e: esfuerzoEnProyectsActivos){
            
            if(semanaInf <= semanaSup && e.getEsfuerzosemanalPK().getNumSemana()>=semanaInf && e.getEsfuerzosemanalPK().getNumSemana()<=semanaSup)
                esfuerzoEnProyectsActivosFecha.add(e);
            else if(semanaInf > semanaSup && e.getEsfuerzosemanalPK().getNumSemana()<= semanaInf && e.getEsfuerzosemanalPK().getNumSemana()<=semanaSup )
                esfuerzoEnProyectsActivosFecha.add(e);
        }
        
        Esfuerzosemanal []esfuerzos = new Esfuerzosemanal[esfuerzoEnProyectsActivosFecha.size()];
        
        for(int i=0;i<esfuerzoEnProyectsActivosFecha.size();i++)
            esfuerzos[i] = esfuerzoEnProyectsActivosFecha.get(i);
        
        respuesta.entity(esfuerzos);
        return respuesta.build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/developer/esfuerzoSemanal")
    public Response addEsfuerzoSemenal(JsonObject esfuerzo){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        try{
            //EsfuerzosemanalPK clave = new EsfuerzosemanalPK(esfuerzo.getInt("numSemena"), esfuerzo.getInt("idActividad"), esfuerzo.getInt("idProyecto"));
            //System.out.println(esfuerzo.getInt("numSemana"));
            if(esfuerzoFacade.comprobarHorasAndEsfuerzosPersonaSemana(esfuerzo.getString("dni"), esfuerzo.getInt("numSemana"), esfuerzo.getInt("horas"))){
                Esfuerzosemanal e = new Esfuerzosemanal(esfuerzo.getInt("numSemana"), esfuerzo.getString("dni"),esfuerzo.getInt("idActividad"), esfuerzo.getInt("idProyecto"));
                e.setComentario(esfuerzo.getString("comentario"));
                e.setHorasInvertidas(esfuerzo.getInt("horas"));
                esfuerzoFacade.create(e);
            }else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            
            return respuesta.build();
        }catch(JsonException e){
            return respuesta.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/developer/esfuerzosemanal/modif")
    public Response setModifEsfuerzoSemanal(JsonObject esfuerzo){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        try{
            JsonObject esfu = esfuerzo.getJsonObject("esfuerzo").getJsonObject("esfuerzosemanalPK");
            EsfuerzosemanalPK esPk = new EsfuerzosemanalPK(esfu.getInt("numSemana"), esfu.getString("dni"), esfu.getInt("idActividad"), esfu.getInt("idProyecto"));
            Esfuerzosemanal es = esfuerzoFacade.find(esPk);
            if(esfuerzo.getString("modificacion").equals("nuevoTiem")){
                if(esfuerzoFacade.comprobarHorasAndEsfuerzosPersonaSemana(esfu.getString("dni"), esfu.getInt("numSemana"), esfuerzo.getInt("cambio")))
                    es.setHorasInvertidas(esfuerzo.getInt("cambio"));
                else
                    return respuesta.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }else if(esfuerzo.getString("modificacion").equals("nuevoComentario"))
                es.setComentario(esfuerzo.getString("cambio"));
            
            esfuerzoFacade.edit(es);
            return Response.status(Response.Status.OK).build();
        }catch(JsonException e){
            return respuesta.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
  
    }
    
    
    @GET
    @Path("/informeGlobalProyectos")
    @Produces("application/json")
    public Response getInformeGlobal(){
        
        Response.ResponseBuilder respuesta = Response.status(Response.Status.OK);
         
        respuesta.header("Access-Control-Allow-Origin", "*");
        respuesta.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        respuesta.type("application/json");
        respuesta.allow("OPTIONS");
        
        List<Proyecto> proyectos = proyectoFacade.findAll();
        List<Proyecto> prCerrados = new ArrayList<>();
        
        for(Proyecto p : proyectos){
            if(p.getFechaFin()!=null){
                prCerrados.add(p);
            }
        }
        
        if(prCerrados.isEmpty()) return respuesta.status(Response.Status.NO_CONTENT).build();
        
        Proyecto[] pr = new Proyecto [prCerrados.size()];
        for(int i=0;i<prCerrados.size();i++)
            pr[i]=prCerrados.get(i);
        
        respuesta.entity(pr);
        return respuesta.build();
    }
    
    private int getNivelRol(String rol){
        switch(rol){
            case "Jefe de proyecto":
                return 1;
            case "Analista":
                return 2;
            case "Diseñador":
                return 3;
            case "Analista-programador":
                return 3;
            case "Responsable pruebas":
                return 3;
            case "Programador":
                return 4;
            case "Probador":
                return 4;
            default: 
                return -1;
        }
    }   
    
}
