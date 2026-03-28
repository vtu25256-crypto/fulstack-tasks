package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public void displayEmployees() {
        System.out.println("--- Employee Management List ---");
        List<Employee> list = repository.findAll();
        for (Employee e : list) {
            System.out.println(e);
        }
    }
}
