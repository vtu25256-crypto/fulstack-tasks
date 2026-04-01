package com.example.task12;

import com.example.task12.entity.Product;
import com.example.task12.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Task12Application {

    public static void main(String[] args) {
        SpringApplication.run(Task12Application.class, args);
    }

    @Bean
    CommandLineRunner runner(ProductRepository repository) {
        return args -> {
            System.out.println("=== Seeding Initial Product Data ===");
            repository.save(new Product("Laptop", "High-performance gaming laptop", 1500.00));
            repository.save(new Product("Smartphone", "Latest model with AMOLED screen", 800.00));
            repository.save(new Product("Wireless Headphones", "Noise-cancelling over-ear headphones", 250.00));
            System.out.println("Data Seeding Complete. REST API is ready to accept requests at /api/products");
        };
    }
}
