<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.webapp.ims.model.ProposedEmploymentDetails"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script type="text/javascript">
	window.onload = function() {

		if ('${gender}' === 'Male') {
			$('input:radio[name=gender][value=Male]').attr('checked', true);
		} else if ('${gender}' === 'Female') {
			$('input:radio[name=gender][value=Female]').attr('checked', true);
		} else {
			$('input:radio[name=gender][value=Transgender]').attr('checked',
					true);
		}

		if ('${regiOrLicense}' === 'EncloseUAM') {
			$('input:radio[name=regiOrLicense][value=EncloseUAM]').attr(
					'checked', true);
		} else {
			$('input:radio[name=regiOrLicense][value=IEMcopy]').attr('checked',
					true);
		}
		if ('${natureOfProject}' == 'NewProject') {
			$('select').find('option[value=NewProject]').attr('selected',
					'selected');
			//var x = document.getElementsByClassName('hide-by-class').hide;
			$(".hide-by-class").hide();
			document.getElementById('hideByRowId1').style.display = 'none';
			document.getElementById('hideByRowId2').style.display = 'none';
			var element = document.getElementById("hideByRowId3");
			element.classList.remove("hide");
			document.getElementById('hideByRowId4').style.display = 'table-row';
			document.getElementById('natureOfProjectDiv').style.display = 'none';

		}
		if ('${natureOfProject}' == 'ExistingProject') {
			$('select').find('option[value=ExistingProject]').attr('selected',
					'selected');
			$(".hide-by-class").show();
			var element = document.getElementById("hideByRowId1");
			element.classList.remove("hide");
			var element = document.getElementById("hideByRowId2");
			element.classList.remove("hide");
			var element = document.getElementById("hideByRowId3");
			element.classList.remove("hide");
			document.getElementById('hideByRowId4').style.display = 'table-row';
			document.getElementById('natureOfProjectDiv').style.display = 'block';

			if ('${expansion}' == 'Expansion') {
				$('input:checkbox[name="expansion"][value="Expansion"]').prop(
						'checked', true);
			}
			if ('${diversification}' == 'Diversification') {
				$(
						'input:checkbox[name="diversification"][value="Diversification"]')
						.prop('checked', true);
			}
		}
		if ('${ISF_Reim_SCST}' != null && '${ISF_Reim_SCST}' != '') {
			$(
					'input:checkbox[name="ISF_SGST_Claim_Reim"][value="ISF_Reim_SCST"]')
					.prop('checked', true);

		}

		if ('${ISF_Reim_FW}' != null && '${ISF_Reim_FW}' != '') {
			$('input:checkbox[name="ISF_SGST_Claim_Reim"][value="ISF_Reim_FW"]')
					.prop('checked', true);

		}
		if ('${ISF_Reim_BPLW}' != null && '${ISF_Reim_BPLW}' != '') {
			$(
					'input:checkbox[name="ISF_SGST_Claim_Reim"][value="ISF_Reim_BPLW"]')
					.prop('checked', true);

		}

		if ('${ISF_Additonal_Stamp_Duty_EX}' != null
				&& '${ISF_Additonal_Stamp_Duty_EX}' != '') {
			$(
					'input:checkbox[name="ISF_Stamp_Duty"][value="ISF_Additonal_Stamp_Duty_EX"]')
					.prop('checked', true);

		}

		if ('${ISF_Add_Epf_Reim_SkUkW}' != null
				&& '${ISF_Add_Epf_Reim_SkUkW}' != '') {
			$(
					'input:checkbox[name="Add_EFP_Reim_SkUkW_checkbox"][value="ISF_Add_Epf_Reim_SkUkW"]')
					.prop('checked', true);

		}
		if ('${ISF_Add_Epf_Reim_DIVSCSTF}' != null
				&& '${ISF_Add_Epf_Reim_DIVSCSTF}' != '') {
			$(
					'input:checkbox[name="Add_EFP_Reim_DIVSCSTF_checkbox"][value="ISF_Add_Epf_Reim_DIVSCSTF"]')
					.prop('checked', true);

		}

		if ('${ISF_ACI_Subsidy_Indus}' != null
				&& '${ISF_ACI_Subsidy_Indus}' != '') {
			$(
					'input:checkbox[name="Add_CIS_checkbox"][value="ISF_ACI_Subsidy_Indus"]')
					.prop('checked', true);

		}
		if ('${ISF_AII_Subsidy_DIVSCSTF}' != null
				&& '${ISF_AII_Subsidy_DIVSCSTF}' != '') {
			$(
					'input:checkbox[name="Add_IIS_checkbox"][value="ISF_AII_Subsidy_DIVSCSTF"]')
					.prop('checked', true);

		}
		if ('${ISF_Cstm_Inc_Status}' === 'Yes') {
			$('input:radio[name="ISF_Cstm_Inc_Status"][value="Yes"]').prop(
					'checked', true);

		} else {
			$('input:radio[name="ISF_Cstm_Inc_Status"][value="No"]').prop(
					'checked', true);
		}

		if ('${hideCustamList}' === 'hideCustamList') {
			document.getElementById('showHide1').style.display = 'table-row';
			document.getElementById('showHide2').style.display = 'none';
			document.getElementById('showHide3').style.display = 'none';
			document.getElementById('showHide4').style.display = 'none';

		} else {
			document.getElementById('showHide1').style.display = 'none';
			document.getElementById('showHide2').style.display = 'table-row';
			document.getElementById('showHide3').style.display = 'table-row';
			document.getElementById('showHide4').style.display = 'table-row';
		}
	}
	$(document).on('click', '#printMethod', function(e) {
		e.preventDefault();

		var $this = $(this);
		var originalContent = $('body').html();
		var printArea = $this.parents('.col-sm-8 text-right').html();

		$('body').html(printArea);
		window.print();
		$('body').html(originalContent);
	});
	
	
</script>


<div class="without-wizard-steps">

	<form:form action="saveApplicantDetails" class="isf-form"
									method="get" modelAttribute="applicantDetail"
									enctype="multipart/form-data">
									<hr class="mt-2">									
									<div class="isf-form mt-4" id="content1">
									<h4	class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Authorised Signatory Details</h4>
										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered">
														<tbody>
															<tr>
																<td>First Name</td>
																<td><input type="text" class="form-control"
																	name="appFirstName" id="appFirstNameId"
																	value="${appFirstName}" disabled="disabled">
																<td rowspan="4" class="text-center"><img
																	src="data:image/png;base64,${applicantDetail.base64imageFile}"  style="height:250px;" /></td>
															</tr>
															<tr>
																<td>Middle Name</td>
																<td><input type="text" class="form-control"
																	value="${appMiddleName}" disabled="disabled"
																	id="appMiddleNameId" name="appMiddleName"></td>
															</tr>
															<tr>
																<td>Last Name</td>
																<td><input type="text" class="form-control"
																	value="${appLastName}" disabled="disabled"
																	id="appLastNameId" name="appLastName"></td>
															</tr>
															<tr>
																<td>Email ID</td>
																<td><input type="text" class="form-control"
																	value="${appEmailId}" disabled="disabled"
																	id="appEmailId" name="appEmailId"></td>
															</tr>
															<tr>
																<td>Mobile No:(+91)</td>
																<td colspan="2"><input type="text"
																	class="form-control" value="${appMobileNo}"
																	disabled="disabled" id="appMobileNoId"
																	name="appMobileNo"></td>
															</tr>
															<tr>
																<td>Phone No. (Landline)</td>
																<td colspan="2"><input type="text"
																	class="form-control" value="${appPhoneNo}"
																	disabled="disabled" id="appPhoneNo" name="appPhoneNo"></td>
															</tr>
															<tr>
																<td>Designation</td>
																<td colspan="2"><input type="text"
																	class="form-control" value="${appDesignation}"
																	disabled="disabled" id="appDesignation"
																	name="appDesignation"></td>
															</tr>
															<tr>
																<td>Gender</td>
																<td colspan="2">
																	<div class="form-group">
																		<div
																			class="custom-control custom-radio custom-control-inline">
																			<input type="radio" class="custom-control-input"
																				id="MaleOpt" disabled="disabled" name="gender"
																				value="Male"> <label
																				class="custom-control-label" for="MaleOpt">Male</label>
																		</div>
																		<div
																			class="custom-control custom-radio custom-control-inline">
																			<input type="radio" class="custom-control-input"
																				id="FemaleOpt" disabled="disabled" name="gender"
																				value="Female"> <label
																				class="custom-control-label" for="FemaleOpt">Female</label>
																		</div>
																		<div
																			class="custom-control custom-radio custom-control-inline">
																			<input type="radio" class="custom-control-input"
																				id="Transgender" disabled="disabled" name="gender"
																				value="Transgender"> <label
																				class="custom-control-label" for="Transgender">Transgender
																			</label>
																		</div>

																	</div>
																</td>
															</tr>
															<tr>
																<td>Aadhaar Number</td>
																<td colspan="2"><input type="text"
																	value="${appAadharNo}" disabled="disabled"
																	class="form-control" name="appAadharNo"></td>
															</tr>
															<tr>
																<td>PAN Number</td>
																<td colspan="2"><input type="text"
																	value="${appPancardNo}" disabled="disabled"
																	class="form-control" name="appPancardNo"></td>
															</tr>
															<tr>
																<td>Address Line - 1</td>
																<td colspan="2"><input type="text"
																	class="form-control" value="${appAddressLine1}"
																	disabled="disabled" id="appAddressLine1"
																	name="appAddressLine1"></td>
															</tr>
															<tr>
																<td>Address Line - 2</td>
																<td colspan="2"><input type="text"
																	class="form-control" value="${appAddressLine2}"
																	disabled="disabled" id="appAddressLine2"
																	name="appAddressLine2"></td>
															</tr>
															<tr>
																<td>Country</td>
																<td colspan="2"><input type="text"
																	value="${appCountry}" disabled="disabled"
																	class="form-control" name="appCountry"></td>
															</tr>
															<tr>
																<td>State/UT</td>
																<td colspan="2"><input type="text"
																	value="${appState}" disabled="disabled"
																	class="form-control" name="appState"></td>
															</tr>
															<tr>
																<td>District</td>
																<td colspan="2"><input type="text"
																	value="${appDistrict}" disabled="disabled"
																	class="form-control" name="appDistrict"></td>
															</tr>
															<tr>
																<td>Pin Code</td>
																<td colspan="2"><input type="text"
																	class="form-control" value="${appPinCode}"
																	disabled="disabled" id="appPinCode" name="appPinCode"></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>

								</form:form>

	<hr class="mt-2">
	

	<div class="isf-form mt-4" id="content2">
	<h4	class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Business	Entity Details</h4>
		<hr class="mt-2">
		<div class="row">
			<div class="col-sm-12" id="downloadBED">
				<div class="table-responsive">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td style="width: 50%;">Business Entity Name</td>
								<td><input type="text" class="form-control"
									value="${businessEntityName}" disabled="disabled"
									id="businessEntityName" name="businessEntityName"></td>
							</tr>
							<tr>
								<td>Organisation Type</td>
								<td><input type="text" class="form-control"
									disabled="disabled" value="${businessEntityType}"></td>
							</tr>
							<tr>
								<td>Authorised Signatory Name</td>
								<td><input type="text" class="form-control"
									value="${authorisedSignatoryName}" disabled="disabled"
									id="authorisedSignatoryName"
									name="authorisedSignatoryName"></td>
							</tr>
							<tr>
								<td>Designation</td>
								<td><input type="text" class="form-control"
									value="${businessDesignation}" disabled="disabled"
									id="businessDesignation" name="businessDesignation"></td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input type="email" class="form-control"
									value="${emailId}" disabled="disabled" id="emailId"
									name="emailId"></td>
							</tr>
							<tr>
								<td>Mobile No.</td>
								<td><input type="text" class="form-control"
									value="${mobileNumber}" disabled="disabled"
									id="mobileNumber" name="mobileNumber"></td>
							</tr>
							<tr>
								<td>Fax</td>
								<td><input type="text" class="form-control"
									value="${fax}" disabled="disabled" id="fax" name="fax"></td>
							</tr>
							<tr>
								<td>Registered Office Address</td>
								<td><input type="text" class="form-control"
									value="${businessAddress}" disabled="disabled"
									id="businessAddress" name="businessAddress"></td>
							</tr>
							<tr>
								<td>Country</td>
								<td><input type="text" class="form-control" id=""
									value="${businessCountryName}" disabled="" name=""></td>
							</tr>
							<tr>
								<td>State/UT</td>
								<td><input type="text" class="form-control" id=""
									value="${businessStateName}" disabled="" name=""></td>
							</tr>
							<tr>
								<td>District</td>
								<td><input type="text" class="form-control" id=""
									value="${businessDistrictName}" disabled="" name=""></td>
							</tr>
							<tr>
								<td>Pin Code</td>
								<td><input type="text" class="form-control"
									value="${PinCode}" disabled="disabled" id="PinCode"
									name="PinCode"></td>
							</tr>
							<tr>
								<td colspan="2"><strong>Director / Partner
										/ Owner / Proprietor Details</strong></td>
							</tr>

						</tbody>
					</table>
					<table
													class="table table-stripped table-bordered directors-table"
													id="customFields">
													<c:if test="${ not empty ProprietorDetailsList}">

														<thead>
															<tr>
																<th>Director Name</th>
																<th>Designation</th>
																<th>Address</th>
																<th>Mobile No.</th>
																<th>% Equity</th>
																<th>Email</th>
																<th>Gender</th>
																<th>Category</th>
																<th>Pan No</th>
																<th>DIN</th>
															</tr>
														</thead>
													</c:if>
													<tbody>
														<c:forEach var="list" items="${ProprietorDetailsList}"
															varStatus="counter">
															<tr>
																<td>${list.directorName}</td>
																<td>${list.designation}</td>
																<td>${list.proprietorDetailsaddress}</td>
																<td>${list.mobileNo}</td>
																<td>${list.equity}</td>
																<td>${list.email}</td>
																<td>${list.gender}</td>
																<td>${list.category}</td>
																<td>${list.panCardNo}</td>
																<td>${list.din}</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td colspan="2"><strong>Uploaded Documents
										and Certificates</strong></td>
							</tr>
							<tr>
								<td style="width: 50%;">Year of Unit Establishment</td>
								<td><input type="text" class="form-control"
									value="${yearEstablishment}" disabled="disabled"
									id="yearEstablishment" name="yearEstablishment"></td>
							</tr>
							<tr>
								<td>GSTIN</td>
								<td><input type="text" value="${gstin}"
									disabled="disabled" class="form-control" id="gstin"
									name="gstin"></td>
							</tr>
							<tr>
								<td>CIN</td>
								<td><input type="text" value="${cin}"
									disabled="disabled" class="form-control" id="cin"
									name="cin"></td>
							</tr>
							<tr>
								<td>Company PAN No.</td>
								<td><input type="text" class="form-control"
									value="${companyPanNo}" disabled="disabled"
									id="companyPanNo" name="companyPanNo"></td>
							</tr>
							<tr>
								<td>MoA / Partnership Deed Attachment</td>
								<td><a  href="./downloadDocBusiness?fileName=${moaDocFname}"><small>${moaDocFname}</small></a>
								</td>
							</tr>
							<tr>
								<td>Certificate Incorporation Registration
									Attachment</td>
								<td><a href="./downloadDocBusiness1?fileName=${regisAttacDocFname}"><small>${regisAttacDocFname}</small></a>
								</td>
							</tr>
							<tr>
								<td>Certified copy of the resolution of the Board of
									Directors of the Company</td>
								<td><a href="./downloadDocBusiness2?fileName=${bodDocFname}"><small>${bodDocFname}</small></a>
								</td>
							</tr>
							<tr>
								<td>Competent Authority of the Industrial
									Undertaking authorizing the deponent be provided along
									with the affidavit.</td>
								<td><a href="./downloadDocBusiness3?fileName=${indusAffidaDocFname}"><small>${indusAffidaDocFname}</small></a>
								</td>
							</tr>
							<tr>
								<td>Annexure I-A Format</td>
								<td><a href="./downloadDocBusiness4?fileName=${annexureiaformat}"><small>${annexureiaformat}</small></a>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>							

	<div class="isf-form mt-4" id="content3">
	<h4	class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Project Details</h4>
		<hr class="mt-2">
		<div class="row" id="downloadPD">
			<div class="col-sm-12">
				<div class="table-responsive">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td>Name of Contact Person</td>
								<td><input type="text" class="form-control"
									value="${contactPersonName}" disabled="disabled"
									id="contactPersonName" name="contactPersonName"></td>
							</tr>
							<tr>
								<td>Mobile No:(+91)</td>
								<td><input type="text" class="form-control"
									value="${mobileNo}" disabled="disabled" id="mobileNo"
									name="mobileNo"></td>
							</tr>
							<tr>
								<td>Designation</td>
								<td><input type="text" class="form-control"
									value="${designation}" disabled="disabled"
									id="designation" name="designation"></td>
							</tr>
							<tr>
								<td>Project Description</td>
								<td><input type="text" class="form-control"
									disabled="disabled" id="projectDescription"
									name="projectDescription" value="${projectDescription}"></td>
							</tr>
							<tr>
								<td>Website (if any)</td>
								<td><input type="text" class="form-control"
									value="${webSiteName}" disabled="disabled"
									id="webSiteName" name="webSiteName"></td>
							</tr>
							<tr>
								<td>Full Address</td>
								<td><input type="text" class="form-control"
									value="${fullAddress}" disabled="disabled"
									id="fullAddress" name="fullAddress"></td>
							</tr>
							<tr>
								<td>District</td>
								<td><input type="text" class="form-control"
									value="${districtName}" disabled="disabled"
									id="districtName" name="districtName"></td>
							</tr>
							<tr>
								<td>Mandal</td>
								<td><input type="text" class="form-control"
									value="${mandalName}" disabled="disabled"
									id="mandalName" name="mandalName"></td>
							</tr>
							<tr>
								<td>Region</td>
								<td><input type="text" class="form-control"
									value="${resionName}" disabled="disabled"
									id="resionName" name="resionName"></td>
							</tr>
							<tr>
								<td style="width: 50%;">Enclose Udyam Registration
									Number (for MSME)</td>
								<td><a href="./downloadDoc1?fileName=${regiOrLicenseFileName}"><small>${regiOrLicenseFileName}</small></a>
								</td>
							</tr>
							<!-- <tr>
                                                     <td>IEM copy (for large & Mega)</td>
                                                     <td>
                                                         <a href="javascript:void();"><small>document-file.pdf</small></a>
                                                     </td>
                                                 </tr> -->
							<tr>
								<td>Select Nature of the project</td>
								<td><input type="text" class="form-control"
									value="${natureOfProject}" id="natureOfProjectId"
									disabled="disabled" name=""></td>
							</tr>
							<tr>
								<td colspan="2">
									<div class="col-sm-12" id="natureOfProjectDiv">
										<div class="form-group mt-3">
											<div
												class="custom-control custom-checkbox custom-control-inline">
												<input type="checkbox" readonly="readonly"
													class="custom-control-input" id="Expansion"
													name="expansion" value="Expansion"> <label
													class="custom-control-label" for="Expansion">Expansion</label>
											</div>
											<div
												class="custom-control custom-checkbox custom-control-inline">
												<input type="checkbox" readonly="readonly"
													class="custom-control-input" id="Diversification"
													name="diversification" value="Diversification">
												<label class="custom-control-label"
													for="Diversification">Diversification</label>
											</div>
										</div>
									</div>
								</td>
							</tr>
							<tr class="hide-by-class">
								<td colspan="2"><strong>Details of
										existing/proposed products to be manufactured and its
										capacity (for expansion projects only)</strong></td>
							</tr>
							<tr class="hide-by-class">
								<td>Existing Products</td>
								<td><input type="text" class="form-control"
									value="${ExistingProducts}" readonly="readonly"
									name="ExistingProducts"></td>
							</tr>
							<tr class="hide-by-class">
								<td>Existing Installed Capacity</td>
								<td>
									<table class="table table-bordered">
										<tr>
											<td><input type="text" class="form-control"
												value="${existingInstalledCapacity}"
												disabled="disabled" name="existingInstalledCapacity"></td>
											<td><select class="form-control" readonly="readonly">
													<option value="0">Select Unit</option>
													<option value="1">Sq.Ft.</option>
													<option value="2">Unit-Wise Count</option>
													<option value="3" selected="">Tonnes</option>
													<option value="4">TPH/Unit</option>
													<option value="5">MT</option>
													<option value="6">Litres</option>
													<option value="8">Kg</option>
											</select></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="hide-by-class">
								<td>Proposed Products</td>
								<td><input type="text" class="form-control"
									value="${proposedProducts}" readonly="readonly"
									name="proposedProducts"></td>
							</tr>
							<tr class="hide-by-class">
								<td>Proposed Installed Capacity</td>
								<td>
									<table class="table table-bordered">
										<tr>
											<td><input type="text" class="form-control"
												value="${proposedInstalledCapacity}"
												disabled="disabled" name="proposedProducts"></td>
											<td><select class="form-control" disabled="">
													<option value="0">Select Unit</option>
													<option value="1" selected="">Sq.Ft.</option>
													<option value="2">Unit-Wise Count</option>
													<option value="3">Tonnes</option>
													<option value="4">TPH/Unit</option>
													<option value="5">MT</option>
													<option value="6">Litres</option>
													<option value="8">Kg</option>
											</select></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="hide-by-class">
								<td>Existing Gross Block</td>
								<td><input type="text" class="form-control"
									value="${existingGrossBlock}" readonly="readonly"
									name="existingGrossBlock"></td>
							</tr>
							<tr class="hide-by-class">
								<td>Proposed Gross Block</td>
								<td><input type="text" class="form-control"
									value="${proposedGrossBlock}" readonly="readonly"
									name="proposedGrossBlock"></td>
							</tr>
							<tr id="hideByRowId4">
								<td colspan="2"><strong>Relevant
										Documentary Support</strong></td>
							</tr>
							<tr id="hideByRowId3">
								<td>Enclose Detailed Project Report prepared by
									External Consultant/Chartered Accountants</td>
								<td><a href="./downloadDoc2?fileName=${enclDetProRepFileName}"><small>${enclDetProRepFileName}</small></a></td>
							</tr>

							<tr id="hideByRowId1">
								<td>CA Certificate for existing gross block</td>
								<td><a href="./downloadDoc3?fileName=${caCertificateFileName}"><small>${caCertificateFileName}</small></a></td>
							</tr>
							<tr id="hideByRowId2">
								<td>Chartered Engineer’s Certified List of Fixed
									Assets in support of existing gross block.</td>
								<td><a href="./downloadDoc4?fileName=${charatEngFileName}"><small>${charatEngFileName}</small></a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	

	<div class="isf-form mt-4" id="content4">
	<h4	class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Investment Details</h4>
		<hr class="mt-2">
		<div class="row" id="downloadID">
			<div class="col-sm-12">
				<div class="table-responsive">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td>Category of Industrial Undertaking</td>
								<td><input type="text" class="form-control" id=""
									value="${invIndType}" disabled="" name=""></td>
							</tr>
							<tr>
								<td>Proposed Fixed Capital Investment</td>
								<td><input type="text" class="form-control"
									value="${invFci}" disabled="disabled" id="invFci"
									name="invFci"></td>
							</tr>
							<tr>
								<td>Total Cost of Project</td>
								<td><input type="text" class="form-control"
									value="${invTotalProjCost}" disabled="disabled"
									id="invTotalProjCost" name="invTotalProjCost"></td>
							</tr>
							<tr>
								<td>Land Cost</td>
								<td><input type="text" class="form-control"
									value="${invLandCost}" disabled="disabled"
									id="invLandCost" name="invLandCost"></td>
							</tr>
							<tr>
								<td>Building Site Development & Civil Works Cost</td>
								<td><input type="text" class="form-control"
									value="${invBuildingCost}" disabled="disabled"
									id="invBuildingCost" name="invBuildingCost"></td>
							</tr>
							<tr>
								<td>Plant & Machinery Cost</td>
								<td><input type="text" class="form-control"
									value="${invPlantAndMachCost}" disabled="disabled"
									id="invPlantAndMachCost" name="invPlantAndMachCost"></td>
							</tr>
							<tr>
								<td>Miscellaneous Fixed Asset(MFA)</td>
								<td><input type="text" class="form-control"
									value="${invOtherCost}" disabled="disabled"
									id="invOtherCost" name="invOtherCost"></td>
							</tr>
							<tr>
								<td>Indicate Opted Cut-off date investment</td>
								<td><input type="text" class="form-control"
									value="${invCommenceDate}" disabled="disabled"
									name="invCommenceDate"></td>
							</tr>
							<tr>
								<td>Proposed Date of Commencement of Commercial Production</td>
								<td><input type="text" class="form-control"
									value="${propCommProdDate}" disabled="disabled"
									name="propCommProdDate"></td>
							</tr>
							<tr>
								<td colspan="2"><strong>Phase Wise
										Investment</strong></td>
							</tr>
							<tr>
								<td style="width: 50%;">Phase Wise Investment as per
									Detailed Project Report (If Applicable)</td>
								<td><input type="text" class="form-control" id=""
									value="${pwApply}" disabled="" name=""></td>
							</tr>
							<tr>
															<td style="width: 50%;">Phase Wise Investment as per
																Detailed Project Report (If Applicable)</td>
															<td><input type="text" class="form-control" id=""
																value="${pwApply}" disabled="" name=""></td>
														</tr>
														<tr>
															<td colspan="2"><c:if test="${not empty pwInvList}">
																	<table class="table table-stripped table-bordered">
																		<thead>
																			<tr>
																				<th>Sr. No.</th>
																				<th>Name of the Phase</th>
																				<th>Proposed Fixed Capital Investment</th>
																				<th>Proposed Date of Commercial Production</th>



																			</tr>
																		</thead>

																		<%
																			int k = 0;
																		%>
																		<tbody>
																			<c:forEach var="pwInvObj" items="${pwInvList}">
																				<!-- Iterating the list using JSTL tag  -->
																				<c:if test="${not empty pwInvList}">
																					<c:if test="${fn:length(pwInvList)>0}">
																						<tr>
																							<td class="text-center"><%=++k%></td>
																							<td>${pwInvObj.pwPhaseName}</td>
																							<td>${pwInvObj.pwFci}</td>
																							<td>${pwInvObj.pwPropProductDate}</td>

																						</tr>
																					</c:if>
																				</c:if>
																			</c:forEach>
																		</tbody>
																	</table>
																</c:if></td>
														</tr>
							<tr>
								<td>Upload for Break-Up of Project Cost & Means of
									Financing</td>
								<td><a href="./downloadDocInvestment?fileName=${invFileName}"><small>${invFileName}</small></a></td>
							</tr>

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>							

	<div class="isf-form mt-4" id="downloadPED">
	<h4	class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Proposed	Employment Details</h4>
		<hr class="mt-2">
		<div class="row">
			<div class="col-sm-12">
				<div class="table-responsive">
					<div class="table-responsive mt-3">
						<table class="table table-stripped table-bordered">
							<thead>
								<tr>
									<th>Skilled/Unskilled</th>
									<th>Male Emp</th>
									<th>Female Emp</th>
									<th>General</th>
									<th>SC</th>
									<th>ST</th>
									<th>OBC</th>
									<th>BPL</th>
									<th>Divyang</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list"
									items="${proposedEmploymentDetails}" varStatus="counter">
									<tr>
										<td>${list.skilledUnskilled}</td>
										<td>${list.numberofMaleEmployees}</td>
										<td>${list.numberOfFemaleEmployees}</td>
										<td>${list.numberOfGeneral}</td>
										<td>${list.numberOfSc}</td>
										<td>${list.numberOfSt}</td>
										<td>${list.numberOfObc}</td>
										<td>${list.numberOfBpl}</td>
										<td>${list.numberOfDivyang}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td style="width: 50%;">Skilled Male Employees</td>
								<td><input type="text" class="form-control" id=""
									disabled="" name="" value="${skilledEmploymentMale}"></td>
							</tr>
							<tr>
								<td>Skilled Female Employees</td>
								<td><input type="text" class="form-control" id=""
									disabled="" name="" value="${skilledEmploymentFemale}"></td>
							</tr>
							<tr>
								<td>Total Skilled Employees</td>
								<td><input type="text" class="form-control" id=""
									value="${totalSkilledEmployment}" disabled="" name=""></td>
							</tr>

									<tr>
								<td style="width: 50%;">Unskilled Male Employees</td>
								<td><input type="text" class="form-control" id=""
									disabled="" name="" value="${unSkilledEmploymentMale}"></td>
							</tr>
							<tr>
								<td>Unskilled Female Employees</td>
								<td><input type="text" class="form-control" id=""
									disabled="" name="" value="${unSkilledEmploymentFemale}"></td>
							</tr>
							<tr>
								<td>Total Unskilled Employees</td>
								<td><input type="text" class="form-control" id=""
									value="${totalUnSkilledEmployment}" disabled="" name=""></td>
							</tr>

							<tr>
								<td>Total Male Employees</td>
								<td><input type="text" class="form-control" id=""
									value="${grossTotalMaleEmployment}" disabled="" name=""></td>
							</tr>
							<tr>
								<td>Total Female Employees</td>
								<td><input type="text" class="form-control" id=""
									value="${grossTotalFemaleEmployment}" disabled=""
									name=""></td>
							</tr>
							<tr>
								<td>Total Employees</td>
								<td><input type="text" class="form-control" id=""
									value="${grossTotalMaleandFemaleEmployment}" disabled=""
									name=""></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<h4
		class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">Incentive
		Details</h4>

	<div class="isf-form" id="content6">
		<hr class="mt-2">
		<div class="row" id="downloadInc">
			<div class="col-sm-12">
				<div class="table-responsive">
					<table
						class="table table-stripped table-bordered loc-stage-table">
						<tbody>
							<tr>
								<td style="width: 50%;"><strong>Amount of
										SGST claimed for reimbursement</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Claim_Reim}" disabled="disabled"
									name="ISF_Claim_Reim"></td>
							</tr>
							<tr>
								<td>
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											disabled="disabled" id="SGST1"
											name="ISF_SGST_Claim_Reim" value="ISF_Reim_SCST">
										<label class="custom-control-label" for="SGST1">Additional
											10% GST where 25% minimum SC/ST workers employed
											subject to minimum of 400 total workers in industrial
											undertakings located in Paschimanchal and minimum of
											200 total workers in B-P-M</label>
									</div>
								</td>
								<td><input type="text" class="form-control"
									value="${ISF_Reim_SCST}" disabled="disabled"
									name="ISF_Reim_SCST"></td>
							</tr>
							<tr>
								<td>
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											disabled="disabled" id="SGST2"
											name="ISF_SGST_Claim_Reim" value="ISF_Reim_FW">
										<label class="custom-control-label" for="SGST2">Additional
											10% GST where 40% minimum female workers employed
											subject to minimum of 400 total workers in industrial
											undertakings located in Paschimanchal and minimum of
											200 total workers in B-P-M</label>
									</div>
								</td>
								<td><input type="text" class="form-control"
									value="${ISF_Reim_FW}" disabled="disabled"
									name="ISF_Reim_FW"></td>
							</tr>
							<tr>
								<td>
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											disabled="disabled" id="SGST3"
											name="ISF_SGST_Claim_Reim" value="ISF_Reim_BPLW">
										<label class="custom-control-label" for="SGST3">Additional
											10% GST where 25% minimum BPL workers employed subject
											to minimum of 400 total workers in industrial
											undertakings located in Paschimanchal and minimum of
											200 total workers in B-P-M</label>
									</div>
								</td>
								<td><input type="text" class="form-control"
									value="${ISF_Reim_BPLW}" disabled="disabled"
									name="ISF_Reim_BPLW"></td>
							</tr>
							<tr>
								<td><strong>Total SGST Reimbursement</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Ttl_SGST_Reim}" disabled="disabled"
									name="ISF_Ttl_SGST_Reim"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12">
				<div class="table-responsive">
					<table
						class="table table-stripped table-bordered loc-stage-table">
						<tbody>
							<tr>
								<td style="width: 50%;"><strong>Amount of
										Stamp Duty Exemption</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Stamp_Duty_EX}" disabled="disabled"
									name="ISF_Stamp_Duty_EX"></td>
							</tr>
							<tr>
								<td colspan="2"><strong>Or</strong></td>
							</tr>
							<tr>
								<td style="width: 50%;"><strong>Amount of
										Stamp Duty Reimbursement</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Additonal_Stamp_Duty_EX}"
									disabled="disabled" name="ISF_Additonal_Stamp_Duty_EX"></td>
							</tr>
							<tr>
								<td>
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											disabled="disabled" id="SGST4" name="ISF_Stamp_Duty"
											value="ISF_Additonal_Stamp_Duty_EX"> <label
											class="custom-control-label" for="SGST4">Additional
											Stamp Duty exemption @20% upto maximum of 100% in case
											of industrial undertakings having 75% equity owned by
											Divyang/SC/ ST/Females Promoters</label>
									</div>
								</td>
								<td><input type="text" class="form-control"
									value="${ISF_Amt_Stamp_Duty_Reim}" disabled="disabled"
									name="ISF_Amt_Stamp_Duty_Reim"></td>
							</tr>
							<tr>
								<td><strong>Total Stamp Duty
										Exemption/Reimbursement</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Ttl_Stamp_Duty_EX}" disabled="disabled"
									name="ISF_Ttl_Stamp_Duty_EX"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12">
				<div class="table-responsive">
					<table
						class="table table-stripped table-bordered loc-stage-table">
						<tbody>
							<tr>
								<td style="width: 50%;"><strong>EPF
										Reimbursement (100 or more unskilled workers)</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Epf_Reim_UW}" disabled="disabled"
									name="ISF_Epf_Reim_UW"></td>
							</tr>
							<tr>
								<td>
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											disabled="disabled" id="SGST5"
											name="Add_EFP_Reim_SkUkW_checkbox"
											value="ISF_Add_Epf_Reim_SkUkW"> <label
											class="custom-control-label" for="SGST5">Additional
											10% EPF Reimbursement (200 direct skilled and
											unskilled workers)</label>
									</div>
								</td>
								<td><input type="text" class="form-control"
									value="${ISF_Add_Epf_Reim_SkUkW}" disabled="disabled"
									name="ISF_Add_Epf_Reim_SkUkW"></td>
							</tr>
							<tr>
								<td>
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											disabled="disabled" id="SGST6"
											name="Add_EFP_Reim_DIVSCSTF_checkbox"
											value="ISF_Add_Epf_Reim_DIVSCSTF"> <label
											class="custom-control-label" for="SGST6">Additional
											10% EPF Reimbursement upto maximum of 70% in case of
											industrial undertakings having 75% equity owned by
											Divyang/SC/ST/Females Promoters</label>
									</div>
								</td>
								<td><input type="text" class="form-control"
									value="${ISF_Add_Epf_Reim_DIVSCSTF}" disabled="disabled"
									name="ISF_Add_Epf_Reim_DIVSCSTF"></td>
							</tr>
							<tr>
								<td><strong>Total EPF Reimbursement</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Ttl_EPF_Reim}" disabled="disabled"
									name="ISF_Ttl_EPF_Reim"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12">
				<div class="table-responsive">
					<table
						class="table table-stripped table-bordered loc-stage-table">
						<tbody>
							<tr>
								<td style="width: 50%;"><strong>Capital
										Interest Subsidy</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Cis}" disabled="disabled" name="ISF_Cis"></td>
							</tr>
							<tr>
								<td>
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											disabled="disabled" id="SGST7" name="Add_CIS_checkbox"
											value="ISF_ACI_Subsidy_Indus"> <label
											class="custom-control-label" for="SGST7">Additional
											Capital Interest Subsidy@2.5% upto maximum of 7.5% in
											case of industrial undertakings having 75% equity
											owned by Divyang/SC/ST/Females Promoters</label>
									</div>
								</td>
								<td><input type="text" class="form-control"
									value="${ISF_ACI_Subsidy_Indus}" disabled="disabled"
									name="ISF_ACI_Subsidy_Indus"></td>
							</tr>
							<tr>
								<td style="width: 50%;"><strong>Infrastructure
										Interest Subsidy</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Infra_Int_Subsidy}" disabled="disabled"
									name="ISF_Infra_Int_Subsidy"></td>
							</tr>
							<tr>
								<td>
									<div class="custom-control custom-checkbox mb-4">
										<input type="checkbox" class="custom-control-input"
											disabled="disabled" id="SGST8" name="Add_IIS_checkbox"
											value="ISF_AII_Subsidy_DIVSCSTF"> <label
											class="custom-control-label" for="SGST8">Additional
											Infrastructure Interest Subsidy @2.5% upto maximum of
											7.5% in case of industrial undertakings having 75%
											equity owned by Divyang/SC/ST/Females Promoters</label>
									</div>
								</td>
								<td><input type="text" class="form-control"
									value="${ISF_AII_Subsidy_DIVSCSTF}" disabled="disabled"
									name="ISF_AII_Subsidy_DIVSCSTF"></td>
							</tr>
							<tr>
								<td style="width: 50%;"><strong>Interest
										Subsidy on loans for industrial research, quality
										improvement, etc.</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Loan_Subsidy}" disabled="disabled"
									name="ISF_Loan_Subsidy"></td>
							</tr>
							<tr>
								<td><strong>Total Interest Subsidies</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Total_Int_Subsidy}" disabled="disabled"
									name="ISF_Total_Int_Subsidy"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12">
				<div class="table-responsive">
					<table
						class="table table-stripped table-bordered loc-stage-table">
						<tbody>
							<tr>
								<td style="width: 50%;">Reimbursement of Disallowed
									Input Tax Credit on plant, building materials, and other
									capital goods.</td>
								<td><input type="text" class="form-control"
									value="${ISF_Tax_Credit_Reim}" disabled="disabled"
									name="ISF_Tax_Credit_Reim"></td>
							</tr>
							<tr>
								<td>Exemption from Electricity Duty from captive
									power for self-use</td>
								<td><input type="text" class="form-control"
									value="${ISF_EX_E_Duty}" disabled="disabled"
									name="ISF_EX_E_Duty"></td>
							</tr>
							<tr>
								<td>Exemption from Electricity duty on power drawn
									from power companies</td>
								<td><input type="text" class="form-control"
									value="${ISF_EX_E_Duty_PC}" disabled="disabled"
									name="ISF_EX_E_Duty_PC"></td>
							</tr>
							<tr>
								<td>Exemption from Mandi Fee</td>
								<td><input type="text" class="form-control"
									value="${ISF_EX_Mandee_Fee}" disabled="disabled"
									name="ISF_EX_Mandee_Fee"></td>
							</tr>
							<tr>
								<td>Industrial units providing employment to
									differently abled workers will be provided payroll
									assistance of Rs. 500 per month for each such worker.</td>
								<td><input type="text" class="form-control"
									value="${ISF_Indus_Payroll_Asst}" disabled="disabled"
									name="ISF_Indus_Payroll_Asst"></td>
							</tr>
							<tr>
								<td><strong>Total Other Incentives</strong></td>
								<td><input type="text" class="form-control"
									value="${Total_Other_Incentive}" disabled="disabled"
									name="Total_Other_Incentive"></td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr id="showHide1">
								<td>Do you have any other Additional Request</td>
								<td><input type="text" class="form-control"
									value="${othAddRequest1}" disabled="disabled"
									name="othAddRequest1"></td>
							</tr>
			
							<tr id="showHide2">
								<td><strong>Do you want to avail customised
										incentives</strong></td>
								<td><input type="text" class="form-control"
									value="${ISF_Cstm_Inc_Status}" disabled="" name=""></td>
							</tr>

							<tr id="showHide3">
								<td colspan="2">
									<table
										class="table table-stripped table-bordered directors-table"
										id="customFields">
										<c:if test="${ not empty availCustomisedDetailsList}">
											<thead>
												<tr>
													<th>Incentive Type</th>
													<th>Amount</th>
													<th>Additional Request</th>
													<th>Type Details of Customised Incentives</th>
												</tr>
											</thead>
										</c:if>
										<tbody>
											<c:forEach var="list"
												items="${availCustomisedDetailsList}"
												varStatus="counter">
												<tr>
													<td>${list.addIncentiveType}</td>
													<td>${list.addAmt}</td>
													<td>${list.othAddRequest}</td>
													<td>${list.typeDtlCusIncentives}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</td>
							</tr>

							<tr id="showHide4">
								<td>Upload Related Document</td>
								<td><a href="./downloadDocIncentive?fileName=${isfCustIncDocName}"><small>${isfCustIncDocName}</small></a>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- <div class="isf-form mt-4">
		<hr class="mt-2">
		<div class="row">
			<div class="col-sm-4">
				<button onclick="closeCurrentTab()"
					class="btn btn-outline-danger mb-3">Close</button>
			</div>
			<div class="col-sm-8 text-right">
				<a href="./editApplicantForm"
					onclick="return confirm('Are you sure you want edit records?')"
					class="btn btn-outline-info mb-3"><i class="fa fa-edit"></i>
					Edit</a>
				<button class="btn btn-outline-success mb-3" id="formId1">
					<i class="fa fa-download"></i>Download Form
				</button>
				<button type="button" id="printMethod"
					class="btn btn-outline-secondary mb-3">
					<i class="fa fa-print"></i> e-Print
				</button>
			</div>
		</div>
	</div> -->
</div>