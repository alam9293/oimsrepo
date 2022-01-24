<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>View Business Entity Details</title>
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
                          <li><a href="./viewApplicantDetails?edit=<%=session.getAttribute("appId")%>" class="filled"><span>1</span> Applicant Details</a></li>
                          <li><a class="active"><span>2</span> Business Entity Details</a></li>
                          <li><a href="./viewProjectDetails"><span>3</span> Project Details</a></li>
                          <li><a href="./viewInvestmentDetails"><span>4</span> Investment Details</a></li>
                          <li><a href="./viewProposedEmploymentDetails"><span>5</span> Proposed Employment Details</a></li>
                        </ul>
                      </div>
                      <div class="isf-form">
                        <form class="mt-4">

                          <div class="row">
                              <div class="col-sm-12 mt-4">
                                <h3 class="common-heading">Business Entity Details and Address</h3>
                              </div>
                            </div>
                            
                            <div class="row">
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Business Entity Name <span>*</span></label>
                                  <input type="text" class="form-control" value="ABC Company Pvt. Ltd." disabled="" id="" name="">
                                </div>
                              </div>
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Select Business Entity Type <span>*</span></label>
                                  <select class="form-control" disabled="">
                                    <option value="NONE">Select One</option>
                                    <option value="1">Limited </option>
                                    <option value="2" selected="">Private Limited</option>
                                    <option value="3">Partnership</option>
                                    <option value="4">Proprietorship</option>
                                    <option value="5">Public Enterprise</option>
                                    <option value="6">LLP</option>
                                    <option value="7">Society</option>
                                    <option value="8">Trust</option>
                                  </select>
                                </div>
                              </div>
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Authorised Signatory Name <span>*</span></label>
                                  <input type="text" class="form-control" value="Parmod Kumar Gupta" disabled="" id="" name="">
                                </div>
                              </div>
                            </div>


                            <div class="row">
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Designation <span>*</span></label>
                                  <input type="text" class="form-control" value="CEO" disabled="" id="" name="">
                                </div>
                              </div>
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Email <span>*</span></label>
                                  <input type="email" class="form-control" value="parmod.gupta@gmail.com" disabled="" id="" name="">
                                </div>
                              </div>
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Mobile No. <span>*</span></label>
                                  <input type="text" class="form-control" value="8510888671" disabled="" id="" name="">
                                </div>
                              </div>
                            </div>


                            <div class="row">
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Fax</label>
                                  <input type="text" class="form-control" value="7634639989" disabled="" id="" name="">
                                </div>
                              </div>
                              <div class="col-md-6 col-lg-8 col-xl-8">
                                <div class="form-group">
                                  <label>Registered Office Address <span>*</span></label>
                                  <input type="text" class="form-control" value="9A/B Govind Nagar, Kanpur" disabled="" id="" name="">
                                </div>
                              </div>
                            </div>

                            <div class="row">
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Select Country <span>*</span></label>
                                  <select class="form-control" disabled="">
                                    <option value="NONE">Select One</option>
                                    <option value="1" selected="">India</option>
                                  </select>
                                </div>
                              </div>
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Select State/UT <span>*</span></label>
                                  <select class="form-control" disabled="">
                                    <option value="NONE">Select One</option>
                                    <option value="1" selected="">Uttar Pradesh</option>
                                  </select>
                                </div>
                              </div>
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Select District <span>*</span></label>
                                  <select class="form-control" disabled="">
                                    <option value="NONE">Select One</option>
                                    <option value="1" selected="">Kanpur</option>
                                  </select>
                                </div>
                              </div>
                            </div>

                            <div class="row">
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Pin Code <span>*</span></label>
                                  <input type="text" class="form-control" value="208027" disabled="" id="" name="">
                                </div>
                              </div>
                            </div>


                            <div class="row">
                              <div class="col-sm-12 mt-4">
                                <h3 class="common-heading">Director / Partner / Owner / Proprietor Details</h3>
                              </div>
                            </div>


                            <div class="row">
                              <div class="col-sm-12">
                                <div class="table-responsive">
                                  <table class="table table-stripped table-bordered directors-table" id="customFields">
                                    <thead>
                                      <tr>
                                        <th>Director Name</th>
                                        <th>Designation</th>
                                        <th>Address</th>
                                        <th>Mobile No.</th>
                                        <th>% Equity</th>
                                        <th>Email</th>
                                        <th>Gender</th>
                                        <th>Category</th>
                                        <th>Action</th>
                                      </tr>
                                    </thead>
                                    <tbody>
                                      <tr>
                                        <td>Vishal Gupta</td>
                                        <td>Vice President</td>
                                        <td>Nirala Nagar Kanpur</td>
                                        <td>9572783898</td>
                                        <td>18%</td>
                                        <td>vishal.gupta@gmail.com</td>
                                        <td>Male</td>
                                        <td>General</td>
                                        <td class="text-center"><img src="images/pan-card.jpg" class="pan-card-img" alt="Pan-Card"></td>
                                      </tr>
                                      <tr>
                                        <td>Arjun Reddy</td>
                                        <td>President</td>
                                        <td>Gomti Nagar Lucknow</td>
                                        <td>9584069830</td>
                                        <td>22%</td>
                                        <td>arjun.3030@gmail.com</td>
                                        <td>Male</td>
                                        <td>General</td>
                                        <td class="text-center"><img src="images/pan-card.jpg" class="pan-card-img" alt="Pan-Card"></td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </div>
                              </div>
                            </div>

                            <div class="row">
                              <div class="col-sm-12 mt-4">
                                <h3 class="common-heading">Upload Documents and Certificates</h3>
                              </div>
                            </div>

                            <div class="row">
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Year of Unit Establishment</label>
                                  <input type="text" class="form-control" value="20-01-2017" disabled="" id="" name="">
                                </div>
                              </div>
                              <div class="col-md-6 col-lg-4 col-xl-4">
                                <div class="form-group">
                                  <label>Company Pan No. <span>*</span></label>
                                  <input type="text" class="form-control" value="EEHPS3008P" disabled="" id="" name="">
                                </div>
                              </div>
                            </div>

                            <div class="row">
                              <div class="col-md-12">
                                <div class="form-group">
                                  <label>Upload Company Pan Card <span>*</span> <small>(In PDF format less than 1 MB)</small> <img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"></label>
                                  <div class="custom-file">
                                    <input type="file" class="custom-file-input" value="abc-compnay-pan.pdf" disabled="" id="customFile5">
                                    <label class="custom-file-label" for="customFile5">abc-compnay-pan.pdf</label>
                                  </div>
                                </div>
                              </div>
                              <div class="col-sm-12">
                                <div class="form-group">
                                  <label>MoA / Partnership Deed Attachment <span>*</span> <small>(In PDF format less than 6 MB)</small> <img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"></label>
                                  <div class="custom-file">
                                    <input type="file" class="custom-file-input" value="abc-compnay-partnership-deed.pdf" disabled="" id="customFile6">
                                    <label class="custom-file-label" for="customFile6">abc-compnay-partnership-deed.pdf</label>
                                  </div>
                                </div>
                              </div>
                              <div class="col-sm-12">
                                <div class="form-group">
                                  <label>Certificate Incorporation Registration Attachment <span>*</span> <small>(In PDF format less than 1 MB)</small> <img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"></label>
                                  <div class="custom-file">
                                    <input type="file" class="custom-file-input" value="certificate-incorporation.pdf" disabled="" id="customFile7">
                                    <label class="custom-file-label" for="customFile7">certificate-incorporation.pdf</label>
                                  </div>
                                </div>
                              </div>
                            </div>

                            

                            <hr class="mt-4">


                            <div class="row">
                              <div class="col-sm-5">
                                <a href="./viewApplicantDetails?edit=<%=session.getAttribute("appId")%>" class="common-default-btn mt-3">Previous</a>
                              </div>
                              <div class="col-sm-7 text-right">
                                <a href="./viewProjectDetails" class="common-btn mt-3">Next</a>
                              </div>
                            </div>

                        </form>
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