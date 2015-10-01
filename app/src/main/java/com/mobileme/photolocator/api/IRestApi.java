package com.mobileme.photolocator.api;


import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Interface describe RestApi on server side
 * Created by User on 27.08.2015.
 */
public interface IRestApi {

    /**
     * Read on need start preferences
     */
    @GET("restapi/getprefs")
    public Call<HSRespPrefs> getPrefs();

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


    @POST("restapi/addphoto")
    public Call<HSRespSuccess> addPhoto(@Body HSPhoto photo);


}
