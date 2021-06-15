package com.bakafulteam.weplan;


import com.bakafulteam.weplan.domains.SimpleTask;
import com.bakafulteam.weplan.domains.TeamsTask;
import com.bakafulteam.weplan.domains.User;
import com.bakafulteam.weplan.repositories.UserRepository;
import com.bakafulteam.weplan.repositories.TaskRepository;
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

        SimpleTask simpleTask =
                new SimpleTask("myTask", "myDescription",
                        LocalDate.now(), LocalTime.now());

        simpleTask.setTaskOwner(user1);

        TeamsTask teamsTask =
                new TeamsTask("ourTask", "ourDescription",
                        LocalDate.now(), LocalTime.now());

        teamsTask.getCollaborators().add(user1);
        teamsTask.getCollaborators().add(user2);

        eventRepository.save(simpleTask);
        eventRepository.save(teamsTask);
    }
}
