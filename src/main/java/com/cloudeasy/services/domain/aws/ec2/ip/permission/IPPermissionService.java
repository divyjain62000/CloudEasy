package com.cloudeasy.services.domain.aws.ec2.ip.permission;

import com.cloudeasy.domain.aws.ec2.ip.permission.IPPermission;
import com.cloudeasy.domain.aws.ec2.keypair.KeyPair;

public interface IPPermissionService {

    /**
     * To create IP permission
     * @param groupName
     * @return {@link IPPermission}
     */
    IPPermission createIPPermission(String groupName);


    /**
     * To get public ipv4 address by instance id from aws ec2
     * @param instanceId
     * @return String
     */
    String findPublicIPv4AddressByInstanceIdFromAwsEC2(String instanceId);

    /**
     * To save ip permission
     * @param ipPermission
     * @return Long
     */
    Long save(IPPermission ipPermission);

    /**
     * To delete ip permission
     * @param ipPermission
     */
    void delete(IPPermission ipPermission);


}
