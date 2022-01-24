<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Disbursement Incentive</title>
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
              <a class="nav-link" href="training.html">Training</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="http://invest.up.gov.in/" target="_blank">Invest UP</a>
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
        <!-- Main Title -->
        <div class="inner-banner-text">
          <h1>Application Form</h1>
        </div>
        <div class="row">
          <div class="col-sm-12">
            <div class="form-card">
              <div class="without-wizard-steps select-incentive">
                <h4 class="card-title mb-4 mt-4 text-center">Select Incentive Type</h4>
                <hr>
                <div class="isf-form">
                <form:form modelAttribute="incentivetype"
									name="incentivetype" method="post" id="myForm"
									enctype="multipart/form-data">
                 

                    <div class="row mt-5">
                  <div class="col-sm-4 mb-5 dis-sgst">
                    <div class="card card-block policy-box-card p-3 h-100" id="reimActive">
                      <div class="ploicy-name h-100">
                        <a href="./disReimbrsDepositeGST" class="h-100">
                          <label class="select-incentive-heading">Reimbursement Of Deposited GST</label>
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 mb-5 dis-cis">
                    <div class="card card-block policy-box-card p-3 h-100" id="capinvestActive">
                      <div class="ploicy-name h-100">
                        <a href="./disCis" class="h-100">
                          <label class="select-incentive-heading">Capital Interest Subsidy</label>
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 mb-5 dis-infra" >
                    <div class="card card-block policy-box-card p-3 h-100" id="infrasubActive">
                      <div class="ploicy-name h-100">
                        <a href="./disiis" class="h-100">
                          <label class="select-incentive-heading">Infrastructure Interest Subsidy</label>
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 mb-5 dis-epf">
                    <div class="card card-block policy-box-card p-3 h-100" id="epfinvActive">
                      <div class="ploicy-name h-100">
                        <a href="./disepfincentive" class="h-100">
                          <label class="select-incentive-heading">Employees Provident Fund Reimbursement</label>
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 mb-5 dis-stamp">
                    <div class="card card-block policy-box-card p-3 h-100" id="stampinvActive">
                      <div class="ploicy-name h-100">
                        <a href="./stampDuty" class="h-100">
                          <label class="select-incentive-heading">Stamp Duty Exemption/Reimbursement</label>
                        </a>
                      </div>
                    </div>
                  </div>
                   <!-- <div class="col-sm-4 mb-5 dis-qis">
                    <div class="card card-block policy-box-card p-3 h-100">
                      <div class="ploicy-name">
                        <a href="Dis-industrial-research.html">
                          <label class="select-incentive-heading">Interest Subsidy For Industrial Research, Quality Improvement & Development Of Product</label>
                        </a>
                      </div>
                    </div>
                  </div> -->
                  <div class="col-sm-4 mb-5 dis-other">
                    <div class="card card-block policy-box-card p-3 h-100" id="othinvActive">
                      <div class="ploicy-name h-100">
                        <a href="./otherIncentive" class="h-100">
                          <label class="select-incentive-heading">Other Incentive</label>
                        </a>
                      </div>
                    </div>
                  </div>
                </div>

                <hr>

                    <div class="row">
                      <div class="col-sm-5">
                        
                        <a href="./disEmploymentDetails"
											onclick="return confirm('Are you sure you want to go to Employment Details?')"
											class="common-default-btn mt-3 prev-step">Back</a>
                      </div>
                      <div class="col-sm-7 text-right  dis-sub">
                      	<button type="submit" formaction="submitIncentivetype" onclick="return confirm('Are you sure want to Submit the Application Form?')"
												
												class="common-btn mt-3">Submit</button>
                      </div>
                    </div>
                    
                    

                 
                  </form:form>
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
                <li><a href="http://invest.up.gov.in/" target="_blank">Home</a></li>
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
    <script src="js/datepicker.js"></script>
    <script src="js/custom.js"></script>
    <script>
	$(document).ready(function() {
		var sgst = '${sgst}';

		if (sgst == '' || sgst == '0') {
			$(".dis-sgst").hide();
		}

		var cis = '${cis}';

		if (cis == '' || cis == '0') {
			$(".dis-cis").hide();
		}

		var stamp = '${stamp}';

		if (stamp == '' || stamp == '0') {
			$(".dis-stamp").hide();
		}

		var epf = '${epf}';

		if (epf == '' || epf == '0') {
			$(".dis-epf").hide();
		}

		var infra = '${infra}';

		if (infra == '' || infra == '0') {
			$(".dis-infra").hide();
		}

		var other = '${other}';

		if (other == '' || other == '0') {
			$(".dis-other").hide();
		}

		var qis = '${qis}';

		if (qis == '' || qis == '0') {
			$(".dis-qis").hide();
		}

		var step = '${proceed}';

		if (step == 'stop')

			$(".dis-sub").hide();
		
	});

	$(document).ready(function(){

		var capinvest = '${capinvest}';
		var infrasub = '${infrasub}';
		var epfinv = '${epfinv}';
		var reim = '${reim}';
		var stam = '${stam}';
		var oth = '${oth}';
		if (capinvest != '' && capinvest != null){
			$("#capinvestActive").addClass("active");
			}
		if (infrasub != '' && infrasub != null){
			$("#infrasubActive").addClass("active");
			}
		if (epfinv != '' && epfinv != null){
			$("#epfinvActive").addClass("active");
			}
		if (reim != '' && reim != null){
			$("#reimActive").addClass("active");
			}

		if (oth != '' && oth != null){
			$("#othinvActive").addClass("active");
			}

		if (stam != '' && stam != null){
			$("#stampinvActive").addClass("active");
			}
		});
		
		

	
</script>


  </body>
</html>