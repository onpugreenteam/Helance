package com.company.Helance.interfaces.navigators;

public interface CreatingResumeNavigator {

    void handleError(Throwable throwable);

    void onComplete();

    void postResume();
}
