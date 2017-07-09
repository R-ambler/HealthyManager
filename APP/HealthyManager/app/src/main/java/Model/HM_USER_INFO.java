package Model;

import java.util.Date;

/**
 * 
 * 模块：HM_USER_INFO 实体类
 * 功能：
 * 作者： Administrator
 * 时间： 2017-5-22 上午10:07:44
 *
 */
public class HM_USER_INFO {

	private long USER_NO;// 用户编号
	private String USER_NAME;// 用户名
	private String PHONE_NUMBER;// 联系电话
	private String PASSWORD;// 登录密码
	private Date LAST_LOG_TIME;// 最后登录时间

	public long getUSER_NO() {
		return USER_NO;
	}

	public void setUSER_NO(long uSER_NO) {
		USER_NO = uSER_NO;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getPHONE_NUMBER() {
		return PHONE_NUMBER;
	}

	public void setPHONE_NUMBER(String pHOTO_NUMBER) {
		PHONE_NUMBER = pHOTO_NUMBER;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public Date getLAST_LOG_TIME() {
		return LAST_LOG_TIME;
	}

	public void setLAST_LOG_TIME(Date lAST_LOG_TIME) {
		LAST_LOG_TIME = lAST_LOG_TIME;
	}
}
