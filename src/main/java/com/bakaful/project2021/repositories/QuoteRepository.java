package com.bakaful.project2021.repositories;

import com.bakaful.project2021.domains.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long>{
}
