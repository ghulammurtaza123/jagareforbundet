
package com.produkt.service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	public boolean sendEmail(String subject, String message, String to) {

		boolean flag = false;

		String from = "ghulammurtaza123@gmail.com";

		String host = "smtp.gmail.com";

		// get system properties

		Properties systemProperties = System.getProperties();
		System.out.println("systemProperties");

		systemProperties.setProperty("mail.smtp.host", host);
		systemProperties.setProperty("mail.smtp.port", "465");
		systemProperties.setProperty("mail.smtp.ssl.enable", "true");
		systemProperties.setProperty("mail.smtp.auth", "true");

		Session session = Session.getInstance(systemProperties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(from, "ujwr icvl wsld kyqu");
			}

		});
		session.setDebug(true);

		// compose message text,multimedia

		MimeMessage msg = new MimeMessage(session);

		try {

			// from field msg.setFrom(from);

			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			msg.setSubject(subject);

			 msg.setText(message); msg.setContent(message,"text/html");

			Transport.send(msg);

			System.out.println("Message sent successfully");

			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

}
