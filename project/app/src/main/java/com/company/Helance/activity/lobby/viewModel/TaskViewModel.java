package com.company.Helance.activity.lobby.viewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.company.Helance.activity.lobby.DefaultSubscriber;
import com.company.Helance.interfaces.navigators.TaskNavigator;
import com.company.Helance.base.BaseViewModel;
import com.company.Helance.dataBase.App;
import com.company.Helance.dataBase.local.LocalDB;
import com.company.Helance.dataBase.local.dao.TaskDAO;
import com.company.Helance.dto.TaskDTO;
import com.company.Helance.networking.retrofit.ApiService;
import com.company.Helance.networking.retrofit.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TaskViewModel extends BaseViewModel<TaskNavigator> {

    private Context context;

    private List<TaskDTO> data = new ArrayList<>();
    private LocalDB localDB = App.getInstance().getLocalDB();
    private TaskDAO taskDAO = localDB.taskDao();

    private ApiService apiService = RetrofitClient.getInstance()
            .create(ApiService.class);

    public TaskViewModel(Context context) {
        this.context = context;
    }


    @SuppressLint("CheckResult")
    public void getTasksFromServer(){
        getNavigator().startLoading();
        apiService.getAllTask()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<TaskDTO>>() {
                    @Override
                    public void onNext(List<TaskDTO> taskDTOS) {
                        addTasksToLocalDB(taskDTOS);
                        getNavigator().getDataInAdapter(taskDTOS);
                        Log.d("Task size from server", String.valueOf(taskDTOS.size()));
                        getNavigator().stopLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error", e.getMessage());
                        getNavigator().handleError(e);
                        getTasksFromLocalDB();
                        getNavigator().stopLoading();
                    }

                    @Override
                    public void onComplete() {
                        // Received all notes
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getTasksFromLocalDB(){
        taskDAO.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskDTOS -> {
                    getNavigator().getDataInAdapter(taskDTOS);
                    Log.d("Task size in LocalDB", String.valueOf(taskDTOS.size()));
                },throwable -> {
                    getNavigator().handleError(throwable);
                });
    }

    private void addTasksToLocalDB(List<TaskDTO> tasksDTOS) {
        Observable.fromCallable(() -> localDB.taskDao().insertAll(tasksDTOS))
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
                        Log.d("AddTasks","insert tasks transaction complete");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        super.onError(e);
                        Log.d("AddTasks","error storing countries in db"+e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("AddTasks","insert tasks transaction complete");
                    }
                });
    }

    public List<TaskDTO> getData() {
        return data;
    }

    public void setData(List<TaskDTO> data) {
        this.data = data;
    }
}
