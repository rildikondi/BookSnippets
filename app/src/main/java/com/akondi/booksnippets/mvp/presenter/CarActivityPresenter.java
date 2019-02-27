package com.akondi.booksnippets.mvp.presenter;

import com.akondi.booksnippets.api.CarApiService;
import com.akondi.booksnippets.base.BasePresenter;
import com.akondi.booksnippets.mapper.CarMapper;
import com.akondi.booksnippets.mvp.model.CarsResponse;
import com.akondi.booksnippets.mvp.model.Storage;
import com.akondi.booksnippets.mvp.view.MainView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;

import io.reactivex.disposables.Disposable;

public class CarActivityPresenter extends BasePresenter<MainView> implements Observer<CarsResponse> {

    private CarApiService apiService;
    private CarMapper carMapper;
    private Storage storage;
    private Scheduler mainScheduler;

    @Inject
    public CarActivityPresenter(CarApiService apiService, CarMapper carMapper, Storage storage, Scheduler mainScheduler) {
        this.apiService = apiService;
        this.carMapper = carMapper;
        this.storage = storage;
        this.mainScheduler = mainScheduler;
    }

    public void getCars() {
        getView().onShowDialog("Loading cars");

        Observable<CarsResponse> carsResponseObservable = apiService.getCars();
        subscribe(carsResponseObservable, this, mainScheduler);

        /*Disposable subscribe =
                apiService.getCars()
                        .subscribeOn(Schedulers.io())
                        .observeOn(mainScheduler)
                        .doOnNext(this::shouldDisplayCars) // this:: its like putting inside a consumer
                        .doOnError(this::shouldDisplayError)
                        .doOnComplete(this::shouldDoOnComplete)
//                        .flatMap(value -> carMapper.mapCars(storage, value) // flat map is used for mapping the result
//                        .flatMap(v ->
//                                Observable.just(v)
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(mainScheduler)
//                                        .map(w -> carMapper.mapCars(storage, v))
//                                        .doOnNext(this::displayCars)
//                                        .doOnError(this::shouldDisplayError)
//                                        .doOnComplete(this::shouldDoOnComplete)
//                        )
                        .subscribe();
        addToCompositeDisposable(subscribe);*/
    }


//    private void shouldDisplayError(Throwable throwable) {
//        getView().onHideDialog();
//        getView().onShowToast("Error loading cars: " + throwable.getMessage());
//    }
//
//    private void shouldDoOnComplete() {
//        getView().onHideDialog();
//        getView().onShowToast("Cars loading complete!");
//    }
//
//
//    private void shouldDisplayCars(CarsResponse carsResponse) {
//        System.out.println("Thread display: " + Thread.currentThread().getId());
//        getView().onClearItem();
//        if (carsResponse.getPlacemarks() != null)
//            getView().onCarsLoaded(carsResponse);
//        else
//            getView().onShowToast("Car list is empty!");
//    }

//    private Consumer<CarsResponse> consumer = new Consumer<CarsResponse>() {
//        @Override
//        public void accept(CarsResponse response) throws Exception {
//            //cars = carMapper.mapCars(storage, response);
//            if (response.getPlacemarks() != null)
//                getView().onCarsLoaded(response);
//            else
//                getView().onShowToast("Car list is empty!");
//            getView().onClearItem();
//
//        }
//    };

    @Override
    public void onSubscribe(Disposable d) {
        addToCompositeDisposable(d);
    }

    @Override
    public void onNext(CarsResponse carsResponse) {
        if (carsResponse.getPlacemarks() != null)
            getView().onCarsLoaded(carsResponse);
        else
            getView().onShowToast("Car list is empty!");
    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
        getView().onShowToast("Error loading cars: " + e.getMessage());
    }

    @Override
    public void onComplete() {
        getView().onHideDialog();
        getView().onShowToast("Cars loading complete!");
    }

//    public void getCarsFromDatabase() {
//        List<Car> cars = storage.getSavedCars();
//        getView().onClearItem();
//        getView().onCarsLoaded(cars);
//    }
}
