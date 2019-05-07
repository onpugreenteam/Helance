package com.ranpeak.ProjectX.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ranpeak.ProjectX.dataBase.local.dto.Resume;
import com.ranpeak.ProjectX.dataBase.local.repository.ResumeRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResumeViewModel extends AndroidViewModel {

    private ResumeRepository repository;
    private LiveData<List<Resume>> allResumes;

    public ResumeViewModel(@NotNull Application application) {
        super(application);
        repository = new ResumeRepository(application);
        allResumes = repository.getAllResumes();
    }

    public void insert(Resume resume) {
        repository.insert(resume);
    }

    public void update(Resume resume) {
        repository.update(resume);
    }

    public void delete(Resume resume) {
        repository.delete(resume);
    }

    public LiveData<List<Resume>> getAllResumes() {
        return allResumes;
    }

    public LiveData<Integer> getCountOfUsersResumes(String userLogin) {
        return repository.getCountOfUsersResumes(userLogin);
    }
}
