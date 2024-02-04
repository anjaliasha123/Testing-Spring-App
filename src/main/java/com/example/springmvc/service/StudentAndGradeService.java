package com.example.springmvc.service;

import com.example.springmvc.models.CollegeStudent;
import com.example.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    @Autowired
    private StudentDao studentDao;
    public void createStudent(String firstName, String lastName, String email){
        CollegeStudent student = new CollegeStudent(firstName, lastName, email);
        student.setId(0);
        studentDao.save(student);
    }
    public boolean checkIfStudentIsNUll(int id){
        Optional<CollegeStudent> student = studentDao.findById(id);
        return student.isPresent();
    }
    public void deleteStudent(int id){
        studentDao.deleteById(id);
    }
    public Iterable<CollegeStudent> getGradeBook(){
        Iterable<CollegeStudent> collegeStudents = studentDao.findAll();
        return collegeStudents;
    }
}
