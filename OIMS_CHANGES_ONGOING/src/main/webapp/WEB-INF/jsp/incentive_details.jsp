<!doctype html>


<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Incentive Details</title>
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
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
          <button class="navbar-toggler top-main-menu" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
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

          <div class="col-md-9 col-lg-10 mt-4 main">
            <div class="card card-block p-3">
              <div class="row">
                <div class="col-sm-6">
                  <h4 class="card-title mt-2">Incentive Details</h4>
                </div>
                <div class="col-sm-6 text-right">
                  <a href="#" class="common-btn mt-0">Generate LoC</a>
                  <a href="#" class="common-green-btn mt-0">Accept Form</a>
                </div>
              </div>
              <div class="table-responsive mt-3">
                <table class="table table-stripped table-bordered">
                  <thead>
                    <tr>
                      <th>S.No.</th>
                      <th style="width:18%;">Incentive Type Details</th>
                      <th>Incentives Sought</th>
                      <th>Incentives Admissible</th>
                      <th>Remarks (if any)</th>
                      <th>Select Relevant Deptt</th>
                      <th>Comments from Relevant Deptt</th>
                      <th>Final Comments by Nodal Agency</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>1</td>
                      <td>Capital Interest Subsidy</td>
                      <td><input type="text" class="form-control" name="" value="" disabled=""></td>
                      <td><input type="text" class="form-control" name="" value=""></td>
                      <td><input type="text" class="form-control" name=""></td>
                      <td>
                        <select class="form-control">
                          <option class="NONE">Select One</option>
                          <option class="1">Deptt 1</option>
                          <option class="2">Deptt 2</option>
                        </select>
                      </td>
                      <td><input type="text" class="form-control" name=""></td>
                      <td><input type="text" class="form-control" name=""></td>
                    </tr>
                    <tr>
                      <td>2</td>
                      <td>Infrastructure Interest Subsidy</td>
                      <td><input type="text" class="form-control" value="" disabled="" name=""></td>
                      <td><input type="text" class="form-control" name="" value=""></td>
                      <td><input type="text" class="form-control" name=""></td>
                      <td>
                        <select class="form-control">
                          <option class="NONE">Select One</option>
                          <option class="1">Deptt 1</option>
                          <option class="2">Deptt 2</option>
                        </select>
                      </td>
                      <td><input type="text" class="form-control" name=""></td>
                      <td><input type="text" class="form-control" name=""></td>
                    </tr>
                    <tr>
                      <td>3</td>
                      <td>Interest Subsidy on loans for industrial research, quality improvement, etc.</td>
                      <td><input type="text" class="form-control" " disabled="" name="" value=""></td>
                      <td><input type="text" class="form-control" name="" value=""></td>
                      <td><input type="text" class="form-control" name=""></td>
                      <td>
                        <select class="form-control">
                          <option class="NONE">Select One</option>
                          <option class="1">Deptt 1</option>
                          <option class="2">Deptt 2</option>
                        </select>
                      </td>
                      <td><input type="text" class="form-control" name=""></td>
                      <td><input type="text" class="form-control" name=""></td>
                    </tr>
                  </tbody>
                </table>
                
              </div>
              <div class="row">
                <div class="col-sm-3">
                  <a href="./reviewIncentiveApplication" class="common-default-btn mt-3">Back</a>
                </div>
                <div class="col-sm-9 text-right">
                  <a href="#" class="common-btn mt-3">Propose Agenda Note</a>
                  <a href="#" class="common-btn mt-3">Preview</a>
                  <a href="#" class="common-btn mt-3">Send for Review</a>
                  <a href="#" class="common-green-btn mt-3">Save</a>
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

  <script>
    $(document).ready(function() {
      $("[data-toggle=offcanvas]").click(function() {
        $(".row-offcanvas").toggleClass("active");
      });
    });
  </script>
  </body>
</html>
