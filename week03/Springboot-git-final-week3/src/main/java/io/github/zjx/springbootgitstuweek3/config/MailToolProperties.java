package io.github.zjx.springbootgitstuweek3.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.mail")
public class MailToolProperties {

    private boolean enabled;
    private String from;
    private String defaultSubject;
}
