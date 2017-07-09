package com.rambler.healthymanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Comm.CommFunction;
import Comm.CommVariable;
import Comm.CountDownTimerUtils;
import Comm.SMS;
import Comm.Service;
import Comm.SysApplication;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class FindPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText findPhoneNumberEText;
    private EditText findIdentifyCodeEText;
    private EditText findPasswordOneEText;
    private EditText findPasswordTwoEText;

    private Button findIdentifyCodeBtn;
    private Button findSubmitBtn;

    Service serviceVisit = new Service();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
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
            case R.id.findIdentifyCodeBtn:
                identifyCodeBtnClick();
                break;
            case R.id.findSubmitBtn:
                findSubmitBtn.setEnabled(false);    // 设置按钮不可点击
                findSubmitBtn.setBackgroundResource(R.drawable.shape_gray); //设置按钮为灰色
                submitBtnClick();
                findSubmitBtn.setEnabled(true);
                findSubmitBtn.setBackgroundResource(R.drawable.shape); //还原背景色
                break;

            default:
                break;
        }
    }

    //初始化组件
    private void initView() {
        findPhoneNumberEText = (EditText) findViewById(R.id.findPhoneNumber);
        findIdentifyCodeEText = (EditText) findViewById(R.id.findIdentifyCode);
        findPasswordOneEText = (EditText) findViewById(R.id.findPasswordOne);
        findPasswordTwoEText = (EditText) findViewById(R.id.findPasswordTwo);

        findIdentifyCodeBtn = (Button) findViewById(R.id.findIdentifyCodeBtn);
        findSubmitBtn = (Button) findViewById(R.id.findSubmitBtn);
    }

    //初始化事件
    private void initEvent() {

        this.findIdentifyCodeBtn.setOnClickListener(this);
        this.findSubmitBtn.setOnClickListener(this);
    }

    //发送验证码
    private void identifyCodeBtnClick() {
        String phoneNum = findPhoneNumberEText.getText().toString().trim();
        String reg = "[1][358]\\d{9}";
        //判断手机号码是否输入
        if (phoneNum == null || phoneNum.equals("")) {
            Toast.makeText(getApplicationContext(), "请输入手机号码！", Toast.LENGTH_SHORT).show();
            Log.i("FindPasswordActivity", "请输入手机号码！");
            return;
        }
        //手机号码格式判断
        if (!phoneNum.matches(reg)) {
            Toast.makeText(getApplicationContext(), "手机号码格式不正确！", Toast.LENGTH_SHORT).show();
            Log.i("FindPasswordActivity", "手机号码格式不正确！");
            return;
        }
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("phone_number", phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("FindPasswordActivity", "Function identifyCodeBtnClick JSONException.");
        }
        //判断手机号是否已注册
        String verifyPhoneNumExistServiceResult = serviceVisit.toVisitServer(FindPasswordActivity.this,"VerifyPhoneNumExistService", jsonObject.toString());
        if (!"".equals(verifyPhoneNumExistServiceResult)) {
            //解析服务端返回的结果集
            int dataNum = new CommFunction().getDataNum(verifyPhoneNumExistServiceResult);
            if (dataNum <= 0) {
                Toast.makeText(getApplicationContext(), "手机号未注册！", Toast.LENGTH_SHORT).show();
                Log.i("FindPasswordActivity", "手机号【" + phoneNum + "】未注册！");
                return;
            }
        } else {
            Toast.makeText(getApplicationContext(), "操作失败，请重试！", Toast.LENGTH_SHORT).show();
            Log.i("FindPasswordActivity", "手机号【" + phoneNum + "】验证是否注册获取后台数据失败！");
            return;
        }
        //发送验证码
        SMS sms = new SMS();
        boolean flag = sms.toGetCode(phoneNum);
        if (flag) {
            //设置倒计时
            CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(findIdentifyCodeBtn, CommVariable.smsSendCodeAgainTime * 1000, 1000);
            mCountDownTimerUtils.start();

            Toast.makeText(getApplicationContext(), "验证码发送成功！", Toast.LENGTH_SHORT).show();
            Log.i("FindPasswordActivity", "验证码发送成功！");
        } else {
            Toast.makeText(getApplicationContext(), "验证码发送失败！", Toast.LENGTH_SHORT).show();
            Log.i("FindPasswordActivity", "验证码发送失败！");
            return;
        }
    }

    //提交事件
    private void submitBtnClick() {

        //获取输入框内容
        String phoneNum = findPhoneNumberEText.getText().toString().trim();
        String passwordOne = findPasswordOneEText.getText().toString().trim();
        String passwordTwo = findPasswordTwoEText.getText().toString().trim();
        String code = findIdentifyCodeEText.getText().toString().trim();
        //判断是否有信息未填写
        if ("".equals(phoneNum) || "".equals(passwordOne) || "".equals(passwordTwo) || "".equals(code)) {
            Toast.makeText(getApplicationContext(), "有信息未填写！", Toast.LENGTH_SHORT).show();
            return;
        }
        //Base64加密
        String password = Base64.encodeToString(passwordOne.getBytes(), Base64.DEFAULT);
        //判断两次输入的密码是否相等
        if (!passwordOne.equals(passwordTwo)) {
            Toast.makeText(getApplicationContext(), "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
            Log.i("FindPasswordActivity", "两次输入的密码不一致！");
            findPasswordTwoEText.setText("");
            findPasswordOneEText.setText("");
            return;
        }
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("password", password);
            jsonObject.put("phone_number", phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("FindPasswordActivity", "Function submitBtnClick JSONException.");
        }
        //校验验证码
        SMS sms = new SMS();
        String smsString = sms.verifyCode(phoneNum, code);
        //解析数据
        int flag = -1;
        try {
            JSONObject jsonObjectResult = new JSONObject(smsString);
            flag = (Integer) jsonObjectResult.get("flag");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //0：提交成功；1：验证成功；2：验证码过期；3：验证失败
        if (flag == 1) {
            //调用service方法
            String FindPasswordServiceResult = serviceVisit.toVisitServer(FindPasswordActivity.this,"FindPasswordService", jsonObject.toString());

            if (!"".equals(FindPasswordServiceResult)) {
                //解析服务端返回的结果集
                int dataNum = new CommFunction().getDataNum(FindPasswordServiceResult);
                if (dataNum > 0) {
                    Toast.makeText(getApplicationContext(), "重置密码成功！", Toast.LENGTH_SHORT).show();
                    Log.i("FindPasswordActivity", "手机号【" + phoneNum + "】重置密码成功！");
                    //跳转至登录页面
                    Intent intent = new Intent(FindPasswordActivity.this, LogInActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "重置密码失败，请重试！", Toast.LENGTH_SHORT).show();
                Log.i("FindPasswordActivity", "手机号【" + phoneNum + "】重置密码获取后台数据失败！");
            }
        } else if (flag == 2) {
            Toast.makeText(getApplicationContext(), "验证码过期！", Toast.LENGTH_SHORT).show();
            Log.i("FindPasswordActivity", "验证码过期！");
            findIdentifyCodeEText.setText("");
        } else if (flag != 0) {
            Toast.makeText(getApplicationContext(), "验证码验证失败，请重试！", Toast.LENGTH_SHORT).show();
            Log.i("FindPasswordActivity", "验证码验证失败，请重试！");
            findIdentifyCodeEText.setText("");
        }
    }
}
