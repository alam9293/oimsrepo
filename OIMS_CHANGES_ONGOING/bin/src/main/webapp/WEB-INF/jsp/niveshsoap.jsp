<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Nivesh SOAP</title>

<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png">
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>


	<section class="login-signup">
		<div class="container h-100">
			<div class="d-flex justify-content-center h-100">
				<div class="user_card">
					<div class="d-flex justify-content-center">

						<form action="applicantDetails" class="isf-form" method="POST" style="width:90%;">
							<h4 class="mt-4">Nivesh SOAP</h4>
							<hr class="mb-3">
							<div class="form-group mb-3">
								<label>Control ID</label> <input type="text"
									class="form-control" name="TxtControlID">
							</div>
							<div class="form-group mb-3">
								<label>Unit ID</label> <input type="text" class="form-control"
									name="TxtUnitID">
							</div>
							<div class="form-group mb-2">
								<label>Service ID</label> <input type="text"
									class="form-control" name="TxtServiceID">
							</div>
							<div class="form-group mb-2">
								<label>ProcessIndustry ID</label> <input type="text"
									class="form-control" name="TxtProcessIndustryID">
							</div>
							<div class="form-group mb-2">
								<label>Passsalt</label> <input type="text" class="form-control"
									name="PassAlt">
							</div>
							<div class="form-group mb-2">
								<label>RequestID</label> <input type="text" class="form-control"
									name="RequestID">
							</div>

							<div class="form-group">
								<!-- <div class="d-flex justify-content-center mt-3">
									<a href="javascript:void();" name="button"
										class="btn login_btn d-block">Submit</a>
								</div> -->
								<button type="submit" class="common-btn mt-3">Submit</button>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
	</section>




	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
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