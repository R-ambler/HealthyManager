package HM.Handler.Medical;

import javax.servlet.http.HttpServlet;

import HM.BLL.Medical.MedicalBLL;
import HRP.Comm.Util.HLogger;

public class MedicalHandler extends HttpServlet {
	
	MedicalBLL medicalBLL = new MedicalBLL();
	// 查询数据
	public String toSelect(String JsonData) {
		HLogger.info("MedicalHandler Function toSelect Begin.");
		String jsonResultString = "";

		jsonResultString = medicalBLL.toSelect(JsonData);

		HLogger.info("MedicalHandler Function toSelect End.");
		return jsonResultString;
	}
	// 删除数据
	public int toDelete(String JsonData) {
		HLogger.info("MedicalHandler Function toDelete Begin.");
		int dataNum = 0;
		
		dataNum = medicalBLL.toDelete(JsonData);
		
		HLogger.info("MedicalHandler Function toDelete End.");
		return dataNum;
	}
	// 修改数据
	public int toUpdate(String JsonData) {
		HLogger.info("MedicalHandler Function toUpdate Begin.");
		int dataNum = 0;
		
		dataNum = medicalBLL.toUpdate(JsonData);
		
		HLogger.info("MedicalHandler Function toUpdate End.");
		return dataNum;
	}
	// 插入数据
	public int toInsert(String JsonData) {
		HLogger.info("MedicalHandler Function toInsert Begin.");
		int dataNum = 0;
		
		dataNum = medicalBLL.toInsert(JsonData);
		
		HLogger.info("MedicalHandler Function toInsert End.");
		return dataNum;
	}
}
