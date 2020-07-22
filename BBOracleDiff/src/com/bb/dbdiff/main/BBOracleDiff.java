package com.bb.dbdiff.main;

import java.io.File;
import java.util.HashMap;

import com.bb.dbdiff.common.CommonConst;
import com.bb.dbdiff.dbdata.Database;
import com.bb.dbdiff.form.OracleDiffForm;
import com.bb.dbdiff.util.PropertiesUtil;
import com.bb.dbdiff.util.StringUtil;

public class BBOracleDiff {

	public static void main(String[] args) {
		
		Database database1 = null;
		Database database2 = null;
		
		File file = new File(CommonConst.propFilePath);
		if (file.exists() && file.isFile()) {
			HashMap<String, String> propMap = null;
			try {
				propMap = PropertiesUtil.readPropertiesFile(file.getAbsolutePath());
				if (propMap != null) {
					String host1 = StringUtil.parseString(propMap.get("asis_host")).trim();
					String port1 = StringUtil.parseString(propMap.get("asis_port")).trim();
					String sid1 = StringUtil.parseString(propMap.get("asis_sid")).trim();
					String user1 = StringUtil.parseString(propMap.get("asis_user")).trim();
					String password1 = StringUtil.parseString(propMap.get("asis_password")).trim();
					
					database1 = new Database(host1, port1, sid1, user1, password1);
					
					String host2 = StringUtil.parseString(propMap.get("tobe_host")).trim();
					String port2 = StringUtil.parseString(propMap.get("tobe_port")).trim();
					String sid2 = StringUtil.parseString(propMap.get("tobe_sid")).trim();
					String user2 = StringUtil.parseString(propMap.get("tobe_user")).trim();
					String password2 = StringUtil.parseString(propMap.get("tobe_password")).trim();
					
					database2 = new Database(host2, port2, sid2, user2, password2);
					
					CommonConst.compareToolPath = StringUtil.parseString(propMap.get("compare_tool_path")).trim();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 윈도우를 띄운다.
		OracleDiffForm diffForm = new OracleDiffForm();
		diffForm.openForm(database1, database2);
	}
}