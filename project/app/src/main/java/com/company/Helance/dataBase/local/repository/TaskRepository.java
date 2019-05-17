package com.company.Helance.dataBase.local.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.company.Helance.dataBase.App;
import com.company.Helance.dataBase.local.LocalDB;
import com.company.Helance.dataBase.local.dao.TaskDAO;
import com.company.Helance.dto.MyTaskDTO;
import com.company.Helance.dto.TaskDTO;
import com.company.Helance.dto.pojo.TaskPOJO;
import com.company.Helance.networking.retrofit.ApiService;
import com.company.Helance.networking.retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRepository {
    private TaskDAO taskDao;
    private Flowable<List<TaskDTO>> allTasks;
    private ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);


    public TaskRepository() {
        LocalDB localDB = App.getInstance().getLocalDB();
        taskDao = localDB.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public void insert(TaskDTO taskDTO) {
        Completable.fromRunnable(() -> taskDao.insert(taskDTO))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
//        new InsertTaskAsyncTask(taskDao).execute(taskDTO);
//        taskDao.insert(taskDTO);
    }

    public void insertAll(List<TaskDTO> taskDTO) {
        Completable.fromRunnable(() -> taskDao.insertAll(taskDTO))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void insertAllUsersTasks(List<MyTaskDTO> taskDTO) {
        Completable.fromRunnable(() -> taskDao.insertAllUsersTasks(taskDTO))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void update(MyTaskDTO taskDTO) {
        Completable.fromRunnable(() -> taskDao.update(taskDTO))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        Call<TaskPOJO> call = apiService.updateTask(
                new TaskPOJO(
                        taskDTO.getId(),
                        taskDTO.getSubject(),
                        taskDTO.getHeadLine(),
                        taskDTO.getDescription(),
                        taskDTO.getDateStart(),
                        taskDTO.getPrice(),
                        taskDTO.getDeadline(),
                        taskDTO.getStatus(),
                        taskDTO.getUserLogin(),
                        taskDTO.getViews(),
                        taskDTO.getTelephone()
                )
        );
        call.enqueue(new Callback<TaskPOJO>() {
            @Override
            public void onResponse(Call<TaskPOJO> call, Response<TaskPOJO> response) {

            }

            @Override
            public void onFailure(Call<TaskPOJO> call, Throwable t) {

            }
        });
//        new UpdateTaskAsyncTask(taskDao).execute(taskDTO);
    }

    public void delete(MyTaskDTO taskDTO) {
        Completable.fromRunnable(() -> taskDao.delete(taskDTO))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        Call<MyTaskDTO> deleteReq = apiService.deleteTask(taskDTO.getId());
        deleteReq.enqueue(new Callback<MyTaskDTO>() {
            @Override
            public void onResponse(Call<MyTaskDTO> call, Response<MyTaskDTO> response) {

            }

            @Override
            public void onFailure(Call<MyTaskDTO> call, Throwable t) {

            }
        });
//        new DeleteTaskAsyncTask(taskDao).execute(taskDTO);
    }

    public void deleteAll() {
        Completable.fromRunnable(() -> taskDao.deleteAllTasks())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
//        new DeleteTaskAsyncTask(taskDao).execute(taskDTO);
    }

    public void deleteAllUsersTasks() {
        Completable.fromRunnable(() -> taskDao.deleteAllUsersTasks())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
//        new DeleteTaskAsyncTask(taskDao).execute(taskDTO);
    }

    public Flowable<List<TaskDTO>> getAllTasks() {
        return allTasks;
    }

    public LiveData<Integer> getCountOfUserTask(String userLogin) {

        return taskDao.getCountOfUsersTask(userLogin);
    }

    @SuppressLint("CheckResult")
    public Flowable<List<MyTaskDTO>> getAllUsersTask (String userLogin) {
        apiService.getAllUsersTasks(userLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<MyTaskDTO>>() {
                    @Override
                    public void onNext(List<MyTaskDTO> taskDTOS) {
                        refreshAllUsersTasks(taskDTOS);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        // Received all notes
                    }
                });
        return taskDao.getAllUsersTasks();
    }

    public Flowable<MyTaskDTO> getTaskById (long userLogin) {
        return taskDao.getTaskById(userLogin);
    }

    private void refreshAllUsersTasks(List<MyTaskDTO> myTaskDTO) {
        Completable.fromRunnable(() -> {
            taskDao.deleteAllUsersTasks();
            taskDao.insertAllUsersTasks(myTaskDTO);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
