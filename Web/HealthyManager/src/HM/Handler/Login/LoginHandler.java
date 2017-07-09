package HM.Handler.Login;

import javax.servlet.http.HttpServlet;

import HM.BLL.Login.LoginBLL;
import HM.BLL.Signin.SigninBLL;
import HRP.Comm.Util.HLogger;

public class LoginHandler extends HttpServlet {
	// 用户登录
	public String toLogin(String JsonData) {
		HLogger.info("LoginHandler Function toLogin Begin.");
		String jsonResultString = "";

		LoginBLL loginBLL = new LoginBLL();
		jsonResultString = loginBLL.toLogin(JsonData);

		HLogger.info("LoginHandler Function toLogin End.");
		return jsonResultString;
	}

	// 修改上次登录时间
	public int toUpdateLastLogTime(String JsonData) {
		HLogger.info("LoginHandler Function toUpdateLastLogTime Begin.");
		int dataNum = 0;

		LoginBLL loginBLL = new LoginBLL();
		dataNum = loginBLL.toUpdateLastLogTime(JsonData);

		HLogger.info("LoginHandler Function toUpdateLastLogTime End.");
		return dataNum;
	}
}
