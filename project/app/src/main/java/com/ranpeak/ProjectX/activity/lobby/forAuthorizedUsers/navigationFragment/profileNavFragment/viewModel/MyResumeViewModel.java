package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.ranpeak.ProjectX.dataBase.local.repository.ResumeRepository;
import com.ranpeak.ProjectX.dto.MyResumeDTO;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import io.reactivex.Flowable;

public class MyResumeViewModel extends AndroidViewModel {

    private ResumeRepository repository;

    public MyResumeViewModel(@NotNull Application application) {
        super(application);
        repository = new ResumeRepository(application);
    }

    public void insert(ResumeDTO resume) {
        repository.insert(resume);
    }

    public void insertAll(List<ResumeDTO>resume) {
        repository.insertAll(resume);
    }

    public void update(MyResumeDTO resume) {
        repository.update(resume);
    }

    public void delete(MyResumeDTO resume) {
        repository.delete(resume);
    }

    public Flowable<List<MyResumeDTO>> getAllUsersResumes(String userLogin) {
        return repository.getAllUsersResume(userLogin);
    }

    public LiveData<Integer> getCountOfUsersResumes(String userLogin) {
        return repository.getCountOfUsersResumes(userLogin);
    }

    public Flowable<MyResumeDTO> getResumeById(long id) {
        return repository.getResumeById(id);
    }
}
