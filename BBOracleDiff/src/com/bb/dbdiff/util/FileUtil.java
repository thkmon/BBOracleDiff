package com.bb.dbdiff.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileUtil {

	public static boolean writeFile(String filePath, ArrayList<String> stringList, boolean bAppend)
			throws IOException, Exception {
		if (filePath == null || filePath.length() == 0) {
			return false;
		}

		File file = new File(filePath);

		boolean bWrite = false;

		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileOutputStream = new FileOutputStream(file, bAppend);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			bufferedWriter = new BufferedWriter(outputStreamWriter);

			if (stringList != null && stringList.size() > 0) {
				String oneLine = null;

				int lineCount = stringList.size();
				int lastIndex = lineCount - 1;

				for (int i = 0; i < lineCount; i++) {
					oneLine = stringList.get(i);

					bufferedWriter.write(oneLine, 0, oneLine.length());
					if (i < lastIndex) {
						bufferedWriter.newLine();
					}
				}
			}

			bWrite = true;

		} catch (IOException e) {
			throw e;

		} catch (Exception e) {
			throw e;

		} finally {
			close(bufferedWriter);
			close(outputStreamWriter);
			close(fileOutputStream);
		}

		return bWrite;
	}

	
	public static String writeFile(String str, boolean bAppend) throws IOException, Exception {
		String resultPath = "";

		File outputDir = new File("output");
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}

		String dateTime = DateUtil.getTodayDateTime();
		String outputFileName = dateTime;
		String outputFilePath = "output/" + outputFileName;
		String outputFileExt = ".txt";

		// 중복 있을 경우 새 파일을 만든다.
		while (new File(outputFilePath + outputFileExt).exists()) {
			outputFilePath = outputFilePath + "_1";
		}

		// 경로 뒤에 확장자를 붙인다.
		outputFilePath = outputFilePath + outputFileExt;

		boolean bResult = writeFile(outputFilePath, outputFileName + "\n" + str, bAppend);
		if (bResult) {
			File file = new File(outputFilePath);
			if (file.exists()) {
				resultPath = file.getAbsolutePath();
			}
		}

		return resultPath;
	}

	
	public static boolean writeFile(String filePath, String str, boolean bAppend) throws IOException, Exception {
		if (filePath == null || filePath.length() == 0) {
			return false;
		}

		File file = new File(filePath);

		boolean bWrite = false;

		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileOutputStream = new FileOutputStream(file, bAppend);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			bufferedWriter = new BufferedWriter(outputStreamWriter);

			bufferedWriter.write(str, 0, str.length());

			bWrite = true;

		} catch (IOException e) {
			throw e;

		} catch (Exception e) {
			throw e;

		} finally {
			close(bufferedWriter);
			close(outputStreamWriter);
			close(fileOutputStream);
		}

		return bWrite;
	}

	
	public static ArrayList<String> readFile(File file) throws IOException, Exception {
		if (file == null || !file.exists()) {
			return null;
		}

		ArrayList<String> resultList = null;

		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileInputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String oneLine = null;
			while ((oneLine = bufferedReader.readLine()) != null) {
				if (resultList == null) {
					resultList = new ArrayList<String>();
				}

				resultList.add(oneLine);
			}

		} catch (IOException e) {
			throw e;

		} catch (Exception e) {
			throw e;

		} finally {
			close(bufferedReader);
			close(inputStreamReader);
			close(fileInputStream);
		}

		return resultList;
	}

	
	private static void close(BufferedWriter bufferedWriter) {
		try {
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
		} catch (Exception e) {
		} finally {
			bufferedWriter = null;
		}
	}

	
	private static void close(OutputStreamWriter outputStreamWriter) {
		try {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
		} catch (Exception e) {
		} finally {
			outputStreamWriter = null;
		}
	}

	
	private static void close(FileOutputStream fileOutputStream) {
		try {
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		} catch (Exception e) {
		} finally {
			fileOutputStream = null;
		}
	}

	
	private static void close(FileInputStream fileInputStream) {
		try {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		} catch (Exception e) {
		} finally {
			fileInputStream = null;
		}
	}

	
	private static void close(InputStreamReader inputStreamReader) {
		try {
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
		} catch (Exception e) {
		} finally {
			inputStreamReader = null;
		}
	}

	private static void close(BufferedReader bufferedReader) {

		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		} catch (Exception e) {
		} finally {
			bufferedReader = null;
		}
	}
}