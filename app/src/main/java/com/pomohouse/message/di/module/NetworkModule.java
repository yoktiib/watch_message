package com.pomohouse.message.di.module;

import android.app.Application;

import com.pomohouse.message.config.Config;
import com.pomohouse.message.http.HttpAPI;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        return new Cache(application.getCacheDir(), (10 * 1024 * 1024));
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        CertificatePinner certPinner = new CertificatePinner.Builder()
                //.add(Config.API,"sha256/Vjs8r4z+80wjNcr1YKepWQboSIRi63WsWXhIMN+eWys=")
                .build();
        return new OkHttpClient.Builder()
                //.certificatePinner(certPinner)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(
                        15, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    HttpAPI provideHttpApi(Retrofit retrofit) {
        return retrofit.create(HttpAPI.class);
    }

}
