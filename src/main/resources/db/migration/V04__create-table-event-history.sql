CREATE TABLE event_history (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  type varchar(20) NOT NULL,
  subscription_id varchar(255) NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE event_history
    ADD CONSTRAINT fk_event_history_subscription
         FOREIGN KEY (subscription_id) REFERENCES subscription(id);