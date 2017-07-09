package HM.DAL.Search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import HM.Comm.CommFunction;
import HM.Comm.Database;
import HM.Model.HM_SEARCH;
import HRP.Comm.Util.HLogger;

public class SearchDAL extends Database {
	Connection con = null;
	PreparedStatement ps = null;
	Statement st = null;
	ResultSet rs = null;

	// 查询
	public List<HM_SEARCH> toSelect(long user_no) {
		HLogger.info("SearchDAL Function toSelect Begin.");
		List<HM_SEARCH> list = new ArrayList<HM_SEARCH>();
		HM_SEARCH searchBean = null;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "SELECT\n" +
					"	*\n" + "FROM\n" + 
					"	hm_search hm\n"+ 
					"WHERE\n" + "	hm.USER_NO IS NULL\n" +
					"OR hm.USER_NO = ?\n"+ 
					"ORDER BY\n" + 
					"	hm.USER_NO";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setLong(1, user_no);
			// 执行数据库操作
			rs = ps.executeQuery();
			while (rs.next()) {
				searchBean = new HM_SEARCH();
				searchBean.setSEARCH_NO(rs.getInt("SEARCH_NO"));
				searchBean.setSEARCH_NAME(rs.getString("SEARCH_NAME"));
				searchBean.setSEARCH_URL(rs.getString("SEARCH_URL"));
				searchBean.setUSER_NO(rs.getInt("USER_NO"));
				searchBean.setSEARCH_ABSTRACT(rs.getString("SEARCH_ABSTRACT"));

				list.add(searchBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("SearchDAL Function toSelect End.");
		return list;// 返回更新的数据数量
	}

	// 删除
	public int toDelete(int search_no) {
		HLogger.info("SearchDAL Function toDelete Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "DELETE\n" + 
					"FROM\n" + 
					"	hm_search\n" + 
					"WHERE\n"+ 
					"	SEARCH_NO = ?";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setLong(1, search_no);
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("SearchDAL Function toDelete End.");
		return dataNum;// 返回更新的数据数量
	}
	// 修改
	public int toUpdate(HM_SEARCH searchBean) {
		HLogger.info("SearchDAL Function toUpdate Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "UPDATE hm_search\n" +
				"SET SEARCH_NAME = ?,\n" +
				" SEARCH_URL = ?,\n" +
				" SEARCH_ABSTRACT = ?\n" +
				"WHERE\n" +
				"	SEARCH_NO = ?";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setString(1, searchBean.getSEARCH_NAME());
			ps.setString(2, searchBean.getSEARCH_URL());
			ps.setString(3, searchBean.getSEARCH_ABSTRACT());
			ps.setInt(4, searchBean.getSEARCH_NO());
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("SearchDAL Function toUpdate End.");
		return dataNum;// 返回更新的数据数量
	}
	// 插入
	public int toInsert(HM_SEARCH searchBean) {
		HLogger.info("SearchDAL Function toInsert Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "INSERT INTO hm_search (\n" +
				"	SEARCH_NAME,\n" +
				"	SEARCH_URL,\n" +
				"	USER_NO,\n" +
				"	SEARCH_ABSTRACT\n" +
				")\n" +
				"VALUES\n" +
				"	(\n" +
				"		?,\n" +
				"		?,\n" +
				"		?,\n" +
				"		?\n" +
				"	)";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setString(1, searchBean.getSEARCH_NAME());
			ps.setString(2, searchBean.getSEARCH_URL());
			ps.setLong(3, searchBean.getUSER_NO());
			ps.setString(4, searchBean.getSEARCH_ABSTRACT());
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("SearchDAL Function toInsert End.");
		return dataNum;// 返回更新的数据数量
	}
}
