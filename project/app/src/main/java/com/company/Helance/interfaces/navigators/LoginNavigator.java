package com.company.Helance.interfaces.navigators;

public interface LoginNavigator {

    void handleError(Throwable throwable);

    void loginClicked();

    void openLobbyActivity();

    void openRegistrationActivity(String login,
                                  String email,
                                  String name,
                                  String country,
                                  String avatar,
                                  String phone);
}
