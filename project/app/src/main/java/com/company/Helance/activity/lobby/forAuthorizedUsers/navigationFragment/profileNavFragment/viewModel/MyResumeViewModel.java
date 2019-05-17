package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.company.Helance.dataBase.local.repository.ProfileRepository;
import com.company.Helance.dataBase.local.repository.ResumeRepository;
import com.company.Helance.dto.MyResumeDTO;
import com.company.Helance.dto.ResumeDTO;
import com.company.Helance.dto.SocialNetworkDTO;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class MyResumeViewModel extends AndroidViewModel {

    private ResumeRepository repository;
    private ProfileRepository profileRepository;

    public MyResumeViewModel(@NotNull Application application) {
        super(application);
        repository = new ResumeRepository(application);
        profileRepository = new ProfileRepository();
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

    public Observable<List<SocialNetworkDTO>> getUserNetworks(String userLogin) {
//        Log.d("Network_2", String.valueOf(profileRepository.getUserNetworks(userLogin).size()));

        return profileRepository.getUserNetworks(userLogin);
    }

    public void deleteAllUsersResumes() {
        repository.deleteAllUsersResumes();
    }
}
