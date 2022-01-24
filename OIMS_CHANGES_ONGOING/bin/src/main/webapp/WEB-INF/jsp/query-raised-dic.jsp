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
<title>Query Raised</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
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
						<li class="nav-item animate__animated animate__bounce"><a
							class="nav-link" href="./dicDepartmentDashboard"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewDICApplication"><i class="fa fa-eye"></i> View
								SME applications</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./queryRaisedDIC"><i class="fa fa-question-circle"></i>
								Query Raised</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewQueryResponseDIC"><i
								class="fa fa-question-circle"></i> Query Response By
								Entrepreneur</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./prepareAgendaNoteDicList"><i class="fa fa-list"></i>
								Prepare Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link" href="#"><i
								class="fa fa-calendar"></i> Schedule meeting</a></li>
						<li class="nav-item"><a class="nav-link" href="#"><i
								class="fa fa-calendar"></i> Minutes of Meeting and GO's</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./dicgenerateLoc"><i class="fa fa-wpforms"></i>
								Generate LOC</a></li>
					</ul>
				</div>
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-12">
								<div class="mt-4">
									<div class="without-wizard-steps">
										<h4
											class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Query
											Raised</h4>
									</div>
								</div>
								<hr class="mt-2 mb-3">
								<div class="row">
									<div class="col-md-12 main">
										<div class="table-responsive mt-3" id="PackageTable">
											<table class="table table-stripped table-bordered">
												<thead>
													<tr>
														<th>S.No.</th>
														<th>Application ID</th>
														<th class="text-center">View Query</th>
													</tr>
												</thead>
												<%
													int i = 0;
												%>
												<tbody>
													<c:forEach var="applicationId" items="${appIdList}">
														<c:if test="${not empty appIdList}">
															<tr>
																<td><%=++i%></td>
																<td>${applicationId}</td>
																<td class="text-center"><a href="#ViewRaiseQuery"
																	class="view-btn" data-toggle="modal"
																	id='${applicationId}' data-target="#ViewRaiseQuery"><i
																		class="fa fa-eye"></i> View Query</a></td>
															</tr>
														</c:if>
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
		<div class="modal fade" id="ViewRaiseQuery">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">View Raised Query</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>Clarification Sought</label>
									<textarea class="form-control" class="ClarificationSought"
										id="clarificationsought" disabled="disabled"></textarea>

								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label>Details of Missing Documents</label>
									<textarea class="form-control" id="detailsofmissingdocuments"
										disabled="disabled"></textarea>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label>Upload Related Document</label> <a
										href="javascript:void();" class="d-block"
										id="uploadrelateddocument" disabled="disabled"><small></small></a>
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

	<script>
		$('#ViewRaiseQuery')
				.on(
						'show.bs.modal',
						function(e) {
							var $modal = $(this), esseyId = e.relatedTarget.id;
							<c:forEach var="raiseQuery" items="${raiseQueryList}">
							if (esseyId == '${raiseQuery.rqApplId}') {
								document.getElementById("clarificationsought").value = '${raiseQuery.rqClarifySought}';
								document
										.getElementById("detailsofmissingdocuments").value = '${raiseQuery.rqMissdocdtl}';
								document
										.getElementById("uploadrelateddocument").text = '${raiseQuery.rqFilename}';
							}

							</c:forEach>
						});
	</script>


</body>

</html>