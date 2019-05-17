package com.company.Helance.activity.creating.commands;

public interface CreatingTaskNavigator {

    void handleError(Throwable throwable);

    void onComplete();

    void postTask();
}
