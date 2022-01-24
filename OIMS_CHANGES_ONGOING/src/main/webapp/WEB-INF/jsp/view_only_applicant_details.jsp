<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>View Applicant Details</title>
    <!-- Fonts -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body>
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
              <a class="nav-link" href="#"><i class="fa fa-power-off"></i> Logout</a>
            </li>

            <li class="nav-item">
              <a class="nav-link active" href="#"><i class="fa fa-user"></i> User Name</a>
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
              <li class="nav-item"><a class="nav-link active" href="./dashboard"><i class="fa fa-eye"></i> View Incentive Applications</a></li>
              <li class="nav-item"><a class="nav-link" href="./reviewIncentiveApplication"><i class="fa fa-tasks"></i> Review Incentive Applications</a></li>
              <li class="nav-item"><a class="nav-link" href="#"><i class="fa fa-check-square"></i> Verify Incentive Applications</a></li>
              <li class="nav-item"><a class="nav-link" href=""><i class="fa fa-list"></i> Propose Agenda Note</a></li>
              <li class="nav-item"><a class="nav-link" href=""><i class="fa fa-sliders"></i> Prepare Agenda Note </a></li>
              <li class="nav-item"><a class="nav-link" href=""><i class="fa fa-wpforms"></i> Issue Letter of Comfort (LoC)</a></li>
            </ul>
          </div>
          <!--/col-->

          <div class="col-md-9 col-lg-10 mt-4 main mb-4">
            <div class="card card-block p-3">
              <div class="row">
          <div class="col-sm-12">
            <div class="mt-4">
              <div class="without-wizard-steps">
                <div class="step-links">
                  <ul>
                    <li class="active"><a class="active"><span>1</span> Applicant Details</a></li>
                    <li><a href="./viewBusinessEntityDetails"><span>2</span> Business Entity Details</a></li>
                    <li><a href="./viewProjectDetails"><span>3</span> Project Details</a></li>
                    <li><a href="./viewInvestmentDetails"><span>4</span> Investment Details</a></li>
                    <li><a href="./viewProposedEmploymentDetails"><span>5</span> Proposed Employment Details</a></li>
                  </ul>
                </div>
                <div class="isf-form">
                  <form:form action="saveApplicantDetails" method="POST" modelAttribute="applicantDetails">

                    <div class="row">
                      <div class="col-sm-12 mt-4">
                        <h3 class="common-heading">Applicant Personal Info</h3>
                      </div>
                    </div>
                     
                    <div class="row">
                    <%-- <div class="col-md-6 col-lg-4 col-xl-4">
                    <div class="form-group">
			        <form:label path="appTitle">Title <span></span>
					</form:label>
				    <form:select class="form-control" path="appTitle" disabled="true">
					<form:option value="">Select Title</form:option>
					<form:option value="Mr.">Mr</form:option>
					<form:option value="Mrs.">Mrs</form:option>
					</form:select>															
					</div>
                    </div> --%>
                     
                    <div class="col-md-6 col-lg-4 col-xl-4">
                    <div class="form-group">
					<form:label path="appFirstName">First Name <span></span>
					</form:label>
					<form:input path="appFirstName"	class="form-control" id="applicantFirstName" disabled="true"></form:input>
					</div>
                    </div>
                    
                    <div class="col-md-6 col-lg-4 col-xl-4">
                    <div class="form-group">
					<form:label path="appMiddleName">Middle Name</form:label>
					<form:input path="appMiddleName" class="form-control" id="appMiddleName" disabled="true"></form:input>							
					</div>
                    </div>
                    </div>

                    <div class="row">
                    <div class="col-md-6 col-lg-4 col-xl-4">
                    <div class="form-group">
				    <form:label path="appLastName">Last Name<span></span></form:label>
					<form:input path="appLastName" class="form-control"	id="appLastName" disabled="true"></form:input>
					</div>
                    </div>
                    
                    <div class="col-md-6 col-lg-4 col-xl-4">
                    <div class="form-group">
					<form:label path="appEmailId">Applicant Email ID <span></span>
					</form:label>
					<form:input path="appEmailId" class="form-control" id="applicantEmailId" disabled="true"></form:input>														
					</div>										
                    </div>
                    
                      <div class="col-md-6 col-lg-4 col-xl-4">
                        <div class="form-group">
															<form:label path="appMobileNo">Applicant Mobile No. <span></span>
															</form:label>
															<form:input path="appMobileNo" class="form-control"
																id="appMobileNo" disabled="true"></form:input>
															
														</div>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-md-6 col-lg-4 col-xl-4">
                       <div class="form-group">
															<form:label path="appPhoneNo">Applicant Phone No.(LandLine)</form:label>
															<form:input path="appPhoneNo" class="form-control"
																id="appPhoneNo" disabled="true"></form:input>
															
														</div>
                      </div>
                      <div class="col-md-6 col-lg-4 col-xl-4">
                        <div class="form-group">
															<form:label path="appDesignation">Designation <span></span>
															</form:label>
															<form:input path="appDesignation"
																class="form-control" id="appDesignation" disabled="true"></form:input>
															
														</div>
                      </div>

                      <div class="col-md-6 col-lg-4 col-xl-4">
                        <div class="form-group">
                          <div class="gender-label">
                            <label>Select Gender</label>
                          </div>
                          <div class="custom-control custom-radio custom-control-inline">
                            <input type="radio" class="custom-control-input" checked="" id="MaleOpt" disabled="disabled" name="Gender" value="Male">
                            <label class="custom-control-label" for="MaleOpt">Male</label>
                          </div>
                          <div class="custom-control custom-radio custom-control-inline">
                            <input type="radio" class="custom-control-input" id="FemaleOpt" disabled="disabled" name="Gender" value="Female">
                            <label class="custom-control-label" for="FemaleOpt">Female</label>
                          </div>
                          <div class="custom-control custom-radio custom-control-inline">
                            <input type="radio" class="custom-control-input" id="Transgender" disabled="disabled" name="Gender" value="Transgender">
                            <label class="custom-control-label" for="Transgender">Transgender </label>
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-sm-12 mt-4">
                        <h3 class="common-heading">Upload Applicant Aadhaar & Pan</h3>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-md-6">
                        <div class="form-group">
                          <label>Upload ID Doc/Aadhaar <small>(Less than 1 MB)</small><img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"></label>
                          <div class="custom-file">
                            <input type="file" class="custom-file-input" value="pramod-aadhaar.pdf" disabled="disabled" id="customFile1">
                            <label class="custom-file-label" for="customFile1">aadhaar.pdf</label>
                          </div>
                        </div>
                      </div>
                      <div class="col-md-6">
                        <div class="form-group">
                          <label>Upload Pan Card <small>(Less than 1 MB)</small><img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"></label>
                          <div class="custom-file">
                            <input type="file" class="custom-file-input" value="pramod-pan.pdf" disabled="disabled" id="customFile2">
                            <label class="custom-file-label" for="customFile2">pan.pdf</label>
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-sm-12 mt-4">
                        <h3 class="common-heading">Applicant Address Details</h3>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-sm-6">
                       <div class="form-group">
															<form:label path="appAddressLine1">Address Line - 1 <span></span>
															</form:label>
															<form:input path="appAddressLine1"
																class="form-control" id="appAddressLine1" disabled="true"></form:input>
															
														</div>
                      </div>
                      <div class="col-sm-6">
                        <div class="form-group">
															<form:label path="appAddressLine2">Address Line - 2</form:label>
															<form:input path="appAddressLine2"
																class="form-control" id="appAddressLine2" disabled="true"></form:input>
															
														</div>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-md-6 col-lg-4 col-xl-4">
                       <div class="form-group">
															<form:label path="appCountry">Select Country <span></span>
															</form:label>
															<form:select class="form-control" path="appCountry" disabled="true">
																<form:option value="">Select One</form:option>
																<form:option value="India">India</form:option>
															</form:select>															
														</div>
                      </div>
                      <div class="col-md-6 col-lg-4 col-xl-4">
                        <div class="form-group">
															<form:label path="appState">Select State/UT <span></span>
															</form:label>
															<form:select class="form-control" path="appState" disabled="true">
																<form:option value="">Select One</form:option>
																<form:option value="UP">Uttar Pradesh</form:option>
															</form:select>
															
														</div>
                      </div>
                      <div class="col-md-6 col-lg-4 col-xl-4">
                        <div class="form-group">
															<form:label path="appDistrict">Select District <span></span>
															</form:label>
															<form:select class="form-control"
																path="appDistrict" disabled="true">
																<form:option value="">Select One</form:option>
																<form:option value="Lucknow">Lucknow</form:option>
															</form:select>
															
														</div>
                      </div>
                    </div>


                    <div class="row">
                      <div class="col-md-6 col-lg-4 col-xl-4">
                        <div class="form-group">
															<form:label path="appPinCode">PIN Code <span></span>
															</form:label>
															<form:input path="appPinCode" class="form-control"
																id="" name="appPinCode" disabled="true"></form:input>
															
														</div>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-sm-12 mt-4">
                        <h3 class="common-heading">Upload Applicant Photo & Signature</h3>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-sm-6">
                        <div class="form-group">
                          <label>Applicant Photo <small>(Upload JPG or PNG Less than 100 KB)</small></label>
                          <div class="custom-file">
                            <input type="file" class="custom-file-input" value="pramod-pic.jpg" disabled="disabled" id="customFile3">
                            <label class="custom-file-label" for="customFile3">pic.jpg</label>
                          </div>
                        </div>
                      </div>
                      <div class="col-sm-6 text-center">
                        <img src="images/user-icon.png" class="user-upload-preview" align="user-icon">
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-sm-6">
                        <div class="form-group">
                          <label>Applicant Sign <small>(Upload JPG or PNG Less than 50 KB)</small></label>
                          <div class="custom-file">
                            <input type="file" class="custom-file-input" value="pramod-sign.jpg" disabled="disabled" id="customFile4">
                            <label class="custom-file-label" for="customFile4">sign.jpg</label>
                          </div>
                        </div>
                      </div>
                      <div class="col-sm-6 text-center">
                        <img src="images/sign-sample.png" class="sign-upload-preview" align="sign-sample">
                      </div>
                    </div>

                    <hr>

                    <div class="row">
                      <div class="col-sm-5">
                        <a href="./dashboard" class="common-default-btn mt-3">Back</a>
                      </div>
                      <div class="col-sm-7 text-right">
                        <a href="./viewBusinessEntityDetails" class="common-btn mt-3">Next</a>
                      </div>
                    </div>

                  </form:form>
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
                <li><a href="http://udyogbandhu.com/topics.aspx?mid=Disclaimer">Disclaimer</a></li>
                <li><a href="http://udyogbandhu.com/topics.aspx?mid=General%20Terms%20And%20Conditions">General Terms And Conditions</a></li>
                <li><a href="http://udyogbandhu.com/topics.aspx?mid=Privacy%20Policy">Privacy Policy</a></li>
                <li><a href="http://udyogbandhu.com/topics.aspx?mid=Refund%20Policy">Refund Policy</a></li>
                <li><a href="http://udyogbandhu.com/topics.aspx?mid=Delivery%20Policy">Delivery Policy</a></li>
                <li><a href="http://udyogbandhu.com/topics.aspx?mid=Contact%20Us">Contact Us</a></li>
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


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>

  


  <script>
    $(document).ready(function() {
      $("[data-toggle=offcanvas]").click(function() {
        $(".row-offcanvas").toggleClass("active");
      });
    });
  </script>
  </body>
</html>