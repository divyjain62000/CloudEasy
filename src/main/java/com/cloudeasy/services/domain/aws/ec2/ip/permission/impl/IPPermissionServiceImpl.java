package com.cloudeasy.services.domain.aws.ec2.ip.permission.impl;

import com.amazonaws.services.connect.model.DescribeInstanceRequest;
import com.amazonaws.services.connect.model.DescribeInstanceResult;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.services.workspaces.model.DeleteIpGroupRequest;
import com.cloudeasy.domain.aws.ec2.ip.permission.IPPermission;
import com.cloudeasy.domain.aws.ec2.keypair.KeyPair;
import com.cloudeasy.repository.aws.ec2.ip.permission.IPPermissionRepository;
import com.cloudeasy.services.domain.aws.ec2.ip.permission.IPPermissionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IPPermissionServiceImpl implements IPPermissionService {

    @Autowired
    private IPPermissionRepository ipPermissionRepository;

    private static int PORT_NUMBER=22;
    private static String CIDR_IP="0.0.0.0/0";
    private static String IP_PROTOCOL="tcp";


    /**
     * To create IP permission
     * @param groupName
     * @return {@link IPPermission}
     */
    public IPPermission createIPPermission(String groupName) {
        log.info("Request to create IP Permission");
        AmazonEC2 amazonEC2= AmazonEC2ClientBuilder.defaultClient();
        IpRange ipRange=new IpRange().withCidrIp(CIDR_IP);
        IpPermission ipPermission1=new IpPermission()
                .withIpProtocol(IP_PROTOCOL)
                .withFromPort(PORT_NUMBER)
                .withToPort(PORT_NUMBER)
                .withIpv4Ranges(ipRange);
        IpPermission ipPermission2=new IpPermission()
                .withIpProtocol("tcp")
                .withFromPort(80)
                .withToPort(80)
                .withIpv4Ranges(ipRange);
        IpPermission ipPermission3=new IpPermission()
                .withIpProtocol("tcp")
                .withFromPort(443)
                .withToPort(443)
                .withIpv4Ranges(ipRange);
        AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest=new AuthorizeSecurityGroupIngressRequest()
                .withGroupName(groupName)
                .withIpPermissions(ipPermission1,ipPermission2,ipPermission3
                );
        AuthorizeSecurityGroupIngressResult authorizeSecurityGroupIngressResult=amazonEC2.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
        IPPermission ipPermissionRes=new IPPermission();
        ipPermissionRes.setProtocol(IP_PROTOCOL);
        ipPermissionRes.setIpv4Address(CIDR_IP);
        ipPermissionRes.setIpv6Address(null);
        ipPermissionRes.setFromPort(PORT_NUMBER);
        ipPermissionRes.setToPort(PORT_NUMBER);
        Long ipPermissionId=save(ipPermissionRes);
        ipPermissionRes.setId(ipPermissionId);
        log.info("IP Permission created successfully");
        return ipPermissionRes;
    }


    /**
     * To save ip permission
     * @param ipPermission
     * @return Long
     */
    public Long save(IPPermission ipPermission) {
        log.info("Request to save IP Permission in DB");
        IPPermission ipPermission1=this.ipPermissionRepository.save(ipPermission);
        return ipPermission1.getId();
    }


    /**
     * To get public ipv4 address by instance id from aws ec2
     * @param instanceId
     * @return String
     */
    public String findPublicIPv4AddressByInstanceIdFromAwsEC2(String instanceId){
        String publicIpv4Address="";
        AmazonEC2 amazonEC2=AmazonEC2ClientBuilder.defaultClient();
        DescribeInstancesRequest describeInstancesRequest=new DescribeInstancesRequest()
                .withInstanceIds(instanceId);
        DescribeInstancesResult describeInstancesResult=amazonEC2.describeInstances(describeInstancesRequest);
        Reservation reservation=describeInstancesResult.getReservations().get(0);
        Instance instance=reservation.getInstances().get(0);
        publicIpv4Address=instance.getPublicIpAddress();
        return publicIpv4Address;
    }

    /**
     * To delete ip permission
     * @param ipPermission
     */
    public void delete(IPPermission ipPermission) {
        this.ipPermissionRepository.delete(ipPermission);
    }


}
