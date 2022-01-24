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
<!-- Pankaj -->
<script type="text/javascript">
	window.onload = function() {
		if ('${enableIncludeAgenda.status}' == "Evaluation Completed") {
		$("#button2").removeClass("evaluate-btn");
		} else {
		  }	}

	function RejectApplication() {
		var r = confirm("Are you Sure Want to Reject the Application ?");
		if (r == true) {
			alert("Application Rejected Successfully");
		} else {
			return false
		}}
	
	function RaiseQuery() {
		var r = confirm("Are you Sure Want to Submit the Query ?");

		if (r == true) {
			alert("Query Raised Successfully");
		} else {
			return false
		}}

	function includeAgendaNote() {
		var r = confirm("Are you Sure Want to Include Application in Agenda Note?");
		if (r == true) {
			alert("Application Included In Prepare Agenda Note Successfully");
		} else {
			return false
		}}
	
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
		}}
	
</script>
</head>
<body>
	<button onclick="topFunction()" id="GoToTopBtn" title="Go to top">Top</button>
	<section class="inner-header">
		<!-- Navigation / Navbar / Menu -->
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="#"><img src="images/logo.png"
					class="logo" alt="Logo"></a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarTogglerDemo02"
					aria-controls="navbarTogglerDemo02" aria-expanded="false"
					aria-label="Toggle navigation">
					<i class="fa fa-user"></i>
				</button>
				<div class="flex-row d-flex left-menu-toggle">
					<button type="button" class="hidden-md-up navbar-toggler"
						data-toggle="offcanvas" title="Toggle responsive left sidebar">
						<span class="navbar-toggler-icon"></span>
					</button>
				</div>
				<div class="collapse navbar-collapse" id="navbarTogglerDemo02">
					<ul class="navbar-nav ml-auto mt-2 mt-lg-0">
						<li class="nav-item"><a class="nav-link" href="./userLogout"><i
								class="fa fa-power-off"></i> Logout</a></li>
						<li class="nav-item"><a class="nav-link active" href="#"><i
								class="fa fa-user"></i>${userName}</a></li>

						<li class="nav-item dropdown dropleft notification-dropdown">
							<a class="nav-link notification" data-toggle="dropdown" href="#"><i
								class="fa fa-bell"></i><span
								class="animate__animated animate__bounceIn">5</span></a>
							<div class="dropdown-menu">
								<a class="dropdown-item" href="notification.html">You have 3
									new recommendations please check.</a> <a class="dropdown-item"
									href="notification.html">You have 4 new notes please check.</a>
								<a class="dropdown-item" href="notification.html">You have 5
									new comments please check.</a> <a class="dropdown-item"
									href="notification.html">You have 3 new recommendations
									please check.</a> <a class="dropdown-item" href="notification.html">You
									have 4 new notes please check.</a>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<!-- End Navigation / Navbar / Menu -->
	</section>
	<section class="dashboard-wrapper">
		<div class="container-fluid" id="main">
			<div class="row row-offcanvas row-offcanvas-left">
				<div class="col-md-3 col-lg-2 sidebar-offcanvas pt-3" id="sidebar"
					role="navigation">
					<ul class="nav flex-column pl-1">
						<li class="nav-item"><a class="nav-link" href="./dashboard"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./viewAndEvaluate"><i class="fa fa-eye"></i> View and
								Evaluate Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluateSME"><i class="fa fa-eye"></i> View
								and Forward SME applications</a></li>
						<li class="nav-item"><a class="nav-link" href="./queryRaised"><i
								class="fa fa-question-circle"></i> Query</a></li>
						<li class="nav-item"><a class="nav-link" href="./agendaNode"><i
								class="fa fa-list"></i> Prepare Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./meetingschedule"><i class="fa fa-calendar"></i>
								Schedule meeting</a></li>
						<li class="nav-item"><a class="nav-link" href="./momgo"><i
								class="fa fa-calendar"></i> Minutes of Meeting and GO's</a></li>
						<li class="nav-item"><a class="nav-link" href="./generateLoc"><i
								class="fa fa-wpforms"></i> Generate LOC</a></li>
					</ul>
				</div>
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-5">
								<a
									href="./viewApplicationDetailsDis?applicantId=<%=session.getAttribute("appId")%>"
									class="common-default-btn mt-3">Back</a>
							</div>
							<div class="col-sm-7 text-right">
								<a href="javacript:void(0);" class="common-btn mt-3"><i
									class="fa fa-edit"></i> Edit</a>
							</div>
						</div>
						<form:form modelAttribute="disViewEvaluate" method="post"
							action="SaveEvaluateApplicationDis" class="mt-4"
							autocomplete="off" enctype="multipart/form-data">
							<form:hidden path="evaluateId" />
							<div class="row mt-5">
								<div class="col-sm-12">
									<div class="table-responsive">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>Application ID</th>
													<th>Company Name</th>
													<!-- <th>Type</th> -->
													<th>Product</th>
													<th>Region</th>
													<th>District</th>
													<th width="15%">Investment</th>
													<th>Category</th>
													<th width="10%">Date of LOC issued</th>
												</tr>
											</thead>
											<tbody id="evalTable">
												<c:forEach var="list" items="${evalObjList}"
													varStatus="counter">
													<tr>
														<td><c:out value="${list[0]}" /></td>
														<td><c:out value="${list[1]}" /></td>
														<%-- <td><c:out value="${natureOfProject}" /></td> --%>
														<td><c:out value="${products}" /></td>
														<td><c:out value="${list[2]}" /></td>
														<td><c:out value="${list[3]}" /></td>
														<td><c:out value="${list[4]}" /></td>
														<td><c:out value="${list[5]}" /></td>
														<td><fmt:formatDate value="${locDate}"
																pattern="dd-MM-yyyy" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								<div class="col-md-12 mt-4">
									<div class="form-group">
										<label>Add Promoters background & project Details</label>
										<form:textarea path="addPromotersDetails"
											placeholder="Add Comment" class="form-control border-info"
											rows="8" cols="148"></form:textarea>
									</div>
								</div>
							</div>
							<h4 class="h2">Table-1</h4>
							<div class="table-responsive">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Sl.No</th>
                                            <th>Deatils of Incentives</th>
                                            <th>Observations of PICUP</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td><div class="alert alert-secondary border-info">The company is proposing to set up a green-fieldunit in YEIDA, Gautam Budh Nagar and Ghaziabad District Region</div></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td><div class="alert alert-secondary border-info">The company is proposing to set up a green-fieldunit in YEIDA, Gautam Budh Nagar and Ghaziabad District Region</div></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td><div class="alert alert-secondary border-info">The company is proposing to set up a green-fieldunit in YEIDA, Gautam Budh Nagar and Ghaziabad District Region</div></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td><div class="alert alert-secondary border-info">The company is proposing to set up a green-fieldunit in YEIDA, Gautam Budh Nagar and Ghaziabad District Region</div></td>
                                        </tr>
                                    </tbody>
                                </table>
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
														<td><strong>IEM II</strong></td>
														<td width="33%">
															<table class="table">
																<tbody>
																	<tr>
																		<td><input type="text" class="form-control"
																			value="Yes" readonly="readonly" name=""></td>
																		<td><form:input path="iemStatusCompl" type="text"
																				class="form-control"
																				value="${evaluateInvestMentDetails.invIemNumber}"
																				readonly="true" /></td>
																	</tr>
																</tbody>
															</table>

														</td>
														<td><form:textarea path="iemStatusObserve"
																class="form-control" readonly="false"
																placeholder="Observations By PICUP" rows="2" /></td>
													</tr>
													<tr>
														<td>2</td>
														<td><strong>New/Expansion/Diversification</strong></td>
														<td><form:input path="newExpDivCompl" type="text"
																class="form-control" readonly="true"
																value="${natureOfProject}" /></td>
														<td><form:textarea path="newExpDivObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
													</tr>
													<tr>
														<td>3</td>
														<td><strong>Total Cost Of Project</strong></td>
														<td><textarea class="form-control" rows="2"
																readonly="true">Plant & Machinery Cost ${evaluateInvestMentDetails.invPlantAndMachCost},&#13;&#10;Cost of Project ${evaluateInvestMentDetails.invTotalProjCost}  </textarea>
															<%-- <form:input path="proTtlInvCompl" type="text"
																	class="form-control" name="" readonly="true" /> --%></td>
														<td><form:textarea path="proTtlInvObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
													</tr>
													<tr>
														<td>4</td>
														<td><strong>First Date Of Investment (Cut
																Off Date)</strong></td>
														<td><form:input path="CutOffDateCompl" type="text"
																class="form-control" id="invcutofdate" readonly="true" /></td>

														<td><form:textarea path="cutOffDateObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
													</tr>
													<tr>
														<td>5</td>
														<td><strong>Actual Date of Commencement of
																Commercial Production</strong></td>
														<td><form:input path="dateCommProCompl" type="text"
																class="form-control" name=""
																value="${evaluateInvestMentDetails.propCommProdDate}"
																readonly="true" /></td>
														<td><form:textarea path="dateCommProObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
													</tr>
													<tr>
														<td>6</td>
														<td><strong>Investment Period</strong></td>
														<td><form:input path="invPeriodCompl" type="text"
																class="form-control" id="invPeriodCompl" readonly="true" /></td>
														<td><form:textarea path="invPeriodObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
													</tr>
													<tr>
														<td>7</td>
														<td><strong>Project in Phases</strong></td>
														<td><form:input path="projPhasesCompl" type="text"
																class="form-control" name="" value="Yes" readonly="true" /></td>
														<td><form:textarea path="projPhasesObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
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
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
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
																	placeholder="Observations By PICUP" rows="2" /></td>
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
																	placeholder="Observations By PICUP" rows="2" /></td>
														</tr>
													</c:if>

													<c:if test="${natureOfProject !='NewProject'}">
														<tr>
															<td>10</td>
															<td><strong>List of Assets</strong> Whether List of
																Assets (in Expansion/Diversification Cases only)
																certified by C.E. Submitted. Whether Chartered Engineer
																as certified List of Assets provided as on last date of
																preceding financial year before first date Of investment
																(Cut Off Date). <a
																href="./downloadExistProjDoc?fileName=${charatEngFileName}">View
																	File</a></td>
															<td><form:input path="listOfAssetsCompl" type="text"
																	class="form-control" name="" /></td>
															<td><form:textarea path="listOfAssetsObserve"
																	class="form-control"
																	placeholder="Observations By PICUP" rows="2" /></td>
														</tr>
													</c:if>

													<tr>
														<td>11</td>
														<td><strong>Undertaking</strong>Whether notarized
															undertaking as per format placed at Annexure I-A on Stamp
															Paper of Rs. 10/-submitted. <a
															href="./downloadBusinessEntityDoc?fileName=${annexureiaformat}">View
																File</a></td>
														<td><form:input path="undertakingCompl" type="text"
																class="form-control" name="" value="Yes" readonly="true" /></td>
														<td><form:textarea path="undertakingObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
													</tr>
													<tr>
														<td>12</td>
														<td><strong>Name of Authorized Signatory</strong>Whether
															Board Resolution copy provided.</td>
														<td><form:input path="authSignCompl" type="text"
																class="form-control" value="${bussAuthSigName}" name=""
																readonly="true" /></td>
														<td><form:textarea path="authSignObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
													</tr>
													<tr>
														<td>13</td>
														<td><strong>Application Format</strong>Whether
															application is as per prescribed format & signed by
															Authorized Signatory with Name,Designation and Official
															Seal.</td>
														<td><form:input path="applFormatCompl" type="text"
																class="form-control" name="" value="Yes" readonly="true" /></td>
														<td><form:textarea path="applFormatObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
													</tr>
													<tr>
														<td>14</td>
														<td>Whether all supporting document including DPR of
															application authenticated by a Director/Partner/Officer
															duly authorized by the competent authority of the
															applicant on its behalf.</td>
														<td><form:input path="suppDocDirCompl" type="text"
																class="form-control" name="" value="Yes" readonly="true" /></td>
														<td><form:textarea path="suppDocDirObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
													</tr>
													<tr>
														<td>15</td>
														<td>Whether copy of Bank Appraisal submitted</td>
														<td><form:input path="bankApprCompl" type="text"
																class="form-control" name="bankApprCompl" /></td>
														<td><form:textarea path="bankApprObserve"
																class="form-control" placeholder="Observations By PICUP"
																rows="2" readonly="false" /></td>
													</tr>
													<tr>
														<td colspan="5"><form:textarea
																path="eligOfBenefitsNote"
																class="form-control border-info"
																placeholder="Note (if any)" rows="4" /></td>

													</tr>
													<tr>
														<td colspan="5"><label class="table-heading">Admissible
																Benefits (<a
																href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																target="_blank">As per para 3.3</a> As per table-3.0</a> of
																Rules IIEPP-2017)

														</label></td>
													</tr>
													<tr class="disallow-row">
														<td colspan="5"><form:textarea
																path="admissibleBenefits" id="admissibleBenefits"
																placeholder="Observations Comment By PICUP"
																name="admissibleBenefits"
																class="form-control border-info" rows="4"></form:textarea>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
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
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td><label class="table-heading">Address of
																	Company of the Industrial Unit</label></td>
															<td><form:input path="addOfFactoryCompl" type="text"
																	class="form-control" id="addOfFactoryCompl"
																	value="${disApplicantDetails.projAddressDis}"
																	readonly="true" name="addOfFactoryCompl" /></td>
															<td><form:textarea path="addOfFactoryObserv"
																	id="addOfFactoryObserv"
																	placeholder="Observations Comment By PICUP"
																	name="addOfFactoryObserv"
																	class="form-control border-info" rows="2"></form:textarea>
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
																	rows="2"></form:textarea></td>
														</tr>
														<tr>
															<td><label class="table-heading">Date of
																	Actual Start of Commercial Production</label></td>
															<td><form:input path="dateOfStartCompl" type="date"
																	class="form-control" id="dateOfStartCompl"
																	value="${evaluateInvestMentDetails.propCommProdDate}"
																	name="dateOfStartCompl" /></td>
															<td><form:textarea path="dateOfStartObserv"
																	id="dateOfStartObserv"
																	placeholder="Observations Comment By PICUP"
																	name="dateOfStartObserv"
																	class="form-control border-info" rows="2"></form:textarea>
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
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<c:if test="${natureOfProject=='Expansion'}">
															<%-- <tr>
																<td><label class="table-heading">Product-Wise
																		Installed Capacity for Expansion Unit</label></td>
																<td><form:input path="productWiseCompl" type="text"
																		class="form-control" id="productWiseCompl" value="Yes"
																		readonly="true" name="productWiseCompl" /></td>
																<td><form:textarea path="productWiseObserv"
																		id="productWiseObserv"
																		placeholder="Observations Comment By PICUP"
																		name="productWiseObserv"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr> --%>
															<tr>
																<td colspan="3">
																	<div class="table-responsive mt-3">
																		<div class="table-responsive mt-3"
																			id="productsDetailsTbl">
																			<table class="table table-stripped table-bordered"
																				id="edittable">
																				<thead>
																					<tr>
																						<td colspan="6">
																							<h4>
																								<b>Product-Wise Installed Capacity</b>
																							</h4>
																						</td>
																					</tr>
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
																		class="form-control border-info" rows="2"></form:textarea>
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
															<td>Whether project is being set up in phases?</td>
															<td><form:input path="wheProdSetupPhs"
																	id="wheProdSetupPhs" name="wheProdSetupPhs" value="Yes"
																	class="form-control" readonly="true" /></td>
															<td><form:textarea path="wheProdSetupPhsObserv"
																	id="wheProdSetupPhsObserv"
																	placeholder="Observations Comment By PICUP"
																	name="wheProdSetupPhsObserv"
																	class="form-control border-info" rows="2"></form:textarea>
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
																						<td><input type="date" name="actDtCommProd"
																							class="form-control border-info"
																							value="${pwInvObj.actDtCommProd}"
																							placeholder="DD/MM/YYY"></td>
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
																	rows="2"></form:textarea></td>
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
																	rows="2"></form:textarea></td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<tbody>
																			<tr>
																				<td><label><strong>External
																							Expert Report Details(If any)</strong></label> <form:textarea
																						path="externalERD" id="externalERD"
																						placeholder="Confirmation Comment By PICUP"
																						name="externalERD"
																						class="form-control border-info" rows="4"></form:textarea>
																					<label class="mt-2"><strong>Upload
																							Related Documents</strong></label>
																					<div class="custom-file">
																						<input type="file"
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
																	of Cost of Project- As per statutory auditor
																	certificate</label></td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered  cp-invest">
																		<thead>
																			<th>S.No</th>
																			<th>Particulars <a href="javascript:void(0);"
																				class="remove-row" data-toggle="tooltip" title=""
																				data-original-title="(In accordance with Table-2 of Rules for implementation of IIEPP-2017.)"><i
																					class="fa fa-info-circle text-info"></i></a></th>

																			<th colspan="2">Proposed Investment in the
																				project</th>

																			<th width="14%;">If any investment made in the
																				proposed project prior to first date Of investment
																				(Cut Off Date). <a href="javascript:void(0);"
																				class="remove-row" data-toggle="tooltip" title=""
																				data-original-title="If any investment made in the proposed project prior to First Date Of Investment (Cut Off Date) then indicate first date of such investment and amount invested from first date till first date Of investment (Cut Off Date)."><i
																					class="fa fa-info-circle text-info"></i></a>
																			</th>

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
																				<td>I</td>
																				<td>II</td>
																				<td>III</td>
																				<td>IV</td>
																				<td>V</td>
																				<td>VI</td>
																				<td>VII</td>
																				<td>VIII</td>
																				<td>IX</td>
																				<td>X</td>
																			</tr>
																			<tr>
																				<td></td>
																				<td></td>

																				<td>As per DPR</td>
																				<td>As per Bank Appraisal</td>

																				<td>Indicate first date of investment <form:input
																						path="optCutofDate" name="optCutofDate"
																						type="date" class="form-control mt-2 border-info"
																						value="" placeholder="DD-MM-YYYY" />
																				</td>

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
																				<td>Total (VI+VII)</td>
																				<td></td>
																				<td></td>
																			</tr>
																		</thead>
																		<tbody>

																			<td>1</td>
																			<td>Land and Site Development</td>
																			<td width="12%;"><form:input path="landAmtInv"
																					type="text"
																					class="form-control is-numeric amtInvCutOff"
																					maxlength="12" /> <small class="words text-info"></small></td>
																			<td width="12%;"><form:input path="landPerDpr"
																					type="text" class="form-control is-numeric dprAmt"
																					maxlength="12" value="${capInvDetails.capInvDPRLC}"
																					readonly="true" />
																			<td width="12%;"><form:input
																					path="landBankApprai" type="text"
																					class="form-control is-numeric bankApprAmt"
																					maxlength="12"
																					value="${capInvDetails.capInvAppraisalLC}" /><small
																				class="words text-info"></small></td>

																			<td width="12%;"><form:input path="landPerCerti"
																					type="text"
																					class="VIandVII1 form-control is-numeric perCertiAuditor"
																					maxlength="12" /> <small class="words text-info"></small></td>

																			<td width="12%;"><form:input path="landCapInvCA"
																					type="text"
																					class="VIandVII1 form-control is-numeric capitalCA"
																					maxlength="12" /><small class="words text-info"></small></td>
																			<td width="12%;"><form:input
																					path="landCapInvValuer" type="text" readonly="true"
																					class="VIandVIITotal1 form-control is-numeric capitalValuer"
																					maxlength="12" /><small class="words text-info"></small></td>
																			<td width="12%;"><form:input type="text"
																					path="landafterinv"
																					class="form-control is-numeric statutoryAudit1"
																					maxlength="12" /><small class="words text-info"></small></td>
																			<td width="12%;"><form:input
																					path="landStatutoryAudit" type="text"
																					class="form-control is-numeric certificateAudit"
																					maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>2</td>
																				<td>Building and Civil Works</td>
																				<td><form:input path="buildAmtInv" type="text"
																						class="form-control is-numeric amtInvCutOff"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="buildPerDpr" type="text"
																						value="${capInvDetails.capInvDPRBC}"
																						maxlength="12" class="form-control dprAmt"
																						readonly="true" /></td>
																				<td><form:input path="buildBankApprai"
																						type="text" maxlength="12"
																						class="form-control is-numeric bankApprAmt"
																						value="${capInvDetails.capInvAppraisalBC}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="buildPerCerti"
																						type="text"
																						class="VIandVII2 form-control is-numeric perCertiAuditor"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="buildCapInvCA"
																						type="text"
																						class="VIandVII2 form-control is-numeric capitalCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="buildCapInvValuer"
																						type="text" readonly="true"
																						class="VIandVIITotal2 form-control is-numeric capitalValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="buildafterinv" type="text"
																						class="form-control is-numeric statutoryAudit1"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="buildStatutoryAudit"
																						type="text"
																						class="form-control is-numeric certificateAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>3</td>
																				<td>Plant & Machinery</td>
																				<td><form:input path="plantAmtInv" type="text"
																						class="form-control is-numeric amtInvCutOff"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="plantPerDpr" type="text"
																						class="form-control is-numeric dprAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvDPRPMC}"
																						readonly="true" /></td>
																				<td><form:input path="plantBankApprai"
																						type="text"
																						class="form-control is-numeric bankApprAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalPMC}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="plantPerCertificate"
																						type="text"
																						class="VIandVII3 form-control is-numeric perCertiAuditor"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="plantCapInvCA"
																						type="text"
																						class="VIandVII3 form-control is-numeric capitalCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="plantCapInvValuer"
																						type="text" readonly="true"
																						class="VIandVIITotal3 form-control is-numeric capitalValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="plantdafterinv" type="text"
																						class="form-control is-numeric statutoryAudit1"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="plantStatutoryAudit"
																						type="text"
																						class="form-control is-numeric certificateAudit"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>4</td>
																				<td>Misc. Fixed Assets</td>
																				<td><form:input path="miscAmtInve" type="text"
																						class="form-control is-numeric amtInvCutOff"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="miscPerDpr" type="text"
																						class="form-control is-numeric dprAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvDPRMFA}"
																						readonly="true" /></td>
																				<td><form:input path="miscBankApprai"
																						type="text"
																						class="form-control is-numeric  bankApprAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalMFA}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="miscPerCertificate"
																						type="text"
																						class="VIandVII4 form-control is-numeric perCertiAuditor"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="miscCapInvCA" type="text"
																						class="VIandVII4 form-control is-numeric capitalCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="miscCapInvValuer"
																						type="text" readonly="true"
																						class="VIandVIITotal4 form-control is-numeric capitalValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="miscafterinv" type="text"
																						class="form-control is-numeric statutoryAudit1"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="miscStatutoryAuditor"
																						type="text"
																						class="form-control is-numeric certificateAudit"
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
																						type="text" id="subTtlAPerDpr"
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
																						path="subTtlAafterinv" type="text" readonly="true"
																						class="form-control is-numeric statutoryAuditTotal1 subkasubTtlStatutory"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="subTtlAStatutoryAudit"
																						type="text"
																						class="form-control is-numeric certificateAuditTotal subkasubcertificateAudit"
																						readonly="true" maxlength="12" /></td>

																			</tr>
																			<tr>
																				<td>5</td>
																				<td>Provision for Cost Escalation &
																					Contingencies</td>
																				<td><form:input path="provisionAmtInve"
																						type="text"
																						class="form-control is-numeric cutOffDateAmt"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="provisionPerDpr"
																						type="text"
																						class="form-control is-numeric perDPRAmt"
																						value="${capInvDetails.capInvDPRTKF}"
																						maxlength="12" readonly="true" /></td>
																				<td><form:input path="provisionBankApprai"
																						type="text"
																						class="form-control is-numeric perBankAppr"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalTKF}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="provisionPerCerti"
																						type="text"
																						class="VIandVII5 form-control is-numeric companyDated"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="provisionCapInvCA"
																						type="text"
																						class="VIandVII5 form-control is-numeric empaneledCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="provisionCapInvValuer"
																						type="text" readonly="true"
																						class="VIandVIITotal5 form-control is-numeric empaneledValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="provisionafterinv" type="text"
																						class="form-control is-numeric statutoryAudit2"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="provisionStatutoryAudit"
																						type="text"
																						class="form-control is-numeric certificateAudit2"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>6</td>
																				<td>Preliminary & Preoperative Expenses</td>
																				<td><form:input path="prelimAmtInve"
																						type="text"
																						class="form-control is-numeric cutOffDateAmt"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="prelimPerDpr" type="text"
																						readonly="true"
																						class="form-control is-numeric perDPRAmt"
																						value="${capInvDetails.capInvDPRPPE}"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="prelimBankApprai"
																						type="text"
																						class="form-control is-numeric perBankAppr"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalPPE}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="prelimPerCerti"
																						type="text"
																						class="VIandVII6 form-control is-numeric companyDated "
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="prelimCapInvCA"
																						type="text"
																						class="VIandVII6 form-control is-numeric empaneledCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="prelimCapInvValuer"
																						type="text" readonly="true"
																						class="VIandVIITotal6 form-control is-numeric empaneledValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="prelimafterinv" type="text"
																						class="form-control is-numeric statutoryAudit2"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="prelimStatutoryAudit"
																						type="text"
																						class="form-control is-numeric certificateAudit2"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>7</td>
																				<td>Interest During Construction Period</td>
																				<td><form:input path="interestAmtInve"
																						type="text"
																						class="form-control is-numeric cutOffDateAmt"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="interestPerDpr"
																						type="text" readonly="true"
																						class="form-control is-numeric perDPRAmt"
																						maxlength="12"
																						value="${capInvDetails.capInvDPRICP}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="interestBankApprai"
																						type="text"
																						class="form-control is-numeric perBankAppr"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalICP}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="interestPerCerti"
																						type="text"
																						class="VIandVII7 form-control is-numeric companyDated "
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="interestCapInvCA"
																						type="text"
																						class="VIandVII7 form-control is-numeric empaneledCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="interestCapInvValuer"
																						type="text" readonly="true"
																						class="VIandVIITotal7 form-control is-numeric empaneledValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="interestafterinv" type="text"
																						class="form-control is-numeric statutoryAudit2"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="interestStatutoryAudit"
																						type="text"
																						class="form-control is-numeric certificateAudit2"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>8</td>
																				<td>Margin Money for Working Capital</td>
																				<td><form:input path="marginAmtInve"
																						type="text"
																						class="form-control is-numeric cutOffDateAmt"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="marginPerDpr" type="text"
																						class="form-control is-numeric perDPRAmt"
																						maxlength="12" readonly="true"
																						value="${capInvDetails.capInvDPRMMC}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="marginBankApprai"
																						type="text"
																						class="form-control is-numeric perBankAppr"
																						maxlength="12"
																						value="${capInvDetails.capInvAppraisalMMC}" /><small
																					class="words text-info"></small></td>
																				<td><form:input path="marginPerCerti"
																						type="text"
																						class="VIandVII8 form-control is-numeric companyDated "
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="marginCapInvCA"
																						type="text"
																						class="VIandVII8 form-control is-numeric empaneledCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="marginCapInvValuer"
																						type="text" readonly="true"
																						class="VIandVIITotal8 form-control is-numeric empaneledValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="marginafterinv" type="text"
																						class="form-control is-numeric statutoryAudit2"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="marginStatutoryAudit"
																						type="text"
																						class="form-control is-numeric certificateAudit2"
																						maxlength="12" /><small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>9</td>
																				<td>Other, If any</td>
																				<td><form:input path="otherAmtInve" type="text"
																						class="form-control is-numeric cutOffDateAmt"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="otherPerDpr" type="text"
																						class="form-control is-numeric perDPRAmt"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="otherBankApprai"
																						type="text"
																						class="form-control is-numeric perBankAppr"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="otherPerCerti"
																						type="text"
																						class="VIandVII9 form-control is-numeric companyDated "
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="otherCapInvCA"
																						type="text"
																						class="VIandVII9 form-control is-numeric empaneledCA"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="otherCapInvValuer"
																						type="text" readonly="true"
																						class="VIandVIITotal9 form-control is-numeric empaneledValuer"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td width="12%;"><form:input
																						path="otherafterinv" type="text"
																						class="form-control is-numeric statutoryAudit2"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="otherStatutoryAudit"
																						type="text"
																						class="form-control is-numeric certificateAudit2"
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
																						readonly="true"
																						class="form-control is-numeric statutoryAuditTotal2 subkasubTtlStatutory"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="subTtlBStatutoryAudit"
																						type="text"
																						class="form-control certificateAuditTotal2 subkasubcertificateAudit"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td></td>
																				<td><strong>Total (A+B)</strong></td>
																				<td><form:input path="ttlAmtInve" type="text"
																						class="form-control subTtlCutOffTotal"
																						readonly="true" /></td>
																				<td><form:input path="ttlPerDpr" id="ttlPerDpr"
																						type="text" class="form-control subTtlDPRTotal"
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
																						type="text" readonly="true"
																						class="form-control is-numeric subkasubTtlStatutoryTotal"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="ttlStatutoryAudit"
																						type="text"
																						class="form-control subkasubcertificateAuditTotal"
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
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">Means
																	Of Financing - As per statutory auditor certificate</label></td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-responsive cp-invest">
																		<thead>
																			<tr>
																				<th>S.No.</th>
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
																				<td>I</td>
																				<td>II</td>
																				<td>III</td>
																				<td>IV</td>
																				<td>V</td>
																				<td>VI</td>
																				<td>VII</td>
																				<td>VIII</td>
																				<td>IX</td>
																			</tr>
																			<tr>
																				<td></td>
																				<td>Particulars</td>
																				<td>As per DPR</td>
																				<td>As per Bank Appraisal</td>
																				<td>Indicate amount invested from first date of
																					investment <a href="javascript:void(0);"
																					class="remove-row" data-toggle="tooltip" title=""
																					data-original-title="Indicate amount invested from first date of investment (cut-off date)
																					 till 12.07.2017(if project is being set up in phases, phase-wise investment be indicated)"><i
																						class="fa fa-info-circle text-info"></i></a>
																				</td>
																				<td>Indicate amount invested from 13.07.2017 to
																					the date of submitting the application for LOC or
																					till the date of commencement of commercial
																					Production <a href="javascript:void(0);"
																					class="remove-row" data-toggle="tooltip" title=""
																					data-original-title="Indicate amount invested from 13.07.2017 to the date
																					 of submitting the application for LOC or till the date of commencement of commercial Production
																					  (in case commercial production started) (if project is being set up in phases, phase-wise
																					   investment be indicated from 13.7.2017)"><i
																						class="fa fa-info-circle text-info"></i></a>
																				</td>
																				<td>Total (V+VI)</td>
																				<td></td>
																				<td></td>
																			</tr>
																			<tr>
																				<td>1</td>
																				<td colspan="6"><strong>Equity</strong></td>

																			</tr>
																			<tr>
																				<td>1.1</td>
																				<td>Equity Share Capital</td>
																				<td><form:input path="equityCapPerDpr"
																						type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="equityCapBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="equityCapPerCerti"
																						type="text" id="equityCapPerCerti"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="equityCapCapInvCA"
																						type="text" id="equityCapCapInvCA"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="equityCapStatutoryAudit"
																						type="text" id="equityCapStatutoryAudit"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>

																				<td><form:input path="equAftinvdate"
																						type="text"
																						class="form-control FinaStaAuditt is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="equAftinvproddate"
																						type="text"
																						class="form-control FinaStaAudit12 is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>1.2</td>
																				<td>Internal Cash Accruals</td>
																				<td><form:input path="intCashPerDpr"
																						type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intCashBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intCashPerCerti"
																						type="text" id="intCashPerCerti"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intCashCapInvCA"
																						type="text" id="intCashCapInvCA"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intCashStatutoryAudit"
																						type="text" id="intCashStatutoryAudit"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intCashAftinvdate"
																						type="text"
																						class="form-control FinaStaAuditt is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intCashAftinvproddate"
																						type="text"
																						class="form-control FinaStaAudit12 is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>1.3</td>
																				<td>Interest Free Unsecured Loans & Promoter as
																					contribution</td>
																				<td><form:input path="intFreePerDpr"
																						type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intFreeBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intFreePerCerti"
																						type="text" id="intFreePerCerti"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="intFreeCapInvCA"
																						type="text" id="intFreeCapInvCA"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intFreeStatutoryAudit"
																						type="text" id="intFreeStatutoryAudit"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intFreeAftinvdate"
																						type="text"
																						class="form-control FinaStaAuditt is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="intFreeAftinvproddate"
																						type="text"
																						class="form-control FinaStaAudit12 is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>1.4</td>
																				<td>Security Deposit</td>
																				<td><form:input path="SecPerDpr" type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="SecBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="SecPerCerti" type="text"
																						id="SecPerCerti"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="SecCapInvCA" type="text"
																						id="SecCapInvCA"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="SecStatutoryAudit"
																						type="text" id="SecStatutoryAudit"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="SeceAftinvdate"
																						type="text"
																						class="form-control FinaStaAuditt is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="SecAftinvproddate"
																						type="text"
																						class="form-control FinaStaAudit12 is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>1.5</td>
																				<td>Advances from Dealers</td>

																				<td><form:input path="dealPerDpr" type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="dealBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="dealPerCerti" type="text"
																						id="dealPerCerti"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /><small class="words text-info"></small></td>
																				<td><form:input path="dealCapInvCA" type="text"
																						id="dealCapInvCA"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="dealStatutoryAudit"
																						type="text" id="dealStatutoryAudit"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="dealeAftinvdate"
																						type="text"
																						class="form-control FinaStaAuditt is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="dealAftinvproddate"
																						type="text"
																						class="form-control FinaStaAudit12 is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>

																			<tr>
																				<td>1.6</td>
																				<td>Other, If any</td>

																				<td><form:input path="finOthPerDpr" type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="finOthBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="finOthPerCerti"
																						type="text" id="finOthPerCerti"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="finOthCapInvCA"
																						type="text" id="finOthCapInvCA"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="finOthStatutoryAudit"
																						type="text" id="finOthStatutoryAudit"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="finOtheAftinvdate"
																						type="text"
																						class="form-control FinaStaAuditt is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="finOthAftinvproddate"
																						type="text"
																						class="form-control FinaStaAudit12 is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>2</td>
																				<td colspan="6"><strong>Loan</strong></td>

																			</tr>
																			<tr>
																				<td>2.1</td>
																				<td>From FI's</td>
																				<td><form:input path="FromFiPerDpr" type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="FromFiBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="FromFiPerCerti"
																						type="text" id="FromFiPerCerti"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>

																				<td width="9%;"><form:input
																						path="FromFiCapInvCA" type="text"
																						id="FromFiCapInvCA"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="FromFiStatutoryAudit"
																						type="text" id="FromFiStatutoryAudit"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td width="9%;"><form:input
																						path="FromFieAftinvdate" type="text"
																						class="form-control FinaStaAuditt is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="FromFiAftinvproddate"
																						type="text"
																						class="form-control FinaStaAudit12 is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>2.2</td>
																				<td>From Bank</td>
																				<td><form:input path="FrBankPerDpr" type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="FrBankBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="FrBankPerCerti"
																						type="text" id="FrBankPerCerti"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>

																				<td width="9%;"><form:input
																						path="FrBankCapInvCA" type="text"
																						id="FrBankCapInvCA"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="FrBankStatutoryAudit"
																						type="text" id="FrBankStatutoryAudit"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td width="9%;"><form:input
																						path="frBankeAftinvdate" type="text"
																						class="form-control FinaStaAuditt is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="frBankAftinvproddate"
																						type="text"
																						class="form-control FinaStaAudit12 is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td>2.3</td>
																				<td>Other, If any</td>
																				<td><form:input path="PlantMachPerDpr"
																						type="text"
																						class="form-control FinancingDPR is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="PlantMachBankApprai"
																						type="text"
																						class="form-control FinaBankAppr is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="Plant_MachPerCerti"
																						type="text" id="Plant_MachPerCerti"
																						class="form-control FinaPerCerti is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="PlantMachCapInvCA"
																						type="text" id="PlantMachCapInvCA"
																						class="form-control FinancingCA is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="PlantMachStatutoryAudit"
																						type="text" id="PlantMachStatutoryAudit"
																						class="form-control FinaStaAudit is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="Plant_MacheAftinvdate"
																						type="text"
																						class="form-control FinaStaAuditt is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="Plant_MachAftinvproddate"
																						type="text"
																						class="form-control FinaStaAudit12 is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td></td>
																				<td><strong>Total</strong></td>
																				<td><form:input path="finTttlPerDpr"
																						type="text" class="form-control FinancingDPRTotal"
																						readonly="true" /></td>
																				<td><form:input path="finTttlBankApprai"
																						type="text" class="form-control FinaBankApprTotal"
																						readonly="true" /></td>
																				<td><form:input path="finTttlPerCerti"
																						id="finTttlPerCerti" type="text"
																						class="form-control FinaPerCertiTotal"
																						readonly="true" /></td>
																				<td><form:input path="finTttlCapInvCA"
																						id="finTttlCapInvCA" type="text"
																						class="form-control FinancingCATotal"
																						readonly="true" /></td>
																				<td><form:input path="finTttlStatutoryAudit"
																						type="text" id="finTttlStatutoryAudit"
																						class="form-control  FinaStaAuditTotal"
																						readonly="true" /></td>

																				<td><form:input path="finTttldate" type="text"
																						id="finTttldate1"
																						class="form-control  finTttldate1" readonly="true" /></td>

																				<td><form:input path="finTttlproddate"
																						type="text" class="form-control  finTttlproddate"
																						readonly="true" /></td>
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
																	name="financingObserve"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<%-- <tr>
															<td colspan="3"><label class="table-heading">Details
																	of Incentive Claimed (As per LoC)</label></td>
														</tr>
														<tr>
															<td colspan="3">
															<form:form modelAttribute="incentiveDeatilsData"
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
																									rows="4" maxlength="1000" class="form-control"></form:textarea></td>
										
																						</tr>
																						<c:if
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
																					</c:if>
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
																									rows="4" maxlength="1000" class="form-control" /></td>
																						</tr>
																						<c:if
																		test="${not empty incentiveDeatilsData.isfScstComment}">
																		<tr class="ISF_Reim_SCST-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Industrial Development/ Commercial Tax</p> <form:textarea
																					class="form-control" path="isfScstComment"
																					name="isfScstComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									maxlength="1000" class="form-control" /></td>
																						</tr>
																						<c:if
																		test="${not empty incentiveDeatilsData.isffwComment}">

																		<tr class="ISF_Reim_FW-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Industrial Development/ Commercial Tax</p> <form:textarea
																					class="form-control" path="isffwComment"
																					name="isffwComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
																						</tr>
																						<c:if  test="${not empty incentiveDeatilsData.isfBplComment}">
																		<tr class="ISF_Reim_BPLW-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Industrial Development/ Commercial Tax</p> <form:textarea
																					class="form-control" path="isfBplComment"
																					name="isfBplComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									rows="4" class="form-control"></form:textarea></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfStampComment}">
																		<tr class="ISF_Stamp_Duty_EX-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Stamp & Registration</p> <form:textarea
																					class="form-control" path="isfStampComment"
																					name="isfStampComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
																						<!-- sachin -->
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
																									rows="4" class="form-control"></form:textarea></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfStampComment}">
																		<tr class="ISF_Amt_Stamp_Duty_Reim-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Stamp & Registration</p> <form:textarea
																					class="form-control" path="isfStampComment"
																					name="isfStampComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									rows="4" maxlength="1000" class="form-control" /></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfStampscstComment}">
																		<tr class="ISF_Additonal_Stamp_Duty_EX-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Stamp & Registration</p> <form:textarea
																					class="form-control" path="isfStampscstComment"
																					name="isfStampscstComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									rows="4" class="form-control" /></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfepfComment}">
																		<tr class="ISF_Epf_Reim_UW-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Labour/Industrial Development</p> <form:textarea
																					class="form-control" path="isfepfComment"
																					name="isfepfComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									rows="4" maxlength="1000" class="form-control" /></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfepfaddComment}">
																		<tr class="ISF_Add_Epf_Reim_SkUkW-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Labour/Industrial Development</p> <form:textarea
																					class="form-control" path="isfepfaddComment"
																					name="isfepfaddComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									rows="4" maxlength="1000" class="form-control" /></td>
																						</tr>
																							<c:if test="${not empty incentiveDeatilsData.isfepfscComment}">
																		<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Labour/Industrial Development</p> <form:textarea
																					class="form-control" path="isfepfscComment"
																					name="isfepfscComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfcapisComment}">
																		<tr class="ISF_Cis-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Industrial Development</p> <form:textarea
																					class="form-control" path="isfcapisComment"
																					name="isfcapisComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									rows="4" maxlength="1000" class="form-control" /></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfcapisaComment}">
																		<tr class="ISF_ACI_Subsidy_Indus-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Industrial Development</p> <form:textarea
																					class="form-control" path="isfcapisaComment"
																					name="isfcapisaComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfinfComment}">
																		<tr class="ISF_Infra_Int_Subsidy-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Industrial Development</p> <form:textarea
																					class="form-control" path="isfinfComment"
																					name="isfinfComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									rows="4" maxlength="1000" class="form-control"></form:textarea></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfinfaComment}">
																		<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Industrial Development</p> <form:textarea
																					class="form-control" path="isfinfaComment"
																					name="isfinfaComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfloanComment}">
																		<tr class="ISF_Loan_Subsidy-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Industrial Development</p> <form:textarea
																					class="form-control" path="isfloanComment"
																					name="isfloanComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
																						
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
																									rows="4" maxlength="1000" class="form-control"></form:textarea></td>
																						</tr>
																						<c:if test="${not empty incentiveDeatilsData.isfdisComment}">
																		<tr class="ISF_Tax_Credit_Reim-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Industrial Development</p> <form:textarea
																					class="form-control" path="isfdisComment"
																					name="isfdisComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																				</c:if>
																						
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
																									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
																						</tr>
																							<c:if test="${not empty incentiveDeatilsData.isfelepodownComment}">
																		<tr class="ISF_EX_E_Duty-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : UPPCL</p> <form:textarea class="form-control"
																					path="isfelepodownComment"
																					name="isfelepodownComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									rows="4" class="form-control"></form:textarea></td>
																						</tr>
																						
																						<c:if
																		test="${not empty incentiveDeatilsData.isfElecdutyComment}">
																		<tr class="ISF_EX_E_Duty_PC-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : UPPCL</p> <form:textarea class="form-control"
																					path="isfElecdutyComment" name="isfElecdutyComment"
																					disabled="" rows="3" placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
																									rows="4" maxlength="1000" class="form-control"></form:textarea></td>
																						</tr>
																						<c:if
																		test="${not empty incentiveDeatilsData.isfMandiComment}">
																		<tr class="ISF_EX_Mandee_Fee-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Agriculture Marketing & Agriculture Foreign
																					Trade/MandiParishad</p> <form:textarea
																					class="form-control" path="isfMandiComment"
																					name="isfMandiComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
																						
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
																									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
																						</tr>
																						<c:if
																		test="${not empty incentiveDeatilsData.isfdifferabilComment}">
																		<tr class="ISF_Indus_Payroll_Asst-row">
																			<td colspan="5"><p class="text-info">Comment
																					From : Labour/Industrial Development</p> <form:textarea
																					class="form-control" path="isfdifferabilComment"
																					name="isfdifferabilComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
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
														</tr> --%>

														<tr class="expensionSGST sgst-row">
															<td colspan="3"><label class="table-heading">Turnover
																	of Base Production(For Items Approved)</label></td>
														</tr>
														<tr class="expensionSGST sgst-row">
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
																						class="form-control is-numeric" maxlength="12"
																						placeholder="Enter dd-mm-yyyy or YYYY-YYYY" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="turnoverOfSales1"
																						type="text" class="form-control" maxlength="500" />
																					<small class="words text-info"></small></td>
																				<td><form:input path="turnoverProduction1"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="finYr2" type="text"
																						class="form-control is-numeric" maxlength="12"
																						placeholder="Enter dd-mm-yyyy or YYYY-YYYY" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="turnoverOfSales2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="turnoverProduction2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="finYr3" type="text"
																						class="form-control is-numeric" maxlength="12"
																						placeholder="Enter dd-mm-yyyy or YYYY-YYYY" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="turnoverOfSales3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="turnoverProduction3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="finYr4" type="text"
																						class="form-control is-numeric" maxlength="12"
																						placeholder="Enter dd-mm-yyyy or YYYY-YYYY" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="turnoverOfSales4"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="turnoverProduction4"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr class="sgst-row">
															<td colspan="3"><form:textarea
																	path="turnOverObserve" id="turnOverObserve"
																	placeholder="Observations Comment By PICUP"
																	name="turnOverObserve" class="form-control border-info"
																	rows="2"></form:textarea></td>
														</tr>
														<tr class="sgst-row">
															<td colspan="4">
																<h4>
																	<b>Details of Incentives Claimed</b>
																</h4>
															</td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">SGST-
																	Amount of Reimbursement</label></td>
														</tr>

														<tr class="newSGST sgst-row">
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<!-- <th>S. No.</th> -->
																				<th rowspan="2">Financial Year</th>
																				<th colspan="2">Duration/Period</th>
																				<th rowspan="2">Amount of Net SGST Deposited by
																					the Company</th>

																				<th rowspan="2">Amount of Net SGST Eligible for
																					Reimbursement. <small>(${category} of
																						Deposited Incremental SGST)</small>
																				</th>
																			</tr>

																			<tr>
																				<th>From</th>
																				<th>To</th>
																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach var="rgstTable"
																				items="${disReimbrsGSTTableList}"
																				varStatus="counter">
																				<tr>
																					<td><form:input path="durationFinYr1New"
																							value="${rgstTable.financialYear}" type="text"
																							class="form-control " maxlength="12"
																							readonly="true" /></td>
																					<td width="11%"><input type="text"
																						value="${rgstTable.durationPeriodDtFr}"
																						class="form-control" readonly="readonly"></input>
																					</td>
																					<td width="11%"><input type="text"
																						value="${rgstTable.durationPeriodDtTo}"
																						class="form-control" readonly="readonly"></input>
																					</td>
																					<td><form:input path="amtOfNetSGST1New"
																							type="text" id="amtOfNetSGST1New" readonly="true"
																							onkeyup="amtOfNetSGST1New"
																							value="${rgstTable.amtNetSgst}"
																							class="form-control is-numeric amtOfNetSGSTColumn"
																							maxlength="12" /> <!-- <small class="words text-info"></small> --></td>

																					<td><form:input path="amtOfNetSGSTReim1New"
																							id="amtOfNetSGSTReim1New" type="text"
																							value="${rgstTable.amtnetconvert}"
																							class="form-control is-numeric amtOfNetSGSTReimColumn"
																							readonly="true" maxlength="12" /> <!-- <small class="words text-info"></small> --></td>
																				</tr>
																			</c:forEach>
																			<%-- <tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="durationFinYr2New" readonly="true"
																						type="text" class="form-control" maxlength="12" />
																					<small class="words text-info"></small></td>

																				<td><form:input path="amtOfNetSGST2New" readonly="true"
																						id="amtOfNetSGST2New" type="text"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>

																				<td><form:input path="amtOfNetSGSTReim2New"
																						id="amtOfNetSGSTReim2New" type="text"
																						class="form-control is-numeric" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="durationFinYr3New" readonly="true"
																						type="text" class="form-control" maxlength="12" />
																					<small class="words text-info"></small></td>

																				<td><form:input path="amtOfNetSGST3New" readonly="true"
																						id="amtOfNetSGST3New" type="text"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>

																				<td><form:input path="amtOfNetSGSTReim3New"
																						id="amtOfNetSGSTReim3New" type="text"
																						class="form-control is-numeric" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="durationFinYr4New" readonly="true"
																						type="text" class="form-control" maxlength="12" />
																					<small class="words text-info"></small></td>

																				<td><form:input path="amtOfNetSGST4New" readonly="true"
																						id="amtOfNetSGST4New" type="text"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>

																				<td><form:input path="amtOfNetSGSTReim4New"
																						id="amtOfNetSGSTReim4New" type="text"
																						class="form-control is-numeric" readonly="true"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>


														<tr class="expensionSGST sgst-row">
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered cp-SGST">
																		<thead>
																			<tr>
																				<!-- <th>S. No.</th> -->
																				<th>Duration/Period/ Financial Year</th>
																				<th>Current TurnOver</th>
																				<th>Turnover of Base Production</th>
																				<th>Amount of Net SGST Deposited by the Company</th>
																				<th>The incremental turn over <small>(i.e.
																						Turn over –Turn over of the Base Production)</small></th>
																				<th>The incremental Net SGST <small>(i.e.
																						SGST on Turn over – SGST on Turn over equal to
																						the turn over ot the year of Base Production)</small> of
																					current year
																				</th>
																				<th>Amount of Net SGST Eligible for
																					Reimbursement. <small>(${category} of
																						Deposited Incremental SGST)</small>
																				</th>
																			</tr>
																		</thead>
																		<tbody>
																		<c:forEach var="rgstTable"
																							items="${disReimbrsGSTTableList}"
																							varStatus="counter">
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="durationFinYr1" value="${rgstTable.financialYear}"
																						type="text" class="form-control" maxlength="12" readonly="true" />
																					<!-- <small class="words text-info"></small> --></td>
																				<td width="14%;"><form:input
																						path="turnoverOfProduction1" type="text"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td width="14%;"><form:input
																						path="ttlAmtCommTax1" type="text" 
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td><form:input path="amtOfNetSGST1" value="${rgstTable.amtNetSgst}"
																						type="text" class="form-control is-numeric"
																						maxlength="12" readonly="true" /><!--  <small class="words text-info"></small> --></td>
																				<td width="14%;"><form:input
																						path="increTurnover1" type="text"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td width="14%;"><form:input
																						path="increNetSGST1" type="text"
																						class="form-control is-numeric" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td><form:input path="amtOfNetSGSTReim1" readonly="true" value="${rgstTable.amtnetconvert}"
																						type="text" class="form-control is-numeric" 
																						maxlength="12" /> <!-- <small class="words text-info"></small> --></td>
																			</tr>
																			</c:forEach>
																			<%-- <tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="durationFinYr2"
																						type="text" class="form-control" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td><form:input path="turnoverOfProduction2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="ttlAmtCommTax2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="amtOfNetSGST2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="increTurnover2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="increNetSGST2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="amtOfNetSGSTReim2"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="durationFinYr3"
																						type="text" class="form-control" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td><form:input path="turnoverOfProduction3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="ttlAmtCommTax3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="amtOfNetSGST3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="increTurnover3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="increNetSGST3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="amtOfNetSGSTReim3"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<!-- <td>&nbsp;</td> -->
																				<td><form:input path="durationFinYr4"
																						type="text" class="form-control" maxlength="12" />
																					<small class="words text-info"></small></td>
																				<td><form:input path="turnoverOfProduction4"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="ttlAmtCommTax4"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="amtOfNetSGST4"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="increTurnover4"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="increNetSGST4"
																						type="text" class="form-control  is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																				<td><form:input path="amtOfNetSGSTReim4"
																						type="text" class="form-control is-numeric"
																						maxlength="12" /> <small class="words text-info"></small></td>
																			</tr> --%>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr class="sgst-row">
															<td colspan="3"><form:textarea
																	path="sgstAmtReimObserve" id="sgstAmtReimObserve"
																	placeholder="Observations Comment By PICUP"
																	name="sgstAmtReimObserve"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
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
																						name="confProvbyCTD"
																						class="form-control border-info" rows="4"></form:textarea>
																					<label class="mt-2"><strong>Upload
																							Related Documents</strong></label>
																					<div class="custom-file">
																						<input type="file"
																							class="custom-file-input user-file"
																							onchange="return DocumentconfProvbyCTDDoc(event)"
																							id="confProvbyCTDDoc" name="confProvbyCTDDoc">
																						<label class="custom-file-label file"
																							id="chooseFileConfProvbyCTDDoc"
																							for="confProvbyCTDDoc">Choose File</label>
																					</div> <br> <br> <span id="confProvbyCTDDocMsg"
																					class="text-danger"></span></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>



														<tr class="discis-row">
															<td colspan="3"><label class="table-heading">Particulars
																	of Claims for Sanction of Capital Interest Subsidy <small>(as
																		provided by company)</small>
															</label></td>
														</tr>
														<tr class="discis-row">
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th rowspan="2">Year for which subsidy Applied</th>
																				<th colspan="2">Duration/ Period</th>
																				<th colspan="3">Payment made to FI/Bank during
																					the year</th>
																				<th rowspan="2" width="20%">Amount of Interest
																					Subsidy Applied</th>
																			</tr>
																			<tr>
																				<th>From</th>
																				<th>To</th>
																				<th>Total Interest on Loan</th>
																				<th>Principal</th>
																				<th>Interest on P&M</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td><input id="year1" name="yearI"
																					onkeyup="yrOfSubsidy1()" type="text"
																					value="${cisincentiveDeatilsForm.yearI}"
																					disabled="disabled"><span id="year1Msg"
																					class="text-danger"></span></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${cisincentiveDeatilsForm.durationPeriodDtFr1}"></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${cisincentiveDeatilsForm.durationPeriodDtTo1}"></td>

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
																				<td><input id="year2" name="yearII"
																					onkeyup="yrOfSubsidy2()" type="text"
																					value="${cisincentiveDeatilsForm.yearII}"
																					disabled="disabled"><span id="year2Msg"
																					class="text-danger"></span></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${cisincentiveDeatilsForm.durationPeriodDtFr2}"></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${cisincentiveDeatilsForm.durationPeriodDtTo2}"></td>

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
																				<td><input id="year3" name="yearIII"
																					onkeyup="yrOfSubsidy3()" type="text"
																					value="${cisincentiveDeatilsForm.yearIII}"
																					disabled="disabled"><span id="year3Msg"
																					class="text-danger"></span></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${cisincentiveDeatilsForm.durationPeriodDtFr3}"></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${cisincentiveDeatilsForm.durationPeriodDtTo3}"></td>

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
																				<td><input id="year4" name="yearIV"
																					onkeyup="yrOfSubsidy4()" type="text"
																					value="${cisincentiveDeatilsForm.yearIV}"
																					disabled="disabled"><span id="year4Msg"
																					class="text-danger"></span></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${cisincentiveDeatilsForm.durationPeriodDtFr4}"></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${cisincentiveDeatilsForm.durationPeriodDtTo4}"></td>

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
																				<td><input id="year5" name="yearV"
																					onkeyup="yrOfSubsidy5()" type="text"
																					value="${cisincentiveDeatilsForm.yearV}"
																					disabled="disabled"><span id="year5Msg"
																					class="text-danger"></span></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${cisincentiveDeatilsForm.durationPeriodDtFr5}"></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${cisincentiveDeatilsForm.durationPeriodDtTo5}"></td>

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
																				<td></td>
																				<td></td>
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
														<tr class="discis-row">
															<td colspan="3"><form:textarea path="cisTblObserve"
																	id="cisTblObserve"
																	placeholder="Observations Comment By PICUP"
																	name="cisTblObserve" class="form-control border-info"
																	rows="2"></form:textarea></td>
														</tr>
														<!-- <tr class="discis-row">
															<td colspan="3">
																<p>
																	<span class="text-danger">*</span> Calculation of
																	interest subsidy proportionate to the pro-rata loan on
																	plant and machinery, made by the company for 2015-16
																	and 2016-17 has been provided.
																</p>
															</td>
														</tr> -->

														<tr class="discis-row">
															<td colspan="3"><label class="table-heading">CIS
																	- Computation Methodology &amp; Amount</label> <a
																href="#Formula" data-toggle="modal" class="ml-3"><strong>(Formula)</strong></a>
															</td>
														</tr>
														<tr class="discis-row">
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
																				<td><form:input path="doFirstLoanCIS"
																						type="Date" class="form-control" /></td>
																			</tr>

																			<tr>
																				<td>2</td>
																				<td>Date of Last Loan Sanction</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doLastLoanCIS"
																						type="Date" class="form-control" /></td>
																			</tr>

																			<tr>
																				<td>3</td>
																				<td>Date of First Disbursement</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doFirstDisCIS"
																						type="Date" class="form-control" /></td>
																			</tr>


																			<tr>
																				<td>4</td>
																				<td>Date of Last Disbursement</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doLastDisCIS" type="Date"
																						class="form-control" /></td>
																			</tr>

																			<tr>
																				<td>5</td>
																				<td>Date of Payment</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doPaymentCIS" type="Date"
																						class="form-control" /></td>
																			</tr>

																			<tr>
																				<td>6</td>
																				<td>Total Cost of Project</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="cisCostOfProjectAmt"
																						id="cisCostOfProjectAmt" type="text"
																						class="form-control" readonly="true" value="" /></td>
																			</tr>
																			<tr>
																				<td>7</td>
																				<td>Cost of Plant & Machinery</td>
																				<!-- <td>B</td> -->
																				<td><form:input path="cisPlantMachAmt"
																						id="cisPlantMachAmt" type="text"
																						class="form-control" value="${newpnm}"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>8</td>
																				<td>Total Loan Taken</td>
																				<!-- <td>C</td> -->
																				<td><form:input path="cisEntireProjectAmt"
																						readonly="true" type="text" class="form-control"
																						value="${TotalLoan}" /></td>
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
																						value="${Totalinterest}" readonly="true" /></td>
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
														<tr class="discis-row">
															<td colspan="3"><form:textarea path="cisObserve"
																	class="form-control border-info"
																	placeholder="Observations By PICUP" rows="3"></form:textarea>
															</td>
														</tr>
														<%-- <form:form modelAttribute="disEligibleAmtCIS" name="DisEligibleAmtCISForm" method="post"> --%>
														<tr class="discis-row">
															<td colspan="3"><label class="table-heading">Eligible
																	Amount of CIS</label></td>
														</tr>
														<tr class="discis-row">


															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th>Financial Year</th>
																				<!-- <th>Interest on P&M</th> -->
																				<th>${bankcertdoc}</th>
																				<th>Proportionate interest for P&M at @
																					${additional} p.a. (CIS)</th>
																				<th>Eligible CIS (lower value of actual,
																					applied and CIS% subject to ceiling) <a
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
																				<td><form:input path="fYI" type="text"
																						name="fYI" readonly="true"
																						value="${cisincentiveDeatilsForm.yearI}"
																						class="form-control" /></td>

																				<td><form:input path="propIntRoiI" type="text"
																						name="propIntRoiI"
																						value="${cisincentiveDeatilsForm.firstYI}"
																						readonly="true" class="form-control" /></td>
																				<td><form:input path="propIntPAI" type="text"
																						name="propIntPAI" class="form-control"
																						value="${finterPnMateligi}" readonly="true" /></td>
																				<td><form:input path="actAmtIPI" type="text"
																						name="actAmtIPI" class="form-control"
																						value="${ceiling1}" readonly="true" /></td>
																			</tr>
																			<tr>
																				<td><form:input path="fYII" type="text"
																						readonly="true"
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
																				<td><form:input path="propIntRoiII" type="text"
																						readonly="true" name="propIntRoiII"
																						class="form-control"
																						value="${cisincentiveDeatilsForm.secondYI}" /></td>
																				<td><form:input path="propIntPAII" type="text"
																						name="propIntPAII" class="form-control"
																						value="${sinterPnMateligi}" readonly="true" /></td>
																				<td><form:input path="actAmtIPII" type="text"
																						name="actAmtIPII" class="form-control"
																						value="${ceiling2}" readonly="true" /></td>
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
																				<td><form:input path="propIntRoiIII"
																						type="text" name="propIntRoiIII"
																						class="form-control" readonly="true"
																						value="${cisincentiveDeatilsForm.thirdYI}" /></td>
																				<td><form:input path="propIntPAIII" type="text"
																						name="propIntPAIII" class="form-control"
																						value="${tinterPnMateligi}" readonly="true" /></td>
																				<td><form:input path="actAmtIPIII" type="text"
																						name="actAmtIPIII" class="form-control"
																						value="${ceiling3}" readonly="true" /></td>
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
																						readonly="true" name="propIntRoiIV"
																						class="form-control"
																						value="${cisincentiveDeatilsForm.fourthYI}" /></td>
																				<td><form:input path="propIntPAIV" type="text"
																						name="propIntPAIV" class="form-control"
																						value="${fourinterPnMateligi}" readonly="true" /></td>
																				<td><form:input path="actAmtIPIV" type="text"
																						name="actAmtIPIV" class="form-control"
																						value="${ceiling4}" readonly="true" /></td>
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
																				<td><form:input path="propIntRoiV" type="text"
																						readonly="true" name="propIntRoiV"
																						class="form-control"
																						value="${cisincentiveDeatilsForm.fifthYI}" /></td>
																				<td><form:input path="propIntPAV" type="text"
																						name="propIntPAV" class="form-control"
																						value="${fiftinterPnMateligi}" readonly="true" /></td>
																				<td><form:input path="actAmtIPV" type="text"
																						name="actAmtIPV" class="form-control"
																						value="${ceiling5}" readonly="true" /></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr class="discis-row">
															<td colspan="3"><form:textarea
																	path="ElgAmtcisObserv" id="ElgAmtcisObserv"
																	placeholder="Observations Comment By PICUP"
																	name="ElgAmtcisObserv" class="form-control border-info"
																	rows="2"></form:textarea></td>
														</tr>
														<tr class="disiis-row">
															<td colspan="3"><label class="table-heading">Particulars
																	of Claims for Sanction of Infrastructure Interest
																	Subsidy <small>(as provided by company)</small>
															</label></td>
														</tr>
														<tr class="disiis-row">
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th rowspan="2">Year for which subsidy Applied</th>
																				<th colspan="2">Duration/Period</th>
																				<th colspan="3">Payment made to FI/Bank during
																					the year</th>
																				<th rowspan="2" width="20%">Amount of Interest
																					Subsidy Applied</th>
																			</tr>
																			<tr>
																				<th>From</th>
																				<th>To</th>
																				<th>Total Interest on Loan</th>
																				<th>Principal</th>
																				<th>Interest on Infrastructure</th>
																			</tr>
																		</thead>
																		<tbody>

																			<tr>
																				<td><input id="year1" name="yearI"
																					onkeyup="yrOfSubsidy1()" type="text"
																					value="${iisincentiveDeatilsForm.yearI}"
																					disabled="disabled"><span id="year1Msg"
																					class="text-danger"></span></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${iisincentiveDeatilsForm.durationPeriodDtFr1}"></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${iisincentiveDeatilsForm.durationPeriodDtTo1}"></td>

																				<td><input id="firstYTI" name="firstYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrstIIS"
																					value="${iisincentiveDeatilsForm.firstYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="firstYP" name="firstYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipalIIS"
																					value="${iisincentiveDeatilsForm.firstYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="firstYI" name="firstYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterestIIS"
																					value="${iisincentiveDeatilsForm.firstYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="firstYAmtIntSubsidy"
																					name="firstYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidyIIS"
																					value="${iisincentiveDeatilsForm.firstYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>
																			</tr>
																			<tr>
																				<td><input id="year2" name="yearII"
																					onkeyup="yrOfSubsidy2()" type="text"
																					value="${iisincentiveDeatilsForm.yearII}"
																					disabled="disabled"><span id="year2Msg"
																					class="text-danger"></span></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${iisincentiveDeatilsForm.durationPeriodDtFr2}"></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${iisincentiveDeatilsForm.durationPeriodDtTo2}"></td>

																				<td><input id="secondYTI" name="secondYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrstIIS"
																					value="${iisincentiveDeatilsForm.secondYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="secondYP" name="secondYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipalIIS"
																					value="${iisincentiveDeatilsForm.secondYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="secondYI" name="secondYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterestIIS"
																					value="${iisincentiveDeatilsForm.secondYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="secondYAmtIntSubsidy"
																					name="secondYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidyIIS"
																					value="${iisincentiveDeatilsForm.secondYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>

																			</tr>
																			<tr>
																				<td><input id="year3" name="yearIII"
																					onkeyup="yrOfSubsidy3()" type="text"
																					value="${iisincentiveDeatilsForm.yearIII}"
																					disabled="disabled"><span id="year3Msg"
																					class="text-danger"></span></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${iisincentiveDeatilsForm.durationPeriodDtFr3}"></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${iisincentiveDeatilsForm.durationPeriodDtTo3}"></td>

																				<td><input id="thirdYTI" name="thirdYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrstIIS"
																					value="${iisincentiveDeatilsForm.thirdYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="thirdYP" name="thirdYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipalIIS"
																					value="${iisincentiveDeatilsForm.thirdYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="thirdYI" name="thirdYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterestIIS"
																					value="${iisincentiveDeatilsForm.thirdYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="thirdYAmtIntSubsidy"
																					name="thirdYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidyIIS"
																					value="${iisincentiveDeatilsForm.thirdYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>
																			</tr>
																			<tr>
																				<td><input id="year4" name="yearIV"
																					onkeyup="yrOfSubsidy4()" type="text"
																					value="${iisincentiveDeatilsForm.yearIV}"
																					disabled="disabled"><span id="year4Msg"
																					class="text-danger"></span></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${iisincentiveDeatilsForm.durationPeriodDtFr4}"></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${iisincentiveDeatilsForm.durationPeriodDtTo4}"></td>

																				<td><input id="fourthYTI" name="fourthYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrstIIS"
																					value="${iisincentiveDeatilsForm.fourthYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fourthYP" name="fourthYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipalIIS"
																					value="${iisincentiveDeatilsForm.fourthYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fourthYI" name="fourthYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterestIIS"
																					value="${iisincentiveDeatilsForm.fourthYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fourthYAmtIntSubsidy"
																					name="fourthYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidyIIS"
																					value="${iisincentiveDeatilsForm.fourthYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>

																			</tr>
																			<tr>
																				<td><input id="year5" name="yearV"
																					onkeyup="yrOfSubsidy5()" type="text"
																					value="${iisincentiveDeatilsForm.yearV}"
																					disabled="disabled"><span id="year5Msg"
																					class="text-danger"></span></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${iisincentiveDeatilsForm.durationPeriodDtFr5}"></td>

																				<td><input type="text" readonly="true" name=""
																					class="form-control"
																					value="${iisincentiveDeatilsForm.durationPeriodDtTo5}"></td>

																				<td><input id="fifthYTI" name="fifthYTI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlIntrstIIS"
																					value="${iisincentiveDeatilsForm.fifthYTI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fifthYP" name="fifthYP"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlPrincipalIIS"
																					value="${iisincentiveDeatilsForm.fifthYP}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fifthYI" name="fifthYI"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlInterestIIS"
																					value="${iisincentiveDeatilsForm.fifthYI}"
																					maxlength="12" readonly="readonly"></td>
																				<td><input id="fifthYAmtIntSubsidy"
																					name="fifthYAmtIntSubsidy"
																					onkeypress="return validateNumberField()"
																					placeholder="Enter Amount in INR" type="text"
																					class="form-control AllYearTotlAmtSubsidyIIS"
																					value="${iisincentiveDeatilsForm.fifthYAmtIntSubsidy}"
																					maxlength="12" readonly="readonly"></td>

																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td></td>
																				<td></td>
																				<td><input type="text"
																					class="form-control totalofIIS" readonly="true"
																					name="totalofTI" maxlength="12"></td>
																				<td><input type="text"
																					class="form-control totalofPrincipalIIS"
																					readonly="true" name="totalofPrincipal"
																					maxlength="12"></td>
																				<td><input type="text"
																					class="form-control totalofInterestIIS"
																					readonly="true" name="totalofIntrest"
																					maxlength="12"></td>
																				<td><input type="text"
																					class="form-control totalofAmtSubsidyIIS"
																					readonly="true" name="totalofAmtSubsidy"
																					maxlength="12"></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr class="disiis-row">
															<td colspan="3"><form:textarea
																	path="clamSactniisObserv" id="clamSactniisObserv"
																	placeholder="Observations Comment By PICUP"
																	name="clamSactniisObserv"
																	class="form-control border-info" rows="2"></form:textarea></td>
														</tr>
														<!-- <tr class="disiis-row">
															<td colspan="3">
																<p>
																	<span class="text-danger">*</span> Calculation of
																	interest subsidy proportionate to the pro-rata loan on
																	plant and machinery, made by the company for 2015-16
																	and 2016-17 has been provided.
																</p>
															</td>
														</tr> -->
														<tr class="disiis-row">
															<td colspan="3"><label class="table-heading">IIS-
																	Computation Methodology &amp; Amount</label> <a href="#Formula"
																data-toggle="modal" class="ml-3"><strong>(Formula)</strong></a>
															</td>
														</tr>
														<tr class="disiis-row">
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
																				<td><form:input path="doFirstLoanIIS"
																						type="Date" class="form-control" /></td>
																			</tr>

																			<tr>
																				<td>2</td>
																				<td>Date of Last Loan Sanction</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doLastLoanIIS"
																						type="Date" class="form-control" /></td>
																			</tr>

																			<tr>
																				<td>3</td>
																				<td>Date of First Disbursement</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doFirstDisIIS"
																						type="Date" class="form-control" /></td>
																			</tr>


																			<tr>
																				<td>4</td>
																				<td>Date of Last Disbursement</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doLastDisIIS" type="Date"
																						class="form-control" /></td>
																			</tr>

																			<tr>
																				<td>5</td>
																				<td>Date of Payment</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="doPaymentIIS" type="Date"
																						class="form-control" /></td>
																			</tr>

																			<tr>
																				<td>6</td>
																				<td>Total Cost of Project</td>
																				<!-- <td>A</td> -->
																				<td><form:input path="cisCostOfProjectAmt"
																						id="iisCostOfProjectAmt" type="text"
																						class="form-control" value="${TotalLoan}"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>7</td>
																				<td>Cost of Infrastructure</td>
																				<!-- <td>B</td> -->
																				<td><form:input path="cisPlantMachAmt"
																						id="iisPlantMachAmt" type="text"
																						class="form-control" value="${newinfra}"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>8</td>
																				<td>Total Loan Taken</td>
																				<!-- <td>C</td> -->
																				<td><form:input path="cisEntireProjectAmt"
																						readonly="true" type="text" class="form-control"
																						value="${TotalLoan}" /></td>
																			</tr>
																			<tr>
																				<td>9</td>
																				<td>Term Loan for Infrastructure</td>
																				<!-- <td>(B/A)*C</td> -->
																				<td><form:input path="cisTermPlantMachAmt"
																						id="iisTermPlantMachAmt" type="text"
																						class="form-control" value="${noinfra}"
																						readonly="true" /></td>
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
																						class="form-control" id="iisRoiPMAmt"
																						readonly="true" /></td>
																			</tr>
																			<tr>
																				<td>13</td>
																				<td>Proportionate amount of interest for
																					Infrastructure at @ ${additional} p.a.</td>
																				<!-- <td>(B/A)*E*(5/D)</td> -->
																				<td><form:input path="cisIntPM5Amt" type="text"
																						id="iisIntPM5Amt" class="form-control"
																						readonly="true" /></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr class="disiis-row">
															<td colspan="3"><form:textarea path="iisCMAmtObserv"
																	id="iisCMAmtObserv" placeholder="Observations By PICUP"
																	name="iisCMAmtObserv" class="form-control border-info"
																	rows="2"></form:textarea></td>
														</tr>
														<tr class="disiis-row">
															<td colspan="3"><label class="table-heading">Eligible
																	Amount of IIS</label></td>
														</tr>
														<tr>
														<tr class="disiis-row">
															<td colspan="3">
																<table class="table table-bordered">
																	<thead>
																		<tr>
																			<th>Financial Year</th>
																			<!-- Sachin -->

																			<th>Proportionate interest for Infrastructure at
																				Applicable Rate of Interest</th>
																			<th>Proportionate interest for Infrastructure at
																				@ ${additional} p.a. (IIS)</th>
																			<th>Eligible IIS (lower value of actual, claimed
																				and IIS% subject to ceiling) <a
																				href="javascript:void(0);" class="remove-row"
																				data-toggle="tooltip" title=""
																				data-original-title="(Capital Interest Subsidy @ 5% p.a. or actual interest paid whichever is
                                            less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for procurement of
                                            plant &amp; machinery, subject to an annual ceiling of Rs. 1 Caror. However, additional Capital
                                            Interest Subsidy @ 2.5% upto maximum of 7.5% shall be applicable in case of industrial undertakings having 75% equity owned by Divyang/SC/CT/Females Promoters.)"><i
																					class="fa fa-info-circle text-info"></i></a>
																			</th>
																		</tr>
																	</thead>
																	<tbody>
																		<tr>
																			<td><form:input path="iisFinYr1" type="text"
																					name="iisFinYr1" class="form-control"
																					value="${iisincentiveDeatilsForm.yearI}"
																					readonly="true" /></td>

																			<td><form:input path="propIntInfra1" type="text"
																					name="propIntInfra1" class="form-control"
																					value="${iisincentiveDeatilsForm.firstYI}"
																					readonly="true" /></td>
																			<td><form:input path="propIntInfraPA1"
																					type="text" name="propIntInfraPA1"
																					class="form-control" value="${finterPnMateligiiis}"
																					readonly="true" /></td>
																			<td><form:input path="eligibleIIS1" type="text"
																					name="eligibleIIS1" class="form-control"
																					value="${ceiling1iis}" readonly="true" /></td>
																		</tr>
																		<tr>
																			<td><form:input path="iisFinYr2" type="text"
																					name="iisFinYr2" class="form-control"
																					value="${iisincentiveDeatilsForm.yearII}"
																					readonly="true" /></td>
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
																					name="propIntInfra2" class="form-control"
																					value="${iisincentiveDeatilsForm.secondYI}"
																					readonly="true" /></td>
																			<td><form:input path="propIntInfraPA2"
																					type="text" name="propIntInfraPA2"
																					class="form-control" value="${sinterPnMateligiiis}"
																					readonly="true" /></td>
																			<td><form:input path="eligibleIIS2" type="text"
																					name="eligibleIIS2" class="form-control"
																					value="${ceiling2iis}" readonly="true" /></td>
																		</tr>
																		<tr>
																			<td><form:input path="iisFinYr3" type="text"
																					name="iisFinYr3" class="form-control"
																					value="${iisincentiveDeatilsForm.yearIII}"
																					readonly="true" /></td>
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
																					name="propIntInfra3" class="form-control"
																					value="${iisincentiveDeatilsForm.thirdYI}"
																					readonly="true" /></td>
																			<td><form:input path="propIntInfraPA3"
																					type="text" name="propIntInfraPA3"
																					class="form-control" value="${tinterPnMateligiiis}"
																					readonly="true" /></td>
																			<td><form:input path="eligibleIIS3" type="text"
																					name="eligibleIIS3" class="form-control"
																					value="${ceiling3iis}" readonly="true" /></td>
																		</tr>
																		<tr>
																			<td><form:input path="iisFinYr4" type="text"
																					name="iisFinYr4" class="form-control"
																					value="${iisincentiveDeatilsForm.yearIV}"
																					readonly="true" /></td>
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
																					name="propIntInfra4" class="form-control"
																					value="${iisincentiveDeatilsForm.fourthYI}"
																					readonly="true" /></td>
																			<td><form:input path="propIntInfraPA4"
																					type="text" name="propIntInfraPA4"
																					class="form-control"
																					value="${fourinterPnMateligiiis}" readonly="true" /></td>
																			<td><form:input path="eligibleIIS4" type="text"
																					name="eligibleIIS4" class="form-control"
																					value="${ceiling4iis}" readonly="true" /></td>
																		</tr>
																		<tr>
																			<td><form:input path="iisFinYr5" type="text"
																					name="iisFinYr5" class="form-control"
																					value="${iisincentiveDeatilsForm.yearV}"
																					readonly="true" /></td>
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
																					name="propIntInfra5" class="form-control"
																					value="${iisincentiveDeatilsForm.fifthYI}"
																					readonly="true" /></td>
																			<td><form:input path="propIntInfraPA5"
																					type="text" name="propIntInfraPA5"
																					class="form-control"
																					value="${fiftinterPnMateligiiis}" readonly="true" /></td>
																			<td><form:input path="eligibleIIS5" type="text"
																					name="eligibleIIS5" class="form-control"
																					value="${ceiling5iis}" readonly="true" /></td>
																		</tr>

																	</tbody>
																</table>
															</td>
														</tr>
														<tr class="disiis-row">
															<td colspan="3"><form:textarea
																	path="eligAmtIisObserv"
																	class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2"></form:textarea>
															</td>
														</tr>

														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<tbody>
																			<tr>
																				<td><label><strong>Confirmation
																							by Bank(If any)</strong></label> <form:textarea
																						path="confProvbyBank" id="confProvbyBank"
																						placeholder="Confirmation Comment By PICUP"
																						name="confProvbyBank"
																						class="form-control border-info" rows="4"></form:textarea>
																					<label class="mt-2"><strong>Upload
																							Related Documents</strong></label>
																					<div class="custom-file">
																						<input type="file"
																							class="custom-file-input user-file"
																							maxlength="10" id="confProvbyBankDoc"
																							name="confProvbyBankDoc"> <label
																							class="custom-file-label file"
																							id="chooseFileConfProvbyBankDoc"
																							for="confProvbyBankDoc">Choose File</label>
																					</div></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<!-- New Changes for EPS-Stamp Duty ExemptionMandi other incentive -->

														<tr class="disepf-row">
															<td colspan="3"><label class="table-heading">EPF
																	Computation and Eligibility</label></td>
														</tr>

														<tr class="disepf-row">
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
																				<%-- <td><form:input path="epfComputFinYr1"
																						type="text" class="form-control"
																						name="epfComputFinYr1" /></td> --%>
																				<td><form:select path="epfComputFinYr1"
																						type="text" placeholder="Enter Year"
																						class="form-control  financial-year"
																						id="epfComputFinYr1" name="epfComputFinYr1">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="${epfyear1}"></form:option>
																					</form:select> <span id="year1Msg" class="text-danger"></span></td>

																				<td><form:input path="employerContributionEPF1"
																						type="text"
																						class="form-control  is-numeric epfclaim"
																						name="employerContributionEPF1"
																						id="employerContributionEPF1" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="reimEligibility1"
																						type="text"
																						class="form-control is-numeric epfeligible epfclaimreim"
																						name="reimEligibility1" id="reimEligibility1"
																						readonly="true" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<%-- <td><form:input path="epfComputFinYr2"
																						type="text" class="form-control"
																						name="epfComputFinYr2" /></td> --%>

																				<td><form:select path="epfComputFinYr2"
																						type="text" placeholder="Enter Year"
																						class="form-control  financial-year"
																						id="epfComputFinYr1" name="epfComputFinYr1">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="${epfyear2}"></form:option>
																					</form:select> <span id="year2Msg" class="text-danger"></span></td>

																				<td><form:input path="employerContributionEPF2"
																						type="text"
																						class="form-control is-numeric epfclaim"
																						name="employerContributionEPF2"
																						id="employerContributionEPF2" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="reimEligibility2"
																						type="text"
																						class="form-control is-numeric epfeligible epfclaimreim"
																						name="reimEligibility2" id="reimEligibility2"
																						readonly="true" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<%-- <td><form:input path="epfComputFinYr3"
																						type="text" class="form-control"
																						name="epfComputFinYr3" /></td> --%>

																				<td><form:select path="epfComputFinYr3"
																						type="text" placeholder="Enter Year"
																						class="form-control  financial-year"
																						id="epfComputFinYr1" name="epfComputFinYr1">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="${epfyear3}"></form:option>
																					</form:select> <span id="year3Msg" class="text-danger"></span></td>

																				<td><form:input path="employerContributionEPF3"
																						type="text"
																						class="form-control is-numeric epfclaim"
																						name="employerContributionEPF3"
																						id="employerContributionEPF3" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="reimEligibility3"
																						type="text"
																						class="form-control is-numeric epfeligible epfclaimreim"
																						name="reimEligibility3" id="reimEligibility3"
																						readonly="true" /> <small class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><form:input
																						path="ttlEmployerContributionEPF" type="text"
																						class="form-control epfclaimtotal"
																						id="employerContributionEPF4" readonly="true"
																						name="ttlEmployerContributionEPF" /></td>
																				<td><form:input path="ttlReimEligibility"
																						readonly="true" type="text"
																						class="form-control epfreimtotal"
																						name="ttlReimEligibility" id="reimEligibility4" /></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr class="disepf-row">
															<td colspan="3"><form:textarea
																	path="epfComputEligObserv"
																	class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2"></form:textarea>
															</td>
														</tr>

														<tr class="disstamp-row">
															<td colspan="3"><label class="table-heading">Stamp
																	Duty Exemption or Reimbursement Computation</label></td>
														</tr>

														<tr class="disstamp-row">
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<!-- <th>Financial Year</th> -->
																				<!-- <th>Admissible Stamp Duty</th> -->
																				<th>Claimed Stamp Duty Reimbursement Amount</th>
																				<th>Stamp Duty Reimbursement Eligibility</th>
																				<!-- <th>Stamp Duty Reimbursement Availed</th> -->
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<%-- <td><form:input path="computationFinYr" type="text" class="form-control" name="computationFinYr"/></td> --%>
																				<%-- <td><form:input path="admissibleStampDuty" type="text" class="form-control" name="admissibleStampDuty"/></td> --%>
																				<td><form:input path="claimStampDutyReimAmt"
																						id="claimStampDutyReimAmt" type="text"
																						class="form-control" name="claimStampDutyReimAmt" /></td>
																				<td><form:input path="stampDutyReimElig"
																						id="stampDutyReimElig" type="text"
																						class="form-control" name="stampDutyReimElig"
																						readonly="true" /></td>
																				<%-- <td><form:input path="stampDutyReimAvail" type="text" class="form-control" name="stampDutyReimAvail"/></td> --%>
																			</tr>
																			<%-- <tr>
																				<td><strong>Total</strong></td>
																				<td><form:input path="ttlAdmissibleStampDuty" type="text" class="form-control" name="ttlAdmissibleStampDuty"/></td>
																				<td><form:input path="ttlClaimStampDutyReimAmt" type="text" class="form-control" name="ttlClaimStampDutyReimAmt"/></td>
																				<td><form:input path="ttlStampDutyReimElig" type="text" class="form-control" name="ttlStampDutyReimElig"/></td>
																				<td><form:input path="ttlStampDutyReimAvail" type="text" class="form-control" name="ttlStampDutyReimAvail"/></td>
																			</tr> --%>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr class="disstamp-row">
															<td colspan="3"><form:textarea
																	path="stampDutyExeObserv"
																	class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2"></form:textarea>
															</td>
														</tr>

														<tr class="diselect-row">
															<td colspan="3"><label class="table-heading">Electricity
																	Duty Exemption</label></td>
														</tr>

														<tr class="diselect-row">
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th>Financial Year</th>
																				<th>Availed ED Exemption - Amount</th>
																				<th>Eligible ED Exemption - Amount</th>
																				<th>Availed ED Exemption - Captive Power Plant</th>
																				<th>Eligible ED Exemption - Captive Power Plant</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td><form:select
																						path="electricityDutyExeFinYr1" type="text"
																						class="form-control financial-year"
																						id="efinancialYear1"
																						name="electricityDutyExeFinYr1">
																						<form:option value="">-Select Financial Year-</form:option>
																						<form:option value="" />
																					</form:select></td>
																				<td><form:input path="availedEDExeUnits1"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityUnits"
																						name="availedEDExeUnits1" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibedEDExeUnits1"
																						type="text" maxlength="12"
																						class="form-control is-numeric"
																						name="eligibedEDExeUnits1" /> <small
																					class="words text-info"></small></td>

																				<td><form:input path="availedEDExeCPP1"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityCPP"
																						name="availedEDExeCPP1" /> <small
																					class="words text-info"></small></td>

																				<td><form:input path="eligibEDExeCPP1"
																						type="text" maxlength="12"
																						class="form-control is-numeric "
																						name="eligibEDExeCPP1" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><form:select
																						path="electricityDutyExeFinYr2" type="text"
																						class="form-control financial-year"
																						id="efinancialYear2"
																						name="electricityDutyExeFinYr2">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="" />
																					</form:select></td>
																				<td><form:input path="availedEDExeUnits2"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityUnits"
																						name="availedEDExeUnits2" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibedEDExeUnits2"
																						type="text" maxlength="12"
																						class="form-control is-numeric"
																						name="eligibedEDExeUnits2" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="availedEDExeCPP2"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityCPP"
																						name="availedEDExeCPP2" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibEDExeCPP2"
																						type="text" maxlength="12"
																						class="form-control is-numeric "
																						name="eligibEDExeCPP2" /> <small
																					class="words text-info"></small></td>
																			</tr>

																			<tr>
																				<td><form:select
																						path="electricityDutyExeFinYr3" type="text"
																						class="form-control financial-year"
																						id="efinancialYear3"
																						name="electricityDutyExeFinYr3">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="" />
																					</form:select></td>

																				<td><form:input path="availedEDExeUnits3"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityUnits"
																						name="availedEDExeUnits3" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibedEDExeUnits3"
																						type="text" maxlength="12"
																						class="form-control is-numeric"
																						name="eligibedEDExeUnits3" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="availedEDExeCPP3"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityCPP"
																						name="availedEDExeCPP3" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibEDExeCPP3"
																						type="text" maxlength="12"
																						class="form-control is-numeric "
																						name="eligibEDExeCPP3" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><form:select
																						path="electricityDutyExeFinYr4" type="text"
																						class="form-control financial-year"
																						id="efinancialYear4"
																						name="electricityDutyExeFinYr4">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="" />
																					</form:select></td>
																				<%-- <td><form:input path="electricityDutyExeFinYr4" type="text" class="form-control " name="electricityDutyExeFinYr4"/></td> --%>
																				<td><form:input path="availedEDExeUnits4"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityUnits"
																						name="availedEDExeUnits4" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibedEDExeUnits4"
																						type="text" maxlength="12"
																						class="form-control is-numeric"
																						name="eligibedEDExeUnits4" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="availedEDExeCPP4"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityCPP"
																						name="availedEDExeCPP4" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibEDExeCPP4"
																						type="text" maxlength="12"
																						class="form-control is-numeric "
																						name="eligibEDExeCPP4" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><form:select
																						path="electricityDutyExeFinYr5" type="text"
																						class="form-control financial-year"
																						id="efinancialYear5"
																						name="electricityDutyExeFinYr5">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="" />
																					</form:select></td>
																				<td><form:input path="availedEDExeUnits5"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityUnits"
																						name="availedEDExeUnits5" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibedEDExeUnits5"
																						type="text" maxlength="12"
																						class="form-control is-numeric"
																						name="eligibedEDExeUnits5" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="availedEDExeCPP5"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityCPP"
																						name="availedEDExeCPP5" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibEDExeCPP5"
																						type="text" maxlength="12"
																						class="form-control is-numeric "
																						name="eligibEDExeCPP5" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><form:select
																						path="electricityDutyExeFinYr6" type="text"
																						class="form-control financial-year"
																						id="efinancialYear6"
																						name="electricityDutyExeFinYr6">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="" />
																					</form:select></td>
																				<td><form:input path="availedEDExeUnits6"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityUnits"
																						name="availedEDExeUnits6" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibedEDExeUnits6"
																						type="text" maxlength="12"
																						class="form-control is-numeric"
																						name="eligibedEDExeUnits6" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="availedEDExeCPP6"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityCPP"
																						name="availedEDExeCPP6" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibEDExeCPP6"
																						type="text" maxlength="12"
																						class="form-control is-numeric "
																						name="eligibEDExeCPP6" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><form:select
																						path="electricityDutyExeFinYr7" type="text"
																						class="form-control financial-year"
																						id="efinancialYear7"
																						name="electricityDutyExeFinYr">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="" />
																					</form:select></td>
																				<td><form:input path="availedEDExeUnits7"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityUnits"
																						name="availedEDExeUnits7" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibedEDExeUnits7"
																						type="text" maxlength="12"
																						class="form-control is-numeric"
																						name="eligibedEDExeUnits7" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="availedEDExeCPP7"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityCPP"
																						name="availedEDExeCPP7" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibEDExeCPP7"
																						type="text" maxlength="12"
																						class="form-control is-numeric "
																						name="eligibEDExeCPP7" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>


																				<td><form:select
																						path="electricityDutyExeFinYr8" type="text"
																						class="form-control financial-year"
																						id="efinancialYear8"
																						name="electricityDutyExeFinYr8">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="" />
																					</form:select></td>


																				<%-- <td><form:select path="electricityDutyExeFinYr8" type="text" class="form-control financial-year" id="efinancialYear8" name="electricityDutyExeFinYr8">
																	                     <form:option value="" selected="selected">-Select Financial Year-</form:option>
																	                      <form:option value="" />
																               </form:select></td> --%>
																				<%--  <td><form:input path="electricityDutyExeFinYr8" type="text" class="form-control " name="electricityDutyExeFinYr8"/></td> --%>
																				<td><form:input path="availedEDExeUnits8"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityUnits"
																						name="availedEDExeUnits8" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibedEDExeUnits8"
																						type="text" maxlength="12"
																						class="form-control is-numeric"
																						name="eligibedEDExeUnits8" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="availedEDExeCPP8"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityCPP"
																						name="availedEDExeCPP8" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibEDExeCPP8"
																						type="text" maxlength="12"
																						class="form-control is-numeric "
																						name="eligibEDExeCPP8" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>

																				<td><form:select
																						path="electricityDutyExeFinYr9" type="text"
																						class="form-control financial-year"
																						id="efinancialYear9"
																						name="electricityDutyExeFinYr9">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="" />
																					</form:select></td>


																				<%-- <td><form:select path="electricityDutyExeFinYr9" type="text" class="form-control financial-year" id="efinancialYear9" name="electricityDutyExeFinYr9">
																	                     <form:option value="" selected="selected">-Select Financial Year-</form:option>
																	                      <form:option value="" />
																               </form:select></td> --%>
																				<%-- <td><form:input path="electricityDutyExeFinYr9"
																						type="text" class="form-control "
																						name="electricityDutyExeFinYr9" /></td> --%>
																				<td><form:input path="availedEDExeUnits9"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityUnits"
																						name="availedEDExeUnits9" /> <small
																					class="words text-info"></small></td>

																				<td><form:input path="eligibedEDExeUnits9"
																						type="text" maxlength="12"
																						class="form-control is-numeric"
																						name="eligibedEDExeUnits9" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="availedEDExeCPP9"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityCPP"
																						name="availedEDExeCPP9" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibEDExeCPP9"
																						type="text" maxlength="12"
																						class="form-control is-numeric "
																						name="eligibEDExeCPP9" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<%-- <td><form:select path="electricityDutyExeFinYr10" type="text" class="form-control financial-year" id="efinancialYear10" name="electricityDutyExeFinYr10">
																	                     <form:option value="" selected="selected">-Select Financial Year-</form:option>
																	                      <form:option value="" />
																               </form:select></td> --%>

																				<td><form:select
																						path="electricityDutyExeFinYr10" type="text"
																						class="form-control financial-year"
																						id="efinancialYear10"
																						name="electricityDutyExeFinYr10">
																						<form:option value="" selected="selected">-Select Financial Year-</form:option>
																						<form:option value="" />
																					</form:select></td>
																				<%-- <td><form:input
																						path="electricityDutyExeFinYr10" type="text"
																						class="form-control financial-year" 
																						name="electricityDutyExeFinYr10" /></td> --%>
																				<td><form:input path="availedEDExeUnits10"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityUnits"
																						name="availedEDExeUnits10" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibedEDExeUnits10"
																						type="text" maxlength="12"
																						class="form-control is-numeric"
																						name="eligibedEDExeUnits10" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="availedEDExeCPP10"
																						type="text" maxlength="12"
																						class="form-control is-numeric electricityCPP"
																						name="availedEDExeCPP10" /> <small
																					class="words text-info"></small></td>
																				<td><form:input path="eligibEDExeCPP10"
																						type="text" maxlength="12"
																						class="form-control is-numeric "
																						name="eligibEDExeCPP10" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><form:input path="ttlAvailedEDExeUnits"
																						type="text" readonly="true"
																						class="form-control electricityUnitsTotal"
																						name="ttlAvailedEDExeUnits" /></td>

																				<td></td>


																				<td><form:input path="ttlAvailedEDExeCPP"
																						type="text" readonly="true"
																						class="form-control electricityCPPTotal"
																						name="ttlAvailedEDExeCPP" /></td>
																				<td></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr class="diselect-row">
															<td colspan="3"><form:textarea
																	path="electricityDutyExeObserv"
																	class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2"></form:textarea>
															</td>
														</tr>

														<tr class="dismandi-row">
															<td colspan="3"><label class="table-heading">Mandi
																	Fee Exemption</label></td>
														</tr>

														<tr class="dismandi-row">
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
																				<td><form:input path="mandiFeeExeFinYr1"
																						type="text" class="form-control"
																						name="mandiFeeExeFinYr1" /></td>
																				<td><form:input path="AvailMandiFeeExe1"
																						type="text" maxlength="12"
																						class="form-control is-numeric availedMandiFee "
																						name="AvailMandiFeeExe1" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><form:input path="mandiFeeExeFinYr2"
																						type="text" class="form-control"
																						name="mandiFeeExeFinYr2" /></td>
																				<td><form:input path="AvailMandiFeeExe2"
																						type="text" maxlength="12"
																						class="form-control is-numeric availedMandiFee"
																						name="AvailMandiFeeExe2" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><form:input path="mandiFeeExeFinYr3"
																						type="text" class="form-control"
																						name="mandiFeeExeFinYr3" /></td>
																				<td><form:input path="AvailMandiFeeExe3"
																						type="text" maxlength="12"
																						class="form-control is-numeric availedMandiFee"
																						name="AvailMandiFeeExe3" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><form:input path="mandiFeeExeFinYr4"
																						type="text" class="form-control"
																						name="mandiFeeExeFinYr4" /></td>
																				<td><form:input path="AvailMandiFeeExe4"
																						type="text" maxlength="12"
																						class="form-control is-numeric availedMandiFee"
																						name="AvailMandiFeeExe4" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><form:input path="mandiFeeExeFinYr5"
																						type="text" class="form-control"
																						name="mandiFeeExeFinYr5" /></td>
																				<td><form:input path="AvailMandiFeeExe5"
																						type="text" maxlength="12"
																						class="form-control is-numeric availedMandiFee"
																						name="AvailMandiFeeExe5" /> <small
																					class="words text-info"></small></td>
																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><form:input path="ttlAvailMandiFeeExe"
																						type="text"
																						class="form-control availedMandiFeeTotal"
																						name="ttlAvailMandiFeeExe" /></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr class="dismandi-row">
															<td colspan="3"><form:textarea
																	path="mandiFeeExeObserv"
																	class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2"></form:textarea>
															</td>
														</tr>

														<tr class="disallow-row">
															<td colspan="3"><label class="table-heading">Reimbursement
																	of Disallowed Input Tax <a href="javascript:void(0);"
																	class="remove-row" data-toggle="tooltip" title=""
																	data-original-title="Reimbursement of Disallowed Input Tax Credit on plant, building materials, and other capital goods."><i
																		class="fa fa-info-circle text-info"></i></a>
															</label></td>
														</tr>

														<tr class="disallow-row">
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
																				<td><form:input path="reimDisallowFinYr"
																						type="text" class="form-control"
																						name="reimDisallowFinYr" /></td>
																				<td><form:input path="reimDisallowAvailAmt"
																						type="text" class="form-control"
																						name="reimDisallowAvailAmt" /></td>
																			</tr>
																			<tr>
																				<td><strong>Total</strong></td>
																				<td><form:input path="ttlReimDisallowAvailAmt"
																						type="text" class="form-control"
																						name="ttlReimDisallowAvailAmt" /></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>


														<tr class="disallow-row">
															<td colspan="3"><form:textarea
																	path="reimDisallowObserv"
																	class="form-control border-info"
																	placeholder="Observations By PICUP" rows="2"></form:textarea>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>

								<hr class="mt-2">
								<div class="isf-form mt-4">
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
																			id="ttlEligAmt" class="form-control is-numeric" /> <small
																		class="words text-info"></small></td>
																</tr>
																<tr>
																	<td>B.</td>
																	<td>Amount proposed for disbursement</td>
																	<td><form:input path="propDisbAmt" type="text"
																			class="form-control is-numeric" /> <small
																		class="words text-info"></small></td>
																</tr>
																<tr>
																	<td>C.</td>
																	<td>Balance amount of eligibility of benefits
																		after proposed disbursal</td>
																	<td><form:input path="eligBenefitsAmt" type="text"
																			class="form-control is-numeric" /> <small
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
													<td><form:input path="disbEffDate" type="text"
															placeholder="Enter Amount in INR"
															class="form-control is-numeric" maxlength="12" /><small
														class="words text-info"></small></td>
												</tr>
												<tr>
													<td colspan="2"><form:textarea path="disbEffComments"
															class="form-control border-info"
															placeholder="Comments (if any)" rows="4" /></td>
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
															class="form-control is-numeric" /><small
														class="words text-info"></small></td>
												</tr>
												<tr>
													<td>Date of Admissibility of Incentives</td>
													<td><form:input path="dateAdmissibilityInc"
															type="Date" class="form-control" /></td>
												</tr>
												<tr>
													<td colspan="2"><label class="table-heading">Compliance
															of Conditions</label></td>
												</tr>
												<tr>
													<td colspan="2"><form:textarea
															path="complCondiComments"
															class="form-control border-info"
															placeholder="Comments (if any)" rows="4" /></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>




							<hr class="mt-4 mb-4">

							<div class="row">
								<div class="col-sm-12">
									<div class="custom-control custom-checkbox mb-1">
										<input type="checkbox" class="custom-control-input"
											id="verified" name="Capital-Interest-Subsidy"> <label
											class="custom-control-label" for="verified"
											id="verifiedChecked">I have read and verified the
											entire form carefully</label>
									</div>
								</div>
							</div>

							<hr class="mt-4 mb-3">

							<div class="row mt-4">
								<div class="col-sm-12 text-center">


									<button type="submit" id="button1"
										class="btn btn-outline-secondary evaluate-btn btn-sm mb-3"
										formaction="disApplicationEvaluation"
										onclick="return enableButton2()">Evaluation Completed</button>
									<!--  <button type="button" disabled="disabled" id="button5"
										onclick="return sendAcknowledgeInvestor()"
										class="btn btn-outline-info btn-sm mb-3">Send
										Acknowledgement to Investor</button>-->
									<a href="#RaiseQuery"
										class="btn btn-outline-danger btn-sm mb-3 "
										data-toggle="modal">Raise Query</a>

									<button type="submit" id="button2"
										class="btn btn-outline-info btn-sm mb-3 evaluate-btn"
										formaction="disApplicationAgendaNote"
										onclick="return includeAgendaNote()">Include
										Application in Agenda Note</button>

									<button data-target="#RejectApplication" type="button"
										class="btn btn-outline-danger btn-sm mb-3" id="reject"
										data-toggle="modal">Reject</button>



								</div>
							</div>

							<hr class="mt-2">

							<div class="row mb-4">
								<div class="col-sm-5">
									<a
										href="./viewApplicationDetailsDis?applicantId=<%=session.getAttribute("appId")%>"
										class="common-default-btn mt-3">Back</a>
								</div>
								<div class="col-sm-7 text-right">
									<a href="javacript:void(0);" class="common-btn mt-3"><i
										class="fa fa-edit"></i> Edit</a>
									<form:button onclick="return Saveconfirm()"
										class="common-btn mt-3">Save</form:button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<footer class="dashboard-footer">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<div class="social-icons">
						<ul>
							<li><a href="#"><i class="fa fa-facebook-f"></i></a></li>
							<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							<li><a href="#"><i class="fa fa-youtube"></i></a></li>
						</ul>
					</div>
				</div>
				<div class="col-sm-12">
					<div class="footer-menu">
						<ul>
							<li><a href="index.html" target="_blank">Home</a></li>
							<li><a
								href="http://udyogbandhu.com/topics.aspx?mid=About%20us"
								target="_blank">About Us</a></li>
							<li><a
								href="http://udyogbandhu.com/topics.aspx?mid=Disclaimer"
								target="_blank">Disclaimer</a></li>
							<li><a
								href="http://udyogbandhu.com/topics.aspx?mid=General%20Terms%20And%20Conditions"
								target="_blank">General Terms And Conditions</a></li>
							<li><a
								href="http://udyogbandhu.com/topics.aspx?mid=Privacy%20Policy"
								target="_blank">Privacy Policy</a></li>
							<li><a
								href="http://udyogbandhu.com/topics.aspx?mid=Refund%20Policy"
								target="_blank">Refund Policy</a></li>
							<li><a
								href="http://udyogbandhu.com/topics.aspx?mid=Delivery%20Policy"
								target="_blank">Delivery Policy</a></li>
							<li><a
								href="http://udyogbandhu.com/topics.aspx?mid=Contact%20Us"
								target="_blank">Contact Us</a></li>
						</ul>
					</div>
				</div>
				<div class="col-sm-12">
					<div class="row">
						<div class="col-sm-3">
							<div class="nic-footer-logo">
								<img src="images/nic-logo.png" alt="NIC Logo">
							</div>
						</div>
						<div class="col-sm-6">
							<div class="copyright-text">
								<p>Â© 2020 - IT Solution powered by National Informatics
									Centre Uttar Pradesh State Unit</p>
								<p>Designed and Developed by National Informatics Centre (
									NIC )</p>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="page-visit"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</footer>
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
												<td>Total Loan Taken = <span class="text-danger">C</span></td>
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


		function VIandVIITotal1() {
			var sum = 0;
			$(".VIandVII1").each(function() {
				sum += +$(this).val();
			});
			$(".VIandVIITotal1").val(sum);
		}

	 	$(document).ready(VIandVIITotal1);	
		$(document).on("keyup", VIandVIITotal1);		
		$(document).on("change", VIandVIITotal1); 
		
		function VIandVIITotal2() {
			var sum = 0;
			$(".VIandVII2").each(function() {
				sum += +$(this).val();
			});
			$(".VIandVIITotal2").val(sum);
		}

	 	$(document).ready(VIandVIITotal2);	
		$(document).on("keyup", VIandVIITotal2);		
		$(document).on("change", VIandVIITotal2); 

		function VIandVIITotal3() {
			var sum = 0;
			$(".VIandVII3").each(function() {
				sum += +$(this).val();
			});
			$(".VIandVIITotal3").val(sum);
		}

	 	$(document).ready(VIandVIITotal3);	
		$(document).on("keyup", VIandVIITotal3);		
		$(document).on("change", VIandVIITotal3); 

		function VIandVIITotal4() {
			var sum = 0;
			$(".VIandVII4").each(function() {
				sum += +$(this).val();
			});
			$(".VIandVIITotal4").val(sum);
		}

	 	$(document).ready(VIandVIITotal4);	
		$(document).on("keyup", VIandVIITotal4);		
		$(document).on("change", VIandVIITotal4); 

		function VIandVIITotal5() {
			var sum = 0;
			$(".VIandVII5").each(function() {
				sum += +$(this).val();
			});
			$(".VIandVIITotal5").val(sum);
		}

	 	$(document).ready(VIandVIITotal5);	
		$(document).on("keyup", VIandVIITotal5);		
		$(document).on("change", VIandVIITotal5); 

		function VIandVIITotal6() {
			var sum = 0;
			$(".VIandVII6").each(function() {
				sum += +$(this).val();
			});
			$(".VIandVIITotal6").val(sum);
		}

	 	$(document).ready(VIandVIITotal6);	
		$(document).on("keyup", VIandVIITotal6);		
		$(document).on("change", VIandVIITotal6); 
		
		function VIandVIITotal7() {
			var sum = 0;
			$(".VIandVII7").each(function() {
				sum += +$(this).val();
			});
			$(".VIandVIITotal7").val(sum);
		}

	 	$(document).ready(VIandVIITotal7);	
		$(document).on("keyup", VIandVIITotal7);		
		$(document).on("change", VIandVIITotal7); 

		function VIandVIITotal8() {
			var sum = 0;
			$(".VIandVII8").each(function() {
				sum += +$(this).val();
			});
			$(".VIandVIITotal8").val(sum);
		}

	 	$(document).ready(VIandVIITotal8);	
		$(document).on("keyup", VIandVIITotal8);		
		$(document).on("change", VIandVIITotal8); 

		function VIandVIITotal9() {
			var sum = 0;
			$(".VIandVII9").each(function() {
				sum += +$(this).val();
			});
			$(".VIandVIITotal9").val(sum);
		}

	 	$(document).ready(VIandVIITotal9);	
		$(document).on("keyup", VIandVIITotal9);		
		$(document).on("change", VIandVIITotal9); 
		
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
			$(".statutoryAudit1").each(function() {
				sum += +$(this).val();
			});
			$(".statutoryAuditTotal1", this).val(sum);
		}

		$(document).ready(statutoryAuditerTotal);
		$(document).on("keyup", statutoryAuditerTotal);
		$(document).on("change", statutoryAuditerTotal);

		function statutoryAuditerTotal2() {
			var sum = 0;
			$(".statutoryAudit2").each(function() {
				sum += +$(this).val();
			});
			$(".statutoryAuditTotal2", this).val(sum);
		}

		$(document).ready(statutoryAuditerTotal2);
		$(document).on("keyup", statutoryAuditerTotal2);
		$(document).on("change", statutoryAuditerTotal2);

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


		function certifiAuditTotal2() {
			var sum = 0;
			$(".certificateAudit2").each(function() {
				sum += +$(this).val();
			});
			$(".certificateAuditTotal2", this).val(sum);
		}

		$(document).ready(certifiAuditTotal2);
		$(document).on("keyup", certifiAuditTotal2);
		$(document).on("change", certifiAuditTotal2);

		
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
			$(".subkasubTtlStatutory").each(function() {
				sum += +$(this).val();
			});
			$(".subkasubTtlStatutoryTotal", this).val(sum);
		}

		$(document).ready(subTtlStatutoryTotal);
		$(document).on("keyup", subTtlStatutoryTotal);
		$(document).on("change", subTtlStatutoryTotal);

		function subTtlcertificateAuditTotal() {
			var sum = 0;
			$(".subkasubcertificateAudit").each(function() {
				sum += +$(this).val();
			});
			$(".subkasubcertificateAuditTotal", this).val(sum);
		}

		$(document).ready(subTtlcertificateAuditTotal);
		$(document).on("keyup", subTtlcertificateAuditTotal);
		$(document).on("change", subTtlcertificateAuditTotal);

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

		function FinaStaAudittTotal1() {
			var sum = 0;
			$(".FinaStaAuditt").each(function() {
				sum += +$(this).val();
			});
			$(".finTttldate1", this).val(sum);
		}

		$(document).ready(FinaStaAudittTotal1);
		$(document).on("keyup", FinaStaAudittTotal1);
		$(document).on("change", FinaStaAudittTotal1);

		

		function FinaStaAudittTotal() {
			var sum = 0;
			$(".FinaStaAudit12").each(function() {
				sum += +$(this).val();
			});
			$(".finTttlproddate", this).val(sum);
		}

		$(document).ready(FinaStaAudittTotal);
		$(document).on("keyup", FinaStaAudittTotal);
		$(document).on("change", FinaStaAudittTotal);

		function electricityUnitsTtl() {
			var sum = 0;
			$(".electricityUnits").each(function() {
				sum += +$(this).val();
			});
			$(".electricityUnitsTotal", this).val(sum);
		}

		$(document).ready(electricityUnitsTtl);
		$(document).on("keyup", electricityUnitsTtl);
		$(document).on("change", electricityUnitsTtl);

		function electricityCPPTtl() {
			var sum = 0;
			$(".electricityCPP").each(function() {
				sum += +$(this).val();
			});
			$(".electricityCPPTotal", this).val(sum);
		}

		$(document).ready(electricityCPPTtl);
		$(document).on("keyup", electricityCPPTtl);
		$(document).on("change", electricityCPPTtl);

		function availedMandiFeeTtl() {
			var sum = 0;
			$(".availedMandiFee").each(function() {
				sum += +$(this).val();
			});
			$(".availedMandiFeeTotal", this).val(sum);
		}

		$(document).ready(availedMandiFeeTtl);
		$(document).on("keyup", availedMandiFeeTtl);
		$(document).on("change", availedMandiFeeTtl);
	
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

		function AllYearTotalIntrestIIS() {
			var sum = 0;
			$(".AllYearTotlIntrstIIS").each(function() {
				sum += +$(this).val();
			});
			$(".totalofIIS", this).val(sum);
		}

		$(document).ready(AllYearTotalIntrestIIS);
		$(document).on("keyup", AllYearTotalIntrestIIS);
		$(document).on("change", AllYearTotalIntrestIIS);

		function AllYearTotalPrincipalIIS() {
			var sum = 0;
			$(".AllYearTotlPrincipalIIS").each(function() {
				sum += +$(this).val();
			});
			$(".totalofPrincipalIIS", this).val(sum);
		}

		$(document).ready(AllYearTotalPrincipalIIS);
		$(document).on("keyup", AllYearTotalPrincipalIIS);
		$(document).on("change", AllYearTotalPrincipalIIS);

		function AllYearTotalInterestIIS() {
			var sum = 0;
			$(".AllYearTotlInterestIIS").each(function() {
				sum += +$(this).val();
			});
			$(".totalofInterestIIS", this).val(sum);
		}

		$(document).ready(AllYearTotalInterestIIS);
		$(document).on("keyup", AllYearTotalInterestIIS);
		$(document).on("change", AllYearTotalInterestIIS);

		function AllYearTotalAmtSubsidyIIS() {
			var sum = 0;
			$(".AllYearTotlAmtSubsidyIIS").each(function() {
				sum += +$(this).val();
			});
			$(".totalofAmtSubsidyIIS", this).val(sum);

		}

		$(document).ready(AllYearTotalAmtSubsidyIIS);
		$(document).on("keyup", AllYearTotalAmtSubsidyIIS);
		$(document).on("change", AllYearTotalAmtSubsidyIIS);
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

	function showsgst() 
		{
		var natureOfProject = '${natureOfProject}';
		if (natureOfProject == 'NewProject' || natureOfProject=='diversification') 
			{
			$(".expensionSGST").hide();
			}
		}
		$(document).ready(showsgst);

	</script>

	<script type="text/javascript">
	function showsCIS() 
		{
		var discis = '${discis}';
		if (discis =='' || discis== null ) 
			{
			$(".discis-row").hide();
			}
		}
		$(document).ready(showsCIS);

	</script>

	<script type="text/javascript">
	function showsIIS() 
		{
		var disiis = '${disiis}';
		if (disiis =='' || disiis== null ) 
			{
			$(".disiis-row").hide();
			}
		}
		$(document).ready(showsIIS);
	</script>
	<script type="text/javascript">
	function showsEPF() 
		{
		var epf = '${epf}';
		if (epf =='' || epf== null ) 
			{
			$(".disepf-row").hide();
			}
		}
		$(document).ready(showsEPF);
	</script>
	<script type="text/javascript">
	function showsstamp() 
		{
		var stamp = '${stamp}';
		if (stamp =='' || stamp== null ) 
			{
			$(".disstamp-row").hide();
			}
		}
		$(document).ready(showsstamp);
	</script>
	<script type="text/javascript">
	function electric() 
		{
		var electric = '${electric}';
		if (electric =='' || electric== null ) 
			{
			$(".diselect-row").hide();
			}
		}
		$(document).ready(electric);
	</script>
	<script type="text/javascript">
	function mandi() 
		{
		var mandi = '${mandi}';
		if (mandi =='' || mandi== null ) 
			{
			$(".dismandi-row").hide();
			}
		}
		$(document).ready(mandi);
	</script>
	<script type="text/javascript">
	function showsSGST() 
		{
		var sgst = '${sgst}';
		if (sgst =='' || sgst== null ) 
			{
			$(".sgst-row").hide();
			}
		}
		$(document).ready(showsSGST);
	</script>
	<script type="text/javascript">
	function showsTax() 
		{
		var taxcreadit = '${taxcreadit}';
		if (taxcreadit =='' || taxcreadit== null ) 
			{
			$(".disallow-row").hide();
			}
		}
		$(document).ready(showsTax);
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
					} else {
					document.getElementById('confProvbyCTDDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
					document.getElementById('confProvbyCTDDoc').focus();
					return false;
				}}
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
				}}
			if ('${disViewEvaluate.confProvbyCTDDocBase64}' != '') {
				return true;
			}}
	</script>
	<script type="text/javascript">
 	/* function newSGST(){

 		<c:forEach var="rgstTable"
			items="${disReimbrsGSTTableList}"
			varStatus="counter">
			//var amtOfNetSGST1New = document.getElementsByClassName('amtOfNetSGSTColumn');
		 	var amtOfNetSGST1New = "${rgstTable.amtNetSgst}";
		 	var categoryofindustry = "${categoryofindustry}";
			var reimnewsgst11 = 0.0;
			reimnewsgst11 = parseFloat(amtOfNetSGST1New*categoryofindustry);
			var reimnewsgst1 = 0.0;
			reimnewsgst1 = parseFloat(reimnewsgst11.toFixed(2));
			//reimnewsgst1 = $(this).val();
			$(".amtOfNetSGSTReimColumn", this).val(reimnewsgst1);
		</c:forEach> 
		
 	 var amtOfNetSGST1New =document.getElementsByClassName('amtOfNetSGSTColumn');
 	var categoryofindustry = "${categoryofindustry}";
	var reimnewsgst11 = amtOfNetSGST1New*categoryofindustry;

	var reimnewsgst1 = reimnewsgst11.toFixed(2);

	$(".amtOfNetSGSTColumn").each(function() {
		reimnewsgst1 = $(this).val();
		
	});
	$(".amtOfNetSGSTReimColumn", this).val(reimnewsgst1);
		
 	}
 	
 	$(document).ready(newSGST);
 	$(document).on("change", newSGST);*/ 
 	
	 </script>
	<script type="text/javascript">
	 	function epfeligible(){
	 	var employerContributionEPF1 = document.getElementById("employerContributionEPF1").value;
	 	var employerContributionEPF2 = document.getElementById("employerContributionEPF2").value;
	 	var employerContributionEPF3 = document.getElementById("employerContributionEPF3").value;
	 	var employerContributionEPF4 = document.getElementById("employerContributionEPF4").value;

	 	var addepfSKUkW = "${incentiveDeatilsData.ISF_Add_Epf_Reim_SkUkW}";
	 	var addepfDiv = "${incentiveDeatilsData.ISF_Add_Epf_Reim_DIVSCSTF}";
	 	
	 	if(addepfSKUkW != '' ){
			if(addepfDiv != ''){
				var epf1 = employerContributionEPF1 * 0.5 + employerContributionEPF1 * 0.10 + employerContributionEPF1 * 0.10;
				var epf2 = employerContributionEPF2 * 0.5 + employerContributionEPF2 * 0.10 + employerContributionEPF2 * 0.10;
				var epf3 = employerContributionEPF3 * 0.5 + employerContributionEPF3 * 0.10 + employerContributionEPF3 * 0.10;
				var epf4 = employerContributionEPF4 * 0.5 + employerContributionEPF4 * 0.10 + employerContributionEPF4 * 0.10;
				} else{
				var epf1 = employerContributionEPF1 * 0.5 + employerContributionEPF1 * 0.10;
				var epf2 = employerContributionEPF2 * 0.5 + employerContributionEPF2 * 0.10;
				var epf3 = employerContributionEPF3 * 0.5 + employerContributionEPF3 * 0.10;
				var epf4 = employerContributionEPF4 * 0.5 + employerContributionEPF4 * 0.10;
			}
	 	} else {
	 		var epf1 = employerContributionEPF1 * 0.5;
	 		var epf2 = employerContributionEPF2 * 0.5;
			var epf3 = employerContributionEPF3 * 0.5;
			var epf4 = employerContributionEPF4 * 0.5;
		 	}
		

		$("#reimEligibility1", this).val(epf1);
		$("#reimEligibility2", this).val(epf2);
		$("#reimEligibility3", this).val(epf3);
		$("#reimEligibility4", this).val(epf4);
	 	}
		$(document).on("change", epfeligible);
	 </script>
	<%-- <script type="text/javascript">
	 function breakuptotal() {
		  var landPerCerti = <%=request.getParameter("landPerCerti")%>
		  var landCapInvCA = <%=request.getParameter("landCapInvCA")%>
		 	var landtotal=  landPerCerti + landCapInvCA;
		 	$("#landCapInvValuer", this).val(landtotal);
		 	 }
		$(document).ready(breakuptotal);
		$(document).on("keyup", breakuptotal);
		$(document).on("change", breakuptotal);
</script> --%>
	<script type="text/javascript">
$(document).ready(function() {
	var mySelect = $('.financial-year');
	var startYear = 2051;
	var prevYear = 2050;
	for (var i = 0; i < 60; i++) {
	  startYear = startYear - 1;
	  prevYear = prevYear - 1;
	  mySelect.append(
	    $('<option></option>').val(prevYear + "-" + startYear).html(prevYear + "-" + startYear)
	  );
	}
	if ("${electricityDutyExeFinYr1}" == null || "${electricityDutyExeFinYr1}" == '') {
		  $("#efinancialYear1 option").removeAttr("selected");
	}
	if ("${electricityDutyExeFinYr2}" == null || "${electricityDutyExeFinYr2}" == '') {
		  $("#efinancialYear2 option").removeAttr("selected");
	}
	if ("${electricityDutyExeFinYr3}" == null || "${electricityDutyExeFinYr3}" == '') {
		  $("#efinancialYear3 option").removeAttr("selected");
	}
	if ("${electricityDutyExeFinYr4}" == null || "${electricityDutyExeFinYr4}" == '') {
		  $("#efinancialYear4 option").removeAttr("selected");
	}
	if ("${electricityDutyExeFinYr5}" == null || "${electricityDutyExeFinYr5}" == '') {
		  $("#efinancialYear5 option").removeAttr("selected");
	}
	if ("${electricityDutyExeFinYr6}" == null || "${electricityDutyExeFinYr6}" == '') {
		  $("#efinancialYear6 option").removeAttr("selected");
	}
	 if ("${electricityDutyExeFinYr7}" == null || "${electricityDutyExeFinYr7}" == '') {
		  $("#efinancialYear7 option").removeAttr("selected");
	} 
	 if ("${electricityDutyExeFinYr8}" == null || "${electricityDutyExeFinYr8}" == '') {
		  $("#efinancialYear8 option").removeAttr("selected");
	}
	 if ("${electricityDutyExeFinYr9}" == null || "${electricityDutyExeFinYr9}" == '') {
		  $("#efinancialYear9 option").removeAttr("selected");
	}
	 if ("${electricityDutyExeFinYr10}" == null || "${electricityDutyExeFinYr10}" == '') {
		  $("#efinancialYear10 option").removeAttr("selected");
	}
 
});
</script>
	<script type="text/javascript">
function EPFClaimTotalTotal() {
	var sum = 0;
	$(".epfclaim").each(function() {
		sum += +$(this).val();
	});
	$(".epfclaimtotal", this).val(sum);
	var sureimm = 0;
	$(".epfclaimreim").each(function() {
		sureimm += +$(this).val();
	});
	$(".epfreimtotal", this).val(sureimm);	
}
$(document).ready(EPFClaimTotalTotal);
$(document).on("keyup", EPFClaimTotalTotal);
$(document).on("change", EPFClaimTotalTotal);
</script>
	<script type="text/javascript">
function costofProject() {
	var ttlPerDpr = document.getElementById("ttlPerDpr").value;
	$("#cisCostOfProjectAmt", this).val(ttlPerDpr);
	$("#iisCostOfProjectAmt", this).val(ttlPerDpr);
}
$(document).ready(costofProject);
$(document).on("keyup", costofProject);
$(document).on("change", costofProject);
</script>
	<script type="text/javascript">
function ratioofpnm() {
	var ttlPerDpr = document.getElementById("ttlPerDpr").value;
	var cisPlantMachAmt = document.getElementById("cisPlantMachAmt").value;
	var cisEntireProjectAmt = document.getElementById("cisEntireProjectAmt").value;
	var pnmratio =  cisPlantMachAmt/ttlPerDpr;
	var propnatepnm1 = pnmratio*cisEntireProjectAmt;
	var propnatepnm =  Math.floor(propnatepnm1);
	var bankcert= '${bankcert}';
	var nopnm1 = '${noPnM}';
	var Totalinterest ="${Totalinterest}";
	var additional ="${additional}";
	var roi ="${roi}";
	if(bankcert =='Yes'){
	$("#cisTermPlantMachAmt", this).val(${noPnM});
	}
	else {
		$("#cisTermPlantMachAmt", this).val(propnatepnm);
	}
	var intapplirate1=pnmratio*Totalinterest;
	var intapplirate=Math.floor(intapplirate1);
	$("#cisRoiPMAmt", this).val(intapplirate);
	var d=additional/roi;
	var cisIntPM5Amt=Math.floor(intapplirate*d);
	$("#cisIntPM5Amt", this).val(cisIntPM5Amt);
}
$(document).ready(ratioofpnm);
$(document).on("keyup", ratioofpnm);
$(document).on("change", ratioofpnm);
</script>

	<script type="text/javascript">
function ratioofInfra() {

	var ttlPerDpr = document.getElementById("ttlPerDpr").value;
	var iisPlantMachAmt = document.getElementById("iisPlantMachAmt").value;
	var cisEntireProjectAmt = document.getElementById("cisEntireProjectAmt").value;
	var totalcost = parseInt(ttlPerDpr)+parseInt(iisPlantMachAmt);
	var infraratio =  iisPlantMachAmt/totalcost;
	
	var propnateinfra1 = infraratio*cisEntireProjectAmt;
	var propnateinfra =  Math.floor(propnateinfra1);
	var bankcertiis= '${bankcertiis}';
	var noinfra1 = '${noinfra}';
	var Totalinterest ="${Totalinterest}";
	var additional ="${additional}";
	var roiiis ="${roiiis}";
	
	if(bankcertiis =='Yes'){
	
	$("#iisTermPlantMachAmt", this).val(${noinfra1});
	//alert(nopnm1);
	}
	else {
		$("#iisTermPlantMachAmt", this).val(propnateinfra);
	}
	//Proportionate Interest at Applicable Rate of Interest for P&M
	var intapplirate1=infraratio*Totalinterest;
	var intapplirate =Math.floor(intapplirate1);
	$("#iisRoiPMAmt", this).val(intapplirate);
	var d=additional/roiiis
	var iisIntPM5Amt=Math.floor(intapplirate*d);
	$("#iisIntPM5Amt", this).val(iisIntPM5Amt);
	
}
$(document).ready(ratioofInfra);
$(document).on("keyup", ratioofInfra);
$(document).on("change", ratioofInfra);
</script>

	<script type="text/javascript">

function stampdisburesment() {
	var regionName = '${eligiblereg}';
	var districtName = '${eligibledis}';
	var additional= "${stampadditional}";
	var categoryregion= "${categoryregion}";
	var subTtlAPerDpr = document.getElementById("subTtlAPerDpr").value;

	var cutofdate = '${evaluateInvestMentDetails.invCommenceDate}';
	cutofdate = cutofdate.split(' ')[0];
	$("#invcutofdate", this).val(cutofdate)

	var toDate = '${toDate}';
	
    if (categoryregion === 'Mega' || categoryregion === 'Mega Plus'){
		var year = "From " + cutofdate + " TO "+ toDate ;
		$("#invPeriodCompl", this).val(year)
		}

	   if (categoryregion === 'Large'){
			var year = "From " + cutofdate + " TO "+ toDate ;
			$("#invPeriodCompl", this).val(year)
		}

	   if (categoryregion === 'Super Mega'){
			var year = "From " + cutofdate + " TO "+ toDate ;
			$("#invPeriodCompl", this).val(year)
		}
	
	if ((regionName === 'Madhyanchal' || regionName === 'Paschimanchal')
			&& !(districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD')){
		var a = parseFloat(additional);
		var actualstam = 0.75 + parseFloat(additional);
		var claimStampDutyReimAmt = document.getElementById("claimStampDutyReimAmt").value;
		$("#stampDutyReimElig", this).val(claimStampDutyReimAmt*actualstam)
		}
	if (districtName === 'GAUTAM BUDH NAGAR'
		|| districtName === 'GHAZIABAD'){
		var actualstam = 0.5 + parseInt(additional);
		var claimStampDutyReimAmt = document.getElementById("claimStampDutyReimAmt").value;
		$("#stampDutyReimElig", this).val(claimStampDutyReimAmt*actualstam)
		}
	if (regionName === 'Bundelkhand' || regionName === 'Poorvanchal'){
		
		var claimStampDutyReimAmt = document.getElementById("claimStampDutyReimAmt").value;
		$("#stampDutyReimElig", this).val(claimStampDutyReimAmt)
		}
	if(regionName === 'Bundelkhand' || regionName === 'Poorvanchal')
		{
		if (categoryregion === 'Small' || categoryregion === 'Medium' || categoryregion === 'Large'){
			var sum = 0;
			$(".epfclaim").each(function() {
				sum += +$(this).val();
			});
			$(".epfclaimtotal", this).val(sum);
			}
		}
}

$(document).ready(stampdisburesment);
//$(document).on("keyup", stampdisburesment);
$(document).on("change", stampdisburesment);

</script>

	<script type="text/javascript">
function MeansOfFinancing() {
	var equityCapPerCerti = document.getElementById("equityCapPerCerti").value;
	var equityCapCapInvCA = document.getElementById("equityCapCapInvCA").value;
	var equitytotal =0;
	equitytotal = parseInt(equityCapPerCerti)+parseInt(equityCapCapInvCA);
	$("#equityCapStatutoryAudit", this).val(equitytotal);	

	var intCashPerCerti = document.getElementById("intCashPerCerti").value;
	var intCashCapInvCA = document.getElementById("intCashCapInvCA").value;
	var intCashtotal =0;
	intCashtotal = parseInt(intCashPerCerti)+parseInt(intCashCapInvCA);
	$("#intCashStatutoryAudit", this).val(intCashtotal);

	var intFreePerCerti = document.getElementById("intFreePerCerti").value;
	var intFreeCapInvCA = document.getElementById("intFreeCapInvCA").value;
	var intFreetotal =0;
	intFreetotal = parseInt(intFreePerCerti)+parseInt(intFreeCapInvCA);
	$("#intFreeStatutoryAudit", this).val(intFreetotal);

	var SecPerCerti = document.getElementById("SecPerCerti").value;
	var SecCapInvCA = document.getElementById("SecCapInvCA").value;
	var SecCaptotal =0;
	SecCaptotal = parseInt(SecPerCerti)+parseInt(SecCapInvCA);
	$("#SecStatutoryAudit", this).val(SecCaptotal);	

	var dealPerCerti = document.getElementById("dealPerCerti").value;
	var dealCapInvCA = document.getElementById("dealCapInvCA").value;
	var dealCaptotal =0;
	dealCaptotal = parseInt(dealPerCerti)+parseInt(dealCapInvCA);
	$("#dealStatutoryAudit", this).val(dealCaptotal);	

	var finOthPerCerti = document.getElementById("finOthPerCerti").value;
	var finOthCapInvCA = document.getElementById("finOthCapInvCA").value;
	var finOthCaptotal =0;
	finOthCaptotal = parseInt(finOthPerCerti)+parseInt(finOthCapInvCA);
	$("#finOthStatutoryAudit", this).val(finOthCaptotal);	

	var FromFiPerCerti = document.getElementById("FromFiPerCerti").value;
	var FromFiCapInvCA = document.getElementById("FromFiCapInvCA").value;
	var FromFiCaptotal =0;
	FromFiCaptotal = parseInt(FromFiPerCerti)+parseInt(FromFiCapInvCA);
	$("#FromFiStatutoryAudit", this).val(FromFiCaptotal);	

	var FrBankPerCerti = document.getElementById("FrBankPerCerti").value;
	var FrBankCapInvCA = document.getElementById("FrBankCapInvCA").value;
	var FrBankCaptotal =0;
	FrBankCaptotal = parseInt(FrBankPerCerti)+parseInt(FrBankCapInvCA);
	$("#FrBankStatutoryAudit", this).val(FrBankCaptotal);	

	var Plant_MachPerCerti = document.getElementById("Plant_MachPerCerti").value;
	var PlantMachCapInvCA = document.getElementById("PlantMachCapInvCA").value;
	var PlantMachCaptotal =0;
	PlantMachCaptotal = parseInt(Plant_MachPerCerti)+parseInt(PlantMachCapInvCA);
	$("#PlantMachStatutoryAudit", this).val(PlantMachCaptotal);	

	var finTttlPerCerti = document.getElementById("finTttlPerCerti").value;
	var finTttlCapInvCA = document.getElementById("finTttlCapInvCA").value;
	var finTttlCaptotal =0;
	finTttlCaptotal = parseInt(finTttlPerCerti)+parseInt(finTttlCapInvCA);
	$("#finTttlStatutoryAudit", this).val(finTttlCaptotal);	
	
}
$(document).ready(MeansOfFinancing);
$(document).on("keyup", MeansOfFinancing);
$(document).on("change", MeansOfFinancing);
</script>


</body>
</html>