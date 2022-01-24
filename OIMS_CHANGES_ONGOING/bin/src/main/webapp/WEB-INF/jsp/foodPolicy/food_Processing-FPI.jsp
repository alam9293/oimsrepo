<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="com.webapp.ims.model.PhaseWiseInvestmentDetails"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!doctype html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Food View All Applicant Details</title>
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
						<li class="nav-item"><a class="nav-link"
							href="./fooduserLogout"><i class="fa fa-power-off"></i>
								Logout</a></li>

						<li class="nav-item"><a class="nav-link active" href="#"><i
								class="fa fa-user"></i>${userName} </a></li>

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
							href="./foodPolicyDashboard"><i class="fa fa-tachometer"></i>
								Dashboard</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./foodviewEvaluate"><i class="fa fa-eye"></i> View and
								Evaluate Applications</a></li>
					</ul>
				</div>
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<h4 class="card-title mb-4 mt-4 text-center">FOOD PROCESSING
						INDUSTRY POLICY-2017</h4>
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-12">
								<div class="mt-4">
									<div class="without-wizard-steps">
										<div class="row">
											<div class="col-sm-8">
												<a href="./foodProcessIndustryPolicy"
													class="common-default-btn mt-0">Back</a> <a
													href="./evaluationViewIS" class="common-btn mt-3"
													id="evaluateBtnTop">Evaluate Application</a>
												<!-- <button type="button" class="btn btn-outline-info btn-sm" data-target="#SendtoConcernDepartment" data-toggle="modal">Send to Concern Department</button> -->
											</div>
											<div class="col-sm-4 text-right">
												<!-- <div class="form-group">
                                                    <select class="form-control" id="SelectParameter">
                                                        <option value="viewAllApplicationDetails">View as per entrepreneur</option>
                                                        <option value="Annexure-I">View as per policy LOC form Annexure-I</option>
                                                        <option value="Annexure-III">View as per policy LOC form Annexure-III</option>
                                                    </select>
                                                </div> -->
											</div>
										</div>
										<hr>
										<div class="row mt-4">
											<div class="col-sm-12 text-center">
												<button type="button"
													class="btn btn-outline-success btn-sm mb-3">
													<i class="fa fa-download"></i> Download Only Application
													Form
												</button>

												<button type="button"
													class="btn btn-outline-secondary btn-sm mb-3">
													<i class="fa fa-download"></i> Download Only Supporting
													Docs
												</button>
											</div>
										</div>


										<!-- Start view All Application Details -->

										<div class="parameter-properties"
											id="viewAllApplicationDetails">
											<hr class="mt-2">
											<h4 class="card-title mb-4 mt-4 text-center">Business
												Entity Details</h4>

											<div class="isf-form mt-4">
												<div class="row">
													<div class="col-sm-12">
														<div class="table-responsive">
															<table class="table table-bordered">
																<tbody>
																	<%-- <c:forEach var="list" items="${businessEntityDetailsfood}"> --%>
																	<tr>
																		<td>Name of Promoter</td>
																		<td><input type="text" class="form-control" id=""
																			value="${businessEntityDetailsfood.nameofpromoter}"
																			disabled="disabled" name=""></td>
																		<td rowspan="4" class="text-center"><img
																			src="data:image/png;base64,${businesfoodImg}"
																			style="height: 250px;" /></td>
																	</tr>
																	<tr>
																		<td>Name of Company</td>
																		<td><input type="text" class="form-control" id=""
																			value="${businessEntityDetailsfood.nameofcompany}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>Address of Company</td>
																		<td><input type="text" class="form-control" id=""
																			value="${businessEntityDetailsfood.addressofcompany}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>Mobile No</td>
																		<td><input type="text" class="form-control" id=""
																			value="${businessEntityDetailsfood.mobileno}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>Email</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.email}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>State</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.companystate}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>District</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.companydistrict}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>PAN Card of Firm /Proprietor</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.pancardoffirmproprietor}"
																			disabled="" name=""></td>
																	</tr>

																	<tr>
																		<td>GSTIN</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.gstin}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>Type of Organization</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.typeoforganization}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>Date of Incorporation</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.dateofincorporation}"
																			disabled="" name=""></td>
																	</tr>
																	<%-- </c:forEach> --%>
																	<tr>
																		<td colspan="3"><strong>Financial Status</strong></td>
																	</tr>
																	<table
																		class="table table-stripped table-bordered directors-table"
																		id="customFields">
																		<thead>
																			<tr>
																				<th>Financial Year</th>
																				<th>Turn Over (In Lakh)</th>

																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach var="list" items="${fianancStatusList}"
																				varStatus="counter">
																				<tr>
																					<td><input type="text" class="form-control"
																						id="" value="${list.financialyear}" disabled=""
																						name=""></td>
																					<td><input type="text" class="form-control"
																						id="" value="${list.turnover}" disabled="" name=""></td>
																				</tr>
																			</c:forEach>
																		</tbody>
																	</table>




																	</div>
																	</div>
																	</div>
																	</div>

																	<hr class="mt-2">

																	<h4 class="card-title mb-4 mt-4 text-center">Project
																		and Proposed Employment Details</h4>

																	<div class="isf-form mt-4">
																		<hr class="mt-2">
																		<div class="row">
																			<div class="col-sm-12">
																				<div class="table-responsive">
																					<table class="table table-bordered">
																						<tbody>
																							<tr>
																								<td style="width: 50%;">District</td>
																								<td><input type="text" class="form-control"
																									id=""
																									value="${projectandproposedemploymentdetails.district}"
																									disabled="" name=""></td>
																							</tr>
																							<tr>
																								<td>Division</td>
																								<td><input type="text" class="form-control"
																									id=""
																									value="${projectandproposedemploymentdetails.division}"
																									disabled="" name=""></td>
																							</tr>
																							<tr>
																								<td>Sector</td>
																								<td><input type="text" class="form-control"
																									id=""
																									value="${projectandproposedemploymentdetails.sector}"
																									disabled="" name=""></td>

																							</tr>
																							<tr>
																								<td>Sub-Sector</td>
																								<td><input type="text" class="form-control"
																									id=""
																									value="${projectandproposedemploymentdetails.selectsubsector}"
																									disabled="" name=""></td>
																							</tr>
																							<tr>
																								<td>Name of the Project</td>
																								<td><input type="text" class="form-control"
																									id=""
																									value="${projectandproposedemploymentdetails.nameoftheproject}"
																									disabled="" name=""></td>
																							</tr>
																							<tr>
																								<td>Location/Area of the project</td>
																								<td><input type="text" class="form-control"
																									id=""
																									value="${projectandproposedemploymentdetails.locationareaoftheproject}"
																									disabled="" name=""></td>
																							</tr>


																							<tr>
																								<td colspan="3"><strong>Product
																										Details</strong></td>
																							</tr>
																							<table
																								class="table table-stripped table-bordered directors-table"
																								id="customFields">
																								<thead>
																									<tr>
																										<th>Products</th>
																										<th>By Products</th>

																									</tr>
																								</thead>
																								<tbody>
																									<c:forEach var="list" items="${productdetails}"
																										varStatus="counter">
																										<tr>
																											<td><input type="text"
																												class="form-control" id=""
																												value="${list.products}" disabled="" name=""></td>
																											<td><input type="text"
																												class="form-control" id=""
																												value="${list.byproducts}" disabled=""
																												name=""></td>
																										</tr>
																									</c:forEach>

																								</tbody>
																							</table>
																							<tr>
																								<td><label for="unittype"
																									class="formbuilder-name-label"><strong>Technology
																											(Indigenous/ imported)</strong><span
																										class="formbuilder-required"></span></label></td>
																								<td><input type="text"
																									class="form-control valid" name="" id=""
																									value="${projectandproposedemploymentdetails.technology}"
																									readonly="" aria-invalid="false">
																								<td>
																							</tr>

																							<tr>
																								<h1 class="common-heading">In case of
																									expansion/ modernization of existing
																									facilities/unit</h1>
																							</tr>

																							<tr>
																								<td><strong>Capacity of the Plant
																										Or Unit</strong></td>
																							</tr>
																							<table
																								class="table table-stripped table-bordered directors-table"
																								id="customFields">
																								<thead>
																									<tr>
																										<th>Items</th>
																										<th>Unit Type</th>
																										<th>Existing Unit</th>
																										<th>New Unit</th>
																										<th>Total After expansion</th>
																									</tr>
																								</thead>
																								<tbody>
																									<tr>
																										<td><strong>Capacity</strong></td>
																										<td>${capacityoftheplantorunit.unittypecapacity}</td>
																										<td><input type="text"
																											class="form-control" id=""
																											value="${capacityoftheplantorunit.existingunitcapacity}"
																											disabled="" name=""></td>
																										<td><input type="text"
																											class="form-control" id=""
																											value="${capacityoftheplantorunit.newunitcapacity}"
																											disabled="" name=""></td>
																										<td><input type="text"
																											class="form-control" id=""
																											value="${capacityoftheplantorunit.totalafterexpansioncapacity}"
																											disabled="" name=""></td>
																									</tr>

																								</tbody>
																							</table>

																							<tr>
																								<h1 class="common-heading">Employment
																									Details</h1>
																							</tr>

																							<tr>
																								<td><strong>Employment Generation</strong></td>
																							</tr>
																							<table
																								class="table table-stripped table-bordered directors-table"
																								id="customFields">
																								<thead>
																									<tr>
																										<th width="50%">Items</th>
																										<th>No Of Person</th>
																									</tr>
																								</thead>
																								<tbody>
																									<tr>
																										<td><strong>Direct</strong></td>
																										<td><input type="text"
																											class="form-control" id=""
																											value="${employmentgeneration.noofpersondirect}"
																											disabled="" name=""></td>
																									</tr>
																									<tr>
																										<td><strong>Indirect</strong></td>
																										<td><input type="text"
																											class="form-control" id=""
																											value="${employmentgeneration.noofpersonindirect}"
																											disabled="" name=""></td>
																									</tr>
																									<tr>
																										<td><strong>Total</strong></td>
																										<td><input type="text"
																											class="form-control" id=""
																											value="${employmentgeneration.noofpersontotal}"
																											disabled="" name=""></td>
																									</tr>

																								</tbody>
																							</table>





																							</div>
																							</div>
																							</div>
																							</div>


																							<h4 class="card-title mb-4 mt-4 text-center">Investment
																								Details</h4>

																							<div class="isf-form mt-4">
																								<hr class="mt-2">
																								<div class="row">
																									<div class="col-sm-12">
																										<div class="table-responsive">
																											<table class="table table-bordered">
																												<tbody>
																													<h1 class="common-heading">Unit
																														Details</h1>
																													<tr>
																														<label for="unittype"
																															class="formbuilder-name-label"><strong>Unit
																																Type</strong><span class="formbuilder-required"></span></label>
																														<input type="text"
																															class="form-control valid"
																															name="Unit Type" id="unittype"
																															value="${investmentDetailsFood.unittype}"
																															readonly="" aria-invalid="false">
																													</tr>
																													<tr>
																														<td><strong>Project
																																Investment Details</strong></td>
																													</tr>
																													<table
																														class="table table-stripped table-bordered directors-table"
																														id="customFields">
																														<thead>
																															<tr>
																																<th>Items</th>
																																<th>Existing Investment (In Lakh)</th>
																																<th>Proposed Investment (In Lakh)</th>
																																<th>Appraised Cost By Bank (In
																																	Lakh)</th>
																															</tr>
																														</thead>
																														<tbody>
																															<c:forEach var="pidList"
																																items="${projectInvestmentDetails}"
																																varStatus="counter">

																																<tr>
																																	<td><strong>Land Cost</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.existinginvestmentlandcost}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.proposedinvestmentlandcost}"
																																		"
																																	disabled=""
																																		name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.appraisedcostbybanklandcost}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Building</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.existinginvestmentcivilwork}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.proposedinvestmentcivilwork}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.appraisedcostbybankcivilwork}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Plant &
																																			Machinery</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.existinginvestmentplantmachinery}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.proposedinvestmentplantmachinery}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.appraisedcostbybankplantmachinery}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Pre-operative
																																			expenses</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.existinginvestmentpreoperativeexpenses}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.proposedinvestmentpreoperativeexpenses}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.appraisedcostbybankpreoperativeexpenses}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Working
																																			Capital</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.existinginvestmentworkingcapital}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.proposedinvestmentworkingcapital}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.appraisedcostbybankworkingcapital}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Total</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.existinginvestmenttotal}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.proposedinvestmenttotal}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${pidList.appraisedcostbybanktotal}"
																																		disabled="" name=""></td>

																																</tr>
																															</c:forEach>
																														</tbody>
																													</table>

																													<td><strong>Means of
																															Financing</strong></td>
																													</tr>
																													<table
																														class="table table-stripped table-bordered directors-table"
																														id="customFields">
																														<thead>
																															<tr>
																																<th>Items</th>
																																<th>Proposed Investment (In Lakh)</th>
																																<th>Appraised Cost By Bank (In
																																	Lakh)</th>
																															</tr>
																														</thead>
																														<tbody>
																															<c:forEach var="mofList"
																																items="${meansofFinancing}"
																																varStatus="counter">
																																<tr>
																																	<td><strong>Promoter's
																																			Equitygyan</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${mofList.proposedinvestmentpromotersequity}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${mofList.appraisedcostbybankpromotersequity}"
																																		disabled="" name=""></td>

																																</tr>
																																<tr>
																																	<td><strong>Term Loan</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${mofList.proposedinvestmenttermloan}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${mofList.appraisedcostbybanktermloan}"
																																		disabled="" name=""></td>

																																</tr>
																																<tr>
																																	<td><strong>Margin Money
																																			for Working Capital</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${mofList.proposedinvestmentmarginmoneyforworkingcapital}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${mofList.appraisedcostbybankmarginmoneyforworkingcapital}"
																																		disabled="" name=""></td>

																																</tr>
																																<tr>
																																	<td><strong>Others</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${mofList.proposedinvestmentothers}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${mofList.appraisedcostbybankothers}"
																																		disabled="" name=""></td>

																																</tr>
																																<tr>
																																	<td><strong>Total</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${mofList.proposedinvestmenttotal}"
																																		disabled="" name=""></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${mofList.appraisedcostbybanktotal}"
																																		disabled="" name=""></td>

																																</tr>
																															</c:forEach>
																														</tbody>
																													</table>

																													<td><strong>Implementation
																															Schedule</strong></td>
																													</tr>
																													<table
																														class="table table-stripped table-bordered directors-table"
																														id="customFields">
																														<thead>
																															<tr>
																																<th width="50%">Items</th>
																																<th>Details</th>

																															</tr>
																														</thead>
																														<tbody>
																															<c:forEach var="isList"
																																items="${implementationSchedule}"
																																varStatus="counter">
																																<tr>
																																	<td><strong>Acquisition
																																			Date of Land</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${isList.detailsacquisitiondateofland}"
																																		disabled="" name=""></td>


																																</tr>
																																<tr>
																																	<td><strong>Date of
																																			Building Construction-From</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${isList.detailsdateofbuildingconstructionfrom}"
																																		disabled="" name=""></td>


																																</tr>
																																<tr>
																																	<td><strong>Date of
																																			Building Construction-To</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${isList.detailsdateofbuildingconstructionto}"
																																		disabled="" name=""></td>

																																</tr>
																																<tr>
																																	<td><strong>Date of
																																			Placing order for plant &
																																			machinery-From</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${isList.detailsdateofplacingorderforplantmachineryfrom}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Date of
																																			Placing order for plant &
																																			machinery-To</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${isList.detailsdateofplacingorderforplantmachineryto}"
																																		disabled="" name=""></td>

																																</tr>
																																<tr>
																																	<td><strong>Trial
																																			Production Date-From</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${isList.detailstrialproductiondatefrom}"
																																		disabled="" name=""></td>

																																</tr>
																																<tr>
																																	<td><strong>Date of
																																			Placing order for plant &
																																			machinery-To</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${isList.detailstrialproductiondateto}"
																																		disabled="" name=""></td>

																																</tr>
																																<tr>
																																	<td><strong>Date of
																																			Commercial production/running</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${isList.detailsdateofcommercialproductionrunning}"
																																		disabled="" name=""></td>

																																</tr>
																															</c:forEach>
																														</tbody>
																													</table>

																													<td><strong>Bank/Term Loan
																															Details</strong></td>
																													</tr>
																													<table
																														class="table table-stripped table-bordered directors-table"
																														id="customFields">
																														<thead>
																															<tr>
																																<th>Items</th>
																																<th>Details</th>

																															</tr>
																														</thead>
																														<tbody>
																															<c:forEach var="bdList"
																																items="${bankTermLoanDetails}"
																																varStatus="counter">
																																<tr>
																																	<td width="50%"><strong>IFSC
																																			Code</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailsifsccode}"
																																		disabled="" name=""></td>


																																</tr>
																																<tr>
																																	<td><strong>Name of the
																																			Bank / FI</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailsnameofthebankfi}"
																																		disabled="" name=""></td>


																																</tr>
																																<tr>
																																	<td><strong>Branch
																																			Address</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailsbranchaddress}"
																																		disabled="" name=""></td>

																																</tr>
																																<tr>
																																	<td><strong>Bank Email</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailsbankemail}"
																																		disabled="" name=""></td>

																																</tr>
																																<tr>
																																	<td><strong>Bank
																																			Telephone</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailsbanktelephone}"
																																		disabled="" name=""></td>


																																</tr>
																																<tr>
																																	<td><strong>Account no.</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailsaccountno}"
																																		disabled="" name=""></td>


																																</tr>
																																<tr>
																																	<td><strong>Rate of
																																			interest (%)</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailsrateofinterest}"
																																		disabled="" name=""></td>


																																</tr>
																																<tr>
																																	<td><strong>Date of
																																			Sanction Of Term Loan</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailsdateofsanctionoftermloan}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Date of First
																																			Disbursement</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailsdateoffirstdisbursement}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Date of Last
																																			Disbursement</strong></td>

																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailsdateoflastdisbursement}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Total Amount
																																			of term loan sanctioned</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailstotalamountoftermloansanctioned}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Total Amount
																																			of Term loan sanctioned for new plant
																																			machinery/spares/technical civil work</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailstotalamountoftermloansanctionedfornewplantmachineryspares}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Total Amount
																																			of term loan disbursed</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailstotalamountoftermloandisbursed}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Total Amount
																																			of first disbursement</strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailstotalamountoffirstdisbursement}"
																																		disabled="" name=""></td>
																																</tr>
																																<tr>
																																	<td><strong>Total Amount
																																			of last disbursement/strong></td>
																																	<td><input type="text"
																																		class="form-control" id=""
																																		value="${bdList.detailstotalamountoflastdisbursement}"
																																		disabled="" name=""></td>
																																</tr>
																															</c:forEach>
																														</tbody>
																													</table>

																													</div>
																													</div>
																													</div>
																													</div>

<!-- sachin -->
<div>
<h4 class="card-title">Details of Proposed Plant And Machinery to be Installed</h4>
</div>
<div class="col-sm-12">
<div class="table-responsive">
<table class="table table-bordered" id="exceltable">
<thead>
<tr>
 <th>S.No.</th>
<th>MachineName</th>
<th>SupplierName</th>
<th>invoicetype</th>
<th>invoiceNo</th>
<th>invoicedate</th>
<th>Quantity</th>
<th>BasicPrice</th>
<th>EligibleCost</th>
<th>GSTAmount</th>
<th>Installation/Erection/TransporationCharges</th>
<th>Total Amount</th>
 </tr>
</thead>
<tbody>
<c:forEach var="list" items="${plantAndMachinerytobeCSIS}"
varStatus="counter">
 <tr>
<td><c:out value="${list.sno}" /></td>
<td><c:out value="${list.machinename}" /></td>
<td><c:out value="${list.suppliername}" /></td>
<td><c:out value="${list.invoicetype}" /></td>
<td><c:out value="${list.invoiceno}" /></td>
<td><c:out value="${list.invoicedate}" /></td>
<td><c:out value="${list.quantity}" /></td>
<td><c:out value="${list.basicprice}" /></td>
<td><c:out value="${list.eligiblecost}" /></td>
<td><c:out value="${list.gstamount}" /></td>
<td><c:out value="${list.installationerectiontransporationcharges}" /></td>
<td><c:out value="${list.totalamount}" /></td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
<div class="col-md-12"> 
<ul class="pagination pull-right"> 
<li class="page-item"><a class="page-link" href="#">Previous</a></li> 
<li class="page-item active"><a class="page-link" href="#">1</a></li> 
<li class="page-item"><a class="page-link" href="#">2</a></li> 
<li class="page-item"><a class="page-link" href="#">3</a></li> 
<li class="page-item"><a class="page-link" href="#">Next</a></li> 
</ul> 
</div>
</div>
<br>
<br>
<div>
<h4 class="card-title">Details of Eligible Technical Civil Work</h4>
</div>
<div class="col-sm-12">
<div class="table-responsive1">
<table class="table table-bordered" id="exceltable">
<thead>
<tr>
 <th>S.No.</th>
<th>Particulars</th>
<th>Area Sqft</th>
<th>Rate</th>
<th>Amount</th>
<th>Eligibility Cost(in Lacs)</th>

 </tr>
</thead>
<tbody>
<c:forEach var="list" items="${detailsofEligibleTechnicalCivilWork}"
varStatus="counter">
 <tr>
<td><c:out value="${list.sno}" /></td>
<td><c:out value="${list.particulars}" /></td>
<td><c:out value="${list.areasqft}" /></td>
<td><c:out value="${list.rate}" /></td>
<td><c:out value="${list.amount}" /></td>
<td><c:out value="${list.eligiblecost}" /></td>

</tr>
</c:forEach>
</tbody>
</table>
</div>
<div class="col-md-12"> 
<ul class="pagination pull-right"> 
<li class="page-item"><a class="page-link" href="#">Previous</a></li> 
<li class="page-item active"><a class="page-link" href="#">1</a></li> 
<li class="page-item"><a class="page-link" href="#">2</a></li> 
<li class="page-item"><a class="page-link" href="#">Next</a></li> 
</ul> 
</div>
</div>
																													<!-- sachin -->

																													<h4
																														class="card-title mb-4 mt-4 text-center">Documents</h4>

																													<div class="isf-form mt-4">
																														<hr class="mt-2">
																														<div class="row">
																															<div class="col-sm-12">
																																<div class="table-responsive">
																																	<table class="table table-bordered">
																																		<tbody>
																																			<tr>
																																				<td width="50%"><strong>Incorporation
																																						Certificate of Firm</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.incorporationcertificateoffirm}">${foodDocument.incorporationcertificateoffirm}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Partnership
																																						deed / Byelaws of the Firm</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.partnershipdeedbyelawsofthefirm}">${foodDocument.partnershipdeedbyelawsofthefirm}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Registered
																																						Land Deed / Rent Agreement</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.registeredlanddeedrentagreement}">${foodDocument.registeredlanddeedrentagreement}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Khasra/Khatauni</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.khasrakhatauni}">${foodDocument.khasrakhatauni}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Detailed
																																						Project Report as per Annexure
																																						A/16</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.detailedprojectreportasperannexurea16}">${foodDocument.detailedprojectreportasperannexurea16}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Term
																																						loan Sanction letter</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.termloansanctionletter}">${foodDocument.termloansanctionletter}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Bank
																																						Appraisal Report</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.bankappraisalreport}">${foodDocument.bankappraisalreport}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Building
																																						Plan of Factory Approved by
																																						Competent Authority</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.buildingplanoffactoryapprovedbycompetentauthority}">${foodDocument.buildingplanoffactoryapprovedbycompetentauthority}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Udyog
																																						Aadhar / Udyam / IEM registration
																																						certificate</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.udyogaadharudyamregistration}">${foodDocument.udyogaadharudyamregistration}</a>
																																			</tr>
																																			
																																			<!-- sachin -->
																																				<tr>
																																				<td width="50%"><strong>NOC for Pollution</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.nocforpollution}">${foodDocument.nocforpollution}</a>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>>NOC for Fire Safety</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.nocforfiresafety}">${foodDocument.nocforfiresafety}</a>
																																			</tr><tr>
																																				<td width="50%"><strong>Power load sanction letter form UPPCL</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.powerloadsanctionletterformuppcl}">${foodDocument.powerloadsanctionletterformuppcl}</a>
																																			</tr><tr>
																																				<td width="50%"><strong>FSSAI License/Certificate</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.fssailicensecertificate}">${foodDocument.fssailicensecertificate}</a>
																																			</tr>
																																			
																																			<!-- sachan -->
																																			<tr>
																																				<td width="50%"><strong>Repayment
																																						Schedule of Term Loan</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.repaymentscheduleoftermloan}">${foodDocument.repaymentscheduleoftermloan}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Annexure-A/6
																																						(Affidavit/Declaration on 100
																																						Rupees Non Judicial Stamp Paper )</strong></td>

																																					<td><a href="./downloadFoodDocument?fileName=${foodDocument.annexurea6}">${foodDocument.annexurea6}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Annexure-A/7
																																						Chartered Engineer (civil)
																																						Certificate for civil work</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.annexurea7charteredengineercertificateforcivilwork}">${foodDocument.annexurea7charteredengineercertificateforcivilwork}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Annexure-A/8
																																						CE (Mechanical) Certificate for
																																						Plant & Machinery</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.annexurea8cecertificateforplantampmachinery}">${foodDocument.annexurea8cecertificateforplantampmachinery}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Annexure-A/10
																																						(Affidavit on 100 Rs Non Judicial
																																						Stamp Paper)</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.annexurea10}">${foodDocument.annexurea10}</a>
																																				</td>
																																			</tr>

																																			<tr>
																																				<td width="50%"><strong>Annexure-A/13
																																						(Bank Certificate for interest
																																						liability)</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.annexurea13}">${foodDocument.annexurea13}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Annexure-A/14
																																						(Interest subsidy claim
																																						application from bank)</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.annexurea14}">${foodDocument.annexurea14}</a>
																																				</td>
																																			</tr>
																																			<tr>
																																				<td width="50%"><strong>Annexure-A/18
																																						CA certificate for increase in
																																						Gross Block Value.</strong></td>

																																				<td><a
																																					href="./downloadFoodDocument?fileName=${foodDocument.annexurea18cacertificateforincreaseingrossblockvalue}">${foodDocument.annexurea18cacertificateforincreaseingrossblockvalue}</a>
																																				</td>
																																			</tr>


																																			</div>
																																			</div>
																																			</div>
																																			</div>
																																		</tbody>
																																	</table>



																																	<!-- End view All Application Details  -->



																																	<!-- Start Annexure-I  -->



																																	<!-- End Annexure-I  -->


																																	<!-- Start Annexure-III -->



																																	<!-- End Annexure-III  -->





																																	<div class="isf-form mt-4">
																																		<hr class="mt-4 mb-4">
																																		<div class="row mt-4">
																																			<div class="col-sm-12 text-center">
																																				<button type="button"
																																					class="btn btn-outline-success btn-sm mb-3">
																																					<i class="fa fa-download"></i>
																																					Download Only Application Form
																																				</button>

																																				<button type="button"
																																					class="btn btn-outline-secondary btn-sm mb-3">
																																					<i class="fa fa-download"></i>
																																					Download Only Supporting Docs
																																				</button>
																																			</div>
																																		</div>
																																		<hr class="mt-2">
																																		<div class="row">
																																			<div class="col-sm-4">
																																				<a
																																					href="./foodProcessIndustryPolicy"
																																					class="common-default-btn mt-3">Back</a>
																																			</div>
																																			<div class="col-sm-8 text-right">
																																				<!-- <a href="javacript:void(0);" onclick="printDiv('printableArea')" class="common-btn mt-3"><i class="fa fa-print"></i> Print</a> -->
																																				<a href="./evaluationViewIS"
																																					id="evaluateBtn"
																																					class="common-btn mt-3">Evaluate
																																					Application </a>
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
																																			<li><a href="#"><i
																																					class="fa fa-facebook-f"></i></a></li>
																																			<li><a href="#"><i
																																					class="fa fa-twitter"></i></a></li>
																																			<li><a href="#"><i
																																					class="fa fa-youtube"></i></a></li>
																																		</ul>
																																	</div>
																																</div>
																																<div class="col-sm-12">
																																	<div class="footer-menu">
																																		<ul>
																																			<li><a href="index.html"
																																				target="_blank">Home</a></li>
																																			<li><a
																																				href="http://udyogbandhu.com/topics.aspx?mid=About%20us"
																																				target="_blank">About Us</a></li>
																																			<li><a
																																				href="http://udyogbandhu.com/topics.aspx?mid=Disclaimer"
																																				target="_blank">Disclaimer</a></li>
																																			<li><a
																																				href="http://udyogbandhu.com/topics.aspx?mid=General%20Terms%20And%20Conditions"
																																				target="_blank">General Terms
																																					And Conditions</a></li>
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
																																				<img src="images/nic-logo.png"
																																					alt="NIC Logo">
																																			</div>
																																		</div>
																																		<div class="col-sm-6">
																																			<div class="copyright-text">
																																				<p>© 2020 - IT Solution powered
																																					by National Informatics Centre
																																					Uttar Pradesh State Unit</p>
																																				<p>Designed and Developed by
																																					National Informatics Centre ( NIC )</p>
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
																														<div class="modal fade"
																															id="SendtoConcernDepartment">
																															<div class="modal-dialog modal-lg">
																																<div class="modal-content">

																																	<!-- Modal Header -->
																																	<div class="modal-header">
																																		<h4 class="modal-title">Send to
																																			Concern Department</h4>
																																		<button type="button" class="close"
																																			data-dismiss="modal">&times;</button>
																																	</div>

																																	<!-- Modal body -->
																																	<div class="modal-body">
																																		<div class="row">
																																			<div class="col-sm-12"
																																				style="height: 400px; overflow-y: scroll;">
																																				<!-- gopal  -->
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input id="Agriculture Department"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="adappup2018@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Agriculture Department">Agriculture
																																						Department</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Board of Revenue, Uttar Pradesh"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="borlko@up.nic.in"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Board of Revenue, Uttar Pradesh">Board
																																						of Revenue, Uttar Pradesh</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Commercial Tax Department"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="Puneet.tripathi@gov.in"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Commercial Tax Department">Commercial
																																						Tax Department</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input id="Department of Excise"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="taskupexcise01@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Department of Excise">Department
																																						of Excise</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input id="Department of Labour"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="hq.sankhya@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Department of Labour">Department
																																						of Labour</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Department of Stamp and Registration"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="igruplko@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Department of Stamp and Registration">Department
																																						of Stamp and Registration</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Department of Weights and Measures : Legal Metrology"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="dclmhqlko@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Department of Weights and Measures : Legal Metrology">Department
																																						of Weights and Measures : Legal
																																						Metrology</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Directorate of Electrical Safety"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="electricalsafety.upgovt@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Directorate of Electrical Safety">Directorate
																																						of Electrical Safety</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Food Safety and Drug Administration (Drug)"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="upfdadrug@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Food Safety and Drug Administration (Drug)">Food
																																						Safety and Drug Administration
																																						(Drug)</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Forest and Wildlife Department"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="apccfit-up@nic.in"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Forest and Wildlife Department">Forest
																																						and Wildlife Department</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input id="Geology and Mining"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="dgmupexp@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Geology and Mining">Geology
																																						and Mining</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input id="Housing Department"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="awasbandhu@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Housing Department">Housing
																																						Department</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Information Technology Department"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="praveenuplc1@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Information Technology Department">Information
																																						Technology Department</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Infrastructure and Industrial Development Authority - GREATER NOIDA"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="osdsps@gnida.in"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Infrastructure and Industrial Development Authority - GREATER NOIDA">Infrastructure
																																						and Industrial Development
																																						Authority - GREATER NOIDA</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Infrastructure and Industrial Development Authority - NOIDA"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="industry@noidaauthorityonline.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Infrastructure and Industrial Development Authority - NOIDA">Infrastructure
																																						and Industrial Development
																																						Authority - NOIDA</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Infrastructure and Industrial Development Authority - UPSIDC"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="upsidc.etc@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Infrastructure and Industrial Development Authority - UPSIDC">Infrastructure
																																						and Industrial Development
																																						Authority - UPSIDC</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Infrastructure and Industrial Development Authority - YEIDA"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="skbhatiapcs@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Infrastructure and Industrial Development Authority - YEIDA">Infrastructure
																																						and Industrial Development
																																						Authority - YEIDA</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Medical Health Department"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="director.medical.care.up@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Medical Health Department">Medical
																																						Health Department</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input id="PICUP" name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="rajkr412@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="PICUP">PICUP</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input id="Pollution Control Board"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="pradeepsharma1764@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Pollution Control Board">Pollution
																																						Control Board</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input id="Public Works Department"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="seidslko@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Public Works Department">Public
																																						Works Department</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Registrar Firms Societies and Chits"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="drlkoregion@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Registrar Firms Societies and Chits">Registrar
																																						Firms Societies and Chits</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input id="Tourism Department"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="dg.upt1@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Tourism Department">Tourism
																																						Department</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Urban Development Department"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="diruplb@nic.in"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Urban Development Department">Urban
																																						Development Department</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Uttar Pradesh Fire Services"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="arvindk1961.ak@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Uttar Pradesh Fire Services">Uttar
																																						Pradesh Fire Services</label>
																																				</div>
																																				<div
																																					class="custom-control custom-checkbox">
																																					<input
																																						id="Uttar Pradesh Power Corporation Limited"
																																						name="deptName"
																																						class="custom-control-input"
																																						type="checkbox"
																																						value="seit.uppcl@gmail.com"><input
																																						type="hidden" name="_deptName"
																																						value="on"> <label
																																						class="custom-control-label"
																																						for="Uttar Pradesh Power Corporation Limited">Uttar
																																						Pradesh Power Corporation Limited</label>
																																				</div>
																																				<!--  gopal -->
																																			</div>
																																		</div>
																																	</div>
																																	<div class="modal-footer">
																																		<a href="#QueryReply"
																																			class="common-btn mt-0"
																																			data-toggle="modal"
																																			data-dismiss="modal">Submit</a>
																																	</div>
																																</div>
																															</div>
																														</div>
																													</div>


																													<!-- Optional JavaScript -->
																													<!-- jQuery first, then Popper.js, then Bootstrap JS -->
																													<script
																														src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
																													<script
																														src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
																													<script
																														src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
																													<script src="js/custom.js"></script>
																													<script>
																														$(
																																document)
																																.ready(
																																		function() {
																																			$(
																																					"[data-toggle=offcanvas]")
																																					.click(
																																							function() {
																																								$(
																																										".row-offcanvas")
																																										.toggleClass(
																																												"active");
																																							});
																																		});
																													</script>
																													<script>
																														//Get the button
																														var mybutton = document
																																.getElementById("GoToTopBtn");

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
</body>

</html>