package io.github.zjx.springbootgitstuweek3;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AiChatRequest {

    @NotBlank(message = "question must not be blank")
    private String question;
}
