package com.cloudeasy.enums.email.subject;

public enum EmailSubject {
    VERIFICATION_EMAIL("Verify your Cloudeasy account"),
    INSTANCE_CREATED("New Server Created"),
    INSTANCE_TERMINATED("Server Terminated");


    private String emailSubject;
    EmailSubject(String emailSubject) { this.emailSubject=emailSubject; }

    public String getEmailSubject() {
        return emailSubject;
    }
}
