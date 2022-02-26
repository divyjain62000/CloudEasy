package com.cloudeasy.services.domain.aws.ec2.instance.dto;

import com.cloudeasy.domain.aws.ec2.ip.permission.IPPermission;
import com.cloudeasy.domain.aws.ec2.keypair.KeyPair;
import com.cloudeasy.domain.aws.ec2.security.group.SecurityGroup;
import com.cloudeasy.domain.os.OperatingSystem;
import com.cloudeasy.domain.user.User;
import com.cloudeasy.dto.DataTransferObject;
import com.cloudeasy.services.domain.aws.ec2.ip.permission.dto.IPPermissionDTO;
import com.cloudeasy.services.domain.aws.ec2.keypair.dto.KeyPairDTO;
import com.cloudeasy.services.domain.aws.ec2.security.group.dto.SecurityGroupDTO;
import com.cloudeasy.services.domain.user.dto.UserDTO;
import com.cloudeasy.services.os.dto.OperatingSystemResponseDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
public class EC2InstanceDTO extends DataTransferObject {

    private Long id;
    private Integer minCount; //min number of instance needed
    private Integer maxCount; //max number of instance needed
    private Set<SecurityGroupDTO> securityGroups;
    private Long userId;
    private IPPermissionDTO ipPermissionDTO;
    private KeyPairDTO keyPairDTO;
    private OperatingSystemResponseDTO operatingSystemResponseDTO;
    private UserDTO userDTO;
    private String instanceId;
    Set<String> softwaresInstalled;

}
