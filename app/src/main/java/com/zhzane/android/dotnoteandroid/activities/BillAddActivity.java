
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BillAddActivity extends BaseActivity {

    private EditText money;
    private EditText describe;
    private EditText createTime;
    private Button buttonIsMoneyAdd;
    private Button submit;
    private boolean isMoneyAdd;
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

        //初始化状态
        isMoneyAdd = false; //默认为支出

        //初始化控件
        money = (EditText) findViewById(R.id.edtxt_BillAdd_Money);
        describe = (EditText) findViewById(R.id.edtxt_BillAdd_Describe);
        createTime = (EditText) findViewById(R.id.edtxt_BillAdd_CreateTime);
        buttonIsMoneyAdd = (Button) findViewById(R.id.bill_add_button_is_add);
        submit = (Button) findViewById(R.id.btn_BillAdd_Submit);
        mgr = new DBManager(this);

        //支出/收入按钮
        buttonIsMoneyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoneyAdd) {
                    //当前为收入的情况，变为支出
                    isMoneyAdd = false;

                    buttonIsMoneyAdd.setText(R.string.awesome_minus);
                    buttonIsMoneyAdd.setTextColor(getResources().getColor(R.color.colorMoneyDecrease));
                    money.setTextColor(getResources().getColor(R.color.colorMoneyDecrease));
                } else {
                    //当前为支出的情况，变为收入
                    isMoneyAdd = true;

                    buttonIsMoneyAdd.setText(R.string.awesome_plus);
                    buttonIsMoneyAdd.setTextColor(getResources().getColor(R.color.colorMoneyIncrease));
                    money.setTextColor(getResources().getColor(R.color.colorMoneyIncrease));
                }
            }
        });

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
                        Toast.makeText(BillAddActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(BillAddActivity.this, "添加失败", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(BillAddActivity.this,"金额和创建时间不能为空，请输入。",Toast.LENGTH_LONG).show();
                }
            }
        });

        // 时间选择窗口
        // 设置当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        createTime.setText(formatter.format(new Date(System.currentTimeMillis())));
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