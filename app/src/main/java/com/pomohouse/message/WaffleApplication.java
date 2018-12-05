package com.pomohouse.message;

import android.app.Application;

import com.pomohouse.message.di.component.DaggerNetworkComponent;
import com.pomohouse.message.di.component.NetworkComponent;
import com.pomohouse.message.di.module.AppModule;
import com.pomohouse.message.di.module.NetworkModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by SITTIPONG on 30/8/2559.
 */
public class WaffleApplication extends Application {

    private NetworkComponent component;
    //private RealmConfiguration realmConfig;
    private String databaseName = "message_chat";

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerNetworkComponent.builder().appModule(new AppModule(this)).networkModule(new NetworkModule()).build();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME).build();
        //set RealmConfiguration
        /*realmConfig = new RealmConfiguration
                .Builder(getApplicationContext())
                .name(Realm.DEFAULT_REALM_NAME)
                .build();
*/
        Realm.setDefaultConfiguration(config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public NetworkComponent getComponent() {
        return component;
    }
}
