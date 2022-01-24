<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Other Incentive</title>
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
              <a class="nav-link" href="training.html" target="_blank">Training</a>
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
              <div class="without-wizard-steps">
                <h4 class="card-title mb-4 mt-4 text-center">Other Incentive</h4>
                <div class="isf-form">
         <form:form modelAttribute="otherDisbursement" id="disForm"
								name="" autocomplete="off" method="POST"
								enctype="multipart/form-data" action="saveOtherIncentive"
								class="mt-4">
                  <form class="mt-4">
                  <div class="row">
                      <div class="col-sm-12 mt-4">
                        <div class="table-responsive">
                          <table class="table table-bordered">
                            <thead>
                              <tr>
                                <th width="70%">Incentives Sought</th>
                               
                                <th>Amount</th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr>
                                <td>Reimbursement of Disallowed Input Tax Credit on plant, building materials, and other capital goods.</td>
                                
                                <td>
                                 <form:input path="reimDissAllowedAvailAmt" onblur="emptyCapInvErrMsg()"
																value="" type="text" maxlength="12" id="reimAvailAmt"
																disabled="false" class="form-control is-numeric"></form:input>
																<small class="words text-info"></small>
															<span id="reimAvailAmtRR" class="text-danger"></span></td>
                          
                              </tr>
                              <tr>
                                <td>Exemption from Electricity Duty from captive power for self-use</td>
                                
                                <td>
                                 <form:input path="exCapitivePowerAmt" onblur="emptyCapInvErrMsg()"
																value="" type="text" maxlength="12" id="exCaptiveAmt"
																disabled="false" class="form-control is-numeric" name=""></form:input>
																<small class="words text-info"></small>
															<span id="exCaptiveAmtEE" class="text-danger"></span></td>
                              
                              </tr>
                              <tr>
                                <td>Exemption from Electricity duty on power drawn from power companies</td>
                                
                                <td>
                                  <form:input path="exePowerDrawnAmt" onblur="emptyCapInvErrMsg()"
																value="" type="text" maxlength="12" id="exPowerAmt"
																disabled="false" class="form-control is-numeric" name=""></form:input>
																<small class="words text-info"></small>
															<span id="exPowerAmtPP" class="text-danger"></span></td>
                              
                              </tr>
                              <tr>
                                <td>Exemption from Mandi Fee</td>
                                
                                <td>
                                  <form:input path="exeMandiFreeAmt" onblur="emptyCapInvErrMsg()"
																value="" type="text" maxlength="12" id="exMandiAmt"
																disabled="false" class="form-control is-numeric" name=""></form:input>
																<small class="words text-info"></small>
															<span id="exMandiAmtMM" class="text-danger"></span></td>
                      
                              </tr>
                              <tr>
                                <td>Industrial units providing employment to differently abled workers will be provided payroll assistance of Rs. 500 per month for each such worker</td>
                                
                                <td>
                                  <form:input path="industrialUnitAmt" onblur="emptyCapInvErrMsg()"
																value="" type="text" maxlength="12" id="induDiffAbleAmt"
																disabled="false" class="form-control is-numeric" name="" ></form:input>
																<small class="words text-info"></small>
															<span id="induDiffAbleAmtDD" class="text-danger"></span></td>
                             
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </div>

                    <hr>

                    <div class="row">
                      <div class="col-sm-12 mt-4">
                        <h3 class="common-heading">Declaration</h3>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-sm-12">
                        <div class="form-group mt-3">
                            <div class="custom-control custom-checkbox custom-control-inline">
                              <input type="checkbox" class="custom-control-input" id="verified" value="Yes" name="Capital-Interest-Subsidy">
                              <label class="custom-control-label" for="verified" >The above information are completely true and no fact has been concealed or
                              misrepresented. It is further certified that the company has not applied for benefits of
                              the above nature under any sector-specific or other policy of the Government of Uttar
                              Pradesh for purpose of availing benefits of the above nature.
                              I/we hereby agree that I/we shall forthwith repay the benefits released to me/us under
                              Rules of Policy for Promotion of Industrial Investment and Employment-2017, if the
                              said benefits are found to be disbursed in excess of the amount actually admissible
                              whatsoever the reason. </label>
                            </div>
                          </div>
                      </div>
                    </div>

                    <hr>

                    <div class="row">
                      <div class="col-sm-5">
                        <a href="./disincentivetype" class="common-default-btn mt-3 prev-step" onclick="return confirm('Are you sure you want to go to SELECT INCENTIVE TYPE?')" >Back</a>
                      </div>
                      <div class="col-sm-7 text-right">
                 <form:button class="common-btn mt-3" onclick="return validateOtherInc()">Submit</form:button>
                      </div>
                    </div>

                  </form>
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
    <script src="js/datepicker.js"></script>
    <script src="js/custom.js"></script>

 <script type="text/javascript">

function  validateOtherInc()
{
	var cutOffValue=document.getElementById('reimAvailAmt').value;
	if(cutOffValue == null || cutOffValue == "")
		{
		document.getElementById('reimAvailAmtRR').innerHTML="field can't be empty";
		document.getElementById('reimAvailAmt').focus();
		return false;
		}

	var cutOffValue=document.getElementById('exCaptiveAmt').value;
	if(cutOffValue == null || cutOffValue == "")
		{
		document.getElementById('exCaptiveAmtEE').innerHTML="field can't be empty";
		document.getElementById('exCaptiveAmt').focus();
		return false;
		}

	var cutOffValue=document.getElementById('exPowerAmt').value;
	if(cutOffValue == null || cutOffValue == "")
		{
		document.getElementById('exPowerAmtPP').innerHTML="field can't be empty";
		document.getElementById('exPowerAmt').focus();
		return false;
		}

	var cutOffValue=document.getElementById('exMandiAmt').value;
	if(cutOffValue == null || cutOffValue == "")
		{
		document.getElementById('exMandiAmtMM').innerHTML="field can't be empty";
		document.getElementById('exMandiAmt').focus();
		return false;
		}

	var cutOffValue=document.getElementById('induDiffAbleAmt').value;
	if(cutOffValue == null || cutOffValue == "")
		{
		document.getElementById('induDiffAbleAmtDD').innerHTML="field can't be empty";
		document.getElementById('induDiffAbleAmt').focus();
		return false;
		}


	if (!$('#verified').is(':checked')) {
		alert(' Please select/check the Declaration');
		$("#verified").focus();
		return false;
	}

	var r = confirm("Are you Sure want to Submit the Form?");
	
	if (r == true) {
		alert("Application Form is Submitted Succesfully");
	} else {
		return false
	}

	
	}

function emptyCapInvErrMsg() {
	

	$("#reimAvailAmtRR").text('');
	$("#exCaptiveAmtEE").text('');
	$("#exPowerAmtPP").text('');
	$("#exMandiAmtMM").text('');
	
	$("#induDiffAbleAmtDD").text('');
}

    </script>

  </body>
</html>