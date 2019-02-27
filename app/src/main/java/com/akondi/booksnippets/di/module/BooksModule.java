package com.akondi.booksnippets.di.module;

import com.akondi.booksnippets.api.CarApiService;
import com.akondi.booksnippets.di.scope.PerActivity;
import com.akondi.booksnippets.mvp.presenter.BooksActivityPresenter;
import com.akondi.booksnippets.mvp.view.BooksActivityView;
import com.akondi.booksnippets.repositories.BooksRepository;


import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

@Module
public class BooksModule {

    private BooksActivityView booksActivityView;


    public BooksModule(BooksActivityView booksActivityView) {
        this.booksActivityView = booksActivityView;
    }

//    @PerActivity
//    @Provides
//    BooksActivityPresenter providesBooksActivityPresenter(BooksRepository booksRepository, Scheduler mainScheduler) {
//        return new BooksActivityPresenter(booksRepository, mainScheduler);
//    }


    @PerActivity
    @Provides
    BooksActivityView providesBooksActivityView() {
        return booksActivityView;
    }
}
