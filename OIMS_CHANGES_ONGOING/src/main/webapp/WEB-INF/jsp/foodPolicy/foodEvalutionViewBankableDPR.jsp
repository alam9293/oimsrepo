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
                <h4 class="card-title mb-4 mt-4 text-center">EVALUATION FOR PREPARATION OF BANKABLE DETAILED PROJECT REPORT (DPR)</h4>
                    <div class="card card-block p-3">
                        <div class="row">
                            <div class="col-sm-5">
                                <a href="./foodProcessingBankDPR?unit_Id=${businessEntityDetailsfood.unit_id}" class="common-default-btn mt-3">Back</a>
                            </div>
                            <div class="col-sm-7 text-right">
                                <a href="#EvaluationAuditTrail" class="text-info mr-3" data-toggle="modal">Evaluation Audit Trail</a>
                                <a href="javacript:void(0);" class="common-btn mt-3"><i class="fa fa-edit"></i> Edit</a>
                            </div>
                        </div>
                        
                        <form:form modelAttribute="foodViewEvaluateBankableDPR" method="post"
							action="savefoodEvaluateBankableDPR" class="mt-4" enctype="multipart/form-data">
                        <div class="row mt-5">
                            <div class="col-sm-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Name of Organization/Promoter  </th>
                                                <th>Registered Address</th>
                                                <th>Project Name</th>
                                              
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td><form:input path="nameOfOrganization" id="nameOfOrganizationId" name="nameOfOrganization" value="${businessEntityDetailsfood.nameofcompany}" type="text"
                                                        readonly="true" class="form-control"/></td>
                                                <td><form:input path="registeredAddress" id="registeredAddressId" name="registeredAddress" 	value="${businessEntityDetailsfood.address}" type="text"
                                                        readonly="true" class="form-control"/></td>
                                                <td><form:input path="projectName" id="projectNameId" name="projectName" value="${projectandproposedemploymentdetails.nameoftheproject}" type="text"
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
                              <form:input path="panNo" id="panNoId" name="panNo" value="${businessEntityDetailsfood.panno}" type="text"
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
                              <form:input path="registeredOfficeOfTheFirm" id="registeredOfficeOfTheFirmId" name="registeredOfficeOfTheFirm" value="${businessEntityDetailsfood.address}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="registeredOfficeOfTheFirmObserv" id="registeredOfficeOfTheFirmObservId" name="registeredOfficeOfTheFirmObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>  
                         <tr>
                             <td colspan="3"><strong>Product Detailst</strong></td>
                       </tr>
                        <table class="table table-stripped table-bordered directors-table" id="customFields">
                        <thead>
                            <tr>
                                <th>Products</th>
                                <th>By Products</th>
                               
                                
                            </tr>
                        </thead>
                        <c:forEach items="${productdetails}"
																		var="productdetailsList">
																		<tr>
																			<td><input type="text" class="form-control"
																				id="" value="${productdetailsList.products}"
																				disabled="" name=""></td>
																			<td><input type="text" class="form-control"
																				id="" value="${productdetailsList.byproducts }"
																				disabled="" name=""></td>
																		</tr>
																	</c:forEach>
                    </table> 

                              <td>
                              <form:textarea path="registeredProductDetailsObserv" id="registeredProductDetailsObservId" name="registeredProductDetailsObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr> 
                          <table class="table table-bordered">
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Name of Agency who Prepared DPR</label></td>
                              <td style="width: 33%;">
                              <form:input path="detailsOfInstitution" id="detailsOfInstitutionId" name="detailsOfInstitution" value="${projectandproposedemploymentdetails.nameofthenbspagencywhoprepareddpr}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="detailsOfInstitutionObserv" id="detailsOfInstitutionObservId" name="detailsOfInstitutionObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                        </table>
                        <%--  <table class="table table-bordered">
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Details of export import <br>license for market development </label></td>
                              <td style="width: 33%;">
                              <form:input path="detailsOfExportImport" id="detailsOfExportImportId" name="detailsOfExportImport" value="" type="text"
                              readonly="false" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="detailsOfExportImportObserv" id="detailsOfExportImportObservId" name="detailsOfExportImportObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                          </tr>
                        </table>
                         --%>
                        
                         

                          
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
                         <table class="table table-bordered">
                          <tr>
                              <td style="width: 33%;"><label class="table-heading">Total Project Cost proposed in DPR (in Lacs)</label></td>
                              <td style="width: 33%;">
                              <form:input path="detailsOfInstitution" id="detailsOfInstitutionId" name="detailsOfInstitution" value="${projectandproposedemploymentdetails.nameofthenbspagencywhoprepareddpr}" type="text"
                              readonly="true" class="form-control"/>
                              </td>
                              <td>
                              <form:textarea path="detailsOfInstitutionObserv" id="detailsOfInstitutionObservId" name="detailsOfInstitutionObserv"
								class="form-control border-info" rows="2"></form:textarea>
                              </td>
                              </tr>
                              </table>
                              
                        
                       <div class="row">
                            <div class="col-sm-12"> <%-- 
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Cost of Project as per bank appraisal (in lacs)    </h4>
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
                                                      <td><label class="table-heading">Consultancy Fee</label></td>
                                                      <td>
                                                      <form:input path="consultancyFeeCost" id="consultancyFeeCostId" name="consultancyFeeCost" value="" type="text"
                                                      readonly="false" class="form-control costOfProject"/>
                                                      </td>
                                                    
                                                   
                                                      <td>
                                                      <form:textarea path="consultancyFeeRemarks" id="consultancyFeeRemarksId" name="consultancyFeeRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>


                                                  <tr>
                                                      <td><label class="table-heading">Fee charged by institution<br> for preparation of DPR </label></td>
                                                      <td>
                                                      <form:input path="feeChargedByInstitutionCost" id="feeChargedByInstitutionCostId" name="feeChargedByInstitutionCost" value="" type="text"
                                                      readonly="false" class="form-control costOfProject"/>
                                                      </td>
                                                     
                                                      <td>
                                                      <form:textarea path="feeChargedByInstitutionRemarks" id="feeChargedByInstitutionRemarksId" name="feeChargedByInstitutionRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>


                                                  <tr>
                                                      <td><label class="table-heading">Plant machinery required for<br> quality certification/up gradation  </label></td>
                                                      <td>
                                                      <form:input path="plantMachineryRequiredCost" id="plantMachineryRequiredCostId" name="plantMachineryRequiredCost" value="" type="text"
                                                      readonly="false" class="form-control costOfProject"/>
                                                      </td>
                                                   
                                                      <td>
                                                      <form:textarea path="plantMachineryRequiredRemarks" id="plantMachineryRequiredRemarksId" name="plantMachineryRequiredRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>

                                                  <tr>
                                                      <td><label class="table-heading">Technical civil work required <br>for quality certification/up <br>gradation </label></td>
                                                      <td>
                                                      <form:input path="technicalCivilWorkCost" id="technicalCivilWorkCostId" name="technicalCivilWorkCost" value="" type="text"
                                                      readonly="false" class="form-control costOfProject"/>
                                                      </td>
                                                      
                                                    
                                                      <td>
                                                      <form:textarea path="technicalCivilWorkRemarks" id="technicalCivilWorkRemarksId" name="technicalCivilWorkRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Others </label></td>
                                                      <td>
                                                      <form:input path="othersCost" id="othersCostId" name="othersCost" value="${totalProjectCostproposedinDPR.amountothercost}" type="text"
                                                      readonly="true" class="form-control costOfProject"/>
                                                      </td>
                                                    
                                                      <td>
                                                      <form:textarea path="othersRemarks" id="othersRemarksId" name="othersRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>

                                                  <tr>
                                                    <td><label class="table-heading">Total </label></td>
                                                    <td>
                                                    <form:input path="totalCost" id="totalCostId" name="totalCost" value="" type="text"
                                                    readonly="true" class="form-control totalCostOfProject"/>
                                                    </td>
                                                  
                                                    <td>
                                                    <form:textarea path="totalRemarks" id="totalRemarksId" name="totalRemarks"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                 

                                               
                                            </table> --%>
                                  
                               <%--  <div class="mt-4">
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
                                                <c:forEach items="${meansofFinanceBankDPR}" var="meansofFinanceBankDPRList">
																		
                                                  <tr>
                                                      <td><label class="table-heading">Equity (Promoter Share)</label></td>
                                                      
                                                      <td>
                                                      <form:input path="mofEquityCost" id="mofEquityCostId" name="mofEquityCost" value="${meansofFinanceBankDPRList.proposedinvestmentequity}" type="text"
                                                      readonly="true" class="form-control meanseOfFinance"/>
                                                      </td>
                                                    
                                                      <td>
                                                      <form:textarea path="mofEquityRemarks" id="mofEquityRemarksId" name="mofEquityRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Term loan </label></td>
                                                      
                                                      <td>
                                                      <form:input path="mofTermLoanCost" id="mofTermLoanCostId" name="mofTermLoanCost" value="${meansofFinanceBankDPRList.proposedinvestmenttermloan}" type="text"
                                                      readonly="true" class="form-control meanseOfFinance"/>
                                                      </td>
                                                    
                                                      <td>
                                                      <form:textarea path="mofTermLoanRemark" id="mofTermLoanRemarkId" name="mofTermLoanRemark"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Assistance from other sources</label></td>
                                                      
                                                      <td>
                                                      <form:input path="mofAssistanceFromOtherSourcesCost" id="mofAssistanceFromOtherSourcesCostId" name="mofAssistanceFromOtherSourcesCost" value="" type="text"
                                                      readonly="false" class="form-control meanseOfFinance"/>
                                                      </td>
                                                     
                                                      <td>
                                                      <form:textarea path="mofAssistanceFromOtherSourcesRemark" id="mofAssistanceFromOtherSourcesRemarkId" name="mofAssistanceFromOtherSourcesRemark"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Others</label></td>
                                                      
                                                      <td>
                                                      <form:input path="mofOthersCost" id="mofOthersCostId" name="mofOthersCost" value="${meansofFinanceBankDPRList.proposedinvestmentothers}" type="text"
                                                      readonly="true" class="form-control meanseOfFinance"/>
                                                      </td>
                                                     
                                                      <td>
                                                      <form:textarea path="mofOthersCostRemarks" id="mofOthersCostRemarksId" name="mofOthersCostRemarks"
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
                                                    readonly="true" class="form-control totalMeanseOfFinance"/>
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
                                </div>--%>
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
                                                        <th>Description </th>
                                                     
                                                        <th>Nodal Officer's Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                  <tr>
                                                      <td><label class="table-heading">Application on Prescribed format &nbsp;<a href="">View</a></label></td>
                                                      <td>
                                                      <form:input path="appOnPrescribedFormatDesc" id="appOnPrescribedFormatDescId" name="appOnPrescribedFormatDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="appOnPrescribedFormatRemarks" id="appOnPrescribedFormatRemarksId" name="appOnPrescribedFormatRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Detailed Project Report&nbsp;<a href="./downloadfoodDocumentsBankDPR?fileName=${foodDocumentsBankDPR.projectreportdetail}">View</a> </label></td>
                                                      
                                                      <td>
                                                      <form:input path="detailedProjectReportDesc" id="detailedProjectReportDescId" name="detailedProjectReportDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="detailedProjectReportRemarks" id="detailedProjectReportRemarksId" name="detailedProjectReportRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Certificate of Incorporation &nbsp;<a href="./downloadfoodDocumentsBankDPR?fileName=${foodDocumentsBankDPR.incorporationcertificateoffirm}">View</a></label></td>
                                                      
                                                      <td>
                                                      <form:input path="certificateOfIncorporationDesc" id="certificateOfIncorporationDescId" name="certificateOfIncorporationDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="certificateOfIncorporationRemarks" id="certificateOfIncorporationRemarksId" name="certificateOfIncorporationRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                    <td><label class="table-heading">Certificate of Partnership deed  &nbsp;<a href="./downloadfoodDocumentsBankDPR?fileName=${foodDocumentsBankDPR.partnershipdeedbyelawsofthefirm}">View</a></label></td>
                                                    
                                                    <td>
                                                    <form:input path="certificateOfPartnershipDeedDesc" id="certificateOfPartnershipDeedDescId" name="certificateOfPartnershipDeedDesc" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="certificateOfPartnershipDeedRemarks" id="certificateOfPartnershipDeedRemarksId" name="certificateOfPartnershipDeedRemarks"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>
                                                 <%--  <tr>
                                                      <td><label class="table-heading">Promoters Bio-Data&nbsp;<a href="./downloadfoodDocumentsBankDPR?fileName=${foodDocumentsBankDPR.chiefpromoterdirectorsbiodata}">View</a> </label></td>
                                                      
                                                      <td>
                                                      <form:input path="promotersBioDataDesc" id="promotersBioDataDescId" name="promotersBioDataDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="promotersBioDataRemark" id="promotersBioDataRemarkId" name="promotersBioDataRemark"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Financial statements for last 3 years&nbsp;<a href="">View</a> </label></td>
                                                      
                                                      <td>
                                                      <form:input path="financialStatementsForLast3YrsDesc" id="financialStatementsForLast3YrsDescId" name="financialStatementsForLast3YrsDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="financialStatementsForLast3YrsRemarks" id="financialStatementsForLast3YrsRemarksId" name="financialStatementsForLast3YrsRemarks"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr> --%>
                                                  <tr>
                                                      <td><label class="table-heading">Copies of paid Bill/Boucher Against &nbsp;<a href="">View</a> </label></td>
                                                      
                                                      <td>
                                                      <form:input path="receiptOfPaymentCostDesc" id="receiptOfPaymentCostDescId" name="receiptOfPaymentCostDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="receiptOfPaymentRemark" id="receiptOfPaymentRemarkId" name="receiptOfPaymentRemark"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>
                                                  <tr>
                                                      <td><label class="table-heading">Chartared Engineer Certificate for Project Implementation&nbsp;<a href="">View</a> </label></td>
                                                      
                                                      <td>
                                                      <form:input path="layoutOfPlantDesc" id="layoutOfPlantDescId" name="layoutOfPlantDesc" value="" type="text"
                                                      readonly="false" class="form-control"/>
                                                      </td>
                                                      <td>
                                                      <form:textarea path="layoutOfPlantRemark" id="layoutOfPlantRemarkId" name="layoutOfPlantRemark"
                      								class="form-control border-info" rows="2"></form:textarea>
                                                      </td>
                                                  </tr>

                                                  <tr>
                                                    <td><label class="table-heading">Loan Sanction Letter  &nbsp;<a href="">View</a>  </label></td>
                                                    
                                                    <td>
                                                    <form:input path="detailsOfPlantMachineryCEMechDesc" id="detailsOfPlantMachineryCEMechDescId" name="detailsOfPlantMachineryCEMechDesc" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="detailsOfPlantMachineryCEMechRemark" id="detailsOfPlantMachineryCEMechRemarkId" name="detailsOfPlantMachineryCEMechRemark"
                    								class="form-control border-info" rows="2"></form:textarea>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td><label class="table-heading">Loan Disbursement document &nbsp;<a href="">View</a>  </label></td>
                                                    
                                                    <td>
                                                    <form:input path="detailsOfTechnicalCivilDesc" id="detailsOfTechnicalCivilDesc" name="detailsOfTechnicalCivilDesc" value="" type="text"
                                                    readonly="false" class="form-control"/>
                                                    </td>
                                                    <td>
                                                    <form:textarea path="detailsOfTechnicalCivilRemark" id="detailsOfTechnicalCivilRemarkId" name="detailsOfTechnicalCivilRemark"
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
                                        <h4 class="card-title mb-4 mt-4">Details of payment for preparation of bankable project-</h4>
                                    </div>
                                </div>
                                <div class="table-responsive mt-3">
                                      <table class="table table-stripped table-bordered" id="BreakUpTable1">
                                          <thead>
                                              <tr>
                                               <!--    <th>S. No</th> -->
                                               <th>Voucher Number</th>
                                               <th>Voucher Date</th>
                                                  <th>Amount</th>
                                                  <th>T.D.S.
                                                    </th>
                                                 <th>Total Amount Paid</th>
                                                  <th>Eligible cost
                                                    (in lacs)
                                                    </th>
                                                 
                                                 
                                                
                                              </tr>
                                          </thead>
                                          <tbody class="add-from-here1">
                                          	<c:forEach items="${detailsOfPaymentBankableDPRList}" var="detailOfPaymentList">
																		
                                              <tr>
                                                  <td><form:input path="detailsOfPaymentBankableBoucherNo" id="detailsOfPaymentBankableBoucherNoId" name="detailsOfPaymentBankableBoucherNo" 
                                                  value="${detailOfPaymentList.detailsOfPaymentBankableBoucherNo}" type="text"
                                                          readonly="true" class="form-control"/></td> 
                                                          
                                                           <td><form:input path="detailsOfPaymentBankableBoucherDate" id="detailsOfPaymentBankableBoucherDateId" name="detailsOfPaymentBankableBoucherDate" 
                                                  value="${detailOfPaymentList.detailsOfPaymentBankableBoucherDate}" type="text"
                                                          readonly="true" class="form-control"/></td>
                                                          
                                                  <td> <form:input path="detailsOfPaymentBankableProjectAmount" id="detailsOfPaymentBankableProjectAmountId" name="detailsOfPaymentBankableProjectAmount"
                                                   value="${detailOfPaymentList.detailsOfPaymentBankableProjectAmount}" type="text"
                                                          readonly="true" class="form-control"/></td>
                                                 
                                                 
                                                  <td>
                                                  <form:input path="detailsOfPaymentBankableProjectTDS" id="detailsOfPaymentBankableProjectTDSId" name="detailsOfPaymentBankableProjectTDS" 
                                                  value="${detailOfPaymentList.detailsOfPaymentBankableProjectTDS}" type="text"
                                                  readonly="true" class="form-control"/>
                                                  </td>
                                                  
                                                   <td><form:input path="detailsOfPaymentBankableTotalAmountPaid" id="detailsOfPaymentBankableTotalAmountPaidId" name="detailsOfPaymentBankableTotalAmountPaid" 
                                                  value="${detailOfPaymentList.detailsOfPaymentBankableTotalAmountPaid}" type="text"
                                                          readonly="true" class="form-control"/></td>
                                                  
                                                  <td>
                                                   
                                                  <form:input path="detailsOfPaymentBankableProjectEligCost" id="detailsOfPaymentBankableProjectEligCostId" name="detailsOfPaymentBankableProjectEligCost" 
                                                  value="${detailOfPaymentList.detailsOfPaymentBankableProjectEligCost}" type="text"
                                                  readonly="false" class="form-control detailsPayEligCost"/>
                                                 
                                                </td>
                                              </tr>
                                              </c:forEach>
                                          
                                          <tfoot>
                                              <tr>
                                                  <td colspan="5" class="text-right"><strong>Total:</strong></td>
                                                   <td><form:input path="ttlDetailsOfPaymentBankableProject" id="ttlDetailsOfPaymentBankableProjectId" name="ttlDetailsOfPaymentBankableProject" value="" type="text"
                                                          readonly="true" class="form-control totaldetailsPayEligCost"/></td>
                                                          
                                                  <%-- <td><form:input path="detailsOfPaymentBankableProjectRemarks" id="detailsOfPaymentBankableProjectRemarksId" name="detailsOfPaymentBankableProjectRemarks" value="" type="text"
                                                          readonly="true" class="form-control"/></td> --%>
                                                          
                                                  
                                              </tr>
                                              <!-- <tr>
                                                  <td colspan="5">
                                                      <button type="button" class="btn btn-outline-success add-row-break-up-new"><i class="fa fa-plus"></i> Add More Items</button>
                                                  </td>
                                              </tr> -->
                                          </tfoot>
                                          </tbody>
                                      </table>
                                  </div>
                                  <div class="mt-4">
                                    <div class="without-wizard-steps">
                                       
                                    <form:textarea path="detailsOfPaymentBankableProjectRemarks" id="detailsOfPaymentBankableProjectRemarksId" name="detailsOfPaymentBankableProjectRemarks"
    								class="form-control mt-4" placeholder="Nodal Officer's Remark" rows="4"></form:textarea>
                                    </div>
                                </div>
                           
                              
                            
                                <div class="mt-4">
                                    <div class="without-wizard-steps">
                                        <h4 class="card-title mb-4 mt-4">Eligible Grant amount:(In Lacs)  </h4>
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
                                                  <td><p>Eligible cost of DPR Preparation  </p></td>
                                                  <td><form:input path="eligibleCostOfDPR" id="eligibleCostOfDPRId" name="eligibleCostOfDPR" value="" type="text"
                                                          readonly="true" class="form-control"/></td>
                                                </tr>
                                                <tr>
                                                  <td><p>Eligibility of Grant @50% of Eligible cost of DPR Preparation </p></td>
                                                  <td><form:input path="eligibilityOfGrant50Percent" id="eligibilityOfGrant50PercentId" name="eligibilityOfGrant50Percent" value="" type="text"
                                                          readonly="true" class="form-control"/></td>
                                                </tr>
                                                <tr>
                                                  <td><p>Max. Eligible Grant </p></td>
                                                  <td><form:input path="maxEligibleGrant" id="maxEligibleGrantId" name="maxEligibleGrant" value="" type="text"
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
                                        <a href="./foodProcessingBankDPR?unit_Id=${businessEntityDetailsfood.unit_id}" class="common-default-btn mt-3">Back</a>
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
    function DetailsPaymentEligCost() {
		var sum = 0;
		$(".detailsPayEligCost").each(function() {
			sum += +$(this).val();
		});
		$(".totaldetailsPayEligCost").val(sum);
	}

	$(document).ready(DetailsPaymentEligCost);
	$(document).on("keyup", DetailsPaymentEligCost);
	$(document).on("change", DetailsPaymentEligCost);

	 function TotalCostOfProject() {
			var sum = 0;
			$(".costOfProject").each(function() {
				sum += +$(this).val();
			});
			$(".totalCostOfProject").val(sum);
		}

		$(document).ready(TotalCostOfProject);
		$(document).on("keyup", TotalCostOfProject);
		$(document).on("change", TotalCostOfProject);

		function TotalMeanseOfFinance() {
			var sum = 0;
			$(".meanseOfFinance").each(function() {
				sum += +$(this).val();
			});
			$(".totalMeanseOfFinance").val(sum);
		}

		$(document).ready(TotalMeanseOfFinance);
		$(document).on("keyup", TotalMeanseOfFinance);
		$(document).on("change", TotalMeanseOfFinance);//
		
	
    function eligibcost() {
		var detailsOfPaymentEligibleCost = document
				.getElementById('ttlDetailsOfPaymentBankableProjectId').value;
		
		var grantAmtEligibleDPR = detailsOfPaymentEligibleCost * 0.050;

		if (detailsOfPaymentEligibleCost > 0 && !detailsOfPaymentEligibleCost == '') {
			$('#eligibleCostOfDPRId').val(detailsOfPaymentEligibleCost);
			$('#eligibilityOfGrant50PercentId').val(grantAmtEligibleDPR);
			$('#maxEligibleGrantId').val(grantAmtEligibleDPR); 
		}
    }
		$(document).ready(eligibcost);
		$(document).on("change", eligibcost);
		
    </script>
</body>

</html>