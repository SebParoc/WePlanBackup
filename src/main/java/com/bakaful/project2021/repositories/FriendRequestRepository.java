package com.bakaful.project2021.repositories;

import com.bakaful.project2021.domains.FriendRequest;
import com.bakaful.project2021.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("select u from FriendRequest u where u.sender = ?1")
    FriendRequest findBySender(User sender);

    @Query("select u from FriendRequest u where u.recipient = ?1")
    FriendRequest findByRecipient(User recipient);
}
