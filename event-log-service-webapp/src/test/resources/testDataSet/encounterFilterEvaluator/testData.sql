INSERT INTO person(person_id)
VALUES (1);

INSERT INTO person(person_id)
VALUES (2);

INSERT INTO person_attribute_type(person_attribute_type_id, name)
VALUES (1, 'addressCode');

INSERT INTO person_attribute(person_attribute_id, person_id, person_attribute_type_id, value, voided)
VALUES (1, 1, 1, 'Who Knows', false);

INSERT INTO encounter(encounter_id, patient_id, uuid)
VALUES (1, 1, 'encounter uuid');

INSERT INTO person_attribute(person_attribute_id, person_id, person_attribute_type_id, value, voided)
VALUES (2, 2, 1, 'First', true);

INSERT INTO person_attribute(person_attribute_id, person_id, person_attribute_type_id, value, voided)
VALUES (3, 2, 1, 'Second', false);

INSERT INTO encounter(encounter_id, patient_id, uuid)
VALUES (2, 2, 'encounter uuid2');