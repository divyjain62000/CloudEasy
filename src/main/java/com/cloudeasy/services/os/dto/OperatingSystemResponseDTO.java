package com.cloudeasy.services.os.dto;

import com.cloudeasy.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;

@Data
public class OperatingSystemResponseDTO extends DataTransferObject {

    private Integer id;
    private String imageOf;
    private String imageId;
    private String username;
    private Integer ram;
    private Integer hdd;
    private String belongsToInstanceType;

}
