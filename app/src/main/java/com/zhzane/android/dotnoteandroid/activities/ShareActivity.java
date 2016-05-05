package com.zhzane.android.dotnoteandroid.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhzane.android.dotnoteandroid.DB.DBManager;
import com.zhzane.android.dotnoteandroid.R;

import java.io.File;

public class ShareActivity extends AppCompatActivity {

    private TextView btnBack;
    private TextView chooseFile;
    private Button inprot;
    private Button exprot;
    private DBManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mgr = new DBManager(this);

        chooseFile = (TextView) findViewById(R.id.sharing_textview_chooseFile);
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooeser();
            }
        });

        setButton();
        importFile();   //导入文件
        exportFile();   //导出文件
    }

    /**
     * 导入数据文件
     */
    private void importFile(){

        inprot = (Button) findViewById(R.id.sharing_button_inport);

        inprot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = chooseFile.getText().toString();
                String errMsg ="";
                errMsg = mgr.getJSON(filePath);
                if (errMsg.equals("")) {
                    Toast.makeText(ShareActivity.this, "导入成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(ShareActivity.this,errMsg,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 导出数据文件
     */
    private void exportFile(){
        exprot = (Button) findViewById(R.id.sharing_button_exprot);
        exprot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean isOk = mgr.toJSON(String.valueOf(mgr.currentUser.UserId));
                    if (isOk) {
                        Toast.makeText(ShareActivity.this, "导出成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ShareActivity.this,"导出失败",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //文件选择器
    private void showFileChooeser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), 1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",  Toast.LENGTH_SHORT).show();
        }
    }
    //选择文件后，将文件路径返回并填入文本框中
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String path = FileUtils.getPath(this,uri);
            chooseFile.setText(path);
        }
    }
    public static class FileUtils {
        //返回文件路径
        public static String getPath(Context context, Uri uri) {

            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = { "_data" };
                Cursor cursor = null;

                try {
                    cursor = context.getContentResolver().query(uri, projection,null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow("_data");
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                } catch (Exception e) {
                    //
                }
            }

            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }
    }

    private void setButton() {
        btnBack = (TextView) findViewById(R.id.nav_btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
