package org.sil.cmb.employee_svc;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/employee")
public class EmployeeResource {

    ArrayList<Employee> employees = new ArrayList<Employee>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String handleGet() {
        return "getall";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String handleGetbyId(@PathParam("id") String id) {
        return "get by id for " + id;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String handlePut(@PathParam("id") String id) {
        return "put to " + id;
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String handlePost(@PathParam("id") String id) {
        return "post for " + id;
    }


    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String handleDelete(@PathParam("id") String id) {
        return "delete for " + id;

    }

}
