DROP TABLE IF EXISTS service_questions;
CREATE TABLE service_questions (
   id                     INTEGER       PRIMARY KEY  AUTO_INCREMENT,
   title                  VARCHAR(255),
   description            VARCHAR(2000),
   created_at             DATETIME,
   updated_at             DATETIME,
   service_question_type  VARCHAR(32),
   service_id             INTEGER,
   FOREIGN KEY (service_id) REFERENCES services (id)
) ENGINE=InnoDB;