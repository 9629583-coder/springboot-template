package io.github.zjx.springbootgitstuweek3.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSmsProperties {

    private boolean enabled = true;
    private String endpoint = "dypnsapi.aliyuncs.com";
    private String regionId = "cn-hangzhou";
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String templateCode;
    private String schemeName = "default-scheme";
}
