package org.sil.cmb.employee_svc.model;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Employee {

    @Id
    private final String id;
    private String name; // TODO given/surnames
    private EmploymentStatus status;
    private String title;
    private String department;
    private Employee supervisor;
    private Gender gender;
    private DateTime birthDate;
    private String CNPSno;
    private MaritalStatus martialStatus;
    private ArrayList<String> children;

    public Employee(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return this.id;
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

    public String getDepartment() { return this.department; }
    public void setDepartment(String department) { this.department = department; }

    // TODO: Person
    public Employee getSupervisor() { return this.supervisor; }
    public void setSupervisor(Employee supervisor) { this.supervisor = supervisor; }

    public Gender getGender() { return this.gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    // TODO: is this the correct API for this?
    public String getBirthDate() {
        if (this.birthDate == null) {
            return null;
        } else {
            return this.birthDate.toString();
        }
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = new DateTime(birthDate);
    }
    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getCNPSno() { return this.CNPSno; }
    public void setCNPSno(String CNPSno) { this.CNPSno = CNPSno; }

    public MaritalStatus getMartialStatus() { return this.martialStatus; }
    public void setMartialStatus(MaritalStatus martialStatus) { this.martialStatus = martialStatus; }

    // TODO: Person
    // TODO: Fix This.
    public String[] getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<String>();
        }
        return this.children.toArray(new String[this.children.size()]);
    }
    public void addChild(String childName) {
        String[] childrenArray = this.getChildren();
        this.children.add(childName);
    }
}