package com.example.springintegrationstudy.controller;

import com.example.springintegrationstudy.adapter.outbound.TableEventProducer;
import com.example.springintegrationstudy.dto.TableEvent;
import com.example.springintegrationstudy.dto.TableEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TableController {

    private final TableEventProducer producer;

    @GetMapping
    public void get() {
        TableEvent tableEvent = new TableEvent();

        tableEvent.setTableId("test-table");
        tableEvent.setEventType(TableEventType.TABLE_CREATED);

        producer.publish(tableEvent);
    }
}
