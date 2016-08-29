package org.bahmni.module.eventlogservice.controller;

import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.repository.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<EventLog> getEvents(@RequestParam(value = "uuid", required = false) String uuid, @RequestParam(value = "filterBy", required = true) String[] filterBy) {
        List<String> categoryList = new ArrayList<String>();
        categoryList.add("addressHierarchy");
        if (uuid == null) {
            return eventLogRepository.findTop100ByFilterInAndCategoryNotIn(Arrays.asList(filterBy), categoryList);
        }
        EventLog lastReadEventLog = eventLogRepository.findTop1ByUuid(uuid);

        return eventLogRepository.findTop100ByFilterInAndIdAfterAndCategoryNotIn( Arrays.asList(filterBy), lastReadEventLog.getId(), categoryList);
    }

    @RequestMapping(value = "/getAddressHierarchyEvents", method = RequestMethod.GET)
        public List<EventLog> getAddressHierarchyEvents(@RequestParam(value = "uuid", required = false) String uuid, @RequestParam(value = "filterBy", required = false) String[] filterBy) {
        if(filterBy != null && filterBy.length > 1){
            throw new RuntimeException("Address hierarchy events should have only one filter!!");
        }
        if (filterBy == null && uuid == null) {
            return eventLogRepository.findTop100ByCategoryAndFilterIsNull("addressHierarchy");
        }else if (filterBy == null){
            EventLog lastReadEventLog = eventLogRepository.findTop1ByUuid(uuid);
            return  eventLogRepository.findTop100ByCategoryAndIdAfterAndFilterIsNull("addressHierarchy", lastReadEventLog.getId());
        }else if (uuid == null){
            return  eventLogRepository.findTop100ByCategoryAndFilterStartingWith("addressHierarchy", filterBy[0]);
        }else{
            EventLog lastReadEventLog = eventLogRepository.findTop1ByUuid(uuid);
            return  eventLogRepository.findTop100ByCategoryAndFilterStartingWithAndIdAfter("addressHierarchy", filterBy[0], lastReadEventLog.getId());
        }
    }

    @RequestMapping(value = "/concepts", method = RequestMethod.GET)
    public List<EventLog> getConcepts(@RequestParam(value = "uuid", required = false) String uuid) {

        if (uuid == null) {
            return eventLogRepository.findTop100ByCategoryIs("offline-concepts");
        }
        EventLog lastReadEventLog = eventLogRepository.findTop1ByUuid(uuid);
        return eventLogRepository.findTop100ByCategoryIsAndIdAfter("offline-concepts", lastReadEventLog.getId());
    }


}
