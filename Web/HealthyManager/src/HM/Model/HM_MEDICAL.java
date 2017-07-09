package HM.Model;

import java.util.Date;

/**
 * 
 * 模块：HM_MEDICAL 实体类
 * 功能：
 * 作者： Administrator
 * 时间： 2017-5-22 上午9:45:19
 *
 */
public class HM_MEDICAL {

	private long MEDICAL_NO;				// 病历编号
	private long USER_NO;					// 用户编号
	private Date INQUIRY_TIME;				// 就诊日期
	private String HOSPITAL_NAME;			// 医院名称
	private String DEPT_NAME;				// 科室名称
	private String DOCTOR_NAME;				// 医生姓名
	private String MAIN_SUIT;				// 主诉
	private String MEDICAL_HISTORY;			// 现病史
	private String PHYSICAL_EXAMINATION;	// 查体
	private String DIAGNOSE;				// 诊断
	private String SUGGEST;					// 诊疗建议
	private long PRESCRIPTION_NO;			// 处方编号
	private String PHOTO_URL;				// 照片路径
	private String REMARK;					// 备注

	public long getMEDICAL_NO() {
		return MEDICAL_NO;
	}

	public void setMEDICAL_NO(long mEDICAL_NO) {
		MEDICAL_NO = mEDICAL_NO;
	}

	public long getUSER_NO() {
		return USER_NO;
	}

	public void setUSER_NO(long uSER_NO) {
		USER_NO = uSER_NO;
	}

	public Date getINQUIRY_TIME() {
		return INQUIRY_TIME;
	}

	public void setINQUIRY_TIME(Date iNQUIRY_TIME) {
		INQUIRY_TIME = iNQUIRY_TIME;
	}

	public String getHOSPITAL_NAME() {
		return HOSPITAL_NAME;
	}

	public void setHOSPITAL_NAME(String hOSPITAL_NAME) {
		HOSPITAL_NAME = hOSPITAL_NAME;
	}

	public String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
	}

	public String getDOCTOR_NAME() {
		return DOCTOR_NAME;
	}

	public void setDOCTOR_NAME(String dOCTOR_NAME) {
		DOCTOR_NAME = dOCTOR_NAME;
	}

	public String getMAIN_SUIT() {
		return MAIN_SUIT;
	}

	public void setMAIN_SUIT(String mAIN_SUIT) {
		MAIN_SUIT = mAIN_SUIT;
	}

	public String getMEDICAL_HISTORY() {
		return MEDICAL_HISTORY;
	}

	public void setMEDICAL_HISTORY(String mEDICAL_HISTORY) {
		MEDICAL_HISTORY = mEDICAL_HISTORY;
	}

	public String getPHYSICAL_EXAMINATION() {
		return PHYSICAL_EXAMINATION;
	}

	public void setPHYSICAL_EXAMINATION(String pHYSICAL_EXAMINATION) {
		PHYSICAL_EXAMINATION = pHYSICAL_EXAMINATION;
	}

	public String getDIAGNOSE() {
		return DIAGNOSE;
	}

	public void setDIAGNOSE(String dIAGNOSE) {
		DIAGNOSE = dIAGNOSE;
	}

	public String getSUGGEST() {
		return SUGGEST;
	}

	public void setSUGGEST(String sUGGEST) {
		SUGGEST = sUGGEST;
	}

	public long getPRESCRIPTION_NO() {
		return PRESCRIPTION_NO;
	}

	public void setPRESCRIPTION_NO(long pRESCRIPTION_NO) {
		PRESCRIPTION_NO = pRESCRIPTION_NO;
	}

	public String getPHOTO_URL() {
		return PHOTO_URL;
	}

	public void setPHOTO_URL(String pHOTO_URL) {
		PHOTO_URL = pHOTO_URL;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

}
