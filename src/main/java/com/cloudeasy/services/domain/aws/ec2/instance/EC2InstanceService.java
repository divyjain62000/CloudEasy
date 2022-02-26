package com.cloudeasy.services.domain.aws.ec2.instance;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceDTO;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceRequestDTO;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceResponseDTO;
import com.jcraft.jsch.JSchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

/**
 * EC2Instance service interface
 */
public interface EC2InstanceService {

    /**
     * To create EC2 instance
     * @param ec2InstanceRequestDTO
     * @return boolean
     * @throws CustomException
     * @throws IOException
     */
    boolean createEC2Instance(EC2InstanceRequestDTO ec2InstanceRequestDTO) throws CustomException, IOException, JSchException;

    /**
     * To start EC2 instance
     * @param
     * @return String
     */
    String startEC2Instance(Long id) throws CustomException;

    /**
     * To stop EC2 instance
     * @param id
     */
    void stopEC2Instance(Long id) throws CustomException;


    /**
     * To reboot ec2 instance
     * @param id
     * @throws CustomException
     */
    void rebootEC2Instance(Long id) throws CustomException;


    /**
     * To terminate ec2 instance
     * @param id
     * @throws CustomException
     */
    void terminateEC2Instance(Long id) throws CustomException;


    /**
     * To find EC2 instance by id
     * @param id
     * @return EC2Instance
     * @throws CustomException
     */
    EC2Instance findById(Long id)  throws CustomException;

    /**
     * To delete instance
     * @param ec2Instance
     */
    void delete(EC2Instance ec2Instance);


    /**
     * To get all ec2 instance by user id
     * @param userId
     * @return Page
     */
    List<EC2InstanceResponseDTO> findAllEC2InstanceByUserId(Long userId);

    /**
     * To save ec2 instance
     * @param ec2InstanceDTO
     */
    void save(EC2InstanceDTO ec2InstanceDTO) throws JSchException, CustomException, IOException;


    }
