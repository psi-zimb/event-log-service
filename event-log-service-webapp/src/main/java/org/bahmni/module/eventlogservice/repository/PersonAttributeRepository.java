package org.bahmni.module.eventlogservice.repository;

import org.bahmni.module.eventlogservice.model.PersonAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface PersonAttributeRepository extends JpaRepository<PersonAttribute, Integer> {
    @Query("select pa from PersonAttribute pa, Person p, PersonAttributeType pat where p.uuid=:uuid and p.id = pa.person and pa.personAttributeType = pat.id and pat.name=:attributeTypeName and pa.voided = false")
    PersonAttribute findByPersonUuidAndPersonAttributeType(@Param("uuid") String objectUuid, @Param("attributeTypeName") String attributeTypeName);

    @Query("select pa from PersonAttribute pa, PersonAttributeType pat, Encounter e where e.uuid=:uuid and e.person = pa.person and pa.personAttributeType = pat.id and pat.name=:attributeTypeName and pa.voided = false")
    PersonAttribute findByEncounterUuidAndPersonAttributeType(@Param("uuid") String objectUuid, @Param("attributeTypeName") String attributeTypeName);
}
