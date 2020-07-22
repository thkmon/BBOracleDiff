package com.bb.dbdiff.dbdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bb.dbdiff.util.StringUtil;

public class FunctionList extends ArrayList<Function> {
	
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
	 * 함수를 오름차순으로 정렬한다.
	 */
	public void sortByName() {
		Collections.sort(this, new Comparator<Function>() {

			@Override
			public int compare(Function o1, Function o2) {
				return StringUtil.compareStringForAsc(o1.getFunctionName(), o2.getFunctionName());
			}
		});
	}
}