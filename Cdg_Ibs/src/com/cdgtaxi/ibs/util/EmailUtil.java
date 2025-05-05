package com.cdgtaxi.ibs.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.model.email.FileAttachment;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;

public class EmailUtil {
	private static final Logger logger = Logger.getLogger(EmailUtil.class);
	private static JavaMailSenderImpl emailSender;
	public static String from;
	private static final String PROTOCOL_SMTP = "smtp";
	
	public void setEmailSender(JavaMailSenderImpl emailSender) {
		this.emailSender = emailSender;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public static void sendEmail(String from, String[] to, String[] cc, String[] bcc, String subject,
			String content, Collection<FileAttachment> attachments) throws MessagingException {
		logger.info("Sending email: " + subject);

		MimeMessage message = emailSender.createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(from);
		helper.setTo(to);
		if (cc.length > 0)
			if (cc[0].length() > 0)
				helper.setCc(cc);
		if (bcc.length > 0)
			if (bcc[0].length() > 0)
				helper.setBcc(bcc);
		helper.setSubject(subject);

		// use the true flag to indicate the text included is HTML
		helper.setText(content, true);

		// attaching the attachments
		for (FileAttachment attachment : attachments) {
			helper.addAttachment(attachment.fileName, attachment.tempFile);
		}

		emailSender.send(message);
	}

	/**
	 * The "From" is populated with system default settings.
	 * 
	 * @param to
	 *            Multiple Receiptients
	 * @param subject
	 * @param content
	 */
	public static void sendEmail(String[] to, String subject, String content) {
		logger.info("");
		try {
			SimpleMailMessage email = new SimpleMailMessage();
			email.setFrom(from);
			email.setTo(to);
			email.setSubject(subject);
			email.setText(content);
			emailSender.send(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendEmail(String from, String[] to, String subject, String content) {
		logger.info("");
		try {
			Map emailProperties = (Map) SpringUtil.getBean("emailProperties");
			String emailHost = (String) emailProperties.get("mail.host");
			
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", PROTOCOL_SMTP);
			props.setProperty("mail.host", emailHost);
			
			Session mailSession = Session.getDefaultInstance(props, null);
			Transport transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from));
			
			message.setSentDate(Calendar.getInstance().getTime());
			message.setSubject(subject);
			message.setContent(content, "text/html");
			
			InternetAddress[] toInternetAddresses = new InternetAddress[to.length];
			for(int i=0; i<to.length; i++){
				toInternetAddresses[i] = new InternetAddress(to[i]);
			}
			message.addRecipients(Message.RecipientType.TO, toInternetAddresses);
			
			transport.connect();
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
//			SimpleMailMessage email = new SimpleMailMessage();
//			email.setFrom(from);
//			email.setTo(to);
//			email.setSubject(subject);
//			email.setText(content);
//			emailSender.send(message);
		} catch (Exception e) {
			logger.info("Email have error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * The "From" is populated with system default settings.
	 * 
	 * @param to
	 *            Single Receiptient
	 * @param subject
	 * @param content
	 */
	public static void sendEmail(String to, String subject, String content) {
		logger.info("");
		try {
			SimpleMailMessage email = new SimpleMailMessage();
			email.setFrom(from);
			email.setTo(to);
			email.setSubject(subject);
			email.setText(content);
			emailSender.send(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendEmail(String from, String to, String subject, String content) {
		logger.info("");
		try {
			SimpleMailMessage email = new SimpleMailMessage();
			email.setFrom(from);
			email.setTo(to);
			email.setSubject(subject);
			email.setText(content);
			emailSender.send(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendEmailHtml( final String to, final String subject, final String content) {
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setFrom(new InternetAddress(from));
					mimeMessage.addRecipients(Message.RecipientType.TO, to);
					mimeMessage.setSubject(subject);
					Multipart mp = new MimeMultipart();
					BodyPart contentPart = new MimeBodyPart();
					contentPart.setContent(content, "text/html");
					mp.addBodyPart(contentPart);
					mimeMessage.setContent(mp);
				}
			};
			emailSender.send(preparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * email to send with CC
	 *
	 * @param to
	 * @param cc
	 * @param subject
	 * @param content
	 */
	public static void sendEmail(final String[] to, final String[] cc, final String subject,
			final String content) {
		logger.info("sendEmail(String[] to, String[] cc, String subject, String content)");
		logger.info("sendEmail(to = " + Arrays.toString(to) + ", cc = " + Arrays.toString(cc)
				+ ", subject = " + subject + ", content = " + content + ")");
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setFrom(new InternetAddress(from));
					for (String toEmail : to) {
						mimeMessage.addRecipients(Message.RecipientType.TO, toEmail);
					}
					for (String ccEmail : cc) {
						mimeMessage.addRecipients(Message.RecipientType.CC, ccEmail);
					}
					mimeMessage.setSubject(subject);
					Multipart mp = new MimeMultipart();
					BodyPart contentPart = new MimeBodyPart();
					contentPart.setContent(content, "text/html");
					mp.addBodyPart(contentPart);
					mimeMessage.setContent(mp);
				}
			};
			emailSender.send(preparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * email to send with CC
	 * 
	 * @param to
	 * @param cc
	 * @param subject
	 * @param content
	 */
	public static void sendEmailwithSender(final String sender, final String[] to, final String[] cc, final String subject,
			final String content) {
		logger.info("sendEmail(String[] to, String[] cc, String subject, String content)");
		logger.info("sendEmail(to = " + Arrays.toString(to) + ", cc = " + Arrays.toString(cc)
				+ ", subject = " + subject + ", content = " + content + ")");
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setFrom(new InternetAddress(sender));
					for (String toEmail : to) {
						mimeMessage.addRecipients(Message.RecipientType.TO, toEmail);
					}
					for (String ccEmail : cc) {
						mimeMessage.addRecipients(Message.RecipientType.CC, ccEmail);
					}
					mimeMessage.setSubject(subject);
					Multipart mp = new MimeMultipart();
					BodyPart contentPart = new MimeBodyPart();
					contentPart.setContent(content, "text/html");
					mp.addBodyPart(contentPart);
					mimeMessage.setContent(mp);
				}
			};
			emailSender.send(preparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Send generic success/completion messages
	public static void sendGenericNotification(String emailType, String from) {

		// AmtbAccount acct = req.getAmtbAccount();

//		String emails = ConfigurableConstants.getEmailText(emailType, "EMAIL");
		MstbMasterTable emEmailMaster = ConfigurableConstants.getMasterTable(emailType, "EMAIL");
		String toEmails = emEmailMaster.getMasterValue();
		
		MstbMasterTable emSubjMaster = ConfigurableConstants.getMasterTable(emailType, "SUBJ");
		MstbMasterTable emContMaster = ConfigurableConstants.getMasterTable(emailType, "CONT");
		
		sendEmail(from, toEmails.split(";"),
				emSubjMaster.getMasterValue(),
				emContMaster.getMasterValue());
	}
	//Send generic success/completion messages
	public static void sendGenericRecurringNotificationComplete(String emailType, String from, Integer noOfFile) {

			// AmtbAccount acct = req.getAmtbAccount();

//			String emails = ConfigurableConstants.getEmailText(emailType, "EMAIL");
			MstbMasterTable emEmailMaster = ConfigurableConstants.getMasterTable(emailType, "EMAIL");
			String toEmails = emEmailMaster.getMasterValue();
			
			MstbMasterTable emSubjMaster = ConfigurableConstants.getMasterTable(emailType, "SUBJ");
			MstbMasterTable emContMaster = ConfigurableConstants.getMasterTable(emailType, "CONT");
			
			
			if(noOfFile == 0 && emailType.equalsIgnoreCase("RERC"))
			{
				sendEmail(from, toEmails.split(";"),
						emSubjMaster.getMasterValue() + ". 0 File Uploaded",
						emContMaster.getMasterValue() + ". 0 File Uploaded");
			}
			else if(noOfFile == 0 && emailType.equalsIgnoreCase("RERDC"))
			{
				sendEmail(from, toEmails.split(";"),
						emSubjMaster.getMasterValue() + ". 0 File Downloaded",
						emContMaster.getMasterValue() + ". 0 File Downloaded");
			}
			else
			{
				sendEmail(from, toEmails.split(";"),
						emSubjMaster.getMasterValue(),
						emContMaster.getMasterValue());
			}
		}
	
	//Send generic success/completion messages
	public static void sendGenericNotificationError(String emailType, String from, String msg) {

		// AmtbAccount acct = req.getAmtbAccount();

//		String emails = ConfigurableConstants.getEmailText(emailType, "EMAIL");
		MstbMasterTable emEmailMaster = ConfigurableConstants.getMasterTable(emailType, "EMAIL");
		String toEmails = emEmailMaster.getMasterValue();
		
		MstbMasterTable emSubjMaster = ConfigurableConstants.getMasterTable(emailType, "SUBJ");
		MstbMasterTable emContMaster = ConfigurableConstants.getMasterTable(emailType, "CONT");
		
		sendEmail(from, toEmails.split(";"),
				emSubjMaster.getMasterValue() + " Error",
				"Dear Receiver, theres an Error in the job : "+msg);
	}
}
