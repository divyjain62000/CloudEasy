package com.cloudeasy.services.domain.aws.ec2.keypair.impl;

import com.amazonaws.services.cloudfront.model.DeleteKeyGroupRequest;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.DeleteKeyPairRequest;
import com.amazonaws.services.ec2.model.DeleteKeyPairResult;
import com.cloudeasy.domain.aws.ec2.keypair.KeyPair;
import com.cloudeasy.repository.aws.ec2.keypair.KeyPairRepository;
import com.cloudeasy.services.domain.aws.ec2.keypair.KeyPairService;
import com.cloudeasy.services.domain.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

@Service
@Slf4j
public class KeyPairServiceImpl implements KeyPairService {


    @Autowired
    private KeyPairRepository keyPairRepository;

    private static final String folderPath="/resorces/key-pair/";
    private static final String pemFileExtension=".pem";


    /**
     * To create key pair
     * @param userDTO
     * @return {@link KeyPair}
     * @throws IOException
     */
    @Override
    public KeyPair createKeyPair(UserDTO userDTO) throws IOException {
        log.info("Request to create Key Pair");
        AmazonEC2 amazonEC2= AmazonEC2ClientBuilder.defaultClient();
        CreateKeyPairRequest createKeyPairRequest=new CreateKeyPairRequest();
        String keyName=generateKeyName(userDTO);
        createKeyPairRequest.withKeyName(keyName);
        CreateKeyPairResult createKeyPairResult=amazonEC2.createKeyPair(createKeyPairRequest);
        com.amazonaws.services.ec2.model.KeyPair  keyPairAWS=createKeyPairResult.getKeyPair();
        String keyMaterial=keyPairAWS.getKeyMaterial();
        log.debug("-----------");
        log.debug("{}",keyMaterial);
        log.debug("-----------");
        KeyPair keyPair=new KeyPair();
        keyPair.setKeyName(keyName);
        keyPair=createAndStorePemFile(keyMaterial,userDTO,keyPair);
        keyPair.setKeyType(null);
        Long keyPairId=save(keyPair);
        keyPair.setId(keyPairId);
        log.info("Key Pair created successfully");
        return keyPair;
    }

    /**
     * To save key pair
     * @param keyPair
     * @return Long
     */
    private Long save(KeyPair keyPair) {
        log.info("Request to save Key Pair");
        KeyPair keyPair1=this.keyPairRepository.save(keyPair);
        return keyPair1.getId();
    }


    /**
     * To generate key name
     * @param userDTO
     * @return String
     */
    private String generateKeyName(UserDTO userDTO) {
        log.info("Request to generate key name");
        String keyName="";
        keyName=userDTO.getEmailId()+"_"+UUID.randomUUID().toString();
        return keyName;
    }


    /**
     * To create and store PEM file
     * @param keyMaterial
     * @param userDTO
     * @param keyPair
     * @return {@link KeyPair}
     * @throws IOException
     */
    private KeyPair createAndStorePemFile(String keyMaterial,UserDTO userDTO,KeyPair keyPair) throws IOException {
        log.info("Request to create and store pem file");
        String path=keyPair.getKeyName()+pemFileExtension;
        File file=new File(path);
        if(file.exists()) file.delete();
        RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
        randomAccessFile.writeBytes(keyMaterial);
        randomAccessFile.close();
        keyPair.setPemFilePath(path);
        return keyPair;
    }

    /**
     * To delete key pair
     * @param keyPair
     */
    public void delete(KeyPair keyPair) {
        log.info("Request to delete key pair from database");
        this.keyPairRepository.delete(keyPair);
        String pemFile=keyPair.getPemFilePath();
        File file=new File(pemFile);
        if(file.exists()) file.delete();
    }

    /**
     * To delete key pair from  aws ec2 instance
     * @param keyPair
     */
    public void deleteKeyPair(KeyPair keyPair) {
        log.info("Request to delete key pair from aws ec2 instance");
        final AmazonEC2 amazonEC2=AmazonEC2ClientBuilder.defaultClient();
        DeleteKeyPairRequest deleteKeyPairRequest=new DeleteKeyPairRequest()
                .withKeyName(keyPair.getKeyName());
        DeleteKeyPairResult deleteKeyPairResult=amazonEC2.deleteKeyPair(deleteKeyPairRequest);
    }






}
