package com.akondi.booksnippets.mvp.presenter;

import com.akondi.booksnippets.base.BaseSinglePresenter;
import com.akondi.booksnippets.mvp.model.Book;
import com.akondi.booksnippets.repositories.BooksRepository;
import com.akondi.booksnippets.mvp.view.BooksActivityView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class BooksActivityPresenter extends BaseSinglePresenter<BooksActivityView> {

    private BooksRepository booksRepository;
    private Scheduler mainScheduler;

    @Inject
    public BooksActivityPresenter(BooksRepository booksRepository, Scheduler mainScheduler) {
        this.booksRepository = booksRepository;
        this.mainScheduler = mainScheduler;
    }

    public void loadBooks() {
        // Observable goes to get data on repository and uses a new thread
        // Observer (subscriber) listen for incoming data using main thread
//        Single<List<Book>> observable = booksRepository.getBooks();
//        subscribe(observable, new DisposableSingleObserver<List<Book>>() {
//            @Override
//            public void onSuccess(List<Book> bookList) {
//                System.out.println("Thread subsribe: " + Thread.currentThread().getId());
//                if (!bookList.isEmpty())
//                    getView().displayBooks(bookList);
//                else
//                    getView().displayNoBooks();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                getView().displayError();
//            }
//        }, mainScheduler);


        booksRepository.getBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .doOnSuccess(this::shouldDisplayBooks)
                .doOnError(this::shouldDisplayError)
                .subscribe();


//        Disposable disposable = booksRepository.getBooks()
//                .subscribeOn(Schedulers.io())
//                .observeOn(mainScheduler)
//                .doOnSuccess(this::shouldDisplayBooks)
//                .subscribe();
    }

    private void shouldDisplayError(Throwable throwable) {
        getView().displayError();
    }

    private void shouldDisplayBooks(List<Book> bookList) {
        System.out.println("Thread subsribe: " + Thread.currentThread().getId());
        if (!bookList.isEmpty())
            getView().displayBooks(bookList);
        else
            getView().displayNoBooks();
    }

    public void unsubscribe() {
        clearCompositeDisposable();
    }
}
