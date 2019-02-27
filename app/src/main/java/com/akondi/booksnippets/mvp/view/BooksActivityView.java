package com.akondi.booksnippets.mvp.view;

import com.akondi.booksnippets.mvp.model.Book;

import java.util.List;

public interface BooksActivityView extends BaseView {

    void displayBooks(List<Book> bookList);

    void displayNoBooks();

    void displayError();
}
