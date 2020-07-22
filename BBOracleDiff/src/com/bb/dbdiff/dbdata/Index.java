package com.bb.dbdiff.dbdata;

import com.bb.dbdiff.util.StringUtil;

public class Index {
	private String tableName = "";
	
	private String indexName = "";
	private IndexConditionList indexConditionList = null;

	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[index] ");
		buff.append("tableName : ").append(tableName).append(" / ");
		buff.append("indexName : ").append(indexName).append(" / ");
		buff.append("indexConditionList : ").append(indexConditionList);
		return buff.toString();
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getIndexName() {
		return indexName;
	}


	public void setIndexName(String indexName) {
		indexName = StringUtil.reviseColumnName(indexName);
		this.indexName = indexName;
	}


	public IndexConditionList getIndexConditionList() {
		return indexConditionList;
	}


	public void setIndexConditionList(IndexConditionList indexConditionList) {
		this.indexConditionList = indexConditionList;
	}
	
	
	/**
	 * 인덱스 조건을 컬럼 위치 순으로 정렬한다.
	 */
	public void sortAllIndexConditionsByColumnPosition() {
		if (this.getIndexConditionList() != null) {
			this.getIndexConditionList().sortByColumnPosition();
		}
	}
}