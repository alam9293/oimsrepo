<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="en">
<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
	<title>Policy Form</title>	
	<link rel="stylesheet"	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">	
	<link rel="stylesheet"	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/style.css">
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<script src="js/custom.js"></script>	
	<script type="text/javascript">	
	function radioValidation() {
		var policyName = document.getElementsByName('${selection_policy}');		
		var policyNameValue = false;
		for (var i = 0; i < policyName.length; i++) {
			if (policyName[i].checked == true) {
				policyNameValue = true;
			}
		}
		if (!policyNameValue) {
			var msg = document.getElementById('msg').innerHTML = "Please Select Policy";			
			return false;
		}
	}
  </script>
</head>
<body>
	<jsp:include page="header.jsp" />
	<section class="common-form-area">
		<div class="container">			
		  <div class="inner-banner-text">
			 <h1>Select policy </h1>
		  </div>
		  <div class="row">
			<div class="col-sm-12">
			 <div class="form-card  bdr-checkbox">
			  <form:form modelAttribute="selection_policy" name="policyForm" action="applicantDetails" method="post">     		
               <div class="row">                          
                <c:forEach var="policy" items="${selection_policy}">           
                 <div class="col-sm-6">
				  <div class="custom-control custom-radio">
					<input type="Radio" name="${selection_policy}" class="custom-control-input" id="${policy.policyId }" value="policy">
					<label class="custom-control-label" for="${policy.policyId }">${policy.policyName}</label>
				  </div>
				</div>
                </c:forEach>                                        
               </div>  
              <div class="row">
		      <div class="col-sm-12">
				<div class="form-group text-center">
				 <span id="msg" class="text-danger font-weight-bold"></span>
				</div>
				</div>
	     	</div>
			<div class="row">
			 <div class="col-sm-12">
			  <div class="form-group text-center">
				<button type="submit" class="common-btn" onclick="return radioValidation()">Proceed to Application Form</button>
				</div>
			 </div>
		  </div>
		 </</form:form >
		</div>
	  </div>
	</div>
   </div>
</section>
<jsp:include page="footer.jsp" />
</body>
</html>