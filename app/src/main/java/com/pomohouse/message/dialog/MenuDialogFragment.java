package com.pomohouse.message.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.pomohouse.message.R;
import com.pomohouse.message.config.Config;
import com.pomohouse.message.controller.SendMessageController;
import com.pomohouse.message.interfaceclass.StickerReceiveListener;
import com.pomohouse.message.interfaceclass.VoiceReceiveListener;
import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.tools.DateTime;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import info.abdolahi.CircularMusicProgressBar;

/**
 * Created by mac on 10/31/2016 AD.
 */

public class MenuDialogFragment extends DialogFragment {

    @BindView(R.id.btn_back)
    ImageButton btnBack;

    private String TAG = this.getClass().getSimpleName();
    private Context context;
    private File recordFile;
    private boolean isRecording = false;
    private SendMessageController sendMessage;
    private MediaRecorder recorder;
    private String tempFileName;
    private int timer = 0;
    private Runnable runnable;
    private Handler handler;
    private VoiceReceiveListener voiceReceiveListener;
    private StickerReceiveListener stickerReceiveListener;

    public static MenuDialogFragment newInstanc(VoiceReceiveListener voiceReceiveListener, StickerReceiveListener stickerReceiveListener) {
        MenuDialogFragment fragment = new MenuDialogFragment();
        fragment.voiceReceiveListener = voiceReceiveListener;
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
        return inflater.inflate(R.layout.dialog_menu_buttom, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialView(view);
    }

    private void initialData() {
        context = getActivity().getApplicationContext();
        recorder = new MediaRecorder();
        handler = new Handler();
        sendMessage = new SendMessageController(context);
    }

    private void initialView(View view) {
        ButterKnife.bind(this, view);
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

    @OnClick(R.id.btn_menu_voice)
    void onOpenRecordVoice() {
        RecordVoiceDialogFragment menuDialogFragment = RecordVoiceDialogFragment.newInstanc(voiceReceiveListener);
        menuDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        menuDialogFragment.show(getFragmentManager(), null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, Config.DELAY_DISMISS_DIALOG);
    }

    @OnClick(R.id.btn_menu_sticker)
    void onOpenStickerList() {
        StickerListDialogFragment menuDialogFragment = StickerListDialogFragment.newInstanc(stickerReceiveListener);
        menuDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        menuDialogFragment.show(getFragmentManager(), null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, Config.DELAY_DISMISS_DIALOG);
    }
}