<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="com.webapp.ims.model.AvailCustomisedDetails" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Incentive Details</title>
<jsp:include page="head.jsp" />
<script src="js/number.js"></script>
<script type="text/javascript">
function fetchdata(){
$(document).ready(function() {
	$(document).on("change", ".form-control", function() {
		/* var $j = jQuery.noConflict();
		$.ajax({
		  url: 'proposedEmploymentDtails',
		  data: $('form').serialize(),
		  processData: false,
		  enctype : 'multipart/form-data',
		  contentType: false,
		  type: 'POST',
		  success: function(data)
		  {
		    alert(data);
		  }
		}); */
		document.getElementById("myForm").action = "saveIncentiveDetails";
		document.getElementById('myForm').submit();
	});
});


}
/* $(document).ready(function(){
	 setInterval(fetchdata,6000);
	}); */

$(function() {
    $("#UploadDocumentISFId").html('${incentiveDeatilsForm.isfCustIncDocName}');
   	      
    });


var category='${category}';
//var category='Small';
function showYesOrNo()
{

if(category ==='Super Mega')
	{
	//CstmIncStatusNo
	//CstmIncStatusYes
	var iscstminc = '${isctmincentive}';
	if(iscstminc == "Yes"){
		$("#AddIncentiveType").show();
		$(".add-incentive-type").show();
		document.getElementById('CstmIncStatusYes').checked=true;  
   		$(".row").removeClass("condition-on-super-mega");
   		$("#ReimbursementRow1").hide();
	}else{
		$("#AddIncentiveType").hide();
		
		$("#customFields").hide();
		$(".add-incentive-type").hide();
		document.getElementById('CstmIncStatusNo').checked=true;  
   		$(".row").removeClass("condition-on-super-mega");
   		$("#ReimbursementRow1").hide();
	}
   		
	}
else
	{
	   //alert("in No");
	 document.getElementById('CstmIncStatusNo').checked=true; 	
	 $('#AddIncentiveType').hide();
	}
if(category==='Small' || category==='Medium' || category==='Large')
{
	 document.getElementById('note').innerHTML = "Note: Period of Reimbursement of Incentives for small,medium and large industrial undertakings is 5 years";
}

}







$(document).ready(function(){
	var projectTypeInput = '${projectType}';
	if (projectTypeInput != 'NewProject') {
		$(".condition-on-electric").hide();
	}
});

function reimbursement()
{
	
	var marker='${eligibleuns}';
	var projectTypeInput = '${projectType}';
	//alert("eligibleuns"+marker);
	if(marker<100 || projectTypeInput != 'NewProject')
		{
		$("#ReimbursementRow").hide();
		
		$("#efphide").hide();
			}

	} 



function incentiveDeatilsForm() {

	var ISF_Claim_Reim = document.incentiveDeatilsform.ISF_Claim_Reim.value;
	if (ISF_Claim_Reim == null || ISF_Claim_Reim == '') {
		document.getElementById('ISF_claim_reim').innerHTML = "Please Fill Amount for Reimbursement";
		document.incentiveDeatilsform.ISF_Claim_Reim.focus();
		return false;
	}

	if(!/^[0-9]+$/.test(ISF_Claim_Reim)) {
		  document.getElementById('ISF_claim_reim').innerHTML = "Only Numeric Value allowed.";
		  document.incentiveDeatilsform.ISF_Claim_Reim.focus();
	      return false;
	}

	
	var ISF_Stamp_Duty_EX = document.incentiveDeatilsform.ISF_Stamp_Duty_EX.value;
	if (ISF_Stamp_Duty_EX == null || ISF_Stamp_Duty_EX == '') {
		document.getElementById('ISF_Stamp_Duty_ex').innerHTML = "Please Fill Amount of Stamp Duty Exemption";
		document.incentiveDeatilsform.ISF_Stamp_Duty_EX.focus();
		return false;
	}

	if(!/^[0-9]+$/.test(ISF_Stamp_Duty_EX)) {
		  document.getElementById('ISF_Stamp_Duty_ex').innerHTML = "Only Numeric Value allowed.";
		  document.incentiveDeatilsform.ISF_Stamp_Duty_EX.focus();
	      return false;
	}

	
	
	var ISF_cis = document.incentiveDeatilsform.ISF_Cis.value;
	if (ISF_cis == null || ISF_cis == '') {
		document.getElementById('ISF_cis').innerHTML = "Please Fill Capital Interest Subsidy";
		document.incentiveDeatilsform.ISF_Cis.focus();
		return false;
	}
	if(!/^[0-9]+$/.test(ISF_cis)) {
		  document.getElementById('ISF_cis').innerHTML = "Only Numeric Value allowed.";
		  document.incentiveDeatilsform.ISF_Cis.focus();
	      return false;
	}

	if(!ValidateSize(document.getElementById('UploadDocumentISF'))){
		return false;
	}

	/*  var CstmIncStatus = document.incentiveDeatilsform.CstmIncStatusYes.value;
		if(CstmIncStatus ==='Yes'){
	
	var UploadDocumentISF = document.incentiveDeatilsform.UploadDocumentISF.value;
	if (UploadDocumentISF == null || UploadDocumentISF == '') {
		//document.getElementById('UploadDocumentisf').innerHTML = "Upload Document is Required";
		//document.incentiveDeatilsform.UploadDocumentISF.focus();
		return false;
	}
	var ext = UploadDocumentISF.split('.').pop();
	if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('UploadDocumentisf').innerHTML = "Please Upload Document in PDF Format";
		document.incentiveDeatilsform.UploadDocumentISF.focus();
		return false;
	}
  }	 */
	
}

function incentiveDeatilsSubmitForm() {
	
			
				var	sgstClaim = document.getElementById("ISF_Claim_Reim").value
				
					if (sgstClaim <= 0) {
						document.getElementById('ISF_claim_reim').innerHTML = "SGST claimed amt should be greater than Zero";
						document.getElementById('ISF_Claim_Reim').focus();
						return false;
					} else {
				        document.getElementById('ISF_claim_reim').innerHTML = "";
				    }
				
			
				/* var	stampExe= document.getElementById("ISF_Stamp_Duty_EX").value
				
					if (stampExe <= 0) {
						document.getElementById('ISF_Stamp_Duty_ex').innerHTML = "Stamp Duty Exemption amt should be greater than Zero";
						document.getElementById('ISF_Stamp_Duty_EX').focus();
						return false;
					} else {
				        document.getElementById('ISF_Stamp_Duty_ex').innerHTML = "";
				    }
				
			var	stampReim = document.getElementById("ISF_Amt_Stamp_Duty_Reim").value
				
					if (stampReim <= 0) {
						document.getElementById('ISF_Amt_Stamp_Duty_reim').innerHTML = "Stamp Duty Reim amt should be greater than Zero";
						document.getElementById('ISF_Amt_Stamp_Duty_Reim').focus();
						return false;
					} else {
				        document.getElementById('ISF_Amt_Stamp_Duty_reim').innerHTML = "";
				    } */
			
				var	stampReim = document.getElementById("ISF_Cis").value
				
					if (stampReim <= 0) {
						document.getElementById('ISF_cis').innerHTML = "CIS amt should be greater than Zero";
						document.getElementById('ISF_Cis').focus();
						return false;
					} else {
				        document.getElementById('ISF_cis').innerHTML = "";
				    }
				
			
				var	iisAmt = document.getElementById("ISF_Iis").value
				
					if (iisAmt <= 0) {
						document.getElementById('ISF_iis').innerHTML = "IIS amt should be greater than Zero";
						document.getElementById('ISF_Iis').focus();
						return false;
					} else {
				        document.getElementById('ISF_iis').innerHTML = "";
				    }
				
			
				var	isLoan = document.getElementById("ISF_IsLoan").value
				
					if (isLoan <= 0) {
						document.getElementById('ISF_isLoan').innerHTML = "IS Loan amt should be greater than Zero";
						document.getElementById('ISF_IsLoan').focus();
						return false;
					} else {
				        document.getElementById('ISF_isLoan').innerHTML = "";
				    }
				
			var	reimDisallowed = document.getElementById("ISF_ReimDisallowed").value
				
					if (reimDisallowed <= 0) {
						document.getElementById('ISF_reimDisallowed').innerHTML = "Reim Disallowed amt should be greater than Zero";
						document.getElementById('ISF_ReimDisallowed').focus();
						return false;
					} else {
				        document.getElementById('ISF_reimDisallowed').innerHTML = "";
				    }
				
			
			
				var	diffAbled = document.getElementById("ISF_DiffAbled").value
				
					if (diffAbled <= 0) {
						document.getElementById('ISF_diffAbled').innerHTML = "differently abled amt should be greater than Zero";
						document.getElementById('ISF_DiffAbled').focus();
						return false;
					} else {
				        document.getElementById('ISF_diffAbled').innerHTML = "";
				    }
				
			
	
	
	
	var r = confirm("Press Ok for Final Submission! ");

	if (r == true) {
		alert(" Your Application Form is Submitted");
		// return false
		//window.location.href = 'https://niveshmitra.up.nic.in/Default.aspx';
	} else {
		return false
	}
	
}



function ValidateSize(file) {
	var uploadDocumentISF = document.getElementById('UploadDocumentISF').value;
	var maxSize = '2';
	if(uploadDocumentISF!=null && uploadDocumentISF!=''){
		//File extension validation
	var ext = uploadDocumentISF.split('.').pop();
	if (ext == "pdf"  || ext == "PDF") {
		// value is ok, use it
	} else {
		document.getElementById('UploadDocumentisf').innerHTML = "Please upload document in PDF format.";
		document.getElementById('UploadDocumentISF').focus();
		document.getElementById("UploadDocumentISF").value="";
		return false;
	}
	
   //File size validation
   var fileSize = file.files[0].size / 1024 / 1024; // in MB
   if (fileSize > maxSize) {
            document.getElementById('UploadDocumentisf').innerHTML = " File size should not be more than 2 MB. "+"Upload file size is "+fileSize+" MB.";
   	document.getElementById('UploadDocumentISF').focus();
   	document.getElementById("UploadDocumentISF").value="";
		return false;
   }    
	}	
	document.getElementById('UploadDocumentisf').innerHTML = "";
	return true;
	
}
	
	
	function numeric_only (event, input) {
	    if ((event.which < 32) || (event.which > 126))
		return true; 
    	return jQuery.isNumeric ($(input).val () + String.fromCharCode (event.which));
}
	
	 $(function() {
		$("input[name='ISF_Cstm_Inc_Status']").click(function() {
			if ($("#CstmIncStatusYes").is(":checked")) {
				
				$("#AddIncentiveType").show();
				$(".add-incentive-type").show();
				
				
			} else {
				$("#AddIncentiveType").hide();
				$(".add-incentive-type").hide();
				$("#customFields").empty();
			}
		});
		
		
	}); 

	 $( document ).ready(function() {
		 if ($("#CstmIncStatusYes").is(":checked")) {
			 $("#AddIncentiveType").show();	
		} 
	 }); 

	

	 $(window).on('load', function() {
	     $(".all_total").val(parseFloat($(".Total_Stamp_Duty_EX").val()) + parseFloat($(".Total_SGST_Reim").val())
		    	    + parseFloat($(".Total_EPF_Reim").val()) + parseFloat($(".Total_ISF_CIS").val()) + parseFloat($(".Total_Other_Incentives").val()));
	     $("input[name='ISF_Ttl_SGST_Reim']").attr("readonly", true);
	     $("input[name='ISF_Ttl_Stamp_Duty_EX']").attr("readonly", true);
	     $("input[name='ISF_Total_Int_Subsidy']").attr("readonly", true);
	     $("input[name='Total_Other_Incentive']").attr("readonly", true);
	    }); 
		 

	$(document).on("keyup", ".ISF_Reim_SGST", function() {
	    var sum = 0;
	    $(".ISF_Reim_SGST").each(function(){
	        sum += +$(this).val();
	    });
	    $(".Total_SGST_Reim").val(sum);
	    $(".all_total").val(parseFloat($(".Total_Stamp_Duty_EX").val()) + parseFloat($(".Total_SGST_Reim").val())
	    	    + parseFloat($(".Total_EPF_Reim").val()) + parseFloat($(".Total_ISF_CIS").val()) + parseFloat($(".Total_Other_Incentives").val()));
	});

	$(document).on("keyup", ".Stamp_Duty_EX", function() {
	    var sum = 0;
	    $(".Stamp_Duty_EX").each(function(){
	        sum += +$(this).val();
	    });
	    $(".Total_Stamp_Duty_EX").val(sum);
	    $(".all_total").val(parseFloat($(".Total_Stamp_Duty_EX").val()) + parseFloat($(".Total_SGST_Reim").val())
	    	    + parseFloat($(".Total_EPF_Reim").val()) + parseFloat($(".Total_ISF_CIS").val()) + parseFloat($(".Total_Other_Incentives").val()));
	});

	$(document).on("keyup", ".EPF_Reim", function() {
	    var sum = 0;
	    $(".EPF_Reim").each(function(){
	        sum += +$(this).val();
	    });
	    $(".Total_EPF_Reim").val(sum);
	    $(".all_total").val(parseFloat($(".Total_Stamp_Duty_EX").val()) + parseFloat($(".Total_SGST_Reim").val())
	    	    + parseFloat($(".Total_EPF_Reim").val()) + parseFloat($(".Total_ISF_CIS").val()) + parseFloat($(".Total_Other_Incentives").val()));
	});

	$(document).on("keyup", ".ISF_CIS", function() {
	    var sum = 0;
	    $(".ISF_CIS").each(function(){
	        sum += +$(this).val();
	    });
	    $(".Total_ISF_CIS").val(sum);
	    $(".all_total").val(parseFloat($(".Total_Stamp_Duty_EX").val()) + parseFloat($(".Total_SGST_Reim").val())
	    	    + parseFloat($(".Total_EPF_Reim").val()) + parseFloat($(".Total_ISF_CIS").val()) + parseFloat($(".Total_Other_Incentives").val()));
	});

	$(document).on("keyup", ".Other_Incentives", function() {
	    var sum = 0;
	    $(".Other_Incentives").each(function(){
	        sum += +$(this).val();
	    });
	    $(".Total_Other_Incentives").val(sum);
	    $(".all_total").val(parseFloat($(".Total_Stamp_Duty_EX").val()) + parseFloat($(".Total_SGST_Reim").val())
	    	    + parseFloat($(".Total_EPF_Reim").val()) + parseFloat($(".Total_ISF_CIS").val()) + parseFloat($(".Total_Other_Incentives").val()));
	});

	
	

	$( document ).ready(function() {
		var marker = new String("(${Marker})"); // access marker from controller /IAFCtl


			$('#Add_SCST_checkbox').on('click',function(){
				   var checked=$('#Add_SCST_checkbox').is(':checked');
				   var value =marker.charAt(1);
				   
				   if(value !=1){
					   alert("you are not eligible for this additional incentive")
					   $("#unselect_SCST_checkbox").addClass("d-select-checkbox");
					   
					   }
				   else if(checked==true  && value == 1)
				    {   
				        $('#Add_SCST_Textbox').attr('readonly',false);
				        var text=$('#Add_SCST_Textbox').val();
				        checktext(text)
				    } else {
				        $('#Add_SCST_Textbox').attr('readonly',true);
				        var text=$('#Add_SCST_Textbox').val();
				        checktext(text)
				    }
			 });

			$('#Add_FW_checkbox').on('click',function(){
				   var checked=$('#Add_FW_checkbox').is(':checked');
				   var value =marker.charAt(2);
				   if(value !=1){
					   alert("you are not eligible for this additional incentive")
					   $("#unselect_FW_checkbox").addClass("d-select-checkbox");			   
					   }
				   if(checked==true  && value == 1)
				    {   
				        $('#Add_FW_Textbox').attr('readonly',false);
				        var text=$('#Add_FW_Textbox').val();
				        checktext(text)
				    } else {
				        $('#Add_FW_Textbox').attr('readonly',true);
				        var text=$('#Add_FW_Textbox').val();
				        checktext(text)
				    }
			 });

			$('#Add_BPLW_checkbox').on('click',function(){
				   var checked=$('#Add_BPLW_checkbox').is(':checked');
				   var value =marker.charAt(3);
				   if(value !=1){
					   alert("you are not eligible for this additional incentive")
					   //08-09-2020
					   $("#unselect_BPLW_checkbox").addClass("d-select-checkbox");
					   }
				  
				   if(checked==true  && value == 1)
				    {   
				        $('#Add_BPLW_Textbox').attr('readonly',false);
				        var text=$('#Add_BPLW_Textbox').val();
				        checktext(text)
				    } else {
				        $('#Add_BPLW_Textbox').attr('readonly',true);
				        var text=$('#Add_BPLW_Textbox').val();
				        checktext(text)
				       // $("#Add_BPLW_Textbox").toggleClass("hide-content");
				        
				    }
			 });
		
			$('#Add_Stamp_Duty_checkbox').on('click',function(){
				   var checked=$('#Add_Stamp_Duty_checkbox').is(':checked');
				  // var value ="elisible";
				   var value = "${eligiblestamp}";
				   if(value!="eligible"){
					   alert("you are not eligible for this additional incentive")
					   //08-09-2020
					   $("#unselect_Stamp_Duty_checkbox").addClass("d-select-checkbox");
					   }
				   if(checked==true  && value == "eligible")
				    {   
				        $('#Add_Stamp_Duty_Textbox').attr('readonly',false);
				        var text=$('#Add_Stamp_Duty_Textbox').val();
				        checktext(text)
				    } else {
				        $('#Add_Stamp_Duty_Textbox').attr('readonly',true);
				        var text=$('#Add_Stamp_Duty_Textbox').val();
				        checktext(text)
				    }
			 });

			$('#Add_EFP_Reim_SkUkW_checkbox').on('click',function(){
				   var checked=$('#Add_EFP_Reim_SkUkW_checkbox').is(':checked');
				   var value = "${eligibleaepfw}";
				   if(value!="eligible"){
					   alert("you are not eligible for this additional incentive")
					   //08-09-2020
					   $("#unselect_EFP_Reim_SkUkW_checkbox").addClass("d-select-checkbox");
					   }
				  // var value ="elisible";
				   if(checked==true  && value == "eligible")
				    {   
				        $('#Add_EFP_Reim_SkUkW_Textbox').attr('readonly',false);
				        var text=$('#Add_EFP_Reim_SkUkW_Textbox').val();
				        checktext(text)
				    } else {
				        $('#Add_EFP_Reim_SkUkW_Textbox').attr('readonly',true);
				        var text=$('#Add_EFP_Reim_SkUkW_Textbox').val();
				        checktext(text)
				    }
			 });

			$('#Add_EFP_Reim_DIVSCSTF_checkbox').on('click',function(){
				   var checked=$('#Add_EFP_Reim_DIVSCSTF_checkbox').is(':checked');
				   var value ="${eligibleaepfe}";
				   if(value!="eligible"){
					   alert("you are not eligible for this additional incentive")
					   //08-09-2020
					   $("#unselect_EFP_Reim_DIVSCSTF_checkbox").addClass("d-select-checkbox");
					   }
				   if(checked==true  && value == "eligible")
				    {   
				        $('#Add_EFP_Reim_DIVSCSTF_Textbox').attr('readonly',false);
				        var text=$('#Add_EFP_Reim_DIVSCSTF_Textbox').val();
				        checktext(text)
				    } else {
				        $('#Add_EFP_Reim_DIVSCSTF_Textbox').attr('readonly',true);
				        var text=$('#Add_EFP_Reim_DIVSCSTF_Textbox').val();
				        checktext(text)
				    }
			 });

			$('#Add_CIS_checkbox').on('click',function(){
				   var checked=$('#Add_CIS_checkbox').is(':checked');
				   var value ="${eligiblecis}";
				   if(value!="eligible"){
					   alert("you are not eligible for this additional incentive")
					   //08-09-2020
					   $("#unselect_CIS_checkbox").addClass("d-select-checkbox");
					   }
				   if(checked==true  && value == "eligible")
				    {   
				        $('#Add_CIS_Textbox').attr('readonly',false);
				        var text=$('#Add_CIS_Textbox').val();
				        checktext(text)
				    } else {
				        $('#Add_CIS_Textbox').attr('readonly',true);
				        var text=$('#Add_CIS_Textbox').val();
				        checktext(text)
				    }
			 });

			$('#Add_IIS_checkbox').on('click',function(){
				   var checked=$('#Add_IIS_checkbox').is(':checked');
				   var value ="${eligiblecis}";
				   if(value!="eligible"){
					   alert("you are not eligible for this additional incentive")
					   //08-09-2020
					   $("#unselect_IIS_checkbox").addClass("d-select-checkbox");
					   }
				   if(checked==true  && value == "eligible")
				    {   
				        $('#Add_IIS_Textbox').attr('readonly',false);
				        var text=$('#Add_IIS_Textbox').val();
				        checktext(text)
				    } else {
				        $('#Add_IIS_Textbox').attr('readonly',true);
				        var text=$('#Add_IIS_Textbox').val();
				        checktext(text)
				    }
			 });
			 

		});

	//for positioning
	$(function() {
	    if('${customlist}'!=null  && '${customlist}'!=''){	       
	   	 $("#customFields").focus();
	   	
	   	 
	    }	      
	    });

	//Sachin
	
	
</script>
<script>
$(function() {
	$("#ISF_Stamp_Duty_EX").change(function() {

	var	sgstExemption = document.getElementById("ISF_Stamp_Duty_EX").value
		
		if (sgstExemption>0){
			$("input[name='ISF_Amt_Stamp_Duty_Reim']").attr("readonly", true);
			}
		else{
			$("input[name='ISF_Amt_Stamp_Duty_Reim']").attr("readonly", false);
			}
	});
});
</script>
<script>
$(function() {
	$("#ISF_Amt_Stamp_Duty_Reim").change(function() {

	var	sgstreim = document.getElementById("ISF_Amt_Stamp_Duty_Reim").value
		
		if (sgstreim>0){
			$("input[name='ISF_Stamp_Duty_EX']").attr("readonly", true);
			}
		else{
			$("input[name='ISF_Stamp_Duty_EX']").attr("readonly", false);
			}
	});
});
</script>
<script>
$(document).ready(function(){

	var	sgstreim = document.getElementById("ISF_Amt_Stamp_Duty_Reim").value
	var	sgstExemption = document.getElementById("ISF_Stamp_Duty_EX").value
	
	if (sgstreim>0){
		$("input[name='ISF_Stamp_Duty_EX']").attr("readonly", true);
		}
	if (sgstExemption>0){
		$("input[name='ISF_Amt_Stamp_Duty_Reim']").attr("readonly", true);
		}
});
</script>
<script type="text/javascript">
function SGSTclaimedNotZero()
{
	var	sgstClaim = document.getElementById("ISF_Claim_Reim").value
	
		if (sgstClaim <= 0) {
			document.getElementById('ISF_claim_reim').innerHTML = "SGST claimed amt should be greater than Zero";
			document.getElementById('ISF_Claim_Reim').focus();
			return false;
		} else {
	        document.getElementById('ISF_claim_reim').innerHTML = "";
	    }
	
}

function StampDutyExeNotZero()
{
	var	stampExe= document.getElementById("ISF_Stamp_Duty_EX").value
	
		if (stampExe <= 0) {
			document.getElementById('ISF_Stamp_Duty_ex').innerHTML = "Stamp Duty Exemption amt should be greater than Zero";
			document.getElementById('ISF_Stamp_Duty_EX').focus();
			return false;
		} else {
	        document.getElementById('ISF_Stamp_Duty_ex').innerHTML = "";
	    }
	
}
function StampDutyReimNotZero()
{
	var	stampReim = document.getElementById("ISF_Amt_Stamp_Duty_Reim").value
	
		if (stampReim <= 0) {
			document.getElementById('ISF_Amt_Stamp_Duty_reim').innerHTML = "Stamp Duty Reim amt should be greater than Zero";
			document.getElementById('ISF_Amt_Stamp_Duty_Reim').focus();
			return false;
		} else {
	        document.getElementById('ISF_Amt_Stamp_Duty_reim').innerHTML = "";
	    }
	
}

function CisNotZero()
{
	var	stampReim = document.getElementById("ISF_Cis").value
	
		if (stampReim <= 0) {
			document.getElementById('ISF_cis').innerHTML = "CIS amt should be greater than Zero";
			document.getElementById('ISF_Cis').focus();
			return false;
		} else {
	        document.getElementById('ISF_cis').innerHTML = "";
	    }
	
}
function IisNotZero()
{
	var	iisAmt = document.getElementById("ISF_Iis").value
	
		if (iisAmt <= 0) {
			document.getElementById('ISF_iis').innerHTML = "IIS amt should be greater than Zero";
			document.getElementById('ISF_Iis').focus();
			return false;
		} else {
	        document.getElementById('ISF_iis').innerHTML = "";
	    }
	
}

function IsLoanNotZero()
{
	var	isLoan = document.getElementById("ISF_IsLoan").value
	
		if (isLoan <= 0) {
			document.getElementById('ISF_isLoan').innerHTML = "IS Loan amt should be greater than Zero";
			document.getElementById('ISF_IsLoan').focus();
			return false;
		} else {
	        document.getElementById('ISF_isLoan').innerHTML = "";
	    }
	
}

function reimDisallowed()
{
	var	reimDisallowed = document.getElementById("ISF_ReimDisallowed").value
	
		if (reimDisallowed <= 0) {
			document.getElementById('ISF_reimDisallowed').innerHTML = "Reim Disallowed amt should be greater than Zero";
			document.getElementById('ISF_ReimDisallowed').focus();
			return false;
		} else {
	        document.getElementById('ISF_reimDisallowed').innerHTML = "";
	    }
	
}

function diffAbled()
{
	var	diffAbled = document.getElementById("ISF_DiffAbled").value
	
		if (diffAbled <= 0) {
			document.getElementById('ISF_diffAbled').innerHTML = "differently abled amt should be greater than Zero";
			document.getElementById('ISF_DiffAbled').focus();
			return false;
		} else {
	        document.getElementById('ISF_diffAbled').innerHTML = "";
	    }
	
}

</script>
<style>
.disabled-input {
	background-color: #e9ecef;
	opacity: 1;
	pointer-events: none;
}

.custom-control-input:checked ~.custom-control-label.d-select-checkbox::before
	{
	border-color: #adb5bd;
	background-color: #ffffff;
}

.custom-checkbox .custom-control-input:checked ~.custom-control-label.d-select-checkbox::after
	{
	background-image: none;
}
</style>
<jsp:include page="Inventivehead.jsp" />
<body class="bottom-bg" onload="reimbursement(); showYesOrNo()">
	<section class="common-form-area">
		<div class="container">
			<!-- Main Title -->
			<div class="inner-banner-text">
				<h1>Incentive Specific Form (ISF)</h1>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="form-card">
						<div class="without-wizard-steps">
							<div class="step-links">
								<h4 class="text-center">
									<strong>Incentive Details</strong>
								</h4>
							</div>
							<div class="isf-form">
								<form:form modelAttribute="incentiveDeatilsForm" name="incentiveDeatilsform" method="post" id="myForm" enctype="multipart/form-data">
									<div id="accordion" class="accordion mt-5 mb-3" >
										<div class="card mb-0 border-0">
											<div class="card-header  mb-4" data-toggle="collapse" href="#collapseOne" data-bs-toggle="collapse"  aria-expanded="true">
												<a class="card-title"> <strong>SGST Reimbursemnt</strong></a>
											</div>
											<div id="collapseOne" class="card-body collapse show" data-parent="#accordion">
												<div class="row">
													<div class="col-sm-12">
														<div class="table-responsive">
															<table class="table table-stripped table-bordered loc-stage-table">
																<tbody>
																	<tr>
																		<td style="width: 70%;">
																			<strong>Amount of SGST claimed for reimbursement</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Claim_Reim" type="text" class="ISF_Reim_SGST form-control" placeholder="Enter Amount INR (Cr)" 
																			onkeyup="SGSTclaimedNotZero();" name="ISF_Claim_Reim" id="ISF_Claim_Reim" onkeypress="return numeric_only (event, this);" value="" maxlength="12"></form:input>
																			<span id="ISF_claim_reim" class="text-danger d-inline-block mt-2"></span> <small class="d-block text-info" id="SGSTwords"></small>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<div class="custom-control custom-checkbox mb-4">
																				<input type="checkbox" class="custom-control-input" id="Add_SCST_checkbox" name="ISF_SGST_Claim_Reim"> <label class="custom-control-label" id="unselect_SCST_checkbox" for="Add_SCST_checkbox">Additional 10% GST where 25% minimum SC/ST workers employed subject to minimum of 400 total workers in industrial undertakings located in Paschimanchal and minimum of 200 total workers in B-P-M</label>
																			</div>
																		</td>
																		<td>
																			<form:input path="ISF_Reim_SCST" type="text" id="Add_SCST_Textbox" class="ISF_Reim_SGST form-control" name="ISF_Reim_SCST" maxlength="12" onkeypress="return numeric_only (event, this);" readonly="true"></form:input>
																			<small class="d-block text-info" id="SCSTwords"></small>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<div class="custom-control custom-checkbox mb-4">
																				<input type="checkbox" class="custom-control-input" id="Add_FW_checkbox" name="ISF_SGST_Claim_Reim"> <label class="custom-control-label" id="unselect_FW_checkbox" for="Add_FW_checkbox">Additional 10% GST where 40% minimum female workers employed subject to minimum of 400 total workers in industrial undertakings located in Paschimanchal and minimum of 200 total workers in B-P-M</label>
																			</div>
																		</td>
																		<td>
																			<form:input path="ISF_Reim_FW" type="text" id="Add_FW_Textbox" maxlength="12" class="ISF_Reim_SGST form-control" name="ISF_Reim_FW" onkeypress="return numeric_only (event, this);" readonly="true"></form:input>
																			<small class="d-block text-info" id="fwwords"></small>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<div class="custom-control custom-checkbox mb-4">
																				<input type="checkbox" class="custom-control-input" id="Add_BPLW_checkbox" name="ISF_SGST_Claim_Reim"> <label class="custom-control-label" id="unselect_BPLW_checkbox" for="Add_BPLW_checkbox">Additional 10% GST where 25% minimum BPL workers employed subject to minimum of 400 total workers in industrial undertakings located in Paschimanchal and minimum of 200 total workers in B-P-M</label>
																			</div>
																		</td>
																		<td>
																			<form:input path="ISF_Reim_BPLW" type="text" class="ISF_Reim_SGST form-control" maxlength="12" name="ISF_Reim_BPLW" id="Add_BPLW_Textbox" onkeypress="return numeric_only (event, this);" readonly="true"></form:input>
																			<small class="d-block text-info" id="bplwwords"></small>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong>Total SGST Reimbursement</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Ttl_SGST_Reim" type="text" class="Total_SGST_Reim form-control" name="ISF_Ttl_SGST_Reim" id="ISF_Ttl_SGST_Reim" ></form:input>
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
											<div class="card-header  mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" data-bs-toggle="collapse"  aria-expanded="true">
												<a class="card-title"> <strong>Stamp Duty Exemption/Reimbursement</strong>
												</a>
											</div>
											<div id="collapseTwo" class="card-body collapse show" data-parent="#accordion">
												<div class="row">
													<div class="col-sm-12">
														<div class="table-responsive">
															<table class="table table-stripped table-bordered loc-stage-table">
																<tbody>
																	<tr>
																		<td style="width: 70%;">
																			<strong>Amount of Stamp Duty Exemption</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Stamp_Duty_EX" id="ISF_Stamp_Duty_EX" type="text" 
																			onkeyup="StampDutyExeNotZero();" class="Stamp_Duty_EX form-control" placeholder="Enter Amount INR (Cr)" name="ISF_Stamp_Duty_EX" maxlength="12" onkeypress="return numeric_only (event, this);" value=""></form:input>
																			<span id="ISF_Stamp_Duty_ex" class="text-danger d-inline-block mt-2"></span> <small class="d-block text-info" id="ISFTwords"></small>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="2">
																			<strong>Or</strong>
																		</td>
																	</tr>
																	<tr>
																		<td style="width: 70%;">
																			<strong>Amount of Stamp Duty Reimbursement</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Amt_Stamp_Duty_Reim" id="ISF_Amt_Stamp_Duty_Reim" 
																			onkeyup="StampDutyReimNotZero();" type="text" class="Stamp_Duty_EX form-control" placeholder="Enter Amount INR (Cr)" name="ISF_Amt_Stamp_Duty_Reim" maxlength="12" onkeypress="return numeric_only (event, this);" value=""></form:input>
																			<span id="ISF_Amt_Stamp_Duty_reim" class="text-danger d-inline-block mt-2"></span> <small class="d-block text-info" id="Reimwords"></small>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<div class="custom-control custom-checkbox mb-4">
																				<input type="checkbox" class="custom-control-input" id="Add_Stamp_Duty_checkbox" name="ISF_Stamp_Duty"> <label class="custom-control-label" id="unselect_Stamp_Duty_checkbox" for="Add_Stamp_Duty_checkbox">Additional Stamp Duty exemption @20% upto maximum of 100% in case of industrial undertakings having 75% equity owned by Divyang/SC/ ST/Females Promoters</label>
																			</div>
																		</td>
																		<td>
																			<form:input path="ISF_Additonal_Stamp_Duty_EX" type="text" id="Add_Stamp_Duty_Textbox" class="Stamp_Duty_EX form-control" name="ISF_Additonal_Stamp_Duty_EX" maxlength="12" onkeypress="return numeric_only (event, this);" readonly="true"></form:input>
																			<small class="d-block text-info" id="EXwords"></small>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong>Total Stamp Duty Exemption/Reimbursement</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Ttl_Stamp_Duty_EX" type="text" class="Total_Stamp_Duty_EX form-control" id="ISF_Ttl_Stamp_Duty_EX"   name="ISF_Ttl_Stamp_Duty_EX"></form:input>
																			<small class="d-block text-info" id="ttlwords"></small>
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
											<div class="card-header   mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" id="efphide"   aria-expanded="true">
												<a class="card-title"> <strong>EPF Reimbursement</strong>
												</a>
											</div>
											<div id="collapseThree" class="card-body collapse show" data-parent="#accordion">
												<div class="row" id="ReimbursementRow">
													<div class="col-sm-12">
														<div class="table-responsive">
															<table class="table table-stripped table-bordered loc-stage-table">
																<tbody>
																	<tr>
																		<td style="width: 70%;">
																			<strong>EPF Reimbursement (100 or more unskilled workers)</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Epf_Reim_UW" type="text" class="EPF_Reim form-control" placeholder="Enter Amount INR (Cr)" name="ISF_Epf_Reim_UW" maxlength="12" onkeypress="return numeric_only (event, this);" value=""></form:input>
																			<span id="ISF_Epf_Reim_uw" class="text-danger d-inline-block mt-2"></span>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<div class="custom-control custom-checkbox mb-4">
																				<input type="checkbox" class="custom-control-input" id="Add_EFP_Reim_SkUkW_checkbox" name="Add_EFP_Reim_SkUkW_checkbox"> <label class="custom-control-label" id="unselect_EFP_Reim_SkUkW_checkbox" for="Add_EFP_Reim_SkUkW_checkbox">Additional 10% EPF Reimbursement (200 direct skilled and unskilled workers)</label>
																			</div>
																		</td>
																		<td>
																			<form:input path="ISF_Add_Epf_Reim_SkUkW" type="text" id="Add_EFP_Reim_SkUkW_Textbox" class="EPF_Reim form-control" name="ISF_Add_Epf_Reim_SkUkW" maxlength="12" onkeypress="return numeric_only (event, this);" readonly="true"></form:input>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<div class="custom-control custom-checkbox mb-4">
																				<input type="checkbox" class="custom-control-input" id="Add_EFP_Reim_DIVSCSTF_checkbox" name="Add_EFP_Reim_DIVSCSTF_checkbox"> <label class="custom-control-label" id="unselect_EFP_Reim_DIVSCSTF_checkbox" for="Add_EFP_Reim_DIVSCSTF_checkbox">Additional 10% EPF Reimbursement upto maximum of 70% in case of industrial undertakings having 75% equity owned by Divyang/SC/CT/Females Promoters</label>
																			</div>
																		</td>
																		<td>
																			<form:input path="ISF_Add_Epf_Reim_DIVSCSTF" type="text" id="Add_EFP_Reim_DIVSCSTF_Textbox" class="EPF_Reim form-control" name="ISF_Add_Epf_Reim_DIVSCSTF" maxlength="12" onkeypress="return numeric_only (event, this);" readonly="true"></form:input>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong>Total EPF Reimbursement</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Ttl_EPF_Reim" type="text" value="0" class="Total_EPF_Reim form-control" name="ISF_Ttl_EPF_Reim"></form:input>
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
											<div class="card-header  mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" data-bs-toggle="collapse"  aria-expanded="true">
												<a class="card-title"> <strong>Interest Subsidies</strong>
												</a>
											</div>
											<div id="collapseFour" class="card-body collapse show" data-parent="#accordion">
												<div class="card-body">
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td style="width: 70%;">
																				<strong>Capital Interest Subsidy</strong>
																			</td>
																			<td>
																				<form:input path="ISF_Cis" id="ISF_Cis" type="text" class="ISF_CIS form-control" 
																				onkeyup="CisNotZero();" placeholder="Enter Amount INR (Cr)" name="ISF_Cis" onkeypress="return numeric_only (event, this);" maxlength="12" value=""></form:input>
																				<span id="ISF_cis" class="text-danger d-inline-block mt-2"></span> <small class="d-block text-info" id="Subsidywords"></small>
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="custom-control custom-checkbox mb-4">
																					<input type="checkbox" class="custom-control-input" id="Add_CIS_checkbox" name="Add_CIS_checkbox"> <label class="custom-control-label" id="unselect_CIS_checkbox" for="Add_CIS_checkbox">Additional Capital Interest Subsidy@2.5% upto maximum of 7.5% in case of industrial undertakings having 75% equity owned by Divyang/SC/CT/Females Promoters</label>
																				</div>
																			</td>
																			<td>
																				<form:input path="ISF_ACI_Subsidy_Indus" type="text" id="Add_CIS_Textbox" class="ISF_CIS form-control" name="ISF_ACI_Subsidy_Indus" maxlength="12" onkeypress="return numeric_only (event, this);" readonly="true"></form:input>
																				<small class="d-block text-info" id="textboxwords"></small>
																			</td>
																		</tr>
																		<tr>
																			<td style="width: 70%;">
																				<strong>Infrastructure Interest Subsidy</strong>
																			</td>
																			<td>
																				<form:input path="ISF_Infra_Int_Subsidy" type="text" id="ISF_Iis" class="ISF_CIS form-control" 
																				onkeyup="IisNotZero();" placeholder="Enter Amount INR (Cr)" name="ISF_Infra_Int_Subsidy" maxlength="12" onkeypress="return numeric_only (event, this);" value=""></form:input>
																				<span id="ISF_iis" class="text-danger d-inline-block mt-2"></span>
																				<small class="d-block text-info" id="intwords"></small>
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="custom-control custom-checkbox mb-4">
																					<input type="checkbox" class="custom-control-input" id="Add_IIS_checkbox" name="Add_IIS_checkbox"> <label class="custom-control-label" id="unselect_IIS_checkbox" for="Add_IIS_checkbox">Additional Infrastructure Interest Subsidy @2.5% upto maximum of 7.5% in case of industrial undertakings having 75% equity owned by Divyang/SC/CT/Females Promoters</label>
																				</div>
																			</td>
																			<td>
																				<form:input path="ISF_AII_Subsidy_DIVSCSTF" type="text" id="Add_IIS_Textbox" class="ISF_CIS form-control" name="ISF_AII_Subsidy_DIVSCSTF" maxlength="12" onkeypress="return numeric_only (event, this);" readonly="true"></form:input>
																				<small class="d-block text-info" id="iisTwords"></small>
																			</td>
																		</tr>
																		<tr>
																			<td style="width: 70%;">
																				<strong>Interest Subsidy on loans for industrial research, quality improvement, etc.</strong>
																			</td>
																			<td>
																				<form:input path="ISF_Loan_Subsidy" type="text" class="ISF_CIS form-control" id="ISF_IsLoan"
																				onkeyup="IsLoanNotZero();" placeholder="Enter Amount INR (Cr)" name="ISF_Loan_Subsidy" maxlength="12" onkeypress="return numeric_only (event, this);" value=""></form:input>
																				<span id="ISF_isLoan" class="text-danger d-inline-block mt-2"></span>
																				<small class="d-block text-info" id="loanwords"></small>
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<strong>Total Interest Subsidies</strong>
																			</td>
																			<td>
																				<form:input path="ISF_Total_Int_Subsidy" type="text" class="Total_ISF_CIS form-control"  name="ISF_Total_Int_Subsidy"></form:input>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="card-header  mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseFive"  aria-expanded="true">
												<a class="card-title"> <strong>Other Incentives</strong>
												</a>
											</div>
											<div id="collapseFive" class="card-body collapse show" data-parent="#accordion" >
												<div class="card-body">
													<div class="row">
														<div class="col-sm-12">
															<div class="table-responsive">
																<table class="table table-stripped table-bordered loc-stage-table">
																	<tbody>
																		<tr>
																			<td style="width: 70%;">Reimbursement of Disallowed Input Tax Credit on plant, building materials, and other capital goods.</td>
																			<td>
																				<form:input path="ISF_Tax_Credit_Reim" type="text" class="Other_Incentives form-control" 
																				id="ISF_ReimDisallowed" onkeyup="reimDisallowed();" placeholder="Enter Amount INR (Cr)" name="ISF_Tax_Credit_Reim" maxlength="12" onkeypress="return numeric_only (event, this);"></form:input>
																				<span id="ISF_reimDisallowed" class="text-danger d-inline-block mt-2"></span>
																				<small class="d-block text-info" id="incentivewords"></small>
																			</td>
																		</tr>
																		<tr class="condition-on-electric">
																			<td>Exemption from Electricity Duty from captive power for self-use</td>
																			<td>
																				<form:input path="ISF_EX_E_Duty" type="text" class="Other_Incentives form-control" placeholder="Enter Amount INR (Cr)" name="ISF_EX_E_Duty" maxlength="12" onkeypress="return numeric_only (event, this);"></form:input>
																			</td>
																		</tr>
																		<tr class="condition-on-electric">
																			<td>Exemption from Electricity duty on power drawn from power companies</td>
																			<td>
																				<form:input path="ISF_EX_E_Duty_PC" type="text" class="Other_Incentives form-control" placeholder="Enter Amount INR (Cr)" name="ISF_EX_E_Duty_PC" maxlength="12" onkeypress="return numeric_only (event, this);"></form:input>
																			</td>
																		</tr>
																		<tr class="condition-on-electric">
																			<td>Exemption from Mandi Fee</td>
																			<td>
																				<form:input path="ISF_EX_Mandee_Fee" type="text" class="Other_Incentives form-control" placeholder="Enter Amount INR (Cr)" name="ISF_EX_Mandee_Fee" maxlength="12" onkeypress="return numeric_only (event, this);"></form:input>
																			</td>
																		</tr>
																		<tr>
																			<td>Industrial units providing employment to differently abled workers will be provided payroll assistance of Rs. 500 per month for each such worker.</td>
																			<td>
																				<form:input path="ISF_Indus_Payroll_Asst" type="text" class="Other_Incentives form-control" 
																				id="ISF_DiffAbled" onkeyup="diffAbled();" placeholder="Enter Amount INR (Cr)" maxlength="12" name="ISF_Indus_Payroll_Asst" onkeypress="return numeric_only (event, this);"></form:input>
																				<span id="ISF_diffAbled" class="text-danger d-inline-block mt-2"></span>
																				<small class="d-block text-info" id="payrollwords"></small>
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<strong>Total Other Incentives</strong>
																			</td>
																			<td>
																				<form:input path="Total_Other_Incentive" type="text" class="Total_Other_Incentives form-control"  name="Total_Other_Incentive"></form:input>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label><strong>Aggregate Quantum of Fiscal Benefits </strong></label>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<input type="number" readonly="readonly" class="form-control all_total" value='0'> <span id="ISF_Cstm_Inc_status" class="text-danger"></span>
											</div>
										</div>
									</div>
									<div class="row condition-on-super-mega">
										<div class="col-sm-12">
											<div class="custom-control form-group custom-radio custom-control-inline pl-0 ">
												<label class=""><strong>Do you want to avail customised incentives </strong></label>
											</div>
											<div class="custom-control custom-radio custom-control-inline">
												<form:radiobutton path="ISF_Cstm_Inc_Status" class="custom-control-input" id="CstmIncStatusYes" name="ISF_Cstm_Inc_Status" value="Yes"></form:radiobutton>
												<label class="custom-control-label" for="CstmIncStatusYes">Yes</label>
											</div>
											<div class="custom-control custom-radio custom-control-inline">
												<form:radiobutton path="ISF_Cstm_Inc_Status" class="custom-control-input" id="CstmIncStatusNo" name="ISF_Cstm_Inc_Status" value="No"></form:radiobutton>
												<label class="custom-control-label" for="CstmIncStatusNo">No</label>
											</div>
											<span id="ISF_Cstm_Inc_status" class="text-danger"></span>
										</div>
									</div>
									<div class="row condition-on-super-mega" id="AddIncentiveType">
										<div class="col-sm-12">
											<div class="form-group">
												<label> Add Incentive Type </label>
												<!-- <input path="addIncentiveType" type="text"
														class="form-control" placeholder="Please Add Incentive"
														id="addIncentiveType" name="addIncentiveType"  onkeypress="return /[a-zедц ]/i.test(event.key)"></input> -->
												<form:hidden path="acdid" name="acdid" id="acdid" value="${acdid}" />
												<form:input path="addIncentiveType" type="text" class="form-control" placeholder="Please Add Incentive" id="addIncentiveType" name="addIncentiveType" onkeypress="return /[a-zедц ]/i.test(event.key)"></form:input>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<label>Add Amount</label>
												<!-- 	<input  path="addAmt" type="text" maxlength="12"
														class="form-control" placeholder="Enter Amount INR (Cr)"
														name="addAmt" onkeypress="return numeric_only (event, this);"></input> -->
												<form:input path="addAmt" type="text" maxlength="12" class="form-control" placeholder="Enter Amount INR (Cr)" name="addAmt" onkeypress="return numeric_only (event, this);"></form:input>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<label>Type Details of Customised Incentives</label>
												<!-- <textarea path="typeDtlCusIncentives" class="form-control" name="typeDtlCusIncentives"
														placeholder="Add details here" rows="4"></textarea> -->
												<form:textarea path="typeDtlCusIncentives" class="form-control" name="typeDtlCusIncentives" placeholder="Add details here" maxlength="250" rows="4"></form:textarea>
											</div>
										</div>
									</div>
									<div class="row condition-on-super-mega add-incentive-type">
										<div class="col-sm-12">
											<div class="form-group">
												<label>Do you have any other Additional Request</label>
												<!-- <input  path="othAddRequest"
														type="text" class="form-control" placeholder=""
														name="othAddRequest"></input> -->
												<form:textarea path="othAddRequest" rows="4" class="form-control" placeholder="" maxlength="250" name="othAddRequest"></form:textarea>
											</div>
										</div>
									</div>
									<div class="row condition-on-super-mega add-incentive-type">
										<div class="col-sm-12 text-right">
											<button type="submit" formaction="availCustomisedDetails" onclick="return validationAvailCustomisedDetailsForm()" class="common-btn mt-3 mb-4">Add</button>
										</div>
									</div>
									<div class="row  condition-on-super-mega">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-stripped table-bordered directors-table" id="customFields" tabindex="1">
													<c:if test="${ not empty availCustomisedDetailsList}">
														<thead>
															<tr>
																<th>Incentive Type</th>
																<th>Amount</th>
																<th>Additional Request</th>
																<th>Type Details of Customised Incentives</th>
																<th>Action</th>
															</tr>
														</thead>
													</c:if>
													<tbody>
														<c:forEach var="list" items="${availCustomisedDetailsList}" varStatus="counter">
															<tr>
																<td>${list.addIncentiveType}</td>
																<td>${list.addAmt}</td>
																<td>${list.othAddRequest}</td>
																<td>${list.typeDtlCusIncentives}</td>
																<td class="text-center">
																	<a href="./editAvailCustomisedDetails?editAvailCustomisedRecord=<c:out value="${counter.index}"/>" onclick="return confirm('Are you sure you want to edit Customised Incentive Record?')"> <i class="fa fa-edit text-info"></i>
																	</a> <a href="./removeAvailList?deleteAvailRecord=${counter.index}" onclick="return confirm('Are you sure you want to Delete Customised Incentive Details?')"><i class="fa fa-trash text-danger"></i></a>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									<div class="row  condition-on-super-mega add-incentive-type">
										<div class="col-md-12">
											<div class="form-group">
												<label>Upload Document <small>(In PDF format less than 2 MB)</small> <img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"></label>
												<div class="custom-file">
													<input type="file" class="custom-file-input" id="UploadDocumentISF" name="isfCustIncDocName" onchange="ValidateSize(this)"> <label class="custom-file-label" for="UploadDocumentISF" id="UploadDocumentISFId">Choose file</label> <span id="UploadDocumentisf" class="text-danger d-inline-block mt-2"></span>
												</div>
											</div>
										</div>
									</div>
									<!-- </div> -->
									<div class="row" id="ReimbursementRow1">
										<div class="col-sm-12">
											<div class="form-group">
												<label>Do you have any other Additional Request</label>
												<!-- <input  path="othAddRequest"
														type="text" class="form-control" placeholder=""
														name="othAddRequest"></input> -->
												<form:textarea path="othAddRequest1" rows="4" class="form-control" placeholder="" maxlength="250" name="othAddRequest1"></form:textarea>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<label id="note"> </label>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-4">
											<a href="./skilUnskEmplDet" onclick="return confirm('Are you sure you want to go to Proposed Employment Details?')" class="common-default-btn mt-3">Back</a>
										</div>
										<div class="col-sm-8 text-right">
											<a href="./previewAfIfForm" target="_blank" class="common-green-btn mt-3">Preview of Application Form</a>
											<button type="submit" formaction="saveIncentiveDetails" onclick="confirm('Are you sure, You want to save Incentive Details record.'); ValidateSize(this) " class="common-btn mt-3">Save</button>
											<!-- <div class="form-group mb-3">
												<input type="text" class="form-control" name="ControlID"
													value="GnhleDDJQMRsFCammjZ70g==" hidden="true">
											</div>
											<div class="form-group mb-3">
												<input type="text" hidden="true"
													value="Pa3prT7oe57OkAcz//wiUM5Iwh7TWkD6zZKaG/JfxZU="
													class="form-control" name="UnitID">
											</div>
											<div class="form-group mb-2">
												<input type="text" hidden="true"
													value="DBQqcIpczI4aYDT5OsZHUA==" class="form-control"
													name="ServiceID">
											</div>
											<div class="form-group mb-2">
												<input type="text" hidden="true"
													value="EhulCUuPTZw9dGrbaDU5HA==" class="form-control"
													name="Dept_Code">
											</div>
											<div class="form-group mb-2">
												<input type="text" hidden="true"
													value="2nTevOJovqF+KVNBAYJs4/jp8EukuL5LmbbGDrjBVL/OSMIe01pA+s2SmhvyX8WV"
													class="form-control" name="PassSalt">
											</div>


											<button type="submit"
												formaction="http://72.167.225.87/testing_nmswp/nmmasters/Entrepreneur_Bck_page.aspx"
												onclick="return incentiveDeatilsSubmitForm()"
												class="common-btn mt-3">Submit</button>
 -->
											<button type="submit" formaction="submitIncentiveDetails" onclick="return incentiveDeatilsSubmitForm()" class="common-btn mt-3">Submit</button>
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
	<!-- 	<script type="text/javascript">
	/* function validationAvailCustomisedDetailsForm()
{			
/* 	var letters = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/;
	var addIncentiveType = document.getElementById('addIncentiveType').value;
	if (addIncentiveType == null || addIncentiveType == '') {
		document.getElementById('AddIncentiveType').innerHTML = "Add Incentive Type is Required.";
		document.getElementById('addIncentiveType').focus();
		return false;
	}
	if (!letters.test(directorName)) { 	
		document.getElementById('AddIncentiveType').innerHTML = "Add Incentive Type only Character.";
		document.getElementById('addIncentiveType').focus();
		return false;
	} */
	
}

/* function validationFinalDetails()

{
	 /* <c:if test="${empty businessEntityDetailsId }">
	   validationBusinessEntityAddress();
    return false
    </c:if> */
	/*  <c:if test="${empty availCustomisedDetailsList}">
	 validationAvailCustomisedDetailsForm();
    return false
    </c:if>

    var CstmIncStatus = document.incentiveDeatilsform.CstmIncStatusYes.value;
	if(CstmIncStatus ==='Yes'){
	var UploadDocumentISF = document.incentiveDeatilsform.UploadDocumentISF.value;
	if (UploadDocumentISF == null || UploadDocumentISF == '') {
		document.getElementById('UploadDocumentisf').innerHTML = "Upload Document is Required";
		document.incentiveDeatilsform.UploadDocumentISF.focus();
		return false;
	}
	var ext = UploadDocumentISF.split('.').pop();
	if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('UploadDocumentisf').innerHTML = "Please Upload Document in PDF Format";
		document.incentiveDeatilsform.UploadDocumentISF.focus();
		return false;
	}
  }	
	var r = confirm("Press Ok for Final Submission! ");

	if (r == true) {
		alert(" Your Application Form is Submitted");
		// return false
		window.location.href = 'https://niveshmitra.up.nic.in/Default.aspx';
	} else {
		return false
	}
} 
 */ </script> -->