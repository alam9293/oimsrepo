// Code for wizard
 $(function() {
      $('.add-column').click(function(e) {
        e.preventDefault();
        $('#sTable72').find('thead tr').each(function(i,e) {
          $(e).prepend($('<td/>').html($('#componentn').val()));
        });
        $('#componentn').val("");
        
        $('#sTable72').find('tbody tr').each(function(i,e) {
            $(e).prepend($('<td/>').html($('#componentv').val()));
          });
        
        $('#componentv').val("");
      });
    });
 $(function() {
     $('.add-column-mom').click(function(e) {
       e.preventDefault();
       $('#sTable72mom').find('thead tr').each(function(i,e) {
         $(e).prepend($('<td/>').html($('#componentnmom').val()));
       });
       $('#componentnmom').val("");
       
       $('#sTable72mom').find('tbody tr').each(function(i,e) {
           $(e).prepend($('<td/>').html($('#componentvmom').val()));
         });
       
       $('#componentvmom').val("");
     });
   });
$(document).ready(function() {
    
    $(".wordcount").keyup(function(){
//        var msg = '<span>Characters left: ' + (1000 - $(this).val().length) + '</span>';
//        $(this).append(msg);
//        $("#count").text("Characters left: " + (1000 - $(this).val().length));
        $(this).next("span").html("Characters left: " + (1000 - $(this).val().length));
      });
    //Initialize tooltips
    $('.nav-tabs > li a[title]').tooltip();
    //Wizard
    $('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
        var $target = $(e.target);
        if ($target.parent().hasClass('disabled')) {
            return false;
        }
    });
    $(".next-step").click(function(e) {
        var $active = $('.nav-tabs li>.active');
        $active.parent().next().find('.nav-link').removeClass('disabled');
        nextTab($active);
    });
    $(".prev-step").click(function(e) {
        var $active = $('.nav-tabs li>a.active');
        prevTab($active);
    });
});
function nextTab(elem) {
    $(elem).parent().next().find('a[data-toggle="tab"]').click();
}
function prevTab(elem) {
    $(elem).parent().prev().find('a[data-toggle="tab"]').click();
}
// Code for File Upload to show file name
// Add the following code if you want the name of the file appear on select
$(document).ready(function() {
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });
});
// JS code for Add More Row
$(document).ready(function() {
    var i = 0;
    function buttonClick() {
        i++;
        document.getElementById('inc').innerHTML = i;
    }
    $(".add-more-row").click(function() {
        $("#customFields").append('<tr><td><input type="text" class="form-control" name=""></td><td><input type="text" class="form-control" name=""></td><td><input type="text" class="form-control" name=""></td><td><input type="text" class="form-control" name=""></td><td><input type="text" class="form-control" name=""></td><td><input type="text" class="form-control" name=""></td><td><input type="text" class="form-control" name=""></td><td><input type="text" class="form-control" name=""></td><td class="text-center"><a href="javascript:void(0);" class="remove-row"><i class="fa fa-trash text-danger"></i></a></td></tr>');
    });
    $("#customFields").on('click', '.remove-row', function() {
        $(this).parent().parent().remove();
    });
});
// Tooltip
$(document).ready(function() {
    $('[data-toggle="tooltip"]').tooltip();
});
// Upload and Preview Image
/* $(document).ready(function() {
    $(document).on("click", ".file", function() {
        var file = $(this).parents().find(".file");
        file.trigger("click");
    });
    $('input[type="file"].user-file').change(function(e) {
        var fileName = e.target.files[0].name;
        $("#file").val(fileName);
        var reader = new FileReader();
        reader.onload = function(e) {
            // get loaded data and render thumbnail.
            document.getElementById("preview").src = e.target.result;
        };
        // read the image file as a data URL.
        reader.readAsDataURL(this.files[0]);
    });
});*/
// File upload for Signature
$(document).ready(function() {
    $(document).on("click", "#sign-upload", function() {
        var file = $(this).parents().find("#sign-upload");
        file.trigger("click");
    });
    $('input[type="file"].user-sign').change(function(e) {
        var fileName = e.target.files[0].name;
        $("#sign-upload").val(fileName);
        var reader = new FileReader();
        reader.onload = function(e) {
            // get loaded data and render thumbnail.
            document.getElementById("preview-Sign").src = e.target.result;
        };
        // read the image file as a data URL.
        reader.readAsDataURL(this.files[0]);
    });
});
// Add Row and Table Data
$(document).ready(function() {
    var i = 4;
    $(".add-row").click(function() {
        var name = $("#name").val();
        var email = $("#email").val();
        var markup = "<tr><td>" + (i + 1) + "</td><td>" + name + "</td><td class='text-center'><a href='#' class='action-btn'><i class='fa fa-edit text-info'></i></a> <a href='#' class='action-btn delete-row'><i class='fa fa-trash text-danger'></i></a></td></tr>";
        $("table tbody").append(markup);
        i = i + 1;
    });
    // Find and remove selected table rows
    $("#PackageTable").on('click', '.delete-row', function() {
        $(this).parent().parent().remove();
        i = i - 1;
        // location.reload(true);
    });
});
// Selected Option Jquery for Hide and Show
$(document).ready(function() {
    $('#viewAllApplicationDetails').show();
    $('#MDPICUP').show();
    $(function() {
        $('#SelectParameter').change(function() {
            $('.parameter-properties').hide();
            $('#' + $(this).val()).show();
            $('.' + $(this).val()).show();
        });
    });
});
$(document).ready(function() {
    $(function() {
        $('#departmentId').change(function() {
            $('.parameter-properties').hide();
            $('#' + $(this).val()).show();
            $('.' + $(this).val()).show();
        });
    });
});
// Add Remove Field 
$(document).ready(function() {
    $(function() {
        $('#userRoleId').change(function() {
            $('.parameter-properties').hide();
            $('#' + $(this).val()).show();
            $('.' + $(this).val()).show();
        });
    });
});
// Add Remove Field 
$(document).ready(function() {
    function buttonClick() {
        document.getElementById('inc').innerHTML = i;
    }
    $(".add-values").click(function() {
        $("#addValues").append('<tr><td><input type="text" placeholder="Add value" class="form-control" name=""></td><td class="text-right"><button type="button" class="delete-btn mt-1 remove-values">Remove</button></td></tr>');
    });
    $("#addValues").on('click', '.remove-values', function() {
        $(this).parent().parent().remove();
    });
});
$(document).ready(function() {
    function buttonClick() {
        document.getElementById('inc').innerHTML = i;
    }
    $(".add-multi-values").click(function() {
        $("#addMultiValues").append('<tr><td><input type="text" placeholder="Add value" class="form-control" name=""></td><td class="text-right"><button type="button" class="delete-btn mt-1 remove-multi-values">Remove</button></td></tr>');
    });
    $("#addMultiValues").on('click', '.remove-multi-values', function() {
        $(this).parent().parent().remove();
    });
});
// Add remove checkbox div
$(document).ready(function() {
    $("#fileTypeLabel2").click(function() {
        $("#fileType2").removeClass("hide-file-type");
        $("#fileType1").addClass("hide-file-type");
    });
    $("#fileTypeLabel1").click(function() {
        $("#fileType1").removeClass("hide-file-type");
        $("#fileType2").addClass("hide-file-type");
    });
});
//Selected Option Jquery for Hide and Show for YES NO dropdown
$(document).ready(function() {
    $(function() {
        $('#pwApply').change(function() {
            $('.if-applicable').hide();
            $('#' + $(this).val()).show();
        });
    });
});
// JavaScript for Save in PDF and Print
$(document).ready(function() {
});
// Add results
$(document).ready(function() {
    $(document).on("keyup", ".fixed-total-input", function() {
        var sum = 0;
        $(".fixed-total-input").each(function() {
            sum += +$(this).val();
        });
        $(".fixed-total").val(sum);
    });
    $(document).on("keyup", ".all-total-input", function() {
        var sum = 0;
        $(".all-total-input").each(function() {
            sum += +$(this).val();
        });
        $(".all-Sum-total").val(sum);
    });
    $(document).on("keyup", ".other-total-input", function() {
        var sum = 0;
        $(".other-total-input").each(function() {
            sum += +$(this).val();
        });
        $(".all-other-total").val(sum);
    });
});
// JavaScript for Equity 
$(document).ready(function() {
    $('#aninput').keyup(function(){
      if ($(this).val() > 100){
        alert("No numbers above 100");
        $(this).val('100');
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
//Add Row and Table Data for Send to Concern Department
$(document).ready(function() {
    $(".add-row-for-deptt").click(function() {
        var name = $("#depttName").val();
        var email = $("#depttEmail").val();
        var markup = "<tr><td>" + name + "</td><td>" + email + "</td></tr>";
        $("#DepartmentNameTable tbody").append(markup);
    });
    // Find and remove selected table rows
    $("#DepartmentNameTable").on('click', '.delete-row', function() {
        $(this).parent().parent().remove();
        // location.reload(true);
    });
});
// Add Row and Table Data for Break-up of Cost vinay
$(document).ready(function() {
    $(".add-row-break-up").click(function() {
    var status=0;
	$(".it_em1").each(function(){
		if($(this).val().trim() == '')
		{
			status++;
		
		}
	});
/*	$(".CostasperIIEPP1").each(function(){
		if($(this).val().trim() == '')
		{
			status++;
		
		}
	});*/
	$(".CostasperDPR1").each(function(){
		if($(this).val().trim() == '')
		{
			status++;
		
		}
	});
	if(status==0)
	{
	
	var markup = "<tr><td><input type='text' class='form-control it_em1'></td><td><select class='form-control'><option value='1'>Yes</option><option value='2'>No</option></select></td><td><input type='text' class='form-control CostasperDPR CostasperDPR1' onchange='return landcostIIEP()'></td><td><input type='text' class='form-control CostasperIIEPP CostasperIIEPP1'></td><td class='delete-div-row'><input type='text' class='form-control'><a href='javascript:void(0);' class='remove-row  delete-row delete-break-up'><i class='fa fa-trash text-danger'></i></a></td></tr>";
        $("table tbody.add-from-here").append(markup);
        }
        else { alert("Please  Enter the Empty Item Field Details");
        }
    });
// vinay end
    // Find and remove selected table rows
     $("#BreakUpTable").on('click', '.delete-row', function() {
        $(this).parent().parent().remove();
        CostasperDPR();
        landcostIIEP();
        CostasperIIEPP();
        // location.reload(true);
    });
});
//Search JS
$(document).ready(function(){
  $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
// Selected Option Jquery for Hide and Show
$(document).ready(function() {
    $('#NewUnits').show();
    $(function() {
        $('#SelectSchemeType').change(function() {
            $('.parameter-properties').hide();
            $('#' + $(this).val()).show();
            $('.' + $(this).val()).show();
        });
    });
});
// Active food policy Subsidy JS
$(document).ready(function() {
    // For Capital Subsidy
    $("#CapitalSubsidyLabel").click(function(){ 
      $("#SubsidyBtn a").removeClass("disable-btn");
      $("#SubsidyBtn a").removeClass("common-default-btn");
      $(".policy-box-card").removeClass("disable-btn"); 
      $("#CapitalSubsidyLink").removeClass("disable-btn");
      $("#InterestSubsidyLink").removeClass("active");
      $("#InterestSubsidyLink").addClass("disable-btn");
      $("#CapitalSubsidyLink").addClass("active");
      $("#SubsidyBtn a").addClass("common-btn");
      $(".both-subsidy-btn").hide();
      $(".interest-subsidy-btn").hide();
      $(".capital-subsidy-btn").show();
    });
    // For Interest Subsidy
    $("#InterestSubsidyLabel").click(function(){ 
      $("#SubsidyBtn a").removeClass("disable-btn");
      $("#SubsidyBtn a").removeClass("common-default-btn");
      $(".policy-box-card").removeClass("disable-btn"); 
      $("#InterestSubsidyLink").removeClass("disable-btn");
      $("#CapitalSubsidyLink").removeClass("active");
      $("#CapitalSubsidyLink").addClass("disable-btn");
      $("#InterestSubsidyLink").addClass("active");
      $("#SubsidyBtn a").addClass("common-btn");
      $(".capital-subsidy-btn").hide();
      $(".both-subsidy-btn").hide();
      $(".interest-subsidy-btn").show();
      
    });
    // For both Capital and Interest subsidy
    $("#BothSubsidyLabel").click(function(){ 
      $("#SubsidyBtn a").removeClass("disable-btn");
      $("#SubsidyBtn a").removeClass("common-default-btn");
      $(".policy-box-card").removeClass("disable-btn"); 
      $("#InterestSubsidyLink").removeClass("disable-btn");
      $("#CapitalSubsidyLink").removeClass("disable-btn");
      $("#CapitalSubsidyLink").addClass("active");
      $("#InterestSubsidyLink").addClass("active");
      $("#SubsidyBtn a").addClass("common-btn");
      $(".capital-subsidy-btn").hide();
      $(".interest-subsidy-btn").hide();
      $(".both-subsidy-btn").show();
    });
});
//Evaluate Application Button for Disable Enable
$(document).ready(function() {
	$("#verifiedChecked").click(function(){
		$("#button3").toggleClass("btn-outline-secondary evaluate-btn");
		$("#button1").toggleClass("btn-outline-secondary evaluate-btn");
		$("#button3").toggleClass("btn-outline-success");
		$("#button1").toggleClass("btn-outline-success");
		
	});
});
//For Disable Enable Raise Query Button and Include Application Button by vinay
$(document).ready(function() {
	$("#button1").click(function(){
		$("#button2").removeClass("disable-btn");
		$("#reject").removeClass("disable-btn");
	});
});
$(document).ready(function() {
	$("#button3").click(function(){
		$("#button2").removeClass("disable-btn");
		$("#reject").removeClass("disable-btn");
	});
});
// end vinay
//Active button on checked Checkbox
$(document).ready(function() {
    $(".agenda-note-checkbox").click(function(){ 
        $("#viewAllAgendaNote").removeClass("common-default-btn disable-btn");
        $("#viewAllAgendaNote").addClass("common-btn");
    });
});



//Start Till Date Input JS

$(document).ready(function(){
    $('#cAStatutoryDate').addClass("filled-till-date");
    //var myInput = document.getElementById("cAStatutoryDate").value;
    var myInput = document.getElementById("cAStatutoryDate");
    if (myInput == "") {
      $("#cAStatutoryDate").removeClass("filled-till-date");
      return false;
    }
});

//End Till Date Input JS

//Add new row for easy to use developer
$(document).ready(function() {
    $(".add-row-break-up-new").click(function() {
        var itemName = $("#itemName").val();
        var ConsiderasFCI = $("#ConsiderasFCI").val();
        var DPRInput = $("#DPRInput").val();
        var IIEPPInput = $("#IIEPPInput").val();
        var PICUPRemarks = $("#PICUPRemarks").val();
        
        var markup = "<tr><td>" + itemName + "</td><td>" + ConsiderasFCI + "</td><td>" + DPRInput + "</td><td>" + IIEPPInput + "</td><td>" + PICUPRemarks + "</td><td class='text-center'><a href='javascript:void();' class='action-btn delete-row'><i class='fa fa-trash text-danger'></i></a></td></tr>";
        $("#BreakUpTable tbody").append(markup);
        $("#BreakUpTable tfoot input").val("");
        $("#BreakUpTable tfoot textarea").val("");
        // $("select").val("");
    });
    // Find and remove selected table rows
    $("#BreakUpTable").on('click', '.delete-row', function() {
        $(this).parent().parent().remove();
    });
});

//Is Numeric Function
$(document).ready(function(){
   $(".is-numeric").keypress(function() {
       var regex = new RegExp("^[0-9]+$");
       var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
       if (!regex.test(key)) {
           event.preventDefault();
           $(this).next().html("Only numeric are allowed");
       } else {
           $(this).next().html(" ");
       }
   });
});

//Convert digits into words with JavaScript

$(document).ready(function() {
  var str = '';
  var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
  var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

  function inWords(num) {
      if ((num = num.toString()).length > 13) {
          return 'overflow';
      }
      n = ('000000000000' + num).substr(-13).match(/^(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
      if (!n) {
          return str = '';
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

    $(".is-numeric").keyup(function() {
      //console.log("Run");
     $(this).parent().find(".words").text(inWords($(this).val()));
  });

});



//Start Generic Function for File Upload validation
$(document).ready(function(){
       

      //Upload file and check onchnage
        $('.doc-support').change(function(e) {
            $(this).next().html('');
            var success = true;
            
            if($(this).val()=="")
            {
                $(this).val("");
                $(this).next().html("Attachment Document is Required");
                success = false;
            }    
            return success;
        });

         

      // File extension validation, Add more extension you want to allow
        $('.doc-support').change(function(fdata) {
          $(this).each(function(){

            //alert(this.files[0].size);
            var fileSize = (this.files[0].size)/1024;
            var mbFileSize = fileSize / 1024;
            var twoDecNum = mbFileSize.toFixed(2);
            if (this.files[0].size > 5242880) { 
              $(this).val("");
              $(this).next().html('Your file size is: '+ twoDecNum +' MB, File size should not be more than 5 MB');
            } else {
          return true;
          }

          });
        });
        //Gyan
         // File extension validation, Add more extension you want to allow
           $('.doc-support').change(function(e) {
			$(this).each(function(){
			
				var validExt = ".pdf";
				var filePath = $(this).val();
				var getFileExt = filePath.substring(filePath.lastIndexOf('.') + 1)
				.toLowerCase();
				var pos = validExt.indexOf(getFileExt);
				if (pos < 0) {
				
				$(this).val("");
				$(this).next().html("Please upload PDF file format.");
				$(this).val("");
				
				} else {
				return true;
				}
				});
			});
        
        });
//End Generic Function for File Upload validation

//DIS Evaluation JS

$(document).ready(function() {
    $(".add-row-break-up-new-mak-b").click(function() {
        var markup = "<tr><td><input type='text' name='loanBankName' class='form-control'></td><td><input type='date' name='loanBankDate' class='form-control'></td></td><td><input type='text' name='loanBankAmt' class='form-control bankloanAmt'></td><td class='delete-div-row'><input type='text' name='loanBankROI'  class='form-control'><a href='javascript:void(0);' class='remove-row  delete-row delete-break-up'><i class='fa fa-trash text-danger'></i></a></td></tr>";
        $("table tbody.add-from-here3-mak-b").append(markup);
    });
    // Find and remove selected table rows
    $("#BreakUpTable3-mak-b").on('click', '.delete-row', function() {
        $(this).parent().parent().remove();
        // location.reload(true);
    });
});


$(document).ready(function() {
    $(".add-row-break-up-new-mak-c").click(function() {
        var markup = "<tr><td><input type='text' name='pandMBankName' class='form-control'></td><td><input type='date' name='pandMBankDate' class='form-control'></td><td class='delete-div-row'><input type='text' name='pandMBankAmt' class='form-control '><a href='javascript:void(0);' class='remove-row  delete-row delete-break-up'><i class='fa fa-trash text-danger'></i></a></td></tr>";
        $("table tbody.add-from-here3-mak-c").append(markup);
    });
    // Find and remove selected table rows
    $("#BreakUpTable3-mak-c").on('click', '.delete-row', function() {
        $(this).parent().parent().remove();
        // location.reload(true);
    });
});




$(document).ready(function() {
    $(".add-row-break-up-new-mak-d").click(function() {
        var markup = "<tr><td><input type='text' class='form-control'></td><td><input type='text' class='form-control'></td><td class='delete-div-row'><input type='text' class='form-control'><a href='javascript:void(0);' class='remove-row  delete-row delete-break-up'><i class='fa fa-trash text-danger'></i></a></td></tr>";
        $("table tbody.add-from-here3-mak-d").append(markup);
    });
    // Find and remove selected table rows
    $("#BreakUpTable3-mak-d").on('click', '.delete-row', function() {
        $(this).parent().parent().remove();
        // location.reload(true);
    });
});



$(document).ready(function() {
    $(".add-row-break-up-new-mak-e").click(function() {
        var markup = "<tr><td><input type='text' class='form-control'></td><td><input type='text' class='form-control'></td><td><input type='text' class='form-control'></td><td><input type='text' class='form-control'></td><td><input type='text' class='form-control'></td><td class='delete-div-row'><input type='text' class='form-control'><a href='javascript:void(0);' class='remove-row  delete-row delete-break-up'><i class='fa fa-trash text-danger'></i></a></td></tr>";
        $("table tbody.add-from-here3-mak-e").append(markup);
    });
    // Find and remove selected table rows
    $("#BreakUpTable3-mak-e").on('click', '.delete-row', function() {
        $(this).parent().parent().remove();
        // location.reload(true);
    });
});


$(document).ready(function() {
    $(".add-row-break-up-new-mak-f").click(function() {
        var markup = "<tr><td><input type='text' class='form-control'></td><td><input type='text' class='form-control'></td><td><input type='text' class='form-control'></td><td><input type='text' class='form-control'></td><td><input type='text' class='form-control'></td><td class='delete-div-row'><input type='text' class='form-control'><a href='javascript:void(0);' class='remove-row  delete-row delete-break-up'><i class='fa fa-trash text-danger'></i></a></td></tr>";
        $("table tbody.add-from-here3-mak-f").append(markup);
    });
    // Find and remove selected table rows
    $("#BreakUpTable3-mak-f").on('click', '.delete-row', function() {
        $(this).parent().parent().remove();
        // location.reload(true);
    });
});


$(document).ready(function() {
    $(".add-row-break-up-new-mak-g").click(function() {
        var markup = "<tr><td><input type='text' class='form-control'></td><td><input type='text' class='form-control'></td><td class='delete-div-row'><input type='text' class='form-control'><a href='javascript:void(0);' class='remove-row  delete-row delete-break-up'><i class='fa fa-trash text-danger'></i></a></td></tr>";
        $("table tbody.add-from-here3-mak-g").append(markup);
    });
    // Find and remove selected table rows
    $("#BreakUpTable3-mak-g").on('click', '.delete-row', function() {
        $(this).parent().parent().remove();
        // location.reload(true);
    });
});
