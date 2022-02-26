package com.cloudeasy.services.domain.aws.ec2.instance.configure.impl;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import com.cloudeasy.domain.aws.ec2.keypair.KeyPair;
import com.cloudeasy.enums.compilerinterpreter.CompilerInterpreterEnum;
import com.cloudeasy.enums.db.DatabaseEnum;
import com.cloudeasy.enums.error.EC2InstanceError;
import com.cloudeasy.enums.error.ErrorFor;
import com.cloudeasy.enums.instance.status.InstanceStatus;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.repository.aws.ec2.instance.EC2InstanceRepository;
import com.cloudeasy.services.domain.aws.ec2.instance.EC2InstanceService;
import com.cloudeasy.services.domain.aws.ec2.instance.configure.InstanceConfigureService;
import com.cloudeasy.services.domain.aws.ec2.instance.configure.dto.InstanceConfigurationDTO;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.SuccessFailureDataService;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Service
@Slf4j
public class InstanceConfigureServiceImpl implements InstanceConfigureService {

    @Autowired
    private EC2InstanceService ec2InstanceService;

    @Autowired
    private EC2InstanceRepository ec2InstanceRepository;

    @Autowired
    private SuccessFailureDataService successFailureDataService;

    private final static int PORT_NUMBER=22;
    private final static String APT_UPDATE="sudo apt-get update";
    private final static String HOST_CHECKING_TYPE="StrictHostKeyChecking";
    private final static String HOST_CHECKING_VALUE="no";
    private final static String OPEN_CHANNEL_TYPE="exec";


    /**
     * To get all databases
     * @return map
     */
    @Override
    public Map<String,String> getAllDatabase() {
        Map<String,String> databases=new HashMap<>();
        for(DatabaseEnum database: DatabaseEnum.values()) {
            databases.put(database.name(),database.getDatabaseName());
        }
        return databases;
    }

    /**
     * To get all compilers and interpreters
     * @return map
     */
    @Override
    public Map<String,String> getAllCompilerAndInterpreter() {
        Map<String,String> compilerInterpreters=new HashMap<>();
        for(CompilerInterpreterEnum compilerInterpreter: CompilerInterpreterEnum.values()) {
            compilerInterpreters.put(compilerInterpreter.name(),compilerInterpreter.getCompilerInterpreterName());
        }
        return compilerInterpreters;
    }


    /**
     * To install database
     * @param instanceConfigurationDTO
     * @throws CustomException
     */
    @Override
    public void installDatabases(InstanceConfigurationDTO instanceConfigurationDTO) throws CustomException, JSchException, IOException {
        log.debug("Request to install database");
        EC2Instance ec2Instance=this.ec2InstanceService.findById(instanceConfigurationDTO.getEc2InstanceId());
        if(ec2Instance.getInstanceStatus()!= InstanceStatus.RUNNING) {
            CustomException customException=new CustomException();
            customException.addException(ErrorFor.INSTANCE_ERR.getErrorFor(), EC2InstanceError.SERVER_IS_STOP.getEc2InstanceError());
            throw customException;
        }
        try {
            JSch jsch = new JSch();

            KeyPair keyPair = ec2Instance.getKeyPair();
            jsch.addIdentity(keyPair.getPemFilePath());
            String username = ec2Instance.getOperatingSystem().getUsername();
            String serverIp = ec2Instance.getIpPermission().getIpv4Address();
            Session session = jsch.getSession(username, serverIp, PORT_NUMBER);
            Properties properties = new Properties();
            properties.put(HOST_CHECKING_TYPE, HOST_CHECKING_VALUE);
            session.setConfig(properties);
            session.connect();
            log.debug("Connected");

            List<String> commands = new ArrayList<>();
            switch (instanceConfigurationDTO.getDatabase()) {
                case MySQL:
                    commands.add(APT_UPDATE);
                    commands.add("sudo apt-get -y install mysql-server");
                    break;
                case MONGO_DB:
                    commands.add(APT_UPDATE);
                    commands.add("sudo apt install -y mongodb");
                    break;
                case POSTGRE_SQL:
                    commands.add(APT_UPDATE);
                    commands.add("sudo apt-get -y install postgresql postgresql-contrib");
                    break;
                case MARIA_DB:
                    commands.add(APT_UPDATE);
                    commands.add("sudo apt install -y mariadb-server");
                    break;

            }

            installTool(commands, session); // to install tool
            session.disconnect();
            ec2Instance.getSoftwaresInstalled().add(instanceConfigurationDTO.getDatabase().getDatabaseName());
            this.ec2InstanceRepository.save(ec2Instance);
            log.debug("Database Installed");
        } catch (Exception exception) {
            successFailureDataService.failureCounter(ec2Instance.getUser().getId());
            throw exception;
        }
    }


    /**
     * To install compilers and interpreters
     * @param instanceConfigurationDTO
     * @throws CustomException
     */
    @Override
    public void installCompilersAndInterpreters(InstanceConfigurationDTO instanceConfigurationDTO) throws CustomException, JSchException, IOException {
        log.debug("Request to install compiler and interpreter");
        EC2Instance ec2Instance = this.ec2InstanceService.findById(instanceConfigurationDTO.getEc2InstanceId());
        if(ec2Instance.getInstanceStatus()!= InstanceStatus.RUNNING) {
            log.debug("Instance not running");
            CustomException customException=new CustomException();
            customException.addException(ErrorFor.INSTANCE_ERR.getErrorFor(), EC2InstanceError.SERVER_IS_STOP.getEc2InstanceError());
            throw customException;
        }
        try {
            JSch jsch = new JSch();
            KeyPair keyPair = ec2Instance.getKeyPair();
            jsch.addIdentity(keyPair.getPemFilePath());
            String username = ec2Instance.getOperatingSystem().getUsername();
            String serverIp = ec2Instance.getIpPermission().getIpv4Address();
            Session session = jsch.getSession(username, serverIp, PORT_NUMBER);
            Properties properties = new Properties();
            properties.put(HOST_CHECKING_TYPE, HOST_CHECKING_VALUE);
            session.setConfig(properties);
            session.connect();
            log.debug("Connected");

            List<String> commands = new ArrayList<>();
            switch (instanceConfigurationDTO.getCompilerInterpreter()) {
                case C_AND_CPP:
                    commands.add(APT_UPDATE);
                    commands.add("sudo apt-get -y install build-essential");
                    commands.add("sudo apt-get -y install manpages-dev");
                    break;
                case JAVA8:
                    commands.add(APT_UPDATE);
                    commands.add("sudo apt-get -y install openjdk-8-jdk openjdk-8-jre");
                    break;
                case OPEN_JDK:
                    commands.add(APT_UPDATE);
                    commands.add("sudo apt install -y default-jdk");
                    commands.add("sudo apt install -y openjdk-11-jdk-headless");
                    break;
                case PYTHON:
                    commands.add(APT_UPDATE);
                    commands.add("sudo apt -y upgrade");
                    commands.add("sudo apt install -y python3-pip");
                    break;
                case NODE_JS:
                    commands.add(APT_UPDATE);
                    commands.add("sudo apt install -y nodejs");
                    break;

            }


            installTool(commands, session); // to install tool
            session.disconnect();
            ec2Instance.getSoftwaresInstalled().add(instanceConfigurationDTO.getCompilerInterpreter().getCompilerInterpreterName());
            this.ec2InstanceRepository.save(ec2Instance);
            log.debug("Compiler or Interpreter Installed");
        } catch (Exception exception) {
            successFailureDataService.failureCounter(ec2Instance.getUser().getId());
            throw exception;
        }

    }

    /**
     * To configure instance while we create
     * @param instanceConfigurationDTO
     * @throws CustomException
     * @return
     */
    @Override
    public boolean initConfigureInstance(InstanceConfigurationDTO instanceConfigurationDTO) throws CustomException, JSchException, IOException {
        log.debug("Request to configure ec2 instance");
        EC2Instance ec2Instance = this.ec2InstanceService.findById(instanceConfigurationDTO.getEc2InstanceId());
        if (ec2Instance.getInstanceStatus() != InstanceStatus.RUNNING) {
            log.debug("Instance not running");
            CustomException customException = new CustomException();
            customException.addException(ErrorFor.INSTANCE_ERR.getErrorFor(), EC2InstanceError.SERVER_IS_STOP.getEc2InstanceError());
            throw customException;
        }
        try {
            JSch jsch = new JSch();
            KeyPair keyPair = ec2Instance.getKeyPair();
            jsch.addIdentity(keyPair.getPemFilePath());
            String username = ec2Instance.getOperatingSystem().getUsername();
            String serverIp = ec2Instance.getIpPermission().getIpv4Address();
            Session session = jsch.getSession(username, serverIp, PORT_NUMBER);
            Properties properties = new Properties();
            properties.put(HOST_CHECKING_TYPE, HOST_CHECKING_VALUE);
            session.setConfig(properties);
            session.connect();
            log.debug("Connected");

            List<String> commands = new ArrayList<>();
            commands.add(APT_UPDATE);
            commands.add("sudo apt -y install apache2");
            commands.add(APT_UPDATE);
            commands.add("sudo apt -y install zip unzip");
            commands.add("sudo rm /var/www/html/index.html");

            installTool(commands, session); // to install tool
            session.disconnect();
            log.debug("Compiler or Interpreter Installed");
            return true;
        } catch (Exception exception) {
            successFailureDataService.failureCounter(ec2Instance.getUser().getId());
            throw exception;
        }
    }






    /**
     * To install tool
     * @param commands
     */
    private void installTool(List<String> commands,Session session) throws IOException, JSchException {

        for(String command:commands) {
            Channel channel=session.openChannel(OPEN_CHANNEL_TYPE);
            ((ChannelExec) channel).setCommand(command);
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
        }

    }



}
