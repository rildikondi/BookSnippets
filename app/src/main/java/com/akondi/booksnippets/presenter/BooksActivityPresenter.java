package com.akondi.booksnippets.presenter;

import com.akondi.booksnippets.model.Book;
import com.akondi.booksnippets.repositories.BooksRepository;
import com.akondi.booksnippets.view.BooksActivityView;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BooksActivityPresenter {

    private BooksActivityView view;
    private BooksRepository booksRepository;
    private Scheduler mainScheduler;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();// to handle lifecycle of activity


    public BooksActivityPresenter(BooksRepository booksRepository, Scheduler mainScheduler) {
        this.booksRepository = booksRepository;
        this.mainScheduler = mainScheduler;
    }

    public void setView(BooksActivityView view) {
        this.view = view;
    }

    public void loadBooks() {
        // Observable goes to get data on repository and uses a new thread
        // Observer (subscriber) listen for incoming data using main thread
        DisposableSingleObserver<List<Book>> disposableSingleObserver = booksRepository.getBooks()
                .subscribeOn(Schedulers.io()) // new thread on getting data
                .observeOn(mainScheduler) // listen for incoming data in main thread
                .subscribeWith(new DisposableSingleObserver<List<Book>>() {
                    @Override
                    public void onSuccess(List<Book> bookList) {
                        System.out.println("Thread subsribe: " + Thread.currentThread().getId());
                        if (!bookList.isEmpty())
                            view.displayBooks(bookList);
                        else
                            view.displayNoBooks();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.displayError();
                    }
                });
        compositeDisposable.add(disposableSingleObserver);
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
