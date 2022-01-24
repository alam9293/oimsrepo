<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
				<div class="col-md-3 col-lg-2 sidebar-offcanvas pt-3" id="sidebar"
					role="navigation">
					<ul class="nav flex-column pl-1">
						<li class="nav-item"><a class="nav-link"
							href="./foodPolicyDashboard"><i class="fa fa-tachometer"></i>
								Dashboard</a></li>
						<li class="nav-item"><a class="nav-link active"
							href="./foodviewEvaluate"><i class="fa fa-eye"></i> View and
								Evaluate Applications</a></li>
					</ul>
                </div>
                <!--/col-->
                <div class="col-md-9 col-lg-10 mt-4 main mb-4">
                 <h4 class="card-title mb-4 mt-4 text-center">Application for seeking assistance for implementation of Total Quality Management System (TQM) including ISO 14000, ISO 22000, HACCP, GHP & GMP / Patent</h4>
                    <div class="card card-block p-3">
                        <div class="row">
                            <div class="col-sm-5">
                                <a href="./foodProcessingTQM?unit_Id=${businessEntityDetailsfood.unit_id}" class="common-default-btn mt-3">Back</a>
                            </div>
                            <div class="col-sm-7 text-right">
                                <a href="#EvaluationAuditTrail" class="text-info mr-3" data-toggle="modal">Evaluation Audit Trail</a>
                                <a href="javacript:void(0);" class="common-btn mt-3"><i class="fa fa-edit"></i> Edit</a>
                            </div>
                        </div>
                        
                        <form:form modelAttribute="foodViewEvaluateTQM" method="post"
							action="savefoodEvaluateViewTQM" class="mt-4" enctype="multipart/form-data">
                        <div class="row mt-5">
                            <div class="col-sm-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Name of Organization</th>
                                                <th>Registered Address</th>
                                                <th>Project Name</th>
                                              
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td> <form:input path="nameOfOrganization" id="nameOfOrganizationId" name="nameOfOrganization" value="${businessEntityDetailsfood.nameofcompany}" type="text"
                                                        readonly="true" class="form-control"/></td>
                                                        
                                                <td> <form:input path="registeredAddress" id="registeredAddressId" name="registeredAddress" value="${businessEntityDetailsfood.addressofcompany}" type="text"
                                                        readonly="true" class="form-control"/></td>
                                                        
                                                <td> <form:input path="projectName" id="projectNameId" name="projectName" value="${foodTQMprojectdetails.nameoftheproject}" type="text"
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
                              <td style="width: 33%;"><label class="table-heading">Name of the firm </label></td>
                              <td style="width: 33%;">
                              <form:input path="nameOfTheFirm" id="nameOfTheFirmId" name="nameOfTheFirm" value="${businessEntityDetailsfood.nameofcompany}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="nameOfTheFirmObsev" id="nameOfTheFirmObsevId" name="nameOfTheFirmObsev"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>  
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Location/Area of the Manufacturing unit</label></td>
                              <td style="width: 33%;">
                              <form:input path="locationOfManufactUnit" id="locationOfManufactUnitId" name="locationOfManufactUnit" value="${foodTQMprojectdetails.locationareaofthemanufacturingunit}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="locationOfManufactUnitObserv" id="locationOfManufactUnitObservId" name="locationOfManufactUnitObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Name of Promoter </label></td>
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
                              <td style="width: 33%;"><label class="table-heading">Type of organization</label></td>
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
                              <td style="width: 33%;"><label class="table-heading">PAN No.</label></td>
                              <td style="width: 33%;">
                              <form:input path="panNo" id="panNoId" name="panNo" value="${businessEntityDetailsfood.pancardoffirmproprietor}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="panNoObserv" id="panNoObservId" name="panNoObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Registered Office of the firm</label></td>
                              <td style="width: 33%;">
                              <form:input path="registeredOfficeOfTheFirm" id="registeredOfficeOfTheFirmId" name="registeredOfficeOfTheFirm" 							value="${businessEntityDetailsfood.addressofcompany}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="registeredOfficeOfTheFirmObserv" id="registeredOfficeOfTheFirmObservId" name="registeredOfficeOfTheFirmObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>  
                          <tr>
                             <td colspan="3"><strong>Registered product details</strong></td>
                       </tr>
                        <table class="table table-stripped table-bordered directors-table" id="customFields">
                        <thead>
                            <tr>
                                <th>Name of the Products</th>
                                <th>By-products</th>
                               <th>Brand Name</th>
                                
                            </tr>
                        </thead>
                        <c:forEach var="listpd" items="${productDetail}" varStatus="counter">
								
																		<tr>
																			<td><input type="text" class="form-control"
																				id="" value="${listpd.nameoftheproducts}"
																				disabled="" name=""></td>
																			<td><input type="text" class="form-control"
																				id="" value="${listpd.byproducts}"
																				disabled="" name=""></td>
																				<td><input type="text" class="form-control"
																				id="" value="${listpd.brandname}"
																				disabled="" name=""></td>
																		</tr>
																	</c:forEach>
                    </table>
                     <td>
                              <form:textarea path="registeredProductDetailsObserv" id="registeredProductDetailsObservId" name="registeredProductDetailsObserv"
								class="form-control border-info" rows="2" placeholder="Observations"></form:textarea>
                              </td>
                          </tr> 
                        <table class="table table-bordered">
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Location the firm/unit</label></td>
                              <td style="width: 33%;">
                              <form:input path="locationTheFirm" id="locationTheFirmId" name="locationTheFirm" value="${foodTQMprojectdetails.locationareaofthemanufacturingunit}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="locationTheFirmObserv" id="locationTheFirmObservId" name="locationTheFirmObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          </table>
                            <table class="table table-bordered">
                          <tr>
                             <td colspan="3"><strong>Capacity of Installed Plant & Manufacturing Unit. (in MT)</strong></td>
                       </tr>
                        <table class="table table-stripped table-bordered directors-table" id="customFields">
                        <thead>
                            <tr>
                                <th>Capacity</th>
                                <th>Per Day</th>
                               <th>Per Year</th>
                                
                            </tr>
                        </thead>
                         <c:forEach var="listcu" items="${capacityofpnmUnit}"
								varStatus="counter">
                            <tr>
                              
                               
                                <td>Metric Ton</td>
                                 <td><input type="text" class="form-control" id="" value="${listcu.perdaymetricton}" disabled="" name=""></td>
                                  <td><input type="text" class="form-control" id="" value="${listcu.peryearmetricton}" disabled="" name=""></td>
                                
                            </tr>
                             <tr>
                              
                               
                                <td>Metric Litre</td>
                                 <td><input type="text" class="form-control" id="" value="${listcu.perdaymetriclitre}" disabled="" name=""></td>
                                  <td><input type="text" class="form-control" id="" value="${listcu.peryearmetriclitre}" disabled="" name=""></td>
                                
                            </tr>
                            </c:forEach>
                    </table>
                              <td>
                              <form:textarea path="capacityOfInstalledPlantManufactUnitObserv" id="capacityOfInstalledPlantManufactUnitObservId" name="capacityOfInstalledPlantManufactUnitObserv"
								class="form-control border-info" placeholder="Observation" rows="2"></form:textarea>
                              </td>
                          </tr>
                          </table>
                            <table class="table table-bordered">
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Name of Consultant Agency </label></td>
                              <td style="width: 33%;">
                              <form:input path="nameOfConsultAgency" id="nameOfConsultAgencyId" name="nameOfConsultAgency" value="" type="text"
                              readonly="false" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="nameOfConsultAgencyObserv" id="nameOfConsultAgencyObservId" name="nameOfConsultAgencyObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                        </table>
                          <table class="table table-bordered">
                          <c:forEach var="listpc" items="${projectCost}" varStatus="counter">
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Fee Charged by Certifying Agency  </label></td>
                              <td style="width: 33%;">
                              <form:input path="feeChargedByCertifyAgency" id="feeChargedByCertifyAgencyId" name="feeChargedByCertifyAgency" value="${listpc.costfeechargedbycertifyingagency}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="feeChargedByCertifyAgencyObserv" id="feeChargedByCertifyAgencyObservId" name="feeChargedByCertifyAgencyObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          </c:forEach> 
                          </table>
                            <table class="table table-bordered">
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Details and Experience of the Consultant </label></td>
                              <td style="width: 33%;">
                              <form:input path="detailsAndExpOfConsult" id="detailsAndExpOfConsult" name="detailsAndExpOfConsult" value="${foodTQMprojectdetails.detailsandexperienceoftheconsultant}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="detailsAndExpOfConsultObserv" id="detailsAndExpOfConsultObservId" name="detailsAndExpOfConsultObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          </table>
                            <table class="table table-bordered"> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Details and Experience of certifying body </label></td>
                              <td style="width: 33%;">
                              <form:input path="detailsAndExpOfCertifyBody" id="detailsAndExpOfCertifyBodyId" name="detailsAndExpOfCertifyBody" value="${foodTQMprojectdetails.detailsandexperienceofcertifyingbody}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="detailsAndExpOfCertifyBodyObserv" id="detailsAndExpOfCertifyBodyObservId" name="detailsAndExpOfCertifyBodyObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          </table>
                            <table class="table table-bordered"> 
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Details of Accreditation body </label></td>
                              <td style="width: 33%;">
                              <form:input path="detailsOfAccreditationBody" id="detailsOfAccreditationBodyId" name="detailsOfAccreditationBody" value="${foodTQMprojectdetails.detailsofaccreditationbody}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="detailsOfAccreditationBodyObserv" id="detailsOfAccreditationBodyObservId" name="detailsOfAccreditationBodyObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                      </table>


                          
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
                                    <form:textarea path="projectSummary" id="projectSummaryId" name="projectSummary"
    								class="form-control border-info" rows="2"></form:textarea>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Cost of Project as per bank appraisal (in lacs)  </h4>
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
                                                        <th>Cost</th>
                                                     
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="listpc" items="${projectCost}" varStatus="counter">
								
                                                  <tr>
                                                      <td><label class="table-heading">Consultancy Fee</label></td>
                                                      <td>
                                                      <form:input path="consultancyFeeCost" id="consultancyFeeCostId" name="consultancyFeeCost" value="${listpc.costconsultantfee}" type="text"
                                                      readonly="true" class="form-control countCOP"/>
                                                      </td>
                                                    
                                                   
                                                      <td>
                                                      <form:textarea path="consultancyFeeRemarks" id="consultancyFeeRemarksId" name="consultancyFeeRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>


                                                  <tr>
                                                      <td><label class="table-heading">Fee charged by certifying  Agency </label></td>
                                                      <td>
                                                      <form:input path="feeChargedBycertifyAgencyCost" id="feeChargedBycertifyAgencyCostId" name="feeChargedBycertifyAgencyCost" value="${listpc.costfeechargedbycertifyingagency}" type="text"
                                                      readonly="true" class="form-control countCOP"/>
                                                      </td>
                                                     
                                                      <td>
                                                      <form:textarea path="feeChargedBycertifyAgencyRemarks" id="feeChargedBycertifyAgencyRemarksId" name="feeChargedBycertifyAgencyRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>


                                                  <tr>
                                                      <td><label class="table-heading">Plant and machinery with <br>reference to GAP analysis report </label></td>
                                                      <td>
                                                      <form:input path="plantAndMachWithReferenceGAPCost" id="plantAndMachWithReferenceGAPCostId" name="plantAndMachWithReferenceGAPCost" value="${listpc.costplantmachinerywithreferencetogapanalysisreport}" type="text"
                                                      readonly="true" class="form-control countCOP"/>
                                                      </td>
                                                   
                                                      <td>
                                                      <form:textarea path="plantAndMachWithReferenceGAPRemarks" id="plantAndMachWithReferenceGAPRemarksId" name="plantAndMachWithReferenceGAPRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>

                                                  <tr>
                                                      <td><label class="table-heading">Any other expenses as per <br>the requirement of GMP/GHP/ISO<br> 14000/ISO 22000/HACCP</label></td>
                                                      <td>
                                                      <form:input path="anyOtherExpensesAsPerReqOfGMPCost" id="anyOtherExpensesAsPerReqOfGMPCostId" name="anyOtherExpensesAsPerReqOfGMPCost" value="${listpc.costanyotherexpensesaspertherequirementofgmpghpiso14000iso22000h}" type="text"
                                                      readonly="true" class="form-control countCOP"/>
                                                      </td>
                                                      
                                                    
                                                      <td>
                                                      <form:textarea path="anyOtherExpensesAsPerReqOfGMPRemarks" id="anyOtherExpensesAsPerReqOfGMPRemarksId" name="anyOtherExpensesAsPerReqOfGMPRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Patent Registration Fee   </label></td>
                                                      <td>
                                                      <form:input path="patentRegisFeeCost" id="patentRegisFeeCostId" name="patentRegisFeeCost" value="${listpc.costpatentregistrationfee}" type="text"
                                                      readonly="true" class="form-control countCOP"/>
                                                      </td>
                                                    
                                                      <td>
                                                      <form:textarea path="patentRegisFeeRemarks" id="patentRegisFeeRemarksId" name="patentRegisFeeRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  
                                                   <tr>
                                                      <td><label class="table-heading">Technical Civil Works (if any) with reference to GAP Study Report</label></td>
                                                      <td>
                                                      <form:input path="technicalCivilWorksCost" id="technicalCivilWorksCostId" name="technicalCivilWorksCost" value="${listpc.costtechnicalcivilworkswithreferencetogapstudyreport}" type="text"
                                                      readonly="true" class="form-control countCOP"/>
                                                      </td>
                                                    
                                                      <td>
                                                      <form:textarea path="technicalCivilWorksRemarks" id="technicalCivilWorksRemarksId" name="technicalCivilWorksRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                   <tr>
                                                      <td><label class="table-heading">Others</label></td>
                                                      <td>
                                                      <form:input path="othersCost" id="othersCostId" name="othersCost" value="" type="text"
                                                      readonly="false" class="form-control countCOP"/>
                                                      </td>
                                                    
                                                      <td>
                                                      <form:textarea path="othersRemarks" id="othersRemarksId" name="othersRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>

                                                  <tr>
                                                      <td><label class="table-heading">Total  </label></td>
                                                      <td>
                                                      <form:input path="totalCost" id="totalCostId" name="totalCost" value="" type="text"
                                                      readonly="true" class="form-control countCOPTotal"/>
                                                      </td>
                                                    
                                                      <td>
                                                      <form:textarea path="totalRemarks" id="totalRemarksId" name="totalRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>

                                               </c:forEach>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Means of Finance As per bank appraisal</h4>
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
                                                        <th>Cost</th>
                                                     
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                 <c:forEach var="listmftqm" items="${meansofFinancingTQM}" varStatus="counter">
                                                  <tr>
                                                      <td><label class="table-heading">Equity (Promoter Share)</label></td>
                                                      
                                                      <td>
                                                      <form:input path="mofEquityCost" id="mofEquityCostId" name="mofEquityCost" value="${listmftqm.costpromotersequity}" type="text"
                                                      readonly="true" class="form-control countMOF"/>
                                                      </td>
                                                    
                                                      <td>
                                                      <form:textarea path="mofEquityRemarks" id="mofEquityRemarksId" name="mofEquityRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Term loan </label></td>
                                                      
                                                      <td>
                                                      <form:input path="mofTermLoanCost" id="mofTermLoanCostId" name="mofTermLoanCost" value="${listmftqm.costtermloan}" type="text"
                                                      readonly="true" class="form-control countMOF"/>
                                                      </td>
                                                    
                                                      <td>
                                                      <form:textarea path="mofTermLoanRemark" id="mofTermLoanRemarkId" name="mofTermLoanRemark"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Working Capital</label></td>
                                                      
                                                      <td>
                                                      <form:input path="mofWorkingCapitalCost" id="mofWorkingCapitalCostId" name="mofWorkingCapitalCost" value="${listmftqm.costworkingcapital}" type="text"
                                                      readonly="true" class="form-control countMOF"/>
                                                      </td>
                                                     
                                                      <td>
                                                      <form:textarea path="mofWorkingCapitalRemark" id="mofWorkingCapitalRemarkId" name="mofWorkingCapitalRemark"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Others</label></td>
                                                      
                                                      <td>
                                                      <form:input path="mofOthersCost" id="mofOthersCostId" name="mofOthersCost" value="${listmftqm.costothers}" type="text"
                                                      readonly="true" class="form-control countMOF"/>
                                                      </td>
                                                     
                                                      <td>
                                                      <form:textarea path="mofOthersRemarks" id="mofOthersRemarksId" name="mofOthersRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                     </c:forEach>                   
                                                </tbody>
                                                <tfoot>
                                                    <tr>
                                                    <td><label class="table-heading">Total</label></td>
                                                    
                                                    <th>
                                                    <form:input path="mofTotalCost" id="mofTotalCostId" name="mofTotalCost" value="" type="text"
                                                    readonly="true" class="form-control countMOFTotal"/>
                                                    </th>
                                                   
                                                    <td>
                                                    <form:textarea path="mofTotalRemarks" id="mofTotalRemarksId" name="mofTotalRemarks"
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
                                        <h4 class="card-title mb-4 mt-4">Status of document  :</h4>
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
                                                        <th>Cost</th>
                                                     
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                  <tr>
                                                      <td><label class="table-heading">Application on Prescribed format &nbsp;<a href="">View</a></label></td>
                                                      <td>
                                                      <form:input path="appOnPrescribedFormatCost" id="appOnPrescribedFormatCostId" name="appOnPrescribedFormatCost" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="appOnPrescribedFormatRemarks" id="appOnPrescribedFormatRemarksId" name="appOnPrescribedFormatRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Detailed Project Report&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.detailedprojectreport}">View</a> </label></td>
                                                      
                                                      <td>
                                                      <form:input path="detailedProjectReportCost" id="detailedProjectReportCostId" name="detailedProjectReportCost" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="detailedProjectReportRemarks" id="detailedProjectReportRemarksId" name="detailedProjectReportRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">SSI/IEM/Udyam/Udyog Aadhar No &nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.udyogaadharudyamregistration}">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="udyogAadharCost" id="udyogAadharCostId" name="udyogAadharCost" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="udyogAadharCostRemarks" id="udyogAadharCostRemarksId" name="udyogAadharCostRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">FSSAI License&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.fssailicense}">View</a> </label></td>
                                                      
                                                      <td>
                                                      <form:input path="fSSAILicenseCost" id="fSSAILicenseCostId" name="fSSAILicenseCost" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="fSSAILicenseRemarks" id="fSSAILicenseRemarksId" name="fSSAILicenseRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Bio data/Experience of<br> the consultant&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.biodataexperienceofconsultant}">View</a> </label></td>
                                                      
                                                      <td>
                                                      <form:input path="bioDataCost" id="bioDataCostId" name="bioDataCost" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="bioDataRemark" id="bioDataRemarkId" name="bioDataRemark"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Quotation by Consultant&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.quotationofconsultant}">View</a> </label></td>
                                                      
                                                      <td>
                                                      <form:input path="quotationByConsultCost" id="quotationByConsultCostId" name="quotationByConsultCost" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="quotationByConsultRemarks" id="quotationByConsultRemarksId" name="quotationByConsultRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Consultant Registration details&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.consultantregistrationdetails}">View</a> </label></td>
                                                      
                                                      <td>
                                                      <form:input path="consultRegisDetailsCost" id="consultRegisDetailsCostId" name="consultRegisDetailsCost" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="consultRegisDetailsRemark" id="consultRegisDetailsRemarkId" name="consultRegisDetailsRemark"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>

                                                  <tr>
                                                    <td><label class="table-heading">Quotation from Certification<br> body along with the details <br>of the certification body&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.quotationfromcertificationbodyalongwiththedetailsofthecertifi}">View</a>  </label></td>
                                                    
                                                    <td>
                                                    <form:input path="quotationFrCertiBodyCost" id="quotationFrCertiBodyCostId" name="quotationFrCertiBodyCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="quotationFrCertiBodyRemark" id="quotationFrCertiBodyRemarkId" name="quotationFrCertiBodyRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Accreditation Certificate of Certification Agency from  QCI/FSSAI/National Accreditation<br>for Certification Bodies &nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.divcertificationagencyshouldbenbspaccreditedbytheqciornationa}">View</a>  </label></td>
                                                    
                                                    <td>
                                                    <form:input path="certificationAgencyCost" id="certificationAgencyCostId" name="certificationAgencyCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="certificationAgencyRemark" id="certificationAgencyRemarkId" name="certificationAgencyRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Details of Plant & Machinery<br> as per GAP Study Report counter<br> signed by consultant&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.detailsofplantampmachineryaspergapstudyreportcountersignedbyc}">View</a>   </label></td>
                                                    
                                                    <td>
                                                    <form:input path="detailsOfPlantMachineryAsPerGAPStudyCost" id="detailsOfPlantMachineryAsPerGAPStudyCostId" name="detailsOfPlantMachineryAsPerGAPStudyCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="detailsOfPlantMachineryAsPerGAPStudyRemark" id="detailsOfPlantMachineryAsPerGAPStudyRemarkId" name="detailsOfPlantMachineryAsPerGAPStudyRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>



                                                <tr>
                                                    <td><label class="table-heading">Details of Plant & Machinery <br>quotations duly certified by<br> approved Chartered<br> Engineer (Mech.)&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.detailsofplantampmachinerynbspquotationsdulycertifiedbyapprov}">View</a>  </label></td>
                                                    
                                                    <td>
                                                    <form:input path="detailsOfPlantMachineryAquotDulyCost" id="detailsOfPlantMachineryAquotDulyCostId" name="detailsOfPlantMachineryAquotDulyCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="detailsOfPlantMachineryAquotDulyRemark" id="detailsOfPlantMachineryAquotDulyRemarkId" name="detailsOfPlantMachineryAquotDulyRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label class="table-heading">Details of Technical Civil <br>Works as per GAP Study <br>Report approved by Chartered<br> Engineer (Civil) and Counter <br>signed by consultant&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp}">View</a>  </label></td>
                                                    
                                                    <td>
                                                    <form:input path="detailsOfTechCivilWorksCost" id="detailsOfTechCivilWorksCostId" name="detailsOfTechCivilWorksCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="detailsOfTechCivilWorksRemark" id="detailsOfTechCivilWorksRemarkId" name="detailsOfTechCivilWorksRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label class="table-heading">Gap Study Report as per<br> (Annexure-19)&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.gapstudyreportasper}">View</a> </label></td>
                                                    
                                                    <td>
                                                    <form:input path="gapStudyReportCostAsPerAnnux19Cost" id="gapStudyReportCostAsPerAnnux19CostId" name="gapStudyReportCostAsPerAnnux19Cost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="gapStudyReportAsPerAnnux19Remark" id="gapStudyReportAsPerAnnux19RemarkId" name="gapStudyReportAsPerAnnux19Remark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label class="table-heading">Affidavit as per (Annexure-20)&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.affidavitasper}">View</a>  </label></td>
                                                    
                                                    <td>
                                                    <form:input path="affidevitAsPerAnnux20Cost" id="affidevitAsPerAnnux20CostId" name="affidevitAsPerAnnux20Cost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="affidevitAsPerAnnux20CostRemark" id="affidevitAsPerAnnux20CostRemarkId" name="affidevitAsPerAnnux20CostRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label class="table-heading">Implementation Schedule of <br>the HACCP/ISO 9000/ISO <br>22000/GMP/GHP system <br>Implementation&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.implementationscheduleofthehaccpiso9000iso22000gmpghpsystemim}">View</a>   </label></td>
                                                    
                                                    <td>
                                                    <form:input path="impScheduleOfHACCPCost" id="impScheduleOfHACCPCostId" name="impScheduleOfHACCPCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="impScheduleOfHACCPRemark" id="impScheduleOfHACCPRemarkId" name="impScheduleOfHACCPRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">The applicant organization<br> is required to give on<br> undertaking that the<br> training of their employees <br>of implementation of ISO 14000/<br>ISO 22000/HACCP/GMP Patent <br>will be obtained from <br>the consultant&nbsp;
                                                    <a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.theapplicantorganizationisrequiredtogiveanundertakingthatthet}">View</a>  </label></td>
                                                    
                                                    <td>
                                                    <form:input path="theApplicantOrgIsReqCost" id="theApplicantOrgIsReqCostId" name="theApplicantOrgIsReqCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="theApplicantOrgIsReqRemark" id="theApplicantOrgIsReqRemarkId" name="theApplicantOrgIsReqRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                            
                                                
                                                <tr>
                                                    <td><label class="table-heading">List of existing Plant & Machinery <br>and Quality control facilities with cost&nbsp;
                                                    <a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.listofexistingplantampmachineryandqualitycontrolfacilitieswit}">View</a>   </label></td>
                                                    
                                                    <td>
                                                    <form:input path="listOfExistPlantMachCost" id="listOfExistPlantMachCostId" name="listOfExistPlantMachCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="listOfExistPlantMachRemark" id="listOfExistPlantMachRemarkId" name="listOfExistPlantMachRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                            

                                                <tr>
                                                    <td><label class="table-heading">Process of manufacture <br>(with Flow Chart)&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.processofmanufacture}">View</a> </label></td>
                                                    
                                                    <td>
                                                    <form:input path="procesOfManufactCost" id="procesOfManufactCostId" name="procesOfManufactCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="procesOfManufactRemark" id="procesOfManufactRemarkId" name="procesOfManufactRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                
                                                <tr>
                                                    <td><label class="table-heading">Patent Certificate issued by <br>Competent Authority&nbsp;
                                                    <a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.patentcertificateissuedbycompetentauthority}">View</a>  </label></td>
                                                    
                                                    <td>
                                                    <form:input path="patentCertiIssuedByCompetentAuthCost" id="patentCertiIssuedByCompetentAuthCostId" name="patentCertiIssuedByCompetentAuthCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="patentCertiIssuedByCompetentAuthRemark" id="patentCertiIssuedByCompetentAuthRemarkId" name="patentCertiIssuedByCompetentAuthRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                
                                                <tr>
                                                    <td><label class="table-heading">Declaration upload&nbsp;<a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.declarationupload}">View</a> </label></td>
                                                    
                                                    <td>
                                                    <form:input path="declarationUploadCost" id="declarationUploadCostId" name="declarationUploadCost" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="declarationUploadRemark" id="declarationUploadRemarkId" name="declarationUploadRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                            
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                </div>
                           
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Details of payment for the project-</h4>
                                    </div>
                                </div>
                                <div class="table-responsive mt-3">
                                      <table class="table table-stripped table-bordered" id="BreakUpTable1">
                                          <thead>
                                              <tr>
                                                  <th>S.No</th>
                                                  <th>Details</th>
                                                  <th>Amount 
                                                    (in Lacs)
                                                    </th>
                                                  <th>T.D.S.</th>
                                                 
                                                 <th>Total Paid Amount</th>
                                                  <th style="width: 17%;">Eligible cost (in Lacs)</th>
                                              </tr>
                                          </thead>
                                          <tbody class="add-from-here1">
                                       
                                              <tr>
                                                  <td>1</td>
                                                  <td>Fee and Testing Chargs of Accrditation Body</td>
                                                   
                                                  
                                                  <td>
                                                  <form:input path="feeAndTestingChargsAmtInLacs" id="feeAndTestingChargsAmtInLacsId" name="feeAndTestingChargsAmtInLacs" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="feeAndTestingChargsTDS" id="feeAndTestingChargsTDSId" name="feeAndTestingChargsTDS" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                  
                                                   <td>
                                                  <form:input path="feeAndTestingChargsTtlPaidAmt" id="feeAndTestingChargsTtlPaidAmtId" name="feeAndTestingChargsTtlPaidAmt" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                 
                                                  <td>
                                                  <form:input path="feeAndTestingChargsEligibleCost" id="feeAndTestingChargsEligibleCostId" name="feeAndTestingChargsEligibleCost" value="" type="text"
                                                  readonly="false" class="form-control countEligCost"/>
                                                  </td>
                                                 
                                              </tr>
                                               <tr>
                                                  <td>2</td>
                                                  <td>Cunsultancy Fee</td>
                                                   
                                                  
                                                  <td>
                                                  <form:input path="cunsultancyFeeAmtInLacs" id="cunsultancyFeeAmtInLacsId" name="cunsultancyFeeAmtInLacs" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="cunsultancyFeeTDS" id="cunsultancyFeeTDSId" name="cunsultancyFeeTDS" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                  
                                                   <td>
                                                  <form:input path="cunsultancyFeeTtlPaidAmt" id="cunsultancyFeeTtlPaidAmtId" name="cunsultancyFeeTtlPaidAmt" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                 
                                                  <td>
                                                  <form:input path="cunsultancyFeeEligibleCost" id="cunsultancyFeeEligibleCostId" name="cunsultancyFeeEligibleCost" value="" type="text"
                                                  readonly="false" class="form-control countEligCost"/>
                                                  </td>
                                                 
                                              </tr>
                                               <tr>
                                                  <td>3</td>
                                                  <td>Fee Charged By Certifying Agency</td>
                                                   
                                                  
                                                  <td>
                                                  <form:input path="feeChargedByCertiAgencyAmtInLacs" id="feeChargedByCertiAgencyAmtInLacsId" name="feeChargedByCertiAgencyAmtInLacs" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="feeChargedByCertiAgencyTDS" id="feeChargedByCertiAgencyTDSId" name="feeChargedByCertiAgencyTDS" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                  
                                                   <td>
                                                  <form:input path="feeChargedByCertiAgencyTtlPaidAmt" id="feeChargedByCertiAgencyTtlPaidAmtId" name="feeChargedByCertiAgencyTtlPaidAmt" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                 
                                                  <td>
                                                  <form:input path="feeChargedByCertiAgencyEligibleCost" id="feeChargedByCertiAgencyEligibleCostId" name="feeChargedByCertiAgencyEligibleCost" value="" type="text"
                                                  readonly="false" class="form-control countEligCost"/>
                                                  </td>
                                                 
                                              </tr>
                                               <tr>
                                                  <td>4</td>
                                                  <td>Cost of Plant & Machinary</td>
                                                   
                                                  
                                                  <td>
                                                  <form:input path="costOfPlantMachAmtInLacs" id="costOfPlantMachAmtInLacsId" name="costOfPlantMachAmtInLacs" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="costOfPlantMachTDS" id="costOfPlantMachTDSId" name="costOfPlantMachTDS" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                  
                                                   <td>
                                                  <form:input path="costOfPlantMachTtlPaidAmt" id="costOfPlantMachTtlPaidAmtId" name="costOfPlantMachTtlPaidAmt" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                 
                                                  <td>
                                                  <form:input path="costOfPlantMachEligibleCost" id="costOfPlantMachEligibleCostId" name="costOfPlantMachEligibleCost" value="" type="text"
                                                  readonly="false" class="form-control countEligCost"/>
                                                  </td>
                                                 
                                              </tr>
                                              
                                              <tr>
                                                  <td>5</td>
                                                  <td>Any Other Expences as requirement of GMP/GHP/ISO/HACCP</td>
                                                   
                                                  
                                                  <td>
                                                  <form:input path="anyOtherExpencesAmtInLacs" id="anyOtherExpencesAmtInLacsId" name="anyOtherExpencesAmtInLacs" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="anyOtherExpencesTDS" id="anyOtherExpencesTDSId" name="anyOtherExpencesTDS" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                  
                                                   <td>
                                                  <form:input path="anyOtherExpencesTtlPaidAmt" id="anyOtherExpencesTtlPaidAmtId" name="anyOtherExpencesTtlPaidAmt" value="" type="text"
                                                  readonly="false" class="form-control"/>
                                                  </td>
                                                 
                                                  <td>
                                                  <form:input path="anyOtherExpencesEligibleCost" id="anyOtherExpencesEligibleCostId" name="anyOtherExpencesEligibleCost" value="" type="text"
                                                  readonly="false" class="form-control countEligCost"/>
                                                  </td>
                                                 
                                              </tr>
                                             
                                          </tbody>
                                          <tfoot>
                                             <%--  <tr>
                                                  <td colspan="4" class="text-right"><strong>Total:</strong></td>
                                                  <!-- <td> <form:input path="projectProfileAppId" id="ProjectProfileAppId" name="projectProfileAppId" value="" type="text"
                                                          readonly="true" class="form-control"/></td>
                                                  <td> <form:input path="projectProfileAppId" id="ProjectProfileAppId" name="projectProfileAppId" value="" type="text"
                                                          readonly="true" class="form-control"/></td> -->
                                                  <td> <form:input path="ttlDetailsOfPaymentEligCost" id="ttlDetailsOfPaymentEligCostId" name="ttlDetailsOfPaymentEligCost" value="" type="text"
                                                          readonly="true" class="form-control countEligCostTotal"/></td>
                                              </tr> --%>
                                              <!-- <tr>
                                                  <td colspan="5">
                                                      <button type="button" class="btn btn-outline-success add-row-break-up-new"><i class="fa fa-plus"></i> Add More Items</button>
                                                  </td>
                                              </tr> -->
                                          </tfoot>
                                      </table>
                                  </div>
                                  <div class="mt-4">
                                    <div class="without-wizard-steps">
                                       
                                    <form:textarea path="detailsOfPaymentRemarks" id="detailsOfPaymentRemarksId" name="detailsOfPaymentRemarks"
    								class="form-control border-info" rows="2"></form:textarea>
                                    </div>
                                </div>
                           
                              
                            
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Eligible Grant amount:  </h4>
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
                                                  <td><p>Eligible cost </p></td>
                                                  <td> <form:input path="eligibleCost" id="eligibleCostId" name="eligibleCost" value="" type="text"
                                                          readonly="true" class="form-control"/></td>
                                                </tr>
                                                <tr>
                                                  <td><p>Eligibility of Grant @ 50% </p></td>
                                                  <td> <form:input path="eligibilityOfGrant50Percent" id="eligibilityOfGrant50PercentId" name="eligibilityOfGrant50Percent" value="" type="text"
                                                          readonly="true" class="form-control"/></td>
                                                </tr>
                                                <tr>
                                                  <td><p>Max. Eligible Grant </p></td>
                                                  <td> <form:input path="maxEligibleGrant" id="maxEligibleGrantId" name="maxEligibleGrant" value="" type="text"
                                                          readonly="true" class="form-control"/></td>
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
                                           
                                        <form:textarea path="eligibleGrantAmountRemarks" id="eligibleGrantAmountRemarksId" name="eligibleGrantAmountRemarks"
        								class="form-control border-info" rows="2"></form:textarea>
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
                                        <a href="./foodProcessingTQM?unit_Id=${businessEntityDetailsfood.unit_id}" class="common-default-btn mt-3">Back</a>
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
    function TotalcountCOP() {
		var sum = 0;
		$(".countCOP").each(function() {
			sum += +$(this).val();
		});
		$(".countCOPTotal").val(sum);
	}

	$(document).ready(TotalcountCOP);
	$(document).on("keyup", TotalcountCOP);
	$(document).on("change", TotalcountCOP);

	 function TotalcountMOF() {
			var sum = 0;
			$(".countMOF").each(function() {
				sum += +$(this).val();
			});
			$(".countMOFTotal").val(sum);
		}

		$(document).ready(TotalcountMOF);
		$(document).on("keyup", TotalcountMOF);
		$(document).on("change", TotalcountMOF);

		 function TotalcountEligCost() {
				var sum = 0;
				$(".countEligCost").each(function() {
					sum += +$(this).val();
				});
				$(".countEligCostTotal").val(sum);
			}

			$(document).ready(TotalcountEligCost);
			$(document).on("keyup", TotalcountEligCost);
			$(document).on("change", TotalcountEligCost);

		 function eligibcost() {
				var detailsOfPaymentEligibleCost = document
						.getElementById('ttlDetailsOfPaymentEligCostId').value;
				
				var grantAmtEligibleTQM = detailsOfPaymentEligibleCost * 0.050;

				if (detailsOfPaymentEligibleCost > 0 && !detailsOfPaymentEligibleCost == '') {
					$('#eligibleCostId').val(detailsOfPaymentEligibleCost);
					$('#eligibilityOfGrant50PercentId').val(grantAmtEligibleTQM);
					 
				}
				if (grantAmtEligibleTQM >= 5000000) {
					
					document.getElementById("maxEligibleGrantId").value = "5000000";
				
				} else {
					document.getElementById("maxEligibleGrantId").value = grantAmtEligibleTQM;
					
				}
		    }
				$(document).ready(eligibcost);//eligibleCostId, eligibilityOfGrant50PercentId, 
				$(document).on("change", eligibcost);
				
    </script>
</body>

</html>