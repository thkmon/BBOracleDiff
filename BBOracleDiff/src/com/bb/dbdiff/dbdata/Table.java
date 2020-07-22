package com.bb.dbdiff.dbdata;

public class Table {
	
	private String tableName = "";
	private ColumnList columnList = null;
	private IndexList indexList = null;
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[table] ");
		buff.append("tableName : ").append(tableName).append("\n");

		if (columnList != null && columnList.size() > 0) {
			buff.append(columnList.toString());
		}
		
		if (indexList != null && indexList.size() > 0) {
			buff.append(indexList.toString());
		}
		
		return buff.toString();
	}
	
	
	public Index findIndex(String indexName) {
		if (indexList == null || indexList.size() == 0) {
			return null;
		}
		
		Index oneIndex = null;
		int count = indexList.size();
		for (int i=0; i<count; i++) {
			oneIndex = indexList.get(i);
			if (oneIndex != null) {
				if (oneIndex.getIndexName() != null && oneIndex.getIndexName().equalsIgnoreCase(indexName)) {
					return oneIndex;
				}
			}
		}
		
		return null;
	}
	
	
	public String getTableName() {
		return tableName;
	}
	
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
	public ColumnList getColumnList() {
		return columnList;
	}
	
	
	public void setColumnList(ColumnList columnList) {
		this.columnList = columnList;
	}


	public IndexList getIndexList() {
		return indexList;
	}


	public void setIndexList(IndexList indexList) {
		this.indexList = indexList;
	}
	
	
	/**
	 * 인덱스 조건을 컬럼 위치 순으로 정렬한다.
	 */
	public void sortAllIndexListByName() {
		if (this.getIndexList() != null) {
			this.getIndexList().sortByName();
		}
	}
	
	
	/**
	 * 인덱스 조건을 컬럼 위치 순으로 정렬한다.
	 */
	public void sortAllIndexConditionsByColumnPosition() {
		if (this.getIndexList() != null) {
			this.getIndexList().sortAllIndexConditionsByColumnPosition();
		}
	}
}