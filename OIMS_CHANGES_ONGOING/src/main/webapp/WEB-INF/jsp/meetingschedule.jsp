<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="com.webapp.ims.model.ExistingProjectDetails"
	pageEncoding="ISO-8859-1"%>
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
<title>Meeting Schedule | LOC</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="css/time-picker.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<script type="text/javascript">
	jQuery(document).ready(
			function($) {
				$(function() {
					$("#schedulderDateId").datepicker({
						showOn : "both",
						buttonImage : "images/b_calendar.png",
						buttonImageOnly : true,
						buttonText : "Select date",
						changeMonth : true,
						changeYear : true,
						dateFormat : 'dd/mm/yy'
					});
				});

				var format = "dd/mm/yyyy";
				var match = new RegExp(format.replace(/(\w+)\W(\w+)\W(\w+)/,
						"^\\s*($1)\\W*($2)?\\W*($3)?([0-9]*).*").replace(
						/d|m|y/g, "\\d"));
				var replace = "$1/$2/$3$4".replace(/\//g, format.match(/\W/));

				function doFormat(target) {
					target.value = target.value.replace(/(^|\W)(?=\d\W)/g,
							"$10").replace(match, replace).replace(/(\W)+/g,
							"$1");
				}

				$("input[name='schedulderDate']:first").keyup(
						function(e) {
							if (!e.ctrlKey && !e.metaKey
									&& (e.keyCode == 32 || e.keyCode > 46))
								doFormat(e.target)
						});
			});

	function validationMeetingScheduler() {
		var commiteeDepartmentId = document
				.getElementById("commiteeDepartmentId").value;
		if (commiteeDepartmentId == null || commiteeDepartmentId == '') {
			document.getElementById('CommiteeDepartmentId').innerHTML = "Commitee is required.";
			document.getElementById('commiteeDepartmentId').focus();
			return false;
		} else {
			document.getElementById('CommiteeDepartmentId').innerHTML = "";
		}

		var deptEmail = document.getElementById("deptEmail").value;
		if (deptEmail == null || deptEmail == '') {
			document.getElementById('deptEmailID').innerHTML = "Department is required.";
			document.getElementById('deptEmail').focus();
			return false;
		} else {
			document.getElementById('deptEmailID').innerHTML = "";
		}

		if (commiteeDepartmentId == 'Empowered Committee') {

			var gosAppMegaID = document.getElementById("gosAppMegaID").value;
			if (gosAppMegaID == null || gosAppMegaID == '') {
				document.getElementById('GosAppMegaID').innerHTML = "Please Select Mega Applicant ID";
				document.getElementById('gosAppMegaID').focus();
				return false;
			} else {
				document.getElementById('GosAppMegaID').innerHTML = "";
			}
		}
		if (commiteeDepartmentId == 'Sanction Committee') {
			var gosAppID1 = document.getElementById("gosAppLargeID").value;
			if (gosAppID1 == null || gosAppID1 == '') {
				document.getElementById('GosAppLargeID').innerHTML = "Please Select Applicant ID";
				document.getElementById('gosAppLargeID').focus();
				return false;
			} else {
				document.getElementById('GosAppLargeID').innerHTML = "";
			}
		}

		var schedulerSubjectId = document.getElementById("schedulerSubjectId").value;
		if (schedulerSubjectId == null || schedulerSubjectId == '') {
			document.getElementById('SchedulerSubjectId').innerHTML = "Subject is required.";
			document.getElementById('schedulerSubjectId').focus();
			return false;
		} else {
			document.getElementById('SchedulerSubjectId').innerHTML = "";
		}
		var dateId = document.getElementById("schedulderDateId").value;
		if (dateId == null || dateId == '') {
			document.getElementById('SchedulderDateId').innerHTML = "Date is required.";
			document.getElementById('schedulderDateId').focus();
			return false;
		} else {
			document.getElementById('SchedulderDateId').innerHTML = "";
		}

		var timeId = document.getElementById("timePicker").value;
		if (timeId == null || timeId == '') {
			document.getElementById('TimePicker').innerHTML = "Time is required.";
			document.getElementById('timePicker').focus();
			return false;
		} else {
			document.getElementById('TimePicker').innerHTML = "";
		}
		var schedulerLocationId = document
				.getElementById("schedulerLocationId").value;
		if (schedulerLocationId == null || schedulerLocationId == '') {
			document.getElementById('SchedulerLocationId').innerHTML = "Location is required.";
			document.getElementById('schedulerLocationId').focus();
			return false;
		} else {
			document.getElementById('SchedulerLocationId').innerHTML = "";
		}

		var dataSplit = $("#schedulderDateId").val().split("/");
		var time = $("#timePicker").val();
		var hours = Number(time.match(/^(\d+)/)[1]);
		var minutes = Number(time.match(/:(\d+)/)[1]);
		var AMPM = time.match(/\s(.*)$/)[1];
		if (AMPM == "PM" && hours < 12)
			hours = hours + 12;
		if (AMPM == "AM" && hours == 12)
			hours = hours - 12;
		var sHours = hours.toString();
		var sMinutes = minutes.toString();
		if (hours < 10)
			sHours = "0" + sHours;
		if (minutes < 10)
			sMinutes = "0" + sMinutes;
		var schedulderDate = new Date(dataSplit[2], dataSplit[1] - 1,
				dataSplit[0]);
		schedulderDate.setHours(0, 0, 0, 0);
		schedulderDate.setHours(sHours);
		schedulderDate.setMinutes(sMinutes)
		var currentDate = new Date();
		if (schedulderDate < currentDate) {
			alert("Date and Time scheduled must be in the future.");
			document.getElementById('schedulderDateId').focus();
			return false;
		}
	}

	function showAndHidePM() {
		var commiteeName = document.getElementById("commiteeDepartmentId").value;

		if (commiteeName == 'Empowered Committee') {
			$('.megaId').show();
			$('.largeId').hide();
		}
		if (commiteeName == 'Sanction Committee') {
			$('.megaId').hide();
			$('.largeId').show();
		}

	}
	$(document).ready(function() {
		$('.largeId').hide();
	});
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
					<div class="mt-4">
						<div class="without-wizard-steps">
							<h4
								class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Meeting
								Scheduler</h4>
						</div>
					</div>
					<div class="card card-block p-3 overflow-v">
						<div class="step-links">
							<ul class="only-steps">
								<li><a href="./meetingschedule" class="active pr-3">Meeting
										Scheduler for LOC</a></li>
								<li><a href="./meetingscheduleDis" class="pr-3">Meeting
										Scheduler for Disbursement </a></li>
							</ul>
						</div>

						<div class="row">
							<div class="col-sm-12">
								<hr class="mt-2 mb-3">
								<spring:url value="/meetingScheduler"
									var="meetingSchedulerActionUrl" />
								<form:form modelAttribute="meetingScheduler" method="post"
									action="${meetingSchedulerActionUrl}" class="mt-4"
									onsubmit="return validationMeetingScheduler();">
									<div class="row">
										<div class="col-md-12 main">
											<div class="row">
												<div class="col-sm-5">
													<div class="form-group multiple-select">
														<label class="d-block">Select Department<font
															color="red"><span>*</span></font></label>
														<form:select path="deptEmail" class="form-control"
															id="deptEmail" multiple="multiple">
															<c:forEach items="${concernDepartmentList}" var="list">
																<form:option value="${list.deptEmail}">${list.newDeptName}</form:option>
															</c:forEach>
														</form:select>
														<span id="deptEmailID" class="text-danger color:red"></span>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<label>Select Commitee<font color="red"><span>*</span></font>
														</label>
														<form:select path="commiteeDepartment"
															class="form-control" id="commiteeDepartmentId"
															onchange="showAndHidePM()">
															<form:option value="">Select One</form:option>
															<form:option value="Empowered Committee">Empowered Committee</form:option>
															<form:option value="Sanction Committee">Sanction Committee</form:option>
														</form:select>
														<span id="CommiteeDepartmentId"
															class="text-danger color:red"></span>
													</div>
												</div>

												<div class="col-sm-5 megaId">
													<div class="form-group multiple-select">
														<label class="d-block">Select Application ID<font
															color="red"><span>*</span></font></label>
														<form:select path="appliId" class="form-control"
															id="gosAppMegaID" multiple="multiple"
															style="display: none;">
															<c:forEach items="${listpreAgendaNotesMega}" var="list">
																<form:option
																	value="${list.appliId} , ${list.appliacntEmailId}">${list.appliId}</form:option>
															</c:forEach>
														</form:select>
														<span id="GosAppMegaID" class="text-danger color:red"></span>
													</div>
												</div>
												<div class="col-sm-5 largeId">
													<div class="form-group multiple-select">
														<label class="d-block">Select Application ID<font
															color="red"><span>*</span></font></label>
														<form:select path="appliId" class="form-control"
															name="appliId" id="gosAppLargeID" multiple="multiple"
															style="display: none;">
															<c:forEach items="${listpreAgendaNotesLarge}" var="list">
																<form:option
																	value="${list.appliId}, ${list.appliacntEmailId}">${list.appliId}</form:option>
															</c:forEach>
														</form:select>
														<span id="GosAppLargeID" class="text-danger color:red"></span>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<label>Subject<font color="red"><span>*</span></font>
														</label>
														<form:input path="schedulerSubject" class="form-control"
															name="schedulerSubject" id="schedulerSubjectId"
															maxlength="100"></form:input>
														<span id="SchedulerSubjectId"
															class="text-danger color:red"></span>
													</div>

												</div>
												<div class="col-sm-5">
													<div class="form-group datepicker-box">
														<label>Set Date<font color="red"><span>*</span></font>
														</label>
														<form:input path="schedulderDate" class="form-control"
															name="schedulderDate" id="schedulderDateId" type="text"
															placeholder="DD/MM/YYYY" maxlength="10"></form:input>
													</div>
													<span id="SchedulderDateId" class="text-danger color:red"></span>
												</div>
												<div class="col-sm-6">
													<div class="form-group">
														<label>Set Time<font color="red"><span>*</span></font>
														</label>
														<div class="time-picker">
															<form:input path="time" class="form-control" name="time"
																id="timePicker" type="text" maxlength="10"></form:input>
															<span class="time-picker-show"><i
																class="fa fa-clock-o" aria-hidden="true"></i></span> <span
																id="TimePicker" class="text-danger color:red"></span>
														</div>
													</div>

												</div>
												<div class="col-sm-5">
													<div class="form-group">
														<label>Location<font color="red"><span>*</span></font>
														</label>
														<form:input path="schedulerLocation" class="form-control"
															name="schedulerLocation" id="schedulerLocationId"
															maxlength="100"></form:input>
														<span id="SchedulerLocationId"
															class="text-danger color:red"></span>
													</div>

												</div>
												<div class="col-sm-12 text-right">
													<button type="submit" class="common-btn">Schedule
														Meeting</button>
												</div>
											</div>
										</div>
									</div>
								</form:form>
								<hr class="mt-4">
								<div class="row">
									<div class="col-sm-12">
										<div class="table-responsive mt-3">
											<table class="table table-stripped table-bordered"
												id="customFields">
												<c:if test="${ not empty meetingSchedulerList}">
													<thead>
														<tr>
															<th>Select Commitee</th>
															<th>From Mail</th>
															<th>To Mail</th>
															<th>Subject</th>
															<th>Set Date</th>
															<th>Set Time</th>
															<th>Location</th>
														</tr>
													</thead>
												</c:if>
												<tbody>
													<c:forEach var="list" items="${meetingSchedulerList}"
														varStatus="counter">
														<tr>
															<td>${list.commiteeDepartment}</td>
															<td>${list.fromMail}</td>
															<td>${list.toMail}</td>
															<td>${list.schedulerSubject}</td>
															<td><fmt:formatDate pattern="dd/MM/yyyy"
																	value="${list.schedulderDate}" /></td>
															<td>${list.time}</td>
															<td>${list.schedulerLocation}</td>
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

	<!-- For TimePicker -->
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/moment.min.js"></script>
	<script src="https://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="js/time-picker.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.1.2/js/tempusdominus-bootstrap-4.min.js"></script>


	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<script src="js/bootstrap-multiselect.min.js"></script>
	<script src="js/datepicker.js" type="text/javascript"></script>
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

		// Multiple Select Dropdown Department
		$(document).ready(function() {
			$('#deptEmail').multiselect({
				buttonWidth : '100%',
				includeSelectAllOption : true,
				nonSelectedText : 'Select Options'
			});
		});

		function getSelectedValues() {
			var selectedVal = $("#deptEmail").val();
			for (var i = 0; i < selectedVal.length; i++) {
				function innerFunc(i) {
					setTimeout(function() {
						location.href = selectedVal[i];
					}, i * 2000);
				}
				innerFunc(i);
			}
		}

		// Multiple Select Dropdown For Mega
		$(document).ready(function() {
			$('#gosAppMegaID').multiselect({
				buttonWidth : '100%',
				includeSelectAllOption : true,
				nonSelectedText : 'Select Options'
			});
		});

		function getSelectedValues() {
			var selectedVal = $("#gosAppMegaID").val();
			for (var i = 0; i < selectedVal.length; i++) {
				function innerFunc(i) {
					setTimeout(function() {
						location.href = selectedVal[i];
					}, i * 2000);
				}
				innerFunc(i);
			}
		}

		// Multiple Select Dropdown For Large
		$(document).ready(function() {
			$('#gosAppLargeID').multiselect({
				buttonWidth : '100%',
				includeSelectAllOption : true,
				nonSelectedText : 'Select Options'
			});
		});

		function getSelectedValues() {
			var selectedVal = $("#gosAppLargeID").val();
			for (var i = 0; i < selectedVal.length; i++) {
				function innerFunc(i) {
					setTimeout(function() {
						location.href = selectedVal[i];
					}, i * 2000);
				}
				innerFunc(i);
			}
		}
	</script>
</body>

</html>