package org.sil.cmb.employee_svc;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.sil.cmb.employee_svc.model.Employee;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class EmployeeResourceTests extends JerseyTest {

    @Override
    public Application configure() {
        // change if you want debugging info
        if (false) {
            enable(TestProperties.LOG_TRAFFIC);
            enable(TestProperties.DUMP_ENTITY);
        }
        return new ResourceConfig(EmployeeResource.class);
    }

    @Test
    public void testGetAllWithCreateAndDelete() {
        String expectedName = "testing name";
        String id = "1234";

        EmployeeContainer.employees = new ArrayList<Employee>();

        Response output = target("/employee/").request().get();
        assertEquals(200, output.getStatus());
        assertEquals("0", output.readEntity(String.class));

        String json = "{id: '1234', name: '" + expectedName + "'}";

        output = target("/employee/" + id).request().put(Entity.json(json));
        assertEquals(201, output.getStatus());
        assertThat(output.readEntity(String.class), containsString("\"name\":\"" + expectedName + "\""));

        output = target("/employee").request().get();
        assertEquals(200, output.getStatus());
        assertEquals("1", output.readEntity(String.class));

        output = target("/employee/" + id).request().get();
        assertEquals(200, output.getStatus());
        assertEquals(expectedName, output.readEntity(String.class));

        output = target("/employee").request().get();
        assertEquals(200, output.getStatus());
        assertEquals("1", output.readEntity(String.class));

        // clean up
        output = target("/employee/" + id).request().delete();
        assertEquals(200, output.getStatus());
        assertEquals("deleted " + id, output.readEntity(String.class));
    }

    @Test
    public void testGetById() {
        Response output = target("/employee/1234").request().get();
        assertEquals(404, output.getStatus());
        assertEquals("", output.readEntity(String.class));
    }

    @Test
    public void testPut() {
        // Object to be stored.

        String expectedName = "Bob Smith";
        String expectedTitle = "A Title";
        String expectedDepartment = "Computer Services";
        String expectedCNPSNo = "123456789";

        String json =
                "{ " +
                  "name: '" + expectedName + "'," +
                  "status: 'FULL_TIME'," +
                  "title: '" + expectedTitle + "'," +
                  "department: '" + expectedDepartment + "'," +
                  "supervisor: {" +
                    "'id': '1234'," +
                    "'name': 'Bob Supervisor'" +
                  "}," +
                  "gender: 'MALE', " +
                  "CNPSno: '" + expectedCNPSNo + "'," +
                  "maritalStatus: 'SINGLE'," +
                  "children: [" +
                    "'no 1'," +
                    "'no 2'," +
                    "'no 3'" +
                  "]," +
                  "birthDate: '1966-02-27'" +
                "}";

        Response output = target("/employee/2345").request().put(Entity.json(json));
        assertEquals(201, output.getStatus());
        String jsonOutput = output.readEntity(String.class);

        // TODO: rinse, repeat
        assertThat(jsonOutput, containsString("\"status\":\"FULL_TIME\""));
    }


    @Test
    public void testPost() {
        Response output = target("/employee/3456").request().post(Entity.json(new String()));
        assertEquals(200, output.getStatus());
        assertEquals("post for 3456", output.readEntity(String.class));
    }


    @Test
    public void testDeleteNonExistentId() {
        Response output = target("/employee/4567").request().delete();
        assertEquals(404, output.getStatus());
    }
}
