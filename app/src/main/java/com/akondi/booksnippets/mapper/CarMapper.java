package com.akondi.booksnippets.mapper;

import android.util.Log;

import com.akondi.booksnippets.mvp.model.Car;
import com.akondi.booksnippets.mvp.model.CarsResponse;
import com.akondi.booksnippets.mvp.model.CarsResponsePlacemarks;
import com.akondi.booksnippets.mvp.model.Storage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CarMapper {
    private static final String TAG = "CarMapper";

    @Inject
    public CarMapper() {

    }

    public List<Car> mapCars(Storage mStorage, CarsResponse response) {
        List<Car> carList = new ArrayList<>();
        if (response != null) {
            CarsResponsePlacemarks[] placemarksList = response.getPlacemarks();
            if (placemarksList != null) {
                for (CarsResponsePlacemarks crp : placemarksList) {
                    Car car = new Car();
                    car.setAddress(crp.getAddress());

                    List<Double> coordinates = new ArrayList<>();
                    if (crp.getCoordinates() != null) {
                        coordinates.add(crp.getCoordinates()[0]);
                        coordinates.add(crp.getCoordinates()[1]);
                    }
                    car.setCoordinates(coordinates);

                    car.setEngineType(crp.getEngineType());
                    car.setExterior(crp.getExterior());
                    car.setInterior(crp.getInterior());
                    car.setFuel(crp.getFuel());
                    car.setName(crp.getName());
                    car.setVin(crp.getVin());
                    Log.d(TAG, "mapCars: vin" + car.getVin());
                    //mStorage.addCar(car);
                    carList.add(car);
                }
            } else {
                Log.d(TAG, "mapCars: placemarksList " + "is null");
            }
        } else {
            Log.d(TAG, "mapCars: response " + "is null");
        }
        return carList;
    }
}
