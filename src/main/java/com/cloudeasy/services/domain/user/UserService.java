package com.cloudeasy.services.domain.user;

import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.services.domain.user.dto.UserDTO;

/**
 * User service interface
 */
public interface UserService {

    /**
     * To add user
     * @param userDTO
     * @throws CustomException
     */
    void save(UserDTO userDTO) throws CustomException;

    /**
     * To find user by id
     * @param id
     * @return {@link UserDTO}
     */
    UserDTO findById(Long id);

    /**
     * To find user by email id
     * @param emailId
     * @return {@link UserDTO}
     */
    UserDTO findByEmailId(String emailId);
}
