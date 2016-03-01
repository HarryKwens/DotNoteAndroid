package com.zhzane.android.dotnoteandroid.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.zhzane.android.dotnoteandroid.DB.Bill;
import com.zhzane.android.dotnoteandroid.DB.DBManager;
import com.zhzane.android.dotnoteandroid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BillActivity extends BaseActivity {

    private ListView listView;      //账单列表
    private SimpleAdapter sim_adapter;      //数据适配器
    private List<Map<String,Object>> dataList;      //数据源
    private Button btnAdd;      //添加账单按钮
    public DBManager mgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //TextView mTitle = (TextView)toolbar.findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //绑定listView控件
        listView = (ListView)findViewById(R.id.listView);
        mgr = new DBManager(this);

        dataList = new ArrayList<Map<String,Object>>();
        //调用设置listView数据
        setListView();

        //点击圆形添加按钮跳转到添加账单页面
        btnAdd = (Button)findViewById(R.id.view_circle_button);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BillActivity.this, Bill_Add_Activity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 重新加载时，先清楚listview中的数据集，再重新填充。
     */
    @Override
    protected void onResume() {
        super.onResume();
        dataList.removeAll(dataList);
        sim_adapter.notifyDataSetChanged();
        listView.setAdapter(sim_adapter);
        setListView();
    }
    //退出时关闭数据库
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mgr.CloseDB();
    }

    /**
     * 填充数据到Listview
     */
    private void setListView(){
        //填充适配器时map的Key
        String[] mapKey = new String[]{
                "money","describe","createTime"
        };
        //填充适配器时对应控件的id
        int[] idList = new int[]{
                R.id.money,
                R.id.describe,
                R.id.createTime
        };
        sim_adapter = new SimpleAdapter(this,getDataList(),R.layout.item,mapKey,idList);
        listView.setAdapter(sim_adapter);
    }

    /**
     * 获取数据源
     * @return 返回数据列表
     */
    private List<Map<String,Object>> getDataList(){
        List<Bill> billList = mgr.queryBill();
        for (Bill bill : billList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("money",bill.Money);
            map.put("describe",bill.Describe);
            map.put("createTime",bill.CreateTime);
            dataList.add(map);
        }
        return dataList;
    }
}
