package io.github.zjx.springbootgitstuweek3.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AppConfigTest {

    private final AppConfig appConfig;

    @Autowired
    public AppConfigTest(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Test
    public void getAppConfigInfo() {
        log.info("名称：{}", appConfig.getAppName());
        log.info("版本：{}", appConfig.getVersion());
        log.info("描述：{}", appConfig.getDescription());
        log.info("是否发布：{}", appConfig.getPublished());
        log.info("作者：{}", appConfig.getAuthor());
        log.info("特性：{}", appConfig.getFeatures());
    }
}