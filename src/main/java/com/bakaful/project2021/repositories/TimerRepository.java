package com.bakaful.project2021.repositories;

import com.bakaful.project2021.domains.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {
}
