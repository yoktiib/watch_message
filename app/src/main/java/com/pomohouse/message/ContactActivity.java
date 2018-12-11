package com.pomohouse.message;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pomohouse.message.global.ObjectDataInstance;
import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.model.ContactDAO;
import com.pomohouse.message.provider.ContactProvider;
import com.pomohouse.message.receiver.KillAppReceiver;
import com.pomohouse.message.tools.Utils;
import com.pomohouse.message.view.AllContactFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 9/20/2016 AD.
 */

public class ContactActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private final int MY_PERMISSIONS = 1010;
    String[] PERMISSIONS = {android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, MY_PERMISSIONS);
            }else
                initialData();
        }else{
            initialData();
        }
        KillAppReceiver.getInstance().startEventReceiver(this);
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("", "Permission callback called-------");
        initialData();
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
