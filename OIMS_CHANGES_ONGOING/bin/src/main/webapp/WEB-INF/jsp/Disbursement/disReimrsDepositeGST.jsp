<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
	if ("${disReimbrsDepositeGST.financialYear}" == null || "${disReimbrsDepositeGST.financialYear}" == '') {
		  $("#financialYear option").removeAttr("selected");
	}
	
});


</script>
<style>
div#tabledataid_row .table-responsive.mt-3.cp-SGST.add-row-new tr th {
    min-width: 200px;
}

div#tabledataid_row .table-responsive.mt-3.cp-SGST.add-row-new {
    overflow-x: scroll;
    width: auto;
}
div#tabledataid_row tr td input {
    width: 175px !important;
}
div#tabledataid_row .table-responsive.mt-3.cp-SGST.add-row-new tr:first-child  th:last-child {
    max-width: 100px !important;
    min-width: 100px !important;
}
</style>

<script type="text/javascript">

$('.add-save-data-gst').click(function() { 
    $(".add-row-new table tbody").append('<tr><td><input type="text" class="form-control" name=""></td></td><td><input type="text" placeholder="" class="form-control" name=""></td><td><input type="text" placeholder="" class="form-control" name=""></td><td><input type="text" placeholder="" class="form-control" name=""></td><td><input type="text" placeholder="" class="form-control" name=""></td><td><input type="text" placeholder="" class="form-control" name=""></td><td class="text-center"><a href="#" class="action-btn"><i class="fa fa-edit text-info"></i></a><a href="javascript:void();" class="action-btn delete-row-new"><i class="fa fa-trash text-danger"></i></a></td></tr>')    
});
$(".add-row-new table tbody").on('click', '.delete-row-new', function() {
    $(this).parent().parent().remove();
    // location.reload(true);
});
</script>

<script type="text/javascript">
$(document).ready(function() {
    console.log( "document loaded" );
    if ("${disReimbrsDepositeGST.relevantDocbase64File}" != null
			|| "${disReimbrsDepositeGST.relevantDocbase64File}" != '') {
    	document.getElementById('choosefileRelevantDoc').innerHTML = '${disReimbrsDepositeGST.relevantDoc}';
		document.getElementById("RelevantDoc").src = "data:image/png;base64,${disReimbrsDepositeGST.relevantDocbase64File}";
    	
	} 
	
	if ("${disReimbrsDepositeGST.auditedAccountsDocbase64File}" != null
			|| "${disReimbrsDepositeGST.auditedAccountsDocbase64File}" != '') {
		document.getElementById('choosefileauditedAccounts').innerHTML = '${disReimbrsDepositeGST.auditedAccounts}';
		document.getElementById("auditedAccounts").src = "data:image/png;base64,${disReimbrsDepositeGST.auditedAccountsDocbase64File}";
	} 


	if ("${disReimbrsDepositeGST.GSTAuditDocbase64File}" != null
			|| "${disReimbrsDepositeGST.GSTAuditDocbase64File}" != '') {
		document.getElementById('choosefileGSTAudit').innerHTML = '${disReimbrsDepositeGST.GSTAudit}';
		document.getElementById("GSTAudit").src = "data:image/png;base64,${disReimbrsDepositeGST.GSTAuditDocbase64File}";
	} 

	if ("${disReimbrsDepositeGST.CACertificateDocbase64File}" != null
			|| "${disReimbrsDepositeGST.CACertificateDocbase64File}" != '') {
		document.getElementById('choosefileCACertificate').innerHTML = '${disReimbrsDepositeGST.CACertificate}';
		document.getElementById("CACertificate").src = "data:image/png;base64,${disReimbrsDepositeGST.CACertificateDocbase64File}";
	}
});
</script>

<script type="text/javascript">
$(document).ready(function(){
		$('#verified:checked').prop('checked',false);
});
</script>

<script type="text/javascript">
function ReimbrsmentGST() {
	var gstComrTaxDept = document.getElementById('gstComrTaxDept').value;
	if (gstComrTaxDept == null || gstComrTaxDept == '') {
		document.getElementById('GSTComrTaxDept').innerHTML = "GSTIN issued by Commercial Taxes Department is Required";
		document.getElementById('gstComrTaxDept').focus();
		return false;
	} else {
		document.getElementById('GSTComrTaxDept').innerHTML = " ";
		}

	<c:if test="${empty disReimbrsGSTTableList}">
	 // alert('Please Fill the Details of tax paid under GST Table.');
	  document.getElementById("tabledataid_row").scrollIntoView();	
	  document.getElementById('gstTable').innerHTML = "Please Fill the Details of tax paid under GST Table.";
	  document.getElementById('gstTable').style.cssText = "color: red !important";
   return false
   </c:if>
   
	 if(!DocumentRelevantDoc(document.getElementById('RelevantDoc'))){  
			return false;
		}

		if(!DocumentauditedAccountsDoc(document.getElementById('auditedAccounts'))){
			return false;
		}

		if(!DocumentGSTAuditDoc(document.getElementById('GSTAudit'))){
			return false;
		}

		if(!DocumentCACertificateDoc(document.getElementById('CACertificate'))){
			return false;
		}

		
	     
	if (!$('#verified').is(':checked')) {
		alert(' Please select/check the Declaration');
		$("#verified").focus();
		return false;
	}

	var r = confirm("Are you Sure want to Submit the Form?");
	
	if (r == true) {
		alert("Application Form is Submitted Succesfully");
	} else {
		return false
	}

	 function disbCapInvLCTotal() {
		   var sum = 0;
		   $(".is-numeric").each(function() {
		   //alert("hi");
		   sum += +$(this).val();
		   });
		   $(".totalEPF", this).val(sum);
		   //alert(sum);
		   }

		   $(document).ready(disbCapInvLCTotal);
		   $(document).on("keyup", disbCapInvLCTotal);
		   $(document).on("change", disbCapInvLCTotal); 
}

function ReimbrsmentGSTTable() {
	var gstComrTaxDept = document.getElementById('financialYear').value;
	if (gstComrTaxDept == null || gstComrTaxDept == '') {
		document.getElementById('finYerFF').innerHTML = "Financial Year/Duration/Period is Required";
		document.getElementById('financialYear').focus();
		return false;
	} else {
		document.getElementById('finYerFF').innerHTML = " ";
		}
	
	var gstComrTaxDept = document.getElementById('sgstAmtQyr').value;
	if (gstComrTaxDept == null || gstComrTaxDept == '') {
		document.getElementById('sgstAmtQyrSS').innerHTML = "Amount Of SGST Quarterly is Required";
		document.getElementById('sgstAmtQyr').focus();
		return false;
	} else {
		document.getElementById('sgstAmtQyrSS').innerHTML = " ";
		}

	var amtNetSgst = document.getElementById('amtNetSgst').value;
	if (amtNetSgst == null || amtNetSgst == '') {
		document.getElementById('AmtNetSgst').innerHTML = "Amount of net SGST is Required";
		document.getElementById('amtNetSgst').focus();
		return false;
	} else {
		document.getElementById('AmtNetSgst').innerHTML = " ";
	} 

	var gstComrTaxDept = document.getElementById('ttlAmtSgstReim').value;
	if (gstComrTaxDept == null || gstComrTaxDept == '') {
		document.getElementById('ttlAmtSgstReimRR').innerHTML = "Total Amount SGST Reim is Required";
		document.getElementById('ttlAmtSgstReim').focus();
		return false;
	} else {
		document.getElementById('ttlAmtSgstReimRR').innerHTML = " ";
		}
	
	var amtTaxDeptGst = document.getElementById('amtAdmtTaxDeptGst').value;
	if (amtTaxDeptGst == null || amtTaxDeptGst == '') {
		document.getElementById('AmtAdmtTaxDeptGst').innerHTML = "Amount of admitted Tax is Required";
		document.getElementById('amtAdmtTaxDeptGst').focus();
		return false;
	} else {
		document.getElementById('AmtAdmtTaxDeptGst').innerHTML = " ";
	} 

	var eligbAmountDepo = document.getElementById('eligbAmtDepo').value;
	if (eligbAmountDepo == null || eligbAmountDepo == '') {
		document.getElementById('eligbAmtDepoMsg').innerHTML = "Eligible amount of deposited GST is Required";
		document.getElementById('eligbAmtDepo').focus();
		return false;
	} else {
		document.getElementById('eligbAmtDepoMsg').innerHTML = " ";
		}
	
}
</script>

<script>
function DocumentRelevantDoc(file) {
	
	 var RelevantDoc = document.getElementById('RelevantDoc').value;
	 var fileSize = document.getElementById("RelevantDoc").files[0];
	 var maxSize = '5000';

	 if('${disReimbrsDepositeGST.relevantDocbase64File}' != '' && typeof fileSize === "undefined"){
			document.getElementById('choosefileRelevantDoc').innerHTML = '${disReimbrsDepositeGST.relevantDoc}';
			document.getElementById("RelevantDoc").src = "data:image/png;base64,${disReimbrsDepositeGST.relevantDocbase64File}";
			document.getElementById('RelevantDocMsg').innerHTML = "";
			return true;
		} 
		
	 if(RelevantDoc!=null && RelevantDoc!=''){
	 var ext = RelevantDoc.split('.').pop();
	 if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('RelevantDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
		document.getElementById('RelevantDoc').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
   if (FileSize >5) {
   	document.getElementById('RelevantDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
   	document.getElementById('RelevantDoc').focus();
		return false;
   }
	 }
	 if(RelevantDoc ==='' ||RelevantDoc === null  ){
		document.getElementById('RelevantDocMsg').innerHTML = "Certificate issued by Competent Authority document is Required.";
		document.getElementById('RelevantDoc').focus();
		return false;
	  }
		    document.getElementById('RelevantDocMsg').innerHTML = "";
		    return true;
}

function DocumentauditedAccountsDoc(file) {
	
	 var auditedAccounts = document.getElementById('auditedAccounts').value;
	 var fileSize = document.getElementById("auditedAccounts").files[0];
	 var maxSize = '5000';

	 if('${disReimbrsDepositeGST.auditedAccountsDocbase64File}' != '' && auditedAccounts == ''){
		 document.getElementById('choosefileauditedAccounts').innerHTML = '${disReimbrsDepositeGST.auditedAccounts}';
			document.getElementById("auditedAccounts").src = "data:image/png;base64,${disReimbrsDepositeGST.auditedAccountsDocbase64File}";
			 document.getElementById('auditedAccountsMsg').innerHTML = "";
			return true;
		}
		
	 if(auditedAccounts!=null && auditedAccounts!=''){
	 var ext = auditedAccounts.split('.').pop();
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
		document.getElementById('auditedAccountsMsg').innerHTML = "Unit level audited accounts document is Required.";
		document.getElementById('auditedAccounts').focus();
		return false;
	  }
		    document.getElementById('auditedAccountsMsg').innerHTML = "";
		    return true;
}

function DocumentGSTAuditDoc(file) {
	
	 var GSTAudit = document.getElementById('GSTAudit').value;
	 var fileSize = document.getElementById("GSTAudit").files[0];
	 var maxSize = '5000';

	 if('${disReimbrsDepositeGST.GSTAuditDocbase64File}' != '' && GSTAudit == ''){
		 document.getElementById('choosefileGSTAudit').innerHTML = '${disReimbrsDepositeGST.GSTAudit}';
			document.getElementById("GSTAudit").src = "data:image/png;base64,${disReimbrsDepositeGST.GSTAuditDocbase64File}";
			 document.getElementById('GSTAuditMsg').innerHTML = "";
			return true;
		}
		
	 if(GSTAudit!=null && GSTAudit!=''){
	 var ext = GSTAudit.split('.').pop();
	 if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('GSTAuditMsg').innerHTML = "Please Upload Support documnet in PDF Format";
		document.getElementById('GSTAudit').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
  if (FileSize >5) {
  	document.getElementById('GSTAuditMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
  	document.getElementById('GSTAudit').focus();
		return false;
  }
  
	} else{
		document.getElementById('GSTAuditMsg').innerHTML = "GST Audit Report document is Required.";
		document.getElementById('GSTAudit').focus();
		return false;
	  }
		    document.getElementById('GSTAuditMsg').innerHTML = "";
		    return true;
}

function DocumentCACertificateDoc(file) {
	
	 var CACertificate = document.getElementById('CACertificate').value;
	 var fileSize = document.getElementById("CACertificate").files[0];
	 var maxSize = '5000';

	 if('${disReimbrsDepositeGST.CACertificateDocbase64File}' != '' && CACertificate == ''){
		 document.getElementById('choosefileCACertificate').innerHTML = '${disReimbrsDepositeGST.CACertificate}';
			document.getElementById("CACertificate").src = "data:image/png;base64,${disReimbrsDepositeGST.CACertificateDocbase64File}";
			document.getElementById('CACertificateMsg').innerHTML = "";
			return true;
		}
		
	 if(CACertificate!=null && CACertificate!=''){
	 var ext = CACertificate.split('.').pop();
	 if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('CACertificateMsg').innerHTML = "Please Upload Support documnet in PDF Format";
		document.getElementById('CACertificate').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
 if (FileSize >5) {
 	document.getElementById('CACertificateMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
 	document.getElementById('CACertificate').focus();
		return false;
 }
 
	} else{
		document.getElementById('CACertificateMsg').innerHTML = "CA Certificate document is Required.";
		document.getElementById('CACertificate').focus();
		return false;
	  }
		    document.getElementById('CACertificateMsg').innerHTML = "";
		    return true;
}


</script>
<script type="text/javascript">
function IsGSTIN(inputtext)
{
	var gstin = document.getElementById("gstComrTaxDept").value;
	if (gstin == null || gstin == '') {
		document.getElementById('GSTComrTaxDept').innerHTML = "Please enter GSTIN number.";
		document.getElementById('GSTComrTaxDept').style.cssText = "color: red !important";
		document.getElementById('gstComrTaxDept').focus();
		return false;
	}

	if (/^\d{2}[A-Z]{5}\d{4}[A-Z]{1}[A-Z\d]{1}[Z]{1}[A-Z\d]{1}$/.test(gstin)) {
		document.getElementById('GSTComrTaxDept').innerHTML = "Valid GSTIN number.";
		document.getElementById('GSTComrTaxDept').style.cssText = "color: green !important";
		return true;
	} else {
		document.getElementById('GSTComrTaxDept').innerHTML = "Please enter a valid GSTIN number.";
		document.getElementById('GSTComrTaxDept').style.cssText = "color: red !important";
		document.getElementById('gstComrTaxDept').focus();
		return false
	}

}
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
			<div class="col-sm-12">
				<div class="form-card">
					<div class="without-wizard-steps">
						<h4 class="card-title mb-4 mt-4 text-center">Reimbursement of
							Deposit GST</h4>
						<div class="isf-form">
							<form:form class="mt-4" modelAttribute="disReimbrsDepositeGST"
								method="post" action="disReimbrsDepositeGSTSave"
								enctype="multipart/form-data">

								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Details of tax paid under GST
											Act</h3>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-5">
										<div class="form-group">
											<label>GSTIN issued by Commercial Taxes Department <span>*</span>
											</label>
										</div>
									</div>
									<div class="col-sm-7">
										<div class="form-group">
											<form:input path="gstComrTaxDept" type="text"
												class="form-control numeric" id="gstComrTaxDept"
												name="gstComrTaxDept" maxlength="15"
												onkeyup="return IsGSTIN(event)"
												oninput="this.value = this.value.toUpperCase()"></form:input>
											<span id="GSTComrTaxDept" class="text-danger"></span>
										</div>
									</div>
								</div>
								<div class="row" id="tabledataid_row">
									<div class="col-sm-12">
										<div class="table-responsive mt-3 cp-SGST add-row-new">
											<table class="table table-stripped table-bordered">
												<thead>
													<tr>
														<span id="gstTable" class="text-danger"></span>
														<th rowspan="2">Financial Year/ Duration/ Period</th>
														<th colspan="2" width="20%">Duration/ Period</th>
														
														
														<th rowspan="2">Amount of Quarterly SGST paid during the
															Financial Year</th>
														<th rowspan="2">Amount of net SGST paid during Financial Year</th>
														<th rowspan="2">Total Amount of SGST Claimed for Reimbursement</th>
														<th rowspan="2">Amount of admitted Tax on manufacture goods under
															deposited GST for FY</th>
														<th rowspan="2">Amount eligible for deposited GST for
															reimbursement of FY<a href="javascript:void(0);"
															class="remove-row" data-toggle="tooltip" title=""
															data-original-title="Provision as per policy - Annual Percentage of GST Reimbursement 70% for Mega/ Mega Plus/ Super Mega category Industries for period of 10 years.
                              Provision as per rule -  Period - 10 years; Annual Ceiling as percentage of Admissible Capital Investment - 20%; Admissible Capital Investment -  Paschimanchal-100%
                              "><i
																class="fa fa-info-circle text-info"></i></a>
														</th>
														<th class="text-center" rowspan="2">Action</th>
													</tr>
													<tr>
														<th>From</th>
																<th>To</th>
															
														</tr>
												</thead>
												<tbody>
													<tr>
														<%-- <td style="width: 10%;"><form:input path="financialYear" type="text"
																class="form-control financial_Year" id="financialYear"
																name="financialYear"></form:input> <span id="finYerFF"
															class="text-danger"></span></td> --%>
															
															
															<td style="width:30%;"><form:select path="financialYear"
																	type="text" placeholder="Enter Year"
																	class="form-control financial-year" id="financialYear"
																	name="financialYear">
																	<form:option value="" selected="selected">-Select Financial Year-</form:option>
																	<form:option value="${disReimbrsDepositeGST.financialYear}"></form:option> 
																</form:select> <span id="finYerFF" class="text-danger"></span></td>
															
															
															<td style="width:30%;"><form:input path="durationPeriodDtFr" type="date"
																class="form-control" id="durationPeriodIdFr"
																name="durationPeriodDtFr"></form:input> <span id="durationPeriodDtFrMsg"
															class="text-danger"></span></td>
															<td style="width: 30%;"><form:input path="durationPeriodDtTo" type="date"
																class="form-control" id="durationPeriodIdTo"
																name="durationPeriodDtTo"></form:input> <span id="durationPeriodDtToMsg"
															class="text-danger"></span></td>
															
													
															
														<td style="width: 15%;"><form:input path="amtNetSgstQYr" type="text"
																placeholder="Enter Amount in INR"
																class="form-control is-numeric" id="sgstAmtQyr"
																maxlength="12" onkeypress="return IsNumeric(event)"></form:input>
															<span id="sgstAmtQyrSS" class="text-danger"></span> <small
															class="words text-info"></small></td>
														<td style="width: 15%;"><form:input path="amtNetSgst" type="text"
																placeholder="Enter Amount in INR"
																class="form-control is-numeric" id="amtNetSgst"
																name="amtNetSgst" maxlength="12"
																onkeypress="return IsNumeric(event)"></form:input> <span
															id="AmtNetSgst" class="text-danger"></span> <small
															class="words text-info"></small></td>
														<td style="width: 14%;"><form:input path="ttlSgstReim" type="text"
																placeholder="Enter Amount in INR"
																class="form-control is-numeric" id="ttlAmtSgstReim"
																name="amtNetSgst" maxlength="12"
																onkeypress="return IsNumeric(event)"></form:input> <span
															id="ttlAmtSgstReimRR" class="text-danger"></span> <small
															class="words text-info"></small></td>
														<td style="width: 14.5%;"><form:input path="amtAdmtTaxDeptGst" type="text"
																placeholder="Enter Amount in INR" maxlength="12"
																class="form-control is-numeric" id="amtAdmtTaxDeptGst"
																name="amtAdmtTaxDeptGst"></form:input> <span
															id="AmtAdmtTaxDeptGst" class="text-danger"></span> <small
															class="words text-info"></small></td>
														<td style="width: 14.5%;"><form:input path="eligbAmtDepo" type="text"
																placeholder="Enter Amount in INR" maxlength="12"
																class="form-control is-numeric" id="eligbAmtDepo"
																name="eligbAmtDepo"></form:input> <span
															id="eligbAmtDepoMsg" class="text-danger"></span> <small
															class="words text-info"></small></td>
														<td style="width: 08%; text-align: center;">
																<c:if test="${empty edit}">
																		<button formaction="addReimbrsGSTTable"
																onclick="return ReimbrsmentGSTTable()"
																class="btn btn-outline-success">Add</button>
																	</c:if>
																	<c:if test="${not empty edit}">
																		<button formaction="addReimbrsGSTTable"
																onclick="return ReimbrsmentGSTTable()"
																class="btn btn-outline-success">Save</button>
																	</c:if>
														</td>
													</tr>

													<c:if test="${not empty disReimbrsGSTTableList}">

														<c:forEach var="rgstTable"
															items="${disReimbrsGSTTableList}" varStatus="counter">
															<!-- Iterating the list using JSTL tag  -->
															<tr>
																<td><input type="text"
																	value="${rgstTable.financialYear}" class="form-control"
																	readonly="readonly"></input></td>
																	<td><input type="text"
																	value="${rgstTable.durationPeriodDtFr}" class="form-control"
																	readonly="readonly"></input></td>
																	<td><input type="text"
																	value="${rgstTable.durationPeriodDtTo}" class="form-control"
																	readonly="readonly"></input>
																	</td>

																<td><input type="text"
																	value="${rgstTable.amtNetSgstQYr}" class="form-control"
																	readonly="readonly"></input></td>

																<td><input type="text"
																	value="${rgstTable.amtNetSgst}" class="form-control"
																	readonly="readonly"></input></td>

																<td><input type="text"
																	value="${rgstTable.ttlSgstReim}" class="form-control"
																	readonly="readonly"></input></td>

																<td><input type="text"
																	value="${rgstTable.amtAdmtTaxDeptGst}"
																	class="form-control" readonly="readonly"></input></td>

																<td><input type="text"
																	value="${rgstTable.eligbAmtDepo}" class="form-control"
																	readonly="readonly"></input></td>

																<td class="text-center">
																	<button class="border-0 bg-white"
																		onclick="return confirm('Are you sure you want to edit Record?')"
																		formaction="${pageContext.request.contextPath }/editRGSTTable?editRecord=${counter.index}"
																		data-toggle="tooltip" title="Edit">
																		<i class="fa fa-edit text-info"></i>
																	</button>
																	<button class="border-0 bg-white"
																		onclick="return confirm('Are you sure? Do you want to delete?')"
																		formaction="${pageContext.request.contextPath }/deleteRgstTable?selectedRow=${counter.index}"
																		data-toggle="tooltip" title="Delete" id="deleteButton">
																		<i class="fa fa-trash text-danger"></i>
																	</button>
																</td>
															</tr>
														</c:forEach>
													</c:if>
												</tbody>
											</table>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Documents Required in Support
											of GST Paid</h3>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<form:label path="relevantDoc">Certificate issued by Competent Authority in
												support of GST received in State Government's Account for
												the relevant period <span>*</span>
												<small>(Upload doc less than 5 MB in PDF)</small>
											</form:label>
											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" id="RelevantDoc" name="relevantDoc"
													onchange="return DocumentRelevantDoc(event)">
												<form:label path="relevantDoc"
													class="custom-file-label file" id="choosefileRelevantDoc"
													for="RelevantDoc">Choose
													file</form:label>
											</div>
											<span id="RelevantDocMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Unit level audited accounts for the relevant
												financial year <span>*</span> <small>(for which GST
													reimbursement is being claimed)</small> <small>(Upload doc
													less than 5 MB in PDF)</small>
											</label>
											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" id="auditedAccounts" name="auditedAccounts"
													onchange="return DocumentauditedAccountsDoc(event)">
												<label class="custom-file-label file"
													id="choosefileauditedAccounts" for="auditedAccounts">Choose
													file</label>
											</div>
											<span id="auditedAccountsMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>GST Audit Report for the relevant financial
												year for the unit <span>*</span> <small>(standalone
													GST statement/report for the unit certified by a Chartered
													Accountant)</small> <small>(Upload doc less than 5 MB in
													PDF)</small>
											</label>
											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" id="GSTAudit" name="GSTAudit"
													onchange="return DocumentGSTAuditDoc(event)"> <label
													class="custom-file-label file" id="choosefileGSTAudit"
													for="GSTAudit">Choose file</label>
											</div>
											<span id="GSTAuditMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>CA Certificate for sales reconciliation of
												Manufactured Goods/Trading goods/Scrap/Stock Transfer and
												SGST paid towards the same separately <span>*</span> <small>(Upload
													doc less than 5 MB in PDF)</small>
											</label>
											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" id="CACertificate" name="CACertificate"
													onchange="return DocumentCACertificateDoc(event)">
												<label class="custom-file-label file"
													id="choosefileCACertificate" for="CACertificate">Choose
													file</label>
											</div>
											<span id="CACertificateMsg" class="text-danger"></span>
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
													id="verified" name="declaration" value="Yes"> <label
													class="custom-control-label" for="verified">The
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
											onclick="return ReimbrsmentGST()">Save</button>

									</div>
								</div>
								<hr>

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