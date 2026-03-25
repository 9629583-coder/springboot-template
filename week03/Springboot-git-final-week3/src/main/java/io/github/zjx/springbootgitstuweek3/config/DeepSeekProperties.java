package io.github.zjx.springbootgitstuweek3.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "deepseek")
public class DeepSeekProperties {

    private boolean enabled;
    private String apiUrl;
    private String apiKey;
    private String model;
    private String systemPrompt;
}
