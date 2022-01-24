

function IsMobileNoCharacter(inputtext)
{
	var mobileNo=document.getElementById("mobileNumberId").value;
	
	 

	if(mobileNo!=null || mobileNo!=''){
		var regex = new RegExp("^[0-9]+$");
		var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		if (!regex.test(key)) {
			event.preventDefault();
			document.getElementById('Mobile').innerHTML = "Only numeric are allowed.";
			document.businessEntityAddressForm.mobileNumber.focus();		
			return false;
		}
		else
		{
			document.getElementById('Mobile').innerHTML="";
		}


	}    

}

function IsMobileNoNeumeric(inputtext)
{
	var mobileNo=document.getElementById("mobileNumberId").value;
	if (mobileNo == null || mobileNo == '') {
			document.getElementById('Mobile').innerHTML = "Mobile Number is Required.";
			document.businessEntityAddressForm.mobileNumber.focus();
			return false;
		}else {
	        document.getElementById('Mobile').innerHTML = "";
	    }
	if(mobileNo!=null || mobileNo!=''){
		if(mobileNo.match(/^0+/))
		{
			document.getElementById('Mobile').innerHTML = "Mobile Number should not Start with 0.";
			document.getElementById('mobileNumber').value="";
			document.businessEntityAddressForm.mobileNumber.focus();	
		}
		else{
			document.getElementById('Mobile').innerHTML="";
		}
	}
}

function IsPinCodeCharacter(inputtext)
{	
	var pinCode=document.getElementById("businessPinCode").value;
	if(pinCode!=null || pinCode==''){
		var regex = new RegExp("^[0-9]+$");
		var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		if (!regex.test(key)) {
			event.preventDefault();
			document.getElementById('PinCode').innerHTML = "Only numeric are allowed.";
			document.getElementById('businessPinCode').focus();			
			return false;
		} 
		else
		{
			document.getElementById('PinCode').innerHTML="";
		}
	}    

}

function IsPinCodeNeumeric(inputtext)
{	var businessPinCode = document.getElementById("businessPinCode").value;
		if (businessPinCode == null || businessPinCode == '') {
			document.getElementById('PinCode').innerHTML = "Please Enter Pin Code";
			document.getElementById('businessPinCode').focus();
			return false;
		}else {
	        document.getElementById('PinCode').innerHTML = "";
	    } 
	    
		if(businessPinCode!=null || businessPinCode!=''){
		if(businessPinCode.match(/^0+/))
		{
			document.getElementById('PinCode').innerHTML = "Mobile Number should not Start with 0.";
			document.getElementById('businessPinCode').focus();
		}
		else{
			document.getElementById('PinCode').innerHTML="";
		}
	}
}

function IsEmailId(inputtext)
{
	var emailId = document.businessEntityAddressForm.emailId.value;
	if (emailId == null || emailId == '') {
		document.getElementById('Email').innerHTML = "Email Id is Required";
		document.businessEntityAddressForm.emailId.focus();
		return false;
	} else {
		document.getElementById('Email').innerHTML = "";
	} 

}

function IsAddress1(inputtext)
{
	var businessAddress = document.businessEntityAddressForm.businessAddress.value;

		//vinay 
		var res = businessAddress.substring(0, 1);
	
   
	if (businessAddress == null || businessAddress == '') {
		document.getElementById('BAddress').innerHTML = "Registered Office Address is Required.";
		document.businessEntityAddressForm.businessAddress.focus();
		return false;
	} else if (res.match(/[^a-zA-Z0-9]+$/)) {
	
		document.getElementById('BAddress').innerHTML = "Registered Office Address not allow only special character.";
		document.businessEntityAddressForm.businessAddress.focus();
		return false;
		
	}
	else if (businessAddress.match(/^[0-9]+$/)) {
		document.getElementById('BAddress').innerHTML = "Registered Office Address not allow only number.";
		document.businessEntityAddressForm.businessAddress.focus();
		return false;
	}else {
		document.getElementById('BAddress').innerHTML = "";
	}
  		
}


function IsCountry(inputtext)
{
	var businessCountryName = document.getElementById("businessCountryName").value;
	if (businessCountryName == null || businessCountryName == '') {
		document.getElementById('CountryName').innerHTML = "Please Select Country";
		document.businessEntityAddressForm.businessCountryName.focus();
		return false;
	}else {
		document.getElementById('CountryName').innerHTML = "";
	} 
}

function IsState(inputtext)
{
	var businessStateName = document.getElementById("businessStateName").value;
	if (businessStateName == null || businessStateName == '') {
		document.getElementById('StateName').innerHTML = "Please Select State/UT";
		document.businessEntityAddressForm.businessStateName.focus();
		return false;
	}else {
		document.getElementById('StateName').innerHTML = "";
	} 

}

function IsDistrict(inputtext)
{
	var businessDistrictName = document.getElementById("businessDistrictName").value;
	if (businessDistrictName == null || businessDistrictName == '') {
		document.getElementById('DistrictName').innerHTML = "Please Select District";
		document.businessEntityAddressForm.businessDistrictName.focus();
		return false;
	}else {
		document.getElementById('DistrictName').innerHTML = "";
	} 

}

function IsyearEstablishment(inputtext)
{
	var yearEstablishment = document.getElementById("yearEstablishmentId").value;
	if (yearEstablishment == null || yearEstablishment == '') {
		document.getElementById('YearEstablishment').innerHTML = "Year of Unit Establishment is Required";
		document.businessEntityAddressForm.yearEstablishment.focus();
		return false;
	}else {
		document.getElementById('YearEstablishment').innerHTML = "";
	} 

}

function IsGSTIN(inputtext)
{
	var gstin = document.getElementById("gstin").value;
	if (gstin == null || gstin == '') {
		document.getElementById('Gstin').innerHTML = "Please enter GSTIN number.";
		document.getElementById('Gstin').style.cssText = "color: red !important";
		document.businessEntityAddressForm.gstin.focus();
		return false;
	}

	if (/^\d{2}[A-Z]{5}\d{4}[A-Z]{1}[A-Z\d]{1}[Z]{1}[A-Z\d]{1}$/.test(gstin)) {
		document.getElementById('Gstin').innerHTML = "Valid GSTIN number.";
		document.getElementById('Gstin').style.cssText = "color: green !important";
		return true;
	} else {
		document.getElementById('Gstin').innerHTML = "Please enter a valid GSTIN number.";
		document.getElementById('Gstin').style.cssText = "color: red !important";
		document.businessEntityAddressForm.gstin.focus();
		return false
	}

}

function IsCIN(inputtext)
{
	var cin = document.getElementById("cin").value;
	if (cin == null || cin == '') {
		document.getElementById('Cin').innerHTML = "Please enter a valid CIN number.";
		document.getElementById('Cin').style.cssText = "color: red !important";
		document.businessEntityAddressForm.cin.focus();
		return false;
	}

	if (/^([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$/.test(cin)) {
		document.getElementById('Cin').innerHTML = "Valid CIN number.";
		document.getElementById('Cin').style.cssText = "color: green !important";
		return true;
	} else {
		document.getElementById('Cin').innerHTML = "Please enter a valid CIN number.";
		document.getElementById('Cin').style.cssText = "color: red !important";
		document.businessEntityAddressForm.cin.focus();
		return false
	}
}

function IsPAN(inputtext)
{
    var companyPanNo = document.getElementById("companyPanNoId").value;
	if (companyPanNo == null || companyPanNo == '') {
		document.getElementById('CPanNo').innerHTML = "Please enter valid PAN number.";
		document.getElementById('CPanNo').style.cssText = "color: red !important";
		document.getElementById("companyPanNoId").focus();
		return false;
	}
	if (/^[A-Z]{3}[PCHFATBLJG]{1}[A-Z]{1}[0-9]{4}[A-Z]{1}$/.test(companyPanNo)) {
		document.getElementById('CPanNo').innerHTML = "Valid PAN number.";
		document.getElementById('CPanNo').style.cssText = "color: green !important";
		return true;
	} else {
		document.getElementById('CPanNo').innerHTML = "Please enter Valid PAN Number.";
		document.getElementById('CPanNo').style.cssText = "color: red !important";
		document.getElementById("companyPanNoId").focus();
		return false
	}

}


function IsPAND(inputtext)
{
    var companyPanNo = document.getElementById("panCardNOId").value;
	if (companyPanNo == null || companyPanNo == '') {
		document.getElementById('panCardNO').innerHTML = "Please enter valid PAN number.";
		document.getElementById("panCardNO").style.cssText = "color: red !important";
		document.getElementById("panCardNOId").focus();
		return false;
	}
	if (/^[A-Z]{3}[PCHFATBLJG]{1}[A-Z]{1}[0-9]{4}[A-Z]{1}$/.test(companyPanNo)) {
		document.getElementById('panCardNO').innerHTML = "Valid PAN number.";
		document.getElementById("panCardNO").style.cssText = "color: green !important";
		return true;
	} else {
		document.getElementById('panCardNO').innerHTML = "Please enter Valid PAN Number.";
		document.getElementById("panCardNO").style.cssText = "color: red !important";
		document.getElementById("panCardNOId").focus();
		return false
	}

}

function IsdinId()
{
    var din = document.getElementById("din").value;
	if (din == null || din == '') {
		document.getElementById('Din').innerHTML = "Please enter valid DIN number.";
		document.getElementById("Din").style.cssText = "color: red !important";
		document.getElementById("din").focus();
		return false;
	}
	if (/^(?!0{8})[0-9]{8}$/.test(din)) {
		document.getElementById('Din').innerHTML = "Valid DIN number.";
		document.getElementById("Din").style.cssText = "color: green !important";
		return true;
	} else {
		document.getElementById('Din').innerHTML = "Please enter Valid DIN Number.";
		document.getElementById("Din").style.cssText = "color: red !important";
		document.getElementById("din").focus();
		return false
	}

}



function IsMobileNoCharacter1(inputtext)
{
	var mobileNo=document.getElementById("mobileNumberId").value;

	if(mobileNo!=null || mobileNo!=''){
		var regex = new RegExp("^[0-9]+$");
		var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		if (!regex.test(key)) {
			event.preventDefault();
			document.getElementById('MobileNo').innerHTML = "Only numeric are allowed.";
			document.getElementById('mobileNo').focus();	
			return false;
		}
		else
		{
			document.getElementById('MobileNo').innerHTML="";
		}
	}	

}    


function IsMobileNoNeumeric1(inputtext)
{
	var mobileNo=document.getElementById("mobileNumberId").value;
	if(mobileNo!=null || mobileNo!=''){
		if(mobileNo.match(/^0+/))
		{
			document.getElementById('MobileNo').innerHTML = "Mobile Number should not Start with 0.";
			document.getElementById('mobileNumber').value="";
			document.getElementById('mobileNo').focus();
		}
		else{
			document.getElementById('MobileNo').innerHTML="";
		}
	}
}


function IsMAODoc(inputtext)
{
	var moaDoc = document.getElementById("moaDoc").value;
	if (moaDoc == null || moaDoc == '') {
	
		document.getElementById('MaoDoc').innerHTML = "MoA/Partnership Deed Attachment is Required";
		document.businessEntityAddressForm.moaDoc.focus();
		return false;
	}else {
		document.getElementById('MaoDoc').innerHTML = "";
	} 

	var ext = moaDoc.split('.').pop();
	if (ext == "pdf") {

	} else {
		document.getElementById('MaoDoc').innerHTML = "Please Upload MoA/Partnership Deed Attachment in PDF Format";
		document.businessEntityAddressForm.moaDoc.focus();
		return false;
	}
	
	if(fileSize.size >1048576){
	var FileSize = file.files[0].size / 1024/1024; // in MB
	FileSize = FileSize.toFixed(0.500);
	if (FileSize >0.500) {
		document.getElementById('MaoDoc').innerHTML = "to big, file size exceeds maximum is 500 KB";
		document.businessEntityForm.moaDoc.focus();
		return false;
	} else {
		document.getElementById('MaoDoc').innerHTML = "";
	}
}	else
   		{
   		var fsize = (fileSize.size / 1024).toFixed(1);    
   	    if (fsize > maxSize) {
   	    	document.getElementById('MaoDoc').innerHTML = "Your file size is: "+fsize+" kb," +" File size should not be more than 500 kb";
   	    	document.businessEntityForm.moaDoc.focus();
   			return false;
   		}
   	    else{
   	    	document.getElementById('MaoDoc').innerHTML="";
   	    }
      }

}



function IsregistrationAttachedDoc(inputtext)
{
	var registrationAttachedDoc = document.getElementById("registrationAttachedDoc").value;

	if (registrationAttachedDoc == null || registrationAttachedDoc == '') {
		document.getElementById('CIRA').innerHTML = "Certificate Incorpoation Registration Attachment is Required";
		document.businessEntityAddressForm.registrationAttachedDoc.focus();
		return false;
	}else {
		document.getElementById('CIRA').innerHTML = "";
	} 

	var ext = registrationAttachedDoc.split('.').pop();
	if (ext == "pdf") {
		document.getElementById('CIRA').innerHTML = "";
	} else {
		document.getElementById('CIRA').innerHTML = "Please Upload Certificate Incorpoation Registration Attachment in PDF Format";
		document.businessEntityAddressForm.registrationAttachedDoc.focus();
		return false;
	}

	var FileSize = file.files[0].size / 1024/1024; // in MB
	FileSize = FileSize.toFixed(0.500);
	if (FileSize > 0.500) {
		document.getElementById('CIRA').innerHTML = "to big, file size exceeds maximum is 500 KB";
		document.businessEntityForm.registrationAttachedDoc.focus();
		return false;
	} else {
		document.getElementById('CIRA').innerHTML = "";
	}

}




function IsbodDoc(inputtext)
{

	var bodDoc = document.getElementById("bodDoc").value;
	if (bodDoc == null || bodDoc == '') {
		document.getElementById('BodDoc').innerHTML = "Certified copy of the resolution of the Board of Directors of the Company is Required";
		document.businessEntityAddressForm.bodDoc.focus();
		return false;
	}else {
		document.getElementById('BodDoc').innerHTML = "";
	} 
}

function ValidatebodDocSize(file) {
	var bodDoc = document.getElementById("bodDoc").value;
	var ext = bodDoc.split('.').pop();
	if (ext == "pdf") {
		document.getElementById('BodDoc').innerHTML = "";
	} else {
		document.getElementById('BodDoc').innerHTML = "Please Upload Certified copy of the resolution of the Board of Directors of the Company in PDF Format";
		document.businessEntityAddressForm.bodDoc.focus();
		return false;
	}
}


function IsindusAffidaviteDoc(inputtext)
{
	var indusAffidaviteDoc = document.getElementById("indusAffidaviteDoc").value;
	if (indusAffidaviteDoc == null || indusAffidaviteDoc == '') {
		document.getElementById('IndusAffidaviteDoc').innerHTML = "Competent Authority of the Industrial Undertaking authorizing the deponent be provided along with the affidavit is Required";
		document.businessEntityAddressForm.indusAffidaviteDoc.focus();
		return false;
	}else {
		document.getElementById('IndusAffidaviteDoc').innerHTML = "";
	} 
}

function ValidateIsindusAffidaviteSize(file) {
	var ext = indusAffidaviteDoc.split('.').pop();
	if (ext == "pdf") {
		document.getElementById('IndusAffidaviteDoc').innerHTML = "";
	} else {
		document.getElementById('IndusAffidaviteDoc').innerHTML = "Please Upload Competent Authority of the Industrial Undertaking authorizing the deponent be provided along with the affidavit in PDF Format";
		document.businessEntityAddressForm.indusAffidaviteDoc.focus();
		return false;
	}
}

function IsannexureiaformatDoc(inputtext)
{
	var annexureiaformatId=document.getElementById("annexureiaformatId").value;
	// regiOrLicenseFile = 'data:application/pdf;base64,${projectDetailsForm.pdfFile}';
	if (annexureiaformatId == null || annexureiaformatId == '') {
		document.getElementById('AnnexureiaformatId').innerHTML = "Annexure I-A Format is Required.";
		document.getElementById('annexureiaformatId').focus();
		return false;
	}else {
		document.getElementById('AnnexureiaformatId').innerHTML = "";
	} 
}

function ValidateIsannexureiaformatSize(file) {
	var ext = annexureiaformatId.split('.').pop();
	if (ext == "pdf") {
		document.getElementById('AnnexureiaformatId').innerHTML = "";
	} else {
		document.getElementById('AnnexureiaformatId').innerHTML = "Please upload file in PDF Format.";
		document.getElementById('annexureiaformatId').focus();	
		return false;
	}

}



//JS code for stop jump to top on click.
$(document).on("keyup", ".other-total-input", function() {
	var sum = 0;
	$(".other-total-input").each(function(){
		sum += +$(this).val();
	});
	$("#invFinTotal").val(sum);
});



