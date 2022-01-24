<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="com.webapp.ims.model.PhaseWiseInvestmentDetails"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<title>Investment Details</title>

<%@ include file="head.jsp"%>

<script src="js/number.js"></script>
<script src="js/projectDetails.js"></script>
<style type="text/css">
.error {
	color: red;
	font-size: 12px;
}

.disable_a_href {
	pointer-events: none;
}
</style>

<script type="text/javascript">

//End Datepicker

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

		if (!calculate()) {
			document.getElementById('invFci').focus();

			errorCounter++;
		}
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

		if (invIndType === 'Large') //invplantandmachcost  regionName
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
		var totalCost = document.getElementById("invTotalProjCost").value;
		var bkmoffile = document.getElementById("chooseInvfile").innerHTML;
		var phaseApply = document.getElementById("pwApply").value;
		var fixcapinv = document.getElementById("invFci").value

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

		if (totalCost == null || totalCost == '') {
			document.getElementById('invtotalprojcost').innerHTML = "Total Cost of Project is required";
			document.getElementById('invTotalProjCost').focus();
			return false;
		} else {
			document.getElementById('invtotalprojcost').innerHTML = "";
		}

		if (landCost == null || landCost == '') {
			document.getElementById('invlandcost').innerHTML = "Land Cost is required";
			document.getElementById('invLandCost').focus();
			return false;
		} else {
			document.getElementById('invlandcost').innerHTML = "";
		}

		if (buildingCost == null || buildingCost == '') {
			document.getElementById('invbuildingcost').innerHTML = "Building Site Development & Civil Works Cost is required";
			document.getElementById('invBuildingCost').focus();
			return false;
		} else {
			document.getElementById('invbuildingcost').innerHTML = "";
		}

		if (plantAndMachCost == null || plantAndMachCost == '') {
			document.getElementById('invplantandmachcost').innerHTML = "Plant and Machinery cost is required";
			document.getElementById('invPlantAndMachCost').focus();
			return false;
		}

		else {
			document.getElementById('invplantandmachcost').innerHTML = "";
		}

		if (otherCost == null || otherCost == '') {
			document.getElementById('invothercost').innerHTML = "Miscellaneous Fixed Asset(MFA) is required";
			document.getElementById('invOtherCost').focus();
			return false;
		} else {
			document.getElementById('invothercost').innerHTML = "";
		}

		var regiOrLicenseFile1 = document.getElementById("regiOrLicenseFile1").innerHTML;
		if (regiOrLicenseFile1 == null || regiOrLicenseFile1 == ''
				|| regiOrLicenseFile1 == 'Choose file') {
			document.getElementById('RegiOrLicenseFile').innerHTML = "Enclose UAM or IEM Report is Required.";
			document.getElementById('regiOrLicenseFile').focus();
			return false;
		}

		if (phaseApply == null || phaseApply == '') {
			document.getElementById('pwapply').innerHTML = "Phasewise Investment is required";
			document.getElementById('pwApply').focus();
			return false;
		} else {
			document.getElementById('pwapply').innerHTML = "";
		}

		<c:if test="${empty pwInvList}">
		if (phaseApply == 'Yes') {
			alert("Please fill Phasewise Investment Details or select as No");
			return false;
		}
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
		var phaseno = $("#pwPhaseNo").val();
		var productName = $("#pwProductName").val();
		var capacity = $("#pwCapacity").val();
		var unit = $("#pwUnit").val();
		var fci = $("#pwFci").val();
		var propProductDate = $("#pwPropProductDate").val();

		if (phaseno == null || phaseno == '') {
			document.getElementById('pwphaseno').innerHTML = "Phase Number is required";
			document.getElementById('pwPhaseNo').focus();
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

		//Not null or empty validation
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

		if (fci == null || fci == '') {
			document.getElementById('pwfci').innerHTML = "Proposed capital investment is required";
			document.getElementById('pwFci').focus();
			return false;
		}

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
		return true;
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
	}
	function emptyFile() {
		$('#brkupmoffile').text("");
	}

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

		} else {
			return false
		}

		
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
									<li><a href="./getIdByTabs33?authoTab=authoTab"
										class="filled"> <span>1</span>Authorized Signatory Details
									</a></li>
									<li><a href="./getIdByTabs34?busiTab=busiTab"
										class="filled"> <span>2</span>Business Entity Details
									</a></li>
									<li><a href="./getIdByTabs35?projTab=projTab"
										class="filled"> <span>3</span>Project Details
									</a></li>
									<li><a href="#" class="active"><span>4</span>Investment
											Details</a></li>
									<li><c:if test="${not empty PEflag}">
											<a href="./getIdByTabs36?propoTab=propoTab" class="filled"><span>5</span>Proposed
												Employment Details</a>
										</c:if> <c:if test="${empty PEflag}">
											<a href="./getIdByTabs36?propoTab=propoTab"><span>5</span>Proposed
												Employment Details</a>
										</c:if></li>
								</ul>
							</div>
							<form:form modelAttribute="investmentDetails" id="invForm"
								name="InvestmentForm" autocomplete="off" method="POST"
								enctype="multipart/form-data">
								<div class="tab-content py-2">
									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Investment Details</h3>
										</div>
									</div>
									<div class="row">

										<div class="col-md-6 col-lg-4 col-xl-4">
											<div class="form-group">
												<form:label path="invIndType">Category of Industrial Undertaking  <span>*</span>
												</form:label>
												<form:select id="invIndType" path="invIndType"
													onchange="return validateDate1();"
													onblur="return  emptyCategory()" class="form-control"
													name="invIndType">
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
											<h3 class="common-heading">Fixed Capital Investment
												Component</h3>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<form:label path="invLandCost">Land Cost 
													<span>*</span>
												</form:label>
												<form:input id="invLandCost" path="invLandCost"
													placeholder="Enter Amount in INR"
													onchange="return validateInvestmentForm()" maxlength="12"
													class="form-control fixed-total-input "></form:input>
												<small class="mt-2 d-block text-info" id="words"></small> <span
													id="invlandcost" class="text-danger"></span>
												<form:errors path="invLandCost" cssClass="error" />
											</div>
										</div>

										<div class="col-md-6">
											<div class="form-group">
												<form:label path="invBuildingCost">Building Site Development & Civil Works Cost 
													<span>*</span>
												</form:label>
												<form:input id="invBuildingCost" path="invBuildingCost"
													placeholder="Enter Amount in INR"
													onblur="emptyBuildingCost()"
													onkeypress="return validateNumberField()" maxlength="12"
													class="form-control fixed-total-input "></form:input>
												<small class="mt-2 d-block text-info" id="words1"></small> <span
													id="invbuildingcost" class="text-danger"></span>
												<form:errors path="invBuildingCost" cssClass="error" />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<form:label path="invPlantAndMachCost">Plant & Machinery Cost  
													<span>*</span>
												</form:label>
												<form:input path="invPlantAndMachCost"
													placeholder="Enter Amount in INR"
													class="form-control fixed-total-input "
													onchange="calculate();"
													onkeypress="return validateNumberField()" maxlength="12"
													id="invPlantAndMachCost"></form:input>
												<span id="invplantandmachcost" class="text-danger"></span> <small
													class="mt-2 d-block text-info" id="words2"></small>
												<form:errors path="invPlantAndMachCost" cssClass="error" />
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<form:label path="invOtherCost">Miscellaneous Fixed Asset(MFA)
													<span>*</span>
												</form:label>
												<form:input id="invOtherCost" path="invOtherCost"
													placeholder="Enter Amount in INR"
													onchange="return calculate(); "
													onkeypress="return validateNumberField()" maxlength="12"
													class="form-control fixed-total-input "></form:input>
												<small class="mt-2 d-block text-info" id="words3"></small> <span
													id="invothercost" class="text-danger"></span>
												<form:errors path="invOtherCost" cssClass="error" />
											</div>
										</div>
									</div>


									<div class="row">
										<div class="col-md-6">
											<div class="form-group ">
												<form:label path="invFci">Proposed Capital Investment
													<span>*</span>
												</form:label>
												<form:input id="invFci" path="invFci" readonly="true"
													onchange="return calculate();validateInvestmentForm(); capinvestAmount()"
													class="form-control fixed-total"
													onkeypress="return validateNumberField()"></form:input>
												<small class="mt-2 d-block text-info"></small> <span
													id="invfciSpan" class="text-danger"></span>
												<form:errors path="invFci" cssClass="error" />
											</div>
										</div>

										<div class="col-md-6">
											<div class="form-group">
												<form:label path="invTotalProjCost">Total Cost of Project
													<span>*</span>
												</form:label>
												<form:input id="invTotalProjCost" path="invTotalProjCost"
													onkeypress="return validateNumberField()"
													
													placeholder="Enter Amount in INR" class="form-control "></form:input>
												<small class="mt-2 d-block text-info" id="words4"></small> <span
													id="invtotalprojcost" class="text-danger"></span>
												<form:errors path="invTotalProjCost" cssClass="error" />
											</div>
										</div>
									</div>




									<hr class="mb-4 mt-4 thick-line">

									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<form:label path="invCommenceDate">Indicate Opted Cut-off date investment <span>*</span>
													<a href="javascript:void(0);" class="remove-row"
														data-toggle="tooltip" title=""
														data-original-title="Enter the date of commencement of investment for the project"><i
														class="fa fa-info-circle text-info"></i></a>
												</form:label>
												<form:input path="invCommenceDate" type="date"
													onblur="return validateDate();" onchange="capinveffDate()"
													class="form-control" id="invCommenceDate"></form:input>
												<span id="invcommencedate" class="text-danger"></span>

												<c:if test="${not empty commencepastdatemsg}">
													<span class="text-danger">${commencepastdatemsg}</span>
												</c:if>
												<c:if test="${not empty commenceperiodmsg}">
													<span class="text-danger">${commenceperiodmsg}</span>
												</c:if>

												<form:errors path="invCommenceDate" cssClass="error" />
											</div>
										</div>


										<div class="col-md-6">
											<div class="form-group">
												<form:label path="propCommProdDate">Proposed Date of Commencement of Commercial Production <span>*</span>
													<a href="javascript:void(0);" class="remove-row"
														data-toggle="tooltip" title=""
														data-original-title="Please enter the date on which the industrial undertaking will start commercial production."><i
														class="fa fa-info-circle text-info"></i></a>
												</form:label>
												<form:input id="propCommProdDate" path="propCommProdDate"
													onblur="return validateDate1();" type="date"
													class="form-control"></form:input>
												<span id="propcommproddate" class="text-danger"></span>
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
													<a href="javascript:void(0);" class="remove-row"
														data-toggle="tooltip" title=""
														data-original-title="Atleast 40% of eligible Capital Investment shall have to be undertaken after effective date "><i
														class="fa fa-info-circle text-info"></i></a>
												</form:label>
												<form:input type="text" path="capInvAmt" readonly="false"
													onchange="return capinvestAmount()"
													placeholder="Please Enter Amount(INR)"
													onkeypress="return validateNumberField()"
													class="form-control" name=""></form:input>
												<span id="capinvamt" class="text-danger"></span>
												<form:errors path="capInvAmt" cssClass="error" />
											</div>
										</div>
									</div>

									<hr class="mb-4 mt-4 thick-line">

									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Registration or License for
												setting up Industrial Undertaking</h3>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-12">
											<spring:bind path="regiOrLicense">
												<div
													class="custom-control custom-radio custom-control-inline">
													<input type="radio" name="regiOrLicense"
														class="custom-control-input" id="regiOrLicense"
														value="EncloseUAM" disabled="disabled " />
													<form:label path="regiOrLicense"
														class="custom-control-label" for="regiOrLicense">Udyam Registration Certificate <small>(for
															MSME)</small>
													</form:label>
												</div>
												<div
													class="custom-control custom-radio custom-control-inline">
													<input type="radio" name="regiOrLicense"
														class="custom-control-input" id="regiOrLicenseIEM"
														value="IEMcopy" disabled="disabled" />
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
													onblur="emptyRegiOrLicenseFile()" id="regiOrLicenseFile"
													name="regiOrLicenseFileName" onchange="return ValidateSize(event)"
													disabled="disabled" />
												<form:label path="regiOrLicenseFileName"
													class="custom-file-label" for="regiOrLicenseFile"
													id="regiOrLicenseFile1">Choose file</form:label>
											</div>
											<span id="RegiOrLicenseFile" class="text-danger color:red"></span>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-12 mt-4">
											<h3 class="common-heading">Phasewise Details</h3>
										</div>
									</div>

									<div class="row">
										<div class="col-md-7">
											<div class="form-group">
												<form:label path="pwApply">Phase Wise Investment as per Detailed Project
											Report <span>*</span> (If Applicable)
										</form:label>
												<form:select class="form-control" id="pwApply"
													onchange="return pwrestrictDate()" name="pwApply"
													path="pwApply">
													<form:option value="">Select</form:option>
													<form:option value="Yes">Yes</form:option>
													<form:option value="No">No</form:option>
												</form:select>
												<span id="pwapply" class="text-danger"></span>
											</div>
										</div>
									</div>
							</form:form>


							<%-- </form:form> --%>
							<!--<form:form modelAttribute="phaseWiseInvestmentDetails"
									onsubmit="return validatePwInvestmentForm()" autocomplete="off"
									name="pwInvestmentForm" action="addPwInvestment" method="POST"> -->
							<div class="if-applicable" id="Yes" style="display: none"
								id="if-applicable">
								<div class="row">

									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Phase Number</label>
											<form:input type="text" path="pwPhaseNo" name="pwPhaseNo"
												class="form-control" onblur="emptyPwInvestmentForm()"
												onkeypress="return validateNumberField()"
												placeholder="Enter Phase Number e.g - 1 or 2 or 3"></form:input>
											<span id="pwphaseno" class="text-danger"></span>
										</div>
									</div>

									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Product</label>
											<form:input type="text" path="pwProductName" maxlength="250"
												onblur="emptyPwInvestmentForm()" class="form-control"
												id="pwProductName" name="pwProductName"></form:input>
											<span id="pwproductname" class="text-danger"></span>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Capacity(PA)</label>
											<form:input type="text" path="pwCapacity" maxlength="12"
												onblur="emptyPwInvestmentForm()" class="form-control"
												placeholder="Enter Capacity" id="pwCapacity"
												onkeypress="return validateNumberField()" name="pwCapacity"></form:input>
											<span id="pwcapacity" class="text-danger"></span>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<label>Unit</label>

											<form:select class="form-control" path="pwUnit" id="pwUnit"
												name="pwUnit" onblur="emptyPwInvestmentForm()">
												<form:option value="">Select Unit</form:option>
												<form:options items="${unitListMap}" />
											</form:select>

											<span id="pwunit" class="text-danger"></span>
										</div>
									</div>
									<div class="col-md-6 col-lg-4 col-xl-4">
										<div class="form-group">
											<form:label path="pwFci">Proposed Capital Investment
											</form:label>
											<form:input path="pwFci" class="form-control" id="pwFci"
												placeholder="Enter Amount in INR" name="pwFci"
												onblur="emptyPwInvestmentForm()"
												onkeypress="return validateNumberField()" maxlength="12"></form:input>
											<span id="pwfci" class="text-danger"></span> <small
												class="mt-2 d-block text-info" id="words21"></small>
											<form:errors path="pwFci" cssClass="error" />
										</div>
									</div>

									<div class="col-md-8">
										<div class="form-group">
											<form:label path="pwPropProductDate">Proposed Date of Commercial Production</form:label>
											<form:input path="pwPropProductDate" type="date"
												onblur="emptyPwInvestmentForm()" name="pwPropProductDate"
												id="pwPropProductDate" class="form-control"></form:input>
											<span id="pwpropproductdate" class="text-danger"></span>
											<form:errors path="pwPropProductDate" cssClass="error" />
										</div>
									</div>

								</div>

								<c:if test="${empty edit}">
									<div class="row">
										<div class="col-md-6 col-lg-12 col-xl-12">
											<div class="form-group text-right mt-1">
												<button class="common-btn mt-3"
													onclick="return validatePwInvestmentForm();submitForm();"
													formaction="addPwInvestment" id="addButtom">Add</button>
											</div>
										</div>
									</div>
								</c:if>

								<c:if test="${not empty edit}">
									<div class="row">
										<div class="col-md-6 col-lg-12 col-xl-12">
											<div class="form-group text-right mt-1">
												<button class="common-btn mt-3"
													onclick="return validatePwInvestmentForm();submitForm();"
													formaction="addPwInvestment">Save</button>
											</div>
										</div>
									</div>
								</c:if>

							</div>

							<div class="row">
								<div class="col-sm-12">
									<div class="table-responsive mt-3">
										<table class="table table-stripped table-bordered"
											id="customFields3" tabindex="3">
											<c:if test="${not empty pwInvList}">
												<thead>
													<tr>
														<th>Phase</th>
														<th>Product</th>
														<th>Capacity</th>
														<th>Unit</th>
														<th>Proposed Capital Investment</th>
														<th>Proposed Date of Commercial Production</th>
														<th>Action</th>

													</tr>
												</thead>

												<%
													int i = 0;
												%>
												<tbody id="editButton">
													<c:forEach var="pwInvObj" items="${pwInvList}"
														varStatus="counter">
														<!-- Iterating the list using JSTL tag  -->
														<c:if test="${not empty pwInvList}">
															<c:if test="${fn:length(pwInvList)>0}">
																<tr>

																	<%-- <td class="text-center"><%=++i%></td> --%>
																	<td>${pwInvObj.pwPhaseNo}</td>
																	<td>${pwInvObj.pwProductName}</td>
																	<td>${pwInvObj.pwCapacity}</td>
																	<td>${pwInvObj.pwUnit}</td>
																	<td>${pwInvObj.pwFci}</td>
																	<td>${pwInvObj.pwPropProductDate}</td>
																	<%-- <td>${pwInvObj.pwCutoffProdAmt}</td> --%>

																	<td class="text-center">

																		<button class="border-0 bg-white"
																			formaction="${pageContext.request.contextPath }/editPwRow?selectedRow=${pwInvObj.pwPhaseNo}"
																			data-toggle="tooltip" title="Edit" class="action-btn"
																			id="editbutton">
																			<i class="fa fa-edit text-info"></i>
																		</button>

																		<button class="border-0 bg-white"
																			onclick="return confirm('Are you sure? Do you want to delete?')"
																			formaction="${pageContext.request.contextPath }/deletePwRow?selectedRow=${pwInvObj.pwPhaseNo}"
																			data-toggle="tooltip" title="Delete"
																			id="deleteButton">
																			<i class="fa fa-trash text-danger"></i>
																		</button>
																	</td>
																</tr>
															</c:if>
														</c:if>
													</c:forEach>
												</tbody>
											</c:if>
										</table>
									</div>
								</div>
							</div>


							<hr class="mt-4 mb-4">


							<div class="row">
								<div class="col-sm-12">
									<div class="table-responsive">
										<table class="table table-bordered">
											<tbody>
												<tr>
													<td>Break-Up of Project Cost & Means<br> of
														Financing <small>(In PDF format less than 2MB)</small><img
														src="images/pdf-icon.png" class="pdf-icon" id="pdficon"
														alt="pdf-icon"> <br> <small><a
															href="./downloadmofFile/doc">Download Template</a></small>
													</td>
													<td>
														<div class="custom-file">
															<input type="file" name="invFilename"
																class="custom-file-input" onblur="emptyFile()"
																id="brkupmofFile"></input> <label
																class="custom-file-label" id="chooseInvfile"
																for="invFilename">Choose file</label> <span
																id="brkupmoffile" class="text-danger"></span>
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
									<a href="./projectDetails"
										onclick="return confirm('Are you sure you want to go Project Details?')"
										class="common-default-btn mt-3">Previous</a>
								</div>
								<div class="col-sm-7 text-right">
									<c:if test="${not empty investmentId}">
										<button type="button" class="common-btn mt-3"
											onclick="editInvestmentDetails()">
											<i class="fa fa-edit"></i>Edit
										</button>
									</c:if>
									<button id="saveButton"
										onclick="return submitForm(); validateInvestmentForm()"
										class="common-btn mt-3" formaction="saveInvestmentDetails">Save
										and Next</button>
								</div>
							</div>
							<%-- 			</form:form> --%>
						</div>
						<div class="clearfix"></div>

					</div>
				</div>
			</div>
		</div>
		</form:form>
	</section>


	<script>
		if (window.history.replaceState) {
			window.history.replaceState(null, null, window.location.href);
		}
	</script>
</html>
<%@ include file="footer.jsp"%>