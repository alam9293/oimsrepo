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
<title>Evaluation View</title>
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
	<script
src="https://rawgit.com/jackmoore/autosize/master/dist/autosize.min.js"></script>
<script src="js/custom.js"></script>

<script>
	$(document).ready(function() {
		autosize(document.getElementsByClassName("form-control"));
	});
</script>

<script type="text/javascript">

$(function() {
	var ctypeSMALLselect = '${ctypeSMALL}';

	if (ctypeSMALLselect == 'No') {
		$(".in-case-of-small-row").hide();
	}

	var ctypeLARGEselect = '${ctypeLARGE}';
	if (ctypeLARGEselect == 'No') {
		$(".in-case-of-large-row").hide();
	}

	var ctypeMEGAselect = '${ctypeMEGA}';
	if (ctypeMEGAselect == 'No') {
		$(".in-case-of-mega-row").hide();
	}

	var ctypeSUPERMEGAselect = '${ctypeSUPERMEGA}';
	if (ctypeSUPERMEGAselect == 'No') {
		$(".in-case-of-supermega-row").hide();
	}

	var phase = '${phsWsAply}';

	if (phase == 'No') {
		$(".phase").hide();
	}

	var ctype = '${category}';
	if (ctype == 'Mega' || ctype == 'Mega Plus' || ctype == 'Super Mega') {
		document.getElementById("percent").innerHTML = '300';

	} else {
		document.getElementById("percent").innerHTML = '100';
		$(".in-case-of-inMMSM-row").hide();
	}

	var regionName = '${region}';
	var districtName = '${district}';

	if (regionName == 'Bundelkhand' || regionName == 'Poorvanchal') {
		document.getElementById("sdepercent").innerHTML = '100';
		document.getElementById("sdrpercent").innerHTML = '100';

	}
	if (regionName == 'Paschimanchal') {
		document.getElementById("sdepercent").innerHTML = '75';
		document.getElementById("sdrpercent").innerHTML = '75';
	}
	if (regionName == 'Madhyanchal') {
		document.getElementById("sdepercent").innerHTML = '75';
		document.getElementById("sdrpercent").innerHTML = '75';

	}

	if (districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD') {
		document.getElementById("sdepercent").innerHTML = '50';
		document.getElementById("sdrpercent").innerHTML = '50';
	}

});

	window.onload = function() {
		if ('${flag}' === 'false') {
			document.getElementById('showHide1').style.display = 'none';
			document.getElementById('showHide2').style.display = 'none';
		} else {
			document.getElementById('showHide1').style.display = 'block';
			document.getElementById('showHide2').style.display = 'block';
		}


    
  if('${flag1}'==='false1'){
    	  document.getElementById('showHide3').style.display ='table-row';
    	 
         }
      else
    	{
    	  //document.getElementById('showHide3').style.display ='none';
    	  
    	}
    
  if('${flag1}'==='false1'){
    	  document.getElementById('showHide3').style.display ='table-row';
    	 
         }
      else
    	{
    	  document.getElementById('showHide3').style.display ='none';
    	  
    	}
		if ('${flag1}' === 'false1') {
			document.getElementById('showHide3').style.display = 'table-row';

		} else {
			document.getElementById('showHide3').style.display = 'none';

		}

	}

	function preparedAgendaNoteValidation() {
		<c:if test="${empty flag}">
		alert("Please select Applicant Record.");
		return false
		</c:if>

	}
	function validate(value) {
		document.getElementById("appliId").value = value;
	}
	$(document).ready(CostasperDPR);
	$(document).on("keyup", CostasperDPR);

	function CostasperDPR() {
		var sum = 0;
		$(".CostasperDPR").each(function() {
			sum += +$(this).val();
		});
		$(".TotalCostasperDPR").val(sum);
	}

	$(document).ready(CostasperIIEPP);
	$(document).on("keyup", CostasperIIEPP);

	function CostasperIIEPP() {
		var sum = 0;
		$(".CostasperIIEPP").each(function() {
			sum += +$(this).val();
		});
		$(".TotalCostasperIIEPP").val(sum);
	}

	$(document).ready(MFCostasperDPR);
	$(document).on("keyup", MFCostasperDPR);

	function MFCostasperDPR() {
		var sum = 0;
		$(".MFCostasperDPR").each(function() {
			sum += +$(this).val();
		});
		$(".TotalMFCostasperDPR").val(sum);
	}

	$(document).ready(MFCostasperIIEPP);
	$(document).on("keyup", MFCostasperIIEPP);

	function MFCostasperIIEPP() {
		var sum = 0;
		$(".MFCostasperIIEPP").each(function() {
			sum += +$(this).val();
		});
		$(".TotalMFCostasperIIEPP").val(sum);
	}

	function Save() {
		var r = confirm("Are you Sure Want to Save the Prepare Agenda Note?");

		if (r == true) {
			alert("Prepare Agenda Note Saved Successfully");
		} else {
			return false
		}

	}

	function showYesOrNo1() {
		var natureOfProject = '${natureOfProject}';
		if (natureOfProject === 'NewProject') {

			$(".hide-tbl-row").hide();
		}

		else
			$(".hidep-tbl-row").hide();

	}
	$(document).on("change", showYesOrNo1);
	$(document).ready(showYesOrNo1);

	function showYesOrNo2() {
		var ISF_Claim_Reim = '${incentiveDeatilsData.ISF_Claim_Reim}';
		if (ISF_Claim_Reim == '') {
			//alert("hi");
			$(".ISF_Claim_Reim-row").hide();
		}
		var ISF_Reim_SCST = '${incentiveDeatilsData.ISF_Reim_SCST}';
		//if( iSF_Reim_BPLW == '')
		if (ISF_Reim_SCST == '') {
			//alert(ISF_Reim_SCST);
			$(".ISF_Reim_SCST-row").hide();
		}

		var ISF_Reim_FW = '${incentiveDeatilsData.ISF_Reim_FW}';
		if (ISF_Reim_FW == '') {
			//alert("hi");
			$(".ISF_Reim_FW-row").hide();
		}

		var iSF_Reim_BPLW = '${incentiveDeatilsData.ISF_Reim_BPLW}';
		if (iSF_Reim_BPLW == '') {
			//alert("hi");
			$(".ISF_Reim_BPLW-row").hide();
		}

		var ISF_Stamp_Duty_EX = '${incentiveDeatilsData.ISF_Stamp_Duty_EX}';
		if (ISF_Stamp_Duty_EX == '') {
			//alert("hi");
			$(".ISF_Stamp_Duty_EX-row").hide();
		}

		var ISF_Amt_Stamp_Duty_Reim = '${incentiveDeatilsData.ISF_Amt_Stamp_Duty_Reim}';
		if (ISF_Amt_Stamp_Duty_Reim == '') {
			//alert("hi");
			$(".ISF_Amt_Stamp_Duty_Reim-row").hide();
		}

		var ISF_Additonal_Stamp_Duty_EX = '${incentiveDeatilsData.ISF_Additonal_Stamp_Duty_EX}';
		if (ISF_Additonal_Stamp_Duty_EX == '') {
			//alert("hi");
			$(".ISF_Additonal_Stamp_Duty_EX-row").hide();
		}

		var ISF_Epf_Reim_UW = '${incentiveDeatilsData.ISF_Epf_Reim_UW}';
		if (ISF_Epf_Reim_UW == '') {
			//alert("hi");
			$(".ISF_Epf_Reim_UW-row").hide();
		}

		var ISF_Add_Epf_Reim_SkUkW = '${incentiveDeatilsData.ISF_Add_Epf_Reim_SkUkW}';
		if (ISF_Add_Epf_Reim_SkUkW == '') {
			//alert("hi");
			$(".ISF_Add_Epf_Reim_SkUkW-row").hide();
		}

		var ISF_Add_Epf_Reim_DIVSCSTF = '${incentiveDeatilsData.ISF_Add_Epf_Reim_DIVSCSTF}';
		if (ISF_Add_Epf_Reim_DIVSCSTF == '') {
			//alert("hi");
			$(".ISF_Add_Epf_Reim_DIVSCSTF-row").hide();
		}

		var ISF_Cis = '${incentiveDeatilsData.ISF_Cis}';
		if (ISF_Cis == '') {
			//alert("hi");
			$(".ISF_Cis-row").hide();
		}

		var ISF_ACI_Subsidy_Indus = '${incentiveDeatilsData.ISF_ACI_Subsidy_Indus}';
		if (ISF_ACI_Subsidy_Indus == '') {
			//alert("hi");
			$(".ISF_ACI_Subsidy_Indus-row").hide();
		}

		var ISF_Infra_Int_Subsidy = '${incentiveDeatilsData.ISF_Infra_Int_Subsidy}';
		if (ISF_Infra_Int_Subsidy == '') {
			//alert("hi");
			$(".ISF_Infra_Int_Subsidy-row").hide();
		}

		var ISF_AII_Subsidy_DIVSCSTF = '${incentiveDeatilsData.ISF_AII_Subsidy_DIVSCSTF}';
		if (ISF_AII_Subsidy_DIVSCSTF == '') {
			//alert("hi");
			$(".ISF_AII_Subsidy_DIVSCSTF-row").hide();
		}

		var ISF_Loan_Subsidy = '${incentiveDeatilsData.ISF_Loan_Subsidy}';
		if (ISF_Loan_Subsidy == '') {
			//alert("hi");
			$(".ISF_Loan_Subsidy-row").hide();
		}

		var ISF_Tax_Credit_Reim = '${incentiveDeatilsData.ISF_Tax_Credit_Reim}';
		if (ISF_Tax_Credit_Reim == '') {
			//alert("hi");
			$(".ISF_Tax_Credit_Reim-row").hide();
		}

		var ISF_EX_E_Duty = '${incentiveDeatilsData.ISF_EX_E_Duty}';
		if (ISF_EX_E_Duty == '') {
			//alert("hi");
			$(".ISF_EX_E_Duty-row").hide();
		}

		var ISF_EX_E_Duty_PC = '${incentiveDeatilsData.ISF_EX_E_Duty_PC}';
		if (ISF_EX_E_Duty_PC == '') {
			//alert("hi");
			$(".ISF_EX_E_Duty_PC-row").hide();
		}

		var ISF_EX_Mandee_Fee = '${incentiveDeatilsData.ISF_EX_Mandee_Fee}';
		if (ISF_EX_Mandee_Fee == '') {
			//alert("hi");
			$(".ISF_EX_Mandee_Fee-row").hide();
		}

		var ISF_Indus_Payroll_Asst = '${incentiveDeatilsData.ISF_Indus_Payroll_Asst}';
		if (ISF_Indus_Payroll_Asst == '') {
			//alert("hi");
			$(".ISF_Indus_Payroll_Asst-row").hide();
		}
	}
	$(document).ready(showYesOrNo2);

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
						<li class="nav-item"><a class="nav-link" href="./dashboard"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluate"><i class="fa fa-eye"></i> View and
								Evaluate Applications</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewAndEvaluateSME"><i class="fa fa-eye"></i> View
								and Forward SME applications</a></li>
						<li class="nav-item"><a class="nav-link" href="./queryRaised"><i
								class="fa fa-question-circle"></i> Query Raised</a></li>
						<!--  <li class="nav-item"><a class="nav-link" href="./viewQueryResponse"><i class="fa fa-question-circle"></i> Query Response By Entrepreneur</a></li> -->
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
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-5">
								<a href="./agendaNode" class="common-default-btn mt-3">Back</a>
							</div>
						</div>


						<form:form modelAttribute="PrepareAgendaNotes" class="mt-4"
							name="PrepareAgendaNotes" method="get">
							<form:hidden path="prepareAgendaNote" id="prepareAgendaNote"></form:hidden>
							<form:hidden path="appliId" id="appliId"></form:hidden>
							<div class="row mt-5">
								<div class="col-sm-12">
									<div class="table-responsive">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>Application ID</th>
													<th>Company Name</th>
													<th>Type </th>
													<th>Product</th>
													<th>Category</th>
													<th>Investment</th>
													<th>Location</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="list" items="${investmentDetailsmixedList}"
													varStatus="counter">
													<tr>
														<td>${list.appliId}</td>
														<td>${list.companyName}</td>
														<td><c:out value="${natureOfProject}" /></td>
														<td><c:out value="${products}" /></td>
														<td>${list.categoryIndsType}</td>
														<td>${list.investment}</td>
														<td>${list.district},${list.region}</td>
														<td><input type="submit" value="View"
															class="btn btn-outline-info btn-sm"
															formaction="./dispalyAgendaNote?appliId=${list.appliId}"
															onclick="return validate('${list.appliId}')"></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</form:form>
						
						<div class="row" id="showHide1">
							<div class="col-sm-12">
								<hr class="mt-2">
								<c:import url="/WEB-INF/jsp/agenda_note_evaluation_comon_view.jsp">
								</c:import>
							</div>
						</div>			
								<form:form modelAttribute="PrepareAgendaNotes"
									action="preparedAgendaNote" class="mt-4"
									name="PrepareAgendaNotes" method="POST"
									onsubmit="return preparedAgendaNoteValidation()">
						<div class="row" id="showHide2">
							<div class="col-sm-12">
								
								<hr class="mt-2 mb-5">
								<div class="row">
									
									<div class="col-sm-12">
										<div class="form-group">
											<label>Note</label> <!-- disabled="disabled"  -->
											<textarea  name="notes" class="form-control" rows="4">${prepAgenNotes}</textarea>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Nodal Agency and IIDD Recommendation<small></small></label> <!-- disabled="disabled" -->
											<textarea  name="pkupScrutinyDetail" class="form-control"  rows="4">${scrtnyComment}</textarea>
										</div>
									</div>
								</div>
								<hr>
							 
									<form:hidden path="prepareAgendaNote" id="prepareAgendaNote"></form:hidden>
									<div class="row mb-3">
										<div class="col-sm-5">
											<a href="./agendaNode" class="common-default-btn mt-3">Back</a>
										</div>


										<div class="col-sm-7 text-right">
											<form:hidden path="appliId" id="appliId"></form:hidden>
											<input type="submit" value="Save" onclick="return Save()"
												class="common-btn mt-0">
										</div>

									</div>
							
							</div>
						</div>	</form:form>
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