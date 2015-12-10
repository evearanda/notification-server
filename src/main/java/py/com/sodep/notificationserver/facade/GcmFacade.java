package py.com.sodep.notificationserver.facade;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import py.com.sodep.notificationserver.db.dao.EventoDao;
import py.com.sodep.notificationserver.db.entities.notification.AndroidResponse;
import py.com.sodep.notificationserver.db.entities.notification.AndroidNotification;
import py.com.sodep.notificationserver.util.Parametro;

/**
 * Se comunica con el servidor GCM para enviar la notificación correspondiente
 *
 * @author Gabriela Gaona <gabriela.gaona@konecta.com.py>
 *
 */
/**
 * Se comunica con el servidor GCM para enviar la notificación correspondiente
 *
 * @author Gabriela Gaona <gabriela.gaona@konecta.com.py>
 *
 */
public class GcmFacade {

    @Inject
    ObjectMapper map;

    @Inject
    Logger log;

    @Inject
    EventoDao eventoDao;

    public AndroidResponse send(String apiKey, AndroidNotification notification) {
        log.info("API KEY: " + apiKey);
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Parametro.URL_GCM);
        Invocation.Builder builder = target.request().accept(MediaType.APPLICATION_JSON);
        builder.header(HttpHeaders.AUTHORIZATION, "key=" + apiKey);

        try {
            //map = new ObjectMapper();
            String jsonInString = map.writeValueAsString(notification);
            log.info("Json a enviar: " + jsonInString);
            Response response = builder.post(Entity.entity(jsonInString, MediaType.APPLICATION_JSON));
            if (response.getStatus() != 200) {
                log.info("Error en la respuesta : HTTP error code :" + response.getStatus());
                log.info(response.getStringHeaders().toString());
                throw new RuntimeException("Error en la respuesta : HTTP error code : "
                        + response.getStatus());
            }
            AndroidResponse r = response.readEntity(AndroidResponse.class);
            log.info(r);
            return r;
        } catch (JsonProcessingException ex) {
            log.error(ex);
        } finally {
        }
        return null;
    }
}
