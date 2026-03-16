package io.github.zjx.springbootgitstuweek2.entity;

import io.github.zjx.springbootgitstuweek2.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zjx
 * @date 2026/3/13
 * @description Student
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    private Long id;
    private String name;
    private String mobile;
    private GenderEnum gender;
    private String avatar;
    private Boolean enabled;
    private LocalDate birthday;
    private LocalDateTime createTime;


}