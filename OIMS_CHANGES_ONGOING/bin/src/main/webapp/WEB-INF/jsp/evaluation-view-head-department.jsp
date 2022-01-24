<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!doctype html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Evaluation View Head Department</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">


<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="js/custom.js"></script>
<script type="text/javascript">
	window.onload = function() {

		if ('${enableIncludeAgenda}' != '') {
			document.getElementById("button2").disabled = false;
			document.getElementById("button4").disabled = false;
		}
	}

	function sendAcknowledgeInvestor() {
		var r = confirm("Are you Sure Want to Send the Acknowledgement to Investor?");
		var apcId = '${appId}';
		if (r == true) {
			alert("Your Application ID - "
					+ apcId
					+ " Evaluated Successfully, Please Keep your Application ID for Your Reference in the Future.");
			// return false
			//window.location.href = 'https://niveshmitra.up.nic.in/Default.aspx';
		} else {
			return false
		}

	}

	function Saveconfirm() {
		var r = confirm("Are you Sure Want to Save the Data ?");

		if (r == true) {
			alert("Application Data Saved Successfully");
		} else {
			return false
		}

	}

	function RaiseQuery() {
		var r = confirm("Are you Sure Want to Submit the Query ?");

		if (r == true) {
			alert("Query Raised Successfully");
		} else {
			return false
		}

	}

	function includeAgendaNote() {
		var r = confirm("Are you Sure Want to Include Application in Agenda Note?");

		if (r == true) {
			alert("Application Included In Prepare Agenda Note Successfully");
		} else {
			return false
		}

	}

	function enableButton2() {

		var r = confirm("Are you Sure Want to Complete the Evaluation?");

		if (r == true) {
			alert("Evaluation Completed Successfully");
			document.getElementById("button2").disabled = false;
			document.getElementById("button4").disabled = false;
			document.getElementById("button5").disabled = false;
			document.getElementById("button6").disabled = false;
		} else {
			return false
		}
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

	$(document).ready(MFCostasperDPR);
	$(document).on("keyup", MFCostasperDPR);

	function MFCostasperDPR() {
		var sum = 0;
		$(".MFCostasperDPR").each(function() {
			sum += +$(this).val();
		});
		$(".TotalMFCostasperDPR").val(sum);
	}

	$(document).ready(MFCostasperIIEPP);
	$(document).on("keyup", MFCostasperIIEPP);

	function MFCostasperIIEPP() {
		var sum = 0;
		$(".MFCostasperIIEPP").each(function() {
			sum += +$(this).val();
		});
		$(".TotalMFCostasperIIEPP").val(sum);
	}

	var iiepland = 0;
	function landcostIIEP() {
		var landcostIIEPRule = document.getElementById("TotalCostasperDPR1").value;
		var a = parseInt("landcostIIEPRule");
		iiepland = landcostIIEPRule / 4;
		document.getElementById("iiepland").value = iiepland;
	}
	$(document).ready(landcostIIEP);
	$(document).ready(CostasperIIEPP);
	$(document).on("change", landcostIIEP);

	function myEditInput() {

		var r = confirm('Are you Sure Want to Edit the Application Data?');

		if (r == true) {
			$(".form-control").removeAttr("disabled");
		} else {
			return false
		}
	}

	function showYesOrNo1() {
		var natureOfProject = '${natureOfProject}';
		if (natureOfProject === 'NewProject') {

			$(".hide-tbl-row").hide();
		}

		else
			$(".hidep-tbl-row").hide();

	}
	$(document).on("change", showYesOrNo1);
	$(document).ready(showYesOrNo1);

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
	$(function() {
		$("#UploadDocumentForQuery").change(function() {
			if (fileExtValidate(this)) {
				if (FileSizeValidate(this)) {
					return true;
				}
			}
		});

		// File extension validation, Add more extension you want to allow
		var validExt = ".pdf";
		function fileExtValidate(fdata) {
			var filePath = fdata.value;
			var getFileExt = filePath.substring(filePath.lastIndexOf('.') + 1)
					.toLowerCase();
			var pos = validExt.indexOf(getFileExt);
			if (pos < 0) {
				document.getElementById("UploadDocumentForQuery").value = '';
				document.getElementById("UploadDocumentForQuery1").innerHTML = '';
				alert("Please upload PDF file format.");

				return false;
			} else {
				return true;
			}
		}

		// photo file size validation
		// size in mb
		function FileSizeValidate(fdata) {
			var maxSize = '2';//size in KB
			if (fdata.files && fdata.files[0]) {
				var fsize = fdata.files[0].size / (1024 * 1024);
				if (fsize > maxSize) {
					alert('File size should not be more than 2MB. The uploaded file size is: '
							+ fsize + " MB");
					document.getElementById("UploadDocumentForQuery").value = '';
					document.getElementById("UploadDocumentForQuery1").innerHTML = '';
					return false;
				} else {
					return true;
				}
			}
		}

	});

	function validateNumberField() {
		return event.charCode > 47 && event.charCode < 58;
	}
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
						<li class="nav-item"><a class="nav-link"
							href="./nodalDeptHeadDashboard"><i class="fa fa-tachometer"></i>
								Dashboard</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./viewAndEvaluateNodalDept"><i class="fa fa-eye"></i>
								View Applications</a></li>

						<li class="nav-item"><a class="nav-link"
							href="./preAgenNoteheadNodDepart"><i class="fa fa-list"></i>
								Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link" href="./nodalmomgo"><i
								class="fa fa-calendar"></i> View Minutes of Meeting and GO's</a></li>
					</ul>
				</div>
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">

					<div class="card card-block p-3">
						<form:form modelAttribute="applicationAgendaNote" class="mt-4"
							name="applicationAgendaNote" method="post">
							<!-- <div class="row mt-4">
								<div class="col-sm-12 text-center">


									<button type="button" id="button1" onclick="enableButton2()"
										class="btn btn-outline-success btn-sm mb-3">Evaluation
										Completed</button>
									<button type="button" disabled="disabled" id="button5"
										onclick="return sendAcknowledgeInvestor()"
										class="btn btn-outline-info btn-sm mb-3">Send
										Acknowledgement to Investor</button>
									<a href="#RaiseQuery"
										class="btn btn-outline-danger btn-sm mb-3" data-toggle="modal">Raise
										Query</a>

									<button type="submit" id="button2"
										class="btn btn-outline-info btn-sm mb-3" disabled="disabled"
										formaction="applicationAgendaNote"
										onclick="return includeAgendaNote()">Include
										Application in Agenda Note</button>

								</div>
							</div> -->
							<hr class="mt-2">
							<div class="row">
								<div class="col-sm-5">
									<a
										href="./viewNodalDeptHeadApplicationDetails?applicantId=<%=session.getAttribute("appId")%>"
										class="common-default-btn mt-3">Back</a>
								</div>
								<!-- <div class="col-sm-7 text-right">
									<a href="#EvaluationAuditTrail" class="text-info mr-3"
										data-toggle="modal">Evaluation Audit Trail</a>
									<button type="button" class="common-btn mt-3"
										onclick="myEditInput()">
										<i class="fa fa-edit"></i> Edit
									</button>
								</div> -->
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="mt-4">
										<div class="without-wizard-steps">
											<h4
												class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">(A).
												Evaluate Application</h4>
										</div>
									</div>
									<hr class="mt-2 mb-5">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-bordered">
													<tbody>
														<tr>
															<td style="width: 50%;"><label class="table-heading">New
																	Project or Expansion / Diversification</label></td>
															<td colspan="6"><input type="text"
																readonly="readonly" class="form-control"
																value="${natureOfProject}" disabled="disabled"></td>


															<!-- 	<td class="text-center" style="width: 10%;"><a
																	href="javacript:void(0);"
																	class="btn btn-outline-success btn-sm"><i
																		class="fa fa-edit"></i> Edit</a> <a href="javacript:void(0);" class="btn btn-outline-info btn-sm">Save</a>
																</td> -->
														</tr>
														<tr class="hidep-tbl-row">
															<td><label class="table-heading">Product
																	Details</label></td>
															<td colspan="6"><input type="text"
																class="form-control" name=""></td>
															<!-- 	<td class="text-center" style="width: 10%;"><a
																	href="javacript:void(0);"
																	class="btn btn-outline-success btn-sm"><i
																		class="fa fa-edit"></i> Edit</a></td> -->
														</tr>
														<tr>
															<td colspan="3" class="p-4">

																<div class="table-responsive mt-3">
																	<table
																		class="hide-tbl-row table table-stripped table-bordered">
																		<thead>
																			<tr>
																				<th style="width: 50%;">Particulars</th>
																				<th colspan="2">Details</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td>Existing Products</td>
																				<td colspan="2"><input type="text"
																					class="form-control" value="${ExistingProducts}"
																					disabled="" name=""></td>
																			</tr>
																			<tr>
																				<td>Existing Installed Capacity</td>
																				<td><input type="text" class="form-control"
																					value="${existingInstalledCapacity}" disabled=""
																					name=""></td>
																				<td style="width: 17%;"><select
																					class="form-control" disabled="">
																						<option value="0">Select Unit</option>
																						<option value="1">Sq.Ft.</option>
																						<option value="2">Unit-Wise Count</option>
																						<option value="3" selected="">Tonnes</option>
																						<option value="4">TPH/Unit</option>
																						<option value="5">MT</option>
																						<option value="6">Litres</option>
																						<option value="8">Kg</option>
																				</select></td>
																			</tr>
																			<tr>
																				<td>Proposed Products</td>
																				<td colspan="2"><input type="text"
																					class="form-control" value="${proposedProducts}"
																					disabled="" name=""></td>
																			</tr>
																			<tr>
																				<td>Proposed Installed Capacity</td>
																				<td><input type="text" class="form-control"
																					value="${proposedInstalledCapacity}" disabled=""
																					name=""></td>
																				<td style="width: 17%;"><select
																					class="form-control" disabled="">
																						<option value="0">Select Unit</option>
																						<option value="1" selected="">Sq.Ft.</option>
																						<option value="2">Unit-Wise Count</option>
																						<option value="3">Tonnes</option>
																						<option value="4">TPH/Unit</option>
																						<option value="5">MT</option>
																						<option value="6">Litres</option>
																						<option value="8">Kg</option>
																				</select></td>
																			</tr>
																			<tr>
																				<td>Existing Gross Block</td>
																				<td colspan="2"><input type="text"
																					class="form-control" value="${existingGrossBlock}"
																					disabled="" name=""></td>
																			</tr>
																			<tr>
																				<td>Proposed Gross Block</td>
																				<td colspan="2"><input type="text"
																					class="form-control" value="${proposedGrossBlock }"
																					disabled="" name=""></td>
																			</tr>
																		</tbody>
																	</table>
																</div>

																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Location</label>
																	</div>
																	<!-- 																<div class="col-sm-6 text-right">
																			<a href="javacript:void(0);"
																				class="btn btn-outline-success btn-sm"><i
																				class="fa fa-edit"></i> Edit</a>
																		</div>
		 -->
																</div>

																<div class="table-responsive mt-3">
																	<table class="table table-stripped table-bordered">
																		<tbody>
																			<tr>
																				<td style="width: 50%;">Full Address</td>
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
																				<td>Region</td>
																				<td><input type="text" class="form-control"
																					value="${resionName}" disabled="disabled"
																					id="resionName" name="resionName"></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr>
															<td colspan="3" class="p-4">

																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Employment</label>
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
																			<tr>
																				<td style="width: 50%;">Total Direct Employment</td>
																				<td><input type="text" class="form-control"
																					value="${grossTotalMaleandFemaleEmployment}"
																					readonly="readonly" name=""></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>

														<tr>
															<td><label class="table-heading">If
																	Expansion / Diversification whether proposed gross
																	block more then 25% of the existing gross block</label></td>
															<td><input type="text" class="form-control"
																value="${pergrosblock}" readonly="readonly"></td>
															<!-- <td class="text-center" style="width: 10%;"><a
																	href="javacript:void(0);"
																	class="btn btn-outline-success btn-sm"><i
																		class="fa fa-edit"></i> Edit</a></td> -->
														</tr>


														<tr>
															<td><label class="table-heading">Category of
																	Industrial Undertaking</label></td>
															<td>
																<%-- <select class="form-control" readonly="readonly">
																			<option value="">${category}</option>
																			<option value="1">Small</option>
																			<option value="2">Medium</option>
																			<option value="3">Large</option>
																			<option value="4">Mega</option>
																			<option value="5">Mega Plus</option>
																			<option value="6">Super Mega</option>
																	</select> --%> <input type="text" class="form-control"
																value="${category}" readonly="readonly">
															</td>
															<!-- <td class="text-center" style="width: 10%;"><a
																	href="javacript:void(0);"
																	class="btn btn-outline-success btn-sm"><i
																		class="fa fa-edit"></i> Edit</a></td> -->
														</tr>

														<tr>
															<td colspan="3" class="p-4">

																<div class="row">
																	<div class="col-sm-12">
																		<label class="table-heading">Project
																			Investment Details</label>
																	</div>
																</div>

																<div class="table-responsive mt-3">
																	<table class="table table-stripped table-bordered">
																		<tbody>
																			<tr>
																				<td>Proposed Capital Investment</td>
																				<td><input id="invFci" path="invFci"
																					class="form-control" value="${InvFCI}"
																					readonly="readonly"></td>
																				<!-- <td style="width: 10%;"><a
																						href="javacript:void(0);"
																						class="btn btn-outline-success btn-sm"> <i
																							class="fa fa-edit"> </i> Edit
																					</a></td> -->
																			</tr>
																			<tr>
																				<td>Total Cost of Project</td>
																				<td><input id="invtpc" path="invtpc"
																					class="form-control" value="${InvTPC}"
																					readonly="readonly"></td>
																				<!-- <td><a href="javacript:void(0);"
																						class="btn btn-outline-success btn-sm"><i
																							class="fa fa-edit"></i> Edit</a></td> -->
																			</tr>
																		</tbody>
																	</table>
																</div>

																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Break up of
																			proposed capital Investment</label>
																	</div>
																	<!-- <div class="col-sm-6 text-right">
																			<a href="javacript:void(0);"
																				class="btn btn-outline-success btn-sm"><i
																				class="fa fa-edit"></i> Edit</a>
																		</div> -->
																</div>

																<div class="table-responsive mt-3">
																	<table id="BreakUpTable"
																		class="table table-stripped table-bordered">
																		<thead>
																			<tr>
																				<!-- <th>Select</th> -->
																				<th>Item</th>
																				<th>Consider as FCI</th>
																				<th>Investment as per DPR or Document Provided</th>
																				<th>Capital Investment Eligible as per IIEPP
																					Policy Rule</th>
																				<th>Remarks</th>
																			</tr>
																		</thead>
																		<tbody class="add-from-here">
																			<tr>
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="selectItem1"
																							name="SlectItem"> <label
																							class="custom-control-label" for="selectItem1"></label>
																					</div>
																				</td> -->
																				<td>Land Cost</td>
																				<td><select class="form-control"
																					disabled="disabled">
																						<option value="1">Yes</option>
																						<option value="2">No</option>
																				</select></td>
																				<td><input class="CostasperDPR form-control"
																					type="text" name="" value="${InvLC}"
																					readonly="readonly"></td>
																				<td><input type="text"
																					class="CostasperIIEPP form-control" name=""
																					id="iiepland" disabled="disabled"></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																			<tr>
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="selectItem2"
																							name="SlectItem"> <label
																							class="custom-control-label" for="selectItem2"></label>
																					</div>
																				</td> -->
																				<td>Building,Site Development & Civil Works
																					Cost</td>
																				<td><select class="form-control"
																					disabled="disabled">
																						<option value="1">Yes</option>
																						<option value="2">No</option>
																				</select></td>
																				<td><input type="text"
																					class="CostasperDPR form-control" name=""
																					value="${InvBuiildC}" readonly="readonly"></td>
																				<td><input type="text"
																					class="CostasperIIEPP form-control" name=""
																					disabled="disabled"></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																			<tr>
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="selectItem3"
																							name="SlectItem"> <label
																							class="custom-control-label" for="selectItem3"></label>
																					</div>
																				</td> -->
																				<td>Plant & Machinery Cost</td>
																				<td><select class="form-control"
																					disabled="disabled">
																						<option value="1">Yes</option>
																						<option value="2">No</option>
																				</select></td>
																				<td><input type="text"
																					class="CostasperDPR form-control" name=""
																					value="${invPlantAndMachCost}" readonly="readonly"></td>
																				<td><input type="text"
																					class="CostasperIIEPP form-control" name=""
																					disabled="disabled"></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																			<tr>
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="selectItem4"
																							name="SlectItem"> <label
																							class="custom-control-label" for="selectItem4"></label>
																					</div>
																				</td> -->
																				<td>Miscellaneous Fixed Asset(MFA)</td>
																				<td><select class="form-control"
																					disabled="disabled">
																						<option value="1">Yes</option>
																						<option value="2">No</option>
																				</select></td>
																				<td><input type="text"
																					class=" CostasperDPR form-control" name=""
																					value="${invMisFixCost}" readonly="readonly"></td>
																				<td><input type="text"
																					class="CostasperIIEPP form-control" name=""
																					disabled="disabled"></td>
																				<td><input type="text" class="form-control"
																					name=""></td>
																			</tr>
																			<!-- <tr>
																				<td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="selectItem5"
																							name="SlectItem" id="chekbox"> <label
																							class="custom-control-label" for="selectItem5"></label>
																					</div>
																				</td>
																				<td><input type="text" class="form-control"
																					name="" id="fname"></td>
																				<td><select class="form-control" id="fname1">
																						<option value="1">Yes</option>
																						<option value="2">No</option>
																				</select></td>
																				<td><input type="text"
																					class="CostasperDPR form-control" id="fname2"
																					onchange="return landcostIIEP()" name=""></td>
																				<td><input type="text"
																					class="CostasperIIEPP form-control" id="fname3"
																					name="""></td>
																				<td><input type="text" class="form-control"
																					id="fname4" name=""></td>

																			</tr> -->
																		</tbody>
																		<tfoot>
																			<tr>
																				<td colspan="2" align="right"><strong>Total:</strong></td>
																				<td><input type="text" readonly="readonly"
																					class="TotalCostasperDPR form-control"
																					name="dprtotal" id="TotalCostasperDPR1"></td>
																				<td><input
																					class="TotalCostasperIIEPP form-control"
																					readonly="readonly" name=""></td>
																				<td></td>
																			</tr>
																			<tr>
																				<!-- <td colspan="6">
																					<button type="button"
																						class="btn btn-outline-success add-row-break-up">
																						<i class="fa fa-plus"></i> Add More Items
																					</button>
																				</td> -->
																			</tr>
																		</tfoot>
																	</table>
																</div> <%-- <div class="row">
																		<div class="col-sm-6">
																			<label class="table-heading">Means of Financing</label>
																		</div>
																		<div class="col-sm-6 text-right">
																			<a href="javacript:void(0);"
																				class="btn btn-outline-success btn-sm"><i
																				class="fa fa-edit"></i> Edit</a>
																		</div>
																	</div>
		
																	<div class="table-responsive mt-3">
																		<table class="table table-stripped table-bordered">
																			<thead>
																				<tr>
																					<th>Select</th>
																					<th>Item</th>
																					<th>Consider as FCI</th>
																					<th>Cost as per DPR</th>
																					<th>Cost Eligible as per IIEPP Policy Rule</th>
																					<th>Remarks</th>
																				</tr>
																			</thead>
																			<tbody>
																				<tr>
																					<td>
																						<div class="custom-control custom-checkbox mb-4">
																							<input type="checkbox" class="custom-control-input"
																								id="selectItem1" name="SlectItem"> <label
																								class="custom-control-label" for="selectItem1"></label>
																						</div>
																					</td>
																					<td>Equity Share Capital</td>
																					<td><select class="form-control">
																							<option value="1">Yes</option>
																							<option value="2">No</option>
																					</select></td>
																					<td><input type="text"
																						class="MFCostasperDPR form-control" name=""
																						value="${invEqShCapital}"></td>
																					<td><input type="text"
																						class="MFCostasperIIEPP form-control" name=""></td>
																					<td><input type="text" class="form-control"
																						name=""></td>
																				</tr>
																				<tr>
																					<td>
																						<div class="custom-control custom-checkbox mb-4">
																							<input type="checkbox" class="custom-control-input"
																								id="selectItem2" name="SlectItem"> <label
																								class="custom-control-label" for="selectItem2"></label>
																						</div>
																					</td>
																					<td>Internal Cash Accruals</td>
																					<td><select class="form-control">
																							<option value="1">Yes</option>
																							<option value="2">No</option>
																					</select></td>
																					<td><input type="text"
																						class="MFCostasperDPR form-control" name=""
																						value="${invEqIntCashAccrl}"></td>
																					<td><input type="text"
																						class="MFCostasperIIEPP form-control" name=""></td>
																					<td><input type="text" class="form-control"
																						name=""></td>
																				</tr>
																				<tr>
																					<td>
																						<div class="custom-control custom-checkbox mb-4">
																							<input type="checkbox" class="custom-control-input"
																								id="selectItem3" name="SlectItem"> <label
																								class="custom-control-label" for="selectItem3"></label>
																						</div>
																					</td>
																					<td>Interest Free Unsecured Loans</td>
																					<td><select class="form-control">
																							<option value="1">Yes</option>
																							<option value="2">No</option>
																					</select></td>
																					<td><input type="text"
																						class="MFCostasperDPR form-control" name=""
																						value="${invEqIntFreeUnsecLoan}"></td>
																					<td><input type="text"
																						class="MFCostasperIIEPP form-control" name=""></td>
																					<td><input type="text" class="form-control"
																						name=""></td>
																				</tr>
																				<tr>
																					<td>
																						<div class="custom-control custom-checkbox mb-4">
																							<input type="checkbox" class="custom-control-input"
																								id="selectItem4" name="SlectItem"> <label
																								class="custom-control-label" for="selectItem4"></label>
																						</div>
																					</td>
																					<td>Security Deposit</td>
																					<td><select class="form-control">
																							<option value="1">Yes</option>
																							<option value="2">No</option>
																					</select></td>
																					<td><input type="text"
																						class="MFCostasperDPR form-control" name=""
																						value="${invEqSecDept}"></td>
																					<td><input type="text"
																						class="MFCostasperIIEPP form-control" name=""></td>
																					<td><input type="text" class="form-control"
																						name=""></td>
																				</tr>
																				<tr>
																					<td>
																						<div class="custom-control custom-checkbox mb-4">
																							<input type="checkbox" class="custom-control-input"
																								id="selectItem4" name="SlectItem"> <label
																								class="custom-control-label" for="selectItem4"></label>
																						</div>
																					</td>
																					<td>Advances from Dealers</td>
																					<td><select class="form-control">
																							<option value="1">Yes</option>
																							<option value="2">No</option>
																					</select></td>
																					<td><input type="text"
																						class="MFCostasperDPR form-control" name=""
																						value="${invEqAdvDealer}"></td>
																					<td><input type="text"
																						class="MFCostasperIIEPP form-control" name=""></td>
																					<td><input type="text" class="form-control"
																						name=""></td>
																				</tr>
																				<tr>
																					<td>
																						<div class="custom-control custom-checkbox mb-4">
																							<input type="checkbox" class="custom-control-input"
																								id="selectItem4" name="SlectItem"> <label
																								class="custom-control-label" for="selectItem4"></label>
																						</div>
																					</td>
																					<td>From Financial Institution (FI's)</td>
																					<td><select class="form-control">
																							<option value="1">Yes</option>
																							<option value="2">No</option>
																					</select></td>
																					<td><input type="text"
																						class="MFCostasperDPR form-control" name=""
																						value="${invDebtFi}"></td>
																					<td><input type="text"
																						class="MFCostasperIIEPP form-control" name=""></td>
																					<td><input type="text" class="form-control"
																						name=""></td>
																				</tr>
																				<tr>
																					<td>
																						<div class="custom-control custom-checkbox mb-4">
																							<input type="checkbox" class="custom-control-input"
																								id="selectItem4" name="SlectItem"> <label
																								class="custom-control-label" for="selectItem4"></label>
																						</div>
																					</td>
																					<td>From Bank</td>
																					<td><select class="form-control">
																							<option value="1">Yes</option>
																							<option value="2">No</option>
																					</select></td>
																					<td><input type="text"
																						class="MFCostasperDPR form-control" name=""
																						value="${invDebtBank}"></td>
																					<td><input type="text"
																						class="MFCostasperIIEPP form-control" name=""></td>
																					<td><input type="text" class="form-control"
																						name=""></td>
																				</tr>
																				<tr>
																					<td>
																						<div class="custom-control custom-checkbox mb-4">
																							<input type="checkbox" class="custom-control-input"
																								id="selectItem5" name="SlectItem"> <label
																								class="custom-control-label" for="selectItem5"></label>
																						</div>
																					</td>
																					<td><input type="text" class="form-control"
																						name=""></td>
																					<td><select class="form-control">
																							<option value="1">Yes</option>
																							<option value="2">No</option>
																					</select></td>
																					<td><input type="text"
																						class="MFCostasperDPR form-control" name=""></td>
																					<td><input type="text"
																						class="MFCostasperIIEPP form-control" name=""></td>
																					<td><input type="text" class="form-control"
																						name=""></td>
		
																				</tr>
																				<tr>
																					<td colspan="3" align="right"><strong>Total:</strong></td>
																					<td><input type="text" readonly="readonly"
																						class="TotalMFCostasperDPR form-control" name=""></td>
																					<td><input
																						class="TotalMFCostasperIIEPP form-control"
																						readonly="readonly" name=""></td>
																					<td></td>
																				</tr>
																				<tr>
																					<td colspan="6">
																						<button type="button"
																							class="btn btn-outline-success">
																							<i class="fa fa-plus"></i> Add More Fields
																						</button>
																					</td>
																				</tr>
																			</tbody>
																		</table>
																	</div>--%>



															</td>
														</tr>

														<tr>
															<td><label class="table-heading">Whether the
																	company have filed LOI/IEM/Udyam registration No.</label></td>
															<td><input type="text" class="form-control"
																value="${regorlic}" readonly="readonly" name=""></td>

															<!-- 	<td class="text-center" style="width: 10%;"><a
																	href="javacript:void(0);"
																	class="btn btn-outline-success btn-sm"><i
																		class="fa fa-edit"></i> Edit</a></td> -->
														</tr>

														<tr>
															<td><label class="table-heading">Industrial
																	Undertaking should not have Govt./Govt. undertaking
																	equity of more than 50%.</label></td>
															<td><select class="form-control">
																	<option value="1">Yes</option>
																	<option value="2" selected="">No</option>
															</select></td>
															<!-- <td class="text-center" style="width: 10%;"><a
																	href="javacript:void(0);"
																	class="btn btn-outline-success btn-sm"><i
																		class="fa fa-edit"></i> Edit</a></td> -->
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
																			<tr>
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
																			<tr>
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="EIP2"
																							name="SlectItem"> <label
																							class="custom-control-label" for="EIP2"></label>
																					</div>
																				</td> -->
																				<td>in case of large industrial undertakings,
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
																			<tr>
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="EIP3"
																							name="SlectItem"> <label
																							class="custom-control-label" for="EIP3"></label>
																					</div>
																				</td> -->
																				<td>In case of mega & mega plus industrial
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
																			<tr>
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="EIP4"
																							name="SlectItem"> <label
																							class="custom-control-label" for="EIP4"></label>
																					</div>
																				</td> -->
																				<td>in case of super mega industrial
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
															<td><label class="table-heading">The company
																	should not have invested more than 60% of the expected
																	capital investment before the effective date i.e.
																	13.07.2017.</label></td>
															<td><select class="form-control">
																	<option value="1" selected="">Yes</option>
																	<option value="2">No</option>
															</select></td>
															<!-- <td class="text-center" style="width: 10%;"><a
																	href="javacript:void(0);"
																	class="btn btn-outline-success btn-sm"><i
																		class="fa fa-edit"></i> Edit</a></td> -->
														</tr>

														<tr>
															<td><label class="table-heading">If the
																	project is being set up in a phased manner and the
																	commercial production is proposed in phases.</label></td>

															<td><input type="text" class="form-control"
																value="${phsWsAply}" readonly="readonly"></td>
														</tr>

														<tr>
															<td><label class="table-heading">Investment
																	made till date as per CA/Statutory Certificate</label></td>
															<td><input type="text" class="form-control"
																onkeypress="return validateNumberField()"
																placeholder="Amount in Rs." name=""></td>
															<!-- 	<td class="text-center" style="width: 10%;"><a
																	href="javacript:void(0);"
																	class="btn btn-outline-success btn-sm"><i
																		class="fa fa-edit"></i> Edit</a></td> -->
														</tr>

													</tbody>
												</table>
											</div>

										</div>
									</div>

									<div class="row">
										<div class="col-sm-12">
											<div class="mt-4">
												<div class="without-wizard-steps">
													<h4
														class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">(B).
														Incentive Sought</h4>
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
																		<th style="width: 25%;">Details of Incentives
																			Sought and Concerned Department</th>
																		<th style="width: 10%;">Amount of Incentives <small>(Rs.
																				in crores)</small></th>
																		<th>Incentive as per Rules</th>
																		<th style="width: 15%;">Remarks</th>
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
																			<td colspan="5"><form:textarea
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
																			B-P-M</strong>

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
																			<td colspan="5"><form:textarea
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
																			employerâs contribution to all such new
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
																			<td colspan="5"><form:textarea
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
																			<td colspan="5"><form:textarea
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
																	<tr class="ISF_Stamp_Duty_EX-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
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
																	<tr class="ISF_Amt_Stamp_Duty_Reim-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>
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
																	<tr class="ISF_Additonal_Stamp_Duty_EX-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>
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
																			employer’s contribution to all such new Industrial
																			undertakings providing direct employment to 100 or
																			more unskilled workers, after three years from the
																			date of commercial production for a period of 5
																			years.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>
																	<tr class="ISF_Epf_Reim_UW-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>
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
																			employer’s contribution to all such new Industrial
																			undertakings providing direct employment to 100 or
																			more unskilled workers, after three years from the
																			date of commercial production for a period of 5
																			years.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>
																	<tr class="ISF_Add_Epf_Reim_SkUkW-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>
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
																			employer’s contribution to all such new Industrial
																			undertakings providing direct employment to 100 or
																			more unskilled workers, after three years from the
																			date of commercial production for a period of 5
																			years.</td>
																		<td><textarea class="form-control"></textarea></td>
																	</tr>
																	<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>
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

																	<tr class="ISF_Cis-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>

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

																	<tr class="ISF_ACI_Subsidy_Indus-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>

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

																	<tr class="ISF_Infra_Int_Subsidy-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>

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

																	<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>

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

																	<tr class="ISF_Loan_Subsidy-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>

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

																	<tr class="ISF_Tax_Credit_Reim-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>

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

																	<tr class="ISF_EX_E_Duty-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>

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
																			<td colspan="5"><form:textarea
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
																			<td colspan="5"><form:textarea
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

																	<tr class="ISF_Indus_Payroll_Asst-row">
																		<td colspan="5"><textarea class="form-control"
																				disabled="" rows="3" placeholder="Comments"></textarea>
																		</td>
																	</tr>

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
																	readonly="true" class="form-control" placeholder=""
																	maxlength="250" name="othAddRequest1"></form:input>
															</div>
														</div>
													</div>
												</c:if>
												<!-- <div class="row mt-4">
													<div class="col-sm-12 text-center">
														<button type="button" id="button3"
															onclick="enableButton2()"
															class="btn btn-outline-success btn-sm mb-3">Evaluation
															Completed</button>
														<button type="button" disabled="disabled" id="button6"
															onclick="return sendAcknowledgeInvestor()"
															class="btn btn-outline-info btn-sm mb-3">Send
															Acknowledgement to Investor</button>
														<a href="#RaiseQuery"
															class="btn btn-outline-danger btn-sm mb-3"
															data-toggle="modal">Raise Query</a>

														<button type="submit" id="button4"
															class="btn btn-outline-info btn-sm mb-3"
															disabled="disabled" formaction="applicationAgendaNote"
															onclick="return includeAgendaNote()">Include
															Application in Agenda Note</button>
													</div>
												</div> -->
											</form:form>

											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-5">
													<a
														href="./viewNodalDeptHeadApplicationDetails?applicantId=<%=session.getAttribute("appId")%>"
														class="common-default-btn mt-3">Back</a>
												</div>
												<!-- <div class="col-sm-7 text-right">
													<a href="javacript:void(0);" onclick="myEditInput()"
														class="common-btn mt-3"><i class="fa fa-edit"></i>
														Edit</a> <a href="#" onclick="return Saveconfirm()"
														class="common-btn mt-3">Save</a>
												</div> -->
											</div>
										</div>
									</div>
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
									<label>Clarification Sought</label>
									<textarea class="form-control"></textarea>
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