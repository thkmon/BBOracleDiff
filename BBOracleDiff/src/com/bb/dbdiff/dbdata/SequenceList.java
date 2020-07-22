package com.bb.dbdiff.dbdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bb.dbdiff.util.StringUtil;

public class SequenceList extends ArrayList<Sequence> {

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
	 * 시퀀스를 이름 기준으로 오름차순 정렬한다.
	 */
	public void sortByName() {
		Collections.sort(this, new Comparator<Sequence>() {

			@Override
			public int compare(Sequence o1, Sequence o2) {
				return StringUtil.compareStringForAsc(o1.getSequenceName(), o2.getSequenceName());
			}
		});
	}
}