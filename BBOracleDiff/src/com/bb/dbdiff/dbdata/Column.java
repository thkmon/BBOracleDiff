package com.bb.dbdiff.dbdata;

public class Column {

	private String tableName = "";
	
	private String columnName = "";
	private String dataType = "";
	private int dataLength = 0;
	private boolean nullable = true;
	private int columnId = 0;
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[column] ");
		buff.append("tableName : ").append(tableName).append(" / ");
		buff.append("columnName : ").append(columnName).append(" / ");
		buff.append("dataType : ").append(dataType).append(" / ");
		buff.append("dataLength : ").append(dataLength).append(" / ");
		// buff.append("nullable : ").append(nullable);
		buff.append("notNull : ").append(!nullable);
		return buff.toString();
	}
	

	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getColumnName() {
		return columnName;
	}


	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}


	public String getDataType() {
		return dataType;
	}


	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	public int getDataLength() {
		return dataLength;
	}


	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}


	public boolean isNullable() {
		return nullable;
	}


	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}


	public int getColumnId() {
		return columnId;
	}


	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
}