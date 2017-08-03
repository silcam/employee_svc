package org.sil.cmb.employee_svc;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

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
    public void testBasicGet() {
        Response output = target("/myresource").request().get();
        assertEquals(200, output.getStatus());
        assertEquals("this is normal.", output.readEntity(String.class));
    }
}
