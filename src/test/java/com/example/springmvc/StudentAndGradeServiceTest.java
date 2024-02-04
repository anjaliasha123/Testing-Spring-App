package com.example.springmvc;

import com.example.springmvc.models.CollegeStudent;
import com.example.springmvc.repository.StudentDao;
import com.example.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentAndGradeService studentService;
    @Autowired
    private StudentDao studentDao;
    @BeforeEach
    public void setUpDataBase(){
        jdbcTemplate.execute("insert into student(id, firstName, lastName, email_address) "+
                "values (1, 'Eric', 'Robins', 'eric@gmail.com')");
    }
    @Test
    public void createStudentService(){
        studentService.createStudent("sample","test","sample@gmail.com");
        CollegeStudent student = studentDao.findByEmailAddress("sample@gmail.com");
        assertEquals("sample@gmail.com", student.getEmailAddress());
    }
    @Test
    public void isStudentNullCheck(){
        assertTrue(studentService.checkIfStudentIsNUll(1));
        assertFalse(studentService.checkIfStudentIsNUll(0));
    }
    @Test
    public void deleteStudentService(){
        Optional<CollegeStudent> deletedStudent = studentDao.findById(1);
        assertTrue(deletedStudent.isPresent());
        studentService.deleteStudent(1);
        deletedStudent = studentDao.findById(1);
        assertFalse(deletedStudent.isPresent());
    }
    @Sql("/insertData.sql")
    @Test
    public void getGradeBookService(){
        Iterable<CollegeStudent> students = studentService.getGradeBook();
        List<CollegeStudent> collegeStudents = new ArrayList<>();
        for(CollegeStudent student : students) collegeStudents.add(student);
        assertEquals(5, collegeStudents.size());
    }
    @AfterEach
    public void setupAfterTransaction(){
        jdbcTemplate.execute("DELETE FROM student");
    }
}
