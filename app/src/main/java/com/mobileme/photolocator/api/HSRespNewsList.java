package com.mobileme.photolocator.api;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 31.08.2015.
 */
public class HSRespNewsList implements Serializable {
    private List<HSArticleInfo> articleInfoList;

    public List<HSArticleInfo> getArticleInfoList() {
        return articleInfoList;
    }

    public void setArticleInfoList(List<HSArticleInfo> articleInfoList) {
        this.articleInfoList = articleInfoList;
    }
}
