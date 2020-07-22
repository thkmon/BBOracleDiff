package com.bb.dbdiff.sql;

import java.sql.Connection;

public class SqlUtil {

	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
			
		} catch (Exception e) {
		} finally {
			conn = null;
		}
	}
}
