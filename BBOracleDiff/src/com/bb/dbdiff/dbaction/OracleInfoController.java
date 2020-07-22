package com.bb.dbdiff.dbaction;

import java.sql.Connection;

import com.bb.dbdiff.dbdata.Column;
import com.bb.dbdiff.dbdata.ColumnList;
import com.bb.dbdiff.dbdata.Database;
import com.bb.dbdiff.dbdata.Function;
import com.bb.dbdiff.dbdata.FunctionList;
import com.bb.dbdiff.dbdata.Index;
import com.bb.dbdiff.dbdata.IndexCondition;
import com.bb.dbdiff.dbdata.IndexConditionList;
import com.bb.dbdiff.dbdata.IndexList;
import com.bb.dbdiff.dbdata.Procedure;
import com.bb.dbdiff.dbdata.ProcedureList;
import com.bb.dbdiff.dbdata.Sequence;
import com.bb.dbdiff.dbdata.SequenceList;
import com.bb.dbdiff.dbdata.Table;
import com.bb.dbdiff.dbdata.TableList;
import com.bb.dbdiff.dbdata.View;
import com.bb.dbdiff.dbdata.ViewList;
import com.bb.dbdiff.prototype.StringMap;
import com.bb.dbdiff.prototype.StringMapList;
import com.bb.dbdiff.sql.BBOracleMapper;
import com.bb.dbdiff.sql.SqlUtil;
import com.bb.dbdiff.util.StringUtil;

public class OracleInfoController {
	
	
	public Database getOracleDatabaseInfo(String host, String port, String sid, String user, String password) {
		if (host == null || host.trim().length() == 0) {
			System.err.println("getOracleDatabaseInfo : host is null or empty.");
			return null;
		} else {
			host = host.trim();
		}
		
		if (port == null || port.trim().length() == 0) {
			System.err.println("getOracleDatabaseInfo : port is null or empty.");
			return null;
		} else {
			port = port.trim();
		}
		
		if (sid == null || sid.trim().length() == 0) {
			System.err.println("getOracleDatabaseInfo : sid is null or empty.");
			return null;
		} else {
			sid = sid.trim();
		}
		
		if (user == null || user.trim().length() == 0) {
			System.err.println("getOracleDatabaseInfo : user is null or empty.");
			return null;
		} else {
			user = user.trim();
		}
		
		if (password == null || password.trim().length() == 0) {
			password = "";
		} else {
			password = password.trim();
		}
		
		Database database = null;
		Connection conn = null;
		
		try {
			database = makeDatabase(host, port, sid, user, password);
			
			BBOracleMapper bbOraMapper = new BBOracleMapper();
			conn = bbOraMapper.getConnection(database);
			
			// 테이블 가져오기
			String tableSql = " SELECT table_name FROM TABS ";
			StringMapList tableMapList = bbOraMapper.select(conn, tableSql, null);
			TableList tableList = makeTableList(tableMapList);
			database.setTableList(tableList);
			
			// 컬럼 가져오기
			// ALL_TAB_COLUMNS : 계정 상관없이 모든 컬럼 목록 (계정이 많을 경우 전체 셀렉트 엄청나게 느림)
			// USER_TAB_COLUMNS : 접속한 해당 계정의 모든 컬럼 목록
			String columnSql = " SELECT table_name, column_name, data_type, data_length, nullable, column_id FROM USER_TAB_COLUMNS ";
			StringMapList columnMapList = bbOraMapper.select(conn, columnSql, null);
			setColumnListToTableList(columnMapList, tableList);
			
			// 인덱스 가져오기
			// ALL_IND_COLUMNS : 계정 상관없이 모든 인덱스 (계정이 많을 경우 전체 셀렉트 엄청나게 느림)
			// USER_IND_COLUMNS : 접속한 해당 계정의 모든 인덱스 목록
			String indexSql = " SELECT index_name, table_name, column_name, descend, column_position FROM USER_IND_COLUMNS ";
			StringMapList indexMapList = bbOraMapper.select(conn, indexSql, null);
			setIndexListToTableList(indexMapList, tableList);
			
			// ALL_OBJECTS
			// DBA_OBJECTS
			// USER_OBJECTS
			
			// 뷰 가져오기
			String viewSql = " SELECT object_name, status FROM USER_OBJECTS where object_type = 'VIEW' ";
			StringMapList viewMapList = bbOraMapper.select(conn, viewSql, null);
			ViewList viewList = makeViewList(viewMapList);
			setColumnListToViewList(columnMapList, viewList);
			database.setViewList(viewList);

			// 시퀀스 가져오기
			String sequenceSql = " SELECT object_name, status FROM USER_OBJECTS where object_type = 'SEQUENCE' ";
			StringMapList sequenceMapList = bbOraMapper.select(conn, sequenceSql, null);
			SequenceList sequenceList = makeSequenceList(sequenceMapList);
			database.setSequenceList(sequenceList);
			
			// 함수 가져오기
			String functionSql = " SELECT object_name, status FROM USER_OBJECTS where object_type = 'FUNCTION' ";
			StringMapList functionMapList = bbOraMapper.select(conn, functionSql, null);
			FunctionList functionList = makeFunctionList(functionMapList);
			database.setFunctionList(functionList);
			
			// 프로시저 가져오기
			String procedureSql = " SELECT object_name, status FROM USER_OBJECTS where object_type = 'PROCEDURE' ";
			StringMapList procedureMapList = bbOraMapper.select(conn, procedureSql, null);
			ProcedureList procedureList = makeProcedureList(procedureMapList);
			database.setProcedureList(procedureList);
			
			SqlUtil.close(conn);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			SqlUtil.close(conn);
		}
		
		return database;
	}
	
	
	private Database makeDatabase(String host, String port, String sid, String user, String password) {
		Database database = new Database(host, port, sid, user, password);
		return database;
	}
	
	
	private TableList makeTableList(StringMapList tableMapList) {
		if (tableMapList == null || tableMapList.size() == 0) {
			return null;
		}
		
		TableList tableList = new TableList();
		
		StringMap oneMap = null;
		String oneTableName = "";
		int count = tableMapList.size();
		for (int i=0; i<count; i++) {
			oneMap = tableMapList.get(i);
			if (oneMap == null) {
				continue;
			}
			
			oneTableName = StringUtil.parseString(oneMap.get("table_name"));
			if (oneTableName == null || oneTableName.length() == 0) {
				continue;
			}
			
			Table table = new Table();
			table.setTableName(oneTableName);
			
			tableList.add(table);
		}
		
		tableList.sortByName();
		return tableList;
	}
	
	
	private void setColumnListToTableList(StringMapList columnMapList, TableList tableList) {
		if (columnMapList == null || columnMapList.size() == 0) {
			return;
		}
		
		if (tableList == null || tableList.size() == 0) {
			return;
		}
		
		StringMap oneMap = null;
		String oneTableName = "";
		int count = columnMapList.size();
		for (int i=0; i<count; i++) {
			oneMap = columnMapList.get(i);
			if (oneMap == null) {
				continue;
			}
			
			oneTableName = StringUtil.parseString(oneMap.get("table_name"));
			if (oneTableName == null || oneTableName.length() == 0) {
				continue;
			}
			
			Table oneTable = tableList.findTable(oneTableName);
			if (oneTable == null) {
				continue;
			}
			
			String columnName = StringUtil.parseString(oneMap.get("column_name"));
			String dataType = StringUtil.parseString(oneMap.get("data_type"));
			int dataLength = StringUtil.parseInt(oneMap.get("data_length"));
			boolean nullable = StringUtil.parseString(oneMap.get("nullable")).equalsIgnoreCase("Y");
			int columnId = StringUtil.parseInt(oneMap.get("column_id"));
			
			Column column = new Column();
			column.setTableName(oneTableName);
			column.setColumnName(columnName);
			column.setDataType(dataType);
			column.setDataLength(dataLength);
			column.setNullable(nullable);
			column.setColumnId(columnId);
			
			if (oneTable.getColumnList() == null) {
				oneTable.setColumnList(new ColumnList());
			}
			
			oneTable.getColumnList().add(column);
		}
		
		// 테이블 내의 모든 컬럼을 컬럼 아이디 순으로 정렬한다.
		tableList.sortAllColumnsByColumnId();
	}
	
	
	private void setColumnListToViewList(StringMapList columnMapList, ViewList viewList) {
		if (columnMapList == null || columnMapList.size() == 0) {
			return;
		}
		
		if (viewList == null || viewList.size() == 0) {
			return;
		}
		
		StringMap oneMap = null;
		String oneViewName = "";
		int count = columnMapList.size();
		for (int i=0; i<count; i++) {
			oneMap = columnMapList.get(i);
			if (oneMap == null) {
				continue;
			}
			
			oneViewName = StringUtil.parseString(oneMap.get("table_name"));
			if (oneViewName == null || oneViewName.length() == 0) {
				continue;
			}
			
			View oneView = viewList.findView(oneViewName);
			if (oneView == null) {
				continue;
			}
			
			String columnName = StringUtil.parseString(oneMap.get("column_name"));
			String dataType = StringUtil.parseString(oneMap.get("data_type"));
			int dataLength = StringUtil.parseInt(oneMap.get("data_length"));
			boolean nullable = StringUtil.parseString(oneMap.get("nullable")).equalsIgnoreCase("Y");
			int columnId = StringUtil.parseInt(oneMap.get("column_id"));
			
			Column column = new Column();
			column.setViewName(oneViewName);
			column.setColumnName(columnName);
			column.setDataType(dataType);
			column.setDataLength(dataLength);
			column.setNullable(nullable);
			column.setColumnId(columnId);
			
			if (oneView.getColumnList() == null) {
				oneView.setColumnList(new ColumnList());
			}
			
			oneView.getColumnList().add(column);
		}
		
		// 뷰 내의 모든 컬럼을 컬럼 아이디 순으로 정렬한다.
		viewList.sortAllColumnsByColumnId();
	}
	
	
	private void setIndexListToTableList(StringMapList indexMapList, TableList tableList) {
		if (indexMapList == null || indexMapList.size() == 0) {
			return;
		}
		
		if (tableList == null || tableList.size() == 0) {
			return;
		}
		
		StringMap oneMap = null;
		String oneTableName = "";
		int count = indexMapList.size();
		for (int i=0; i<count; i++) {
			oneMap = indexMapList.get(i);
			if (oneMap == null) {
				continue;
			}
			
			oneTableName = StringUtil.parseString(oneMap.get("table_name"));
			if (oneTableName == null || oneTableName.length() == 0) {
				continue;
			}
			
			Table oneTable = tableList.findTable(oneTableName);
			if (oneTable == null) {
				continue;
			}
			
			String indexName = StringUtil.parseString(oneMap.get("index_name"));
			String columnName = StringUtil.parseString(oneMap.get("column_name"));
			String descend = StringUtil.parseString(oneMap.get("descend"));
			int columnPosition = StringUtil.parseInt(oneMap.get("column_position"));
			
			Index oneIndex = oneTable.findIndex(indexName);
			if (oneIndex == null) {
				Index index = new Index();
				index.setTableName(oneTableName);
				index.setIndexName(indexName);
				
				if (oneTable.getIndexList() == null) {
					oneTable.setIndexList(new IndexList());
				}
				
				oneTable.getIndexList().add(index);
				
				// 다시 가져온다.
				oneIndex = oneTable.findIndex(indexName);
			}
			
			// 다시 가져왔는데도 없으면 그만둔다.
			if (oneIndex == null) {
				continue;
			}
			
			if (oneIndex.getIndexConditionList() == null) {
				oneIndex.setIndexConditionList(new IndexConditionList());
			}
			
			IndexConditionList indexConditionList = oneIndex.getIndexConditionList();
			
			IndexCondition indexCondition = new IndexCondition();
			indexCondition.setIndexName(indexName);
			indexCondition.setColumnName(columnName);
			indexCondition.setDescend(descend);
			indexCondition.setColumnPosition(columnPosition);

			indexConditionList.add(indexCondition);
		}
		
		// 인덱스를 이름 기준으로 오름차순 정렬한다.
		tableList.sortAllIndexListByName();
		
		// 인덱스 조건을 컬럼 위치 순으로 정렬한다.
		tableList.sortAllIndexConditionsByColumnPosition();
	}
	
	
	private FunctionList makeFunctionList(StringMapList functionMapList) {
		if (functionMapList == null || functionMapList.size() == 0) {
			return null;
		}
		
		FunctionList functionList = new FunctionList();
		
		StringMap oneMap = null;
		String oneFunctionName = "";
		int count = functionMapList.size();
		for (int i=0; i<count; i++) {
			oneMap = functionMapList.get(i);
			if (oneMap == null) {
				continue;
			}
			
			oneFunctionName = StringUtil.parseString(oneMap.get("object_name"));
			if (oneFunctionName == null || oneFunctionName.length() == 0) {
				continue;
			}
			
			String oneStatus = StringUtil.parseString(oneMap.get("status"));
			
			Function function = new Function();
			function.setFunctionName(oneFunctionName);
			function.setStatus(oneStatus);
			
			functionList.add(function);
		}
		
		// 함수를 이름 기준으로 오름차순 정렬한다.
		functionList.sortByName();
		return functionList;
	}
	
	private ProcedureList makeProcedureList(StringMapList procedureMapList) {
		if (procedureMapList == null || procedureMapList.size() == 0) {
			return null;
		}
		
		ProcedureList procedureList = new ProcedureList();
		
		StringMap oneMap = null;
		String oneProcedureName = "";
		int count = procedureMapList.size();
		for (int i=0; i<count; i++) {
			oneMap = procedureMapList.get(i);
			if (oneMap == null) {
				continue;
			}
			
			oneProcedureName = StringUtil.parseString(oneMap.get("object_name"));
			if (oneProcedureName == null || oneProcedureName.length() == 0) {
				continue;
			}
			
			String oneStatus = StringUtil.parseString(oneMap.get("status"));
			
			Procedure procedure = new Procedure();
			procedure.setProcedureName(oneProcedureName);
			procedure.setStatus(oneStatus);
			
			procedureList.add(procedure);
		}
		
		// 프로시저를 이름 기준으로 오름차순 정렬한다.
		procedureList.sortByName();
		return procedureList;
	}
	
	
	private SequenceList makeSequenceList(StringMapList sequenceMapList) {
		if (sequenceMapList == null || sequenceMapList.size() == 0) {
			return null;
		}
		
		SequenceList sequenceList = new SequenceList();
		
		StringMap oneMap = null;
		String oneSequenceName = "";
		int count = sequenceMapList.size();
		for (int i=0; i<count; i++) {
			oneMap = sequenceMapList.get(i);
			if (oneMap == null) {
				continue;
			}
			
			oneSequenceName = StringUtil.parseString(oneMap.get("object_name"));
			if (oneSequenceName == null || oneSequenceName.length() == 0) {
				continue;
			}
			
			String oneStatus = StringUtil.parseString(oneMap.get("status"));
			
			Sequence sequence = new Sequence();
			sequence.setSequenceName(oneSequenceName);
			sequence.setStatus(oneStatus);
			
			sequenceList.add(sequence);
		}
		
		// 시퀀스를 이름 기준으로 오름차순 정렬한다.
		sequenceList.sortByName();
		return sequenceList;
	}
	
	private ViewList makeViewList(StringMapList viewMapList) {
		if (viewMapList == null || viewMapList.size() == 0) {
			return null;
		}
		
		ViewList viewList = new ViewList();
		
		StringMap oneMap = null;
		String oneViewName = "";
		int count = viewMapList.size();
		for (int i=0; i<count; i++) {
			oneMap = viewMapList.get(i);
			if (oneMap == null) {
				continue;
			}
			
			oneViewName = StringUtil.parseString(oneMap.get("object_name"));
			if (oneViewName == null || oneViewName.length() == 0) {
				continue;
			}
			
			String oneStatus = StringUtil.parseString(oneMap.get("status"));
			
			View view = new View();
			view.setViewName(oneViewName);
			view.setStatus(oneStatus);
			
			viewList.add(view);
		}
		
		// 뷰를 이름 기준으로 오름차순 정렬한다.
		viewList.sortByName();
		return viewList;
	}
}