package HM.BLL.Prescription;

import java.util.List;

import HM.DAL.Prescription.PrescriptionDAL;
import HM.Model.HM_PRESCRIPTION;
import HRP.Comm.Util.DotNetToJavaStringHelper;
import HRP.Comm.Util.HLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PrescriptionBLL {

	PrescriptionDAL prescriptionDAL = new PrescriptionDAL();

	// 查询
	public String toSelect(String jsonString) {
		HLogger.info("PrescriptionBLL Function toSelect Begin.");
		Gson gson = new Gson();
		String jsonResultString = "";
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_PRESCRIPTION prescriptionBean = gson.fromJson(jsonString, HM_PRESCRIPTION.class);
			long user_no = prescriptionBean.getUSER_NO();
			// 查询登录用户的数据
			List<HM_PRESCRIPTION> list = prescriptionDAL.toSelect(user_no);
			jsonResultString = gson.toJson(list); // list转jsonString
		}
		HLogger.info("PrescriptionBLL Function toSelect End.");
		return jsonResultString;
	}

	// 删除
	public int toDelete(String jsonString) {
		HLogger.info("PrescriptionBLL Function toDelete Begin.");
		int dataNum = 0;
		Gson gson = new Gson();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_PRESCRIPTION prescriptionBean = gson.fromJson(jsonString, HM_PRESCRIPTION.class);
			long prescription_no = prescriptionBean.getPRESCRIPTION_NO();
			// 查询登录用户的数据
			dataNum = prescriptionDAL.toDelete(prescription_no);
		}
		HLogger.info("PrescriptionBLL Function toDelete End.");
		return dataNum;
	}

	// 修改
	public int toUpdate(String jsonString) {
		HLogger.info("PrescriptionBLL Function toUpdate Begin.");
		int dataNum = 0;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_PRESCRIPTION prescriptionBean = gson.fromJson(jsonString, HM_PRESCRIPTION.class);
			// 查询登录用户的数据
			dataNum = prescriptionDAL.toUpdate(prescriptionBean);
		}
		HLogger.info("PrescriptionBLL Function toUpdate End.");
		return dataNum;
	}

	// 插入
	public int toInsert(String jsonString) {
		HLogger.info("PrescriptionBLL Function toInsert Begin.");
		int dataNum = 0;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_PRESCRIPTION prescriptionBean = gson.fromJson(jsonString, HM_PRESCRIPTION.class);
			// 查询登录用户的数据
			dataNum = prescriptionDAL.toInsert(prescriptionBean);
		}
		HLogger.info("PrescriptionBLL Function toInsert End.");
		return dataNum;
	}
}
