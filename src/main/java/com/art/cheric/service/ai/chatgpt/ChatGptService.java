package com.art.cheric.service.ai.chatgpt;

import com.art.cheric.dto.exhibition.ai.respond.ChatGptResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ChatGptService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ChatGptService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public ChatGptResponseDTO getChatGPTResponse(String prompt) {
        // 요청 본문 설정
        Map<String, Object> requestBodyMap = Map.of(
                "model", "gpt-3.5-turbo-0125",
                "messages", Collections.singletonList(Map.of("role", "user", "content", prompt)),
                "max_tokens", 500
        );

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request body to JSON: " + e.getMessage());
        }

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // API 호출
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            // API 응답을 JSON 형식으로 변환
            JsonNode jsonResponse = objectMapper.readTree(responseEntity.getBody());
            System.out.println("jsonResponse: " + jsonResponse.toString());

            // 'choices' 배열 확인
            JsonNode choicesNode = jsonResponse.get("choices");
            if (choicesNode == null || !choicesNode.isArray() || choicesNode.isEmpty()) {
                throw new RuntimeException("No valid choices found in API response");
            }

            // 첫 번째 choice에서 'message' 객체 찾기
            JsonNode messageNode = choicesNode.get(0).get("message");
            System.out.println("messageNode: " + messageNode);
            if (messageNode == null) {
                throw new RuntimeException("No 'message' object found in the first choice");
            }

            // 'content' 객체에서 'result'와 'reason' 추출
            String content = messageNode.get("content").asText();
            JsonNode contentNode = objectMapper.readTree(content);

            List<String> result = new ArrayList<>();
            List<String> reason = new ArrayList<>();

            JsonNode resultNode = contentNode.get("result");
            if (resultNode != null && resultNode.isArray()) {
                for (JsonNode node : resultNode) {
                    result.add(node.asText());
                }
            }

            JsonNode reasonNode = contentNode.get("reason");
            if (reasonNode != null && reasonNode.isArray()) {
                for (JsonNode node : reasonNode) {
                    reason.add(node.asText());
                }
            }

            return new ChatGptResponseDTO(result, reason);

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("HTTP Error: " + e.getMessage(), e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse API response: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute API request: " + e.getMessage(), e);
        }
    }
}
