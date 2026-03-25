package io.github.zjx.springbootgitstuweek3.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    @NotBlank(message = "app.name must not be blank")
    private String name;

    private String version;
    private String description;
    private Boolean published;
    private String uploadDir;

    @Valid
    private Author author = new Author();

    @Valid
    private Database database = new Database();

    private List<String> features = new ArrayList<>();

    @Data
    public static class Author {
        private String name;
        private String email;
    }

    @Data
    public static class Database {
        private String host;
        private Integer port;
        private String username;
        private String password;
    }
}
