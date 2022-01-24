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
<title>Evaluation View Department</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
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
						<li class="nav-item"><a class="nav-link" href="./userLogout"><i
								class="fa fa-power-off"></i> Logout</a></li>

						<li class="nav-item"><a class="nav-link active" href="#"><i
								class="fa fa-user"></i>${userName} </a></li>

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
						<li class="nav-item"><a class="nav-link" href=""><i
								class="fa fa-eye"></i> View Circulated Draft LOC</a></li>
					</ul>
				</div>
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-12">
								<a
									href="./viewDeptApplicationDetails?applicantId=<%=session.getAttribute("appId")%>"
									class="common-default-btn mt-3">Back</a>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-12">
								<div class="mt-4">
									<div class="without-wizard-steps">
										<h4
											class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">
										Incentive Sought Comments <small>(If Any)</small>
										</h4>
									</div>
								</div>
								<hr class="mt-2 mb-5">



								<form:form modelAttribute="incentiveDeatilsData"
									name="incentiveDeatilsform" method="post">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-stripped table-bordered">
													<thead>
														<tr>
															<th>Sr. No.</th>
															<th style="width: 15%;">Details of Incentives Sought
															</th>
															<th style="width: 10%;">Amount of Reliefs <small>(Rs.
																	in crores)</small></th>
															<th>Incentive as per Rules</th>
															<!-- <th style="width: 13%;">Justification (if any) given
																by the Company</th>
															<th style="width: 15%;">Remarks</th> -->
														</tr>
													</thead>


													<tbody>
														<tr>
															<td>1</td>
															<td><strong>Amount of SGST claimed for
																	reimbursement</strong></td>
															<td><form:input path="ISF_Claim_Reim" type="text"
																	class="ISF_Reim_SGST form-control"
																	name="ISF_Claim_Reim" id="ISF_Claim_Reim"
																	maxlength="12" readonly="true"></form:input> <span
																class="text-danger d-inline-block mt-2"></span></td>

															<td><a href="javacript:void();">As per Table - 3</a>
																of Rules related to IIEPP-2017 (The Rules), there is a
																provision for reimbursement of 70% of deposited GST (Net
																SGST) for 10 years.</td>
															<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td> -->
														</tr>
														<tr>
															<td colspan="4"><form:textarea class="form-control"
																	path="isfSgstComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>

														<tr>
															<td>1.1</td>
															<td>Additional 10% GST where 25% minimum SC/ST
																workers employed subject to minimum of 400 total workers
																in industrial undertakings located in Paschimanchal and
																minimum of 200 total workers in B-P-M</strong>

															</td>
															<td><form:input path="ISF_Reim_SCST" type="text"
																	id="Add_SCST_Textbox"
																	class="ISF_Reim_SGST form-control" name="ISF_Reim_SCST"
																	maxlength="12" readonly="true"></form:input></td>
															<td><a href="javacript:void();">As per Table 3</a>
																of The Rules, there is a provision for 75% Stamp Duty
																Exemption in Paschimanchal Region. <br>Further, as
																per G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there
																is a provision for reimbursement equivalent to the paid
																Stamp Duty, for which company will have to apply before
																concerned GM, DIC.</td>
															<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td> -->
														</tr>


														<tr>
															<td colspan="4"><form:textarea class="form-control"
																	path="isfScstComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>




														<tr>
															<td>1.2</td>
															<td>Additional 10% GST where 40% minimum female
																workers employed subject to minimum of 400 total workers
																in industrial undertakings located in Paschimanchal and
																minimum of 200 total workers in B-P-M</td>
															<td><form:input path="ISF_Reim_FW" type="text"
																	id="Add_FW_Textbox" maxlength="12"
																	class="ISF_Reim_SGST form-control" name="ISF_Reim_FW"
																	readonly="true"></form:input></td>
															<td><a href="javacript:void();">As per para 3.3</a>
																and Table 3 of The Rules, there is a provision for
																incentive of reimbursement of EPF to the extent of 50%
																of employer’s contribution to all such new Industrial
																undertakings providing direct employment to 100 or more
																unskilled workers, after three years from the date of
																commercial production for a period of 5 years.</td>
															<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td> -->
														</tr>


														<tr>
															<td colspan="4"><form:textarea class="form-control"
																	path="isffwComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>

														<tr>
															<td>1.3</td>
															<td>Additional 10% GST where 25% minimum BPL workers
																employed subject to minimum of 400 total workers in
																industrial undertakings located in Paschimanchal and
																minimum of 200 total workers in B-P-M</td>
															<td><form:input path="ISF_Reim_BPLW" type="text"
																	class="ISF_Reim_SGST form-control" maxlength="12"
																	name="ISF_Reim_BPLW" id="Add_BPLW_Textbox"
																	readonly="true"></form:input></td>
															<td><a href="javacript:void();">As per para
																	3.5.7</a> of the Rules, there is a following provision: The
																industries which are disallowed for input tax credit
																under the GST regime, will be eligible for reimbursement
																of that amount of GST paid on purchase of plant and
																machinery, building material and other capital goods
																during construction and commissioning period and raw
																materials and other inputs in respect of which input tax
																credit has not been allowed. While calculating the
																overall eligible capital investment such amount will be
																added to the fixed capital investment.</td>
															<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td> -->
														</tr>



														<tr>
															<td colspan="4"><form:textarea class="form-control"
																	path="isfBplComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>

														<tr>
															<td>2</td>
															<td>Amount of Stamp Duty Exemption<br> <strong>Stamp
																	& Registration</strong>
															</td>
															<td><form:input path="ISF_Stamp_Duty_EX" type="text"
																	class="ISF_Reim_SGST form-control" maxlength="12"
																	name="ISF_Stamp_Duty_EXe" id="ISF_Stamp_Duty_EXe"
																	readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per Table 3</a> of The Rules, there
																is a provision for dynamic percentage Stamp Duty
																Exemption in Dynamic Region. Further, as per G.O. No.
																1515/77-6-18-5(M)/17, dated 1.5.2018, there is a
																provision for reimbursement equivalent to the paid Stamp
																Duty, for which company will have to apply before
																concerned GM, DIC.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>
														<tr>
															<td colspan="4"><form:textarea class="form-control"
																	path="isfStampComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>
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
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per Table 3</a> of The Rules, there
																is a provision for dynamic percentage Stamp Duty
																Exemption in Dynamic Region. Further, as per G.O. No.
																1515/77-6-18-5(M)/17, dated 1.5.2018, there is a
																provision for reimbursement equivalent to the paid Stamp
																Duty, for which company will have to apply before
																concerned GM, DIC.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>
														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfStampremComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>
														<tr class="ISF_Additonal_Stamp_Duty_EX-row">
															<td>2.1</td>
															<td>Additional Stamp Duty exemption @20% upto
																maximum of 100% in case of industrial undertakings
																having 75% equity owned by Divyang/SC/ ST/Females
																Promoters</td>
															<td><form:input path="ISF_Additonal_Stamp_Duty_EX"
																	type="text"
																	class="ISF_Additonal_Stamp_Duty_EX form-control"
																	maxlength="12" name="ISF_Additonal_Stamp_Duty_EX"
																	id="ISF_Additonal_Stamp_Duty_EX" readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per Table 3</a> of The Rules, there
																is a provision for dynamic percentage Stamp Duty
																Exemption in Dynamic Region. Further, as per G.O. No.
																1515/77-6-18-5(M)/17, dated 1.5.2018, there is a
																provision for reimbursement equivalent to the paid Stamp
																Duty, for which company will have to apply before
																concerned GM, DIC.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>
														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfStampscstComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>
														<tr class="ISF_Epf_Reim_UW-row">
															<td>3</td>
															<td>EPF Reimbursement (100 or more unskilled
																workers) <br> <strong>Labour/Industrial
																	Development</strong>
															</td>
															<td><form:input path="ISF_Epf_Reim_UW" type="text"
																	class="ISF_Epf_Reim_UW form-control" maxlength="12"
																	name="ISF_Amt_Stamp_Duty_Reim"
																	id="Add_ISF_Amt_Stamp_Duty_Reim" readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per para 3.3</a> and Table 3 of The
																Rules, there is a provision for incentive of
																reimbursement of EPF to the extent of 50% of
																employer’s contribution to all such new Industrial
																undertakings providing direct employment to 100 or more
																unskilled workers, after three years from the date of
																commercial production for a period of 5 years.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>
														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfepfComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>
														<tr class="ISF_Add_Epf_Reim_SkUkW-row">
															<td>3.1</td>
															<td>Additional 10% EPF Reimbursement (200 direct
																skilled and unskilled workers)</td>
															<td><form:input path="ISF_Add_Epf_Reim_SkUkW"
																	type="text" class="ISF_Add_Epf_Reim_SkUkW form-control"
																	maxlength="12" name="ISF_Add_Epf_Reim_SkUkW"
																	id="ISF_Add_Epf_Reim_SkUkW" readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per para 3.3</a> and Table 3 of The
																Rules, there is a provision for incentive of
																reimbursement of EPF to the extent of 50% of
																employer’s contribution to all such new Industrial
																undertakings providing direct employment to 100 or more
																unskilled workers, after three years from the date of
																commercial production for a period of 5 years.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>
														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfepfaddComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>
														<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
															<td>3.2</td>
															<td>Additional 10% EPF Reimbursement upto maximum of
																70% in case of industrial undertakings having 75% equity
																owned by Divyang/SC/ST/Females Promoters</td>
															<td><form:input path="ISF_Add_Epf_Reim_DIVSCSTF"
																	type="text"
																	class="ISF_Add_Epf_Reim_DIVSCSTF form-control"
																	maxlength="12" name="ISF_Add_Epf_Reim_DIVSCSTF"
																	id="ISF_Add_Epf_Reim_DIVSCSTF" readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per para 3.3</a> and Table 3 of The
																Rules, there is a provision for incentive of
																reimbursement of EPF to the extent of 50% of
																employer’s contribution to all such new Industrial
																undertakings providing direct employment to 100 or more
																unskilled workers, after three years from the date of
																commercial production for a period of 5 years.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>
														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfepfscComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>
														<tr class="ISF_Cis-row">
															<td>4</td>
															<td>Capital Interest Subsidy <br> <strong>Industrial
																	Development</strong></td>
															<td><form:input path="ISF_Cis" type="text"
																	class="ISF_Cis form-control" maxlength="12"
																	name="ISF_Cis" id="ISF_Cis" readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per para 3.5.1</a> of The Rules,
																there is a provision for capital interest subsidy @ 5%
																p.a. or actual interest paid whichever is less annually
																for 5 years in the form of reimbursement of interest
																paid on outstanding loan taken for procurement of plant
																& machinery, subject to an annual ceiling of Rs. 50
																lacs.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>

														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfcapisComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>

														<tr class="ISF_ACI_Subsidy_Indus-row">
															<td>4.1</td>
															<td>Additional Capital Interest Subsidy@2.5% upto
																maximum of 7.5% in case of industrial undertakings
																having 75% equity owned by Divyang/SC/ST/Females
																Promoters</td>
															<td><form:input path="ISF_ACI_Subsidy_Indus"
																	type="text" class="ISF_ACI_Subsidy_Indus form-control"
																	maxlength="12" name="ISF_ACI_Subsidy_Indus"
																	id="ISF_ACI_Subsidy_Indus" readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per para 3.5.1</a> of The Rules,
																there is a provision for capital interest subsidy @ 5%
																p.a. or actual interest paid whichever is less annually
																for 5 years in the form of reimbursement of interest
																paid on outstanding loan taken for procurement of plant
																& machinery, subject to an annual ceiling of Rs. 50
																lacs.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>

														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfcapisaComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>

														<tr class="ISF_Infra_Int_Subsidy-row">
															<td>5</td>
															<td>Infrastructure Interest Subsidy <br> <strong>Industrial
																	Development</strong></td>
															<td><form:input path="ISF_Infra_Int_Subsidy"
																	type="text" class="ISF_Infra_Int_Subsidy form-control"
																	maxlength="12" name="ISF_Infra_Int_Subsidy"
																	id="ISF_Infra_Int_Subsidy" readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per para 3.5.2</a> of the Rules,
																there is a provision for incentive of infrastructure
																interest subsidy @ 5% p.a. or actual interest paid
																whichever is less annually for 5 years in the form of
																reimbursement of interest paid on outstanding loan taken
																for development of infrastructural amenities (as defined
																in para 2.17) subject to an overall ceiling of Rs. 1
																Crore.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>

														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfinfComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>

														<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
															<td>5.1</td>
															<td>Additional Infrastructure Interest Subsidy @2.5%
																upto maximum of 7.5% in case of industrial undertakings
																having 75% equity owned by Divyang/SC/ST/Females
																Promoters</td>
															<td><form:input path="ISF_AII_Subsidy_DIVSCSTF"
																	type="text"
																	class="ISF_AII_Subsidy_DIVSCSTF form-control"
																	maxlength="12" name="ISF_AII_Subsidy_DIVSCSTF"
																	id="ISF_AII_Subsidy_DIVSCSTF" readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per para 3.5.2</a> of the Rules,
																there is a provision for incentive of infrastructure
																interest subsidy @ 5% p.a. or actual interest paid
																whichever is less annually for 5 years in the form of
																reimbursement of interest paid on outstanding loan taken
																for development of infrastructural amenities (as defined
																in para 2.17) subject to an overall ceiling of Rs. 1
																Crore.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>

														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfinfaComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>

														<tr class="ISF_Loan_Subsidy-row">
															<td>6</td>
															<td>Interest Subsidy on loans for industrial
																research, quality improvement, etc. <br> <strong>Industrial
																	Development</strong>
															</td>
															<td><form:input path="ISF_Loan_Subsidy" type="text"
																	class="ISF_Loan_Subsidy form-control" maxlength="12"
																	name="ISF_Loan_Subsidy" id="ISF_Loan_Subsidy"
																	readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per para 3.5.3</a> of The Rules,
																there is a provision for Interest subsidy on loans for
																industrial research @ 5% or actual interest paid
																whichever is less annually for 5 years in the form of
																reimbursement of interest paid on outstanding loan taken
																for industrial research, quality improvement and
																development of products by incurring expenditure on
																procurement of plant, machinery & equipment for setting
																up testing labs, quality certification labs and tool
																rooms, subject to an overall ceiling of Rs. 1 Crore</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>

														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfloanComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>

														<tr class="ISF_Tax_Credit_Reim-row">
															<td>7</td>
															<td>Reimbursement of Disallowed Input Tax Credit on
																plant, building materials, and other capital goods. <br>
																<strong>Industrial Development</strong></br>
															</td>
															<td><form:input path="ISF_Tax_Credit_Reim"
																	type="text" class="ISF_Tax_Credit_Reim form-control"
																	maxlength="12" name="ISF_Tax_Credit_Reim"
																	id="ISF_Tax_Credit_Reim" readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per para 3.5.7</a> of the Rules, it
																is provided - The industries which are disallowed for
																input tax credit under the GST regime, will be eligible
																for reimbursement of that amount of GST paid on purchase
																of plant and machinery, building material and other
																capital goods during construction and commissioning
																period and raw materials and other inputs in respect of
																which input tax credit has not been allowed. While
																calculating the overall eligible capital investment such
																amount will be added to the fixed capital investment.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>

														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfdisComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>
														<tr class="ISF_EX_E_Duty-row">
															<td>8</td>
															<td>Exemption from Electricity Duty from captive
																power for self-use <br> <strong>Energy/UPPCL/
																	Electricity Safety</strong></br>
															</td>
															<td><form:input path="ISF_EX_E_Duty" type="text"
																	class="ISF_EX_E_Duty form-control" maxlength="12"
																	name="ISF_EX_E_Duty" id="ISF_EX_E_Duty" readonly="true"></form:input></td>
															<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																target="_blank">As per para 3.5.5</a> of The Rules,
																there is a provision for Exemption from Electricity Duty
																for 10 years to all new industrial undertakings
																producing electricity from captive power plants for
																self-use.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>

														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfelepodownComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>
														<!-- //previous -->
														<tr>
															<td>9</td>
															<td>Exemption from Electricity Duty on power drawn
																from power companies for 20 years <br> <strong>Energy/UPPCL/
																	Electricity Safety</strong>

															</td>
															<td><form:input path="ISF_EX_E_Duty_PC" type="text"
																	class="Other_Incentives form-control"
																	id="ISF_EX_E_Duty_PC" name="ISF_EX_E_Duty_PC"
																	maxlength="12" readonly="true"></form:input></td>
															<td><a href="javacript:void();">As per para
																	3.5.5</a> of the Rules, there is a provision for Exemption
																from Electricity Duty for 10 years to all new industrial
																undertakings producing electricity from captive power
																plants for self-use.</td>
														</tr>


														<tr>
															<td colspan="4"><form:textarea class="form-control"
																	path="isfElecdutyComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>



														<tr>
															<td>10</td>
															<td>Exemption from Mandi Fee for minimum 15 years
																(100% for 20 years) <br> <strong>Agriculture
																	Marketing & Agriculture Foreign Trade/MandiParishad</strong>

															</td>
															<td><form:input path="ISF_EX_Mandee_Fee" type="text"
																	id="ISF_EX_Mandee_Fee"
																	class="Other_Incentives form-control" readonly="true"
																	name="ISF_EX_Mandee_Fee" maxlength="12"></form:input></td>
															<td><a href="javacript:void();">As per para
																	3.5.6</a> of The Rules, There is provision for exemption
																from Mandi Fee for all new food-processing undertakings
																on purchase of raw material for 5 years.</td>
															<!-- <td><textarea class="form-control" disabled>No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td> -->
														</tr>



														<tr>
															<td colspan="4"><form:textarea class="form-control"
																	path="isfMandiComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>
														<tr class="ISF_Indus_Payroll_Asst-row">
															<td>11</td>
															<td>Industrial units providing employment to
																differently abled workers will be provided payroll
																assistance of Rs. 500 per month for each such worker.</td>
															<td><form:input path="ISF_Indus_Payroll_Asst"
																	type="text" class="ISF_Indus_Payroll_Asst form-control"
																	maxlength="12" name="ISF_Amt_Stamp_Duty_Reim"
																	id="Add_ISF_Amt_Stamp_Duty_Reim" readonly="true"></form:input></td>
															<td>No such provision in The Rules.</td>
															<!-- <td><textarea class="form-control"></textarea></td> -->
														</tr>

														<tr class="isfStampremComment">
															<td colspan="4"><form:textarea class="form-control"
																	path="isfdifferabilComment" rows="5"
																	placeholder="Enter Comment"></form:textarea></td>
														</tr>


														<tr>
															<td colspan="2"></td>
															<td><input id="ISF_total" type="text"
																value="Total: ${total}"
																class="Other_Incentives form-control" disabled></input></td>
															<td colspan="4"></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>

									<c:if test="${not empty incentiveDeatilsData.othAddRequest1}">

										<div class="row">
											<div class="col-sm-12">
												<div class="form-group">
													<label>Additional Request</label>
													<form:input path="othAddRequest1" type="text"
														class="form-control" placeholder="" maxlength="250"
														name="othAddRequest1"></form:input>
												</div>
											</div>
										</div>
									</c:if>

									<hr class="mt-2">
									<div class="row">
										<div class="col-sm-5">
											<a
												href="./viewDeptApplicationDetails?applicantId=<%=session.getAttribute("appId")%>"
												class="common-default-btn mt-3">Back</a>
										</div>
										<div class="col-sm-7 text-right">
											<!-- <button class="common-btn mt-3" formaction="addIsfComments">Save</button>
											<a href="javacript:void(0);" class="common-btn mt-3">Submit</a> -->
											<button class="common-btn mt-3" formaction="addIsfComments">Submit</button>
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
		<!-- The Modal -->
		<div class="modal fade" id="RaiseQuery">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Raise Query</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>Name of Issue</label> <input type="text"
										class="form-control" name="">
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label>Details of Missing Documents <small>(If
											any)</small></label>
									<textarea class="form-control"></textarea>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label>Clarification Sought</label>
									<textarea class="form-control"></textarea>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label>Upload Related Document</label>
									<div class="custom-file">
										<input type="file" class="custom-file-input"
											id="UploadDocumentForQuery"> <label
											class="custom-file-label" for="UploadDocumentForQuery">Choose
											file</label>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#QueryReply" class="common-btn mt-0" data-toggle="modal"
							data-dismiss="modal">Submit Query</a>
					</div>

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



	</div>


	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
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
</body>

</html>