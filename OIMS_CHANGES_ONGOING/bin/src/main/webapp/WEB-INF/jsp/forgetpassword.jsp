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
    <title>Forgot Password</title>

    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <!-- Fonts -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript">
	/* function messagesend()
	{
		var m="${message1}";
		if (!m == "")
		{
			alert(m);
		}
		
	} */

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
          
          <form:form modelAttribute="login" method="post" action="sendpassword" onsubmit="" class="login-form">
          <div><c:if test="${not empty message}">
              <font color="red">${message}</font> 
              </c:if> </div>
              
            
            
              <div class="input-group mb-3">
                <select class="form-control" name="department">
                   <option value="0">Select Relative Depatment</option>
                   <option value="PICUP Processing Officer">PICUP Processing Officer</option>
                   <option value="JMD PICUP">JMD PICUP</option>
                   <option value="ConcernedDepartments">Concerned Departments IIEPP</option>
                   <option value="DIC">DIC</option>
                   <option value="MD PICUP">MD PICUP</option>
                   <option value="Head of Nodal Department IIEPP-2017">Head of Nodal Department IIEPP-2017</option>
                   <option value="Concerned JCI IIEPP-2017">Concerned JCI IIEPP-2017</option>
                   <option value="PSI">PSI(ACS-Industries)</option>
                   <option value="CS">IIDC</option>
                   <option value="ID6">SO-ID6</option>
                   
                 </select>
              </div>
              <div class="input-group mb-3">
                <div class="input-group-append">
                  <span class="input-group-text"><i class="fa fa-envelope"></i></span>
                </div>
                <input type="email" name="userName" class="form-control input_user" value="" placeholder="Enter Registered Email">
              </div>
              <div class="d-flex justify-content-center mt-3">
              <button type="submit" class="btn login_btn d-block">Submit</button>                
              </div>
              <div><c:if test="${not empty message1}">
              <font color="green">${message1}</font> 
              </c:if> </div>
            </form:form>
          </div>
      
          <div class="mt-4">
            <div class="d-flex justify-content-center links">
              <a href="./login"><small>Go Back to Login</small></a>
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
  </script>
  </body>
</html>