package HM.Handler.FindPassword;

import javax.servlet.http.HttpServlet;

import HM.BLL.FindPassword.FindPasswordBLL;
import HRP.Comm.Util.HLogger;

public class FindPasswordHandler extends HttpServlet {

	// 修改上次登录时间
	public int toFindPassword(String JsonData) {
		HLogger.info("FindPasswordHandler Function toFindPassword Begin.");
		int dataNum = 0;

		FindPasswordBLL findPasswordBLL = new FindPasswordBLL();
		dataNum = findPasswordBLL.toFindPassword(JsonData);

		HLogger.info("FindPasswordHandler Function toFindPassword End.");
		return dataNum;
	}
}
