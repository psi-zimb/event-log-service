INSERT INTO person(person_id)
VALUES (1);

INSERT INTO person_attribute_type(person_attribute_type_id, name)
VALUES (1, 'Catchment number');

INSERT INTO person_attribute(person_attribute_id, person_id, person_attribute_type_id, value)
VALUES (1, 1, 1, 'Who Knows');

INSERT INTO encounter(encounter_id, patient_id, uuid)
VALUES (1, 1, 'encounter uuid');