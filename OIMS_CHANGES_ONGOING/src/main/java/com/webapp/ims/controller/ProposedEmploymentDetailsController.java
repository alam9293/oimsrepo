package com.webapp.ims.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.repository.SkillUnSkilledRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * @author dell
 *
 */
@Controller
@Component
public class ProposedEmploymentDetailsController {

	private List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntList = new ArrayList<SkilledUnSkilledEmployemnt>();
	int rowIndex = 0;
	boolean isRowIndex = false;
	@Autowired
	private ProposedEmploymentDetailsService proposedEmploymentDetailsService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private InvestmentDetailsService investDs;
	SoapConsumeEx soapdetails = new SoapConsumeEx();
	@Autowired
	private SkillUnSkilledRepository skillUnSkilledRepository;
	@Autowired
	private ApplicantDetailsService applicantDetailsService;
	@Autowired
	BusinessEntityDetailsService businessService;
	/**
	 * show Proposed Employement Details
	 * 
	 * @author rathour
	 *
	 */
	@RequestMapping(value = "/skilUnskEmplDet", method = RequestMethod.GET)
	public ModelAndView show(Model model,HttpSession session) {

		String propId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : responce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				propId = entry.getValue() + "PE1";
			}

		}
		try {

			if (propId != null && !propId.isEmpty()) {
				ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService
						.getPropEmpById(propId);
				if (proposedEmploymentDetail != null) {
					List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = proposedEmploymentDetail
							.getSkilledUnSkilledEmployemnt();
					model.addAttribute("proposedEmploymentDetails", proposedEmploymentDetail);
					skilledUnSkilledEmployemntList.clear();
					skilledUnSkilledEmployemntList.addAll(skilledUnSkilledEmployemntslist);
					model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					totalSkilledAndUnSkilledEmploment(model);
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					model.addAttribute("propoEmplDetailsId",proposedEmploymentDetail.getId());

				} else {
					skilledUnSkilledEmployemntList.clear();
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("proposed_employment_details");
	}

	/**
	 * add skilled/unskilled list
	 * 
	 * @author ratho
	 *
	 */

	@RequestMapping(value = "/addSkilUnSkilEmploy", method = RequestMethod.POST)
	public ModelAndView addSkilUnSkilEmploy(
			@ModelAttribute("skilledUnSkilledEmployemnt") @Validated SkilledUnSkilledEmployemnt skilledUnSkilledEmployemnt,
			Model model, HttpSession session) {

		boolean flag = false;
		final String MEGA = "Mega";
		final String MEGAPLUS = "Mega Plus";
		final String SUPERMEGA = "Super Mega";
		long skilledEmploymentMale = 0;
		long skilledEmploymentFemale = 0;
		long totalSkilledEmployment = 0;
		long unSkilledEmploymentMale = 0;
		long unSkilledEmploymentFemale = 0;
		long totalUnSkilledEmployment = 0;
		String projId = "";
		String invId = "";
		String propId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : responce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				projId = entry.getValue() + "P1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				invId = entry.getValue() + "I1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				propId = entry.getValue() + "PE1";
			}
		}
		if (isRowIndex) {
             
				ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propId);
				if (proposedEmploymentDetail != null)
				{
					List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = proposedEmploymentDetail.getSkilledUnSkilledEmployemnt();
					for (SkilledUnSkilledEmployemnt match : skilledUnSkilledEmployemntslist) {
						if (match.getSkilledUnskilled().equalsIgnoreCase("Skilled")
								&& skilledUnSkilledEmployemnt.getSkilledUnskilled().equalsIgnoreCase("Skilled") && match.getNumberofMaleEmployees().equals(skilledUnSkilledEmployemnt.getNumberofMaleEmployees())
								&& match.getNumberOfFemaleEmployees().equals(skilledUnSkilledEmployemnt.getNumberOfFemaleEmployees()) && match.getNumberOfGeneral().equals(skilledUnSkilledEmployemnt.getNumberOfGeneral())
								&& match.getNumberOfSc().equals(skilledUnSkilledEmployemnt.getNumberOfSc())&& match.getNumberOfSt().equals(skilledUnSkilledEmployemnt.getNumberOfSt()) && match.getNumberOfObc().equals(skilledUnSkilledEmployemnt.getNumberOfObc())
								&& match.getNumberOfBpl().equals(skilledUnSkilledEmployemnt.getNumberOfBpl())&& match.getNumberOfDivyang().equals(skilledUnSkilledEmployemnt.getNumberOfDivyang())) {
							isRowIndex = false;	
							model.addAttribute("message", "Skilled Employment Record already exist");
						} else if (match.getSkilledUnskilled().equalsIgnoreCase("Unskilled")
								&& skilledUnSkilledEmployemnt.getSkilledUnskilled().equalsIgnoreCase("Unskilled")&& match.getNumberofMaleEmployees().equals(skilledUnSkilledEmployemnt.getNumberofMaleEmployees())
								&& match.getNumberOfFemaleEmployees().equals(skilledUnSkilledEmployemnt.getNumberOfFemaleEmployees()) && match.getNumberOfGeneral().equals(skilledUnSkilledEmployemnt.getNumberOfGeneral())
								&& match.getNumberOfSc().equals(skilledUnSkilledEmployemnt.getNumberOfSc())&& match.getNumberOfSt().equals(skilledUnSkilledEmployemnt.getNumberOfSt()) && match.getNumberOfObc().equals(skilledUnSkilledEmployemnt.getNumberOfObc())
								&& match.getNumberOfBpl().equals(skilledUnSkilledEmployemnt.getNumberOfBpl())&& match.getNumberOfDivyang().equals(skilledUnSkilledEmployemnt.getNumberOfDivyang())) {
							isRowIndex = false;	
							model.addAttribute("message", "UnSkilled Employment Record already exist");
						}
					}
					if (isRowIndex) {
						skilledUnSkilledEmployemntList.remove(rowIndex);
				        updateskillUnskilled(rowIndex, skilledUnSkilledEmployemnt);           
				        isRowIndex = false;			
				        model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					}
				}
				
		} else {
			for (SkilledUnSkilledEmployemnt match : skilledUnSkilledEmployemntList) {
				if (match.getSkilledUnskilled().equalsIgnoreCase("Skilled")
						&& skilledUnSkilledEmployemnt.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
					flag = true;
					model.addAttribute("message", "Skilled Employment Record already exist");
				} else if (match.getSkilledUnskilled().equalsIgnoreCase("Unskilled")
						&& skilledUnSkilledEmployemnt.getSkilledUnskilled().equalsIgnoreCase("Unskilled")) {
					flag = true;
					model.addAttribute("message", "UnSkilled Employment Record already exist");
				}
			}
			if (flag == false) {
				getSkilledEmploymentList(skilledUnSkilledEmployemnt);
			}

		}
		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
		InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(invId);

		if (skilledUnSkilledEmployemntList.size() > 0) {

			for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
				if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
					if (count.getNumberofMaleEmployees() != null) {
						skilledEmploymentMale = skilledEmploymentMale + (count.getNumberofMaleEmployees());
					}
					if (count.getNumberOfFemaleEmployees() != null) {
						skilledEmploymentFemale = skilledEmploymentFemale + (count.getNumberOfFemaleEmployees());
					}
					totalSkilledEmployment = (skilledEmploymentMale + skilledEmploymentFemale);
					model.addAttribute("skilledEmploymentMale", skilledEmploymentMale);
					model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale);
					model.addAttribute("totalSkilledEmployment", totalSkilledEmployment);
					model.addAttribute("skilledDisplay", "skilledDisplay");

				} else {
					if (count.getNumberofMaleEmployees() != null) {
						unSkilledEmploymentMale = unSkilledEmploymentMale + (count.getNumberofMaleEmployees());
					}
					if (count.getNumberOfFemaleEmployees() != null) {
						unSkilledEmploymentFemale = unSkilledEmploymentFemale + (count.getNumberOfFemaleEmployees());
					}
					totalUnSkilledEmployment = (unSkilledEmploymentMale + unSkilledEmploymentFemale);
					model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale);
					model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale);
					model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment);
					model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
				}
			}

		}
		if (ProjectDetail != null && invdtlFromDb != null) {

			// MEGA CATAGORY......

			if ((ProjectDetail.getResionName().equalsIgnoreCase("Poorvanchal")
					&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGA))
					|| (ProjectDetail.getResionName().equalsIgnoreCase("Bundelkhand")
							&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGA))) {
				long skilledEmploymentMale1 = 0;
				long skilledEmploymentFemale1 = 0;
				long totalSkilledEmployment1 = 0;
				long unSkilledEmploymentMale1 = 0;
				long unSkilledEmploymentFemale1 = 0;
				long totalUnSkilledEmployment1 = 0;
				List<SkilledUnSkilledEmployemnt> list = new ArrayList<SkilledUnSkilledEmployemnt>();
				if (((totalSkilledEmployment) + (totalUnSkilledEmployment)) > 500) {
					if (skilledUnSkilledEmployemntList.size() == 1) {
						model.addAttribute("unSkilledDisplay", "");
						model.addAttribute("skilledDisplay", "");
						model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					} else {

						for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
							if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
								if (count.getNumberofMaleEmployees() != null) {
									skilledEmploymentMale1 = skilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									skilledEmploymentFemale1 = skilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalSkilledEmployment1 = (skilledEmploymentMale1 + skilledEmploymentFemale1);
								model.addAttribute("skilledEmploymentMale", skilledEmploymentMale1);
								model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale1);
								model.addAttribute("totalSkilledEmployment", totalSkilledEmployment1);
								model.addAttribute("skilledDisplay", "skilledDisplay");
								model.addAttribute("unSkilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							} else {
								if (count.getNumberofMaleEmployees() != null) {
									unSkilledEmploymentMale1 = unSkilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									unSkilledEmploymentFemale1 = unSkilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalUnSkilledEmployment1 = (unSkilledEmploymentMale1 + unSkilledEmploymentFemale1);
								model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale1);
								model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale1);
								model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment1);
								model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
								model.addAttribute("skilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalUnSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							}
						}
						skilledUnSkilledEmployemntList.clear();
						getSkilledEmploymentList(skilledUnSkilledEmployemnt);
					}

					model.addAttribute("skilledUnSkilledEmployemntList", list);
					model.addAttribute("message", "Total Number of Employment should not be greater than 500");
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					return new ModelAndView("proposed_employment_details");
				}
			} else if ((ProjectDetail.getResionName().equalsIgnoreCase("Madhyanchal")
					&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGA))
					|| (ProjectDetail.getResionName().equalsIgnoreCase("Paschimanchal")
							&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGA))) {
				long skilledEmploymentMale1 = 0;
				long skilledEmploymentFemale1 = 0;
				long totalSkilledEmployment1 = 0;
				long unSkilledEmploymentMale1 = 0;
				long unSkilledEmploymentFemale1 = 0;
				long totalUnSkilledEmployment1 = 0;
				List<SkilledUnSkilledEmployemnt> list = new ArrayList<SkilledUnSkilledEmployemnt>();
				if (((totalSkilledEmployment) + (totalUnSkilledEmployment)) > 750) {
					if (skilledUnSkilledEmployemntList.size() == 1) {
						model.addAttribute("unSkilledDisplay", "");
						model.addAttribute("skilledDisplay", "");
						skilledUnSkilledEmployemntList.clear();
						model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					} else {

						for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
							if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
								if (count.getNumberofMaleEmployees() != null) {
									skilledEmploymentMale1 = skilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									skilledEmploymentFemale1 = skilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalSkilledEmployment1 = (skilledEmploymentMale1 + skilledEmploymentFemale1);
								model.addAttribute("skilledEmploymentMale", skilledEmploymentMale1);
								model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale1);
								model.addAttribute("totalSkilledEmployment", totalSkilledEmployment1);
								model.addAttribute("skilledDisplay", "skilledDisplay");
								model.addAttribute("unSkilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							} else {
								if (count.getNumberofMaleEmployees() != null) {
									unSkilledEmploymentMale1 = unSkilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									unSkilledEmploymentFemale1 = unSkilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalUnSkilledEmployment1 = (unSkilledEmploymentMale1 + unSkilledEmploymentFemale1);
								model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale1);
								model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale1);
								model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment1);
								model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
								model.addAttribute("skilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalUnSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							}
						}
						skilledUnSkilledEmployemntList.clear();
						getSkilledEmploymentList(skilledUnSkilledEmployemnt);
					}

					model.addAttribute("skilledUnSkilledEmployemntList", list);
					model.addAttribute("message", "Total Number of Employment should not be greater than 750");
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					return new ModelAndView("proposed_employment_details");
				}
			} else if ((ProjectDetail.getDistrictName().equalsIgnoreCase("Gautambudh nagar")
					&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGA))
					|| (ProjectDetail.getDistrictName().equalsIgnoreCase("Ghaziabad")
							&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGA))) {
				long skilledEmploymentMale1 = 0;
				long skilledEmploymentFemale1 = 0;
				long totalSkilledEmployment1 = 0;
				long unSkilledEmploymentMale1 = 0;
				long unSkilledEmploymentFemale1 = 0;
				long totalUnSkilledEmployment1 = 0;
				List<SkilledUnSkilledEmployemnt> list = new ArrayList<SkilledUnSkilledEmployemnt>();
				if (((totalSkilledEmployment) + (totalUnSkilledEmployment)) > 1000) {
					if (skilledUnSkilledEmployemntList.size() == 1) {
						model.addAttribute("unSkilledDisplay", "");
						model.addAttribute("skilledDisplay", "");
						model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					} else {

						for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
							if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
								if (count.getNumberofMaleEmployees() != null) {
									skilledEmploymentMale1 = skilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									skilledEmploymentFemale1 = skilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalSkilledEmployment1 = (skilledEmploymentMale1 + skilledEmploymentFemale1);
								model.addAttribute("skilledEmploymentMale", skilledEmploymentMale1);
								model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale1);
								model.addAttribute("totalSkilledEmployment", totalSkilledEmployment1);
								model.addAttribute("skilledDisplay", "skilledDisplay");
								model.addAttribute("unSkilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							} else {
								if (count.getNumberofMaleEmployees() != null) {
									unSkilledEmploymentMale1 = unSkilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									unSkilledEmploymentFemale1 = unSkilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalUnSkilledEmployment1 = (unSkilledEmploymentMale1 + unSkilledEmploymentFemale1);
								model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale1);
								model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale1);
								model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment1);
								model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
								model.addAttribute("skilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalUnSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							}
						}
						skilledUnSkilledEmployemntList.clear();
						getSkilledEmploymentList(skilledUnSkilledEmployemnt);
					}

					model.addAttribute("skilledUnSkilledEmployemntList", list);
					model.addAttribute("message", "Total Number of Employment should not be greater than 1000");
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					return new ModelAndView("proposed_employment_details");
				}
			}

			// Mega Plus

			if ((ProjectDetail.getResionName().equalsIgnoreCase("Poorvanchal")
					&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGAPLUS))
					|| (ProjectDetail.getResionName().equalsIgnoreCase("Bundelkhand")
							&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGAPLUS))) {
				long skilledEmploymentMale1 = 0;
				long skilledEmploymentFemale1 = 0;
				long totalSkilledEmployment1 = 0;
				long unSkilledEmploymentMale1 = 0;
				long unSkilledEmploymentFemale1 = 0;
				long totalUnSkilledEmployment1 = 0;
				List<SkilledUnSkilledEmployemnt> list = new ArrayList<SkilledUnSkilledEmployemnt>();
				if (((totalSkilledEmployment) + (totalUnSkilledEmployment)) > 1000) {
					if (skilledUnSkilledEmployemntList.size() == 1) {
						model.addAttribute("unSkilledDisplay", "");
						model.addAttribute("skilledDisplay", "");
						model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					} else {

						for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
							if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
								if (count.getNumberofMaleEmployees() != null) {
									skilledEmploymentMale1 = skilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									skilledEmploymentFemale1 = skilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalSkilledEmployment1 = (skilledEmploymentMale1 + skilledEmploymentFemale1);
								model.addAttribute("skilledEmploymentMale", skilledEmploymentMale1);
								model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale1);
								model.addAttribute("totalSkilledEmployment", totalSkilledEmployment1);
								model.addAttribute("skilledDisplay", "skilledDisplay");
								model.addAttribute("unSkilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							} else {
								if (count.getNumberofMaleEmployees() != null) {
									unSkilledEmploymentMale1 = unSkilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									unSkilledEmploymentFemale1 = unSkilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalUnSkilledEmployment1 = (unSkilledEmploymentMale1 + unSkilledEmploymentFemale1);
								model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale1);
								model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale1);
								model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment1);
								model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
								model.addAttribute("skilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalUnSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							}
						}
						skilledUnSkilledEmployemntList.clear();
						getSkilledEmploymentList(skilledUnSkilledEmployemnt);
					}

					model.addAttribute("skilledUnSkilledEmployemntList", list);
					model.addAttribute("message", "Total Number of Employment should not be greater than 1000");
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					return new ModelAndView("proposed_employment_details");
				}
			} else if ((ProjectDetail.getResionName().equalsIgnoreCase("Madhyanchal")
					&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGAPLUS))
					|| (ProjectDetail.getResionName().equalsIgnoreCase("Paschimanchal")
							&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGAPLUS))) {
				long skilledEmploymentMale1 = 0;
				long skilledEmploymentFemale1 = 0;
				long totalSkilledEmployment1 = 0;
				long unSkilledEmploymentMale1 = 0;
				long unSkilledEmploymentFemale1 = 0;
				long totalUnSkilledEmployment1 = 0;
				List<SkilledUnSkilledEmployemnt> list = new ArrayList<SkilledUnSkilledEmployemnt>();
				if (((totalSkilledEmployment) + (totalUnSkilledEmployment)) > 1500) {
					if (skilledUnSkilledEmployemntList.size() == 1) {
						model.addAttribute("unSkilledDisplay", "");
						model.addAttribute("skilledDisplay", "");
						skilledUnSkilledEmployemntList.clear();
						model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					} else {

						for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
							if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
								if (count.getNumberofMaleEmployees() != null) {
									skilledEmploymentMale1 = skilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									skilledEmploymentFemale1 = skilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalSkilledEmployment1 = (skilledEmploymentMale1 + skilledEmploymentFemale1);
								model.addAttribute("skilledEmploymentMale", skilledEmploymentMale1);
								model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale1);
								model.addAttribute("totalSkilledEmployment", totalSkilledEmployment1);
								model.addAttribute("skilledDisplay", "skilledDisplay");
								model.addAttribute("unSkilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							} else {
								if (count.getNumberofMaleEmployees() != null) {
									unSkilledEmploymentMale1 = unSkilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									unSkilledEmploymentFemale1 = unSkilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalUnSkilledEmployment1 = (unSkilledEmploymentMale1 + unSkilledEmploymentFemale1);
								model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale1);
								model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale1);
								model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment1);
								model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
								model.addAttribute("skilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalUnSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							}
						}
						skilledUnSkilledEmployemntList.clear();
						getSkilledEmploymentList(skilledUnSkilledEmployemnt);
					}

					model.addAttribute("skilledUnSkilledEmployemntList", list);
					model.addAttribute("message", "Total Number of Employment should not be greater than 1500");
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					return new ModelAndView("proposed_employment_details");
				}
			} else if ((ProjectDetail.getDistrictName().equalsIgnoreCase("Gautambudh nagar")
					&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGAPLUS))
					|| (ProjectDetail.getDistrictName().equalsIgnoreCase("Ghaziabad")
							&& invdtlFromDb.getInvIndType().equalsIgnoreCase(MEGAPLUS))) {
				long skilledEmploymentMale1 = 0;
				long skilledEmploymentFemale1 = 0;
				long totalSkilledEmployment1 = 0;
				long unSkilledEmploymentMale1 = 0;
				long unSkilledEmploymentFemale1 = 0;
				long totalUnSkilledEmployment1 = 0;
				List<SkilledUnSkilledEmployemnt> list = new ArrayList<SkilledUnSkilledEmployemnt>();
				if (((totalSkilledEmployment) + (totalUnSkilledEmployment)) > 2000) {
					if (skilledUnSkilledEmployemntList.size() == 1) {
						model.addAttribute("unSkilledDisplay", "");
						model.addAttribute("skilledDisplay", "");
						model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					} else {

						for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
							if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
								if (count.getNumberofMaleEmployees() != null) {
									skilledEmploymentMale1 = skilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									skilledEmploymentFemale1 = skilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalSkilledEmployment1 = (skilledEmploymentMale1 + skilledEmploymentFemale1);
								model.addAttribute("skilledEmploymentMale", skilledEmploymentMale1);
								model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale1);
								model.addAttribute("totalSkilledEmployment", totalSkilledEmployment1);
								model.addAttribute("skilledDisplay", "skilledDisplay");
								model.addAttribute("unSkilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							} else {
								if (count.getNumberofMaleEmployees() != null) {
									unSkilledEmploymentMale1 = unSkilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									unSkilledEmploymentFemale1 = unSkilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalUnSkilledEmployment1 = (unSkilledEmploymentMale1 + unSkilledEmploymentFemale1);
								model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale1);
								model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale1);
								model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment1);
								model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
								model.addAttribute("skilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalUnSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							}
						}
						skilledUnSkilledEmployemntList.clear();
						getSkilledEmploymentList(skilledUnSkilledEmployemnt);
					}

					model.addAttribute("skilledUnSkilledEmployemntList", list);
					model.addAttribute("message", "Total Number of Employment should not be greater than 2000");
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					return new ModelAndView("proposed_employment_details");
				}
			}

			// super mega

			if ((ProjectDetail.getResionName().equalsIgnoreCase("Poorvanchal")
					&& invdtlFromDb.getInvIndType().equalsIgnoreCase(SUPERMEGA))
					|| (ProjectDetail.getResionName().equalsIgnoreCase("Bundelkhand")
							&& invdtlFromDb.getInvIndType().equalsIgnoreCase(SUPERMEGA))) {
				long skilledEmploymentMale1 = 0;
				long skilledEmploymentFemale1 = 0;
				long totalSkilledEmployment1 = 0;
				long unSkilledEmploymentMale1 = 0;
				long unSkilledEmploymentFemale1 = 0;
				long totalUnSkilledEmployment1 = 0;
				List<SkilledUnSkilledEmployemnt> list = new ArrayList<SkilledUnSkilledEmployemnt>();
				if (((totalSkilledEmployment) + (totalUnSkilledEmployment)) > 2000) {
					if (skilledUnSkilledEmployemntList.size() == 1) {
						model.addAttribute("unSkilledDisplay", "");
						model.addAttribute("skilledDisplay", "");
						model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					} else {

						for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
							if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
								if (count.getNumberofMaleEmployees() != null) {
									skilledEmploymentMale1 = skilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									skilledEmploymentFemale1 = skilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalSkilledEmployment1 = (skilledEmploymentMale1 + skilledEmploymentFemale1);
								model.addAttribute("skilledEmploymentMale", skilledEmploymentMale1);
								model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale1);
								model.addAttribute("totalSkilledEmployment", totalSkilledEmployment1);
								model.addAttribute("skilledDisplay", "skilledDisplay");
								model.addAttribute("unSkilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							} else {
								if (count.getNumberofMaleEmployees() != null) {
									unSkilledEmploymentMale1 = unSkilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									unSkilledEmploymentFemale1 = unSkilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalUnSkilledEmployment1 = (unSkilledEmploymentMale1 + unSkilledEmploymentFemale1);
								model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale1);
								model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale1);
								model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment1);
								model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
								model.addAttribute("skilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalUnSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							}
						}
						skilledUnSkilledEmployemntList.clear();
						getSkilledEmploymentList(skilledUnSkilledEmployemnt);
					}

					model.addAttribute("skilledUnSkilledEmployemntList", list);
					model.addAttribute("message", "Total Number of Employment should not be greater than 2000");
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					return new ModelAndView("proposed_employment_details");
				}
			} else if ((ProjectDetail.getResionName().equalsIgnoreCase("Madhyanchal")
					&& invdtlFromDb.getInvIndType().equalsIgnoreCase(SUPERMEGA))
					|| (ProjectDetail.getResionName().equalsIgnoreCase("Paschimanchal")
							&& invdtlFromDb.getInvIndType().equalsIgnoreCase(SUPERMEGA))) {
				long skilledEmploymentMale1 = 0;
				long skilledEmploymentFemale1 = 0;
				long totalSkilledEmployment1 = 0;
				long unSkilledEmploymentMale1 = 0;
				long unSkilledEmploymentFemale1 = 0;
				long totalUnSkilledEmployment1 = 0;
				List<SkilledUnSkilledEmployemnt> list = new ArrayList<SkilledUnSkilledEmployemnt>();
				if (((totalSkilledEmployment) + (totalUnSkilledEmployment)) > 3000) {
					if (skilledUnSkilledEmployemntList.size() == 1) {
						model.addAttribute("unSkilledDisplay", "");
						model.addAttribute("skilledDisplay", "");
						skilledUnSkilledEmployemntList.clear();
						model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					} else {

						for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
							if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
								if (count.getNumberofMaleEmployees() != null) {
									skilledEmploymentMale1 = skilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									skilledEmploymentFemale1 = skilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalSkilledEmployment1 = (skilledEmploymentMale1 + skilledEmploymentFemale1);
								model.addAttribute("skilledEmploymentMale", skilledEmploymentMale1);
								model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale1);
								model.addAttribute("totalSkilledEmployment", totalSkilledEmployment1);
								model.addAttribute("skilledDisplay", "skilledDisplay");
								model.addAttribute("unSkilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							} else {
								if (count.getNumberofMaleEmployees() != null) {
									unSkilledEmploymentMale1 = unSkilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									unSkilledEmploymentFemale1 = unSkilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalUnSkilledEmployment1 = (unSkilledEmploymentMale1 + unSkilledEmploymentFemale1);
								model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale1);
								model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale1);
								model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment1);
								model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
								model.addAttribute("skilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalUnSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							}
						}
						skilledUnSkilledEmployemntList.clear();
						getSkilledEmploymentList(skilledUnSkilledEmployemnt);
					}

					model.addAttribute("skilledUnSkilledEmployemntList", list);
					model.addAttribute("message", "Total Number of Employment should not be greater than 3000");
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					return new ModelAndView("proposed_employment_details");
				}
			} else if ((ProjectDetail.getDistrictName().equalsIgnoreCase("Gautambudh nagar")
					&& invdtlFromDb.getInvIndType().equalsIgnoreCase(SUPERMEGA))
					|| (ProjectDetail.getDistrictName().equalsIgnoreCase("Ghaziabad")
							&& invdtlFromDb.getInvIndType().equalsIgnoreCase(SUPERMEGA))) {
				long skilledEmploymentMale1 = 0;
				long skilledEmploymentFemale1 = 0;
				long totalSkilledEmployment1 = 0;
				long unSkilledEmploymentMale1 = 0;
				long unSkilledEmploymentFemale1 = 0;
				long totalUnSkilledEmployment1 = 0;
				List<SkilledUnSkilledEmployemnt> list = new ArrayList<SkilledUnSkilledEmployemnt>();
				if (((totalSkilledEmployment) + (totalUnSkilledEmployment)) > 4000) {
					if (skilledUnSkilledEmployemntList.size() == 1) {
						model.addAttribute("unSkilledDisplay", "");
						model.addAttribute("skilledDisplay", "");
						model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
					} else {

						for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
							if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
								if (count.getNumberofMaleEmployees() != null) {
									skilledEmploymentMale1 = skilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									skilledEmploymentFemale1 = skilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalSkilledEmployment1 = (skilledEmploymentMale1 + skilledEmploymentFemale1);
								model.addAttribute("skilledEmploymentMale", skilledEmploymentMale1);
								model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale1);
								model.addAttribute("totalSkilledEmployment", totalSkilledEmployment1);
								model.addAttribute("skilledDisplay", "skilledDisplay");
								model.addAttribute("unSkilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							} else {
								if (count.getNumberofMaleEmployees() != null) {
									unSkilledEmploymentMale1 = unSkilledEmploymentMale1
											+ (count.getNumberofMaleEmployees());
								}
								if (count.getNumberOfFemaleEmployees() != null) {
									unSkilledEmploymentFemale1 = unSkilledEmploymentFemale1
											+ (count.getNumberOfFemaleEmployees());
								}
								totalUnSkilledEmployment1 = (unSkilledEmploymentMale1 + unSkilledEmploymentFemale1);
								model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale1);
								model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale1);
								model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment1);
								model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
								model.addAttribute("skilledDisplay", "");
								model.addAttribute("grossTotalMaleandFemaleEmployment", totalUnSkilledEmployment1);
								model.addAttribute("grossTotalMaleEmployment",
										(skilledEmploymentMale1 + unSkilledEmploymentMale1));
								model.addAttribute("grossTotalFemaleEmployment",
										(skilledEmploymentFemale1 + unSkilledEmploymentFemale1));
								list.add(count);
								skilledUnSkilledEmployemnt.setSkilledUnskilled(count.getSkilledUnskilled());
								skilledUnSkilledEmployemnt.setNumberofMaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfFemaleEmployees(count.getNumberofMaleEmployees());
								skilledUnSkilledEmployemnt.setNumberOfGeneral(count.getNumberOfGeneral());
								skilledUnSkilledEmployemnt.setNumberOfSc(count.getNumberOfSc());
								skilledUnSkilledEmployemnt.setNumberOfSt(count.getNumberOfSt());
								skilledUnSkilledEmployemnt.setNumberOfObc(count.getNumberOfObc());
								skilledUnSkilledEmployemnt.setNumberOfBpl(count.getNumberOfBpl());
								skilledUnSkilledEmployemnt.setNumberOfDivyang(count.getNumberOfDivyang());
								break;
							}
						}
						skilledUnSkilledEmployemntList.clear();
						getSkilledEmploymentList(skilledUnSkilledEmployemnt);
					}

					model.addAttribute("skilledUnSkilledEmployemntList", list);
					model.addAttribute("message", "Total Number of Employment should not be greater than 4000");
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					return new ModelAndView("proposed_employment_details");
				}
			}

		}

		totalSkilledAndUnSkilledEmploment(model);
		model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
		model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
		model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
		model.addAttribute("skilledunskilled", "skilledunskilled");
		return new ModelAndView("proposed_employment_details");

	}

	/**
	 * add list for skilled and unskilled
	 * 
	 * @author ratho
	 *
	 */
	private List<SkilledUnSkilledEmployemnt> getSkilledEmploymentList(
			SkilledUnSkilledEmployemnt skilledUnSkilledEmployemnt) {

		if (skilledUnSkilledEmployemnt != null) {
			if (skilledUnSkilledEmployemntList != null && skilledUnSkilledEmployemntList.size() > 0) {
				skilledUnSkilledEmployemntList.add(skilledUnSkilledEmployemnt);
			} else {
				skilledUnSkilledEmployemntList.add(skilledUnSkilledEmployemnt);
			}
		}
		return skilledUnSkilledEmployemntList;
	}

	/**
	 * total male, female,obc, general, sc,st and bpl
	 * 
	 * @author ratho
	 *
	 */
	private void totalSkilledAndUnSkilledEmploment(Model model) {
		long skilledEmploymentMale = 0;
		long skilledEmploymentFemale = 0;
		long totalSkilledEmployment = 0;
		long unSkilledEmploymentMale = 0;
		long unSkilledEmploymentFemale = 0;
		long totalUnSkilledEmployment = 0;

		if (skilledUnSkilledEmployemntList.size() > 0) {

			for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntList) {
				if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
					if (count.getNumberofMaleEmployees() != null) {
						skilledEmploymentMale = skilledEmploymentMale + (count.getNumberofMaleEmployees());
					}
					if (count.getNumberOfFemaleEmployees() != null) {
						skilledEmploymentFemale = skilledEmploymentFemale + (count.getNumberOfFemaleEmployees());
					}
					totalSkilledEmployment = (skilledEmploymentMale + skilledEmploymentFemale);
					model.addAttribute("skilledEmploymentMale", skilledEmploymentMale);
					model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale);
					model.addAttribute("totalSkilledEmployment", totalSkilledEmployment);
					model.addAttribute("skilledDisplay", "skilledDisplay");

				} else {
					if (count.getNumberofMaleEmployees() != null) {
						unSkilledEmploymentMale = unSkilledEmploymentMale + (count.getNumberofMaleEmployees());
					}
					if (count.getNumberOfFemaleEmployees() != null) {
						unSkilledEmploymentFemale = unSkilledEmploymentFemale + (count.getNumberOfFemaleEmployees());
					}
					totalUnSkilledEmployment = (unSkilledEmploymentMale + unSkilledEmploymentFemale);
					model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale);
					model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale);
					model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment);
					model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
				}
			}

		}
		model.addAttribute("skilledUnSkilledEmployemntList", skilledUnSkilledEmployemntList);
		model.addAttribute("grossTotalMaleEmployment", (skilledEmploymentMale + unSkilledEmploymentMale));
		model.addAttribute("grossTotalFemaleEmployment", (skilledEmploymentFemale + unSkilledEmploymentFemale));
		model.addAttribute("grossTotalMaleandFemaleEmployment", ((skilledEmploymentFemale + unSkilledEmploymentFemale)
				+ (skilledEmploymentMale + unSkilledEmploymentMale)));
	}

	/**
	 * save proposed Employement details Records
	 * 
	 * @author ratho
	 *
	 */
	@RequestMapping(value = "/deleteProposedEmploymentDetails", method = RequestMethod.GET)
	public ModelAndView deleteProposedEmploymentDetails( SkilledUnSkilledEmployemnt skilledUnSkilledEmployemnt,
			@RequestParam(value = "deleteunskilledRecord", required = false) int index, Model model) {

		skilledUnSkilledEmployemnt=skilledUnSkilledEmployemntList.remove(index);
		try {
			skillUnSkilledRepository.deleteById(skilledUnSkilledEmployemnt.getId());
		} catch (Exception e) {

		}
		
		totalSkilledAndUnSkilledEmploment(model);
		isRowIndex = false;	
		model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
		model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
		model.addAttribute("skilledunskilled", "skilledunskilled");
		return new ModelAndView("proposed_employment_details");
	}

	@RequestMapping(value = "/editProposedEmploymentDetails", method = RequestMethod.GET)
	public ModelAndView editProposedEmploymentDetails(
			@RequestParam(value = "editunskilledRecord", required = false) int index, Model model) {

		SkilledUnSkilledEmployemnt skilledUnSkilledEmployemnt = new SkilledUnSkilledEmployemnt();

		skilledUnSkilledEmployemnt = skilledUnSkilledEmployemntList.get(index);
		model.addAttribute("skilledUnSkilledEmployemnt", skilledUnSkilledEmployemnt);
		rowIndex = index;
		isRowIndex = true;
		totalSkilledAndUnSkilledEmploment(model);
		model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
		model.addAttribute("skilledunskilled", "skilledunskilled");
		model.addAttribute("edit", "edit");
		return new ModelAndView("proposed_employment_details");
	}

	public void updateskillUnskilled(int index, SkilledUnSkilledEmployemnt skilledUnSkilledEmployemnt) {
		skilledUnSkilledEmployemntList.add(index, skilledUnSkilledEmployemnt);

	}

	@RequestMapping(value = "/proposedEmploymentDtails", method = RequestMethod.POST)
	public ModelAndView saveProposedEmploymentDtails(
			@ModelAttribute("proposedEmploymentDetails") @Validated ProposedEmploymentDetails proposedEmploymentDetails,
			SkilledUnSkilledEmployemnt skilledUnSkilledEmployemnt, Model model, HttpSession session) {

		
		String propId = "";
		String appliId = "";
		String skilunskil = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : responce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				propId = entry.getValue() + "PE1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appliId = entry.getValue() + "A1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				skilunskil = entry.getValue() + "SUS";
			}
		}
		proposedEmploymentDetails.setId(propId);
		proposedEmploymentDetails.setAppId(appliId);
		proposedEmploymentDetails.setCreatedBy("AdminUser");
		proposedEmploymentDetails.setModifiedtedBy("AdminUser");
		proposedEmploymentDetails.setStatus("Active");
		totalSkilledAndUnSkilledEmploment(model);
		SkilledUnSkilledEmployemnt skilledUnSkilledEmployemnts = null;
		List<SkilledUnSkilledEmployemnt> SkilledUnSkilledEmployemntLis = new ArrayList<SkilledUnSkilledEmployemnt>();
		int length = skilledUnSkilledEmployemntList.size();
		for (int i = 0; i < length; i++) {
			skilledUnSkilledEmployemnts = new SkilledUnSkilledEmployemnt();
			skilledUnSkilledEmployemnts = skilledUnSkilledEmployemntList.get(i);
			skilledUnSkilledEmployemnts.setId(skilunskil + i);
			skilledUnSkilledEmployemnts.setCreatedBy("AdminUser");
			skilledUnSkilledEmployemnts.setModifiedBy("AdminUser");
			skilledUnSkilledEmployemnts.setStatus("Active");
			skilledUnSkilledEmployemnts.setProposedEmploymentDetails(proposedEmploymentDetails);
		}
		SkilledUnSkilledEmployemntLis.add(skilledUnSkilledEmployemnts);

		proposedEmploymentDetails.setSkilledUnSkilledEmployemnt(skilledUnSkilledEmployemntList);

		try {
			proposedEmploymentDetailsService.saveProposedEmploymentDetails(proposedEmploymentDetails);
		}

		catch (Exception e) {

			proposedEmploymentDetailsService.updateProposedEmploymentDetails(proposedEmploymentDetails);
		}

		model.addAttribute("proposedEmploymentDetails", proposedEmploymentDetails);
		model.addAttribute("incentiveTypeForm", new IncentiveDetails());

		return new ModelAndView("redirect:/incentiveDetails");

	}
	
/*-----------------------------tabs-------------------------------------*/
	
	@RequestMapping(value = "/getIdByTabs44", method = RequestMethod.GET)
	public ModelAndView getIdByTabs44(Model model, HttpSession session,@RequestParam(value = "authoTab")String  authoTab) {
		
		String appId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		
		if(applicantDetail!=null && authoTab.equalsIgnoreCase("authoTab"))
		{			
			return new ModelAndView("redirect:/editApplicantForm");
		}
		return null;
		
		
	}
	
	@RequestMapping(value = "/getIdByTabs45", method = RequestMethod.GET)
	public ModelAndView getIdByTabs45(Model model, HttpSession session,@RequestParam(value = "busiTab")String  busiTab) {
		
		String appId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(appId);
		if(businessEntityDetails!=null && busiTab.equalsIgnoreCase("busiTab"))
		{
			session.setAttribute("applicantDetails", applicantDetail);
			session.setAttribute("applicantDetailsId", appId);
			return new ModelAndView("redirect:/businessDetails");
		}
		return null;
		
		
	}
	
	@RequestMapping(value = "/getIdByTabs46", method = RequestMethod.GET)
	public ModelAndView getIdByTabs46(Model model, HttpSession session,@RequestParam(value = "projTab")String  projTab) {
		
		String projId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				projId = entry.getValue() + "P1";
			}
		}
		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
		if(ProjectDetail!=null && projTab.equalsIgnoreCase("projTab"))
		{
			return new ModelAndView("redirect:/projectDetails");
		}
		return null;
		
		
	}
	
	@RequestMapping(value = "/getIdByTabs47", method = RequestMethod.GET)
	public ModelAndView getIdByTabs47(Model model, HttpSession session,@RequestParam(value = "investTab")String  investTab) {
		
		String investId = "";
		
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				investId = entry.getValue() + "I1";
			}
			
		}
		
		InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
		if(invdtlFromDb!=null && investTab.equalsIgnoreCase("investTab"))
		{
			return new ModelAndView("redirect:/investmentDetails");
		}
		return null;
		
	}
	
	
	/*-----------------------------tabs-------------------------------------*/
	
}
