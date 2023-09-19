package com.cst438.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@RestController
@CrossOrigin
public class StudentController {
//Add in the Student Repository
    @Autowired
    private StudentRepository studentRepository;
//Returns a list of all Students
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }
//Gets Student by ID or returns student not found
    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable Integer studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }
//We use post to add the student to the repository

    @PostMapping("/students")
    @Transactional
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
  //We use put to update the student to the repository

    @PutMapping("/students/{studentId}")
    @Transactional
    public Student updateStudent(@PathVariable Integer studentId, @RequestBody Student updatedStudent) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());

        return studentRepository.save(existingStudent);
    }

//Delete Mapping is used to remove a student, we find the student first then we can remove them
    //if the student doesn't exist we can't delete it so the else throw will be used
    @DeleteMapping("/students/{studentId}")
    @Transactional
    public void deleteStudent(@PathVariable Integer studentId) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        studentRepository.delete(existingStudent);
    }

}
