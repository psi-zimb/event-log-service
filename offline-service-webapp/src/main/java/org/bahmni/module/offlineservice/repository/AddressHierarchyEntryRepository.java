package org.bahmni.module.offlineservice.repository;

import org.bahmni.module.offlineservice.model.AddressHierarchyEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressHierarchyEntryRepository extends JpaRepository<AddressHierarchyEntry, Integer> {
    AddressHierarchyEntry findByUuid(String uuid);
}
