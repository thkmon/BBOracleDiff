package com.bb.dbdiff.dbdata;

public class Procedure {
	
	private String procedureName = "";
	private String status = "";
	
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[procedure] ");
		buff.append("procedureName : ").append(procedureName);
		
		if (status != null && !status.equalsIgnoreCase("VALID")) {
			buff.append(" (").append(status).append(")");
		}
		
		return buff.toString();
	}
	
	
	public String getProcedureName() {
		return procedureName;
	}
	
	
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
}