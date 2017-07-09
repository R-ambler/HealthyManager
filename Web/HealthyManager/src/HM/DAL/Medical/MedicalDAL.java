package HM.DAL.Medical;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import HM.Comm.CommFunction;
import HM.Comm.Database;
import HM.Model.HM_MEDICAL;
import HRP.Comm.Util.HLogger;

public class MedicalDAL extends Database {
	Connection con = null;
	PreparedStatement ps = null;
	Statement st = null;
	ResultSet rs = null;

	// 查询
	public List<HM_MEDICAL> toSelect(long medical_no) {
		HLogger.info("MedicalDAL Function toSelect Begin.");
		List<HM_MEDICAL> list = new ArrayList<HM_MEDICAL>();
		HM_MEDICAL medicalBean = null;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	hm_medical hm\n" +
				"WHERE\n" +
				"	hm.USER_NO = ? ";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setLong(1, medical_no);
			// 执行数据库操作
			rs = ps.executeQuery();
			while (rs.next()) {
				medicalBean = new HM_MEDICAL();
				medicalBean.setMEDICAL_NO(rs.getInt("MEDICAL_NO"));
				medicalBean.setUSER_NO(rs.getInt("USER_NO"));
				medicalBean.setINQUIRY_TIME(rs.getTimestamp("INQUIRY_TIME"));
				medicalBean.setHOSPITAL_NAME(rs.getString("HOSPITAL_NAME"));
				medicalBean.setDEPT_NAME(rs.getString("DEPT_NAME"));
				medicalBean.setDOCTOR_NAME(rs.getString("DOCTOR_NAME"));
				medicalBean.setMAIN_SUIT(rs.getString("MAIN_SUIT"));
				medicalBean.setMEDICAL_HISTORY(rs.getString("MEDICAL_HISTORY"));
				medicalBean.setPHYSICAL_EXAMINATION(rs.getString("PHYSICAL_EXAMINATION"));
				medicalBean.setDIAGNOSE(rs.getString("DIAGNOSE"));
				medicalBean.setSUGGEST(rs.getString("SUGGEST"));
				medicalBean.setPRESCRIPTION_NO(rs.getInt("PRESCRIPTION_NO"));
				medicalBean.setPHOTO_URL(rs.getString("PHOTO_URL"));
				medicalBean.setREMARK(rs.getString("REMARK"));

				list.add(medicalBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("MedicalDAL Function toSelect End.");
		return list;// 返回更新的数据数量
	}

	// 删除
	public int toDelete(long medical_no) {
		HLogger.info("MedicalDAL Function toDelete Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "DELETE\n" +
				"FROM\n" +
				"	hm_medical\n" +
				"WHERE\n" +
				"	MEDICAL_NO = ?";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setLong(1, medical_no);
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("MedicalDAL Function toDelete End.");
		return dataNum;// 返回更新的数据数量
	}
	// 修改
	public int toUpdate(HM_MEDICAL medicalBean) {
		HLogger.info("MedicalDAL Function toUpdate Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "UPDATE hm_medical\n" +
				"SET INQUIRY_TIME =?, \n" +
				"	HOSPITAL_NAME =?, \n" +
				"	DEPT_NAME =?, \n" +
				"	DOCTOR_NAME =?, \n" +
				"	MAIN_SUIT =?, \n" +
				"	MEDICAL_HISTORY =?, \n" +
				"	PHYSICAL_EXAMINATION =?, \n" +
				"	DIAGNOSE =?, \n" +
				"	SUGGEST =?, \n" +
				"	PHOTO_URL =?, \n" +
				"	REMARK =?\n" +
				"WHERE\n" +
				"	MEDICAL_NO = ?";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setTimestamp(1, new java.sql.Timestamp(medicalBean.getINQUIRY_TIME().getTime()));
			ps.setString(2, medicalBean.getHOSPITAL_NAME());
			ps.setString(3, medicalBean.getDEPT_NAME());
			ps.setString(4, medicalBean.getDOCTOR_NAME());
			ps.setString(5, medicalBean.getMAIN_SUIT());
			ps.setString(6, medicalBean.getMEDICAL_HISTORY());
			ps.setString(7, medicalBean.getPHYSICAL_EXAMINATION());
			ps.setString(8, medicalBean.getDIAGNOSE());
			ps.setString(9, medicalBean.getSUGGEST());
			ps.setString(10, medicalBean.getPHOTO_URL());
			ps.setString(11, medicalBean.getREMARK());
			ps.setLong(12, medicalBean.getMEDICAL_NO());
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("MedicalDAL Function toUpdate End.");
		return dataNum;// 返回更新的数据数量
	}
	// 插入
	public int toInsert(HM_MEDICAL medicalBean) {
		HLogger.info("MedicalDAL Function toInsert Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "INSERT INTO hm_medical (\n" +
				"	USER_NO,\n" +
				"	INQUIRY_TIME,\n" +
				"	HOSPITAL_NAME,\n" +
				"	DEPT_NAME,\n" +
				"	DOCTOR_NAME,\n" +
				"	MAIN_SUIT,\n" +
				"	MEDICAL_HISTORY,\n" +
				"	PHYSICAL_EXAMINATION,\n" +
				"	DIAGNOSE,\n" +
				"	SUGGEST,\n" +
				"	PHOTO_URL,\n" +
				"	REMARK\n" +
				")\n" +
				"VALUES\n" +
				"	(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setLong(1,medicalBean.getUSER_NO());
			ps.setTimestamp(2, new java.sql.Timestamp(medicalBean.getINQUIRY_TIME().getTime()));
			ps.setString(3, medicalBean.getHOSPITAL_NAME());
			ps.setString(4, medicalBean.getDEPT_NAME());
			ps.setString(5, medicalBean.getDOCTOR_NAME());
			ps.setString(6, medicalBean.getMAIN_SUIT());
			ps.setString(7, medicalBean.getMEDICAL_HISTORY());
			ps.setString(8, medicalBean.getPHYSICAL_EXAMINATION());
			ps.setString(9, medicalBean.getDIAGNOSE());
			ps.setString(10, medicalBean.getSUGGEST());
			ps.setString(11, medicalBean.getPHOTO_URL());
			ps.setString(12, medicalBean.getREMARK());
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("MedicalDAL Function toInsert End.");
		return dataNum;// 返回更新的数据数量
	}
}
