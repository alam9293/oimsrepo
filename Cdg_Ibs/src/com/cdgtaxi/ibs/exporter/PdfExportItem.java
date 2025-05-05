package com.cdgtaxi.ibs.exporter;

public class PdfExportItem {

	private Object listGrid;
	private String title;
	private int[] excludeColumns;
	
	
	public PdfExportItem(Object listGrid, String title, int[] excludeColumns) {
		super();
		this.listGrid = listGrid;
		this.title = title;
		this.excludeColumns = excludeColumns;
	}
	
	public Object getListGrid() {
		return listGrid;
	}
	public void setListGrid(Object listGrid) {
		this.listGrid = listGrid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int[] getExcludeColumns() {
		return excludeColumns;
	}
	public void setExcludeColumns(int[] excludeColumns) {
		this.excludeColumns = excludeColumns;
	}
	
	
}
