package com.ranpeak.ProjectX.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ranpeak.ProjectX.activity.lobby.commands.TaskNavigator;
import com.ranpeak.ProjectX.dataBase.local.repository.TaskRepository;
import com.ranpeak.ProjectX.dto.TaskDTO;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;

    public TaskViewModel(@NotNull Application application) {
        super(application);
        repository = new TaskRepository();
    }

    public void insert(TaskDTO taskDTO) {
        repository.insert(taskDTO);
    }

    public void insertAll(List<TaskDTO> taskDTO) {
        repository.insertAll(taskDTO);
    }

    public void update(TaskDTO taskDTO) {
        repository.update(taskDTO);
    }

    public void delete(TaskDTO taskDTO) {
        repository.delete(taskDTO);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<Integer> getCountOfUsersTask(String userLogin) {
        return repository.getCountOfUserTask(userLogin);
    }

    public Flowable<List<TaskDTO>> getAllUsersTask(String userLogin) {
        return repository.getAllUsersTask(userLogin);
    }

    public Flowable<List<TaskDTO>> getAllTasks() {
        return repository.getAllTasks();
    }

    public Flowable<TaskDTO> getTaskById(long id) {
        return repository.getTaskById(id);
    }
}
