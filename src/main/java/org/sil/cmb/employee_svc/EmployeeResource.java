package org.sil.cmb.employee_svc;

import com.google.gson.Gson;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.sil.cmb.employee_svc.gson.GSONFactory;
import org.sil.cmb.employee_svc.hibernate.SessionFactory;
import org.sil.cmb.employee_svc.model.Employee;
import org.sil.cmb.employee_svc.model.EmploymentStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Path("/employee")
public class EmployeeResource {

    /**
     * Retrieve all objects
     * <p>
     * TODO: pagination, etc.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleGet() {
        Gson gson = GSONFactory.getInstance();
        List allEmployees = getAllEmployees();

        if (allEmployees == null) {
            allEmployees = new ArrayList();
        }

        Response response = Response.ok().entity(gson.toJson(allEmployees)).build();
        return response;
    }

    /**
     * Retrieve a specific object
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleGetbyId(@PathParam("id") String id) {
        Gson gson = GSONFactory.getInstance();
        Employee employee = getEmployeeById(id);

        if (employee == null) {
            Response response = Response.status(404).build();
            return response;
        } else {
            String jsonOutput = gson.toJson(employee);
            Response response = Response.ok().entity(jsonOutput).build();
            return response;
        }
    }

    /**
     * Create an object if you know the expected id.
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handlePut(@PathParam("id") String id, String body) {
        Gson gson = GSONFactory.getInstance();

        Employee employee = createEmployee(id, body);

        String serializedEmployee = gson.toJson(employee);

        Response response = Response.status(201).entity(serializedEmployee).build();
        return response;
    }

    /**
     * Create an object without a known id
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handlePostCreate(String body) {
        Gson gson = GSONFactory.getInstance();

        Employee createdEmployee = createEmployee(body);

        String serializedEmployee = gson.toJson(createdEmployee);

        Response response = Response.status(201).entity(serializedEmployee).build();
        return response;
    }


    /**
     * Edit/Modify an object
     */
    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handlePostModify(@PathParam("id") String id, String body) {
        Gson gson = GSONFactory.getInstance();

        // TODO: validation?
        Employee modifiedEmployee = editEmployee(id, body);
        String outputJson = gson.toJson(modifiedEmployee);

        Response response = Response.ok().entity(outputJson).build();
        return response;
    }

    /**
     * Delete an object
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleDelete(@PathParam("id") String id) {
        Employee employee = deleteEmployeeById(id);
        if (employee == null) {
            Response response = Response.status(404).build();
            return response;
        } else {
            Response response = Response.ok().build();
            return response;
        }
    }

    private Employee getEmployeeById(String id) {
        Session session = SessionFactory.getSession();
        Employee employee = null;

        try {
            // TODO: null checking, type checking, etc.
            employee = (Employee) session.get(Employee.class, id);
        } finally {
            session.close();
        }
        return employee;
    }

    private Employee deleteEmployeeById(String id) {
        Session session = SessionFactory.getSession();
        Employee employee = null;
        Transaction txn = null;
        try {
            txn = session.beginTransaction();
            employee = (Employee) session.get(Employee.class, id);
            session.delete(employee);
            txn.commit();
        } catch (Exception e) {
            // TODO FIXME
            if (txn != null) txn.rollback();
        } finally {
            session.close();
        }

        return employee;
    }

    private Employee createEmployee(String body) {
        String newId = createId();
        return createEmployee(newId, body);
    }

    private Employee createEmployee(String id, String body) {
        Gson gson = GSONFactory.getInstance();

        Session session = SessionFactory.getSession();
        Employee employee = null;

        Transaction txn = null;
        try {
            txn = session.beginTransaction();

            employee = gson.fromJson(body, Employee.class);
            employee.setId(id);

            session.save(employee);

            txn.commit();
        } catch (Exception e) {
            if (txn != null) txn.rollback();
        } finally {
            session.close();
        }

        return employee;
    }

    private Employee editEmployee(String id, String body) {
        Gson gson = GSONFactory.getInstance();
        Session session = SessionFactory.getSession();

        Employee modifiedEmployee = gson.fromJson(body, Employee.class);
        // TODO: what is correct behavior?
        modifiedEmployee.setId(id);

        Transaction txn = null;
        try {
            txn = session.beginTransaction();

            session.save(modifiedEmployee);

            txn.commit();
        } catch (Exception e) {
            if (txn != null) txn.rollback();
        } finally {
            session.close();
        }

        return modifiedEmployee;
    }

    private List getAllEmployees() {
        Session session = SessionFactory.getSession();
        List employees = null;

        try {
            employees = (List) session.createCriteria(Employee.class).list();
        } finally {
            session.close();
        }

        return employees;
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