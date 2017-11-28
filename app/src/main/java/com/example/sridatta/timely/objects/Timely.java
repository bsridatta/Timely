package com.example.sridatta.timely.objects;

import android.app.Application;

import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sridatta on 17-09-2017.
 */

public class Timely extends Application {
    // for FIREBASE
    //launches every time for every activity..so all activities can use firebase


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
