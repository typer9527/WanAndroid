package com.yl.wanandroid.service.interfaces;

import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.BannerData;
import com.yl.wanandroid.service.dto.EmptyData;
import com.yl.wanandroid.service.dto.ProjectCategory;

import java.util.List;

import io.reactivex.Observable;
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

    @GET("project/tree/json")
    Observable<HttpResponse<List<ProjectCategory>>> getProjectCategory();

    @GET("user/logout/json")
    Observable<HttpResponse<EmptyData>> signOut();

    @GET("lg/collect/list/{index}/json")
    Observable<HttpResponse<Articles>> getCollectedArticles(@Path("index") int index);

    @POST("lg/collect/{id}/json")
    Observable<HttpResponse<EmptyData>> collectArticle(@Path("id") int id);

    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<HttpResponse<EmptyData>> revokeCollectArticle(
            @Path("id") int id, @Field("originId") int originId);

    @POST("lg/uncollect_originId/{id}/json")
    Observable<HttpResponse<EmptyData>> revokeCollectArticle(@Path("id") int id);

//    @FormUrlEncoded
//    @POST("lg/collect/add/json")
//    Observable<HttpResponse<EmptyData>> collectOffSiteArticle(@Field("title") String title,
//                                                              @Field("author") String author,
//                                                              @Field("link") String link);
    @GET("lg/collect/usertools/json")
    Observable<HttpResponse<List<Articles.Article>>> getCollectedWebsites();
}
