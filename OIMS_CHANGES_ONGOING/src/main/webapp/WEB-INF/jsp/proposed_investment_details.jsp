<!doctype html>
<html lang="en">
  <head>    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Proposed Investment Details</title> 
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>  
    <script type="text/javascript">
	function proposedInvetmentDetailsForm() {
		var msgbox = "";
		var temp = true;
		var financialyear = document.proposedDetailsForm.financialyear.value;
		var male = document.proposedDetailsForm.male.value;
		var female = document.proposedDetailsForm.female.value;
		var indrectFY = document.proposedDetailsForm.indrectFY.value;
		var indirectmale = document.proposedDetailsForm.indirectmale.value;
		var indirectfemale = document.proposedDetailsForm.indirectfemale.value;	

		if (financialyear == null || financialyear == '') {
			msgbox += "Financial Year is Required.";
			temp = false;
		}
 
		if (male == null || male == '') {
			if (msgbox.length > 0) {
				msgbox += " \n Direct Male Employees is Required.";
			} else {
				msgbox += " \n Direct Male Employees is Required.";
			}
			temp = false;
		}
	
	 if (female == null || female == '') {
		if (msgbox.length > 0) {
			msgbox += " \n Direct Female Employees is Required.";
		} else {
			msgbox += " \n Direct Female Employees is Required.";
		}
		temp = false;
	}
		if (indrectFY == null || indrectFY == '') {
		if (msgbox.length > 0) {
			msgbox += " \n Indirect Financial Year is Required.";
		} else {
			msgbox += " \n Indirect Financial Year is Required.";
		}
		temp = false;
	}
 	if (indirectmale == null || indirectmale == '') {
		if (msgbox.length > 0) {
			msgbox += " \n Indirect Male Employees is Required.";
		} else {
			msgbox += " \n Indirect Male Employees is Required.";
		}
		temp = false;
	}
	if (indirectfemale == null || indirectfemale == '') {
		if (msgbox.length > 0) {
			msgbox += " \n Indirect Female Employees is Required.";
		} else {
			msgbox += " \n indirect Female Employees is Required.";
		}
		temp = false;
	} 
 	if (!temp) {
		alert(msgbox);
		return temp;
	}
}
	function previous() { 
        window.history.back() 
    } 
</script>    
  </head>
  
  <%@ include file = "header.jsp" %>
  <body class="bottom-bg">
    <section class="common-form-area">
      <div class="container">
        <!-- Main Title -->
        <div class="inner-banner-text">
          <h1>Application Form</h1>
        </div>
        <div class="row">
          <div class="col-sm-12">
            <div class="form-card">
              <div class="wizard-steps">
                <div class="row">
                    <section class="col-12">
                        <ul class="nav nav-tabs flex-nowrap" role="tablist">
                            <li role="presentation" class="nav-item">
                                <a href="#step1" class="nav-link" data-toggle="tab" title="Step 1"> <span>1</span> Applicant Details </a>
                            </li>
                            <li role="presentation" class="nav-item">
                                <a href="#step2" class="nav-link" data-toggle="tab" title="Step 2"> <span>2</span> Business Entity Details</a>
                            </li>
                            <li role="presentation" class="nav-item">
                                <a href="#step3" class="nav-link" data-toggle="tab" title="Step 3"> <span>3</span> Project Details</a>
                            </li>
                            <li role="presentation" class="nav-item">
                                <a href="#step4" class="nav-link" data-toggle="tab"  title="Step 4"> <span>4</span> Investment Details</a>
                            </li>
                            <li role="presentation" class="nav-item">
                                <a href="#step5" class="nav-link active" data-toggle="tab"  title="Step 5"> <span>5</span> Proposed Employment Details</a>
                            </li>                            
                        </ul>
                        <form name="proposedDetailsForm" onsubmit="return proposedInvetmentDetailsForm()" autocomplete="on" action="proposedinvestmentdetails" method="get">
                            <div class="tab-content py-2">
                                
                                    <div class="row">
                                      <div class="col-sm-12 mb-3 mt-2">
                                        <label><strong>Direct Employment</strong></label>
                                      </div>
                                    </div>

                                    <div class="row">
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>Financial Year <span>*</span></label>
                                          <select class="form-control" name="financialyear">
                                            <option value="">Select One</option>
                                            <option value="2">1990</option>
                                            <option value="2">1991</option>
                                          </select>
                                        </div>
                                      </div>
                                      <div class="col-md-4 col-lg-8 col-xl-8">
                                        <div class="form-group">
                                          <label>Select Employment Details Skilled/Unskilled</label>
                                          <select class="form-control">
                                            <option value="1">Select One</option>
                                          </select>
                                        </div>
                                      </div>
                                    </div>

                                    <div class="row">
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>Direct Male Employees <span>*</span></label>
                                          <input type="text" class="form-control" id="" name="male">
                                        </div>
                                      </div>
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>Direct Female Employees <span>*</span></label>
                                          <input type="text" class="form-control" id="" name="female">
                                        </div>
                                      </div>
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group text-right mt-1">
                                          <button type="submit" class="common-btn mt-4 next-step">Save</button>
                                        </div>
                                      </div>
                                    </div>

                                    <hr class="mt-4">

                                    <div class="row">
                                      <div class="col-sm-12">
                                        <div class="table-responsive mt-3">
                                          <table class="table table-stripped table-bordered">
                                            <thead>
                                              <tr>
                                                <th>Sr. No.</th>
                                                <th>Financial Year</th>
                                                <th>Employment Details Skilled/Unskilled</th>
                                                <th>Direct Male</th>
                                                <th>Direct Female</th>
                                                <th>Action</th>
                                              </tr>
                                            </thead>
                                            <tbody>
                                              <tr>
                                                <td class="text-center">1</td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td class="text-center"><i class="fa fa-trash text-danger"></i></td>
                                              </tr>
                                              <tr>
                                                <td class="text-center">2</td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td class="text-center"><i class="fa fa-trash text-danger"></i></td>
                                              </tr>
                                            </tbody>
                                          </table>
                                        </div>
                                      </div>
                                    </div>


                                    <div class="row">
                                      <div class="col-md-12">
                                        <div class="form-group text-right mt-1">
                                          <button type="button" class="common-green-btn mt-3">Add More Row</button>
                                        </div>
                                      </div>
                                    </div>



                                    <div class="row">
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>Direct Male Employees</label>
                                          <input type="text" class="form-control" value="9" disabled="" id="" name="">
                                        </div>
                                      </div>
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>Direct Female Employees</label>
                                          <input type="text" class="form-control" value="4" disabled="" id="" name="">
                                        </div>
                                      </div>
                                    </div>

                                    <hr class="mt-4">

                                    <div class="row">
                                      <div class="col-sm-12 mb-3 mt-2">
                                        <label><strong>InDirect Employment</strong></label>
                                      </div>
                                    </div>

                                    <div class="row">
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>Financial Year <span>*</span></label>
                                          <select class="form-control" name="indrectFY">
                                            <option value="">Select One</option>
                                            <option value="1">1990</option>
                                            <option value="2">1991</option>
                                          </select>
                                        </div>
                                      </div>
                                      <div class="col-md-4 col-lg-8 col-xl-8">
                                        <div class="form-group">
                                          <label>Select Employment Details Skilled/Unskilled</label>
                                          <select class="form-control">
                                            <option value="1">Select One</option>
                                          </select>
                                        </div>
                                      </div>
                                    </div>

                                    <div class="row">
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>InDirect Male Employees <span>*</span></label>
                                          <input type="text" class="form-control" id="" name="indirectmale">
                                        </div>
                                      </div>
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>InDirect Female Employees <span>*</span></label>
                                          <input type="text" class="form-control" id="" name="indirectfemale">
                                        </div>
                                      </div>
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group text-right mt-1">
                                          <button type="submit" class="common-btn mt-4 next-step">Save</button>
                                        </div>
                                      </div>
                                    </div>

                                    <hr class="mt-4">

                                    <div class="row">
                                      <div class="col-sm-12">
                                        <div class="table-responsive mt-3">
                                          <table class="table table-stripped table-bordered">
                                            <thead>
                                              <tr>
                                                <th>Sr. No.</th>
                                                <th>Financial Year</th>
                                                <th>Employment Details Skilled/Unskilled</th>
                                                <th>InDirect Male</th>
                                                <th>InDirect Female</th>
                                                <th>Action</th>
                                              </tr>
                                            </thead>
                                            <tbody>
                                              <tr>
                                                <td class="text-center">1</td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td class="text-center"><i class="fa fa-trash text-danger"></i></td>
                                              </tr>
                                              <tr>
                                                <td class="text-center">2</td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td class="text-center"><i class="fa fa-trash text-danger"></i></td>
                                              </tr>
                                            </tbody>
                                          </table>
                                        </div>
                                      </div>
                                    </div>

                                    <div class="row">
                                      <div class="col-md-12">
                                        <div class="form-group text-right mt-1">
                                          <button type="button" class="common-green-btn mt-3">Add More Row</button>
                                        </div>
                                      </div>
                                    </div>



                                    <div class="row">
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>InDirect Male Employees</label>
                                          <input type="text" class="form-control" value="9" disabled="" id="" name="">
                                        </div>
                                      </div>
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>InDirect Female Employees</label>
                                          <input type="text" class="form-control" value="7" disabled="" id="" name="">
                                        </div>
                                      </div>
                                    </div>

                                    <hr class="mt-4">

                                    <div class="row">
                                      <div class="col-sm-12 mb-3 mt-2">
                                        <label><strong>Total Direct Employment</strong></label>
                                      </div>
                                    </div>

                                    <div class="row">
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>Total Male Employees</label>
                                          <input type="text" class="form-control" value="18" disabled="" id="" name="">
                                        </div>
                                      </div>
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>Total Female Employees</label>
                                          <input type="text" class="form-control" value="11" disabled="" id="" name="">
                                        </div>
                                      </div>
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group text-right">
                                          <label class="total-emp">Total Employees = 29</label>
                                        </div>
                                      </div>
                                    </div>


                                    <hr class="mt-4 mb-4">

                                    <div class="row">
                                      <div class="col-sm-6">
                                      <button type="button" class="common-second-btn mt-3" onClick="window.location.href='/ims/selection_policy';">Cancel</button>                                
                                     
                                      </div>
                                      <div class="col-sm-6 text-right">
                                        <button type="button" class="common-default-btn mt-3 prev-step" onclick="previous()">Previous</button>
                                        <button type="submit" class="common-btn mt-3">submit</button>
                                      </div>
                                    </div>
                                                               
                                <div class="clearfix"></div>
                            </div>
                        </form>
                    </section>
                </div>
            </div>
            </div>
          </div>
        </div>
      </div>
    </section>   
  </body>
</html>
<%@ include file = "footer.jsp" %>