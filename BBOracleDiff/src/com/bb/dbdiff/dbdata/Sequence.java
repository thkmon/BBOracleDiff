package com.bb.dbdiff.dbdata;

public class Sequence {
	
	private String sequenceName = "";
	private String status = "";
	
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[sequence] ");
		buff.append("sequenceName : ").append(sequenceName);
		
		if (status != null && !status.equalsIgnoreCase("VALID")) {
			buff.append(" (").append(status).append(")");
		}
		
		return buff.toString();
	}
	
	
	public String getSequenceName() {
		return sequenceName;
	}
	
	
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
}