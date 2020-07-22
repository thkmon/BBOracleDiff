package com.bb.dbdiff.dbdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bb.dbdiff.util.StringUtil;

public class TableList extends ArrayList<Table> {
	
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		
		int count = this.size();
		for (int i=0; i<count; i++) {
			buff.append(this.get(i).toString());
			buff.append("\n");
		}
		
		return buff.toString();
	}
	
	
	/**
	 * 테이블을 이름으로 찾는다.
	 * 
	 * @param tableName
	 * @return
	 */
	public Table findTable(String tableName) {
		Table oneTable = null;
		int count = this.size();
		for (int i=0; i<count; i++) {
			oneTable = this.get(i);
			if (oneTable != null) {
				if (oneTable.getTableName() != null && oneTable.getTableName().equalsIgnoreCase(tableName)) {
					return oneTable;
				}
			}
		}
		
		return null;
	}
	
	
	/**
	 * 테이블을 이름 기준으로 오름차순 정렬한다.
	 */
	public void sortByName() {
		Collections.sort(this, new Comparator<Table>() {

			@Override
			public int compare(Table o1, Table o2) {
				return StringUtil.compareStringForAsc(o1.getTableName(), o2.getTableName());
			}
		});
	}
	
	
	/**
	 * 테이블 내의 모든 컬럼을 컬럼 아이디 순으로 정렬한다.
	 */
	public void sortAllColumnsByColumnId() {
		Table oneTable = null;
		int count = this.size();
		for (int i=0; i<count; i++) {
			oneTable = this.get(i);
			if (oneTable != null && oneTable.getColumnList() != null) {
				oneTable.getColumnList().sortByColumnId();
			}
		}
	}
	
	
	/**
	 * 인덱스를 이름 기준으로 오름차순 정렬한다.
	 */
	public void sortAllIndexListByName() {
		Table oneTable = null;
		int count = this.size();
		for (int i=0; i<count; i++) {
			oneTable = this.get(i);
			if (oneTable != null) {
				oneTable.sortAllIndexListByName();
			}
		}
	}
	
	
	/**
	 * 인덱스 조건을 컬럼 위치 순으로 정렬한다.
	 */
	public void sortAllIndexConditionsByColumnPosition() {
		Table oneTable = null;
		int count = this.size();
		for (int i=0; i<count; i++) {
			oneTable = this.get(i);
			if (oneTable != null) {
				oneTable.sortAllIndexConditionsByColumnPosition();
			}
		}
	}
}