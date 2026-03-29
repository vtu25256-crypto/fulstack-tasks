package com.example.task11;

import com.example.task11.entity.Student;
import com.example.task11.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class Task11Application {

    public static void main(String[] args) {
        SpringApplication.run(Task11Application.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentRepository repository) {
        return args -> {
            // Seed Database
            System.out.println("=== Seeding Database ===");
            repository.save(new Student("Alice", "Computer Science", 20));
            repository.save(new Student("Bob", "Computer Science", 22));
            repository.save(new Student("Charlie", "Mathematics", 21));
            repository.save(new Student("David", "Computer Science", 23));
            repository.save(new Student("Eve", "Physics", 20));
            
            // Test 1: Pagination by Department
            System.out.println("\n=== Students in 'Computer Science' (Page 0, Size 2) ===");
            PageRequest pageRequest = PageRequest.of(0, 2);
            Page<Student> csStudentsPage = repository.findByDepartment("Computer Science", pageRequest);
            csStudentsPage.getContent().forEach(System.out::println);
            System.out.println("Total Pages: " + csStudentsPage.getTotalPages());

            // Test 2: Sort by age descending where age > 20
            System.out.println("\n=== Students older than 20, sorted by age DESC ===");
            Sort sortByAgeDesc = Sort.by("age").descending();
            List<Student> olderStudents = repository.findByAgeGreaterThan(20, sortByAgeDesc);
            olderStudents.forEach(System.out::println);
        };
    }
}
