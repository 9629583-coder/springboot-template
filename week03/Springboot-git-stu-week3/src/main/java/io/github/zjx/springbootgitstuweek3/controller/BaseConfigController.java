package io.github.zjx.springbootgitstuweek3.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.zjx.springbootgitstuweek3.common.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseConfigController，使用 @Value 读取配置项
 */
@RestController
@RequestMapping("/config")
public class BaseConfigController {

    @Value("${server.port}")
    private Integer port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${app.app-name}")
    private String appName;

    @Value("${app.version}")
    private String version;

    @Value("${app.description}")
    private String description;

    @Value("${app.published}")
    private Boolean published;

    @GetMapping("/base")
    public Result<Map<String, Object>> getBaseConfigInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("port", port);
        map.put("contextPath", contextPath);
        map.put("applicationName", applicationName);
        map.put("appName", appName);
        map.put("version", version);
        map.put("description", description);
        map.put("published", published);

        return Result.success(map);
    }
}