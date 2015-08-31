package com.mobileme.inhouse_android.api;

/**
 * Interface describe RestApi on server side
 * Created by User on 27.08.2015.
 */
public interface IRestApi {

    /**
     * Read on need start preferences
     */
    public HSRespPrefs getPrefs();

    /**
     * Try register user in system by phone number
     * @param requestRegister
     * @return
     */
    public HSRespSuccess register(HSRequestRegister requestRegister);

    /**
     * Login user by phone and password
     */
    public HSRespSuccess login(HSRequestAuth requestAuth);

    /**
     * Get profile
     */
    public HSRespProfile getProfile(HSRequestAuth requestAuth);

    /**
     * Update profile
     */
    public HSRespSuccess updateProfile(HSRequestProfile requestProfile);

    ///////////////////////////////////////////////////////////////////////
    /**
     * Add request
     */
    public HSRespSuccess addRequest(HSRequestRequest requestRequest);
    /**
     * Modyfy request
     */
    public HSRespSuccess updateRequest(HSRequestRequest requestRequest);
    /**
     * Delete request
     */
    public HSRespSuccess deleteRequest(HSRequestRequest requestRequest);
    /**
     * Get users requests
     */
    public HSRespRequestsList getRequestsList(HSRequestAuth requestAuth);

    //////////////////////////////////////////////////////////////////////////////////////
    /**
     * News
     */
    public HSRespNewsList getNewsList(HSRequestAuth requestAuth);

    /**
     * Get one article
     * @param requestArticleInfo
     */
    public HSFullArticleInfo getArticle(HSArticleInfo requestArticleInfo);



}
