<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="com.webapp.ims.model.EvaluateInvestmentDetails"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>

<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>DIS Evaluation View</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/style.css">

<script type="text/javascript">

	function Saveconfirm() {

		if (!DocumentconfProvbyCTDDoc(document
				.getElementById('confProvbyCTDDoc'))) {
			return false;
		}

		if (!$('#verified').is(':checked')) {
			alert('Please check and verify the form.');
			$("#verified").focus();
			return false;
		}

		var r = confirm("Are you sure, You want to save the form data ?");

		if (r == true) {
			alert("Application Data Saved Successfully");
		} else {
			return false
		}

	}
</script>
<script type="text/javascript">
	window.onload = function() {
		if ('${enableIncludeAgenda.status}' == "Evaluation Completed") {
			//$("#button1").removeClass("evaluate-btn");
			$("#button2").removeClass("evaluate-btn");
		} else {
			//$("#button2").addClass("disable-btn");
		}
	}

	/* Pankaj */
	function RejectApplication() {
		var r = confirm("Are you Sure Want to Reject the Application ?");

		if (r == true) {
			alert("Application Rejected Successfully");
		} else {
			return false
		}

	}
	/* Pankaj */
	function RaiseQuery() {
		var r = confirm("Are you Sure Want to Submit the Query ?");

		if (r == true) {
			alert("Query Raised Successfully");
		} else {
			return false
		}

	}
	/* Pankaj */
	function includeAgendaNote() {
		var r = confirm("Are you Sure Want to Include Application in Agenda Note?");

		if (r == true) {
			alert("Application Included In Prepare Agenda Note Successfully");
		} else {
			return false
		}

	}
	//By Pankaj
	function enableButton2() {

		var r = confirm("Are you Sure Want to Complete the Evaluation?");
		var apcId = '${appId}';
		if (r == true) {
			alert("Your Application ID - "
					+ apcId
					+ "Evaluated Successfully, Please Keep your Application ID for Your Reference in the Future.");
			document.getElementById("button2").disabled = false;
		} else {
			document.getElementById("button2").disabled = true;
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
						<li class="nav-item"><a class="nav-link" href="./userLogout"><i
								class="fa fa-power-off"></i> Logout</a></li>
						<li class="nav-item"><a class="nav-link active" href="#"><i
								class="fa fa-user"></i>${userName}</a></li>

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
						<li class="nav-item"><a class="nav-link" href="./dashboard"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./viewAndEvaluate"><i class="fa fa-eye"></i> View and
								Evaluate Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluateSME"><i class="fa fa-eye"></i> View
								and Forward SME applications</a></li>
						<li class="nav-item"><a class="nav-link" href="./queryRaised"><i
								class="fa fa-question-circle"></i> Query</a></li>
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
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-5">
								<a
									href="./agendaNodedis"
									class="common-default-btn mt-3">Back</a>
							</div>
							<!-- <div class="col-sm-7 text-right">
								<a href="#EvaluationAuditTrail" class="text-info mr-3"
									data-toggle="modal">Evaluation Audit Trail</a>
								<a href="javacript:void(0);" class="common-btn mt-3"><i
									class="fa fa-edit"></i> Edit</a>
							</div> -->
						</div>

						<form:form modelAttribute="disViewEvaluate" method="post"
							action="SaveEvaluateApplicationDis" class="mt-4"
							autocomplete="off" enctype="multipart/form-data">
							<form:hidden path="evaluateId" />
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
													<th>Region</th>
													<th>District</th>
													<th>Capital Investment</th>
													<th>Category</th>
													<th>Date of LOC issued</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody id="evalTable">
												<c:forEach var="list" items="${evalObjList}"
													varStatus="counter">
													<tr>
														<td><c:out value="${list[0]}" /></td>
														<td><c:out value="${list[1]}" /></td>
														<td><c:out value="${natureOfProject}" /></td>
														<td><c:out value="${products}" /></td>
														<td><c:out value="${list[2]}" /></td>
														<td><c:out value="${list[3]}" /></td>
														<td><c:out value="${list[4]}" /></td>
														<td><c:out value="${list[5]}" /></td>
														<td><fmt:formatDate value="${locDate}" pattern="dd-MM-yyyy"/></td>
														<td><input type="submit" value="View"
															class="btn btn-outline-info btn-sm"
															formaction="./dispalyAgendaNoteDis?appliId=${list[0]}"
															onclick="return validate('${list[0]}"></td>
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
								<hr class="mt-2">
								<c:import
									url="/WEB-INF/jsp/Disbursement/commonEvaluateApplicationDis.jsp">
								</c:import>
							</div>
						</div>


						<form:form modelAttribute="PrepareAgendaNotesDis" method="post"
							action="saveCommonEvaluteDis" class="mt-4" autocomplete="off"
							enctype="multipart/form-data">

							<div class="row" id="showHide2">
								<div class="col-sm-12">

									<hr class="mt-2 mb-5">
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<label>Note</label>
												<textarea class="form-control" name="note" rows="5">${disprepAgendaNotes}</textarea>
											</div>
										</div>
										<div class="col-sm-12 mt-3">
											<div class="form-group">
												<label>Nodal Agency and IIDD Recommendation</label>
												<textarea class="form-control" name="nodalAgency" rows="5">${nodalAgencyNm}</textarea>
											</div>
										</div>
									</div>

									<hr class="mt-2">


							<div class="row mb-4">
								<div class="col-sm-5">
									<a
										href="./agendaNodedis"
										class="common-default-btn mt-3">Back</a>
								</div>
								<div class="col-sm-7 text-right">
									
									<form:button 
										class="common-btn mt-3">Save</form:button>
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
		<div class="container">

			<div class="modal fade" id="RejectApplication">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">

						<!-- Modal Header -->
						<div class="modal-header">
							<h4 class="modal-title">Reject Application</h4>
							<button type="button" class="close" data-dismiss="modal">&times;</button>
						</div>


						<!-- Modal body -->
						<form:form action="saveRejectApplicationDIS" class="isf-form"
							method="POST">
							<div class="modal-body">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label>Reason</label>
											<textarea class="form-control" rows="5" name="rejectValue"></textarea>
										</div>
									</div>
								</div>
							</div>

							<!-- Modal footer -->
							<div class="modal-footer">

								<button type="submit" onclick="return RejectApplication()"
									class="common-btn" value="Submit">Submit</button>
							</div>
						</form:form>
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

					<form:form action="saveRaiseQueryDIS" class="isf-form"
						method="POST" modelAttribute="raiseQuery"
						enctype="multipart/form-data">
						<!-- Modal body -->
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<form:label path="rqClarifySought">Clarification Sought</form:label>
										<form:textarea path="rqClarifySought" class="form-control"></form:textarea>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<form:label path="rqMissdocdtl">Details of Missing Documents <small>(If
												any)</small>
										</form:label>
										<form:textarea path="rqMissdocdtl" class="form-control"></form:textarea>
									</div>
								</div>

								<div class="col-md-12">
									<div class="form-group">
										<label>Upload Related Document</label>
										<div class="custom-file">
											<input type="file" name="rqFilename"
												class="custom-file-input" id="UploadDocumentForQuery">
											<label class="custom-file-label" for="UploadDocumentForQuery"
												id="UploadDocumentForQuery1">Choose file</label>
										</div>
									</div>
								</div>
							</div>
						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<form:button type="submit" onclick="return RaiseQuery()"
								class="common-btn mt-3">Submit Query</form:button>
						</div>

					</form:form>

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

		<div class="modal fade" id="Formula">
			<div class="modal-dialog modal-xl">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Formula</h4>
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
												<th width="5%">Sl. No.</th>
												<th width="30%">Particulars & Formula</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>1</td>
												<td>Cost of Project = <span class="text-danger">A</span></td>
											</tr>
											<tr>
												<td>2</td>
												<td>Cost of Plant & Machinery or Infrastructure = <span
													class="text-danger">B</span></td>
											</tr>
											<tr>
												<td>3</td>
												<td>Term Loan for Entire Project = <span
													class="text-danger">C</span></td>
											</tr>
											<tr>
												<td>4</td>
												<td>Term Loan for Plant & Machinery or Infrastructure =
													<span class="text-danger">(B/A)*C</span>
												</td>
											</tr>
											<tr>
												<td>5</td>
												<td>Applicable Rate of Interest (ROI) on Loan = <span
													class="text-danger">D</span></td>
											</tr>
											<tr>
												<td>6</td>
												<td>Amount of Interest Paid = <span class="text-danger">E</span></td>
											</tr>
											<tr>
												<td>7</td>
												<td>Proportionate Interest at Applicable Rate of
													Interest for P&M or Infrastructure = <span
													class="text-danger">(B/A)*E</span>
												</td>
											</tr>
											<tr>
												<td>8</td>
												<td>Proportionate amount of interest for P&M or
													Infrastructure at @ 5% p.a. = <span class="text-danger">(B/A)*E*(5/D)</span>
												</td>
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
	<script src="js/datepicker.js"></script>
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
		function amtInvCutOffDateTtl() {
			var sum = 0;
			$(".amtInvCutOff").each(function() {
				sum += +$(this).val();
			});
			$(".amtInvCutOffTotal").val(sum);
		}

		$(document).ready(amtInvCutOffDateTtl);
		$(document).on("keyup", amtInvCutOffDateTtl);
		$(document).on("change", amtInvCutOffDateTtl);

		function amtInvDPRTotal() {
			var sum = 0;
			$(".dprAmt").each(function() {
				sum += +$(this).val();
			});
			$(".dprAmtTotal", this).val(sum);
		}

		$(document).ready(amtInvDPRTotal);
		$(document).on("keyup", amtInvDPRTotal);
		$(document).on("change", amtInvDPRTotal);

		function bankAppraisalTotal() {
			var sum = 0;
			$(".bankApprAmt").each(function() {
				sum += +$(this).val();
			});
			$(".bankApprAmtTotal", this).val(sum);
		}

		$(document).ready(bankAppraisalTotal);
		$(document).on("keyup", bankAppraisalTotal);
		$(document).on("change", bankAppraisalTotal);

		function certificateAuditTotal() {
			var sum = 0;
			$(".perCertiAuditor").each(function() {
				sum += +$(this).val();
			});
			$(".perCertiAuditorTotal", this).val(sum);
		}

		$(document).ready(certificateAuditTotal);
		$(document).on("keyup", certificateAuditTotal);
		$(document).on("change", certificateAuditTotal);

		function capitalInvCATotal() {
			var sum = 0;
			$(".capitalCA").each(function() {
				sum += +$(this).val();
			});
			$(".capitalCATotal", this).val(sum);
		}

		$(document).ready(capitalInvCATotal);
		$(document).on("keyup", capitalInvCATotal);
		$(document).on("change", capitalInvCATotal);

		function capitalInvValuerTotal() {
			var sum = 0;
			$(".capitalValuer").each(function() {
				sum += +$(this).val();
			});
			$(".capitalValuerTotal", this).val(sum);
		}

		$(document).ready(capitalInvValuerTotal);
		$(document).on("keyup", capitalInvValuerTotal);
		$(document).on("change", capitalInvValuerTotal);

		function statutoryAuditerTotal() {
			var sum = 0;
			$(".statutoryAudit").each(function() {
				sum += +$(this).val();
			});
			$(".statutoryAuditTotal", this).val(sum);
		}

		$(document).ready(statutoryAuditerTotal);
		$(document).on("keyup", statutoryAuditerTotal);
		$(document).on("change", statutoryAuditerTotal);

		function cutOffDateAmountTotal() {
			var sum = 0;
			$(".cutOffDateAmt").each(function() {
				sum += +$(this).val();
			});
			$(".cutOffDateAmtTotal", this).val(sum);
		}

		$(document).ready(cutOffDateAmountTotal);
		$(document).on("keyup", cutOffDateAmountTotal);
		$(document).on("change", cutOffDateAmountTotal);

		function perDPRAmountTotal() {
			var sum = 0;
			$(".perDPRAmt").each(function() {
				sum += +$(this).val();
			});
			$(".perDPRAmtTotal", this).val(sum);
		}

		$(document).ready(perDPRAmountTotal);
		$(document).on("keyup", perDPRAmountTotal);
		$(document).on("change", perDPRAmountTotal);

		function perBankApprAmountTotal() {
			var sum = 0;
			$(".perBankAppr").each(function() {
				sum += +$(this).val();
			});
			$(".perBankApprTotal", this).val(sum);
		}

		$(document).ready(perBankApprAmountTotal);
		$(document).on("keyup", perBankApprAmountTotal);
		$(document).on("change", perBankApprAmountTotal);

		function companyDatedAmountTotal() {
			var sum = 0;
			$(".companyDated").each(function() {
				sum += +$(this).val();
			});
			$(".companyDatedTotal", this).val(sum);
		}

		$(document).ready(companyDatedAmountTotal);
		$(document).on("keyup", companyDatedAmountTotal);
		$(document).on("change", companyDatedAmountTotal);

		function empaneledCATotalAmtTotal() {
			var sum = 0;
			$(".empaneledCA").each(function() {
				sum += +$(this).val();
			});
			$(".empaneledCATotal", this).val(sum);
		}

		$(document).ready(empaneledCATotalAmtTotal);
		$(document).on("keyup", empaneledCATotalAmtTotal);
		$(document).on("change", empaneledCATotalAmtTotal);

		function empaneledCAAmtTotal() {
			var sum = 0;
			$(".empaneledValuer").each(function() {
				sum += +$(this).val();
			});
			$(".empaneledValuerTotal", this).val(sum);
		}

		$(document).ready(empaneledCAAmtTotal);
		$(document).on("keyup", empaneledCAAmtTotal);
		$(document).on("change", empaneledCAAmtTotal);

		function certifiAuditTotal() {
			var sum = 0;
			$(".certificateAudit").each(function() {
				sum += +$(this).val();
			});
			$(".certificateAuditTotal", this).val(sum);
		}

		$(document).ready(certifiAuditTotal);
		$(document).on("keyup", certifiAuditTotal);
		$(document).on("change", certifiAuditTotal);

		function subTtlOffTotal() {
			var sum = 0;
			$(".subTtlCutOff").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlCutOffTotal", this).val(sum);
		}

		$(document).ready(subTtlOffTotal);
		$(document).on("keyup", subTtlOffTotal);
		$(document).on("change", subTtlOffTotal);

		function subTtlOffTotal() {
			var sum = 0;
			$(".subTtlCutOff").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlCutOffTotal", this).val(sum);
		}

		$(document).ready(subTtlOffTotal);
		$(document).on("keyup", subTtlOffTotal);
		$(document).on("change", subTtlOffTotal);

		function subTtlDprTotal() {
			var sum = 0;
			$(".subTtlDPR").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlDPRTotal", this).val(sum);
		}

		$(document).ready(subTtlDprTotal);
		$(document).on("keyup", subTtlDprTotal);
		$(document).on("change", subTtlDprTotal);

		function subBankApprTotal() {
			var sum = 0;
			$(".subBankAppr").each(function() {
				sum += +$(this).val();
			});
			$(".subBankApprTotal", this).val(sum);
		}

		$(document).ready(subBankApprTotal);
		$(document).on("keyup", subBankApprTotal);
		$(document).on("change", subBankApprTotal);

		function subTtlAuditorTotal() {
			var sum = 0;
			$(".subTtlAuditor").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlAuditorTotal", this).val(sum);
		}

		$(document).ready(subTtlAuditorTotal);
		$(document).on("keyup", subTtlAuditorTotal);
		$(document).on("change", subTtlAuditorTotal);

		function subTtlCATotal() {
			var sum = 0;
			$(".subTtlCA").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlCATotal", this).val(sum);
		}

		$(document).ready(subTtlCATotal);
		$(document).on("keyup", subTtlCATotal);
		$(document).on("change", subTtlCATotal);

		function subTtlValuerTotal() {
			var sum = 0;
			$(".subTtlValuer").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlValuerTotal", this).val(sum);
		}

		$(document).ready(subTtlValuerTotal);
		$(document).on("keyup", subTtlValuerTotal);
		$(document).on("change", subTtlValuerTotal);

		function subTtlStatutoryTotal() {
			var sum = 0;
			$(".subTtlStatutory").each(function() {
				sum += +$(this).val();
			});
			$(".subTtlStatutoryTotal", this).val(sum);
		}

		$(document).ready(subTtlStatutoryTotal);
		$(document).on("keyup", subTtlStatutoryTotal);
		$(document).on("change", subTtlStatutoryTotal);

		function FinancingDPRTotal() {
			var sum = 0;
			$(".FinancingDPR").each(function() {
				sum += +$(this).val();
			});
			$(".FinancingDPRTotal", this).val(sum);
		}

		$(document).ready(FinancingDPRTotal);
		$(document).on("keyup", FinancingDPRTotal);
		$(document).on("change", FinancingDPRTotal);

		function FinaBankApprTotal() {
			var sum = 0;
			$(".FinaBankAppr").each(function() {
				sum += +$(this).val();
			});
			$(".FinaBankApprTotal", this).val(sum);
		}

		$(document).ready(FinaBankApprTotal);
		$(document).on("keyup", FinaBankApprTotal);
		$(document).on("change", FinaBankApprTotal);

		function FinaPerCertiTotal() {
			var sum = 0;
			$(".FinaPerCerti").each(function() {
				sum += +$(this).val();
			});
			$(".FinaPerCertiTotal", this).val(sum);
		}

		$(document).ready(FinaPerCertiTotal);
		$(document).on("keyup", FinaPerCertiTotal);
		$(document).on("change", FinaPerCertiTotal);

		function FinancingCATotal() {
			var sum = 0;
			$(".FinancingCA").each(function() {
				sum += +$(this).val();
			});
			$(".FinancingCATotal", this).val(sum);
		}

		$(document).ready(FinancingCATotal);
		$(document).on("keyup", FinancingCATotal);
		$(document).on("change", FinancingCATotal);

		function FinaStaAuditTotal() {
			var sum = 0;
			$(".FinaStaAudit").each(function() {
				sum += +$(this).val();
			});
			$(".FinaStaAuditTotal", this).val(sum);
		}

		$(document).ready(FinaStaAuditTotal);
		$(document).on("keyup", FinaStaAuditTotal);
		$(document).on("change", FinaStaAuditTotal);
	</script>


	<script type="text/javascript">
		function AllYearTotalIntrest() {
			var sum = 0;
			$(".AllYearTotlIntrst").each(function() {
				sum += +$(this).val();
			});
			$(".totalofTI", this).val(sum);
		}

		$(document).ready(AllYearTotalIntrest);
		$(document).on("keyup", AllYearTotalIntrest);
		$(document).on("change", AllYearTotalIntrest);

		function AllYearTotalPrincipal() {
			var sum = 0;
			$(".AllYearTotlPrincipal").each(function() {
				sum += +$(this).val();
			});
			$(".totalofPrincipal", this).val(sum);
		}

		$(document).ready(AllYearTotalPrincipal);
		$(document).on("keyup", AllYearTotalPrincipal);
		$(document).on("change", AllYearTotalPrincipal);

		function AllYearTotalInterest() {
			var sum = 0;
			$(".AllYearTotlInterest").each(function() {
				sum += +$(this).val();
			});
			$(".totalofInterest", this).val(sum);
		}

		$(document).ready(AllYearTotalInterest);
		$(document).on("keyup", AllYearTotalInterest);
		$(document).on("change", AllYearTotalInterest);

		function AllYearTotalAmtSubsidy() {
			var sum = 0;
			$(".AllYearTotlAmtSubsidy").each(function() {
				sum += +$(this).val();
			});
			$(".totalofAmtSubsidy", this).val(sum);

		}

		$(document).ready(AllYearTotalAmtSubsidy);
		$(document).on("keyup", AllYearTotalAmtSubsidy);
		$(document).on("change", AllYearTotalAmtSubsidy);
	</script>

	<script>
		$(document).ready(function() {
			$("[data-toggle=offcanvas]").click(function() {
				$(".row-offcanvas").toggleClass("active");
			});
		});
		// For date-picker
		$(document).ready(function() {
			$('#datepicker1').datepicker({
				uiLibrary : 'bootstrap4'
			});
			$('#datepicker2').datepicker({
				uiLibrary : 'bootstrap4'
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
	<script type="text/javascript">
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
		$(document)
				.ready(
						function() {
							console.log("document loaded");
							if ("${disViewEvaluate.confProvbyCTDDocBase64}" == null
									|| "${disViewEvaluate.confProvbyCTDDocBase64}" == '') {

							} else {
								document
										.getElementById('chooseFileConfProvbyCTDDoc').innerHTML = '${disViewEvaluate.confProvbyCTDDoc}';
								document.getElementById("confProvbyCTDDoc").src = "data:image/png;base64,${disViewEvaluate.confProvbyCTDDocBase64}";
							}

							if ("${disViewEvaluate.confProvbyBankDocBase64}" == null
									|| "${disViewEvaluate.confProvbyBankDocBase64}" == '') {

							} else {
								document
										.getElementById('chooseFileConfProvbyBankDoc').innerHTML = '${disViewEvaluate.confProvbyBankDoc}';
								document.getElementById("confProvbyBankDoc").src = "data:image/png;base64,${disViewEvaluate.confProvbyBankDocBase64}";
							}

							if ("${disViewEvaluate.externalERDDocBase64}" == null
									|| "${disViewEvaluate.externalERDDocBase64}" == '') {

							} else {
								document
										.getElementById('choosefileExternalERDDoc').innerHTML = '${disViewEvaluate.externalERDDoc}';
								document.getElementById("externalERDDoc").src = "data:image/png;base64,${disViewEvaluate.externalERDDocBase64}";
							}
						});

		function DocumentconfProvbyCTDDoc(file) {
			var confProvbyCTDDoc = document.getElementById('confProvbyCTDDoc').value;
			var maxSize = '5000';

			if (confProvbyCTDDoc != null && confProvbyCTDDoc != '') {
				var ext = confProvbyCTDDoc.split('.').pop();
				if (ext == "pdf") {
					//document.getElementById('confProvbyCTDDocMsg').innerHTML = " ";
				} else {
					document.getElementById('confProvbyCTDDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
					document.getElementById('confProvbyCTDDoc').focus();
					return false;
				}
			}
			if (confProvbyCTDDoc != null && confProvbyCTDDoc != '') {
				var fileSize = document.getElementById("confProvbyCTDDoc").files[0];

				var fsize = (fileSize.size / (1024 * 1024)).toFixed(5);
				if (fsize > 5) {
					document.getElementById('confProvbyCTDDocMsg').innerHTML = "Your file size is: "
							+ fsize
							+ " MB,"
							+ " File size should not be more than 5 MB";
					document.getElementById('confProvbyCTDDoc').focus();
					return false;
				}

				else {
					document.getElementById('confProvbyCTDDocMsg').innerHTML = "";
				}

			}
			if ('${disViewEvaluate.confProvbyCTDDocBase64}' != '') {
				return true;
			}

		}
	</script>
</body>

</html>