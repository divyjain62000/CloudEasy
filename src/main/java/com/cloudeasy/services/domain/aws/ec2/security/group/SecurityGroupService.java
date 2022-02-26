package com.cloudeasy.services.domain.aws.ec2.security.group;

import com.cloudeasy.domain.aws.ec2.security.group.SecurityGroup;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.services.domain.user.dto.UserDTO;

import java.util.Set;

/**
 * Security group service interface
 */
public interface SecurityGroupService {

    /**
     * To create security group
     * @param userDTO
     * @return {@link SecurityGroup}
     * @throws CustomException
     */
    SecurityGroup createSecurityGroup(UserDTO userDTO) throws CustomException;

    /**
     * To delete security group
     * @param securityGroups
     */
    void delete(Set<SecurityGroup> securityGroups);


    /**
     * To delete security group from ec2 instance
     * @param securityGroups
     */
    public void deleteSecurityGroup(Set<SecurityGroup> securityGroups);

}
