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
    private Flowable<List<ResumeDTO>> allResumes;

    public ResumeViewModel(@NotNull Application application) {
        super(application);
        repository = new ResumeRepository(application);
        allResumes = repository.getAllResumes();
    }

    public void insert(ResumeDTO resume) {
        repository.insert(resume);
    }

    public void update(ResumeDTO resume) {
        repository.update(resume);
    }

    public void delete(ResumeDTO resume) {
        repository.delete(resume);
    }

    public Flowable<List<ResumeDTO>> getAllResumes() {
        return allResumes;
    }

    public LiveData<Integer> getCountOfUsersResumes(String userLogin) {
        return repository.getCountOfUsersResumes(userLogin);
    }
}
