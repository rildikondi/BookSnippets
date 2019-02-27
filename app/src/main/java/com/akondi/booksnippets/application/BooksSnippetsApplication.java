package com.akondi.booksnippets.application;

import android.app.Application;
import android.util.Log;

import com.akondi.booksnippets.di.component.AppComponent;

import com.akondi.booksnippets.di.component.DaggerAppComponent;
import com.akondi.booksnippets.di.module.AppModule;
import com.akondi.booksnippets.di.module.DbModule;
import com.akondi.booksnippets.di.module.WebServiceModule;

public class BooksSnippetsApplication extends Application {

    private static final String TAG = "BooksSnippApplication";
    private AppComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dbModule(new DbModule())
                .webServiceModule(new WebServiceModule(this, "http://redirect.mytaxi.net"))
                .build();
    }

    public AppComponent getAppComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
