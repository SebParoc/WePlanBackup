package com.bakafulteam.weplan.domains;

import javax.persistence.*;

@Entity
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderUsername;

    private String recipientUsername;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getSenderUsername () {
        return senderUsername;
    }

    public void setSenderUsername (String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getRecipientUsername () {
        return recipientUsername;
    }

    public void setRecipientUsername (String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }
}
