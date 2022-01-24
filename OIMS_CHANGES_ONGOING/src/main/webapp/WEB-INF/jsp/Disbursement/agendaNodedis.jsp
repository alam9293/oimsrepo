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
<title>Dis Prepare Agenda Note</title>

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
								class="fa fa-power-off"></i>Logout</a></li>

						<li class="nav-item"><a class="nav-link active" href="#"><i
								class="fa fa-user"></i>${userName}</a></li>

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
					<h4 class="card-title mb-4 mt-4 text-center">Prepare Agenda
						Note</h4>
					<div class="card card-block p-4 mb-5 prepare-agenda-note-tabs">

						<div class="step-links">
							<ul class="only-steps">
								<li><a href="./agendaNode" class="pr-3">Prepare Agenda
										Note for LOC</a></li>
								<li><a href="./agendaNodedis" class="active pr-3">Prepare
										Agenda Note for Disbursement </a></li>
							</ul>
						</div>

						<!-- Tab panes -->
						<div id="accordion"
							class="accordion mt-3 mb-3 animate__animated animate__pulse">
							<div class="mb-0 border-0">
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									href="#collapseOne">
									<a class="card-title"> <strong>Applications
											included for Agenda Note Preparation - Empowered Committee</strong>
									</a>
								</div>
								<form:form modelAttribute="PrepareAgendaNotesDis"
									action="prepareAgendaNoteDis" class="mt-4"
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
																<th>Action</th>
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
																	<td><a
																		href="./viewAgendaDetails?applicantId=${list.appliId}"
																		class="btn btn-outline-info btn-sm">View</a> <a
																		href="#AddNote" data-toggle="modal"
																		id="${list.appliId}" data-target="#edit-modal"
																		class="btn btn-outline-success btn-sm">Add Note</a> <a
																		href="#AddNote" data-toggle="modal"
																		id="${list.appliId}" data-target="#edit-modal1"
																		class="btn btn-outline-success btn-sm">Add
																			Scrutiny Committee Comments</a></td>

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

								<form:form modelAttribute="PrepareAgendaNotesDis"
									action="prepareAgendaNoteDis" class="mt-4"
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
																<th>Action</th>
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
																	<td><a
																		href="./viewAgendaDetails?applicantId=${list.appliId}"
																		class="btn btn-outline-info btn-sm">View</a> <a
																		href="#AddNote" data-toggle="modal"
																		id="${list.appliId}" data-target="#edit-modal"
																		class="btn btn-outline-success btn-sm">Add Note</a> <a
																		href="#AddNote" data-toggle="modal"
																		id="${list.appliId}" data-target="#edit-modal1"
																		class="btn btn-outline-success btn-sm">Add
																			Scrutiny Committee Comments</a></td>
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
																	<td><fmt:formatDate pattern = "dd-MM-yyyy" value = "${list.submissionDate}" /></td>
																	  <td><fmt:formatDate pattern = "dd-MM-yyyy" value = "${list.acsapprovalDate}" /></td>  
																	<%-- <td>${acsapprovalDate}</td> --%>
																	<td><a
																		href="./submitToComiteeDis?applicantId=${list.appliId}"
																		class="btn btn-outline-info btn-sm"
																		onclick="return submitToComitee()">Submit to
																			Commitee </a> <a href="javacript:void();"
																		onclick="return circulateAgendaNote()"
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
													
													<a  class="btn btn-outline-success btn-sm" href="./downloadDisMega"><small> </small>Download Agenda Report</a>
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
																	<td>${list.appliId}</td>
																	<%--<td> ${list.submissionDate}</td> --%>
																	<%-- <td>${submissionDate}</td> --%>
																	 <td><fmt:formatDate pattern = "dd-MM-yyyy" value = "${list.submissionDate}" /></td>
																	  <td><fmt:formatDate pattern = "dd-MM-yyyy" value = "${list.approvalDate}" /></td>  
																	<%-- <td>${approvalDate}</td> --%>
																	<td><input type="submit" value="View"
																		class="btn btn-outline-info btn-sm"> <a
																		href="./submitToComiteeDis?applicantId=${list.appliId}"
																		class="btn btn-outline-info btn-sm">Submit to
																			Commitee </a> <a href="javacript:void();"
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
													
													<a  class="btn btn-outline-success btn-sm" href="./downloadLarge?fileName=${fileName}"><small>${fileName}</small>Download Agenda Report</a>
											</div>
										</div>

									</div>
								</form:form>
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									data-parent="#accordion" href="#collapseFour">
									<a class="card-title"> <strong>View Agenda Note</strong>
									</a>
								</div>
								
								
								
								<form:form modelAttribute="PrepareAgendaNotesDis"
									action="approvingAgendaNoteDis" class="mt-4"
									name="PrepareAgendaNotes" method="post"
									onsubmit="return preparedAgendaNoteValidation()">
									<div id="collapseFour" class="card-body collapse"
										data-parent="#accordion">

										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered" id="table2">
														<thead>
															<tr>
																<th></th>
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
																	<td><input type="checkbox"
																		name="prepareAgendaNote" id="prepareAgendaNoteId"
																		value="${list.appliId}"></td>
																	<td class="text-center">${counter.index+1}</td>
																	<td>${list.appliId}</td>
																	<td>${list.companyName}</td>
																	<td>${list.investment}</td>
																	<!-- <td></td> -->
																	<td><input type="submit" value="View"
																		class="btn btn-outline-info btn-sm"></td>
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
									<a class="card-title"> <strong>COMMENT FROM JMD</strong>
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
																		disabled="disabled">${list.jmdComment}</textarea></td>
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
																		disabled="disabled">${list.mdComments}</textarea></td>
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
																		disabled="disabled">${list.acsComments}</textarea></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
								<div class="card-header mb-4 collapsed" data-toggle="collapse" data-parent="#accordion" href="#IIDC" aria-expanded="false">
                              <a class="card-title"> <strong>Comment From IIDC</strong>
                              </a>
                            </div>

                            <div id="IIDC" class="card-body collapse" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
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
                                          <tr>
                                            <td>&nbsp;</td>
                                            <td></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                      </div>
                                  </div>
                                  <div class="col-md-12">
                                  <ul class="pagination pull-right">
                                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                                  </ul>
                                </div>
                              </div>

                            </div>


                            <div class="card-header mb-4 collapsed" data-toggle="collapse" data-parent="#accordion" href="#SO-ID6" aria-expanded="false">
                              <a class="card-title"> <strong>Comment From SO-ID6</strong>
                              </a>
                            </div>

                            <div id="SO-ID6" class="card-body collapse" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
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
                                          <tr>
                                            <td>&nbsp;</td>
                                            <td></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                      </div>
                                  </div>
                                  <div class="col-md-12">
                                  <ul class="pagination pull-right">
                                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                                  </ul>
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

		<div class="modal fade" id="AddScrutiny">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Upload Required Documents</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
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
													<input class="form-control" name="fields[]" type="file">
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
				</div>
			</div>
		</div>

		<!-- The Modal -->
		<div class="modal fade" id="CreateIndex">
			<div class="modal-dialog modal-xl">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title text-center">Agenda items for
							5thEmpowered Committee Meeting under Industrial Investment and
							Employment Promotion Policy-2017(IIEPP-2017)to be held on
							10thOctober 2019</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-12 text-center">
								<h4 class="card-title mb-4 mt-4 text-center l-space">I N D
									E X</h4>
							</div>
							<div class="col-sm-12">
								<div class="table-responsive">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>Sr. No.</th>
												<th>Particular</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>1.</td>
												<td>Confirmation of minutes of 4thEC Meeting held on
													14.8.2019</td>
											</tr>
											<tr>
												<td>2.</td>
												<td><strong>M/s Pepsico India Holdings Pvt.
														Ltd.</strong> – Setting up a unit for diversification of various
													savory/namkeen products at existing unit at KosiKatwan
													Industrial Park, Phase II, Mathura (Paschimanchal Region)
													with an investment of <strong>Rs. 514.00 crores.</strong></td>
											</tr>
											<tr>
												<td>3.</td>
												<td><strong>M/s Triveni Engineering &
														Industries Ltd.</strong> –Diversification by setting up a
													Distillery Project alongwith spent wash based incineration
													boiler to achieve Zero Liquid Discharge (ZLD) adjacent to
													its existing sugar unit at Khurja, Distt. Bulandshahr
													(Paschimanchal Region) with an investment of <strong>Rs.
														175.00 crores.</strong></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-outline-danger btn-sm"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-outline-success btn-sm">Download
							PDF</button>
					</div>

				</div>
			</div>
		</div>

		<!-- The Modal -->
		<div class="modal fade" id="AddItemName">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Add Item Header Name</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-12">
								<div class="row mb-4">
									<div class="col-sm-12">
										<div class="form-group">
											<label>Item Header Name</label>
											<textarea placeholder="Enter Item Heading Name"
												class="form-control" name=""></textarea>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-outline-danger btn-sm"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-outline-success btn-sm">Submit</button>
					</div>

				</div>
			</div>
		</div>


		<!-- The Modal -->
		<div class="modal fade" id="AddHeaderNote">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Add Header Note</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group">
									<label>Header Name</label>
									<textarea placeholder="Enter Header Name" rows="5"
										class="form-control" name=""></textarea>
								</div>
							</div>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-outline-danger btn-sm"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-outline-success btn-sm">Submit</button>
					</div>

				</div>
			</div>
		</div>


		<div class="modal fade" id="CirculateDraft">
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
 <script type="text/javascript">

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
 </script>
</body>
</html>