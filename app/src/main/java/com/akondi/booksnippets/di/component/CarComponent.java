package com.akondi.booksnippets.di.component;


import com.akondi.booksnippets.di.module.CarModule;
import com.akondi.booksnippets.di.scope.PerActivity;
import com.akondi.booksnippets.modules.cars.CarActivity;

import dagger.Component;

@PerActivity
@Component(modules = CarModule.class, dependencies = AppComponent.class)
public interface CarComponent {

    void inject(CarActivity carActivity);
}
