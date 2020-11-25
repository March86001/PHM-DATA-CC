package com.genertech.phm.utils;

public class LogUtil {

	/**
	 * 比较接口取得的数据和新增数据
	 * 
	 * @param dataNumber
	 *            接口取得的数据
	 * @param deleteNumber
	 *            删除数据
	 * @param insertNumber
	 *            新增数据
	 */
	public static void compareNumber(int dataNumber, int deleteNumber,
			int insertNumber) {
		if (dataNumber != insertNumber) {
			throw new RuntimeException("实际接收未全部存入，实际接收数据" + dataNumber
					+ "条，数据库删除数据" + deleteNumber + "条，存入数据库" + insertNumber
					+ "条。");
		}
	}

	/**
	 * json的值中出现""替换为中文“”
	 * 
	 * @param oldJson
	 * @return
	 */
	public static String jsonString(String oldJson) {
		char[] temp = oldJson.toCharArray();
		int n = temp.length;
		if (n > 1) {
			for (int i = 1; i < n; i++) {
				if (temp[i - 1] == '"' && temp[i] == ':' && temp[i + 1] == '"') {
					for (int j = i + 2; j < n; j++) {
						if (temp[j] == '"') {
							if (temp[j + 1] != ',' && temp[j + 1] != '}') {
								temp[j] = '”';
							} else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
								break;
							}
						}
					}
				}
			}
		}
		return new String(temp);
	}
}
