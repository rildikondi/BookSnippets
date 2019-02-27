package com.akondi.booksnippets.di.module;

import android.content.Context;


import com.akondi.booksnippets.databases.Database;
import com.akondi.booksnippets.repositories.BooksRepository;
import com.akondi.booksnippets.repositories.impl.DatabaseBooksRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

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
}
