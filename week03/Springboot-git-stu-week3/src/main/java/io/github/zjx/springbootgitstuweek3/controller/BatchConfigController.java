package io.github.zjx.springbootgitstuweek3.controller;

import io.github.zjx.springbootgitstuweek3.common.Result;
import io.github.zjx.springbootgitstuweek3.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BatchConfigController锛岄€氳繃 @ConfigurationProperties 璇诲彇閰嶇疆淇℃伅
 */
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class BatchConfigController {

    private final AppConfig appConfig;

    @GetMapping("/batch")
    public Result<AppConfig> getBatchConfigInfo() {
        return Result.success(appConfig);
    }
}
