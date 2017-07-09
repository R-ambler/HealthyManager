package HM.DAL.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import HM.Comm.CommFunction;
import HM.Comm.Database;
import HM.Model.HM_USER_INFO;
import HRP.Comm.Util.HLogger;

public class LoginDAL extends Database {

	Connection con = null;
	PreparedStatement ps = null;
	Statement st = null;
	ResultSet rs = null;

	// 用户登录
	public List<HM_USER_INFO> toLogin(String photo_number) {
		HLogger.info("LoginDAL Function toLogin Begin.");
		List<HM_USER_INFO> list = new ArrayList<HM_USER_INFO>();
		HM_USER_INFO UserInfoBean = null;

		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "SELECT\n" + 
					"	*\n" + 
					"FROM\n" + 
					"	hm_user_info hm\n" +
					"WHERE\n" +
					"	hm.PHONE_NUMBER = ?;";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setString(1, photo_number);
			// 执行数据库操作
			rs = ps.executeQuery();
			while (rs.next()) {
				UserInfoBean = new HM_USER_INFO();
				UserInfoBean.setUSER_NO(rs.getInt("USER_NO"));
				UserInfoBean.setUSER_NAME(rs.getString("USER_NAME"));
				UserInfoBean.setPASSWORD(rs.getString("PASSWORD"));
				UserInfoBean.setLAST_LOG_TIME(rs.getTimestamp("LAST_LOG_TIME"));

				list.add(UserInfoBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			HLogger.info(ps.toString());
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("LoginDAL Function toLogin End.");
		return list;// 返回添加的数据数量
	}

	//修改上次登录时间
	public int toUpdateLastLogTime(String photo_number) {
		HLogger.info("LoginDAL Function toUpdateLastLogTime Begin.");
		int dataNum=0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "UPDATE hm_user_info hm\n" +
					"SET hm.LAST_LOG_TIME = NOW()\n" +
					"WHERE\n" +
					"	hm.PHONE_NUMBER = ?";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setString(1, photo_number);
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("LoginDAL Function toUpdateLastLogTime End.");
		return dataNum;//返回更新的数据数量
	}
}
