package com.pomohouse.message;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.pomohouse.message.interfaceclass.ScreenReceiveListener;
import com.pomohouse.message.model.ContactData;
import com.pomohouse.message.tools.Utils;
import com.pomohouse.message.view.MessageFragment;

public class MainActivity extends AppCompatActivity implements ScreenReceiveListener {

    private final String TAG = this.getClass().getSimpleName();
    private ContactData contactData;
    //    private BroadcastReceiver mReceiver;
    private MessageFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((WaffleApplication) getApplication()).getComponent().injectToMainActivity(this);
        initialData();
        initialView(savedInstanceState);
    }

    private void initialData() {
        contactData = getIntent().getParcelableExtra("contact_data");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.isLoadMessage = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.isLoadMessage = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.isLoadMessage = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mReceiver);
    }

    private void initialView(Bundle savedInstanceState) {
        //First Page
        if (savedInstanceState == null) {
            fragment = MessageFragment.newInstanc(contactData);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fram_content, fragment)
                    .commit();
        }
    }

    @Override
    public void screenOff() {
//        Log.e("ScreenOff", "System.exit(1)");
//        getSupportFragmentManager()
//                .beginTransaction()
//                .remove(fragment)
//                .commit();
//        finish();
////        android.os.Process.killProcess(android.os.Process.myPid());
//        Log.e("ScreenOff", "System.exit(0)");
//        System.exit(0);
    }
}
