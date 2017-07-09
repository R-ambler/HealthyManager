package HM.BLL.Login;

import java.util.List;

import HM.DAL.Login.LoginDAL;
import HM.Model.HM_USER_INFO;
import HRP.Comm.Util.DotNetToJavaStringHelper;
import HRP.Comm.Util.HLogger;

import com.google.gson.Gson;

/**
 * 用户登录
 * 
 * @author Administrator
 * @date 2017-5-10 上午11:18:51
 *
 */
public class LoginBLL {

	// 用户登录
	public String toLogin(String jsonString) {
		HLogger.info("LoginBLL Function toLogin Begin.");
		Gson gson = new Gson();
		String jsonResultString = "";
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_USER_INFO UserInfoBean = gson.fromJson(jsonString,
					HM_USER_INFO.class);
			String phone_number = UserInfoBean.getPHONE_NUMBER();

			LoginDAL loginDAL = new LoginDAL();
			//查询登录用户的数据
			List<HM_USER_INFO> list = loginDAL.toLogin(phone_number);
			jsonResultString = gson.toJson(list);	//list转jsonString
		}
		HLogger.info("LoginBLL Function toLogin End.");
		return jsonResultString;
	}
	//修改上次登录时间
	public int toUpdateLastLogTime(String jsonString){
		HLogger.info("LoginBLL Function toUpdateLastLogTime Begin.");
		int dataNum = 0;
		Gson gson = new Gson();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_USER_INFO UserInfoBean = gson.fromJson(jsonString,
					HM_USER_INFO.class);
			String phone_number = UserInfoBean.getPHONE_NUMBER();

			LoginDAL loginDAL = new LoginDAL();
			//更新上次登录时间
			dataNum = loginDAL.toUpdateLastLogTime(phone_number);
		}
		HLogger.info("LoginBLL Function toUpdateLastLogTime End.");
		return dataNum;
	}
}
