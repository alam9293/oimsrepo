
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
<!-- Required meta tags Sachin-->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>View All Applicant Details</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
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
<script src="js/custom.js"></script>


<!-- <script>
	$(document)
			.ready(
					function() {
						document.getElementById('chooseInvfile').innerHTML = '${viewInvestDetails.invFilename}';
					});
</script> -->

<script>
 $(document).ready(function() {										
	 var cat = '${categorytype}';
	 var districtName='${districtName}';
		if (cat != null && cat != '') {
			if (cat === "Small") {

				$(
						'input:checkbox[name="BP_Checkbox"][value="${categorytype}"]')
						.prop('checked', true);
			}

			else if (cat === "Medium") {

				$(
						'input:checkbox[name="MP_Checkbox"][value="${categorytype}"]')
						.prop('checked', true);
			}

			else if (cat === "Large") {

				var resionName = '${resionName}';
				var cat = '${categorytype}';
				var districtName='${districtName}';
				
					if (resionName === "Bundelkhand" || resionName === "Poorvanchal") {
						$('input:checkbox[name="BP_Checkbox"][value="${categorytype}"]')
								.prop('checked', true);
					}

					else if ((resionName === "Madhyanchal"
							|| resionName === "Paschimanchal") && !(districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD')) {
						$('input:checkbox[name="MP_Checkbox"][value="${categorytype}"]')
								.prop('checked', true);
					}

					else if (districtName === "GAUTAM BUDH NAGAR"
							|| districtName === "GHAZIABAD") {
						$(
								'input:checkbox[name="GBNG_Checkbox"][value="${categorytype}"]')
								.prop('checked', true);
					}
				
			}
			else if (cat === "Mega") {
				$(
						'input:checkbox[name="Mega_Checkbox"][value="${categorytype}"]')
						.prop('checked', true);
			}

			else if (cat === "Mega Plus") {

				$(
						'input:checkbox[name="Mega_Plus_Checkbox"][value="${categorytype}"]')
						.prop('checked', true);
			}

			else if (cat === "Super Mega") {

				$(
						'input:checkbox[name="Super_Mega_Checkbox"][value="${categorytype}"]')
						.prop('checked', true);
			}

		}	
	
		var pwapply='${pwApply}';
		if (pwapply == 'No') {
			$(".pwapply-row").hide();
		}

		if (cat === "Mega") {

			var resionName = '${resionName}';
			var cat = '${categorytype}';
			
				if (resionName === "Bundelkhand" || resionName === "Poorvanchal") {
					$('input:checkbox[name="BP_M"][value="${categorytype}"]')
							.prop('checked', true);
				}
				else if ((resionName === "Madhyanchal" || resionName === "Paschimanchal") 
						&& !(districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD')) {
			
					$('input:checkbox[name="MP_M"][value="${categorytype}"]')
							.prop('checked', true);
				}

				else if (districtName === "GAUTAM BUDH NAGAR"
						|| districtName === "GHAZIABAD") {
					$(
							'input:checkbox[name="GG_M"][value="${categorytype}"]')
							.prop('checked', true);
				}
			
		}	
		if (cat === "Mega Plus") {

			var resionName = '${resionName}';
			var cat = '${categorytype}';
			
				if (resionName === "Bundelkhand" || resionName === "Poorvanchal") {
					$('input:checkbox[name="BP_MP"][value="${categorytype}"]')
							.prop('checked', true);
				}

				
	else if ((resionName === "Madhyanchal" || resionName === "Paschimanchal")
									&& !(districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD')) {

								$(
										'input:checkbox[name="MP_MP"][value="${categorytype}"]')
										.prop('checked', true);
							}

							else if (districtName === "GAUTAM BUDH NAGAR"
									|| districtName === "GHAZIABAD") {
								$(
										'input:checkbox[name="GBNG_MP"][value="${categorytype}"]')
										.prop('checked', true);
							}

						}

						if (cat === "Super_Mega") {

							var resionName = '${resionName}';
							var cat = '${categorytype}';

							if (resionName === "Bundelkhand"
									|| resionName === "Poorvanchal") {
								$(
										'input:checkbox[name="BP_SM"][value="${categorytype}"]')
										.prop('checked', true);
							}

							else if ((resionName === "Madhyanchal"
								|| resionName === "Paschimanchal") && !(districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD')) {
							$(
										'input:checkbox[name="MP_SM"][value="${categorytype}"]')
										.prop('checked', true);
							}

							else if (districtName === "GAUTAM BUDH NAGAR"
									|| districtName === "GHAZIABAD") {
								$(
										'input:checkbox[name="GBNG_SM"][value="${categorytype}"]')
										.prop('checked', true);
							}

						}
					});
</script>

<script type="text/javascript">

	function validateDepartment() {
		return (event.charCode > 64 && event.charCode < 91)
				|| (event.charCode > 96 && event.charCode < 123)
				|| event.charCode == 32 || event.charCode > 47
				&& event.charCode < 58
	}

	function validateEmail() {
		var email = document.getElementById("deptEmailid").value;
		if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
				.test(email)) {
			document.getElementById('deptemail').innerHTML = "";
			return true;
		} else {
			document.getElementById('deptemail').innerHTML = "Invalid email address!";
			document.getElementById('deptEmailid').focus();
			return false;
		}

	}

	

	window.onload = function() {

		
		var resionName = '${resionName}';
		var cat = '${categorytype}';
		var districtName='${districtName}';
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
		

	

    $(document).on('click', '#printMethod', function(e) {
		e.preventDefault();

		var $this = $(this);
		var originalContent = $('body').html();
		var printArea = $this.parents('#printpdf').html();

		$('body').html(printArea);
		 var backid1 = document.getElementById("backid1");
		 var printMethod = document.getElementById("printMethod");
		 var SelectParameterId = document.getElementById("SelectParameterId");
		 var formHideIds = document.getElementById("formHideIds");
		 var hideFields = document.getElementById("hideFields");		 				
		backid1.style.visibility = 'hidden';
		printMethod.style.visibility = 'hidden';
		SelectParameterId.style.visibility = 'hidden';
		formHideIds.style.visibility = 'hidden';
		hideFields.style.visibility = 'hidden';
		
		
		window.print();
		$('body').html(originalContent);
	});
	 
</script>

<!-- <script>
	$(document).ready (function() {
						$(':radio:not(:checked)').attr('disabled', true);

						var photoFile = document.getElementById("photofile").value;
						//document.getElementById("photofile").value = "${applicantDetails.fileName}";
						if ("${signatoryDetail.base64imageFile}" == null
								|| "${signatoryDetail.base64imageFile}" == '') {

						} else {
							document.getElementById("preview-photo").src = "data:image/png;base64,${signatoryDetail.base64imageFile}";
							document.getElementById('choosefile').innerHTML = '${signatoryDetail.fileName}';
							photoFile = "${signatoryDetail.fileName}";
						}
					});
</script>-->



</head>

<body>
	<button onclick="topFunction()" id="GoToTopBtn" title="Go to top">Top</button>
	<section class="inner-header">
		
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
						<li class="nav-item"><a class="nav-link"
							href=""><i class="fa fa-eye"></i>
								View Circulated Draft LOC</a></li>
				</ul>
			</div>
			<!--/col-->
			<div class="col-md-9 col-lg-10 mt-4 main mb-4" id="printpdf">
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
											<a href="./deptApplicationForLoc" class="common-default-btn mt-0" id="backid1">Back</a>
											<button type="button" id="printMethod"	class="common-default-btn mt-0" ><i class="fa fa-print"></i> Print</button>
											<!--  <a	href="./deptEvaluateApplication"
												onclick="return confirm('Are you sure, you want to evaluate application ?')"
												class="common-btn mt-3">Evaluate Application </a>
											<button type="button" class="btn btn-outline-info btn-sm"
												data-target="#SendtoConcernDepartment" data-toggle="modal">Send
												to Concern Department</button>
 -->										</div>
										<div class="col-sm-4 text-right" id="SelectParameterId">
											<div class="form-group">
												<select class="form-control" id="SelectParameter">
													<option value="viewAllApplicationDetails">View as
														per entrepreneur</option>
													<option value="Annexure-I">View as per policy LOC
														form Annexure-I</option>
													<option value="Annexure-III">View as per policy
														LOC form Annexure-III</option>
												</select>
											</div>
										</div>
									</div>
									<hr>
									<form:form modelAttribute="downloadSupportCD" class="mt-4"
										name="downloadSupportCD" method="post" id="formHideIds">
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




									<!-- Start view All Application Details -->

	
										<div id="content2">	
									 	<div class="parameter-properties" id="viewAllApplicationDetails">
										<hr class="mt-2">										
										<c:import url="/WEB-INF/jsp/preview_evaluation.jsp">
										</c:import>
										</div>
										
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
											INCENTIVES FOR SMALL, MEDIUM & LARGE INDUSTRIAL UNDERTAKINGS</strong>
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
															<td rowspan="2">Investment in Plant and Machinery as
																defined in MSMED Act, 2006</td>
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
																		id="Small-p" value="${categorytype}" name="Small_Checkbox"> <label
																		class="custom-control-label" for="Small-p"></label>
																</div>
															</td>
															<td class="text-center">
																<div
																	class="custom-control custom-checkbox custom-control-inline">
																	<input type="checkbox" class="custom-control-input"
																		id="Medium-p" value="${categorytype}" name="Medium_Checkbox">
																	<label class="custom-control-label" for="Medium-p"></label>
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
																Plant & Machinery for Medium Industrial Undertakings (Rs
																10 Crore) and upto capital investment Rs</td>
															<td>Bundelkhand & Poorvanchal Investment amount upto
																Rs 100 Crore</td>
															<td>Madhyanchal & Paschimanchal (except Gautam Buddh
																Nagar & Ghaziabad) <br>Investment amount upto Rs
																150 Crore
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
																		name="BP_Checkbox"> <label
																		class="custom-control-label" for="Bundelkhand"></label>
																</div>
															</td>
															<td class="text-center">
																<div
																	class="custom-control custom-checkbox custom-control-inline">
																	<input type="checkbox" class="custom-control-input"
																		id="Madhyanchal" value="${categorytype}"
																		name="MP_Checkbox"> <label
																		class="custom-control-label" for="Madhyanchal"></label>
																</div>
															</td>
															<td class="text-center">
																<div
																	class="custom-control custom-checkbox custom-control-inline">
																	<input type="checkbox" class="custom-control-input"
																		id="Ghaziabad" value="${categorytype}"
																		name="GBNG_Checkbox"> <label
																		class="custom-control-label" for="Ghaziabad"></label>
																</div>
															</td>
														</tr>
														<tr>
															<td>Investment amount</td>
																	<td colspan="3" id="fciL">${invFci}</td>
														</tr>
														<tr>
															<td>District/ Area</td>
																	<td colspan="3"><span id="districtNameL">${districtName}</span> /
																		<span id="resionNameL">${resionName}</span></td>
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
															<th style="width: 30%;">Relevant Documentary Support</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>1.</td>
															<td>Name, Address& Contact Details of the Applicant</td>
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
															<td>${fullAddress}</td>
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
																				items="${ProprietorDetailsList}" varStatus="counter">
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
															<td>Enclose UAM (for MSME) / IEM/IL copy (for large)</td>
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
															<td style="width: 12%;">${propCommProdDate}</td>
															<td style="width: 12%;"></td>
														</tr>
														<tr>
															<td>11.</td>
															<td>Proposed Capital Investment</td>
															<td>${invFci}</td>
															<td>DPR</td>
														</tr>
														<tr>
															<td>11.1</td>
															<td>Date from which capital investment has
																commenced, or is proposed to be commenced (Cut-off
																date as opted)</td>
															<td>${invCommenceDate}</td>
															<td></td>
														</tr>
														<tr>
															<td>11.2</td>
															<td>Is the capital investment proposed in phases</td>
															<td>${pwApply}</td>
															<td></td>
														</tr>
														<tr>
															<td>11.3</td>
															<td>Phase-wise details of Proposed Investment and
																dates of start of commercial production</td>
															<td>${invCommenceDate}, 
															<c:forEach var="pwInvObj" items="${pwInvList}">
															${pwInvObj.pwPropProductDate}
															</c:forEach>									
															</td>
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
															<td>${total}</td>
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
															<td>10% GST where 25% minimum SC/ST workers employed
																subject to minimum of 400 total workers in industrial
																undertakings located in Paschimanchal and minimum of 200
																total workers in B-P-M</td>
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
																undertakings located in Paschimanchal and minimum of 200
																total workers in B-P-M</td>
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
															<td>Additional 10% EPF Reimbursement upto maximum of
																70% in case of industrial undertakings having 75% equity
																owned by Divyang/SC/CT/Females Promoters</td>
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
															<td>Additional Infrastructure Interest Subsidy @2.5%
																upto 18 maximum of 7.5% in case of industrial
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
															<td>Reimbursement of Disallowed Input Tax Credit on
																plant, building materials, and other capital goods.</td>
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
											<p>The above information are completely true and no fact
												has been concealed or misrepresented. It is further
												certified that the company has not applied for benefits of
												the above nature under any sector-specific or other policy
												of the Govt of UP for purpose of availing benefits of the
												above nature.</p>
											<p>I/we hereby agree that I/we shall forthwith repay the
												benefits released to me/us under Rules of Policy for
												Promotion of Industrial Investment and Employment-2017, if
												the said benefits are found to be disbursed in excess of the
												amount actually admissible whatsoever the reason.</p>

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
											<p>(b) Detailed Project Report (DPR) prepared by external
												consultant / Chartered Accountant</p>
											<p>(c) Charted Accountant’s Certificate for existing
												gross block industrial undertaking.</p>
											<p>(d) Chartered Engineer’s Certified List of Fixed
												Assets of existing industrial undertaking in support of
												gross block.</p>
											<p>(e) Undertaking (as per format placed at Annexure I-A)
												on Stamp Paper of Rs. 10</p>
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
															<td rowspan="3">Capital Investment as Defined in the
																Rules</td>
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
																		id="checkIII1" value="${categorytype}"
																		name="Mega_Checkbox"> <label
																		class="custom-control-label" for="checkIII1"></label>
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
																		id="checkIII2" value="${categorytype}"
																		name="Mega_Plus_Checkbox"> <label
																		class="custom-control-label" for="checkIII2"></label>
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
																		id="checkIII3" value="${categorytype}"
																		name="Super_Mega_Checkbox"> <label
																		class="custom-control-label" for="checkIII3"></label>
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
																		id="checkIII4" value="${categorytype}" name="BP_M">
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
																		id="checkIII5" value="${categorytype}" name="MP_M">
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
																		id="checkIII6" value="${categorytype}" name="BP_MP">
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
																		id="checkIII7" value="${categorytype}" name="GG_M">
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
																		id="checkIII8" value="${categorytype}" name="MP_MP">
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
																		id="checkIII9" value="${categorytype}" name="BP_SM">
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
																		id="checkIII10" value="${categorytype}" name="GG_MP">
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
																		id="checkIII11" value="${categorytype}" name="MP_SM">
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
																		id="checkIII12" value="${categorytype}" name="GG_SM">
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
													<thead>
														<tr>
															<th style="width: 7%;">Sl No</th>
															<th style="width: 27%;">Particulars</th>
															<th>Details</th>
															<th style="width: 30%;">Relevant Documentary Support</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>1.</td>
															<td>Name, Address& Contact Details of the Applicant</td>
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
															<td>${fullAddress}</td>
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
																				items="${ProprietorDetailsList}" varStatus="counter">
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
															<td>PAN& DIN numbers (Supported by relevant
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
															<td>Enclose UAM (for MSME) / IEM/IL copy (for large)</td>
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
															<td style="width: 12%;">${propCommProdDate}</td>
															<td style="width: 12%;"></td>
														</tr>
														<tr>
															<td>11.</td>
															<td>Proposed Capital Investment</td>
															<td>${invFci}</td>
															<td>DPR</td>
														</tr>
														<tr>
															<td>11.1</td>
															<td>Date from which capital investment has
																commenced, or is proposed to be commenced (Cut-off
																date as opted)</td>
															<td>${invCommenceDate}</td>
															<td></td>
														</tr>
														<tr>
															<td>11.2</td>
															<td>Is the capital investment proposed in phases</td>
															<td>${pwApply}</td>
															<td></td>
														</tr>
														<tr>
															
															<td>11.3</td>
															<td>Phase-wise details of Proposed Investment and
																dates of start of commercial production</td>
															<td>${invCommenceDate}, 
															<c:forEach var="pwInvObj" items="${pwInvList}">
															${pwInvObj.pwPropProductDate}
															</c:forEach>									
															</td>
															<td></td>
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
															<th>Quantum (Rs crores)</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>12.1</td>
															<td>Aggregate Quantum of Fiscal Benefits</td>
															<td>${total}</td>
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
															<td>10% GST where 25% minimum SC/ST workers employed
																subject to minimum of 400 total workers in industrial
																undertakings located in Paschimanchal and minimum of 200
																total workers in B-P-M</td>
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
																undertakings located in Paschimanchal and minimum of 200
																total workers in B-P-M</td>
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
															<td>Additional 10% EPF Reimbursement upto maximum of
																70% in case of industrial undertakings having 75% equity
																owned by Divyang/SC/CT/Females Promoters</td>
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
															<td>Additional Infrastructure Interest Subsidy @2.5%
																upto 18 maximum of 7.5% in case of industrial
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
															<td>Reimbursement of Disallowed Input Tax Credit on
																plant, building materials, and other capital goods.</td>
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
										</div>
									</div>


									<div class="row mt-4">
										<div class="col-sm-12">
											<p
												class="mb-4 text-center animate__animated animate__fadeInDown mt-3">
												<strong>Declaration</strong>
											</p>
											<p>The above information are completely true and no fact
												has been concealed or misrepresented. It is further
												certified that the company has not applied for benefits of
												the above nature under any sector-specific or other policy
												of the Govt of UP for purpose of availing benefits of the
												above nature.</p>
											<p>I/we hereby agree that I/we shall forthwith repay the
												benefits released to me/us under Rules of Policy for
												Promotion of Industrial Investment and Employment-2017, if
												the said benefits are found to be disbursed in excess of the
												amount actually admissible whatsoever the reason.</p>

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
											<p>(b) Detailed Project Report (DPR) prepared by external
												consultant/Chartered Accountant</p>
											<p>(c) Charted Accountant’s Certificate for existing
												gross block industrial undertaking.</p>
											<p>(d) Chartered Engineer’s Certified List of Fixed
												Assets of existing industrial undertaking in support of
												gross block.</p>
											<p>(e) Undertaking (as per format placed at Annexure I-A)
												on Stamp Paper of Rs. 10</p>
										</div>
									</div>
								</div>

								<!-- End Annexure-III  -->



								<div class="isf-form mt-4" id="hideFields">
										<%-- <hr class="mt-4 mb-4">
										<form:form modelAttribute="downloadSupportCD" class="mt-4"
							             name="downloadSupportCD" method="post">
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
												<a href="./deptApplicationForLoc"
													class="common-default-btn mt-3">Back</a>
											</div>
											<div class="col-sm-7 text-right">
											<button type="button" id="printMethod"	class="common-btn mt-3"><i class="fa fa-print"></i> Print</button>												
											 <a	href="./facilitiesReliefSought" class="common-btn mt-3">Next </a>
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

			<form:form modelAttribute="department" id="concernDept"
				action="saveConcernDepartment" autocomplete="off" method="POST">


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
									<form:checkbox path="newDeptName"
										value="Commercial
											Tax"
										class="custom-control-input" id="CommercialTax"
										name="Capital-Interest-Subsidy"></form:checkbox>
									<label class="custom-control-label" for="CommercialTax">Commercial
										Tax</label>
								</div>
								<div class="custom-control custom-checkbox">
									<form:checkbox path="newDeptName" value="UPPCL"
										class="custom-control-input" id="UPPCL"
										name="Capital-Interest-Subsidy"></form:checkbox>
									<label class="custom-control-label" for="UPPCL">UPPCL</label>
								</div>
								<div class="custom-control custom-checkbox">
									<form:checkbox path="newDeptName"
										value="Stamp
											& Registration"
										class="custom-control-input" id="StampRegistration"
										name="Capital-Interest-Subsidy"></form:checkbox>
									<label class="custom-control-label" for="StampRegistration">Stamp
										& Registration</label>
								</div>
								<div class="custom-control custom-checkbox">
									<form:checkbox path="newDeptName"
										value="Mandi
											Parishad"
										class="custom-control-input" id="MandiParishad"
										name="Capital-Interest-Subsidy"></form:checkbox>
									<label class="custom-control-label" for="MandiParishad">Mandi
										Parishad</label>
								</div>
								<div class="custom-control custom-checkbox">
									<form:checkbox path="newDeptName"
										value="Labour
											Department"
										class="custom-control-input" id="LabourDepartment"
										name="Capital-Interest-Subsidy"></form:checkbox>
									<label class="custom-control-label" for="LabourDepartment">Labour
										Department</label>
								</div>
								<div class="custom-control custom-checkbox">
									<form:checkbox path="newDeptName" value="DIC"
										class="custom-control-input" id="DIC"
										name="Capital-Interest-Subsidy"></form:checkbox>
									<label class="custom-control-label" for="DIC">DIC</label>
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
									<label>Department Name</label>
									<!--  <input type="text" onkeypress="return validateDepartment()" class="form-control" id="depttName" name=""> -->
									<form:input type="text" path="newDeptName" class="form-control"
										id="depttName" maxlength="50"
										onkeypress="return validateDepartment()" name=""></form:input>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label>Email</label>
									<!-- <input type="email" class="form-control" id="depttEmail" name=""> -->
									<form:input path="deptEmail" type="email" class="form-control"
										id="depttEmail" name=""></form:input>
									<span id="deptemail" class="text-danger"></span>
									<form:errors path="deptEmail" cssClass="error" />

								</div>
							</div>
							<div class="col-sm-12 text-right">
								<div class="form-group">
									<button type="button"
										class="btn btn-outline-success btn-sm mb-3 add-row-for-deptt">Add</button>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="table-responsive">
									<table class="table table-bordered" id="DepartmentNameTable">
										<thead>
											<tr>
												<th>Department Name</th>
												<th>Email</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<!-- <tr>
                          <td>Department-1</td>
                          <td>info@domain.com</td>
                          <td><a href="javascript:void(0);" class="remove-row  delete-row"><i class="fa fa-trash text-danger"></i></a></td>
                        </tr>
                        <tr>
                          <td>Department-2</td>
                          <td>info@domain.com</td>
                          <td><a href="javascript:void(0);" class="remove-row  delete-row"><i class="fa fa-trash text-danger"></i></a></td>
                        </tr> -->
										</tbody>
									</table>
								</div>
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