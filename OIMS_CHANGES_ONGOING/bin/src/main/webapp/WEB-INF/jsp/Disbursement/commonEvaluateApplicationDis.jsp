<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="com.webapp.ims.model.EvaluateInvestmentDetails"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>

<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>DIS Evaluation View</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/style.css">

<script type="text/javascript">
	function Saveconfirm() {

		if (!DocumentconfProvbyCTDDoc(document
				.getElementById('confProvbyCTDDoc'))) {
			return false;
		}

		if (!$('#verified').is(':checked')) {
			alert('Please check and verify the form.');
			$("#verified").focus();
			return false;
		}

		var r = confirm("Are you sure, You want to save the form data ?");

		if (r == true) {
			alert("Application Data Saved Successfully");
		} else {
			return false
		}

	}
</script>
<script type="text/javascript">
	window.onload = function() {
		if ('${enableIncludeAgenda.status}' == "Evaluation Completed") {
			//$("#button1").removeClass("evaluate-btn");
			$("#button2").removeClass("evaluate-btn");
		} else {
			//$("#button2").addClass("disable-btn");
		}
	}

	/* Pankaj */
	function RejectApplication() {
		var r = confirm("Are you Sure Want to Reject the Application ?");

		if (r == true) {
			alert("Application Rejected Successfully");
		} else {
			return false
		}

	}
	/* Pankaj */
	function RaiseQuery() {
		var r = confirm("Are you Sure Want to Submit the Query ?");

		if (r == true) {
			alert("Query Raised Successfully");
		} else {
			return false
		}

	}
	/* Pankaj */
	function includeAgendaNote() {
		var r = confirm("Are you Sure Want to Include Application in Agenda Note?");

		if (r == true) {
			alert("Application Included In Prepare Agenda Note Successfully");
		} else {
			return false
		}

	}
	//By Pankaj
	function enableButton2() {

		var r = confirm("Are you Sure Want to Complete the Evaluation?");
		var apcId = '${appId}';
		if (r == true) {
			alert("Your Application ID - "
					+ apcId
					+ "Evaluated Successfully, Please Keep your Application ID for Your Reference in the Future.");
			document.getElementById("button2").disabled = false;
		} else {
			document.getElementById("button2").disabled = true;
			return false
		}
	}
</script>
</head>

<body>
	<button onclick="topFunction()" id="GoToTopBtn" title="Go to top">Top</button>
								<form:form modelAttribute="disViewEvaluate" method="post"
							action="SaveEvaluateApplicationDis" class="mt-4"
							autocomplete="off" enctype="multipart/form-data">
								<div class="col-md-12 mt-4">
									<div class="form-group">
										<label>Add Promoters &amp; Proposed project Details</label>
										<form:textarea path="addPromotersDetails" readonly="true"
											placeholder="Add Comment" class="form-control border-info"
											rows="5" cols="148"></form:textarea>
									</div>
								</div>
							 


							<div class="parameter-properties" id="viewAllApplicationDetails">
								<hr class="mt-2">
								<h4 class="h2">The Proposal- Company's Request for
									Disbursement of Incentives</h4>
								<div class="isf-form mt-4">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-bordered">
													<thead>
														<tr>
															<th style="width: 30%;">Eligibility Criteria</th>
															<th style="width: 40%;">Compliance</th>
															<th style="width: 30%;">Observations By PICUP</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td><label class="table-heading">Address of
																	Registered Office of the Industrial Company</label></td>
															<td><form:input path="addOfRegisCompl" type="text"
																	class="form-control" id=""
																	value="${disApplicantDetails.bussAddressDis}"
																	readonly="true" name=""></form:input></td>
															<td><form:textarea path="addOfRegisObserv"
																	id="addOfRegisObserv"
																	placeholder="Observations Comment By PICUP"
																	name="addOfRegisObserv"
																	class="form-control border-info" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>
														<tr>
															<td><label class="table-heading">Address of
																	Factory of the Industrial Unit</label></td>
															<td><form:input path="addOfFactoryCompl" type="text"
																	class="form-control" id="addOfFactoryCompl"
																	value="${disApplicantDetails.projAddressDis}"
																	readonly="true" name="addOfFactoryCompl" /></td>
															<td><form:textarea path="addOfFactoryObserv"
																	id="addOfFactoryObserv"
																	placeholder="Observations Comment By PICUP"
																	name="addOfFactoryObserv"
																	class="form-control border-info" rows="2" readonly="true"></form:textarea>
															</td>

														</tr>

														<tr>
															<td><label class="table-heading">Constitution
																	of the Concern</label></td>
															<td><form:input path="constiOfCompl" type="text"
																	class="form-control" id="constiOfCompl"
																	value="${businessEntityType}" readonly="true"
																	name="constiOfCompl" /></td>
															<td><form:textarea path="constiOfObserv"
																	id="constiOfObserv"
																	placeholder="Observations Comment By PICUP"
																	name="constiOfObserv" class="form-control border-info"
																	rows="2" readonly="true"></form:textarea></td>
														</tr>
														<tr>
															<td><label class="table-heading">Date of
																	Start of Commercial Production</label></td>
															<td><form:input path="dateOfStartCompl" type="date"
																	class="form-control" id="dateOfStartCompl"
																	value="${evaluateInvestMentDetails.propCommProdDate}"
																	readonly="true" name="dateOfStartCompl" /></td>
															<td><form:textarea path="dateOfStartObserv"
																	id="dateOfStartObserv"
																	placeholder="Observations Comment By PICUP"
																	name="dateOfStartObserv"
																	class="form-control border-info" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>
														<tr>
															<td><label class="table-heading">New
																	Unit/Expansion/Diversification Project</label></td>
															<td><form:input path="newUnitExpanCompl" type="text"
																	class="form-control" id="newUnitExpanCompl"
																	value="${natureOfProject}" readonly="true"
																	name="newUnitExpanCompl" /></td>
															<td><form:textarea path="newUnitExpanObserv"
																	id="newUnitExpanObserv"
																	placeholder="Observations Comment By PICUP"
																	name="newUnitExpanObserv"
																	class="form-control border-info" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>
														<c:if test="${natureOfProject=='Expansion'}">
															<tr>
																<td><label class="table-heading">Product-Wise
																		Installed Capacity for Expansion Unit</label></td>
																<td><form:input path="productWiseCompl" type="text"
																		class="form-control" id="productWiseCompl" value="Yes"
																		readonly="true" name="productWiseCompl" /></td>
																<td><form:textarea path="productWiseObserv"
																		id="productWiseObserv"
																		placeholder="Observations Comment By PICUP"
																		name="productWiseObserv"
																		class="form-control border-info" rows="2" readonly="true"></form:textarea>
																</td>
															</tr>
															<tr>
																<td colspan="3">
																	<div class="table-responsive mt-3">
																		<div class="table-responsive mt-3"
																			id="productsDetailsTbl">
																			<table class="table table-stripped table-bordered"
																				id="edittable">
																				<thead>
																					<c:if
																						test="${not empty evaluateExistNewProjDetailsList}">
																						<tr>
																							<th>Existing Products</th>
																							<th>Existing Installed Capacity</th>
																							<th>Proposed Products</th>
																							<th>Proposed Installed Capacity</th>
																							<th>Existing Gross Block</th>
																							<th>Proposed Gross Block</th>
																						</tr>
																					</c:if>
																				</thead>
																				<tbody class="existing-proposed-products">
																					<c:forEach var="list"
																						items="${evaluateExistNewProjDetailsList}"
																						varStatus="counter">
																						<tr>

																							<td><input id="epdExisProductsId"
																								name="epdExisProducts"
																								value="${list.epdExisProducts}"
																								class="form-control" readonly="true"></input></td>
																							<td><input id="epdExisInstallCapacityId"
																								name="epdExisInstallCapacity"
																								value="${list.epdExisInstallCapacity}"
																								class="form-control" readonly="true"></input></td>
																							<td><input id="epdPropProductsId"
																								name="epdPropProducts"
																								value="${list.epdPropProducts}"
																								class="form-control" readonly="true"></input></td>
																							<td><input id="epdPropInstallCapacityId"
																								name="epdPropInstallCapacity"
																								value="${list.epdPropInstallCapacity}"
																								class="form-control" readonly="true"></input></td>
																							<td><input id="epdExisGrossBlockId"
																								name="epdExisGrossBlock"
																								value="${list.epdExisGrossBlock}"
																								class="form-control" readonly="true"></input></td>
																							<td><input id="epdPropoGrossBlockId"
																								name="epdPropoGrossBlock"
																								value="${list.epdPropoGrossBlock}"
																								class="form-control" readonly="true"></input></td>
																						</tr>
																					</c:forEach>
																				</tbody>
																			</table>
																		</div>
																	</div>
																</td>
															</tr>
														</c:if>
														<c:if test="${natureOfProject=='NewProject'}">
															<tr>
																<td><label class="table-heading">Product-Wise
																		Installed Capacity for New Unit</label></td>
																<td><form:input path="productWiseCompl" type="text"
																		class="form-control" id="productWiseCompl" value="Yes"
																		readonly="true" name="productWiseCompl" /></td>
																<td><form:textarea path="productWiseObserv"
																		id="productWiseObserv"
																		placeholder="Observations Comment By PICUP"
																		name="productWiseObserv"
																		class="form-control border-info" rows="2" readonly="true"></form:textarea>
																</td>
															</tr>
															<tr>
																<td colspan="3">
																	<div class="table-responsive mt-3">
																		<table class="table table-stripped table-bordered">
																			<thead>
																				<tr>
																					<th>Proposed Products</th>
																					<th>Proposed Installed Capacity (PA)</th>
																				</tr>
																			</thead>
																			<tbody class="existing-proposed-products">
																				<c:forEach var="newProj" items="${newProjList}"
																					varStatus="counter">
																					<c:if test="${not empty newProjList}">
																						<tr>
																							<td>${newProj.newPropProducts}</td>
																							<td>${newProj.newPropInstallCapacity}</td>
																						</tr>

																					</c:if>
																				</c:forEach>
																			</tbody>
																		</table>
																	</div>
																</td>
															</tr>
														</c:if>


														<tr>
															<td>Whether product is being set up in phases?</td>
															<td><form:input path="wheProdSetupPhs"
																	id="wheProdSetupPhs" name="wheProdSetupPhs" value="Yes"
																	class="form-control" readonly="true" /></td>
															<td><form:textarea path="wheProdSetupPhsObserv"
																	id="wheProdSetupPhsObserv"
																	placeholder="Observations Comment By PICUP"
																	name="wheProdSetupPhsObserv"
																	class="form-control border-info" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive mt-3">
																	<table class="table table-stripped table-bordered">
																		<thead>
																			<tr>
																				<th>Phase</th>
																				<th>Product</th>
																				<th>Capacity</th>
																				<th>Unit</th>
																				<th>Proposed Capital Investment</th>
																				<th>Proposed Date of Commercial Production</th>
																				<th>Actual Date of Commercial Production</th>
																			</tr>
																		</thead>
																		<c:if test="${not empty pwInvDetailsList}">
																			<tbody>
																				<c:forEach var="pwInvObj"
																					items="${pwInvDetailsList}" varStatus="counter">
																					<tr>
																						<td>${pwInvObj.pwPhaseNo}</td>
																						<td>${pwInvObj.pwProductName}</td>
																						<td>${pwInvObj.pwCapacity}</td>
																						<td>${pwInvObj.pwUnit}</td>
																						<td>${pwInvObj.pwFci}</td>
																						<td>${pwInvObj.pwPropProductDate}</td>
																						<td><input type="text"
																							class="form-control border-info"
																							placeholder="DD/MM/YYY" value="2021-01-29"
																							disabled="disabled"></td>
																					</tr>
																				</c:forEach>
																			</tbody>

																		</c:if>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td>GSTN No. of the Industrial Unit/Firm/ Company</td>
															<td><form:input path="gstnNoOfCompl" type="text"
																	class="form-control"
																	value="${businessEntityDetails.gstin}" readonly="true" /></td>
															<td><form:textarea path="gstnNoOfObserv"
																	id="gstnNoOfObserv"
																	placeholder="Observations Comment By PICUP"
																	name="gstnNoOfObserv" class="form-control border-info"
																	rows="2" readonly="true"></form:textarea></td>
														</tr>
														<tr>
															<td>PAN No. of the Industrial Unit/Firm/Company</td>
															<td><form:input path="panNoOfCompl" type="text"
																	class="form-control"
																	value="${businessEntityDetails.companyPanNo}"
																	readonly="true" /></td>
															<td><form:textarea path="panNoOfObserv"
																	id="panNoOfObserv"
																	placeholder="Observations Comment By PICUP"
																	name="panNoOfObserv" class="form-control border-info"
																	rows="2" readonly="true"></form:textarea></td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<tbody>
																			<tr>
																				<td><label><strong>Confirmation
																							by Commercial Tax Department(If any)</strong></label> <form:textarea
																						path="confProvbyCTD" id="confProvbyCTD"
																						placeholder="Confirmation Comment By PICUP"
																						name="confProvbyCTD" readonly="true"
																						class="form-control border-info" rows="4"></form:textarea>
																					<label class="mt-2"><strong>Upload
																							Related Documents</strong></label>
																					<div class="custom-file">
																						<input type="file" disabled="disabled"
																							class="custom-file-input user-file"
																							onchange="return DocumentconfProvbyCTDDoc(event)"
																							id="confProvbyCTDDoc" name="confProvbyCTDDoc">
																						<label class="custom-file-label file"
																							id="chooseFileConfProvbyCTDDoc"
																							for="confProvbyCTDDoc">Choose File</label>
																					</div> <br> <br> <span id="confProvbyCTDDocMsg"
																					class="text-danger"></span></td>
																			</tr>
																			<tr>
																				<td><label><strong>Confirmation
																							by Bank(If any)</strong></label> <form:textarea
																						path="confProvbyBank" id="confProvbyBank"
																						placeholder="Confirmation Comment By PICUP"
																						name="confProvbyBank" readonly="true"
																						class="form-control border-info" rows="4"></form:textarea>
																					<label class="mt-2"><strong>Upload
																							Related Documents</strong></label>
																					<div class="custom-file">
																						<input type="file" disabled="disabled"
																							class="custom-file-input user-file"
																							maxlength="10" id="confProvbyBankDoc"
																							name="confProvbyBankDoc"> <label
																							class="custom-file-label file"
																							id="chooseFileConfProvbyBankDoc"
																							for="confProvbyBankDoc">Choose File</label>
																					</div></td>
																			</tr>
																			<tr>
																				<td><label><strong>External
																							Expert Report Details(If any)</strong></label> <form:textarea
																						path="externalERD" id="externalERD"
																						placeholder="Confirmation Comment By PICUP"
																						name="externalERD" readonly="true"
																						class="form-control border-info" rows="4"></form:textarea>
																					<label class="mt-2"><strong>Upload
																							Related Documents</strong></label>
																					<div class="custom-file">
																						<input type="file" disabled="disabled"
																							class="custom-file-input user-file"
																							maxlength="10" id="externalERDDoc"
																							name="externalERDDoc"> <label
																							class="custom-file-label file"
																							id="choosefileExternalERDDoc"
																							for="externalERDDoc">Choose File</label>
																					</div></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">Break-up
																	of Cost of Project- Investment Details</label></td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<th>S.No</th>
																			<th>Particulars <a href="javascript:void(0);"
																				class="remove-row" data-toggle="tooltip" title=""
																				data-original-title="(In accordance with Table-2 of Rules for implementation of IIEPP-2017.)"><i
																					class="fa fa-info-circle text-info"></i></a></th>
																			<th>If any investment made in the proposed
																				project prior to Opted Cut-off date. <a
																				href="javascript:void(0);" class="remove-row"
																				data-toggle="tooltip" title=""
																				data-original-title="If any investment made in the proposed project prior to Opted Cut-off date then indicate first date of such investment and amount invested from first date till opted cut-off date."><i
																					class="fa fa-info-circle text-info"></i></a>
																			</th>
																			<th colspan="2">Proposed Investment in the
																				project</th>
																			<th colspan="3">If any investment made in the
																				proposed project prior to 13.07.2017 then</th>
																			<th>If investment made in the proposed project
																				after 13.07.2017 <a href="javascript:void(0);"
																				class="remove-row" data-toggle="tooltip" title=""
																				data-original-title="If investment made in the proposed project after 13.07.2017 then indicate amount invested from first date of investment (cut-off date) to the date of submitting the application for LOC or till the date of commencement of commercial Production (in case commercial production started) (if project is being set up in phases, phase-wise investment be indicated from first date of investment)"><i
																					class="fa fa-info-circle text-info"></i></a>
																			</th>
																			<th>Investment (if any) in the proposed project
																				after commencement of commercial production <a
																				href="javascript:void(0);" class="remove-row"
																				data-toggle="tooltip" title=""
																				data-original-title="Investment (if any) in the proposed project after commencement of commercial production (in project is being set up in phases, then after commencement of commercial production of final phase) (to ascertain eligible capital investment as per para 2.9 of IIEPP Rules 2017)"><i
																					class="fa fa-info-circle text-info"></i></a>
																			</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td></td>
																				<td></td>
																				<td>Indicate first date of investment <form:input path="optCutofDate"
																						name="optCutofDate" type="date"
																						class="form-control mt-2 border-info" value=""
																						placeholder="DD-MM-YYYY" readonly="true"/>
																				</td>
																				<td>As per DPR</td>
																				<td>As per Bank Appraisal</td>
																				<td>Indicate amount invested from first date of
																					investment <a href="javascript:void(0);"
																					class="remove-row" data-toggle="tooltip" title=""
																					data-original-title="Indicate amount invested from first date of investment (cut-off date)  till 12.07.2017 (if project is being set up in phases, phase-wise investment be indicated)"><i
																						class="fa fa-info-circle text-info"></i></a>
																				</td>
																				<td>Indicate amount invested from 13.07.2017 to
																					the date of submitting the application for LOC or
																					till the date of commencement of commercial
																					Production <a href="javascript:void(0);"
																					class="remove-row" data-toggle="tooltip" title=""
																					data-original-title="Indicate amount invested from 13.07.2017 to the date of submitting the application for LOC or till the date of commencement of commercial Production (in case commercial production started) (if project is being set up in phases, phase-wise investment be indicated from 13.7.2017)"><i
																						class="fa fa-info-circle text-info"></i></a>
																				</td>
																				<td>Total (Col. 6+7)</td>
																				<td></td>
																				<td></td>
																			</tr>
																		</thead>
																		<tbody>
																			<%-- <tr>
																				<td></td>
																				<td></td>
																				<td>Amount invested from first date to opted
																					Cut- off date <form:input path="optCutofDate"
																						name="optCutofDate" type="text"
																						class="form-control mt-2 border-info" value=""
																						placeholder="DD-MM-YYYY" />

																				</td>
																				<td>As per DPR</td>
																				<td>As per Bank Appraisal</td>
																				<td>As per certificate of statutory auditor of
																					the company dated</td>
																				<td>Capital investment as per certificate of
																					empaneled CA</td>
																				<td>Capital investment verified by empaneled
																					Valuer</td>
																				<td>As per certificate of statutory auditor of
																					the company dated</td>
																			</tr> --%>
																			<tr>
																				<td>1</td>
																				<td>Land and Site Development</td>
																				<td width="12%;"><form:input path="landAmtInv"
																						type="text"
																						class="form-control is-numeric amtInvCutOff"
																						maxlength="12" readonly="true"/> <small class="words text-info"></small></td>
																				<td width="12%;"><form:input path="landPerDpr"
																						type="text" class="form-control is-numeric dprAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvDPRLC}"
																						readonly="true" />
																				<td width="12%;"><form:input
																						path="landBankApprai" type="text"
																						class="form-control is-numeric bankApprAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalLC}" readonly="true"/><small
																					class="words text-info"></small></td>

																				<td width="12%;"><form:input
																						path="landPerCerti" type="text"
																						class="form-control is-numeric perCertiAuditor" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>

																				<td width="12%;"><form:input
																						path="landCapInvCA" type="text" readonly="true"
																						class="form-control is-numeric capitalCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="landCapInvValuer" type="text"
																						readonly="true"
																						class="form-control is-numeric capitalValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input type="text"
																						path="landafterinv"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="landStatutoryAudit" type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>2</td>
																				<td>Building and Civil Works</td>
																				<td><form:input path="buildAmtInv" type="text"
																						class="form-control is-numeric amtInvCutOff"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="buildPerDpr" type="text"
																						value="${capInvDetails.capInvDPRBC}"
																						maxlength="12" class="form-control dprAmt"
																						readonly="true" /></td>
																				<td><form:input path="buildBankApprai"
																						type="text" maxlength="12" readonly="true"
																						class="form-control is-numeric bankApprAmt"
																						value="${capInvDetails.capInvAppraisalBC}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="buildPerCerti"
																						type="text" readonly="true"
																						class="form-control is-numeric perCertiAuditor"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="buildCapInvCA"
																						type="text"
																						class="form-control is-numeric capitalCA"
																						maxlength="12" readonly="true" /><small class="words text-info"></small></td>
																				<td><form:input path="buildCapInvValuer"
																						type="text" readonly="true"
																						class="form-control is-numeric capitalValuer"
																						maxlength="12"/><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="buildafterinv" type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="buildStatutoryAudit"
																						type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>3</td>
																				<td>Plant & Machinery</td>
																				<td><form:input path="plantAmtInv" type="text"
																						class="form-control is-numeric amtInvCutOff"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="plantPerDpr" type="text"
																						class="form-control is-numeric dprAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvDPRPMC}"
																						readonly="true" /></td>
																				<td><form:input path="plantBankApprai"
																						type="text" readonly="true"
																						class="form-control is-numeric bankApprAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalPMC}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="plantPerCertificate"
																						type="text"
																						class="form-control is-numeric perCertiAuditor"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="plantCapInvCA"
																						type="text"
																						class="form-control is-numeric capitalCA"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="plantCapInvValuer"
																						type="text" readonly="true"
																						class="form-control is-numeric capitalValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="plantdafterinv" type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="plantStatutoryAudit"
																						type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>4</td>
																				<td>Misc. Fixed Assets</td>
																				<td><form:input path="miscAmtInve" type="text"
																						class="form-control is-numeric amtInvCutOff"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="miscPerDpr" type="text"
																						class="form-control is-numeric dprAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvDPRMFA}"
																						readonly="true" /></td>
																				<td><form:input path="miscBankApprai"
																						type="text"
																						class="form-control is-numeric  bankApprAmt"
																						maxlength="12" readonly="true"
																						value="${capInvDetails.capInvAppraisalMFA}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="miscPerCertificate"
																						type="text" readonly="true"
																						class="form-control is-numeric perCertiAuditor"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="miscCapInvCA" type="text"
																						class="form-control is-numeric capitalCA"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="miscCapInvValuer"
																						type="text" readonly="true"
																						class="form-control is-numeric capitalValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="miscafterinv" type="text" readonly="true"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="miscStatutoryAuditor"
																						type="text" readonly="true"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td></td>
																				<td><strong>SubTotal (A)</strong> (1+2+3+4)</td>
																				<td><form:input path="subTtlAAmtInv"
																						type="text"
																						class="form-control is-numeric amtInvCutOffTotal subTtlCutOff"
																						readonly="true" maxlength="12" /></td>
																				<td><form:input path="subTtlAPerDpr"
																						type="text"
																						class="form-control is-numeric dprAmtTotal subTtlDPR"
																						readonly="true" maxlength="12" /></td>
																				<td><form:input path="subTtlABankApprai"
																						type="text"
																						class="form-control is-numeric bankApprAmtTotal subBankAppr"
																						readonly="true" maxlength="12" /></td>
																				<td><form:input path="subTtlAPerCerti"
																						type="text"
																						class="form-control is-numeric perCertiAuditorTotal subTtlAuditor"
																						readonly="true" maxlength="12" /></td>
																				<td><form:input path="subTtlACapInvCA"
																						type="text"
																						class="form-control is-numeric capitalCATotal subTtlCA"
																						readonly="true" maxlength="12" /></td>
																				<td><form:input path="subTtlACapInvValuer"
																						type="text"
																						class="form-control is-numeric capitalValuerTotal subTtlValuer"
																						readonly="true" maxlength="12" /></td>
																				<td width="12%;"><form:input
																						path="subTtlAafterinv" type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="subTtlAStatutoryAudit"
																						type="text"
																						class="form-control is-numeric statutoryAuditTotal subTtlStatutory"
																						readonly="true" maxlength="12" /></td>

																			</tr>
																			<tr>
																				<td>5</td>
																				<td>Provision for Cost Escalation &
																					Contingencies</td>
																				<td><form:input path="provisionAmtInve"
																						type="text"
																						class="form-control is-numeric cutOffDateAmt"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="provisionPerDpr"
																						type="text"
																						class="form-control is-numeric perDPRAmt"
																						value="${capInvDetails.capInvDPRTKF}"
																						maxlength="12" readonly="true" /></td>
																				<td><form:input path="provisionBankApprai"
																						type="text" readonly="true"
																						class="form-control is-numeric perBankAppr"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalTKF}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="provisionPerCerti"
																						type="text" readonly="true"
																						class="form-control is-numeric companyDated"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="provisionCapInvCA"
																						type="text" readonly="true"
																						class="form-control is-numeric empaneledCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="provisionCapInvValuer"
																						type="text" readonly="true"
																						class="form-control is-numeric empaneledValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="provisionafterinv" type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="provisionStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control is-numeric certificateAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>6</td>
																				<td>Preliminary & Preoperative Expenses</td>
																				<td><form:input path="prelimAmtInve"
																						type="text" readonly="true"
																						class="form-control is-numeric cutOffDateAmt"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="prelimPerDpr" type="text"
																						class="form-control is-numeric perDPRAmt"
																						value="${capInvDetails.capInvDPRPPE}"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="prelimBankApprai"
																						type="text" readonly="true"
																						class="form-control is-numeric perBankAppr"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalPPE}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="prelimPerCerti"
																						type="text"
																						class="form-control is-numeric companyDated "
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="prelimCapInvCA"
																						type="text" readonly="true"
																						class="form-control is-numeric empaneledCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="prelimCapInvValuer"
																						type="text" readonly="true"
																						class="form-control is-numeric empaneledValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="prelimafterinv" type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="prelimStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control is-numeric certificateAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>7</td>
																				<td>Interest During Construction Period</td>
																				<td><form:input path="interestAmtInve"
																						type="text" readonly="true"
																						class="form-control is-numeric cutOffDateAmt"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="interestPerDpr"
																						type="text" readonly="true"
																						class="form-control is-numeric perDPRAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvDPRICP}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="interestBankApprai"
																						type="text" readonly="true"
																						class="form-control is-numeric perBankAppr"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalICP}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="interestPerCerti"
																						type="text" readonly="true"
																						class="form-control is-numeric companyDated "
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="interestCapInvCA"
																						type="text" readonly="true"
																						class="form-control is-numeric empaneledCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="interestCapInvValuer"
																						type="text" readonly="true"
																						class="form-control is-numeric empaneledValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="interestafterinv" type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="interestStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control is-numeric certificateAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>8</td>
																				<td>Margin Money for Working Capital</td>
																				<td><form:input path="marginAmtInve"
																						type="text" readonly="true"
																						class="form-control is-numeric cutOffDateAmt"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="marginPerDpr" type="text"
																						class="form-control is-numeric perDPRAmt"
																						maxlength="12" readonly="true"
																						value="${capInvDetails.capInvDPRMMC}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="marginBankApprai"
																						type="text" readonly="true"
																						class="form-control is-numeric perBankAppr"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalMMC}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="marginPerCerti"
																						type="text" readonly="true"
																						class="form-control is-numeric companyDated "
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="marginCapInvCA"
																						type="text" readonly="true"
																						class="form-control is-numeric empaneledCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="marginCapInvValuer"
																						type="text" readonly="true"
																						class="form-control is-numeric empaneledValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="marginafterinv" type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="marginStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control is-numeric certificateAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>9</td>
																				<td>Other, If any</td>
																				<td><form:input path="otherAmtInve" type="text"
																						class="form-control is-numeric cutOffDateAmt"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="otherPerDpr" type="text"
																						class="form-control is-numeric perDPRAmt"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="otherBankApprai"
																						type="text" readonly="true"
																						class="form-control is-numeric perBankAppr"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="otherPerCerti"
																						type="text" readonly="true"
																						class="form-control is-numeric companyDated "
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="otherCapInvCA"
																						type="text" readonly="true"
																						class="form-control is-numeric empaneledCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="otherCapInvValuer"
																						type="text" readonly="true"
																						class="form-control is-numeric empaneledValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="otherafterinv" type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" readonly="true"/><small class="words text-info"></small></td>
																				<td><form:input path="otherStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control is-numeric certificateAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td></td>
																				<td><strong>SubTotal (B)</strong> (5+6+7+8+9)</td>
																				<td><form:input path="subTtlBAmtInv"
																						type="text"
																						class="form-control cutOffDateAmtTotal subTtlCutOff"
																						readonly="true" /></td>
																				<td><form:input path="subTtlBPerDpr"
																						type="text"
																						class="form-control perDPRAmtTotal subTtlDPR"
																						readonly="true" /></td>
																				<td><form:input path="subTtlBBankApprai"
																						type="text"
																						class="form-control perBankApprTotal subBankAppr"
																						readonly="true" /></td>
																				<td><form:input path="subTtlBPerCerti"
																						type="text"
																						class="form-control companyDatedTotal subTtlAuditor"
																						readonly="true" /></td>
																				<td><form:input path="subTtlBCapInvCA"
																						type="text"
																						class="form-control empaneledCATotal subTtlCA"
																						readonly="true" /></td>
																				<td><form:input path="subTtlBCapInvValuer"
																						type="text"
																						class="form-control empaneledValuerTotal subTtlValuer"
																						readonly="true" /></td>
																				<td width="12%;"><form:input
																						path="subTtlBCafterinv" type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="subTtlBStatutoryAudit"
																						type="text"
																						class="form-control certificateAuditTotal subTtlStatutory"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td></td>
																				<td><strong>Total (A+B)</strong></td>
																				<td><form:input path="ttlAmtInve" type="text"
																						class="form-control subTtlCutOffTotal"
																						readonly="true" /></td>
																				<td><form:input path="ttlPerDpr" type="text"
																						class="form-control subTtlDPRTotal"
																						readonly="true" /></td>
																				<td><form:input path="TtlBankApprai"
																						type="text" class="form-control subBankApprTotal"
																						readonly="true" /></td>
																				<td><form:input path="ttlPerCerti" type="text"
																						class="form-control subTtlAuditorTotal"
																						readonly="true" /></td>
																				<td><form:input path="ttlCapInvCA" type="text"
																						class="form-control subTtlCATotal" readonly="true" /></td>
																				<td><form:input path="ttlCapInvValuer"
																						type="text" class="form-control subTtlValuerTotal"
																						readonly="true" /></td>
																				<td width="12%;"><form:input path="ttlafterinv"
																						type="text"
																						class="form-control is-numeric statutoryAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="ttlStatutoryAudit"
																						type="text"
																						class="form-control subTtlStatutoryTotal"
																						readonly="true" /></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3"><form:textarea
																	path="breakUpCostObserve" id="breakUpCostObserve"
																	placeholder="Observations Comment By PICUP"
																	name="breakUpCostObserve"
																	class="form-control border-info" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">Means
																	Of Financing</label></td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-responsive">
																		<thead>
																			<tr>
																				<th></th>
																				<th>Particulars</th>
																				<th colspan="2">Proposed Investment in the
																					project</th>
																				<th colspan="3">If any investment made in the
																					proposed project prior to 13.07.2017 then</th>
																				<th>If investment made in the proposed project
																					after 13.07.2017 <a href="javascript:void(0);"
																					class="remove-row" data-toggle="tooltip" title=""
																					data-original-title="If investment made in the proposed project after 13.07.2017 then indicate amount invested from first date of investment (cut-off date) to the date of submitting the application for LOC or till the date of commencement of commercial Production (in case commercial production started) (if project is being set up in phases, phase-wise investment be indicated from first date of investment)"><i
																						class="fa fa-info-circle text-info"></i></a>
																				</th>
																				<th>Investment (if any) in the proposed project
																					after commencement of commercial production <a
																					href="javascript:void(0);" class="remove-row"
																					data-toggle="tooltip" title=""
																					data-original-title="Investment (if any) in the proposed project after commencement of commercial production (in project is being set up in phases, then after commencement of commercial production of final phase) (to ascertain eligible capital investment as per para 2.9 of IIEPP Rules 2017)"><i
																						class="fa fa-info-circle text-info"></i></a>
																				</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td>S.No.</td>
																				<td>Particulars</td>
																				<td>As per DPR</td>
																				<td>As per Bank Appraisal</td>
																				<td>Indicate amount invested from first date of
																					investment <a href="javascript:void(0);"
																					class="remove-row" data-toggle="tooltip" title=""
																					data-original-title="Indicate amount invested from first date of investment (cut-off date) till 12.07.2017(if project is being set up in phases, phase-wise investment be indicated)"><i
																						class="fa fa-info-circle text-info"></i></a>
																				</td>
																				<td>Indicate amount invested from 13.07.2017 to
																					the date of submitting the application for LOC or
																					till the date of commencement of commercial
																					Production <a href="javascript:void(0);"
																					class="remove-row" data-toggle="tooltip" title=""
																					data-original-title="Indicate amount invested from 13.07.2017 to the date of submitting the application for LOC or till the date of commencement of commercial Production (in case commercial production started) (if project is being set up in phases, phase-wise investment be indicated from 13.7.2017)"><i
																						class="fa fa-info-circle text-info"></i></a>
																				</td>
																				<td>Total (Col. 5+6)</td>
																				<td></td>
																				<td></td>
																			</tr>
																			<tr>
																				<td></td>
																				<td colspan="6"><strong>Equity</strong></td>

																			</tr>
																			<tr>
																				<td>1.1</td>
																				<td>Equity Share Capital</td>
																				<td><form:input path="equityCapPerDpr"
																						type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" readonly="true"/> <small class="words text-info"></small></td>
																				<td><form:input path="equityCapBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" readonly="true"/> <small class="words text-info"></small></td>
																				<td><form:input path="equityCapPerCerti"
																						type="text"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" readonly="true"/> <small class="words text-info"></small></td>
																				<td><form:input path="equityCapCapInvCA"
																						type="text"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" readonly="true"/> <small class="words text-info"></small></td>
																				<td><form:input path="equityCapStatutoryAudit"
																						type="text"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" readonly="true"/> <small class="words text-info"></small></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																			</tr>
																			<tr>
																				<td>1.2</td>
																				<td>Internal Cash Accruals</td>
																				<td><form:input path="intCashPerDpr"
																						type="text"
																						class="form-control FinancingDPR is-numeric" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intCashBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intCashPerCerti"
																						type="text"
																						class="form-control FinaPerCerti is-numeric" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intCashCapInvCA"
																						type="text" readonly="true"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intCashStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																			</tr>
																			<tr>
																				<td>1.3</td>
																				<td>Interest Free Unsecured Loans & Promoter as
																					contribution</td>
																				<td><form:input path="intFreePerDpr"
																						type="text" readonly="true"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intFreeBankApprai"
																						type="text" readonly="true"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intFreePerCerti"
																						type="text" readonly="true"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="intFreeCapInvCA"
																						type="text" readonly="true"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intFreeStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																			</tr>
																			<tr>
																				<td>1.4</td>
																				<td>Security Deposit</td>
																				<td><form:input path="finOthPerDpr" type="text"
																						class="form-control FinancingDPR is-numeric" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="finOthBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="finOthPerCerti"
																						type="text" readonly="true"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="finOthCapInvCA"
																						type="text" readonly="true"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="finOthStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																			</tr>
																			<tr>
																				<td>1.5</td>
																				<td>Advances from Dealers</td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																			</tr>

																			<tr>
																				<td>1.6</td>
																				<td>Other, If any</td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																			</tr>
																			<tr>
																				<td>2.0</td>
																				<td colspan="6"><strong>Loan</strong></td>

																			</tr>
																			<tr>
																				<td>2.1</td>
																				<td>From FI's</td>
																				<td><form:input path="FromFiPerDpr" type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" readonly="true"/> <small class="words text-info"></small></td>
																				<td><form:input path="FromFiBankApprai"
																						type="text" readonly="true"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="FromFiPerCerti"
																						type="text" readonly="true"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				
																				<td width="9%;"><form:input
																						path="FromFiCapInvCA" type="text"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" readonly="true"/> <small class="words text-info"></small></td>
																				<td><form:input path="FromFiStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																			</tr>
																			<tr>
																				<td>2.2</td>
																				<td>From Bank</td>
																				<td><form:input path="FrBankPerDpr" type="text" readonly="true"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="FrBankBankApprai"
																						type="text" readonly="true"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="FrBankPerCerti"
																						type="text" readonly="true"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				
																				<td width="9%;"><form:input
																						path="FrBankCapInvCA" type="text"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" readonly="true"/> <small class="words text-info"></small></td>
																				<td><form:input path="FrBankStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																			</tr>
																			<tr>
																				<td>2.3</td>
																				<td>Other, If any</td>
																				<td><form:input path="PlantMachPerDpr"
																						type="text" readonly="true"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="PlantMachBankApprai"
																						type="text" readonly="true"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="Plant_MachPerCerti"
																						type="text" readonly="true"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="PlantMachCapInvCA"
																						type="text" readonly="true"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="PlantMachStatutoryAudit"
																						type="text" readonly="true"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																			</tr>
																			<tr>
																				<td>3.0</td>
																				<td><strong>Total</strong></td>
																				<td><form:input path="finTttlPerDpr"
																						type="text" class="form-control FinancingDPRTotal"
																						readonly="true" /></td>
																				<td><form:input path="finTttlBankApprai"
																						type="text" class="form-control FinaBankApprTotal"
																						readonly="true" /></td>
																				<td><form:input path="finTttlPerCerti"
																						type="text" class="form-control FinaPerCertiTotal"
																						readonly="true" /></td>
																				<td><form:input path="finTttlCapInvCA"
																						type="text" class="form-control FinancingCATotal"
																						readonly="true" /></td>
																				<td><form:input path="finTttlStatutoryAudit"
																						type="text"
																						class="form-control  FinaStaAuditTotal"
																						readonly="true" /></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																				<td><input type="text" class="form-control" readonly="true"></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3"><form:textarea
																	path="financingObserve" id="financingObserve"
																	placeholder="Observations Comment By PICUP"
																	name="financingObserve" readonly="true"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">Details
																	of Incentive Claimed (As per LoC)</label></td>
														</tr>
														<tr>
															<td colspan="3"><form:form
																	modelAttribute="incentiveDeatilsData"
																	name="incentiveDeatilsform" method="post">
																	<div class="row">
																		<div class="col-sm-12">
																			<div class="table-responsive">
																				<table class="table table-stripped table-bordered">
																					<thead>
																						<tr>
																							<th>Sr. No.</th>
																							<th style="width: 25%;">Details of
																								Incentives Sought and Concerned Department</th>
																							<th style="width: 10%;">Amount of Incentives
																								<small>(Rs. in crores)</small>
																							</th>
																							<th>Incentive as per Rules</th>
																							<th style="width: 25%;">PICUP Officer's
																								Remark</th>
																						</tr>
																					</thead>
																					<tbody>
																						<tr class="ISF_Claim_Reim-row">
																							<td>1</td>
																							<td>Amount of SGST claimed for reimbursement
																								<br> <strong>Industrial
																									Development/ Commercial Tax</strong>
																							</td>
																							<td><form:input path="ISF_Claim_Reim"
																									type="text" class="ISF_Reim_SGST form-control"
																									name="ISF_Claim_Reim" id="ISF_Claim_Reim"
																									maxlength="12" readonly="true"></form:input> <span
																								class="text-danger d-inline-block mt-2"></span></td>

																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																								target="_blank">As per Table 3</a> of Rules
																								related to IIEPP-2017 (The Rules), there is a
																								provision for reimbursement of 70% of deposited
																								GST (Net SGST) for 10 years.</td>

																							<td><form:textarea path="sgstRemark"
																									rows="4" maxlength="1000" class="form-control" readonly="true"></form:textarea></td>
																						</tr>

																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfSgstComment}">
																						<tr class="ISF_Claim_Reim-row">
																							<td colspan="5">
																								<p class="text-info">Comment From :
																									Industrial Development/ Commercial Tax</p> <form:textarea
																									class="form-control" path="isfSgstComment"
																									name="isfSgstComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea>
																							</td>
																						</tr>
																					</c:if> --%>
																						<tr class="ISF_Reim_SCST-row">
																							<td>1.1</td>
																							<td>Additional 10% GST where 25% minimum
																								SC/ST workers employed subject to minimum of 400
																								total workers in industrial undertakings located
																								in Paschimanchal and minimum of 200 total
																								workers in B-P-M <br> <strong>Industrial
																									Development/ Commercial Tax</strong>

																							</td>
																							<td><form:input path="ISF_Reim_SCST"
																									type="text" id="Add_SCST_Textbox"
																									class="ISF_Reim_SGST form-control"
																									name="ISF_Reim_SCST" maxlength="12"
																									readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																								target="_blank">As per Table 3</a> of The Rules,
																								there is a provision for 75% Stamp Duty
																								Exemption in Paschimanchal Region. <br>Further,
																								as per G.O. No. 1515/77-6-18-5(M)/17, dated
																								1.5.2018, there is a provision for reimbursement
																								equivalent to the paid Stamp Duty, for which
																								company will have to apply before concerned GM,
																								DIC.</td>

																							<td><form:textarea path="scstRemark"
																									rows="4" maxlength="1000" class="form-control" readonly="true"/></td>
																						</tr>

																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfScstComment}">
																						<tr class="ISF_Reim_SCST-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Industrial Development/ Commercial Tax</p> <form:textarea
																									class="form-control" path="isfScstComment"
																									name="isfScstComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>

																						<tr class="ISF_Reim_FW-row">
																							<td>1.2</td>
																							<td>Additional 10% GST where 40% minimum
																								female workers employed subject to minimum of
																								400 total workers in industrial undertakings
																								located in Paschimanchal and minimum of 200
																								total workers in B-P-M</td>
																							<td><form:input path="ISF_Reim_FW"
																									type="text" id="Add_FW_Textbox" maxlength="12"
																									class="ISF_Reim_SGST form-control"
																									name="ISF_Reim_FW" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																								target="_blank">As per para 3.3</a> and Table 3
																								of The Rules, there is a provision for incentive
																								of reimbursement of EPF to the extent of 50% of
																								employer's contribution to all such new
																								Industrial undertakings providing direct
																								employment to 100 or more unskilled workers,
																								after three years from the date of commercial
																								production for a period of 5 years.</td>


																							<td><form:textarea path="fwRemark" rows="4"
																									maxlength="1000" class="form-control" readonly="true"/></td>
																						</tr>


																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isffwComment}">

																						<tr class="ISF_Reim_FW-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Industrial Development/ Commercial Tax</p> <form:textarea
																									class="form-control" path="isffwComment"
																									name="isffwComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>
																						<tr class="ISF_Reim_BPLW-row">
																							<td>1.3</td>
																							<td>Additional 10% GST where 25% minimum BPL
																								workers employed subject to minimum of 400 total
																								workers in industrial undertakings located in
																								Paschimanchal and minimum of 200 total workers
																								in B-P-M</td>
																							<td><form:input path="ISF_Reim_BPLW"
																									type="text" class="ISF_Reim_SGST form-control"
																									maxlength="12" name="ISF_Reim_BPLW"
																									id="Add_BPLW_Textbox" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																								target="_blank">As per para 3.5.7</a> of the
																								Rules, there is a following provision: The
																								industries which are disallowed for input tax
																								credit under the GST regime, will be eligible
																								for reimbursement of that amount of GST paid on
																								purchase of plant and machinery, building
																								material and other capital goods during
																								construction and commissioning period and raw
																								materials and other inputs in respect of which
																								input tax credit has not been allowed. While
																								calculating the overall eligible capital
																								investment such amount will be added to the
																								fixed capital investment.</td>

																							<td><form:textarea path="bplRemark"
																									maxlength="1000" rows="4" class="form-control" readonly="true"></form:textarea></td>
																						</tr>

																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfBplComment}">
																						<tr class="ISF_Reim_BPLW-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Industrial Development/ Commercial Tax</p> <form:textarea
																									class="form-control" path="isfBplComment"
																									name="isfBplComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>

																						<tr class="ISF_Stamp_Duty_EX-row">
																						<tr>
																							<td>2</td>
																							<td>Amount of Stamp Duty Exemption<br>
																								<strong>Stamp & Registration</strong>
																							</td>
																							<td><form:input path="ISF_Stamp_Duty_EX"
																									type="text" class="ISF_Reim_SGST form-control"
																									maxlength="12" name="ISF_Stamp_Duty_EXe"
																									id="ISF_Stamp_Duty_EXe" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																								target="_blank">As per Table 3</a> of The Rules,
																								there is a provision for <span id="sdepercent">
																							</span> % Stamp Duty Exemption in ${region} Region.
																								Further, as per G.O. No. 1515/77-6-18-5(M)/17,
																								dated 1.5.2018, there is a provision for
																								reimbursement equivalent to the paid Stamp Duty,
																								for which company will have to apply before
																								concerned GM, DIC.</td>
																							<td><form:textarea
																									path="stampDutyExemptRemark" maxlength="1000"
																									rows="4" class="form-control" readonly="true"></form:textarea></td>
																						</tr>

																						<!-- sachin -->
																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfStampComment}">
																						<tr class="ISF_Stamp_Duty_EX-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Stamp & Registration</p> <form:textarea
																									class="form-control" path="isfStampComment"
																									name="isfStampComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>


																						<tr class="ISF_Amt_Stamp_Duty_Reim-row">
																							<td></td>
																							<td>Amount of Stamp Duty Reimbursement</td>
																							<td><form:input
																									path="ISF_Amt_Stamp_Duty_Reim" type="text"
																									class="ISF_Amt_Stamp_Duty_Reim form-control"
																									maxlength="12" name="ISF_Amt_Stamp_Duty_Reim"
																									id="Add_ISF_Amt_Stamp_Duty_Reim"
																									readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																								target="_blank">As per Table 3</a> of The Rules,
																								there is a provision for <span id="sdrpercent">
																							</span> % Stamp Duty Exemption in ${region} Region.
																								Further, as per G.O. No. 1515/77-6-18-5(M)/17,
																								dated 1.5.2018, there is a provision for
																								reimbursement equivalent to the paid Stamp Duty,
																								for which company will have to apply before
																								concerned GM, DIC.</td>
																							<td><form:textarea
																									path="stampDutyReimRemark" maxlength="1000"
																									rows="4" class="form-control" readonly="true"></form:textarea></td>
																						</tr>
																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfStampComment}">
																						<tr class="ISF_Amt_Stamp_Duty_Reim-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Stamp & Registration</p> <form:textarea
																									class="form-control" path="isfStampComment"
																									name="isfStampComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>
																						<tr class="ISF_Additonal_Stamp_Duty_EX-row">
																							<td>2.1</td>
																							<td>Additional Stamp Duty exemption @20%
																								upto maximum of 100% in case of industrial
																								undertakings having 75% equity owned by
																								Divyang/SC/ ST/Females Promoters</td>
																							<td><form:input
																									path="ISF_Additonal_Stamp_Duty_EX" type="text"
																									class="ISF_Additonal_Stamp_Duty_EX form-control"
																									maxlength="12"
																									name="ISF_Additonal_Stamp_Duty_EX"
																									id="ISF_Additonal_Stamp_Duty_EX"
																									readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																								target="_blank">As per Table 3</a> of The Rules,
																								there is a provision for dynamic percentage
																								Stamp Duty Exemption in Dynamic Region. Further,
																								as per G.O. No. 1515/77-6-18-5(M)/17, dated
																								1.5.2018, there is a provision for reimbursement
																								equivalent to the paid Stamp Duty, for which
																								company will have to apply before concerned GM,
																								DIC.</td>

																							<td><form:textarea path="divyangSCSTRemark"
																									rows="4" maxlength="1000" class="form-control" readonly="true"/></td>
																						</tr>
																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfStampscstComment}">
																						<tr class="ISF_Additonal_Stamp_Duty_EX-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Stamp & Registration</p> <form:textarea
																									class="form-control" path="isfStampscstComment"
																									name="isfStampscstComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>
																						<tr class="ISF_Epf_Reim_UW-row">
																							<td>3</td>
																							<td>EPF Reimbursement (100 or more unskilled
																								workers) <br> <strong>Labour/Industrial
																									Development</strong>
																							</td>
																							<td><form:input path="ISF_Epf_Reim_UW"
																									type="text"
																									class="ISF_Epf_Reim_UW form-control"
																									maxlength="12" name="ISF_Amt_Stamp_Duty_Reim"
																									id="Add_ISF_Amt_Stamp_Duty_Reim"
																									readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																								target="_blank">As per para 3.3</a> and Table 3
																								of The Rules, there is a provision for incentive
																								of reimbursement of EPF to the extent of 50% of
																								employer's contribution to all such new
																								Industrial undertakings providing direct
																								employment to 100 or more unskilled workers,
																								after three years from the date of commercial
																								production for a period of 5 years.</td>

																							<td><form:textarea path="epfUnsklRemark"
																									rows="4" class="form-control" readonly="true"/></td>
																						</tr>
																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfepfComment}">
																						<tr class="ISF_Epf_Reim_UW-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Labour/Industrial Development</p> <form:textarea
																									class="form-control" path="isfepfComment"
																									name="isfepfComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>
																						<tr class="ISF_Add_Epf_Reim_SkUkW-row">
																							<td>3.1</td>
																							<td>Additional 10% EPF Reimbursement (200
																								direct skilled and unskilled workers)</td>
																							<td><form:input
																									path="ISF_Add_Epf_Reim_SkUkW" type="text"
																									class="ISF_Add_Epf_Reim_SkUkW form-control"
																									maxlength="12" name="ISF_Add_Epf_Reim_SkUkW"
																									id="ISF_Add_Epf_Reim_SkUkW" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																								target="_blank">As per para 3.3</a> and Table 3
																								of The Rules, there is a provision for incentive
																								of reimbursement of EPF to the extent of 50% of
																								employer's contribution to all such new
																								Industrial undertakings providing direct
																								employment to 100 or more unskilled workers,
																								after three years from the date of commercial
																								production for a period of 5 years.</td>

																							<td><form:textarea path="epfSklUnsklRemark"
																									rows="4" maxlength="1000" class="form-control" readonly="true"/></td>
																						</tr>
																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfepfaddComment}">
																						<tr class="ISF_Add_Epf_Reim_SkUkW-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Labour/Industrial Development</p> <form:textarea
																									class="form-control" path="isfepfaddComment"
																									name="isfepfaddComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>
																						<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
																							<td>3.2</td>
																							<td>Additional 10% EPF Reimbursement upto
																								maximum of 70% in case of industrial
																								undertakings having 75% equity owned by
																								Divyang/SC/ST/Females Promoters</td>
																							<td><form:input
																									path="ISF_Add_Epf_Reim_DIVSCSTF" type="text"
																									class="ISF_Add_Epf_Reim_DIVSCSTF form-control"
																									maxlength="12" name="ISF_Add_Epf_Reim_DIVSCSTF"
																									id="ISF_Add_Epf_Reim_DIVSCSTF" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																								target="_blank">As per para 3.3</a> and Table 3
																								of The Rules, there is a provision for incentive
																								of reimbursement of EPF to the extent of 50% of
																								employer's contribution to all such new
																								Industrial undertakings providing direct
																								employment to 100 or more unskilled workers,
																								after three years from the date of commercial
																								production for a period of 5 years.</td>

																							<td><form:textarea path="epfDvngSCSTRemark"
																									rows="4" maxlength="1000" class="form-control" readonly="true"/></td>
																						</tr>
																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfepfscComment}">
																						<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Labour/Industrial Development</p> <form:textarea
																									class="form-control" path="isfepfscComment"
																									name="isfepfscComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>
																						<tr class="ISF_Cis-row">
																							<td>4</td>
																							<td>Capital Interest Subsidy <br> <strong>Industrial
																									Development</strong></td>
																							<td><form:input path="ISF_Cis" type="text"
																									class="ISF_Cis form-control" maxlength="12"
																									name="ISF_Cis" id="ISF_Cis" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
																								target="_blank">As per para 3.5.1</a> of The
																								Rules, there is a provision for capital interest
																								subsidy @ 5% p.a. or actual interest paid
																								whichever is less annually for 5 years in the
																								form of reimbursement of interest paid on
																								outstanding loan taken for procurement of plant
																								& machinery, subject to an annual ceiling of Rs.
																								50 lacs.</td>
																							<td><form:textarea path="capIntSubRemark"
																									maxlength="1000" rows="4" class="form-control" readonly="true"></form:textarea></td>
																						</tr>

																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfcapisComment}">
																						<tr class="ISF_Cis-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Industrial Development</p> <form:textarea
																									class="form-control" path="isfcapisComment"
																									name="isfcapisComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>

																						<tr class="ISF_ACI_Subsidy_Indus-row">
																							<td>4.1</td>
																							<td>Additional Capital Interest Subsidy@2.5%
																								upto maximum of 7.5% in case of industrial
																								undertakings having 75% equity owned by
																								Divyang/SC/ST/Females Promoters</td>
																							<td><form:input path="ISF_ACI_Subsidy_Indus"
																									type="text"
																									class="ISF_ACI_Subsidy_Indus form-control"
																									maxlength="12" name="ISF_ACI_Subsidy_Indus"
																									id="ISF_ACI_Subsidy_Indus" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
																								target="_blank">As per para 3.5.1</a> of The
																								Rules, there is a provision for capital interest
																								subsidy @ 5% p.a. or actual interest paid
																								whichever is less annually for 5 years in the
																								form of reimbursement of interest paid on
																								outstanding loan taken for procurement of plant
																								& machinery, subject to an annual ceiling of Rs.
																								50 lacs.</td>

																							<td><form:textarea path="aciSubsidyRemark"
																									rows="4" maxlength="1000" class="form-control" readonly="true"/></td>
																						</tr>

																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfcapisaComment}">
																						<tr class="ISF_ACI_Subsidy_Indus-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Industrial Development</p> <form:textarea
																									class="form-control" path="isfcapisaComment"
																									name="isfcapisaComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>

																						<tr class="ISF_Infra_Int_Subsidy-row">
																							<td>5</td>
																							<td>Infrastructure Interest Subsidy <br>
																								<strong>Industrial Development</strong></td>
																							<td><form:input path="ISF_Infra_Int_Subsidy"
																									type="text"
																									class="ISF_Infra_Int_Subsidy form-control"
																									maxlength="12" name="ISF_Infra_Int_Subsidy"
																									id="ISF_Infra_Int_Subsidy" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
																								target="_blank">As per para 3.5.2</a> of the
																								Rules, there is a provision for incentive of
																								infrastructure interest subsidy @ 5% p.a. or
																								actual interest paid whichever is less annually
																								for 5 years in the form of reimbursement of
																								interest paid on outstanding loan taken for
																								development of infrastructural amenities (as
																								defined in para 2.17) subject to an overall
																								ceiling of Rs. 1 Crore.</td>
																							<td><form:textarea path="infraIntSubRemark"
																									maxlength="1000" rows="4" class="form-control" readonly="true"></form:textarea></td>
																						</tr>

																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfinfComment}">
																						<tr class="ISF_Infra_Int_Subsidy-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Industrial Development</p> <form:textarea
																									class="form-control" path="isfinfComment"
																									name="isfinfComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>

																						<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
																							<td>5.1</td>
																							<td>Additional Infrastructure Interest
																								Subsidy @2.5% upto maximum of 7.5% in case of
																								industrial undertakings having 75% equity owned
																								by Divyang/SC/ST/Females Promoters</td>
																							<td><form:input
																									path="ISF_AII_Subsidy_DIVSCSTF" type="text"
																									class="ISF_AII_Subsidy_DIVSCSTF form-control"
																									maxlength="12" name="ISF_AII_Subsidy_DIVSCSTF"
																									id="ISF_AII_Subsidy_DIVSCSTF" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
																								target="_blank">As per para 3.5.2</a> of the
																								Rules, there is a provision for incentive of
																								infrastructure interest subsidy @ 5% p.a. or
																								actual interest paid whichever is less annually
																								for 5 years in the form of reimbursement of
																								interest paid on outstanding loan taken for
																								development of infrastructural amenities (as
																								defined in para 2.17) subject to an overall
																								ceiling of Rs. 1 Crore.</td>

																							<td><form:textarea path="aiiSubsidyRemark"
																									rows="4" maxlength="1000" class="form-control" readonly="true"></form:textarea></td>
																						</tr>

																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfinfaComment}">
																						<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Industrial Development</p> <form:textarea
																									class="form-control" path="isfinfaComment"
																									name="isfinfaComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>

																						<tr class="ISF_Loan_Subsidy-row">
																							<td>6</td>
																							<td>Interest Subsidy on loans for industrial
																								research, quality improvement, etc. <br> <strong>Industrial
																									Development</strong>
																							</td>
																							<td><form:input path="ISF_Loan_Subsidy"
																									type="text"
																									class="ISF_Loan_Subsidy form-control"
																									maxlength="12" name="ISF_Loan_Subsidy"
																									id="ISF_Loan_Subsidy" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
																								target="_blank">As per para 3.5.3</a> of The
																								Rules, there is a provision for Interest subsidy
																								on loans for industrial research @ 5% or actual
																								interest paid whichever is less annually for 5
																								years in the form of reimbursement of interest
																								paid on outstanding loan taken for industrial
																								research, quality improvement and development of
																								products by incurring expenditure on procurement
																								of plant, machinery & equipment for setting up
																								testing labs, quality certification labs and
																								tool rooms, subject to an overall ceiling of Rs.
																								1 Crore</td>
																							<td><form:textarea path="loanIntSubRemark"
																									maxlength="1000" rows="4" class="form-control" readonly="true"></form:textarea></td>
																						</tr>

																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfloanComment}">
																						<tr class="ISF_Loan_Subsidy-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Industrial Development</p> <form:textarea
																									class="form-control" path="isfloanComment"
																									name="isfloanComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>

																						<tr class="ISF_Tax_Credit_Reim-row">
																							<td>7</td>
																							<td>Reimbursement of Disallowed Input Tax
																								Credit on plant, building materials, and other
																								capital goods. <br> <strong>Industrial
																									Development</strong></br>
																							</td>
																							<td><form:input path="ISF_Tax_Credit_Reim"
																									type="text"
																									class="ISF_Tax_Credit_Reim form-control"
																									maxlength="12" name="ISF_Tax_Credit_Reim"
																									id="ISF_Tax_Credit_Reim" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																								target="_blank">As per para 3.5.7</a> of the
																								Rules, it is provided - The industries which are
																								disallowed for input tax credit under the GST
																								regime, will be eligible for reimbursement of
																								that amount of GST paid on purchase of plant and
																								machinery, building material and other capital
																								goods during construction and commissioning
																								period and raw materials and other inputs in
																								respect of which input tax credit has not been
																								allowed. While calculating the overall eligible
																								capital investment such amount will be added to
																								the fixed capital investment.</td>
																							<td><form:textarea path="inputTaxRemark"
																									rows="4" maxlength="1000" class="form-control" readonly="true"></form:textarea></td>
																						</tr>

																						<%-- 	<c:if
																						test="${not empty incentiveDeatilsData.isfdisComment}">
																						<tr class="ISF_Tax_Credit_Reim-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Industrial Development</p> <form:textarea
																									class="form-control" path="isfdisComment"
																									name="isfdisComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>

																						<tr class="ISF_EX_E_Duty-row">
																							<td>8</td>
																							<td>Exemption from Electricity Duty from
																								captive power for self-use <br> <strong>Energy/UPPCL/
																									Electricity Safety</strong></br>
																							</td>
																							<td><form:input path="ISF_EX_E_Duty"
																									type="text" class="ISF_EX_E_Duty form-control"
																									maxlength="12" name="ISF_EX_E_Duty"
																									id="ISF_EX_E_Duty" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																								target="_blank">As per para 3.5.5</a> of The
																								Rules, there is a provision for Exemption from
																								Electricity Duty for 10 years to all new
																								industrial undertakings producing electricity
																								from captive power plants for self-use.</td>
																							<td><form:textarea path="elecDutyCapRemark"
																									maxlength="1000" rows="4" class="form-control" readonly="true"></form:textarea></td>
																						</tr>

																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfelepodownComment}">
																						<tr class="ISF_EX_E_Duty-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : UPPCL</p> <form:textarea
																									class="form-control" path="isfelepodownComment"
																									name="isfelepodownComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>

																						<tr class="ISF_EX_E_Duty_PC-row">
																							<td>9</td>
																							<td>Exemption from Electricity duty on power
																								drawn from power companies <br> <strong>Energy/UPPCL/
																									Electricity Safety</strong></br>
																							</td>
																							<td><form:input path="ISF_EX_E_Duty_PC"
																									type="text"
																									class="ISF_EX_E_Duty_PC form-control"
																									maxlength="12" name="ISF_EX_E_Duty_PC"
																									id="ISF_EX_E_Duty_PC" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																								target="_blank">As per para 3.5.4</a> of The
																								Rules, there is a provision for Exemption from
																								Electricity Duty to all new industrial
																								undertakings set up in the State for 10 years.</td>
																							<td><form:textarea
																									path="elecDutyDrawnRemark" maxlength="1000"
																									rows="4" class="form-control" readonly="true"></form:textarea></td>
																						</tr>


																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfElecdutyComment}">
																						<tr class="ISF_EX_E_Duty_PC-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : UPPCL</p> <form:textarea
																									class="form-control" path="isfElecdutyComment"
																									name="isfElecdutyComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if>
 --%>
																						<tr class="ISF_EX_Mandee_Fee-row">
																							<td>10</td>
																							<td>Exemption from Mandi Fee <br> <strong>Agriculture
																									Marketing & Agriculture Foreign
																									Trade/MandiParishad</strong></br></td>
																							<td><form:input path="ISF_EX_Mandee_Fee"
																									type="text"
																									class="ISF_EX_Mandee_Fee form-control"
																									maxlength="12" name="ISF_EX_Mandee_Fee"
																									id="ISF_EX_Mandee_Fee" readonly="true"></form:input></td>
																							<td><a
																								href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																								target="_blank">As per para 3.5.6</a> of The
																								Rules, There is provision for exemption from
																								Mandi Fee for all new food-processing
																								undertakings on purchase of raw material for 5
																								years.</td>
																							<td><form:textarea path="mandiFeeRemark"
																									rows="4" maxlength="1000" class="form-control" readonly="true"></form:textarea></td>
																						</tr>


																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfMandiComment}">
																						<tr class="ISF_EX_Mandee_Fee-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Agriculture Marketing & Agriculture
																									Foreign Trade/MandiParishad</p> <form:textarea
																									class="form-control" path="isfMandiComment"
																									name="isfMandiComment" disabled="" rows="3"
																									placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>
																						<tr class="ISF_Indus_Payroll_Asst-row">
																							<td>11</td>
																							<td>Industrial units providing employment to
																								differently abled workers will be provided
																								payroll assistance of Rs. 500 per month for each
																								such worker.</td>
																							<td><form:input
																									path="ISF_Indus_Payroll_Asst" type="text"
																									class="ISF_Indus_Payroll_Asst form-control"
																									maxlength="12" name="ISF_Amt_Stamp_Duty_Reim"
																									id="Add_ISF_Amt_Stamp_Duty_Reim"
																									readonly="true"></form:input></td>
																							<td>No such provision in The Rules.</td>
																							<td><form:textarea path="diffAbleWorkRemark"
																									maxlength="1000" rows="4" class="form-control" readonly="true"></form:textarea></td>
																						</tr>

																						<%-- <c:if
																						test="${not empty incentiveDeatilsData.isfdifferabilComment}">
																						<tr class="ISF_Indus_Payroll_Asst-row">
																							<td colspan="5"><p class="text-info">Comment
																									From : Labour/Industrial Development</p> <form:textarea
																									class="form-control"
																									path="isfdifferabilComment"
																									name="isfdifferabilComment" disabled=""
																									rows="3" placeholder="Comments"></form:textarea></td>
																						</tr>
																					</c:if> --%>

																						<tr>
																							<td colspan="2" align="Center"><strong>Total</strong></td>
																							<td><input type="text" class="form-control"
																								name="ttlIncAmt" value="${total}"
																								disabled="disabled"></td>
																							<td colspan="4"></td>
																						</tr>

																					</tbody>
																				</table>
																			</div>

																		</div>
																	</div>
																</form:form></td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">Turnover
																	of Base Production(For Items Approved)</label></td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<!-- <th>S. No.</th> -->
																				<th>Period/Financial Year</th>
																				<th>Turnover of sales of items manufactured</th>
																				<th>Turnover of Base Production</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="finYr1" type="text"
																						class="form-control is-numeric" maxlength="12" readonly="true"/>
																					<small class="words text-info"></small></td>
																				<td><form:input path="turnoverOfSales1"
																						type="text" class="form-control" maxlength="500" readonly="true"/>
																					<small class="words text-info"></small></td>
																				<td><form:input path="turnoverProduction1"
																						type="text" class="form-control is-numeric"
																						maxlength="12" readonly="true"/> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="finYr2" type="text"
																						class="form-control is-numeric" maxlength="12" readonly="true"/>
																					<small class="words text-info"></small></td>
																				<td><form:input path="turnoverOfSales2"
																						type="text" class="form-control is-numeric" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="turnoverProduction2" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="finYr3" type="text" readonly="true"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td><form:input path="turnoverOfSales3" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="turnoverProduction3" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="finYr4" type="text" readonly="true"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td><form:input path="turnoverOfSales4"
																						type="text" class="form-control is-numeric" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="turnoverProduction4" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3"><form:textarea
																	path="turnOverObserve" id="turnOverObserve" readonly="true"
																	placeholder="Observations Comment By PICUP"
																	name="turnOverObserve" class="form-control border-info"
																	rows="2"></form:textarea></td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">SGST-
																	Amount of Reimbursement</label></td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<!-- <th>S. No.</th> -->
																				<th>Duration/Period/ Financial Year</th>
																				<!-- <th>Current TurnOver</th>
																				<th>Turnover of Base Production</th> -->
																				<th>Amount of Net SGST Deposited by the Co.</th>
																				<!-- <th>The incremental turn over <small>(i.e.
																						Turn over –Turn over of the Base Production)</small></th>
																				<th>The incremental Net SGST <small>(i.e.
																						SGST on Turn over SGST on Turn over equal to the
																						turn over ot the year of Base Production)</small> of
																					current year
																				</th> -->
																				<th>Amount of Net SGST Eligible for
																					Reimbursement. <small>(60% of Deposited
																						Incremental SGST)</small>
																				</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input
																						path="durationFinYr1" type="text"
																						class="form-control is-numeric" maxlength="12" readonly="true"/>
																					<small class="words text-info"></small></td>
																				<%-- <td width="14%;"><form:input
																						path="turnoverOfProduction1" type="text"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td width="14%;"><form:input
																						path="ttlAmtCommTax1" type="text"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td> --%>
																				<td ><form:input
																						path="amtOfNetSGST1" type="text" readonly="true"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<%-- <td width="14%;"><form:input
																						path="increTurnover1" type="text"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td width="14%;"><form:input
																						path="increNetSGST1" type="text"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td> --%>
																				<td><form:input path="amtOfNetSGSTReim1" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="durationFinYr2" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<%-- <td><form:input path="turnoverOfProduction2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="ttlAmtCommTax2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td> --%>
																				<td><form:input path="amtOfNetSGST2" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<%-- <td><form:input path="increTurnover2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="increNetSGST2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td> --%>
																				<td><form:input path="amtOfNetSGSTReim2" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="durationFinYr3" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<%-- <td><form:input path="turnoverOfProduction3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="ttlAmtCommTax3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td> --%>
																				<td><form:input path="amtOfNetSGST3" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<%-- <td><form:input path="increTurnover3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="increNetSGST3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td> --%>
																				<td><form:input path="amtOfNetSGSTReim3" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="durationFinYr4" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<%-- <td><form:input path="turnoverOfProduction4"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="ttlAmtCommTax4"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td> --%>
																				<td><form:input path="amtOfNetSGST4" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<%-- <td><form:input path="increTurnover4"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="increNetSGST4"
																						type="text" class="form-control  is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td> --%>
																				<td><form:input path="amtOfNetSGSTReim4" readonly="true"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr>
															<td colspan="3"><form:textarea
																	path="sgstAmtReimObserve" id="sgstAmtReimObserve" readonly="true"
																	placeholder="Observations Comment By PICUP"
																	name="sgstAmtReimObserve"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>



														<tr>
															<td colspan="3"><label class="table-heading">Particulars
																	of Claims for Sanction of Capital Interest Subsidy <small>(as
																		provided by company)</small>
															</label></td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th rowspan="2">Year for which subsidy Applied</th>
																				<th colspan="3">Payment made to FI/Bank during
																					the year</th>
																				<th rowspan="2" width="20%">Amount of Interest
																					Subsidy Applied</th>
																			</tr>
																			<tr>
																				<th>Total Interest on Loan</th>
																				<th>Principal</th>
																				<th>Interest on P&M</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td>Year I &nbsp;&nbsp;<input id="year1"
																					name="yearI" onkeyup="yrOfSubsidy1()"
																					placeholder="YYYY-YYYY" type="text"
																					value="${cisincentiveDeatilsForm.yearI}"
																					disabled="disabled"><span id="year1Msg"
																					class="text-danger"></span></td>
																				<td><input id="firstYTI" name="firstYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrst"
																					value="${cisincentiveDeatilsForm.firstYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="firstYP" name="firstYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipal"
																					value="${cisincentiveDeatilsForm.firstYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="firstYI" name="firstYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterest"
																					value="${cisincentiveDeatilsForm.firstYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="firstYAmtIntSubsidy"
																					name="firstYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidy"
																					value="${cisincentiveDeatilsForm.firstYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>
																			</tr>
																			<tr>
																				<td>Year II &nbsp;<input id="year2"
																					name="yearII" onkeyup="yrOfSubsidy2()"
																					placeholder="YYYY-YYYY" type="text"
																					value="${cisincentiveDeatilsForm.yearII}"
																					disabled="disabled"><span id="year2Msg"
																					class="text-danger"></span></td>
																				<td><input id="secondYTI" name="secondYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrst"
																					value="${cisincentiveDeatilsForm.secondYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="secondYP" name="secondYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipal"
																					value="${cisincentiveDeatilsForm.secondYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="secondYI" name="secondYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterest"
																					value="${cisincentiveDeatilsForm.secondYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="secondYAmtIntSubsidy"
																					name="secondYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidy"
																					value="${cisincentiveDeatilsForm.secondYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>

																			</tr>
																			<tr>
																				<td>Year III <input id="year3" name="yearIII"
																					onkeyup="yrOfSubsidy3()" placeholder="YYYY-YYYY"
																					type="text"
																					value="${cisincentiveDeatilsForm.yearIII}"
																					disabled="disabled"><span id="year3Msg"
																					class="text-danger"></span></td>
																				<td><input id="thirdYTI" name="thirdYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrst"
																					value="${cisincentiveDeatilsForm.thirdYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="thirdYP" name="thirdYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipal"
																					value="${cisincentiveDeatilsForm.thirdYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="thirdYI" name="thirdYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterest"
																					value="${cisincentiveDeatilsForm.thirdYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="thirdYAmtIntSubsidy"
																					name="thirdYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidy"
																					value="${cisincentiveDeatilsForm.thirdYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>
																			</tr>
																			<tr>
																				<td>Year IV&nbsp;<input id="year4"
																					name="yearIV" onkeyup="yrOfSubsidy4()"
																					placeholder="YYYY-YYYY" type="text"
																					value="${cisincentiveDeatilsForm.yearIV}"
																					disabled="disabled"><span id="year4Msg"
																					class="text-danger"></span></td>
																				<td><input id="fourthYTI" name="fourthYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrst"
																					value="${cisincentiveDeatilsForm.fourthYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fourthYP" name="fourthYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipal"
																					value="${cisincentiveDeatilsForm.fourthYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fourthYI" name="fourthYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterest"
																					value="${cisincentiveDeatilsForm.fourthYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fourthYAmtIntSubsidy"
																					name="fourthYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidy"
																					value="${cisincentiveDeatilsForm.fourthYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>

																			</tr>
																			<tr>
																				<td>Year V &nbsp;<input id="year5" name="yearV"
																					onkeyup="yrOfSubsidy5()" placeholder="YYYY-YYYY"
																					type="text"
																					value="${cisincentiveDeatilsForm.yearV}"
																					disabled="disabled"><span id="year5Msg"
																					class="text-danger"></span></td>
																				<td><input id="fifthYTI" name="fifthYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrst"
																					value="${cisincentiveDeatilsForm.fifthYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fifthYP" name="fifthYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipal"
																					value="${cisincentiveDeatilsForm.fifthYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fifthYI" name="fifthYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterest"
																					value="${cisincentiveDeatilsForm.fifthYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fifthYAmtIntSubsidy"
																					name="fifthYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidy"
																					value="${cisincentiveDeatilsForm.fifthYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>

																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><input type="text"
																					class="form-control totalofTI" readonly="true"
																					name="totalofTI" maxlength="12"></td>
																				<td><input type="text"
																					class="form-control totalofPrincipal"
																					readonly="true" name="totalofPrincipal"
																					maxlength="12"></td>
																				<td><input type="text"
																					class="form-control totalofInterest"
																					readonly="true" name="totalofIntrest"
																					maxlength="12"></td>
																				<td><input type="text"
																					class="form-control totalofAmtSubsidy"
																					readonly="true" name="totalofAmtSubsidy"
																					maxlength="12"></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3"><form:textarea path="cisTblObserve"
																	id="cisTblObserve"
																	placeholder="Observations Comment By PICUP"
																	name="cisTblObserve" class="form-control border-info"
																	rows="2" readonly="true"></form:textarea></td>
														</tr>
														<tr>
															<td colspan="3">
																<p>
																	<span class="text-danger">*</span> Calculation of
																	interest subsidy proportionate to the pro-rata loan on
																	plant and machinery, made by the company for 2015-16
																	and 2016-17 has been provided.
																</p>
															</td>
														</tr>

														<tr>
															<td colspan="3"><label class="table-heading">CIS
																	- Computation Methodology &amp; Amount</label> <a
																href="#Formula" data-toggle="modal" class="ml-3"><strong>(Formula)</strong></a>
															</td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th width="5%">Sl. No.</th>
																				<th width="30%">Particulars</th>
																				<!-- <th width="20%">Computation & Formulae</th> -->
																				<th>Amount</th>
																			</tr>
																		</thead>
																		<tbody>

																			<tr>
																				<td>1</td>
																				<td>Date of First Loan Sanction</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doFirstLoanCIS" type="Date" class="form-control" readonly="true"/></td>
																			</tr>

																			<tr>
																				<td>2</td>
																				<td>Date of Last Loan Sanction</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doLastLoanCIS" type="Date" class="form-control" readonly="true"/></td>
																			</tr>

																			<tr>
																				<td>3</td>
																				<td>Date of First Disbursement</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doFirstDisCIS" type="Date" class="form-control" readonly="true"/></td>
																			</tr>


																			<tr>
																				<td>4</td>
																				<td>Date of Last Disbursement</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doLastDisCIS" type="Date" class="form-control" readonly="true"/></td>
																			</tr>

																			<tr>
																				<td>5</td>
																				<td>Date of Payment</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doPaymentCIS"  type="Date"  class="form-control"
																					 readonly="true"/></td>
																			</tr>

																			<tr>
																				<td>6</td>
																				<td>Total Cost of Project</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="cisCostOfProjectAmt"
																						type="text" class="form-control"
																						value="${newtotal}" readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>7</td>
																				<td>Cost of Plant & Machinery</td>
																				<!-- <td>B</td> -->
																				<td><form:input path="cisPlantMachAmt"
																						type="text" class="form-control" value="${newpnm}"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>8</td>
																				<td>Term Loan for Entire Project</td>
																				<!-- <td>C</td> -->
																				<td><form:input path="cisEntireProjectAmt"
																						readonly="true" type="text" class="form-control"
																						value="${cisincentiveDeatilsForm.total}" /></td>
																			</tr>
																			<tr>
																				<td>9</td>
																				<td>Term Loan for Plant & Machinery</td>
																				<!-- <td>(B/A)*C</td> -->
																				<td><form:input path="cisTermPlantMachAmt"
																						type="text" class="form-control"
																						value="${loanforPnM}" readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>10</td>
																				<td>Applicable Rate of Interest (ROI) on Loan</td>
																				<!-- <td>D</td> -->
																				<td><form:input path="cisApplROIAmt"
																						type="text" class="form-control"
																						value="${cisincentiveDeatilsForm.roi}"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>11</td>
																				<td>Amount of Interest Paid</td>
																				<!-- <td>E</td> -->
																				<td><form:input path="cisIntPaidAmt"
																						type="text" class="form-control"
																						value="${cisincentiveDeatilsForm.totalint}"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>12</td>
																				<td>Proportionate Interest at Applicable Rate
																					of Interest for P&M</td>
																				<!-- <td>(B/A)*E</td> -->
																				<td><form:input path="cisRoiPMAmt" type="text"
																						class="form-control" value="${propintforPnM}"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>13</td>
																				<td>Proportionate amount of interest for P&M at
																					@ ${additional} p.a.</td>
																				<!-- <td>(B/A)*E*(5/D)</td> -->
																				<td><form:input path="cisIntPM5Amt" type="text"
																						class="form-control" value="${prop5}"
																						readonly="true" /></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3"><form:textarea path="cisObserve"
																	class="form-control border-info" readonly="true"
																	placeholder="Observations By PICUP" rows="3"></form:textarea>
															</td>
														</tr>
														<%-- <form:form modelAttribute="disEligibleAmtCIS" name="DisEligibleAmtCISForm" method="post"> --%>
														<tr>
															<td colspan="3"><label class="table-heading">Eligible
																	Amount of CIS</label></td>
														</tr>
														<tr>
															<%-- <td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<!-- <th>S. No.</th> -->
																			<th>Financial Year</th>
																			<th>Total Loan Amount</th>
																			<th>Date of Disbursement</th>
																			<!-- <th>Interest Period From</th> -->
																			<th>Actual (Applicable) Rate of Interest on Loan
																				(ROI)</th>
																			<th>Actual Amount of Interest Paid</th>
																			<th>Date of Payment</th>
																			<th>Proportionate interest for P&M at Applicable
																				Rate of Interest</th>
																			<th>Proportionate interest for P&M at @
																				${additional} p.a. (CIS)</th>

																			<th>Eligible CIS (lower value of actual, applied
																				and CIS% subject to ceiling) <a
																				href="javascript:void(0);" class="remove-row"
																				data-toggle="tooltip" title=""
																				data-original-title="(Capital Interest Subsidy @ 5% p.a. or actual interest paid whichever is
																					 less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for procurement of 
																					 plant & machinery, subject to an annual ceiling of Rs. 50 lacs. However, additional Capital 
																					 Interest Subsidy @ 2.5% upto maximum of 7.5% shall be applicable in case of industrial undertakings having 75% equity owned by Divyang/SC/CT/Females Promoters.)"><i
																					class="fa fa-info-circle text-info"></i></a>
																			</th>

																		</thead>
																		<tbody>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td width="9%;">${cisincentiveDeatilsForm.yearI}</td>
																				<td><input type="text" class="form-control"
																					id="total_loan_amount"
																					value="${cisincentiveDeatilsForm.total}" name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					id="total_interest" name=""
																					value="${cisincentiveDeatilsForm.roi}"></td>
																				<!-- <td><input type="text" class="form-control"  name=""</td> -->
																				<td><input type="text" class="form-control"
																					id="total_loan_amount" name=""
																					value="${cisincentiveDeatilsForm.firstYP}"></td>
																				<td><input type="text" class="form-control"
																					id="total_loan_amount" name=""></td>
																				<td><input type="text" class="form-control"
																					id="total_loan_amount" name=""
																					value="${fproposnate}"></td>
																				<td><input type="text" class="form-control"
																					id="total_loan_amount" name="" value="${propfirst}"></td>
																				<td><input type="text" class="form-control"
																					id="total_loan_amount" name="" value="${ceiling}"></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td>${cisincentiveDeatilsForm.yearII}</td>
																				<td><input type="text" class="form-control"
																					name="" value="${cisincentiveDeatilsForm.total}"></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name="" value="${cisincentiveDeatilsForm.roi}"></td>
																				<!-- <td><input type="text" class="form-control"  name=""</td> -->
																				<td><input type="text" class="form-control"
																					name="" value="${cisincentiveDeatilsForm.secondYP}"></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name="" value="${fproposnate2}"></td>
																				<td><input type="text" class="form-control"
																					name="" value="${propfirst2}"></td>
																				<td><input type="text" class="form-control"
																					name="" value="${ceiling2}"></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td>${cisincentiveDeatilsForm.yearIII}</td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<!-- <td><input type="text" class="form-control"  name=""</td> -->
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td>${cisincentiveDeatilsForm.yearIV}</td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<!-- <td><input type="text" class="form-control"  name=""</td> -->
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td>${cisincentiveDeatilsForm.yearV}</td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<!-- <td><input type="text" class="form-control"  name=""</td> -->
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td> --%>

															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th>Financial Year</th>
																				<!-- <th>Interest on P&M</th> -->
																				<th>${bankcertdoc}</th>
																				<th>Proportionate interest for P&M at @ ${additional}
																					p.a. (CIS)</th>
																					<th>Eligible CIS (lower value of actual, applied and CIS% subject to ceiling) <a href="javascript:void(0);" class="remove-row" data-toggle="tooltip" title="" data-original-title="(Capital Interest Subsidy @ 5% p.a. or actual interest paid whichever is
                                            less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for procurement of
                                            plant &amp; machinery, subject to an annual ceiling of Rs. 50 lacs. However, additional Capital
                                            Interest Subsidy @ 2.5% upto maximum of 7.5% shall be applicable in case of industrial undertakings having 75% equity owned by Divyang/SC/CT/Females Promoters.)"><i class="fa fa-info-circle text-info"></i></a></th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td><form:input path="fYI" type="text"
																						name="fYI" readonly="true"
																						value="${cisincentiveDeatilsForm.yearI}"
																						class="form-control" /></td>
																				<%-- <td><form:input path="intPMI" type="text" value="${cisincentiveDeatilsForm.firstYI}"
																						name="intPMI" class="form-control" /></td> --%>
																				<%-- <td><form:input path="dateofDISI" type="text"
																						name="dateofDISI" class="form-control" /></td>
																				<td><form:input path="actAmtIPI" type="text"
																						name="actAmtIPI" class="form-control" /></td>
																				<td><form:input path="dateofPI" type="text"
																						name="dateofPI" class="form-control" /></td> --%>
																				<td><form:input path="propIntRoiI" readonly="true" type="text"
																						name="propIntRoiI"  value="${cisincentiveDeatilsForm.firstYI}" class="form-control" /></td>
																				<td><form:input path="propIntPAI" type="text" 
																						name="propIntPAI" class="form-control" value="${finterPnMateligi}" readonly="ture"/></td>
																				<td><form:input path="actAmtIPI" type="text"
																						name="actAmtIPI" class="form-control"  value="${ceiling}" readonly="ture" /></td>
																			</tr>
																			<tr>
																				<td><form:input path="fYII" type="text" readonly="true"
																						value="${cisincentiveDeatilsForm.yearII}"
																						name="fYII" class="form-control" /></td>
																				<%-- <td><form:input path="intPMII" type="text"
																						name="intPMII" class="form-control" /></td> --%>
																				<%-- <td><form:input path="dateofDISII" type="text"
																						name="dateofDISII" class="form-control" /></td>
																				<td><form:input path="actAmtIPII" type="text"
																						name="actAmtIPII" class="form-control" /></td>
																				<td><form:input path="dateofPII" type="text"
																						name="dateofPII" class="form-control" /></td> --%>
																				<td><form:input path="propIntRoiII" type="text" readonly="true"
																						name="propIntRoiII" class="form-control"  value="${cisincentiveDeatilsForm.secondYI}" /></td>
																				<td><form:input path="propIntPAII" type="text"
																						name="propIntPAII" class="form-control" readonly="true"/></td>
																				<td><form:input path="actAmtIPII" type="text"
																						name="actAmtIPII" class="form-control" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="fYIII" type="text"
																						name="fYIII" readonly="true"
																						value="${cisincentiveDeatilsForm.yearIII}"
																						class="form-control" /></td>
																				<%-- <td><form:input path="intPMIII" type="text"
																						name="intPMIII" class="form-control" /></td> --%>
																				<%-- <td><form:input path="dateofDISIII" type="text"
																						name="dateofDISIII" class="form-control" /></td>
																				<td><form:input path="actAmtIPIII" type="text"
																						name="actAmtIPIII" class="form-control" /></td>
																				<td><form:input path="dateofPIII" type="text"
																						name="dateofPIII" class="form-control" /></td> --%>
																				<td><form:input path="propIntRoiIII" readonly="true"
																						type="text" name="propIntRoiIII"
																						class="form-control"  value="${cisincentiveDeatilsForm.thirdYI}"/></td>
																				<td><form:input path="propIntPAIII" type="text"
																						name="propIntPAIII" class="form-control" readonly="true"/></td>
																			 <td><form:input path="actAmtIPIII" type="text"
																						name="actAmtIPIII" class="form-control" readonly="true"/></td> 
																			</tr>
																			<tr>
																				<td><form:input path="fYIV" type="text"
																						name="fYIV" readonly="true"
																						value="${cisincentiveDeatilsForm.yearIV}"
																						class="form-control" /></td>
																				<%-- <td><form:input path="intPMIV" type="text"
																						name="intPMIV" class="form-control" /></td> --%>
																				<%-- <td><form:input path="dateofDISIV" type="text"
																						name="dateofDISIV" class="form-control" /></td>
																				<td><form:input path="actAmtIPIV" type="text"
																						name="actAmtIPIV" class="form-control" /></td>
																				<td><form:input path="dateofPIV" type="text"
																						name="dateofPIV" class="form-control" /></td> --%>
																				<td><form:input path="propIntRoiIV" type="text"
																						name="propIntRoiIV" class="form-control" readonly="true" value="${cisincentiveDeatilsForm.fourthYI}"/></td>
																				<td><form:input path="propIntPAIV" type="text"
																						name="propIntPAIV" class="form-control" readonly="true"/></td>
																				<td><form:input path="actAmtIPIV" type="text"
																						name="actAmtIPIV" class="form-control" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="fYV" type="text"
																						name="fYV" readonly="true"
																						value="${cisincentiveDeatilsForm.yearV}"
																						class="form-control" /></td>
																				<%-- <td><form:input path="intPMV" type="text"
																						name="intPMV" class="form-control" /></td> --%>
																				<%-- <td><form:input path="dateofDISV" type="text"
																						name="dateofDISV" class="form-control" /></td>
																				<td><form:input path="actAmtIPV" type="text"
																						name="actAmtIPV" class="form-control" /></td>
																				<td><form:input path="dateofPV" type="text"
																						name="dateofPV" class="form-control" /></td> --%>
																				<td><form:input path="propIntRoiV" type="text" readonly="true"
																						name="propIntRoiV" class="form-control" value="${cisincentiveDeatilsForm.fifthYI}" /></td>
																				<td><form:input path="propIntPAV" type="text" readonly="true"
																						name="propIntPAV" class="form-control" /></td>
																				<td><form:input path="actAmtIPV" type="text" readonly="true"
																						name="actAmtIPV" class="form-control" /></td> 
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3"><form:textarea readonly="true"
																	path="ElgAmtcisObserv" id="ElgAmtcisObserv"
																	placeholder="Observations Comment By PICUP"
																	name="ElgAmtcisObserv" class="form-control border-info"
																	rows="2"></form:textarea></td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">Particulars
																	of Claims for Sanction of Infrastructure Interest
																	Subsidy <small>(as provided by company)</small>
															</label></td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th rowspan="2">Year for which subsidy Applied</th>
																				<th colspan="3">Payment made to FI/Bank during
																					the year</th>
																				<th rowspan="2" width="20%">Amount of Interest
																					Subsidy Applied</th>
																			</tr>
																			<tr>
																				<th>Total Interest on Loan</th>
																				<th>Principal</th>
																				<th>Interest on Infrastructure</th>
																			</tr>
																		</thead>
																		<tbody>
																		
																			<tr>
																				<td>Year I &nbsp;&nbsp;<input id="year1"
																					name="yearI" onkeyup="yrOfSubsidy1()"
																					placeholder="YYYY-YYYY" type="text"
																					value="${iisincentiveDeatilsForm.yearI}"
																					disabled="disabled"><span id="year1Msg"
																					class="text-danger"></span></td>
																				<td><input id="firstYTI" name="firstYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrst"
																					value="${iisincentiveDeatilsForm.firstYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="firstYP" name="firstYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipal"
																					value="${iisincentiveDeatilsForm.firstYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="firstYI" name="firstYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterest"
																					value="${iisincentiveDeatilsForm.firstYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="firstYAmtIntSubsidy"
																					name="firstYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidy"
																					value="${iisincentiveDeatilsForm.firstYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>
																			</tr>
																			<tr>
																				<td>Year II &nbsp;<input id="year2"
																					name="yearII" onkeyup="yrOfSubsidy2()"
																					placeholder="YYYY-YYYY" type="text"
																					value="${iisincentiveDeatilsForm.yearII}"
																					disabled="disabled"><span id="year2Msg"
																					class="text-danger"></span></td>
																				<td><input id="secondYTI" name="secondYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrst"
																					value="${iisincentiveDeatilsForm.secondYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="secondYP" name="secondYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipal"
																					value="${iisincentiveDeatilsForm.secondYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="secondYI" name="secondYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterest"
																					value="${iisincentiveDeatilsForm.secondYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="secondYAmtIntSubsidy"
																					name="secondYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidy"
																					value="${iisincentiveDeatilsForm.secondYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>

																			</tr>
																			<tr>
																				<td>Year III <input id="year3" name="yearIII"
																					onkeyup="yrOfSubsidy3()" placeholder="YYYY-YYYY"
																					type="text"
																					value="${iisincentiveDeatilsForm.yearIII}"
																					disabled="disabled"><span id="year3Msg"
																					class="text-danger"></span></td>
																				<td><input id="thirdYTI" name="thirdYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrst"
																					value="${iisincentiveDeatilsForm.thirdYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="thirdYP" name="thirdYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipal"
																					value="${iisincentiveDeatilsForm.thirdYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="thirdYI" name="thirdYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterest"
																					value="${iisincentiveDeatilsForm.thirdYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="thirdYAmtIntSubsidy"
																					name="thirdYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidy"
																					value="${iisincentiveDeatilsForm.thirdYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>
																			</tr>
																			<tr>
																				<td>Year IV&nbsp;<input id="year4"
																					name="yearIV" onkeyup="yrOfSubsidy4()"
																					placeholder="YYYY-YYYY" type="text"
																					value="${iisincentiveDeatilsForm.yearIV}"
																					disabled="disabled"><span id="year4Msg"
																					class="text-danger"></span></td>
																				<td><input id="fourthYTI" name="fourthYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrst"
																					value="${iisincentiveDeatilsForm.fourthYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fourthYP" name="fourthYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipal"
																					value="${iisincentiveDeatilsForm.fourthYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fourthYI" name="fourthYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterest"
																					value="${iisincentiveDeatilsForm.fourthYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fourthYAmtIntSubsidy"
																					name="fourthYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidy"
																					value="${iisincentiveDeatilsForm.fourthYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>

																			</tr>
																			<tr>
																				<td>Year V &nbsp;<input id="year5" name="yearV"
																					onkeyup="yrOfSubsidy5()" placeholder="YYYY-YYYY"
																					type="text"
																					value="${iisincentiveDeatilsForm.yearV}"
																					disabled="disabled"><span id="year5Msg"
																					class="text-danger"></span></td>
																				<td><input id="fifthYTI" name="fifthYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrst"
																					value="${iisincentiveDeatilsForm.fifthYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fifthYP" name="fifthYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipal"
																					value="${iisincentiveDeatilsForm.fifthYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fifthYI" name="fifthYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterest"
																					value="${iisincentiveDeatilsForm.fifthYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fifthYAmtIntSubsidy"
																					name="fifthYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidy"
																					value="${iisincentiveDeatilsForm.fifthYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>

																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><input type="text"
																					class="form-control totalofTI" readonly="true"
																					name="totalofTI" maxlength="12"></td>
																				<td><input type="text"
																					class="form-control totalofPrincipal"
																					readonly="true" name="totalofPrincipal"
																					maxlength="12"></td>
																				<td><input type="text"
																					class="form-control totalofInterest"
																					readonly="true" name="totalofIntrest"
																					maxlength="12"></td>
																				<td><input type="text"
																					class="form-control totalofAmtSubsidy"
																					readonly="true" name="totalofAmtSubsidy"
																					maxlength="12"></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3"><form:textarea readonly="true"
																	path="clamSactniisObserv" id="clamSactniisObserv"
																	placeholder="Observations Comment By PICUP"
																	name="clamSactniisObserv"
																	class="form-control border-info" rows="2"></form:textarea></td>
														</tr>
														<tr>
															<td colspan="3">
																<p>
																	<span class="text-danger">*</span> Calculation of
																	interest subsidy proportionate to the pro-rata loan on
																	plant and machinery, made by the company for 2015-16
																	and 2016-17 has been provided.
																</p>
															</td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">IIS-
																	Computation Methodology &amp; Amount</label> <a href="#Formula"
																data-toggle="modal" class="ml-3"><strong>(Formula)</strong></a>
															</td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th width="5%">Sl. No.</th>
																				<th width="30%">Particulars</th>
																				<!-- <th width="20%">Computation & Formulae</th> -->
																				<th>Amount</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td>1</td>
																				<td>Date of First Loan Sanction</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doFirstLoanIIS" type="Date" class="form-control" readonly="true"/></td>
																			</tr>

																			<tr>
																				<td>2</td>
																				<td>Date of Last Loan Sanction</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doLastLoanIIS" type="Date" class="form-control" readonly="true"/></td>
																			</tr>

																			<tr>
																				<td>3</td>
																				<td>Date of First Disbursement</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doFirstDisIIS" type="Date" class="form-control" readonly="true"/></td>
																			</tr>


																			<tr>
																				<td>4</td>
																				<td>Date of Last Disbursement</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doLastDisIIS" type="Date" class="form-control" readonly="true"/></td>
																			</tr>

																			<tr>
																				<td>5</td>
																				<td>Date of Payment</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doPaymentIIS" type="Date" class="form-control" readonly="true"/></td>
																			</tr>

																			<tr>
																				<td>6</td>
																				<td>Total Cost of Project</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="cisCostOfProjectAmt"
																						type="text" class="form-control"
																						value="${newtotal}" readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>7</td>
																				<td>Cost of Infrastructure</td>
																				<!-- <td>B</td> -->
																				<td><form:input path="cisPlantMachAmt"
																						type="text" class="form-control"
																						value="${newinfra}" readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>8</td>
																				<td>Term Loan for Entire Project</td>
																				<!-- <td>C</td> -->
																				<td><form:input path="cisEntireProjectAmt"
																						readonly="true" type="text" class="form-control"
																						value="${iisincentiveDeatilsForm.total}" /></td>
																			</tr>
																			<tr>
																				<td>9</td>
																				<td>Term Loan for Infrastructure</td>
																				<!-- <td>(B/A)*C</td> -->
																				<td><form:input path="cisTermPlantMachAmt"
																						type="text" class="form-control"
																						value="${loanforPnM}" readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>10</td>
																				<td>Applicable Rate of Interest (ROI) on Loan</td>
																				<!-- <td>D</td> -->
																				<td><form:input path="cisApplROIAmt"
																						type="text" class="form-control"
																						value="${iisincentiveDeatilsForm.roi}"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>11</td>
																				<td>Amount of Interest Paid</td>
																				<!-- <td>E</td> -->
																				<td><form:input path="cisIntPaidAmt"
																						type="text" class="form-control"
																						value="${iisincentiveDeatilsForm.totalint}"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>12</td>
																				<td>Proportionate Interest at Applicable Rate
																					of Interest for Infrastructure</td>
																				<!-- <td>(B/A)*E</td> -->
																				<td><form:input path="cisRoiPMAmt" type="text"
																						class="form-control" value="${propintforPnM}"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>13</td>
																				<td>Proportionate amount of interest for
																					Infrastructure at @ ${additional} p.a.</td>
																				<!-- <td>(B/A)*E*(5/D)</td> -->
																				<td><form:input path="cisIntPM5Amt" type="text"
																						class="form-control" value="${prop5}"
																						readonly="true" /></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3"><form:textarea path="iisCMAmtObserv"
																	id="iisCMAmtObserv" placeholder="Observations By PICUP"
																	name="iisCMAmtObserv" class="form-control border-info"
																	rows="2"></form:textarea></td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">Eligible
																	Amount of IIS</label></td>
														</tr>
														<tr>
															<%-- <td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<!-- <tr><th>S. No.</th> -->
																			<th>Financial Year</th>
																			<th>Total Loan Amount</th>
																			<th>Date of Disbursement</th>
																			<!--  <th>Interest Period</th> -->
																			<th>Actual (Applicable) Rate of Interest on Loan
																				(ROI)</th>
																			<th>Actual Amount of Interest Paid</th>
																			<th>Date of Payment</th>
																			<th>Proportionate interest for Infrastructure at
																				Applicable Rate of Interest</th>
																			<th>Proportionate interest for Infrastructure at
																				@ ${additional} p.a. (IIS)</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td width="9%;">${iisincentiveDeatilsForm.yearI}</td>
																				<td><input type="text" class="form-control"
																					value="${cisincentiveDeatilsForm.total}" name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name="" value="${iisincentiveDeatilsForm.roi}"></td>
																				<!-- <td><input type="text" class="form-control"  name=""</td> -->
																				<td><input type="text" class="form-control"
																					name="" value="${iisincentiveDeatilsForm.firstYP}"></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td>${iisincentiveDeatilsForm.yearII}</td>
																				<td><input type="text" class="form-control"
																					name="" value="${cisincentiveDeatilsForm.total}"></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name="" value="${iisincentiveDeatilsForm.roi}"></td>
																				<!-- <td><input type="text" class="form-control"  name=""</td> -->
																				<td><input type="text" class="form-control"
																					name="" value="${iisincentiveDeatilsForm.secondYP}"></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td>${iisincentiveDeatilsForm.yearIII}</td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<!-- <td><input type="text" class="form-control"  name=""</td> -->
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td>${iisincentiveDeatilsForm.yearIV}</td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<!-- <td><input type="text" class="form-control"  name=""</td> -->
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td>${iisincentiveDeatilsForm.yearV}</td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<!-- <td><input type="text" class="form-control"  name=""</td> -->
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td> --%>
														<tr>
															<td colspan="3">
																<table class="table table-bordered">
																	<thead>
																		<tr>
																			<th>Financial Year</th>
																			<!-- <th>Total Loan Amount</th> -->

																			<th>Proportionate interest for Infrastructure at
																				Applicable Rate of Interest</th>
																			<th>Proportionate interest for Infrastructure at
																				@ ${additional} p.a. (IIS)</th>
																			<th>Eligible IIS (lower value of actual, applied
																				and IIS% subject to ceiling) <a
																				href="javascript:void(0);" class="remove-row"
																				data-toggle="tooltip" title=""
																				data-original-title="(Capital Interest Subsidy @ 5% p.a. or actual interest paid whichever is
                                            less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for procurement of
                                            plant &amp; machinery, subject to an annual ceiling of Rs. 50 lacs. However, additional Capital
                                            Interest Subsidy @ 2.5% upto maximum of 7.5% shall be applicable in case of industrial undertakings having 75% equity owned by Divyang/SC/CT/Females Promoters.)"><i
																					class="fa fa-info-circle text-info"></i></a>
																			</th>
																		</tr>
																	</thead>
																	<tbody>
																		<tr>
																			<td><form:input path="iisFinYr1" type="text"
																					name="iisFinYr1" class="form-control" readonly="true"/></td>
																			<%-- <td><form:input path="iisTtlLoanAmt1"
																					type="text" name="iisTtlLoanAmt1"
																					class="form-control" /></td> --%>
																			<%-- <td><form:input path="iisDateOfDisb1"
																					type="text" name="iisDateOfDisb1"
																					class="form-control" /></td>
																			<td><form:input path="iisActAmtIP1" type="text"
																					name="iisActAmtIP1" class="form-control" /></td>
																			<td><form:input path="dateOfPay1" type="text"
																					name="dateOfPay1" class="form-control" /></td> --%>
																			<td><form:input path="propIntInfra1" type="text"
																					name="propIntInfra1" class="form-control" readonly="true"/></td>
																			<td><form:input path="propIntInfraPA1"
																					type="text" name="propIntInfraPA1"
																					class="form-control" readonly="true"/></td>
																			<td><form:input path="eligibleIIS1" type="text"
																					name="eligibleIIS1" class="form-control" readonly="true"/></td>
																		</tr>
																		<tr>
																			<td><form:input path="iisFinYr2" type="text"
																					name="iisFinYr2" class="form-control" readonly="true"/></td>
																			<%-- <td><form:input path="iisTtlLoanAmt2"
																					type="text" name="iisTtlLoanAmt2"
																					class="form-control" /></td> --%>
																			<%-- <td><form:input path="iisDateOfDisb2"
																					type="text" name="iisDateOfDisb2"
																					class="form-control" /></td>
																			<td><form:input path="iisActAmtIP2" type="text"
																					name="iisActAmtIP2" class="form-control" /></td>
																			<td><form:input path="dateOfPay2" type="text"
																					name="dateOfPay2" class="form-control" /></td> --%>
																			<td><form:input path="propIntInfra2" type="text"
																					name="propIntInfra2" class="form-control" readonly="true"/></td>
																			<td><form:input path="propIntInfraPA2"
																					type="text" name="propIntInfraPA2"
																					class="form-control" readonly="true"/></td>
																			<td><form:input path="eligibleIIS2" type="text"
																					name="eligibleIIS2" class="form-control" readonly="true"/></td>
																		</tr>
																		<tr>
																			<td><form:input path="iisFinYr3" type="text"
																					name="iisFinYr3" class="form-control" readonly="true"/></td>
																			<%-- <td><form:input path="iisTtlLoanAmt3"
																					type="text" name="iisTtlLoanAmt3"
																					class="form-control" /></td> --%>
																			<%-- <td><form:input path="iisDateOfDisb3"
																					type="text" name="iisDateOfDisb3"
																					class="form-control" /></td>
																			<td><form:input path="iisActAmtIP3" type="text"
																					name="iisActAmtIP3" class="form-control" /></td>
																			<td><form:input path="dateOfPay3" type="text"
																					name="dateOfPay3" class="form-control" /></td> --%>
																			<td><form:input path="propIntInfra3" type="text"
																					name="propIntInfra3" class="form-control" readonly="true"/></td>
																			<td><form:input path="propIntInfraPA3"
																					type="text" name="propIntInfraPA3"
																					class="form-control" readonly="true"/></td>
																			<td><form:input path="eligibleIIS3" type="text"
																					name="eligibleIIS3" class="form-control" readonly="true"/></td>
																		</tr>
																		<tr>
																			<td><form:input path="iisFinYr4" type="text"
																					name="iisFinYr4" class="form-control" readonly="true"/></td>
																			<%-- <td><form:input path="iisTtlLoanAmt4"
																					type="text" name="iisTtlLoanAmt4"
																					class="form-control" /></td> --%>
																			<%-- <td><form:input path="iisDateOfDisb4"
																					type="text" name="iisDateOfDisb4"
																					class="form-control" /></td>
																			<td><form:input path="iisActAmtIP4" type="text"
																					name="iisActAmtIP4" class="form-control" /></td>
																			<td><form:input path="dateOfPay4" type="text"
																					name="dateOfPay4" class="form-control" /></td> --%>
																			<td><form:input path="propIntInfra4" type="text"
																					name="propIntInfra4" class="form-control" readonly="true"/></td>
																			<td><form:input path="propIntInfraPA4"
																					type="text" name="propIntInfraPA4"
																					class="form-control" readonly="true"/></td>
																			<td><form:input path="eligibleIIS4" type="text"
																					name="eligibleIIS4" class="form-control" readonly="true"/></td>
																		</tr>
																		<tr>
																			<td><form:input path="iisFinYr5" type="text"
																					name="iisFinYr5" class="form-control" readonly="true"/></td>
																			<%-- <td><form:input path="iisTtlLoanAmt5"
																					type="text" name="iisTtlLoanAmt5"
																					class="form-control" /></td> --%>
																			<%-- <td><form:input path="iisDateOfDisb5"
																					type="text" name="iisDateOfDisb5"
																					class="form-control" /></td>
																			<td><form:input path="iisActAmtIP5" type="text"
																					name="iisActAmtIP5" class="form-control" /></td>
																			<td><form:input path="dateOfPay5" type="text"
																					name="dateOfPay5" class="form-control" /></td> --%>
																			<td><form:input path="propIntInfra5" type="text"
																					name="propIntInfra5" class="form-control" readonly="true"/></td>
																			<td><form:input path="propIntInfraPA5"
																					type="text" name="propIntInfraPA5"
																					class="form-control" readonly="true"/></td>
																			<td><form:input path="eligibleIIS5" type="text"
																					name="eligibleIIS5" class="form-control" readonly="true"/></td>
																		</tr>
																		
																	</tbody>
																</table>
															</td>
														</tr>
														<tr>
															<td colspan="3"><form:textarea
																	path="eligAmtIisObserv" readonly="true"
																	class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2"></form:textarea>
															</td>
														</tr>


														<!-- New Changes for EPS-Stamp Duty ExemptionMandi other incentive -->

														<tr>
															<td colspan="3"><label class="table-heading">EPF
																	Computation and Eligibility</label></td>
														</tr>

														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th>Financial Year</th>
																				<th>Employer's Claims for EPF</th>
																				<th>Reimbursement Eligibility</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td><form:input path="epfComputFinYr1" type="text" class="form-control" name="epfComputFinYr1" readonly="true"/></td>
																				<td><form:input path="employerContributionEPF1" type="text" class="form-control" name="employerContributionEPF1" readonly="true"/></td>
																				<td><form:input path="reimEligibility1" type="text" class="form-control" name="reimEligibility1" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="epfComputFinYr2" type="text" class="form-control" name="epfComputFinYr2" readonly="true"/></td>
																				<td><form:input path="employerContributionEPF2" type="text" class="form-control" name="employerContributionEPF2" readonly="true"/></td>
																				<td><form:input path="reimEligibility2" type="text" class="form-control" name="reimEligibility2" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="epfComputFinYr3" type="text" class="form-control" name="epfComputFinYr3" readonly="true"/></td>
																				<td><form:input path="employerContributionEPF3" type="text" class="form-control" name="employerContributionEPF3" readonly="true"/></td>
																				<td><form:input path="reimEligibility3" type="text" class="form-control" name="reimEligibility3" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><form:input path="ttlEmployerContributionEPF" type="text" class="form-control" name="ttlEmployerContributionEPF" readonly="true"/></td>
																				<td><form:input path="ttlReimEligibility" type="text" class="form-control" name="ttlReimEligibility" readonly="true"/></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr>
															<td colspan="3"><form:textarea path="epfComputEligObserv" class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>

														<tr>
															<td colspan="3"><label class="table-heading">Stamp
																	Duty Exemption or Reimbursement Computation</label></td>
														</tr>

														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th>Financial Year</th>
																				<th>Admissible Stamp Duty</th>
																				<th>Claimed Stamp Duty Reimbursement Amount</th>
																				<th>Stamp Duty Reimbursement Eligibility</th>
																				<th>Stamp Duty Reimbursement Availed</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td><form:input path="computationFinYr" type="text" class="form-control" name="computationFinYr" readonly="true"/></td>
																				<td><form:input path="admissibleStampDuty" type="text" class="form-control" name="admissibleStampDuty" readonly="true"/></td>
																				<td><form:input path="claimStampDutyReimAmt" type="text" class="form-control" name="claimStampDutyReimAmt" readonly="true"/></td>
																				<td><form:input path="stampDutyReimElig" type="text" class="form-control" name="stampDutyReimElig" readonly="true"/></td>
																				<td><form:input path="stampDutyReimAvail" type="text" class="form-control" name="stampDutyReimAvail" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><form:input path="ttlAdmissibleStampDuty" type="text" class="form-control" name="ttlAdmissibleStampDuty" readonly="true"/></td>
																				<td><form:input path="ttlClaimStampDutyReimAmt" type="text" class="form-control" name="ttlClaimStampDutyReimAmt" readonly="true"/></td>
																				<td><form:input path="ttlStampDutyReimElig" type="text" class="form-control" name="ttlStampDutyReimElig" readonly="true"/></td>
																				<td><form:input path="ttlStampDutyReimAvail" type="text" class="form-control" name="ttlStampDutyReimAvail" readonly="true"/></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr>
															<td colspan="3"><form:textarea path="stampDutyExeObserv" class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>

														<tr>
															<td colspan="3"><label class="table-heading">Electricity
																	Duty Exemption</label></td>
														</tr>

														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th>Financial Year</th>
																				<th>Availed ED Exemption - Units</th>
																				<th>Availed ED Exemption - Captive Power Plant</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td><form:input path="electricityDutyExeFinYr1" type="text" class="form-control" name="electricityDutyExeFinYr1" readonly="true"/></td>
																				<td><form:input path="availedEDExeUnits1" type="text" class="form-control" name="availedEDExeUnits1" readonly="true"/></td>
																				<td><form:input path="availedEDExeCPP1" type="text" class="form-control" name="availedEDExeCPP1" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="electricityDutyExeFinYr2" type="text" class="form-control" name="electricityDutyExeFinYr2" readonly="true"/></td>
																				<td><form:input path="availedEDExeUnits2" type="text" class="form-control" name="availedEDExeUnits2" readonly="true"/></td>
																				<td><form:input path="availedEDExeCPP2" type="text" class="form-control" name="availedEDExeCPP2" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="electricityDutyExeFinYr3" type="text" class="form-control" name="electricityDutyExeFinYr3" readonly="true"/></td>
																				<td><form:input path="availedEDExeUnits3" type="text" class="form-control" name="availedEDExeUnits3" readonly="true"/></td>
																				<td><form:input path="availedEDExeCPP3" type="text" class="form-control" name="availedEDExeCPP3" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="electricityDutyExeFinYr4" type="text" class="form-control" name="electricityDutyExeFinYr4" readonly="true"/></td>
																				<td><form:input path="availedEDExeUnits4" type="text" class="form-control" name="availedEDExeUnits4" readonly="true"/></td>
																				<td><form:input path="availedEDExeCPP4" type="text" class="form-control" name="availedEDExeCPP4" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="electricityDutyExeFinYr5" type="text" class="form-control" name="electricityDutyExeFinYr5" readonly="true"/></td>
																				<td><form:input path="availedEDExeUnits5" type="text" class="form-control" name="availedEDExeUnits5" readonly="true"/></td>
																				<td><form:input path="availedEDExeCPP5" type="text" class="form-control" name="availedEDExeCPP5" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="electricityDutyExeFinYr6" type="text" class="form-control" name="electricityDutyExeFinYr6" readonly="true"/></td>
																				<td><form:input path="availedEDExeUnits6" type="text" class="form-control" name="availedEDExeUnits6" readonly="true"/></td>
																				<td><form:input path="availedEDExeCPP6" type="text" class="form-control" name="availedEDExeCPP6" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="electricityDutyExeFinYr7" type="text" class="form-control" name="electricityDutyExeFinYr7" readonly="true"/></td>
																				<td><form:input path="availedEDExeUnits7" type="text" class="form-control" name="availedEDExeUnits7" readonly="true"/></td>
																				<td><form:input path="availedEDExeCPP7" type="text" class="form-control" name="availedEDExeCPP7" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="electricityDutyExeFinYr8" type="text" class="form-control" name="electricityDutyExeFinYr8" readonly="true"/></td>
																				<td><form:input path="availedEDExeUnits8" type="text" class="form-control" name="availedEDExeUnits8" readonly="true"/></td>
																				<td><form:input path="availedEDExeCPP8" type="text" class="form-control" name="availedEDExeCPP8" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="electricityDutyExeFinYr9" type="text" class="form-control" name="electricityDutyExeFinYr9" readonly="true"/></td>
																				<td><form:input path="availedEDExeUnits9" type="text" class="form-control" name="availedEDExeUnits9" readonly="true"/></td>
																				<td><form:input path="availedEDExeCPP9" type="text" class="form-control" name="availedEDExeCPP9" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="electricityDutyExeFinYr10" type="text" class="form-control" name="electricityDutyExeFinYr10" readonly="true"/></td>
																				<td><form:input path="availedEDExeUnits10" type="text" class="form-control" name="availedEDExeUnits10" readonly="true"/></td>
																				<td><form:input path="availedEDExeCPP10" type="text" class="form-control" name="availedEDExeCPP10" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><form:input path="ttlAvailedEDExeUnits" type="text" class="form-control" name="ttlAvailedEDExeUnits" readonly="true"/></td>
																				<td><form:input path="ttlAvailedEDExeCPP" type="text" class="form-control" name="ttlAvailedEDExeCPP" readonly="true"/></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr>
															<td colspan="3"><form:textarea path="electricityDutyExeObserv" class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>

														<tr>
															<td colspan="3"><label class="table-heading">Mandi
																	Fee Exemption</label></td>
														</tr>

														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th>Financial Year</th>
																				<th>Availed Mandi Fee Exemption</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td><form:input path="mandiFeeExeFinYr1" type="text" class="form-control" name="mandiFeeExeFinYr1" readonly="true"/></td>
																				<td><form:input path="AvailMandiFeeExe1" type="text" class="form-control" name="AvailMandiFeeExe1" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="mandiFeeExeFinYr2" type="text" class="form-control" name="mandiFeeExeFinYr2" readonly="true"/></td>
																				<td><form:input path="AvailMandiFeeExe2" type="text" class="form-control" name="AvailMandiFeeExe2" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="mandiFeeExeFinYr3" type="text" class="form-control" name="mandiFeeExeFinYr3" readonly="true"/></td>
																				<td><form:input path="AvailMandiFeeExe3" type="text" class="form-control" name="AvailMandiFeeExe3" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="mandiFeeExeFinYr4" type="text" class="form-control" name="mandiFeeExeFinYr4" readonly="true"/></td>
																				<td><form:input path="AvailMandiFeeExe4" type="text" class="form-control" name="AvailMandiFeeExe4" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><form:input path="mandiFeeExeFinYr5" type="text" class="form-control" name="mandiFeeExeFinYr5" readonly="true"/></td>
																				<td><form:input path="AvailMandiFeeExe5" type="text" class="form-control" name="AvailMandiFeeExe5" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><form:input path="ttlAvailMandiFeeExe" type="text" class="form-control" name="ttlAvailMandiFeeExe" readonly="true"/></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr>
															<td colspan="3"><form:textarea path="mandiFeeExeObserv" class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>

														<tr>
															<td colspan="3"><label class="table-heading">Reimbursement
																	of Disallowed Input Tax <a href="javascript:void(0);"
																	class="remove-row" data-toggle="tooltip" title=""
																	data-original-title="Reimbursement of Disallowed Input Tax Credit on plant, building materials, and other capital goods."><i
																		class="fa fa-info-circle text-info"></i></a>
															</label></td>
														</tr>

														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th>Financial Year</th>
																				<th>Availed Amount</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td><form:input path="reimDisallowFinYr" type="text" class="form-control"
																					name="reimDisallowFinYr" readonly="true"/></td>
																				<td><form:input path="reimDisallowAvailAmt" type="text" class="form-control"
																					name="reimDisallowAvailAmt" readonly="true"/></td>
																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><form:input path="ttlReimDisallowAvailAmt" type="text" class="form-control"
																					name="ttlReimDisallowAvailAmt" readonly="true"/></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>


														<tr>
															<td colspan="3"><form:textarea path="reimDisallowObserv" class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>


														<tr>
															<td colspan="3"><label class="table-heading">Admissible
																	Benefits (<a href="#">As per table-3.0</a> of Rules
																	IIEPP-2017)
															</label></td>
														</tr>
														<tr>
															<td colspan="3"><form:textarea
																	path="admissibleBenefits" id="admissibleBenefits"
																	placeholder="Observations Comment By PICUP"
																	name="admissibleBenefits"
																	class="form-control border-info" rows="2" readonly="true"></form:textarea>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>

								<hr class="mt-2">
								<h4 class="h2">Eligibility of Benefits</h4>
								<div class="isf-form mt-4">
									<h4 class="card-title mb-4 mt-4">Status of compliance of
										eligibility criteria for disbursement</h4>
									<hr class="mt-2">
									<div class="row">
										<div class="col-md-12">
											<div class="table-responsive">
												<table class="table table-bordered">
													<thead>
														<tr>
															<th width="5%">Sl.No</th>
															<th width="35%">Eligibility Criteria</th>
															<th>Compliance</th>
															<th>Observations</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>1</td>
															<td><strong>IEM Status</strong></td>
															<td width="33%">
																<table class="table">
																	<tbody>
																		<tr>
																			<td><input type="text" class="form-control"
																				value="Yes" readonly="readonly" name=""></td>
																			<td><form:input path="iemStatusCompl"
																					type="text" class="form-control"
																					value="${evaluateInvestMentDetails.invIemNumber}"
																					readonly="true" /></td>
																		</tr>
																	</tbody>
																</table>

															</td>
															<td><form:textarea path="iemStatusObserve"
																	class="form-control" readonly="true"
																	placeholder="Observations By PICUP" rows="2" /></td>
														</tr>
														<tr>
															<td>2</td>
															<td><strong>New/Expansion/Diversification</strong></td>
															<td><form:input path="newExpDivCompl" type="text"
																	class="form-control" readonly="true"
																	value="${natureOfProject}" /></td>
															<td><form:textarea path="newExpDivObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>3</td>
															<td><strong>Proposed Total Investment in
																	and Investment in Plant & Machinery</strong></td>
															<td><textarea class="form-control" rows="2"
																	readonly="true">Plant & Machinery Cost ${evaluateInvestMentDetails.invPlantAndMachCost},&#13;&#10;Cost of Project ${evaluateInvestMentDetails.invTotalProjCost}  </textarea>
																<%-- <form:input path="proTtlInvCompl" type="text"
																	class="form-control" name="" readonly="true" /> --%></td>
															<td><form:textarea path="proTtlInvObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>4</td>
															<td><strong>Opted cut-off Date</strong></td>
															<td><form:input path="CutOffDateCompl" type="text"
																	class="form-control" name=""
																	value="${evaluateInvestMentDetails.invCommenceDate}"
																	readonly="true" /></td>
															<td><form:textarea path="cutOffDateObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>5</td>
															<td><strong>Date of Commencement of
																	Commercial Production</strong></td>
															<td><form:input path="dateCommProCompl" type="text"
																	class="form-control" name=""
																	value="${evaluateInvestMentDetails.propCommProdDate}"
																	readonly="true" /></td>
															<td><form:textarea path="dateCommProObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>6</td>
															<td><strong>Investment Period</strong></td>
															<td><form:input path="invPeriodCompl" type="text"
																	class="form-control" name="" value="Yes"
																	readonly="true" /></td>
															<td><form:textarea path="invPeriodObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>7</td>
															<td><strong>Project in Phases</strong></td>
															<td><form:input path="projPhasesCompl" type="text"
																	class="form-control" name="" value="Yes"
																	readonly="true" /></td>
															<td><form:textarea path="projPhasesObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>8</td>
															<td><strong>Direct/Indirect/Skilled/Unskilled
																	Workers</strong></td>
															<td><form:input path="dirIndirWorkersCompl"
																	type="text" class="form-control"
																	value="${grossTotalMaleandFemaleEmployment}" name=""
																	readonly="true" /></td>
															<td><form:textarea path="dirIndirWorkersObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>

														<c:if test="${natureOfProject=='Expansion'}">
															<tr>
																<td>9</td>
																<td colspan="2"><label class="table-heading">Detailed
																		Project Report prepared by External
																		Consultant/Chartered Accountants <a
																		href="./downloadExistProjDoc?fileName=${enclDetProRepFileName}"><small>(View
																				File)</small></a>
																</label></td>
																<td><form:textarea path="dprObserve"
																		class="form-control"
																		placeholder="Observations By PICUP" rows="2" readonly="true"/></td>
															</tr>
														</c:if>


														<c:if test="${natureOfProject=='NewProject'}">
															<tr>
																<td>9</td>
																<td colspan="2"><label class="table-heading">Detailed
																		Project Report prepared by External
																		Consultant/Chartered Accountants <a
																		href="./downloadNewProjDoc?fileName=${enclDetProRepFileName}"><small>(View
																				File)</small></a>
																</label></td>
																<td><form:textarea path="dprObserve"
																		class="form-control"
																		placeholder="Observations By PICUP" rows="2" readonly="true"/></td>
															</tr>
														</c:if>

														<c:if test="${natureOfProject !='NewProject'}">
															<tr>
																<td>10</td>
																<td><strong>List of Assets</strong> Whether List of
																	Assets (in Expansion/Diversification Cases only)
																	certified by C.E. Submitted. Whether Chartered Engineer
																	as certified List of Assets provided as on last date of
																	preceding financial year before opted cutoff date. <a
																	href="./downloadExistProjDoc?fileName=${charatEngFileName}">View
																		File</a></td>
																<td><form:input path="listOfAssetsCompl"
																		type="text" class="form-control" name="" readonly="true"/></td>
																<td><form:textarea path="listOfAssetsObserve"
																		class="form-control"
																		placeholder="Observations By PICUP" rows="2" readonly="true"/></td>
															</tr>
														</c:if>

														<tr>
															<td>11</td>
															<td><strong>Undertaking</strong>Whether notarized
																undertaking as per format placed at Annexure I-A on
																Stamp Paper of Rs. 10/-submitted. <a
																href="./downloadBusinessEntityDoc?fileName=${annexureiaformat}">View
																	File</a></td>
															<td><form:input path="undertakingCompl" type="text"
																	class="form-control" name="" value="Yes"
																	readonly="true" /></td>
															<td><form:textarea path="undertakingObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>12</td>
															<td><strong>Name of Authorized Signatory</strong>Whether
																Board Resolution copy provided.</td>
															<td><form:input path="authSignCompl" type="text"
																	class="form-control" value="${bussAuthSigName}" name=""
																	readonly="true" /></td>
															<td><form:textarea path="authSignObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>13</td>
															<td><strong>Application Format</strong>Whether
																application is as per prescribed format & signed by
																Authorized Signatory with Name,Designation and Official
																Seal.</td>
															<td><form:input path="applFormatCompl" type="text"
																	class="form-control" name="" value="Yes"
																	readonly="true" /></td>
															<td><form:textarea path="applFormatObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>14</td>
															<td>Whether all supporting document including DPR of
																application authenticated by a Director/Partner/Officer
																duly authorized by the competent authority of the
																applicant on its behalf.</td>
															<td><form:input path="suppDocDirCompl" type="text"
																	class="form-control" name="" value="Yes"
																	readonly="true" /></td>
															<td><form:textarea path="suppDocDirObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>15</td>
															<td>Whether copy of Bank Appraisal submitted</td>
															<td><form:input path="bankApprCompl" type="text"
																	class="form-control" name="bankApprCompl" /></td>
															<td><form:textarea path="bankApprObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td colspan="5"><form:textarea
																	path="eligOfBenefitsNote" class="form-control border-info"
																	placeholder="Note (if any)" rows="4" readonly="true"/></td>

														</tr>
													</tbody>
												</table>
											</div>
											<div class="table-responsive mt-2">
												<table class="table table-bordered">
													<tbody>
														<tr>
															<td><strong>Disbursement already released
																	so far is nil</strong></td>
														</tr>
														<tr>
															<td><form:textarea path="eligOfBenefitsComments"
																	class="form-control border-info" placeholder="Note (if any)"
																	rows="4" readonly="true"/></td>
														</tr>
													</tbody>
												</table>
											</div>
											<div class="table-responsive mt-2">
												<table class="table table-bordered">
													<tbody>
														<tr>
															<td><label class="table-heading">Quantum of
																	Benefits</label></td>
														</tr>
														<tr>
															<td>
																<table class="table table-bordered">
																	<thead>
																		<tr>
																			<th>Sl.No.</th>
																			<th>Particulars</th>
																			<th>Amount (Rs. in crores)</th>
																		</tr>
																	</thead>
																	<tbody>
																		<tr>
																			<td>A.</td>
																			<td>Total eligibility of benefits</td>
																			<td><form:input path="ttlEligAmt" type="text"
																					class="form-control is-numeric" readonly="true"/> <small
																				class="words text-info"></small></td>
																		</tr>
																		<tr>
																			<td>B.</td>
																			<td>Amount proposed for disbursement</td>
																			<td><form:input path="propDisbAmt" type="text"
																					class="form-control is-numeric" readonly="true"/> <small
																				class="words text-info"></small></td>
																		</tr>
																		<tr>
																			<td>C.</td>
																			<td>Balance amount of eligibility of benefits
																				after proposed disbursal</td>
																			<td><form:input path="eligBenefitsAmt"
																					type="text" class="form-control is-numeric" readonly="true"/> <small
																				class="words text-info"></small></td>
																		</tr>
																		<tr>
																			<td>D.</td>
																			<td>Balance Period</td>
																			<td><form:input path="balancePeriodAmt"
																					type="text" class="form-control is-numeric" readonly="true"/> <small
																				class="words text-info"></small></td>
																		</tr>
																	</tbody>
																</table>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
											<div class="table-responsive mt-2">
												<table class="table table-bordered">
													<tbody>
														<tr>
															<td><strong>Disbursement effected till date</strong></td>
															<td><form:input path="disbEffDate" type="Date"
																	class="form-control" readonly="true"/></td>
														</tr>
														<tr>
															<td colspan="2"><form:textarea
																	path="disbEffComments" class="form-control border-info"
																	placeholder="Comments (if any)" rows="4" readonly="true"/></td>
														</tr>
													</tbody>
												</table>
											</div>
											<div class="table-responsive mt-2">
												<table class="table table-bordered">
													<tbody>
														<tr>
															<td colspan="2"><label class="table-heading">Proposal
																	for Consideration</label></td>
														</tr>
														<tr>
															<td>Total Eligibility of Incentives Under</td>
															<td><form:input path="ttlEligIncentives" type="text"
																	class="form-control is-numeric" readonly="true"/><small
																class="words text-info"></small></td>
														</tr>
														<tr>
															<td>Date of Admissibility of Incentives</td>
															<td><form:input path="dateAdmissibilityInc"
																	type="Date" class="form-control" readonly="true"/></td>
														</tr>
														<tr>
															<td colspan="2"><label class="table-heading">Compliance
																	of Conditions</label></td>
														</tr>
														<tr>
															<td colspan="2"><form:textarea
																	path="complCondiComments" class="form-control border-info"
																	placeholder="Comments (if any)" rows="4" readonly="true"/></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>

</div>
</form:form>

	 

	<div class="container">
		<!-- The Modal -->
		<div class="modal fade" id="evaluateConfirm">
			<div class="modal-dialog">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Confirmation</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<p>Are you sure you want to submit the application</p>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="evaluation-view.html" class="btn common-btn btn-sm mt-0">Yes</a>
						<button type="button" class="btn btn-outline-danger mt-0"
							data-dismiss="modal">No</button>
					</div>
				</div>
			</div>
		</div>
		<!-- The Modal -->
		<div class="container">

			<div class="modal fade" id="RejectApplication">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">

						<!-- Modal Header -->
						<div class="modal-header">
							<h4 class="modal-title">Reject Application</h4>
							<button type="button" class="close" data-dismiss="modal">&times;</button>
						</div>


						<!-- Modal body -->
						<form:form action="saveRejectApplicationDIS" class="isf-form"
							method="POST">
							<div class="modal-body">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label>Reason</label>
											<textarea class="form-control" rows="5" name="rejectValue"></textarea>
										</div>
									</div>
								</div>
							</div>

							<!-- Modal footer -->
							<div class="modal-footer">

								<button type="submit" onclick="return RejectApplication()"
									class="common-btn" value="Submit">Submit</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" id="RaiseQuery">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Raise Query</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<form:form action="saveRaiseQueryDIS" class="isf-form"
						method="POST" modelAttribute="raiseQuery"
						enctype="multipart/form-data">
						<!-- Modal body -->
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<form:label path="rqClarifySought">Clarification Sought</form:label>
										<form:textarea path="rqClarifySought" class="form-control"></form:textarea>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<form:label path="rqMissdocdtl">Details of Missing Documents <small>(If
												any)</small>
										</form:label>
										<form:textarea path="rqMissdocdtl" class="form-control"></form:textarea>
									</div>
								</div>

								<div class="col-md-12">
									<div class="form-group">
										<label>Upload Related Document</label>
										<div class="custom-file">
											<input type="file" name="rqFilename"
												class="custom-file-input" id="UploadDocumentForQuery">
											<label class="custom-file-label" for="UploadDocumentForQuery"
												id="UploadDocumentForQuery1">Choose file</label>
										</div>
									</div>
								</div>
							</div>
						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<form:button type="submit" onclick="return RaiseQuery()"
								class="common-btn mt-3">Submit Query</form:button>
						</div>

					</form:form>

				</div>
			</div>
		</div>

		<div class="modal fade" id="EvaluationAuditTrail">
			<div class="modal-dialog modal-xl">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Evaluation Audit Trail</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-12">
								<div class="table-responsive">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>S.No</th>
												<th>Username</th>
												<th>Field Name</th>
												<th>Old Details</th>
												<th>New Details</th>
												<th>Modified Date & Time</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>1.</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
											</tr>
											<tr>
												<td>2.</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" id="Formula">
			<div class="modal-dialog modal-xl">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Formula</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-12">
								<div class="table-responsive">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th width="5%">Sl. No.</th>
												<th width="30%">Particulars & Formula</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>1</td>
												<td>Cost of Project = <span class="text-danger">A</span></td>
											</tr>
											<tr>
												<td>2</td>
												<td>Cost of Plant & Machinery or Infrastructure = <span
													class="text-danger">B</span></td>
											</tr>
											<tr>
												<td>3</td>
												<td>Term Loan for Entire Project = <span
													class="text-danger">C</span></td>
											</tr>
											<tr>
												<td>4</td>
												<td>Term Loan for Plant & Machinery or Infrastructure =
													<span class="text-danger">(B/A)*C</span>
												</td>
											</tr>
											<tr>
												<td>5</td>
												<td>Applicable Rate of Interest (ROI) on Loan = <span
													class="text-danger">D</span></td>
											</tr>
											<tr>
												<td>6</td>
												<td>Amount of Interest Paid = <span class="text-danger">E</span></td>
											</tr>
											<tr>
												<td>7</td>
												<td>Proportionate Interest at Applicable Rate of
													Interest for P&M or Infrastructure = <span
													class="text-danger">(B/A)*E</span>
												</td>
											</tr>
											<tr>
												<td>8</td>
												<td>Proportionate amount of interest for P&M or
													Infrastructure at @ 5% p.a. = <span class="text-danger">(B/A)*E*(5/D)</span>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<script src="js/datepicker.js"></script>
	<script src="js/custom.js"></script>

	<script type="text/javascript">
		function amtInvCutOffDateTtl() {
			var sum = 0;
			$(".amtInvCutOff").each(function() {
				sum += +$(this).val();
			});
			$(".amtInvCutOffTotal").val(sum);
		}

		$(document).ready(amtInvCutOffDateTtl);
		$(document).on("keyup", amtInvCutOffDateTtl);
		$(document).on("change", amtInvCutOffDateTtl);

		function amtInvDPRTotal() {
			var sum = 0;
			$(".dprAmt").each(function() {
				sum += +$(this).val();
			});
			$(".dprAmtTotal", this).val(sum);
		}

		$(document).ready(amtInvDPRTotal);
		$(document).on("keyup", amtInvDPRTotal);
		$(document).on("change", amtInvDPRTotal);

		function bankAppraisalTotal() {
			var sum = 0;
			$(".bankApprAmt").each(function() {
				sum += +$(this).val();
			});
			$(".bankApprAmtTotal", this).val(sum);
		}

		$(document).ready(bankAppraisalTotal);
		$(document).on("keyup", bankAppraisalTotal);
		$(document).on("change", bankAppraisalTotal);

		function certificateAuditTotal() {
			var sum = 0;
			$(".perCertiAuditor").each(function() {
				sum += +$(this).val();
			});
			$(".perCertiAuditorTotal", this).val(sum);
		}

		$(document).ready(certificateAuditTotal);
		$(document).on("keyup", certificateAuditTotal);
		$(document).on("change", certificateAuditTotal);

		function capitalInvCATotal() {
			var sum = 0;
			$(".capitalCA").each(function() {
				sum += +$(this).val();
			});
			$(".capitalCATotal", this).val(sum);
		}

		$(document).ready(capitalInvCATotal);
		$(document).on("keyup", capitalInvCATotal);
		$(document).on("change", capitalInvCATotal);

		function capitalInvValuerTotal() {
			var sum = 0;
			$(".capitalValuer").each(function() {
				sum += +$(this).val();
			});
			$(".capitalValuerTotal", this).val(sum);
		}

		$(document).ready(capitalInvValuerTotal);
		$(document).on("keyup", capitalInvValuerTotal);
		$(document).on("change", capitalInvValuerTotal);

		function statutoryAuditerTotal() {
			var sum = 0;
			$(".statutoryAudit").each(function() {
				sum += +$(this).val();
			});
			$(".statutoryAuditTotal", this).val(sum);
		}

		$(document).ready(statutoryAuditerTotal);
		$(document).on("keyup", statutoryAuditerTotal);
		$(document).on("change", statutoryAuditerTotal);

		function cutOffDateAmountTotal() {
			var sum = 0;
			$(".cutOffDateAmt").each(function() {
				sum += +$(this).val();
			});
			$(".cutOffDateAmtTotal", this).val(sum);
		}

		$(document).ready(cutOffDateAmountTotal);
		$(document).on("keyup", cutOffDateAmountTotal);
		$(document).on("change", cutOffDateAmountTotal);

		function perDPRAmountTotal() {
			var sum = 0;
			$(".perDPRAmt").each(function() {
				sum += +$(this).val();
			});
			$(".perDPRAmtTotal", this).val(sum);
		}

		$(document).ready(perDPRAmountTotal);
		$(document).on("keyup", perDPRAmountTotal);
		$(document).on("change", perDPRAmountTotal);

		function perBankApprAmountTotal() {
			var sum = 0;
			$(".perBankAppr").each(function() {
				sum += +$(this).val();
			});
			$(".perBankApprTotal", this).val(sum);
		}

		$(document).ready(perBankApprAmountTotal);
		$(document).on("keyup", perBankApprAmountTotal);
		$(document).on("change", perBankApprAmountTotal);

		function companyDatedAmountTotal() {
			var sum = 0;
			$(".companyDated").each(function() {
				sum += +$(this).val();
			});
			$(".companyDatedTotal", this).val(sum);
		}

		$(document).ready(companyDatedAmountTotal);
		$(document).on("keyup", companyDatedAmountTotal);
		$(document).on("change", companyDatedAmountTotal);

		function empaneledCATotalAmtTotal() {
			var sum = 0;
			$(".empaneledCA").each(function() {
				sum += +$(this).val();
			});
			$(".empaneledCATotal", this).val(sum);
		}

		$(document).ready(empaneledCATotalAmtTotal);
		$(document).on("keyup", empaneledCATotalAmtTotal);
		$(document).on("change", empaneledCATotalAmtTotal);

		function empaneledCAAmtTotal() {
			var sum = 0;
			$(".empaneledValuer").each(function() {
				sum += +$(this).val();
			});
			$(".empaneledValuerTotal", this).val(sum);
		}

		$(document).ready(empaneledCAAmtTotal);
		$(document).on("keyup", empaneledCAAmtTotal);
		$(document).on("change", empaneledCAAmtTotal);

		function certifiAuditTotal() {
			var sum = 0;
			$(".certificateAudit").each(function() {
				sum += +$(this).val();
			});
			$(".certificateAuditTotal", this).val(sum);
		}

		$(document).ready(certifiAuditTotal);
		$(document).on("keyup", certifiAuditTotal);
		$(document).on("change", certifiAuditTotal);

		function subTtlOffTotal() {
			var sum = 0;
			$(".subTtlCutOff").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlCutOffTotal", this).val(sum);
		}

		$(document).ready(subTtlOffTotal);
		$(document).on("keyup", subTtlOffTotal);
		$(document).on("change", subTtlOffTotal);

		function subTtlOffTotal() {
			var sum = 0;
			$(".subTtlCutOff").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlCutOffTotal", this).val(sum);
		}

		$(document).ready(subTtlOffTotal);
		$(document).on("keyup", subTtlOffTotal);
		$(document).on("change", subTtlOffTotal);

		function subTtlDprTotal() {
			var sum = 0;
			$(".subTtlDPR").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlDPRTotal", this).val(sum);
		}

		$(document).ready(subTtlDprTotal);
		$(document).on("keyup", subTtlDprTotal);
		$(document).on("change", subTtlDprTotal);

		function subBankApprTotal() {
			var sum = 0;
			$(".subBankAppr").each(function() {
				sum += +$(this).val();
			});
			$(".subBankApprTotal", this).val(sum);
		}

		$(document).ready(subBankApprTotal);
		$(document).on("keyup", subBankApprTotal);
		$(document).on("change", subBankApprTotal);

		function subTtlAuditorTotal() {
			var sum = 0;
			$(".subTtlAuditor").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlAuditorTotal", this).val(sum);
		}

		$(document).ready(subTtlAuditorTotal);
		$(document).on("keyup", subTtlAuditorTotal);
		$(document).on("change", subTtlAuditorTotal);

		function subTtlCATotal() {
			var sum = 0;
			$(".subTtlCA").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlCATotal", this).val(sum);
		}

		$(document).ready(subTtlCATotal);
		$(document).on("keyup", subTtlCATotal);
		$(document).on("change", subTtlCATotal);

		function subTtlValuerTotal() {
			var sum = 0;
			$(".subTtlValuer").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlValuerTotal", this).val(sum);
		}

		$(document).ready(subTtlValuerTotal);
		$(document).on("keyup", subTtlValuerTotal);
		$(document).on("change", subTtlValuerTotal);

		function subTtlStatutoryTotal() {
			var sum = 0;
			$(".subTtlStatutory").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlStatutoryTotal", this).val(sum);
		}

		$(document).ready(subTtlStatutoryTotal);
		$(document).on("keyup", subTtlStatutoryTotal);
		$(document).on("change", subTtlStatutoryTotal);

		function FinancingDPRTotal() {
			var sum = 0;
			$(".FinancingDPR").each(function() {
				sum += +$(this).val();
			});
			$(".FinancingDPRTotal", this).val(sum);
		}

		$(document).ready(FinancingDPRTotal);
		$(document).on("keyup", FinancingDPRTotal);
		$(document).on("change", FinancingDPRTotal);

		function FinaBankApprTotal() {
			var sum = 0;
			$(".FinaBankAppr").each(function() {
				sum += +$(this).val();
			});
			$(".FinaBankApprTotal", this).val(sum);
		}

		$(document).ready(FinaBankApprTotal);
		$(document).on("keyup", FinaBankApprTotal);
		$(document).on("change", FinaBankApprTotal);

		function FinaPerCertiTotal() {
			var sum = 0;
			$(".FinaPerCerti").each(function() {
				sum += +$(this).val();
			});
			$(".FinaPerCertiTotal", this).val(sum);
		}

		$(document).ready(FinaPerCertiTotal);
		$(document).on("keyup", FinaPerCertiTotal);
		$(document).on("change", FinaPerCertiTotal);

		function FinancingCATotal() {
			var sum = 0;
			$(".FinancingCA").each(function() {
				sum += +$(this).val();
			});
			$(".FinancingCATotal", this).val(sum);
		}

		$(document).ready(FinancingCATotal);
		$(document).on("keyup", FinancingCATotal);
		$(document).on("change", FinancingCATotal);

		function FinaStaAuditTotal() {
			var sum = 0;
			$(".FinaStaAudit").each(function() {
				sum += +$(this).val();
			});
			$(".FinaStaAuditTotal", this).val(sum);
		}

		$(document).ready(FinaStaAuditTotal);
		$(document).on("keyup", FinaStaAuditTotal);
		$(document).on("change", FinaStaAuditTotal);
	</script>


	<script type="text/javascript">
		function AllYearTotalIntrest() {
			var sum = 0;
			$(".AllYearTotlIntrst").each(function() {
				sum += +$(this).val();
			});
			$(".totalofTI", this).val(sum);
		}

		$(document).ready(AllYearTotalIntrest);
		$(document).on("keyup", AllYearTotalIntrest);
		$(document).on("change", AllYearTotalIntrest);

		function AllYearTotalPrincipal() {
			var sum = 0;
			$(".AllYearTotlPrincipal").each(function() {
				sum += +$(this).val();
			});
			$(".totalofPrincipal", this).val(sum);
		}

		$(document).ready(AllYearTotalPrincipal);
		$(document).on("keyup", AllYearTotalPrincipal);
		$(document).on("change", AllYearTotalPrincipal);

		function AllYearTotalInterest() {
			var sum = 0;
			$(".AllYearTotlInterest").each(function() {
				sum += +$(this).val();
			});
			$(".totalofInterest", this).val(sum);
		}

		$(document).ready(AllYearTotalInterest);
		$(document).on("keyup", AllYearTotalInterest);
		$(document).on("change", AllYearTotalInterest);

		function AllYearTotalAmtSubsidy() {
			var sum = 0;
			$(".AllYearTotlAmtSubsidy").each(function() {
				sum += +$(this).val();
			});
			$(".totalofAmtSubsidy", this).val(sum);

		}

		$(document).ready(AllYearTotalAmtSubsidy);
		$(document).on("keyup", AllYearTotalAmtSubsidy);
		$(document).on("change", AllYearTotalAmtSubsidy);
	</script>

	<script>
		$(document).ready(function() {
			$("[data-toggle=offcanvas]").click(function() {
				$(".row-offcanvas").toggleClass("active");
			});
		});
		// For date-picker
		$(document).ready(function() {
			$('#datepicker1').datepicker({
				uiLibrary : 'bootstrap4'
			});
			$('#datepicker2').datepicker({
				uiLibrary : 'bootstrap4'
			});
		});
	</script>
	<script>
		//Get the button
		var mybutton = document.getElementById("GoToTopBtn");

		// When the user scrolls down 20px from the top of the document, show the button
		window.onscroll = function() {
			scrollFunction()
		};

		function scrollFunction() {
			if (document.body.scrollTop > 20
					|| document.documentElement.scrollTop > 20) {
				mybutton.style.display = "block";
			} else {
				mybutton.style.display = "none";
			}
		}

		// When the user clicks on the button, scroll to the top of the document
		function topFunction() {
			document.body.scrollTop = 0;
			document.documentElement.scrollTop = 0;
		}
	</script>
	<script type="text/javascript">
		function showYesOrNo2() {
			var ISF_Claim_Reim = '${incentiveDeatilsData.ISF_Claim_Reim}';
			if (ISF_Claim_Reim == '') {
				//alert("hi");
				$(".ISF_Claim_Reim-row").hide();
			}
			var ISF_Reim_SCST = '${incentiveDeatilsData.ISF_Reim_SCST}';
			//if( iSF_Reim_BPLW == '')
			if (ISF_Reim_SCST == '') {
				//alert(ISF_Reim_SCST);
				$(".ISF_Reim_SCST-row").hide();
			}

			var ISF_Reim_FW = '${incentiveDeatilsData.ISF_Reim_FW}';
			if (ISF_Reim_FW == '') {
				//alert("hi");
				$(".ISF_Reim_FW-row").hide();
			}

			var iSF_Reim_BPLW = '${incentiveDeatilsData.ISF_Reim_BPLW}';
			if (iSF_Reim_BPLW == '') {
				//alert("hi");
				$(".ISF_Reim_BPLW-row").hide();
			}

			var ISF_Stamp_Duty_EX = '${incentiveDeatilsData.ISF_Stamp_Duty_EX}';
			if (ISF_Stamp_Duty_EX == '') {
				//alert("hi");
				$(".ISF_Stamp_Duty_EX-row").hide();
			}

			var ISF_Amt_Stamp_Duty_Reim = '${incentiveDeatilsData.ISF_Amt_Stamp_Duty_Reim}';
			if (ISF_Amt_Stamp_Duty_Reim == '') {
				//alert("hi");
				$(".ISF_Amt_Stamp_Duty_Reim-row").hide();
			}

			var ISF_Additonal_Stamp_Duty_EX = '${incentiveDeatilsData.ISF_Additonal_Stamp_Duty_EX}';
			if (ISF_Additonal_Stamp_Duty_EX == '') {
				//alert("hi");
				$(".ISF_Additonal_Stamp_Duty_EX-row").hide();
			}

			var ISF_Epf_Reim_UW = '${incentiveDeatilsData.ISF_Epf_Reim_UW}';
			if (ISF_Epf_Reim_UW == '') {
				//alert("hi");
				$(".ISF_Epf_Reim_UW-row").hide();
			}

			var ISF_Add_Epf_Reim_SkUkW = '${incentiveDeatilsData.ISF_Add_Epf_Reim_SkUkW}';
			if (ISF_Add_Epf_Reim_SkUkW == '') {
				//alert("hi");
				$(".ISF_Add_Epf_Reim_SkUkW-row").hide();
			}

			var ISF_Add_Epf_Reim_DIVSCSTF = '${incentiveDeatilsData.ISF_Add_Epf_Reim_DIVSCSTF}';
			if (ISF_Add_Epf_Reim_DIVSCSTF == '') {
				//alert("hi");
				$(".ISF_Add_Epf_Reim_DIVSCSTF-row").hide();
			}

			var ISF_Cis = '${incentiveDeatilsData.ISF_Cis}';
			if (ISF_Cis == '') {
				//alert("hi");
				$(".ISF_Cis-row").hide();
			}

			var ISF_ACI_Subsidy_Indus = '${incentiveDeatilsData.ISF_ACI_Subsidy_Indus}';
			if (ISF_ACI_Subsidy_Indus == '') {
				//alert("hi");
				$(".ISF_ACI_Subsidy_Indus-row").hide();
			}

			var ISF_Infra_Int_Subsidy = '${incentiveDeatilsData.ISF_Infra_Int_Subsidy}';
			if (ISF_Infra_Int_Subsidy == '') {
				//alert("hi");
				$(".ISF_Infra_Int_Subsidy-row").hide();
			}

			var ISF_AII_Subsidy_DIVSCSTF = '${incentiveDeatilsData.ISF_AII_Subsidy_DIVSCSTF}';
			if (ISF_AII_Subsidy_DIVSCSTF == '') {
				//alert("hi");
				$(".ISF_AII_Subsidy_DIVSCSTF-row").hide();
			}

			var ISF_Loan_Subsidy = '${incentiveDeatilsData.ISF_Loan_Subsidy}';
			if (ISF_Loan_Subsidy == '') {
				//alert("hi");
				$(".ISF_Loan_Subsidy-row").hide();
			}

			var ISF_Tax_Credit_Reim = '${incentiveDeatilsData.ISF_Tax_Credit_Reim}';
			if (ISF_Tax_Credit_Reim == '') {
				//alert("hi");
				$(".ISF_Tax_Credit_Reim-row").hide();
			}

			var ISF_EX_E_Duty = '${incentiveDeatilsData.ISF_EX_E_Duty}';
			if (ISF_EX_E_Duty == '') {
				//alert("hi");
				$(".ISF_EX_E_Duty-row").hide();
			}

			var ISF_EX_E_Duty_PC = '${incentiveDeatilsData.ISF_EX_E_Duty_PC}';
			if (ISF_EX_E_Duty_PC == '') {
				//alert("hi");
				$(".ISF_EX_E_Duty_PC-row").hide();
			}

			var ISF_EX_Mandee_Fee = '${incentiveDeatilsData.ISF_EX_Mandee_Fee}';
			if (ISF_EX_Mandee_Fee == '') {
				//alert("hi");
				$(".ISF_EX_Mandee_Fee-row").hide();
			}

			var ISF_Indus_Payroll_Asst = '${incentiveDeatilsData.ISF_Indus_Payroll_Asst}';
			if (ISF_Indus_Payroll_Asst == '') {
				//alert("hi");
				$(".ISF_Indus_Payroll_Asst-row").hide();
			}
		}
		$(document).ready(showYesOrNo2);
	</script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							console.log("document loaded");
							if ("${disViewEvaluate.confProvbyCTDDocBase64}" == null
									|| "${disViewEvaluate.confProvbyCTDDocBase64}" == '') {

							} else {
								document
										.getElementById('chooseFileConfProvbyCTDDoc').innerHTML = '${disViewEvaluate.confProvbyCTDDoc}';
								document.getElementById("confProvbyCTDDoc").src = "data:image/png;base64,${disViewEvaluate.confProvbyCTDDocBase64}";
							}

							if ("${disViewEvaluate.confProvbyBankDocBase64}" == null
									|| "${disViewEvaluate.confProvbyBankDocBase64}" == '') {

							} else {
								document
										.getElementById('chooseFileConfProvbyBankDoc').innerHTML = '${disViewEvaluate.confProvbyBankDoc}';
								document.getElementById("confProvbyBankDoc").src = "data:image/png;base64,${disViewEvaluate.confProvbyBankDocBase64}";
							}

							if ("${disViewEvaluate.externalERDDocBase64}" == null
									|| "${disViewEvaluate.externalERDDocBase64}" == '') {

							} else {
								document
										.getElementById('choosefileExternalERDDoc').innerHTML = '${disViewEvaluate.externalERDDoc}';
								document.getElementById("externalERDDoc").src = "data:image/png;base64,${disViewEvaluate.externalERDDocBase64}";
							}
						});

		function DocumentconfProvbyCTDDoc(file) {
			var confProvbyCTDDoc = document.getElementById('confProvbyCTDDoc').value;
			var maxSize = '5000';

			if (confProvbyCTDDoc != null && confProvbyCTDDoc != '') {
				var ext = confProvbyCTDDoc.split('.').pop();
				if (ext == "pdf") {
					//document.getElementById('confProvbyCTDDocMsg').innerHTML = " ";
				} else {
					document.getElementById('confProvbyCTDDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
					document.getElementById('confProvbyCTDDoc').focus();
					return false;
				}
			}
			if (confProvbyCTDDoc != null && confProvbyCTDDoc != '') {
				var fileSize = document.getElementById("confProvbyCTDDoc").files[0];

				var fsize = (fileSize.size / (1024 * 1024)).toFixed(5);
				if (fsize > 5) {
					document.getElementById('confProvbyCTDDocMsg').innerHTML = "Your file size is: "
							+ fsize
							+ " MB,"
							+ " File size should not be more than 5 MB";
					document.getElementById('confProvbyCTDDoc').focus();
					return false;
				}

				else {
					document.getElementById('confProvbyCTDDocMsg').innerHTML = "";
				}

			}
			if ('${disViewEvaluate.confProvbyCTDDocBase64}' != '') {
				return true;
			}

		}
	</script>
</body>

</html>