package org.bahmni.module.eventlogservice.repository;

import org.bahmni.module.eventlogservice.model.AddressHierarchyEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressHierarchyEntryRepository extends JpaRepository<AddressHierarchyEntry, Integer> {
    AddressHierarchyEntry findByUuid(String uuid);
}
