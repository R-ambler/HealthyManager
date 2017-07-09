package HM.BLL.FindPassword;

import HM.DAL.FindPassword.FindPasswordDAL;
import HM.Model.HM_USER_INFO;
import HRP.Comm.Util.DotNetToJavaStringHelper;
import HRP.Comm.Util.HLogger;

import com.google.gson.Gson;

public class FindPasswordBLL {

	// 找回密码/重置密码
	public int toFindPassword(String jsonString) {
		HLogger.info("FindPasswordBLL Function toFindPassword Begin.");
		int dataNum = 0;
		Gson gson = new Gson();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_USER_INFO UserInfoBean = gson.fromJson(jsonString,
					HM_USER_INFO.class);
			String phone_number = UserInfoBean.getPHONE_NUMBER();
			String password = UserInfoBean.getPASSWORD();

			FindPasswordDAL findPasswordDAL = new FindPasswordDAL();
			dataNum = findPasswordDAL.toFindPassword(phone_number,password);
		}
		HLogger.info("FindPasswordBLL Function toFindPassword End.");
		return dataNum;
	}
}
