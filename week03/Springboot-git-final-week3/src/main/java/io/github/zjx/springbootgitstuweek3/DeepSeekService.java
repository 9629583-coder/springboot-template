package io.github.zjx.springbootgitstuweek3;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.zjx.springbootgitstuweek3.config.DeepSeekProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeepSeekService {

    private final DeepSeekProperties properties;

    public Map<String, Object> chat(String question) {
        validateProperties(question);

        JSONObject payload = new JSONObject();
        payload.set("model", properties.getModel());
        payload.set("stream", false);
        payload.set("messages", List.of(
                Map.of("role", "system", "content", properties.getSystemPrompt()),
                Map.of("role", "user", "content", question)
        ));

        String responseText = HttpUtil.createPost(properties.getApiUrl())
                .header("Authorization", "Bearer " + properties.getApiKey())
                .header("Content-Type", ContentType.JSON.getValue())
                .body(payload.toString())
                .execute()
                .body();

        JSONObject response = JSONUtil.parseObj(responseText);
        JSONArray choices = response.getJSONArray("choices");
        String answer = "";
        if (choices != null && !choices.isEmpty()) {
            JSONObject firstChoice = choices.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");
            if (message != null) {
                answer = message.getStr("content", "");
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("model", properties.getModel());
        result.put("question", question);
        result.put("answer", answer);
        // Return plain text instead of Hutool JSON types to avoid Jackson serialization issues.
        result.put("rawResponse", responseText);
        return result;
    }

    private void validateProperties(String question) {
        if (!properties.isEnabled()) {
            throw new BusinessException(500, "deepseek service is disabled");
        }
        if (StrUtil.hasBlank(properties.getApiUrl(), properties.getApiKey(), properties.getModel())) {
            throw new BusinessException(500, "deepseek configuration is incomplete");
        }
        if (StrUtil.isBlank(question)) {
            throw new BusinessException(400, "question must not be blank");
        }
    }
}
