DROP TABLE IF EXISTS service_question_choice_options;
CREATE TABLE service_question_choice_options (
  id int(11) NOT NULL AUTO_INCREMENT,
  question_answer_id int(11) NOT NULL,
  text varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (question_answer_id) references service_question_answers (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
