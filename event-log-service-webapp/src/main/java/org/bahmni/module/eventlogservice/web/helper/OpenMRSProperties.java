package org.bahmni.module.eventlogservice.web.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class OpenMRSProperties {

    @Value("${openmrs.auth.uri}")
    private String openmrsAuthURL;

    @Value("${openmrs.user}")
    private String openmrsUser;

    @Value("${openmrs.password}")
    private String openmrsPassword;

    @Value("${bahmni.filter.uri}")
    private String bahmniEventLogFilterURL;

    @Value("${openmrs.connectionTimeoutInMilliseconds}")
    private String openMRSconnectionTimeoutInMilliseconds;

    @Value("${openmrs.replyTimeoutInMilliseconds}")
    private String openMRSReplyTimeoutInMilliseconds;

    public String getOpenMRSAuthURL() {
        return openmrsAuthURL;
    }

    public String getOpenMRSUser() {
        return openmrsUser;
    }

    public String getOpenMRSPassword() {
        return openmrsPassword;
    }

    public int getConnectionTimeoutInMilliseconds() {
        return Integer.parseInt(openMRSconnectionTimeoutInMilliseconds);
    }

    public int getReplyTimeoutInMilliseconds() {
        return Integer.parseInt(openMRSReplyTimeoutInMilliseconds);
    }


}
