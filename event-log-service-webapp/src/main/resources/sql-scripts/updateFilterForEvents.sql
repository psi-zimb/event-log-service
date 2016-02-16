-- update filter for address hierarchy entry events.
UPDATE event_log el
  INNER JOIN address_hierarchy_entry ahe
    ON el.object LIKE concat('%', ahe.uuid, '%') AND el.category = 'Address Hierarchy'
SET el.filter = ahe.user_generated_id;

-- update filter for patient events.
UPDATE event_log el
  INNER JOIN person p
    ON substring(el.object,29,36) = p.uuid AND el.category = 'patient'
  INNER JOIN person_attribute pa
    ON pa.person_id = p.person_id
  INNER JOIN person_attribute_type pat
    ON pa.person_attribute_type_id = pat.person_attribute_type_id AND pat.name='class'
SET el.filter = pa.value;

-- update filter for encounter events.
UPDATE event_log el
  INNER JOIN encounter e
    ON substring(el.object,31,36) = e.uuid AND el.category = 'Encounter'
  INNER JOIN person_attribute pa
    ON pa.person_id = e.patient_id
  INNER JOIN person_attribute_type pat
    ON pa.person_attribute_type_id = pat.person_attribute_type_id AND pat.name='class'
SET el.filter = pa.value;