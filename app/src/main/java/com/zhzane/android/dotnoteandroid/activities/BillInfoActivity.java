package com.zhzane.android.dotnoteandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhzane.android.dotnoteandroid.DB.DBManager;
import com.zhzane.android.dotnoteandroid.DB.Tag;
import com.zhzane.android.dotnoteandroid.R;
import com.zhzane.android.dotnoteandroid.component.awesome.AwesomeTextView;

import java.util.ArrayList;
import java.util.List;

public class BillInfoActivity extends AppCompatActivity {

    private TextView billInfoMoney;
    private Button billInfoCreateTime;
    private TextView billInfoDescribe;
    private TextView billInfoTag;
    private DBManager mgr;
    private List<Tag> tagList;
    private LinearLayout layout;
    private boolean isDisAllClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);

        layout = (LinearLayout) findViewById(R.id.bill_info_layout);
        billInfoMoney = (TextView) findViewById(R.id.bill_info_tv_Money);
        billInfoCreateTime = (Button) findViewById(R.id.bill_info_button_CreateTime);
        billInfoDescribe = (TextView) findViewById(R.id.bill_info_tv_Describe);
        mgr = new DBManager(this);
        tagList = new ArrayList<Tag>();
        isDisAllClick = false;

        Bundle bundle = this.getIntent().getExtras();
        String money = bundle.getString("money").toString();
        String createTime = bundle.getString("createTime").toString();
        String describe = bundle.getString("describe").toString();
        String tag = bundle.getString("tag").toString();

        String[] taglst = tag.split(",");

        for (String tagid : taglst){
            Tag tagEntity = new Tag();
            tagEntity = mgr.queryTagByWhere(tagid);
            tagList.add(tagEntity);
        }

        billInfoMoney.setText(money);
        billInfoCreateTime.setText(createTime);
        billInfoDescribe.setText(describe);

        setTagButton();
    }

    private void setTagButton() {

        double LinRow = 1;      //记录需要创建标签的总行数，每行最多5个标签。
        int mCount = 0;         //记录生成标签的个数。
        /**
         * 当读取出来的标签数据条数小于5时，默认为1行。
         * 当读取出来的数据条数大于5时，先判断显示全部标签的按钮的状态，默认为fasle,即为没按下。
         * 此时再判断条数是否为5的倍数，如果不是，则默认为两行。
         * 当显示全部的按钮处于按下的状态，则进行行数计算。
         * 如果数据条数为5的倍数，则增加1行，给“添加标签”按钮提供容器。
         */
        if (!isDisAllClick) {
            if (tagList.size() >= 5) {
                LinRow = 2;
            }
        } else {
            LinRow = Math.ceil(tagList.size() / 5.0);
            if (tagList.size() % 5 == 0) {
                LinRow += 1;
            }
        }

        /**
         * 循环生成控件，外层循环生成LinearLayout，生成的个数由LinRow决定。每个LinearLayout中有5个标签
         * 内层循环生成标签控件。
         */
        for (int i = 0; i < LinRow; i++) {
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            );
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setId(i + 10);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 10, 0, 0);
            linearLayout.setLayoutParams(layoutParams);

            for (int j = 0; j < 5; j++) {
                final AwesomeTextView tv = new AwesomeTextView(this);
                tv.setBackground(getResources().getDrawable(R.drawable.button_bill_tag_circle));
                tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                LinearLayout.LayoutParams layoutParamsTxt = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParamsTxt.setMargins(40, 0, 0, 0);
                tv.setLayoutParams(layoutParamsTxt);
                //当数据集的大小大于用于计数的mCount则添加一个按钮。
                if (tagList.size() > mCount) {
                    tv.setId(tagList.get(mCount).TagId);
                    tv.setText(tagList.get(mCount).TagName);

                    //点击全部显示按钮特别处理。当状态为false时执行以下判断。
                    if (!isDisAllClick) {
                        //只有个数大于9个的时候才需要全部显示按钮。将最后一个按钮替换成全部显示按钮
                        if (tagList.size() > 9 && mCount == 9) {
                            tv.setId(Integer.valueOf(-2));
                            tv.setText(R.string.awesome_tag_showAll);

                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //点击全部显示，先移除全部标签，在重新生成。此时点击状态变为true。
                                    isDisAllClick = true;
                                    layout.removeAllViews();
                                    setTagButton();
                                }
                            });
                        }
                    }
                    linearLayout.addView(tv);
                }

                mCount++;
            }
            layout.addView(linearLayout, linearParams);
        }
    }


}
