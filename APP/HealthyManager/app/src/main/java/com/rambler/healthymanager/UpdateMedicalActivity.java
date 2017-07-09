package com.rambler.healthymanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Comm.CommFunction;
import Comm.CommVariable;
import Comm.Service;
import Comm.SysApplication;
import Model.HM_MEDICAL;

public class UpdateMedicalActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView medicalTitleTxt;

    private EditText medicalInquiryTimeEText;
    private EditText medicalHospitalNameEText;
    private EditText medicalDeptNameEText;
    private EditText medicalDoctorNameEText;
    private EditText medicalMainSuitEText;
    private EditText medicalMedicalHistoryEText;
    private EditText medicalPhysicalExaminationEText;
    private EditText medicalDiagnoseEText;
    private EditText medicalSuggestEText;
    private EditText medicalRemarkEText;

    private Button medicalSubmitBtn;

    Gson gson = new Gson();
    HM_MEDICAL medicalBean = null;
    private int flag = 0;   //0：新增数据；1：修改数据
    private String serviceName = "";
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_medical);
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
        //判断是添加还是修改
        if (bundle != null) {
            flag = 1;
            String jsonString = bundle.getString("jsonString");
            medicalBean = gson.fromJson(jsonString, HM_MEDICAL.class);
            serviceName = "UpdateMedicalInfoService";
        } else {
            serviceName = "InsertMedicalInfoService";
        }

        initView();
        initEvents();
    }

    //初始化控件
    private void initView() {
        medicalTitleTxt = (TextView) this.findViewById(R.id.medicalTitleTxt);

        medicalInquiryTimeEText = (EditText) this.findViewById(R.id.medicalInquiryTimeEText);
        medicalHospitalNameEText = (EditText) this.findViewById(R.id.medicalHospitalNameEText);
        medicalDeptNameEText = (EditText) this.findViewById(R.id.medicalDeptNameEText);
        medicalDoctorNameEText = (EditText) this.findViewById(R.id.medicalDoctorNameEText);
        medicalMainSuitEText = (EditText) this.findViewById(R.id.medicalMainSuitEText);
        medicalMedicalHistoryEText = (EditText) this.findViewById(R.id.medicalMedicalHistoryEText);
        medicalPhysicalExaminationEText = (EditText) this.findViewById(R.id.medicalPhysicalExaminationEText);
        medicalDiagnoseEText = (EditText) this.findViewById(R.id.medicalDiagnoseEText);
        medicalSuggestEText = (EditText) this.findViewById(R.id.medicalSuggestEText);
        medicalRemarkEText = (EditText) this.findViewById(R.id.medicalRemarkEText);

        medicalSubmitBtn = (Button) this.findViewById(R.id.medicalSubmitBtn);
        // 获得日历对象
        Calendar c = Calendar.getInstance(Locale.CHINA);
        // 获取当前年份
        year = c.get(Calendar.YEAR);
        // 获取当前月份
        month = c.get(Calendar.MONTH);
        // 获取当前月份的天数
        day = c.get(Calendar.DAY_OF_MONTH);

        //给控件赋值
        if (flag == 1) {
            medicalTitleTxt.setText("修改病历");
            //日期格式转换
            String medicalInquiryTime = medicalBean.getINQUIRY_TIME().toString();
            String medicalInquiryTimeFormat = new CommFunction().formatGMTDate(medicalInquiryTime);
            medicalInquiryTimeEText.setText(medicalInquiryTimeFormat);

            medicalHospitalNameEText.setText(medicalBean.getHOSPITAL_NAME());
            medicalDeptNameEText.setText(medicalBean.getDEPT_NAME());
            medicalDoctorNameEText.setText(medicalBean.getDOCTOR_NAME());
            medicalMainSuitEText.setText(medicalBean.getMAIN_SUIT());
            medicalMedicalHistoryEText.setText(medicalBean.getMEDICAL_HISTORY());
            medicalPhysicalExaminationEText.setText(medicalBean.getPHYSICAL_EXAMINATION());
            medicalDiagnoseEText.setText(medicalBean.getDIAGNOSE());
            medicalSuggestEText.setText(medicalBean.getSUGGEST());
            medicalRemarkEText.setText(medicalBean.getREMARK());
        } else {
            medicalTitleTxt.setText("添加病历");
        }
    }

    //初始化事件
    private void initEvents() {
        medicalSubmitBtn.setOnClickListener(this);
        medicalInquiryTimeEText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.medicalSubmitBtn:
                submitBtnClick();
                break;
            case R.id.medicalInquiryTimeEText:
                displayCalendar();
                break;

            default:
                break;
        }
    }

    //提交事件
    private void submitBtnClick() {
        Service serviceVisit = new Service();
        //获取输入框内容
        String medicalInquiryTime = medicalInquiryTimeEText.getText().toString().trim();

        String medicalHospitalName = medicalHospitalNameEText.getText().toString().trim();
        String medicalDeptName = medicalDeptNameEText.getText().toString().trim();
        String medicalDoctorName = medicalDoctorNameEText.getText().toString().trim();
        String medicalMainSuit = medicalMainSuitEText.getText().toString().trim();
        String medicalMedicalHistory = medicalMedicalHistoryEText.getText().toString().trim();
        String medicalPhysicalExamination = medicalPhysicalExaminationEText.getText().toString().trim();
        String medicalDiagnose = medicalDiagnoseEText.getText().toString().trim();
        String medicalSuggest = medicalSuggestEText.getText().toString().trim();
        String medicalRemark = medicalRemarkEText.getText().toString().trim();

        //判断是否有信息未填写
        if ("".equals(medicalMainSuit) || "".equals(medicalDiagnose) || "".equals(medicalInquiryTime)) {
            Toast.makeText(getApplicationContext(), "主诉、诊断、就诊日期不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("inquiry_time", medicalInquiryTime);
            jsonObject.put("hospital_name", medicalHospitalName);
            jsonObject.put("dept_name", medicalDeptName);
            jsonObject.put("doctor_name", medicalDoctorName);
            jsonObject.put("main_suit", medicalMainSuit);
            jsonObject.put("medical_history", medicalMedicalHistory);
            jsonObject.put("physical_examination", medicalPhysicalExamination);
            jsonObject.put("diagnose", medicalDiagnose);
            jsonObject.put("suggest", medicalSuggest);
            jsonObject.put("remark", medicalRemark);

            jsonObject.put("user_no", CommVariable.userInfo.getUserNo());
            if (flag == 1) {
                jsonObject.put("medical_no", medicalBean.getMEDICAL_NO());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("UpdateMedicalActivity", "Function submitBtnClick JSONException.");
        }
        //调用service方法
        String medicalServiceResult = serviceVisit.toVisitServer(UpdateMedicalActivity.this, serviceName, jsonObject.toString());

        if (!"".equals(medicalServiceResult)) {
            //解析服务端返回的结果集
            int dataNum = new CommFunction().getDataNum(medicalServiceResult);
            if (dataNum > 0) {
                Toast.makeText(getApplicationContext(), "操作成功！", Toast.LENGTH_SHORT).show();
                //结束当前Activity，返回上一Activity
                this.finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "操作失败，请重试！", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //选择日期弹框
    private void displayCalendar() {
        //创建DatePickerDialog对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
                UpdateMedicalActivity.this.year = year;
                month = monthOfYear;
                day = dayOfMonth;
                //更新日期
                updateDate();
            }
            //当DatePickerDialog关闭时，更新日期显示
            private void updateDate() {
                //在TextView上显示日期
                medicalInquiryTimeEText.setText(year + "-" + (month + 1) + "-" + day);
            }
        } , year, month, day);
        datePickerDialog.show();//显示DatePickerDialog组件
    }
}
