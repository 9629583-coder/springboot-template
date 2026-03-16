package io.github.zjx.springbootgitstuweek2.VO;

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
 * @description StudentVO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentVO {

    private Long id;
    private String name;
    private String avatar;
    private String mobile;
    private GenderEnum gender;
    private LocalDate birthday;
    private LocalDateTime createTime;
}