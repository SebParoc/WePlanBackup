package com.bakaful.project2021;

import static org.assertj.core.api.Assertions.assertThat;
import com.bakaful.project2021.domains.User;
import com.bakaful.project2021.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@SuppressWarnings("ALL")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("spazrocha@gmail.com");
        user.setPassword("12345");
        user.setFirstName("Sebastian");
        user.setLastName("Paz");

        User savedUser = userRepository.save(user);
        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }

    @Test
    public void testFindUserByEmail() {
        String email = "spazrocha@unibz.it";

        User user = userRepository.findByEmail(email);

        assertThat(user).isNotNull();
        System.out.println(user);
    }

}