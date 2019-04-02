INSERT INTO service_questions (id, title, description, created_at, updated_at, service_question_type, service_id)
VALUES (1, 'What size houses can you clean (in square feet)?', 'Select the minimum and maximum size of houses that you can clean.', NOW(), NOW(), 'MINMAX', 123);
INSERT INTO service_questions (id, description, service_question_type, title, service_id, created_at, updated_at)
VALUES (2, 'Select how many pet you have', 'MULTIPLECHOICES', 'Do you have any pet?', 890, NOW(), NOW());
INSERT INTO service_questions (id, description, service_question_type, title, service_id)
VALUES (3, 'Choose your answer', 'YESORNO', 'Does the service need to be provided under your supervision? ', 789);
