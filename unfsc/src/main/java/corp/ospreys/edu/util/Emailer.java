/**
 * 
 */
package corp.ospreys.edu.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author ppenamak
 *
 */
public class Emailer

{

	public static String triggerEmail(String subject, String body) {
		final String username = "unfsc.ospreys@gmail.com";
		final String password = "unfscadmin$";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("unfsc.ospreys@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("unfsc.ospreys@gmail.com"));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return "sent";
	}
}