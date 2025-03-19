package com.bb.dbdiff.util; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.bb.dbdiff.prototype.StringList;
import com.bb.dbdiff.prototype.StringMap;

/**
 * 프로퍼티 파일 읽기 (PropertiesUtil 클래스의 readPropertiesFile 메서드)
 * UTF-8 인코딩 형식의 properties 파일을 읽어서 HashMap 객체로 만들어 리턴하는 메서드.
 */
public class PropertiesUtil {
	
	/**
	 * UTF-8 인코딩 형식의 properties 파일을 읽어서 HashMap 객체로 만들어 리턴한다.
	 * 
	 * @param propFilePath
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, String> readPropertiesFile(String propFilePath) throws Exception {
		if (propFilePath == null || propFilePath.length() == 0) {
			throw new Exception("PropertiesUtil readPropertiesFile : propFilePath == null || propFilePath.length() == 0");
		}

		File propFileObj = new File(propFilePath);
		if (!propFileObj.exists()) {
			throw new Exception("PropertiesUtil readPropertiesFile : propFileObj does not exists. [" + propFileObj.getAbsolutePath() + "]");
		}
		
		if (!propFileObj.canRead()) {
			throw new Exception("PropertiesUtil readPropertiesFile : propFileObj can not read. [" + propFileObj.getAbsolutePath() + "]");
		}

		HashMap<String, String> resultMap = new HashMap<String, String>();

		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileInputStream = new FileInputStream(propFileObj);
			inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String oneLine = null;
			while ((oneLine = bufferedReader.readLine()) != null) {
				if (oneLine == null || oneLine.length() == 0) {
					continue;
				}
				
				// 주석 무시
				if (oneLine.trim().startsWith("#")) {
					continue;
				}
				
				int equalIndex = oneLine.indexOf("=");
				if (equalIndex < 0) {
					continue;
				}
				
				// 좌측값(key값)만 trim 처리한다. 우측값(value값)은 의도적으로 공백이 포함될 수 있다고 판단한다.
				String leftText = oneLine.substring(0, equalIndex).trim();
				String rightText = oneLine.substring(equalIndex + 1);
				
				// 등호 좌측 텍스트가 존재하지 않을 경우 무시
				if (leftText.length() == 0) {
					continue;
				}
				
				if (resultMap.get(leftText) != null) {
                	System.err.println("프로퍼티가 중복되어 발견되었습니다. 프로그램을 종료합니다. (Properties found duplicate. Exit the program.) : " + leftText);
                	System.exit(1);
                	break;
                }
				
				resultMap.put(leftText, rightText);
			}

		} catch (IOException e) {
			throw e;

		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception e) {
			} finally {
				bufferedReader = null;
			}

			try {
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (Exception e) {
			} finally {
				inputStreamReader = null;
			}

			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (Exception e) {
			} finally {
				fileInputStream = null;
			}
		}

		return resultMap;
	}
	
	
	public static boolean modifyPropertiesFile(String filePath, StringMap keyValueMap) {
		if (filePath == null || filePath.length() == 0) {
			return false;
		}
		
		if (keyValueMap == null || keyValueMap.keySet() == null) {
			return false;
		}
		
		boolean bResult = false;
		
		try {
			File file = new File(filePath);
			if (file.exists() && file.isFile()) {
				ArrayList<String> strList = FileUtil.readFile(file);
				if (strList != null && strList.size() > 0) {
					String oneLine = "";
					int count = strList.size();
					for (int i=0; i<count; i++) {
						oneLine = strList.get(i);
						if (oneLine == null || oneLine.length() == 0) {
							continue;
						}
						
						if (oneLine == null || oneLine.startsWith("#")) {
							continue;
						}
						
						int idxEqual = oneLine.indexOf("=");
						int idxSharp = oneLine.indexOf("#");
						if (idxEqual > -1 && idxSharp < 0) {
							String key = oneLine.substring(0, idxEqual);
							String value = keyValueMap.get(key);
							if (value != null) {
								oneLine = key + "=" + value;
								strList.set(i, oneLine);
							}
						}
					}
					
					if (FileUtil.writeFile(filePath, strList, false)) {
						bResult = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bResult;
	}
}