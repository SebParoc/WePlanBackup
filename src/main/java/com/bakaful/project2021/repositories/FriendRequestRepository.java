package com.bakaful.project2021.repositories;

import com.bakaful.project2021.domains.FriendRequest;
import com.bakaful.project2021.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("select u from FriendRequest u where u.senderUsername = ?1 and u.recipientUsername = ?2")
    FriendRequest findByUsernames(String senderUsername, String recipientUsername);

}
