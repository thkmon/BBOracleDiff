package com.bb.dbdiff.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.bb.dbdiff.dbdata.Database;
import com.bb.dbdiff.prototype.StringList;
import com.bb.dbdiff.prototype.StringMap;
import com.bb.dbdiff.prototype.StringMapList;

public class BBOracleMapper {
	
	private static boolean bInitDriver = false;
	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	public Connection getConnection(Database database) {

		Connection conn = null;
		
		try {
			if (!bInitDriver) {
				Class.forName(DRIVER);
				bInitDriver = true;
			}
			
			// url ex : "jdbc:oracle:thin:@000.000.000.000:1521:orcl"
			String url = "jdbc:oracle:thin:@" + database.getHost() + ":" + database.getPort() + ":" + database.getSid();
			conn = DriverManager.getConnection(url, database.getUser(), database.getPassword());

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return conn;
	}
	
	
	public StringMapList select(Connection conn, String sql, StringList bindList) throws Exception {
		
		StringMapList resultMapList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		int colCount = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			if (bindList != null && bindList.size() > 0) {
				int bindCount = bindList.size();
				for (int i=0; i<bindCount; i++) {
					pstmt.setString(i + 1, bindList.get(i));
				}
			}
			
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			colCount = rsmd.getColumnCount();
			
			while (rs.next()) {
				if (resultMapList == null) {
					resultMapList = new StringMapList();
				}
				
				StringMap oneMap = new StringMap();
				
				for (int i=0; i<colCount; i++) {
					oneMap.put(rsmd.getColumnName(i + 1).toLowerCase(), rs.getString(i + 1));
				}
				
				resultMapList.add(oneMap);
			}
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return resultMapList;
	}
	
	
	private void close(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			
		} catch (Exception e) {
		} finally {
			pstmt = null;
		}
	}
	
	
	private void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			
		} catch (Exception e) {
		} finally {
			rs = null;
		}
	}
}