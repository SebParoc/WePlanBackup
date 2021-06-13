package com.bakaful.project2021.repositories;

import com.bakaful.project2021.domains.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.Timestamp;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value="select current_timestamp", nativeQuery = true)
    public Timestamp getCurrentTime();

    @Query(value="select * from tasks where task_id = :id"
            , nativeQuery = true)
    public Task getByTaskId(@Param("id")Long id);
}
