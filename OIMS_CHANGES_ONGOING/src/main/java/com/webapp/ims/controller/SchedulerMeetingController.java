package com.webapp.ims.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.model.MeetingSchedulerDis;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.dis.service.DisMeetingSchedulerService;
import com.webapp.ims.login.model.Login;
import com.webapp.ims.model.Department;
import com.webapp.ims.model.MeetingScheduler;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.repository.DepartmentRepository;
import com.webapp.ims.repository.PrepareAgendaNoteRepository;
import com.webapp.ims.service.DepartmentService;
import com.webapp.ims.service.MeetingSchedulerService;
import com.webapp.ims.service.SendingEmail;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;

@Controller
public class SchedulerMeetingController {

	@Autowired
	private TblUsersService loginService;

	@Autowired
	DISPrepareAgendaNotesRepository disprepareAgendaRepos;

	@Autowired
	DisMeetingSchedulerService disMeetingSchedulerService;

	@Autowired
	private MeetingSchedulerService meetingSchedulerService;

	@Autowired
	PrepareAgendaNoteRepository prepareAgendaRepo;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	SendingEmail sendingEmail;

	@Autowired
	DepartmentRepository deptRepos;;

	private AtomicInteger atomicInteger = new AtomicInteger();

	private final Logger logger = LoggerFactory.getLogger(ApplicationDetailsViewController.class);

	@RequestMapping(value = "/meetingschedule", method = RequestMethod.GET)
	public ModelAndView meetingschedule(ModelMap model, HttpSession session) {
		logger.debug("Meeting Schedule Start");
		String userId = (String) session.getAttribute("userId");
		if (userId != null && !userId.isEmpty()) {
			List<PrepareAgendaNotes> listpreAgendaNotesMega = prepareAgendaRepo.findAllPrepareAgendaNotebyStatus();
			List<PrepareAgendaNotes> listpreAgendaNotesLarge = prepareAgendaRepo.findLargePrepareAgendaNotebyStatus();

			List<MeetingScheduler> meetingSchedulerList = meetingSchedulerService.getMeetingSchedulerByuserId(userId);

//			Iterable<Department> list = 
			Map<String, String> deptListMap = new LinkedHashMap<>();

			List<Department> deptList = deptRepos.findAll();
			List<Department> deptsortList = deptList.stream().sorted(Comparator.comparing(Department::getNewDeptName))
					.collect(Collectors.toList());

			for (Department dept : deptsortList) {
				deptListMap.put(dept.getNewDeptName(), dept.getNewDeptName());
			}

			model.addAttribute("concernDepartmentList", deptsortList);

			model.addAttribute("meetingSchedulerList", meetingSchedulerList);
			model.addAttribute("listpreAgendaNotesMega", listpreAgendaNotesMega);
			model.addAttribute("listpreAgendaNotesLarge", listpreAgendaNotesLarge);

		}

		model.addAttribute("meetingScheduler", new MeetingScheduler());
		return new ModelAndView("meetingschedule");
	}

	// For DIS Get List Metting Scheduler
	@RequestMapping(value = "/meetingscheduleDis", method = RequestMethod.GET)
	public ModelAndView meetingscheduleDis(ModelMap model, HttpSession session) {
		logger.debug("Dis Get Meeting Schedule Start");
		String userId = (String) session.getAttribute("userId");
		if (userId != null && !userId.isEmpty()) {
			List<DISPrepareAgendaNotes> listpreAgendaNotesMega = disprepareAgendaRepos
					.findAllPrepareAgendaNotebyStatus();
			List<DISPrepareAgendaNotes> listpreAgendaNotesLarge = disprepareAgendaRepos
					.findLargePrepareAgendaNotebyStatus();
			List<MeetingSchedulerDis> meetingSchedulerList = disMeetingSchedulerService
					.getDisMeetingSchedulerByuserId(userId);

			Map<String, String> deptListMap = new LinkedHashMap<>();

			List<Department> deptList = deptRepos.findAll();
			List<Department> deptsortList = deptList.stream().sorted(Comparator.comparing(Department::getNewDeptName))
					.collect(Collectors.toList());

			for (Department dept : deptsortList) {
				deptListMap.put(dept.getNewDeptName(), dept.getNewDeptName());
			}

			model.addAttribute("concernDepartmentList", deptsortList);

			model.addAttribute("meetingSchedulerList", meetingSchedulerList);
			model.addAttribute("listpreAgendaNotesMega", listpreAgendaNotesMega);
			model.addAttribute("listpreAgendaNotesLarge", listpreAgendaNotesLarge);
		}
		model.addAttribute("meetingScheduler", new MeetingSchedulerDis());
		logger.debug("Dis Get Meeting Schedule End");
		return new ModelAndView("meetingscheduleDis");
	}

	@RequestMapping(value = "/meetingschedulebyDIC", method = RequestMethod.GET)
	public ModelAndView meetingschedulebyDIC(ModelMap model, HttpSession session) {
		logger.debug("Meeting Schedule by DIC  Start");
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MeetingScheduler> meetingSchedulerList = meetingSchedulerService.getMeetingSchedulerByuserId(userId);
			model.addAttribute("meetingSchedulerList", meetingSchedulerList);
		}
		model.addAttribute("meetingScheduler", new MeetingScheduler());
		return new ModelAndView("meetingschedulebyDIC");
	}

	@RequestMapping(value = "/meetingScheduler", method = RequestMethod.POST)
	public ModelAndView scheduleMeeting(
			@ModelAttribute("meetingScheduler") @Validated MeetingScheduler meetingScheduler, BindingResult result,
			Model model, HttpSession httpSession)
			throws AddressException, MessagingException, IOException, ParseException {

		logger.debug("Meeting Schedule Start");

		List<String> emailList = new ArrayList<>();
		List<String> newemaillist = new ArrayList<>();

		SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yyyy");
		String time = meetingScheduler.getTime();

		MeetingScheduler meetingSchedulers = new MeetingScheduler();

		String userid = (String) httpSession.getAttribute("userId");

		/*------------ NEED TO IMPLEMENT FOR NIC MAIL OR UDHOG BANDHU INFO------------------------------*/

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.debug", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.EnableSSL.enable", "true");

		List<MeetingScheduler> meetingSchedulersList = new ArrayList<>();

		String str = meetingScheduler.getAppliId().toString();
		String appid = "";
		List<String> aalist = new ArrayList<>();

		System.out.println(str);
		String[] strarr = str.split(",");
		for (String a : strarr) {
			if (appid != "") {
				appid += ",";
			}
			appid += a;

			if (a.contains("@")) {
				aalist.add(appid);
				appid = "";
				emailList.add(a);
				System.out.println(a);

				if (userid != null && !userid.isEmpty()) {
					Optional<TblUsers> loginUser = loginService.getLoginIdById(userid);
					final String username = loginUser.get().getUserName();
					final String password = loginUser.get().getPassword();
					Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					});

					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(username));

					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(a));
					msg.setSubject("Meeting Scheduled for Application Review on : "
							+ dateFormate.format(meetingScheduler.getSchedulderDate()));
					msg.setContent("Meeting Scheduled", "text/html");
					MimeBodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setContent(
							"Department Name: " + meetingScheduler.getCommiteeDepartment() + "\n" + " Meeting Date: "
									+ dateFormate.format(meetingScheduler.getSchedulderDate()) + "\n" + " Meeting Time"
									+ time + "\n" + " Meeting Location: " + meetingScheduler.getSchedulerLocation(),
							"text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
					msg.setContent(multipart);
					Transport.send(msg);

				}
			}

		}

		String deptEmail = meetingScheduler.getDeptEmail().toString();
		String[] deptEmailList = deptEmail.split(",");
		for (String deptList : deptEmailList) {
			if (deptList.contains("@")) {
				newemaillist.add(deptList);
			}
		}

		String msg1 = "Department Name: " + meetingScheduler.getCommiteeDepartment() + "\n" + " Meeting Date: "
				+ dateFormate.format(meetingScheduler.getSchedulderDate()) + "\n" + " Meeting Time" + time + "\n"
				+ " Meeting Location: " + meetingScheduler.getSchedulerLocation();

		newemaillist.forEach(x -> sendingEmail.sentEmail("Meeting Scheduled for Application Review on : "
				+ dateFormate.format(meetingScheduler.getSchedulderDate()), msg1, x));

		for (String a : aalist) {
			String data[] = a.split(",");
			MeetingScheduler meetingSchedulers1 = new MeetingScheduler();
			meetingSchedulers1.setId("MS" + atomicInteger.getAndIncrement());
			// String appId = meetingScheduler.getAppId();
			if (data[0] != null) {
				meetingSchedulers1.setAppliId(data[0]);
			}
			if (data[1] != null) {
				meetingSchedulers1.setToMail(data[1]);
			}
			meetingSchedulers1.setCommiteeDepartment(meetingScheduler.getCommiteeDepartment());
			meetingSchedulers1.setSchedulerSubject(meetingScheduler.getSchedulerSubject());
			meetingSchedulers1.setSchedulderDate(meetingScheduler.getSchedulderDate());
			meetingSchedulers1.setTime(time);
			meetingSchedulers1.setSchedulerLocation(meetingScheduler.getSchedulerLocation());
			meetingSchedulers1.setUserId(userid);
			if (userid != null && !userid.isEmpty()) {
				Optional<TblUsers> loginUser = loginService.getLoginIdById(userid);
				meetingSchedulers1.setFromMail(loginUser.get().getUserName());
				// meetingSchedulers.setToMail(loginUser.getUserName());
				meetingSchedulers1.setCreatedBy(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
			}
			meetingSchedulers1.setCreateDate(new Date());
			meetingSchedulers1.setActive("Active");
			meetingSchedulerService.saveMeetingScheduler(meetingSchedulers1);

		}
		model.addAttribute("meetingScheduler", new MeetingScheduler());
		return new ModelAndView("redirect:/meetingschedule");
	}

	// DIS Save Scheduler
	@RequestMapping(value = "/meetingSchedulerDis", method = RequestMethod.POST)
	public ModelAndView scheduleMeetingDis(
			@ModelAttribute("meetingScheduler") @Validated MeetingSchedulerDis meetingScheduler, BindingResult result,
			Model model, HttpSession httpSession)
			throws AddressException, MessagingException, IOException, ParseException {

		// List<String> emailList = new
		// ArrayList<>(Arrays.asList(meetingScheduler.getAppId()));
		List<String> emailList = new ArrayList<>();

		MeetingSchedulerDis meetingSchedulers = new MeetingSchedulerDis();
		logger.debug("Dis Meeting Schedule Start");

		SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yyyy");
		String time = meetingScheduler.getTime();
		String userid = (String) httpSession.getAttribute("userId");

		/*------------ NEED TO IMPLEMENT FOR NIC MAIL OR UDHOG BANDHU INFO------------------------------*/

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.debug", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.EnableSSL.enable", "true");

		List<MeetingSchedulerDis> meetingSchedulersList = new ArrayList<>();

		String str = meetingScheduler.getAppId().toString();
		String appid = "";
		List<String> aalist = new ArrayList<>();

		System.out.println(str);
		String[] strarr = str.split(",");
		for (String a : strarr) {
			if (appid != "") {
				appid += ",";
			}
			appid += a;

			if (a.contains("@")) {
				aalist.add(appid);
				appid = "";
				emailList.add(a);
				System.out.println(a);

				if (userid != null && !userid.isEmpty()) {
					Optional<TblUsers> loginUser = loginService.getLoginIdById(userid);
					final String username = loginUser.get().getUserName();
					final String password = loginUser.get().getPassword();
					Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					});

					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(username));

					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(a));
					msg.setSubject("Meeting Scheduled for Application Review on : "
							+ dateFormate.format(meetingScheduler.getSchedulderDate()));
					msg.setContent("Meeting Scheduled", "text/html");
					MimeBodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setContent(
							"Department Name: " + meetingScheduler.getCommiteeDepartment() + "\n" + " Meeting Date: "
									+ dateFormate.format(meetingScheduler.getSchedulderDate()) + "\n" + " Meeting Time"
									+ time + "\n" + " Meeting Location: " + meetingScheduler.getSchedulerLocation(),
							"text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
					msg.setContent(multipart);
					Transport.send(msg);
				}
			}

		}
		List<String> newemaillist = new ArrayList<>();
		String deptEmail = meetingScheduler.getDeptEmail().toString();
		String[] deptEmailList = deptEmail.split(",");
		for (String deptList : deptEmailList) {
			if (deptList.contains("@")) {
				newemaillist.add(deptList);
			}
		}

		String msg1 = "Department Name: " + meetingScheduler.getCommiteeDepartment() + "\n" + "Meeting Date: "
				+ dateFormate.format(meetingScheduler.getSchedulderDate()) + "\n" + "Subject: "
				+ meetingScheduler.getSchedulerSubject() + "\n" + "Meeting Time" + time + "\n" + "Meeting Location: "
				+ meetingScheduler.getSchedulerLocation();

		newemaillist.forEach(x -> sendingEmail.sentEmail("Disbursment - Meeting Scheduled for Application Review on : "
				+ dateFormate.format(meetingScheduler.getSchedulderDate()), msg1, x));

		for (String a : aalist) {
			String data[] = a.split(",");
			MeetingSchedulerDis meetingSchedulers1 = new MeetingSchedulerDis();
			meetingSchedulers1.setId("MS" + atomicInteger.getAndIncrement());
			// String appId = meetingScheduler.getAppId();
			if (data[0] != null) {
				meetingSchedulers1.setAppId(data[0]);
			}
			if (data[1] != null) {
				meetingSchedulers1.setToMail(data[1]);
			}
			meetingSchedulers1.setCommiteeDepartment(meetingScheduler.getCommiteeDepartment());
			meetingSchedulers1.setSchedulerSubject(meetingScheduler.getSchedulerSubject());
			meetingSchedulers1.setSchedulderDate(meetingScheduler.getSchedulderDate());
			meetingSchedulers1.setTime(time);
			meetingSchedulers1.setSchedulerLocation(meetingScheduler.getSchedulerLocation());
			meetingSchedulers1.setUserId(userid);
			if (userid != null && !userid.isEmpty()) {
				Optional<TblUsers> loginUser = loginService.getLoginIdById(userid);
				meetingSchedulers1.setFromMail(loginUser.get().getUserName());
				// meetingSchedulers.setToMail(loginUser.getUserName());
				meetingSchedulers1.setCreatedBy(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
			}
			meetingSchedulers1.setCreateDate(new Date());
			meetingSchedulers1.setActive("Active");
			disMeetingSchedulerService.saveDisMeetingScheduler(meetingSchedulers1);

		}
		model.addAttribute("meetingScheduler", new MeetingSchedulerDis());
		return new ModelAndView("redirect:/meetingscheduleDis");
	}

	@RequestMapping(value = "/meetingSchedulerByDIC", method = RequestMethod.POST)
	public ModelAndView scheduleMeetingByDIC(
			@ModelAttribute("meetingScheduler") @Validated MeetingScheduler meetingScheduler, BindingResult result,
			Model model, HttpSession httpSession)
			throws AddressException, MessagingException, IOException, ParseException {

		MeetingScheduler meetingSchedulers = new MeetingScheduler();
		logger.debug("Meeting Schedule Start");

		SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yyyy");
		String time = meetingScheduler.getTime();
		String userid = (String) httpSession.getAttribute("userId");

		/*------------ NEED TO IMPLEMENT FOR NIC MAIL OR UDHOG BANDHU INFO------------------------------*/

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.debug", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.EnableSSL.enable", "true");
		if (userid != null && !userid.isEmpty()) {
			Optional<TblUsers> loginUser = loginService.getLoginIdById(userid);
			final String username = loginUser.get().getUserName();
			final String password = loginUser.get().getPassword();
			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(username));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(username));
			msg.setSubject("Meeting Scheduled for Application Review on : "
					+ dateFormate.format(meetingScheduler.getSchedulderDate()));
			msg.setContent("Meeting Scheduled", "text/html");
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart
					.setContent(
							"Department Name: " + meetingScheduler.getCommiteeDepartment() + "\n" + " Meeting Date: "
									+ dateFormate.format(meetingScheduler.getSchedulderDate()) + "\n" + " Meeting Time"
									+ time + "\n" + " Meeting Location: " + meetingScheduler.getSchedulerLocation(),
							"text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);
			Transport.send(msg);
		}

		meetingSchedulers.setId("MSDIC" + atomicInteger.getAndIncrement());
		meetingSchedulers.setCommiteeDepartment(meetingScheduler.getCommiteeDepartment());
		meetingSchedulers.setSchedulerSubject(meetingScheduler.getSchedulerSubject());
		meetingSchedulers.setSchedulderDate(meetingScheduler.getSchedulderDate());
		meetingSchedulers.setTime(time);
		meetingSchedulers.setSchedulerLocation(meetingScheduler.getSchedulerLocation());
		meetingSchedulers.setUserId(userid);
		if (userid != null && !userid.isEmpty()) {
			Optional<TblUsers> loginUser = loginService.getLoginIdById(userid);
			meetingSchedulers.setFromMail(loginUser.get().getUserName());
			meetingSchedulers.setToMail(loginUser.get().getUserName());
			meetingSchedulers.setCreatedBy(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
		}
		meetingSchedulers.setCreateDate(new Date());
		meetingSchedulers.setActive("active");
		meetingSchedulerService.saveMeetingScheduler(meetingSchedulers);
		model.addAttribute("meetingScheduler", new MeetingScheduler());
		return new ModelAndView("redirect:/meetingschedulebyDIC");
	}
}
