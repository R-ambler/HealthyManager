package com.rambler.healthymanager;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import Comm.CommFunction;
import Comm.SysApplication;
import Model.HM_MEDICAL;

public class DisplayMedicalActivity extends AppCompatActivity {

    private TextView medicalInquiryTimeTxt;
    private TextView medicalHospitalNameTxt;
    private TextView medicalDeptNameTxt;
    private TextView medicalDoctorNameTxt;
    private TextView medicalMainSuitTxt;
    private TextView medicalMedicalHistoryTxt;
    private TextView medicalPhysicalExaminationTxt;
    private TextView medicalDiagnoseTxt;
    private TextView medicalSuggestTxt;
    private TextView medicalRemakeTxt;

    Gson gson = new Gson();
    HM_MEDICAL medicalBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medical);
        //添加当前activity到activityList
        SysApplication.getInstance().addActivity(this);
        //直接在main Thread 进行网络操作
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        //接收数据
        Bundle bundle = this.getIntent().getExtras();
        String jsonString = bundle.getString("jsonString");
        medicalBean = gson.fromJson(jsonString, HM_MEDICAL.class);

        initView();
    }

    //初始化组件
    private void initView() {

        medicalInquiryTimeTxt = (TextView) this.findViewById(R.id.medicalInquiryTimeTxt);
        medicalHospitalNameTxt = (TextView) this.findViewById(R.id.medicalHospitalNameTxt);
        medicalDeptNameTxt = (TextView) this.findViewById(R.id.medicalDeptNameTxt);
        medicalDoctorNameTxt = (TextView) this.findViewById(R.id.medicalDoctorNameTxt);
        medicalMainSuitTxt = (TextView) this.findViewById(R.id.medicalMainSuitTxt);
        medicalMedicalHistoryTxt = (TextView) this.findViewById(R.id.medicalMedicalHistoryTxt);
        medicalPhysicalExaminationTxt = (TextView) this.findViewById(R.id.medicalPhysicalExaminationTxt);
        medicalDiagnoseTxt = (TextView) this.findViewById(R.id.medicalDiagnoseTxt);
        medicalSuggestTxt = (TextView) this.findViewById(R.id.medicalSuggestTxt);
        medicalRemakeTxt = (TextView) this.findViewById(R.id.medicalRemakeTxt);
        //赋值
        //日期格式转换
        String medicalInquiryTime = medicalBean.getINQUIRY_TIME().toString();
        String medicalInquiryTimeFormat = new CommFunction().formatGMTDate(medicalInquiryTime);
        medicalInquiryTimeTxt.setText(medicalInquiryTimeFormat);

        medicalHospitalNameTxt.setText(medicalBean.getHOSPITAL_NAME());
        medicalDeptNameTxt.setText(medicalBean.getDEPT_NAME());
        medicalDoctorNameTxt.setText(medicalBean.getDOCTOR_NAME());
        medicalMainSuitTxt.setText(medicalBean.getMAIN_SUIT());
        medicalMedicalHistoryTxt.setText(medicalBean.getMEDICAL_HISTORY());
        medicalPhysicalExaminationTxt.setText(medicalBean.getPHYSICAL_EXAMINATION());
        medicalDiagnoseTxt.setText(medicalBean.getDIAGNOSE());
        medicalSuggestTxt.setText(medicalBean.getSUGGEST());
        medicalRemakeTxt.setText(medicalBean.getREMARK());
    }
}
