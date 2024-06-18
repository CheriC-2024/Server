package com.art.cheric.dto.exhibition.ai.respond;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatGptResponseDTO {

    @JsonProperty("result")
    private List<String> result;

    @JsonProperty("reason")
    private List<String> reason;

    public ChatGptResponseDTO() {
    }

    public ChatGptResponseDTO(List<String> result, List<String> reason) {
        this.result = result;
        this.reason = reason;
    }
}
