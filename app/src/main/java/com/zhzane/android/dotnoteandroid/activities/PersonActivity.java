package com.zhzane.android.dotnoteandroid.activities;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhzane.android.dotnoteandroid.DB.DBManager;
import com.zhzane.android.dotnoteandroid.DB.User;
import com.zhzane.android.dotnoteandroid.R;
import com.zhzane.android.dotnoteandroid.fragments.UserEditDialogFragment;

import java.util.List;

public class PersonActivity extends BaseActivity implements UserEditDialogFragment.UserEditListener{

    private TextView btnBack;
    private TextView btnEdit;
    private TextView textMac;
    private TextView textUser;
    private DBManager mgr;
    private List<User> userList;
    private User CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        mgr = new DBManager(this);
        CurrentUser = new User();

        btnBack = (TextView) findViewById(R.id.btn_nav_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final UserEditDialogFragment dialog = new UserEditDialogFragment();
        btnEdit = (TextView) findViewById(R.id.btn_nav_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(getFragmentManager(),"userEditDialog");
            }
        });

        textMac = (TextView) findViewById(R.id.text_mac);
        textMac.setText(getLocalMacAddress());

        textUser = (TextView) findViewById(R.id.text_user);

        userList = mgr.queryUser();
        initUser();
    }
    //初始化用户，判断用户是否存在，如果不存在则插入数据库。
    public void initUser(){
        boolean isExsisUser = false;
        for (User u : userList) {
            isExsisUser = u.MAC.equals(textMac.getText().toString())? true : false;
            if (isExsisUser){
                CurrentUser.UserId = 1;
                CurrentUser.UserName = u.UserName;
                CurrentUser.RelatedUserId = u.RelatedUserId;
                CurrentUser.MAC = u.MAC;
                CurrentUser.TotalMoney = u.TotalMoney;
                textUser.setText(u.UserName);
            }
        }
        if (isExsisUser == false) {
            User user = new User();
            user.UserId = 1;
            user.UserName = textUser.getText().toString();
            user.MAC = textMac.getText().toString();
            user.TotalMoney = 0.0;
            user.RelatedUserId = "";
            try {
                mgr.addUser(user);
            } catch (Exception e) {
                Log.i("AddUser", "插入用户失败，错误：" + e.getMessage());
            }
        }
    }

    public String getLocalMacAddress() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    @Override
    public void onUserEditComplete(String userName){
        textUser.setText(userName);
        CurrentUser.UserName = userName;
        try {
            mgr.updateUser(CurrentUser);
        }catch (Exception e){
            Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }

}
