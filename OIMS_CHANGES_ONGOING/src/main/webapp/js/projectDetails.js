function ValidateSize(file) {
	var regiOrLicenseFile=document.getElementById("regiOrLicenseFile").value;
	if(regiOrLicenseFile!=null && regiOrLicenseFile!='')
	{
	var ext = regiOrLicenseFile.split('.').pop();
	if (ext == "pdf" || ext == "PDF") {
		document.getElementById('regiOrLicenseFile').innerHTML="";
	} 
	else {
		document.getElementById('RegiOrLicenseFile').innerHTML = "Please upload file in PDF Format.";
		document.getElementById('regiOrLicenseFile').focus();	
		return false;
	}
    var FileSize = document.getElementById("regiOrLicenseFile").files[0].size / 1024/1024; // in MB
    FileSize = FileSize.toFixed(1);
    if (FileSize > 2) {
    	document.getElementById('RegiOrLicenseFile').innerHTML = "Your file size is: "+FileSize+" MB, File size should not be more than 2 MB";
    	document.getElementById('regiOrLicenseFile').focus();
		return false;
    }
    else{
    document.getElementById('RegiOrLicenseFile').innerHTML="";
    }
  }
}
function ValidateSize1(file) {
	var encloseDetailProReportFileName=document.getElementById("encloseDetailProReportFileName").value;
	if(encloseDetailProReportFileName!=null && encloseDetailProReportFileName!='')
	{
	var ext = encloseDetailProReportFileName.split('.').pop();
	if (ext == "pdf" || ext == "PDF") {
		document.getElementById('EncloseDetailProReportFileName').innerHTML="";
	} 
	else {
		document.getElementById('EncloseDetailProReportFileName').innerHTML = "Please upload file in PDF Format.";
		document.getElementById('encloseDetailProReportFileName').focus();	
		return false;
	}
    var FileSize = file.files[0].size / 1024/1024; // in MB
    FileSize = FileSize.toFixed(1);
    if (FileSize > 5) {
    	document.getElementById('EncloseDetailProReportFileName').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
    	document.getElementById('encloseDetailProReportFileName').focus();
		return false;
    }
    else{
    document.getElementById('EncloseDetailProReportFileName').innerHTML="";
    }
    
  }
}
function ValidateSize2(file) {
	var caCertificate=document.getElementById("caCertificate").value;		
	if(caCertificate!=null && caCertificate!='')
		{
		var ext = caCertificate.split('.').pop();
		if (ext == "pdf" || ext == "PDF") {
			document.getElementById('CaCertificateFileName').innerHTML="";
		} 
		else {
			document.getElementById('CaCertificateFileName').innerHTML = "Please upload file in PDF Format.";
			document.getElementById('caCertificate').focus();	
			return false;
		}
      
	
	var FileSize = file.files[0].size / 1024/1024; // in MB
    FileSize = FileSize.toFixed(1);   
    if (FileSize > 2) {
    	document.getElementById('CaCertificateFileName').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 2 MB";
    	document.getElementById('caCertificate').focus();
		return false;
    }
    else
       {
	    document.getElementById('CaCertificateFileName').innerHTML="";
       }	
    }	
 }

function ValidateSize3(file) {
	
	var certifiedList=document.getElementById("certifiedList").value;		
	if(certifiedList!=null && certifiedList!='')
		{
		var ext = certifiedList.split('.').pop();
		if (ext == "pdf" || ext == "PDF") {
			document.getElementById('CharateredEngineerFileName').innerHTML="";
		} 
		else {
			document.getElementById('CharateredEngineerFileName').innerHTML = "Please upload file in PDF Format.";
			document.getElementById('certifiedList').focus();	
			return false;
		}
      
	
	  var FileSize = file.files[0].size / 1024/1024; // in MB
      FileSize = FileSize.toFixed(1); 
    if (FileSize > 2) {
    	document.getElementById('CharateredEngineerFileName').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 2 MB";
    	document.getElementById('certifiedList').focus();
		return false;
    }
    else
    {
        document.getElementById('CharateredEngineerFileName').innerHTML="";
       }
	}
    
}

/* function IsCharacter1(inputtext)
{
	 var projectUnitName=document.getElementById("projectUnitName").value;
    if(projectUnitName!=null || projectUnitName==''){
    var regex = new RegExp("^[A-Za-z ]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('ProjectUnitName').innerHTML = "Only character are allowed.";
	   document.getElementById('projectUnitName').focus();			
	   return false;
    }
    else
    	{
    	 document.getElementById('ProjectUnitName').innerHTML="";
    	}
    }    
	    
} */

function IsCharacter11(inputtext)
{
	 var projectDescription=document.getElementById("projectDescription").value;
    if(projectDescription!=null || projectDescription==''){
    var regex = new RegExp("^[A-Za-z ]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('ProjectDescription').innerHTML = "Only character are allowed.";
	   document.getElementById('projectDescription').focus();			
	   return false;
    }
    else
    	{
    	document.getElementById('ProjectDescription').innerHTML="";
    	}
   }    
	    
}

function IsCharacter111(inputtext)
{
	 var contactPersonName=document.getElementById("contactPersonName").value;
    if(contactPersonName!=null || contactPersonName==''){
    var regex = new RegExp("^[A-Za-z ]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('ContactPersonName').innerHTML = "Only character are allowed.";
	   document.getElementById('contactPersonName').focus();			
	   return false;
    }
    else
    	{
    	document.getElementById('ContactPersonName').innerHTML="";
    	}
   }    
	    
}


function IsCharacter1111(inputtext)
{
	var designation=document.getElementById("designation").value;
    if(designation!=null || designation==''){
    var regex = new RegExp("^[A-Za-z ]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('Designation').innerHTML = "Only character are allowed.";
	   document.getElementById('designation').focus();			
	   return false;
    }
    else
    	{
    	document.getElementById('Designation').innerHTML="";
    	}
   }    
	    
}




function IsCharacter11111(inputtext)
{
	var mobileNo=document.getElementById("mobileNo").value;
	
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
function IsMobileNo(inputtext)
{
   var mobileNo=document.getElementById("mobileNo").value;
   if(mobileNo!=null || mobileNo!=''){
	 if(mobileNo.match(/^0+/))
		{
			 document.getElementById('MobileNo').innerHTML = "Mobile Number should not Start with 0.";
			 document.getElementById('mobileNo').value="";
	         document.getElementById('mobileNo').focus();	
		}
		else{
			document.getElementById('MobileNo').innerHTML="";
		}
   }
}

function IsCharacter111111(inputtext)
{	
	var pinCode=document.getElementById("pinCode").value;
    if(pinCode!=null || pinCode==''){
    var regex = new RegExp("^[0-9]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('PinCode').innerHTML = "Only numeric value is allowed.";
	   document.getElementById('pinCode').focus();			
	   return false;
    } 
    else
    	{
    	document.getElementById('PinCode').innerHTML="";
    	}
  }    
	    
}


function IsNumericValidation(inputtext)
{	
	var existingProducts=document.getElementById("existingProducts").value;
    if(existingProducts!=null || existingProducts==''){
    var regex = new RegExp("^[0-9a-zA-Z,' ']+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('ExistingProducts').innerHTML = "Only Alphanumeric characters are allowed.";
	   document.getElementById('existingProducts').focus();			
	   return false;
    } 
    else
    	{
    	document.getElementById('ExistingProducts').innerHTML="";
    	}
  }    
	    
}


function validateNumericField() {
	return (event.charCode > 47 && event.charCode < 58)

}




function IsNumericValidation1(inputtext)
{	
	var existingInstalledCapacity=document.getElementById("existingInstalledCapacity").value;
    if(existingInstalledCapacity!=null || existingInstalledCapacity==''){
    var regex = new RegExp("^[0-9a-zA-Z,' ']+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('ExistingInstalledCapacity').innerHTML = "Only Alphanumeric characters are allowed.";
	   document.getElementById('existingInstalledCapacity').focus();			
	   return false;
    } 
    else
    	{
    	document.getElementById('ExistingInstalledCapacity').innerHTML="";
    	}
  }    
	    
}



function IsNumericValidation2(inputtext)
{	
	var proposedProducts=document.getElementById("proposedProducts").value;
    if(proposedProducts!=null || proposedProducts==''){
    var regex = new RegExp("^[0-9a-zA-Z,' ']+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('ProposedProducts').innerHTML = "Only Alphanumeric characters are allowed.";
	   document.getElementById('proposedProducts').focus();			
	   return false;
    } 
    else
    	{
    	document.getElementById('ProposedProducts').innerHTML="";
    	}
  }    
	    
}
function IsNumericValidation3(inputtext)
{	
	var proposedInstalledCapacity=document.getElementById("proposedInstalledCapacity").value;
    if(proposedInstalledCapacity!=null || proposedInstalledCapacity==''){
    var regex = new RegExp("^[0-9a-zA-Z,' ']+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('ProposedInstalledCapacity').innerHTML = "Only Alphanumeric characters are allowed.";
	   document.getElementById('proposedInstalledCapacity').focus();			
	   return false;
    } 
    else
    	{
    	document.getElementById('ProposedInstalledCapacity').innerHTML="";
    	}
  }    
	    
}

function IsNumericValidation4(inputtext)
{	
	var existingGrossBlock=document.getElementById("existingGrossBlock").value;
    if(existingGrossBlock!=null || existingGrossBlock==''){
    var regex = new RegExp("^[0-9]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('ExistingGrossBlock').innerHTML = "Only numeric value is allowed.";
	   document.getElementById('existingGrossBlock').focus();			
	   return false;
    } 
    else
    	{
    	document.getElementById('ExistingGrossBlock').innerHTML="";
    	}
  }    
	    
}
function IsNumericValidation5(inputtext)
{	
	var proposedGrossBlock=document.getElementById("proposedGrossBlock").value;
    if(proposedGrossBlock!=null || proposedGrossBlock==''){
    var regex = new RegExp("^[0-9]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       document.getElementById('ProposedGrossBlock').innerHTML = "Only numeric value is allowed.";
	   document.getElementById('proposedGrossBlock').focus();			
	   return false;
    } 
    else
    	{
    	document.getElementById('ProposedGrossBlock').innerHTML="";
    	}
  }    
	    
}
//Add remove checkbox div
$(document).ready(function(){
	$("#fileTypeLabel2").click(function(){
	$("#fileType2").removeClass("hide-file-type");
	$("#fileType1").addClass("hide-file-type");
	});
	
	$("#fileTypeLabel1").click(function(){
	$("#fileType1").removeClass("hide-file-type");
	$("#fileType2").addClass("hide-file-type");
	});
});


