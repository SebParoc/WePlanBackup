package com.bakaful.project2021.repositories;

import com.bakaful.project2021.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}