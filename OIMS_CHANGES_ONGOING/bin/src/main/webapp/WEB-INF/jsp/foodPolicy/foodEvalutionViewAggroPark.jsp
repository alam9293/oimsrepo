<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Evaluation View</title>
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <!-- Fonts -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
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
              <a class="nav-link active" href="#"><i class="fa fa-user"></i> User Name</a>
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
                 <h4 class="card-title mb-4 mt-4 text-center">Capital Subsidy for Mega Food Park/Agro Processing Cluster Sanctioned by GOI under Pradhan Mantri Kishan Sampadda Yojana</h4>
                    <div class="card card-block p-3">
                        <div class="row">
                            <div class="col-sm-5">
                                <a href="./foodViewApplication_MFP_PMKSY?unit_Id=${businessEntityDetailsfood.unit_id}" class="common-default-btn mt-3">Back</a>
                            </div>
                            <div class="col-sm-7 text-right">
                                <a href="#EvaluationAuditTrail" class="text-info mr-3" data-toggle="modal">Evaluation Audit Trail</a>
                                <a href="javacript:void(0);" class="common-btn mt-3"><i class="fa fa-edit"></i> Edit</a>
                            </div>
                        </div>
                         <form:form modelAttribute="foodViewEvaluateAggroPark" method="post"
							action="savefoodEvaluateApplicationAggroPark" class="mt-4" enctype="multipart/form-data">
                        <div class="row mt-5">
                            <div class="col-sm-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Name of Organization  </th>
                                                <th>Registered Address</th>
                                                <th>Project Name</th>
                                                <th>Proposed Site Address</th>
                                              
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td> <form:input path="nameOfOrganization" id="nameOfOrganizationId" name="nameOfOrganization" value="${businessEntityDetailsfood.nameofcompany}" type="text"
                              readonly="true" class="form-control"/></td>
                              
                                                <td> <form:input path="registeredAddress" id="registeredAddressId" name="registeredAddress" value="${businessEntityDetailsfood.addressofcompany}" type="text"
                              readonly="true" class="form-control"/></td>
                              
                                                <td> <form:input path="projectName" id="projectNameId" name="projectName" value="${projectandproposedemploymentdetails.nameoftheproject}" type="text"
                              readonly="true" class="form-control"/></td>
                              
                                                <td> <form:input path="proposedSiteAddress" id="proposedSiteAddressId" name="proposedSiteAddress" value="${projectandproposedemploymentdetails.locationareaoftheproject}" type="text"
                              readonly="true" class="form-control"/></td>
                                               
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
                              <form:input path="projectProfileAppId" id="projectProfileAppIdid" name="projectProfileAppId" value="" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="projectProfileAppIdObserv" id="projectProfileAppIdObservId" name="projectProfileAppIdObserv" class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                              <td><label class="table-heading">Proposal submission date</label></td>
                              <td>
                                  <form:input path="proposalSubmissionDate" id="proposalSubmissionDateId" name="proposalSubmissionDate" value="" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="proposalSubmissionDateObserv" id="proposalSubmissionDateObservId" name="proposalSubmissionDateObserv" class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Name of Promoter</label></td>
                              <td style="width: 33%;">
                                  <form:input path="nameOfPromoter" id="nameOfPromoterId" name="nameOfPromoter" value="${businessEntityDetailsfood.nameofpromoter}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="nameOfPromoterObserv" id="nameOfPromoterObservId" name="nameOfPromoterObserv" class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>  
                       
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Type of organization </label></td>
                              <td style="width: 33%;">
                                  <form:input path="typeOfOrganization" id="typeOfOrganizationId" name="typeOfOrganization" value="${businessEntityDetailsfood.typeoforganization}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="typeOfOrganizationObserv" id="typeOfOrganizationObservId" name="typeOfOrganizationObserv" class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Date of incorporation</label></td>
                              <td style="width: 33%;">
                                  <form:input path="dateOfIncorporation" id="dateOfIncorporationId" name="dateOfIncorporation" value="${corformatdate}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="dateOfIncorporationObsev" id="dateOfIncorporationObsevId" name="dateOfIncorporationObsev" class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Availability of land </label></td>
                              <td style="width: 33%;">
                                  <form:input path="availabilityOfLand" id="availabilityOfLandId" name="availabilityOfLand" value="" type="text"
                              readonly="false" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="availabilityOfLandObserv" id="availabilityOfLandObservId" name="availabilityOfLandObserv" class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">PAN</label></td>
                              <td style="width: 33%;">
                                   <form:input path="panNo" id="panNoId" name="panNo" value="${businessEntityDetailsfood.pancardoffirmproprietor}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="panNoObserv" id="panNoObservId" name="panNoObserv" class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>  
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">GST</label></td>
                              <td style="width: 33%;">
                                  <form:input path="GSTNo" id="GSTNoId" name="GSTNo" value="${businessEntityDetailsfood.gstin}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="GSTNoObserv" id="GSTNoObservId" name="GSTNoObserv" class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">SSI/IEM/Udyam/Udyog Aadhar No:</label></td>
                              <td style="width: 33%;">
                                  <form:input path="udyogAadharNo" id="udyogAadharNoId" name="udyogAadharNo" value="" type="text"
                              readonly="false" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="udyogAadharNoObserv" id="udyogAadharNoObservId" name="udyogAadharNoObserv" class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                        
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Name of SPV  </label></td>
                              <td style="width: 33%;">
                                    <form:input path="nameOfSPV" id="nameOfSPVId" name="nameOfSPV" value="" type="text" readonly="false" class="form-control"/>
                              </td>
                              <td>
                                  <form:textarea path="nameOfSPVObserv" id="nameOfSPVObservId" name="nameOfSPVObserv" class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                        
                          <tr>
                            <td style="width: 33%;"><label class="table-heading">Name & Address of PMA/PMC   </label></td>
                            <td style="width: 33%;">
                                  <form:input path="nameAndAddressOfPMA" id="nameAndAddressOfPMAId" name="nameAndAddressOfPMA" value="" type="text" readonly="false" class="form-control"/>
                            </td>
                            <td>
                                <form:textarea path="nameAndAddressOfPMAObserv" id="nameAndAddressOfPMAObservId" name="nameAndAddressOfPMAObserv" class="form-control border-info" rows="2"></form:textarea>
                            </td>
                        </tr>
                      

                        <tr>
                            <td style="width: 33%;"><label class="table-heading">Annual Turn over of the company </label></td>
                            <td style="width: 33%;">
                                  <form:input path="annualTurnOverOfCompany" id="annualTurnOverOfCompanyId" name="annualTurnOverOfCompany" value="" type="text" readonly="false" class="form-control"/>
                            </td>
                            <td>
                                <form:textarea path="annualTurnOverOfCompanyObserv" id="annualTurnOverOfCompanyObservId" name="annualTurnOverOfCompanyObserv" class="form-control border-info" rows="2"></form:textarea>
                            </td>
                        </tr>
                      

                        <tr>
                            <td style="width: 33%;"><label class="table-heading">Amount of Term Loan Sanctioned   </label></td>
                            <td style="width: 33%;">
                            <c:forEach var="bdList" items="${bankTermLoanDetailsFMP}" varStatus="counter">
																									
                                  <form:input path="amountOfTermLoanSanctioned" id="amountOfTermLoanSanctionedId" name="amountOfTermLoanSanctioned" value="${bdList.detailstotalamountoftermloansanctioned}" type="text" readonly="true" class="form-control"/>
                           </c:forEach>
                            </td>
                            
                            <td>
                                <form:textarea path="amountOfTermLoanSanctionedObserv" id="amountOfTermLoanSanctionedObservId" name="amountOfTermLoanSanctionedObserv" class="form-control border-info" rows="2"></form:textarea>
                            </td>
                            
                        </tr>
                      

                        <tr>
                            <td style="width: 33%;"><label class="table-heading">Date of Sanction  </label></td>
                            <td style="width: 33%;">
                                    <c:forEach var="bdList" items="${bankTermLoanDetailsFMP}" varStatus="counter">
                                  <form:input path="dateOfSanction" id="dateOfSanctionId" name="dateOfSanction" value="${bdList.detailsdateofsanction}" type="text" readonly="true" class="form-control"/>
                            </c:forEach>
                            </td>
                            <td>
                                <form:textarea path="dateOfSanctionObserv" id="dateOfSanctionObservId" name="dateOfSanctionObserv" class="form-control border-info" rows="2"></form:textarea>
                            </td>
                        </tr>
                      

                        
                         

                          
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
                            </div>
                            <div class="col-md-12 mt-4">
                                <div class="form-group">
                                    <h4 class="card-title mb-4 mt-4 ">  Project Summary</h4>
                                  <form:textarea path="projectSummary" id="projectSummaryId" name="projectSummary" class="form-control border-info" rows="2"></form:textarea>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Capital Investment (in lacs)    </h4>
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
                                                        <th>Proposed Cost</th>
                                                        <th>Appraised cost</th>
                                                        
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="capInvList" items="${capitalInvestmentDetails}" varStatus="counter">
																									
                                                  <tr>
                                                      <td><label class="table-heading">Land cost </label></td>
                                                      <td>
                                                            <form:input path="landProposedCost" id="landProposedCostId" name="landProposedCost" value="${capInvList.proposedinvestmentlandcost}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="landAppraisedCost" id="landAppraisedCostId" name="landAppraisedCost" value="${capInvList.appraisedcostbybanklandcost}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                   
                                                      <td>
                                                          <form:textarea path="landCostRemarks" id="landCostRemarksId" name="landCostRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>


                                                  <tr>
                                                      <td><label class="table-heading">Central Processing Center (Site Development) </label></td>
                                                      <td>
                                                        <form:input path="centralProcessCenterProposedCost" id="centralProcessCenterProposedCostId" name="centralProcessCenterProposedCost" value="${capInvList.proposedinvestmentcentralprocessingcenter}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="centralProcessCenterAppraisedCost" id="centralProcessCenterAppraisedCostId" name="centralProcessCenterAppraisedCost" value="${capInvList.appraisedcostbybankcentralprocessingcenter}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                      <td>
                                                          <form:textarea path="centralProcessCenterRemarks" id="centralProcessCenterRemarksId" name="centralProcessCenterRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>


                                                  <tr>
                                                      <td><label class="table-heading">Primary Processing Center (Site Development) </label></td>
                                                      <td>
                                                        <form:input path="primaryProcessCenterProposedCost" id="primaryProcessCenterProposedCostId" name="primaryProcessCenterProposedCost" value="${capInvList.proposedinvestmentprimaryprocessingcenter}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="primaryProcessCenterAppraisedCost" id="primaryProcessCenterAppraisedCostId" name="primaryProcessCenterAppraisedCost" value="${capInvList.appraisedcostbybankprimaryprocessingcenter}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                      <td>
                                                          <form:textarea path="primaryProcessCenterRemarks" id="primaryProcessCenterRemarksId" name="primaryProcessCenterRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>

                                                  <tr>
                                                      <td><label class="table-heading">Basic enabling infrastructure (CPC) </label></td>
                                                      <td>
                                                          <form:input path="basicEnablingInfraCPCProposedCost" id="basicEnablingInfraCPCProposedCostId" name="basicEnablingInfraCPCProposedCost" value="${capInvList.proposedinvestmentbasicenablinginfrastructurecentralprocessingce}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                      </td>
                                                      
                                                      <td>
                                                          <form:input path="basicEnablingInfraCPCAppraisedCost" id="basicEnablingInfraCPCAppraisedCostId" name="basicEnablingInfraCPCAppraisedCost" value="${capInvList.appraisedcostbybankbasicenablinginfrastructurecentralprocessingc}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                      <td>
                                                          <form:textarea path="basicEnablingInfraCPCRemarks" id="basicEnablingInfraCPCRemarksId" name="basicEnablingInfraCPCRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Basic enabling infrastructure  (PPC) </label></td>
                                                      <td>
                                                        <form:input path="basicEnablingInfraPPCProposedCost" id="basicEnablingInfraPPCProposedCostId" name="basicEnablingInfraPPCProposedCost" 	value="${capInvList.proposedinvestmentbasicenablinginfrastructureprimaryprocessingce}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                      </td>
                                                      <td>
                                                          <form:input path="basicEnablingInfraPPCAppraisedCost" id="basicEnablingInfraPPCAppraisedCostId" name="basicEnablingInfraPPCAppraisedCost" value="${capInvList.appraisedcostbybankbasicenablinginfrastructureprimaryprocessingc}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                      <td>
                                                          <form:textarea path="basicEnablingInfraPPCRemarks" id="basicEnablingInfraPPCRemarksId" name="basicEnablingInfraPPCRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                 
                                                  <tr>
                                                    <td><label class="table-heading"> Core Infrastructure (CPC) </label></td>
                                                    <td>
                                                      <form:input path="coreInfraCPCProposedCost" id="coreInfraCPCProposedCostId" name="coreInfraCPCProposedCost" value="${capInvList.proposedinvestmentcoreinfrastructurecentralprocessingcenter}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                    </td>
                                                    <td>
                                                          <form:input path="coreInfraCPCAppraisedCost" id="coreInfraCPCAppraisedCostId" name="coreInfraCPCAppraisedCost" value="${capInvList.appraisedcostbybankcoreinfrastructurecentralprocessingcenter}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="coreInfraCPCRemarks" id="coreInfraCPCRemarksId" name="coreInfraCPCRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                 
                                                <tr>
                                                    <td><label class="table-heading">Core Infrastructure (PPC) </label></td>
                                                    <td>
                                                      <form:input path="coreInfraPPCProposedCost" id="coreInfraPPCProposedCostId" name="coreInfraPPCProposedCost" value="${capInvList.proposedinvestmentcoreinfrastructureprimaryprocessingcenter}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                    </td>
                                                    <td>
                                                          <form:input path="coreInfraPPCAppraisedCost" id="coreInfraPPCAppraisedCostId" name="coreInfraPPCAppraisedCost" value="${capInvList.appraisedcostbybankcoreinfrastructureprimaryprocessingcenter}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="coreInfraPPCRemarks" id="coreInfraPPCRemarksId" name="coreInfraPPCRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Non core infrastructure (CPC) </label></td>
                                                    <td>
                                                      <form:input path="nonCoInfraCPCProposedCost" id="nonCoInfraCPCProposedCostId" name="nonCoInfraCPCProposedCost" value="${capInvList.proposedinvestmentnoncoreinfrastructurecentralprocessingcenter}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                    </td>
                                                    <td>
                                                          <form:input path="nonCoInfraCPCAppraisedCost" id="nonCoInfraCPCAppraisedCostId" name="nonCoInfraCPCAppraisedCost" value="${capInvList.appraisedcostbybanknoncoreinfrastructurecentralprocessingcenter}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="nonCoInfraCPCRemarks" id="nonCoInfraCPCRemarksId" name="nonCoInfraCPCRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Non core infrastructure (PPC) </label></td>
                                                    <td>
                                                      <form:input path="nonCoInfraPPCProposedCost" id="nonCoInfraPPCProposedCostId" name="nonCoInfraPPCProposedCost" value="${capInvList.proposedinvestmentnoncoreinfrastructureprimaryprocessingcenter}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                    </td>
                                                    <td>
                                                          <form:input path="nonCoInfraPPCAppraisedCost" id="nonCoInfraPPCAppraisedCostId" name="nonCoInfraPPCAppraisedCost" value="${capInvList.appraisedcostbybanknoncoreinfrastructureprimaryprocessingcenter}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="nonCoInfraPPCRemarks" id="nonCoInfraPPCRemarksId" name="nonCoInfraPPCRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Interest during construction Period </label></td>
                                                    <td>
                                                      <form:input path="interestDuringConstructProposedCost" id="interestDuringConstructProposedCostId" name="interestDuringConstructProposedCost" value="${capInvList.proposedinvestmentinterestduringconstruction}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                    </td>
                                                    <td>
                                                          <form:input path="interestDuringConstructAppraisedCost" id="interestDuringConstructAppraisedCostId" name="interestDuringConstructAppraisedCost" value="${capInvList.appraisedcostbybankinterestduringconstruction}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="interestDuringConstructRemarks" id="interestDuringConstructRemarksId" name="interestDuringConstructRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">PMC Consultancy Fee  </label></td>
                                                    <td>
                                                      <form:input path="pmcConsultancyFeeProposedCost" id="pmcConsultancyFeeProposedCostId" name="pmcConsultancyFeeProposedCost" value="${capInvList.proposedinvestmentprojectmanagementconsultantandconsultancyfee}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                    </td>
                                                    <td>
                                                          <form:input path="pmcConsultancyFeeAppraisedCost" id="pmcConsultancyFeeAppraisedCostId" name="pmcConsultancyFeeAppraisedCost" value="${capInvList.appraisedcostbybankprojectmanagementconsultantandconsultancyfee}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="pmcConsultancyFeeRemarks" id="pmcConsultancyFeeRemarksId" name="pmcConsultancyFeeRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Preliminary and Pre operative expanses  </label></td>
                                                    <td>
                                                      <form:input path="preliminaryAndPreOperativeProposedCost" id="preliminaryAndPreOperativeProposedCostId" name="preliminaryAndPreOperativeProposedCost" value="${capInvList.proposedinvestmentpreliminaryandpreparativeexpenses}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                    </td>
                                                    <td>
                                                          <form:input path="preliminaryAndPreOperativeAppraisedCost" id="preliminaryAndPreOperativeAppraisedCostId" name="preliminaryAndPreOperativeAppraisedCost" value="${capInvList.appraisedcostbybankpreliminaryandpreparativeexpenses}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="preliminaryAndPreOperativeRemarks" id="preliminaryAndPreOperativeRemarksId" name="preliminaryAndPreOperativeRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Margin money for Working Capital  </label></td>
                                                    <td>
                                                      <form:input path="marginMoneyForWorkingCapitalProposedCost" id="marginMoneyForWorkingCapitalProposedCostId" name="marginMoneyForWorkingCapitalProposedCost" value="${capInvList.proposedinvestmentmarginmoneyforworkingcapital}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                    </td>
                                                    <td>
                                                          <form:input path="marginMoneyForWorkingCapitalAppraisedCost" id="marginMoneyForWorkingCapitalAppraisedCostId" name="marginMoneyForWorkingCapitalAppraisedCost" value="${capInvList.appraisedcostbybankmarginmoneyforworkingcapital}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="marginMoneyForWorkingCapitalRemarks" id="marginMoneyForWorkingCapitalRemarksId" name="projectProfileAppIdObserv" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Contingencies  </label></td>
                                                    <td>
                                                      <form:input path="contingenciesProposedCost" id="contingenciesProposedCostId" name="contingenciesProposedCost" value="${capInvList.proposedinvestmentcontingencies}" type="text" readonly="true" class="form-control countPeoposedCost"/>
                                                    </td>
                                                    <td>
                                                          <form:input path="contingenciesAppraisedCost" id="contingenciesAppraisedCostId" name="contingenciesAppraisedCost" value="${capInvList.appraisedcostbybankcontingencies}" type="text" readonly="true" class="form-control countAppraisedCost"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="contingenciesRemarks" id="contingenciesRemarksId" name="contingenciesRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label class="table-heading">Total  </label></td>
                                                    <td>
                                                      <form:input path="totalProposedCost" id="totalProposedCostId" name="totalProposedCost" value="" type="text" readonly="true" class="form-control countTotalPeoposedCost"/>
                                                    </td>
                                                    <td>
                                                          <form:input path="totalAppraisedCost" id="totalAppraisedCostId" name="totalAppraisedCost" value="" type="text" readonly="true" class="form-control countTotalAppraisedCost"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="totalRemarks" id="totalRemarksId" name="totalRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                               </c:forEach>
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
                                                        <th>Proposed Cost </th>
                                                        <th>Appraised </th>
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="mofList" items="${meansofFinancingFMP}" varStatus="counter">
																									
                                                  <tr>
                                                      <td><label class="table-heading">Equity </label></td>
                                                      
                                                      <td>
                                                        <form:input path="mofEquityProposedCost" id="mofEquityProposedCostId" name="mofEquityProposedCost" value="" type="text" readonly="false" class="form-control countMOFProposed"/>
                                                      </td>
                                                      <td>
                                                      <form:input path="mofEquityAppraisedCost" id="mofEquityAppraisedCostId" name="mofEquityAppraisedCost" value="" type="text" readonly="false" class="form-control countMOFAppraised"/>
                                                    </td>
                                                      <td>
                                                          <form:textarea path="mofEquityRemarks" id="mofEquityRemarksId" name="mofEquityRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Term loan  </label></td>
                                                      
                                                      <td>
                                                        <form:input path="mofTermLoanProposedCost" id="mofTermLoanProposedCostId" name="mofTermLoanProposedCost" value="${mofList.proposedinvestmenttermloan}" type="text" readonly="true" class="form-control countMOFProposed"/>
                                                      </td>
                                                      <td>
                                                      <form:input path="mofTermLoanAppraisedCost" id="mofTermLoanAppraisedCostId" name="mofTermLoanAppraisedCost" value="${mofList.asperappraisalofthebanktermloan}" type="text" readonly="true" class="form-control countMOFAppraised"/>
                                                    </td>
                                                      <td>
                                                          <form:textarea path="mofTermLoanRemark" id="mofTermLoanRemarkId" name="mofTermLoanRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Assistance under UPFPIP </label></td>
                                                      
                                                      <td>
                                                        <form:input path="mofAssisUnderUPFPIPProposedCost" id="mofAssisUnderUPFPIPProposedCostId" name="mofAssisUnderUPFPIPProposedCost" value="${mofList.proposedinvestmentassistanceunderupfpip}" type="text" readonly="true" class="form-control countMOFProposed"/>
                                                      </td>
                                                      <td>
                                                      <form:input path="mofAssisUnderUPFPIPAppraisedCost" id="mofAssisUnderUPFPIPAppraisedCostId" name="mofAssisUnderUPFPIPAppraisedCost" value="${mofList.asperappraisalofthebankassistanceunderupfpip}" type="text" readonly="true" class="form-control countMOFAppraised"/>
                                                    </td>
                                                      <td>
                                                          <form:textarea path="mofAssisUnderUPFPIPRemark" id="mofAssisUnderUPFPIPRemarkId" name="mofAssisUnderUPFPIPRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Grant- in- aid from MOFPI</label></td>
                                                      
                                                      <td>
                                                        <form:input path="grantInAidFromMOFPIProposedCost" id="grantInAidFromMOFPIProposedCostId" name="grantInAidFromMOFPIProposedCost" value="${mofList.proposedinvestmentgrantinaidfrommofpi}" type="text" readonly="true" class="form-control countMOFProposed"/>
                                                      </td>
                                                      <td>
                                                      <form:input path="grantInAidFromMOFPIPAppraisedCost" id="grantInAidFromMOFPIPAppraisedCostId" name="grantInAidFromMOFPIPAppraisedCost" value="${mofList.asperappraisalofthebankgrantinaidfrommofpi}" type="text" readonly="true" class="form-control countMOFAppraised"/>
                                                    </td>
                                                      <td>
                                                          <form:textarea path="grantInAidFromMOFPIRemark" id="grantInAidFromMOFPIRemarkId" name="grantInAidFromMOFPIRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                                        
                                               
                                                
                                                    <tr>
                                                    <td><label class="table-heading">Others</label></td>
                                                    
                                                    <th>
                                                      <form:input path="mofOthersProposedCost" id="mofOthersProposedCostId" name="mofOthersProposedCost" value="${mofList.proposedinvestmentothers}" type="text" readonly="true" class="form-control countMOFProposed"/>
                                                    </th>
                                                    <td>
                                                      <form:input path="mofOthersAppraisedCost" id="mofOthersAppraisedCostId" name="mofOthersAppraisedCost" value="${mofList.asperappraisalofthebankothers}" type="text" readonly="true" class="form-control countMOFAppraised"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="mofOthersRemarks" id="mofOthersRemarksId" name="mofOthersRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                
                                              
                                               
                                               
                                                    <tr>
                                                    <td><label class="table-heading">Total</label></td>
                                                    
                                                    <th>
                                                      <form:input path="mofTotalProposedCost" id="mofTotalProposedCostId" name="mofTotalProposedCost" value="" type="text" readonly="true" class="form-control countTotalMOFProposed"/>
                                                    </th>
                                                    <td>
                                                      <form:input path="mofTotalAppraisedCost" id="mofTotalAppraisedCostId" name="mofTotalAppraisedCost" value="" type="text" readonly="true" class="form-control countTotalMOFAppraised"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="mofTotalRemarks" id="mofTotalRemarksId" name="mofTotalRemarks" class="form-control border-info" rows="2"></form:textarea>
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
                                        <h4 class="card-title mb-4 mt-4">Status of document :</h4>
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
                                                        <th>Description </th>
                                                     
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                  <tr>
                                                      <td><label class="table-heading">Incorporation certificate of SPV/PEA  &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.incorporationcertificateofspvpea}">View</a></label></td>
                                                      <td>
                                                        <form:input path="incorporationCertificateOfSPVDesc" id="incorporationCertificateOfSPVDescId" name="incorporationCertificateOfSPVDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="incorporationCertificateOfSPVRemarks" id="incorporationCertificateOfSPVRemarksId" name="incorporationCertificateOfSPVRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Memorandum and article <br>of association & byelaws of the SPV/PEA  &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.memorandumamparticleofassociationandbyelawsofthespvpea}">View</a> </label></td>
                                                      
                                                      <td>
                                                        <form:input path="memorandumAndArticleDesc" id="memorandumAndArticleDescId" name="memorandumAndArticleDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="memorandumAndArticleRemarks" id="memorandumAndArticleRemarksId" name="memorandumAndArticleRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Registered land deed &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.registeredlanddeed}">View</a></label></td>
                                                      
                                                      <td>
                                                        <form:input path="registeredLandDeedDesc" id="registeredLandDeedDescId" name="registeredLandDeedDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="registeredLandDeedRemarks" id="registeredLandDeedRemarksId" name="registeredLandDeedRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                    <td><label class="table-heading">Certificate of Partnership deed  &nbsp;<a href="">View</a></label></td>
                                                    
                                                    <td>
                                                      <form:input path="certificateOfPartnershipDeedDesc" id="certificateOfPartnershipDeedDescId" name="certificateOfPartnershipDeedDesc" value="" type="text" readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="certificateOfPartnershipDeedRemarks" id="certificateOfPartnershipDeedRemarksId" name="certificateOfPartnershipDeedRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Rent Agreement or Registered deed &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.rentagreementregisterednbsp}">View</a> </label></td>
                                                      
                                                      <td>
                                                            <form:input path="rentAgreementDeedDesc" id="rentAgreementDeedDescId" name="rentAgreementDeedDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="rentAgreementDeedRemark" id="rentAgreementDeedRemarkId" name="rentAgreementDeedRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Khasra/Khatauni &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.khasrakhatauni}">View</a> </label></td>
                                                      
                                                      <td>
                                                            <form:input path="khasraKhatauniDesc" id="khasraKhatauniDescId" name="khasraKhatauniDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="khasraKhatauniRemarks" id="khasraKhatauniRemarksId" name="khasraKhatauniRemarks" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Land use Certificate  &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.landusecertificate}">View</a> </label></td>
                                                      
                                                      <td>
                                                        <form:input path="landUseCertificateDesc" id="landUseCertificateDescId" name="landUseCertificateDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="landUseCertificateRemark" id="landUseCertificateRemarkId" name="landUseCertificateRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Detailed Project Report submitted<br> to Ministry of Food Processing <br>Industries, Government of India  &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.detailedprojectreportsubmittedtoministryoffoodprocessingindus}">View</a> </label></td>
                                                      
                                                      <td>
                                                            <form:input path="detailedProjectReportDesc" id="detailedProjectReportDescId" name="detailedProjectReportDesc" value="" type="text" readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="detailedProjectReportRemark" id="detailedProjectReportRemarkId" name="detailedProjectReportRemark" class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>

                                                  <tr>
                                                    <td><label class="table-heading">Term loan Sanction letter   &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.termloansanctionletter}">View</a>  </label></td>
                                                    
                                                    <td>
                                                          <form:input path="termLoanSanctionLetterDesc" id="termLoanSanctionLetterDescId" name="termLoanSanctionLetterDesc" value="" type="text" readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="termLoanSanctionLetterRemark" id="termLoanSanctionLetterRemarkId" name="termLoanSanctionLetterRemark" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Bank Appraisal Report  &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.bankappraisalreport}">View</a>  </label></td>
                                                    
                                                    <td>
                                                          <form:input path="bankAppraisalReportDesc" id="bankAppraisalReportDescId" name="bankAppraisalReportDesc" value="" type="text" readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="bankAppraisalReportRemark" id="bankAppraisalReportRemarkId" name="bankAppraisalReportRemark" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Site Plan of Food Park Approved <br>by Competent Authority  &nbsp;<a href="">View</a>  </label></td>
                                                    
                                                    <td>
                                                          <form:input path="sitePlanOfFoodParkDesc" id="sitePlanOfFoodParkDescId" name="sitePlanOfFoodParkDesc" value="" type="text" readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="sitePlanOfFoodParkRemark" id="sitePlanOfFoodParkRemarkId" name="sitePlanOfFoodParkRemark" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">CA Certificate for Project<br> Cost & Means of finance<br> Annexure-A/4 &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.annexurea4}">View</a>  </label></td>
                                                    
                                                    <td>
                                                          <form:input path="caCertificateForProjectCostDesc" id="caCertificateForProjectCostDescId" name="caCertificateForProjectCostDesc" value="" type="text" readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="caCertificateForProjectCostRemark" id="caCertificateForProjectCostRemarkId" name="caCertificateForProjectCostRemark" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label class="table-heading">Affidavit from promoter <br>on 100 Rupees Non Judicial <br>Stamp Paper Annexure-A/10  &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.annexurea10}">View</a>  </label></td>
                                                    
                                                    <td>
                                                          <form:input path="affidavitFromPromoterDesc" id="affidavitFromPromoterDescId" name="affidavitFromPromoterDesc" value="" type="text" readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="affidavitFromPromoterDescRemark" id="affidavitFromPromoterDescRemarkId" name="affidavitFromPromoterDescRemark" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label class="table-heading">Bank Certificate NOC <br>Annexure-A/11&nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.annexurea11}">View</a>  </label></td>
                                                    
                                                    <td>
                                                          <form:input path="bankCertificateNOCDesc" id="bankCertificateNOCDescId" name="bankCertificateNOCDesc" value="" type="text" readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="bankCertificateNOCDescRemark" id="bankCertificateNOCDescRemarkId" name="bankCertificateNOCDescRemark" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label class="table-heading">Sanction letter issued by<br> Ministry of Food Processing <br>Industries, Government of India   &nbsp;<a href="./downloadfoodDocumentFMP_PMKSY?fileName=${foodDocumentFMP_PMKSY.sanctionedletterissuedbyministryoffoodprocessingindustriesgov}">View</a>  </label></td>
                                                    
                                                    <td>
                                                          <form:input path="sanctionLetterIssuedByMinistryDesc" id="sanctionLetterIssuedByMinistryDescId" name="sanctionLetterIssuedByMinistryDesc" value="" type="text" readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="sanctionLetterIssuedByMinistryRemark" id="sanctionLetterIssuedByMinistryRemarkId" name="sanctionLetterIssuedByMinistryRemark" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                
                                                 <tr>
                                                    <td><label class="table-heading">SSI/IEM/Udyam/Udyog Aadhar No  &nbsp;<a href="#">View</a>  </label></td>
                                                    
                                                    <td>
                                                          <form:input path="sanctionLetterIssuedByMinistryDesc" id="sanctionLetterIssuedByMinistryDescId" name="sanctionLetterIssuedByMinistryDesc" value="" type="text" readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="sanctionLetterIssuedByMinistryRemark" id="sanctionLetterIssuedByMinistryRemarkId" name="sanctionLetterIssuedByMinistryRemark" class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                
                                                
                                               
                                              

                                             
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                </div>
                           
                       
                           
                              
                            
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Calculation for Capital Subsidy 
                                            Capital Subsidy 
                                            :  </h4>
                                    </div>
                                </div>
                                <div class="table-responsive mt-3">
                                      <table class="table table-stripped table-bordered" id="BreakUpTable3">
                                          <!-- <thead>
                                              <tr>
                                                  <th>Particular</th>
                                                  <th>Area (Sqmt.)</th>
                                                  <th>Rate</th>
                                                  <th>Amount</th>
                                                  <th style="width: 17%;">Eligible cost (in lacs)</th>
                                              </tr>
                                          </thead> -->
                                          <tbody class="add-from-here3">
                                              <tr>
                                                  <td><p>Amount Sanctioned by MOFPI GoI for Mega Food Park/Agro Processing Cluster Under PMKSY   </p></td>
                                                 <td> <form:input path="amountSanctionedByMOFPI" id="amountSanctionedByMOFPIId" name="amountSanctionedByMOFPI" value="" type="text" readonly="false" class="form-control"/></td>
                                                </tr>
                                                <tr>
                                                  <td><p>Eligible Additional Capital Subsidy As per UPFPIP-2017  Section 7.1.1 (A) @10% of Grant Sanctioned by MOFPI GoI  </p></td>
                                                <td> <form:input path="eligibleAddCapitalSubsidy" id="eligibleAddCapitalSubsidyId" name="eligibleAddCapitalSubsidy" value="" type="text" readonly="true" class="form-control"/></td>
                                                </tr>
                                                <tr>
                                                  <td><p>Max. Grant </p></td>
                                                 <td><form:input path="maxGrant" id="maxGrantId" name="maxGrant" value="" type="text" readonly="true" class="form-control"/></td>
                                                </tr>
                                             
                                              
                                          </tbody>
                                          <!-- <tfoot>
                                              <tr>
                                                  <td colspan="2">
                                                      <button type="button" class="btn btn-outline-success add-row-break-up-new3"><i class="fa fa-plus"></i> Add More Items</button>
                                                  </td>
                                              </tr>
                                          </tfoot> -->
                                      </table>

                                      <div class="mt-4">
                                        <div class="without-wizard-steps">
                                           
                                          <form:textarea path="appAmtCapitalSubSidyRemarks" id="appAmtCapitalSubSidyRemarksId" name="appAmtCapitalSubSidyRemarks" placeholder="Nodal Officers Remarks" class="form-control border-info" rows="2"></form:textarea>
                                        </div>
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
                                        <a href="./foodViewApplication_MFP_PMKSY?unit_Id=${businessEntityDetailsfood.unit_id}" class="common-default-btn mt-3">Back</a>
                                    </div>
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
    <script type="text/javascript">

    function TotalcountIncentive() {
		var sum = 0;
		$(".countPeoposedCost").each(function() {
			sum += +$(this).val();
		});
		$(".countTotalPeoposedCost").val(sum);
	}

	$(document).ready(TotalcountIncentive);
	$(document).on("keyup", TotalcountIncentive);
	$(document).on("change", TotalcountIncentive);


    function TotalcountIncentiveAppraised() {
		var sum = 0;
		$(".countAppraisedCost").each(function() {
			sum += +$(this).val();
		});
		$(".countTotalAppraisedCost").val(sum);
	}

	$(document).ready(TotalcountIncentiveAppraised);
	$(document).on("keyup", TotalcountIncentiveAppraised);
	$(document).on("change", TotalcountIncentiveAppraised);

	 function TotalcountMOFProposed() {
			var sum = 0;
			$(".countMOFProposed").each(function() {
				sum += +$(this).val();
			});
			$(".countTotalMOFProposed").val(sum);
		}

		$(document).ready(TotalcountMOFProposed);
		$(document).on("keyup", TotalcountMOFProposed);
		$(document).on("change", TotalcountMOFProposed);

		 function TotalcountMOFAppraised() {
				var sum = 0;
				$(".countMOFAppraised").each(function() {
					sum += +$(this).val();
				});
				$(".countTotalMOFAppraised").val(sum);
			}

			$(document).ready(TotalcountMOFAppraised);
			$(document).on("keyup", TotalcountMOFAppraised);
			$(document).on("change", TotalcountMOFAppraised);

    function eligibcost() {
		var detailsOfPaymentEligibleCost = document
				.getElementById('amountSanctionedByMOFPIId').value;
		
		var grantAmtEligibleMegaFoodPark = detailsOfPaymentEligibleCost * 0.10;

		if (detailsOfPaymentEligibleCost > 0 && !detailsOfPaymentEligibleCost == '') {//, 
			$('#eligibleAddCapitalSubsidyId').val(grantAmtEligibleMegaFoodPark);
			$('#maxGrantId').val(grantAmtEligibleMegaFoodPark);
			 
		}
    }
		$(document).ready(eligibcost);
		$(document).on("change", eligibcost);
    </script>
</body>

</html>