
/* function financialvalidation()
  {
	  var financialYearId=document.getElementById("financialYearId").value;
	  if (financialYearId!= null || financialYearId!= '') {
	    document.getElementById('FinancialYearId').innerHTML = "";		
		}		
  } */

function skilledUnskilledvalidation()
{
	  var skilledUnskilledId=document.getElementById("skilledUnskilledId").value;
	  if (skilledUnskilledId!= null || skilledUnskilledId!= '') {
	    document.getElementById('SkilledUnskilledId').innerHTML = "";		
		}		
}

 function IsNumeric0(inputtext)
 {
	 
	    var numberofMaleEmployeesId=document.getElementById("numberofMaleEmployeesId").value;
	    if(numberofMaleEmployeesId!=null || numberofMaleEmployeesId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('NumberofMaleEmployeesId').innerHTML = "Please enter number.";
		   document.getElementById('numberofMaleEmployeesId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('NumberofMaleEmployeesId').innerHTML = "";
	    	}
    }   
 }
  
  function IsNumeric1(inputtext)
 {
	 
	    var numberOfFemaleEmployeesId=document.getElementById("numberOfFemaleEmployeesId").value;
	    if(numberOfFemaleEmployeesId!=null || numberOfFemaleEmployeesId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('NumberOfFemaleEmployeesId').innerHTML = "Please enter number.";
		   document.getElementById('numberOfFemaleEmployeesId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('NumberOfFemaleEmployeesId').innerHTML = "";
	    	}
    }   
 } 
 function IsNumeric2(inputtext)
 {
	 
	    var numberOfGeneralId=document.getElementById("numberOfGeneralId").value;
	    if(numberOfGeneralId!=null || numberOfGeneralId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('NumberOfGeneralId').innerHTML = "Please enter number.";
		   document.getElementById('numberOfGeneralId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('NumberOfGeneralId').innerHTML = "";
	    	}
    }   
 } 
 function IsNumeric3(inputtext)
 {
	 
	    var numberOfScId=document.getElementById("numberOfScId").value;
	    if(numberOfScId!=null || numberOfScId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('NumberOfScId').innerHTML = "Please enter number.";
		   document.getElementById('numberOfScId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('NumberOfScId').innerHTML = "";
	    	}
    }   
 } 
 function IsNumeric4(inputtext)
 {
	 
	    var numberOfStId=document.getElementById("numberOfStId").value;
	    if(numberOfStId!=null || numberOfStId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('NumberOfStId').innerHTML = "Please enter number.";
		   document.getElementById('numberOfStId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('NumberOfStId').innerHTML = "";
	    	}
    }   
 } 
 function IsNumeric5(inputtext)
 {
	 
	    var numberOfObcId=document.getElementById("numberOfObcId").value;
	    if(numberOfObcId!=null || numberOfObcId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('NumberOfObcId').innerHTML = "Please enter number";
		   document.getElementById('numberOfObcId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('NumberOfObcId').innerHTML = "";
	    	}
    }   
 } 
 
 function IsNumeric6(inputtext)
 {
	 
	    var numberOfBplId=document.getElementById("numberOfBplId").value;
	    if(numberOfBplId!=null || numberOfBplId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('NumberOfBplId').innerHTML = "Please enter number.";
		   document.getElementById('numberOfBplId').focus();			
		   return false;
	    }
	    else
    	{
    	document.getElementById('NumberOfBplId').innerHTML = "";
    	}
    }   
 } 
 
 function IsNumeric7(inputtext)
 {
	 
	    var numberOfDivyang=document.getElementById("numberOfDivyang").value;
	    if(numberOfDivyang !=null || numberOfDivyang==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('NumberOfDivyangId').innerHTML = "Please enter number.";
		   document.getElementById('numberOfDivyang').focus();			
		   return false;
	    }
		else
    	{
    	   document.getElementById('NumberOfDivyangId').innerHTML = "";
    	}
    }   
 }
 
 
 /*function IsNumeric01(inputtext)
 {
	 
	    var unNumberofMaleEmployeesId=document.getElementById("unNumberofMaleEmployeesId").value;
	    if(unNumberofMaleEmployeesId !=null || unNumberofMaleEmployeesId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('UnNumberofMaleEmployeesId').innerHTML = "Please enter number.";
		   document.getElementById('unNumberofMaleEmployeesId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('UnNumberofMaleEmployeesId').innerHTML = "";
	    	}
    }   
 }
  
  function IsNumeric02(inputtext)
 {
	 
	    var unNumberOfFemaleEmployeesId=document.getElementById("unNumberOfFemaleEmployeesId").value;
	    if(unNumberOfFemaleEmployeesId!=null || unNumberOfFemaleEmployeesId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('UnNumberOfFemaleEmployeesId').innerHTML = "Please enter number.";
		   document.getElementById('unNumberOfFemaleEmployeesId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('UnNumberOfFemaleEmployeesId').innerHTML="";
	    	}
    }   
 } 
 function IsNumeric03(inputtext)
 {
	 
	    var unNumberOfGeneralId=document.getElementById("unNumberOfGeneralId").value;
	    if(unNumberOfGeneralId!=null || unNumberOfGeneralId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('UnNumberOfGeneralId').innerHTML = "Please enter number.";
		   document.getElementById('unNumberOfGeneralId').focus();			
		   return false;
	    }
	    else
	    	{
	    	 document.getElementById('UnNumberOfGeneralId').innerHTML="";
	    	}
    }   
 } 
 function IsNumeric04(inputtext)
 {
	 
	    var unNumberOfScId=document.getElementById("unNumberOfScId").value;
	    if(unNumberOfScId !=null || unNumberOfScId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('UnNumberOfScId').innerHTML = "Please enter number.";
		   document.getElementById('numberOfScId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('UnNumberOfScId').innerHTML = "";
	    	}
    }   
 } 
 function IsNumeric05(inputtext)
 {
	 
	    var unNumberOfStId=document.getElementById("unNumberOfStId").value;
	    if(unNumberOfStId!=null || unNumberOfStId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('UnNumberOfStId').innerHTML = "Please enter number.";
		   document.getElementById('unNumberOfStId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('UnNumberOfStId').innerHTML = "";
	    	}
    }   
 } 
 function IsNumeric06(inputtext)
 {
	 
	    var unNumberOfObcId=document.getElementById("unNumberOfObcId").value;
	    if(unNumberOfObcId!=null || unNumberOfObcId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('UnNumberOfObcId').innerHTML = "Please enter number.";
		   document.getElementById('unNumberOfObcId').focus();			
		   return false;
	    }
	    else
	    	{
	    	document.getElementById('UnNumberOfObcId').innerHTML = "";
	    	}
    }   
 } 
 
 
 
 
 function IsNumeric07(inputtext)
 {
	 
	    var unNumberOfDivyangId=document.getElementById("unNumberOfDivyangId").value;
	    if(unNumberOfDivyangId !=null || unNumberOfDivyangId==''){
	    var regex = new RegExp("^[0-9]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       document.getElementById('UnNumberOfDivyangId').innerHTML = "Please enter number.";
		   document.getElementById('unNumberOfDivyangId').focus();			
		   return false;
	    }
    }   
 }
 
 
 function unSkilledEmployemntDetails(){
	 
	 var unFinancialYearId=document.getElementById("unFinancialYearId").value;
	  if (unFinancialYearId == null || unFinancialYearId == '') {
	    document.getElementById('UnFinancialYearId').innerHTML = "Financial Year is Required.";
		document.getElementById('unFinancialYearId').focus();			
		return false;
		} 
	 } 
 function clearFields()
 {
	 document.getElementById("numberofMaleEmployeesId").value="";
	 document.getElementById("numberOfFemaleEmployeesId").value="";
	 document.getElementById("numberOfGeneralId").value="";
	 document.getElementById("numberOfScId").value="";
	 document.getElementById("numberOfStId").value="";
	 document.getElementById("numberOfObcId").value="";
	 document.getElementById("numberOfDivyangId").value="";
	 document.getElementById("unNumberofMaleEmployeesId").value="";
	 document.getElementById("unNumberOfFemaleEmployeesId").value="";
	 document.getElementById("unNumberOfGeneralId").value="";
	 document.getElementById("unNumberOfScId").value="";
	 document.getElementById("unNumberOfStId").value="";
	 document.getElementById("unNumberOfObcId").value="";
	 document.getElementById("unNumberOfDivyangId").value="";
	 }*/