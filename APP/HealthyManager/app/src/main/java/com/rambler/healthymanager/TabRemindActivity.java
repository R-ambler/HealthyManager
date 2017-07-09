package com.rambler.healthymanager;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import Comm.SysApplication;

/**
 * Created by Administrator on 06/06 0006.
 */
public class TabRemindActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_remind);
        //添加当前activity到activityList
        SysApplication.getInstance().addActivity(this);
        //直接在main Thread 进行网络操作
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        initView();
        initEvent();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.findIdentifyCodeBtn:
//                identifyCodeBtnClick();
//                break;
//            case R.id.findSubmitBtn:
//                findSubmitBtn.setEnabled(false);    // 设置按钮不可点击
//                findSubmitBtn.setBackgroundResource(R.drawable.shape_gray); //设置按钮为灰色
//                submitBtnClick();
//                findSubmitBtn.setEnabled(true);
//                findSubmitBtn.setBackgroundResource(R.drawable.shape); //还原背景色
//                break;

            default:
                break;
        }
    }

    //初始化组件
    private void initView() {
//        findPhoneNumberEText = (EditText) findViewById(R.id.findPhoneNumber);
    }

    //初始化事件
    private void initEvent() {

//        this.findIdentifyCodeBtn.setOnClickListener(this);
    }
    //提交事件
    private void submitBtnClick() {

    }
}