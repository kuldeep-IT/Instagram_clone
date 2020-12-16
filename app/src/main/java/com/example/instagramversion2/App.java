package com.example.instagramversion2;

import android.app.Application;

import com.parse.Parse;

// please visit parseplatform.org

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("S5BYXMHmU9rmz2PDQAFXC0nqJMOdc56VU7RB1QIZ")
                // if defined
                .clientKey("0B4ZxouvVVYaUUmi9HDwUq466TG8OeGZAx8D607z")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
