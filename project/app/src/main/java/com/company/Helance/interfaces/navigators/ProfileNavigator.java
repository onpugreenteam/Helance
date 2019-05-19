package com.company.Helance.interfaces.navigators;

import android.graphics.Bitmap;

public interface ProfileNavigator {

    void handleError(Throwable throwable);

    void uploadAvatar(Bitmap bitmap);

    void onComplete();
}
