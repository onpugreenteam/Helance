package com.ranpeak.ProjectX.dataBase.local.repository;

import android.arch.lifecycle.LiveData;

import com.ranpeak.ProjectX.dataBase.App;
import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.TaskDAO;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.dto.pojo.TaskPOJO;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
//        new InsertAllTaskAsyncTask(taskDao).execute(taskDTO);
//        taskDao.insertAll(taskDTO);
    }

    public void update(TaskDTO taskDTO) {
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
                        taskDTO.getViews()
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

    public void delete(TaskDTO taskDTO) {
        Completable.fromRunnable(() -> taskDao.delete(taskDTO))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        Call<TaskDTO> deleteReq = apiService.deleteTask(taskDTO.getId());
        deleteReq.enqueue(new Callback<TaskDTO>() {
            @Override
            public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {

            }

            @Override
            public void onFailure(Call<TaskDTO> call, Throwable t) {

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

    public Flowable<List<TaskDTO>> getAllTasks() {
        return allTasks;
    }

    public LiveData<Integer> getCountOfUserTask(String userLogin) {

        return taskDao.getCountOfUsersTask(userLogin);
    }

    public Flowable<List<TaskDTO>> getAllUsersTask (String userLogin) {

        return taskDao.getAllUsersTasks(userLogin);
    }

    public Flowable<TaskDTO> getTaskById (long userLogin) {
        return taskDao.getTaskById(userLogin);
    }
}
