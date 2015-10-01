package com.mobileme.photolocator.api;

import java.io.Serializable;

/**
 * Created by User on 31.08.2015.
 */
public class HSArticleInfo implements Serializable {
    private int idx;
    private String title;
    private long createDate;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
