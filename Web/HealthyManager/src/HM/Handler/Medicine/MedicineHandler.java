package HM.Handler.Medicine;

import javax.servlet.http.HttpServlet;

import HM.BLL.Medicine.MedicineBLL;
import HRP.Comm.Util.HLogger;

public class MedicineHandler extends HttpServlet {

	MedicineBLL medicineBLL = new MedicineBLL();

	// 根据处方编号查询药品数据
	public String toSelectByPrescriptionNo(String JsonData) {
		HLogger.info("MedicineHandler Function toSelectByPrescriptionNo Begin.");
		String jsonResultString = "";

		jsonResultString = medicineBLL.toSelectByPrescriptionNo(JsonData);

		HLogger.info("MedicineHandler Function toSelectByPrescriptionNo End.");
		return jsonResultString;
	}

	// 删除数据
	public int toDelete(String JsonData) {
		HLogger.info("MedicineHandler Function toDelete Begin.");
		int dataNum = 0;

		dataNum = medicineBLL.toDelete(JsonData);

		HLogger.info("MedicineHandler Function toDelete End.");
		return dataNum;
	}

	// 根据处方编号删除
	public int toDeleteByPrescriptionNo(String JsonData) {
		HLogger.info("MedicineHandler Function toDeleteByPrescriptionNo Begin.");
		int dataNum = 0;

		dataNum = medicineBLL.toDeleteByPrescriptionNo(JsonData);

		HLogger.info("MedicineHandler Function toDeleteByPrescriptionNo End.");
		return dataNum;
	}
	 // 修改数据
	 public int toUpdate(String JsonData) {
	 HLogger.info("MedicineHandler Function toUpdate Begin.");
	 int dataNum = 0;
	
	 dataNum = medicineBLL.toUpdate(JsonData);
	
	 HLogger.info("MedicineHandler Function toUpdate End.");
	 return dataNum;
	 }
	 // 插入数据
	 public int toInsert(String JsonData) {
	 HLogger.info("MedicineHandler Function toInsert Begin.");
	 int dataNum = 0;
	
	 dataNum = medicineBLL.toInsert(JsonData);
	
	 HLogger.info("MedicineHandler Function toInsert End.");
	 return dataNum;
	 }
}
