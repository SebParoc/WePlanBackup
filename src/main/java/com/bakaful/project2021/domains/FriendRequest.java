package com.bakaful.project2021.domains;

import javax.persistence.*;

@Entity
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public User getSender () {
        return sender;
    }

    public void setSender (User sender) {
        this.sender = sender;
    }

    public User getRecipient () {
        return recipient;
    }

    public void setRecipient (User recipient) {
        this.recipient = recipient;
    }
}
