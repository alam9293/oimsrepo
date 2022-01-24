<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Track Application</title>
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
	$(function() {
		$("#UploadMissingDoc").change(function() {
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
				alert("Please upload PDF file format.");
				document.getElementById("UploadMissingDoc").value = "";
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
					document.getElementById("UploadMissingDoc").value = "";
					return false;
				} else {
					return true;
				}
			}
		}

	});
</script>
<script src="js/custom.js"></script>


</head>
<%@ include file="header.jsp"%>
<body>
	<section class="common-form-area">
		<div class="container">
			<!-- Main Title -->
			<div class="inner-banner-text">
				<h1>Track Application Status</h1>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="form-card bdr-checkbox">
						<form action="#">
							<div class="row">
								<div class="col-md-12 mt-4 main">
									<div class="table-responsive mt-3" id="PackageTable">
										<table class="table table-stripped table-bordered">
											<thead>
												<tr>
													<th>S.No.</th>
													<th>Reference Id</th>
													<th class="text-center">View Query</th>
													<!-- <th class="text-center">Query Reply</th> -->
												</tr>
											</thead>
											<%
												int i = 0;
											%>
											<tbody>
												<c:forEach var="queryId" items="${raiseQueryIdList}">
													<c:if test="${not empty raiseQueryIdList}">
														<tr>
															<td><%=++i%></td>
															<td>${queryId}</td>
															<td class="text-center"><a href="#ViewRaiseQuery"
																id='${queryId}' class="view-btn" data-toggle="modal"><i
																	class="fa fa-eye"></i> View Query</a></td>
															<!-- <td class="text-center"><a href="#QueryReply"
																class="view-btn" data-toggle="modal"><i
																	class="fa fa-reply"></i> Query Reply</a></td> -->
														</tr>
													</c:if>
												</c:forEach>

											</tbody>

										</table>
									</div>
								</div>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
	</section>

	<footer>
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
		<div class="modal fade" id="ViewRaiseQuery">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">View Raised Query</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>Name of Nodal Deptt</label> <input type="text"
										class="form-control" id="nodaldept" disabled name="">
								</div>
							</div>
							<div class="modal-body">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label>Clarification Sought</label>
											<textarea class="form-control" id="clarificationsought"
												disabled></textarea>

										</div>
									</div>
									<div class="col-md-12">
										<div class="form-group">
											<label>Details of Missing Documents</label>
											<textarea class="form-control" id="detailsofmissingdocuments"
												disabled></textarea>
										</div>
									</div>
									<div class="col-md-12">
										<div class="form-group">
											<label>Upload Related Document</label> <a
												id="uploadrelateddocument" href="javascript:void();"
												class="d-block"><small></small></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#QueryReply" class="common-btn mt-0" data-toggle="modal"
							data-dismiss="modal">Answer Query</a>
					</div>

				</div>
			</div>
		</div>

		<!-- The Modal -->
		<div class="modal fade" id="QueryReply">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Query Reply</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<form:form action="saveQueryResponse" class="isf-form"
						method="POST" modelAttribute="queryResponse"
						enctype="multipart/form-data">

						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<form:label path="respClarifyDtl">Clarification Details</form:label>
										<form:textarea path="respClarifyDtl" class="form-control"
											rows="4"></form:textarea>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label>Upload Document</label>
										<div class="custom-file">
											<input type="file" name="respFilename"
												class="custom-file-input" id="UploadMissingDoc"> <label
												class="custom-file-label" for="UploadMissingDoc">Choose
												file</label>
										</div>
									</div>
								</div>
							</div>
						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<form:button type="submit" id="button1" class="common-btn mt-3">Submit</form:button>
						</div>
					</form:form>
				</div>
			</div>
		</div>

	</div>
	<script>
		$('#ViewRaiseQuery')
				.on(
						'show.bs.modal',
						function(e) {
							var $modal = $(this), esseyId = e.relatedTarget.id;
							<c:forEach var="raiseQuery" items="${raisedQueryList}">
							// 							if (esseyId == '${raiseQuery.rqId}') {
							//if (esseyId == '${raiseQuery.id}') {
								document.getElementById("nodaldept").value = '${raiseQuery.rqDept}';
								document.getElementById("clarificationsought").value = '${raiseQuery.rqClarifySought}';
								document
										.getElementById("detailsofmissingdocuments").value = '${raiseQuery.rqMissdocdtl}';
								document
										.getElementById("uploadrelateddocument").text = '${raiseQuery.rqFilename}';
						//	}

							</c:forEach>
						});
	</script>

</body>
</html>