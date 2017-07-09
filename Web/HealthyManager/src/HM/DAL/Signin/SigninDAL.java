package HM.DAL.Signin;

import HM.Comm.CommFunction;
import HM.Comm.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import HRP.Comm.Util.HLogger;

public class SigninDAL extends Database{

	Connection con=null;
	PreparedStatement ps=null;
	Statement st=null;
	ResultSet rs=null;
	
	//确认手机号是否已注册
	public int toVerifyPhoneNumExist(String photo_number) {
		HLogger.info("SigninDAL Function toVerifyPhoneNumExist Begin.");
		int dataNum=0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "SELECT\n" +
					"	count(*)\n" +
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
			
			while(rs.next()){
				dataNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("SigninDAL Function toVerifyPhoneNumExist End.");
		return dataNum;//返回查询的数据数量
	}
	//用户注册
	public int toSignin(String user_name, String password, String photo_number) {
		HLogger.info("SigninDAL Function toSignin Begin.");
		int dataNum=0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "INSERT INTO hm_user_info (\n" +
					"	USER_NAME,\n" +
					"	PASSWORD,\n" +
					"	PHONE_NUMBER\n" +
					")\n" +
					"VALUES\n" +
					"	(?,?,?)";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setString(1, user_name);
			ps.setString(2, password);
			ps.setString(3, photo_number);
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("SigninDAL Function toSignin End.");
		return dataNum;//返回添加的数据数量
	}
}
