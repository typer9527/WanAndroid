package com.yl.wanandroid.service;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxService {
    private static final String TAG = "RxService";
    private CompositeDisposable mCd;

    public RxService() {
        if (mCd == null || mCd.isDisposed()) {
            mCd = new CompositeDisposable();
        }
    }

    public <T> void add(Observable<HttpResponse<T>> observable, final ResponseListener<T> listener) {
        mCd.add(observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpResponse<T>>() {
                    @Override
                    public void accept(HttpResponse<T> response) {
                        if (response.getErrorCode() == 0) {
                            listener.onSuccess(response.getData());
                        } else if (response.getErrorCode() == -1001) {
                            listener.onTokenInvalid(response.getErrorMsg());
                        } else {
                            Log.e(TAG, "accept: " + response.getErrorMsg());
                            listener.onError(response.getErrorMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, "accept: " + throwable.getMessage());
                    }
                }));
    }

    // onDestroy/onDetach
    public void dispose() {
        if (mCd != null) {
            if (!mCd.isDisposed()) mCd.dispose();
            mCd = null;
        }
    }
}
