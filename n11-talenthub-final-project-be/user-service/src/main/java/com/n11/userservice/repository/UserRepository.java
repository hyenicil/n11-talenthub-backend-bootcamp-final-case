package com.n11.userservice.repository;

import com.n11.userservice.dto.user.UserResponse;
import com.n11.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
