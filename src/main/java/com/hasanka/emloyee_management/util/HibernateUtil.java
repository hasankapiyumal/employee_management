package com.hasanka.emloyee_management.util;

import org.hibernate.SessionFactory;

public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY=buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
