<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Minutes of Meeting and GO's</title>
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
						<li class="nav-item"><a class="nav-link" href="./userLogout2"><i
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
					  <c:if test="${not empty Tblformactiondsplname}">
		                  <c:forEach var="Map" items="${Tblformactiondsplname}" varStatus="counter">
		                           <c:set var="dsplname" value="${fn:toUpperCase(Map.value)}" />
		                           <c:choose>
			                           <c:when test = "${fn:contains(dsplname, 'DASHBOARD')}">
					                       <li class="nav-item animate__animated animate__bounce">
						                       <a class="nav-link active" href="${Map.key}">
						                         <i class="fa fa-tachometer"></i>${dsplname}
						                       </a>
					                       </li>
					                   </c:when> 
					                    <c:when test = "${fn:contains(dsplname, 'VIEW')}">
					                       <li class="nav-item">
						                       <a class="nav-link" href="${Map.key}">
						                         <i class="fa fa-eye"></i>${dsplname}
						                       </a>
					                       </li>
					                   </c:when> 
					                   <c:when test = "${fn:contains(dsplname, 'MEETING')}">
					                       <li class="nav-item">
						                       <a class="nav-link" href="${Map.key}">
						                         <i class="fa fa-calendar"></i>${dsplname}
						                       </a>
					                       </li>
					                   </c:when> 
					                   <c:otherwise>
								           <li class="nav-item animate__animated animate__bounce">
						                       <a class="nav-link active" href="${Map.key}">
						                         <i class="fa fa-tachometer"></i>${dsplname}
						                       </a>
					                       </li>
								       </c:otherwise>
				                   </c:choose>
			                      </c:forEach>
		                </c:if>
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
											class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Minutes
											of Meeting and GO's</h4>
									</div>
								</div>
								
								
								<div class="card card-block p-3">

						<div class="row">
							<div class="col-sm-12">
								<div class="step-links">
									<ul class="only-steps">
										<li><a href="./nodalmomgo" class="active pr-3">MOM for LOC</a></li>
										<li><a href="./nodalmomgoDis" class=" pr-3">MOM for
												Disbursement </a></li>
									</ul>
								</div>
								
								<hr class="mt-2 mb-3">
								<div class="row">
									<div class="col-sm-12 text-right">
										<div class="form-group">
											<!--    <button type="button" class="btn btn-outline-info btn-sm" data-target="#UploadMOM" data-toggle="modal">Upload Minutes of Meeting</button> -->
										</div>
									</div>
								</div>
								<div id="accordion"
									class="accordion mt-3 mb-3 animate__animated animate__pulse">
									<div class="mb-0 border-0">
										<div class="card-header collapsed mb-4" data-toggle="collapse"
											href="#collapseOne">
											<a class="card-title"> <strong>Minutes of
													Meeting for Empowered Committee</strong>
											</a>
										</div>
										<div id="collapseOne" class="card-body collapse" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-bordered">
                                      <thead>
                                        <tr>
                                          <th>S.No</th>
                                          <th>Committee Name</th>
                                          <th>Date</th>
                                          <th>View Document</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                     <c:forEach var="list" items="${minutesOfMeetingEmpowerCommitList}" varStatus="counter">
                                       <tr > 
                                       <td>${counter.index+1}</td>                                 
                                       <td>${list.committeeName}</td>                                       
                                       <td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${list.dateOfMeeting}" /></td>                                  
                                        <td><a href="./downloadDocMom?fileNameMomId=${list.id}"><small>${list.uploadMomFile}</small></a></td>                                 
                                       </tr>
                                   </c:forEach>  
                                      </tbody>
                                        </table>
                                      </div>
                                  </div>
                                  <!-- <div class="col-md-12">
                                  <ul class="pagination pull-right">
                                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                                  </ul>
                                </div> -->
                              </div>
                            
										</div>
										<div class="card-header collapsed mb-4" data-toggle="collapse"
											data-parent="#accordion" href="#collapseTwo">
											<a class="card-title"> <strong>Minutes of
													Meeting for Sanction Committee</strong>
											</a>
										</div>
										 <div id="collapseTwo" class="card-body collapse" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-bordered">
                                      <thead>
                                        <tr>
                                          <th>S.No</th>
                                          <th>Committee Name</th>
                                          <th>Date</th>
                                          <th>View Document</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                       <c:forEach var="list" items="${minutesOfMeetingSactionCommitList}" varStatus="counter">
                                       <tr > 
                                       <td>${counter.index+1}</td>                                 
                                       <td>${list.committeeName}</td>                                       
                                       <td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${list.dateOfMeeting}" /></td>                                  
                                        <td><a href="./downloadDocMom?fileNameMomId=${list.id}"><small>${list.uploadMomFile}</small></a></td>
                                                                         
                                       </tr>
                                      </c:forEach>  
                                      </tbody>
                                        </table>
                                      </div>
                                  </div>
                                 <!--  <div class="col-md-12">
                                  <ul class="pagination pull-right">
                                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                                  </ul>
                                </div> -->
                              </div>

                            

										</div>

										<div class="card-header collapsed mb-4" data-toggle="collapse"
											data-parent="#accordion" href="#collapseThree">
											<a class="card-title"> <strong>GO's</strong>
											</a>
										</div>
										<div id="collapseThree" class="card-body collapse"
											data-parent="#accordion">

											<div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-bordered">
                                      <thead>
                                        <tr>
                                          <th>S.No</th>
                                          <th>G.O. No. & Date</th>
                              
                                          <th>View Document</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <c:forEach var="list" items="${minutesOfMeetingGosCommitList}" varStatus="counter">
                                       <tr > 
                                       <td>${counter.index+1}</td>                                 
                                       <td>${list.gosNo}</td>                                       
                                                                      
                                        <td><a href="./downloadDocMom?fileNameMomId=${list.id}"><small>${list.uploadGosFile}</small></a></td>
                                                                         
                                       </tr>
                                      </c:forEach>
                                      </tbody>
                                        </table>
                                      </div>
                                  </div>
                                 <!--  <div class="col-md-12">
                                  <ul class="pagination pull-right">
                                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                                  </ul>
                                </div> -->
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
		<!-- The Modal -->
		<div class="modal fade" id="UploadMOM">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Upload MOM/Go's</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>Upload for</label> <select class="form-control"
										id="SelectParameter">
										<option value="0">Select One</option>
										<option value="MinutesofMeeting">Minutes of Meeting</option>
										<option value="GOs">GO's</option>
									</select>
								</div>
							</div>
						</div>
						<div class="row parameter-properties" id="MinutesofMeeting">
							<div class="col-md-12">
								<div class="form-group">
									<label>Committee Name</label> <input type="text"
										class="form-control" name="">
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label>Date of Meeting</label> <input type="date"
										class="form-control" name="">
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label>Upload MOM File</label>
									<div class="custom-file">
										<input type="file" class="custom-file-input" id="UploadMom1">
										<label class="custom-file-label" for="UploadMom1">Choose
											File</label>
									</div>
								</div>
							</div>
						</div>
						<div class="row parameter-properties" id="GOs">
							<div class="col-md-12">
								<div class="form-group">
									<label>GO's Name</label> <input type="text"
										class="form-control" name="">
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label>GO's Date</label> <input type="date"
										class="form-control" name="">
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label>Upload GO's File</label>
									<div class="custom-file">
										<input type="file" class="custom-file-input" id="GOFile">
										<label class="custom-file-label" for="GOFile">Choose
											File</label>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<a href="#AddNote" class="common-btn mt-0" data-toggle="modal"
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