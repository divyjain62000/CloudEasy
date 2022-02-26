package com.cloudeasy.services.domain.aws.ec2.security.group.dto;

import com.cloudeasy.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;

@Data
public class SecurityGroupDTO extends DataTransferObject {

    private Long id;
    private String name;
    private String description;
    private String securityGroupId;

}
