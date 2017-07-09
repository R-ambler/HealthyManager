package HM.DAL.FindPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import HM.Comm.CommFunction;
import HM.Comm.Database;
import HRP.Comm.Util.HLogger;

public class FindPasswordDAL extends Database {
	Connection con = null;
	PreparedStatement ps = null;
	Statement st = null;
	ResultSet rs = null;

	// 找回密码/重置密码
	public int toFindPassword(String photo_number,String password) {
		HLogger.info("FindPasswordDAL Function toFindPassword Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "UPDATE hm_user_info hm\n" +
				"SET hm.PASSWORD = ?\n" +
				"WHERE\n" +
				"	hm.PHONE_NUMBER = ?";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setString(1, password);
			ps.setString(2, photo_number);
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("FindPasswordDAL Function toFindPassword End.");
		return dataNum;// 返回更新的数据数量
	}
}
