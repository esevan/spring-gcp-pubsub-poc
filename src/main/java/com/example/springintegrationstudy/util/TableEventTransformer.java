package com.example.springintegrationstudy.util;

import com.example.springintegrationstudy.dto.TableEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TableEventTransformer {
    private final ObjectMapper objectMapper;

    public byte[] transform(TableEvent event) {
        try {
            return objectMapper.writeValueAsBytes(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("exception");
        }
    }

    public TableEvent transform(byte[] eventData) {
        try {
            return objectMapper.readValue(eventData, TableEvent.class);
        } catch (IOException e) {
            throw new RuntimeException("cannot deserialize");
        }
    }
}
