package com.example;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        // Initializing the Context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Demonstrating BeanFactory usage (as requested)
        // ApplicationContext is a sub-interface of BeanFactory
        BeanFactory factory = context;

        // Retrieving the service bean from the factory
        EmployeeService service = factory.getBean(EmployeeService.class);

        // Invoking the business logic
        service.displayEmployees();

        // Closing the context
        context.close();
    }
}
