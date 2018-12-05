package com.pomohouse.message.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.pomohouse.message.R;
import com.pomohouse.message.adapter.StickerListAdapter;
import com.pomohouse.message.controller.StickerMap;
import com.pomohouse.message.interfaceclass.OnItemTouchListener;
import com.pomohouse.message.interfaceclass.StickerReceiveListener;
import com.pomohouse.message.model.StickerData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mac on 10/31/2016 AD.
 */

public class StickerPreviewDialogFragment extends DialogFragment {

    @BindView(R.id.iv_sticker_preview)
    ImageView ivStickerPreview;

    private String TAG = this.getClass().getSimpleName();
    private Context context;
    private StickerReceiveListener stickerReceiveListener;
    private String stickerId;
    private StickerData stickerData;

    public static StickerPreviewDialogFragment newInstanc(StickerReceiveListener stickerReceiveListener, StickerData stickerData) {
        StickerPreviewDialogFragment fragment = new StickerPreviewDialogFragment();
        fragment.stickerReceiveListener = stickerReceiveListener;
        fragment.stickerData = stickerData;
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
        window.setBackgroundDrawableResource(R.color.white_tran);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_menu_sticker_preview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialView(view);
    }

    private void initialData() {
        context = getActivity().getApplicationContext();
        stickerId = stickerData.getId() + "";
    }

    private void initialView(View view) {
        ButterKnife.bind(this, view);
        ivStickerPreview.setImageResource(stickerData.getStickerImg());
    }

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

    @OnClick(R.id.iv_sticker_preview)
    void onSend() {
        stickerReceiveListener.stickerReceive(stickerId);
        dismiss();
    }
}