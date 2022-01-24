<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Facilities Reliefs Sought</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>


<script type="text/javascript">
	/* $(document).ready(
			function() {
				var IsfClaimReim = $("#ISF_Claim_Reim").val();
				var scst = $("#Add_SCST_Textbox").val();
				var fw = $("#Add_FW_Textbox").val();
				var bpl = $("#Add_BPLW_Textbox").val();
				var exduty = $("#ISF_EX_E_Duty_PC").val();
				var mandiFee = $("#ISF_EX_Mandee_Fee").val();
				var total;

				if ((IsfClaimReim != null || !IsfClaimReim == '')
						|| (scst != null || !scst == '')
						|| (fw != null || !fw == '')
						|| (bpl != null || !bpl == '')
						|| (exduty != null || !exduty == '')
						|| (mandiFee != null || !mandiFee == '')) {
					total = parseInt(IsfClaimReim) + parseInt(scst)
							+ parseInt(fw) + parseInt(bpl) + parseInt(exduty)
							+ parseInt(mandiFee);
				}

				if (isNaN(total)) {
					alert("not a number");
				}

				$("#ISF_total").val(parseInt(total));

			}); */
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
						<li class="nav-item"><a class="nav-link" href="#"><i
								class="fa fa-power-off"></i> Logout</a></li>
						<li class="nav-item"><a class="nav-link active" href="#"><i
								class="fa fa-user"></i> User Name</a></li>
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
							class="nav-link" href="dashboard.html"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
						<li class="nav-item animate__animated animate__bounce"><a
							class="nav-link active" href="select-policy.html"><i
								class="fa fa-eye"></i> View and Evaluate Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="select-policy-SME.html"><i class="fa fa-eye"></i> View
								and Forward SME applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="query-raised.html"><i class="fa fa-question-circle"></i>
								Query Raised</a></li>
						<li class="nav-item"><a class="nav-link"
							href="query-response.html"><i class="fa fa-question-circle"></i>
								Query Response</a></li>
						<li class="nav-item"><a class="nav-link"
							href="prepare-agenda-note.html"><i class="fa fa-list"></i>
								Prepare Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link"
							href="meeting-schedule.html"><i class="fa fa-calendar"></i>
								Schedule meeting</a></li>
						<li class="nav-item"><a class="nav-link" href="mom-go.html"><i
								class="fa fa-calendar"></i> Minutes of Meeting and GO's</a></li>
						<li class="nav-item"><a class="nav-link"
							href="generate-LOC.html"><i class="fa fa-wpforms"></i>
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
											class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">(B).
											Incentive Sought</h4>
									</div>
								</div>
								<hr class="mt-2 mb-5">

								<form:form modelAttribute="incentiveDeatilsData"
									name="incentiveDeatilsform" method="post">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-stripped table-bordered">
													<thead>
														<tr>
															<th>Sr. No.</th>
															<th style="width: 15%;">Details of Incentives Sought
															</th>
															<th style="width: 10%;">Amount of Reliefs <small>(Rs.
																	in crores)</small></th>
															<th>Incentive as per Rules</th>
															<th style="width: 13%;">Justification (if any) given
																by the Company</th>
															<th style="width: 15%;">Remarks</th>
														</tr>
													</thead>


													<tbody>
														<tr>
															<td>1</td>
															<td><strong>Amount of SGST claimed for
																	reimbursement</strong></td>
															<td><form:input path="ISF_Claim_Reim" type="text"
																	class="ISF_Reim_SGST form-control"
																	name="ISF_Claim_Reim" id="ISF_Claim_Reim"
																	maxlength="12" readonly="true"></form:input> <span
																class="text-danger d-inline-block mt-2"></span></td>

															<td><a href="javacript:void();">As per Table –
																	3</a> of Rules related to IIEPP-2017 (The Rules), there is
																a provision for reimbursement of 70% of deposited GST
																(Net SGST) for 10 years.</td>
															<td><textarea class="form-control" disabled="">No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td>
														</tr>

														<tr>
															<td>1.1</td>
															<td>Additional 10% GST where 25% minimum SC/ST
																workers employed subject to minimum of 400 total workers
																in industrial undertakings located in Paschimanchal and
																minimum of 200 total workers in B-P-M</strong>

															</td>
															<td><form:input path="ISF_Reim_SCST" type="text"
																	id="Add_SCST_Textbox"
																	class="ISF_Reim_SGST form-control" name="ISF_Reim_SCST"
																	maxlength="12" readonly="true"></form:input></td>
															<td><a href="javacript:void();">As per Table 3</a>
																of The Rules, there is a provision for 75% Stamp Duty
																Exemption in Paschimanchal Region. <br>Further, as
																per G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there
																is a provision for reimbursement equivalent to the paid
																Stamp Duty, for which company will have to apply before
																concerned GM, DIC.</td>
															<td><textarea class="form-control" disabled="">No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td>
														</tr>

														<tr>
															<td>1.2</td>
															<td>Additional 10% GST where 40% minimum female
																workers employed subject to minimum of 400 total workers
																in industrial undertakings located in Paschimanchal and
																minimum of 200 total workers in B-P-M</td>
															<td><form:input path="ISF_Reim_FW" type="text"
																	id="Add_FW_Textbox" maxlength="12"
																	class="ISF_Reim_SGST form-control" name="ISF_Reim_FW"
																	readonly="true"></form:input></td>
															<td><a href="javacript:void();">As per para 3.3</a>
																and Table 3 of The Rules, there is a provision for
																incentive of reimbursement of EPF to the extent of 50%
																of employer’s contribution to all such new Industrial
																undertakings providing direct employment to 100 or more
																unskilled workers, after three years from the date of
																commercial production for a period of 5 years.</td>
															<td><textarea class="form-control" disabled="">No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td>
														</tr>

														<tr>
															<td>1.3</td>
															<td>Additional 10% GST where 25% minimum BPL workers
																employed subject to minimum of 400 total workers in
																industrial undertakings located in Paschimanchal and
																minimum of 200 total workers in B-P-M</td>
															<td><form:input path="ISF_Reim_BPLW" type="text"
																	class="ISF_Reim_SGST form-control" maxlength="12"
																	name="ISF_Reim_BPLW" id="Add_BPLW_Textbox"
																	readonly="true"></form:input></td>
															<td><a href="javacript:void();">As per para
																	3.5.7</a> of the Rules, there is a following provision: The
																industries which are disallowed for input tax credit
																under the GST regime, will be eligible for reimbursement
																of that amount of GST paid on purchase of plant and
																machinery, building material and other capital goods
																during construction and commissioning period and raw
																materials and other inputs in respect of which input tax
																credit has not been allowed. While calculating the
																overall eligible capital investment such amount will be
																added to the fixed capital investment.</td>
															<td><textarea class="form-control" disabled="">No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td>
														</tr>

														<tr>
															<td>5</td>
															<td>Exemption from Electricity Duty on power drawn
																from power companies for 20 years <br> <strong>Energy/UPPCL/
																	Electricity Safety</strong>

															</td>
															<td><form:input path="ISF_EX_E_Duty_PC" type="text"
																	class="Other_Incentives form-control"
																	id="ISF_EX_E_Duty_PC" name="ISF_EX_E_Duty_PC"
																	maxlength="12" readonly="true"></form:input></td>
															<td><a href="javacript:void();">As per para
																	3.5.5</a> of the Rules, there is a provision for Exemption
																from Electricity Duty for 10 years to all new
																industrialundertakings producing electricity from
																captive power plants for self-use.</td>
															<td><textarea class="form-control" disabled="">No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td>
														</tr>

														<tr>
															<td>6</td>
															<td>Exemption from Mandi Fee for minimum 15 years
																(100% for 20 years) <br> <strong>Agriculture
																	Marketing & Agriculture Foreign Trade/MandiParishad</strong>

															</td>
															<td><form:input path="ISF_EX_Mandee_Fee" type="text"
																	id="ISF_EX_Mandee_Fee"
																	class="Other_Incentives form-control" readonly="true"
																	name="ISF_EX_Mandee_Fee" maxlength="12"></form:input></td>
															<td><a href="javacript:void();">As per para
																	3.5.6</a> of The Rules, There is provision for exemption
																from Mandi Fee for all new food-processing undertakings
																on purchase of raw material for 5 years.</td>
															<td><textarea class="form-control" disabled>No Justification</textarea></td>
															<td><textarea class="form-control"></textarea></td>
														</tr>
														<tr>
															<td colspan="2"></td>
															<td><input id="ISF_total" type="text"
																value="${total}" class="Other_Incentives form-control"
																disabled></input></td>
															<td colspan="4"></td>
														</tr>
													</tbody>
													<%-- 	</form:form> --%>
												</table>
											</div>
										</div>
									</div>


									<c:if test="${not empty incentiveDeatilsData.othAddRequest1}">
										<div class="row">
											<div class="col-sm-12">
												<div class="form-group">
													<label>Additional Request</label>
													<form:input path="othAddRequest1" type="text"
														class="form-control" placeholder="" maxlength="250"
														name="othAddRequest1"></form:input>
												</div>
											</div>
										</div>
									</c:if>
								</form:form>

								<hr class="mt-2">
								<div class="row">
									<div class="col-sm-5">
										<a href="./evaluateApplication"
											class="common-default-btn mt-3">Back</a>
									</div>
									<div class="col-sm-7 text-right">
										<a href="javacript:void(0);" class="common-btn mt-3"><i
											class="fa fa-edit"></i> Edit</a> <a href="evaluation-view.html"
											class="common-btn mt-3">Save & Next</a>
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