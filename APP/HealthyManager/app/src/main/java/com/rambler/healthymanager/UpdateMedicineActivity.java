package com.rambler.healthymanager;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import Comm.CommFunction;
import Comm.Service;
import Comm.SysApplication;
import Model.HM_MEDICINE;

public class UpdateMedicineActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView medicineTitleTxt;

    private EditText medicineNameEText;
    private EditText medicineSpecEText;
    private EditText medicineDosageEText;
    private EditText medicineFrequencyEText;
    private EditText medicineProducerEText;
    private EditText medicineRemakeEText;
    private EditText medicineWayEText;

    private Button medicineSubmitBtn;

    Gson gson = new Gson();
    HM_MEDICINE medicineBean = null;
    private String serviceName = "UpdateMedicineInfoService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_medicine);
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
        medicineBean = gson.fromJson(jsonString, HM_MEDICINE.class);

        initView();
        initEvents();
    }

    //初始化控件
    private void initView() {

        medicineTitleTxt = (TextView) this.findViewById(R.id.medicineTitleTxt);

        medicineNameEText = (EditText) this.findViewById(R.id.medicineNameEText);
        medicineSpecEText = (EditText) this.findViewById(R.id.medicineSpecEText);
        medicineDosageEText = (EditText) this.findViewById(R.id.medicineDosageEText);
        medicineFrequencyEText = (EditText) this.findViewById(R.id.medicineFrequencyEText);
        medicineProducerEText = (EditText) this.findViewById(R.id.medicineProducerEText);
        medicineRemakeEText = (EditText) this.findViewById(R.id.medicineRemakeEText);
        medicineWayEText = (EditText) this.findViewById(R.id.medicineWayEText);

        medicineSubmitBtn = (Button) this.findViewById(R.id.medicineSubmitBtn);
        //给控件赋值
        medicineTitleTxt.setText("修改药品");

        medicineNameEText.setText(medicineBean.getMEDICINE_NAME());
        medicineSpecEText.setText(medicineBean.getMEDICINE_SPEC());
        medicineDosageEText.setText(medicineBean.getMEDICINE_DOSAGE());
        medicineFrequencyEText.setText(medicineBean.getFREQUENCY());
        medicineProducerEText.setText(medicineBean.getPRODUCER());
        medicineRemakeEText.setText(medicineBean.getREMARK());
        medicineWayEText.setText(medicineBean.getWAY());
    }

    //初始化事件
    private void initEvents() {
        medicineSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.medicineSubmitBtn:
                submitBtnClick();
                break;

            default:
                break;
        }
    }

    //提交事件
    private void submitBtnClick() {
        Service serviceVisit = new Service();
        //获取输入框内容
        String medicineName = medicineNameEText.getText().toString().trim();
        String medicineSpec = medicineSpecEText.getText().toString().trim();
        String medicineDosage = medicineDosageEText.getText().toString().trim();
        String medicineFrequency = medicineFrequencyEText.getText().toString().trim();
        String medicineProducer = medicineProducerEText.getText().toString().trim();
        String medicineRemake = medicineRemakeEText.getText().toString().trim();
        String medicineWay = medicineWayEText.getText().toString().trim();

        //判断是否有信息未填写
        if ("".equals(medicineName)) {
            Toast.makeText(getApplicationContext(), "药品名称不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("medicine_name", medicineName);
            jsonObject.put("medicine_spec", medicineSpec);
            jsonObject.put("medicine_dosage", medicineDosage);
            jsonObject.put("frequency", medicineFrequency);
            jsonObject.put("way", medicineWay);
//            jsonObject.put("photo_url", );
            jsonObject.put("remark", medicineRemake);
            jsonObject.put("producer", medicineProducer);
            jsonObject.put("medicine_no", medicineBean.getMEDICINE_NO());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("UpdateMedicineActivity", "Function submitBtnClick JSONException.");
        }
        //调用service方法
        String medicineServiceResult = serviceVisit.toVisitServer(UpdateMedicineActivity.this, serviceName, jsonObject.toString());

        if (!"".equals(medicineServiceResult)) {
            //解析服务端返回的结果集
            int dataNum = new CommFunction().getDataNum(medicineServiceResult);
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
}