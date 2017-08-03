package org.sil.cmb.employee_svc;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

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
    public void testGetAll() {
        Response output = target("/employee").request().get();
        assertEquals(200, output.getStatus());
        assertEquals("getall", output.readEntity(String.class));
    }

    @Test
    public void testGetById() {
        Response output = target("/employee/1234").request().get();
        assertEquals(200, output.getStatus());
        assertEquals("get by id for 1234", output.readEntity(String.class));
    }

    @Test
    public void testPut() {
        Response output = target("/employee/2345").request().put(Entity.json(new String()));
        assertEquals(200, output.getStatus());
        assertEquals("put to 2345", output.readEntity(String.class));
    }


    @Test
    public void testPost() {
        Response output = target("/employee/3456").request().post(Entity.json(new String()));
        assertEquals(200, output.getStatus());
        assertEquals("post for 3456", output.readEntity(String.class));
    }


    @Test
    public void testDelete() {
        Response output = target("/employee/4567").request().delete();
        assertEquals(200, output.getStatus());
        assertEquals("delete for 4567", output.readEntity(String.class));
    }
}
