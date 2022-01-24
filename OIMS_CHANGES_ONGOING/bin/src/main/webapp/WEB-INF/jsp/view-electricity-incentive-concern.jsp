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
<title>View Incentive Application Department</title>

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
						<li class="nav-item"><a class="nav-link"
							href="./electricityDashboard"><i class="fa fa-tachometer"></i>
								Dashboard</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./viewElectricityDeptApplication"><i class="fa fa-eye"></i> View
								Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./circulateElectricityViewAgendaNote"><i class="fa fa-eye"></i>
								View Circulated Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewLocElectricity"><i class="fa fa-eye"></i>
								View Circulated Draft LOC</a></li>
					</ul>
				</div>
				<!--/col-->

				<div class="col-md-9 col-lg-10 mt-4 main">
					<h4
						class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Industrial
						Investment & Employment Promotion Policy 2017</h4>
					<div class="card card-block p-4 mb-5">
						<div id="accordion"
							class="accordion mt-3 mb-3 animate__animated animate__pulse">
							<div class="mb-0 border-0">
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									href="#collapseOne">
									<a class="card-title"> <strong>Applications for
											LoC</strong>
									</a>
								</div>
								<div id="collapseOne" class="card-body collapse"
									data-parent="#accordion">

									<div class="row">
										<div class="col-sm-9">
											<div class="form-group">
												<div class="search-box">
													<input type="text" class="form-control" id="myInput"
														placeholder="Search by Application ID/Policy Name/Business Entity Name/Investment/Category/Status"
														name=""> <a href="javascript:void();"><i
														class="fa fa-search"></i></a>
												</div>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group text-right">
												<a href="javascript:void();" title="Excel Report"
													class="excel-icon"><img src="images/excel-icon.png"
													alt="Excel Icon" onclick="generateExcel();"></a>
											</div>
										</div>
										<div class="col-sm-12">

											<div class="table-responsive mt-3">
												<table class="table table-stripped table-bordered" id="generateExcel">
													<c:if test="${ not empty investmentDetailsList}">
														<thead>
															<tr>
																<th>S.No.</th>
																<th>Application ID</th>
																<th>Policy Name</th>
																<th>Business Entity Name</th>
																<th>Investment</th>
																<th>Category</th>
																<th>Region</th>
																<th>District</th>
															</tr>
														</thead>
													</c:if>
												<tbody id="myTable">
														<c:forEach var="list" items="${investmentDetailsList}"
															varStatus="counter">
															<tr>
																<td class="text-center">${counter.index+1}</td>
																<td class="text-center"><a
																	href="./viewDeptApplicationDetails?applicantId=${list[0]}"
																	class="view-btn">${list[0]}</a></td>
																<td>Industrial Policy</td>
																<td><c:out value="${list[1]}" /></td>
																<td><c:out value="${list[2]}" /></td>
																<td><c:out value="${list[3]}" /></td>
																<td><c:out value="${list[4]}" /></td>
																<td><c:out value="${list[5]}" /></td>
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
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-stripped table-bordered" id="generateExcel">
													
														<thead>
															<tr>
																<th>S.No.</th>
																<th>Application ID</th>
																<th>Policy Name</th>
																<th>Business Entity Name</th>
																<th>Investment</th>
																<th>Category</th>
																<th>Region</th>
																<th>District</th>
															</tr>
														</thead>
												
												<tbody id="myTable">
														<c:forEach var="list" items="${investmentDetailsListdis}"
															varStatus="counter">
															<tr>
																<td class="text-center">${counter.index+1}</td>
																<td class="text-center"><a
																	href="./viewApplicationDetailsDisConcern?applicantId=${list[0]}"
																	class="view-btn">${list[0]}</a></td>
																<td>Industrial Policy</td>
																<td><c:out value="${list[1]}" /></td>
																<td><c:out value="${list[2]}" /></td>
																<td><c:out value="${list[3]}" /></td>
																<td><c:out value="${list[4]}" /></td>
																<td><c:out value="${list[5]}" /></td>
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


	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<script src="js/custom.js"></script>
 
<script src= 
"//cdn.rawgit.com/rainabba/jquery-table2excel/1.1.0/dist/jquery.table2excel.min.js"> 
</script> 

	<script>
		$(document).ready(function() {
			$("[data-toggle=offcanvas]").click(function() {
				$(".row-offcanvas").toggleClass("active");
			});
		});
		function generateExcel()
		{

			 $("#generateExcel").table2excel({ 
			        filename: "APPLICATIONS FOR LOC.xls" 
			    }); 
      

			}
	</script>
</body>
</html>