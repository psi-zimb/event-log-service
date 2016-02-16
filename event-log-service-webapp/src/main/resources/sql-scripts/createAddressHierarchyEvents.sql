Insert into event_records(uuid,
                          title,
                          timestamp,
                          uri,
                          object,
                          category)
  (select uuid(),
     'Address Hierarchy',
     '1970-01-01 00:00:01',
     concat('/openmrs/ws/rest/v1/addressHierarchy/',uuid),
     concat('/openmrs/ws/rest/v1/addressHierarchy/',uuid),
     'Address Hierarchy'
   from address_hierarchy_entry);