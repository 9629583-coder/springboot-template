package io.github.zjx.springbootgitstuweek3;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.zjx.springbootgitstuweek3.config.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MinioUtil {

    private final MinioProperties properties;

    public Map<String, String> upload(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(400, "uploaded file must not be empty");
        }
        validateProperties();

        String extension = FileUtil.extName(file.getOriginalFilename());
        String objectName = IdUtil.fastSimpleUUID();
        if (StrUtil.isNotBlank(extension)) {
            objectName = objectName + "." + extension;
        }

        MinioClient client = createClient();
        ensureBucketExists(client);
        client.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(StrUtil.blankToDefault(file.getContentType(), "application/octet-stream"))
                        .build()
        );

        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("bucket", properties.getBucket());
        payload.put("originalFilename", file.getOriginalFilename());
        payload.put("objectName", objectName);
        payload.put("url", buildObjectUrl(objectName));
        return payload;
    }

    private MinioClient createClient() {
        return MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

    private void ensureBucketExists(MinioClient client) throws Exception {
        boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(properties.getBucket()).build());
        if (!exists) {
            client.makeBucket(MakeBucketArgs.builder().bucket(properties.getBucket()).build());
        }
    }

    private String buildObjectUrl(String objectName) {
        String baseUrl = StrUtil.isNotBlank(properties.getPublicUrl()) ? properties.getPublicUrl() : properties.getEndpoint();
        return StrUtil.removeSuffix(baseUrl, "/") + "/" + properties.getBucket() + "/" + objectName;
    }

    private void validateProperties() {
        if (!properties.isEnabled()) {
            throw new BusinessException(500, "minio service is disabled");
        }
        if (StrUtil.hasBlank(properties.getEndpoint(), properties.getAccessKey(), properties.getSecretKey(), properties.getBucket())) {
            throw new BusinessException(500, "minio configuration is incomplete");
        }
    }
}
