package com.yl.wanandroid.service.interfaces;

import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.Articles;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    @GET("article/list/{index}/json")
    Observable<HttpResponse<Articles>> getArticleList(@Path("index") int index);
}
