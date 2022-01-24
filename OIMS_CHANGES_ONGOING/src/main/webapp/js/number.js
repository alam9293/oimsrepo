//Convert digits into words with JavaScript

$(document).ready(function(){
var str = '';
var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

function inWords(num) {
   if ((num = num.toString()).length > 13) {
       return 'overflow';
   }
   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
   if (!n) {
      return str='';
   } 
   str = '';
   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
   return (str);
}
		  ////document.getElementById('invLandCost').onkeyup = function () {
		     // //document.getElementById('words').innerHTML = inWords(//document.getElementById('invLandCost').value);
		 // };
		 
		    var input = document.getElementById("invLandCost");
		    input.addEventListener("keyup", function () {
			   document.getElementById('words').innerHTML = inWords(document.getElementById('invLandCost').value);
			});
      
});

$(document).ready(function(){
var str = '';
var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

function inWords(num) {
   if ((num = num.toString()).length > 13) {
       return 'overflow';
   }
   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
   if (!n) {
      return str='';
   } 
   str = '';
   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
   return (str);
}

    // //document.getElementById('invBuildingCost').onkeyup = function () {
      ////document.getElementById('words1').innerHTML = inWords(//document.getElementById('invBuildingCost').value);
        var input = document.getElementById("invBuildingCost");
		    input.addEventListener("keyup", function () {
			   document.getElementById('words1').innerHTML = inWords(document.getElementById('invBuildingCost').value);
  });
});


$(document).ready(function(){
var str = '';
var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

function inWords(num) {
   if ((num = num.toString()).length > 13) {
       return 'overflow';
   }
   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
   if (!n) {
      return str='';
   } 
   str = '';
   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
   return (str);
}

      ////document.getElementById('invPlantAndMachCost').onkeyup = function () {
     // //document.getElementById('words2').innerHTML = inWords(//document.getElementById('invPlantAndMachCost').value);
  var input = document.getElementById("invPlantAndMachCost");
		    input.addEventListener("keyup", function () {
			   document.getElementById('words2').innerHTML = inWords(document.getElementById('invPlantAndMachCost').value);
  });
});

$(document).ready(function(){
var str = '';
var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

function inWords(num) {
   if ((num = num.toString()).length > 13) {
       return 'overflow';
   }
   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
   if (!n) {
      return str='';
   } 
   str = '';
   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
   return (str);
}
  		var input = document.getElementById("invOtherCost");
		    input.addEventListener("keyup", function () {
			   document.getElementById('words3').innerHTML = inWords(document.getElementById('invOtherCost').value);
  });
});



$(document).ready(function(){
	var str = '';
	var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
	var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

	function inWords(num) {
	   if ((num = num.toString()).length > 13) {
	       return 'overflow';
	   }
	   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
	   if (!n) {
	      return str='';
	   } 
	   str = '';
	   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
	   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
	   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
	   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
	   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
	   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
	   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
	   return (str);
	}
	  		var input = document.getElementById("invTotalProjCost");
			    input.addEventListener("keyup", function () {
				   document.getElementById('words4').innerHTML = inWords(document.getElementById('invTotalProjCost').value);
	  });
	});




$(document).ready(function(){
	var str = '';
	var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
	var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

	function inWords(num) {
	   if ((num = num.toString()).length > 13) {
	       return 'overflow';
	   }
	   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
	   if (!n) {
	      return str='';
	   } 
	   str = '';
	   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
	   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
	   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
	   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
	   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
	   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
	   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
	   return (str);
	}
	  		var input = document.getElementById("invFci");
			    input.addEventListener("keyup", function () {
				   document.getElementById('words5').innerHTML = inWords(document.getElementById('invFci').value);
	  });
	});


$(document).ready(function(){
	var str = '';
	var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
	var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

	function inWords(num) {
		
	   if ((num = num.toString()).length > 13) {
	       return 'overflow';
	   }
	   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
	   if (!n) {
	      return str='';
	   } 
	   str = '';
	   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
	   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
	   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
	   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
	   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
	   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
	   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
	   return (str);
	}
	  		var input = document.getElementById("pwFci");
			    input.addEventListener("keyup", function () {
				   document.getElementById('words21').innerHTML = inWords(document.getElementById('pwFci').value);
	  });
	});



$(document).ready(function(){
	var str = '';
	var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
	var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

	function inWords(num) {
	   if ((num = num.toString()).length > 13) {
	       return 'overflow';
	   }
	   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
	   if (!n) {
	      return str='';
	   } 
	   str = '';
	   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
	   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
	   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
	   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
	   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
	   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
	   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
	   return (str);
	}
	  		var input = document.getElementById("cAStatutoryAmt");
			    input.addEventListener("keyup", function () {
				   document.getElementById('cAStatutoryAmtWord').innerHTML = inWords(document.getElementById('cAStatutoryAmt').value);
	  });
	});


//Numeric to Word for Amount of SGST claimed for reimbursement

$(document).ready(function(){
	var str = '';
	var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
	var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

	function inWords(num) {
	   if ((num = num.toString()).length > 13) {
	       return 'overflow';
	   }
	   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
	   if (!n) {
	      return str='';
	   } 
	   str = '';
	   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
	   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
	   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
	   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
	   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
	   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
	   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
	   return (str);
	}
	  		var input = document.getElementById("ISF_Claim_Reim");
			    input.addEventListener("keyup", function () {
				document.getElementById('SGSTwords').innerHTML = inWords(document.getElementById('ISF_Claim_Reim').value);
	  });
	  
	  
	  var inputSCST = document.getElementById("Add_SCST_Textbox");
			    inputSCST.addEventListener("keyup", function () {
				document.getElementById('SCSTwords').innerHTML = inWords(document.getElementById('Add_SCST_Textbox').value);
	  });
	  
	  var inputfw = document.getElementById("Add_FW_Textbox");
			    inputfw.addEventListener("keyup", function () {
				document.getElementById('fwwords').innerHTML = inWords(document.getElementById('Add_FW_Textbox').value);
	  });
	  
	  
	   var inputbplw = document.getElementById("Add_BPLW_Textbox");
			    inputbplw.addEventListener("keyup", function () {
				document.getElementById('bplwwords').innerHTML = inWords(document.getElementById('Add_BPLW_Textbox').value);
	  });
	  
	 	var inputISF = document.getElementById("ISF_Stamp_Duty_EX");
			    inputISF.addEventListener("keyup", function () {
				document.getElementById('ISFTwords').innerHTML = inWords(document.getElementById('ISF_Stamp_Duty_EX').value);
	  });
	  
	  	  var inputduty = document.getElementById("ISF_Amt_Stamp_Duty_Reim");
			    inputduty.addEventListener("keyup", function () {
				document.getElementById('Reimwords').innerHTML = inWords(document.getElementById('ISF_Amt_Stamp_Duty_Reim').value);
	  });
	  	
	  
	    var inputex = document.getElementById("Add_Stamp_Duty_Textbox");
			    inputex.addEventListener("keyup", function () {
				document.getElementById('EXwords').innerHTML = inWords(document.getElementById('Add_Stamp_Duty_Textbox').value);
	  });
	  	
	  	
	  
	});
	
	
//Numeric to Word for ISF_Cis
	
$(document).ready(function(){
	var str = '';
	var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
	var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

	function inWords(num) {
	   if ((num = num.toString()).length > 13) {
	       return 'overflow';
	   }
	   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
	   if (!n) {
	      return str='';
	   } 
	   str = '';
	   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
	   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
	   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
	   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
	   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
	   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
	   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
	   return (str);
	}
	  		 var inputISFCIS = document.getElementById("ISF_Cis");
			    inputISFCIS.addEventListener("keyup", function () {
				document.getElementById('Subsidywords').innerHTML = inWords(document.getElementById('ISF_Cis').value);
	  		});
	  		
	  		
	  	  var inputbox= document.getElementById("Add_CIS_Textbox");
			    inputbox.addEventListener("keyup", function () {
				document.getElementById('textboxwords').innerHTML = inWords(document.getElementById('Add_CIS_Textbox').value);
	  });
	  
	   var inputint= document.getElementById("ISF_Infra_Int_Subsidy");
			    inputint.addEventListener("keyup", function () {
				document.getElementById('intwords').innerHTML = inWords(document.getElementById('ISF_Infra_Int_Subsidy').value);
	  });
	  
	  		
	  		 var inputiis= document.getElementById("Add_IIS_Textbox");
			    inputiis.addEventListener("keyup", function () {
				document.getElementById('iisTwords').innerHTML = inWords(document.getElementById('Add_IIS_Textbox').value);
	  });
	  		
	  		
	  		
	  		 var inputloan= document.getElementById("ISF_Loan_Subsidy");
			    inputloan.addEventListener("keyup", function () {
				document.getElementById('loanwords').innerHTML = inWords(document.getElementById('ISF_Loan_Subsidy').value);
	  });
	  		
	  		
	  		 var inputincen= document.getElementById("ISF_Tax_Credit_Reim");
			    inputincen.addEventListener("keyup", function () {
				document.getElementById('incentivewords').innerHTML = inWords(document.getElementById('ISF_Tax_Credit_Reim').value);
	  });
	  
	  
	   var inputpay= document.getElementById("ISF_Indus_Payroll_Asst");
			    inputpay.addEventListener("keyup", function () {
				document.getElementById('payrollwords').innerHTML = inWords(document.getElementById('ISF_Indus_Payroll_Asst').value);
	  });
	  
	
	  		
	  		
	});
	
	
// evalaution page field 

$(document).ready(function(){
	var str = '';
	var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
	var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

	function inWords(num) {
	   if ((num = num.toString()).length > 13) {
	       return 'overflow';
	   }
	   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
	   if (!n) {
	      return str='';
	   } 
	   str = '';
	   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
	   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
	   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
	   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
	   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
	   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
	   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
	   return (str);
	}

    var inputiiep= document.getElementById("buildingIIEPP");
			    inputiiep.addEventListener("keyup", function () {
				document.getElementById('iiepwords').innerHTML = inWords(document.getElementById('buildingIIEPP').value);
	  });
	  
	   var inputplant= document.getElementById("plantAndMachIIEPP");
			    inputplant.addEventListener("keyup", function () {
				document.getElementById('plantwords').innerHTML = inWords(document.getElementById('plantAndMachIIEPP').value);
	  });
	  
	 

});


$(document).ready(function(){
	var str = '';
	var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
	var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

	function inWords(num) {
	   if ((num = num.toString()).length > 13) {
	       return 'overflow';
	   }
	   n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
	   if (!n) {
	      return str='';
	   } 
	   str = '';
	   str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'kharab ' : '';
	   str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'arab ' : '';
	   str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'crore ' : '';
	   str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'lakh ' : '';
	   str += (n[5] != 0) ? (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'thousand ' : '';
	   str += (n[6] != 0) ? (a[Number(n[6])] || b[n[6][0]] + ' ' + a[n[6][1]]) + 'hundred ' : '';
	   str += (n[7] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[7])] || b[n[7][0]] + ' ' + a[n[7][1]]) + 'only ' : '';
	   return (str);
	}

   
	  
	   var inputplant= document.getElementById("plantAndMachIIEPP");
			    inputplant.addEventListener("keyup", function () {
				document.getElementById('plantwords').innerHTML = inWords(document.getElementById('plantAndMachIIEPP').value);
	  });
	  
	  var inputdpr= document.getElementById("fixedAssetIIEPP");
			    inputdpr.addEventListener("keyup", function () {
				document.getElementById('dprwords').innerHTML = inWords(document.getElementById('fixedAssetIIEPP').value);
	  });
	  
                
               var inputempf= document.getElementById("emfphsInvestAmt");
			    inputempf.addEventListener("keyup", function () {
				document.getElementById('empfwords').innerHTML = inWords(document.getElementById('emfphsInvestAmt').value);
	  });
	  
	  
	     
            
	  

});


