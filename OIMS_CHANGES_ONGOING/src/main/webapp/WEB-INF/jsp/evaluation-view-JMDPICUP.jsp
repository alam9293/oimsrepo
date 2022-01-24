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
<title>Evaluation View MD PICUP</title>
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
	function myEditInput() {

		$(".form-control").removeAttr("disabled");
		//$(".form-control").removeAttr("readonly");

		//var x = document.getElementsByClassName("form-control");
		//x[0].removeAttribute("disabled");
		//document.getElementById("demo").innerHTML = "The p element above is now editable. Try to change its text.";
	}

	function showYesOrNo() {

		var pergrosblock = '${pergrosblock}';
		if (pergrosblock === 'eligible') {
			alert("in yes");
		} else {
			alert("in No");
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

	function deleteRow(id, row) {
		document.getElementById(id).deleteRow(row);
	}

	$(document).ready(landcostIIEP);
	$(document).ready(CostasperIIEPP);
	$(document).on("change", landcostIIEP);

	function insRow(id) {
		var filas = document.getElementById("myTable").rows.length;
		var x = document.getElementById(id).insertRow(filas);
		var a = x.insertCell(0);
		var b = x.insertCell(1);
		var c = x.insertCell(2);
		var d = x.insertCell(3);
		var e = x.insertCell(4);
		var f = x.insertCell(5);
		//var z = x.insertCell(5);
		a.innerHTML = '<input type="checkbox" id="chekbox0">';
		b.innerHTML = '<input type="select" id="fname1">';
		c.innerHTML = '<input type="text" id="fname2">';
		d.innerHTML = '<input type="text" id="fname3">';
		e.innerHTML = '<input type="text" id="fname4">';
		f.innerHTML = '<input type="text" id="fname5">';
		//  z.innerHTML ='<input type="button" id="btn"  value=" Delete " name="btn"  ></button>';
	}

	function enableButton2() {
		document.getElementById("button2").disabled = false;
		document.getElementById("button3").disabled = false;
		alert("Evaluation Completed");
	}

	var iiepland = 0;
	function landcostIIEP() {
		var landcostIIEPRule = document.getElementById("TotalCostasperDPR1").value;
		var a = parseInt("landcostIIEPRule");
		iiepland = landcostIIEPRule / 4;
		document.getElementById("iiepland").value = iiepland;
	}
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
				alert("Please upload PDF file format.");
				document.getElementById("UploadDocumentForQuery").value = "";
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
					document.getElementById("UploadDocumentForQuery").value = "";
					return false;
				} else {
					return true;
				}
			}
		}

	});
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
							href="./dashboardJMDPICUP"><i class="fa fa-tachometer"></i>
								Dashboard</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./selectPolicyJMDPICUP"><i class="fa fa-eye"></i> View
								Applications</a></li>
						<!-- <li class="nav-item"><a class="nav-link "
							href="./viewSMEJMDPICUPApplications"><i class="fa fa-eye"></i>
								View SME Applications</a></li> -->
						<li class="nav-item"><a class="nav-link" href="./jmdPiCupAgendaNote"><i
								class="fa fa-list"></i> Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link" href="./momgobyJMDPICUP"><i
								class="fa fa-calendar"></i> View Minutes of Meeting and GO's</a></li>
					</ul>
				</div>
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<div class="card card-block p-3">
						<form:form modelAttribute="applicationAgendaNote" class="mt-4"
							name="applicationAgendaNote" method="post">
							<div class="row">
								<div class="col-sm-5">
									<a          
										href="./viewJMDPICUPApplicationDetails?applicantId=<%=session.getAttribute("appId")%>"
										class="common-default-btn mt-3">Back</a>
								</div>

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
															<td><label class="table-heading">New Project
																	or Expansion / Diversification</label></td>
															<td colspan="2"><input type="text"
																class="form-control" disabled="disabled" value="${natureOfProject}"
																disabled="disabled"></td>

														</tr>
														<tr>
															<td><label class="table-heading">Product
																	Details</label></td>
															<td colspan="2"><input type="text"
																class="form-control" disabled="disabled"  value="${webSiteName}" disabled="disabled" id="webSiteName"
																name="webSiteName"></td>
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
																			<td><input type="text" class="form-control" disabled="disabled"
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
														</tr>

														<tr>
															<td colspan="3" class="p-4">

																<div class="row">
																	<div class="col-sm-6">
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
																					disabled="" name=""></td>
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
																value="${pergrosblock}" disabled="disabled"></td>
														</tr>


														<tr>
															<td><label class="table-heading">Category of
																	Industrial Undertaking</label></td>
															<td>
																<%-- <select class="form-control" disabled="">
																	<option value="">${category}</option>
																	<option value="1">Small</option>
																	<option value="2">Medium</option>
																	<option value="3">Large</option>
																	<option value="4">Mega</option>
																	<option value="5">Mega Plus</option>
																	<option value="6">Super Mega</option>
															</select> --%> <input type="text" class="form-control"
																value="${category}" disabled="disabled">


															</td>

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
																				<td>Proposed Fixed Capital Investment</td>
																				<td><input id="invFci" path="invFci"
																					class="form-control" value="${InvFCI}" disabled=""></td>

																			</tr>
																			<tr>
																				<td>Total Cost of Project</td>
																				<td><input id="invtpc" path="invtpc"
																					class="form-control" value="${InvTPC}" disabled=""></td>

																			</tr>
																		</tbody>
																	</table>
																</div>

																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Break-up of Cost
																			of Project and Proposed Fixed Capital Investment</label>
																	</div>

																</div>

																<div class="table-responsive mt-3" >
																	<table id="myTable"
																		class="table table-stripped table-bordered" >
																		<thead>
																			<tr>
																				<!-- <th>Select</th> -->
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
																						<input type="checkbox"
																							class="custom-control-input" id="selectItem1"
																							name="SlectItem"> <label
																							class="custom-control-label" for="selectItem1"></label>
																					</div>
																				</td> -->
																				<td>Land & Site Development Cost</td>
																				<td><select class="form-control"
																					disabled="disabled">
																						<option value="1">Yes</option>
																						<option value="2">No</option>
																				</select></td>
																				<td><input class="CostasperDPR form-control"
																					type="text" name="" value="${InvLC}" disabled=""></td>
																				<td><input type="text"
																					class="CostasperIIEPP form-control" name="" 
																					id="iiepland" disabled="disabled"></td>
																				<td><input type="text" class="form-control" disabled="disabled"
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
																				<td>Building and Civil Work Cost</td>
																				<td><select class="form-control"
																					disabled="disabled">
																						<option value="1">Yes</option>
																						<option value="2">No</option>
																				</select></td>
																				<td><input type="text"
																					class="CostasperDPR form-control" name=""
																					value="${InvBuiildC}" disabled="disabled"></td>
																				<td><input type="text"
																					class="CostasperIIEPP form-control" name=""
																					disabled="disabled"></td>
																				<td><input type="text" class="form-control" disabled="disabled"
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
																					value="${invPlantAndMachCost}" disabled="disabled"></td>
																				<td><input type="text"
																					class="CostasperIIEPP form-control" name=""
																					disabled="disabled"></td>
																				<td><input type="text" class="form-control" disabled="disabled"
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
																				<td><input type="text" class="form-control" disabled="disabled"
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
																					<button type="button" onclick="insRow('myTable1')"
																						class="btn btn-outline-success">
																						<i class="fa fa-plus"></i> Add More Items
																					</button>
																				</td> -->
																			</tr>
																		</tbody>
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
															</div>
 --%>


															</td>
														</tr>

														<tr>
															<td><label class="table-heading">Whether the
																	company have filed LOI/IEM/Udyam registration No.</label></td>
															<td><select class="form-control" disabled="disabled">
																	<option value="1" selected="">${regorlic}</option>
																	<option value="2">No</option>
															</select></td>
															<!-- 	<td class="text-center" style="width: 10%;"><a
															href="javacript:void(0);"
															class="btn btn-outline-success btn-sm"><i
																class="fa fa-edit"></i> Edit</a></td> -->
														</tr>

														<tr>
															<td><label class="table-heading">Industrial
																	Undertaking should not have Govt./Govt. undertaking
																	equity of more than 50%.</label></td>
															<td><select class="form-control" disabled="disabled">
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

																				<td><select class="form-control" disabled="">
																						<c:choose>
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
																				</select></td>
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
																				<td><select class="form-control" disabled="">
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
																				</select></td>
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
																				<td><select class="form-control" disabled="">
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
																				</select></td>
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
																				<td><select class="form-control" disabled="">
																						<c:choose>
																							<c:when test="${ctypeSUPERMEGA eq 'Yes'}">
																								<option value="${ctypeSUPERMEGA}"
																									selected="selected">${ctypeSUPERMEGA}</option>
																								<option value="No">No</option>
																								<%-- <option value="${opt}" selected="selected">${opt}</option> --%>
																							</c:when>
																							<c:otherwise>
																								<option value="Yes">Yes</option>
																								<option value="${ctypeSUPERMEGA}"
																									selected="selected">${ctypeSUPERMEGA}</option>
																								<%-- 	<option value="${opt}">${opt}</option> --%>
																							</c:otherwise>
																						</c:choose>
																				</select></td>
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
															<td><select class="form-control" disabled="disabled" >
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
															<td><select class="form-control" disabled="">
																	<c:choose>
																		<c:when test="${phsWsAply eq 'Yes'}">
																			<option value="${phsWsAply}" selected="selected">${phsWsAply}</option>
																			<option value="No">No</option>
																			<%-- <option value="${opt}" selected="selected">${opt}</option> --%>
																		</c:when>
																		<c:otherwise>
																			<option value="Yes">Yes</option>
																			<option value="${phsWsAply}" selected="selected">${phsWsAply}</option>
																			<%-- 	<option value="${opt}">${opt}</option> --%>
																		</c:otherwise>
																	</c:choose>
															</select></td>
															<!-- 	<td class="text-center" style="width: 10%;"><a
															href="javacript:void(0);"
															class="btn btn-outline-success btn-sm"><i
																class="fa fa-edit"></i> Edit</a></td> -->
														</tr>

														<tr>
															<td><label class="table-heading">Investment
																	made till date</label></td>
															<td><input type="text" class="form-control" 
readonly="true
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
									<!-- 	<hr class="mt-2"> -->
									<%-- 	<div class="row">
									<div class="col-sm-5">
										<a
											href="./viewApplicationDetails?applicantId=<%=session.getAttribute("appId")%>"
											class="common-default-btn mt-3">Back</a>
									</div>
									<div class="col-sm-7 text-right">
										<a href="javacript:void(0);" class="common-btn mt-3"><i
											class="fa fa-print"></i> Print</a> <a
											href="./getFacilitiesRelief" class="common-btn mt-3">Next</a>
									</div>
								</div> --%>
									<!-- </div>
						</div>
					</div>
				</div> -->
									<!-- 				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<div class="card card-block p-3">
 -->
									<div class="row">
										<div class="col-sm-12">
											<div class="mt-4">
												<div class="without-wizard-steps">
													<h4
														class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">(B).
														INCENTIVES SOUGHT</h4>
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
																	<th>Sr. No.</th>
																	<th style="width: 25%;">Details of Incentive
																			Sought and Concerned Department</th>
																		<th style="width: 10%;">Amount of Incentives <small>(Rs.
																				in crores)</small></th>
																	<th>Incentive as per Rules</th>
																	<th style="width: 15%;">Remarks</th>
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

																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
																			target="_blank">As per Table 3</a> of Rules related
																			to IIEPP-2017 (The Rules), there is a provision for
																			reimbursement of 70% of deposited GST (Net SGST) for
																			10 years.</td>
																		<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td> -->
																		<td><textarea class="form-control"  readonly="true"></textarea></td>
																	</tr>

																	<tr>
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
																		<td><textarea class="form-control" readonly="true"></textarea></td>
																	</tr>

																	<tr>
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
																			employer’s contribution to all such new Industrial
																			undertakings providing direct employment to 100 or
																			more unskilled workers, after three years from the
																			date of commercial production for a period of 5
																			years.</td>
																		<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td> -->
																		<td><textarea class="form-control" readonly="true"></textarea></td>
																	</tr>

																	<tr>
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
																		<td><textarea class="form-control" readonly="true"></textarea></td>
																	</tr>

																	<tr>
																		<td>5</td>
																		<td>Exemption from Electricity Duty on power
																			drawn from power companies for 20 years <br> <strong>Energy/UPPCL/
																				Electricity Safety</strong>

																		</td>
																		<td><form:input path="ISF_EX_E_Duty_PC"
																				type="text" class="Other_Incentives form-control"
																				id="ISF_EX_E_Duty_PC" name="ISF_EX_E_Duty_PC"
																				maxlength="12" readonly="true"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.5</a> of the Rules,
																			there is a provision for Exemption from Electricity
																			Duty for 10 years to all new industrialundertakings
																			producing electricity from captive power plants for
																			self-use.</td>
																		<!-- <td><textarea class="form-control" disabled="">No Justification</textarea></td> -->
																		<td><textarea class="form-control" readonly="true"></textarea></td>
																	</tr>

																	<tr>
																		<td>6</td>
																		<td>Exemption from Mandi Fee for minimum 15 years
																			(100% for 20 years) <br> <strong>Agriculture
																				Marketing & Agriculture Foreign Trade/MandiParishad</strong>

																		</td>
																		<td><form:input path="ISF_EX_Mandee_Fee"
																				type="text" id="ISF_EX_Mandee_Fee"
																				class="Other_Incentives form-control"
																				readonly="true" name="ISF_EX_Mandee_Fee"
																				maxlength="12"></form:input></td>
																		<td><a
																			href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
																			target="_blank">As per para 3.5.6</a> of The Rules,
																			There is provision for exemption from Mandi Fee for
																			all new food-processing undertakings on purchase of
																			raw material for 5 years.</td>
																		<!-- <td><textarea class="form-control" disabled>No Justification</textarea></td> -->
																		<td><textarea class="form-control" readonly="true"></textarea></td>
																	</tr>
																	<tr>
																		<td colspan="2"></td>
																		<td><input id="ISF_total" type="text"
																			value="Total: ${total}"
																			class="Other_Incentives form-control" disabled></input></td>
																		<td colspan="4"></td>
																	</tr>
																</tbody>
																<%-- 	</form:form> --%>
															</table>
														</div>
													</div>
												</div>

												<c:if
													test="${not empty incentiveDeatilsData.othAddRequest1}">
													<div class="row">
														<div class="col-sm-12">
															<div class="form-group" >
																<label>Additional Request</label>
																<form:input path="othAddRequest1" type="text"
																	class="form-control"  placeholder="" maxlength="250" readonly="true"
																	name="othAddRequest1" ></form:input>
															</div>
														</div>
													</div>
												</c:if>
											</form:form>

											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-5">
													<a
														href="./JMDPICUPEvaluateApplication?applicantId=<%=session.getAttribute("appId")%>"
														class="common-default-btn mt-3">Back</a>
												</div>
												<!-- <div class="col-sm-7 text-right">
													<a href="javacript:void(0);" class="common-btn mt-3"><i
														class="fa fa-edit"></i> Edit</a> <a href="#"
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