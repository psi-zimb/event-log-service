package org.bahmni.module.eventlogservice.controller;

import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.repository.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/eventlog")
public class EventLogController {
    private EventLogRepository eventLogRepository;

    @Autowired
    public EventLogController(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<EventLog> getEvents(@RequestParam(value = "uuid", required = false) String uuid, @RequestParam(value = "filterBy", required = true) String filterBy) {
        if (uuid == null) {
            return eventLogRepository.findTop100ByFilterStartingWith(filterBy);
        }
        EventLog lastReadEventLog = eventLogRepository.findByUuid(uuid);
        return eventLogRepository.findTop100ByFilterStartingWithAndIdAfter(filterBy, lastReadEventLog.getId());
    }

    @RequestMapping(value = "/concepts", method = RequestMethod.GET)
    public List<EventLog> getConcepts(@RequestParam(value = "uuid", required = false) String uuid) {

        if (uuid == null) {
            return eventLogRepository.findTop100ByCategoryIs("offline-concepts");
        }
        EventLog lastReadEventLog = eventLogRepository.findByUuid(uuid);
        return eventLogRepository.findTop100ByCategoryIsAndIdAfter("offline-concepts", lastReadEventLog.getId());
    }


}
