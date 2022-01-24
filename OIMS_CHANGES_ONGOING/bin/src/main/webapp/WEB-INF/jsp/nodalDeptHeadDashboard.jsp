<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Dashboard Head Department</title>

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
						<li class="nav-item"><a class="nav-link active"
							href="./nodalDeptHeadDashboard"><i class="fa fa-tachometer"></i>
								Dashboard</a></li>
						<li class="nav-item"><a class="nav-link"
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

				<div class="col-md-9 col-lg-10 mt-4 main">

					<h4 class="card-title mb-4 animate__animated animate__fadeInDown">LOC
						Stage</h4>

					<div class="row mb-5">
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels received-tiels"> <label>${countAllApplication}<i
										class="fa fa-get-pocket pull-right"></i>
								</label>
									<hr> <span>Total Number of Applications Received</span>
								</a>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels issued-tiels"> <label>${countCompleteLoc}<i
										class="fa fa-clipboard pull-right"></i></label>
									<hr> <span>Number of LOC Issued</span>
								</a>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels pendings-tiels"> <label>${countDeferLoc}
										<i class="fa fa-clock-o pull-right"></i>
								</label>
									<hr> <span>Number of Applications Deferred</span>
								</a>
							</div>
						</div>
					</div>
					<div class="row mb-5">
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels rejected-tiels"> <label>${countRejectLoc}
										<i class="fa fa-window-close pull-right"></i>
								</label>
									<hr> <span>Number of Applications Rejected</span>
								</a>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels raised-tiels"> <label>${countQueryLoc}
										<i class="fa fa-question-circle pull-right"></i>
								</label>
									<hr> <span>Number of Applications on Which Query
										has been Raised</span>
								</a>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels evaluation-tiels"> <label>${countReadyLoc}
										<i class="fa fa-sliders pull-right"></i>
								</label>
									<hr> <span>Number of Applications Under Evaluation</span>
								</a>
							</div>
						</div>
					</div>


					<div class="row mb-5">
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels ready-tiels"> <label>${countPreparedLoc }
										<i class="fa fa-thumbs-up pull-right"></i>
								</label>
									<hr> <span>Number of Applications Ready for
										Sanctioning/Empowered Committee</span>
								</a>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels agenda-tiels"> <label>${countApprovalLoc}
										<i class="fa fa-clipboard pull-right"></i>
								</label>
									<hr> <span>Number of Applications Pending for
										Approval of Hon'ble Cabinet</span>
								</a>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels review-tiels"> <label>${ApprovedByCommitee}
										<i class="fa fa-check pull-right"></i>
								</label>
									<hr> <span>Number of Applications Approved by
										Sanctioning Committee/Hon'ble Cabinet and Pending for Issuance
										of LOC</span>
								</a>
							</div>
						</div>
					</div>

					<h4 class="card-title mb-4 animate__animated animate__fadeInDown">Disbursement
						Stage</h4>

					<div class="row mb-5">
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels issued-tiels"> <label>2903 <i
										class="fa fa-check-square pull-right"></i></label>
									<hr> <span>Number of Disbursement Approved</span>
								</a>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels pendings-tiels"> <label>343
										<i class="fa fa-clock-o pull-right"></i>
								</label>
									<hr> <span>Number of Disbursement Pending</span>
								</a>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="card card-block p-4 h-100">
								<a href="javascript:void(0);"
									class="dashboard-tiels rejected-tiels"> <label>212
										<i class="fa fa-window-close pull-right"></i>
								</label>
									<hr> <span>Number of Disbursement Rejected</span>
								</a>
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
</body>
</html>