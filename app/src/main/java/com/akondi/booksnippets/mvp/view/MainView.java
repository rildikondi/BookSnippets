package com.akondi.booksnippets.mvp.view;

import com.akondi.booksnippets.mvp.model.Car;
import com.akondi.booksnippets.mvp.model.CarsResponse;

import java.util.List;

public interface MainView extends BaseView {

    void onCarsLoaded(CarsResponse carsResponse);

    void onShowDialog(String message);

    void onShowToast(String message);

    void onHideDialog();

    void onClearItem();
}
