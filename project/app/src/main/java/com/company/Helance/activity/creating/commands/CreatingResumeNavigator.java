package com.company.Helance.activity.creating.commands;

public interface CreatingResumeNavigator {

    void handleError(Throwable throwable);

    void onComplete();

    void postResume();
}
