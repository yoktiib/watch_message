package com.pomohouse.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.pomohouse.message.global.ObjectDataInstance;
import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.model.ContactDAO;
import com.pomohouse.message.provider.ContactProvider;
import com.pomohouse.message.receiver.KillAppReceiver;
import com.pomohouse.message.tools.Utils;
import com.pomohouse.message.view.AllContactFragment;

/**
 * Created by mac on 9/20/2016 AD.
 */

public class ContactActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onResume() {
        super.onResume();
        Utils.isLoadMessage = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ((WaffleApplication) getApplication()).getComponent().injectToContactActivity(this);
        initialData();
        KillAppReceiver.getInstance().startEventReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //KillAppReceiver.getInstance().stopEventService(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initialData() {
        //set Api loop time
        //TODO: change requirement
        ObjectDataInstance.getInstance().setApiLoopTime(5);
        //Get Imei From Content Provider
        getImeiWatch();
        getAllContact();
    }

    private void getImeiWatch() {
        String imei = WearerInfoUtils.getInstance().initWearerInfoUtils(this).getImei();
        AbstractLog.e("IMEI", imei);
        ObjectDataInstance.getInstance().setMyImei(imei);

//        //Test------------------------------------------------------------------------
//        String DEBUG_IMEI = "356879451687865";
//        ObjectDataInstance.getInstance().setMyImei(DEBUG_IMEI);
//        //Test------------------------------------------------------------------------
    }

    private void getAllContact() {
        try {
            new ContactProvider(this).onContactListProvider(new ContactProvider.OnLoadContactListener() {
                @Override
                public void success(ContactDAO c) {
                    //Next Page
                    initialView(c);
                }

                @Override
                public void error(Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    private void initialView(ContactDAO c) {
        //First Page
        Fragment fragment = AllContactFragment.newInstanc(c);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fram_content, fragment)
                .commit();
    }

    private void alert() {
        new AlertDialog.Builder(this)
                .setMessage("IMEI Not Found.")
                .setCancelable(false)
                .create()
                .show();
    }
}
