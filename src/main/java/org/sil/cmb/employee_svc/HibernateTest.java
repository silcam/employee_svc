package org.sil.cmb.employee_svc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.sil.cmb.employee_svc.model.Employee;
import org.sil.cmb.employee_svc.model.EmploymentStatus;

public class HibernateTest {

    public static void main(String[] args) throws Exception {
        AnnotationConfiguration cfg = new AnnotationConfiguration();
        cfg.addAnnotatedClass(Employee.class);
        cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file

        //creating session factory object
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object
        Session session = factory.openSession();

        //creating transaction object
        Transaction t = session.beginTransaction();

        Employee emp = new Employee("3334", "User Name");
        emp.setStatus(EmploymentStatus.FULL_TIME);
        emp.setTitle("Master");

        session.persist(emp);//persisting the object

        t.commit();//transaction is commited
        session.close();

        System.out.println("successfully saved");
    }
}