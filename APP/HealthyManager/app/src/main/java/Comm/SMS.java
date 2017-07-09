package Comm;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Administrator on 06/01 0001.
 */
public class SMS {

    public boolean toGetCode(String phoneNum) {
        SMS sms = new SMS();
        String code = "";
        boolean result = false;
        //获得验证码
        String smsGetCodeString = sms.getCode(phoneNum);
        try {
            JSONObject jsonObject = new JSONObject(smsGetCodeString);
            String flag = (String) jsonObject.get("flag");
            if ("0".equals(flag)) {
                code = (String) jsonObject.get("code");
                result = true;
            } else {
                Log.i("SMS", "获取验证码错误，响应码为：" + code);
                result = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送验证码
        String smsSetCodeString = sms.getSend(phoneNum, code);
        try {
            JSONObject jsonObject = new JSONObject(smsSetCodeString);
            String responseCode = (String) jsonObject.get("responseCode");
            if (!"0".equals(responseCode)) {
                Log.i("SMS", "发送验证码错误，响应码为：" + code);
                result = false;
            } else {

                result = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 验证码短信发送，GET方式
     */
    public String getSend(String phoneNum, String code) {
        String result = "";
        try {
            String content = URLEncoder.encode(CommVariable.getNoteContent(code),"UTF-8");
            String path = CommVariable.smsGroupSendURL + "?srcSeqId=123&account=" + CommVariable.smsAccount + "&password=" + CommVariable.smsPassword + "&mobile=" + phoneNum + "&content=" + content;

            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            InputStream inStream = conn.getInputStream();

            byte[] data = readInputStream(inStream);
            result = new String(data, "UTF-8");

            Log.i("SMS", "Function getSend 返回结果集为：" + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取随机码，GET方式
     */
    public String getCode(String phoneNum) {
        String result = "";
        try {
            String path = CommVariable.smsGetURL + "?account=" + CommVariable.smsAccount + "&password=" + CommVariable.smsPassword + "&mobile=" + phoneNum + "&expireTime=" + CommVariable.smsEffectiveTime + "&length=" + CommVariable.smsCodeLength;

            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            InputStream inStream = conn.getInputStream();

            byte[] data = readInputStream(inStream);
            result = new String(data, "UTF-8");

            Log.i("SMS", "Function getCode 返回结果集为：" + result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //校验验证码
    public String verifyCode(String phoneNum, String code) {
        String result = "";
        try {
            String path = CommVariable.smsVerifyURL + "?mobile=" + phoneNum + "&code=" + code;

            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            InputStream inStream = conn.getInputStream();

            byte[] data = readInputStream(inStream);
            result = new String(data, "UTF-8");

            Log.i("SMS", "Function verifyCode 返回结果集为：" + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送短信的具体方法
     *
     * @param url
     * @param parameter
     * @return
     */
    public static String post(String url, String parameter, String type) {
        URL postUrl;
        String res = "";
        try {
            postUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(type);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(parameter);
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                res = res + line;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 从输入流中读取数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }
}
