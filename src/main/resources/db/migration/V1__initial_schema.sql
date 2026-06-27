CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,

    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    timezone VARCHAR(100),

    preferred_channel VARCHAR(20) NOT NULL,
    role VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notifications (
    notification_id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    notification_status VARCHAR(20) NOT NULL,

    retry_count INTEGER NOT NULL DEFAULT 0,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    channel VARCHAR(20) NOT NULL,

    message TEXT NOT NULL,

    urgency VARCHAR(20),

    CONSTRAINT fk_notification_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
);

CREATE INDEX idx_notification_user
ON notifications(user_id);

CREATE INDEX idx_notification_status
ON notifications(notification_status);

CREATE TABLE factor_percentage (

    id BIGSERIAL PRIMARY KEY,

    factor VARCHAR(50) NOT NULL UNIQUE,

    percentage NUMERIC(5,2) NOT NULL
);

CREATE TABLE urgency_weight (

    id BIGSERIAL PRIMARY KEY,

    urgency VARCHAR(20) NOT NULL,

    channel VARCHAR(20) NOT NULL,

    urgency_percentage NUMERIC(5,2) NOT NULL,

    CONSTRAINT uk_urgency_channel
        UNIQUE(urgency,channel)
);

CREATE TABLE notification_selection_audit (

    notification_selection_audit_id BIGSERIAL PRIMARY KEY,

    notification_id BIGINT NOT NULL,

    email_score NUMERIC(5,2) NOT NULL,

    sms_score NUMERIC(5,2) NOT NULL,

    push_score NUMERIC(5,2) NOT NULL,

    selected_channel VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_audit_notification
        FOREIGN KEY(notification_id)
        REFERENCES notifications(notification_id)
);

CREATE INDEX idx_notification_audit_notification
ON notification_selection_audit(notification_id);

CREATE TABLE channel_metrics (

    channel VARCHAR(20) PRIMARY KEY,

    success_count BIGINT NOT NULL DEFAULT 0,

    failure_count BIGINT NOT NULL DEFAULT 0,

    retry_count BIGINT NOT NULL DEFAULT 0,

    last_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);