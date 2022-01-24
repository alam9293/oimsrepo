<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Authorized Signatory Details</title>
<jsp:include page="head.jsp" />
<script src="js/validate-aadhar.js"></script>

<script>
	//Upload and Preview Image
	$(document).ready(function() {
		$(document).on("click", ".file", function() {
			var file = $(this).parents().find(".file");
			file.trigger("click");
		});
		$('input[type="file"].user-file').change(function(e) {
			var fileName = e.target.files[0].name;
			$("#file").val(fileName);
			var reader = new FileReader();
			reader.onload = function(e) {
				// get loaded data and render thumbnail.
				document.getElementById("preview").src = e.target.result;
			};
			// read the image file as a data URL.
			reader.readAsDataURL(this.files[0]);
		});
	});
	// File upload for Signature
	$(document).ready(function() {
		$(document).on("click", "#sign-upload", function() {
			var file = $(this).parents().find("#sign-upload");
			file.trigger("click");
		});
		$('input[type="file"].user-sign').change(function(e) {
			var fileName = e.target.files[0].name;
			$("#sign-upload").val(fileName);
			var reader = new FileReader();
			reader.onload = function(e) {
				// get loaded data and render thumbnail.
				document.getElementById("preview-Sign").src = e.target.result;
			};
			// read the image file as a data URL.
			reader.readAsDataURL(this.files[0]);
		});
	});
</script>

<script>
	$(function() {
		$("#photofile").change(function() {
			if (fileExtValidate(this)) {
				if (photoSizeValidate(this)) {
					showPhotoImg(this);
				}
			}
		});

		// File extension validation, Add more extension you want to allow
		var validExt = ".jpeg, .png, .jpg";
		function fileExtValidate(fdata) {
			var filePath = fdata.value;
			var getFileExt = filePath.substring(filePath.lastIndexOf('.') + 1)
					.toLowerCase();
			var pos = validExt.indexOf(getFileExt);
			if (pos < 0) {
				alert("Applicant photo should be in jpg, jpeg or png format.");
				document.getElementById("photofile").value = "";
				return false;
			} else {
				return true;
			}
		}

		// photo file size validation
		// size in kb

		function photoSizeValidate(fdata) {
			var maxSize = '50';//size in KB
			if (fdata.files && fdata.files[0]) {
				var fsize = fdata.files[0].size / 1024;
				if (fsize > maxSize) {
					alert('Applicant photo size should not be more than 50KB. This file size is: '
							+ fsize + " KB");
					document.getElementById("photofile").value = "";
					return false;
				} else {
					return true;
				}
			}
		}

		// display photo imag preview before upload.
		function showPhotoImg(fdata) {
			if (fdata.files && fdata.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#preview-photo').attr('src', e.target.result);
				}

				reader.readAsDataURL(fdata.files[0]);
			}
		}

	});

	function validateTextField() {
		return (event.charCode > 64 && event.charCode < 91)
				|| (event.charCode > 96 && event.charCode < 123)
	}

	function validateDesignation() {
		return (event.charCode > 64 && event.charCode < 91)
				|| (event.charCode > 96 && event.charCode < 123)
				|| event.charCode == 32
	}

	function validateNumberField() {
		return event.charCode > 47 && event.charCode < 58;
	}

	function validateApplicantForm1() {
		var photo = document.getElementById("photofile").value;
		var choosefile = document.getElementById('choosefile').innerHTML;
		if (choosefile == null || choosefile == ''
				|| choosefile == 'Choose file') {
			document.getElementById('PhotoFile').innerHTML = "Upload Applicant Photo.";
			document.getElementById('photofile').focus();
			return false;
		}

		return true;
	}

	function validatePhone() {
		var phoneRegex = /^0[0-9].*$/;
		var phoneno = document.getElementById("appPhoneNo").value;

		if (phoneno == null || phoneno == '') {
			return true;
		}

		else {
			if (phoneno.length < 11) {
				document.getElementById('appphoneno').innerHTML = "Phone number should be 11 digits long.";
				document.getElementById('appPhoneNo').focus();
				return false;
			} else {
				document.getElementById('appphoneno').innerHTML = "";
			}
		}

		if (!phoneRegex.test(phoneno)) {
			document.getElementById('appphoneno').innerHTML = "Phone number should start with 0 (zero).";
			document.getElementById("appPhoneNo").focus();
			return false;
		} else {
			return true;
		}
	}

	function emptyErrorMessage() {
		$("#appdesignation").text('');
		return true;
	}

	function validateApplicantForm() {

		var firstName = document.getElementById("appFirstName").value;
		/* var title = document.getElementById("appTitle").value; */
		var lastName = document.getElementById("appLastName").value;
		var emailId = document.getElementById("appEmailId").value;
		var mobileNo = document.getElementById("appMobileNo").value;
		var aadharNo = document.getElementById("appAadharNo").value;
		var pancardNo = document.getElementById("appPancardNo").value;
		var addressLine1 = document.getElementById("appAddressLine1").value;
		var country = document.getElementById("appCountry").value;
		var state = document.getElementById("appState").value;
		var district = document.getElementById("appDistrict").value;
		var pinCode = document.getElementById("appPinCode").value;
		var photoFile = document.getElementById("photofile").value;
		var choosefile = document.getElementById('choosefile').innerHTML;
		var designation = document.getElementById("appDesignation").value;

		if (firstName == null || firstName == '') {
			document.getElementById('appfirstname').innerHTML = "First name field is required";
			document.getElementById('appFirstName').focus();
			return false;
		} else {
			document.getElementById('appfirstname').innerHTML = "";
		}
		if (lastName == null || lastName == '') {
			document.getElementById('applastname').innerHTML = "Last name is required";
			document.getElementById('appLastName').focus();
			return false;
		} else {
			document.getElementById('applastname').innerHTML = "";
		}

		if (emailId == null || emailId == '') {
			document.getElementById('appemailid').innerHTML = "Email-Id is required";
			document.getElementById('appEmailId').focus();
			return false;
		} else {
			document.getElementById('appemailid').innerHTML = "";
		}

		if (mobileNo == null || mobileNo == '') {
			document.getElementById('appmobileno').innerHTML = "Mobile number is required";
			document.getElementById('appMobileNo').focus();
			return false;
		}

		else {
			document.getElementById('appmobileno').innerHTML = "";
		}

		if (designation == null || designation == '') {
			document.getElementById('appdesignation').innerHTML = "Designation is required";
			document.getElementById('appDesignation').focus();
			return false;
		}

		else {
			document.getElementById('appdesignation').innerHTML = "";
		}

		/* if (aadharNo == null || aadharNo == '') {
			document.getElementById('appaadharno').innerHTML = "AAdhaar number is required";
			document.getElementById('appAadharNo').focus();
			return false;
		} else {
			document.getElementById('appaadharno').innerHTML = "";
		} */
		if (!AadharValidate()) {
			document.getElementById('appAadharNo').focus();
			return false;
		}

		if (pancardNo == null || pancardNo == '') {
			document.getElementById('apppancardno').innerHTML = "PAN card is required";
			document.getElementById('appPancardNo').focus();
			return false;
		}

		else {
			document.getElementById('apppancardno').innerHTML = "";
		}
		if (addressLine1 == null || addressLine1 == '') {
			document.getElementById('appaddressline1').innerHTML = "Address Line-1 is required";
			document.getElementById('appAddressLine1').focus();
			return false;
		}

		else {
			document.getElementById('appaddressline1').innerHTML = "";
		}

		if (country == null || country == '') {
			document.getElementById('appcountry').innerHTML = "Country is required";
			document.getElementById('appCountry').focus();
			return false;
		} else {
			document.getElementById('appcountry').innerHTML = "";
		}

		if (state == null || state == '') {
			document.getElementById('appstate').innerHTML = "State is required";
			document.getElementById('appState').focus();
			return false;
		} else {
			document.getElementById('appstate').innerHTML = "";
		}

		if (district == null || district == '') {
			document.getElementById('appdistrict').innerHTML = "District is required";
			document.getElementById('appDistrict').focus();
			return false;
		} else {
			document.getElementById('appdistrict').innerHTML = "";
		}

		if (pinCode == null || pinCode == '') {
			document.getElementById('apppincode').innerHTML = "PIN Code is required";
			document.getElementById('appPinCode').focus();
			return false;
		} else {
			document.getElementById('apppincode').innerHTML = "";
		}

		/*  if (photoFile == null || photoFile == '') {
			document.getElementById('PhotoFile').innerHTML = "Upload Applicant Photo";
			document.getElementById('photofile').focus();
			return false;
		} else {
			document.getElementById('PhotoFile').innerHTML = "";
		} */

		/* if (choosefile == null || choosefile == ''
				|| choosefile == 'Choose file') {
			document.getElementById('PhotoFile').innerHTML = "Please Upload Applicant Photo.";
			document.getElementById('photofile').focus();
			return false;
		} */

		if (confirm("Are you sure, you want to save Authorized Signatory Details.")) {

			$("#appPhoneNo").removeAttr("readonly");
			$("#appDesignation").removeAttr("readonly");
			$("#appAadharNo").removeAttr("readonly");
			$("#appAddressLine2").removeAttr("readonly");
			$("#photofile").removeAttr("disabled");
			return true;
		} else {
			return false;
		}

		return true;
	}
	window.onload = function() {

		if ('${applicantId}' != null && '${applicantId}' != '') {
			$("input[name='appPhoneNo']").attr("readonly", true);
			$("input[name='appDesignation']").attr("readonly", true);
			$("input[name='appAadharNo']").attr("readonly", true);
			$("input[name='appAddressLine2']").attr("readonly", true);

			$("input[name='appFirstName']").attr("readonly", true);
			$("input[name='appLastName']").attr("readonly", true);
			$("input[name='appEmailId']").attr("readonly", true);
			$("input[name='appMobileNo']").attr("readonly", true);
			$("input[name='appAadharNo']").attr("readonly", true);
			$("input[name='appPancardNo']").attr("readonly", true);
			$("input[name='appAddressLine1']").attr("readonly", true);
			$("input[name='appCountry']").attr("readonly", true);
			$("input[name='appState']").attr("readonly", true);
			$("input[name='appDistrict']").attr("readonly", true);
			$("input[name='appPinCode']").attr("readonly", true);
			$("input[name='appDesignation']").attr("readonly", true);
			$("input[name='appMiddleName']").attr("readonly", true);
			$("input[name='gender']").attr("disabled", true);
			document.getElementById("photofile").disabled = true;
		} else {
			$("input[name='appMiddleName']").attr("readonly", false);
			$("input[name='appPhoneNo']").attr("readonly", false);
			$("input[name='appDesignation']").attr("readonly", false);
			$("input[name='appAadharNo']").attr("readonly", false);
			$("input[name='appAddressLine2']").attr("readonly", false);

			$("input[name='appFirstName']").attr("readonly", false);
			$("input[name='appLastName']").attr("readonly", false);
			$("input[name='appEmailId']").attr("readonly", false);
			$("input[name='appMobileNo']").attr("readonly", false);
			$("input[name='appAadharNo']").attr("readonly", false);
			$("input[name='appPancardNo']").attr("readonly", false);
			$("input[name='appAddressLine1']").attr("readonly", false);
			$("input[name='appCountry']").attr("readonly", false);
			$("input[name='appState']").attr("readonly", false);
			$("input[name='appDistrict']").attr("readonly", false);
			$("input[name='appPinCode']").attr("readonly", false);
			$("input[name='appDesignation']").attr("readonly", false);
			document.getElementById("photofile").disabled = false;
			$("input[name='gender']").attr("disabled", false);
		}
	};

	function editApplicantDetails() {

		var r = confirm('Are you Sure,Want to Edit the Authorized Signatory Details?');

		if (r == true) {
			document.getElementById('appPhoneNo').focus();
			$("#appPhoneNo").removeAttr("readonly");
			$("#appDesignation").removeAttr("readonly");
			$("#appAadharNo").removeAttr("readonly");
			$("#appAddressLine2").removeAttr("readonly");
			$("#photofile").removeAttr("disabled");
			$("input[name='appMiddleName']").attr("readonly", false);
			$("input[name='appFirstName']").attr("readonly", false);
			$("input[name='appLastName']").attr("readonly", false);
			$("input[name='appEmailId']").attr("readonly", false);
			$("input[name='appMobileNo']").attr("readonly", false);
			$("input[name='appAadharNo']").attr("readonly", false);
			$("input[name='appPancardNo']").attr("readonly", false);
			$("input[name='appAddressLine1']").attr("readonly", false);
			$("input[name='appCountry']").attr("readonly", false);
			$("input[name='appState']").attr("readonly", false);
			$("input[name='appDistrict']").attr("readonly", false);
			$("input[name='appPinCode']").attr("readonly", false);
			$("input[name='appDesignation']").attr("readonly", false);
			$("input[name='gender']").attr("disabled", false);
		} else {
			return false
		}
	}
</script>



<script>
	$(document)
			.ready(
					function() {
						$(':radio:not(:checked)').attr('disabled', true);

						var photoFile = document.getElementById("photofile").value;
						//document.getElementById("photofile").value = "${applicantDetails.fileName}";
						if ("${applicantDetails.base64imageFile}" == null
								|| "${applicantDetails.base64imageFile}" == '') {

						} else {
							document.getElementById("preview-photo").src = "data:image/png;base64,${applicantDetails.base64imageFile}";
							document.getElementById('choosefile').innerHTML = '${applicantDetails.fileName}';
							//photoFile = "${applicantDetails.fileName}";
						}
					});

	function fetchdata() {
		$(document)
				.ready(
						function() {
							$(document)
									.on(
											"change",
											".form-control",
											function() {
												//$('#appAadharNo').rules('remove');
												//$('.appAadharNo').rules('remove');
												document
														.getElementById("appAadharNo").disableValidation = true;
												document
														.getElementById("appDesignation").disableValidation = true;

												document
														.getElementById("myFormA").action = "autosaveApplicantDetails";
												document.getElementById(
														'myFormA').submit();
											});
						});
	}

	/* $(document).ready(function(){
		 validatePhone(); 
		 setInterval(fetchdata,15000);
		}); */
</script>





<style type="text/css">
.error {
	color: red;
	font-size: 12px;
}
</style>
</head>

<%@ include file="header.jsp"%>
<body class="bottom-bg">
	<section class="common-form-area">
		<div class="container">
			<!-- Main Title -->
			<div class="inner-banner-text">
				<h1>Application Form</h1>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="form-card">
						<div class="without-wizard-steps">
							<div class="step-links">
								<div class="row">
									<ul>
										<li><a href="#" class="active"><span>1</span>Authorized
												Signatory Details</a></li>
										<li><c:if test="${not empty Bflag}">
												<a href="./getIdByTabs1?busiTab=busiTab" class="filled"><span>2</span>
													Business Entity Details</a>
											</c:if> <c:if test="${ empty Bflag}">
												<a href="./getIdByTabs1?busiTab=busiTab"><span>2</span>
													Business Entity Details</a>
											</c:if></li>

										<li><c:if test="${not empty Pflag}">
												<a href="./getIdByTabs2?projTab=projTab" class="filled"><span>3</span>
													Project Details</a>
											</c:if> <c:if test="${ empty Pflag}">
												<a href="./getIdByTabs2?projTab=projTab"><span>3</span>
													Project Details</a>
											</c:if></li>

										<li><c:if test="${not empty Iflag}">
												<a href="./getIdByTabs3?investTab=investTab" class="filled"><span>4</span>
													Investment Details</a>
											</c:if> <c:if test="${empty Iflag}">
												<a href="./getIdByTabs3?investTab=investTab"><span>4</span>
													Investment Details</a>
											</c:if></li>

										<li><c:if test="${not empty PEflag}">
												<a href="./getIdByTabs4?propoTab=propoTab" class="filled"><span>5</span>
													Proposed Employment Details</a>
											</c:if> <c:if test="${empty PEflag}">
												<a href="./getIdByTabs4?propoTab=propoTab"><span>5</span>
													Proposed Employment Details</a>
											</c:if></li>
									</ul>
								</div>

								<form:form action="saveApplicantDetails" class="isf-form"
									method="POST" modelAttribute="applicantDetails" id="myFormA"
									enctype="multipart/form-data">
									<div class="tab-content py-2">
										<div class="tab-pane active">
											<div class="row">
												<div class="col-sm-12 mt-4">
													<h3 class="common-heading">
														Authorized Signatory Details <a href="javascript:void(0);"
															class="remove-row" data-toggle="tooltip" title=""
															data-original-title="Please Enter the Authorized Person Details of your Company"><i
															class="fa fa-info-circle text-info"></i></a>
													</h3>
												</div>
											</div>
											<div class="row">
												<%-- <div class="col-md-6 col-lg-4 col-xl-4">
													<div class="form-group">
														<form:label path="appTitle">Title <span>*</span>
														</form:label>
														<form:select class="form-control" path="appTitle" onblur="validateApplicantForm1()"
															id="appTitle">
															<form:option value="">Select Title</form:option>
															<form:option value="Mr.">Mr.</form:option>
															<form:option value="Mrs.">Mrs.</form:option>
														</form:select>
														<span id="apptitle" class="text-danger"></span>
														<form:errors path="appTitle" cssClass="error" />
													</div>
												</div> --%>



												<div class="col-md-6 col-lg-4 col-xl-4">
													<div class="form-group">
														<form:label path="appFirstName">First Name <span>*</span>
														</form:label>
														<form:input path="appFirstName" class="form-control"
															onkeypress="return validateTextField()" type="text"
															id="appFirstName" readonly="false"></form:input>
														<span id="appfirstname" class="text-danger"></span>
														<form:errors path="appFirstName" cssClass="error" />
													</div>
												</div>

												<div class="col-md-6 col-lg-4 col-xl-4">
													<div class="form-group">
														<form:label path="appMiddleName">Middle Name</form:label>
														<form:input path="appMiddleName" class="form-control"
															readonly="false" onkeypress="return validateTextField()"
															id="appMiddleName"></form:input>
														<form:errors path="appMiddleName" cssClass="error" />
													</div>
												</div>

												<div class="col-md-6 col-lg-4 col-xl-4">
													<div class="form-group">
														<form:label path="appLastName">Last Name<span>*</span>
														</form:label>
														<form:input path="appLastName" class="form-control"
															readonly="false" onkeypress="return validateTextField()"
															id="appLastName"></form:input>
														<span id="applastname" class="text-danger"></span>
														<form:errors path="appLastName" cssClass="error" />
													</div>
												</div>
											</div>

											<div class="row">



												<div class="col-md-6 col-lg-4 col-xl-4">
													<div class="form-group">
														<form:label path="appEmailId">Email ID <span>*</span>
														</form:label>
														<form:input path="appEmailId" class="form-control"
															type="email" readonly="false" id="appEmailId"></form:input>
														<span id="appemailid" class="text-danger"></span>
														<form:errors path="appEmailId" cssClass="error" />
													</div>
												</div>

												<div class="col-md-6 col-lg-4 col-xl-4">
													<div class="form-group">
														<form:label path="appMobileNo">Mobile No:<small>(+91)</small>
															<span>*</span>
														</form:label>
														<form:input path="appMobileNo" class="form-control"
															readonly="false"
															onkeypress="return validateNumberField()" maxlength="10"
															id="appMobileNo"></form:input>
														<span id="appmobileno" class="text-danger"></span>
														<form:errors path="appMobileNo" cssClass="error" />
													</div>
												</div>
												<div class="col-md-6 col-lg-4 col-xl-4">
													<div class="form-group">
														<form:label path="appPhoneNo">Phone No.(LandLine)</form:label>
														<form:input path="appPhoneNo" class="form-control"
															onblur="validatePhone()"
															onkeypress="return validateNumberField()" maxlength="11"
															minlength="11" id="appPhoneNo"></form:input>
														<span id="appphoneno" class="text-danger"></span>
														<form:errors path="appPhoneNo" cssClass="error" />
													</div>
												</div>
											</div>

											<div class="row">



												<div class="col-md-6 col-lg-4 col-xl-4">
													<div class="form-group">
														<form:label path="appDesignation">Designation <span>*</span>
														</form:label>
														<form:input path="appDesignation" class="form-control"
															onkeypress="return validateDesignation()" maxlength="60"
															onchange="return emptyErrorMessage()" id="appDesignation"></form:input>
														<span id="appdesignation" class="text-danger"></span>
														<form:errors path="appDesignation" cssClass="error" />
													</div>
												</div>

												<div class="col-md-6 col-lg-4 col-xl-4">
													<div class="form-group">
														<div class="gender-label">
															<form:label path="gender">Gender <span>*</span>
															</form:label>
														</div>
														<div
															class="custom-control custom-radio custom-control-inline">
															<form:radiobutton path="gender"
																class="custom-control-input" id="MaleOpt" name="gender"
																value="Male" />
															<label class="custom-control-label" for="MaleOpt">Male</label>
														</div>
														<div
															class="custom-control custom-radio custom-control-inline">
															<form:radiobutton path="gender"
																class="custom-control-input" id="FemaleOpt"
																name="gender" value="Female" />
															<label class="custom-control-label" for="FemaleOpt">Female</label>

														</div>
														<div
															class="custom-control custom-radio custom-control-inline">
															<form:radiobutton path="gender"
																class="custom-control-input" id="Transgender"
																name="gender" value="Transgender" />
															<label class="custom-control-label" for="Transgender">Transgender
															</label>

														</div>
														<span id="apptitle" class="text-danger"></span>
														<form:errors path="gender" cssClass="error" />
													</div>
												</div>
											</div>

											<div class="row">
												<div class="col-sm-12 mt-4">
													<h3 class="common-heading">Aadhaar & PAN Details</h3>
												</div>
											</div>


											<div class="row">

												<div class="col-md-6">
													<div class="form-group">
														<form:label id="GFG" path="appAadharNo">Aadhaar Number  <span>*</span>
														</form:label>
														<form:input path="appAadharNo" class="form-control"
															onkeyup="return AadharValidate();"
															onkeypress="return validateNumberField()" maxlength="12"
															id="appAadharNo"></form:input>
														<span id="appaadharno" class="text-danger"></span>

														<form:errors path="appAadharNo" cssClass="error" />
													</div>
												</div>





												<div class="col-md-6">
													<div class="form-group">
														<form:label path="appPancardNo">PAN Number <span>*</span>
														</form:label>
														<form:input path="appPancardNo" class="form-control"
															maxlength="10" readonly="false" id="appPancardNo"></form:input>
														<span id="apppancardno" class="text-danger"></span>
														<form:errors path="appPancardNo" cssClass="error" />
													</div>
												</div>
											</div>


										</div>
										<div class="row">
											<div class="col-sm-12 mt-4">
												<h3 class="common-heading">Address Details</h3>
											</div>
										</div>

										<div class="row">
											<div class="col-sm-6">
												<div class="form-group">
													<form:label path="appAddressLine1">Address Line - 1 <span>*</span>
													</form:label>
													<form:input path="appAddressLine1" class="form-control"
														maxlength="250" readonly="false" id="appAddressLine1"></form:input>
													<span id="appaddressline1" class="text-danger"></span>
													<form:errors path="appAddressLine1" cssClass="error" />
												</div>
											</div>

											<div class="col-sm-6">
												<div class="form-group">
													<form:label path="appAddressLine2">Address Line - 2</form:label>
													<form:input path="appAddressLine2" class="form-control"
														maxlength="200" id="appAddressLine2"></form:input>
													<form:errors path="appAddressLine2" cssClass="error" />
												</div>
											</div>
										</div>

										<div class="row">

											<div class="col-md-6 col-lg-4 col-xl-4">
												<div class="form-group">
													<form:label path="appCountry"> Country <span>*</span>
													</form:label>
													<form:input path="appCountry" class="form-control"
														maxlength="5" value="India" id="appCountry"
														readonly="false"></form:input>
													<span id="appcountry" class="text-danger"></span>
													<form:errors path="appCountry" cssClass="error" />
												</div>
											</div>
											<div class="col-md-6 col-lg-4 col-xl-4">
												<div class="form-group">
													<form:label path="appState">State/UT <span>*</span>
													</form:label>
													<form:input path="appState" class="form-control"
														maxlength="20" value="${appState}" id="appState"
														readonly="false"></form:input>
													<span id="appstate" class="text-danger"></span>
													<form:errors path="appState" cssClass="error" />
												</div>
											</div>

											<div class="col-md-6 col-lg-4 col-xl-4">
												<div class="form-group">
													<form:label path="appDistrict">District <span>*</span>
													</form:label>
													<form:input path="appDistrict" class="form-control"
														maxlength="50" value="${appDistrict}" id="appDistrict"
														readonly="false"></form:input>
													<span id="appdistrict" class="text-danger"></span>
													<form:errors path="appDistrict" cssClass="error" />
												</div>
											</div>
										</div>




										<div class="row">
											<div class="col-md-6 col-lg-4 col-xl-4">
												<div class="form-group">
													<form:label path="appPinCode">Pin Code <span>*</span>
													</form:label>
													<form:input type="text" path="appPinCode" readonly="false"
														onkeypress="return validateNumberField()" maxlength="6"
														class="form-control" id="appPinCode" name="appPinCode" />
													<span id="apppincode" class="text-danger"></span>
													<form:errors path="appPinCode" cssClass="error" />
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-sm-12 mt-4">
												<h3 class="common-heading">Upload Authorized Signatory
													Photo</h3>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-6">
												<div class="form-group">
													<label>Upload Photo <small>(Upload JPG or
															PNG Less than 50 KB)</small></label>
													<div class="custom-file">
														<input type="file" name="file" id="photofile"
															class="custom-file-input user-file"></input> <label
															class="custom-file-label file" id="choosefile">Choose
															file</label> <span id="PhotoFile" class="text-danger"></span>
													</div>
												</div>
											</div>

											<div class="col-sm-6 text-center">
												<img src="images/user-icon.png" id="preview-photo"
													class="user-upload-preview" align="user-icon">
											</div>
										</div>


										<!-- <div class="row">
											<div class="col-sm-12 mt-4">
												<h3 class="common-heading">Digital Signature</h3>
											</div>
											<div class="col-sm-12">
												<img id="preview-Sign" class="sign-upload-preview"
													align="sign-sample"> <a href="#"> <i class="fa fa-check"></i>
												</a>
											</div>
										</div> -->


										<hr>
										<div class="row">
											<div class="col-sm-12 text-right">
												<c:if test="${not empty applicantId}">
													<button type="button" class="common-btn mt-3"
														onclick="editApplicantDetails()">
														<i class="fa fa-edit"></i>Edit
													</button>
												</c:if>
												<form:button type="submit" id="button1"
													onclick="return validateApplicantForm()"
													class="common-btn mt-3">Save & Next</form:button>
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

	<jsp:include page="footer.jsp" />