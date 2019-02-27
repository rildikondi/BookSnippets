package com.akondi.booksnippets.mvp.presenter;

import com.akondi.booksnippets.api.CarApiService;
import com.akondi.booksnippets.mapper.CarMapper;
import com.akondi.booksnippets.mvp.model.CarsResponse;
import com.akondi.booksnippets.mvp.model.CarsResponsePlacemarks;
import com.akondi.booksnippets.mvp.model.Storage;
import com.akondi.booksnippets.mvp.view.MainView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class CarActivityPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule(); // allows different runners

    @Mock
    CarApiService carApiService;

    @Mock
    CarMapper carMapper;

    @Mock
    Storage storage;

    @Mock
    MainView view;

    CarActivityPresenter presenter;


    private  CarsResponse EMPTY_RESPONSE = new CarsResponse();
    private  CarsResponse MANY_CARS = new CarsResponse();
    private CarsResponsePlacemarks[] carsResponsePlacemarks =  {new CarsResponsePlacemarks(), new CarsResponsePlacemarks(), new CarsResponsePlacemarks()};

    @Before
    public void setUp() {
        //Schedulers.trampoline() refers to current thread(the same that runs the test)
        presenter = new CarActivityPresenter(carApiService, carMapper, storage, Schedulers.trampoline());
        presenter.setView(view);
        MANY_CARS.setPlacemarks(carsResponsePlacemarks);

        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        //RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        //reset for next test because they are static methods and they will have old values
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldLoadCarsToView() throws InterruptedException {
        //given
        Mockito.when(carApiService.getCars()).thenReturn(Observable.<CarsResponse>just(MANY_CARS));

        // when
        presenter.getCars();

        //then
        Mockito.verify(presenter.getView()).onCarsLoaded(MANY_CARS);
    }

    @Test
    public void shouldHandleBooksFound() {
        //given
        Mockito.when(carApiService.getCars()).thenReturn(Observable.<CarsResponse>just(EMPTY_RESPONSE));

        // when
        presenter.getCars();

        //then
        Mockito.verify(presenter.getView()).onShowToast("Car list is empty!");
    }

    @Test
    public void shouldHandleError() {
        Mockito.when(carApiService.getCars()).thenReturn(Observable.<CarsResponse>error(new Throwable("boom")));

        presenter.getCars();

        Mockito.verify(presenter.getView()).onShowToast("Error loading cars: " + "boom");
    }
}