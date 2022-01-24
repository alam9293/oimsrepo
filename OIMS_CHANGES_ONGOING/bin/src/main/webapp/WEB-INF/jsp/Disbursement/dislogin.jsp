<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Loc Number</title>

    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <!-- Fonts -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <!-- <link rel="stylesheet" href="css/bootstrap-datepicker.min.css"> -->
    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript">

    function loginValidation()
    {
 	
  	var userNameId=document.getElementById("userNameId").value;		
  	if (userNameId == null || userNameId == '') {				
  	document.getElementById('UserNameId').innerHTML = "Please enter LOC Number";	
  	document.getElementById('userNameId').focus();		
  	return false;
  	}  
    else
  	{
  	  document.getElementById('UserNameId').innerHTML="";
  	}
    }

    </script>
  </head>
  <body>
    
	
  <section class="login-signup">
    <div class="container h-100">
      <div class="d-flex justify-content-center h-100">
        <div class="user_card">
          <div class="d-flex justify-content-center">
            <div class="brand_logo_container">
              <img src="images/logo-icon.png" class="brand_logo" alt="Logo">
            </div>
          </div>
          <div class="d-flex justify-content-center form_container">
          <form:form action="disloginapp" class="isf-form login-form loc-number-input" method="POST" modelAttribute="dislogin" onsubmit="return loginValidation()">
          <div>
          <c:if test="${not empty message}">
              <font color="red">${message}</font> 
           </c:if> </div>
            
              <div class="input-group mb-3 border-bottom">
                <h2 class="card-title pb-2 pt-4 m-auto">Letter of Comfort (LoC)</h2>
              </div>
              
              
            
              <div class="input-group mb-3">
             
           <form:select path="locNumber" class="form-control">
            <form:option value="-" label="-Select LOC Number-"/>
            <form:options items="${locList}"/>            
          </form:select>
              </div>
             
              <span id="UserNameId" class="text-danger color:red"></span>
              
              <div class="loc-number mt-4">
                <div class="d-flex justify-content-center mt-3">
                  <button type="submit" class="btn login_btn d-block">Submit</button>
                 
                </div>
              </div>
            
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </section>
 <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="js/datepicker.js"></script>
    <!-- <script src="js/bootstrap-datepicker.js"></script> -->
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