package Model;

/**
 * 
 * 模块：HM_MEDICINE 实体类
 * 功能：
 * 作者： Administrator
 * 时间： 2017-5-22 上午9:52:28
 *
 */
public class HM_MEDICINE {

	private long MEDICINE_NO;		// 药品编号
	private long PRESCRIPTION_NO;	// 处方编号
	private String MEDICINE_NAME;	// 药品名称
	private String MEDICINE_SPEC;	// 药品规格（数量）
	private String MEDICINE_DOSAGE;	// 药品用量（单位）
	private String FREQUENCY;		// 频次
	private String WAY;				// 用药途径
	private String PHOTO_URL;		// 照片路径
	private String REMARK;			// 备注
	private String PRODUCER;		//生产商

	public long getMEDICINE_NO() {
		return MEDICINE_NO;
	}

	public void setMEDICINE_NO(long mEDICINE_NO) {
		MEDICINE_NO = mEDICINE_NO;
	}

	public long getPRESCRIPTION_NO() {
		return PRESCRIPTION_NO;
	}

	public void setPRESCRIPTION_NO(long pRESCRIPTION_NO) {
		PRESCRIPTION_NO = pRESCRIPTION_NO;
	}

	public String getMEDICINE_NAME() {
		return MEDICINE_NAME;
	}

	public void setMEDICINE_NAME(String mEDICINE_NAME) {
		MEDICINE_NAME = mEDICINE_NAME;
	}

	public String getMEDICINE_SPEC() {
		return MEDICINE_SPEC;
	}

	public void setMEDICINE_SPEC(String mEDICINE_SPEC) {
		MEDICINE_SPEC = mEDICINE_SPEC;
	}

	public String getMEDICINE_DOSAGE() {
		return MEDICINE_DOSAGE;
	}

	public void setMEDICINE_DOSAGE(String mEDICINE_DOSAGE) {
		MEDICINE_DOSAGE = mEDICINE_DOSAGE;
	}

	public String getFREQUENCY() {
		return FREQUENCY;
	}

	public void setFREQUENCY(String fREQUENCY) {
		FREQUENCY = fREQUENCY;
	}

	public String getWAY() {
		return WAY;
	}

	public void setWAY(String wAY) {
		WAY = wAY;
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

	public String getPRODUCER() {
		return PRODUCER;
	}

	public void setPRODUCER(String pRODUCER) {
		PRODUCER = pRODUCER;
	}
}
