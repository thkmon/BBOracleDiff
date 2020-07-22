package com.bb.dbdiff.util;

public class StringUtil {

	public static String parseString(Object obj) {
		if (obj == null) {
			return "";
		}
		
		String str = "";
		try {
			str = String.valueOf(obj);
		} catch (Exception e) {
			str = "";
		}
		
		return str;
	}
	
	
	public static int parseInt(Object obj) {
		if (obj == null) {
			return 0;
		}
		
		int result = 0;
		try {
			result = Integer.parseInt(String.valueOf(obj));
		} catch (Exception e) {
			result = 0;
		}
		
		return result;
	}
	
	
	/**
	 * 문자열 오름차순 정렬을 위한 비교 메서드
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int compareStringForAsc(String s1, String s2) {
        int result = 0;
        
        if (s1 == null) {
            s1 = "";
        }
        
        if (s2 == null) {
            s2 = "";
        }
        
        int len1 = s1.length();
        int len2 = s2.length();
        int len = (len1 < len2) ? len1 : len2;
        for (int i=0; i<len; i++) {
            if (s1.charAt(i) > s2.charAt(i)) {
                result = 1;
                break;
            } else if (s1.charAt(i) < s2.charAt(i)) {
                result = -1;
                break;
            }
        }
        
        if (result == 0) {
            if (len1 > len2) {
                result = 1;
            } else if (len1 < len2) {
                result = -1;
            }
        }
        
        return result;
    }
	
	
	public static String reviseColumnName(String columnName) {
		if (columnName == null) {
			columnName = "";
		}
		
		if (columnName.matches("SYS_[A-Z0-9$]*")) {
			columnName = "SYS_";
		}
		
		return columnName;
	}
}