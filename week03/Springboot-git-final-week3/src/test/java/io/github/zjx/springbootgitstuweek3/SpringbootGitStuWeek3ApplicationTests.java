package io.github.zjx.springbootgitstuweek3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SpringbootGitStuWeek3ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldReturnAppInfo() throws Exception {
        mockMvc.perform(get("/config/app-info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.serverPort").value("8082"))
                .andExpect(jsonPath("$.data.app.name").value("enterprise-admin-platform-test"))
                .andExpect(jsonPath("$.data.activeProfiles", hasSize(1)))
                .andExpect(jsonPath("$.data.activeProfiles[0]").value("test"));
    }

    @Test
    void shouldReturnDatabaseInfo() throws Exception {
        mockMvc.perform(get("/config/db-info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.host").value("test.localhost"))
                .andExpect(jsonPath("$.data.port").value(3307));
    }

    @Test
    void shouldGenerateMd5ViaHutool() throws Exception {
        mockMvc.perform(get("/hutool/md5").param("text", "123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.hash").value("e10adc3949ba59abbe56e057f20f883e"));
    }

    @Test
    void shouldGenerateUuidViaHutool() throws Exception {
        mockMvc.perform(get("/hutool/id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", matchesPattern("^[0-9a-f]{32}$")));
    }

    @Test
    void shouldUploadFileViaHutool() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "hello homework".getBytes()
        );

        mockMvc.perform(multipart("/hutool/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.originalFilename").value("hello.txt"))
                .andExpect(jsonPath("$.data.storedFilename", matchesPattern("^[0-9a-f]{32}\\.txt$")));
    }

    @AfterEach
    void cleanUploads() throws Exception {
        Path uploadDir = Path.of("target/test-uploads");
        if (!Files.exists(uploadDir)) {
            return;
        }

        try (Stream<Path> stream = Files.walk(uploadDir)) {
            stream.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (Exception ignored) {
                        }
                    });
        }
    }
}
