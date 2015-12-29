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
@RequestMapping("/eventlogservice")
public class EventLogController {
    private EventLogRepository eventLogRepository;

    @Autowired
    public EventLogController(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    @RequestMapping(value = "/getevents", method = RequestMethod.GET)
    public List<EventLog> getEvents(@RequestParam(value = "uuid", required = false) String uuid, @RequestParam(value = "filterBy", required = true) String filterBy) {
        if (uuid == null) {
            return eventLogRepository.findTop100ByFilter(filterBy);
        }
        EventLog lastReadEventLog = eventLogRepository.findByUuid(uuid);
        return eventLogRepository.findTop100ByFilterAndIdAfter(filterBy, lastReadEventLog.getId());
    }
}
