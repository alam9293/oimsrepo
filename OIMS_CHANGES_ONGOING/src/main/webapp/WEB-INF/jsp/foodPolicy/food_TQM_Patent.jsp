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
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Food View All Applicant Details</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
</head>

<body>
	<button onclick="topFunction()" id="GoToTopBtn" title="Go to top">Top</button>
	<section class="inner-header">
		<!-- Navigation / Navbar / Menu -->
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="#"><img src="images/logo.png"
					class="logo" alt="Logo"></a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarTogglerDemo02"
					aria-controls="navbarTogglerDemo02" aria-expanded="false"
					aria-label="Toggle navigation">
					<i class="fa fa-user"></i>
				</button>
				<div class="flex-row d-flex left-menu-toggle">
					<button type="button" class="hidden-md-up navbar-toggler"
						data-toggle="offcanvas" title="Toggle responsive left sidebar">
						<span class="navbar-toggler-icon"></span>
					</button>
				</div>
				<div class="collapse navbar-collapse" id="navbarTogglerDemo02">
					<ul class="navbar-nav ml-auto mt-2 mt-lg-0">
						<li class="nav-item"><a class="nav-link"
							href="./fooduserLogout"><i class="fa fa-power-off"></i>
								Logout</a></li>

						<li class="nav-item"><a class="nav-link active" href="#"><i
								class="fa fa-user"></i>${userName} </a></li>

						<li class="nav-item dropdown dropleft notification-dropdown">
							<a class="nav-link notification" data-toggle="dropdown" href="#"><i
								class="fa fa-bell"></i><span
								class="animate__animated animate__bounceIn">5</span></a>
							<div class="dropdown-menu">
								<a class="dropdown-item" href="notification.html">You have 3
									new recommendations please check.</a> <a class="dropdown-item"
									href="notification.html">You have 4 new notes please check.</a>
								<a class="dropdown-item" href="notification.html">You have 5
									new comments please check.</a> <a class="dropdown-item"
									href="notification.html">You have 3 new recommendations
									please check.</a> <a class="dropdown-item" href="notification.html">You
									have 4 new notes please check.</a>
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
					<h4 class="card-title mb-4 mt-4 text-center">FOOD PROCESSING
						INDUSTRY POLICY-2017</h4>
					<div class="card card-block p-3">
						<div class="row">
							<div class="col-sm-12">
								<div class="mt-4">
									<div class="without-wizard-steps">
										<div class="row">
											<div class="col-sm-8">
												<a href="./foodProcessIndustryPolicy"
													class="common-default-btn mt-0">Back</a> <a
													href="./evaluationViewTQM" class="common-btn mt-3"
													id="evaluateBtnTop">Evaluate Application</a>
												<!-- <button type="button" class="btn btn-outline-info btn-sm" data-target="#SendtoConcernDepartment" data-toggle="modal">Send to Concern Department</button> -->
											</div>
											<div class="col-sm-4 text-right">
												<!-- <div class="form-group">
                                                    <select class="form-control" id="SelectParameter">
                                                        <option value="viewAllApplicationDetails">View as per entrepreneur</option>
                                                        <option value="Annexure-I">View as per policy LOC form Annexure-I</option>
                                                        <option value="Annexure-III">View as per policy LOC form Annexure-III</option>
                                                    </select>
                                                </div> -->
											</div>
										</div>
										<hr>
										<div class="row mt-4">
											<div class="col-sm-12 text-center">
												<button type="button"
													class="btn btn-outline-success btn-sm mb-3">
													<i class="fa fa-download"></i> Download Only Application
													Form
												</button>

												<button type="button"
													class="btn btn-outline-secondary btn-sm mb-3">
													<i class="fa fa-download"></i> Download Only Supporting
													Docs
												</button>
											</div>
										</div>


										<!-- Start view All Application Details -->

										<div class="parameter-properties"
											id="viewAllApplicationDetails">
											<hr class="mt-2">
											<h4 class="card-title mb-4 mt-4 text-center">Business
												Entity Details</h4>

											<div class="isf-form mt-4">
												<div class="row">
													<div class="col-sm-12">
														<div class="table-responsive">
															<table class="table table-bordered">
																<tbody>
																	<%-- <c:forEach var="list" items="${businessEntityDetailsfood}"> --%>
																	<tr>
																		<td>Name of Promoter</td>
																		<td><input type="text" class="form-control" id=""
																			value="${businessEntityDetailsfood.nameofpromoter}"
																			disabled="disabled" name=""></td>
																		<td rowspan="4" class="text-center"><img
																			src="data:image/png;base64,${businesfoodImg}"
																			style="height: 250px;" /></td>
																	</tr>
																	<tr>
																		<td>Name of Company</td>
																		<td><input type="text" class="form-control" id=""
																			value="${businessEntityDetailsfood.nameofcompany}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>Address of Company</td>
																		<td><input type="text" class="form-control" id=""
																			value="${businessEntityDetailsfood.addressofcompany}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>Mobile No</td>
																		<td><input type="text" class="form-control" id=""
																			value="${businessEntityDetailsfood.mobileno}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>Email</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.email}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>State</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.companystate}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>District</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.companydistrict}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>PAN Card of Firm /Proprietor</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.pancardoffirmproprietor}"
																			disabled="" name=""></td>
																	</tr>

																	<tr>
																		<td>GSTIN</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.gstin}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>Type of Organization</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${businessEntityDetailsfood.typeoforganization}"
																			disabled="" name=""></td>
																	</tr>
																	<tr>
																		<td>Date of Incorporation</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id=""
																			value="${corformatdate}"
																			disabled="" name=""></td>
																	</tr>
																	<%-- </c:forEach> --%>
																	<tr>
																		<td colspan="3"><strong>Financial Status
																				of Last 3 Years (Rupees in Lacs)</strong></td>
																	</tr>
																	<table
																		class="table table-stripped table-bordered directors-table"
																		id="customFields">
																		<thead>
																			<tr>
																				<th>Financial Year</th>
																				<th>Turn Over (In Lakh)</th>

																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach var="list" items="${fianancStatusList}"
																				varStatus="counter">
																				<tr>
																					<td><input type="text" class="form-control"
																						id="" value="${list.financialyear}" disabled=""
																						name=""></td>
																					<td><input type="text" class="form-control"
																						id="" value="${list.turnover}" disabled="" name=""></td>
																				</tr>
																			</c:forEach>
																		</tbody>
																	</table>
																	<tr>

																		<td>Existing activities (If Any)</td>
																		<td colspan="2"><input type="text"
																			class="form-control" id="" value="Sachin" disabled=""
																			name=""></td>
																	</tr>
																	</div>
																	</div>
																	</div>
																	</div>

																	<hr class="mt-2">

																	<h4 class="card-title mb-4 mt-4 text-center">Project Details</h4>

																	<div class="isf-form mt-4">
																		<hr class="mt-2">
																		<div class="row">
																			<div class="col-sm-12">
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tbody>
                             <tr>
                             <td colspan="3"><strong>Product Detail</strong></td>
                       </tr>
                        <table class="table table-stripped table-bordered directors-table" id="customFields">
                        <thead>
                            <tr>
                                <th>Name of the Products</th>
                                <th>By-products</th>
                                <th>Brand Name</th>
                                
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="listpd" items="${productDetail}"
								varStatus="counter">
                            <tr>
                              
                               
                                <td><input type="text" class="form-control" id="" value="${listpd.nameoftheproducts}" disabled="" name=""></td>
                                 <td><input type="text" class="form-control" id="" value="${listpd.byproducts}" disabled="" name=""></td>
                                  <td><input type="text" class="form-control" id="" value="${listpd.brandname}" disabled="" name=""></td>
                                
                            </tr>
                            </c:forEach>
                           </tbody>
                    </table> 
                            <tr>
                                <td style="width: 50%;">Technology (Indigenous/ imported)</td>
                                <td><input type="text" class="form-control" id="" value="${foodTQMprojectdetails.technology}" disabled="" name=""></td>
                            </tr>
                             <tr>
                             <td colspan="3"><strong>Capacity of installed Plant & Manufacturing Unit</strong></td>
                       </tr>
                        <table class="table table-stripped table-bordered directors-table" id="customFields">
                        <thead>
                            <tr>
                                <th>Capacity</th>
                                <th>Per Day</th>
                                <th>Per Year</th>
                                
                            </tr>
                        </thead>
                        <tbody>
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
                           </tbody>
                    </table> 
                            <tr>
                                <td>Capacity utilization in last completed year (figures in %)</td>
                                <td><input type="text" class="form-control" id="" value="${foodTQMprojectdetails.capacityutilizationinlastcompletedyear}"" disabled="" name=""></td>
                            </tr>
                             <tr>
                                <td>Name of the Project: (Implementation of HACCP/ ISO 14000/ ISO 22000/ GMP/ GHP / Patent)</td>
                                <td><input type="text" class="form-control" id="" value="${foodTQMprojectdetails.nameoftheproject}" disabled="" name=""></td>
                            </tr>
                             <tr>
                                <td>Location/ Area of the Manufacturing Unit</td>
                                <td><input type="text" class="form-control" id="" value="${foodTQMprojectdetails.locationareaofthemanufacturingunit}" disabled="" name=""></td>
                            </tr>
                          

                     <tr>
                             <td colspan="3"><strong>Project Cost</strong></td>
                       </tr>
                        <table class="table table-stripped table-bordered directors-table" id="customFields">
                        <thead>
                            <tr>
                                <th>Items</th>
                                <th>Cost (In Lakh)</th>
                               
                        
                                
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="listpc" items="${projectCost}"
								varStatus="counter">
                            <tr>
                                <td>Consultant Fee</td>
                                <td><input type="text" class="form-control" id="" value="${listpc.costconsultantfee}" disabled="" name=""></td>
                               
                                
                            </tr>
                             <tr>
                                 <td>Fee charged by Certifying Agency</td>
                                  <td><input type="text" class="form-control" id="" value="${listpc.costfeechargedbycertifyingagency}" disabled="" name=""></td>
                                
                                
                            </tr>
                            <tr>
                                <td>Technical Civil Works (if any) with reference to GAP Study Report</td>
                                    <td><input type="text" class="form-control" id="" value="${listpc.costtechnicalcivilworkswithreferencetogapstudyreport}" disabled="" name=""></td>
                                 

                            </tr>
                             <tr>
                                <td>Plant & Machinery with reference to GAP Analysis Report</td>
                                    <td><input type="text" class="form-control" id="" value="${listpc.costplantmachinerywithreferencetogapanalysisreport}" disabled="" name=""></td>
                                  

                            </tr>
                             <tr>
                                <td>Any other expenses as per the requirement of GMP/ GHP/ ISO 14000/ ISO 22000/ HACCP</td>
                                    <td><input type="text" class="form-control" id="" value="${listpc.costanyotherexpensesaspertherequirementofgmpghpiso14000iso22000h}" disabled="" name=""></td>
                                  

                            </tr>

                             <tr>
                                <td>Total</td>
                                    <td><input type="text" class="form-control" id="" value="${listpc.costtotal}" disabled="" name=""></td>
                                  

                            </tr>
                           </c:forEach>
                        </tbody>
                    </table> 
                           


                          

                     <td><strong>Means of Finance</strong></td>
                       </tr>
                        <table class="table table-stripped table-bordered directors-table" id="customFields">
                        <thead>
                            <tr>
                                <th>Items</th>
                        
                                <th>Cost (In Lakh)</th>
                                
                            </tr>
                        </thead>
                        <tbody>
                       <c:forEach var="listmftqm" items="${meansofFinancingTQM}" varStatus="counter">
                            <tr>
                                <td><strong>Promoter's Equity</strong></td>
                                 
                                <td><input type="text" class="form-control" id="" value="${listmftqm.costpromotersequity}" disabled="" name=""></td>
                        
                             
                            </tr>
                             <tr>
                                <td><strong>Term Loan</strong></td>
                                
                                <td><input type="text" class="form-control" id="" value="${listmftqm.costtermloan}" disabled="" name=""></td>
                         
                            
                            </tr>
                             <tr>
                                <td><strong>Working Capital</strong></td>
                                
                                <td><input type="text" class="form-control" id="" value="${listmftqm.costworkingcapital}" disabled="" name=""></td>
                         
                            </tr>
                             <tr>
                                <td><strong>Others</strong></td>
                                
                               <td><input type="text" class="form-control" id="" value="${listmftqm.costothers}" disabled="" name=""></td>
                             
                            
                            </tr>
                           
                             </c:forEach>
                        </tbody>
                    </table> 
                      <td><strong>Implementation Schedule</strong></td>
                       </tr>
                        <table class="table table-stripped table-bordered directors-table" id="customFields">
                        <thead>
                            <tr>
                                <th>Items</th>
                        
                                <th>Details</th>
                                
                            </tr>
                        </thead>
                        <tbody>
                         <c:forEach var="listis" items="${implementationScheduleTQM}" varStatus="counter">
                            <tr>
                                <td><strong>Items of Work</strong></td>
                                 
                                <td><input type="text" class="form-control" id="" value="${listis.detailsitemsofwork}" disabled="" name=""></td>
                        
                             
                            </tr>
                             <tr>
                                <td><strong>Date of Implementation</strong></td>
                                
                                <td><input type="text" class="form-control" id="" value="${listis.detailsdateofimplementation}" disabled="" name=""></td>
                         
                            
                            </tr>
                            
                           
                            </c:forEach>
                        </tbody>
                    </table> 
                           <tr>
                                <td>Details and Experience of the Consultant (if any)</td>
                                <td><input type="text" class="form-control" id="" value="${foodTQMprojectdetails.detailsandexperienceoftheconsultant}" disabled="" name=""></td>
                            </tr> 
                            <tr>
                                <td>Details and Experience of Certifying Body</td>
                                <td><input type="text" class="form-control" id="" value="${foodTQMprojectdetails.detailsandexperienceofcertifyingbody}" disabled="" name=""></td>
                            </tr>
                            <tr>
                                <td>Details of Accreditation Body</td>
                                <td><input type="text" class="form-control" id="" value="${foodTQMprojectdetails.detailsofaccreditationbody}" disabled="" name=""></td>
                            </tr>
                    

                            
                </div>
            </div>
        </div>
																						</div>


																										<h4 class="card-title mb-4 mt-4 text-center">Documents</h4>
																										 <div class="isf-form mt-4">
        <hr class="mt-2">
        <div class="row">
            <div class="col-sm-12">
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <td width="50%"><strong>Detailed Project Report</strong></td>
                            
                                <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.detailedprojectreport}">${foodDocumentTQM.detailedprojectreport}</a></td>
                            </tr>
                            <tr>
                                <td width="50%"><strong>Income Tax Returns of the proprietor/organization for previous three years</strong></td>
                            
                                 <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.incometaxreturnsoftheproprietororganizationforpreviousthreeye}">${foodDocumentTQM.incometaxreturnsoftheproprietororganizationforpreviousthreeye}</a></td>
                            </tr>
                            <tr>
                                <td width="50%"><strong>Udyog aadhar / Udyam Registration</strong></td>
                            
                                <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.udyogaadharudyamregistration}">${foodDocumentTQM.udyogaadharudyamregistration}</a></td>
                            </tr>
                           <tr>
                                <td width="50%"><strong>FSSAI License</strong></td>
                            
                               <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.fssailicense}">${foodDocumentTQM.fssailicense}</a></td>
                            </tr>
                            <tr>
                                <td width="50%"><strong>Bio-Data/Experience of consultant</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.biodataexperienceofconsultant}">${foodDocumentTQM.biodataexperienceofconsultant}</a></td>
                            </tr>
                            <tr>
                                <td width="50%"><strong>Quotation of consultant</strong></td>
                            
                                <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.quotationofconsultant}">${foodDocumentTQM.quotationofconsultant}</a></td>
                            </tr>
                           <tr>
                                <td width="50%"><strong>Consultant Registration Details</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.consultantregistrationdetails}">${foodDocumentTQM.consultantregistrationdetails}</a></td>
                            </tr>
                            <tr>
                                <td width="50%"><strong>Quotation from Certification Body along with the details of the Certification Body</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.quotationfromcertificationbodyalongwiththedetailsofthecertifi}">${foodDocumentTQM.quotationfromcertificationbodyalongwiththedetailsofthecertifi}</a></td>
                            </tr>
                            <tr>
                                <td width="50%"><strong>Certification Agency should be  accredited by the QCI or National Accreditation for Certification Bodies or FSSAI</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.divcertificationagencyshouldbenbspaccreditedbytheqciornationa}">${foodDocumentTQM.divcertificationagencyshouldbenbspaccreditedbytheqciornationa}</a></td>
                            </tr>
                            <tr>
                                <td width="50%"><strong>Details of Plant & Machinery as per Gap Study Report counter signed by consultant</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.detailsofplantampmachineryaspergapstudyreportcountersignedbyc}">${foodDocumentTQM.detailsofplantampmachineryaspergapstudyreportcountersignedbyc}</a></td>
                            </tr>
                            <tr>
                                <td width="50%"><strong>Details of Plant & Machinery  quotations duly certified by approved Chartered Engineer (Mech.)</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.detailsofplantampmachinerynbspquotationsdulycertifiedbyapprov}">${foodDocumentTQM.detailsofplantampmachinerynbspquotationsdulycertifiedbyapprov}</a></td>
                            </tr>
                             <tr>
                                <td width="50%"><strong>Details of Technical Civil Works as per Gap Study Report approved by  Chartered Engineer (civil.) and countersigned by consultant</strong></td>
                            
                               <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp}">${foodDocumentTQM.detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp}</a></td>
                            </tr>
                             <tr>
                                <td width="50%"><strong>Gap Study Report as per (Annexure-19)</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.gapstudyreportasper}">${foodDocumentTQM.gapstudyreportasper}</a></td>
                            </tr>
                            
                             <tr>
                                <td width="50%"><strong>Affidavit as per(Annexure-20)</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.affidavitasper}">${foodDocumentTQM.affidavitasper}</a></td>
                            </tr>


                             <tr>
                                <td width="50%"><strong>Implementation schedule of the HACCP/ISO 9000/ ISO 22000/GMP/GHP system Implementation</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.implementationscheduleofthehaccpiso9000iso22000gmpghpsystemim}">${foodDocumentTQM.implementationscheduleofthehaccpiso9000iso22000gmpghpsystemim}</a></td>
                            </tr>
                             <tr>
                                <td width="50%"><strong>The applicant organization is required to give an undertaking that the training of their employees on implementation of ISO 14000/ISO 22000/HACCP/GMP/GHP/Patent will be obtained from the consultant</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.theapplicantorganizationisrequiredtogiveanundertakingthatthet}">${foodDocumentTQM.theapplicantorganizationisrequiredtogiveanundertakingthatthet}</a></td>
                            </tr>
                             <tr>
                                <td width="50%"><strong>Process of manufacture (with Flow Chart)</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.processofmanufacture}">${foodDocumentTQM.processofmanufacture}</a></td>
                            </tr>
                            
                             <tr>
                                <td width="50%"><strong>List of existing Plant & Machinery and Quality Control facilities with cost</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.listofexistingplantampmachineryandqualitycontrolfacilitieswit}">${foodDocumentTQM.listofexistingplantampmachineryandqualitycontrolfacilitieswit}</a></td>
                            </tr>
                            
                             <tr>
                                <td width="50%"><strong>Patent Certificate issued by Competent Authority</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.patentcertificateissuedbycompetentauthority}">${foodDocumentTQM.patentcertificateissuedbycompetentauthority}</a></td>
                            </tr>
                            
                             <tr>
                                <td width="50%"><strong>Declaration upload</strong></td>
                            <td><a href="./downloadfoodDocumentTQM?fileName=${foodDocumentTQM.declarationupload}">${foodDocumentTQM.declarationupload}</a></td>
                            </tr>
                      </div>
            </div>
        </div>
    </div>
</tbody>
</table>



																																<!-- End view All Application Details  -->



																																<!-- Start Annexure-I  -->

																																<div class="parameter-properties"
																																	id="Annexure-I">
																																	<hr class="mt-2">
																																	<h4
																																		class="card-title mb-4 mt-4 text-center">Annexure-I</h4>
																																	<p class="mt-4 mb-1 text-center">
																																		<strong>APPLICATION FORM FOR
																																			SANCTION/DISBURSAL OF INCENTIVES FOR
																																			SMALL, MEDIUM & LARGE INDUSTRIAL
																																			UNDERTAKINGS</strong>
																																	</p>
																																	<p class="mb-2 text-center">
																																		<i>(all supporting documents must
																																			be authenticated by a
																																			Director/Partner/Officer duly
																																			authorized by the Competent authority
																																			of the Applicant on its behalf)</i>
																																	</p>
																																	<p class="mb-4 text-center">
																																		<strong>Category of
																																			Industrial Undertaking Applied For
																																			Based on Capital Investment &
																																			Location</strong>
																																	</p>


																																	<div class="row">
																																		<div class="col-sm-12">
																																			<div class="table-responsive">
																																				<table class="table table-bordered">
																																					<tbody>
																																						<tr>
																																							<td colspan="2"
																																								style="width: 17%;">Criteria
																																								for investment</td>
																																							<td rowspan="2">Investment
																																								in Plant and Machinery as
																																								defined in MSMED Act, 2006</td>
																																							<td rowspan="2">More than Rs
																																								25 lakhs but does not exceed Rs
																																								5 Crore</td>
																																							<td rowspan="2">More than Rs
																																								5 crores but does not exceed Rs.
																																								10 crores</td>
																																						</tr>
																																						<tr>
																																							<td class="text-center">Small</td>
																																							<td class="text-center">Medium</td>
																																						</tr>
																																						<tr>
																																							<td class="text-center">
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="Small-p"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="Small-p"></label>
																																								</div>
																																							</td>
																																							<td class="text-center">
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="Medium-p"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="Medium-p"></label>
																																								</div>
																																							</td>
																																							<td></td>
																																							<td class="text-center">
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="25exceedRs"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="25exceedRs"></label>
																																								</div>
																																							</td>
																																							<td class="text-center">
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="5exceedRs"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="5exceedRs"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td colspan="2">Investment
																																								amount</td>
																																							<td colspan="3"></td>
																																						</tr>
																																					</tbody>
																																				</table>
																																			</div>
																																		</div>
																																	</div>

																																	<div class="row mt-4">
																																		<div class="col-sm-12">
																																			<div class="table-responsive">
																																				<table class="table table-bordered">
																																					<tbody>
																																						<tr>
																																							<td style="width: 17%;">Criteria
																																								for investment of Large</td>
																																							<td colspan="3">Eligible
																																								Capital Investment as Defined in
																																								the Rules</td>
																																						</tr>
																																						<tr>
																																							<td rowspan="2">Beyond the
																																								investment amount in Plant &
																																								Machinery for Medium Industrial
																																								Undertakings (Rs 10 Crore) and
																																								upto capital investment Rs</td>
																																							<td>Bundelkhand &
																																								Poorvanchal Investment amount
																																								upto Rs 100 Crore</td>
																																							<td>Madhyanchal &
																																								Paschimanchal (except Gautam
																																								Buddh Nagar & Ghaziabad) <br>Investment
																																								amount upto Rs 150 Crore
																																							</td>
																																							<td>Gautam Buddh Nagar &
																																								Ghaziabad <br>Investment
																																								amount upto Rs 200 Crore
																																							</td>
																																						</tr>
																																						<tr>
																																							<td class="text-center">
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="Bundelkhand"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="Bundelkhand"></label>
																																								</div>
																																							</td>
																																							<td class="text-center">
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="Madhyanchal"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="Madhyanchal"></label>
																																								</div>
																																							</td>
																																							<td class="text-center">
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="Ghaziabad"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="Ghaziabad"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td>Investment amount</td>
																																							<td></td>
																																							<td></td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>District/ Area</td>
																																							<td></td>
																																							<td></td>
																																							<td></td>
																																						</tr>
																																					</tbody>
																																				</table>
																																			</div>
																																		</div>
																																	</div>

																																	<div class="row mt-4">
																																		<div class="col-sm-12">
																																			<div class="table-responsive">
																																				<table class="table table-bordered">
																																					<thead>
																																						<tr>
																																							<th style="width: 7%;">Sl No</th>
																																							<th style="width: 27%;">Particulars</th>
																																							<th>Details</th>
																																							<th style="width: 30%;">Relevant
																																								Documentary Support</th>
																																						</tr>
																																					</thead>
																																					<tbody>
																																						<tr>
																																							<td>1.</td>
																																							<td>Name, Address& Contact
																																								Details of the Applicant</td>
																																							<td></td>
																																							<td>Certificate of
																																								Incorporation, registered
																																								partnership deed, trust /society
																																								registration deed.</td>
																																						</tr>
																																						<tr>
																																							<td>2.</td>
																																							<td>Constitution of
																																								Applicant</td>
																																							<td></td>
																																							<td>Company/Partnership
																																								Firm/Others
																																								(MoA/Articles/By-Laws, etc)</td>
																																						</tr>
																																						<tr>
																																							<td>3.</td>
																																							<td>Location of the
																																								Existing/ Proposed Industrial
																																								Undertaking</td>
																																							<td></td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>4.</td>
																																							<td>Names Address& Contact
																																								Details of Directors/Partners
																																								/Others</td>
																																							<td></td>
																																							<td>PAN& DIN numbers
																																								(Supported by relevant
																																								documents)</td>
																																						</tr>
																																						<tr>
																																							<td>5.</td>
																																							<td>PAN & GSTIN of Applicant</td>
																																							<td></td>
																																							<td>Supported by relevant
																																								documents</td>
																																						</tr>
																																						<tr>
																																							<td>6.</td>
																																							<td>Status of Industrial
																																								Undertaking</td>
																																							<td></td>
																																							<td>New
																																								/Expansion/Diversification</td>
																																						</tr>
																																						<tr>
																																							<td>7.</td>
																																							<td>Nature of Industry</td>
																																							<td></td>
																																							<td>Industrial
																																								Categorization as per ID&R
																																								Act/NIC</td>
																																						</tr>
																																						<tr>
																																							<td>8.</td>
																																							<td>Registration or License
																																								for setting up Industrial
																																								Undertaking</td>
																																							<td></td>
																																							<td>Enclose UAM (for MSME) /
																																								IEM/IL copy (for large)</td>
																																						</tr>
																																					</tbody>
																																				</table>
																																			</div>

																																			<div class="table-responsive">
																																				<table class="table table-bordered">
																																					<tbody>
																																						<tr>
																																							<td>9.</td>
																																							<td colspan="3">Details of
																																								Existing/Proposed products to be
																																								manufactured and its capacity</td>
																																						</tr>
																																						<tr>
																																							<td colspan="4">
																																								<div class="table-responsive">
																																									<table
																																										class="table table-bordered">
																																										<thead>
																																											<tr>
																																												<th>Sl. No.</th>
																																												<th>Existing Products</th>
																																												<th>Existing Installed
																																													Capacity</th>
																																												<th>Proposed Products</th>
																																												<th>Proposed Installed
																																													Capacity</th>
																																												<th>Existing Gross
																																													Block</th>
																																												<th>Proposed Gross
																																													Block</th>
																																											</tr>
																																										</thead>
																																										<tbody>
																																											<tr>
																																												<td>&nbsp;</td>
																																												<td>&nbsp;</td>
																																												<td>&nbsp;</td>
																																												<td>&nbsp;</td>
																																												<td>&nbsp;</td>
																																												<td>&nbsp;</td>
																																												<td>&nbsp;</td>
																																											</tr>
																																										</tbody>
																																									</table>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td>10.</td>
																																							<td>Proposed Proposed Date
																																								of Commencement of Commercial
																																								Production</td>
																																							<td style="width: 12%;"></td>
																																							<td style="width: 12%;"></td>
																																						</tr>
																																						<tr>
																																							<td>11.</td>
																																							<td>Proposed Capital
																																								Investment</td>
																																							<td></td>
																																							<td>DPR</td>
																																						</tr>
																																						<tr>
																																							<td>11.1</td>
																																							<td>Date from which capital
																																								investment has commenced, or is
																																								proposed to 17 be commenced
																																								(Cut-off date as opted)</td>
																																							<td></td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>11.2</td>
																																							<td>Is the capital
																																								investment proposed in phases</td>
																																							<td></td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>11.3</td>
																																							<td>Phase-wise details of
																																								Proposed Investment and dates of
																																								start of commercial production</td>
																																							<td></td>
																																							<td></td>
																																						</tr>
																																					</tbody>
																																				</table>
																																			</div>

																																			<p class="mb-4 mt-3">
																																				<strong>12. BENEFITS
																																					REQUESTED BY APPLICANT</strong>
																																			</p>
																																			<div class="table-responsive">
																																				<table class="table table-bordered">
																																					<thead>
																																						<tr>
																																							<th>Sl. No.</th>
																																							<th>Item</th>
																																							<th>Quantum (Rs crores)</th>
																																						</tr>
																																					</thead>
																																					<tbody>
																																						<tr>
																																							<td>12.1</td>
																																							<td>Aggregate Quantum of
																																								Fiscal Benefits</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td colspan="3"><strong
																																								class="text-info">DETAILS
																																									of BENEFITS</strong></td>
																																						</tr>
																																						<tr>
																																							<td>12.2</td>
																																							<td>Reimbursement of
																																								deposited GST</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.3</td>
																																							<td>Additional Reimbursement
																																								of deposited GST</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.3.1</td>
																																							<td>10% GST where 25%
																																								minimum SC/ST workers employed
																																								subject to minimum of 400 total
																																								workers in industrial
																																								undertakings located in
																																								Paschimanchal and minimum of 200
																																								total workers in B-P-M</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.3.2</td>
																																							<td>10% GST where 40%
																																								minimum female workers employed
																																								subject to minimum of 400 total
																																								workers in industrial
																																								undertakings located in
																																								Paschimanchal and minimum of 200
																																								total workers in B-P-M</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.3.3</td>
																																							<td>10% GST where 25%
																																								minimum BPL workers employed
																																								subject to minimum of 400 total
																																								workers in industrial
																																								undertakings located in
																																								Paschimanchal and minimum of 200
																																								total workers in B-P-M</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.4</td>
																																							<td>Stamp Duty Exemption</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.4.1</td>
																																							<td>Additional Stamp Duty
																																								exemption @20% upto maximum of
																																								100% in case of industrial
																																								undertakings having 75% equity
																																								owned by Divyang/SC/ ST/Females
																																								Promoters</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.5</td>
																																							<td>EPF Reimbursement (100
																																								or more unskilled workers)</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.5.1</td>
																																							<td>Additional 10% EPF
																																								Reimbursement (200 direct
																																								skilled and unskilled workers)</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.5.2</td>
																																							<td>Additional 10% EPF
																																								Reimbursement upto maximum of
																																								70% in case of industrial
																																								undertakings having 75% equity
																																								owned by Divyang/SC/CT/Females
																																								Promoters</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.6</td>
																																							<td>Capital Interest Subsidy</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.6.1</td>
																																							<td>Additional Capital
																																								Interest Subsidy@2.5% upto
																																								maximum of 7.5% in case of
																																								industrial undertakings having
																																								75% equity owned by
																																								Divyang/SC/CT/Females Promoters</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.7</td>
																																							<td>Infrastructure Interest
																																								Subsidy</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.7.1</td>
																																							<td>Additional
																																								Infrastructure Interest Subsidy
																																								@2.5% upto 18 maximum of 7.5% in
																																								case of industrial undertakings
																																								having 75% equity owned by
																																								Divyang/SC/CT/Females Promoters</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.8</td>
																																							<td>Interest Subsidy on
																																								loans for industrial research,
																																								quality improvement, etc.</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.9</td>
																																							<td>Reimbursement of
																																								Disallowed Input Tax Credit on
																																								plant, building materials, and
																																								other capital goods.</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.10</td>
																																							<td>Exemption from
																																								Electricity Duty from captive
																																								power for self-use</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.11</td>
																																							<td>Exemption from
																																								Electricity duty on power drawn
																																								from power companies</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.12</td>
																																							<td>Exemption from Mandi Fee</td>
																																							<td></td>
																																						</tr>
																																					</tbody>
																																				</table>
																																			</div>

																																			<p class="mb-4 text-center mt-3">
																																				<strong>Declaration</strong>
																																			</p>
																																			<p>The above information are
																																				completely true and no fact has been
																																				concealed or misrepresented. It is
																																				further certified that the company
																																				has not applied for benefits of the
																																				above nature under any
																																				sector-specific or other policy of
																																				the Govt of UP for purpose of
																																				availing benefits of the above
																																				nature.</p>
																																			<p>I/we hereby agree that I/we
																																				shall forthwith repay the benefits
																																				released to me/us under Rules of
																																				Policy for Promotion of Industrial
																																				Investment and Employment-2017, if
																																				the said benefits are found to be
																																				disbursed in excess of the amount
																																				actually admissible whatsoever the
																																				reason.</p>

																																		</div>
																																	</div>

																																	<div class="row mt-4 mb-5">
																																		<div class="col-md-6">
																																			<p class="mb-0">
																																				<strong>Date:</strong>
																																			</p>
																																			<p>
																																				<strong>Place:</strong>
																																			</p>
																																		</div>
																																		<div class="col-md-6 text-right">
																																			<p class="mb-0">
																																				<strong>Signature of
																																					Authorised Signatory with</strong>
																																			</p>
																																			<p>
																																				<strong>Name, Designation
																																					and Office Seal</strong>
																																			</p>
																																		</div>
																																	</div>

																																	<div class="row mt-4 mb-5">
																																		<div class="col-md-12">
																																			<p>
																																				<strong>Supporting
																																					Documents:</strong>
																																			</p>
																																		</div>
																																		<div class="col-md-12">
																																			<p>(a) UAM/ IEM/IL acknowledgment</p>
																																			<p>(b) Detailed Project Report
																																				(DPR) prepared by external
																																				consultant / Chartered Accountant</p>
																																			<p>(c) Charted Accountant’s
																																				Certificate for existing gross block
																																				industrial undertaking.</p>
																																			<p>(d) Chartered Engineer’s
																																				Certified List of Fixed Assets of
																																				existing industrial undertaking in
																																				support of gross block.</p>
																																			<p>(e) Notarized Undertaking (as
																																				per format placed at Annexure I-A)
																																				on Stamp Paper of Rs. 10</p>
																																		</div>
																																	</div>



																																</div>

																																<!-- End Annexure-I  -->


																																<!-- Start Annexure-III -->

																																<div class="parameter-properties"
																																	id="Annexure-III">
																																	<hr class="mt-2">
																																	<h4
																																		class="card-title mb-4 mt-4 text-center">Annexure-III</h4>
																																	<p class="mt-4 mb-1 text-center">
																																		<strong>APPLICATION FORM FOR
																																			MEGA, MEGA PLUS AND SUPER MEGA
																																			INDUSTRIAL UNDERTAKINGS</strong>
																																	</p>
																																	<p class="mb-2 text-center">
																																		<i>(all supporting documents must
																																			be authenticated by a
																																			Director/Partner/Officer duly
																																			authorized by the Competent authority
																																			of the Applicant on its behalf)</i>
																																	</p>
																																	<p class="mb-4 text-center">
																																		<strong>Category of
																																			Industrial Undertaking Applied For
																																			Based on Capital Investment, Location
																																			and Employment Proposed</strong>
																																	</p>
																																	<p class="mb-4 mt-5">
																																		<strong>A. Based on
																																			Locational Investment</strong>
																																	</p>


																																	<div class="row mt-4">
																																		<div class="col-sm-12">
																																			<div class="table-responsive">
																																				<table class="table table-bordered">
																																					<thead>
																																						<tr>
																																							<th>Criteria for investment</th>
																																							<th>Investment amount</th>
																																							<th colspan="3"
																																								class="text-center">District/Region</th>
																																							<th>Category of industrial
																																								undertaking</th>
																																							<th>Tick as applicable</th>
																																						</tr>
																																					</thead>
																																					<tbody>
																																						<tr>
																																							<td></td>
																																							<td></td>
																																							<td>Bundelkhand, Poorvanchal</td>
																																							<td>Madhyanchal,
																																								Paschimanchal (except
																																								GautamBuddh Nagar & Ghaziabad)</td>
																																							<td>GautamBuddh Nagar &
																																								Ghaziabad</td>
																																							<td></td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td rowspan="3">Capital
																																								Investment as Defined in the
																																								Rules</td>
																																							<td rowspan="3">Beyond the
																																								investment amount for Large
																																								Industrial Undertakings and upto
																																								capital investment of Rs…..,</td>
																																							<td>>100<250</td>
																																							<td>>150<300</td>
																																							<td>>200<500</td>
																																							<td>MEGA</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII1"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII1"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td>=>250<500</td>
																																							<td>=>300<750</td>
																																							<td>=>500<1000</td>
																																							<td>MEGA PLUS</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII2"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII2"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td>=>500</td>
																																							<td>=>750</td>
																																							<td>=>1000</td>
																																							<td>SUPER MEGA</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII3"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII3"></label>
																																								</div>
																																							</td>
																																						</tr>
																																					</tbody>
																																				</table>
																																			</div>
																																		</div>
																																	</div>

																																	<p class="mb-4 text-center mt-2">
																																		<strong>OR</strong>
																																	</p>
																																	<p class="mb-4 mt-2">
																																		<strong>B. Based on
																																			Locational Employment</strong>
																																	</p>



																																	<div class="row mt-4">
																																		<div class="col-sm-12">
																																			<div class="table-responsive">
																																				<table class="table table-bordered">
																																					<thead>
																																						<tr>
																																							<th>PROPOSED NO. OF WORKERS</th>
																																							<th>REGION</th>
																																							<th>QUALIFYING CATEGORY OF
																																								INDUSTRIAL UNDERTAKING</th>
																																							<th>TICK AS APPLICABLE</th>
																																						</tr>
																																					</thead>
																																					<tbody>
																																						<tr>
																																							<td>500</td>
																																							<td>Bundelkhand &
																																								Poorvanchal</td>
																																							<td>MEGA</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII4"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII4"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td>750</td>
																																							<td>Madhyanchal &
																																								Paschimanchal (excluding
																																								GautamBuddh Nagar & Ghaziabad)</td>
																																							<td>MEGA</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII5"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII5"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td rowspan="2">1000</td>
																																							<td>Bundelkhand &
																																								Poorvanchal</td>
																																							<td>MEGA PLUS</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII6"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII6"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td>GautamBuddh Nagar &
																																								Ghaziabad</td>
																																							<td>MEGA</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII7"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII7"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td>1500</td>
																																							<td>Madhyanchal &
																																								Paschimanchal (excluding
																																								GautamBuddh Nagar & Ghaziabad)</td>
																																							<td>MEGA PLUS</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII8"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII8"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td rowspan="2">2000</td>
																																							<td>Bundelkhand &
																																								Poorvanchal</td>
																																							<td>SUPER MEGA</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII9"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII9"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td>GautamBuddh Nagar &
																																								Ghaziabad</td>
																																							<td>MEGA PLUS</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII10"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII10"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td>3000</td>
																																							<td>Madhyanchal &
																																								Paschimanchal (excluding
																																								GautamBuddh Nagar & Ghaziabad)</td>
																																							<td>SUPER MEGA</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII11"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII11"></label>
																																								</div>
																																							</td>
																																						</tr>
																																						<tr>
																																							<td>4000</td>
																																							<td>GautamBuddh Nagar &
																																								Ghaziabad</td>
																																							<td>SUPER MEGA</td>
																																							<td>
																																								<div
																																									class="custom-control custom-checkbox custom-control-inline">
																																									<input type="checkbox"
																																										class="custom-control-input"
																																										id="checkIII12"
																																										name="Capital-Interest-Subsidy">
																																									<label
																																										class="custom-control-label"
																																										for="checkIII12"></label>
																																								</div>
																																							</td>
																																						</tr>
																																					</tbody>
																																				</table>
																																			</div>
																																		</div>
																																	</div>

																																	<div class="row mt-4">
																																		<div class="col-sm-12">
																																			<div class="table-responsive">
																																				<table class="table table-bordered">
																																					<tbody>
																																						<tr>
																																							<td>1.</td>
																																							<td>Name, Address& Contact
																																								Details of the Applicant</td>
																																							<td style="width: 12%;"></td>
																																							<td>Certificate of
																																								Incorporation, registered
																																								partnership deed, trust /society
																																								registration deed.</td>
																																						</tr>
																																						<tr>
																																							<td>2.</td>
																																							<td>Constitution of
																																								Applicant</td>
																																							<td></td>
																																							<td>Company/Partnership
																																								Firm/Others(MoA/Articles/Byelaws,
																																								etc.)</td>
																																						</tr>
																																						<tr>
																																							<td>3.</td>
																																							<td>Location of the
																																								Existing/proposed Industrial
																																								Undertaking</td>
																																							<td></td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>4.</td>
																																							<td>Names, address & contact
																																								details of
																																								Directors/Partners/Others</td>
																																							<td></td>
																																							<td>PAN & DIN numbers
																																								(Supported by relevant document)</td>
																																						</tr>
																																						<tr>
																																							<td>5.</td>
																																							<td>PAN & GSTIN of Applicant</td>
																																							<td></td>
																																							<td>Supported by relevant
																																								document</td>
																																						</tr>
																																						<tr>
																																							<td>6.</td>
																																							<td>Status of Industrial
																																								Undertaking</td>
																																							<td></td>
																																							<td>New/Expansion/Diversification</td>
																																						</tr>
																																						<tr>
																																							<td>7.</td>
																																							<td>Nature of Industry</td>
																																							<td></td>
																																							<td>Industrial
																																								Categorization as per ID&R
																																								Act/NIC</td>
																																						</tr>
																																						<tr>
																																							<td>8.</td>
																																							<td>Registration or License
																																								for setting up Industrial
																																								Undertaking</td>
																																							<td></td>
																																							<td>Enclose acknowledgement
																																								of IEM/ IL</td>
																																						</tr>

																																					</tbody>
																																				</table>
																																			</div>
																																		</div>
																																	</div>


																																	<div class="row mt-4">
																																		<div class="col-sm-12">
																																			<div>
																																				<div class="table-responsive">
																																					<table class="table table-bordered">
																																						<tbody>
																																							<tr>
																																								<td>9.</td>
																																								<td colspan="6">Details of
																																									existing/proposed products to
																																									be manufactured and its
																																									capacity (Enclose Detailed
																																									Project Report prepared by
																																									External Consultant/Chartered
																																									Accountants)</td>
																																							</tr>
																																							<tr>
																																								<td>Sl. No.</td>
																																								<td>Existing Products</td>
																																								<td>Existing Installed
																																									Capacity</td>
																																								<td>Proposed Products</td>
																																								<td>Proposed Installed
																																									Capacity</td>
																																								<td>Existing Gross Block</td>
																																								<td>Proposed Gross Block</td>
																																							</tr>
																																							<tr>
																																								<td>&nbsp;</td>
																																								<td>&nbsp;</td>
																																								<td>&nbsp;</td>
																																								<td>&nbsp;</td>
																																								<td>&nbsp;</td>
																																								<td>&nbsp;</td>
																																								<td>&nbsp;</td>
																																							</tr>
																																						</tbody>
																																					</table>
																																				</div>
																																			</div>
																																		</div>
																																	</div>


																																	<div class="row mt-4">
																																		<div class="col-sm-12">
																																			<div class="table-responsive">
																																				<table class="table table-bordered">
																																					<tbody>
																																						<tr>
																																							<td>10.</td>
																																							<td>Proposed Proposed Date
																																								of Commencement of Commercial
																																								Production after
																																								Expansion/Diversification</td>
																																							<td style="width: 12%;"></td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>11.</td>
																																							<td>Proposed Capital
																																								Investment</td>
																																							<td></td>
																																							<td>DPR</td>
																																						</tr>
																																						<tr>
																																							<td>11.1</td>
																																							<td>Date from which capital
																																								investment has 28 commenced, or
																																								is proposed to commence (Cut-off
																																								date, as opted)</td>
																																							<td></td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>11.2</td>
																																							<td>Is the capital
																																								investment proposed in phases</td>
																																							<td></td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>11.3</td>
																																							<td>Phase-wise details of
																																								proposed Investment and dates of
																																								start of commercial production</td>
																																							<td></td>
																																							<td>DPR</td>
																																						</tr>
																																					</tbody>
																																				</table>
																																			</div>
																																		</div>
																																	</div>


																																	<div class="row mt-4">
																																		<div class="col-sm-12">
																																			<p class="mb-4 mt-3">
																																				<strong>12. BENEFITS
																																					REQUESTED BY APPLICANT </strong>
																																			</p>
																																			<div class="table-responsive">
																																				<table class="table table-bordered">
																																					<thead>
																																						<tr>
																																							<th>Sl. No.</th>
																																							<th>Item</th>
																																							<th>Quantum (Rs.,crores) )</th>
																																						</tr>
																																					</thead>
																																					<tbody>
																																						<tr>
																																							<td>12.1</td>
																																							<td>Aggregate Quantum of
																																								Fiscal Benefits</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td colspan="3"><strong
																																								class="text-info">BENEFITS
																																									REQUESTED</strong></td>
																																						</tr>
																																						<tr>
																																							<td>12.2</td>
																																							<td>Reimbursement of
																																								deposited GST</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.3</td>
																																							<td>Additional Reimbursement
																																								of deposited GST</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.3.1</td>
																																							<td>10% SGST where 25%
																																								minimum SC/ST workers employed
																																								subject to minimum of 400 total
																																								workers in industrial
																																								undertakings located in
																																								Paschimanchal and minimum of 200
																																								total workers in B-P-M</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.3.2</td>
																																							<td>10% SGST where 40%
																																								minimum female workers employed
																																								subject to minimum of 400 total
																																								workers in industrial
																																								undertakings located in
																																								Paschimanchal and minimum of 200
																																								total workers in B-P-M</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.3.3</td>
																																							<td>10% SGST where 25%
																																								minimum BPL workers employed
																																								subject to minimum of 400 total
																																								workers in industrial
																																								undertakings located in
																																								Paschimanchal and minimum of 200
																																								total workers in B-P-M</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.4</td>
																																							<td>Stamp Duty Exemption</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.4.1</td>
																																							<td>Additional Stamp Duty
																																								exemption @20% upto maximum of
																																								100% in case of industrial
																																								undertakings having 75% equity
																																								owned by Divyang/SC/CT/Females
																																								Promoters</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.5</td>
																																							<td>EPF Reimbursement (100
																																								or more unskilled workers)</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.5.1</td>
																																							<td>Addl. 10% EPF
																																								Reimbursement (200 direct
																																								skilled and unskilled workers)</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.5.2</td>
																																							<td>Addl. 10% EPF
																																								Reimbursement upto maximum of
																																								70% in case of industrial
																																								undertakings having 75% equity
																																								owned by Divyang/SC/CT/Female
																																								Promoters</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.6</td>
																																							<td>Capital Interest Subsidy</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.6.1</td>
																																							<td>Additional Capital
																																								Interest Subsidy@2.5% upto
																																								maximum of 7.5% in case of
																																								industrial undertakings having
																																								75% equity owned by
																																								Divyang/SC/CT/Females Promoters</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.7</td>
																																							<td>Infrastructure Interest
																																								Subsidy</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.7.1</td>
																																							<td>Additional
																																								Infrastructure Interest Subsidy
																																								@2.5% upto maximum of 7.5% in
																																								case of industrial undertakings
																																								having 75% equity owned by
																																								Divyang/SC/CT/Females Promoters</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.8</td>
																																							<td>Interest Subsidy on
																																								loans for industrial research,
																																								quality improvement, etc.</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.9</td>
																																							<td>Reimbursement of
																																								Disallowed Input Tax Credit on
																																								plant, building materials, and
																																								other capital goods.</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.10</td>
																																							<td>Exemption from
																																								Electricity Duty from captive
																																								power for self-use</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.11</td>
																																							<td>Exemption from
																																								Electricity duty on power drawn
																																								from power companies</td>
																																							<td></td>
																																						</tr>
																																						<tr>
																																							<td>12.12</td>
																																							<td>Exemption from Mandi Fee</td>
																																							<td></td>
																																						</tr>
																																					</tbody>
																																				</table>
																																			</div>
																																		</div>
																																	</div>


																																	<div class="row mt-4">
																																		<div class="col-sm-12">
																																			<p class="mb-4 text-center mt-3">
																																				<strong>Declaration</strong>
																																			</p>
																																			<p>The above information are
																																				completely true and no fact has been
																																				concealed or misrepresented. It is
																																				further certified that the company
																																				has not applied for benefits of the
																																				above nature under any
																																				sector-specific or other policy of
																																				the Govt of UP for purpose of
																																				availing benefits of the above
																																				nature.</p>
																																			<p>I/we hereby agree that I/we
																																				shall forthwith repay the benefits
																																				released to me/us under Rules of
																																				Policy for Promotion of Industrial
																																				Investment and Employment-2017, if
																																				the said benefits are found to be
																																				disbursed in excess of the amount
																																				actually admissible whatsoever the
																																				reason.</p>

																																		</div>
																																	</div>

																																	<div class="row mt-4 mb-5">
																																		<div class="col-md-6">
																																			<p class="mb-0">
																																				<strong>Date:</strong>
																																			</p>
																																			<p>
																																				<strong>Place:</strong>
																																			</p>
																																		</div>
																																		<div class="col-md-6 text-right">
																																			<p class="mb-0">
																																				<strong>Signature of
																																					Authorised Signatory with</strong>
																																			</p>
																																			<p>
																																				<strong>Name, Designation
																																					and Office Seal</strong>
																																			</p>
																																		</div>
																																	</div>

																																	<div class="row mt-4 mb-5">
																																		<div class="col-md-12">
																																			<p>
																																				<strong>Supporting
																																					Documents:</strong>
																																			</p>
																																		</div>
																																		<div class="col-md-12">
																																			<p>(a) UAM/ IEM/IL acknowledgment</p>
																																			<p>(b) Detailed Project Report
																																				(DPR) prepared by external
																																				consultant/Chartered Accountant</p>
																																			<p>(c) Charted Accountant’s
																																				Certificate for existing gross block
																																				industrial undertaking.</p>
																																			<p>(d) Chartered Engineer’s
																																				Certified List of Fixed Assets of
																																				existing industrial undertaking in
																																				support of gross block.</p>
																																			<p>(e) Notarized Undertaking (as
																																				per format placed at Annexure I-A)
																																				on Stamp Paper of Rs. 10</p>
																																		</div>
																																	</div>
																																</div>

																																<!-- End Annexure-III  -->





																																<div class="isf-form mt-4">
																																	<hr class="mt-4 mb-4">
																																	<div class="row mt-4">
																																		<div class="col-sm-12 text-center">
																																			<button type="button"
																																				class="btn btn-outline-success btn-sm mb-3">
																																				<i class="fa fa-download"></i>
																																				Download Only Application Form
																																			</button>

																																			<button type="button"
																																				class="btn btn-outline-secondary btn-sm mb-3">
																																				<i class="fa fa-download"></i>
																																				Download Only Supporting Docs
																																			</button>
																																		</div>
																																	</div>
																																	<hr class="mt-2">
																																	<div class="row">
																																		<div class="col-sm-4">
																																			<a href="./foodProcessIndustryPolicy"
																																				class="common-default-btn mt-3">Back</a>
																																		</div>
																																		<div class="col-sm-8 text-right">
																																			<!-- <a href="javacript:void(0);" onclick="printDiv('printableArea')" class="common-btn mt-3"><i class="fa fa-print"></i> Print</a> -->
																																			<a href="./evaluationViewTQM"
																																				id="evaluateBtn"
																																				class="common-btn mt-3">Evaluate
																																				Application </a>
																																		</div>
																																	</div>
																																</div>




																															</div>
																														</div>
																													</div>
																												</div>
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
																																		<li><a href="#"><i
																																				class="fa fa-facebook-f"></i></a></li>
																																		<li><a href="#"><i
																																				class="fa fa-twitter"></i></a></li>
																																		<li><a href="#"><i
																																				class="fa fa-youtube"></i></a></li>
																																	</ul>
																																</div>
																															</div>
																															<div class="col-sm-12">
																																<div class="footer-menu">
																																	<ul>
																																		<li><a href="index.html"
																																			target="_blank">Home</a></li>
																																		<li><a
																																			href="http://udyogbandhu.com/topics.aspx?mid=About%20us"
																																			target="_blank">About Us</a></li>
																																		<li><a
																																			href="http://udyogbandhu.com/topics.aspx?mid=Disclaimer"
																																			target="_blank">Disclaimer</a></li>
																																		<li><a
																																			href="http://udyogbandhu.com/topics.aspx?mid=General%20Terms%20And%20Conditions"
																																			target="_blank">General Terms And
																																				Conditions</a></li>
																																		<li><a
																																			href="http://udyogbandhu.com/topics.aspx?mid=Privacy%20Policy"
																																			target="_blank">Privacy Policy</a></li>
																																		<li><a
																																			href="http://udyogbandhu.com/topics.aspx?mid=Refund%20Policy"
																																			target="_blank">Refund Policy</a></li>
																																		<li><a
																																			href="http://udyogbandhu.com/topics.aspx?mid=Delivery%20Policy"
																																			target="_blank">Delivery Policy</a></li>
																																		<li><a
																																			href="http://udyogbandhu.com/topics.aspx?mid=Contact%20Us"
																																			target="_blank">Contact Us</a></li>
																																	</ul>
																																</div>
																															</div>
																															<div class="col-sm-12">
																																<div class="row">
																																	<div class="col-sm-3">
																																		<div class="nic-footer-logo">
																																			<img src="images/nic-logo.png"
																																				alt="NIC Logo">
																																		</div>
																																	</div>
																																	<div class="col-sm-6">
																																		<div class="copyright-text">
																																			<p>© 2020 - IT Solution powered
																																				by National Informatics Centre Uttar
																																				Pradesh State Unit</p>
																																			<p>Designed and Developed by
																																				National Informatics Centre ( NIC )</p>
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


																												<div class="container">
																													<div class="modal fade"
																														id="SendtoConcernDepartment">
																														<div class="modal-dialog modal-lg">
																															<div class="modal-content">

																																<!-- Modal Header -->
																																<div class="modal-header">
																																	<h4 class="modal-title">Send to
																																		Concern Department</h4>
																																	<button type="button" class="close"
																																		data-dismiss="modal">&times;</button>
																																</div>

																																<!-- Modal body -->
																																<div class="modal-body">
																																	<div class="row">
																																		<div class="col-sm-12"
																																			style="height: 400px; overflow-y: scroll;">
																																			<!-- gopal  -->
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input id="Agriculture Department"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="adappup2018@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Agriculture Department">Agriculture
																																					Department</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Board of Revenue, Uttar Pradesh"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="borlko@up.nic.in"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Board of Revenue, Uttar Pradesh">Board
																																					of Revenue, Uttar Pradesh</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Commercial Tax Department"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="Puneet.tripathi@gov.in"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Commercial Tax Department">Commercial
																																					Tax Department</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input id="Department of Excise"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="taskupexcise01@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Department of Excise">Department
																																					of Excise</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input id="Department of Labour"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="hq.sankhya@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Department of Labour">Department
																																					of Labour</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Department of Stamp and Registration"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="igruplko@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Department of Stamp and Registration">Department
																																					of Stamp and Registration</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Department of Weights and Measures : Legal Metrology"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="dclmhqlko@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Department of Weights and Measures : Legal Metrology">Department
																																					of Weights and Measures : Legal
																																					Metrology</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Directorate of Electrical Safety"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="electricalsafety.upgovt@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Directorate of Electrical Safety">Directorate
																																					of Electrical Safety</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Food Safety and Drug Administration (Drug)"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="upfdadrug@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Food Safety and Drug Administration (Drug)">Food
																																					Safety and Drug Administration
																																					(Drug)</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Forest and Wildlife Department"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="apccfit-up@nic.in"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Forest and Wildlife Department">Forest
																																					and Wildlife Department</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input id="Geology and Mining"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="dgmupexp@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Geology and Mining">Geology
																																					and Mining</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input id="Housing Department"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="awasbandhu@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Housing Department">Housing
																																					Department</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Information Technology Department"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="praveenuplc1@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Information Technology Department">Information
																																					Technology Department</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Infrastructure and Industrial Development Authority - GREATER NOIDA"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="osdsps@gnida.in"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Infrastructure and Industrial Development Authority - GREATER NOIDA">Infrastructure
																																					and Industrial Development
																																					Authority - GREATER NOIDA</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Infrastructure and Industrial Development Authority - NOIDA"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="industry@noidaauthorityonline.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Infrastructure and Industrial Development Authority - NOIDA">Infrastructure
																																					and Industrial Development
																																					Authority - NOIDA</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Infrastructure and Industrial Development Authority - UPSIDC"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="upsidc.etc@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Infrastructure and Industrial Development Authority - UPSIDC">Infrastructure
																																					and Industrial Development
																																					Authority - UPSIDC</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Infrastructure and Industrial Development Authority - YEIDA"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="skbhatiapcs@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Infrastructure and Industrial Development Authority - YEIDA">Infrastructure
																																					and Industrial Development
																																					Authority - YEIDA</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Medical Health Department"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="director.medical.care.up@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Medical Health Department">Medical
																																					Health Department</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input id="PICUP" name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="rajkr412@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="PICUP">PICUP</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input id="Pollution Control Board"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="pradeepsharma1764@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Pollution Control Board">Pollution
																																					Control Board</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input id="Public Works Department"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="seidslko@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Public Works Department">Public
																																					Works Department</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Registrar Firms Societies and Chits"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="drlkoregion@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Registrar Firms Societies and Chits">Registrar
																																					Firms Societies and Chits</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input id="Tourism Department"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="dg.upt1@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Tourism Department">Tourism
																																					Department</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Urban Development Department"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="diruplb@nic.in"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Urban Development Department">Urban
																																					Development Department</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Uttar Pradesh Fire Services"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="arvindk1961.ak@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Uttar Pradesh Fire Services">Uttar
																																					Pradesh Fire Services</label>
																																			</div>
																																			<div
																																				class="custom-control custom-checkbox">
																																				<input
																																					id="Uttar Pradesh Power Corporation Limited"
																																					name="deptName"
																																					class="custom-control-input"
																																					type="checkbox"
																																					value="seit.uppcl@gmail.com"><input
																																					type="hidden" name="_deptName"
																																					value="on"> <label
																																					class="custom-control-label"
																																					for="Uttar Pradesh Power Corporation Limited">Uttar
																																					Pradesh Power Corporation Limited</label>
																																			</div>
																																			<!--  gopal -->
																																		</div>
																																	</div>
																																</div>
																																<div class="modal-footer">
																																	<a href="#QueryReply"
																																		class="common-btn mt-0"
																																		data-toggle="modal"
																																		data-dismiss="modal">Submit</a>
																																</div>
																															</div>
																														</div>
																													</div>
																												</div>


																												<!-- Optional JavaScript -->
																												<!-- jQuery first, then Popper.js, then Bootstrap JS -->
																												<script
																													src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
																												<script
																													src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
																												<script
																													src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
																												<script src="js/custom.js"></script>
																												<script>
																														$(
																																document)
																																.ready(
																																		function() {
																																			$(
																																					"[data-toggle=offcanvas]")
																																					.click(
																																							function() {
																																								$(
																																										".row-offcanvas")
																																										.toggleClass(
																																												"active");
																																							});
																																		});
																													</script>
																												<script>
																														//Get the button
																														var mybutton = document
																																.getElementById("GoToTopBtn");

																														// When the user scrolls down 20px from the top of the document, show the button
																														window.onscroll = function() {
																															scrollFunction()
																														};

																														function scrollFunction() {
																															if (document.body.scrollTop > 20
																																	|| document.documentElement.scrollTop > 20) {
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