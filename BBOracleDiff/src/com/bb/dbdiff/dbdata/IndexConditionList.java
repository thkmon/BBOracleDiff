package com.bb.dbdiff.dbdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IndexConditionList extends ArrayList<IndexCondition> {
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		
		int count = this.size();
		int lastIndex = count - 1;
		for (int i=0; i<count; i++) {
			buff.append(this.get(i).toString());
			if (i < lastIndex) {
				buff.append(", ");
			}
		}
		
		return buff.toString();
	}
	
	
	/**
	 * 인덱스 조건을 컬럼 위치 순으로 정렬한다.
	 */
	public void sortByColumnPosition() {
		Collections.sort(this, new Comparator<IndexCondition>() {

			@Override
			public int compare(IndexCondition o1,IndexCondition o2) {
				int result = 0;
				if (o1 != null && o2 != null) {
					if (o1.getColumnPosition() < o2.getColumnPosition()) {
						result = -1;
					} else if (o1.getColumnPosition() > o2.getColumnPosition()) {
						result = 1;
					}
				}
				
				return result;
			}
		});
	}
}