package com.bb.dbdiff.main;

import java.io.File;
import java.util.HashMap;

import com.bb.dbdiff.common.CommonConst;
import com.bb.dbdiff.dbaction.OracleInfoController;
import com.bb.dbdiff.dbdata.Database;
import com.bb.dbdiff.form.OracleDiffForm;
import com.bb.dbdiff.util.FileUtil;
import com.bb.dbdiff.util.PropertiesUtil;
import com.bb.dbdiff.util.StringUtil;

public class BBOracleDiff {

	public static void main(String[] args) {
		boolean bWindowMode = true;
		
		Database database1 = null;
		Database database2 = null;
		
		File file = new File(CommonConst.propFilePath);
		if (file.exists() && file.isFile()) {
			HashMap<String, String> propMap = null;
			try {
				propMap = PropertiesUtil.readPropertiesFile(file.getAbsolutePath());
				if (propMap != null) {
					String osType = StringUtil.parseString(propMap.get("os_type")).trim();
					
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
					
					// 리눅스 OS타입 추가. 리눅스일 경우 output 파일 생성
					if (osType != null && osType.equalsIgnoreCase("linux")) {
						bWindowMode = false;
						System.out.println("os_type : linux");
						
						System.out.println("host1 : " + host1);
						System.out.println("port1 : " + port1);
						System.out.println("sid1 : " + sid1);
						System.out.println("user1 : " + user1);
						
						boolean bSaveDatabase1 = false;
						String filePath1 = "";
						try {
							if (database1 != null) {
								filePath1 = FileUtil.writeFile(database1.toString(), false);
								if (filePath1 != null && filePath1.length() > 0) {
									bSaveDatabase1 = true;
								}
							}
						} catch (Exception e) {
							System.err.println("file writing error. host == [" + host1 + "] / port == [" + port1 + "] / sid == [" + sid1 + "] / user == [" + user1 + "]");
							return;
						}
						
						System.out.println("bSaveDatabase1 :" + bSaveDatabase1);
						System.out.println("filePath1 : " + filePath1);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (bWindowMode) {
			// 윈도우를 띄운다.
			OracleDiffForm diffForm = new OracleDiffForm();
			diffForm.openForm(database1, database2);
		}
	}
}