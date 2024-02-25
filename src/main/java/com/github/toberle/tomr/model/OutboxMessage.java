package com.github.toberle.tomr.model;

import java.time.Instant;

public class OutboxMessage {

    private String id;
    private Instant createdTimestamp;
    private String key;
    private String type;
    private byte[] data;

    public OutboxMessage(String id, Instant createdTimestamp, String key, String type, byte[] data) {
        this.id = id;
        this.createdTimestamp = createdTimestamp;
        this.key = key;
        this.type = type;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public byte[] getData() {
        return data;
    }
}
