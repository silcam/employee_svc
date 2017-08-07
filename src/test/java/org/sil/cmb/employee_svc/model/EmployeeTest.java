package org.sil.cmb.employee_svc.model;

import org.joda.time.DateTime;
import org.junit.Test;
import org.sil.cmb.employee_svc.model.Employee;
import org.sil.cmb.employee_svc.model.EmploymentStatus;

import java.util.ArrayList;

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

    @Test
    public void testDepartment() {
        String expectedDepartment = "Computer Services";
        Employee employee = new Employee("id", "Employee Name");
        assertNull(employee.getDepartment());
        employee.setDepartment(expectedDepartment);
        assertEquals(expectedDepartment, employee.getDepartment());
    }

    @Test
    public void testSupervisor() {
        Employee supervisor = new Employee("id1", "Joe Supervisor");
        Employee employee = new Employee("id", "Employee Name");
        assertNull(employee.getSupervisor());
        employee.setSupervisor(supervisor);
        // not necessarily assertSame
        // TODO: equals() on employee object?
        assertEquals(supervisor.getId(), employee.getSupervisor().getId());
    }

    @Test
    public void testGender() {
        Employee employee = new Employee("id", "Employee Name");
        assertNull(employee.getGender());
        employee.setGender(Gender.FEMALE);
        assertEquals(Gender.FEMALE, employee.getGender());
    }

    @Test
    public void testBirthDate() {
        String expectedBirthDate = "2016-01-01";
        DateTime jodaDate = new DateTime(expectedBirthDate);

        Employee employee = new Employee("id", "Employee Name");
        assertNull(employee.getBirthDate());
        employee.setBirthDate(jodaDate);
        assertEquals(jodaDate.toString(), employee.getBirthDate());

        String newExpectedBirthdate = "2001-01-31";
        DateTime newJodaDate = new DateTime(newExpectedBirthdate);
        employee.setBirthDate(newExpectedBirthdate);
        assertEquals(newJodaDate.toString(), employee.getBirthDate());
    }

    @Test
    public void testCNPSno() {
        String expectedCNPSno = "123456";
        Employee employee = new Employee("id", "Employee Name");
        assertNull(employee.getCNPSno());
        employee.setCNPSno(expectedCNPSno);
        assertNotNull(expectedCNPSno, employee.getCNPSno());
    }

    @Test
    public void testMartialStatus() {
        Employee employee = new Employee("id", "Employee Name");
        assertNull(employee.getMartialStatus());
        employee.setMartialStatus(MaritalStatus.SINGLE);
        assertEquals(MaritalStatus.SINGLE, employee.getMartialStatus());
    }

    @Test
    public void testChildren() {
        String expectedChildName = "child1";
        Employee employee = new Employee("id", "Employee Name");

        String [] children = employee.getChildren();

        assertNotNull(children);
        assertEquals(0, children.length);

        employee.addChild(expectedChildName);

        children = employee.getChildren();

        assertEquals(1, children.length);
        assertEquals(expectedChildName, children[0]);
    }
}
