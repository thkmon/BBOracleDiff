package com.bb.dbdiff.dbdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ColumnList extends ArrayList<Column> {
	
	
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
	 * 컬럼을 컬럼 아이디 순으로 정렬한다.
	 */
	public void sortByColumnId() {
		Collections.sort(this, new Comparator<Column>() {

			@Override
			public int compare(Column o1, Column o2) {
				int result = 0;
				if (o1 != null && o2 != null) {
					if (o1.getColumnId() < o2.getColumnId()) {
						result = -1;
					} else if (o1.getColumnId() > o2.getColumnId()) {
						result = 1;
					}
				}
				
				return result;
			}
		});
	}
}