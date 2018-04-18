package com.example.admin.healofytest;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by admin on 18/04/18.
 */

public class SongApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
