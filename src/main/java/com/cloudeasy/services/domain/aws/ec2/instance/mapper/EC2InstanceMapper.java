package com.cloudeasy.services.domain.aws.ec2.instance.mapper;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import com.cloudeasy.enums.instance.InstanceDetailsEnum;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceResponseDTO;

public class EC2InstanceMapper {

    public static EC2InstanceResponseDTO entityToResponseDTO(EC2Instance ec2Instance) {
        EC2InstanceResponseDTO ec2InstanceResponseDTO=new EC2InstanceResponseDTO();
        ec2InstanceResponseDTO.setId(ec2Instance.getId());
        ec2InstanceResponseDTO.setStatus(ec2Instance.getInstanceStatus().getStatus());
        ec2InstanceResponseDTO.setOperatingSystem(ec2Instance.getOperatingSystem().getImageOf());
        ec2InstanceResponseDTO.setUserId(ec2Instance.getUser().getId());
        ec2InstanceResponseDTO.setInstanceId(InstanceDetailsEnum.INSTANCE_ID_PREFIX.getDetail()+ec2Instance.getId());
        ec2InstanceResponseDTO.setIPv4Address(ec2Instance.getIpPermission().getIpv4Address());
        ec2InstanceResponseDTO.setSoftwaresInstalled(ec2Instance.getSoftwaresInstalled());
        return ec2InstanceResponseDTO;
    }

}
