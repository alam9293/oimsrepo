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
  <title>OIMS MODIFY FORMS</title>
  
  
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
jQuery(document).ready(function($) {
    $("#departmentId").change(function (){
    	var departmentID   = $("#departmentId").val();
    	var formdropdownId = $("#formdropdownId").val();
    	var reqdata ={"departmentID":departmentID,"formdropdownId":formdropdownId};
    	
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "loadFormsName",
			data : JSON.stringify(reqdata),
			dataType : 'json',				
			success : function(data) {
				var resstatus = data.status;
				var Formslist = data.formNamelist;
				if(resstatus="Success")
				{
					if(document.getElementById("formdropdownId").selectedIndex==0)
					{
						document.getElementById("enbltbodyid").setAttribute("hidden", "hidden");
						document.getElementById("dsbltbodyid").setAttribute("hidden", "hidden");
						document.getElementById("modifytbodyid").setAttribute("hidden", "hidden");
					}
					else if(document.getElementById("formdropdownId").selectedIndex==1)
					{
						document.getElementById("dsbltbodyid").setAttribute("hidden", "hidden");
						document.getElementById("modifytbodyid").setAttribute("hidden", "hidden");
						document.getElementById("enbltbodyid").removeAttribute("hidden");
						var dynamichtml = "<select class='form-control' id='enblformId' onchange ='loadenblFormDesc()'>"+
						"<option path='id' value=''>--PLEASE SELECT FORM--</option>";
						for(var i = 0; i < Formslist.length; ++i) 
						{
							dynamichtml+="<option value='"+Formslist[i].id+"seprator"+Formslist[i].actionName+"seprator"+Formslist[i].displayName+"'>"+Formslist[i].displayName+"</option>";
						}
						dynamichtml+="</select>";
						$('#enblformdivId').html(dynamichtml);	
					}
					else if(document.getElementById("formdropdownId").selectedIndex==2)
					{
						document.getElementById("enbltbodyid").setAttribute("hidden", "hidden");
						document.getElementById("modifytbodyid").setAttribute("hidden", "hidden");
						document.getElementById("dsbltbodyid").removeAttribute("hidden");
						
						var dynamichtml = "<select class='form-control' name ='dsblformdata'  id='dsblformId' onchange ='loaddsblFormDesc()'>"+
						"<option path='id' value=''>--PLEASE SELECT FORM--</option>";
						for(var i = 0; i < Formslist.length; ++i) 
						{
							dynamichtml+="<option value='"+Formslist[i].id+"seprator"+Formslist[i].actionName+"seprator"+Formslist[i].displayName+"'>"+Formslist[i].displayName+"</option>";
						}
						dynamichtml+="</select>";
						$('#dsblformdivId').html(dynamichtml);	
						
					}
					else if(document.getElementById("formdropdownId").selectedIndex==3)
					{
						document.getElementById("enbltbodyid").setAttribute("hidden", "hidden");
						document.getElementById("dsbltbodyid").setAttribute("hidden", "hidden");
						document.getElementById("modifytbodyid").removeAttribute("hidden");
						
						var dynamichtml = "<select class='form-control' id='modifyformId' onchange ='loadmodifyFormDesc()'>"+
						"<option path='id' value=''>--PLEASE SELECT FORM--</option>";
						for(var i = 0; i < Formslist.length; ++i) 
						{
							dynamichtml+="<option value='"+Formslist[i].id+"seprator"+Formslist[i].actionName+"seprator"+Formslist[i].displayName+"'>"+Formslist[i].displayName+"</option>";
						}
						dynamichtml+="</select>";
						$('#modifyformdivId').html(dynamichtml);	
						
					}
				}
				
			},
			error : function(xhr,status,thrownError) {
				alert(xhr.status);
				alert(thrownError);
				
			}
    	    
		});
    	          	
	});
});
</script>
<script type="text/javascript">
function getformId()
{
	var dataarray = document.getElementById("modifyformId").value.split("seprator");
	const form = document.getElementById("formid");
	form.method = "post";
	form.action = "./updateForm?id="+dataarray[0];
	form.submit();
}
</script>
<script type="text/javascript">
function validateValues()
{
	var target = event.target
	if(target.id=="enblbtn")
	{
		if(document.getElementById("enblformId").selectedIndex==0)
		{
			document.getElementById("enblformspn").innerHTML ="PLEASE SELECT DEPARTMENT";
			return false;
		}
		else
		{
			document.getElementById("enblformspn").innerHTML ="";
		}
	}
	if(target.id=="dsblbtn")
	{
		if(document.getElementById("dsblformId").selectedIndex==0)
		{
			document.getElementById("dsblformspn").innerHTML ="PLEASE SELECT DEPARTMENT";
			return false;
		}
		else
		{
			document.getElementById("dsblformspn").innerHTML ="";
		}
	}
	if(target.id=="modifybtn")
	{
		if(document.getElementById("modifyformId").selectedIndex==0)
		{
			document.getElementById("modifyformspn").innerHTML ="PLEASE SELECT DEPARTMENT";
			return false;
		}
		else
		{
			document.getElementById("modifyformspn").innerHTML ="";
		}
	}
	
	return true;
}
</script>
<script>
function showMessage(Done,Msg,Action) {

	if(Done=="done")
	alert(Msg);
	
	if(Action=="M")
	{
		document.getElementById("enbltbodyid").setAttribute("hidden", "hidden");
		document.getElementById("dsbltbodyid").setAttribute("hidden", "hidden");
		document.getElementById("modifytbodyid").removeAttribute("hidden");
	}
}
</script>
<script type="text/javascript">
	function loadenblFormDesc()
	{
		var dataarray = document.getElementById("enblformId").value.split("seprator");
		var actionname = dataarray[1];
		var dsplname   = dataarray[2];
		
		if(document.getElementById("enblformId").selectedIndex==0)
		{
			document.getElementById("enblformspn").innerHTML ="PLEASE SELECT DEPARTMENT";
			document.getElementById("enblformactionNameid").value = "";
			document.getElementById("enblformdisplayNameid").value = "";
		}
		else
		{
			document.getElementById("enblformspn").innerHTML ="";
			document.getElementById("enblformactionNameid").value = actionname.replace("./", "");
			document.getElementById("enblformdisplayNameid").value = dsplname;
		}
	}
</script>
<script type="text/javascript">
	function loadmodifyFormDesc()
	{
		
		var dataarray = document.getElementById("modifyformId").value.split("seprator");
		var actionname = dataarray[1];
		var dsplname   = dataarray[2];
		
		if(document.getElementById("modifyformId").selectedIndex==0)
		{
			document.getElementById("modifyformspn").innerHTML ="PLEASE SELECT DEPARTMENT";
			document.getElementById("modifyformactionNameid").value = "";
			document.getElementById("modifyformdisplayNameid").value = "";
		}
		else
		{
			document.getElementById("modifyformspn").innerHTML ="";
			document.getElementById("modifyformactionNameid").value = actionname.replace("./", "");
			document.getElementById("modifyformdisplayNameid").value = dsplname;
		}
	}
</script>
<script type="text/javascript">
	function loaddsblFormDesc()
	{
		var dataarray = document.getElementById("dsblformId").value.split("seprator");
		var actionname = dataarray[1];
		var dsplname   = dataarray[2];
		
		if(document.getElementById("dsblformId").selectedIndex==0)
		{
			document.getElementById("dsblformspn").innerHTML ="PLEASE SELECT DEPARTMENT";
			return false;
		}
		else
		{
			document.getElementById("dsblformspn").innerHTML ="";
			document.getElementById("dsblformactionNameid").value = actionname.replace("./", "");
			document.getElementById("dsblformdisplayNameid").value = dsplname;
		}
	}
</script>
</head>
<body class="skin-blue sidebar-mini" onload="showMessage('${Done}','${Message}','${Action}')">
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
      <h1>MODIFY FORMS</h1>
      <ol class="breadcrumb">
      </ol>
    </div>
    <div class="content">
       <div class="card">
       <div class="card-body">
       <div align ="center">
						<table class="table table-bordered" id ="mainTableid">
							<tbody>
								<form:form modelAttribute ="enbldsblformmodel" id ="formid">
								   <tr>
										<td>ENABLE/DISABLE/MODIFY</td>
										<td>
										   <div class="input-group mb-3">
								                <select class="form-control" name ="enbldsbltype" id="formdropdownId" onchange ="loadselectedFormDetail()">
								                     <option  value="">Please Select</option>
								                     <option  value="E">Enable</option>
								                     <option  value="D">Disable</option>
								                     <option  value="E">Modify</option>
								                </select>
										    </div>
										    <span id="enbldsblmodifyformspn" class="text-danger color:red"></span>
										 </td>
									</tr>
									<tr>
								      <td>Department</td>
										<td>
										   <div class="input-group mb-3">
								                <form:select path="deptCode" class="form-control" id="departmentId">
								                  <form:option path="deptName" value="">--PLEASE SELECT DEPARTMENT--</form:option>
								                  <c:forEach var="enbldeptlist" items="${enblDepartmentlist}" varStatus="counter">
								                     <form:option path="deptCode" value="${enbldeptlist.deptId}">${enbldeptlist.deptName}</form:option>
								                  </c:forEach>
								                </form:select>
										    </div>
										    <span id="enbldepartmentspn" class="text-danger color:red"></span>
										 </td>
								     </tr>
									<tbody id ="enbltbodyid" hidden ="hidden">
									     <tr>
									      <td>Forms</td>
											<td>
											   <div class="input-group mb-3" id ="enblformdivId">
									                //loaded from ajax response
											    </div>
											    <span id="enblformspn" class="text-danger color:red"></span>
											 </td>
										  </tr>
										   <tr>
											    <td>Form Action Name</td>
											    <td><input type="text" class="form-control" name="enblformactionName" id="enblformactionNameid" readonly="readonly"></td>
										    </tr>
										    <tr>
											    <td>Form Display Name</td>
											    <td><input type="text" class="form-control" name="enblformdisplayName" id="enblformdisplayNameid" readonly="readonly"></td>
										    </tr>
										    <tr>
										</tr>
										 <tr>
											<td colspan=2>
											   <input type="submit" id ="enblbtn"  value ="Disable" formaction ="./dsblForm" onclick ="return validateValues()"/>
											</td>
										</tr>
									</tbody>
									
									<tbody id ="dsbltbodyid" hidden ="hidden">
									     <tr>
									      <td>Forms</td>
											<td>
											   <div class="input-group mb-3" id ="dsblformdivId">
									                //loaded from ajax response
											    </div>
											    <span id="dsblformspn" class="text-danger color:red"></span>
											 </td>
										  </tr>
										   <tr>
											    <td>Form Action Name</td>
											    <td><input type="text" class="form-control" name="dsblformactionName" id="dsblformactionNameid" readonly="readonly"></td>
										    </tr>
										    <tr>
											    <td>Form Display Name</td>
											    <td><input type="text" class="form-control" name="dsblformdisplayName" id="dsblformdisplayNameid" readonly="readonly"></td>
										    </tr>
										    <tr>
										</tr>
										 <tr>
											<td colspan=2>
											   <input type="submit" id ="dsblbtn"  value ="Enable" formaction ="./enblForm"  onclick ="return validateValues()"/>
											</td>
										</tr>
									</tbody>
									<tbody id ="modifytbodyid" hidden ="hidden">
									     <tr>
									      <td>Forms</td>
											<td>
											   <div class="input-group mb-3" id ="modifyformdivId">
									                //loaded from ajax response
											    </div>
											    <span id="modifyformspn" class="text-danger color:red"></span>
											 </td>
										  </tr>
										   <tr>
											    <td>Form Action Name</td>
											    <td><input type="text" class="form-control" name="modifyformactionName" id="modifyformactionNameid"></td>
										    </tr>
										    <tr>
											    <td>Form Display Name</td>
											    <td><input type="text" class="form-control" name="modifyformdisplayName" id="modifyformdisplayNameid"></td>
										    </tr>
										    
										 <tr>
											<td colspan=2>
											   <input type="submit" id ="modifybtn"  value ="Modify" formaction = "javascript:getformId(this);" onclick ="return validateValues()"/>
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