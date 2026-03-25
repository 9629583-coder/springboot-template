package io.github.zjx.springbootgitstuweek3.controller;

import io.github.zjx.springbootgitstuweek3.common.Result;
import io.github.zjx.springbootgitstuweek3.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigController {

    private final AppConfig appConfig;
    private final Environment environment;

    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/app-info")
    public Result<Map<String, Object>> getAppInfo() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("serverPort", serverPort);
        payload.put("applicationName", applicationName);
        payload.put("activeProfiles", environment.getActiveProfiles());
        payload.put("app", appConfig);
        return Result.success(payload);
    }

    @GetMapping("/db-info")
    public Result<Map<String, Object>> getDatabaseInfo() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("activeProfiles", environment.getActiveProfiles());
        payload.put("host", appConfig.getDatabase().getHost());
        payload.put("port", appConfig.getDatabase().getPort());
        payload.put("username", appConfig.getDatabase().getUsername());
        return Result.success(payload);
    }
}
