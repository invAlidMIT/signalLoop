-- CLEAN TABLES
TRUNCATE TABLE notifications RESTART IDENTITY CASCADE;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE urgency_weight RESTART IDENTITY CASCADE;
TRUNCATE TABLE reliability RESTART IDENTITY CASCADE;
TRUNCATE TABLE factor_percentage RESTART IDENTITY CASCADE;


-- FACTOR PERCENTAGES
INSERT INTO factor_percentage (factor, percentage)
VALUES
('USER_PREFERENCE', 40),
('RELIABILITY', 35),
('URGENCY', 25);


-- RELIABILITY SCORES
INSERT INTO reliability (channel, value)
VALUES
('SMS', 95),
('EMAIL', 80),
('PUSH', 70);


-- URGENCY WEIGHTS
INSERT INTO urgency_weight (urgency, channel, urgency_percentage)
VALUES
('HIGH','PUSH',100),
('HIGH','SMS',85),
('HIGH','EMAIL',50),

('MEDIUM','PUSH',75),
('MEDIUM','SMS',85),
('MEDIUM','EMAIL',80),

('LOW','PUSH',60),
('LOW','SMS',70),
('LOW','EMAIL',100);


-- USERS
INSERT INTO users
(email, password, timezone, preferred_channel, created_at, role)
VALUES
(
'xys@gmail.com',
'$2a$10$bQMz20Kr.bnG2SK8I124N.IPg/cnJEimGE9U660FBxwp6IAAbS5wS',
'Asia/Kolkata',
'EMAIL',
NOW(),
'ROLE_ADMIN'
);


-- NOTIFICATIONS
INSERT INTO notifications
(user_id, notification_status, retry_count, created_at, channel, message, urgency)
VALUES

-- HIGH
(1,'PENDING',0,NOW(),'PUSH','Server CPU usage critical','HIGH'),
(1,'PENDING',0,NOW(),'SMS','Payment failed for customer','HIGH'),
(1,'PENDING',0,NOW(),'EMAIL','Security alert login attempt','HIGH'),

-- MEDIUM
(1,'PENDING',0,NOW(),'EMAIL','Daily backup completed','MEDIUM'),
(1,'PENDING',0,NOW(),'SMS','Invoice generated successfully','MEDIUM'),
(1,'PENDING',0,NOW(),'PUSH','Weekly report ready','MEDIUM'),

-- LOW
(1,'PENDING',0,NOW(),'EMAIL','Promotional offer available','LOW'),
(1,'PENDING',0,NOW(),'SMS','Newsletter published','LOW'),
(1,'PENDING',0,NOW(),'PUSH','App update available','LOW');

--Channel Metrics
INSERT INTO channel_metrics
(channel,success_count,failure_count,retry_count)
VALUES
('SMS',0,0,0),
('EMAIL',0,0,0),
('PUSH',0,0,0);