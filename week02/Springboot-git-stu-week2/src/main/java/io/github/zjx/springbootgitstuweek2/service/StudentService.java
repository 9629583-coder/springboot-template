package io.github.zjx.springbootgitstuweek2.service;

import io.github.zjx.springbootgitstuweek2.VO.StudentVO;
import io.github.zjx.springbootgitstuweek2.constant.GenderEnum;
import io.github.zjx.springbootgitstuweek2.dto.StudentAddDTO;
import io.github.zjx.springbootgitstuweek2.dto.StudentUpdateDTO;
import io.github.zjx.springbootgitstuweek2.entity.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zjx
 * @date 2026/3/13
 * @description StudentService
 **/
@Service
public class StudentService {

    private static final ConcurrentHashMap<Long, Student> STUDENT_DATA = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    static {
        initializeStudentData();
    }

    private static void initializeStudentData() {
        STUDENT_DATA.clear();

        Student student1 = Student.builder()
                .id(1001L)
                .name("张三")
                .mobile("13888888888")
                .gender(GenderEnum.MALE)
                .avatar("https://mqxu.top/1.png")
                .enabled(true)
                .birthday(LocalDate.of(1999, 1, 1))
                .createTime(LocalDateTime.now())
                .build();

        Student student2 = Student.builder()
                .id(1002L)
                .name("张小红")
                .mobile("13988888888")
                .gender(GenderEnum.FEMALE)
                .avatar("https://mqxu.top/2.png")
                .enabled(true)
                .birthday(LocalDate.of(2000, 1, 1))
                .createTime(LocalDateTime.now())
                .build();

        STUDENT_DATA.put(student1.getId(), student1);
        STUDENT_DATA.put(student2.getId(), student2);
        ID_GENERATOR.set(1002L);
    }

    public List<StudentVO> getAllStudents() {
        return STUDENT_DATA.values()
                .stream()
                .sorted(Comparator.comparing(Student::getId))
                .map(this::toStudentVO)
                .toList();
    }

    public StudentVO getStudent(Long id) {
        Student student = STUDENT_DATA.get(id);
        if (student == null) {
            return null;
        }
        return toStudentVO(student);
    }

    public List<StudentVO> getStudentByName(String name) {
        if (name == null || name.isBlank()) {
            return getAllStudents();
        }

        return STUDENT_DATA.values()
                .stream()
                .filter(student -> student.getName() != null && student.getName().contains(name))
                .sorted(Comparator.comparing(Student::getId))
                .map(this::toStudentVO)
                .toList();
    }

    public StudentVO addStudent(StudentAddDTO dto) {
        long id = ID_GENERATOR.incrementAndGet();
        Student student = Student.builder()
                .id(id)
                .name(dto.getName())
                .avatar(dto.getAvatar())
                .mobile(dto.getMobile())
                .gender(dto.getGender())
                .enabled(true)
                .birthday(dto.getBirthday())
                .createTime(LocalDateTime.now())
                .build();
        STUDENT_DATA.put(id, student);
        return toStudentVO(student);
    }

    public StudentVO updateStudent(Long id, StudentUpdateDTO dto) {
        Student updatedStudent = STUDENT_DATA.computeIfPresent(id, (key, student) -> {
            if (dto.getName() != null) {
                student.setName(dto.getName());
            }
            if (dto.getMobile() != null) {
                student.setMobile(dto.getMobile());
            }
            if (dto.getAvatar() != null) {
                student.setAvatar(dto.getAvatar());
            }
            return student;
        });

        if (updatedStudent == null) {
            return null;
        }

        return toStudentVO(updatedStudent);
    }

    public void deleteStudent(Long id) {
        STUDENT_DATA.remove(id);
    }

    void resetStudentData() {
        initializeStudentData();
    }

    private StudentVO toStudentVO(Student student) {
        return StudentVO.builder()
                .id(student.getId())
                .name(student.getName())
                .avatar(student.getAvatar())
                .mobile(student.getMobile())
                .gender(student.getGender())
                .birthday(student.getBirthday())
                .createTime(student.getCreateTime())
                .build();
    }
}
