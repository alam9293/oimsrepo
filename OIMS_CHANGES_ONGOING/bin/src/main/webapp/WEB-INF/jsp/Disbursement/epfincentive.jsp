<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Employees Provident Fund Reimbursement</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->

<%@ include file="../head.jsp"%>

<!-- Please write your custom JS here -->
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						console.log("document loaded");
						if ("${epfincentiveDeatilsForm.affidavitDocBase64File}" == null
								|| "${epfincentiveDeatilsForm.affidavitDocBase64File}" == '') {

						} else {
							document.getElementById('choosefileAffidavitDoc').innerHTML = '${epfincentiveDeatilsForm.affidavitDoc}';
							document.getElementById("affidavitDoc").src = "data:image/png;base64,${epfincentiveDeatilsForm.affidavitDocBase64File}";
						}

						if ("${epfincentiveDeatilsForm.copyFormDocBase64File}" == null
								|| "${epfincentiveDeatilsForm.copyFormDocBase64File}" == '') {

						} else {
							document.getElementById('choosefileCopyFormDoc').innerHTML = '${epfincentiveDeatilsForm.copyFormDoc}';
							document.getElementById("copyFormDoc").src = "data:image/png;base64,${epfincentiveDeatilsForm.copyFormDocBase64File}";
						}

						if ("${epfincentiveDeatilsForm.monthwiseDocBase64File}" == null
								|| "${epfincentiveDeatilsForm.monthwiseDocBase64File}" == '') {
							document.getElementById('verified').checked = false;
						} else {
							document.getElementById('choosefileMonthwiseDoc').innerHTML = '${epfincentiveDeatilsForm.monthwiseDoc}';
							document.getElementById("monthwiseDoc").src = "data:image/png;base64,${epfincentiveDeatilsForm.monthwiseDocBase64File}";
							document.getElementById('verified').checked = true;
						}
					});
</script>
<script type="text/javascript">


$(document).ready(function(){

		$('#verified:checked').prop('checked',false);

		});

</script>


<script>
	$(document).ready(function() {
		$("[data-toggle=offcanvas]").click(function() {
			$(".row-offcanvas").toggleClass("active");
		});
	});
</script>

<script type="text/javascript">
	window.onload = function() {

		if ('${ISF_Add_Epf_Reim_SkUkW}' != null
				&& '${ISF_Add_Epf_Reim_SkUkW}' != '') {
			$(
					'input:checkbox[name="Add_EFP_Reim_SkUkW_checkbox"][value="ISF_Add_Epf_Reim_SkUkW"]')
					.prop('checked', true);

		}
		if ('${ISF_Add_Epf_Reim_DIVSCSTF}' != null
				&& '${ISF_Add_Epf_Reim_DIVSCSTF}' != '') {
			$(
					'input:checkbox[name="Add_EFP_Reim_DIVSCSTF_checkbox"][value="ISF_Add_Epf_Reim_DIVSCSTF"]')
					.prop('checked', true);

		}
	}
	function disbCapInvLCTotal() {
		var sum = 0;
		$(".is-numeric").each(function() {
			//alert("hi");
			sum += +$(this).val();
		});
		$(".totalEPF", this).val(sum);
		//alert(sum);
	}

	$(document).ready(disbCapInvLCTotal);
	$(document).on("keyup", disbCapInvLCTotal);
	$(document).on("change", disbCapInvLCTotal);
</script>
<script type="text/javascript">
	function epfValidation() {


		if(!AffidavitDocument(document.getElementById('affidavitDoc'))){
			return false;
		}

		if(!CopyOfFormNo(document.getElementById('copyFormDoc'))){
			return false;
		}

		if(!MonthWiseDetails(document.getElementById('monthwiseDoc'))){
			return false;
		}
				
		$(".empty-input").each(function() {
			if ($(this).val() == "") {
				$(this).val("");
				$(this).next().html("Field Required");
			}
		});

		if (!$('#verified').is(':checked')) {
			alert(' Please select/check the Declaration');
			$("#verified").focus();
			return false;
		}

		var r = confirm("Do you want to Save the Form?");

		if (r == true) {
			alert("Application Form is Submitted Succesfully");
		} else {
			return false
		}

	}
</script>
<script type="text/javascript">


function AffidavitDocument(file) {
	
	 var affidavitFile = document.getElementById('affidavitDoc').value;
	 var fileSize = document.getElementById("affidavitDoc").files[0];
	 var maxSize = '5000';

	  if('${epfincentiveDeatilsForm.affidavitDocBase64File}' != '' && affidavitFile == ''){
			return true;
		} 
		
	 if(affidavitFile != null && affidavitFile != ''){
	 var ext = affidavitFile.split('.').pop();
	 if (ext == "pdf") {
		
	} else {
		document.getElementById('affidavitDocMsg').innerHTML = "Please Upload Support Documnet in PDF Format";
		document.getElementById('affidavitDoc').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
  if (FileSize >5) {
  	document.getElementById('affidavitDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
  	document.getElementById('affidavitDoc').focus();
		return false;
  }
  
	} else{
		document.getElementById('affidavitDocMsg').innerHTML = "Affidavit Promotor Document is Required.";
		document.getElementById('affidavitDoc').focus();
		return false;
	  }
		    document.getElementById('affidavitDocMsg').innerHTML = "";
		    return true;
}


function CopyOfFormNo(file) {
	
	 var copyFormDocDocument = document.getElementById('copyFormDoc').value;
	 var fileSize = document.getElementById("copyFormDoc").files[0];
	 var maxSize = '5000';

	  if('${epfincentiveDeatilsForm.copyFormDocBase64File}' != '' && copyFormDocDocument == ''){
			return true;
		} 
		
	 if(copyFormDocDocument != null && copyFormDocDocument != ''){
	 var ext = copyFormDocDocument.split('.').pop();
	 if (ext == "pdf") {
		
	} else {
		document.getElementById('copyFormDocMsg').innerHTML = "Please Upload Support Documnet in PDF Format";
		document.getElementById('copyFormDoc').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
 if (FileSize >5) {
 	document.getElementById('copyFormDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
 	document.getElementById('copyFormDoc').focus();
		return false;
 }
 
	} else{
		document.getElementById('copyFormDocMsg').innerHTML = "Form No. 12 Document is Required.";
		document.getElementById('copyFormDoc').focus();
		return false;
	  }
		    document.getElementById('copyFormDocMsg').innerHTML = "";
		    return true;
}


function MonthWiseDetails(file) {
	
	 var monthwiseDocDocument = document.getElementById('monthwiseDoc').value;
	 var fileSize = document.getElementById("monthwiseDoc").files[0];
	 var maxSize = '5000';

	 if('${epfincentiveDeatilsForm.monthwiseDocBase64File}' != '' && monthwiseDocDocument == ''){
			return true;
		} 
		
	 if(monthwiseDocDocument != null && monthwiseDocDocument != ''){
	 var ext = monthwiseDocDocument.split('.').pop();
	 if (ext == "pdf") {
		
	} else {
		document.getElementById('monthwiseDocMsg').innerHTML = "Please Upload Support Documnet in PDF Format";
		document.getElementById('monthwiseDoc').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
if (FileSize >5) {
	document.getElementById('monthwiseDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	document.getElementById('monthwiseDoc').focus();
		return false;
}

	} else{
		document.getElementById('monthwiseDocMsg').innerHTML = " EPFO  Document is Required.";
		document.getElementById('monthwiseDoc').focus();
		return false;
	  }
		    document.getElementById('monthwiseDocMsg').innerHTML = "";
		    return true;
}


</script>
</head>

<%@ include file="../topMenu.jsp"%>


<section class="common-form-area">
	<div class="container">
		<!-- Main Title -->
		<div class="inner-banner-text">
			<h1>Application Form</h1>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="form-card">
					<div class="without-wizard-steps">
						<h4 class="card-title mb-4 mt-4 text-center">Employees
							Provident Fund Reimbursement</h4>
						<div class="isf-form">
							<%-- 							<form class="mt-4"> --%>
							<%-- 								<form:form modelAttribute="epfincentiveDeatilsForm" --%>
							<%-- 									name="epfincentiveDeatilsForm" method="post" id="myForm" --%>
							<%-- 									action="epfReimSave" enctype="multipart/form-data"> --%>

							<form:form class="mt-4" modelAttribute="epfincentiveDeatilsForm"
								method="post" action="epfReimSave" enctype="multipart/form-data">
								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Number of Skilled/Unskilled
											Workers</h3>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="table-responsive mt-3">
											<table class="table table-stripped table-bordered">
												<thead>
													<tr>
														<th>Skilled/Unskilled</th>
														<th>Male Emp</th>
														<th>Female Emp</th>
														<th>General</th>
														<th>SC</th>
														<th>ST</th>
														<th>OBC</th>
														<th>BPL</th>
														<th>Divyang</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="list" items="${proposedEmploymentDetails}"
														varStatus="counter">
														<tr>
															<td>${list.skilledUnskilled}</td>
															<td>${list.numberofMaleEmployees}</td>
															<td>${list.numberOfFemaleEmployees}</td>
															<td>${list.numberOfGeneral}</td>
															<td>${list.numberOfSc}</td>
															<td>${list.numberOfSt}</td>
															<td>${list.numberOfObc}</td>
															<td>${list.numberOfBpl}</td>
															<td>${list.numberOfDivyang}</td>
														</tr>
													</c:forEach>

												</tbody>
											</table>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Skilled Male Employees</label>
											<form:input type="text" path="skillmale" class="form-control"
												value="${skilledEmploymentMale}" readonly="true"></form:input>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Skilled Female Employees</label>
											<form:input type="text" path="skillfemale"
												class="form-control" id=""
												value="${skilledEmploymentFemale}" readonly="true" name=""></form:input>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Total Skilled Employees</label>
											<form:input type="text" path="skillemp" class="form-control"
												id="" value="${totalSkilledEmployment}" readonly="true"
												name=""></form:input>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Unskilled Male Employees</label>
											<form:input type="text" path="unskillmale"
												class="form-control" id=""
												value="${unSkilledEmploymentMale}" readonly="true" name=""></form:input>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Unskilled Female Employees</label>
											<form:input type="text" path="unskillfemale"
												class="form-control" id=""
												value="${unSkilledEmploymentFemale}" readonly="true" name=""></form:input>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Total Unskilled Employees</label>
											<form:input type="text" path="unskillemp"
												class="form-control" id=""
												value="${totalUnSkilledEmployment}" readonly="true" name=""></form:input>
										</div>
									</div>
								</div>

								<hr class="mt-4">
								<div class="row">
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Total Male Employees</label>
											<form:input type="text" path="totalmale" class="form-control"
												id="" value="${grossTotalMaleEmployment}" readonly="true"
												name=""></form:input>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Total Female Employees</label>
											<form:input type="text" path="totalfemale"
												class="form-control" id=""
												value="${grossTotalFemaleEmployment}" readonly="true"
												name=""></form:input>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group text-right">
											<label class="total-emp">Total Employees =
												${grossTotalMaleandFemaleEmployment}</label>

										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Details of Claims for EPF
											Reimbursement</h3>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="table-responsive">
											<table
													class="table table-stripped table-bordered loc-stage-table">
													<tbody>
														<tr>
															<td style="width: 70%;"><strong>EPF
																	Reimbursement (100 or more unskilled workers)</strong></td>
															<td><form:input type="text"
																class="form-control is-numeric" maxlength="10"
																value="${ISF_Epf_Reim_UW}" path ="epfReim" name="ISF_Epf_Reim_UW" readonly="true"></form:input>
																<span class="text-danger"></span> <small
																class="words text-info"></small></td>
														</tr>
														<tr>
															<td>
																<div class="custom-control custom-checkbox mb-4">
																	<input type="checkbox" class="custom-control-input"
																		id="SGST5" name="Add_EFP_Reim_SkUkW_checkbox"
																		value="ISF_Add_Epf_Reim_SkUkW" disabled="disabled"> <label
																		class="custom-control-label" for="SGST5">Additional
																		10% EPF Reimbursement (200 direct skilled and
																		unskilled workers)</label>
																</div>
															</td>
															<td><form:input type="text"
																class="form-control is-numeric" maxlength="10"
																value="${ISF_Add_Epf_Reim_SkUkW}"
																name="ISF_Add_Epf_Reim_SkUkW" path="epfReimSkUnWorker" readonly="true"></form:input> <span
																class="text-danger"></span> <small
																class="words text-info"></small></td>
														</tr>
														<tr>
															<td>
																<div class="custom-control custom-checkbox mb-4">
																	<input type="checkbox" class="custom-control-input"
																		id="SGST6" name="Add_EFP_Reim_DIVSCSTF_checkbox"
																		value="ISF_Add_Epf_Reim_DIVSCSTF" disabled="disabled" > <label
																		class="custom-control-label" for="SGST6">Additional
																		10% EPF Reimbursement upto maximum of 70% in case of
																		industrial undertakings having 75% equity owned by
																		Divyang/SC/ST/Females Promoters</label>
																</div>
															</td>
															<td><form:input type="text"  maxlength="10"
																class="form-control is-numeric" path ="epfEquity"
																value="${ISF_Add_Epf_Reim_DIVSCSTF}"
																name="ISF_Add_Epf_Reim_DIVSCSTF" readonly="true"></form:input> <span
																class="text-danger"></span> <small
																class="words text-info"></small></td>
														</tr>
														<tr>
															<td><strong>Total EPF Reimbursement</strong></td>
															<td><form:input type="text" class="form-control totalEPF" path ="EpfTotal"
																value="${epf}" readonly="true" name="ISF_Ttl_EPF_Reim" ></form:input></td>
														</tr>
													</tbody>
												</table>
										</div>
									</div>
								</div>
								
								       <div class="row">
                                <div class="col-sm-12 mt-4">
                                  <h3 class="common-heading">EPF Claim Details </h3>
                                </div>
                              </div>
        
                              <div class="row">
                                <div class="col-md-12">
                                  <div class="table-responsive mt-3" id="productsDetailsTbl-1">
                                    <table class="table table-stripped table-bordered">
                                      <thead>
                                        <tr>
                                          <th>Financial Year	</th>
                                         
                                          <th class="text-center px-0 pb-0 mb-3">Duration / Period
                                              
                                            <table class="border-top w-100 mt-3" >
                                              <tr>
                                                <th class="border-right border-top-0 border-bottom-0" style="width: 50% !important;" >From</th>
                                                <th  class="border-0">To</th>
                                              </tr>
                                            </table>
                                          </th>
                                          <th>Employer's Contribution of EPF		</th>
                                        
                                        
                                          <th style="width: 8%;">Action</th>
                                        </tr>
                                      </thead>
                                     
                                      <tfoot class="existing-proposed-products-1">
                                        <tr>
                                          <td>
                                           
                                            <form:input type="text" path="epfClaimsfinancialYear"
												class="form-control" id="epfClaimsfinancialYearId"
												value="" readonly="false" name=""></form:input>
                                          </td>
                                        
                                          <td class="text-left">
                                              <table class="border-0" style="width: 100%;font-size: 13px;" >
                                                <tr>
                                                  <td  style="border: none;padding: 0;width: 50% !important;"> <form:input type="text" path="epfClaimsfrom"
												   class="form-control" id="epfClaimsfromId" value="" style="width: 100% !important;" readonly="false" name=""></form:input></td>
												
                                                  <td style="border: none;padding: 0;width: 50% !important;"> <form:input type="text" path="epfClaimsto"
												   class="form-control" id="epfClaimstoId" value="" style="width: 100% !important;" readonly="false" name=""></form:input></td>
                                                </tr>
                                              </table>
                                            </td>
                                       
                                         
                                          <td>
                                            <form:input type="text" path="employersContributions"
												   class="form-control" id="employersContributionsId" value="" readonly="false" name=""></form:input>
                                          </td>
                                          <td class="text-center">
                                            <a href="javascript:void();" class="btn btn-outline-success btn-sm add-save-data-1">Add</a>
                                          </td>
                                        </tr>
                                      </tfoot>
                                    </table>
                                  </div>
                                </div>
                              </div>
        


								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Required Supporting Documents</h3>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<label>Affidavit from the main promoter/ Authorized <a
												href="javascript:void(0);" class="remove-row"
												data-toggle="tooltip" title=""
												data-original-title="Affidavit from the main promoter/ Authorized Officer to the effect that all the above particulars are true and that the unit had 100 unskilled workers in its continuous employment for the full period of the relevant year for which application for reimbursement is being filed."><i
													class="fa fa-info-circle text-info"></i></a> <small>(Upload
													doc less than 5 MB in PDF)</small>
											</label>
											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" id="affidavitDoc" name="affidavitDoc" onchange="return AffidavitDocument(event)">
											
												<label class="custom-file-label file"
													id="choosefileAffidavitDoc" for="affidavitDoc">Choose
													file</label>
											</div>
											<span id="affidavitDocMsg" class="text-danger"></span>
										</div>
									
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Copy of Form No. 12 required to be filed under
												the Factories Act, 1948. <span>*</span> <small>(Upload
													doc less than 5 MB in PDF)</small>
											</label>
											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" id="copyFormDoc" name="copyFormDoc" onchange="return CopyOfFormNo(event)">
												<span class="text-danger error_msg"></span>
												<label class="custom-file-label file"
													id="choosefileCopyFormDoc" for="copyFormDoc">Choose
													file</label>
											</div>
											<span id="copyFormDocMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Month wise details of contributions paid by
												Employer into the EPFO or Employer's<span>*</span> <a
												href="javascript:void(0);" class="remove-row"
												data-toggle="tooltip" title=""
												data-original-title="Month wise details of contributions paid by Employer into the EPFO or Employer’s PF Trust, which should be certified by the concerned competent officer of EPFO/Competent officer of Trust."><i
													class="fa fa-info-circle text-info"></i></a> <small>(Upload
													doc less than 5 MB in PDF)</small>
											</label>
											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" id="monthwiseDoc" name="monthwiseDoc" onchange="return MonthWiseDetails(event)">
												<span class="text-danger error_msg"></span>
												<label class="custom-file-label file"
													id="choosefileMonthwiseDoc" for="monthwiseDoc">Choose
													file</label>
											</div>
											<span id="monthwiseDocMsg" class="text-danger"></span>
										</div>
									</div>
								</div>

								<hr>

								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Declaration</h3>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group mt-3">
											<div
												class="custom-control custom-checkbox custom-control-inline">
												<input type="checkbox" class="custom-control-input"
													id="verified" name="Capital-Interest-Subsidy"> <label
													class="custom-control-label" for="verified">The
													above information are completely true and no fact has been
													concealed or misrepresented. It is further certified that
													the company has not applied for benefits of the above
													nature under any sector-specific or other policy of the
													Government of Uttar Pradesh for purpose of availing
													benefits of the above nature. I/we hereby agree that I/we
													shall forthwith repay the benefits released to me/us under
													Rules of Policy for Promotion of Industrial Investment and
													Employment-2017, if the said benefits are found to be
													disbursed in excess of the amount actually admissible
													whatsoever the reason. </label>
											</div>
										</div>
									</div>
								</div>



								<hr>

								<div class="row">
									<div class="col-sm-5">
										<a href="./disincentivetype"
											class="common-default-btn mt-3 prev-step" onclick="return confirm('Are you sure you want to go to SELECT INCENTIVE TYPE?')"> Back </a>
									</div>
									<div class="col-sm-7 text-right">

											<button type="submit" class="common-btn"
											onclick="return epfValidation()">Save</button>
									</div>
								</div>
							</form:form>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>


<%@ include file="../footerMenu.jsp"%>


</body>
</html>