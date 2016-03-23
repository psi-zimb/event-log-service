INSERT INTO person(person_id, uuid)
VALUES (1, 'patient uuid');

INSERT INTO person_attribute_type(person_attribute_type_id, name)
VALUES (1, 'addressCode');

INSERT INTO person_attribute(person_attribute_id, person_id, person_attribute_type_id, value)
VALUES (1, 1, 1, 'Who Knows');