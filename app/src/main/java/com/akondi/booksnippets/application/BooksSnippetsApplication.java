package com.akondi.booksnippets.application;

import android.app.Application;
import android.util.Log;

import com.akondi.booksnippets.component.AppComponent;
import com.akondi.booksnippets.component.DaggerAppComponent;
import com.akondi.booksnippets.module.AppModule;

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
