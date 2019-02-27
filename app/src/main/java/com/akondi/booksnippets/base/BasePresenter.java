package com.akondi.booksnippets.base;

import com.akondi.booksnippets.mvp.view.BaseView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<V extends BaseView> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();// to handle lifecycle of activity


    @Inject
    protected V mView;

    public V getView(){
        return mView;
    }

    public void setView(V view) {
        this.mView = view;
    }

    protected <T> void subscribe(Observable<T> observable, Observer<T> observer, Scheduler scheduler){
        observable.subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(observer);
    }

    protected void addToCompositeDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void clearCompositeDisposable() {
        compositeDisposable.clear();
    }
}
