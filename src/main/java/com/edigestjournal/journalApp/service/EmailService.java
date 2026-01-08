package com.edigestjournal.journalApp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String Email,String Body,String Subject){  //sendEmail(String to,String subject,String body) for functional use
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setSubject(Subject);
            mail.setText(Body);
            mail.setTo(Email);
            javaMailSender.send(mail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
