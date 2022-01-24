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
<script data-require="jquery@*" data-semver="2.0.3"
	src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
<script data-require="bootstrap@*" data-semver="3.1.1"
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript">
	function prepareAgendaNoteValidation() {
		var selected = new Array();

		$("#table1 input[type=checkbox]:checked").each(function() {

			selected.push(this.value);
			selected.join(",")
		});

		if (selected.length <= 0) {
			alert('Please select Application Included for Agenda Note.');
			return false;
		}
	}

	function preAgenNoSanctionCommVali() {
		var selected = new Array();
		$("#table4 input[type=checkbox]:checked").each(function() {

			selected.push(this.value);
			selected.join(",")
		});

		if (selected.length <= 0) {
			alert('Please select Application Included for Agenda Note.');
			return false;
		}
	}
	function preparedAgendaNoteValidation() {
		var selected = new Array();

		$("#table2 input[type=checkbox]:checked").each(function() {

			selected.push(this.value);
			selected.join(",")
		});

		if (selected.length <= 0) {
			alert('Please select view prepare Agenda Note.');
			return false;
		}
	}
	function approvedAgendaNoteValidation() {
		var selected = new Array();

		$("#table3 input[type=checkbox]:checked").each(function() {

			selected.push(this.value);
			selected.join(",")
		});

		if (selected.length <= 0) {
			alert('Please select Agenda Note Approved for Empower Committee.');
			return false;
		}
		$('input[name=prepareAgendaNote]').val(selected);
		//document.getElementById("applicantid").innerHTML = selected;

	}

	$(document).ready(displayCheckbox);

	CountSelectedCB = [];

	function displayCheckbox() {
		$("input:checkbox").change(function() {
			selectedCB = [];
			notSelectedCB = [];

			CountSelectedCB.length = 0; // clear selected cb count
			$("input:checkbox").each(function() {
				if ($(this).is(":checked")) {
					CountSelectedCB.push($(this).attr("id"));
				}
			});

			$('input[name=selectedCB]').val(CountSelectedCB);
		});
	}
	function approvedAgeNoLargeValidation() {
		var selected = new Array();

		$("#table4 input[type=checkbox]:checked").each(function() {

			selected.push(this.value);
			selected.join(",")
		});

		if (selected.length <= 0) {
			alert('Please select Agenda Note Approved for Sanction Committee.');
			return false;
		}
	}

	function addNote() {
		var r = confirm("Are you Sure Want to Submit ?");

		if (r == true) {
			alert("Note Added Successfully");
		} else {
			return false
		}
	}

	function submitToComitee() {
		var r = confirm("Are you Sure Want to Submit the Application to Commitee?");

		if (r == true) {
			alert("Application Submitted to Commitee Successfully");
		} else {
			return false
		}
	}

	function circulateAgendaNote() {
		var r = confirm("Are you Sure Want to Circulate the Agenda Note?");

		if (r == true) {
			alert("Agenda Note Circulated to Concerned Department Successfully");
		} else {
			return false
		}
	}
</script>
<script type="text/javascript">
	$(function() {
		$("#AddScrutinyFile1").change(function() {
			if (fileExtValidate(this)) {
				if (FileSizeValidate(this)) {
					return true;
				}
			}
		});

		// File extension validation, Add more extension you want to allow
		var validExt = ".pdf";
		function fileExtValidate(fdata) {
			var filePath = fdata.value;
			var getFileExt = filePath.substring(filePath.lastIndexOf('.') + 1)
					.toLowerCase();
			var pos = validExt.indexOf(getFileExt);
			if (pos < 0) {
				document.getElementById("AddScrutinyFile").value = '';
				document.getElementById("AddScrutinyFile1").innerHTML = '';
				alert("Please upload PDF file format.");

				return false;
			} else {
				return true;
			}
		}

		// photo file size validation
		// size in mb
		function FileSizeValidate(fdata) {
			var maxSize = '2';//size in KB
			if (fdata.files && fdata.files[0]) {
				var fsize = fdata.files[0].size / (1024 * 1024);
				if (fsize > maxSize) {
					alert('File size should not be more than 2MB. The uploaded file size is: '
							+ fsize + " MB");
					document.getElementById("AddScrutinyFile").value = '';
					document.getElementById("AddScrutinyFile1").innerHTML = '';
					return false;
				} else {
					return true;
				}
			}
		}

	});
</script>

<script>
	function ScrutinyFilenameDoc(file) {
		const fi = document.getElementById('scrutinyFilenameId');
		// Check if any file is selected.
		if (fi.files.length > 0) {
			for (const i = 0; i <= fi.files.length - 1; i++) {

				const fsize = fi.files.item(i).size;
				const file = Math.round((fsize / 1024));
				// The size of the file.
				if (file >= 2048) {
					alert("File too Big, please select a file less than 2mb");
				} else {
					document.getElementById('size').innerHTML = '<b>' + file
							+ '</b> KB';
				}
			}
		}
	}

	function ScrutinyFilenameDocSanction(file) {
		const fi = document.getElementById('scrutinyFilenameIdSanction');
		// Check if any file is selected.
		if (fi.files.length > 0) {
			for (const i = 0; i <= fi.files.length - 1; i++) {

				const fsize = fi.files.item(i).size;
				const file = Math.round((fsize / 1024));
				// The size of the file.
				if (file >= 2048) {
					alert("File too Big, please select a file less than 2mb");
				} else {
					document.getElementById('size').innerHTML = '<b>' + file
							+ '</b> KB';
				}
			}
		}
	}
</script>

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
								class="fa fa-power-off"></i>Logout</a></li>

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
						<li class="nav-item"><a class="nav-link" href="./dashboard"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluate"><i class="fa fa-eye"></i> View and
								Evaluate Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluateSME"><i class="fa fa-eye"></i> View
								and Forward SME applications</a></li>
						<li class="nav-item"><a class="nav-link" href="./queryRaised"><i
								class="fa fa-question-circle"></i> Query </a></li>
						<!-- <li class="nav-item"><a class="nav-link"
							href="./viewQueryResponse"><i class="fa fa-question-circle"></i>
								Query Response By Entrepreneur</a></li> -->
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

				<div class="col-md-9 col-lg-10 mt-4 main">
					<h4
						class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Prepare
						Agenda Note</h4>
					<div class="card card-block p-4 mb-5">
						<div class="step-links">
							<ul class="only-steps">
								<li><a href="./agendaNode" class="active pr-3">Prepare
										Agenda Note for LOC</a></li>
								<li><a href="./agendaNodedis" class="pr-3">Prepare
										Agenda Note for Disbursement </a></li>
							</ul>
						</div>
						<div id="accordion"
							class="accordion mt-3 mb-3 animate__animated animate__pulse">
							<div class="mb-0 border-0">
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									href="#collapseOne">
									<a class="card-title"> <strong> Applications
											included for Agenda Note Preparation - Empowered Committee</strong>
									</a>
								</div>
								<form:form modelAttribute="PrepareAgendaNotes"
									action="prepareAgendaNote" class="mt-4"
									name="PrepareAgendaNotes" method="post"
									onsubmit="return prepareAgendaNoteValidation()">
									<div id="collapseOne" class="card-body collapse"
										data-parent="#accordion">

										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered" id="table1">
														<thead>
															<tr>
																<th></th>
																<th>S.No</th>
																<th>Application ID</th>
																<th>Company Name</th>
																<th>Investment</th>
																<!-- <th>Action</th> -->
																<th width="20%">Upload Document</th>
																<th>Uploaded Document</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="list"
																items="${prepareAgendaNotesMegaLists}"
																varStatus="counter">
																<tr>
																	<td><input type="checkbox"
																		name="prepareAgendaNote" id="prepareAgendaNoteId"
																		value="${list.appliId}"></td>
																	<td class="text-center">${counter.index+1}</td>
																	<td>${list.appliId}</td>
																	<td>${list.companyName}</td>
																	<td>${list.investment}</td>
																	<td>

																		<button type="button"
																			class="btn btn-outline-success btn-sm"
																			id="${list.appliId}" data-target="#AddScrutiny"
																			value="Submit" onClick="selectFileList()"
																			data-toggle="modal">Upload Documents (If
																			Any)</button>

																	</td>
																	<td><c:forEach var="list1"
																			items="${list.scrutinyDocumentList}">
																			<a
																				href="./downloadFiles?fileName=${list1.fileName}&appId=${list1.appId}">
																				<p id="permalink_section">${list1.fileName}</p>
																			</a>

																		</c:forEach></td>

																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
											<div class="col-sm-12 text-right">
												<form:button type="submit" class="common-btn mt-3">Save Agenda Note</form:button>
											</div>
										</div>
									</div>
								</form:form>
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseTwo">
									<a class="card-title"> <strong>Applications
											included for Agenda Note Preparation - Sanction Committee</strong>
									</a>
								</div>

								<form:form modelAttribute="PrepareAgendaNotes"
									action="prepareAgendaNote" class="mt-4"
									name="PrepareAgendaNotes" method="post"
									onsubmit="return preAgenNoSanctionCommVali()">
									<div id="collapseTwo" class="card-body collapse"
										data-parent="#accordion">

										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered" id="table4">
														<thead>
															<tr>
																<th></th>
																<th>S.No</th>
																<th>Application ID</th>
																<th>Company Name</th>
																<th>Investment</th>

																<th width="20%">Upload Document</th>
																<th>Uploaded Document</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="list"
																items="${prearepAgendaNotesLargeLists}"
																varStatus="counter">
																<tr>
																	<td><input type="checkbox"
																		name="prepareAgendaNote" id="prepareAgendaNoteId"
																		value="${list.appliId}"></td>
																	<td class="text-center">${counter.index+1}</td>
																	<td>${list.appliId}</td>
																	<td>${list.companyName}</td>
																	<td>${list.investment}</td>

																	<td>

																		<button type="button"
																			class="btn btn-outline-success btn-sm"
																			id="${list.appliId}"
																			data-target="#AddScrutinySanction" value="Submit"
																			onClick="selectFileList()" data-toggle="modal">Upload
																			Documents (If Any)</button>

																	</td>
																	<td><c:forEach var="list1"
																			items="${list.scrutinyDocumentList}">
																			<a
																				href="./downloadFiles?fileName=${list1.fileName}&appId=${list1.appId}">
																				<p id="permalink_section">${list1.fileName}</p>
																			</a>
																			<br>
																		</c:forEach></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
											<div class="col-sm-12 text-right">
												<form:button type="submit" class="common-btn mt-3">Save Agenda Note</form:button>
											</div>
										</div>

									</div>
								</form:form>
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseThree">
									<a class="card-title"> <strong>Agenda Note
											Approved for Empowered Committee</strong>
									</a>
								</div>
								<form:form modelAttribute="PrepareAgendaNotes"
									action="viewAgendaNoteByMega" class="mt-4"
									name="PrepareAgendaNotes" method="post"
									onsubmit="return approvedAgendaNoteValidation()">
									<div id="collapseThree" class="card-body collapse"
										data-parent="#accordion">

										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered" id="table3">
														<thead>
															<tr>
																<th></th>
																<th>S.No</th>
																<th>Application ID</th>
																<th>Submission Date</th>
																<th>Approval Date</th>
																<th>Action</th>
															</tr>
														</thead>
														<tbody>

															<c:forEach var="list"
																items="${prepareAgendaNotesApprovedMegaLists}"
																varStatus="counter">
																<tr>
																	<td><input type="checkbox"
																		class="agenda-note-checkbox" name="prepareAgendaNote"
																		id="prepareAgendaNoteId" value="${list.appliId}"></td>
																	<td class="text-center">${counter.index+1}</td>
																	<td>${list.appliId}</td>
																	<td>${list.submissionDate}</td>
																	<td>${list.acsapprovalDate}</td>
																	<td><a
																		href="./submitToComitee?applicantId=${list.appliId}"
																		class="btn btn-outline-info btn-sm"
																		onclick="return submitToComitee()">Submit to
																			Commitee </a> <%-- <button type="button" class="btn btn-outline-info btn-sm"
																			id="${list.appliId}" data-target="#CirculateDraft"
																		value="Submit" data-toggle="modal">Circulate Agenda Note Report</button> --%>
																		<a href="javacript:void();" id="${list.appliId}"
																		data-target="#CirculateDraftMega" data-toggle="modal"
																		class="btn btn-outline-info btn-sm">Circulate
																			Agenda Note</a></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
											<div class="col-md-12 mb-4 text-right">
												<!--  	<button type="submit"
													class="common-default-btn disable-btn mt-0"
													id="viewAllAgendaNote">View</button>-->

												<button type="submit" formaction="downloadMega"
													onclick="return approvedAgendaNoteValidation()"
													class="btn btn-outline-success btn-sm">Download
													Agenda Report</button>

												<!-- <a class="btn btn-outline-success btn-sm"
													onclick="return approvedAgendaNoteValidation()"
													href="./downloadMega?applicantID=" + id="applicantID">Download
													Agenda Report</a> -->
											</div>
										</div>

									</div>
								</form:form>
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseFive">
									<a class="card-title"> <strong>Agenda Note
											Approved for Sanction Committee</strong>
									</a>
								</div>
								<form:form modelAttribute="PrepareAgendaNotes"
									action="circulateAgendaNote" class="mt-4"
									name="PrepareAgendaNotes" method="post"
									onsubmit="return approvedAgeNoLargeValidation()">
									<div id="collapseFive" class="card-body collapse"
										data-parent="#accordion">

										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered" id="table4">
														<thead>
															<tr>
																<th></th>
																<th>S.No</th>
																<th>Application ID</th>
																<th>Submission Date</th>
																<th>Approval Date</th>
																<th>Action</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="list"
																items="${prepareAgendaNotesApprovedLargeLists}"
																varStatus="counter">
																<tr>
																	<td><input type="checkbox"
																		name="prepareAgendaNote" id="prepareAgendaNoteId"
																		value="${list.appliId}"></td>
																	<td class="text-center">${counter.index+1}</td>
																	<td id="appId">${list.appliId}</td>
																	<td>${list.submissionDate}</td>
																	<td>${list.approvalDate}</td>
																	<td><a
																		href="./submitToComitee?applicantId=${list.appliId}"
																		class="btn btn-outline-info btn-sm"
																		onclick="return submitToComitee()">Submit to
																			Commitee </a>
																		<button type="button"
																			class="btn btn-outline-info btn-sm"
																			id="${list.appliId}" data-target="#CirculateDraft"
																			value="Submit" data-toggle="modal">Circulate
																			Agenda Note Report</button></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
											<div class="col-md-12 mb-4 text-right">
												<!--  	<button type="submit"
													class="common-default-btn disable-btn mt-0"
													id="viewAllAgendaNote">View</button>-->
												<button type="submit" formaction="downloadLarge"
													onclick="return approvedAgeNoLargeValidation()"
													class="btn btn-outline-success btn-sm">Download
													Agenda Report</button>

												<%-- <a class="btn btn-outline-success btn-sm"
													href="./downloadLarge?fileName=${fileName}"><small>${fileName}</small>Download
													Agenda Report</a> --%>
											</div>
										</div>

									</div>
								</form:form>
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseFour">
									<a class="card-title"> <strong>View Agenda Note</strong>
									</a>
								</div>
								<form:form modelAttribute="PrepareAgendaNotes" class="mt-4"
									name="PrepareAgendaNotes" method="post">
									<div id="collapseFour" class="card-body collapse"
										data-parent="#accordion">

										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered" id="table2">
														<thead>
															<tr>
																<!-- <th></th> -->
																<th>S.No</th>
																<th>Application ID</th>
																<th>Company Name</th>
																<th>Investment</th>
																<!-- <th>Location</th> -->
																<th>Action</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="list"
																items="${prepareAgendaNotesAllPreparedLists}"
																varStatus="counter">
																<tr>
																	<%-- <td><input type="checkbox"
																		name="prepareAgendaNote" id="prepareAgendaNoteId"
																		value="${list.appliId}"></td> --%>
																	<td class="text-center">${counter.index+1}</td>
																	<td>${list.appliId}</td>
																	<td>${list.companyName}</td>
																	<td>${list.investment}</td>
																	<!-- <td></td> -->
																	<td><input type="submit" value="View"
																		class="btn btn-outline-info btn-sm"
																		formaction="./approvingAgendaNote?appliId=${list.appliId}">
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
									data-parent="#accordion" href="#collapseSeven1">
									<a class="card-title"> <strong>View JMD Comments</strong>
									</a>
								</div>

								<div id="collapseSeven1" class="card-body collapse"
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
															items="${prepareAgendaNotesListJMDComment}"
															varStatus="counter">
															<tr>
																<td class="text-center align-middle">${list.appliId}</td>
																<td><textarea class="form-control"
																		disabled="disabled">${list.comments}</textarea></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>

								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseSix">
									<a class="card-title"> <strong>Comment From
											MDPICUP</strong>
									</a>
								</div>
								<div id="collapseSix" class="card-body collapse"
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
															items="${prepareAgendaNotesListMDPICUPComment}"
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
															items="${prepareAgendaNotesListACSComment}"
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


	<div class="modal fade" id="edit-modal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Add Your Note</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<form:form modelAttribute="PrepareAgendaNotes" action="saveNotes"
					class="mt-4" name="PrepareAgendaNotes" method="post">
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group">
									<textarea class="form-control" rows="6" name="notes"
										maxlength="1000"></textarea>
									<form:hidden path="appliId" id="appliId"></form:hidden>
									<form:hidden path="id" id="id"></form:hidden>
									<form:hidden path="companyName" id="companyName"></form:hidden>
									<form:hidden path="investment" id="investment"></form:hidden>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" value="submit" onclick="return addNote()"
							class="common-btn mt-0">
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="edit-modal1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Scrutiny Recommendation by Committee
						(if Any)</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<form:form modelAttribute="PrepareAgendaNotes"
					action="SaveScrutinyDetails" class="mt-4" name="PrepareAgendaNotes"
					method="post" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group">
									<label>Upload Scrutiny Recommendation by Committee</label>
									<div class="custom-file mt-2">
										<input type="file" class="custom-file-input"
											id="AddScrutinyFile" name="pkupFilename"> <label
											for="AddScrutinyFile" id="AddScrutinyFile1"
											class="custom-file-label">Choose File</label>
									</div>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="form-group">
									<label>Add Scrutiny Details (If Any)</label>
									<textarea class="form-control" rows="6"
										name="pkupScrutinyDetail" maxlength="1000"></textarea>
									<form:hidden path="appliId" id="appliId1"></form:hidden>
									<form:hidden path="id" id="id"></form:hidden>
									<form:hidden path="companyName" id="companyName"></form:hidden>
									<form:hidden path="investment" id="investment"></form:hidden>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" value="submit" class="common-btn mt-0">
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="AddScrutiny">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Upload Required Documents</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<form:form modelAttribute="ScrutinyDocument"
					action="SaveScrutinyDocuments" class="mt-4" name="ScrutinyDocument"
					method="post" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<div class="row form-group">
									<div class="col-12 col-md-12">
										<div class="control-group" id="fields">
											<label class="control-label" for="field1"> Upload
												Documents <small class="text-danger">(Upload PDF
													Less than 2 MB)</small>
											</label>
											<div class="controls">
												<div class="entry input-group upload-input-group">
													<input hidden="true" type="text" id="appliId2" name="appId">
													<input class="form-control" name="scrutinyFilename"
														id="scrutinyFilenameId"
														onchange="return ScrutinyFilenameDoc(event)" type="file">
													<!-- filed -->
													<button class="btn btn-upload btn-success btn-add"
														type="button">
														<i class="fa fa-plus"></i>
													</button>

												</div>

											</div>
										</div>
									</div>

								</div>

							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" value="Save" class="common-btn mt-0">
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="AddScrutinySanction">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Upload Required Documents</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<form:form modelAttribute="ScrutinyDocument"
					action="SaveScrutinyDocuments" class="mt-4" name="ScrutinyDocument"
					method="post" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<div class="row form-group">
									<div class="col-12 col-md-12">
										<div class="control-group" id="fields">
											<label class="control-label" for="field1"> Upload
												Documents <small class="text-danger">(Upload PDF
													Less than 2 MB)</small>
											</label>
											<div class="controls">
												<div class="entry input-group upload-input-group">
													<input hidden="true" type="text" id="appliId2" name="appId">
													<input class="form-control" name="scrutinyFilenameSanction"
														id="scrutinyFilenameIdSanction"
														onchange="return ScrutinyFilenameDocSanction(event)"
														type="file">
													<!-- filed -->
													<button class="btn btn-upload btn-success btn-add"
														type="button">
														<i class="fa fa-plus"></i>
													</button>

												</div>

											</div>
										</div>
									</div>

								</div>

							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" value="Save" class="common-btn mt-0">
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="modal fade" id="CirculateDraft" style="display: none;"
			aria-hidden="true">
			<div class="modal-dialog modal-lg">

				<form:form modelAttribute="CirculateToDepartment"
					action="circulateAgendaReport" autocomplete="off" method="POST">
					<div class="modal-content">
						<!-- Modal Header -->
						<div class="modal-header">
							<h4 class="modal-title">Send to Concern Department</h4>
							<button type="button" class="close" data-dismiss="modal">�</button>
						</div>

						<!-- Modal body -->
						<div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<!-- Sachin  -->
									<input type="text" name="AppId" id="appliId3" hidden="">
									<c:forEach items="${concernDepartment}" var="temp">
										<div class="custom-control custom-checkbox">
											<form:checkbox path="deptName"
												value="${temp.deptEmail},${temp.newDeptName},${temp.deptId}|"
												class="custom-control-input" id="${temp.newDeptName}"
												name="${temp.newDeptName}"></form:checkbox>
											<label class="custom-control-label" for="${temp.newDeptName}">${temp.newDeptName}</label>
										</div>
									</c:forEach>
								</div>
							</div>
							<div class="modal-footer">
								<form:button type="submit" class="common-btn mt-3">Submit</form:button>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="modal fade" id="CirculateDraftMega" style="display: none;"
			aria-hidden="true">
			<div class="modal-dialog modal-lg">

				<form:form modelAttribute="CirculateToDepartment"
					action="circulateAgendaReportMega" autocomplete="off" method="POST">
					<div class="modal-content">
						<!-- Modal Header -->
						<div class="modal-header">
							<h4 class="modal-title">Send to Concern Department</h4>
							<button type="button" class="close" data-dismiss="modal">�</button>
						</div>

						<!-- Modal body -->
						<div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<!-- Sachin  -->
									<input type="text" name="AppId" id="appliId4" hidden="">
									<c:forEach items="${concernDepartmentMega}" var="tempMega">
										<div class="custom-control custom-checkbox">
											<form:checkbox path="deptName"
												value="${tempMega.deptEmail},${tempMega.newDeptName},${tempMega.deptId}|"
												class="custom-control-input"
												id="${tempMega.newDeptName},${tempMega.deptEmail}"
												name="${tempMega.newDeptName}"></form:checkbox>
											<label class="custom-control-label"
												for="${tempMega.newDeptName},${tempMega.deptEmail}">${tempMega.newDeptName}</label>
										</div>
									</c:forEach>
								</div>
							</div>
							<div class="modal-footer">
								<form:button type="submit" class="common-btn mt-3">Submit</form:button>
							</div>
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
		//Multi File Upload JS
		$(document)
				.ready(
						function() {
							var i = 0;
							$(function() {
								$(document)
										.on(
												'click',
												'.btn-add',
												function(e) {
													i++;
													var newID = i;
													e.preventDefault();
													var controlForm = $('.controls:first'), currentEntry = $(
															this).parents(
															'.entry:first'), newEntry = $(
															currentEntry
																	.clone())
															.appendTo(
																	controlForm);
													newEntry
															.find('input')
															.attr(
																	"id",
																	"photo_"
																			+ newID
																			+ "")
															.val('');
													controlForm
															.find(
																	'.entry:not(:last) .btn-add')
															.removeClass(
																	'btn-add')
															.addClass(
																	'btn-remove')
															.removeClass(
																	'btn-success')
															.addClass(
																	'btn-danger')
															.html(
																	'<span class="fa fa-trash"></span>');
												}).on(
												'click',
												'.btn-remove',
												function(e) {
													$(this).parents(
															'.entry:first')
															.remove();
													e.preventDefault();
													return false;
												});
							});
						});
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

			$('#edit-modal1').on('show.bs.modal', function(e) {

				var $modal = $(this), esseyId = e.relatedTarget.id;
				document.getElementById("appliId1").value = e.relatedTarget.id;
			})

			$('#AddScrutiny').on('show.bs.modal', function(e) {

				var $modal = $(this), esseyId = e.relatedTarget.id;
				document.getElementById("appliId2").value = e.relatedTarget.id;
			})

			$('#CirculateDraft').on('show.bs.modal', function(e) {
				var $modal = $(this), esseyId = e.relatedTarget.id;
				document.getElementById("appliId3").value = e.relatedTarget.id;
			})

			$('#CirculateDraftMega').on('show.bs.modal', function(e) {
				var $modal = $(this), esseyId = e.relatedTarget.id;
				document.getElementById("appliId4").value = e.relatedTarget.id;
			})

		});
	</script>
</body>
</html>
