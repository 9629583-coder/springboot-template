package io.github.zjx.springbootgitstuweek3;

import cn.hutool.core.util.StrUtil;
import io.github.zjx.springbootgitstuweek3.config.MailToolProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender mailSender;
    private final MailToolProperties properties;

    public Map<String, String> sendTextMail(String to, String subject, String content) {
        validateProperties(to, content);

        String finalSubject = StrUtil.blankToDefault(subject, properties.getDefaultSubject());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(properties.getFrom());
        message.setTo(to);
        message.setSubject(finalSubject);
        message.setText(content);
        mailSender.send(message);

        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("to", to);
        payload.put("subject", finalSubject);
        payload.put("status", "sent");
        return payload;
    }

    private void validateProperties(String to, String content) {
        if (!properties.isEnabled()) {
            throw new BusinessException(500, "mail service is disabled");
        }
        if (StrUtil.isBlank(properties.getFrom())) {
            throw new BusinessException(500, "mail sender address is missing");
        }
        if (StrUtil.hasBlank(to, content)) {
            throw new BusinessException(400, "mail to and content must not be blank");
        }
    }
}
