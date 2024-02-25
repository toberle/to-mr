package com.github.toberle.tomr.reader;

import com.github.toberle.tomr.model.OutboxMessage;

import java.util.List;

public interface OutboxMessageReader {

    List<OutboxMessage> read();
}
