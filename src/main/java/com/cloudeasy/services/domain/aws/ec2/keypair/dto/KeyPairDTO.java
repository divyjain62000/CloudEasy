package com.cloudeasy.services.domain.aws.ec2.keypair.dto;

import com.cloudeasy.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;

@Data
public class KeyPairDTO extends DataTransferObject {

    private Long id;
    private String keyName;
    private String keyType;
    private String pemFilePath;

}
