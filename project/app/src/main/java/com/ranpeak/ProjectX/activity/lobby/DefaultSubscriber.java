package com.ranpeak.ProjectX.activity.lobby;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class DefaultSubscriber<T> implements Observer<T> {

    Disposable disposable;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("sds",e.getMessage());
    }

    @Override
    public void onComplete() {

    }

}