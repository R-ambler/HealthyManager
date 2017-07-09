package Model;

import java.util.Date;

/**
 * 
 * 模块：HM_SCHEDULE 实体类
 * 功能：
 * 作者： Administrator
 * 时间： 2017-5-22 上午9:59:41
 *
 */
public class HM_SCHEDULE {

	private long SCHEDULE_NO;	// 提醒编号
	private String ITEM;		// 主题
	private String CONTENT;		// 内容
	private Date TIME;			// 时间
	private String BELL_URL;	// 铃声路径
	private long USER_NO;		// 用户编号

	public long getSCHEDULE_NO() {
		return SCHEDULE_NO;
	}

	public void setSCHEDULE_NO(long sCHEDULE_NO) {
		SCHEDULE_NO = sCHEDULE_NO;
	}

	public String getITEM() {
		return ITEM;
	}

	public void setITEM(String iTEM) {
		ITEM = iTEM;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public Date getTIME() {
		return TIME;
	}

	public void setTIME(Date tIME) {
		TIME = tIME;
	}

	public String getBELL_URL() {
		return BELL_URL;
	}

	public void setBELL_URL(String bELL_URL) {
		BELL_URL = bELL_URL;
	}

	public long getUSER_NO() {
		return USER_NO;
	}

	public void setUSER_NO(long uSER_NO) {
		USER_NO = uSER_NO;
	}

}
