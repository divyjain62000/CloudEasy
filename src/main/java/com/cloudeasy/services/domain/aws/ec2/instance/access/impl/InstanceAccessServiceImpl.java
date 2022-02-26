package com.cloudeasy.services.domain.aws.ec2.instance.access.impl;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import com.cloudeasy.domain.aws.ec2.keypair.KeyPair;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.services.domain.aws.ec2.instance.EC2InstanceService;
import com.cloudeasy.services.domain.aws.ec2.instance.access.InstanceAccessService;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;


@Service
@Slf4j
public class InstanceAccessServiceImpl implements InstanceAccessService {

    @Autowired
    private EC2InstanceService ec2InstanceService;

    private final static int PORT_NUMBER=22;
    private final static String APT_UPDATE="sudo apt-get update";
    private final static String HOST_CHECKING_TYPE="StrictHostKeyChecking";
    private final static String HOST_CHECKING_VALUE="no";
    private final static String OPEN_CHANNEL_TYPE="sftp";
    private final static String OPEN_CHANNEL_TYPE_2="exec";
    private static final String DESTINATION_PATH ="/home";
    private static final String DIRECTORY="cloudeasy";




    /**
     * To upload file on ec2 instance
     * @param file
     * @param instanceId
     * @return boolean
     */
    public boolean uploadFile(MultipartFile file, Long instanceId) throws IOException, JSchException, CustomException, SftpException {
        InputStream inputStream=file.getInputStream();
        JSch jsch=new JSch();
        EC2Instance ec2Instance=this.ec2InstanceService.findById(instanceId);
        KeyPair keyPair=ec2Instance.getKeyPair();
        jsch.addIdentity(keyPair.getPemFilePath());
        String username=ec2Instance.getOperatingSystem().getUsername();
        String serverIp=ec2Instance.getIpPermission().getIpv4Address();
        Session session=jsch.getSession(username,serverIp,PORT_NUMBER);
        Properties properties=new Properties();
        properties.put(HOST_CHECKING_TYPE,HOST_CHECKING_VALUE);
        session.setConfig(properties);
        session.connect();
        log.info("Connected");

        byte[] content=inputStream.readAllBytes();


        ChannelSftp channelSftp= (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelSftp.put(new ByteArrayInputStream(content),file.getOriginalFilename());

        log.info("Upload complete");
        channelSftp.exit();
        session.disconnect();
        return true;
    }


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
    public ByteArrayInputStream downloadFile(Long instanceId, String filePath) throws IOException, JSchException, CustomException, SftpException {

        JSch jsch=new JSch();
        EC2Instance ec2Instance=this.ec2InstanceService.findById(instanceId);
        KeyPair keyPair=ec2Instance.getKeyPair();
        jsch.addIdentity(keyPair.getPemFilePath());
        String username=ec2Instance.getOperatingSystem().getUsername();
        String serverIp=ec2Instance.getIpPermission().getIpv4Address();
        Session session=jsch.getSession(username,serverIp,PORT_NUMBER);
        Properties properties=new Properties();
        properties.put(HOST_CHECKING_TYPE,HOST_CHECKING_VALUE);
        session.setConfig(properties);
        session.connect();
        log.info("Connected");
        ChannelSftp channelSftp= (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        InputStream inputStream=channelSftp.get(filePath);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(channelSftp.get(filePath));
        byte[] content=bis.readAllBytes();
        bis.close();
        log.info("Download completed");
        channelSftp.exit();
        session.disconnect();
        return new ByteArrayInputStream(content);
    }


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
    @Override
    public boolean uploadStaticWebsite(MultipartFile file, Long instanceId) throws IOException, JSchException, CustomException, SftpException {
        InputStream inputStream=file.getInputStream();
        JSch jsch=new JSch();
        EC2Instance ec2Instance=this.ec2InstanceService.findById(instanceId);
        KeyPair keyPair=ec2Instance.getKeyPair();
        jsch.addIdentity(keyPair.getPemFilePath());
        String username=ec2Instance.getOperatingSystem().getUsername();
        String serverIp=ec2Instance.getIpPermission().getIpv4Address();
        Session session=jsch.getSession(username,serverIp,PORT_NUMBER);
        Properties properties=new Properties();
        properties.put(HOST_CHECKING_TYPE,HOST_CHECKING_VALUE);
        session.setConfig(properties);
        session.connect();
        log.info("Connected");

        byte[] content=inputStream.readAllBytes();

        ChannelSftp channelSftp= (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelSftp.put(new ByteArrayInputStream(content),file.getOriginalFilename());

        log.info("Upload complete");
        channelSftp.exit();
        channelSftp.disconnect();

        Channel channel=session.openChannel(OPEN_CHANNEL_TYPE_2);
        ((ChannelExec) channel).setCommand("sudo unzip "+file.getOriginalFilename()+" -d /var/www/html/");
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);

        InputStream in = channel.getInputStream();
        channel.connect();
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                System.out.print(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                System.out.println("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
            }
        }
        channel.disconnect();

        session.disconnect();
        return true;
    }



}
