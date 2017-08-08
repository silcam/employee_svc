package org.sil.cmb.employee_svc;

import com.google.gson.Gson;
import org.sil.cmb.employee_svc.gson.GSONFactory;
import org.sil.cmb.employee_svc.model.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;
import java.util.Random;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/employee")
public class EmployeeResource {

    // retrieve all objects
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleGet() {
        Response response = Response.ok().entity(String.valueOf(EmployeeContainer.employees.size())).build();
        return response;
    }

    // retrieve a specific object
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleGetbyId(@PathParam("id") String id) {
        Gson gson = GSONFactory.getInstance();
        Employee employee = handleGetById(id);

        if (employee == null) {
            Response response = Response.status(404).build();
            return response;
        } else {
            String jsonOutput = gson.toJson(employee);
            Response response = Response.ok().entity(jsonOutput).build();
            return response;
        }
    }

    // Create an object if you know the expected id.
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handlePut(@PathParam("id") String id, String body) {
        Gson gson = GSONFactory.getInstance();

        Employee employee = handleCreate(id, body);

        String serializedEmployee = gson.toJson(employee);

        Response response = Response.status(201).entity(serializedEmployee).build();
        return response;
    }

    // Create an object without a known id
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handlePostCreate(String body) {
        Gson gson = GSONFactory.getInstance();

        Employee createdEmployee = handleCreate(body);

        String serializedEmployee = gson.toJson(createdEmployee);

        Response response = Response.status(201).entity(serializedEmployee).build();
        return response;
    }


    // Edit/Modify an object
    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String handlePostModify(@PathParam("id") String id) {
        return "post for " + id;
    }

    // Delete an object
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleDelete(@PathParam("id") String id) {
        Employee employee = handleDeleteById(id);
        if (employee == null) {
            Response response = Response.status(404).build();
            return response;
        } else {
            Response response = Response.ok().entity("deleted " + id).build();
            return response;
        }
    }

    private Employee handleGetById(String id) {
        Iterator<Employee> employeesIter = EmployeeContainer.employees.iterator();

        while (employeesIter.hasNext()) {
            Employee employee = employeesIter.next();
            if (id != null && id.equals(employee.getId())) {
                return employee;
            }
        }
        return null;
    }

    private Employee handleDeleteById(String id) {
        Iterator<Employee> employeesIter = EmployeeContainer.employees.iterator();

        if (employeesIter.hasNext()) {
            Employee employee = employeesIter.next();
            if (id != null && id.equals(employee.getId())) {
                EmployeeContainer.employees.remove(employee);
                return employee;
            }
        }
        return null;
    }

    private Employee handleCreate(String body) {
        String newId = createId();
        return handleCreate(newId, body);
    }

    private Employee handleCreate(String id, String body) {
        Gson gson = GSONFactory.getInstance();

        // TODO: validation

        Employee employee = gson.fromJson(body, Employee.class);
        employee.setId(id);

        EmployeeContainer.employees.add(employee);
        return employee;
    }

    // TODO: remove this, replace with auto-increment IDs.
    private String createId() {
        int idLength = 5;
        String possibleChars = "1234567890";
        Random random = new Random();
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i < idLength; i++) {
            int randomInt = random.nextInt(10);
            newString.append(possibleChars.charAt(randomInt));
        }

        return newString.toString();
    }
}