package com.yl.wanandroid.service;

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
                        switch (response.getErrorCode()) {
                            case 0:
                                listener.onSuccess(response);
                                break;
                            case -1001:
                                errorListener.onTokenInvalid(response.getErrorMsg());
                                break;
                            default:
                                Log.e(TAG, "accept: " + response.getErrorMsg());
                                errorListener.onError(response.getErrorMsg());
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
