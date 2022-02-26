package com.cloudeasy.services.domain.aws.ec2.instance.configure;

import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.services.domain.aws.ec2.instance.configure.dto.InstanceConfigurationDTO;
import com.jcraft.jsch.JSchException;

import java.io.IOException;
import java.util.Map;

public interface InstanceConfigureService {

    /**
     * To get all databases
     * @return map
     */
    Map<String,String> getAllDatabase();

    /**
     * To get all compilers and interpreters
     * @return map
     */
    Map<String,String> getAllCompilerAndInterpreter();

    /**
     * To install database
     * @param instanceConfigurationDTO
     * @throws CustomException
     */
    void installDatabases(InstanceConfigurationDTO instanceConfigurationDTO) throws CustomException, JSchException, IOException;

    /**
     * To install compilers and interpreters
     * @param instanceConfigurationDTO
     * @throws CustomException
     */
    void installCompilersAndInterpreters(InstanceConfigurationDTO instanceConfigurationDTO) throws CustomException, JSchException, IOException;

    /**
     * To configure instance while we create
     * @param instanceConfigurationDTO
     * @throws CustomException
     * @return
     */
    boolean initConfigureInstance(InstanceConfigurationDTO instanceConfigurationDTO) throws CustomException, JSchException, IOException;

}
