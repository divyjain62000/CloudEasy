package com.cloudeasy.services.domain.aws.ec2.instance.access;

import com.cloudeasy.exceptions.CustomException;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface InstanceAccessService {

    /**
     * To upload file
     * @param file
     * @param instanceId
     * @return boolean
     * @throws IOException
     * @throws JSchException
     * @throws CustomException
     * @throws SftpException
     */
    boolean uploadFile(MultipartFile file,Long instanceId) throws IOException, JSchException, CustomException, SftpException;

    /**
     * To download file
     * @param instanceId
     * @param filePath
     * @return ByteArrayInputStream
     * @throws IOException
     * @throws JSchException
     * @throws CustomException
     * @throws SftpException
     */
    ByteArrayInputStream downloadFile(Long instanceId, String filePath) throws IOException, JSchException, CustomException, SftpException;

    /**
     * To upload static web page
     * @param file
     * @param instanceId
     * @return boolean
     * @throws IOException
     * @throws JSchException
     * @throws CustomException
     * @throws SftpException
     */
    boolean uploadStaticWebsite(MultipartFile file,Long instanceId) throws IOException, JSchException, CustomException, SftpException;

}
