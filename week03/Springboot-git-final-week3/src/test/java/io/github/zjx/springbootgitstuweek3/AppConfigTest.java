package io.github.zjx.springbootgitstuweek3;

import io.github.zjx.springbootgitstuweek3.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class AppConfigTest {

    private final AppConfig appConfig;

    @Autowired
    AppConfigTest(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Test
    void shouldBindAppConfigFromTestProfile() {
        assertThat(appConfig.getName()).isEqualTo("enterprise-admin-platform-test");
        assertThat(appConfig.getVersion()).isEqualTo("1.0.2");
        assertThat(appConfig.getDatabase().getHost()).isEqualTo("test.localhost");
        assertThat(appConfig.getUploadDir()).isEqualTo("target/test-uploads");
    }
}
