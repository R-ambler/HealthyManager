package Model;

import java.util.Date;

/**
 * 
 * 模块：HM_PRESCRIPTION 实体类
 * 功能：
 * 作者：Administrator
 * 时间：2017年6月10日上午10:11:56
 */
public class HM_PRESCRIPTION {

	private long PRESCRIPTION_NO;			// 处方编号
	private String HOSPITAL_NAME;			// 医院/门诊名称
	private Date INQUIRY_TIME;				// 就诊日期
	private String REMARK;					// 备注
	private String PRESCRIPTION_ABSTRACT;	// 处方描述
	private String PHOTO_URL;				// 照片路径
	private long USER_NO;					// 用户编号
	
	public long getPRESCRIPTION_NO() {
		return PRESCRIPTION_NO;
	}
	public void setPRESCRIPTION_NO(long pRESCRIPTION_NO) {
		PRESCRIPTION_NO = pRESCRIPTION_NO;
	}
	public String getHOSPITAL_NAME() {
		return HOSPITAL_NAME;
	}
	public void setHOSPITAL_NAME(String hOSPITAL_NAME) {
		HOSPITAL_NAME = hOSPITAL_NAME;
	}
	public Date getINQUIRY_TIME() {
		return INQUIRY_TIME;
	}
	public void setINQUIRY_TIME(Date iNQUIRY_TIME) {
		INQUIRY_TIME = iNQUIRY_TIME;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getPRESCRIPTION_ABSTRACT() {
		return PRESCRIPTION_ABSTRACT;
	}
	public void setPRESCRIPTION_ABSTRACT(String pRESCRIPTION_ABSTRACT) {
		PRESCRIPTION_ABSTRACT = pRESCRIPTION_ABSTRACT;
	}
	public String getPHOTO_URL() {
		return PHOTO_URL;
	}
	public void setPHOTO_URL(String pHOTO_URL) {
		PHOTO_URL = pHOTO_URL;
	}
	public long getUSER_NO() {
		return USER_NO;
	}
	public void setUSER_NO(long uSER_NO) {
		USER_NO = uSER_NO;
	}
	
}
