package io.github.zjx.springbootgitstuweek2.service;

import io.github.zjx.springbootgitstuweek2.VO.StudentVO;
import io.github.zjx.springbootgitstuweek2.constant.GenderEnum;
import io.github.zjx.springbootgitstuweek2.dto.StudentAddDTO;
import io.github.zjx.springbootgitstuweek2.dto.StudentUpdateDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Slf4j
class StudentServiceTest {

    @Resource
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService.resetStudentData();
    }

    @Test
    void getAllStudents() {
        List<StudentVO> allStudents = studentService.getAllStudents();
        allStudents.forEach(studentVO -> log.info("{}", studentVO));
    }

    @Test
    void addStudent() {
        studentService.addStudent(StudentAddDTO.builder()
                .name("mqxu")
                .mobile("12345678901")
                .gender(GenderEnum.MALE)
                .avatar("https://mqxu.top/avatar.jpg")
                .birthday(LocalDate.of(1999, 1, 1))
                .build());

        log.info("添加成功");
        List<StudentVO> allStudents = studentService.getAllStudents();
        allStudents.forEach(studentVO -> log.info("{}", studentVO));
    }

    @Test
    void getStudent() {
        StudentVO studentVO = studentService.getStudent(1001L);
        log.info("{}", studentVO);
    }

    @Test
    void getStudentByName() {
        List<StudentVO> studentVOList = studentService.getStudentByName("张");
        studentVOList.forEach(studentVO -> log.info("{}", studentVO));
    }

    @Test
    void updateStudent() {
        studentService.updateStudent(1001L, StudentUpdateDTO.builder()
                .name("张三111")
                .mobile("12345678901")
                .avatar("https://mqxu.top/new.jpg")
                .build());

        log.info("修改成功");
        StudentVO studentVO = studentService.getStudent(1001L);
        log.info("{}", studentVO);
    }

    @Test
    void deleteStudent() {
        studentService.deleteStudent(1001L);
        log.info("删除成功");
        List<StudentVO> allStudents = studentService.getAllStudents();
        allStudents.forEach(studentVO -> log.info("{}", studentVO));
    }
}