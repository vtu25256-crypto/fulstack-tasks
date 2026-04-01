package com.example.task11.repository;

import com.example.task11.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Pagination by department
    Page<Student> findByDepartment(String department, Pageable pageable);

    // Sorting by age greater than
    List<Student> findByAgeGreaterThan(int age, Sort sort);
}
