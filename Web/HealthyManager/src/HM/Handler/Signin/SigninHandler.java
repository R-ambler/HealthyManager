package HM.Handler.Signin;

import javax.servlet.http.HttpServlet;

import HM.BLL.Signin.SigninBLL;
import HRP.Comm.Util.HLogger;

public class SigninHandler extends HttpServlet {

	//用户注册
	public int toSignin(String JsonData){
		HLogger.info("SigninHandler Function toSignin Begin.");
		int dataNum = 0;

		SigninBLL signinBLL = new SigninBLL();
		dataNum = signinBLL.toSignin(JsonData);

		HLogger.info("SigninHandler Function toSignin End.");
		return dataNum;
	}
	//确认手机号是否已注册
	public int toVerifyPhoneNumExist(String JsonData){
		HLogger.info("SigninHandler Function toVerifyPhoneNumExist Begin.");
		int dataNum = 0;

		SigninBLL signinBLL = new SigninBLL();
		dataNum = signinBLL.toVerifyPhoneNumExist(JsonData);

		HLogger.info("SigninHandler Function toVerifyPhoneNumExist End.");
		return dataNum;
	}
}
