/**
 * Author:: Mohd Alam Created on::
 */
/*
 * package com.webapp.ims.dis.controller;
 * 
 * import java.text.ParseException; import java.text.SimpleDateFormat; import
 * java.util.ArrayList; import java.util.LinkedHashMap; import java.util.List;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpSession;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.servlet.ModelAndView; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes; import
 * org.springframework.web.servlet.view.RedirectView;
 * 
 * import com.webapp.ims.dis.model.EvaluateApplicationDisTbl11DisDtl; import
 * com.webapp.ims.dis.model.EvaluateApplicationDisTbl11SancdDtl; import
 * com.webapp.ims.dis.service.Tbl11DisDtlService; import
 * com.webapp.ims.dis.service.Tbl11SancDtlService;
 * 
 * 
 * 
 *//**
	 * @author dell
	 *//*
		 * 
		 * @Controller public class Table11ViewControlller { private final Logger logger
		 * = LoggerFactory.getLogger(Table11ViewControlller.class);
		 * 
		 * 
		 * 
		 * 
		 * @Autowired Tbl11SancDtlService Tbl11SancDtlService;
		 * 
		 * @Autowired Tbl11DisDtlService Tbl11DisDtlService;
		 * 
		 * @RequestMapping(value = "/loadSancdtextfieldintoMap", method =
		 * RequestMethod.GET) public RedirectView loadSancdMapValues(RedirectAttributes
		 * redir, HttpServletRequest request, HttpSession session) { String applId =
		 * (String)session.getValue("applId");
		 * logger.info("evaluateApplicationDis.... called"+applId );
		 * logger.debug("Evaluate Application Start");
		 * 
		 * LinkedHashMap<String,ArrayList<String>>Tbl11SacdDtlMap = new
		 * LinkedHashMap<String,ArrayList<String>>();
		 * List<EvaluateApplicationDisTbl11SancdDtl> tbl11dtl =
		 * Tbl11SancDtlService.getDataSancdDtlTbl11byAppid(applId+"DEV"); for(int i
		 * =0;i<tbl11dtl.size();i++) {
		 * Tbl11SacdDtlMap.put(tbl11dtl.get(i).getTbl11_Sacnd_Id(), new
		 * ArrayList<String>());
		 * 
		 * logger.info(tbl11dtl.get(i).getTbl11_Sacnd_Id());
		 * Tbl11SacdDtlMap.get(tbl11dtl.get(i).getTbl11_Sacnd_Id()).add(tbl11dtl.get(i).
		 * getTbl11_Sancd_LndrBank());
		 * Tbl11SacdDtlMap.get(tbl11dtl.get(i).getTbl11_Sacnd_Id()).add(""+tbl11dtl.get(
		 * i).getTbl11_Sancd_Date());
		 * Tbl11SacdDtlMap.get(tbl11dtl.get(i).getTbl11_Sacnd_Id()).add(INRNumberFormat.
		 * getValue(tbl11dtl.get(i).getTbl11_Sancd_Amt()));
		 * Tbl11SacdDtlMap.get(tbl11dtl.get(i).getTbl11_Sacnd_Id()).add(""+tbl11dtl.get(
		 * i).getTbl11_Sancd_IntrstRate()); }
		 * logger.info("tbl11dtl.size() :"+tbl11dtl.size()); RedirectView redirectView=
		 * new RedirectView("/evaluateApplicationDis",true);
		 * redir.addFlashAttribute("Tbl11SacdDtlMap",Tbl11SacdDtlMap); return
		 * redirectView; }
		 * 
		 * 
		 * @RequestMapping(value = "/loadDistextfieldintoMapTbl11", method =
		 * RequestMethod.GET) public RedirectView loadDisMapValues(RedirectAttributes
		 * redir, HttpServletRequest request, HttpSession session) { String applId =
		 * (String)session.getValue("applId"); LinkedHashMap<String,ArrayList<String>>
		 * Tbl11DisDtlMap = new LinkedHashMap<String,ArrayList<String>>();
		 * List<EvaluateApplicationDisTbl11DisDtl> tbl11dtl =
		 * Tbl11DisDtlService.getDataDisDtlTbl11byAppid(applId+"DEV"); for(int i
		 * =0;i<tbl11dtl.size();i++) {
		 * Tbl11DisDtlMap.put(tbl11dtl.get(i).getTbl11_Dis_Id(), new
		 * ArrayList<String>());
		 * 
		 * logger.info(tbl11dtl.get(i).getTbl11_Dis_Id());
		 * Tbl11DisDtlMap.get(tbl11dtl.get(i).getTbl11_Dis_Id()).add(tbl11dtl.get(i).
		 * getTbl11_Dis_LndrBank());
		 * Tbl11DisDtlMap.get(tbl11dtl.get(i).getTbl11_Dis_Id()).add(""+tbl11dtl.get(i).
		 * getTbl11_Dis_Date());
		 * Tbl11DisDtlMap.get(tbl11dtl.get(i).getTbl11_Dis_Id()).add(INRNumberFormat.
		 * getValue(tbl11dtl.get(i).getTbl11_Dis_Amt())); }
		 * logger.info("tbl11dtl.size() :"+tbl11dtl.size()); RedirectView redirectView=
		 * new RedirectView("/evaluateApplicationDis",true);
		 * redir.addFlashAttribute("Tbl11DisDtlMap",Tbl11DisDtlMap); return
		 * redirectView; }
		 * 
		 * @RequestMapping(value = "/SaveDataTbl11SancDtl", method = RequestMethod.POST)
		 * public ModelAndView SaveTbl11SancDtl (
		 * 
		 * @RequestParam("Sancdldrbank") String Sancdldrbank,
		 * 
		 * @RequestParam("SancdDate") String SancdDate,
		 * 
		 * @RequestParam("SancdAmt") String SancdAmt,
		 * 
		 * @RequestParam("SancdInstRate") String SancdInstRate,
		 * 
		 * @RequestParam(value = "applicantId", required = false) String applId,
		 * 
		 * @ModelAttribute("SpringWeb")EvaluateApplicationDisTbl11SancdDtl
		 * Tbl11SancdDtl,HttpSession session ) {
		 * logger.info("Tbl11SancdDtl method start");
		 * logger.info("UserId"+(String)session.getAttribute("userId"));
		 * Tbl11SancdDtl.setEvaluate_Id(applId+"DEV");
		 * Tbl11SancdDtl.setTbl11_Sancd_LndrBank(Sancdldrbank); try {
		 * Tbl11SancdDtl.setTbl11_Sancd_Date(new
		 * SimpleDateFormat("yyyy-MM-dd").parse(SancdDate)); } catch (ParseException e)
		 * { e.printStackTrace(); }
		 * Tbl11SancdDtl.setTbl11_Sancd_Amt(Double.valueOf(SancdAmt));
		 * Tbl11SancdDtl.setTbl11_Sancd_IntrstRate(Double.valueOf(SancdInstRate));
		 * Tbl11SancdDtl.setCreator_Id((String)session.getAttribute("userId"));
		 * Tbl11SancdDtl.setCreator_Time(Tbl11SancDtlService.getCurrentTime());
		 * Tbl11SancDtlService.saveSancdAmountDtlTbl11(Tbl11SancdDtl);
		 * session.setAttribute("applId", applId); return new
		 * ModelAndView("redirect:/loadSancdtextfieldintoMap"); }
		 * 
		 * @RequestMapping(value = "/RemoveDataTbl11SancDtl", method =
		 * RequestMethod.GET) public ModelAndView RemoveSancData(@RequestParam(value =
		 * "applicantId", required = false) String applId) {
		 * logger.info("RemoveDataTbl11SancDtl method start");
		 * logger.info("applId :"+applId);
		 * Tbl11SancDtlService.removeSancdAmountDtlTbl11(applId); return new
		 * ModelAndView("redirect:/loadSancdtextfieldintoMap"); }
		 * 
		 * @RequestMapping(value = "/RemoveDataTbl11DisDtl", method = RequestMethod.GET)
		 * public ModelAndView RemoveDisData(@RequestParam(value = "applicantId",
		 * required = true) String applId) {
		 * logger.info("RemoveDataTbl11DisDtl method start");
		 * logger.info("applId :"+applId);
		 * Tbl11DisDtlService.removeDisAmountDtlTbl11(applId); return new
		 * ModelAndView("redirect:/loadDistextfieldintoMapTbl11"); }
		 * 
		 * 
		 * @RequestMapping(value = "/SaveDataTbl11DisDtl", method = RequestMethod.POST)
		 * public ModelAndView SaveTbl11DisDtl (
		 * 
		 * @RequestParam("Disldrbank") String Disldrbank,
		 * 
		 * @RequestParam("DisDate") String DisDate,
		 * 
		 * @RequestParam("DisAmt") String DisAmt,
		 * 
		 * @RequestParam(value = "applicantId", required = true) String applId,
		 * 
		 * @ModelAttribute("SpringWeb")EvaluateApplicationDisTbl11DisDtl
		 * Tbl11DisDtl,HttpSession session ) { logger.info("applId :"+applId);
		 * logger.info("Disldrbank :"+Disldrbank); logger.info("DisDate :"+DisDate);
		 * logger.info("DisAmt :"+DisAmt);
		 * logger.info("applicantId :"+session.getValue("applicantId"));
		 * 
		 * Tbl11DisDtl.setEvaluate_Id(applId+"DEV");
		 * Tbl11DisDtl.setTbl11_Dis_LndrBank(Disldrbank); try {
		 * Tbl11DisDtl.setTbl11_Dis_Date(new
		 * SimpleDateFormat("yyyy-MM-dd").parse(DisDate)); } catch (ParseException e) {
		 * e.printStackTrace(); } Tbl11DisDtl.setTbl11_Dis_Amt(Double.valueOf(DisAmt));
		 * Tbl11DisDtl.setCreator_Id((String)session.getAttribute("userId"));
		 * Tbl11DisDtl.setCreator_Time(Tbl11DisDtlService.getCurrentTime());
		 * Tbl11DisDtlService.saveDisAmountDtlTbl11(Tbl11DisDtl);
		 * session.setAttribute("applId", applId); return new
		 * ModelAndView("redirect:/loadDistextfieldintoMapTbl11"); }
		 * 
		 * @RequestMapping(value = "/SaveDataTbl11DisDtlNext", method =
		 * RequestMethod.GET) public ModelAndView SaveTbl11DisDtlNext (
		 * 
		 * @RequestParam("nextDisBankName") String nextDisBankName,
		 * 
		 * @RequestParam("nextDisDate") String nextDisDate,
		 * 
		 * @RequestParam("nextDisAmt") String nextDisAmt,
		 * 
		 * @RequestParam(value = "applicantId", required = true) String applId,
		 * 
		 * @ModelAttribute("SpringWeb")EvaluateApplicationDisTbl11DisDtl
		 * Tbl11DisDtl,HttpSession session ) { nextDisDate = nextDisDate.replaceAll(",",
		 * ""); nextDisAmt = nextDisAmt.replaceAll(",", ""); nextDisBankName=
		 * nextDisBankName.replaceAll(",", "");
		 * logger.info("NextDisDate :"+nextDisDate);
		 * logger.info("NextDisAmt :"+nextDisAmt);
		 * logger.info("nextDisBankName :"+nextDisBankName);
		 * 
		 * Tbl11DisDtl.setEvaluate_Id(applId+"DEV");
		 * Tbl11DisDtl.setTbl11_Dis_LndrBank(nextDisBankName); try {
		 * Tbl11DisDtl.setTbl11_Dis_Date(new
		 * SimpleDateFormat("yyyy-MM-dd").parse(nextDisDate)); } catch (ParseException
		 * e) { e.printStackTrace(); }
		 * Tbl11DisDtl.setTbl11_Dis_Amt(Double.valueOf(nextDisAmt));
		 * Tbl11DisDtl.setCreator_Id((String)session.getAttribute("userId"));
		 * Tbl11DisDtl.setCreator_Time(Tbl11DisDtlService.getCurrentTime());
		 * Tbl11DisDtlService.saveDisAmountDtlTbl11(Tbl11DisDtl);
		 * session.setAttribute("applId", applId); return new
		 * ModelAndView("redirect:/loadDistextfieldintoMapTbl11"); } }
		 */