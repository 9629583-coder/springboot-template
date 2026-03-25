package io.github.zjx.springbootgitstuweek3.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import io.github.zjx.springbootgitstuweek3.common.Result;
import io.github.zjx.springbootgitstuweek3.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/hutool")
@RequiredArgsConstructor
public class HutoolController {

    private final AppConfig appConfig;

    @GetMapping("/id")
    public Result<Map<String, String>> generateId() {
        return Result.success(Map.of("id", IdUtil.fastSimpleUUID()));
    }

    @GetMapping("/md5")
    public Result<Map<String, String>> md5(@RequestParam String text) {
        return Result.success(Map.of("hash", SecureUtil.md5(text)));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("uploaded file must not be empty");
        }

        String extension = StrUtil.blankToDefault(FileUtil.extName(file.getOriginalFilename()), "bin");
        String storedFilename = IdUtil.fastSimpleUUID() + "." + extension;
        Path uploadDir = Path.of(appConfig.getUploadDir()).toAbsolutePath().normalize();
        FileUtil.mkdir(uploadDir.toFile());

        File targetFile = uploadDir.resolve(storedFilename).toFile();
        FileUtil.writeFromStream(file.getInputStream(), targetFile);

        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("originalFilename", file.getOriginalFilename());
        payload.put("storedFilename", storedFilename);
        payload.put("savedPath", targetFile.getAbsolutePath());
        return Result.success("upload success", payload);
    }
}
