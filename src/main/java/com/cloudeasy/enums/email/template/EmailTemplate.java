package com.cloudeasy.enums.email.template;

public enum EmailTemplate {

    VERIFICATION_EMAIL_TEMPLATE("VerificationEmail"),
    INSTANCE_CREATED_TEMPLATE("InstanceCreatedEmail"),
    INSTANCE_TERMINATED_TEMPLATE("InstanceTerminatedEmail");

    private String emailTemplate;
    EmailTemplate(String mailTemplate) { this.emailTemplate=mailTemplate; }
    public String getEmailTemplate() { return this.emailTemplate; }
}
