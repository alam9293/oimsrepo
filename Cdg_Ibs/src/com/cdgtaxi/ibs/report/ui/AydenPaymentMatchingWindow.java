package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxn;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrca;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableBatchForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableTxnsForm;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.dto.NonBillableBatchDto;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.elixirtech.net.NetException;

public class AydenPaymentMatchingWindow extends ReportWindow implements AfterCompose {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AydenPaymentMatchingWindow.class);
    private final String SUMMARY_REPORT = "Summary Report";
    private final String BREAKDOWN_REPORT = "Breakdown Of Excess in 2nd File";
    private final String SUMMARY_BREAKDOWN_REPORT = SUMMARY_REPORT + " + " + BREAKDOWN_REPORT;
    private Long reportRsrcId;
    private String report = "Ayden Payment Matching";
    private String matchingStatus = null;
    private CapsTextbox pspRefNoTextBox, jobNoTextBox, batchNoTextBox, aydenMerchantIdTextBox;
    private Datebox settlementStartDateDateBox, settlementEndDateDateBox;
    private Listbox matchingStatusListBox, reportFormatListBox, reportTypeFormatListBox;

    public AydenPaymentMatchingWindow() throws IOException, ParseException {
        super("Adyen Payment Matching Window", "Non Billable");
//        if (this.getHttpServletRequest().getParameter("rsrcId") != null)
//            reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
//        if (this.getHttpServletRequest().getParameter("status") != null)
//            matchingStatus = new String(this.getHttpServletRequest().getParameter("status"));
//        if (this.getHttpServletRequest().getParameter("date") != null) {
//            String dateStr = new String(this.getHttpServletRequest().getParameter("date"));
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            settlementDate = new Date(formatter.parse(dateStr).getTime());
//        }
    }

    public void afterCompose() {
        Components.wireVariables(this, this);

        prepopulateFields();

    }

    private void prepopulateFields() {

        matchingStatusListBox.appendChild(ComponentUtil.createRequiredDefaultListitem());
        matchingStatusListBox.appendChild(new Listitem("ALL",
                NonConfigurableConstants.AYDEN_MATCHING_STATUS_ALL));
        matchingStatusListBox.setSelectedIndex(1);


        reportTypeFormatListBox.appendChild(ComponentUtil.createRequiredDefaultListitem());
        reportTypeFormatListBox.appendChild(new Listitem(SUMMARY_REPORT, SUMMARY_REPORT));
        reportTypeFormatListBox.appendChild(new Listitem(BREAKDOWN_REPORT, BREAKDOWN_REPORT));
        reportTypeFormatListBox.appendChild(new Listitem(SUMMARY_BREAKDOWN_REPORT, SUMMARY_BREAKDOWN_REPORT));
        reportTypeFormatListBox.setSelectedIndex(1);

//        if (matchingStatus != null && settlementDate != null) {
//            settlementStartDateDateBox.setValue(settlementDate);
//            if (matchingStatus.equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
//                matchingStatusListBox.setSelectedIndex(1);
//            } else if (matchingStatus.equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED)) {
//                matchingStatusListBox.setSelectedIndex(2);
//            }
//        }
    }


    public void populateReportFormatList(Listbox listbox) throws NetException {
        List<MstbReportFormatMap> reportFormatMapList = this.businessHelper.getReportBusiness()
                .getReportFormatMap(this.getReportRsrcId());
        boolean firstItem = true;
        for (MstbReportFormatMap formatMap : reportFormatMapList) {
            Listitem listItem = new Listitem(formatMap.getReportFormat(),
                    Constants.EXTENSION_MAP.get(formatMap.getReportFormat()));
            if (firstItem) {
                listItem.setSelected(true);
                firstItem = false;
            }
            listbox.appendChild(listItem);
        }
    }

    public void generate() throws HttpException, IOException, InterruptedException, NetException,
            WrongValueException {

        List<NonBillableBatchDto> batchList = new ArrayList();
        boolean showSummaryReport = false;
        boolean showBreakdownReport = false;
        Map<String, Object[]> processedResults = null;
        List<TmtbNonBillableTxnCrca> breakdownResults = null;
        this.displayProcessing();

        if (matchingStatusListBox.getSelectedIndex() == 0) {
            throw new WrongValueException(matchingStatusListBox, "* Mandatory Field");
        }

        Listbox reportFormatList = (Listbox) this.getFellow("reportTypeFormatListBox");
        if (reportFormatList.getSelectedItem() == null)
            throw new WrongValueException(reportFormatListBox, "* Mandatory field");

        Listbox reportTypeFormatList = (Listbox) this.getFellow("reportFormatListBox");
        if (reportTypeFormatList.getSelectedItem() == null)
            throw new WrongValueException(reportTypeFormatListBox, "* Mandatory field");

        Datebox settlementStartDate = (Datebox) this.getFellow("settlementStartDateDateBox");
        if (settlementStartDate.getValue() == null || StringUtil.isBlank(settlementStartDate.getValue().toString())) {
            throw new WrongValueException(settlementStartDateDateBox, "* Mandatory field");
        }

        Datebox settlementEndDate = (Datebox) this.getFellow("settlementEndDateDateBox");
        if (settlementEndDate.getValue() == null || StringUtil.isBlank(settlementEndDate.getValue().toString())) {
            throw new WrongValueException(settlementEndDateDateBox, "* Mandatory field");
        }

        if (settlementStartDate.getValue() != null && settlementEndDate.getValue() != null)
            if (settlementStartDate.getValue().compareTo(settlementEndDate.getValue()) == 1) {
                throw new WrongValueException(settlementStartDateDateBox, "Settlement Start Date From cannot be later than Settlement End Date.");
            }

        if (reportFormatList.getSelectedItem().getValue().toString().equals(SUMMARY_BREAKDOWN_REPORT)) {
            showSummaryReport = true;
            showBreakdownReport = true;
        } else if (reportFormatList.getSelectedItem().getValue().toString().equals(SUMMARY_REPORT)) {
            showSummaryReport = true;
        } else if (reportFormatList.getSelectedItem().getValue().toString().equals(BREAKDOWN_REPORT)) {
            showBreakdownReport = true;
        }

//        List<String> recordType = new ArrayList<String>();
//        recordType.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_SETTLED);
//        recordType.add(NonConfigurableConstants.AMEX_RECORD_STATUS_TRANSACTION);
        String recordType1 = NonConfigurableConstants.AYDEN_RECORD_STATUS_SETTLED ;
        String recordType2 = NonConfigurableConstants.AMEX_RECORD_STATUS_TRANSACTION;
        String settlementStartDateTime = DateUtil.convertDateToStr(settlementStartDate.getValue(), DateUtil.REPORT_DATE_FORMAT) + " 00:00:00";
        String settlementEndDateTime = DateUtil.convertDateToStr(settlementEndDate.getValue(), DateUtil.REPORT_DATE_FORMAT) + " 23:59:59";
        if (showSummaryReport) {
            logger.info("Retrieving Summary Report");
            List<Object[]> results = this.businessHelper.getReportBusiness().getAydenPaymentMatchingSummaryReport(settlementStartDateTime, settlementEndDateTime, batchNoTextBox.getValue(), recordType1, recordType2);
            logger.info("End of Retrieving Summary Report");
            logger.info("Retrieving Summary Report 2");
            List<Object[]> results2 = this.businessHelper.getReportBusiness().getAydenPaymentMatchingSummaryReport2(settlementStartDateTime, settlementEndDateTime, batchNoTextBox.getValue(), recordType1, recordType2);
            logger.info("End of Retrieving Summary Report 2");
            logger.info("Retrieving Excess Amount");
            List<Object[]> results3 = this.businessHelper.getReportBusiness().getAydenPaymentMatchingExcessAmount(settlementStartDateTime, settlementEndDateTime, batchNoTextBox.getValue());
            logger.info("End of Retrieving Excess Amount");
            processedResults = processSummaryReportResults(results, results2, results3);
        }

        //toDo: remember to put in the record type for others when chargeback comes in
        if (showBreakdownReport) {
            breakdownResults = this.businessHelper.getReportBusiness().getAydenPaymentMatchingBreakdownReport(settlementStartDateTime, settlementEndDateTime, batchNoTextBox.getValue(), recordType1, recordType2);
        }

        exportCSVResult(showSummaryReport, showBreakdownReport, processedResults, breakdownResults);


//        SearchNonBillableBatchForm batchForm = this.buildNonBillableBatchSearchForm();
//        List<Object[]> batches = this.businessHelper.getNonBillableBusiness().searchNonBillableBatch(batchForm);
//
//        for (Object[] batch : batches) {
//            NonBillableBatchDto dto = new NonBillableBatchDto(batch);
//            batchList.add(dto);
//        }

//        SearchNonBillableTxnsForm txnsForm = (SearchNonBillableTxnsForm) this.buildNonBillableTxnsSearchForm(batchList);
//        List<TmtbNonBillableTxn> txns = this.businessHelper.getNonBillableBusiness().searchNonBillableTxns(txnsForm);

//        exportCSVResult(txns);
    }

    private Map<String, Object[]> processSummaryReportResults(List<Object[]> results, List<Object[]> results2, List<Object[]> results3) {
        //The content of results
        //numOfRecords,batchId,matchingStatus

        //The content of results2
        //numOfRecords,batchId,batchNo,fileName,total,matchingStatus,completeStatus,commission,interchange,schemeFee,markUp,grossAmount,uploadDate,settlementDate,batchNoSecond
        //numOfRecords,batchId,batchNo,fileName,total,matchingStatus,completeStatus,commission,interchange,schemeFee,markUp,grossAmount,uploadDate,settlementDate,batchNoSecond

        //The content of new results (key batchId@fileName)
        //settlementDate,batchNo,completeStatus,noOfPendingRecords,amtOfPendingRecords,noOfErrorRecords,amtOfErrorRecords,noOfMatchedRecords,amtOfMatchedRecords,noOfProcessed,amtOfProcessedRecords,noOfExcessRecords,amtOfExcessRecords,uploadDate,batchNoSecond,totalBeforeMDR,commission,interchange,schemeFee,markUp,totalAfterMDR


        Map<String, Object[]> newResult = new HashMap<String, Object[]>();
        Map<String,Boolean> toNA = new HashMap<String,Boolean>();

        //process results
        for (Object[] temp : results2) {
            Object[] obj;
            if (newResult.get(temp[1] + "@" + temp[3]) == null) {
//                logger.info("processing 1: " + temp[1] + " @ " + temp[3]);
                obj = new Object[27];
                obj[0] = temp[13];
                obj[1] = temp[2];
                obj[2] = NonConfigurableConstants.AYDEN_COMPLETE_STATUS.get(temp[6]);

                if (obj[4] == null) {
                    obj[10] = BigDecimal.ZERO;
                }

                if (obj[6] == null) {
                    obj[6] = BigDecimal.ZERO;
                }

                if (obj[8] == null) {
                    obj[8] = BigDecimal.ZERO;
                }

                if (temp[5].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_PENDING)) {
                    obj[10] = temp[4];
                }
                if (temp[5].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
                    obj[6] = temp[4];
                }
                if (temp[5].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED)) {
                    obj[8] = temp[4];
                }

                obj[4] = ((BigDecimal) obj[8]).add((BigDecimal) obj[6]).add((BigDecimal) obj[10]);
                obj[11] = 0L;
                obj[12] = BigDecimal.ZERO;
                obj[13] = temp[12];
                obj[14] = temp[14];
                //obj[15] = temp[11]; count(*) for payment to CDG Bank
                //obj[16] = temp[11]; $ payment to CDG Bank
                obj[17] = temp[0];
                obj[18] = temp[11];
                obj[19] = temp[0];
                obj[20] = temp[7];
                obj[21] = temp[0];
                obj[22] = temp[8];
                obj[23] = temp[0];
                obj[24] = temp[9];
                obj[25] = temp[0];
                obj[26] = temp[10];
                obj[15] = temp[0];
                obj[16] = (((((BigDecimal) obj[18]).subtract((BigDecimal) obj[20])).subtract((BigDecimal) obj[22])).subtract((BigDecimal) obj[24])).subtract((BigDecimal) obj[26]);
            } else {
//                logger.info("processing 2: " + temp[1] + " @ " + temp[3]);
                obj = newResult.get(temp[1] + "@" + temp[3]);

                if (temp[5].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_PENDING)) {
                    obj[10] = temp[4];
                }
                if (temp[5].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
                    obj[6] = temp[4];
                }
                if (temp[5].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED)) {
                    obj[8] = temp[4];
                }

                obj[4] = ((BigDecimal) obj[8]).add((BigDecimal) obj[6]).add((BigDecimal) obj[10]);
                obj[17] = (Long)obj[17] + (Long)temp[0];
                obj[18] = ((BigDecimal) obj[18]).add((BigDecimal) temp[11]);
                obj[19] = (Long) obj[19] + (Long)temp[0];
                obj[20] = ((BigDecimal) obj[20]).add((BigDecimal) temp[7]);
                obj[21] = (Long)obj[21] + (Long)temp[0];
                obj[22] = ((BigDecimal) obj[22]).add((BigDecimal) temp[8]);
                obj[23] = (Long)obj[23] + (Long) temp[0];
                obj[24] = ((BigDecimal) obj[24]).add((BigDecimal) temp[9]);
                obj[25] = (Long)obj[25] + (Long)temp[0];
                obj[26] = ((BigDecimal) obj[26]).add((BigDecimal) temp[10]);

                obj[16] = (((((BigDecimal) obj[18]).subtract((BigDecimal) obj[20])).subtract((BigDecimal) obj[22])).subtract((BigDecimal) obj[24])).subtract((BigDecimal) obj[26]);
            }
            newResult.put(temp[1].toString() + "@" + temp[3].toString(), obj);
        }

        //to NA if there is 2 entries of same matching status but different 2nd file
        for (Object[] obj : results) {

            int countP = 0;
            int countE = 0;
            int countM = 0;

            for (String temp : newResult.keySet()) {

                if (temp.contains(obj[1].toString())) {
//                    logger.info("temp: " + temp + " vs " + " batch id: " + obj[1].toString() + " (status " + obj[2] + " )");


                    if (obj[2].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_PENDING)) {
                        countP++;
                    }
                    if (obj[2].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
                        countE++;
                    }
                    if (obj[2].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED)) {
                        countM++;
                    }

//                    logger.info("counts: " + countP + " | " + countE + " | " + countM);

                    if(countP > 1 || countE > 1 || countM > 1){
//                        logger.info("toNA: " + obj[1].toString());
                        toNA.put(obj[1].toString(),true);
                        break;
                    }

//                    logger.info("end of counts: " + countP + " | " + countE + " | " + countM);

                }
            }
        }


        for (String temp : newResult.keySet()) {

            if (newResult.get(temp)[9] == null) {
                newResult.get(temp)[9] = 0L;
            }
            if (newResult.get(temp)[5] == null) {
                newResult.get(temp)[5] = 0L;
            }
            if (newResult.get(temp)[7] == null) {
                newResult.get(temp)[7] = 0L;
            }


            for (Object[] obj : results) {
                if (toNA.get(obj[1].toString()) == null && temp.contains(obj[1].toString())) {
                    if (obj[2].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_PENDING)) {
                        newResult.get(temp)[9] = obj[0];
                    }
                    if (obj[2].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
                        newResult.get(temp)[5] = obj[0];
                    }
                    if (obj[2].equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED)) {
                        newResult.get(temp)[7] = obj[0];
                    }
                    newResult.get(temp)[3] = (Long) newResult.get(temp)[7] + (Long) newResult.get(temp)[5] + (Long) newResult.get(temp)[9];
                    newResult.put(temp, newResult.get(temp));
                }
//                else{
//                    newResult.get(temp)[9] = NonConfigurableConstants.NA_FLAG;
//                    newResult.get(temp)[5] = NonConfigurableConstants.NA_FLAG;
//                    newResult.get(temp)[7] = NonConfigurableConstants.NA_FLAG;
//                    newResult.get(temp)[3] = NonConfigurableConstants.NA_FLAG;
//                    break;
//                }
            }

        }


            //process result2
        for (String temp : newResult.keySet()) {
            for (Object[] obj : results3) {
                if (temp.contains(obj[1].toString())) {
                    newResult.get(temp)[11] = obj[0];
                    newResult.get(temp)[12] = obj[2];
                    newResult.put(temp, newResult.get(temp));
                }
            }

        }

        return newResult;
    }

    public void generateSummaryReport(XSSFWorkbook wb, Map<String, Object[]> processedResults) {

        XSSFSheet sheet = wb.createSheet(SUMMARY_BREAKDOWN_REPORT);
        XSSFRow row = null;
        XSSFCell cell = null;
        Short rowNum;
        Short colNum;

        TreeMap<String, Object[]> sorted = new TreeMap<String, Object[]>();
        sorted.putAll(processedResults);

        //Customise
        XSSFFont header1 = wb.createFont();
        header1.setFontHeightInPoints((short) 14);
        header1.setBold(true);

        for (short i = 0; i < 20; i++) {
            sheet.createRow(i);
            for (short j = 0; j < 50; j++)
                sheet.getRow(i).createCell(j);
        }

        sheet.addMergedRegion(new CellRangeAddress(10, 13, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(11, 13, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(11, 13, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 6, 7));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 8, 9));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 10, 11));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 12, 13));
        sheet.addMergedRegion(new CellRangeAddress(11, 13, 14, 14));
        sheet.addMergedRegion(new CellRangeAddress(11, 13, 15, 15));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 16, 17));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 18, 19));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 20, 21));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 22, 23));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 24, 25));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 26, 27));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 28, 29));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 30, 31));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 32, 33));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 34, 35));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 36, 37));
        sheet.addMergedRegion(new CellRangeAddress(11, 12, 38, 39));


        sheet.addMergedRegion(new CellRangeAddress(10, 10, 2, 13));
        sheet.addMergedRegion(new CellRangeAddress(10, 10, 14, 39));

        XSSFCellStyle headerCellStyle = wb.createCellStyle();
        XSSFFont boldFont = wb.createFont();
        //boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        boldFont.setBold(true);
        headerCellStyle.setFont(boldFont);

        row = sheet.getRow(0);
        cell = row.getCell((short) 0);

        cell.setCellStyle(headerCellStyle);
        XSSFRichTextString temp = new XSSFRichTextString("Selection Criteria");
        temp.applyFont(header1);
        cell.setCellValue(temp);

        row = sheet.getRow(1);
        cell = row.getCell((short) 0);

        cell.setCellValue(new XSSFRichTextString("Settlement Start Date: "));

        cell = row.getCell((short) 1);

        cell.setCellValue(new XSSFRichTextString(DateUtil.convertDateToStrWithGDFormat(settlementStartDateDateBox.getValue())));

        row = sheet.getRow(2);
        cell = row.getCell((short) 0);

        cell.setCellValue(new XSSFRichTextString("Settlement End Date: "));

        cell = row.getCell((short) 1);

        cell.setCellValue(new XSSFRichTextString(DateUtil.convertDateToStrWithGDFormat(settlementEndDateDateBox.getValue())));

        row = sheet.getRow(3);
        cell = row.getCell((short) 0);

        cell.setCellValue(new XSSFRichTextString("Batch No: "));

        cell = row.getCell((short) 1);

        cell.setCellValue(new XSSFRichTextString(batchNoTextBox.getValue().toString()));

        row = sheet.getRow(4);
        cell = row.getCell((short) 0);

        cell.setCellValue(new XSSFRichTextString("Matching Status: "));

        cell = row.getCell((short) 1);

        cell.setCellValue(new XSSFRichTextString("ALL"));

        row = sheet.getRow(7);
        cell = row.getCell(0);
        cell.setCellStyle(headerCellStyle);
        temp = new XSSFRichTextString("Results");
        temp.applyFont(header1);
        cell.setCellValue(temp);


        CellStyle yellowStyle = wb.createCellStyle();
        yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        yellowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        yellowStyle.setAlignment(CellStyle.ALIGN_CENTER);
        yellowStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        yellowStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        yellowStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        yellowStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        row = sheet.getRow(10);
        cell = row.getCell(1);
        temp = new XSSFRichTextString("ADYEN");
        temp.applyFont(header1);
        cell.setCellValue(temp);
        cell.setCellStyle(yellowStyle);

        for (int i = 10; i < 14; i++) {
            sheet.getRow(i).getCell(1).setCellStyle(yellowStyle);
        }

        CellStyle orangeStyle = wb.createCellStyle();
        orangeStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        orangeStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        orangeStyle.setAlignment(CellStyle.ALIGN_CENTER);
        orangeStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        orangeStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        orangeStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        orangeStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        row = sheet.getRow(10);
        cell = row.getCell(2);
        temp = new XSSFRichTextString("First File");
        temp.applyFont(header1);
        cell.setCellValue(temp);
        cell.setCellStyle(orangeStyle);

        for (int i = 2; i < 13; i++) {
            sheet.getRow(10).getCell(i).setCellStyle(orangeStyle);
        }

        CellStyle greenStyle = wb.createCellStyle();
        greenStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        greenStyle.setFillPattern((CellStyle.SOLID_FOREGROUND));
        greenStyle.setAlignment(CellStyle.ALIGN_CENTER);
        greenStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        greenStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        greenStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        greenStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        row = sheet.getRow(10);
        cell = row.getCell(14);
        temp = new XSSFRichTextString("Second File");
        temp.applyFont(header1);
        cell.setCellValue(temp);
        cell.setCellStyle(greenStyle);

        for (int i = 13; i < 40; i++) {
            sheet.getRow(10).getCell(i).setCellStyle(greenStyle);
        }

        CellStyle commonStyle = wb.createCellStyle();
        commonStyle.setAlignment(CellStyle.ALIGN_CENTER);
        commonStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        commonStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        commonStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        commonStyle.setBorderRight(CellStyle.BORDER_MEDIUM);

        rowNum = 11;
        colNum = 2;

        setCommonTemplate2(rowNum, colNum, "Batch Number <Internal>", sheet, commonStyle);
        colNum = (short) (colNum + 1);
        setCommonTemplate2(rowNum, colNum, "Status", sheet, commonStyle);
        colNum = (short) (colNum + 1);
        setCommonTemplate(rowNum, colNum, "Processed", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Error", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Matched", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Underpaid(Pending)", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Overpaid(Excess)", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate2(rowNum, colNum, "Date From Acquirer", sheet, commonStyle);
        colNum = (short) (colNum + 1);
        setCommonTemplate2(rowNum, colNum, "Batch Number From Acquirer", sheet, commonStyle);
        colNum = (short) (colNum + 1);
        setCommonTemplate(rowNum, colNum, "Payment to CDG Bank", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Transaction Value (Total individual trxs before MDR)", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Commission", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Markup", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Scheme Fee", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Interchange", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Chargeback", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Chargeback Reversed", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Refund", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Refund Reversed", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Other Misc (Debit)", sheet, commonStyle);
        colNum = (short) (colNum + 2);
        setCommonTemplate(rowNum, colNum, "Other Misc (Credit)", sheet, commonStyle);

        rowNum = (short) (rowNum + 3);
        sheet.createRow(rowNum);
        colNum = 1;
        sheet.getRow(rowNum).createCell(colNum);

        for (String var : sorted.keySet()) {
            Object[] obj = sorted.get(var);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] == null) {
                    colNum++;
                    sheet.getRow(rowNum).createCell(colNum);
                    sheet.getRow(rowNum).getCell(colNum).setCellStyle(commonStyle);
                    continue;
                }
                setCommonTemplate3(rowNum, colNum, obj[i], sheet, commonStyle);
                colNum++;
                sheet.getRow(rowNum).createCell(colNum);
            }
            rowNum++;
            sheet.createRow(rowNum);
            colNum = 1;
            sheet.getRow(rowNum).createCell(colNum);
        }

        for (int i = 0; i < 39; i++) {
            sheet.autoSizeColumn(i,true);
        }

    }

    public void generateBreakdownReport(XSSFWorkbook wb, List<TmtbNonBillableTxnCrca> breakdownReport) {

        XSSFSheet sheet = wb.createSheet(BREAKDOWN_REPORT);
        XSSFRow row = null;
        XSSFCell cell = null;
        Short rowNum = 0;
        Short colNum = 0;

        sheet.createRow(rowNum);
        for (int i = 0; i < 38; i++) {
            sheet.getRow(rowNum).createCell(i);
        }
        setCommonTemplate3(rowNum, colNum++, "Crca Id", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Psp Ref No", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Record Type", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Submission Merchant Id", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Batch Code", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Payment Method", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Gross Amount", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Gross Debit", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Gross Credit", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Net Debit", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Net Credit", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Commission", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Markup", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "SchemeFee", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Interchange", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "PaymentDate", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Payment Currency", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Transaction Amount", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Transaction Date", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Fee Code", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Fee Amount", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Discount Rate", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Discount Amount", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Chargeback No", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Chargeback Reason Code", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Chargeback Reason Description", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Service Fee Amount", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Tax Amount", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Net Amount", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Service Fee Rate", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Adjustment No", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Adjustment Reason Code", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Adjustment Reason Description", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Source", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Created Date", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Modified Date", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "File Name", sheet, null);
        setCommonTemplate3(rowNum, colNum++, "Upload Date", sheet, null);

        for (TmtbNonBillableTxnCrca crca : breakdownReport) {

            rowNum++;
            colNum = 0;

            sheet.createRow(rowNum);
            for (int i = 0; i < 38; i++) {
                sheet.getRow(rowNum).createCell(i);

            }

            setCommonTemplate3(rowNum, colNum++, crca.getCrcaId(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getPspRefNo(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getRecordType(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getSubmissionMerchantId(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getBatchCode(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getPaymentMethod(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getGrossAmount(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getGrossDebit(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getGrossCredit(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getNetDebit(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getNetCredit(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getCommission(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getMarkup(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getSchemeFee(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getInterchange(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getPaymentDate(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getPaymentCurrency(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getTransactionAmount(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getTransactionDate(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getFeeCode(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getFeeAmount(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getDiscountRate(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getDiscountAmount(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getChargebackNo(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getChargebackReasonCode(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getChargebackReasonDescription(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getServiceFeeAmount(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getTaxAmount(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getNetAmount(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getServiceFeeRate(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getAdjustmentNo(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getAdjustmentReasonCode(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getAdjustmentReasonDescription(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getSource(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getCreatedDt(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getModifiedDt(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getFileName(), sheet, null);
            setCommonTemplate3(rowNum, colNum++, crca.getUploadDate(), sheet, null);
        }

        for (int i = 0; i < 38; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    public void exportCSVResult(boolean showSummaryReport, boolean showBreakdownReport, Map<String, Object[]> processedResults, List<TmtbNonBillableTxnCrca> breakdownReport) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();

        if (showSummaryReport) {
            generateSummaryReport(wb, processedResults);
        }

        if (showBreakdownReport) {
            generateBreakdownReport(wb, breakdownReport);
        }


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            wb.write(bos);
        } finally {
            bos.close();
        }
        byte[] bytes = bos.toByteArray();
        AMedia media = new AMedia("Adyen_Payment_Matching_Report" + "." + com.cdgtaxi.ibs.report.Constants.EXTENSION_EXCEL, com.cdgtaxi.ibs.report.Constants.EXTENSION_EXCEL,
                com.cdgtaxi.ibs.report.Constants.FORMAT_EXCEL, bytes);
        Filedownload.save(media);

    }


    public void exportCSVResult(List<TmtbNonBillableTxn> txns) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        TmtbNonBillableTxn prevTxn = null;
        XSSFSheet sheet = null;
        int j = 0;
        for (TmtbNonBillableTxn txn : txns) {
            if (prevTxn == null || !prevTxn.getTmtbNonBillableBatch().equals(txn.getTmtbNonBillableBatch())) {


                wb.createSheet(txn.getTmtbNonBillableBatch().getBatchNo());

//                /**
//                 * Setting the width of the first three columns.
//                 */
//                sheet.setColumnWidth((short) 0, (short) 5000);
//                sheet.setColumnWidth((short) 1, (short) 7500);
//                sheet.setColumnWidth((short) 2, (short) 7500);
//                sheet.setColumnWidth((short) 3, (short) 5000);
//                sheet.setColumnWidth((short) 4, (short) 7500);

                /**
                 * Style for the header cells.
                 */
                XSSFCellStyle headerCellStyle = wb.createCellStyle();
                XSSFFont boldFont = wb.createFont();
                //boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                boldFont.setBold(true);
                headerCellStyle.setFont(boldFont);

                XSSFRow row = sheet.createRow(0);
                XSSFCell cell = row.createCell((short) 0);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Card No"));
                cell = row.createCell((short) 1);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Payment Type"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Trip Date Time"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Company Code"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Taxi No"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Driver ID"));
                cell = row.createCell((short) 6);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Fare Amount($)"));
                cell = row.createCell((short) 7);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Admin Fee($)"));
                cell = row.createCell((short) 8);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("GST($)"));
                cell = row.createCell((short) 9);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Total Amount($)"));
                cell = row.createCell((short) 10);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Offline"));
                cell = row.createCell((short) 11);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Job No"));
                cell = row.createCell((short) 12);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Psp Ref No 1"));
                cell = row.createCell((short) 13);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Txn Amount 1"));
                cell = row.createCell((short) 14);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Psp Ref No 2"));
                cell = row.createCell((short) 15);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Txn Amount 2"));
                cell = row.createCell((short) 16);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Txn Status"));
                cell = row.createCell((short) 17);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Matching Status"));
                cell = row.createCell((short) 18);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Acquirer"));
                cell = row.createCell((short) 19);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Payment Method 1"));
                cell = row.createCell((short) 20);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Gross Amount 1"));
                cell = row.createCell((short) 21);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Interchange 1"));
                cell = row.createCell((short) 22);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Scheme Fee 1"));
                cell = row.createCell((short) 23);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Markup 1"));
                cell = row.createCell((short) 24);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Commission 1"));
                cell = row.createCell((short) 25);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Gross Amount 2"));
                cell = row.createCell((short) 26);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Interchange 2"));
                cell = row.createCell((short) 27);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Scheme Fee 2"));
                cell = row.createCell((short) 28);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Markup 2"));
                cell = row.createCell((short) 29);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(new XSSFRichTextString("Commission 2"));
                ///////////////////////////////////////////////////////////////
                j = 0;

            }
            j++;
            XSSFRichTextString temp = null;
            XSSFRow row = sheet.createRow(j);
            XSSFCell cell = row.createCell((short) 0);
            temp = new XSSFRichTextString(txn.getCardNo());
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(temp);
            cell = row.createCell((short) 1);
            temp = new XSSFRichTextString(txn.getMstbAcquirerPymtType().getMstbMasterTable().getMasterValue());
            cell.setCellValue(temp);
            cell = row.createCell((short) 2);
            temp = new XSSFRichTextString(DateUtil.convertDateToStr(txn.getTripStartDt(), DateUtil.GLOBAL_DATE_FORMAT));
            cell.setCellValue(temp);
            cell = row.createCell((short) 3);
            temp = new XSSFRichTextString(txn.getMstbMasterTableByServiceProvider().getMasterValue());
            cell.setCellValue(temp);
            cell = row.createCell((short) 4);
            temp = new XSSFRichTextString(txn.getTaxiNo());
            cell.setCellValue(temp);
            cell = row.createCell((short) 5);
            temp = new XSSFRichTextString(StringUtil.maskNric(txn.getNric()));
            cell.setCellValue(temp);
            cell = row.createCell((short) 6);
            if (txn.getFareAmt() != null) {
                temp = new XSSFRichTextString(txn.getFareAmt().toString());
            }
            cell.setCellValue(temp);
            cell = row.createCell((short) 7);
            if (txn.getAdminFee() != null) {
                temp = new XSSFRichTextString(txn.getAdminFee().toString());
            }
            cell.setCellValue(temp);
            cell = row.createCell((short) 8);
            if (txn.getGst() != null) {
                temp = new XSSFRichTextString(txn.getGst().toString());
            }
            cell.setCellValue(temp);
            cell = row.createCell((short) 9);
            if (txn.getTotal() != null) {
                temp = new XSSFRichTextString(txn.getTotal().toString());
            }
            cell.setCellValue(temp);
            cell = row.createCell((short) 10);
            temp = new XSSFRichTextString(NonConfigurableConstants.BOOLEAN_YN.get(txn.getOfflineFlag()));
            cell.setCellValue(temp);
            cell = row.createCell((short) 11);
            temp = new XSSFRichTextString(txn.getJobNo());
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(temp);
            cell = row.createCell((short) 12);
            temp = new XSSFRichTextString(txn.getPspRefNo1());
            cell.setCellValue(temp);
            cell = row.createCell((short) 13);
            if (txn.getTxnAmount1() != null) {
                temp = new XSSFRichTextString(txn.getTxnAmount1().toString());
            }
            cell.setCellValue(temp);
            cell = row.createCell((short) 14);
            temp = new XSSFRichTextString(txn.getPspRefNo2());
            cell.setCellValue(temp);
            cell = row.createCell((short) 15);
            if (txn.getTxnAmount2() != null) {
                temp = new XSSFRichTextString(txn.getTxnAmount2().toString());
            }
            cell.setCellValue(temp);
            cell = row.createCell((short) 16);
            temp = new XSSFRichTextString(NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(txn.getStatus()));
            cell.setCellValue(temp);
            cell = row.createCell((short) 17);
            temp = new XSSFRichTextString(NonConfigurableConstants.AYDEN_MATCHING_STATUS.get(txn.getMatchingStatus()));
            cell.setCellValue(temp);


            prevTxn = txn;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            wb.write(bos);
        } finally {
            bos.close();
        }
        byte[] bytes = bos.toByteArray();
        AMedia media = new AMedia("Non_Billable_Batch" + "." + com.cdgtaxi.ibs.report.Constants.EXTENSION_EXCEL, com.cdgtaxi.ibs.report.Constants.EXTENSION_EXCEL,
                com.cdgtaxi.ibs.report.Constants.FORMAT_EXCEL, bytes);
        Filedownload.save(media);
    }

    private SearchNonBillableTxnsForm buildNonBillableTxnsSearchForm(List<NonBillableBatchDto> batchList) {
        SearchNonBillableTxnsForm form = new SearchNonBillableTxnsForm();
        List temp = new ArrayList<String>();

        if (matchingStatusListBox.getSelectedItem().getValue().toString().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ALL)) {
            temp.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED);
            temp.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR);
            temp.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_PENDING);
        } else {
            temp.add(matchingStatusListBox.getSelectedItem().getValue().toString());
        }

        form.jobNo = jobNoTextBox.getValue();
        form.pspRefNo = pspRefNoTextBox.getValue();
        form.batchNo = batchNoTextBox.getValue();
        form.aydenMerchantId = aydenMerchantIdTextBox.getValue();
        form.matchingStatuses = temp;
        form.addTmtbNonBillableBatches(batchList);

        temp = new ArrayList<String>();
        temp.add(NonConfigurableConstants.AMEX_RECORD_STATUS_TRANSACTION); //to exclude this transaction

        form.recordType = temp;

        return form;
    }

    private SearchNonBillableBatchForm buildNonBillableBatchSearchForm() {
        SearchNonBillableBatchForm form = new SearchNonBillableBatchForm();
        List temp = new ArrayList<String>();

        if (settlementStartDateDateBox.getValue() != null && settlementEndDateDateBox.getValue() != null) {
            form.uploadDateFrom = new Date(settlementStartDateDateBox.getValue().getTime());
            form.uploadDateTo = new Date(settlementEndDateDateBox.getValue().getTime());
        }

        form.batchNo = batchNoTextBox.getValue();


        return form;
    }

    public void reset() {

        settlementStartDateDateBox.setText("");
        settlementEndDateDateBox.setText("");
        batchNoTextBox.setText("");
        matchingStatusListBox.setSelectedIndex(0);
        reportFormatListBox.setSelectedIndex(0);
        reportTypeFormatListBox.setSelectedIndex(0);
    }


    @Override
    public void refresh() throws InterruptedException {
        // TODO Auto-generated method stub
    }

    private void setCommonTemplate(Short rowNum, Short colNum, String name, XSSFSheet sheet, CellStyle commonStyle) {

        XSSFRow row = sheet.getRow(rowNum);
        XSSFCell cell = row.getCell(colNum);
        XSSFRichTextString temp = new XSSFRichTextString(name);
        cell.setCellValue(temp);

        for (int i = rowNum; i < rowNum + 2; i++) {
            sheet.getRow(i).getCell(colNum).setCellStyle(commonStyle);
        }

        for (int i = colNum; i < colNum + 2; i++) {
            sheet.getRow(rowNum).getCell(i).setCellStyle(commonStyle);
            sheet.getRow(rowNum + 1).getCell(i).setCellStyle(commonStyle);
        }

        row = sheet.getRow(rowNum + 2);
        cell = row.getCell(colNum);
        temp = new XSSFRichTextString("Count");
        cell.setCellValue(temp);

        for (int i = rowNum + 2; i < rowNum + 3; i++) {
            sheet.getRow(i).getCell(colNum).setCellStyle(commonStyle);
        }

        row = sheet.getRow(rowNum + 2);
        cell = row.getCell(++colNum);
        temp = new XSSFRichTextString("Amt");
        cell.setCellValue(temp);

        for (int i = rowNum + 2; i < rowNum + 3; i++) {
            sheet.getRow(i).getCell(colNum).setCellStyle(commonStyle);
        }

    }

    private void setCommonTemplate2(Short rowNum, Short colNum, String name, XSSFSheet sheet, CellStyle commonStyle) {

        XSSFRow row = sheet.getRow(rowNum);
        XSSFCell cell = row.getCell(colNum);
        XSSFRichTextString temp = new XSSFRichTextString(name);
        cell.setCellValue(temp);

        for (int i = rowNum; i < rowNum + 3; i++) {
            sheet.getRow(i).getCell(colNum).setCellStyle(commonStyle);
        }
    }

    private void setCommonTemplate3(Short rowNum, Short colNum, Object name, XSSFSheet sheet, CellStyle commonStyle) {

        XSSFRow row = sheet.getRow(rowNum);
        XSSFCell cell = row.getCell(colNum);

        if (name instanceof java.util.Date) {
            name = DateUtil.convertDateToStrWithGDFormat((java.util.Date) name);
        }

        if(name != null) {
            XSSFRichTextString temp = new XSSFRichTextString(name.toString());
            cell.setCellValue(temp);
        }
        cell.setCellStyle(commonStyle);
    }

}