package com.ranpeak.ProjectX.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import java.lang.ref.WeakReference;

public abstract class BaseViewModel<N> extends ViewModel {

    private final ObservableBoolean mIsLoading = new ObservableBoolean();

    private WeakReference<N> mNavigator;

    public BaseViewModel(WeakReference<N> mNavigator) {
        this.mNavigator = mNavigator;
    }

    protected BaseViewModel() {
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

}
