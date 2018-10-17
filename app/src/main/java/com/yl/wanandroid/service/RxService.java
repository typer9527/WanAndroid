package com.yl.wanandroid.service;

import android.text.TextUtils;
import android.util.Log;

import com.yl.wanandroid.service.interfaces.APIService;
import com.yl.wanandroid.service.interfaces.ErrorListener;
import com.yl.wanandroid.service.interfaces.ResponseListener;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxService {
    private static final String TAG = "RxService";
    private Disposable disposable;

    public APIService getService() {
        return RetrofitFactory.getInstance().getApiService();
    }

    public <T> void add(final Observable<HttpResponse<T>> observable, final ResponseListener<T> listener, final ErrorListener errorListener) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResponse<T>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(HttpResponse<T> response) {
                        String errorMsg = response.getErrorMsg();
                        switch (response.getErrorCode()) {
                            case 0:
                                listener.onSuccess(response);
                                break;
                            case -1001:
                                errorListener.onTokenInvalid(errorMsg);
                                break;
                            default:
                                if (TextUtils.isEmpty(errorMsg)) errorMsg = "服务器异常，请稍后再试";
                                Log.e(TAG, "accept: " + errorMsg);
                                errorListener.onError(errorMsg);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                        errorListener.onNetError();
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
    }
}
