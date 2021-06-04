package com.test.notify.covid19vaccine.slotsnotifier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.notify.covid19vaccine.slotsnotifier.entity.CentersEntity;
import com.test.notify.covid19vaccine.slotsnotifier.entity.EmailEntity;
import com.test.notify.covid19vaccine.slotsnotifier.entity.SessionsEntity;
import com.test.notify.covid19vaccine.slotsnotifier.entity.VaccineSlotsResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service
public
class NotifySlotsService {

  @Value("${gmail.username}")
  private String userName;

  @Value("${gmail.password}")
  private String password;

  private String toPin;

  @Async
  public
  CompletableFuture<String> getResult(String email, String pinCode, int age) {
    System.out.println("Started looking for available slots at Pin Code " + pinCode + " .....");

    toPin = pinCode;

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.add("user-agent",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/54.0.2840.99 Safari/537.36");

    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

    final String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public" +
        "/calendarByPin?pincode=" +
        pinCode +
        "&date=" +
        formattedDate();

    getSlot(entity, url, email, age);
    return CompletableFuture.completedFuture("Thread is doing it's task!");
  }

  private
  void getSlot(HttpEntity<String> entity, String url, String email, int age) {
    Runnable runnable = () -> {
      boolean isEmailSent = false;
      while (true) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> res = rt.exchange(url, HttpMethod.GET, entity, String.class);
        int minAge;
        int availableDoses1;
        int availableDoses2;
        try {
          if (null != res) {
            VaccineSlotsResponseEntity centers =
                new ObjectMapper().readValue(res.getBody(), VaccineSlotsResponseEntity.class);
            EmailEntity emailEntity = new EmailEntity();
            emailEntity.setTo_address(email);
            emailEntity.setSubject("Vaccine slot available at PIN Code " + toPin);
            System.out.println("Checking for URL: " + url);
            boolean isAvailable = false;
            StringBuilder sb = new StringBuilder();
            sb.append("Dear User,");
            if (null != centers) {
              for (CentersEntity c : centers.getCenters()) {
                for (SessionsEntity s : c.getSessions()) {
                  minAge = s.getMin_age_limit();
                  availableDoses1 = s.getAvailable_capacity_dose1();
                  availableDoses2 = s.getAvailable_capacity_dose2();
                  if (minAge == age) {
                    System.out.println("Center Id: " + c.getCenter_id());
                    System.out.println("Age: " + minAge);
                    System.out.println("Date & Time: " + new Date());
                    System.out.println("Available Dose1 Capacity: " + availableDoses1);
                    if (availableDoses1 > 0) {
                      isAvailable = true;
                      System.out.println("Dose 1 is available at " + c.getName());
                      sb.append(" Vaccine: " + s.getVaccine());
                      sb.append(" available for age " + age + " +");
                      sb.append(" Total: " + s.getAvailable_capacity());
                      sb.append(" Dose1: " + availableDoses1);
                      sb.append(" Dose2: " + availableDoses2);
                      sb.append(" at " + c.getName());
                      sb.append(" on Date " + s.getDate());
                      sb.append("........");
                    }
                  }
                }
              }
              if (isAvailable) {
                sb.append(" Visit https://www.cowin.gov.in/ to book the appointment!!!");
                emailEntity.setBody(sb.toString());
                try {
                  System.out.println("Sending Email......!!!");
                  sendmail(emailEntity);
                  isEmailSent = true;
                } catch (MessagingException e) {
                  e.printStackTrace();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            }
          }
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
        try {
          System.out.println("isEmailSent: " + isEmailSent);
          if (isEmailSent) {
            Thread.sleep(10800000);
          } else {
            Thread.sleep(300000);
          }
        } catch (InterruptedException e) {
        }
      }
    };
    new Thread(runnable).start();
  }

  private
  String formattedDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    sdf.format(new Date());
    return sdf.format(new Date());
  }

  private
  void sendmail(EmailEntity emailEntity)
  throws MessagingException, IOException {
    Properties props = new Properties();
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
      protected
      PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
      }
    });

    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(userName, false));

    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailEntity.getTo_address()));
    msg.setSubject(emailEntity.getSubject());
    msg.setContent(emailEntity.getBody(), "text/html");
    msg.setSentDate(new Date());

    MimeBodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setContent(emailEntity.getBody(), "text/html");

    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(messageBodyPart);

    MimeBodyPart attachPart = new MimeBodyPart();
    attachPart.attachFile("D:\\My data\\Code\\covid19vaccine\\emailImage.jpg");

    multipart.addBodyPart(attachPart);
    msg.setContent(multipart);
    //Send the email
    Transport.send(msg);
    System.out.println("Email Sent!!!");
  }
}
