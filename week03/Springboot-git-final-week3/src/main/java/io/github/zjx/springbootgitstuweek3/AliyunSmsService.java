package io.github.zjx.springbootgitstuweek3;

import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.CheckSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import com.aliyun.teaopenapi.models.Config;
import io.github.zjx.springbootgitstuweek3.config.AliyunSmsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AliyunSmsService {

    private final AliyunSmsProperties properties;

    private Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(properties.getAccessKeyId())
                .setAccessKeySecret(properties.getAccessKeySecret());
        config.endpoint = properties.getEndpoint();
        config.regionId = properties.getRegionId();
        return new Client(config);
    }

    public SendSmsVerifyCodeResponse sendVerifyCodeAuto(String phone) throws Exception {
        String templateParam = "{\"code\":\"##code##\",\"min\":\"5\"}";
        return sendVerifyCode(phone, templateParam, true);
    }

    public SendSmsVerifyCodeResponse sendVerifyCode(String phone, String templateParam, boolean returnVerifyCode) throws Exception {
        validateProperties();

        SendSmsVerifyCodeRequest request = new SendSmsVerifyCodeRequest()
                .setPhoneNumber(phone)
                .setSignName(properties.getSignName())
                .setTemplateCode(properties.getTemplateCode())
                .setTemplateParam(templateParam)
                .setCodeLength(6L)
                .setValidTime(300L)
                .setDuplicatePolicy(1L)
                .setInterval(60L)
                .setCodeType(1L)
                .setReturnVerifyCode(returnVerifyCode);

        if (properties.getSchemeName() != null && !properties.getSchemeName().isBlank()) {
            request.setSchemeName(properties.getSchemeName());
        }

        Client client = createClient();
        SendSmsVerifyCodeResponse response = client.sendSmsVerifyCode(request);

        if (response.getBody() != null) {
            String businessCode = response.getBody().getCode();
            if ("OK".equals(businessCode)) {
                String bizId = response.getBody().getModel() == null ? "" : response.getBody().getModel().getBizId();
                log.info("SMS verify code sent successfully, phone={}, bizId={}", maskPhone(phone), bizId);
            } else {
                log.warn("SMS verify code sending failed, phone={}, code={}, message={}",
                        maskPhone(phone),
                        businessCode,
                        response.getBody().getMessage());
            }
        }
        return response;
    }

    public boolean checkVerifyCode(String phone, String verifyCode, String outId) throws Exception {
        if (!properties.isEnabled()) {
            return false;
        }

        CheckSmsVerifyCodeRequest request = new CheckSmsVerifyCodeRequest()
                .setPhoneNumber(phone)
                .setVerifyCode(verifyCode);

        if (outId != null && !outId.isBlank()) {
            request.setOutId(outId);
        }
        if (properties.getSchemeName() != null && !properties.getSchemeName().isBlank()) {
            request.setSchemeName(properties.getSchemeName());
        }

        Client client = createClient();
        var response = client.checkSmsVerifyCode(request);
        if (response.getBody() != null && response.getBody().getModel() != null) {
            return "PASS".equals(response.getBody().getModel().getVerifyResult());
        }
        return false;
    }

    private void validateProperties() {
        if (!properties.isEnabled()) {
            throw new BusinessException(500, "sms service is disabled");
        }
        if (properties.getAccessKeyId() == null || properties.getAccessKeyId().isBlank()) {
            throw new BusinessException(500, "missing ALIYUN_SMS_ACCESS_KEY_ID");
        }
        if (properties.getAccessKeySecret() == null || properties.getAccessKeySecret().isBlank()) {
            throw new BusinessException(500, "missing ALIYUN_SMS_ACCESS_KEY_SECRET");
        }
        if (properties.getSignName() == null || properties.getSignName().isBlank()) {
            throw new BusinessException(500, "missing aliyun.sms.sign-name");
        }
        if (properties.getTemplateCode() == null || properties.getTemplateCode().isBlank()) {
            throw new BusinessException(500, "missing aliyun.sms.template-code");
        }
    }

    private String maskPhone(String phone) {
        if (phone == null || phone.length() < 7) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }
}
