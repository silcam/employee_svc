package org.sil.cmb.employee_svc;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeeTest {

    @Test
    public void testEmployeeClass() {
        Employee employee = new Employee();
        assertNotNull(employee);
    }

    @Test
    public void testEmployeeName() {

        String name = "full_name";

        Employee employee = new Employee();
        assertNull(employee.getName());
        employee.setName(name);
        assertEquals(name, employee.getName());
    }

}
