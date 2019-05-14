package com.ranpeak.ProjectX.activity.registration.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.ranpeak.ProjectX.dataBase.local.repository.UserRepository;
import com.ranpeak.ProjectX.dto.pojo.SocialNetworkPOJO;

import java.util.List;

public class RegistrationViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public boolean checkUserLogin(String login) {
        return userRepository.checkLogin(login);
    }

    public boolean checkUserEmail(String email) {
        return userRepository.checkEmail(email);
    }

    // used to recreate password
    public boolean checkEmailOnServer(String email) {
        return userRepository.checkEmailOnServer(email);
    }

    public void sendCodeOnEmail(String email) {

        userRepository.sendCodeOnEmail(email);
    }

    public void register(String login, String email, String name, String password, String country, String phone) {
        userRepository.register(login,
                email, name, password, country, phone);
    }

    public void register(String login, String email, String name,
                         String password, String country, String phone,
                         List<SocialNetworkPOJO> list) {
        userRepository.register(login, email, name,
                password, country, phone, list);
    }

    public void register(UserRepository.OnRegistrationUserFinished listener, String login, String email, String name, String password, String country, String phone) {
        userRepository.register(listener, login,
                email, name, password, country, phone);
    }

    public boolean checkCode(String email, String code) {
        return userRepository.checkCode(email, code);
    }

    public void changePassword(String email, String password) {
        userRepository.changePassword(email, password);
    }

    public void getSocialNetworks(String login) {
        userRepository.getSocialNetworks(login);
    }
}
