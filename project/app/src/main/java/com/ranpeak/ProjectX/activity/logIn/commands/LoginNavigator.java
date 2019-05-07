package com.ranpeak.ProjectX.activity.logIn.commands;

public interface LoginNavigator {

    void handleError(Throwable throwable);

    void loginClicked();

    void openLobbyActivity();

    void openRegistrationActivity(String name, String email, String login, String country);
}
