package com.zhzane.android.dotnoteandroid.DB;

/**
 * Created by KWENS on 2016/2/16.
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
}
