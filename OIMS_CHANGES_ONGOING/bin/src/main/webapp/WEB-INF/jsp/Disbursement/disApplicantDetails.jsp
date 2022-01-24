<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>APPLICATION FORM FOR DISBURSEMENT</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->

<%@ include file="../head.jsp"%>

<script type="text/javascript">
$(document).ready(function(){

	var empapp = '${empapp}';
	var capiapp = '${capiapp}';
	var applicantID = '${applicantID}';
	if (applicantID != '' && applicantID != null){
		$("#applicantIDActive").addClass("filled");
		}
	if (capiapp != '' && capiapp != null){
		$("#capiappActive").addClass("filled");
		}
	if (empapp != '' && empapp != null){
		$("#empappActive").addClass("filled");
		}
	
	});
	


</script>

</head>

<%@ include file="../topMenu.jsp"%>

<section class="common-form-area">
	<div class="container">
		<!-- Main Title -->
		<div class="inner-banner-text">
			<h1>APPLICATION FORM</h1>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="form-card">
					<div class="without-wizard-steps">
						<div class="step-links">
							<ul>
								<li><a href="#" class="active" ><span>1</span> Applicant
										Details</a></li>
								<li><a href="./disbIncentiveCapInv"  id = "capiappActive" ><span>2</span>
										Capital Investment</a></li>
								<li><a href="./disEmploymentDetails"id ="empappActive" ><span>3</span>Employment
										Details</a></li>
							</ul>
						</div>
						<div class="isf-form">
							<form:form class="mt-4" modelAttribute="disApplicantDetails"
								method="post" action="disApplicantDetailsSave">

								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">
											Applicant Details <a href="javascript:void(0);"
												class="remove-row" data-toggle="tooltip" title=""
												data-original-title="Please Enter the Authorized Person Details of your Company"><i
												class="fa fa-info-circle text-info"></i></a>
										</h3>
									</div>
								</div>

								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label>Name of the Applicant</label>
											<form:input path="bussAuthSigName" type="text" name="bussAuthSigName"
												class="form-control" readonly="true"
												value="${bussAuthSigName}"></form:input>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label>Designation</label>
											<form:input path="bussDesignation" type="text" name="bussDesignation"
												class="form-control" readonly="true"
												value="${bussDesignation}"></form:input>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Company Details</h3>
									</div>
								</div>

								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label>Company Name</label>
											<form:input path="bussNameDis" type="text"
												name="bussNameDis" class="form-control" readonly="true"
												value="${businessEntityName}"></form:input>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label>Full Address</label>
											<form:input path="bussAddressDis" type="text"
												name="bussAddressDis" class="form-control" readonly="true"
												value="${businessAddress}"></form:input>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Country</label>
											<form:input path="bussCountryDis" type="text"
												name="bussCountryDis" value="${businessCountryName}"
												readonly="true" class="form-control"></form:input>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>State/UT</label>
											<form:input path="bussStateDis" type="text"
												name="bussStateDis" value="${businessStateName}"
												readonly="true" class="form-control"></form:input>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>District</label>
											<form:input path="bussDistrictDis" type="text"
												name="bussDistrictDis" value="${businessDistrictName}"
												readonly="true" class="form-control"></form:input>
										</div>
									</div>
								</div>


								<div class="row">
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Pin Code</label>
											<form:input path="bussPinCodeDis" type="text"
												name="bussPinCodeDis" value="${PinCode}"
												class="form-control" readonly="true"></form:input>
										</div>
									</div>
								</div>


								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Location of the Industrial
											Undertaking</h3>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<label>Full Address</label>
											<form:input path="projAddressDis" type="text"
												name="projAddressDis" class="form-control" readonly="true"
												value="${fullAddress}"></form:input>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>District</label>
											<form:input path="projDistrictDis" type="text"
												name="projDistrictDis" value="${districtName}"
												readonly="true" class="form-control"></form:input>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Mandal</label>
											<form:input path="projMandalDis" type="text"
												name="projMandalDis" value="${mandalName}" readonly="true"
												class="form-control"></form:input>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Region</label>
											<form:input path="projRegionDis" type="text"
												name="projRegionDis" value="${resionName}" readonly="true"
												class="form-control"></form:input>
										</div>
									</div>
								</div>


								<div class="row">
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Pin Code</label>
											<form:input path="projPinCodeDis" type="text"
												name="projPinCodeDis" class="form-control" readonly="true"
												value="${pinCode}"></form:input>
										</div>
									</div>
								</div>


								<hr>

								<div class="row">
									<div class="col-sm-12 text-right">
										<button type="submit" class="common-btn" value="Submit"
											onclick="return confirm('Are you sure want to go in next page?')">Next</button>
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

<%@ include file="../footerMenu.jsp"%>

</body>
</html>