package com.github.toberle.tomr.producer;

import com.github.toberle.tomr.helper.KafkaTopicHelper;
import com.github.toberle.tomr.exception.OutboxMessageSendException;
import com.github.toberle.tomr.model.OutboxMessage;
import com.github.toberle.tomr.model.OutboxMessageResponseMetadata;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaOutboxMessageProducer implements OutboxMessageProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final KafkaTopicHelper kafkaTopicHelper;

    public KafkaOutboxMessageProducer(KafkaTemplate<String, byte[]> kafkaTemplate,
                                      KafkaTopicHelper kafkaTopicHelper) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicHelper = kafkaTopicHelper;
    }

    @Override
    public OutboxMessageResponseMetadata send(OutboxMessage outboxMessage) {
        try {
            String topicName = kafkaTopicHelper.determineQueueName(outboxMessage.getType());

            var message = new ProducerRecord<>(topicName, outboxMessage.getKey(), outboxMessage.getData());
            message.headers().add("message_id", outboxMessage.getId().getBytes(StandardCharsets.UTF_8));
            message.headers().add("message_type", outboxMessage.getType().getBytes(StandardCharsets.UTF_8));
            message.headers().add("message_ts", outboxMessage.getCreatedTimestamp().toString()
                    .getBytes(StandardCharsets.UTF_8));

            SendResult<String, byte[]> result = kafkaTemplate.send(message).get();
            Instant timestamp = extractTimestamp(result);
            String metadata = extractMetadata(result);
            return new OutboxMessageResponseMetadata(outboxMessage.getId(), timestamp, metadata);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new OutboxMessageSendException("Execution interrupted exception", e);
        } catch (ExecutionException e) {
            throw new OutboxMessageSendException(e);
        }
    }

    private String extractMetadata(SendResult<String, byte[]> result) {
        return MessageFormat.format("topic: {0}, partition: {1}, offset: {2}",
                result.getRecordMetadata().topic(), result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset());
    }

    private Instant extractTimestamp(SendResult<String, byte[]> stringSendResult) {
        if (stringSendResult.getRecordMetadata().hasTimestamp()) {
           return Instant.ofEpochMilli(stringSendResult.getRecordMetadata().timestamp());
        } else {
            return Instant.now();
        }
    }
}
