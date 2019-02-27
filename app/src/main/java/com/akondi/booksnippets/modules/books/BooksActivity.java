package com.akondi.booksnippets.modules.books;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.akondi.booksnippets.R;
import com.akondi.booksnippets.base.BaseActivity;
import com.akondi.booksnippets.di.component.DaggerBooksComponent;
import com.akondi.booksnippets.di.module.BooksModule;
import com.akondi.booksnippets.modules.cars.CarActivity;
import com.akondi.booksnippets.mvp.model.Book;
import com.akondi.booksnippets.mvp.presenter.BooksActivityPresenter;
import com.akondi.booksnippets.mvp.view.BooksActivityView;
import com.akondi.booksnippets.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class BooksActivity extends BaseActivity implements BooksActivityView {

    private static final String TAG = "BooksActivity";

    @Inject
    protected BooksActivityPresenter presenter;

    @BindView(R.id.btn_goToCars)
    protected Button btn_goToCars;


    @Override
    protected void onViewReady(Bundle savedInstanceSate, Intent intent) {
        setupCarButton();
        super.onViewReady(savedInstanceSate, intent);
        if (NetworkUtils.isNetAvailable(this)) {
            presenter.loadBooks();
        } else {
            //presenter.getCarsFromDatabase();
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupCarButton() {
        btn_goToCars.setOnClickListener(v -> {
            Intent intent = new Intent(this, CarActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void resolveDaggerDependency() {
        //getApplicationComponent().inject(this);

        DaggerBooksComponent.builder()
                .appComponent(getApplicationComponent())
                .booksModule(new BooksModule(this))
                .build().inject(this);

        //presenter.setView(this); // activity can't be provided with dagger
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
