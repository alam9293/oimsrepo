package com.cdgtaxi.ibs.exporter;

import java.awt.Color;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ZkPdfExporter {

	private Color defaultBorderColor = WebColors.getRGBColor("#CFCFCF");
	private Color defaultOddRowBackgroundColor = WebColors.getRGBColor("#F7F7F7");
	private Color defaultFooterBackgroundColor = WebColors.getRGBColor("#F9F9F9");
	private Color defaultHeaderBackgroundColor = WebColors.getRGBColor("#e5e5e5");

	protected float defaultFontSize = 8;
	protected Color defaultFontColor = WebColors.getRGBColor("#636363");
	protected String comboAccountName = "";
	private Rectangle pageSize;

	public ZkPdfExporter() {

	}
	

	public ZkPdfExporter(Rectangle pageSize) {
		super();
		this.pageSize = pageSize;
	}

	public Rectangle getPageSize() {
		return pageSize;
	}

	public void setPageSize(Rectangle pageSize) {
		this.pageSize = pageSize;
	}
	public void export(List<PdfExportItem> items, OutputStream outputStream, String enterAcctName) throws Exception {
		comboAccountName = enterAcctName;
		export(items, outputStream);
	}
	public void export(List<PdfExportItem> items, OutputStream outputStream, float fontSize) throws Exception {
		defaultFontSize = fontSize;
		export(items, outputStream);
	}

	public void export(List<PdfExportItem> items, OutputStream outputStream) throws Exception {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, outputStream);
			if (pageSize != null) {
				document.setPageSize(pageSize);
			} else {
				document.setPageSize(PageSize.A4);
			}
			document.open();
			for (PdfExportItem item : items) {
				if (item.getListGrid() instanceof Grid) {
					export((Grid)item.getListGrid(),item.getTitle(),document);
				} else if  (item.getListGrid() instanceof Listbox) {
					export((Listbox)item.getListGrid(),item.getTitle(),document, item.getExcludeColumns());
				} else {
					throw new Exception("Only Grid and Listbox is supported to export PDF!");
				}
			}
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	private void export(Grid grid, String title, Document document) {
		try {

			int columnSize = grid.getColumns().getChildren().size();
			PdfPTable table = getPdfPTableForGrid(columnSize);
			if (StringUtils.isNotBlank(title)) {
				exportTitleRows(columnSize, title, table);
			}
			exportRows(columnSize, grid.getRows(), table, title);
			document.add(table);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void export(Listbox listbox,String title, Document document, int[] excludeColumns) {
		try {
			Listhead head = (Listhead) listbox.getHeads().iterator().next();
			int columnSize = 0;
			if (excludeColumns != null) {
				columnSize = head.getChildren().size() - excludeColumns.length;
			} else {
				columnSize = head.getChildren().size();
			}
			PdfPTable table = getPdfPTableForList(columnSize);
			if (StringUtils.isNotBlank(title)) {
				exportTitleRows(columnSize, title, table);
			}
			exportColumnHeaders(head.getChildren(), table, excludeColumns);
			int rowIndex = 0;
			for (Listitem row : (List<Listitem>) listbox.getItems()) {
				rowIndex++;
				exportRows(columnSize, row, table, rowIndex, excludeColumns);
			}
			document.add(table);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

//	public void export(Grid grid, OutputStream outputStream) {
//		try {
//			Document document = new Document();
//			PdfWriter.getInstance(document, outputStream);
//			document.open();
//
//			int columnSize = grid.getColumns().getChildren().size();
//			PdfPTable table = getPdfPTable(columnSize);
//
//			// if (getInterceptor() != null)
//			// getInterceptor().beforeRendering(table);
//			exportRows(columnSize, grid.getRows(), table);
//
//			document.add(table);
//			document.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public void export(Listbox listbox, OutputStream outputStream) {
//		try {
//			Document document = new Document();
//			PdfWriter.getInstance(document, outputStream);
//			document.setMargins(15, 15, 15, 15);
//			document.open();
//
//			Listhead head = (Listhead) listbox.getHeads().iterator().next();
//			int columnSize = head.getChildren().size();
//			PdfPTable table = getPdfPTable(columnSize);
//			exportColumnHeaders(head.getChildren(), table, null);
//			// if (getInterceptor() != null)
//			// getInterceptor().beforeRendering(table);
//			int rowIndex = 0;
//			for (Listitem row : (List<Listitem>) listbox.getItems()) {
//				rowIndex++;
//				exportRows(columnSize, row, table, rowIndex, null);
//			}
//			document.add(table);
//			document.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


	private PdfPTable getPdfPTableForList(int columnSize) {
		PdfPTable table = new PdfPTable(columnSize);
		table.setHeaderRows(2);
		table.setWidthPercentage(100);
		table.setSpacingAfter((float)20);
		return table;
	}
	
	private PdfPTable getPdfPTableForGrid(int columnSize) {
		PdfPTable table = new PdfPTable(columnSize);;
		table.setWidthPercentage(100);
		table.setSpacingAfter((float)20);
		return table;
	}

//	private void exportColumnHeaders(List<Listheader> headers, PdfPTable table) {
//		for (Listheader c : headers) {
//			String label = getStringValue(c);
//
//			PdfPCell cell = getHeaderCell();
//			cell.setPhrase(new Phrase(label, getHeaderFont()));
//			syncAlignment(c, cell);
//			table.addCell(cell);
//		}
//		table.completeRow();
//	}

	private void exportColumnHeaders(List<Listheader> headers, PdfPTable table, int[] excludeColumns) {
		List<Listheader> filteredHeaders = new ArrayList<Listheader>();
		filteredHeaders.addAll(headers);
		if (excludeColumns != null) {
			for (int i : excludeColumns) {
				filteredHeaders.remove(i);
			}
		}
		for (int i = 0; i < filteredHeaders.size();i ++ ) {
			Listheader c = filteredHeaders.get(i);
			String label = getStringValue(c);

			PdfPCell cell = getHeaderCell();
			cell.setPhrase(new Phrase(label.trim(), getHeaderFont()));
			syncAlignment(c, cell);
			table.addCell(cell);
		}
		table.completeRow();
	}
	
	private void exportRows(int columnSize, Rows rows, PdfPTable table, String title) {
		int rowIndex = 0;
		for (Row row : (List<Row>) rows.getChildren()) {
			if (row.isVisible()) {
				exportCells(rowIndex++, columnSize, row, table, title);
			}
		}
	}

	
	private void exportTitleRows(int columnSize, String title, PdfPTable table) {
		PdfPCell cell = getTitleCell();
		cell.setColspan(columnSize);
		cell.setPhrase(new Phrase(title, getHeaderFont()));
		table.addCell(cell);
		table.completeRow();
	}

	private void exportRows(int columnSize, Listitem listitem, PdfPTable table, int rowIndex, int[] excludeColumns) {
		List<Listcell> cells =  new ArrayList<Listcell>();
		cells.addAll((List<Listcell>) listitem.getChildren());
		if (excludeColumns != null) {
			for (int i : excludeColumns) {
				cells.remove(i);
			}
		}
		for (Listcell cell : cells) {
			exportCells(rowIndex, columnSize, cell, table);
		}
		table.completeRow();
	}

	protected void exportCells(int rowIndex, int columnSize, Row row, PdfPTable table, String title) {

		List<Component> children = row.getChildren();
		
		for (int c = 0; c < columnSize; c++) {
			Component cmp = c < children.size() ? children.get(c) : null;

			if (cmp == null) {
				PdfPCell cell = getCell(false);
				cell.setColspan(columnSize - c);
				table.addCell(cell);
				return;
			} 

				PdfPCell cell = getCell(isOddRow(rowIndex));
				String result = StringUtils.trim(getStringValue(cmp));
				
				//remove Certain String
				if(title.equalsIgnoreCase("Transaction Details"))
					result = removeSpecificString(result,"yes");
				else
					result = removeSpecificString(result,"no");
				
				cell.setPhrase(new Phrase(result, getCellFont()));
	
				syncCellColSpan(cmp, cell);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				//syncAlignment(cmp, cell);
				table.addCell(cell);
			
		}
		table.completeRow();
	}

	protected void exportCells(int rowIndex, int columnSize, Listcell listcell, PdfPTable table) {

		PdfPCell cell = getCell(isOddRow(rowIndex));
		cell.setPhrase(new Phrase(StringUtils.trim(getStringValue(listcell)), getCellFont()));

		syncAlignment(listcell, cell);
		table.addCell(cell);

	}


	private PdfPCell getCell(boolean isOddRow) {
		PdfPCell cell = getDefaultPdfPCell();
//		if (isOddRow)
//			cell.setBackgroundColor(defaultOddRowBackgroundColor);
		return cell;
	}

	private PdfPCell getDefaultPdfPCell() {
		PdfPCell cell = new PdfPCell();
		cell.setBorderColor(defaultBorderColor);
		cell.setPaddingTop(3);
		cell.setPaddingRight(3);
		cell.setPaddingBottom(3);
		cell.setPaddingLeft(3);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		return cell;
	}

	public PdfPCell getHeaderCell() {
		PdfPCell cell = new PdfPCell();
		cell.setPaddingTop(8);
		cell.setPaddingRight(4);
		cell.setPaddingBottom(7);
		cell.setPaddingLeft(6);

		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(defaultHeaderBackgroundColor);
		cell.setBorderColor(defaultBorderColor);
		return cell;
	}
	
	public PdfPCell getTitleCell() {
		PdfPCell cell = new PdfPCell();
		cell.setPaddingTop(8);
		cell.setPaddingRight(4);
		cell.setPaddingBottom(7);
		cell.setPaddingLeft(6);

		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(defaultHeaderBackgroundColor);
		cell.setBorderColor(defaultBorderColor);
		return cell;
	}

	private boolean isOddRow(int rowIndex) {
		return rowIndex % 2 == 1;
	}

	protected Font getHeaderFont() {
		Font font = getDefaultFont();
		font.setStyle(Font.BOLD);
		return font;
	}

	protected Font getCellFont() {
		return getDefaultFont();
	}

	protected Font getFooterFont() {
		return getDefaultFont();
	}

	protected Font getDefaultFont() {
		Font font = new Font();
		font.setColor(defaultFontColor);
		font.setSize(defaultFontSize);
		return font;
	}

	private String getStringValue(Component component) {
		String result = "";
		// Loop child if component is not drop down and have child.
		if (!(component instanceof Listbox && "select".equalsIgnoreCase(component.getMold())) && component.getChildren().size() > 0){
			for (Component comp : (List<Component>)component.getChildren()) {
					
					//to Solve comboItem the accountName dropdown issue..
					if (comp instanceof Comboitem) {
						result = comboAccountName.trim();
						break;
					}

					String smallResult = getStringValue(comp);
					
					if (StringUtils.isNotBlank(smallResult)) {
						result += smallResult + " ";
					}
			}
		} else {
			// If component is drop down, just take selected value
			if (component instanceof Listbox && "select".equalsIgnoreCase(component.getMold())) {
				if(null != ((Listbox)component).getSelectedItem())	{
					if(component.isVisible())
						result += ((Listbox)component).getSelectedItem().getLabel();
					else
						result += "";
				}else
					result += "-";
			} else {
				if(component.isVisible()) {
					Object getterResultCheck = invokeComponentGetter(component, "isChecked");
					if (getterResultCheck != null) {
						if(getterResultCheck.toString().equals("true"))
							result+= "YES";
						else if(getterResultCheck.toString().equals("false"))
							result+= "NO";
					}
					else {
						Object getterResult = invokeComponentGetter(component, "getLabel", "getText", "getValue");
						if (getterResult != null) {
								result += (String) getterResult;
						} else {
							result += "";
						}
					}
				}
				else
					result += "";
			}
		}
		return result;
	}

	public static Object invokeComponentGetter(Component target, String... methods) {
		Class<? extends Component> cls = target.getClass();
		for (String methodName : methods) {
			try {
				Method method = cls.getMethod(methodName, null);
				Object ret = method.invoke(target, null);
				if (ret != null)
					return ret;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
		return null;
	}

	private int syncCellColSpan(Component cmp, PdfPCell cell) {
		int span = 1;
		Object spanVal = invokeComponentGetter(cmp, "getColspan", "getSpan");
		if (spanVal != null && spanVal instanceof Number)
			span = ((Number) spanVal).intValue();
		return span;
	}

	private boolean syncAlignment(Component cmp, PdfPCell cell) {
		if (cmp == null)
			return false;

		final String align = getAlign(cmp);
//		if ("center".equals(align)) {
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			return true;
//		} else if ("right".equals(align)) {
//			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			return true;
//		} else if ("left".equals(align)) {
//			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//			return true;
//		} else {
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			return true;
//		}
	}
	
	private String getAlign(Component cmp) {
		return (String) invokeComponentGetter(cmp, "getAlign");
	}
	
	private String removeSpecificString(String aString, String type) {
	

		if(type.equalsIgnoreCase("YES"))
		{
			String[] toRemoves = {"View History", "Search Reset", "Submit Back Export To PDF" };
			
			for(String toRemove : toRemoves){
				aString = aString.replaceAll(toRemove, "");
			}
		}
		else
		{
			String[] toRemoves = {"View History", "Search Reset", "Submit Back Export To PDF" , "\\*"};
		
			for(String toRemove : toRemoves){
				aString = aString.replaceAll(toRemove, "");
			}
		}
		return aString.trim(); 
	}
}
