package com.rambler.healthymanager;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import Comm.CommFunction;
import Comm.SysApplication;
import Model.HM_MEDICINE;
import Model.HM_PRESCRIPTION;

public class DisplayMedicineActivity extends AppCompatActivity {

    private TextView medicineNameTxt;
    private TextView medicineSpecTxt;
    private TextView medicineDosageTxt;
    private TextView medicineFrequencyNameTxt;
    private TextView medicineWayTxt;
    private TextView medicineProducerTxt;
    private TextView medicineRemakeTxt;

    Gson gson = new Gson();
    HM_MEDICINE medicineBean = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medicine);
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
    }

    //初始化组件
    private void initView() {

        medicineNameTxt = (TextView) this.findViewById(R.id.medicineNameTxt);
        medicineSpecTxt = (TextView) this.findViewById(R.id.medicineSpecTxt);
        medicineDosageTxt = (TextView) this.findViewById(R.id.medicineDosageTxt);
        medicineFrequencyNameTxt = (TextView) this.findViewById(R.id.medicineFrequencyNameTxt);
        medicineWayTxt = (TextView) this.findViewById(R.id.medicineWayTxt);
        medicineProducerTxt = (TextView) this.findViewById(R.id.medicineProducerTxt);
        medicineRemakeTxt = (TextView) this.findViewById(R.id.medicineRemakeTxt);
        //赋值
        medicineNameTxt.setText(medicineBean.getMEDICINE_NAME());
        medicineSpecTxt.setText(medicineBean.getMEDICINE_SPEC());
        medicineDosageTxt.setText(medicineBean.getMEDICINE_DOSAGE());
        medicineFrequencyNameTxt.setText(medicineBean.getFREQUENCY());
        medicineWayTxt.setText(medicineBean.getWAY());
        medicineProducerTxt.setText(medicineBean.getPRODUCER());
        medicineRemakeTxt.setText(medicineBean.getREMARK());
    }
}
