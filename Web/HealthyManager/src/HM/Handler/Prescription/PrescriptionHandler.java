package HM.Handler.Prescription;

import javax.servlet.http.HttpServlet;

import HM.BLL.Prescription.PrescriptionBLL;
import HRP.Comm.Util.HLogger;

public class PrescriptionHandler extends HttpServlet {
	
	PrescriptionBLL prescriptionBLL = new PrescriptionBLL();
	// 查询数据
	public String toSelect(String JsonData) {
		HLogger.info("PrescriptionHandler Function toSelect Begin.");
		String jsonResultString = "";

		jsonResultString = prescriptionBLL.toSelect(JsonData);

		HLogger.info("PrescriptionHandler Function toSelect End.");
		return jsonResultString;
	}
	// 删除数据
	public int toDelete(String JsonData) {
		HLogger.info("PrescriptionHandler Function toDelete Begin.");
		int dataNum = 0;
		
		dataNum = prescriptionBLL.toDelete(JsonData);
		
		HLogger.info("PrescriptionHandler Function toDelete End.");
		return dataNum;
	}
	// 修改数据
	public int toUpdate(String JsonData) {
		HLogger.info("PrescriptionHandler Function toUpdate Begin.");
		int dataNum = 0;
		
		dataNum = prescriptionBLL.toUpdate(JsonData);
		
		HLogger.info("PrescriptionHandler Function toUpdate End.");
		return dataNum;
	}
	// 插入数据
	public int toInsert(String JsonData) {
		HLogger.info("PrescriptionHandler Function toInsert Begin.");
		int dataNum = 0;
		
		dataNum = prescriptionBLL.toInsert(JsonData);
		
		HLogger.info("PrescriptionHandler Function toInsert End.");
		return dataNum;
	}
}
