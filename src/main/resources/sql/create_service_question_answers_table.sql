DROP DATABASE IF EXISTS service_question_answers;
CREATE TABLE service_question_answers (
   id integer not null auto_increment,
   true_false_answer bit,
   max_range_answer integer,
   min_range_answer integer,
   choice_answer integer,
   updatedBy_id integer,
   service_question_id integer,
   primary key (id),
   foreign key (updatedBy_id) references users (id),
   foreign key (service_question_id) references service_questions (id)
) ENGINE=InnoDB