package com.akondi.booksnippets.modules.cars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.akondi.booksnippets.R;

import com.akondi.booksnippets.base.BaseActivity;
import com.akondi.booksnippets.di.component.DaggerCarComponent;
import com.akondi.booksnippets.di.module.CarModule;
import com.akondi.booksnippets.mapper.CarMapper;
import com.akondi.booksnippets.modules.cars.adapter.CarAdapter;
import com.akondi.booksnippets.mvp.model.Car;
import com.akondi.booksnippets.mvp.model.CarsResponse;
import com.akondi.booksnippets.mvp.presenter.CarActivityPresenter;
import com.akondi.booksnippets.mvp.view.MainView;
import com.akondi.booksnippets.utils.NetworkUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class CarActivity extends BaseActivity implements MainView {

    @BindView(R.id.cars_list)
    protected RecyclerView carList;

    @Inject
    protected CarActivityPresenter presenter;

    private CarAdapter carAdapter;

    @Override
    protected void onViewReady(Bundle savedInstanceSate, Intent intent) {
        super.onViewReady(savedInstanceSate, intent);
        Fresco.initialize(this);
        initializeList();
        if (NetworkUtils.isNetAvailable(this)) {
            presenter.getCars();
        } else {
            //presenter.getCarsFromDatabase();
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.clearCompositeDisposable();
    }

    private void initializeList() {
        carList.setHasFixedSize(true);
        carList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        carAdapter = new CarAdapter(getLayoutInflater());
        carList.setAdapter(carAdapter);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_cars;
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerCarComponent.builder()
                .appComponent(getApplicationComponent())
                .carModule(new CarModule(this))
                .build().inject(this);
    }

    @Override
    public void onCarsLoaded(CarsResponse carsResponse) {
        CarMapper carMapper = new CarMapper();
        List<Car> carList = carMapper.mapCars(null, carsResponse);
        carAdapter.addCars(carList);
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onClearItem() {
        carAdapter.clearCars();
    }
}
