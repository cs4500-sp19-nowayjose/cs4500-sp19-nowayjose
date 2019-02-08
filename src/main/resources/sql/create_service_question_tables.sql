DROP DATABASE IF EXISTS service_questions;
CREATE TABLE service_questions (
   id                   INTEGER       PRIMARY KEY  AUTO_INCREMENT,
   title                VARCHAR(255),
   description          VARCHAR(255),
   createdAt            DATETIME,
   updatedAt            DATETIME,
   serviceQuestionType  VARCHAR(255),
   service_id           INTEGER,
   FOREIGN KEY (service_id) REFERENCES services (id)
) ENGINE=InnoDB;