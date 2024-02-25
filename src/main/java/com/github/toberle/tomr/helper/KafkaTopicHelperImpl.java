package com.github.toberle.tomr.helper;

import com.github.toberle.tomr.properties.OutboxProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class KafkaTopicHelperImpl implements KafkaTopicHelper {

    private final OutboxProperties outboxProperties;

    public KafkaTopicHelperImpl(OutboxProperties outboxProperties) {
        this.outboxProperties = outboxProperties;
    }

    @Override
    public String determineQueueName(String messageType) {
        if (messageType == null || messageType.isBlank() || outboxProperties.getQueue().getTypeRouting() == null) {
            return outboxProperties.getQueue().getDefaultQueue();
        }
        String topicName = outboxProperties.getQueue()
                .getTypeRouting()
                .get(messageType.toLowerCase(Locale.ROOT));
        return topicName != null && !topicName.isBlank() ? topicName : outboxProperties.getQueue().getDefaultQueue();
    }
}
