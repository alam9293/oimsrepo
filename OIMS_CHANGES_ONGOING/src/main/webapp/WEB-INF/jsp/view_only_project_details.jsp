<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>View  Project Details</title>
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
                          <li><a href="./viewBusinessEntityDetails" class="filled"><span>2</span> Business Entity Details</a></li>
                          <li><a class="active"><span>3</span> Project Details</a></li>
                          <li><a href="./viewInvestmentDetails"><span>4</span> Investment Details</a></li>
                          <li><a href="./viewProposedEmploymentDetails"><span>5</span> Proposed Employment Details</a></li>
                        </ul>
                      </div>
                      <div class="isf-form">
                        <form class="mt-4">

                      <div class="row">
                        <div class="col-sm-12 mt-4">
                          <h3 class="common-heading">Project Details</h3>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Project Name <span>*</span></label>
                            <input type="text" class="form-control" value="ABC Company Pvt. Ltd." disabled="" id="" name="">
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Project Description  <span>*</span></label>
                            <input type="text" class="form-control" value="ABC Company Pvt. Ltd." disabled="" id="" name="">
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Name of Contact Person <span>*</span></label>
                            <input type="text" class="form-control" value="Parmod Kumar Gupta" disabled="" id="" name="">
                          </div>
                        </div>
                      </div>


                      <div class="row">
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Designation</label>
                            <input type="text" class="form-control" value="CEO" disabled="" id="" name="">
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Telephone Number <span>*</span></label>
                            <input type="text" class="form-control" value="8510888671" disabled="" id="" name="">
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Website <small>(if any)</small></label>
                            <input type="text" class="form-control" value="www.abccompany.com" disabled="" id="" name="">
                          </div>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-sm-12 mt-4">
                          <h3 class="common-heading">Project Location</h3>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-sm-6">
                          <div class="form-group">
                            <label>Address Line - 1 <span>*</span></label>
                            <input type="text" class="form-control" value="9A/B Govind Nagar, Kanpur" disabled="" id="" name="">
                          </div>
                        </div>
                        <div class="col-sm-6">
                          <div class="form-group">
                            <label>Address Line - 2</label>
                            <input type="text" class="form-control" value="Uttar Pradesh, 208027" disabled="" id="" name="">
                          </div>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Select District <span>*</span></label>
                            <select class="form-control" disabled="">
                              <option value="NONE">Select One</option>
                              <option value="1" selected="">Kanpur</option>
                            </select>
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Mandal <span>*</span></label>
                            <input type="text" class="form-control" value="Kanpur" disabled="" id="" name="">
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Region <span>*</span></label>
                            <input type="text" class="form-control" value="Kanpur Nagar" disabled="" id="" name="">
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Pin Code <span>*</span></label>
                            <input type="text" class="form-control" value="208027" disabled="" id="" name="">
                          </div>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-sm-12 mt-4">
                          <h3 class="common-heading">Registration or License for setting up Industrial Undertaking</h3>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-sm-12">
                          <div class="custom-control custom-radio custom-control-inline">
                              <input type="radio" class="custom-control-input" checked="" disabled="" id="EncloseUAM" name="License" value="customEx">
                              <label class="custom-control-label" for="EncloseUAM">Enclose UAM <small>(for MSME)</small></label>
                            </div>
                            <div class="custom-control custom-radio custom-control-inline">
                              <input type="radio" class="custom-control-input" disabled="" id="IEMcopy" name="License" value="customEx">
                              <label class="custom-control-label" for="IEMcopy">IEM copy <small>(for large & Mega)</small></label>
                            </div>

                            <img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon">
                            <div class="custom-file mt-2">
                              <input type="file" class="custom-file-input" value="Loc-Cabinat-note.pdf" disabled="" id="EncloseUAM">
                              <label class="custom-file-label" for="EncloseUAM">Loc-Cabinat-note.pdf</label>
                            </div>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-sm-12 mt-4">
                          <h3 class="common-heading">Details of existing/proposed products to be manufactured and its capacity <small>(for expansion projects only)</small></h3>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-sm-12">
                          <div class="custom-control custom-radio custom-control-inline">
                            <input type="radio" class="custom-control-input" disabled="" id="NewProject" name="askProject" value="New Project">
                            <label class="custom-control-label" for="NewProject">New Project</label>
                          </div>
                          <div class="custom-control custom-radio custom-control-inline">
                            <input type="radio" class="custom-control-input" checked="" disabled="" id="Expansion" checked="" name="askProject" value="Expansion">
                            <label class="custom-control-label" for="Expansion">Expansion</label>
                          </div>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-sm-12">
                          <div class="table-responsive mt-3">
                            <table class="table table-stripped table-bordered">
                              <thead>
                                <tr>
                                  <th style="width: 50%;">Particulars</th>
                                  <th>Details</th>
                                </tr>
                              </thead>
                              <tbody>
                                <tr>
                                  <td>Existing Products </td>
                                  <td><input type="text" class="form-control" value="ABC Products" disabled="" name=""></td>
                                </tr>
                                <tr>
                                  <td>Existing Installed Capacity</td>
                                  <td><input type="text" class="form-control" value="2000300" disabled="" name=""></td>
                                </tr>
                                <tr>
                                  <td>Proposed Products</td>
                                  <td><input type="text" class="form-control" value="New Product" disabled="" name=""></td>
                                </tr>
                                <tr>
                                  <td>Proposed Installed Capacity</td>
                                  <td><input type="text" class="form-control" value="5000" disabled="" name=""></td>
                                </tr>
                                <tr>
                                  <td>Existing Gross Block</td>
                                  <td><input type="text" class="form-control" value="8000" disabled="" name=""></td>
                                </tr>
                                <tr>
                                  <td>Proposed Gross Block</td>
                                  <td><input type="text" class="form-control" value="9000" disabled="" name=""></td>
                                </tr>
                              </tbody>
                            </table>
                          </div>
                        </div>
                      </div>

                     <div class="row">
                        <div class="col-sm-12 mt-4">
                          <h3 class="common-heading">Relevant Documentary Support</h3>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-sm-12">
                          <div class="table-responsive mt-3">
                            <table class="table table-stripped table-bordered">
                              <tbody>
                                  <tr>
                                    <td style="width: 50%;">
                                      <ul class="common-list">
                                        <li>Enclose Detailed Project Report prepared by External Consultant/Chartered Accountants <span>*</span> <img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"></li>
                                      </ul>
                                    </td>
                                    <td>
                                      <div class="custom-file">
                                        <input type="file" class="custom-file-input" value="enclose-document.pdf" disabled="" id="EncloseDetailed">
                                        <label class="custom-file-label" for="EncloseDetailed">enclose-document.pdf</label>
                                      </div>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <ul class="common-list">
                                        <li>CA Certificate for existing gross block <img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"></li>
                                      </ul>
                                    </td>
                                    <td>
                                      <div class="custom-file">
                                        <input type="file" class="custom-file-input" value="CACertificate.pdf" disabled="" id="CACertificate">
                                        <label class="custom-file-label" for="CACertificate">CACertificate.pdf</label>
                                      </div>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <ul class="common-list">
                                        <li>Chartered Engineer’s Certified List of Fixed Assets in support of existing gross block. <img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"></li>
                                      </ul>
                                    </td>
                                    <td>
                                      <div class="custom-file mt-2">
                                        <input type="file" class="custom-file-input" value="CertifiedList.pdf" disabled="" id="CertifiedList">
                                        <label class="custom-file-label" for="CertifiedList">CertifiedList.pdf</label>
                                      </div>
                                    </td>
                                  </tr>
                              </tbody>
                            </table>
                          </div>
                        </div>
                      </div>

                      <div class="row">
                        <div class="col-sm-5">
                         <button type="button" class="common-default-btn mt-3" onclick="javascript:history.back()">Previous</button>
                        </div>
                        <div class="col-sm-7 text-right">
                          <a href="./viewInvestmentDetails" class="common-btn mt-3">Next</a>
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