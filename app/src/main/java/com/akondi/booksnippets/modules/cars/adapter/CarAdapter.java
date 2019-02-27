package com.akondi.booksnippets.modules.cars.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.akondi.booksnippets.R;
import com.akondi.booksnippets.mvp.model.Car;
import com.akondi.booksnippets.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.Holder> {

    private LayoutInflater layoutInflater;
    private List<Car> carList = new ArrayList<>();

    public CarAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void addCars(List<Car> cars) {
        carList.addAll(cars);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.list_item_layout, viewGroup, false);
        return  new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.bind(carList.get(i));
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public void clearCars() {
        carList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.car_icon) protected SimpleDraweeView mCarIcon;
        @BindView(R.id.textview_title) protected TextView mCarTitle;
        @BindView(R.id.textview_preview_description) protected TextView mCarDescription;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Car car) {
            mCarTitle.setText(car.getName());
            mCarDescription.setText(car.getVin());
            mCarIcon.setController(Utils.reduceImageSize("https://www.enterprise.com/content/dam/global-vehicle-images/cars/TOYO_CAMR_2018.png", 200));
        }
    }
}
