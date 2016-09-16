package org.bahmni.module.eventlogservice.fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.web.helper.OpenMRSWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Component
public class EventLogFetcher {

    private OpenMRSWebClient openMRSWebClient;

    @Value("${bahmni.eventlog.uri}")
    private String bahmniEventLogURL;


    @Autowired
    public EventLogFetcher(OpenMRSWebClient openMRSWebClient) {
        this.openMRSWebClient = openMRSWebClient;

    }

    public List<EventLog> fetchEventLogsAfter(String lastReadEventUuid) throws IOException {
        URI uri = constructBahmniURLForEventLogs(lastReadEventUuid);
        ObjectMapper objectMapper = new ObjectMapper();
        EventLog[] eventLogs = objectMapper.readValue(openMRSWebClient.get(uri), EventLog[].class);
        return Arrays.asList(eventLogs);
    }

    private URI constructBahmniURLForEventLogs(String lastReadEventUuid) {
        URI uri;
        String url = bahmniEventLogURL + lastReadEventUuid;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid url for event log filter : " + url, e);
        }
        return uri;
    }

    protected void setBahmniEventLogURL(String bahmniEventLogURL) {
        this.bahmniEventLogURL = bahmniEventLogURL;
    }
}
