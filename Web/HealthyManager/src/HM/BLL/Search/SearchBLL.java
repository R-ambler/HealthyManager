package HM.BLL.Search;

import java.util.List;

import HM.DAL.Search.SearchDAL;
import HM.Model.HM_SEARCH;
import HRP.Comm.Util.DotNetToJavaStringHelper;
import HRP.Comm.Util.HLogger;

import com.google.gson.Gson;

public class SearchBLL {
	
	SearchDAL searchDAL = new SearchDAL();
	// 查询
	public String toSelect(String jsonString) {
		HLogger.info("SearchBLL Function toSelect Begin.");
		Gson gson = new Gson();
		String jsonResultString = "";
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_SEARCH searchBean = gson.fromJson(jsonString, HM_SEARCH.class);
			long user_no = searchBean.getUSER_NO();
			// 查询登录用户的数据
			List<HM_SEARCH> list = searchDAL.toSelect(user_no);
			jsonResultString = gson.toJson(list); // list转jsonString
		}
		HLogger.info("SearchBLL Function toSelect End.");
		return jsonResultString;
	}
	// 删除
	public int toDelete(String jsonString) {
		HLogger.info("SearchBLL Function toDelete Begin.");
		int dataNum = 0;
		Gson gson = new Gson();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_SEARCH searchBean = gson.fromJson(jsonString, HM_SEARCH.class);
			int search_no = searchBean.getSEARCH_NO();
			// 查询登录用户的数据
			dataNum = searchDAL.toDelete(search_no);
		}
		HLogger.info("SearchBLL Function toDelete End.");
		return dataNum;
	}
	// 修改
	public int toUpdate(String jsonString) {
		HLogger.info("SearchBLL Function toUpdate Begin.");
		int dataNum = 0;
		Gson gson = new Gson();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_SEARCH searchBean = gson.fromJson(jsonString, HM_SEARCH.class);
			// 查询登录用户的数据
			dataNum = searchDAL.toUpdate(searchBean);
		}
		HLogger.info("SearchBLL Function toUpdate End.");
		return dataNum;
	}
	// 插入
	public int toInsert(String jsonString) {
		HLogger.info("SearchBLL Function toInsert Begin.");
		int dataNum = 0;
		Gson gson = new Gson();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(jsonString)) {
			// Json转Bean
			HM_SEARCH searchBean = gson.fromJson(jsonString, HM_SEARCH.class);
			// 查询登录用户的数据
			dataNum = searchDAL.toInsert(searchBean);
		}
		HLogger.info("SearchBLL Function toInsert End.");
		return dataNum;
	}
}
