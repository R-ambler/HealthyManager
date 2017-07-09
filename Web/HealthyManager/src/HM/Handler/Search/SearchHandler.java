package HM.Handler.Search;

import javax.servlet.http.HttpServlet;

import HM.BLL.Search.SearchBLL;
import HRP.Comm.Util.HLogger;

public class SearchHandler extends HttpServlet {
	
	SearchBLL searchBLL = new SearchBLL();
	// 查询数据
	public String toSelect(String JsonData) {
		HLogger.info("SearchHandler Function toSelect Begin.");
		String jsonResultString = "";

		jsonResultString = searchBLL.toSelect(JsonData);

		HLogger.info("SearchHandler Function toSelect End.");
		return jsonResultString;
	}
	// 删除数据
	public int toDelete(String JsonData) {
		HLogger.info("SearchHandler Function toDelete Begin.");
		int dataNum = 0;
		
		dataNum = searchBLL.toDelete(JsonData);
		
		HLogger.info("SearchHandler Function toDelete End.");
		return dataNum;
	}
	// 修改数据
	public int toUpdate(String JsonData) {
		HLogger.info("SearchHandler Function toUpdate Begin.");
		int dataNum = 0;
		
		dataNum = searchBLL.toUpdate(JsonData);
		
		HLogger.info("SearchHandler Function toUpdate End.");
		return dataNum;
	}
	// 插入数据
	public int toInsert(String JsonData) {
		HLogger.info("SearchHandler Function toInsert Begin.");
		int dataNum = 0;
		
		dataNum = searchBLL.toInsert(JsonData);
		
		HLogger.info("SearchHandler Function toInsert End.");
		return dataNum;
	}
}
