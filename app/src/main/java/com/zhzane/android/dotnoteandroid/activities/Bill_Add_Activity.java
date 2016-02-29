
package com.zhzane.android.dotnoteandroid.activities;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhzane.android.dotnoteandroid.DB.Bill;
import com.zhzane.android.dotnoteandroid.DB.DBManager;
import com.zhzane.android.dotnoteandroid.R;

import java.util.ArrayList;

public class Bill_Add_Activity extends AppCompatActivity {

    private EditText money;
    private EditText describe;
    private EditText createTime;
    private Button submit;
    public DBManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //初始化控件
        money = (EditText) findViewById(R.id.edtxt_BillAdd_Money);
        describe = (EditText) findViewById(R.id.edtxt_BillAdd_Describe);
        createTime = (EditText) findViewById(R.id.edtxt_BillAdd_CreateTime);
        submit = (Button) findViewById(R.id.btn_BillAdd_Submit);
        mgr = new DBManager(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isAdd =  AddBill();
                if (isAdd){
                    Toast.makeText(Bill_Add_Activity.this,"添加成功",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Bill_Add_Activity.this,"添加失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mgr.CloseDB();
    }

    //测试--添加账单
    private Boolean AddBill(){
        boolean isOk = false;
        Bill bill = new Bill();
        bill.Money = 2000.0;
        bill.CreateTime = "2015-01-01";
        bill.Describe = "123456";
        bill.LastModifiedTime = "2015-01-01";
        bill.ExternalId = "11111";
        bill.TagId = "321";
        bill.UserId = "22222";
        bill.BillNo = "001";
        try {
            mgr.addBill(bill);
            isOk = true;
            return isOk;
        }catch (Exception e){
            Log.d("AddBill","添加账单失败，错误："+ e.getMessage());
        }
        return isOk;
    }
}