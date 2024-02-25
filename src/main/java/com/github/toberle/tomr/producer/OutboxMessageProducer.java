package com.github.toberle.tomr.producer;

import com.github.toberle.tomr.model.OutboxMessage;
import com.github.toberle.tomr.model.OutboxMessageResponseMetadata;

public interface OutboxMessageProducer {

    OutboxMessageResponseMetadata send(OutboxMessage outboxMessage);
}
