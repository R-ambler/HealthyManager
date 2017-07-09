package com.rambler.healthymanager;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

public class AddSearchActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView icon;
    private TextView name;

    Intent intent = new Intent();
    List<HM_SEARCH> list=null;
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
        list=toSearchData();

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchBean = (HM_SEARCH) listView.getItemAtPosition(position);
                //调用webservice
                intent.setClass(AddSearchActivity.this, WebViewActivity.class);
                intent.putExtra("url",searchBean.getSEARCH_URL());
                startActivity(intent);
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
                linearLayout = (LinearLayout) LayoutInflater.from(AddSearchActivity.this).inflate(R.layout.tab_search, null);
            }
            searchBean = getItem(position);
            //初始化
            icon = (ImageView) linearLayout.findViewById(R.id.search_list_icon);
            name = (TextView) linearLayout.findViewById(R.id.search_list_name);
            //设置元素
            icon.setImageResource(searchIconID);//设置图标
            name.setText(searchBean.getSEARCH_NAME());

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
    private List<HM_SEARCH> toSearchData(){

        Service serviceVisit = new Service();
        //组装数据
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("user_no", CommVariable.userInfo.getUserNo());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("LogInActivity", "Function loginBtnClick JSONException.");
        }
        String searchResultData = serviceVisit.toVisitServer(AddSearchActivity.this,"SelectSearchInfoService", jsonObject.toString());
        //解析服务端返回的结果集
        String jsonResultData = new CommFunction().getJsonResultData(searchResultData);

        Gson gson = new Gson();

        List<HM_SEARCH> list = gson.fromJson("["+jsonResultData+"]",new TypeToken<List<HM_SEARCH>>(){}.getType());
        return list;
    }

}
