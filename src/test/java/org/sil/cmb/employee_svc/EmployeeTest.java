package org.sil.cmb.employee_svc;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeeTest {

    @Test
    public void testEmployeeClass() {
        Employee employee = new Employee("id","Employee Name");
        assertNotNull(employee);
    }

    // TODO should this be final?
    // TODO if so, how are name changes handled?
    @Test
    public void testEmployeeName() {
        String name = "full_name";
        String newName = "new name";

        Employee employee = new Employee("id", name);
        assertEquals(name, employee.getName());
        employee.setName(newName);
        assertEquals(newName, employee.getName());
    }

    @Test
    public void testEmployeeTitle() {
        String title = "Director";

        Employee employee = new Employee("id", "Employee Name");
        assertNull(employee.getTitle());
        employee.setTitle(title);
        assertEquals(title, employee.getTitle());

    }

    @Test
    public void testEmployeeStatus() {
        Employee employee = new Employee("id", "Employee Name");
        assertNull(employee.getStatus());
        employee.setStatus(EmploymentStatus.FULL_TIME);
        assertEquals(EmploymentStatus.FULL_TIME, employee.getStatus());
    }
}
