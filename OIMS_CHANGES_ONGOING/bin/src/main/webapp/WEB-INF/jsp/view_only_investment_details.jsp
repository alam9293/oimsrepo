<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>View  Investment Details</title>
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
                          <li><a href="./viewProjectDetails" class="filled"><span>3</span> Project Details</a></li>
                          <li><a class="active"><span>4</span> Investment Details</a></li>
                          <li><a href="./viewProposedEmploymentDetails"><span>5</span> Proposed Employment Details</a></li>
                        </ul>
                      </div>
                      <div class="isf-form">
                        <form class="mt-4">

                          <div class="row">
                          <div class="col-sm-12 mt-4">
                              <h3 class="common-heading">Investment Details</h3>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col-md-6 col-lg-4 col-xl-4">
                              <div class="form-group">
                                <label>Land Cost <small>(In Crores)</small> <span>*</span></label>
                                <input type="text" class="form-control" value="80007800" disabled="" id="" name="">
                              </div>
                            </div>
                            <div class="col-md-6 col-lg-4 col-xl-4">
                              <div class="form-group">
                                <label>Building Cost <small>(In Crores)</small> <span>*</span></label>
                                <input type="text" class="form-control" value="60007800" disabled="" id="" name="">
                              </div>
                            </div>
                            <div class="col-md-6 col-lg-4 col-xl-4">
                              <div class="form-group">
                                <label>Plant & Machinery Cost <small>(In Crores)</small> <span>*</span></label>
                                <input type="text" class="form-control" value="70007800" disabled="" id="" name="">
                              </div>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col-md-6 col-lg-4 col-xl-4">
                              <div class="form-group">
                                <label>Fixed Capital Investment <small>(In Crores)</small> <span>*</span></label>
                                <input type="text" class="form-control" value="50007800" disabled="" id="" name="">
                              </div>
                            </div>
                            <div class="col-md-6 col-lg-4 col-xl-4">
                              <div class="form-group">
                                <label>Other Cost <small>(In Crores)</small> <span>*</span></label>
                                <input type="text" class="form-control" value="40007800" disabled="" id="" name="">
                              </div>
                            </div>
                            <div class="col-md-6 col-lg-4 col-xl-4">
                              <div class="form-group">
                                <label>Total Project Cost <small>(In Crores)</small> <span>*</span></label>
                                <input type="text" class="form-control" value="" disabled="309674848" disabled="" id="" name="">
                              </div>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col-md-6 col-lg-4 col-xl-4">
                              <div class="form-group">
                                <label>Type of Industry <span>*</span></label>
                                <select class="form-control" disabled="">
                                  <option value="NONE">Select One</option>
                                  <option value="1">Small</option>
                                  <option value="2" selected="">Medium</option>
                                  <option value="3">Large</option>
                                  <option value="4">Mega</option>
                                  <option value="5">Mega Plus</option>
                                  <option value="6">Super Mega</option>
                                </select>
                              </div>
                            </div>
                            <div class="col-md-4 col-lg-8 col-xl-8">
                              <div class="form-group">
                                <label>Proposed Date of Commercial Production <span>*</span></label>
                                <input type="text" class="form-control" value="21-11-2020" disabled="" id="" name="">
                              </div>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col-sm-12 mt-4">
                              <h3 class="common-heading">Phase Wise Investment as per Detailed Project Report (If Applicable)</h3>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col-sm-12">
                              <div class="table-responsive mt-3">
                                <table class="table table-stripped table-bordered">
                                  <thead>
                                    <tr>
                                      <th>Sr. No.</th>
                                      <th>Name of Phase</th>
                                      <th>Fixed Capital Investment (In Crores)</th>
                                      <th>Proposed Date of Investment</th>
                                      <th>Proposed Date of Production</th>
                                      <th>Action</th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                    <tr>
                                      <td class="text-center">1</td>
                                      <td>Phase-1</td>
                                      <td>80007800</td>
                                      <td>21-11-2020</td>
                                      <td>21-02-2021</td>
                                      <td class="text-center"><i class="fa fa-trash text-danger"></i></td>
                                    </tr>
                                    <tr>
                                      <td class="text-center">1</td>
                                      <td>Phase-2</td>
                                      <td>50007800</td>
                                      <td>08-11-2020</td>
                                      <td>09-02-2021</td>
                                      <td class="text-center"><i class="fa fa-trash text-danger"></i></td>
                                    </tr>
                                  </tbody>
                                </table>
                              </div>
                            </div>
                          </div>

                          <hr class="mt-4 mb-4">

                          <div class="row">
                            <div class="col-sm-5">
                              <button type="button" class="common-default-btn mt-3" onclick="javascript:history.back()">Previous</button>
                            </div>
                            <div class="col-sm-7 text-right">
                              <a href="./viewProposedEmploymentDetails" class="common-btn mt-3 next-step">Next</a>
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