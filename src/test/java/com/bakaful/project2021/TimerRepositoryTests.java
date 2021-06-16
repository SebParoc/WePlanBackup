package com.bakaful.project2021;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TimerRepositoryTests {

    /*@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TimerRepository timerRepository;

    @Test
    public void shouldCreateTimer(){
        Timer timer  = new Timer();
        timer.setName("timerTest");
        timer.setMinutes(25);
        timer.setSeconds(0);
        timer.setLongBreakMins(15);
        timer.setLongBreakSecs(0);
        timer.setShortBreakMins(3);
        timer.setShortBreakSecs(0);


        Timer timer1  = new Timer();
        timer1.setName("timerTest1");
        timer1.setMinutes(30);
        timer1.setSeconds(0);
        timer1.setLongBreakMins(10);
        timer1.setLongBreakSecs(0);
        timer1.setShortBreakMins(5);
        timer1.setShortBreakSecs(0);


        Timer savedTimer = timerRepository.save(timer);
        timerRepository.save(timer1);
        Timer existTimer = entityManager.find(Timer.class, savedTimer.getId());
    }
*/


}
