package com.webapp.ims.dis.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.webapp.ims.dis.model.DISPrepareAgendaNotes;

@Service
public interface DisPrepareAgendaNoteService {
	public void common(Model model, HttpServletRequest request, HttpSession session);

	public DISPrepareAgendaNotes findDisPrepAgendaNotesByAppliId(String appliId);

	public DISPrepareAgendaNotes savePrepareAgendaNotes(DISPrepareAgendaNotes disPrepareAgendaNotes);

	public List<DISPrepareAgendaNotes> findDisPrepAgendaNotesByUserId(String userid);

	public List<DISPrepareAgendaNotes> getAllDisPrepareAgendaNote();

}