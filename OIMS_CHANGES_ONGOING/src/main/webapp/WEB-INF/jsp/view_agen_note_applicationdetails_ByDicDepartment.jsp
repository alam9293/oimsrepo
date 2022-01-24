<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<script type="text/javascript">
	window.onload = function() {

		if ('${gender}' === 'Male') {
			$('input:radio[name=gender][value=Male]').attr('checked', true);
		} else if ('${gender}' === 'Female') {
			$('input:radio[name=gender][value=Female]').attr('checked', true);
		} else {
			$('input:radio[name=gender][value=Transgender]').attr('checked',
					true);
		}

		if ('${regiOrLicense}' === 'EncloseUAM') {
			$('input:radio[name=regiOrLicense][value=EncloseUAM]').attr(
					'checked', true);
		} else {
			$('input:radio[name=regiOrLicense][value=IEMcopy]').attr('checked',
					true);
		}
		if ('${natureOfProject}' == 'NewProject') {
			$('select').find('option[value=NewProject]').attr('selected',
					'selected');
			document.getElementById('hideById').style.display = 'none';
			document.getElementById('hideByRowId1').style.display = 'none';
			document.getElementById('hideByRowId2').style.display = 'none';
			var element = document.getElementById("hideByRowId3");
			element.classList.remove("hide");
			document.getElementById('hideByRowId4').style.display = 'block';
			document.getElementById('natureOfProjectDiv').style.display = 'none';

		}
		if ('${natureOfProject}' == 'ExistingProject') {
			$('select').find('option[value=ExistingProject]').attr('selected',
					'selected');
			document.getElementById('hideById').style.display = 'block';
			var element = document.getElementById("hideByRowId1");
			element.classList.remove("hide");
			var element = document.getElementById("hideByRowId2");
			element.classList.remove("hide");
			var element = document.getElementById("hideByRowId3");
			element.classList.remove("hide");
			document.getElementById('hideByRowId4').style.display = 'block';
			document.getElementById('natureOfProjectDiv').style.display = 'block';

			if ('${expansion}' == 'Expansion') {
				$('input:checkbox[name="expansion"][value="Expansion"]').prop(
						'checked', true);
			}
			if ('${diversification}' == 'Diversification') {
				$(
						'input:checkbox[name="diversification"][value="Diversification"]')
						.prop('checked', true);
			}
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
							pdf.addHTML(document.getElementById("printform"),
									options, function() {
										pdf.save("AFIFFORM.pdf");
									});
						});
			});
</script>
<style type="text/css">
@media screen , print {
	body {
		background: #fff;
	}
	#content2 {
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
							href="./dicDepartmentDashboard"><i class="fa fa-tachometer"></i>
								Dashboard</a></li>

						<li class="nav-item"><a class="nav-link"
							href="select-policy-dic.html"><i class="fa fa-eye"></i> View
								SME Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./queryRaisedDIC"><i class="fa fa-question-circle"></i>
								Query Raised</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewQueryResponseDIC"><i
								class="fa fa-question-circle"></i> Query Response By
								Enterpreneur</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./prepareAgendaNoteDicList"><i class="fa fa-list"></i>
								Prepare Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link"
							href="meeting-schedule-dic.html"><i class="fa fa-calendar"></i>
								Schedule meeting</a></li>
						<li class="nav-item"><a class="nav-link"
							href="mom-go-dic.html"><i class="fa fa-calendar"></i> Minutes
								of Meeting and GO's</a></li>
						<li class="nav-item"><a class="nav-link"
							href="generate-LOC-dic.html"><i class="fa fa-wpforms"></i>
								Generate LOC</a></li>
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
												<a href="./prepareAgendaNoteDicList"
													class="common-default-btn mt-0">Back</a> <a
													href="javascript:void(0);" id="printMethod"
													class="common-btn mt-0"><i class="fa fa-print"></i>
													Print</a>
											</div>
											<div class="col-sm-4 text-right">
												<div class="form-group">
													<select class="form-control">
														<option value="1">View as per entrepreneur</option>
														<option value="2">View as per policy LOC form</option>
													</select>
												</div>
											</div>
										</div>
										<hr>
										<form:form modelAttribute="downloadSupport" class="mt-4"
											name="downloadSupport" method="post">
										<div class="row mt-4">
											<div class="col-sm-12 text-center">
												<button type="button" id="formId"
													class="btn btn-outline-success btn-sm mb-3">
													<i class="fa fa-download"></i> Download Only Application
													Form
												</button>
												<button type="button"
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


										<div id="printform">
											<form:form action="saveApplicantDetails" class="isf-form"
												method="get" modelAttribute="applicantDetail"
												enctype="multipart/form-data">
												<hr class="mt-2">
												<h4
													class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Authorised
													Signatory Details</h4>
												<div class="isf-form mt-4">
													<div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">Authorised Signatory
																Details</h3>
														</div>
													</div>
													<div class="row">
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>First Name</label> <input type="text"
																	class="form-control" name="appFirstName"
																	id="appFirstNameId" value="${appFirstName}"
																	disabled="disabled">
															</div>
														</div>
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Middle Name</label> <input type="text"
																	class="form-control" value="${appMiddleName}"
																	disabled="disabled" id="appMiddleNameId"
																	name="appMiddleName">
															</div>
														</div>
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Last Name</label> <input type="text"
																	class="form-control" value="${appLastName}"
																	disabled="disabled" id="appLastNameId"
																	name="appLastName">
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Email ID</label> <input type="text"
																	class="form-control" value="${appEmailId}"
																	disabled="disabled" id="appEmailId" name="appEmailId">
															</div>
														</div>
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Mobile No:<small>(+91)</small></label> <input
																	type="text" class="form-control" value="${appMobileNo}"
																	disabled="disabled" id="appMobileNoId"
																	name="appMobileNo">
															</div>
														</div>
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Phone No. (Landline)</label> <input type="text"
																	class="form-control" value="${appPhoneNo}"
																	disabled="disabled" id="appPhoneNo" name="appPhoneNo">
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Designation</label> <input type="text"
																	class="form-control" value="${appDesignation}"
																	disabled="disabled" id="appDesignation"
																	name="appDesignation">
															</div>
														</div>
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<div class="gender-label">
																	<label>Gender<span>*</span></label>
																</div>
																<div
																	class="custom-control custom-radio custom-control-inline">
																	<input type="radio" class="custom-control-input"
																		id="MaleOpt" disabled="disabled" name="gender"
																		value="Male"> <label
																		class="custom-control-label" for="MaleOpt">Male</label>
																</div>
																<div
																	class="custom-control custom-radio custom-control-inline">
																	<input type="radio" class="custom-control-input"
																		id="FemaleOpt" disabled="disabled" name="gender"
																		value="Female"> <label
																		class="custom-control-label" for="FemaleOpt">Female</label>
																</div>
																<div
																	class="custom-control custom-radio custom-control-inline">
																	<input type="radio" class="custom-control-input"
																		id="Transgender" disabled="disabled" name="gender"
																		value="Transgender"> <label
																		class="custom-control-label" for="Transgender">Transgender
																	</label>
																</div>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-sm-12 mt-4">
															<h3 class="common-heading">Aadhaar & PAN Number</h3>
														</div>
													</div>
													<div class="row">
														<div class="col-md-6">
															<div class="form-group">
																<label>Aadhaar Number</label>
																<div class="form-group">
																	<input type="text" value="${appAadharNo}"
																		disabled="disabled" class="form-control"
																		name="appAadharNo">
																</div>
															</div>
														</div>
														<div class="col-md-6">
															<div class="form-group">
																<label>PAN Number</label>
																<div class="form-group">
																	<input type="text" value="${appPancardNo}"
																		disabled="disabled" class="form-control"
																		name="appPancardNo">
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
																<label>Address Line - 1</label> <input type="text"
																	class="form-control" value="${appAddressLine1}"
																	disabled="disabled" id="appAddressLine1"
																	name="appAddressLine1">
															</div>
														</div>
														<div class="col-sm-6">
															<div class="form-group">
																<label>Address Line - 2</label> <input type="text"
																	class="form-control" value="${appAddressLine2}"
																	disabled="disabled" id="appAddressLine2"
																	name="appAddressLine2">
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Country</label> <input type="text"
																	value="${appCountry}" disabled="disabled"
																	class="form-control" name="appCountry">
															</div>
														</div>
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>State/UT</label> <input type="text"
																	value="${appState}" disabled="disabled"
																	class="form-control" name="appState">
															</div>
														</div>
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>District</label> <input type="text"
																	value="${appDistrict}" disabled="disabled"
																	class="form-control" name="appDistrict">
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Pin Code</label> <input type="text"
																	class="form-control" value="${appPinCode}"
																	disabled="disabled" id="appPinCode" name="appPinCode">
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
														<div class="col-sm-6 text-left">
															<img
																src="data:image/png;base64,${applicantDetail.base64imageFile}" />
														</div>
													</div>
													<hr>
												</div>
											</form:form>
											<h4
												class="card-title mb-4 mt-5 text-center animate__animated animate__fadeInDown">Business
												Entity Details</h4>
											<div class="isf-form mt-4" id="content">
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
															<label>Name of Contact Person</label> <input type="text"
																class="form-control" value="${contactPersonName}"
																disabled="disabled" id="contactPersonName"
																name="contactPersonName">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Mobile No:(+91)</label> <input type="text"
																class="form-control" value="${mobileNo}"
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
															<label>Project Description</label>
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
															<label>Full Address</label> <input type="text"
																class="form-control" value="${fullAddress}"
																disabled="disabled" id="fullAddress" name="fullAddress">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>District</label> <input type="text"
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
															<label>Pin Code</label> <input type="text"
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
														<h3 class="common-heading">Select Nature of the
															project</h3>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-6">
														<div class="form-group">
															<label>Select Nature of the project<span
																class="text-danger">*</span></label> <select
																class="form-control" id="natureOfProjectId"
																disabled="disabled">
																<option value="">Select One</option>
																<option value="NewProject">New Project</option>
																<option value="ExistingProject">Existing
																	Project</option>
															</select>

														</div>
													</div>
													<div class="col-sm-12" id="natureOfProjectDiv">
														<div class="form-group mt-3">
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
																<label class="custom-control-label"
																	for="Diversification">Diversification</label>
															</div>

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
																					value="${enclDetProRepFileName}"
																					disabled="disabled" id="enclDetProRepFileName"
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
																					value="${caCertificateFileName}"
																					disabled="disabled" id="caCertificateFileName"
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
												<div class="row">
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Category of Industrial Undertaking<span>*</span></label>
															<select class="form-control" disabled="disabled">
																<option value="${invIndType}">${invIndType}</option>
															</select>
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Proposed Fixed Capital Investment<span>*</span></label>
															<input type="text" class="form-control" value="${invFci}"
																disabled="disabled" id="invFci" name="invFci">
														</div>
													</div>
													<div class="col-md-6 col-lg-4 col-xl-4">
														<div class="form-group">
															<label>Total Cost of Project<span>*</span></label> <input
																type="text" class="form-control"
																value="${invTotalProjCost}" disabled="disabled"
																id="invTotalProjCost" name="invTotalProjCost">
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
															<label>Land Cost<span>*</span>
															</label> <input type="text" class="form-control"
																value="${invLandCost}" disabled="disabled"
																id="invLandCost" name="invLandCost">
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label>Building Site Development & Civil Works
																Cost <span>*</span>
															</label> <input type="text" class="form-control"
																value="${invBuildingCost}" disabled="disabled"
																id="invBuildingCost" name="invBuildingCost">
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label>Plant & Machinery Cost <span>*</span></label> <input
																type="text" class="form-control"
																value="${invPlantAndMachCost}" disabled="disabled"
																id="invPlantAndMachCost" name="invPlantAndMachCost">
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label>Miscellaneous Fixed Asset(MFA) <span>*</span></label>
															<input type="text" class="form-control"
																value="${invOtherCost}" disabled="disabled"
																id="invOtherCost" name="invOtherCost">
														</div>
													</div>
												</div>

												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label>Indicate Opted Cut-off date investment <span>*</span></label>
															<input type="text" class="form-control"
																value="${invCommenceDate}" disabled="disabled"
																name="invCommenceDate">
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label>Date of Commencement of Commercial
																Production <span>*</span>
															</label> <input type="text" class="form-control"
																value="${propCommProdDate}" disabled="disabled"
																name="propCommProdDate">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-12 mt-4">
														<h3 class="common-heading">Phase Wise Investment</h3>
													</div>
												</div>
												<div class="row">
													<div class="col-md-7">
														<div class="form-group">
															<label>Phase Wise Investment as per Detailed
																Project Report (If Applicable)<span>*</span>
															</label> <select class="form-control" disabled="disabled">
																<option value="${pwApply}">${pwApply}</option>
															</select>
														</div>
													</div>
												</div>
												<c:if test="${not empty pwInvList}">
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive mt-3">
																<table class="table table-stripped table-bordered">
																	<thead>
																		<tr>
																			<th>Sr. No.</th>
																			<th>Name of the Phase</th>
																			<th>Proposed Fixed Capital Investment</th>
																			<th>Proposed Date of Commercial Production</th>



																		</tr>
																	</thead>

																	<%
																		int k = 0;
																	%>
																	<tbody>
																		<c:forEach var="pwInvObj" items="${pwInvList}">
																			<!-- Iterating the list using JSTL tag  -->
																			<c:if test="${not empty pwInvList}">
																				<c:if test="${fn:length(pwInvList)>0}">
																					<tr>
																						<td class="text-center"><%=++k%></td>
																						<td>${pwInvObj.pwProductName}</td>
																						<td>${pwInvObj.pwFci}</td>
																						<td>${pwInvObj.pwPropProductDate}</td>

																					</tr>
																				</c:if>
																			</c:if>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</c:if>
												<hr class="mt-4 mb-4">
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
																<label>Skilled Male Employees</label> <input type="text"
																	class="form-control" value="${unSkilledEmploymentMale}"
																	disabled="disabled">
															</div>
														</div>
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Skilled Female Employees</label> <input
																	type="text" class="form-control"
																	value="${unSkilledEmploymentFemale}"
																	disabled="disabled">
															</div>
														</div>
														<div class="col-md-6 col-lg-4 col-xl-4">
															<div class="form-group">
																<label>Total Skilled Employees</label> <input
																	type="text" class="form-control"
																	value="${totalUnSkilledEmployment}" disabled="disabled">
															</div>
														</div>
													</div>
												</c:if>
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
											</div>

											<hr class="mt-4">

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
																			<label>Additional
																					10% GST where 25% minimum SC/ST workers employed
																					subject to minimum of 400 total workers in
																					industrial undertakings located in Paschimanchal
																					and minimum of 200 total workers in B-P-M</label>
																			
																		</td>
																		<td><input type="text" class="form-control"
																			value="${ISF_Reim_SCST}" disabled="disabled"
																			name="ISF_Reim_SCST"></td>
																	</tr>
																	<tr>
																		<td>
																			<label>Additional
																					10% GST where 40% minimum female workers employed
																					subject to minimum of 400 total workers in
																					industrial undertakings located in Paschimanchal
																					and minimum of 200 total workers in B-P-M</label>
																			
																		</td>
																		<td><input type="text" class="form-control"
																			value="${ISF_Reim_FW}" disabled="disabled"
																			name="ISF_Reim_FW"></td>
																	</tr>
																	<tr>
																		<td>
																			<label>Additional
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
																			 <label>Additional
																					Stamp Duty exemption @20% upto maximum of 100% in
																					case of industrial undertakings having 75% equity
																					owned by Divyang/SC/ ST/Females Promoters</label>
																		
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
																			<label>Additional
																					10% EPF Reimbursement (200 direct skilled and
																					unskilled workers)</label>
																		
																		</td>
																		<td><input type="text" class="form-control"
																			value="${ISF_Add_Epf_Reim_SkUkW}" disabled="disabled"
																			name="ISF_Add_Epf_Reim_SkUkW"></td>
																	</tr>
																	<tr>
																		<td>
																		 <label>Additional
																					10% EPF Reimbursement upto maximum of 70% in case
																					of industrial undertakings having 75% equity owned
																					by Divyang/SC/ST/Females Promoters</label>
																		
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
																			value="${ISF_Cis}" disabled="disabled" name="ISF_Cis"></td>
																	</tr>
																	<tr>
																		<td>
																			<label>Additional
																					Capital Interest Subsidy@2.5% upto maximum of 7.5%
																					in case of industrial undertakings having 75%
																					equity owned by Divyang/SC/ST/Females Promoters</label>
																		
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
																			 <label>Additional
																					Infrastructure Interest Subsidy @2.5% upto maximum
																					of 7.5% in case of industrial undertakings having
																					75% equity owned by Divyang/SC/ST/Females Promoters</label>
																			
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
																			assistance of Rs. 500 per month for each such worker.</td>
																		<td><input type="text" class="form-control"
																			value="${ISF_Indus_Payroll_Asst}" disabled="disabled"
																			name="ISF_Indus_Payroll_Asst"></td>
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
																	format less than 500 KB)</small> <img src="images/pdf-icon.png"
																class="pdf-icon" alt="pdf-icon"></label>
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

										<div class="isf-form mt-4">
											<!-- <hr class="mt-4 mb-4">
                                            <div class="row mt-4">
                                                <div class="col-sm-12 text-center">
                                                    <button type="button" class="btn btn-outline-success btn-sm mb-3"><i class="fa fa-download"></i> Download Only Application Form</button>
                                                    <button type="button" class="btn btn-outline-info btn-sm mb-3"><i class="fa fa-download"></i> Download Application Form with Suporting Docs</button>
                                                    <button type="button" class="btn btn-outline-secondary btn-sm mb-3"><i class="fa fa-download"></i> Download Only Supporting Docs</button>
                                                </div>
                                            </div> -->
											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-5">
													<a href="./prepareAgendaNoteDicList"
														class="common-default-btn mt-3">Back</a>
												</div>
												<div class="col-sm-7 text-right">
													<a href="javacript:void(0);" id="printMethod1"
														onclick="printDiv('printableArea')"
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