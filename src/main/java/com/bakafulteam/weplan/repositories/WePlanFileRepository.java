package com.bakafulteam.weplan.repositories;

import com.bakafulteam.weplan.domains.WePlanFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WePlanFileRepository extends JpaRepository<WePlanFile, Long> {

    @Query("select u from WePlanFile u where u.name = ?1")
    WePlanFile findByFileName(String email);
}
