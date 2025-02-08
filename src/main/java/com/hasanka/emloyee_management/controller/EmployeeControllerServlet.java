package com.hasanka.emloyee_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasanka.emloyee_management.entity.Employee;
import com.hasanka.emloyee_management.service.EmployeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/employee")
public class EmployeeControllerServlet extends HttpServlet {
    private EmployeeService employeeService =new EmployeeService();
    private ObjectMapper objectMapper =new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String position = request.getParameter("position");
        String department = request.getParameter("department");
        String hireDate=request.getParameter("hireDate");

        if (id != null) {
            Employee employee = employeeService.getEmployeeById(Integer.parseInt(id));
            response.setContentType("application/json");
            if (employee == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Employee not found");
            }else {
                response.getWriter().write(objectMapper.writeValueAsString(employee));
            }



        } else if (name != null || position != null || department != null || hireDate != null) {

            Date formatDate=null;
            if (hireDate != null && !hireDate.isEmpty()) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    formatDate= dateFormat.parse(hireDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            List<Employee> employees = employeeService.searchEmployees(name, position, department, formatDate);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(employees));
        }else {
            List<Employee> allEmployees = employeeService.getAllEmployees();
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(allEmployees));

        }


    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       Employee employee = objectMapper.readValue(request.getReader(),Employee.class);
       employeeService.saveEmployee(employee);
       response.setContentType("application/json");
       response.getWriter().write(objectMapper.writeValueAsString("Employee saved successfully"));
       response.setStatus(HttpServletResponse.SC_CREATED);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = objectMapper.readValue(req.getReader(),Employee.class);
        employeeService.updateEmployee(employee);
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString("Employee updated successfully"));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String employee = employeeService.deleteEmployee(id);
        response.getWriter().write(employee);
    }
}
