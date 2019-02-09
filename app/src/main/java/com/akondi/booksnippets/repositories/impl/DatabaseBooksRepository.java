package com.akondi.booksnippets.repositories.impl;

import android.app.Application;
import android.content.Context;

import com.akondi.booksnippets.activity.BooksActivity;
import com.akondi.booksnippets.databases.Database;
import com.akondi.booksnippets.model.Book;
import com.akondi.booksnippets.repositories.BooksRepository;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;

public class DatabaseBooksRepository implements BooksRepository {

    private final Database database;

    public DatabaseBooksRepository(Database database) {
        this.database = database;
    }

    @Override
    public Single<List<Book>> getBooks() {
        return Single.fromCallable(() -> {
            try {
                System.out.println("Thread db: " + Thread.currentThread().getId());
                return database.getCarts();
            } catch (Exception e) {
                throw new RuntimeException("boom!");
            }
        });
    }
}
