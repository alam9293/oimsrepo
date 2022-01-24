<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from uxliner.com/adminkit/demo/material/ltr/index.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 08 Dec 2021 07:35:25 GMT -->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>OIMS ROLE CREATION</title>
  
  
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="dist/bootstrap/css/bootstrap.min.css">
  <link rel="icon" type="" sizes="16x16" href="dist/img/favicon-16x16.png">
  <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" rel="stylesheet">
  <link rel="stylesheet" href="dist/css/style.css">
  <link rel="stylesheet" href="dist/css/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="dist/css/et-line-font/et-line-font.css">
  <link rel="stylesheet" href="dist/css/themify-icons/themify-icons.css">
  <link rel="stylesheet" href="dist/css/simple-lineicon/simple-line-icons.css">
  <link rel="stylesheet" href="css/style.css">
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script
	src="https://rawgit.com/jackmoore/autosize/master/dist/autosize.min.js"></script>
  <script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="js/number.js"></script>
  <script src="js/custom.js"></script>
  <script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script>
function showMessage(Done,Msg) {

	if(Done=="done")
	alert(Msg);
}
</script>
<script type="text/javascript">
function selectAllForm(key,listsize)
{
for (var i = 0; i < listsize; i++)
{
if(document.getElementById(key+'id'+i).checked)
  document.getElementById(key+'id'+i).checked = false;
else
document.getElementById(key+'id'+i).checked = true;
}
}
</script>
<script type="text/javascript">
function validateValues()
{
	
	if(document.getElementById("roleid").value==null||document.getElementById("roleid").value=="")
	{
		document.getElementById("rolespn").innerHTML ="PLEASE ENTER ROLE NAME";
		return false;
	}
	else
	{
		document.getElementById("rolespn").innerHTML ="";
	}
	if(document.getElementById("roleDescId").value==null||document.getElementById("roleDescId").value=="")
	{
		document.getElementById("roleDescspn").innerHTML ="PLEASE ENTER ROLE NAME";
		return false;
	}
	else
	{
		document.getElementById("roleDescspn").innerHTML ="";
	}
	
	if(document.getElementById("departmentId").selectedIndex==0)
	{
		document.getElementById("departmentIdspn").innerHTML ="PLEASE SELECT DEPARTMENT";
		return false;
	}
	else
	{
		document.getElementById("departmentIdspn").innerHTML ="";
	}	
	return true;
}
</script>

</head>
<body class="skin-blue sidebar-mini" onload="showMessage('${Done}','${Message}')">
<div class="wrapper boxed-wrapper">
  <header class="main-header"> 
    <!-- Logo --> 
    <a href="index.html" class="logo blue-bg"> 
    <!-- mini logo for sidebar mini 50x50 pixels --> 
    <span class="logo-mini"><img src="dist/img/logo-n.png" alt=""></span> 
    <!-- logo for regular state and mobile devices --> 
    <span class="logo-lg"><img src="dist/img/logo.png" alt=""></span> </a> 
    <!-- Header Navbar -->
    <nav class="navbar blue-bg navbar-static-top"> 
      <!-- Sidebar toggle button-->
      <ul class="nav navbar-nav pull-left">
        <li><a class="sidebar-toggle" data-toggle="push-menu" href="#"></a> </li>
      </ul>
      <div class="pull-left search-box">
        <form action="#" method="get" class="search-form">
          <div class="input-group">
            <input name="search" class="form-control" placeholder="" type="text">
            <span class="input-group-btn">
            <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i> </button>
            </span></div>
        </form>
        <!-- search form --> </div>
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <li class="dropdown user user-menu p-ph-res"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="dist/img/img1.jpg" class="user-image" alt="User Image"> <span class="hidden-xs">USER</span> </a>
            <ul class="dropdown-menu">
              <li class="user-header">
                <div class="pull-left user-img"><img src="dist/img/img1.jpg" class="img-responsive img-circle" alt="User"></div>
                <p class="text-left">${userName}<small>${userEmaiId}</small> </p>
              </li>
              <li><a href="#"><i class="icon-profile-male">USER ROLE</i> ${userr}</a></li>
              <li><a href="#"><i class="icon-profile-male">USER GROUP</i> ${userg}</a></li>
              <!-- <li><a href="#"><i class="icon-wallet"></i> My Balance</a></li>
              <li><a href="#"><i class="icon-envelope"></i> Inbox</a></li>
              <li role="separator" class="divider"></li>
              <li><a href="#"><i class="icon-gears"></i> Account Setting</a></li> -->
              <li role="separator" class="divider"></li>
              <li><a href="./userLogout2"><i class="fa fa-power-off"></i> Logout</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar"> 
    <!-- sidebar -->
    <div class="sidebar"> 
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="image text-center"><img src="dist/img/img1.jpg" class="img-circle" alt="User Image"> </div>
        <div class="info">
          <p>${userName}</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a> </div>
      </div>
      
      <!-- sidebar menu -->
      <ul class="sidebar-menu" data-widget="tree">
          <li class="treeview"> <a href="#"> <i class="icon-note"></i> <span>Forms</span> <span class="pull-right-container"> <i class="fa fa-angle-left pull-right"></i> </span> </a>
          <ul class="treeview-menu">
            <c:if test="${not empty Tblformactiondsplname}">
               <c:forEach var="Map" items="${Tblformactiondsplname}" varStatus="counter">
		            <li><a href="${Map.key}"><i class="fa fa-angle-right"></i>${Map.value}</a></li>
	            </c:forEach>
            </c:if>
          </ul>
        </li>
        <li class="treeview"> <a href="#"> <i class="fa fa-table"></i> <span>Tables</span> <span class="pull-right-container"> <i class="fa fa-angle-left pull-right"></i> </span> </a>
          <ul class="treeview-menu">
            <li><a href="#"><i class="fa fa-angle-right"></i> Basic Tables</a></li>
            <li><a href="#"><i class="fa fa-angle-right"></i> Table Layouts</a></li>
            <li><a href="#"><i class="fa fa-angle-right"></i> Data Tables</a></li>
            <li><a href="#"><i class="fa fa-angle-right"></i> Js Grid Table</a></li>
          </ul>
        </li>
      </ul>
    </div>
    <!-- /.sidebar --> 
  </aside>
  
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper"> 
    <!-- Content Header (Page header) -->
    <div class="content-header sty-one">
      <h1>ADD NEW ROLE</h1>
      <ol class="breadcrumb">
      </ol>
    </div>
    <div class="content">
       <div class="card">
       <div class="card-body">
       <div align ="center">
						<table class="table table-bordered">
							<tbody>
								<form:form modelAttribute ="userRolemodel" onSubmit ="return validateValues()">
									<tr>
										<td>Role</td>
										<td>
										   <input type="text" class="form-control" name="role" id="roleid" onchange="return validateValues()">
										   <span id="rolespn" class="text-danger color:red"></span>
										</td>
										</tr>
										<tr>
										   <td>Role Description</td>
										<td>
										    <input type="text" class="form-control" name="roleDesc" id="roleDescId" onchange="return validateValues()">
										    <span id="roleDescspn" class="text-danger color:red"></span>
										</td>
									</tr>
									<tr>
										<td>Department</td>
										<td>
										   <div class="input-group mb-3">
								                <form:select path="deptId" class="form-control" id="departmentId" onclick="return validateValues()">
								                  <c:forEach var="deptMap" items="${DepartmentMap}" varStatus="counter">
								                     <form:option path="deptId" value="${deptMap.key}">${deptMap.value}</form:option>
								                  </c:forEach>
								                </form:select>
										    </div>
										    <span id="departmentIdspn" class="text-danger color:red"></span>
										 </td>
									</tr>
									<tr>
									   <td>Allowed Forms</td>
									   <td>
										  <c:forEach var="map" items="${formNameDeptCodeMap}" varStatus="counter">
										     <div class="mb-3 parameter-properties" id= "${map.key}">
												 <c:forEach var="subMap" items="${map.value}" varStatus="counter2">
													<ul>
													   <c:if test="${counter2.index==0}">
													   <li>
													      <label><input type="checkbox" id= "${map.key}"  onchange ="selectAllForm('${map.key}','${fn:length(map.value)}')"/> select All</label>
													   </li>
													   </c:if>
													   <li>
													      <label><input type="checkbox" id= "${map.key}id${counter2.index}" name="selectFormsIds" value ="${map.key}id${subMap.key}"/>${subMap.value}</label>
													   </li>
													 </ul>
												  </c:forEach>
						                       </div>
						                     </c:forEach>
						                </td>
									 </tr>
									 <tr>
										<td colspan=2>
										   <input type="submit"  value ="Add" formaction ="./AddNewRole"/>
										</td>
									</tr>
								</form:form>
							</tbody>
					</table>
				</div>
           </div>
           </div>
    </div>
</div>
  
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <div class="pull-right hidden-xs"></div>
    Copyright © 2021 OIMS. All rights reserved.</footer>
</div>
<!-- ./wrapper --> 

<!-- jQuery 3 --> 
<script src="dist/js/jquery.min.js"></script> 

<!-- v4.0.0-alpha.6 --> 
<script src="dist/bootstrap/js/bootstrap.min.js"></script> 

<!-- template --> 
<script src="dist/js/adminkit.js"></script> 

<!-- Morris JavaScript --> 
<script src="dist/plugins/raphael/raphael-min.js"></script> 
<script src="dist/plugins/morris/morris.js"></script> 
<script src="dist/plugins/functions/dashboard1.js"></script> 

<!-- Chart Peity JavaScript --> 
<script src="dist/plugins/peity/jquery.peity.min.js"></script> 
<script src="dist/plugins/functions/jquery.peity.init.js"></script>
<script>
  $(function(){
    $('ul li').click(function(){
        $('ul li.active').removeClass('active');
        $(this).addClass('active');
    });
});
  </script>
</body>
</html>