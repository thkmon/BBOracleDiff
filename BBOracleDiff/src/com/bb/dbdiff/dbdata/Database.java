package com.bb.dbdiff.dbdata;

public class Database {
	
	private String host = "";
	private String port = "";
	private String sid = "";
	
	private String user = "";
	private String password = "";
	
	private TableList tableList = null;
	private ViewList viewList = null;
	private SequenceList sequenceList = null;
	private FunctionList functionList = null;
	private ProcedureList procedureList = null;
	
	
	public Database(String host, String port, String sid, String user, String password) {
		this.host = host;
		this.port = port;
		this.sid = sid;
		
		this.user = user;
		this.password = password;
	}
	
	
	public String getConnectionInfo() {
		StringBuffer buff = new StringBuffer();
		buff.append("host : " + host).append(" / ");
		buff.append("port : " + port).append(" / ");
		buff.append("sid : " + sid).append(" / ");
		buff.append("user : " + user);
		return buff.toString();
	}
	
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("host : " + host).append("\n");
		buff.append("port : " + port).append("\n");
		buff.append("sid : " + sid).append("\n");
		buff.append("user : " + user).append("\n");
		buff.append("\n");
		
		if (tableList != null && tableList.size() > 0) {
			buff.append(tableList.toString());
		}
		
		if (viewList != null && viewList.size() > 0) {
			buff.append(viewList.toString());
			buff.append("\n");
		}
		
		if (sequenceList != null && sequenceList.size() > 0) {
			buff.append(sequenceList.toString());
			buff.append("\n");
		}
		
		if (functionList != null && functionList.size() > 0) {
			buff.append(functionList.toString());
			buff.append("\n");
		}
		
		if (procedureList != null && procedureList.size() > 0) {
			buff.append(procedureList.toString());
			buff.append("\n");
		}
		
		return buff.toString();
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public String getSid() {
		return sid;
	}


	public void setSid(String sid) {
		this.sid = sid;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public TableList getTableList() {
		return tableList;
	}


	public void setTableList(TableList tableList) {
		this.tableList = tableList;
	}
	
	
	public ViewList getViewList() {
		return viewList;
	}
	
	
	public void setViewList(ViewList viewList) {
		this.viewList = viewList;
	}
	
	
	public SequenceList getSequenceList() {
		return sequenceList;
	}
	
	
	public void setSequenceList(SequenceList sequenceList) {
		this.sequenceList = sequenceList;
	}
	
	
	public FunctionList getFunctionList() {
		return functionList;
	}
	
	
	public void setFunctionList(FunctionList functionList) {
		this.functionList = functionList;
	}
	
	
	public ProcedureList getProcedureList() {
		return procedureList;
	}
	
	
	public void setProcedureList(ProcedureList procedureList) {
		this.procedureList = procedureList;
	}
}