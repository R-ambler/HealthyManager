package com.rambler.healthymanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import Comm.CommFunction;
import Comm.CommVariable;
import Comm.Service;
import Comm.SysApplication;
import Model.HM_SEARCH;

/**
 * Created by Administrator on 06/06 0006.
 */
public class TabSearchActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView search_list_icon;
    private TextView search_list_name;
    private TextView search_list_abstract;
    //修改&删除弹框组件
    private View inflate;
    private Dialog dialog;
    private TextView dialogUpdateText;
    private TextView dialogDeleteText;

    Service serviceVisit = new Service();
    Intent intent = new Intent();
    Gson gson = new Gson();
    List<HM_SEARCH> list = null;
    HM_SEARCH searchBean = null;
    int searchIconID = R.drawable.search_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_view);
        //添加当前activity到activityList
        SysApplication.getInstance().addActivity(this);
        //直接在main Thread 进行网络操作
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        //从后台获取数据
        list = toSearchData();

        initView();
        listViewClick();
        listView.setAdapter(adapter);
    }

    //初始化组件
    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
    }

    //listView点击事件
    private void listViewClick() {
        //单击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchBean = (HM_SEARCH) listView.getItemAtPosition(position);
                //调用webservice
                intent.setClass(TabSearchActivity.this, WebViewActivity.class);
                intent.putExtra("url", searchBean.getSEARCH_URL());
                startActivity(intent);
            }
        });
        //长按事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                searchBean = (HM_SEARCH) listView.getItemAtPosition(position);
                //判断是否为当前用户添加的数据
                if(searchBean.getUSER_NO()==CommVariable.userInfo.getUserNo()){
                    showDialog();
                }
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
                linearLayout = (LinearLayout) LayoutInflater.from(TabSearchActivity.this).inflate(R.layout.tab_search, null);
            }
            searchBean = getItem(position);
            //初始化
            search_list_icon = (ImageView) linearLayout.findViewById(R.id.search_list_icon);
            search_list_name = (TextView) linearLayout.findViewById(R.id.search_list_name);
            search_list_abstract = (TextView) linearLayout.findViewById(R.id.search_list_abstract);
            //设置元素
            search_list_icon.setImageResource(searchIconID);//设置图标
            search_list_name.setText(searchBean.getSEARCH_NAME());
            search_list_abstract.setText(searchBean.getSEARCH_ABSTRACT());
            //设置系统数据的颜色
            if(searchBean.getUSER_NO() == 0){
                search_list_name.setTextColor(getResources().getColor(R.color.colorTheme));
                search_list_abstract.setTextColor(getResources().getColor(R.color.colorTheme));
            }

            return linearLayout;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public HM_SEARCH getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    };

    //从后台查询数据
    private List<HM_SEARCH> toSearchData() {

        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("user_no", CommVariable.userInfo.getUserNo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String searchResultData = serviceVisit.toVisitServer(TabSearchActivity.this, "SelectSearchInfoService", jsonObject.toString());
        //解析服务端返回的结果集
        String jsonResultData = new CommFunction().getJsonResultData(searchResultData);

        List<HM_SEARCH> list = gson.fromJson("[" + jsonResultData + "]", new TypeToken<List<HM_SEARCH>>() {
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
                intent.setClass(TabSearchActivity.this, UpdateSearchActivity.class);
                String jsonString = gson.toJson(searchBean);
                intent.putExtra("jsonString", jsonString);
                startActivity(intent);
            }
        });
        dialogDeleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=deleteSearchInfo(searchBean.getSEARCH_NO());
                if(flag){
                    toRefresh();
                    Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                    Log.i("TabSearchActivity", "search数据" + searchBean.getSEARCH_NO() + "】删除成功！");
                }else {
                    Toast.makeText(getApplicationContext(), "删除失败，请重试！", Toast.LENGTH_SHORT).show();
                    Log.i("TabSearchActivity", "search数据" + searchBean.getSEARCH_NO() + "】删除失败！");
                }
            }
        });
    }
    //删除数据
    private boolean deleteSearchInfo(int searchNo){
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("search_no", searchNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String searchResultData = serviceVisit.toVisitServer(TabSearchActivity.this, "DeleteSearchInfoService", jsonObject.toString());
        //解析服务端返回的结果集
        int dataNum = new CommFunction().getDataNum(searchResultData);
        if(dataNum==1){
            return true;
        }
        return false;
    }

    //更新数据
    private void toRefresh(){
        list = toSearchData();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toRefresh();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //显示退出弹框
            new CommFunction().showDialog(TabSearchActivity.this);
            return true;
        }
        return false;
    }
}