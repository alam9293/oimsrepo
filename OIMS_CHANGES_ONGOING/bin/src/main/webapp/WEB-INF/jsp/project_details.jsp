<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="com.webapp.ims.model.ExistingProjectDetails"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>project Details</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">

<style type="text/css">
error {
	color: red;
}
</style>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="js/custom.js"></script>
<script src="js/projectDetails.js"></script>

<style>
.hide {
	display: none;
}

.disable_a_href {
	pointer-events: none;
}
</style>

<script type="text/javascript">

window.onload = function() {
	
/* 	 if('${regiOrLicense}'=='EncloseUAM')
		 {
		 $('input:radio[name="regiOrLicense"][value="EncloseUAM"]').prop('checked', true);
	       }
	 else
		 {
		 $('input:radio[name="regiOrLicense"][value="IEMcopy"]').prop('checked', true);
		 } */


		 
	 if('${natureOfProject}'==='' )
	 {
	  document.getElementById('hideById').style.display ='none';
	  document.getElementById('hideByIdNew').style.display ='none';
	  var element = document.getElementById("hideByRowId1");
	  element.classList.add("hide");	  
	  var element = document.getElementById("hideByRowId2");
	  element.classList.add("hide");
	  var element = document.getElementById("hideByRowId3");
	  element.classList.add("hide");
	  document.getElementById('hideByRowId4').style.display ='none';
	  document.getElementById('natureOfProjectDiv').style.display ='none';
	  			  
	 }
	 if('${natureOfProject}'=='NewProject')
	 {
		 document.getElementById('hideByIdNew').style.display ='block';
		  document.getElementById('hideById').style.display ='none';
		  var element = document.getElementById("hideByRowId1");
		  element.classList.add("hide");	  
		  var element = document.getElementById("hideByRowId2");
		  element.classList.add("hide");
		  var element = document.getElementById("hideByRowId3");
		  element.classList.remove("hide");		  
		  document.getElementById('hideByRowId4').style.display ='block';		 
		  document.getElementById('natureOfProjectDiv').style.display ='none';    
     }
	 if('${natureOfProject}'=='ExistingProject')
	 {
		 
		 document.getElementById('hideById').style.display = 'block';
		 document.getElementById('hideByIdNew').style.display ='none'; 
	      var element = document.getElementById("hideByRowId1");
		  element.classList.remove("hide");	  
		  var element = document.getElementById("hideByRowId2");
		  element.classList.remove("hide");		  
		  var element = document.getElementById("hideByRowId3");
		  element.classList.remove("hide");
		  document.getElementById('hideByRowId4').style.display ='block';
		  document.getElementById('natureOfProjectDiv').style.display ='block';
		  if('${expansion}'=='Expansion')
			 {
		  $('input:checkbox[name="expansion"][value="Expansion"]').prop('checked', true);
			 } 
		  if('${diversification}'=='Diversification')
			 {
			  $('input:checkbox[name="diversification"][value="Diversification"]').prop('checked', true);
			 }    
     }

	
	};

	function fetchdata(){
		$(document).ready(function() {
			$(document).on("change", ".form-control", function() {
				//$('#appAadharNo').rules('remove');
				//$('.appAadharNo').rules('remove');
				 document.getElementById("mobileNo").disableValidation = true;
				 document.getElementById("fullAddress").disableValidation = true;
				 document.getElementById("districtName").disableValidation = true;
				 document.getElementById("pinCode").disableValidation = true;
				 document.getElementById("regiOrLicenseFile1").disableValidation = true;

				 	document.getElementById("regiOrLicenseIEM").disableValidation = true; 	
				  	//document.getElementById("regiOrLicenseFile").disableValidation = true;
				  	document.getElementById("natureOfProjectId").disableValidation = true;
				  	document.getElementById("expansionId").disableValidation = true;
				  	document.getElementById("diversificationId").disableValidation = true;
				  	document.getElementById("existingProducts").disableValidation = true;
				  	document.getElementById("existingInstalledCapacity").disableValidation = true;
				  	document.getElementById("proposedProducts").disableValidation = true;
				  	document.getElementById("existingGrossBlock").disableValidation = true;
				  	document.getElementById("proposedInstalledCapacity").disableValidation = true;
				  	document.getElementById("encloseDetailProReportFileName").disableValidation = true;
				  	document.getElementById("caCertificate").disableValidation = true;
				  	document.getElementById("certifiedList").disableValidation = true;
				 
				document.getElementById("myFormP").action = "autoprojectDetails";
				document.getElementById('myFormP').submit();
			});
		});
		}

		
	
	$(function() {
	    
	       if ("${projectDetailsForm.projReportbase64File}" == null
	   			|| "${projectDetailsForm.projReportbase64File}" == '') {

	   	} else {
	   		document.getElementById('encloseDetailProReportFileName1').innerHTML = '${projectDetailsForm.enclDetProRepFileName}';
	   	}

	   	if ("${projectDetailsForm.caReportbase64File}" == null
	   			|| "${projectDetailsForm.caReportbase64File}" == '') {

	   	} else {
	   		document.getElementById('caCertificate1').innerHTML = '${projectDetailsForm.caCertificateFileName}';
	   	}



	   	if ("${projectDetailsForm.charEngbase64File}" == null
	   			|| "${projectDetailsForm.charEngbase64File}" == '') {

	   	} else {
	   		document.getElementById('certifiedList1').innerHTML = '${projectDetailsForm.charatEngFileName}';
	   	}
	    




	       
	       
	     if('${projectId}'!=null && '${projectId}'!='')
	  	  {	       		 
		  	 $("input[name='mobileNo']").attr("readonly", true);
		  	 $("input[name='designation']").attr("readonly", true);
		  	 $("input[name='phoneNo']").attr("readonly", true);
		  	 $("input[name='webSiteName']").attr("readonly", true);		  	 
		  	/*  document.getElementById("regiOrLicense").disabled = true;
		  	 document.getElementById("regiOrLicenseIEM").disabled = true;  	
		  	 document.getElementById("regiOrLicenseFile").disabled = true; */
		  	 $('#natureOfProjectId').attr('disabled', 'disabled');		  	
		  	 $("input[name='expansion']").attr('disabled', 'disabled');
		  	 $("input[name='diversification']").attr('disabled', 'disabled');		  	 
		  	 		  	 
		  	$("input[name='ExistingProducts']").attr("readonly", true);
		  	 $("input[name='existingInstalledCapacity']").attr("readonly", true);
		  	  $("input[name='proposedProducts']").attr("readonly", true);
		  	  $("input[name='proposedInstalledCapacity']").attr("readonly", true);
		  	 $("input[name='existingGrossBlock']").attr("readonly", true);
		  	 $("input[name='proposedGrossBlock']").attr("readonly", true);
		  	document.getElementById("eicUnit").disabled = true;
		  	document.getElementById("picUnit").disabled = true; 
		   	 document.getElementById("encloseDetailProReportFileName").disabled = true;
		  	 document.getElementById("caCertificate").disabled = true;  	
		  	 document.getElementById("certifiedList").disabled = true;
		  	document.getElementById("existProjAdd").disabled = true;

		  	$("#existProjData").addClass("disable_a_href");
		  			  	 
	       }
	   else
	  	 {	  	 
		   $("#mobileNo").removeAttr("readonly");
	  		$("#designation").removeAttr("readonly");
	  		$("#webSiteName").removeAttr("readonly");
	  		$("#pinCode").removeAttr("readonly");
	  		/* document.getElementById("regiOrLicense").disabled = false;
		  	document.getElementById("regiOrLicenseIEM").disabled = false;  	
		  	document.getElementById("regiOrLicenseFile").disabled = false; */
		  	document.getElementById("natureOfProjectId").disabled = false;
		  	document.getElementById("expansionId").disabled = false;
		  	document.getElementById("diversificationId").disabled = false;

		  	
		   $("#existingProducts").removeAttr("readonly");
	  		$("#existingInstalledCapacity").removeAttr("readonly");
	  		$("#proposedProducts").removeAttr("readonly");
	  		$("#proposedInstalledCapacity").removeAttr("readonly");
	  		$("#existingGrossBlock").removeAttr("readonly");
	  		$("#proposedGrossBlock").removeAttr("readonly"); 
	  		document.getElementById("eicUnit").disabled = false;
		  	document.getElementById("picUnit").disabled = false; 
		   	document.getElementById("encloseDetailProReportFileName").disabled = false;
		  	document.getElementById("caCertificate").disabled = false;  	
		  	document.getElementById("certifiedList").disabled = false;
		  	document.getElementById("existProjAdd").disabled = false;
		  	 $("#existProjData").removeClass("disable_a_href");
		  	
	  	 }		      
	       });




	function enableExistProjDetails(){
		 $("#existingProducts").removeAttr("readonly");
	  		$("#existingInstalledCapacity").removeAttr("readonly");
	  		$("#proposedProducts").removeAttr("readonly");
	  		$("#proposedInstalledCapacity").removeAttr("readonly");
	  		$("#existingGrossBlock").removeAttr("readonly");
	  		$("#proposedGrossBlock").removeAttr("readonly"); 
	  		document.getElementById("eicUnit").disabled = false;
		  	document.getElementById("picUnit").disabled = false;
		
		}


	

	function propGrossBlockAmt(){
		var existGrossBlock= parseInt(document.getElementById("existingGrossBlock").value);
		var propGrossBlock= parseInt(document.getElementById("proposedGrossBlock").value);

		var reqGrossBlock=(existGrossBlock*0.25);
		
		if(propGrossBlock<=reqGrossBlock)
			{
			$('#ProposedGrossBlock')
			.text("Proposed Gross Block should be greater than the 25% of Existing Gross Block.");
			document.getElementById('proposedGrossBlock').focus();
			return false;

			}
			else {
				$('#ProposedGrossBlock').text("");
				}
			return true;
		
	}

    

function showhideMultipleDivs()
{
	var natureOfProjectId=document.getElementById("natureOfProjectId").value;

	
	if('${id}'==''){
	if(natureOfProjectId==='NewProject')
		{		  
		  document.getElementById('hideById').style.display ='none';
		  document.getElementById('hideByIdNew').style.display ='block';
		  var element = document.getElementById("hideByRowId1");
		  element.classList.add("hide");	  
		  var element = document.getElementById("hideByRowId2");
		  element.classList.add("hide");
		  var element = document.getElementById("hideByRowId3");
		  element.classList.remove("hide");		  
		  document.getElementById('hideByRowId4').style.display ='block';		  
		  document.getElementById('natureOfProjectDiv').style.display ='none';		  
		  document.getElementById('encloseDetailProReportFileName').innerHTML = 'Choose file';
      	  document.getElementById('encloseDetailProReportFileName1').innerHTML = '';	        	 
      	  document.getElementById("encloseDetailProReportFileName").value = null;
      	  document.getElementById('caCertificate').innerHTML = 'Choose file';
    	  document.getElementById('caCertificate1').innerHTML = '';	        	 
    	  document.getElementById("caCertificate").value = null;
    	  document.getElementById('certifiedList').innerHTML = 'Choose file';
      	  document.getElementById('certifiedList1').innerHTML = '';	        	 
      	  document.getElementById("certifiedList").value = null;
      	  document.getElementById('NatureOfProjectId').innerHTML="";
      	  document.getElementById("existingProducts").value = "";
		  document.getElementById("existingInstalledCapacity").value = "";
		  document.getElementById("proposedProducts").value = "";
		  document.getElementById("proposedInstalledCapacity").value = "";
		  document.getElementById("existingGrossBlock").value = "";
		  document.getElementById("proposedGrossBlock").value = "";
		  $("#expansionId").prop('checked', false);
		  $("#diversificationId").prop('checked', false);
		  
		}
	else if(natureOfProjectId==='ExistingProject')
		{
		
		document.getElementById('hideById').style.display ='none';
		document.getElementById('hideByIdNew').style.display ='none';
		  var element = document.getElementById("hideByRowId1");
		  element.classList.add("hide");	  
		  var element = document.getElementById("hideByRowId2");
		  element.classList.add("hide");		  
		  var element = document.getElementById("hideByRowId3");
		  element.classList.add("hide");
		  document.getElementById('hideByRowId4').style.display ='none';
		  document.getElementById('natureOfProjectDiv').style.display ='block';

		  document.getElementById("existingProducts").value = "";
		  document.getElementById("existingInstalledCapacity").value = "";
		  document.getElementById("proposedProducts").value = "";
		  document.getElementById("proposedInstalledCapacity").value = "";
		  document.getElementById("existingGrossBlock").value = "";
		  document.getElementById("proposedGrossBlock").value = "";
		  $("#expansionId").prop('checked', false);
		  $("#diversificationId").prop('checked', false);

		  		  
		  document.getElementById('encloseDetailProReportFileName').innerHTML = 'Choose file';
    	  document.getElementById('encloseDetailProReportFileName1').innerHTML = '';	        	 
    	  document.getElementById("encloseDetailProReportFileName").value = null;

    	  document.getElementById('caCertificate').innerHTML = 'Choose file';
  	  document.getElementById('caCertificate1').innerHTML = '';	        	 
  	  document.getElementById("caCertificate").value = null;

  	  document.getElementById('certifiedList').innerHTML = 'Choose file';
    	  document.getElementById('certifiedList1').innerHTML = '';	        	 
    	  document.getElementById("certifiedList").value = null;
	      	
		}
	else
		{
		  document.getElementById('hideById').style.display ='none';
		  document.getElementById('hideByIdNew').style.display ='none';
		  var element = document.getElementById("hideByRowId1");
		  element.classList.add("hide");	  
		  var element = document.getElementById("hideByRowId2");
		  element.classList.add("hide");		  
		  var element = document.getElementById("hideByRowId3");
		  element.classList.add("hide");
		  document.getElementById('hideByRowId4').style.display ='none';
		  document.getElementById('natureOfProjectDiv').style.display ='none';
		  

		  document.getElementById("existingProducts").value = "";
		  document.getElementById("existingInstalledCapacity").value = "";
		  document.getElementById("proposedProducts").value = "";
		  document.getElementById("proposedInstalledCapacity").value = "";
		  document.getElementById("existingGrossBlock").value = "";
		  document.getElementById("proposedGrossBlock").value = "";
		  $("#expansionId").prop('checked', false);
		  $("#diversificationId").prop('checked', false);

		  		  
		  document.getElementById('encloseDetailProReportFileName').innerHTML = 'Choose file';
      	  document.getElementById('encloseDetailProReportFileName1').innerHTML = '';	        	 
      	  document.getElementById("encloseDetailProReportFileName").value = null;

      	  document.getElementById('caCertificate').innerHTML = 'Choose file';
    	  document.getElementById('caCertificate1').innerHTML = '';	        	 
    	  document.getElementById("caCertificate").value = null;

    	  document.getElementById('certifiedList').innerHTML = 'Choose file';
      	  document.getElementById('certifiedList1').innerHTML = '';	        	 
      	  document.getElementById("certifiedList").value = null;
		}
	}
	else
		{
		if(natureOfProjectId==='NewProject')
		{		  
		  document.getElementById('hideById').style.display ='none';
		  document.getElementById('hideByIdNew').style.display ='block';
		  var element = document.getElementById("hideByRowId1");
		  element.classList.add("hide");	  
		  var element = document.getElementById("hideByRowId2");
		  element.classList.add("hide");
		  var element = document.getElementById("hideByRowId3");
		  element.classList.remove("hide");		  
		  document.getElementById('hideByRowId4').style.display ='block';		  
		  document.getElementById('natureOfProjectDiv').style.display ='none';
		  document.getElementById('NatureOfProjectId').innerHTML="";		 
      	document.getElementById('EncloseDetailProReportFileName').innerHTML="";
		}
	else if(natureOfProjectId==='ExistingProject')
		{
		  document.getElementById('hideByIdNew').style.display ='none';
		  document.getElementById('hideById').style.display ='block';
		  var element = document.getElementById("hideByRowId1");
		  element.classList.remove("hide");	  
		  var element = document.getElementById("hideByRowId2");
		  element.classList.remove("hide");
		  var element = document.getElementById("hideByRowId3");
		  element.classList.remove("hide");		  
		  document.getElementById('hideByRowId4').style.display ='block';			  
	      document.getElementById('natureOfProjectDiv').style.display ='block';			 
		  document.getElementById('NatureOfProject').innerHTML = "";			  
	      document.getElementById('NatureOfProjectId').innerHTML="";	      
	      document.getElementById('EncloseDetailProReportFileName').innerHTML="";
	      	
		}
	else
		{
		  document.getElementById('hideById').style.display ='none';
		  document.getElementById('hideByIdNew').style.display ='none';
		  var element = document.getElementById("hideByRowId1");
		  element.classList.add("hide");	  
		  var element = document.getElementById("hideByRowId2");
		  element.classList.add("hide");		  
		  var element = document.getElementById("hideByRowId3");
		  element.classList.add("hide");
		  document.getElementById('hideByRowId4').style.display ='none';
		  document.getElementById('natureOfProjectDiv').style.display ='none';
		  document.getElementById('EncloseDetailProReportFileName').innerHTML="";		 
		}
		}
	document.getElementById('encloseDetailProReportFileName1').innerHTML='Choose file';
	document.getElementById('encloseDetailProReportFileName').value="";
	document.getElementById('certifiedList1').innerHTML='Choose file';
	document.getElementById('certifiedList').value="";
	document.getElementById('caCertificate1').innerHTML='Choose file';
	document.getElementById('caCertificate').value="";
 }

function hideMultipleDivs()
{
	var expansion = $("input:checkbox[name=expansion]:checked").val();
	var diversification = $("input:checkbox[name=diversification]:checked").val();
	if('${id}'==''){
	if((expansion!=null && expansion!='')||(diversification!=null && diversification!=''))
	{
	   document.getElementById('hideById').style.display = 'block';	  
	   var element = document.getElementById("hideByRowId1");
		  element.classList.remove("hide");	  
		  var element = document.getElementById("hideByRowId2");
		  element.classList.remove("hide");		  
		  var element = document.getElementById("hideByRowId3");
		  element.classList.remove("hide");
		  document.getElementById('hideByRowId4').style.display ='block';
		  document.getElementById('NatureOfProject').innerHTML = "";
	}
	else
		{
		document.getElementById('hideById').style.display ='none';
		  var element = document.getElementById("hideByRowId1");
		  element.classList.add("hide");	  
		  var element = document.getElementById("hideByRowId2");
		  element.classList.add("hide");		  
		  var element = document.getElementById("hideByRowId3");
		  element.classList.add("hide");
		  document.getElementById('hideByRowId4').style.display ='none';		  		  
		  document.getElementById('encloseDetailProReportFileName').innerHTML = 'Choose file';
    	  document.getElementById('encloseDetailProReportFileName1').innerHTML = '';	        	 
    	  document.getElementById("encloseDetailProReportFileName").value = null;

    	  document.getElementById('caCertificate').innerHTML = 'Choose file';
  	      document.getElementById('caCertificate1').innerHTML = '';	        	 
  	      document.getElementById("caCertificate").value = null;
  	     document.getElementById('NatureOfProject').innerHTML = "";
  	      document.getElementById('certifiedList').innerHTML = 'Choose file';
    	  document.getElementById('certifiedList1').innerHTML = '';	        	 
    	  document.getElementById("certifiedList").value = null;
		}
	}
	else
		{
		if((expansion!=null && expansion!='')||(diversification!=null && diversification!=''))
		{
		   document.getElementById('hideById').style.display = 'block';	  
		   var element = document.getElementById("hideByRowId1");
			  element.classList.remove("hide");	  
			  var element = document.getElementById("hideByRowId2");
			  element.classList.remove("hide");		  
			  var element = document.getElementById("hideByRowId3");
			  element.classList.remove("hide");
			  document.getElementById('hideByRowId4').style.display ='block';
			  document.getElementById('NatureOfProject').innerHTML = "";
		}
		else
			{
			 document.getElementById('hideById').style.display ='none';
			  var element = document.getElementById("hideByRowId1");
			  element.classList.add("hide");	  
			  var element = document.getElementById("hideByRowId2");
			  element.classList.add("hide");		  
			  var element = document.getElementById("hideByRowId3");
			  element.classList.add("hide");
			  document.getElementById('hideByRowId4').style.display ='none';	    	  
	  	      document.getElementById('NatureOfProject').innerHTML = "";
	  	      
			}
		}
	}
function projectDetailsValidation()
	 {		 

        var contactPersonName=document.getElementById("contactPersonName").value;		
		if (contactPersonName == null || contactPersonName == '') {				
			document.getElementById('ContactPersonName').innerHTML = "Contact Person is Required";	
			document.getElementById('contactPersonName').focus();		
			return false;
		}  
		else
			{
			 document.getElementById('ContactPersonName').innerHTML="";
			}
            
        
       var projectDescription=document.getElementById("projectDescription").value;		
		if (projectDescription == null || projectDescription == '') {
		    document.getElementById('ProjectDescription').innerHTML = "Project Name is Required";
			document.getElementById('projectDescription').focus();			
			return false;
			}
		else
			{
			 document.getElementById('ProjectDescription').innerHTML="";
			}
		
       
		
      var mobileNo=document.getElementById("mobileNo").value;		
		if (mobileNo == null || mobileNo == '') {
			document.getElementById('MobileNo').innerHTML = "Mobile Number is Required";
			document.getElementById('mobileNo').focus();			
			return false;
		}
		else{
			document.getElementById('MobileNo').innerHTML="";
		}
		
		if (/^\d{10}$/.test(mobileNo))
			{
			document.getElementById('MobileNo').innerHTML="";
			}
		else {
			document.getElementById('MobileNo').innerHTML  = "Mobile Number must be 10 digit.";
			document.getElementById('MobileNo').focus();
			return false
		}
		
	   var fullAddress=document.getElementById("fullAddress").value; 	 
		if (fullAddress == null || fullAddress == '') {
			document.getElementById('FullAddress').innerHTML = "Full Address is Required";
			document.getElementById('fullAddress').focus();			
			return false;
		   } 
		
		
		var districtName=document.getElementById("districtName").value;
		if (districtName == null || districtName == '') {
			document.getElementById('DistrictName').innerHTML = "District Name is Required.";
			document.getElementById('districtName').focus();				
			return false;
		} 
		else{
			document.getElementById('DistrictName').innerHTML="";
		}
		
		var pinCode=document.getElementById("pinCode").value;
		if (pinCode == null || pinCode == '') {
			document.getElementById('PinCode').innerHTML = "Pin Code is Required.";
			document.getElementById('pinCode').focus();			
			return false;
		}	
		else
			{
			 document.getElementById('PinCode').innerHTML="";
			}
		
		
		
		
     /*  var regiOrLicenseFile1=document.getElementById("regiOrLicenseFile1").innerHTML;    
		if (regiOrLicenseFile1 == null || regiOrLicenseFile1 == '' || regiOrLicenseFile1=='Choose file') {
			document.getElementById('RegiOrLicenseFile').innerHTML = "Enclose UAM or IEM Report is Required.";
			document.getElementById('regiOrLicenseFile').focus();
			return false;
		}
		
		var regiOrLicenseFile=document.getElementById("regiOrLicenseFile").value;		
		if(regiOrLicenseFile!=null && regiOrLicenseFile!='')
			{
			var ext = regiOrLicenseFile.split('.').pop();
			if (ext == "pdf" || ext == "PDF") {
				// value is ok, use it
			} else {
				document.getElementById('RegiOrLicenseFile').innerHTML = "Please upload file in PDF Format.";
				document.getElementById('regiOrLicenseFile').focus();	
				return false;
			}
        }		
	 if(regiOrLicenseFile!=null && regiOrLicenseFile!=''){
		 var fileSize = document.getElementById("regiOrLicenseFile").files[0];
		 var sizeInMb = (fileSize.size/1024)/1024;// in MB
		     sizeInMb = sizeInMb.toFixed(1);
		    if (sizeInMb > 2) {
		    	document.getElementById('RegiOrLicenseFile').innerHTML = "Your file size is: "+sizeInMb+" MB, File size should not be more than 2 MB";
		    	document.getElementById('regiOrLicenseFile').focus();
				return false;
		    }
		    else{
		    document.getElementById('RegiOrLicenseFile').innerHTML="";
		    }
	 } */

	 var natureOfProjectId=document.getElementById("natureOfProjectId").value;
	 if (natureOfProjectId == null || natureOfProjectId == '') {
			document.getElementById('NatureOfProjectId').innerHTML = "Nature Of Project is Required.";
			document.getElementById('natureOfProjectId').focus();			
			return false;
		}	
		else
			{
			 document.getElementById('NatureOfProjectId').innerHTML="";
			}
       var encloseDetailProReportFileName=document.getElementById("encloseDetailProReportFileName").value;    		
		if(encloseDetailProReportFileName!=null && encloseDetailProReportFileName!='')
			{
			var ext = encloseDetailProReportFileName.split('.').pop();
			if (ext == "pdf" || ext == "PDF") {
				// value is ok, use it
			} else {
				document.getElementById('EncloseDetailProReportFileName').innerHTML = "Please upload file in PDF Format.";
				document.getElementById('encloseDetailProReportFileName').focus();	
				return false;
			}
     }		
     if(encloseDetailProReportFileName!=null && encloseDetailProReportFileName!=''){
		 var fileSize = document.getElementById("encloseDetailProReportFileName").files[0];
		 var sizeInMb = (fileSize.size/1024)/1024;// in MB
		     sizeInMb = sizeInMb.toFixed(1);
		    if (sizeInMb > 5) {
		    	document.getElementById('EncloseDetailProReportFileName').innerHTML = "Your file size is: "+sizeInMb+" MB, File size should not be more than 2 MB";
		    	document.getElementById('encloseDetailProReportFileName').focus();
				return false;
		    }
		    else{
		    document.getElementById('EncloseDetailProReportFileName').innerHTML="";
		    }
	 }



    
     var caCertificate=document.getElementById("caCertificate").value;
     if(caCertificate!=null && caCertificate!='')
		{
		var ext = caCertificate.split('.').pop();
		if (ext == "pdf" || ext == "PDF") {
			// value is ok, use it
		} else {
			document.getElementById('CaCertificateFileName').innerHTML = "Please upload file in PDF Format.";
			document.getElementById('caCertificate').focus();	
			return false;
		}
}		
     if(caCertificate!=null && caCertificate!=''){
   	  var fileSize = document.getElementById("caCertificate").files[0]; 	  
       	
       	var fsize = (fileSize.size / (1024*1024)).toFixed(1);    
           if (fsize >2) {
           	document.getElementById('CaCertificateFileName').innerHTML = "Your file size is: "+fsize+" MB," +" File size should not be more than 2 MB";
           	document.getElementById('caCertificate').focus();
       		return false;
           }
        
       	else
       		{
       		document.getElementById('CaCertificateFileName').innerHTML="";
          }
		 
	 }


     var certifiedList=document.getElementById("certifiedList").value;
     if(certifiedList!=null && certifiedList!='')
		{
		var ext = certifiedList.split('.').pop();
		if (ext == "pdf" || ext == "PDF") {
			// value is ok, use it
		} else {
			document.getElementById('CharateredEngineerFileName').innerHTML = "Please upload file in PDF Format.";
			document.getElementById('certifiedList').focus();	
			return false;
		}
}		
     if(certifiedList!=null && certifiedList!=''){
   	  var fileSize = document.getElementById("certifiedList").files[0];	  
       	var fsize = (fileSize.size / (1024*1024)).toFixed(1);    
           if (fsize > 2) {
           	document.getElementById('CharateredEngineerFileName').innerHTML = "Your file size is: "+fsize+" MB," +" File size should not be more than 2 MB";
           	document.getElementById('certifiedList').focus();
       		return false;
           }
        
       	else
       		{
       		document.getElementById('CharateredEngineerFileName').innerHTML="";
          }
		 
	 }
    
        var natureOfProjectId=document.getElementById("natureOfProjectId").value;    

		if(natureOfProjectId==='ExistingProject')
			{
			     var expansion = $("input:checkbox[name=expansion]:checked").val();
			     var diversification = $("input:checkbox[name=diversification]:checked").val();
			     if((expansion==null || expansion=='') && (diversification==null || diversification=='')){
			     document.getElementById('NatureOfProject').innerHTML = "Please select either Expansion or Diversification or both.";
			     document.getElementById('expansionId').focus();			
			     return false;
			     }
			     else{
			    	 document.getElementById('NatureOfProject').innerHTML = "";
				     }
			}
			
         if(natureOfProjectId!=null && natureOfProjectId!='')
         { 
      
          if(natureOfProjectId==='NewProject')
           {
           <c:if test="${empty newProjList}">
        	  //alert('Please Fill the Details of tax paid under GST Table.');
        	  document.getElementById("productsDetailsTblNew").scrollIntoView();	
        	  document.getElementById('Table').innerHTML = "Please Fill the Details of new products to be manufactured and its capacity.";
        	  document.getElementById('Table').style.cssText = "color: red !important";
           return false
           </c:if>
           
             var encloseDetailProReportFileName1=document.getElementById("encloseDetailProReportFileName1").innerHTML;		
		      if (encloseDetailProReportFileName1 == null || encloseDetailProReportFileName1 == ''|| encloseDetailProReportFileName1=='Choose file') {
			  document.getElementById('EncloseDetailProReportFileName').innerHTML = "Detailed Project Report is Required.";
			  document.getElementById('encloseDetailProReportFileName').focus();
			  return false;          
            }else{

            	 document.getElementById('EncloseDetailProReportFileName').innerHTML = "";
                }
		    }
         
          if(natureOfProjectId==='ExistingProject'){
        	  <c:if test="${empty existProjList}">
         	  //alert('Please Fill the Details of tax paid under GST Table.');
         	  document.getElementById("existingTable").scrollIntoView();	
         	  document.getElementById('Table1').innerHTML = "Please Fill the Details of existing/proposed products to be manufactured and its capacity.";
         	  document.getElementById('Table1').style.cssText = "color: red !important";
            return false
            </c:if>

          var encloseDetailProReportFileName1=document.getElementById("encloseDetailProReportFileName1").innerHTML;	
          var caCertificate1=document.getElementById("caCertificate1").innerHTML;
          var certifiedList1=document.getElementById("certifiedList1").innerHTML;
          	
	      if (encloseDetailProReportFileName1 == null || encloseDetailProReportFileName1 == ''|| encloseDetailProReportFileName1=='Choose file') {
			document.getElementById('EncloseDetailProReportFileName').innerHTML = "Detailed Project Report is Required.";
			document.getElementById('encloseDetailProReportFileName').focus();
			return false;
		}else{

       	 document.getElementById('EncloseDetailProReportFileName').innerHTML = "";
           }
   

      	
	    if (caCertificate1 == null || caCertificate1 == ''|| caCertificate1=='Choose file') {
			document.getElementById('CaCertificateFileName').innerHTML = "CA CertificateReport is Required.";
			document.getElementById('caCertificate').focus();
			return false;
		}else{

	       	 document.getElementById('CaCertificateFileName').innerHTML = "";
	           }
	   


      	
	    if (certifiedList1 == null || certifiedList1 == ''||certifiedList1=='Choose file') {
			document.getElementById('CharateredEngineerFileName').innerHTML = "Chartered Engineers Certified Report is Required.";
			document.getElementById('certifiedList').focus();
			return false;
		}else{

	       	 document.getElementById('CharateredEngineerFileName').innerHTML = "";
	           }
          
		  }
 }
		
  if (confirm("Are you sure, You want to save Project Details record."))
	   {
		   $("#mobileNo").removeAttr("readonly");
			$("#designation").removeAttr("readonly");
			$("#webSiteName").removeAttr("readonly");
			$("#pinCode").removeAttr("readonly");
			/* document.getElementById("regiOrLicense").disabled = false;
		  	document.getElementById("regiOrLicenseIEM").disabled = false;  	
		  	document.getElementById("regiOrLicenseFile").disabled = false; */
		  	document.getElementById("natureOfProjectId").disabled = false;
		  	document.getElementById("expansionId").disabled = false;
		  	document.getElementById("diversificationId").disabled = false;
		  	$("#existingProducts").removeAttr("readonly");
			$("#existingInstalledCapacity").removeAttr("readonly");
			$("#proposedProducts").removeAttr("readonly");
			$("#proposedInstalledCapacity").removeAttr("readonly");
			$("#existingGrossBlock").removeAttr("readonly");
			$("#proposedGrossBlock").removeAttr("readonly");
		   	document.getElementById("encloseDetailProReportFileName").disabled = false;
		  	document.getElementById("caCertificate").disabled = false;  	
		  	document.getElementById("certifiedList").disabled = false;
		  	document.getElementById("existProjAdd").disabled = false;
		  	 $("#existProjData").removeClass("disable_a_href");
          return true;
        }
       else
           {
		  return false;
            }	
 
	}





function validateExistProjDetails(){
	
        var existingProducts=document.getElementById("existingProducts").value;	
        var existingInstalledCapacity=document.getElementById("existingInstalledCapacity").value;
        var proposedProducts=document.getElementById("proposedProducts").value;
        var proposedInstalledCapacity=document.getElementById("proposedInstalledCapacity").value;
        var existingGrossBlock=document.getElementById("existingGrossBlock").value;	
        var proposedGrossBlock=document.getElementById("proposedGrossBlock").value;	
        var eicunit=document.getElementById("eicUnit").value;	
        var picunit=document.getElementById("picUnit").value;	
        
			    if (existingProducts == null || existingProducts == '') {
					document.getElementById('ExistingProducts').innerHTML = "Existing Products is Required.";
					document.getElementById('existingProducts').focus();
					return false;
				}
        	
			    if (existingInstalledCapacity == null || existingInstalledCapacity == '') {
					document.getElementById('ExistingInstalledCapacity').innerHTML = "Existing installed capacity is Required.";
					document.getElementById('existingInstalledCapacity').focus();
					return false;
				}
			    if (eicunit == null || eicunit == '') {
					document.getElementById('eicunit').innerHTML = "Please select unit.";
					document.getElementById('eicUnit').focus();
					return false;
				}
       	
			    if (proposedProducts == null || proposedProducts == '') {
					document.getElementById('ProposedProducts').innerHTML = "Proposed products is Required.";
					document.getElementById('proposedProducts').focus();
					return false;
				}
            	
			    if (proposedInstalledCapacity == null || proposedInstalledCapacity == '') {
					document.getElementById('ProposedInstalledCapacity').innerHTML = "Proposed installed capacity is Required.";
					document.getElementById('proposedInstalledCapacity').focus();
					return false;
				}


			    if (picunit == null || picunit == '') {
					document.getElementById('picunit').innerHTML = "Please select unit.";
					document.getElementById('picUnit').focus();
					return false;
				}
			    
		           
			    if (existingGrossBlock == null || existingGrossBlock == '') {
					document.getElementById('ExistingGrossBlock').innerHTML = "Existing gross block is Required.";
					document.getElementById('existingGrossBlock').focus();
					return false;
				}
           
			    if (proposedGrossBlock == null || proposedGrossBlock == '') {
					document.getElementById('ProposedGrossBlock').innerHTML = "Proposed gross block is Required.";
					document.getElementById('proposedGrossBlock').focus();
					return false;
				}
				return true;
			   
	
}


function emptyExistProjDetails(){
	$("#ExistingProducts").text('');
	$("#ExistingInstalledCapacity").text('');
	$("#ProposedProducts").text('');
	$("#ProposedInstalledCapacity").text('');
	$("#ExistingGrossBlock").text('');
	$("#ProposedGrossBlock").text('');
	$("#eicunit").text('');
	$("#picunit").text('');

}




//For Edit code

function editProjectDetails() {

	var r = confirm('Are you Sure,Want to Edit the Project Details?');

	if (r == true) {
		
		
		$("input[name='mobileNo']").attr("readonly", false);
		$("#mobileNo").removeAttr("readonly");
  		$("#designation").removeAttr("readonly");
  		$("#webSiteName").removeAttr("readonly");  		
  		/* document.getElementById("regiOrLicense").disabled = false;
	  	document.getElementById("regiOrLicenseIEM").disabled = false;  	
	  	document.getElementById("regiOrLicenseFile").disabled = false; */
	  	document.getElementById("natureOfProjectId").disabled = false;
	  	document.getElementById("expansionId").disabled = false;
	  	document.getElementById("diversificationId").disabled = false;

	   $("#existingProducts").removeAttr("readonly");
  		$("#existingInstalledCapacity").removeAttr("readonly");
  		$("#proposedProducts").removeAttr("readonly");
  		$("#existingGrossBlock").removeAttr("readonly");
  		$("#proposedInstalledCapacity").removeAttr("readonly");
  		$("#proposedGrossBlock").removeAttr("readonly");
  		document.getElementById("eicUnit").disabled = false;
	  	document.getElementById("picUnit").disabled = false; 
	  	
	   	document.getElementById("encloseDetailProReportFileName").disabled = false;
	  	document.getElementById("caCertificate").disabled = false;  	
	  	document.getElementById("certifiedList").disabled = false;
		document.getElementById("existProjAdd").disabled = false;
		$("#existProjData").removeClass("disable_a_href");
		document.getElementById('existProjData').focus();
		document.getElementById('mobileNo').focus();  
				
	  	
	} else {
		return false
	}
	
}




</script>
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
								<ul>
									<li><a href="./getIdByTabs22?authoTab=authoTab"
										class="filled"><span>1</span> Authorized Signatory Details</a></li>
									<li><a href="./getIdByTabs23?busiTab=busiTab"
										class="filled"><span>2</span> BusinessEntity Details</a></li>
									<li><a href="#" class="active"><span>3</span> Project
											Details</a></li>
									<li><c:if test="${not empty Iflag}">
											<a href="./getIdByTabs24?investTab=investTab" class="filled"><span>4</span>Investment
												Details</a>
										</c:if> <c:if test="${empty Iflag}">
											<a href="./getIdByTabs24?investTab=investTab"><span>4</span>Investment
												Details</a>
										</c:if></li>
									<li><c:if test="${not empty PEflag}">
											<a href="./getIdByTabs25?propoTab=propoTab" class="filled"><span>5</span>Proposed
												Employment Details</a>
										</c:if> <c:if test="${empty PEflag}">
											<a href="./getIdByTabs25?propoTab=propoTab"><span>5</span>Proposed
												Employment Details</a>
										</c:if></li>
								</ul>
							</div>
							<div class="isf-form">
								<spring:url value="/projectDetails"
									var="projectDetailsActionUrl" />
								<form:form modelAttribute="projectDetailsForm" method="post"
									id="myFormP" action="${projectDetailsActionUrl}" class="mt-4"
									enctype="multipart/form-data">
									<form:hidden path="id" />

									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">
												Project Details <a href="javascript:void(0);"
													class="remove-row" data-toggle="tooltip" title=""
													data-original-title="Please enter details of the proposed project."><i
													class="fa fa-info-circle text-info"></i></a>
											</h3>
										</div>
									</div>

									<div class="row">
										<%--  <div class="col-md-6 col-lg-4 col-xl-4">
                          <spring:bind path="projectUnitName">
												<div class="form-group">
													<form:label path="projectUnitName">Project/Unit Name <span>*</span>
													</form:label>
													<form:input path="projectUnitName" class="form-control"
														id="projectUnitName" maxlength="60"
														onkeypress="return IsCharacter1(event)" readonly="true"></form:input>
													<span id="ProjectUnitName" class="text-danger color:red"></span>
													<form:errors path="projectUnitName" class="control-label"
														cssStyle="color:red" />
												</div>
											</spring:bind>
                        </div> --%>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<spring:bind path="contactPersonName">
												<div class="form-group">
													<form:label path="contactPersonName">Name of Contact Person <span>*</span>
													</form:label>
													<form:input path="contactPersonName" class="form-control"
														id="contactPersonName" maxlength="100"
														onkeypress="return IsCharacter111(event)" readonly="true"></form:input>
													<span id="ContactPersonName" class="text-danger color:red"></span>
													<form:errors path="contactPersonName" class="control-label"
														cssStyle="color:red" />
												</div>
											</spring:bind>
										</div>

										<div class="col-md-6 col-lg-4 col-xl-4">
											<spring:bind path="mobileNo">
												<div class="form-group">
													<form:label path="mobileNo">Mobile No:<small>(+91)</small>
														<span>*</span>
													</form:label>
													<form:input path="mobileNo" class="form-control"
														id="mobileNo" maxlength="10"
														onkeypress="return IsCharacter11111(event)"
														onkeyup="IsMobileNo(event)"></form:input>
													<span id="MobileNo" class="text-danger color:red"></span>
													<form:errors path="mobileNo" class="control-label"
														cssStyle="color:red" />
												</div>
											</spring:bind>
										</div>

										<div class="col-md-6 col-lg-4 col-xl-4">
											<spring:bind path="designation">
												<div class="form-group">
													<form:label path="designation">Designation</form:label>
													<form:input path="designation" class="form-control"
														id="designation" maxlength="30"
														onkeypress="return IsCharacter1111(event)"></form:input>
													<span id="Designation" class="text-danger color:red"></span>
												</div>
											</spring:bind>
										</div>


										<div class="col-md-12">
											<spring:bind path="projectDescription">
												<div class="form-group">
													<form:label path="projectDescription">Project Description <span>*</span>
													</form:label>
													<form:textarea path="projectDescription"
														class="form-control" id="projectDescription"
														onkeypress="return IsCharacter11(event)" readonly="true"></form:textarea>
													<span id="ProjectDescription" class="text-danger color:red"></span>
													<form:errors path="projectDescription"
														class="control-label" cssStyle="color:red" />
												</div>
											</spring:bind>
										</div>
									</div>


									<div class="row">
										<div class="col-md-12">
											<spring:bind path="webSiteName">
												<div class="form-group">
													<form:label path="webSiteName">Website <small>(if
															any)</small>
													</form:label>
													<form:input path="webSiteName" class="form-control"
														id="webSiteName" maxlength="200"></form:input>
												</div>
											</spring:bind>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Project Location</h3>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-12">
											<spring:bind path="fullAddress">
												<div class="form-group">
													<form:label path="fullAddress">Full Address <span>*</span>
													</form:label>
													<form:input path="fullAddress" class="form-control"
														id="fullAddress" readonly="true"></form:input>
													<span id="fullAddress" class="text-danger color:red"></span>
													<form:errors path="fullAddress" class="control-label"
														cssStyle="color:red" />
												</div>
											</spring:bind>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<spring:bind path="districtName">
												<div class="form-group">
													<form:label path="districtName">District <span>*</span>
													</form:label>
													<form:input path="districtName" class="form-control"
														readonly="true" id="districtName"></form:input>
													<span id="DistrictName" class="text-danger color:red"></span>
													<form:errors path="districtName" class="control-label"
														cssStyle="color:red" />
												</div>
											</spring:bind>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<spring:bind path="mandalName">
												<div class="form-group">
													<form:label path="mandalName">Mandal</form:label>
													<form:input path="mandalName" class="form-control"
														id="mandalName" readonly="true"></form:input>
													<span id="MandalName" class="text-danger color:red"></span>
													<form:errors path="mandalName" class="control-label" />
												</div>
											</spring:bind>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<spring:bind path="resionName">
												<div class="form-group">
													<form:label path="resionName">Region</form:label>
													<form:input path="resionName" class="form-control"
														id="resionName" readonly="true"></form:input>
													<span id="resionName" class="text-danger color:red"></span>
													<form:errors path="resionName" class="control-label" />
												</div>
											</spring:bind>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<spring:bind path="pinCode">
												<div class="form-group">
													<form:label path="pinCode">Pin Code <span>*</span>
													</form:label>
													<form:input path="pinCode" class="form-control"
														id="pinCode" maxlength="6"
														onkeypress="return IsCharacter111111(event)"
														readonly="true"></form:input>
													<span id="PinCode" class="text-danger color:red"></span>
													<form:errors path="pinCode" class="control-label"
														cssStyle="color:red" />
												</div>
											</spring:bind>
										</div>
									</div>

									<%-- <div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Registration or License for
												setting up Industrial Undertaking</h3>
										</div>
									</div>

									<div class="row">
						Rproje				<div class="col-sm-12">
											<spring:bind path="regiOrLicense">
												<div
													class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="regiOrLicense"
														class="custom-control-input" id="regiOrLicense"
														value="EncloseUAM"></form:radiobutton>
													<form:label path="regiOrLicense"
														class="custom-control-label" for="regiOrLicense">Udyam Registration Certificate <small>(for
															MSME)</small>
													</form:label>
												</div>
												<div
													class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="regiOrLicense"
														class="custom-control-input" id="regiOrLicenseIEM"
														value="IEMcopy"></form:radiobutton>
													<form:label path="regiOrLicense"
														class="custom-control-label" for="regiOrLicenseIEM">IEM copy <small>(for
															large & Mega)</small>
													</form:label>
												</div>
											</spring:bind>

											<img src="images/pdf-icon.png" class="pdf-icon"
												alt="pdf-icon"> <small>(In PDF format less
												than 2 MB)</small> <span class="text-danger">*</span>
											<div class="custom-file mt-2">
												<input type="file" class="custom-file-input"
													id="regiOrLicenseFile" name="regiOrLicenseFileName"
													onchange="ValidateSize(this)" /> <label
													class="custom-file-label" for="regiOrLicenseFile"
													id="regiOrLicenseFile1">Choose file</label>
											</div>
											<span id="RegiOrLicenseFile" class="text-danger color:red"></span>
										</div>
									</div> --%>

									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Select Nature of the project</h3>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<form:label path="natureOfProject">Select Nature of the project<span
														class="text-danger">*</span>

													<!-- vinay ex -->
													<!-- 				
														<select id="my_select">
                                             <option value="a">a</option>
                                           <option value="b" selected="selected">b</option>
                                         <option value="c">c</option>
                                                </select>

                                               <div id="reset">
                                                     reset
                                                         </div>
														 -->

													<!-- vinay ex -->

												</form:label>
												<form:select path="natureOfProject" class="form-control"
													id="natureOfProjectId" onchange="showhideMultipleDivs();">
													<form:option value="">Select One</form:option>
													<form:option value="NewProject">New Project</form:option>
													<form:option value="ExistingProject">Existing Project</form:option>

												</form:select>


												<span id="NatureOfProjectId" class="text-danger color:red"></span>
											</div>
										</div>
										<div class="col-sm-12" id="natureOfProjectDiv">
											<div class="form-group mt-3">
												<div
													class="custom-control custom-checkbox custom-control-inline">
													<form:checkbox path="expansion"
														class="custom-control-input" id="expansionId"
														name="expansion" value="Expansion"
														onclick="hideMultipleDivs()"></form:checkbox>
													<label class="custom-control-label" for="expansionId">Expansion</label>
												</div>
												<div
													class="custom-control custom-checkbox custom-control-inline">
													<form:checkbox path="diversification"
														class="custom-control-input" id="diversificationId"
														name="diversification" value="Diversification"
														onclick="hideMultipleDivs()"></form:checkbox>
													<label class="custom-control-label" for="diversificationId">Diversification</label>
												</div>
												<span id="NatureOfProject" class="text-danger color:red"></span>
											</div>
										</div>
									</div>


									<div class="row" id="hideById">
										<div class="col-sm-12 mt-4" id="existingTable">
										<span id="Table1" class="text-danger"></span>
											<h3 class="common-heading">
												Details of existing/proposed products to be manufactured and
												its capacity <small>(for expansion projects only)</small>
											</h3>
										</div>
										<div class="col-md-12" >
											<div class="table-responsive mt-3" id="productsDetailsTbl">
												<%-- 	<c:if test="${not empty existProjList}"> --%>
												<table class="table table-stripped table-bordered">
													<thead>
														<tr>
															<th>Existing Products</th>
															<th>Existing Installed Capacity(PA)</th>
															<th>Proposed Products</th>
															<th>Proposed Installed Capacity(PA)</th>
															<th>Existing Gross Block (In INR)</th>
															<th>Proposed Gross Block (In INR)</th>
															<th style="width: 10%;">Action</th>
														</tr>
													</thead>

													<tbody class="existing-proposed-products"
														id="existProjData">
														<c:forEach var="existProj" items="${existProjList}"
															varStatus="counter">
															<c:if test="${not empty existProjList}">
																<!-- Iterating the list using JSTL tag  -->

																<tr>

																	<td>${existProj.epdExisProducts}</td>
																	<td>${existProj.epdExisInstallCapacity}</td>
																	<td>${existProj.epdPropProducts}</td>
																	<td>${existProj.epdPropInstallCapacity}</td>
																	<td>${existProj.epdExisGrossBlock}</td>
																	<td>${existProj.epdPropoGrossBlock}</td>

																	<td class="text-center">
																		<%-- <a id="existProjEdit"
																			class="my-edit" title="Edit"  
																			href="./editExistProjectRow?selectedRow=<c:out value="${counter.index}"/>">
																				<i class="fa fa-edit text-info"></i>
																		</a> --%>

																		<button id="existProjEdit" autofocus="autofocus"
																			class="my-edit border-0 bg-white action-btn p-0"
																			title="Edit"
																			formaction="${pageContext.request.contextPath }/editExistProjectRow?selectedRow=<c:out value="${counter.index}"/>">
																			<i class="fa fa-edit text-info"></i>
																		</button> <%-- <a id="existProjDelete" autofocus="autofocus" class="my-delete"
																		href="./deleteExistProjectRow?selectedRow=${counter.index}"
																		title="Delete"
																		onclick="return confirm('Are you sure, you want to delete record?')">
																			<i class="fa fa-trash text-danger"></i>
																	</a>   --%>


																		<button class="border-0 bg-white action-btn p-0"
																			autofocus="autofocus"
																			onclick="return confirm('Are you sure? Do you want to delete?')"
																			formaction="${pageContext.request.contextPath }/deleteExistProjectRow?selectedRow=${counter.index}"
																			data-toggle="tooltip" title="Delete"
																			id="deleteButton">
																			<i class="fa fa-trash text-danger"></i>
																		</button>


																	</td>
																</tr>
															</c:if>
														</c:forEach>
													</tbody>
													<tfoot>
														<tr>
															<td><form:input path="ExistingProducts"
																	name="ExistingProducts" class="form-control"
																	onblur="emptyExistProjDetails()" id="existingProducts"
																	maxlength="50"></form:input> <span
																id="ExistingProducts" class="text-danger color:red"></span></td>

															<td><form:input path="existingInstalledCapacity"
																	onblur="emptyExistProjDetails()"
																	name="existingInstalledCapacity" class="form-control"
																	id="existingInstalledCapacity" maxlength="12"
																	onkeypress="return validateNumericField()"></form:input>
																<span id="ExistingInstalledCapacity"
																class="text-danger color:red"></span> <form:select
																	path="eicUnit" class="form-control mt-2" id="eicUnit"
																	onblur="emptyExistProjDetails()">
																	<form:option value="">Select Unit</form:option>
																	<form:options items="${unitListMap}" />
																</form:select> <span id="eicunit" class="text-danger color:red"></span>
															</td>
															<td><form:input path="proposedProducts"
																	onblur="emptyExistProjDetails()"
																	name="proposedProducts" class="form-control"
																	id="proposedProducts" maxlength="50"></form:input> <span
																id="ProposedProducts" class="text-danger color:red"></span></td>
															<td><form:input path="proposedInstalledCapacity"
																	onblur="emptyExistProjDetails()"
																	name="proposedInstalledCapacity" class="form-control"
																	id="proposedInstalledCapacity" maxlength="12"
																	onkeypress="return validateNumericField()"></form:input>
																<span id="ProposedInstalledCapacity"
																class="text-danger color:red"></span> <form:select
																	path="picUnit" class="form-control mt-2"
																	onblur="emptyExistProjDetails()" id="picUnit">
																	<form:option value="">Select Unit</form:option>
																	<form:options items="${unitListMap}" />
																</form:select> <span id="picunit" class="text-danger color:red"></span>
															</td>
															<td><form:input path="existingGrossBlock"
																	onblur="emptyExistProjDetails()"
																	name="existingGrossBlock" class="form-control"
																	id="existingGrossBlock" maxlength="12"
																	onkeypress="return validateNumericField()"></form:input>
																<span id="ExistingGrossBlock"
																class="text-danger color:red"></span></td>

															<td><form:input path="proposedGrossBlock"
																	onblur="return propGrossBlockAmt();emptyExistProjDetails()"
																	name="proposedGrossBlock" class="form-control"
																	id="proposedGrossBlock" maxlength="12"
																	onkeypress="return validateNumericField()"></form:input>
																<span id="ProposedGrossBlock"
																class="text-danger color:red"></span></td>

															<c:if test="${empty editepd}">
																<td class="text-center"><button
																		formaction="addExistProjectDetails"
																		onclick="return propGrossBlockAmt(); validateExistProjDetails()"
																		id="existProjAdd"
																		class="btn btn-outline-success btn-sm add-save-data">Add</button>
																</td>
															</c:if>
															<c:if test="${not empty editepd}">
																<td class="text-center"><button
																		formaction="addExistProjectDetails"
																		onclick="return validateExistProjDetails()"
																		id="existProjAdd"
																		class="btn btn-outline-success btn-sm add-save-data">Save</button>
																</td>
															</c:if>
														</tr>
													</tfoot>
												</table>
											</div>
										</div>
									</div>



									<!-- Sachin -->

									<div class="row" id="hideByIdNew" class="NewProject">
										<div class="col-sm-12 mt-4">
										<span id="gstTable" class="text-danger"></span>
											<h3 class="common-heading">Details of new products to be
												manufactured and its capacity</h3>
										</div>
										<div class="col-md-12">
											<div class="table-responsive mt-3" id="productsDetailsTblNew">
												<%-- 	<c:if test="${not empty existProjList}"> --%>
												<table class="table table-stripped table-bordered">
													<thead>
														<span id="Table" class="text-danger"></span>
														<tr>

															<th>Proposed Products</th>
															<th>Proposed Installed Capacity(PA)</th>

															<th style="width: 10%;">Action</th>
														</tr>
													</thead>

													<tbody class="new-proposed-products" id="newProjData">
														<c:forEach var="newProj" items="${newProjList}"
															varStatus="counter">
															<c:if test="${not empty newProjList}">
																<!-- Iterating the list using JSTL tag  -->

																<tr>


																	<td>${newProj.newPropProducts}</td>
																	<td>${newProj.newPropInstallCapacity}</td>


																	<td class="text-center">
																		<button id="newProjEdit" autofocus="autofocus"
																			class="my-edit border-0 bg-white action-btn p-0"
																			title="Edit"
																			formaction="${pageContext.request.contextPath }/editNewProjectRow?selectedRow=<c:out value="${counter.index}"/>">
																			<i class="fa fa-edit text-info"></i>
																		</button> <%-- <a id="existProjDelete" autofocus="autofocus" class="my-delete"
																		href="./deleteExistProjectRow?selectedRow=${counter.index}"
																		title="Delete"
																		onclick="return confirm('Are you sure, you want to delete record?')">
																			<i class="fa fa-trash text-danger"></i>
																	</a>   --%>


																		<button class="border-0 bg-white action-btn p-0"
																			autofocus="autofocus"
																			onclick="return confirm('Are you sure? Do you want to delete?')"
																			formaction="${pageContext.request.contextPath }/deleteNewProjectRow?selectedRow=${counter.index}"
																			data-toggle="tooltip" title="Delete"
																			id="deleteButton">
																			<i class="fa fa-trash text-danger"></i>
																		</button>


																	</td>
																</tr>
															</c:if>
														</c:forEach>
													</tbody>
													<tfoot>
														<tr>



															<td><form:input path="proposedProductsNew"
																	name="proposedProducts" class="form-control"
																	id="proposedProducts" maxlength="50"></form:input> <span
																id="ProposedProducts" class="text-danger color:red"></span></td>
															<td>

																<table class="table m-0">
																	<tr>
																		<td width="50%" class="border-0 pl-0 pt-0 pb-0">
																			<form:input path="proposedInstalledCapacityNew"
																				name="proposedInstalledCapacity"
																				class="form-control" id="proposedInstalledCapacity"
																				maxlength="12"></form:input>
																			<span id="ProposedInstalledCapacity"
																			class="text-danger color:red"></span>
																		</td>
																		<td class="border-0 p-0"><form:select
																				path="picUnit" class="form-control"
																				onblur="emptyExistProjDetails()" id="picUnit">
																				<form:option value="">Select Unit</form:option>
																				<form:options items="${unitListMap}" />
																			</form:select> <span id="picunit" class="text-danger color:red"></span>
																		</td>
																	</tr>
																</table>

															</td>
															<c:if test="${empty editnpd}">
																<td class="text-center"><button
																		formaction="addNewProjectDetails" id="newProjAdd"
																		class="btn btn-outline-success btn-sm add-save-data">Add</button>
																	<!-- onclick="return propGrossBlockAmt(); validateExistProjDetails()"  -->

																</td>
															</c:if>
															<c:if test="${not empty editnpd}">
																<td class="text-center"><button
																		formaction="addNewProjectDetails" id="newProjAdd"
																		class="btn btn-outline-success btn-sm add-save-data">Save</button>
																	<!-- onclick="return validateExistProjDetails()" --></td>
															</c:if>
														</tr>
													</tfoot>
												</table>
											</div>
										</div>
									</div>





									<div class="row" id="hideByRowId4">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Relevant Documentary Support</h3>
										</div>
									</div>

									<div class="row">

										<div class="col-sm-12">
											<div class="table-responsive mt-3">
												<table class="table table-stripped table-bordered">
													<tbody>

														<tr id="hideByRowId3">
															<td style="width: 50%;">
																<ul class="common-list">
																	<li>Enclose Detailed Project Report prepared by
																		External Consultant/Chartered Accountants <span
																		class="text-danger">*</span> <small>(In PDF
																			format less than 5 MB)</small> <img src="images/pdf-icon.png"
																		class="pdf-icon" alt="pdf-icon">
																	</li>
																</ul>
															</td>
															<td>
																<div class="custom-file">
																	<input type="file" class="custom-file-input"
																		id="encloseDetailProReportFileName"
																		name="enclDetProRepFileName"
																		onchange="ValidateSize1(this)" /> <label
																		class="custom-file-label" for="encloseDetailed"
																		id="encloseDetailProReportFileName1">Choose file</label>
																</div> <span id="EncloseDetailProReportFileName"
																class="text-danger color:red"></span>
															</td>
														</tr>
														<tr id="hideByRowId1">
															<td>
																<ul class="common-list">
																	<li>CA Certificate for existing gross block<span
																		class="text-danger">*</span> <small>(In PDF
																			format less than 2 MB)</small> <img src="images/pdf-icon.png"
																		class="pdf-icon" alt="pdf-icon"></li>
																</ul>
															</td>
															<td>
																<div class="custom-file">
																	<input type="file" class="custom-file-input"
																		id="caCertificate" name="caCertificateFileName"
																		onchange="ValidateSize2(this)" /> <label
																		class="custom-file-label" for="caCertificate"
																		id="caCertificate1">Choose file</label>
																</div> <span id="CaCertificateFileName"
																class="text-danger color:red"></span>
															</td>
														</tr>
														<tr id="hideByRowId2">
															<td>
																<ul class="common-list">
																	<li>Chartered Engineers Certified List of Fixed
																		Assets in support of existing gross block.<span
																		class="text-danger">*</span> <small>(In PDF
																			format less than 2 MB)</small> <img src="images/pdf-icon.png"
																		class="pdf-icon" alt="pdf-icon">
																	</li>
																</ul>
															</td>
															<td>
																<div class="custom-file mt-2">
																	<input type="file" class="custom-file-input"
																		id="certifiedList" name="charatEngFileName"
																		onchange="ValidateSize3(this)" /> <label
																		class="custom-file-label" for="certifiedList"
																		id="certifiedList1">Choose file</label>
																</div> <span id="CharateredEngineerFileName"
																class="text-danger color:red"></span>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-5">
											<div class="col-sm-5">
												<a href="./businessDetails"
													onclick="return confirm('Are you sure you want to go to Business Entity Details?')"
													class="common-default-btn mt-3">Previous</a>
											</div>
										</div>
										<div class="col-sm-7 text-right">
											<c:if test="${not empty projectId}">
												<button type="button" class="common-btn mt-3"
													onclick="editProjectDetails()">
													<i class="fa fa-edit"></i>Edit
												</button>
											</c:if>
											<form:button onclick="return projectDetailsValidation()"
												class="common-btn mt-3">Save and Next</form:button>
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

<script>
    if ( window.history.replaceState ) {
        window.history.replaceState( null, null, window.location.href );
    }   
</script>

</html>
<%@ include file="footer.jsp"%>