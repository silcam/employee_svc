package org.sil.cmb.employee_svc;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class PingResourceTests extends JerseyTest {

    @Override
    public Application configure() {
        // change if you want debugging info
        if (false) {
            enable(TestProperties.LOG_TRAFFIC);
            enable(TestProperties.DUMP_ENTITY);
        }
        return new ResourceConfig(PingResource.class);
    }

    @Test
    public void testPing() {
        Response output = target("/ping").request().get();
        assertEquals(200, output.getStatus());
        assertEquals("pong", output.readEntity(String.class));
        assertEquals("expected", output.getHeaderString("value"));
    }
}
