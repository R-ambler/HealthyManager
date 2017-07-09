package Comm;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rambler.healthymanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 公共方法类
 */
public class CommFunction {

    //解析服务端返回的数据获得 dataNum
    public int getDataNum(String result) {
        int dataNum = -1;
        try {
            JSONArray jsonResultArray = new JSONArray(result);
            JSONObject jsonResultDataNum = (JSONObject) jsonResultArray.get(0);
            dataNum = (Integer) jsonResultDataNum.get("dataNum");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("CommFunction", "Function getDataNum JSONException.");
        }

        return dataNum;
    }

    //解析服务端返回的数据，获得 JsonResultData
    public String getJsonResultData(String result) {
        String jsonResultData = null;
        try {
            JSONArray jsonResultArray = new JSONArray(result);
            JSONObject jsonResultString = (JSONObject) jsonResultArray.get(1);
            jsonResultData = (String) jsonResultString.get("JsonResultData");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("CommFunction", "Function getJsonResultData JSONException.");
        }

        return jsonResultData.substring(1, jsonResultData.length() - 1);
    }

    //格式化CST时间（yyyy/MM/dd HH:mm:ss）
    public String formatCSTTime(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa", Locale.US);
        Date date = null;
        try {
            date = (Date) sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatDateStr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);

        return formatDateStr;
    }

    //格式化GMT时间（yyyy-MM-dd）
    public String formatGMTDate(String dateStr) {
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 格林尼治标准时间 yyyy", Locale.US);
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 格林尼治标准时间+0800 yyyy", Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT+08:00' yyyy", Locale.US);
        Date date = null;
        try {
            date = (Date) sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatDateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);

        return formatDateStr;
    }

    //退出弹窗
    public static void showDialog(Context context) {
        //退出弹框
        final Dialog dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充弹框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_exit, null);
        //初始化控件
        TextView exitYText = (TextView) inflate.findViewById(R.id.exitYText);
        TextView exitNText = (TextView) inflate.findViewById(R.id.exitNText);
        exitYText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysApplication.getInstance().exit();
            }
        });
        exitNText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

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
}
