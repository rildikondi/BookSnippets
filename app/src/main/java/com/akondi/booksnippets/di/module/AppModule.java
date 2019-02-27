package com.akondi.booksnippets.di.module;

import android.content.Context;
import android.content.res.Resources;

import com.akondi.booksnippets.application.BooksSnippetsApplication;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
public class AppModule {

    private final BooksSnippetsApplication application;

    public AppModule(BooksSnippetsApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    Resources providesActivityResources(Context app) {
        return app.getResources();
    }


    @Provides
    @Singleton
    Scheduler providesScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
