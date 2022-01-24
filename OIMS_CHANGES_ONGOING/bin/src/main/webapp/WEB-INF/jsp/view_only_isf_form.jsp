<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>View ISF</title>
    <!-- Fonts -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <script> 
        function accepted() { 
            var confirmationMessage=confirm('Are you sure want to acccept the Application Form?'); 
            if(confirmationMessage){
            window.location.href="./dashboard";
            }
        } 
        </script>
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
                <div class="isf-form">
                  <form class="mt-4">

                    <h4 class="text-center"><strong>Incentive Details</strong></h4>
                    <div id="accordion" class="accordion mt-5 mb-3">
                        <div class="card mb-0 border-0">
                            <div class="card-header collapsed mb-4" data-toggle="collapse" href="#collapseOne">
                                <a class="card-title">
                                  <strong>SGST Reimbursemnt</strong>
                                </a>
                            </div>
                            <div id="collapseOne" class="card-body collapse show" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-stripped table-bordered loc-stage-table">
                                      <tbody>
                                        <tr>
                                          <td style="width: 70%;"><strong>Amount of SGST claimed for reimbursement</strong></td>
                                          <td><input type="text" class="form-control" value="10000890" disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>
                                              Additional 10% GST where 25% minimum SC/ST workers employed subject to minimum of 400 total workers in industrial undertakings located in Paschimanchal and minimum of 200 total workers in B-P-M
                                          </td>
                                          <td><input type="text" class="form-control"  disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>
                                            Additional 10% GST where 40% minimum female workers employed subject to minimum of 400 total workers in industrial undertakings located in Paschimanchal and minimum of 200 total workers in B-P-M
                                          </td>
                                          <td><input type="text" class="form-control"  disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>
                                            Additional 10% GST where 25% minimum BPL workers employed subject to minimum of 400 total workers in industrial undertakings located in Paschimanchal and minimum of 200 total workers in B-P-M
                                          </td>
                                          <td><input type="text" class="form-control"  disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td><strong>Total SGST Reimbursement</strong></td>
                                          <td><input type="text" class="form-control" disabled="" name=""></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
                              </div>


                            </div>
                            <div class="card-header collapsed mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                <a class="card-title">
                                  <strong>Stamp Duty Exemption</strong>
                                </a>
                            </div>
                            <div id="collapseTwo" class="card-body collapse" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-stripped table-bordered loc-stage-table">
                                      <tbody>
                                        <tr>
                                          <td style="width: 70%;"><strong>Amount of Stamp Duty Exemption</strong></td>
                                          <td><input type="text" class="form-control" value="10000890" disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>
                                            Additional Stamp Duty exemption @20% upto maximum of 100% in case of industrial undertakings having 75% equity owned by Divyang/SC/ ST/Females Promoters
                                          </td>
                                          <td><input type="text" class="form-control"  disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td><strong>Total Stamp Duty Exemption</strong></td>
                                          <td><input type="text" class="form-control" disabled="" name=""></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
                              </div>

                            </div>
                            <div class="card-header collapsed mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                <a class="card-title">
                                  <strong>EPF Reimbursement</strong>
                                </a>
                            </div>
                            <div id="collapseThree" class="card-body collapse" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-stripped table-bordered loc-stage-table">
                                      <tbody>
                                        <tr>
                                          <td style="width: 70%;"><strong>EPF Reimbursement (100 or more unskilled workers)</strong></td>
                                          <td><input type="text" class="form-control" value="5000000" disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>
                                            Additional 10% EPF Reimbursement (200 direct skilled and unskilled workers)
                                          </td>
                                          <td><input type="text" class="form-control"  disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>
                                            Additional 10% EPF Reimbursement upto maximum of 70% in case of industrial undertakings having 75% equity owned by Divyang/SC/CT/Females Promoters
                                          </td>
                                          <td><input type="text" class="form-control"  disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td><strong>Total EPF Reimbursement</strong></td>
                                          <td><input type="text" class="form-control" disabled="" name=""></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
                              </div>

                            </div>

                            <div class="card-header collapsed mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                <a class="card-title">
                                  <strong>Interest Subsidies</strong>
                                </a>
                            </div>
                            <div id="collapseFour" class="collapse" data-parent="#accordion" >
                                <div class="card-body">

                                  <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-stripped table-bordered loc-stage-table">
                                      <tbody>
                                        <tr>
                                          <td style="width: 70%;"><strong>Capital Interest Subsidy</strong></td>
                                          <td><input type="text" class="form-control" value="15000000" disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>
                                            Additional Capital Interest Subsidy@2.5% upto maximum of 7.5% in case of industrial undertakings having 75% equity owned by Divyang/SC/CT/Females Promoters
                                          </td>
                                          <td><input type="text" class="form-control"  disabled="" name="" value="375000"></td>
                                        </tr>
                                        <tr>
                                          <td style="width: 70%;"><strong>Infrastructure Interest Subsidy</strong></td>
                                          <td><input type="text" class="form-control" value="2000000" disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>
                                            Additional Infrastructure Interest Subsidy @2.5% upto maximum of 7.5% in case of industrial undertakings having 75% equity owned by Divyang/SC/CT/Females Promoters
                                          </td>
                                          <td><input type="text" class="form-control" disabled="" name="" value="50000"></td>
                                        </tr>
                                        <tr>
                                          <td style="width: 70%;"><strong>Interest Subsidy on loans for industrial research, quality improvement, etc.</strong></td>
                                          <td><input type="text" class="form-control" value="10000000" disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td><strong>Total Interest Subsidies</strong></td>
                                          <td><input type="text" class="form-control" disabled="" name="" value="27087500"></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
                              </div>

                                </div>
                            </div>

                            <div class="card-header collapsed mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                                <a class="card-title">
                                  <strong>Other Incentives</strong>
                                </a>
                            </div>
                            <div id="collapseFive" class="collapse" data-parent="#accordion" >
                                <div class="card-body">

                                  <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-stripped table-bordered loc-stage-table">
                                      <tbody>
                                        <tr>
                                          <td style="width: 70%;">Reimbursement of Disallowed Input Tax Credit on plant, building materials, and other capital goods.</td>
                                          <td><input type="text" class="form-control" value="2500000" disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>Exemption from Electricity Duty from captive power for self-use</td>
                                          <td><input type="text" class="form-control"  value="2500000" disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>Exemption from Electricity duty on power drawn from power companies</td>
                                          <td><input type="text" class="form-control"  value="2500000" disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td>Exemption from Mandi Fee</td>
                                          <td><input type="text" class="form-control"  value="2500000" disabled="" name=""></td>
                                        </tr>
                                        <tr>
                                          <td><strong>Total Other Incentives</strong></td>
                                          <td><input type="text" class="form-control" value="10000000" disabled="" name=""></td>
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



                    <div class="row">
                      <div class="col-sm-12">
                        <div class="custom-control form-group custom-radio custom-control-inline pl-0">
                          <label class=""><strong>Do you want to avail customised incentives</strong></label>
                        </div>
                        <div class="custom-control custom-radio custom-control-inline">
                          <input type="radio" class="custom-control-input" checked="" disabled="" id="SGSTclaimedYes" name="SGST-Radio" value="Yes">
                          <label class="custom-control-label" for="SGSTclaimedYes">Yes</label>
                        </div>
                        <div class="custom-control custom-radio custom-control-inline">
                          <input type="radio" class="custom-control-input" disabled="" id="SGSTclaimedNo" name="SGST-Radio" value="No">
                          <label class="custom-control-label" for="SGSTclaimedNo">No</label>
                        </div>
                      </div>
                    </div>


                    <div class="row">
                        <div class="col-sm-12">
                          <div class="form-group">
                            <label>Add Incentive Type</label>
                            <input type="text" class="form-control" value="Stamp Duty Exemption" disabled="" name="">
                          </div>
                      </div>
                    </div>


                    <div class="row">
                      <div class="col-sm-12">
                        <div class="form-group">
                          <label>Type Details of Customised Incentives</label>
                          <textarea class="form-control" disabled="" rows="4">Additional Stamp Duty exemption @20% upto maximum of 100% in case of industrial undertakings having 75% equity owned by Divyang/SC/ ST/Females Promoters
                          </textarea>
                        </div>
                      </div>
                    </div>


                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label>Upload Document <span>*</span> <small>(In PDF format less than 5 MB)</small> <img src="images/pdf-icon.png" class="pdf-icon" alt="pdf-icon"></label>
                          <div class="custom-file">
                            <input type="file" class="custom-file-input" value="incentive-documnet.pdf" disabled="" id="UploadDocumentISF">
                            <label class="custom-file-label" for="UploadDocumentISF">incentive-documnet.pdf</label>
                          </div>
                        </div>
                      </div>
                    </div>
                    
                    <hr class="mt-4">

                    <div class="row mb-3">
                      <div class="col-sm-4">
                       <button type="button" class="common-default-btn mt-3" onclick="javascript:history.back()">Back</button>
                      </div>
                      <div class="col-sm-8 text-right">
                        <button type="button" class="common-btn mt-3">Download Document</button>
                        <button type="button" class="common-btn mt-3">Download Form</button>
                        <button type="button" class="common-btn mt-3">
                        <!--  data-toggle="modal" data-target="#RaiseQuery" -->
                        Raise Query</button>
                        <button type="button" class="common-green-btn mt-3" onclick="accepted()">Accept Form</button>
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

    <div class="container">

      <!-- The Modal -->
      <div class="modal fade" id="RaiseQuery">
        <div class="modal-dialog">
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
                    <input type="text" class="form-control" name="">
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <label>Details of Missing Documents</label>
                    <input type="text" class="form-control" name="">
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <label>Other</label>
                    <textarea class="form-control"></textarea>
                  </div>
                </div>
              </div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
              <a href="dashboard.html" class="common-btn mt-0">Submit</a>
            </div>

          </div>
        </div>
      </div>
    </div>


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