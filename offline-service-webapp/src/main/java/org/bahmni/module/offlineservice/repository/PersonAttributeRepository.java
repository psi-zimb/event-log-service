package org.bahmni.module.offlineservice.repository;

import org.bahmni.module.offlineservice.model.PersonAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface PersonAttributeRepository extends JpaRepository<PersonAttribute, Integer> {
    @Query("select pa from PersonAttribute pa, Person p, PersonAttributeType pat where p.uuid=:uuid and p.id = pa.person and pa.personAttributeType = pat.id and pat.name=:attributeTypeName")
    PersonAttribute findByPersonUuidAndPersonAttributeType(@Param("uuid") String objectUuid, @Param("attributeTypeName") String attributeTypeName);

    @Query("select pa from PersonAttribute pa, PersonAttributeType pat, Encounter e where e.uuid=:uuid and e.person = pa.person and pa.personAttributeType = pat.id and pat.name=:attributeTypeName")
    PersonAttribute findByEncounterUuidAndPersonAttributeType(@Param("uuid") String objectUuid, @Param("attributeTypeName") String attributeTypeName);
}
