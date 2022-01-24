<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="date" class="java.util.Date" />

<!doctype html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Generate LOC</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.debug.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				var pdf = new jsPDF("l", "pt", "a4");
				pdf.internal.scaleFactor = 1.3;
				var options = {
					pagesplit : true
				};
				$('#formId').click(
						function() {
							pdf.addHTML(document.getElementById("content2"),
									options, function() {
										pdf.save("LocForm.pdf");
									});
						});
			});
</script>
<style type="text/css">
@media screen , print {
	body {
		background: #fff;
	}
	#content2 {
		background: #fff;
		padding: 20px;
	}
}
</style>
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
							class="nav-link " href="./dashboard"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluate"><i class="fa fa-eye"></i> View and
								Evaluate Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluateSME"><i class="fa fa-eye"></i> View
								and Forward SME applications</a></li>
						<li class="nav-item"><a class="nav-link" href="./queryRaised"><i
								class="fa fa-question-circle"></i> Query Raised</a></li>
						<!-- <li class="nav-item"><a class="nav-link"
							href="./viewQueryResponse"><i class="fa fa-question-circle"></i>Query
								Response By Entrepreneur</a></li> -->
						<li class="nav-item"><a class="nav-link" href="./agendaNode"><i
								class="fa fa-list"></i> Prepare Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./meetingschedule"><i class="fa fa-calendar"></i>
								Schedule meeting</a></li>
						<li class="nav-item"><a class="nav-link" href="./momgo"><i
								class="fa fa-calendar"></i> Minutes of Meeting and GO's</a></li>
						<li class="nav-item animate__animated animate__bounce"><a
							class="nav-link active" href="./generateLoc"><i
								class="fa fa-wpforms"></i> Generate LOC</a></li>
					</ul>
				</div>
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<div class="card card-block draft-box">


						<div class="row">
							<div class="col-sm-12 text-right">
								<a href="javascript:void();" id="formId"
									class="btn btn-outline-success btn-sm">Download LOC</a>
							</div>
							
							<div class="col-sm-12">
								<div class="mt-4">
									<div class="without-wizard-steps">
										<h4
											class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">D
											R A F T</h4>
									</div>
								</div>
								<hr class="mt-2 mb-3">

							</div>
						</div>
						<div id="content2">
							<div class="row mb-4">
								<div class="col-sm-12 text-center">
									<h4 class="secondary-heading">LETTER OF COMFORT</h4>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-9">
									<p>
										<label>Ref.No.: <font
											face="Arial,Helvetica,Sans serif" size="2.8"><b>${viewSignatoryDetail.appId }</b></font></label>
									</p>
									<p>
										<label>M/s</label> <font face="Arial,Helvetica,Sans serif"
											size="2.8"><b>${businessEntityName}</b></font>
									</p>
								</div>
								<div class="col-sm-3">
									<p>
										<label>Dated:</label> <b><fmt:formatDate value="${date}"
												pattern="dd-MM-yyyy" /></b>
									</p>
								</div>
							</div>

							<div class="row">
								<div class="col-md-8 offset-md-2">
									<p>
										<strong>Re: Letter of Comfort for special
											facilities/reliefsunder Rules for Uttar Pradesh Industrial
											Investment & Employment Promotion Policy - 2017</strong>
									</p>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-12">

									<p>Dear Sirs,</p>

									<p class="indent">
										This is with reference to your application dated <b><fmt:formatDate
												type="date" dateStyle="short" pattern="dd-MM-yyyy"
												value="${createdate}" /></b> and subsequent discussions
										requesting for grant of special incentives under Rules for
										Uttar Pradesh Industrial Investment & Employment Promotion
										Policy - 2017 (hereinafter referred to as Rules - 2017) for
										your proposed New/Expansion/Diversification project for the
										manufacture of <b>${projectDescription}</b> with an installed
										capacity of <b>${existingInstalledCapacity}</b> with a capital
										investment of Rs. <b>${fci1}</b> crores at - <b>
											${fullAddress}</b> Distt : <b> ${districtName}</b> (Region : <b>
											${resionName}</b>) in the State of Uttar Pradesh under the G.O.
										No. <b>${meeting}</b>. dated 25.10.2017, as amended from
										time to time.
									</p>

									<p class="indent">
										In this connection, it is to inform that your request for
										grant of special incentives (as per Rules - 2017) has been
										considered and approved vide G.O. No. <font
											face="Arial,Helvetica,Sans serif" size="3"><b>${meeting}</b></font>
										dated <font face="Arial,Helvetica,Sans serif" size="3"><b>26-10-2017</b></font>
										(photocopy enclosed),
									</p>

									<p class="indent">
										On fulfilment of requirement as stipulated in G.O. No.
										1359/77-6-17-5(M)/17 dated 25.10.2017 and G.O. No. <font
											face="Arial,Helvetica,Sans serif" size="3"><b>${meeting}</b></font>
										dated <font face="Arial,Helvetica,Sans serif" size="3"><b>26-10-2017</b></font>,
										as amended from time to time, the above proposed project shall
										be entitled for consideration of grant of the following
										facilities/ reliefs:-
									</p>

								</div>
							</div>

							<div class="row">
								<div class="col-md-12">
									<div class="table-responsive">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th style="width: 7%;">S. No.</th>
													<th>Facilities/Reliefs granted as per G.O. dated
														29.1.2020</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>1</td>
													<td>Reimbursement of deposited GST (after deduction of
														Input Tax Credit) <a
														href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
														target="_blank">As per para 3.2</a> of Rules-2017
													</td>
												</tr>
												<tr>
													<td>2</td>
													<td>Stamp Duty Exemption <a
														href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
														target="_blank">As per para 3.1</a> of Rules-2017
													</td>
												</tr>
												<tr>
													<td>3</td>
													<td>EPF Reimbursement <a
														href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
														target="_blank">As per para 3.3</a> of Rules-2017
													</td>
												</tr>
												<tr>
													<td>4</td>
													<td>Capital Interest Subsidy <a
														href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
														target="_blank">As per para 3.5.1</a> of Rules-2017
													</td>
												</tr>
												<tr>
													<td>5</td>
													<td>Infrastructure Interest Subsidy <a
														href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
														target="_blank">As per para 3.5.2</a> of Rules-2017
													</td>
												</tr>
												<tr>
													<td>6</td>
													<td>Interest Subsidy on loans for industrial research,
														quality improvement, etc. <a
														href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
														target="_blank">As per para 3.5.3</a> of Rules-2017
													</td>
												</tr>
												<tr>
													<td>7</td>
													<td>Exemption from Electricity duty on power drawn
														from power companies <a
														href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
														target="_blank">As per para 3.5.4</a> of Rules-2017
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-12">
									<p class="indent">
										The above facilities/reliefs shall be provided to the company
										subject to the fulfilment of entire requirement as stipulated
										in G.O. No. 1359/77-6-17-5(M)/17 dated 25.10.2017 and G.O. No.
										<font face="Arial,Helvetica,Sans serif" size="3"><b>${meeting}</b></font>
										dated <font face="Arial,Helvetica,Sans serif" size="3"><b>26-10-2017</b></font>,
										as amended from time to time and subject to the following
										terms & conditions:-
									</p>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-12">
									<ol class="p-0 m-0 pl-2">
										<li>
											<p>The company shall submit a copy of Appraisal Note
												prepared by a Scheduled Commercial Bank (except Regional
												Rural Bank) or Financial Institution controlled by these
												banks or Central Government within six months from the date
												of issuance of Sanction Letter/LOC. The Appraisal will be
												required to be got done by the industrial undertaking from
												one of the above-mentioned agencies even if no loan is being
												availed by it from any financial institution/Bank.</p>
										</li>
										<li>
											<p>For correct assessment of the capital investment, the
												same shall have to be certified by Authorized Director and
												Statutory Auditor of the company. Based on the
												certificatesprovided by the company, Nodal Agency (PICUP)
												shall get assessment & verification of the factual position
												of capital investment through its empanelled
												Valuers/CA/Consultants.</p>
										</li>
										<li>
											<p>Company shall manufacture the goods at its above
												proposed location, for which it is eligible for incentives.</p>
										</li>
										<li>
											<p>The company shall furnish all the information as asked
												by Nodal Agency (PICUP) or Government of Uttar Pradesh from
												time to time as a condition for disbursement, viz. detailed
												particulars of production, sale, stoppages in production, if
												any, closure of unit, etc. with clear reasons for same,
												certified particulars of increase in fixed capital
												investment, if any, sale/loss of fixed assets, if any, and
												change in constitution of the unit, audited Statements of
												Accounts and balance sheet of eligible unit within 6 months
												of close of each financial year, etc.</p>
										</li>
										<li>
											<p>The company shall be required to reimburse
												Administrative expenses equivalent to 1.50% of the amount of
												benefits sanctioned, to the concerned Nodal Agency, which
												shall be deducted from the disbursement. In addition, the
												expenses incurred towards verification of capital investment
												made by industrial undertakings through empanelled Valuers
												and Chartered Accountants would be borne by applicant
												companies on actual basis.</p>
										</li>
										<li>
											<p>Application for any modifications/changes in nature of
												the project, or in the cost of project so as to effect
												change in its category, or for changes in LOC conditions,
												etc. will be made by the company and will be examined by
												Nodal Agency (PICUP) on its own or through an outside
												competent agency and will be placed before the sanctioning
												authority, whose decision will be final.</p>
										</li>
										<li>
											<p>The disputed matters or clarification required related
												to implementation of the Rules - 2017 shall be referred to
												PICUP. If the dispute remains unresolved the same shall be
												referred to Empowered Committee, whose decision shall be
												final.</p>
										</li>
										<li>
											<p>The right to clarify any subject matter and approval
												to carry out modification in the scheme shall rest with the
												Empowered Committee.</p>
										</li>
										<li>
											<p>If any information submitted by the company is found
												to be false, or benefits are found to have been drawn based
												on concealment of material facts, the LOC shall be
												cancelled, and all benefits released to the undertaking
												shall become recoverable under the applicable state laws as
												arrears of land revenue.</p>
										</li>
										<li>
											<p>
												In case of violation of any of the conditions of this Letter
												of Comfort, G.O. No. 1359/77-6-17-5(M)/17 dated 25.10.2017
												and G.O. No. <font face="Arial,Helvetica,Sans serif"
													size="3"><b>${meeting}</b></font> dated <font
													face="Arial,Helvetica,Sans serif" size="3"><b>26-10-2017</b></font>,
												as amended from time to time, the Empowered Committee shall
												have the right to cancel the Letter of Comfort and shall
												initiate action to recall the benefits availed by the
												company under the Rules-2017.
											</p>
										</li>
										<li>
											<p>Upon achieving the prescribed limits of benefits
												(quantum/period), or contravention in terms and conditions,
												the LOC would automatically be treated as cancelled.</p>
										</li>
										<li>
											<p>The representatives of Government/Nodal Agency (PICUP)
												may visit the proposed site and office of the company and
												call for the records pertaining to the project for
												perusal/verification at any time during operation of the
												scheme. The company shall facilitate/arrange all such visits
												as and when it is required.</p>
										</li>
										<li>
											<p>For the purpose of interpretation of any clauses of
												this letter, reference shall be made to the relevant
												Government Orders.</p>
										</li>
										<li>
											<p>Company will require to submit the progress report of
												the project on quarterly basis to the Nodal Agency (PICUP).</p>
										</li>
									</ol>
								</div>
							</div>

							<div class="row">
								<div class="col-md-12">
									<p class="indent">You are requested to kindly return the
										duplicate copy of this LoC (enclosed) as a token of acceptance
										of the terms & conditions contained hereinabove.</p>
								</div>
							</div>

							<div class="row">
								<div class="col-md-12">
									<p class="text-right">Yours faithfully,</p>
								</div>
							</div>

							<div class="row">
								<div class="col-md-12">
									<p>Enclosure: As above.</p>
								</div>
							</div>

							<div class="row">
								<div class="col-md-12">
									<p class="text-right">
										<font face="Arial,Helvetica,Sans serif" size="5">Smt.
											Sujata Sharma</font>
									</p>
									<p class="text-right">Managing Director/Joint Managing
										Director</p>
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
		<div class="modal fade" id="LOCgenerated">
			<div class="modal-dialog">
				<div class="modal-content">

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">×</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group success-msg">
									<label><i class="fa fa-check-circle"></i></label>
									<p>LOC has been generated successfully.</p>
								</div>
							</div>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer footer-center-btn">
						<a href="dashboard.html" class="common-btn mt-0">OK</a>
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