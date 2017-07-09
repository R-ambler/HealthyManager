package HM.Model;

/**
 * 
 * 模块：HM_SEARCH 实体类
 * 功能：
 * 作者：Administrator
 * 时间：2017年6月8日下午2:37:32
 */
public class HM_SEARCH {

	private int SEARCH_NO;			//编号
	private String SEARCH_NAME;		//网址名称
	private String SEARCH_URL;		//网址路径
	private String SEARCH_ABSTRACT;	//简介
	private long USER_NO;			// 用户编号
	public int getSEARCH_NO() {
		return SEARCH_NO;
	}
	public void setSEARCH_NO(int sEARCH_NO) {
		SEARCH_NO = sEARCH_NO;
	}
	public String getSEARCH_NAME() {
		return SEARCH_NAME;
	}
	public void setSEARCH_NAME(String sEARCH_NAME) {
		SEARCH_NAME = sEARCH_NAME;
	}
	public String getSEARCH_URL() {
		return SEARCH_URL;
	}
	public void setSEARCH_URL(String sEARCH_URL) {
		SEARCH_URL = sEARCH_URL;
	}
	public String getSEARCH_ABSTRACT() {
		return SEARCH_ABSTRACT;
	}
	public void setSEARCH_ABSTRACT(String sEARCH_ABSTRACT) {
		SEARCH_ABSTRACT = sEARCH_ABSTRACT;
	}
	public long getUSER_NO() {
		return USER_NO;
	}
	public void setUSER_NO(long uSER_NO) {
		USER_NO = uSER_NO;
	}
}
