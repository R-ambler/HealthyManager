package HM.DAL.Prescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import HM.Comm.CommFunction;
import HM.Comm.Database;
import HM.Model.HM_PRESCRIPTION;
import HRP.Comm.Util.HLogger;

public class PrescriptionDAL extends Database {
	Connection con = null;
	PreparedStatement ps = null;
	Statement st = null;
	ResultSet rs = null;

	// 查询
	public List<HM_PRESCRIPTION> toSelect(long prescription_no) {
		HLogger.info("PrescriptionDAL Function toSelect Begin.");
		List<HM_PRESCRIPTION> list = new ArrayList<HM_PRESCRIPTION>();
		HM_PRESCRIPTION prescriptionBean = null;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	hm_prescription hm\n" +
				"WHERE\n" +
				"	hm.USER_NO = ? ";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setLong(1, prescription_no);
			// 执行数据库操作
			rs = ps.executeQuery();
			while (rs.next()) {
				prescriptionBean = new HM_PRESCRIPTION();
				prescriptionBean.setPRESCRIPTION_NO(rs.getInt("PRESCRIPTION_NO"));
				prescriptionBean.setHOSPITAL_NAME(rs.getString("HOSPITAL_NAME"));
				prescriptionBean.setINQUIRY_TIME(rs.getTimestamp("INQUIRY_TIME"));
				prescriptionBean.setREMARK(rs.getString("REMARK"));
				prescriptionBean.setPRESCRIPTION_ABSTRACT(rs.getString("PRESCRIPTION_ABSTRACT"));
				prescriptionBean.setPHOTO_URL(rs.getString("PHOTO_URL"));
				prescriptionBean.setUSER_NO(rs.getInt("USER_NO"));

				list.add(prescriptionBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("PrescriptionDAL Function toSelect End.");
		return list;// 返回更新的数据数量
	}

	// 删除
	public int toDelete(long prescription_no) {
		HLogger.info("PrescriptionDAL Function toDelete Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "DELETE\n" +
				"FROM\n" +
				"	hm_prescription\n" +
				"WHERE\n" +
				"	PRESCRIPTION_NO = ?";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setLong(1, prescription_no);
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("PrescriptionDAL Function toDelete End.");
		return dataNum;// 返回更新的数据数量
	}
	// 插入
	public int toInsert(HM_PRESCRIPTION prescriptionBean) {
		HLogger.info("PrescriptionDAL Function toInsert Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "INSERT INTO hm_prescription (\n" +
				"	HOSPITAL_NAME,\n" +
				"	INQUIRY_TIME,\n" +
				"	REMARK,\n" +
				"	PRESCRIPTION_ABSTRACT,\n" +
//				"	PHOTO_URL,\n" +
				"	USER_NO\n" +
				")\n" +
				"VALUES\n" +
				"	(?,?,?,?,?)";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setString(1, prescriptionBean.getHOSPITAL_NAME());
			ps.setTimestamp(2, new java.sql.Timestamp(prescriptionBean.getINQUIRY_TIME().getTime()));
			ps.setString(3, prescriptionBean.getREMARK());
			ps.setString(4, prescriptionBean.getPRESCRIPTION_ABSTRACT());
//			ps.setString(5, prescriptionBean.getPHOTO_URL());
			ps.setLong(5,prescriptionBean.getUSER_NO());
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("PrescriptionDAL Function toInsert End.");
		return dataNum;// 返回更新的数据数量
	}
	// 修改
	public int toUpdate(HM_PRESCRIPTION prescriptionBean) {
		HLogger.info("PrescriptionDAL Function toUpdate Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "UPDATE hm_prescription\n" +
				"SET \n" +
				"	HOSPITAL_NAME = ?,\n" +
				"	INQUIRY_TIME = ?,\n" +
				"	REMARK = ?,\n" +
//				"	PHOTO_URL = ?,\n" +
				"	PRESCRIPTION_ABSTRACT = ?\n" +
				"WHERE\n" +
				"	PRESCRIPTION_NO = ?";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setString(1, prescriptionBean.getHOSPITAL_NAME());
			ps.setTimestamp(2, new java.sql.Timestamp(prescriptionBean.getINQUIRY_TIME().getTime()));
			ps.setString(3, prescriptionBean.getREMARK());
			ps.setString(4, prescriptionBean.getPRESCRIPTION_ABSTRACT());
//			ps.setString(5, prescriptionBean.getPHOTO_URL());
			ps.setLong(5, prescriptionBean.getPRESCRIPTION_NO());
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("PrescriptionDAL Function toUpdate End.");
		return dataNum;// 返回更新的数据数量
	}
}
