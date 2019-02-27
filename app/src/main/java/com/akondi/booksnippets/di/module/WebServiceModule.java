package com.akondi.booksnippets.di.module;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class WebServiceModule {

  private String mBaseUrl;
  private Context context;

  public WebServiceModule(Context context, String mBaseUrl) {
    this.context = context;
    this.mBaseUrl = mBaseUrl;
  }

  @Singleton
  @Provides
  GsonConverterFactory providesGsonConverterFactory() {
    return GsonConverterFactory.create();
  }

  @Singleton
  @Provides
  @Named("ok-1")
  OkHttpClient providesOkHttpClient1() {
    return new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();
  }

  @Singleton
  @Provides
  @Named("ok-2")
  OkHttpClient providesOkHttpClient2() {
    return new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
  }

  @Provides
  @Singleton
  RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
    return RxJava2CallAdapterFactory.create();
  }

  @Provides
  @Singleton
  Retrofit providesRetrofit(@Named("ok-1") OkHttpClient client, GsonConverterFactory converterFactory, RxJava2CallAdapterFactory adapterFactory) {
    return new Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(adapterFactory)
            .client(client)
            .build();
  }
}
