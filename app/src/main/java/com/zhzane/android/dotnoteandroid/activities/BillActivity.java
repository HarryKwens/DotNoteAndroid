package com.zhzane.android.dotnoteandroid.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

        dataList = new ArrayList<Map<String,Object>>();
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
        //点击圆形添加按钮跳转到添加账单页面
        btnAdd = (Button)findViewById(R.id.view_circle_button);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BillActivity.this,Bill_Add_Activity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 测试——获取数据源
     * @return
     */
    private List<Map<String,Object>> getDataList(){
        for (int i= 0;i<20;i++){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("money",10000);
            map.put("describe","我真的是太牛逼了"+i);
            map.put("createTime",df.format(new Date()));
            dataList.add(map);
        }
        return dataList;
    }
}
