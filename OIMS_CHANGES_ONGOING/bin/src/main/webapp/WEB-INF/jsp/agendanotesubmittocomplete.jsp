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
	function completeAgendaNoteValidation() {
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
													<th>Type </th>
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
															formaction="./viewApprovedAgendaDetails?appliId=${list.appliId}"
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
								<hr class="mt-2">
								<c:import url="/WEB-INF/jsp/agenda_note_evaluation_comon_view.jsp">
								</c:import>
							</div>
						</div>	
						
						<div class="row" id="showHide2">
							<div class="col-sm-12">
								
								<hr class="mt-2 mb-5">
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<label>Note <small>(If Any)</small></label>
											<textarea class="form-control" disabled="disabled" rows="4">${prepAgenNotes}</textarea>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>PICUP Recommendations<small></small></label>
											<textarea class="form-control" disabled="disabled" rows="4">${scrtnyComment}</textarea>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>JMD Recommendation<small></small></label>
											<textarea class="form-control" disabled="disabled" rows="4">${JMDComment}</textarea>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>MD PICUP Recommendation<small></small></label>
											<textarea class="form-control" disabled="disabled" rows="4">${MDComment}</textarea>
										</div>
									</div>
									<hr>
									<form:form modelAttribute="PrepareAgendaNotes"
										action="approvedAgendaNote" class="mt-4"
										name="PrepareAgendaNotes" method="POST">
										<form:hidden path="prepareAgendaNote" id="prepareAgendaNote"></form:hidden>
										<div class="row mb-3">
											<div class="col-sm-5">
												<a href="./agendaNode" class="common-default-btn mt-3">Back</a>
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
									<label>Name of Nodal Deptt</label> <input type="text"
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
									<label>Upload Document</label>
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