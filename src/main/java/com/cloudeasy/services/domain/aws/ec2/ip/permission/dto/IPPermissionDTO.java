package com.cloudeasy.services.domain.aws.ec2.ip.permission.dto;

import com.cloudeasy.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;

@Data
public class IPPermissionDTO extends DataTransferObject {

    private Long id;
    private String protocol; //need to put default protocol here
    private String ipv4Address;
    private String ipv6Address;
    private Integer fromPort; //need to put default fromPort here
    private Integer toPort; //need to put default toPort here

}
