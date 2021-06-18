package com.bakafulteam.weplan;

import com.bakafulteam.weplan.domains.Timer;
import com.bakafulteam.weplan.domains.User;
import com.bakafulteam.weplan.repositories.TimerRepository;
import com.bakafulteam.weplan.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimerRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TimerRepository timerRepository;

    @Autowired
    private UserRepository userRepository;

    private Timer timer;

    @BeforeEach
    public void createTimer(){
        timer = new Timer();
        timer.setName("timerTest");
        timer.setMinutes(25);
        timer.setLongBreakMins(15);
        timer.setShortBreakMins(5);
        timerRepository.save(timer);
    }

    @Test
    public void timerShouldBeSaved(){
        Timer savedTimer = timerRepository.save(timer);
        Timer existTimer = entityManager.find(Timer.class, savedTimer.getId());
        assertEquals(timer.getId(), existTimer.getId());
    }

    @Test
    public void shouldFindTimerInTheDatabase() {
        Optional<Timer> timerOptional= timerRepository.findById(timer.getId());
        assertTrue(timerOptional.isPresent());
    }

    @Test
    public void shouldHaveAnOwner() {
        User user = new User("taskowner@test.com", "TaskOwner", "12345", "Task", "Owner");
        userRepository.save(user);
        timer.setTimerOwner(user);
        timerRepository.save(timer);
        assertAll(
                ()-> assertNotEquals(null, timer.getTimerOwner()),
                ()->assertEquals(user, timer.getTimerOwner())
        );
        userRepository.delete(user);
    }

    @Test
    public void shouldDeleteTimerIfItExist() {
        Optional<Timer> timerOptional = timerRepository.findById(timer.getId());
        timerOptional.ifPresent(byeTimer -> timerRepository.delete(byeTimer));
        assertThrows(NoSuchElementException.class, () -> timerRepository.findById(timer.getId()).get());
    }

    @AfterEach
    public void deleteTimerIfItsInTheDatabase() {
        Optional<Timer> timerOptional = timerRepository.findById(timer.getId());
        timerOptional.ifPresent(byeTimer -> timerRepository.delete(byeTimer));
    }
}
