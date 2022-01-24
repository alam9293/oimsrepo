<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="com.webapp.ims.model.EvaluateInvestmentDetails" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Evaluate Application</title>
<link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://rawgit.com/jackmoore/autosize/master/dist/autosize.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="js/number.js"></script>
<script src="js/custom.js"></script>
<script>
    $(document).ready(function() {
        autosize(document.getElementsByClassName("form-control"));
    });

    $(document).ready(function() {
        $("#itemName").val('');
        $("#DPRInput").val('');
        $("#IIEPPInput").val('');
        $("#PICUPRemarks").val('');

        if ('${addMofClick}' == "addMof") {
            $("#emfinvestamt").val('');
            $("#emfitemname").val('');
            $("#emfphaseno").val('');
        }
    });
</script>
<script type="text/javascript">
    $(function() {
        var ctypeSMALLselect = '${ctypeSMALL}';

        if (ctypeSMALLselect == 'No') {
            $(".in-case-of-small-row").hide();
        }

        var ctypeLARGEselect = '${ctypeLARGE}';
        if (ctypeLARGEselect == 'No') {
            $(".in-case-of-large-row").hide();
        }

        var ctypeMEGAselect = '${ctypeMEGA}';
        if (ctypeMEGAselect == 'No') {
            $(".in-case-of-mega-row").hide();
        }

        var ctypeSUPERMEGAselect = '${ctypeSUPERMEGA}';
        if (ctypeSUPERMEGAselect == 'No') {
            $(".in-case-of-supermega-row").hide();
        }

        var phase = '${phsWsAply}';

        if (phase == 'No') {
            $(".phase").hide();
        }

        var ctype = '${category}';
        if (ctype == 'Mega' || ctype == 'Mega Plus' || ctype == 'Super Mega') {
            document.getElementById("percent").innerHTML = '300';

        } else {
            document.getElementById("percent").innerHTML = '100';
            $(".in-case-of-inMMSM-row").hide();
        }

        var regionName = '${region}';
        var districtName = '${district}';

        if (regionName == 'Bundelkhand' || regionName == 'Poorvanchal') {
            document.getElementById("sdepercent").innerHTML = '100';
            document.getElementById("sdrpercent").innerHTML = '100';

        }
        if (regionName == 'Paschimanchal') {
            document.getElementById("sdepercent").innerHTML = '75';
            document.getElementById("sdrpercent").innerHTML = '75';
        }
        if (regionName == 'Madhyanchal') {
            document.getElementById("sdepercent").innerHTML = '75';
            document.getElementById("sdrpercent").innerHTML = '75';

        }

        if (districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD') {
            document.getElementById("sdepercent").innerHTML = '50';
            document.getElementById("sdrpercent").innerHTML = '50';
        }

    });

    window.onload = function() {

        if ('${enableIncludeAgenda}' != '') {
            document.getElementById("button2").disabled = false;
            /* document.getElementById("button4").disabled = false; */
            //document.getElementById("button5").disabled = false;
            /* document.getElementById("button6").disabled = false; */
            document.getElementById("reject").disabled = false;

        }
        $("#edittable *").attr('readonly', true);
    }

    function sendAcknowledgeInvestor() {
        var r = confirm("Are you Sure Want to Send the Acknowledgement to Investor?");
        var apcId = '${appId}';
        if (r == true) {
            alert("Your Application ID - " + apcId
                    + " Evaluated Successfully, Please Keep your Application ID for Your Reference in the Future.");
            // return false
            //window.location.href = 'https://niveshmitra.up.nic.in/Default.aspx';
        } else {
            return false
        }

    }

    function Saveconfirm() {

        if (!$('#verified').is(':checked')) {
            alert('Please check and verify the form.');
            $("#verified").focus();
            return false;
        }

        var r = confirm("Are you sure, You want to save the form data ?");

        if (r == true) {
            alert("Application Data Saved Successfully");
        } else {
            return false
        }

    }

    function RaiseQuery() {
        var r = confirm("Are you Sure Want to Submit the Query ?");

        if (r == true) {
            alert("Query Raised Successfully");
        } else {
            return false
        }

    }
    /* Sachin */
    function RejectApplication() {
        var r = confirm("Are you Sure Want to Reject the Application ?");

        if (r == true) {
            alert("Application Rejected Successfully");
        } else {
            return false
        }

    }

    function includeAgendaNote() {
        var r = confirm("Are you Sure Want to Include Application in Agenda Note?");

        if (r == true) {
            alert("Application Included In Prepare Agenda Note Successfully");
        } else {
            return false
        }

    }
    //vinay start
    function enableButton2() {

        var r = confirm("Are you Sure Want to Complete the Evaluation?");
        var apcId = '${appId}';
        if (r == true) {
            alert("Your Application ID - " + apcId
                    + "Evaluated Successfully, Please Keep your Application ID for Your Reference in the Future.");
            document.getElementById("button2").disabled = false;
            /* document.getElementById("button4").disabled = false; */
            document.getElementById("button5").disabled = false;
            /* document.getElementById("button6").disabled = false; */
            document.getElementById("reject").disabled = false;
        } else {
            return false
        }
    }

    function CostasperDPR() {

        var sum = 0;
        $(".CostasperDPR").each(function() {
            sum += +$(this).val();
        });

        /* <c:if test="${not empty evalCapitalInvList}">

        <c:forEach var="eciObj" items="${evalCapitalInvList}" varStatus="counter">
        if ('${eciObj.eciIsFci}' == 'Yes' || '${eciObj.eciIsFci}' == 'No') {
        	$(".CostasperDPRECI").each(function() {
        		sum += +$(this).val();
        	});
        }

        </c:forEach>
        </c:if> */

        $(".TotalCostasperDPR").val(sum);
        $("#cAStatutoryAmt").removeAttr("readonly");
    }

    $(document).ready(CostasperDPR);
    $(document).on("keyup", CostasperDPR);

    $(document).on("change", CostasperDPR);

    function landcostIIEP() {
        var iiepland = 0;
        var landcostIIEPRule = document.getElementById("TotalCostasperDPRId").value;
        //alert("landcost  "+landcostIIEPRule);
        iiepland = parseInt(landcostIIEPRule);
        iiepland = iiepland / 4;
        //alert("iiepland  "+iiepland);
        document.getElementById("iieplandcost").value = Math.ceil(iiepland);

        var iieppValue = parseInt($("#iieplandcost").val());
        var dprValue = parseInt($("#dprland").val());

        if (iieppValue > dprValue) {
            $("#iieplandcost").val(dprValue);
            //alert("dprValue > "+dprValue);
        } else {
            //alert("dprValue < "+Math.ceil(iiepland));
            $("#iieplandcost").val(Math.ceil(iiepland));
        }
    }
    $(document).ready(landcostIIEP);
    $(document).on("keyup", landcostIIEP);
    $(document).on("change", landcostIIEP);

    function CostasperIIEPP() {
        var sum = 0;
        $(".CostasperIIEPP").each(function() {
            sum += +$(this).val();
        });
        $(".TotalCostasperIIEPP").val(sum);
    }

    $(document).ready(CostasperIIEPP);
    $(document).on("keyup", CostasperIIEPP);
    $(document).on("change", CostasperIIEPP);

    function MFCostasperDPR() {
        var sum = 0;
        $(".MFCostasperDPR").each(function() {
            sum += +$(this).val();
        });
        $(".TotalMFCostasperDPR").val(sum);
    }

    $(document).ready(MFCostasperDPR);
    $(document).on("keyup", MFCostasperDPR);

    function MFCostasperIIEPP() {
        var sum = 0;
        $(".MFCostasperIIEPP").each(function() {
            sum += +$(this).val();
        });
        $(".TotalMFCostasperIIEPP").val(sum);
    }

    $(document).ready(MFCostasperIIEPP);
    $(document).on("keyup", MFCostasperIIEPP);

    function myEditInput() {

        var r = confirm('Are you Sure Want to Edit the Application Data?');

        if (r == true) {

            $("#edittable *").attr('readonly', false);
            /* $("#totalEmploymentId").removeAttr("readonly"); */
            $("#categoryId").removeAttr("readonly");
            $("#invFci").removeAttr("readonly");
            $("#cAStatutoryAmt").removeAttr("readonly");
            $("#invtpc").removeAttr("readonly");
            //$("#pergrosblockId").removeAttr("readonly");

        } else {
            return false
        }
    }

    function showYesOrNo1() {
        var natureOfProject = '${natureOfProject}';
        if (natureOfProject === 'NewProject') {

            $(".hide-tbl-row").hide();
        }

        else
            $(".hidep-tbl-row").hide();

    }
    $(document).on("change", showYesOrNo1);
    $(document).ready(showYesOrNo1);

    function showYesOrNo2() {
        var ISF_Claim_Reim = '${incentiveDeatilsData.ISF_Claim_Reim}';
        if (ISF_Claim_Reim == '') {
            //alert("hi");
            $(".ISF_Claim_Reim-row").hide();
        }
        var ISF_Reim_SCST = '${incentiveDeatilsData.ISF_Reim_SCST}';
        //if( iSF_Reim_BPLW == '')
        if (ISF_Reim_SCST == '') {
            //alert(ISF_Reim_SCST);
            $(".ISF_Reim_SCST-row").hide();
        }

        var ISF_Reim_FW = '${incentiveDeatilsData.ISF_Reim_FW}';
        if (ISF_Reim_FW == '') {
            //alert("hi");
            $(".ISF_Reim_FW-row").hide();
        }

        var iSF_Reim_BPLW = '${incentiveDeatilsData.ISF_Reim_BPLW}';
        if (iSF_Reim_BPLW == '') {
            //alert("hi");
            $(".ISF_Reim_BPLW-row").hide();
        }

        var ISF_Stamp_Duty_EX = '${incentiveDeatilsData.ISF_Stamp_Duty_EX}';
        if (ISF_Stamp_Duty_EX == '') {
            //alert("hi");
            $(".ISF_Stamp_Duty_EX-row").hide();
        }

        var ISF_Amt_Stamp_Duty_Reim = '${incentiveDeatilsData.ISF_Amt_Stamp_Duty_Reim}';
        if (ISF_Amt_Stamp_Duty_Reim == '') {
            //alert("hi");
            $(".ISF_Amt_Stamp_Duty_Reim-row").hide();
        }

        var ISF_Additonal_Stamp_Duty_EX = '${incentiveDeatilsData.ISF_Additonal_Stamp_Duty_EX}';
        if (ISF_Additonal_Stamp_Duty_EX == '') {
            //alert("hi");
            $(".ISF_Additonal_Stamp_Duty_EX-row").hide();
        }

        var ISF_Epf_Reim_UW = '${incentiveDeatilsData.ISF_Epf_Reim_UW}';
        if (ISF_Epf_Reim_UW == '') {
            //alert("hi");
            $(".ISF_Epf_Reim_UW-row").hide();
        }

        var ISF_Add_Epf_Reim_SkUkW = '${incentiveDeatilsData.ISF_Add_Epf_Reim_SkUkW}';
        if (ISF_Add_Epf_Reim_SkUkW == '') {
            //alert("hi");
            $(".ISF_Add_Epf_Reim_SkUkW-row").hide();
        }

        var ISF_Add_Epf_Reim_DIVSCSTF = '${incentiveDeatilsData.ISF_Add_Epf_Reim_DIVSCSTF}';
        if (ISF_Add_Epf_Reim_DIVSCSTF == '') {
            //alert("hi");
            $(".ISF_Add_Epf_Reim_DIVSCSTF-row").hide();
        }

        var ISF_Cis = '${incentiveDeatilsData.ISF_Cis}';
        if (ISF_Cis == '') {
            //alert("hi");
            $(".ISF_Cis-row").hide();
        }

        var ISF_ACI_Subsidy_Indus = '${incentiveDeatilsData.ISF_ACI_Subsidy_Indus}';
        if (ISF_ACI_Subsidy_Indus == '') {
            //alert("hi");
            $(".ISF_ACI_Subsidy_Indus-row").hide();
        }

        var ISF_Infra_Int_Subsidy = '${incentiveDeatilsData.ISF_Infra_Int_Subsidy}';
        if (ISF_Infra_Int_Subsidy == '') {
            //alert("hi");
            $(".ISF_Infra_Int_Subsidy-row").hide();
        }

        var ISF_AII_Subsidy_DIVSCSTF = '${incentiveDeatilsData.ISF_AII_Subsidy_DIVSCSTF}';
        if (ISF_AII_Subsidy_DIVSCSTF == '') {
            //alert("hi");
            $(".ISF_AII_Subsidy_DIVSCSTF-row").hide();
        }

        var ISF_Loan_Subsidy = '${incentiveDeatilsData.ISF_Loan_Subsidy}';
        if (ISF_Loan_Subsidy == '') {
            //alert("hi");
            $(".ISF_Loan_Subsidy-row").hide();
        }

        var ISF_Tax_Credit_Reim = '${incentiveDeatilsData.ISF_Tax_Credit_Reim}';
        if (ISF_Tax_Credit_Reim == '') {
            //alert("hi");
            $(".ISF_Tax_Credit_Reim-row").hide();
        }

        var ISF_EX_E_Duty = '${incentiveDeatilsData.ISF_EX_E_Duty}';
        if (ISF_EX_E_Duty == '') {
            //alert("hi");
            $(".ISF_EX_E_Duty-row").hide();
        }

        var ISF_EX_E_Duty_PC = '${incentiveDeatilsData.ISF_EX_E_Duty_PC}';
        if (ISF_EX_E_Duty_PC == '') {
            //alert("hi");
            $(".ISF_EX_E_Duty_PC-row").hide();
        }

        var ISF_EX_Mandee_Fee = '${incentiveDeatilsData.ISF_EX_Mandee_Fee}';
        if (ISF_EX_Mandee_Fee == '') {
            //alert("hi");
            $(".ISF_EX_Mandee_Fee-row").hide();
        }

        var ISF_Indus_Payroll_Asst = '${incentiveDeatilsData.ISF_Indus_Payroll_Asst}';
        if (ISF_Indus_Payroll_Asst == '') {
            //alert("hi");
            $(".ISF_Indus_Payroll_Asst-row").hide();
        }
    }
    $(document).ready(showYesOrNo2);
</script>
<script type="text/javascript">
    $(function() {
        $("#UploadDocumentForQuery").change(function() {
            if (fileExtValidate(this)) {
                if (FileSizeValidate(this)) {
                    return true;
                }
            }
        });

        // File extension validation, Add more extension you want to allow
        var validExt = ".pdf";
        function fileExtValidate(fdata) {
            var filePath = fdata.value;
            var getFileExt = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();
            var pos = validExt.indexOf(getFileExt);
            if (pos < 0) {
                document.getElementById("UploadDocumentForQuery").value = '';
                document.getElementById("UploadDocumentForQuery1").innerHTML = '';
                alert("Please upload PDF file format.");

                return false;
            } else {
                return true;
            }
        }

        // photo file size validation
        // size in mb
        function FileSizeValidate(fdata) {
            var maxSize = '2';//size in KB
            if (fdata.files && fdata.files[0]) {
                var fsize = fdata.files[0].size / (1024 * 1024);
                if (fsize > maxSize) {
                    alert('File size should not be more than 2MB. The uploaded file size is: ' + fsize + " MB");
                    document.getElementById("UploadDocumentForQuery").value = '';
                    document.getElementById("UploadDocumentForQuery1").innerHTML = '';
                    return false;
                } else {
                    return true;
                }
            }
        }

    });

    function validateNumberField() {
        return event.charCode > 47 && event.charCode < 58;

    }

    $(document).ready(function() {

        if ('${invGovtEquity}' == 'No') {
            $("#govtequity").val("No");
        } else {
            $("#govtequity").val("Yes");
        }

        if ('${invEligcapInvest}' == 'No') {
            $("#eligcapinvest").val("No");
        } else {
            $("#eligcapinvest").val("Yes");
        }

        if ('${invLandcostFci}' == 'No') {
            $("#landcostfci").val("No");
        } else {
            $("#landcostfci").val("Yes");
        }

        if ('${invBuildingFci}' == 'No') {
            $("#buildingfci").val("No");
        } else {
            $("#buildingfci").val("Yes");
        }

        if ('${invPlantAndMachFci}' == 'No') {
            $("#plantfci").val("No");
        } else {
            $("#plantfci").val("Yes");
        }

        if ('${invFixedAssetFci}' == 'No') {
            $("#fixassetfci").val("No");
        } else {
            $("#fixassetfci").val("Yes");
        }

        if ('${isPresFormat}' == 'No') {
            $("#presFormatId").val("No");
        } else {
            $("#presFormatId").val("Yes");
        }

        if ('${isDocAuthorized}' == 'No') {
            $("#docAuthorizedId").val("No");
        } else {
            $("#docAuthorizedId").val("Yes");
        }

        /* <c:if test="${not empty evalCapitalInvList}">

        <c:forEach var="eciObj" items="${evalCapitalInvList}" varStatus="counter">
        if ('${eciObj.eciIsFci}' == 'No') {
        	$("#ConsiderasFCI").val("No");
        } else {
        	$("#ConsiderasFCI").val("Yes");
        }


        
        </c:forEach>
        </c:if> */

    });

    //To validate AddMoreItems field
    function validateAddMoreItems() {
        var item = $("#itemName").val();
        var dprInput = $("#DPRInput").val();
        var iieppInput = $("#IIEPPInput").val();
        var picupRemarks = $("#PICUPRemarks").val();

        if (item == null || item == '') {
            document.getElementById('itemname').innerHTML = "Item field is required";
            document.getElementById('itemName').focus();
            return false;
        }

        else if (dprInput == null || dprInput == '') {
            document.getElementById('dprinput').innerHTML = "Investment as per DPR is required";
            document.getElementById('DPRInput').focus();
            return false;
        } else if (iieppInput == null || iieppInput == '') {
            document.getElementById('iieppinput').innerHTML = "Capital Investment Eligible as per IIEPP Policy Rule is required";
            document.getElementById('IIEPPInput').focus();
            return false;
        }

        return true;

    }

    //To remove required error message 
    function removeAddMoreItemsErrMsg() {
        $("#itemname").text('');
        $("#dprinput").text('');
        $("#iieppinput").text('');
    }
</script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#input").on("keypress keyup", function() {
            if ($(this).val() == '0') {
                $(this).val('');
            }
        });
    });
</script>
</head>
<body>
	<button onclick="topFunction()" id="GoToTopBtn" title="Go to top">Top</button>
	<section class="inner-header">
		<!-- Navigation / Navbar / Menu -->
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="#"><img src="images/logo.png" class="logo" alt="Logo"></a>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
					<i class="fa fa-user"></i>
				</button>
				<div class="flex-row d-flex left-menu-toggle">
					<button type="button" class="hidden-md-up navbar-toggler" data-toggle="offcanvas" title="Toggle responsive left sidebar">
						<span class="navbar-toggler-icon"></span>
					</button>
				</div>
				<div class="collapse navbar-collapse" id="navbarTogglerDemo02">
					<ul class="navbar-nav ml-auto mt-2 mt-lg-0">
						<li class="nav-item">
							<a class="nav-link" href="./userLogout2"><i class="fa fa-power-off"></i> Logout</a>
						</li>
						<li class="nav-item">
							<a class="nav-link active" href="#"><i class="fa fa-user"></i>${userName} </a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<!-- End Navigation / Navbar / Menu -->
	</section>
	<section class="dashboard-wrapper">
		<div class="container-fluid" id="main">
			<div class="row row-offcanvas row-offcanvas-left">
				<div class="col-md-3 col-lg-2 sidebar-offcanvas pt-3" id="sidebar" role="navigation">
					<ul class="nav flex-column pl-1">
					  <c:if test="${not empty Tblformactiondsplname}">
		               <c:forEach var="Map" items="${Tblformactiondsplname}" varStatus="counter">
		                            <c:set var="dsplname" value="${fn:toUpperCase(Map.value)}" />
		                           <c:choose>
			                           <c:when test = "${fn:contains(dsplname, 'DASHBOARD')}">
					                       <li class="nav-item animate__animated animate__bounce">
						                       <a class="nav-link active" href="${Map.key}">
						                         <i class="fa fa-tachometer"></i>${dsplname}
						                       </a>
					                       </li>
					                   </c:when> 
					                    <c:when test = "${fn:contains(dsplname, 'VIEW')}">
					                       <li class="nav-item">
						                       <a class="nav-link" href="${Map.key}">
						                         <i class="fa fa-eye"></i>${dsplname}
						                       </a>
					                       </li>
					                   </c:when> 
					                   <c:when test = "${fn:contains(dsplname, 'MEETING')}">
					                       <li class="nav-item">
						                       <a class="nav-link" href="${Map.key}">
						                         <i class="fa fa-calendar"></i>${dsplname}
						                       </a>
					                       </li>
					                   </c:when> 
					                   <c:otherwise>
								           <li class="nav-item animate__animated animate__bounce">
						                       <a class="nav-link active" href="${Map.key}">
						                         <i class="fa fa-tachometer"></i>${dsplname}
						                       </a>
					                       </li>
								       </c:otherwise>
				                   </c:choose>
			                      </c:forEach>
		            </c:if>
		          </ul>

				</div>
				<!--/col-->
				<div class="col-md-9 col-lg-10 mt-4 main mb-4">
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-5">
								<a href="./viewApplicationDetails?applicantId=<%=session.getAttribute("appId")%>" class="common-default-btn mt-3">Back</a>
							</div>
							<div class="col-sm-7 text-right">
								<a href="#EvaluationAuditTrail" class="text-info mr-3" data-toggle="modal">Evaluation Audit Trail</a>
								<button type="button" class="common-btn mt-3" onclick="myEditInput()">
									<i class="fa fa-edit"></i>Edit
								</button>
							</div>
						</div>
						<spring:url value="/EvaluateProjectDetails" var="EvaluateProjectDetailsActionUrl" />
						<form:form modelAttribute="EvaluateProjectDetails" method="post" action="${EvaluateProjectDetailsActionUrl}" class="mt-4" autocomplete="off">
							<form:hidden path="id" />
							<div class="row mt-5">
								<div class="col-sm-12">
									<div class="table-responsive">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>Application ID</th>
													<th>Company Name</th>
													<th>Type</th>
													<th>Product</th>
													<th>Region</th>
													<th>District</th>
													<th>Investment</th>
													<th>Category</th>
												</tr>
											</thead>
											<tbody id="evalTable">
												<c:forEach var="list" items="${evalObjList}" varStatus="counter">
													<tr>
														<td>
															<c:out value="${list[0]}" />
														</td>
														<td>
															<c:out value="${list[1]}" />
														</td>
														<td>
															<c:out value="${natureOfProject}" />
														</td>
														<td>
															<c:out value="${products}" />
														</td>
														<td>
															<c:out value="${list[2]}" />
														</td>
														<td>
															<c:out value="${list[3]}" />
														</td>
														<td>
															<c:out value="${list[4]}" />
														</td>
														<td>
															<c:out value="${list[5]}" />
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								<div class="col-md-12 mt-4">
									<div class="form-group">
										<label>Add Promoters &amp; Proposed project Details</label>
										<form:textarea path="propsProdtDetailObserv" name="propsProdtDetailObserv" class="form-control" value="${propsProdtDetailObserv}" rows="5"></form:textarea>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="mt-4">
										<div class="without-wizard-steps">
											<h4 class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Evaluate Application</h4>
										</div>
									</div>
									<hr class="mt-2 mb-5">
									<div class="row">
										<div class="col-sm-12">
											<div class="table-responsive">
												<table class="table table-bordered">
													<thead>
														<tr>
															<th>Eligibility Criteria</th>
															<th>Compliance</th>
															<th>Observations By PICUP</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td style="width: 33%;">
																<label class="table-heading">New Project or Expansion / Diversification</label>
															</td>
															<td style="width: 33%;">
																<input type="text" readonly="readonly" class="form-control" value="${natureOfProject}" disabled="disabled">
															</td>
															<td>
																<form:textarea name="projectObserv" path="projectObserv" value="${projectObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr class="hidep-tbl-row">
															<td>
																<label class="table-heading">Product Details</label>
															</td>
															<td>
																<form:input path="productDetails" type="text" maxlength="500" value="${products}" class="form-control" name=""></form:input>
															</td>
															<td>
																<form:textarea path="prodDetailObserv" name="prodDetailObserv" class="form-control" rows="2" />
															</td>
														</tr>
														<tr>
															<td colspan="3" class="p-4">
																<div class="table-responsive mt-3" id="productsDetailsTbl">
																	<table class="table table-stripped table-bordered" id="edittable">
																		<thead>
																			<c:if test="${not empty evaluateExistNewProjDetailsList}">
																				<tr>
																					<th></th>
																					<th>Existing Products</th>
																					<th>Existing Installed Capacity</th>
																					<th>Proposed Products</th>
																					<th>Proposed Installed Capacity</th>
																					<th>Existing Gross Block</th>
																					<th>Proposed Gross Block</th>
																				</tr>
																			</c:if>
																		</thead>
																		<tbody class="existing-proposed-products">
																			<c:forEach var="list" items="${evaluateExistNewProjDetailsList}" varStatus="counter">
																				<tr>
																					<td>
																						<form:hidden path="epdId" value="${list.epdId}" />
																					</td>
																					<td>
																						<form:input path="epdExisProducts" id="epdExisProductsId" name="epdExisProducts" value="${list.epdExisProducts}" class="form-control"></form:input>
																					</td>
																					<td>
																						<form:input path="epdExisInstallCapacity" id="epdExisInstallCapacityId" name="epdExisInstallCapacity" value="${list.epdExisInstallCapacity}" class="form-control"></form:input>
																					</td>
																					<td>
																						<form:input path="epdPropProducts" id="epdPropProductsId" name="epdPropProducts" value="${list.epdPropProducts}" class="form-control"></form:input>
																					</td>
																					<td>
																						<form:input path="epdPropInstallCapacity" id="epdPropInstallCapacityId" name="epdPropInstallCapacity" value="${list.epdPropInstallCapacity}" class="form-control"></form:input>
																					</td>
																					<td>
																						<form:input path="epdExisGrossBlock" id="epdExisGrossBlockId" name="epdExisGrossBlock" value="${list.epdExisGrossBlock}" class="form-control"></form:input>
																					</td>
																					<td>
																						<form:input path="epdPropoGrossBlock" id="epdPropoGrossBlockId" name="epdPropoGrossBlock" value="${list.epdPropoGrossBlock}" class="form-control"></form:input>
																					</td>
																				</tr>
																			</c:forEach>
																		</tbody>
																	</table>
																</div>
																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Location</label>
																	</div>
																</div>
																<div class="table-responsive mt-3">
																	<table class="table table-stripped table-bordered">
																		<tbody>
																			<tr>
																				<td style="width: 33%;">Full Registered Address of Company</td>
																				<td style="width: 33%;">
																					<input type="text" name="fullAddress" class="form-control" id="fullAddress" readonly="true" value="${businessAddress}" /> <span id="fullAddress" class="text-danger color:red"></span>
																					<form:errors path="fullAddress" class="control-label" cssStyle="color:red" />
																				</td>
																				<td rowspan="4">
																					<form:textarea name="locationObserv" path="locationObserv" placeholder="Observations Comment By PICUP" value="${locationObserv}" class="form-control" rows="10"></form:textarea>
																				</td>
																			</tr>
																			<tr>
																				<td>Full Address of Unit</td>
																				<td>
																					<input type="text" class="form-control" value="${fullAddrs}" disabled="" name="">
																				</td>
																			</tr>
																			<tr>
																				<td>District</td>
																				<td>
																					<input type="text" name="districtName" class="form-control" readonly="true" id="districtName" value="${district}"><span id="DistrictName" class="text-danger color:red"></span>
																					<form:errors path="districtName" class="control-label" cssStyle="color:red" />
																				</td>
																			</tr>
																			<tr>
																				<td>Region</td>
																				<td>
																					<input type="text" name="resionName" class="form-control" id="resionName" readonly="true" value="${region}"> <span id="resionName" class="text-danger color:red"></span>
																					<form:errors path="resionName" class="control-label" />
																				</td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3">
																<div class="table-responsive">
																	<label class="table-heading mb-3">Director Details</label>
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th>Name</th>
																				<th>Designation</th>
																				<th>% Equity</th>
																				<th>Mobile No.</th>
																				<th>Address</th>
																				<th>Email</th>
																				<th>Gender</th>
																				<th>Category</th>
																				<th>Divyang</th>
																				<th>PAN No.</th>
																				<th>DIN</th>
																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach var="list" items="${ProprietorDetailsList}">
																				<tr>
																					<td>${list.directorName}</td>
																					<td>${list.designation}</td>
																					<td>${list.equity}</td>
																					<td>${list.mobileNo}</td>
																					<td>${list.proprietorDetailsaddress}</td>
																					<td>${list.email}</td>
																					<td>${list.gender}</td>
																					<td>${list.category}</td>
																					<td>${list.div_Status}</td>
																					<td>${list.panCardNo}</td>
																					<td>${list.din}</td>
																				</tr>
																			</c:forEach>
																		</tbody>
																	</table>
																	<form:textarea name="dirDetailsObserv" path="dirDetailsObserv" id="direcObserv" placeholder="Observations Comment By PICUP" value="${dirDetailObserv}" class="form-control" rows="3"></form:textarea>
																</div>
															</td>
														</tr>
														<tr>
															<td colspan="3" class="p-4">
																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Employment</label>
																	</div>
																</div>
																<div class="table-responsive mt-3">
																	<table class="table table-stripped table-bordered">
																		<tbody>
																			<tr>
																				<td style="width: 50%;">Total Direct Employment</td>
																				<td>
																					<input type="text" class="form-control" value="${grossTotalMaleandFemaleEmployment}" readonly="readonly" name="" id="totalEmploymentId">
																				</td>
																				<td>
																					<form:textarea name="totalDetailObserv" path="totalDetailObserv" id="totalempObserv" placeholder="Observations Comment By PICUP" value="${totalEmpDetailObserv}" class="form-control" rows="2"></form:textarea>
																				</td>
																			</tr>
																			<tr>
																				<td colspan="3">
																					<div class="table-responsive">
																						<table class="table table-stripped table-bordered" id="customFields" tabindex="1">
																							<thead>
																								<tr>
																									<th>Skilled/Unskilled</th>
																									<th>Male</th>
																									<th>Female</th>
																									<th>General</th>
																									<th>SC</th>
																									<th>ST</th>
																									<th>OBC</th>
																									<th>BPL</th>
																									<th>Divyang</th>
																								</tr>
																							</thead>
																							<tbody>
																								<c:forEach var="list" items="${skilledUnSkilledEmployemntList}" varStatus="counter">
																									<tr>
																										<td>${list.skilledUnskilled}</td>
																										<td>${list.numberofMaleEmployees}</td>
																										<td>${list.numberOfFemaleEmployees}</td>
																										<td>${list.numberOfGeneral}</td>
																										<td>${list.numberOfSc}</td>
																										<td>${list.numberOfSt}</td>
																										<td>${list.numberOfObc}</td>
																										<td>${list.numberOfBpl}</td>
																										<td>${list.numberOfDivyang}</td>
																									</tr>
																								</c:forEach>
																							</tbody>
																						</table>
																					</div>
																				</td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<c:if test="${natureOfProject=='Expansion'}">
															<tr>
																<td>
																	<label class="table-heading">If Expansion / Diversification whether proposed gross block more than 25% of the existing gross block <a href="./downloadExistProjDoc?fileName=${caCertificateFileName}"><small>(View File)</small></a>
																	</label>
																</td>
																<td>
																	<input type="text" class="form-control" value="Yes" readonly="readonly" id="pergrosblockId">
																</td>
																<td>
																	<form:textarea name="expDivfObserv" path="expDivfObserv" placeholder="Observations Comment By PICUP" value="${expDivfObserv}" class="form-control" rows="2"></form:textarea>
																</td>
															</tr>
														</c:if>
														<tr>
															<td>
																<label class="table-heading">Category of Industrial Undertaking</label>
															</td>
															<td>
																<form:input path="invIndType" class="form-control" value="${category}" readonly="true" id="categoryId"></form:input>
															</td>
															<td>
																<form:textarea name="catIndusUndtObserv" path="catIndusUndtObserv" id="catObserv" placeholder="Observations Comment By PICUP" value="${invcatundtakObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">Total cost of project and Investment in Plant and Machinery</label>
															</td>
															<td>
																<textarea class="form-control" rows="2" disabled="disabled">Total Cost of Project ${InvTPC} , &#13;&#10;Plant & Machinery Cost ${invPlantAndMachCost} </textarea>
															</td>
															<td>
																<form:textarea name="propsPlntMcnryCostObserv" path="propsPlntMcnryCostObserv" id="plantMachObserv" placeholder="Observations Comment By PICUP" value="${propsPlntMcnryCostObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">Opted Cut-off date investment</label>
															</td>
															<td>
																<form:input type="text" path="invCommenceDate" value="${optedcufoffdate}" class="form-control" readonly="true"></form:input>
															</td>
															<td>
																<form:textarea name="optcutofdateobserv" path="optcutofdateobserv" id="cutoffdateObserv" placeholder="Observations Comment By PICUP" value="${optcutofdateObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">Proposed Date of Commencement of Commercial Production</label>
															</td>
															<td>
																<form:input type="text" path="propCommProdDate" value="${invPropCommProdDate}" class="form-control" readonly="true"></form:input>
															</td>
															<td>
																<form:textarea name="dateofComProdObserv" path="dateofComProdObserv" id="comprodObserv" placeholder="Observations Comment By PICUP" value="${dateofcumProdObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<c:if test="${natureOfProject=='Expansion'}">
															<tr>
																<td colspan="2">
																	<label class="table-heading">Detailed Project Report prepared by External Consultant/Chartered Accountants <a href="./downloadExistProjDoc?fileName=${enclDetProRepFileName}"><small>(View File)</small></a>
																	</label>
																</td>
																<td>
																	<form:textarea name="detailProjReportObserv" path="detailProjReportObserv" id="detailProjReportObserv" placeholder="Observations Comment By PICUP" value="${projreportObserv}" class="form-control" rows="2"></form:textarea>
																</td>
															</tr>
														</c:if>
														<c:if test="${natureOfProject=='NewProject'}">
															<tr>
																<td colspan="2">
																	<label class="table-heading">Detailed Project Report prepared by External Consultant/Chartered Accountants <a href="./downloadNewProjDoc?fileName=${enclDetProRepFileName}"><small>(View File)</small></a>
																	</label>
																</td>
																<td>
																	<form:textarea name="detailProjReportObserv" path="detailProjReportObserv" placeholder="Observations Comment By PICUP" value="${projreportObserv}" class="form-control" rows="2"></form:textarea>
																</td>
															</tr>
														</c:if>
														<c:if test="${natureOfProject !='NewProject'}">
															<tr>
																<td>
																	<label class="table-heading">List of Assets</label>
																	<p>
																		Whether List of Assets (in Expansion/Diversification Cases only) certified by C.E. Submitted. <a href="./downloadExistProjDoc?fileName=${charatEngFileName}">View File</a>
																	</p>
																</td>
																<td>
																	<textarea name="listAssets" class="form-control" rows="3">${listAssets}</textarea>
																</td>
																<td>
																	<form:textarea name="listAssetsObserv" path="listAssetsObserv" placeholder="Observations Comment By PICUP" value="${listAssetsObserv}" class="form-control" rows="2"></form:textarea>
																</td>
															</tr>
														</c:if>
														<tr>
															<td>
																<label class="table-heading">Undertaking</label>
																<p>
																	Whether notarized undertaking as per format placed at Annexure I-A (as per format of IIEPP Rules- 2017) on Stamp Paper of Rs. 10/-submitted. <a href="./downloadBusinessEntityDoc?fileName=${bodDocFname}">View File</a>
																</p>
															</td>
															<td>
																<select name="anexurUndertk" class="form-control" disabled="">
																	<option value="Yes" selected="">Yes</option>
																	<option value="No">No</option>
																</select>
															</td>
															<td>
																<form:textarea name="anexurUndertkObserv" path="anexurUndertkObserv" placeholder="Observations Comment By PICUP" value="${anexurUndertkObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td colspan="3" class="p-4">
																<div class="row">
																	<div class="col-sm-12">
																		<label class="table-heading">Capital Investment Details</label>
																	</div>
																</div>
																<div class="table-responsive mt-3">
																	<table class="table table-stripped table-bordered">
																		<tbody>
																			<tr>
																				<td>Proposed Capital Investment</td>
																				<td>
																					<form:input id="invFci" path="invFci" class="form-control" value="${InvFCI}" onkeypress="return validateNumberField()" maxlength="12" readonly="true"></form:input>
																				</td>
																				<td>
																					<form:textarea name="propCapInvObserv" path="propCapInvObserv" id="capinvObserv" placeholder="Observations Comment By PICUP" value="${propCapInvObserv}" class="form-control" rows="2"></form:textarea>
																				</td>
																			</tr>
																			<tr>
																				<td>Total Cost of Project</td>
																				<td>
																					<form:input id="invtpc" path="invTotalProjCost" class="form-control" value="${InvTPC}" onkeypress="return validateNumberField()" maxlength="12" readonly="true"></form:input>
																				</td>
																				<td>
																					<form:textarea name="totlCostProjObserv" path="totlCostProjObserv" id="totlcostObserv" placeholder="Observations Comment By PICUP" value="${totlcostprojObserv}" class="form-control" rows="2"></form:textarea>
																				</td>
																			</tr>
																		</tbody>
																	</table>
																</div>
																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Break up of proposed capital Investment</label>
																	</div>
																</div>
																<div class="table-responsive mt-3">
																	<table id="BreakUpTable" class="table table-stripped table-bordered">
																		<thead>
																			<tr>
																				<!-- <th>Select</th> -->
																				<th>Item</th>
																				<th>Consider as FCI</th>
																				<th>Investment as per DPR or Document Provided</th>
																				<th>Capital Investment Eligible as per IIEPP Policy Rule - 2017</th>
																				<th>PICUP Officer's Remarks</th>
																				<th>Action</th>
																			</tr>
																		</thead>
																		<tbody class="add-from-here">
																			<tr>
																				<td>Land Cost</td>
																				<td>
																					<form:select class="form-control" path="landcostFci" id="landcostfci" disabled="true">
																						<form:option value="Yes">Yes</form:option>
																						<form:option value="No">No</form:option>
																					</form:select>
																				</td>
																				<td>
																					<input class="CostasperDPR form-control" id="dprland" type="text" name="" value="${InvLC}" readonly="readonly">
																				</td>
																				<td>
																					<form:input type="text" id="iieplandcost" path="landcostIIEPP" maxlength="12" onkeypress="return validateNumberField()" class="CostasperIIEPP form-control" readonly="true"></form:input>
																				</td>
																				<td>
																					<form:textarea class="form-control" path="landcostRemarks" rows="2" value="${invLandcostRemarks}" maxlength="1000"></form:textarea>
																				</td>
																				<td rowspan="4">&nbsp;</td>
																			</tr>
																			<tr>
																				<td>Building,Site Development & Civil Works Cost</td>
																				<td>
																					<form:select class="form-control" path="buildingFci" id="buildingfci" disabled="true">
																						<form:option value="Yes">Yes</form:option>
																						<form:option value="No">No</form:option>
																					</form:select>
																				</td>
																				<td>
																					<input type="text" class="CostasperDPR form-control" name="" value="${InvBuiildC}" readonly="readonly">
																				</td>
																				<td>
																					<form:input type="text" path="buildingIIEPP" value="${invBuildingIIEPP}" maxlength="12" onkeypress="return validateNumberField()" class="CostasperIIEPP form-control" name=""></form:input>
																					<small class="d-block text-info" id="iiepwords"></small>
																				</td>
																				<td>
																					<form:textarea type="text" path="buildingRemarks" value="${invBuildingRemarks}" maxlength="1000" rows="2" class="form-control" name=""></form:textarea>
																				</td>
																			</tr>
																			<tr>
																				<td>Plant & Machinery Cost</td>
																				<td>
																					<form:select class="form-control" path="plantAndMachFci" id="plantfci" disabled="true">
																						<form:option value="Yes">Yes</form:option>
																						<form:option value="No">No</form:option>
																					</form:select>
																				</td>
																				<td>
																					<input type="text" class="CostasperDPR form-control" name="" value="${invPlantAndMachCost}" readonly="readonly">
																				</td>
																				<td>
																					<form:input type="text" path="plantAndMachIIEPP" value="${invPlantAndMachIIEPP}" maxlength="12" onkeypress="return validateNumberField()" class="CostasperIIEPP form-control"></form:input>
																					<small class="d-block text-info" id="plantwords"></small>
																				</td>
																				<td>
																					<form:textarea type="text" path="plantAndMachRemarks" value="${invPlantAndMachRemarks}" maxlength="1000" rows="2" class="form-control" name=""></form:textarea>
																				</td>
																			</tr>
																			<tr>
																				<td>Miscellaneous Fixed Asset(MFA)</td>
																				<td>
																					<form:select class="form-control" path="fixedAssetFci" id="fixassetfci" disabled="true">
																						<form:option value="Yes">Yes</form:option>
																						<form:option value="No">No</form:option>
																					</form:select>
																				</td>
																				<td>
																					<input type="text" class=" CostasperDPR form-control" name="" value="${invMisFixCost}" readonly="readonly">
																				</td>
																				<td>
																					<form:input type="text" path="fixedAssetIIEPP" value="${invFixedAssetIIEPP}" maxlength="12" onkeypress="return validateNumberField()" class="CostasperIIEPP form-control"></form:input>
																					<small class="d-block text-info" id="dprwords"></small>
																				</td>
																				<td>
																					<form:textarea type="text" path="fixedAssetRemarks" value="${invFixedAssetRemarks}" maxlength="1000" rows="2" class="form-control" name=""></form:textarea>
																				</td>
																			</tr>
																			<c:if test="${not empty evalCapitalInvList}">
																				<c:forEach var="eciObj" items="${evalCapitalInvList}" varStatus="counter">
																					<!-- Iterating the list using JSTL tag  -->
																					<c:if test="${not empty eciObj.eciItem  && not empty eciObj.eciDPRInvest && not empty eciObj.eciIIEPPInvest }">
																						<tr>
																							<td>
																								<input type="text" value="${eciObj.eciItem}" id="itemName1" maxlength="500" onblur="removeAddMoreItemsErrMsg()" class="form-control" readonly="readonly"></input>
																							</td>
																							<td>
																								<select class="form-control" id="ConsiderasFCI" disabled="true">
																									<option>${eciObj.eciIsFci}</option>
																									<%-- <form:option value="No">No</form:option> --%>
																								</select>
																							</td>
																							<td>
																								<input type="text" value="${eciObj.eciDPRInvest}" id="DPRInput1" maxlength="12" onkeypress="return validateNumberField()" onblur="removeAddMoreItemsErrMsg()" class=" CostasperDPR form-control" readonly="readonly"></input> <span id="dprinput" class="text-danger"></span>
																							</td>
																							<td>
																								<input type="text" value="${eciObj.eciIIEPPInvest}" id="IIEPPInput1" maxlength="12" readonly="readonly" onkeypress="return validateNumberField()" onblur="removeAddMoreItemsErrMsg()" class="CostasperIIEPP form-control"></input> <span id="iieppinput" class="text-danger"></span>
																							</td>
																							<td>
																								<input value="${eciObj.eciPICUP_Remarks}" maxlength="1000" class="form-control" id="PICUPRemarks1" rows="2" readonly="readonly"></input>
																							</td>
																							<td class="text-center">
																								<button class="border-0 bg-white" onclick="return confirm('Are you sure? Do you want to delete?')" formaction="${pageContext.request.contextPath }/deleteEvaluateCapInvest?selectedRow=${counter.index}" data-toggle="tooltip" title="Delete" id="deleteButton">
																									<i class="fa fa-trash text-danger"></i>
																								</button>
																							</td>
																						</tr>
																					</c:if>
																				</c:forEach>
																			</c:if>
																		</tbody>
																		<tfoot>
																			<tr>
																				<td>
																					<form:input path="eciItem" type="text" id="itemName" maxlength="500" onblur="removeAddMoreItemsErrMsg()" class="form-control"></form:input>
																					<span id="itemname" class="text-danger"></span>
																				</td>
																				<td>
																					<form:select path="eciIsFci" class="form-control" id="ConsiderasFCII">
																						<form:option value="Yes">Yes</form:option>
																						<form:option value="No">No</form:option>
																					</form:select>
																				</td>
																				<td>
																					<form:input type="text" path="eciDPRInvest" id="DPRInput" maxlength="12" onkeypress="return validateNumberField()" onblur="removeAddMoreItemsErrMsg()" class="CostasperDPRECI form-control is-numeric"></form:input>
																					<small class="words text-info"></small> <span id="dprinput" class="text-danger"></span>
																				</td>
																				<td>
																					<form:input type="text" path="eciIIEPPInvest" id="IIEPPInput" maxlength="12" onkeypress="return validateNumberField()" onblur="removeAddMoreItemsErrMsg()" class="CostasperIIEPP form-control is-numeric"></form:input>
																					<small class="words text-info"></small> <span id="iieppinput" class="text-danger"></span>
																				</td>
																				<td>
																					<form:textarea path="eciPICUP_Remarks" class="form-control" id="PICUPRemarks" rows="2"></form:textarea>
																				</td>
																				<td colspan="6" class="text-right">
																					<button formaction="addEvaluateCapInvest" onclick="return validateAddMoreItems()" class="btn btn-outline-success ">Add</button>
																				</td>
																			</tr>
																			<tr>
																				<td colspan="2" align="right"></td>
																				<td>
																					<strong>Total:</strong><input type="text" readonly="readonly" class="TotalCostasperDPR form-control" name="dprtotal" id="TotalCostasperDPRId">
																				</td>
																				<td>
																					<strong>Total:</strong><input class="TotalCostasperIIEPP form-control" readonly="readonly" name="">
																				</td>
																				<td colspan="2"></td>
																			</tr>
																		</tfoot>
																	</table>
																</div>
																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Breakup Fixed Capital Investment Component <a href="./downloadInvestmentDoc?fileName=${invFileName}">(View File)</a>
																		</label>
																	</div>
																	<div class="col-sm-12">
																		<div class="table-responsive mt-3">
																			<table class="table table-stripped table-bordered" id="customFields3" tabindex="3">
																				<%-- <c:if test="${not empty pwInvList}"> --%>
																				<thead>
																					<tr>
																						<th>Phase</th>
																						<th>Land Cost</th>
																						<th>Building Site Development And Civil Works Cost</th>
																						<th>Plant And Machinery Cost</th>
																						<th>Miscellaneous Fixed Asset</th>
																						<th>Proposed Fixed Capital Investment</th>
																					</tr>
																				</thead>
																				<%
																					int i = 0;
																				%>
																				<tbody id="editButton">
																					<c:forEach var="pwInvObj" items="${pwInvList}" varStatus="counter">
																						<!-- Iterating the list using JSTL tag  -->
																						<c:if test="${not empty pwInvList}">
																							<tr>
																								<td>${pwInvObj.pwPhaseNo}</td>
																								<td>${pwInvObj.invLandCost}</td>
																								<td>${pwInvObj.invBuildingCost}</td>
																								<td>${pwInvObj.invPlantAndMachCost}</td>
																								<td>${pwInvObj.invOtherCost}</td>
																								<td>${pwInvObj.invFci}</td>
																							</tr>
																						</c:if>
																					</c:forEach>
																				</tbody>
																			</table>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Others if any <a href="./downloadInvestmentDoc?fileName=${invFileName}">(View File)</a>
																		</label>
																	</div>
																	<div class="col-sm-12" id="tabledataarea">
																		<div class="table-responsive mt-3">
																			<table id="sTable72" class="table table-stripped table-bordered ">
																				<thead>
																					<tr>
																						<th>Phase</th>
																						<th>Particulars</th>
																						<th>Proposed Investment in the project (As per DPR)</th>
																					</tr>
																				</thead>
																				<tbody>
																					<c:forEach var="invother" items="${invotherlist}" varStatus="counter">
																						<tr>
																							<td>${ invother.phaseNumber}</td>
																							<td>${ invother.particulars}</td>
																							<td>${ invother.proposedInvestmentInProject}</td>
																						</tr>
																					</c:forEach>
																				</tbody>
																			</table>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Phase-wise Installed Capacities <a href="./downloadInvestmentDoc?fileName=${invFileName}">(View File)</a>
																		</label>
																	</div>
																	<div class="col-sm-12">
																		<div class="table-responsive mt-3">
																			<table class="table table-stripped table-bordered" id="customFields3" tabindex="3">
																				<%-- <c:if test="${not empty pwInvList}"> --%>
																				<thead>
																					<tr>
																						<th>Phase</th>
																						<th>Proposed Fixed Capital Investment</th>
																						<th>Product</th>
																						<th>Capacity</th>
																						<th>Unit</th>
																						<th>Proposed Date of Commercial Production</th>
																					</tr>
																				</thead>
																				<tbody id="editButton">
																					<c:forEach var="ic" items="${pwInvList1}" varStatus="counter">
																						<!-- Iterating the list using JSTL tag  -->
																						<c:if test="${not empty pwInvList1}">
																							<%-- <c:if test="${fn:length(invic)>0}"> --%>
																							<tr>
																								<td>${ic.pwPhaseNo}</td>
																								<td>${ic.invFci}</td>
																								<td>${ic.pwProductName}</td>
																								<td>${ic.pwCapacity}</td>
																								<td>${ic.pwUnit}</td>
																								<td>${ic.pwPropProductDate}</td>
																							</tr>
																							<%-- </c:if> --%>
																						</c:if>
																					</c:forEach>
																				</tbody>
																			</table>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Means of Financing <a href="./downloadInvestmentDoc?fileName=${invFileName}">(View File)</a>
																		</label>
																	</div>
																</div>
																<%-- <div class="row mt-4">
																	<div class="col-sm-3">
																		<div class="form-group">
																			<form:input path="emfphaseNo"
																				placeholder="Enter Phase Number e.g - 1 or 2 or 3"
																				class="form-control" name="emfphaseNo"
																				onkeypress="return validateNumberField()"
																				id='emfphaseno'></form:input>
																		</div>
																	</div>
																	<div class="col-sm-3">
																		<div class="form-group">
																			<form:input path="emfphsItemName" id="emfitemname"
																				placeholder="Enter Item Name" class="form-control"
																				name="emfphsItemName"></form:input>
																		</div>
																	</div>
																	<div class="col-sm-3">
																		<div class="form-group">
																			<form:input path="emfphsInvestAmt" id="emfinvestamt"
																				onkeypress="return validateNumberField()"
																				placeholder="Enter Investment Amouts"
																				class="form-control is-numeric" name="emfphsInvestAmt"></form:input>
																			<small class="words text-info"></small>

																		</div>
																	</div>
																	<div class="col-sm-3 text-right">
																		<div class="form-group">
																			<button formaction="addProjAndMeanFinance"
																				class="btn btn-outline-success ">Add</button>
																		</div>
																	</div>
																</div> --%>
																<div class="row">
																	<div class="col-sm-12">
																		<div class="table-responsive">
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Particulars</th>
																						<th>Proposed Investment in the project (As per DPR)</th>
																					</tr>
																				</thead>
																				<tbody>
																					<c:forEach var="mof" items="${momlist}" varStatus="counter">
																						<tr>
																							<td>${ mof.mofParameter}</td>
																							<td>${ mof.mofAmount}</td>
																						</tr>
																					</c:forEach>
																				</tbody>
																			</table>
																		</div>
																		<form:textarea name="mofObserv" path="mofObserv" placeholder="Observations Comment By PICUP" value="${mofObserv}" class="form-control" rows="4"></form:textarea>
																	</div>
																</div>
															</td>
														</tr>
														<tr>
															<td width="33%">
																<label class="table-heading">Whether the company have filed LOI/IEM registration with the Department of Industrial Policy and Promotion GoI. <a href="./downloadInvestmentDoc?fileName=${regiOrLicenseFileName}">(View File)</a>
																</label>
															</td>
															<td width="33%">
																<table class="table">
																	<tr>
																		<td>
																			<input type="text" class="form-control" value="${regorlic}" readonly="readonly" name="">
																		</td>
																		<td>
																			<form:input type="text" path="iemNumber" value="${invIemNumber}" maxlength="50" class="form-control" placeholder="Enter IEM Number" name=""></form:input>
																		</td>
																	</tr>
																</table>
															</td>
															<td width="33%">
																<form:textarea name="IemRegObserv" path="IemRegObserv" id="regGoiObserv" placeholder="Observations Comment By PICUP" value="${IemRegObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">Industrial undertaking does not have more than 50% equity of any government/government undertaking</label>
															</td>
															<td>
																<form:select path="govtEquity" id="govtequity" class="form-control" disabled="true">
																	<form:option value="Yes">Yes</form:option>
																	<form:option value="No">No</form:option>
																</form:select>
															</td>
															<td>
																<form:textarea name="indusUntkObserv" path="indusUntkObserv" id="indusUndtakObserv" placeholder="Observations Comment By PICUP" value="${IndusUndrtkObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td colspan="3" class="p-4">
																<div class="row">
																	<div class="col-sm-6">
																		<label class="table-heading">Eligible Investment Period</label>
																	</div>
																</div>
																<div class="table-responsive mt-3">
																	<table class="table table-stripped table-bordered">
																		<tbody>
																			<tr class="in-case-of-small-row">
																				<td>In case of small & medium industrial undertakings, the period commencing from cutoff date falling in the effective period of these Rules upto 3 years or till the date of commencement of commercial production, whichever is earlier. Such cases will also be covered in which the cut-off date is within the period immediately preceding 3 years from the effective date, subject to the condition that commercial production in such cases commences on or after the effective date. In any case, the maximum eligible investment period shall not be more than 3 years.</td>
																				<td style="width: 33%;">
																					<input type="text" class="form-control" value="${ctypeSMALL}" readonly="readonly">
																					<%-- <c:choose>
																									<c:when test="${ctypeSMALL eq 'Yes'}">
																										<option value="${ctypeSMALL}"
																											selected="selected">${ctypeSMALL}</option>
																										<option value="No">No</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeSMALL}"
																											selected="selected">${ctypeSMALL}</option>
																									</c:otherwise>
																								</c:choose> 
																						</select>--%>
																				</td>
																				<td style="width: 33%;">
																					<textarea placeholder="Observations Comment By PICUP" class="form-control" rows="2"></textarea>
																				</td>
																			</tr>
																			<tr class="in-case-of-large-row">
																				<td>in case of large industrial undertakings, the period commencing from cutoff date falling in the effective period of these Rules upto 4 years or till the date of commencement of commercial production, whichever is earlier. Such cases will also be covered in which the cut-off date is within the period immediately preceding 4 years from the effective date, subject to the condition that commercial production in such cases commences on or after the effective date. In any case, the maximum eligible investment period shall not be more than 4 years.</td>
																				<td style="width: 33%;">
																					<input type="text" class="form-control" value="${ctypeLARGE}" readonly="readonly">
																				</td>
																				<%-- <td><select class="form-control" readonly="readonly">
																								<c:choose>
																									<c:when test="${ctypeLARGE eq 'Yes'}">
																										<option value="${ctypeLARGE}"
																											selected="selected">${ctypeLARGE}</option>
																										<option value="No">No</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeLARGE}"
																											selected="selected">${ctypeLARGE}</option>
																									</c:otherwise>
																								</c:choose>
																						</select></td> --%>
																				<td style="width: 33%;">
																					<form:textarea id="eligInvObserv" name="eligblInvPerdLargeObserv" path="eligblInvPerdLargeObserv" placeholder="Observations Comment By PICUP" value="${eligblInvPerdLargeObserv}" class="form-control" rows="2"></form:textarea>
																				</td>
																			</tr>
																			<tr class="in-case-of-mega-row">
																				<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="EIP3"
																							name="SlectItem"> <label
																							class="custom-control-label" for="EIP3"></label>
																					</div>
																				</td> -->
																				<td>In case of mega & mega plus industrial undertakings, the period commencing from the cut-off date falling in the effective period of these Rules, and upto 5 years, or the date of commencement of commercial production, whichever falls earlier. Such cases will also be covered in which the cut-off date is within the period immediately preceding 3 years from the effective date, subject to the condition that atleast 40% of eligible capital investment shall have to be undertaken after the effective date. In any case, the maximum eligible investment period shall not be more than 5 years.</td>
																				<td style="width: 33%;">
																					<input type="text" class="form-control" value="${ctypeMEGA}" readonly="readonly">
																					<%-- <td><select class="form-control" readonly="readonly">
																								<c:choose>
																									<c:when test="${ctypeMEGA eq 'Yes'}">
																										<option value="${ctypeMEGA}" selected="selected">${ctypeMEGA}</option>
																										<option value="No">No</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeMEGA}" selected="selected">${ctypeMEGA}</option>
																									</c:otherwise>
																								</c:choose>
																						</select>--%>
																				</td>
																				<td style="width: 33%;">
																					<form:textarea id="megaObserv" name="eligblInvPerdMegaObserv" path="eligblInvPerdMegaObserv" placeholder="Observations Comment By PICUP" value="${eligblInvPerdMegaObserv}" class="form-control" rows="2"></form:textarea>
																				</td>
																			</tr>
																			<tr class="in-case-of-supermega-row">
																				<td>in case of super mega industrial undertakings, the period commencing from the cutoff date falling in the effective period of these Rules, and upto 7 years, or the date of commencement of commercial production, whichever falls earlier. Such cases will also be covered in which the cut-off date is within the period immediately preceding 3 years from the effective date, subject to the condition that atleast 40% of eligible capital investment shall have to be undertaken after the effective date. In any case, the maximum eligible investment period shall not be more than 7 years.</td>
																				<td style="width: 33%;">
																					<input type="text" class="form-control" value="${ctypeSUPERMEGA}" readonly="readonly">
																					<%-- <td><select class="form-control" readonly="readonly">
																								<c:choose>
																									<c:when test="${ctypeSUPERMEGA eq 'Yes'}">
																										<option value="${ctypeSUPERMEGA}"
																											selected="selected">${ctypeSUPERMEGA}</option>
																										<option value="No">No</option>
																										<option value="${opt}" selected="selected">${opt}</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeSUPERMEGA}"
																											selected="selected">${ctypeSUPERMEGA}</option>
																											<option value="${opt}">${opt}</option>
																									</c:otherwise>
																								</c:choose>
																						</select> --%>
																				</td>
																				<td style="width: 33%;">
																					<form:textarea id="supermegaObserv" name="eligblInvPerdSupermegaObserv" path="eligblInvPerdSupermegaObserv" placeholder="Observations Comment By PICUP" value="${eligblInvPerdSupermegaObserv}" class="form-control" rows="2"></form:textarea>
																				</td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
														<tr class="in-case-of-inMMSM-row">
															<td>
																<label class="table-heading">Company has invested atleast 40% of the eligible capital investment after the effective date of policy i.e. 13/07/2017</label>
															</td>
															<td>
																<form:select path="eligcapInvest" disabled="true" class="form-control" id="eligcapinvest">
																	<form:option value="Yes">Yes</form:option>
																	<form:option value="No">No</form:option>
																</form:select>
															</td>
															<td>
																<form:textarea name="eligblCapInvObserv" path="eligblCapInvObserv" id="eligcapinvObserv" placeholder="Observations Comment By PICUP" value="${eligblCapInvObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">Whether the project is planned in phases (if yes phase wise details to be added).</label>
															</td>
															<td>
																<input type="text" class="form-control" value="${phsWsAply}" readonly="readonly">
															</td>
															<td>
																<form:textarea name="projPhasesObserv" path="projPhasesObserv" id="projphaseObserv" placeholder="Observations Comment By PICUP" value="${projPhasesObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<%-- <tr class="phase">
															<td colspan="3">
																<div class="table-responsive">
																	<table class="table table-stripped table-bordered" id="customFields3" tabindex="3">
																		<thead>
																			<tr>
																				<th>Phase</th>
																				<th>Product</th>
																				<th>Capacity</th>
																				<th>Unit</th>
																				<th>Proposed Capital Investment</th>
																				<th>Proposed Date of Commercial Production</th>
																			</tr>
																		</thead>
																		<tbody id="editButton" class="disable_a_href">
																			<c:forEach var="pwInvObj" items="${deptPwInvList}" varStatus="counter">
																				<!-- Iterating the list using JSTL tag  -->
																				<c:if test="${not empty deptPwInvList}">
																					<tr>
																						<td>${pwInvObj.pwPhaseNo}</td>
																						<td>${pwInvObj.pwProductName}</td>
																						<td>${pwInvObj.pwCapacity}</td>
																						<td>${pwInvObj.pwUnit}</td>
																						<td>${pwInvObj.pwFci}</td>
																						<td>${pwInvObj.pwPropProductDate}</td>
																					</tr>
																				</c:if>
																			</c:forEach>
																		</tbody>
																	</table>
																</div>
															</td>
														</tr> --%>
														<tr>
															<td>
																<label class="table-heading">Investment made till <form:input type="Date" path="cAStatutoryDate" class="till-date" value="${invCAStatutoryDate}" pattern="^([0-9]{2})-([0-9]{2})-([0-9]{4})$" title="enter Date in dd-mm-yyyy" placeholder="dd-mm-yyyy"></form:input> as per CA/Statutory Certificate
																</label>
															</td>
															<td>
																<form:input type="text" class="form-control is-numeric" path="cAStatutoryAmt" maxlength="12" readonly="true" onkeypress="return validateNumberField()" value="${invCAStatutoryAmount}" placeholder="Amount in Rs." name=""></form:input>
																<small class="words text-info"></small>
															</td>
															<td>
																<form:textarea name="caStatutoryObserv" path="caStatutoryObserv" id="castatObserv" placeholder="Observations Comment By PICUP" value="${caStatutoryObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">Name of Authorized Signatory. (Board Resolution copy provided.) <a href="./downloadBusinessEntityDoc?fileName=${bodDocFname}">(View File)</a>
																</label>
															</td>
															<td>
																<form:input type="text" path="authorisedSignatoryName" value="${authSignName}" class="form-control" readonly="true"></form:input>
															</td>
															<td>
																<form:textarea name="authSignatoryObserv" path="authSignatoryObserv" id="authsignObserv" placeholder="Observations Comment By PICUP" value="${authSignatoryObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">Application Format- (Whether application is as per prescribed format & signed by Authorized Signatory with Name, Designation and Official Seal.)</label>
															</td>
															<td>
																<form:select class="form-control" path="presFormat" id="presFormatId" disabled="true">
																	<form:option value="Yes">Yes</form:option>
																	<form:option value="No">No</form:option>
																</form:select>
															</td>
															<td>
																<form:textarea name="appformatObserv" path="appformatObserv" id="appformatObserv" placeholder="Observations Comment By PICUP" value="${appformatObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">Whether all supporting document of application authenticated by a Director/Partner/Officer duly authorized by the competent authority of the applicant on its behalf. </label>
															</td>
															<td>
																<form:select class="form-control" path="docAuthorized" id="docAuthorizedId" disabled="true">
																	<form:option value="Yes">Yes</form:option>
																	<form:option value="No">No</form:option>
																</form:select>
															</td>
															<td>
																<form:textarea name="supprtdocObserv" path="supprtdocObserv" id="docObserv" placeholder="Observations Comment By PICUP" value="${supprtdocObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">Online Submission of Date of Application </label>
															</td>
															<td>
																<form:input type="text" path="CreatedDate" class="form-control" value="${onlineSubmitDate}" readonly="true"></form:input>
															</td>
															<td>
																<form:textarea name="subDateAppObserv" path="subDateAppObserv" id="submitdateObserv" placeholder="Observations Comment By PICUP" value="${subDateAppObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">GSTIN Number</label>
															</td>
															<td>
																<form:input type="text" class="form-control" path="gstin" value="${GSTno}" readonly="true"></form:input>
															</td>
															<td>
																<form:textarea name="gstinObserv" path="gstinObserv" id="gstObserv" placeholder="Observations Comment By PICUP" value="${gstinObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">PAN Number</label>
															</td>
															<td>
																<form:input type="text" path="companyPanNo" class="form-control" value="${companyPANno}" readonly="true"></form:input>
															</td>
															<td>
																<form:textarea name="panObserv" path="panObserv" id="pannumObserv" placeholder="Observations Comment By PICUP" value="${panObserv}" class="form-control" rows="2"></form:textarea>
															</td>
														</tr>
														<tr>
															<td>
																<label class="table-heading">Solar/Captive Power</label>
															</td>
															<td>
																<form:input path="solarCaptivePower" type="text" name="solarCaptivePower" class="form-control is-numeric" value="${solarCaptivePower}" />
																<small class="words text-info"></small>
															</td>
															<!-- path="solarCaptivePower" -->
															<td>
																<form:textarea path="solarCaptivePowerObserv" name="solarCaptivePowerObserv" placeholder="Observations Comment By PICUP" class="form-control" rows="2" />${solarCaptivePowerObserv}</td>
															<!--  path="solarCaptivePowerObserv" -->
														</tr>
													</tbody>
													<tfoot>
														<tr hidden="">
															<td colspan="3">
																<a href="javacript:void();" class="btn btn-outline-success btn-sm">Add More</a>
															</td>
														</tr>
													</tfoot>
												</table>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<div class="mt-4">
												<div class="without-wizard-steps">
													<h4 class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Incentive Sought</h4>
												</div>
											</div>
											<hr class="mt-2 mb-5">
											<form:form modelAttribute="incentiveDeatilsData" name="incentiveDeatilsform" method="post">
												<div class="row">
													<div class="col-sm-12">
														<div class="table-responsive">
															<table class="table table-stripped table-bordered">
																<thead>
																	<tr>
																		<th>Sr. No.</th>
																		<th style="width: 25%;">Details of Incentives Sought and Concerned Department</th>
																		<th style="width: 10%;">Amount of Incentives <small>(Rs. in crores)</small></th>
																		<th>Incentive as per Rules</th>
																		<th style="width: 25%;">PICUP Officer's Remark</th>
																	</tr>
																</thead>
																<tbody>
																	<tr class="ISF_Claim_Reim-row">
																		<td>1</td>
																		<td>
																			Amount of SGST claimed for reimbursement <br> <strong>Industrial Development/ Commercial Tax</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Claim_Reim" type="text" class="ISF_Reim_SGST form-control" name="ISF_Claim_Reim" id="ISF_Claim_Reim" maxlength="12" readonly="true"></form:input>
																			<span class="text-danger d-inline-block mt-2"></span>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6" target="_blank">As per Table 3</a> of Rules related to IIEPP-2017 (The Rules), there is a provision for reimbursement of 70% of deposited GST (Net SGST) for 10 years.
																		</td>
																		<td>
																			<form:textarea path="sgstRemark" rows="4" maxlength="1000" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfSgstComment}">
																		<tr class="ISF_Claim_Reim-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Industrial Development/ Commercial Tax</p>
																				<form:textarea class="form-control" path="isfSgstComment" name="isfSgstComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Reim_SCST-row">
																		<td>1.1</td>
																		<td>
																			Additional 10% GST where 25% minimum SC/ST workers employed subject to minimum of 400 total workers in industrial undertakings located in Paschimanchal and minimum of 200 total workers in B-P-M <br> <strong>Industrial Development/ Commercial Tax</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Reim_SCST" type="text" id="Add_SCST_Textbox" class="ISF_Reim_SGST form-control" name="ISF_Reim_SCST" maxlength="12" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6" target="_blank">As per Table 3</a> of The Rules, there is a provision for 75% Stamp Duty Exemption in Paschimanchal Region. <br>Further, as per G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there is a provision for reimbursement equivalent to the paid Stamp Duty, for which company will have to apply before concerned GM, DIC.
																		</td>
																		<td>
																			<form:textarea path="scstRemark" rows="4" maxlength="1000" class="form-control" />
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfScstComment}">
																		<tr class="ISF_Reim_SCST-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Industrial Development/ Commercial Tax</p>
																				<form:textarea class="form-control" path="isfScstComment" name="isfScstComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Reim_FW-row">
																		<td>1.2</td>
																		<td>Additional 10% GST where 40% minimum female workers employed subject to minimum of 400 total workers in industrial undertakings located in Paschimanchal and minimum of 200 total workers in B-P-M</td>
																		<td>
																			<form:input path="ISF_Reim_FW" type="text" id="Add_FW_Textbox" maxlength="12" class="ISF_Reim_SGST form-control" name="ISF_Reim_FW" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6" target="_blank">As per para 3.3</a> and Table 3 of The Rules, there is a provision for incentive of reimbursement of EPF to the extent of 50% of employer's contribution to all such new Industrial undertakings providing direct employment to 100 or more unskilled workers, after three years from the date of commercial production for a period of 5 years.
																		</td>
																		<td>
																			<form:textarea path="fwRemark" rows="4" maxlength="1000" class="form-control" />
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isffwComment}">
																		<tr class="ISF_Reim_FW-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Industrial Development/ Commercial Tax</p>
																				<form:textarea class="form-control" path="isffwComment" name="isffwComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Reim_BPLW-row">
																		<td>1.3</td>
																		<td>Additional 10% GST where 25% minimum BPL workers employed subject to minimum of 400 total workers in industrial undertakings located in Paschimanchal and minimum of 200 total workers in B-P-M</td>
																		<td>
																			<form:input path="ISF_Reim_BPLW" type="text" class="ISF_Reim_SGST form-control" maxlength="12" name="ISF_Reim_BPLW" id="Add_BPLW_Textbox" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8" target="_blank">As per para 3.5.7</a> of the Rules, there is a following provision: The industries which are disallowed for input tax credit under the GST regime, will be eligible for reimbursement of that amount of GST paid on purchase of plant and machinery, building material and other capital goods during construction and commissioning period and raw materials and other inputs in respect of which input tax credit has not been allowed. While calculating the overall eligible capital investment such amount will be added to the fixed capital investment.
																		</td>
																		<td>
																			<form:textarea path="bplRemark" maxlength="1000" rows="4" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfBplComment}">
																		<tr class="ISF_Reim_BPLW-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Industrial Development/ Commercial Tax</p>
																				<form:textarea class="form-control" path="isfBplComment" name="isfBplComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Stamp_Duty_EX-row">
																	<tr>
																		<td>2</td>
																		<td>
																			Amount of Stamp Duty Exemption<br> <strong>Stamp & Registration</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Stamp_Duty_EX" type="text" class="ISF_Reim_SGST form-control" maxlength="12" name="ISF_Stamp_Duty_EXe" id="ISF_Stamp_Duty_EXe" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6" target="_blank">As per Table 3</a> of The Rules, there is a provision for <span id="sdepercent"> </span> % Stamp Duty Exemption in ${region} Region. Further, as per G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there is a provision for reimbursement equivalent to the paid Stamp Duty, for which company will have to apply before concerned GM, DIC.
																		</td>
																		<td>
																			<form:textarea path="stampDutyExemptRemark" maxlength="1000" rows="4" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<!-- sachin -->
																	<c:if test="${not empty incentiveDeatilsData.isfStampComment}">
																		<tr class="ISF_Stamp_Duty_EX-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Stamp & Registration</p>
																				<form:textarea class="form-control" path="isfStampComment" name="isfStampComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Amt_Stamp_Duty_Reim-row">
																		<td></td>
																		<td>Amount of Stamp Duty Reimbursement</td>
																		<td>
																			<form:input path="ISF_Amt_Stamp_Duty_Reim" type="text" class="ISF_Amt_Stamp_Duty_Reim form-control" maxlength="12" name="ISF_Amt_Stamp_Duty_Reim" id="Add_ISF_Amt_Stamp_Duty_Reim" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6" target="_blank">As per Table 3</a> of The Rules, there is a provision for <span id="sdrpercent"> </span> % Stamp Duty Exemption in ${region} Region. Further, as per G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there is a provision for reimbursement equivalent to the paid Stamp Duty, for which company will have to apply before concerned GM, DIC.
																		</td>
																		<td>
																			<form:textarea path="stampDutyReimRemark" maxlength="1000" rows="4" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfStampComment}">
																		<tr class="ISF_Amt_Stamp_Duty_Reim-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Stamp & Registration</p>
																				<form:textarea class="form-control" path="isfStampComment" name="isfStampComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Additonal_Stamp_Duty_EX-row">
																		<td>2.1</td>
																		<td>Additional Stamp Duty exemption @20% upto maximum of 100% in case of industrial undertakings having 75% equity owned by Divyang/SC/ ST/Females Promoters</td>
																		<td>
																			<form:input path="ISF_Additonal_Stamp_Duty_EX" type="text" class="ISF_Additonal_Stamp_Duty_EX form-control" maxlength="12" name="ISF_Additonal_Stamp_Duty_EX" id="ISF_Additonal_Stamp_Duty_EX" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8" target="_blank">As per Table 3</a> of The Rules, there is a provision for dynamic percentage Stamp Duty Exemption in Dynamic Region. Further, as per G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there is a provision for reimbursement equivalent to the paid Stamp Duty, for which company will have to apply before concerned GM, DIC.
																		</td>
																		<td>
																			<form:textarea path="divyangSCSTRemark" rows="4" maxlength="1000" class="form-control" />
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfStampscstComment}">
																		<tr class="ISF_Additonal_Stamp_Duty_EX-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Stamp & Registration</p>
																				<form:textarea class="form-control" path="isfStampscstComment" name="isfStampscstComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Epf_Reim_UW-row">
																		<td>3</td>
																		<td>
																			EPF Reimbursement (100 or more unskilled workers) <br> <strong>Labour/Industrial Development</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Epf_Reim_UW" type="text" class="ISF_Epf_Reim_UW form-control" maxlength="12" name="ISF_Amt_Stamp_Duty_Reim" id="Add_ISF_Amt_Stamp_Duty_Reim" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6" target="_blank">As per para 3.3</a> and Table 3 of The Rules, there is a provision for incentive of reimbursement of EPF to the extent of 50% of employer's contribution to all such new Industrial undertakings providing direct employment to 100 or more unskilled workers, after three years from the date of commercial production for a period of 5 years.
																		</td>
																		<td>
																			<form:textarea path="epfUnsklRemark" rows="4" class="form-control" />
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfepfComment}">
																		<tr class="ISF_Epf_Reim_UW-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Labour/Industrial Development</p>
																				<form:textarea class="form-control" path="isfepfComment" name="isfepfComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Add_Epf_Reim_SkUkW-row">
																		<td>3.1</td>
																		<td>Additional 10% EPF Reimbursement (200 direct skilled and unskilled workers)</td>
																		<td>
																			<form:input path="ISF_Add_Epf_Reim_SkUkW" type="text" class="ISF_Add_Epf_Reim_SkUkW form-control" maxlength="12" name="ISF_Add_Epf_Reim_SkUkW" id="ISF_Add_Epf_Reim_SkUkW" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6" target="_blank">As per para 3.3</a> and Table 3 of The Rules, there is a provision for incentive of reimbursement of EPF to the extent of 50% of employer's contribution to all such new Industrial undertakings providing direct employment to 100 or more unskilled workers, after three years from the date of commercial production for a period of 5 years.
																		</td>
																		<td>
																			<form:textarea path="epfSklUnsklRemark" rows="4" maxlength="1000" class="form-control" />
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfepfaddComment}">
																		<tr class="ISF_Add_Epf_Reim_SkUkW-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Labour/Industrial Development</p>
																				<form:textarea class="form-control" path="isfepfaddComment" name="isfepfaddComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
																		<td>3.2</td>
																		<td>Additional 10% EPF Reimbursement upto maximum of 70% in case of industrial undertakings having 75% equity owned by Divyang/SC/ST/Females Promoters</td>
																		<td>
																			<form:input path="ISF_Add_Epf_Reim_DIVSCSTF" type="text" class="ISF_Add_Epf_Reim_DIVSCSTF form-control" maxlength="12" name="ISF_Add_Epf_Reim_DIVSCSTF" id="ISF_Add_Epf_Reim_DIVSCSTF" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6" target="_blank">As per para 3.3</a> and Table 3 of The Rules, there is a provision for incentive of reimbursement of EPF to the extent of 50% of employer's contribution to all such new Industrial undertakings providing direct employment to 100 or more unskilled workers, after three years from the date of commercial production for a period of 5 years.
																		</td>
																		<td>
																			<form:textarea path="epfDvngSCSTRemark" rows="4" maxlength="1000" class="form-control" />
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfepfscComment}">
																		<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Labour/Industrial Development</p>
																				<form:textarea class="form-control" path="isfepfscComment" name="isfepfscComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Cis-row">
																		<td>4</td>
																		<td>
																			Capital Interest Subsidy <br> <strong>Industrial Development</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Cis" type="text" class="ISF_Cis form-control" maxlength="12" name="ISF_Cis" id="ISF_Cis" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=7" target="_blank">As per para 3.5.1</a> of The Rules, there is a provision for capital interest subsidy @ 5% p.a. or actual interest paid whichever is less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for procurement of plant & machinery, subject to an annual ceiling of Rs. 50 lacs.
																		</td>
																		<td>
																			<form:textarea path="capIntSubRemark" maxlength="1000" rows="4" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfcapisComment}">
																		<tr class="ISF_Cis-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Industrial Development</p>
																				<form:textarea class="form-control" path="isfcapisComment" name="isfcapisComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_ACI_Subsidy_Indus-row">
																		<td>4.1</td>
																		<td>Additional Capital Interest Subsidy@2.5% upto maximum of 7.5% in case of industrial undertakings having 75% equity owned by Divyang/SC/ST/Females Promoters</td>
																		<td>
																			<form:input path="ISF_ACI_Subsidy_Indus" type="text" class="ISF_ACI_Subsidy_Indus form-control" maxlength="12" name="ISF_ACI_Subsidy_Indus" id="ISF_ACI_Subsidy_Indus" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=7" target="_blank">As per para 3.5.1</a> of The Rules, there is a provision for capital interest subsidy @ 5% p.a. or actual interest paid whichever is less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for procurement of plant & machinery, subject to an annual ceiling of Rs. 50 lacs.
																		</td>
																		<td>
																			<form:textarea path="aciSubsidyRemark" rows="4" maxlength="1000" class="form-control" />
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfcapisaComment}">
																		<tr class="ISF_ACI_Subsidy_Indus-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Industrial Development</p>
																				<form:textarea class="form-control" path="isfcapisaComment" name="isfcapisaComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Infra_Int_Subsidy-row">
																		<td>5</td>
																		<td>
																			Infrastructure Interest Subsidy <br> <strong>Industrial Development</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Infra_Int_Subsidy" type="text" class="ISF_Infra_Int_Subsidy form-control" maxlength="12" name="ISF_Infra_Int_Subsidy" id="ISF_Infra_Int_Subsidy" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=7" target="_blank">As per para 3.5.2</a> of the Rules, there is a provision for incentive of infrastructure interest subsidy @ 5% p.a. or actual interest paid whichever is less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for development of infrastructural amenities (as defined in para 2.17) subject to an overall ceiling of Rs. 1 Crore.
																		</td>
																		<td>
																			<form:textarea path="infraIntSubRemark" maxlength="1000" rows="4" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfinfComment}">
																		<tr class="ISF_Infra_Int_Subsidy-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Industrial Development</p>
																				<form:textarea class="form-control" path="isfinfComment" name="isfinfComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
																		<td>5.1</td>
																		<td>Additional Infrastructure Interest Subsidy @2.5% upto maximum of 7.5% in case of industrial undertakings having 75% equity owned by Divyang/SC/ST/Females Promoters</td>
																		<td>
																			<form:input path="ISF_AII_Subsidy_DIVSCSTF" type="text" class="ISF_AII_Subsidy_DIVSCSTF form-control" maxlength="12" name="ISF_AII_Subsidy_DIVSCSTF" id="ISF_AII_Subsidy_DIVSCSTF" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=7" target="_blank">As per para 3.5.2</a> of the Rules, there is a provision for incentive of infrastructure interest subsidy @ 5% p.a. or actual interest paid whichever is less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for development of infrastructural amenities (as defined in para 2.17) subject to an overall ceiling of Rs. 1 Crore.
																		</td>
																		<td>
																			<form:textarea path="aiiSubsidyRemark" rows="4" maxlength="1000" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfinfaComment}">
																		<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Industrial Development</p>
																				<form:textarea class="form-control" path="isfinfaComment" name="isfinfaComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Loan_Subsidy-row">
																		<td>6</td>
																		<td>
																			Interest Subsidy on loans for industrial research, quality improvement, etc. <br> <strong>Industrial Development</strong>
																		</td>
																		<td>
																			<form:input path="ISF_Loan_Subsidy" type="text" class="ISF_Loan_Subsidy form-control" maxlength="12" name="ISF_Loan_Subsidy" id="ISF_Loan_Subsidy" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=7" target="_blank">As per para 3.5.3</a> of The Rules, there is a provision for Interest subsidy on loans for industrial research @ 5% or actual interest paid whichever is less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for industrial research, quality improvement and development of products by incurring expenditure on procurement of plant, machinery & equipment for setting up testing labs, quality certification labs and tool rooms, subject to an overall ceiling of Rs. 1 Crore
																		</td>
																		<td>
																			<form:textarea path="loanIntSubRemark" maxlength="1000" rows="4" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfloanComment}">
																		<tr class="ISF_Loan_Subsidy-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Industrial Development</p>
																				<form:textarea class="form-control" path="isfloanComment" name="isfloanComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Tax_Credit_Reim-row">
																		<td>7</td>
																		<td>
																			Reimbursement of Disallowed Input Tax Credit on plant, building materials, and other capital goods. <br> <strong>Industrial Development</strong></br>
																		</td>
																		<td>
																			<form:input path="ISF_Tax_Credit_Reim" type="text" class="ISF_Tax_Credit_Reim form-control" maxlength="12" name="ISF_Tax_Credit_Reim" id="ISF_Tax_Credit_Reim" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8" target="_blank">As per para 3.5.7</a> of the Rules, it is provided - The industries which are disallowed for input tax credit under the GST regime, will be eligible for reimbursement of that amount of GST paid on purchase of plant and machinery, building material and other capital goods during construction and commissioning period and raw materials and other inputs in respect of which input tax credit has not been allowed. While calculating the overall eligible capital investment such amount will be added to the fixed capital investment.
																		</td>
																		<td>
																			<form:textarea path="inputTaxRemark" rows="4" maxlength="1000" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfdisComment}">
																		<tr class="ISF_Tax_Credit_Reim-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Industrial Development</p>
																				<form:textarea class="form-control" path="isfdisComment" name="isfdisComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_EX_E_Duty-row">
																		<td>8</td>
																		<td>
																			Exemption from Electricity Duty from captive power for self-use <br> <strong>Energy/UPPCL/ Electricity Safety</strong></br>
																		</td>
																		<td>
																			<form:input path="ISF_EX_E_Duty" type="text" class="ISF_EX_E_Duty form-control" maxlength="12" name="ISF_EX_E_Duty" id="ISF_EX_E_Duty" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8" target="_blank">As per para 3.5.5</a> of The Rules, there is a provision for Exemption from Electricity Duty for 10 years to all new industrial undertakings producing electricity from captive power plants for self-use.
																		</td>
																		<td>
																			<form:textarea path="elecDutyCapRemark" maxlength="1000" rows="4" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfelepodownComment}">
																		<tr class="ISF_EX_E_Duty-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : UPPCL</p>
																				<form:textarea class="form-control" path="isfelepodownComment" name="isfelepodownComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_EX_E_Duty_PC-row">
																		<td>9</td>
																		<td>
																			Exemption from Electricity duty on power drawn from power companies <br> <strong>Energy/UPPCL/ Electricity Safety</strong></br>
																		</td>
																		<td>
																			<form:input path="ISF_EX_E_Duty_PC" type="text" class="ISF_EX_E_Duty_PC form-control" maxlength="12" name="ISF_EX_E_Duty_PC" id="ISF_EX_E_Duty_PC" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8" target="_blank">As per para 3.5.4</a> of The Rules, there is a provision for Exemption from Electricity Duty to all new industrial undertakings set up in the State for 10 years.
																		</td>
																		<td>
																			<form:textarea path="elecDutyDrawnRemark" maxlength="1000" rows="4" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfElecdutyComment}">
																		<tr class="ISF_EX_E_Duty_PC-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : UPPCL</p>
																				<form:textarea class="form-control" path="isfElecdutyComment" name="isfElecdutyComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_EX_Mandee_Fee-row">
																		<td>10</td>
																		<td>
																			Exemption from Mandi Fee <br> <strong>Agriculture Marketing & Agriculture Foreign Trade/MandiParishad</strong></br>
																		</td>
																		<td>
																			<form:input path="ISF_EX_Mandee_Fee" type="text" class="ISF_EX_Mandee_Fee form-control" maxlength="12" name="ISF_EX_Mandee_Fee" id="ISF_EX_Mandee_Fee" readonly="true"></form:input>
																		</td>
																		<td>
																			<a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8" target="_blank">As per para 3.5.6</a> of The Rules, There is provision for exemption from Mandi Fee for all new food-processing undertakings on purchase of raw material for 5 years.
																		</td>
																		<td>
																			<form:textarea path="mandiFeeRemark" rows="4" maxlength="1000" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfMandiComment}">
																		<tr class="ISF_EX_Mandee_Fee-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Agriculture Marketing & Agriculture Foreign Trade/MandiParishad</p>
																				<form:textarea class="form-control" path="isfMandiComment" name="isfMandiComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr class="ISF_Indus_Payroll_Asst-row">
																		<td>11</td>
																		<td>Industrial units providing employment to differently abled workers will be provided payroll assistance of Rs. 500 per month for each such worker.</td>
																		<td>
																			<form:input path="ISF_Indus_Payroll_Asst" type="text" class="ISF_Indus_Payroll_Asst form-control" maxlength="12" name="ISF_Amt_Stamp_Duty_Reim" id="Add_ISF_Amt_Stamp_Duty_Reim" readonly="true"></form:input>
																		</td>
																		<td>No such provision in The Rules.</td>
																		<td>
																			<form:textarea path="diffAbleWorkRemark" maxlength="1000" rows="4" class="form-control"></form:textarea>
																		</td>
																	</tr>
																	<c:if test="${not empty incentiveDeatilsData.isfdifferabilComment}">
																		<tr class="ISF_Indus_Payroll_Asst-row">
																			<td colspan="5">
																				<p class="text-info">Comment From : Labour/Industrial Development</p>
																				<form:textarea class="form-control" path="isfdifferabilComment" name="isfdifferabilComment" readonly="true" rows="3" placeholder="Comments"></form:textarea>
																			</td>
																		</tr>
																	</c:if>
																	<tr>
																		<td colspan="2" align="Center">
																			<strong>Total</strong>
																		</td>
																		<td>
																			<strong>${total}</strong>
																		</td>
																		<td colspan="4">
																			All incentives in the form of reimbursement, subsidies, exemptions etc., will be subject to a maximum of <span id="percent"> </span> % of fixed capital investment made in ${region} area.
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
												<c:if test="${not empty incentiveDeatilsData.othAddRequest1}">
													<div class="row">
														<div class="col-sm-12">
															<div class="form-group">
																<label>Additional Request</label>
																<form:input path="othAddRequest1" type="text" class="form-control" placeholder="" maxlength="250" name="othAddRequest1" readonly="true"></form:input>
															</div>
														</div>
													</div>
												</c:if>
											</form:form>
											<hr class="mt-4 mb-4">
											<div class="row">
												<div class="col-sm-12">
													<div class="custom-control custom-checkbox mb-1">
														<input type="checkbox" class="custom-control-input" id="verified" name="Capital-Interest-Subsidy"> <label class="custom-control-label" for="verified" id="verifiedChecked">I have read and verified the entire form carefully</label>
													</div>
												</div>
											</div>
											<hr class="mt-4 mb-4">
											<!-- vinay  -->
											<form:form modelAttribute="applicationAgendaNote" class="mt-4" name="applicationAgendaNote" method="post">
												<div class="row mt-4">
													<div class="col-sm-12 text-center">
														<button type="button" id="button1" onclick="enableButton2()" class="btn btn-outline-secondary evaluate-btn btn-sm mb-3">Evaluation Completed</button>
														<!--  <button type="button" disabled="disabled" id="button5"
										onclick="return sendAcknowledgeInvestor()"
										class="btn btn-outline-info btn-sm mb-3">Send
										Acknowledgement to Investor</button>-->
														<a href="#RaiseQuery" class="btn btn-outline-danger btn-sm mb-3" data-toggle="modal">Raise Query</a>
														<button type="submit" id="button2" class="btn btn-outline-info btn-sm mb-3 disable-btn" formaction="applicationAgendaNote" onclick="return includeAgendaNote()">Include Application in Agenda Note</button>
														<button data-target="#RejectApplication" type="button" class="btn btn-outline-danger btn-sm mb-3 disable-btn" id="reject" data-toggle="modal">Reject</button>
													</div>
												</div>
											</form:form>
											<!-- vinay  -->
											<hr class="mt-2">
											<div class="row">
												<div class="col-sm-5">
													<a href="./viewApplicationDetails?applicantId=<%=session.getAttribute("appId")%>" class="common-default-btn mt-3">Back</a>
												</div>
												<div class="col-sm-7 text-right">
													<a href="javacript:void(0);" onclick="myEditInput()" class="common-btn mt-3"><i class="fa fa-edit"></i>Edit</a>
													<form:button onclick="return Saveconfirm()" class="common-btn mt-3">Save</form:button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<footer class="dashboard-footer">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<div class="social-icons">
						<ul>
							<li>
								<a href="#"><i class="fa fa-facebook-f"></i></a>
							</li>
							<li>
								<a href="#"><i class="fa fa-twitter"></i></a>
							</li>
							<li>
								<a href="#"><i class="fa fa-youtube"></i></a>
							</li>
						</ul>
					</div>
				</div>
				<div class="col-sm-12">
					<div class="footer-menu">
						<ul>
							<li>
								<a href="index.html" target="_blank">Home</a>
							</li>
							<li>
								<a href="http://udyogbandhu.com/topics.aspx?mid=About%20us" target="_blank">About Us</a>
							</li>
							<li>
								<a href="http://udyogbandhu.com/topics.aspx?mid=Disclaimer" target="_blank">Disclaimer</a>
							</li>
							<li>
								<a href="http://udyogbandhu.com/topics.aspx?mid=General%20Terms%20And%20Conditions" target="_blank">General Terms And Conditions</a>
							</li>
							<li>
								<a href="http://udyogbandhu.com/topics.aspx?mid=Privacy%20Policy" target="_blank">Privacy Policy</a>
							</li>
							<li>
								<a href="http://udyogbandhu.com/topics.aspx?mid=Refund%20Policy" target="_blank">Refund Policy</a>
							</li>
							<li>
								<a href="http://udyogbandhu.com/topics.aspx?mid=Delivery%20Policy" target="_blank">Delivery Policy</a>
							</li>
							<li>
								<a href="http://udyogbandhu.com/topics.aspx?mid=Contact%20Us" target="_blank">Contact Us</a>
							</li>
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
								<p>Â© 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit</p>
								<p>Designed and Developed by National Informatics Centre ( NIC )</p>
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
	<div class="modal fade" id="RaiseQuery">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Raise Query</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<form:form action="saveRaiseQuery" class="isf-form" method="POST" modelAttribute="raiseQuery" enctype="multipart/form-data">
					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<form:label path="rqClarifySought">Clarification Sought</form:label>
									<form:textarea path="rqClarifySought" class="form-control"></form:textarea>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<form:label path="rqMissdocdtl">Details of Missing Documents <small>(If any)</small>
									</form:label>
									<form:textarea path="rqMissdocdtl" class="form-control"></form:textarea>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label>Upload Related Document</label>
									<div class="custom-file">
										<input type="file" name="rqFilename" class="custom-file-input"
											id="UploadDocumentForQuery"  multiple> <label
											class="custom-file-label" for="UploadDocumentForQuery"
											id="UploadDocumentForQuery1">Choose file</label>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<form:button type="submit" id="button1" onclick="return RaiseQuery()" class="common-btn mt-3">Submit Query</form:button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<!-- The Modal -->
	<div class="modal fade" id="RaiseQuery">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Raise Query</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- Modal body -->
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label>Clarification Sought</label>
								<textarea class="form-control"></textarea>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label>Details of Missing Documents <small>(If any)</small></label>
								<textarea class="form-control"></textarea>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label>Upload Related Document</label>
								<div class="custom-file">
									<input type="file" class="custom-file-input" id="UploadDocumentForQuery"> <label class="custom-file-label" for="UploadDocumentForQuery">Choose file</label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- Modal footer -->
				<div class="modal-footer">
					<a href="#QueryReply" class="common-btn mt-0" data-toggle="modal" data-dismiss="modal">Submit Query</a>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="EvaluationAuditTrail">
		<div class="modal-dialog modal-xl">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Evaluation Audit Trail</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- Modal body -->
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12">
							<div class="table-responsive">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>S.No</th>
											<th>Username</th>
											<th>Field Name</th>
											<th>Old Details</th>
											<th>New Details</th>
											<th>Modified Date & Time</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="list" items="${EvaluationAuditTrailList}" varStatus="counter">
											<tr>
												<td class="text-center">${counter.index+1}</td>
												<td>${list.modifyedBy}</td>
												<td>${list.fieldsName}</td>
												<td>${list.oldDetails}</td>
												<td>${list.newDetails}</td>
												<td>
													<fmt:formatDate type="both" dateStyle="short" timeStyle="short" pattern="dd/MM/yyyy  HH:mm:ss a" value="${list.modifyedDate}"></fmt:formatDate>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="modal fade" id="RejectApplication">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Reject Application</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<form:form action="saveRejectApplication" class="isf-form" method="POST">
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label>Reason</label>
										<textarea class="form-control" rows="5" name="rejectValue"></textarea>
									</div>
								</div>
							</div>
						</div>
						<!-- Modal footer -->
						<div class="modal-footer">
							<button type="submit" onclick="return RejectApplication()" class="common-btn" value="Submit">Submit</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<script>
        $(document).ready(function() {
            $("[data-toggle=offcanvas]").click(function() {
                $(".row-offcanvas").toggleClass("active");
            });
        });
    </script>
	<script>
        //Get the button
        var mybutton = document.getElementById("GoToTopBtn");

        // When the user scrolls down 20px from the top of the document, show the button
        window.onscroll = function() {
            scrollFunction()
        };

        function scrollFunction() {
            if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
                mybutton.style.display = "block";
            } else {
                mybutton.style.display = "none";
            }
        }

        // When the user clicks on the button, scroll to the top of the document
        function topFunction() {
            document.body.scrollTop = 0;
            document.documentElement.scrollTop = 0;
        }
    </script>
</body>
</html>