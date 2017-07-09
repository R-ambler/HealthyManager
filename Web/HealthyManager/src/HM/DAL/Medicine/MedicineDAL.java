package HM.DAL.Medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import HM.Comm.CommFunction;
import HM.Comm.Database;
import HM.Model.HM_MEDICINE;
import HRP.Comm.Util.HLogger;

public class MedicineDAL extends Database {
	Connection con = null;
	PreparedStatement ps = null;
	Statement st = null;
	ResultSet rs = null;

	// 根据处方编号查询药品信息
	public List<HM_MEDICINE> toSelectByPrescriptionNo(long prescription_no) {
		HLogger.info("MedicineDAL Function toSelectByPrescriptionNo Begin.");
		List<HM_MEDICINE> list = new ArrayList<HM_MEDICINE>();
		HM_MEDICINE medicineBean = null;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	hm_medicine hm\n" +
				"WHERE\n" +
				"	hm.PRESCRIPTION_NO = ? ";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setLong(1, prescription_no);
			// 执行数据库操作
			rs = ps.executeQuery();
			
			while (rs.next()) {
				medicineBean = new HM_MEDICINE();
				medicineBean.setMEDICINE_NO(rs.getInt("MEDICINE_NO"));
				medicineBean.setPRESCRIPTION_NO(rs.getInt("PRESCRIPTION_NO"));
				medicineBean.setMEDICINE_NAME(rs.getString("MEDICINE_NAME"));
				medicineBean.setMEDICINE_SPEC(rs.getString("MEDICINE_SPEC"));
				medicineBean.setMEDICINE_DOSAGE(rs.getString("MEDICINE_DOSAGE"));
				medicineBean.setFREQUENCY(rs.getString("FREQUENCY"));
				medicineBean.setWAY(rs.getString("WAY"));
				medicineBean.setPHOTO_URL(rs.getString("PHOTO_URL"));
				medicineBean.setREMARK(rs.getString("REMARK"));
				medicineBean.setPRODUCER(rs.getString("PRODUCER"));

				list.add(medicineBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("MedicineDAL Function toSelectByPrescriptionNo End.");
		return list;// 返回更新的数据数量
	}

	// 删除
	public int toDelete(long medicine_no) {
		HLogger.info("MedicineDAL Function toDelete Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "DELETE\n" +
				"FROM\n" +
				"	hm_medicine\n" +
				"WHERE\n" +
				"	MEDICINE_NO = ?";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setLong(1, medicine_no);
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("MedicineDAL Function toDelete End.");
		return dataNum;// 返回更新的数据数量
	}
	// 根据处方编号删除
	public int toDeleteByPrescriptionNo(long prescription_no) {
		HLogger.info("MedicineDAL Function toDeleteByPrescriptionNo Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "DELETE\n" +
				"FROM\n" +
				"	hm_medicine\n" +
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
		HLogger.info("MedicineDAL Function toDeleteByPrescriptionNo End.");
		return dataNum;// 返回更新的数据数量
	}
	// 修改
	public int toUpdate(HM_MEDICINE medicineBean) {
		HLogger.info("MedicineDAL Function toUpdate Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "UPDATE hm_medicine\n" +
				"SET MEDICINE_NAME =?, \n" +
				"	MEDICINE_SPEC =?, \n" +
				"	MEDICINE_DOSAGE =?, \n" +
				"	FREQUENCY =?, \n" +
				"	WAY =?, \n" +
//				"	PHOTO_URL =?, \n" +
				"	REMARK =?, \n" +
				"	PRODUCER =? \n" +
				"WHERE\n" +
				"	MEDICINE_NO = ?";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setString(1, medicineBean.getMEDICINE_NAME());
			ps.setString(2, medicineBean.getMEDICINE_SPEC());
			ps.setString(3, medicineBean.getMEDICINE_DOSAGE());
			ps.setString(4, medicineBean.getFREQUENCY());
			ps.setString(5, medicineBean.getWAY());
			ps.setString(6, medicineBean.getREMARK());
			ps.setString(7, medicineBean.getPRODUCER());
			ps.setLong(8,medicineBean.getMEDICINE_NO());
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("MedicineDAL Function toUpdate End.");
		return dataNum;// 返回更新的数据数量
	}
	// 插入
	public int toInsert(HM_MEDICINE medicineBean) {
		HLogger.info("MedicineDAL Function toInsert Begin.");
		int dataNum = 0;
		// 获得连接
		con = getCon();
		// 编写sql语句
		String sql = "INSERT INTO hm_medicine (\n" +
				"	PRESCRIPTION_NO,\n" +
				"	MEDICINE_NAME,\n" +
				"	MEDICINE_SPEC,\n" +
				"	MEDICINE_DOSAGE,\n" +
				"	FREQUENCY,\n" +
				"	WAY,\n" +
//				"	PHOTO_URL,\n" +
				"	REMARK,\n" +
				"	PRODUCER\n" +
				")\n" +
				"VALUES\n" +
				"	(?,?,?,?,?,?,?,?)";
		try {
			// 预编译
			ps = con.prepareStatement(sql);
			// 给占位符赋值
			ps.setLong(1,medicineBean.getPRESCRIPTION_NO());
			ps.setString(2, medicineBean.getMEDICINE_NAME());
			ps.setString(3, medicineBean.getMEDICINE_SPEC());
			ps.setString(4, medicineBean.getMEDICINE_DOSAGE());
			ps.setString(5, medicineBean.getFREQUENCY());
			ps.setString(6, medicineBean.getWAY());
			ps.setString(7, medicineBean.getREMARK());
			ps.setString(8, medicineBean.getPRODUCER());
			// 执行数据库操作
			dataNum = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接
			CommFunction.toCloseDBCon(rs, ps, con, st);
		}
		HLogger.info("MedicineDAL Function toInsert End.");
		return dataNum;// 返回更新的数据数量
	}
}
