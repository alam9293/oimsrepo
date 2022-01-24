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
<title>Generate LOC</title>

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

<script type="text/javascript">

function generateLOCValidation(appId) {
		var selected = new Array();
		$("#table1 input[type=checkbox]:checked").each(function() {
			selected.push(this.value);
		});

		if (selected.length <= 0) {
			alert('Please Select Application for Generate LOC');
			return false;
		}
		
		var AppID = document.getElementById('appId').value;
		//alert(AppID);
		if (confirm("Your G.O. NO. is " +AppID+". Are you sure you want to Genarate LOC?")) {
			return true;
		} else {
			return false;
		}
}

function rejectLOCValidation(appId) {
	var selected = new Array();

	$("#table1 input[type=checkbox]:checked").each(function() {

		selected.push(this.value);

	});

	if (selected.length <= 0) {
		alert('Please Select Application for Reject');
		return false;
	}
	
	var AppID = document.getElementById('appId').value;
	//alert(AppID);
	if (confirm("Your G.O. NO. is "+ AppID+ ". Are you sure you want to Reject the Application?")) {
		
		return true;
	} else {
		return false;
	}

}

function deferredLOCValidation(appId) {
	var selected = new Array();

	$("#table1 input[type=checkbox]:checked").each(function() {

		selected.push(this.value);

	});

	if (selected.length <= 0) {
		alert('Please Select Application for Deferred');
		return false;
	}
	
	var AppID = document.getElementById('appId').value;
	//alert(AppID);
	if (confirm("Your G.O. NO. is "+ AppID+ ". Are you sure you want to Defer the Application?")) {
		
		return true;
	} else {
		return false;
	}

}

function generateLOCLargeValidation() {
	var selected = new Array();

	$("#table2 input[type=checkbox]:checked").each(function() {

		selected.push(this.value);

	});

	if (selected.length <= 0) {
		alert('Please select Application for Generate LOC');
		return false;
	}

	var AppID1 = document.getElementById('appId1').value;
	//alert(AppID);
	if (confirm("Are you sure you want to Genarate LOC?")) {
		
		return true;
	} else {
		return false;
	}
	
}

function rejectLOCLargeValidation() {
	var selected = new Array();

	$("#table2 input[type=checkbox]:checked").each(function() {

		selected.push(this.value);

	});

	if (selected.length <= 0) {
		alert('Please Select Application for Reject');
		return false;
	}
	
	var AppID = document.getElementById('appId1').value;
	//alert(AppID);
	if (confirm("Are you sure you want to Reject the Application?")) {
		
		return true;
	} else {
		return false;
	}

}

function deferredLOCLargeValidation() {
	var selected = new Array();

	$("#table2 input[type=checkbox]:checked").each(function() {

		selected.push(this.value);

	});

	if (selected.length <= 0) {
		alert('Please Select Application for Deferred');
		return false;
	}
	
	var AppID = document.getElementById('appId1').value;
	//alert(AppID);
	if (confirm("Are you sure you want to Defer the Application?")) {
		
		return true;
	} else {
		return false;
	}

}

	function onlyOne(checkbox) {
	    var checkboxes = document.getElementsByName('applicantId')
	    checkboxes.forEach((item) => {
	        if (item !== checkbox) item.checked = false
	    })
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

				<div class="col-md-9 col-lg-10 mt-4 main">
					<h4
						class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">GENERATE
						LOC APPLICATION</h4>
					<div class="card card-block p-4 mb-5">
						<div id="accordion"
							class="accordion mt-3 mb-3 animate__animated animate__pulse">
							<div class="mb-0 border-0">
								<div class="card-header collapsed mb-4" data-toggle="collapse"
									href="#collapseOne">
									<a class="card-title"> <strong>APPLICATION
											SUBMITTED TO EMPOWERED COMMITTEE</strong>
									</a>
								</div>
								<form:form modelAttribute="generateLocEvaluation" class="mt-4"
									name="generateLocEvaluation" method="get">

									<div id="collapseOne" class="card-body collapse "
										data-parent="#accordion">
										<div class="row">
											<div class="col-sm-12 text-right">
												<div class="form-group">
													<button type="submit" formaction="savegenerateLoc"
														onclick="return generateLOCValidation()" class="btn btn-outline-success btn-sm">Generate
														LOC</button>
														
													<button type="submit" formaction="rejectLoc" onclick="return rejectLOCValidation()"
														class="btn btn-outline-danger btn-sm">Reject</button>
														
													<button type="submit" formaction="deferApplcation" onclick="return deferredLOCValidation();" 
														class="btn btn-outline-warning btn-sm">Defer</button>
												</div>
											</div>

											<div class="col-sm-12">
												<div class="table-responsive mt-3">
													<table class="table table-stripped table-bordered"
														id="table1">
														<c:if test="${ not empty prepareAgendaNotesMagaLists}">
															<thead>
																<tr>
																	<th></th>
																	<th>S.No.</th>
																	<th>Application ID</th>
																	<th>Policy Name</th>
																	<th>Business Entity Name</th>
																	<th>Investment</th>
																	<th>Category</th>
																	<th>Region</th>
																	<th>District</th>
																	<th hidden="">G.O. NO</th>
																</tr>
															</thead>
														</c:if>
														<tbody>
															<c:forEach var="list"
																items="${prepareAgendaNotesMagaLists}"
																varStatus="counter">
																<tr>
																	<td><input type="checkbox" onclick="onlyOne(this)"
																		name="applicantId" value="${list[0]}"></td>
																	<td class="text-center">${counter.index+1}</td>
																	<td class="text-center"><c:out value="${list[0]}" /></td>
																	<td>Industrial Policy</td>
																	<td><c:out value="${list[1]}" /></td>
																	<td><c:out value="${list[2]}" /></td>
																	<td><c:out value="${list[3]}" /></td>
																	<td><c:out value="${list[4]}" /></td>
																	<td><c:out value="${list[5]}" /></td>
																	<td hidden=""><input type="text" id="appId"
																		value="${list[6]}"></td>
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
									data-parent="#accordion" href="#collapseTwo">
									<a class="card-title"> <strong>APPLICATION
											SUBMITTED TO SANCTION COMMITTEE</strong>
									</a>
								</div>

								<form:form modelAttribute="generateLocEvaluation" class="mt-4"
									name="generateLocEvaluation" method="get">
									
									<div id="collapseTwo" class="card-body collapse"
										data-parent="#accordion">
										<div class="row">
											<div class="col-sm-12 text-right">
												<div class="form-group">
													<button type="submit" formaction="savegenerateLocLarge"
														onclick="return generateLOCLargeValidation()" class="btn btn-outline-success btn-sm">Generate
														LOC</button>
														
													<button type="submit" formaction="rejectLoc" onclick="return rejectLOCLargeValidation()"
														class="btn btn-outline-danger btn-sm">Reject</button>
														
													<button type="submit" formaction="deferApplcation" onclick="return deferredLOCLargeValidation();" 
														class="btn btn-outline-warning btn-sm">Defer</button>
												
													
												</div>
											</div>
											<div class="col-sm-12">
												<div class="table-responsive mt-3">
													<table class="table table-stripped table-bordered"
														id="table2">
														<c:if test="${ not empty prepareAgendaNotesLargeLists}">
														<thead>
															<tr>
																<th></th>
																	<th>S.No.</th>
																	<th>Application ID</th>
																	<th>Policy Name</th>
																	<th>Business Entity Name</th>
																	<th>Investment</th>
																	<th>Category</th>
																	<th>Region</th>
																	<th>District</th>
																	<th hidden="">G.O. NO</th>
																	<th width="15%">Action</th>
															</tr>
														</thead>
														</c:if>
														<tbody>
															<c:forEach var="list"
																items="${prepareAgendaNotesLargeLists}"
																varStatus="counter">
																<tr>
																	<td><input type="checkbox" onclick="onlyOne(this)"
																		name="applicantId" value="${list[0]}"></td>
																	<td class="text-center">${counter.index+1}</td>
																	<td class="text-center"><c:out value="${list[0]}" /></td>
																	<td>Industrial Policy</td>
																	<td><c:out value="${list[1]}" /></td>
																	<td><c:out value="${list[2]}" /></td>
																	<td><c:out value="${list[3]}" /></td>
																	<td><c:out value="${list[4]}" /></td>
																	<td><c:out value="${list[5]}" /></td>
																	<td hidden=""><input type="text" id="appId1"
																		value="${list[6]}"></td>
																	<td><button type="button" class="btn btn-outline-info btn-sm"
														id="${list[0]}" data-target="#CirculateDraft" data-toggle="modal">Circulate
														Draft LOC</button></td>	
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
		
	</section>
	<div class="container">
        <div class="modal fade" id="CirculateDraft" style="display: none;" aria-hidden="true">
        <div class="modal-dialog modal-lg">
        
        <form:form modelAttribute="department"
					action="locconcerncirculatest" autocomplete="off" method="POST">
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">Send to Departments Sanction</h4>
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
				document.getElementById("appliIds").value = e.relatedTarget.id;
			})
		});
	</script>
</body>
</html>