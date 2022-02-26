package com.cloudeasy.services.domain.aws.ec2.instance.dto;

import com.cloudeasy.dto.DataTransferObject;
import lombok.Data;

import java.util.Set;

@Data
public class EC2InstanceResponseDTO extends DataTransferObject {

    private Long id;
    private String status;
    private Long userId;
    private String operatingSystem;
    private String instanceId;
    private String IPv4Address;
    private Set<String> softwaresInstalled;

}
