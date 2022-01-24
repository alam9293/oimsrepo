<%@page import="com.webapp.ims.model.ProprietorDetails"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<!doctype html>
<html lang="en">

<head>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Disbursement View Applicant Concern</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<style type="text/css">
@media screen , print {
	body {
		background: #fff;
	}
	#printform {
		background: #fff;
		padding: 20px;
	}
}
</style>
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
					<li class="nav-item"><a class="nav-link"
							href="./departmentDashboard"><i class="fa fa-tachometer"></i>
								Dashboard</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./viewDeptApplication"><i class="fa fa-eye"></i> View
								Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./circulateViewAgendaNote"><i class="fa fa-eye"></i>
								View Circulated Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link"
							href=""><i class="fa fa-eye"></i>
								View Circulated Draft LOC</a></li>
				</ul>
				</div>
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<h4 class="card-title mb-4 mt-4 text-center">Industrial
						Investment & Employment Promotion Policy 2017</h4>
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-12">
								<div class="mt-4">
									<div class="without-wizard-steps">
										<div class="row">
											<div class="col-sm-8">
												<a href="./deptApplicationForLoc"
													class="common-default-btn mt-0">Back</a> <a
													href="javascript:void(0);" id="printMethod"
													class="common-btn mt-0"><i class="fa fa-print"></i>
													Print</a> <!-- <a href="./evaluateApplicationDis"
													class="common-btn mt-3" id="evaluateBtnTop">Evaluate
													Application</a>
													<button type="button" class="btn btn-outline-info btn-sm"
													data-target="#SendtoConcernDepartment" data-toggle="modal">Send
													to Concern Department</button> -->
											</div>
											<div class="col-sm-4 text-right">
												<div class="form-group">
													<select class="form-control" id="SelectParameter">
														<option value="viewAllApplicationDetails">View as
															per entrepreneur</option>
														<option value="Annexure-II">View as per policy
															LOC form Annexure-II</option>
														<option value="Annexure-IV">View as per policy
															LOC form Annexure-IV</option>
													</select>
												</div>
											</div>
										</div>
										<hr>
										<form:form modelAttribute="downloadSupportDIS" class="mt-4"
											name="downloadSupportDIS" method="post">
											<div class="row mt-4">
												<div class="col-sm-12 text-center" id="downloadbuttons">


													<button type="button"
														class="btn btn-outline-success btn-sm mb-3" id="formId">
														<i class="fa fa-download"></i> Download Only Application
														Form
													</button>

													<button type="submit" formaction="downloadSupportDIS"
														class="btn btn-outline-secondary btn-sm mb-3">
														<i class="fa fa-download"></i> Download Only Supporting
														Docs
													</button>
												</div>
											</div>
										</form:form>


										<!-- Start view All Application Details -->
										<div id="printform">
											<div class="parameter-properties"
												id="viewAllApplicationDetails">
												<hr class="mt-2">
												<h4
													class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Authorised
													Signatory Details</h4>

												<div class="isf-form mt-4">
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table class="table table-bordered">
																	<tbody>
																		<tr>
																			<td width="50%">Name of the Applicant</td>
																			<td><input type="text" class="form-control"
																				id="" value="${authorisedSignatoryName}" disabled=""
																				name=""></td>
																		</tr>
																		<tr>
																			<td width="50%">Designation</td>
																			<td><input type="text" class="form-control"
																				id="" value="${businessDesignation}" disabled=""
																				name=""></td>
																		</tr>
																		<tr>
																			<td colspan="2"><strong>Company Details</strong></td>
																		</tr>
																		<tr>
																			<td>Company Name</td>
																			<td><input type="text" class="form-control"
																				id="" value="${businessEntityName}" disabled=""
																				name=""></td>
																		</tr>
																		<tr>
																			<td>Full Address</td>
																			<td><input type="text" class="form-control"
																				id="" value="${businessAddress}" disabled="" name=""></td>
																		</tr>
																		<tr>
																			<td>Country</td>
																			<td><input type="text" class="form-control"
																				id="" value="${businessCountryName}" disabled=""
																				name=""></td>
																		</tr>
																		<tr>
																			<td>State/UT</td>
																			<td><input type="text" class="form-control"
																				id="" value="${businessStateName}" disabled=""
																				name=""></td>
																		</tr>
																		<tr>
																			<td>District</td>
																			<td><input type="text" class="form-control"
																				id="" value="${businessDistrictName}" disabled=""
																				name=""></td>
																		</tr>
																		<tr>
																			<td>Pin Code</td>
																			<td><input type="text" class="form-control"
																				id="" value="${PinCode}" disabled="" name=""></td>
																		</tr>
																		<tr>
																			<td colspan="2"><strong>Location of the
																					Industrial Undertaking</strong></td>
																		</tr>
																		<tr>
																			<td>Full Address</td>
																			<td><input type="text" class="form-control"
																				id="" value="${fullAddress}" disabled="" name=""></td>
																		</tr>
																		<tr>
																			<td>District</td>
																			<td><input type="text" class="form-control"
																				id="" value="${districtName}" disabled="" name=""></td>
																		</tr>
																		<tr>
																			<td>Mandal</td>
																			<td><input type="text" class="form-control"
																				id="" value="${mandalName}" disabled="" name=""></td>
																		</tr>
																		<tr>
																			<td>Region</td>
																			<td><input type="text" class="form-control"
																				id="" value="${resionName}" disabled="" name=""></td>
																		</tr>
																		<tr>
																			<td>Pin Code</td>
																			<td><input type="text" class="form-control"
																				id="" value="${pinCode}" disabled="" name=""></td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>

												<hr class="mt-2">

												<h4 class="card-title mb-4 mt-4 text-center">Capital
													Investment</h4>

												<div class="isf-form mt-4">
													<hr class="mt-2">
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table class="table table-bordered">
																	<tbody>
																		<tr>
																			<td colspan="2"><strong>Details of
																					Eligible Capital Investment in Industrial
																					Undertaking</strong></td>
																		</tr>
																		<tr>
																			<td colspan="2">
																				<div class="newproject">
																					<div class="table-responsive">
																						<table class="table table-bordered">
																							<thead>
																								<tr>
																									<th>Sl No</th>
																									<th>Item</th>
																									<th width="50%">New Project</th>
																								</tr>
																							</thead>
																							<tbody>
																								<tr>
																									<td>1</td>
																									<td>Land Cost</td>
																									<td><input type="text"
																										class="form-control disbNewproj" name=""
																										value="${newprjLandCost}" readonly="true"></td>
																								</tr>
																								<tr>
																									<td>2</td>
																									<td>Building Site Development & Civil
																										Works Cost</td>
																									<td><input type="text"
																										class="form-control disbNewproj" name=""
																										value="${newprjBuildingCost}" readonly="true"></td>
																								</tr>
																								<tr>
																									<td>3</td>
																									<td>Plant and Machinery</td>
																									<td><input type="text"
																										class="form-control disbNewproj" name=""
																										value="${newprjPlantAndMachCost}"
																										readonly="true"></td>
																								</tr>
																								<tr>
																									<td>4</td>
																									<td>Infrastructure Facilities</td>
																									<td><input type="text"
																										class="form-control disbNewproj" name=""
																										value="${newprjInfCost}" readonly="true"></td>
																								</tr>
																								<tr>
																									<td></td>
																									<td>Total</td>
																									<td><input type="text"
																										class="form-control disbNewprojTotal" name=""
																										value="" readonly="true"></td>
																								</tr>
																							</tbody>
																						</table>
																					</div>
																				</div>
																				<div class="existingproject">
																					<div class="table-responsive">
																						<table class="table table-bordered">
																							<thead>
																								<tr>
																									<th>Sl No</th>
																									<th>Item</th>
																									<th>Existing Project</th>
																									<th>Expansion/Diversification</th>
																									<th>% of increase under
																										Expansion/Diversification</th>
																								</tr>
																							</thead>
																							<tbody>
																								<tr>
																									<td>1</td>
																									<td>Land</td>
																									<td><input type="text"
																										class="form-control" name=""
																										value="${invLandCost}" readonly="true"></td>
																									<td><input type="text" value="${exLand}"
																										class="form-control" name="" readonly="true"></td>
																									<td><input type="text"
																										value="${projLandIncrement}"
																										class="form-control" name="" readonly="true"></td>
																								</tr>
																								<tr>
																									<td>2</td>
																									<td>Building</td>
																									<td><input type="text"
																										class="form-control" name=""
																										value="${invBuildingCost}" readonly="true"></td>
																									<td><input type="text"
																										value="${invBuildingCost}"
																										class="form-control" name="" readonly="true"></td>
																									<td><input type="text"
																										value="${projBuildIncrement}"
																										class="form-control" name="" readonly="true"></td>
																								</tr>
																								<%-- <tr>
																									<td>3</td>
																									<td>Other Construction</td>
																									<td><input type="text"
																							class="form-control" name=""
																										value="${invOtherCost}" readonly="true"></td>
																									<td><input type="text" value="${projDisConstruct}"
																										class="form-control" name="" readonly="true"></td>
																									<td><input type="text"  value="${projConstructIncrement}"
																										class="form-control" name="" readonly="true"></td>
																								</tr> --%>
																								<tr>
																									<td>3</td>
																									<td>Plant and Machinery</td>
																									<td><input type="text"
																										class="form-control" name=""
																										value="${invPlantAndMachCost}" readonly="true"></td>
																									<td><input type="text" value="${explant}"
																										class="form-control" name="" readonly="true"></td>
																									<td><input type="text"
																										value="${projPlantIncrement}"
																										class="form-control" name="" readonly="true"></td>
																								</tr>
																								<tr>
																									<td>4</td>
																									<td>Infrastructure Facilities</td>
																									<td><input type="text"
																										class="form-control" name=""
																										value="${newprjInfCost}" readonly="true"></td>
																									<td><input type="text" value="${exinfra}"
																										class="form-control" name="" readonly="true"></td>
																									<td><input type="text"
																										value="${projInfraIncrement}"
																										class="form-control" name="" readonly="true"></td>
																								</tr>
																								<tr>
																									<td></td>
																									<td>Total</td>
																									<td><input type="text"
																										value="${ttlAmtExitProj}" class="form-control"
																										name="" value="" readonly="true"></td>
																									<td><input type="text"
																										value="${totalAmountOfInvest}"
																										class="form-control" name="" readonly="true"></td>
																									<td><input type="text"
																										value="${totalIncrement}" class="form-control"
																										name="" readonly="true"></td>
																								</tr>
																							</tbody>
																						</table>
																					</div>
																				</div>
																			</td>
																		</tr>

																		<tr>
																			<td width="50%">Relevant documents be provided
																				alongwith the Statutory Auditor's Certificate for
																				Capital Investment</td>
																			<td><a
																				href="./downloadDISCapInvDoc?fileName=${statutoryAuditorDoc}">${statutoryAuditorDoc}</a></td>
																		</tr>

																		<tr>
																			<td width="50%">Annual Turnover of the Project</td>
																			<td><input type="text" class="form-control"
																				name="" value="${capinv.annualTurnOver}"
																				readonly="true"></td>
																		</tr>


																		<tr>
																			<td colspan="2"><strong>Phasewise
																					Details</strong></td>
																		</tr>

																		<tr>
																			<td colspan="2">
																				<div class="table-responsive mt-3">
																					<table class="table table-stripped table-bordered">
																						<c:if test="${not empty pwInvList}">
																							<thead>
																								<tr>
																									<th>Phase</th>
																									<th>Product</th>
																									<th>Capacity</th>
																									<th>Unit</th>
																									<th>Capital Investment</th>
																									<th>Date of Commercial Production</th>

																								</tr>
																							</thead>

																							<%
																								int i = 0;
																							%>
																							<tbody>
																								<c:forEach var="pwInvObj" items="${pwInvList}">
																									<!-- Iterating the list using JSTL tag  -->
																									<c:if test="${not empty pwInvList}">

																										<tr>
																											<td>${pwInvObj.pwPhaseNo}</td>
																											<td>${pwInvObj.pwProductName}</td>
																											<td>${pwInvObj.pwCapacity}</td>
																											<td>${pwInvObj.pwUnit}</td>
																											<td>${pwInvObj.pwFci}</td>
																											<td>${pwInvObj.pwPropProductDate}</td>

																										</tr>
																									</c:if>

																								</c:forEach>
																							</tbody>
																						</c:if>
																					</table>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td width="50%">Enclose certificate</td>
																			<td><a
																				href="./downloadDISCapInvDoc?fileName=${encCertificateFName}">${encCertificateFName}</a></td>
																		</tr>

																		<tr>
																			<td colspan="2"><strong>Details of
																					Capital Investment in Industrial Undertaking</strong></td>
																		</tr>

																		<tr>
																			<td colspan="2">
																				<div class="table-responsive mt-3">
																					<table class="table table-stripped table-bordered"
																						id="BreakUpTable">
																						<thead>
																							<tr>
																								<th rowspan="3">Item</th>
																								<th rowspan="3">Cost of Project As per DPR</th>
																								<th rowspan="3">Cost of Project As per
																									Appraisal</th>
																								<th colspan="5" class="text-center">Actual
																									Investment <small>(As per certificate
																										from Statutory Auditors)</small>
																								</th>
																							</tr>
																							<tr>

																								<th rowspan="2">Before Cut Off Date</th>
																								<th rowspan="2" width="20%">Cut Off date to
																									the date of commencement of commercial
																									production <small>( in case of phase,
																										give investment phasewise)</small>
																								</th>
																								<th colspan="2">Date of commencement of
																									final phase of commercial production to till
																									date</th>
																								<th rowspan="2">Total</th>
																							</tr>
																							<tr>
																								<th>Actual Investment (100%)</th>
																								<th>Additional 10% of actual investment
																									beyond the date of actual commercial production
																									(as per 2.9 Policy rule 2017)</th>
																							</tr>
																						</thead>
																						<tbody class="add-from-here">
																							<tr>
																								<td>Land and Site Development</td>
																								<td><input type="text" class="form-control"
																									name="" value="${invLandCost}" readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAppraisalLC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCutoffDateLC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCommProdLC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvActualInvLC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAddlInvLC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capInvTotalLC}"
																									readonly="true"></td>
																							</tr>
																							<tr>
																								<td>Building and Civil Works</td>
																								<td><input type="text" class="form-control"
																									name="" value="${invBuildingCost}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAppraisalBC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCutoffDateBC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCommProdBC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvActualInvBC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAddlInvBC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capInvTotalBC}"
																									readonly="true"></td>
																							</tr>
																							<tr>
																								<td>Plant and Machinery</td>
																								<td><input type="text" class="form-control"
																									name="" value="${invPlantAndMachCost}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAppraisalPMC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCutoffDatePMC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCommProdPMC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvActualInvPMC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAddlInvPMC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capInvTotalPMC}"
																									readonly="true"></td>
																							</tr>
																							<tr>
																								<td>Miscellaneous Fixed Assets</td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvDPRMFA}" readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAppraisalMFA}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCutoffDateMFA}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCommProdMFA}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvActualInvMFA}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAddlInvMFA}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capInvTotalMFA}"
																									readonly="true"></td>
																							</tr>
																							<tr>
																								<td>Technical Knowhow Fee</td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvDPRTKF}" readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAppraisalTKF}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCutoffDateTKF}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCommProdTKF}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvActualInvTKF}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAddlInvTKF}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capInvTotalTKF}"
																									readonly="true"></td>
																							</tr>
																							<tr>
																								<td>Interest During Construction Period</td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvDPRICP}" readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAppraisalICP}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCutoffDateICP}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCommProdICP}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvActualInvICP}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAddlInvICP}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capInvTotalICP}"
																									readonly="true"></td>
																							</tr>
																							<tr>
																								<td>Prel. And Preoperative Expenses</td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvDPRPPE}" readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAppraisalPPE}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCutoffDatePPE}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCommProdPPPE}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvActualInvPPE}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAddlInvPPE}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capInvTotalPPE}"
																									readonly="true"></td>
																							</tr>
																							<tr>
																								<td>Margin Money for Working Capital</td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvDPRMMC}" readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAppraisalMMC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCutoffDateMMC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvCommProdMMC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvActualInvMMC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${CapInvAddlInvMMC}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capInvTotalMMC}"
																									readonly="true"></td>
																							</tr>
																							<tr>
																								<td><strong>Total:</strong></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capinv.ttlCostDpr}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capinv.ttlcostProAppFrBank}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capinv.ttlBeforeCutOff}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name=""
																									value="${capinv.ttlCutOffCommProduction}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capinv.ttlActualInvestment}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name=""
																									value="${capinv.ttlAddiActualInvestment}"
																									readonly="true"></td>
																								<td><input type="text" class="form-control"
																									name="" value="${capinv.disValueTotalOfTotal}"
																									readonly="true"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</td>
																		</tr>

																	</tbody>
																</table>

																<table class="table table-bordered">
																	<tbody>
																		<tr>
																			<td colspan="2"><strong>Relevant
																					Supporting Documents</strong></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">Registered document
																				showing purchase price</td>
																			<td><a
																				href="./downloadDISCapInvDoc?fileName=${purchasePriceDoc}">${purchasePriceDoc}</a></td>
																		</tr>
																		<tr>
																			<td>Receipt of payment of stamp duty</td>
																			<td><a
																				href="./downloadDISCapInvDoc?fileName=${stampDutyDoc}">${stampDutyDoc}</a></td>
																		</tr>
																		<tr>
																			<td>Receipt of payment of registration fee</td>
																			<td><a
																				href="./downloadDISCapInvDoc?fileName=${registrationFeeDoc}">${registrationFeeDoc}</a></td>
																		</tr>
																		<tr>
																			<td>If land purchased from UPSIDC/DI/FIs/Banks
																				in auction, supporting documents for price paid.</td>
																			<td><a
																				href="./downloadDISCapInvDoc?fileName=${banksAuctionDoc}">${banksAuctionDoc}</a></td>
																		</tr>
																		<tr>
																			<td>Detailed cost estimates of building and
																				civil works <a href="javascript:void(0);"
																				class="remove-row" data-toggle="tooltip" title=""
																				data-original-title="Detailed cost estimates of building and civil works constructed or to be constructed (as per DPR/Appraisal Note) and supported with layout plans and cost estimates prepared by external consultants/CA firms and cost incurred duly certified by statutory auditors."><i
																					class="fa fa-info-circle text-info"></i></a>
																			</td>
																			<td><a
																				href="./downloadDISCapInvDoc?fileName=${civilWorksDoc}">${civilWorksDoc}</a>
																			</td>
																		</tr>
																		<tr>
																			<td>The cost of proposed/actual capital
																				investment in the head of plant and machinery and
																				misc. fixed assets <a href="javascript:void(0);"
																				class="remove-row" data-toggle="tooltip" title=""
																				data-original-title="The cost of proposed/actual capital investment in the head of plant and machinery and misc. fixed assets should be shown itemized in accordance with the provisions of the Rules for scrutiny, verification and certification."><i
																					class="fa fa-info-circle text-info"></i></a>
																			</td>
																			<td><a
																				href="./downloadDISCapInvDoc?fileName=${machineryMiscDoc}">${machineryMiscDoc}</a>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>

												<h4 class="card-title mb-4 mt-4 text-center">EMPLOYMENT
													DETAILS</h4>
												<div class="isf-form mt-4">
													<hr class="mt-2">
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table
																	class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td style="width: 50%;">Number of BPL Employees</td>
																			<td><input type="text" class="form-control"
																				value="${totalBPL}" readonly="true"></td>
																		</tr>
																		<tr>
																			<td>Particulars of Workers from BPL families
																				with supporting documents</td>
																			<td><a
																				href="./downloadDISempDetlDoc?fileName=${noBPLEmplDoc}">${noBPLEmplDoc}</a></td>
																		</tr>
																		<tr>
																			<td>Number of SC Employees</td>
																			<td><input type="text" class="form-control"
																				readonly="true" value="${totalSC}"></td>
																		</tr>
																		<tr>
																			<td>Particulars of Workers from SC families with
																				supporting documents and Employee Payroll</td>
																			<td><a
																				href="./downloadDISempDetlDoc?fileName=${noSCEmplDoc}">${noSCEmplDoc}</a></td>
																		</tr>
																		<tr>
																			<td>Number of ST Employees</td>
																			<td><input type="text" class="form-control"
																				readonly="true" value="${totalSt}"></td>
																		</tr>
																		<tr>
																			<td>Particulars of Workers from ST families with
																				supporting documents and Employee Payroll</td>
																			<td><a
																				href="./downloadDISempDetlDoc?fileName=${noSTEmplDoc}">${noSTEmplDoc}</a></td>
																		</tr>
																		<tr>
																			<td>Number of Female Employees</td>
																			<td><input type="text" class="form-control"
																				readonly="true" value="${totalFemaleEmp}"></td>
																		</tr>
																		<tr>
																			<td>Particulars of female workers with
																				supporting documents</td>
																			<td><a
																				href="./downloadDISempDetlDoc?fileName=${noFemaleEmplDoc}">${noFemaleEmplDoc}</a></td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>

												<h4 class="card-title mb-4 mt-4 text-center">Incentive
													Details</h4>

												<div class="isf-form mt-4">
													<hr class="mt-2">
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table
																	class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td colspan="2">
																				<h5 class="text-center">Reimbursement of
																					Deposit GST</h5>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><strong>Details of tax
																					paid under GST Act</strong></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">GSTIN issued by
																				Commercial Taxes Department</td>
																			<td><input type="text" class="form-control"
																				readonly="true" value="${gstComTaxDept}" name=""></td>
																		</tr>
																		<tr>
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Financial Year/ Duration/ Period</th>
																						<th>Amount of Quarterly SGST paid during the
																							Financial Year</th>
																						<th>Amount of net SGST paid during Financial
																							Year</th>
																						<th>Total Amount of SGST Claimed for
																							Reimbursement</th>
																						<th>Amount of admitted Tax on manufacture
																							goods under deposited GST for FY</th>
																						<th>Amount eligible for deposited GST for
																							reimbursement of FY<a href="javascript:void(0);"
																							class="remove-row" data-toggle="tooltip" title=""
																							data-original-title="Provision as per policy - Annual Percentage of GST Reimbursement 70% for Mega/ Mega Plus/ Super Mega category Industries for period of 10 years.
                              Provision as per rule -  Period - 10 years; Annual Ceiling as percentage of Admissible Capital Investment - 20%; Admissible Capital Investment -  Paschimanchal-100%
                              "><i class="fa fa-info-circle text-info"></i></a>
																						</th>
																					</tr>
																				</thead>
																				<tbody>

																					<c:if test="${not empty disReimbrsGSTTableList}">
																						<c:forEach var="rgstTable"
																							items="${disReimbrsGSTTableList}"
																							varStatus="counter">
																							<!-- Iterating the list using JSTL tag  -->
																							<tr>
																								<td><input type="text"
																									value="${rgstTable.financialYear}"
																									class="form-control" readonly="readonly"></input></td>

																								<td><input type="text"
																									value="${rgstTable.amtNetSgstQYr}"
																									class="form-control" readonly="readonly"></input></td>

																								<td><input type="text"
																									value="${rgstTable.amtNetSgst}"
																									class="form-control" readonly="readonly"></input></td>

																								<td><input type="text"
																									value="${rgstTable.ttlSgstReim}"
																									class="form-control" readonly="readonly"></input></td>

																								<td><input type="text"
																									value="${rgstTable.amtAdmtTaxDeptGst}"
																									class="form-control" readonly="readonly"></input></td>

																								<td><input type="text"
																									value="${rgstTable.eligbAmtDepo}"
																									class="form-control" readonly="readonly"></input></td>
																							</tr>
																						</c:forEach>
																					</c:if>
																				</tbody>
																			</table>
																		</tr>
																	</tbody>
																</table>
																<table class="table table-bordered">
																	<tbody>
																		<tr>
																			<td colspan="2"><strong>Documents
																					Required in Support of GST Paid</strong></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">Certificate issued by
																				Competent Authority in support of GST received in
																				State Government’s Account for the relevant period</td>
																			<td><a
																				href="./downloadDISReimbrsGSTDoc?fileName=${relevantDocRGST}">${relevantDocRGST}</a></td>
																		</tr>
																		<tr>
																			<td>Unit level audited accounts for the relevant
																				financial year (for which GST reimbursement is being
																				claimed)</td>
																			<td><a
																				href="./downloadDISReimbrsGSTDoc?fileName=${auditedAccountsRGST}">${auditedAccountsRGST}</a></td>
																		</tr>
																		<tr>
																			<td>GST Audit Report for the relevant financial
																				year for the unit (standalone GST statement/report
																				for the unit certified by a Chartered Accountant)</td>
																			<td><a
																				href="./downloadDISReimbrsGSTDoc?fileName=${GSTAuditRGST}">${GSTAuditRGST}</a></td>
																		</tr>
																		<tr>
																			<td>CA Certificate for sales reconciliation of
																				Manufactured Goods/Trading goods/Scrap/Stock
																				Transfer and SGST paid towards the same separately</td>
																			<td><a
																				href="./downloadDISReimbrsGSTDoc?fileName=${CACertificateRGST}">${CACertificateRGST}</a></td>
																		</tr>
																	</tbody>
																</table>
																<table
																	class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td colspan="2">
																				<h5 class="text-center">Capital Interest
																					Subsidy</h5>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><strong>Bank/Financial
																					Institutions Details from which Loan Availed</strong></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">Name of Banks/Financial
																				Institutions</td>
																			<td><input type="text" class="form-control"
																				readonly="true" value="${bankname}" name=""></td>
																		</tr>
																		<tr>
																			<td>Address of Banks/Financial Institutions</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${bankadd}"></td>
																		</tr>
																		<tr>
																			<td>Total Loan Taken</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${totalLoTkn}"></td>
																		</tr>
																		<tr>
																			<td>Is FI/ Bank certified amount of loan on P&M
																				available?</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${bankcert}"></td>
																		</tr>
																		<tr>
																			<td>Amount of loan sanctioned on Investment in
																				Plant & Machinery</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${pnmloan}"></td>
																		</tr>
																		<tr>
																			<td>Sanction Letter, Agreement with FI/Bank</td>
																			<td><a
																				href="./downloadDISCISDoc?fileName=${sectionletter}">${sectionletter}</a></td>
																		</tr>
																		<tr>
																			<td>Total Interest on Loan</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${totalIntrs}"></td>
																		</tr>
																		<tr>
																			<td>Rate of Interest</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${roi}"></td>
																		</tr>
																		<tr>
																			<td>Date of Sanction</td>
																			<td><input type="date" class="form-control"
																				readonly="true" name="" value="${sanctiondate}"></td>
																		</tr>
																		<tr>
																			<td>Amount of Loan Disbursed towards Investment
																				in Plant & Machinery with dates of Disbursement.</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${disloan}"></td>
																		</tr>
																	</tbody>
																</table>
																<table class="table table-bordered">
																	<tbody>
																		<tr>
																			<td colspan="2"><strong>Required
																					Supporting Documents</strong></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">Certificate from FI/Bank
																				certifying loan for Plant & Machinery and interest &
																				other relevant details.</td>
																			<td><a
																				href="./downloadDISCISDoc?fileName=${certifyingLoan}">${certifyingLoan}</a></td>
																		</tr>
																		<tr>
																			<td>Certificate from FI/Bank for No Default in
																				the account during the entire period for which
																				reimbursement claimed.</td>
																			<td><a
																				href="./downloadDISCISDoc?fileName=${auditedAccounts}">${auditedAccounts}</a></td>
																		</tr>
																	</tbody>
																</table>
																<table class="table table-bordered">
																	<tbody>
																		<tr>
																			<td colspan="2"><strong>Particulars of
																					Claims for Sanction of Capital Interest Subsidy</strong></td>
																		</tr>
																		<tr>
																			<td colspan="2">
																				<div class="table-responsive">
																					<table class="table table-bordered">
																						<thead>
																							<tr>
																								<th rowspan="2" colspan="2">Year for which
																									subsidy Applied</th>
																								<th colspan="2">Payment made to FI/Bank
																									during the year</th>
																								<th rowspan="2">Amount of Interest Subsidy
																									Applied</th>
																							</tr>
																							<tr>
																								<!-- <th>Total Interest</th> -->
																								<th>Principal</th>
																								<th>Interest on P&M</th>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td>Year I</td>
																								<td><input type="text" readonly="true"
																									name="" class="form-control" value="${Year1}"></td>
																								<%-- <td><input type="text" readonly="true"
																								placeholder="Enter Amount in INR"
																								class="form-control AllYearTotlIntrst" name="" value="${FirstYTI}"></td> --%>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlPrincipal "
																									name="" value="${FirstYP}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlInterest"
																									name="" value="${FirstYI}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlAmtSubsidy"
																									name="" value="${FirstYAIS}"></td>
																							</tr>
																							<tr>
																								<td>Year II</td>
																								<td><input type="text" class="form-control"
																									readonly="true" name="" value="${Year2}"></td>
																								<%-- <td><input type="text" readonly="true"
																								placeholder="Enter Amount in INR"
																								class="form-control AllYearTotlIntrst" name="" value="${SecondYTI}"></td> --%>
																								<td><input type="text"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlPrincipal"
																									name="" readonly="true" value="${SecondYP}"></td>
																								<td><input type="text"
																									placeholder="Enter Amount in INR"
																									readonly="true"
																									class="form-control AllYearTotlInterest"
																									name="" value="${SecondYI}"></td>
																								<td><input type="text"
																									placeholder="Enter Amount in INR"
																									readonly="true"
																									class="form-control AllYearTotlAmtSubsidy"
																									name="" value="${SecondYAIS}"></td>
																							</tr>
																							<tr>
																								<td>Year III</td>
																								<td><input type="text" class="form-control"
																									name="" readonly="true" value="${Year3}"></td>
																								<%-- <td><input type="text" readonly="true"
																								placeholder="Enter Amount in INR"
																								class="form-control AllYearTotlIntrst" name="" value="${ThirdYTI}"></td> --%>
																								<td><input type="text"
																									placeholder="Enter Amount in INR"
																									readonly="true"
																									class="form-control AllYearTotlPrincipal"
																									name="" value="${ThirdYP}"></td>
																								<td><input type="text"
																									placeholder="Enter Amount in INR"
																									readonly="true"
																									class="form-control AllYearTotlInterest"
																									name="" value="${ThirdYI}"></td>
																								<td><input type="text"
																									placeholder="Enter Amount in INR"
																									readonly="true"
																									class="form-control AllYearTotlAmtSubsidy"
																									name="" value="${ThirdYAIS}"></td>
																							</tr>
																							<tr>
																								<td>Year IV</td>
																								<td><input type="text" class="form-control"
																									name="" readonly="true" value="${Year4}"></td>
																								<%-- <td><input type="text" readonly="true"
																								placeholder="Enter Amount in INR"
																								class="form-control AllYearTotlIntrst" name="" value="${FourthYTI}"></td> --%>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlPrincipal"
																									name="" value="${FourthYP}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlInterest"
																									name="" value="${FourthYI}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlAmtSubsidy"
																									name="" value="${FourthYAIS}"></td>
																							</tr>
																							<tr>
																								<td>Year V</td>
																								<td><input type="text" name=""
																									class="form-control" readonly="true"
																									value="${Year5}"></td>
																								<%-- <td><input type="text" readonly="true"
																								placeholder="Enter Amount in INR"
																								class="form-control AllYearTotlIntrst" name="" value="${FifthYTI}"></td> --%>
																								<td><input type="text"
																									placeholder="Enter Amount in INR"
																									readonly="true"
																									class="form-control AllYearTotlPrincipal"
																									name="" value="${FifthYP}"></td>
																								<td><input type="text"
																									placeholder="Enter Amount in INR"
																									readonly="true"
																									class="form-control AllYearTotlInterest"
																									name="" value="${FifthYI}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlAmtSubsidy"
																									name="" value="${FifthYAIS}"></td>
																							</tr>
																							<tr>
																								<td colspan="2" align="right"><strong>Total</strong></td>
																								<!-- <td><input type="text"	class="form-control totalofTI" readonly="true"	name="totalofTI" maxlength="12" ></td> -->
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
																			<td width="50%">Certification from FI/Bank
																				required</td>
																			<td><a
																				href="./downloadDISCISDoc?fileName=${fiBankCertificate}">${fiBankCertificate}</a></td>
																		</tr>
																	</tbody>
																</table>
																<table
																	class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td colspan="2">
																				<h5 class="text-center">Infrastructure Interest
																					Subsidy</h5>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><strong>Bank/Financial
																					Institutions Details from which Loan Availed</strong></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">Name of Banks/Financial
																				Institutions</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${iibankname}"></td>
																		</tr>
																		<tr>
																			<td>Address of Banks/Financial Institutions</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${iisbankadd}"></td>
																		</tr>
																		<tr>
																			<td>Total Loan Taken</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${iistotalLoTkn}"></td>
																		</tr>
																		<tr>
																			<td>Is FI/ Bank certified amount of loan on
																				Infra available?</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${iisbankcert}"></td>
																		</tr>
																		<tr>
																			<td>Amount of loan sanctioned forInvestment in
																				Infrastructure Facilities, as defined</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${iisloan}"></td>
																		</tr>
																		<tr>
																			<td>Sanction Letter, Agreement with FI/Bank</td>
																			<td><a
																				href="./downloadDISIISDoc?fileName=${sectionletteriis}">${sectionletteriis}</a></td>
																		</tr>
																		<tr>
																			<td>Total Interest on Loan</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${iistotalIntrs}"></td>
																		</tr>
																		<tr>
																			<td>Rate of Interest</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${iisroi}"></td>
																		</tr>
																		<tr>
																			<td>Date of Sanction</td>
																			<td><input type="date" class="form-control"
																				readonly="true" name="" value="${iissanctiondate}"></td>
																		</tr>
																		<tr>
																			<td>Amount of Loan Disbursed towards
																				Infrastructure Facilitieswith dates of Disbursement.
																			</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${iisdisloan}"></td>
																		</tr>
																	</tbody>
																</table>
																<table class="table table-bordered">
																	<tbody>
																		<tr>
																			<td colspan="2"><strong>Required
																					Supporting Documents</strong></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">Certificate from FI/Bank
																				certifying loan for Plant & Machinery and interest&
																				other relevant details.</td>
																			<td><a
																				href="./downloadDISIISDoc?fileName=${certifyingLoaniis}">${certifyingLoaniis}</a></td>
																		</tr>
																		<tr>
																			<td>Certificate from FI/Bank for No Default in
																				the account during the entire period for which
																				reimbursement claimed.</td>
																			<td><a
																				href="./downloadDISIISDoc?fileName=${auditedAccountsiis}">${auditedAccountsiis}</a></td>
																		</tr>
																	</tbody>
																</table>
																<table class="table table-bordered">
																	<tbody>
																		<tr>
																			<td colspan="2"><strong>Particulars of
																					Claims for Sanction of Infrastructure Interest
																					Subsidy</strong></td>
																		</tr>
																		<tr>
																			<td colspan="2">
																				<div class="table-responsive">
																					<table class="table table-bordered">
																						<thead>
																							<tr>
																								<th rowspan="2" colspan="2">Year for which
																									subsidy Applied</th>
																								<th colspan="2">Payment made to FI/Bank
																									during the year</th>
																								<th rowspan="2">Amount of Interest Subsidy
																									Applied</th>
																							</tr>
																							<tr>
																								<!-- <th>Total Interest</th> -->
																								<th>Principal</th>
																								<th>Interest on P&M</th>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td>Year I</td>
																								<td><input type="text" readonly="true"
																									name="" class="form-control" value="${Year1i}"></td>
																								<%-- <td><input type="text" readonly="true"
																								placeholder="Enter Amount in INR"
																								class="form-control AllYearTotlIntrstiis"
																								name="" value="${FirstYTIi}"></td> --%>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlPrincipaliis"
																									name="" value="${FirstYPi}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlInterestiis"
																									name="" value="${FirstYIi}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlAmtSubsidyiis"
																									name="" value="${IISFirstYAIS}"></td>
																							</tr>
																							<tr>
																								<td>Year II</td>
																								<td><input type="text" readonly="true"
																									name="" class="form-control" value="${Year2i}"></td>
																								<%-- <td><input type="text" readonly="true"
																								placeholder="Enter Amount in INR"
																								class="form-control AllYearTotlIntrstiis"
																								name="" value="${SecondYTIi}"></td> --%>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlPrincipaliis"
																									name="" value="${SecondYPi}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlInterestiis"
																									name="" value="${SecondYIi}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlAmtSubsidyiis"
																									name="" value="${IISSecondYAIS}"></td>
																							</tr>
																							<tr>
																								<td>Year III</td>
																								<td><input type="text" readonly="true"
																									name="" class="form-control" value="${Year3i}"></td>
																								<%-- <td><input type="text" readonly="true"
																								placeholder="Enter Amount in INR"
																								class="form-control AllYearTotlIntrstiis"
																								name="" value="${ThirdYTIi}"></td> --%>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlPrincipaliis"
																									name="" value="${ThirdYPi}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlInterestiis"
																									name="" value="${ThirdYIi}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlAmtSubsidyiis"
																									name="" value="${IISThirdYAIS}"></td>
																							</tr>
																							<tr>
																								<td>Year IV</td>
																								<td><input type="text" readonly="true"
																									name="" class="form-control" value="${Year4i}"></td>
																								<%-- <td><input type="text" readonly="true"
																								placeholder="Enter Amount in INR"
																								class="form-control AllYearTotlIntrstiis"
																								name="" value="${FourthYTIi}"></td> --%>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlPrincipaliis"
																									name="" value="${FourthYPi}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlInterestiis"
																									name="" value="${FourthYIi}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlAmtSubsidyiis"
																									name="" value="${IISFourthYAIS}"></td>
																							</tr>
																							<tr>
																								<td>Year V</td>
																								<td><input type="text" readonly="true"
																									name="" class="form-control" value="${Year5i}"></td>
																								<%-- <td><input type="text" readonly="true"
																								placeholder="Enter Amount in INR"
																								class="form-control AllYearTotlIntrstiis"
																								name="" value="${FifthYTIi}"></td> --%>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlPrincipaliis"
																									name="" value="${FifthYPi}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlInterestiis"
																									name="" value="${FifthYIi}"></td>
																								<td><input type="text" readonly="true"
																									placeholder="Enter Amount in INR"
																									class="form-control AllYearTotlAmtSubsidyiis"
																									name="" value="${IISFifthYAIS}"></td>
																							</tr>
																							<tr>
																								<td colspan="2" align="right"><strong>Total</strong></td>
																								<!-- <td><input type="text"
																								class="form-control totalofTIiis"
																								readonly="true" name="totalofTI" maxlength="12"></td> -->
																								<td><input type="text"
																									class="form-control totalofPrincipaliis"
																									readonly="true" name="totalofPrincipal"
																									maxlength="12"></td>
																								<td><input type="text"
																									class="form-control totalofInterestiis"
																									readonly="true" name="totalofIntrest"
																									maxlength="12"></td>
																								<td><input type="text"
																									class="form-control totalofAmtSubsidyiis"
																									readonly="true" name="totalofAmtSubsidy"
																									maxlength="12"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td width="50%">Certification from FI/Bank
																				required</td>
																			<td><a
																				href="./downloadDISIISDoc?fileName=${fiBankCertificateiis}">${fiBankCertificateiis}</a></td>
																		</tr>
																	</tbody>
																</table>
																<table
																	class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td colspan="2">
																				<h5 class="text-center">Employees Provident
																					Fund Reimbursement</h5>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><strong>Number of
																					Skilled/Unskilled Workers</strong></td>
																		</tr>
																		<tr>
																			<td colspan="2">
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
																							<c:forEach var="list"
																								items="${proposedEmploymentDetails}"
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
																			</td>
																		</tr>
																		<tr>
																			<td width="50%">Skilled Male Employees</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${skilledEmploymentMale}"></td>
																		</tr>
																		<tr>
																			<td>Skilled Female Employees</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${skilledEmploymentFemale}"></td>
																		</tr>
																		<tr>
																			<td>Total Skilled Employees</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${totalSkilledEmployment}"></td>
																		</tr>
																		<tr>
																			<td>Unskilled Male Employees</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${unSkilledEmploymentMale}"></td>
																		</tr>
																		<tr>
																			<td>Unskilled Female Employees</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${unSkilledEmploymentFemale}"></td>
																		</tr>
																		<tr>
																			<td>Total Unskilled Employees</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${totalUnSkilledEmployment}"></td>
																		</tr>
																		<tr>
																			<td>Total Male Employees</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${grossTotalMaleEmployment}"></td>
																		</tr>
																		<tr>
																			<td>Total Female Employees</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${grossTotalFemaleEmployment}"></td>
																		</tr>
																		<tr>
																			<td>Total Employees</td>
																			<td><input type="text" class="form-control"
																				disabled=""
																				value="${grossTotalMaleandFemaleEmployment}"></td>
																		</tr>
																	</tbody>
																</table>
																<table class="table table-bordered">
																	<tbody>
																		<tr>
																			<td colspan="2"><strong>Details of
																					Claims for EPF Reimbursement</strong></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">EPF Reimbursement (100
																				or more unskilled workers)</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${ISF_Epf_Reim_UW}"></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">Additional 10% EPF
																				Reimbursement (200 direct skilled and unskilled
																				workers)</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${ISF_Add_Epf_Reim_SkUkW}"></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">Additional 10% EPF
																				Reimbursement upto maximum of 70% in case of
																				industrial undertakings having 75% equity owned by
																				Divyang/SC/ST/Females Promoters</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${ISF_Add_Epf_Reim_DIVSCSTF}"></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">Total EPF Reimbursement</td>
																			<td><input type="text" class="form-control"
																				disabled="" value="${ISF_Ttl_EPF_Reim}"></td>
																		</tr>
																	</tbody>
																</table>
																<table class="table table-bordered">
																	<tbody>
																		<tr>
																			<td colspan="2"><strong>Required
																					Supporting Documents</strong></td>
																		</tr>
																		<tr>
																			<td style="width: 50%;">Affidavit from the main
																				promoter/ Authorized <a href="javascript:void(0);"
																				class="remove-row" data-toggle="tooltip" title=""
																				data-original-title="Affidavit from the main promoter/ Authorized Officer to the effect that all the above particulars are true and that the unit had 100 unskilled workers in its continuous employment for the full period of the relevant year for which application for reimbursement is being filed."><i
																					class="fa fa-info-circle text-info"></i></a>
																			</td>
																			<td><a
																				href="./downloadDISEPFDoc?fileName=${affidavitDoc}">${affidavitDoc}</a></td>
																		</tr>
																		<tr>
																			<td>Copy of Form No. 12 required to be filed
																				under the Factories Act, 1948.</td>
																			<td><a
																				href="./downloadDISEPFDoc?fileName=${copyFormDoc}">${copyFormDoc}</a></td>
																		</tr>
																		<tr>
																			<td>Month wise details of contributions paid by
																				Employer into the EPFO or Employer's <a
																				href="javascript:void(0);" class="remove-row"
																				data-toggle="tooltip" title=""
																				data-original-title="Month wise details of contributions paid by Employer into the EPFO or Employer’s PF Trust, which should be certified by the concerned competent officer of EPFO/Competent officer of Trust."><i
																					class="fa fa-info-circle text-info"></i></a>
																			</td>
																			<td><a
																				href="./downloadDISEPFDoc?fileName=${monthwiseDoc}">${monthwiseDoc}</a></td>
																		</tr>
																	</tbody>
																</table>
																<div hidden="">
																	<table
																		class="table table-stripped table-bordered loc-stage-table">
																		<tbody>
																			<tr>
																				<td colspan="2">
																					<h5 class="text-center">Interest Subsidy For
																						Industrial Research, Quality Improvement &
																						Development Of Product</h5>
																				</td>
																			</tr>
																			<tr>
																				<td colspan="2"><strong>Details of
																						Financial Institutions from which loan availed</strong></td>
																			</tr>
																			<tr>
																				<td style="width: 50%;">Name of Financial
																					Institutions</td>
																				<td><input type="text" class="form-control"
																					disabled="" name=""></td>
																			</tr>
																			<tr>
																				<td>Amount of loan sanctioned for establishment
																					of Testing Lab, Quality Certification Lab and Tool
																					Room with dates of Disbursement on Investment</td>
																				<td><input type="text" class="form-control"
																					disabled="" name=""></td>
																			</tr>
																			<tr>
																				<td>Sanction Letter, Agreement with FI/Bank</td>
																				<td><a href="javascript:void();">document-file.pdf</a></td>
																			</tr>
																			<tr>
																				<td>Rate of Interest</td>
																				<td><input type="text" class="form-control"
																					disabled="" name=""></td>
																			</tr>
																			<tr>
																				<td>Sanction Letter, Agreement with FI/Bank</td>
																				<td><a href="javascript:void();">document-file.pdf</a></td>
																			</tr>
																			<tr>
																				<td>Date of Sanction</td>
																				<td><input type="text" class="form-control"
																					disabled="" name=""></td>
																			</tr>
																			<tr>
																				<td>Amount of Loan Disbursed towards
																					establishment of Testing Lab, Quality Certification
																					Lab and Tool Room with dates of Disbursement</td>
																				<td><input type="text" class="form-control"
																					disabled="" name=""></td>
																			</tr>
																		</tbody>
																	</table>
																	<table class="table table-bordered">
																		<tbody>
																			<tr>
																				<td colspan="2"><strong>Required
																						Supporting Documents</strong></td>
																			</tr>
																			<tr>
																				<td style="width: 50%;">Certificate from
																					FI/Bank certifying loan for Plant & Machinery and
																					interest& other relevant details.</td>
																				<td><a href="javascript:void();">document-file.pdf</a></td>
																			</tr>
																			<tr>
																				<td>Certificate from FI/Bank for No Default in
																					the account during the entire period for which
																					reimbursement claimed.</td>
																				<td><a href="javascript:void();">document-file.pdf</a></td>
																			</tr>
																		</tbody>
																	</table>
																	<table class="table table-bordered">
																		<tbody>
																			<tr>
																				<td colspan="2"><strong>Particulars of
																						Claims for Sanction of Industrial Research,
																						Quality Improvement & Development Subsidy</strong></td>
																			</tr>
																			<tr>
																				<td colspan="2">
																					<div class="table-responsive">
																						<table class="table table-bordered">
																							<thead>
																								<tr>
																									<th rowspan="2">Year for which subsidy
																										Applied</th>
																									<th colspan="2">Payment made to FI/Bank
																										during the year</th>
																									<th rowspan="2">Amount of Interest Subsidy
																										Applied</th>
																								</tr>
																								<tr>
																									<th>Principal</th>
																									<th>Interest on P&M</th>
																								</tr>
																							</thead>
																							<tbody>
																								<tr>
																									<td>Year –I &nbsp;&nbsp;<input
																										type="text" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																								</tr>
																								<tr>
																									<td>Year –II &nbsp;<input type="text"
																										name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																								</tr>
																								<tr>
																									<td>Year –III <input type="text" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																								</tr>
																								<tr>
																									<td>Year –IV <input type="text" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																								</tr>
																								<tr>
																									<td>Year –V <input type="text" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																									<td><input type="text"
																										placeholder="Enter Amount in INR"
																										class="form-control" name=""></td>
																								</tr>
																								<tr>
																									<td><strong>Total</strong></td>
																									<td></td>
																									<td></td>
																									<td></td>
																								</tr>
																							</tbody>
																						</table>
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td width="50%">Certification from FI/Bank
																					required</td>
																				<td><a href="javascript:void();">document-file.pdf</a></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
																<table class="table table-bordered">
																	<td>
																		<h5 class="text-center">Stamp Duty
																			Exemption/Reimbursement</h5>
																	</td>
																</table>
																<table class="table table-bordered">
																	<thead>
																		<tr>
																			<th width="40%">Incentives Sought</th>
																			<th width="30%">Claimed Amount <small>(As
																					per LoC)</small></th>
																			<th>Avail Amount</th>
																		</tr>
																	</thead>
																	<tbody>
																		<tr>
																			<td>Amount of Stamp Duty Exemption</td>
																			<td><input type="text" class="form-control"
																				name="" value="${ExemptionClaimAmount}"
																				readonly="true" placeholder=""></td>
																			<td><input type="text" class="form-control"
																				readonly="true" name=""
																				value="${ExemptionAvailAmount}" placeholder=""></td>
																		</tr>
																		<tr>
																			<td>Amount of Stamp Duty Reimbursement</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name=""
																				value="${ReimbursementClaimAmount}" placeholder=""></td>
																			<td><input type="text" class="form-control"
																				readonly="true" name=""
																				value="${ReimbursementAvailAmount}" placeholder=""></td>
																		</tr>
																		<tr>
																			<td>Additional Stamp Duty exemption @20% upto
																				maximum of 100% in case of industrial undertakings
																				having 75% equity owned by Divyang/SC/ ST/Females
																				Promoters</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${StampClaimAmount}"
																				placeholder=""></td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${StampAvailAmount}"
																				placeholder=""></td>
																		</tr>
																		<tr>
																			<td><strong>Total Stamp Duty
																					Exemption/Reimbursement</strong></td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${TotalClaimAmount}"
																				placeholder=""></td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${TotalAvailAmount}"
																				placeholder=""></td>
																		</tr>
																	</tbody>
																</table>
																<table class="table table-bordered">
																	<td>
																		<h5 class="text-center">Other Incentive</h5>
																	</td>
																</table>
																<table class="table table-bordered">
																	<thead>
																		<tr>
																			<th width="50%">Incentives Sought</th>
																			<th>Avail Amount</th>
																		</tr>
																	</thead>
																	<tbody>
																		<tr>
																			<td>Reimbursement of Disallowed Input Tax Credit
																				on plant, building materials, and other capital
																				goods.</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name=""
																				value="${ReimDissAllowedAvailAmt}" placeholder=""></td>
																		</tr>
																		<tr>
																			<td>Exemption from Electricity Duty from captive
																				power for self-use</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name=""
																				value="${ExCapitivePowerAmt}" placeholder=""></td>
																		</tr>
																		<tr>
																			<td>Exemption from Electricity duty on power
																				drawn from power companies</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${ExePowerDrawnAmt}"
																				placeholder=""></td>
																		</tr>
																		<tr>
																			<td>Exemption from Mandi Fee</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${ExeMandiFreeAmt}"
																				placeholder=""></td>
																		</tr>
																		<tr>
																			<td>Industrial units providing employment to
																				differently abled workers will be provided payroll
																				assistance of Rs. 500 per month for each such worker</td>
																			<td><input type="text" class="form-control"
																				readonly="true" name="" value="${IndustrialUnitAmt}"
																				placeholder=""></td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<!-- End view All Application Details  -->



										<!-- Start Annexure-I  -->

										<div class="parameter-properties" id="Annexure-II">
											<hr class="mt-2">
											<h4
												class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Annexure-II</h4>
											<p
												class="mt-4 mb-1 text-center animate__animated animate__fadeInDown">
												<strong>APPLICATION FORM FOR SANCTION/DISBURSAL OF
													INCENTIVES FOR SMALL, MEDIUM OR LARGE INDUSTRIAL
													UNDERTAKINGS WHERE LETTER OF COMFORT HAS BEEN ISSUED </strong>
											</p>




											<div class="row">
												<div class="col-sm-12">
													<p class="mb-2 mt-4">
														<strong>1. Information of Industrial Undertaking</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th width="7%">Sl No</th>
																	<th width="40%">Particulars</th>
																	<th>Details /Documents</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>i)</td>
																	<td>Name& Address of the Applicant</td>
																	<td>${businessEntityName}<br>
																		${businessAddress}
																	</td>
																</tr>
																<tr>
																	<td>ii)</td>
																	<td>Location of the Industrial Undertaking</td>
																	<td>${fullAddress}</td>
																</tr>
																<tr>
																	<td>iii)</td>
																	<td>Phase-wise details of Actual Investment and
																		dates of start of commercial production (Enclose
																		certificate from concerned Dy. Commissioner,
																		Industries, District Industries & Enterprise Promotion
																		Centre or Chartered Accountant</td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>
													<p class="mb-2 mt-4">
														<strong>2. Details of Eligible Capital Investment
															in Industrial Undertaking</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th>Item</th>
																	<th>Amount of Capital Investment in New Project</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1</td>
																	<td>Land Cost</td>
																	<td><input type="text" value="${newprjLandCost}"
																		class="form-control" disabled=""></td>
																</tr>
																<tr>
																	<td>2</td>
																	<td>Building Site Development & Civil Works Cost</td>
																	<td><input type="text"
																		value="${newprjBuildingCost}" class="form-control"
																		disabled=""></td>
																</tr>
																<tr>
																	<td>3</td>
																	<td>Plant and Machinery</td>
																	<td><input type="text"
																		value=${newprjPlantAndMachCost }" class="form-control"
																		disabled=""></td>
																</tr>
																<tr>
																	<td>4</td>
																	<td>Infrastructure Facilities</td>
																	<td><input type="text" value="${newprjInfCost}"
																		class="form-control" disabled=""></td>
																</tr>
																<tr>
																	<td></td>
																	<td>Total</td>
																	<td><input type="text" value="7573200000"
																		class="form-control" disabled=""></td>
																</tr>
															</tbody>
														</table>
													</div>

													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th>Item</th>
																	<th>Amount of Capital Investment in Existing
																		Project</th>
																	<th>Expansion/Diversification</th>
																	<th>% of increase under Expansion/Diversification</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1</td>
																	<td>Land</td>
																	<td><input type="text" disabled=""
																		value="${invLandCost}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${exLand}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${projLandIncrement}" class="form-control"
																		name=""></td>
																</tr>
																<tr>
																	<td>2</td>
																	<td>Building</td>
																	<td><input type="text" disabled=""
																		value="${invBuildingCost}" class="form-control"
																		name=""></td>
																	<td><input type="text" disabled=""
																		value="${invBuildingCost}" class="form-control"
																		name=""></td>
																	<td><input type="text" disabled=""
																		value="${projBuildIncrement}" class="form-control"
																		name=""></td>
																</tr>
																<%--  <tr>
                                <td>3</td>
                                <td>Other Construction</td> 
                                <td><input type="text" disabled="" value="${invOtherCost}"  class="form-control" name=""></td>
                                <td><input type="text" disabled=""  value="${projDisConstruct}" class="form-control" name=""></td>
                                <td><input type="text" disabled=""  value="${projConstructIncrement}" class="form-control" name=""></td>
                              </tr> --%>
																<tr>
																	<td>3</td>
																	<td>Plant and Machinery</td>
																	<td><input type="text" disabled=""
																		value="${invPlantAndMachCost}" class="form-control"
																		name=""></td>
																	<td><input type="text" disabled=""
																		value="${explant}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${projPlantIncrement}" class="form-control"
																		name=""></td>
																</tr>
																<tr>
																	<td>4</td>
																	<td>Infrastructure Facilities</td>
																	<td><input type="text" disabled=""
																		value="${newprjInfCost}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${exinfra}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${projInfraIncrement}" class="form-control"
																		name=""></td>
																</tr>
																<tr>
																	<td></td>
																	<td>Total</td>
																	<td><input type="text" disabled=""
																		value="${ttlAmtExitProj}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${totalAmountOfInvest}" class="form-control"
																		name=""></td>
																	<td><input type="text" disabled=""
																		value="${totalIncrement}" class="form-control" name=""></td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-0">
														<strong>Note:-</strong>
													</p>
													<p>Relevant documents be provided alongwith the
														Statutory Auditor's Certificate for Capital Investment
														made as above in accordance with provisions of the
														Rules/GOs / MSMED Act, 2006</p>

													<p class="mb-0">
														<strong>In case of Large Industrial Undertaking</strong>
													</p>
													<p>1. Nodal agency shall arrange examination &
														certification on Capital Investment made by the company as
														per the provision of G/O’s through its empanelled C/A
														firms</p>
													<p>2. Nodal agency shall also arrange to get examined
														the installation & verification of Capital Investment at
														site (Land, Building & Plant & Machinery) through its
														empanelled consultants/ valuer/engineer.</p>
													<p>The above two reports shall be put up at the stage
														of commencement of disbursal of benefits before the
														Competent Committee for determination of the quantum of
														capital investment.</p>

													<p class="mb-2 mt-4">
														<strong>3. Details of Capital Investment in
															Industrial Undertaking (Rs. in crore)</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-stripped table-bordered"
															id="BreakUpTable">
															<thead>
																<tr>
																	<th rowspan="3">Sl No</th>
																	<th rowspan="3">Item</th>
																	<th rowspan="3">Cost of Project As per DPR</th>
																	<th rowspan="3">Cost of Project As per Appraisal</th>
																	<th colspan="5" class="text-center">Actual
																		Investment <small>(As per certificate from
																			Statutory Auditors)</small>
																	</th>
																</tr>
																<tr>

																	<th rowspan="2">Before Cut Off Date</th>
																	<th rowspan="2" width="20%">Cut Off date to the
																		date of commence ment of commercial production <small>(
																			in case of phase, give investment phasewise)</small>
																	</th>
																	<th colspan="2">Date of commence ment of final
																		phase of commercial production to till date</th>
																	<th rowspan="2">Total</th>
																</tr>
																<tr>
																	<th>10%</th>
																	<th>Balance (90%)</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1</td>
																	<td>2</td>
																	<td>3</td>
																	<td>4</td>
																	<td>5</td>
																	<td>6</td>
																	<td>7</td>
																	<td>8</td>
																	<td>9</td>
																</tr>
																<tr>
																	<td>i.</td>
																	<td>Land and Site Development</td>
																	<td><input type="text" class="form-control"
																		name="" value="${invLandCost}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalLC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateLC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdLC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvLC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvLC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalLC}" readonly="true"></td>
																</tr>
																<tr>
																	<td>ii.</td>
																	<td>Building and Civil Works</td>
																	<td><input type="text" class="form-control"
																		name="" value="${invBuildingCost}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalBC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateBC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdBC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvBC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvBC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalBC}" readonly="true"></td>
																</tr>
																<tr>
																	<td>iii.</td>
																	<td>Plant and Machinery</td>
																	<td><input type="text" class="form-control"
																		name="" value="${invPlantAndMachCost}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalPMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDatePMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdPMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvPMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvPMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalPMC}" readonly="true"></td>
																</tr>
																<tr>
																	<td>iv.</td>

																	<td>Miscellaneous Fixed Assets</td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvDPRMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalMFA}" readonly="true"></td>
																</tr>
																<tr>
																	<td>v.</td>
																	<td>Technical Knowhow Fee</td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvDPRTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalTKF}" readonly="true"></td>
																</tr>
																<tr>
																	<td>vi.</td>
																	<td>Interest During Construction Period</td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvDPRICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalICP}" readonly="true"></td>
																</tr>
																<tr>
																	<td>vii.</td>

																	<td>Prel. And Preoperative Expenses</td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvDPRPPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalPPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDatePPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdPPPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvPPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvPPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalPPE}" readonly="true"></td>
																</tr>
																<tr>
																	<td>viii.</td>
																	<td>Margin Money for Working Capital</td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvDPRMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalMMC}" readonly="true"></td>
																</tr>
																<tr>
																	<td>ix.</td>
																	<td><strong>Total:</strong></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlCostDpr}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlcostProAppFrBank}"
																		readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlBeforeCutOff}"
																		readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlCutOffCommProduction}"
																		readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlActualInvestment}"
																		readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlAddiActualInvestment}"
																		readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.disValueTotalOfTotal}"
																		readonly="true"></td>
																</tr>
															</tbody>
														</table>
													</div>
													<p class="mb-2 mt-4">
														<strong>4.1 Reimbursement Of Deposited GST</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th>Details of tax paid under GST Act</th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>i)</td>
																	<td>GSTIN issued by Commercial Taxes Department
																		--> ${gstComTaxDept}</td>
																	<td rowspan="4">Certificate issued by Competent
																		Authority in support of GST</td>
																</tr>
																<tr>
																	<td>ii)</td>
																	<td>Amount of Total GST deposited for FY ${FYear}</td>
																</tr>
																<tr>
																	<td>iii)</td>
																	<td>Amount of admitted Tax on manufactured goods
																		under Deposited GST for FY ${FYear} is
																		${amtAdmtTaxDeptGst}</td>
																</tr>
																<tr>
																	<td>iv)</td>
																	<td>Eligible amount of deposited GST for
																		reimbursement for the FY ${FYear} is ${amtNetSgst}</td>
																</tr>
															</tbody>
														</table>
													</div>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<th>Sl No</th>
																<th>Documents Required in Support of GST Paid by
																	the Unit</th>
															</thead>
															<tbody>
																<tr>
																	<td>i.</td>
																	<td>Certificate issued by Competent Authority of
																		the Commercial Taxes Department.</td>
																</tr>
																<tr>
																	<td>ii.</td>
																	<td>Unit level audited accounts for the relevant
																		financial year (for which GST reimbursement is being
																		claimed)</td>
																</tr>
																<tr>
																	<td>iii.</td>
																	<td>GST Audit Report for the relevant financial
																		year for the company</td>
																</tr>
																<tr>
																	<td>iv.</td>
																	<td>GSTAudit Report for the relevant financial
																		year for the unit (standalone GST statement/report for
																		the unit certified by a Chartered Accountant)</td>
																</tr>
																<tr>
																	<td>v.</td>
																	<td>CA Certificate for sales reconciliation of
																		Manufactured Goods/Trading goods/Scrap/ Stock Transfer
																		and GST paid towards the same separately.</td>
																</tr>
															</tbody>
														</table>
													</div>
													<p class="mb-2 mt-4">
														<strong>4.2 Capital Interest Subsidy</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th></th>
																	<th width="30%"></th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>

																	<td>4.2.1</td>
																	<td>Name & Address of Banks/Financial Institutions
																		from which loan availed</td>
																	<td>${bankname} <br> ${bankadd}
																	</td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.2.2</td>
																	<td>Amount of loan sanctioned on Investment in
																		Plant & Machinery</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${pnmloan}"></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>4.2.3</td>
																	<td>Rate of Interest</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${roi}"></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>4.2.4</td>
																	<td>Date of Sanction</td>
																	<td><input type="date" class="form-control"
																		readonly="true" name="" value="${sanctiondate}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.2.5</td>
																	<td>Amount of Loan Disbursed towards Investment in
																		Plant & Machinery with dates of Disbursement.</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${disloan}"></td>
																	<td>1. Certificate from FI/Bank certifying loan
																		for Plant & Machinery and interest& other relevant
																		details. <br>2. Certificate from FI/Bank for No
																		Default in the account during the entire period for
																		which reimbursement claimed.
																	</td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>4.2.6 Particulars of Claims for Sanction
															of Capital Interest Subsidy</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th rowspan="2">Sl No</th>
																	<th rowspan="2">Year for which subsidy Applied</th>
																	<th colspan="2">Payment made to FI/Bank during the
																		year</th>
																	<th rowspan="2">Amount of Interest Subsidy Applied</th>
																	<th rowspan="2">Documents required in support</th>
																</tr>
																<tr>
																	<th>Principal</th>
																	<th>Interest</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1.</td>
																	<td>Year –I ${Year1 }</td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipal" name=""
																		readonly="true" value="${SecondYP}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlInterest" name=""
																		value="${SecondYI}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${SecondYAIS}"></td>
																	<td>Certification from FI/Bank required</td>
																</tr>
																<tr>
																	<td>2.</td>
																	<td>Year –II ${Year2 }</td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipal" name=""
																		readonly="true" value="${SecondYP}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlInterest" name=""
																		value="${SecondYI}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${SecondYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>3.</td>
																	<td>Year –III ${Year3 }</td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlPrincipal" name=""
																		value="${ThirdYP}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlInterest" name=""
																		value="${ThirdYI}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${ThirdYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.</td>
																	<td>Year –IV ${Year4 })</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipal" name=""
																		value="${FourthYP}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterest" name=""
																		value="${FourthYI}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${FourthYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>5.</td>
																	<td>Year –V ${Year5 }</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipal" name=""
																		value="${FourthYP}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterest" name=""
																		value="${FourthYI}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${FourthYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td></td>
																	<td><strong>Total</strong></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlPrincipal" name=""
																		value="${FifthYP}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlInterest" name=""
																		value="${FifthYI}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${FifthYAIS}"></td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>4.3 Infrastructure Interest Subsidy</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th></th>
																	<th width="30%"></th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>4.3.1</td>
																	<td>Name& Address of Banks/Financial Institutions
																		from which loan availed</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${iibankname}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.3.2</td>
																	<td>Amount of loan sanctioned for Investmentin
																		Infrastructure Facilities as defined</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${iisloan}"></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>4.3.3</td>
																	<td>Rate of Interest</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${iisroi}"></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>4.3.4</td>
																	<td>Date of Sanction</td>
																	<td><input type="date" class="form-control"
																		readonly="true" name="" value="${iissanctiondate}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.3.5</td>
																	<td>Amount of Loan Disbursed towards Investment in
																		Infrastructure Facilities with dates of Disbursement.</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${iisdisloan}"></td>
																	<td>1. Certificate from FI/Bank certifying loan
																		for Plant & Machinery and interest& other relevant
																		details. <br>2. Certificate from FI/ Bank for No
																		Default in the account during the entire period for
																		which reimbursement claimed.
																	</td>

																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>4.3.6 Particulars of Claims for Sanction
															of Infrastructure Interest Subsidy</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th rowspan="2">Sl No</th>
																	<th rowspan="2">Year for which subsidy Applied</th>
																	<th colspan="2">Payment made to FI/Bank during the
																		year</th>
																	<th rowspan="2">Amount of Interest Subsidy Applied</th>
																	<th rowspan="2">Documents required in support</th>
																</tr>
																<tr>
																	<th>Principal</th>
																	<th>Interest</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1.</td>
																	<td>Year –I ${Year1i })</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipaliis" name=""
																		value="${FirstYPi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterestiis" name=""
																		value="${FirstYIi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidyiis" name=""
																		value="${IISFirstYAIS}"></td>
																	<td>Certification from FI/Bank required</td>
																</tr>
																<tr>
																	<td>2.</td>
																	<td>Year –II ${Year2i }</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipaliis" name=""
																		value="${SecondYPi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterestiis" name=""
																		value="${SecondYIi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidyiis" name=""
																		value="${IISSecondYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>3.</td>
																	<td>Year –III ${Year3i })</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipaliis" name=""
																		value="${ThirdYPi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterestiis" name=""
																		value="${ThirdYIi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidyiis" name=""
																		value="${IISThirdYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.</td>
																	<td>Year –IV ${Year4i })</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipaliis" name=""
																		value="${FourthYPi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterestiis" name=""
																		value="${FourthYIi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidyiis" name=""
																		value="${IISFourthYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>5.</td>
																	<td>Year –V ${Year5i }</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipaliis" name=""
																		value="${FifthYPi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterestiis" name=""
																		value="${FifthYIi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidyiis" name=""
																		value="${IISFifthYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td></td>
																	<td><strong>Total</strong></td>
																	<td><input type="text"
																		class="form-control totalofPrincipaliis"
																		readonly="true" name="totalofPrincipal" maxlength="12"></td>
																	<td><input type="text"
																		class="form-control totalofInterestiis"
																		readonly="true" name="totalofIntrest" maxlength="12"></td>
																	<td><input type="text"
																		class="form-control totalofAmtSubsidyiis"
																		readonly="true" name="totalofAmtSubsidy"
																		maxlength="12"></td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>5.1 Employees Provident Fund
															Reimbursement</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th></th>
																	<th width="30%"></th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>5.1.1</td>
																	<td>Number of Unskilled Workers with full
																		particulars of 100 unskilled workers and employee wise
																		contributions for relevant year.</td>
																	<td>${unskill}<br>Rs. ${epfreim}
																	</td>
																	<td>Affidavit from the main promoter/Authorized
																		Officer to the effect that all the above particulars
																		are true and that the unit had 100 unskilled workers
																		in its continuous employment for the full period of
																		the relevant year for which application for
																		reimbursement is being filed. <br> Copy of Form
																		No. 12 required to be filed under the Factories Act,
																		1948.
																	</td>
																</tr>
																<tr>
																	<td>5.1.2</td>
																	<td>Number of Other Workers</td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>5.1.3</td>
																	<td>Total No. of Workers</td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>5.1.4</td>
																	<td>Details of Claims for EPF Reimbursement</td>
																	<td></td>
																	<td>Month wise details of contributions paid by
																		employer into the EPFO or Employer’s PF Trust, which
																		should be certified by the concerned competent officer
																		of EPFO/Competent officer of Trust.</td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>6.1 Interest Subsidy For Industrial
															Research, Quality Improvement & Development Of Product</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th></th>
																	<th width="30%"></th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>6.1.1</td>
																	<td>Name & Address of Banks/Financial Institutions
																		from which loan availed</td>
																	<td></td>
																	<td>Affidavit from the main promoter/Authorized
																		Officer to the effect that all the above particulars
																		are true and that the unit had 100 unskilled workers
																		in its continuous employment for the full period of
																		the relevant year for which application for
																		reimbursement is being filed. <br> Copy of Form
																		No. 12 required to be filed under the Factories Act,
																		1948.
																	</td>
																</tr>
																<tr>
																	<td>6.1.2</td>
																	<td>Amount of loan sanctioned for Establishment of
																		Testing Lab, Quality Certification Lab and Tool Room</td>
																	<td></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>6.1.3</td>
																	<td>Rate of Interest</td>
																	<td></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>6.1.4</td>
																	<td>Date of Sanction</td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>6.1.5</td>
																	<td>Amount of Loan Disbursed towards Investment
																		for Establishment of Testing Lab, Quality
																		Certification Lab and Tool Room with dates of
																		Disbursement.</td>
																	<td></td>
																	<td>1. Certificate from Bank certifying loan for
																		Plant & Machinery and interest& other relevant
																		details. <br> 2. Certificate of No Default in the
																		account during the entire period for which
																		reimbursement claimed.
																	</td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>6.2.1 Particulars of Claims for Sanction
															of Industrial Research, Quality Improvement & Development
															Subsidy</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th rowspan="2">Sl No</th>
																	<th rowspan="2">Year for which subsidy Applied</th>
																	<th colspan="2">Payment made to FI during the year</th>
																	<th rowspan="2">Amount of Interest Subsidy Applied</th>
																	<th rowspan="2">Documents required in support</th>
																</tr>
																<tr>
																	<th>Principal</th>
																	<th>Interest</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1.</td>
																	<td>Year –I
																		&nbsp;&nbsp;(&nbsp;&nbsp;&nbsp;&nbsp;)</td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td>Certification from FI/Bank required</td>
																</tr>
																<tr>
																	<td>2.</td>
																	<td>Year –II &nbsp;(&nbsp;&nbsp;&nbsp;&nbsp;)</td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>3.</td>
																	<td>Year –III (&nbsp;&nbsp;&nbsp;&nbsp;)</td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.</td>
																	<td>Year –IV (&nbsp;&nbsp;&nbsp;&nbsp;)</td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>5.</td>
																	<td>Year –V (&nbsp;&nbsp;&nbsp;&nbsp;)</td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td></td>
																	<td><strong>Total</strong></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>

													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>7.</td>
																	<td>Particulars of Workers from BPL families with
																		supporting documents (Employee Distinctive Numbers,
																		EPFO Number, BPL Card, Employee Payroll etc.)</td>
																</tr>
																<tr>
																	<td>8.</td>
																	<td>Particulars of Workers from SC/ST families
																		with supporting documents and Employee Payroll</td>
																</tr>
																<tr>
																	<td>9.</td>
																	<td>Particulars of female workers with supporting
																		documents</td>
																</tr>
																<tr>
																	<td>10.</td>
																	<td>Detailed Calculation of Stamp Duty Exemption
																		Availed</td>
																</tr>
																<tr>
																	<td>11.</td>
																	<td>Detailed Calculation of Mandi Fee Exemption
																		availed</td>
																</tr>
																<tr>
																	<td>12.</td>
																	<td>Detailed Calculation of Electricity Duty
																		Exemption availed</td>
																</tr>
																<tr>
																	<td></td>
																	<td>
																		<p class="text-center">
																			<strong>Declaration</strong>
																		</p> The above information are completely true and no fact
																		has been concealed or misrepresented. It is further
																		certified that the company has not applied for
																		benefits of the above nature under any sector-specific
																		or other policy of the Govt of UP for purpose of
																		availing benefits of the above nature. I/we hereby
																		agree that I/we shall forthwith repay the benefits
																		released to me/us under Rules of Policy for Promotion
																		of Industrial Investment and Employment-2017, if the
																		said benefits are found to be disbursed in excess of
																		the amount actually admissible whatsoever the reason.
																	</td>
																</tr>
																<tr>
																	<td></td>
																	<td>
																		<div class="table-responsive">
																			<table class="table">
																				<tr>
																					<td><strong>Date:</strong><br> <strong>Place:</strong>
																					</td>
																					<td class="text-right"><strong>Signature
																							of Authorised Signatory with</strong><br> <strong>Name,
																							Designation and Office Seal</strong></td>
																				</tr>
																			</table>
																		</div>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>

												</div>
											</div>

										</div>

										<!-- End Annexure-II  -->


										<!-- Start Annexure-IV -->

										<div class="parameter-properties" id="Annexure-IV">
											<hr class="mt-2">
											<h4
												class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Annexure-IV</h4>
											<p
												class="mt-4 mb-1 text-center animate__animated animate__fadeInDown">
												<strong>APPLICATION FORM FOR DISBURSEMENT OF
													INCENTIVES FOR MEGA, MEGA PLUS AND SUPER MEGA INDUSTRIAL
													UNDERTAKINGS</strong>
											</p>




											<div class="row">
												<div class="col-sm-12">
													<p class="mb-2 mt-4">
														<strong>1. Information of Industrial Undertaking</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th width="7%">Sl No</th>
																	<th width="40%">Particulars</th>
																	<th>Details /Documents</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>i)</td>
																	<td>Name& Address of the Applicant</td>
																	<td>${businessEntityName}<br>
																		${businessAddress}
																	</td>
																</tr>
																<tr>
																	<td>ii)</td>
																	<td>Location of the Industrial Undertaking</td>
																	<td>${fullAddress}</td>
																</tr>
																<tr>
																	<td>iii)</td>
																	<td>Phase-wise details of Actual Investment and
																		dates of start of commercial production (Enclose
																		certificate from concerned Dy. Commissioner,
																		Industries, District Industries & Enterprise Promotion
																		Centre or Chartered Accountant</td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>
													<p class="mb-2 mt-4">
														<strong>2. Details of Eligible Capital Investment
															in Industrial Undertaking</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th>Item</th>
																	<th>Amount of Capital Investment in New Project</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1</td>
																	<td>Land Cost</td>
																	<td><input type="text" value="732300000"
																		class="form-control" disabled=""></td>
																</tr>
																<tr>
																	<td>2</td>
																	<td>Building Site Development & Civil Works Cost</td>
																	<td><input type="text" value="1345800000"
																		class="form-control" disabled=""></td>
																</tr>
																<tr>
																	<td>3</td>
																	<td>Plant and Machinery</td>
																	<td><input type="text" value="5495100000"
																		class="form-control" disabled=""></td>
																</tr>
																<tr>
																	<td>4</td>
																	<td>Infrastructure Facilities</td>
																	<td><input type="text" value="0"
																		class="form-control" disabled=""></td>
																</tr>
																<tr>
																	<td></td>
																	<td>Total</td>
																	<td><input type="text" value="7573200000"
																		class="form-control" disabled=""></td>
																</tr>
															</tbody>
														</table>
													</div>

													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th>Item</th>
																	<th>Amount of Capital Investment in Existing
																		Project</th>
																	<th>Expansion/Diversification</th>
																	<th>% of increase under Expansion/Diversification</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1</td>
																	<td>Land</td>
																	<td><input type="text" disabled=""
																		value="${invLandCost}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${exLand}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${projLandIncrement}" class="form-control"
																		name=""></td>
																</tr>
																<tr>
																	<td>2</td>
																	<td>Building</td>
																	<td><input type="text" disabled=""
																		value="${invBuildingCost}" class="form-control"
																		name=""></td>
																	<td><input type="text" disabled=""
																		value="${invBuildingCost}" class="form-control"
																		name=""></td>
																	<td><input type="text" disabled=""
																		value="${projBuildIncrement}" class="form-control"
																		name=""></td>
																</tr>




																<%-- <tr>
                                <td>3</td>
                                <td>Other Construction</td>
                                <td><input type="text" disabled="" value="${invOtherCost}" class="form-control" name=""></td>
                                <td><input type="text" disabled="" value="${projDisConstruct}" class="form-control" name=""></td>
                                <td><input type="text" disabled="" value="${projConstructIncrement}" class="form-control" name=""></td>
                              </tr> --%>
																<tr>
																	<td>3</td>
																	<td>Plant and Machinery</td>
																	<td><input type="text" disabled=""
																		value="${invPlantAndMachCost}" class="form-control"
																		name=""></td>
																	<td><input type="text" disabled=""
																		value="${explant}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${projPlantIncrement}" class="form-control"
																		name=""></td>
																</tr>
																<tr>
																	<td>4</td>
																	<td>Infrastructure Facilities</td>
																	<td><input type="text" disabled=""
																		value="${newprjInfCost}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${exinfra}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${projInfraIncrement}" class="form-control"
																		name=""></td>
																</tr>
																<tr>
																	<td></td>
																	<td>Total</td>
																	<td><input type="text" disabled=""
																		value="${ttlAmtExitProj}" class="form-control" name=""></td>
																	<td><input type="text" disabled=""
																		value="${totalAmountOfInvest}" class="form-control"
																		name=""></td>
																	<td><input type="text" disabled=""
																		value="${totalIncrement}" class="form-control" name=""></td>
																</tr>
															</tbody>
														</table>


													</div>
													<p class="mb-0">
														<strong>Note:-</strong>
													</p>
													<p>Relevant documents be provided alongwith the
														Statutory Auditor’s Certificate for Capital Investment
														made as above in accordance with provisions of the
														Rules/GOs / MSMED Act, 2006</p>

													<p class="mb-0">
														<strong>In case of Large Industrial Undertaking</strong>
													</p>
													<p>1. Nodal agency shall arrange examination &
														certification on Capital Investment made by the company as
														per the provision of G/O’s through its empanelled C/A
														firms</p>
													<p>2. Nodal agency shall also arrange to get examined
														the installation & verification of Capital Investment at
														site (Land, Building & Plant & Machinery) through its
														empanelled consultants/ valuer/engineer.</p>
													<p>The above two reports shall be put up at the stage
														of commencement of disbursal of benefits before the
														Competent Committee for determination of the quantum of
														capital investment.</p>

													<p class="mb-2 mt-4">
														<strong>3. Details of Capital Investment in
															Industrial Undertaking (Rs. in crore)</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-stripped table-bordered"
															id="BreakUpTable">
															<thead>
																<tr>
																	<th rowspan="3">Sl No</th>
																	<th rowspan="3">Item</th>
																	<th rowspan="3">Cost of Project As per DPR</th>
																	<th rowspan="3">Cost of Project As per Appraisal</th>
																	<th colspan="5" class="text-center">Actual
																		Investment <small>(As per certificate from
																			Statutory Auditors)</small>
																	</th>
																</tr>
																<tr>

																	<th rowspan="2">Before Cut Off Date</th>
																	<th rowspan="2" width="20%">Cut Off date to the
																		date of commence ment of commercial production <small>(
																			in case of phase, give investment phasewise)</small>
																	</th>
																	<th colspan="2">Date of commence ment of final
																		phase of commercial production to till date</th>
																	<th rowspan="2">Total</th>
																</tr>
																<tr>
																	<th>10%</th>
																	<th>Balance (90%)</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1</td>
																	<td>2</td>
																	<td>3</td>
																	<td>4</td>
																	<td>5</td>
																	<td>6</td>
																	<td>7</td>
																	<td>8</td>
																	<td>9</td>
																</tr>
																<tr>
																	<td>i.</td>
																	<td>Land and Site Development</td>
																	<td><input type="text" class="form-control"
																		name="" value="${invLandCost}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalLC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateLC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdLC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvLC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvLC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalLC}" readonly="true"></td>
																</tr>
																<tr>
																	<td>ii.</td>
																	<td>Building and Civil Works</td>
																	<td><input type="text" class="form-control"
																		name="" value="${invBuildingCost}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalBC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateBC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdBC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvBC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvBC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalBC}" readonly="true"></td>
																</tr>
																<tr>
																	<td>iii.</td>
																	<td>Plant and Machinery</td>
																	<td><input type="text" class="form-control"
																		name="" value="${invPlantAndMachCost}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalPMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDatePMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdPMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvPMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvPMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalPMC}" readonly="true"></td>
																</tr>
																<tr>
																	<td>iv.</td>
																	<td>Miscellaneous Fixed Assets</td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvDPRMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvMFA}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalMFA}" readonly="true"></td>
																</tr>
																<tr>
																	<td>v.</td>
																	<td>Technical Knowhow Fee</td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvDPRTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvTKF}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalTKF}" readonly="true"></td>
																</tr>
																<tr>
																	<td>vi.</td>
																	<td>Interest During Construction Period</td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvDPRICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvICP}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalICP}" readonly="true"></td>
																</tr>
																<tr>
																	<td>vii.</td>
																	<td>Prel. And Preoperative Expenses</td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvDPRPPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalPPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDatePPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdPPPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvPPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvPPE}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalPPE}" readonly="true"></td>
																</tr>
																<tr>
																	<td>viii.</td>
																	<td>Margin Money for Working Capital</td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvDPRMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAppraisalMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCutoffDateMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvCommProdMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvActualInvMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${CapInvAddlInvMMC}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capInvTotalMMC}" readonly="true"></td>
																</tr>
																<tr>
																	<td>ix.</td>
																	<td><strong>Total:</strong></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlCostDpr}" readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlcostProAppFrBank}"
																		readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlBeforeCutOff}"
																		readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlCutOffCommProduction}"
																		readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlActualInvestment}"
																		readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.ttlAddiActualInvestment}"
																		readonly="true"></td>
																	<td><input type="text" class="form-control"
																		name="" value="${capinv.disValueTotalOfTotal}"
																		readonly="true"></td>
																</tr>
															</tbody>
														</table>
													</div>
													<p class="mb-2 mt-4">
														<strong>4.1 Reimbursement Of Deposited GST</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th>Details of tax paid under GST Act</th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>i)</td>
																	<td>GSTIN issued by Commercial Taxes Department
																		--> ${gstComTaxDept}</td>
																	<td rowspan="4">Certificate issued by Competent
																		Authority in support of GST</td>
																</tr>
																<tr>
																	<td>ii)</td>
																	<td>Amount of Total GST deposited for FY ${FYear}</td>
																</tr>
																<tr>
																	<td>iii)</td>
																	<td>Amount of admitted Tax on manufactured goods
																		under Deposited GST for FY ${FYear} is
																		${amtAdmtTaxDeptGst}</td>
																</tr>
																<tr>
																	<td>iv)</td>
																	<td>Eligible amount of deposited GST for
																		reimbursement for the FY ${FYear} is ${amtNetSgst}</td>
																</tr>
															</tbody>
														</table>
													</div>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<th>Sl No</th>
																<th>Documents Required in Support of GST Paid by
																	the Unit</th>
															</thead>
															<tbody>
																<tr>
																	<td>i.</td>
																	<td>Certificate issued by Competent Authority of
																		the Commercial Taxes Department.</td>
																</tr>
																<tr>
																	<td>ii.</td>
																	<td>Unit level audited accounts for the relevant
																		financial year (for which GST reimbursement is being
																		claimed)</td>
																</tr>
																<tr>
																	<td>iii.</td>
																	<td>GST Audit Report for the relevant financial
																		year for the company</td>
																</tr>
																<tr>
																	<td>iv.</td>
																	<td>GSTAudit Report for the relevant financial
																		year for the unit (standalone GST statement/report for
																		the unit certified by a Chartered Accountant)</td>
																</tr>
																<tr>
																	<td>v.</td>
																	<td>CA Certificate for sales reconciliation of
																		Manufactured Goods/Trading goods/Scrap/ Stock Transfer
																		and GST paid towards the same separately.</td>
																</tr>
															</tbody>
														</table>
													</div>
													<p class="mb-2 mt-4">
														<strong>4.2 Capital Interest Subsidy</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th></th>
																	<th width="30%"></th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>4.2.1</td>
																	<td>Name & Address of Banks/Financial Institutions
																		from which loan availed</td>
																	<td>${bankname} <br> ${bankadd}
																	</td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.2.2</td>
																	<td>Amount of loan sanctioned on Investment in
																		Plant & Machinery</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${pnmloan}"></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>4.2.3</td>
																	<td>Rate of Interest</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${roi}"></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>4.2.4</td>
																	<td>Date of Sanction</td>
																	<td><input type="date" class="form-control"
																		readonly="true" name="" value="${sanctiondate}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.2.5</td>
																	<td>Amount of Loan Disbursed towards Investment in
																		Plant & Machinery with dates of Disbursement.</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${disloan}"></td>
																	<td>1. Certificate from FI/Bank certifying loan
																		for Plant & Machinery and interest& other relevant
																		details. <br>2. Certificate from FI/Bank for No
																		Default in the account during the entire period for
																		which reimbursement claimed.
																	</td>
																</tr>
															</tbody>
														</table>
													</div>


													<p class="mb-2 mt-4">
														<strong>4.2.6 Particulars of Claims for Sanction
															of Capital Interest Subsidy</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th rowspan="2">Sl No</th>
																	<th rowspan="2">Year for which subsidy Applied</th>
																	<th colspan="2">Payment made to FI/Bank during the
																		year</th>
																	<th rowspan="2">Amount of Interest Subsidy Applied</th>
																	<th rowspan="2">Documents required in support</th>
																</tr>
																<tr>
																	<th>Principal</th>
																	<th>Interest</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1.</td>
																	<td>Year –I ${Year1}</td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipal" name=""
																		readonly="true" value="${SecondYP}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlInterest" name=""
																		value="${SecondYI}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${SecondYAIS}"></td>
																	<td>Certification from FI/Bank required</td>
																</tr>
																<tr>
																	<td>2.</td>
																	<td>Year –II ${Year2}</td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipal" name=""
																		readonly="true" value="${SecondYP}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlInterest" name=""
																		value="${SecondYI}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${SecondYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>3.</td>
																	<td>Year –III ${Year3}</td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlPrincipal" name=""
																		value="${ThirdYP}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlInterest" name=""
																		value="${ThirdYI}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${ThirdYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.</td>
																	<td>Year –IV ${Year4 }</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipal" name=""
																		value="${FourthYP}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterest" name=""
																		value="${FourthYI}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${FourthYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>5.</td>
																	<td>Year –V ${Year5}</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipal" name=""
																		value="${FourthYP}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterest" name=""
																		value="${FourthYI}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${FourthYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td></td>
																	<td><strong>Total</strong></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlPrincipal" name=""
																		value="${FifthYP}"></td>
																	<td><input type="text"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control AllYearTotlInterest" name=""
																		value="${FifthYI}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidy" name=""
																		value="${FifthYAIS}"></td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>4.3 Infrastructure Interest Subsidy</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th></th>
																	<th width="30%"></th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>4.3.1</td>
																	<td>Name& Address of Banks/Financial Institutions
																		from which loan availed</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${iibankname}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.3.2</td>
																	<td>Amount of loan sanctioned for Investmentin
																		Infrastructure Facilities as defined</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${iisloan}"></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>4.3.3</td>
																	<td>Rate of Interest</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${iisroi}"></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>4.3.4</td>
																	<td>Date of Sanction</td>
																	<td><input type="date" class="form-control"
																		readonly="true" name="" value="${iissanctiondate}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.3.5</td>
																	<td>Amount of Loan Disbursed towards Investment in
																		Infrastructure Facilities with dates of Disbursement.</td>
																	<td><input type="text" class="form-control"
																		readonly="true" name="" value="${iisdisloan}"></td>
																	<td>1. Certificate from FI/Bank certifying loan
																		for Plant & Machinery and interest& other relevant
																		details. <br>2. Certificate from FI/ Bank for No
																		Default in the account during the entire period for
																		which reimbursement claimed.
																	</td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>4.3.6 Particulars of Claims for Sanction
															of Infrastructure Interest Subsidy</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th rowspan="2">Sl No</th>
																	<th rowspan="2">Year for which subsidy Applied</th>
																	<th colspan="2">Payment made to FI/Bank during the
																		year</th>
																	<th rowspan="2">Amount of Interest Subsidy Applied</th>
																	<th rowspan="2">Documents required in support</th>
																</tr>
																<tr>
																	<th>Principal</th>
																	<th>Interest</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1.</td>
																	<td>Year –I ${Year1i}</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipaliis" name=""
																		value="${FirstYPi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterestiis" name=""
																		value="${FirstYIi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidyiis" name=""
																		value="${IISFirstYAIS}"></td>
																	<td>Certification from FI/Bank required</td>
																</tr>
																<tr>
																	<td>2.</td>
																	<td>Year –II ${Year2i}</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipaliis" name=""
																		value="${SecondYPi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterestiis" name=""
																		value="${SecondYIi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidyiis" name=""
																		value="${IISSecondYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>3.</td>
																	<td>Year –III ${Year3i}</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipaliis" name=""
																		value="${ThirdYPi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterestiis" name=""
																		value="${ThirdYIi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidyiis" name=""
																		value="${IISThirdYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.</td>
																	<td>Year –IV ${Year4i})</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipaliis" name=""
																		value="${FourthYPi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterestiis" name=""
																		value="${FourthYIi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidyiis" name=""
																		value="${IISFourthYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>5.</td>
																	<td>Year –V ${Year5i})</td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlPrincipaliis" name=""
																		value="${FifthYPi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlInterestiis" name=""
																		value="${FifthYIi}"></td>
																	<td><input type="text" readonly="true"
																		placeholder="Enter Amount in INR"
																		class="form-control AllYearTotlAmtSubsidyiis" name=""
																		value="${IISFifthYAIS}"></td>
																	<td></td>
																</tr>
																<tr>
																	<td></td>
																	<td><strong>Total</strong></td>
																	<td><input type="text"
																		class="form-control totalofPrincipaliis"
																		readonly="true" name="totalofPrincipal" maxlength="12"></td>
																	<td><input type="text"
																		class="form-control totalofInterestiis"
																		readonly="true" name="totalofIntrest" maxlength="12"></td>
																	<td><input type="text"
																		class="form-control totalofAmtSubsidyiis"
																		readonly="true" name="totalofAmtSubsidy"
																		maxlength="12"></td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>5.1 Employees Provident Fund
															Reimbursement</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th></th>
																	<th width="30%"></th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>5.1.1</td>
																	<td>Number of Unskilled Workers with full
																		particulars of 100 unskilled workers and employee wise
																		contributions for relevant year.</td>
																	<td>${unskill}<br>Rs. ${epfreim}
																	</td>
																	<td>Affidavit from the main promoter/Authorized
																		Officer to the effect that all the above particulars
																		are true and that the unit had 100 unskilled workers
																		in its continuous employment for the full period of
																		the relevant year for which application for
																		reimbursement is being filed. <br> Copy of Form
																		No. 12 required to be filed under the Factories Act,
																		1948.
																	</td>
																</tr>
																<tr>
																	<td>5.1.2</td>
																	<td>Number of Other Workers</td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>5.1.3</td>
																	<td>Total No. of Workers</td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>5.1.4</td>
																	<td>Details of Claims for EPF Reimbursement</td>
																	<td></td>
																	<td>Month wise details of contributions paid by
																		employer into the EPFO or Employer’s PF Trust, which
																		should be certified by the concerned competent officer
																		of EPFO/Competent officer of Trust.</td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>6.1 Interest Subsidy For Industrial
															Research, Quality Improvement & Development Of Product</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th></th>
																	<th width="30%"></th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>6.1.1</td>
																	<td>Name & Address of Banks/Financial Institutions
																		from which loan availed</td>
																	<td></td>
																	<td>Affidavit from the main promoter/Authorized
																		Officer to the effect that all the above particulars
																		are true and that the unit had 100 unskilled workers
																		in its continuous employment for the full period of
																		the relevant year for which application for
																		reimbursement is being filed. <br> Copy of Form
																		No. 12 required to be filed under the Factories Act,
																		1948.
																	</td>
																</tr>
																<tr>
																	<td>6.1.2</td>
																	<td>Amount of loan sanctioned for Establishment of
																		Testing Lab, Quality Certification Lab and Tool Room</td>
																	<td></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>6.1.3</td>
																	<td>Rate of Interest</td>
																	<td></td>
																	<td>Sanction Letter, Agreement with FI/Bank</td>
																</tr>
																<tr>
																	<td>6.1.4</td>
																	<td>Date of Sanction</td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>6.1.5</td>
																	<td>Amount of Loan Disbursed towards Investment
																		for Establishment of Testing Lab, Quality
																		Certification Lab and Tool Room with dates of
																		Disbursement.</td>
																	<td></td>
																	<td>1. Certificate from Bank certifying loan for
																		Plant & Machinery and interest& other relevant
																		details. <br> 2. Certificate of No Default in the
																		account during the entire period for which
																		reimbursement claimed.
																	</td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-2 mt-4">
														<strong>6.2.1 Particulars of Claims for Sanction
															of Industrial Research, Quality Improvement & Development
															Subsidy</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th rowspan="2">Sl No</th>
																	<th rowspan="2">Year for which subsidy Applied</th>
																	<th colspan="2">Payment made to FI during the year</th>
																	<th rowspan="2">Amount of Interest Subsidy Applied</th>
																	<th rowspan="2">Documents required in support</th>
																</tr>
																<tr>
																	<th>Principal</th>
																	<th>Interest on P&M</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1.</td>
																	<td>Year –I
																		&nbsp;&nbsp;(&nbsp;&nbsp;&nbsp;&nbsp;)</td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td>Certification from FI/Bank required</td>
																</tr>
																<tr>
																	<td>2.</td>
																	<td>Year –II &nbsp;(&nbsp;&nbsp;&nbsp;&nbsp;)</td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>3.</td>
																	<td>Year –III (&nbsp;&nbsp;&nbsp;&nbsp;)</td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.</td>
																	<td>Year –IV (&nbsp;&nbsp;&nbsp;&nbsp;)</td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>5.</td>
																	<td>Year –V (&nbsp;&nbsp;&nbsp;&nbsp;)</td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td></td>
																	<td><strong>Total</strong></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>

													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl No</th>
																	<th>Documents Required</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>7.</td>
																	<td>Particulars of Workers from BPL families with
																		supporting documents (Employee Distinctive Numbers,
																		EPFO Number, BPL Card, Employee Payroll etc.)</td>
																</tr>
																<tr>
																	<td>8.</td>
																	<td>Particulars of Workers from SC/ST families
																		with supporting documents and Employee Payroll</td>
																</tr>
																<tr>
																	<td>9.</td>
																	<td>Particulars of female workers with supporting
																		documents</td>
																</tr>
																<tr>
																	<td>10.</td>
																	<td>Detailed Calculation of Stamp Duty Exemption
																		Availed</td>
																</tr>
																<tr>
																	<td>11.</td>
																	<td>Detailed Calculation of Mandi Fee Exemption
																		availed</td>
																</tr>
																<tr>
																	<td>12.</td>
																	<td>Detailed Calculation of Electricity Duty
																		Exemption availed</td>
																</tr>
																<tr>
																	<td></td>
																	<td>
																		<p class="text-center">
																			<strong>Declaration</strong>
																		</p> The above information are completely true and no fact
																		has been concealed or misrepresented. It is further
																		certified that the company has not applied for
																		benefits of the above nature under any sector-specific
																		or other policy of the Govt of UP for purpose of
																		availing benefits of the above nature. I/we hereby
																		agree that I/we shall forthwith repay the benefits
																		released to me/us under Rules of Policy for Promotion
																		of Industrial Investment and Employment-2017, if the
																		said benefits are found to be disbursed in excess of
																		the amount actually admissible whatsoever the reason.
																	</td>
																</tr>
																<tr>
																	<td></td>
																	<td>
																		<div class="table-responsive">
																			<table class="table">
																				<tr>
																					<td><strong>Date:</strong><br> <strong>Place:</strong>
																					</td>
																					<td class="text-right"><strong>Signature
																							of Authorised Signatory with</strong><br> <strong>Name,
																							Designation and Office Seal</strong></td>
																				</tr>
																			</table>
																		</div>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>

												</div>
											</div>

										</div>

										<!-- End Annexure-III  -->





										<div class="isf-form mt-4">
											<!-- 		<hr class="mt-4 mb-4">
											<div class="row mt-4">
												<div class="col-sm-12 text-center">
													<button type="button"
														class="btn btn-outline-success btn-sm mb-3">
														<i class="fa fa-download"></i> Download Only Application
														Form
													</button>
													<button type="button" class="btn btn-outline-info btn-sm mb-3"><i class="fa fa-download"></i> Download Application Form with Suporting Docs</button>
													<button type="button"
														class="btn btn-outline-secondary btn-sm mb-3">
														<i class="fa fa-download"></i> Download Only Supporting
														Docs
													</button>
												</div>
											</div> -->
											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-4">
													<a href="./deptApplicationForLoc"
														class="common-default-btn mt-3">Back</a>
												</div>
												<div class="col-sm-8 text-right">
													<a href="javacript:void(0);" id="printMethod1"
														onclick="printDiv('printableArea')"
														class="common-btn mt-3"><i class="fa fa-print"></i>
														Print</a>
														<a	href="./DisfacilitiesReliefSought" class="common-btn mt-3">Next </a>
												</div>
											</div>
										</div>
										</div>
								</div>
							</div>
						</div>
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
								<p>© 2020 - IT Solution powered by National Informatics
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
		<div class="modal fade" id="SendtoConcernDepartment">
			<div class="modal-dialog modal-lg">

				<form:form modelAttribute="department" id="concernDept"
					action="dissaveConcernDepartment" autocomplete="off" method="POST">


					<div class="modal-content">

						<!-- Modal Header -->
						<div class="modal-header">
							<h4 class="modal-title">Send to Concern Department</h4>
							<button type="button" class="close" data-dismiss="modal">&times;</button>
						</div>

						<!-- Modal body -->


						<div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<!-- gopal  -->
									<c:forEach items="${concernDepartment}" var="temp">
										<div class="custom-control custom-checkbox">
											<form:checkbox path="newDeptName" value="${temp.deptEmail}"
												class="custom-control-input" id="${temp.newDeptName}"
												name="${temp.newDeptName}"></form:checkbox>
											<label class="custom-control-label" for="${temp.newDeptName}">${temp.newDeptName}</label>
										</div>
									</c:forEach>
									<!--  gopal -->

								</div>
							</div>
						</div>
						<div class="modal-footer">
							<form:button type="submit" class="common-btn mt-3">Submit</form:button>
						</div>
					</div>
				</form:form>

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
	<script src="js/datepicker.js" type="text/javascript"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.min.js"></script>
	<script src="js/custom.js"></script>
	<script>
		$(document).ready(function() {
			$("[data-toggle=offcanvas]").click(function() {
				$(".row-offcanvas").toggleClass("active");
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
		$(document).on('click', '#printMethod', function(e) {
			e.preventDefault();

			var $this = $(this);
			var originalContent = $('body').html();
			var printArea = document.getElementById('printform').innerHTML;

			document.body.innerHTML = printArea;
			window.print();
			$('body').html(originalContent);
		});

		$(document).on('click', '#printMethod1', function(e) {
			e.preventDefault();

			var $this = $(this);
			var originalContent = $('body').html();
			var printArea = document.getElementById('printform').innerHTML;

			document.body.innerHTML = printArea;
			window.print();
			$('body').html(originalContent);
		});

		$(document).ready(
				function() {
					var pdf = new jsPDF("l", "pt", "a4");
					pdf.internal.scaleFactor = 1.5;
					var options = {
						pagesplit : true
					};
					$('#formId').click(
							function() {
								pdf.addHTML(document
										.getElementById("printform"), options,
										function() {
											pdf.save("AFIFFORM.pdf");
										});
							});
				});
	</script>

	<script type="text/javascript">
		$(document).ready(function() {
			if ('${natureOfProject}' == 'NewProject') {
				$(".newproject").show();
				$(".existingproject").hide();
			}

			if ('${natureOfProject}' == 'ExistingProject') {
				$(".newproject").hide();
				$(".existingproject").show();
			}

		});
	</script>
	<script type="text/javascript">
		function disbNewProjTotal() {
			var sum = 0;
			$(".disbNewproj").each(function() {
				sum += +$(this).val();
			});
			$(".disbNewprojTotal").val(sum);
		}

		$(document).ready(disbNewProjTotal);
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
	<script type="text/javascript">
		function AllYearTotalIntrestiis() {
			var sum = 0;
			$(".AllYearTotlIntrstiis").each(function() {
				sum += +$(this).val();
			});
			$(".totalofTIiis", this).val(sum);
		}

		$(document).ready(AllYearTotalIntrestiis);

		function AllYearTotalPrincipaliis() {
			var sum = 0;
			$(".AllYearTotlPrincipaliis").each(function() {
				sum += +$(this).val();
			});
			$(".totalofPrincipaliis", this).val(sum);
		}

		$(document).ready(AllYearTotalPrincipaliis);

		function AllYearTotalInterestiis() {
			var sum = 0;
			$(".AllYearTotlInterestiis").each(function() {
				sum += +$(this).val();
			});
			$(".totalofInterestiis", this).val(sum);
		}

		$(document).ready(AllYearTotalInterestiis);

		function AllYearTotalAmtSubsidyiis() {
			var sum = 0;
			$(".AllYearTotlAmtSubsidyiis").each(function() {
				sum += +$(this).val();
			});
			$(".totalofAmtSubsidyiis", this).val(sum);

		}

		$(document).ready(AllYearTotalAmtSubsidyiis);
	</script>
</body>

</html>