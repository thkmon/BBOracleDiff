package com.bb.dbdiff.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.bb.dbdiff.common.CommonConst;
import com.bb.dbdiff.dbaction.OracleDiffController;
import com.bb.dbdiff.dbdata.Database;
import com.bb.dbdiff.prototype.StringMap;
import com.bb.dbdiff.util.PropertiesUtil;

public class OracleDiffForm {

	private static JTextField textBoxHost1;
	private static JTextField textBoxPort1;
	private static JTextField textBoxSid1;
	private static JTextField textBoxServiceName1;
	private static JTextField textBoxUser1;
	private static JTextField textBoxPassword1;

	private static JTextField textBoxHost2;
	private static JTextField textBoxPort2;
	private static JTextField textBoxSid2;
	private static JTextField textBoxServiceName2;
	private static JTextField textBoxUser2;
	private static JTextField textBoxPassword2;

	public static boolean bSaveDatabase1 = false;
	public static boolean bSaveDatabase2 = false;

	public static String host1 = "";
	public static String port1 = "";
	public static String sid1 = "";
	public static String serviceName1 = "";
	public static String user1 = "";
	public static String password1 = "";

	public static String host2 = "";
	public static String port2 = "";
	public static String sid2 = "";
	public static String serviceName2 = "";
	public static String user2 = "";
	public static String password2 = "";

	public void openForm(Database database1, Database database2) {
		final BasicForm form = new BasicForm(730, 410, "BBOracleDiff_" + CommonConst.version);
		form.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		JTextField[] databaseFields1 = addDatabaseFields(form, 0, "[AS-IS]");
		textBoxHost1 = databaseFields1[0];
		textBoxPort1 = databaseFields1[1];
		textBoxSid1 = databaseFields1[2];
		textBoxServiceName1 = databaseFields1[3];
		textBoxUser1 = databaseFields1[4];
		textBoxPassword1 = databaseFields1[5];

		JTextField[] databaseFields2 = addDatabaseFields(form, 340, "[TO-BE]");
		textBoxHost2 = databaseFields2[0];
		textBoxPort2 = databaseFields2[1];
		textBoxSid2 = databaseFields2[2];
		textBoxServiceName2 = databaseFields2[3];
		textBoxUser2 = databaseFields2[4];
		textBoxPassword2 = databaseFields2[5];

		setDatabaseValues(databaseFields1, database1);
		setDatabaseValues(databaseFields2, database2);

		JButton buttonDiff = form.addButton(38, 300, 625, 30, "Diff");
		buttonDiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				doDiff();
			}
		});

		form.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				saveDatabaseOptions();
				form.dispose();
				System.out.println("종료");
			}
		});

		form.open();
	}

	private JTextField[] addDatabaseFields(BasicForm form, int horizontalOffset, String title) {
		int labelLeft = 40 + horizontalOffset;
		int textLeft = 120 + horizontalOffset;
		int top = 30;

		form.addLabel(labelLeft, top, 80, 25, title);
		top += 35;

		String[] labels = { "Host", "Port", "Sid", "ServiceName", "User", "Password" };
		JTextField[] fields = new JTextField[labels.length];

		for (int i = 0; i < labels.length; i++) {
			form.addLabel(labelLeft, top, 80, 25, labels[i]);
			if (i == labels.length - 1) {
				fields[i] = form.addPasswordInput(textLeft, top, 200, 25);
			} else {
				fields[i] = form.addTextInput(textLeft, top, 200, 25);
			}
			top += 35;
		}

		return fields;
	}

	private void setDatabaseValues(JTextField[] fields, Database database) {
		if (database == null) {
			return;
		}

		setText(fields[0], database.getHost());
		setText(fields[1], database.getPort());
		setText(fields[2], database.getSid());
		setText(fields[3], database.getServiceName());
		setText(fields[4], database.getUser());
		setText(fields[5], database.getPassword());
	}

	private void setText(JTextField field, String value) {
		if (value != null) {
			field.setText(value);
		}
	}

	private void doDiff() {
		bSaveDatabase1 = false;
		bSaveDatabase2 = false;

		host1 = textBoxHost1.getText().trim();
		port1 = textBoxPort1.getText().trim();
		sid1 = textBoxSid1.getText().trim();
		serviceName1 = textBoxServiceName1.getText().trim();
		user1 = textBoxUser1.getText().trim();
		password1 = textBoxPassword1.getText().trim();

		host2 = textBoxHost2.getText().trim();
		port2 = textBoxPort2.getText().trim();
		sid2 = textBoxSid2.getText().trim();
		serviceName2 = textBoxServiceName2.getText().trim();
		user2 = textBoxUser2.getText().trim();
		password2 = textBoxPassword2.getText().trim();

		OracleDiffController oracleDiffController = new OracleDiffController();
		int[] saveDatabaseArr = oracleDiffController.doDiff(
				host1, port1, sid1, serviceName1, user1, password1,
				host2, port2, sid2, serviceName2, user2, password2);

		if (saveDatabaseArr != null) {
			bSaveDatabase1 = saveDatabaseArr[0] == 1;
			bSaveDatabase2 = saveDatabaseArr[1] == 1;
		}
	}

	private void saveDatabaseOptions() {
		if (!bSaveDatabase1 && !bSaveDatabase2) {
			return;
		}

		StringMap keyValueMap = new StringMap();
		if (bSaveDatabase1) {
			keyValueMap.put("asis_host", host1);
			keyValueMap.put("asis_port", port1);
			keyValueMap.put("asis_sid", sid1);
			keyValueMap.put("asis_servicename", serviceName1);
			keyValueMap.put("asis_user", user1);
			keyValueMap.put("asis_password", password1);
		}

		if (bSaveDatabase2) {
			keyValueMap.put("tobe_host", host2);
			keyValueMap.put("tobe_port", port2);
			keyValueMap.put("tobe_sid", sid2);
			keyValueMap.put("tobe_servicename", serviceName2);
			keyValueMap.put("tobe_user", user2);
			keyValueMap.put("tobe_password", password2);
		}

		PropertiesUtil.modifyPropertiesFile(CommonConst.propFilePath, keyValueMap);
	}
}
