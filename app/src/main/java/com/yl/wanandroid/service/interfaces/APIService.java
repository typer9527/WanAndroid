package com.yl.wanandroid.service.interfaces;

import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.BannerData;
import com.yl.wanandroid.service.dto.EmptyData;
import com.youth.banner.Banner;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("article/list/{index}/json")
    Observable<HttpResponse<Articles>> getArticleList(@Path("index") int index);

    @FormUrlEncoded
    @POST("user/login")
    Observable<HttpResponse<EmptyData>> loginByPsw(@Field("username") String username,
                                                   @Field("password") String psw);

    @FormUrlEncoded
    @POST("user/register")
    Observable<HttpResponse<EmptyData>> register(@Field("username") String username,
                                                 @Field("password") String psw,
                                                 @Field("repassword") String confirmPsw);

    @GET("banner/json")
    Observable<HttpResponse<List<BannerData>>> getBanners();

    @GET("project/list/{index}/json")
    Observable<HttpResponse<Articles>> getProjectList(@Path("index") int index, @Query("cid") int id);
}
