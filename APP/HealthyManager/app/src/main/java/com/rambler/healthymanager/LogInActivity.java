package com.rambler.healthymanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Comm.CommFunction;
import Comm.CommVariable;
import Comm.Service;
import Comm.SysApplication;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LogInActivity.class.getSimpleName();

    private EditText phoneNumberEText;
    private EditText passwordEText;

    private TextView findPasswordTView;
    private TextView signinTView;
//    private TextView nologinTView;

    private Button loginBtn;

    private CheckBox logCBox;

    //先定义
    SharedPreferences sp = null;

//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (msg.what == 0x123) {
//                Toast.makeText(LogInActivity.this, "登录成功", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(LogInActivity.this, "登录失败", Toast.LENGTH_LONG).show();
//            }
//        }
//    };
    /**
     * Activity 创建，做一些组件初始化，加载数据，此时界面不可见
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //添加当前activity到activityList
        SysApplication.getInstance().addActivity(this);
        //直接在main Thread 进行网络操作
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        // SharedPreference存储登录信息
        sp = this.getSharedPreferences("HMUserInfo", Context.MODE_PRIVATE);
        initView();
        initEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                loginBtn.setEnabled(false);
                loginBtn.setBackgroundResource(R.drawable.shape_gray); //设置按钮为灰色
                loginBtnClick();
                loginBtn.setEnabled(true);
                loginBtn.setBackgroundResource(R.drawable.shape); //还原背景色
                break;
            case R.id.findPassword:
                Intent findPasswordIntent = new Intent(LogInActivity.this, FindPasswordActivity.class);
                startActivity(findPasswordIntent);
                break;
            case R.id.signin:
                Intent signinIntent = new Intent(LogInActivity.this, SignInActivity.class);
                startActivity(signinIntent);
                break;
//            case R.id.nologin:
//                Intent nologinIntent = new Intent(LogInActivity.this, MainActivity.class);
//                startActivity(nologinIntent);
//                break;

            default:
                break;
        }
    }

    //初始化组件
    private void initView(){
        //EditText
        phoneNumberEText = (EditText) findViewById(R.id.logPhoneNum);
        passwordEText = (EditText) findViewById(R.id.logPassword);
        //TextView
        findPasswordTView = (TextView) this.findViewById(R.id.findPassword);
        signinTView = (TextView) this.findViewById(R.id.signin);
//        nologinTView = (TextView) this.findViewById(R.id.nologin);
        //Button
        loginBtn = (Button) this.findViewById(R.id.loginBtn);
        //CheckBox
        logCBox = (CheckBox) this.findViewById(R.id.logCBox);
    }
    //初始化事件
    private void initEvent() {

        this.loginBtn.setOnClickListener(this);
        this.findPasswordTView.setOnClickListener(this);
        this.signinTView.setOnClickListener(this);
        this.loginBtn.setOnClickListener(this);

        if (sp.getBoolean("checkboxBoolean", false))
        {
            phoneNumberEText.setText(sp.getString("phoneNumber", null));
            passwordEText.setText(sp.getString("password", null));
            logCBox.setChecked(true);
        }

    }
    //登录按钮事件
    private void loginBtnClick() {

        Service serviceVisit = new Service();
        String userName="";
        long userNo = -1;
        String lastLogTime = "";
        //获取输入框内容
        String phoneNum = phoneNumberEText.getText().toString().trim();
        String password = passwordEText.getText().toString().trim();
        //判断是否有信息未填写
        if ("".equals(phoneNum) || "".equals(password)) {
            Toast.makeText(getApplicationContext(), "有信息未填写！", Toast.LENGTH_SHORT).show();
            return;
        }
        //手机号码格式判断
        String reg = "[1][358]\\d{9}";
        if (!phoneNum.matches(reg)) {
            Toast.makeText(getApplicationContext(), "手机号码有误！", Toast.LENGTH_SHORT).show();
            return;
        }
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("phone_number", phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("LogInActivity", "Function loginBtnClick JSONException.");
        }
        //判断手机号是否已注册
        String verifyPhoneNumExistServiceResult = serviceVisit.toVisitServer(LogInActivity.this,"VerifyPhoneNumExistService", jsonObject.toString());
        if (!"".equals(verifyPhoneNumExistServiceResult)) {
            //解析服务端返回的结果集
            int dataNum = new CommFunction().getDataNum(verifyPhoneNumExistServiceResult);
            if (dataNum <= 0) {
                Toast.makeText(getApplicationContext(), "账号未注册！", Toast.LENGTH_SHORT).show();
                Log.i("LogInActivity", "手机号【" + phoneNum + "】账号未注册！");
                return;
            }
        } else {
            Toast.makeText(getApplicationContext(), "登录失败，请重试！", Toast.LENGTH_SHORT).show();
            Log.i("LogInActivity", "手机号【" + phoneNum + "】登录失败！");
            return;
        }
        //调用service方法
        String loginServiceResult = serviceVisit.toVisitServer(LogInActivity.this,"LoginService", jsonObject.toString());

        if (!"".equals(loginServiceResult)) {
            //解析服务端返回的结果集
            String jsonResultData = new CommFunction().getJsonResultData(loginServiceResult);
            String passwordResultDecoded = null;
            String lastLogTimeFormat = "";
            try {
                JSONObject jsonObjectResult = new JSONObject(jsonResultData);
                userNo = (int)jsonObjectResult.get("USER_NO");
                userName = (String)jsonObjectResult.get("USER_NAME");
                String passwordResult = (String)jsonObjectResult.get("PASSWORD");
                //Base64解密
                passwordResultDecoded =new String(Base64.decode(passwordResult,Base64.DEFAULT));
                lastLogTime = (String)jsonObjectResult.get("LAST_LOG_TIME");
                lastLogTimeFormat = new CommFunction().formatCSTTime(lastLogTime);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //判断密码是否正确
            if (password.equals(passwordResultDecoded)) {
                Toast.makeText(getApplicationContext(), "上次登录时间："+lastLogTimeFormat, Toast.LENGTH_SHORT).show();
                //保存登录信息
                CommVariable.userInfo.setVal(userNo,userName,phoneNum,password,lastLogTime);
                Log.i("LogInActivity", "手机号【" + phoneNum + "】登录成功！");
                //更新上次登录时间
                String updateLastLogTimeServiceResult = serviceVisit.toVisitServer(LogInActivity.this,"UpdateLastLogTimeService", jsonObject.toString());
                if ((updateLastLogTimeServiceResult == null)||("".equals(updateLastLogTimeServiceResult))) {
                    Toast.makeText(getApplicationContext(), "登录失败，请重试！", Toast.LENGTH_SHORT).show();
                    Log.i("LogInActivity", "手机号【" + phoneNum + "】修改上次登录时间失败——获取服务端数据为空！");
                    return;
                }
                //解析服务端返回的结果集
                int dataNum = new CommFunction().getDataNum(updateLastLogTimeServiceResult);
                if (dataNum <= 0) {
                    Toast.makeText(getApplicationContext(), "登录失败，请重试！", Toast.LENGTH_SHORT).show();
                    Log.i("LogInActivity", "手机号【" + phoneNum + "】修改上次登录时间失败！");
                    return;
                }
                //记住密码
                boolean checkBoxLogin = logCBox.isChecked();
                if(checkBoxLogin){  //按钮被选中，下次进入时会显示账号和密码
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("phoneNumber", phoneNum);
                    editor.putString("password", password);
                    editor.putBoolean("checkboxBoolean", true);
                    editor.commit();
                //按钮被选中，清空账号和密码，下次进入时不显示账号和密码
                }else{
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("phoneNumber", null);
                    editor.putString("password", null);
                    editor.putBoolean("checkboxBoolean", false);
                    editor.commit();
                }
                //跳转至主页面
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "密码错误！", Toast.LENGTH_SHORT).show();
                Log.i("LogInActivity", "手机号【" + phoneNum + "】登录密码错误！");
                return;
            }
        } else {
            Toast.makeText(getApplicationContext(), "，请重试！", Toast.LENGTH_SHORT).show();
            Log.i("LogInActivity", "手机号【" + phoneNum + "】登录失败！");
            return;
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

