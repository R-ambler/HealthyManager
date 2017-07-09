package com.rambler.healthymanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.List;
import java.util.Locale;

import Comm.CommFunction;
import Comm.CommVariable;
import Comm.Service;
import Comm.SysApplication;
import Model.HM_MEDICAL;
import Model.HM_MEDICINE;
import Model.HM_PRESCRIPTION;

public class UpdatePrescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView prescriptionTitleTxt;

    private EditText prescriptionAbstractEText;
    private EditText prescriptionHospitalNameEText;
    private EditText prescriptionInquiryTimeEText;
    private EditText prescriptionRemakeEText;

    private Button prescriptionSubmitBtn;

    Service serviceVisit = new Service();
    Intent intent = new Intent();
    Gson gson = new Gson();
    HM_PRESCRIPTION prescriptionBean = null;
    HM_MEDICINE medicineBean = null;
    private int flag = 0;   //0：新增数据；1：修改数据
    List<HM_MEDICINE> list = null;
    private String serviceName = "";
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_prescription);
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
        if (bundle != null) {
            flag = 1;
            String jsonString = bundle.getString("jsonString");
            prescriptionBean = gson.fromJson(jsonString, HM_PRESCRIPTION.class);
            serviceName = "UpdatePrescriptionInfoService";
        } else {
            serviceName = "InsertPrescriptionInfoService";
        }

        initView();
        initEvents();
    }

    //初始化控件
    private void initView() {
        prescriptionTitleTxt =(TextView)this.findViewById(R.id.prescriptionTitleTxt);

        prescriptionAbstractEText = (EditText) this.findViewById(R.id.prescriptionAbstractEText);
        prescriptionHospitalNameEText = (EditText) this.findViewById(R.id.prescriptionHospitalNameEText);
        prescriptionInquiryTimeEText = (EditText) this.findViewById(R.id.prescriptionInquiryTimeEText);
        prescriptionRemakeEText = (EditText) this.findViewById(R.id.prescriptionRemakeEText);

        prescriptionSubmitBtn = (Button) this.findViewById(R.id.prescriptionSubmitBtn);
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
            prescriptionTitleTxt.setText("修改处方");
            //给控件赋值
            //日期格式转换
            String prescriptionInquiryTime = prescriptionBean.getINQUIRY_TIME().toString();
            String prescriptionInquiryTimeFormat = new CommFunction().formatGMTDate(prescriptionInquiryTime);
            prescriptionInquiryTimeEText.setText(prescriptionInquiryTimeFormat);

            prescriptionAbstractEText.setText(prescriptionBean.getPRESCRIPTION_ABSTRACT());
            prescriptionHospitalNameEText.setText(prescriptionBean.getHOSPITAL_NAME());
            prescriptionRemakeEText.setText(prescriptionBean.getREMARK());
        }else{
            prescriptionTitleTxt.setText("添加处方");
        }
    }

    //初始化事件
    private void initEvents() {
        prescriptionSubmitBtn.setOnClickListener(this);
        prescriptionInquiryTimeEText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prescriptionSubmitBtn:
                submitBtnClick();
                break;
            case R.id.prescriptionInquiryTimeEText:
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
        String prescriptionInquiryTime = prescriptionInquiryTimeEText.getText().toString().trim();

        String prescriptionHospitalName = prescriptionHospitalNameEText.getText().toString().trim();
        String prescriptionRemake = prescriptionRemakeEText.getText().toString().trim();
        String prescriptionAbstract = prescriptionAbstractEText.getText().toString().trim();

        //判断是否有信息未填写
        if ("".equals(prescriptionAbstract) || "".equals(prescriptionHospitalName) || "".equals(prescriptionInquiryTime)) {
            Toast.makeText(getApplicationContext(), "除备注外，其他信息不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("hospital_name", prescriptionHospitalName);
            jsonObject.put("inquiry_time", prescriptionInquiryTime);
            jsonObject.put("remark", prescriptionRemake);
            jsonObject.put("prescription_abstract", prescriptionAbstract);

            jsonObject.put("user_no", CommVariable.userInfo.getUserNo());
            if (flag == 1) {
                jsonObject.put("prescription_no", prescriptionBean.getPRESCRIPTION_NO());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("UpdateMedicalActivity", "Function submitBtnClick JSONException.");
        }
        //调用service方法
        String prescriptionServiceResult = serviceVisit.toVisitServer(UpdatePrescriptionActivity.this, serviceName, jsonObject.toString());

        if (!"".equals(prescriptionServiceResult)) {
            //解析服务端返回的结果集
            int dataNum = new CommFunction().getDataNum(prescriptionServiceResult);
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
                UpdatePrescriptionActivity.this.year = year;
                month = monthOfYear;
                day = dayOfMonth;
                //更新日期
                updateDate();
            }
            //当DatePickerDialog关闭时，更新日期显示
            private void updateDate() {
                //在TextView上显示日期
                prescriptionInquiryTimeEText.setText(year + "-" + (month + 1) + "-" + day);
            }
        } , year, month, day);
        datePickerDialog.show();//显示DatePickerDialog组件
    }
}