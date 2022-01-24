package com.webapp.ims.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.webapp.ims.model.PrepareAgendaNotes;

public interface PrepareAgendaNoteService {

 

	public PrepareAgendaNotes getPrepareByAppliId(String appliId);

	public List<PrepareAgendaNotes> findPrepAgendaNotesListByUserId(String userid);

	public void common(Model model, HttpServletRequest request, HttpSession session);

	

	List<PrepareAgendaNotes> getAllPrepareAgendaNote();

	public List<PrepareAgendaNotes> getPrepareAgendaNote(HttpSession session);

	public List<PrepareAgendaNotes> getPrepareAgendaNoteBySendforApproval(HttpSession session);

	List<PrepareAgendaNotes> getPreAgendaNote(HttpSession session);

	List<PrepareAgendaNotes> getPrepareViewAgendaNoteList(HttpSession session);

	List<PrepareAgendaNotes> getPrepareAgendaNoteSubmit(HttpSession session);
	
}

