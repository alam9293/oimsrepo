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
<script src="js/custom.js"></script>
<script type="text/javascript">
	window.onload = function() {
		if ('${flag}' === 'false') {
			document.getElementById('showHide1').style.display = 'none';
			document.getElementById('showHide2').style.display = 'none';
		} else {
			document.getElementById('showHide1').style.display = 'block';
			document.getElementById('showHide2').style.display = 'block';
		}

		if ('${flag1}' === 'false1') {
			document.getElementById('showHide3').style.display = 'table-row';

		} else {
			document.getElementById('showHide3').style.display = 'none';

		}

	}
	function ApprovedAgendaNoteValidation() {
		<c:if test="${empty flag}">
		alert("Please select Applicant Record.");
		return false
		</c:if>

	}
	function validate(value) {
		document.getElementById("appliId").value = value;
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

	function SubmitToApproval() {
		var r = confirm("Are you Sure Want to Submit the Prepare Agenda Note for Approval?");

		if (r == true) {
			alert("Prepare Agenda Note Submitted Sucessfully for Approval");
		} else {
			return false
		}

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
						<li class="nav-item"><a class="nav-link" href="./userLogout2"><i
								class="fa fa-power-off"></i> Logout</a></li>
						<li class="nav-item"><a class="nav-link active" href="#"><i
								class="fa fa-user"></i> ${userName}</a></li>
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
						<li class="nav-item"><a class="nav-link" href="./dashboard"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
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
							<div class="col-sm-5">
								<a href="./agendaNode" class="common-default-btn mt-3">Back</a>
							</div>
							<!-- <div class="col-sm-7 text-right">
                                <a href="javacript:void(0);" class="common-btn mt-3"><i class="fa fa-edit"></i> Edit</a>
                            </div> -->
						</div>
						<form:form modelAttribute="PrepareAgendaNotes" class="mt-4"
							name="PrepareAgendaNotes" method="get">
							<form:hidden path="prepareAgendaNote" id="prepareAgendaNote"></form:hidden>
							<form:hidden path="appliId" id="appliId"></form:hidden>
							<div class="row mt-5">
								<div class="col-sm-12">
									<div class="table-responsive">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>Application ID</th>
													<th>Company Name</th>
													<th>Type</th>
													<th>Product</th>
													<th>Category</th>
													<th>Investment</th>
													<th>Location</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="list" items="${investmentDetailsmixedList}"
													varStatus="counter">
													<tr>
														<td>${list.appliId}</td>
														<td>${list.companyName}</td>
														<td><c:out value="${natureOfProject}"/></td>
														<td><c:out value="${products}" /></td>
														<td>${list.categoryIndsType}</td>
														<td>${list.investment}</td>
														<td>${list.district},${list.region}</td>
														<td><input type="submit" value="View"
															class="btn btn-outline-info btn-sm"
															formaction="./viewPreparedAgendaDetails?appliId=${list.appliId}"
															onclick="return validate('${list.appliId}')"></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</form:form>
						<div class="row" id="showHide1">
							<div class="col-sm-12">
								<div class="mt-4">
									<div class="without-wizard-steps">
										<!--   <h4 class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">(A). Evaluate Application</h4> -->
									</div>
								</div>
								<!-- <hr class="mt-2 mb-5"> -->
								<div class="row">
									<div class="col-sm-12">
										<div class="table-responsive">
											<table class="table table-bordered">
												<tbody>
													<tr>
														<td style="width: 50%;"><label class="table-heading">New
																Project or Expansion / Diversification</label></td>
														<td><select class="form-control" disabled="disabled">
																<option value="${natureOfProject}" selected>${natureOfProject}</option>

														</select></td>
													</tr>
													<tr id="showHide3">
														<td colspan="3" class="p-4">

															<div class="row">
																<div class="col-sm-12">
																	<label class="table-heading">Product Details</label>
																</div>
															</div>

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
																			<td>Existing Products</td>
																			<td><input type="text" class="form-control"
																				value="${ExistingProducts}" disabled="disabled"
																				name="ExistingProducts"></td>
																		</tr>
																		<tr>
																			<td>Existing Installed Capacity</td>
																			<td><input type="text" class="form-control"
																				value="${existingInstalledCapacity}"
																				disabled="disabled" name="existingInstalledCapacity"></td>
																		</tr>
																		<tr>
																			<td>Proposed Products</td>
																			<td><input type="text" class="form-control"
																				value="${proposedProducts}" disabled="disabled"
																				name="proposedProducts"></td>
																		</tr>
																		<tr>
																			<td>Proposed Installed Capacity</td>
																			<td><input type="text" class="form-control"
																				value="${proposedInstalledCapacity}"
																				disabled="disabled" name="proposedInstalledCapacity"></td>
																		</tr>
																		<tr>
																			<td>Existing Gross Block</td>
																			<td><input type="text" class="form-control"
																				value="${existingGrossBlock}" disabled="disabled"
																				name="existingGrossBlock"></td>
																		</tr>
																		<tr>
																			<td>Proposed Gross Block</td>
																			<td><input type="text" class="form-control"
																				value="${proposedGrossBlock}" disabled="disabled"
																				name="proposedGrossBlock"></td>
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
														</td>
													</tr>

													<tr>
														<td><label class="table-heading">If Expansion
																/ Diversification whether proposed gross block more then
																25% of the existing gross block</label></td>
														<td><select class="form-control" disabled="disabled">
																<option value="${pergrosblock}" selected>${pergrosblock}</option>
														</select></td>
													</tr>


													<tr>
														<td><label class="table-heading">Category of
																Industrial Undertaking</label></td>
														<td><select class="form-control" disabled="disabled">
																<option value="${category}" selected>${category}</option>
														</select></td>
													</tr>

													<tr>
														<td colspan="3" class="p-4">

															<div class="row">
																<div class="col-sm-12">
																	<label class="table-heading">Break up of
																		proposed capital Investment</label>
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
																			<td><select class="form-control"
																				disabled="disabled">
																					<option value="1" selected="selected">Yes</option>
																					<option value="2">No</option>
																			</select></td>
																			<td><input type="text"
																				class="CostasperDPR form-control" name="InvLC"
																				value="${InvLC} "></td>
																			<td><input type="text"
																				class="CostasperIIEPP form-control" name=""
																				value="${LCasperIEPP}"></td>
																			<td><input type="text" class="form-control"
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
																			<td><select class="form-control">
																					<option value="1">Yes</option>
																					<option value="2">No</option>
																			</select></td>
																			<td><input type="text"
																				class="CostasperDPR form-control" name=""
																				value="${InvBuiildC}"></td>
																			<td><input type="text"
																				class="CostasperIIEPP form-control" name="">
																			</td>
																			<td><input type="text" class="form-control"
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
																			<td><select class="form-control">
																					<option value="1">Yes</option>
																					<option value="2">No</option>
																			</select></td>
																			<td><input type="text"
																				class="CostasperDPR form-control" name=""
																				value="${invPlantAndMachCost}"></td>
																			<td><input type="text"
																				class="CostasperIIEPP form-control" name="">
																			</td>
																			<td><input type="text" class="form-control"
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
																			<td><select class="form-control">
																					<option value="1">Yes</option>
																					<option value="2">No</option>
																			</select></td>
																			<td><input type="text"
																				class="CostasperDPR form-control" name=""
																				value="${invMisFixCost}"></td>
																			<td><input type="text"
																				class="CostasperIIEPP form-control" name="">
																			</td>
																			<td><input type="text" class="form-control"
																				name=""></td>
																		</tr>
																		<tr>
																			<!--  <td>
                                                              <div class="custom-control custom-checkbox mb-4">
                                                                <input type="checkbox" class="custom-control-input" id="selectItem5" name="SlectItem">
                                                                <label class="custom-control-label" for="selectItem5"></label>
                                                              </div>
                                                            </td> -->
																			<td><input type="text" class="form-control"
																				name=""></td>
																			<td><select class="form-control">
																					<option value="1">Yes</option>
																					<option value="2">No</option>
																			</select></td>
																			<td><input type="text"
																				class="CostasperDPR form-control" name=""></td>
																			<td><input type="text"
																				class="CostasperIIEPP form-control" name="">
																			</td>
																			<td><input type="text" class="form-control"
																				name=""></td>

																		</tr>
																		<tr>
																			<td colspan="2" align="right"><strong>Total:</strong></td>
																			<td><input type="text" readonly="readonly"
																				class="TotalCostasperDPR form-control" name=""></td>
																			<td><input
																				class="TotalCostasperIIEPP form-control"
																				readonly="readonly" name=""></td>
																			<td></td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</td>
													</tr>

													<tr>
														<td><label class="table-heading">Whether the
																company should have filed LOI/IEM registration with the
																Department of Industrial Policy and Promotion GoI.</label></td>
														<td><select class="form-control" disabled="disabled">
																<option value="${regorlic}" selected>${regorlic}</option>
														</select></td>
													</tr>

													<tr>
														<td><label class="table-heading">Whether
																Industrial Undertaking should not have Govt./Govt.
																undertaking equity of more than 50%.</label></td>
														<td><select class="form-control" disabled="disabled">
																<option value="1">Yes</option>
																<option value="2" selected="selected">No</option>
														</select></td>
													</tr>

													<tr>
														<td colspan="3" class="p-4">

															<div class="row">
																<div class="col-sm-12">
																	<label class="table-heading">Eligible
																		Investment Period</label>
																</div>
															</div>

															<div class="table-responsive mt-3">
																<table class="table table-stripped table-bordered">
																	<tbody>
																		<tr>
																			<!-- <td>
                                                              <div class="custom-control custom-checkbox mb-4">
                                                                <input type="checkbox" class="custom-control-input" id="EIP1" name="SlectItem">
                                                                <label class="custom-control-label" for="EIP1"></label>
                                                              </div>
                                                            </td> -->
																			<td style="width: 80%;">In case of small &
																				medium industrial undertakings, the period
																				commencing from cutoff date falling in the effective
																				period of these Rules upto 3 years or till the date
																				of commencement of commercial production, whichever
																				is earlier. Such cases will also be covered in which
																				the cut-off date is within the period immediately
																				preceding 3 years from the effective date, subject
																				to the condition that commercial production in such
																				cases commences on or after the effective date. In
																				any case, the maximum eligible investment period
																				shall not be more than 3 years.</td>
																			<td><select class="form-control"
																				disabled="disabled">
																					<c:choose>
																						<c:when test="${ctypeSMALL eq 'Yes'}">
																							<option value="${ctypeSMALL}" selected="selected">${ctypeSMALL}</option>
																							<option value="No">No</option>
																						</c:when>
																						<c:otherwise>
																							<option value="Yes">Yes</option>
																							<option value="${ctypeSMALL}" selected="selected">${ctypeSMALL}</option>
																						</c:otherwise>
																					</c:choose>
																			</select></td>
																		</tr>
																		<tr>
																			<!--  <td>
                                                              <div class="custom-control custom-checkbox mb-4">
                                                                <input type="checkbox" class="custom-control-input" id="EIP2" name="SlectItem">
                                                                <label class="custom-control-label" for="EIP2"></label>
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
																			<td><select class="form-control"
																				disabled="disabled">
																					<c:choose>
																						<c:when test="${ctypeLARGE eq 'Yes'}">
																							<option value="${ctypeLARGE}" selected="selected">${ctypeLARGE}</option>
																							<option value="No">No</option>
																						</c:when>
																						<c:otherwise>
																							<option value="Yes">Yes</option>
																							<option value="${ctypeLARGE}" selected="selected">${ctypeLARGE}</option>
																						</c:otherwise>
																					</c:choose>
																			</select></td>
																		</tr>
																		<tr>
																			<!-- <td>
                                                              <div class="custom-control custom-checkbox mb-4">
                                                                <input type="checkbox" class="custom-control-input" id="EIP3" name="SlectItem">
                                                                <label class="custom-control-label" for="EIP3"></label>
                                                              </div>
                                                            </td> -->
																			<td>In case of mega & mega plus industrial
																				undertakings, the period commencing from the cut-off
																				date falling in the effective period of these Rules,
																				and upto 5 years, or the date of commencement of
																				commercial production, whichever falls earlier. Such
																				cases will also be covered in which the cut-off date
																				is within the period immediately preceding 3 years
																				from the effective date, subject to the condition
																				that atleast 40% of eligible capital investment
																				shall have to be undertaken after the effective
																				date. In any case, the maximum eligible investment
																				period shall not be more than 5 years.</td>
																			<td><select class="form-control"
																				disabled="disabled">
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
                                                                <input type="checkbox" class="custom-control-input" id="EIP4" name="SlectItem">
                                                                <label class="custom-control-label" for="EIP4"></label>
                                                              </div>
                                                            </td> -->
																			<td>in case of super mega industrial
																				undertakings, the period commencing from the cutoff
																				date falling in the effective period of these Rules,
																				and upto 7 years, or the date of commencement of
																				commercial production, whichever falls earlier. Such
																				cases will also be covered in which the cut-off date
																				is within the period immediately preceding 3 years
																				from the effective date, subject to the condition
																				that atleast 40% of eligible capital investment
																				shall have to be undertaken after the effective
																				date. In any case, the maximum eligible investment
																				period shall not be more than 7 years.</td>
																			<td><select class="form-control"
																				disabled="disabled">
																					<c:choose>
																						<c:when test="${ctypeSUPERMEGA eq 'Yes'}">
																							<option value="${ctypeSUPERMEGA}"
																								selected="selected">${ctypeSUPERMEGA}</option>
																							<option value="No">No</option>

																						</c:when>
																						<c:otherwise>
																							<option value="Yes">Yes</option>
																							<option value="${ctypeSUPERMEGA}"
																								selected="selected">${ctypeSUPERMEGA}</option>

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
														<td><select class="form-control" disabled="">
																<option value="1" selected="">Yes</option>
																<option value="2">No</option>
														</select></td>
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

																	</c:when>
																	<c:otherwise>
																		<option value="Yes">Yes</option>
																		<option value="${phsWsAply}" selected="selected">${phsWsAply}</option>

																	</c:otherwise>
																</c:choose>
														</select></td>
													</tr>

													<tr>
														<td><label class="table-heading">Investment
																made till date as per CA/Statutory Certificate </label></td>
														<td><input type="text" class="form-control"
															placeholder="Amount in Rs." name=""></td>
													</tr>

												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row" id="showHide2">
							<div class="col-sm-12">
								<div class="mt-4">
									<div class="without-wizard-steps">
										<h4
											class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Incentive
											Sought</h4>
									</div>
								</div>
								<hr class="mt-2 mb-5">
								<div class="row">
									<div class="col-sm-12">
										<div class="table-responsive">
											<table class="table table-stripped table-bordered">
												<thead>
													<tr>
														<th>Sr. No.</th>
														<th style="width: 25%;">Details of Incentives Sought
															and Concerned Department</th>
														<th style="width: 10%;">Amount of Incentives <small>(Rs.
																in crores)</small></th>
														<th>Incentive as per Rules</th>
														<th style="width: 13%;">Justification (if any) given
															by the Company</th>
														<th style="width: 15%;">Remarks</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>1</td>
														<td><strong>Amount of SGST claimed for
																reimbursement</strong></td>
														<td><input type="text" class="form-control"
															value="${ISF_Claim_Reim}" disabled="disabled"></td>
														<td><a href="./pdffiles/IIEPP_Rules_2017.pdf"
															target="_blank">As per Table – 3</a> of Rules related
															to IIEPP-2017 (The Rules), there is a provision for
															reimbursement of 70% of deposited GST (Net SGST) for 10
															years.</td>
														<td><textarea class="form-control"
																disabled="disabled">No Justification</textarea></td>
														<td><textarea class="form-control"></textarea></td>
													</tr>

													<tr>
														<td>1.1</td>
														<td>Additional 10% GST where 25% minimum SC/ST
															workers employed subject to minimum of 400 total workers
															in industrial undertakings located in Paschimanchal and
															minimum of 200 total workers in B-P-M</td>
														<td><input type="text" class="form-control"
															value="${ISF_Reim_SCST}"></td>
														<td><a href="./pdffiles/IIEPP_Rules_2017.pdf"
															target="_blank">As per Table 3</a> of The Rules, there is
															a provision for 75% Stamp Duty Exemption in Paschimanchal
															Region. <br>Further, as per G.O. No.
															1515/77-6-18-5(M)/17, dated 1.5.2018, there is a
															provision for reimbursement equivalent to the paid Stamp
															Duty, for which company will have to apply before
															concerned GM, DIC.</td>
														<td><textarea class="form-control"
																disabled="disabled">No Justification</textarea></td>
														<td><textarea class="form-control"></textarea></td>
													</tr>

													<tr>
														<td>1.2</td>
														<td>Additional 10% GST where 40% minimum female
															workers employed subject to minimum of 400 total workers
															in industrial undertakings located in Paschimanchal and
															minimum of 200 total workers in B-P-M</td>
														<td><input type="text" class="form-control"
															value="${ISF_Reim_FW}"></td>
														<td><a href="./pdffiles/IIEPP_Rules_2017.pdf"
															target="_blank">As per para 3.3</a> and Table 3 of The
															Rules, there is a provision for incentive of
															reimbursement of EPF to the extent of 50% of employer’s
															contribution to all such new Industrial undertakings
															providing direct employment to 100 or more unskilled
															workers, after three years from the date of commercial
															production for a period of 5 years.</td>
														<td><textarea class="form-control"
																disabled="disabled">No Justification</textarea></td>
														<td><textarea class="form-control"></textarea></td>
													</tr>

													<tr>
														<td>1.3</td>
														<td>Additional 10% GST where 25% minimum BPL workers
															employed subject to minimum of 400 total workers in
															industrial undertakings located in Paschimanchal and
															minimum of 200 total workers in B-P-M</td>
														<td><input type="text" class="form-control"
															value="${ISF_Reim_BPLW}"></td>
														<td><a href="./pdffiles/IIEPP_Rules_2017.pdf"
															target="_blank">As per para 3.5.7</a> of the Rules, there
															is a following provision: The industries which are
															disallowed for input tax credit under the GST regime,
															will be eligible for reimbursement of that amount of GST
															paid on purchase of plant and machinery, building
															material and other capital goods during construction and
															commissioning period and raw materials and other inputs
															in respect of which input tax credit has not been
															allowed. While calculating the overall eligible capital
															investment such amount will be added to the fixed capital
															investment.</td>
														<td><textarea class="form-control"
																disabled="disabled">No Justification</textarea></td>
														<td><textarea class="form-control"></textarea></td>
													</tr>

													<tr>
														<td>5</td>
														<td>Exemption from Electricity Duty on power drawn
															from power companies for 20 years <br>
														<strong>Energy/UPPCL/ Electricity Safety</strong>

														</td>
														<td><input type="text" class="form-control"
															value="${ISF_EX_E_Duty_PC}"></td>
														<td><a href="./pdffiles/IIEPP_Rules_2017.pdf"
															target="_blank">As per para 3.5.5</a> of the Rules, there
															is a provision for Exemption from Electricity Duty for 10
															years to all new industrialundertakings producing
															electricity from captive power plants for self-use.</td>
														<td><textarea class="form-control"
																disabled="disabled">No Justification</textarea></td>
														<td><textarea class="form-control"></textarea></td>
													</tr>

													<tr>
														<td>6</td>
														<td>Exemption from Mandi Fee for minimum 15 years
															(100% for 20 years) <br>
														<strong>Agriculture Marketing &amp; Agriculture
																Foreign Trade/MandiParishad</strong>

														</td>
														<td><input type="text" class="form-control"
															value="${ISF_EX_Mandee_Fee}"></td>
														<td><a href="./pdffiles/IIEPP_Rules_2017.pdf"
															target="_blank">As per para 3.5.6</a> of The Rules, There
															is provision for exemption from Mandi Fee for all new
															food-processing undertakings on purchase of raw material
															for 5 years.</td>
														<td><textarea class="form-control"
																disabled="disabled">No Justification</textarea></td>
														<td><textarea class="form-control"></textarea></td>
													</tr>
													<tr>
														<td colspan="2"></td>
														<td><strong>Total : ${total}</strong></td>
														<td colspan="4"></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Note <small>(If Any)</small></label>
											<textarea class="form-control" disabled="disabled" rows="4">${prepAgenNotes}</textarea>
										</div>
									</div>
								</div>
								<hr>
								<form:form modelAttribute="PrepareAgendaNotes"
									action="saveForwardedToMdpicup" class="mt-4"
									name="PrepareAgendaNotes" method="POST"
									onsubmit="return ApprovedAgendaNoteValidation()">
									<form:hidden path="prepareAgendaNote" id="prepareAgendaNote"></form:hidden>
									<div class="row mb-3">
										<div class="col-sm-5">
											<a href="./agendaNode" class="common-default-btn mt-3">Back</a>
										</div>


										<div class="col-sm-7 text-right">
											<form:hidden path="appliId" id="appliId"></form:hidden>
											<input type="submit" value="Submit for Approval"
												onclick="return SubmitToApproval()" class="common-btn mt-0">

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
    $(document).ready(function() {
        $("[data-toggle=offcanvas]").click(function() {
            $(".row-offcanvas").toggleClass("active");
        });
    });
    </script>
    <script>
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
>
