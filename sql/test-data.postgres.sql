INSERT INTO outbox (created_ts, message_key, message_type, message_data)
    VALUES (CURRENT_TIMESTAMP, 'TEST1', 'TESTING', 'test-data'::bytea);


