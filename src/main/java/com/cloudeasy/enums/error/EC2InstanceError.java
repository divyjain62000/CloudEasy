package com.cloudeasy.enums.error;

public enum EC2InstanceError {
    ID_REQUIRED("Server not exists"),
    INVALID_ID("Invalid id"),
    SERVER_IS_STOP("Server is not running, first start your server");

    private String ec2InstanceError;

    EC2InstanceError(String ec2InstanceError) {
        this.ec2InstanceError = ec2InstanceError;
    }

    public String getEc2InstanceError() {
        return ec2InstanceError;
    }
}
