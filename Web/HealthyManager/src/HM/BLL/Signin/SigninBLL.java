package HM.BLL.Signin;

import HM.DAL.Signin.SigninDAL;
import HM.Model.HM_USER_INFO;
import HRP.Comm.Util.DotNetToJavaStringHelper;
import HRP.Comm.Util.HLogger;

import com.google.gson.Gson;

/**
 * 
 * 模块：用户注册BLL层
 * 功能：
 * 作者：Administrator
 * 时间：2017年6月4日下午5:36:01
 */
public class SigninBLL {
	
	//用户注册
	public int toSignin(String jsonString){
		HLogger.info("SigninBLL Function toSignin Begin.");
		int dataNum = 0;
		Gson gson = new Gson();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_USER_INFO UserInfoBean = gson.fromJson(jsonString,
					HM_USER_INFO.class);

			String user_name = UserInfoBean.getUSER_NAME();
			String password = UserInfoBean.getPASSWORD();
			String phone_number = UserInfoBean.getPHONE_NUMBER();

			SigninDAL signinDAL = new SigninDAL();
			dataNum = signinDAL.toSignin(user_name, password, phone_number);
		}
		HLogger.info("SigninBLL Function toSignin End.");
		return dataNum;
	}
	//确认手机号是否已注册
	public int toVerifyPhoneNumExist(String jsonString) {
		HLogger.info("SigninBLL Function toVerifyPhoneNumExist Begin.");
		int dataNum = 0;
		Gson gson = new Gson();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_USER_INFO UserInfoBean = gson.fromJson(jsonString,
					HM_USER_INFO.class);

			String phone_number = UserInfoBean.getPHONE_NUMBER();

			SigninDAL signinDAL = new SigninDAL();
			dataNum = signinDAL.toVerifyPhoneNumExist(phone_number);
		}
		HLogger.info("SigninBLL Function toVerifyPhoneNumExist End.");
		return dataNum;
	}
}
