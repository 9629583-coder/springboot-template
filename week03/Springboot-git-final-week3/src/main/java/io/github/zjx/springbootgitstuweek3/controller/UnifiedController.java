package io.github.zjx.springbootgitstuweek3.controller;

import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import io.github.zjx.springbootgitstuweek3.AiChatRequest;
import io.github.zjx.springbootgitstuweek3.AliyunSmsService;
import io.github.zjx.springbootgitstuweek3.DeepSeekService;
import io.github.zjx.springbootgitstuweek3.MailSendRequest;
import io.github.zjx.springbootgitstuweek3.MailUtil;
import io.github.zjx.springbootgitstuweek3.MinioUtil;
import io.github.zjx.springbootgitstuweek3.common.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/unified")
@RequiredArgsConstructor
public class UnifiedController {

    private final AliyunSmsService aliyunSmsService;
    private final MinioUtil minioUtil;
    private final MailUtil mailUtil;
    private final DeepSeekService deepSeekService;

    @PostMapping("/sms/send-code")
    public Result<Map<String, String>> sendSms(@RequestBody Map<String, String> body) throws Exception {
        String phone = body.get("phone");
        SendSmsVerifyCodeResponse response = aliyunSmsService.sendVerifyCodeAuto(phone);
        if (response.getBody() != null && "OK".equals(response.getBody().getCode())) {
            var model = response.getBody().getModel();
            return Result.success(Map.of(
                    "message", "verify code sent",
                    "bizId", model != null && model.getBizId() != null ? model.getBizId() : "",
                    "outId", model != null && model.getOutId() != null ? model.getOutId() : ""
            ));
        }
        return Result.error(500, response.getBody() != null ? response.getBody().getMessage() : "send failed");
    }

    @PostMapping(value = "/minio/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Map<String, String>> uploadToMinio(@RequestParam("file") MultipartFile file) throws Exception {
        return Result.success("upload success", minioUtil.upload(file));
    }

    @PostMapping("/mail/send")
    public Result<Map<String, String>> sendMail(@Valid @RequestBody MailSendRequest request) {
        return Result.success("mail sent", mailUtil.sendTextMail(request.getTo(), request.getSubject(), request.getContent()));
    }

    @PostMapping("/ai/chat")
    public Result<Map<String, Object>> chat(@Valid @RequestBody AiChatRequest request) {
        return Result.success("chat success", deepSeekService.chat(request.getQuestion()));
    }
}
