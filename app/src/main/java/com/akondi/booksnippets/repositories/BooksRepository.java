package com.akondi.booksnippets.repositories;

import com.akondi.booksnippets.model.Book;

import java.util.List;

import io.reactivex.Single;

public interface BooksRepository {

    Single<List<Book>> getBooks();
}
