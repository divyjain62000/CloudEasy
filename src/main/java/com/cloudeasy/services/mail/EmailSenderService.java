package com.cloudeasy.services.mail;

import com.cloudeasy.enums.ApplicationProperties;
import com.cloudeasy.enums.email.template.EmailTemplate;
import com.cloudeasy.services.mail.dto.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * This service is used to send and manage email related things
 *
 * @author DIVY JAIN
 * @version 1.0
 * @since Feb 09, 2022 7:22:00PM
 */
@Service
@EnableAsync
@Slf4j
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailProperties mailProperties;

    private static final String MAIL_FROM="divyjain62000@gmail.com";

    @Async
    public void sendInstanceCreatedNotification(MailDTO mailDTO) {
        log.info("Request to send EC2 instance created email notification");
        String templateName= EmailTemplate.INSTANCE_CREATED_TEMPLATE.getEmailTemplate();
        MimeMessage message = javaMailSender.createMimeMessage();
        Context context=new Context();
        mailDTO.getEmailMap().forEach((key,val)->{
            context.setVariable(key,val);
        });
        context.setVariable("url", ApplicationProperties.BASE_URL_BACKEND.getApplicationProperties());
        String body=templateEngine.process(templateName, context);
        try
        {
            MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(EmailSenderService.MAIL_FROM);
            String recipientEmails[]=new String[mailDTO.getTo().size()];
            recipientEmails=mailDTO.getTo().toArray(recipientEmails);
            mimeMessageHelper.setTo(recipientEmails);
            mimeMessageHelper.setSubject(mailDTO.getSubject());
            mimeMessageHelper.setText(body,true);
            if (mailDTO.getOriginalFilename() != null) {
                File file=new File(mailDTO.getOriginalFilename());
                mimeMessageHelper.addAttachment(mailDTO.getAttachedFilename(),file);
            }
            javaMailSender.send(message);
        }catch(MessagingException messagingException) {
            System.out.print(messagingException.getMessage());
        }
    }

    @Async
    public void sendInstanceTerminatedNotification(MailDTO mailDTO) {
        log.info("Request to send EC2 instance terminated email notification");
        String templateName= EmailTemplate.INSTANCE_TERMINATED_TEMPLATE.getEmailTemplate();
        MimeMessage message = javaMailSender.createMimeMessage();
        Context context=new Context();
        mailDTO.getEmailMap().forEach((key,val)->{
            context.setVariable(key,val);
        });
        String body=templateEngine.process(templateName, context);
        try
        {
            MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(EmailSenderService.MAIL_FROM);
            String recipientEmails[]=new String[mailDTO.getTo().size()];
            recipientEmails=mailDTO.getTo().toArray(recipientEmails);
            mimeMessageHelper.setTo(recipientEmails);
            mimeMessageHelper.setSubject(mailDTO.getSubject());
            mimeMessageHelper.setText(body,true);
            javaMailSender.send(message);
        }catch(MessagingException messagingException) {
            System.out.print(messagingException.getMessage());
        }
    }



}
