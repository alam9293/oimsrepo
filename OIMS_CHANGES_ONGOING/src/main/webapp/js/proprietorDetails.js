	function IsDirectorName(inputtext)
	{
		var directorName = document.getElementById('directorNameId').value;
		if (directorName == null || directorName == '') {
			document.getElementById('DirectorName').innerHTML = "Director Name is Required";
			document.getElementById('directorNameId').focus();
			return false;
		}else {
	        document.getElementById('DirectorName').innerHTML = "";
	    } 
	}

	function Isdesignation(inputtext)
	{
		var designation = document.getElementById('designationId').value;
		if (designation == null || designation == '') {
			document.getElementById('Designation').innerHTML = "Designation is Required";
			document.getElementById('designationId').focus();
			return false;
		}else {
	        document.getElementById('Designation').innerHTML = "";
	    } 
	}


function Isequity(inputtext)
	{
	var equity = document.getElementById('equityId').value;

	if (equity == null || equity == '') {
		document.getElementById('Equity').innerHTML = "Equity is Required";
		document.getElementById('equityId').focus();
		return false;
	}
    
	if(isNaN(equity)){
		document.getElementById('Equity').innerHTML = "Only numeric are allowed.";
		document.getElementById('equityId').value = "";
	  	document.getElementById('equityId').focus();	
		return false;
	} 
	else{
	document.getElementById('Equity').innerHTML = "";
	  return true;
	}
	
/*	if (/^([1-9]([0-9])?|0)(\.[0-9]{1,2})?$/.test(equity)) {
			// value is ok, use it
		} 
		 else {
			document.getElementById('Equity').innerHTML = "Equity should be in Numeric Decimal Format. Example like:35.78";
			document.getElementById('equityId').focus();
			return false
		} 
*/	
	
}

	function IsmobileNumber(inputtext)
	{
		var mobileNumber = document.getElementById('mobileNoId').value;
		if (mobileNumber == null || mobileNumber == '') {
			document.getElementById('MobileNo').innerHTML = "Mobile Number is Required.";
			document.getElementById('mobileNoId').focus();
			return false;
		}else {
	        document.getElementById('MobileNo').innerHTML = "";
	    } 
	}
	
	function IsproprietorDetailsaddress(inputtext)
	{
		var ProprietorDetailsaddress = document.getElementById('proprietorDetailsaddressId').value;
		if (ProprietorDetailsaddress == null || ProprietorDetailsaddress == '') {
			document.getElementById('ProprietorDetailsAddress').innerHTML = "Address is Required.";
			document.getElementById('proprietorDetailsaddressId').focus();
			return false;
		}else {
	        document.getElementById('ProprietorDetailsAddress').innerHTML = "";
	    } 
	}

	function Isemail(inputtext)
	{
		var email = document.getElementById('email').value;
		if (email == null || email == '') {
			document.getElementById('Email2').innerHTML = "Email Id is Required.";
			document.getElementById('email').focus();
			return false;
		}else {
	        document.getElementById('Email2').innerHTML = "";
	    } 
	}

	function IsGender(inputtext)
	{	
		var radios = document.getElementsByName("gender");
	    var formValid = false;

	    var i = 0;
	    while (!formValid && i < radios.length) {
	        if (radios[i].checked) formValid = true;
	        i++;        
	    }
	    if (!formValid) {
		document.getElementById('Gender').innerHTML = "Please select Gender."; 
	    return false;
	    }else {
	        document.getElementById('Gender').innerHTML = "";
	    }
	}
	
		function IsGenderclick(inputtext)
	{	
		var radios = document.getElementsByName("gender");
	    var formValid = false;

	    var i = 0;
	    while (!formValid && i < radios.length) {
	        if (radios[i].checked) formValid = true;
	        i++;        
	    }
	    if (!formValid) {
		document.getElementById('Gender').innerHTML = "Please select Gender."; 
	    return false;
	    }else {
	        document.getElementById('Gender').innerHTML = "";
	    }
	}
	
	
	
	function Iscategory(inputtext)
	{
		 var category = document.getElementById("category").value;
	   		if (category == null || category == '') {
	   	   		document.getElementById('Category').innerHTML = "Please Select Category";
	   			document.businessEntityAddressForm.category.focus();
	   			return false;
	   		}else {
		        document.getElementById('Category').innerHTML = "";
		    } 
	}
	
	function Isdivyang(inputtext)
	{	
	var radios = document.getElementsByName("div_Status");
	    var formValid = false;

	    var i = 0;
	    while (!formValid && i < radios.length) {
	        if (radios[i].checked) formValid = true;
	        i++;        
	    }
	    if (!formValid) {
		document.getElementById('Div_Status').innerHTML = "Please select Divyang."; 
	    return false;
	    }else {
	         document.getElementById('Div_Status').innerHTML = "";
	    }
	}
	
		function Isdivyangclick(inputtext)
	{	
	var radios = document.getElementsByName("div_Status");
	    var formValid = false;

	    var i = 0;
	    while (!formValid && i < radios.length) {
	        if (radios[i].checked) formValid = true;
	        i++;        
	    }
	    if (!formValid) {
		document.getElementById('Div_Status').innerHTML = "Please select Divyang."; 
	    return false;
	    }else {
	         document.getElementById('Div_Status').innerHTML = "";
	    }
	}
	
	
	
	function IspanCardNo(inputtext)
	{
		 var panCardNo = document.getElementById('panCardNOId').value;
			if (panCardNo == null || panCardNo == '') {
				document.getElementById('panCardNO').innerHTML = "PAN Number is Required";
				document.getElementById('panCardNOId').focus();
				return false;
			}else {
		        document.getElementById('panCardNO').innerHTML = "";
		    } 
	}


	function IsdinIdCharacter(inputtext)
	{
		var dinId = document.getElementById('din').value;
		if (dinId == null || dinId == '') {
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('Din').innerHTML = "Only numeric are allowed.";
	       document.getElementById('din').focus();
			return false;
		}else {
	        document.getElementById('Din').innerHTML = "";
	    }
			

	   }    
		    
	}
	



// JavaScript for Equity

$(document).ready(function() {
$('#equityId').keyup(function(){
if ($(this).val() > 100){
document.getElementById('Equity').innerHTML = "No numbers above 100";
//alert("No numbers above 100");
$(this).val('100');
}else {
	        document.getElementById('Equity').innerHTML = "";
	    }
});

});

function validateFloatKeyPress(el, evt) {
var charCode = (evt.which) ? evt.which : event.keyCode;
var number = el.value.split('.');
if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
return false;
}
//just one dot
if(number.length>1 && charCode == 46){
return false;
}
//get the carat position
var caratPos = getSelectionStart(el);
var dotPos = el.value.indexOf(".");
if( caratPos > dotPos && dotPos>-1 && (number[1].length > 1)){
return false;
}
return true;
}

//thanks: http://javascript.nwbox.com/cursor_position/
function getSelectionStart(o) {
if (o.createTextRange) {
var r = document.selection.createRange().duplicate()
r.moveEnd('character', o.value.length)
if (r.text == '') return o.value.length
return o.value.lastIndexOf(r.text)
} else return o.selectionStart
}







