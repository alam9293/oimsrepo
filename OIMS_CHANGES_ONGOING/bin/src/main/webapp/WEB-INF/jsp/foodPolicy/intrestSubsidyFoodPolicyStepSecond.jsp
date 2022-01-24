<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Step-2</title>
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <!-- Fonts -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body class="bottom-bg">
    <section class="inner-header">
      <div class="top-header">
        <div class="container">
          <div class="row">
            <div class="col-sm-6 text-left">
              <span class="top-gov-text">Goverment of Uttar Pradesh</span>
            </div>
            <div class="col-sm-6 text-right">
              <a href="tel:05222238902"><i class="fa fa-phone"></i> 0522-2238902</a> | <a href="mailto:info@udyogbandhu.com"><i class="fa fa-envelope"></i> info@udyogbandhu.com</a>
            </div>
          </div>
        </div>
      </div>
        <!-- Navigation / Navbar / Menu -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
          <div class="container">
          <a class="navbar-brand" href="#"><img src="images/logo.png" class="logo" alt="Logo"></a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
            <ul class="navbar-nav ml-auto mt-2 mt-lg-0">               
              <li class="nav-item">
              <a class="nav-link active" href="applicant-details.html">Home</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="http://udyogbandhu.com/" target="_blank">Invest UP</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="http://niveshmitra.up.nic.in/About.aspx?ID=whyup" target="_blank">Why Invest in UP</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="http://udyogbandhu.com/topics.aspx?mid=Policies" target="_blank">Policies</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="http://udyogbandhu.com/topics.aspx?mid=UdyogBandhu" target="_blank">Contact Us</a>
            </li>

            </ul>
            <div class="gov-logo-group">
              <a href="#"><img src="images/up-logo.png" align="up-Logo"></a>
              
            </div>
          </div>
        </div>
        </nav>
        <!-- End Navigation / Navbar / Menu -->
    </section>


    <section class="common-form-area">
      <div class="container">
        <div class="row">
          <div class="col-sm-12">
            <div class="form-card">
              <div class="without-wizard-steps">
                <div class="step-links">
                  <h4 class="card-title text-center mt-4">Interest Subsidy for settingup new units/Technology Up Gradation / <br>Modernisation of Food Processing Industries.</h4>
                </div>
                <div class="step-links">
                  <a href="./intrestSubsidyFoodPolicyStepFirst" class="common-second-btn mt-0 top-step-btn btn-sm">Back</a>
                  <ul class="only-steps">
                    <li><a href="./intrestSubsidyFoodPolicyStepFirst" class="filled">Step <span>1</span></a></li>
                    <li><a href="#" class="active">Step <span>2</span></a></li>
                    <li><a href="#">Step <span>3</span></a></li>
                  </ul>
                </div>

                <div class="isf-form">
                  <form>

                    <div class="row">
                      <div class="col-sm-12 mb-3 mt-3">
                        <h3 class="common-heading">Capacity of the Plant/Unit <span class="text-danger">*</span></h3>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <div class="custom-control custom-radio custom-control-inline">
                              <input type="radio" class="custom-control-input" checked="" id="CapacityExisting" name="capacity" value="Male">
                              <label class="custom-control-label" for="CapacityExisting">Existing</label>
                          </div>
                          <div class="custom-control custom-radio custom-control-inline">
                              <input type="radio" class="custom-control-input" id="CapacityProposed" name="capacity" value="Female">
                              <label class="custom-control-label" for="CapacityProposed">Proposed</label>
                          </div>
                      </div>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-md-12">
                        <div class="table-responsive">
                          <table class="table table-bordered">
                            <tr>
                              <td style="width:40%;">In case of expansion/ modernisation of existing facilities/unit (details of existing capacity and proposed capacity after expansion & along with capacity utilisation) <span class="text-danger">*</span></td>
                              <td>
                                <label>Existing</label>
                                <input type="text" class="form-control" name="">
                              </td>
                              <td>
                                <label>Proposed</label>
                                <input type="text" class="form-control" name="">
                              </td>
                              <td>
                                <label>Total After Expansion</label> 
                                <input type="text" class="form-control" name="">
                              </td>
                            </tr>
                          </table>
                        </div>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-md-6">
                        <div class="form-group">
                          <label>Select Capacity <span>*</span></label>
                          <select class="form-control">
                            <option selected="selected" value="0">--Select Capacity--</option>
                            <option value="Metric Ton Per Annum">Metric Ton Per Annum</option>
                            <option value="Million Liter Per Annum">Million Liter Per Annum</option>
                            <option value="Others">Others</option>
                          </select>
                        </div>
                      </div>
                      <div class="col-md-6">
                        <div class="form-group">
                          <label>Capacity Utilization (%)</label>
                          <input type="text" class="form-control" name=""> 
                        </div>
                      </div>
                    </div>


                    <div class="row">
                      <div class="col-sm-12 mb-3 mt-3">
                        <h3 class="common-heading">Project Cost <small>(indicating proposed and appraised Project Cost, separately)</small></h3>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-md-12">
                        <div class="table-responsive">
                          <table class="table table-bordered">
                            <thead>
                              <tr>
                                <th style="width:40%;">Items</th>
                                <th>Cost of existing Items</th>
                                <th>Proposed Cost</th>
                                <th>Appraised Cost</th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr>
                                <td colspan="4">
                                  <h5><strong>Capital Investment <small>(Fixed Capital)</small></strong></h5>
                                </td>
                              </tr>
                              <tr>
                                <td>Land Area Cost <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td colspan="4">
                                  <h5><strong>Building</strong></h5>
                                </td>
                              </tr>
                              <tr>
                                <td>Civil Works <small>(Other than Civil Work)</small> <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Technical Civil Works <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Plant & Machinery (Indigenous) List all existing, and proposed plant & machinery details of existing/proposed to be installed <a href="pdf/A1.pdf" target="_blank">annexure (A/1)</a> <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Imported Machinery (Capacity/Specification /Cost) List all existing, and proposed plant & machinery details of existing/proposed to be installed <a href="pdf/A1.pdf" target="_blank">annexure (A/1)</a> <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Pre-operative Expenses <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Working Capital <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Raw Material/Packaging List all the raw materials and packaging items required for the project <small>(Source/Quantity/Cost)</small> <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Labour <small>(Quantity/Cost)</small> <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Effluent Disposal (Method/Machinery/Cost) List all the Machines to be Installed <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td><strong>Total</strong></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td colspan="4">
                                  <h5><strong>Means of Finance <small>(indicating proposed & appraised means of finance, separately)</small></strong></h5>
                                </td>
                              </tr>
                              <tr>
                                <td><strong>Means of Finance</strong></td>
                                <td><strong>Existing</strong></td>
                                <td><strong>Proposed</strong></td>
                                <td><strong>Appraised</strong></td>
                              </tr>
                              <tr>
                                <td>Equity <small>(Promoter /Foreign / Other)</small> <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Term Loan <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Assistance From Other Sources <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Grant-in-aid <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Others</td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td><strong>Total</strong></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td colspan="4">
                                  <h5><strong>Financial Benchmarks</strong></h5>
                                </td>
                              </tr>
                              <tr>
                                <td><strong>Items</strong></td>
                                <td><strong>Existing</strong></td>
                                <td colspan="2"><strong>Projected</strong></td>
                              </tr>
                              <tr>
                                <td>Cash Flow <small>(.pdf File Upload Only)</small> <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td colspan="2"><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Break Even Point <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td colspan="2"><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Internal Rate of Return <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td colspan="2"><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Debt Equity Ratio <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td colspan="2"><input type="text" class="form-control" name=""></td>
                              </tr>
                              <tr>
                                <td>Debt Service Coverage Ratio <span class="text-danger">*</span></td>
                                <td><input type="text" class="form-control" name=""></td>
                                <td colspan="2"><input type="text" class="form-control" name=""></td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </div>


                    <hr class="mt-4">


                    <div class="row">
                      <div class="col-sm-4">
                        <a href="./intrestSubsidyFoodPolicyStepFirst" class="common-second-btn mt-3">Back</a>
                      </div>
                      <div class="col-sm-8 text-right">
                        <a href="./intrestSubsidyFoodPolicyStepThird" class="common-btn mt-3 next-step">Save and continue</a>
                      </div>
                    </div>

                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>


    <footer>
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

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>
  </body>
</html>