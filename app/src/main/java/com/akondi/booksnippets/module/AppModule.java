package com.akondi.booksnippets.module;

import android.content.Context;
import android.content.res.Resources;

import com.akondi.booksnippets.activity.BooksActivity;
import com.akondi.booksnippets.application.BooksSnippetsApplication;
import com.akondi.booksnippets.databases.Database;
import com.akondi.booksnippets.presenter.BooksActivityPresenter;
import com.akondi.booksnippets.repositories.BooksRepository;
import com.akondi.booksnippets.repositories.impl.DatabaseBooksRepository;
import com.akondi.booksnippets.view.BooksActivityView;

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
    Database providesDatabase(Context context) {
        return new Database(context);
    }

    @Provides
    @Singleton
    BooksRepository providesBooksRepository(Database database) {
        return new DatabaseBooksRepository(database);
    }

    @Provides
    @Singleton
    BooksActivityPresenter providesBooksActivityPresenter(BooksRepository booksRepository, Scheduler mainScheduler) {
        return new BooksActivityPresenter(booksRepository, mainScheduler);
    }

    @Provides
    @Singleton
    Scheduler providesScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
