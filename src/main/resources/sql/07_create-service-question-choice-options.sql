DROP TABLE IF EXISTS service_question_choice_options;
CREATE TABLE service_question_choice_options (
  id int(11) NOT NULL AUTO_INCREMENT,
  service_question_id int(11) NOT NULL,
  text varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (service_question_id) references service_questions (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
