package com.example;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        // Sample in-memory data
        employees.add(new Employee(101, "Amit Sharma", "Engineering"));
        employees.add(new Employee(102, "Sushanth Kumar", "Marketing"));
        employees.add(new Employee(103, "Ravi Teja", "Finance"));
    }

    public List<Employee> findAll() {
        return employees;
    }
}
