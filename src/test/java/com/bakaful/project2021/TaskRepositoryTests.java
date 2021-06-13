package com.bakaful.project2021;


import com.bakaful.project2021.domains.PersonalTask;
import com.bakaful.project2021.domains.TeamsTask;
import com.bakaful.project2021.domains.User;
import com.bakaful.project2021.repositories.UserRepository;
import com.bakaful.project2021.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TaskRepositoryTests {

    @Autowired
    private TaskRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateEvent() {

        User user1 = new User("spazrocha@gmail.com", "Sebas",
                "12345", "Sebastian", "Paz");
        User user2 = new User("isantangelo@gmail.com", "Ilaria",
                "12345", "Ilaria", "Santangelo");

        userRepository.save(user1);
        userRepository.save(user2);

        PersonalTask personalTask =
                new PersonalTask("myTask", "myDescription",
                        LocalDate.now(), LocalTime.now());

        personalTask.setTaskOwner(user1);

        TeamsTask teamsTask =
                new TeamsTask("ourTask", "ourDescription",
                        LocalDate.now(), LocalTime.now());

        teamsTask.getCollaborators().add(user1);
        teamsTask.getCollaborators().add(user2);

        eventRepository.save(personalTask);
        eventRepository.save(teamsTask);
    }
}
