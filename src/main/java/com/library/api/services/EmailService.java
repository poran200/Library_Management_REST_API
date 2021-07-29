package com.library.api.services;/*
 * @created 7/23/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.model.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.library.api.dto.EmailConstant.EMAIL_SUBJECT;
import static com.library.api.dto.EmailConstant.FROM_EMAIL;


@RequiredArgsConstructor@Service
@Log4j2
public class EmailService {
  private final JavaMailSender mailSender;


  public void sendPasswordEmail(String firstName, String password, String email)  {
      Mail mail = new Mail();
      mail.setFrom(FROM_EMAIL);
      mail.setTo(email);
      mail.setSubject(EMAIL_SUBJECT);
      mail.setContent("Hello "+firstName+",\n \n Your new Account Password is : "+password +"\n \n The User Support Team");
      try {
          send(mail);
      } catch (MessagingException e) {
          log.error("Email can not be send [user email] = "+email);
          e.printStackTrace();
      }
  }
  public void send(Mail mail) throws MessagingException {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
      helper.setTo(mail.getTo());
      helper.setText(mail.getContent(),true);
      helper.setSubject(mail.getSubject());
      helper.setFrom(mail.getFrom());
      helper.setSentDate(new Date());
      mailSender.send(mimeMessage);
  }
}
