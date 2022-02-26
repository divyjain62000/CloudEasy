package com.cloudeasy.services.auth;

import java.util.ArrayList;

import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.services.domain.user.UserService;
import com.cloudeasy.services.domain.user.dto.UserDTO;
import com.cloudeasy.services.utils.encryption.EncryptionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDTO=userService.findByEmailId(username);

        if (userDTO.getEmailId().equals(username)) {
            return new User(username, userDTO.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public void save(UserDTO userDTO) throws CustomException {
        this.userService.save(userDTO);
    }


}
