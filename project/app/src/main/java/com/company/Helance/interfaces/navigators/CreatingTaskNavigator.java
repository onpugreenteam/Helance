package com.company.Helance.interfaces.navigators;

public interface CreatingTaskNavigator {

    void handleError(Throwable throwable);

    void onComplete();

    void postTask();
}
