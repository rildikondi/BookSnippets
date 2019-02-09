package com.akondi.booksnippets.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.akondi.booksnippets.R;
import com.akondi.booksnippets.application.BooksSnippetsApplication;
import com.akondi.booksnippets.component.AppComponent;
import com.akondi.booksnippets.model.Book;
import com.akondi.booksnippets.module.AppModule;
import com.akondi.booksnippets.presenter.BooksActivityPresenter;
import com.akondi.booksnippets.repositories.BooksRepository;
import com.akondi.booksnippets.repositories.impl.DatabaseBooksRepository;
import com.akondi.booksnippets.view.BooksActivityView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class BooksActivity extends AppCompatActivity implements BooksActivityView {

    private static final String TAG = "BooksActivity";

    @Inject
    BooksActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BooksSnippetsApplication) getApplication()).getAppComponent().inject(this);
        presenter.setView(this); // activity can't be provided with dagger
        presenter.loadBooks();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void displayBooks(List<Book> bookList) {
        Log.d(TAG, "displayBooks: found some books");
    }

    @Override
    public void displayNoBooks() {
        Log.d(TAG, "displayNoBooks: found no book");
    }

    @Override
    public void displayError() {
        Toast.makeText(this, "Error accessing data!", Toast.LENGTH_SHORT).show();
    }
}
