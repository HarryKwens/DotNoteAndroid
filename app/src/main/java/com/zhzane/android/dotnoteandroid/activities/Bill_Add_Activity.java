
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
    private String txtMoney;
    private String txtDescribe;
    private String txtCreateTime;
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
                //获取控件数据
                txtMoney = money.getText().toString();
                txtCreateTime = createTime.getText().toString();
                txtDescribe = describe.getText().toString();

                boolean b = (txtMoney != null && !txtMoney.equals("")&&txtCreateTime != null &&
                        !txtCreateTime.equals(""))?true:false;
                if (b) {
                    Boolean isAdd = AddBill();
                    if (isAdd) {
                        //添加成功后，返回列表页面，并刷新列表页面。
                        Toast.makeText(Bill_Add_Activity.this, "添加成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(Bill_Add_Activity.this, "添加失败", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(Bill_Add_Activity.this,"金额和创建时间不能为空，请输入。",Toast.LENGTH_LONG).show();
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
        bill.Money = Double.valueOf(money.getText().toString());
        bill.CreateTime = createTime.getText().toString();
        bill.Describe = describe.getText().toString();
        bill.LastModifiedTime = createTime.getText().toString();
        bill.ExternalId = "11111";
        bill.TagId = "123";
        bill.UserId = "22222";
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