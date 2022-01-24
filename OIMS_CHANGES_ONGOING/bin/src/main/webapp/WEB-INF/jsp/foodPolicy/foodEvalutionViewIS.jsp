<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
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
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="js/datepicker.js"></script>
<script src="js/custom.js"></script>
<script type="text/javascript">
	function COPExistingTotal() {
		var sum = 0;
		$(".COPExisting").each(function() {
			sum += +$(this).val();
		});
		$(".COPExistingTotal").val(sum);
	}

	$(document).ready(COPExistingTotal);
	$(document).on("keyup", COPExistingTotal);
	$(document).on("change", COPExistingTotal);

	function COPProposedTotal() {
		var sum = 0;
		$(".COPProposed").each(function() {
			sum += +$(this).val();
		});
		$(".COPProposedTotal").val(sum);
	}

	$(document).ready(COPProposedTotal);
	$(document).on("keyup", COPProposedTotal);
	$(document).on("change", COPProposedTotal);

	function COPAppraisedTotal() {
		var sum = 0;
		$(".COPAppraised").each(function() {
			sum += +$(this).val();
		});
		$(".COPAppraisedTotal").val(sum);
	}

	$(document).ready(COPAppraisedTotal);
	$(document).on("keyup", COPAppraisedTotal);
	$(document).on("change", COPAppraisedTotal);

	function MOFExistingTotal() {
		var sum = 0;
		$(".MOFExisting").each(function() {
			sum += +$(this).val();
		});
		$(".MOFExistingTotal").val(sum);
	}

	$(document).ready(MOFExistingTotal);
	$(document).on("keyup", MOFExistingTotal);
	$(document).on("change", MOFExistingTotal);

	function MOFProposedTotal() {
		var sum = 0;
		$(".MOFProposed").each(function() {
			sum += +$(this).val();
		});
		$(".MOFProposedTotal").val(sum);
	}

	$(document).ready(MOFProposedTotal);
	$(document).on("keyup", MOFProposedTotal);
	$(document).on("change", MOFProposedTotal);

	function MOFAppraisedTotal() {
		var sum = 0;
		$(".MOFAppraised").each(function() {
			sum += +$(this).val();
		});
		$(".MOFAppraisedTotal").val(sum);
	}

	$(document).ready(MOFAppraisedTotal);
	$(document).on("keyup", MOFAppraisedTotal);
	$(document).on("change", MOFAppraisedTotal);

	function eligibleCostInLacsofPlant() {
		var sum = 0;
		var abc = $(".eligibleCostInPlant").val();
		$(".eligibleCostInPlant").each(function() {
			sum += +$(this).val();
		});
		$(".eligibleCostInPlantTotal").val(sum);
	}

	$(document).ready(eligibleCostInLacsofPlant);
	$(document).on("keyup", eligibleCostInLacsofPlant);
	$(document).on("change", eligibleCostInLacsofPlant);

	function eligibleCostInLacsofCivilWork() {
		var sum = 0;
		$(".eligibleCostInCivilWork").each(function() {
			sum += +$(this).val();
		});
		$(".eligibleCostInCivilWorkTotal").val(sum);
	}

	$(document).ready(eligibleCostInLacsofCivilWork);
	$(document).on("keyup", eligibleCostInLacsofCivilWork);
	$(document).on("change", eligibleCostInLacsofCivilWork);

	function eligibcost() {
		var grantAmtPlant = document
				.getElementById('grantAmtEligiblleCostOfPlantMachId').value;
		var grantAmtCivil = document
				.getElementById('grantAmtEligibleCostOfTechnicalCivilWorkId').value;
		var grantAmtEligiblePMId = document
				.getElementById('grantAmtEligiblePMId').value;

		var totalGrantAmtPlantAndCivil = parseInt(grantAmtPlant)
				+ parseInt(grantAmtCivil);

		var grantAmtEligiblePMId = totalGrantAmtPlantAndCivil * 0.07;

		if (grantAmtPlant > 0 && !grantAmtPlant == '') {
			$('#grantAmtEligibleProjectCostId').val(totalGrantAmtPlantAndCivil);
			$('#grantAmtEligiblePMId').val(grantAmtEligiblePMId);

			if (grantAmtEligiblePMId >= 5000000) {
				document.getElementById("grantAmtEligiblePMId").value = grantAmtEligiblePMId;
				document.getElementById("grantAmtMaxEligibleGrantId").value = "5000000";
				$('#grantAmtEligibleProjectCostId').val(
						totalGrantAmtPlantAndCivil);
			} else {
				document.getElementById("grantAmtEligiblePMId").value = grantAmtEligiblePMId;
				document.getElementById("grantAmtMaxEligibleGrantId").value = grantAmtEligiblePMId;
				$('#grantAmtEligibleProjectCostId').val(
						totalGrantAmtPlantAndCivil);
			}

		} else {
			if ("${foodViewEvaluateIS.grantAmtEligiblleCostOfPlantMach}" != null
					|| "${foodViewEvaluateIS.grantAmtEligiblleCostOfPlantMach}" != ''
					&& "${foodViewEvaluateIS.grantAmtEligibleCostOfTechnicalCivilWork}" != null
					|| "${foodViewEvaluateIS.grantAmtEligibleCostOfTechnicalCivilWork}" != ''
					&& "${foodViewEvaluateIS.grantAmtEligibleProjectCost}" != null
					|| "${foodViewEvaluateIS.grantAmtEligibleProjectCost}" != '') {
				$('#grantAmtEligiblleCostOfPlantMachId')
						.val('${foodViewEvaluateIS.grantAmtEligiblleCostOfPlantMach}');
				$('#grantAmtEligibleCostOfTechnicalCivilWorkId').val('${foodViewEvaluateIS.grantAmtEligibleCostOfTechnicalCivilWork}');
				$('#grantAmtEligibleProjectCostId').val('${foodViewEvaluateIS.grantAmtEligibleProjectCost}');

			} else {

				$('#totaleEligibleCostInLacsId').val('');
				$('#ttlEligibleTechCivilWorkEligibleCostId').val('');
				$('#grantAmtEligiblleCostOfPlantMachId').val('');
				$('#grantAmtEligibleCostOfTechnicalCivilWorkId').val('');
				$('#grantAmtEligibleProjectCostId').val('');
			}

		}

	}

	$(document).ready(eligibcost);
	$(document).on("change", eligibcost);
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
				 <h4 class="card-title mb-4 mt-4 text-center">
Interest Subsidy for settingup new units/Technology Up gradation/Modernisation of Food Processing Industries.</h4>
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-5">
								<a
									href="./foodProcessingFPI?unit_Id=${businessEntityDetailsfood.unit_id}"
									class="common-default-btn mt-3">Back</a>

							</div>

							<div class="col-sm-7 text-right">
								<a href="#EvaluationAuditTrail" class="text-info mr-3"
									data-toggle="modal">Evaluation Audit Trail</a> <a
									href="javacript:void(0);" class="common-btn mt-3"><i
									class="fa fa-edit"></i> Edit</a>
							</div>
						</div>
						<form:form modelAttribute="foodViewEvaluateIS" method="post"
							action="SavefoodEvaluateIS" class="mt-4">

							<div class="row mt-5">
								<div class="col-sm-12">
									<div class="table-responsive">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>Name of Organization</th>
													<th>Registered Address</th>
													<th>Project Name</th>
													<th>Proposed Site Address</th>
													<th>Capacity</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td><input id="nameOfOrganizationId"
														name="nameOfOrganization"
														value="${businessEntityDetailsfood.nameofcompany}"
														class="form-control" readonly="true"></input></td>

													<td><input id="registeredAddressId"
														name="registeredAddress"
														value="${businessEntityDetailsfood.addressofcompany}"
														class="form-control" readonly="true"></input></td>

													<td><input id="projectNameid" name="projectName"
														value="${projectandproposedemploymentdetails.nameoftheproject}"
														class="form-control" readonly="true"></input></td>

													<td><input id="proposedSiteAddressid"
														name="proposedSiteAddress"
														value="${projectandproposedemploymentdetails.locationareaoftheproject}"
														class="form-control" readonly="true"></input></td>

													<td><input id="capacityid" name="capacity"
														value="${capacityoftheplantorunit.totalafterexpansioncapacity} ${capacityoftheplantorunit.unittypecapacity}"
														class="form-control" readonly="true"></input></td>
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
											<h4 class="card-title mb-4 mt-4 ">Project Profile</h4>
										</div>
									</div>
									<hr class="mt-2 mb-5">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-bordered">
													<thead>
														<tr>
															<th>Particulars</th>
															<th>Details</th>
															<th>Observations By Nodal Officer</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td style="width: 33%;"><label class="table-heading">Application
																	ID</label></td>
															<td style="width: 33%;"><form:input
																	path="projectProfileAppId" id="ProjectProfileAppId"
																	name="projectProfileAppId"
																	value="${businessEntityDetailsfood.unit_id}"
																	type="text" readonly="true" class="form-control" /></td>
															<td><form:textarea path="projectProfileAppIdObserv"
																	id="projectProfileAppIdObservId"
																	name="projectProfileAppIdObserv"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>

														</tr>
														<tr>
															<td><label class="table-heading">Proposal
																	submission date</label></td>

															<td><form:input path="proposalSubmissionDate"
																	id="proposalSubmissionDateId"
																	name="proposalSubmissionDate" value="25-04-2021"
																	type="text" readonly="true" class="form-control" /></td>
															<td><form:textarea
																	path="proposalSubmissionDateObserv"
																	id="proposalSubmissionDateObservId"
																	name="proposalSubmissionDateObserv"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>

														</tr>
														<tr>
															<td style="width: 33%;"><label class="table-heading">Name
																	of Promoter</label></td>
															<td style="width: 33%;"><form:input
																	path="nameOfPromoter" id="nameOfPromoterId"
																	name="nameOfPromoter"
																	value="${businessEntityDetailsfood.nameofpromoter}"
																	type="text" readonly="true" class="form-control" /></td>
															<td><form:textarea path="nameOfPromoterObserv"
																	id="nameOfPromoterObservId" name="nameOfPromoterObserv"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>

														</tr>
														<tr>
															<td style="width: 33%;"><label class="table-heading">Type
																	of Organization</label></td>
															<td style="width: 33%;"><form:input
																	path="typeOfOrganization" id="typeOfOrganizationId"
																	name="typeOfOrganization"
																	value="${businessEntityDetailsfood.typeoforganization}"
																	type="text" readonly="true" class="form-control" /></td>
															<td><form:textarea path="typeOfOrganizationObserv"
																	id="typeOfOrganizationObservId"
																	name="typeOfOrganizationObserv"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>

														</tr>
														<tr>
															<td style="width: 33%;"><label class="table-heading">Date
																	of incorporation</label></td>
															<td style="width: 33%;"><form:input
																	path="dateOfIncorporation" id="dateOfIncorporationId"
																	name="dateOfIncorporation"
																	value="${businessEntityDetailsfood.dateofincorporation}"
																	type="text" readonly="true" class="form-control" /></td>
															<td><form:textarea path="dateOfIncorporationObserv"
																	id="dateOfIncorporationObservId"
																	name="dateOfIncorporationObserv"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>

														</tr>
														<tr>
															<td style="width: 33%;"><label class="table-heading">Availability
																	of land and Building</label></td>
															<td style="width: 33%;"><form:input
																	path="availOfLandAndBuilding"
																	id="availOfLandAndBuildingId"
																	name="availOfLandAndBuilding"
																	value="${projectandproposedemploymentdetails.district} ${projectandproposedemploymentdetails.division} ${projectandproposedemploymentdetails.locationareaoftheproject}"
																	type="text" readonly="true" class="form-control" /></td>
															<td><form:textarea
																	path="availOfLandAndBuildingObserv"
																	id="availOfLandAndBuildingObservId"
																	name="availOfLandAndBuildingObserv"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>

														</tr>
														<tr>
															<td style="width: 33%;"><label class="table-heading">PAN
																	No.</label></td>
															<td style="width: 33%;"><form:input path="panNo"
																	id="panNoId" name="panNo"
																	value="${businessEntityDetailsfood.pancardoffirmproprietor}"
																	type="text" readonly="true" class="form-control" /></td>
															<td><form:textarea path="panNoObserv" id="panNoId"
																	name="panNoObserv" class="form-control border-info"
																	rows="2"></form:textarea></td>

														</tr>
														<tr>
															<td style="width: 33%;"><label class="table-heading">GST
																	No.</label></td>
															<td style="width: 33%;"><form:input path="gstNo"
																	id="gstNoId" name="gstNo"
																	value="${businessEntityDetailsfood.gstin}" type="text"
																	readonly="true" class="form-control" /></td>
															<td><form:textarea path="gstNoObserv"
																	id="gstNoObservId" name="gstNoObserv"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>

														</tr>
														<tr>
															<td style="width: 33%;"><label class="table-heading">SSI/IEM/Udyam/Udyog Aadhar No.
																	</label></td>
															<td style="width: 33%;"><form:input
																	path="udyogAadharNo" id="udyogAadharNoId"
																	name="udyogAadharNo" value="" type="text"
																	readonly="false" maxlength="12" class="form-control is-numeric" /></td>
															<td><form:textarea path="udyogAadharNoObservation"
																	id="udyogAadharNoObservationId"
																	name="udyogAadharNoObservation"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>


														</tr>
														<tr>
															<td style="width: 33%;"><label class="table-heading">Location
																	the firm/unit</label></td>
															<td style="width: 33%;"><form:input
																	path="locationOfFirm" id="locationOfFirmId"
																	name="locationOfFirm"
																	value="${projectandproposedemploymentdetails.locationareaoftheproject}"
																	type="text" readonly="true" class="form-control" /></td>
															<td><form:textarea path="locationOfFirmObservation"
																	id="locationOfFirmObservationId"
																	name="locationOfFirmObservation"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>

														</tr>
														<tr>
															<td style="width: 33%;"><label class="table-heading">Sector</label></td>
															<td style="width: 33%;"><form:input path="sector"
																	id="sectorId" name="sector"
																	value="${projectandproposedemploymentdetails.sector}"
																	type="text" readonly="true" class="form-control" /></td>
															<td><form:textarea path="sectorObservation"
																	id="sectorObservationId" name="sectorObservation"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td style="width: 33%;"><label class="table-heading">Sub-Sector</label></td>
															<td style="width: 33%;"><form:input path="subSector"
																	id="subSectorId" name="subSector"
																	value="${projectandproposedemploymentdetails.selectsubsector}"
																	type="text" readonly="true" class="form-control" /></td>
															<td><form:textarea path="subSectorObservation"
																	id="subSectorObservationId" name="subSectorObservation"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td colspan="3"><label class="table-heading">Proposed
																	Products by Products</label></td>
														</tr>
														<tr>
															<td colspan="3">
																<table style="width: 100%">
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
																					<td><input type="text" class="form-control"
																						id="" value="${list.products}" disabled="" name=""></td>
																					<td><input type="text" class="form-control"
																						id="" value="${list.byproducts}" disabled=""
																						name=""></td>
																				</tr>
																			</c:forEach>
																		</tbody>
																	</table>
																	<table
																		class="table table-stripped table-bordered directors-table">
																		<tbody>
																			<c:forEach var="bdList"
																				items="${bankTermLoanDetails}" varStatus="counter">
																				<tr>
																					<td style="width: 33%;"><label
																						class="table-heading">Proposed
																							Capacity(For New Unit)</label></td>
																					<td style="width: 33%;"><form:input
																							path="proposedCapacityBuyProducts"
																							id="proposedCapacityBuyProductsId"
																							name="proposedCapacityBuyProducts" value=""
																							type="text" readonly="false" maxlength="10" class="form-control is-numeric" />
																					</td>
																					<td><form:textarea
																							path="proposedCapacityBuyProductsObserv"
																							id="proposedCapacityBuyProductsObservId"
																							name="proposedCapacityBuyProductsObserv"
																							class="form-control border-info" rows="2"></form:textarea>
																					</td>
																				</tr>
																				<tr>
																					<td style="width: 33%;"><label
																						class="table-heading">Existing Capacity
																							(in case of Expansion/Modernization)</label></td>
																					<td style="width: 33%;"><form:input
																							path="existingCapacityBuyProducts"
																							id="existingCapacityBuyProductsId"
																							name="existingCapacityBuyProducts"
																							value="${capacityoftheplantorunit.totalafterexpansioncapacity}"
																							type="text" readonly="true" class="form-control" />
																					</td>
																					<td><form:textarea
																							path="existingCapacityBuyProductsObserv"
																							id="existingCapacityBuyProductsObservId"
																							name="existingCapacityBuyProductsObserv"
																							class="form-control border-info" rows="2"></form:textarea>
																					</td>
																				</tr>
																				<tr>
																					<td style="width: 33%;"><label
																						class="table-heading">Capacity Utilization
																							(%)</label></td>
																					<td style="width: 33%;"><form:input
																							path="capacityUtilizationBuyProducts"
																							id="capacityUtilizationBuyProductsId"
																							name="capacityUtilizationBuyProducts" value=""
																							type="text" readonly="false" maxlength="3" class="form-control is-numeric" />
																					</td>
																					<td><form:textarea
																							path="capacityUtilizationBuyProductsObserv"
																							id="capacityUtilizationBuyProductsObservId"
																							name="capacityUtilizationBuyProductsObserv"
																							class="form-control border-info" rows="2"></form:textarea>
																					</td>
																				</tr>
																				<tr>
																					<td style="width: 33%;"><label
																						class="table-heading">Name of Appraising
																							Bank</label></td>
																					<td style="width: 33%;"><form:input
																							path="nameOfAppraisingBankBuyProducts"
																							id="nameOfAppraisingBankBuyProductsId"
																							name="nameOfAppraisingBankBuyProducts"
																							value="${bdList.detailsnameofthebankfi}"
																							type="text" readonly="true" class="form-control" />
																					</td>
																					<td><form:textarea
																							path="nameOfAppraisingBankBuyProductsObserv"
																							id="nameOfAppraisingBankBuyProductsObservId"
																							name="nameOfAppraisingBankBuyProductsObserv"
																							class="form-control border-info" rows="2"></form:textarea>
																					</td>
																				</tr>
																				<tr>
																					<td style="width: 33%;"><label
																						class="table-heading">Amount of Term loan
																							Sanctioned</label></td>
																					<td style="width: 33%;"><form:input
																							path="amtOfTermloanSanctionedBuyProducts"
																							id="amtOfTermloanSanctionedBuyProductsId"
																							name="amtOfTermloanSanctionedBuyProducts"
																							value="${bdList.detailstotalamountoftermloansanctioned}"
																							type="text" readonly="true" class="form-control" />
																					</td>
																					<td><form:textarea
																							path="amtOfTermloanSanctionedBuyProductsObservs"
																							id="amtOfTermloanSanctionedBuyProductsObservId"
																							name="amtOfTermloanSanctionedBuyProductsObservs"
																							class="form-control border-info" rows="2"></form:textarea>
																					</td>
																				</tr>
																				<tr>
																					<td style="width: 33%;"><label
																						class="table-heading">Date of term loan
																							Sanctioned</label></td>
																					<td style="width: 33%;"><form:input
																							path="dateOfTermLoanSanctionedBuyProducts"
																							id="dateOfTermLoanSanctionedBuyProductsId"
																							name="dateOfTermLoanSanctionedBuyProducts"
																							value="${bdList.detailsdateofsanctionoftermloan}"
																							type="date" readonly="true" class="form-control" />
																					</td>
																					<td><form:textarea
																							path="dateOfTermLoanSanctionedBuyProductsObserv"
																							id="dateOfTermLoanSanctionedBuyProductsObservId"
																							name="dateOfTermLoanSanctionedBuyProductsObserv"
																							class="form-control border-info" rows="2"></form:textarea>
																					</td>
																				</tr>
																			</c:forEach>
																		</tbody>
																	</table>
																</table>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-12 mt-4">
									<div class="form-group">
										<h4 class="card-title mb-4 mt-4 ">Project Summary</h4>
										<form:textarea path="projectSummaryObserv"
											id="projectSummaryObservId" name="projectSummaryObserv"
											class="form-control border-info" rows="2"></form:textarea>
									</div>
								</div>

							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="mt-4">
										<div class="without-wizard-steps">
											<h4 class="card-title mb-4 mt-4">Cost of Project (in
												Lacs)</h4>
										</div>
									</div>
									<hr class="mt-2 mb-5">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-stripped table-bordered">
													<thead>
														<tr>
															<th>Particulars</th>
															<th>Existing Cost As per books of Account</th>
															<th>Proposed Cost as per Project Report</th>
															<th>Appraised Cost As Per Bank Appraisal</th>
															<th>Nodal Officer's Remark</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="pidList"
															items="${projectInvestmentDetails}" varStatus="counter">
															<tr>
																<td><label class="table-heading">Land</label></td>
																<td><form:input path="landExistCost"
																		id="landExistCostId" name="landExistCost"
																		value="${pidList.existinginvestmentlandcost}"
																		type="text" readonly="true"
																		class="form-control COPExisting" /></td>
																<td><form:input path="landProposedCost"
																		id="landProposedCostId" name="landProposedCost"
																		value="${pidList.proposedinvestmentlandcost}"
																		type="text" readonly="true"
																		class="form-control COPProposed" /></td>
																<td><form:input path="landAppraisedCost"
																		id="landAppraisedCostId" name="landAppraisedCost"
																		value="${pidList.appraisedcostbybanklandcost}"
																		type="text" readonly="true"
																		class="form-control COPAppraised" /></td>
																<td><form:textarea path="landRemarks"
																		id="landRemarksId" name="landRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Factory
																		Building</label></td>
																<td><form:input path="factoryBuildExistCost"
																		id="factoryBuildExistCostId"
																		name="factoryBuildExistCost"
																		value="${pidList.existinginvestmentcivilwork}"
																		type="text" readonly="true"
																		class="form-control COPExisting" /></td>
																<td><form:input path="factoryBuildProposedCost"
																		id="factoryBuildProposedCostId"
																		name="factoryBuildProposedCost"
																		value="${pidList.proposedinvestmentcivilwork}"
																		type="text" readonly="true"
																		class="form-control COPProposed" /></td>
																<td><form:input path="factoryBuildAppraisedCost"
																		id="factoryBuildAppraisedCostId"
																		name="factoryBuildAppraisedCost"
																		value="${pidList.appraisedcostbybankcivilwork}"
																		type="text" readonly="true"
																		class="form-control COPAppraised" /></td>
																<td><form:textarea path="factoryBuildRemarks"
																		id="factoryBuildRemarksId" name="factoryBuildRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Plant &
																		Machinery</label></td>
																<td><form:input path="plantMachExistCost"
																		id="plantMachExistCostId" name="plantMachExistCost"
																		value="${pidList.existinginvestmentplantmachinery}"
																		type="text" readonly="true"
																		class="form-control COPExisting" /></td>
																<td><form:input path="plantMachProposedCost"
																		id="plantMachProposedCostId"
																		name="plantMachProposedCost"
																		value="${pidList.proposedinvestmentplantmachinery}"
																		type="text" readonly="true"
																		class="form-control COPProposed" /></td>
																<td><form:input path="plantMachAppraisedCost"
																		id="plantMachAppraisedCostId"
																		name="plantMachAppraisedCost"
																		value="${pidList.appraisedcostbybankplantmachinery}"
																		type="text" readonly="true"
																		class="form-control COPAppraised" /></td>
																<td><form:textarea path="plantMachRemarks"
																		id="plantMachRemarksId" name="plantMachRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Misc.
																		Fixed Assets </label></td>
																<td><form:input path="miscFixedAssetsExistCost"
																		id="miscFixedAssetsExistCostId"
																		name="miscFixedAssetsExistCost" value="" type="text"
																		readonly="false" maxlength="10" class="form-control is-numeric COPExisting" /></td>

																<td><form:input path="miscFixedAssetsProposedCost"
																		id="miscFixedAssetsProposedCostId"
																		name="miscFixedAssetsProposedCost" value=""
																		type="text" readonly="false" maxlength="10" 
																		class="form-control is-numeric COPProposed" /></td>
																<td><form:input path="miscFixedAssetsAppraisedCost"
																		id="miscFixedAssetsAppraisedCostId" maxlength="10"
																		name="miscFixedAssetsAppraisedCost" value=""
																		type="text" readonly="false"
																		class="form-control is-numeric COPAppraised" /></td>
																<td><form:textarea path="miscFixedAssetsRemarks"
																		id="miscFixedAssetsRemarksId"
																		name="miscFixedAssetsRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Contingences
																</label></td>
																<td><form:input path="contingencesExistCost"
																		id="contingencesExistCostId"
																		name="contingencesExistCost" value="" type="text" maxlength="10"
																		readonly="false" class="form-control is-numeric COPExisting" /></td>
																<td><form:input path="contingencesProposedCost"
																		id="contingencesProposedCostId"
																		name="contingencesProposedCost" value="" type="text" maxlength="10"
																		readonly="false" class="form-control is-numeric  COPProposed" /></td>
																<td><form:input path="contingencesAppraisedCost"
																		id="contingencesAppraisedCostId" maxlength="10"
																		name="contingencesAppraisedCost" value="" type="text"
																		readonly="false" class="form-control is-numeric COPAppraised" /></td>
																<td><form:textarea path="contingencesRemarks"
																		id="contingencesRemarksId" name="contingencesRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Pre
																		Operative expenses </label></td>
																<td><form:input
																		path="preOperativeExpensesExistCost"
																		id="preOperativeExpensesExistCostId"
																		name="preOperativeExpensesExistCost"
																		value="${pidList.existinginvestmentpreoperativeexpenses}"
																		type="text" readonly="true"
																		class="form-control COPExisting" /></td>
																<td><form:input
																		path="preOperativeExpensesProposedCost"
																		id="preOperativeExpensesProposedCostId"
																		name="preOperativeExpensesProposedCost"
																		value="${pidList.proposedinvestmentpreoperativeexpenses}"
																		type="text" readonly="true"
																		class="form-control COPProposed" /></td>
																<td><form:input
																		path="preOperativeExpensesAppraisedCost"
																		id="preOperativeExpensesAppraisedCost"
																		name="preOperativeExpensesAppraisedCost"
																		value="${pidList.appraisedcostbybankpreoperativeexpenses}"
																		type="text" readonly="true"
																		class="form-control COPAppraised" /></td>
																<td><form:textarea
																		path="preOperativeExpensesRemarks"
																		id="preOperativeExpensesRemarksId"
																		name="preOperativeExpensesRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Interest
																		During construction </label></td>
																<td><form:input path="intDuringConstructExistCost"
																		id="intDuringConstructExistCostId" maxlength="10" 
																		name="intDuringConstructExistCost" value=""
																		type="text" readonly="false"
																		class="form-control is-numeric COPExisting" /></td>
																<td><form:input
																		path="intDuringConstructProposedCost"
																		id="intDuringConstructProposedCostId" maxlength="10" 
																		name="intDuringConstructProposedCost" value=""
																		type="text" readonly="false"
																		class="form-control is-numeric COPProposed" /></td>
																<td><form:input
																		path="intDuringConstructAppraisedCost"
																		id="intDuringConstructAppraisedCostId" maxlength="10" 
																		name="intDuringConstructAppraisedCost" value=""
																		type="text" readonly="false"
																		class="form-control is-numeric COPAppraised" /></td>
																<td><form:textarea path="intDuringConstructRemarks"
																		id="intDuringConstructRemarksId"
																		name="intDuringConstructRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Margin
																		Money for Working Capital</label></td>
																<td><form:input path="marginMoneyExistCost"
																		id="marginMoneyExistCost" name="marginMoneyExistCost"
																		value="" type="text" readonly="false" maxlength="10" 
																		class="form-control is-numeric COPExisting" /></td>
																<td><form:input path="marginMoneyProposedCost"
																		id="marginMoneyProposedCost"
																		name="marginMoneyProposedCost" value="" type="text" maxlength="10" 
																		readonly="false" class="form-control is-numeric  COPProposed" /></td>
																<td><form:input path="marginMoneyAppraisedCost"
																		id="marginMoneyAppraisedCost" maxlength="10" 
																		name="marginMoneyAppraisedCost" value="" type="text"
																		readonly="false" class="form-control is-numeric  COPAppraised" /></td>
																<td><form:textarea path="marginMoneyRemarks"
																		id="marginMoneyRemarksId" name="marginMoneyRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
														</c:forEach>
													</tbody>
													<tfoot>
														<tr>
															<td><label class="table-heading">Total</label></td>
															<td><form:input path="totalExistCost"
																	id="totalExistCostId" name="totalExistCost" value=""
																	type="text" readonly="true"
																	class="form-control COPExistingTotal" /></td>
															<td><form:input path="totalProposedCost"
																	id="totalProposedCostId" name="totalProposedCost"
																	value="" type="text" readonly="true"
																	class="form-control COPProposedTotal" /></td>
															<td><form:input path="totalAppraisedCost"
																	id="totalAppraisedCostId" name="totalAppraisedCost"
																	value="" type="text" readonly="true"
																	class="form-control COPAppraisedTotal" /></td>
															<td><form:textarea path="totalRemarks"
																	id="totalRemarksId" name="totalRemarks"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
													</tfoot>
												</table>
											</div>
										</div>
									</div>
									<div class="mt-4">
										<div class="without-wizard-steps">
											<h4 class="card-title mb-4 mt-4">Means of Finance (in
												lacs)</h4>
										</div>
									</div>
									<hr class="mt-2 mb-5">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-stripped table-bordered">
													<thead>
														<tr>
															<th>Particulars</th>
															<th>Existing</th>
															<th>Proposed</th>
															<th>Appraised</th>
															<th>Nodal Officer's Remark</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="mofList" items="${meansofFinancing}"
															varStatus="counter">
															<tr>
																<td><label class="table-heading">Equity
																		(Promoter share)</label></td>

																<td><form:input path="equityExistCost"
																		id="equityExistCostId" name="equityExistCost"
																		value="${mofList.existinginvestmentpromotersequity}"
																		type="text" readonly="true"
																		class="form-control MOFExisting" /></td>
																<td><form:input path="equityProposedCost"
																		id="equityProposedCosIdt" name="equityProposedCost"
																		value="${mofList.proposedinvestmentpromotersequity}"
																		type="text" readonly="true"
																		class="form-control MOFProposed" /></td>
																<td><form:input path="equityAppraisedCost"
																		id="equityAppraisedCostId" name="equityAppraisedCost"
																		value="${mofList.appraisedcostbybankpromotersequity}"
																		type="text" readonly="true"
																		class="form-control MOFAppraised" /></td>
																<td><form:textarea path="equityRemarks"
																		id="equityRemarksId" name="equityRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Grant </label></td>

																<td><form:input path="grantExistCost"
																		id="grantExistCostId" name="grantExistCost" value="" maxlength="10"
																		type="text" class="form-control is-numeric MOFExisting" /></td>
																<td><form:input path="grantProposedCost"
																		id="grantProposedCostId" name="grantProposedCost" maxlength="10"
																		value="" type="text" class="form-control is-numeric MOFProposed" /></td>
																<td><form:input path="grantAppraisedCost"
																		id="grantAppraisedCostId" name="grantAppraisedCost" maxlength="10"
																		value="" type="text" class="form-control is-numeric  MOFAppraised" /></td>
																<td><form:textarea path="grantRemarks"
																		id="grantRemarksId" name="grantRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Term loan</label></td>

																<td><form:input path="termLoanExistCost"
																		id="termLoanExistCostId" name="termLoanExistCost"
																		value="${mofList.existinginvestmenttermloan}"
																		type="text" readonly="true"
																		class="form-control MOFExisting" /></td>
																<td><form:input path="termLoanProposedCost"
																		id="termLoanProposedCostId"
																		name="termLoanProposedCost"
																		value="${mofList.proposedinvestmenttermloan}"
																		type="text" readonly="true"
																		class="form-control MOFProposed" /></td>
																<td><form:input path="termLoanAppraisedCost"
																		id="termLoanAppraisedCostId"
																		name="termLoanAppraisedCost"
																		value="${mofList.appraisedcostbybanktermloan}"
																		type="text" readonly="true"
																		class="form-control MOFAppraised" /></td>
																<td><form:textarea path="termLoanRemarks"
																		id="termLoanRemarksId" name="termLoanRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Working
																		Capital</label></td>

																<td><form:input path="workingCapitalExistCost"
																		id="workingCapitalExistCostId" maxlength="10"
																		name="workingCapitalExistCost" value="${mofList.existinginvestmentmarginmoneyforworkingcapital}" type="text"
																		class="form-control is-numeric MOFExisting" /></td>
																<td><form:input path="workingCapitalProposedCost"
																		id="workingCapitalProposedCostId" maxlength="10"
																		name="workingCapitalProposedCost" value="${mofList.proposedinvestmentmarginmoneyforworkingcapital}" type="text"
																		class="form-control is-numeric MOFProposed" /></td>
																<td><form:input path="workingCapitalAppraisedCost"
																		id="workingCapitalAppraisedCost" maxlength="10"
																		name="workingCapitalAppraisedCost" value="${mofList.appraisedcostbybankmarginmoneyforworkingcapital}"
																		type="text" class="form-control is-numeric MOFAppraised" /></td>
																<td><form:textarea path="workingCapitalRemarks"
																		id="workingCapitalRemarksId"
																		name="workingCapitalRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Others</label></td>

																<td><form:input path="mofOtherExitCost"
																		id="mofOtherExitCostId" maxlength="10"
																		name="mofOtherExitCost" value="${mofList.existinginvestmentothers}" type="text"
																		class="form-control is-numeric MOFExisting" /></td>
																		
																<td><form:input path="mofOtherProposedCost"
																		id="mofOtherProposedCostId" maxlength="10"
																		name="mofOtherProposedCost" value="${mofList.proposedinvestmentothers}" type="text"
																		class="form-control is-numeric MOFProposed" /></td>
																		
																<td><form:input path="mofOtherAppraisedCost"
																		id="mofOtherAppraisedCostId" maxlength="10"
																		name="mofOtherAppraisedCost" value="${mofList.appraisedcostbybankothers}"
																		type="text" class="form-control is-numeric MOFAppraised" /></td>
																		
																<td><form:textarea path="mofOtherRemarks"
																		id="mofOtherRemarksId"
																		name="mofOtherRemarks"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
														</c:forEach>
													</tbody>
													<tfoot>
														<tr>
															<td><label class="table-heading">Total</label></td>

															<td><form:input path="totalMofExistCost"
																	id="totalMofExistCostId" name="totalMofExistCost"
																	type="text" readonly="true"
																	class="form-control MOFExistingTotal" /></td>
															<td><form:input path="totalMofProposedCost"
																	id="totalMofProposedCostId" name="totalMofProposedCost"
																	value="" type="text" readonly="true"
																	class="form-control MOFProposedTotal" /></td>
															<td><form:input path="totalMofAppraisedCost"
																	id="totalMofAppraisedCostId"
																	name="totalMofAppraisedCost" value="" type="text"
																	readonly="true" class="form-control MOFAppraisedTotal" />
															</td>
															<td><form:textarea path="totalMofRemarks"
																	id="totalMofRemarksId" name="totalMofRemarks"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
													</tfoot>
												</table>
											</div>
										</div>
									</div>
									<div class="mt-4">
										<div class="without-wizard-steps">
											<h4 class="card-title mb-4 mt-4">Implementation
												Schedule:</h4>
										</div>
									</div>
									<hr class="mt-2 mb-5">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-stripped table-bordered">
													<thead>
														<tr>
															<th>Item of Work</th>
															<th>Date</th>
															<th>Nodal Officer's Remark</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="isList" items="${implementationSchedule}"
															varStatus="counter">
															<tr>
																<td><label class="table-heading">Acquisition
																		land</label></td>
																<td><form:input path="acquisitionLandDate"
																		id="acquisitionLandDateId" name="acquisitionLandDate"
																		value="${isList.detailsacquisitiondateofland}"
																		type="date" readonly="true" class="form-control" /></td>
																<td><form:textarea path="acquisitionLandRemark"
																		id="acquisitionLandRemarkId"
																		name="acquisitionLandRemark"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Start of
																		construction of building </label></td>

																<td><form:input path="startConstructionOfBuildDate"
																		id="startConstructionOfBuildDateId"
																		name="startConstructionOfBuildDate"
																		value="${isList.detailsdateofbuildingconstructionfrom}"
																		type="date" readonly="true" class="form-control" /></td>
																<td><form:textarea
																		path="startOfConstructionOfBuildRemark"
																		id="startOfConstructionOfBuildRemarkId"
																		name="startOfConstructionOfBuildRemark"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Completion
																		of Building</label></td>

																<td><form:input path="completionOfBuildDate"
																		id="completionOfBuildDateId"
																		name="completionOfBuildDate"
																		value="${isList.detailsdateofbuildingconstructionto}"
																		type="date" readonly="true" class="form-control" /></td>
																<td><form:textarea path="completionOfBuildRemark"
																		id="completionOfBuildRemarkId"
																		name="completionOfBuildRemark"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Placing
																		order for plant & machinery</label></td>

																<td><form:input path="placingOrderPlantMachDate"
																		id="placingOrderPlantMachDateId"
																		name="placingOrderPlantMachDate"
																		value="${isList.detailsdateofplacingorderforplantmachineryfrom} TO ${isList.detailsdateofplacingorderforplantmachineryto}"
																		type="text" readonly="true" class="form-control" /></td>
																<td><form:textarea
																		path="placingOrderPlantMachRemark"
																		id="placingOrderPlantMachRemarkId"
																		name="placingOrderPlantMachRemark"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Installation/erection</label></td>

																<td><form:input path="installationDate"
																		id="installationDateId" name="installationDate"
																		value="" type="date" readonly="false"
																		class="form-control" /></td>
																<td><form:textarea path="installationRemark"
																		id="installationRemarkId" name="installationRemark"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Trial
																		production/running</label></td>

																<td><form:input path="trialProductionDate"
																		id="trialProductionDateId" name="trialProductionDate"
																		value="${isList.detailstrialproductiondatefrom} TO ${isList.detailstrialproductiondateto}"
																		type="text" readonly="true" class="form-control" /></td>
																<td><form:textarea path="trialProductionRemark"
																		id="trialProductionRemarkId"
																		name="trialProductionRemark"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
															<tr>
																<td><label class="table-heading">Commercial
																		production/running</label></td>

																<td><form:input path="commercialProductionDate"
																		id="commercialProductionDateId"
																		name="commercialProductionDate"
																		value="${isList.detailsdateofcommercialproductionrunning}"
																		type="date" readonly="true" class="form-control" /></td>
																<td><form:textarea
																		path="commercialProductionRemark"
																		id="commercialProductionRemarkId"
																		name="commercialProductionRemark"
																		class="form-control border-info" rows="2"></form:textarea>
																</td>
															</tr>
														</c:forEach>
													</tbody>

												</table>
											</div>
										</div>
									</div>
									<div class="mt-4">
										<div class="without-wizard-steps">
											<h4 class="card-title mb-4 mt-4">Status of Documents:</h4>
										</div>
									</div>
									<hr class="mt-2 mb-5">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-stripped table-bordered">
													<thead>
														<tr>
															<th>Item of Work</th>
															<th>Description</th>
															<th>Nodal Officer's Remark</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td><label class="table-heading">Application
																	on prescribed format <a href="#">View</a>
															</label></td>
															<td><form:input path="appOnPrescribedFormatDesc"
																	id="appOnPrescribedFormatDescId"
																	name="appOnPrescribedFormatDesc" value="" type="text"
																	maxlength="250" class="form-control" /></td>
															<td><form:textarea
																	path="appOnPrescribedFormatRemark"
																	id="appOnPrescribedFormatRemarkId"
																	name="appOnPrescribedFormatRemark"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td><label class="table-heading">Detailed
																	Project Report<a
																	href="./downloadFoodDocument?fileName=${foodDocument.detailedprojectreportasperannexurea16}">View</a>
															</label></td>

															<td><form:input path="detailedProjectReportDesc"
																	id="detailedProjectReportDescId"
																	name="detailedProjectReportDesc" value="" type="text"
																	maxlength="250" class="form-control" /></td>
															<td><form:textarea
																	path="detailedProjectReportRemark"
																	id="detailedProjectReportRemarkId"
																	name="detailedProjectReportRemark"
																	class="form-control border-info" rows="2"></form:textarea>

															</td>
														</tr>
														<tr>
															<td><label class="table-heading">Sanction
																	letter for Term loan<a
																	href="./downloadFoodDocument?fileName=${foodDocument.termloansanctionletter}">View</a>
															</label></td>

															<td><form:input path="sanctionLetterTermLoanDesc"
																	id="sanctionLetterTermLoanDescId"
																	name="sanctionLetterTermLoanDesc" value="" type="text"
																	maxlength="250" class="form-control" /></td>
															<td><form:textarea
																	path="sanctionLetterTermLoanRemark"
																	id="sanctionLetterTermLoanRemark"
																	name="sanctionLetterTermLoanRemark"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td><label class="table-heading">Bank
																	Appraisal Report<a
																	href="./downloadFoodDocument?fileName=${foodDocument.bankappraisalreport}">View</a>
															</label></td>

															<td><form:input path="bankAppraisalReportDesc"
																	id="bankAppraisalReportDescId"
																	name="bankAppraisalReportDesc" value="" type="text"
																	maxlength="250" class="form-control" /></td>
															<td><form:textarea path="bankAppraisalReportRemark"
																	id="bankAppraisalReportRemarkId"
																	name="bankAppraisalReportRemark"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td><label class="table-heading">C.E. Civil
																	Certification for(A/7)<a
																	href="./downloadFoodDocument?fileName=${foodDocument.annexurea7charteredengineercertificateforcivilwork}">View</a>
															</label></td>

															<td><form:input path="ceCivilCertificationForA7Desc"
																	id="ceCivilCertificationForA7DescId"
																	name="ceCivilCertificationForA7Desc" value=""
																	type="text" maxlength="250" class="form-control" /></td>
															<td><form:textarea
																	path="ceCivilCertificationForA7Remark"
																	id="ceCivilCertificationForA7RemarkId"
																	name="ceCivilCertificationForA7Remark"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td><label class="table-heading">C.E.
																	Mechanical Certification for(A/8)<a
																	href="./downloadFoodDocument?fileName=${foodDocument.annexurea8cecertificateforplantampmachinery}">View</a>
															</label></td>

															<td><form:input
																	path="ceMechanicalCertificationForA8Desc"
																	id="ceMechanicalCertificationForA8DescId"
																	name="ceMechanicalCertificationForA8Desc" value=""
																	type="text" maxlength="250" class="form-control" /></td>
															<td><form:textarea
																	path="ceMechanicalCertificationForA8Remark"
																	id="ceMechanicalCertificationForA8RemarkId"
																	name="ceMechanicalCertificationForA8Remark"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>

														<tr>
															<td><label class="table-heading">Building
																	Plan of Factory Approved By Competent Authority<a
																	href="./downloadFoodDocument?fileName=${foodDocument.buildingplanoffactoryapprovedbycompetentauthority}">View</a>
															</label></td>

															<td><form:input path="buildingPlanOfFactoryDesc"
																	id="buildingPlanOfFactoryDescId"
																	name="buildingPlanOfFactoryDesc" value="" type="text"
																	maxlength="250" class="form-control" /></td>
															<td><form:textarea
																	path="buildingPlanOfFactoryRemark"
																	id="buildingPlanOfFactoryRemark"
																	name="buildingPlanOfFactoryRemark"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td><label class="table-heading">Land
																	Document<a
																	href="./downloadFoodDocument?fileName=${foodDocument.registeredlanddeedrentagreement}">View</a>
															</label></td>

															<td><form:input path="landDocumentDesc"
																	id="landDocumentDescId" name="landDocumentDesc"
																	value="" type="text" maxlength="250"
																	class="form-control" /></td>
															<td><form:textarea path="landDocumentRemark"
																	id="landDocumentRemarkId" name="landDocumentRemark"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td><label class="table-heading">Affidavit
																	on Prescribed format<a
																	href="./downloadFoodDocument?fileName=${foodDocument.annexurea6}">View</a>
															</label></td>

															<td><form:input path="affidavitPrescribedFormatDesc"
																	id="affidavitPrescribedFormatDescId"
																	name="affidavitPrescribedFormatDesc" value=""
																	type="text" maxlength="250" class="form-control" /></td>
															<td><form:textarea
																	path="affidavitPrescribedFormatRemark"
																	id="affidavitPrescribedFormatRemarkId"
																	name="affidavitPrescribedFormatRemark"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
														
														 <tr>
                                                      <td><label class="table-heading">Incorporation Certificate of Firm<a href="./downloadFoodDocument?fileName=${foodDocument.incorporationcertificateoffirm}">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="incorporationCertificateOfFirmDesc" id="incorporationCertificateOfFirmDescId"
                                                          name="incorporationCertificateOfFirmDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="incorporationCertificateOfFirmRemark" id="incorporationCertificateOfFirmRemarkId" 
                                                          name="incorporationCertificateOfFirmRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                   <tr>
                                                      <td><label class="table-heading">Partnership deed / Byelaws of the Firm<a href="./downloadFoodDocument?fileName=${foodDocument.partnershipdeedbyelawsofthefirm}">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="partnershipDeedDesc" id="partnershipDeedDescId"
                                                          name="partnershipDeedDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="partnershipDeedRemark" id="partnershipDeedRemarkId" 
                                                          name="partnershipDeedRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  
                                                   <tr>
                                                      <td><label class="table-heading">Annexure-A/13 (Bank Certificate for interest liability)<a href="./downloadFoodDocument?fileName=${foodDocument.annexurea13}">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="a13BankCertiForIntLiabilityDesc" id="a13BankCertiForIntLiabilityDescId"
                                                          name="a13BankCertiForIntLiabilityDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="a13BankCertiForIntLiabilityRemark" id="a13BankCertiForIntLiabilityRemarkId" 
                                                          name="a13BankCertiForIntLiabilityRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Annexure-A/18 CA certificate for increase in Gross Block Value<a href="./downloadFoodDocument?fileName=${foodDocument.annexurea18cacertificateforincreaseingrossblockvalue}">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="a18certiForIncreInGrossBlockValueDesc" id="a18certiForIncreInGrossBlockValueDescId"
                                                          name="a18certiForIncreInGrossBlockValueDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="a18certiForIncreaseInGrossBlockValueRemark" id="a18certiForIncreaseInGrossBlockValueRemarkId" 
                                                          name="a18certiForIncreaseInGrossBlockValueRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                      

													</tbody>

												</table>
											</div>
										</div>
									</div>
									<%-- <div class="mt-4">
										<div class="without-wizard-steps">
											<h4 class="card-title mb-4 mt-4">Eligible cost of Plant
												Machinery-</h4>
										</div>
									</div>
									<div class="table-responsive mt-3">
										<table class="table table-stripped table-bordered"
											id="BreakUpTable1">
											<thead>
												<tr>
													<th>S.No.</th>
													<th>Name of Plant Machinery</th>
													<th>Name of the Supplier company</th>
													<th>Basic Price</th>
													<th>Total</th>
													<th style="width: 17%;">Eligible cost in lacs</th>
												</tr>
											</thead>
											<tbody class="add-from-here1">
												<c:forEach var="list" items="${plantAndMachinerytobeCSISList}"
													varStatus="counter">
													<tr>
														<td><c:out value="${list.sno}" /></td>
														<td><form:input path="eligibleCostNameOfPlantMach"
																id="eligibleCostNameOfPlantMachId"
																name="eligibleCostNameOfPlantMach"
																value="${list.machinename}" type="text" readonly="true"
																class="form-control" /></td>
														<td><form:input
																path="eligibleCostNameOfSupplierCompany"
																id="eligibleCostNameOfSupplierCompanyId"
																name="eligibleCostNameOfSupplierCompany"
																value="${list.suppliername}" type="text" readonly="true"
																class="form-control" /></td>
														<td><form:input path="eligibleCostBasicPrice"
																id="eligibleCostBasicPriceId"
																name="eligibleCostBasicPrice" value="${list.basicprice}"
																type="text" readonly="true" class="form-control" /></td>
														<td><form:input path="totalplandmachamt"
																id="eligibleCostTotalId" name="totalplandmachamt"
																value="${list.totalplandmachamt}" type="text" maxlength="12" class="form-control" /></td>
														<td><form:input path="eligiblecostinlacs"
																id="eligibleCostInLacsId" name="eligiblecostinlacs"
																value="${list.eligiblecostinlacs}" maxlength="12" type="text"
																class="form-control eligibleCostInPlant" /></td>
													</tr>
												</c:forEach>
												<tr>
													<td colspan="5" class="text-right"><strong>Total:</strong></td>
													<td><form:input path="totaleEligibleCostInLacs"
															id="totaleEligibleCostInLacsId"
															name="totaleEligibleCostInLacs" value="" type="text"
															readonly="true"
															class="form-control eligibleCostInPlantTotal" /></td>
												</tr>
											</tbody>

										</table>
									</div>
									<div class="mt-4">
										<div class="without-wizard-steps">
											<label class="mt-4">Nodal Officer's Remark</label>
											<form:textarea path="eligibleCostRemarks"
												id="eligibleCostRemarksId" name="eligibleCostRemarks"
												class="form-control border-info" rows="2"></form:textarea>
										</div>
									</div>
									<div class="mt-4">
										<div class="without-wizard-steps">
											<h4 class="card-title mb-4 mt-4">Eligible Technical
												Civil Work-</h4>
										</div>
									</div>
									<div class="table-responsive mt-3">
										<table class="table table-stripped table-bordered"
											id="BreakUpTable2">
											<thead>
												<tr>
													<th>S.No.</th>
													<th>Particular</th>
													<th>Area (Sqmt.)</th>
													<th>Rate</th>
													<th>Amount</th>
													<th style="width: 17%;">Eligible cost (in lacs)</th>
												</tr>
											</thead>
											<tbody class="add-from-here2">
												<c:forEach var="list"
													items="${detailsofEligibleTechnicalCivilWorkList}"
													varStatus="counter">
													<tr>
														<td><c:out value="${list.sno}" /></td>
														<td><form:input
																path="eligibleTechCivilWorkParticular"
																id="eligibleTechCivilWorkParticularId"
																name="eligibleTechCivilWorkParticular"
																value="${list.particulars}" type="text" readonly="true"
																class="form-control" /></td>

														<td><form:input path="eligibleTechCivilWorkArea"
																id="eligibleTechCivilWorkAreaId"
																name="eligibleTechCivilWorkArea"
																value="${list.areasqft}" type="text" readonly="true"
																class="form-control" /></td>
														<td><form:input path="eligibleTechCivilWorkRate"
																id="eligibleTechCivilWorkRateId"
																name="eligibleTechCivilWorkRate" value="${list.rate}"
																type="text" readonly="true" class="form-control" /></td>
														<td><form:input path="eligibleTechCivilWorkAmount"
																id="eligibleTechCivilWorkAmountId"
																name="eligibleTechCivilWorkAmount"
																value="${list.amount}" type="text" readonly="true"
																class="form-control" /></td>
														<td><form:input
																path="eligibleTechCivilWorkEligibleCost"
																id="eligibleTechCivilWorkEligibleCostId"
																name="eligibleTechCivilWorkEligibleCost" value="${list.eligiblecostinlacs}"
																maxlength="12" type="text"
																class="form-control eligibleCostInCivilWork" /></td>
													</tr>
												</c:forEach>
											</tbody>
											<tfoot>
												<tr>
													<td colspan="5" class="text-right"><strong>Total:</strong></td>
													<td><form:input
															path="ttlEligibleTechCivilWorkEligibleCost"
															id="ttlEligibleTechCivilWorkEligibleCostId"
															name="ttlEligibleTechCivilWorkEligibleCost" value=""
															type="text" readonly="true"
															class="form-control eligibleCostInCivilWorkTotal" /></td>
												</tr>
											</tfoot>
										</table>
									</div>
									<div class="mt-4">
										<div class="without-wizard-steps">
											<label class="mt-4">Nodal Officer's Remark</label>
											<form:textarea path="eligibleTechCivilWorkRemarks"
												id="eligibleTechCivilWorkRemarksId"
												name="eligibleTechCivilWorkRemarks"
												class="form-control border-info" rows="2"></form:textarea>
										</div>
									</div>
									<div class="mt-4">
										<div class="without-wizard-steps">
											<h4 class="card-title mb-4 mt-4">Eligible Grant amount:</h4>
										</div>
									</div>
									<div class="table-responsive mt-3">
										<table class="table table-stripped table-bordered"
											id="BreakUpTable3">
											<tbody class="add-from-here3">
												<tr>
													<td><p>Eligible cost of Plant Machinery</p></td>
													<td><form:input
															path="grantAmtEligiblleCostOfPlantMach"
															id="grantAmtEligiblleCostOfPlantMachId"
															name="grantAmtEligiblleCostOfPlantMach" value=""
															type="text" readonly="true"
															class="form-control eligibleCostInPlantTotal" /></td>
												</tr>
												<tr>
													<td><p>Eligible cost of Technical civil work</p></td>
													<td><form:input
															path="grantAmtEligibleCostOfTechnicalCivilWork"
															id="grantAmtEligibleCostOfTechnicalCivilWorkId"
															name="grantAmtEligibleCostOfTechnicalCivilWork" value=""
															type="text" readonly="true"
															class="form-control eligibleCostInCivilWorkTotal" /></td>
												</tr>
												<tr>
													<td><p>Eligible Project cost</p></td>
													<td><form:input path="grantAmtEligibleProjectCost"
															id="grantAmtEligibleProjectCostId"
															name="grantAmtEligibleProjectCost" value="" type="text"
															readonly="true" class="form-control" /></td>
												</tr>
												<tr>
													<td><p>Eligibility of Grant @7% of Eligible P/M &
															TC</p></td>
													<td><form:input path="grantAmtEligiblePM"
															id="grantAmtEligiblePMId" name="grantAmtEligiblePM"
															value="" type="text" readonly="true" class="form-control" /></td>
												</tr>
												<tr>
													<td><p>
															Max. Eligible Grant <a href="javascript:void(0);"
																class="remove-row" data-toggle="tooltip" title=""
																data-original-title="As per Food Policy-2017 for the Interest subsidy Maximum Amount of 50 Lakhs per year or Eligibility of Grant @7% of Eligible P/M & TC or rate of interest whichever is less will be Provided for the 5 years"><i
																class="fa fa-info-circle text-info"></i></a>
														</p></td>
													<td><form:input path="grantAmtMaxEligibleGrant"
															id="grantAmtMaxEligibleGrantId"
															name="grantAmtMaxEligibleGrant" value="" type="text"
															readonly="true" class="form-control" /></td>
												</tr>

											</tbody>
										</table>
									</div> --%>
									<div class="mt-4">
										<div class="without-wizard-steps">
											<label class="mt-4">Nodal Officer's Remark</label>
											<form:textarea path="grantAmtRemarks" id="grantAmtRemarksId"
												name="grantAmtRemarks" class="form-control border-info"
												rows="2"></form:textarea>
										</div>
									</div>
									<div class="mt-4">
										<div class="without-wizard-steps">
											<h4 class="card-title  mt-4">Interest Subsidy</h4>
											<form:textarea path="interestSubsidy" id="interestSubsidyId"
												name="interestSubsidy" class="form-control border-info"
												rows="2"></form:textarea>
										</div>
									</div>
									<div class="mt-4">
										<div class="without-wizard-steps">
											<h4 class="card-title  mt-4">Recommendation/Comments</h4>
											<form:textarea path="recommendationComments"
												id="recommendationCommentsId" name="recommendationComments"
												class="form-control border-info" rows="2"></form:textarea>
										</div>
									</div>
									<hr class="mt-4 mb-4">
									<div class="row">
										<div class="col-sm-12">
											<div class="custom-control custom-checkbox mb-1">
												<input type="checkbox" class="custom-control-input"
													id="verified" name="Capital-Interest-Subsidy"> <label
													class="custom-control-label" for="verified"
													id="verifiedChecked">I have read and verified the
													entire form carefully</label>
											</div>
										</div>
									</div>
									<hr class="mt-4 mb-4">
									<div class="row mt-4">
										<div class="col-sm-12 text-center">
											<button type="button"
												class="btn btn-outline-secondary evaluate-btn btn-sm mb-3"
												data-target="#evaluateConfirm" data-toggle="modal"
												id="evaluateBtn">Evaluation Completed</button>
											<a href="#RaiseQuery"
												class="btn btn-outline-danger btn-sm mb-3"
												data-toggle="modal">Raise Query</a>
											<button type="button" id="IncludeApplicationBtn"
												class="btn btn-outline-info disable-btn btn-sm mb-3">Include
												Application in Agenda Note</button>
											<a href="#IncludeApplicationBtn"
												class="btn btn-outline-danger disable-btn btn-sm mb-3"
												data-toggle="modal" id="RejectBtn">Reject</a>
										</div>
									</div>
									<hr class="mt-2">
									<div class="row">
										<div class="col-sm-5">
											<a
												href="./foodProcessingFPI?unit_Id=${businessEntityDetailsfood.unit_id}"
												class="common-default-btn mt-3">Back</a>
										</div>
										<div class="col-sm-7 text-right">
											<a href="javacript:void(0);" class="common-btn mt-3"><em
												class="fa fa-edit"></em> Edit</a>
											<form:button class="common-btn mt-3">Save</form:button>
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
		<div class="modal fade" id="evaluateConfirm">
			<div class="modal-dialog">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Confirmation</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<p>Are you sure you want to submit the application</p>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="evaluation-view.html" class="btn common-btn btn-sm mt-0">Yes</a>
						<button type="button" class="btn btn-outline-danger mt-0"
							data-dismiss="modal">No</button>
					</div>
				</div>
			</div>
		</div>
		<!-- The Modal -->
		<div class="modal fade" id="RejectApplication">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Reject Application</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>Reason</label>
									<textarea class="form-control" rows="5"></textarea>
								</div>
							</div>
						</div>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="javacript:void();" class="common-btn mt-0"
							data-toggle="modal" data-dismiss="modal">Submit</a>
					</div>
				</div>
			</div>
		</div>
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