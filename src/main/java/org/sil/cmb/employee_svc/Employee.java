package org.sil.cmb.employee_svc;

public class Employee {

    private String name; // TODO given/surnames
    private EmploymentStatus status;
    private String title;
    private String department;
    private Employee supervisor;
    private Gender gender;
    private String birthDate;
    private String CNPSno;
    private MaritalStatus martialStatus;
    private String[] children;
    private boolean isStudent;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmploymentStatus getStatus() {
        return this.status;
    }

    public void setStatus(EmploymentStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
