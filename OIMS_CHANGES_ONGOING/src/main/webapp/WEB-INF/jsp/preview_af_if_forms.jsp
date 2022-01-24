<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.webapp.ims.model.ProposedEmploymentDetails"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="en">
<head>
<style>
html2canvas {
	width: 100px !important;
	height: 200px !important;
}

body {
	background-color: coral;
}
</style>


<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>AF ISF Preview Form</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.debug.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.min.js"></script>
<link rel="stylesheet" href="css/style.css">
<script src="js/custom.js"></script>
<script type="text/javascript"></script>
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
			//var x = document.getElementsByClassName('hide-by-class').hide;
			$(".hide-by-class").hide();
			document.getElementById('hideByRowId1').style.display = 'none';
			document.getElementById('hideByRowId2').style.display = 'none';
			var element = document.getElementById("hideByRowId3");
			element.classList.remove("hide");
			document.getElementById('hideByRowId4').style.display = 'table-row';
			//document.getElementById('natureOfProjectDiv').style.display = 'none';

		}
		if ('${natureOfProject}' == 'ExistingProject') {
			$('select').find('option[value=ExistingProject]').attr('selected',
					'selected');
			$(".hide-by-class").show();
			var element = document.getElementById("hideByRowId1");
			element.classList.remove("hide");
			var element = document.getElementById("hideByRowId2");
			element.classList.remove("hide");
			var element = document.getElementById("hideByRowId3");
			element.classList.remove("hide");
			document.getElementById('hideByRowId4').style.display = 'table-row';
			//document.getElementById('natureOfProjectDiv').style.display = 'block';

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
		/* if ('${ISF_Reim_SCST}' != null && '${ISF_Reim_SCST}' != '') {
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
					'input:checkbox[name="ISF_SGST_Claim_Reim"][value="ISF_Reim_BPLW"]')
					.prop('checked', true);

		} */

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

		if ('${hideCustamList}' === 'hideCustamList') {
			document.getElementById('showHide1').style.display = 'table-row';
			document.getElementById('showHide2').style.display = 'none';
			document.getElementById('showHide3').style.display = 'none';
			document.getElementById('showHide4').style.display = 'none';

		}else {
			document.getElementById('showHide1').style.display = 'none';
			//document.getElementById('showHide2').style.display = 'table-row';
			document.getElementById('showHide3').style.display = 'table-row';
			//document.getElementById('showHide4').style.display = 'table-row';
		}
	}
	$(document).on('click', '#printMethod', function(e) {
		e.preventDefault();

		var $this = $(this);
		var originalContent = $('body').html();
		var printArea = $this.parents('#printpdf').html();

		$('body').html(printArea);
		var printButton = document.getElementById("clisedId");
		var editdownprint = document.getElementById("editdownprint");
		var removebottomId = document.getElementById("removebottomId");
		var removeTopId = document.getElementById("removeTopId");
		printButton.style.visibility = 'hidden';
		editdownprint.style.visibility = 'hidden';
		removebottomId.style.visibility = 'hidden';
		removeTopId.style.visibility = 'hidden';
		window.print();
		$('body').html(originalContent);
	});

	$(document)
			.ready(
					function() {
						var pdf = new jsPDF("l", "pt", "a4");
						pdf.internal.scaleFactor = 2;
						var options = {
							pagesplit : true
						};
						$('#formId')
								.click(
										function() {
											document
													.getElementById('editdownprint').style.display = 'none';
											document.getElementById('clisedId').style.display = 'none';
											pdf
													.addHTML(
															document
																	.getElementById("content2"),
															options,
															function() {
																pdf
																		.save("AFIFFORM.pdf");
															});
											document.getElementById('clisedId').style.display = 'inline-block';
											document
													.getElementById('editdownprint').style.display = 'inline-block';
										});
					});

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

		var ISF_Ttl_SGST_Reim = '${incentiveDeatilsData.ISF_Ttl_SGST_Reim}';
		if (ISF_Ttl_SGST_Reim == '') {
			//alert("hi");
			$(".ISF_Ttl_SGST_Reim_Row").hide();
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

		var ISF_Ttl_Stamp_Duty_EX = '${incentiveDeatilsData.ISF_Ttl_Stamp_Duty_EX}';
		if (ISF_Ttl_Stamp_Duty_EX == '') {
			//alert("hi");
			$(".ISF_Ttl_Stamp_Duty_EX_Row").hide();
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
		var ISF_Total_EPF_Row = '${incentiveDeatilsData.ISF_Ttl_EPF_Reim}';
		if (ISF_Total_EPF_Row == '') {
			//alert("hi");
			$(".ISF_Total_EPF_Row").hide();
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

		var ISF_Total_Int_Subsidy = '${incentiveDeatilsData.ISF_Total_Int_Subsidy}';
		if (ISF_Total_Int_Subsidy == '') {
			//alert("hi");
			$(".ISF_Total_Int_Subsidy_Row").hide();
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


		var Total_Other_Incentive = '${Total_Other_Incentive}';
		if (Total_Other_Incentive == '') {
			//alert("hi");
			$(".Total_Other_Incentive_Row").hide();
		}

		var othAddRequest1 = '${othAddRequest1}';
		if (othAddRequest1 == '') {
			//alert("hi");
			$("#showHide1").hide();
		}

		var incentiveId = '${incentiveId}';
		if (incentiveId == '') {
			//alert("hi");
			$("#incentiveId").hide();
		}

		var incentiveDeatilsData = '${incentiveDeatilsData}';
		var ISF_Cstm_Inc_Status = '${ISF_Cstm_Inc_Status}';
		if (ISF_Cstm_Inc_Status == '' || incentiveDeatilsData == null) {
			//alert("hi");
			$("#showHide2").hide();
		}

		var isfCustIncDocName = '${isfCustIncDocName}';
		if (isfCustIncDocName == '' || incentiveDeatilsData == null) {
			//alert("hi");
			$("#showHide4").hide();
		}
		
		 
		
		var natureOfProject = '${natureOfProject}';
		if (natureOfProject == 'NewProject') {
			//alert("hi");
			$(".NewProject-row").hide();
		}

		var natureOfProject = '${natureOfProject}';
		if (natureOfProject == 'NewProject') {
			//alert("hi");
			$(".natureOfProject").hide();
		}
	}
	$(document).ready(showYesOrNo2);
</script>

</head>

<body class="bottom-bg">
	<div>
		<button onclick="topFunction()" id="GoToTopBtn" title="Go to top">Top</button>
		<section class="inner-header" id="removeTopId">
			<div class="top-header">
				<div class="container">
					<div class="row">
						<div class="col-sm-6 text-left">
							<span class="top-gov-text">Goverment of Uttar Pradesh</span>
						</div>
						<div class="col-sm-6 text-right">
							<a href="tel:05222238902"><i class="fa fa-phone"></i>
								0522-2238902</a> | <a href="mailto:info@udyogbandhu.com"><i
								class="fa fa-envelope"></i> info@udyogbandhu.com</a>
						</div>
					</div>
				</div>
			</div>
			<!-- Navigation / Navbar / Menu -->
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<div class="container">
					<a class="navbar-brand" href="#"><img src="images/logo.png"
						class="logo" alt="Logo"></a>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navbarTogglerDemo02"
						aria-controls="navbarTogglerDemo02" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>
					<div class="collapse navbar-collapse" id="navbarTogglerDemo02">
						<ul class="navbar-nav ml-auto mt-2 mt-lg-0">
							<li class="nav-item"><a class="nav-link active" href="#">Home</a>
							</li>
							<li class="nav-item"><a class="nav-link"
								href="http://udyogbandhu.com/" target="_blank">Invest UP</a></li>
							<li class="nav-item"><a class="nav-link"
								href="http://niveshmitra.up.nic.in/About.aspx?ID=whyup"
								target="_blank">Why Invest in UP</a></li>
							<li class="nav-item"><a class="nav-link"
								href="http://udyogbandhu.com/topics.aspx?mid=Policies"
								target="_blank">Policies</a></li>
							<li class="nav-item"><a class="nav-link"
								href="http://udyogbandhu.com/topics.aspx?mid=UdyogBandhu"
								target="_blank">Contact Us</a></li>
						</ul>
						<div class="gov-logo-group">
							<a href="#"><img src="images/up-logo.png" align="up-Logo"></a>
						</div>
					</div>
				</div>
			</nav>
			<!-- End Navigation / Navbar / Menu -->
		</section>
		<section class="common-form-area" id="content2">
			<div class="container">
				<!-- Main Title -->
				<div class="inner-banner-text mb-4">
					<h1>Preview Application Form</h1>
				</div>
				<div class="card card-block p-3">
					<div class="row">
						<div class="col-sm-12">
							<div class="mt-4">
								<div class="without-wizard-steps">
									<div class="row">
										<div class="col-sm-4" id="clisedId">
											<button onclick="closeCurrentTab()"
												class="btn btn-outline-danger mb-3">Close</button>
										</div>
										<div class="col-sm-8 text-right" id="editdownprint">
											<a href="./editApplicantForm"
												onclick="return confirm('Are you sure you want edit records?')"
												class="btn btn-outline-info mb-3"><i class="fa fa-edit"></i>
												Edit</a>
											<button class="btn btn-outline-success mb-3" id="formId">
												<i class="fa fa-download"></i>Download Form
											</button>
											<button type="button" id="printMethod"
												class="btn btn-outline-secondary mb-3">
												<i class="fa fa-print"></i> e-Print
											</button>
										</div>
									</div>
									<div id="printpdf">
										<form:form action="saveApplicantDetails" class="isf-form"
											method="get" modelAttribute="applicantDetail"
											enctype="multipart/form-data">
											<hr class="mt-2">
											<div class="isf-form mt-4" id="content1">
												<h4
													class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Authorised
													Signatory Details</h4>
												<div class="row">
													<div class="col-sm-12">
														<div class="table-responsive">
															<table class="table table-bordered">
																<tbody>
																	<tr>
																		<td>First Name</td>
																		<td><input type="text" class="form-control"
																			name="appFirstName" id="appFirstNameId"
																			value="${appFirstName}" disabled="disabled">
																		<td rowspan="4" class="text-center"><img
																			src="data:image/png;base64,${applicantDetail.base64imageFile}"
																			style="height: 250px; max-width: 220px;" /></td>
																	</tr>
																	<tr>
																		<td>Middle Name</td>
																		<td><input type="text" class="form-control"
																			value="${appMiddleName}" disabled="disabled"
																			id="appMiddleNameId" name="appMiddleName"></td>
																	</tr>
																	<tr>
																		<td>Last Name</td>
																		<td><input type="text" class="form-control"
																			value="${appLastName}" disabled="disabled"
																			id="appLastNameId" name="appLastName"></td>
																	</tr>
																	<tr>
																		<td>Email ID</td>
																		<td><input type="text" class="form-control"
																			value="${appEmailId}" disabled="disabled"
																			id="appEmailId" name="appEmailId"></td>
																	</tr>
																	<tr>
																		<td>Mobile No:(+91)</td>
																		<td colspan="2"><input type="text"
																			class="form-control" value="${appMobileNo}"
																			disabled="disabled" id="appMobileNoId"
																			name="appMobileNo"></td>
																	</tr>
																	<tr>
																		<td>Phone No. (Landline)</td>
																		<td colspan="2"><input type="text"
																			class="form-control" value="${appPhoneNo}"
																			disabled="disabled" id="appPhoneNo" name="appPhoneNo"></td>
																	</tr>
																	<tr>
																		<td>Designation</td>
																		<td colspan="2"><input type="text"
																			class="form-control" value="${appDesignation}"
																			disabled="disabled" id="appDesignation"
																			name="appDesignation"></td>
																	</tr>
																	<tr>
																		<td>Gender</td>
																		<td colspan="2">
																			<div class="form-group">
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
																		</td>
																	</tr>
																	<tr>
																		<td>Aadhaar Number</td>
																		<td colspan="2"><input type="text"
																			value="${appAadharNo}" disabled="disabled"
																			class="form-control" name="appAadharNo"></td>
																	</tr>
																	<tr>
																		<td>PAN Number</td>
																		<td colspan="2"><input type="text"
																			value="${appPancardNo}" disabled="disabled"
																			class="form-control" name="appPancardNo"></td>
																	</tr>
																	<tr>
																		<td>Address Line - 1</td>
																		<td colspan="2"><input type="text"
																			class="form-control" value="${appAddressLine1}"
																			disabled="disabled" id="appAddressLine1"
																			name="appAddressLine1"></td>
																	</tr>
																	<tr>
																		<td>Address Line - 2</td>
																		<td colspan="2"><input type="text"
																			class="form-control" value="${appAddressLine2}"
																			disabled="disabled" id="appAddressLine2"
																			name="appAddressLine2"></td>
																	</tr>
																	<tr>
																		<td>Country</td>
																		<td colspan="2"><input type="text"
																			value="${appCountry}" disabled="disabled"
																			class="form-control" name="appCountry"></td>
																	</tr>
																	<tr>
																		<td>State/UT</td>
																		<td colspan="2"><input type="text"
																			value="${appState}" disabled="disabled"
																			class="form-control" name="appState"></td>
																	</tr>
																	<tr>
																		<td>District</td>
																		<td colspan="2"><input type="text"
																			value="${appDistrict}" disabled="disabled"
																			class="form-control" name="appDistrict"></td>
																	</tr>
																	<tr>
																		<td>Pin Code</td>
																		<td colspan="2"><input type="text"
																			class="form-control" value="${appPinCode}"
																			disabled="disabled" id="appPinCode" name="appPinCode"></td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>

										</form:form>

										<hr class="mt-2">


										<div class="isf-form mt-4">
											<h4
												class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Business
												Entity Details</h4>
											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td style="width: 50%;">Business Entity Name</td>
																	<td><input type="text" class="form-control"
																		value="${businessEntityName}" disabled="disabled"
																		id="businessEntityName" name="businessEntityName"></td>
																</tr>
																<tr>
																	<td>Organisation Type</td>
																	<td><input type="text" class="form-control"
																		disabled="disabled" value="${businessEntityType}"></td>
																</tr>
																<tr>
																	<td>Authorised Signatory Name</td>
																	<td><input type="text" class="form-control"
																		value="${authorisedSignatoryName}" disabled="disabled"
																		id="authorisedSignatoryName"
																		name="authorisedSignatoryName"></td>
																</tr>
																<tr>
																	<td>Designation</td>
																	<td><input type="text" class="form-control"
																		value="${businessDesignation}" disabled="disabled"
																		id="businessDesignation" name="businessDesignation"></td>
																</tr>
																<tr>
																	<td>Email</td>
																	<td><input type="email" class="form-control"
																		value="${emailId}" disabled="disabled" id="emailId"
																		name="emailId"></td>
																</tr>
																<tr>
																	<td>Mobile No.</td>
																	<td><input type="text" class="form-control"
																		value="${mobileNumber}" disabled="disabled"
																		id="mobileNumber" name="mobileNumber"></td>
																</tr>
																<tr>
																	<td>Phone No. (Landline)</td>
																	<td colspan="2"><input type="text"
																		class="form-control" value="${phoneNo}"
																		disabled="disabled" id="phoneNo" name="phoneNo"></td>
																</tr>
																<tr>
																	<td>Fax</td>
																	<td><input type="text" class="form-control"
																		value="${fax}" disabled="disabled" id="fax" name="fax"></td>
																</tr>
																<tr>
																	<td>Registered Office Address</td>
																	<td><input type="text" class="form-control"
																		value="${businessAddress}" disabled="disabled"
																		id="businessAddress" name="businessAddress"></td>
																</tr>
																<tr>
																	<td>Country</td>
																	<td><input type="text" class="form-control" id=""
																		value="${businessCountryName}" disabled="" name=""></td>
																</tr>
																<tr>
																	<td>State/UT</td>
																	<td><input type="text" class="form-control" id=""
																		value="${businessStateName}" disabled="" name=""></td>
																</tr>
																<tr>
																	<td>District</td>
																	<td><input type="text" class="form-control" id=""
																		value="${businessDistrictName}" disabled="" name=""></td>
																</tr>
																<tr>
																	<td>Pin Code</td>
																	<td><input type="text" class="form-control"
																		value="${PinCode}" disabled="disabled" id="PinCode"
																		name="PinCode"></td>
																</tr>
																<tr>
																	<td colspan="2"><strong>Director /
																			Partner / Owner / Proprietor Details</strong></td>
																</tr>

															</tbody>
														</table>
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
																		<th>Divyang Category</th>
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
																		<td>${list.div_Status}</td>
																		<td>${list.panCardNo}</td>
																		<td>${list.din}</td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td colspan="2"><strong>Uploaded
																			Documents and Certificates</strong></td>
																</tr>
																<tr>
																	<td style="width: 50%;">Date of Incorporation of
																		Company</td>
																	<td><input type="date" class="form-control"
																		value="${yearEstablishment}" disabled="disabled"
																		id="yearEstablishment" name="yearEstablishment"></td>
																</tr>
																<tr>
																	<td>GSTIN</td>
																	<td><input type="text" value="${gstin}"
																		disabled="disabled" class="form-control" id="gstin"
																		name="gstin"></td>
																</tr>
																<tr>
																	<td>CIN</td>
																	<td><input type="text" value="${cin}"
																		disabled="disabled" class="form-control" id="cin"
																		name="cin"></td>
																</tr>
																<tr>
																	<td>Company PAN No.</td>
																	<td><input type="text" class="form-control"
																		value="${companyPanNo}" disabled="disabled"
																		id="companyPanNo" name="companyPanNo"></td>
																</tr>
																<tr>
																	<td>MoA / Partnership Deed Attachment</td>
																	<td><a
																		href="./downloadBusinessEntityDoc1?fileName=${moaDocFname}"><small>${moaDocFname}</small></a>
																	</td>
																</tr>
																<tr>
																	<td>Certificate Of Incorporation/Registration</td>
																	<td><a
																		href="./downloadBusinessEntityDoc1?fileName=${regisAttacDocFname}"><small>${regisAttacDocFname}</small></a>
																	</td>
																</tr>
																<tr>
																	<td>Certified copy of the resolution of the Board
																		of Directors of the Company</td>
																	<td><a
																		href="./downloadBusinessEntityDoc1?fileName=${bodDocFname}"><small>${bodDocFname}</small></a>
																	</td>
																</tr>
																<%-- 	<tr>
															<td>Competent Authority of the Industrial
																Undertaking authorizing the deponent be provided along
																with the affidavit.</td>
															<td><a href="./downloadDocBusiness3?fileName=${indusAffidaDocFname}"><small>${indusAffidaDocFname}</small></a>
															</td>
														</tr> --%>
																<tr>
																	<td>Notarized undertaking (as per format placed at
																		Annexure I-A) on Stamp Paper of Rs. 10</td>
																	<td><a
																		href="./downloadBusinessEntityDoc1?fileName=${annexureiaformat}"><small>${annexureiaformat}</small></a>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>

										<div class="isf-form mt-4" id="content3">
											<h4
												class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Project
												Details</h4>
											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td>Name of Contact Person</td>
																	<td><input type="text" class="form-control"
																		value="${contactPersonName}" disabled="disabled"
																		id="contactPersonName" name="contactPersonName"></td>
																</tr>
																<tr>
																	<td>Mobile No:(+91)</td>
																	<td><input type="text" class="form-control"
																		value="${mobileNo}" disabled="disabled" id="mobileNo"
																		name="mobileNo"></td>
																</tr>
																<tr>
																	<td>Designation</td>
																	<td><input type="text" class="form-control"
																		value="${designation}" disabled="disabled"
																		id="designation" name="designation"></td>
																</tr>
																<tr>
																	<td>Project Description</td>
																	<td><input type="text" class="form-control"
																		disabled="disabled" id="projectDescription"
																		name="projectDescription"
																		value="${projectDescription}"></td>
																</tr>
																<tr>
																	<td>Website (if any)</td>
																	<td><input type="text" class="form-control"
																		value="${webSiteName}" disabled="disabled"
																		id="webSiteName" name="webSiteName"></td>
																</tr>
																<tr>
																	<td>Full Address</td>
																	<td><input type="text" class="form-control"
																		value="${fullAddress}" disabled="disabled"
																		id="fullAddress" name="fullAddress"></td>
																</tr>
																<tr>
																	<td>District</td>
																	<td><input type="text" class="form-control"
																		value="${districtName}" disabled="disabled"
																		id="districtName" name="districtName"></td>
																</tr>
																<tr>
																	<td>Mandal</td>
																	<td><input type="text" class="form-control"
																		value="${mandalName}" disabled="disabled"
																		id="mandalName" name="mandalName"></td>
																</tr>
																<tr>
																	<td>Region</td>
																	<td style="width: 50%;"><input type="text"
																		class="form-control" value="${resionName}"
																		disabled="disabled" id="resionName" name="resionName"></td>
																</tr>

																<tr>
																	<td>Pin Code</td>
																	<td style="width: 50%;"><input type="text"
																		class="form-control" value="${pinCode}"
																		disabled="disabled" id="resionName" name="resionName"></td>
																</tr>

																<%-- <tr>
																<td style="width: 50%;">Enclose Udyam Registration
																	Number (for MSME)</td>
																<td><a
																	href="./downloadDoc1?fileName=${regiOrLicenseFileName}"><small>${regiOrLicenseFileName}</small></a>
																</td>
															</tr> --%>
																<!-- <tr>
                                                            <td>IEM copy (for large & Mega)</td>
                                                            <td>
                                                                <a href="javascript:void();"><small>document-file.pdf</small></a>
                                                            </td>
                                                        </tr> -->
																<tr>
																	<td>Nature of the project</td>
																	<td><input type="text" class="form-control"
																		value="${natureOfProject}" id="natureOfProjectId"
																		disabled="disabled" name=""></td>
																</tr>
																<tr class="natureOfProject">
																	<td colspan="2">
																		<div class="table-responsive mt-3"
																			id="productsDetailsTbl">
																			<c:if test="${not empty existProjList}">
																				<table class="table table-stripped table-bordered"
																					id="edittable">
																					<thead>
																						<tr>
																							<th>Existing Products</th>
																							<th>Existing Installed Capacity</th>
																							<th>Proposed Products</th>
																							<th>Proposed Installed Capacity</th>
																							<th>Existing Gross Block</th>
																							<th>Proposed Gross Block</th>
																						</tr>

																					</thead>
																					<tbody class="existing-proposed-products">

																						<c:forEach var="existProj"
																							items="${existProjList}" varStatus="counter">
																							<tr>
																								<td>${existProj.epdExisProducts}</td>
																								<td>${existProj.epdExisInstallCapacity}</td>
																								<td>${existProj.epdPropProducts}</td>
																								<td>${existProj.epdPropInstallCapacity}</td>
																								<td>${existProj.epdExisGrossBlock}</td>
																								<td>${existProj.epdPropoGrossBlock}</td>

																							</tr>
																						</c:forEach>
																					</tbody>
																				</table>
																			</c:if>
																		</div>
																	</td>
																</tr>

																<tr id="hideByRowId4">
																	<td colspan="2"><strong>Relevant
																			Documentary Support</strong></td>
																</tr>
																<tr id="hideByRowId3">
																	<td>Enclose Detailed Project Report prepared by
																		External Consultant/Chartered Accountants</td>
																	<td><a
																		href="./downloadProjectDoc1?fileName=${enclDetProRepFileName}"><small>${enclDetProRepFileName}</small></a></td>
																</tr>

																<tr id="hideByRowId1">
																	<td>CA Certificate for existing gross block</td>
																	<td><a
																		href="./downloadProjectDoc1?fileName=${caCertificateFileName}"><small>${caCertificateFileName}</small></a></td>
																</tr>
																<tr id="hideByRowId2">
																	<td>Chartered Engineerâ€™s Certified List of Fixed
																		Assets in support of existing gross block.</td>
																	<td><a
																		href="./downloadProjectDoc1?fileName=${charatEngFileName}"><small>${charatEngFileName}</small></a></td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>



										<div class="isf-form mt-4" id="content4">
											<h4
												class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Investment
												Details</h4>
											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td>Category of Industrial Undertaking</td>
																	<td><input type="text" class="form-control" id=""
																		value="${invIndType}" disabled="" name=""></td>
																</tr>
																<tr>
																	<td>Proposed Capital Investment</td>
																	<td><input type="text" class="form-control"
																		value="${invFci}" disabled="disabled" id="invFci"
																		name="invFci"></td>
																</tr>
																<tr>
																	<td>Total Cost of Project</td>
																	<td><input type="text" class="form-control"
																		value="${invTotalProjCost}" disabled="disabled"
																		id="invTotalProjCost" name="invTotalProjCost"></td>
																</tr>
																<tr>
																	<td>Land Cost</td>
																	<td><input type="text" class="form-control"
																		value="${invLandCost}" disabled="disabled"
																		id="invLandCost" name="invLandCost"></td>
																</tr>
																<tr>
																	<td>Building Site Development & Civil Works Cost</td>
																	<td><input type="text" class="form-control"
																		value="${invBuildingCost}" disabled="disabled"
																		id="invBuildingCost" name="invBuildingCost"></td>
																</tr>
																<tr>
																	<td>Plant & Machinery Cost</td>
																	<td><input type="text" class="form-control"
																		value="${invPlantAndMachCost}" disabled="disabled"
																		id="invPlantAndMachCost" name="invPlantAndMachCost"></td>
																</tr>
																<tr>
																	<td>Miscellaneous Fixed Asset(MFA)</td>
																	<td><input type="text" class="form-control"
																		value="${invOtherCost}" disabled="disabled"
																		id="invOtherCost" name="invOtherCost"></td>
																</tr>
																<tr>
																	<td>Indicate Opted Cut-off date investment</td>
																	<td><input type="date" class="form-control"
																		value="${invCommenceDate}" disabled="disabled"
																		name="invCommenceDate"></td>
																</tr>
																<tr>
																	<td>Proposed Date of Commencement of Commercial Production</td>
																	<td><input type="date" class="form-control"
																		value="${propCommProdDate}" disabled="disabled"
																		name="propCommProdDate"></td>
																</tr>

																<c:if
																	test="${invIndType=='Small' || invIndType=='Medium' }">
																	<tr>
																		<td>Enclose Udyam Registration Certificate (for
																			MSME)</td>
																		<td><a
																			href="./downloadInvestmentDoc1?fileName=${regiOrLicenseFileName}"><small>${regiOrLicenseFileName}</small></a>
																		</td>
																	</tr>

																</c:if>

																<c:if
																	test="${invIndType=='Mega' || invIndType=='Large' || invIndType=='Super Mega' || invIndType=='Mega Plus' }">
																	<tr>
																		<td>Enclose IEM copy (for large & Mega)</td>
																		<td><a
																			href="./downloadInvestmentDoc1?fileName=${regiOrLicenseFileName}"><small>${regiOrLicenseFileName}</small></a>
																		</td>
																	</tr>

																</c:if>



																<tr>
																	<td colspan="2"><strong>Phase Wise
																			Investment</strong></td>
																</tr>
																<tr>
																	<td style="width: 50%;">Phase Wise Investment as
																		per Detailed Project Report (If Applicable)</td>
																	<td><input type="text" class="form-control" id=""
																		value="${pwApply}" disabled="" name=""></td>
																</tr>
																<tr>
								<td colspan="2">
									<c:if test="${not empty pwInvList}">
										<table class="table table-stripped table-bordered">
											<thead>
												<tr>
													<th>Phase</th>
															<th>Land Cost</th>
															<th>Building Site Development And Civil Works Cost</th>
															<th>Plant And Machinery Cost</th>
															<th>Miscellaneous Fixed Asset</th>
															<th>Proposed Fixed Capital Investment</th>
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
																<td>${pwInvObj.pwPhaseNo}</td>
																		<td>${pwInvObj.invLandCost}</td>
																		<td>${pwInvObj.invBuildingCost}</td>
																		<td>${pwInvObj.invPlantAndMachCost}</td>
																		<td>${pwInvObj.invOtherCost}</td>
																		<td>${pwInvObj.invFci}</td>
															</tr>
														</c:if>
													</c:if>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2"><strong>Others If Any</strong></td>
							</tr>
							<tr>
								<td colspan="2">
									<c:if test="${not empty invotherlist}">
										<table class="table table-stripped table-bordered">
											<thead>
												<tr>
													<th>Phase</th>
													<th>Particulars</th>
													<th>Proposed Investment in the project (As per DPR)</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="invother" items="${invotherlist}" varStatus="counter">
													<tr>
														<td>${ invother.phaseNumber}</td>
														<td>${ invother.particulars}</td>
														<td>${ invother.proposedInvestmentInProject}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2"><strong>Phase-wise Installed Capacities</strong></td>
							</tr>
							<tr>
								<td colspan="2">
									<c:if test="${not empty invic}">
										<table class="table table-stripped table-bordered">
											<thead>
												<tr>
													<th>Phase</th>
															<th>Proposed Fixed Capital Investment</th>
															<th>Product</th>
															<th>Capacity</th>
															<th>Unit</th>
															<th>Proposed Date of Commercial Production</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="ic" items="${invic}" varStatus="counter">
													<tr>
														<td>${ic.phaseNo}</td>
																		<td>${ic.invFci}</td>
																		<td>${ic.productName}</td>
																		<td>${ic.capacity}</td>
																		<td>${ic.unit}</td>
																		<td>${ic.propProductDate}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2"><strong>Means of Financing</strong></td>
							</tr>
							<tr>
								<td colspan="2">
									<c:if test="${not empty invotherlist}">
										<table class="table table-stripped table-bordered">
											<thead>
												<tr>
													<th>Particulars</th>
													<th>Proposed Investment in the project (As per DPR)</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="mof" items="${momlist}" varStatus="counter">
													<tr>
														<td>${ mof.mofParameter}</td>
														<td>${ mof.mofAmount}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
								</td>
							</tr>
																<tr>
																	<td>Upload for Break-Up of Project Cost & Means of
																		Financing</td>
																	<td><a
																		href="./downloadInvestmentDoc1?fileName=${invFileName}"><small>${invFileName}</small></a></td>
																</tr>

															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>

										<div class="isf-form mt-4" id="content5">
											<h4
												class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Proposed
												Employment Details</h4>
											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive">
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
														<table class="table table-bordered">
															<tbody>
																<tr>
																	<td style="width: 50%;">Skilled Male Employees</td>
																	<td><input type="text" class="form-control" id=""
																		disabled="" name="" value="${skilledEmploymentMale}"></td>
																</tr>
																<tr>
																	<td>Skilled Female Employees</td>
																	<td><input type="text" class="form-control" id=""
																		disabled="" name="" value="${skilledEmploymentFemale}"></td>
																</tr>
																<tr>
																	<td>Total Skilled Employees</td>
																	<td><input type="text" class="form-control" id=""
																		value="${totalSkilledEmployment}" disabled="" name=""></td>
																</tr>

																<tr>
																	<td style="width: 50%;">Unskilled Male Employees</td>
																	<td><input type="text" class="form-control" id=""
																		disabled="" name="" value="${unSkilledEmploymentMale}"></td>
																</tr>
																<tr>
																	<td>Unskilled Female Employees</td>
																	<td><input type="text" class="form-control" id=""
																		disabled="" name=""
																		value="${unSkilledEmploymentFemale}"></td>
																</tr>
																<tr>
																	<td>Total Unskilled Employees</td>
																	<td><input type="text" class="form-control" id=""
																		value="${totalUnSkilledEmployment}" disabled=""
																		name=""></td>
																</tr>

																<tr>
																	<td>Total Male Employees</td>
																	<td><input type="text" class="form-control" id=""
																		value="${grossTotalMaleEmployment}" disabled=""
																		name=""></td>
																</tr>
																<tr>
																	<td>Total Female Employees</td>
																	<td><input type="text" class="form-control" id=""
																		value="${grossTotalFemaleEmployment}" disabled=""
																		name=""></td>
																</tr>
																<tr>
																	<td>Total Employees</td>
																	<td><input type="text" class="form-control" id=""
																		value="${grossTotalMaleandFemaleEmployment}"
																		disabled="" name=""></td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>

										<h4 id="incentiveId"
											class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Incentive
											Details</h4>

										<div class="isf-form" id="content6">
											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table
															class="table table-stripped table-bordered loc-stage-table">
															<tbody>
																<tr class="ISF_Claim_Reim-row">
																	<td style="width: 50%;"><strong>Amount of
																			SGST claimed for reimbursement</strong></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Claim_Reim}" disabled="disabled"
																		name="ISF_Claim_Reim"></td>
																</tr>
																<tr class="ISF_Reim_SCST-row">
																	<td><label>Additional 10% GST where 25%
																			minimum SC/ST workers employed subject to minimum of
																			400 total workers in industrial undertakings located
																			in Paschimanchal and minimum of 200 total workers in
																			B-P-M</label></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Reim_SCST}" disabled="disabled"
																		name="ISF_Reim_SCST"></td>
																</tr>
																<tr class="ISF_Reim_FW-row">
																	<td><label>Additional 10% GST where 40%
																			minimum female workers employed subject to minimum of
																			400 total workers in industrial undertakings located
																			in Paschimanchal and minimum of 200 total workers in
																			B-P-M </label></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Reim_FW}" disabled="disabled"
																		name="ISF_Reim_FW"></td>
																</tr>
																<tr  class="ISF_Reim_BPLW-row">
																	<td><label>Additional 10% GST where 25%
																			minimum BPL workers employed subject to minimum of
																			400 total workers in industrial undertakings located
																			in Paschimanchal and minimum of 200 total workers in
																			B-P-M</label></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Reim_BPLW}" disabled="disabled"
																		name="ISF_Reim_BPLW"></td>
																</tr>
																<tr class="ISF_Ttl_SGST_Reim_Row">
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
												<div class="col-sm-12">
													<div class="table-responsive">
														<table
															class="table table-stripped table-bordered loc-stage-table">
															<tbody>
																<tr class="ISF_Stamp_Duty_EX-row">
																	<td style="width: 50%;"><strong>Amount of
																			Stamp Duty Exemption</strong></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Stamp_Duty_EX}" disabled="disabled"
																		name="ISF_Stamp_Duty_EX"></td>
																</tr>
															
																<tr class="ISF_Amt_Stamp_Duty_Reim-row">
																	<td style="width: 50%;"><strong>Amount of
																			Stamp Duty Reimbursement</strong></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Amt_Stamp_Duty_Reim}"
																		disabled="disabled" name="ISF_Additonal_Stamp_Duty_EX"></td>
																</tr>
																<tr class="ISF_Additonal_Stamp_Duty_EX-row">
																	<td><label>Additional Stamp Duty exemption
																			@20% upto maximum of 100% in case of industrial
																			undertakings having 75% equity owned by Divyang/SC/
																			ST/Females Promoters</label></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Additonal_Stamp_Duty_EX}" disabled="disabled"
																		name="ISF_Amt_Stamp_Duty_Reim"></td>
																</tr>
																<tr class="ISF_Ttl_Stamp_Duty_EX_Row" >
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
												<div class="col-sm-12">
													<div class="table-responsive">
														<table
															class="table table-stripped table-bordered loc-stage-table">
															<tbody>
																<tr class="ISF_Epf_Reim_UW-row">
																	<td style="width: 50%;"><strong>EPF
																			Reimbursement (100 or more unskilled workers)</strong></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Epf_Reim_UW}" disabled="disabled"
																		name="ISF_Epf_Reim_UW"></td>
																</tr>
																<tr class="ISF_Add_Epf_Reim_SkUkW-row">
																	<td><label>Additional 10% EPF
																			Reimbursement (200 direct skilled and unskilled
																			workers)</label></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Add_Epf_Reim_SkUkW}" disabled="disabled"
																		name="ISF_Add_Epf_Reim_SkUkW"></td>
																</tr>
																<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
																	<td><label>Additional 10% EPF
																			Reimbursement upto maximum of 70% in case of
																			industrial undertakings having 75% equity owned by
																			Divyang/SC/ST/Females Promoters</label></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Add_Epf_Reim_DIVSCSTF}"
																		disabled="disabled" name="ISF_Add_Epf_Reim_DIVSCSTF"></td>
																</tr>
																<tr class="ISF_Total_EPF_Row">
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
												<div class="col-sm-12">
													<div class="table-responsive">
														<table
															class="table table-stripped table-bordered loc-stage-table">
															<tbody>
																<tr  class="ISF_Cis-row">
																	<td style="width: 50%;"><strong>Capital
																			Interest Subsidy</strong></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Cis}" disabled="disabled" name="ISF_Cis"></td>
																</tr>
																<tr class="ISF_ACI_Subsidy_Indus-row">
																	<td><label>Additional Capital Interest
																			Subsidy@2.5% upto maximum of 7.5% in case of
																			industrial undertakings having 75% equity owned by
																			Divyang/SC/ST/Females Promoters</label></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_ACI_Subsidy_Indus}" disabled="disabled"
																		name="ISF_ACI_Subsidy_Indus"></td>
																</tr>
																<tr class="ISF_Infra_Int_Subsidy-row">
																	<td style="width: 50%;"><strong>Infrastructure
																			Interest Subsidy</strong></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Infra_Int_Subsidy}" disabled="disabled"
																		name="ISF_Infra_Int_Subsidy"></td>
																</tr>
																<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
																	<td><label>Additional Infrastructure
																			Interest Subsidy @2.5% upto maximum of 7.5% in case
																			of industrial undertakings having 75% equity owned by
																			Divyang/SC/ST/Females Promoters</label></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_AII_Subsidy_DIVSCSTF}"
																		disabled="disabled" name="ISF_AII_Subsidy_DIVSCSTF"></td>
																</tr>
																<tr class="ISF_Loan_Subsidy-row">
																	<td style="width: 50%;"><strong>Interest
																			Subsidy on loans for industrial research, quality
																			improvement, etc.</strong></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Loan_Subsidy}" disabled="disabled"
																		name="ISF_Loan_Subsidy"></td>
																</tr>
																<tr class="ISF_Total_Int_Subsidy_Row">
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
												<div class="col-sm-12">
													<div class="table-responsive">
														<table
															class="table table-stripped table-bordered loc-stage-table">
															<tbody>
																<tr class="ISF_Tax_Credit_Reim-row">
																	<td style="width: 50%;">Reimbursement of
																		Disallowed Input Tax Credit on plant, building
																		materials, and other capital goods.</td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Tax_Credit_Reim}" disabled="disabled"
																		name="ISF_Tax_Credit_Reim"></td>
																</tr>
																<tr class="ISF_EX_E_Duty-row">
																	<td>Exemption from Electricity Duty from captive
																		power for self-use</td>
																	<td><input type="text" class="form-control"
																		value="${ISF_EX_E_Duty}" disabled="disabled"
																		name="ISF_EX_E_Duty"></td>
																</tr>
																<tr  class="ISF_EX_E_Duty_PC-row">
																	<td>Exemption from Electricity duty on power drawn
																		from power companies</td>
																	<td><input type="text" class="form-control"
																		value="${ISF_EX_E_Duty_PC}" disabled="disabled"
																		name="ISF_EX_E_Duty_PC"></td>
																</tr>
																<tr  class="ISF_EX_Mandee_Fee-row">
																	<td>Exemption from Mandi Fee</td>
																	<td><input type="text" class="form-control"
																		value="${ISF_EX_Mandee_Fee}" disabled="disabled"
																		name="ISF_EX_Mandee_Fee"></td>
																</tr>
																<tr class="ISF_Indus_Payroll_Asst-row">
																	<td>Industrial units providing employment to
																		differently abled workers will be provided payroll
																		assistance of Rs. 500 per month for each such worker.</td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Indus_Payroll_Asst}" disabled="disabled"
																		name="ISF_Indus_Payroll_Asst"></td>
																</tr>
																<tr class="Total_Other_Incentive_Row">
																	<td><strong>Total Other Incentives</strong></td>
																	<td><input type="text" class="form-control"
																		value="${Total_Other_Incentive}" disabled="disabled"
																		name="Total_Other_Incentive"></td>
																</tr>
																<tr id="showHide1">
																	<td>Do you have any other Additional Request</td>
																	<td><input type="text" class="form-control"
																		value="${othAddRequest1}" disabled="disabled"
																		name="othAddRequest1"></td>
																</tr>

																<tr id="showHide2">
																	<td><strong>Do you want to avail
																			customised incentives</strong></td>
																	<td><input type="text" class="form-control"
																		value="${ISF_Cstm_Inc_Status}" disabled="" name=""></td>
																</tr>
																<c:if test="${ not empty availCustomisedDetailsList}">
																<tr id="showHide3">
																	<td colspan="2">
																		<table
																			class="table table-stripped table-bordered directors-table"
																			id="customFields">
																		<%-- 	<c:if test="${ not empty availCustomisedDetailsList}"> --%>
																				<thead>
																					<tr>
																						<th>Incentive Type</th>
																						<th>Amount</th>
																						<th>Additional Request</th>
																						<th>Type Details of Customised Incentives</th>
																					</tr>
																				</thead>
																			
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
																	</td>
																</tr>
</c:if>
																<tr id="showHide4">
																	<td>Upload Related Document</td>
																	<td><a
																		href="./downloadDocIncentive?fileName=${isfCustIncDocName}">${isfCustIncDocName}</a>
																	</td>
																</tr>
																
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>

										<!-- <div class="isf-form mt-4">
									<hr class="mt-2">
									<div class="row">
										<div class="col-sm-4">
											<button onclick="closeCurrentTab()"
												class="btn btn-outline-danger mb-3">Close</button>
										</div>
										<div class="col-sm-8 text-right">
											<a href="./editApplicantForm"
												onclick="return confirm('Are you sure you want edit records?')"
												class="btn btn-outline-info mb-3"><i class="fa fa-edit"></i>
												Edit</a>
											<button class="btn btn-outline-success mb-3" id="formId1">
												<i class="fa fa-download"></i>Download Form
											</button>
											<button type="button" id="printMethod"
												class="btn btn-outline-secondary mb-3">
												<i class="fa fa-print"></i> e-Print
											</button>
										</div>
									</div>
								</div> -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<footer id="removebottomId">
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
	<script>
		function closeCurrentTab() {
			var conf = confirm("Are you sure, you want to close this tab?");
			if (conf == true) {
				close();
			}
		}
	</script>
</body>

</html>