package com.cloudeasy.services.domain.aws.ec2.instance.configure.dto;

import com.cloudeasy.dto.DataTransferObject;
import com.cloudeasy.enums.compilerinterpreter.CompilerInterpreterEnum;
import com.cloudeasy.enums.db.DatabaseEnum;
import lombok.Data;

import java.util.List;

/**
 * DTO represents instance configuration class
 */
@Data
public class InstanceConfigurationDTO extends DataTransferObject {

    private DatabaseEnum database;
    private CompilerInterpreterEnum compilerInterpreter;
    private Long userId;
    private Long ec2InstanceId;

}
