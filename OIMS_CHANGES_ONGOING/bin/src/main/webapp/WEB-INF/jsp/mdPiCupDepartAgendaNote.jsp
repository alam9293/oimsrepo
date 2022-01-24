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
<title>Prepare Agenda Note</title>

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
</head>
<body>
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
							href="./dashboardMDPICUP"><i class="fa fa-tachometer"></i>
								Dashboard</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./selectPolicyMDPICUP"><i class="fa fa-eye"></i> View
								Applications</a></li>
						<li class="nav-item"><a class="nav-link "
							href="./viewSMEMDPICUPApplications"><i class="fa fa-eye"></i>
								View SME Applications</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./mdPiCupAgendaNote"><i class="fa fa-list"></i> Agenda
								Note</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./momgobyMDPICUP"><i class="fa fa-calendar"></i> View
								Minutes of Meeting and GO's</a></li>
					</ul>
				</div>
				<!--/col-->

				<div class="col-md-9 col-lg-10 mt-4 main">
					<h4 class="card-title mb-4 mt-4 text-center">Agenda Note</h4>
					<div class="card card-block p-4 mb-5 prepare-agenda-note-tabs">






						<div class="step-links">
							<ul class="only-steps">
								<li><a href="./mdPiCupAgendaNote" class=" active pr-3">Prepare
										Agenda Note for LOC</a></li>
								<li><a href="./mdPiCupAgendaNoteDis" class=" pr-3">Prepare
										Agenda Note for Disbursement </a></li>
							</ul>
						</div>





						<div id="accordion"
							class="accordion mt-3 mb-3 animate__animated animate__pulse">
							<div class="mb-0 border-0">
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseThree">
									<a class="card-title"> <strong>Agenda Note for
											Empowered Committee</strong>
									</a>
								</div>
								<form:form modelAttribute="PrepareAgendaNotes"
									action="prepareAgendaNote" class="mt-4"
									name="PrepareAgendaNotes" method="post"
									onsubmit="return prepareAgendaNoteValidation()">
									<div id="collapseThree" class="card-body collapse"
										data-parent="#accordion">

										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered" id="table1">
														<thead>
															<tr>
																<!-- <th></th> -->
																<th>S.No</th>
																<th>Application ID</th>
																<th>Submission Date</th>
																<th>Action</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="list"
																items="${prepareAgendaNotesMegaMdPiCupLists}"
																varStatus="counter">
																<tr>
																	<%-- <td><input type="checkbox"
																		name="prepareAgendaNote" id="prepareAgendaNoteId"
																		value="${list.appliId}"></td> --%>
																	<td class="text-center">${counter.index+1}</td>
																	<td>${list.appliId}</td>
																	<td>${list.submissionDate}</td>
																	<td><a
																		href="./viewAgendaDetailsByMdPiCupDepart?applicantId=${list.appliId}"
																		class="btn btn-outline-info btn-sm">View</a> <a
																		href="#commentNote"
																		class="btn btn-outline-success btn-sm"
																		data-toggle="modal" id="${list.appliId}"
																		data-target="#edit-modal">Comment</a> <a
																		href="./saveForwardedToACSIndustry?applicantId=${list.appliId}"
																		class="btn btn-outline-success btn-sm"
																		onclick="return ApprovedAgendaNoteValidation();">Forward
																			to ACS Industry</a></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</form:form>
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseFive">
									<a class="card-title"> <strong>Agenda Note for
											Sanction Committee</strong>
									</a>
								</div>
								<form:form modelAttribute="PrepareAgendaNotes"
									action="prepareAgendaNote" class="mt-4"
									name="PrepareAgendaNotes" method="post"
									onsubmit="return prepareAgendaNoteValidation()">
									<div id="collapseFive" class="card-body collapse"
										data-parent="#accordion">
										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered">
														<thead>
															<tr>
																<!-- <th></th> -->
																<th>S.No</th>
																<th>Application ID</th>
																<th>Submission Date</th>
																<th>Action</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="list"
																items="${prearepAgendaNotesLargeMdPiCupLists}"
																varStatus="counter">
																<tr>
																	<%-- <td><input type="checkbox"
																		name="prepareAgendaNote" id="prepareAgendaNoteId"
																		value="${list.appliId}"></td> --%>
																	<td class="text-center">${counter.index+1}</td>
																	<td>${list.appliId}</td>
																	<td>${list.submissionDate}</td>
																	<td><a
																		href="./viewAgendaDetailsByMdPiCupLargeDepart?applicantId=${list.appliId}"
																		class="btn btn-outline-info btn-sm">View</a> <a
																		href="#commentNote"
																		class="btn btn-outline-success btn-sm"
																		data-toggle="modal" id="${list.appliId}"
																		data-target="#edit-modal">Comment</a> <a
																		href="./saveLargeAndApproved?applicantId=${list.appliId}"
																		class="btn btn-outline-success btn-sm"
																		onclick="return ApprovedAgendaNoteValidation();">Approve</a></td>
																</tr>
																<!-- <span >${signaturekeyMsg}</span> 
																<div>
																	<c:if test="${not empty signaturekeyMsg}">
																	alert(<font color="red">${signaturekeyMsg}</font>);
																	</c:if>
																</div>-->


															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</form:form>



								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseMDPICUP">
									<a class="card-title"> <strong>View MD PICUP
											Comments</strong>
									</a>
								</div>


								<div id="collapseMDPICUP" class="card-body collapse"
									data-parent="#accordion">

									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-bordered">
													<thead>
														<tr>
															<th style="width: 15%;" class="text-center">Application
																ID</th>
															<th>Comment</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="list"
															items="${prepareAgendaNotesListComment}"
															varStatus="counter">
															<tr>
																<td class="text-center align-middle">${list.appliId}</td>
																<td><textarea class="form-control"
																		disabled="disabled">${list.mdScrutinyDetail}</textarea></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>

								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseSeven">
									<a class="card-title"> <strong>Comment From ACS /
											Head of Nodal</strong>
									</a>
								</div>
								<div id="collapseSeven" class="card-body collapse"
									data-parent="#accordion">

									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-bordered">
													<thead>
														<tr>
															<th style="width: 15%;" class="text-center">Application
																ID</th>
															<th>Comment</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="list"
															items="${prepareAgendaNotesAcsComments}"
															varStatus="counter">
															<tr>
																<td class="text-center align-middle">${list.appliId}</td>
																<td><textarea class="form-control"
																		disabled="disabled">${list.acsScrutinyDetail}</textarea></td>
															</tr>
														</c:forEach>
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
		<!-- <div class="modal fade" id="AddNote">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">

            Modal Header
            <div class="modal-header">
              <h4 class="modal-title">Add Your Note</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            Modal body
            <div class="modal-body">
              <div class="row">
                <div class="col-sm-12">
                  <div class="form-group">
                    <textarea class="form-control" rows="6"></textarea>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a href="#AddNote" class="common-btn mt-0" data-toggle="modal" data-dismiss="modal">Submit</a>
            </div>
          </div>
        </div>
      </div> -->


		<!-- The Modal -->
		<div class="modal fade" id="edit-modal">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">MDPICUP Recommendation</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<form:form modelAttribute="PrepareAgendaNotes"
						action="saveCommenSubByMdPicupDepartment" class="mt-4"
						name="PrepareAgendaNotes" method="post">
						<!-- Modal body -->
						<div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<form:textarea path="mdScrutinyDetail" class="form-control"
											placeholder="Enter Your Comment" rows="4"
											name="mdScrutinyDetail" maxlength="500"></form:textarea>
									</div>
									<form:hidden path="appliId" id="appliId"></form:hidden>
									<form:hidden path="id" id="id"></form:hidden>
									<form:hidden path="companyName" id="companyName"></form:hidden>
									<form:hidden path="investment" id="investment"></form:hidden>
								</div>
							</div>
						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-info">Submit</button>
						</div>
					</form:form>
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
		function ApprovedAgendaNoteValidation() {
			var r = confirm("Are you sure you want to Forward the Application?");

			if (r == true) {
				alert("Application Forwarded Successfully");
			} else {
				return false
			}

			var sign = '${signaturekeyMsg}';
			if (sign != null && sign != '') {
				alert("Please Insert USB Digital key.");
				return false;
			} else {
				return true;
			}
		}
	</script>
	<script>
		$(document).ready(function() {
			$("[data-toggle=offcanvas]").click(function() {
				$(".row-offcanvas").toggleClass("active");
			});

			$('#edit-modal').on('show.bs.modal', function(e) {

				var $modal = $(this), esseyId = e.relatedTarget.id;
				document.getElementById("appliId").value = e.relatedTarget.id;
			})
		});
	</script>
</body>
</html>