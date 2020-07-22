package com.bb.dbdiff.dbdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bb.dbdiff.util.StringUtil;

public class IndexList extends ArrayList<Index> {

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
	 * 인덱스를 이름 기준으로 오름차순 정렬한다.
	 */
	public void sortByName() {
		Collections.sort(this, new Comparator<Index>() {

			@Override
			public int compare(Index o1, Index o2) {
				return StringUtil.compareStringForAsc(o1.getIndexName(), o2.getIndexName());
			}
		});
	}
	
	
	/**
	 * 인덱스 조건을 컬럼 위치 순으로 정렬한다.
	 */
	public void sortAllIndexConditionsByColumnPosition() {
		Index oneIndex = null;
		int count = this.size();
		for (int i=0; i<count; i++) {
			oneIndex = this.get(i);
			if (oneIndex != null) {
				oneIndex.sortAllIndexConditionsByColumnPosition();
			}
		}
	}
}