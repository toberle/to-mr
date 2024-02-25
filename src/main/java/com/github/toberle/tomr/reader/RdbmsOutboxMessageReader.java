package com.github.toberle.tomr.reader;

import com.github.toberle.tomr.model.OutboxMessage;
import com.github.toberle.tomr.properties.OutboxProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RdbmsOutboxMessageReader implements OutboxMessageReader {

    private final JdbcTemplate jdbcTemplate;
    private final OutboxProperties outboxProperties;

    public RdbmsOutboxMessageReader(JdbcTemplate jdbcTemplate, OutboxProperties outboxProperties) {
        this.jdbcTemplate = jdbcTemplate;
        this.outboxProperties = outboxProperties;
    }

    @Override
    public List<OutboxMessage> read() {
        return jdbcTemplate.query("SELECT * FROM outbox WHERE sent_ts IS NULL ORDER BY created_ts LIMIT ?",
                preparedStatement -> preparedStatement.setInt(1, outboxProperties.getQuery().getLimit()),
                (resultSet, rowNum) -> new OutboxMessage(
                        resultSet.getString("id"),
                        resultSet.getTimestamp("created_ts").toInstant(),
                        resultSet.getString("message_key"),
                        resultSet.getString("message_type"),
                        resultSet.getBytes("message_data")
                ));
    }
}
