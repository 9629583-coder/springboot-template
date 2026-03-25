package io.github.zjx.springbootgitstuweek3.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String appName;
    private String version;
    private String description;
    private Boolean published;
    private Author author;
    private List<String> features;

    @Data
    public static class Author {
        private String name;
        private String email;
    }
}
