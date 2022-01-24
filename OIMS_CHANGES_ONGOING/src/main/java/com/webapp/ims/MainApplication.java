package com.webapp.ims;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "com.webapp.ims.*" })
@ComponentScan({ "com.webapp.ims.*" })
@EnableJpaRepositories("com.webapp.ims.*")
public class MainApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainApplication.class);
	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("100MB");
		factory.setMaxRequestSize("100MB");
		return factory.createMultipartConfig();
	}
	/*
	 * @Override public void run(String... args) throws Exception {
	 * 
	 * try { MeetingScheduler meetingSchedulers=new MeetingScheduler();
	 * 
	 * 
	 * 
	 * SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yyyy"); String
	 * time = System.currentTimeMillis()+""; String userid = "userid";
	 * 
	 * ------------ NEED TO IMPLEMENT FOR NIC MAIL OR UDHOG BANDHU
	 * INFO------------------------------
	 * 
	 * Properties properties = System.getProperties();
	 * properties.put("mail.smtp.host", "smtp.gmail.com");
	 * properties.put("mail.debug", "true");
	 * properties.setProperty("mail.smtp.auth", "true");
	 * properties.put("mail.smtp.port", "587");
	 * properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	 * properties.put("mail.smtp.starttls.enable","true");
	 * properties.put("mail.smtp.EnableSSL.enable","true"); if(userid!=null &&
	 * !userid.isEmpty()) { final String username="sachinsinghsachnan@gmail.com";
	 * final String password="S@c#in09"; Session session =
	 * Session.getInstance(properties, new javax.mail.Authenticator() {
	 * 
	 * protected PasswordAuthentication getPasswordAuthentication() { return new
	 * PasswordAuthentication(username,password); } });
	 * 
	 * 
	 * MimeMessage msg = new MimeMessage(session); msg.setFrom(new
	 * InternetAddress(username)); msg.addRecipient(Message.RecipientType.TO, new
	 * InternetAddress("swapnil.singh@velocis.co.in"));
	 * msg.setSubject("Meeting Scheduled for Application Review on : "+new Date());
	 * msg.setContent("Meeting Scheduled", "text/html"); MimeBodyPart
	 * messageBodyPart = new MimeBodyPart();
	 * messageBodyPart.setContent("Department Name: "+"dept name"
	 * +" Meeting Date: "+new Date() + "\n" +" Meeting Time"+time + "\n"
	 * +" Meeting Location: "+"loc", "text/html"); Multipart multipart = new
	 * MimeMultipart(); multipart.addBodyPart(messageBodyPart);
	 * msg.setContent(multipart); Transport.send(msg); }
	 * 
	 * 
	 * }catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 */

}
