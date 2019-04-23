INSERT INTO service_questions (id, title, description, created_at, updated_at, service_question_type, service_id)
VALUES (11, 'Please select a house size (in square feet)', 'What is the house size (in square feet)?', NOW(), NOW(), 'MINMAX', 123);
INSERT INTO service_questions (id, title, description, created_at, updated_at, service_question_type, service_id)
VALUES (12, 'Multiple stories?', 'Do you have multiple stories in the house?', NOW(), NOW(), 'YESORNO', 123);
INSERT INTO service_questions (id, title, description, created_at, updated_at, service_question_type, service_id)
VALUES (13, 'How clean?', 'How clean do you want the house?', NOW(), NOW(), 'MULTIPLECHOICES', 123);
INSERT INTO service_questions (id, description, service_question_type, title, service_id, created_at, updated_at)
VALUES (20, 'Select how many pet you have', 'MULTIPLECHOICES', 'Do you have any pet?', 890, NOW(), NOW());
INSERT INTO service_questions (id, description, service_question_type, title, service_id)
VALUES (30, 'Choose your answer', 'YESORNO', 'Does the service need to be provided under your supervision? ', 789);
INSERT INTO service_questions (id, title, description, created_at, updated_at, service_question_type, service_id)
VALUES (41, 'Big dog?', 'Is the dog big?', NOW(), NOW(), 'YESORNO', 789);
