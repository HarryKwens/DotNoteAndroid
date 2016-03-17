package com.zhzane.android.dotnoteandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhzane.android.dotnoteandroid.DB.DBManager;
import com.zhzane.android.dotnoteandroid.DB.Tag;
import com.zhzane.android.dotnoteandroid.R;

import java.util.ArrayList;
import java.util.List;

public class TagAddActivity extends BaseActivity {

    //控件
    private EditText tagName;
    private EditText describe;
    private Button submit;
    private DBManager mgr;

    //控件文本
    private String txtTagName;
    private String txtDescribe;

    private List<Tag> tagList;
    private int maxTagId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_add);

        tagName = (EditText) findViewById(R.id.tag_add_editText_tagName);
        describe = (EditText) findViewById(R.id.tag_add_editText_describe);
        submit = (Button) findViewById(R.id.tag_add_button_submit);

        mgr = new DBManager(this);
        tagList = new ArrayList<Tag>();
        getMaxTagId();      //获取数据库中tag的个数

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtTagName = tagName.getText().toString();
                txtDescribe = describe.getText().toString();
                //判断名称输入是否为空
                boolean isNull = (txtTagName != null && !txtTagName.equals("")) ? true : false;
                //判断标签名称是否为空，再判断是否添加成功。
                if (isNull){
                    boolean isOk = isAddTag(txtTagName,txtDescribe);
                    if (isOk){
                        Toast.makeText(TagAddActivity.this, "添加标签成功", Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(TagAddActivity.this, "添加标签失败", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(TagAddActivity.this, "标签名称不能为空，请输入。", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getMaxTagId(){
        tagList = mgr.queryTag();
        maxTagId = tagList.size();
    }

    private boolean isAddTag(String txtTagName,String txtDescribe){
        boolean isAdd = false;

        Tag tag = new Tag();
        tag.TagId = maxTagId;
        tag.TagName = txtTagName;
        tag.UseNum = 0;
        tag.Describe = txtDescribe;
        tag.mac = mgr.currentUser.MAC;

        try{
            mgr.addTag(tag);
            isAdd = true;
        }catch (Exception e){
            Log.d("AddTag", "添加标签失败，错误：" + e.getMessage());
        }
        return isAdd;
    }
}
