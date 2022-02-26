package com.cloudeasy.services.domain.user.dto;

import com.cloudeasy.dto.DataTransferObject;
import com.cloudeasy.enums.role.Role;
import lombok.Data;


@Data
public class UserDTO extends DataTransferObject {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNumber;
    private String password;
    private String confirmPassword;
    private Role role;

}
