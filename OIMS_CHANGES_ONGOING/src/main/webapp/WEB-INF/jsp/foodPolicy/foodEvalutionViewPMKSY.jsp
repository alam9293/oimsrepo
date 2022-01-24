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
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Evaluation View</title>
    
        <!-- Fonts -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Bootstrap & CSS -->
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="css/style.css">
    <style>
.tooltip {
  position: relative;
  display: inline-block;
  border-bottom: 1px dotted black;
}

.tooltip .tooltiptext {
  visibility: hidden;
  width: 120px;
  background-color: black;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 0;

  /* Position the tooltip */
  position: absolute;
  z-index: 1;
}

.tooltip:hover .tooltiptext {
  visibility: visible;
}


</style>
    
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="js/datepicker.js"></script>
    <script src="js/custom.js"></script>
     
   <script>
        function NewTab() {
            var url ="./downloadFoodDocument?fileName=${foodDocument.detailedprojectreportasperannexurea16}";
            window.open(url , "_blank");
        }

       
        function isNumberKey(evt)
        {
           var charCode = (evt.which) ? evt.which : evt.keyCode;
           if (charCode != 46 && charCode > 31 
             && (charCode < 48 || charCode > 57))
              return false;

           return true;
        }
        function onlyAlphabets(e, t) {
            try {
                if (window.event) {
                    var charCode = window.event.keyCode;
                }
                else if (e) {
                    var charCode = e.which;
                }
                else { return true; }
                if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123))
                    return true;
                else
                    return false;
            }
            catch (err) {
                alert(err.Description);
            }
        }
        
    </script>
    <script type="text/javascript">

    $(document).ready(function() {
				$("#ammountmofpipmksy").on('change', function() {

					var ammountmofpipmksy = document.getElementById('ammountmofpipmksy').value;
					if(ammountmofpipmksy>0 || !ammountmofpipmksy =='' ){
						var eligiaddics1 = ammountmofpipmksy * 0.1;
						var eligiaddics =parseInt(eligiaddics1);
						
						if(eligiaddics>=5000000){
							document.getElementById("eligiaddics").value = eligiaddics;
							document.getElementById("maxgrant").value = "5000000";
							}
						else
							{
							document.getElementById("eligiaddics").value = eligiaddics;
							document.getElementById("maxgrant").value = eligiaddics;
							}
							
						}
					else {
						document.getElementById("maxgrant").value = '';
						document.getElementById("eligiaddics").value = '';
						}

					var numberRegex = /^[0-9]+$/; 
			        

			        if (!numberRegex.test(ammountmofpipmksy)) {
			            alert("Please Enter only number");
			            document.getElementById("ammountmofpipmksy").focus();
			            $('#ammountmofpipmksy').val("");
			            $('#maxgrant').val("");
			            return false;
			        } else {
			            return true;
			        }
					});
				
			});

    </script>
    <script type="text/javascript">

    $(document).on("keyup", ".eligibleCostInLacsId", function() {
	    var sum = 0;
	    $(".eligibleCostInLacsId").each(function(){
	        sum += +$(this).val();
	    });
	    $(".totaleEligibleCostInLacs").val(sum);
	});

	$(document).on("keyup", ".eligibleTechCivilWorkEligibleCostId", function() {
	    var sum = 0;
	    $(".eligibleTechCivilWorkEligibleCostId").each(function(){
	        sum += +$(this).val();
	    });
	    $(".ttlEligibleTechCivilWorkEligibleCostId").val(sum);
	});

	function COPExistingTotal() {
		var sum = 0;
		$(".COPExisting").each(function() {
			sum += +$(this).val();
		});
		$(".COPExistingTotal").val(sum);
	}

	$(document).ready(COPExistingTotal);
	$(document).on("keyup", COPExistingTotal);
	$(document).on("change", COPExistingTotal);

	function COPProposedTotal() {
		var sum = 0;
		$(".COPProposed").each(function() {
			sum += +$(this).val();
		});
		$(".COPProposedTotal").val(sum);
	}

	$(document).ready(COPProposedTotal);
	$(document).on("keyup", COPProposedTotal);
	$(document).on("change", COPProposedTotal);

	function COPAppraisedTotal() {
		var sum = 0;
		$(".COPAppraised").each(function() {
			sum += +$(this).val();
		});
		$(".COPAppraisedTotal").val(sum);
	}

	$(document).ready(COPAppraisedTotal);
	$(document).on("keyup", COPAppraisedTotal);
	$(document).on("change", COPAppraisedTotal);

	function MOFExistingTotal() {
		var sum = 0;
		$(".MOFExisting").each(function() {
			sum += +$(this).val();
		});
		$(".MOFExistingTotal").val(sum);
	}

	$(document).ready(MOFExistingTotal);
	$(document).on("keyup", MOFExistingTotal);
	$(document).on("change", MOFExistingTotal);

	function MOFProposedTotal() {
		var sum = 0;
		$(".MOFProposed").each(function() {
			sum += +$(this).val();
		});
		$(".MOFProposedTotal").val(sum);
	}

	$(document).ready(MOFProposedTotal);
	$(document).on("keyup", MOFProposedTotal);
	$(document).on("change", MOFProposedTotal);

	function MOFAppraisedTotal() {
		var sum = 0;
		$(".MOFAppraised").each(function() {
			sum += +$(this).val();
		});
		$(".MOFAppraisedTotal").val(sum);
	}

	$(document).ready(MOFAppraisedTotal);
	$(document).on("keyup", MOFAppraisedTotal);
	$(document).on("change", MOFAppraisedTotal);

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
                    <button type="button" class="hidden-md-up navbar-toggler" data-toggle="offcanvas" title="Toggle responsive left sidebar"><span class="navbar-toggler-icon"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
                    <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="./fooduserLogout"><i class="fa fa-power-off"></i> Logout</a>
                        </li>
                        <li class="nav-item">
              <a class="nav-link active" href="#"><i class="fa fa-user"></i>${userName}</a>
            </li>

             <li class="nav-item dropdown dropleft notification-dropdown">
              <a class="nav-link notification" data-toggle="dropdown" href="#"><i class="fa fa-bell"></i><span class="animate__animated animate__bounceIn">5</span></a>
              <div class="dropdown-menu">
                <a class="dropdown-item" href="notification.html">You have 3 new recommendations please check.</a>
                <a class="dropdown-item" href="notification.html">You have 4 new notes please check.</a>
                <a class="dropdown-item" href="notification.html">You have 5 new comments please check.</a>
                <a class="dropdown-item" href="notification.html">You have 3 new recommendations please check.</a>
                <a class="dropdown-item" href="notification.html">You have 4 new notes please check.</a>
              </div>
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
                        <li class="nav-item"><a class="nav-link" href="./foodPolicyDashboard"><i class="fa fa-tachometer"></i> Dashboard</a></li>
                      <li class="nav-item"><a class="nav-link active" href="./foodviewEvaluate"><i class="fa fa-eye"></i> View and Evaluate Applications</a></li>
                    </ul>
                </div>
                <!--/col-->
                <div class="col-md-9 col-lg-10 mt-4 main mb-4">
                <h4 class="card-title mb-4 mt-4 text-center">Capital Subsidy for settingup new units/Technology Up gradation / Modernisation of Food Processing Industries. Sanctioned under Pradhan Mantri Kishan Sampada Yojna.</h4>
                    <div class="card card-block p-3">
                        <div class="row">
                            <div class="col-sm-5">
                                <a href="./foodviewApplicatPMKSY?unit_Id=${businessEntityDetailsfood.unit_id}" class="common-default-btn mt-3">Back</a>
                            </div>
                            
                            <div class="col-sm-7 text-right">
                                <a href="#EvaluationAuditTrail" class="text-info mr-3" data-toggle="modal">Evaluation Audit Trail</a>
                                <a href="javacript:void(0);" class="common-btn mt-3"><i class="fa fa-edit"></i> Edit</a>
                            </div>
                        </div>
                              <form:form modelAttribute="foodViewEvaluatePMKSY" method="post"
							action = "savefoodEvaluatePMKSY" class="mt-4" enctype="multipart/form-data">
							
                        <div class="row mt-5">
                            <div class="col-sm-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Name of Organization</th>
                                                <th>Registered Address</th>
                                                <th>Project Name</th>
                                                <th>Proposed Site Address</th>
                                                <th>Capacity </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td><input id="nameOfOrganizationId"
													name="nameOfOrganization" value="${businessEntityDetailsfood.nameofcompany}"
													class="form-control" readonly="true"></input></td>
													
                                                <td><input id="registeredAddressId"
													name="registeredAddress"value="${businessEntityDetailsfood.addressofcompany}"
													class="form-control" readonly="true"></input></td>
													
                                                <td><input id="projectNameid"
													name="projectName" value="${projectandproposedemploymentdetails.nameoftheproject}"
													class="form-control" readonly="true"></input></td>
													
                                                <td><input id="proposedSiteAddressid"
													name="proposedSiteAddress"value="${projectandproposedemploymentdetails.locationareaoftheproject}"
													class="form-control" readonly="true"></input></td>
													
                                                <td><input id="capacityid"
													name="capacity" value="${capacityoftheplantorunit.totalafterexpansioncapacity} ${capacityoftheplantorunit.unittypecapacity}"
													class="form-control" readonly="true"></input></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
      
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4 ">Project Profile</h4>
                                    </div>
                                </div>
                                <hr class="mt-2 mb-5">
      <div class="row">
          <div class="col-sm-12">
              <div class="table-responsive">
                  <table class="table table-bordered">
                      <thead>
                          <tr>
                              <th>Particulars</th>
                              <th>Details</th>
                              <th>Observations By Nodal Officer</th>
                          </tr>
                      </thead>
                      <tbody>
                               <tr>
                              <td style="width: 33%;"><label class="table-heading">Application ID</label></td>
                              <td style="width: 33%;">
                              <form:input path="projectProfileAppId" id="ProjectProfileAppId" name="projectProfileAppId" value="" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="projectProfileAppIdObserv" id="projectProfileAppIdObservId" name="projectProfileAppIdObserv"
									class="form-control border-info" maxlenght="10000" rows="2"></form:textarea>
                              </td>
                                 
                          </tr>
                          <tr>
                              <td><label class="table-heading">Proposal submission date</label></td>
                              
                              <td>
                              <form:input path="proposalSubmissionDate" id="proposalSubmissionDateId" name="proposalSubmissionDate" value="" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="proposalSubmissionDateObserv" id="proposalSubmissionDateObservId" name="proposalSubmissionDateObserv"
									class="form-control border-info" maxlenght="1" rows="2"></form:textarea>
                              </td>
                              
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Name of Promoter</label></td>
                              <td style="width: 33%;">
                              
                              <form:input path="nameOfPromoter" id="nameOfPromoterId" name="nameOfPromoter" value="${businessEntityDetailsfood.nameofpromoter}" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="nameOfPromoterObserv" id="nameOfPromoterObservId" name="nameOfPromoterObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                          </tr>  
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Type of Organization</label></td>
                              <td style="width: 33%;">
                              
                               <form:input path="typeOfOrganization" id="typeOfOrganizationId" name="typeOfOrganization" value="${businessEntityDetailsfood.typeoforganization}" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="typeOfOrganizationObserv" id="typeOfOrganizationObservId" name="typeOfOrganizationObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                            
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Date of incorporation</label></td>
                              <td style="width: 33%;">
                              
                              <form:input path="dateOfIncorporation" id="dateOfIncorporationId" name="dateOfIncorporation" value="${businessEntityDetailsfood.dateofincorporation}" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="dateOfIncorporationObserv" id="dateOfIncorporationObservId" name="dateOfIncorporationObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Availability of land and Building</label></td>
                              <td style="width: 33%;">
                              
                              <form:input path="availOfLandAndBuilding" id="availOfLandAndBuildingId" name="availOfLandAndBuilding" 
                              value="${projectandproposedemploymentdetails.district} ${projectandproposedemploymentdetails.division} ${projectandproposedemploymentdetails.locationareaoftheproject}"  type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="availOfLandAndBuildingObserv" id="availOfLandAndBuildingObservId" name="availOfLandAndBuildingObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                               
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">PAN No.</label></td>
                              <td style="width: 33%;">
                              
                              <form:input path="panNo" id="panNoId" name="panNo" value="${businessEntityDetailsfood.pancardoffirmproprietor}" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="panNoObserv" id="panNoId" name="panNoObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">GST No.</label></td>
                              <td style="width: 33%;">
                              
                               <form:input path="gstNo" id="gstNoId" name="gstNo" value="${businessEntityDetailsfood.gstin}" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="gstNoObserv" id="gstNoObservId" name="gstNoObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                                                                 
                          </tr>  
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">SSI/IEM/Udyam/Udyog Aadhar No.</label></td>
                              <td style="width: 33%;">
                              
                              <form:input path="udyogAadharNo" id="udyogAadharNoId" name="udyogAadharNo" value="" type="text"
                               readonly="false" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="udyogAadharNoObservation" id="udyogAadharNoObservationId" name="udyogAadharNoObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                                   
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Location the firm/unit</label></td>
                             <td style="width: 33%;">
                             
                             <form:input path="udyogAadharNo" id="udyogAadharNoId" name="udyogAadharNo" value="${projectandproposedemploymentdetails.locationareaoftheproject}" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="udyogAadharNoObservation" id="udyogAadharNoObservationId" name="udyogAadharNoObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Sector</label></td>
                              <td style="width: 33%;">
                                  <form:input path="sector" id="sectorId" name="sector" value="${projectandproposedemploymentdetails.sector}" type="text" readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="sectorObservation" id="sectorObservationId" name="sectorObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Sub-Sector</label></td>
                              <td style="width: 33%;">
                                 <form:input path="subSector" id="subSectorId" name="subSector" value="${projectandproposedemploymentdetails.selectsubsector}" type="text" readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="subSectorObservation" id="subSectorObservationId" name="subSectorObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                            <td colspan="3"><label class="table-heading">Proposed Products by Products</label></td>
                          </tr> 
                          <tr>
                            <td colspan="3">
                                <table style="width: 100%">
                                <table
																								class="table table-stripped table-bordered directors-table"
																								id="customFields">
																								<thead>
																									<tr>
																										<th>Products</th>
																										<th>By Products</th>

																									</tr>
																								</thead>
																								<tbody>
																									<c:forEach var="list" items="${productdetails}"
																				varStatus="counter">
																				<tr>
																					<td><input type="text" class="form-control"
																						id="" value="${list.products}" disabled=""
																						name=""></td>
																					<td><input type="text" class="form-control"
																						id="" value="${list.byproducts}" disabled="" name=""></td>
																				</tr>
																			</c:forEach>

																								</tbody>
																								</table>
																								<div class="col-md-12 mt-4">
                                <div class="form-group">
                                    <h4 class="card-title mb-4 mt-4 "> </h4>
                                    <form:textarea path="productbyproduct" id="productbyproduct" name="productbyproduct" placeholder ="Nodal Officer's Remark"
									class="form-control border-info" rows="2"></form:textarea>
                                </div>
                            </div>
							<tr>
                              <td style="width: 33%;"><label class="table-heading">Proposed Capacity(For New Unit)</label></td>
                              <td style="width: 33%;">
                                  <form:input path="proposedCapacityBuyProducts" id="proposedCapacityBuyProductsId" name="proposedCapacityBuyProducts" value="" type="text" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="proposedCapacityBuyProductsObserv" id="proposedCapacityBuyProductsObservId" name="proposedCapacityBuyProductsObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Existing Capacity (in case of Expansion/Modernization)</label></td>
                              <td style="width: 33%;">
                                  <form:input path="existingCapacityBuyProducts" id="existingCapacityBuyProductsId" name="existingCapacityBuyProducts" value="${capacityoftheplantorunit.totalafterexpansioncapacity}" type="text" readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="existingCapacityBuyProductsObserv" id="existingCapacityBuyProductsObservId" name="existingCapacityBuyProductsObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Capacity Utilization (%)</label></td>
                              <td style="width: 33%;">
                                  <form:input path="capacityUtilizationBuyProducts" id="capacityUtilizationBuyProductsId" name="capacityUtilizationBuyProducts" value="" type="text" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="capacityUtilizationBuyProductsObserv" id="capacityUtilizationBuyProductsObservId" name="capacityUtilizationBuyProductsObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <tr>
                         
                              <td style="width: 33%;"><label class="table-heading">Name of Appraising Bank</label></td>
                              
                              <c:forEach var="bdList" items="${bankTermLoanDetails}" varStatus="counter">
                              <td style="width: 33%;">
                               
                                  <form:input path="nameOfAppraisingBankBuyProducts" id="nameOfAppraisingBankBuyProductsId" 
                                  name="nameOfAppraisingBankBuyProducts" value="${bdList.detailsnameofthebankfi}" type="text" readonly="true" class="form-control"/>
                                                            </td>
                              <td>
                                  <form:textarea path="nameOfAppraisingBankBuyProductsObserv" id="nameOfAppraisingBankBuyProductsObservId" 
                                  name="nameOfAppraisingBankBuyProductsObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Amount of Term loan Sanctioned</label></td>
                              <td style="width: 33%;">
                                 <form:input path="amtOfTermloanSanctionedBuyProducts" id="amtOfTermloanSanctionedBuyProductsId" 
                                 name="amtOfTermloanSanctionedBuyProducts" value="${bdList.detailstotalamountoftermloansanctioned}" type="text" readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="amtOfTermloanSanctionedBuyProductsObserv" id="amtOfTermloanSanctionedBuyProductsObservId" 
                                  name="amtOfTermloanSanctionedBuyProductsObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Date of term loan Sanctioned</label></td>
                              <td style="width: 33%;">
                                  <form:input path="dateOfTermLoanSanctionedBuyProducts" id="dateOfTermLoanSanctionedBuyProductsId"
                                   name="dateOfTermLoanSanctionedBuyProducts" value="${bdList.detailsdateofsanctionoftermloan}" type="text" readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="dateOfTermLoanSanctionedBuyProductsObserv" id="dateOfTermLoanSanctionedBuyProductsObservId" 
                                  name="dateOfTermLoanSanctionedBuyProductsObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                       </c:forEach>
                       
                        

	

                          
                     <!--  <tfoot>
                          <tr>
                              <td colspan="3"><a href="javacript:void();" class="btn btn-outline-success btn-sm">Add More</a></td>
                          </tr>
                      </tfoot> -->
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
                            

                            <div class="col-md-12 mt-4">
                                <div class="form-group">
                                    <h4 class="card-title mb-4 mt-4 ">  Project Summary</h4>
                                    <form:textarea path="projectSummary" id="projectSummaryId" name="projectSummary"
									class="form-control border-info" rows="2"></form:textarea>
                                </div>
                            </div>

                       
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Cost of Project (in Lacs)</h4>
                                    </div>
                                </div>
                                <hr class="mt-2 mb-5">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="table-responsive">
                                            <table class="table table-stripped table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>Particulars</th>
                                                        <th>Existing Cost As per books of Account</th>
                                                        <th>Proposed Cost as per Project Report</th>
                                                        <th>Appraised Cost As Per Bank Appraisal </th>
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                 <tbody>
                                                <c:forEach var="pidList" items="${projectInvestmentDetails}" varStatus="counter">
                                                  <tr>
                                                      <td><label class="table-heading">Building</label></td>
                                                      <td>
                                                         <form:input path="landExistCost" id="landExistCostId" readonly="true"
                                                         name="landExistCost" value="${pidList.existinginvestmentbuilding}" type="text"  class="form-control COPExisting"/>
                                                      </td>
                                                      
                                                      <td>
                                                         <form:input path="landProposedCost" id="landProposedCostId" readonly="true"
                                                         name="landProposedCost" value="${pidList.proposedinvestmentbuilding}" type="text"  class="form-control COPProposed"/>
                                                      </td>
                                                      <td>
                                                         <form:input path="landAppraisedCost" id="landAppraisedCostId" readonly="true"
                                                         name="landAppraisedCost" value="${pidList.appraisedcostbybankbuilding}" type="text" class="form-control COPAppraised"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="landRemarks" id="landRemarksId" name="landRemarks"
									                            class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Plant & Machinery</label></td>
                                                      <td>
                                                         <form:input path="plantMachExistCost" id="plantMachExistCostId"
                                                          name="plantMachExistCost"  value="${pidList.existinginvestmentplantmachinery}"  type="text" readonly="true" class="form-control COPExisting"/>
                                                      </td>
                                                      <td>
                                                         <form:input path="plantMachProposedCost" id="plantMachProposedCostId" 
                                                         name="plantMachProposedCost" value="${pidList.proposedinvestmentplantmachinery}" type="text" readonly="true" class="form-control COPProposed"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="plantMachAppraisedCost" id="plantMachAppraisedCostId" 
                                                          name="plantMachAppraisedCost" value="${pidList.appraisedcostbybankplantmachinery}" type="text" readonly="true" class="form-control COPAppraised"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="plantMachBuildRemarks" id="plantMachBuildRemarksId" name="plantMachBuildRemarks"
									                            class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Misc. Fixed Assets </label></td>
                                                      <td>
                                                          <form:input path="miscFixedAssetsExistCost" id="miscFixedAssetsExistCostId" onkeypress="return isNumberKey(event)"
                                                          name="miscFixedAssetsExistCost" value="" type="text" readonly="false" class="form-control COPExisting"/>
                                                      </td>
                                                      
                                                      <td>
                                                          <form:input path="miscFixedAssetsProposedCost" id="miscFixedAssetsProposedCostId" onkeypress="return isNumberKey(event)"
                                                          name="miscFixedAssetsProposedCost" value="" type="text" readonly="false" class="form-control COPProposed"/>
                                                      </td>
                                                      <td>
                                                         <form:input path="miscFixedAssetsAppraisedCost" id="miscFixedAssetsAppraisedCostId" onkeypress="return isNumberKey(event)"
                                                         name="miscFixedAssetsAppraisedCost" value="" type="text" readonly="false" class="form-control COPAppraised"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="miscFixedAssetsBuildRemarks" id="miscFixedAssetsBuildRemarksId" name="miscFixedAssetsBuildRemarks"
									                            class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Margin Money for W.C.</label></td>
                                                      <td>
                                                          <form:input path="marginMoneyExistCost" id="marginMoneyExistCost"
                                                          name="marginMoneyExistCost" value="${pidList.existinginvestmentworkingcapital}" type="text" readonly="true" class="form-control COPExisting"/>
                                                      </td>
                                                      <td>
                                                         <form:input path="marginMoneyProposedCost" id="marginMoneyProposedCost"
                                                         name="marginMoneyProposedCost" value="${pidList.proposedinvestmentworkingcapital}" type="text" readonly="true" class="form-control COPProposed"/>
                                                      </td>
                                                      <td>
                                                         <form:input path="marginMoneyAppraisedCost" id="marginMoneyAppraisedCost" 
                                                         name="marginMoneyAppraisedCost" value="${pidList.appraisedcostbybankworkingcapital}" type="text" readonly="true" class="form-control COPAppraised"/>
                                                      </td>
                                                      <td>
                                                         <form:textarea path="marginMoneyRemarks" id="marginMoneyRemarksId" 
                                                          name="marginMoneyRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">IDC  </label></td>
                                                      <td>
                                                         <form:input path="idcExistCost" id="idcExistCostId" onkeypress="return isNumberKey(event)"
                                                         name="idcExistCost" value="" type="text" class="form-control COPExisting" />
                                                      </td>
                                                      <td>
                                                          <form:input path="idcProposedCost" id="idcProposedCostId" onkeypress="return isNumberKey(event)"
                                                           name="idcProposedCost" value="" type="text"  class="form-control COPProposed"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="idcAppraisedCost" id="idcAppraisedCostId" onkeypress="return isNumberKey(event)"
                                                          name="idcAppraisedCost" value="" type="text"  class="form-control COPAppraised" />
                                                      </td>
                                                      <td>
                                                          <form:textarea path="idcRemarks" id="idcRemarksId" name="idcRemarks"
									                            class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Preliminary and preoperative expenses   </label></td>
                                                      <td>
                                                          <form:input path="preOperativeExpensesExistCost" id="preOperativeExpensesExistCostId" 
                                                           name="preOperativeExpensesExistCost" 
                                                          value="${pidList.existinginvestmentpreoperativeexpenses}" type="text" readonly="true" class="form-control COPExisting"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="preOperativeExpensesProposedCost" id="preOperativeExpensesProposedCostId"
                                                           name="preOperativeExpensesProposedCost"
                                                           value="${pidList.proposedinvestmentpreoperativeexpenses}" type="text" readonly="true" class="form-control COPProposed"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="preOperativeExpensesAppraisedCost" id="preOperativeExpensesAppraisedCost"
                                                           name="preOperativeExpensesAppraisedCost" 
                                                          value="${pidList.appraisedcostbybankpreoperativeexpenses}" type="text" readonly="true" class="form-control COPAppraised"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="preOperativeExpensesRemarks" id="preOperativeExpensesRemarksId" 
                                                          name="preOperativeExpensesRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr> 
                                                  </c:forEach>            
                                                </tbody>
                                                <tfoot>
                                                    <tr>
															<td><label class="table-heading">Total</label></td>
															<td><form:input path="totalExistCost"
																	id="totalExistCostId" name="totalExistCost" value=""
																	type="text" readonly="true"
																	class="form-control COPExistingTotal" /></td>
															<td><form:input path="totalProposedCost"
																	id="totalProposedCostId" name="totalProposedCost"
																	value="" type="text" readonly="true"
																	class="form-control COPProposedTotal" /></td>
															<td><form:input path="totalAppraisedCost"
																	id="totalAppraisedCostId" name="totalAppraisedCost"
																	value="" type="text" readonly="true"
																	class="form-control COPAppraisedTotal" /></td>
															<td><form:textarea path="totalRemarks"
																	id="totalRemarksId" name="totalRemarks"
																	class="form-control border-info" rows="2"></form:textarea>
															</td>
														</tr>
                                                </tfoot>
                                                
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Means of Finance (in lacs)</h4>
                                    </div>
                                </div>
                                <hr class="mt-2 mb-5">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="table-responsive">
                                            <table class="table table-stripped table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>Particulars</th>
                                                        <th>Existing</th>
                                                        <th>Proposed </th>       
                                                        <th>Appraised </th>
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                 <c:forEach var="mofList" items="${meansofFinancing}" varStatus="counter">
                                                  <tr>
                                                      <td><label class="table-heading">Equity (Promoter share)</label></td>
                                                      
                                                      <td>
                                                          <form:input path="equityExistCost" id="equityExistCostId" 
                                                          name="equityExistCost" value="${mofList.existinginvestmentpromotersequity}"  type="text" readonly="true" class="form-control MOFExisting"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="equityProposedCost" id="equityProposedCosIdt" 
                                                          name="equityProposedCost"  value="${mofList.proposedinvestmentpromotersequity}"  type="text" readonly="true" class="form-control MOFProposed"/>
                                                      </td>
                                                      <td>
                                                         <form:input path="equityAppraisedCost" id="equityAppraisedCostId"
                                                          name="equityAppraisedCost" value="${mofList.appraisedcostbybankpromotersequity}" type="text" readonly="true" class="form-control MOFAppraised"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="equityRemarks" id="equityRemarksId" 
                                                          name="equityRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                 <tr>
                                                      <td><label class="table-heading">Term loan</label></td>
                                                      
                                                      <td>
                                                         <form:input path="termLoanExistCost" id="termLoanExistCostId" 
                                                         name="termLoanExistCost" value="${mofList.existinginvestmenttermloan}" type="text" readonly="true" class="form-control MOFExisting"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="termLoanProposedCost" id="termLoanProposedCostId" 
                                                          name="termLoanProposedCost" value="${mofList.proposedinvestmenttermloan}" type="text" readonly="true" class="form-control MOFProposed"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="termLoanAppraisedCost" id="termLoanAppraisedCostId" 
                                                          name="termLoanAppraisedCost" value="${mofList.appraisedcostbybanktermloan}" type="text" readonly="true" class="form-control MOFAppraised"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="termLoanRemarks" id="termLoanRemarksId" 
                                                          name="termLoanRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                   
                                                  
                                                   <tr>
                                                      <td><label class="table-heading">Assistance from other sources</label></td>
                                                      <!-- previous working capital -->
                                                      <td>
                                                          <form:input path="assistanceCapitalExistCost" id="assistanceCapitalExistCostId" 
                                                          name="assistanceCapitalExistCost" value="" type="text"  onkeypress="return isNumberKey(event)" class="form-control MOFExisting"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="assistanceCapitalProposedCost" id="assistanceCapitalProposedCostId" 
                                                          name="assistanceCapitalProposedCost" value="" type="text"  onkeypress="return isNumberKey(event)" class="form-control MOFProposed"/>
                                                      </td>
                                                      <td>
                                                         <form:input path="assistanceCapitalAppraisedCost" id="assistanceCapitalAppraisedCost" 
                                                         name="assistanceCapitalAppraisedCost" value="" type="text"  onkeypress="return isNumberKey(event)" class="form-control MOFAppraised"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="assistanceCapitalRemarks" id="assistanceCapitalRemarksId" 
                                                          name="assistanceCapitalRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr> 
                                                      <tr>
                                                      <td><label class="table-heading">Grant- in- aid </label></td>
                                                      
                                                      <td>
                                                          <form:input path="grantExistCost" id="otherExistCostId"
                                                           name="grantExistCost" value="${mofList.existinginvestmentothers}" type="text" readonly="true" class="form-control MOFExisting"/>
                                                      </td>
                                                      <td>
                                                         <form:input path="grantProposedCost" id="grantProposedCostId" 
                                                         name="grantProposedCost" value="${mofList.existinginvestmentothers}" type="text" readonly="true" class="form-control MOFProposed"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="grantAppraisedCost" id="grantAppraisedCostId" name="grantAppraisedCost" 
                                                          value="${mofList.existinginvestmentothers}" type="text" readonly="true" class="form-control MOFAppraised"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="grantRemarks" id="grantRemarksId" 
                                                          name="grantRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  
                                                  <tr>
                                                      <td><label class="table-heading">other </label></td>
                                                      
                                                      <td>
                                                          <form:input path="otherExistCost" id="otherExistCostId"
                                                           name="otherExistCost" value="${mofList.existinginvestmentothers}" type="text"  readonly="true" class="form-control MOFExisting"/>
                                                      </td>
                                                      <td>
                                                         <form:input path="otherProposedCost" id="otherProposedCostId" 
                                                         name="otherProposedCost" value="${mofList.proposedinvestmentothers}" type="text"  readonly="true" class="form-control MOFProposed"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="otherAppraisedCost" id="otherAppraisedCostId" name="otherAppraisedCost" 
                                                          value="${mofList.appraisedcostbybankothers}" type="text" readonly="true" class="form-control MOFAppraised"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="otherRemarks" id="otherRemarksId" 
                                                          name="otherRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>  
                       </c:forEach>                                          
                                                </tbody>
                                                <tfoot>
                                                    <tr>
                                                    <td><label class="table-heading">Total</label></td>
                                                    
                                                    <th>
                                                       <form:input path="totalMofExistCost" id="totalMofExistCostId" 
                                                       name="totalMofExistCost" value="" type="text" readonly="true" class="form-control MOFExistingTotal"/>
                                                    </th>
                                                    <th>
                                                       <form:input path="totalMofProposedCost" id="totalMofProposedCostId" 
                                                       name="totalMofProposedCost" value="" type="text" readonly="true" class="form-control MOFProposedTotal"/>
                                                    </th>
                                                    <th>
                                                        <form:input path="totalMofAppraisedCost" id="totalMofAppraisedCostId" 
                                                        name="totalMofAppraisedCost" value="" type="text" readonly="true" class="form-control MOFAppraisedTotal"/>
                                                    </td>
                                                    <td>
                                                         <form:textarea path="totalMofRemarks" id="totalMofRemarksId" 
                                                          name="totalMofRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                </tfoot>
                                             
                                            </table>
                                           
                                            
                                        </div>
                                    </div>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Implementation Schedule:</h4>
                                    </div>
                                </div>
                                <hr class="mt-2 mb-5">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="table-responsive">
                                            <table class="table table-stripped table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>Item of Work</th>
                                                        <th>Date</th>
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="isList" items="${implementationSchedule}" varStatus="counter">
                                                  <tr>
                                                      <td><label class="table-heading">Acquisition land</label></td>
                                                      <td>
                                                          <form:input path="acquisitionLandDate" id="acquisitionLandDateId"
                                                           name="acquisitionLandDate" value="${isList.detailsacquisitiondateofland}" type="text" readonly="true" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="acquisitionLandRemark" id="acquisitionLandRemarkId" 
                                                          name="acquisitionLandRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Start of construction of building </label></td>
                                                      
                                                      <td>
                                                          <form:input path="startConstructionOfBuildDate" id="startConstructionOfBuildDateId"
                                                           name="startConstructionOfBuildDate" value="${isList.detailsdateofbuildingconstructionfrom}" type="text" readonly="true" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="startOfConstructionOfBuildRemark" id="startOfConstructionOfBuildRemarkId" 
                                                          name="startOfConstructionOfBuildRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Completion of Building</label></td>
                                                      
                                                      <td>
                                                          <form:input path="completionOfBuildDate" id="completionOfBuildDateId"
                                                           name="completionOfBuildDate" value="${isList.detailsdateofbuildingconstructionto}" type="text" readonly="true" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="completionOfBuildRemark" id="completionOfBuildRemarkId" 
                                                          name="completionOfBuildRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Placing order for plant & machinery</label></td>
                                                      
                                                      <td>
                                                          <form:input path="placingOrderPlantMachDate" id="placingOrderPlantMachDateId" 
                                                          name="placingOrderPlantMachDate" value="${isList.detailsdateofplacingorderforplantmachineryfrom} to ${isList.detailsdateofplacingorderforplantmachineryto}" type="text" readonly="true" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="placingOrderPlantMachRemark" id="placingOrderPlantMachRemarkId" 
                                                          name="placingOrderPlantMachRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Installation/erection</label></td>
                                                      
                                                      <td>
                                                         <form:input path="installationDate" id="installationDateId"
                                                          name="installationDate" value="" type="Date" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="installationRemark" id="installationRemarkId" 
                                                          name="installationRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Trial production/running</label></td>
                                                      
                                                      <td>
                                                         <form:input path="trialProductionDate" id="trialProductionDateId" 
                                                         name="trialProductionDate" value="${isList.detailstrialproductiondatefrom} to ${isList.detailstrialproductiondateto}" type="text" readonly="true" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="trialProductionRemark" id="trialProductionRemarkId" 
                                                          name="trialProductionRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Commercial production/running</label></td>
                                                      
                                                      <td>
                                                         <form:input path="commercialProductionDate" id="commercialProductionDateId" 
                                                         name="commercialProductionDate" value="${isList.detailsdateofcommercialproductionrunning}" type="text" readonly="true" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="commercialProductionRemark" id="commercialProductionRemarkId" 
                                                          name="commercialProductionRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                      </c:forEach>                  
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Status of Documents:</h4>
                                    </div>
                                </div>
                                <hr class="mt-2 mb-5">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="table-responsive">
                                            <table class="table table-stripped table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>Item of Work</th>
                                                        <th>Description</th>
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                  <tr>
                                                      <td><label class="table-heading">Application on prescribed format <a href="">View</a></label></td>
                                                      <td>
                                                         <form:input path="appOnPrescribedFormatDesc" id="appOnPrescribedFormatDescId" 
                                                         name="appOnPrescribedFormatDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="appOnPrescribedFormatRemark" id="appOnPrescribedFormatRemarkId" 
                                                          name="appOnPrescribedFormatRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Detailed Project Report
                                                      <a href="./downloadFoodDocument?fileName=${foodDocument.detailedprojectreportasperannexurea16}" onclick="return NewTab()">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="detailedProjectReportDesc" id="detailedProjectReportDescId" 
                                                         name="detailedProjectReportDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="detailedProjectReportRemark" id="detailedProjectReportRemarkId" 
                                                          name="detailedProjectReportRemark" class="form-control border-info" rows="2"></form:textarea>
                                                          
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Sanction letter for Term loan
                                                      <a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.termloansanctionletter}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                          <form:input path="sanctionLetterTermLoanDesc" id="sanctionLetterTermLoanDescId" 
                                                          name="sanctionLetterTermLoanDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="sanctionLetterTermLoanRemark" id="sanctionLetterTermLoanRemark" 
                                                          name="sanctionLetterTermLoanRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Bank Appraisal Report
                                                      <a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.bankappraisalreport}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                          <form:input path="bankAppraisalReportDesc" id="bankAppraisalReportDescId" 
                                                          name="bankAppraisalReportDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="bankAppraisalReportRemark" id="bankAppraisalReportRemarkId" 
                                                          name="bankAppraisalReportRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">C.E. Civil Certification for(A/7)
                                                      <a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.annexurea7charteredengineercertificateforcivilwork}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                          <form:input path="ceCivilCertificationForA7Desc" id="ceCivilCertificationForA7DescId" 
                                                          name="ceCivilCertificationForA7Desc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="ceCivilCertificationForA7Remark" id="ceCivilCertificationForA7RemarkId" 
                                                          name="ceCivilCertificationForA7Remark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">C.E. Mechanical Certification for(A/8)
                                                      <a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.annexurea8cecertificateforplantampmachinery}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="ceMechanicalCertificationForA8Desc" id="ceMechanicalCertificationForA8DescId" 
                                                         name="ceMechanicalCertificationForA8Desc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="ceMechanicalCertificationForA8Remark" id="ceMechanicalCertificationForA8RemarkId" 
                                                          name="ceMechanicalCertificationForA8Remark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  
                                                  <tr>
                                                      <td><label class="table-heading">Building Plan of Factory Approved By Competent Authority<a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.termloansanctionletter}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                          <form:input path="buildingPlanOfFactoryDesc" id="buildingPlanOfFactoryDescId" 
                                                          name="buildingPlanOfFactoryDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="buildingPlanOfFactoryRemark" id="buildingPlanOfFactoryRemark" 
                                                          name="buildingPlanOfFactoryRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Land Document<a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.registeredlanddeedrentagreement}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="landDocumentDesc" id="landDocumentDescId"
                                                          name="landDocumentDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="landDocumentRemark" id="landDocumentRemarkId" 
                                                          name="landDocumentRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Affidavit on Prescribed format
                                                      <a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.registeredlanddeedrentagreement}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="affidavitPrescribedFormatDesc" id="affidavitPrescribedFormatDescId"
                                                          name="affidavitPrescribedFormatDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="affidavitPrescribedFormatRemark" id="affidavitPrescribedFormatRemarkId" 
                                                          name="affidavitPrescribedFormatRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  
                                                  
                                                  <tr>
                                                      <td><label class="table-heading">Incorporation Certificate of Firm
                                                      <a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.incorporationcertificateoffirm}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="incorporationCertificationDesc" id="incorporationCertificationDescId"
                                                          name="incorporationCertificationDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="incorporationCertificationRemark" id="incorporationCertificationRemarkId" 
                                                          name="incorporationCertificationRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                        
                                                        
                                                        <tr>
                                                      <td><label class="table-heading">Partnership deed / Byelaws of the Firm
                                                      <a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.partnershipdeedbyelawsofthefirm}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="partnershipDeedDesc" id="partnershipDeedDescId"
                                                          name="partnershipDeedDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="partnershipDeedRemark" id="partnershipDeedRemarkId" 
                                                          name="partnershipDeedRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  
                                                  <tr>
                                                      <td><label class="table-heading">IEM / Udyog Aadhar / Udyam Register
                                                      <a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.iemudyogaadharudyamregister}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="udyogAadharDesc" id="udyogAadharDescId"
                                                          name="udyogAadharDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="udyogAadharRemark" id="udyogAadharRemarkId" 
                                                          name="udyogAadharRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  
                                                  <tr>
                                                      <td><label class="table-heading">Sanction letter Issued by MOFPI Government of India
                                                      <a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.sanctionletterissuedbymofpigovernmentofindia}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="sanctionLetterIssuedByMOFPIDesc" id="sanctionLetterIssuedByMOFPIDescId"
                                                          name="sanctionLetterIssuedByMOFPIDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="sanctionLetterIssuedByMOFPIRemark" id="sanctionLetterIssuedByMOFPIRemarkId" 
                                                          name="sanctionLetterIssuedByMOFPIRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>                
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Eligible cost of Plant Machinery-</h4>
                                    </div>
                                </div>
                                <div class="table-responsive mt-3">
                                      <table class="table table-stripped table-bordered" id="BreakUpTable1">
                                          <thead>
                                              <tr>
                                                  <th>Name of Plant Machinery</th>
                                                  <th>Name of the Supplier company</th>
                                                  <th>Basic Price</th>
                                                  <th>Total</th>
                                                  <th style="width: 17%;">Eligible cost in lacs</th>
                                              </tr>
                                          </thead>
                                          <tbody class="add-from-here1">
                                          <c:forEach var ="pnmlist" items ="${eligiblePlantMachPMKSYList}" varStatus ="counter" >
                                              <tr>
                                                  <td>
                                                 <form:input path="eligibleCostNameOfPlantMach" id="eligibleCostNameOfPlantMachId" 
                                                 name="eligibleCostNameOfPlantMach" value="${pnmlist.eligibleCostNameOfPlantMach}" type="text" readonly="true" class="form-control"/>
                                                  </td>
                                                  <td><form:input path="eligibleCostNameOfSupplierCompany" id="eligibleCostNameOfSupplierCompanyId"
                                                   name="eligibleCostNameOfSupplierCompany" value="${pnmlist.eligibleCostNameOfSupplierCompany}" type="text" readonly="true" class="form-control"/></td>
                                                  <td>
                                                     <form:input path="eligibleCostBasicPrice" id="eligibleCostBasicPriceId"
                                                      name="eligibleCostBasicPrice" value="${pnmlist.eligibleCostBasicPrice}" type="text" readonly="true" class="form-control"/>
                                                  </td>
                                                  <td>
                                                     <form:input path="eligibleCostTotal" id="eligibleCostTotalId"
                                                      name="eligibleCostTotal" value="${pnmlist.eligibleCostTotal}" type="text"  class="form-control"/>
                                                  </td>
                                                  <td>
                                                      <form:input path="eligibleCostInLacs" id="eligibleCostInLacsId" class = "eligibleCostInLacsId form-control" 
                                                      name="eligibleCostInLacs" value="${pnmlist.eligibleCostInLacs}" type="text"  />
                                                  </td>
                                              </tr>
                                              </c:forEach>
                                          </tbody>
                                          <tfoot>
                                              <tr>
                                                  <td colspan="4" class="text-right"><strong>Total:</strong></td>
                                                  <td><form:input path="totaleEligibleCostInLacs" id="totaleEligibleCostInLacsId" 
                                                  name="totaleEligibleCostInLacs" value="" type="text" class="form-control totaleEligibleCostInLacs" readonly ="true"/></td>
                                              </tr>
                                             
                                             
                                          </tfoot>
                                      </table>
                                  </div>
                                  <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <label class="mt-4">Nodal Officer's Remark</label>
                                        <form:textarea path="eligibleCostRemarks" id="eligibleCostRemarksId" 
                                           name="eligibleCostRemarks" class="form-control border-info" rows="2"></form:textarea>
                                    </div>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Eligible Technical Civil Work-</h4>
                                    </div>
                                </div>
                                <div class="table-responsive mt-3">
                                      <table class="table table-stripped table-bordered" id="BreakUpTable2">
                                          <thead>
                                              <tr>
                                                  <th>Particular</th>
                                                  <th>Area (Sqmt.)</th>
                                                  <th>Rate</th>
                                                  <th>Amount</th>
                                                  <th style="width: 17%;">Eligible cost (in lacs)</th>
                                              </tr>
                                          </thead>
                                          <tbody class="add-from-here2">
                                          <c:forEach var ="civilist" items ="${eligibleCostOfCivilWorkPMKSYList}" varStatus = "counter">
                                              <tr>
                                                  <td><form:input path="eligibleTechCivilWorkParticular" id="eligibleTechCivilWorkParticularId"
                                                   name="eligibleTechCivilWorkParticular" value="${civilist.eligibleTechCivilWorkParticular}" type="text" readonly="true" class="form-control"/></td>
                                                   
                                                  <td><form:input path="eligibleTechCivilWorkArea" id="eligibleTechCivilWorkAreaId" 
                                                  name="eligibleTechCivilWorkArea" value="${civilist.eligibleTechCivilWorkArea}" type="text" readonly="true" class="form-control"/></td>
                                                  <td>
                                                      <form:input path="eligibleTechCivilWorkRate" id="eligibleTechCivilWorkRateId"
                                                       name="eligibleTechCivilWorkRate" value="${civilist.eligibleTechCivilWorkRate}" type="text" readonly="true" class="form-control"/>
                                                  </td>
                                                  <td>
                                                     <form:input path="eligibleTechCivilWorkAmount" id="eligibleTechCivilWorkAmountId" 
                                                     name="eligibleTechCivilWorkAmount" value="${civilist.eligibleTechCivilWorkAmount}" type="text" readonly="true" class="form-control"/>
                                                  </td>
                                                  <td>
                                                      <form:input path="eligibleTechCivilWorkEligibleCost" id="eligibleTechCivilWorkEligibleCostId"
                                                       name="eligibleTechCivilWorkEligibleCost" value="${civilist.eligibleTechCivilWorkEligibleCost}" type="text"  class="eligibleTechCivilWorkEligibleCostId form-control"/>
                                                  </td>
                                              </tr>
                                              </c:forEach>
                                          </tbody>
                                          <tfoot>
                                              <tr>
                                                  <td colspan="4" class="text-right"><strong>Total:</strong></td>
                                                  
                                                  <td><form:input path="ttlEligibleTechCivilWorkEligibleCost" id="ttlEligibleTechCivilWorkEligibleCostId"
                                                  name="ttlEligibleTechCivilWorkEligibleCost" value="" type="text"  class="ttlEligibleTechCivilWorkEligibleCostId form-control" readonly ="true"/></td>
                                              </tr>
                                              <!-- <tr>
                                                  <td colspan="5">
                                                      <button type="button" class="btn btn-outline-success add-row-break-up-new1"><i class="fa fa-plus"></i> Add More Items</button>
                                                  </td>
                                              </tr> -->
                                          </tfoot>
                                      </table>
                                  </div>
                                  <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <label class="mt-4">Nodal Officer's Remark</label>
                                         <form:textarea path="eligibleTechCivilWorkRemarks" id="eligibleTechCivilWorkRemarksId" 
                                           name="eligibleTechCivilWorkRemarks" class="form-control border-info" rows="2"></form:textarea>
                                    </div>
                                </div>
                                
                                
                                
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Calculation for Capital Subsidy </h4>
                                    </div>
                                </div>
                                <div class="table-responsive mt-3">
                                
                                  <table class="table table-stripped table-bordered">
                                  
                                    <tbody>
                                      <tr>
                                        <td>Amount Sanctioned by MOFPI GoI Under PMKSY (Scheme for Creation/Expansion of Food Processing &amp; Preservation Capacities</td>
                                        <td><form:input type="text" path = "ammountmofpipmksy" value=""  id ="ammountmofpipmksy" class="form-control" name="" maxlength="12"></form:input></td>
                                      </tr>
                                      <tr>
                                        <td>Eligible Additional Capital Subsidy As per UPFPIP-2017  Section 7.1.1 (A) @10% of Grant Sanctioned by MOFPI GoI </td>
                                        <td><form:input type="text" path ="eligiaddics" value="" id ="eligiaddics" readonly ="true" class="form-control" name="" maxlength="12"></form:input></td>
                                      </tr>
                                    </tbody>
                                    
                                    <div>
                                    <tfoot>
                                    
                                      <tr>
                                      <td><p> Max. Eligible Grant <a href="javascript:void(0);" class="remove-row" data-toggle="tooltip" title=""
																data-original-title="As per Food Policy-2017 for the capital subsidy pmksy Maximum Amount of 50 Lakhs or 
Eligible Additional Capital Subsidy As per UPFPIP-2017 Section 7.1.1 (A) @10% of Grant Sanctioned by MOFPI GoI whichever is less will be Provided ">
		<i class="fa fa-info-circle text-info"></i></a></p></td>
                                      
                                        
                                        <th><form:input type="text" path = "maxgrant" value=""  id ="maxgrant" class="form-control" name=""  readonly ="true"></form:input>></th>
                                      </tr>
                                      </div>
                                    </tfoot>
                                  </table>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title  mt-4">Recommendation/Comments</h4>
                                        <form:textarea path="recommendationComments" id="recommendationCommentsId" 
                                           name="recommendationComments" class="form-control border-info" rows="2"></form:textarea>
                                    </div>
                                </div>
                                <hr class="mt-4 mb-4">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="custom-control custom-checkbox mb-1">
                                            <input type="checkbox" class="custom-control-input" id="verified" name="Capital-Interest-Subsidy">
                                            <label class="custom-control-label" for="verified" id="verifiedChecked">I have read and verified the entire form carefully</label>
                                        </div>
                                    </div>
                                </div>
                                <hr class="mt-4 mb-4">
                                <div class="row mt-4">
                                    <div class="col-sm-12 text-center">
                                        <button type="button" class="btn btn-outline-secondary evaluate-btn btn-sm mb-3" data-target="#evaluateConfirm" data-toggle="modal" id="evaluateBtn">Evaluation Completed</button>
                                        <a href="#RaiseQuery" class="btn btn-outline-danger btn-sm mb-3" data-toggle="modal">Raise Query</a>
                                        <button type="button" id="IncludeApplicationBtn" class="btn btn-outline-info disable-btn btn-sm mb-3">Include Application in Agenda Note</button>
                                        <a href="#IncludeApplicationBtn" class="btn btn-outline-danger disable-btn btn-sm mb-3" data-toggle="modal" id="RejectBtn">Reject</a>
                                    </div>
                                </div>
                                <hr class="mt-2">
                                <div class="row">
                                    <div class="col-sm-5">
                                        <a href="./foodviewApplicatPMKSY?unit_Id=${businessEntityDetailsfood.unit_id}" class="common-default-btn mt-3">Back</a>
                                    </div>
                                    <div class="col-sm-7 text-right">
                                        <a href="javacript:void(0);" class="common-btn mt-3"><i class="fa fa-edit"></i> Edit</a>
                                        <form:button class="common-btn mt-3">Save</form:button>
                                    </div>
                                </div>
                                
                            </div>
                            
                        </div>
                        
                    </div>
                    
                </div>
                </form:form>
            </div>
        </div>
    </section>
    <footer class="dashboard-footer">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="social-icons">
                        <ul>
                            <li><a href="#"><i class="fa fa-facebook-f"></i></a></li>
                            <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                            <li><a href="#"><i class="fa fa-youtube"></i></a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="footer-menu">
                        <ul>
                            <li><a href="index.html" target="_blank">Home</a></li>
                            <li><a href="http://udyogbandhu.com/topics.aspx?mid=About%20us" target="_blank">About Us</a></li>
                            <li><a href="http://udyogbandhu.com/topics.aspx?mid=Disclaimer" target="_blank">Disclaimer</a></li>
                            <li><a href="http://udyogbandhu.com/topics.aspx?mid=General%20Terms%20And%20Conditions" target="_blank">General Terms And Conditions</a></li>
                            <li><a href="http://udyogbandhu.com/topics.aspx?mid=Privacy%20Policy" target="_blank">Privacy Policy</a></li>
                            <li><a href="http://udyogbandhu.com/topics.aspx?mid=Refund%20Policy" target="_blank">Refund Policy</a></li>
                            <li><a href="http://udyogbandhu.com/topics.aspx?mid=Delivery%20Policy" target="_blank">Delivery Policy</a></li>
                            <li><a href="http://udyogbandhu.com/topics.aspx?mid=Contact%20Us" target="_blank">Contact Us</a></li>
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
                                <p>© 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit</p>
                                <p>Designed and Developed by National Informatics Centre ( NIC )</p>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="page-visit">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>
    <div class="container">
        <!-- The Modal -->
        <div class="modal fade" id="evaluateConfirm">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">Confirmation</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <!-- Modal body -->
                    <div class="modal-body">
                        <p>Are you sure you want to submit the application</p>
                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <a href="evaluation-view.html" class="btn common-btn btn-sm mt-0">Yes</a>
                        <button type="button" class="btn btn-outline-danger mt-0" data-dismiss="modal">No</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- The Modal -->
        <div class="modal fade" id="RejectApplication">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">Reject Application</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <!-- Modal body -->
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Reason</label>
                                    <textarea class="form-control" rows="5"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <a href="javacript:void();" class="common-btn mt-0" data-toggle="modal" data-dismiss="modal">Submit</a>
                    </div>
                </div>
            </div>
        </div>
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
                                        <input type="file" class="custom-file-input" id="UploadDocumentForQuery">
                                        <label class="custom-file-label" for="UploadDocumentForQuery">Choose file</label>
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
                                            <tr>
                                                <td>1.</td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>2.</td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
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
    </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>
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
    window.onscroll = function() { scrollFunction() };

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