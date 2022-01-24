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
                 <h4 class="card-title mb-4 mt-4 text-center">Interest Subsidy for Reefer Vehicles/Mobile Units.</h4>
               
                    <div class="card card-block p-3">
                        <div class="row">
                            <div class="col-sm-5">
                               
                                    <a href="./foodviewApplicationDetails?unit_Id=${businessEntityDetailsfood.unit_id}"
									class="common-default-btn mt-3">Back</a>
                            </div>
                            
                            <div class="col-sm-7 text-right">
                                <a href="#EvaluationAuditTrail" class="text-info mr-3" data-toggle="modal">Evaluation Audit Trail</a>
                                <a href="javacript:void(0);" class="common-btn mt-3"><i class="fa fa-edit"></i> Edit</a>
                            </div>
                        </div>
                              <form:form modelAttribute="foodViewEvaluateISRV" method="post"
							action="savefoodEvaluateApplicationISRV" class="mt-4" enctype="multipart/form-data">
							
                        <div class="row mt-5">
                            <div class="col-sm-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Name of Organization</th>
                                                <th>Registered Address</th>
                                                <th>Project Name</th>
                                              <!--   <th>Proposed Site Address</th> -->
                                                <th>Capacity </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td><input id="nameOfOrganizationId"
													name="nameOfOrganization" value="${bEDVReeferVehicles.nameoffirm}"
													class="form-control" readonly="true"></input></td>
													
                                                <td><input id="registeredAddressId"
													name="registeredAddress" value="${bEDVReeferVehicles.addressoffirm}"
													class="form-control" readonly="true"></input></td>
													
                                                <td><input id="projectNameid"
													name="projectName" value="${ProjectDtailsReefereVehicles.nameoftheproject}"
													class="form-control" readonly="true"></input></td>
													
                                                <%-- <td><input id="proposedSiteAddressid"
													name="proposedSiteAddress"value="${ProjectDtailsReefereVehicles.locationareaoftheproject}"
													class="form-control" readonly="true"></input></td> --%>
													<c:forEach var="rvdlist" items="${reeferVehiclesDetails}" varStatus="counter">
                                                <td><input id="capacityid"
													name="capacity" value="${rvdlist.quantitycapacityofthereefervehiclesmobileprecoolingvans}"
													class="form-control" readonly="true"></input></td>
													</c:forEach>
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
									class="form-control border-info" rows="2"></form:textarea>
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
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                          </tr>
                          
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Name of the firm</label></td>
                              <td style="width: 33%;">
                              
                               <form:input path="nameOfOrganization" id="nameOfOrganization" name="nameOfOrganization" value="${bEDVReeferVehicles.nameoffirm}" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="typeOfOrganizationObserv" id="typeOfOrganizationObservId" name="typeOfOrganizationObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                            
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Name of Promoter</label></td>
                              <td style="width: 33%;">
                              
                              <form:input path="nameOfPromoter" id="nameOfPromoterId" name="nameOfPromoter" value="${bEDVReeferVehicles.nameofpromoter}" type="text"
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
                              
                               <form:input path="typeOfOrganization" id="typeOfOrganizationId" name="typeOfOrganization" value="${bEDVReeferVehicles.typeoforganization}" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="typeOfOrganizationObserv" id="typeOfOrganizationObservId" name="typeOfOrganizationObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                            
                          </tr> 
                          <%-- <tr>
                              <td style="width: 33%;"><label class="table-heading">Date of incorporation</label></td>
                              <td style="width: 33%;">
                              
                              <form:input path="dateOfIncorporation" id="dateOfIncorporationId" name="dateOfIncorporation" value="${businessEntityDetailsfood.dateofincorporation}" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="dateOfIncorporationObserv" id="dateOfIncorporationObservId" name="dateOfIncorporationObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                          </tr> --%>
                          
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">PAN No.</label></td>
                              <td style="width: 33%;">
                              
                              <form:input path="panNo" id="panNoId" name="panNo" value="${bEDVReeferVehicles.pancardoffirmproprietor}" type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="panNoObserv" id="panNoId" name="panNoObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Registered Office the firm</label></td>
                              <td style="width: 33%;">
                              
                              <form:input path="regisOfficefirm" id="regisOfficefirm" name="regisOfficefirm" 
                             value="${bEDVReeferVehicles.addressoffirm}"  type="text"
                               readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="regisOfficefirmobser" id="regisOfficefirmobserId" name="regisOfficefirmobser"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                               
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Purpose of Reefer Vehicle</label></td>
                              <td style="width: 33%;">
                              
                               <form:input path="perposevehicle" id="perposevehicle" name="perposevehicle" value="" type="text"
                               class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="perposevehicleObserv" id="perposevehicleId" name="perposevehicleObserv"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                                                                 
                          </tr>  
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Number of Refer vehicle/ Mobile pre cooling van</label></td>
                               <c:forEach var="rvdlist" items="${reeferVehiclesDetails}" varStatus="counter">
                              <td style="width: 33%;">
                              
                              <form:input path="numcaprefvehi" id="numcaprefvehi" name="numcaprefvehi" value="${rvdlist.quantitynumberofthereefervehiclesmobileprecoolingvans}"  type="text"
                               readonly="false" class="form-control"/>
                              </td>
                              </c:forEach>
                              <td>
                                  <form:textarea path="numcaprefvehiObservation" id="numcaprefvehiObservationId" name="numcaprefvehiObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                                   
                          </tr> 
                          
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Capacities of Refer vehicle/ Mobile pre cooling van</label></td>
                               <c:forEach var="rvdlist" items="${reeferVehiclesDetails}" varStatus="counter">
                              <td style="width: 33%;">
                              
                              <form:input path="numcaprefvehi" id="numcaprefvehi" name="numcaprefvehi" value="${rvdlist.quantitycapacityofthereefervehiclesmobileprecoolingvans}"  type="text"
                               readonly="false" class="form-control"/>
                              </td>
                              </c:forEach>
                              <td>
                                  <form:textarea path="numcaprefvehiObservation" id="numcaprefvehiObservationId" name="numcaprefvehiObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                                   
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Make/Model of Refer vehicle</label></td>
                             <td style="width: 33%;">
                             
                             <form:input path="modelRV" id="modelRVId" name="modelRV" value="" type="text"
                               class="form-control" onkeypress="return onlyAlphabets(event,this);" maxlength="250"/>
                              </td>
                              <td>
                                  <form:textarea path="modelrvObservation" id="modelrvObservationId" name="modelrvObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Chassis No.</label></td>
                              <td style="width: 33%;">
                                  <form:input path="chassis" id="chassisId" name="chassis" value="" type="text" class="form-control" onkeypress="return onlyAlphabets(event,this);" maxlength="100"/>
                              </td>
                              <td>
                                  <form:textarea path="chassisObservation" id="chassisObservationId" name="chassisObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Engine No.</label></td>
                              <td style="width: 33%;">
                                 <form:input path="engine" id="engineId" name="engine" value="" type="text"  class="form-control" onkeypress="return onlyAlphabets(event,this);" maxlength="100"/>
                              </td>
                              <td>
                                  <form:textarea path="engineObservation" id="engineObservationId" name="engineObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Name of appraising bank</label></td>
                              <td style="width: 33%;">
                                 <form:input path="appraisingBank" id="appraisingBankId" name="appraisingBank" value="" type="text" class="form-control" onkeypress="return onlyAlphabets(event,this);" maxlength="250"/>
                              </td>
                              <td>
                                  <form:textarea path="appraiseBankObservation" id="appraiseBankObservationId" name="appraiseBankObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Amount of Term loan Sanctioned</label></td>
                              <td style="width: 33%;">
                                 <form:input path="sanctionloan" id="sanctionloanId" name="sanctionloan" value="" type="text" class="form-control" maxlength="100"/>
                              </td>
                              <td>
                                  <form:textarea path="sanctionloanObservation" id="sanctionloanObservationId" name="sanctionloanObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Rate of Interest</label></td>
                              <td style="width: 33%;">
                                 <form:input path="roi" id="roiId" name="roi" value="" type="text" class="form-control" onkeypress="return isNumberKey(event)" maxlength="10"/>
                              </td>
                              <td>
                                  <form:textarea path="roiObservation" id="roiObservationId" name="roiObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Date of term loan Sanctioned</label></td>
                              <td style="width: 33%;">
                                 <form:input path="datesancloan" id="datesancloanId" pattern = "dd/MM/yyyy"  name="datesancloan" value="" type="Date" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="datesancloanObservation" id="datesancloanObservationId" name="datesancloanObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Date of Term loan disbursed</label></td>
                              <td style="width: 33%;">
                                 <form:input path="datedisbloan" id="datedisbloanId" name="datedisbloan" value="" type="Date" readonly="false" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="datedisbloanObservation" id="datedisbloanObservationId" name="datedisbloanObservation"
									class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          </tbody>
                          </table>
                          <tr>
                            <td colspan="3">
                            <%--     <table style="width: 100%">
                               
							<tr>
                              <td style="width: 33%;"><label class="table-heading">Proposed Capacity(For New Unit)</label></td>
                              <td style="width: 33%;">
                                  <form:input path="proposedCapacityBuyProducts" id="proposedCapacityBuyProductsId" name="proposedCapacityBuyProducts" value="" type="text" readonly="true" class="form-control"/>
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
                                  <form:input path="capacityUtilizationBuyProducts" id="capacityUtilizationBuyProductsId" name="capacityUtilizationBuyProducts" value="" type="text" readonly="true" class="form-control"/>
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
                   </table>--%>
              </div>
          </div>
      </div>
                            

                            <div class="col-md-12 mt-4">
                                <div class="form-group">
                                    <h4 class="card-title mb-4 mt-4 ">	Project Summary</h4>
                                    <form:textarea path="projectSummary"  maxlength="10000" id="projectSummaryId" name="projectSummary"
									class="form-control border-info" rows="3"></form:textarea>
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
                                            <table class="table table-stripped table-bordered" id="BreakUpTable-ree">
                                          <thead>
                                              <tr>
                                                  <th>Particulars</th>
                                                  <th>Cost</th>
                                              </tr>
                                          </thead>
                                          <tbody class="add-from-here1ree">
                                          <c:forEach var="pcList" items="${projectCostReeferVehicles}" varStatus="counter">
                                              <tr>
                                                  <td><label class="table-heading">Reefer vehicle cost</label></td>
                                                   <c:forEach var="rvdlist" items="${reeferVehiclesDetails}" varStatus="counter">
                                                   <td><form:input path="reeferVehiclesCost" id="reeferVehiclesCostId" name="reeferVehiclesCost" value="${pcList.proposedinvestmentreefervehiclesmobileprecoolingvan}"
                                                    readonly="true" class="form-control"/></td>
                                                    </c:forEach>
                                                                       
                                              </tr>
                                              
                                          </tbody>
                                          <tfoot>
                                              <tr>
                                                  <td class="text-right"><strong>Total:</strong></td>
                                                 <td><form:input path="totalreeferVehiclesCost" id="totalreeferVehiclesCostId" name="totalreeferVehiclesCost" value="" readonly="true" class="form-control"/></td>
                                              </tr>
                                              <!-- <tr>
                                                  <td colspan="2">
                                                      <button type="button" class="btn btn-outline-success add-row-break-up-newree"><i class="fa fa-plus"></i> Add More Items</button>
                                                  </td>
                                              </tr> -->
                                              </c:forEach>
                                          </tfoot>
                                      </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <label class="mt-4">Nodal Officer's Remark</label>
                                       <form:textarea path="copNodalOfficer"  maxlength="10000" id="copNodalOfficerId" name="copNodalOfficer"
									class="form-control border-info" rows="3"></form:textarea>
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
                                            <table class="table table-stripped table-bordered" id="BreakUpTable-ree1">
                                          <thead>
                                              <tr>
                                                  <th>Particulars</th>
                                                  <th>Cost</th>
                                              </tr>
                                          </thead>
                                         
                                          <tbody class="add-from-here1ree1">
                                           <c:forEach  var="mofList" items="${meansOfFinancingReeferVehicles}" varStatus="counter">
                                         
                                              <tr>
                                                  <td><label class="table-heading">Equity (Promoter Share)</label></td>
                                                    
                                                   <td><form:input path="equityCost" id="equityCostId" name="equityCost" value="${mofList.asperappraisalofthebankpromoterscontribution}"
                                                    readonly="true" class="form-control"/></td>
                                                   
                                                           
                                              </tr>
                                              <tr>
                                                  <td><label class="table-heading">Term loan</label></td>
                                                   <td><form:input path="termLoanCost" id="termLoanCostId" name="termLoanCost"   value="${mofList.asperappraisalofthebanktermloan}" readonly="true" class="form-control"/></td>
                                                                     
                                              </tr>
                                              </c:forEach>
                                          </tbody>
                                          
                                          <tfoot>
                                              <tr>
                                                  <td class="text-right"><strong>Total:</strong></td>
                                                  <td><form:input path="totalMOFCost" id="totalMOFCostId" name="totalMOFCost"  readonly="true" class="form-control totaladdmeans"/></td>
                                              
                                              </tr>
                                             
                                         
                                              <!-- <tr>
                                                  <td colspan="2">
                                                     <button type="button" class="btn btn-outline-success add-row-break-up-newree1"><i class="fa fa-plus"></i> Add More Items</button>
                                                  </td>
                                              </tr> -->
                                            
                                          </tfoot>
                                      </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <label class="mt-4">Nodal Officer's Remark</label>
                                        <form:textarea path="totalMofRemarks"  maxlength="10000" id="totalMofRemarksId" name="totalMofRemarks"
									class="form-control border-info" rows="3"></form:textarea>
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
                                                         <form:input path="appOnPrescribedFormatDesc" maxlength="250" id="appOnPrescribedFormatDescId" 
                                                         name="appOnPrescribedFormatDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="appOnPrescribedFormatRemark"  maxlength="10000" id="appOnPrescribedFormatRemarkId" 
                                                          name="appOnPrescribedFormatRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Detailed Project Report
                                                      <a href="./downloadFoodReeferVehiclesDocument?fileName=${documentsReeferVehicles.selfnbspattesteddetailedprojectreportnbsp}" onclick="return NewTab()">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="detailedProjectReportDesc"  maxlength="250" id="detailedProjectReportDescId"  
                                                         name="detailedProjectReportDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="detailedProjectReportRemark"  maxlength="10000" id="detailedProjectReportRemarkId" 
                                                          name="detailedProjectReportRemark" class="form-control border-info" rows="2"></form:textarea>
                                                          
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Sanction letter for Term loan
                                                      <a href="./downloadFoodReeferVehiclesDocument?fileName=${documentsReeferVehicles.termloansanctionletter}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                          <form:input path="sanctionLetterTermLoanDesc"   id="sanctionLetterTermLoanDescId"  maxlength="250"
                                                          name="sanctionLetterTermLoanDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td> 
                                                           <form:textarea path="sanctionLetterTermLoanRemark"  maxlength="10000" id="sanctionLetterTermLoanRemark" 
                                                          name="sanctionLetterTermLoanRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Bank Appraisal Report
                                                      <a href="./downloadFoodReeferVehiclesDocument?fileName=${documentsReeferVehicles.bankappraisalreport}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                          <form:input path="bankAppraisalReportDesc"  maxlength="250" id="bankAppraisalReportDescId" 
                                                          name="bankAppraisalReportDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="bankAppraisalReportRemark"  maxlength="10000" id="bankAppraisalReportRemarkId" 
                                                          name="bankAppraisalReportRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <!-- change -->
                                                  <tr>
                                                      <td><label class="table-heading">Quotation for reefer vehicle certified by CE
                                                      <a href="./downloadFoodDocument?fileName=${documentsReeferVehicles.billboucherinvoicescertifiedbycharteredengineer}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                          <form:input path="quotationReeferVehicleCertifiedCEDesc" maxlength="250" id="quotationReeferVehicleCertifiedCEDescId" 
                                                          name="quotationReeferVehicleCertifiedCEDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="quotationReeferVehicleCertifiedCEDescRemark"  maxlength="10000" id="quotationReeferVehicleCertifiedCEDescRemarkId" 
                                                          name="quotationReeferVehicleCertifiedCEDescRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Invoice of reefer vehicle Certified by CE Mechanical
                                                      <a href="./downloadFoodReeferVehiclesDocument?fileName=${documentsReeferVehicles.billboucherinvoicescertifiedbycharteredengineer}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="invoiceReeferVehicleCertifiedCEMecDecs" maxlength="250" id="invoiceReeferVehicleCertifiedCEMecDecsId" 
                                                         name="invoiceReeferVehicleCertifiedCEMecDecs" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="invoiceReeferVehicleCertifiedCEMecDecsRemark" maxlength="10000"  id="invoiceReeferVehicleCertifiedCEMecDecsRemarkId" 
                                                          name="invoiceReeferVehicleCertifiedCEMecDecsRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Certificate of Incorporation/ Partnership deed
                                                      <a href="./downloadFoodReeferVehiclesDocument?fileName=${documentsReeferVehicles.incorporationcertificateoffirm}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                          <form:input path="certificateOfIncorporationDesc"  maxlength="250" id="certificateOfIncorporationDescId"
                                                           name="certificateOfIncorporationDesc" value=""  type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="certificateOfIncorporationRemark" maxlength="1000" id="certificateOfIncorporationRemarkId" 
                                                          name="certificateOfIncorporationRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr> 
                                                  <tr>
                                                      <td><label class="table-heading">Promoters Bio-Data
                                                      <%-- <a href="./downloadFoodDocument?fileName=${foodDocumentPMKSY.termloansanctionletter}" target="_blank">View</a> --%>
                                                      </label></td>
                                                      
                                                      <td>
                                                          <form:input path="promotersBioDataDesc" id="promotersBioDataDescId" 
                                                          name="promotersBioDataDesc" value="" type="text"  maxlength="250"  class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="promotersBioDataDescRemark" id="promotersBioDataDescRemarkId" 
                                                          name="promotersBioDataDescRemark"  maxlength="10000" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Delivery challan and receipt of vehicle chassis & Refrigeration unit certified by bank
                                                      <a href="./downloadFoodReeferVehiclesDocument?fileName=${documentsReeferVehicles.copyofdeliverychallanampreceiptofchassisbodyforreefervehicles}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="deliveryChallanDesc" id="deliveryChallanDescId"
                                                          name="deliveryChallanDesc" value="" type="text"  maxlength="250" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="deliveryChallanRemark" id="deliveryChallanRemarkId" 
                                                          name="deliveryChallanRemark" class="form-control border-info"  maxlength="10000" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Repayment schedule of term loan
                                                      <a href="./downloadFoodReeferVehiclesDocument?fileName=${documentsReeferVehicles.repaymentscheduleofnbsptermloanforinterestbybankfinancialinst}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="repaymentScheduleOfTermLoanDesc" id="repaymentScheduleOfTermLoanDescId"
                                                          name="repaymentScheduleOfTermLoanDesc" value=""  maxlength="250" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="repaymentScheduleOfTermLoanRemark"  maxlength="10000" id="repaymentScheduleOfTermLoanRemarkId" 
                                                          name="repaymentScheduleOfTermLoanRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  
                                                  <tr>
                                                      <td><label class="table-heading">Certificate of Registration for reefer vehicle
                                                      <a href="./downloadFoodReeferVehiclesDocument?fileName=${documentsReeferVehicles.repaymentscheduleofnbsptermloanforinterestbybankfinancialinst}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="certificateOfRegisForReeferVehicleDesc" id="certificateOfRegisForReeferVehicleDescId"
                                                          name="certificateOfRegisForReeferVehicleDesc" value="" maxlength="250"  type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="certificateOfRegisForReeferVehicleRemarks"  maxlength="10000" id="certificateOfRegisForReeferVehicleRemarksId" 
                                                          name="certificateOfRegisForReeferVehicleRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Certificate of Fitness for vehicle
                                                      <%-- <a href="./downloadFoodDocument?fileName=${documentsReeferVehicles.registeredlanddeedrentagreement}" target="_blank">View</a> --%>
                                                      </label></td>
                                                      
                                                      <td>
                                                         <form:input path="certificateOfFitnessForVehicleDesc"  maxlength="250" id="certificateOfFitnessForVehicleDescId"
                                                          name="certificateOfFitnessForVehicleDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="certificateOfFitnessForVehiclesRemarks"  maxlength="10000" id="certificateOfFitnessForVehiclesRemarksId" 
                                                          name="certificateOfFitnessForVehiclesRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Undertaking for Job work
                                                      <%-- <a href="./downloadFoodDocument?fileName=${documentsReeferVehicles.registeredlanddeedrentagreement}" target="_blank">View</a> --%>
                                                      </label></td>
                                                      
                                                      <td>
                                                         <form:input path="undertakingForJobWorkDesc"  maxlength="250" id="undertakingForJobWorkDescId"
                                                          name="undertakingForJobWorkDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="undertakingForJobWorkRemarks"  maxlength="10000" id="undertakingForJobWorkRemarksId" 
                                                          name="undertakingForJobWorkRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Affidavit on Prescribed format
                                                      <a href="./downloadFoodReeferVehiclesDocument?fileName=${documentsReeferVehicles.affidavitonnonjudicialstamppaperofrs100}" target="_blank">View</a>
                                                      </label></td>
                                                      
                                                      <td>
                                                         <form:input path="affidavitOnPrescribedFormatDesc"  maxlength="250" id="affidavitOnPrescribedFormatDescId"
                                                          name="affidavitOnPrescribedFormatDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="affidavitOnPrescribedFormatRemarks" maxlength="10000"  id="affidavitOnPrescribedFormatRemarksId" 
                                                          name="affidavitOnPrescribedFormatRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Bank Claim Certificate
                                                      <a href="./downloadFoodReeferVehiclesDocument?fileName=${documentsReeferVehicles.bankclaimnbspcertificatenbspforreefervehiclesmobileprecooling}" target="_blank">View</a></label></td>
                                                      
                                                      <td>
                                                         <form:input path="bankClaimCertificateDesc" maxlength="250"  id="bankClaimCertificateDescId"
                                                          name="bankClaimCertificateDesc" value="" type="text" class="form-control"/>
                                                      </td>
                                                      <td>
                                                           <form:textarea path="bankClaimCertificateRemarks"  maxlength="10000" id="bankClaimCertificateRemarksId" 
                                                          name="bankClaimCertificateRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                                        
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                  
                                
                                
                                  
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title  mt-4">INTEREST SUBSIDY CALCULATION</h4>
                                        <form:textarea path="interestSubsudy"  maxlength="10000" id="interestSubsudyId" name="interestSubsudy"
									class="form-control border-info" rows="3"></form:textarea>
                                    </div>
                                </div>
                               
                               
                                
                                
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title  mt-4">Recommendation/Comments</h4>
                                        <form:textarea path="recommendationComments"  maxlength="10000" id="recommendationCommentsId" name="recommendationComments"
									class="form-control border-info" rows="3"></form:textarea>
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
                                    
                                    <a href="./foodviewApplicationDetails?unit_Id=${businessEntityDetailsfood.unit_id}"
									class="common-default-btn mt-3">Back</a>
                                    <div class="col-sm-7 text-right">
                                        <a href="javacript:void(0);" class="common-btn mt-3"><i class="fa fa-edit"></i> Edit</a>
                                   <form:button class="common-btn mt-3">Save</form:button>
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