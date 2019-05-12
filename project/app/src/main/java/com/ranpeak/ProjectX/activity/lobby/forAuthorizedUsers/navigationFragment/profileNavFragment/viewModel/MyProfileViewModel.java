package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.ranpeak.ProjectX.dataBase.local.repository.ProfileRepository;
import com.ranpeak.ProjectX.dto.SocialNetworkDTO;

import java.util.List;

import io.reactivex.Flowable;

public class MyProfileViewModel extends AndroidViewModel {
    private ProfileRepository repository;

    public MyProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository();
    }

    public Flowable<List<SocialNetworkDTO>> getAllSocialNetworks(String userLogin) {
        return repository.getAllSocialNetworks(userLogin);
    }
}
