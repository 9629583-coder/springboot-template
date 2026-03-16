package io.github.zjx.springbootgitstuweek2.dto;

import io.github.zjx.springbootgitstuweek2.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author zjx
 * @date 2026/3/13
 * @description StudentAddDTO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAddDTO {

    private String name;
    private String avatar;
    private String mobile;
    private GenderEnum gender;
    private LocalDate birthday;
}
