package com.cloudeasy.services.domain.aws.ec2.security.group.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.DeleteSecurityGroupRequest;
import com.amazonaws.services.ec2.model.DeleteSecurityGroupResult;
import com.cloudeasy.domain.aws.ec2.security.group.SecurityGroup;
import com.cloudeasy.enums.error.ErrorFor;
import com.cloudeasy.enums.error.UserError;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.repository.aws.ec2.security.group.SecurityGroupRepository;
import com.cloudeasy.services.domain.aws.ec2.security.group.SecurityGroupService;

import com.cloudeasy.services.domain.user.UserService;
import com.cloudeasy.services.domain.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class SecurityGroupServiceImpl implements SecurityGroupService {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityGroupRepository securityGroupRepository;

    private final static String GROUP_NAME_PREFIX ="GN_";
    private final static String GROUP_DESCRIPTION_PRE="Group for ";

    /**
     * To create security group
     * @throws CustomException
     */
    @Override
    public SecurityGroup createSecurityGroup(UserDTO userDTO) throws CustomException {

        log.info("Request to create security group");

        if(userDTO==null) {
            CustomException customException=new CustomException();
            customException.addException(ErrorFor.ID_ERR.getErrorFor(), UserError.INVALID_ID.getUserError());
        }
        String groupName=generateGroupName(userDTO);
        String groupDescription=GROUP_DESCRIPTION_PRE+groupName;

        //create amazon ec2 security group
//        AmazonEC2 amazonEC2= AmazonEC2ClientBuilder.standard()
//                .withRegion(Regions.US_WEST_2)
//                .build();
        final AmazonEC2 amazonEC2=AmazonEC2ClientBuilder.defaultClient();
        CreateSecurityGroupRequest createSecurityGroupRequest=new CreateSecurityGroupRequest()
                                                .withGroupName(groupName)
                                                .withDescription(groupDescription);
        CreateSecurityGroupResult createSecurityGroupResult=amazonEC2.createSecurityGroup(createSecurityGroupRequest);
        SecurityGroup securityGroup=new SecurityGroup();
        securityGroup.setName(groupName);
        securityGroup.setDescription(groupDescription);
        securityGroup.setSecurityGroupId(createSecurityGroupResult.getGroupId());
        Long securityGroupId=save(securityGroup); // save security group details in db
        securityGroup.setId(securityGroupId);
        log.info("security group created successfully");
        return securityGroup;
    }

    /**
     * To save security group
     * @param securityGroup
     */
    private Long save(SecurityGroup securityGroup) {
        log.info("Request to save security group in DB");
        SecurityGroup securityGroup1=this.securityGroupRepository.save(securityGroup);
        return securityGroup1.getId();
    }

    /**
     * To generate security group name
     * @param userDTO
     * @return
     */
    private String generateGroupName(UserDTO userDTO) {
        log.info("Request to generate group name");
        String groupName= GROUP_NAME_PREFIX +UUID.randomUUID().toString();
        return groupName;
    }


    /**
     * To delete security group
     * @param securityGroups
     */
    @Transactional
    public void delete(Set<SecurityGroup> securityGroups) {
        log.info("Request to delete security groups from database");
        securityGroups.forEach(securityGroup -> {
            this.securityGroupRepository.delete(securityGroup);
        });
    }

    /**
     * To delete security group from ec2 instance
     * @param securityGroups
     */
    public void deleteSecurityGroup(Set<SecurityGroup> securityGroups) {
        log.info("Request to delete security groups from aws ec2 instance");
        final AmazonEC2 amazonEC2=AmazonEC2ClientBuilder.defaultClient();
        securityGroups.forEach(securityGroup -> {
            DeleteSecurityGroupRequest deleteSecurityGroupRequest=new DeleteSecurityGroupRequest()
                    .withGroupName(securityGroup.getName());
            DeleteSecurityGroupResult deleteSecurityGroupResult=amazonEC2.deleteSecurityGroup(deleteSecurityGroupRequest);
        });

    }


}
