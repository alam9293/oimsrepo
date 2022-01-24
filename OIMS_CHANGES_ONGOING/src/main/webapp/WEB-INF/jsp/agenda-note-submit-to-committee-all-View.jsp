<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Evaluation View</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
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
							href="./viewAndEvaluate"><i class="fa fa-eye"></i> View and
								Evaluate Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluateSME"><i class="fa fa-eye"></i> View
								and Forward SME applications</a></li>
						<li class="nav-item"><a class="nav-link" href="./queryRaised"><i
								class="fa fa-question-circle"></i> Query Raised</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewQueryResponse"><i class="fa fa-question-circle"></i>
								Query Response By Entrepreneur</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./agendaNode"><i class="fa fa-list"></i> Prepare Agenda
								Note</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./meetingschedule"><i class="fa fa-calendar"></i>
								Schedule meeting</a></li>
						<li class="nav-item"><a class="nav-link" href="./momgo"><i
								class="fa fa-calendar"></i> Minutes of Meeting and GO's</a></li>
						<li class="nav-item"><a class="nav-link" href="./generateLoc"><i
								class="fa fa-wpforms"></i> Generate LOC</a></li>
					</ul>
				</div>
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-12">
								<a href="javascript:void(0);" id="printMethod"
									class="common-btn mt-3" class="fa fa-print">Print</a>
								<button type="button" class="common-btn mt-3" id="formId">
									<i class="fa fa-download"></i> Download
								</button>

							</div>
						</div>
						<div id="printform">
							<div class="row">

								<hr class="mt-4 mb-4">
								<div class="col-md-12 mt-4 text-center">
									<h4 class="secondary-heading">${agendaNote}</h4>
									<%
										int i = 1;
									int j = 0;
									%>
								</div>
								<div class="col-sm-12 text-center">
									<h4 class="card-title mb-4 mt-4 text-center l-space">I N D
										E X</h4>
								</div>
								<div class="col-sm-12" id="printform">
									<div class="table-responsive">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>Sr. No.</th>
													<th>Particular</th>
													<th width="10%">Items</th>
												</tr>
											</thead>
											
											<tbody>
												<c:forEach var="list" items="${investmentDetailMegaList}"
													varStatus="counter">
													<tr><td><%=i%></td><td>Confirmation of minutes of last EC Meeting </td><td></td></tr>
														
														<tr><td><%=++i%></td>
														<td><strong>${list.companyName}</strong> Setting up a
															unit for <strong>${natureOfProject}</strong> at existing
															unit at <strong>${list.district},${list.region}</strong>
															with an investment of <strong>Rs.
																${list.investment}.</strong></td>
																<td><a href="#Item-${counter.index+1}">Item-${counter.index+1}</a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<c:forEach var="list" items="${plist}" varStatus="counter">
								<div class="row id="showHide1"">
									<div class="col-md-12 mt-4">
										<h4 class="secondary-heading">
											<%-- Item-<%=++j%> --%>
											 Item - ${counter.index+1}
											<hr>
										</h4>
										
										<div class="row mt-5">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-bordered">
                                      <thead>
                                        <tr>
                                          <th>Application ID</th>
                                          <th>Company Name</th>
                                          <th>Product</th>
                                    	   <th>Region</th>
                                    	   <th>District</th>                                        
                                          <th>Investment</th>
                                           <th>Category</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                         <c:forEach var="list" items="${investmentDetailMegaList}" varStatus="counter">
                                         <tr>                                        
                                        <td>${list.appliId}</td>                                        
                                        <td>${list.companyName}</td>
                                        <td>product</td>
                                         <td>${list.region}</td>
                                          <td>${list.district}</td>
                                         <td>${list.investment}</td>
                                         <td>${category}</td>
                                          <%-- <td><input type="submit" value="View"class="btn btn-outline-info btn-sm" formaction="./viewApprovedAgendaDetailsMegaByPicup?appliId=${list.appliId}" onclick="return validate('${list.appliId}')"></td> --%>                           
                                        </tr>
                                       </c:forEach>
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
                              </div>
										
										<div class="form-group">
                                      <label>Add Company Details</label>
                                      <div class="alert alert-secondary">
    The company has applied for various incentives under IIEPP-2017 vide its applicationdated 19.8.2020 for the proposed greenfield projectfor manufacture of Flexible Films within an investment of Rs. 953.29 crores at YEIDA, Gautam Budh Nagar. The proposal is put up as hereunder for consideration of EC: -<br>
1.  M/s Surya Global Flexifilms Pvt. Ltd. (SGFPL), a new company incorporated on 22.5.2020, is a group company of M/s Surya Food &amp;Agro Ltd., which is a leading food processing company based at Noida makes biscuits, cookies, cakes, confectionery, chocolates, fruit-based beverages, baked snacks etc. It owns brand “Priyagold” and services mass &amp; niche markets.<br>
2.  The group/company is promoted by S/Sri B.P. Agarwal, Manoj Agarwal, Navin Agarwal, Shekhar Agarwal and Mrs. Bina Agarwal, who have the sufficient experience in the business &amp; industry.<br>

3.  SGFPL, vide its letter dated 19.08.2020&amp; 26.8.2020 has informed that they propose to set up a mega project to manufacture Flexible Films (BOPET, BOPP, Metalized &amp; other films) with following installed capacities in two phases in YEIDA, Distt. Gautam Budh Nagar under its subsidiary company i.e. SGFPL with a total investment of Rs. 953.29 crores: -<br>
          
4.  As per DPR(prepared by Chartered Accountants viz. M/s Y.D. Singh &amp; Associates, Noida),the capital investment under Land &amp; Site Development, Building &amp; Civil Works, Machinery &amp;Equipment, etc. has been estimated as Rs. 810.20 crores, which is within prescribed minimum capital requirement for Mega Plus Category (Capital investment of or more than Rs. 500 crores but less than Rs. 1,000 crores) in Gautam Budh Nagar and Ghaziabad District Region.<br>
5.  As per prescribed application format of IIEPP-2017 (Annexure-I), the company has indicated opted Cut-off date for commencement of investment as 27.07.2020 and proposed to start commercial production for Phase I &amp; Phase II by April 2022 &amp; April 2024 respectively.<br>
6.  As per investment certificate from M/s. Y.D. Singh &amp; Associates, Statutory Auditors, Noida, company has indicated First Date of investment as 22.5.2020 and opted cut-off date for investment as 5.10.2020. The company has made investment of Rs. 70.07 crores from 22.5.2020 ttill 5.10.2020. Out of total investment of Rs. 70.07 crores, fixed capital investment is Rs. 69.20 crores.Therefore, cut-off date for commencement of investment may be considered as 22.5.2020.<br>
7.  The company has submitted application for availing incentives under Rules for implementation of Industrial Investment &amp; Employment Promotion Policy - 2017 for its above Project, brief particulars of which are as under: -

</div>
                                    </div>
										
										 <form:form modelAttribute="PrepareAgendaNotes" class="mt-4" name="PrepareAgendaNotes" method="get" >
                         <form:hidden path="prepareAgendaNote" id="prepareAgendaNote"></form:hidden>
                         <form:hidden path="appliId" id="appliId"></form:hidden>
                         
                        
                              </form:form>
									</div>
									<div class="col-sm-12">
										<!--  <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">(A). Evaluate Application</h4>
                                    </div>
                                </div>
                                <hr class="mt-2 mb-5"> -->
										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered">
														<tbody>
															<tr>
                                            <td style="width: 50%;"><label class="table-heading">New Project or Expansion / Diversification</label></td>
                                            <td>
                                            <input type="text" class="form-control" value="${natureOfProject}" disabled="" name="">
                                              
                                            </td>
                                          </tr>
                                          <tr>
                                            <td><label class="table-heading">Product Details</label></td>
                                            <td>
                                              <input type="text" class="form-control" value="Manufacturing of Flexible Films" disabled="" name="">
                                            </td>
                                          </tr>
															<tr>
														 <td colspan="3" class="p-4">
														  <div class="table-responsive mt-3" id="productsDetailsTbl">
														<table class="table table-stripped table-bordered" id="edittable">
					                              <thead>
					                              <c:if test="${not empty existProjList}">
					                                <tr>
					                                <!--  <th ></th> -->
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
					                              <c:forEach var="list" items="${existProjList}" varStatus="counter">
					                                <tr>
					                                   <%-- <td ><form:hidden path="epdId" value="${list.epdId}"/></td>				                            --%>     
					                                    
														<td>${list.epdExisProducts}</td>
														<td>${list.epdExisInstallCapacity}</td>
														<td>${list.epdPropProducts}</td>
														<td>${list.epdPropInstallCapacity}</td>
														<td>${list.epdExisGrossBlock}</td>
														<td>${list.epdPropoGrossBlock}</td>
					                                    
					                                </c:forEach>
					                              </tbody>
					                            </table>	
												</div>
											</tr>

															<tr>
																<td colspan="3" class="p-4">

																	<div class="row">
																		<div class="col-sm-12">
																			<label class="table-heading">Location</label>
																		</div>
																	</div>

																	<div class="table-responsive mt-3">
																		<table class="table table-stripped table-bordered">
																			<tbody>
																				<tr>
																					<td style="width: 50%;">Full Address</td>
																					<td><input type="text" class="form-control"
																						value="${fullAddress}" disabled="disabled"
																						name="fullAddress"></td>
																				</tr>
																				<tr>
																					<td>District</td>
																					<td><input type="text" class="form-control"
																						value="${districtName}" disabled="disabled"
																						name="districtName"></td>
																				</tr>
																				<tr>
																					<td>Region</td>
																					<td><input type="text" class="form-control"
																						value="${resionName}" disabled="disabled"
																						name="resionName"></td>
																				</tr>
																			</tbody>
																		</table>
																	</div>
																</td>
															</tr>

															<tr>
																<td colspan="3" class="p-4">

																	<div class="row">
																		<div class="col-sm-12">
																			<label class="table-heading">Employment</label>
																		</div>
																	</div>

																	 <div class="table-responsive mt-3">
																		<table class="table table-stripped table-bordered">
																			<tbody>
																				<tr>
																					<td style="width: 50%;">Total Direct Employment</td>
																					<td><input type="text" class="form-control"
																						value="${grossTotalMaleandFemaleEmployment}"
																						disabled="disabled"
																						name="grossTotalMaleandFemaleEmployment"></td>
																				</tr>
																			</tbody>
																		</table>
																	</div> 
																	
																	<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive mt-3">
														<table class="table table-stripped table-bordered"
															id="customFields" tabindex='1'>
															<c:if test="${ not empty skilledUnSkilledEmployemntList}">
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
															</c:if>
															<tbody>
																<c:forEach var="list"
																	items="${skilledUnSkilledEmployemntList}"
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


											<%-- <c:if test="${not empty skilledDisplay}">
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Skilled Male Employees</label> <input type="text"
																class="form-control" value="${skilledEmploymentMale}"
																disabled="disabled">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Skilled Female Employees</label> <input
																type="text" class="form-control"
																value="${skilledEmploymentFemale}" disabled="disabled">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Total Skilled Employees</label> <input type="text"
																class="form-control" value="${totalSkilledEmployment}"
																disabled="disabled">
														</div>
													</div>
												</div>
											</c:if> --%>

											<c:if test="${not empty unSkilledDisplay}">
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Unskilled Male Employees</label> <input
																type="text" class="form-control"
																value="${unSkilledEmploymentMale}" disabled="disabled">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Unskilled Female Employees</label> <input
																type="text" class="form-control"
																value="${unSkilledEmploymentFemale}" disabled="disabled">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Total Unskilled Employees</label> <input
																type="text" class="form-control"
																value="${totalUnSkilledEmployment}" disabled="disabled">
														</div>
													</div>
												</div>
											</c:if>


											<%-- <c:if test="${ not empty skilledUnSkilledEmployemntList}">

												<hr class="mt-4">

												<!-- <div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">Total Employees</h3>
													</div>
												</div> -->

												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Total Male Employees</label> <input type="text"
																class="form-control" value="${grossTotalMaleEmployment}"
																disabled>
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Total Female Employees</label> <input type="text"
																class="form-control"
																value="${grossTotalFemaleEmployment}" disabled>
														</div>

													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label class="total-emp">Total Employees: <c:out
																	value="${grossTotalMaleandFemaleEmployment}" /></label>
														</div>
													</div>
												</div>

											</c:if> --%>
																</td>
															</tr>
															<tr class ="NewProject-row">
																<td><label class="table-heading">If Expansion / Diversification whether proposed gross block more then 25% of the existing gross block <a href="#">(View File)</a></label></td>
																<td><input type="text" class="form-control" value="Yes" disabled="disabled"></td>
															</tr>
															<tr>
																<td><label class="table-heading">Category
																		of Industrial Undertaking</label></td>
																		<td><input type="text" class="form-control" value="${category}" disabled="disabled"></td>
																<%-- <td><select class="form-control"
																	disabled="disabled">
																		<option value="${category}" selected>${category}</option>
																</select></td> --%>
															</tr>
															<tr>
                                            <td><label class="table-heading">Opted Cut-off date investment</label></td>
                                            <td>
                                              <input type="text" class="form-control" disabled="" value="${invCommenceDate}" name="">
                                            </td>
                                          </tr>
                                          <tr>
                                            <td><label class="table-heading">Proposed Date of Commencement of Commercial Production</label></td>
                                            <td>
                                              <input type="text" class="form-control" disabled="" value="${propCommProdDate}" name="">
                                            </td>
                                          </tr>
                                          <tr>
                                            <td colspan="2"><label class="table-heading">Detailed Project Report prepared by External Consultant/Chartered Accountants <a href="#">(View File)</a></label></td>
                                          </tr>

                                          <tr>
                                            <td colspan="3" class="p-4">

                                              <div class="row">
                                                <div class="col-sm-12">
                                                  <label class="table-heading">Capital Investment Details</label>
                                                </div>
                                              </div>

                                              <div class="table-responsive mt-3">
                                                <table class="table table-stripped table-bordered">
                                                  <tbody>
                                                        <tr>
                                                          <td>Proposed Capital Investment</td>
                                                          <td><input type="text" class="form-control" name="" value="${InvFCI}"></td>
                                                        </tr>
                                                        <tr>
                                                          <td>Total Cost of Project</td>
                                                          <td><input type="text" class="form-control" name="" value="${InvTPC}"></td>
                                                        </tr>
                                                  </tbody>
                                                </table>
                                              </div>

                                              <div class="row">
                                                <div class="col-sm-6">
                                                  <label class="table-heading">Break up of proposed capital Investment</label>
                                                </div>
                                              </div>

															<tr>
																<td colspan="3" class="p-4">

																	<div class="row">
																		<div class="col-sm-12">
																			<label class="table-heading">Cost of Project
																				Investment Details</label>
																		</div>
																	</div>

																	<div class="table-responsive mt-3">
																		<table class="table table-stripped table-bordered">
																			<thead>
																				<tr>
																					<!--  <th>Select</th> -->
																					<th>Item</th>
																					<th>Consider as FCI</th>
																					<th>Cost as per DPR</th>
																					<th>Cost Eligible as per IIEPP Policy Rule</th>
																					<th>Remarks</th>
																				</tr>
																			</thead>
																			<tbody>
																				<tr>
																					<!-- <td>
                                                              <div class="custom-control custom-checkbox mb-4">
                                                                <input type="checkbox" class="custom-control-input" id="selectItem1" name="SlectItem">
                                                                <label class="custom-control-label" for="selectItem1"></label>
                                                              </div>
                                                            </td> -->
																					<td>Land & Site Development Cost</td>
																					<td><input type="text" class="form-control" disabled="disabled" value=Yes></td>
																					<td><input type="text"
																						class="CostasperDPR form-control" name="InvLC" disabled="disabled"
																						value="${InvLC} "></td>
																					<td><input type="text"
																						class="CostasperIIEPP form-control" name="" disabled="disabled"
																						value="${LCasperIEPP}"></td>
																					<td><input type="text" class="form-control" disabled="disabled"
																						name=""></td>
																				</tr>
																				<tr>
																					<!-- <td>
                                                              <div class="custom-control custom-checkbox mb-4">
                                                                <input type="checkbox" class="custom-control-input" id="selectItem2" name="SlectItem">
                                                                <label class="custom-control-label" for="selectItem2"></label>
                                                              </div>
                                                            </td> -->
																					<td>Building and Civil Work Cost</td>
																					<td><input type="text" class="form-control" value=Yes disabled="disabled"></td>
																					<td><input type="text"
																						class="CostasperDPR form-control" name="" disabled="disabled"
																						value="${InvBuiildC}"></td>
																					<td><input type="text"
																						class="CostasperIIEPP form-control" name="" disabled="disabled" value="${InvBuiildC}">
																					</td>
																					<td><input type="text" class="form-control" disabled="disabled"
																						name=""></td>
																				</tr>
																				<tr>
																					<!-- <td>
                                                              <div class="custom-control custom-checkbox mb-4">
                                                                <input type="checkbox" class="custom-control-input" id="selectItem3" name="SlectItem">
                                                                <label class="custom-control-label" for="selectItem3"></label>
                                                              </div>
                                                            </td> -->
																					<td>Plant & Machinery Cost</td>
																					<td><input type="text" class="form-control" value=Yes disabled="disabled"></td>
																					<td><input type="text"
																						class="CostasperDPR form-control" name="" disabled="disabled"
																						value="${invPlantAndMachCost}"></td>
																					<td><input type="text"
																						class="CostasperIIEPP form-control" name=""  value="${invPlantAndMachCost}" disabled="disabled">
																					</td>
																					<td><input type="text" class="form-control" disabled="disabled"
																						name=""></td>
																				</tr>
																				<tr>
																					<!-- <td>
                                                              <div class="custom-control custom-checkbox mb-4">
                                                                <input type="checkbox" class="custom-control-input" id="selectItem4" name="SlectItem">
                                                                <label class="custom-control-label" for="selectItem4"></label>
                                                              </div>
                                                            </td> -->
																					<td>Miscellaneous Fixed Asset(MFA)</td>
																					<td><input type="text" class="form-control" value=Yes disabled="disabled"></td>
																					<td><input type="text"
																						class="CostasperDPR form-control" name="" disabled="disabled"
																						value="${invMisFixCost}"></td>
																					<td><input type="text"
																						class="CostasperIIEPP form-control" name="" value="${invMisFixCost}" disabled="disabled">
																					</td>
																					<td><input type="text" class="form-control" disabled="disabled"
																						name=""></td>
																				</tr>
																				<!-- <tr>
																					
																					<td><input type="text" class="form-control"
																						name=""></td>
																					<td><select class="form-control">
																							<option value="1">Yes</option>
																							<option value="2">No</option>
																					</select></td>
																					<td><input type="text"
																						class="CostasperDPR form-control" name="" disabled="disabled">
																					</td>
																					<td><input type="text"
																						class="CostasperIIEPP form-control" name="" disabled="disabled">
																					</td>
																					<td><input type="text" class="form-control" disabled="disabled"
																						name=""></td>

																				</tr> -->
																				<tr>
																					<td colspan="2" align="right"><strong>Total:</strong></td>
																					<td><input type="text" readonly="readonly"
																						class="TotalCostasperDPR form-control" name="" disabled="disabled"></td>
																					<td><input
																						class="TotalCostasperIIEPP form-control"
																						readonly="readonly" name=""></td>
																					<td></td>
																				</tr>
																			</tbody>
																		</table>
																		
																		
																		
																	</div>
																	<div class="row">
                                                <div class="col-sm-6">
                                                  <label class="table-heading">Break-Up of Project Cost & Means of Financing <a href="#">(View File)</a></label>
                                                </div>
                                              </div>
																</td>
															</tr>
														
															

															<%-- <tr>
																<td><label class="table-heading">Whether
																		the company should have filed LOI/IEM registration
																		with the Department of Industrial Policy and Promotion
																		GoI. <a href="#">(View File)</a></label></td>
																<td><input type="text" class="form-control" value="${regorlic}" disabled="disabled"><td>
                                                    <input type="text" placeholder="Enter IEM Number" class="form-control" name="">
                                                  </td></td>
															</tr> --%>
															<tr>
                                            <td><label class="table-heading">Whether the company have filed LOI/IEM registration with the Department of Industrial Policy and Promotion GoI. <a href="#">(View File)</a></label></td>
                                            <td>
                                              <table class="table">
                                                <tr>
                                                  <td><input type="text" class="form-control" value="${regorlic}" disabled="disabled"></td>
                                                  <td>
                                                    <input type="text" placeholder="Enter IEM Number" class="form-control" name="">
                                                  </td>
                                                </tr>
                                              </table>
                                            </td>
                                          </tr>

															<tr>
																<td><label class="table-heading">Whether
																		Industrial Undertaking should have more than 50% equity of any government/government undertaking.
													
																		</label></td>
																<td><input type="text" class="form-control" value= "No" disabled="disabled"></td>
															</tr>

															<tr>
															<td colspan="3" class="p-4">

																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Eligible
																			Investment Period</label>
																	</div>
																	<!-- <div class="col-sm-6 text-right">
																			<a href="javacript:void(0);"
																				class="btn btn-outline-success btn-sm"><i
																				class="fa fa-edit"></i> Edit</a>
																		</div> -->
																</div>

																<div class="table-responsive mt-3">
																	<table class="table table-stripped table-bordered">
																		<tbody>
																			<tr class="in-case-of-small-row">
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="EIP1"
																							name="SlectItem"> <label
																							class="custom-control-label" for="EIP1"></label>
																					</div>
																				</td> -->
																				<td style="width: 80%;">In case of small &
																					medium industrial undertakings, the period
																					commencing from cutoff date falling in the
																					effective period of these Rules upto 3 years or
																					till the date of commencement of commercial
																					production, whichever is earlier. Such cases will
																					also be covered in which the cut-off date is within
																					the period immediately preceding 3 years from the
																					effective date, subject to the condition that
																					commercial production in such cases commences on or
																					after the effective date. In any case, the maximum
																					eligible investment period shall not be more than 3
																					years.</td>

																				<td><input type="text" class="form-control"
																					value="${ctypeSMALL}" readonly="readonly">
																					<%-- <c:choose>
																									<c:when test="${ctypeSMALL eq 'Yes'}">
																										<option value="${ctypeSMALL}"
																											selected="selected">${ctypeSMALL}</option>
																										<option value="No">No</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeSMALL}"
																											selected="selected">${ctypeSMALL}</option>
																									</c:otherwise>
																								</c:choose> 
																						</select>--%></td>
																			</tr>
																			<tr class="in-case-of-large-row">
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="EIP2"
																							name="SlectItem"> <label
																							class="custom-control-label" for="EIP2"></label>
																					</div>
																				</td> -->
																				<td style="width: 80%;">in case of large industrial undertakings,
																					the period commencing from cutoff date falling in
																					the effective period of these Rules upto 4 years or
																					till the date of commencement of commercial
																					production, whichever is earlier. Such cases will
																					also be covered in which the cut-off date is within
																					the period immediately preceding 4 years from the
																					effective date, subject to the condition that
																					commercial production in such cases commences on or
																					after the effective date. In any case, the maximum
																					eligible investment period shall not be more than 4
																					years.</td>
																				<td><input type="text" class="form-control"
																					value="${ctypeLARGE}" readonly="readonly">
																					<%-- <td><select class="form-control" readonly="readonly">
																								<c:choose>
																									<c:when test="${ctypeLARGE eq 'Yes'}">
																										<option value="${ctypeLARGE}"
																											selected="selected">${ctypeLARGE}</option>
																										<option value="No">No</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeLARGE}"
																											selected="selected">${ctypeLARGE}</option>
																									</c:otherwise>
																								</c:choose>
																						</select></td> --%>
																			</tr>
																			<tr class="in-case-of-mega-row">
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="EIP3"
																							name="SlectItem"> <label
																							class="custom-control-label" for="EIP3"></label>
																					</div>
																				</td> -->
																				<td style="width: 80%;">In case of mega & mega plus industrial
																					undertakings, the period commencing from the
																					cut-off date falling in the effective period of
																					these Rules, and upto 5 years, or the date of
																					commencement of commercial production, whichever
																					falls earlier. Such cases will also be covered in
																					which the cut-off date is within the period
																					immediately preceding 3 years from the effective
																					date, subject to the condition that atleast 40% of
																					eligible capital investment shall have to be
																					undertaken after the effective date. In any case,
																					the maximum eligible investment period shall not be
																					more than 5 years.</td>
																				<td><input type="text" class="form-control"
																					value="${ctypeMEGA}" readonly="readonly"> <%-- <td><select class="form-control" readonly="readonly">
																								<c:choose>
																									<c:when test="${ctypeMEGA eq 'Yes'}">
																										<option value="${ctypeMEGA}" selected="selected">${ctypeMEGA}</option>
																										<option value="No">No</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeMEGA}" selected="selected">${ctypeMEGA}</option>
																									</c:otherwise>
																								</c:choose>
																						</select>--%></td>
																			</tr>
																			<tr class="in-case-of-supermega-row" >
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="EIP4"
																							name="SlectItem"> <label
																							class="custom-control-label" for="EIP4"></label>
																					</div>
																				</td> -->
																				<td style="width: 80%;">in case of super mega industrial
																					undertakings, the period commencing from the cutoff
																					date falling in the effective period of these
																					Rules, and upto 7 years, or the date of
																					commencement of commercial production, whichever
																					falls earlier. Such cases will also be covered in
																					which the cut-off date is within the period
																					immediately preceding 3 years from the effective
																					date, subject to the condition that atleast 40% of
																					eligible capital investment shall have to be
																					undertaken after the effective date. In any case,
																					the maximum eligible investment period shall not be
																					more than 7 years.</td>
																				<td><input type="text" class="form-control"
																					value="${ctypeSUPERMEGA}" readonly="readonly">

																					<%-- <td><select class="form-control" readonly="readonly">
																								<c:choose>
																									<c:when test="${ctypeSUPERMEGA eq 'Yes'}">
																										<option value="${ctypeSUPERMEGA}"
																											selected="selected">${ctypeSUPERMEGA}</option>
																										<option value="No">No</option>
																										<option value="${opt}" selected="selected">${opt}</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeSUPERMEGA}"
																											selected="selected">${ctypeSUPERMEGA}</option>
																											<option value="${opt}">${opt}</option>
																									</c:otherwise>
																								</c:choose>
																						</select> --%></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

															<tr>
																<td><label class="table-heading">Company has invested atleast 40% of the eligible capital investment after the effective date of policy i.e. 13/07/2017.</label></td>
																<td><input type="text" class="form-control" value="Yes" disabled="disabled"></td>
															</tr>

															<tr>
																<td><label class="table-heading">Whether
																		the project is planned in phases (if yes phase wise
																		details to be added).</label></td>
																<td><select class="form-control" disabled="">
																		<c:choose>
																			<c:when test="${phsWsAply eq 'Yes'}">
																				<option value="${phsWsAply}" selected="selected">${phsWsAply}</option>
																				<option value="No">No</option>

																			</c:when>
																			<c:otherwise>
																				<option value="Yes">Yes</option>
																				<option value="${phsWsAply}" selected="selected">${phsWsAply}</option>

																			</c:otherwise>
																		</c:choose>
																</select></td>
															</tr>
															
															<tr>
                                            <td colspan="2">
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
                                </tr>
                              </thead>
                              <tbody>
                                <tr>
                                  <td class="text-center">1</td>
                                  <td></td>
                                  <td></td>
                                  <td>TPH/Unit</td>
                                  <td></td>
                                  <td></td>
                                </tr>
                                <tr>
                                  <td class="text-center">2</td>
                                  <td></td>
                                  <td></td>
                                  <td>Tonnes</td>
                                  <td></td>
                                  <td></td>
                                </tr>
                              </tbody>
                            </table>
                          </div></td></tr>
                          
                          <tr>
                                            <td><label class="table-heading">Investment Made till dateas per CA/Statutory Certificate</label></td>
                                            <td>
                                              <input type="text" class="form-control" placeholder="Amount in Rs." name="">
                                            </td>
                                          </tr>

                                          <tr>
                                            <td><label class="table-heading">Name of Authorized Signatory. (Board Resolution copy provided.) <a href="#">(View File)</a></label></td>
                                            <td>
                                              <input type="text" class="form-control" name="">
                                            </td>
                                          </tr>
                                          <tr>
                                            <td><label class="table-heading">Application Format- (Whether application is as per prescribed format & signed by Authorized Signatory with Name, Designation and Official Seal.)</label></td>
                                            <td>
                                              <select class="form-control">
                                                <option value="1">Yes</option>
                                                <option value="2">No</option>
                                              </select>
                                            </td>
                                          </tr>
                                          <tr>
                                            <td><label class="table-heading">Whether all supporting document of application authenticated by a Director/Partner/Officer duly authorized by the competent authority of the applicant on its behalf. </label></td>
                                            <td>
                                              <select class="form-control">
                                                <option value="1">Yes</option>
                                                <option value="2">No</option>
                                              </select>
                                            </td>
                                          </tr>
                                          <tr>
                                            <td><label class="table-heading">Online Submission of Date of Application </label></td>
                                            <td>
                                              <input type="text" class="form-control" value="12/12/2020" disabled="" name="">
                                            </td>
                                          </tr>
                                          <tr>
                                            <td><label class="table-heading">GSTIN Number</label></td>
                                            <td>
                                              <input type="text" class="form-control" value="${gst}" disabled="" name="">
                                            </td>
                                          </tr>
                                          <tr>
                                            <td><label class="table-heading">PAN Number</label></td>
                                            <td>
                                              <input type="text" class="form-control" value="${pan}" disabled="" name="">
                                            </td>
                                          </tr>
                          					</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
								<form:form modelAttribute="incentiveDeatilsData"
												name="incentiveDeatilsform" method="post">
												<div class="row">
													<div class="col-sm-12">
														<div class="table-responsive">
															<table class="table table-stripped table-bordered">
																<thead>
																	<tr>
																		<th>Sr. No.</th>
																		<th style="width: 25%;">Details of Incentives
																			Sought and Concerned Department</th>
																		<th style="width: 10%;">Amount of Incentives <small>(Rs.
																				in crores)</small></th>
																		<th>Incentive as per Rules</th>
																		<th style="width: 15%;">PICUP Officer's Remark</th>
																	</tr>
																</thead>
																<tbody>
																	<tr class="ISF_Claim_Reim-row">
																		<td>1</td>
																		<td>Amount of SGST claimed for reimbursement <br>
																			<strong>Industrial Development/ Commercial
																				Tax</strong></td>
																		<td><form:input path="ISF_Claim_Reim" type="text"
																				class="ISF_Reim_SGST form-control"
																				name="ISF_Claim_Reim" id="ISF_Claim_Reim"
																				maxlength="12" readonly="true"></form:input> <span
																			class="text-danger d-inline-block mt-2"></span></td>

																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																			target="_blank">As per Table 3</a> of Rules related
																			to IIEPP-2017 (The Rules), there is a provision for
																			reimbursement of 70% of deposited GST (Net SGST) for
																			10 years.</td>
																		<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td> -->
																		<td><textarea class="form-control"></textarea></td>
																	</tr>
                                                                    
																	<c:if
																		test="${not empty incentiveDeatilsData.isfSgstComment}">
																		<tr class="ISF_Claim_Reim-row">
																			<td colspan="5">
																			<p class="text-info">Comment From : Industrial Development/ Commercial Tax</p>
																			<form:textarea
																					class="form-control" path="isfSgstComment"
																					name="isfSgstComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Reim_SCST-row">
																		<td>1.1</td>
																		<td>Additional 10% GST where 25% minimum SC/ST
																			workers employed subject to minimum of 400 total
																			workers in industrial undertakings located in
																			Paschimanchal and minimum of 200 total workers in
																			B-P-M
																			<br>
																			<strong>Industrial Development/ Commercial
																				Tax</strong>

																		</td>
																		<td><form:input path="ISF_Reim_SCST" type="text"
																				id="Add_SCST_Textbox"
																				class="ISF_Reim_SGST form-control"
																				name="ISF_Reim_SCST" maxlength="12" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																			target="_blank">As per Table 3</a> of The Rules,
																			there is a provision for 75% Stamp Duty Exemption in
																			Paschimanchal Region. <br>Further, as per G.O.
																			No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there is a
																			provision for reimbursement equivalent to the paid
																			Stamp Duty, for which company will have to apply
																			before concerned GM, DIC.</td>
																		<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td> -->
																		<td><textarea class="form-control"></textarea></td>
																	</tr>

																	<c:if
																		test="${not empty incentiveDeatilsData.isfScstComment}">
																		<tr class="ISF_Reim_SCST-row">
																			<td colspan="5"><p class="text-info">Comment From : Industrial Development/ Commercial
																				Tax</p>
																			<form:textarea
																					class="form-control" path="isfScstComment"
																					name="isfScstComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>

																	<tr class="ISF_Reim_FW-row">
																		<td>1.2</td>
																		<td>Additional 10% GST where 40% minimum female
																			workers employed subject to minimum of 400 total
																			workers in industrial undertakings located in
																			Paschimanchal and minimum of 200 total workers in
																			B-P-M</td>
																		<td><form:input path="ISF_Reim_FW" type="text"
																				id="Add_FW_Textbox" maxlength="12"
																				class="ISF_Reim_SGST form-control"
																				name="ISF_Reim_FW" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
																			target="_blank">As per para 3.3</a> and Table 3 of
																			The Rules, there is a provision for incentive of
																			reimbursement of EPF to the extent of 50% of
																			employerÃ¢ÂÂs contribution to all such new
																			Industrial undertakings providing direct employment
																			to 100 or more unskilled workers, after three years
																			from the date of commercial production for a period
																			of 5 years.</td>
																		<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td> -->
																		<td><textarea class="form-control"></textarea></td>
																	</tr>


																	<c:if
																		test="${not empty incentiveDeatilsData.isffwComment}">

																		<tr class="ISF_Reim_FW-row">
																			<td colspan="5"><p class="text-info">Comment From : Industrial Development/ Commercial
																				Tax</p>
																			<form:textarea
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
																			Paschimanchal and minimum of 200 total workers in
																			B-P-M</td>
																		<td><form:input path="ISF_Reim_BPLW" type="text"
																				class="ISF_Reim_SGST form-control" maxlength="12"
																				name="ISF_Reim_BPLW" id="Add_BPLW_Textbox"
																				readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.7</a> of the Rules,
																			there is a following provision: The industries which
																			are disallowed for input tax credit under the GST
																			regime, will be eligible for reimbursement of that
																			amount of GST paid on purchase of plant and
																			machinery, building material and other capital goods
																			during construction and commissioning period and raw
																			materials and other inputs in respect of which input
																			tax credit has not been allowed. While calculating
																			the overall eligible capital investment such amount
																			will be added to the fixed capital investment.</td>
																		<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td> -->
																		<td><textarea class="form-control"></textarea></td>
																	</tr>

																	<c:if
																		test="${not empty incentiveDeatilsData.isfBplComment}">
																		<tr class="ISF_Reim_BPLW-row">
																			<td colspan="5"><p class="text-info">Comment From : Industrial Development/ Commercial
																				Tax</p>
																			<form:textarea
																					class="form-control" path="isfBplComment"
																					name="isfBplComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>

																	<tr class="ISF_Stamp_Duty_EX-row">
																	<tr>
																		<td>2</td>
																		<td>Amount of Stamp Duty Exemption<br> <strong>Stamp
																				& Registration</strong>
																		</td>
																		<td><form:input path="ISF_Stamp_Duty_EX"
																				type="text" class="ISF_Reim_SGST form-control"
																				maxlength="12" name="ISF_Stamp_Duty_EXe"
																				id="ISF_Stamp_Duty_EXe" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per Table 3</a> of The Rules,
																			there is a provision for dynamic percentage Stamp
																			Duty Exemption in Dynamic Region. Further, as per
																			G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there
																			is a provision for reimbursement equivalent to the
																			paid Stamp Duty, for which company will have to apply
																			before concerned GM, DIC.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>
																	
																	<!-- sachin -->
																	<c:if
																		test="${not empty incentiveDeatilsData.isfStampComment}">
																		<tr class="ISF_Stamp_Duty_EX-row">
																			<td colspan="5"><p class="text-info">Comment From : Stamp & Registration</p>
																			<form:textarea
																					class="form-control" path="isfStampComment"
																					name="isfStampComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
																	<!-- <tr class="ISF_Stamp_Duty_EX-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="disabled" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr> -->
																	<tr>
																		<td colspan="5"><strong>OR</strong></td>
																	</tr>

																	<tr class="ISF_Amt_Stamp_Duty_Reim-row">
																		<td></td>
																		<td>Amount of Stamp Duty Reimbursement</td>
																		<td><form:input path="ISF_Amt_Stamp_Duty_Reim"
																				type="text"
																				class="ISF_Amt_Stamp_Duty_Reim form-control"
																				maxlength="12" name="ISF_Amt_Stamp_Duty_Reim"
																				id="Add_ISF_Amt_Stamp_Duty_Reim" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per Table 3</a> of The Rules,
																			there is a provision for dynamic percentage Stamp
																			Duty Exemption in Dynamic Region. Further, as per
																			G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there
																			is a provision for reimbursement equivalent to the
																			paid Stamp Duty, for which company will have to apply
																			before concerned GM, DIC.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>
																	<c:if
																		test="${not empty incentiveDeatilsData.isfStampComment}">
																		<tr class="ISF_Amt_Stamp_Duty_Reim-row">
																			<td colspan="5"><p class="text-info">Comment From : Stamp & Registration</p>
																			<form:textarea
																					class="form-control" path="isfStampComment"
																					name="isfStampComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Additonal_Stamp_Duty_EX-row">
																		<td>2.1</td>
																		<td>Additional Stamp Duty exemption @20% upto
																			maximum of 100% in case of industrial undertakings
																			having 75% equity owned by Divyang/SC/ ST/Females
																			Promoters</td>
																		<td><form:input
																				path="ISF_Additonal_Stamp_Duty_EX" type="text"
																				class="ISF_Additonal_Stamp_Duty_EX form-control"
																				maxlength="12" name="ISF_Additonal_Stamp_Duty_EX"
																				id="ISF_Additonal_Stamp_Duty_EX" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per Table 3</a> of The Rules,
																			there is a provision for dynamic percentage Stamp
																			Duty Exemption in Dynamic Region. Further, as per
																			G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there
																			is a provision for reimbursement equivalent to the
																			paid Stamp Duty, for which company will have to apply
																			before concerned GM, DIC.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>
																	<c:if
																		test="${not empty incentiveDeatilsData.isfStampscstComment}">
																		<tr class="ISF_Additonal_Stamp_Duty_EX-row">
																			<td colspan="5"><p class="text-info">Comment From : Stamp & Registration</p>
																			<form:textarea
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
																				type="text" class="ISF_Epf_Reim_UW form-control"
																				maxlength="12" name="ISF_Amt_Stamp_Duty_Reim"
																				id="Add_ISF_Amt_Stamp_Duty_Reim" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.3</a> and Table 3 of
																			The Rules, there is a provision for incentive of
																			reimbursement of EPF to the extent of 50% of
																			employerâs contribution to all such new Industrial
																			undertakings providing direct employment to 100 or
																			more unskilled workers, after three years from the
																			date of commercial production for a period of 5
																			years.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>
																	<c:if
																		test="${not empty incentiveDeatilsData.isfepfComment}">
																		<tr class="ISF_Epf_Reim_UW-row">
																			<td colspan="5"><p class="text-info">Comment From : Labour/Industrial Development</p>
																			<form:textarea
																					class="form-control" path="isfepfComment"
																					name="isfepfComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Add_Epf_Reim_SkUkW-row">
																		<td>3.1</td>
																		<td>Additional 10% EPF Reimbursement (200 direct
																			skilled and unskilled workers)</td>
																		<td><form:input path="ISF_Add_Epf_Reim_SkUkW"
																				type="text"
																				class="ISF_Add_Epf_Reim_SkUkW form-control"
																				maxlength="12" name="ISF_Add_Epf_Reim_SkUkW"
																				id="ISF_Add_Epf_Reim_SkUkW" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.3</a> and Table 3 of
																			The Rules, there is a provision for incentive of
																			reimbursement of EPF to the extent of 50% of
																			employerâs contribution to all such new Industrial
																			undertakings providing direct employment to 100 or
																			more unskilled workers, after three years from the
																			date of commercial production for a period of 5
																			years.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>
																	<c:if
																		test="${not empty incentiveDeatilsData.isfepfaddComment}">
																		<tr class="ISF_Add_Epf_Reim_SkUkW-row">
																			<td colspan="5"><p class="text-info">Comment From : Labour/Industrial Development</p>
																			<form:textarea
																					class="form-control" path="isfepfaddComment"
																					name="isfepfaddComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
																		<td>3.2</td>
																		<td>Additional 10% EPF Reimbursement upto maximum
																			of 70% in case of industrial undertakings having 75%
																			equity owned by Divyang/SC/ST/Females Promoters</td>
																		<td><form:input path="ISF_Add_Epf_Reim_DIVSCSTF"
																				type="text"
																				class="ISF_Add_Epf_Reim_DIVSCSTF form-control"
																				maxlength="12" name="ISF_Add_Epf_Reim_DIVSCSTF"
																				id="ISF_Add_Epf_Reim_DIVSCSTF" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.3</a> and Table 3 of
																			The Rules, there is a provision for incentive of
																			reimbursement of EPF to the extent of 50% of
																			employerâs contribution to all such new Industrial
																			undertakings providing direct employment to 100 or
																			more unskilled workers, after three years from the
																			date of commercial production for a period of 5
																			years.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>
																	<c:if
																		test="${not empty incentiveDeatilsData.isfepfscComment}">
																		<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
																			<td colspan="5"><p class="text-info">Comment From : Labour/Industrial Development</p>
																			<form:textarea
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
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.1</a> of The Rules,
																			there is a provision for capital interest subsidy @
																			5% p.a. or actual interest paid whichever is less
																			annually for 5 years in the form of reimbursement of
																			interest paid on outstanding loan taken for
																			procurement of plant & machinery, subject to an
																			annual ceiling of Rs. 50 lacs.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>

																	<c:if
																		test="${not empty incentiveDeatilsData.isfcapisComment}">
																		<tr class="ISF_Cis-row">
																			<td colspan="5"><p class="text-info">Comment From : Industrial Development</p>
																			<form:textarea
																					class="form-control" path="isfcapisComment"
																					name="isfcapisComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>

																	<tr class="ISF_ACI_Subsidy_Indus-row">
																		<td>4.1</td>
																		<td>Additional Capital Interest Subsidy@2.5% upto
																			maximum of 7.5% in case of industrial undertakings
																			having 75% equity owned by Divyang/SC/ST/Females
																			Promoters</td>
																		<td><form:input path="ISF_ACI_Subsidy_Indus"
																				type="text"
																				class="ISF_ACI_Subsidy_Indus form-control"
																				maxlength="12" name="ISF_ACI_Subsidy_Indus"
																				id="ISF_ACI_Subsidy_Indus" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.1</a> of The Rules,
																			there is a provision for capital interest subsidy @
																			5% p.a. or actual interest paid whichever is less
																			annually for 5 years in the form of reimbursement of
																			interest paid on outstanding loan taken for
																			procurement of plant & machinery, subject to an
																			annual ceiling of Rs. 50 lacs.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>

																	<c:if
																		test="${not empty incentiveDeatilsData.isfcapisaComment}">
																		<tr class="ISF_ACI_Subsidy_Indus-row">
																			<td colspan="5"><p class="text-info">Comment From : Industrial Development</p>
																			<form:textarea
																					class="form-control" path="isfcapisaComment"
																					name="isfcapisaComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>

																	<tr class="ISF_Infra_Int_Subsidy-row">
																		<td>5</td>
																		<td>Infrastructure Interest Subsidy <br> <strong>Industrial
																				Development</strong></td>
																		<td><form:input path="ISF_Infra_Int_Subsidy"
																				type="text"
																				class="ISF_Infra_Int_Subsidy form-control"
																				maxlength="12" name="ISF_Infra_Int_Subsidy"
																				id="ISF_Infra_Int_Subsidy" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.2</a> of the Rules,
																			there is a provision for incentive of infrastructure
																			interest subsidy @ 5% p.a. or actual interest paid
																			whichever is less annually for 5 years in the form of
																			reimbursement of interest paid on outstanding loan
																			taken for development of infrastructural amenities
																			(as defined in para 2.17) subject to an overall
																			ceiling of Rs. 1 Crore.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>

																	<c:if
																		test="${not empty incentiveDeatilsData.isfinfComment}">
																		<tr class="ISF_Infra_Int_Subsidy-row">
																			<td colspan="5"><p class="text-info">Comment From : Industrial Development</p>
																			<form:textarea
																					class="form-control" path="isfinfComment"
																					name="isfinfComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>

																	<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
																		<td>5.1</td>
																		<td>Additional Infrastructure Interest Subsidy
																			@2.5% upto maximum of 7.5% in case of industrial
																			undertakings having 75% equity owned by
																			Divyang/SC/ST/Females Promoters</td>
																		<td><form:input path="ISF_AII_Subsidy_DIVSCSTF"
																				type="text"
																				class="ISF_AII_Subsidy_DIVSCSTF form-control"
																				maxlength="12" name="ISF_AII_Subsidy_DIVSCSTF"
																				id="ISF_AII_Subsidy_DIVSCSTF" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.2</a> of the Rules,
																			there is a provision for incentive of infrastructure
																			interest subsidy @ 5% p.a. or actual interest paid
																			whichever is less annually for 5 years in the form of
																			reimbursement of interest paid on outstanding loan
																			taken for development of infrastructural amenities
																			(as defined in para 2.17) subject to an overall
																			ceiling of Rs. 1 Crore.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>

																	<c:if
																		test="${not empty incentiveDeatilsData.isfinfaComment}">
																		<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
																			<td colspan="5"><p class="text-info">Comment From : Industrial Development</p>
																			<form:textarea
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
																				type="text" class="ISF_Loan_Subsidy form-control"
																				maxlength="12" name="ISF_Loan_Subsidy"
																				id="ISF_Loan_Subsidy" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.3</a> of The Rules,
																			there is a provision for Interest subsidy on loans
																			for industrial research @ 5% or actual interest paid
																			whichever is less annually for 5 years in the form of
																			reimbursement of interest paid on outstanding loan
																			taken for industrial research, quality improvement
																			and development of products by incurring expenditure
																			on procurement of plant, machinery & equipment for
																			setting up testing labs, quality certification labs
																			and tool rooms, subject to an overall ceiling of Rs.
																			1 Crore</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>

																	<c:if
																		test="${not empty incentiveDeatilsData.isfloanComment}">
																		<tr class="ISF_Loan_Subsidy-row">
																			<td colspan="5"><p class="text-info">Comment From : Industrial Development</p>
																			<form:textarea
																					class="form-control" path="isfloanComment"
																					name="isfloanComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>

																	<tr class="ISF_Tax_Credit_Reim-row">
																		<td>7</td>
																		<td>Reimbursement of Disallowed Input Tax Credit
																			on plant, building materials, and other capital
																			goods. <br> <strong>Industrial
																				Development</strong></br>
																		</td>
																		<td><form:input path="ISF_Tax_Credit_Reim"
																				type="text" class="ISF_Tax_Credit_Reim form-control"
																				maxlength="12" name="ISF_Tax_Credit_Reim"
																				id="ISF_Tax_Credit_Reim" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.7</a> of the Rules,
																			it is provided - The industries which are disallowed
																			for input tax credit under the GST regime, will be
																			eligible for reimbursement of that amount of GST paid
																			on purchase of plant and machinery, building material
																			and other capital goods during construction and
																			commissioning period and raw materials and other
																			inputs in respect of which input tax credit has not
																			been allowed. While calculating the overall eligible
																			capital investment such amount will be added to the
																			fixed capital investment.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>

																	<c:if
																		test="${not empty incentiveDeatilsData.isfdisComment}">
																		<tr class="ISF_Tax_Credit_Reim-row">
																			<td colspan="5"><p class="text-info">Comment From : Industrial Development</p>
																			<form:textarea
																					class="form-control" path="isfdisComment"
																					name="isfdisComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>

																	<tr class="ISF_EX_E_Duty-row">
																		<td>8</td>
																		<td>Exemption from Electricity Duty from captive
																			power for self-use <br> <strong>Energy/UPPCL/
																				Electricity Safety</strong></br>
																		</td>
																		<td><form:input path="ISF_EX_E_Duty" type="text"
																				class="ISF_EX_E_Duty form-control" maxlength="12"
																				name="ISF_EX_E_Duty" id="ISF_EX_E_Duty"
																				readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.5</a> of The Rules,
																			there is a provision for Exemption from Electricity
																			Duty for 10 years to all new industrial undertakings
																			producing electricity from captive power plants for
																			self-use.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>

																	<c:if
																		test="${not empty incentiveDeatilsData.isfelepodownComment}">
																		<tr class="ISF_EX_E_Duty-row">
																			<td colspan="5"><p class="text-info">Comment From : UPPCL</p>
																			<form:textarea
																					class="form-control" path="isfelepodownComment"
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
																				type="text" class="ISF_EX_E_Duty_PC form-control"
																				maxlength="12" name="ISF_EX_E_Duty_PC"
																				id="ISF_EX_E_Duty_PC" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.4</a> of The Rules,
																			there is a provision for Exemption from Electricity
																			Duty to all new industrial undertakings set up in the
																			State for 10 years.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>


																	<c:if
																		test="${not empty incentiveDeatilsData.isfElecdutyComment}">
																		<tr class="ISF_EX_E_Duty_PC-row">
																			<td colspan="5"><p class="text-info">Comment From : UPPCL</p>
																			<form:textarea
																					class="form-control" path="isfElecdutyComment"
																					name="isfElecdutyComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>

																	<tr class="ISF_EX_Mandee_Fee-row">
																		<td>10</td>
																		<td>Exemption from Mandi Fee <br> <strong>Agriculture
																				Marketing & Agriculture Foreign Trade/MandiParishad</strong></br></td>
																		<td><form:input path="ISF_EX_Mandee_Fee"
																				type="text" class="ISF_EX_Mandee_Fee form-control"
																				maxlength="12" name="ISF_EX_Mandee_Fee"
																				id="ISF_EX_Mandee_Fee" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.6</a> of The Rules,
																			There is provision for exemption from Mandi Fee for
																			all new food-processing undertakings on purchase of
																			raw material for 5 years.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>


																	<c:if
																		test="${not empty incentiveDeatilsData.isfMandiComment}">
																		<tr class="ISF_EX_Mandee_Fee-row">
																			<td colspan="5"><p class="text-info">Comment From : Agriculture
																				Marketing & Agriculture Foreign Trade/MandiParishad</p>
																			<form:textarea
																					class="form-control" path="isfMandiComment"
																					name="isfMandiComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Indus_Payroll_Asst-row">
																		<td>11</td>
																		<td>Industrial units providing employment to
																			differently abled workers will be provided payroll
																			assistance of Rs. 500 per month for each such worker.</td>
																		<td><form:input path="ISF_Indus_Payroll_Asst"
																				type="text"
																				class="ISF_Indus_Payroll_Asst form-control"
																				maxlength="12" name="ISF_Amt_Stamp_Duty_Reim"
																				id="Add_ISF_Amt_Stamp_Duty_Reim" readonly="true"></form:input></td>
																		<td>No such provision in The Rules.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>

																	<c:if
																		test="${not empty incentiveDeatilsData.isfdifferabilComment}">
																		<tr class="ISF_Indus_Payroll_Asst-row">
																			<td colspan="5"><p class="text-info">Comment From : Labour/Industrial Development</p>
																			<form:textarea
																					class="form-control" path="isfdifferabilComment"
																					name="isfdifferabilComment" disabled="" rows="3"
																					placeholder="Comments"></form:textarea></td>
																		</tr>
																	</c:if>

																	<tr>
																		<td colspan="2" align="Center"><strong>Total</strong></td>
																		<td><strong>${total}</strong></td>
																		<td colspan="4"></td>
																	</tr>

																</tbody>
															</table>
														</div>

													</div>
												</div>
												<c:if
													test="${not empty incentiveDeatilsData.othAddRequest1}">
													<div class="row">
														<div class="col-sm-12">
															<div class="form-group">
																<label>Additional Request</label>
																<form:input path="othAddRequest1" type="text"
																	class="form-control" placeholder="" maxlength="250"
																	name="othAddRequest1" readonly="true"></form:input>
															</div>
														</div>
													</div>
												</c:if>
												</form:form>
												<div class="row">
												 <div class="col-sm-12">
				                                    <div class="form-group">
				                                      <label>Note <small>(If Any)</small></label>
				                                     <textarea class="form-control" disabled="disabled" rows="4">${prepAgenNotes}</textarea>
				                                    </div>
				                                  </div>
				                                </div>
							</c:forEach>
							
						</div>
						<hr>
						<form:form modelAttribute="PrepareAgendaNotes"
							action="approvedAgendaNoteByDicDepart" class="mt-4"
							name="PrepareAgendaNotes" method="POST"
							onsubmit="return ApprovedAgendaNoteValidation()" id="lowerbutton">
							<form:hidden path="prepareAgendaNote" id="prepareAgendaNote"></form:hidden>
							<div class="row mb-3">
								<!-- <div class="col-sm-5">
									<a href="./prepareAgendaNoteDicList"
										class="common-default-btn mt-3">Back</a>
								</div> -->


								<%-- <div class="col-sm-7 text-right">
									<form:hidden path="appliId" id="appliId"></form:hidden>
									<input type="submit" value="Submit for Approval"
										onclick="return SubmitToApproval()" class="common-btn mt-0">

								</div> --%>

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





	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.min.js"></script>
	<script src="js/custom.js"></script>
	<script type="text/javascript">
		$(document).on('click', '#printMethod', function(e) {
			e.preventDefault();

			var $this = $(this);
			var originalContent = $('body').html();
			var printArea = document.getElementById('printform').innerHTML;

			var printbutton = document.getElementById("lowerbutton");
			printbutton.style.visibility = 'hidden';

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
											pdf.save("Agenda Note Form.pdf");
										});
							});
				});
	</script>

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
		$(document).ready(CostasperDPR);
		$(document).on("keyup", CostasperDPR);

		function CostasperDPR() {
			var sum = 0;
			$(".CostasperDPR").each(function() {
				sum += +$(this).val();
			});
			$(".TotalCostasperDPR").val(sum);
		}

		$(document).ready(CostasperIIEPP);
		$(document).on("keyup", CostasperIIEPP);
		$(document).on("change", CostasperIIEPP);

		function CostasperIIEPP() {
			var sum = 0;
			$(".CostasperIIEPP").each(function() {
				sum += +$(this).val();
			});
			$(".TotalCostasperIIEPP").val(sum);
		}

		$(function() {
			var ctypeSMALLselect = '${ctypeSMALL}';
				if (ctypeSMALLselect == 'No') {
				$(".in-case-of-small-row").hide();
			}

			var ctypeLARGEselect = '${ctypeLARGE}';
				if (ctypeLARGEselect == 'No') {
				$(".in-case-of-large-row").hide();
			}

			var ctypeMEGAselect = '${ctypeMEGA}';
				if (ctypeMEGAselect == 'No') {
				$(".in-case-of-mega-row").hide();
			}

				var ctypeSUPERMEGAselect = '${ctypeSUPERMEGA}';
				if (ctypeSUPERMEGAselect == 'No') {
				$(".in-case-of-supermega-row").hide();
			}
				
		});
	</script>
	<script>
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

		var natureOfProject = '${natureOfProject}';
		if (natureOfProject == 'NewProject') {
			//alert("hi");
			$(".NewProject-row").hide();
		}
	}
	$(document).ready(showYesOrNo2);
	</script>
	
</body>

</html>
</html>