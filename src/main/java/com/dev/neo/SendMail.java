/*package com.dev.neo;

import java.io.File;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {
	   public void alertMail(String ip,File diskdrive,long totalspace,double freespace) 
	   {
	      String recipient = "dipesh.parab14@gmail.com";
	 
	      final String sender = "nqtemp@gmail.com";
	      final String password = "neoquant@12345";	 
	      String host = "smtp.gmail.com";
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);
	      properties.setProperty("mail.smtp.port", "587");
	      properties.put("mail.smtp.starttls.enable", "true");
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	      Session session = Session.getDefaultInstance(properties, 
	    		    new javax.mail.Authenticator(){
	    		        protected PasswordAuthentication getPasswordAuthentication() {
	    		            return new PasswordAuthentication(
	    		                sender, password);
	    		        }
	    		});
	      try
	      {
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(sender));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	         message.setSubject("Alert : Low Disk Space");
	         String html = "<h2>Low Disk Space. Details are mentioned below.</h2>	<table border='1'><tr><td>IP Address</td><td>Disk Drive</td><td>Total Space</td><td>Free Space</td></tr>	<tr><td>"+ip+"</td>	<td>"+diskdrive+"</td>	<td>"+totalspace+"</td>	<td>"+freespace+"</td></tr></table>";
	         Multipart multipart = new MimeMultipart( "alternative" );
	         MimeBodyPart htmlPart = new MimeBodyPart();
	         htmlPart.setContent( html, "text/html; charset=utf-8" );
	         multipart.addBodyPart( htmlPart );
	         message.setContent( multipart );
	         Transport.send(message);
	         System.out.println("Mail successfully sent");
	      }
	      catch (MessagingException mex) 
	      {
	         mex.printStackTrace();
	      }
	   }
	

}*/
