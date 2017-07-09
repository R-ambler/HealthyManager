package Model;

import java.util.Date;

/**
 * 
 * 模块：HM_CLOCK 实体类
 * 功能：
 * 作者： Administrator
 * 时间： 2017-5-22 上午9:44:43
 *
 */
public class HM_CLOCK {
	private int CLOCK_NO;		// 闹钟编号
	private String FREQUENCY;	// 频率（0：一次；1：每天一次；2：每天两次；3、每天三次）
	private Date TIME1;			// 时间1
	private Date TIME2;			// 时间2
	private Date TIME3;			// 时间3
	private long USER_NO;		// 用户编号
	private String BELL_URL;	// 铃声路径
	private String REMARK;		// 备注
	private String ITEM;		// 主题

	public int getCLOCK_NO() {
		return CLOCK_NO;
	}

	public void setCLOCK_NO(int cLOCK_NO) {
		CLOCK_NO = cLOCK_NO;
	}

	public String getFREQUENCY() {
		return FREQUENCY;
	}

	public void setFREQUENCY(String fREQUENCY) {
		FREQUENCY = fREQUENCY;
	}

	public Date getTIME1() {
		return TIME1;
	}

	public void setTIME1(Date tIME1) {
		TIME1 = tIME1;
	}

	public Date getTIME2() {
		return TIME2;
	}

	public void setTIME2(Date tIME2) {
		TIME2 = tIME2;
	}

	public Date getTIME3() {
		return TIME3;
	}

	public void setTIME3(Date tIME3) {
		TIME3 = tIME3;
	}

	public long getUSER_NO() {
		return USER_NO;
	}

	public void setUSER_NO(long uSER_NO) {
		USER_NO = uSER_NO;
	}

	public String getBELL_URL() {
		return BELL_URL;
	}

	public void setBELL_URL(String bELL_URL) {
		BELL_URL = bELL_URL;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getITEM() {
		return ITEM;
	}

	public void setITEM(String iTEM) {
		ITEM = iTEM;
	}

}
