package com.nonono.test.smtp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("/smtp")
public class SmtpController {

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/send/simple")
    public String sendSimple() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("crm.system@hujiang.com");
        message.setTo("chenjing@hujiang.com");
        message.setSubject("test subject");
        message.setText("test body.");

        mailSender.send(message);
        return "ok";
    }

    @RequestMapping("/send/mime")
    public String sendMime() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("crm.system@hujiang.com");
        helper.setTo("chenjing@hujiang.com");
        helper.setSubject("test subject");
        helper.setText("<html><h1>test</h1><br/><div>body is here.</div></html>", true);

        mailSender.send(message);
        return "ok";
    }

    @RequestMapping("/send/attachment")
    public String sendAttachment() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("crm.system@hujiang.com");
        helper.setTo("chenjing@hujiang.com");
        helper.setSubject("test subject");
        helper.setText("<html><h1>test</h1><br/><div>body is here.</div></html>", true);

        ClassPathResource image = new ClassPathResource("test.png");
        helper.addAttachment("test.png", image);
        mailSender.send(message);
        return "ok";
    }
}
