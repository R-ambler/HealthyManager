package com.rambler.healthymanager;

import android.app.Dialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Comm.CommFunction;
import Comm.CommVariable;
import Comm.Service;
import Comm.SysApplication;
import Model.HM_MEDICAL;
import Model.HM_MEDICINE;
import Model.HM_PRESCRIPTION;

public class DisplayPrescriptionActivity extends AppCompatActivity {

    private ListView listView;
    //处方信息
    private TextView prescriptionAbstractTxt;
    private TextView prescriptionHospitalNameTxt;
    private TextView prescriptionInquiryTimeTxt;
    private TextView prescriptionRemakeTxt;
    //药品信息列表
    private ImageView medicine_list_icon;
    private TextView medicine_list_name;
    private TextView medicine_list_frequency;
    private TextView prescription_list_way;
    //修改&删除弹框组件
    private View inflate;
    private Dialog dialog;
    private TextView dialogUpdateText;
    private TextView dialogDeleteText;
    //添加药品按钮
    private Button addMedicineBtn;

    Service serviceVisit = new Service();
    Intent intent = new Intent();
    Gson gson = new Gson();
    HM_PRESCRIPTION prescriptionBean = null;
    HM_MEDICINE medicineBean = null;
    List<HM_MEDICINE> list = null;
    int medicineIconID = R.drawable.medicine_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_display_prescription_list_view);
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
        prescriptionBean = gson.fromJson(jsonString, HM_PRESCRIPTION.class);

        //从后台获取数据
        list = toMedicineData();

        initView();
        listViewClick();
        listView.setAdapter(adapter);
    }

    //初始化组件
    private void initView() {
        listView = (ListView) findViewById(R.id.prescription_display_list_view);

        prescriptionAbstractTxt = (TextView)findViewById(R.id.prescriptionAbstractTxt);
        prescriptionHospitalNameTxt = (TextView)findViewById(R.id.prescriptionHospitalNameTxt);
        prescriptionInquiryTimeTxt = (TextView)findViewById(R.id.prescriptionInquiryTimeTxt);
        prescriptionRemakeTxt = (TextView)findViewById(R.id.prescriptionRemakeTxt);

        addMedicineBtn = (Button) findViewById(R.id.addMedicineBtn);
        //显示处方信息
        prescriptionAbstractTxt.setText(prescriptionBean.getPRESCRIPTION_ABSTRACT());
        prescriptionHospitalNameTxt.setText(prescriptionBean.getHOSPITAL_NAME());
        prescriptionRemakeTxt.setText(prescriptionBean.getREMARK());
        //日期格式转换
        String prescriptionInquiryTime = prescriptionBean.getINQUIRY_TIME().toString();
        String prescriptionInquiryTimeFormat = new CommFunction().formatGMTDate(prescriptionInquiryTime);
        prescriptionInquiryTimeTxt.setText(prescriptionInquiryTimeFormat);

        //添加药品按钮监听
        addMedicineBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //显示数据详情
                intent.setClass(DisplayPrescriptionActivity.this, AddMedicineActivity.class);
                intent.putExtra("jsonString", gson.toJson(prescriptionBean));
                startActivity(intent);
            }
        });
    }

    //listView点击事件
    private void listViewClick() {
        //单击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                medicineBean = (HM_MEDICINE) listView.getItemAtPosition(position);
                //显示数据详情
                intent.setClass(DisplayPrescriptionActivity.this, DisplayMedicineActivity.class);
                intent.putExtra("jsonString", gson.toJson(medicineBean));
                startActivity(intent);
            }
        });
        //长按事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                medicineBean = (HM_MEDICINE) listView.getItemAtPosition(position);
                //修改&删除弹框
                showDialog();
                return true;
            }
        });
    }
    //创建适配器
    private BaseAdapter adapter = new BaseAdapter() {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout linearLayout = null;
            if (convertView != null) {
                linearLayout = (LinearLayout) convertView;
            } else {
                linearLayout = (LinearLayout) LayoutInflater.from(DisplayPrescriptionActivity.this).inflate(R.layout.activity_display_prescription, null);
            }
            medicineBean = getItem(position);
            //初始化
            medicine_list_icon = (ImageView)linearLayout.findViewById(R.id.medicine_list_icon);
            medicine_list_name = (TextView)linearLayout.findViewById(R.id.medicine_list_name);
            medicine_list_frequency = (TextView)linearLayout.findViewById(R.id.medicine_list_frequency);
            prescription_list_way = (TextView)linearLayout.findViewById(R.id.prescription_list_way);
            //设置元素
            medicine_list_icon.setImageResource(medicineIconID);//设置图标
            medicine_list_name.setText(medicineBean.getMEDICINE_NAME());
            medicine_list_frequency.setText(medicineBean.getFREQUENCY());
            prescription_list_way.setText(medicineBean.getWAY());

            return linearLayout;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public HM_MEDICINE getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    };

    //从后台查询数据
    private List<HM_MEDICINE> toMedicineData() {

        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("prescription_no", prescriptionBean.getPRESCRIPTION_NO());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String medicineResultData = serviceVisit.toVisitServer(DisplayPrescriptionActivity.this, "SelectMedicineInfoByPrescriptionNoService", jsonObject.toString());
        //解析服务端返回的结果集
        String jsonResultData = new CommFunction().getJsonResultData(medicineResultData);

        List<HM_MEDICINE> list = gson.fromJson("[" + jsonResultData + "]", new TypeToken<List<HM_MEDICINE>>() {
        }.getType());
        return list;
    }

    //显示弹框
    public void showDialog() {
        //退出弹框
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充弹框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null);
        //添加当前activity到activityList
        SysApplication.getInstance().addActivity(this);
        //初始化控件
        dialogUpdateText = (TextView) inflate.findViewById(R.id.dialogUpdateText);
        dialogDeleteText = (TextView) inflate.findViewById(R.id.dialogDeleteText);
        //修改&删除弹框监听事件
        dialogClickListener();
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        //显示退出弹框
        dialog.show();
    }

    //修改&删除弹框监听事件
    private void dialogClickListener() {
        dialogUpdateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转
                intent.setClass(DisplayPrescriptionActivity.this, UpdateMedicineActivity.class);
                String jsonString = gson.toJson(medicineBean);
                intent.putExtra("jsonString", jsonString);
                startActivity(intent);
            }
        });
        dialogDeleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = deleteMedicineInfo(medicineBean.getMEDICINE_NO());
                if (flag) {
                    toRefresh();
                    Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                    //刷新页面
                } else {
                    Toast.makeText(getApplicationContext(), "删除失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //删除数据
    private boolean deleteMedicineInfo(long medicineNo) {
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("medicine_no", medicineNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String medicineResultData = serviceVisit.toVisitServer(DisplayPrescriptionActivity.this, "DeleteMedicineInfoService", jsonObject.toString());
        //解析服务端返回的结果集
        int dataNum = new CommFunction().getDataNum(medicineResultData);
        if (dataNum == 1) {
            return true;
        }
        return false;
    }

    //更新数据
    private void toRefresh(){
        list = toMedicineData();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toRefresh();
    }
}