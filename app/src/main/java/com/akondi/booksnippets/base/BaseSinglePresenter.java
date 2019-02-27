package com.akondi.booksnippets.base;


import com.akondi.booksnippets.mvp.view.BaseView;


import javax.inject.Inject;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BaseSinglePresenter<V extends BaseView> {


    private CompositeDisposable compositeDisposable = new CompositeDisposable();// to handle lifecycle of activity


    @Inject
    protected V mView;

    public V getView() {
        return mView;
    }

    public void setView(V view) {
        this.mView = view;
    }

    protected <T> void subscribe( Single<T>  observable, DisposableSingleObserver<T> observer, Scheduler scheduler) {
        DisposableSingleObserver<T> disposableSingleObserver = observable.subscribeOn(Schedulers.io()) // new thread on getting data
                .observeOn(scheduler) // listen for incoming data in main thread
                .subscribeWith(observer);
        compositeDisposable.add(disposableSingleObserver);
    }

//    protected <T> void subscribe(Observable<T> observable, Observer<T> observer){
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//    }

    protected void clearCompositeDisposable() {
        compositeDisposable.clear();
    }


}
