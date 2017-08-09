package org.sil.cmb.employee_svc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.hibernate.Session;
import org.junit.Test;
import org.sil.cmb.employee_svc.gson.GSONFactory;
import org.sil.cmb.employee_svc.model.Employee;
import org.sil.cmb.employee_svc.model.EmploymentStatus;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class EmployeeResourceTests extends JerseyTest {

    @Override
    public Application configure() {
        // change if you want debugging info
        if (true) {
            enable(TestProperties.LOG_TRAFFIC);
            enable(TestProperties.DUMP_ENTITY);
        }
        return new ResourceConfig(EmployeeResource.class);
    }

    @Test
    public void testGetAllWithCreateAndDelete() {
        Gson gson = GSONFactory.getInstance();

        String expectedName = "testing name";
        String id = "1234";

        // TODO: delete all before beginning test.
        Session session = org.sil.cmb.employee_svc.hibernate.SessionFactory.getSession();
        session.createQuery("DELETE FROM Employee").executeUpdate();
        session.close();


        Response output = target("/employee/").request().get();
        assertEquals(200, output.getStatus());

        String outputJson = output.readEntity(String.class);
        List<Employee> employeeList = gson.fromJson(outputJson, ArrayList.class);
        assertThat(employeeList.size(), is(0));


        String json = "{id: '1234', name: '" + expectedName + "'}";

        output = target("/employee/" + id).request().put(Entity.json(json));
        assertEquals(201, output.getStatus());
        assertThat(output.readEntity(String.class), containsString("\"name\":\"" + expectedName + "\""));

        output = target("/employee").request().get();
        assertEquals(200, output.getStatus());

        // read the output (ugh!)
        outputJson = output.readEntity(String.class);
        Type expectedType = new TypeToken<ArrayList<Employee>>(){}.getType();
        ArrayList<Employee> employeeArrayList = gson.fromJson(outputJson, expectedType);
        assertThat(employeeArrayList.size(), is(1));
        assertThat(employeeArrayList.get(0).getName(), is(expectedName));

        output = target("/employee/" + id).request().get();
        assertEquals(200, output.getStatus());
        assertThat(output.readEntity(String.class), containsString(expectedName));


        // clean up
        output = target("/employee/" + id).request().delete();
        assertEquals(200, output.getStatus());
        assertThat(output.readEntity(String.class).length(), is(0));

        // get by Id should now fail.
        output = target("/employee/" + id).request().get();
        assertThat(output.getStatus(), is(404));

        // nothing should be return by getAll.
        output = target("/employee/").request().get();
        assertEquals(200, output.getStatus());

        outputJson = output.readEntity(String.class);
        employeeList = gson.fromJson(outputJson, ArrayList.class);
        assertThat(employeeList.size(), is(0));
    }

    @Test
    public void testGetById() {
        Response output = target("/employee/1234").request().get();
        assertEquals(404, output.getStatus());
        assertEquals("", output.readEntity(String.class));
    }

    @Test
    public void testPostCreate() {
        // Object to be stored.
        String expectedName = "Bob Smith";
        String expectedTitle = "A Title";
        String expectedDepartment = "Computer Services";
        String expectedCNPSNo = "123456789";
        String json = getJson(expectedName, expectedTitle, expectedDepartment, expectedCNPSNo);

        // Send post request to create.
        Response output = target("/employee/").request().post(Entity.json(json));
        assertEquals(201, output.getStatus());
        String jsonOutput = output.readEntity(String.class);

        // validate output.
        Gson gson = GSONFactory.getInstance();
        Employee createdAndReturnedEmployee = gson.fromJson(jsonOutput, Employee.class);

        assertThat(createdAndReturnedEmployee.getId(), is(notNullValue()));
        // TODO: rinse, repeat
        assertThat(createdAndReturnedEmployee.getStatus(), is(EmploymentStatus.FULL_TIME));

        // Attempt to retrieve the id sent in the JSON by making another service call.
        String createdId = createdAndReturnedEmployee.getId();
        output = target("/employee/" + createdId).request().get();
        assertEquals(200, output.getStatus());

        // validate output
        jsonOutput = output.readEntity(String.class);
        Employee retrievedEmployee = gson.fromJson(jsonOutput, Employee.class);

        assertThat(createdAndReturnedEmployee.getId(), is(notNullValue()));
        assertThat(createdAndReturnedEmployee.getId(), is(createdId));
        // TODO: rinse, repeat
        assertThat(createdAndReturnedEmployee.getStatus(), is(EmploymentStatus.FULL_TIME));
    }

    @Test
    public void testPut() {
        // Object to be stored.
        String expectedId = "2345";
        String expectedName = "Bob Smith";
        String expectedTitle = "A Title";
        String expectedDepartment = "Computer Services";
        String expectedCNPSNo = "123456789";
        String json = getJson(expectedId, expectedName, expectedTitle, expectedDepartment, expectedCNPSNo);


        Response output = target("/employee/" + expectedId).request().put(Entity.json(json));
        assertEquals(201, output.getStatus());
        String jsonOutput = output.readEntity(String.class);

        Gson gson = GSONFactory.getInstance();
        Employee createdAndReturnedEmployee = gson.fromJson(jsonOutput, Employee.class);

        assertThat(createdAndReturnedEmployee.getId(), is(notNullValue()));
        assertThat(createdAndReturnedEmployee.getId(), is(expectedId));
        // TODO: rinse, repeat
        assertThat(createdAndReturnedEmployee.getStatus(), is(EmploymentStatus.FULL_TIME));
    }


    @Test
    public void testPostToModify() {

        // Created an object to modify later.
        String expectedName = "Bob Smith";
        String expectedTitle = "A Title";
        String expectedDepartment = "Computer Services";
        String expectedCNPSNo = "123456789";
        String json = getJson(expectedName, expectedTitle, expectedDepartment, expectedCNPSNo);


        Response output = target("/employee/").request().post(Entity.json(json));
        assertEquals(201, output.getStatus());
        String jsonOutput = output.readEntity(String.class);

        Gson gson = GSONFactory.getInstance();
        Employee createdEmployee = gson.fromJson(jsonOutput, Employee.class);
        String createdEmployeeId = createdEmployee.getId();

        // Modify the object.
        String newName = "NEW NAME";
        createdEmployee.setName(newName);
        createdEmployee.setStatus(EmploymentStatus.PART_TIME);

        String modifiedJson = gson.toJson(createdEmployee);
        output = target("/employee/" + createdEmployeeId).request().post(Entity.json(modifiedJson));

        assertEquals(200, output.getStatus());
        String postJsonOutput = output.readEntity(String.class);

        Employee modifiedEmployee = gson.fromJson(postJsonOutput, Employee.class);

        assertThat(modifiedEmployee.getId(), is(notNullValue()));
        assertThat(modifiedEmployee.getId(), is(createdEmployeeId));
        assertThat(modifiedEmployee.getName(), is(newName));
        assertThat(modifiedEmployee.getStatus(), is(EmploymentStatus.PART_TIME));
    }

    @Test
    public void testDeleteNonExistentId() {
        Response output = target("/employee/4567").request().delete();
        assertEquals(404, output.getStatus());
    }


    private String getJson(String expectedName, String expectedTitle, String expectedDepartment,
                           String expectedCNPSNo) {

        return getJson(null, expectedName, expectedTitle, expectedDepartment, expectedCNPSNo);
    }

    private String getJson(String expectedId, String expectedName, String expectedTitle,
                           String expectedDepartment, String expectedCNPSNo) {

        String expectedIdJson = "";

        if (expectedId != null) {
            expectedIdJson = ", id: \"" + expectedId + "\"";
        }

        String json =
            "{ " +
              "name: '" + expectedName + "'," +
              "status: 'FULL_TIME'," +
              "title: '" + expectedTitle + "'," +
              "department: '" + expectedDepartment + "'," +
              "gender: 'MALE', " +
              "CNPSno: '" + expectedCNPSNo + "'," +
              "maritalStatus: 'SINGLE'," +
              "children: [" +
                "'no 1'," +
                "'no 2'," +
                "'no 3'" +
              "]," +
              "birthDate: '1966-02-27'" + expectedIdJson +
            "}";

        return json;
    }
}
