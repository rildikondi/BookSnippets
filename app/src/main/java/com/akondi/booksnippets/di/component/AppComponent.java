package com.akondi.booksnippets.di.component;

import android.content.Context;

import com.akondi.booksnippets.di.module.AppModule;
import com.akondi.booksnippets.di.module.DbModule;
import com.akondi.booksnippets.di.module.WebServiceModule;
import com.akondi.booksnippets.repositories.BooksRepository;


import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, WebServiceModule.class, DbModule.class})
public interface AppComponent {

    //void inject(BooksSnippetsApplication application);

    Scheduler providesScheduler();

    Context providesContext();

    BooksRepository exposeBooksRepository();

    Retrofit exposeRetrofit();
}
