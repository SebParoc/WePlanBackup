package com.bakaful.project2021.repositories;

import com.bakaful.project2021.domains.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
