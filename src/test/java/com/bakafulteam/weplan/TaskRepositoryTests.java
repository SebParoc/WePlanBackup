package com.bakafulteam.weplan;


import com.bakafulteam.weplan.domains.*;
import com.bakafulteam.weplan.repositories.UserRepository;
import com.bakafulteam.weplan.repositories.TaskRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TaskRepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    SimpleTask simpleTask;
    TeamsTask teamsTask;
    ScheduledTask scheduledTask;

    @BeforeEach
    public void createTasks() {
        simpleTask = new SimpleTask();
        simpleTask.setName("My Simple Task");
        simpleTask.setDescription("My simple task's description");
        simpleTask.setDate("2021-06-18");
        simpleTask.setTaskTime("18:00");

        teamsTask = new TeamsTask();
        teamsTask.setName("My teams Task");
        teamsTask.setDescription("My teams task's description");
        teamsTask.setDate("2021-06-19");
        teamsTask.setTaskTime("19:00");

        scheduledTask = new ScheduledTask();
        scheduledTask.setName("My scheduled Task");
        scheduledTask.setDescription("My scheduled task's description");
        scheduledTask.setDate("2021-06-20");
        scheduledTask.setStartTime("21:00");
        scheduledTask.setEndTime("22:00");
        scheduledTask.setTaskTime();

        taskRepository.save(simpleTask);
        taskRepository.save(teamsTask);
        taskRepository.save(scheduledTask);
    }

    @Test
    public void tasksShouldBeSaved() {
        Task existTask1 = entityManager.find(Task.class, simpleTask.getId());
        Task existTask2 = entityManager.find(Task.class, teamsTask.getId());
        Task existTask3 = entityManager.find(Task.class, scheduledTask.getId());

        System.out.println(scheduledTask);
        assertEquals(simpleTask.getId(), existTask1.getId());
        assertEquals(teamsTask.getId(), existTask2.getId());
        assertEquals(scheduledTask.getId(), existTask3.getId());
    }

    @Test
    public void shouldHaveOwnersAndCollaborators() {
        User user1 = new User("spazrocha@test.com", "Sebas",
                "12345", "Sebastian", "Paz");
        User user2 = new User("isantangelo@test.com", "Ilaria",
                "12345", "Ilaria", "Santangelo");

        userRepository.save(user1);
        userRepository.save(user2);

        simpleTask.setTaskOwner(user1);
        scheduledTask.setTaskOwner(user2);
        teamsTask.getCollaborators().add(user1);
        teamsTask.getCollaborators().add(user2);

        taskRepository.save(simpleTask);
        taskRepository.save(teamsTask);
        taskRepository.save(scheduledTask);

        assertAll(
                () -> assertEquals(user1, simpleTask.getTaskOwner()),
                () -> assertEquals(user2, scheduledTask.getTaskOwner()),
                () -> assertEquals(List.of(user1, user2), teamsTask.getCollaborators())
        );

        userRepository.delete(user1);
        userRepository.delete(user2);
    }

    @AfterEach
    public void deleteTheTasksIfTheyAreInTheDatabase() {
        Optional<SimpleTask> simpleTaskOptional = taskRepository.findById(simpleTask.getId())
                .map(task -> (SimpleTask) task);
        simpleTaskOptional.ifPresent(byeTask -> taskRepository.delete(byeTask));
        Optional<TeamsTask> teamsTaskOptional = taskRepository.findById(teamsTask.getId())
                .map(task -> (TeamsTask) task);
        ;
        teamsTaskOptional.ifPresent(byeTask -> taskRepository.delete(byeTask));
        Optional<ScheduledTask> scheduleTaskOptional = taskRepository.findById(scheduledTask.getId())
                .map(task -> (ScheduledTask) task);
        ;
        scheduleTaskOptional.ifPresent(byeTask -> taskRepository.delete(byeTask));
    }
/*
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
@BeforeEach
public void createInitialUsers() {
    user1 = new User();
    user1.setEmail("spazrocha@test.com");
    user1.setUsername("SebastianTest");
    user1.setPassword("12345");
    user1.setFirstName("Sebastian");
    user1.setLastName("Paz Rocha");

    user2 = new User("isantangelo@test.com",
            "IlariaTest",
            "54321",
            "Ilaria",
            "Santangelo");

    user1.getFriends().add(user2);
    user2.getFriends().add(user1);

    userRepository.save(user1);
    userRepository.save(user2);
}


    @Test
    public void shouldCheckIfUsersAreSaved() {
        User existUser1 = entityManager.find(User.class, user1.getId());
        User existUser2 = entityManager.find(User.class, user2.getId());

        assertEquals(user1.getEmail(), existUser1.getEmail());
        assertEquals(user2.getEmail(), existUser2.getEmail());
    }

    //The user must be new and not a duclicate from the repository
    @Test
    public void shouldCreateNewUserAndSaveThem() {

        User user3 = new User("spongebob@test.com",
                "BobTest",
                "12345",
                "Sponge Bob",
                "Square Pants");

        userRepository.save(user3);

        User existUser3 = entityManager.find(User.class, user3.getId());

        assertEquals(user3.getEmail(), existUser3.getEmail());
    }

    @Test
    public void shouldFindUserByID() {
        Long id = user2.getId();
        Optional<User> userOptional = userRepository.findById(id);

        assertTrue(userOptional.isPresent());
    }

    @Test
    public void shouldFindUserByEmail() {
        String email = "spazrocha@test.com";
        User user = userRepository.findByEmail(email);

        assertNotNull(user);
    }

    @Test
    public void shouldFindUserByUsername() {
        String username = "IlariaTest";
        User user = userRepository.findByUsername(username);

        assertNotNull(user);
    }

    @Test
    public void usersShouldBeFriends() {
        assertTrue(userRepository.findByUsername("SebastianTest").getFriends().contains(
                userRepository.findByUsername("IlariaTest")
        ));
    }

    @Test
    public void shouldDeleteUserFromRepository() {

        userRepository.delete(user1);
        User existUser4 = entityManager.find(User.class, user1.getId());

        assertNull(existUser4);
    }

    //The user must be in the repository
    @Test
    public void shouldDeleteFoundUser() {
        User user = userRepository.findByUsername("BobTest");
        userRepository.delete(user);
        User existUser = entityManager.find(User.class, user.getId());

        assertNull(existUser);
    }

    @AfterEach
    public void shouldDeleteUsers() {
        user1.getFriends().remove(user2);
        user2.getFriends().remove(user1);

        userRepository.deleteAll(List.of(user1, user2));
    }*/
}
