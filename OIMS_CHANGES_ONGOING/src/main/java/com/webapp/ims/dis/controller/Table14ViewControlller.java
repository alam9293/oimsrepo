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
 * import com.webapp.ims.dis.model.EvaluateApplicationDisTbl14DisDtl; import
 * com.webapp.ims.dis.model.EvaluateApplicationDisTbl14SancdDtl; import
 * com.webapp.ims.dis.service.Tbl14DisDtlService; import
 * com.webapp.ims.dis.service.Tbl14SancDtlService; import
 * com.webapp.ims.utils.INRNumberFormat;
 * 
 * 
 * 
 *//**
	 * @author dell
	 *//*
		 * 
		 * @Controller public class Table14ViewControlller { private final Logger logger
		 * = LoggerFactory.getLogger(Table14ViewControlller.class);
		 * 
		 * 
		 * 
		 * 
		 * @Autowired Tbl14SancDtlService Tbl14SancDtlService;
		 * 
		 * @Autowired Tbl14DisDtlService Tbl14DisDtlService;
		 * 
		 * @RequestMapping(value = "/loadSancdtextfieldintoMapTbl14", method =
		 * RequestMethod.GET) public RedirectView loadSancdMapValues(RedirectAttributes
		 * redir, HttpServletRequest request, HttpSession session) { String applId =
		 * (String)session.getValue("applId");
		 * logger.info("evaluateApplicationDis.... called"+applId );
		 * logger.debug("Evaluate Application Start");
		 * 
		 * LinkedHashMap<String,ArrayList<String>>Tbl14SacdDtlMap = new
		 * LinkedHashMap<String,ArrayList<String>>();
		 * List<EvaluateApplicationDisTbl14SancdDtl> Tbl14dtl =
		 * Tbl14SancDtlService.getDataSancdDtlTbl14byAppid(applId+"DEV"); for(int i
		 * =0;i<Tbl14dtl.size();i++) {
		 * Tbl14SacdDtlMap.put(Tbl14dtl.get(i).getTbl14_Sancd_Id(), new
		 * ArrayList<String>());
		 * 
		 * logger.info(Tbl14dtl.get(i).getTbl14_Sancd_Id());
		 * Tbl14SacdDtlMap.get(Tbl14dtl.get(i).getTbl14_Sancd_Id()).add(Tbl14dtl.get(i).
		 * getTbl14_Sancd_LndrBank());
		 * Tbl14SacdDtlMap.get(Tbl14dtl.get(i).getTbl14_Sancd_Id()).add(""+Tbl14dtl.get(
		 * i).getTbl14_Sancd_Date());
		 * Tbl14SacdDtlMap.get(Tbl14dtl.get(i).getTbl14_Sancd_Id()).add(INRNumberFormat.
		 * getValue(Tbl14dtl.get(i).getTbl14_Sancd_Amt()));
		 * Tbl14SacdDtlMap.get(Tbl14dtl.get(i).getTbl14_Sancd_Id()).add(""+Tbl14dtl.get(
		 * i).getTbl14_Sancd_IntrstRate()); }
		 * logger.info("Tbl14dtl.size() :"+Tbl14dtl.size()); RedirectView redirectView=
		 * new RedirectView("/evaluateApplicationDis",true);
		 * redir.addFlashAttribute("Tbl14SacdDtlMap",Tbl14SacdDtlMap); return
		 * redirectView; }
		 * 
		 * 
		 * @RequestMapping(value = "/loadDistextfieldintoMapTbl14", method =
		 * RequestMethod.GET) public RedirectView loadDisMapValues(RedirectAttributes
		 * redir, HttpServletRequest request, HttpSession session) { String applId =
		 * (String)session.getValue("applId");
		 * logger.info("evaluateApplicationDis.... called"+applId ); String oldbank
		 * ="",newbank =""; logger.debug("Evaluate Application Start");
		 * LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> Tbl14DisBankMap
		 * = new LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>();
		 * LinkedHashMap<String,ArrayList<String>> Tbl14DisDtlMap = new
		 * LinkedHashMap<String,ArrayList<String>>();
		 * List<EvaluateApplicationDisTbl14DisDtl> Tbl14dtl =
		 * Tbl14DisDtlService.getDataDisDtlTbl14byAppid(applId+"DEV"); for(int i
		 * =0;i<Tbl14dtl.size();i++) { oldbank =
		 * Tbl14dtl.get(i).getTbl14_Dis_LndrBank();
		 * if(!oldbank.equalsIgnoreCase(newbank)) {
		 * Tbl14DisBankMap.put(Tbl14dtl.get(i).getTbl14_Dis_LndrBank(), Tbl14DisDtlMap);
		 * } Tbl14DisDtlMap.put(Tbl14dtl.get(i).getTbl14_Dis_Id(), new
		 * ArrayList<String>()); logger.info(Tbl14dtl.get(i).getTbl14_Dis_Id());
		 * Tbl14DisDtlMap.get(Tbl14dtl.get(i).getTbl14_Dis_Id()).add(Tbl14dtl.get(i).
		 * getTbl14_Dis_LndrBank());
		 * Tbl14DisDtlMap.get(Tbl14dtl.get(i).getTbl14_Dis_Id()).add(""+Tbl14dtl.get(i).
		 * getTbl14_Dis_Date());
		 * Tbl14DisDtlMap.get(Tbl14dtl.get(i).getTbl14_Dis_Id()).add(INRNumberFormat.
		 * getValue(Tbl14dtl.get(i).getTbl14_Dis_Amt())); newbank = oldbank; }
		 * logger.info("Tbl14dtl.size() :"+Tbl14dtl.size()); RedirectView redirectView=
		 * new RedirectView("/evaluateApplicationDis",true);
		 * redir.addFlashAttribute("Tbl14DisDtlMap",Tbl14DisDtlMap);
		 * redir.addFlashAttribute("TableName","Table14"); return redirectView; }
		 * 
		 * @RequestMapping(value = "/SaveDataTbl14SancDtl", method = RequestMethod.POST)
		 * public ModelAndView SaveTbl14SancDtl (
		 * 
		 * @RequestParam("SancdldrbankTbl14") String Sancdldrbank,
		 * 
		 * @RequestParam("SancdDateTbl14") String SancdDate,
		 * 
		 * @RequestParam("SancdAmtTbl14") String SancdAmt,
		 * 
		 * @RequestParam("SancdInstRateTbl14") String SancdInstRate,
		 * 
		 * @RequestParam(value = "applicantId", required = true) String applId,
		 * 
		 * @ModelAttribute("SpringWeb")EvaluateApplicationDisTbl14SancdDtl
		 * Tbl14SancdDtl,HttpSession session ) {
		 * logger.info("Tbl14SancdDtl method start");
		 * Tbl14SancdDtl.setEvaluate_Id(applId+"DEV");
		 * Tbl14SancdDtl.setTbl14_Sancd_LndrBank(Sancdldrbank); try {
		 * Tbl14SancdDtl.setTbl14_Sancd_Date(new
		 * SimpleDateFormat("yyyy-MM-dd").parse(SancdDate)); } catch (ParseException e)
		 * { e.printStackTrace(); }
		 * Tbl14SancdDtl.setTbl14_Sancd_Amt(Double.valueOf(SancdAmt));
		 * Tbl14SancdDtl.setTbl14_Sancd_IntrstRate(Double.valueOf(SancdInstRate));
		 * Tbl14SancdDtl.setCreator_Id((String)session.getAttribute("userId"));
		 * Tbl14SancdDtl.setCreator_Time(Tbl14SancDtlService.getCurrentTime());
		 * Tbl14SancDtlService.saveSancdAmountDtlTbl14(Tbl14SancdDtl);
		 * session.setAttribute("applId", applId); return new
		 * ModelAndView("redirect:/loadSancdtextfieldintoMapTbl14"); }
		 * 
		 * @RequestMapping(value = "/RemoveDataTbl14SancDtl", method =
		 * RequestMethod.GET) public ModelAndView RemoveSancData(@RequestParam(value =
		 * "applicantId", required = false) String applId) {
		 * logger.info("RemoveDataTbl14SancDtl method start");
		 * logger.info("applId :"+applId);
		 * Tbl14SancDtlService.removeSancdAmountDtlTbl14(applId); return new
		 * ModelAndView("redirect:/loadSancdtextfieldintoMapTbl14"); }
		 * 
		 * @RequestMapping(value = "/RemoveDataTbl14DisDtl", method = RequestMethod.GET)
		 * public ModelAndView RemoveDisData(@RequestParam(value = "applicantId",
		 * required = true) String applId) {
		 * logger.info("RemoveDataTbl14DisDtl method start");
		 * logger.info("applId :"+applId);
		 * Tbl14DisDtlService.removeDisAmountDtlTbl14(applId); return new
		 * ModelAndView("redirect:/loadDistextfieldintoMapTbl14"); }
		 * 
		 * 
		 * @RequestMapping(value = "/SaveDataTbl14DisDtl", method = RequestMethod.POST)
		 * public ModelAndView SaveTbl14DisDtl (
		 * 
		 * @RequestParam("DisldrbankTbl14") String Disldrbank,
		 * 
		 * @RequestParam("DisDateTbl14") String DisDate,
		 * 
		 * @RequestParam("DisAmtTbl14") String DisAmt,
		 * 
		 * @RequestParam(value = "applicantId", required = true) String applId,
		 * 
		 * @ModelAttribute("SpringWeb")EvaluateApplicationDisTbl14DisDtl
		 * Tbl14DisDtl,HttpSession session ) { logger.info("applId :"+applId);
		 * logger.info("Disldrbank :"+Disldrbank); logger.info("DisDate :"+DisDate);
		 * logger.info("DisAmt :"+DisAmt);
		 * 
		 * Tbl14DisDtl.setEvaluate_Id(applId+"DEV");
		 * Tbl14DisDtl.setTbl14_Dis_LndrBank(Disldrbank); try {
		 * Tbl14DisDtl.setTbl14_Dis_Date(new
		 * SimpleDateFormat("yyyy-MM-dd").parse(DisDate)); } catch (ParseException e) {
		 * e.printStackTrace(); } Tbl14DisDtl.setTbl14_Dis_Amt(Double.valueOf(DisAmt));
		 * Tbl14DisDtl.setCreator_Id((String)session.getAttribute("userId"));
		 * Tbl14DisDtl.setCreator_Time(Tbl14DisDtlService.getCurrentTime());
		 * Tbl14DisDtlService.saveDisAmountDtlTbl14(Tbl14DisDtl);
		 * session.setAttribute("applId", applId); return new
		 * ModelAndView("redirect:/loadDistextfieldintoMapTbl14"); }
		 * 
		 * 
		 * @RequestMapping(value = "/SaveDataTbl14DisDtlNext", method =
		 * RequestMethod.GET) public ModelAndView SaveTbl14DisDtlNext (
		 * 
		 * @RequestParam("nextDisBankName") String nextDisBankName,
		 * 
		 * @RequestParam("nextDisDate") String nextDisDate,
		 * 
		 * @RequestParam("nextDisAmt") String nextDisAmt,
		 * 
		 * @RequestParam(value = "applicantId", required = true) String applId,
		 * 
		 * @ModelAttribute("SpringWeb")EvaluateApplicationDisTbl14DisDtl
		 * Tbl14DisDtl,HttpSession session ) { nextDisDate = nextDisDate.replaceAll(",",
		 * ""); nextDisAmt = nextDisAmt.replaceAll(",", ""); nextDisBankName=
		 * nextDisBankName.replaceAll(",", "");
		 * logger.info("NextDisDate :"+nextDisDate);
		 * logger.info("NextDisAmt :"+nextDisAmt);
		 * logger.info("nextDisBankName :"+nextDisBankName);
		 * 
		 * Tbl14DisDtl.setEvaluate_Id(applId+"DEV");
		 * Tbl14DisDtl.setTbl14_Dis_LndrBank(nextDisBankName); try {
		 * Tbl14DisDtl.setTbl14_Dis_Date(new
		 * SimpleDateFormat("yyyy-MM-dd").parse(nextDisDate)); } catch (ParseException
		 * e) { e.printStackTrace(); }
		 * Tbl14DisDtl.setTbl14_Dis_Amt(Double.valueOf(nextDisAmt));
		 * Tbl14DisDtl.setCreator_Id((String)session.getAttribute("userId"));
		 * Tbl14DisDtl.setCreator_Time(Tbl14DisDtlService.getCurrentTime());
		 * Tbl14DisDtlService.saveDisAmountDtlTbl14(Tbl14DisDtl);
		 * session.setAttribute("applId", applId); return new
		 * ModelAndView("redirect:/loadDistextfieldintoMapTbl14"); } }
		 */