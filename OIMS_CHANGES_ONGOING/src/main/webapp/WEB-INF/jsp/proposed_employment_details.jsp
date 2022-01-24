<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.webapp.ims.model.ProposedEmploymentDetails" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html lang="en">
  <head>    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <title>Proposed Employment Details</title>
<style type="text/css">
 error {
	color: red;
     }
 .disable_a_href{
    pointer-events: none;     
</style>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>
    <script src="js/proposedEmploymentDetails.js"></script>
    <script type="text/javascript">	

    $(function() {
        if('${skilledunskilled}'!=null  && '${skilledunskilled}'!=''){
       	 $("#customFields").focus();
       	$("#customFields").css("border", "0");
       	 
        }	      
        });
    function proposedEmploymentDetailsValidationForm()
    {
		 <c:if test="${empty skilledUnSkilledEmployemntList}">
		      alert("Please first save the Employment Details.");
		      return false
		</c:if>	

if (confirm("Are you sure, You want to save Proposed Employment Details record."))
  {
	 document.getElementById("skilledUnskilledId").disabled = false;
     $("input[name='numberofMaleEmployees']").attr("readonly", false);
	 $("input[name='numberOfFemaleEmployees']").attr("readonly", false);
	 $("input[name='numberOfGeneral']").attr("readonly", false);
	 $("input[name='numberOfSc']").attr("readonly", false);
	 $("input[name='numberOfSt']").attr("readonly", false);
	 $("input[name='numberOfObc']").attr("readonly", false);
	 $("input[name='numberOfBpl']").attr("readonly", false);
	 $("input[name='numberOfDivyang']").attr("readonly", false); 
	 document.getElementById("addButtonId").disabled = false;    			
	 $("#editButton").removeClass("disable_a_href");
	 $("#deleteButton").removeClass("disable_a_href");
    return true;
    }
  else
	{
	 return false;
	  }		  	
	 }
function SkilledUnSkilledEmployemntFormValidation() {
  		  
		  
		  var skilledUnskilledId=document.getElementById("skilledUnskilledId").value;
		  if (skilledUnskilledId == null || skilledUnskilledId == '') {
		    document.getElementById('SkilledUnskilledId').innerHTML = "Skilled/Unskilled is Required";
			document.getElementById('skilledUnskilledId').focus();			
			return false;
			}	
		  
		  var numberofMaleEmployeesId=document.getElementById("numberofMaleEmployeesId").value;
		  if (numberofMaleEmployeesId == null || numberofMaleEmployeesId == '') {
		    document.getElementById('NumberofMaleEmployeesId').innerHTML = "Number of Male Employees is Required";
			document.getElementById('numberofMaleEmployeesId').focus();			
			return false;
			}	
		  var numberOfFemaleEmployeesId=document.getElementById("numberOfFemaleEmployeesId").value;
		  if (numberOfFemaleEmployeesId == null || numberOfFemaleEmployeesId == '') {
		    document.getElementById('NumberOfFemaleEmployeesId').innerHTML = "Number of Female Employees is Required";
			document.getElementById('numberOfFemaleEmployeesId').focus();			
			return false;
			}	
		  var numberOfGeneralId=document.getElementById("numberOfGeneralId").value;
		  if (numberOfGeneralId == null || numberOfGeneralId == '') {
		    document.getElementById('NumberOfGeneralId').innerHTML = "Number of General Employees is Required";
			document.getElementById('numberOfGeneralId').focus();			
			return false;
			}	
		  var numberOfScId=document.getElementById("numberOfScId").value;
		  if (numberOfScId == null || numberOfScId == '') {
		    document.getElementById('NumberOfScId').innerHTML = "Number of SC Employees is Required";
			document.getElementById('numberOfScId').focus();			
			return false;
			}	
		  var numberOfStId=document.getElementById("numberOfStId").value;
		  if (numberOfStId == null || numberOfStId == '') {
		    document.getElementById('NumberOfStId').innerHTML = "Number of ST Employees is Required";
			document.getElementById('numberOfStId').focus();			
			return false;
			}	
		 
		  
		  var numberOfBplId=document.getElementById("numberOfBplId").value;
		  if (numberOfBplId == null || numberOfBplId == '') {
		    document.getElementById('NumberOfBplId').innerHTML = "Number of BPL Employees is Required";
			document.getElementById('numberOfBplId').focus();			
			return false;
			}
		  
		  var numberOfDivyang=document.getElementById("numberOfDivyang").value;
		  if (numberOfDivyang == null || numberOfDivyang == '') {
		    document.getElementById('NumberOfDivyangId').innerHTML = "Number of Divyang Employees is Required";
			document.getElementById('numberOfDivyang').focus();			
			return false;
			}
		  var numberOfObcId=document.getElementById("numberOfObcId").value;
		  var totalskilledMaleFemale=parseInt(numberofMaleEmployeesId)+parseInt(numberOfFemaleEmployeesId);		
		  var totalSkilledEmployee=parseInt(numberOfGeneralId)+parseInt(numberOfScId)+parseInt(numberOfStId);
		  var totalSkilledEmployees=parseInt(numberOfGeneralId)+parseInt(numberOfScId)+parseInt(numberOfStId)+parseInt(numberOfObcId);
		 if(numberOfObcId==null || numberOfObcId==''){
		 if(totalSkilledEmployee>totalskilledMaleFemale ||totalSkilledEmployee<totalskilledMaleFemale)
			 {
			   alert('Total count of General,SC and ST should not be greater than or less than total of Male and Female Employees.');
			   return false;
			 }
		   }
		   else{
		   if(totalSkilledEmployees>totalskilledMaleFemale ||totalSkilledEmployees<totalskilledMaleFemale)
			 {
			   alert('Total count of General,SC,ST and OBC should not be greater than or less than total of Male and Female Employees.');
			   return false;
			 }
			}
			if(totalskilledMaleFemale<parseInt(numberOfBplId))
			{
			   alert('Total count of BPL Employees should not be greater than total of Male and Female Employees.');
			   return false;
			}
			
			if(totalskilledMaleFemale<parseInt(numberOfDivyang))
			{
			   alert('Total count of Divyang Employees should not be greater than total of Male and Female Employees.');
			   return false;
			}    
			 document.getElementById("skilledUnskilledId").disabled = false;
		     $("input[name='numberofMaleEmployees']").attr("readonly", false);
			 $("input[name='numberOfFemaleEmployees']").attr("readonly", false);
			 $("input[name='numberOfGeneral']").attr("readonly", false);
			 $("input[name='numberOfSc']").attr("readonly", false);
			 $("input[name='numberOfSt']").attr("readonly", false);
			 $("input[name='numberOfObc']").attr("readonly", false);
			 $("input[name='numberOfBpl']").attr("readonly", false);
			 $("input[name='numberOfDivyang']").attr("readonly", false); 
			 document.getElementById("addButtonId").disabled = false;    			
			 $("#editButton").removeClass("disable_a_href");
			 $("#deleteButton").removeClass("disable_a_href");
	 }
window.onload = function() {

	 if('${propoEmplDetailsId}'!=null && '${propoEmplDetailsId}'!='')
	 {		
		     document.getElementById("skilledUnskilledId").disabled = true; 
		     $("input[name='numberofMaleEmployees']").attr("readonly", true);
			 $("input[name='numberOfFemaleEmployees']").attr("readonly", true);
			 $("input[name='numberOfGeneral']").attr("readonly", true);
			 $("input[name='numberOfSc']").attr("readonly", true);
			 $("input[name='numberOfSt']").attr("readonly", true);
			 $("input[name='numberOfObc']").attr("readonly", true);
			 $("input[name='numberOfBpl']").attr("readonly", true);
			 $("input[name='numberOfDivyang']").attr("readonly", true);
		     document.getElementById("addButtonId").disabled = true;		        			
			 $("#editButton").addClass("disable_a_href");
			 $("#deleteButton").addClass("disable_a_href");
      }
   else
	 {
	     document.getElementById("skilledUnskilledId").disabled = false;
	     $("input[name='numberofMaleEmployees']").attr("readonly", false);
		 $("input[name='numberOfFemaleEmployees']").attr("readonly", false);
		 $("input[name='numberOfGeneral']").attr("readonly", false);
		 $("input[name='numberOfSc']").attr("readonly", false);
		 $("input[name='numberOfSt']").attr("readonly", false);
		 $("input[name='numberOfObc']").attr("readonly", false);
		 $("input[name='numberOfBpl']").attr("readonly", false);
		 $("input[name='numberOfDivyang']").attr("readonly", false); 
		 document.getElementById("addButtonId").disabled = false;    			
		 $("#editButton").removeClass("disable_a_href");
		 $("#deleteButton").removeClass("disable_a_href");	
	 }
 };

function editPorpoEmploDetails() {

	var r = confirm('Are you Sure,Want to Edit the Proposed Employment Details?');

	if (r == true) {
		 document.getElementById("skilledUnskilledId").disabled = false;
	     $("input[name='numberofMaleEmployees']").attr("readonly", false);
		 $("input[name='numberOfFemaleEmployees']").attr("readonly", false);
		 $("input[name='numberOfGeneral']").attr("readonly", false);
		 $("input[name='numberOfSc']").attr("readonly", false);
		 $("input[name='numberOfSt']").attr("readonly", false);
		 $("input[name='numberOfObc']").attr("readonly", false);
		 $("input[name='numberOfBpl']").attr("readonly", false);
		 $("input[name='numberOfDivyang']").attr("readonly", false); 
		 document.getElementById("addButtonId").disabled = false;    			
		 $("#editButton").removeClass("disable_a_href");
		 $("#deleteButton").removeClass("disable_a_href");
		 document.getElementById('skilledUnskilledId').focus();
	  	
	} else {
		return false
	}
}	
</script>
  <script type="text/javascript">

  $(document).ready(function(){
	  $(".myclass").on("change", function(){
		    var numberofMaleEmployeesId = parseInt(document.getElementById('numberofMaleEmployeesId').value);
		    if(numberofMaleEmployeesId==null || numberofMaleEmployeesId=='')
			    {
		    	numberofMaleEmployeesId=0;
			    }
		    var numberOfFemaleEmployeesId = document.getElementById('numberOfFemaleEmployeesId').value;
		    if(numberOfFemaleEmployeesId==null || numberOfFemaleEmployeesId=='')
		    {
		    	numberOfFemaleEmployeesId=0;
		    }
		    var totalmafeemploye = parseInt(numberofMaleEmployeesId) + parseInt(numberOfFemaleEmployeesId);
		    
		    var numberOfGeneralId = document.getElementById('numberOfGeneralId').value;
		    if(numberOfGeneralId==null || numberOfGeneralId=='')
		    {
		    	numberOfGeneralId=0;
		    }
		    var numberOfScId = document.getElementById('numberOfScId').value;
		    if(numberOfScId==null || numberOfScId=='')
		    {
		    	numberOfScId=0;
		    }
		    var numberOfStId = document.getElementById('numberOfStId').value;
		    if(numberOfStId==null || numberOfStId=='')
		    {
		    	numberOfStId=0;
		    }
		    var numberOfObcId = document.getElementById('numberOfObcId').value;
		    if(numberOfObcId==null || numberOfObcId=='')
		    {
		    	numberOfObcId=0;
		    }
		    var totalSkilledEmployee1=parseInt(numberOfGeneralId)+parseInt(numberOfScId)+parseInt(numberOfStId);
			  var totalSkilledEmployees1=parseInt(numberOfGeneralId)+parseInt(numberOfScId)+parseInt(numberOfStId)+parseInt(numberOfObcId);
		    if(numberOfObcId==null || numberOfObcId==''){
				 if(totalSkilledEmployee1>totalmafeemploye)
					 {
					   alert('Total count of General,SC and ST should not be greater than total of Male and Female Employees.');
					   return false;
					 }
				   }
				   else{
				   if(totalSkilledEmployees1>totalmafeemploye)
					 {
					   alert('Total count of General,SC,ST and OBC should not be greater than total of Male and Female Employees.');
					   return false;
					 }
					}
		});
  })


  </script>     
    </head>
<%@ include file="header.jsp" %>

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
              <div class="without-wizard-steps">
                <div class="step-links">
                  <ul>
                    <li><a href="./getIdByTabs44?authoTab=authoTab" class="filled"><span>1</span> Authorized Signatory Details</a></li>
                    <li><a href="./getIdByTabs45?busiTab=busiTab" class="filled"><span>2</span> Business Entity Details</a></li>
                    <li><a href="./getIdByTabs46?projTab=projTab" class="filled"><span>3</span> Project Details</a></li>
                    <li><a href="./getIdByTabs47?investTab=investTab" class="filled"><span>4</span> Investment Details</a></li>
                    <li><a href="#" class="active"><span>5</span> Proposed Employment Details</a></li>
                  </ul>
                </div>
                  <div class="isf-form">
                    
                  <form:form modelAttribute="skilledUnSkilledEmployemnt" name="SkilledUnSkilledEmployemntForm" onsubmit="return SkilledUnSkilledEmployemntFormValidation()" 
                    action="addSkilUnSkilEmploy" method="post">  
                      <form:hidden path="id" value="${id}" />
                     
                    <div class="row">
                      <div class="col-sm-12 mt-4">
                          <h3 class="common-heading">Employment Details</h3>
                        </div>
                      </div>                         
                      <div class="row">
                      <c:if test="${not empty message}">
                      <font color="red"> ${message}</font>
                      </c:if>
                      </div>
                      <br>
                     <div class="row">        
                        
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <form:label path="skilledUnskilled">Skilled/Unskilled <span>*</span></form:label>
                            <form:select class="form-control" path="skilledUnskilled" id="skilledUnskilledId" onchange="skilledUnskilledvalidation(event)">
                              <form:option value="">Select One</form:option>
                              <form:option value="Skilled">Skilled</form:option>
                              <form:option value="Unskilled">Unskilled</form:option>                              
                            </form:select>
                            <span id="SkilledUnskilledId" class="text-danger color:red"></span>
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                                          <form:label path="numberofMaleEmployees">Number of Male Employees<span>*</span></form:label>
                                          <form:input path="numberofMaleEmployees"  class="form-control" id="numberofMaleEmployeesId"
                                           onkeypress="return IsNumeric0(event)" maxlength="12"></form:input>                                          
                                         </div>
                        <span id="NumberofMaleEmployeesId" class="text-danger color:red"></span>
                        </div>
                        
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                                          <form:label path="numberOfFemaleEmployees">Number of Female Employees<span>*</span></form:label>
                                          <form:input path="numberOfFemaleEmployees"  class="form-control" id="numberOfFemaleEmployeesId" 
                                          onkeypress="return IsNumeric1(event)" maxlength="12"></form:input>                                          
                                        </div>
                                      <span id="NumberOfFemaleEmployeesId" class="text-danger color:red"></span>       
                        </div>
                       </div>
                       
                       

                      <div class="row">                       
                        
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                                          <form:label path="numberOfGeneral">Number of General Employees<span>*</span></form:label>
                                          <form:input path="numberOfGeneral"  class="form-control myclass" id="numberOfGeneralId" 
                                          onkeypress="return IsNumeric2(event)" maxlength="12"></form:input>
                                          <span id="NumberOfGeneralId" class="text-danger color:red"></span>
                                        </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                                                    <div class="form-group">
                                          <form:label path="numberOfSc">Number of SC Employees<span>*</span></form:label>
                                         <form:input path="numberOfSc" class="form-control myclass" id="numberOfScId" 
                                         onkeypress="return IsNumeric3(event)" maxlength="12"></form:input>
                                         <span id="NumberOfScId" class="text-danger color:red"></span>
                                        </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <form:label path="numberOfSt">Number of ST Employees<span>*</span></form:label>
                            <form:input path="numberOfSt" class="form-control myclass" id="numberOfStId" 
                            onkeypress="return IsNumeric4(event)" maxlength="12"></form:input>
                             <span id="NumberOfStId" class="text-danger color:red"></span>
                          </div>
                        </div>
                        
                      </div>

                      <div class="row">
                        
                        
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <form:label path="numberOfObc">Number of OBC Employees</form:label>
                            <form:input path="numberOfObc" class="form-control myclass" id="numberOfObcId" 
                            onkeypress="return IsNumeric5(event)" maxlength="12"></form:input>
                            <span id="NumberOfObcId" class="text-danger color:red"></span>
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <form:label path="numberOfBpl">Number of BPL Employees <span>*</span></form:label>
                            <form:input path="numberOfBpl" class="form-control myclass" id="numberOfBplId" 
                                          onkeypress="return IsNumeric6(event)" maxlength="12"></form:input>
                                          <span id="NumberOfBplId" class="text-danger color:red"></span>
                          </div>
                        </div>
                        
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <form:label path="numberOfDivyang">Number of Divyang Employees<span>*</span></form:label>
                            <form:input path="numberOfDivyang" class="form-control" id="numberOfDivyang" onkeypress="return IsNumeric7(event)" maxlength="12"></form:input>
                            <span id="NumberOfDivyangId" class="text-danger color:red"></span>
                          </div>
                        </div>
                      </div>
                      

                      <div class="row">
                        <div class="col-md-12">
                        <c:if test="${empty edit}">
                           <div class="form-group text-right mt-1">
                            <button type="submit" class="common-btn mt-4" id="addButtonId">Add</button>
                          </div>
                          </c:if>
                          <c:if test="${not empty edit}">
                           <div class="form-group text-right mt-1">
                            <button type="submit" class="common-btn mt-4">save</button>
                          </div>
                          </c:if>
                        </div>
                      </div>
                      </form:form>
                      
                      <hr class="mt-4">                   
                      <div class="row">
                        <div class="col-sm-12">
                          <div class="table-responsive mt-3">
                            <table class="table table-stripped table-bordered" id="customFields" tabindex='1'>
                            <c:if test="${ not empty skilledUnSkilledEmployemntList}">
                              <thead>
                                <tr>                                  
                                  <th>Skilled/Unskilled</th>
                                  <th>Male</th>
                                  <th>Female</th>
                                  <th>General</th>
                                  <th>SC</th>
                                  <th>ST</th>
                                  <th>OBC</th>
                                  <th>BPL</th>                                  
                                  <th>Divyang</th>
                                  <th>Action</th>
                                </tr>
                              </thead>
                             </c:if>
                              <tbody id="editButton">
                              <c:forEach var="list" items="${skilledUnSkilledEmployemntList}" varStatus="counter">
                                <tr >                                  
                                  <td>${list.skilledUnskilled}</td>
                                  <td>${list.numberofMaleEmployees}</td>
                                  <td>${list.numberOfFemaleEmployees}</td>
                                  <td>${list.numberOfGeneral}</td>
                                  <td>${list.numberOfSc}</td>
                                  <td>${list.numberOfSt}</td>
                                  <td>${list.numberOfObc}</td>                                  
                                  <td>${list.numberOfBpl}</td>
                                  <td>${list.numberOfDivyang}</td>
                                  <td class="text-center">
                                  <a href="./editProposedEmploymentDetails?editunskilledRecord=<c:out value="${counter.index}"/>"
                                    onclick="return confirm('Are you sure you want to edit Employment Record?')">
                                    <i class="fa fa-edit text-info"></i></a>
                                  
                                 <a href="./deleteProposedEmploymentDetails?deleteunskilledRecord=<c:out value="${counter.index}"/>"
                                              onclick="return confirm('Are you sure you want to delete Employment Record?')">
                                 <i class="fa fa-trash text-danger"></i></a></td>  </tr>
                                </c:forEach>                     
                              </tbody>
                            </table>
                          </div>
                        </div>
                      </div>
                       
                       
                      <c:if test="${not empty skilledDisplay}">
                      <div class="row">
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Skilled Male Employees</label>
                            <input type="text" class="form-control" value="${skilledEmploymentMale}" disabled="disabled" >
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Skilled Female Employees</label>
                            <input type="text" class="form-control" value="${skilledEmploymentFemale}" disabled="disabled" >                                       
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Total Skilled Employees</label>
                            <input type="text" class="form-control" value="${totalSkilledEmployment}" disabled="disabled" >
                          </div>
                        </div>
                      </div>
					</c:if>
					
					<c:if test="${not empty unSkilledDisplay}">
                      <div class="row">
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Unskilled Male Employees</label>
                            <input type="text" class="form-control" value= "${unSkilledEmploymentMale}" disabled="disabled">
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Unskilled Female Employees</label>
                            <input type="text" class="form-control" value= "${unSkilledEmploymentFemale}" disabled="disabled">
                          </div>
                        </div>
                        <div class="col-md-6 col-lg-4 col-xl-4">
                          <div class="form-group">
                            <label>Total Unskilled Employees</label>
                            <input type="text" class="form-control" value="${totalUnSkilledEmployment}" disabled="disabled">
                          </div>
                        </div>
                      </div>
                      </c:if>
                       
                       
                       <c:if test="${ not empty skilledUnSkilledEmployemntList}">
                       
                      <hr class="mt-4">

                      <div class="row">
                        <div class="col-sm-12 mt-4">
                          <h3 class="common-heading">Total Employees</h3>
                        </div>
                      </div>

                      <div class="row">
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>Total Male Employees</label>
                                          <input type="text" class="form-control" value="${grossTotalMaleEmployment}" disabled>
                                        </div>
                                      </div>
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label>Total Female Employees</label>
                                          <input type="text" class="form-control" value="${grossTotalFemaleEmployment}" disabled>
                                        </div>
                                        
                                      </div>
                                      <div class="col-md-6 col-lg-4 col-xl-4">
                                        <div class="form-group">
                                          <label class="total-emp">Total Employees: <c:out value="${grossTotalMaleandFemaleEmployment}"/></label>
                                        </div>
                                      </div>
                                    </div>
                        
                        </c:if>                        
                        
                      <form:form modelAttribute="proposedEmploymentDetails" name="proposedDetailsForm" 
                            onsubmit="return proposedEmploymentDetailsValidationForm()" action="proposedEmploymentDtails" method="post">
                     <form:hidden path="id" value="${id}" />
                      <hr class="mt-4 mb-4">
                      <div class="row">
                        <div class="col-sm-5">
                       <a href="./investmentDetails" onclick="return confirm('Are you sure you want to go to Investment Details?')" class="common-default-btn mt-3">Previous</a>
                        </div>
                        <div class="col-sm-7 text-right">
                               <c:if test="${not empty propoEmplDetailsId}">
								<button type="button" class="common-btn mt-3" onclick="editPorpoEmploDetails()"><i class="fa fa-edit"></i>Edit</button>
								</c:if>
                          <button type="submit" class="common-btn">Save & Next</button>
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
  </body>
</html>
<%@ include file = "footer.jsp" %>