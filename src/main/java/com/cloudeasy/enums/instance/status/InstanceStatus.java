package com.cloudeasy.enums.instance.status;

public enum InstanceStatus {
    RUNNING("Running"),
    STOPPED("Stopped");

    private String status;

    InstanceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
