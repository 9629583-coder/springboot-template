package io.github.zjx.springbootgitstuweek3.controller;

import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import io.github.zjx.springbootgitstuweek3.AliyunSmsService;
import io.github.zjx.springbootgitstuweek3.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class AliyunSmsController {

    private final AliyunSmsService smsService;

    @PostMapping("/send-code")
    public Result<Map<String, String>> sendCode(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        if (phone == null || phone.isBlank()) {
            return Result.error(400, "phone must not be blank");
        }

        try {
            SendSmsVerifyCodeResponse response = smsService.sendVerifyCodeAuto(phone);
            if (response.getBody() != null && "OK".equals(response.getBody().getCode())) {
                var model = response.getBody().getModel();
                String bizId = model != null ? model.getBizId() : "";
                String outId = model != null ? model.getOutId() : "";
                return Result.success(Map.of(
                        "message", "verify code sent",
                        "bizId", bizId == null ? "" : bizId,
                        "outId", outId == null ? "" : outId
                ));
            }
            return Result.error(500, response.getBody() != null ? response.getBody().getMessage() : "send failed");
        } catch (Exception exception) {
            return Result.error(500, "send failed: " + exception.getMessage());
        }
    }

    @PostMapping("/verify")
    public Result<Boolean> verify(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        String code = body.get("code");
        if (phone == null || phone.isBlank() || code == null || code.isBlank()) {
            return Result.error(400, "phone and code must not be blank");
        }

        try {
            boolean pass = smsService.checkVerifyCode(phone, code, body.get("outId"));
            return Result.success(pass);
        } catch (Exception exception) {
            return Result.error(500, "verify failed: " + exception.getMessage());
        }
    }
}
