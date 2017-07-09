package app.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import HM.Comm.*;
import HM.Handler.FindPassword.FindPasswordHandler;
import HM.Handler.Login.LoginHandler;
import HM.Handler.Medical.MedicalHandler;
import HM.Handler.Medicine.MedicineHandler;
import HM.Handler.Prescription.PrescriptionHandler;
import HM.Handler.Search.SearchHandler;
import HM.Handler.Signin.SigninHandler;
import HRP.Comm.Util.HLogger;

/**
 * 
 * 模块：APP服务类
 * 功能：APP与后台数据交互接口
 * 作者： Administrator
 * 时间： 2017-5-24 上午9:47:38
 *
 */
public class AppService extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AppService() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HLogger.info("AppService Function doPost Begin.");
		int dataNum = -1;
		String jsonResultString = "";
		
		//获取前台传递的json数据
		String jsonString = CommFunction.getRequest(request);
		//解析json
		JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
		//初始化参数
		String opName = jsonArray.get(0).getAsJsonObject().get("OP_NAME").getAsString();
		String JsonData = jsonArray.get(1).getAsJsonObject().get("JsonData").getAsString();
		
		//确认手机号是否已注册
		if("VerifyPhoneNumExistService".equals(opName)){
			SigninHandler signinHandler = new SigninHandler();
			dataNum = signinHandler.toVerifyPhoneNumExist(JsonData);
		}
		//用户注册
		if("SigninService".equals(opName)){
			SigninHandler signinHandler = new SigninHandler();
			dataNum = signinHandler.toSignin(JsonData);
		}
		//用户登录
		if("LoginService".equals(opName)){
			LoginHandler loginHandler = new LoginHandler();
			jsonResultString = loginHandler.toLogin(JsonData);
		}
		//修改上次登录时间
		if("UpdateLastLogTimeService".equals(opName)){
			LoginHandler loginHandler = new LoginHandler();
			dataNum = loginHandler.toUpdateLastLogTime(JsonData);
		}
		//找回密码
		if("FindPasswordService".equals(opName)){
			FindPasswordHandler findPasswordHandler = new FindPasswordHandler();
			dataNum = findPasswordHandler.toFindPassword(JsonData);
		}
		//查询网址信息
		if("SelectSearchInfoService".equals(opName)){
			SearchHandler searchHandler = new SearchHandler();
			jsonResultString = searchHandler.toSelect(JsonData);
		}
		//删除网址信息
		if("DeleteSearchInfoService".equals(opName)){
			SearchHandler searchHandler = new SearchHandler();
			dataNum = searchHandler.toDelete(JsonData);
		}
		//修改网址信息
		if("UpdateSearchInfoService".equals(opName)){
			SearchHandler searchHandler = new SearchHandler();
			dataNum = searchHandler.toUpdate(JsonData);
		}
		//插入网址信息
		if("InsertSearchInfoService".equals(opName)){
			SearchHandler searchHandler = new SearchHandler();
			dataNum = searchHandler.toInsert(JsonData);
		}
		//查询病历信息
		if("SelectMedicalInfoService".equals(opName)){
			MedicalHandler medicalHandler = new MedicalHandler();
			jsonResultString = medicalHandler.toSelect(JsonData);
		}
		//删除病历信息
		if("DeleteMedicalInfoService".equals(opName)){
			MedicalHandler medicalHandler = new MedicalHandler();
			dataNum = medicalHandler.toDelete(JsonData);
		}
		//修改病历信息
		if("UpdateMedicalInfoService".equals(opName)){
			MedicalHandler medicalHandler = new MedicalHandler();
			dataNum = medicalHandler.toUpdate(JsonData);
		}
		//插入病历信息
		if("InsertMedicalInfoService".equals(opName)){
			MedicalHandler medicalHandler = new MedicalHandler();
			dataNum = medicalHandler.toInsert(JsonData);
		}
		//查询处方信息
		if("SelectPrescriptionInfoService".equals(opName)){
			PrescriptionHandler prescriptionHandler = new PrescriptionHandler();
			jsonResultString = prescriptionHandler.toSelect(JsonData);
		}
		//删除处方信息
		if("DeletePrescriptionInfoService".equals(opName)){
			//根据处方编号删除药品信息
			MedicineHandler medicineHandler = new MedicineHandler();
			medicineHandler.toDeleteByPrescriptionNo(JsonData);
			//删除处方信息
			PrescriptionHandler prescriptionHandler = new PrescriptionHandler();
			dataNum = prescriptionHandler.toDelete(JsonData);
		}
		//修改处方信息
		if("UpdatePrescriptionInfoService".equals(opName)){
			PrescriptionHandler prescriptionHandler = new PrescriptionHandler();
			dataNum = prescriptionHandler.toUpdate(JsonData);
		}
		//插入处方信息
		if("InsertPrescriptionInfoService".equals(opName)){
			PrescriptionHandler prescriptionHandler = new PrescriptionHandler();
			dataNum = prescriptionHandler.toInsert(JsonData);
		}
		//根据处方编号查询药品信息
		if("SelectMedicineInfoByPrescriptionNoService".equals(opName)){
			MedicineHandler medicineHandler = new MedicineHandler();
			jsonResultString = medicineHandler.toSelectByPrescriptionNo(JsonData);
		}
		//删除药品信息
		if("DeleteMedicineInfoService".equals(opName)){
			MedicineHandler medicineHandler = new MedicineHandler();
			dataNum = medicineHandler.toDelete(JsonData);
		}
		//修改药品信息
		if("UpdateMedicineInfoService".equals(opName)){
			MedicineHandler medicineHandler = new MedicineHandler();
			dataNum = medicineHandler.toUpdate(JsonData);
		}
		//插入药品信息
		if("InsertMedicineInfoService".equals(opName)){
			MedicineHandler medicineHandler = new MedicineHandler();
			dataNum = medicineHandler.toInsert(JsonData);
		}
		//向前台传递数据
		JsonArray jsonResultArray = new JsonArray();
		JsonObject jsonResultDataNum = new JsonObject();
		jsonResultDataNum.addProperty("dataNum", dataNum);
		JsonObject jsonResultData = new JsonObject();
		jsonResultData.addProperty("JsonResultData", jsonResultString);
		jsonResultArray.add(jsonResultDataNum);
		jsonResultArray.add(jsonResultData);
		
		String resultString = jsonResultArray.toString();
        CommFunction.setResponse(response,resultString);
        
		HLogger.info("AppService Function doPost End.");
	}
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
}
