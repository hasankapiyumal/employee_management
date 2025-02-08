package com.hasanka.emloyee_management.dao;

import com.hasanka.emloyee_management.entity.Employee;
import com.hasanka.emloyee_management.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class EmployeeDAO {

    Transaction transaction=null;
    public boolean saveEmployee(Employee employee) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

            }
            e.printStackTrace();
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> employees = session.createQuery("from Employee").list();
        session.close();
        return employees;
    }

    public Employee getEmployeeById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employee = session.get(Employee.class, id);
        session.close();
        return employee;
    }

    public List<Employee> searchEmployees(String name, String position, String department, Date  hireDate){
        Session session=HibernateUtil.getSessionFactory().openSession();
        System.out.println("This is hireDate :-"+hireDate);
       try {
           // Start building the query dynamically
           StringBuilder queryBuilder = new StringBuilder("FROM Employee WHERE 1=1");

           if (name != null && !name.isEmpty()) {
               queryBuilder.append(" AND name = :name");
           }
           if (position != null && !position.isEmpty()) {
               queryBuilder.append(" AND position = :position");
           }
           if (department != null && !department.isEmpty()) {
               queryBuilder.append(" AND department = :department");
           }
           if (hireDate != null) {
               queryBuilder.append(" AND hireDate = :hireDate");
           }

           // Create the query
           Query<Employee> query = session.createQuery(queryBuilder.toString(), Employee.class);

           // Set parameters if they are not null
           if (name != null && !name.isEmpty()) {
               query.setParameter("name", name);
           }
           if (position != null && !position.isEmpty()) {
               query.setParameter("position", position);
           }
           if (department != null && !department.isEmpty()) {
               query.setParameter("department", department);
           }
           if (hireDate != null) {
               query.setParameter("hireDate", hireDate);
           }

           // Execute the query and return the results
           return query.getResultList();
       } finally {
           session.close();
       }

    }

    public  boolean updateEmployee(Employee employee){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int id){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            session.delete(employee);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            return false;
        }

    }

}

