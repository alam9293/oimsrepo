<%@page import="com.webapp.ims.model.ProprietorDetails"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Business Entity Details</title>
<style type="text/css">
.disable_a_href{
    pointer-events: none;
}
</style>
<jsp:include page="head.jsp" />
<script src="js/businessDetails.js" type="text/javascript"></script>
<script src="js/proprietorDetails.js" type="text/javascript"></script>

<script>
	$(document).ready(function(){
		function IsCountry(){
			var businessCountryName = document.getElementById("businessCountryName").value;
	        if (businessCountryName == null || businessCountryName == '') {
				document.getElementById('CountryName').innerHTML = "Please Select Country";
				document.businessEntityAddressForm.businessCountryName.focus();
				return false;
				}else {
			        document.getElementById('CountryName').innerHTML = "";
				} 
				}
		});

	$(document).ready(function(){
		function IsState(){
 var businessStateName = document.getElementById("businessStateName").value;
   		if (businessStateName == null || businessStateName == '') {
   			document.getElementById('StateName').innerHTML = "Please Select State/UT";
   			document.businessEntityAddressForm.businessStateName.focus();
   			return false;
   		}else {
	        document.getElementById('StateName').innerHTML = "";
   		}
		}
   		});
	
	

//Category

	$(document).ready(function(){
		function Iscategory(){
	 var category = document.getElementById("category").value;
		if (category == null || category == '') {
	   		document.getElementById('Category').innerHTML = "Please Select Category";
			document.businessEntityAddressForm.category.focus();
			return false;
		}else {
	        document.getElementById('Category').innerHTML = "";
		}
		}
	

	});
	</script>








<script type="text/javascript">

function getComboA(selectObject) {
	  var value = selectObject.value;  
	  var select = document.getElementById('businessDistrictName');
	  var dis = '${districtDetails}'; 
	  var length = document.getElementById('businessDistrictName').options.length;
	  for (i = length-1; i >= 0; i--) {
		  document.getElementById('businessDistrictName').options[i] = null;
	  }	  
	<c:forEach var="districtDetailsList" items="${districtDetails}">
	
	if(value == '${districtDetailsList.stateCode}'){
		var opt = document.createElement('option');
	    opt.value = '${districtDetailsList.districtName}';
	    opt.innerHTML = '${districtDetailsList.districtName}';
	    select.appendChild(opt);  
	    
	}
	                                 
	</c:forEach>	  
}



</script>

<script type="text/javascript">
$(document).ready(function() {
	var state = '${businessEntityDetails.businessStateName}';
	var district = '${businessEntityDetails.businessDistrictName}';

	var opt = document.createElement('option');
	opt.value = '${businessEntityDetails.businessDistrictName}';
	opt.innerHTML = '${businessEntityDetails.businessDistrictName}';
	document.getElementById('businessDistrictName').appendChild(opt); 
	
	<c:forEach var="districtDetailsList" items="${districtDetails}">
	if(state == '${districtDetailsList.stateCode}'){
		if(district != '${districtDetailsList.districtName}'){
			var opt = document.createElement('option');
		    opt.value = '${districtDetailsList.districtName}';
		    opt.innerHTML = '${districtDetailsList.districtName}';
		    document.getElementById('businessDistrictName').appendChild(opt);  
		}
	}
	</c:forEach>
});


</script>

<script type="text/javascript">

//Rathour----------------------
$(function() {
   /*  $("#choosefileMoDoc").html('${businessEntityDetails.moaDocFname}');
    $("#choosefileRCDoc").html('${businessEntityDetails.regisAttacDocFname}');
    $("#choosefilebodDoc").html('${businessEntityDetails.bodDocFname}');
   /*  $("#choosefileindusAffidaviteDoc").html('${businessEntityDetails.indusAffidaDocFname}'); */
    //$("#annexureiaformatDoc").html('${businessEntityDetails.annexureiaformat}');
 
 
 
 if ("${businessEntityDetails.moaDocbase64File}" == null
			|| "${businessEntityDetails.moaDocbase64File}" == '') {

	} else {
		document.getElementById('choosefileMoDoc').innerHTML = '${businessEntityDetails.moaDocFname}';
	}

	if ("${businessEntityDetails.regisAttacbase64File}" == null
			|| "${businessEntityDetails.regisAttacbase64File}" == '') {

	} else {
		document.getElementById('choosefileRCDoc').innerHTML = '${businessEntityDetails.regisAttacDocFname}';
	}



	if ("${businessEntityDetails.bodDocbase64File}" == null
			|| "${businessEntityDetails.bodDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilebodDoc').innerHTML = '${businessEntityDetails.bodDocFname}';
	}

	if ("${businessEntityDetails.indusAffidaDocbase64File}" == null
			|| "${businessEntityDetails.indusAffidaDocbase64File}" == '') {

	} else {
		document.getElementById('annexureiaformatDoc').innerHTML = '${businessEntityDetails.annexureiaformat}';
	}
 
 
 
 
 
 
    if('${saveproriter}'!=null  && '${saveproriter}'!=''){
   	 $("#customFields").focus();
    }	      
    });


//Ended......................

	function validationBusinessEntityAddress() {
		
	
		var letters = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/;
		var businessEntityName = document.businessEntityAddressForm.businessEntityName.value;
		if (businessEntityName == null || businessEntityName == '') {
			document.getElementById('BEName').innerHTML = "Business Entity Name is Required.";
			document.businessEntityAddressForm.businessEntityName.focus();
			return false;
		} 
		
		/* if (!letters.test(businessEntityName)) {
			document.getElementById('BEName').innerHTML = "Business Entity Name only Character.";
			document.businessEntityAddressForm.businessEntityName.focus();
			return false;
		}
 */
		var businessEntityType = document.businessEntityAddressForm.businessEntityType.value;
		if (businessEntityType == null || businessEntityType == '') {
			document.getElementById('BEType').innerHTML = "Please Select Organisation Type";
			document.businessEntityAddressForm.businessEntityType.focus();
			return false;
		}else {
	        document.getElementById('BEType').innerHTML = "";
	    } 
	    
		
		var authorisedSignatoryName = document.businessEntityAddressForm.authorisedSignatoryName.value;
		if (authorisedSignatoryName == null || authorisedSignatoryName == '') {
			document.getElementById('APName').innerHTML = "Authorized Signatory Name is Required.";
			document.businessEntityAddressForm.authorisedSignatoryName.focus();
			return false;
		}
		if (!letters.test(authorisedSignatoryName)) {
			document.getElementById('APName').innerHTML = "Authorized Signatory Name only Character.";
			document.businessEntityAddressForm.authorisedSignatoryName.focus();
			return false;
		}

		var businessDesignation = document.businessEntityAddressForm.businessDesignation.value;
		if (businessDesignation == null || businessDesignation == '') {
			document.getElementById('BD').innerHTML = "Designation is Required";
			document.businessEntityAddressForm.businessDesignation.focus();
			return false;
		}
		if (!letters.test(businessDesignation)) {
			document.getElementById('BD').innerHTML = "Designation only Character.";
			document.businessEntityAddressForm.businessDesignation.focus();
			return false;
		}

		var emailId = document.businessEntityAddressForm.emailId.value;
		if (emailId == null || emailId == '') {
			document.getElementById('Email').innerHTML = "Email Id is Required";
			document.businessEntityAddressForm.emailId.focus();
			return false;
		} else {
	        document.getElementById('Email').innerHTML = "";
	    } 
		
		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(emailId)) {
			// value is ok, use it
		} else {
			document.getElementById('Email').innerHTML = "Please Enter a Email ID in format: @gmail.com, @yahoo.com";
			document.businessEntityAddressForm.emailId.focus();
			return false
		}

		var mobileNumber = document.businessEntityAddressForm.mobileNumber.value;
		if (mobileNumber == null || mobileNumber == '') {
			document.getElementById('Mobile').innerHTML = "Mobile Number is Required.";
			document.businessEntityAddressForm.mobileNumber.focus();
			return false;
		}else {
	        document.getElementById('Mobile').innerHTML = "";
	    } 

		if (/^\d{10}$/.test(mobileNumber)) {
			// value is ok, use it
		} else {
			document.getElementById('Mobile').innerHTML = "Mobile Number must be 10 digit.";
			document.businessEntityAddressForm.mobileNumber.focus();
			return false
		}
		
		if (/^[1-9][0-9]{9}$/.test(mobileNumber)) {
			// value is ok, use it
		} else {
			document.getElementById('Mobile').innerHTML = "Mobile Number should not Start with 0 and should not allow all 10 zeros";
			document.businessEntityAddressForm.mobileNumber.focus();
			return false
		}

		
		
		var businessAddress = document.businessEntityAddressForm.businessAddress.value;
		if (businessAddress == null || businessAddress == '') {
			document.getElementById('BAddress').innerHTML = "Registered Office Address is Required.";
			document.businessEntityAddressForm.businessAddress.focus();
			return false;
		} else if (businessAddress.match(/[^a-zA-Z0-9]+$/)) {
			
			document.getElementById('BAddress').innerHTML = "Registered Office Address not allow only special character.";
			document.businessEntityAddressForm.businessAddress.focus();
			return false;
			
		}else {
	        document.getElementById('BAddress').innerHTML = "";
	    } 
		
		var businessCountryName = document.getElementById("businessCountryName").value;
           if (businessCountryName == null || businessCountryName == '') {
			document.getElementById('CountryName').innerHTML = "Please Select Country";
			document.businessEntityAddressForm.businessCountryName.focus();
			return false;
			}else {
		        document.getElementById('CountryName').innerHTML = "";
		        
		    } 
		
        var businessStateName = document.getElementById("businessStateName").value;
   		if (businessStateName == null || businessStateName == '') {
   			document.getElementById('StateName').innerHTML = "Please Select State/UT";
   			document.businessEntityAddressForm.businessStateName.focus();
   			return false;
   		}else {
	        document.getElementById('StateName').innerHTML = "";
	    } 

   		var businessDistrictName = document.getElementById("businessDistrictName").value;
		if (businessDistrictName == null || businessDistrictName == '') {
			document.getElementById('DistrictName').innerHTML = "Please Select District";
			document.businessEntityAddressForm.businessDistrictName.focus();
			return false;
		}else {
	        document.getElementById('DistrictName').innerHTML = "";
	    } 
		
		var businessPinCode = document.getElementById("businessPinCode").value;
		if (businessPinCode == null || businessPinCode == '') {
			document.getElementById('PinCode').innerHTML = "Please Enter Pin Code";
			document.businessEntityAddressForm.businessPinCode.focus();
			return false;
		}else {
	        document.getElementById('PinCode').innerHTML = "";
	    } 
	    
		if (/^\d{6}$/.test(businessPinCode)) {
			// value is ok, use it
		} else {
			document.getElementById('PinCode').innerHTML = "Pin code should cotain only 6 digit number";
			document.businessEntityAddressForm.businessPinCode.focus();
			return false
		}

		 var yearEstablishment = document.getElementById("yearEstablishmentId").value;
			if (yearEstablishment == null || yearEstablishment == '') {
				document.getElementById('YearEstablishment').innerHTML = "Year of Unit Establishment is Required";
				document.businessEntityAddressForm.yearEstablishment.focus();
				return false;
			}else {
		        document.getElementById('YearEstablishment').innerHTML = "";
		    } 

		
		if(!IsGSTIN()){
			document.getElementById('Gstin').innerHTML = "Please enter GSTIN number.";
			document.businessEntityAddressForm.gstin.focus();
			return false;
		}
			
		if(!IsCIN()){
			document.getElementById('Cin').innerHTML = "Please enter a valid CIN number.";
			document.businessEntityAddressForm.cin.focus();
			return false;
		}
		
		if(!IsPAN()){
			document.getElementById('CPanNo').innerHTML = "Please enter valid PAN number.";
			document.getElementById("companyPanNoId").focus();
			return false;
		}

		//alert("clilkec");		 
		if(!ValidateMaoSize(document.getElementById('moaDoc'))){
			return false;
		}
		
		if(!ValidateregistrationAttachedSize(document.getElementById('registrationAttachedDoc'))){
			return false;
		}

		if(!ValidatebodDocSize(document.getElementById('bodDoc'))){
			return false;
		}

		if(!ValidatebodDocSize(document.getElementById('bodDoc'))){
			return false;
		}
				
		/*  if(!ValidateIsindusAffidaviteSize(document.getElementById('indusAffidaviteDoc'))){
			return false;
		}  */
		 
		 if(!ValidateIsannexureiaformatSize(document.getElementById('annexureiaformatId'))){
				return false;
			}

			function ValidateSize2(file) {
				var caCertificate=document.getElementById("caCertificate").value;		
				if(caCertificate!=null && caCertificate!='')
					{ //alert();
					var ext = caCertificate.split('.').pop();
					if (ext == "pdf") {
						document.getElementById('CaCertificateFileName').innerHTML="";
					} 
					else {
						document.getElementById('CaCertificateFileName').innerHTML = "Please upload file in PDF Format.";
						document.getElementById('caCertificate').focus();	
						return false;
					}
			      
				
				var maxSize = '500';
				if(file.files[0].size >1048576){
				var fsize = (file.files[0].size / (1024*1024)).toFixed(1);    
			    if (file.files[0].size > maxSize) {
			    	document.getElementById('CaCertificateFileName').innerHTML = "Your file size is: "+fsize+" mb," +" File size should not be more than 500 kb";
			    	document.getElementById('caCertificate').focus();
					return false;
			    }
			 }
				else
					{
					var fsize = (file.files[0].size / 1024).toFixed(1);    
				    if (fsize > maxSize) {
				    	document.getElementById('CaCertificateFileName').innerHTML = "Your file size is: "+fsize+" kb," +" File size should not be more than 500 kb";
				    	document.getElementById('caCertificate').focus();
						return false;
					}
				    else{
				    	document.getElementById('CaCertificateFileName').innerHTML="";
				    }
			   }
			  }	
			 }



			

			var registrationAttachedDoc = document.getElementById("choosefileRCDoc").innerHTML;
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
       
			var bodDoc = document.getElementById("choosefilebodDoc").innerHTML;
			if (bodDoc == null || bodDoc == '') {
				document.getElementById('BodDoc').innerHTML = "Certified copy of the resolution of the Board of Directors of the Company is Required";
				document.businessEntityAddressForm.bodDoc.focus();
				return false;
			}else {
		        document.getElementById('BodDoc').innerHTML = "";
		    } 
			var ext = bodDoc.split('.').pop();
			if (ext == "pdf") {
				 document.getElementById('BodDoc').innerHTML = "";
			} else {
				document.getElementById('BodDoc').innerHTML = "Please Upload Certified copy of the resolution of the Board of Directors of the Company in PDF Format";
				document.businessEntityAddressForm.bodDoc.focus();
				return false;
			}

		/* 	var indusAffidaviteDoc = document.getElementById("choosefileindusAffidaviteDoc").innerHTML;
			if (indusAffidaviteDoc == null || indusAffidaviteDoc == '') {
				document.getElementById('IndusAffidaviteDoc').innerHTML = "Competent Authority of the Industrial Undertaking authorizing the deponent be provided along with the affidavit is Required";
				document.businessEntityAddressForm.indusAffidaviteDoc.focus();
				return false;
			}else {
		        document.getElementById('IndusAffidaviteDoc').innerHTML = "";
		    } 
			var ext = indusAffidaviteDoc.split('.').pop();
			if (ext == "pdf") {
				// value is ok, use it
			} else {
				document.getElementById('IndusAffidaviteDoc').innerHTML = "Please Upload Competent Authority of the Industrial Undertaking authorizing the deponent be provided along with the affidavit in PDF Format";
				document.businessEntityAddressForm.indusAffidaviteDoc.focus();
				return false;
			} */
			//Rathour----------------------------------
			var annexureiaformatId=document.getElementById("annexureiaformatDoc").innerHTML;
		     // regiOrLicenseFile = 'data:application/pdf;base64,${projectDetailsForm.pdfFile}';
				if (annexureiaformatId == null || annexureiaformatId == '') {
					document.getElementById('AnnexureiaformatId').innerHTML = "Annexure I-A Format is Required.";
					document.getElementById('annexureiaformatId').focus();
					return false;
				}
				
				var annexureiaformatId=document.getElementById("annexureiaformatId").value;		
				if(annexureiaformatId!=null && annexureiaformatId!='')
					{
					var ext = annexureiaformatId.split('.').pop();
					if (ext == "pdf") {
						// value is ok, use it
					} else {
						document.getElementById('AnnexureiaformatId').innerHTML = "Please upload file in PDF Format.";
						document.getElementById('annexureiaformatId').focus();	
						return false;
					}
		        }			
		//ended---------------------------------------
				<c:if test="${empty ProprietorDetailsList}">
				  alert('Please Fill the Director / Partner / Owner / Proprietor Details.');	
			     return false
			     </c:if>

	       if (confirm("Are you sure, You want to save Business Entity record."))   
		      {
	    	   $("#businessEntityType").removeAttr("disabled");
   			$("#emailId").removeAttr("readonly");
   			$("#mobileNumberId").removeAttr("readonly");
   			$("#phoneNo").removeAttr("readonly");
   			$("#fax").removeAttr("readonly");
   			$("#businessAddress").removeAttr("readonly");
   			$("#businessCountryName").removeAttr("disabled");
   			$("#businessStateName").removeAttr("disabled");
   			$("#businessDistrictName").removeAttr("disabled");
   			$("#businessPinCode").removeAttr("readonly");

   			 $("input[name='directorName']").attr("readonly", false);
   			 $("input[name='designation']").attr("readonly", false);
   			 $("input[name='equity']").attr("readonly", false);
   			 $("input[name='mobileNo']").attr("readonly", false);
   			 $("input[name='proprietorDetailsaddress']").attr("readonly", false);
   			 $("input[name='email']").attr("readonly", false);
   			 document.getElementById("MaleOpt2").disabled = false;
   			 document.getElementById("FemaleOpt2").disabled = false;
   			 document.getElementById("category").disabled = false;		
   			 document.getElementById("DivyangYes").disabled = false;
   			 document.getElementById("DivyangNo").disabled = false;
   			 $("input[name='panCardNo']").attr("readonly", false);
   			 $("input[name='din']").attr("readonly", false);
   			 
   			$("#yearEstablishmentId").removeAttr("readonly");
   			$("#gstin").removeAttr("readonly");
   			$("#cin").removeAttr("readonly");
   			$("#companyPanNoId").removeAttr("readonly");
   			document.getElementById("moaDoc").disabled = false;
   			document.getElementById("registrationAttachedDoc").disabled = false;
   			document.getElementById("bodDoc").disabled = false;
   			document.getElementById("annexureiaformatId").disabled = false;	
   			document.getElementById("addButtonId").disabled = false;    			
   			$("#editButton").removeClass("disable_a_href");
   			$("#deleteButton").removeClass("disable_a_href");   
			      
	            return true;
			  }
			    else {
				 return false;
			    } 	 			 		
}

	function validatMaoDoc() {
		if ("${businessEntityDetails.base64EncodedMaoDoc}" == null || "${businessEntityDetails.base64EncodedMaoDoc}" == '' ) {
			
		}else{
			document.getElementById("moaDoc").src = "data:image/png;base64,${businessEntityDetails.base64EncodedMaoDoc}";
			
		}
	}

	function validatRCDoc() {
		if ("${businessEntityDetails.base64EncodedRCDoc}" == null || "${businessEntityDetails.base64EncodedRCDoc}" == '' ) {
			
		}else{
			document.getElementById("registrationAttachedDoc").src = "data:image/png;base64,${businessEntityDetails.base64EncodedRCDoc}";
			
		}
	}
	
	/* function ValidateSize(file) {
        var FileSize = file.files[0].size / 1024/1024; // in MB
        FileSize = FileSize.toFixed(1);
        if (FileSize > 1) {
        	document.getElementById('CPanDoc').innerHTML = "to big, file size exceeds maximum is 1 MB";
			document.businessEntityForm.companyPanCardDoc.focus();
			return false;
        }
      } */
	function ValidateregistrationAttachedSize(file) {
    	  var registrationAttachedDoc = document.getElementById('registrationAttachedDoc').value;
    	  //var filename = registrationAttachedDoc.replace(/^.*[\\\/]/, '');
    	if('${businessEntityDetails.regisAttacDocFname}' != '' && ''  == registrationAttachedDoc){
  			return true;
  		}
    	  
    	  //var registrationAttachedDoc = document.getElementById("choosefileRCDoc").innerHTML;
    	   if(registrationAttachedDoc!=null && registrationAttachedDoc!=''){
		   var maxSize = '2000';   
			var ext = registrationAttachedDoc.split('.').pop();
			if (ext == "pdf") {
				
			} else {
				document.getElementById('CIRA').innerHTML = "Please Upload Certificate Incorpoation Registration Attachment in PDF Format";
				document.getElementById('registrationAttachedDoc').focus();
				return false;
			}
  	
			var FileSize = file.files[0].size / 1024/1024; // in MB
        FileSize = FileSize.toFixed(2);
	if (FileSize > 2) {
    	document.getElementById('CIRA').innerHTML = "File size uploaded is greater than 2MB";
    	document.getElementById('registrationAttachedDoc').focus();
		return false;
	} 
    else 
		{
		var fsize = (file.size / 1024).toFixed(1);    
	    if (fsize > maxSize) {
	    	document.getElementById('CIRA').innerHTML = "Your file size is: "+fsize+" kb," +" File size should not be more than 2 MB";
	    	document.getElementById('registrationAttachedDoc').focus();
			return false;
		}
	    
  } 
    	   }else{
    		   document.getElementById('CIRA').innerHTML = "Certificate Incorpoation Registration Attachment is Required.";
				document.getElementById('registrationAttachedDoc').focus();
				return false;
         	  }
	document.getElementById('CIRA').innerHTML = "";
	  return true;
}  	
	function ValidateMaoSize(file) {
		var moaDoc = document.getElementById('moaDoc').value;
		//var filename = moaDoc.replace(/^.*[\\\/]/, '');
		if('${businessEntityDetails.moaDocFname}' != '' && moaDoc == ''){
			return true;
		}
		
		 var maxSize = '2000';
		 if(moaDoc!=null && moaDoc!=''){
		//var moaDoc = document.getElementById("choosefileMoDoc").innerHTML;
		var ext = moaDoc.split('.').pop();
		if (ext == "pdf") {
			// value is ok, use it
		} else {
			document.getElementById('MaoDoc').innerHTML = "Please Upload MoA/Partnership Deed Attachment in PDF Format";
			document.getElementById('moaDoc').focus();
			return false;
		}
				
        var FileSize = file.files[0].size / 1024/1024; // in MB
        FileSize = FileSize.toFixed(2);
        if (FileSize >2) {
        	document.getElementById('MaoDoc').innerHTML = "File size uploaded is greater than 2MB";
        	document.getElementById('moaDoc').focus();
			return false;
        }
        else 
   		{
   		var fsize = (file.size / 1024).toFixed(1);    
   	    if (fsize > maxSize) {
   	    	document.getElementById('MaoDoc').innerHTML = "Your file size is: "+fsize+" MB," +" File size should not be more than 2 MB";
   	    	document.getElementById('moaDoc').focus();
   			return false;
   		}
   	    
      }
    }else{
    	document.getElementById('MaoDoc').innerHTML = "MoA/Partnership Deed Attachment is Required.";
		document.getElementById('moaDoc').focus();
		return false;
      }
		    document.getElementById('MaoDoc').innerHTML = "";
		    return true;
}
		 
	function ValidatebodDocSize(file) {
		var bodDocFname = document.getElementById('bodDoc').value;
		//var filename = bodDocFname.replace(/^.*[\\\/]/, '');
		if( '${businessEntityDetails.bodDocFname}' != '' && bodDocFname == ''){
			return true;
		}
		
		 var maxSize = '2000';
		 if(bodDocFname!=null && bodDocFname!=''){
			 var ext = bodDocFname.split('.').pop();
		if (ext == "pdf") {
			document.getElementById('BodDoc').innerHTML = "";
		} else {
			document.getElementById('BodDoc').innerHTML = "Please Upload Certified copy of the resolution of the Board of Directors of the Company in PDF Format";;
			document.getElementById('bodDoc').focus();
			return false;
		}
				
      var FileSize = file.files[0].size / 1024/1024; // in MB
      FileSize = FileSize.toFixed(2);
      if (FileSize >2) {
    	    document.getElementById('BodDoc').innerHTML = "File size uploaded is greater than 2MB";
    	    document.getElementById('bodDoc').focus();
			return false;
      }
      else 
 		{
 		var fsize = (file.size / 1024).toFixed(1);    
 	    if (fsize > maxSize) {
 	    	document.getElementById('BodDoc').innerHTML = "Your file size is: "+fsize+" MB," +" File size should not be more than 2 MB";
 	    	document.getElementById('bodDoc').focus();
 			return false;
 		}
 	    
    }
  }else{
	  document.getElementById('BodDoc').innerHTML = "Certified copy of the resolution of the Board of Directors of the Company is Required.";;
		document.getElementById('bodDoc').focus();
		return false;

	  }
		 document.getElementById('BodDoc').innerHTML = "";
		    return true;
       
        
    }


/* 	function ValidateIsindusAffidaviteSize(file) {
		var indusAffidaDocFname = document.getElementById('indusAffidaviteDoc').value;
		//var filename = indusAffidaDocFname.replace(/^.*[\\\/]/, '');
		if( '${businessEntityDetails.indusAffidaDocFname}' != '' && indusAffidaDocFname == ''){
			return true;
		}
		
		 var maxSize = '2000';
		 if(moaDoc!=null && moaDoc!=''){
		var ext = indusAffidaDocFname.split('.').pop();
		if (ext == "pdf") {
			 document.getElementById('IndusAffidaviteDoc').innerHTML = "";
		} else {
			document.getElementById('IndusAffidaviteDoc').innerHTML = "Please Upload Competent Authority of the Industrial Undertaking authorizing the deponent be provided along with the affidavit in PDF Format.";
			document.getElementById('indusAffidaviteDoc').focus();
			return false;
		}
				
       var FileSize = file.files[0].size / 1024/1024; // in MB
       FileSize = FileSize.toFixed(2);
       if (FileSize >2) {
    	   document.getElementById('IndusAffidaviteDoc').innerHTML = "to big, file size exceeds maximum is 2 MB";
    	   document.getElementById('indusAffidaviteDoc').focus();
			return false;
       }
       else 
  		{
  		var fsize = (file.size / 1024).toFixed(1);    
  	    if (fsize > maxSize) {
  	    	 document.getElementById('IndusAffidaviteDoc').innerHTML = "Your file size is: "+fsize+" MB," +" File size should not be more than 2 MB";
  	    	document.getElementById('indusAffidaviteDoc').focus();
  			return false;
  		}
  	    
     }
   }else{
	   document.getElementById('IndusAffidaviteDoc').innerHTML = "Competent Authority of the Industrial Undertaking authorizing the deponent be provided along with the affidavit is Required.";
		document.getElementById('indusAffidaviteDoc').focus();
		return false;
	   
	   }
		 document.getElementById('IndusAffidaviteDoc').innerHTML = "";
	    return true;
} */

	function ValidateIsannexureiaformatSize(file) {
		var annexureiaformat = document.getElementById('annexureiaformatId').value;
		//var filename = annexureiaformat.replace(/^.*[\\\/]/, '');
		if('${businessEntityDetails.annexureiaformat}'  != '' && annexureiaformat == ''){
			return true;
		}
		
		//var moaDoc = document.getElementById("choosefileMoDoc").innerHTML;
		 if(annexureiaformat!=null && annexureiaformat!=''){
		var ext = annexureiaformat.split('.').pop();
		if (ext == "pdf") {
			
		} else {
			document.getElementById('AnnexureiaformatId').innerHTML = "Please Upload Annexure I-A Format in PDF Format.";
			document.getElementById('annexureiaformatId').focus();
			return false;
		}
		 }else{
			 document.getElementById('AnnexureiaformatId').innerHTML = "Annexure I-A Format is Required.";
				document.getElementById('annexureiaformatId').focus();
				return false;

		}
		
        var FileSize = file.files[0].size / 1024/1024; // in MB
        FileSize = FileSize.toFixed(2);
        if (FileSize >2) {
        	document.getElementById('AnnexureiaformatId').innerHTML = "File size uploaded is greater than 2MB";
        	document.getElementById('annexureiaformatId').focus();
			return false;
        } else {
        	 document.getElementById('AnnexureiaformatId').innerHTML = "";
        	 return true;
        } 
        
    } 
	//var totalEquity=0.0;
	//var status;
     function getEquity()
    {
    	
            //alert("bye getEquity");
    
     var totalEquity=document.getElementById('equityId').value;
    // var equity=$('#equityvalue').val();
     var currentEquity=document.getElementById('equityvalue').value;
    // alert("currentequity="+currentEquity);
    	 <c:forEach var="list" items="${ProprietorDetailsList}">

    	 var value = '${list.equity}';
    	// alert("Listofvalue="+value);
    	 totalEquity=parseFloat(totalEquity)+parseFloat(value);	 
    	                              
    	</c:forEach>	
    	 
    	if(currentEquity>0.0)
        	{
    		totalEquity=totalEquity-currentEquity;
        	}
    
   // alert("final"+totalEquity);

       if(totalEquity>100)
           {

    	   alert("Equity more than 100% is not allowed");
    	   return false;
           }    	
       
    	 <%-- totalEquity1=('${totalEquity}');
    	// totalEquity1='<%=session.getAttribute("totalEquity")%>';
    	 status=('${status}');

    	 var equity = "<c:out value='${totalClients}'/>";
    	 alert("equity"+equity)
    	 alert("status="+status);
    	 alert("totalEquity on onlod="+totalEquity1); --%>
     }
	function validationProprietorDetailsForm()
	{			
		var letters = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/;
		var directorName = document.getElementById('directorNameId').value;
		if (directorName == null || directorName == '') {
			document.getElementById('DirectorName').innerHTML = "Director Name is Required";
			document.getElementById('directorNameId').focus();
			return false;
		}else {
	        document.getElementById('DirectorName').innerHTML = "";
	    } 
	
		 if (!letters.test(directorName)) {
			document.getElementById('DirectorName').innerHTML = "Director Name only Character";
			document.getElementById('directorNameId').focus();
			return false;
		}  
	
		var designation = document.getElementById('designationId').value;
		if (designation == null || designation == '') {
			document.getElementById('Designation').innerHTML = "Designation is Required";
			document.getElementById('designationId').focus();
			return false;
		}else {
	        document.getElementById('Designation').innerHTML = "";
	    } 
		
		var equity = document.getElementById('equityId').value;
		if (equity == null || equity == '') {
			document.getElementById('Equity').innerHTML = "Equity is Required";
			document.getElementById('equityId').focus();
			return false;
		} else {
	        document.getElementById('Equity').innerHTML = "";
	    } 
		/* if (/^([1-9]([0-9])?|0)(\.[0-9]{1,2})?$/.test(equity)) {
			// value is ok, use it
		} 
		 else {
			document.getElementById('Equity').innerHTML = "Equity should be in Numeric Decimal Format. Example like:35.78";
			document.getElementById('equityId').focus();
			return false
		} 
 */
		/* if(equity >= 100){
			document.getElementById('Equity').innerHTML = "Your %Equity should not be greater than 100";
			document.getElementById('equityId').focus();
			return false
		}  */



		var mobileNumber = document.getElementById('mobileNoId').value;
		if (mobileNumber == null || mobileNumber == '') {
			document.getElementById('MobileNo').innerHTML = "Mobile Number is Required.";
			document.getElementById('mobileNoId').focus();
			return false;
		}else {
	        document.getElementById('MobileNo').innerHTML = "";
	    } 
		if (/^\d{10}$/.test(mobileNumber)) {
			// value is ok, use it
		} else {
			document.getElementById('MobileNo').innerHTML = "Mobile no must be 10 digit.";
			document.getElementById('mobileNoId').focus();
			return false
		}
		
		if (/^[1-9][0-9]{9}$/.test(mobileNumber)) {
			// value is ok, use it
		} else {
			document.getElementById('MobileNo').innerHTML = "Mobile Number should not Start with 0 and should not allow all 10 zeros";
			document.getElementById('mobileNoId').focus();
			return false
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
		
		function validateNumberField() {
			return event.charCode > 47 && event.charCode < 58;
		}
		
		var ProprietorDetailsaddress = document.getElementById('proprietorDetailsaddressId').value;
		if (ProprietorDetailsaddress == null || ProprietorDetailsaddress == '') {
			document.getElementById('ProprietorDetailsAddress').innerHTML = "Address is Required.";
			document.getElementById('proprietorDetailsaddressId').focus();
			return false;
		}else {
	        document.getElementById('ProprietorDetailsAddress').innerHTML = "";
	    } 
		
		var email = document.getElementById('email').value;
		if (email == null || email == '') {
			document.getElementById('Email2').innerHTML = "Email Id is Required.";
			document.getElementById('email').focus();
			return false;
		}else {
	        document.getElementById('Email2').innerHTML = "";
	    } 
				
		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
			// value is ok, use it
		} else {
			document.getElementById('Email2').innerHTML = "Please Enter a Email ID in format: @gmail.com, @yahoo.com";
			document.getElementById('email').focus();
			return false
		}

		var radios = document.getElementsByName("gender");
	    var formValid = false;

	    var i = 0;
	    while (!formValid && i < radios.length) {
	        if (radios[i].checked) formValid = true;
	        i++;        
	    }

	    if (!formValid) {
		    
	   	document.getElementById('Gender').innerHTML = "Please select Gender."; 
	    return false;
	    }else {
	        document.getElementById('Gender').innerHTML = "";
	    } 

	    var category = document.getElementById("category").value;
   		if (category == null || category == '') {
   	   		document.getElementById('Category').innerHTML = "Please Select Category";
   			document.businessEntityAddressForm.category.focus();
   			return false;
   		}else {
	        document.getElementById('Category').innerHTML = "";
	    } 


   		var div_Status = document.getElementsByName("div_Status");
	    var div_StatusValid = false;

	    var i = 0;
	    while (!div_StatusValid && i < div_Status.length) {
	        if (div_Status[i].checked) div_StatusValid = true;
	        i++;        
	    }

	    if (!div_StatusValid) {
	   	document.getElementById('Div_Status').innerHTML = "Please select Divyang."; 
	    return false;
	    }else {
	        document.getElementById('Div_Status').innerHTML = "";
	    } 
	    
   		
		 var panCardNo = document.getElementById('panCardNOId').value;
			if (panCardNo == null || panCardNo == '') {
				document.getElementById('panCardNO').innerHTML = "PAN Number is Required";
				document.getElementById('panCardNOId').focus();
				return false;
			}else {
		        document.getElementById('panCardNO').innerHTML = "";
		    } 
			if (/[A-Z]{3}[PCHFATBLJG]{1}[A-Z]{1}[0-9]{4}[A-Z]{1}$/
					.test(panCardNo)) {
				// value is ok, use it
			} else {
				document.getElementById('panCardNO').innerHTML = "Please Enter Valid PAN Number";
				document.getElementById('panCardNOId').focus();
				return false
			}
		
			var designation = document.getElementById('designationId').value;
			var dinNo = document.getElementById('din').value;
			if (designation.includes("Director") || designation.includes("DIRECTOR") || designation.includes("director")) {
			
				if (dinNo == null || dinNo == '') 
				{
					document.getElementById('Din').innerHTML = "Din Number is Required";
					document.getElementById('din').focus();
					return false;
				}else {
			        document.getElementById('Din').innerHTML = "";
			    } 
			}
				  //alert("bye getEquity");
		            
		            var totalEquity=document.getElementById('equityId').value;
		           // var equity=$('#equityvalue').val();
		            var currentEquity=document.getElementById('equityvalue').value;
		            //alert("currentequity="+currentEquity);
		           	 <c:forEach var="list" items="${ProprietorDetailsList}">

		           	 var value = '${list.equity}';
		           	// alert("Listofvalue="+value);
		           	 totalEquity=parseFloat(totalEquity)+parseFloat(value);	 
		           	                              
		           	</c:forEach>	
	
		           	if(currentEquity>0.0)
		               	{
		           		totalEquity=totalEquity-currentEquity;
		               	}
		           
		          // alert("final"+totalEquity);

		              if(totalEquity>100)
		                  {

		           	   alert("More than 100 is not allowed");
		           	   return false;
		                  }	

			
		              $("#businessEntityType").removeAttr("disabled");
		    			$("#emailId").removeAttr("readonly");
		    			$("#mobileNumberId").removeAttr("readonly");
		    			$("#phoneNo").removeAttr("readonly");
		    			$("#fax").removeAttr("readonly");
		    			$("#businessAddress").removeAttr("readonly");
		    			$("#businessCountryName").removeAttr("disabled");
		    			$("#businessStateName").removeAttr("disabled");
		    			$("#businessDistrictName").removeAttr("disabled");
		    			$("#businessPinCode").removeAttr("readonly");

		    			 $("input[name='directorName']").attr("readonly", false);
		    			 $("input[name='designation']").attr("readonly", false);
		    			 $("input[name='equity']").attr("readonly", false);
		    			 $("input[name='mobileNo']").attr("readonly", false);
		    			 $("input[name='proprietorDetailsaddress']").attr("readonly", false);
		    			 $("input[name='email']").attr("readonly", false);
		    			 document.getElementById("MaleOpt2").disabled = false;
		    			 document.getElementById("FemaleOpt2").disabled = false;
		    			 document.getElementById("category").disabled = false;		
		    			 document.getElementById("DivyangYes").disabled = false;
		    			 document.getElementById("DivyangNo").disabled = false;
		    			 $("input[name='panCardNo']").attr("readonly", false);
		    			 $("input[name='din']").attr("readonly", false);
		    			 
		    			$("#yearEstablishmentId").removeAttr("readonly");
		    			$("#gstin").removeAttr("readonly");
		    			$("#cin").removeAttr("readonly");
		    			$("#companyPanNoId").removeAttr("readonly");
		    			document.getElementById("moaDoc").disabled = false;
		    			document.getElementById("registrationAttachedDoc").disabled = false;
		    			document.getElementById("bodDoc").disabled = false;
		    			document.getElementById("annexureiaformatId").disabled = false;	
		    			document.getElementById("addButtonId").disabled = false;    			
		    			$("#editButton").removeClass("disable_a_href");
		    			$("#deleteButton").removeClass("disable_a_href");   	

			    

			
}

	
	
	
	
	
//Rathour------------------------------
	
	function ValidateSizeAnn(file) {
	
	var annexureiaformatId=document.getElementById("annexureiaformatId").value;		
	if(annexureiaformatId!=null && annexureiaformatId!='')
		{
		var ext = annexureiaformatId.split('.').pop();
		if (ext == "pdf") {
			document.getElementById('AnnexureiaformatId').innerHTML="";
		} 
		else {
			document.getElementById('AnnexureiaformatId').innerHTML = "Please upload file in PDF Format.";
			document.getElementById('annexureiaformatId').focus();	
			return false;
		}
      }
	
	var maxSize = '500';
	if(file.files[0].size >1048576){
	var fsize = (file.files[0].size / (1024*1024)).toFixed(1);    
    if (file.files[0].size > maxSize) {
    	document.getElementById('AnnexureiaformatId').innerHTML = "Your file size is: "+fsize+" mb," +" File size should not be more than 500 kb";
    	document.getElementById('annexureiaformatId').focus();
		return false;
    }
 }
	else
		{
		var fsize = (file.files[0].size / 1024).toFixed(1);    
	    if (fsize > maxSize) {
	    	document.getElementById('AnnexureiaformatId').innerHTML = "Your file size is: "+fsize+" kb," +" File size should not be more than 500 kb";
	    	document.getElementById('annexureiaformatId').focus();
			return false;
		}
	    else{
	    	document.getElementById('AnnexureiaformatId').innerHTML="";
	    }
  }
}
	//ended------------------------
 /* function validationBusinessEntityAddress()

 {	
	 <c:if test="${empty ProprietorDetailsList}">	
     return false
     </c:if>

 } */

 $(function(){
	    var dtToday = new Date();
	    
	    var month = dtToday.getMonth() + 1;
	    var day = dtToday.getDate();
	    var year = dtToday.getFullYear();
	    if(month < 10)
	        month = '0' + month.toString();
	    if(day < 10)
	        day = '0' + day.toString();
	    
	    var maxDate = year + '-' + month + '-' + day;
	    //alert(maxDate);
	    $('#yearEstablishmentId').attr('max', maxDate);
	    
	    if('${bussinessId}'!=null && '${bussinessId}'!='')
		 {
	      $('#businessEntityType').attr('disabled', 'disabled');		 
		 $("input[name='emailId']").attr("readonly", true);
		 $("input[name='mobileNumber']").attr("readonly", true);
		 $("input[name='phoneNo']").attr("readonly", true);
		 $("input[name='fax']").attr("readonly", true);
		 $("input[name='businessAddress']").attr("readonly", true);
		 $('#businessCountryName').attr('disabled', 'disabled');
		 $('#businessStateName').attr('disabled', 'disabled');
		 $('#businessDistrictName').attr('disabled', 'disabled');		
		 $("input[name='PinCode']").attr("readonly", true);
		 $("input[name='yearEstablishment']").attr("readonly", true);
		 $("input[name='gstin']").attr("readonly", true);
		 $("input[name='cin']").attr("readonly", true);
		 $("input[name='companyPanNo']").attr("readonly", true);
		 document.getElementById("moaDoc").disabled = true;
		 document.getElementById("registrationAttachedDoc").disabled = true;
		 document.getElementById("bodDoc").disabled = true;
		 document.getElementById("annexureiaformatId").disabled = true;

		 $("input[name='directorName']").attr("disabled", true);
		 $("input[name='designation']").attr("disabled", true);
		 $("input[name='equity']").attr("disabled", true);
		 $("input[name='mobileNo']").attr("disabled", true);
		 $("input[name='proprietorDetailsaddress']").attr("disabled", true);
		 $("input[name='email']").attr("disabled", true);
		 document.getElementById("MaleOpt2").disabled = true;
		 document.getElementById("FemaleOpt2").disabled = true;
		 document.getElementById("category").disabled = true;		
		 document.getElementById("DivyangYes").disabled = true;
		 document.getElementById("DivyangNo").disabled = true;
		 $("input[name='panCardNo']").attr("disabled", true);
		 $("input[name='din']").attr("disabled", true);
		 
		 $("#editButton").addClass("disable_a_href");
		 $("#deleteButton").addClass("disable_a_href");	 
		 document.getElementById("addButtonId").disabled = true;

		 
		 
	       }
	 else
		 {
		 $("#businessEntityType").removeAttr("disabled");
			$("#emailId").removeAttr("readonly");
			$("#mobileNumberId").removeAttr("readonly");
			$("#phoneNo").removeAttr("readonly");
			$("#fax").removeAttr("readonly");
			$("#businessAddress").removeAttr("readonly");
			$("#businessCountryName").removeAttr("disabled");
			$("#businessStateName").removeAttr("disabled");
			$("#businessDistrictName").removeAttr("disabled");
			$("#businessPinCode").removeAttr("readonly");

			 $("input[name='directorName']").attr("disabled", false);
			 $("input[name='designation']").attr("disabled", false);
			 $("input[name='equity']").attr("disabled", false);
			 $("input[name='mobileNo']").attr("disabled", false);
			 $("input[name='proprietorDetailsaddress']").attr("disabled", false);
			 $("input[name='email']").attr("disabled", false);
			 document.getElementById("MaleOpt2").disabled = false;
			 document.getElementById("FemaleOpt2").disabled = false;
			 document.getElementById("category").disabled = false;		
			 document.getElementById("DivyangYes").disabled = false;
			 document.getElementById("DivyangNo").disabled = false;
			 $("input[name='panCardNo']").attr("disabled", false);
			 $("input[name='din']").attr("disabled", false);
			 
			$("#yearEstablishmentId").removeAttr("readonly");
			$("#gstin").removeAttr("readonly");
			$("#cin").removeAttr("readonly");
			$("#companyPanNoId").removeAttr("readonly");
			document.getElementById("moaDoc").disabled = false;
			document.getElementById("registrationAttachedDoc").disabled = false;
			document.getElementById("bodDoc").disabled = false;
			document.getElementById("annexureiaformatId").disabled = false;	
			document.getElementById("addButtonId").disabled = false;    			
			$("#editButton").removeClass("disable_a_href");
			$("#deleteButton").removeClass("disable_a_href");   
			 
		 }	
	});

 
      
function editBussinessDetails() {

    		var r = confirm('Are you Sure,Want to Edit the Bussiness Entity Details?');

    		if (r == true) {
    			
    			$("#businessEntityType").removeAttr("disabled");
    			$("#emailId").removeAttr("readonly");
    			$("#mobileNumberId").removeAttr("readonly");
    			$("#phoneNo").removeAttr("readonly");
    			$("#fax").removeAttr("readonly");
    			$("#businessAddress").removeAttr("readonly");
    			$("#businessCountryName").removeAttr("disabled");
    			$("#businessStateName").removeAttr("disabled");
    			$("#businessDistrictName").removeAttr("disabled");
    			$("#businessPinCode").removeAttr("readonly");
    			 $("input[name='directorName']").attr("disabled", false);
    			 $("input[name='designation']").attr("disabled", false);
    			 $("input[name='equity']").attr("disabled", false);
    			 $("input[name='mobileNo']").attr("disabled", false);
    			 $("input[name='proprietorDetailsaddress']").attr("disabled", false);
    			 $("input[name='email']").attr("disabled", false);
    			 document.getElementById("MaleOpt2").disabled = false;
    			 document.getElementById("FemaleOpt2").disabled = false;
    			 document.getElementById("category").disabled = false;		
    			 document.getElementById("DivyangYes").disabled = false;
    			 document.getElementById("DivyangNo").disabled = false;
    			 $("input[name='panCardNo']").attr("disabled", false);
    			 $("input[name='din']").attr("disabled", false);    			 
    			$("#yearEstablishmentId").removeAttr("readonly");
    			$("#gstin").removeAttr("readonly");
    			$("#cin").removeAttr("readonly");
    			$("#companyPanNoId").removeAttr("readonly");
    			document.getElementById("moaDoc").disabled = false;
    			document.getElementById("registrationAttachedDoc").disabled = false;
    			document.getElementById("bodDoc").disabled = false;
    			document.getElementById("annexureiaformatId").disabled = false;	
    			document.getElementById("addButtonId").disabled = false;    			
    			$("#editButton").removeClass("disable_a_href");
    			$("#deleteButton").removeClass("disable_a_href"); 
    			document.getElementById('businessEntityType').focus();  			
    			
    		} else {
    			return false
    		}
    	}	


function fetchdata(){
	$(document).ready(function() {
		$(document).on("change", ".form-control", function() {
			 document.getElementById("emailId").disableValidation = true;
			 document.getElementById("businessEntityType").disableValidation = true;
			 document.getElementById("mobileNumberId").disableValidation = true;
			 document.getElementById("businessAddress").disableValidation = true;
			 document.getElementById("businessPinCode").disableValidation = true;
			 document.getElementById("phoneNo").disableValidation = true;
			 document.getElementById("fax").disableValidation = true;
			 document.getElementById("businessAddress").disableValidation = true;
			 document.getElementById("businessCountryName").disableValidation = true;
			 document.getElementById("businessStateName").disableValidation = true;
			 document.getElementById("businessDistrictName").disableValidation = true;
			 document.getElementById("businessPinCode").disableValidation = true;
			 document.getElementById("yearEstablishmentId").disableValidation = true;
			 document.getElementById("gstin").disableValidation = true;
			 document.getElementById("cin").disableValidation = true;
			 document.getElementById("companyPanNoId").disableValidation = true;
			 	
			 document.getElementById("annexureiaformatId").disableValidation = true;		 
			// document.getElementById("businessEntityType").disableValidation = true;
			document.getElementById("myFormB").action = "autobusinessDetails";
			//editBussinessDetails();
			document.getElementById('myFormB').submit();
		});
	});
	}

	/* $(document).change(function(){
		 setInterval(fetchdata,360000);
		 $("#businessEntityType").removeAttr("disabled");
			$("#emailId").removeAttr("readonly");
			$("#mobileNumberId").removeAttr("readonly");
			$("#phoneNo").removeAttr("readonly");
			$("#fax").removeAttr("readonly");
			$("#businessAddress").removeAttr("readonly");
			$("#businessCountryName").removeAttr("disabled");
			$("#businessStateName").removeAttr("disabled");
			$("#businessDistrictName").removeAttr("disabled");
			$("#businessPinCode").removeAttr("readonly");

			 $("input[name='directorName']").attr("readonly", false);
			 $("input[name='designation']").attr("readonly", false);
			 $("input[name='equity']").attr("readonly", false);
			 $("input[name='mobileNo']").attr("readonly", false);
			 $("input[name='proprietorDetailsaddress']").attr("readonly", false);
			 $("input[name='email']").attr("readonly", false);
			 document.getElementById("MaleOpt2").disabled = false;
			 document.getElementById("FemaleOpt2").disabled = false;
			 document.getElementById("category").disabled = false;		
			 document.getElementById("DivyangYes").disabled = false;
			 document.getElementById("DivyangNo").disabled = false;
			 $("input[name='panCardNo']").attr("readonly", false);
			 $("input[name='din']").attr("readonly", false);
			 
			$("#yearEstablishmentId").removeAttr("readonly");
			$("#gstin").removeAttr("readonly");
			$("#cin").removeAttr("readonly");
			$("#companyPanNoId").removeAttr("readonly");
			document.getElementById("moaDoc").disabled = false;
			document.getElementById("registrationAttachedDoc").disabled = false;
			document.getElementById("bodDoc").disabled = false;
			document.getElementById("annexureiaformatId").disabled = false;	
			document.getElementById("addButtonId").disabled = false;    			
			$("#editButton").removeClass("disable_a_href");
			$("#deleteButton").removeClass("disable_a_href"); 
		}); */
</script>
<script type="text/javascript">

/* function IsDirectorName()
{ 
	var directorName =document.getElementById("directorNameId").value;
	
      var letters = /^[A-Za-z]+$/;
      if(directorName.match(letters))
      {
    	  document.getElementById('DirectorName').innerHTML = "";
    	 
      }
      else
      {
    
   		document.getElementById('DirectorName').innerHTML = "DirectorName not allow numbers and special characters.";
   		document.getElementById("directorNameId").focus();
   		return false;
      }
      } */
      
$(function() {
    $('#email').on('keypress', function(e) {
        if (e.which == 32){
            console.log('Space Detected');
            return false;
        }
    });
});

$(function() {
    $('#emailId').on('keypress', function(e) {
        if (e.which == 32){
            console.log('Space Detected');
            return false;
        }
    });
});

function phoneNoValidation()
{
	
	var phoneRegex = /^0[0-9].*$/;
	var phoneno = document.getElementById("phoneNo").value;

	if (phoneno == null || phoneno == '') {
		return true;
	}

	else {
		if (phoneno.length < 11) {
			document.getElementById('phoneNoMsg').innerHTML = "Phone number should be 11 digits long.";
			document.getElementById('phoneNo').focus();
			return false;
		} else {
			document.getElementById('phoneNoMsg').innerHTML = "";
		}
	}

	if (!phoneRegex.test(phoneno)) {
		document.getElementById('phoneNoMsg').innerHTML = "Phone number should start with 0 (zero).";
		document.getElementById("phoneNo").focus();
		return false;
	} else {
		return true;
	}
	}
</script>
<script type="text/javascript">

function emptyCapInvErrMsg()
{
$("#DirectorName").text('');

    }
</script>

</head>
<body class="bottom-bg" onload="getEquity()">
	<%
		ProprietorDetails proprietorDetails = new ProprietorDetails();
	%>
	<section class="inner-header">
      <div class="top-header">
        <div class="container">
          <div class="row">
            <div class="col-sm-6 text-left">
              <span class="top-gov-text">Goverment of Uttar Pradesh</span>
            </div>
            <div class="col-sm-6 text-right">
              <a href="tel:05222238902"><i class="fa fa-phone"></i> 0522-2238902</a> | <a href="mailto:info@udyogbandhu.com"><i class="fa fa-envelope"></i> info@udyogbandhu.com</a>
            </div>
          </div>
        </div>
      </div>
        <!-- Navigation / Navbar / Menu -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
          <div class="container">
          <a class="navbar-brand" href="#"><img src="images/logo.png" class="logo" alt="Logo"></a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
            <ul class="navbar-nav ml-auto mt-2 mt-lg-0">               
              <li class="nav-item">
              <a class="nav-link active" href="http://72.167.225.87/Testing_NMSWP/nmmasters/Entrepreneur_Dashboard.aspx">Home</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="http://udyogbandhu.com/" target="_blank">Invest UP</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="http://niveshmitra.up.nic.in/About.aspx?ID=whyup" target="_blank">Why Invest in UP</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="http://udyogbandhu.com/topics.aspx?mid=Policies" target="_blank">Policies</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="http://udyogbandhu.com/topics.aspx?mid=UdyogBandhu" target="_blank">Contact Us</a>
            </li>

            </ul>
            <div class="gov-logo-group">
              <a href="#"><img src="images/up-logo.png" align="up-Logo"></a>
              <!-- <a href="#"><img src="images/udhogbandhu-logo.png" align="up-Logo"></a> -->
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
				<div class="col-sm-12">
					<div class="form-card">
						<div class="without-wizard-steps">
							<div class="step-links">
								<ul>
									<li><a href="./getIdByTabs11?authoTab=authoTab"	class="filled"><span>1</span>Authorized Signatory Details</a></li>
									<li><a href="#" class="active"><span>2</span>Business Entity Details</a></li>
									
									<li>
									<c:if test="${not empty Pflag}">
									<a href="./getIdByTabs12?projTab=projTab" class="filled"><span>3</span>Project Details</a>
									</c:if>
									<c:if test="${empty Pflag}">
									<a href="./getIdByTabs12?projTab=projTab"><span>3</span>Project Details</a>
									</c:if>
									</li>
									<li>
									 <c:if test="${not empty Iflag}">
									<a href="./getIdByTabs13?investTab=investTab" class="filled"><span>4</span>Investment Details</a>
									</c:if>
									<c:if test="${empty Iflag}">
									<a href="./getIdByTabs13?investTab=investTab"><span>4</span>Investment Details</a>
									</c:if>
									</li>
									<li>
									<c:if test="${not empty PEflag}">
									<a href="./getIdByTabs14?propoTab=propoTab" class="filled"><span>5</span>Proposed Employment Details</a>
									</c:if>
									<c:if test="${empty PEflag}">
									<a href="./getIdByTabs14?propoTab=propoTab"><span>5</span>Proposed Employment Details</a>
									</c:if>
									</li>
								</ul>
							</div>

							<div class="isf-form">
								<form:form modelAttribute="businessEntityDetails" id="myFormB" 
									name="businessEntityAddressForm" method="post" 
									enctype="multipart/form-data">


									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Business Entity Details and
												Address</h3>
										</div>
									</div>
									<%-- <form:hidden path="id" value="${id}" /> --%>
									<div class="row">

										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="businessEntityName">Business Entity Name <span>*</span>
													<a href="javascript:void(0);" class="remove-row"
														data-toggle="tooltip" title=""
														data-original-title="Please enter the name of your Company"><i
														class="fa fa-info-circle text-info"></i></a>
												</form:label>
												<form:input path="businessEntityName" class="form-control"
													id="businessEntityName" readonly="true"></form:input>
												<span id="BEName" class="text-danger"></span>
												<form:errors path="businessEntityName" class="control-label" />
											</div>
										</div>

										<div class="col-md-6 col-lg-4 col-xl-4">
											<spring:bind path="businessEntityType">
												<div class="form-group">
													<form:label path="businessEntityType">Organisation Type<span>*</span>
														<a href="javascript:void(0);" class="remove-row"
															data-toggle="tooltip" title=""
															data-original-title="Fill your organization type in which category you have registered it. e.g. your company is registered under Proprietary OR Partnership OR Private limited company OR Public Limited Company etc. ."><i
															class="fa fa-info-circle text-info"></i></a>
													</form:label>
													<form:select path="businessEntityType" class="form-control"
														id="businessEntityType" name="businessEntityType">
														<form:option value="">Select One</form:option>
														<form:option value="Limited">Limited</form:option>
														<form:option value="Private Limited">Private Limited</form:option>
														<form:option value="Partnership">Partnership</form:option>
														<form:option value="Proprietorship">Proprietorship</form:option>
														<form:option value="Public Enterprise">Public Enterprise</form:option>
														<form:option value="LLP">LLP</form:option>
														<form:option value="Society">Society</form:option>
														<form:option value="Trust">Trust</form:option>
													</form:select>
													<span id="BEType" class="text-danger "></span>
												</div>
											</spring:bind>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="authorisedSignatoryName">Authorized Signatory Name<span>*</span>
												</form:label>
												<form:input path="authorisedSignatoryName"
													class="form-control" id="" name="authorisedSignatoryName"
													autocomplete="off" readonly="true"></form:input>
												<span id="APName" class="text-danger"></span>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="businessDesignation">Designation <span>*</span>
												</form:label>
												<form:input path="businessDesignation" class="form-control"
													id="businessDesignation" name="businessDesignation"
													maxlength="30" readonly="true"></form:input>
												<span id="BD" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="emailId">Email <span>*</span>
													<a href="javascript:void(0);" class="remove-row"
														data-toggle="tooltip" title=""
														data-original-title="Please Enter Company Email ID"><i
														class="fa fa-info-circle text-info"></i></a>
												</form:label>
												<form:input path="emailId" type="email" class="form-control"
													id="emailId" onkeyup="return IsEmailId(event)"
													maxlength="60" name="emailId" autocomplete="off"></form:input>
												<span id="Email" class="text-danger"></span>
												<form:errors path="emailId" class="control-label" />
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="mobileNumber">Mobile No. (+91) <span>*</span>
												</form:label>
												<form:input path="mobileNumber" type="text"
													class="form-control" id="mobileNumberId"
													onkeypress="return IsMobileNoCharacter(event)"
													onkeyup="IsMobileNoNeumeric(event)" name="mobileNumber"
													autocomplete="off" maxlength="10"></form:input>
												<span id="Mobile" class="text-danger"></span>
												<form:errors path="mobileNumber" class="control-label" />

											</div>
										</div>
									</div>


									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<label>Phone No <small>(Landline)</small></label>
												<form:input path="phoneNo" type="text" class="form-control"
													id="phoneNo" name="phoneNo" onblur="phoneNoValidation()"
													
													autocomplete="off" maxlength="11"></form:input>
													<span id="phoneNoMsg" class="text-danger "></span>
											</div>
										</div>

										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="fax">Fax</form:label>
												<form:input path="fax" type="text" class="form-control"
													id="fax" name="fax" maxlength="12"
													oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');"></form:input>
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="businessAddress">Registered Office Address <span>*</span>
												</form:label>
												<form:input path="businessAddress" type="text"
													class="form-control" id="businessAddress" maxlength="200"
													onkeyup="return IsAddress(event)" name="businessAddress"
													autocomplete="off"></form:input>
												<span id="BAddress" class="text-danger"></span>
												<form:errors path="businessAddress" class="control-label" />
											</div>

										</div>
									</div>

									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="businessCountryName">Select Country <span>*</span>
												</form:label>
												<form:select path="businessCountryName" class="form-control"
													id="businessCountryName" name="businessCountryName"
													onchange="IsCountry();">
													<form:option value="">Select One</form:option>
													<form:option value="India">India</form:option>
												</form:select>
												<span id="CountryName" class="text-danger"></span>
												<form:errors path="businessCountryName"
													class="control-label" />
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="businessStateName">Select State/UT <span>*</span>
												</form:label>
												<form:select path="businessStateName" class="form-control"
													name="businessStateName" id="businessStateName"
													onchange=" IsState(); getComboA(this)"  onkeyup="return IsState(event)">
													<form:option value="">Select One</form:option>
													<c:forEach items="${stateDetails}" var="stateDetailsList">
														<form:option value="${stateDetailsList.stateCode}">${stateDetailsList.stateName}</form:option>
													</c:forEach>
												</form:select>
												<span id="StateName" class="text-danger"></span>
												<form:errors path="businessStateName" class="control-label" />
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="businessDistrictName">Select District <span>*</span>
												</form:label>
												<form:select path="businessDistrictName"
													class="form-control" name="businessDistrictName"
													onkeyup="return IsDistrict(event)"
													id="businessDistrictName">
													<%-- 	<form:option value="${businessEntityDetails.businessDistrictName}">${businessEntityDetails.businessDistrictName}</form:option> --%>

												</form:select>
												<span id="DistrictName" class="text-danger"></span>
												<form:errors path="businessDistrictName"
													class="control-label" />
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="PinCode">Pin Code <span>*</span>
												</form:label>
												<form:input path="PinCode" type="text" class="form-control"
													id="businessPinCode" name="businessPinCode"
													autocomplete="off" maxlength="6"
													onkeypress="return IsPinCodeCharacter(event)"
													onkeyup="IsPinCodeNeumeric(event)"></form:input>
												<span id="PinCode" class="text-danger"></span>
												<form:errors path="PinCode" class="control-label" />
											</div>
										</div>
										<!-- <div class="col-md-6 col-lg-8 col-xl-8">
											<div class="form-group text-right mt-1">
												<button type="submit" class="common-btn mt-4">Save</button>
											</div>
										</div> -->
									</div>
									<%-- </form:form>

								<form:form modelAttribute="proprietorDetails"
									name="proprietorDetailsForm"
									onsubmit="return validationProprietorDetailsForm()"
									action="proprietorDetails" method="post"> --%>
									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">
												Fill Director / Partner / Owner / Proprietor Details <a
													href="javascript:void(0);" class="remove-row"
													data-toggle="tooltip" title=""
													data-original-title="Please Enter your Director / Partner / Owner / Proprietor Details of your Company"><i
													class="fa fa-info-circle text-info"></i></a>
											</h3>
										</div>
									</div>
									<form:hidden path="id" value="${id}" />
									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="directorName">Name <span>*</span>
												</form:label>
												<form:input path="directorName" class="form-control"
													id="directorNameId" name="directorName" maxlength="30"
													onblur="emptyCapInvErrMsg()" onkeypress="return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123) || (event.charCode==32)"
													onkeyup="IsDirectorName()"></form:input>
												<span id="DirectorName" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="designation">Designation<span>*</span>
												</form:label>
												<form:input path="designation" class="form-control"
													maxlength="30" id="designationId"
													onkeyup="return Isdesignation(event)"
													onkeypress="return /[a-z��� ]/i.test(event.key)"></form:input>
												<span id="Designation" class="text-danger "></span>
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<input type="hidden" id="equityvalue" value="${equatity}" />

												<form:label path="equity">% Equity <span>*</span>
												</form:label>
												<form:input path="equity" class="form-control"
													onkeypress="return validateFloatKeyPress(this,event);"
													id="equityId" onkeyup="return Isequity(event)"></form:input>
												<span id="Equity" class="text-danger "></span>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="mobileNo">Mobile No. (+91)<span>*</span>
												</form:label>
												<form:input path="mobileNo" type="text" class="form-control"
													id="mobileNoId" name="mobileNo" maxlength="11"
													onkeypress="return IsMobileNoCharacter1(event)"
													onkeyup="IsmobileNumber()"></form:input>
												<span id="MobileNo" class="text-danger "></span>
											</div>
										</div>
										<div class="col-md-6 col-lg-8 col-xl-8">
											<div class="form-group">
												<form:label path="proprietorDetailsaddress">Address<span>*</span>
												</form:label>
												<form:input path="proprietorDetailsaddress" maxlength="200"
													onkeyup="return IsproprietorDetailsaddress(event)"
													class="form-control" id="proprietorDetailsaddressId"></form:input>
												<span id="ProprietorDetailsAddress" class="text-danger "></span>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="email">Email<span>*</span>
												</form:label>
												<form:input path="email" type="email" class="form-control"
													maxlength="60" id="email"></form:input>
												<span id="Email2" class="text-danger "></span>
											</div>
										</div>

										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<div class="gender-label">
													<label>Select Gender <span>*</span></label>
												</div>
												<div
													class="custom-control custom-radio custom-control-inline">
													<c:set var="gender" value="${gender}" />
													<input type="radio" class="custom-control-input"
														onkeyup="return IsGender(event)" onclick="return IsGenderclick(event)" id="MaleOpt2"
														name="gender" value="Male"
														<c:if test="${gender=='Male'}">checked</c:if>> <label
														class="custom-control-label" for="MaleOpt2">Male</label>
												</div>
												<div
													class="custom-control custom-radio custom-control-inline">
													<input type="radio" class="custom-control-input"
														onkeyup="return IsGender(event)" onclick="return IsGenderclick(event)" id="FemaleOpt2"
														name="gender" value="Female"
														<c:if test="${gender=='Female'}">checked</c:if>> <label
														class="custom-control-label" for="FemaleOpt2">Female</label>
												</div>

												<span id="Gender" class="text-danger d-block"></span>
											</div>
										</div>

										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="category">Category<span>*</span>
												</form:label>
												<form:select class="form-control" path="category"
													onchange="Iscategory();" onkeyup="Iscategory();" id="category"
													name="category">
													<form:option value="">Select One</form:option>
													<form:option value="General">General</form:option>
													<form:option value="SC">SC</form:option>
													<form:option value="ST">ST</form:option>
													<form:option value="OBC">OBC</form:option>
													<%-- <form:option value="Divyang">Divyang</form:option> --%>
												</form:select>
												<span id="Category" class="text-danger "></span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<div class="gender-label">
													<label>Does the person fall under divyang category?
														<span>*</span>
													</label>
												</div>
												<div
													class="custom-control custom-radio custom-control-inline">
													<c:set var="divyang" value="${divyang}" />
													<input type="radio" class="custom-control-input"
														onkeyup="return Isdivyang(event)" onclick="return Isdivyangclick(event)" id="DivyangYes"
														name="div_Status" value="Yes"
														<c:if test="${divyang=='Yes'}">checked</c:if>> <label
														class="custom-control-label" for="DivyangYes">Yes</label>
												</div>
												<div
													class="custom-control custom-radio custom-control-inline">
													<input type="radio" class="custom-control-input"
														onkeyup="return Isdivyang(event)" onclick="return Isdivyangclick(event)" id="DivyangNo"
														name="div_Status" value="No"
														<c:if test="${divyang=='No'}">checked</c:if>> <label
														class="custom-control-label" for="DivyangNo">No</label>
												</div>
												<span id="Div_Status" class="text-danger d-block"></span>
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="panCardNo">PAN Card Number <span>*</span>
												</form:label>
												<form:input path="panCardNo" type="text"
													onkeyup="return IsPAND(event)" class="form-control"
													id="panCardNOId" maxlength="10" name="panCardNo"
													autocomplete="off"
													oninput="this.value = this.value.toUpperCase()"></form:input>
												<span id="panCardNO" class="text-danger"></span>
												<form:errors path="panCardNo" class="control-label" />
											</div>
										</div>
              
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="din">DIN <span></span>
													<a href="javascript:void(0);" class="remove-row"
														data-toggle="tooltip" title=""
														data-original-title="Please Enter your Director Identification Number"><i
														class="fa fa-info-circle text-info"></i></a>
												</form:label>
												<form:input path="din" type="text" class="form-control"
													id="din" maxlength="8" name="din"
													onkeyup="return IsdinId()"
													oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');"></form:input>
												<span id="Din" class="text-danger"></span>
											</div>
										</div>

										<div class="col-md-12 col-lg-12 col-xl-12">
											<div class="form-group text-right mb-5">
												<c:if test="${empty edit}">
													<button type="submit" class="common-btn mt-4"
														formaction="proprietorDetails"
														onclick="return validationProprietorDetailsForm()" id="addButtonId">
														Add</button>
												</c:if>
												<c:if test="${not empty edit}">
													<button type="submit" class="common-btn mt-4"
														formaction="./proprietorDetails?newpropId=${list.propId}"
														onclick="return validationProprietorDetailsForm()">
														Save</button>
												</c:if>

											</div>
										</div>
									</div>


									<div class="row" id="table-wrapper">
										<div class="col-sm-12" id="table-scroll">
											<div class="table-responsive">
												<table
													class="table table-stripped table-bordered directors-table"
													id="customFields" tabindex='1'>
													<c:if test="${ not empty ProprietorDetailsList}">

														<thead>
															<tr>
															<th>Prop ID</th>
																<th>Director Name</th>
																<th>Designation</th>
																<th>Address</th>
																<th>Mobile No.</th>
																<th>% Equity</th>
																<th>Email</th>
																<th>Gender</th>
																<th>Category</th>
																<th>Divyang Category</th>
																<th>PAN Number</th>
																<th>DIN</th>
																<th>Action</th>
															</tr>
														</thead>
													</c:if>
													<tbody id="editButton">
														<c:forEach var="list" items="${ProprietorDetailsList}"
															varStatus="counter">
															<tr>
															<td>${list.propId}</td>
																<td>${list.directorName}</td>
																<td>${list.designation}</td>
																<td>${list.proprietorDetailsaddress}</td>
																<td>${list.mobileNo}</td>
																<td><input type="text" value="${list.equity}"
																	disabled="disabled" style="width: 70px;"></td>
																<td>${list.email}</td>
																<td>${list.gender}</td>
																<td>${list.category}</td>
																<td>${list.div_Status}</td>
																<td>${list.panCardNo}</td>
																<td>${list.din}</td>
																<td class="text-center"><a
																	href="./editProprietorDetails?editProprietorDetailsRecord=${list.propId}"
																	onclick="return confirm('Are you sure you want to edit Proprietor Record?')">
																		<i class="fa fa-edit text-info"></i>
																</a> <a href="./removeList?removeItem=${list.propId}"
																	class="ref-rmv"
																	onclick="return confirm('Are you sure want to remove Proprietor Details?')"><i
																		class="fa fa-trash text-danger"></i></a></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									<%-- 	</form:form>



								<form:form modelAttribute="businessEntityDetails"
									name="businessEntityForm"
									onsubmit="return validationFinalDetails()"
									action="businessDetails" method="post"> --%>
									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading"> Documents and
												Certificates</h3>
										</div>
									</div>
									<form:hidden path="id" value="${id}" />
									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="yearEstablishment">Date of Incorporation of Company<span>*</span>
												</form:label>
												<form:input path="yearEstablishment" type="date"
													class="form-control" id="yearEstablishmentId"
													onkeyup="return IsyearEstablishment(event)"
													autocomplete="off" name="yearEstablishment"></form:input>
												<span id="YearEstablishment" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="gstin">GSTIN <span>*</span>
												</form:label>
												<form:input path="gstin" type="text" class="form-control"
													id="gstin" autocomplete="off" name="gstin" maxlength="15"
													onkeyup="return IsGSTIN(event)"
													oninput="this.value = this.value.toUpperCase()"></form:input>
												<span id="Gstin" class="text-danger"></span>
											</div>
										</div>

										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="cin">CIN <span>*</span>
												</form:label>
												<form:input path="cin" type="text" class="form-control"
													id="cin" autocomplete="off" name="cin" maxlength="21"
													onkeyup="return IsCIN(event)"
													oninput="this.value = this.value.toUpperCase()"></form:input>
												<span id="Cin" class="text-danger"></span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="companyPanNo">Company PAN No. <span>*</span>
												</form:label>
												<form:input path="companyPanNo" type="text"
													class="form-control" id="companyPanNoId" autocomplete="off"
													name="companyPanNo" maxlength="10"
													onkeyup="return IsPAN(event)"
													oninput="this.value = this.value.toUpperCase()"></form:input>
												<span id="CPanNo" class="text-danger"></span>
												<form:errors path="companyPanNo" class="control-label" />
											</div>
										</div>
										<div class="col-sm-8">
											<div class="form-group">
												<form:label path="moaDocFname">MoA / Partnership Deed Attachment <span>*</span>
													<small>(In PDF format less than 2MB)</small>
													<img src="images/pdf-icon.png" class="pdf-icon"
														alt="pdf-icon">
												</form:label>
												<div class="custom-file">
													<input type="file" onkeyup="return IsMAODoc(event)"
														onchange="ValidateMaoSize(this)" class="custom-file-input"
														id="moaDoc" name="moaDocFname"></input>
													<form:label path="moaDocFname" class="custom-file-label"
														for="moaDocFname" id="choosefileMoDoc">Choose file</form:label>
												</div>
												<span id="MaoDoc" class="text-danger"></span>
												<form:errors path="moaDoc" class="control-label" />
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<form:label path="regisAttacDocFname"> Certificate Of Incorporation/Registration <span>*</span>
													<small>(In PDF format less than 2MB)</small>
													<img src="images/pdf-icon.png" class="pdf-icon"
														alt="pdf-icon">
												</form:label>
												<div class="custom-file">
													<input type="file"
														onkeyup="return IsregistrationAttachedDoc(event)"
														onchange="ValidateregistrationAttachedSize(this)"
														class="custom-file-input" id="registrationAttachedDoc"
														name="regisAttacDocFname"></input>
													<form:label path="regisAttacDocFname"
														class="custom-file-label" for="regisAttacDocFname"
														id="choosefileRCDoc">Choose file</form:label>
												</div>
												<span id="CIRA" class="text-danger"></span>

											</div>
										</div>

										<div class="col-sm-12">
											<div class="form-group">
												<form:label path="bodDocFname">Certified copy of the resolution of the Board of Directors of the Company <span>*</span>
													<small>(In PDF format less than 2MB)</small>
													<img src="images/pdf-icon.png" class="pdf-icon"
														alt="pdf-icon">
													<a href="javascript:void(0);" class="remove-row"
														data-toggle="tooltip" title=""
														data-original-title="Certified copy of the resolution of the Board of Directors / Competent Authority of
														 Industrial Undertaking authorizing the deponent as signatory (not below the director rank of the company)"><i
														class="fa fa-info-circle text-info"></i></a>
												</form:label>
												<div class="custom-file">
													<input type="file" onkeyup="return IsbodDoc(event)"
														onchange="ValidatebodDocSize(this)"
														class="custom-file-input" id="bodDoc" name="bodDocFname"></input>
													<form:label path="bodDocFname" class="custom-file-label"
														for="bodDocFname" id="choosefilebodDoc">Choose file</form:label>
												</div>
												<span id="BodDoc" class="text-danger"></span>
											</div>
										</div>

										<%-- <div class="col-sm-12">
											<div class="form-group">
												<form:label path="indusAffidaDocFname">Competent Authority of the Industrial Undertaking authorizing the deponent be provided along with the affidavit. <span>*</span>
													<br>
													<small>(In PDF format less than 2MB)</small>
													<img src="images/pdf-icon.png" class="pdf-icon"
														alt="pdf-icon">
												</form:label>
												<div class="custom-file">
													<input type="file"
														onkeyup="return IsindusAffidaviteDoc(event)"
														onchange="ValidateIsindusAffidaviteSize(this)"
														class="custom-file-input" id="indusAffidaviteDoc"
														name="indusAffidaDocFname"></input>
													<form:label path="indusAffidaDocFname"
														class="custom-file-label" for="indusAffidaDocFname"
														id="choosefileindusAffidaviteDoc">Choose file</form:label>
												</div>
												<span id="IndusAffidaviteDoc" class="text-danger"></span>
											</div>
										</div> --%>
										<div class="col-sm-12 mt-3 mb-3">
											<div class="table-responsive">
												<table class="table table-bordered">
													<tbody>
														<tr>
															<td width="40%">Notarized undertaking (as per format placed at
																Annexure I-A) on Stamp Paper of Rs. 10 <span
																class="text-danger">*</span> <small>(In PDF
																	format less than 2MB)</small> <img src="images/pdf-icon.png"
																class="pdf-icon" alt="pdf-icon"> <br> <small><a
																	href="./downloadFile/doc">Download Template</a></small>
															</td>
															<td>
															
															
															<div class="custom-file">
													<input type="file" onkeyup="return IsannexureiaformatDoc(event)"
														onchange="ValidateIsannexureiaformatSize(this)"
														class="custom-file-input" id="annexureiaformatId" name="annexureiaformat"></input>
													<form:label path="annexureiaformat" class="custom-file-label"
														for="annexureiaformatId" id="annexureiaformatDoc">Choose file</form:label>
												</div>
															
																<%-- <div class="custom-file">
																	<input type="file" class="custom-file-input"
																		onkeyup="return IsannexureiaformatDoc(event)"
																		onchange="ValidateIsannexureiaformatSize(this)"
																		id="annexureiaformatId" name="annexureiaformat">
																		
																		<form:label path="annexureiaformat" class="custom-file-label"
														for="annexureiaformat" id="annexureiaformatDoc">Choose file</form:label>
																		
																	<!-- <label class="custom-file-label" for="customFile10"
																		id="annexureiaformatDoc">Choose file</label> -->
																</div> --%> <span id="AnnexureiaformatId" class="text-danger"></span>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									<hr class="mt-4">


									<div class="row">
										<div class="col-sm-5">
											<a href="./editApplicantForm"
												onclick="return confirm('Are you sure you want to go to Authorized Signatory Details?')"
												class="common-default-btn mt-3">Previous</a>
										</div>
										<div class="col-sm-7 text-right">
										 <c:if test="${not empty bussinessId}">
											<button type="button" class="common-btn mt-3" onclick="editBussinessDetails()"><i class="fa fa-edit"></i>Edit</button>
											</c:if>
											<button type="submit" class="common-btn mt-4"
												formaction="businessDetails"
												onclick="return validationBusinessEntityAddress()">Save
												and Next</button>
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

</body>
</html>
<%@ include file="footer.jsp"%>