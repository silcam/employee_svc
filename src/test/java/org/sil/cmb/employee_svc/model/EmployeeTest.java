package org.sil.cmb.employee_svc.model;

import com.google.gson.*;
import org.joda.time.DateTime;
import org.junit.Test;
import org.sil.cmb.employee_svc.gson.DateTimeDeserializer;
import org.sil.cmb.employee_svc.gson.DateTimeSerializer;

import java.lang.reflect.Type;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class EmployeeTest {

    @Test
    public void testEmployeeClass() {
        Employee employee = new Employee();
        employee.setId("id");
        employee.setName("Employee Name");
        assertNotNull(employee);
    }

    // TODO should this be final?
    // TODO if so, how are name changes handled?
    @Test
    public void testEmployeeName() {
        String name = "full_name";
        String newName = "new name";

        Employee employee = new Employee();
        employee.setId("id");
        employee.setName(name);
        assertEquals(name, employee.getName());
        employee.setName(newName);
        assertEquals(newName, employee.getName());
    }

    @Test
    public void testEmployeeTitle() {
        String title = "Director";

        Employee employee = new Employee();
        employee.setId("id");
        employee.setName("Employee Name");
        assertNull(employee.getTitle());
        employee.setTitle(title);
        assertEquals(title, employee.getTitle());

    }

    @Test
    public void testEmployeeStatus() {
        Employee employee = new Employee();
        employee.setId("id");
        employee.setName("Employee Name");
        assertNull(employee.getStatus());
        employee.setStatus(EmploymentStatus.FULL_TIME);
        assertEquals(EmploymentStatus.FULL_TIME, employee.getStatus());
    }

    @Test
    public void testDepartment() {
        String expectedDepartment = "Computer Services";

        Employee employee = new Employee();
        employee.setId("id");
        employee.setName("Employee Name");
        assertNull(employee.getDepartment());
        employee.setDepartment(expectedDepartment);
        assertEquals(expectedDepartment, employee.getDepartment());
    }

    @Test
    public void testSupervisor() {
        Employee supervisor = new Employee();
        supervisor.setId("id1");
        supervisor.setName("Joe Supervisor");


        Employee employee = new Employee();
        employee.setId("id");
        employee.setName("Employee Name");

        assertNull(employee.getSupervisor());
        employee.setSupervisor(supervisor);
        // not necessarily assertSame
        // TODO: equals() on employee object?
        assertEquals(supervisor.getId(), employee.getSupervisor().getId());
    }

    @Test
    public void testGender() {
        Employee employee = new Employee();
        employee.setId("id");
        employee.setName("Employee Name");
        assertNull(employee.getGender());
        employee.setGender(Gender.FEMALE);
        assertEquals(Gender.FEMALE, employee.getGender());
    }

    @Test
    public void testBirthDate() {
        String expectedBirthDate = "2016-01-01";
        DateTime jodaDate = new DateTime(expectedBirthDate);

        Employee employee = new Employee();
        employee.setId("id");
        employee.setName("Employee Name");
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
        Employee employee = new Employee();
        employee.setId("id");
        employee.setName("Employee Name");
        assertNull(employee.getCNPSno());
        employee.setCNPSno(expectedCNPSno);
        assertNotNull(expectedCNPSno, employee.getCNPSno());
    }

    @Test
    public void testMartialStatus() {
        Employee employee = new Employee();
        employee.setId("id");
        employee.setName("Employee Name");
        assertNull(employee.getMaritalStatus());
        employee.setMaritalStatus(MaritalStatus.SINGLE);
        assertEquals(MaritalStatus.SINGLE, employee.getMaritalStatus());
    }

    @Test
    public void testChildren() {
        String expectedChildName = "child1";
        Employee employee = new Employee();
        employee.setId("id");
        employee.setName("Employee Name");

        String [] children = employee.getChildren();

        assertNotNull(children);
        assertEquals(0, children.length);

        employee.addChild(expectedChildName);

        children = employee.getChildren();

        assertEquals(1, children.length);
        assertEquals(expectedChildName, children[0]);
    }

    @Test
    public void testObjectSerializationWithGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeDeserializer());
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());
        Gson gson = gsonBuilder.create();

        Employee baseEmployee = new Employee();

        Employee supervisor = new Employee();
        supervisor.setId("2345");
        supervisor.setName("Bob Supervisor");

        String expectedId = "1234";
        String expectedName = "Bob Smith";
        String expectedTitle = "A Title";
        String expectedDepartment = "Computer Services";
        String expectedCNPSNo = "123456789";

        baseEmployee.setId(expectedId);
        baseEmployee.setName(expectedName);
        baseEmployee.setStatus(EmploymentStatus.FULL_TIME);
        baseEmployee.setTitle(expectedTitle);
        baseEmployee.setDepartment(expectedDepartment);
        baseEmployee.setSupervisor(supervisor);
        baseEmployee.setGender(Gender.MALE);
        baseEmployee.setCNPSno(expectedCNPSNo);
        baseEmployee.setMaritalStatus(MaritalStatus.SINGLE);
        baseEmployee.addChild("no 1");
        baseEmployee.addChild("no 2");
        baseEmployee.addChild("no 3");
        baseEmployee.setBirthDate("1966-02-27");

        String serializedEmployee = gson.toJson(baseEmployee);

        assertThat(serializedEmployee, containsString("\"id\":\"" + expectedId + "\""));
        assertThat(serializedEmployee, containsString("\"name\":\"" + expectedName + "\""));
        assertThat(serializedEmployee, containsString("\"status\":\"FULL_TIME\""));
        assertThat(serializedEmployee, containsString("\"title\":\"" + expectedTitle + "\""));
        assertThat(serializedEmployee, containsString("\"department\":\"" + expectedDepartment + "\""));
        assertThat(serializedEmployee, containsString("\"name\":\"Bob Supervisor\""));
        assertThat(serializedEmployee, containsString("\"gender\":\"MALE\""));
        assertThat(serializedEmployee, containsString("\"CNPSno\":\"" + expectedCNPSNo + "\""));
        assertThat(serializedEmployee, containsString("\"maritalStatus\":\"SINGLE\""));
        assertThat(serializedEmployee, containsString("no 1"));
        assertThat(serializedEmployee, containsString("no 2"));
        assertThat(serializedEmployee, containsString("no 3"));

        // TODO: Birth Date Format
        assertThat(serializedEmployee, containsString("no 3"));
    }

    @Test
    public void testObjectDeserializationWithGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeDeserializer());
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());
        Gson gson = gsonBuilder.create();

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

        Employee createdEmployee = gson.fromJson(json, Employee.class);

        assertEquals(expectedName, createdEmployee.getName());
        assertEquals(EmploymentStatus.FULL_TIME, createdEmployee.getStatus());
        assertEquals(expectedTitle, createdEmployee.getTitle());
        assertEquals(expectedDepartment, createdEmployee.getDepartment());
        assertNotNull(createdEmployee.getSupervisor());
        assertEquals("1234", createdEmployee.getSupervisor().getId());
        assertEquals(Gender.MALE, createdEmployee.getGender());
        assertEquals(expectedCNPSNo, createdEmployee.getCNPSno());
        assertEquals(MaritalStatus.SINGLE, createdEmployee.getMaritalStatus());

        String[] employeesChildren = createdEmployee.getChildren();
        assertEquals(3, employeesChildren.length);
        assertEquals("no 1", employeesChildren[0]);
        assertEquals("no 2", employeesChildren[1]);
        assertEquals("no 3", employeesChildren[2]);

        assertEquals(new DateTime("1966-02-27").toString(), createdEmployee.getBirthDate());
    }
}
