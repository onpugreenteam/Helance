package com.company.Helance.activity.lobby.viewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.company.Helance.activity.lobby.DefaultSubscriber;
import com.company.Helance.interfaces.navigators.ResumeNavigator;
import com.company.Helance.base.BaseViewModel;
import com.company.Helance.dataBase.App;
import com.company.Helance.dataBase.local.LocalDB;
import com.company.Helance.dataBase.local.dao.ResumeDAO;
import com.company.Helance.dto.ResumeDTO;
import com.company.Helance.networking.retrofit.ApiService;
import com.company.Helance.networking.retrofit.RetrofitClient;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ResumeViewModel extends BaseViewModel<ResumeNavigator> {

    private Context context;

    private LocalDB localDB = App.getInstance().getLocalDB();
    private ResumeDAO resumeDAO = localDB.resumeDAO();

    private ApiService apiService = RetrofitClient.getInstance()
            .create(ApiService.class);

    public ResumeViewModel(Context context) {
        this.context = context;
    }

    @SuppressLint("CheckResult")
    public void getResumesFromServer(){
        getNavigator().startLoading();
        apiService.getAllResumes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ResumeDTO>>() {
                    @Override
                    public void onNext(List<ResumeDTO> resumeDTOS) {
                        addResumesToLocalDB(resumeDTOS);
                        getNavigator().getDataInAdapter(resumeDTOS);
                        Log.d("Resume size from server", String.valueOf(resumeDTOS.size()));
                        getNavigator().stopLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error", e.getMessage());
                        getNavigator().handleError(e);
                        getResumesFromLocalDB();
                    }

                    @Override
                    public void onComplete() {
                        // Received all notes
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getResumesFromLocalDB(){
        resumeDAO.getAllResumes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resumeDTOS -> {
                    getNavigator().getDataInAdapter(resumeDTOS);
                    Log.d("Resume size in LocalDB", String.valueOf(resumeDTOS.size()));
                    getNavigator().stopLoading();
                },throwable -> {
                    getNavigator().handleError(throwable);
                    getNavigator().stopLoading();
                });
    }

    private void addResumesToLocalDB(List<ResumeDTO> resumeDTOS) {
        Observable.fromCallable(() -> localDB.resumeDAO().insertAll(resumeDTOS))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<Long>>(){
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        super.onSubscribe(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<Long> longs) {
                        super.onNext(longs);
                        Log.d("AddResumes","insert resumes transaction complete");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        super.onError(e);
                        Log.d("AddResumes","error storing countries in db"+e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("AddResumes","insert resumes transaction complete");
                    }
                });
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
