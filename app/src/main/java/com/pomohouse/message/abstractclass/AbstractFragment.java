package com.pomohouse.message.abstractclass;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pomohouse.message.R;

/**
 * Created by SITTIPONG on 30/8/2559.
 */

public class AbstractFragment extends Fragment {

    protected Context context;
    protected FragmentActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        context = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void changeFragmentReplace(Fragment fragment, boolean isAddBackStack) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        ts.replace(R.id.fram_content, fragment);
        if (isAddBackStack)
            ts.addToBackStack(null);
        ts.commit();
    }

    protected void changeFragmentAdd(Fragment fragment, boolean isAddBackStack) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        ts.add(R.id.fram_content, fragment);
        if (isAddBackStack)
            ts.addToBackStack(null);
        ts.commit();
    }
}
