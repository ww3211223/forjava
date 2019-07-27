package com.nonono.test;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

public class SendCalendarHelper {

    private class EmailAuthenticator extends Authenticator {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            String userId = "crm.system@hujiang.com";
            String password = "crm@0705";
            return new PasswordAuthentication(userId, password);
        }
    }

    public void send() throws Exception {
        System.out.println("send email.");
        try {
            String fromEmail = "crm.system@hujiang.com";
            String toEmail = "chenjing@hujiang.com";
            Properties props = new Properties();
            try {
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.host", "mail.hujiang.com");
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Session session;
            Authenticator authenticator = new EmailAuthenticator();
            session = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("chenjing@hujiang.com"));
            message.setSubject("测试会议");
            message.setSentDate(new Date());
            StringBuffer buffer = new StringBuffer();
            String uuid = UUID.randomUUID().toString();
            //uuid = "493d8a5b-8077-4aac-8ae7-e0e2a6c0e97b";
            System.out.println("uuid：" + uuid);

            buffer.append("BEGIN:VCALENDAR\n"
                    + "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
                    + "VERSION:2.0\n"
                    + "METHOD:REQUEST\n"
                    + "BEGIN:VEVENT\n"
                    + "SEQUENCE:0\n"
                    + "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + toEmail + "\n"
                    + "ORGANIZER:MAILTO:" + fromEmail + "\n"
                    + "DTSTAMP:20190708T080000Z\n"
                    + "DTSTART:20190710T013000Z\n"
                    + "DTEND:20190710T020000Z\n"
                    + "LOCATION:我的日程\n"
                    + "UID:" + uuid + "\n"//如果id相同的话，outlook会认为是同一个会议请求，所以使用uuid。
                    //+ "CATEGORIES:SuccessCentral Reminder\n"
                    + "DESCRIPTION:测试会议\n"
                    + "SUMMARY:测试会议\n"
                    + "PRIORITY:5\n"
                    + "CLASS:PUBLIC\n"
                    + "BEGIN:VALARM\n"
                    + "TRIGGER:-PT15M\n"
                    + "ACTION:DISPLAY\n"
                    + "DESCRIPTION:Reminder\n"
                    + "END:VALARM\n"
                    + "END:VEVENT\n"
                    + "END:VCALENDAR");
            BodyPart messageBodyPart = new MimeBodyPart();
            // 测试下来如果不这么转换的话，会以纯文本的形式发送过去，
            //如果没有method=REQUEST;charset=\"UTF-8\"，outlook会议附件的形式存在，而不是直接打开就是一个会议请求
            messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(),
                    "text/calendar;method=REQUEST;charset=\"UTF-8\"")));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createEvent() throws Exception {
        System.out.println("send event.");
        try {
            String fromEmail = "crm.system@hujiang.com";
            String toEmail = "chenjing@hujiang.com";
            Properties props = new Properties();
            try {
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.host", "mail.hujiang.com");
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Session session;
            Authenticator authenticator = new EmailAuthenticator();
            session = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("chenjing@hujiang.com"));
            message.setSubject("测试会议");
            message.setSentDate(new Date());
            StringBuffer buffer = new StringBuffer();
            String uuid = UUID.randomUUID().toString();
            System.out.println("uuid：" + uuid);

            buffer.append("BEGIN:VEVENT\n" +
                    "UID:" + uuid + "\n" +
                    "DTSTAMP:20190708T080000Z\n" +
                    "DTSTART:20190710T013000Z\n" +
                    "DTEND:20190710T020000Z\n" +
                    "SUMMARY:测试事件\n" +
                    "CLASS:PRIVATE\n" +
                    "END:VEVENT");
            BodyPart messageBodyPart = new MimeBodyPart();
            // 测试下来如果不这么转换的话，会以纯文本的形式发送过去，
            //如果没有method=REQUEST;charset=\"UTF-8\"，outlook会议附件的形式存在，而不是直接打开就是一个会议请求
            messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(),
                    "text/calendar;charset=\"UTF-8\"")));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void cancel(String uuid) throws Exception {
        System.out.println("cancel email.");
        try {
            String fromEmail = "crm.system@hujiang.com";
            String toEmail = "chenjing@hujiang.com";
            Properties props = new Properties();
            try {
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.host", "mail.hujiang.com");
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Session session;
            Authenticator authenticator = new EmailAuthenticator();
            session = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("chenjing@hujiang.com"));
            message.setSubject("测试取消会议");
            message.setSentDate(new Date());
            StringBuffer buffer = new StringBuffer();

            buffer.append("BEGIN:VCALENDAR\n"
                    + "VERSION:2.0\n"
                    + "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
                    + "CALSCALE:GREGORIAN\n"
                    + "METHOD:CANCEL\n"
                    + "BEGIN:VEVENT\n"
                    + "DTSTART:20190709T001200Z\n"
                    + "SEQUENCE:1\n"
                    + "STATUS:CANCELLED\n"
                    + "UID:" + uuid + "\n"
                    + "END:VEVENT\n"
                    + "END:VCALENDAR\n"
            );

            BodyPart messageBodyPart = new MimeBodyPart();
            // 测试下来如果不这么转换的话，会以纯文本的形式发送过去，
            //如果没有method=REQUEST;charset=\"UTF-8\"，outlook会议附件的形式存在，而不是直接打开就是一个会议请求
            messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(),
                    "text/calendar;method=CANCEL;charset=\"UTF-8\"")));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
