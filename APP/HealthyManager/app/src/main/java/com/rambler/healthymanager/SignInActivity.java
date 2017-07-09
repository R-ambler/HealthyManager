package com.rambler.healthymanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userNameEText;
    private EditText phoneNumberEText;
    private EditText identifyCodeEText;
    private EditText passwordOneEText;
    private EditText passwordTwoEText;

    private Button identifyCodeBtn;
    private Button submitBtn;

//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (msg.what == 0x123) {
//                Toast.makeText(SignInActivity.this, "注册成功", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(SignInActivity.this, "注册失败", Toast.LENGTH_LONG).show();
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
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
            case R.id.identifyCodeBtn:
                identifyCodeBtnClick();
                break;
            case R.id.submitBtn:
                submitBtn.setEnabled(false);
                submitBtn.setBackgroundResource(R.drawable.shape_gray); //设置按钮为灰色
                submitBtnClick();
                submitBtn.setEnabled(true);
                submitBtn.setBackgroundResource(R.drawable.shape); //还原背景色
                break;

            default:
                break;
        }
    }

    //初始化组件
    private void initView() {
        userNameEText = (EditText) findViewById(R.id.userNameEText);
        phoneNumberEText = (EditText) findViewById(R.id.phoneNumberEText);
        identifyCodeEText = (EditText) findViewById(R.id.identifyCodeEText);
        passwordOneEText = (EditText) findViewById(R.id.passwordOneEText);
        passwordTwoEText = (EditText) findViewById(R.id.passwordTwoEText);

        identifyCodeBtn = (Button) findViewById(R.id.identifyCodeBtn);
        submitBtn = (Button) findViewById(R.id.submitBtn);
    }

    //初始化事件
    private void initEvent() {

        this.identifyCodeBtn.setOnClickListener(this);
        this.submitBtn.setOnClickListener(this);
    }

    //发送验证码
    private void identifyCodeBtnClick() {
        String phoneNum = phoneNumberEText.getText().toString().trim();
        String reg = "[1][358]\\d{9}";
        //判断手机号码是否输入
        if (phoneNum == null || phoneNum.equals("")) {
            Toast.makeText(getApplicationContext(), "请输入手机号码！", Toast.LENGTH_SHORT).show();
            Log.i("SignInActivity", "请输入手机号码！");
            return;
        }
        //手机号码格式判断
        if (!phoneNum.matches(reg)) {
            Toast.makeText(getApplicationContext(), "手机号码格式不正确！", Toast.LENGTH_SHORT).show();
            Log.i("SignInActivity", "手机号码格式不正确！");
            return;
        }
        //发送验证码
        SMS sms = new SMS();
        boolean flag = sms.toGetCode(phoneNum);
        if (flag) {
            //设置倒计时
            CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(identifyCodeBtn, CommVariable.smsSendCodeAgainTime * 1000, 1000);
            mCountDownTimerUtils.start();

            Toast.makeText(getApplicationContext(), "验证码发送成功！", Toast.LENGTH_SHORT).show();
            Log.i("SignInActivity", "验证码发送成功！");
        } else {
            Toast.makeText(getApplicationContext(), "验证码发送失败！", Toast.LENGTH_SHORT).show();
            Log.i("SignInActivity", "验证码发送失败！");
            return;
        }
    }

    //提交事件
    private void submitBtnClick() {

        Service serviceVisit = new Service();
        //获取输入框内容
        String userName = userNameEText.getText().toString().trim();
        String passwordOne = passwordOneEText.getText().toString().trim();
        String passwordTwo = passwordTwoEText.getText().toString().trim();
        String phoneNum = phoneNumberEText.getText().toString().trim();
        String code = identifyCodeEText.getText().toString().trim();
        //Base64加密
        String password = Base64.encodeToString(passwordOne.getBytes(), Base64.DEFAULT);
        //判断是否有信息未填写
        if ("".equals(userName) || "".equals(passwordOne) || "".equals(passwordTwo) || "".equals(phoneNum) || "".equals(code)) {
            Toast.makeText(getApplicationContext(), "有信息未填写！", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断两次输入的密码是否相等
        if (!passwordOne.equals(passwordTwo)) {
            Toast.makeText(getApplicationContext(), "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
            Log.i("SignInActivity", "两次输入的密码不一致！");
            passwordTwoEText.setText("");
            passwordOneEText.setText("");
            return;
        }
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("user_name", userName);
            jsonObject.put("password", password);
            jsonObject.put("phone_number", phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("SignInActivity", "Function submitBtnClick JSONException.");
        }
        //判断手机号是否已注册
        String verifyPhoneNumExistServiceResult = serviceVisit.toVisitServer(SignInActivity.this,"VerifyPhoneNumExistService", jsonObject.toString());
        if (!"".equals(verifyPhoneNumExistServiceResult)) {
            //解析服务端返回的结果集
            int dataNum = new CommFunction().getDataNum(verifyPhoneNumExistServiceResult);
            if (dataNum > 0) {
                Toast.makeText(getApplicationContext(), "手机号已注册！", Toast.LENGTH_SHORT).show();
                Log.i("SignInActivity", "用户【" + userName + "】手机号已注册！");
                return;
            }
        } else {
            Toast.makeText(getApplicationContext(), "注册失败，请重试！", Toast.LENGTH_SHORT).show();
            Log.i("SignInActivity", "用户【" + userName + "】注册失败！");
            return ;
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
            String signinServiceResult = serviceVisit.toVisitServer(SignInActivity.this,"SigninService", jsonObject.toString());

            if (!"".equals(signinServiceResult)) {
                //解析服务端返回的结果集
                int dataNum = new CommFunction().getDataNum(signinServiceResult);
                if (dataNum > 0) {
                    Toast.makeText(getApplicationContext(), "注册成功，请登录！", Toast.LENGTH_SHORT).show();
                    Log.i("SignInActivity", "用户【" + userName + "】注册成功！");
                    //跳转至登录页面
                    Intent intent = new Intent(SignInActivity.this, LogInActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "注册失败，请重试！", Toast.LENGTH_SHORT).show();
                Log.i("SignInActivity", "用户【" + userName + "】注册失败！");
            }
        } else if (flag == 2) {
            Toast.makeText(getApplicationContext(), "验证码过期！", Toast.LENGTH_SHORT).show();
            Log.i("SignInActivity", "验证码过期！");
            identifyCodeEText.setText("");
        } else if (flag != 0) {
            Toast.makeText(getApplicationContext(), "验证码验证失败，请重试！", Toast.LENGTH_SHORT).show();
            Log.i("SignInActivity", "验证码验证失败，请重试！");
            identifyCodeEText.setText("");
        }
    }
    /**
     * 界面销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

