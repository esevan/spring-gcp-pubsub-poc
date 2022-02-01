package com.example.springintegrationstudy.adapter.outbound;

import com.example.springintegrationstudy.dto.TableEvent;
import com.example.springintegrationstudy.util.TableEventTransformer;
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
@Log4j2
public class TableEventProducer {

    private final TableEventTransformer transformer;
    @Qualifier("tablePublisher")
    private final Publisher publisher;

    @Value("${messaging.enabled:true}")
    private Boolean enabled;

    @Async("publishExecutor")
    @Retryable(value = {RuntimeException.class}, backoff = @Backoff(delay = 500L))
    public void publish(TableEvent tableEvent) {
        if (enabled) {
            publishSync(tableEvent);
        } else {
            log.info("messaging is disabled, table event is not published: {}", tableEvent);
        }
    }

    private void publishSync(TableEvent tableEvent) {
        System.out.println("publishing...");
        byte[] data = transformer.transform(tableEvent);

        ApiFuture<String> publishFuture = publisher.publish(PubsubMessage.newBuilder()
                .setOrderingKey(tableEvent.getTableId())
                .setData(ByteString.copyFrom(data))
                .build());

        try {
            publishFuture.get(100L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            throw new RuntimeException("service unavailable");
        }
    }
}
