
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
                <h4 class="card-title mb-4 mt-4 text-center">Assistance for Market development & brand promotion</h4>
                    <div class="card card-block p-3">
                        <div class="row">
                            <div class="col-sm-5">
                                <a href="./foodProcessingMDBP?unit_Id=${businessEntityDetailsfood.unit_id}" class="common-default-btn mt-3">Back</a>
                            </div>
                            <div class="col-sm-7 text-right">
                                <a href="#EvaluationAuditTrail" class="text-info mr-3" data-toggle="modal">Evaluation Audit Trail</a>
                                <a href="javacript:void(0);" class="common-btn mt-3"><i class="fa fa-edit"></i> Edit</a>
                            </div>
                        </div>
                         <form:form modelAttribute="foodViewEvaluateMDBP" method="post"
							action="savefoodEvaluateApplicationMDBP" class="mt-4" enctype="multipart/form-data">
                        <div class="row mt-5">
                            <div class="col-sm-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Name of Organization</th>
                                                <th>Registered Address</th>
                                                <th>Project Name</th>
                                                <!-- <th>Proposed Site Address</th>
                                                <th>Capacity </th> -->
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td><input id="nameOfOrganizationId"
    													name="nameOfOrganization" value="${businessEntityDetailsfood.nameofcompany}"
    													class="form-control" readonly="true"></input></td>
                                                <td><input id="registeredAddressId"
    													name="registeredAddress" value="${businessEntityDetailsfood.address}"
    													class="form-control" readonly="true"></input></td>
                                                <td><input id="projectNameId"
    													name="projectName" value="${businessEntityDetailsfood.nameofcompany}"
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
                              <form:input path="projectProfileAppId" id="ProjectProfileAppId" name="projectProfileAppId" value="" type="text" readonly="true" class="form-control"/>
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
                              <form:textarea path="proposalSubmissionDateObserv" id="proposalSubmissionDateObservId" name="proposalSubmissionDateObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          <tr>
                            <td colspan="3"><strong>Financial Status (audited balance sheet for last 3 years)</strong></td>
                                                       
                       </tr>
                        <table class="table table-stripped table-bordered directors-table" id="customFields">
                        <thead>
                            <tr>
                               <th>Financial Year</th>
                               <th>Turn over (in Lacs.)</th>
                               
                                
                            </tr>
                        </thead>
                       <c:forEach var="list" items="${fianancStatusList}" varStatus="counter">
																		
																		 <tr>
																			<td><input type="text" name="financialYear" class="form-control"
																				id="" value="${list.financialyear}" disabled=""
																				name=""></td>
																			<td><input type="text" name="turnOverInLacs" class="form-control"
																				id="" value="${list.turnover}" disabled="" name=""></td>
																		</tr>
																	</c:forEach>
                    </table> 

                             <td>
                              <form:textarea path="financialStatusObserv" id="financialStatusObservId" name="financialStatusObserv"
								class="form-control border-info" rows="2"></form:textarea>
                                                        </td>
                          </tr> 
                          <table class="table table-bordered">
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">SSI/IEM  Registration</label></td>
                              <td style="width: 33%;">
                              <form:input path="ssiRegistration" id="ssiRegistrationId" name="ssiRegistration" value="" type="text"
                              readonly="false" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="ssiRegistrationObserv" id="ssiRegistrationObservId" name="ssiRegistrationObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          </table>
                          <table class="table table-bordered">
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Type of organisation like Govt. Institution /Organisation, Industry Association, University, NGO, Co-operative, Entrepreneur, Partnership Firm, Company, etc.</label></td>
                              <td style="width: 33%;">
                              <form:input path="typeOfOrganization" id="typeOfOrganizationId" name="typeOfOrganization" value="${businessEntityDetailsfood.typeoforganization}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="typeOfOrganizationObserv" id="typeOfOrganizationObservId" name="typeOfOrganizationObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                          </table>
                          <table class="table table-bordered">
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Name of Promoters Biodata/Background </label></td>
                              <td style="width: 33%;">
                              <form:input path="promoterBiodata" id="promoterBiodataId" name="promoterBiodata" 	value="${businessEntityDetailsfood.nameofpromoter}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="promoterBiodataObserv" id="promoterBiodataObservId" name="promoterBiodataObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                         </table>

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
                                        <h4 class="card-title mb-4 mt-4">Project profile</h4>
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
                                                        <th>Cost in Rs.</th>
                                                        <th>Details</th>
                                                        <th>Observations By Nodal Officer</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                  <tr>
                                                      <td><label class="table-heading">Transportation/Freight Paid.</label></td>
                                                      <td>
                                                      <form:input path="transportationPaidCost" id="transportationPaidCostId" name="transportationPaidCost" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:input path="transportationPaidContinCorporate" id="transportationPaidContinCorporateId" name="transportationPaidContinCorporate" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="transportationPaidObserv" id="transportationPaidObservId" name="transportationPaidObserv"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">FOB Value  </label></td>
                                                      <td>
                                                      <form:input path="fobValueCost" id="fobValueCostId" name="fobValueCost" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:input path="fobValueContainCorporate" id="fobValueContainCorporateId" name="fobValueContainCorporate" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="fobValueObserv" id="fobValueObservId" name="fobValueObserv"
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
                                                      <form:input path="appOnPrescribedFormatDesc" id="appOnPrescribedFormatDescId" name="appOnPrescribedFormatDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="appOnPrescribedFormatDescRemarks" id="appOnPrescribedFormatDescRemarksId" name="appOnPrescribedFormatDescRemarks"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                 <%--  <tr>
                                                      <td><label class="table-heading">Detailed Project Report<a href="">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="detailedProjectReportDesc" id="detailedProjectReportDescId" name="detailedProjectReportDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                          <form:textarea path="detailedProjectReportRemark" id="detailedProjectReportRemarkId" name="detailedProjectReportRemark"
                                    								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr> --%>
                                                  <tr>
                                                      <td><label class="table-heading">Certificate of  Exporter-Importer Code (IEC)No
                                                      <a href="./downloadfoodDocumentMDBP?fileName=${foodDocumentMDBP.importerexportercodecertificatefromdgftoffice}">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="certificateOfExportImportDesc" id="certificateOfExportImportDescId" name="certificateOfExportImportDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="certificateOfExportImportRemark" id="certificateOfExportImportRemarkId" name="certificateOfExportImportRemark"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Registration Certificate from APEDA<a href="">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="caCertificateDesc" id="caCertificateDescId" name="caCertificateDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="caCertificateRemark" id="caCertificateRemarkId" name="caCertificateRemark"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Cost of Plant Machinery as per CA certificate (in lacs) <a href="">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="listOfPlantMachDesc" id="listOfPlantMachDescId" name="listOfPlantMachDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="listOfPlantMachDescRemark" id="listOfPlantMachDescRemark" name="listOfPlantMachDescRemark"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Cost of Building as per CA certificate (in lacs)<a href="">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="detailsOfExportDoneDesc" id="detailsOfExportDoneDescId" name="detailsOfExportDoneDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="detailsOfExportDoneRemark" id="detailsOfExportDoneRemarkId" name="detailsOfExportDoneRemark"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                   <tr>
                                                      <td><label class="table-heading">Buyer Purchase order
                                                      <a href="./downloadfoodDocumentMDBP?fileName=${foodDocumentMDBP.buyerpurchaseorderfromimportingcountry}">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="buyerPurchaseOrderDesc" id="buyerPurchaseOrderDescId" name="buyerPurchaseOrderDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="buyerPurchaseOrderDescRemark" id="buyerPurchaseOrderDescRemarkId" name="buyerPurchaseOrderDescRemark"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                   <tr>
                                                      <td><label class="table-heading">Phyto sanitary/Health Certificate
                                                       <a href="./downloadfoodDocumentMDBP?fileName=${foodDocumentMDBP.phytosanitarycertificate}">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="phytoSanitaryCertificateDesc" id="phytoSanitaryCertificateDescId" name="phytoSanitaryCertificateDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="phytoSanitaryCertificateRemark" id="phytoSanitaryCertificateRemarkId" name="phytoSanitaryCertificateRemark"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <%-- <tr>
                                                      <td><label class="table-heading">Freight Bills of Container Corporation of  India <a href="">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="freightBillsOfContainerDesc" id="freightBillsOfContainerDescId" name="freightBillsOfContainerDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="freightBillsOfContainerRemark" id="freightBillsOfContainerRemarkId" name="freightBillsOfContainerRemark"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  --%>
                                                 
                                                  <tr>
                                                      <td><label class="table-heading">Export Invoice cum Packing List
                                                      <a href="./downloadfoodDocumentMDBP?fileName=${foodDocumentMDBP.exportinvoicecumpackinglistissuingauthoritydetails}">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="exportInvoiceDesc" id="exportInvoiceDescId" name="exportInvoiceDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="exportInvoiceRemark" id="exportInvoiceRemarkId" name="exportInvoiceRemark"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Bank Realization Certificate
                                                      <a href="./downloadfoodDocumentMDBP?fileName=${foodDocumentMDBP.nameofbankrealizationcertificate}">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="bankRealizationCertificateDesc" id="bankRealizationCertificateDescId" name="bankRealizationCertificateDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="bankRealizationCertificateRemark" id="bankRealizationCertificateRemarkId" name="bankRealizationCertificateRemark"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Shipping Bills for Export
                                                      <a href="./downloadfoodDocumentMDBP?fileName=${foodDocumentMDBP.shippingbillsforexports}">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="shippingBillsForExportDesc" id="shippingBillsForExportDescId" name="shippingBillsForExportDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="shippingBillsForExportRemark" id="shippingBillsForExportRemarkId" name="shippingBillsForExportRemark"
                        								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Bills of Lading<a href="">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="billsOfLandingDesc" id="billsOfLandingDescId" name="billsOfLandingDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="billsOfLandingRemark" id="billsOfLandingRemarkId" name="billsOfLandingRemark"
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
                                        <h4 class="card-title mb-4 mt-4">A. Subsidy Calculation for Road Transportation/Freight to the sea port.</h4>
                                    </div>
                                </div>
                                <div class="table-responsive mt-3">
                                      <table class="table table-stripped table-bordered" id="BreakUpTable1-mak">
                                          <thead>
                                              <tr>
                                                  <th>Concor Inv No.</th>
                                                  <th>Date </th>
                                                  <th>Basic Price (in Rs.)</th>
                                                <th>Abated Value</th>
                                                  <th>CGST</th>
                                                  <th>SGST</th>
                                                  <th>Total Amount</th>
                                                  <th style="width: 17%;">Eligible cost in lacs</th>
                                              </tr>
                                          </thead>
                                          <tbody class="add-from-here1-mak">
                                           <c:forEach var ="roadTransportlist" items ="${roadTransportList}" varStatus ="counter" >
                                              <tr>
                                                  <td><form:input path="subCalRoadTransportConcorInvNo" id="subCalRoadTransportConcorInvNoId" name="subCalRoadTransportConcorInvNo" value="${roadTransportlist.subCalRoadTransportConcorInvNo}" type="text"
                                                          readonly="false" class="form-control"/></td>
                                                          
                                                  <td><form:input path="subCalRoadTransportDate" id="subCalRoadTransportDateId" name="subCalRoadTransportDate" value="${roadTransportlist.subCalRoadTransportDate}" type="date"
                                                          readonly="false" class="form-control"/></td>
                                                          
                                                  <td>
                                                  <form:input path="subCalRoadTransportBasicPrice" id="subCalRoadTransportBasicPriceId" name="subCalRoadTransportBasicPrice" value="${roadTransportlist.subCalRoadTransportBasicPrice}" type="text"
                                                  readonly="false" class="form-control roadTransportBasicPrice"/>
                                                  </td>
                                                <td>
                                                  <form:input path="subCalRoadTransportAbatedValue" id="subCalRoadTransportAbatedValueId" name="subCalRoadTransportAbatedValue" value="${roadTransportlist.subCalRoadTransportAbatedValue}" type="text"
                                                  readonly="false" class="form-control roadTransportAvatedValue"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="subCalRoadTransportCGST" id="subCalRoadTransportCGSTId" name="subCalRoadTransportCGST" value="${roadTransportlist.subCalRoadTransportCGST}" type="text"
                                                  readonly="false" class="form-control roadTransportCGST"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="subCalRoadTransportSGST" id="subCalRoadTransportSGSTId" name="subCalRoadTransportSGST" value="${roadTransportlist.subCalRoadTransportSGST}" type="text"
                                                  readonly="false" class="form-control roadTransportSGST"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="subCalTotalAmount" id="subCalTotalAmountId" name="subCalTotalAmount" value="${roadTransportlist.subCalTotalAmount}" type="text"
                                                  readonly="false" class="form-control roadTransportTtlAmt"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="subCalRoadTransportEligibleCostInLacs" id="subCalRoadTransportEligibleCostInLacsId" name="subCalRoadTransportEligibleCostInLacs" value="${roadTransportlist.subCalRoadTransportEligibleCostInLacs}" type="text"
                                                  readonly="false" class="form-control roadTransportEligibleCost"/>
                                                  </td>
                                              </tr>
                                              </c:forEach>
                                          </tbody>
                                          <tfoot>
                                              <tr>
                                                  <td colspan="2" class="text-right"><strong>Total:</strong></td>
                                                  <td><form:input path="totalSubCalRoadTransportBasicPrice" id="totalSubCalRoadTransportBasicPriceId" name="totalSubCalRoadTransportBasicPrice" value="" type="text"
                                                          readonly="true" class="form-control ttlroadTransportBasicPrice"/></td>
                                                          
                                                          <td><form:input path="totalSubCalRoadTransportAbatedValue" id="totalSubCalRoadTransportAbatedValueId" name="totalSubCalRoadTransportAbatedValue" value="" type="text"
                                                          readonly="true" class="form-control ttlroadTransportAvatedValue"/></td>
                                                          
                                                  <td><form:input path="totalSubCalRoadTransportCGST" id="totalSubCalRoadTransportCGSTId" name="totalSubCalRoadTransportCGST" value="" type="text"
                                                          readonly="true" class="form-control ttlroadTransportCGST"/></td>
                                                  <td><form:input path="totalSubCalRoadTransportSGST" id="totalSubCalRoadTransportSGSTId" name="totalSubCalRoadTransportSGST" value="" type="text"
                                                          readonly="true" class="form-control ttlroadTransportSGST"/></td>
                                                          
                                                           <td>
                                                  <form:input path="totalSubCalRoadTransportTtlAmount" id="totalSubCalRoadTransportTtlAmountId" name="totalSubCalRoadTransportTtlAmount" value="" type="text"
                                                  readonly="true" class="form-control ttlroadTransportTtlAmt"/>
                                                  </td>
                                                  <td><form:input path="totalSubCalRoadTransportEligibleCostInLacs" id="totalSubCalRoadTransportEligibleCostInLacsId" name="totalSubCalRoadTransportEligibleCostInLacs" value="" type="text"
                                                          readonly="true" class="form-control ttlroadTransportEligibleCost"/></td>
                                                 
                                                 
                                              </tr>
                                              <tr>
                                                  <td colspan="7">
                                                      <button type="button" class="btn btn-outline-success add-row-break-up-new-mak"><i class="fa fa-plus"></i> Add More Items</button>
                                                  </td>
                                              </tr>
                                          </tfoot>
                                      </table>
                                  </div>
                                  <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <label class="mt-4">Nodal Officer's Remark</label>
                                        		<form:textarea path="subCalRoadTransportNodalOfficerRemark" id="subCalRoadTransportNodalOfficerRemarkId" name="subCalRoadTransportNodalOfficerRemark"
                								class="form-control border-info" rows="2"></form:textarea>
                                    </div>
                                </div>
                                  <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Eligible Subsidy amount of Road Transport :(Rs in Lacs)  </h4>
                                    </div>
                                </div>
                                <div class="table-responsive mt-3">
                                  <table class="table table-stripped table-bordered">
                                    <tbody>
                                      <tr>
                                        <td>Eligible cost </td> 
                                        <td><form:input path="eligibleCostRoadTransport" id="eligibleCostRoadTransportId" name="eligibleCostRoadTransport" value="" type="text"
                                                readonly="true" class="form-control"/></td>
                                      </tr>
                                      <tr>
                                        <td>Eligibility of Subsidy @25% of Eligible Cost</td>
                                        <td><form:input path="eligibilityOfSubsidyRoadTransport" id="eligibilityOfSubsidyRoadTransportId" name="eligibilityOfSubsidyRoadTransport" value="" type="text"
                                                readonly="true" class="form-control"/></td>
                                      </tr>
                                      <!-- <tr>
                                        <td>Total Eligible Amount</td>
                                        <td><form:input path="projectProfileAppId" id="ProjectProfileAppId" name="projectProfileAppId" value="" type="text"
                                                readonly="true" class="form-control"/></td>
                                      </tr> -->
                                    </tbody>
                                    <tfoot>
                                      <tr>
                                        <th>Max. Eligible Grant</th>
                                        <th><form:input path="maxEligibleGrantRoadTransport" id="maxEligibleGrantRoadTransportId" name="maxEligibleGrantRoadTransport" value="" type="text"
                                                readonly="true" class="form-control"/></th>
                                      </tr>
                                    </tfoot>
                                  </table>
                                </div>
                                  <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">B. Subsidy Calculation for FOB Value.</h4>
                                    </div>
                                </div>
                                <div class="table-responsive mt-3">
                                      <table class="table table-stripped table-bordered" id="BreakUpTable1-mak1">
                                          <thead>
                                              <tr>
                                                  <th>ShippingBill No.</th>
                                                  <th>Date </th>
                                                  <th>FOB Value (in Rs.)</th>
                                                  <th>CGST</th>
                                                  <th>SGST</th>
                                                  <th>Total Amount</th>
                                                  <th style="width: 17%;">Eligible cost in lacs</th>
                                              </tr>
                                          </thead>
                                          <tbody class="add-from-here1-mak1">
                                           <c:forEach var ="fobValuelist" items ="${fobValueList}" varStatus ="counter" >
                                              <tr>
                                                  <td><form:input path="subCalForFobValueShippingBillNo" id="subCalForFobValueShippingBillNoId" name="subCalForFobValueShippingBillNo" value="${fobValuelist.subCalForFobValueShippingBillNo}" type="text"
                                                          readonly="false" class="form-control"/></td>
                                                  <td><form:input path="subCalForFobValueDate" id="subCalForFobValueDateId" name="subCalForFobValueDate" value="${fobValuelist.subCalForFobValueDate}" type="date"
                                                          readonly="false" class="form-control"/></td>
                                                  <td>
                                                  <form:input path="subCalForFobValue" id="subCalForFobValueId" name="subCalForFobValue" value="${fobValuelist.subCalForFobValue}" type="text"
                                                  readonly="false" class="form-control fobValue"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="subCalForFobValueCGST" id="subCalForFobValueCGSTId" name="subCalForFobValueCGST" value="${fobValuelist.subCalForFobValueCGST}" type="text"
                                                  readonly="false" class="form-control fobValueCGST"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="subCalForFobValueSGST" id="subCalForFobValueSGSTId" name="subCalForFobValueSGST" value="${fobValuelist.subCalForFobValueSGST}" type="text"
                                                  readonly="false" class="form-control fobValueSGST"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="subCalForFobValueTotalAmount" id="subCalForFobValueTotalAmountId" name="subCalForFobValueTotalAmount" value="${fobValuelist.subCalForFobValueTotalAmount}" type="text"
                                                  readonly="false" class="form-control fobValuettlAmount"/>
                                                  </td>
                                                  <td>
                                                  <form:input path="subCalForFobValueEligibleCostInLacs" id="subCalForFobValueEligibleCostInLacsId" name="subCalForFobValueEligibleCostInLacs" value="${fobValuelist.subCalForFobValueEligibleCostInLacs}" type="text"
                                                  readonly="false" class="form-control fobValueEligibleCost"/>
                                                  </td>
                                              </tr>
                                              </c:forEach>
                                          </tbody>
                                          <tfoot>
                                              <tr>
                                                  <td colspan="2" class="text-right"><strong>Total:</strong></td>
                                                  <td><form:input path="totalSubCalForFobValue" id="totalSubCalForFobValueId" name="totalSubCalForFobValue" value="" type="text"
                                                          readonly="true" class="form-control ttlfobValue"/></td>
                                                  <td><form:input path="totalSubCalForFobValueCGST" id="totalSubCalForFobValueCGSTId" name="totalSubCalForFobValueCGST" value="" type="text"
                                                          readonly="true" class="form-control ttlfobValueCGST"/></td>
                                                  <td><form:input path="totalSubCalForFobValueSGST" id="totalSubCalForFobValueSGSTId" name="totalSubCalForFobValueSGST" value="" type="text"
                                                          readonly="true" class="form-control ttlfobValueSGST"/></td>
                                                  <td><form:input path="totalSubCalForFobValueTotalAmount" id="totalSubCalForFobValueTotalAmountId" name="totalSubCalForFobValueTotalAmount" value="" type="text"
                                                          readonly="true" class="form-control ttlfobValuettlAmount"/></td>
                                                  <td><form:input path="totalSubCalForFobValueEligibleCostInLacs" id="totalSubCalForFobValueEligibleCostInLacsId" name="totalSubCalForFobValueEligibleCostInLacs" value="" type="text"
                                                          readonly="true" class="form-control ttlfobValueEligibleCost"/></td>
                                              </tr>
                                              <tr>
                                                  <td colspan="7">
                                                      <button type="button" class="btn btn-outline-success add-row-break-up-new-mak1"><i class="fa fa-plus"></i> Add More Items</button>
                                                  </td>
                                              </tr>
                                          </tfoot>
                                      </table>
                                  </div>
                                  <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Eligible Subsidy on FOB amount (Rs. in Lacs)   </h4>
                                    </div>
                                </div>
                                <div class="table-responsive mt-3">
                                  <table class="table table-stripped table-bordered">
                                    <tbody>
                                      <tr>
                                        <td>Eligible cost </td>
                                        <td><form:input path="eligibleCostFOBValue" id="eligibleCostFOBValueId" name="eligibleCostFOBValue" value="" type="text"
                                                readonly="true" class="form-control"/></td>
                                      </tr>
                                      <tr>
                                        <td>Eligibility of Subsidy @20% of Eligible Cost</td>
                                        <td><form:input path="eligibilityOfSubsidyFOBValue" id="eligibilityOfSubsidyFOBValueId" name="eligibilityOfSubsidyFOBValue" value="" type="text"
                                                readonly="true" class="form-control"/></td>
                                      </tr>
                                     
                                    </tbody>
                                    <tfoot>
                                      <tr>
                                        <th>Max. Eligible Grant</th>
                                        <th><form:input path="maxEligibleGrantFOBValue" id="maxEligibleGrantFOBValueId" name="maxEligibleGrantFOBValue" value="" type="text"
                                                readonly="true" class="form-control"/></th>
                                      </tr>
                                    </tfoot>
                                  </table>
                                </div>
                               
                                       <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <label class="mt-4">Total Eligible Grant Amount (A+B)</label>
                                        		<form:input path="totalEligibleAmountFOBValue" id="totalEligibleAmountFOBValueId" name="totalEligibleAmountFOBValue" value="" type="text"
                                                readonly="true" class="form-control"/>
                                    </div>
                                </div>
                                 
                                  <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <label class="mt-4">Nodal Officer's Remark</label>
                                        		<form:textarea path="nodalOfficerRemarkFOBValue" id="nodalOfficerRemarkFOBValueId" name="nodalOfficerRemarkFOBValue"
                								class="form-control border-info" rows="2"></form:textarea>
                                    </div>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title  mt-4">Subsidy On Transportation/Freight & FOB Value  </h4>
                                        <form:textarea path="subOnTransportAndFOBValue" id="subOnTransportAndFOBValueId" name="subOnTransportAndFOBValue"
        								class="form-control border-info" rows="2"></form:textarea>
                                    </div>
                                </div>
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title  mt-4">Recommendation/Comments</h4>
                                        <form:textarea path="recommendationComments" id="recommendationCommentsId" name="recommendationComments"
        								class="form-control border-info" rows="2"></form:textarea>
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
                                        <a href="./foodProcessingMDBP?unit_Id=${businessEntityDetailsfood.unit_id}" class="common-default-btn mt-3">Back</a>
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
    function ttlroadTransportBasicPrice() {
		var sum = 0;
		$(".roadTransportBasicPrice").each(function() {
			sum += +$(this).val();
		});
		$(".ttlroadTransportBasicPrice").val(sum);
	}

	$(document).ready(ttlroadTransportBasicPrice);
	$(document).on("keyup", ttlroadTransportBasicPrice);
	$(document).on("change", ttlroadTransportBasicPrice);

	  function ttlroadTransportAvatedValue() {
			var sum = 0;
			$(".roadTransportAvatedValue").each(function() {
				sum += +$(this).val();
			});
			$(".ttlroadTransportAvatedValue").val(sum);
		}

		$(document).ready(ttlroadTransportAvatedValue);
		$(document).on("keyup", ttlroadTransportAvatedValue);
		$(document).on("change", ttlroadTransportAvatedValue);

	 function RoadTransportCGSTTotal() {
			var sum = 0;
			$(".roadTransportCGST").each(function() {
				sum += +$(this).val();
			});
			$(".ttlroadTransportCGST").val(sum);
		}

		$(document).ready(RoadTransportCGSTTotal);
		$(document).on("keyup", RoadTransportCGSTTotal);
		$(document).on("change", RoadTransportCGSTTotal);

		 function RoadTransportSGSTTotal() {
				var sum = 0;
				$(".roadTransportSGST").each(function() {
					sum += +$(this).val();
				});
				$(".ttlroadTransportSGST").val(sum);
			}

			$(document).ready(RoadTransportSGSTTotal);
			$(document).on("keyup", RoadTransportSGSTTotal);
			$(document).on("change", RoadTransportSGSTTotal);
			
			 function RoadTransportTtlAmtTotal() {
					var sum = 0;
					$(".roadTransportTtlAmt").each(function() {
						sum += +$(this).val();
					});
					$(".ttlroadTransportTtlAmt").val(sum);
				}

				$(document).ready(RoadTransportTtlAmtTotal);
				$(document).on("keyup", RoadTransportTtlAmtTotal);
				$(document).on("change", RoadTransportTtlAmtTotal);
				
				 function RoadTransportEligibleCostTotal() {
						var sum = 0;
						$(".roadTransportEligibleCost").each(function() {
							sum += +$(this).val();
						});
						$(".ttlroadTransportEligibleCost").val(sum);
					}

					$(document).ready(RoadTransportEligibleCostTotal);
					$(document).on("keyup", RoadTransportEligibleCostTotal);
					$(document).on("change", RoadTransportEligibleCostTotal);
					
			
					function FOBValuefobValueTotal() {
						var sum = 0;
						$(".fobValue").each(function() {
							sum += +$(this).val();
						});
						$(".ttlfobValue").val(sum);
					}

					$(document).ready(FOBValuefobValueTotal);
					$(document).on("keyup", FOBValuefobValueTotal);
					$(document).on("change", FOBValuefobValueTotal);

					function FOBValueCGSTTotal() {
						var sum = 0;
						$(".fobValueCGST").each(function() {
							sum += +$(this).val();
						});
						$(".ttlfobValueCGST").val(sum);
					}

					$(document).ready(FOBValueCGSTTotal);
					$(document).on("keyup", FOBValueCGSTTotal);
					$(document).on("change", FOBValueCGSTTotal);

					function FOBValueSGSTTotal() {
						var sum = 0;
						$(".fobValueSGST").each(function() {
							sum += +$(this).val();
						});
						$(".ttlfobValueSGST").val(sum);
					}

					$(document).ready(FOBValueSGSTTotal);
					$(document).on("keyup", FOBValueSGSTTotal);
					$(document).on("change", FOBValueSGSTTotal);

					function FOBValuettlAmountTotal() {
						var sum = 0;
						$(".fobValuettlAmount").each(function() {
							sum += +$(this).val();
						});
						$(".ttlfobValuettlAmount").val(sum);
					}

					$(document).ready(FOBValuettlAmountTotal);
					$(document).on("keyup", FOBValuettlAmountTotal);
					$(document).on("change", FOBValuettlAmountTotal);

					function FOBValueEligibleCostTotal() {
						var sum = 0;
						$(".fobValueEligibleCost").each(function() {
							sum += +$(this).val();
						});
						$(".ttlfobValueEligibleCost").val(sum);
					}

					$(document).ready(FOBValueEligibleCostTotal);
					$(document).on("keyup", FOBValueEligibleCostTotal);
					$(document).on("change", FOBValueEligibleCostTotal);

					function eligibcost() {
						var roadtransTtlEligibleCost = document
								.getElementById('totalSubCalRoadTransportEligibleCostInLacsId').value;
						var fobValueTtlEligibleCost = document
								.getElementById('totalSubCalForFobValueEligibleCostInLacsId').value;

						var grantAmtEligibleSubsidy = roadtransTtlEligibleCost * 0.25;
						var  grantAmtEligibleFob = fobValueTtlEligibleCost * 0.020;
						

						if (roadtransTtlEligibleCost > 0 && !roadtransTtlEligibleCost == '') {
							$('#eligibleCostRoadTransportId').val(roadtransTtlEligibleCost);
							$('#eligibilityOfSubsidyRoadTransportId').val(grantAmtEligibleSubsidy);
						}
						if (grantAmtEligibleFob > 0 && !grantAmtEligibleFob == '') {
							$('#eligibleCostFOBValueId').val(fobValueTtlEligibleCost);
							$('#eligibilityOfSubsidyFOBValueId').val(grantAmtEligibleFob);
						}
						
						
						if (grantAmtEligibleSubsidy >= 1000000) {
							
							document.getElementById("maxEligibleGrantRoadTransportId").value = "1000000";
						
						} else {
							document.getElementById("maxEligibleGrantRoadTransportId").value = grantAmtEligibleSubsidy;
							
						}
                      if(grantAmtEligibleFob >= 2000000)
                          {
                    	  document.getElementById("maxEligibleGrantFOBValueId").value = "2000000";
  						
  						} else {
  							document.getElementById("maxEligibleGrantFOBValueId").value = grantAmtEligibleFob;
  							

                          }
                      var roadtransMaxEligibleCost = document
						.getElementById('maxEligibleGrantRoadTransportId').value;

                      var fobMaxEligibleCost = document
						.getElementById('maxEligibleGrantFOBValueId').value;
                      var totalGrantAmtRoadAndFob = parseInt(roadtransMaxEligibleCost) + parseInt(fobMaxEligibleCost);
                      if(totalGrantAmtRoadAndFob != null)
                          {
                    	  document.getElementById("totalEligibleAmountFOBValueId").value = totalGrantAmtRoadAndFob;
                          }
                      
						
						}

							$(document).ready(eligibcost);
							$(document).on("change", eligibcost);
    </script>
</body>

</html>