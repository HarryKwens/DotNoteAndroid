
package com.zhzane.android.dotnoteandroid.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.print.PrintAttributes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zhzane.android.dotnoteandroid.DB.Bill;
import com.zhzane.android.dotnoteandroid.DB.DBManager;
import com.zhzane.android.dotnoteandroid.DB.Tag;
import com.zhzane.android.dotnoteandroid.DB.User;
import com.zhzane.android.dotnoteandroid.R;
import com.zhzane.android.dotnoteandroid.component.awesome.AwesomeButton;
import com.zhzane.android.dotnoteandroid.component.awesome.AwesomeTextView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

public class BillAddActivity extends BaseActivity {

    private EditText money;
    private EditText describe;
    private AwesomeButton createTime;
    private Button buttonIsMoneyAdd;
    private Button submit;
    private boolean isMoneyAdd;
    private boolean isTagClick;
    private boolean isDisAllClick;
    private String txtMoney;
    private String txtDescribe;
    private String txtCreateTime;
    private SimpleDateFormat formatter;
    private List<Tag> tagList;
    private List<String> tagIdList;
    private LinearLayout layout;
//    private User Currentuser;   //当前用户

    /**
     * 时间选择器参数
     */
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMin;
    private String mm;      //辅助月份参数
    private String dd;      //辅助日期参数
    private String hh;
    private String min;
    private Date curDate;   //当前日期

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
        isTagClick = false;
        isDisAllClick = false;

        //初始化控件
        money = (EditText) findViewById(R.id.edtxt_BillAdd_Money);
        describe = (EditText) findViewById(R.id.edtxt_BillAdd_Describe);
        createTime = (AwesomeButton) findViewById(R.id.bill_Add_button_CreateTime);
        buttonIsMoneyAdd = (Button) findViewById(R.id.bill_add_button_is_add);
        submit = (Button) findViewById(R.id.btn_BillAdd_Submit);
        layout = (LinearLayout) findViewById(R.id.bill_add_layout);
        mgr = new DBManager(this);

        //点击创建时间文本编辑框弹出时间选择器
        createTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        createTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideIM(v);
                    showDialog(0);
                }
            }
        });
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

                boolean b = (txtMoney != null && !txtMoney.equals("") && txtCreateTime != null &&
                        !txtCreateTime.equals("")) ? true : false;
                if (b) {
                    Boolean isAdd = AddBill();
                    if (isAdd) {
                        //添加成功后，返回列表页面，并刷新列表页面。
                        Toast.makeText(BillAddActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(BillAddActivity.this, "添加失败", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(BillAddActivity.this, "金额和创建时间不能为空，请输入。", Toast.LENGTH_LONG).show();
                }
            }
        });
        //获取当前用户信息
//        getCurrentuser();

        // 设置当前时间
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        curDate = new Date(System.currentTimeMillis());
        createTime.setText(formatter.format(curDate));
        //获取当前年月日。
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        //tag标签TagId集合
        tagIdList = new ArrayList<String>();
        //tag标签集合
        tagList = new ArrayList<Tag>();
        getTagList();
        setTagButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mgr.CloseDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isDisAllClick = false;
        layout.removeAllViews();
        getTagList();
        setTagButton();
    }

    /**
     * 设置标签按钮方法。使用代码动态生成控件。
     */
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
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //每个按钮的点击事件，默认为白底黑字，当点击过后按钮反色并且将TagId添加到集合中。
                            String id = String.valueOf(tv.getId());
                            if (!tagIdList.contains(id)) {
                                tagIdList.add(id);
                                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                                tv.setBackground(getResources().getDrawable(R.drawable.button_bill_radius));
                            } else {
                                tagIdList.remove(id);
                                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                                tv.setBackground(getResources().getDrawable(R.drawable.button_bill_tag_circle));
                            }
                        }
                    });
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
                //添加标签按钮，生成在所有标签后面。
                if (mCount == tagList.size()) {
                    tv.setId(-1);
                    tv.setText(R.string.awesome_plus);
                    linearLayout.addView(tv);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BillAddActivity.this, TagAddActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                mCount++;
            }
            layout.addView(linearLayout, linearParams);
        }
    }

    /**
     * 获取Tag标签数据，当数据库不存在数据时，添加几条默认数据。
     */
    private void getTagList() {
        tagList = mgr.queryTag();
        if (tagList.size() < 1) {
            List<String> tagStr = new ArrayList<String>();
            tagStr.add(getResources().getString(R.string.awesome_tag_bar));
            tagStr.add(getResources().getString(R.string.awesome_tag_book));
            tagStr.add(getResources().getString(R.string.awesome_tag_coffee));
            tagStr.add(getResources().getString(R.string.awesome_tag_film));
            for (int i = 0; i < tagStr.size(); i++) {
                Tag tag = new Tag();
                tag.TagId = i;
                tag.TagName = tagStr.get(i);
                tagList.add(tag);
            }
            mgr.addTag(tagList);
            getTagList();
        }
    }

//    /**
//     * 获取当前用户信息。
//     *
//     * @return
//     */
//    public void getCurrentuser() {
//        List<User> currentUser = mgr.queryCurrentUser();
//        if (currentUser.size() > 0) {
//            Currentuser = currentUser.get(0);
//        }
//    }


    //测试--添加账单
    private Boolean AddBill() {

        //获取标签TagId,以逗号分隔进行拼接。
        StringBuilder sb = new StringBuilder();
        for (String tagIdStr : tagIdList) {
            sb.append(tagIdStr);
            sb.append(",");
        }

        boolean isOk = false;
        Bill bill = new Bill();
        bill.Money = Double.valueOf(money.getText().toString());
        bill.CreateTime = createTime.getText().toString();
        bill.Describe = describe.getText().toString();
        bill.LastModifiedTime = createTime.getText().toString();
        bill.ExternalId = "11111";
        bill.TagId = sb.toString();
        bill.UserId = (Integer.toString(mgr.currentUser.UserId) == null || Integer.toString(mgr.currentUser.UserId) == "")
                ? "" : Integer.toString(mgr.currentUser.UserId);
        try {
            mgr.addBill(bill);
            isOk = true;
            return isOk;
        } catch (Exception e) {
            Log.d("AddBill", "添加账单失败，错误：" + e.getMessage());
        }
        return isOk;
    }

    /**
     * 日期选择器
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    if (monthOfYear <= 8) {
                        mMonth = monthOfYear + 1;
                        mm = "0" + mMonth;
                    } else {
                        mMonth = monthOfYear + 1;
                        mm = String.valueOf(mMonth);
                    }
                    if (dayOfMonth <= 9) {
                        mDay = dayOfMonth;
                        dd = "0" + mDay;
                    } else {
                        mDay = dayOfMonth;
                        dd = String.valueOf(mDay);
                    }
                    mDay = dayOfMonth;
                    showDialog(1);
                }
            };
    /**
     * 时间选择器
     */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (hourOfDay <= 9) {
                        hh = "0" + hourOfDay;
                    } else {
                        hh = String.valueOf(hourOfDay);
                    }
                    if (minute <= 9) {
                        min = "0" + minute;
                    } else {
                        min = String.valueOf(minute);
                    }
                    mHour = hourOfDay;
                    mMin = minute;
                    createTime.setText(String.valueOf(mYear) + "-" + mm + "-" + dd + " " + hh + ":" + min);
                }
            };

    //日期，时间选择器界面显示
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
            case 1:
                return new TimePickerDialog(this, mTimeSetListener, mHour, mMin, true);
        }
        return null;
    }

    // 隐藏手机键盘
    private void hideIM(View edt) {
        try {
            InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            IBinder windowToken = edt.getWindowToken();
            if (windowToken != null) {
                im.hideSoftInputFromWindow(windowToken, 0);
            }
        } catch (Exception e) {
        }
    }
}