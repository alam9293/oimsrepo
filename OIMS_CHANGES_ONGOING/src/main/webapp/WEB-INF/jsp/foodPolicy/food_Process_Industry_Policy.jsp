<%@page import="com.webapp.ims.model.ProprietorDetails"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>View List Application For LOC & Dis</title>

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

				<div class="col-md-9 col-lg-10 mt-4 main">
					<h4 class="card-title mb-4 mt-4 text-center">Food Processing
						Industry Policy-2017</h4>
					<div class="card card-block p-4 mb-5">
						<div id="accordion"
							class="accordion mt-3 mb-3 animate__animated animate__pulse">
							<div class="mb-0 border-0">
							<div class="card-header collapsed mb-4" data-toggle="collapse"
									href="#collapseDemo">
									<a class="card-title"> <strong>Applications for
											LOC - DEMO </strong>
									</a>
								</div>
							<div id="collapseDemo" class="card-body collapse"
									data-parent="#accordion">

									<div class="row">
										<div class="col-sm-9">
											<div class="form-group">
												<div class="search-box">
													<input type="text" class="form-control"
														placeholder="Search by Application ID/Policy Name/Business Entity Name/Investment/Category/Assign To/Status"
														name=""> <a href="javascript:void();"><i
														class="fa fa-search"></i></a>
												</div>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group text-right">
												<a href="javascript:void();" title="Excel Report"
													class="excel-icon"><img src="images/excel-icon.png"
													alt="Excel Icon"></a>
											</div>
										</div>
										<div class="col-sm-12">

											<div class="table-responsive mt-3">
												<table class="table table-stripped table-bordered">
													
														<thead>
															<tr>
																<th>S.No.</th>
																<th>Application ID</th>
																<th>Policy Name</th>
																<th>Business Entity Name</th>
																<th>Investment</th>
																<th>Region</th>
																<th>District</th>
																<th>Incentive Name</th>
																<th>Action</th>
																<th class="text-center" style="width: 17%;">Status</th>
															</tr>
													
													<tbody id="myTableDis">
															
															<tr>
																<td class="text-center">1</td>
																<td class="text-center"><a
																	href="./foodviewApplicationDetails?unit_Id=UPSWP20000192402" class="view-btn">UPSWP20000192402</a></td>
																<td><c:out value="" />Food Policy 2017</td>
																<td><c:out value="Dwarikesh Sugar Industries" /></td>
																<td><c:out value="500000000" /></td>
																<td><c:out value="Jhanshi" /></td>
																<td><c:out value="ANJAW" /></td>
																<td><c:out value="CS-FPI" /></td>
																<td><button type="button"
																		class="btn btn-outline-info btn-sm"
																		data-target="#food-Forward" data-toggle="modal">Forward</button></td>
																<td>Pending for Evaluation</td>
															</tr>
															<tr>
																<td class="text-center">2</td>
																<td class="text-center"><a
																	href="./foodviewApplicatPMKSY?unit_Id=UPSWP20000192402" class="view-btn">UPSWP20000192402</a></td>
																<td><c:out value="" />Food Policy 2017</td>
																<td><c:out value="Dwarikesh Sugar Industries" /></td>
																<td><c:out value="145450000" /></td>
																<td><c:out value="Lucknow" /></td>
																<td><c:out value="UNNAO" /></td>
																<td><c:out value="CS-FPI-PMKSY" /></td>
																<td><button type="button"
																		class="btn btn-outline-info btn-sm"
																		data-target="#food-Forward" data-toggle="modal">Forward</button></td>
																<td>Pending for Evaluation</td>
															</tr>
															<tr>
																<td class="text-center">3</td>
																<td class="text-center"><a
																	href="./foodProcessingFPI?unit_Id=UPSWP20000192402" class="view-btn">UPSWP20000192402</a></td>
																<td><c:out value="" />Food Policy 2017</td>
																<td><c:out value="Dwarikesh Sugar Industries" /></td>
																<td><c:out value="98500000" /></td>
																<td><c:out value="Lucknow" /></td>
																<td><c:out value="GAUTAM BUDH NAGAR	" /></td>
																<td><c:out value="IS-FPI" /></td>
																<td><button type="button"
																		class="btn btn-outline-info btn-sm"
																		data-target="#food-Forward" data-toggle="modal">Forward</button></td>
																<td>Pending for Evaluation</td>
															</tr>
															<tr>
																<td class="text-center">4</td>
																<td class="text-center"><a
																	href="./foodViewApplication_MFP_PMKSY?unit_Id=UPSWP20000192402" class="view-btn">UPSWP20000192402</a></td>
																<td><c:out value="" />Food Policy 2017</td>
																<td><c:out value="Dwarikesh Sugar Industries" /></td>
																<td><c:out value="875400000" /></td>
																<td><c:out value="Lucknow" /></td>
																<td><c:out value="Gorakhpur" /></td>
																<td><c:out value="CS-MFP-PMKSY" /></td>
																<td><button type="button"
																		class="btn btn-outline-info btn-sm"
																		data-target="#food-Forward" data-toggle="modal">Forward</button></td>
																<td>Pending for Evaluation</td>
															</tr>
															<tr>
																<td class="text-center">5</td>
																<td class="text-center"><a
																	href="./foodISReeferVehicles?unit_Id=UPSWP20000192402" class="view-btn">UPSWP20000192402</a></td>
																<td><c:out value="" />Food Policy 2017</td>
																<td><c:out value="Dwarikesh Sugar Industries" /></td>
																<td><c:out value="58700000" /></td>
																<td><c:out value="Lucknow" /></td>
																<td><c:out value="ANJAW" /></td>
																<td><c:out value="IS- Reefer Vehicles" /></td>
																<td><button type="button"
																		class="btn btn-outline-info btn-sm"
																		data-target="#food-Forward" data-toggle="modal">Forward</button></td>
																<td>Pending for Evaluation</td>
															</tr>
															<tr>
																<td class="text-center">6</td>
																<td class="text-center"><a
																	href="./foodProcessingTQM?unit_Id=UPSWP20000192402" class="view-btn">UPSWP20000192402</a></td>
																<td><c:out value="" />Food Policy 2017</td>
																<td><c:out value="Dwarikesh Sugar Industries" /></td>
																<td><c:out value="9870000" /></td>
																<td><c:out value="Jhanshi" /></td>
																<td><c:out value="GAUTAM BUDH NAGAR" /></td>
																<td><c:out value="TQM" /></td>
																<td><button type="button"
																		class="btn btn-outline-info btn-sm"
																		data-target="#food-Forward" data-toggle="modal">Forward</button></td>
																<td>Pending for Evaluation</td>
															</tr>
															<tr>
																<td class="text-center">7</td>
																<td class="text-center"><a
																	href="./foodProcessingMDBP?unit_Id=UPSWP20000192402" class="view-btn">UPSWP20000192402</a></td>
																<td><c:out value="" />Food Policy 2017</td>
																<td><c:out value="Dwarikesh Sugar Industries" /></td>
																<td><c:out value="500000000" /></td>
																<td><c:out value="Lucknow" /></td>
																<td><c:out value="ANJAW" /></td>
																<td><c:out value="MDBP" /></td>
																<td><button type="button"
																		class="btn btn-outline-info btn-sm"
																		data-target="#food-Forward" data-toggle="modal">Forward</button></td>
																<td>Pending for Evaluation</td>
															</tr>
															<tr>
																<td class="text-center">8</td>
																<td class="text-center"><a
																	href="./foodProcessingBankDPR?unit_Id=UPSWP20000192402" class="view-btn">UPSWP20000192402</a></td>
																<td><c:out value="" />Food Policy 2017</td>
																<td><c:out value="Dwarikesh Sugar Industries" /></td>
																<td><c:out value="500000000" /></td>
																<td><c:out value="Jhanshi" /></td>
																<td><c:out value="GAUTAM BUDH NAGAR" /></td>
																<td><c:out value="Bank DPR" /></td>
																<td><button type="button"
																		class="btn btn-outline-info btn-sm"
																		data-target="#food-Forward" data-toggle="modal">Forward</button></td>
																<td>Pending for Evaluation</td>
															</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									href="#collapseOne" >
									<a class="card-title"> <strong>Applications for
											LoC</strong>
									</a>
								</div>
								<div id="collapseOne" class="card-body collapse"
									data-parent="#accordion" >

									<div class="row">
										<div class="col-sm-9">
											<div class="form-group">
												<div class="search-box">
													<input type="text" class="form-control"
														placeholder="Search by Application ID/Policy Name/Business Entity Name/Investment/Category/Assign To/Status"
														name=""> <a href="javascript:void();"><i
														class="fa fa-search"></i></a>
												</div>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group text-right">
												<a href="javascript:void();" title="Excel Report"
													class="excel-icon"><img src="images/excel-icon.png"
													alt="Excel Icon"></a>
											</div>
										</div>
										<div class="col-sm-12">

											<div class="table-responsive mt-3">
												<table class="table table-stripped table-bordered">
													
														<thead>
															<tr>
																<th>S.No.</th>
																<th>Application ID</th>
																<th>Policy Name</th>
																<th>Business Entity Name</th>
																<th>Investment</th>
																<th>Region</th>
																<th>District</th>
																<th>Incentive Name</th>
																<th>Action</th>
																<th class="text-center" style="width: 17%;">Status</th>
															</tr>
													
													<tbody id="myTableDis">
													
														<c:forEach var="list" items="${foddPolicyList}"
															varStatus="counter">
															
															<tr>
																<td class="text-center">${counter.index+1}</td>
																<td class="text-center"><a
																	href="./foodviewApplicationDetails?unit_Id=${list.unit_id}" class="view-btn">${list.unit_id }</a></td>
																<td><c:out value="" /></td>
																<td><c:out value="${list.nameofcompany}" /></td>
																<td><c:out value="" /></td>
																<td><c:out value="${list.division}" /></td>
																<td><c:out value="${list.district}" /></td>
																<td></td>
																<td><button type="button"
																		class="btn btn-outline-info btn-sm"
																		data-target="#food-Forward" data-toggle="modal">Forward</button></td>
																<td>Pending for Evaluation</td>
																													
															</tr>
															</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
								
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseTwo">
									<a class="card-title"> <strong>Applications for
											Disbursement</strong>
									</a>
								</div>
								<div id="collapseTwo" class="card-body collapse"
									data-parent="#accordion">

									<div class="row">
										<div class="col-sm-9">
											<div class="form-group">
												<div class="search-box">
													<input type="text" class="form-control"
														placeholder="Search by Application ID/Policy Name/Business Entity Name/Investment/Category/Assign To/Status"
														name=""> <a href="javascript:void();"><i
														class="fa fa-search"></i></a>
												</div>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group text-right">
												<a href="javascript:void();" title="Excel Report"
													class="excel-icon"><img src="images/excel-icon.png"
													alt="Excel Icon"></a>
											</div>
										</div>
										<div class="col-sm-12">

											<div class="table-responsive mt-3">
												<table class="table table-stripped table-bordered">
													<thead>
														<tr>
															<th>S.No.</th>
															<th>Application ID</th>
															<th>Policy Name</th>
															<th>Business Entity Name</th>
															<th>Investment</th>
															<th>Region</th>
															<th>District</th>
															<th>Incentive Name</th>
															<th>Action</th>
															<th class="text-center" style="width: 17%;">Status</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td></td>
															<td class="text-center"><a
																href="food-Dis-view-all-application-details.html"
																class="view-btn"></a></td>
															<td> </td>
															<td></td>
															<td></td>
															<td></td>
															<td></td>
															<td></td>
															<td><button type="button"
																	class="btn btn-outline-info btn-sm"
																	data-target="#food-Forward" data-toggle="modal"></button></td>
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
		<div class="modal fade" id="food-Forward">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Select Incentives Specific Processing
							Officer</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-12" style="height: 400px; overflow-y: scroll;">
								<!-- gopal  -->
								<div class="custom-control custom-checkbox">
									<input id="food1" name="deptName" class="custom-control-input"
										type="checkbox" value="adappup2018@gmail.com"><input
										type="hidden" name="_deptName" value="on"> <label
										class="custom-control-label" for="">Capital Subsidy
										for settingup new units/ Technology Up gradation/
										Modernisation -Processing Officer</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input id="" name="deptName" class="custom-control-input"
										type="checkbox" value="borlko@up.nic.in"><input
										type="hidden" name="_deptName" value="on"> <label
										class="custom-control-label" for="">Capital Subsidy
										for settingup new units/Technology Up gradation /
										Modernisation Sanctioned under Pradhan Mantri Kishan Sampada
										Yojna.- processing Officer</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input id="" name="deptName" class="custom-control-input"
										type="checkbox" value="Puneet.tripathi@gov.in"><input
										type="hidden" name="_deptName" value="on"> <label
										class="custom-control-label" for="">Interest Subsidy
										for settingup new units/Technology Up
										gradation/Modernisation-Processing Officer</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input id="" name="deptName" class="custom-control-input"
										type="checkbox" value="taskupexcise01@gmail.com"><input
										type="hidden" name="_deptName" value="on"> <label
										class="custom-control-label" for="">Capital Subsidy
										for Mega Food Park/Agro Processing Cluster Sanctioned by GOI
										under Pradhan Mantri Kishan Sampadda Yojana. - Processing
										Officer</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input id="" name="deptName" class="custom-control-input"
										type="checkbox" value="hq.sankhya@gmail.com"><input
										type="hidden" name="_deptName" value="on"> <label
										class="custom-control-label" for="">Interest Subsidy
										for Reefer Vehicles/Mobile Units. -Processing Officer</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input id="" name="deptName" class="custom-control-input"
										type="checkbox" value="igruplko@gmail.com"><input
										type="hidden" name="_deptName" value="on"> <label
										class="custom-control-label" for="">Application for
										seeking assistance for implementation of Total Quality
										Management System (TQM) including ISO 14000, ISO 22000, HACCP,
										GHP & GMP / Patent - Processing Officer.</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input id="" name="deptName" class="custom-control-input"
										type="checkbox" value="dclmhqlko@gmail.com"><input
										type="hidden" name="_deptName" value="on"> <label
										class="custom-control-label" for="">Assistance for
										Market development & brand promotion. -Processing Officer</label>
								</div>
								<div class="custom-control custom-checkbox">
									<input id="" name="deptName" class="custom-control-input"
										type="checkbox" value="electricalsafety.upgovt@gmail.com"><input
										type="hidden" name="_deptName" value="on"> <label
										class="custom-control-label"
										for="Directorate of Electrical Safety">Assistance for
										Preparation of Bankable Detailed Project Report (DPR).
										-Processing Officer</label>
								</div>





								<!--  gopal -->
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
</body>
</html>