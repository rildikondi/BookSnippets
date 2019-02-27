package com.akondi.booksnippets.di.module;

import com.akondi.booksnippets.api.CarApiService;
import com.akondi.booksnippets.di.scope.PerActivity;
import com.akondi.booksnippets.mvp.view.MainView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class CarModule {

    private MainView mainView;

    public CarModule(MainView mainView) {
        this.mainView = mainView;
    }

    @PerActivity
    @Provides
    CarApiService providesCarApiService(Retrofit retrofit) {
        return retrofit.create(CarApiService.class);
    }

    @PerActivity
    @Provides
    MainView providesMainView() {
        return mainView;
    }
}
