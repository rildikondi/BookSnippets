package com.akondi.booksnippets.component;

import com.akondi.booksnippets.activity.BooksActivity;
import com.akondi.booksnippets.databases.Database;
import com.akondi.booksnippets.module.AppModule;
import com.akondi.booksnippets.repositories.impl.DatabaseBooksRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(BooksActivity booksActivity);
}
