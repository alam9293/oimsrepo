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
<title>DIS - Minutes of Meeting and GO's</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap CSS -->
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
	$(document)
			.ready(
					function() {
						var committeeDepartmentId = document
								.getElementById("committeeDepartmentId").value;
						if (committeeDepartmentId == null
								|| committeeDepartmentId == '') {
							document.getElementById("SelectParameter").disabled = true;
							return false;
						} else if (committeeDepartmentId == 'Sanction Committee'
								|| committeeDepartmentId == 'Empowered Committee') {
							document.getElementById("SelectParameter").disabled = false;
						}
					});
	jQuery(document).ready(
			function($) {
				$(function() {
					$("#dateOfMeetingId").datepicker({
						showOn : "both",
						buttonImage : "images/b_calendar.png",
						buttonImageOnly : true,
						buttonText : "Select date",
						changeMonth : true,
						changeYear : true,
						dateFormat : 'dd/mm/yy'
					});
				});
				$(function() {
					$("#gosDateId").datepicker({
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

				$("input[name='dateOfMeeting']:first").keyup(
						function(e) {
							if (!e.ctrlKey && !e.metaKey
									&& (e.keyCode == 32 || e.keyCode > 46))
								doFormat(e.target)
						});

				$("input[name='gosDate']:first").keyup(
						function(e) {
							if (!e.ctrlKey && !e.metaKey
									&& (e.keyCode == 32 || e.keyCode > 46))
								doFormat(e.target)
						});
			});

	function validationminutesOfMeetings() {

		var committeeDepartmentId = document
				.getElementById("committeeDepartmentId").value;
		if (committeeDepartmentId == null || committeeDepartmentId == '') {
			document.getElementById('CommitteeDepartmentId').innerHTML = "Commitee is required.";
			document.getElementById('committeeDepartmentId').focus();
			return false;
		} else {
			document.getElementById('CommitteeDepartmentId').innerHTML = "";
		}

		var SelectParameter = document.getElementById("SelectParameter").value;
		if (SelectParameter == null || SelectParameter == '') {
			document.getElementById('selectParameter').innerHTML = "Upload for is required.";
			document.getElementById('SelectParameter').focus();
			return false;
		} else {
			document.getElementById('selectParameter').innerHTML = "";
		}
		if (SelectParameter == 'GOs') {

			var gosAppID = document.getElementById("gosAppMega2ID").value;
			if (gosAppID == null || gosAppID == '') {
				document.getElementById('GosAppMega2ID').innerHTML = "Please Select Applicant ID";
				document.getElementById('gosAppMega2ID').focus();
				return false;
			} else {
				document.getElementById('GosAppMega2ID').innerHTML = "";
			}

			var gosNoId = document.getElementById("gosNoId").value;
			if (gosNoId == null || gosNoId == '') {
				document.getElementById('GosNoId').innerHTML = "G.O. No. & Date is required.";
				document.getElementById('gosNoId').focus();
				return false;
			} else {
				document.getElementById('GosNoId').innerHTML = "";
			}
			/*  var gosDateId = document.getElementById("gosDateId").value;
			if (gosDateId == null || gosDateId == '') {
				document.getElementById('GosDateId').innerHTML = "Gos Date is required.";
				document.getElementById('gosDateId').focus();
				return false;
			} else {
				document.getElementById('GosDateId').innerHTML = "";
			}  */

			var GOFileId = document.getElementById("GOFileId").value;
			if (GOFileId == null || GOFileId == '') {
				document.getElementById('gOFileId').innerHTML = "Upload MOM  required.";
				document.getElementById('GOFileId').focus();
				return false;
			} else {
				document.getElementById('gOFileId').innerHTML = "";
			}
			if (GOFileId != null && GOFileId != '') {
				var ext = GOFileId.split('.').pop();
				if (ext == "pdf" || ext == "PDF") {
					// value is ok, use it
				} else {
					document.getElementById('gOFileId').innerHTML = "Please upload file in PDF Format.";
					document.getElementById('GOFileId').focus();
					return false;
				}
			}

		}

		else if (committeeDepartmentId == 'Empowered Committee'
				&& SelectParameter == 'MinutesofMeeting') {

			var gosAppMega1ID = document.getElementById("gosAppMega1ID").value;
			if (gosAppMega1ID == null || gosAppMega1ID == '') {
				document.getElementById('GosAppMega1ID').innerHTML = "Please Select Applicant ID";
				document.getElementById('gosAppMega1ID').focus();
				return false;
			} else {
				document.getElementById('GosAppMega1ID').innerHTML = "";
			}

			var committeeNameId = document.getElementById("committeeNameId").value;
			if (committeeNameId == null || committeeNameId == '') {
				document.getElementById('CommitteeNameId').innerHTML = "Commitee Name is required.";
				document.getElementById('committeeNameId').focus();
				return false;
			} else {
				document.getElementById('CommitteeNameId').innerHTML = "";
			}

			var dateOfMeetingId = document.getElementById("dateOfMeetingId").value;
			if (dateOfMeetingId == null || dateOfMeetingId == '') {
				document.getElementById('DateOfMeetingId').innerHTML = "Date Of Meeting is required.";
				document.getElementById('dateOfMeetingId').focus();
				return false;
			} else {
				document.getElementById('DateOfMeetingId').innerHTML = "";
			}
			var UploadMom1 = document.getElementById("UploadMom1").value;
			if (UploadMom1 == null || UploadMom1 == '') {
				document.getElementById('uploadMom').innerHTML = "Upload MOM  required.";
				document.getElementById('UploadMom1').focus();
				return false;
			} else {
				document.getElementById('uploadMom').innerHTML = "";
			}
			if (UploadMom1 != null && UploadMom1 != '') {
				var ext = UploadMom1.split('.').pop();
				if (ext == "pdf" || ext == "PDF") {
					// value is ok, use it
				} else {
					document.getElementById('uploadMom').innerHTML = "Please upload file in PDF Format.";
					document.getElementById('UploadMom1').focus();
					return false;
				}
			}
		}
		
		else if (committeeDepartmentId == 'Empowered Committee'
			&& SelectParameter == 'CabinetNote')
			{

			var gosAppMega1ID = document.getElementById("cabinetAppMega1ID").value;
			if (gosAppMega1ID == null || gosAppMega1ID == '') {
				document.getElementById('cabinetAppMega1IDMsg').innerHTML = "Please Select Applicant ID";
				document.getElementById('cabinetAppMega1ID').focus();
				return false;
			} else {
				document.getElementById('cabinetAppMega1IDMsg').innerHTML = "";
			}
			
			var UploadCabinet1 = document.getElementById("UploadCabinetNote").value;
			if (UploadCabinet1 == null || UploadCabinet1 == '') {
				document.getElementById('UploadCabinetNoteMsg').innerHTML = "Upload CabinetNote required.";
				document.getElementById('UploadCabinetNote').focus();
				return false;
			} else {
				document.getElementById('UploadCabinetNoteMsg').innerHTML = "";
			}
			if (UploadCabinet1 != null && UploadCabinet1 != '') {
				var ext = UploadCabinet1.split('.').pop();
				if (ext == "pdf" || ext == "PDF") {
					// value is ok, use it
				} else {
					document.getElementById('UploadCabinetNoteMsg').innerHTML = "Please upload file in PDF Format.";
					document.getElementById('UploadCabinetNote').focus();
					return false;
				}
			}

			}

		else if (committeeDepartmentId == 'Sanction Committee'
			&& SelectParameter == 'CabinetNote')
			{

			var gosAppID1 = document.getElementById("cabinetAppLargeID").value;
			if (gosAppID1 == null || gosAppID1 == '') {
				document.getElementById('cabinetAppLargeIDMsg').innerHTML = "Please Select Applicant ID";
				document.getElementById('cabinetAppLargeID').focus();
				return false;
			} else {
				document.getElementById('cabinetAppLargeIDMsg').innerHTML = "";
			}
			
			var UploadCabinet1 = document.getElementById("UploadCabinetNote").value;
			if (UploadCabinet1 == null || UploadCabinet1 == '') {
				document.getElementById('UploadCabinetNoteMsg').innerHTML = "Upload CabinetNote required.";
				document.getElementById('UploadCabinetNote').focus();
				return false;
			} else {
				document.getElementById('UploadCabinetNoteMsg').innerHTML = "";
			}
			if (UploadCabinet1 != null && UploadCabinet1 != '') {
				var ext = UploadCabinet1.split('.').pop();
				if (ext == "pdf" || ext == "PDF") {
					// value is ok, use it
				} else {
					document.getElementById('UploadCabinetNoteMsg').innerHTML = "Please upload file in PDF Format.";
					document.getElementById('UploadCabinetNote').focus();
					return false;
				}
			}

			}
		
		else {
			var committeeDepartmentId = document
					.getElementById("committeeDepartmentId").value;
			if (committeeDepartmentId == null || committeeDepartmentId == '') {
				document.getElementById('CommitteeDepartmentId').innerHTML = "Commitee is required.";
				document.getElementById('committeeDepartmentId').focus();
				return false;
			} else {
				document.getElementById('CommitteeDepartmentId').innerHTML = "";
			}

			var gosAppID1 = document.getElementById("gosAppLargeID").value;
			if (gosAppID1 == null || gosAppID1 == '') {
				document.getElementById('GosAppLargeID').innerHTML = "Please Select Applicant ID";
				document.getElementById('gosAppLargeID').focus();
				return false;
			} else {
				document.getElementById('GosAppLargeID').innerHTML = "";
			}

			var committeeNameId = document.getElementById("committeeNameId").value;
			if (committeeNameId == null || committeeNameId == '') {
				document.getElementById('CommitteeNameId').innerHTML = "Commitee Name is required.";
				document.getElementById('committeeNameId').focus();
				return false;
			} else {
				document.getElementById('CommitteeNameId').innerHTML = "";
			}

			var dateOfMeetingId = document.getElementById("dateOfMeetingId").value;
			if (dateOfMeetingId == null || dateOfMeetingId == '') {
				document.getElementById('DateOfMeetingId').innerHTML = "Date Of Meeting is required.";
				document.getElementById('dateOfMeetingId').focus();
				return false;
			} else {
				document.getElementById('DateOfMeetingId').innerHTML = "";
			}
			var UploadMom1 = document.getElementById("UploadMom1").value;
			if (UploadMom1 == null || UploadMom1 == '') {
				document.getElementById('uploadMom').innerHTML = "Upload MOM  required.";
				document.getElementById('UploadMom1').focus();
				return false;
			} else {
				document.getElementById('uploadMom').innerHTML = "";
			}
			if (UploadMom1 != null && UploadMom1 != '') {
				var ext = UploadMom1.split('.').pop();
				if (ext == "pdf" || ext == "PDF") {
					// value is ok, use it
				} else {
					document.getElementById('uploadMom').innerHTML = "Please upload file in PDF Format.";
					document.getElementById('UploadMom1').focus();
					return false;
				}
			}
		}
		document.getElementById("committeeDepartmentId").disabled = false;
	}

	function clearFormValue() {
		var committeeDepartmentId = document.getElementById("committeeDepartmentId").value;
		var SelectParameter = document.getElementById("SelectParameter").value;
		if (SelectParameter == 'GOs') {
			document.getElementById("committeeDepartmentId").value = "Empowered Committee";
			document.getElementById("committeeNameId").value = "";
			document.getElementById("dateOfMeetingId").value = "";
			document.getElementById("UploadMom1").value = "";
			document.getElementById("UploadCabinetNote").value = "";

			$('.megaCabinetId').hide();
			$('.largeCabinetId').hide();
			$('.cabinetNoteId').hide();
			
			document.getElementById("committeeDepartmentId").disabled = true;
		}
		else if ( committeeDepartmentId =='Empowered Committee' && SelectParameter == 'CabinetNote') {
			document.getElementById("committeeNameId").value = "";
			document.getElementById("dateOfMeetingId").value = "";
			document.getElementById("UploadMom1").value = "";
			document.getElementById("GOFileId").value = "";
			document.getElementById("gosNoId").value = "";
			$('.megaCabinetId').show();
			$('.largeCabinetId').hide();
			$('.cabinetNoteId').show();
			
			document.getElementById("committeeDepartmentId").disabled =false;
		}
		else if (committeeDepartmentId =='Sanction Committee' && SelectParameter == 'CabinetNote' ) {
			document.getElementById("committeeNameId").value = "";
			document.getElementById("dateOfMeetingId").value = "";
			document.getElementById("UploadMom1").value = "";
			document.getElementById("GOFileId").value = "";
			document.getElementById("gosNoId").value = "";
			$('.megaCabinetId').hide();
			$('.largeCabinetId').show();
			$('.cabinetNoteId').show();
			document.getElementById("committeeDepartmentId").disabled =false;
		}
		else if (SelectParameter == '') {
			document.getElementById("committeeDepartmentId").disabled = false;
		} else {
			//document.getElementById('gosNameId').value = "";
			document.getElementById("GOFileId").value = "";
			document.getElementById("gosNoId").value = "";
			document.getElementById("UploadCabinetNote").value = "";
			$('.megaCabinetId').hide();
			$('.largeCabinetId').hide();
			$('.cabinetNoteId').hide();
			document.getElementById("committeeDepartmentId").disabled = false;
		}

	}

	function clearFormValue1() {

		var committeeDepartmentId = document
				.getElementById("committeeDepartmentId").value;
		if (committeeDepartmentId == null || committeeDepartmentId == '') {
			document.getElementById("SelectParameter").disabled = true;
			return false;
		} else if (committeeDepartmentId == 'Sanction Committee'
				|| committeeDepartmentId == 'Empowered Committee') {
			document.getElementById("SelectParameter").disabled = false;
		}

		var committeeDepartmentId = document
				.getElementById("committeeDepartmentId").value;
		if (committeeDepartmentId == 'Sanction Committee') {
			$("#DropDwnGO").hide();
		} else {
			$("#DropDwnGO").show();
		}
	}

	function showAndHidePM() {

		var pmvalue = document.getElementById("committeeDepartmentId").value;
		var SelectParameter = document.getElementById("SelectParameter").value;

		if (pmvalue == 'Empowered Committee') {
			/*  var pp = 0;
			 $('#amtInvPNMIis').val(pp); */
			$('.megaId').show();
			$('.largeId').hide();
		}

		if (pmvalue == 'Sanction Committee') {
			/*  var pp = 0;
			 $('#iisloan').val(pp); */
			$('.megaId').hide();
			$('.largeId').show();
		}
	 if (pmvalue == 'Sanction Committee' && SelectParameter == 'CabinetNote') {
		 $('.megaCabinetId').hide();
			$('.largeCabinetId').show();
			$('.cabinetNoteId').show();
		
		} 

		 if (pmvalue == 'Empowered Committee' && SelectParameter == 'CabinetNote') {
		
		$('.megaCabinetId').show();
		$('.largeCabinetId').hide();
		$('.cabinetNoteId').show();
	} 
	}
</script>
<script type="text/javascript">
	function emptyCapInvErrMsg() {
		$("#CommitteeDepartmentId  ").text('');
		$("#selectParameter").text('');
		$("#GosAppMega1ID").text('');
		$("#GosAppLargeID").text('');
		$("#CommitteeNameId").text('');
		$("#DateOfMeetingId").text('');
		$("#uploadMom").text('');
		$("#GosAppMega2ID").text('');
		$("#GosNoId").text('');
		$("#UploadCabinetNoteMsg").text('');
		$("#gOFileId").text('');
		$("#megaCabinetId").text('');
		$("#largeCabinetId").text('');

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
						<li class="nav-item animate__animated animate__bounce"><a
							class="nav-link" href="./dashboard"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluate"><i class="fa fa-eye"></i> View and
								Evaluate Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluateSME"><i class="fa fa-eye"></i> View
								and Forward SME applications</a></li>
						<li class="nav-item"><a class="nav-link" href="./queryRaised"><i
								class="fa fa-question-circle"></i> Query </a></li>
						<!-- <li class="nav-item"><a class="nav-link" href="./viewQueryResponse"><i class="fa fa-question-circle"></i>Query Response By Enterpreneur</a></li> -->
						<li class="nav-item"><a class="nav-link" href="./agendaNode"><i
								class="fa fa-list"></i> Prepare Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./meetingschedule"><i class="fa fa-calendar"></i>
								Schedule meeting</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./momgo"><i class="fa fa-calendar"></i> Minutes of
								Meeting and GO's</a></li>
						<li class="nav-item"><a class="nav-link" href="./generateLoc"><i
								class="fa fa-wpforms"></i> Generate LOC</a></li>

					</ul>
				</div>
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
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
										<li><a href="./momgo" class="pr-3">MOM for LOC</a></li>
										<li><a href="./momgoDis" class="active pr-3">MOM for
												Disbursement </a></li>
									</ul>
								</div>
								<hr class="mt-2 mb-3">
								<div class="row">
									<div class="col-sm-12 text-right">
										<div class="form-group">
											<button type="button" class="btn btn-outline-info btn-sm"
												data-target="#UploadMOM" data-toggle="modal">Upload
												Minutes of Meeting</button>
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
										<div id="collapseOne" class="card-body collapse"
											data-parent="#accordion">

											<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>S.No</th>
																	<th>Applicant ID</th>
																	<th>Committee Name</th>
																	<th>Date</th>
																	<th>View Document</th>
																	<th>Action</th>
																</tr>
															</thead>
															<tbody>
																<c:forEach var="list"
																	items="${minutesOfMeetingEmpowerCommitList}"
																	varStatus="counter">
																	<tr>
																		<td>${counter.index+1}</td>
																		<td>${list.gosAppID}</td>
																		<td>${list.committeeName}</td>
																		<td><fmt:formatDate pattern="dd/MM/yyyy"
																				value="${list.dateOfMeeting}" /></td>
																		<td><a
																			href="./downloadDocMom?fileNameMomId=${list.id}"
																			target="_blank"><small>${list.uploadMomFile}</small></a></td>
																			
																			<td><%-- <a href="#" class="btn btn-outline-success btn-sm" data-target="#CirculateDraft" data-toggle="modal">${list.gosAppID}Circulate Minutes of Meeting</a> --%>
																		<button type="button"
																			class="btn btn-outline-success btn-sm"
																			id="${list.gosAppID}" data-target="#CirculateDraft"
																		value="Submit" 
																			data-toggle="modal">Circulate Minutes of Meeting</button>
																			</td>
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
										<div id="collapseTwo" class="card-body collapse"
											data-parent="#accordion">

											<div class="row">
												<div class="col-sm-12">
													<div class="table-responsive">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>S.No</th>
																	<th>Applicant ID</th>
																	<th>Committee Name</th>
																	<th>Date</th>
																	<th>View Document</th>
																	<th>Action</th>
																</tr>
															</thead>
															<tbody>
																<c:forEach var="list"
																	items="${minutesOfMeetingSactionCommitList}"
																	varStatus="counter">
																	<tr>
																		<td>${counter.index+1}</td>
																		<td>${list.gosAppID}</td>
																		<td>${list.committeeName}</td>
																		<td><fmt:formatDate pattern="dd/MM/yyyy"
																				value="${list.dateOfMeeting}" /></td>
																		<td><a
																			href="./downloadDocMom?fileNameMomId=${list.id}"><small>${list.uploadMomFile}</small></a></td>
                                                                           <td><a href="#" class="btn btn-outline-success btn-sm" id="${list.gosAppID}" 
                                                                           data-target="#CirculateDraftSanction" data-toggle="modal">Circulate Minutes of Meeting</a>
																		</td>
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
																	<th>Applicant ID</th>
																	<th>G.O. No. & Date</th>
																	<th>View Document</th>
																	<th>Action</th>
																</tr>
															</thead>
															<tbody>
																<c:forEach var="list"
																	items="${minutesOfMeetingGosCommitList}"
																	varStatus="counter">
																	<tr>
																		<td>${counter.index+1}</td>
																		<td>${list.gosAppID}</td>
																		<td>${list.gosNo}</td>
																		<%-- <td>${list.gosName}</td> --%>
																		<%-- <td><fmt:formatDate pattern="dd/MM/yyyy"
																				value="${list.gosDate}" /></td> --%>
																		<td><a
																			href="./downloadDocMom?fileNameMomId=${list.id}"><small>${list.uploadGosFile}</small></a></td>
                                                                            <td><a href="#" class="btn btn-outline-success btn-sm" id="${list.gosAppID}" 
                                                                           data-target="#CirculateDraftGo" data-toggle="modal">Circulate GO's</a></td>
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
				  <div class="card-header collapsed mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                <a class="card-title">
                                  <strong>Cabinet Note</strong>
                                </a>
                            </div>
                            <div id="collapseFour" class="card-body collapse" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th style="width: 15%;" class="text-center">Application ID</th>
                                            <th>View Document</th>
                                            <th>Action</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="list"
																	items="${minutesOfMeetingCabinetCommitList}"
																	varStatus="counter">
																	<tr>
																		<%-- <td>${counter.index+1}</td> --%>
																		<td>${list.gosAppID}</td>
																		
																	
																		<td><a href="./downloadDocMom?fileNameMomId=${list.id}"><small>${list.uploadCabinetFile}</small></a>
																		</td>
                                                                            <td><a href="#" class="btn btn-outline-success btn-sm" id="${list.gosAppID}" 
                                                                           data-target="#CirculateDraftCabinet" data-toggle="modal">Circulate Cabinet Note</a>
																		</td>
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
								<p>� 2020 - IT Solution powered by National Informatics
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
					<spring:url value="/minutesOfMeetingsDis"
						var="minutesOfMeetingsActionUrl" />
					<form:form modelAttribute="minutesOfMeeting" method="post"
						action="${minutesOfMeetingsActionUrl}" class="mt-4"
						onsubmit="return validationminutesOfMeetings();"
						enctype="multipart/form-data">
						<!-- Modal body -->
						<div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label>Select Committee<font color="red"><span>*</span></font></label>
										<form:select class="form-control" path="committeeDepartments"
											id="committeeDepartmentId"
											onchange="clearFormValue1();showAndHidePM()"
											onblur="emptyCapInvErrMsg()">
											<form:option value="">Select Committee</form:option>
											<form:option value="Empowered Committee">Empowered Committee</form:option>
											<form:option value="Sanction Committee">Sanction Committee</form:option>
										</form:select>
										<span id="CommitteeDepartmentId" class="text-danger color:red"></span>
									</div>

								</div>
							</div>

							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label>Upload for<font color="red"><span>*</span></font></label>
										<form:select class="form-control" id="SelectParameter"
											path="minutesOfMeetingOrGos" onchange="clearFormValue()"
											onblur="emptyCapInvErrMsg()">
											<option value="">Select One</option>
											<option value="MinutesofMeeting">Minutes of Meeting</option>
											<option id="DropDwnGO" value="GOs">GO's</option>
											 <option value="CabinetNote">Cabinet Note</option>
										</form:select>
										<span id="selectParameter" class="text-danger color:red"></span>
									</div>
								</div>
							</div>

							<div class="row parameter-properties " id="MinutesofMeeting">
								<div class="col-md-12 megaId">
									<div class="form-group">
										<label>Select Applicant ID<font color="red"><span>*</span></font></label>
										<form:select path="gosAppID" class="form-control"
											name="gosAppID" id="gosAppMega1ID"
											onblur="emptyCapInvErrMsg()">
											<form:option value="">Select One</form:option>
											<c:forEach items="${listpreAgendaNotesMega}" var="list">
												<form:option value="${list.appliId}">${list.appliId}</form:option>
											</c:forEach>
										</form:select>
										<span id="GosAppMega1ID" class="text-danger color:red"></span>
									</div>
								</div>

								<div class="col-md-12 largeId">
									<div class="form-group">
										<label>Select Applicant ID<font color="red"><span>*</span></font></label>
										<form:select path="gosAppID" class="form-control"
											name="gosAppID" id="gosAppLargeID"
											onblur="emptyCapInvErrMsg()">
											<form:option value="">Select One</form:option>
											<c:forEach items="${listpreAgendaNotesLarge}" var="list">
												<form:option value="${list.appliId}">${list.appliId}</form:option>
											</c:forEach>
										</form:select>
										<span id="GosAppLargeID" class="text-danger color:red"></span>
									</div>
								</div>

								<div class="col-md-12">
									<div class="form-group">
										<label>Committee Name<font color="red"><span>*</span></font></label>
										<form:input path="committeeName" type="text"
											class="form-control" name="committeeName"
											id="committeeNameId" maxlength="100"
											onkeyup="emptyCapInvErrMsg()"></form:input>
										<span id="CommitteeNameId" class="text-danger color:red"></span>
									</div>
								</div>

								<div class="col-md-12">
									<div class="form-group datepicker-box">
										<label>Date of Meeting<font color="red"><span>*</span></font></label>
										<form:input path="dateOfMeeting" type="text"
											class="form-control" name="dateOfMeeting"
											id="dateOfMeetingId" maxlength="10"
											onblur="emptyCapInvErrMsg()"></form:input>
										<span id="DateOfMeetingId" class="text-danger color:red"></span>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label>Upload MOM File<font color="red"><span>*</span></font></label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="UploadMom1"
												name="uploadMomFile" onblur="emptyCapInvErrMsg()"> <label
												class="custom-file-label" for="UploadMom1" id="uploadMom1">Choose
												File</label>
										</div>
										<span id="uploadMom" class="text-danger color:red"></span>
									</div>
								</div>
							</div>
							<div class="row parameter-properties " id="GOs">
								<div class="col-md-12">
									<div class="form-group">
										<label>Select Applicant ID<font color="red"><span>*</span></font></label>
										<form:select path="gosAppID" class="form-control"
											name="gosAppID" id="gosAppMega2ID"
											onblur="emptyCapInvErrMsg()">
											<form:option value="">Select One</form:option>
											<c:forEach items="${listpreAgendaNotesMega}" var="list">
												<form:option value="${list.appliId}">${list.appliId}</form:option>
											</c:forEach>
										</form:select>
										<span id="GosAppMega2ID" class="text-danger color:red"></span>
									</div>
								</div>

								<div class="col-md-12">
									<div class="form-group">
										<label>G.O. No. & Date <font color="red"><span>*</span></font></label>
										<form:input path="gosNo" type="text" class="form-control"
											name="gosNo" id="gosNoId" maxlength="100"
											onkeyup="emptyCapInvErrMsg()"></form:input>
										<span id="GosNoId" class="text-danger color:red"></span>
									</div>
								</div>
								<%-- <div class="col-md-12">
									<div class="form-group">
										<label>GO's Name<font color="red"><span>*</span></font></label>
										<form:input path="gosName" type="text" class="form-control"
											name="gosName" id="gosNameId" maxlength="100"></form:input>
									</div>
									<span id="GosNameId" class="text-danger color:red"></span>
								</div> --%>
								<%-- <div class="col-md-12">
									<div class="form-group datepicker-box">
										<label>GO's Date<font color="red"><span>*</span></font></label>
										<form:input path="gosDate" type="text" class="form-control"
											name="gosDate" id="gosDateId" maxlength="10"></form:input>
									</div>
									<span id="GosDateId" class="text-danger color:red"></span>
								</div> --%>
								<div class="col-md-12">
									<div class="form-group">
										<label>Upload GO's File<font color="red"><span>*</span></font></label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="GOFileId"
												name="uploadGosFile" onblur="emptyCapInvErrMsg()"> <label
												class="custom-file-label" for="GOFile">Choose File</label>
										</div>
										<span id="gOFileId" class="text-danger color:red"></span>
									</div>
								</div>
							</div>
							<div class="row parameter-properties " id="CabinetNote">
							<div class="col-md-12 megaCabinetId">
									<div class="form-group">
										<label>Select Applicant ID<font color="red"><span>*</span></font></label>
										<form:select path="gosAppID" class="form-control"
											name="gosAppID" id="cabinetAppMega1ID"
											onblur="emptyCapInvErrMsg()">
											<form:option value="">Select One</form:option>
											<c:forEach items="${listpreAgendaNotesMega}" var="list">
												<form:option value="${list.appliId}">${list.appliId}</form:option>
											</c:forEach>
										</form:select>
										<span id="cabinetAppMega1IDMsg" class="text-danger color:red"></span>
									</div>
								</div>
								
								<div class="col-md-12 largeCabinetId">
									<div class="form-group">
										<label>Select Applicant ID<font color="red"><span>*</span></font></label>
										<form:select path="gosAppID" class="form-control"
											name="gosAppID" id="cabinetAppLargeID"
											onblur="emptyCapInvErrMsg()">
											<form:option value="">Select One</form:option>
											<c:forEach items="${listpreAgendaNotesLarge}" var="list">
												<form:option value="${list.appliId}">${list.appliId}</form:option>
											</c:forEach>
										</form:select>
										<span id="cabinetAppLargeIDMsg" class="text-danger color:red"></span>
									</div>
								</div>
				
						
							
                <div class="col-md-12 cabinetNoteId">
                  <div class="form-group">
                    <label>Upload Cabinet Note File</label>
                    <div class="custom-file">
                      <input type="file" class="custom-file-input" id="UploadCabinetNote" 
                      name="uploadCabinetFile" onblur="emptyCapInvErrMsg()">
                      <label class="custom-file-label" for="UploadCabinetNote">Choose File</label>
                    </div>
                    <span id="UploadCabinetNoteMsg" class="text-danger color:red"></span>
                  </div>
                </div>
              </div>
						
</div>
						<div class="modal-footer">
							<button type="submit" class="common-btn">Submit</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="container">
        <div class="modal fade" id="CirculateDraft" style="display: none;" aria-hidden="true">
        <div class="modal-dialog modal-lg">
        
        <form:form modelAttribute="departmentDis"
					action="momconcerncirculateemDis" autocomplete="off" method="POST">
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">Send to Departments </h4>
              <button type="button" class="close" data-dismiss="modal">�</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<!-- Sachin  -->
									<input type="text" name="conDeptApplId" id="appliId" hidden="">
									<c:forEach items="${concernDepartment}" var="temp">
										<div class="custom-control custom-checkbox">
											<form:checkbox path="newDeptName" value="${temp.deptEmail},${temp.newDeptName}"
												class="custom-control-input" id="${temp.newDeptName}"
												name="${temp.newDeptName}"></form:checkbox>
											<label class="custom-control-label" for="${temp.newDeptName}">${temp.newDeptName}</label>
										</div>
										
									</c:forEach>
									<!--  Sachin -->

								</div>
							</div>
						</div>
            			<div class="modal-footer">
							<form:button type="submit" class="common-btn mt-3">Submit</form:button>
						</div>
          </div>
          </form:form>
        </div>
      </div>
    </div>
	
	<div class="container">
        <div class="modal fade" id="CirculateDraftSanction" style="display: none;" aria-hidden="true">
        <div class="modal-dialog modal-lg">
        
        <form:form modelAttribute="departmentDis"
					action="momconcerncirculateSanDis" autocomplete="off" method="POST">
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">Send to Departments </h4>
              <button type="button" class="close" data-dismiss="modal">�</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<!-- Sachin  -->
									<input type="text" name="conDeptApplId" id="appliIds" hidden="">
									<c:forEach items="${concernDepartment}" var="temp">
										<div class="custom-control custom-checkbox">
											<form:checkbox path="newDeptName" value="${temp.deptEmail},${temp.newDeptName}"
												class="custom-control-input" id="${temp.newDeptName},1"
												name="${temp.newDeptName}"></form:checkbox>
											<label class="custom-control-label" for="${temp.newDeptName},1">${temp.newDeptName}</label>
										</div>
										
									</c:forEach>
									<!--  Sachin -->

								</div>
							</div>
						</div>
            			<div class="modal-footer">
							<form:button type="submit" class="common-btn mt-3">Submit</form:button>
						</div>
          </div>
          </form:form>
        </div>
      </div>
    </div>
    
    <div class="container">
        <div class="modal fade" id="CirculateDraftGo" style="display: none;" aria-hidden="true">
        <div class="modal-dialog modal-lg">
        
        <form:form modelAttribute="departmentDis" 
					action="momconcerncirculateGosDis" autocomplete="off" method="POST">
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">Send to Departments GO</h4>
              <button type="button" class="close" data-dismiss="modal">�</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<!-- Sachin  -->
									<input type="text" name="conDeptApplId" id="appliIdg" hidden="">
									<c:forEach items="${concernDepartment}" var="temp">
										<div class="custom-control custom-checkbox">
											<form:checkbox path="newDeptName" value="${temp.deptEmail},${temp.newDeptName}"
												class="custom-control-input" id="${temp.newDeptName},2"
												name="${temp.newDeptName}"></form:checkbox>
											<label class="custom-control-label" for="${temp.newDeptName},2">${temp.newDeptName}</label>
										</div>
										
									</c:forEach>
									<!--  Sachin -->

								</div>
							</div>
						</div>
            			<div class="modal-footer">
							<form:button type="submit" class="common-btn mt-3">Submit</form:button>
						</div>
          </div>
          </form:form>
        </div>
      </div>
    </div>
    
    <div class="container">
        <div class="modal fade" id="CirculateDraftCabinet" style="display: none;" aria-hidden="true">
        <div class="modal-dialog modal-lg">
        
        <form:form modelAttribute="departmentDis" 
					action="momconcerncirculateCabinet" autocomplete="off" method="POST">
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">Send to Departments GO</h4>
              <button type="button" class="close" data-dismiss="modal">�</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<!-- Sachin  -->
									<input type="text" name="conDeptApplId" id="appliIdc" hidden="">
									<c:forEach items="${concernDepartment}" var="temp">
										<div class="custom-control custom-checkbox">
											<form:checkbox path="newDeptName" value="${temp.deptEmail},${temp.newDeptName}"
												class="custom-control-input" id="${temp.newDeptName},3"
												name="${temp.newDeptName}"></form:checkbox>
											<label class="custom-control-label" for="${temp.newDeptName},3">${temp.newDeptName}</label>
										</div>
										
									</c:forEach>
									<!--  Sachin -->

								</div>
							</div>
						</div>
            			<div class="modal-footer">
							<form:button type="submit" class="common-btn mt-3">Submit</form:button>
						</div>
          </div>
          </form:form>
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

			$('#CirculateDraft').on('show.bs.modal', function(e) {
				var $modal = $(this), esseyId = e.relatedTarget.id;
				document.getElementById("appliId").value = e.relatedTarget.id;
				})

				$('#CirculateDraftGo').on('show.bs.modal',function(e) {
					var $modal = $(this), esseyId = e.relatedTarget.id;
					document.getElementById("appliIdg").value = e.relatedTarget.id;
				})

				$('#CirculateDraftSanction').on('show.bs.modal',function(e) {
					var $modal = $(this), esseyId = e.relatedTarget.id;
					document.getElementById("appliIds").value = e.relatedTarget.id;
				})

				$('#CirculateDraftCabinet').on('show.bs.modal',function(e) {
					var $modal = $(this), esseyId = e.relatedTarget.id;
					document.getElementById("appliIdc").value = e.relatedTarget.id;
				})
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