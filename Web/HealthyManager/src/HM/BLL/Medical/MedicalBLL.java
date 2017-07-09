package HM.BLL.Medical;

import java.util.List;

import HM.DAL.Medical.MedicalDAL;
import HM.Model.HM_MEDICAL;
import HRP.Comm.Util.DotNetToJavaStringHelper;
import HRP.Comm.Util.HLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MedicalBLL {

	MedicalDAL medicalDAL = new MedicalDAL();

	// 查询
	public String toSelect(String jsonString) {
		HLogger.info("MedicalBLL Function toSelect Begin.");
		Gson gson = new Gson();
		String jsonResultString = "";
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_MEDICAL medicalBean = gson.fromJson(jsonString, HM_MEDICAL.class);
			long user_no = medicalBean.getUSER_NO();
			// 查询登录用户的数据
			List<HM_MEDICAL> list = medicalDAL.toSelect(user_no);
			jsonResultString = gson.toJson(list); // list转jsonString
		}
		HLogger.info("MedicalBLL Function toSelect End.");
		return jsonResultString;
	}

	// 删除
	public int toDelete(String jsonString) {
		HLogger.info("MedicalBLL Function toDelete Begin.");
		int dataNum = 0;
		Gson gson = new Gson();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_MEDICAL medicalBean = gson.fromJson(jsonString, HM_MEDICAL.class);
			long medical_no = medicalBean.getMEDICAL_NO();
			// 查询登录用户的数据
			dataNum = medicalDAL.toDelete(medical_no);
		}
		HLogger.info("MedicalBLL Function toDelete End.");
		return dataNum;
	}

	// 修改
	public int toUpdate(String jsonString) {
		HLogger.info("MedicalBLL Function toUpdate Begin.");
		int dataNum = 0;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_MEDICAL medicalBean = gson.fromJson(jsonString, HM_MEDICAL.class);
			// 查询登录用户的数据
			dataNum = medicalDAL.toUpdate(medicalBean);
		}
		HLogger.info("MedicalBLL Function toUpdate End.");
		return dataNum;
	}

	// 插入
	public int toInsert(String jsonString) {
		HLogger.info("MedicalBLL Function toInsert Begin.");
		int dataNum = 0;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();;
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_MEDICAL medicalBean = gson.fromJson(jsonString, HM_MEDICAL.class);
			// 查询登录用户的数据
			dataNum = medicalDAL.toInsert(medicalBean);
		}
		HLogger.info("MedicalBLL Function toInsert End.");
		return dataNum;
	}
}
