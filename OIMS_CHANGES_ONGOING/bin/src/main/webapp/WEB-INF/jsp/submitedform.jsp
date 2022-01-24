<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Welcome</title>
<!-- Fonts -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- Popper JS -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="bottom-bg">
	<jsp:include page="header.jsp" />
	<section class="common-form-area">
		<div class="container">
			<!-- Main Title -->
			<div class="inner-banner-text">
				<h1>Application Form</h1>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="form-card">

						<div class="row">
							<section class="col-12">

								<form action="/selectPolicy" method="get">
									<div class="tab-content py-2">
										<div class="">
											<div class="tab-pane" role="tabpanel" id="complete">
												<h3 class="text-success">Complete!</h3>
												<p>You have successfully completed all steps.</p>
											</div>
											<hr class="mt-4 mb-4">

											<div class="row">
												<div class="col-sm-6">
													<button type="button" class="common-second-btn mt-3"
														onClick="window.location.href='/ims/';">Home</button>

												</div>
												<div class="col-sm-6 text-right">
													<a href="./cafpdf/IPMPSystemflow.pdf" class="common-btn mt-3" download="newfilename">Download pdf</a>
												</div>
											</div>

											<div class="clearfix"></div>
										</div>
									</div>
								</form>
							</section>
						</div>
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