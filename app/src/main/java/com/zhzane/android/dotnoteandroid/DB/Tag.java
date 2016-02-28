package com.zhzane.android.dotnoteandroid.DB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KWENS on 2016/2/16.
 * 标签实体类。在setter中对数据进行格式验证。
 */
public class Tag {
    public int id;
    public String TagId;
    public String TagName;


    public Tag() {}

    public Tag(int id, String tagId, String tagName) {
        this.id = id;
        TagId = tagId;
        TagName = tagName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        try{
            String regex ="^[A-Za-z0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(tagId);
            boolean rs = matcher.matches();
            if (rs){
                TagId = tagId;
            }
            else{
                Exception e = new Exception("tagId数据格式不正确");
                throw e;
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        try {
            String regex ="^[\\u4e00-\\u9fa5A-Za-z0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(tagName);
            boolean rs = matcher.matches();
            if (rs){
                TagName = tagName;
            }
            else{
                Exception e = new Exception("tagName数据格式不正确");
                throw e;
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
