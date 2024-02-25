package com.github.toberle.tomr.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "outbox")
public class OutboxProperties {

    @NotNull
    private Query query;
    @NotNull
    private Queue queue;
    @NotNull
    private Broker broker;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public static class Query {
        private int delayMs;
        private int limit;
        private String tableName;

        public int getDelayMs() {
            return delayMs;
        }

        public void setDelayMs(int delayMs) {
            this.delayMs = delayMs;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
    }

    public static class Queue {
        @NotNull
        @NotBlank
        private String defaultQueue;
        private Map<String, String> typeRouting;

        public String getDefaultQueue() {
            return defaultQueue;
        }

        public void setDefaultQueue(String defaultQueue) {
            this.defaultQueue = defaultQueue;
        }

        public Map<String, String> getTypeRouting() {
            return typeRouting;
        }

        public void setTypeRouting(Map<String, String> typeRouting) {
            this.typeRouting = typeRouting;
        }
    }

    public enum Broker {
        KAFKA, RABBIT_MQ
    }
}
