package com.bb.dbdiff.dbdata;

public class View {
	
	private String viewName = "";
	private String status = "";
	private ColumnList columnList = null;
	
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[view] ");
		buff.append("viewName : ").append(viewName);
		
		if (status != null && !status.equalsIgnoreCase("VALID")) {
			buff.append(" (").append(status).append(")");
		}
		
		buff.append("\n");
		
		if (columnList != null && columnList.size() > 0) {
			buff.append(columnList.toString());
		}
		
		return buff.toString();
	}
	
	
	public String getViewName() {
		return viewName;
	}
	
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public ColumnList getColumnList() {
		return columnList;
	}
	
	
	public void setColumnList(ColumnList columnList) {
		this.columnList = columnList;
	}
}