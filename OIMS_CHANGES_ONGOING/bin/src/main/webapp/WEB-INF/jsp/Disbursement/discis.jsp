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
<title>CAPITAL INTEREST SUBSIDY</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<%@ include file="../head.jsp"%>

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
	if ("${cisincentiveDeatilsForm.yearI}" == null || "${cisincentiveDeatilsForm.yearI}" == '') {
		  $("#financialYear1 option").removeAttr("selected");
	}
	if ("${cisincentiveDeatilsForm.yearII}" == null || "${cisincentiveDeatilsForm.yearII}" == '') {
		  $("#financialYear2 option").removeAttr("selected");
	}
	if ("${cisincentiveDeatilsForm.yearIII}" == null || "${cisincentiveDeatilsForm.yearIII}" == '') {
		  $("#financialYear3 option").removeAttr("selected");
	}
	if ("${cisincentiveDeatilsForm.yearIV}" == null || "${cisincentiveDeatilsForm.yearIV}" == '') {
		  $("#financialYear4 option").removeAttr("selected");
	}
	if ("${cisincentiveDeatilsForm.yearV}" == null || "${cisincentiveDeatilsForm.yearV}" == '') {
		  $("#financialYear5 option").removeAttr("selected");
	}
	
});
</script>

<script type="text/javascript">
$(document).ready(function() {
    console.log( "document loaded" );
    if ("${cisincentiveDeatilsForm.sectionletterDocbase64File}" == null
			|| "${cisincentiveDeatilsForm.sectionletterDocbase64File}" == '') {

	} else {
		document.getElementById('choosefileSectionletterDoc').innerHTML = '${cisincentiveDeatilsForm.sectionletter}';
		document.getElementById("sectionletter").src = "data:image/png;base64,${cisincentiveDeatilsForm.sectionletterDocbase64File}";
	}

	if ("${cisincentiveDeatilsForm.certifyingLoanDocbase64File}" == null
			|| "${cisincentiveDeatilsForm.certifyingLoanDocbase64File}" == '') {

	} else {
		document.getElementById('choosefileCertifyingLoan').innerHTML = '${cisincentiveDeatilsForm.certifyingLoan}';
		document.getElementById("certifyingLoan").src = "data:image/png;base64,${cisincentiveDeatilsForm.certifyingLoanDocbase64File}";
	}

	if ("${cisincentiveDeatilsForm.auditedAccountsDocbase64File}" == null
			|| "${cisincentiveDeatilsForm.auditedAccountsDocbase64File}" == '') {

	} else {
		document.getElementById('choosefileAuditedAccounts').innerHTML = '${cisincentiveDeatilsForm.auditedAccounts}';
		document.getElementById("auditedAccounts").src = "data:image/png;base64,${cisincentiveDeatilsForm.auditedAccountsDocbase64File}";
	}

	if ("${cisincentiveDeatilsForm.fiBankCertificateDocbase64File}" == null
			|| "${cisincentiveDeatilsForm.fiBankCertificateDocbase64File}" == '') {
		document.getElementById('verified').checked=false; 
	} else {
		document.getElementById('choosefilefiBankCertificate').innerHTML = '${cisincentiveDeatilsForm.fiBankCertificate}';
		document.getElementById("fiBankCertificate").src = "data:image/png;base64,${cisincentiveDeatilsForm.fiBankCertificateDocbase64File}";
		document.getElementById('verified').checked=true; 
		}

});
</script>

<script type="text/javascript">

$(document).ready(function(){

		$('#verified:checked').prop('checked',false);

	});

$(document).ready(function(){

	var pmvalue = document.getElementById("selectedAvailPM").value;

	if (pmvalue == 'Yes') {
		var pp = 0;
		 $('#amtInvPlantMachin').val(pp); 
		$('.amtLoan').show();
		$('.amtInvPM').hide();
	}

	if (pmvalue == 'No') {
		var pp = 0;
		 $('#pnmloan').val(pp);
		$('.amtLoan').hide();
		$('.amtInvPM').show();

	}
});



function showAndHidePM()
{

	var pmvalue = document.getElementById("selectedAvailPM").value;

	if (pmvalue == 'Yes') {
		var pp = 0;
		 $('#amtInvPlantMachin').val(pp); 
		$('.amtLoan').show();
		$('.amtInvPM').hide();
	}

	if (pmvalue == 'No') {
		var pp = 0;
		 $('#pnmloan').val(pp);
		$('.amtLoan').hide();
		$('.amtInvPM').show();

	}
	}

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
 	$("#pnmloanMsg").text('');
 	$("#totalintMsg").text('');
 	$("#roiMsg").text('');
 	$("#datepickerMsg").text('');
 	$("#disbursedloanMsg").text('');

 	$("#ttlInterestLoan1Msg").text('');
 	$("#claimPrinciple1Msg").text('');
 	$("#claimInterest1Msg").text('');
 	$("#claimSubsidy1Msg").text('');
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

 function nameOfBank()
 {

 	var letters = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/;
 	var businessEntityName = document.getElementById("bankname").value;
 	/* if (businessEntityName == null || businessEntityName == '') {
 		document.getElementById('BEName').innerHTML = "Field is Required.";
 		document.getElementById("bankname").focus();
 		return false;
 	}  */
 if (businessEntityName.match(/[^a-zA-Z0-9]+$/)) {
 		
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
	/* if (businessAddress == null || businessAddress == '') {
		document.getElementById('BAddress').innerHTML = "Field is Required.";
		document.getElementById("bankadd").focus();
		return false;
	} */ 
	if (businessAddress.match(/[^a-zA-Z0-9]+$/)) {
		
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
 function propPnM(){
 var bankcert = document.getElementById("selectedAvailPM").value;
 var pnmloanYes = document.getElementById('pnmloan').value;


 if (bankcert == 'No') {

	 var eligibleFixedCapitalInvestment =${eligibleFixedCapitalInvestment};
	 var noPnM =${noPnM};
	 $("#amtInvPlantMachin", this).val(noPnM);
	 var pnmloan = document.getElementById('amtInvPlantMachin').value; 
	
	 var proprotion = pnmloan/eligibleFixedCapitalInvestment;
	

	  var firstTotIntLoan = document.getElementById("ttlInterestLoan1").value;
	  var secondTotIntLoan = document.getElementById("ttlInterestLoan2").value;
	  var thirdTotIntLoan = document.getElementById("ttlInterestLoan3").value;
	  var fourthTotIntLoan = document.getElementById("ttlInterestLoan4").value;
	  var fifthTotIntLoan = document.getElementById("ttlInterestLoan5").value;
	 
	
	 var propIntv1 =Math.floor (firstTotIntLoan*proprotion) ;
	 var propIntv2 =Math.floor (secondTotIntLoan*proprotion) ;
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
	 }
 else{
	 $("input[name='firstYI']").attr("readonly", false);
	 $("input[name='secondYI']").attr("readonly", false);
	 $("input[name='thirdYI']").attr("readonly", false);
	 $("input[name='fourthYI']").attr("readonly", false);
	 $("input[name='fifthYI']").attr("readonly", false);
	 }
  	 
 }
 $(document).ready(propPnM);
 $(document).on("change", propPnM);

 </script>
 
 <script type="text/javascript">
 function enabledisable1() {

	 var bankcert = document.getElementById("selectedAvailPM").value;

	 if(bankcert =='Yes')

	 $("input[name='firstYI']").attr("readonly", false);
	 $("input[name='secondYI']").attr("readonly", false);
	 $("input[name='thirdYI']").attr("readonly", false);
	 $("input[name='fourthYI']").attr("readonly", false);
	 $("input[name='fifthYI']").attr("readonly", false);

	// $("input[name='invLandCost']").attr("readonly", false);


	 }
 //$(document).ready(enabledisable);
 //$(document).on("change", enabledisable);
 </script>
 
 <script type="text/javascript">
    function disCISValidation() {
        
    	var businessEntityName = document.getElementById("bankname").value;
     	if (businessEntityName == null || businessEntityName == '') {
     		document.getElementById('BEName').innerHTML = "Field is Required.";
     		document.getElementById("bankname").focus();
     		return false;
     	}
     		else{
     			 document.getElementById('BEName').innerHTML = "";
     	} 

     	var businessEntityName = document.getElementById("bankadd").value;
     	if (businessEntityName == null || businessEntityName == '') {
     		document.getElementById('BAddress').innerHTML = "Field is Required.";
     		document.getElementById("bankadd").focus();
     		return false;
     	}
     		else{
     			 document.getElementById('BAddress').innerHTML = "";
     	} 
     	
		var amtAdmtTaxDeptGst = document.getElementById('total').value;
		if (amtAdmtTaxDeptGst == null || amtAdmtTaxDeptGst == '') {
			document.getElementById('AmtAdmtTaxDeptGst').innerHTML = "Field is Required";
			document.getElementById('total').focus();
			return false;
		} else {
	        document.getElementById('AmtAdmtTaxDeptGst').innerHTML = "";
	    }


		var pnmloandata = document.getElementById('pnmloan').value;
		if (pnmloandata == null || pnmloandata == '') {
			document.getElementById('pnmloanMsg').innerHTML = "Field is Required";
			document.getElementById('pnmloan').focus();
			return false;
		} else {
	        document.getElementById('pnmloanMsg').innerHTML = "";
	    }

		var pnmloandata = document.getElementById('amtInvPlantMachin').value;
		if (pnmloandata == null || pnmloandata == '') {
			document.getElementById('amtInvPlantMachinMsg').innerHTML = "Field is Required";
			document.getElementById('amtInvPlantMachin').focus();
			return false;
		} else {
	        document.getElementById('amtInvPlantMachinMsg').innerHTML = "";
	    }

		if(!DocumentSectionLetterDoc(document.getElementById('sectionletter'))){   
			return false;
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

		if(!CertificateFrBank(document.getElementById('certifyingLoan'))){
			return false;
		}

    	if(!CertificateFrNoDefault(document.getElementById('auditedAccounts'))){
			return false;
		}
	

		var amtAdmtTaxDeptGst = document.getElementById('ttlInterestLoan1').value;
		if (amtAdmtTaxDeptGst == null || amtAdmtTaxDeptGst == '') {
			document.getElementById('ttlInterestLoan1Msg').innerHTML = "Field is Required";
			document.getElementById('ttlInterestLoan1').focus();
			return false;
		} else {
	        document.getElementById('ttlInterestLoan1Msg').innerHTML = "";
	    }

		var amtAdmtTaxDeptGst = document.getElementById('claimPrinciple1').value;
		if (amtAdmtTaxDeptGst == null || amtAdmtTaxDeptGst == '') {
			document.getElementById('claimPrinciple1Msg').innerHTML = "Field is Required";
			document.getElementById('claimPrinciple1').focus();
			return false;
		} else {
	        document.getElementById('claimPrinciple1Msg').innerHTML = "";
	    }

		var amtAdmtTaxDeptGst = document.getElementById('claimInterest1').value;
		if (amtAdmtTaxDeptGst == null || amtAdmtTaxDeptGst == '') {
			document.getElementById('claimInterest1Msg').innerHTML = "Field is Required";
			document.getElementById('claimInterest1').focus();
			return false;
		} else {
	        document.getElementById('claimInterest1Msg').innerHTML = "";
	    }

		var amtAdmtTaxDeptGst = document.getElementById('claimSubsidy1').value;
		if (amtAdmtTaxDeptGst == null || amtAdmtTaxDeptGst == '') {
			document.getElementById('claimSubsidy1Msg').innerHTML = "Field is Required";
			document.getElementById('claimSubsidy1').focus();
			return false;
		} else {
	        document.getElementById('claimSubsidy1Msg').innerHTML = "";
	    }
		

    	if(!CertificateBankFI(document.getElementById('fiBankCertificate'))){
			return false;
		}

    	
    	 //$(this).on("change", disCISValidation); 

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
	
	 var SectionLetterDoc = document.getElementById('sectionletter').value;
	 var fileSize = document.getElementById("sectionletter").files[0];
	 var maxSize = '5000';

	 if('${cisincentiveDeatilsForm.sectionletterDocbase64File}' != '' && SectionLetterDoc == ''){
			return true;
		}
		
	 if(SectionLetterDoc!=null && SectionLetterDoc!=''){
	 var ext = SectionLetterDoc.split('.').pop();
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
	
	 var LoanDocument = document.getElementById('certifyingLoan').value;
	 var fileSize = document.getElementById("certifyingLoan").files[0];
	 var maxSize = '5000';

	 if('${cisincentiveDeatilsForm.certifyingLoanDocbase64File}' != '' && LoanDocument == ''){
			return true;
		}
		
	 if(LoanDocument!=null && LoanDocument!=''){
	 var ext = LoanDocument.split('.').pop();
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

	 if('${cisincentiveDeatilsForm.auditedAccountsDocbase64File}' != '' && NoDefault == ''){
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
		document.getElementById('auditedAccountsMsg').innerHTML = "Certificate from FI/Bank for Reim document is Required.";
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

	 if('${cisincentiveDeatilsForm.fiBankCertificateDocbase64File}' != '' && BankFIDocument == ''){
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

function calculateROI(){
   var roidata=document.getElementById('roi').value;
   
   if(roidata>100)
       {

	   //alert("More than 100 is not allowed");
	   document.getElementById('roiMsg').innerHTML = "ROI More than 100 is not allowed";
		document.getElementById('roi').focus();
	
	   return false;
       }	
}

</script>

</head>
<body class="bottom-bg">
	<section class="inner-header">
		<div class="top-header">
			<div class="container">
				<div class="row">
					<div class="col-sm-6 text-left">
						<span class="top-gov-text">Goverment of Uttar Pradesh</span>
					</div>
					<div class="col-sm-6 text-right">
						<a href="tel:05222238902"><i class="fa fa-phone"></i>
							0522-2238902</a> | <a href="mailto:info@udyogbandhu.com"><i
							class="fa fa-envelope"></i> info@udyogbandhu.com</a>
					</div>
				</div>
			</div>
		</div>
		<!-- Navigation / Navbar / Menu -->
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container">
				<a class="navbar-brand" href="#"><img src="images/logo.png"
					class="logo" alt="Logo"></a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarTogglerDemo02"
					aria-controls="navbarTogglerDemo02" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>

				<div class="collapse navbar-collapse" id="navbarTogglerDemo02">
					<ul class="navbar-nav ml-auto mt-2 mt-lg-0">
						<li class="nav-item"><a class="nav-link active"
							href="applicant-details.html">Home</a></li>

						<li class="nav-item"><a class="nav-link" href="training.html"
							target="_blank">Training</a></li>

						<li class="nav-item"><a class="nav-link"
							href="http://invest.up.gov.in/" target="_blank">Invest UP</a></li>

						<li class="nav-item"><a class="nav-link"
							href="http://niveshmitra.up.nic.in/About.aspx?ID=whyup"
							target="_blank">Why Invest in UP</a></li>

						<li class="nav-item"><a class="nav-link"
							href="http://udyogbandhu.com/topics.aspx?mid=Policies"
							target="_blank">Policies</a></li>

						<li class="nav-item"><a class="nav-link"
							href="http://udyogbandhu.com/topics.aspx?mid=UdyogBandhu"
							target="_blank">Contact Us</a></li>

					</ul>
					<div class="gov-logo-group">
						<a href="#"><img src="images/up-logo.png" align="up-Logo"></a>

					</div>
				</div>
			</div>
		</nav>
		<!-- End Navigation / Navbar / Menu -->
	</section>


	<section class="common-form-area">
		<div class="container">
			<!-- Main Title -->
			<div class="inner-banner-text">
				<h1>Application Form</h1>
			</div>
			<div class="row">

				<form:form modelAttribute="cisincentiveDeatilsForm"
					name="cisincentiveDeatilsform" method="post" id="myForm"
					action="disCisSave" enctype="multipart/form-data">

					<div class="col-sm-12">
						<div class="form-card">
							<div class="without-wizard-steps">
								<h4 class="card-title mb-4 mt-4 text-center">Capital
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
														maxlength="250" class="form-control" onkeyup="emptyCapInvErrMsg();nameOfBank()"></form:input>
														<span id="BEName" class="text-danger"></span>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="form-group">
													<form:label path="bankadd">Address of Banks/Financial Institutions </form:label>

													<form:input id="bankadd" path="bankadd" name="bankadd"
														class="form-control" onkeyup="emptyCapInvErrMsg();addressOfBank()"></form:input>
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
														class="form-control empty-input is-numeric" value="${totalLoan}"
														readonly="true"></form:input>
														<small class="words text-info"></small>
												<span id="AmtAdmtTaxDeptGst" class="text-danger"></span>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="form-group">
													<form:label path="bankcert">Is FI/ Bank certified amount of loan on P&M available?</form:label>
													<form:select path="bankcert" class="form-control" id="selectedAvailPM" onchange="showAndHidePM()">
														<form:option value="">Select One</form:option>
														<form:option value="Yes">Yes</form:option>
														<form:option value="No">No</form:option>
													</form:select>
												</div>
											</div>
											
											
											
											<div class="col-sm-6 amtLoan">
												<div class="form-group">
													<form:label path="pnmloan">FI/ Bank certified amount of loan on P&M</form:label>
													<form:input id="pnmloan" path="pnmloan"
														placeholder="Enter Amount in INR" maxlength="12"
														class="form-control is-numeric"
														onkeypress="return validateNumberField()" onkeyup="emptyCapInvErrMsg()" onchange="return enabledisable()"></form:input>
														<small class="words text-info"></small>
                                                     <span id="pnmloanMsg" class="text-danger"></span>

												</div>
											</div>
											
											<div class="col-sm-6 amtInvPM">
												<div class="form-group">
													<form:label path="">Amount Investment in P&M</form:label>
													<form:input id="amtInvPlantMachin" path="amtInvPlantMachin"
														placeholder="Enter Amount in INR" maxlength="12" 
														class="form-control is-numeric" value = "${noPnM}" readonly="true"
														onkeypress="return validateNumberField()" onkeyup="emptyCapInvErrMsg()"></form:input>
														<small class="words text-info"></small>
                                                     <span id="amtInvPlantMachinMsg" class="text-danger"></span>

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
													<form:input id="totalint" path="totalint" maxlength="12" type="text"
														class="form-control is-numeric" value="${totalInt}"
														readonly="true"></form:input>
														<small class="words text-info"></small>
														 <span id="totalintMsg" class="text-danger"></span>


												</div>
											</div>
											<div class="col-sm-6">
												<div class="form-group">
													<form:label path="roi">Rate of Interest</form:label>
													<form:input path="roi" maxlength="5" type="text"
														class="form-control" id="roi" 
														onkeyup="emptyCapInvErrMsg();calculateROI()" onkeypress="return validateFloatKeyPress(this,event);"></form:input>
														 <span id="roiMsg" class="text-danger"></span>
														
												</div>
											</div>
											<div class="col-sm-6">
												<div class="form-group">
													<label>Date of Sanction</label>
													<form:input path="sanctiondate" maxlength="12" type="date"
														class="form-control" id="datepicker" onkeyup="emptyCapInvErrMsg()"></form:input>
                                                           <span id="datepickerMsg" class="text-danger"></span>
												</div>
											</div>
											<div class="col-sm-12">
												<div class="form-group">
													<form:label path="disbursedloan">Amount of Loan Disbursed towards Investment in Plant & Machinery.</form:label>
													<form:input path="disbursedloan" id="disbursedloan"
														maxlength="12" placeholder="Enter Amount in INR"
														class="form-control is-numeric"
														onkeypress="return validateNumberField()" onkeyup="emptyCapInvErrMsg()"></form:input>
														<small class="words text-info"></small>
                                                           <span id="disbursedloanMsg" class="text-danger"></span>
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-sm-12 mt-4">
												<h3 class="common-heading">Required Supporting
													Documents</h3>
											</div>
										</div>

										<div class="row">
											<div class="col-sm-12">
												<div class="form-group">
													<form:label path="certifyingLoan">Certificate from FI/Bank certifying loan for Plant & Machinery and interest & other relevant details. <span>*</span>
														<small>(Upload doc less than 5 MB in PDF)</small>
													</form:label>
													<div class="custom-file">
														<input type="file" class="custom-file-input user-file"
															id="certifyingLoan" name="certifyingLoan" onchange="return CertificateFrBank(event)">
														
														<form:label path="certifyingLoan" class="custom-file-label file" id="choosefileCertifyingLoan"
															for="certifyingLoan">Choose file</</form:label>
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
															class="custom-file-input user-file" 
															id="auditedAccounts" onchange="return CertificateFrNoDefault(event)">
														
														<form:label path="auditedAccounts" class="custom-file-label file" id="choosefileAuditedAccounts"
															for="auditedAccounts">Choose file</</form:label>>
													</div>
													<span id="auditedAccountsMsg" class="text-danger"></span>
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-sm-12 mt-4">
												<h3 class="common-heading">Particulars of Claims for
													Sanction of Capital Interest Subsidy</h3>
											</div>
										</div>

										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered">
														<thead>
															<tr>
																<th rowspan="2" width="22%">Year for which subsidy Applied</th>
																<th colspan="2">Duration/Period</th>
																
																<th colspan="3">Payment made to FI/Bank during the
																	year</th>
																<th rowspan="2" width="20%">Amount of Interest
																	Subsidy Applied</th>
															</tr>
															<tr>
															<th width="5%">From</th>
																<th>To</th>
																<th>Total Interest on Loan</th>
																<th>Principal</th>
																<th>Interest on P&M</th>
															</tr>
														</thead>
														<tbody>
														<tr>
															<td>Year I &nbsp;&nbsp;  <form:select path="yearI" type="text" class="form-control financial-year" id="financialYear1" name="financialYear1">
									     <form:option value="" selected="selected">-Select Financial Year-</form:option>
									<form:option value="${cisincentiveDeatilsForm.yearI}"/>
							</form:select><span id="year1Msg" class="text-danger"></span></td>
							
							<td><form:input path="durationPeriodDtFr1" type="date"
																class="form-control" id="durationPeriodIdFr1"
																name="durationPeriodDtFr1"></form:input> <span id="durationPeriodDtFrMsg1"
															class="text-danger"></span></td>
															
															<td><form:input path="durationPeriodDtTo1" type="date"
																class="form-control" id="durationPeriodIdTo1"
																name="durationPeriodDtTo1"></form:input> <span id="durationPeriodDtToMsg1"
															class="text-danger"></span></td>
							
							
															<td><form:input path="firstYTI" type="text"	id="ttlInterestLoan1" placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlIntrst"
																name="firstYTI" maxlength="12" onkeypress="return validateNumberField()" onkeyup="emptyCapInvErrMsg()"></form:input><small class="words text-info"></small>
																<span id="ttlInterestLoan1Msg" class="text-danger"></span></td>
															
															
															<td><form:input path="firstYP" type="text" id="claimPrinciple1" maxlength="12"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlPrincipal"
																name="firstYP" onkeypress="return validateNumberField()" onkeyup="emptyCapInvErrMsg()"></form:input><small class="words text-info"></small>
																<span id="claimPrinciple1Msg" class="text-danger"></span></td>
															<td><form:input path="firstYI" type="text" id="claimInterest1" maxlength="12" readonly="true"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlInterest"
																name="firstYI" onkeypress="return validateNumberField()" onkeyup="emptyCapInvErrMsg()"></form:input><small class="words text-info"></small>
																<span id="claimInterest1Msg" class="text-danger"></span></td>
															<td><form:input path="firstYAmtIntSubsidy" type="text" id="claimSubsidy1" maxlength="12"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlAmtSubsidy"
																name="firstYAmtIntSubsidy" onkeypress="return validateNumberField()" onkeyup="emptyCapInvErrMsg()"></form:input><small class="words text-info"></small>
																<span id="claimSubsidy1Msg" class="text-danger"></span></td>
														</tr>
														<tr>
															<td>Year II <form:select path="yearII" type="text" placeholder="Enter Year" class="form-control  financial-year" id="financialYear2" name="financialYear2">
									                            <form:option value="" selected="selected">-Select Financial Year-</form:option>
									                            <form:option value="${cisincentiveDeatilsForm.yearII}"></form:option>
							                             </form:select><span id="year2Msg" class="text-danger"></span></td>
							                             
							                             <td><form:input path="durationPeriodDtFr2" type="date"
																class="form-control" id="durationPeriodIdFr2"
																name="durationPeriodDtFr2"></form:input> <span id="durationPeriodDtFrMsg2"
															class="text-danger"></span></td>
															
															<td><form:input path="durationPeriodDtTo2" type="date"
																class="form-control" id="durationPeriodIdTo2"
																name="durationPeriodDtTo2"></form:input> <span id="durationPeriodDtToMsg2"
															class="text-danger"></span></td>
							                             
							                             
															<td><form:input path="secondYTI" type="text" id="ttlInterestLoan2" maxlength="12"	placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlIntrst"
																name="secondYTI" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="secondYP" type="text" maxlength="12"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlPrincipal"
																name="secondYP" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="secondYI" type="text"  id="claimInterest2" maxlength="12" readonly="true"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlInterest"
																name="secondYI" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="secondYAmtIntSubsidy" type="text" maxlength="12"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlAmtSubsidy"
																name="secondYAmtIntSubsidy" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>

														</tr>
														<tr>
															<td>Year III <form:select path="yearIII" type="text" placeholder="Enter Year" class="form-control financial-year" id="financialYear3" name="financialYear3">
									                              <form:option value="" selected="selected">-Select Financial Year-</form:option>
									                             <form:option value="${cisincentiveDeatilsForm.yearIII}"></form:option>
							                             </form:select><span id="year3Msg" class="text-danger"></span></td>
							                             
							                             <td><form:input path="durationPeriodDtFr3" type="date"
																class="form-control" id="durationPeriodIdFr3"
																name="durationPeriodDtFr3"></form:input> <span id="durationPeriodDtFrMsg3"
															class="text-danger"></span></td>
															
															<td><form:input path="durationPeriodDtTo3" type="date"
																class="form-control" id="durationPeriodIdTo3"
																name="durationPeriodDtTo3"></form:input> <span id="durationPeriodDtToMsg3"
															class="text-danger"></span></td>
															
															
															<td><form:input path="thirdYTI" type="text" maxlength="12"	id="ttlInterestLoan3" placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlIntrst"
																name="thirdYTI" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="thirdYP" type="text" maxlength="12"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlPrincipal"
																name="thirdYP" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="thirdYI" type="text" maxlength="12" id="claimInterest3"  readonly="true" 
															    placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlInterest"
																name="thirdYI" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="thirdYAmtIntSubsidy" type="text" maxlength="12"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlAmtSubsidy"
																name="thirdYAmtIntSubsidy" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
														</tr>
														<tr>
															<td>Year IV <form:select path="yearIV" type="text" placeholder="Enter Year" class="form-control financial-year" id="financialYear4" name="financialYear4">
									                              <form:option value="" selected="selected">-Select Financial Year-</form:option>
									                             <form:option value="${cisincentiveDeatilsForm.yearIV}"></form:option>
							                             </form:select><span id="year4Msg" class="text-danger"></span></td>
							                             
							                             <td><form:input path="durationPeriodDtFr4" type="date"
																class="form-control" id="durationPeriodIdFr4"
																name="durationPeriodDtFr4"></form:input> <span id="durationPeriodDtFrMsg4"
															class="text-danger"></span></td>
															
															<td><form:input path="durationPeriodDtTo4" type="date"
																class="form-control" id="durationPeriodIdTo4"
																name="durationPeriodDtTo4"></form:input> <span id="durationPeriodDtToMsg4"
															class="text-danger"></span></td>
															
															
															<td><form:input path="fourthYTI" type="text" maxlength="12"	id="ttlInterestLoan4" placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlIntrst"
																name="fourthYTI" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="fourthYP" type="text" maxlength="12"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlPrincipal"
																name="fourthYP" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="fourthYI" type="text" maxlength="12" id="claimInterest4" readonly="true"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlInterest"
																name="fourthYI" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="fourthYAmtIntSubsidy" type="text" maxlength="12"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlAmtSubsidy"
																name="fourthYAmtIntSubsidy" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>

														</tr>
														<tr>
															<td>Year V <form:select path="yearV" type="text" placeholder="Enter Year" class="form-control financial-year" id="financialYear5" name="financialYear5">
									                               <form:option value="" selected="selected">-Select Financial Year-</form:option>
									                             <form:option value="${cisincentiveDeatilsForm.yearV}"></form:option>
							                             </form:select><span id="year5Msg" class="text-danger"></span></td>
							                             
							                             <td><form:input path="durationPeriodDtFr5" type="date"
																class="form-control" id="durationPeriodIdFr5"
																name="durationPeriodDtFr5"></form:input> <span id="durationPeriodDtFrMsg5"
															class="text-danger"></span></td>
															
															<td><form:input path="durationPeriodDtTo5" type="date"
																class="form-control" id="durationPeriodIdTo5"
																name="durationPeriodDtTo5"></form:input> <span id="durationPeriodDtToMsg5"
															class="text-danger"></span></td>
															
															
															<td><form:input path="fifthYTI" type="text" maxlength="12"	id="ttlInterestLoan5" placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlIntrst"
																name="fifthYTI" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="fifthYP" type="text" maxlength="12"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlPrincipal"
																name="fifthYP" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="fifthYI" type="text" maxlength="12"  id="claimInterest5" readonly="true"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlInterest"
																name="fifthYI" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>
															<td><form:input path="fifthYAmtIntSubsidy" type="text" maxlength="12"
																placeholder="Enter Amount in INR" class="form-control is-numeric AllYearTotlAmtSubsidy"
																name="fifthYAmtIntSubsidy" onkeypress="return validateNumberField()"></form:input><small class="words text-info"></small></td>

														</tr>
														<tr>
															<td><strong>Total</strong></td>
															<td></td>
															<td></td>
															<td><input type="text"	class="form-control totalofTI" readonly="true" name="totalofTI" maxlength="12" ></td>
															<td><input type="text"	class="form-control totalofPrincipal" readonly="true"	name="totalofPrincipal" maxlength="12" ></td>
															<td><input type="text"	class="form-control totalofInterest" readonly="true"	name="totalofIntrest" maxlength="12" ></td>
															<td><input type="text"	class="form-control totalofAmtSubsidy" readonly="true"	name="totalofAmtSubsidy" maxlength="12"></td>
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
															maxlength="10" id="fiBankCertificate" name="fiBankCertificate" onchange="return CertificateBankFI(event)">
														
														 <form:label path="fiBankCertificate"	class="custom-file-label file" id="choosefilefiBankCertificate" for="fiBankCertificate">Choose
															file</form:label>
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
														<input type="checkbox" id="verified"
															class="custom-control-input"
															name="Capital-Interest-Subsidy" value="Yes"> <label
															class="custom-control-label" for="verified" >
																											The
															above information are completely true and no fact has
															been concealed or misrepresented. It is further certified
															that the company has not applied for benefits of the
															above nature under any sector-specific or other policy of
															the Government of Uttar Pradesh for purpose of availing
															benefits of the above nature. I/we hereby agree that I/we
															shall forthwith repay the benefits released to me/us
															under Rules of Policy for Promotion of Industrial
															Investment and Employment-2017, if the said benefits are
															found to be disbursed in excess of the amount actually
															admissible whatsoever the reason. </label>
															
																										
													</div>
												</div>
											</div>
										</div>

										<hr>

										<div class="row">
									<div class="col-sm-5">
										<a href="./disincentivetype"
											class="common-default-btn mt-3 prev-step" onclick="return confirm('Are you sure you want to go to SELECT INCENTIVE TYPE?')"  >Back</a>
									</div>
										
											<div class="col-sm-7 text-right">
											<button type="submit" class="common-btn"
											onclick="return disCISValidation()">Save</button>
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
							<li><a href="http://invest.up.gov.in/" target="_blank">Home</a></li>
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
	<script src="js/datepicker.js"></script>
	<script src="js/custom.js"></script>


</body>
</html>