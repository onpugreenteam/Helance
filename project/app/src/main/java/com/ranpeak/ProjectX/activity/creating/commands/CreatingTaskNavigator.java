package com.ranpeak.ProjectX.activity.creating.commands;

public interface CreatingTaskNavigator {

    void handleError(Throwable throwable);

    void onComplete();

    void postTask();
}
