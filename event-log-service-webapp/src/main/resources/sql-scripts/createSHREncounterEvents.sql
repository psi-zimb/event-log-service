Insert into event_log(
  uuid,
  timestamp,
  object,
  category
)
  (select uuid(),er.timestamp, er.object,er.category
   from event_records er
   ORDER BY er.timestamp );