
package org.colorcoding.ibas.bobas.db.mysql.test;

import org.colorcoding.ibas.bobas.db.DbConnection;
import org.colorcoding.ibas.bobas.db.IDbCommand;
import org.colorcoding.ibas.bobas.db.IDbConnection;
import org.colorcoding.ibas.bobas.db.IDbDataReader;
import org.colorcoding.ibas.bobas.db.mysql.DbAdapter;

import junit.framework.TestCase;

public class TestDbAdapter extends TestCase {

	public void testforDbAdapter() {
		String server = "ibas-db-mysql";
		String dbName = "ibas_demo";
		String userName = "root";
		String userPwd = "1q2w3e";
		String sql = "SELECT * FROM `CC_TT_ORDR` WHERE (`DocStatus` = N'P' OR `DocStatus` = N'F') AND `CardCode` IS NOT NULL AND CAST(`DocEntry` AS CHAR) LIKE N'2%' AND `DocEntry` > 2000 AND `DocEntry` <> `B1DocEntry` ORDER BY `DocEntry` DESC, `CardCode` ASC LIMIT 100";
		try {
			DbAdapter dbAdapter = new DbAdapter();
			IDbConnection con = new DbConnection(dbAdapter.createConnection(server, dbName, userName, userPwd, ""));
			// 创建执行语句
			IDbCommand comm = con.createCommand();
			IDbDataReader Idr = comm.executeReader(sql);
			int i = 0;
			while (Idr.next()) {
				System.out.println(Idr.getString(1) + "  " + Idr.getString(2));
				i++;
			}
			System.out.println(i);
			con.close();
			Idr.close();
		} catch (Exception e) {
			System.out.println(e.toString());

		}
	}
}