/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sodep.notificationserver.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hibernate.criterion.Projections.id;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import py.com.sodep.notificationserver.db.entities.*;
import py.com.sodep.notificationserver.business.AplicacionBusiness;

/**
 *
 * @author Vanessa
 */
@Path("/aplicacion")
@RequestScoped
public class AplicacionService {

    AplicacionBusiness appBussines = new AplicacionBusiness();

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newApplication(Aplicacion a) throws Exception {
        System.out.println("Application/id " + a);
        appBussines.createAplicacionJson(a, "POST");
        return Response.ok(a).build();

    }

    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@MultipartForm AplicacionFile form) throws Exception {
        System.out.println("Recibido: " + form);
        Aplicacion a = appBussines.newAplicacionFileUpload(form);
        return Response.ok(a).build();
    }

    @PUT
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateApplication(Aplicacion b) throws Exception {
        System.out.println("Application/id " + b);
        Aplicacion a = appBussines.createAplicacionJson(b, "PUT");
        return Response.ok(a).build();

    }

    @PUT
    @Path("/upload")
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFileUpdate(@MultipartForm AplicacionFile form) throws Exception {
        Aplicacion a = appBussines.newAplicacionFileUpload(form);
        return Response.ok(a).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAplicacion(@MultipartForm AplicacionFile form) throws Exception {
        Aplicacion a = appBussines.newAplicacionFileUpload(form);
        return Response.ok(a).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplicationById(@PathParam("id") String id) throws Exception {
        System.out.println("Application/id " + id);
        Aplicacion a = appBussines.getApplication(id);
        return Response.ok(a).build();

    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAplicacion(@QueryParam(value = "nombre") String nombre) throws Exception {
        System.out.println("Application/nombre " + nombre);
        Aplicacion a = appBussines.findAplicacion(nombre);
        return Response.ok(a).build();

    }
}