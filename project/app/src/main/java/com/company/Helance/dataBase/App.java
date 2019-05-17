package com.company.Helance.dataBase;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.company.Helance.dataBase.local.LocalDB;

public class App extends Application {

    public static App instance;

    private LocalDB localDB;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        localDB = Room.databaseBuilder(this, LocalDB.class,"database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public LocalDB getLocalDB() {
        return localDB;
    }

}
