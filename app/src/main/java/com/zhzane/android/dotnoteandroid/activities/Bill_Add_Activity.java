package com.zhzane.android.dotnoteandroid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhzane.android.dotnoteandroid.R;

public class Bill_Add_Activity extends AppCompatActivity {

    private EditText money;
    private EditText describe;
    private EditText createTime;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        money = (EditText)findViewById(R.id.edtxt_BillAdd_Money);
        describe = (EditText)findViewById(R.id.edtxt_BillAdd_Describe);
        createTime = (EditText)findViewById(R.id.edtxt_BillAdd_CreateTime);
        submit = (Button)findViewById(R.id.btn_BillAdd_Submit);
    }

}
