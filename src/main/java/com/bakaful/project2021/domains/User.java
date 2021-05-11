package com.bakaful.project2021.domains;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @ElementCollection
    private Map<String, Task> timetable = new HashMap<>();

    private String friendCode;

    @ElementCollection
    private List<User> friendList = new ArrayList<>();

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

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
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

    public Map<String, Task> getTimetable () {
        return timetable;
    }

    public void setTimetable (Map<String, Task> timetable) {
        this.timetable = timetable;
    }

    public String getFriendCode () {
        return friendCode;
    }

    public void setFriendCode (String friendCode) {
        this.friendCode = friendCode;
    }

    public List<User> getFriendList () {
        return friendList;
    }

    public void setFriendList (List<User> friendList) {
        this.friendList = friendList;
    }

    @Override
    public String toString () {
        return "Email: " + getEmail()
                + "\nName: " + getFirstName()
                + " " + getLastName();
    }
}

