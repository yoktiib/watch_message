package com.pomohouse.message.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.pomohouse.message.R;
import com.pomohouse.message.adapter.StickerListAdapter;
import com.pomohouse.message.config.Config;
import com.pomohouse.message.controller.StickerMap;
import com.pomohouse.message.interfaceclass.OnItemTouchListener;
import com.pomohouse.message.interfaceclass.StickerReceiveListener;
import com.pomohouse.message.model.StickerData;
import com.pomohouse.message.view.StickerPreviewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sittipong on 10/31/2016 AD.
 */

public class StickerListDialogFragment extends DialogFragment {

    @BindView(R.id.sticker_recycler_view)
    RecyclerView listSticker;

    private String TAG = this.getClass().getSimpleName();
    private Context context;
    private StickerListAdapter stickerListAdapter;
    private StickerReceiveListener stickerReceiveListener;

    public static StickerListDialogFragment newInstanc(StickerReceiveListener stickerReceiveListener) {
        StickerListDialogFragment fragment = new StickerListDialogFragment();
        fragment.stickerReceiveListener = stickerReceiveListener;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        if (window != null)
            window.getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(R.color.white);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
        initialData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_menu_sticker, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialView(view);
    }

    private void initialData() {
        context = getActivity().getApplicationContext();
        stickerListAdapter = new StickerListAdapter(context);
    }

    private void initialView(View view) {
        ButterKnife.bind(this, view);

        stickerListAdapter.setStickerData(StickerMap.getStickerList());
        stickerListAdapter.setOnItemClickListener(onItemTouchListener);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        listSticker.setLayoutManager(layoutManager);
        listSticker.setItemAnimator(new DefaultItemAnimator());
        listSticker.setLayoutManager(new GridLayoutManager(context, 2));
        listSticker.setAdapter(stickerListAdapter);
    }

    OnItemTouchListener onItemTouchListener = new OnItemTouchListener() {

        @Override
        public void onSingleTap(Object obj) {
            StickerData d = (StickerData) obj;
            StickerPreviewDialogFragment dialogFragment = StickerPreviewDialogFragment.newInstanc(stickerReceiveListener, d);
            dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
            dialogFragment.show(getFragmentManager(), null);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, Config.DELAY_DISMISS_DIALOG);
        }

        @Override
        public void onDoubleTap(Object obj) {
            StickerData d = (StickerData) obj;
            stickerReceiveListener.stickerReceive(d.getId() + "");
            dismiss();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    @OnClick(R.id.btn_back)
    void onBack() {
        dismiss();
    }
}