package com.bakafulteam.weplan.repositories;

import com.bakafulteam.weplan.domains.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository that help us manage the timer entities in the database
 */
@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {
}
