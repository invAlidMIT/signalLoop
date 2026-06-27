INSERT INTO factor_percentage(factor, percentage)
VALUES
('USER_PREFERENCE',60),
('RELIABILITY',10),
('URGENCY',30);

INSERT INTO urgency_weight(urgency, channel, urgency_percentage)
VALUES

('LOW','EMAIL',100),
('LOW','SMS',70),
('LOW','PUSH',60),

('MEDIUM','EMAIL',80),
('MEDIUM','SMS',85),
('MEDIUM','PUSH',75),

('HIGH','EMAIL',50),
('HIGH','SMS',85),
('HIGH','PUSH',100);

INSERT INTO channel_metrics(channel,success_count,failure_count,retry_count)
VALUES
('EMAIL',0,0,0),
('SMS',0,0,0),
('PUSH',0,0,0);