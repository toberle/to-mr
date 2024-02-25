package com.github.toberle.tomr.processor;

import com.github.toberle.tomr.producer.OutboxMessageProducer;
import com.github.toberle.tomr.reader.OutboxMessageReader;
import com.github.toberle.tomr.writer.OutboxMessageMetadataWriter;
import com.github.toberle.tomr.model.OutboxMessage;
import com.github.toberle.tomr.model.OutboxMessageResponseMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OutboxMessageProcessor {

    private static final Logger log = LoggerFactory.getLogger(OutboxMessageProcessor.class);

    private final OutboxMessageReader outboxMessageReader;
    private final OutboxMessageMetadataWriter outboxMessageMetadataWriter;
    private final OutboxMessageProducer outboxMessageProducer;

    public OutboxMessageProcessor(OutboxMessageReader outboxMessageReader,
                                  OutboxMessageMetadataWriter outboxMessageMetadataWriter,
                                  OutboxMessageProducer outboxMessageProducer) {
        this.outboxMessageReader = outboxMessageReader;
        this.outboxMessageMetadataWriter = outboxMessageMetadataWriter;
        this.outboxMessageProducer = outboxMessageProducer;
    }

    @Scheduled(fixedDelayString = "${outbox.query.delay-ms}")
    public void processOutboxMessages() {
        MDC.put("processing-id", UUID.randomUUID().toString());
        log.info("Processing of outbox messages started");
        List<OutboxMessage> messages = outboxMessageReader.read();
        log.info("Processing outbox messages: {}", messages.size());
        List<OutboxMessageResponseMetadata> resultMetadata = sendMessages(messages);
        log.info("Processing outbox messages result: {}", messages.size());
        writeResponseToOutbox(resultMetadata);
        log.info("Processing of outbox messages finished");
    }

    private void writeResponseToOutbox(List<OutboxMessageResponseMetadata> resultMetadata) {
        try {
            outboxMessageMetadataWriter.writeMetadata(resultMetadata);
        } catch (Exception e) {
            log.error("Error while writing response metadata to outbox", e);
        }
    }

    private List<OutboxMessageResponseMetadata> sendMessages(List<OutboxMessage> messages) {
        List<OutboxMessageResponseMetadata> resultMetadata = new ArrayList<>();
        for (OutboxMessage message : messages) {
            try {
                resultMetadata.add(outboxMessageProducer.send(message));
            } catch (Exception e) {
                log.error("Error while sending message with ID: {}", message.getId(), e);
            }
        }
        return resultMetadata;
    }
}
