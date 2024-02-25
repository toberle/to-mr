package com.github.toberle.tomr.writer;

import com.github.toberle.tomr.model.OutboxMessageResponseMetadata;

import java.util.List;

public interface OutboxMessageMetadataWriter {

    void writeMetadata(List<OutboxMessageResponseMetadata> resultMetadata);
}
