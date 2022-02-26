package com.cloudeasy.repository.user;

import com.cloudeasy.domain.user.User;
import com.cloudeasy.services.domain.user.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage User object
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmailId(String emailId);
}
