package com.rambler.healthymanager;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import Comm.CommFunction;
import Comm.CommVariable;
import Comm.SMS;
import Comm.Service;
import Comm.SysApplication;
import Model.HM_SEARCH;

public class UpdateSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView searchTitleTxt;
    private EditText searchNameEText;
    private EditText searchURLEText;
    private EditText searchAbstractEText;
    private Button searchSubmitBtn;

    Gson gson = new Gson();
    HM_SEARCH searchBean = null;
    private int flag = 0;   //0：新增数据；1：修改数据
    private String serviceName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_search);
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
            searchBean = gson.fromJson(jsonString, HM_SEARCH.class);
            serviceName = "UpdateSearchInfoService";
        } else {
            serviceName = "InsertSearchInfoService";
        }

        initView();
        initEvents();
    }

    //初始化控件
    private void initView() {
        searchTitleTxt = (TextView) this.findViewById(R.id.searchTitleTxt);
        searchNameEText = (EditText) this.findViewById(R.id.searchNameEText);
        searchURLEText = (EditText) this.findViewById(R.id.searchURLEText);
        searchAbstractEText = (EditText) this.findViewById(R.id.searchAbstractEText);
        searchSubmitBtn = (Button) this.findViewById(R.id.searchSubmitBtn);
        //给控件赋值
        if (flag == 1) {
            searchTitleTxt.setText("修改网址");
            searchNameEText.setText(searchBean.getSEARCH_NAME());
            searchURLEText.setText(searchBean.getSEARCH_URL());
            searchAbstractEText.setText(searchBean.getSEARCH_ABSTRACT());
        } else {
            searchTitleTxt.setText("添加网址");
        }
    }

    //初始化事件
    private void initEvents() {
        searchSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchSubmitBtn:
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
        String searchName = searchNameEText.getText().toString().trim();
        String searchURL = searchURLEText.getText().toString().trim();
        String searchAbstract = searchAbstractEText.getText().toString().trim();
        //判断是否有信息未填写
        if ("".equals(searchName) || "".equals(searchURL)) {
            Toast.makeText(getApplicationContext(), "网站名称和网址不允许为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("search_name", searchName);
            jsonObject.put("search_url", searchURL);
            jsonObject.put("search_abstract", searchAbstract);
            jsonObject.put("user_no", CommVariable.userInfo.getUserNo());
            if (flag == 1) {
                jsonObject.put("search_no", searchBean.getSEARCH_NO());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("UpdateSearchActivity", "Function submitBtnClick JSONException.");
        }
        //调用service方法
        String SearchServiceResult = serviceVisit.toVisitServer(UpdateSearchActivity.this, serviceName, jsonObject.toString());

        if (!"".equals(SearchServiceResult)) {
            //解析服务端返回的结果集
            int dataNum = new CommFunction().getDataNum(SearchServiceResult);
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
