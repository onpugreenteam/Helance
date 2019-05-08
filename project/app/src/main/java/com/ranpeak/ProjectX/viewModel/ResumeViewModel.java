package com.ranpeak.ProjectX.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ranpeak.ProjectX.dataBase.local.repository.ResumeRepository;
import com.ranpeak.ProjectX.dto.ResumeDTO;

import org.intellij.lang.annotations.Flow;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;

public class ResumeViewModel extends AndroidViewModel {

    private ResumeRepository repository;

    public ResumeViewModel(@NotNull Application application) {
        super(application);
        repository = new ResumeRepository(application);
    }

    public void insert(ResumeDTO resume) {
        repository.insert(resume);
    }

    public void insertAll(List<ResumeDTO>resume) {
        repository.insertAll(resume);
    }

    public void update(ResumeDTO resume) {
        repository.update(resume);
    }

    public void delete(ResumeDTO resume) {
        repository.delete(resume);
    }

    public Flowable<List<ResumeDTO>> getAllResumes() {
        return repository.getAllResumes();
    }

    public Flowable<List<ResumeDTO>> getAllUsersResumes(String userLogin) {
        return repository.getAllUsersResume(userLogin);
    }

    public LiveData<Integer> getCountOfUsersResumes(String userLogin) {
        return repository.getCountOfUsersResumes(userLogin);
    }

    public Flowable<ResumeDTO> getTaskById(long id) {
        return repository.getResumeById(id);
    }
}
