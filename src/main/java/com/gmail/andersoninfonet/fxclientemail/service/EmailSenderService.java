package com.gmail.andersoninfonet.fxclientemail.service;

import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.gmail.andersoninfonet.fxclientemail.model.EmailAccount;
import com.gmail.andersoninfonet.fxclientemail.model.enums.EmailSendingResult;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class EmailSenderService extends Service<EmailSendingResult> {

    private EmailAccount account;
    private String subject;
    private String recipient;
    private String content;

    
    /**
     * @param account
     * @param subject
     * @param recipient
     * @param content
     */
    public EmailSenderService(EmailAccount account, String subject, String recipient, String content) {
        this.account = account;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
    }



    @Override
    protected Task<EmailSendingResult> createTask() {
        return new Task<EmailSendingResult>(){

            @Override
            protected EmailSendingResult call() {
                try {
                    MimeMessage mimeMessage = new MimeMessage(account.getSession());
                    mimeMessage.setFrom(account.getAddress());
                    mimeMessage.setRecipients(RecipientType.TO, recipient);
                    mimeMessage.setSubject(subject);

                    Multipart multipart = new MimeMultipart();
                    BodyPart bodyPart = new MimeBodyPart();
                    bodyPart.setContent(content, "text/html");
                    multipart.addBodyPart(bodyPart);
                    mimeMessage.setContent(multipart);

                    Transport transport = account.getSession().getTransport();
                    transport.connect(
                        account.getProperties().getProperty("outgoingHost"),
                        account.getAddress(),
                        account.getPassword()
                    );
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                    transport.close();
                    return EmailSendingResult.SUCCESS;
                }  catch(MessagingException ex) {
                    ex.printStackTrace();
                    return EmailSendingResult.FAILED_BY_PROVIDER;
                } catch(Exception ex) {
                    ex.printStackTrace();
                    return EmailSendingResult.FAILED_BY_UNEXPECTED_ERROR;
                }
            }
            
        };
    }
    
}
