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
<title>Infrastructure Interest Subsidy</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->

<%@ include file="../head.jsp"%>
<!-- Please write your custom JS here -->

<script type="text/javascript">
$(document).ready(function() {
	var mySelect = $('.financial-year');
	var startYear = 2051;
	var prevYear = 2050;
	for (var i = 0; i < 60; i++) {
	  startYear = startYear - 1;
	  prevYear = prevYear - 1;
	  mySelect.append(
	    $('<option></option>').val(prevYear + "-" + startYear).html(prevYear + "-" + startYear)
	  );
	}
	if ("${iisincentiveDeatilsForm.yearI}" == null || "${iisincentiveDeatilsForm.yearI}" == '') {
		  $("#financialYear1 option").removeAttr("selected");
	}
	if ("${iisincentiveDeatilsForm.yearII}" == null || "${iisincentiveDeatilsForm.yearII}" == '') {
		  $("#financialYear2 option").removeAttr("selected");
	}
	if ("${iisincentiveDeatilsForm.yearIII}" == null || "${iisincentiveDeatilsForm.yearIII}" == '') {
		  $("#financialYear3 option").removeAttr("selected");
	}
	if ("${iisincentiveDeatilsForm.yearIV}" == null || "${iisincentiveDeatilsForm.yearIV}" == '') {
		  $("#financialYear4 option").removeAttr("selected");
	}
	if ("${iisincentiveDeatilsForm.yearV}" == null || "${iisincentiveDeatilsForm.yearV}" == '') {
		  $("#financialYear5 option").removeAttr("selected");
	}
	
});
</script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						console.log("document loaded");
						if ("${iisincentiveDeatilsForm.sectionletterDocbase64File}" == null
								|| "${iisincentiveDeatilsForm.sectionletterDocbase64File}" == '') {

						} else {
							document
									.getElementById('choosefileSectionletterDoc').innerHTML = '${iisincentiveDeatilsForm.sectionletter}';
							document.getElementById("sectionletter").src = "data:image/png;base64,${iisincentiveDeatilsForm.sectionletterDocbase64File}";
						}

						if ("${iisincentiveDeatilsForm.certifyingLoanDocbase64File}" == null
								|| "${iisincentiveDeatilsForm.certifyingLoanDocbase64File}" == '') {

						} else {
							document.getElementById('choosefileCertifyingLoan').innerHTML = '${iisincentiveDeatilsForm.certifyingLoan}';
							document.getElementById("certifyingLoan").src = "data:image/png;base64,${iisincentiveDeatilsForm.certifyingLoanDocbase64File}";
						}

						if ("${iisincentiveDeatilsForm.auditedAccountsDocbase64File}" == null
								|| "${iisincentiveDeatilsForm.auditedAccountsDocbase64File}" == '') {

						} else {
							document
									.getElementById('choosefileAuditedAccounts').innerHTML = '${iisincentiveDeatilsForm.auditedAccounts}';
							document.getElementById("auditedAccounts").src = "data:image/png;base64,${iisincentiveDeatilsForm.auditedAccountsDocbase64File}";
						}

						if ("${iisincentiveDeatilsForm.fiBankCertificateDocbase64File}" == null
								|| "${iisincentiveDeatilsForm.fiBankCertificateDocbase64File}" == '') {
							document.getElementById('verified').checked = false;

						} else {
							document
									.getElementById('choosefilefiBankCertificate').innerHTML = '${iisincentiveDeatilsForm.fiBankCertificate}';
							document.getElementById("fiBankCertificate").src = "data:image/png;base64,${iisincentiveDeatilsForm.fiBankCertificateDocbase64File}";
							document.getElementById('verified').checked = true;
						}

					});
</script>

<script type="text/javascript">


$(document).ready(function(){

		$('#verified:checked').prop('checked',false);

		});

function showAndHidePM() {

	var pmvalue = document.getElementById("selectedAvailPM").value;

	if (pmvalue == 'Yes') {
		var pp = 0;
		 $('#amtInvPNMIis').val(pp);
		$('.amtLoan').show();
		$('.amtInvPM').hide();
	}

	if (pmvalue == 'No') {
		var pp = 0;
		 $('#iisloan').val(pp);
		$('.amtLoan').hide();
		$('.amtInvPM').show();

	}

}
$(document).ready(showAndHidePM);
$(document).on("change", showAndHidePM);

</script>
<script type="text/javascript">
	function AllYearTotalIntrest() {
		var sum = 0;
		$(".AllYearTotlIntrst").each(function() {
			sum += +$(this).val();
		});
		$(".totalofTI", this).val(sum);
	}

	$(document).ready(AllYearTotalIntrest);
	$(document).on("keyup", AllYearTotalIntrest);
	$(document).on("change", AllYearTotalIntrest);

	function AllYearTotalPrincipal() {
		var sum = 0;
		$(".AllYearTotlPrincipal").each(function() {
			sum += +$(this).val();
		});
		$(".totalofPrincipal", this).val(sum);
	}

	$(document).ready(AllYearTotalPrincipal);
	$(document).on("keyup", AllYearTotalPrincipal);
	$(document).on("change", AllYearTotalPrincipal);

	function AllYearTotalInterest() {
		var sum = 0;
		$(".AllYearTotlInterest").each(function() {
			sum += +$(this).val();
		});
		$(".totalofInterest", this).val(sum);
	}

	$(document).ready(AllYearTotalInterest);
	$(document).on("keyup", AllYearTotalInterest);
	$(document).on("change", AllYearTotalInterest);

	function AllYearTotalAmtSubsidy() {
		var sum = 0;
		$(".AllYearTotlAmtSubsidy").each(function() {
			sum += +$(this).val();
		});
		$(".totalofAmtSubsidy", this).val(sum);
	}

	$(document).ready(AllYearTotalAmtSubsidy);
	$(document).on("keyup", AllYearTotalAmtSubsidy);
	$(document).on("change", AllYearTotalAmtSubsidy);

	function emptyCapInvErrMsg() { 
    	$("#BEName").text('');
    	$("#BAddress").text('');
    	$("#AmtAdmtTaxDeptGst").text('');
    	$("#iisloanMsg").text('');
    	$("#totalintMsg").text('');
    	$("#roiMsg").text('');
    	$("#datepickerMsg").text('');
    	$("#disbursedloanMsg").text('');
    }
	
</script>
<script type="text/javascript">

function nameOfBank()
{

	var letters = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/;
	var businessEntityName = document.getElementById("bankname").value;
	if (businessEntityName == null || businessEntityName == '') {
		document.getElementById('BEName').innerHTML = "Field is Required.";
		document.getElementById("bankname").focus();
		return false;
	} 
else if (businessEntityName.match(/[^a-zA-Z0-9]+$/)) {
		
		document.getElementById('BEName').innerHTML = "Banks/FI Name not allow only special character.";
		document.getElementById("bankname").focus();
		return false;
}
else if (businessEntityName.match(/[^a-zA-Z0-9]+$/)) {
	
	document.getElementById('BEName').innerHTML = "Banks/FI Name not allow only special character.";
	document.getElementById("bankname").focus();
	return false;
}
else if(businessEntityName.match(/[?!^\d+$)^.]+$/))
		{
		document.getElementById('BEName').innerHTML = "Banks/FI Name not allow only number.";
		document.getElementById("bankname").focus();
		return false;

		}
	else {
		document.getElementById('BEName').innerHTML = "";
	}
}
function addressOfBank()
{

	var businessAddress = document.getElementById("bankadd").value;
	if (businessAddress == null || businessAddress == '') {
		document.getElementById('BAddress').innerHTML = "Field is Required.";
		document.getElementById("bankadd").focus();
		return false;
	} else if (businessAddress.match(/[^a-zA-Z0-9]+$/)) {
		
		document.getElementById('BAddress').innerHTML = "Address of Banks/FI not allow only special character.";
		document.getElementById("bankadd").focus();
		return false;
		
	}
	else if(businessAddress.match(/[?!^\d+$)^.]+$/))
	{
	document.getElementById('BAddress').innerHTML = "Banks/FI Address not allow only number.";
	document.getElementById("bankadd").focus();
	return false;

	}

	else {
       document.getElementById('BAddress').innerHTML = "";  
   }

} 

</script>


<script type="text/javascript">
  function validateNumberField() {
		return (event.charCode > 47 && event.charCode < 58)

	}

	function validateTextField() {
		return (event.charCode > 64 && event.charCode < 91)
				|| (event.charCode > 96 && event.charCode < 123)
				|| (event.charCode == 32)
	}
	 
</script>

<script type="text/javascript">
	function disIISValidation() {

	
		var amtAdmtTaxDeptGst = document.getElementById('total').value;
		if (amtAdmtTaxDeptGst == null || amtAdmtTaxDeptGst == '') {
			document.getElementById('AmtAdmtTaxDeptGst').innerHTML = "Field Tax is Required";
			document.getElementById('total').focus();
			return false;
		} else {
	        document.getElementById('AmtAdmtTaxDeptGst').innerHTML = "";
	    }


		var pnmloandata = document.getElementById('iisloan').value;
		if (pnmloandata == null || pnmloandata == '') {
			document.getElementById('iisloanMsg').innerHTML = "Field is Required";
			document.getElementById('iisloan').focus();
			return false;
		} else {
	        document.getElementById('iisloanMsg').innerHTML = "";
	    }


		var amtPNMiis = document.getElementById('amtInvPNMIis').value;
		if (amtPNMiis == null || amtPNMiis == '') {
			document.getElementById('amtInvPNMIisMsg').innerHTML = "Field is Required";
			document.getElementById('amtInvPNMIis').focus();
			return false;
		} else {
	        document.getElementById('amtInvPNMIisMsg').innerHTML = "";
	    }


		var totalintdata = document.getElementById('totalint').value;
		if (totalintdata == null || totalintdata == '') {
			document.getElementById('totalintMsg').innerHTML = "Field is Required";
			document.getElementById('totalint').focus();
			return false;
		} else {
	        document.getElementById('totalintMsg').innerHTML = "";
	    }

		var amtAdmtTaxDeptGst = document.getElementById('roi').value;
		if (amtAdmtTaxDeptGst == null || amtAdmtTaxDeptGst == '') {
			document.getElementById('roiMsg').innerHTML = "Field is Required";
			document.getElementById('roi').focus();
			return false;
		} else {
	        document.getElementById('roiMsg').innerHTML = "";
	    }
	    
		var amtAdmtTaxDeptGst = document.getElementById('datepicker').value;
		if (amtAdmtTaxDeptGst == null || amtAdmtTaxDeptGst == '') {
			document.getElementById('datepickerMsg').innerHTML = "Date of Sanction is Required";    
			document.getElementById('datepicker').focus();
			return false;
		} else {
	        document.getElementById('datepickerMsg').innerHTML = "";
	    }

		var amtAdmtTaxDeptGst = document.getElementById('disbursedloan').value;
		if (amtAdmtTaxDeptGst == null || amtAdmtTaxDeptGst == '') {
			document.getElementById('disbursedloanMsg').innerHTML = "Field is Required";
			document.getElementById('disbursedloan').focus();
			return false;
		} else {
	        document.getElementById('disbursedloanMsg').innerHTML = "";
	    }


		
		if(!DocumentSectionLetterDoc(document.getElementById('sectionletter'))){
			return false;
		}

		if(!CertificateFrBank(document.getElementById('certifyingLoan'))){
			return false;
		}

    	if(!CertificateFrNoDefault(document.getElementById('auditedAccounts'))){
			return false;
		}


    	if(!CertificateBankFI(document.getElementById('fiBankCertificate'))){
			return false;
		}
		

		if (!$('#verified').is(':checked')) {
			alert(' Please select/check the Declaration');
			$("#verified").focus();
			return false;
		}

		var r = confirm("Are you Sure you want to Submit the Form?");

		if (r == true) {
			alert("Application Form is Submitted Succesfully");
		} else {
			return false
		}

		}

</script>


<script type="text/javascript">


function DocumentSectionLetterDoc(file) {
	
	 var sectionLetterDoc = document.getElementById('sectionletter').value;
	 var fileSize = document.getElementById("sectionletter").files[0];
	 var maxSize = '5000';

	 if('${iisincentiveDeatilsForm.sectionletterDocbase64File}' != '' && sectionLetterDoc == ''){
			return true;
		}
		
	 if(sectionLetterDoc!=null && sectionLetterDoc!=''){
	 var ext = sectionLetterDoc.split('.').pop();
	 if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('sectionletterMsg').innerHTML = "Please Upload Support documnet in PDF Format";
		document.getElementById('sectionletter').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
  if (FileSize >5) {
  	document.getElementById('sectionletterMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
  	document.getElementById('sectionletter').focus();
		return false;
  }
  
	} else{
		document.getElementById('sectionletterMsg').innerHTML = "Sanction Letter document is Required.";
		document.getElementById('sectionletter').focus();
		return false;
	  }
		    document.getElementById('sectionletterMsg').innerHTML = "";
		    return true;
}

function CertificateFrBank(file) {
	
	 var loanDocument = document.getElementById('certifyingLoan').value;
	 var fileSize = document.getElementById("certifyingLoan").files[0];
	 var maxSize = '5000';

	 if('${iisincentiveDeatilsForm.certifyingLoanDocbase64File}' != '' && loanDocument == ''){
			return true;
		}
		
	 if(loanDocument!=null && loanDocument!=''){
	 var ext = loanDocument.split('.').pop();
	 if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('certifyingLoanMsg').innerHTML = "Please Upload Support documnet in PDF Format";
		document.getElementById('certifyingLoan').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
 if (FileSize >5) {
 	document.getElementById('certifyingLoanMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
 	document.getElementById('certifyingLoan').focus();
		return false;
 }
 
	} else{
		document.getElementById('certifyingLoanMsg').innerHTML = "Certificate from FI/Bank document is Required.";
		document.getElementById('certifyingLoan').focus();
		return false;
	  }
		    document.getElementById('certifyingLoanMsg').innerHTML = "";
		    return true;
}

function CertificateFrNoDefault(file) {
	
	 var NoDefault = document.getElementById('auditedAccounts').value;
	 var fileSize = document.getElementById("auditedAccounts").files[0];
	 var maxSize = '5000';

	 if('${iisincentiveDeatilsForm.auditedAccountsDocbase64File}' != '' && NoDefault == ''){
			return true;
		}
		
	 if(NoDefault!=null && NoDefault!=''){
	 var ext = NoDefault.split('.').pop();
	 if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('auditedAccountsMsg').innerHTML = "Please Upload Support documnet in PDF Format";
		document.getElementById('auditedAccounts').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
if (FileSize >5) {
	document.getElementById('auditedAccountsMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	document.getElementById('auditedAccounts').focus();
		return false;
}

	} else{
		document.getElementById('auditedAccountsMsg').innerHTML = "Certificate from FI/Bank for Reim is Required.";
		document.getElementById('auditedAccounts').focus();
		return false;
	  }
		    document.getElementById('auditedAccountsMsg').innerHTML = "";
		    return true;
}

function CertificateBankFI(file) {
	
	 var BankFIDocument = document.getElementById('fiBankCertificate').value;
	 var fileSize = document.getElementById("fiBankCertificate").files[0];
	 var maxSize = '5000';

	 if('${iisincentiveDeatilsForm.fiBankCertificateDocbase64File}' != '' && BankFIDocument == ''){
			return true;
		}
		
	 if(BankFIDocument!=null && BankFIDocument!=''){
	 var ext = BankFIDocument.split('.').pop();
	 if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('fiBankCertificateMsg').innerHTML = "Please Upload Support documnet in PDF Format";
		document.getElementById('fiBankCertificate').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
if (FileSize >5) {
	document.getElementById('fiBankCertificateMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	document.getElementById('fiBankCertificate').focus();
		return false;
}

	} else{
		document.getElementById('fiBankCertificateMsg').innerHTML = "Certification from FI/Bank document is Required.";
		document.getElementById('fiBankCertificate').focus();
		return false;
	  }
		    document.getElementById('fiBankCertificateMsg').innerHTML = "";
		    return true;
}

</script>

<script type="text/javascript">
 function propInfra(){
 var bankcert = document.getElementById("selectedAvailPM").value;


 if (bankcert == 'No') {

	 var eligibleFixedCapitalInvestment =${eligibleFixedCapitalInvestment};
	 var infra =${infra};
	 $("#amtInvPNMIis", this).val(infra);
	 var infraLoan = document.getElementById('amtInvPNMIis').value; 
	
	 var proprotion = infraLoan/eligibleFixedCapitalInvestment;
	

	 var firstTotIntLoan = document.getElementById("ttlInterestLoan1").value;
	
	  var secondTotIntLoan = document.getElementById("ttlInterestLoan2").value;
	  var thirdTotIntLoan = document.getElementById("ttlInterestLoan3").value;
	  var fourthTotIntLoan = document.getElementById("ttlInterestLoan4").value;
	  var fifthTotIntLoan = document.getElementById("ttlInterestLoan5").value;
	 
	
	 var propIntv1 =Math.round (firstTotIntLoan*proprotion) ;
	 var propIntv2 =Math.round (secondTotIntLoan*proprotion) ;
	 var propIntv3 =Math.round (thirdTotIntLoan*proprotion) ;
	 var propIntv4 =Math.round (fourthTotIntLoan*proprotion) ;
	 var propIntv5 =Math.round (fifthTotIntLoan*proprotion) ;
	
	 $("#claimInterest1", this).val(propIntv1);
	 $("#claimInterest2", this).val(propIntv2);
	 $("#claimInterest3", this).val(propIntv3);
	 $("#claimInterest4", this).val(propIntv4);
	 $("#claimInterest5", this).val(propIntv5); 
	 $("input[id='claimInterest1']").attr("readonly", true);
	 $("input[id='claimInterest2']").attr("readonly", true);
	 $("input[id='claimInterest3']").attr("readonly", true);
	 $("input[id='claimInterest4']").attr("readonly", true);
	 $("input[id='claimInterest5']").attr("readonly", true);
	 
 } else
 {
	 $("input[name='firstYI']").attr("readonly", false);
	 $("input[name='secondYI']").attr("readonly", false);
	 $("input[name='thirdYI']").attr("readonly", false);
	 $("input[name='fourthYI']").attr("readonly", false);
	 $("input[name='fifthYI']").attr("readonly", false);
	 }
  	 
 }
 $(document).ready(propInfra);
 $(document).on("change", propInfra);

 </script>
 
 <script type="text/javascript">
 function enabledisable() {

	 var bankcert = document.getElementById("selectedAvailPM").value;

	 if(bankcert =='Yes')

	 $("input[name='firstYI']").attr("readonly", false);

	// $("input[name='invLandCost']").attr("readonly", false);


	 }
 $(document).ready(enabledisable);
 $(document).on("change", enabledisable);
 </script>

</head>

<%@ include file="../topMenu.jsp"%>


<section class="common-form-area">
	<div class="container">
		<!-- Main Title -->
		<div class="inner-banner-text">
			<h1>Application Form</h1>
		</div>
		<div class="row">

			<form:form modelAttribute="iisincentiveDeatilsForm"
				name="iisincentiveDeatilsForm" method="post" id="myForm"
				action="disIisSave" enctype="multipart/form-data">

				<div class="col-sm-12">
					<div class="form-card">
						<div class="without-wizard-steps">
							<h4 class="card-title mb-4 mt-4 text-center">Infrastructure
								Interest Subsidy</h4>
							<div class="isf-form">
								<form class="mt-4">

									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Bank/Financial Institutions
												Details from which Loan Availed</h3>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<form:label path="bankname">Name of Banks/Financial Institutions </form:label>
												<form:input id="bankname" path="bankname" name="bankname"
													maxlength="250" class="form-control" value=""
													onkeyup="emptyCapInvErrMsg();nameOfBank()"></form:input>

												<span id="BEName" class="text-danger"></span>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<form:label path="bankadd">Address of Banks/Financial Institutions </form:label>

												<form:input id="bankadd" path="bankadd" name="bankadd"
													class="form-control" maxlength="250"
													onkeyup="emptyCapInvErrMsg();addressOfBank()"></form:input>

												<span id="BAddress" class="text-danger"></span>


											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<form:label path="total">Total Loan Taken <small>(In
														Rs.)</small>
												</form:label>


												<form:input id="total" path="total"
													placeholder="Enter Amount in INR" maxlength="12"
													class="form-control is-numeric"
													value="${totalLoan}" readonly="true"></form:input>
												<small class="words text-info"></small> <span
													id="AmtAdmtTaxDeptGst" class="text-danger"></span>


											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<form:label path="bankcert">Is FI/ Bank certified amount of loan on Infra available?</form:label>
												<form:select path="bankcert" class="form-control" id="selectedAvailPM"  onchange="showAndHidePM()" onblur="return enabledisable()">
													<form:option value="">Select One</form:option>
													<form:option value="Yes">Yes</form:option>
													<form:option value="No">No</form:option>
												</form:select>
											</div>
										</div>
										<div class="col-sm-6 amtLoan">
											<div class="form-group">
												<form:label path="iisloan">FI/ Bank certified amount of loan on Infra</form:label>
												<form:input id="iisloan" path="iisloan"
													placeholder="Enter Amount in INR" maxlength="12"
													class="form-control is-numeric " 
													onkeypress="return validateNumberField()"
													onkeyup="emptyCapInvErrMsg()"></form:input>
												<span class="text-danger"></span> <small
													class="words text-info"></small> <span id="iisloanMsg"
													class="text-danger"></span>

											</div>
										</div>
										
										<div class="col-sm-6 amtInvPM">
												<div class="form-group">
													<form:label path="">Amount Investment in Infrastructure Facilities</form:label>
													<form:input id="amtInvPNMIis" path="amtInvPNMIis"
														placeholder="Enter Amount in INR" maxlength="12"
														class="form-control is-numeric"  value = "${infra}" readonly="true"
														onkeypress="return validateNumberField()" onkeyup="emptyCapInvErrMsg()"></form:input>
														<small class="words text-info"></small>
                                                     <span id="amtInvPNMIisMsg" class="text-danger"></span>

												</div>
											</div>
										
										<div class="col-sm-6">
											<div class="form-group">
												<form:label path="bankcertletter">Sanction Letter, Agreement with FI/Bank <span>*</span>
													<small>(Upload doc less than 5 MB in PDF)</small>
												</form:label>
												<div class="custom-file">
													<input type="file" class="custom-file-input user-file"
														maxlength="10" id="sectionletter" name="sectionletter"
														onchange="return DocumentSectionLetterDoc(event)">
													<span class="text-danger error_msg"></span>
													<form:label path="sectionletter"
														class="custom-file-label file"
														id="choosefileSectionletterDoc" for="sectionletter">Choose file</form:label>
												</div>
												<span id="sectionletterMsg" class="text-danger"></span>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<form:label path="totalint">Total Interest</form:label>
												<form:input path="totalint" maxlength="12" type="text"
													class="form-control is-numeric " id="totalint"
													value="${totalInt}" readonly="true"></form:input>
												<span class="text-danger"></span> <small
													class="words text-info"></small> <span id="totalintMsg"
													class="text-danger"></span>

											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<form:label path="roi">Rate of Interest</form:label>
												<form:input path="roi" maxlength="5" type="text"
													class="form-control" id="roi"
													
													onkeyup="emptyCapInvErrMsg()"></form:input>

												<span id="roiMsg" class="text-danger"></span>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label>Date of Sanction</label>
												<form:input path="sanctiondate" maxlength="12" type="date"
													class="form-control " id="datepicker"
													onkeyup="emptyCapInvErrMsg()"></form:input>
												<span class="text-danger"></span> <span id="datepickerMsg"
													class="text-danger"></span>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<form:label path="disbursedloan">Amount of Loan Disbursed towards Infrastructure Facilities. </form:label>
												<form:input path="disbursedloan" id="disbursedloan"
													maxlength="12" placeholder="Enter Amount in INR"
													class="form-control is-numeric "
													onkeypress="return validateNumberField()"
													onkeyup="emptyCapInvErrMsg()"></form:input>
												<small class="words text-info"></small> <span
													id="disbursedloanMsg" class="text-danger"></span>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Required Supporting Documents</h3>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<form:label path="certifyingLoan">Certificate from FI/Bank certifying loan for Infrastructure and interest & other relevant details. <span>*</span>
													<small>(Upload doc less than 5 MB in PDF)</small>
												</form:label>
												<div class="custom-file">
													<input type="file" class="custom-file-input user-file"
														onchange="return CertificateFrBank(event)"
														id="certifyingLoan" name="certifyingLoan"> <span
														class="text-danger error_msg"></span>
													<form:label path="certifyingLoan"
														class="custom-file-label file"
														id="choosefileCertifyingLoan" for="certifyingLoan">Choose file</</form:label>

												</div>
												<span id="certifyingLoanMsg" class="text-danger"></span>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<form:label path="auditedAccounts"> Certificate from FI/Bank for No Default in the account during the entire period for which reimbursement claimed. <span>*</span>
													<small>(Upload doc less than 5 MB in PDF)</small>
												</form:label>
												<div class="custom-file">
													<input type="file" name="auditedAccounts"
														onchange="return CertificateFrNoDefault(event)"
														class="custom-file-input user-file" id="auditedAccounts">
													<span class="text-danger error_msg"></span>
													<form:label path="auditedAccounts"
														class="custom-file-label file"
														id="choosefileAuditedAccounts" for="auditedAccounts">Choose file</</form:label>

												</div>
												<span id="auditedAccountsMsg" class="text-danger"></span>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Particulars of Claims for
												Sanction of Infrastructure Interest Subsidy</h3>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-bordered">
													<thead>
														<tr>
															<th rowspan="2">Year for which subsidy Applied</th>
															<th colspan="2">Duration/Period</th>
															<th colspan="3">Payment made to FI/Bank during the
																year</th>
															<th rowspan="2" width="20%">Amount of Interest
																Subsidy Applied</th>
														</tr>
														<tr>
														<th width="5%">From</th>
																<th>To</th>
															<th>Total Interest</th>
															<th>Principal</th>
															<th>Interest on Infrastructure</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>Year I &nbsp;&nbsp; <form:select path="yearI"
																	type="text" class="form-control financial-year"
																	id="financialYear1" name="financialYear1">
																	<form:option value="" selected="selected">-Select Financial Year-</form:option>
																	<form:option value="${iisincentiveDeatilsForm.yearI}" />
																</form:select> <span id="year1Msg" class="text-danger"></span></td>
																
															<td><form:input path="durationPeriodDtFr1" type="date"
																class="form-control" id="durationPeriodIdFr1"
																name="durationPeriodDtFr1"></form:input> <span id="durationPeriodDtFrMsg1"
															class="text-danger"></span></td>
															
															<td><form:input path="durationPeriodDtTo1" type="date"
																class="form-control" id="durationPeriodIdTo1"
																name="durationPeriodDtTo1"></form:input> <span id="durationPeriodDtToMsg1"
															class="text-danger"></span></td>
																
															<td><form:input path="firstYTI" type="text" id ="ttlInterestLoan1"
																	placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlIntrst"
																	name="firstYTI" maxlength="12"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="firstYP" type="text"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlPrincipal"
																	name="firstYP"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="firstYI" type="text" id="claimInterest1"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlInterest"
																	name="firstYI"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="firstYAmtIntSubsidy"
																	type="text" maxlength="12"
																	placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlAmtSubsidy"
																	name="firstYAmtIntSubsidy"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
														</tr>
														<tr>
															<td>Year II &nbsp;<form:select path="yearII"
																	type="text" placeholder="Enter Year"
																	class="form-control financial-year" id="financialYear2"
																	name="financialYear2">
																	<form:option value="" selected="selected">-Select Financial Year-</form:option>
																	<form:option value="${iisincentiveDeatilsForm.yearII}"></form:option>
																</form:select> <span id="year2Msg" class="text-danger"></span></td>
																
																<td><form:input path="durationPeriodDtFr2" type="date"
																class="form-control" id="durationPeriodIdFr2"
																name="durationPeriodDtFr2"></form:input> <span id="durationPeriodDtFrMsg2"
															class="text-danger"></span></td>
															
															<td><form:input path="durationPeriodDtTo2" type="date"
																class="form-control" id="durationPeriodIdTo2"
																name="durationPeriodDtTo2"></form:input> <span id="durationPeriodDtToMsg2"
															class="text-danger"></span></td>
																
															<td><form:input path="secondYTI" type="text" id="ttlInterestLoan2"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlIntrst"
																	name="secondYTI"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="secondYP" type="text"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlPrincipal"
																	name="secondYP"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="secondYI" type="text" id="claimInterest2"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlInterest"
																	name="secondYI"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="secondYAmtIntSubsidy"
																	type="text" maxlength="12"
																	placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlAmtSubsidy"
																	name="secondYAmtIntSubsidy"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>

														</tr>
														<tr>
															<td>Year III <form:select path="yearIII" type="text"
																	placeholder="Enter Year"
																	class="form-control financial-year" id="financialYear3"
																	name="financialYear3">
																	<form:option value="" selected="selected">-Select Financial Year-</form:option>
																	<form:option value="${iisincentiveDeatilsForm.yearIII}"></form:option>
																</form:select> <span id="year3Msg" class="text-danger"></span></td>
																
																<td><form:input path="durationPeriodDtFr3" type="date"
																class="form-control" id="durationPeriodIdFr3"
																name="durationPeriodDtFr3"></form:input> <span id="durationPeriodDtFrMsg3"
															class="text-danger"></span></td>
															
															<td><form:input path="durationPeriodDtTo3" type="date"
																class="form-control" id="durationPeriodIdTo3"
																name="durationPeriodDtTo3"></form:input> <span id="durationPeriodDtToMsg3"
															class="text-danger"></span></td>
																
															<td><form:input path="thirdYTI" type="text" id="ttlInterestLoan3"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlIntrst"
																	name="thirdYTI"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="thirdYP" type="text"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlPrincipal"
																	name="thirdYP"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="thirdYI" type="text" id="claimInterest3"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlInterest"
																	name="thirdYI"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="thirdYAmtIntSubsidy"
																	type="text" maxlength="12"
																	placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlAmtSubsidy"
																	name="thirdYAmtIntSubsidy"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
														</tr>
														<tr>
															<td>Year IV&nbsp;<form:select path="yearIV"
																	type="text" placeholder="Enter Year"
																	class="form-control financial-year" id="financialYear4"
																	name="financialYear4">
																	<form:option value="" selected="selected">-Select Financial Year-</form:option>
																	<form:option value="${iisincentiveDeatilsForm.yearIV}"></form:option>
																</form:select> <span id="year4Msg" class="text-danger"></span></td>
																
																<td><form:input path="durationPeriodDtFr4" type="date"
																class="form-control" id="durationPeriodIdFr4"
																name="durationPeriodDtFr4"></form:input> <span id="durationPeriodDtFrMsg4"
															class="text-danger"></span></td>
															
															<td><form:input path="durationPeriodDtTo4" type="date"
																class="form-control" id="durationPeriodIdTo4"
																name="durationPeriodDtTo4"></form:input> <span id="durationPeriodDtToMsg4"
															class="text-danger"></span></td>
																
															<td><form:input path="fourthYTI" type="text" id="ttlInterestLoan4"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlIntrst"
																	name="fourthYTI"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="fourthYP" type="text"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlPrincipal"
																	name="fourthYP"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="fourthYI" type="text" id="claimInterest4"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlInterest"
																	name="fourthYI"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="fourthYAmtIntSubsidy"
																	type="text" maxlength="12"
																	placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlAmtSubsidy"
																	name="fourthYAmtIntSubsidy"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>

														</tr>
														<tr>
															<td>Year V &nbsp;<form:select path="yearV"
																	type="text" placeholder="Enter Year"
																	class="form-control financial-year" id="financialYear5"
																	name="financialYear5">
																	<form:option value="" selected="selected">-Select Financial Year-</form:option>
																	<form:option value="${iisincentiveDeatilsForm.yearV}"></form:option>
																</form:select> <span id="year5Msg" class="text-danger"></span></td>
																
																
																<td><form:input path="durationPeriodDtFr5" type="date"
																class="form-control" id="durationPeriodIdFr5"
																name="durationPeriodDtFr5"></form:input> <span id="durationPeriodDtFrMsg5"
															class="text-danger"></span></td>
															
															<td><form:input path="durationPeriodDtTo5" type="date"
																class="form-control" id="durationPeriodIdTo5"
																name="durationPeriodDtTo5"></form:input> <span id="durationPeriodDtToMsg5"
															class="text-danger"></span></td>
																
															<td><form:input path="fifthYTI" type="text" id="ttlInterestLoan5"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlIntrst"
																	name="fifthYTI"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="fifthYP" type="text"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlPrincipal"
																	name="fifthYP"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="fifthYI" type="text" id="claimInterest5"
																	maxlength="12" placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlInterest"
																	name="fifthYI"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>
															<td><form:input path="fifthYAmtIntSubsidy"
																	type="text" maxlength="12"
																	placeholder="Enter Amount in INR"
																	class="form-control is-numeric AllYearTotlAmtSubsidy"
																	name="fifthYAmtIntSubsidy"
																	onkeypress="return validateNumberField()"></form:input><small
																class="words text-info"></small></td>

														</tr>
														<tr>
															<td><strong>Total</strong></td>
															<td></td>
															<td></td>
															<td><input type="text"
																class="form-control totalofTI" readonly="true"
																name="totalofTI" maxlength="12"></td>
															<td><input type="text"
																class="form-control totalofPrincipal" readonly="true"
																name="totalofPrincipal" maxlength="12"></td>
															<td><input type="text"
																class="form-control totalofInterest" readonly="true"
																name="totalofIntrest" maxlength="12"></td>
															<td><input type="text"
																class="form-control totalofAmtSubsidy" readonly="true"
																name="totalofAmtSubsidy" maxlength="12"></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<label>Certification from FI/Bank required <span>*</span>
													<small>(Upload doc less than 5 MB in PDF)</small>
												</label>
												<div class="custom-file">
													<input type="file" class="custom-file-input user-file"
														maxlength="10" id="fiBankCertificate"
														onchange="return CertificateBankFI(event)"
														name="fiBankCertificate"> <span
														class="text-danger error_msg"></span>
													<form:label path="fiBankCertificate"
														class="custom-file-label file"
														id="choosefilefiBankCertificate" for="fiBankCertificate">Choose
															file</</form:label>

												</div>
												<span id="fiBankCertificateMsg" class="text-danger"></span>
											</div>
										</div>
									</div>

									<hr>

									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Declaration</h3>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group mt-3">
												<div
													class="custom-control custom-checkbox custom-control-inline">
													<input type="checkbox" class="custom-control-input"
														id="verified" value="Yes" name="Capital-Interest-Subsidy"><label
														class="custom-control-label" for="verified"> The
														above information are completely true and no fact has been
														concealed or misrepresented. It is further certified that
														the company has not applied for benefits of the above
														nature under any sector-specific or other policy of the
														Government of Uttar Pradesh for purpose of availing
														benefits of the above nature. I/we hereby agree that I/we
														shall forthwith repay the benefits released to me/us under
														Rules of Policy for Promotion of Industrial Investment and
														Employment-2017, if the said benefits are found to be
														disbursed in excess of the amount actually admissible
														whatsoever the reason. </label>
												</div>
											</div>
										</div>
									</div>

									<hr>

									<div class="row">
										<div class="col-sm-5">
											<a href="./disincentivetype"
												class="common-default-btn mt-3 prev-step"
												onclick="return confirm('Are you sure you want to go to SELECT INCENTIVE TYPE?')">Back</a>
										</div>

										<div class="col-sm-7 text-right">
											<button type="submit" class="common-btn"
												onclick="return disIISValidation()">Save</button>
										</div>
									</div>

								</form>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>

	</div>
</section>


<%@ include file="../footerMenu.jsp"%>



</body>
</html>