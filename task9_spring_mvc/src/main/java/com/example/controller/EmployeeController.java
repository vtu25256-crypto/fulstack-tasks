package com.example.controller;

import com.example.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;

@Controller
public class EmployeeController {

    @GetMapping("/employees")
    public String listEmployees(Model model) {
        List<Employee> employees = Arrays.asList(
            new Employee(1, "Rajesh Kumar", "Senior Developer"),
            new Employee(2, "Ananya Singh", "Project Manager"),
            new Employee(3, "Vijay Sharma", "QA Lead")
        );

        // Add employees to model for the view
        model.addAttribute("employees", employees);
        
        // Return view name (mapped to /WEB-INF/views/employee_list.jsp)
        return "employee_list";
    }
}
