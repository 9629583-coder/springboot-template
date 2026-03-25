package io.github.zjx.springbootgitstuweek3;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MailSendRequest {

    @Email(message = "mail recipient must be a valid email address")
    @NotBlank(message = "mail recipient must not be blank")
    private String to;

    private String subject;

    @NotBlank(message = "mail content must not be blank")
    private String content;
}
