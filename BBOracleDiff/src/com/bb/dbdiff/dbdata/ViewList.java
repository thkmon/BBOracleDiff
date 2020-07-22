package com.bb.dbdiff.dbdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bb.dbdiff.util.StringUtil;

public class ViewList extends ArrayList<View> {

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
	 * 뷰를 이름으로 찾는다.
	 * 
	 * @param tableName
	 * @return
	 */
	public View findView(String viewName) {
		View oneView = null;
		int count = this.size();
		for (int i=0; i<count; i++) {
			oneView = this.get(i);
			if (oneView != null) {
				if (oneView.getViewName() != null && oneView.getViewName().equalsIgnoreCase(viewName)) {
					return oneView;
				}
			}
		}
		
		return null;
	}
	
	
	/**
	 * 뷰 내의 모든 컬럼을 컬럼 아이디 순으로 정렬한다.
	 */
	public void sortAllColumnsByColumnId() {
		View oneView = null;
		int count = this.size();
		for (int i=0; i<count; i++) {
			oneView = this.get(i);
			if (oneView != null && oneView.getColumnList() != null) {
				oneView.getColumnList().sortByColumnId();
			}
		}
	}
	
	
	/**
	 * 뷰를 이름 기준으로 오름차순 정렬한다.
	 */
	public void sortByName() {
		Collections.sort(this, new Comparator<View>() {

			@Override
			public int compare(View o1, View o2) {
				return StringUtil.compareStringForAsc(o1.getViewName(), o2.getViewName());
			}
		});
	}
}