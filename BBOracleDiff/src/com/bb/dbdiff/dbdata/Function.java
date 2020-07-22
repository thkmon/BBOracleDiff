package com.bb.dbdiff.dbdata;

public class Function {

	private String functionName = "";
	private String status = "";
	
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[function] ");
		buff.append("functionName : ").append(functionName);

		if (status != null && !status.equalsIgnoreCase("VALID")) {
			buff.append(" (").append(status).append(")");
		}
		
		return buff.toString();
	}
	
	
	public String getFunctionName() {
		return functionName;
	}
	
	
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
}