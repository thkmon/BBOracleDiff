package com.bb.dbdiff.dbdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bb.dbdiff.util.StringUtil;

public class ProcedureList extends ArrayList<Procedure> {

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
	 * 프로시저를 오름차순으로 정렬한다.
	 */
	public void sortByName() {
		Collections.sort(this, new Comparator<Procedure>() {

			@Override
			public int compare(Procedure o1, Procedure o2) {
				return StringUtil.compareStringForAsc(o1.getProcedureName(), o2.getProcedureName());
			}
		});
	}
}