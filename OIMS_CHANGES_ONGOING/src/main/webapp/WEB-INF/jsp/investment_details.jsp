<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="com.webapp.ims.model.PhaseWiseInvestmentDetails" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
<title>Investment Details</title>
<%@ include file="head.jsp"%>
<script src="js/number.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="js/projectDetails.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css" rel="stylesheet" type="text/css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
<style type="text/css">
.error {
	color: red;
}

.disable_a_href {
	pointer-events: none;
}
.input-group-addon {
    padding: 11px 12px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1;
    color: #555;
    text-align: center;
    background-color: #e9ecef;
    border: 1px solid #ccc;
    border-radius: 0 4px 4px 0;
    }
</style>
<script type="text/javascript">

//End Datepicker

$(document).ready(function(){

	
	addPWDetailsDtlsOnLoad();
	getOIADtlsListOnLoad();
	getMOFListOnLoad();
	getPhaseNoList();
	
});

	$(document)
			.ready(
					function() {
						$("#invIndType")
								.on(
										'change',
										function() {

											if ($(this).val() == "Small"
													|| $(this).val() == "Medium") {
												$('input[type="radio"]:enabled')
														.attr('disabled', true);
												$('#regiOrLicense').attr(
														'disabled', false);
												$('#regiOrLicenseFile').attr(
														'disabled', false);
												$(
														'input:radio[name="regiOrLicense"][value="EncloseUAM"]')
														.prop('checked', true);
												document
														.getElementById('regiOrLicenseFile1').innerHTML = '';

											} else if ($(this).val() == "Large"
													|| $(this).val() == "Mega"
													|| $(this).val() == "Mega Plus"
													|| $(this).val() == "Super Mega") {
												$('input[type="radio"]:enabled')
														.attr('disabled', true);
												$('#regiOrLicenseIEM').attr(
														'disabled', false);
												$('#regiOrLicenseFile').attr(
														'disabled', false);
												$(
														'input:radio[name="regiOrLicense"][value="IEMcopy"]')
														.prop('checked', true);
												document
														.getElementById('regiOrLicenseFile1').innerHTML = '';
											}
										});
						
						
					});
</script>
<script>
	$(document)
			.ready(
					function() {
						if ("${investmentDetails.invbase64File}" == null
								|| "${investmentDetails.invbase64File}" == '') {

						} else {
							document.getElementById('chooseInvfile').innerHTML = '${investmentDetails.invFilename}';
						}

						if ("${investmentDetails.invregorlicbase64File}" == null
								|| "${investmentDetails.invregorlicbase64File}" == '') {

						} else {
							document.getElementById('regiOrLicenseFile1').innerHTML = '${investmentDetails.regiOrLicenseFileName}';
						}
					});

	function validatePhasename() {
		return (event.charCode > 64 && event.charCode < 91)
				|| (event.charCode > 96 && event.charCode < 123)
				|| event.charCode == 32 || event.charCode > 47
				&& event.charCode < 58
	}

	/* 	function fetchdata(){
	 $(document).ready(function() {
	 $(document).on("change", ".form-control", function() {
	
	 document.getElementById("invForm").action = "autosaveInvestmentDetails";
	 document.getElementById('invForm').submit();
	 });
	 });
	 }
	 $(document).ready(function(){
	 setInterval(fetchdata,120000);
	 }); */
	 
</script>
<script>
	$(function() {
		$("#brkupmofFile").change(function() {
			if (InvFileExtValidate(this)) {
				if (InvFileSizeValidate(this)) {
					return true;
				}
			}
		});

		 
		$("#regiOrLicenseFile").change(function() {
			if (RegiOrLicenseFileExtValidate(this)) {
				if (RegiOrLicenseSizeValidate(this)) {
					return true;
				}
			}
		});

		function restrictDate() {
			var dtToday = new Date(
					document.getElementById("invCommenceDate").value);
			// var dtToday = new Date();
			var effective = new Date(2017, 06, 13);
			//var effectiveDate = new Date(2014, 06, 13);
			if (dtToday < effective) {

				var month = effective.getMonth() + 1;
				var day = effective.getDate();
				var year = effective.getFullYear();
				if (month < 10)
					month = '0' + month.toString();
				if (day < 10)
					day = '0' + day.toString();

				var maxDate = year + '-' + month + '-' + day;
				//alert(maxDate);
				$('#propCommProdDate').attr('min', maxDate);

			}

			else {
				var month = dtToday.getMonth() + 1;
				var day = dtToday.getDate();
				var year = dtToday.getFullYear();
				if (month < 10)
					month = '0' + month.toString();
				if (day < 10)
					day = '0' + day.toString();

				var maxDate = year + '-' + month + '-' + day;
				//alert(maxDate);
				$('#propCommProdDate').attr('min', maxDate);
			}
		}

		$(document).ready(restrictDate);

		$(document).on("change", restrictDate);

		// File extension validation, Add more extension you want to allow
		var validExt = ".pdf";
		function InvFileExtValidate(fdata) {
			var filePath = fdata.value;
			var getFileExt = filePath.substring(filePath.lastIndexOf('.') + 1)
					.toLowerCase();
			var pos = validExt.indexOf(getFileExt);
			if (pos < 0) {
				alert("Please upload PDF file format.");
				document.getElementById("brkupmofFile").value = "";
				return false;
			} else {
				return true;
			}
		}





		function RegiOrLicenseFileExtValidate(fdata) {
			var filePath = fdata.value;
			var getFileExt = filePath.substring(filePath.lastIndexOf('.') + 1)
					.toLowerCase();
			var pos = validExt.indexOf(getFileExt);
			if (pos < 0) {
				alert("Please upload PDF file format.");
				document.getElementById("regiOrLicenseFile").value = "";
				return false;
			} else {
				return true;
			}
		}
		

		// photo file size validation
		// size in mb

		function InvFileSizeValidate(fdata) {
			var maxSize = '2';//size in KB
			if (fdata.files && fdata.files[0]) {
				var fsize = fdata.files[0].size / (1024 * 1024);
				if (fsize > maxSize) {
					alert('File size should not be more than 2MB. This file size is: '
							+ fsize + " MB");
					document.getElementById("brkupmofFile").value = "";
					return false;
				} else {
					return true;
				}
			}
		}




		function RegiOrLicenseSizeValidate(fdata) {
			var maxSize = '2';//size in KB
			if (fdata.files && fdata.files[0]) {
				var fsize = fdata.files[0].size / (1024 * 1024);
				if (fsize > maxSize) {
					alert('File size should not be more than 2MB. This file size is: '
							+ fsize + " MB");
					document.getElementById("regiOrLicenseFile").value = "";
					return false;
				} else {
					return true;
				}
			}
		}

		

		$(document).ready(function() {
			var r = '${investmentDetails.pwApply}';
			if (r == 'Yes') {
				$(".if-applicable").css("display", "block");
				$('#pwApply').change(function() {
					var r = $("#pwApply").val();
					if (r == "Yes") {
						$('#' + $(this).val()).show();
						$('#pwapply').text('');
					} else {
						// $('#' + $(this).val()).css("display", "none");
						$(".if-applicable").hide();
						$('#pwapply').text('');
					}
				});
			} else {
				$(".if-applicable").hide();
				$('#pwApply').change(function() {
					var r = $("#pwApply").val();
					if (r == "Yes") {
						$('#' + $(this).val()).show();
						$('#pwapply').text('');
					} else {
						// $('#' + $(this).val()).css("display", "none");
						$(".if-applicable").hide();
						$('#pwapply').text('');
					}
				});

			}
			
			
			
		});
	});
</script>

<script type="text/javascript">
	function submitForm()

	{

		var errorCounter = 0;
		if (!validateInvestmentForm()) {

			//document.getElementById('invCommenceDate').focus();
			errorCounter++;
		}
		var d = new Date(2017, 06, 13);
		var investmentDate = new Date(document
				.getElementById("invCommenceDate").value);
		if (investmentDate < d)
			//capinvestAmount()
			if (!capinvestAmount()) {

				document.getElementById('invCommenceDate').focus();
				errorCounter++;
			}

		if (!validateDate()) {

			document.getElementById('invCommenceDate').focus();
			errorCounter++;
		}
		if (!validateDate1()) {
			errorCounter++;
			document.getElementById('propCommProdDate').focus();
		}

		/* if (!calculate()) {
			document.getElementById('invFci').focus();

			errorCounter++;
		} */
		if (errorCounter > 0) {
			return false;
		} else {
			document.getElementById("invForm").submit();

		}
	}

	function validateDate() {
		var investmentDate = new Date(document
				.getElementById("invCommenceDate").value);
		var invIndType = document.getElementById("invIndType").value;
		var d = new Date(2014, 06, 13);

		if (invIndType === 'Small' || invIndType === 'Medium') {

			if (investmentDate < d) {

				$('#invcommencedate').text(
						'Cut off Date should not be before 13-07-2014.');
				/* $('#invcommencedate').text('Cut off Date should not be before '+d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear()); */

				return false;
			} else {
				$('#invcommencedate').text('');
				return true;

			}
		}
		var d1 = new Date(2013, 06, 13);
		if (invIndType === 'Large') {

			if (investmentDate < d1) {

				$('#invcommencedate').text(
						'Cut off Date should not be before 13-07-2013.');
				return false;
			} else {

				$('#invcommencedate').text('');
				return true;
			}

		}

		var d2 = new Date(2014, 06, 13);
		if (invIndType === 'Mega' || invIndType === 'Mega Plus') {

			if (investmentDate < d2) {
				$('#invcommencedate').text(
						'Cut off Date should not be before 13-07-2014.');
				return false;
			} else {
				$('#invcommencedate').text("");
				return true;
			}
		}

		var d3 = new Date(2014, 06, 13);
		if (invIndType === 'Super Mega') {

			if (investmentDate < d3) {

				$('#invcommencedate').text(
						'Cut off Date should not be before 13-07-2014.');
				return false;
			} else {
				$('#invcommencedate').text("");
				return true;
			}
		}

		return true;

	}
	$(document).ready(validateDate);

	$(document).on("change", validateDate);

	//To validate Capital Investment amount
	function capinvestAmount() {
		var invIndType = document.getElementById("invIndType").value;
		var fciAmt = Number(document.getElementById("invFci").value);
		var capinvestAmt = Number(document.getElementById("capInvAmt").value);
		var calAmt = fciAmt * 0.40;

		if (invIndType === 'Mega' || invIndType === 'Mega Plus'
				|| invIndType === 'Super Mega') {
			if (capinvestAmt < calAmt) {
				$('#capinvamt')
						.text(
								"Atleast 40% of eligible Capital Investment shall have to be undertaken after effective date.");
				document.getElementById('capInvAmt').focus();
				return false;

			} else {
				$('#capinvamt').text("");
			}

			if (capinvestAmt > fciAmt) {
				$('#capinvamt')
						.text(
								"Capital Investment made after effective date should not be greater than proposed capital investment.");
				document.getElementById('capInvAmt').focus();
				return false;
			} else {
				$('#capinvamt').text("");
			}
		}

		return true;
	}

	$(document).ready(capinvestAmount);
	$(document).on("change", capinvestAmount);

	function capinveffDate() {
		var cutoffDate = new Date(
				document.getElementById("invCommenceDate").value);
		var invIndType = document.getElementById("invIndType").value;
		var effectiveDate = new Date(2017, 07, 13);

		var d = new Date(2017, 06, 13);
		var investmentDate = new Date(document
				.getElementById("invCommenceDate").value);
		//if(investmentDate < d)

		if ((invIndType === 'Mega' || invIndType === 'Mega Plus' || invIndType === 'Super Mega')
				&& (investmentDate < d)) {
			if (cutoffDate < effectiveDate) {
				$(".capinveffdate").show();
			} else {
				$(".capinveffdate").hide();
			}
		} else {
			$(".capinveffdate").hide();
		}
	}

	$(document).ready(capinveffDate);

	$(document).on("change", capinveffDate);

	/* $(document).ready(function() {
		var capinvestAmt = Number(document.getElementById("capInvAmt").value);
		if (capinvestAmt == null || capinvestAmt == '') {
			$(".capinveffdate").hide();
		} else {
			$(".capinveffdate").show();
		}
	}); */

	function validateDate1() {
		var invIndType = document.getElementById("invIndType").value;
		var investmentDate = new Date(document
				.getElementById("invCommenceDate").value);
		var productionDate = new Date(document
				.getElementById("propCommProdDate").value);

		const diffTime = Math.abs(productionDate - investmentDate);
		const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
		console.log(diffTime + " milliseconds");
		console.log(diffDays + " days");
		if (invIndType === 'Small' || invIndType === 'Medium') {

			if ((diffDays > 1095 || diffDays > 1096)
					|| (productionDate < investmentDate)) {

				$('#propcommproddate')
						.text(
								"Date of Commercial Production should not be later than 3 years from opted cutoff date of investment.");

				return false;
			}

			else {
				$('#propcommproddate').text("");
				return true;
			}

		}
		if (invIndType === 'Large') {

			if ((diffDays > 1461 || diffDays > 1462)
					|| (productionDate < investmentDate)) {

				$('#propcommproddate')
						.text(
								"Date of Commercial Production should not be later than 4 years from opted cutoff date of investment.");

				return false;
			} else {
				$('#propcommproddate').text("");
				return true;
			}

		}

		if (invIndType === 'Mega' || invIndType === 'Mega Plus') {

			if ((diffDays > 1827 || diffDays > 1826)
					|| (productionDate < investmentDate)) {

				$('#propcommproddate')
						.text(
								"Date of Commercial Production should not be later than 5 years from opted cutoff date of investment.");

				return false;
			}

			else {
				$('#propcommproddate').text("");
				return true;
			}

		}

		if (invIndType === 'Super Mega') {

			if ((diffDays > 2557 || diffDays > 2556)
					&& (productionDate > investmentDate)) {

				$('#propcommproddate')
						.text(
								"Date of Commercial Production should not be later than 7 years from opted cutoff date of investment.");

				return false;
			}

			else {
				$('#propcommproddate').text("");
				return true;
			}

		}

		return true;

	}
	$(document).ready(validateDate1);

	$(document).on("change", validateDate1);

	function calculate() {
		var invFci = document.getElementById("invFci").value;
		//var resionName=document.getElementById("resionName").value;
		var invIndType = document.getElementById("invIndType").value;
		var pnm = document.getElementById("invPlantAndMachCost").value;
		var regionName = '${eligiblereg}';
		var districtName = '${eligibledis}';

		if (invIndType === 'Small') {
			if (pnm <= 2500000) {

				$('#invplantandmachcost')
						.text(
								"Plant and Machinary cost should not be less than or equal to 25 lakh for Small Category.");
				return false;
			} else if (pnm > 100000000) {
				$('#invplantandmachcost')
						.text(
								"Plant and Machinary cost should not be greater than 10 crore for Small Category.");
				return false;
			} else {
				$('#invplantandmachcost').text("");
				return true;
			}
		}

		if (invIndType === 'Medium') {
			if (pnm <= 100000000) {

				$('#invplantandmachcost')
						.text(
								"Plant and Machinary cost  should not be less than or equal to 10 crore for Medium Category.");
				return false;
			} else if (pnm > 500000000) {
				$('#invplantandmachcost')
						.text(
								"Plant and Machinary cost should not be greater than 50 crore for Medium Category.");
				return false;
			}

			else {
				$('#invplantandmachcost').text("");
				return true;
			}
		}

		if (invIndType === 'Large')
		{
			if (pnm <= 500000000) {
				$('#invplantandmachcost')
						.text(
								"Plant and Machinary cost  should not be less than or equal to 50 crore for large category.");
				return false;
			} else {
				$('#invplantandmachcost').text("");
				if (regionName === 'Bundelkhand'
						|| regionName === 'Poorvanchal') {
					if (invFci > 1000000000) {

						$('#invfciSpan')
								.text(
										"Proposed capital investment should not be greater than 100 crore.");
						return false;
					} else {
						$('#invfciSpan').text("");
						$('#invplantandmachcost').text("");
					}
				}

				if ((regionName === 'Madhyanchal' || regionName === 'Paschimanchal')
						&& !(districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD')) {
					if (invFci > 1500000000) {

						$('#invfciSpan')
								.text(
										"Proposed capital investment should not be greater than 150 Cr.");
						return false;
					} else {
						$('#invfciSpan').text("");
						$('#invplantandmachcost').text("");
					}
				}

				if (districtName === 'GAUTAM BUDH NAGAR'
						|| districtName === 'GHAZIABAD') {

					if (invFci > 2000000000) {

						$('#invfciSpan')
								.text(
										"Proposed capital investment should not be greater than 200 Cr.");
						return false;
					}
				} else {
					$('#invfciSpan').text("");
					$('#invplantandmachcost').text("");
				}
				$('#invfciSpan').text("");
				$('#invplantandmachcost').text("");
			}

			return true;

		}

		if (invIndType === 'Mega') {

			if (regionName === 'Bundelkhand' || regionName === 'Poorvanchal') {
				if (invFci <= 1000000000) {

					$('#invfciSpan')
							.text(
									"Proposed capital investment  should not be less than or equal to 100 crore.");
					return false;
				} else if (invFci >= 2500000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment should not be greater than or equal to 250 crore.");
					return false;
				} else {
					$('#invfciSpan').text("");
					$('#invplantandmachcost').text("");
					return true;
				}
			}

			if ((regionName === 'Madhyanchal' || regionName === 'Paschimanchal')
					&& !(districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD')) {
				if (invFci < 1500000000) {

					$('#invfciSpan')
							.text(
									"Proposed capital investment  should not be less than 150 crore.");
					return false;
				}

				else if (invFci == 1500000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment  should not be equal to 150 crore.");
					return false;
				}

				else if (invFci > 3000000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment should not be greater than 300 crore.");
					return false;
				}

				else if (invFci == 3000000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment should not be equal to 300 crore.");
					return false;
				}

				else {
					$('#invfciSpan').text("");
					$('#invplantandmachcost').text("");
					return true;
				}
			}

			if (districtName === 'GAUTAM BUDH NAGAR'
					|| districtName === 'GHAZIABAD') {
				if (invFci <= 2000000000) {

					$('#invfciSpan')
							.text(
									"Proposed capital investment  should not be less than or equal to 200 crore.");
					return false;
				} else if (invFci >= 5000000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment should not be greater than or equal to 500 crore");
					return false;
				}
			} else {
				$('#invfciSpan').text("");
				return true;
			}
		}

		if (invIndType === 'Mega Plus') {

			if (regionName === 'Bundelkhand' || regionName === 'Poorvanchal') {
				if (invFci < 2500000000) {

					$('#invfciSpan')
							.text(
									"Proposed capital investment  should not be less than 250 crore.");
					return false;
				} else if (invFci > 5000000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment should not be greater than 500 crore");
					return false;
				}

				else if (invFci == 5000000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment should not be equal to 500 crore.");
					return false;
				}

				else {
					$('#invfciSpan').text("");
					$('#invplantandmachcost').text("");
					return true;
				}
			}

			if ((regionName === 'Madhyanchal' || regionName === 'Paschimanchal')
					&& !(districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD')) {
				if (invFci < 3000000000) {

					$('#invfciSpan')
							.text(
									"Proposed capital investment  should not be less than 300 crore.");
					return false;
				} else if (invFci > 7500000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment should not be greater than 750 crore");
					return false;
				}

				else if (invFci == 7500000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment should not be equal to 750 crore.");
					return false;
				}

				else {
					$('#invfciSpan').text("");
					$('#invplantandmachcost').text("");
					return true;
				}
			}

			if (districtName === 'GAUTAM BUDH NAGAR'
					|| districtName === 'GHAZIABAD') {
				if (invFci < 5000000000) {

					$('#invfciSpan')
							.text(
									"Proposed capital investment  should not be less than 500 crore.");
					return false;
				} else if (invFci >= 10000000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment should not be greater than or equal 1000 crore");
					return false;
				}
			} else {
				$('#invfciSpan').text("");
				$('#invplantandmachcost').text("");

				return true;
			}

		}

		if (invIndType === 'Super Mega') {

			if (regionName === 'Bundelkhand' || regionName === 'Poorvanchal') {

				if (invFci < 5000000000) {
					$('#invfciSpan')
							.text(
									"Proposed capital investment should not be less than or equal to 500 crore.");
					return false;
				} else {
					$('#invfciSpan').text("");
					$('#invplantandmachcost').text("");
					return true;
				}
			}

			if ((regionName === 'Madhyanchal' || regionName === 'Paschimanchal')
					&& !(districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD')) {
				if (invFci < 7500000000) {

					$('#invfciSpan')
							.text(
									"Proposed capital investment  should not be less than 750 crore.");
					return false;
				} else {
					$('#invfciSpan').text("");
					$('#invplantandmachcost').text("");
					return true;
				}

			}

			if (districtName === 'GAUTAM BUDH NAGAR'
					|| districtName === 'GHAZIABAD') {
				if (invFci < 10000000000) {

					$('#invfciSpan')
							.text(
									"Proposed capital investment  should not be less than 1000 crore.");
					return false;
				}

			} else {
				$('#invfciSpan').text("");
				$('#invplantandmachcost').text("");
				return true;
			}

		} else {
			$('#invfciSpan').text("");
			$('#invplantandmachcost').text("");
			return true;
		}
		$('#invfciSpan').text("");
		$('#invplantandmachcost').text("");
		return true;
	}

	$(document).on("change", calculate);
	//$(document).on("keypress", calculate);

	//pwinvestment date calculation

	function pwrestrictDate() {
		var investmentDate = new Date(document
				.getElementById("invCommenceDate").value);
		var invIndType = document.getElementById("invIndType").value;
		if (invIndType === 'Small' || invIndType === 'Medium'
				|| invIndType === 'Large') {
			var dtToday1 = new Date(
					document.getElementById("invCommenceDate").value);
			// var dtToday = new Date();
			var effective = new Date(2017, 06, 13);
			//var effectiveDate = new Date(2014, 06, 13);
			if (dtToday1 < effective) {

				var month = effective.getMonth() + 1;
				var day = effective.getDate();
				var year = effective.getFullYear();
				if (month < 10)
					month = '0' + month.toString();
				if (day < 10)
					day = '0' + day.toString();

				var maxDate = year + '-' + month + '-' + day;
				//alert(maxDate);
				$('#pwPropProductDate').attr('min', maxDate);

			}

			else {

				var month = dtToday1.getMonth() + 1;
				var day = dtToday1.getDate();
				var year = dtToday1.getFullYear();
				if (month < 10)
					month = '0' + month.toString();
				if (day < 10)
					day = '0' + day.toString();

				var maxDate = year + '-' + month + '-' + day;
				//alert(maxDate);
				$('#pwPropProductDate').attr('min', maxDate);
			}
		} else {
			var dtTodaypw = new Date(
					document.getElementById("invCommenceDate").value);
			// var dtToday = new Date();
			var effective = new Date(2017, 06, 13);
			//var effectiveDate = new Date(2014, 06, 13);

			var month = dtTodaypw.getMonth() + 1;
			var day = dtTodaypw.getDate();
			var year = dtTodaypw.getFullYear();
			if (month < 10)
				month = '0' + month.toString();
			if (day < 10)
				day = '0' + day.toString();

			var maxDate = year + '-' + month + '-' + day;
			//alert(maxDate);
			$('#pwPropProductDate').attr('min', maxDate);

		}
	}

	//$(document).ready(pwrestrictDate);

	$(document).on("change", pwrestrictDate);

	function pwvalidateDate1() {
		var invIndType = document.getElementById("invIndType").value;
		var investmentDate = new Date(document
				.getElementById("invCommenceDate").value);
		var productionDate = new Date(document
				.getElementById("pwPropProductDate").value);

		const diffTime = Math.abs(productionDate - investmentDate);
		const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
		console.log(diffTime + " milliseconds");
		console.log(diffDays + " days");
		if (invIndType === 'Small' || invIndType === 'Medium') {

			if ((diffDays > 1095 || diffDays > 1096)
					|| (productionDate < investmentDate)) {

				$('#pwpropproductdate')
						.text(
								"Date of Commercial Production should not be later than 3 years from opted cutoff date of investment.");

				return false;
			}

			else {
				$('#pwpropproductdate').text("");
				return true;
			}

		}
		if (invIndType === 'Large') {

			if ((diffDays > 1461 || diffDays > 1462)
					|| (productionDate < investmentDate)) {

				$('#pwpropproductdate')
						.text(
								"Date of Commercial Production should not be later than 4 years from opted cutoff date of investment.");

				return false;
			} else {
				$('#pwpropproductdate').text("");
				return true;
			}

		}

		if (invIndType === 'Mega' || invIndType === 'Mega Plus') {

			if ((diffDays > 1827 || diffDays > 1826)
					|| (productionDate < investmentDate)) {

				$('#pwpropproductdate')
						.text(
								"Date of Commercial Production should not be later than 5 years from opted cutoff date of investment.");

				return false;
			}

			else {
				$('#pwpropproductdate').text("");
				return true;
			}

		}

		if (invIndType === 'Super Mega') {

			if ((diffDays > 2557 || diffDays > 2556)
					&& (productionDate > investmentDate)) {

				$('#pwpropproductdate')
						.text(
								"Date of Commercial Production should not be later than 7 years from opted cutoff date of investment.");

				return false;
			}

			else {
				$('#pwpropproductdate').text("");
				return true;
			}

		}

		return true;

	}
	$(document).ready(pwvalidateDate1);

	$(document).on("change", pwvalidateDate1);

	function validateInvestmentForm() {
	
	
		
		var landCost = document.getElementById("invLandCost").value;
		var buildingCost = document.getElementById("invBuildingCost").value;
		var plantAndMachCost = document.getElementById("invPlantAndMachCost").value;
		var otherCost = document.getElementById("invOtherCost").value;
		var industryType = document.getElementById("invIndType").value;
		//var totalCost = document.getElementById("invTotalProjCost").value;
		var bkmoffile = document.getElementById("chooseInvfile").innerHTML;
		var phaseApply = document.getElementById("pwApply").value;
		var fixcapinv = document.getElementById("invFci").value
		
		<c:if test="${empty pwInvList}">

		if (industryType == "Small" && plantAndMachCost < 2500000) {
			document.getElementById('invFci').innerHTML = "correct Category";
			document.getElementById('invFci').focus();
			return false;
		} else {
			document.getElementById('invFci').innerHTML = "";
		}

		if (industryType == "Small" && plantAndMachCost > 100000000) {
			document.getElementById('invFci').innerHTML = "Select correct Category";
			document.getElementById('invFci').focus();
			return false;
		} else {
			document.getElementById('invFci').innerHTML = "";
		}

		if (industryType == "Medium" && plantAndMachCost <= 100000000) {
			document.getElementById('invFci').innerHTML = "correct Category";
			document.getElementById('invFci').focus();
			return false;
		} else {
			document.getElementById('invFci').innerHTML = "";
		}

		if (industryType == "Medium" && plantAndMachCost > 500000000) {
			document.getElementById('invFci').innerHTML = "correct Category";
			document.getElementById('invFci').focus();
			return false;
		} else {
			document.getElementById('invFci').innerHTML = "";
		}

		if (industryType == null || industryType == '') {
			document.getElementById('invindtype').innerHTML = "Category of Industrial Undertaking is required";
			document.getElementById('invIndType').focus();
			return false;
		} else {
			document.getElementById('invindtype').innerHTML = "";
		}

		/* if (totalCost == null || totalCost == '') {
			document.getElementById('invtotalprojcost').innerHTML = "Total Cost of Project is required";
			document.getElementById('invTotalProjCost').focus();
			return false;
		} else {
			document.getElementById('invtotalprojcost').innerHTML = "";
		} */
		/* if (phaseApply == null || phaseApply == '') {
			document.getElementById('pwphaseno').innerHTML = "Phasewise Investment is required";
			document.getElementById('phaseNo').focus();
			return false;
		} else {
			document.getElementById('pwphaseno').innerHTML = "";
		} */
		
		if (phaseApply == null || phaseApply == '') {
			document.getElementById('pwapply').innerHTML = "Phasewise Investment is required";
			document.getElementById('pwApply').focus();
			return false;
		} else {
			document.getElementById('pwapply').innerHTML = "";
		}

		/* if (landCost == null || landCost == '') {
			document.getElementById('invlandcost').innerHTML = "Land Cost is required";
			document.getElementById('invLandCost').focus();
			return false;
		} else {
			document.getElementById('invlandcost').innerHTML = "";
		} */

		/* if (buildingCost == null || buildingCost == '') {
			document.getElementById('invbuildingcost').innerHTML = "Building Site Development & Civil Works Cost is required";
			document.getElementById('invBuildingCost').focus();
			return false;
		} else {
			document.getElementById('invbuildingcost').innerHTML = "";
		} */

		/* if (plantAndMachCost == null || plantAndMachCost == '') {
			document.getElementById('invplantandmachcost').innerHTML = "Plant and Machinery cost is required";
			document.getElementById('invPlantAndMachCost').focus();
			return false;
		}

		else {
			document.getElementById('invplantandmachcost').innerHTML = "";
		} */

		/* if (otherCost == null || otherCost == '') {
			document.getElementById('invothercost').innerHTML = "Miscellaneous Fixed Asset(MFA) is required";
			document.getElementById('invOtherCost').focus();
			return false;
		} else {
			document.getElementById('invothercost').innerHTML = "";
		} */

		var regiOrLicenseFile1 = document.getElementById("regiOrLicenseFile1").innerHTML;
		if (regiOrLicenseFile1 == null || regiOrLicenseFile1 == ''
				|| regiOrLicenseFile1 == 'Choose file') {
			document.getElementById('RegiOrLicenseFile').innerHTML = "Enclose UAM or IEM Report is Required.";
			document.getElementById('regiOrLicenseFile').focus();
			return false;
		}
		
		</c:if>

	/* 	if (phaseApply == null || phaseApply == '') {
			document.getElementById('pwapply').innerHTML = "Phasewise Investment is required";
			document.getElementById('pwApply').focus();
			return false;
		} else {
			document.getElementById('pwapply').innerHTML = "";
		}
 */
		<c:if test="${empty pwInvList}">
		
		
		/* if (phaseApply == 'Yes') {
			//alert("Please fill Phasewise Investment Details or select as No");
			return false;
		} */
		</c:if>

		<c:if test="${not empty pwInvList}">
		if (phaseApply == 'No') {
			alert("Phasewise Investment Details already exists. Please delete created records.");
			return false;
		}
		</c:if>

		if (bkmoffile == null || bkmoffile == '' || bkmoffile == 'Choose file') {
			document.getElementById('brkupmoffile').innerHTML = "Please upload file.";
			document.getElementById('brkupmofFile').focus();
			return false;
		} else {
			document.getElementById('brkupmoffile').innerHTML = "";
		}

		if (confirm("Are you sure, You want to save Investment Details?")) {

			document.getElementById("invIndType").disabled = false;
			$("input[name='invLandCost']").attr("readonly", false);
			$("input[name='invBuildingCost']").attr("readonly", false);
			$("input[name='invPlantAndMachCost']").attr("readonly", false);
			$("input[name='invOtherCost']").attr("readonly", false);
			$("input[name='invTotalProjCost']").attr("readonly", false);
			$("input[name='invCommenceDate']").attr("readonly", false);
			$("input[name='propCommProdDate']").attr("readonly", false);
			$("input[name='invTotalProjCost']").attr("readonly", false);

			$("input[name='pwProductName']").attr("readonly", false);
			$("input[name='pwProductName']").attr("readonly", false);
			$("input[name='pwCapacity']").attr("readonly", false);
			document.getElementById("pwUnit").disabled = false;
			$("input[name='pwFci']").attr("readonly", false);
			$("input[name='pwPropProductDate']").attr("readonly", false);

			document.getElementById("pwApply").disabled = false;
			document.getElementById("brkupmofFile").disabled = false;
			document.getElementById("regiOrLicense").disabled = false;
			document.getElementById("regiOrLicenseIEM").disabled = false;
			document.getElementById("regiOrLicenseFile").disabled = false;
			document.getElementById("addButtom").disabled = false;
			$("#editButton").removeClass("disable_a_href");

			return true;
		} else {
			return false;
		}

		return true;
	}

	function validateBreakupcost() {
		var parameter = document.getElementById("brkupParameter").value;
		var amount = document.getElementById("brkupAmount").value;

		if (parameter == null || parameter == '') {
			document.getElementById('brkupparameter').innerHTML = "Parameter is required";
			document.getElementById('brkupParameter').focus();
			return false;
		} else if (amount == null || amount == '') {
			document.getElementById('brkupamount').innerHTML = "Amount is required";
			document.getElementById('brkupAmount').focus();
			return false;
		}
	}

	function validateMeansOfFinance() {
		var parameter = document.getElementById("mofParameter").value;
		var amount = document.getElementById("mofAmount").value;

		if (parameter == null || parameter == '') {
			document.getElementById('mofparameter').innerHTML = "Parameter is required";
			document.getElementById('mofParameter').focus();
			return false;
		} else if (amount == null || amount == '') {
			document.getElementById('mofamount').innerHTML = "Amount is required";
			document.getElementById('mofAmount').focus();
			return false;
		}
	}

	function emptyPwInvestmentForm() {

		$("#pwphaseno").text('');
		$("#pwproductname").text('');
		$("#pwcapacity").text('');
		$("#pwunit").text('');
		$("#pwfci").text('');
		$("#pwpropproductdate").text('');

	}

	function validatePwInvestmentForm() {
		
		
		var phasenoRegex = /^[1-9][0-9]*$/;
		var phaseno = $("#phaseNo").val();
		var landCost = $("#invLandCost").val();
		var buildCost = $("#invBuildingCost").val();
		var plantMech = $("#invPlantAndMachCost").val();
		var invOtherCost = $("#invOtherCost").val();
		var invCost = $("#invFci").val();
		
		var productName = $("#pwProductName").val();
		var capacity = $("#pwCapacity").val();
		var unit = $("#pwUnit").val();
		//var fci = $("#pwFci").val();
		var propProductDate = $("#pwPropProductDate").val();
        
		if (phaseno == null || phaseno == '') { //phaseNo, pwphaseno 
			document.getElementById('pwphaseno').innerHTML = "Phase Number is required";
			document.getElementById('phaseNo').focus();
			return false;
		}
		//Regular expression validation
		if (!phasenoRegex.test(phaseno)) {
			document.getElementById('pwphaseno').innerHTML = "Phase number should not start with 0 (zero).";
			document.getElementById("pwPhaseNo").focus();
			return false;
		} /* else {
					$("#pwphaseno").text('');
					return true;
				} */

				if (landCost == null || landCost == '') {
					document.getElementById('invlandcost').innerHTML = "Land Cost  is required";
					document.getElementById('invLandCost').focus();
					return false;
				}
				
				if (buildCost == null || buildCost == '') {
					document.getElementById('invbuildingcost').innerHTML = "Building Site Development & Civil Works Cost is required";
					document.getElementById('invBuildingCost').focus();
					return false;
				}
				if (plantMech == null || plantMech == '') {
					document.getElementById('invplantandmachcost').innerHTML = "Plant & Machinery Cost is required";
					document.getElementById('invPlantAndMachCost').focus();
					return false;
				}
				if (invOtherCost == null || invOtherCost == '') {
					document.getElementById('invothercost').innerHTML = "Miscellaneous Fixed Asset(MFA)  is required";
					document.getElementById('invOtherCost').focus();
					return false;
				}
				if (invCost == null || invCost == '') {
					document.getElementById('invfciSpan').innerHTML = "Proposed Fixed Capital Investment";
					document.getElementById('invFci').focus();
					return false;
				}


				
		if (productName == null || productName == '') {
			document.getElementById('pwproductname').innerHTML = "Product name is required";
			document.getElementById('pwProductName').focus();
			return false;
		}

		if (capacity == null || capacity == '') {
			document.getElementById('pwcapacity').innerHTML = "Capacity is required";
			document.getElementById('pwCapacity').focus();
			return false;
		}

		if (unit == null || unit == '') {
			document.getElementById('pwunit').innerHTML = "Unit is required";
			document.getElementById('pwUnit').focus();
			return false;
		}

		/* if (fci == null || fci == '') {
			document.getElementById('pwfci').innerHTML = "Proposed capital investment is required";
			document.getElementById('pwFci').focus();
			return false;
		} */

		if (propProductDate == null || propProductDate == '') {
			document.getElementById('pwpropproductdate').innerHTML = "Proposed date of  commercial production is required";
			document.getElementById('pwPropProductDate').focus();
			return false;
		}

		// vinay strat

		/*   var regiOrLicenseFile1=document.getElementById("regiOrLicenseFile1").innerHTML;    
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
		 } 
		 */
		// vinay end
		document.getElementById("invIndType").disabled = false;
		$("input[name='invLandCost']").attr("readonly", false);
		$("input[name='invBuildingCost']").attr("readonly", false);
		$("input[name='invPlantAndMachCost']").attr("readonly", false);
		$("input[name='invOtherCost']").attr("readonly", false);
		$("input[name='invTotalProjCost']").attr("readonly", false);
		$("input[name='invCommenceDate']").attr("readonly", false);
		$("input[name='propCommProdDate']").attr("readonly", false);
		$("input[name='invTotalProjCost']").attr("readonly", false);

		$("input[name='pwProductName']").attr("readonly", false);
		$("input[name='pwCapacity']").attr("readonly", false);
		document.getElementById("pwUnit").disabled = false;
		$("input[name='pwFci']").attr("readonly", false);
		$("input[name='pwPropProductDate']").attr("readonly", false);

		document.getElementById("pwApply").disabled = false;
		document.getElementById("brkupmofFile").disabled = false;
		document.getElementById("regiOrLicense").disabled = false;
		document.getElementById("regiOrLicenseIEM").disabled = false;
		document.getElementById("regiOrLicenseFile").disabled = false;
		document.getElementById("addButtom").disabled = false;
		$("#editButton").removeClass("disable_a_href");
		phasewiseInvestmentDtls();
		
		
		
	}

	
	function phasewiseInvestmentDtls(){
		
		
		var add = {}
		add["pwId"] = $("#editappid").val();
	    add["pwPhaseNo"] = $("#phaseNo").val();
		add["invLandCost"] = $("#invLandCost").val();
		add["invBuildingCost"] = $("#invBuildingCost").val();
		add["invPlantAndMachCost"] = $("#invPlantAndMachCost").val();
		add["invOtherCost"] =$("#invOtherCost").val();
		add["invFci"] = $("#invFci").val();
		add["pwProductName"] = $("#pwProductName").val();
	
		add["pwCapacity"] = $("#pwCapacity").val();
		//add["div_Status"] = $("input[name='div_Status']:checked").val();
		add["pwUnit"] = $("#pwUnit").val();
		add["dateInString"] = $("#pwPropProductDate").val();
		

	    //$("#addButtom").prop("disabled", true);
	    console.log(JSON.stringify(add));
	    
	     $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "addPhaseWiseInvestment",
	        data: JSON.stringify(add),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	
	        	var tableData;
	        	document.getElementById("addButtom").disabled = false;
	        	
	        	if(data.length>0){
	        	tableData=tableData+"<thead><tr><th>S.No.</th><th>AppId</th><th>Phase</th><th>Land Cost</th><th>Building Site Development And Civil Works Cost</th><th>Plant And Machinery Cost</th><th>Miscellaneous Fixed Asset</th><th>Proposed Fixed Capital Investment</th><th>Product</th><th>Capacity</th><th>Unit</th><th>Proposed Date of Commercial Production</th><th>Action</th></tr></thead>";
	        	}
	        	for (var i= 0; i < data.length; i++) {      		
	        		
	        		tableData=tableData+"<tbody><tr><td>"+(i+1)+"</td><td>"+data[i].pwId+"</td><td>"+data[i].pwPhaseNo+"</td><td>"+data[i].invLandCost+"</td><td>"+data[i].invBuildingCost+"</td><td>"+data[i].invPlantAndMachCost+"</td><td>"+data[i].invOtherCost+"</td><td>"+data[i].invFci+"</td><td>"+data[i].pwProductName+"</td><td>"+data[i].pwCapacity+"</td><td>"+data[i].pwUnit+"</td><td>"+data[i].pwPropProductDate+"</td><td><a id='editButton' disabled='true' href='#editPWId' onclick='editPWValidation(\""+data[i].pwId+"\",\""+data[i].pwPhaseNo+"\",\""+data[i].invLandCost+"\",\""+data[i].invBuildingCost+"\",\""+data[i].invPlantAndMachCost+"\",\""+data[i].invFci+"\",\""+data[i].invOtherCost+"\",\""+data[i].pwProductName+"\",\""+data[i].pwCapacity+"\",\""+data[i].pwUnit+"\",\""+data[i].pwPropProductDate+"\")'><i class='fa fa-edit text-info'></i></a><a id='deleteButton' onclick='deletePWDetailsValidation(\""+data[i].pwId+"\")'><i class='fa fa-trash text-danger'></i></a></td></tr></tbody>";       
	        		       	
	        		
	        	}
	        	//alert(tableData);
	        	$("#editButtonPw").val("");
	        	$("#editButtonPw").empty();
	        	$("#editButtonPw").append(tableData);
	        	
	        	
	            $("#editappid").val("");
	        	
	        	$("#phaseNo").val("");
	        	
	        	$("#invLandCost").val("");
	        	
	        	$("#invBuildingCost").val("");
	        	
	        	$("#invPlantAndMachCost").val("");
	        	
	        	$("#invOtherCost").val("");
	        	
	        	$("#invFci").val("");
	        	/* 
	        	$("input:radio[name='gender']").each(function(i) {
	        	       this.checked = false;
	        	}); */
	        	
	        	$("#pwProductName").val("");
	        	
	        	$("#pwCapacity").val("");
	        	
	        	$("#pwUnit").val("");
	        	$("#pwPropProductDate").val("");
	        	
	        	getPhaseNoList();

	        },
	        error: function (e) {
	        	alert("error");
	            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
	                + e.responseText + "&lt;/pre&gt;";
	            $('#feedback').html(json);

	            console.log("ERROR : ", e);
	           
	        }
	    });
		
	}
	
	function addPWDetailsDtlsOnLoad()

	{
		
		$.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "getAllPWDtlsOnLoad",
	        //data: JSON.stringify(add),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	
	        	var tableData;
	        	//document.getElementById("addButtom").disabled = false;
	        	if(data.length>0){
	        	tableData=tableData+"<thead><tr><th>S.No.</th><th>AppId</th><th>Phase</th><th>Land Cost</th><th>Building Site Development And Civil Works Cost</th><th>Plant And Machinery Cost</th><th>Miscellaneous Fixed Asset</th><th>Proposed Fixed Capital Investment</th><th>Product</th><th>Capacity</th><th>Unit</th><th>Proposed Date of Commercial Production</th><th>Action</th></tr></thead>";
	        	}
	        	for (var i= 0; i < data.length; i++) {      		                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
	        		
	        		tableData=tableData+"<tbody><tr><td>"+(i+1)+"</td><td>"+data[i].pwId+"</td><td>"+data[i].pwPhaseNo+"</td><td>"+data[i].invLandCost+"</td><td>"+data[i].invBuildingCost+"</td><td>"+data[i].invPlantAndMachCost+"</td><td>"+data[i].invOtherCost+"</td><td>"+data[i].invFci+"</td><td>"+data[i].pwProductName+"</td><td>"+data[i].pwCapacity+"</td><td>"+data[i].pwUnit+"</td><td>"+data[i].pwPropProductDate+"</td><td><a id='editButton' disabled='true' href='#editPWId' onclick='editPWValidation(\""+data[i].pwId+"\",\""+data[i].pwPhaseNo+"\",\""+data[i].invLandCost+"\",\""+data[i].invBuildingCost+"\",\""+data[i].invPlantAndMachCost+"\",\""+data[i].invFci+"\",\""+data[i].invOtherCost+"\",\""+data[i].pwProductName+"\",\""+data[i].pwCapacity+"\",\""+data[i].pwUnit+"\",\""+data[i].pwPropProductDate+"\")'><i class='fa fa-edit text-info'></i></a><a id='deleteButton' onclick='deletePWDetailsValidation(\""+data[i].pwId+"\")'><i class='fa fa-trash text-danger'></i></a></td></tr></tbody>";       
	        		       	
	        		
	        	}
	        	//alert(tableData);
	        	$("#editButtonPw").val("");
	        	$("#editButtonPw").append(tableData);
		        	
		        	

		        },
		        error: function (e) {
		        	alert("error");
		            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
		                + e.responseText + "&lt;/pre&gt;";
		            $('#feedback').html(json);

		            console.log("ERROR : ", e);
		            $("#btn-search").prop("disabled", false);

		        }
		    });
		
		}

	function editPWValidation(pwId,pwPhaseNo,invLandCost,invBuildingCost,invPlantAndMachCost,invOtherCost,invFci,pwProductName,pwCapacity,pwUnit,pwPropProductDate)
	{
		
		$("#editappid").val("");
		$("#editappid").val(pwId);
		
		$("#phaseNo").val("");
		$("#phaseNo").val(pwPhaseNo);
		
		$("#invLandCost").val("");
		$("#invLandCost").val(invLandCost);
		
		$("#invBuildingCost").val("");
		$("#invBuildingCost").val(invBuildingCost);
		
		$("#invPlantAndMachCost").val("");
		$("#invPlantAndMachCost").val(invPlantAndMachCost);
		
		$("#invOtherCost").val("");
		$("#invOtherCost").val(invOtherCost);
		
		$("#invFci").val("");
		$("#invFci").val(invFci);
		
		
		$("#pwProductName").val("");
		$("#pwProductName").val(pwProductName);
		
		
		$("#pwCapacity").val("");
		$("#pwCapacity").val(pwCapacity);
		
		$("#pwUnit").val("");
		$("#pwUnit").val(pwUnit);
		
		$("#pwPropProductDate").val("");
		$("#pwPropProductDate").val(pwPropProductDate);
		
		}
		
	function deletePWDetailsValidation(pwId)
	{
	
		
		var add = {}
		add["pwId"] = pwId;
		  //add["propId"] =propid;

		 $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "deletePWDetails",
	        data: JSON.stringify(add),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	
	        	var tableData;
	        	document.getElementById("addButtom").disabled = false;
	        	if(data.length>0){
	        	tableData=tableData+"<thead><tr><th>S.No.</th><th>AppId</th><th>Phase</th><th>Land Cost</th><th>Building Site Development And Civil Works Cost</th><th>Plant And Machinery Cost</th><th>Miscellaneous Fixed Asset</th><th>Proposed Fixed Capital Investment</th><th>Product</th><th>Capacity</th><th>Unit</th><th>Proposed Date of Commercial Production</th><th>Action</th></tr></thead>";
	        	}
	        	for (var i= 0; i < data.length; i++) {      		
	        		
	        		tableData=tableData+"<tbody><tr><td>"+(i+1)+"</td><td>"+data[i].pwId+"</td><td>"+data[i].pwPhaseNo+"</td><td>"+data[i].invLandCost+"</td><td>"+data[i].invBuildingCost+"</td><td>"+data[i].invPlantAndMachCost+"</td><td>"+data[i].invFci+"</td><td>"+data[i].invOtherCost+"</td><td>"+data[i].pwProductName+"</td><td>"+data[i].pwCapacity+"</td><td>"+data[i].pwUnit+"</td><td>"+data[i].pwPropProductDate+"</td><td><a id='editButton' disabled='true' href='#editPWId' onclick='editPWValidation(\""+data[i].pwId+"\",\""+data[i].pwPhaseNo+"\",\""+data[i].invLandCost+"\",\""+data[i].invBuildingCost+"\",\""+data[i].invPlantAndMachCost+"\",\""+data[i].invFci+"\",\""+data[i].invOtherCost+"\",\""+data[i].pwProductName+"\",\""+data[i].pwCapacity+"\",\""+data[i].pwUnit+"\",\""+data[i].pwPropProductDate+"\")'><i class='fa fa-edit text-info'></i></a><a id='deleteButton' onclick='deletePWDetailsValidation(\""+data[i].pwId+"\")'><i class='fa fa-trash text-danger'></i></a></td></tr></tbody>";       
	        		       	
	        		
	        	}
	        	//alert(tableData);
	        	$("#editButtonPw").val("");
	        	$("#editButtonPw").empty();
	        	$("#editButtonPw").append(tableData);
	        	
	        	getPhaseNoList();
	        	
		        },
		        error: function (e) {
		        	alert("error");
		            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
		                + e.responseText + "&lt;/pre&gt;";
		            $('#feedback').html(json);

		            console.log("ERROR : ", e);
		            $("#btn-search").prop("disabled", false);

		        }
		    });
		
		
	}
	
	
	
function addOtherIfAnyInvestmentDtls(){
	
	
	var phaseNumber=$("#pwPhaseNoOT").val();
	var particulars=$("#componentn").val();
	var proposed=$("#componentv").val();
	
	if (phaseNumber == null || phaseNumber == '') {
		document.getElementById('pwphaseno').innerHTML = "Phase Number is required";
		document.getElementById('pwPhaseNoOT').focus();
		return false;
	}
	else{
		 document.getElementById('pwphaseno').innerHTML = "";
} 
	
	if (particulars == null || particulars == '') {
		document.getElementById('componentN').innerHTML = "Particulars is required";
		document.getElementById('componentn').focus();
		return false;
	}
	else{
		 document.getElementById('componentN').innerHTML = "";
} 
	
	if (proposed == null || proposed == '') {
		document.getElementById('componenTV').innerHTML = "Proposed Investment in the project is required";
		document.getElementById('componentv').focus();
		return false;
	}
	else{
		 document.getElementById('componenTV').innerHTML = "";
} 
		
		var add = {}
		add["mofId"] = $("#editOIAId").val();
	    add["phaseNumber"] = $("#pwPhaseNoOT").val();
		add["particulars"] = $("#componentn").val();
		add["proposedInvestmentInProject"] = $("#componentv").val();
		//alert($("#pwPhaseNoOT").val());
	    console.log(JSON.stringify(add));
	    
	     $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "addOtherIfAnyInvestmentDetails",
	        data: JSON.stringify(add),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	
	        	var tableData;
	        	document.getElementById("addButtonOIA").disabled = false;
	        	if(data.length>0){
	        	tableData=tableData+"<thead><tr><th>S.No.</th><th>AppId</th><th>Phase No.</th><th>Particular</th> <th>Proposed Investment in the project</th><th>Action</th></tr></thead>";
	        	}
	        	for (var i= 0; i < data.length; i++) {      		
	        		
	        		tableData=tableData+"<tbody><tr><td>"+(i+1)+"</td><td>"+data[i].mofId+"</td><td>"+data[i].phaseNumber+"</td><td>"+data[i].particulars+"</td><td>"+data[i].proposedInvestmentInProject+"</td><td><a id='editDetailsOIA' disabled='true' href='#highliteOIA' onclick='editOIADetlsValidation(\""+data[i].mofId+"\",\""+data[i].phaseNumber+"\",\""+data[i].particulars+"\",\""+data[i].proposedInvestmentInProject+"\")'><i class='fa fa-edit text-info'></i></a><a id='deleteButtonOIA' onclick='deleteOIADtlsValidation(\""+data[i].mofId+"\")'><i class='fa fa-trash text-danger'></i></a></td></tr></tbody>";       
	        		       	
	        		
	        	}
	        	//alert(tableData);
	        	$("#addDataOIA").val("");
	        	$("#addDataOIA").empty();
	        	$("#addDataOIA").append(tableData);
	        	
	        	
	            $("#editOIAId").val("");
	        	
	        	$("#pwPhaseNoOT").val("");
	        	
	        	$("#componentn").val("");
	        	
	        	$("#componentv").val("");
	        	
	        	

	        },
	        error: function (e) {
	        	alert("error");
	            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
	                + e.responseText + "&lt;/pre&gt;";
	            $('#feedback').html(json);

	            console.log("ERROR : ", e);
	           
	        }
	    });
		
	}
		
		function getOIADtlsListOnLoad()
		{
			
			$.ajax({
		        type: "POST",
		        contentType: "application/json",
		        url: "getOIAListOnLoad",
		        //data: JSON.stringify(add),
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        success: function (data) {
		        	
		        	var tableData;
		        	//document.getElementById("addButtonOIA").disabled = false;
		        	
		        	if(data.length>0){
			        	tableData=tableData+"<thead><tr><th>S.No.</th><th>AppId</th><th>Phase No.</th><th>Particular</th> <th>Proposed Investment in the project</th><th>Action</th></tr></thead>";
			        	}
		        	
		        	for (var i= 0; i < data.length; i++) {      		
		        		
		        		tableData=tableData+"<tbody><tr><td>"+(i+1)+"</td><td>"+data[i].mofId+"</td><td>"+data[i].phaseNumber+"</td><td>"+data[i].particulars+"</td><td>"+data[i].proposedInvestmentInProject+"</td><td><a id='editDetailsOIA' disabled='true' href='#highliteOIA' onclick='editOIADetlsValidation(\""+data[i].mofId+"\",\""+data[i].phaseNumber+"\",\""+data[i].particulars+"\",\""+data[i].proposedInvestmentInProject+"\")'><i class='fa fa-edit text-info'></i></a><a id='deleteButtonOIA' onclick='deleteOIADtlsValidation(\""+data[i].mofId+"\")'><i class='fa fa-trash text-danger'></i></a></td></tr></tbody>";       
		        		       	
		        		
		        	}
		        	//alert(tableData);
		        	$("#addDataOIA").val("");
		        	$("#addDataOIA").empty();
		        	$("#addDataOIA").append(tableData);
		       
			        },
			        error: function (e) {
			        	alert("error");
			            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
			                + e.responseText + "&lt;/pre&gt;";
			            $('#feedback').html(json);

			            console.log("ERROR : ", e);
			           
			        }
			    });
			
		}
	function editOIADetlsValidation(mofId,phaseNumber,particulars,proposedInvestmentInProject)
	{
		
		$("#editOIAId").val("");
		$("#editOIAId").val(mofId);
		
		$("#componentn").val("");
		$("#componentn").val(particulars);
		
		$("#pwPhaseNoOT").val("");
		$("#pwPhaseNoOT").val(phaseNumber);
		
		$("#componentv").val("");
		$("#componentv").val(proposedInvestmentInProject);
		
	}
	
	function deleteOIADtlsValidation(mofId)
	{
		var add = {}
		add["mofId"] = mofId;
		$.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "deleteOIAById",
	        data: JSON.stringify(add),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	
	        	var tableData;
	        	//document.getElementById("addButtonOIA").disabled = false;
	        	if(data.length>0){
		        	tableData=tableData+"<thead><tr><th>S.No.</th><th>AppId</th><th>Phase No.</th><th>Particular</th> <th>Proposed Investment in the project</th><th>Action</th></tr></thead>";
		        	}
	        	for (var i= 0; i < data.length; i++) {      		
	        		
	        		tableData=tableData+"<tbody><tr><td>"+(i+1)+"</td><td>"+data[i].mofId+"</td><td>"+data[i].phaseNumber+"</td><td>"+data[i].particulars+"</td><td>"+data[i].proposedInvestmentInProject+"</td><td><a id='editDetailsOIA' disabled='true' href='#highliteOIA' onclick='editOIADetlsValidation(\""+data[i].mofId+"\",\""+data[i].phaseNumber+"\",\""+data[i].particulars+"\",\""+data[i].proposedInvestmentInProject+"\")'><i class='fa fa-edit text-info'></i></a><a id='deleteButtonOIA' onclick='deleteOIADtlsValidation(\""+data[i].mofId+"\")'><i class='fa fa-trash text-danger'></i></a></td></tr></tbody>";       
	        		       	
	        		
	        	}
	        	//alert(tableData);
	        	$("#addDataOIA").val("");
	        	$("#addDataOIA").empty();
	        	$("#addDataOIA").append(tableData);
	       
		        },
		        error: function (e) {
		        	alert("error");
		            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
		                + e.responseText + "&lt;/pre&gt;";
		            $('#feedback').html(json);

		            console.log("ERROR : ", e);
		           
		        }
		    });
		
	}
	
function addMOFInvestmentDtls(){ 
	
	var phNo=$("#pwPhaseNoMOM").val();
	var particular=$("#particularmom").val();
	var proposedInvest=$("#proposedInvestmentInProjectmom").val();
	
	if (phNo == -1) {
		document.getElementById('pwphaseNo').innerHTML = "Phase Number is required";
		document.getElementById('pwPhaseNoMOM').focus();
		return false;
	}
	else{
		 document.getElementById('pwphaseno').innerHTML = "";
} 
	if (particular == null || particular == '') {
		document.getElementById('particularMom').innerHTML = "Particular is required";
		document.getElementById('particularmom').focus();
		return false;
	}
	else{
		 document.getElementById('particularMom').innerHTML = "";
} 
	if (proposedInvest == null || proposedInvest == '') {
		document.getElementById('proposedInvestmentInProjectMom').innerHTML = "Phase Number is required";
		document.getElementById('proposedInvestmentInProjectmom').focus();
		return false;
	}
	else{
		 document.getElementById('proposedInvestmentInProjectMom').innerHTML = "";
} 
		
		var add = {} //, , 
		add["mofId"] = $("#editMOFId").val();
	    //add["pwPhaseNoMOM"] = $("#pwPhaseNoMOM").val();
		add["mofParameter"] = $("#particularmom").val();
		add["mofAmount"] = $("#proposedInvestmentInProjectmom").val();
		//alert($("#pwPhaseNoOT").val());
	    console.log(JSON.stringify(add));
	    
	     $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "addMOFInvestmentDtls",
	        data: JSON.stringify(add),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	
	        	var tableData;
	        	document.getElementById("addButtonMOF").disabled = false;
	        	if(data.length>0){
	        		tableData=tableData+"<thead><tr><th>S.No.</th><th>AppId</th><th>Particulars</th><th>Proposed Investment in the project (As per DPR)</th><th>Action</th></tr></thead>";
	        	}
	        	for (var i= 0; i < data.length; i++) {      		
	        		
	        		tableData=tableData+"<tbody><tr><td>"+(i+1)+"</td><td>"+data[i].mofId+"</td><td>"+data[i].mofParameter+"</td><td>"+data[i].mofAmount+"</td><td><a id='editDetailsMOF' disabled='true' href='#highliteMOF' onclick='editMOFDetlsValidation(\""+data[i].mofId+"\",\""+data[i].mofParameter+"\",\""+data[i].mofAmount+"\")'><i class='fa fa-edit text-info'></i></a><a id='deleteButtonMOF' onclick='deleteMOFDtlsValidation(\""+data[i].mofId+"\")'><i class='fa fa-trash text-danger'></i></a></td></tr></tbody>";       
	        		       	
	        		
	        	}
	        	//alert(tableData);
	        	$("#addDataMOF").val("");
	        	$("#addDataMOF").empty();
	        	$("#addDataMOF").append(tableData);
	        	
	        	
	            $("#editMOFId").val("");
	        	
	        	
	        	
	        	$("#particularmom").val("");
	        	
	        	$("#proposedInvestmentInProjectmom").val("");
	            $("#pwPhaseNoMOM").val("");
	        	

	        },
	        error: function (e) {
	        	alert("error");
	            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
	                + e.responseText + "&lt;/pre&gt;";
	            $('#feedback').html(json);

	            console.log("ERROR : ", e);
	           
	        }
	    });
		
	}
	
function getMOFListOnLoad(){
	
     $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "getMOFDtlsListOnLoad",
        //data: JSON.stringify(add),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	
        	var tableData;
        	//document.getElementById("addButtonMOF").disabled = false;
        	if(data.length>0){
        		tableData=tableData+"<thead><tr><th>S.No.</th><th>AppId</th><th>Particulars</th><th>Proposed Investment in the project (As per DPR)</th><th>Action</th></tr></thead>";
        	}
        	for (var i= 0; i < data.length; i++) {      		
        		
        		tableData=tableData+"<tbody><tr><td>"+(i+1)+"</td><td>"+data[i].mofId+"</td><td>"+data[i].mofParameter+"</td><td>"+data[i].mofAmount+"</td><td><a id='editDetailsMOF' disabled='true' href='#highliteMOF' onclick='editMOFDetlsValidation(\""+data[i].mofId+"\",\""+data[i].mofParameter+"\",\""+data[i].mofAmount+"\")'><i class='fa fa-edit text-info'></i></a><a id='deleteButtonMOF' onclick='deleteMOFDtlsValidation(\""+data[i].mofId+"\")'><i class='fa fa-trash text-danger'></i></a></td></tr></tbody>";       
        		       	
        		
        	}
        	//alert(tableData);
        	$("#addDataMOF").val("");
        	$("#addDataMOF").empty();
        	$("#addDataMOF").append(tableData);
        	
        },
        error: function (e) {
        	alert("error");
            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
                + e.responseText + "&lt;/pre&gt;";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
           
        }
    });
	
}
function editMOFDetlsValidation(mofId,particulars,mofAmount)
{
	
	
	$("#editMOFId").val("");
	$("#editMOFId").val(mofId);
	
	$("#particularmom").val("");
	$("#particularmom").val(particulars);
	
	/* $("#pwPhaseNoMOM").val("");
	$("#pwPhaseNoMOM").val(phaseNumber); */
	
	$("#proposedInvestmentInProjectmom").val("");
	$("#proposedInvestmentInProjectmom").val(mofAmount);
	
}
function deleteMOFDtlsValidation(mofId){
	
	var add = {}
	add["mofId"] = mofId;
    
    
     $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "deleteMOFInvestmentDtls",
        data: JSON.stringify(add),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	
        	var tableData;
        	//document.getElementById("addButtonMOF").disabled = false;
        	if(data.length>0){
        		tableData=tableData+"<thead><tr><th>S.No.</th><th>AppId</th><th>Particulars</th><th>Proposed Investment in the project (As per DPR)</th><th>Action</th></tr></thead>";
        	}
        	for (var i= 0; i < data.length; i++) {      		
        		
        		tableData=tableData+"<tbody><tr><td>"+(i+1)+"</td><td>"+data[i].mofId+"</td><td>"+data[i].mofParameter+"</td><td>"+data[i].mofAmount+"</td><td><a id='editDetailsMOF' disabled='true' href='#highliteMOF' onclick='editMOFDetlsValidation(\""+data[i].mofId+"\",\""+data[i].mofParameter+"\",\""+data[i].mofAmount+"\")'><i class='fa fa-edit text-info'></i></a><a id='deleteButtonMOF' onclick='deleteMOFDtlsValidation(\""+data[i].mofId+"\")'><i class='fa fa-trash text-danger'></i></a></td></tr></tbody>";       
        		       	
        		
        	}
        	//alert(tableData);
        	$("#addDataMOF").val("");
        	$("#addDataMOF").empty();
        	$("#addDataMOF").append(tableData);
        
        },
        error: function (e) {
        	alert("error");
            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
                + e.responseText + "&lt;/pre&gt;";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
           
        }
    });
	
}
	
	function validateDateField() {
		var commProdDate = document.getElementById("propCommProdDate").value;
		var commenceDate = document.getElementById("invCommenceDate").value;

		if (commenceDate == null || commenceDate == '') {
			document.getElementById('invcommencedate').innerHTML = "Cut-off date of investment is Required.";
			document.getElementById('invCommenceDate').focus();
			return false;
		} else {
			document.getElementById('invcommencedate').innerHTML = "";
		}

		if (commProdDate == null || commProdDate == '') {
			document.getElementById('propcommproddate').innerHTML = "Proposed Date of Commencement of Commercial Production is required";
			document.getElementById('propCommProdDate').focus();
			return false;
		}

		else {
			document.getElementById('propcommproddate').innerHTML = "";
		}

	}

	function validateTextField() {
		return (event.charCode > 64 && event.charCode < 91)
				|| (event.charCode > 96 && event.charCode < 123)
				|| (event.charCode == 32)
	}

	function validatePhaseText() {
		return (event.charCode > 64 && event.charCode < 91)
				|| (event.charCode > 96 && event.charCode < 123)
				|| (event.charCode == 32)
	}

	function validateNumberField() {
		return (event.charCode > 47 && event.charCode < 58)

	}

	//for positioning
	$(function() {
		if ('${otherBreakUplist}' != null && '${otherBreakUplist}' != '') {
			$("#customFields1").focus();

		}

		if ('${otherMeanslist}' != null && '${otherMeanslist}' != '') {
			$("#customFields2").focus();

		}

		if ('${phasewiselist}' != null && '${phasewiselist}' != '') {
			$("#customFields3").focus();

		}
	});

	function emptyCategory() {
		$('#invindtype').text('');
	}

	function emptyBuildingCost() {
		$('#invbuildingcost').text("");
		$('#invlandcost').text("");
		$('#invbuildingcost').text("");
		$('#invplantandmachcost').text("");
		$('#invothercost').text("");
		$('#invfciSpan').text("");
		
	}
	//invLandCost,invlandcost,invBuildingCost,invbuildingcost,
	//invPlantAndMachCost ,invplantandmachcost,invOtherCost,invothercost,invFci,invfciSpan
	
	function emptyRegiOrLicenseFile() {
		$('#regiOrLicenseFile').text("");
	}

	window.onload = function() {
		if ('${invType}' == 'Small' || '${invType}' == 'Medium') {
			$('input:radio[name="regiOrLicense"][value="EncloseUAM"]').prop(
					'checked', true);
		} else {
			$('input:radio[name="regiOrLicense"][value="IEMcopy"]').prop(
					'checked', true);
		}

		if ('${investmentId}' != null && '${investmentId}' != '') {
			document.getElementById("invIndType").disabled = true;
			$("input[name='invLandCost']").attr("readonly", true);
			$("input[name='invBuildingCost']").attr("readonly", true);
			$("input[name='invPlantAndMachCost']").attr("readonly", true);
			$("input[name='invOtherCost']").attr("readonly", true);
			$("input[name='invTotalProjCost']").attr("readonly", true);
			$("input[name='invCommenceDate']").attr("readonly", true);
			$("input[name='propCommProdDate']").attr("readonly", true);
			$("input[name='invTotalProjCost']").attr("readonly", true);

			$("input[name='pwPhaseNo']").attr("readonly", true);
			$("input[name='pwProductName']").attr("readonly", true);
			$("input[name='pwCapacity']").attr("readonly", true);
			document.getElementById("pwUnit").disabled = true;
			$("input[name='pwFci']").attr("readonly", true);
			$("input[name='pwPropProductDate']").attr("readonly", true);

			document.getElementById("pwApply").disabled = true;
			document.getElementById("brkupmofFile").disabled = true;
			document.getElementById("regiOrLicense").disabled = true;
			document.getElementById("regiOrLicenseIEM").disabled = true;
			document.getElementById("regiOrLicenseFile").disabled = true;
			$("#editButton").addClass("disable_a_href");
			document.getElementById("addButtom").disabled = true;
			//document.getElementById("addButtonOIA").disabled = true;
			//document.getElementById("addButtonMOF").disabled = true;
			
			document.getElementById("pwPhaseNoOT").disabled = true;
			document.getElementById("componentn").disabled = true;
			document.getElementById("componentv").disabled = true;
			document.getElementById("addButtonOIA").disabled = true;
			
			document.getElementById("pwPhaseNoMOM").disabled = true;
			document.getElementById("particularmom").disabled = true;
			document.getElementById("proposedInvestmentInProjectmom").disabled = true;
			document.getElementById("addButtonMOF").disabled = true;
			
		} else {

			document.getElementById("invIndType").disabled = false;
			$("input[name='invLandCost']").attr("readonly", false);
			$("input[name='invBuildingCost']").attr("readonly", false);
			$("input[name='invPlantAndMachCost']").attr("readonly", false);
			$("input[name='invOtherCost']").attr("readonly", false);
			$("input[name='invTotalProjCost']").attr("readonly", false);
			$("input[name='invCommenceDate']").attr("readonly", false);
			$("input[name='propCommProdDate']").attr("readonly", false);
			$("input[name='invTotalProjCost']").attr("readonly", false);

			$("input[name='pwPhaseNo']").attr("readonly", false);
			$("input[name='pwCapacity']").attr("readonly", false);
			document.getElementById("pwUnit").disabled = false;
			$("input[name='pwFci']").attr("readonly", false);
			$("input[name='pwPropProductDate']").attr("readonly", false);

			document.getElementById("pwApply").disabled = false;
			document.getElementById("brkupmofFile").disabled = false;
			document.getElementById("regiOrLicense").disabled = false;
			document.getElementById("regiOrLicenseIEM").disabled = false;
			document.getElementById("regiOrLicenseFile").disabled = false;
			document.getElementById("addButtom").disabled = false;
			$("#editButton").removeClass("disable_a_href");

		}

		$(function() {
			$('input:radio[name="regiOrLicense"]')
					.change(
							function() {

								if ($(this).val() == 'IEMcopy') {

									document
											.getElementById('regiOrLicenseFile1').innerHTML = 'Choose file';
									document
											.getElementById('regiOrLicenseFile').innerHTML = '';
									document
											.getElementById("regiOrLicenseFile").value = null;
									document
											.getElementById('RegiOrLicenseFile').innerHTML = "";
								} else {
									document
											.getElementById('regiOrLicenseFile1').innerHTML = 'Choose file';
									document
											.getElementById('regiOrLicenseFile').innerHTML = '';
									document
											.getElementById("regiOrLicenseFile").value = null;
									document
											.getElementById('RegiOrLicenseFile').innerHTML = "";
								}
							});
		});
		

	};

	function editInvestmentDetails() {

		var r = confirm('Are you Sure, You want to edit Investment Details?');

		if (r == true) {

			document.getElementById("invIndType").disabled = false;
			$("input[name='invLandCost']").attr("readonly", false);
			$("input[name='invBuildingCost']").attr("readonly", false);
			$("input[name='invPlantAndMachCost']").attr("readonly", false);
			$("input[name='invOtherCost']").attr("readonly", false);
			$("input[name='invTotalProjCost']").attr("readonly", false);
			$("input[name='invCommenceDate']").attr("readonly", false);
			$("input[name='propCommProdDate']").attr("readonly", false);
			$("input[name='invTotalProjCost']").attr("readonly", false);

			$("input[name='pwPhaseNo']").attr("readonly", false);
			$("input[name='pwProductName']").attr("readonly", false);
			$("input[name='pwCapacity']").attr("readonly", false);
			$("input[name='capInvAmt']").attr("readonly", false);
			document.getElementById("pwUnit").disabled = false;
			$("input[name='pwFci']").attr("readonly", false);
			$("input[name='pwPropProductDate']").attr("readonly", false);
			document.getElementById("pwApply").disabled = false;
			document.getElementById("brkupmofFile").disabled = false;
			document.getElementById("regiOrLicenseFile").disabled = false;
			document.getElementById("addButtom").disabled = false;
			$("#editButton").removeClass("disable_a_href");
			document.getElementById('invIndType').focus();
			
			document.getElementById("pwPhaseNoOT").disabled = false;
			document.getElementById("componentn").disabled = false;
			document.getElementById("componentv").disabled = false;
			document.getElementById("addButtonOIA").disabled = false;
			
			document.getElementById("pwPhaseNoMOM").disabled = false;
			document.getElementById("particularmom").disabled = false;
			document.getElementById("proposedInvestmentInProjectmom").disabled = false;
			document.getElementById("addButtonMOF").disabled = false;
			

		} else {
			return false
		}

		
	}
	function getPhaseNoList()
	{
		 $.ajax({
		        type: "POST",
		        contentType: "application/json",
		        url: "getPWPhaseNoList",
		        //data: JSON.stringify(add),
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        success: function (data) {
		        	
		            $("#pwPhaseNoOT").empty();
		            $("#pwPhaseNoOT").append("<option value='-1'>Select Phase No</option>");
		            
		            $("#pwPhaseNoMOM").empty();
		            $("#pwPhaseNoMOM").append("<option value='-1'>Select Phase No</option>");
		            
		            for (var i= 0; i < data.length; i++) {
		            	
		                $("#pwPhaseNoOT").append("<option value='" + data[i].pwPhaseNo + "'>" + data[i].pwPhaseNo + "</option>");
		                $("#pwPhaseNoMOM").append("<option value='" + data[i].pwPhaseNo + "'>" + data[i].pwPhaseNo + "</option>");
		            }
		        },
		        error: function (e) {
		        	
		        	alert("error");
		            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
		                + e.responseText + "&lt;/pre&gt;";
		            $('#feedback').html(json);

		            console.log("ERROR : ", e);
		           
		        }
		    });
				
		
	}
	
</script>
<script type="text/javascript">
function ValidateSize(file) {
	
	 var SectionLetterDoc = document.getElementById('regiOrLicenseFile').value;
	 var fileSize = document.getElementById("regiOrLicenseFile").files[0];
	 var maxSize = '2000';

	
		
	 if(SectionLetterDoc!=null && SectionLetterDoc!=''){
	 var ext = SectionLetterDoc.split('.').pop();
	 if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('RegiOrLicenseFile').innerHTML = "Please Upload Support documnet in PDF Format";
		document.getElementById('regiOrLicenseFile').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(2);
  if (FileSize >2) {
  	document.getElementById('RegiOrLicenseFile').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 2 MB";
  	document.getElementById('regiOrLicenseFile').focus();
		return false;
  }
	 }
function ValidateSize(file) {
	const fi = document.getElementById('regiOrLicenseFile');
	// Check if any file is selected.
	if (fi.files.length > 0) {
		for (const i = 0; i <= fi.files.length - 1; i++) {

			const fsize = fi.files.item(i).size;
			const file = Math.round((fsize / 1024));
			// The size of the file.
			if (file >= 2048) {
				alert("File too Big, please select a file less than 2mb");
			} else {
				document.getElementById('size').innerHTML = '<b>' + file
						+ '</b> KB';
			}
		}
	}
}
function emptyCapInvErrMsg(){ 
	
	
	$("#pwphaseno").text('');
	$("#componentN").text('');
	$("#componenTV").text('');
	$("#pwphaseNo").text('');
	$("#particularMom").text('');
	$("#proposedInvestmentInProjectMom").text('');
	
}

</script>
</head>
<%-- <%@ include file="header.jsp"%> --%>
<body class="bottom-bg">
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
								<ul class="nav nav-tabs flex-nowrap">
									<li>
										<a href="./getIdByTabs33?authoTab=authoTab" class="filled"> <span>1</span>Authorized Signatory Details
										</a>
									</li>
									<li>
										<a href="./getIdByTabs34?busiTab=busiTab" class="filled"> <span>2</span>Business Entity Details
										</a>
									</li>
									<li>
										<a href="./getIdByTabs35?projTab=projTab" class="filled"> <span>3</span>Project Details
										</a>
									</li>
									<li>
										<a href="#" class="active"><span>4</span>Investment Details</a>
									</li>
									<li>
										<c:if test="${not empty PEflag}">
											<a href="./getIdByTabs36?propoTab=propoTab" class="filled"><span>5</span>Proposed Employment Details</a>
										</c:if>
										<c:if test="${empty PEflag}">
											<a href="./getIdByTabs36?propoTab=propoTab"><span>5</span>Proposed Employment Details</a>
										</c:if>
									</li>
								</ul>
							</div>
							<form:form modelAttribute="investmentDetails" id="invForm" name="InvestmentForm" autocomplete="off" method="POST" enctype="multipart/form-data">
								
								
								
								<div class="tab-content py-2">
									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Investment Details</h3>
										</div>
									</div>
									
									<c:if test="${ not empty pwInvestDtlMsg}">
									<div><font color="red">${pwInvestDtlMsg}</font></div>
									</c:if>
									
									<div class="row">
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="invIndType">Category of Industrial Undertaking  <span>*</span>
												</form:label>
												<form:select id="invIndType" path="invIndType" onchange="return validateDate1();" onblur="return  emptyCategory()" class="form-control" name="invIndType">
													<form:option value="">Select One</form:option>
													<form:option label="Small" value="Small"></form:option>
													<form:option label="Medium" value="Medium"></form:option>
													<form:option label="Large" value="Large"></form:option>
													<form:option label="Mega" value="Mega"></form:option>
													<form:option label="Mega Plus" value="Mega Plus"></form:option>
													<form:option label="Super Mega" value="Super Mega"></form:option>
												</form:select>
												<span id="invindtype" class="text-danger"></span>
												<form:errors path="invIndType" cssClass="error" />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 id="editPWId" class="common-heading">Fixed Capital Investment Component</h3>
										</div>
									</div>
									<div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<form:label path="pwApply">Phase Wise Investment as per Detailed Project Report <span>*</span> (If Applicable)</form:label>
												<form:select class="form-control" id="pwApply" onchange="return pwrestrictDate()" name="pwApply" path="pwApply">
													<form:option value="">Select</form:option>
													<form:option value="Yes">Yes</form:option>
													<form:option value="No">No</form:option>
												</form:select>
												<span id="pwapply" class="text-danger"></span>
											</div>
										</div>
										<form:hidden path="invId" value="" id="editappid" />
										<div class="col-md-6">
											<div class="form-group">
												<label>Phase Number</label>
												<form:input type="text" id="phaseNo" path="pwPhaseNo" name="pwPhaseNo" class="form-control" onblur="emptyPwInvestmentForm()" onkeypress="return validateNumberField()" placeholder="Enter Phase Number e.g - 1 or 2 or 3"></form:input>
												<span id="pwphaseno" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-6">  
											<div class="form-group">
												<form:label path="invLandCost">Land Cost 
													<span>*</span>
												</form:label>
												<form:input id="invLandCost" path="invLandCost" placeholder="Enter Amount in INR"  maxlength="12" onblur="emptyBuildingCost()" class="form-control fixed-total-input is-numeric"></form:input>
												<small class="mt-2 d-block text-info" id="words"></small> <span id="invlandcost" class="text-danger"></span>
												<form:errors path="invLandCost" cssClass="error" />
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<form:label path="invBuildingCost">Building Site Development & Civil Works Cost 
													<span>*</span>
												</form:label>
												<form:input id="invBuildingCost" path="invBuildingCost" placeholder="Enter Amount in INR" onblur="emptyBuildingCost()" onkeypress="return validateNumberField()" maxlength="12" class="form-control fixed-total-input "></form:input>
												<small class="mt-2 d-block text-info" id="words1"></small> <span id="invbuildingcost" class="text-danger"></span>
												<form:errors path="invBuildingCost" cssClass="error" />
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<form:label path="invPlantAndMachCost">Plant & Machinery Cost  
													<span>*</span>
												</form:label>
												<form:input path="invPlantAndMachCost" placeholder="Enter Amount in INR" class="form-control fixed-total-input " onchange="calculate();" onblur="emptyBuildingCost()" onkeypress="return validateNumberField()" maxlength="12" id="invPlantAndMachCost"></form:input>
												<span id="invplantandmachcost" class="text-danger"></span> <small class="mt-2 d-block text-info" id="words2"></small>
												<form:errors path="invPlantAndMachCost" cssClass="error" />
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<form:label path="invOtherCost">Miscellaneous Fixed Asset(MFA)
													<span>*</span>
												</form:label>
												<form:input id="invOtherCost" path="invOtherCost" placeholder="Enter Amount in INR" onchange="return calculate(); " onkeypress="return validateNumberField()" onblur="emptyBuildingCost()" maxlength="12" class="form-control fixed-total-input "></form:input>
												<small class="mt-2 d-block text-info" id="words3"></small> <span id="invothercost" class="text-danger"></span>
												<form:errors path="invOtherCost" cssClass="error" />
											</div>
										</div> 
										<div class="col-md-6">
											<div class="form-group ">
												<form:label path="invFci">Proposed Fixed Capital Investment
													<span>*</span>
												</form:label>
												<form:input id="invFci" path="invFci" readonly="true" onchange="return calculate();validateInvestmentForm(); capinvestAmount()" onblur="emptyBuildingCost()" class="form-control fixed-total" onkeypress="return validateNumberField()"></form:input>
												<small class="mt-2 d-block text-info"></small> <span id="invfciSpan" class="text-danger"></span>
												<form:errors path="invFci" cssClass="error" />
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<label>Product</label>
												<form:input type="text" path="pwProductName" maxlength="250" onblur="emptyPwInvestmentForm()" class="form-control" id="pwProductName" name="pwProductName"></form:input>
												<span id="pwproductname" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<label>Capacity(PA)</label>
												<form:input type="text" path="pwCapacity" maxlength="12" onblur="emptyPwInvestmentForm()" class="form-control is-numeric" placeholder="Enter Capacity" id="pwCapacity" onkeypress="return validateNumberField()" name="pwCapacity"></form:input>
												<small class="words text-info"></small><span id="pwcapacity" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<label>Unit</label>
												<form:select class="form-control" path="pwUnit" id="pwUnit" name="pwUnit" onblur="emptyPwInvestmentForm()">
													<form:option value="">Select Unit</form:option>
													<form:options items="${unitListMap}" />
												</form:select>
												<span id="pwunit" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-8">
											<div class="form-group">
												<form:label path="pwPropProductDate">Proposed Date of Commercial Production</form:label>
												
												<div id="datepicker" class="input-group date" data-date-format="dd-mm-yyyy">
                                                         <input type="text"  value="" class="form-control" id="pwPropProductDate"
                                                          onkeyup="return IsyearEstablishment(event)" autocomplete="off" name="pwPropProductDate">
                                                         <span id="pwpropproductdate" class="input-group-addon text-danger"><i class="fa fa-calendar"></i></span>
                                                        <form:errors path="pwPropProductDate" cssClass="error" />
                                                </div>
												
												<%-- <form:input path="pwPropProductDate" type="date" onblur="emptyPwInvestmentForm()" name="pwPropProductDate" id="pwPropProductDate" class="form-control"></form:input>
												<span id="pwpropproductdate" class="text-danger"></span>
												<form:errors path="pwPropProductDate" cssClass="error" /> --%>
											</div>
										</div>
										<%-- <div class="col-md-6 col-lg-12 col-xl-12">
											<c:if test="${not empty edit}">
												<div class="form-group text-right mt-1">
													<button class="common-btn mt-3" onclick="return validatePwInvestmentForm();submitForm();" id="addButtom">Update</button>
												</div>
											</c:if> --%>
											<%-- <c:if test="${empty edit}"> --%>
												<div class="form-group text-right mt-1">
													<!-- <button class="common-btn mt-3" onclick="return validatePwInvestmentForm();submitForm();" formaction="addPwInvestment" id="addButtom">Add</button> -->
												<button class="common-btn mt-3" onclick="validatePwInvestmentForm();return false" id="addButtom">Add</button>
												</div>
											<%-- </c:if> --%>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive mt-3">
												<table class="table table-stripped table-bordered" id="editButtonPw" tabindex="3">
													<%-- <c:if test="${not empty pwInvList}"> --%>
													
													
													
														<%-- <c:forEach var="pwInvObj" items="${pwInvList}" varStatus="counter">
															<!-- Iterating the list using JSTL tag  -->
															<c:if test="${not empty pwInvList}">
																<c:if test="${fn:length(pwInvList)>0}">
																	<tr>
																		<td>${pwInvObj.pwPhaseNo}</td>
																		<td>${pwInvObj.invLandCost}</td>
																		<td>${pwInvObj.invBuildingCost}</td>
																		<td>${pwInvObj.invPlantAndMachCost}</td>
																		<td>${pwInvObj.invOtherCost}</td>
																		<td>${pwInvObj.invFci}</td>
																		<td>${pwInvObj.pwProductName}</td>
																		<td>${pwInvObj.pwCapacity}</td>
																		<td>${pwInvObj.pwUnit}</td>
																		<td>${pwInvObj.pwPropProductDate}</td>
																		<td class="text-center">
																			<button class="border-0 bg-white" formaction="${pageContext.request.contextPath }/editPwRow?selectedRow=${pwInvObj.pwPhaseNo}" data-toggle="tooltip" title="Edit" class="action-btn" id="editbutton">
																				<i class="fa fa-edit text-info"></i>
																			</button>
																			<button class="border-0 bg-white" onclick="return confirm('Are you sure? Do you want to delete?')" formaction="${pageContext.request.contextPath }/deletePwRow?selectedRow=${pwInvObj.pwPhaseNo}" data-toggle="tooltip" title="Delete" id="deleteButton">
																				<i class="fa fa-trash text-danger"></i>
																			</button>
																		</td>
																	</tr>
																</c:if>
															</c:if>
														</c:forEach> --%>
													
												</table>
											</div>
										</div>
									</div>
									<hr class="mb-4 mt-4 thick-line">
									<div class="row">
										<div class="col-sm-12 mt-1">
											<h3 id=" ghliteOIA" class="common-heading">Others if any</h3>
										</div>
										
										<form:hidden path="invId" value="" id="editOIAId" />
										
										<div class="col-md-6">
											<div class="form-group">
												<label>Phase Number</label>
												<form:select path="pwPhaseNoOT" name="pwPhaseNoOT" onblur="emptyCapInvErrMsg();" id="pwPhaseNoOT"  class="form-control">
													 <!-- <option value="">Select Phase Number</option> -->
													<%--<c:forEach var="phase" items="${phases}" varStatus="counter">
														<option value="${phase}">${phase}</option>
													</c:forEach> --%>
												</form:select>
												<span id="pwphaseno" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-5">
											<div class="form-group"> 
												<label>Particulars</label>
												<form:input path="particulars" list="component" name="componentn" onblur="emptyCapInvErrMsg();" id="componentn" class="form-control"></form:input>
												<datalist id="component">
													<option value="Provision for Cost Escalation & Contingencies">
													<option value="Preliminary & Preoperative Expenses">
													<option value="Interest During Construction Period">
													<option value="Margin Money for Working Capital">
												</datalist>
												<span id="componentN" class="text-danger"></span> 
											</div>
										</div>
										<div class="col-md-7">
											<div class="form-group">
												<label>Proposed Investment in the project (As per DPR)</label>
												<form:input path="proposedInvestmentInProject" type="number" onblur="emptyCapInvErrMsg();" class="form-control is-numeric" id="componentv" name="componentv"></form:input>
												<small class="words text-info"></small>
												<span id="componenTV" class="text-danger"></span> 
											</div>
										</div>
										<div class="col-md-12 text-right mt-1">
											<div class="form-group ">
												<button type="button" class="common-btn mt-3" id="addButtonOIA" onclick="addOtherIfAnyInvestmentDtls();">Add</button>
											</div>
										</div>
										
										
										
										<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive mt-3">
												<table class="table table-stripped table-bordered" id="addDataOIA" tabindex="3">
													
												
													
												</table>
											</div>
										</div>
									</div>
										
										
										<%-- <div class="tabledataarea" id="tabledataarea">
											<div class="table-responsive mt-3">
												<table id="sTable72" class="table table-stripped table-bordered ">
													<tbody>
														<c:forEach var="invother" items="${invotherlist}" varStatus="counter">
															<tr>
																<td>${ invother.phaseNumber}</td>
																<td>${ invother.particulars}</td>
																<td>${ invother.proposedInvestmentInProject}</td>
																<td class="text-center">
																	<button class="border-0 bg-white" formaction="${pageContext.request.contextPath }/editPwRow?selectedRow=${pwInvObj.pwPhaseNo}" data-toggle="tooltip" title="Edit" class="action-btn" id="editbutton">
																		<i class="fa fa-edit text-info"></i>
																	</button>
																	<button class="border-0 bg-white" onclick="return confirm('Are you sure? Do you want to delete?')" formaction="${pageContext.request.contextPath }/deletePwRow?selectedRow=${pwInvObj.pwPhaseNo}" data-toggle="tooltip" title="Delete" id="deleteButton">
																		<i class="fa fa-trash text-danger"></i>
																	</button>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div> --%>
									</div>
									<hr class="mb-4 mt-4 thick-line">
									<div class="row">
										<div class="col-sm-12 mt-1">
											<h3 class="common-heading">Means of Financing</h3>
										</div>
										<form:hidden path="invId" value="" id="editMOFId" />
										<div class="col-md-6">
											<div class="form-group">
												<label>Phase Number</label>
												<form:select path="pwPhaseNoMOM" name="pwPhaseNoMOM" onblur="emptyCapInvErrMsg();" id="pwPhaseNoMOM" class="form-control">
													<%-- <option value="">Select Phase Number</option>
													<c:forEach var="phase" items="${phases}" varStatus="counter">
														<option value="">${phase}</option>
													</c:forEach> --%>
												</form:select>
												<span id="pwphaseNo" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-5">
											<div class="form-group"> 
												<label>Particulars</label> <input path="particularmom" onblur="emptyCapInvErrMsg();" list="componentmom" name="particularmom" id="particularmom" class="form-control">
												<datalist id="componentmom">
													<option value="Equity Share Capital">
													<option value="Internal Cash Accruals">
													<option value="Interest Free Unsecured Loans">
													<option value="Security Deposit">
													<option value="Advances from Dealers">
													<option value="From FI's">
													<option value="From Bank"> 
												</datalist>
												<span id="particularMom" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-7">
											<div class="form-group">
												<label>Proposed Investment in the project (As per DPR) </label> 
												<input path="proposedInvestmentInProjectmom" type="tel" onblur="emptyCapInvErrMsg();" class="form-control is-numeric" id="proposedInvestmentInProjectmom" name="proposedInvestmentInProjectmom" />
												<small class="words text-info"></small>
												<span id="proposedInvestmentInProjectMom" class="text-danger"></span>
											</div>
										</div>
										<div class="col-md-12 text-right mt-1">
											<div class="form-group ">
												<button class="common-btn mt-3" type="button" id="addButtonMOF" onclick="addMOFInvestmentDtls();">Add</button>
											</div>
										</div>
										<%-- <div class="tabledataarea" id="tabledataarea">
											<div class="table-responsive mt-3">
												<table id="sTable72mom" class="table table-stripped table-bordered increasedatatable">
													<c:forEach var="mof" items="${momlist}" varStatus="counter">
															<tr>
																
																<td>${ mof.mofParameter}</td>
																<td>${ mof.mofAmount}</td>
																<td class="text-center">
																	<button class="border-0 bg-white" formaction="${pageContext.request.contextPath }/editPwRow?selectedRow=${pwInvObj.pwPhaseNo}" data-toggle="tooltip" title="Edit" class="action-btn" id="editbutton">
																		<i class="fa fa-edit text-info"></i>
																	</button>
																	<button class="border-0 bg-white" onclick="return confirm('Are you sure? Do you want to delete?')" formaction="${pageContext.request.contextPath }/deletePwRow?selectedRow=${pwInvObj.pwPhaseNo}" data-toggle="tooltip" title="Delete" id="deleteButton">
																		<i class="fa fa-trash text-danger"></i>
																	</button>
																</td>
															</tr>
														</c:forEach>
												</table>
											</div>
										</div> --%>
										<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive mt-3">
												<table class="table table-stripped table-bordered" id="addDataMOF" tabindex="3">												
													
												</table>
											</div>
										</div>
									</div>
									</div>
									<hr class="mb-4 mt-4 thick-line">
									<div class="row">
										<div class="col-md-5">
											<div class="form-group">
												<form:label path="invCommenceDate">Indicate Opted Cut-off date investment <span>*</span>
													<a href="javascript:void(0);" class="remove-row" data-toggle="tooltip" title="" data-original-title="Enter the date of commencement of investment for the project"><i class="fa fa-info-circle text-info"></i></a>
												</form:label>
											<%-- 	<form:input path="invCommenceDate" type="date" readonly="false" onblur="return validateDate();" 
												onchange="capinveffDate()" class="form-control" id="invCommenceDate"></form:input>
												<span id="invcommencedate" class="text-danger"></span> --%>
												
													<div id="datepicker1" class="input-group date" data-date-format="dd-mm-yyyy">
                                                         <input type="text"  value="${invDate}" class="form-control" id="invCommenceDate"
                                                          onkeyup="return IsyearEstablishment(event)" name="invCommenceDate">
                                                         <span class="input-group-addon text-danger"><i class="fa fa-calendar"></i><span  id="invcommencedate"></span></span>
                                                        
                                                </div>
												
												<c:if test="${not empty commencepastdatemsg}">
													<span class="text-danger">${commencepastdatemsg}</span>
												</c:if>
												<c:if test="${not empty commenceperiodmsg}">
													<span class="text-danger">${commenceperiodmsg}</span>
												</c:if>
												<form:errors path="invCommenceDate" cssClass="error" />
											</div>
										</div>
										<div class="col-md-7">
											<div class="form-group">
												<form:label path="propCommProdDate">Proposed Date of Commencement of Commercial Production <span>*</span>
													<a href="javascript:void(0);" class="remove-row" data-toggle="tooltip" title="" data-original-title="Please enter the date on which the industrial undertaking will start commercial production."><i class="fa fa-info-circle text-info"></i></a>
												</form:label>
												<%-- <form:input id="propCommProdDate" path="propCommProdDate" onblur="return validateDate1();" type="date" class="form-control"></form:input>
												<span id="propcommproddate" class="text-danger"></span> --%>
												
												<div id="datepicker2" class="input-group date" data-date-format="dd-mm-yyyy">
                                                         <input type="text"  value="${invDate2}" class="form-control" id="propCommProdDate"
                                                          onkeyup="return IsyearEstablishment(event)" name="propCommProdDate">
                                                         <span id="propcommproddate" class="input-group-addon text-danger"><i class="fa fa-calendar"></i></span>
                                                        
                                                </div>
												
												<c:if test="${not empty propdatemessage}">
													<span class="text-danger">${propdatemessage}</span>
												</c:if>
												<form:errors path="propCommProdDate" cssClass="error" />
											</div>
										</div>
									</div>
									<div class="row capinveffdate">
										<div class="col-md-6">
											<div class="form-group">
												<form:label path="capInvAmt">Enter the amount of Capital Investment made after effective date <span>*</span>
													<a href="javascript:void(0);" class="remove-row" data-toggle="tooltip" title="" data-original-title="Atleast 40% of eligible Capital Investment shall have to be undertaken after effective date "><i class="fa fa-info-circle text-info"></i></a>
												</form:label>
												<form:input type="text" path="capInvAmt" readonly="false" onchange="return capinvestAmount()" placeholder="Please Enter Amount(INR)" onkeypress="return validateNumberField()" class="form-control" name=""></form:input>
												<span id="capinvamt" class="text-danger"></span>
												<form:errors path="capInvAmt" cssClass="error" />
											</div>
										</div>
									</div>
									<hr class="mb-4 mt-4 thick-line">
									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Registration or License for setting up Industrial Undertaking</h3>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<spring:bind path="regiOrLicense">
												<div class="custom-control custom-radio custom-control-inline">
													<input type="radio" name="regiOrLicense" class="custom-control-input" id="regiOrLicense" value="EncloseUAM" disabled="disabled" />
													<form:label path="regiOrLicense" class="custom-control-label" for="regiOrLicense">Udyam Registration Certificate <small>(for MSME)</small>
													</form:label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<input type="radio" name="regiOrLicense" class="custom-control-input" id="regiOrLicenseIEM" value="IEMcopy" disabled="disabled" />
													<form:label path="regiOrLicense" class="custom-control-label" for="regiOrLicenseIEM">IEM copy <small>(for large And Mega)</small>
													</form:label>
												</div>
											</spring:bind>
											<img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"> <small>(In PDF format less than 2 MB)</small> <span class="text-danger">*</span>
											<div class="custom-file mt-2">
												<input type="file" class="custom-file-input" onblur="emptyRegiOrLicenseFile()" id="regiOrLicenseFile" name="regiOrLicenseFileName" onchange="return ValidateSize(event)" disabled="disabled" />
												<form:label path="regiOrLicenseFileName" class="custom-file-label" for="regiOrLicenseFile" id="regiOrLicenseFile1">Choose file</form:label>
											</div>
											<span id="RegiOrLicenseFile" class="text-danger color:red"></span>
										</div>
									</div>
									<hr class="mt-4 mb-4">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-bordered">
													<tbody>
														<tr>
															<td>
																Break-Up of Project Cost And Means<br> of Financing <small>(In PDF format less than 2MB)</small><img src="images/pdf-icon.png" class="pdf-icon" id="pdficon" alt="pdf-icon"> <br> <small><a href="./downloadmofFile/doc">Download Template</a></small>
															</td>
															<td>
																<div class="custom-file">
																	<input type="file" name="invFilename" class="custom-file-input" onblur="emptyFile()" id="brkupmofFile"></input> <label class="custom-file-label" id="chooseInvfile" for="invFilename">Choose file</label> <span id="brkupmoffile" class="text-danger"></span>
																</div>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-5">
											<a href="./projectDetails" onclick="return confirm('Are you sure you want to go Project Details?')" class="common-default-btn mt-3">Previous</a>
										</div>
										<div class="col-sm-7 text-right">
											<c:if test="${not empty investmentId}">
												<button type="button" class="common-btn mt-3" onclick="editInvestmentDetails()">
													<i class="fa fa-edit"></i>Edit
												</button>
											</c:if>
											<button id="saveButton" onclick="return submitForm(); validateInvestmentForm()" class="common-btn mt-3" formaction="saveInvestmentDetails">Save and Next</button>
										</div>
									</div>
								</div>
							</form:form>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script>
		if (window.history.replaceState) {
			window.history.replaceState(null, null, window.location.href);
		}
	</script>
	<script>$(function () {
	  $("#datepicker").datepicker({ 
	        autoclose: true, 
	        todayHighlight: true
	  });

	  $("#datepicker1").datepicker({ 
	        autoclose: true, 
	        todayHighlight: true
	  });
	  
	  $("#datepicker2").datepicker({ 
	        autoclose: true, 
	        todayHighlight: true
	  });

	});
	</script>
</html>
<%@ include file="footer.jsp"%>