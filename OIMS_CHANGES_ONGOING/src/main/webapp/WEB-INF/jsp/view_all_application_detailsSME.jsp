<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.webapp.ims.model.ProposedEmploymentDetails"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>




<!doctype html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>View All Applicant Details</title>
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
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.debug.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"
	integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.min.js"></script>
<link rel="stylesheet" href="css/style.css">
<script src="js/custom.js"></script>


<script>
	$(document)
			.ready(
					function() {
						var cat = '${categorytype}';
						if (cat != null && cat != '') {
							if (cat === "Small") {

								$(
										'input:checkbox[name="Small_Checkbox"][value="${categorytype}"]')
										.prop('checked', true);
							}

							else if (cat === "Medium") {

								$(
										'input:checkbox[name="Medium_Checkbox"][value="${categorytype}"]')
										.prop('checked', true);
							}

							else if (cat === "Large") {

								$(
										'input:checkbox[name="Large_Checkbox"][value="${categorytype}"]')
										.prop('checked', true);
							}

						}
					});
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
		//	var printArea = $this.parents('#printform').html();
		var printArea = document.getElementById('printform').innerHTML;

		//$('body').html(printArea);

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
							pdf.addHTML(document.getElementById("content2"),
									options, function() {
										pdf.save("AFIFFORM.pdf");
									});
						});
			});
</script>


<script type="text/javascript">
	window.onload = function() {

		/* if ('${gender}' === 'Male') {
			$('input:radio[name=gender][value=Male]').attr('checked', true);
		} else if ('${gender}' === 'Female') {
			$('input:radio[name=gender][value=Female]').attr('checked', true);
		} else {
			$('input:radio[name=gender][value=Transgender]').attr('checked',
					true);
		} */

		if ('${regiOrLicense}' === 'EncloseUAM') {
			$('input:radio[name=regiOrLicense][value=EncloseUAM]').attr(
					'checked', true);
		} else {
			$('input:radio[name=regiOrLicense][value=IEMcopy]').attr('checked',
					true);
		}

		if ('${newProject}' === 'NewProject') {
			$('input:checkbox[name="newProject"][value="NewProject"]').prop(
					'checked', true);
			document.getElementById('hideById').style.display = 'none';
			document.getElementById('hideByRowId1').style.display = 'none';
			document.getElementById('hideByRowId2').style.display = 'none';
			document.getElementById('hideByRowId3').style.display = 'block';
			document.getElementById('hideByRowId4').style.display = 'block';

		}
		if ('${expansion}' === 'Expansion') {
			$('input:checkbox[name="expansion"][value="Expansion"]').prop(
					'checked', true);
			document.getElementById('hideById').style.display = 'block';
			var element = document.getElementById("hideByRowId1");
			element.classList.remove("hide");
			var element = document.getElementById("hideByRowId2");
			element.classList.remove("hide");
			var element = document.getElementById("hideByRowId3");
			element.classList.remove("hide");
			document.getElementById('hideByRowId4').style.display = 'block';

		}

		if ('${diversification}' === 'Diversification') {
			$('input:checkbox[name="diversification"][value="Diversification"]')
					.prop('checked', true);
			document.getElementById('hideById').style.display = 'block';
			var element = document.getElementById("hideByRowId1");
			element.classList.remove("hide");
			var element = document.getElementById("hideByRowId2");
			element.classList.remove("hide");
			var element = document.getElementById("hideByRowId3");
			element.classList.remove("hide");
			document.getElementById('hideByRowId4').style.display = 'block';

		}

		if ('${ISF_Reim_SCST}' != null && '${ISF_Reim_SCST}' != '') {
			$(
					'input:checkbox[name="ISF_SGST_Claim_Reim"][value="ISF_Reim_SCST"]')
					.prop('checked', true);

		}

		if ('${ISF_Reim_FW}' != null && '${ISF_Reim_FW}' != '') {
			$('input:checkbox[name="ISF_SGST_Claim_Reim"][value="ISF_Reim_FW"]')
					.prop('checked', true);

		}
		if ('${ISF_Reim_BPLW}' != null && '${ISF_Reim_BPLW}' != '') {
			$(
					'input:checkbox[name="ISF_SGST_Claim_Reim"][value="ISF_Reim_SCST"]')
					.prop('checked', true);

		}

		if ('${ISF_Additonal_Stamp_Duty_EX}' != null
				&& '${ISF_Additonal_Stamp_Duty_EX}' != '') {
			$(
					'input:checkbox[name="ISF_Stamp_Duty"][value="ISF_Additonal_Stamp_Duty_EX"]')
					.prop('checked', true);

		}

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

		if ('${ISF_ACI_Subsidy_Indus}' != null
				&& '${ISF_ACI_Subsidy_Indus}' != '') {
			$(
					'input:checkbox[name="Add_CIS_checkbox"][value="ISF_ACI_Subsidy_Indus"]')
					.prop('checked', true);

		}
		if ('${ISF_AII_Subsidy_DIVSCSTF}' != null
				&& '${ISF_AII_Subsidy_DIVSCSTF}' != '') {
			$(
					'input:checkbox[name="Add_IIS_checkbox"][value="ISF_AII_Subsidy_DIVSCSTF"]')
					.prop('checked', true);

		}
		if ('${ISF_Cstm_Inc_Status}' === 'Yes') {
			$('input:radio[name="ISF_Cstm_Inc_Status"][value="Yes"]').prop(
					'checked', true);

		} else {
			$('input:radio[name="ISF_Cstm_Inc_Status"][value="No"]').prop(
					'checked', true);
		}

	}
</script>

<script>
	/* $(document)
			.ready(
					function() {
						document.getElementById('choosefile').innerHTML = '${viewSignatoryDetail.fileName}';
					}); */
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
						<li class="nav-item animate__animated animate__bounce"><a
							class="nav-link " href="./dashboard"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluate"><i class="fa fa-eye"></i> View and
								Evaluate Applications</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./viewAndEvaluateSME"><i class="fa fa-eye"></i> View
								and Forward SME applications</a></li>
						<li class="nav-item"><a class="nav-link" href="./queryRaised"><i
								class="fa fa-question-circle"></i> Query </a></li>
						<!-- <li class="nav-item"><a class="nav-link"
							href="./viewQueryResponse"><i class="fa fa-question-circle"></i>Query
								Response By Enterpreneur</a></li> -->
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
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<h4
						class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Industrial
						Investment & Employment Promotion Policy 2017</h4>
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-12">
								<div class="mt-4">
									<div class="without-wizard-steps">
										<div class="row">
											<div class="col-sm-8">
												<a href="./applicationForLocSME"
													class="common-default-btn mt-0">Back</a> <a
													href="javascript:void(0);" id="printMethod"
													class="common-btn mt-0"><i class="fa fa-print"></i>
													Print</a>
											</div>
											<div class="col-sm-4 text-right">
												<div class="form-group">
													<select class="form-control" id="SelectParameter">
														<option value="viewAllApplicationDetails">View as
															per entrepreneur</option>
														<option value="Annexure-I">View as per policy LOC
															form Annexure-I</option>
														<!-- <option value="Annexure-III">View as per policy
															LOC form Annexure-III</option> -->
													</select>
												</div>
											</div>
										</div>
										<hr>
										<form:form modelAttribute="downloadSupport" class="mt-4"
											name="downloadSupport" method="post">
											<div class="row mt-4">
												<div class="col-sm-12 text-center">
													<button type="button"
														class="btn btn-outline-success btn-sm mb-3" id="formId">
														<i class="fa fa-download"></i> Download Only Application
														Form
													</button>
													<button type="submit" formaction="downloadSupport"
														class="btn btn-outline-info btn-sm mb-3">
														<i class="fa fa-download"></i> Download Application Form
														with Suporting Docs
													</button>
													<button type="submit" formaction="downloadSupport"
														class="btn btn-outline-secondary btn-sm mb-3">
														<i class="fa fa-download"></i> Download Only Supporting
														Docs
													</button>
												</div>
											</div>
										</form:form>
										<!-- <div class="row mt-4">
											<div class="col-sm-12 text-center">
												<button type="button"
													class="btn btn-outline-success btn-sm mb-3">
													<i class="fa fa-download"></i> Download Only Application
													Form
												</button>
												<button type="button"
													class="btn btn-outline-info btn-sm mb-3">
													<i class="fa fa-download"></i> Download Application Form
													with Suporting Docs
												</button>
												<button type="button"
													class="btn btn-outline-secondary btn-sm mb-3">
													<i class="fa fa-download"></i> Download Only Supporting
													Docs
												</button>
											</div>
										</div> -->



										<!-- Start view All Application Details -->

										<div class="parameter-properties"
											id="viewAllApplicationDetails">
											<hr class="mt-2">
											<div id="content2">
												<c:import url="/WEB-INF/jsp/preview_evaluation.jsp">
												</c:import>
											</div>
											<!-- <h4
												class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Authorised
												Signatory Details</h4> -->
											<%--<form:form class="isf-form" method="POST"
												modelAttribute="viewSignatoryDetail"
												enctype="multipart/form-data">
												<div class="tab-content py-2">
													<div class="tab-pane active">
														<div class="row">
															<div class="col-sm-12 mt-4">
																<h3 class="common-heading">Authorised Signatory
																	Details</h3>
															</div>
														</div>
														<div class="row">
															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="appFirstName">First Name <span>*</span>
																	</form:label>
																	<form:input path="appFirstName" class="form-control"
																		type="text" id="appFirstName" readonly="true"></form:input>
																	<span id="appfirstname" class="text-danger"></span>
																	<form:errors path="appFirstName" cssClass="error" />
																</div>
															</div>

															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="appMiddleName">Middle Name</form:label>
																	<form:input path="appMiddleName" class="form-control"
																		readonly="true" id="appMiddleName"></form:input>
																	<form:errors path="appMiddleName" cssClass="error" />
																</div>
															</div>

															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="appLastName">Last Name<span>*</span>
																	</form:label>
																	<form:input path="appLastName" class="form-control"
																		readonly="true" id="appLastName"></form:input>
																	<span id="applastname" class="text-danger"></span>
																	<form:errors path="appLastName" cssClass="error" />
																</div>
															</div>
														</div>

														<div class="row">



															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="appEmailId">Email ID <span>*</span>
																	</form:label>
																	<form:input path="appEmailId" class="form-control"
																		type="email" readonly="true" id="appEmailId"></form:input>
																	<span id="appemailid" class="text-danger"></span>
																	<form:errors path="appEmailId" cssClass="error" />
																</div>
															</div>

															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="appMobileNo">Mobile No:<small>(+91)</small>
																		<span>*</span>
																	</form:label>
																	<form:input path="appMobileNo" class="form-control"
																		readonly="true" maxlength="10" id="appMobileNo"></form:input>
																	<span id="appmobileno" class="text-danger"></span>
																	<form:errors path="appMobileNo" cssClass="error" />
																</div>
															</div>
															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="appPhoneNo">Phone No.(LandLine)</form:label>
																	<form:input path="appPhoneNo" class="form-control"
																		readonly="true" maxlength="11" minlength="11"
																		id="appPhoneNo"></form:input>
																	<span id="appphoneno" class="text-danger"></span>
																	<form:errors path="appPhoneNo" cssClass="error" />
																</div>
															</div>
														</div>

														<div class="row">



															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="appDesignation">Designation <span>*</span>
																	</form:label>
																	<form:input path="appDesignation" class="form-control"
																		readonly="true" maxlength="60" id="appDesignation"></form:input>
																	<span id="appdesignation" class="text-danger"></span>
																	<form:errors path="appDesignation" cssClass="error" />
																</div>
															</div>

															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<div class="gender-label">
																		<form:label path="gender">Gender <span>*</span>
																		</form:label>
																	</div>
																	<div
																		class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="gender"
																			class="custom-control-input" id="MaleOpt"
																			name="gender" value="Male" disabled="true" />
																		<label class="custom-control-label" for="MaleOpt">Male</label>
																	</div>
																	<div
																		class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="gender"
																			class="custom-control-input" id="FemaleOpt"
																			name="gender" value="Female" disabled="true" />
																		<label class="custom-control-label" for="FemaleOpt">Female</label>

																	</div>
																	<div
																		class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="gender"
																			class="custom-control-input" id="Transgender"
																			name="gender" value="Transgender" disabled="true" />
																		<label class="custom-control-label" for="Transgender">Transgender
																		</label>

																	</div>
																	<span id="apptitle" class="text-danger"></span>
																	<form:errors path="gender" cssClass="error" />
																</div>
															</div>

														</div>

														<div class="row">
															<div class="col-sm-12 mt-4">
																<h3 class="common-heading">Aadhaar & Pan Details</h3>
															</div>
														</div>


														<div class="row">

															<div class="col-md-6">
																<div class="form-group">
																	<form:label id="GFG" path="appAadharNo">Aadhaar Number  <span>*</span>
																	</form:label>
																	<form:input path="appAadharNo" class="form-control"
																		readonly="true" maxlength="12" id="appAadharNo"></form:input>
																	<span id="appaadharno" class="text-danger"></span>

																	<form:errors path="appAadharNo" cssClass="error" />
																</div>
															</div>





															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="appPancardNo">PAN Number <span>*</span>
																	</form:label>
																	<form:input path="appPancardNo" class="form-control"
																		maxlength="10" readonly="true" id="appPancardNo"></form:input>
																	<span id="apppancardno" class="text-danger"></span>
																	<form:errors path="appPancardNo" cssClass="error" />
																</div>
															</div>
														</div>


													</div>
													<div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">Address Details</h3>
														</div>
													</div>

													<div class="row">
														<div class="col-sm-6">
															<div class="form-group">
																<form:label path="appAddressLine1">Address Line - 1 <span>*</span>
																</form:label>
																<form:input path="appAddressLine1" class="form-control"
																	maxlength="200" readonly="true" id="appAddressLine1"></form:input>
																<span id="appaddressline1" class="text-danger"></span>
																<form:errors path="appAddressLine1" cssClass="error" />
															</div>
														</div>

														<div class="col-sm-6">
															<div class="form-group">
																<form:label path="appAddressLine2">Address Line - 2</form:label>
																<form:input path="appAddressLine2" class="form-control"
																	readonly="true" maxlength="200" id="appAddressLine2"></form:input>
																<form:errors path="appAddressLine2" cssClass="error" />
															</div>
														</div>
													</div>

													<div class="row">

														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<form:label path="appCountry"> Country <span>*</span>
																</form:label>
																<form:input path="appCountry" class="form-control"
																	maxlength="5" value="India" id="appCountry"
																	readonly="true"></form:input>
																<span id="appcountry" class="text-danger"></span>
																<form:errors path="appCountry" cssClass="error" />
															</div>
														</div>
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<form:label path="appState">State/UT <span>*</span>
																</form:label>
																<form:input path="appState" class="form-control"
																	maxlength="20" id="appState" readonly="true"></form:input>
																<span id="appstate" class="text-danger"></span>
																<form:errors path="appState" cssClass="error" />
															</div>
														</div>

														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<form:label path="appDistrict">District <span>*</span>
																</form:label>
																<form:input path="appDistrict" class="form-control"
																	maxlength="50" id="appDistrict" readonly="true"></form:input>
																<span id="appdistrict" class="text-danger"></span>
																<form:errors path="appDistrict" cssClass="error" />
															</div>
														</div>
													</div>




													<div class="row">
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<form:label path="appPinCode">Pin Code <span>*</span>
																</form:label>
																<form:input type="text" path="appPinCode"
																	readonly="true" maxlength="6" class="form-control"
																	id="appPinCode" name="appPinCode" />
																<span id="apppincode" class="text-danger"></span>
																<form:errors path="appPinCode" cssClass="error" />
															</div>
														</div>
													</div>

													<div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">Authorised Signatory
																Photo</h3>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-6">
															<div class="form-group">
																<form:label path="fileName">Upload Photo</form:label>
																<div class="custom-file">
																	<form:input type="file" name="fileName" id="photofile"
																		path="fileName" disabled="true"
																		class="custom-file-input user-file"></form:input>
																	<label class="custom-file-label file" id="choosefile">Choose
																		file</label> <span id="PhotoFile" class="text-danger"></span>
																</div>
															</div>
														</div>

														<div class="col-sm-6 text-center">
															<!-- <img src="images/user-icon.png" id="preview-photo"
																class="user-upload-preview" align="user-icon"> -->
															<img
																src="data:image/png;base64,${viewSignatoryDetail.base64imageFile}" />
														</div>
													</div>


													<!-- <div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">Digital Signature</h3>
														</div>
														<div class="col-sm-12">
															<img id="preview-Sign" class="sign-upload-preview"
																align="sign-sample"> <a href="#"> <i class="fa fa-check"></i>
															</a>
														</div>
													</div> -->
													<hr>
												</div>
											</form:form>

											<h4
												class="card-title mb-4 mt-5 text-center animate__animated animate__fadeInDown">Business
												Entity Details</h4>
											<div class="isf-form mt-4">
												<div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">Business Entity Details
															and Address</h3>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Business Entity Name<span>*</span></label> <input
																type="text" class="form-control"
																value="${businessEntityName}" disabled="disabled"
																id="businessEntityName" name="businessEntityName">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Organisation Type<span>*</span></label> <select
																class="form-control" disabled="disabled">
																<option value="${businessEntityType}">${businessEntityType}</option>

															</select>
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Authorised Signatory Name<span>*</span></label> <input
																type="text" class="form-control"
																value="${authorisedSignatoryName}" disabled="disabled"
																id="authorisedSignatoryName"
																name="authorisedSignatoryName">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Designation<span>*</span></label> <input
																type="text" class="form-control"
																value="${businessDesignation}" disabled="disabled"
																id="businessDesignation" name="businessDesignation">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Email<span>*</span></label> <input type="email"
																class="form-control" value="${emailId}"
																disabled="disabled" id="emailId" name="emailId">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Mobile No.<span>*</span></label> <input
																type="text" class="form-control" value="${mobileNumber}"
																disabled="disabled" id="mobileNumber"
																name="mobileNumber">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Fax</label> <input type="text"
																class="form-control" value="${fax}" disabled="disabled"
																id="fax" name="fax">
														</div>
													</div>
													<div class="col-md-6 col-lg-8 col-xl-8">
														<div class="form-group">
															<label>Registered Office Address<span>*</span></label> <input
																type="text" class="form-control"
																value="${businessAddress}" disabled="disabled"
																id="businessAddress" name="businessAddress">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Select Country<span>*</span></label> <select
																class="form-control" disabled="disabled">

																<option value="${businessCountryName}">${businessCountryName}</option>
															</select>
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Select State/UT<span>*</span></label> <select
																class="form-control" disabled="">

																<option value="${businessStateName}">${businessStateName}</option>
															</select>
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Select District<span>*</span></label> <select
																class="form-control" disabled="disabled">

																<option value="${businessDistrictName}">${businessDistrictName}</option>
															</select>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Pin Code<span>*</span></label> <input type="text"
																class="form-control" value="${PinCode}"
																disabled="disabled" id="PinCode" name="PinCode">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">Director / Partner / Owner
															/ Proprietor Details</h3>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12">
														<div class="table-responsive">
															<table
																class="table table-stripped table-bordered directors-table"
																id="customFields">
																<c:if test="${ not empty ProprietorDetailsList}">

																	<thead>
																		<tr>
																			<th>Director Name</th>
																			<th>Designation</th>
																			<th>Address</th>
																			<th>Mobile No.</th>
																			<th>% Equity</th>
																			<th>Email</th>
																			<th>Gender</th>
																			<th>Category</th>
																			<th>Pan No</th>
																			<th>DIN</th>
																		</tr>
																	</thead>
																</c:if>
																<tbody>
																	<c:forEach var="list" items="${ProprietorDetailsList}"
																		varStatus="counter">
																		<tr>
																			<td>${list.directorName}</td>
																			<td>${list.designation}</td>
																			<td>${list.proprietorDetailsaddress}</td>
																			<td>${list.mobileNo}</td>
																			<td>${list.equity}</td>
																			<td>${list.email}</td>
																			<td>${list.gender}</td>
																			<td>${list.category}</td>
																			<td>${list.panCardNo}</td>
																			<td>${list.din}</td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">Uploaded Documents and
															Certificates</h3>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Year of Unit Establishment<span>*</span></label> <input
																type="text" class="form-control"
																value="${yearEstablishment}" disabled="disabled"
																id="yearEstablishment" name="yearEstablishment">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>GSTIN<span>*</span></label> <input type="text"
																value="${gstin}" disabled="disabled"
																class="form-control" id="gstin" name="gstin">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>CIN<span>*</span></label> <input type="text"
																value="${cin}" disabled="disabled" class="form-control"
																id="cin" name="cin">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Company Pan No.<span>*</span></label> <input
																type="text" class="form-control" value="${companyPanNo}"
																disabled="disabled" id="companyPanNo"
																name="companyPanNo">
														</div>
													</div>
													<div class="col-sm-8">
														<div class="form-group">
															<label>MoA / Partnership Deed Attachment<span>*</span></label>
															<div class="custom-file">
																<input type="text" class="form-control"
																	value="${moaDocFname}" disabled="disabled"
																	id="moaDocFname" name="moaDocFname">
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12">
														<div class="form-group">
															<label>Certificate Incorporation Registration
																Attachment<span>*</span>
															</label>
															<div class="custom-file">
																<input type="text" class="form-control"
																	value="${regisAttacDocFname}" disabled="disabled"
																	id="regisAttacDocFname" name="regisAttacDocFname">
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12">
														<div class="form-group">
															<label>Certified copy of the resolution of the
																Board of Directors of the Company<span>*</span>
															</label>
															<div class="custom-file">
																<input type="text" class="form-control"
																	value="${bodDocFname}" disabled="disabled"
																	id="bodDocFname" name="bodDocFname">
															</div>
														</div>
													</div>
													<div class="col-sm-12">
														<div class="form-group">
															<label>Competent Authority of the Industrial
																Undertaking authorizing the deponent be provided along
																with the affidavit.<span>*</span>
															</label>
															<div class="custom-file">
																<input type="text" class="form-control"
																	value="${indusAffidaDocFname}" disabled="disabled"
																	id="indusAffidaDocFname" name="indusAffidaDocFname">
															</div>
														</div>
													</div>
													<div class="col-sm-12 mt-3 mb-3">
														<div class="table-responsive">
															<table class="table table-bordered">
																<tbody>
																	<tr>
																		<td>Annexure I-A Format<font color="red"><span>*</span></font></td>
																		<td>
																			<div class="custom-file">
																				<input type="text" class="form-control"
																					value="${annexureiaformat}" disabled="disabled"
																					id="annexureiaformat" name="annexureiaformat">
																			</div>
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
												<hr class="mt-4">
											</div>
											<h4
												class="card-title mb-4 mt-5 text-center animate__animated animate__fadeInDown">Project
												Details</h4>
											<div class="isf-form">
												<div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">Project Details</h3>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Name of Contact Person<span>*</span></label> <input
																type="text" class="form-control"
																value="${contactPersonName}" disabled="disabled"
																id="contactPersonName" name="contactPersonName">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Mobile No:(+91)<span>*</span></label> <input
																type="text" class="form-control" value="${mobileNo}"
																disabled="disabled" id="mobileNo" name="mobileNo">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Designation</label> <input type="text"
																class="form-control" value="${designation}"
																disabled="disabled" id="designation" name="designation">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-12">
														<div class="form-group">
															<label>Project Description<span>*</span></label>
															<textarea class="form-control" disabled="disabled"
																id="projectDescription" name="projectDescription">${projectDescription}</textarea>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-12">
														<div class="form-group">
															<label>Website <small>(if any)</small></label> <input
																type="text" class="form-control" value="${webSiteName}"
																disabled="disabled" id="webSiteName" name="webSiteName">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">Project Location</h3>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12">
														<div class="form-group">
															<label>Full Address<span>*</span></label> <input
																type="text" class="form-control" value="${fullAddress}"
																disabled="disabled" id="fullAddress" name="fullAddress">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>District<span>*</span></label> <input type="text"
																class="form-control" value="${districtName}"
																disabled="disabled" id="districtName"
																name="districtName">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Mandal</label> <input type="text"
																class="form-control" value="${mandalName}"
																disabled="disabled" id="mandalName" name="mandalName">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Region</label> <input type="text"
																class="form-control" value="${resionName}"
																disabled="disabled" id="resionName" name="resionName">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Pin Code<span>*</span></label> <input type="text"
																class="form-control" value="${pinCode}"
																disabled="disabled" id="pinCode" name="pinCode">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">Registration or License
															for setting up Industrial Undertaking</h3>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12">
														<div
															class="custom-control custom-radio custom-control-inline">
															<input type="radio" class="custom-control-input"
																disabled="disabled" id="EncloseUAM" name="regiOrLicense"
																value="EncloseUAM"> <label
																class="custom-control-label" for="EncloseUAM">Enclose
																Udyam Registration Number <small>(for MSME)</small>
															</label>
														</div>
														<div
															class="custom-control custom-radio custom-control-inline">
															<input type="radio" class="custom-control-input"
																disabled="disabled" id="IEMcopy" name="regiOrLicense"
																value="IEMcopy"> <label
																class="custom-control-label" for="IEMcopy">IEM
																copy <small>(for large & Mega)<font color="red"><span>*</span></font>
															</label>
														</div>
														<div class="custom-file mt-2">
															<input type="text" class="form-control"
																value="${regiOrLicenseFileName}" disabled="disabled"
																id="annexureiaformat" name="annexureiaformat">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">
															Select Nature of the project<font color="red"><span>*</span></font>
														</h3>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12">
														<div
															class="custom-control custom-checkbox custom-control-inline">
															<input type="checkbox" disabled="disabled"
																class="custom-control-input" id="NewProject"
																name="newProject" value="NewProject"> <label
																class="custom-control-label" for="NewProject">New
																Project</label>
														</div>
														<div
															class="custom-control custom-checkbox custom-control-inline">
															<input type="checkbox" disabled="disabled"
																class="custom-control-input" id="Expansion"
																name="expansion" value="Expansion"> <label
																class="custom-control-label" for="Expansion">Expansion</label>
														</div>
														<div
															class="custom-control custom-checkbox custom-control-inline">
															<input type="checkbox" disabled="disabled"
																class="custom-control-input" id="Diversification"
																name="diversification" value="Diversification">
															<label class="custom-control-label" for="Diversification">Diversification</label>
														</div>
													</div>
												</div>
												<div class="row" id="hideById">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">
															Details of existing/proposed products to be manufactured
															and its capacity <small>(for expansion projects
																only)</small>
														</h3>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12">
														<div class="table-responsive mt-3">
															<table class="table table-stripped table-bordered">
																<thead>
																	<tr>
																		<th style="width: 50%;">Particulars</th>
																		<th>Details</th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<td>Existing Products<font color="red"><span>*</span></font>
																		</td>
																		<td><input type="text" class="form-control"
																			value="${ExistingProducts}" disabled="disabled"
																			name="ExistingProducts"></td>
																	</tr>
																	<tr>
																		<td>Existing Installed Capacity<font color="red"><span>*</span></font></td>
																		<td><input type="text" class="form-control"
																			value="${existingInstalledCapacity}"
																			disabled="disabled" name="existingInstalledCapacity"></td>
																	</tr>
																	<tr>
																		<td>Proposed Products<font color="red"><span>*</span></font></td>
																		<td><input type="text" class="form-control"
																			value="${proposedProducts}" disabled="disabled"
																			name="proposedProducts"></td>
																	</tr>
																	<tr>
																		<td>Proposed Installed Capacity<font color="red"><span>*</span></font></td>
																		<td><input type="text" class="form-control"
																			value="${proposedInstalledCapacity}"
																			disabled="disabled" name="proposedProducts"></td>
																	</tr>
																	<tr>
																		<td>Existing Gross Block<font color="red"><span>*</span></font></td>
																		<td><input type="text" class="form-control"
																			value="${existingGrossBlock}" disabled="disabled"
																			name="existingGrossBlock"></td>
																	</tr>
																	<tr>
																		<td>Proposed Gross Block<font color="red"><span>*</span></font></td>
																		<td><input type="text" class="form-control"
																			value="${proposedGrossBlock}" disabled="disabled"
																			name="proposedGrossBlock"></td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
											<div class="row" id="hideByRowId4">
												<div class="col-sm-12 mt-4">
													<h3 class="common-heading">Relevant Documentary
														Support</h3>
												</div>
											</div>
											<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive mt-3">
														<table class="table table-stripped table-bordered">
															<tbody>
																<tr id="hideByRowId3">
																	<td style="width: 50%;">
																		<ul class="common-list">
																			<li>Enclose Detailed Project Report prepared by
																				External Consultant/Chartered Accountants<font
																				color="red"><span>*</span></font> <img
																				src="images/pdf-icon.png" class="pdf-icon"
																				alt="pdf-icon">
																			</li>
																		</ul>
																	</td>
																	<td>
																		<div class="custom-file">
																			<input type="text" class="form-control"
																				value="${enclDetProRepFileName}" disabled="disabled"
																				id="enclDetProRepFileName"
																				name="enclDetProRepFileName">

																		</div>
																	</td>
																</tr>
																<tr id="hideByRowId1">
																	<td>
																		<ul class="common-list">
																			<li>CA Certificate for existing gross block <font
																				color="red"><span>*</span></font><img
																				src="images/pdf-icon.png" class="pdf-icon"
																				alt="pdf-icon"></li>
																		</ul>
																	</td>
																	<td>
																		<div class="custom-file">
																			<input type="text" class="form-control"
																				value="${caCertificateFileName}" disabled="disabled"
																				id="caCertificateFileName"
																				name="caCertificateFileName">

																		</div>
																	</td>
																</tr>
																<tr id="hideByRowId2">
																	<td>
																		<ul class="common-list">
																			<li>Chartered Engineerâs Certified List of
																				Fixed Assets in support of existing gross block.<font
																				color="red"><span>*</span></font><img
																				src="images/pdf-icon.png" class="pdf-icon"
																				alt="pdf-icon">
																			</li>
																		</ul>
																	</td>
																	<td>
																		<div class="custom-file mt-2">
																			<input type="text" class="form-control"
																				value="${charatEngFileName}" disabled="disabled"
																				id="charatEngFileName" name="charatEngFileName">

																		</div>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>

											<h4
												class="card-title mb-4 mt-5 text-center animate__animated animate__fadeInDown">Investment
												Details</h4>
											<div class="isf-form">
												<div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">Investment Details</h3>
													</div>
												</div>
												<form:form modelAttribute="viewInvestDetails"
													autocomplete="off" method="POST">
													<div class="tab-content py-2">
														<div class="row">

															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="invIndType">Category of Industrial Undertaking  <span>*</span>
																	</form:label>
																	<form:select id="invIndType" path="invIndType"
																		disabled="true"
																		onchange="return calculate(); validateInvestmentForm()"
																		class="form-control" name="invIndType">
																		<form:option value="">Select One</form:option>
																		<form:option label="Small" value="Small"></form:option>
																		<form:option label="Medium" value="Medium"></form:option>
																		<form:option label="Large" value="Large"></form:option>
																		<form:option label="Mega" value="Mega"></form:option>
																		<form:option label="Mega Plus" value="Mega Plus"></form:option>
																		<form:option label="Super Mega" value="Super Mega"></form:option>

																	</form:select>
																	<span id="invindtype" class="text-danger"></span>
																	<form:errors path="invIndType" cssClass="error" />
																</div>
															</div>
															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group ">
																	<form:label path="invFci">Proposed Fixed Capital Investment
													<span>*</span>
																	</form:label>
																	<form:input id="invFci" path="invFci" readonly="true"
																		onchange="return calculate(); validateInvestmentForm()"
																		class="form-control fixed-total"
																		onkeypress="return validateNumberField()"></form:input>

																	<span id="invfciSpan" class="text-danger"></span>
																	<form:errors path="invFci" cssClass="error" />
																</div>
															</div>

															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="invTotalProjCost">Total Cost of Project
													<span>*</span>
																	</form:label>
																	<form:input id="invTotalProjCost"
																		path="invTotalProjCost" readonly="true"
																		onkeypress="return validateNumberField()"
																		class="form-control all-Sum-total"></form:input>
																	<span id="invtotalprojcost" class="text-danger"></span>
																	<form:errors path="invTotalProjCost" cssClass="error" />
																</div>
															</div>
														</div>


														<div class="row">
															<div class="col-sm-12 mt-4">
																<h3 class="common-heading">Break-up of Cost of
																	Project and Proposed Fixed Capital Investment</h3>
															</div>
														</div>

														<div class="row">
															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="invLandCost">Land & Site Development Cost 
													<span>*</span>
																	</form:label>
																	<form:input id="invLandCost" path="invLandCost"
																		placeholder="Enter Amount in INR" readonly="true"
																		onchange="return calculate(); validateInvestmentForm()"
																		onkeypress="return validateNumberField()"
																		maxlength="12"
																		class="form-control fixed-total-input all-total-input"></form:input>
																	<small class="mt-2 d-block text-info" id="words"></small>
																	<span id="invlandcost" class="text-danger"></span>
																	<form:errors path="invLandCost" cssClass="error" />
																</div>
															</div>

															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="invBuildingCost">Building and Civil Works Cost 
													<span>*</span>
																	</form:label>
																	<form:input id="invBuildingCost" path="invBuildingCost"
																		placeholder="Enter Amount in INR" readonly="true"
																		onchange="return calculate(); validateInvestmentForm()"
																		onkeypress="return validateNumberField()"
																		maxlength="12"
																		class="form-control fixed-total-input all-total-input"></form:input>
																	<small class="mt-2 d-block text-info" id="words1"></small>
																	<span id="invbuildingcost" class="text-danger"></span>
																	<form:errors path="invBuildingCost" cssClass="error" />
																</div>
															</div>
														</div>
														<div class="row">
															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="invPlantAndMachCost">Plant & Machinery Cost  
													<span>*</span>
																	</form:label>
																	<form:input path="invPlantAndMachCost"
																		placeholder="Enter Amount in INR" readonly="true"
																		class="form-control fixed-total-input all-total-input"
																		onchange="return calculate(); validateInvestmentForm()"
																		onkeypress="return validateNumberField()"
																		maxlength="12" id="invPlantAndMachCost"></form:input>
																	<span id="invplantandmachcost" class="text-danger"></span>
																	<small class="mt-2 d-block text-info" id="words2"></small>
																	<form:errors path="invPlantAndMachCost"
																		cssClass="error" />
																</div>
															</div>
															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="invOtherCost">Miscellaneous Fixed Asset(MFA)
													<span>*</span>
																	</form:label>
																	<form:input id="invOtherCost" path="invOtherCost"
																		placeholder="Enter Amount in INR" readonly="true"
																		onchange="return calculate(); validateInvestmentForm()"
																		onkeypress="return validateNumberField()"
																		maxlength="12"
																		class="form-control fixed-total-input all-total-input"></form:input>
																	<small class="mt-2 d-block text-info" id="words3"></small>
																	<span id="invothercost" class="text-danger"></span>
																	<form:errors path="invOtherCost" cssClass="error" />
																</div>
															</div>
														</div>


														<div class="row">
															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="invcostEscContg">Provision for Cost Escalation & Contingencies
													
												</form:label>
																	<form:input id="invcostEscContg" path="invcostEscContg"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" class="form-control all-total-input"></form:input>
																	<small class="mt-2 d-block text-info" id="words4"></small>
																	<span id="invcostesccontg" class="text-danger"></span>
																	<form:errors path="invcostEscContg" cssClass="error" />
																</div>
															</div>

															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="invprelPreopExp">Preliminary & Preoperative Expenses 
												
												</form:label>
																	<form:input id="invprelPreopExp" path="invprelPreopExp"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" class="form-control all-total-input"></form:input>
																	<small class="mt-2 d-block text-info" id="words5"></small>
																	<span id="invprelpreopexp" class="text-danger"></span>
																	<form:errors path="invprelPreopExp" cssClass="error" />
																</div>
															</div>
														</div>


														<div class="row">
															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="invIntCons">Interest During Construction Period  
													
												</form:label>
																	<form:input path="invIntCons"
																		class="form-control all-total-input"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" id="invIntCons"></form:input>
																	<small class="mt-2 d-block text-info" id="words6"></small>
																	<span id="invintcons" class="text-danger"></span>
																	<form:errors path="invIntCons" cssClass="error" />
																</div>
															</div>
															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="invmmwCap">Margin Money for Working Capital
													
												</form:label>
																	<form:input id="invmmwCap" path="invmmwCap"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" class="form-control all-total-input"></form:input>
																	<small class="mt-2 d-block text-info" id="words7"></small>
																	<span id="invmmwcap" class="text-danger"></span>
																	<form:errors path="invmmwCap" cssClass="error" />
																</div>
															</div>
														</div>
														<!-- <div class="col-md-6 col-lg-12 col-xl-12"></div> -->
														</form:form>



														<!-- <div class="row">
															<div class="col-sm-12 mt-2">
																<h3 class="common-heading">
																	Other for Break-up of Cost <small>( If any)</small>
																</h3>
															</div>
														</div> -->


														<div class="row mt-3 mb-3">
															<div class="col-md-12">
																<div class="table-responsive">
																	<table class="table table-bordered" id="customFields1"
																		tabindex="1">

																		<thead>
																			<tr>
																				<!-- <th>Sr. No.</th> -->
																				<th style="width: 60%;">Parameter</th>
																				<th>Amount</th>

																			</tr>
																		</thead>

																		<%
																		int i = 0;
																	%>
																		<tbody>
																			<c:forEach var="brkupObj" items="${brkupList}">
																				<!-- Iterating the list using JSTL tag  -->
																				<c:if test="${not empty brkupList}">

																					<tr>
																						<td class="text-center"><%=++i%></td>
																						<td>${brkupObj.brkupParameter}</td>

																						<td><form:input path="brkupAmount"
																								readonly="true" value="${brkupObj.brkupAmount}"
																								class="form-control all-total-input"
																								id="brkupAmount"
																								onkeypress="return validateNumberField()"
																								maxlength="12"></form:input></td>

																					</tr>

																				</c:if>
																			</c:forEach>
																		</tbody>


																	</table>
																</div>
															</div>
														</div>


														<hr class="mb-4 mt-4 thick-line">

														<div class="row">
															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="invfdInvest">Indicate First Date of Investment <span>*</span>
																	</form:label>
																	<form:input path="invfdInvest" type="date"
																		readonly="true" id="invfdInvest"
																		onblur="return validateDate();" class="form-control"></form:input>
																	<span id="invfdinvest" class="text-danger"></span>
																	<form:errors path="invfdInvest" cssClass="error" />
																</div>
																<div class="form-group">
																	<form:label path="invCommenceDate">Indicate Opted Cut-off date investment <span>*</span>
																	</form:label>
																	<form:input path="invCommenceDate" type="date"
																		readonly="true" onblur="return validateDate();"
																		class="form-control" id="invCommenceDate"></form:input>
																	<span id="invcommencedate" class="text-danger"></span>

																	<c:if test="${not empty commencepastdatemsg}">
																		<span class="text-danger">${commencepastdatemsg}</span>
																	</c:if>
																	<c:if test="${not empty commenceperiodmsg}">
																		<span class="text-danger">${commenceperiodmsg}</span>
																	</c:if>

																	<form:errors path="invCommenceDate" cssClass="error" />
																</div>
															</div>


															<div class="col-md-6">
																<div class="form-group">
																	<form:label path="propCommProdDate">Proposed Date of Commencement of Commercial Production <span>*</span>
																	</form:label>
																	<form:input id="propCommProdDate"
																		path="propCommProdDate" readonly="true"
																		onblur="return validateDate1();" type="date"
																		class="form-control"></form:input>
																	<span id="propcommproddate" class="text-danger"></span>
																	<c:if test="${not empty propdatemessage}">
																		<span class="text-danger">${propdatemessage}</span>
																	</c:if>

																	<form:errors path="propCommProdDate" cssClass="error" />
																</div>
																<div class="form-group">
																	<form:label path="invCommenceDate">Indicate Opted Cut-off date investment <span>*</span>
																	</form:label>
																	<form:input path="invCommenceDate" type="date"
																		readonly="true" onblur="return validateDate();"
																		class="form-control" id="invCommenceDate"></form:input>
																	<span id="invcommencedate" class="text-danger"></span>

																	<c:if test="${not empty commencepastdatemsg}">
																		<span class="text-danger">${commencepastdatemsg}</span>
																	</c:if>
																	<c:if test="${not empty commenceperiodmsg}">
																		<span class="text-danger">${commenceperiodmsg}</span>
																	</c:if>

																	<form:errors path="invCommenceDate" cssClass="error" />
																</div>
															</div>
														</div>



														<div class="col-md-6">
															<div class="form-group">
																<form:label path="propCommProdDate">Proposed Date of Commencement of Commercial Production <span>*</span>
																</form:label>
																<form:input id="propCommProdDate"
																	path="propCommProdDate" readonly="true"
																	onblur="return validateDate1();" type="date"
																	class="form-control"></form:input>
																<span id="propcommproddate" class="text-danger"></span>
																<c:if test="${not empty propdatemessage}">
																	<span class="text-danger">${propdatemessage}</span>
																</c:if>

																<form:errors path="propCommProdDate" cssClass="error" />
															</div>
														</div>


														<hr class="mb-4 mt-4 thick-line">


														<div class="row">
															<div class="col-md-12">
																<div class="form-group">
																	<form:label path="invPriorFdiAmt">Amount invested prior to First Date of Investment 
												
										</form:label>
																	<form:input path="invPriorFdiAmt" class="form-control"
																		readonly="true" placeholder="Enter Amount in INR"
																		onkeypress="return validateNumberField()"
																		maxlength="12" id="invPriorFdiAmt"></form:input>
																	<span id="invpriorfdiamt" class="text-danger"></span> <small
																		class="mt-2 d-block text-info" id="words8"></small>
																	<form:errors path="invPriorFdiAmt" cssClass="error" />
																</div>
															</div>
															<div class="col-md-12">
																<div class="form-group">
																	<form:label path="pwCutoffProdAmt">Amount of Investment from cut-off date to commercial Production Date 
											
										</form:label>
																	<form:input path="invCutoffProdAmt"
																		class="form-control" placeholder="Enter Amount in INR"
																		readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" id="invCutoffProdAmt"></form:input>
																	<span id="invcutoffprodamt" class="text-danger"></span>
																	<small class="mt-2 d-block text-info" id="words23"></small>
																	<form:errors path="pwCutoffProdAmt" cssClass="error" />
																</div>
															</div>
															<div class="col-md-12">
																<div class="form-group">
																	<form:label path="invPriorCutAmt">Amount of investment made in the proposed
											project prior to Opted Cut-off date then indicate amount
											invested from first date till opted cut-off date(If any) 
												
										</form:label>
																	<form:input path="invPriorCutAmt" class="form-control"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" id="invPriorCutAmt"></form:input>
																	<span id="invpriorcutamt" class="text-danger"></span> <small
																		class="mt-2 d-block text-info" id="words10"></small>
																	<form:errors path="invPriorCutAmt" cssClass="error" />

																</div>
															</div>
															<div class="col-md-12">
																<div class="form-group">
																	<form:label path="invComProdAmt">Amount of Investment in the proposed project
											after commencement of commercial production 
												
										</form:label>
																	<form:input path="invComProdAmt" class="form-control"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" id="invComProdAmt"></form:input>
																	<span id="invcomprodamt" class="text-danger"></span> <small
																		class="mt-2 d-block text-info" id="words11"></small>
																	<form:errors path="invComProdAmt" cssClass="error" />

																</div>
															</div>
														</div>


														<!-- <div class="row">
															<div class="col-sm-12 mt-4">
																<h3 class="common-heading">Means of Financing</h3>
															</div>
														</div> -->
														<div class="row">
															<div class="col-md-12">
																<div class="form-group">
																	<h5 class="popup-heading">Equity</h5>
																	<hr>
																</div>
															</div>

															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="invEqShCapital">Equity Share Capital
                            
										</form:label>
																	<form:input id="invEqShCapital" path="invEqShCapital"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" class="form-control other-total-input"></form:input>
																	<span id="inveqshcapital" class="text-danger"></span> <small
																		class="mt-2 d-block text-info" id="words12"></small>
																	<form:errors path="invEqShCapital" cssClass="error" />
																</div>
															</div>


															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="invEqIntCashAccrl">Internal Cash Accruals
                            
										</form:label>
																	<form:input id="invEqIntCashAccrl"
																		path="invEqIntCashAccrl" readonly="true"
																		placeholder="Enter Amount in INR"
																		onkeypress="return validateNumberField()"
																		maxlength="12" class="form-control other-total-input"></form:input>
																	<span id="inveqintcashAccrl" class="text-danger"></span>
																	<small class="mt-2 d-block text-info" id="words13"></small>
																	<form:errors path="invEqIntCashAccrl" cssClass="error" />
																</div>
															</div>



															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="invEqIntFreeUnsecLoan">Interest Free Unsecured Loans
                            
										</form:label>
																	<form:input id="invEqIntFreeUnsecLoan"
																		path="invEqIntFreeUnsecLoan" readonly="true"
																		placeholder="Enter Amount in INR"
																		onkeypress="return validateNumberField()"
																		maxlength="12" class="form-control other-total-input"></form:input>
																	<span id="inveqintfreeunsecloan" class="text-danger"></span>
																	<small class="mt-2 d-block text-info" id="words14"></small>
																	<form:errors path="invEqIntFreeUnsecLoan"
																		cssClass="error" />
																</div>
															</div>


															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="invEqSecDept">Security Deposit
                            
										</form:label>
																	<form:input id="invEqSecDept" path="invEqSecDept"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" class="form-control other-total-input"></form:input>
																	<span id="inveqsecdept" class="text-danger"></span> <small
																		class="mt-2 d-block text-info" id="words15"></small>
																	<form:errors path="invEqSecDept" cssClass="error" />
																</div>
															</div>



															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="invEqAdvDealer">Advances from Dealers
                            
										</form:label>
																	<form:input id="invEqAdvDealer" path="invEqAdvDealer"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" class="form-control other-total-input"></form:input>
																	<span id="inveqadvdealer" class="text-danger"></span> <small
																		class="mt-2 d-block text-info" id="words16"></small>
																	<form:errors path="invEqAdvDealer" cssClass="error" />
																</div>
															</div>
														</div>



														<div class="row">
															<div class="col-md-12">
																<div class="form-group">
																	<h5 class="popup-heading">Debt</h5>
																	<hr>
																</div>
															</div>

															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="invDebtFi">From Financial Institution (FI's) 
                            
										</form:label>
																	<form:input id="invDebtFi" path="invDebtFi"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" class="form-control other-total-input"></form:input>
																	<span id="invdebtfi" class="text-danger"></span> <small
																		class="mt-2 d-block text-info" id="words17"></small>
																	<form:errors path="invDebtFi" cssClass="error" />
																</div>
															</div>


															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="invDebtBank">From Bank 
                           
										</form:label>
																	<form:input id="invDebtBank" path="invDebtBank"
																		placeholder="Enter Amount in INR" readonly="true"
																		onkeypress="return validateNumberField()"
																		maxlength="12" class="form-control other-total-input"></form:input>
																	<span id="invdebtbank" class="text-danger"></span> <small
																		class="mt-2 d-block text-info" id="words19"></small>
																	<form:errors path="invDebtBank" cssClass="error" />
																</div>
															</div>
														</div>

														<!-- <div class="row">
															<div class="col-sm-12 mt-2">
																<h3 class="common-heading">
																	Other for Means of Financing <small>( If any)</small>
																</h3>
															</div>
														</div> -->



														<div class="row mt-3 mb-3">
															<div class="col-md-12">
																<div class="table-responsive">
																	<table class="table table-bordered" id="customFields2"
																		tabindex="2">
																		<c:if test="${not empty mofList}">
																			<thead>
																				<tr>
																					<!-- 	<th>Sr. No.</th> -->
																					<th style="width: 60%;">Parameter</th>
																					<th>Amount</th>

																				</tr>
																			</thead>

																			<%
																			int k = 0;
																		%>
																			<tbody>
																				<c:forEach var="mofObj" items="${mofList}">
																					<!-- Iterating the list using JSTL tag  -->
																					<c:if test="${not empty mofList}">

																						<tr>
																							<td class="text-center"><%=++k%></td>
																							<td>${mofObj.mofParameter}</td>
																							<td><form:input path="mofAmount"
																									value="${mofObj.mofAmount}" readonly="true"
																									class="form-control other-total-input"
																									id="mofAmount" name="mofAmount"
																									onkeypress="return validateNumberField()"
																									maxlength="12"></form:input></td>

																						</tr>
																					</c:if>

																				</c:forEach>
																			</tbody>
																		</c:if>
																	</table>
																</div>
															</div>
														</div>






														<div class="row">
															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="invFinTotal">Total
                            
										</form:label>
																	<form:input id="invFinTotal" path="invFinTotal"
																		readonly="true"
																		onkeypress="return validateNumberField()"
																		class="form-control all-other-total"></form:input>
																	<span id="invfintotal" class="text-danger"></span>
																	<form:errors path="invFinTotal" cssClass="error" />
																</div>
															</div>
														</div>

														<div class="row">
															<div class="col-sm-12 mt-2">
																<h3 class="common-heading">Phase Wise Investment</h3>
															</div>
														</div>

														<div class="row">
															<div class="col-md-7">
																<div class="form-group">
																	<form:label path="pwApply">Phase Wise Investment as per Detailed Project
											Report <span>*</span> (If Applicable)
										</form:label>
																	<form:select class="form-control" id="pwApply"
																		disabled="true" name="pwApply" path="pwApply">
																		<form:option value="">Select</form:option>
																		<form:option value="Yes">Yes</form:option>
																		<form:option value="No">No</form:option>
																	</form:select>
																	<span id="pwapply" class="text-danger"></span>
																</div>
															</div>
														</div>
												</form:form>


												</form:form>
												<form:form modelAttribute="phaseWiseInvestmentDetails"
													onsubmit="return validatePwInvestmentForm()"
													autocomplete="off" name="pwInvestmentForm"
													action="addPwInvestment" method="POST">
													<div class="if-applicable" id="Yes" style="display: none"
														id="if-applicable">
														<div class="row">
															<div class="col-md-6 col-lg-3 col-xl-3">
																<div class="form-group">
																	<form:label path="pwPhaseName">Name of the Phase</form:label>
																	<form:input path="pwPhaseName" class="form-control"
																		readonly="true" maxlength="30" name="pwPhaseName"
																		id="pwPhaseName"></form:input>
																	<span id="pwphasename" class="text-danger"></span>
																	<form:errors path="pwPhaseName" cssClass="error" />
																</div>
															</div>
															<div class="col-md-6 col-lg-4 col-xl-4">
																<div class="form-group">
																	<form:label path="pwFci">Proposed Fixed Capital Investment
											</form:label>
																	<form:input path="pwFci" class="form-control"
																		id="pwFci" readonly="true"
																		placeholder="Enter Amount in INR" name="pwFci"
																		onkeypress="return validateNumberField()"
																		maxlength="12"></form:input>
																	<span id="pwfci" class="text-danger"></span> <small
																		class="mt-2 d-block text-info" id="words21"></small>
																	<form:errors path="pwFci" cssClass="error" />
																</div>
															</div>

															<div class="col-md-6 col-lg-5 col-xl-5">
																<div class="form-group">
																	<form:label path="pwPropProductDate">Proposed Date of Commercial Production</form:label>
																	<form:input path="pwPropProductDate" type="date"
																		readonly="true" name="pwPropProductDate"
																		id="pwPropProductDate" class="form-control"></form:input>
																	<span id="pwpropproductdate" class="text-danger"></span>
																	<form:errors path="pwPropProductDate" cssClass="error" />
																</div>
															</div>

															<div class="col-md-12">
																<div class="form-group">
																	<form:label path="pwCutoffProdAmt">Amount of Investment from cut-off date to commercial Production Date</form:label>
																	<form:input path="pwCutoffProdAmt" readonly="true"
																		placeholder="Enter Amount in INR"
																		onkeypress="return validateNumberField()"
																		maxlength="12" name="pwCutoffProdAmt"
																		id="pwCutoffProdAmt" class="form-control"></form:input>
																	<span id="pwcutoffprodamt" class="text-danger"></span>
																	<small class="mt-2 d-block text-info" id="words24"></small>
																	<form:errors path="pwCutoffProdAmt" cssClass="error" />
																</div>
															</div>
															</form:form>

														</div>

													</div>


													</form:form>



													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive mt-3">
																<table class="table table-stripped table-bordered"
																	id="customFields3" tabindex="3">
																	<c:if test="${not empty pwInvList}">
																		<thead>
																			<tr>
																				<th>Sr. No.</th>
																				<th>Name of the Phase</th>
																				<th>Proposed Fixed Capital Investment</th>
																				<th>Proposed Date of Commercial Production</th>
																				<th>Amount of Investment from cut-off date to
																					commercial Production Date</th>

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
																						<td class="text-center"><%=++i%></td>
																						<td>${pwInvObj.pwPhaseName}</td>
																						<td>${pwInvObj.pwFci}</td>
																						<td>${pwInvObj.pwPropProductDate}</td>
																						<td>${pwInvObj.pwCutoffProdAmt}</td>

																					</tr>
																				</c:if>

																			</c:forEach>
																		</tbody>
																	</c:if>
																</table>
															</div>
														</div>
													</div>
													<hr class="mt-4 mb-4">

												</form:form>
											</div>
											<div class="clearfix"></div>




											<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td>Upload for Break-Up of Project Cost & Means<br>
																		of Financing <small>(In PDF format less than
																			2MB)</small><img src="images/pdf-icon.png" class="pdf-icon"
																		id="pdficon" alt="pdf-icon"> <br> <!-- <small><a
																			href="./downloadmofFile/doc">Download Template</a></small> -->
																	</td>
																	<td>
																		<div class="custom-file">
																			<input type="file" name="invFilename" disabled
																				class="custom-file-input" onblur="emptyFile()"
																				id="brkupmofFile"></input> <label
																				class="custom-file-label" id="chooseInvfile"
																				for="BreakUpCost">Choose file</label> <span
																				id="brkupmoffile" class="text-danger"></span>
																		</div>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>












											<h4
												class="card-title mb-4 mt-5 text-center animate__animated animate__fadeInDown">
												Proposed Employment Details</h4>
											<div class="isf-form">
												<div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">Employment Details</h3>
													</div>
												</div>
												<form:form modelAttribute="skilledUnSkilledEmployemnt"
													name="SkilledUnSkilledEmployemntForm"
													action="addSkilUnSkilEmploy" method="post">
												</form:form>

												<div class="row">
													<div class="col-sm-12">
														<div class="table-responsive mt-3">
															<table class="table table-stripped table-bordered"
																id="customFields" tabindex='1'>
																<c:if
																	test="${ not empty skilledUnSkilledEmployemntList}">
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


												<c:if test="${not empty skilledDisplay}">
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
																<label>Total Skilled Employees</label> <input
																	type="text" class="form-control"
																	value="${totalSkilledEmployment}" disabled="disabled">
															</div>
														</div>
													</div>
												</c:if>

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
																	value="${unSkilledEmploymentFemale}"
																	disabled="disabled">
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


												<c:if test="${ not empty skilledUnSkilledEmployemntList}">

													<hr class="mt-4">

													<div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">Total Employees</h3>
														</div>
													</div>

													<div class="row">
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Total Male Employees</label> <input type="text"
																	class="form-control"
																	value="${grossTotalMaleEmployment}" disabled>
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

												</c:if>

												<hr class="mt-4">

												<h4
													class="card-title mb-4 mt-5 text-center animate__animated animate__fadeInDown">
													Incentive Details</h4>
												<div class="isf-form">
													<div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">SGST Reimbursement</h3>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table
																	class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td style="width: 70%;"><strong>Amount
																					of SGST claimed for reimbursement</strong></td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Claim_Reim}" disabled="disabled"
																				name="ISF_Claim_Reim"></td>
																		</tr>
																		<tr>
																			<td>
																				<div class="custom-control custom-checkbox mb-4">
																					<input type="checkbox" class="custom-control-input"
																						disabled="disabled" id="SGST1"
																						name="ISF_SGST_Claim_Reim" value="ISF_Reim_SCST">
																					<label class="custom-control-label" for="SGST1">Additional
																						10% GST where 25% minimum SC/ST workers employed
																						subject to minimum of 400 total workers in
																						industrial undertakings located in Paschimanchal
																						and minimum of 200 total workers in B-P-M</label>
																				</div>
																			</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Reim_SCST}" disabled="disabled"
																				name="ISF_Reim_SCST"></td>
																		</tr>
																		<tr>
																			<td>
																				<div class="custom-control custom-checkbox mb-4">
																					<input type="checkbox" class="custom-control-input"
																						disabled="disabled" id="SGST2"
																						name="ISF_SGST_Claim_Reim" value="ISF_Reim_FW">
																					<label class="custom-control-label" for="SGST2">Additional
																						10% GST where 40% minimum female workers employed
																						subject to minimum of 400 total workers in
																						industrial undertakings located in Paschimanchal
																						and minimum of 200 total workers in B-P-M</label>
																				</div>
																			</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Reim_FW}" disabled="disabled"
																				name="ISF_Reim_FW"></td>
																		</tr>
																		<tr>
																			<td>
																				<div class="custom-control custom-checkbox mb-4">
																					<input type="checkbox" class="custom-control-input"
																						disabled="disabled" id="SGST3"
																						name="ISF_SGST_Claim_Reim" value="ISF_Reim_BPLW">
																					<label class="custom-control-label" for="SGST3">Additional
																						10% GST where 25% minimum BPL workers employed
																						subject to minimum of 400 total workers in
																						industrial undertakings located in Paschimanchal
																						and minimum of 200 total workers in B-P-M</label>
																				</div>
																			</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Reim_BPLW}" disabled="disabled"
																				name="ISF_Reim_BPLW"></td>
																		</tr>
																		<tr>
																			<td><strong>Total SGST Reimbursement</strong></td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Ttl_SGST_Reim}" disabled="disabled"
																				name="ISF_Ttl_SGST_Reim"></td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">Stamp Duty
																Exemption/Reimbursement</h3>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table
																	class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td style="width: 70%;"><strong>Amount
																					of Stamp Duty Exemption</strong></td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Stamp_Duty_EX}" disabled="disabled"
																				name="ISF_Stamp_Duty_EX"></td>
																		</tr>
																		<tr>
																			<td colspan="2"><strong>Or</strong></td>
																		</tr>
																		<tr>
																			<td style="width: 70%;"><strong>Amount
																					of Stamp Duty Reimbursement</strong></td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Additonal_Stamp_Duty_EX}"
																				disabled="disabled"
																				name="ISF_Additonal_Stamp_Duty_EX"></td>
																		</tr>
																		<tr>
																			<td>
																				<div class="custom-control custom-checkbox mb-4">
																					<input type="checkbox" class="custom-control-input"
																						disabled="disabled" id="SGST4"
																						name="ISF_Stamp_Duty"
																						value="ISF_Additonal_Stamp_Duty_EX"> <label
																						class="custom-control-label" for="SGST4">Additional
																						Stamp Duty exemption @20% upto maximum of 100% in
																						case of industrial undertakings having 75% equity
																						owned by Divyang/SC/ ST/Females Promoters</label>
																				</div>
																			</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Amt_Stamp_Duty_Reim}"
																				disabled="disabled" name="ISF_Amt_Stamp_Duty_Reim"></td>
																		</tr>
																		<tr>
																			<td><strong>Total Stamp Duty
																					Exemption/Reimbursement</strong></td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Ttl_Stamp_Duty_EX}" disabled="disabled"
																				name="ISF_Ttl_Stamp_Duty_EX"></td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">EPF Reimbursement</h3>
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
																			<td><input type="text" class="form-control"
																				value="${ISF_Epf_Reim_UW}" disabled="disabled"
																				name="ISF_Epf_Reim_UW"></td>
																		</tr>
																		<tr>
																			<td>
																				<div class="custom-control custom-checkbox mb-4">
																					<input type="checkbox" class="custom-control-input"
																						disabled="disabled" id="SGST5"
																						name="Add_EFP_Reim_SkUkW_checkbox"
																						value="ISF_Add_Epf_Reim_SkUkW"> <label
																						class="custom-control-label" for="SGST5">Additional
																						10% EPF Reimbursement (200 direct skilled and
																						unskilled workers)</label>
																				</div>
																			</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Add_Epf_Reim_SkUkW}"
																				disabled="disabled" name="ISF_Add_Epf_Reim_SkUkW"></td>
																		</tr>
																		<tr>
																			<td>
																				<div class="custom-control custom-checkbox mb-4">
																					<input type="checkbox" class="custom-control-input"
																						disabled="disabled" id="SGST6"
																						name="Add_EFP_Reim_DIVSCSTF_checkbox"
																						value="ISF_Add_Epf_Reim_DIVSCSTF"> <label
																						class="custom-control-label" for="SGST6">Additional
																						10% EPF Reimbursement upto maximum of 70% in case
																						of industrial undertakings having 75% equity owned
																						by Divyang/SC/ST/Females Promoters</label>
																				</div>
																			</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Add_Epf_Reim_DIVSCSTF}"
																				disabled="disabled" name="ISF_Add_Epf_Reim_DIVSCSTF"></td>
																		</tr>
																		<tr>
																			<td><strong>Total EPF Reimbursement</strong></td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Ttl_EPF_Reim}" disabled="disabled"
																				name="ISF_Ttl_EPF_Reim"></td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">Interest Subsidies</h3>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table
																	class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td style="width: 70%;"><strong>Capital
																					Interest Subsidy</strong></td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Cis}" disabled="disabled"
																				name="ISF_Cis"></td>
																		</tr>
																		<tr>
																			<td>
																				<div class="custom-control custom-checkbox mb-4">
																					<input type="checkbox" class="custom-control-input"
																						disabled="disabled" id="SGST7"
																						name="Add_CIS_checkbox"
																						value="ISF_ACI_Subsidy_Indus"> <label
																						class="custom-control-label" for="SGST7">Additional
																						Capital Interest Subsidy@2.5% upto maximum of 7.5%
																						in case of industrial undertakings having 75%
																						equity owned by Divyang/SC/ST/Females Promoters</label>
																				</div>
																			</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_ACI_Subsidy_Indus}" disabled="disabled"
																				name="ISF_ACI_Subsidy_Indus"></td>
																		</tr>
																		<tr>
																			<td style="width: 70%;"><strong>Infrastructure
																					Interest Subsidy</strong></td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Infra_Int_Subsidy}" disabled="disabled"
																				name="ISF_Infra_Int_Subsidy"></td>
																		</tr>
																		<tr>
																			<td>
																				<div class="custom-control custom-checkbox mb-4">
																					<input type="checkbox" class="custom-control-input"
																						disabled="disabled" id="SGST8"
																						name="Add_IIS_checkbox"
																						value="ISF_AII_Subsidy_DIVSCSTF"> <label
																						class="custom-control-label" for="SGST8">Additional
																						Infrastructure Interest Subsidy @2.5% upto maximum
																						of 7.5% in case of industrial undertakings having
																						75% equity owned by Divyang/SC/ST/Females
																						Promoters</label>
																				</div>
																			</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_AII_Subsidy_DIVSCSTF}"
																				disabled="disabled" name="ISF_AII_Subsidy_DIVSCSTF"></td>
																		</tr>
																		<tr>
																			<td style="width: 70%;"><strong>Interest
																					Subsidy on loans for industrial research, quality
																					improvement, etc.</strong></td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Loan_Subsidy}" disabled="disabled"
																				name="ISF_Loan_Subsidy"></td>
																		</tr>
																		<tr>
																			<td><strong>Total Interest Subsidies</strong></td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Total_Int_Subsidy}" disabled="disabled"
																				name="ISF_Total_Int_Subsidy"></td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">Other Incentives</h3>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table
																	class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td style="width: 70%;">Reimbursement of
																				Disallowed Input Tax Credit on plant, building
																				materials, and other capital goods.</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Tax_Credit_Reim}" disabled="disabled"
																				name="ISF_Tax_Credit_Reim"></td>
																		</tr>
																		<tr>
																			<td>Exemption from Electricity Duty from captive
																				power for self-use</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_EX_E_Duty}" disabled="disabled"
																				name="ISF_EX_E_Duty"></td>
																		</tr>
																		<tr>
																			<td>Exemption from Electricity duty on power
																				drawn from power companies</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_EX_E_Duty_PC}" disabled="disabled"
																				name="ISF_EX_E_Duty_PC"></td>
																		</tr>
																		<tr>
																			<td>Exemption from Mandi Fee</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_EX_Mandee_Fee}" disabled="disabled"
																				name="ISF_EX_Mandee_Fee"></td>
																		</tr>
																		<tr>
																			<td>Industrial units providing employment to
																				differently abled workers will be provided payroll
																				assistance of Rs. 500 per month for each such
																				worker.</td>
																			<td><input type="text" class="form-control"
																				value="${ISF_Indus_Payroll_Asst}"
																				disabled="disabled" name="ISF_Indus_Payroll_Asst"></td>
																		</tr>
																		<tr>
																			<td><strong>Total Other Incentives</strong></td>
																			<td><input type="text" class="form-control"
																				value="${Total_Other_Incentive}" disabled="disabled"
																				name="Total_Other_Incentive"></td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12">
															<div
																class="custom-control form-group custom-radio custom-control-inline pl-0">
																<label class=""><strong>Do you want to
																		avail customised incentives</strong></label>
															</div>
															<div
																class="custom-control custom-radio custom-control-inline">
																<input type="radio" class="custom-control-input"
																	disabled="disabled" id="SGSTclaimedYes"
																	name="ISF_Cstm_Inc_Status" value="Yes"> <label
																	class="custom-control-label" for="SGSTclaimedYes">Yes</label>
															</div>
															<div
																class="custom-control custom-radio custom-control-inline">
																<input type="radio" class="custom-control-input"
																	disabled="disabled" id="SGSTclaimedNo"
																	name="ISF_Cstm_Inc_Status" value="No"> <label
																	class="custom-control-label" for="SGSTclaimedNo">No</label>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table
																	class="table table-stripped table-bordered directors-table"
																	id="customFields">
																	<c:if test="${ not empty availCustomisedDetailsList}">
																		<thead>
																			<tr>
																				<th>Incentive Type</th>
																				<th>Amount</th>
																				<th>Additional Request</th>
																				<th>Type Details of Customised Incentives</th>
																			</tr>
																		</thead>
																	</c:if>
																	<tbody>
																		<c:forEach var="list"
																			items="${availCustomisedDetailsList}"
																			varStatus="counter">
																			<tr>
																				<td>${list.addIncentiveType}</td>
																				<td>${list.addAmt}</td>
																				<td>${list.othAddRequest}</td>
																				<td>${list.typeDtlCusIncentives}</td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-12">
															<div class="form-group">
																<label>Upload Document <small>(In PDF
																		format less than 500 KB)</small> <img
																	src="images/pdf-icon.png" class="pdf-icon"
																	alt="pdf-icon"></label>
																<div class="custom-file">
																	<input type="text" class="form-control"
																		value="${isfCustIncDocName}" disabled="disabled"
																		id="isfCustIncDocName" name="isfCustIncDocName">
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div> --%>

										</div>
										<!-- End view All Application Details  -->


										<!-- Start Annexure-I  -->

										<div class="parameter-properties" id="Annexure-I">
											<hr class="mt-2">
											<h4
												class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Annexure-I</h4>
											<p
												class="mt-4 mb-1 text-center animate__animated animate__fadeInDown">
												<strong>APPLICATION FORM FOR SANCTION/DISBURSAL OF
													INCENTIVES FOR SMALL, MEDIUM & LARGE INDUSTRIAL
													UNDERTAKINGS</strong>
											</p>
											<p
												class="mb-2 text-center animate__animated animate__fadeInDown">
												<i>(all supporting documents must be authenticated by a
													Director/Partner/Officer duly authorized by the Competent
													authority of the Applicant on its behalf)</i>
											</p>
											<p
												class="mb-4 text-center animate__animated animate__fadeInDown">
												<strong>Category of Industrial Undertaking Applied
													For Based on Capital Investment & Location</strong>
											</p>


											<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td colspan="2" style="width: 17%;">Criteria for
																		investment</td>
																	<td rowspan="2">Investment in Plant and Machinery
																		as defined in MSMED Act, 2006</td>
																	<td rowspan="2">More than Rs 25 lakhs but does not
																		exceed Rs 5 Crore</td>
																	<td rowspan="2">More than Rs 5 crores but does not
																		exceed Rs. 10 crores</td>
																</tr>
																<tr>
																	<td class="text-center">Small</td>
																	<td class="text-center">Medium</td>
																</tr>
																<tr>
																	<td class="text-center">
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="Small-p" value="${categorytype}"
																				name="Small_Checkbox"> <label
																				class="custom-control-label" for="Small-p"></label>
																		</div>
																	</td>
																	<td class="text-center">
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="Medium-p" value="${categorytype}"
																				name="Medium_Checkbox"> <label
																				class="custom-control-label" for="Medium-p"></label>
																		</div>
																	</td>
																	<td>${pnm}</td>
																	<td class="text-center">
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="25exceedRs" value="${categorytype}"
																				name="Small_Checkbox"> <label
																				class="custom-control-label" for="25exceedRs"></label>
																		</div>
																	</td>
																	<td class="text-center">
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="5exceedRs" value="${categorytype}"
																				name="Medium_Checkbox"> <label
																				class="custom-control-label" for="5exceedRs"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td colspan="2">Investment amount</td>
																	<td colspan="3">${invFci}</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>

											<div class="row mt-4">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td style="width: 17%;">Criteria for investment of
																		Large</td>
																	<td colspan="3">Eligible Capital Investment as
																		Defined in the Rules</td>
																</tr>
																<tr>
																	<td rowspan="2">Beyond the investment amount in
																		Plant & Machinery for Medium Industrial Undertakings
																		(Rs 10 Crore) and upto capital investment Rs</td>
																	<td>Bundelkhand & Poorvanchal Investment amount
																		upto Rs 100 Crore</td>
																	<td>Madhyanchal & Paschimanchal (except Gautam
																		Buddh Nagar & Ghaziabad) <br>Investment amount
																		upto Rs 150 Crore
																	</td>
																	<td>Gautam Buddh Nagar & Ghaziabad <br>Investment
																		amount upto Rs 200 Crore
																	</td>
																</tr>
																<tr>
																	<td class="text-center">
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="Bundelkhand" value="${categorytype}"
																				name="Large_Checkbox"> <label
																				class="custom-control-label" for="Bundelkhand"></label>
																		</div>
																	</td>
																	<td class="text-center">
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="Madhyanchal" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="Madhyanchal"></label>
																		</div>
																	</td>
																	<td class="text-center">
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="Ghaziabad" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="Ghaziabad"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td>Investment amount</td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>District/ Area</td>
																	<td></td>
																	<td></td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>

											<div class="row mt-4">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th style="width: 7%;">Sl No</th>
																	<th style="width: 27%;">Particulars</th>
																	<th>Details</th>
																	<th style="width: 30%;">Relevant Documentary
																		Support</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>1.</td>
																	<td>Name, Address& Contact Details of the
																		Applicant</td>
																	<td>${authorisedSignatoryName},
																		${appAddressLine1} 
																		${appAddressLine2},
																		${viewSignatoryDetail.appMobileNo}</td>
																	<td>Certificate of Incorporation, registered
																		partnership deed, trust /society registration deed.</td>
																</tr>
																<tr>
																	<td>2.</td>
																	<td>Constitution of Applicant</td>
																	<td>${businessEntityName}</td>
																	<td>Company/Partnership Firm/Others
																		(MoA/Articles/By-Laws, etc)</td>
																</tr>
																<tr>
																	<td>3.</td>
																	<td>Location of the Existing/ Proposed Industrial
																		Undertaking</td>
																	<td>${businessAddress}</td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.</td>
																	<td>Names Address& Contact Details of
																		Directors/Partners /Others</td>
																	<td>
																		<div class="table-responsive">
																			<table class="table table-bordered">
																				<tbody>

																					<c:forEach var="list"
																						items="${ProprietorDetailsList}"
																						varStatus="counter">
																						<tr>
																							<td>${list.directorName}</td>
																							<td>${list.proprietorDetailsaddress}</td>
																							<td>${list.mobileNo}</td>
																						</tr>
																					</c:forEach>
																				</tbody>
																			</table>
																		</div>
																	</td>
																	<td>PAN & DIN numbers (Supported by relevant
																		documents)</td>
																</tr>
																<tr>
																	<td>5.</td>
																	<td>PAN & GSTIN of Applicant</td>
																	<td>${companyPanNo},${gstin}</td>
																	<td>Supported by relevant documents</td>
																</tr>
																<tr>
																	<td>6.</td>
																	<td>Status of Industrial Undertaking</td>
																	<td>${newProject}${expansion}</td>
																	<td>New /Expansion/Diversification</td>
																</tr>
																<tr>
																	<td>7.</td>
																	<td>Nature of Industry</td>
																	<td>${projectDescription}</td>
																	<td>Industrial Categorization as per ID&R Act/NIC</td>
																</tr>
																<tr>
																	<td>8.</td>
																	<td>Registration or License for setting up
																		Industrial Undertaking</td>
																	<td>${regiOrLicense}</td>
																	<td>Enclose UAM (for MSME) / IEM/IL copy (for
																		large)</td>
																</tr>
															</tbody>
														</table>
													</div>

													<div class="table-responsive">
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td>9.</td>
																	<td colspan="3">Details of Existing/Proposed
																		products to be manufactured and its capacity</td>
																</tr>
																<tr>
																	<td colspan="4">
																		<div class="table-responsive">
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Sl. No.</th>
																						<th>Existing Products</th>
																						<th>Existing Installed Capacity</th>
																						<th>Proposed Products</th>
																						<th>Proposed Installed Capacity</th>
																						<th>Existing Gross Block</th>
																						<th>Proposed Gross Block</th>
																					</tr>
																				</thead>
																				<tbody>
																					<tr>
																						<td>1</td>
																						<td>${ExistingProducts}</td>
																						<td>${existingInstalledCapacity}</td>
																						<td>${proposedProducts}</td>
																						<td>${proposedInstalledCapacity}</td>
																						<td>${existingGrossBlock }</td>
																						<td>${proposedGrossBlock}</td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td>10.</td>
																	<td>Proposed date of commencement of Commercial
																		Production</td>
																	<td style="width: 12%;">${viewInvestDetails.propCommProdDate}</td>
																	<td style="width: 12%;"></td>
																</tr>
																<tr>
																	<td>11.</td>
																	<td>Proposed Capital Investment</td>
																	<td>${viewInvestDetails.invFci}</td>
																	<td>DPR</td>
																</tr>
																<tr>
																	<td>11.1</td>
																	<td>Date from which capital investment has
																		commenced, or is proposed to be commenced (Cut-off
																		date as opted)</td>
																	<td>${viewInvestDetails.invCommenceDate}</td>
																	<td></td>
																</tr>
																<tr>
																	<td>11.2</td>
																	<td>Is the capital investment proposed in phases</td>
																	<td>${viewInvestDetails.pwApply}</td>
																	<td></td>
																</tr>
																<tr>
																	<td>11.3</td>
																	<td>Phase-wise details of Proposed Investment and
																		dates of start of commercial production</td>
																	<td>${PWFCI},</br>${PWpropsdtcoprd}</td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>

													<p class="mb-4 animate__animated animate__fadeInDown mt-3">
														<strong>12. BENEFITS REQUESTED BY APPLICANT</strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl. No.</th>
																	<th>Item</th>
																	<th>Quantum (Rs crores)</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>12.1</td>
																	<td>Aggregate Quantum of Fiscal Benefits</td>
																	<td>${aggregatequantumbenifit}</td>
																</tr>
																<tr>
																	<td colspan="3"><strong class="text-info">DETAILS
																			of BENEFITS</strong></td>
																</tr>
																<tr>
																	<td>12.2</td>
																	<td>Reimbursement of deposited GST</td>
																	<td>${ISF_Claim_Reim}</td>
																</tr>
																<tr>
																	<td>12.3</td>
																	<td>Additional Reimbursement of deposited GST</td>
																	<td>${addigst}</td>
																</tr>
																<tr>
																	<td>12.3.1</td>
																	<td>10% GST where 25% minimum SC/ST workers
																		employed subject to minimum of 400 total workers in
																		industrial undertakings located in Paschimanchal and
																		minimum of 200 total workers in B-P-M</td>
																	<td>${ISF_Reim_SCST}</td>
																</tr>
																<tr>
																	<td>12.3.2</td>
																	<td>10% GST where 40% minimum female workers
																		employed subject to minimum of 400 total workers in
																		industrial undertakings located in Paschimanchal and
																		minimum of 200 total workers in B-P-M</td>
																	<td>${ISF_Reim_FW}</td>
																</tr>
																<tr>
																	<td>12.3.3</td>
																	<td>10% GST where 25% minimum BPL workers employed
																		subject to minimum of 400 total workers in industrial
																		undertakings located in Paschimanchal and minimum of
																		200 total workers in B-P-M</td>
																	<td>${ISF_Reim_BPLW}</td>
																</tr>
																<tr>
																	<td>12.4</td>
																	<td>Stamp Duty Exemption</td>
																	<td>${ISF_Stamp_Duty_EX}</td>
																</tr>
																<tr><td colspan="3"><strong>OR</strong></td></tr>
																		<tr>
																			<td>12.4</td>
																			<td>Stamp Duty Reimbursement</td>
																			<td>${ISF_Amt_Stamp_Duty_Reim}</td>
																		</tr>
																<tr>
																	<td>12.4.1</td>
																	<td>Additional Stamp Duty exemption @20% upto
																		maximum of 100% in case of industrial undertakings
																		having 75% equity owned by Divyang/SC/ ST/Females
																		Promoters</td>
																	<td>${ISF_Additonal_Stamp_Duty_EX}</td>
																</tr>
																<tr>
																	<td>12.5</td>
																	<td>EPF Reimbursement (100 or more unskilled
																		workers)</td>
																	<td>${ISF_Epf_Reim_UW}</td>
																</tr>
																<tr>
																	<td>12.5.1</td>
																	<td>Additional 10% EPF Reimbursement (200 direct
																		skilled and unskilled workers)</td>
																	<td>${ISF_Add_Epf_Reim_SkUkW}</td>
																</tr>
																<tr>
																	<td>12.5.2</td>
																	<td>Additional 10% EPF Reimbursement upto maximum
																		of 70% in case of industrial undertakings having 75%
																		equity owned by Divyang/SC/CT/Females Promoters</td>
																	<td>${ISF_Add_Epf_Reim_DIVSCSTF}</td>
																</tr>
																<tr>
																	<td>12.6</td>
																	<td>Capital Interest Subsidy</td>
																	<td>${ISF_Cis}</td>
																</tr>
																<tr>
																	<td>12.6.1</td>
																	<td>Additional Capital Interest Subsidy@2.5% upto
																		maximum of 7.5% in case of industrial undertakings
																		having 75% equity owned by Divyang/SC/CT/Females
																		Promoters</td>
																	<td>${ISF_ACI_Subsidy_Indus}</td>
																</tr>
																<tr>
																	<td>12.7</td>
																	<td>Infrastructure Interest Subsidy</td>
																	<td>${ISF_Infra_Int_Subsidy}</td>
																</tr>
																<tr>
																	<td>12.7.1</td>
																	<td>Additional Infrastructure Interest Subsidy
																		@2.5% upto 18 maximum of 7.5% in case of industrial
																		undertakings having 75% equity owned by
																		Divyang/SC/CT/Females Promoters</td>
																	<td>${ISF_AII_Subsidy_DIVSCSTF}</td>
																</tr>
																<tr>
																	<td>12.8</td>
																	<td>Interest Subsidy on loans for industrial
																		research, quality improvement, etc.</td>
																	<td>${ISF_Loan_Subsidy}</td>
																</tr>
																<tr>
																	<td>12.9</td>
																	<td>Reimbursement of Disallowed Input Tax Credit
																		on plant, building materials, and other capital goods.</td>
																	<td>${ISF_Tax_Credit_Reim}</td>
																</tr>
																<tr>
																	<td>12.10</td>
																	<td>Exemption from Electricity Duty from captive
																		power for self-use</td>
																	<td>${ISF_EX_E_Duty}</td>
																</tr>
																<tr>
																	<td>12.11</td>
																	<td>Exemption from Electricity duty on power drawn
																		from power companies</td>
																	<td>${ISF_EX_E_Duty_PC}</td>
																</tr>
																<tr>
																	<td>12.12</td>
																	<td>Exemption from Mandi Fee</td>
																	<td>${ISF_EX_Mandee_Fee}</td>
																</tr>
															</tbody>
														</table>
													</div>

													<p
														class="mb-4 text-center animate__animated animate__fadeInDown mt-3">
														<strong>Declaration</strong>
													</p>
													<p>The above information are completely true and no
														fact has been concealed or misrepresented. It is further
														certified that the company has not applied for benefits of
														the above nature under any sector-specific or other policy
														of the Govt of UP for purpose of availing benefits of the
														above nature.</p>
													<p>I/we hereby agree that I/we shall forthwith repay
														the benefits released to me/us under Rules of Policy for
														Promotion of Industrial Investment and Employment-2017, if
														the said benefits are found to be disbursed in excess of
														the amount actually admissible whatsoever the reason.</p>

												</div>
											</div>

											<div class="row mt-4 mb-5">
												<div class="col-md-6">
													<p class="mb-0">
														<strong>Date:</strong>
													</p>
													<p>
														<strong>Place:</strong>
													</p>
												</div>
												<div class="col-md-6 text-right">
													<p class="mb-0">
														<strong>Signature of Authorised Signatory with</strong>
													</p>
													<p>
														<strong>Name, Designation and Office Seal</strong>
													</p>
												</div>
											</div>

											<div class="row mt-4 mb-5">
												<div class="col-md-12">
													<p>
														<strong>Supporting Documents:</strong>
													</p>
												</div>
												<div class="col-md-12">
													<p>(a) UAM/ IEM/IL acknowledgment</p>
													<p>(b) Detailed Project Report (DPR) prepared by
														external consultant / Chartered Accountant</p>
													<p>(c) Charted Accountant’s Certificate for existing
														gross block industrial undertaking.</p>
													<p>(d) Chartered Engineer’s Certified List of Fixed
														Assets of existing industrial undertaking in support of
														gross block.</p>
													<p>(e) Undertaking (as per format placed at Annexure
														I-A) on Stamp Paper of Rs. 10</p>
												</div>
											</div>



										</div>

										<!-- End Annexure-I  -->


										<!-- Start Annexure-III -->

										<div class="parameter-properties" id="Annexure-III">
											<hr class="mt-2">
											<h4
												class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Annexure-III</h4>
											<p
												class="mt-4 mb-1 text-center animate__animated animate__fadeInDown">
												<strong>APPLICATION FORM FOR MEGA, MEGA PLUS AND
													SUPER MEGA INDUSTRIAL UNDERTAKINGS</strong>
											</p>
											<p
												class="mb-2 text-center animate__animated animate__fadeInDown">
												<i>(all supporting documents must be authenticated by a
													Director/Partner/Officer duly authorized by the Competent
													authority of the Applicant on its behalf)</i>
											</p>
											<p
												class="mb-4 text-center animate__animated animate__fadeInDown">
												<strong>Category of Industrial Undertaking Applied
													For Based on Capital Investment, Location and Employment
													Proposed</strong>
											</p>
											<p class="mb-4 mt-5 animate__animated animate__fadeInDown">
												<strong>A. Based on Locational Investment</strong>
											</p>


											<div class="row mt-4">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Criteria for investment</th>
																	<th>Investment amount</th>
																	<th colspan="3" class="text-center">District/Region</th>
																	<th>Category of industrial undertaking</th>
																	<th>Tick as applicable</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td></td>
																	<td></td>
																	<td>Bundelkhand, Poorvanchal</td>
																	<td>Madhyanchal, Paschimanchal (except GautamBuddh
																		Nagar & Ghaziabad)</td>
																	<td>GautamBuddh Nagar & Ghaziabad</td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td rowspan="3">Capital Investment as Defined in
																		the Rules</td>
																	<td rowspan="3">Beyond the investment amount for
																		Large Industrial Undertakings and upto capital
																		investment of Rs…..,</td>
																	<td>>100<250</td>
																	<td>>150<300</td>
																	<td>>200<500</td>
																	<td>MEGA</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII1" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII1"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td>=>250<500</td>
																	<td>=>300<750</td>
																	<td>=>500<1000</td>
																	<td>MEGA PLUS</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII2" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII2"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td>=>500</td>
																	<td>=>750</td>
																	<td>=>1000</td>
																	<td>SUPER MEGA</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII3" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII3"></label>
																		</div>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>

											<p
												class="mb-4 text-center mt-2 animate__animated animate__fadeInDown">
												<strong>OR</strong>
											</p>
											<p class="mb-4 mt-2 animate__animated animate__fadeInDown">
												<strong>B. Based on Locational Employment</strong>
											</p>



											<div class="row mt-4">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>PROPOSED NO. OF WORKERS</th>
																	<th>REGION</th>
																	<th>QUALIFYING CATEGORY OF INDUSTRIAL UNDERTAKING</th>
																	<th>TICK AS APPLICABLE</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>500</td>
																	<td>Bundelkhand & Poorvanchal</td>
																	<td>MEGA</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII4" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII4"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td>750</td>
																	<td>Madhyanchal & Paschimanchal (excluding
																		GautamBuddh Nagar & Ghaziabad)</td>
																	<td>MEGA</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII5" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII5"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td rowspan="2">1000</td>
																	<td>Bundelkhand & Poorvanchal</td>
																	<td>MEGA PLUS</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII6" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII6"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td>GautamBuddh Nagar & Ghaziabad</td>
																	<td>MEGA</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII7" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII7"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td>1500</td>
																	<td>Madhyanchal & Paschimanchal (excluding
																		GautamBuddh Nagar & Ghaziabad)</td>
																	<td>MEGA PLUS</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII8" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII8"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td rowspan="2">2000</td>
																	<td>Bundelkhand & Poorvanchal</td>
																	<td>SUPER MEGA</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII9" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII9"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td>GautamBuddh Nagar & Ghaziabad</td>
																	<td>MEGA PLUS</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII10" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII10"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td>3000</td>
																	<td>Madhyanchal & Paschimanchal (excluding
																		GautamBuddh Nagar & Ghaziabad)</td>
																	<td>SUPER MEGA</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII11" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII11"></label>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td>4000</td>
																	<td>GautamBuddh Nagar & Ghaziabad</td>
																	<td>SUPER MEGA</td>
																	<td>
																		<div
																			class="custom-control custom-checkbox custom-control-inline">
																			<input type="checkbox" class="custom-control-input"
																				id="checkIII12" name="Capital-Interest-Subsidy">
																			<label class="custom-control-label" for="checkIII12"></label>
																		</div>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>

											<div class="row mt-4">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td>1.</td>
																	<td>Name, Address& Contact Details of the
																		Applicant</td>
																	<td style="width: 12%;"></td>
																	<td>Certificate of Incorporation, registered
																		partnership deed, trust /society registration deed.</td>
																</tr>
																<tr>
																	<td>2.</td>
																	<td>Constitution of Applicant</td>
																	<td></td>
																	<td>Company/Partnership
																		Firm/Others(MoA/Articles/Byelaws, etc.)</td>
																</tr>
																<tr>
																	<td>3.</td>
																	<td>Location of the Existing/proposed Industrial
																		Undertaking</td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>4.</td>
																	<td>Names, address & contact details of
																		Directors/Partners/Others</td>
																	<td></td>
																	<td>PAN & DIN numbers (Supported by relevant
																		document)</td>
																</tr>
																<tr>
																	<td>5.</td>
																	<td>PAN & GSTIN of Applicant</td>
																	<td></td>
																	<td>Supported by relevant document</td>
																</tr>
																<tr>
																	<td>6.</td>
																	<td>Status of Industrial Undertaking</td>
																	<td></td>
																	<td>New/Expansion/Diversification</td>
																</tr>
																<tr>
																	<td>7.</td>
																	<td>Nature of Industry</td>
																	<td></td>
																	<td>Industrial Categorization as per ID&R Act/NIC</td>
																</tr>
																<tr>
																	<td>8.</td>
																	<td>Registration or License for setting up
																		Industrial Undertaking</td>
																	<td></td>
																	<td>Enclose acknowledgement of IEM/ IL</td>
																</tr>

															</tbody>
														</table>
													</div>
												</div>
											</div>


											<div class="row mt-4">
												<div class="col-sm-12">
													<div>
														<div class="table-responsive">
															<table class="table table-bordered">
																<tbody>
																	<tr>
																		<td>9.</td>
																		<td colspan="6">Details of existing/proposed
																			products to be manufactured and its capacity (Enclose
																			Detailed Project Report prepared by External
																			Consultant/Chartered Accountants)</td>
																	</tr>
																	<tr>
																		<td>Sl. No.</td>
																		<td>Existing Products</td>
																		<td>Existing Installed Capacity</td>
																		<td>Proposed Products</td>
																		<td>Proposed Installed Capacity</td>
																		<td>Existing Gross Block</td>
																		<td>Proposed Gross Block</td>
																	</tr>
																	<tr>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>


											<div class="row mt-4">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td>10.</td>
																	<td>Proposed date of Commencement of Commercial
																		Production after Expansion/Diversification</td>
																	<td style="width: 12%;"></td>
																	<td></td>
																</tr>
																<tr>
																	<td>11.</td>
																	<td>Proposed Capital Investment</td>
																	<td></td>
																	<td>DPR</td>
																</tr>
																<tr>
																	<td>11.1</td>
																	<td>Date from which capital investment has 28
																		commenced, or is proposed to commence (Cut-off date,
																		as opted)</td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>11.2</td>
																	<td>Is the capital investment proposed in phases</td>
																	<td></td>
																	<td></td>
																</tr>
																<tr>
																	<td>11.3</td>
																	<td>Phase-wise details of proposed Investment and
																		dates of start of commercial production</td>
																	<td></td>
																	<td>DPR</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>


											<div class="row mt-4">
												<div class="col-sm-12">
													<p class="mb-4 animate__animated animate__fadeInDown mt-3">
														<strong>12. BENEFITS REQUESTED BY APPLICANT </strong>
													</p>
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>Sl. No.</th>
																	<th>Item</th>
																	<th>Quantum (Rs.,crores) )</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>12.1</td>
																	<td>Aggregate Quantum of Fiscal Benefits</td>
																	<td></td>
																</tr>
																<tr>
																	<td colspan="3"><strong class="text-info">BENEFITS
																			REQUESTED</strong></td>
																</tr>
																<tr>
																	<td>12.2</td>
																	<td>Reimbursement of deposited GST</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.3</td>
																	<td>Additional Reimbursement of deposited GST</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.3.1</td>
																	<td>10% SGST where 25% minimum SC/ST workers
																		employed subject to minimum of 400 total workers in
																		industrial undertakings located in Paschimanchal and
																		minimum of 200 total workers in B-P-M</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.3.2</td>
																	<td>10% SGST where 40% minimum female workers
																		employed subject to minimum of 400 total workers in
																		industrial undertakings located in Paschimanchal and
																		minimum of 200 total workers in B-P-M</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.3.3</td>
																	<td>10% SGST where 25% minimum BPL workers
																		employed subject to minimum of 400 total workers in
																		industrial undertakings located in Paschimanchal and
																		minimum of 200 total workers in B-P-M</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.4</td>
																	<td>Stamp Duty Exemption</td>
																	<td>${ISF_Stamp_Duty_EX}</td>
																	
																</tr>
																<tr><td colspan="3"><strong>OR</strong></td></tr>
																		<tr>
																			<td>12.4</td>
																			<td>Stamp Duty Reimbursement</td>
																			<td>${ISF_Amt_Stamp_Duty_Reim}</td>
																		</tr>
																<tr>
																	<td>12.4.1</td>
																	<td>Additional Stamp Duty exemption @20% upto
																		maximum of 100% in case of industrial undertakings
																		having 75% equity owned by Divyang/SC/CT/Females
																		Promoters</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.5</td>
																	<td>EPF Reimbursement (100 or more unskilled
																		workers)</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.5.1</td>
																	<td>Addl. 10% EPF Reimbursement (200 direct
																		skilled and unskilled workers)</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.5.2</td>
																	<td>Addl. 10% EPF Reimbursement upto maximum of
																		70% in case of industrial undertakings having 75%
																		equity owned by Divyang/SC/CT/Female Promoters</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.6</td>
																	<td>Capital Interest Subsidy</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.6.1</td>
																	<td>Additional Capital Interest Subsidy@2.5% upto
																		maximum of 7.5% in case of industrial undertakings
																		having 75% equity owned by Divyang/SC/CT/Females
																		Promoters</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.7</td>
																	<td>Infrastructure Interest Subsidy</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.7.1</td>
																	<td>Additional Infrastructure Interest Subsidy
																		@2.5% upto maximum of 7.5% in case of industrial
																		undertakings having 75% equity owned by
																		Divyang/SC/CT/Females Promoters</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.8</td>
																	<td>Interest Subsidy on loans for industrial
																		research, quality improvement, etc.</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.9</td>
																	<td>Reimbursement of Disallowed Input Tax Credit
																		on plant, building materials, and other capital goods.</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.10</td>
																	<td>Exemption from Electricity Duty from captive
																		power for self-use</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.11</td>
																	<td>Exemption from Electricity duty on power drawn
																		from power companies</td>
																	<td></td>
																</tr>
																<tr>
																	<td>12.12</td>
																	<td>Exemption from Mandi Fee</td>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>


											<div class="row mt-4">
												<div class="col-sm-12">
													<p
														class="mb-4 text-center animate__animated animate__fadeInDown mt-3">
														<strong>Declaration</strong>
													</p>
													<p>The above information are completely true and no
														fact has been concealed or misrepresented. It is further
														certified that the company has not applied for benefits of
														the above nature under any sector-specific or other policy
														of the Govt of UP for purpose of availing benefits of the
														above nature.</p>
													<p>I/we hereby agree that I/we shall forthwith repay
														the benefits released to me/us under Rules of Policy for
														Promotion of Industrial Investment and Employment-2017, if
														the said benefits are found to be disbursed in excess of
														the amount actually admissible whatsoever the reason.</p>

												</div>
											</div>

											<div class="row mt-4 mb-5">
												<div class="col-md-6">
													<p class="mb-0">
														<strong>Date:</strong>
													</p>
													<p>
														<strong>Place:</strong>
													</p>
												</div>
												<div class="col-md-6 text-right">
													<p class="mb-0">
														<strong>Signature of Authorised Signatory with</strong>
													</p>
													<p>
														<strong>Name, Designation and Office Seal</strong>
													</p>
												</div>
											</div>

											<div class="row mt-4 mb-5">
												<div class="col-md-12">
													<p>
														<strong>Supporting Documents:</strong>
													</p>
												</div>
												<div class="col-md-12">
													<p>(a) UAM/ IEM/IL acknowledgment</p>
													<p>(b) Detailed Project Report (DPR) prepared by
														external consultant/Chartered Accountant</p>
													<p>(c) Charted Accountant’s Certificate for existing
														gross block industrial undertaking.</p>
													<p>(d) Chartered Engineer’s Certified List of Fixed
														Assets of existing industrial undertaking in support of
														gross block.</p>
													<p>(e) Undertaking (as per format placed at Annexure
														I-A) on Stamp Paper of Rs. 10</p>
												</div>
											</div>
										</div>

										<!-- End Annexure-III  -->



										<div class="isf-form mt-4">
											<%-- <hr class="mt-4 mb-4">
										<form:form modelAttribute="downloadSupport" class="mt-4"
										name="downloadSupport" method="post">
										<div class="row mt-4">
											<div class="col-sm-12 text-center">
												<button type="button"
													class="btn btn-outline-success btn-sm mb-3">
													<i class="fa fa-download"></i> Download Only Application
													Form
												</button>
												<button type="button"
													class="btn btn-outline-info btn-sm mb-3">
													<i class="fa fa-download"></i> Download Application Form
													with Suporting Docs
												</button>
												<button type="submit"  formaction="downloadSupport" 
													class="btn btn-outline-secondary btn-sm mb-3">
													<i class="fa fa-download"></i> Download Only Supporting
													Docs
												</button>
											</div>
										</div>
									</form:form> --%>
											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-5">
													<a href="./applicationForLocSME"
														class="common-default-btn mt-3">Back</a>
												</div>
												<div class="col-sm-7 text-right">
													<a href="javacript:void(0);" id="printMethod1"
														class="common-btn mt-3"><i class="fa fa-print"></i>
														Print</a>
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
		<div class="modal fade" id="SendtoConcernDepartment">
			<div class="modal-dialog modal-lg">
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
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input"
										id="CommercialTax" name="Capital-Interest-Subsidy"> <label
										class="custom-control-label" for="CommercialTax">Commercial
										Tax</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input" id="UPPCL"
										name="Capital-Interest-Subsidy"> <label
										class="custom-control-label" for="UPPCL">UPPCL</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input"
										id="StampRegistration" name="Capital-Interest-Subsidy">
									<label class="custom-control-label" for="StampRegistration">Stamp
										& Registration</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input"
										id="MandiParishad" name="Capital-Interest-Subsidy"> <label
										class="custom-control-label" for="MandiParishad">Mandi
										Parishad</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input"
										id="LabourDepartment" name="Capital-Interest-Subsidy">
									<label class="custom-control-label" for="LabourDepartment">Labour
										Department</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input" id="DIC"
										name="Capital-Interest-Subsidy"> <label
										class="custom-control-label" for="DIC">DIC</label>
								</div>
							</div>
						</div>
						<div class="row mt-4">
							<div class="col-sm-12">
								<div class="form-group">
									<label class="table-heading">Add Other Department <small>(If
											Any)</small></label>
									<hr>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<label>Department Name</label> <input type="text"
										class="form-control" name="">
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label>Email</label> <input type="email" class="form-control"
										name="">
								</div>
							</div>
							<div class="col-sm-12 text-right">
								<div class="form-group">
									<button type="button"
										class="btn btn-outline-success btn-sm mb-3">Save</button>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="table-responsive">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>Department Name</th>
												<th>Email</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>Department-1</td>
												<td>info@domain.com</td>
												<td><a href="javascript:void(0);" class="remove-row"
													data-toggle="tooltip" title="" data-original-title="Delete"><i
														class="fa fa-trash text-danger"></i></a></td>
											</tr>
											<tr>
												<td>Department-2</td>
												<td>info@domain.com</td>
												<td><a href="javascript:void(0);" class="remove-row"
													data-toggle="tooltip" title="" data-original-title="Delete"><i
														class="fa fa-trash text-danger"></i></a></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<a href="#QueryReply" class="common-btn mt-0" data-toggle="modal"
							data-dismiss="modal">Submit</a>
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