package org.bahmni.module.offlineservice;

import org.openmrs.Patient;
import org.openmrs.PersonAddress;

import java.util.HashSet;

public class PatientBuilder {
    private Patient patient = new Patient();

    public Patient build() {
        return patient;
    }

    public PatientBuilder withAddress1(String address1) {
        getPersonAddress(patient).setAddress1(address1);
        return this;
    }

    public PatientBuilder withAddress2(String address2) {
        getPersonAddress(patient).setAddress2(address2);
        return this;
    }

    private PersonAddress getPersonAddress(Patient patient) {
        if (patient.getPersonAddress() == null) {
            PersonAddress personAddress = new PersonAddress();
            HashSet<PersonAddress> addresses = new HashSet<PersonAddress>();
            addresses.add(personAddress);
            patient.setAddresses(addresses);
            return personAddress;
        }
        return patient.getPersonAddress();
    }
}
