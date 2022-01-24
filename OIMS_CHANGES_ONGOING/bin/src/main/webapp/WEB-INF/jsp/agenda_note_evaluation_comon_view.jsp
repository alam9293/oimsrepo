<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	$(function() {
		var ctypeSMALLselect = '${ctypeSMALL}';

		if (ctypeSMALLselect == 'No') {
			$(".in-case-of-small-row").hide();
		}

		var ctypeLARGEselect = '${ctypeLARGE}';
		if (ctypeLARGEselect == 'No') {
			$(".in-case-of-large-row").hide();
		}

		var ctypeMEGAselect = '${ctypeMEGA}';
		if (ctypeMEGAselect == 'No') {
			$(".in-case-of-mega-row").hide();
		}

		var ctypeSUPERMEGAselect = '${ctypeSUPERMEGA}';
		if (ctypeSUPERMEGAselect == 'No') {
			$(".in-case-of-supermega-row").hide();
		}

		var phase = '${phsWsAply}';

		if (phase == 'No') {
			$(".phase").hide();
		}

		var ctype = '${category}';
		if (ctype == 'Mega' || ctype == 'Mega Plus' || ctype == 'Super Mega') {
			document.getElementById("percent").innerHTML = '300';

		} else {
			document.getElementById("percent").innerHTML = '100';
			$(".in-case-of-inMMSM-row").hide();
		}

		var regionName = '${region}';
		var districtName = '${district}';

		if (regionName == 'Bundelkhand' || regionName == 'Poorvanchal') {
			document.getElementById("sdepercent").innerHTML = '100';
			document.getElementById("sdrpercent").innerHTML = '100';

		}
		if (regionName == 'Paschimanchal') {
			document.getElementById("sdepercent").innerHTML = '75';
			document.getElementById("sdrpercent").innerHTML = '75';
		}
		if (regionName == 'Madhyanchal') {
			document.getElementById("sdepercent").innerHTML = '75';
			document.getElementById("sdrpercent").innerHTML = '75';

		}

		if (districtName == 'GAUTAM BUDH NAGAR' || districtName == 'GHAZIABAD') {
			document.getElementById("sdepercent").innerHTML = '50';
			document.getElementById("sdrpercent").innerHTML = '50';
		}

	});

	window.onload = function() {
		if ('${flag}' === 'false') {
			document.getElementById('showHide1').style.display = 'none';
			document.getElementById('showHide2').style.display = 'none';
		} else {
			document.getElementById('showHide1').style.display = 'block';
			document.getElementById('showHide2').style.display = 'block';
		}

	/* 	if ('${flag1}' === 'false1') {
			document.getElementById('showHide3').style.display = 'table-row';

		} else {
			document.getElementById('showHide3').style.display = 'none';

		} */

	}

	function preparedAgendaNoteValidation() {
		<c:if test="${empty flag}">
		alert("Please select Applicant Record.");
		return false
		</c:if>

	}
	function validate(value) {
		document.getElementById("appliId").value = value;
	}
	$(document).ready(CostasperDPR);
	$(document).on("keyup", CostasperDPR);

	function CostasperDPR() {
		var sum = 0;
		$(".CostasperDPR").each(function() {
			sum += +$(this).val();
		});
		$(".TotalCostasperDPR").val(sum);
	}

	$(document).ready(CostasperIIEPP);
	$(document).on("keyup", CostasperIIEPP);

	function CostasperIIEPP() {
		var sum = 0;
		$(".CostasperIIEPP").each(function() {
			sum += +$(this).val();
		});
		$(".TotalCostasperIIEPP").val(sum);
	}

	$(document).ready(MFCostasperDPR);
	$(document).on("keyup", MFCostasperDPR);

	function MFCostasperDPR() {
		var sum = 0;
		$(".MFCostasperDPR").each(function() {
			sum += +$(this).val();
		});
		$(".TotalMFCostasperDPR").val(sum);
	}

	$(document).ready(MFCostasperIIEPP);
	$(document).on("keyup", MFCostasperIIEPP);

	function MFCostasperIIEPP() {
		var sum = 0;
		$(".MFCostasperIIEPP").each(function() {
			sum += +$(this).val();
		});
		$(".TotalMFCostasperIIEPP").val(sum);
	}

	function Save() {
		var r = confirm("Are you Sure Want to Save the Prepare Agenda Note?");

		if (r == true) {
			alert("Prepare Agenda Note Saved Successfully");
		} else {
			return false
		}

	}

	function showYesOrNo1() {
		var natureOfProject = '${natureOfProject}';
		if (natureOfProject === 'NewProject') {

			$(".hide-tbl-row").hide();
		}

		else
			$(".hidep-tbl-row").hide();

	}
	$(document).on("change", showYesOrNo1);
	$(document).ready(showYesOrNo1);

	function showYesOrNo2() {
		var ISF_Claim_Reim = '${incentiveDeatilsData.ISF_Claim_Reim}';
		if (ISF_Claim_Reim == '') {
			//alert("hi");
			$(".ISF_Claim_Reim-row").hide();
		}
		var ISF_Reim_SCST = '${incentiveDeatilsData.ISF_Reim_SCST}';
		//if( iSF_Reim_BPLW == '')
		if (ISF_Reim_SCST == '') {
			//alert(ISF_Reim_SCST);
			$(".ISF_Reim_SCST-row").hide();
		}

		var ISF_Reim_FW = '${incentiveDeatilsData.ISF_Reim_FW}';
		if (ISF_Reim_FW == '') {
			//alert("hi");
			$(".ISF_Reim_FW-row").hide();
		}

		var iSF_Reim_BPLW = '${incentiveDeatilsData.ISF_Reim_BPLW}';
		if (iSF_Reim_BPLW == '') {
			//alert("hi");
			$(".ISF_Reim_BPLW-row").hide();
		}

		var ISF_Stamp_Duty_EX = '${incentiveDeatilsData.ISF_Stamp_Duty_EX}';
		if (ISF_Stamp_Duty_EX == '') {
			//alert("hi");
			$(".ISF_Stamp_Duty_EX-row").hide();
		}

		var ISF_Amt_Stamp_Duty_Reim = '${incentiveDeatilsData.ISF_Amt_Stamp_Duty_Reim}';
		if (ISF_Amt_Stamp_Duty_Reim == '') {
			//alert("hi");
			$(".ISF_Amt_Stamp_Duty_Reim-row").hide();
		}

		var ISF_Additonal_Stamp_Duty_EX = '${incentiveDeatilsData.ISF_Additonal_Stamp_Duty_EX}';
		if (ISF_Additonal_Stamp_Duty_EX == '') {
			//alert("hi");
			$(".ISF_Additonal_Stamp_Duty_EX-row").hide();
		}

		var ISF_Epf_Reim_UW = '${incentiveDeatilsData.ISF_Epf_Reim_UW}';
		if (ISF_Epf_Reim_UW == '') {
			//alert("hi");
			$(".ISF_Epf_Reim_UW-row").hide();
		}

		var ISF_Add_Epf_Reim_SkUkW = '${incentiveDeatilsData.ISF_Add_Epf_Reim_SkUkW}';
		if (ISF_Add_Epf_Reim_SkUkW == '') {
			//alert("hi");
			$(".ISF_Add_Epf_Reim_SkUkW-row").hide();
		}

		var ISF_Add_Epf_Reim_DIVSCSTF = '${incentiveDeatilsData.ISF_Add_Epf_Reim_DIVSCSTF}';
		if (ISF_Add_Epf_Reim_DIVSCSTF == '') {
			//alert("hi");
			$(".ISF_Add_Epf_Reim_DIVSCSTF-row").hide();
		}

		var ISF_Cis = '${incentiveDeatilsData.ISF_Cis}';
		if (ISF_Cis == '') {
			//alert("hi");
			$(".ISF_Cis-row").hide();
		}

		var ISF_ACI_Subsidy_Indus = '${incentiveDeatilsData.ISF_ACI_Subsidy_Indus}';
		if (ISF_ACI_Subsidy_Indus == '') {
			//alert("hi");
			$(".ISF_ACI_Subsidy_Indus-row").hide();
		}

		var ISF_Infra_Int_Subsidy = '${incentiveDeatilsData.ISF_Infra_Int_Subsidy}';
		if (ISF_Infra_Int_Subsidy == '') {
			//alert("hi");
			$(".ISF_Infra_Int_Subsidy-row").hide();
		}

		var ISF_AII_Subsidy_DIVSCSTF = '${incentiveDeatilsData.ISF_AII_Subsidy_DIVSCSTF}';
		if (ISF_AII_Subsidy_DIVSCSTF == '') {
			//alert("hi");
			$(".ISF_AII_Subsidy_DIVSCSTF-row").hide();
		}

		var ISF_Loan_Subsidy = '${incentiveDeatilsData.ISF_Loan_Subsidy}';
		if (ISF_Loan_Subsidy == '') {
			//alert("hi");
			$(".ISF_Loan_Subsidy-row").hide();
		}

		var ISF_Tax_Credit_Reim = '${incentiveDeatilsData.ISF_Tax_Credit_Reim}';
		if (ISF_Tax_Credit_Reim == '') {
			//alert("hi");
			$(".ISF_Tax_Credit_Reim-row").hide();
		}

		var ISF_EX_E_Duty = '${incentiveDeatilsData.ISF_EX_E_Duty}';
		if (ISF_EX_E_Duty == '') {
			//alert("hi");
			$(".ISF_EX_E_Duty-row").hide();
		}

		var ISF_EX_E_Duty_PC = '${incentiveDeatilsData.ISF_EX_E_Duty_PC}';
		if (ISF_EX_E_Duty_PC == '') {
			//alert("hi");
			$(".ISF_EX_E_Duty_PC-row").hide();
		}

		var ISF_EX_Mandee_Fee = '${incentiveDeatilsData.ISF_EX_Mandee_Fee}';
		if (ISF_EX_Mandee_Fee == '') {
			//alert("hi");
			$(".ISF_EX_Mandee_Fee-row").hide();
		}

		var ISF_Indus_Payroll_Asst = '${incentiveDeatilsData.ISF_Indus_Payroll_Asst}';
		if (ISF_Indus_Payroll_Asst == '') {
			//alert("hi");
			$(".ISF_Indus_Payroll_Asst-row").hide();
		}
	}
	$(document).ready(showYesOrNo2);
</script>
<div class="col-md-12 mt-4">
	<div class="form-group">
		<label>Add Promoters &amp; Proposed project Details</label>
		<textarea name="propsProdtDetailObserv" class="form-control" rows="5"
			disabled="disabled">${propsProdtDetailObserv}</textarea>
	</div>
</div>
<div class="row" id="showHide1">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
				<div class="table-responsive">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Eligibility Criteria</th>
								<th>Compliance</th>
								<th>Observations By PICUP</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td style="width: 33%;"><label class="table-heading">New
										Project or Expansion / Diversification</label></td>
								<td style="width: 33%;"><input type="text"
									readonly="readonly" class="form-control"
									value="${natureOfProject}" disabled="disabled"></td>
								<td><textarea name="projectObserv" class="form-control"
										rows="2" disabled="disabled">${projectObserv}</textarea></td>



							</tr>
							<tr class="hidep-tbl-row">
								<td><label class="table-heading">Product Details</label></td>
								<td><input name="productDetails" type="text"
									maxlength="500" value="${newProjProdDetail}"
									class="form-control" disabled="disabled"></td>
								<td><textarea name="prodDetailObserv" class="form-control"
										rows="2" disabled="disabled">${prodDetailObserv}</textarea></td>
							</tr>
							<tr>
								<td colspan="3" class="p-4">
									<div class="table-responsive mt-3" id="productsDetailsTbl">
										<table class="table table-stripped table-bordered"
											id="edittable">
											<thead>
												<c:if test="${not empty evaluateExistNewProjDetailsList}">
													<tr>

														<th>Existing Products</th>
														<th>Existing Installed Capacity</th>
														<th>Proposed Products</th>
														<th>Proposed Installed Capacity</th>
														<th>Existing Gross Block</th>
														<th>Proposed Gross Block</th>
													</tr>
												</c:if>
											</thead>
											<tbody class="existing-proposed-products">
												<c:forEach var="list"
													items="${evaluateExistNewProjDetailsList}"
													varStatus="counter">
													<tr>
														<td hidden=""><input type="text" name="epdId"
															value="${list.epdId}" /></td>
														<td><input id="epdExisProductsId"
															name="epdExisProducts" value="${list.epdExisProducts}"
															class="form-control" disabled="disabled"></td>
														<td><input type="text" id="epdExisInstallCapacityId"
															name="epdExisInstallCapacity"
															value="${list.epdExisInstallCapacity}"
															class="form-control" disabled="disabled"></td>
														<td><input type="text" id="epdPropProductsId"
															name="epdPropProducts" value="${list.epdPropProducts}"
															class="form-control" disabled="disabled"></td>
														<td><input type="text" id="epdPropInstallCapacityId"
															name="epdPropInstallCapacity"
															value="${list.epdPropInstallCapacity}"
															class="form-control" disabled="disabled"></td>
														<td><input type="text" id="epdExisGrossBlockId"
															name="epdExisGrossBlock"
															value="${list.epdExisGrossBlock}" class="form-control"
															disabled="disabled"></td>
														<td><input type="text" id="epdPropoGrossBlockId"
															name="epdPropoGrossBlock"
															value="${list.epdPropoGrossBlock}" class="form-control"
															disabled="disabled"></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<label class="table-heading">Location</label>
										</div>
									</div>

									<div class="table-responsive mt-3">
										<table class="table table-stripped table-bordered">
											<tbody>
												<tr>
													<td style="width: 33%;">Full Registered Address of
														Company</td>
													<td style="width: 33%;"><input type="text"
														name="fullAddress" class="form-control" id="fullAddress"
														disabled="disabled" value="${businessAddress}" /></td>
													<td rowspan="4"><textarea name="locationObserv"
															placeholder="Observations Comment By PICUP"
															class="form-control" rows="10" disabled="disabled">${locationObserv}</textarea></td>
												</tr>
												<tr>
													<td>Full Address of Unit</td>
													<td><input type="text" class="form-control"
														value="${fullAddrs}" name=""
														disabled="disabled"></td>
												</tr>
												<tr>
													<td>District</td>
													<td><input type="text" name="districtName"
														class="form-control" disabled="disabled" id="districtName"
														value="${district}"></td>
												</tr>
												<tr>
													<td>Region</td>
													<td><input type="text" name="resionName"
														class="form-control" id="resionName" disabled="disabled"
														value="${region}"> <span id="resionName"
														class="text-danger color:red"></span></td>
												</tr>
											</tbody>
										</table>
									</div>
								</td>
							</tr>

							<tr>
								<td colspan="3">
									<div class="table-responsive">
										<label class="table-heading mb-3">Director Details</label>
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>Name</th>
													<th>Designation</th>
													<th>% Equity</th>
													<th>Mobile No.</th>
													<th>Address</th>
													<th>Email</th>
													<th>Gender</th>
													<th>Category</th>
													<th>Divyang</th>
													<th>PAN No.</th>
													<th>DIN</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="list" items="${ProprietorDetailsList}">
													<tr>
														<td>${list.directorName}</td>
														<td>${list.designation}</td>
														<td>${list.proprietorDetailsaddress}</td>
														<td>${list.mobileNo}</td>
														<td>${list.equity}</td>
														<td>${list.email}</td>
														<td>${list.gender}</td>
														<td>${list.category}</td>
														<td>${list.div_Status}</td>
														<td>${list.panCardNo}</td>
														<td>${list.din}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<textarea name="dirDetailsObserv"
											placeholder="Observations Comment By PICUP"
											class="form-control" rows="3" disabled="disabled">${dirDetailObserv}</textarea>
									</div>
								</td>
							</tr>

							<tr>
								<td colspan="3" class="p-4">

									<div class="row">
										<div class="col-sm-6">
											<label class="table-heading">Employment</label>
										</div>

									</div>

									<div class="table-responsive mt-3">
										<table class="table table-stripped table-bordered">
											<tbody>
												<tr>
													<td style="width: 50%;">Total Direct Employment</td>
													<td><input type="text" class="form-control"
														value="${grossTotalMaleandFemaleEmployment}"
														readonly="readonly" name="" id="totalEmploymentId"></td>
													<td><textarea name="totalDetailObserv"
															placeholder="Observations Comment By PICUP"
															class="form-control" rows="2" disabled="disabled">${totalEmpDetailObserv}</textarea></td>
												</tr>
												<tr>
													<td colspan="3">
														<div class="table-responsive">
															<table class="table table-stripped table-bordered"
																id="customFields" tabindex="1">

																<thead>
																	<tr>
																		<th>Skilled/Unskilled</th>
																		<th>Male</th>
																		<th>Female</th>
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
																		items="${skilledUnSkilledEmployemntList}"
																		varStatus="counter">
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
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</td>
							</tr>

							<c:if test="${natureOfProject=='Expansion'}">
								<tr>
									<td><label class="table-heading">If Expansion /
											Diversification whether proposed gross block more than 25% of
											the existing gross block <a
											href="./downloadExistProjDoc?fileName=${caCertificateFileName}"><small>(View
													File)</small></a>
									</label></td>
									<td><input type="text" class="form-control" value="Yes"
										readonly="readonly" id="pergrosblockId"></td>
									<td><textarea name="expDivfObserv"
											placeholder="Observations Comment By PICUP"
											class="form-control" rows="2" disabled="disabled">${expDivfObserv}</textarea></td>
								</tr>
							</c:if>


							<tr>
								<td><label class="table-heading">Category of
										Industrial Undertaking</label></td>
								<td><input type="text" name="invIndType"
									class="form-control" value="${category}" readonly="true"
									id="categoryId"></td>
								<td><textarea name="catIndusUndtObserv"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2" disabled="disabled">${invcatundtakObserv}</textarea></td>

							</tr>

							<tr>
								<td><label class="table-heading">Proposed Total
										Investment in the project and Investment in Plant & Machinery</label></td>
								<td><textarea class="form-control" rows="2"
										disabled="disabled">Plant & Machinery Cost ${invPlantAndMachCost} , Cost of Project ${InvTPC}  </textarea>
								</td>
								<td><textarea name="propsPlntMcnryCostObserv"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2" disabled="disabled">${propsPlntMcnryCostObserv}</textarea></td>
							</tr>


							<tr>
								<td><label class="table-heading">Opted Cut-off date
										investment</label></td>
								<td><input type="text" name="invCommenceDate"
									value="${optedcufoffdate}" class="form-control" readonly="true"></td>
								<td><textarea name="optcutofdateobserv"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2" disabled="disabled">${optcutofdateObserv}</textarea></td>
							</tr>
							<tr>
								<td><label class="table-heading">Date of
										Commencement of Commercial Production</label></td>
								<td><input type="text" name="propCommProdDate"
									value="${invPropCommProdDate}" class="form-control"
									readonly="true"></td>
								<td><textarea name="dateofComProdObserv"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2" disabled="disabled">${dateofcumProdObserv} </textarea></td>
							</tr>

							<c:if test="${natureOfProject=='Expansion'}">
								<tr>
									<td colspan="2"><label class="table-heading">Detailed
											Project Report prepared by External Consultant/Chartered
											Accountants <a
											href="./downloadExistProjDoc?fileName=${enclDetProRepFileName}"><small>(View
													File)</small></a>
									</label></td>
									<td><textarea name="detailProjReportObserv"
											placeholder="Observations Comment By PICUP"
											class="form-control" rows="2" disabled="disabled">${projreportObserv} </textarea></td>
								</tr>
							</c:if>


							<c:if test="${natureOfProject=='NewProject'}">
								<tr>
									<td colspan="2"><label class="table-heading">Detailed
											Project Report prepared by External Consultant/Chartered
											Accountants <a
											href="./downloadNewProjDoc?fileName=${enclDetProRepFileName}"><small>(View
													File)</small></a>
									</label></td>
									<td><textarea name="detailProjReportObserv"
											placeholder="Observations Comment By PICUP"
											class="form-control" rows="2" disabled="disabled">${projreportObserv} </textarea></td>
								</tr>
							</c:if>


							<c:if test="${natureOfProject!='NewProject'}">
								<tr>
									<td><label class="table-heading">List of Assets</label>
										<p>
											Whether List of Assets (in Expansion/Diversification Cases
											only) certified by C.A. Submitted. <a
												href="./downloadExistProjDoc?fileName=${charatEngFileName}">View
												File</a>
										</p></td>
									<td><textarea name="listAssets" class="form-control"
											rows="3" disabled="disabled">${listAssets}</textarea></td>
									<td><textarea name="listAssetsObserv"
											path="listAssetsObserv" disabled="disabled"
											placeholder="Observations Comment By PICUP"
											value="${listAssetsObserv}" class="form-control" rows="2">${listAssetsObserv}</textarea></td>
								</tr>
							</c:if>

							
							<tr>
								<td><label class="table-heading">Undertaking</label>
									<p>
										Whether notarized undertaking as per format placed at Annexure
										I-A (as per format of IIEPP Rules- 2017) on Stamp Paper of Rs.
										10/-submitted. <a
											href="./downloadBusinessEntityDoc?fileName=${bodDocFname}">View
											File</a>
									</p></td>
								<td><select name="anexurUndertk" class="form-control"
									disabled="disabled">
										<option value="Yes" selected="">Yes</option>
										<option value="No">No</option>
								</select></td>
								<td><textarea name="anexurUndertkObserv"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2" disabled="disabled">${anexurUndertkObserv}</textarea></td>
							</tr>

							<tr>
								<td colspan="3" class="p-4">
									<div class="row">
										<div class="col-sm-12">
											<label class="table-heading">Capital Investment
												Details</label>
										</div>
									</div>

									<div class="table-responsive mt-3">
										<table class="table table-stripped table-bordered">
											<tbody>
												<tr>
													<td>Proposed Capital Investment</td>
													<td><input type="text" id="invFci" name="invFci"
														class="form-control" value="${InvFCI}"
														onkeypress="return validateNumberField()" maxlength="12"
														readonly="true"></td>
													<td><textarea name="propCapInvObserv"
															placeholder="Observations Comment By PICUP"
															class="form-control" rows="2" disabled="disabled">${propCapInvObserv}</textarea></td>
												</tr>
												<tr>
													<td>Total Cost of Project</td>
													<td><input type="text" id="invtpc"
														name="invTotalProjCost" class="form-control"
														value="${InvTPC}"
														onkeypress="return validateNumberField()" maxlength="12"
														readonly="true"></td>
													<td><textarea name="totlCostProjObserv"
															placeholder="Observations Comment By PICUP"
															class="form-control" rows="2" disabled="disabled">${totlcostprojObserv}</textarea></td>
												</tr>
											</tbody>
										</table>
									</div>

									<div class="row">
										<div class="col-sm-6">
											<label class="table-heading">Break up of proposed
												capital Investment</label>
										</div>
									</div>

									<div class="table-responsive mt-3">
										<table id="BreakUpTable"
											class="table table-stripped table-bordered">
											<thead>
												<tr>
													<!-- <th>Select</th> -->
													<th>Item</th>
													<th>Consider as FCI</th>
													<th>Investment as per DPR or Document Provided</th>
													<th>Capital Investment Eligible as per IIEPP Policy
														Rule - 2017</th>
													<th>PICUP Officer's Remarks</th>

												</tr>
											</thead>
											<tbody class="add-from-here">
												<tr>

													<td>Land Cost</td>
													<td><select class="form-control" name="landcostFci"
														id="landcostfci" disabled="true">
															<option value="Yes">Yes</option>
															<option value="No">No</option>
													</select></td>
													<td><input class="CostasperDPR form-control"
														id="dprland" type="text" name="" value="${InvLC}"
														readonly="readonly"></td>
													<td><input type="text" id="iiepland"
														name="landcostIIEPP" maxlength="12"
														onkeypress="return validateNumberField()" value="${invLandcostIIEPP}"
														class="CostasperIIEPP form-control" readonly="readonly"></td>
													<td><input type="text" class="form-control"
														name="landcostRemarks" disabled="disabled"
														value="${invLandcostRemarks}" maxlength="1000"></td>

												</tr>
												<tr>

													<td>Building,Site Development & Civil Works Cost</td>
													<td><select class="form-control" path="buildingFci"
														id="buildingfci" disabled="true">
															<option value="Yes">Yes</option>
															<option value="No">No</option>
													</select></td>
													<td><input type="text"
														class="CostasperDPR form-control" name=""
														value="${InvBuiildC}" readonly="readonly"></td>
													<td><input type="text" name="buildingIIEPP"
														value="${invBuildingIIEPP}" maxlength="12"
														disabled="disabled"
														onkeypress="return validateNumberField()"
														class="CostasperIIEPP form-control" name=""></td>
													<td><input type="text" disabled="disabled"
														name="buildingRemarks" value="${invBuildingRemarks}"
														maxlength="1000" class="form-control"></td>
												</tr>
												<tr>
													<td>Plant & Machinery Cost</td>
													<td><select class="form-control"
														name="plantAndMachFci" id="plantfci" disabled="true">
															<option value="Yes">Yes</option>
															<option value="No">No</option>
													</select></td>
													<td><input type="text"
														class="CostasperDPR form-control" name=""
														value="${invPlantAndMachCost}" readonly="readonly"></td>
													<td><input type="text" name="plantAndMachIIEPP"
														disabled="disabled" value="${invPlantAndMachIIEPP}"
														maxlength="12" onkeypress="return validateNumberField()"
														class="CostasperIIEPP form-control"></td>
													<td><input type="text" disabled="disabled"
														name="plantAndMachRemarks"
														value="${invPlantAndMachRemarks}" maxlength="1000"
														class="form-control" name=""></td>
												</tr>
												<tr>
													<td>Miscellaneous Fixed Asset(MFA)</td>
													<td><select class="form-control" name="fixedAssetFci"
														id="fixassetfci" disabled="true">
															<option value="Yes">Yes</option>
															<option value="No">No</option>
													</select></td>
													<td><input type="text"
														class=" CostasperDPR form-control" name=""
														value="${invMisFixCost}" readonly="readonly"></td>
													<td><input type="text" name="fixedAssetIIEPP"
														disabled="disabled" value="${invFixedAssetIIEPP}"
														maxlength="12" onkeypress="return validateNumberField()"
														class="CostasperIIEPP form-control"></td>
													<td><input type="text" disabled="disabled"
														name="fixedAssetRemarks" value="${invFixedAssetRemarks}"
														maxlength="1000" class="form-control"></input></td>
												</tr>


												<c:if test="${not empty evalCapitalInvList}">

													<c:forEach var="eciObj" items="${evalCapitalInvList}"
														varStatus="counter">
														<!-- Iterating the list using JSTL tag  -->
														<c:if
															test="${not empty eciObj.eciItem  && not empty eciObj.eciDPRInvest && not empty eciObj.eciIIEPPInvest }">
															<tr>
																<td><input type="text" value="${eciObj.eciItem}"
																	id="itemName" maxlength="500"
																	onblur="removeAddMoreItemsErrMsg()"
																	class="form-control" readonly="readonly"></input></td>


																<td><select class="form-control" id="ConsiderasFCI"
																	disabled="true">
																		<option>${eciObj.eciIsFci}</option>
																		<%-- <form:option value="No">No</form:option> --%>
																</select></td>
																<td><input type="text"
																	value="${eciObj.eciDPRInvest}" id="DPRInput"
																	maxlength="12"
																	onkeypress="return validateNumberField()"
																	onblur="removeAddMoreItemsErrMsg()"
																	class=" CostasperDPRECI form-control"
																	readonly="readonly"></input> <span id="dprinput"
																	class="text-danger"></span></td>
																<td><input type="text"
																	value="${eciObj.eciIIEPPInvest}" id="IIEPPInput"
																	maxlength="12" readonly="readonly"
																	onkeypress="return validateNumberField()"
																	onblur="removeAddMoreItemsErrMsg()"
																	class="CostasperIIEPP form-control"></input> <span
																	id="iieppinput" class="text-danger"></span></td>
																<td><input value="${eciObj.eciPICUP_Remarks}"
																	maxlength="1000" class="form-control" id="PICUPRemarks"
																	rows="2" readonly="readonly"></input></td>


															</tr>
														</c:if>
													</c:forEach>
												</c:if>
											</tbody>




											<tfoot>
												<tr hidden="">
													<td><input name="eciItem" type="text" id="itemName"
														maxlength="500" onblur="removeAddMoreItemsErrMsg()"
														class="form-control"><span id="itemname"
														class="text-danger"></span></td>
													<td><select name="eciIsFci" class="form-control"
														id="ConsiderasFCII">
															<option value="Yes">Yes</option>
															<ption value="No">No
															</option>
													</select></td>
													<td><input type="text" name="eciDPRInvest"
														id="DPRInput" maxlength="12"
														onkeypress="return validateNumberField()"
														onblur="removeAddMoreItemsErrMsg()"
														class="CostasperDPRECI form-control"></input> <span
														id="dprinput" class="text-danger"></span></td>
													<td><input type="text" name="eciIIEPPInvest"
														id="IIEPPInput" maxlength="12"
														onkeypress="return validateNumberField()"
														onblur="removeAddMoreItemsErrMsg()"
														class="CostasperIIEPP form-control"> <span
														id="iieppinput" class="text-danger"></span></td>
													<td><textarea name="eciPICUP_Remarks"
															class="form-control" id="PICUPRemarks" rows="2"></textarea>
													</td>
													<td colspan="6" class="text-right">
														<button formaction="addEvaluateCapInvest"
															onclick="return validateAddMoreItems()"
															class="btn btn-outline-success ">Add</button>
													</td>
												</tr>


												<tr>
													<td colspan="2" align="right"></td>
													<td><strong>Total:</strong><input type="text"
														readonly="readonly" class="TotalCostasperDPR form-control"
														name="dprtotal" id="TotalCostasperDPR1"></td>
													<td><strong>Total:</strong><input
														class="TotalCostasperIIEPP form-control"
														readonly="readonly" name=""></td>
													<td colspan="2"></td>
												</tr>

											</tfoot>




										</table>
									</div> <c:if test="${not empty evalMeanofFinanceList}">
										<div class="row">
											<div class="col-sm-6">
												<label class="table-heading">Means of Financing <a
													href="./downloadInvestmentDoc?fileName=${invFileName}">(View
														File)</a>
												</label>
											</div>
										</div>


										<div class="row">
											<div class="col-sm-12">
												<div class="table-responsive">
													<table class="table table-bordered">
														<thead>
															<tr>
																<th>Phase Number</th>
																<th>Item Name</th>
																<th>Investment Amount</th>

															</tr>
														</thead>
														<tbody>
															<c:forEach var="emf" items="${evalMeanofFinanceList}"
																varStatus="counter">
																<tr>
																	<td>${emf.emfphaseNo}</td>
																	<td>${emf.emfphsItemName}</td>
																	<td>${emf.emfphsInvestAmt}</td>

																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
												<textarea name="mofObserv"
													placeholder="Observations Comment By PICUP"
													class="form-control" rows="4" disabled="disabled">${mofObserv}</textarea>
											</div>
										</div>
									</c:if>
								</td>
							</tr>

							<tr>
								<td width="33%"><label class="table-heading">Whether
										the company have filed LOI/IEM registration with the
										Department of Industrial Policy and Promotion GoI. <a
										href="./downloadInvestmentDoc?fileName=${regiOrLicenseFileName}">(View
											File)</a>
								</label></td>
								<td width="33%">
									<table class="table">
										<tr>
											<td><input type="text" class="form-control"
												value="${regorlic}" readonly="readonly" name=""></td>
											<td><input type="text" name="iemNumber"
												value="${invIemNumber}" maxlength="50" disabled="disabled"
												class="form-control" placeholder="Enter IEM Number"></input></td>
										</tr>
									</table>

								</td>
								<td width="33%"><textarea name="IemRegObserv"
										placeholder="Observations Comment By PICUP"
										disabled="disabled" class="form-control" rows="2">${IemRegObserv}</textarea></td>



							</tr>

							<tr>
								<td><label class="table-heading">Industrial
										undertaking does not have more than 50% equity of any
										government/government undertaking</label></td>
								<td><select name="govtEquity" id="govtequity"
									class="form-control" disabled="true">
										<option value="Yes">Yes</option>
										<option value="No">No</option>
								</select></td>
								<td><textarea name="indusUntkObserv" disabled="disabled"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2">${IndusUndrtkObserv}</textarea></td>

							</tr>

							<tr>
								<td colspan="3" class="p-4">

									<div class="row">
										<div class="col-sm-6">
											<label class="table-heading">Eligible Investment
												Period</label>
										</div>

									</div>

									<div class="table-responsive mt-3">
										<table class="table table-stripped table-bordered">
											<tbody>
												<tr class="in-case-of-small-row">

													<td>In case of small & medium industrial undertakings,
														the period commencing from cutoff date falling in the
														effective period of these Rules upto 3 years or till the
														Proposed Date of Commencement of Commercial Production,
														whichever is earlier. Such cases will also be covered in
														which the cut-off date is within the period immediately
														preceding 3 years from the effective date, subject to the
														condition that commercial production in such cases
														commences on or after the effective date. In any case, the
														maximum eligible investment period shall not be more than
														3 years.</td>

													<td style="width: 33%;"><input type="text"
														class="form-control" value="${ctypeSMALL}"
														readonly="readonly"> <%-- <c:choose>
																									<c:when test="${ctypeSMALL eq 'Yes'}">
																										<option value="${ctypeSMALL}"
																											selected="selected">${ctypeSMALL}</option>
																										<option value="No">No</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeSMALL}"
																											selected="selected">${ctypeSMALL}</option>
																									</c:otherwise>
																								</c:choose> 
																						</select>--%></td>
													<td style="width: 33%;"><textarea
															placeholder="Observations Comment By PICUP"
															class="form-control" rows="2" disabled="disabled"></textarea></td>
												</tr>
												<tr class="in-case-of-large-row">

													<td>in case of large industrial undertakings, the
														period commencing from cutoff date falling in the
														effective period of these Rules upto 4 years or till the
														Proposed Date of Commencement of Commercial Production,
														whichever is earlier. Such cases will also be covered in
														which the cut-off date is within the period immediately
														preceding 4 years from the effective date, subject to the
														condition that commercial production in such cases
														commences on or after the effective date. In any case, the
														maximum eligible investment period shall not be more than
														4 years.</td>
													<td style="width: 33%;"><input type="text"
														class="form-control" value="${ctypeLARGE}"
														readonly="readonly"></td>
													<%-- <td><select class="form-control" readonly="readonly">
																								<c:choose>
																									<c:when test="${ctypeLARGE eq 'Yes'}">
																										<option value="${ctypeLARGE}"
																											selected="selected">${ctypeLARGE}</option>
																										<option value="No">No</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeLARGE}"
																											selected="selected">${ctypeLARGE}</option>
																									</c:otherwise>
																								</c:choose>
																						</select></td> --%>
													<td style="width: 33%;"><textarea
															name="eligblInvPerdLargeObserv" disabled="disabled"
															placeholder="Observations Comment By PICUP"
															class="form-control" rows="2">${eligblInvPerdLargeObserv}</textarea></td>
												</tr>
												<tr class="in-case-of-mega-row">
													<!-- <td>
																					<div class="custom-control custom-checkbox mb-4">
																						<input type="checkbox"
																							class="custom-control-input" id="EIP3"
																							name="SlectItem"> <label
																							class="custom-control-label" for="EIP3"></label>
																					</div>
																				</td> -->
													<td>In case of mega & mega plus industrial
														undertakings, the period commencing from the cut-off date
														falling in the effective period of these Rules, and upto 5
														years, or the date of commencement of commercial
														production, whichever falls earlier. Such cases will also
														be covered in which the cut-off date is within the period
														immediately preceding 3 years from the effective date,
														subject to the condition that atleast 40% of eligible
														capital investment shall have to be undertaken after the
														effective date. In any case, the maximum eligible
														investment period shall not be more than 5 years.</td>
													<td style="width: 33%;"><input type="text"
														class="form-control" value="${ctypeMEGA}"
														readonly="readonly"> <%-- <td><select class="form-control" readonly="readonly">
																								<c:choose>
																									<c:when test="${ctypeMEGA eq 'Yes'}">
																										<option value="${ctypeMEGA}" selected="selected">${ctypeMEGA}</option>
																										<option value="No">No</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeMEGA}" selected="selected">${ctypeMEGA}</option>
																									</c:otherwise>
																								</c:choose>
																						</select>--%></td>
													<td style="width: 33%;"><textarea
															name="eligblInvPerdMegaObserv" disabled="disabled"
															placeholder="Observations Comment By PICUP"
															class="form-control" rows="2">${eligblInvPerdMegaObserv}</textarea></td>
												</tr>
												<tr class="in-case-of-supermega-row">

													<td>in case of super mega industrial undertakings, the
														period commencing from the cutoff date falling in the
														effective period of these Rules, and upto 7 years, or the
														Proposed Date of Commencement of Commercial Production,
														whichever falls earlier. Such cases will also be covered
														in which the cut-off date is within the period immediately
														preceding 3 years from the effective date, subject to the
														condition that atleast 40% of eligible capital investment
														shall have to be undertaken after the effective date. In
														any case, the maximum eligible investment period shall not
														be more than 7 years.</td>
													<td style="width: 33%;"><input type="text"
														class="form-control" value="${ctypeSUPERMEGA}"
														readonly="readonly"> <%-- <td><select class="form-control" readonly="readonly">
																								<c:choose>
																									<c:when test="${ctypeSUPERMEGA eq 'Yes'}">
																										<option value="${ctypeSUPERMEGA}"
																											selected="selected">${ctypeSUPERMEGA}</option>
																										<option value="No">No</option>
																										<option value="${opt}" selected="selected">${opt}</option>
																									</c:when>
																									<c:otherwise>
																										<option value="Yes">Yes</option>
																										<option value="${ctypeSUPERMEGA}"
																											selected="selected">${ctypeSUPERMEGA}</option>
																											<option value="${opt}">${opt}</option>
																									</c:otherwise>
																								</c:choose>
																						</select> --%></td>
													<td style="width: 33%;"><textarea
															name="eligblInvPerdSupermegaObserv" disabled="disabled"
															placeholder="Observations Comment By PICUP"
															class="form-control" rows="2">${eligblInvPerdSupermegaObserv}</textarea></td>
												</tr>
											</tbody>
										</table>
									</div>
								</td>
							</tr>

							<tr class="in-case-of-inMMSM-row">
								<td><label class="table-heading">Company has
										invested atleast 40% of the eligible capital investment after
										the effective date of policy i.e. 13/07/2017</label></td>

								<td><select name="eligcapInvest" disabled="true"
									class="form-control" id="eligcapinvest">
										<option value="Yes">Yes</option>
										<option value="No">No</option>
								</select></td>
								<td><textarea name="eligblCapInvObserv" disabled="disabled"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2">${eligblCapInvObserv}</textarea></td>

							</tr>

							<tr>
								<td><label class="table-heading">Whether the
										project is planned in phases (if yes phase wise details to be
										added).</label></td>

								<td><input type="text" class="form-control"
									value="${phsWsAply}" readonly="readonly"></td>
								<td><textarea name="projPhasesObserv" disabled="disabled"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2">${projPhasesObserv}</textarea></td>
							</tr>

							<tr class="phase">
								<td colspan="3">
									<div class="table-responsive">
										<table class="table table-stripped table-bordered"
											id="customFields3" tabindex="3">

											<thead>
												<tr>
													<th>Phase</th>
													<th>Product</th>
													<th>Capacity</th>
													<th>Unit</th>
													<th>Proposed Capital Investment</th>
													<th>Proposed Date of Commercial Production</th>

												</tr>
											</thead>


											<tbody id="editButton" class="disable_a_href">

												<c:forEach var="pwInvObj" items="${deptPwInvList}"
													varStatus="counter">
													<!-- Iterating the list using JSTL tag  -->
													<c:if test="${not empty deptPwInvList}">
														<tr>
															<td>${pwInvObj.pwPhaseNo}</td>
															<td>${pwInvObj.pwProductName}</td>
															<td>${pwInvObj.pwCapacity}</td>
															<td>${pwInvObj.pwUnit}</td>
															<td>${pwInvObj.pwFci}</td>
															<td>${pwInvObj.pwPropProductDate}</td>
														</tr>
													</c:if>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</td>
							</tr>

							<tr>
								<td><label class="table-heading">Investment made
										till <input type="text" name="cAStatutoryDate"
										class="till-date" value="${invCAStatutoryDate}"
										placeholder="Enter Date in DD/MM/YYYY"> as per
										CA/Statutory Certificate
								</label></td>
								<td><input type="text" class="form-control"
									name="cAStatutoryAmt" maxlength="12" readonly="true"
									onkeypress="return validateNumberField()"
									value="${invCAStatutoryAmount}" placeholder="Amount in Rs."
									name=""> <small class="mt-2 d-block text-info"
									id="cAStatutoryAmtWord"></small></td>
								<td><textarea name="caStatutoryObserv" disabled="disabled"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2">${caStatutoryObserv}</textarea></td>
							</tr>




							<tr>
								<td><label class="table-heading">Name of Authorized
										Signatory. (Board Resolution copy provided.) <a
										href="./downloadBusinessEntityDoc?fileName=${bodDocFname}">(View
											File)</a>
								</label></td>
								<td><input type="text" name="authorisedSignatoryName"
									value="${authSignName}" class="form-control" readonly="true"></td>
								<td><textarea name="authSignatoryObserv"
										disabled="disabled"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2">${authSignatoryObserv}</textarea></td>
							</tr>
							<tr>
								<td><label class="table-heading">Application
										Format- (Whether application is as per prescribed format &
										signed by Authorized Signatory with Name, Designation and
										Official Seal.)</label></td>
								<td><select class="form-control" disabled="disabled"
									name="presFormat" id="presFormatId">
										<option value="Yes">Yes</option>
										<option value="No">No</option>
								</select></td>
								<td><textarea name="appformatObserv" disabled="disabled"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2">${appformatObserv}</textarea></td>
							</tr>
							<tr>
								<td><label class="table-heading">Whether all
										supporting document of application authenticated by a
										Director/Partner/Officer duly authorized by the competent
										authority of the applicant on its behalf. </label></td>
								<td><select class="form-control" disabled="disabled"
									name="docAuthorized" id="docAuthorizedId">
										<option value="Yes">Yes</option>
										<option value="No">No</option>
								</select></td>
								<td><textarea name="supprtdocObserv" disabled="disabled"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2">${supprtdocObserv}</textarea></td>
							</tr>
							<tr>
								<td><label class="table-heading">Online Submission
										of Date of Application </label></td>
								<td><input type="text" name="CreatedDate"
									class="form-control" value="${onlineSubmitDate}"
									readonly="true"></input></td>
								<td><textarea name="subDateAppObserv" disabled="disabled"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2">${subDateAppObserv}</textarea></td>
							</tr>
							<tr>
								<td><label class="table-heading">GSTIN Number</label></td>
								<td><input type="text" class="form-control" name="gstin"
									value="${GSTno}" readonly="true"></td>
								<td><textarea name="gstinObserv" disabled="disabled"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2">${gstinObserv}</textarea></td>
							</tr>
							<tr>
								<td><label class="table-heading">PAN Number</label></td>
								<td><input type="text" name="companyPanNo"
									class="form-control" value="${companyPANno}" readonly="true"></td>
								<td><textarea name="panObserv" disabled="disabled"
										placeholder="Observations Comment By PICUP"
										class="form-control" rows="2">${panObserv}</textarea></td>
							</tr>
							<tr>
								<td><label class="table-heading">Solar/Captive
										Power</label></td>
								<td><input type="text" name="solarCaptivePower"
									path="solarCaptivePower" class="form-control" readonly="true"
									value="${solarCaptivePower}"></td>
								<td><textarea name="solarCaptivePowerObserv"
										disabled="disabled"
										placeholder="Observations Comment By PICUP" disabled="disabled" 
										 class="form-control" rows="2">${solarCaptivePowerObserv} </textarea>
								</td>
							</tr>
						</tbody>
						<tfoot>
							<tr hidden="">
								<td colspan="3"><a href="javacript:void();"
									class="btn btn-outline-success btn-sm">Add More</a></td>
							</tr>
						</tfoot>
					</table>
				</div>

			</div>
		</div>
	</div>
</div>
<div class="mt-4">
	<div class="without-wizard-steps">
		<h4
			class="card-title mb-4 mt-4 text-center animate__animated animate__fadeInDown">
			Incentive Sought</h4>
	</div>
</div>
<form:form modelAttribute="incentiveDeatilsData"
	name="incentiveDeatilsform" method="post">
	<div class="row">
		<div class="col-sm-12">
			<div class="table-responsive">
				<table class="table table-stripped table-bordered">
					<thead>
						<tr>
							<th>Sr. No.</th>
							<th style="width: 25%;">Details of Incentives Sought and
								Concerned Department</th>
							<th style="width: 10%;">Amount of Incentives <small>(Rs.
									in crores)</small></th>
							<th>Incentive as per Rules</th>
							<th style="width: 25%;">PICUP Officer's Remark</th>
						</tr>
					</thead>
					<tbody>
						<tr class="ISF_Claim_Reim-row">
							<td>1</td>
							<td>Amount of SGST claimed for reimbursement <br> <strong>Industrial
									Development/ Commercial Tax</strong></td>
							<td><form:input path="ISF_Claim_Reim" type="text"
									class="ISF_Reim_SGST form-control" name="ISF_Claim_Reim"
									id="ISF_Claim_Reim" maxlength="12" readonly="true"></form:input>
								<span class="text-danger d-inline-block mt-2"></span></td>

							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
								target="_blank">As per Table 3</a> of Rules related to
								IIEPP-2017 (The Rules), there is a provision for reimbursement
								of 70% of deposited GST (Net SGST) for 10 years.</td>

							<td><form:textarea path="sgstRemark" rows="4"
									readonly="true" maxlength="1000" class="form-control"></form:textarea></td>
						</tr>

						<c:if test="${not empty incentiveDeatilsData.isfSgstComment}">
							<tr class="ISF_Claim_Reim-row">
								<td colspan="5">
									<p class="text-info">Comment From : Industrial Development/
										Commercial Tax</p> <form:textarea class="form-control"
										path="isfSgstComment" name="isfSgstComment" readonly="true"
										rows="3" placeholder="Comments"></form:textarea>
								</td>
							</tr>
						</c:if>
						<tr class="ISF_Reim_SCST-row">
							<td>1.1</td>
							<td>Additional 10% GST where 25% minimum SC/ST workers
								employed subject to minimum of 400 total workers in industrial
								undertakings located in Paschimanchal and minimum of 200 total
								workers in B-P-M <br> <strong>Industrial
									Development/ Commercial Tax</strong>

							</td>
							<td><form:input path="ISF_Reim_SCST" type="text"
									id="Add_SCST_Textbox" class="ISF_Reim_SGST form-control"
									name="ISF_Reim_SCST" maxlength="12" readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
								target="_blank">As per Table 3</a> of The Rules, there is a
								provision for 75% Stamp Duty Exemption in Paschimanchal Region.
								<br>Further, as per G.O. No. 1515/77-6-18-5(M)/17, dated
								1.5.2018, there is a provision for reimbursement equivalent to
								the paid Stamp Duty, for which company will have to apply before
								concerned GM, DIC.</td>

							<td><form:textarea path="scstRemark" rows="4"
									readonly="true" maxlength="1000" class="form-control" /></td>
						</tr>

						<c:if test="${not empty incentiveDeatilsData.isfScstComment}">
							<tr class="ISF_Reim_SCST-row">
								<td colspan="5"><p class="text-info">Comment From :
										Industrial Development/ Commercial Tax</p> <form:textarea
										class="form-control" path="isfScstComment"
										name="isfScstComment" readonly="true" rows="3"
										placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr class="ISF_Reim_FW-row">
							<td>1.2</td>
							<td>Additional 10% GST where 40% minimum female workers
								employed subject to minimum of 400 total workers in industrial
								undertakings located in Paschimanchal and minimum of 200 total
								workers in B-P-M</td>
							<td><form:input path="ISF_Reim_FW" type="text"
									id="Add_FW_Textbox" maxlength="12"
									class="ISF_Reim_SGST form-control" name="ISF_Reim_FW"
									readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
								target="_blank">As per para 3.3</a> and Table 3 of The Rules,
								there is a provision for incentive of reimbursement of EPF to
								the extent of 50% of employer's contribution to all such new
								Industrial undertakings providing direct employment to 100 or
								more unskilled workers, after three years from the date of
								commercial production for a period of 5 years.</td>


							<td><form:textarea path="fwRemark" rows="4" maxlength="1000"
									class="form-control" readonly="true" /></td>
						</tr>


						<c:if test="${not empty incentiveDeatilsData.isffwComment}">

							<tr class="ISF_Reim_FW-row">
								<td colspan="5"><p class="text-info">Comment From :
										Industrial Development/ Commercial Tax</p> <form:textarea
										class="form-control" path="isffwComment" name="isffwComment"
										readonly="true" rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>
						<tr class="ISF_Reim_BPLW-row">
							<td>1.3</td>
							<td>Additional 10% GST where 25% minimum BPL workers
								employed subject to minimum of 400 total workers in industrial
								undertakings located in Paschimanchal and minimum of 200 total
								workers in B-P-M</td>
							<td><form:input path="ISF_Reim_BPLW" type="text"
									class="ISF_Reim_SGST form-control" maxlength="12"
									name="ISF_Reim_BPLW" id="Add_BPLW_Textbox" readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
								target="_blank">As per para 3.5.7</a> of the Rules, there is a
								following provision: The industries which are disallowed for
								input tax credit under the GST regime, will be eligible for
								reimbursement of that amount of GST paid on purchase of plant
								and machinery, building material and other capital goods during
								construction and commissioning period and raw materials and
								other inputs in respect of which input tax credit has not been
								allowed. While calculating the overall eligible capital
								investment such amount will be added to the fixed capital
								investment.</td>

							<td><form:textarea path="bplRemark" maxlength="1000"
									rows="4" class="form-control" readonly="true"></form:textarea></td>
						</tr>

						<c:if test="${not empty incentiveDeatilsData.isfBplComment}">
							<tr class="ISF_Reim_BPLW-row">
								<td colspan="5"><p class="text-info">Comment From :
										Industrial Development/ Commercial Tax</p> <form:textarea
										class="form-control" path="isfBplComment" name="isfBplComment"
										readonly="true" rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr class="ISF_Stamp_Duty_EX-row">
						<tr>
							<td>2</td>
							<td>Amount of Stamp Duty Exemption<br> <strong>Stamp
									& Registration</strong>
							</td>
							<td><form:input path="ISF_Stamp_Duty_EX" type="text"
									class="ISF_Reim_SGST form-control" maxlength="12"
									name="ISF_Stamp_Duty_EXe" id="ISF_Stamp_Duty_EXe"
									readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
								target="_blank">As per Table 3</a> of The Rules, there is a
								provision for <span id="sdepercent"> </span> % Stamp Duty
								Exemption in ${region} Region. Further, as per G.O. No.
								1515/77-6-18-5(M)/17, dated 1.5.2018, there is a provision for
								reimbursement equivalent to the paid Stamp Duty, for which
								company will have to apply before concerned GM, DIC.</td>
							<td><form:textarea path="stampDutyExemptRemark"
									readonly="true" maxlength="1000" rows="4" class="form-control"></form:textarea></td>
						</tr>

						<!-- sachin -->
						<c:if test="${not empty incentiveDeatilsData.isfStampComment}">
							<tr class="ISF_Stamp_Duty_EX-row">
								<td colspan="5"><p class="text-info">Comment From :
										Stamp & Registration</p> <form:textarea class="form-control"
										path="isfStampComment" name="isfStampComment" readonly="true"
										rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>


						<tr class="ISF_Amt_Stamp_Duty_Reim-row">
							<td></td>
							<td>Amount of Stamp Duty Reimbursement</td>
							<td><form:input path="ISF_Amt_Stamp_Duty_Reim" type="text"
									class="ISF_Amt_Stamp_Duty_Reim form-control" maxlength="12"
									name="ISF_Amt_Stamp_Duty_Reim" id="Add_ISF_Amt_Stamp_Duty_Reim"
									readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
								target="_blank">As per Table 3</a> of The Rules, there is a
								provision for <span id="sdrpercent"> </span> % Stamp Duty
								Exemption in ${region} Region. Further, as per G.O. No.
								1515/77-6-18-5(M)/17, dated 1.5.2018, there is a provision for
								reimbursement equivalent to the paid Stamp Duty, for which
								company will have to apply before concerned GM, DIC.</td>
							<td><form:textarea path="stampDutyReimRemark"
									readonly="true" maxlength="1000" rows="4" class="form-control"></form:textarea></td>
						</tr>
						<c:if test="${not empty incentiveDeatilsData.isfStampComment}">
							<tr class="ISF_Amt_Stamp_Duty_Reim-row">
								<td colspan="5"><p class="text-info">Comment From :
										Stamp & Registration</p> <form:textarea class="form-control"
										path="isfStampComment" name="isfStampComment" readonly="true"
										rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>
						<tr class="ISF_Additonal_Stamp_Duty_EX-row">
							<td>2.1</td>
							<td>Additional Stamp Duty exemption @20% upto maximum of
								100% in case of industrial undertakings having 75% equity owned
								by Divyang/SC/ ST/Females Promoters</td>
							<td><form:input path="ISF_Additonal_Stamp_Duty_EX"
									type="text" class="ISF_Additonal_Stamp_Duty_EX form-control"
									maxlength="12" name="ISF_Additonal_Stamp_Duty_EX"
									id="ISF_Additonal_Stamp_Duty_EX" readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
								target="_blank">As per Table 3</a> of The Rules, there is a
								provision for dynamic percentage Stamp Duty Exemption in Dynamic
								Region. Further, as per G.O. No. 1515/77-6-18-5(M)/17, dated
								1.5.2018, there is a provision for reimbursement equivalent to
								the paid Stamp Duty, for which company will have to apply before
								concerned GM, DIC.</td>

							<td><form:textarea path="divyangSCSTRemark" readonly="true"
									rows="4" maxlength="1000" class="form-control" /></td>
						</tr>
						<c:if test="${not empty incentiveDeatilsData.isfStampscstComment}">
							<tr class="ISF_Additonal_Stamp_Duty_EX-row">
								<td colspan="5"><p class="text-info">Comment From :
										Stamp & Registration</p> <form:textarea class="form-control"
										path="isfStampscstComment" name="isfStampscstComment"
										readonly="true" rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>
						<tr class="ISF_Epf_Reim_UW-row">
							<td>3</td>
							<td>EPF Reimbursement (100 or more unskilled workers) <br>
								<strong>Labour/Industrial Development</strong>
							</td>
							<td><form:input path="ISF_Epf_Reim_UW" type="text"
									class="ISF_Epf_Reim_UW form-control" maxlength="12"
									name="ISF_Amt_Stamp_Duty_Reim" id="Add_ISF_Amt_Stamp_Duty_Reim"
									readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
								target="_blank">As per para 3.3</a> and Table 3 of The Rules,
								there is a provision for incentive of reimbursement of EPF to
								the extent of 50% of employer's contribution to all such new
								Industrial undertakings providing direct employment to 100 or
								more unskilled workers, after three years from the date of
								commercial production for a period of 5 years.</td>

							<td><form:textarea path="epfUnsklRemark" rows="4"
									readonly="true" class="form-control" /></td>
						</tr>
						<c:if test="${not empty incentiveDeatilsData.isfepfComment}">
							<tr class="ISF_Epf_Reim_UW-row">
								<td colspan="5"><p class="text-info">Comment From :
										Labour/Industrial Development</p> <form:textarea
										class="form-control" path="isfepfComment" name="isfepfComment"
										readonly="true" rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>
						<tr class="ISF_Add_Epf_Reim_SkUkW-row">
							<td>3.1</td>
							<td>Additional 10% EPF Reimbursement (200 direct skilled and
								unskilled workers)</td>
							<td><form:input path="ISF_Add_Epf_Reim_SkUkW" type="text"
									class="ISF_Add_Epf_Reim_SkUkW form-control" maxlength="12"
									name="ISF_Add_Epf_Reim_SkUkW" id="ISF_Add_Epf_Reim_SkUkW"
									readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
								target="_blank">As per para 3.3</a> and Table 3 of The Rules,
								there is a provision for incentive of reimbursement of EPF to
								the extent of 50% of employer's contribution to all such new
								Industrial undertakings providing direct employment to 100 or
								more unskilled workers, after three years from the date of
								commercial production for a period of 5 years.</td>

							<td><form:textarea path="epfSklUnsklRemark" readonly="true"
									rows="4" maxlength="1000" class="form-control" /></td>
						</tr>
						<c:if test="${not empty incentiveDeatilsData.isfepfaddComment}">
							<tr class="ISF_Add_Epf_Reim_SkUkW-row">
								<td colspan="5"><p class="text-info">Comment From :
										Labour/Industrial Development</p> <form:textarea
										class="form-control" path="isfepfaddComment"
										name="isfepfaddComment" readonly="true" rows="3"
										placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>
						<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
							<td>3.2</td>
							<td>Additional 10% EPF Reimbursement upto maximum of 70% in
								case of industrial undertakings having 75% equity owned by
								Divyang/SC/ST/Females Promoters</td>
							<td><form:input path="ISF_Add_Epf_Reim_DIVSCSTF" type="text"
									class="ISF_Add_Epf_Reim_DIVSCSTF form-control" maxlength="12"
									name="ISF_Add_Epf_Reim_DIVSCSTF" id="ISF_Add_Epf_Reim_DIVSCSTF"
									readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=6"
								target="_blank">As per para 3.3</a> and Table 3 of The Rules,
								there is a provision for incentive of reimbursement of EPF to
								the extent of 50% of employer's contribution to all such new
								Industrial undertakings providing direct employment to 100 or
								more unskilled workers, after three years from the date of
								commercial production for a period of 5 years.</td>

							<td><form:textarea path="epfDvngSCSTRemark" readonly="true"
									rows="4" maxlength="1000" class="form-control" /></td>
						</tr>
						<c:if test="${not empty incentiveDeatilsData.isfepfscComment}">
							<tr class="ISF_Add_Epf_Reim_DIVSCSTF-row">
								<td colspan="5"><p class="text-info">Comment From :
										Labour/Industrial Development</p> <form:textarea
										class="form-control" path="isfepfscComment"
										name="isfepfscComment" readonly="true" rows="3"
										placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>
						<tr class="ISF_Cis-row">
							<td>4</td>
							<td>Capital Interest Subsidy <br> <strong>Industrial
									Development</strong></td>
							<td><form:input path="ISF_Cis" type="text"
									class="ISF_Cis form-control" maxlength="12" name="ISF_Cis"
									id="ISF_Cis" readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
								target="_blank">As per para 3.5.1</a> of The Rules, there is a
								provision for capital interest subsidy @ 5% p.a. or actual
								interest paid whichever is less annually for 5 years in the form
								of reimbursement of interest paid on outstanding loan taken for
								procurement of plant & machinery, subject to an annual ceiling
								of Rs. 50 lacs.</td>
							<td><form:textarea path="capIntSubRemark" readonly="true"
									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
						</tr>

						<c:if test="${not empty incentiveDeatilsData.isfcapisComment}">
							<tr class="ISF_Cis-row">
								<td colspan="5"><p class="text-info">Comment From :
										Industrial Development</p> <form:textarea class="form-control"
										path="isfcapisComment" name="isfcapisComment" readonly="true"
										rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr class="ISF_ACI_Subsidy_Indus-row">
							<td>4.1</td>
							<td>Additional Capital Interest Subsidy@2.5% upto maximum of
								7.5% in case of industrial undertakings having 75% equity owned
								by Divyang/SC/ST/Females Promoters</td>
							<td><form:input path="ISF_ACI_Subsidy_Indus" type="text"
									class="ISF_ACI_Subsidy_Indus form-control" maxlength="12"
									name="ISF_ACI_Subsidy_Indus" id="ISF_ACI_Subsidy_Indus"
									readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
								target="_blank">As per para 3.5.1</a> of The Rules, there is a
								provision for capital interest subsidy @ 5% p.a. or actual
								interest paid whichever is less annually for 5 years in the form
								of reimbursement of interest paid on outstanding loan taken for
								procurement of plant & machinery, subject to an annual ceiling
								of Rs. 50 lacs.</td>

							<td><form:textarea path="aciSubsidyRemark" readonly="true"
									rows="4" maxlength="1000" class="form-control" /></td>
						</tr>

						<c:if test="${not empty incentiveDeatilsData.isfcapisaComment}">
							<tr class="ISF_ACI_Subsidy_Indus-row">
								<td colspan="5"><p class="text-info">Comment From :
										Industrial Development</p> <form:textarea class="form-control"
										path="isfcapisaComment" name="isfcapisaComment"
										readonly="true" rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr class="ISF_Infra_Int_Subsidy-row">
							<td>5</td>
							<td>Infrastructure Interest Subsidy <br> <strong>Industrial
									Development</strong></td>
							<td><form:input path="ISF_Infra_Int_Subsidy" type="text"
									class="ISF_Infra_Int_Subsidy form-control" maxlength="12"
									name="ISF_Infra_Int_Subsidy" id="ISF_Infra_Int_Subsidy"
									readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
								target="_blank">As per para 3.5.2</a> of the Rules, there is a
								provision for incentive of infrastructure interest subsidy @ 5%
								p.a. or actual interest paid whichever is less annually for 5
								years in the form of reimbursement of interest paid on
								outstanding loan taken for development of infrastructural
								amenities (as defined in para 2.17) subject to an overall
								ceiling of Rs. 1 Crore.</td>
							<td><form:textarea path="infraIntSubRemark" readonly="true"
									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
						</tr>

						<c:if test="${not empty incentiveDeatilsData.isfinfComment}">
							<tr class="ISF_Infra_Int_Subsidy-row">
								<td colspan="5"><p class="text-info">Comment From :
										Industrial Development</p> <form:textarea class="form-control"
										path="isfinfComment" name="isfinfComment" readonly="true"
										rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
							<td>5.1</td>
							<td>Additional Infrastructure Interest Subsidy @2.5% upto
								maximum of 7.5% in case of industrial undertakings having 75%
								equity owned by Divyang/SC/ST/Females Promoters</td>
							<td><form:input path="ISF_AII_Subsidy_DIVSCSTF" type="text"
									class="ISF_AII_Subsidy_DIVSCSTF form-control" maxlength="12"
									name="ISF_AII_Subsidy_DIVSCSTF" id="ISF_AII_Subsidy_DIVSCSTF"
									readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
								target="_blank">As per para 3.5.2</a> of the Rules, there is a
								provision for incentive of infrastructure interest subsidy @ 5%
								p.a. or actual interest paid whichever is less annually for 5
								years in the form of reimbursement of interest paid on
								outstanding loan taken for development of infrastructural
								amenities (as defined in para 2.17) subject to an overall
								ceiling of Rs. 1 Crore.</td>

							<td><form:textarea path="aiiSubsidyRemark" readonly="true"
									rows="4" maxlength="1000" class="form-control"></form:textarea></td>
						</tr>

						<c:if test="${not empty incentiveDeatilsData.isfinfaComment}">
							<tr class="ISF_AII_Subsidy_DIVSCSTF-row">
								<td colspan="5"><p class="text-info">Comment From :
										Industrial Development</p> <form:textarea class="form-control"
										path="isfinfaComment" name="isfinfaComment" readonly="true"
										rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr class="ISF_Loan_Subsidy-row">
							<td>6</td>
							<td>Interest Subsidy on loans for industrial research,
								quality improvement, etc. <br> <strong>Industrial
									Development</strong>
							</td>
							<td><form:input path="ISF_Loan_Subsidy" type="text"
									class="ISF_Loan_Subsidy form-control" maxlength="12"
									name="ISF_Loan_Subsidy" id="ISF_Loan_Subsidy" readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=7"
								target="_blank">As per para 3.5.3</a> of The Rules, there is a
								provision for Interest subsidy on loans for industrial research
								@ 5% or actual interest paid whichever is less annually for 5
								years in the form of reimbursement of interest paid on
								outstanding loan taken for industrial research, quality
								improvement and development of products by incurring expenditure
								on procurement of plant, machinery & equipment for setting up
								testing labs, quality certification labs and tool rooms, subject
								to an overall ceiling of Rs. 1 Crore</td>
							<td><form:textarea path="loanIntSubRemark" readonly="true"
									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
						</tr>

						<c:if test="${not empty incentiveDeatilsData.isfloanComment}">
							<tr class="ISF_Loan_Subsidy-row">
								<td colspan="5"><p class="text-info">Comment From :
										Industrial Development</p> <form:textarea class="form-control"
										path="isfloanComment" name="isfloanComment" readonly="true"
										rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr class="ISF_Tax_Credit_Reim-row">
							<td>7</td>
							<td>Reimbursement of Disallowed Input Tax Credit on plant,
								building materials, and other capital goods. <br> <strong>Industrial
									Development</strong></br>
							</td>
							<td><form:input path="ISF_Tax_Credit_Reim" type="text"
									class="ISF_Tax_Credit_Reim form-control" maxlength="12"
									name="ISF_Tax_Credit_Reim" id="ISF_Tax_Credit_Reim"
									readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
								target="_blank">As per para 3.5.7</a> of the Rules, it is
								provided - The industries which are disallowed for input tax
								credit under the GST regime, will be eligible for reimbursement
								of that amount of GST paid on purchase of plant and machinery,
								building material and other capital goods during construction
								and commissioning period and raw materials and other inputs in
								respect of which input tax credit has not been allowed. While
								calculating the overall eligible capital investment such amount
								will be added to the fixed capital investment.</td>
							<td><form:textarea path="inputTaxRemark" rows="4"
									readonly="true" maxlength="1000" class="form-control"></form:textarea></td>
						</tr>

						<c:if test="${not empty incentiveDeatilsData.isfdisComment}">
							<tr class="ISF_Tax_Credit_Reim-row">
								<td colspan="5"><p class="text-info">Comment From :
										Industrial Development</p> <form:textarea class="form-control"
										path="isfdisComment" name="isfdisComment" readonly="true"
										rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr class="ISF_EX_E_Duty-row">
							<td>8</td>
							<td>Exemption from Electricity Duty from captive power for
								self-use <br> <strong>Energy/UPPCL/ Electricity
									Safety</strong></br>
							</td>
							<td><form:input path="ISF_EX_E_Duty" type="text"
									class="ISF_EX_E_Duty form-control" maxlength="12"
									name="ISF_EX_E_Duty" id="ISF_EX_E_Duty" readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
								target="_blank">As per para 3.5.5</a> of The Rules, there is a
								provision for Exemption from Electricity Duty for 10 years to
								all new industrial undertakings producing electricity from
								captive power plants for self-use.</td>
							<td><form:textarea path="elecDutyCapRemark" readonly="true"
									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
						</tr>

						<c:if test="${not empty incentiveDeatilsData.isfelepodownComment}">
							<tr class="ISF_EX_E_Duty-row">
								<td colspan="5"><p class="text-info">Comment From :
										UPPCL</p> <form:textarea class="form-control"
										path="isfelepodownComment" name="isfelepodownComment"
										readonly="true" rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr class="ISF_EX_E_Duty_PC-row">
							<td>9</td>
							<td>Exemption from Electricity duty on power drawn from
								power companies <br> <strong>Energy/UPPCL/
									Electricity Safety</strong></br>
							</td>
							<td><form:input path="ISF_EX_E_Duty_PC" type="text"
									class="ISF_EX_E_Duty_PC form-control" maxlength="12"
									name="ISF_EX_E_Duty_PC" id="ISF_EX_E_Duty_PC" readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
								target="_blank">As per para 3.5.4</a> of The Rules, there is a
								provision for Exemption from Electricity Duty to all new
								industrial undertakings set up in the State for 10 years.</td>
							<td><form:textarea path="elecDutyDrawnRemark"
									readonly="true" maxlength="1000" rows="4" class="form-control"></form:textarea></td>
						</tr>


						<c:if test="${not empty incentiveDeatilsData.isfElecdutyComment}">
							<tr class="ISF_EX_E_Duty_PC-row">
								<td colspan="5"><p class="text-info">Comment From :
										UPPCL</p> <form:textarea class="form-control"
										path="isfElecdutyComment" name="isfElecdutyComment"
										readonly="true" rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr class="ISF_EX_Mandee_Fee-row">
							<td>10</td>
							<td>Exemption from Mandi Fee <br> <strong>Agriculture
									Marketing & Agriculture Foreign Trade/MandiParishad</strong></br></td>
							<td><form:input path="ISF_EX_Mandee_Fee" type="text"
									class="ISF_EX_Mandee_Fee form-control" maxlength="12"
									name="ISF_EX_Mandee_Fee" id="ISF_EX_Mandee_Fee" readonly="true"></form:input></td>
							<td><a href="./pdffiles/IIEPP_Rules_2017.pdf#page=8"
								target="_blank">As per para 3.5.6</a> of The Rules, There is
								provision for exemption from Mandi Fee for all new
								food-processing undertakings on purchase of raw material for 5
								years.</td>
							<td><form:textarea path="mandiFeeRemark" rows="4"
									readonly="true" maxlength="1000" class="form-control"></form:textarea></td>
						</tr>


						<c:if test="${not empty incentiveDeatilsData.isfMandiComment}">
							<tr class="ISF_EX_Mandee_Fee-row">
								<td colspan="5"><p class="text-info">Comment From :
										Agriculture Marketing & Agriculture Foreign
										Trade/MandiParishad</p> <form:textarea class="form-control"
										path="isfMandiComment" name="isfMandiComment" readonly="true"
										rows="3" placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>
						<tr class="ISF_Indus_Payroll_Asst-row">
							<td>11</td>
							<td>Industrial units providing employment to differently
								abled workers will be provided payroll assistance of Rs. 500 per
								month for each such worker.</td>
							<td><form:input path="ISF_Indus_Payroll_Asst" type="text"
									class="ISF_Indus_Payroll_Asst form-control" maxlength="12"
									name="ISF_Amt_Stamp_Duty_Reim" id="Add_ISF_Amt_Stamp_Duty_Reim"
									readonly="true"></form:input></td>
							<td>No such provision in The Rules.</td>
							<td><form:textarea path="diffAbleWorkRemark" readonly="true"
									maxlength="1000" rows="4" class="form-control"></form:textarea></td>
						</tr>

						<c:if
							test="${not empty incentiveDeatilsData.isfdifferabilComment}">
							<tr class="ISF_Indus_Payroll_Asst-row">
								<td colspan="5"><p class="text-info">Comment From :
										Labour/Industrial Development</p> <form:textarea
										class="form-control" path="isfdifferabilComment"
										name="isfdifferabilComment" readonly="true" rows="3"
										placeholder="Comments"></form:textarea></td>
							</tr>
						</c:if>

						<tr>
							<td colspan="2" align="Center"><strong>Total</strong></td>
							<td><strong>${total}</strong></td>
							<td colspan="4">All incentives in the form of reimbursement,
								subsidies, exemptions etc., will be subject to a maximum of <span
								id="percent"> </span> % of fixed capital investment made in
								${region} area.
							</td>
						</tr>

					</tbody>
				</table>
			</div>

		</div>
	</div>
	<c:if test="${not empty incentiveDeatilsData.othAddRequest1}">
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group">
					<label>Additional Request</label>
					<form:input path="othAddRequest1" type="text" class="form-control"
						placeholder="" maxlength="250" name="othAddRequest1"
						readonly="true"></form:input>
				</div>
			</div>
		</div>
	</c:if>
</form:form>




<script>
	$(document).ready(function() {
		$("[data-toggle=offcanvas]").click(function() {
			$(".row-offcanvas").toggleClass("active");
		});
	});
</script>
<script>
	//Get the button
	var mybutton = document.getElementById("GoToTopBtn");

	// When the user scrolls down 20px from the top of the document, show the button
	window.onscroll = function() {
		scrollFunction()
	};

	function scrollFunction() {
		if (document.body.scrollTop > 20
				|| document.documentElement.scrollTop > 20) {
			mybutton.style.display = "block";
		} else {
			mybutton.style.display = "none";
		}
	}

	// When the user clicks on the button, scroll to the top of the document
	function topFunction() {
		document.body.scrollTop = 0;
		document.documentElement.scrollTop = 0;
	}
</script>