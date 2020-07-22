package com.bb.dbdiff.dbdata;

import com.bb.dbdiff.util.StringUtil;

public class IndexCondition {

	private String indexName = "";
	private String columnName = "";
	private String descend = "";
	private int columnPosition = 0;
	
	
	@Override
	public String toString() {
		return columnName + " " + descend;
	}
	
	
	public String getIndexName() {
		return indexName;
	}
	
	
	public void setIndexName(String indexName) {
		indexName = StringUtil.reviseColumnName(indexName);
		this.indexName = indexName;
	}
	
	
	public String getColumnName() {
		return columnName;
	}
	
	
	public void setColumnName(String columnName) {
		columnName = StringUtil.reviseColumnName(columnName);
		this.columnName = columnName;
	}
	
	
	public String getDescend() {
		return descend;
	}
	
	
	public void setDescend(String descend) {
		this.descend = descend;
	}


	public int getColumnPosition() {
		return columnPosition;
	}


	public void setColumnPosition(int columnPosition) {
		this.columnPosition = columnPosition;
	}
}