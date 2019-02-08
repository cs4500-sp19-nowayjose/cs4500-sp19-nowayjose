DROP TABLE IF EXISTS service_question_answers;
CREATE TABLE service_question_answers (
   id int(11) NOT NULL AUTO_INCREMENT,
   true_false_answer boolean,
   max_range_answer integer,
   min_range_answer integer,
   choice_answer integer,
   user_id integer,
   service_question_id integer,
   created_at             DATETIME,
   updated_at             DATETIME,
   PRIMARY KEY (id),
   foreign key (user_id) references users (id),
   foreign key (service_question_id) references service_questions (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;