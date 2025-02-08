package com.hasanka.emloyee_management.service;

import com.hasanka.emloyee_management.dao.EmployeeDAO;
import com.hasanka.emloyee_management.entity.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO =new EmployeeDAO();

    public String saveEmployee(Employee employee) {
        boolean saveEmployee = employeeDAO.saveEmployee(employee);
        if (saveEmployee) {

        return "Employee saved successfully";
        }else {
            return "Employee not saved";
        }
    }

    public List<Employee> getAllEmployees(){
        return employeeDAO.getAllEmployees();
    }

    public Employee getEmployeeById(int id) {
        Employee employee = employeeDAO.getEmployeeById(id);
        if (employee != null) {
        return employee;
        }
        return null;
    }

    public List<Employee> searchEmployees(String name,String position,String department,Date hireDate){
        return employeeDAO.searchEmployees(name,position,department,hireDate);

    }

    public String deleteEmployee(int id) {
        boolean employee = employeeDAO.deleteEmployee(id);
        if (employee) {
        return "Employee deleted successfully";
        }else {
            return "Employee not found";
        }
    }

    public String updateEmployee(Employee employee) {
        employeeDAO.updateEmployee(employee);
        return "Employee updated successfully";
    }

}
