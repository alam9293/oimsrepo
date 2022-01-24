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
  <title>OIMS MODIFY USER</title>
  
  
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
function loadselectedUserDetail(){
	
	if(document.getElementById("userdropdownId").selectedIndex==0)
	{
		document.getElementById("enbltbodyid").setAttribute("hidden", "hidden");
		document.getElementById("dsbltbodyid").setAttribute("hidden", "hidden");
		document.getElementById("unlocktbodyid").setAttribute("hidden", "hidden");
		document.getElementById("dlttbodyid").setAttribute("hidden", "hidden");
	}
	else if(document.getElementById("userdropdownId").selectedIndex==1)
	{
		document.getElementById("dsbltbodyid").setAttribute("hidden", "hidden");
		document.getElementById("enbltbodyid").removeAttribute("hidden");
		document.getElementById("unlocktbodyid").setAttribute("hidden", "hidden");
		document.getElementById("dlttbodyid").setAttribute("hidden", "hidden");
	}
	else if(document.getElementById("userdropdownId").selectedIndex==2)
	{
		document.getElementById("enbltbodyid").setAttribute("hidden", "hidden");
		document.getElementById("dsbltbodyid").removeAttribute("hidden");
		document.getElementById("unlocktbodyid").setAttribute("hidden", "hidden");
		document.getElementById("dlttbodyid").setAttribute("hidden", "hidden");
		
	}
	else if(document.getElementById("userdropdownId").selectedIndex==3)
	{
		document.getElementById("unlocktbodyid").removeAttribute("hidden");
		document.getElementById("enbltbodyid").setAttribute("hidden", "hidden");
		document.getElementById("dsbltbodyid").setAttribute("hidden", "hidden");
		document.getElementById("dlttbodyid").setAttribute("hidden", "hidden");
		
	}
	else if(document.getElementById("userdropdownId").selectedIndex==4)
	{
		document.getElementById("dlttbodyid").removeAttribute("hidden");
		document.getElementById("enbltbodyid").setAttribute("hidden", "hidden");
		document.getElementById("dsbltbodyid").setAttribute("hidden", "hidden");
		document.getElementById("unlocktbodyid").setAttribute("hidden", "hidden");
	}
	
}
</script>
<script type="text/javascript">
function validateValues()
{
	var target = event.target
	if(target.id=="enblbtn")
	{
		if(document.getElementById("enbluserId").selectedIndex==0)
		{
			document.getElementById("enbluserspn").innerHTML ="PLEASE SELECT USER ID";
			return false;
		}
		else
		{
			document.getElementById("enbluserspn").innerHTML ="";
		}
	}
	if(target.id=="dsblbtn")
	{
		if(document.getElementById("dsbldepartmentId").selectedIndex==0)
		{
			document.getElementById("dsbldepartmentspn").innerHTML ="PLEASE SELECT DEPARTMENT";
			return false;
		}
		else
		{
			document.getElementById("dsbldepartmentspn").innerHTML ="";
		}
	}
	if(target.id=="dltbtn")
	{
		if(document.getElementById("dltdepartmentId").selectedIndex==0)
		{
			document.getElementById("dltdepartmentspn").innerHTML ="PLEASE SELECT DEPARTMENT";
			return false;
		}
		else
		{
			document.getElementById("dltdepartmentspn").innerHTML ="";
		}
	}
	if(target.id=="unlockbtn")
	{
		if(document.getElementById("unlockdepartmentId").selectedIndex==0)
		{
			document.getElementById("unlockdepartmentspn").innerHTML ="PLEASE SELECT DEPARTMENT";
			return false;
		}
		else
		{
			document.getElementById("unlockdepartmentspn").innerHTML ="";
		}
	}
	return true;
}
</script>
<script>
function showMessage(Done,Msg) {

	if(Done=="done")
	alert(Msg);
}
</script>
<script type="text/javascript">
	function loadenblUserDesc()
	{
		
		var dataarray = document.getElementById("enbluserId").value.split("seprator");
		var usrmailid = dataarray[1];
		var fname     = dataarray[2];
		var Mname     = dataarray[3];
		var Lname     = dataarray[4];
		var deptName  = dataarray[5];
		
		if(document.getElementById("enbluserId").selectedIndex==0)
		{
			document.getElementById("enbluserspn").innerHTML ="PLEASE SELECT USERID";
		}
		else
		{
			document.getElementById("enbluserspn").innerHTML ="";
			document.getElementById("enblusrmailid").value = dataarray[1];
			document.getElementById("enblusrfnameid").value = dataarray[2];
			document.getElementById("enblusrmdlnameid").value = dataarray[3];
			document.getElementById("enblusrlnameid").value = dataarray[4];
			document.getElementById("enblusrdeptnameid").value = dataarray[5];
		}
	}
</script>
<script type="text/javascript">
	function loaddsblUserDesc()
	{
		
		var dataarray = document.getElementById("dsbluserId").value.split("seprator");
		var usrmailid = dataarray[1];
		var fname     = dataarray[2];
		var Mname     = dataarray[3];
		var Lname     = dataarray[4];
		var deptName  = dataarray[5];
		
		if(document.getElementById("dsbluserId").selectedIndex==0)
		{
			document.getElementById("dsbluserspn").innerHTML ="PLEASE SELECT USERID";
		}
		else
		{
			document.getElementById("dsbluserspn").innerHTML ="";
			document.getElementById("dsblusrmailid").value = dataarray[1];
			document.getElementById("dsblusrfnameid").value = dataarray[2];
			document.getElementById("dsblusrmdlnameid").value = dataarray[3];
			document.getElementById("dsblusrlnameid").value = dataarray[4];
			document.getElementById("dsblusrdeptnameid").value = dataarray[5];
		}
	}
</script>
<script type="text/javascript">
	function loaddltUserDesc()
	{
		
		var dataarray = document.getElementById("dltuserId").value.split("seprator");
		var usrmailid = dataarray[1];
		var fname     = dataarray[2];
		var Mname     = dataarray[3];
		var Lname     = dataarray[4];
		var deptName  = dataarray[5];
		
		if(document.getElementById("dltuserId").selectedIndex==0)
		{
			document.getElementById("dltuserspn").innerHTML ="PLEASE SELECT USERID";
		}
		else
		{
			document.getElementById("dltuserspn").innerHTML ="";
			document.getElementById("dltusrmailid").value = dataarray[1];
			document.getElementById("dltusrfnameid").value = dataarray[2];
			document.getElementById("dltusrmdlnameid").value = dataarray[3];
			document.getElementById("dltusrlnameid").value = dataarray[4];
			document.getElementById("dltusrdeptnameid").value = dataarray[5];
		}
	}
</script>
<script type="text/javascript">
	function loadunlockUserDesc()
	{
		
		var dataarray = document.getElementById("unlockuserId").value.split("seprator");
		var usrmailid = dataarray[1];
		var fname     = dataarray[2];
		var Mname     = dataarray[3];
		var Lname     = dataarray[4];
		var deptName  = dataarray[5];
		
		if(document.getElementById("unlockuserId").selectedIndex==0)
		{
			document.getElementById("unlockuserspn").innerHTML ="PLEASE SELECT USERID";
		}
		else
		{
			document.getElementById("unlockuserspn").innerHTML ="";
			document.getElementById("unlockusrmailid").value = dataarray[1];
			document.getElementById("unlockusrfnameid").value = dataarray[2];
			document.getElementById("unlockusrmdlnameid").value = dataarray[3];
			document.getElementById("unlockusrlnameid").value = dataarray[4];
			document.getElementById("unlockusrdeptnameid").value = dataarray[5];
		}
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
                            <c:set var="dsplname" value="${fn:toUpperCase(Map.value)}" />
                           <c:choose>
	                           <c:when test = "${fn:contains(dsplname, 'DASHBOARD')}">
			                       <li class="nav-item animate__animated animate__bounce">
				                       <a class="nav-link active" href="${Map.key}">
				                         <i class="fa fa-tachometer"></i>${dsplname}
				                       </a>
			                       </li>
			                   </c:when> 
			                    <c:when test = "${fn:contains(dsplname, 'VIEW')}">
			                       <li class="nav-item">
				                       <a class="nav-link" href="${Map.key}">
				                         <i class="fa fa-eye"></i>${dsplname}
				                       </a>
			                       </li>
			                   </c:when> 
			                   <c:when test = "${fn:contains(dsplname, 'MEETING')}">
			                       <li class="nav-item">
				                       <a class="nav-link" href="${Map.key}">
				                         <i class="fa fa-calendar"></i>${dsplname}
				                       </a>
			                       </li>
			                   </c:when> 
			                   <c:otherwise>
						           <li class="nav-item animate__animated animate__bounce">
				                       <a class="nav-link active" href="${Map.key}">
				                         <i class="fa fa-tachometer"></i>${dsplname}
				                       </a>
			                       </li>
						       </c:otherwise>
		                   </c:choose>
	              </c:forEach>
            </c:if>
          </ul>
        </li>
        <!-- <li class="treeview"> <a href="#"> <i class="fa fa-table"></i> <span>Tables</span> <span class="pull-right-container"> <i class="fa fa-angle-left pull-right"></i> </span> </a>
          <ul class="treeview-menu">
            <li><a href="#"><i class="fa fa-angle-right"></i> Basic Tables</a></li>
            <li><a href="#"><i class="fa fa-angle-right"></i> Table Layouts</a></li>
            <li><a href="#"><i class="fa fa-angle-right"></i> Data Tables</a></li>
            <li><a href="#"><i class="fa fa-angle-right"></i> Js Grid Table</a></li>
          </ul>
        </li> -->
      </ul>
    </div>
    <!-- /.sidebar --> 
  </aside>
  
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper"> 
    <!-- Content Header (Page header) -->
    <div class="content-header sty-one">
      <h1>MODIFY USER</h1>
      <ol class="breadcrumb">
      </ol>
    </div>
    <div class="content">
       <div class="card">
       <div class="card-body">
       <div align ="center">
						<table class="table table-bordered" id ="mainTableid">
							<tbody>
								<form:form modelAttribute ="enbldsblusermodel">
								   <tr>
										<td>ACTIVITY</td>
										<td>
										   <div class="input-group mb-3">
								                <select class="form-control" name ="enbldsbltype" id="userdropdownId" onchange ="loadselectedUserDetail()">
								                     <option  value="">Please Select</option>
								                     <option  value="">Enable</option>
								                     <option  value="">Disable</option>
								                     <option  value="">Unlock</option>
								                     <option  value="">Terminate</option>
								                </select>
										    </div>
										    <span id="enbldsblmodifyuserspn" class="text-danger color:red"></span>
										 </td>
									</tr>
									<tbody id ="enbltbodyid" hidden ="hidden">
									     <tr>
									      <td>USER IDS</td>
											<td>
											   <div class="input-group mb-3">
									                <form:select path="id" class="form-control" id="enbluserId" onchange ="loadenblUserDesc()">
									                  <form:option path="userName" value="">--PLEASE SELECT USER EMAILID --</form:option>
									                  <c:forEach var="list" items="${enblUserlist}" varStatus="counter">
									                     <form:option path="userName" value="${list.id}seprator${list.userName}seprator${list.firstName}seprator${list.middleName}seprator${list.lastName}seprator${list.department}">${list.userName}</form:option>
									                  </c:forEach>
									                </form:select>
											    </div>
											    <span id="enbluserspn" class="text-danger color:red"></span>
											 </td>
										  </tr>
										   <tr>
											    <td>User EMAIL ID</td>
											    <td><input type="text" class="form-control" name="enblusrmail" id="enblusrmailid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User First Name</td>
											    <td><input type="text" class="form-control" name="enblusrfname" id="enblusrfnameid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User Middle Name</td>
											    <td><input type="text" class="form-control" name="enblusrmdlname" id="enblusrmdlnameid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User Last Name</td>
											    <td><input type="text" class="form-control" name="enblusrlname" id="enblusrlnameid" readonly="readonly"></td>
										    </tr>
										    <tr>
											    <td>Department</td>
											    <td><input type="text" class="form-control" name="enblusrdeptname" id="enblusrdeptnameid" readonly="readonly"></td>
										    </tr>
										 <tr>
											<td colspan=2>
											   <input type="submit" id ="enblbtn"  value ="Disable" formaction ="./dsblUser" onclick ="return validateValues()"/>
											</td>
										</tr>
									</tbody>
									
									<tbody id ="dsbltbodyid" hidden ="hidden">
									     <tr>
									      <td>USER IDS</td>
											<td>
											   <div class="input-group mb-3">
									                <form:select path="id" class="form-control" id="dsbluserId" onchange ="loaddsblUserDesc()">
									                  <form:option path="userName" value="">--PLEASE SELECT USER EMAILID --</form:option>
									                  <c:forEach var="list" items="${dsblUserlist}" varStatus="counter">
									                     <form:option path="userName" value="${list.id}seprator${list.userName}seprator${list.firstName}seprator${list.middleName}seprator${list.lastName}seprator${list.department}">${list.userName}</form:option>
									                  </c:forEach>
									                </form:select>
											    </div>
											    <span id="dsbluserspn" class="text-danger color:red"></span>
											 </td>
										  </tr>
										   <tr>
											    <td>User EMAIL ID</td>
											    <td><input type="text" class="form-control" name="dsblusrmail" id="dsblusrmailid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User First Name</td>
											    <td><input type="text" class="form-control" name="dsblusrfname" id="dsblusrfnameid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User Middle Name</td>
											    <td><input type="text" class="form-control" name="dsblusrmdlname" id="dsblusrmdlnameid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User Last Name</td>
											    <td><input type="text" class="form-control" name="dsblusrlname" id="dsblusrlnameid" readonly="readonly"></td>
										    </tr>
										    <tr>
											    <td>Department</td>
											    <td><input type="text" class="form-control" name="dsblusrdeptname" id="dsblusrdeptnameid" readonly="readonly"></td>
										    </tr>
										 <tr>
											<td colspan=2>
											   <input type="submit" id ="dsblbtn"  value ="Enable" formaction ="./enblUser"  onclick ="return validateValues()"/>
											</td>
										</tr>
									</tbody>
									<tbody id ="unlocktbodyid" hidden ="hidden">
									     <tr>
									      <td>USER IDS</td>
											<td>
											   <div class="input-group mb-3">
									                <form:select path="id" class="form-control" id="unlockuserId" onchange ="loadunlockUserDesc()">
									                  <form:option path="userName" value="">--PLEASE SELECT USER EMAILID --</form:option>
									                  <c:forEach var="list" items="${lockUserlist}" varStatus="counter">
									                     <form:option path="userName" value="${list.id}seprator${list.userName}seprator${list.firstName}seprator${list.middleName}seprator${list.lastName}seprator${list.department}">${list.userName}</form:option>
									                  </c:forEach>
									                </form:select>
											    </div>
											    <span id="unlockuserspn" class="text-danger color:red"></span>
											 </td>
										  </tr>
										   <tr>
											    <td>User EMAIL ID</td>
											    <td><input type="text" class="form-control" name="unlockusrmail" id="unlockusrmailid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User First Name</td>
											    <td><input type="text" class="form-control" name="unlockusrfname" id="unlockusrfnameid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User Middle Name</td>
											    <td><input type="text" class="form-control" name="unlockusrmdlname" id="unlockusrmdlnameid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User Last Name</td>
											    <td><input type="text" class="form-control" name="unlockusrlname" id="unlockusrlnameid" readonly="readonly"></td>
										    </tr>
										    <tr>
											    <td>Department</td>
											    <td><input type="text" class="form-control" name="unlockusrdeptname" id="unlockusrdeptnameid" readonly="readonly"></td>
										    </tr>
										 <tr>
											<td colspan=2>
											   <input type="submit" id ="unlockbtn"  value ="Release" formaction ="./UnlockUser"  onclick ="return validateValues()"/>
											</td>
										</tr>
									</tbody>
									<tbody id ="dlttbodyid" hidden ="hidden">
									     <tr>
									      <td>USER IDS</td>
											<td>
											   <div class="input-group mb-3">
									                <form:select path="id" class="form-control" id="dltuserId" onchange ="loaddltUserDesc()">
									                  <form:option path="userName" value="">--PLEASE SELECT USER EMAILID --</form:option>
									                  <c:forEach var="list" items="${dltUserlist}" varStatus="counter">
									                     <form:option path="userName" value="${list.id}seprator${list.userName}seprator${list.firstName}seprator${list.middleName}seprator${list.lastName}seprator${list.department}">${list.userName}</form:option>
									                  </c:forEach>
									                </form:select>
											    </div>
											    <span id="dltuserspn" class="text-danger color:red"></span>
											 </td>
										  </tr>
										   <tr>
											    <td>User EMAIL ID</td>
											    <td><input type="text" class="form-control" name="dltusrmail" id="dltusrmailid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User First Name</td>
											    <td><input type="text" class="form-control" name="dltusrfname" id="dltusrfnameid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User Middle Name</td>
											    <td><input type="text" class="form-control" name="dltusrmdlname" id="dltusrmdlnameid" readonly="readonly"></td>
										    </tr>
										     <tr>
											    <td>User Last Name</td>
											    <td><input type="text" class="form-control" name="dltusrlname" id="dltusrlnameid" readonly="readonly"></td>
										    </tr>
										    <tr>
											    <td>Department</td>
											    <td><input type="text" class="form-control" name="dltusrdeptname" id="dltusrdeptnameid" readonly="readonly"></td>
										    </tr>
										 <tr>
											<td colspan=2>
											   <input type="submit" id ="dltbtn"  value ="Terminate" formaction ="./TerminateUser"  onclick ="return validateValues()"/>
											</td>
										</tr>
									</tbody>
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