package com.example.springintegrationstudy.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class TableEvent {

    private String tableId;
    private TableEventType eventType;
    private JsonNode context;
}
