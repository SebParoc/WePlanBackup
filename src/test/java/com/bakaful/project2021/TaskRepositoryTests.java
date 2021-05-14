package com.bakaful.project2021;

import static org.assertj.core.api.Assertions.assertThat;
import com.bakaful.project2021.domains.Task;
import com.bakaful.project2021.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

@SuppressWarnings("ALL")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TaskRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setTaskOwner("isantangelo@unibz.it");
        task.setName("Programming Project Assigment");
        task.setDescription("Regular expressions exercises");
        task.setDate(LocalDate.of(2021, Month.MAY, 15));
        task.setStartTime(LocalTime.of(10,30));
        task.setEndTime(LocalTime.of(12,30));
        task.setPriority("Extremely Important");
        task.setShareable("Private");

        Task savedTask = taskRepository.save(task);
        Task existTask = entityManager.find(Task.class, savedTask.getId());

        assertThat(task.getTaskOwner()).isEqualTo(existTask.getTaskOwner());
    }

}
