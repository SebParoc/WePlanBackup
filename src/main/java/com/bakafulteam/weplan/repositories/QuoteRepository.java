package com.bakafulteam.weplan.repositories;

import com.bakafulteam.weplan.domains.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>{
}
