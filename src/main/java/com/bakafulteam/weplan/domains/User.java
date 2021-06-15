package com.bakafulteam.weplan.domains;

import com.bakafulteam.validator.IsValidPassword;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@DynamicUpdate
@Table(name = "users")
public class User {

    public User () {
    }

    public User (String email, String username, String password, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


  @Pattern(regexp = "^[A-Z0-9+_.-]+@[A-Z0-9.-]+$")

  @Column(nullable = false, unique = true, length = 50)
  private String email;

    @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true, length = 15)
    private String username;

    private String image = "Default.png";

    @Column(nullable = false, length = 64)
    @IsValidPassword
    @NotNull
    private String password;

    @NotNull(message = "First name is compulsory")
    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @NotNull(message = "Last name is compulsory")
    @Column(name = "last_name", nullable = false, length = 40)
    @Size(min = 2, max = 35, message = "Surname must be 2-35 characters long.")
    private String lastName;

    @ManyToMany
    private Set<User> friends = new HashSet<>();

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Set<User> getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}

