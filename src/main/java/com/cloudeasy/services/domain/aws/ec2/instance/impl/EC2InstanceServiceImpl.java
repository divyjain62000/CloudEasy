package com.cloudeasy.services.domain.aws.ec2.instance.impl;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.waiters.*;
import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import com.cloudeasy.domain.aws.ec2.instance.data.analysis.SuccessFailureData;
import com.cloudeasy.domain.aws.ec2.ip.permission.IPPermission;
import com.cloudeasy.domain.aws.ec2.keypair.KeyPair;
import com.cloudeasy.domain.aws.ec2.security.group.SecurityGroup;
import com.cloudeasy.domain.os.OperatingSystem;
import com.cloudeasy.domain.user.User;
import com.cloudeasy.enums.email.subject.EmailSubject;
import com.cloudeasy.enums.error.EC2InstanceError;
import com.cloudeasy.enums.error.ErrorFor;
import com.cloudeasy.enums.instance.InstanceDetailsEnum;
import com.cloudeasy.enums.instance.status.InstanceStatus;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.repository.aws.ec2.instance.EC2InstanceRepository;
import com.cloudeasy.services.domain.aws.ec2.instance.EC2InstanceService;
import com.cloudeasy.services.domain.aws.ec2.instance.configure.InstanceConfigureService;
import com.cloudeasy.services.domain.aws.ec2.instance.configure.dto.InstanceConfigurationDTO;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.SuccessFailureDataService;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceDTO;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceRequestDTO;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceResponseDTO;
import com.cloudeasy.services.domain.aws.ec2.instance.mapper.EC2InstanceMapper;
import com.cloudeasy.services.domain.aws.ec2.ip.permission.IPPermissionService;
import com.cloudeasy.services.domain.aws.ec2.ip.permission.dto.IPPermissionDTO;
import com.cloudeasy.services.domain.aws.ec2.keypair.KeyPairService;
import com.cloudeasy.services.domain.aws.ec2.keypair.dto.KeyPairDTO;
import com.cloudeasy.services.domain.aws.ec2.security.group.SecurityGroupService;
import com.cloudeasy.services.domain.aws.ec2.security.group.dto.SecurityGroupDTO;
import com.cloudeasy.services.domain.user.UserService;
import com.cloudeasy.services.domain.user.dto.UserDTO;
import com.cloudeasy.services.mail.EmailSenderService;
import com.cloudeasy.services.mail.dto.MailDTO;
import com.cloudeasy.services.os.OperatingSystemService;
import com.cloudeasy.services.os.dto.OperatingSystemResponseDTO;
import com.cloudeasy.services.utils.encryption.EncryptionUtility;
import com.cloudeasy.services.utils.password.PasswordGenerator;
import com.jcraft.jsch.JSchException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

/**
 * Class implements EC2Service
 */
@Service
@Slf4j
public class EC2InstanceServiceImpl implements EC2InstanceService {

    @Autowired
    private SecurityGroupService securityGroupService;

    @Autowired
    private IPPermissionService ipPermissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private KeyPairService keyPairService;

    @Autowired
    private EC2InstanceRepository ec2InstanceRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private OperatingSystemService operatingSystemService;

    @Autowired
    private SuccessFailureDataService successFailureDataService;

    @Autowired
    private InstanceConfigureService instanceConfigureService;

    /**
     * To create EC2 instance
     * @param ec2InstanceRequestDTO
     * @return boolean
     * @throws CustomException
     * @throws IOException
     */
    public boolean createEC2Instance(EC2InstanceRequestDTO ec2InstanceRequestDTO) throws CustomException, IOException, JSchException {

        log.info("Request to create EC2 Instance");

        UserDTO userDTO=this.userService.findById(ec2InstanceRequestDTO.getUserId());

        if(userDTO==null) {
            throw new CustomException("Unable to create instance");
        }


        SecurityGroup securityGroup=securityGroupService.createSecurityGroup(userDTO);

        IPPermission ipPermission=ipPermissionService.createIPPermission(securityGroup.getName());

        KeyPair keyPair=keyPairService.createKeyPair(userDTO);

        EC2InstanceDTO ec2InstanceDTO=prepareDataToConfigureEC2Instance(ec2InstanceRequestDTO,securityGroup,ipPermission,keyPair,userDTO);

        String publicIPv4Address=launchInstance(ec2InstanceDTO);
        ipPermission.setIpv4Address(publicIPv4Address);
        this.ipPermissionService.save(ipPermission);

        this.successFailureDataService.successCounter(userDTO.getId());

        log.info("EC2 Instance created successfully");
        return true;
    }


    /**
     * To launch ec2 instance
     * @param ec2InstanceDTO
     */
    private String launchInstance(EC2InstanceDTO ec2InstanceDTO) throws JSchException, CustomException, IOException {
        log.info("Request to launch EC2 Instance");
        final AmazonEC2 amazonEC2= AmazonEC2ClientBuilder.defaultClient();
        RunInstancesRequest runInstancesRequest=new RunInstancesRequest();
        runInstancesRequest.setImageId(ec2InstanceDTO.getOperatingSystemResponseDTO().getImageId());
        runInstancesRequest.setInstanceType(ec2InstanceDTO.getOperatingSystemResponseDTO().getBelongsToInstanceType());
        runInstancesRequest.setKeyName(ec2InstanceDTO.getKeyPairDTO().getKeyName());

        Set<SecurityGroupDTO> securityGroupDTOSet=ec2InstanceDTO.getSecurityGroups();
        List<String> securityGroupList=new ArrayList<>();

        securityGroupDTOSet.forEach((sg)->{
            securityGroupList.add(sg.getName());
        });
        runInstancesRequest.setSecurityGroups(securityGroupList);

        runInstancesRequest.setMinCount(ec2InstanceDTO.getMinCount());
        runInstancesRequest.setMaxCount(ec2InstanceDTO.getMaxCount());

        RunInstancesResult runInstancesResult=amazonEC2.runInstances(runInstancesRequest);

        String instanceId=runInstancesResult.getReservation().getInstances().get(0).getInstanceId();

        Waiter waiter=amazonEC2.waiters().instanceRunning();

        waiter.run(new WaiterParameters(new DescribeInstancesRequest()
                .withInstanceIds(instanceId)));

        log.debug("Run Instances Result: "+runInstancesResult);

        String publicIPv4Address=this.ipPermissionService.findPublicIPv4AddressByInstanceIdFromAwsEC2(instanceId);

        ec2InstanceDTO.setInstanceId(instanceId);
        save(ec2InstanceDTO);
        log.info("EC2 Instance saved successfully in DB");
        return publicIPv4Address;
    }


    /**
     * To save EC2Instance
     * @param ec2InstanceDTO
     */
    public void save(EC2InstanceDTO ec2InstanceDTO) throws JSchException, CustomException, IOException {
        log.info("Request to save EC2 Instance in DB");

        ModelMapper mapper=new ModelMapper();
        EC2Instance ec2Instance=mapper.map(ec2InstanceDTO,EC2Instance.class);

        String generatedPassword= PasswordGenerator.generatePassword();
        String passwordKey = EncryptionUtility.getKey();
        String encryptedPassword = EncryptionUtility.encrypt(generatedPassword, passwordKey);
        ec2Instance.setPasswordKey(passwordKey);
        ec2Instance.setPassword(encryptedPassword);

        Set<SecurityGroup> securityGroupSet=new HashSet<>();
        ec2InstanceDTO.getSecurityGroups().forEach((sg)->{
            SecurityGroup securityGroup=mapper.map(sg,SecurityGroup.class);
            securityGroupSet.add(securityGroup);
        });
        ec2Instance.setSecurityGroups(securityGroupSet);

        User user=mapper.map(ec2InstanceDTO.getUserDTO(),User.class);
        ec2Instance.setUser(user);

        IPPermission ipPermission=mapper.map(ec2InstanceDTO.getIpPermissionDTO(),IPPermission.class);
        ec2Instance.setIpPermission(ipPermission);

        KeyPair keyPair=mapper.map(ec2InstanceDTO.getKeyPairDTO(),KeyPair.class);
        ec2Instance.setKeyPair(keyPair);

        OperatingSystem operatingSystem=mapper.map(ec2InstanceDTO.getOperatingSystemResponseDTO(),OperatingSystem.class);
        ec2Instance.setOperatingSystem(operatingSystem);

        ec2Instance.setInstanceStatus(InstanceStatus.RUNNING);

        ec2Instance.setSoftwaresInstalled(ec2InstanceDTO.getSoftwaresInstalled());

        ec2Instance=this.ec2InstanceRepository.save(ec2Instance);

        //configure instance once it created
//        InstanceConfigurationDTO instanceConfigurationDTO=new InstanceConfigurationDTO();
//        instanceConfigurationDTO.setEc2InstanceId(ec2Instance.getId());
//        this.instanceConfigureService.initConfigureInstance(instanceConfigurationDTO);

        //send email notification after instance created
        sendInstanceCreatedEmailNotification(ec2Instance,ec2InstanceDTO.getUserDTO());
    }


    /**
     * To send email notification after instance created
     * @param ec2Instance
     */
    private void sendInstanceCreatedEmailNotification(EC2Instance ec2Instance,UserDTO userDTO) {
        log.info("Request to helper method to send EC2 instance created email notification");
        MailDTO mailDTO=new MailDTO();
        List<String> to=new LinkedList<>();
        to.add(userDTO.getEmailId());
        mailDTO.setTo(to);
        mailDTO.setSubject(EmailSubject.INSTANCE_CREATED.getEmailSubject());
        Map<String,Object> emailMap=new HashMap<>();
        emailMap.put("firstName",userDTO.getFirstName());
//        String instancePassword=EncryptionUtility.decrypt(ec2Instance.getPassword(),ec2Instance.getPasswordKey());
  //      emailMap.put("password",instancePassword);
        mailDTO.setEmailMap(emailMap);
        mailDTO.setAttachedFilename(InstanceDetailsEnum.INSTANCE_ID_PREFIX.getDetail()+ec2Instance.getId()+InstanceDetailsEnum.INSTANCE_FILE_EXTENSION.getDetail());
        mailDTO.setOriginalFilename(ec2Instance.getKeyPair().getPemFilePath());
        emailSenderService.sendInstanceCreatedNotification(mailDTO);
        log.info("Notification send successfully");
    }


    /**
     * To send email notification after instance terminated
     * @param ec2Instance
     */
    private void sendInstanceTerminatedEmailNotification(EC2Instance ec2Instance,UserDTO userDTO) {
        log.info("Request to helper method to send EC2 instance terminaed email notification");
        MailDTO mailDTO=new MailDTO();
        List<String> to=new LinkedList<>();
        to.add(userDTO.getEmailId());
        mailDTO.setTo(to);
        mailDTO.setSubject(EmailSubject.INSTANCE_TERMINATED.getEmailSubject());
        Map<String,Object> emailMap=new HashMap<>();
        emailMap.put("firstName",userDTO.getFirstName());
        emailMap.put("instanceId",InstanceDetailsEnum.INSTANCE_ID_PREFIX.getDetail()+ec2Instance.getId());
        mailDTO.setEmailMap(emailMap);
        emailSenderService.sendInstanceTerminatedNotification(mailDTO);
        log.info("Notification send successfully");
    }


    /**
     * To start EC2 instance
     * @param id
     * @return String
     */
    public String startEC2Instance(Long id) throws CustomException {
        log.info("Request to start ec2 instance");
        EC2Instance ec2Instance=this.findById(id);
        final AmazonEC2 amazonEC2=AmazonEC2ClientBuilder.defaultClient();
        StartInstancesRequest startInstancesRequest=new StartInstancesRequest()
                .withInstanceIds(ec2Instance.getInstanceId());
        StartInstancesResult startInstancesResult=amazonEC2.startInstances(startInstancesRequest);

        Waiter waiter=amazonEC2.waiters().instanceRunning();

        waiter.run(new WaiterParameters(new DescribeInstancesRequest()
                .withInstanceIds(ec2Instance.getInstanceId())));

        String publicIPv4Address=this.ipPermissionService.findPublicIPv4AddressByInstanceIdFromAwsEC2(ec2Instance.getInstanceId());
        IPPermission ipPermission=ec2Instance.getIpPermission();
        ipPermission.setIpv4Address(publicIPv4Address);
        this.ipPermissionService.save(ipPermission);


        ec2Instance.setInstanceStatus(InstanceStatus.RUNNING);
        this.ec2InstanceRepository.save(ec2Instance);
        this.successFailureDataService.successCounter(ec2Instance.getUser().getId());

        return publicIPv4Address;
    }


    /**
     * To stop EC2 instance
     * @param id
     */
    public void stopEC2Instance(Long id) throws CustomException {
        log.info("Request to stop ec2 instance");
        EC2Instance ec2Instance=this.findById(id);
        final AmazonEC2 amazonEC2=AmazonEC2ClientBuilder.defaultClient();
        StopInstancesRequest stopInstancesRequest=new StopInstancesRequest()
                .withInstanceIds(ec2Instance.getInstanceId());
        amazonEC2.stopInstances(stopInstancesRequest);

        Waiter waiter=amazonEC2.waiters().instanceStopped();

        waiter.run(new WaiterParameters(new DescribeInstancesRequest()
                .withInstanceIds(ec2Instance.getInstanceId())));

        ec2Instance.setInstanceStatus(InstanceStatus.STOPPED);
        this.ec2InstanceRepository.save(ec2Instance);
        this.successFailureDataService.successCounter(ec2Instance.getUser().getId());

    }


    /**
     * To reboot ec2 instance
     * @param id
     * @throws CustomException
     */
    public void rebootEC2Instance(Long id) throws CustomException{
        log.info("Request to reboot ec2 instance");
        final AmazonEC2 amazonEC2=AmazonEC2ClientBuilder.defaultClient();

        EC2Instance ec2Instance=this.findById(id);

        RebootInstancesRequest rebootInstancesRequest=new RebootInstancesRequest()
                .withInstanceIds(ec2Instance.getInstanceId());
        RebootInstancesResult rebootInstancesResult=amazonEC2.rebootInstances(rebootInstancesRequest);

        Waiter waiter=amazonEC2.waiters().instanceRunning();

        waiter.run(new WaiterParameters(new DescribeInstancesRequest()
                .withInstanceIds(ec2Instance.getInstanceId())));
        this.successFailureDataService.successCounter(ec2Instance.getUser().getId());

    }

    /**
     * To terminate ec2 instance
     * @param id
     * @throws CustomException
     */
    public void terminateEC2Instance(Long id) throws CustomException {
        log.info("Request to terminate ec2 instance");
        final AmazonEC2 amazonEC2=AmazonEC2ClientBuilder.defaultClient();
        EC2Instance ec2Instance=this.findById(id);

        TerminateInstancesRequest terminateInstancesRequest=new TerminateInstancesRequest()
                .withInstanceIds(ec2Instance.getInstanceId());
        TerminateInstancesResult terminateInstancesResult=amazonEC2.terminateInstances(terminateInstancesRequest);

        Waiter waiter=amazonEC2.waiters().instanceTerminated();

        waiter.run(new WaiterParameters(new DescribeInstancesRequest()
                .withInstanceIds(ec2Instance.getInstanceId())));


        //to delete security group and key pair from aws ec2 instance
        this.securityGroupService.deleteSecurityGroup(ec2Instance.getSecurityGroups());
        this.keyPairService.deleteKeyPair(ec2Instance.getKeyPair());

        //delete ec2 instance, key pair, security group, ip permission from database
        this.delete(ec2Instance);

        this.successFailureDataService.successCounter(ec2Instance.getUser().getId());

    }

    /**
     * To find EC2 instance by id
     * @param id
     * @return EC2Instance
     * @throws CustomException
     */
    public EC2Instance findById(Long id) throws CustomException {
        log.info("Request to find ec2 instance by id: "+id);
        CustomException customException=new CustomException();
        if(id==null) {
            customException.addException(ErrorFor.ID_ERR.getErrorFor(), EC2InstanceError.ID_REQUIRED.getEc2InstanceError());
        }
        Optional<EC2Instance> ec2Instance=this.ec2InstanceRepository.findById(id);
        if(ec2Instance.isPresent()) {
            return ec2Instance.get();
        }else {
            customException.addException(ErrorFor.ID_ERR.getErrorFor(),EC2InstanceError.INVALID_ID.getEc2InstanceError());
        }
        if(customException.getExceptions().size()>0) throw customException;
        return null;
    }

    /**
     * Prepare data to configuration ec2 instance
     * @param ec2InstanceRequestDTO
     * @return EC2InstanceDTO
     */
    private EC2InstanceDTO prepareDataToConfigureEC2Instance(EC2InstanceRequestDTO ec2InstanceRequestDTO,SecurityGroup securityGroup,
                                                             IPPermission ipPermission,KeyPair keyPair,UserDTO userDTO) {

        log.info("Request to prepare data to configure EC2 instance");

        ModelMapper mapper=new ModelMapper();
        EC2InstanceDTO ec2InstanceDTO=new EC2InstanceDTO();
        ec2InstanceDTO.setMinCount(1); // currently we are hard coding later on we will change it
        ec2InstanceDTO.setMaxCount(1); // currently we are hard coding later on we will change it

        SecurityGroupDTO securityGroupDTO=mapper.map(securityGroup,SecurityGroupDTO.class);

        Set<SecurityGroupDTO> securityGroups=new HashSet<>();
        securityGroups.add(securityGroupDTO);
        ec2InstanceDTO.setSecurityGroups(securityGroups);

        KeyPairDTO keyPairDTO=mapper.map(keyPair,KeyPairDTO.class);
        ec2InstanceDTO.setKeyPairDTO(keyPairDTO);

        IPPermissionDTO ipPermissionDTO=mapper.map(ipPermission,IPPermissionDTO.class);
        ec2InstanceDTO.setIpPermissionDTO(ipPermissionDTO);

        OperatingSystemResponseDTO operatingSystemResponseDTO=this.operatingSystemService.findById(ec2InstanceRequestDTO.getOperatingSystemId());
        ec2InstanceDTO.setOperatingSystemResponseDTO(operatingSystemResponseDTO);

        ec2InstanceDTO.setUserDTO(userDTO);
        return ec2InstanceDTO;
    }


    /**
     * To delete instance
     * @param ec2Instance
     */
    @Transactional
    public void delete(EC2Instance ec2Instance) {
        log.info("Request to delete EC2 instance from database");
        this.ec2InstanceRepository.delete(ec2Instance);
        this.securityGroupService.delete(ec2Instance.getSecurityGroups());
        this.ipPermissionService.delete(ec2Instance.getIpPermission());
        this.keyPairService.delete(ec2Instance.getKeyPair());
        ModelMapper mapper=new ModelMapper();
        sendInstanceTerminatedEmailNotification(ec2Instance,mapper.map(ec2Instance.getUser(),UserDTO.class));
    }


    /**
     * To get all ec2 instance by user id
     * @param userId
     * @return Page
     */
    public List<EC2InstanceResponseDTO> findAllEC2InstanceByUserId(Long userId) {
        List<EC2Instance> ec2Instances=this.ec2InstanceRepository.findByUserId(userId);
        List<EC2InstanceResponseDTO> ec2InstanceResponseDTOList=new ArrayList<>();
        ModelMapper mapper=new ModelMapper();
        ec2Instances.forEach(ec2Instance -> {
            EC2InstanceResponseDTO ec2InstanceResponseDTO= EC2InstanceMapper.entityToResponseDTO(ec2Instance);
            ec2InstanceResponseDTOList.add(ec2InstanceResponseDTO);
        });
        return ec2InstanceResponseDTOList;
    }


}
