package com.akondi.booksnippets.view;

import com.akondi.booksnippets.model.Book;

import java.util.List;

public interface BooksActivityView {

    void displayBooks(List<Book> bookList);

    void displayNoBooks();

    void displayError();
}
