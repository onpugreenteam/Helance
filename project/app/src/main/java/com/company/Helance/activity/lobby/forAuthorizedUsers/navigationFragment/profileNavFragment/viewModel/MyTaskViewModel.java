package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.company.Helance.dataBase.local.repository.ProfileRepository;
import com.company.Helance.dataBase.local.repository.TaskRepository;
import com.company.Helance.dto.MyTaskDTO;
import com.company.Helance.dto.SocialNetworkDTO;
import com.company.Helance.dto.TaskDTO;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class MyTaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private ProfileRepository profileRepository;

    public MyTaskViewModel(@NotNull Application application) {
        super(application);
        repository = new TaskRepository();
        profileRepository = new ProfileRepository();
    }

    public void insert(TaskDTO taskDTO) {
        repository.insert(taskDTO);
    }

    public void insertAll(List<TaskDTO> taskDTO) {
        repository.insertAll(taskDTO);
    }

    public void update(MyTaskDTO taskDTO) {
        repository.update(taskDTO);
    }

    public void delete(MyTaskDTO taskDTO) {
        repository.delete(taskDTO);
    }

    public void deleteAllUsersTasks() {
        repository.deleteAllUsersTasks();
    }

    public LiveData<Integer> getCountOfUsersTask(String userLogin) {
        return repository.getCountOfUserTask(userLogin);
    }

    public Flowable<List<MyTaskDTO>> getAllUsersTask(String userLogin) {
        return repository.getAllUsersTask(userLogin);
    }

    public Flowable<List<TaskDTO>> getAllTasks() {
        return repository.getAllTasks();
    }

    public Flowable<MyTaskDTO> getTaskById(long id) {
        return repository.getTaskById(id);
    }

    public Observable<List<SocialNetworkDTO>> getUserNetworks(String userLogin) {
        return profileRepository.getUserNetworks(userLogin);
    }
}
