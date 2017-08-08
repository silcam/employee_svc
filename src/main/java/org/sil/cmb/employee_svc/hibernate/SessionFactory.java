package org.sil.cmb.employee_svc.hibernate;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.sil.cmb.employee_svc.model.Employee;

public class SessionFactory {

    private static org.hibernate.SessionFactory sessionFactory = null;

    private SessionFactory() {}

    public static Session getSession() {

        if (sessionFactory == null) {
            AnnotationConfiguration cfg = new AnnotationConfiguration();
            cfg.addAnnotatedClass(Employee.class);
            cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file

            //creating session factory object
            sessionFactory = cfg.buildSessionFactory();
        }

        return sessionFactory.openSession();
    }
}
