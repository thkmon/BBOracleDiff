package com.bb.dbdiff.form;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.bb.dbdiff.common.CommonConst;
import com.bb.dbdiff.dbaction.OracleInfoController;
import com.bb.dbdiff.dbdata.Database;
import com.bb.dbdiff.prototype.StringMap;
import com.bb.dbdiff.util.FileUtil;
import com.bb.dbdiff.util.PropertiesUtil;

public class OracleDiffForm {
	public static boolean bInit = false;
//	public static Text textBox1 = null;
	
	private static Label labelHost1 = null;
	private static Text textBoxHost1 = null;
	
	private static Label labelPort1 = null;
	private static Text textBoxPort1 = null;
	
	private static Label labelSid1 = null;
	private static Text textBoxSid1 = null;
	
	private static Label labelUser1 = null;
	private static Text textBoxUser1 = null;
	
	private static Label labelPassword1 = null;
	private static Text textBoxPassword1 = null;
	
	private static Label labelHost2 = null;
	private static Text textBoxHost2 = null;
	
	private static Label labelPort2 = null;
	private static Text textBoxPort2 = null;
	
	private static Label labelSid2 = null;
	private static Text textBoxSid2 = null;
	
	private static Label labelUser2 = null;
	private static Text textBoxUser2 = null;
	
	private static Label labelPassword2 = null;
	private static Text textBoxPassword2 = null;
	
	
	public static String host1 = "";
	public static String port1 = "";
	public static String sid1 = "";
	public static String user1 = "";
	public static String password1 = "";
	
	public static String host2 = "";
	public static String port2 = "";
	public static String sid2 = "";
	public static String user2 = "";
	public static String password2 = "";
	

	public void openForm(Database database1, Database database2) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("BBOracleDiff_" + CommonConst.version);
		shell.setLayout(new Layout() {
		
			@Override
			protected void layout(Composite arg0, boolean arg1) {
				// 윈도우 초기 사이즈 설정
				if (!bInit) {
					arg0.setSize(730, 410);
					bInit = true;
				}
			}

			@Override
			protected Point computeSize(Composite arg0, int arg1, int arg2, boolean arg3) {
				return null;
			}
		});
		
		{
			int iLableLeft = 40;
			int iTextLeft = 110;
			int iTop = 30;
			
			// 레이블 생성
			Label labelTitle1 = new Label(shell, 0);
			labelTitle1.setBounds(iLableLeft, iTop, 60, 20);
			labelTitle1.setText("[AS-IS]");
			
			iTop += 40;
			
			labelHost1 = new Label(shell, 0);
			labelHost1.setBounds(iLableLeft, iTop, 60, 20);
			labelHost1.setText("Host");
			
			textBoxHost1 = new Text(shell, SWT.BORDER);
			textBoxHost1.setFont(new Font(display, "굴림", 10, 0));
			textBoxHost1.setBounds(iTextLeft, iTop, 200, 20);
			if (database1 != null && database1.getHost() != null) {
				textBoxHost1.setText(database1.getHost());
			}
			
			iTop += 40;
			
			labelPort1 = new Label(shell, 0);
			labelPort1.setBounds(iLableLeft, iTop, 60, 20);
			labelPort1.setText("Port");
			
			textBoxPort1 = new Text(shell, SWT.BORDER);
			textBoxPort1.setFont(new Font(display, "굴림", 10, 0));
			textBoxPort1.setBounds(iTextLeft, iTop, 200, 20);
			if (database1 != null && database1.getPort() != null) {
				textBoxPort1.setText(database1.getPort());
			}
			
			iTop += 40;
			
			labelSid1 = new Label(shell, 0);
			labelSid1.setBounds(iLableLeft, iTop, 60, 20);
			labelSid1.setText("Sid");
			
			textBoxSid1 = new Text(shell, SWT.BORDER);
			textBoxSid1.setFont(new Font(display, "굴림", 10, 0));
			textBoxSid1.setBounds(iTextLeft, iTop, 200, 20);
			if (database1 != null && database1.getSid() != null) {
				textBoxSid1.setText(database1.getSid());
			}
			
			iTop += 40;
			
			labelUser1 = new Label(shell, 0);
			labelUser1.setBounds(iLableLeft, iTop, 60, 20);
			labelUser1.setText("User");
			
			textBoxUser1= new Text(shell, SWT.BORDER);
			textBoxUser1.setFont(new Font(display, "굴림", 10, 0));
			textBoxUser1.setBounds(iTextLeft, iTop, 200, 20);
			if (database1 != null && database1.getUser() != null) {
				textBoxUser1.setText(database1.getUser());
			}
			
			iTop += 40;
			
			labelPassword1 = new Label(shell, 0);
			labelPassword1.setBounds(iLableLeft, iTop, 60, 20);
			labelPassword1.setText("Password");
			
			textBoxPassword1 = new Text(shell, SWT.BORDER + SWT.PASSWORD);
			textBoxPassword1.setFont(new Font(display, "굴림", 10, 0));
			textBoxPassword1.setBounds(iTextLeft, iTop, 200, 20);
			if (database1 != null && database1.getPassword() != null) {
				textBoxPassword1.setText(database1.getPassword());
			}
			
			iTop += 40;
		}
		
		
		{
			int iLableLeft = 40 + 350;
			int iTextLeft = 110 + 350;
			int iTop = 30;
			
			// 레이블 생성
			Label labelTitle2 = new Label(shell, 0);
			labelTitle2.setBounds(iLableLeft, iTop, 60, 20);
			labelTitle2.setText("[TO-BE]");
			
			iTop += 40;
			
			labelHost2 = new Label(shell, 0);
			labelHost2.setBounds(iLableLeft, iTop, 60, 20);
			labelHost2.setText("Host");
			
			textBoxHost2 = new Text(shell, SWT.BORDER);
			textBoxHost2.setFont(new Font(display, "굴림", 10, 0));
			textBoxHost2.setBounds(iTextLeft, iTop, 200, 20);
			if (database2 != null && database2.getHost() != null) {
				textBoxHost2.setText(database2.getHost());
			}
			
			iTop += 40;
			
			labelPort2 = new Label(shell, 0);
			labelPort2.setBounds(iLableLeft, iTop, 60, 20);
			labelPort2.setText("Port");
			
			textBoxPort2 = new Text(shell, SWT.BORDER);
			textBoxPort2.setFont(new Font(display, "굴림", 10, 0));
			textBoxPort2.setBounds(iTextLeft, iTop, 200, 20);
			if (database2 != null && database2.getPort() != null) {
				textBoxPort2.setText(database2.getPort());
			}
			
			iTop += 40;
			
			labelSid2 = new Label(shell, 0);
			labelSid2.setBounds(iLableLeft, iTop, 60, 20);
			labelSid2.setText("Sid");
			
			textBoxSid2 = new Text(shell, SWT.BORDER);
			textBoxSid2.setFont(new Font(display, "굴림", 10, 0));
			textBoxSid2.setBounds(iTextLeft, iTop, 200, 20);
			if (database2 != null && database2.getSid() != null) {
				textBoxSid2.setText(database2.getSid());
			}
			
			
			iTop += 40;
			
			labelUser2 = new Label(shell, 0);
			labelUser2.setBounds(iLableLeft, iTop, 60, 20);
			labelUser2.setText("User");
			
			textBoxUser2= new Text(shell, SWT.BORDER);
			textBoxUser2.setFont(new Font(display, "굴림", 10, 0));
			textBoxUser2.setBounds(iTextLeft, iTop, 200, 20);
			if (database2 != null && database2.getUser() != null) {
				textBoxUser2.setText(database2.getUser());
			}
			
			iTop += 40;
			
			labelPassword2 = new Label(shell, 0);
			labelPassword2.setBounds(iLableLeft, iTop, 60, 20);
			labelPassword2.setText("Password");
			
			textBoxPassword2 = new Text(shell, SWT.BORDER + SWT.PASSWORD);
			textBoxPassword2.setFont(new Font(display, "굴림", 10, 0));
			textBoxPassword2.setBounds(iTextLeft, iTop, 200, 20);
			if (database2 != null && database2.getPassword() != null) {
				textBoxPassword2.setText(database2.getPassword());
			}
			
			iTop += 40;
		}
		
		// 버튼 생성
		Button buttonDiff = new Button(shell, 0);
		buttonDiff.setBounds(38, 300, 625, 30);
		buttonDiff.setText("Diff");
		buttonDiff.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				OracleInfoController oracleInfoCtrl = new OracleInfoController();
				
				host1 = textBoxHost1.getText().trim();
				port1 = textBoxPort1.getText().trim();
				sid1 = textBoxSid1.getText().trim();
				user1 = textBoxUser1.getText().trim();
				password1 = textBoxPassword1.getText().trim();
				
				Database database1 = oracleInfoCtrl.getOracleDatabaseInfo(host1, port1, sid1, user1, password1);
				
				host2 = textBoxHost2.getText().trim();
				port2 = textBoxPort2.getText().trim();
				sid2 = textBoxSid2.getText().trim();
				user2 = textBoxUser2.getText().trim();
				password2 = textBoxPassword2.getText().trim();
				
				Database database2 = oracleInfoCtrl.getOracleDatabaseInfo(host2, port2, sid2, user2, password2);
				
				String filePath1 = "";
				String filePath2 = "";
				try {
					if (database1 != null) {
						filePath1 = FileUtil.writeFile(database1.toString(), false);
					}
				} catch (Exception e) {
					System.err.println("file writing error. host == [" + host1 + "] / port == [" + port1 + "] / sid == [" + sid1 + "] / user == [" + user1 + "]");
					return;
				}
				
				try {
					if (database2 != null) {
						filePath2 = FileUtil.writeFile(database2.toString(), false);
					}
				} catch (Exception e) {
					System.err.println("file writing error. host == [" + host2 + "] / port == [" + port2 + "] / sid == [" + sid2 + "] / user == [" + user2 + "]");
					return;
				}
				
				if ((filePath1 != null && filePath1.length() > 0) || (filePath2 != null && filePath2.length() > 0)) {
					
					String compareToolPath = getWinmergePath();
					
					if (compareToolPath != null && compareToolPath.length() > 0) {
						try {
							ProcessBuilder builder = new ProcessBuilder();
							
							ArrayList<String> argList = new ArrayList<String>();
							argList.add(compareToolPath);
							if (filePath1 != null && filePath1.length() > 0) {
								argList.add(filePath1);
							} else {
								File emptyFile = new File("data/empty.txt");
								argList.add(emptyFile.getAbsolutePath());
							}
							
							if (filePath2 != null && filePath2.length() > 0) {
								argList.add(filePath2);
							} else {
								File emptyFile = new File("data/empty.txt");
								argList.add(emptyFile.getAbsolutePath());
							}
					
							builder.command(argList);
							builder.start();
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						System.err.println("compareToolPath is null or empty.");
					}
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
		
		
		StringMap keyValueMap = new StringMap();
		keyValueMap.put("asis_host", host1);
		keyValueMap.put("asis_port", port1);
		keyValueMap.put("asis_sid", sid1);
		keyValueMap.put("asis_user", user1);
		keyValueMap.put("asis_password", password1);
		
		keyValueMap.put("tobe_host", host2);
		keyValueMap.put("tobe_port", port2);
		keyValueMap.put("tobe_sid", sid2);
		keyValueMap.put("tobe_user", user2);
		keyValueMap.put("tobe_password", password2);
		
		PropertiesUtil.modifyPropertiesFile(CommonConst.propFilePath, keyValueMap);
		
		
		System.out.println("종료");
	}
	
	
	private static String getWinmergePath() {
		String compareToolPath = "";
		
		// 윈머지 경로를 프로퍼티 파일에서 가져오도록 처리한다.
		// 프로퍼티 내에 값이 없다면, 윈머지 기본경로를 넣어준다.
		if (CommonConst.compareToolPath != null && CommonConst.compareToolPath.trim().length() > 0) {
			compareToolPath = CommonConst.compareToolPath.trim();
		}
		
		return compareToolPath;
	}
}