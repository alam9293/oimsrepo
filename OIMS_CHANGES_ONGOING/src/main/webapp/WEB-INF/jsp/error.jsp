<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

  <title>OIMS</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="dist/bootstrap/css/bootstrap.min.css">
  <!-- Favicon -->
  <link rel="icon" type="" sizes="16x16" href="dist/img/favicon-16x16.png">
  <!-- Google Font -->
  <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" rel="stylesheet">

  <link rel="stylesheet" href="dist/css/font-awesome/css/font-awesome.min.css">
<style>
    .page-wrap {
    min-height: 100vh;
}
.text-yellow {
    color: #f2740f !important;
}
.theme-error .btn-primary {
    background: #17a2b8;
    color: #fff;
    border-color: #17a2b8;
}
.theme-error{
    background-color: #d3d6dc;
}
.sorry-1{
font-size:5.5rem}
</style>

</head>
<body class="theme-error">
  
    <div class="page-wrap d-flex flex-row align-items-center">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 text-center">
                <h1 class="sorry-1"><b>ERROR</b>
                </h1>
                 <h1 class="h2 mb-3">An unexpected error has occurred.<br> Sorry
						for the inconvenience. </h1>
                    <span class="display-1 d-block"><i class="fa fa-warning text-yellow"></i></span>
                    
                    <div class="mb-4 lead"><p> 
                        Meanwhile, you may <a href="javascript:history.back()">return to Previous Page</a>  </p></div>
                    <a class="btn btn-primary" href="javascript:history.back()"><i class="fa fa-arrow-left mr-2"></i>Go back</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>