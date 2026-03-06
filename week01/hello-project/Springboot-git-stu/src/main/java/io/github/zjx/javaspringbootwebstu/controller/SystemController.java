package io.github.zjx.javaspringbootwebstu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SystemController {

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> result = new HashMap<>();
        result.put("status", "UP");
        result.put("message", "application is running");
        return result;
    }

    @GetMapping("/system/info")
    public Map<String, String> systemInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("osName", System.getProperty("os.name"));
        result.put("osVersion", System.getProperty("os.version"));
        result.put("javaVersion", System.getProperty("java.version"));
        result.put("userName", System.getProperty("user.name"));
        return result;
    }
}