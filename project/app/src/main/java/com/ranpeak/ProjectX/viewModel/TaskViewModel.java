package com.ranpeak.ProjectX.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ranpeak.ProjectX.dataBase.local.dto.Task;
import com.ranpeak.ProjectX.dataBase.local.repository.TaskRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel(@NotNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public void insert(Task taskDTO) {
        repository.insert(taskDTO);
    }

    public void update(Task taskDTO) {
        repository.update(taskDTO);
    }

    public void delete(Task taskDTO) {
        repository.delete(taskDTO);
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<Integer> getCountOfUsersTask(String userLogin) {
        return repository.getCountOfUserTask(userLogin);
    }
}
