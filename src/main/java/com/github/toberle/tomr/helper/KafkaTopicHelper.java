package com.github.toberle.tomr.helper;

import java.util.Optional;

public interface KafkaTopicHelper {

    String determineQueueName(String messageType);
}
