<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Disbursement Capital Investment</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<%@ include file="../head.jsp"%>

<script type="text/javascript">

window.onload = function() {
	if ('${radioBtnStatus}' == "Yes") {
		document.getElementById('YeslandPurchased').checked=true; 
		$(".hideBankFile").show();
	} 
	if ('${radioBtnStatus}' == "No") {
		document.getElementById('NolandPurchased').checked=true; 
		$(".hideBankFile").hide();
		}
}
</script>
<script type="text/javascript">
//Disable File Upload on click on No.
$(document).ready(function() {
	$("#NolandPurchasedLabel").click(function() {
		//$("#banksAuctionDoc").prop('disabled', true);
		$(".hideBankFile").hide();
		 document.getElementById("#banksAuctionDoc").style.display ='none';
		 
		
		document.getElementById("#banksAuctionDoc").style.display ='block';
		 $("#banksAuctionDoc-row").css("display", "none");		
		document.getElementById('choosefilebanksAuctionDoc').innerHTML = ' ';
		document.getElementById("banksAuctionDoc").innerHTML = '';

	  });
	$("#YeslandPurchasedLabel").click(function() {
		$("#banksAuctionDoc").prop('disabled', false);
		$(".hideBankFile").show();
		$("#banksAuctionDoc-row").css("display", "block");
		 document.getElementById("#banksAuctionDoc").style.display ='none';
		
		document.getElementById('choosefilebanksAuctionDoc').innerHTML = '';
		document.getElementById('banksAuctionDoc').innerHTML = '';
		
  });
});
</script>

<script type="text/javascript">
$(document).ready(function() {
    console.log( "document loaded" );
    if ("${existProjDisbursement.statutoryAuditorDocbase64File}" == null
			|| "${existProjDisbursement.statutoryAuditorDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilestatutoryAuditorDoc').innerHTML = '${existProjDisbursement.statutoryAuditorDoc}';
		document.getElementById("statutoryAuditorDoc").src = "data:image/png;base64,${existProjDisbursement.statutoryAuditorDocbase64File}";
	}

     if ("${existProjDisbursement.encCertificateFNamebase64File}" == null
			|| "${existProjDisbursement.encCertificateFNamebase64File}" == '') {

	} else {
		document.getElementById('chosseFileEncCertificateFName').innerHTML = '${existProjDisbursement.encCertificateFName}';
		document.getElementById("encCertificateFName").src = "data:image/png;base64,${existProjDisbursement.encCertificateFNamebase64File}";
	} 
	

    if ("${existProjDisbursement.purchasePriceDocbase64File}" == null
			|| "${existProjDisbursement.purchasePriceDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilepurchasePriceDoc').innerHTML = '${existProjDisbursement.purchasePriceDoc}';
		document.getElementById("purchasePriceDoc").src = "data:image/png;base64,${existProjDisbursement.purchasePriceDocbase64File}";
	}

    if ("${existProjDisbursement.stampDutyDocbase64File}" == null
			|| "${existProjDisbursement.stampDutyDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilestampDutyDoc').innerHTML = '${existProjDisbursement.stampDutyDoc}';
		document.getElementById("stampDutyDoc").src = "data:image/png;base64,${existProjDisbursement.stampDutyDocbase64File}";
	}

    if ("${existProjDisbursement.registrationFeeDocbase64File}" == null
			|| "${existProjDisbursement.registrationFeeDocbase64File}" == '') {

	} else {
		document.getElementById('choosefileregistrationFeeDoc').innerHTML = '${existProjDisbursement.registrationFeeDoc}';
		document.getElementById("registrationFeeDoc").src = "data:image/png;base64,${existProjDisbursement.registrationFeeDocbase64File}";
	}

    if ("${existProjDisbursement.banksAuctionDocbase64File}" == null
			|| "${existProjDisbursement.banksAuctionDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilebanksAuctionDoc').innerHTML = '${existProjDisbursement.banksAuctionDoc}';
		document.getElementById("banksAuctionDoc").src = "data:image/png;base64,${existProjDisbursement.banksAuctionDocbase64File}";
	}

    if ("${existProjDisbursement.civilWorksDocbase64File}" == null
			|| "${existProjDisbursement.civilWorksDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilecivilWorksDoc').innerHTML = '${existProjDisbursement.civilWorksDoc}';
		document.getElementById("civilWorksDoc").src = "data:image/png;base64,${existProjDisbursement.civilWorksDocbase64File}";
	}

    if ("${existProjDisbursement.machineryMiscDocbase64File}" == null
			|| "${existProjDisbursement.machineryMiscDocbase64File}" == '') {

	} else {
		document.getElementById('choosefilemachineryMiscDoc').innerHTML = '${existProjDisbursement.machineryMiscDoc}';
		document.getElementById("machineryMiscDoc").src = "data:image/png;base64,${existProjDisbursement.machineryMiscDocbase64File}";
	}
	
});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		if ('${natureOfProject}' == 'NewProject') {
			$(".newproject").show();
			$(".existingproject").hide();
		}

		if ('${natureOfProject}' == 'ExistingProject') {
			$(".newproject").hide();
			$(".existingproject").show();
		}

	});

	function disbNewProjTotal() {
		var sum = 0;
		$(".disbNewproj").each(function() {
			sum += +$(this).val();
		});
		$(".disbNewprojTotal").val(sum);
	}

	$(document).ready(disbNewProjTotal);
	$(document).on("keyup", disbNewProjTotal);
	$(document).on("change", disbNewProjTotal);

	function disbExistProjTotal() {
		var sum = 0;
		$(".disbExistproj").each(function() {
			sum += +$(this).val();
		});
		$(".disbExistprojTotal").val(sum);
	}

	$(document).ready(disbExistProjTotal);
	$(document).on("keyup", disbExistProjTotal);
	$(document).on("change", disbExistProjTotal);

	function disbCapInvLCTotal() {
		var sum = 0;
		$(".capInvLC").each(function() {
			sum += +$(this).val();
		});
		$(".totalLC", this).val(sum);
	}

	$(document).ready(disbCapInvLCTotal);
	$(document).on("keyup", disbCapInvLCTotal);
	$(document).on("change", disbCapInvLCTotal);

	function disbCapInvBCTotal() {
		var sum = 0;
		$(".capInvBC").each(function() {
			sum += +$(this).val();
		});
		$(".totalBC", this).val(sum);
	}

	$(document).ready(disbCapInvBCTotal);
	$(document).on("keyup", disbCapInvBCTotal);
	$(document).on("change", disbCapInvBCTotal);

	function disbCapInvPCTotal() {
		var sum = 0;
		$(".capInvPC").each(function() {
			sum += +$(this).val();
		});
		$(".totalPC", this).val(sum);
	}

	$(document).ready(disbCapInvPCTotal);
	$(document).on("keyup", disbCapInvPCTotal);
	$(document).on("change", disbCapInvPCTotal);

	function disbCapInvMFTotal() {
		var sum = 0;
		$(".capInvMF").each(function() {
			sum += +$(this).val();
		});
		$(".totalMF", this).val(sum);
	}

	$(document).ready(disbCapInvMFTotal);
	$(document).on("keyup", disbCapInvMFTotal);
	$(document).on("change", disbCapInvMFTotal);

	function disbCapInvTKTotal() {
		var sum = 0;
		$(".capInvTK").each(function() {
			sum += +$(this).val();
		});
		$(".totalTK", this).val(sum);
	}

	$(document).ready(disbCapInvTKTotal);
	$(document).on("keyup", disbCapInvTKTotal);
	$(document).on("change", disbCapInvTKTotal);

	function disbCapInvICTotal() {
		var sum = 0;
		$(".capInvIC").each(function() {
			sum += +$(this).val();
		});
		$(".totalIC", this).val(sum);
	}

	$(document).ready(disbCapInvICTotal);
	$(document).on("keyup", disbCapInvICTotal);
	$(document).on("change", disbCapInvICTotal);

	function disbCapInvPETotal() {
		var sum = 0;
		$(".capInvPE").each(function() {
			sum += +$(this).val();
		});
		$(".totalPE", this).val(sum);
	}

	$(document).ready(disbCapInvPETotal);
	$(document).on("keyup", disbCapInvPETotal);
	$(document).on("change", disbCapInvPETotal);

	function disbCapInvMCTotal() {
		var sum = 0;
		$(".capInvMC").each(function() {
			sum += +$(this).val();
		});
		$(".totalMC", this).val(sum);
	}

	$(document).ready(disbCapInvMCTotal);
	$(document).on("keyup", disbCapInvMCTotal);
	$(document).on("change", disbCapInvMCTotal);



	function disbCapdprTotal() {
		var sum = 0;
		$(".costdpr").each(function() {
			sum += +$(this).val();
		});
		$(".totaldpr", this).val(sum);
	}

	$(document).ready(disbCapdprTotal);
	$(document).on("keyup", disbCapdprTotal);
	$(document).on("change", disbCapdprTotal);
	

	function disbBankFITotal() {
		var sum = 0;
		$(".costBankFI").each(function() {
			sum += +$(this).val();
		});
		$(".totalBankFI", this).val(sum);
	}

	$(document).ready(disbBankFITotal);
	$(document).on("keyup", disbBankFITotal);
	$(document).on("change", disbBankFITotal);

	function disbCutOffTotal() {
		var sum = 0;
		$(".beforeCutOff").each(function() {
			sum += +$(this).val();
		});
		$(".totalCutOff", this).val(sum);
	}

	$(document).ready(disbCutOffTotal);
	$(document).on("keyup", disbCutOffTotal);
	$(document).on("change", disbCutOffTotal);


	function disbCutOffComProTotal() {
		var sum = 0;
		$(".cutOffComPro").each(function() {
			sum += +$(this).val();
		});
		$(".totalCutOffComPro", this).val(sum);
	}

	$(document).ready(disbCutOffComProTotal);
	$(document).on("keyup", disbCutOffComProTotal);
	$(document).on("change", disbCutOffComProTotal);



	function disbactualInvestmentTotal() {
		var sum = 0;
		$(".actualInvestment").each(function() {
			sum += +$(this).val();
		});
		$(".totalActualInvestment", this).val(sum);
	}

	$(document).ready(disbactualInvestmentTotal);
	$(document).on("keyup", disbactualInvestmentTotal);
	$(document).on("change", disbactualInvestmentTotal);
	


	function disbaddActualInvestmentTotal() {
		var sum = 0;
		$(".addActualInvestment").each(function() {
			sum += +$(this).val();
		});
		$(".totalAddActualInvestment", this).val(sum);
	}

	$(document).ready(disbaddActualInvestmentTotal);
	$(document).on("keyup", disbaddActualInvestmentTotal);
	$(document).on("change", disbaddActualInvestmentTotal);



	function disbtotalOfTotalTotal() {
		var sum = 0;
		$(".disTotalOfTotal").each(function() {
			sum += +$(this).val();
		});
		$(".totalOfTotal", this).val(sum);
	}

	$(document).ready(disbtotalOfTotalTotal);
	$(document).on("keyup", disbtotalOfTotalTotal);
	$(document).on("change", disbtotalOfTotalTotal);



	function disbamtInvestTotal() {
		var sum = 0;
		$(".amtInvest").each(function() {
			sum += +$(this).val();
		});
		$(".totalAmtInvest", this).val(sum);
	}

	$(document).ready(disbamtInvestTotal);
	$(document).on("keyup", disbamtInvestTotal);
	$(document).on("change", disbamtInvestTotal);



	function disbttlIncrementTotal() {
		var sum = 0;
		$(".ttlIncrement").each(function() {
			sum += +$(this).val();
		});
		$(".disTotalIncrement", this).val(sum);
	}

	$(document).ready(disbttlIncrementTotal);
	$(document).on("keyup", disbttlIncrementTotal);
	$(document).on("change", disbttlIncrementTotal);
	

	function validateNumericField() {
		return event.charCode > 47 && event.charCode < 58;

	}


	
	function BankDocsData() {

		 $("#banksAuctionDocMsg").text('');
		}
	
	function emptyCapInvErrMsg() {
		
		
		$("#costapplformXX").text('');
		$("#befcutoffXX").text('');
		$("#cutoffdatecomprodXX").text('');
		$("#addlinvXX").text('');
		
		$("#costapplformYY").text('');
		$("#befcutoffYY").text('');
		$("#cutoffdatecomprodYY").text('');
		$("#actInvYY").text('');
		$("#addlinvYY").text('');

		$("#costapplformPP").text('');
		$("#befcutoffPP").text('');
		$("#cutoffdatecomprodPP").text('');
		$("#actInvPP").text('');
		$("#addlinvPP").text('');

		$("#costapplformMM").text('');
		$("#befcutoffMM").text('');
		$("#cutoffdatecomprodMM").text('');
		$("#actInvMM").text('');
		$("#addlinvMM").text('');


		$("#costOfDprTT").text('');
		$("#costapplformTT").text('');
		$("#befcutoffTT").text('');
		$("#cutoffdatecomprodTT").text('');
		$("#actInvMTT").text('');
		$("#addlinvTT").text('');


		$("#costOfDprII").text('');
		$("#costapplformII").text('');
		$("#befcutoffII").text('');
		$("#cutoffdatecomprodII").text('');
		$("#actInvMTII").text('');
		$("#addlinvII").text('');

		$("#costOfDprPE").text('');
		$("#costapplformPE").text('');
		$("#befcutoffPE").text('');
		$("#cutoffdatecomprodPE").text('');
		$("#actInvPE").text('');
		$("#addlinvPE").text('');


		$("#costOfDprWC").text('');
		$("#costapplformWC").text('');
		$("#befcutoffWC").text('');
		$("#cutoffdatecomprodWC").text('');
		$("#actInvMTWC").text('');
		$("#addlinvWC").text('');

		$("#annTurnOverXX").text('');
		$("#totalLoanXX").text('');
		$("#totalInterrestXX").text('');


		$("#projDisPP").text('');
		$("#landIncrementLL").text('');
		$("#buildCastBB").text('');
		$("#buildIncrementBB").text('');
		$("#plantMachCostPP").text('');
		$("#plantIncrementPP").text('');
		$("#miscFixedAssetMsg").text('');
		$("#miscFixedIncrementMsg").text('');
		$("#infraFaciExistII").text('');
		$("#infraFaciII").text('');
		$("#infraIncrementII").text('');

		
	
			}

</script>


<script type="text/javascript">
	function submitForm()

	{
		 
		
		
		var errorCounter = 0;
		if (!validateCapInvIndUndertaking()) {

			//document.getElementById('invCommenceDate').focus();
			errorCounter++;
		}

		if (!validateNumericField()) {

			//document.getElementById('invCommenceDate').focus();
			errorCounter++;
		} else {
			document.getElementById("invForm").submit();

		}
	}

	   function disbCapInvLCTotal() {
		   var sum = 0;
		   $(".is-numeric").each(function() {
		   //alert("hi");
		   sum += +$(this).val();
		   });
	   }

		   $(document).ready(disbCapInvLCTotal);
		   $(document).on("keypress", disbCapInvLCTotal);
		   $(document).on("change", disbCapInvLCTotal);
	   </script>
<script type="text/javascript">

	   function validateCapInvIndUndertaking() {

		   if('${natureOfProject}' == 'ExistingProject'){
				var projDis = document.getElementById('projDis').value;
				if(projDis == null || projDis == "")
					{
					document.getElementById('projDisPP').innerHTML="Field is Required.";
					document.getElementById('projDis').focus();
					return false;
					}

				   var cutOffValue=document.getElementById('landIncrement').value;
		            if(cutOffValue == null || cutOffValue == "")
		            {
		             document.getElementById('landIncrementLL').innerHTML="Field is Required.";
		              document.getElementById('landIncrement').focus();
		              return false;
		             }
		             
		               var cutOffValue=document.getElementById('buildCast').value;
		            if(cutOffValue == null || cutOffValue == "")
		            {
		             document.getElementById('buildCastBB').innerHTML="Field is Required.";
		              document.getElementById('buildCast').focus();
		              return false;
		             }
		             
		                var cutOffValue=document.getElementById('buildIncrement').value;
		            if(cutOffValue == null || cutOffValue == "")
		            {
		             document.getElementById('buildIncrementBB').innerHTML="Field is Required.";
		              document.getElementById('buildIncrement').focus();
		              return false;
		             }
		             
		             var cutOffValue=document.getElementById('plantMachCost').value;
		            if(cutOffValue == null || cutOffValue == "")
		            {
		             document.getElementById('plantMachCostPP').innerHTML="Field is Required.";
		              document.getElementById('plantMachCost').focus();
		              return false;
		             }
		             
		             var cutOffValue=document.getElementById('plantIncrement').value;
		            if(cutOffValue == null || cutOffValue == "")
		            {
		             document.getElementById('plantIncrementPP').innerHTML="Field is Required.";
		              document.getElementById('plantIncrement').focus();
		              return false;
		             }


                var cutOffValue=document.getElementById('miscFixedAsset').value;
		            if(cutOffValue == null || cutOffValue == "")
		            {
		             document.getElementById('miscFixedAssetMsg').innerHTML="Field is Required.";
		              document.getElementById('miscFixedAsset').focus();
		              return false;
		             }

		            var cutOffValue=document.getElementById('miscFixedIncrement').value;
		            if(cutOffValue == null || cutOffValue == "")
		            {
		             document.getElementById('miscFixedIncrementMsg').innerHTML="Field is Required.";
		              document.getElementById('miscFixedIncrement').focus();
		              return false;
		             }
		             
		             
		            var cutOffValue=document.getElementById('infraFaciExist').value;
		            if(cutOffValue == null || cutOffValue == "")
		            {
		             document.getElementById('infraFaciExistII').innerHTML="Field is Required.";
		              document.getElementById('infraFaciExist').focus();
		              return false;
		             }
		             
		              var cutOffValue=document.getElementById('infraFaci').value;
		            if(cutOffValue == null || cutOffValue == "")
		            {
		             document.getElementById('infraFaciII').innerHTML="Field is Required.";
		              document.getElementById('infraFaci').focus();
		              return false;
		             }
		             
		             
		              var cutOffValue=document.getElementById('infraIncrement').value;
		            if(cutOffValue == null || cutOffValue == "")
		            {
		             document.getElementById('infraIncrementII').innerHTML="Field is Required.";
		              document.getElementById('infraIncrement').focus();
		              return false;
		             }

		   }
		   
		   if('${natureOfProject}' == 'NewProject')
			   {
			   var cutOffValue=document.getElementById('newprojInfraId').value;
	            if(cutOffValue == null || cutOffValue == "")
	            {
	             document.getElementById('newprojInfraIdMsg').innerHTML="Field is Required.";
	              document.getElementById('newprojInfraId').focus();
	              return false;
	             }

			   }

		 		   if(!StaAuditorDoc(document.getElementById('statutoryAuditorDoc'))){  
		 				return false;
		 			}


				
					var totalLoan=document.getElementById('totalLoan').value;
					if(totalLoan == null || totalLoan == "")
						{
						document.getElementById('totalLoanXX').innerHTML="Total Loan on Project is Required.";
						document.getElementById('totalLoan').focus();
						return false;
						}

					var totalLoan=document.getElementById('totalInterest').value;
					if(totalLoan == null || totalLoan == "")
						{
						document.getElementById('totalInterrestXX').innerHTML="Total Interest on Loan is Required.";
						document.getElementById('totalInterest').focus();
						return false;
						}

			var lcacostOfProject = document.getElementById('costapplform').value;
			if (lcacostOfProject == null || lcacostOfProject == '') {
				document.getElementById('costapplformXX').innerHTML = "Field is Required.";
				document.getElementById('costapplform').focus();
				return false;
			} 
			
			var cutOffValue=document.getElementById('befcutoff').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('befcutoffXX').innerHTML="Field is Required.";
				document.getElementById('befcutoff').focus();
				return false;
				}

			var cutOffValue=document.getElementById('cutoffdatecomprod').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('cutoffdatecomprodXX').innerHTML="Field is Required.";
				document.getElementById('cutoffdatecomprod').focus();
				return false;
				}

			var cutOffValue=document.getElementById('addlinv').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('addlinvXX').innerHTML="Field is Required.";
				document.getElementById('addlinv').focus();
				return false;
				}


			var cutOffValue=document.getElementById('costapplformBC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costapplformYY').innerHTML="Field is Required.";
				document.getElementById('costapplformBC').focus();
				return false;
				}

			var cutOffValue=document.getElementById('befcutoffBC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('befcutoffYY').innerHTML="Field is Required.";
				document.getElementById('befcutoffBC').focus();
				return false;
				}


			var cutOffValue=document.getElementById('cutoffdatecomprodBC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('cutoffdatecomprodYY').innerHTML="Field is Required.";
				document.getElementById('cutoffdatecomprodBC').focus();
				return false;
				}

			var cutOffValue=document.getElementById('actInvBC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('actInvYY').innerHTML="Field is Required.";
				document.getElementById('actInvBC').focus();
				return false;
				}

			var cutOffValue=document.getElementById('addlinvBC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('addlinvYY').innerHTML="Field is Required.";
				document.getElementById('addlinvBC').focus();
				return false;
				}
			
			var cutOffValue=document.getElementById('costapplformPC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costapplformPP').innerHTML="Field is Required.";
				document.getElementById('costapplformPC').focus();
				return false;
				}
			
			var cutOffValue=document.getElementById('befcutoffPMC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('befcutoffPP').innerHTML="Field is Required.";
				document.getElementById('befcutoffPMC').focus();
				return false;
				}

			var cutOffValue=document.getElementById('cutoffdatecomprodPMC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('cutoffdatecomprodPP').innerHTML="Field is Required.";
				document.getElementById('cutoffdatecomprodPMC').focus();
				return false;
				}


			var cutOffValue=document.getElementById('actInvPMC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('actInvPP').innerHTML="Field is Required.";
				document.getElementById('actInvPMC').focus();
				return false;
				}
			
			var cutOffValue=document.getElementById('addlinvPMC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('addlinvPP').innerHTML="Field is Required.";
				document.getElementById('addlinvPMC').focus();
				return false;
				}

			var cutOffValue=document.getElementById('costapplformMFA').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costapplformMM').innerHTML="Field is Required.";
				document.getElementById('costapplformMFA').focus();
				return false;
				}


			var cutOffValue=document.getElementById('befcutoffMFA').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('befcutoffMM').innerHTML="Field is Required.";
				document.getElementById('befcutoffMFA').focus();
				return false;
				}
			
			var cutOffValue=document.getElementById('cutoffdatecomprodMFA').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('cutoffdatecomprodMM').innerHTML="Field is Required.";
				document.getElementById('cutoffdatecomprodMFA').focus();
				return false;
				}


			var cutOffValue=document.getElementById('actInvMFA').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('actInvMM').innerHTML="Field is Required.";
				document.getElementById('actInvMFA').focus();
				return false;
				}


			var cutOffValue=document.getElementById('addlinvMFA').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('addlinvMM').innerHTML="Field is Required.";
				document.getElementById('addlinvMFA').focus();
				return false;
				}

			var cutOffValue=document.getElementById('costOfDprTKF').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costOfDprTT').innerHTML="Field is Required.";
				document.getElementById('costOfDprTKF').focus();
				return false;
				}

			var cutOffValue=document.getElementById('costapplformTKF').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costapplformTT').innerHTML="field is Required.";
				document.getElementById('costapplformTKF').focus();
				return false;
				}


			var cutOffValue=document.getElementById('befcutoffTKF').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('befcutoffTT').innerHTML="Field is Required.";
				document.getElementById('befcutoffTKF').focus();
				return false;
				}

			var cutOffValue=document.getElementById('cutoffdatecomprodTKF').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('cutoffdatecomprodTT').innerHTML="Field is Required.";
				document.getElementById('cutoffdatecomprodTKF').focus();
				return false;
				}


			var cutOffValue=document.getElementById('actInvTKF').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('actInvTT').innerHTML="Field is Required.";
				document.getElementById('actInvTKF').focus();
				return false;
				}

			var cutOffValue=document.getElementById('addlinvTKF').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('addlinvTT').innerHTML="Field is Required.";
				document.getElementById('addlinvTKF').focus();
				return false;
				}

			

			var cutOffValue=document.getElementById('costOfDprICP').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costOfDprII').innerHTML="Field is Required.";
				document.getElementById('costOfDprICP').focus();
				return false;
				}

			var cutOffValue=document.getElementById('costapplformICP').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costapplformII').innerHTML="Field is Required.";
				document.getElementById('costapplformICP').focus();
				return false;
				}

			var cutOffValue=document.getElementById('befcutoffICP').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('befcutoffII').innerHTML="Field is Required.";
				document.getElementById('befcutoffICP').focus();
				return false;
				}

			var cutOffValue=document.getElementById('cutoffdatecomprodICP').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('cutoffdatecomprodII').innerHTML="Field is Required.";
				document.getElementById('cutoffdatecomprodICP').focus();
				return false;
				}


			var cutOffValue=document.getElementById('actInvICP').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('actInvII').innerHTML="Field is Required.";
				document.getElementById('actInvICP').focus();
				return false;
				}

			var cutOffValue=document.getElementById('addlinvICP').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('addlinvII').innerHTML="Field is Required.";
				document.getElementById('addlinvICP').focus();
				return false;
				}

			var cutOffValue=document.getElementById('costOfDprPPE').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costOfDprPE').innerHTML="Field is Required.";
				document.getElementById('costOfDprPPE').focus();
				return false;
				}



			
			var cutOffValue=document.getElementById('costapplformPPE').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costapplformPE').innerHTML="Field is Required.";
				document.getElementById('costapplformPPE').focus();
				return false;
				}

			var cutOffValue=document.getElementById('befcutoffPPE').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('befcutoffPE').innerHTML="Field is Required.";
				document.getElementById('befcutoffPPE').focus();
				return false;
				}

			var cutOffValue=document.getElementById('cutoffdatecomprodPPE').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('cutoffdatecomprodPE').innerHTML="Field is Required.";
				document.getElementById('cutoffdatecomprodPPE').focus();
				return false;
				}


			var cutOffValue=document.getElementById('actInvPPE').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('actInvPE').innerHTML="Field is Required.";
				document.getElementById('actInvPPE').focus();
				return false;
				}

			var cutOffValue=document.getElementById('addlinvPPE').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('addlinvPE').innerHTML="Field is Required.";
				document.getElementById('addlinvPPE').focus();
				return false;
				}
            var cutOffValue=document.getElementById('costOfDprMMWC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costOfDprWC').innerHTML="Field is Required.";
				document.getElementById('costOfDprMMWC').focus();
				return false;
				}


			var cutOffValue=document.getElementById('costapplformMMWC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('costapplformWC').innerHTML="Field is Required.";
				document.getElementById('costapplformMMWC').focus();
				return false;
				}

			var cutOffValue=document.getElementById('befcutoffMMWC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('befcutoffWC').innerHTML="Field is Required.";
				document.getElementById('befcutoffMMWC').focus();
				return false;
				}

			var cutOffValue=document.getElementById('cutoffdatecomprodMMWC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('cutoffdatecomprodWC').innerHTML="Field is Required.";
				document.getElementById('cutoffdatecomprodMMWC').focus();
				return false;
				}

			var cutOffValue=document.getElementById('actInvMMWC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('actInvWC').innerHTML="Field is Required.";
				document.getElementById('actInvMMWC').focus();
				return false;
				}
			
			var cutOffValue=document.getElementById('addlinvMMWC').value;
			if(cutOffValue == null || cutOffValue == "")
				{
				document.getElementById('addlinvWC').innerHTML="Field is Required.";
				document.getElementById('addlinvMMWC').focus();
				return false;
				}
			
			if ('${applyStatus}' == "Yes") {
			 if(!EncloseCertificateDoc(document.getElementById('encCertificateFName'))){  
				return false;
			} 
			   }	
			 if(!RegisteredPriceDoc(document.getElementById('purchasePriceDoc'))){  
					return false;
				}

			   if(!ReceiptStampDutyDoc(document.getElementById('stampDutyDoc'))){    


				  return false;
				}
			   if(!ReceiptRegisteredFeeDoc(document.getElementById('registrationFeeDoc'))){    


				 return false;
				}
		
			   var pmvalue = document.getElementsByName('landPurchaseFrUPSIDC');
			   for (var radio of pmvalue) {
				    if (radio.checked) {
				  
				    	if (radio.value == 'Yes') {

							   if(!LandPurchasedBankAuctionDoc(document.getElementById('banksAuctionDoc'))){     

									return false;
								}
				}
				    
				    }
				  }
		
			   if(!DetailedCostEstimatesDoc(document.getElementById('civilWorksDoc'))){       

					 return false;
				}

			   if(!CostOfProposedDoc(document.getElementById('machineryMiscDoc'))){    


					return false;
				}

				var r = confirm("Are you Sure you want to Save the Form?");
				
				if (r == true) {
					alert("Capital Investment data are Saved Succesfully");
				} else {
					return false
				}
			

		   //Gyan
			 $('span.error_msg').html('');
			    var success = true;

			    if ("${existProjDisbursement.statutoryAuditorDocbase64File}" != '') {
					return true;
				} 

	   }
	   
	   </script>
<script type="text/javascript">

		$(function() {
			var ctypeSMALL = '${applyStatus}';

			if (ctypeSMALL == 'No') {
				$(".in-case-of-small-row").hide();
			}
        });
        
	   </script>

<script type="text/javascript">
$(document).ready(function(){

	var empapp = '${empapp}';
	var capiapp = '${capiapp}';
	var applicantID = '${applicantID}';
	if (applicantID != '' && applicantID != null){
		$("#applicantIDActive").addClass("filled");
		}
	if (capiapp != '' && capiapp != null){
		$("#capiappActive").addClass("filled");
		}
	if (empapp != '' && empapp != null){
		$("#empappActive").addClass("filled");
		}
	
	});
	

</script>

<script type="text/javascript">


	
function LandPurchasedBankAuctionDoc(file) {   
	 var landBanksAuctionDoc = document.getElementById('banksAuctionDoc').value;
	 var fileSize = document.getElementById("banksAuctionDoc").files[0];
	 var maxSize = '5000';

	 if('${existProjDisbursement.banksAuctionDocbase64File}' != '' && landBanksAuctionDoc == ''){
			return true;
		}
		
	 if(landBanksAuctionDoc !=null && landBanksAuctionDoc !=''){
	 var ext = landBanksAuctionDoc.split('.').pop();
	 if (ext == "pdf") {
		// value is ok, use it
	} else {
		document.getElementById('banksAuctionDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
		document.getElementById('banksAuctionDoc').focus();
		return false;
	}
			
	var FileSize = (fileSize.size/1024)/1024; // in MB
	FileSize = FileSize.toFixed(5);
  if (FileSize >5) {
  	document.getElementById('banksAuctionDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
  	document.getElementById('banksAuctionDoc').focus();
		return false;
  }
  
	}  else{
		document.getElementById('banksAuctionDocMsg').innerHTML = "Land Purchased from UPSIDC/DI/FIs/Banks in auction, document is Required.";
		document.getElementById('banksAuctionDoc').focus();
		return false;
	  } 
		  
	    document.getElementById('banksAuctionDocMsg').innerHTML = "";
	    return true; 
	 
}

	function StaAuditorDoc(file) {
		 var statutoryAudDoc = document.getElementById('statutoryAuditorDoc').value;
		 var fileSize = document.getElementById("statutoryAuditorDoc").files[0];
		 var maxSize = '5000';

		
		 if(statutoryAudDoc !=null && statutoryAudDoc !=''){
		 var ext = statutoryAudDoc.split('.').pop();
		 if (ext == "pdf") {
			// value is ok, use it
		} else {
			document.getElementById('statutoryAuditorDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
			document.getElementById('statutoryAuditorDoc').focus();
			return false;
		}
				
		var FileSize = (fileSize.size/1024)/1024; // in MB
		FileSize = FileSize.toFixed(5);
	   if (FileSize >5) {
	   	document.getElementById('statutoryAuditorDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	   	document.getElementById('statutoryAuditorDoc').focus();
			return false;
	   }
	   
		}  else{
			 if('${existProjDisbursement.statutoryAuditorDocbase64File}' != '' && statutoryAudDoc == ''){
					return true;
				}
				
			document.getElementById('statutoryAuditorDocMsg').innerHTML = "Statutory Auditor's Certificate document is Required.";
			document.getElementById('statutoryAuditorDoc').focus();
			return false;
		  } 
			  
		    document.getElementById('statutoryAuditorDocMsg').innerHTML = "";
		    return true; 
		 
	}

	function EncloseCertificateDoc(file) {
		 var encCertificateDoc = document.getElementById('encCertificateFName').value;
		 var fileSize = document.getElementById("encCertificateFName").files[0];
		 var maxSize = '5000';

		 if(encCertificateDoc !=null && encCertificateDoc !=''){
		 var ext = encCertificateDoc.split('.').pop();
		 if (ext == "pdf") {
			// value is ok, use it
		} else {
			document.getElementById('encCertificateFNameMsg').innerHTML = "Please Upload Support documnet in PDF Format";
			document.getElementById('encCertificateFName').focus();
			return false;
		}
				
		var FileSize = (fileSize.size/1024)/1024; // in MB
		FileSize = FileSize.toFixed(5);
	   if (FileSize >5) {
	   	document.getElementById('encCertificateFNameMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	   	document.getElementById('encCertificateFName').focus();
			return false;
	   }
	   
		}  else{

			   if('${existProjDisbursement.encCertificateFNamebase64File}' != '' && encCertificateDoc == ''){
					return true;
				}
				
			document.getElementById('encCertificateFNameMsg').innerHTML = "Enclose Certificate  document is Required.";
			document.getElementById('encCertificateFName').focus();
			return false;
		  } 
			  
		    document.getElementById('encCertificateFNameMsg').innerHTML = "";
		    return true; 
		 
	}
	function RegisteredPriceDoc(file) {
		 var purchaseDoc = document.getElementById('purchasePriceDoc').value;
		 var fileSize = document.getElementById("purchasePriceDoc").files[0];
		 var maxSize = '5000';

		 if(purchaseDoc !=null && purchaseDoc !=''){
		 var ext = purchaseDoc.split('.').pop();
		 if (ext == "pdf") {
			// value is ok, use it
		} else {
			document.getElementById('purchasePriceDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
			document.getElementById('purchasePriceDoc').focus();
			return false;
		}
				
		var FileSize = (fileSize.size/1024)/1024; // in MB
		FileSize = FileSize.toFixed(5);
	   if (FileSize >5) {
	   	document.getElementById('purchasePriceDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	   	document.getElementById('purchasePriceDoc').focus();
			return false;
	   }
	   
		}  else{
			 if('${existProjDisbursement.purchasePriceDocbase64File}' != '' && purchaseDoc == ''){
					return true;
				}
				
			document.getElementById('purchasePriceDocMsg').innerHTML = "Registered document is Required.";
			document.getElementById('purchasePriceDoc').focus();
			return false;
		  } 
			  
		    document.getElementById('purchasePriceDocMsg').innerHTML = "";
		    return true; 
		 
	}

	function ReceiptStampDutyDoc(file) {   
		 var reciStampDutyDoc = document.getElementById('stampDutyDoc').value;  
		 var fileSize = document.getElementById("stampDutyDoc").files[0];
		 var maxSize = '5000';

		 if(reciStampDutyDoc !=null && reciStampDutyDoc !=''){
		 var ext = reciStampDutyDoc.split('.').pop();
		 if (ext == "pdf") {
			// value is ok, use it
		} else {
			document.getElementById('stampDutyDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
			document.getElementById('stampDutyDoc').focus();
			return false;
		}
				
		var FileSize = (fileSize.size/1024)/1024; // in MB
		FileSize = FileSize.toFixed(5);
	   if (FileSize >5) {
	   	document.getElementById('stampDutyDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	   	document.getElementById('stampDutyDoc').focus();
			return false;
	   }
	   
		}  else{
			 if('${existProjDisbursement.stampDutyDocbase64File}' != '' && reciStampDutyDoc == ''){
					return true;
				}
			document.getElementById('stampDutyDocMsg').innerHTML = "Receipt of Payment of stamp duty document is Required.";
			document.getElementById('stampDutyDoc').focus();
			return false;
		  } 
			  
		    document.getElementById('stampDutyDocMsg').innerHTML = "";
		    return true; 
		 
	}

	function ReceiptRegisteredFeeDoc(file) {    
		 var reciRegistrationFeeDocDoc = document.getElementById('registrationFeeDoc').value;  
		 var fileSize = document.getElementById("registrationFeeDoc").files[0];
		 var maxSize = '5000';

		 if(reciRegistrationFeeDocDoc !=null && reciRegistrationFeeDocDoc !=''){
		 var ext = reciRegistrationFeeDocDoc.split('.').pop();
		 if (ext == "pdf") {
			// value is ok, use it
		} else {
			document.getElementById('registrationFeeDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
			document.getElementById('registrationFeeDoc').focus();
			return false;
		}
				
		var FileSize = (fileSize.size/1024)/1024; // in MB
		FileSize = FileSize.toFixed(5);
	   if (FileSize >5) {
	   	document.getElementById('registrationFeeDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	   	document.getElementById('registrationFeeDoc').focus();
			return false;
	   }
	   
		}  else{
			 if('${existProjDisbursement.registrationFeeDocbase64File}' != '' && reciRegistrationFeeDocDoc == ''){
					return true;
				}
				
			document.getElementById('registrationFeeDocMsg').innerHTML = "Receipt of Payment of Registration Fee document is Required.";
			document.getElementById('registrationFeeDoc').focus();
			return false;
		  } 
			  
		    document.getElementById('registrationFeeDocMsg').innerHTML = "";
		    return true; 
		 
	}

	function DetailedCostEstimatesDoc(file) {        
		 var detailCostWorksDoc = document.getElementById('civilWorksDoc').value;  
		 var fileSize = document.getElementById("civilWorksDoc").files[0];
		 var maxSize = '5000';

		 if(detailCostWorksDoc !=null && detailCostWorksDoc !=''){
		 var ext = detailCostWorksDoc.split('.').pop();
		 if (ext == "pdf") {
			// value is ok, use it
		} else {
			document.getElementById('civilWorksDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
			document.getElementById('civilWorksDoc').focus();
			return false;
		}
				
		var FileSize = (fileSize.size/1024)/1024; // in MB
		FileSize = FileSize.toFixed(5);
	   if (FileSize >5) {
	   	document.getElementById('civilWorksDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	   	document.getElementById('civilWorksDoc').focus();
			return false;
	   }
	   
		}  else{
			 if('${existProjDisbursement.civilWorksDocbase64File}' != '' && detailCostWorksDoc == ''){
					return true;
				}

				
			document.getElementById('civilWorksDocMsg').innerHTML = "Detailed Cost Estimates document is Required.";
			document.getElementById('civilWorksDoc').focus();
			return false;
		  } 
			  
		    document.getElementById('civilWorksDocMsg').innerHTML = "";
		    return true; 
		 
	}
	
	function CostOfProposedDoc(file) {         
		 var costMachineryMiscDoc = document.getElementById('machineryMiscDoc').value;  
		 var fileSize = document.getElementById("machineryMiscDoc").files[0];
		 var maxSize = '5000';

		 if(costMachineryMiscDoc !=null && costMachineryMiscDoc !=''){
		 var ext = costMachineryMiscDoc.split('.').pop();
		 if (ext == "pdf") {
			// value is ok, use it
		} else {
			document.getElementById('machineryMiscDocMsg').innerHTML = "Please Upload Support documnet in PDF Format";
			document.getElementById('machineryMiscDoc').focus();
			return false;
		}
				
		var FileSize = (fileSize.size/1024)/1024; // in MB
		FileSize = FileSize.toFixed(5);
	   if (FileSize >5) {
	   	document.getElementById('machineryMiscDocMsg').innerHTML = "Your file size is: "+FileSize+" MB," +" File size should not be more than 5 MB";
	   	document.getElementById('machineryMiscDoc').focus();
			return false;
	   }
	   
		}  else{
			 if('${existProjDisbursement.machineryMiscDocbase64File}' != '' && costMachineryMiscDoc == ''){
					return true;
				}
				
			document.getElementById('machineryMiscDocMsg').innerHTML = "Cost of Proposed/Actual document is Required.";
			document.getElementById('machineryMiscDoc').focus();
			return false;
		  } 
			  
		    document.getElementById('machineryMiscDocMsg').innerHTML = "";
		    return true; 
		 
	}

</script>


</head>

<%@ include file="../topMenu.jsp"%>


<section class="common-form-area">
	<div class="container" style="max-width: 90%;">
		<!-- Main Title -->
		<div class="inner-banner-text">
			<h1>APPLICATION FORM</h1>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="form-card">
					<div class="without-wizard-steps">
						<div class="step-links">
							<ul>
								<li><a href="./disApplicantDetails" class="filled"><span>1</span>
										Applicant Details</a></li>
								<li><a href="#" class="active"><span>2</span> Capital
										Investment</a></li>
								<li><a href="./disEmploymentDetails" id="empappActive"><span>3</span>Employment
										Details</a></li>
							</ul>
						</div>
						<div class="isf-form">
							<form:form modelAttribute="existProjDisbursement" id="disForm"
								name="projDisburseForm" autocomplete="off" method="POST"
								enctype="multipart/form-data" action="saveIncentiveDisburse"
								class="mt-4">

								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Details of Eligible Capital
											Investment in Industrial Undertaking</h3>
									</div>
								</div>
								<div class="row newproject">
									<div class="col-sm-12 ">
										<p>
											<strong>New Project <small>(All fields in
													INR)</small></strong>
										</p>
										<div class="table-responsive">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th>Sl No</th>
														<th>Item</th>
														<th>Amount of Capital Investment in New Project</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>1</td>
														<td>Land</td>
														<td><form:input path="newprojLandCost"
																value="${landcost}" type="text"
																class="form-control disbNewproj" disabled="true"></form:input></td>
													</tr>
													<tr>
														<td>2</td>
														<td>Building Site Development & Civil Works Cost</td>
														<td><form:input path="newprojBldgCost" type="text"
																value="${buildingcost}" class="form-control disbNewproj"
																disabled="true"></form:input></td>
													</tr>
													<%-- <tr>
														<td>3</td>
														<td>Other Construction</td>
														<td><form:input path="projDisConstruct" type="text"
																onkeypress="return validateNumericField()"
																maxlength="12" class="form-control disbNewproj"></form:input></td>
													</tr> --%>
													<tr>
														<td>4</td>
														<td>Plant and Machinery</td>
														<td><form:input path="newprojPlantMachCost"
																value="${plantmachcost}" type="text"
																class="form-control disbNewproj" disabled="true"></form:input></td>
													</tr>
													
													<tr>
														<td>4</td>
														<td>Miscellaneous Fixed Asset</td>
														<td><form:input path="newprojMiscFixedAsset"
																value="${mfaCost}" type="text"
																class="form-control disbNewproj" disabled="true"></form:input></td>
													</tr>
													
													<tr>
														<td>5</td>
														<td>Infrastructure Facilities</td>
														<td><form:input type="text" path="newprojInfra"
																class="form-control disbNewproj" id="newprojInfraId"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input><span
												id="newprojInfraIdMsg" class="text-danger"></span></td>
													</tr>
													<tr>
														<td></td>
														<td>Total</td>
														<td><form:input type="text" path="total"
																readonly="ture" class="form-control disbNewprojTotal"></form:input></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>


								<div class="row existingproject">
									<div class="col-sm-12 mt-4">
										<p>
											<strong>Expansion/Diversification <small>(All
													fields in INR)</small></strong>
										</p>
										<div class="table-responsive">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th>Sl No</th>
														<th>Item</th>
														<th>Amount of Capital Investment in Existing Project</th>
														<th>Amount of Investment for
															Expansion/Diversification</th>
														<th>% of increase under Expansion/Diversification</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>1</td>
														<td>Land</td>
														<td><form:input path="projDisLandCost"
																value="${landcost}" type="text"
																class="form-control disbExistproj is-numeric"
																maxlength="12" disabled="true"></form:input></td>
														<td><form:input path="projDisExpandOrDiverse"
																onblur="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()" value=" "
																type="text" maxlength="12" readonly="false"
																class="form-control is-numeric amtInvest" name=""
																id="projDis"></form:input> <small
															class="words text-info"></small> <span id="projDisPP"
															class="text-danger"></span></td>
														<td><form:input path="projLandIncrement" type="text"
																maxlength="12" id="landIncrement"
																onkeypress="return validateNumericField()" disabled=""
																class="form-control is-numeric ttlIncrement" name=""
																onblur="emptyCapInvErrMsg()"></form:input> <small
															class="words text-info"></small> <span
															id="landIncrementLL" class="text-danger"></span></td>
													</tr>
													<tr>
														<td>2</td>
														<td>Building Site Development & Civil Works Cost</td>
														<td><form:input path="projDisBldgCost"
																value="${buildingcost}" type="text" maxlength="12"
																class="form-control disbExistproj" disabled="true"></form:input></td>
														<td><form:input path="projDisBldgCost" maxlength="12"
																value=" " type="text" disabled="false"
																class="form-control is-numeric amtInvest" name=""
																id="buildCast" onblur="emptyCapInvErrMsg()"></form:input>
															<small class="words text-info"></small> <span
															id="buildCastBB" class="text-danger"></span></td>
														<td><form:input path="projBuildIncrement" type="text"
																maxlength="12" id="buildIncrement"
																onkeypress="return validateNumericField()"
																class="form-control is-numeric ttlIncrement" name=""
																onblur="emptyCapInvErrMsg()"></form:input> <small
															class="words text-info"></small> <span
															id="buildIncrementBB" class="text-danger"></span></td>
													</tr>

													<%-- <tr>
																										
													<td>3</td>
														<td>Other Construction</td>
														<td><form:input path="projDisConstruct" type="text"
																maxlength="12"
																onkeypress="return validateNumericField()"
																class="form-control disbExistproj"></form:input></td>
														<td><form:input path="projDisExpandOrDiverse"
																value="${expansion}  ${diversification}" type="text"
																class="form-control" disabled="true"></form:input></td>
														<td><form:input path="projConstructIncrement" type="text"
																onkeypress="return validateNumericField()"
																class="form-control" name=""></form:input></td>
													</tr> --%>
													<tr>
														<td>3</td>
														<td>Plant and Machinery</td>
														<td><form:input path="projDisPlantMachCost"
																value="${plantmachcost}" type="text" maxlength="12"
																class="form-control disbExistproj" disabled="true"></form:input></td>
														<td><form:input path="projDisPlantMachCost" value=" "
																type="text" maxlength="12" disabled="false"
																class="form-control is-numeric amtInvest" name=""
																id="plantMachCost" onblur="emptyCapInvErrMsg()"></form:input>
															<small class="words text-info"></small> <span
															id="plantMachCostPP" class="text-danger"></span></td>
														<td><form:input path="projPlantIncrement" type="text"
																maxlength="12"
																onkeypress="return validateNumericField()"
																class="form-control is-numeric ttlIncrement" name=""
																id="plantIncrement" onblur="emptyCapInvErrMsg()"></form:input>
															<small class="words text-info"></small> <span
															id="plantIncrementPP" class="text-danger"></span></td>
													</tr>
													
													<tr>
														<td>4</td>
														<td>Miscellaneous Fixed Asset</td>
														<td><form:input path="exProjMiscFixedAsset"
																value="${mfaCost}" type="text"
																class="form-control disbExistproj" disabled="true"></form:input></td>
														<td><form:input path="exMiscExpDiv" value=" "
																type="text" maxlength="12" disabled="false"
																class="form-control is-numeric amtInvest" name=""
																id="miscFixedAsset" onblur="emptyCapInvErrMsg()"></form:input>
															<small class="words text-info"></small> <span
															id="miscFixedAssetMsg" class="text-danger"></span></td>
														<td><form:input path="exMiscIncrement" type="text"
																maxlength="12"
																onkeypress="return validateNumericField()"
																class="form-control is-numeric ttlIncrement" name=""
																id="miscFixedIncrement" onblur="emptyCapInvErrMsg()"></form:input>
															<small class="words text-info"></small> <span
															id="miscFixedIncrementMsg" class="text-danger"></span></td>
													</tr>
													
													<tr>
														<td>5</td>
														<td>Infrastructure Facilities</td>
														<td><form:input type="text" path="projDisInfra"
																class="form-control is-numeric disbExistproj"
																id="infraFaciExist"
																onkeypress="return validateNumericField()"
																maxlength="12" onblur="emptyCapInvErrMsg()"></form:input><small
															class="words text-info"></small> <span
															id="infraFaciExistII" class="text-danger"></span></td>
														<td><form:input path="projDisInfra" maxlength="12"
																type="text" value=" " disabled="false"
																class="form-control is-numeric amtInvest" name=""
																id="infraFaci" onblur="emptyCapInvErrMsg()"></form:input>
															<small class="words text-info"></small> <span
															id="infraFaciII" class="text-danger"></span></td>
														<td><form:input path="projInfraIncrement" type="text"
																maxlength="12"
																onkeypress="return validateNumericField()"
																class="form-control is-numeric ttlIncrement" name=""
																id="infraIncrement" onblur="emptyCapInvErrMsg()"></form:input>
															<small class="words text-info"></small> <span
															id="infraIncrementII" class="text-danger"></span></td>
													</tr>
													<tr>
														<td>6</td>
														<td>Total</td>
														<td style="width: 20%;"><form:input
																path="ttlAmtExitProj" type="text"
																onkeypress="return validateNumericField()"
																class="form-control disbExistprojTotal" readonly="true"
																name=""></form:input></td>


														<td style="width: 20%;"><form:input
																path="totalAmountOfInvest" type="text"
																onkeypress="return validateNumericField()"
																class="form-control totalAmtInvest" readonly="true"
																name=""></form:input></td>


														<td style="width: 20%;"><form:input
																path="totalIncrement" type="text"
																onkeypress="return validateNumericField()"
																class="form-control disTotalIncrement" readonly="true"
																name=""></form:input></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>



								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<form:label path="statutoryAuditorDoc"> Statutory Auditor's Certificate for Capital
												Investment <span>*</span>
												<small>(Upload doc less than 5 MB in PDF)</small>
											</form:label>
											<div class="custom-file">
												<input type="file" class="custom-file-input user-file"
													maxlength="10" id="statutoryAuditorDoc"
													name="statutoryAuditorDoc"
													onchange="return StaAuditorDoc(event)"> <span
													id="statutoryAuditorDoc" class="text-danger"></span>
												<form:label path="statutoryAuditorDoc"
													class="custom-file-label file"
													id="choosefilestatutoryAuditorDoc"
													for="statutoryAuditorDoc">Choose file</form:label>
											</div>
											<span id="statutoryAuditorDocMsg" class="text-danger"></span>
										</div>
									</div>
								</div>


								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<label>Total Loan Taken <a href="javascript:void(0);"
												class="remove-row" data-toggle="tooltip" title=""
												data-original-title="Total Loan on Project"><i
													class="fa fa-info-circle text-info"></i></a></label>
											<form:input path="totalLoan" type="text" id="totalLoan"
												onblur="emptyCapInvErrMsg()"
												onkeypress="return validateNumericField()" maxlength="12"
												class="form-control is-numeric" name=""></form:input>
											<small class="words text-info"></small> <span
												id="totalLoanXX" class="text-danger"></span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<label>Total Interest on Loan<a
												href="javascript:void(0);" class="remove-row"
												data-toggle="tooltip" title=""
												data-original-title="Total Interest on Loan"><i
													class="fa fa-info-circle text-info"></i></a></label>
											<form:input path="totalInterest" type="text"
												id="totalInterest" onblur="emptyCapInvErrMsg()"
												onkeypress="return validateNumericField()" maxlength="12"
												class="form-control is-numeric " name=""></form:input>
											<small class="words text-info"></small> <span
												id="totalInterrestXX" class="text-danger"></span>
										</div>
									</div>
								</div>


								<%-- <div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<label>Annual Turnover of the Project <a
												href="javascript:void(0);" class="remove-row"
												data-toggle="tooltip" title=""
												data-original-title="The maximum turnover in any year, in the preceding
                          5 years (or less in case the unit has been in production for less than 5 years), (i.e. 5 years
                          preceding the financial year in which the date of commercial production falls in case of a
                          single phase project, or 5 years preceding the date of commercial production of first phase
                          in a multi-phase project)."><i
													class="fa fa-info-circle text-info"></i></a></label>
											<form:input path="annualTurnOver" type="text"
												id="annTurnOver" onblur="emptyCapInvErrMsg()"
												onkeypress="return validateNumericField()" maxlength="12"
												class="form-control is-numeric" name=""></form:input>
											<small class="words text-info"></small> <span
												id="annTurnOverXX" class="text-danger"></span>
										</div>
									</div>
								</div> --%>



								<div class="in-case-of-small-row">
									<div class="row">
										<div class="col-sm-12 mt-4">

											<h3 class="common-heading">Phasewise Details</h3>
										</div>

									</div>


									<div class="row">

										<div class="col-sm-12">
											<div class="table-responsive mt-3">
												<table class="table table-stripped table-bordered">
													<thead>
														<tr>
															<th>Phase</th>
															<th>Product</th>
															<th>Capacity</th>
															<th>Unit</th>
															<th>Capital Investment</th>
															<th>Date of Commercial Production</th>
															<!-- 	<th colspan="2">Enclose certificate <a
															href="javascript:void(0);" class="remove-row"
															data-toggle="tooltip" title=""
															data-original-title="Enclose certificate from concerned Dy. Commissioner, Industries, and District Industries & Enterprise Promotion Centre or Chartered Accountant or IEM Part II filed with DIPP"><i
																class="fa fa-info-circle text-info"></i></a> <small>(Upload
																Less than 2 MB in PDF)</small>
														</th> -->
														</tr>
													</thead>

													<c:if test="${not empty pwInvDetailsList}">
														<tbody>
															<c:forEach var="pwInvObj" items="${pwInvDetailsList}"
																varStatus="counter">
																<tr>
																	<td>${pwInvObj.pwPhaseNo}</td>
																	<td>${pwInvObj.pwProductName}</td>
																	<td>${pwInvObj.pwCapacity}</td>
																	<td>${pwInvObj.pwUnit}</td>
																	<td>${pwInvObj.pwFci}</td>
																	<td>${pwInvObj.pwPropProductDate}</td>
																	<!-- 	<td>
																	<div class="custom-file">
																		<input type="file" class="custom-file-input user-file"
																			maxlength="10" id="encCertificateFName" name="encCertificateFName">
																	<label	class="custom-file-label file" for="encCertificateFName" id="encCertificateFNameLabel">Choose
																			file</label>
																	</div>
																</td>
																<td class="text-center"><small><a id="pdfLink" class="pdfLink" download>View</a></small>
																</td> -->
																</tr>
															</c:forEach>
														</tbody>

													</c:if>
												</table>
											</div>
											<div class="form-group">
												<label> Enclose certificate <span>*</span> <small>(Upload
														doc less than 5 MB in PDF)</small>
												</label>
												<div class="custom-file">
													<input type="file" class="custom-file-input user-file"
														maxlength="10" id="encCertificateFName"
														name="encCertificateFName"
														onchange="return EncloseCertificateDoc(event)"> <span
														class="text-danger"></span> <label
														class="custom-file-label file" for="encCertificateFName"
														id="chosseFileEncCertificateFName">Choose file</label>
												</div>
												<span id="encCertificateFNameMsg" class="text-danger"></span>
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Details of Capital Investment
											in Industrial Undertaking</h3>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12">
										<div class="table-responsive mt-3">
											<table class="table table-stripped table-bordered"
												id="CapInvTable">
												<thead>
													<tr>
														<th rowspan="3">Item</th>
														<th rowspan="3">Cost of Project As per DPR</th>
														<th rowspan="3">Cost of Project As per Appraisal From
															Bank/FI's</th>
														<th colspan="5" class="text-center">Actual Investment
															<small>(As per certificate from Statutory
																Auditors)</small>
														</th>
													</tr>
													<tr>

														<th rowspan="2">Before cutoff Date</th>
														<th rowspan="2">Investment of cutoff date <a
															href="javascript:void(0);" class="remove-row"
															data-toggle="tooltip" title=""
															data-original-title="Investment from cutoff date to the date of commencement of commercial production (In case of phase, give investment phasewise)"><i
																class="fa fa-info-circle text-info"></i></a>
														</th>
														<th colspan="2">Date of commencement <a
															href="javascript:void(0);" class="remove-row"
															data-toggle="tooltip" title=""
															data-original-title="Date of commencement of final phase of commercial production to till date"><i
																class="fa fa-info-circle text-info"></i></a></th>
														<th>Total</th>
													</tr>
													<tr>
														<th>Actual Investment (100%)</th>
														<th>Additional 10% <a href="javascript:void(0);"
															class="remove-row" data-toggle="tooltip" title=""
															data-original-title="Additional 10% of actual investment beyond the date of actual commercial production (as per 2.9 Policy rule 2017)"><i
																class="fa fa-info-circle text-info"></i></a></th>
														<th>&nbsp;</th>
													</tr>
												</thead>
												<tbody class="add-from-here">
													<tr>
														<td>Land Cost</td>
														<td style="width: 12%;"><form:input type="text"
																path="capInvDPRLC" value="${landcost}"
																class="form-control costdpr" disabled="true"></form:input></td>

														<td><form:input path="capInvAppraisalLC" type="text"
																id="costapplform" onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control is-numeric costBankFI" name=""></form:input>
															<small class="words text-info"></small> <span
															id="costapplformXX" class="text-danger"></span></td>

														<td><form:input path="capInvCutoffDateLC" type="text"
																id="befcutoff"
																onkeypress="return validateNumericField()"
																maxlength="12" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric beforeCutOff" name=""></form:input>
															<small class="words text-info"></small> <span
															id="befcutoffXX" class="text-danger"></span></td>

														<td><form:input path="capInvCommProdLC" type="text"
																id="cutoffdatecomprod" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric cutOffComPro"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span
															id="cutoffdatecomprodXX" class="text-danger"></span></td>
														<td style="width: 11%;"><form:input
																path="capInvActualInvLC" type="text" value="${InvFci}"
																class="form-control capInvLC actualInvestment"
																disabled="true"></form:input></td>
														<td><form:input path="capInvAddlInvLC" type="text"
																id="addlinv"
																class="form-control capInvLC is-numeric addActualInvestment"
																onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span id="addlinvXX"
															class="text-danger"></span></td>
														<td style="width: 12%;"><form:input
																path="capInvTotalLC" type="text"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control totalLC disTotalOfTotal"
																readonly="true" name=""></form:input></td>
													</tr>
													<tr>
														<td>Building Site Development & Civil Works Cost</td>
														<td><form:input type="text" path="capInvDPRBC"
																value="${buildingcost}" class="form-control costdpr"
																disabled="true"></form:input></td>
														<td style="width: 13%;"><form:input
																path="capInvAppraisalBC" type="text" id="costapplformBC"
																onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control costBankFI is-numeric" name=""></form:input>

															<small class="words text-info"></small> <span
															id="costapplformYY" class="text-danger"></span></td>
														<td><form:input path="capInvCutoffDateBC" type="text"
																id="befcutoffBC"
																onkeypress="return validateNumericField()"
																maxlength="12" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric beforeCutOff" name=""></form:input>
															<small class="words text-info"></small> <span
															id="befcutoffYY" class="text-danger"></span></td>
														<td><form:input path="capInvCommProdBC" type="text"
																id="cutoffdatecomprodBC" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric cutOffComPro"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span
															id="cutoffdatecomprodYY" class="text-danger"></span></td>
														<td style="width: 17%;"><form:input
																path="capInvActualInvBC" id="actInvBC" type="text"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control capInvBC is-numeric actualInvestment"
																disabled="false"></form:input> <small
															class="words text-info"></small> <span id="actInvYY"
															class="text-danger"></span></td>
														<td><form:input path="capInvAddlInvBC" type="text"
																id="addlinvBC"
																class="form-control capInvBC is-numeric addActualInvestment"
																onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span id="addlinvYY"
															class="text-danger"></span></td>
														<td><form:input path="capInvTotalBC" type="text"
																onkeypress="return validateNumericField()"
																class="form-control totalBC disTotalOfTotal"
																readonly="true" name=""></form:input></td>
													</tr>
													<tr>
														<td>Plant & Machinery Cost</td>
														<td><form:input type="text" path="capInvDPRPMC"
																value="${plantmachcost}" class="form-control costdpr"
																disabled="true"></form:input></td>
														<td><form:input path="capInvAppraisalPMC" type="text"
																id="costapplformPC" onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control costBankFI is-numeric" name=""></form:input>

															<small class="words text-info"></small> <span Detailed
															cost estimates id="costapplformPP" class="text-danger"></span></td>
														<td><form:input path="capInvCutoffDatePMC"
																type="text" id="befcutoffPMC"
																onkeypress="return validateNumericField()"
																maxlength="12" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric beforeCutOff" name=""></form:input>
															<small class="words text-info"></small><span
															id="befcutoffPP" class="text-danger"></span></td>
														<td><form:input path="capInvCommProdPMC" type="text"
																id="cutoffdatecomprodPMC" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric cutOffComPro"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span
															id="cutoffdatecomprodPP" class="text-danger"></span></td>
														<td><form:input path="capInvActualInvPMC"
																id="actInvPMC" type="text"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control capInvPC is-numeric actualInvestment"
																disabled="false"></form:input> <small
															class="words text-info"></small> <span
															id="capInvActualInvPP" class="text-danger"></span></td>
														<td><form:input path="capInvAddlInvPMC" type="text"
																id="addlinvPMC"
																class="form-control is-numeric capInvPC addActualInvestment"
																onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small><span id="addlinvPP"
															class="text-danger"></span></td>
														<td><form:input path="capInvTotalPMC" type="text"
																onkeypress="return validateNumericField()"
																class="form-control totalPC disTotalOfTotal"
																readonly="true" name=""></form:input></td>
													</tr>

													<tr>
														<td>Miscellaneous Fixed Asset (MFA)</td>
														<td><form:input type="text" path="capInvDPRMFA"
																value="${mfaCost}" class="form-control costdpr"
																readonly="true"></form:input></td>
														<td><form:input path="capInvAppraisalMFA" type="text"
																id="costapplformMFA" onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control costBankFI is-numeric" name=""></form:input>

															<small class="words text-info"></small> <span
															id="costapplformMM" class="text-danger"></span></td>
														<td><form:input path="capInvCutoffDateMFA"
																type="text" id="befcutoffMFA"
																onkeypress="return validateNumericField()"
																maxlength="12" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric beforeCutOff" name=""></form:input>
															<small class="words text-info"></small> <span
															id="befcutoffMM" class="text-danger"></span></td>
														<td><form:input path="capInvCommProdMFA" type="text"
																id="cutoffdatecomprodMFA" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric cutOffComPro"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span
															id="cutoffdatecomprodMM" class="text-danger"></span></td>
														<td><form:input path="capInvActualInvMFA"
																id="actInvMFA" type="text"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control capInvMF is-numeric actualInvestment"
																disabled="false"></form:input> <small
															class="words text-info"></small> <span id="actInvMM"
															class="text-danger"></span></td>
														<td><form:input path="capInvAddlInvMFA" type="text"
																id="addlinvMFA"
																class="form-control capInvMF is-numeric addActualInvestment"
																onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span id="addlinvMM"
															class="text-danger"></span></td>
														<td><form:input path="capInvTotalMFA" type="text"
																onkeypress="return validateNumericField()"
																class="form-control totalMF disTotalOfTotal"
																readonly="true" name=""></form:input></td>
													</tr>



													<tr>
														<td>Technical Knowhow Fee</td>
														<td><form:input type="text" id="costOfDprTKF"
																onkeypress="return validateNumericField()"
																maxlength="12" path="capInvDPRTKF"
																onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric costdpr"></form:input> <small
															class="words text-info"></small> <span id="costOfDprTT"
															class="text-danger"></span></td>
														<td><form:input path="capInvAppraisalTKF" type="text"
																id="costapplformTKF" onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control costBankFI is-numeric" name=""></form:input>

															<small class="words text-info"></small> <span
															id="costapplformTT" class="text-danger"></span></td>
														<td><form:input path="capInvCutoffDateTKF"
																type="text" id="befcutoffTKF"
																onkeypress="return validateNumericField()"
																maxlength="12" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric beforeCutOff" name=""></form:input>
															<small class="words text-info"></small> <span
															id="befcutoffTT" class="text-danger"></span></td>
														<td><form:input path="capInvCommProdTKF" type="text"
																id="cutoffdatecomprodTKF" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric cutOffComPro"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span
															id="cutoffdatecomprodTT" class="text-danger"></span></td>
														<td><form:input path="capInvActualInvTKF"
																id="actInvTKF" type="text"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control capInvTK is-numeric actualInvestment"
																disabled="false"></form:input> <small
															class="words text-info"></small> <span id="actInvTT"
															class="text-danger"></span></td>
														<td><form:input path="capInvAddlInvTKF" type="text"
																id="addlinvTKF"
																class="form-control capInvTK is-numeric addActualInvestment"
																onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span id="addlinvTT"
															class="text-danger"></span></td>
														<td><form:input path="capInvTotalTKF" type="text"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control totalTK disTotalOfTotal"
																readonly="true" name=""></form:input></td>
													</tr>


													<tr>
														<td>Interest During Construction Period</td>
														<td><form:input type="text" id="costOfDprICP"
																onkeypress="return validateNumericField()"
																onkeyup="emptyCapInvErrMsg()" maxlength="12"
																path="capInvDPRICP"
																class="form-control is-numeric costdpr"></form:input> <small
															class="words text-info"></small> <span id="costOfDprII"
															class="text-danger"></span></td>
														<td><form:input path="capInvAppraisalICP" type="text"
																id="costapplformICP" onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control costBankFI is-numeric" name=""></form:input>

															<small class="words text-info"></small> <span
															id="costapplformII" class="text-danger"></span></td>
														<td><form:input path="capInvCutoffDateICP"
																type="text" id="befcutoffICP"
																onkeypress="return validateNumericField()"
																maxlength="12" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric beforeCutOff" name=""></form:input>
															<small class="words text-info"></small> <span
															id="befcutoffII" class="text-danger"></span></td>
														<td><form:input path="capInvCommProdICP" type="text"
																id="cutoffdatecomprodICP" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric cutOffComPro"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small><span
															id="cutoffdatecomprodII" class="text-danger"></span></td>
														<td><form:input path="capInvActualInvICP"
																id="actInvICP" type="text"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control capInvIC is-numeric actualInvestment"
																disabled="false"></form:input> <small
															class="words text-info"></small> <span id="actInvII"
															class="text-danger"></span></td>
														<td><form:input path="capInvAddlInvICP" type="text"
																id="addlinvICP"
																class="form-control capInvIC is-numeric addActualInvestment"
																onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span id="addlinvII"
															class="text-danger"></span></td>
														<td><form:input path="capInvTotalICP" type="text"
																onkeypress="return validateNumericField()"
																class="form-control totalIC disTotalOfTotal"
																readonly="true" name=""></form:input></td>
													</tr>
													<tr>
														<td>Prel. And Preoperative Expenses</td>
														<td><form:input type="text" id="costOfDprPPE"
																onkeypress="return validateNumericField()"
																onkeyup="emptyCapInvErrMsg()" maxlength="12"
																path="capInvDPRPPE"
																class="form-control is-numeric costdpr"></form:input> <small
															class="words text-info"></small> <span id="costOfDprPE"
															class="text-danger"></span></td>
														<td><form:input path="capInvAppraisalPPE" type="text"
																id="costapplformPPE" onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control costBankFI is-numeric" name=""></form:input>

															<small class="words text-info"></small> <span
															id="costapplformPE" class="text-danger"></span></td>
														<td><form:input path="capInvCutoffDatePPE"
																type="text" id="befcutoffPPE"
																onkeypress="return validateNumericField()"
																maxlength="12" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric beforeCutOff" name=""></form:input>
															<small class="words text-info"></small> <span
															id="befcutoffPE" class="text-danger"></span></td>
														<td><form:input path="capInvCommProdPPE" type="text"
																id="cutoffdatecomprodPPE" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric cutOffComPro"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span
															id="cutoffdatecomprodPE" class="text-danger"></span></td>
														<td><form:input path="capInvActualInvPPE"
																id="actInvPPE" type="text"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control capInvPE is-numeric actualInvestment"
																disabled="false"></form:input> <small
															class="words text-info"></small> <span id="actInvPE"
															class="text-danger"></span></td>
														<td><form:input path="capInvAddlInvPPE" type="text"
																id="addlinvPPE"
																class="form-control capInvPE is-numeric addActualInvestment"
																onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span id="addlinvPE"
															class="text-danger"></span></td>
														<td><form:input path="capInvTotalPPE" type="text"
																onkeypress="return validateNumericField()"
																class="form-control totalPE disTotalOfTotal"
																readonly="true" name=""></form:input></td>
													</tr>
													<tr>
														<td>Margin Money for Working Capital</td>
														<td><form:input type="text" id="costOfDprMMWC"
																onkeypress="return validateNumericField()"
																onkeyup="emptyCapInvErrMsg()" maxlength="12"
																path="capInvDPRMMC"
																class="form-control is-numeric costdpr"></form:input> <small
															class="words text-info"></small> <span id="costOfDprWC"
															class="text-danger"></span></td>
														<td><form:input path="capInvAppraisalMMC" type="text"
																id="costapplformMMWC" onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control costBankFI is-numeric" name=""></form:input>

															<small class="words text-info"></small> <span
															id="costapplformWC" class="text-danger"></span></td>
														<td><form:input path="capInvCutoffDateMMC"
																type="text" id="befcutoffMMWC"
																onkeypress="return validateNumericField()"
																maxlength="12" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric beforeCutOff" name=""></form:input>
															<small class="words text-info"></small> <span
															id="befcutoffWC" class="text-danger"></span></td>
														<td><form:input path="capInvCommProdMMC" type="text"
																id="cutoffdatecomprodMMWC" onkeyup="emptyCapInvErrMsg()"
																class="form-control is-numeric cutOffComPro"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span
															id="cutoffdatecomprodWC" class="text-danger"></span></td>
														<td><form:input path="capInvActualInvMMC"
																id="actInvMMWC" type="text"
																onkeypress="return validateNumericField()"
																maxlength="12"
																class="form-control capInvMC is-numeric actualInvestment"
																disabled="false"></form:input> <small
															class="words text-info"></small> <span id="actInvWC"
															class="text-danger"></span></td>
														<td><form:input path="capInvAddlInvMMC" type="text"
																id="addlinvMMWC"
																class="form-control capInvMC is-numeric addActualInvestment"
																onkeyup="emptyCapInvErrMsg()"
																onkeypress="return validateNumericField()"
																maxlength="12"></form:input> <small
															class="words text-info"></small> <span id="addlinvWC"
															class="text-danger"></span></td>
														<td><form:input path="capInvTotalMMC" type="text"
																onkeypress="return validateNumericField()"
																class="form-control totalMC disTotalOfTotal"
																readonly="true" name=""></form:input></td>
													</tr>
													<tr>
														<td><strong>Total:</strong></td>

														<td><form:input path="ttlCostDpr" type="text"
																onkeypress="return validateNumericField()"
																class="form-control totaldpr" readonly="true" name=""></form:input></td>


														<td><form:input path="ttlcostProAppFrBank"
																type="text" onkeypress="return validateNumericField()"
																class="form-control totalBankFI" readonly="true" name=""></form:input></td>


														<td><form:input path="ttlBeforeCutOff" type="text"
																onkeypress="return validateNumericField()"
																class="form-control totalCutOff" readonly="true" name=""></form:input></td>

														<td><form:input path="ttlCutOffCommProduction"
																type="text" onkeypress="return validateNumericField()"
																class="form-control totalCutOffComPro" readonly="true"
																name=""></form:input></td>


														<td><form:input path="ttlActualInvestment"
																type="text" onkeypress="return validateNumericField()"
																class="form-control totalActualInvestment"
																readonly="true" name=""></form:input></td>

														<td><form:input path="ttlAddiActualInvestment"
																type="text" onkeypress="return validateNumericField()"
																class="form-control totalAddActualInvestment"
																readonly="true" name=""></form:input></td>

														<td><form:input path="disValueTotalOfTotal"
																type="text" onkeypress="return validateNumericField()"
																class="form-control totalOfTotal" readonly="true"
																name=""></form:input></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12 mt-4">
										<h3 class="common-heading">Supporting Documents</h3>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<form:label path="purchasePriceDoc">Registered document showing purchase price <span>*</span>
												<small>(Upload doc less than 5 MB in PDF)</small>
											</form:label>
											<div class="custom-file">
												<input type="file"
													onchange="return RegisteredPriceDoc(event)"
													class="custom-file-input user-file" maxlength="10"
													id="purchasePriceDoc" name="purchasePriceDoc"> <span
													class="text-danger"></span> <label
													class="custom-file-label file"
													id="choosefilepurchasePriceDoc" for="purchasePriceDoc">Choose
													file</label>
											</div>
											<span id="purchasePriceDocMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Receipt of payment of stamp duty <span>*</span>
												<small>(Upload doc less than 5 MB in PDF)</small></label>
											<div class="custom-file">
												<input type="file"
													onchange="return ReceiptStampDutyDoc(event)"
													class="custom-file-input user-file" maxlength="10"
													id="stampDutyDoc" name="stampDutyDoc"> <span
													class="text-danger"></span> <label
													class="custom-file-label file" id="choosefilestampDutyDoc"
													for="stampDutyDoc">Choose file</label>
											</div>
											<span id="stampDutyDocMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Receipt of payment of registration fee <span>*</span>
												<small>(Upload doc less than 5 MB in PDF)</small></label>
											<div class="custom-file">
												<input type="file"
													onchange="return ReceiptRegisteredFeeDoc(event)"
													class="custom-file-input user-file" maxlength="10"
													id="registrationFeeDoc" name="registrationFeeDoc">
												<span class="text-danger"></span> <label
													class="custom-file-label file"
													id="choosefileregistrationFeeDoc" for="registrationFeeDoc">Choose
													file</label>
											</div>
											<span id="registrationFeeDocMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>If land purchased from UPSIDC/DI/FIs/Banks in
												auction, supporting documents for price paid. <span>*</span>
												<small>(Upload doc less than 5 MB in PDF)</small>
											</label>

											<div class="form-radio-group">
												<div
													class="custom-control custom-radio custom-control-inline">
													<input type="radio" class="custom-control-input" checked=""
														id="YeslandPurchased" name="landPurchaseFrUPSIDC" value="Yes">
													<label class="custom-control-label" for="YeslandPurchased"
														id="YeslandPurchasedLabel">Yes</label>
												</div>
												<div
													class="custom-control custom-radio custom-control-inline">
													<input type="radio" class="custom-control-input" 
														id="NolandPurchased" name="landPurchaseFrUPSIDC" value="No" onchange="BankDocsData()">
													<label class="custom-control-label"
														id="NolandPurchasedLabel" for="NolandPurchased">No</label>
												</div>
											</div>

											<div class="custom-file hideBankFile">
												<input type="file"
													onchange="return LandPurchasedBankAuctionDoc(event)"
													class="custom-file-input user-file" maxlength="10"
													id="banksAuctionDoc" name="banksAuctionDoc"> <span
													class="text-danger"></span> <label
													class="custom-file-label file"
													id="choosefilebanksAuctionDoc" for="banksAuctionDoc">Choose
													file</label>
											</div>
											<span id="banksAuctionDocMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>Detailed cost estimates of building and civil
												works <span>*</span> <a href="javascript:void(0);"
												class="remove-row" data-toggle="tooltip" title=""
												data-original-title="Detailed cost estimates of building and civil works constructed or to be constructed (as per DPR/Appraisal Note) and supported with layout plans and cost estimates prepared by external consultants/CA firms and cost incurred duly certified by statutory auditors."><i
													class="fa fa-info-circle text-info"></i></a> <small>(Upload
													doc less than 5 MB in PDF)</small>
											</label>
											<div class="custom-file">
												<input type="file"
													onchange="return DetailedCostEstimatesDoc(event)"
													class="custom-file-input user-file" maxlength="10"
													id="civilWorksDoc" name="civilWorksDoc"> <span
													class="text-danger"></span> <label
													class="custom-file-label file" id="choosefilecivilWorksDoc"
													for="civilWorksDoc">Choose file</label>
											</div>
											<span id="civilWorksDocMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label>The cost of proposed/actual capital investment
												in the head of plant and machinery and misc. fixed assets <span>*</span>
												<a href="javascript:void(0);" class="remove-row"
												data-toggle="tooltip" title=""
												data-original-title="The cost of proposed/actual capital investment in the head of plant and machinery and misc. fixed assets should be shown itemized in accordance with the provisions of the Rules for scrutiny, verification and certification."><i
													class="fa fa-info-circle text-info"></i></a> <small>(Upload
													doc less than 5 MB in PDF)</small>
											</label>
											<div class="custom-file">
												<input type="file"
													onchange="return CostOfProposedDoc(event)"
													class="custom-file-input user-file" maxlength="10"
													id="machineryMiscDoc" name="machineryMiscDoc"> <span
													class="text-danger"></span> <label
													class="custom-file-label file"
													id="choosefilemachineryMiscDoc" for="machineryMiscDoc">Choose
													file</label>
											</div>
											<span id="machineryMiscDocMsg" class="text-danger"></span>
										</div>
									</div>
								</div>

								<hr>

								<div class="row">
									<div class="col-sm-5">
										<a href="./disApplicantDetails"
											onclick="return confirm('Are you sure you want to go to Applicant Details?')"
											class="common-default-btn mt-3 prev-step">Back</a>
									</div>
									<div class="col-sm-7 text-right">

										<form:button
											onclick="return validateCapInvIndUndertaking();submitForm();"
											class="common-btn mt-3">Save And Next</form:button>
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


<%@ include file="../footerMenu.jsp"%>


</body>
</html>