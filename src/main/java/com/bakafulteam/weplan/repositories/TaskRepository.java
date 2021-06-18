package com.bakafulteam.weplan.repositories;

import com.bakafulteam.weplan.domains.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository that help us manage the task entities in the database
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
