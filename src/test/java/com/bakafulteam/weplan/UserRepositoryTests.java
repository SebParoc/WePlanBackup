package com.bakafulteam.weplan;

import static org.assertj.core.api.Assertions.assertThat;

import com.bakafulteam.weplan.domains.User;
import com.bakafulteam.weplan.repositories.UserRepository;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import javax.validation.constraints.AssertTrue;
import java.io.File;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ALL")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user1, user2;

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
    }
}