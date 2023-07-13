package com.bb.dbdiff.dbaction;

import java.io.File;
import java.util.ArrayList;

import com.bb.dbdiff.common.CommonConst;
import com.bb.dbdiff.dbdata.Database;
import com.bb.dbdiff.util.FileUtil;

public class OracleDiffController {

	public int[] doDiff(String host1, String port1, String sid1, String user1, String password1,
						String host2, String port2, String sid2, String user2, String password2) {
		
		int[] saveDatabaseArr = new int[2];
		saveDatabaseArr[0] = 0;
		saveDatabaseArr[1] = 0;
		
		OracleInfoController oracleInfoCtrl = new OracleInfoController();
		Database database1 = oracleInfoCtrl.getOracleDatabaseInfo(host1, port1, sid1, user1, password1);
		Database database2 = oracleInfoCtrl.getOracleDatabaseInfo(host2, port2, sid2, user2, password2);
		
		String filePath1 = "";
		String filePath2 = "";
		try {
			if (database1 != null) {
				filePath1 = FileUtil.writeFile(database1.toString(), false);
				if (filePath1 != null && filePath1.length() > 0) {
					saveDatabaseArr[0] = 1;
				}
			}
		} catch (Exception e) {
			System.err.println("file writing error. host == [" + host1 + "] / port == [" + port1 + "] / sid == [" + sid1 + "] / user == [" + user1 + "]");
			return saveDatabaseArr;
		}
		
		try {
			if (database2 != null) {
				filePath2 = FileUtil.writeFile(database2.toString(), false);
				if (filePath2 != null && filePath2.length() > 0) {
					saveDatabaseArr[1] = 1;
				}
			}
		} catch (Exception e) {
			System.err.println("file writing error. host == [" + host2 + "] / port == [" + port2 + "] / sid == [" + sid2 + "] / user == [" + user2 + "]");
			return saveDatabaseArr;
		}
		
		if ((filePath1 != null && filePath1.length() > 0) || (filePath2 != null && filePath2.length() > 0)) {
			
			String compareToolPath = getCompareToolPath();
			
			boolean isSuccess = false;
			
			// 파일비교툴이 존재하고 파일경로1, 파일경로2가 존재하는 경우에만 파일비교툴 실행하기
			if ((compareToolPath != null && compareToolPath.length() > 0) && (filePath1 != null && filePath1.length() > 0) && (filePath2 != null && filePath2.length() > 0)) {
				try {
					boolean hasCompareTool = false;
					if (compareToolPath.indexOf("/") > -1 || compareToolPath.indexOf("\\") > -1) {
						if (new File(compareToolPath).exists()) {
							hasCompareTool = true;
						} else {
							hasCompareTool = false;
							System.err.println("The tool does not exist at that path. compare_tool_path == [" + compareToolPath + "]");
						}
					} else {
						hasCompareTool = true;
					}
					
					if (hasCompareTool) {
						ProcessBuilder builder = new ProcessBuilder();
						ArrayList<String> argList = new ArrayList<String>();
						argList.add(compareToolPath);
						argList.add(filePath1);
						argList.add(filePath2);
						
						builder.command(argList);
						builder.start();
						
						isSuccess = true;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (!isSuccess) {
				try {
					if (filePath1 != null && filePath1.length() > 0) {
						ProcessBuilder builder1 = new ProcessBuilder();
						ArrayList<String> argList1 = new ArrayList<String>();
						argList1.add("notepad");
						argList1.add(filePath1);
						builder1.command(argList1);
						builder1.start();
					}
					
					if (filePath2 != null && filePath2.length() > 0) {
						ProcessBuilder builder2 = new ProcessBuilder();
						ArrayList<String> argList2 = new ArrayList<String>();
						argList2.add("notepad");
						argList2.add(filePath2);
						builder2.command(argList2);
						builder2.start();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return saveDatabaseArr;
	}
	
	
	private String getCompareToolPath() {
		String compareToolPath = "";
		
		// 윈머지 경로를 프로퍼티 파일에서 가져오도록 처리한다.
		// 프로퍼티 내에 값이 없다면, 윈머지 기본경로를 넣어준다.
		if (CommonConst.compareToolPath != null && CommonConst.compareToolPath.trim().length() > 0) {
			compareToolPath = CommonConst.compareToolPath.trim();
		}
		
		return compareToolPath;
	}
}