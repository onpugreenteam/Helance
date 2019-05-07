package com.ranpeak.ProjectX.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ranpeak.ProjectX.dataBase.local.repository.TaskRepository;
import com.ranpeak.ProjectX.dto.TaskDTO;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private Flowable<List<TaskDTO>> allTasks;

    public TaskViewModel(@NotNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public void insert(TaskDTO taskDTO) {
        repository.insert(taskDTO);
    }

    public void update(TaskDTO taskDTO) {
        repository.update(taskDTO);
    }

    public void delete(TaskDTO taskDTO) {
        repository.delete(taskDTO);
    }

    public Flowable<List<TaskDTO>> getAllTasks() {
        return allTasks;
    }

    public LiveData<Integer> /* Flowable<Integer>*/ getCountOfUsersTask(String userLogin) {
        return repository.getCountOfUserTask(userLogin);
    }

    public Flowable<List<TaskDTO>> getAllUsersTask(String userLogin) {
        return repository.getAllUsersTask(userLogin);
    }

    public Flowable<TaskDTO> getTaskById(long id) {
        return repository.getTaskById(id);
    }
}
