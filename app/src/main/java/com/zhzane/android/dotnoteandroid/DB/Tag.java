package com.zhzane.android.dotnoteandroid.DB;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KWENS on 2016/2/16.
 * 标签实体类。在setter中对数据进行格式验证。
 * @id  自增列id。
 * @Tagid 标签Id 格式为3位数字组成，“123，321，312”类型。
 * @TagName 标签名称 格式为中英文数字皆可。
 */
public class Tag {
    public int _id;
    public String TagId;
    public String TagName;
    public ArrayList<String> IdList;

    public Tag() {
    }

    public Tag(int id, String tagId, String tagName) {
        this._id = id;
        TagId = tagId;
        TagName = tagName;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        String regex = "^[0-9]{3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tagId);
        boolean rs = matcher.matches();
        if (rs) {
            IdList.add(tagId);  //设置每个TagId时把Id加入IdList保存起来。便于后续操作。
            TagId = tagId;
        }
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        String regex = "^[\\u4e00-\\u9fa5A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tagName);
        boolean rs = matcher.matches();
        if (rs) {
            TagName = tagName;
        }
    }

    public ArrayList<String> getIdList() {
        return IdList;
    }
}
