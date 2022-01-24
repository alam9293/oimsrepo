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
<title>Employment Details</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->

<%@ include file="../head.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
    console.log( "document loaded" );
    if ("${employmentDetailsForm.noBPLEmplDocbase64File}" == null
			|| "${employmentDetailsForm.noBPLEmplDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilenoBPLEmplDoc').innerHTML = '${employmentDetailsForm.noBPLEmplDoc}';
		document.getElementById("noBPLEmplDoc").src = "data:image/png;base64,${employmentDetailsForm.noBPLEmplDocbase64File}";
	}

    if ("${employmentDetailsForm.noSCEmplDocbase64File}" == null
			|| "${employmentDetailsForm.noSCEmplDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilenoSCEmplDoc').innerHTML = '${employmentDetailsForm.noSCEmplDoc}';
		document.getElementById("noSCEmplDoc").src = "data:image/png;base64,${employmentDetailsForm.noSCEmplDocbase64File}";
	}

    if ("${employmentDetailsForm.noSTEmplDocbase64File}" == null
			|| "${employmentDetailsForm.noSTEmplDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilenoSTEmplDoc').innerHTML = '${employmentDetailsForm.noSTEmplDoc}';
		document.getElementById("noSTEmplDoc").src = "data:image/png;base64,${employmentDetailsForm.noSTEmplDocbase64File}";
	}

    if ("${employmentDetailsForm.noFemaleEmplDocbase64File}" == null
			|| "${employmentDetailsForm.noFemaleEmplDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilenoFemaleEmplDoc').innerHTML = '${employmentDetailsForm.noFemaleEmplDoc}';
		document.getElementById("noFemaleEmplDoc").src = "data:image/png;base64,${employmentDetailsForm.noFemaleEmplDocbase64File}";
	}
	

});
</script>
<script>
function EmplDtlValidation() {


	if(!WorkersfrBPL(document.getElementById('noBPLEmplDoc'))){
		return false;
	}
	if(!WorkersfrSC(document.getElementById('noSCEmplDoc'))){
		return false;
	}
	if(!WorkersfrST(document.getElementById('noSTEmplDoc'))){
		return false;
	}
	if(!WorkersFemale(document.getElementById('noFemaleEmplDoc'))){
		return false;
	}

var r = confirm("Are you Sure you want to Submit the Form?");
	
	if (r == true) {
		alert("Application Form is Submitted Succesfully");
	} else {
		return false
	}
	
	if('${employmentDetailsForm.noFemaleEmplDocbase64File}' != '' ){
		return true;
	}

	

}
</script>
<script type="text/javascript">
window.onload = function(){

if('${totalBPL}' < 1)
	{
	$(".bplDocs").hide();
	}

if('${totalSC}' < 1)
{
$(".ScDocs").hide();
}

if('${totalSt}' < 1)
{
$(".StDocs").hide();
}

if('${totalFemaleEmp}' < 1)
{
$(".FemaleDocs").hide();
}

}
</script>

<script type="text/javascript">

function WorkersfrBPL(file) {
	
	 var BPLWorkers = document.getElementById('noBPLEmplDoc').value;
	 var fileSize = document.getElementById("noBPLEmplDoc").files[0];
	 var maxSize = '5000';

	  if('${employmentDetailsForm.noBPLEmplDocbase64File}' != '' && BPLWorkers == ''){
			return true;
		} 
		
	 if(BPLWorkers != null && BPLWorkers != ''){
	 var ext = BPLWorkers.split('.').pop();
	 if (ext == "pdf") {
		
	} else {
		document.getElementById('noBPLEmplDocMsg').innerHTML = "Please Upload Support Documnet in PDF Format";
		document.getElementById('noBPLEmplDoc').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
 if (FileSize >5) {
 	document.getElementById('noBPLEmplDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
 	document.getElementById('noBPLEmplDoc').focus();
		return false;
 }
 
	} else{
		document.getElementById('noBPLEmplDocMsg').innerHTML = "Workers from BPL Document is Required.";
		document.getElementById('noBPLEmplDoc').focus();
		return false;
	  }
		    document.getElementById('noBPLEmplDocMsg').innerHTML = "";
		    return true;
}

function WorkersfrSC(file) {
	
	 var WorkersFrSCEmplDoc = document.getElementById('noSCEmplDoc').value;
	 var fileSize = document.getElementById("noSCEmplDoc").files[0];
	 var maxSize = '5000';

	  if('${employmentDetailsForm.noSCEmplDocbase64File}' != '' && WorkersFrSCEmplDoc == ''){
			return true;
		} 
		
	 if(WorkersFrSCEmplDoc != null && WorkersFrSCEmplDoc != ''){
	 var ext = WorkersFrSCEmplDoc.split('.').pop();
	 if (ext == "pdf") {
		
	} else {
		document.getElementById('noSCEmplDocMsg').innerHTML = "Please Upload Support Documnet in PDF Format";
		document.getElementById('noSCEmplDoc').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
if (FileSize >5) {
	document.getElementById('noSCEmplDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	document.getElementById('noSCEmplDoc').focus();
		return false;
}

	} else{
		document.getElementById('noSCEmplDocMsg').innerHTML = "Workers from SC Document is Required.";
		document.getElementById('noSCEmplDoc').focus();
		return false;
	  }
		    document.getElementById('noSCEmplDocMsg').innerHTML = "";
		    return true;
}

function WorkersfrST(file) {
	
	 var WorkersFrSTEmplDoc = document.getElementById('noSTEmplDoc').value;
	 var fileSize = document.getElementById("noSTEmplDoc").files[0];
	 var maxSize = '5000';

	  if('${employmentDetailsForm.noSTEmplDocbase64File}' != '' && WorkersFrSTEmplDoc == ''){
			return true;
		} 
		
	 if(WorkersFrSTEmplDoc != null && WorkersFrSTEmplDoc != ''){
	 var ext = WorkersFrSTEmplDoc.split('.').pop();
	 if (ext == "pdf") {
		
	} else {
		document.getElementById('noSTEmplDocMsg').innerHTML = "Please Upload Support Documnet in PDF Format";
		document.getElementById('noSTEmplDoc').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
if (FileSize >5) {
	document.getElementById('noSTEmplDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	document.getElementById('noSTEmplDoc').focus();
		return false;
}

	} else{
		document.getElementById('noSTEmplDocMsg').innerHTML = "Workers from ST Document is Required.";
		document.getElementById('noSTEmplDoc').focus();
		return false;
	  }
		    document.getElementById('noSTEmplDocMsg').innerHTML = "";
		    return true;
}

function WorkersFemale(file) {
	
	 var WorkersFemaleEmplDoc = document.getElementById('noFemaleEmplDoc').value;
	 var fileSize = document.getElementById("noFemaleEmplDoc").files[0];
	 var maxSize = '5000';

	  if('${employmentDetailsForm.noFemaleEmplDocbase64File}' != '' && WorkersFemaleEmplDoc == ''){
			return true;
		} 
		
	 if(WorkersFemaleEmplDoc != null && WorkersFemaleEmplDoc != ''){
	 var ext = WorkersFemaleEmplDoc.split('.').pop();
	 if (ext == "pdf") {
		
	} else {
		document.getElementById('noFemaleEmplDocMsg').innerHTML = "Please Upload Support Documnet in PDF Format";
		document.getElementById('noFemaleEmplDoc').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
if (FileSize >5) {
	document.getElementById('noFemaleEmplDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	document.getElementById('noFemaleEmplDoc').focus();
		return false;
}

	} else{
		document.getElementById('noFemaleEmplDocMsg').innerHTML = "Female Workers Document is Required.";
		document.getElementById('noFemaleEmplDoc').focus();
		return false;
	  }
		    document.getElementById('noFemaleEmplDocMsg').innerHTML = "";
		    return true;
}


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
								<li><a href="./disApplicantDetails" class="filled"><span>1</span>
										Applicant Details</a></li>
								<li><a href="./disbIncentiveCapInv" class="filled"><span>2</span>
										Capital Investment</a></li>
								<li><a href="#" class="active"><span>3</span>Employment
										Details</a></li>
							</ul>
						</div>
						<div class="isf-form">
							<form:form class="mt-4" modelAttribute="employmentDetailsForm"
								method="post" action="disEmploymentDetailsSave" enctype="multipart/form-data">

								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Employment Detailsss</h3>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<label>Number of BPL Employees</label>
											<form:input path="noBPLEmpl" type="text" class="form-control"
												id="noBPLEmpl" value="${totalBPL}" name="noBPLEmpl" readonly="true"></form:input>
										</div>
									</div>
									<div class="col-sm-12 bplDocs">
										<div class="form-group">
											<label>Particulars of Workers from BPL families with
												supporting documents <a href="javascript:void(0);"
												class="remove-row" data-toggle="tooltip" title=""
												data-original-title="(Employee Distinctive Numbers, EPFO Number, BPL Card, Employee Payroll etc.)"><i
													class="fa fa-info-circle text-info"></i></a> <span>*</span> <small>(Upload
													doc less than 5 MB in PDF)</small>
											</label>
											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" name="noBPLEmplDoc" id="noBPLEmplDoc" onchange="return WorkersfrBPL(event)">
												<span class="text-danger"></span>
											 <label class="custom-file-label file" id="choosefilenoBPLEmplDoc" for="noBPLEmplDoc">Choose
													file</label>
											</div>
											<span id="noBPLEmplDocMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12 ScDocs">
										<div class="form-group">
											<label>Number of SC Employees</label>
											<form:input path="noSCEmpl" value="${totalSC}" type="text" class="form-control"
												id="noSCEmpl" name="noSCEmpl" readonly="true"></form:input>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Particulars of Workers from SC families with
												supporting documents and Employee Payroll <span>*</span> <small>(Upload
													doc less than 5 MB in PDF)</small>
											</label>
											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" name="noSCEmplDoc" id="noSCEmplDoc" onchange="return WorkersfrSC(event)">
												<span class="text-danger"></span>
												<label class="custom-file-label file" id="choosefilenoSCEmplDoc" for="noSCEmplDoc">Choose
													file</label>
											</div>
											<span id="noSCEmplDocMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Number of ST Employees</label>
											<form:input path="noSTEmpl"  type="text" class="form-control"
												id="noSTEmpl" name="noSTEmpl" value="${totalSt}" readonly="true"></form:input>
										</div>
									</div>
									<div class="col-sm-12 StDocs">
										<div class="form-group">
											<label>Particulars of Workers from ST families with
												supporting documents and Employee Payroll <span>*</span> <small>(Upload
													doc less than 5 MB in PDF)</small>
											</label>

											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" name="noSTEmplDoc" id="noSTEmplDoc" onchange="return WorkersfrST(event)">
												<span class="text-danger"></span>
												<label class="custom-file-label file" id="choosefilenoSTEmplDoc" for="noSTEmplDoc">Choose
													file</label>
											</div>
											<span id="noSTEmplDocMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Number of Female Employees</label>
											<form:input path="noFemaleEmpl" type="text"
												class="form-control" id="noFemaleEmpl" value="${totalFemaleEmp}" readonly="true" name="noFemaleEmpl"></form:input>
										</div>
									</div>
									<div class="col-sm-12 FemaleDocs">
										<div class="form-group">
											<label>Particulars of female workers with supporting
												documents <span>*</span> <small>(Upload doc less
													than 5 MB in PDF)</small>
											</label>

											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" id="noFemaleEmplDoc" name="noFemaleEmplDoc" onchange="return WorkersFemale(event)">
												<span class="text-danger"></span>
												<label class="custom-file-label file" id="choosefilenoFemaleEmplDoc" for="noFemaleEmplDoc">Choose
													file</label>
											</div>
											<span id="noFemaleEmplDocMsg" class="text-danger"></span>
										</div>
									</div>
								</div>

								<hr>

								<div class="row">
									<div class="col-sm-5">
										<a href="./disbIncentiveCapInv" onclick="return confirm('Are you sure you want to go to Capital Investment?')"
											class="common-default-btn mt-3 prev-step">Back</a>
									</div>
									<div class="col-sm-7 text-right">

										<!-- <a href="javascript:void();" class="common-btn mt-3">Save</a>
										<a href="Dis-incentive.html" class="common-btn mt-3">Next</a> -->
										<form:button class="common-btn mt-3" onclick="return EmplDtlValidation()">Save And Next</form:button>
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