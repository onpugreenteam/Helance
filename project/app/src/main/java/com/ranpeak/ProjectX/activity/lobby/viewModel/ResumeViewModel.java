package com.ranpeak.ProjectX.activity.lobby.viewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.ranpeak.ProjectX.activity.lobby.commands.ResumeNavigator;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.tasksNavFragment.TasksFragment;
import com.ranpeak.ProjectX.base.BaseViewModel;
import com.ranpeak.ProjectX.dataBase.App;
import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.ResumeDAO;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
        apiService.getAllResumes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ResumeDTO>>() {
                    @Override
                    public void onNext(List<ResumeDTO> resumeDTOS) {
                        addResumesToLocalDB(resumeDTOS);
                        getNavigator().getDataInAdapter(resumeDTOS);
                        Log.d("Resume size from server", String.valueOf(resumeDTOS.size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error", e.getMessage());
                        getResumesFromLocalDB();
                    }

                    @Override
                    public void onComplete() {
                        // Received all notes
                    }
                });
    }

    private void getResumesFromLocalDB(){
        resumeDAO.getAllResumes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resumeDTOS -> {
                    getNavigator().getDataInAdapter(resumeDTOS);
                    Log.d("Resume size in LocalDB", String.valueOf(resumeDTOS.size()));
                },throwable -> {
                    getNavigator().handleError(throwable);
                });
    }

    private void addResumesToLocalDB(List<ResumeDTO> resumeDTOS) {
        Observable.fromCallable(() -> localDB.resumeDAO().insertAll(resumeDTOS))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new TasksFragment.DefaultSubscriber<List<Long>>(){
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        super.onSubscribe(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<Long> longs) {
                        super.onNext(longs);
                        Timber.d("insert countries transaction complete");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        super.onError(e);
                        Timber.d("error storing countries in db"+e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("insert countries transaction complete");
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
