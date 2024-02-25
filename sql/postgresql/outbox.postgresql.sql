CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS outbox (
    id              uuid DEFAULT uuid_generate_v4 (),
    created_ts      TIMESTAMP NOT NULL,
    message_key     VARCHAR(200),
    message_type    VARCHAR(200) NOT NULL,
    message_data    BYTEA NOT NULL,
    sent_ts         TIMESTAMP,
    sent_metadata   VARCHAR(200),
    PRIMARY KEY (id)
);
