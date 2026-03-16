package io.github.zjx.springbootgitstuweek2.controller;

import io.github.zjx.springbootgitstuweek2.VO.StudentVO;
import io.github.zjx.springbootgitstuweek2.dto.StudentAddDTO;
import io.github.zjx.springbootgitstuweek2.dto.StudentUpdateDTO;
import io.github.zjx.springbootgitstuweek2.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author zjx
 * @date 2026/3/13
 * @description StudentController
 **/
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public List<StudentVO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/add")
    public StudentVO addStudent(@RequestBody StudentAddDTO dto) {
        return studentService.addStudent(dto);
    }

    @GetMapping("/get/{id}")
    public StudentVO getStudent(@PathVariable Long id) {
        StudentVO studentVO = studentService.getStudent(id);
        if (studentVO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found");
        }
        return studentVO;
    }

    @GetMapping("/search")
    public List<StudentVO> getStudentByName(@RequestParam String name) {
        return studentService.getStudentByName(name);
    }

    @PutMapping("/update/{id}")
    public StudentVO updateStudent(@PathVariable Long id, @RequestBody StudentUpdateDTO dto) {
        StudentVO studentVO = studentService.updateStudent(id, dto);
        if (studentVO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found");
        }
        return studentVO;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
