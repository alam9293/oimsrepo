<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Prepare Agenda Note</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
    <script data-require="jquery@*" data-semver="2.0.3" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
    <script data-require="bootstrap@*" data-semver="3.1.1" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">    
   <script type="text/javascript">
   function prepareAgendaNoteValidation()
   {
	   var selected = new Array();

       $("#table1 input[type=checkbox]:checked").each(function () {

           selected.push(this.value);
           selected.join(",")
       });

       if (selected.length<= 0) {
           alert('Please select Application Included for Agenda Note Preparation.');
           return false;
        }        
	  }

   function preparedAgendaNoteValidation()
   {
	   var selected = new Array();

       $("#table2 input[type=checkbox]:checked").each(function () {

           selected.push(this.value);
           selected.join(",")
       });

       if (selected.length<= 0) {
           alert('Please select view prepare Agenda Note.');
           return false;
        }        
	  }
   function approvedAgeNoLargeValidation()
   {
	   var selected = new Array();

       $("#table3 input[type=checkbox]:checked").each(function () {

           selected.push(this.value);
           selected.join(",")
       });

       if (selected.length<= 0) {
           alert('Please select view prepare Agenda Note.');
           return false;
        }        
	  }

   function addNote() {
		var r = confirm("Are you Sure Want to Submit ?");

		if (r == true) {
			alert("Note Added Successfully");
		} else {
			return false
		}
	}

	function submitToComitee() {
		var r = confirm("Are you Sure Want to Submit the Application to Commitee?");

		if (r == true) {
			alert("Application Submitted to Commitee Successfully");
		} else {
			return false
		}
	}

	function circulateAgendaNote() {
		var r = confirm("Are you Sure Want to Circulate the Agenda Note?");

		if (r == true) {
			alert("Agenda Note Circulated to Concerned Department Successfully");
		} else {
			return false
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
              <a class="nav-link" href="./userLogout"><i class="fa fa-power-off"></i>Logout</a>
            </li>

            <li class="nav-item">
              <a class="nav-link active" href="#"><i class="fa fa-user"></i>${userName}</a>
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
              <li class="nav-item animate__animated animate__bounce"><a
							class="nav-link" href="./dicDepartmentDashboard"><i
								class="fa fa-tachometer"></i> Dashboard</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewDICApplication"><i class="fa fa-eye"></i> View
								SME applications</a></li>
						<li class="nav-item"><a class="nav-link" href="./queryRaisedDIC"><i
								class="fa fa-question-circle"></i> Query Raised</a></li>
						<li class="nav-item"><a class="nav-link"
							href="./viewQueryResponseDIC"><i class="fa fa-question-circle"></i>
								Query Response By Entrepreneur</a></li>
						<li class="nav-item"><a class="nav-link active" href="./prepareAgendaNoteDicList"><i
								class="fa fa-list"></i> Prepare Agenda Note</a></li>
						<li class="nav-item"><a class="nav-link"
							href="#"><i class="fa fa-calendar"></i>
								Schedule meeting</a></li>
						<li class="nav-item"><a class="nav-link" href="#"><i
								class="fa fa-calendar"></i> Minutes of Meeting and GO's</a></li>
						<li class="nav-item"><a class="nav-link" href="./dicgenerateLoc"><i
								class="fa fa-wpforms"></i> Generate LOC</a></li>
            </ul>
          </div>
          <!--/col-->

          <div class="col-md-9 col-lg-10 mt-4 main">
            <h4 class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Prepare Agenda Note</h4>
            <div class="card card-block p-4 mb-5">
              <div id="accordion" class="accordion mt-3 mb-3 animate__animated animate__pulse">
                        <div class="mb-0 border-0">
                            <div class="card-header collapsed mb-4" data-toggle="collapse" href="#collapseOne">
                                <a class="card-title">
                                  <strong> Applications included for Agenda Note Preparation</strong>
                                </a>
                            </div>
                             <form:form modelAttribute="PrepareAgendaNotes"  action="prepareAgendaNoteDic" class="mt-4" name="PrepareAgendaNotes" method="post" onsubmit="return prepareAgendaNoteValidation()">
                            <div id="collapseOne" class="card-body collapse" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-bordered" id="table1">
                                      <thead>
                                        <tr>
                                          <th></th>
                                          <th>S.No</th>
                                          <th>Application ID</th>
                                          <th>Company Name</th>
                                          <th>Investment</th>
                                          <th>Action</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <c:forEach var="list" items="${prepareAgendaNotesSmallMediumLists}" varStatus="counter">
                                        <tr>
                                        <td><input type="checkbox" name="prepareAgendaNote" id="prepareAgendaNoteId" value="${list.appliId}"></td>
                                        <td class="text-center">${counter.index+1}</td>
                                        <td>${list.appliId}</td>
                                        <td>${list.companyName}</td>
                                        <td>${list.investment}</td>                                   
                                        <td>
                                        <a href="./viewAgendaDetailsDic?applicantId=${list.appliId}" class="btn btn-outline-info btn-sm">View</a>
                                        <a href="#AddNote" data-toggle="modal" id="${list.appliId}" data-target="#edit-modal" class="btn btn-outline-success btn-sm">Add Note</a>                                                                               
                                        </td>
                                        </tr>
                                       </c:forEach>
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
                                <div class="col-sm-12 text-right">
                                <form:button type="submit" class="common-btn mt-3">Prepare Agenda Note</form:button>                                  
                                </div>
                              </div>
                            </div>
                            </form:form>

                            <div class="card-header collapsed mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                <a class="card-title">
                                  <strong>Agenda Note Approved for JCI</strong>
                                </a>
                            </div>
                            
                             <form:form modelAttribute="PrepareAgendaNotes"  action="viewCommitteeAgendaNoteByDicDepart" class="mt-4" name="PrepareAgendaNotes" method="post" onsubmit="return approvedAgeNoLargeValidation()">
                             <div id="collapseThree" class="card-body collapse" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-bordered" id="table3">
                                      <thead>
                                        <tr>
                                          <th></th>
                                          <th>S.No</th>
                                          <th>Agenda Name</th>
                                          <th>Submission Date</th>
                                          <th>Approval Date</th>
                                          <th>Action</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                         <c:forEach var="list" items="${prepareAgenNotesSmallMediumAprrovalLists}" varStatus="counter">
                                        <tr>
                                        <td><input type="checkbox" name="prepareAgendaNote" id="prepareAgendaNoteId" value="${list.appliId}"></td>                                        
                                        <td class="text-center">${counter.index+1}</td>
                                        <td></td>
                                        <td>${list.createDate}</td>
                                        <td></td>                                                         
                                        <td>
                                             <input type="submit" value="View" class="btn btn-outline-info btn-sm">                                            
                                            <a href="./submitToComiteeByDicDepartment?applicantId=${list.appliId}" onclick="return submitToComitee()" class="btn btn-outline-info btn-sm">Submit to Commitee </a>                                           
                                          </td>
                                        </tr>
                                       </c:forEach>   
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
                              </div>

                            </div>
                           </form:form>                       
                            <div class="card-header collapsed mb-4" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                <a class="card-title">
                                  <strong>View prepare Agenda Note</strong>
                                </a>
                            </div>
                             <form:form modelAttribute="PrepareAgendaNotes"  action="approvingAgendaNoteByDicDepart" class="mt-4" name="PrepareAgendaNotes" method="post" onsubmit="return preparedAgendaNoteValidation()">
                            <div id="collapseFour" class="card-body collapse" data-parent="#accordion" >

                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="table-responsive">
                                    <table class="table table-bordered" id="table2">
                                      <thead>
                                        <tr>
                                          <th></th>
                                          <th>S.No</th>
                                          <th>Application ID</th>
                                          <th>Company Name</th>                                       
                                          <th>Investment</th>
                                          <th>Location</th>
                                          <th>Action</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <c:forEach var="list" items="${prepareAgenNotesSmallMediumPreparedLists}" varStatus="counter">
                                        <tr>
                                        <td><input type="checkbox" name="prepareAgendaNote" id="prepareAgendaNoteId" value="${list.appliId}"></td>
                                        <td class="text-center">${counter.index+1}</td>
                                        <td>${list.appliId}</td>
                                        <td>${list.companyName}</td>
                                        <td>${list.investment}</td>
                                        <td></td>                                   
                                        <td>
                                        <input type="submit" value="View" class="btn btn-outline-info btn-sm">                                                                                                                                                             
                                        </td>
                                        </tr>
                                       </c:forEach>
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
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

    <div class="container">
       <div class="modal fade" id="edit-modal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">Add Your Note</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <form:form modelAttribute="PrepareAgendaNotes"  action="saveDicDepartmentNotes" class="mt-4" name="PrepareAgendaNotes" method="post">
            <div class="modal-body">
              <div class="row">
                <div class="col-sm-12">
                  <div class="form-group">
                    <textarea class="form-control" rows="6" name="notes" maxlength="1000"></textarea>
                    <form:hidden path="appliId" id="appliId"></form:hidden>
                    <form:hidden path="id" id="id"></form:hidden>
                    <form:hidden path="companyName" id="companyName"></form:hidden>
                    <form:hidden path="investment" id="investment"></form:hidden>                  
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <input type="submit" value="submit" onclick="return addNote()" class="common-btn mt-0">
            </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>


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
$('#edit-modal').on('show.bs.modal', function(e) {
          
          var $modal = $(this),
              esseyId = e.relatedTarget.id; 
          document.getElementById("appliId").value = e.relatedTarget.id;         
      })
    });
  </script>
  </body>
</html>