package com.example.controller;

import com.example.entity.Student;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping
    public List<Student> getAll() {
        return service.getAllStudents();
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return service.saveStudent(student);
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return service.getStudentById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        return service.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteStudent(id);
        return "Student deleted successfully with id: " + id;
    }
}
