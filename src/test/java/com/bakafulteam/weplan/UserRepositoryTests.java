package com.bakafulteam.weplan;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.io.File;

@SuppressWarnings("ALL")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    /*@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("sparoc");
        user.setEmail("spazrocha@gmail.com");
        user.setPassword("12345");
        user.setFirstName("Sebastian");
        user.setLastName("Paz");

        User user1 = new User();
        user1.setUsername("ilariaaaa");
        user1.setEmail("isantangelo@gmail.com");
        user1.setPassword("12345");
        user1.setFirstName("Ilaria");
        user1.setLastName("Santangelo");

        User user2 = new User();
        user2.setUsername("Concha");
        user2.setEmail("Springdemierda@gmail.com");
        user2.setPassword("12345");
        user2.setFirstName("Fuck");
        user2.setLastName("PP");

        user.getFriends().add(user1);
        user1.getFriends().add(user);
        user.getFriends().add(user2);
        user2.getFriends().add(user);

        User savedUser = userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
        User existUser = entityManager.find(User.class, savedUser.getId());

        System.out.println(user.getFriends());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }

    @Test
    public void testFindUserByEmail() {
        String email = "spazrocha@unibz.it";

        User user = userRepository.findByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testFindUserByUsername() {
        String username = "SebParoc";

        User user = userRepository.findByUsername(username);

        assertThat(user).isNotNull();
    }*/
    StringBuilder builder = new StringBuilder()
            .append(System.getProperty("user.dir"))
            .append(File.separator)
            .append("src");

    @Test
    public void print() {
        builder.append(File.separator)
                .append("main");
        System.out.println(builder);
    }

    @Test
    public void print2() {
        System.out.println(builder);
    }

}