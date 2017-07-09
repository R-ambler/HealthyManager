package HM.BLL.Medicine;

import java.util.List;

import HM.DAL.Medical.MedicalDAL;
import HM.DAL.Medicine.MedicineDAL;
import HM.Model.HM_MEDICINE;
import HRP.Comm.Util.DotNetToJavaStringHelper;
import HRP.Comm.Util.HLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MedicineBLL {

	MedicineDAL medicineDAL = new MedicineDAL();

	// 根据处方编号查询药品信息
	public String toSelectByPrescriptionNo(String jsonString) {
		HLogger.info("MedicineBLL Function toSelectByPrescriptionNo Begin.");
		Gson gson = new Gson();
		String jsonResultString = "";
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_MEDICINE medicineBean = gson.fromJson(jsonString, HM_MEDICINE.class);
			long prescription_no = medicineBean.getPRESCRIPTION_NO();
			// 根据处方编号查询药品数据
			List<HM_MEDICINE> list = medicineDAL.toSelectByPrescriptionNo(prescription_no);
			jsonResultString = gson.toJson(list); // list转jsonString
		}
		HLogger.info("MedicineBLL Function toSelectByPrescriptionNo End.");
		return jsonResultString;
	}

	// 删除
	public int toDelete(String jsonString) {
		HLogger.info("MedicineBLL Function toDelete Begin.");
		int dataNum = 0;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_MEDICINE medicineBean = gson.fromJson(jsonString, HM_MEDICINE.class);
			long medicine_no = medicineBean.getMEDICINE_NO();
			
			dataNum = medicineDAL.toDelete(medicine_no);
		}
		HLogger.info("MedicineBLL Function toDelete End.");
		return dataNum;
	}
	// 根据处方编号删除
	public int toDeleteByPrescriptionNo(String jsonString) {
		HLogger.info("MedicineBLL Function toDeleteByPrescriptionNo Begin.");
		int dataNum = 0;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_MEDICINE medicineBean = gson.fromJson(jsonString, HM_MEDICINE.class);
			long prescription_no = medicineBean.getPRESCRIPTION_NO();
			// 根据处方编号删除
			dataNum = medicineDAL.toDeleteByPrescriptionNo(prescription_no);
		}
		HLogger.info("MedicineBLL Function toDeleteByPrescriptionNo End.");
		return dataNum;
	}

	// 修改
	public int toUpdate(String jsonString) {
		HLogger.info("MedicineBLL Function toUpdate Begin.");
		int dataNum = 0;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_MEDICINE medicineBean = gson.fromJson(jsonString, HM_MEDICINE.class);
			// 查询登录用户的数据
			dataNum = medicineDAL.toUpdate(medicineBean);
		}
		HLogger.info("MedicineBLL Function toUpdate End.");
		return dataNum;
	}

	// 插入
	public int toInsert(String jsonString) {
		HLogger.info("MedicineBLL Function toInsert Begin.");
		int dataNum = 0;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_MEDICINE medicineBean = gson.fromJson(jsonString, HM_MEDICINE.class);
			// 查询登录用户的数据
			dataNum = medicineDAL.toInsert(medicineBean);
		}
		HLogger.info("MedicineBLL Function toInsert End.");
		return dataNum;
	}
}
