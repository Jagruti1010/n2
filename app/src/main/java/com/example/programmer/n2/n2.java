package com.example.programmer.n2;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Programmer on 10/5/2016.
 */

public class n2 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
