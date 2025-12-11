package com.example.sms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sms.entity.Student;
import com.example.sms.exception.StudentNotFoundException;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {

        if (student.getName() == null || student.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (student.getEmail() == null || student.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (student.getYearOfStudy() < 1 || student.getYearOfStudy() > 4) {
            throw new IllegalArgumentException("Year must be between 1 and 4");
        }

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    @Override
    public Student updateStudent(Long id, Student updatedStudent) {

        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        if (updatedStudent.getName() != null) {
            existing.setName(updatedStudent.getName());
        }

        if (updatedStudent.getEmail() != null) {
            if (!existing.getEmail().equals(updatedStudent.getEmail())
                    && studentRepository.existsByEmail(updatedStudent.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            existing.setEmail(updatedStudent.getEmail());
        }

        if (updatedStudent.getDepartment() != null) {
            existing.setDepartment(updatedStudent.getDepartment());
        }

        if (updatedStudent.getYearOfStudy() >= 1 && updatedStudent.getYearOfStudy() <= 4) {
            existing.setYear(updatedStudent.getYear());
        }

        return studentRepository.save(existing);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}
