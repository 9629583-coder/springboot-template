package io.github.zjx.springbootgitstuweek2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zjx
 * @date 2026/3/13
 * @description StudentUpdateDTO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentUpdateDTO {

    private String name;
    private String mobile;
    private String avatar;
}
