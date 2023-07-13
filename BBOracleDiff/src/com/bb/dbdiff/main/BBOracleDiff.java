package com.bb.dbdiff.main;

import java.io.File;
import java.util.HashMap;

import com.bb.dbdiff.common.CommonConst;
import com.bb.dbdiff.dbaction.OracleDiffController;
import com.bb.dbdiff.dbdata.Database;
import com.bb.dbdiff.form.OracleDiffForm;
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
					String serviceName1 = StringUtil.parseString(propMap.get("asis_servicename")).trim();
					String user1 = StringUtil.parseString(propMap.get("asis_user")).trim();
					String password1 = StringUtil.parseString(propMap.get("asis_password")).trim();
					
					database1 = new Database(host1, port1, sid1, serviceName1, user1, password1);
					
					String host2 = StringUtil.parseString(propMap.get("tobe_host")).trim();
					String port2 = StringUtil.parseString(propMap.get("tobe_port")).trim();
					String sid2 = StringUtil.parseString(propMap.get("tobe_sid")).trim();
					String serviceName2 = StringUtil.parseString(propMap.get("tobe_servicename")).trim();
					String user2 = StringUtil.parseString(propMap.get("tobe_user")).trim();
					String password2 = StringUtil.parseString(propMap.get("tobe_password")).trim();
					
					database2 = new Database(host2, port2, sid2, serviceName2, user2, password2);
					
					CommonConst.compareToolPath = StringUtil.parseString(propMap.get("compare_tool_path")).trim();
					
					// 리눅스 OS타입 추가. 리눅스일 경우 윈도우폼 표시하지 않고 output 파일 생성
					if (osType != null && osType.equalsIgnoreCase("linux")) {
						bWindowMode = false;
						System.out.println("os_type : linux");
						System.out.println("");
						
						System.out.println("host1 : " + host1);
						System.out.println("port1 : " + port1);
						if (sid1 != null && sid1.length() > 0) {
							System.out.println("sid1 : " + sid1);
						} else if (serviceName1 != null && serviceName1.length() > 0) {
							System.out.println("serviceName1 : " + serviceName1);
						}
						System.out.println("user1 : " + user1);
						System.out.println("");
						
						System.out.println("host2 : " + host2);
						System.out.println("port2 : " + port2);
						if (sid2 != null && sid2.length() > 0) {
							System.out.println("sid2 : " + sid2);
						} else if (serviceName2 != null && serviceName2.length() > 0) {
							System.out.println("serviceName2 : " + serviceName2);
						}
						System.out.println("user2 : " + user2);
						
						OracleDiffController oracleDiffController = new OracleDiffController();
						oracleDiffController.doDiff(host1, port1, sid1, serviceName1, user1, password1, host2, port2, sid2, serviceName2, user2, password2);
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