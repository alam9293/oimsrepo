<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Incentive Types</title>
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">

<script type="text/javascript">
	function Validate() {
		var checked = 0;

		//Reference the Table.
		var checkIncentive = document.getElementById("CheckIncentive");

		//Reference all the CheckBoxes in Table.
		var chks = checkIncentive.getElementsByTagName("INPUT");

		//Loop and count the number of checked CheckBoxes.
		for (var i = 0; i < chks.length; i++) {
			if (chks[i].checked) {
				checked++;
			}
		}

		if (checked > 0) {
			//	return true;
		} else {
			var msg = document.getElementById('msg').innerHTML = "Please Select Incentive Type";
			//	alert("Please select CheckBoxe(s).");
			return false;
		}
	};
</script>

</head>
<body>
	<jsp:include page="header.jsp" />
	<section class="common-form-area">
		<div class="container">
			<!-- Main Title -->
			<div class="inner-banner-text">
				<h1>Select incentive types under policy (IIEPP-2017)</h1>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="form-card  bdr-checkbox">
						<form:form modelAttribute="incentiveTypeForm"
							name="incentiveTypeForm" action="incentiveDetails" method="get"
							id="CheckIncentive">

							<div class="row">
								<div class="col-sm-6">
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											id="CapitalInterestSubsidy" name="CapitalInterestSubsidy"
											value="CapitalInterestSubsidy"> <label
											class="custom-control-label" for="CapitalInterestSubsidy">Interest
											Subsidies</label>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											id="StampDutyExemption" name="StampDutyExemption"
											value="StampDutyExemption"> <label
											class="custom-control-label" for="StampDutyExemption">Stamp
											Duty Exemption</label>
									</div>
								</div>

							</div>

							<div class="row">
								<div class="col-sm-6">
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											id="SGSTReimbursement" name="SGSTReimbursement"
											value="SGSTReimbursement"> <label
											class="custom-control-label" for="SGSTReimbursement">SGST
											Reimbursement</label>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											id="EPFReimbursement" name="EPFReimbursement"
											value="EPFReimbursement"> <label
											class="custom-control-label" for="EPFReimbursement">EPF
											Reimbursement</label>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-6">
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											id="OtherIncentive" name="OtherIncentive"
											value="OtherIncentive"> <label
											class="custom-control-label" for="OtherIncentive">Other Incentives</label>
									</div>
								</div>
							</div>
							
							<div class="row"></div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group text-center">
										<span id="msg" class="text-danger"></span>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
									<a href="./skilUnskEmplDet"
                                    onclick="return confirm('Are you sure you want to go Employment Details?')" class="common-default-btn mt-3">Back</a>	
										
									</div>
								</div>
								<div class="col-sm-8">
									<div class="form-group text-right">
										<form:button type="submit" class="common-btn"
											value="Check" onclick="return Validate()">Proceed to
											Incentive Form</form:button>
									</div>
								</div>
							</div>
              </</form:form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="footer.jsp" />
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<script src="js/custom.js"></script>
</body>
</html>