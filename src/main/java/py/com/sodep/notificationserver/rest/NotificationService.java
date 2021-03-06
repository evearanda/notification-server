package py.com.sodep.notificationserver.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import py.com.sodep.notificationserver.business.NotificationBusiness;
import py.com.sodep.notificationserver.db.entities.Evento;
import py.com.sodep.notificationserver.exceptions.handlers.BusinessException;
import py.com.sodep.notificationserver.rest.entities.EventoRequest;
import py.com.sodep.notificationserver.rest.entities.EventoResponse;

@Path("/evento")
@RequestScoped
public class NotificationService {

    @Inject
    NotificationBusiness business;

    final static Logger logger = Logger.getLogger(NotificationService.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newNotification(EventoRequest evento) throws 
            BusinessException {
        logger.info("Evento " + evento.getApplicationName());
        Evento e = new Evento(evento);
        EventoResponse ex = business.crearEvento(e, evento.getApplicationName());
        return Response.ok().entity(ex).build();
    }
}
