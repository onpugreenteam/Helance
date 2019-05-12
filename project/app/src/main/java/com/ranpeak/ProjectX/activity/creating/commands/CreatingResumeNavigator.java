package com.ranpeak.ProjectX.activity.creating.commands;

public interface CreatingResumeNavigator {

    void handleError(Throwable throwable);

    void onComplete();

    void postResume();
}
