package org.sil.cmb.employee_svc;

import com.google.gson.Gson;
import org.sil.cmb.employee_svc.model.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/employee")
public class EmployeeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleGet() {
        Response response = Response.ok().entity(String.valueOf(EmployeeContainer.employees.size())).build();
        return response;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleGetbyId(@PathParam("id") String id) {
        Employee employee = handleGetById(id);

        if (employee == null) {
            Response response = Response.status(404).build();
            return response;
        } else {
            Response response = Response.ok().entity(employee.getName()).build();
            return response;
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handlePut(@PathParam("id") String id, String body) {
        handleCreate(id, body);

        Response response = Response.status(201).entity("created " + id).build();
        return response;
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

        if (employeesIter.hasNext()) {
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


    private void handleCreate(String id, String body) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(body);

//        Gson gson = new Gson();
//        Employee employee = gson.fromJson(body, Employee.class);

        EmployeeContainer.employees.add(employee);
    }
}