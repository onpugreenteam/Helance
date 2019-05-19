package com.company.Helance.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.company.Helance.activity.settings.LanguageHelper;

/**
 * Created by Yarosh Andrew on 19.05.2019.
 */

public class BaseActivity extends AppCompatActivity {

    // override the base context of application to update default locale for this activity
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }

}
