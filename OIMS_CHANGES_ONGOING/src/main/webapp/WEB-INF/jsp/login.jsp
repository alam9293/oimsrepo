<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Login</title>

    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <!-- Fonts -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    
<script type="text/javascript">
var code;
function createCaptcha() {  
  document.getElementById('captcha').innerHTML = "";
  var charsArray ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$%^&*";
  var lengthOtp = 6;
  var captcha = [];
  for (var i = 0; i < lengthOtp; i++) {    
    var index = Math.floor(Math.random() * charsArray.length + 1); 
    if (captcha.indexOf(charsArray[index]) == -1)
      captcha.push(charsArray[index]);
    else i--;
  }
  var canv = document.createElement("canvas");
  canv.id = "captcha";
  canv.width = 100;
  canv.height = 50;
  var ctx = canv.getContext("2d");
  ctx.font = "25px Georgia";
  ctx.strokeText(captcha.join(""), 0, 30); 
  code = captcha.join("");
  document.getElementById("captcha").appendChild(canv); 
}
function refresh_captcha()
{

	
        let refreshIcon = document.getElementById("refresh-icon")
        let refreshButton = document.getElementById("refresh-button")
        refreshButton.removeAttribute("class");
        refreshButton.disabled = true

        setTimeout(function () {

          refreshIcon.addEventListener("animationiteration", function () {
            refreshButton.setAttribute("class", "refresh-end")
            refreshButton.disabled = false
            refreshIcon.removeEventListener("animationiteration")
          });
        }, 100)
      
	 createCaptcha();
}

function loginValidation()
  {

	var departmentId=document.getElementById("departmentId").value;		
	if (departmentId == null || departmentId == '') {				
	document.getElementById('DepartmentId').innerHTML = "Relative Department is Required";	
	document.getElementById('departmentId').focus();		
	return false;
	}  
  else
	{
	  document.getElementById('DepartmentId').innerHTML="";
	}
	
	var userNameId=document.getElementById("userNameId").value;		
	if (userNameId == null || userNameId == '') {				
	document.getElementById('UserNameId').innerHTML = "User Name is Required";	
	document.getElementById('userNameId').focus();		
	return false;
	}  
  else
	{
	  document.getElementById('UserNameId').innerHTML="";
	}

	var passwordId=document.getElementById("passwordId").value;		
	if (passwordId == null || passwordId == '') {				
	document.getElementById('PasswordId').innerHTML = "Password is Required";	
	document.getElementById('passwordId').focus();		
	return false;
	}  
  else
	{
	  document.getElementById('PasswordId').innerHTML="";
	}

	var captchaId=document.getElementById("captchaId").value;		
	if (captchaId == null || captchaId == '') {				
	document.getElementById('CaptchaId').innerHTML = "Captcha is Required";	
	document.getElementById('captchaId').focus();		
	return false;
	}  
  else
	{
	  document.getElementById('CaptchaId').innerHTML="";
	}	
  if (document.getElementById("captchaId").value!= code) {
	   alert("Please Enter Valid Captcha.");
	   createCaptcha();
       document.getElementById("captchaId").value="";
	   return false;
	  }  

  //sachin
  var roleId=document.getElementById("roleId").value;
  var departmentId=document.getElementById("departmentId").value;
  if(departmentId === 'ConcernedDepartments'){		
	if (roleId === null || roleId == '') {				
	document.getElementById('RoleId').innerHTML = "Relative role is Required";	
	document.getElementById('roleId').focus();		
	return false;
	}  
else
	{
	  document.getElementById('RoleId').innerHTML="";
	}
  }

 }    
</script>    
  </head>
  <body onload="createCaptcha()">
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
                    
            <spring:url value="/UserLogin"	var="loginActionUrl" />
			<form:form modelAttribute="login" method="post" action="${loginActionUrl}" onsubmit="return loginValidation()" class="login-form">
			<div><c:if test="${not empty message}">
              <font color="red">${message}</font> 
              </c:if> </div>
              <div class="input-group mb-3">
                <form:select path="department" class="form-control" id="departmentId">
                   <form:option value="">Select Department</form:option>
                   <form:option value="PICUP Processing Team">PICUP Processing Team</form:option>
                   <form:option value="JMD PICUP">JMD PICUP</form:option>
                   <form:option value="ConcernedDepartments">Concerned Departments IIEPP</form:option>
                   <form:option value="DIC">DIC</form:option>
                   <form:option value="MD PICUP">MD PICUP</form:option>
                   <form:option value="Head of Nodal Department IIEPP-2017">Head of Nodal Department IIEPP-2017</form:option>
                   <form:option value="Concerned JCI IIEPP-2017">Concerned JCI IIEPP-2017</form:option>
                   <form:option value="PSI">PSI(ACS-Industries)</form:option>
                   <form:option value="CS">IIDC</form:option>
                   <form:option value="ID6">SO-ID6</form:option>
                   
                 </form:select>
                 <span id="DepartmentId" class="text-danger color:red"></span>
              </div>
              
              <div class="mb-3 parameter-properties" id="ConcernedDepartments">
                <form:select path="role" class="form-control" id="roleId">
                   <form:option value="">Select Concerned Department</form:option>
                   <form:option value="ConDept0">Agriculture Department</form:option>
                   <%-- <form:option value="ConDept1">Board of Revenue, Uttar Pradesh</form:option> --%>
                   <form:option value="ConDept2">Commercial Tax Department</form:option>
                   <%-- <form:option value="ConDept3">Department of Excise</form:option> --%>
                   <form:option value="ConDept4">Department of Labour</form:option>
                   <form:option value="ConDept5">Department of Stamp and Registration</form:option>
                   <%-- <form:option value="ConDept6">Department of Weights and Measures : Legal Metrology</form:option>
                   <form:option value="ConDept7">Directorate of Electrical Safety</form:option>
                   <form:option value="ConDept8">Food Safety and Drug Administration (Drug)</form:option>
                   <form:option value="ConDept9">Forest and Wildlife Department</form:option>
                   <form:option value="ConDept10">Geology and Mining</form:option>
                   <form:option value="ConDept11">Housing Department</form:option>
                   <form:option value="ConDept12">Information Technology Department</form:option>
                   <form:option value="ConDept13">Infrastructure and Industrial Development Authority - GREATER NOIDA</form:option>
                   <form:option value="ConDept14">Infrastructure and Industrial Development Authority - NOIDA</form:option>
                   <form:option value="ConDept15">Infrastructure and Industrial Development Authority - UPSIDC</form:option>
                   <form:option value="ConDept16">Infrastructure and Industrial Development Authority - YEIDA</form:option>
                   <form:option value="ConDept17">Medical Health Department</form:option>
                   <form:option value="ConDept18">PICUP</form:option>
                   <form:option value="ConDept19">Pollution Control Board</form:option>
                   <form:option value="ConDept20">Public Works Department</form:option>
                   <form:option value="ConDept21">Registrar Firms Societies and Chits</form:option>
                   <form:option value="ConDept22">Tourism Department</form:option>
                   <form:option value="ConDept23">Urban Development Department</form:option>
                   <form:option value="ConDept24">Uttar Pradesh Fire Services</form:option> --%>
                   <form:option value="ConDept25">Uttar Pradesh Power Corporation Limited</form:option>
                 </form:select>
                  <span id="RoleId" class="text-danger color:red"></span>
              </div>
              
              <div class="input-group mb-3">
                <div class="input-group-append">
                  <span class="input-group-text"><i class="fa fa-user"></i></span>
                </div>
                <form:input path="userName" type="text" name="userName" class="form-control input_user" placeholder="username" maxlength="50" id="userNameId"></form:input>                
              </div>
              <span id="UserNameId" class="text-danger color:red"></span>
              <div class="input-group mb-2">
                <div class="input-group-append">
                  <span class="input-group-text"><i class="fa fa-key"></i></span>
                </div>
                <form:input path="password" type="password" name="password" class="form-control input_pass" placeholder="password" maxlength="15" id="passwordId"></form:input>                
              </div>
              <span id="PasswordId" class="text-danger color:red"></span>
              <div class="input-group mb-2 mt-3">
                <div class="row">
                  <div class="col-sm-6">
                    <form:input path="captcha" type="text" name="captcha" class="form-control input_pass" placeholder="Captcha" maxlength="10" id="captchaId"></form:input>                  
                  </div>
                  <div class="col-sm-6 refresh-captcha">
                  	<span id="captcha"></span>
                  	<a href="javascript:refresh_captcha()" id="refresh-button" class="refresh-captcha-btn refresh-end"><i style="font-size:24px" id="refresh-icon" class="fa refresh-start">&#xf021;</i></a>
                  </div>
                  <div class="col-sm-12">
                  	<span id="CaptchaId" class="text-danger color:red"></span>  
                  </div>
                  
                </div>
                
              </div>
              <div class="d-flex justify-content-center mt-3">
              <form:button type="submit" class="btn login_btn d-block">Login</form:button>                
              </div>
            </form:form>
          </div>
      
          <div class="mt-4">
            <div class="d-flex justify-content-center links">
              <a href="./forgetpassword"><small>Forgot your password?</small></a>
            </div>
            
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
    <script src="js/custom.js"></script>

  <script>
    $(document).ready(function() {
      $("[data-toggle=offcanvas]").click(function() {
        $(".row-offcanvas").toggleClass("active");
      });
    });

	//Animation Captcha Image
	$(document).ready(function() {
		function refreshContent() {
	
		
		
		}
	});

    
  </script>
  </body>
</html>