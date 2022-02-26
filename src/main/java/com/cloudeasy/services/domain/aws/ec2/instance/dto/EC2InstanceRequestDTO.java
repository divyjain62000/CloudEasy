package com.cloudeasy.services.domain.aws.ec2.instance.dto;

import com.cloudeasy.dto.DataTransferObject;
import lombok.Data;

@Data
public class EC2InstanceRequestDTO extends DataTransferObject {

    private Long userId;
    private Integer operatingSystemId;

}
