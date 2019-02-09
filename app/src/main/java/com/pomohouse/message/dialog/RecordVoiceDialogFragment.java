package com.pomohouse.message.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.pomohouse.message.R;
import com.pomohouse.message.controller.SendMessageController;
import com.pomohouse.message.interfaceclass.VoiceReceiveListener;
import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.tools.DateTime;
import com.pomohouse.message.tools.Tools;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import info.abdolahi.CircularMusicProgressBar;

/**
 * Created by mac on 10/31/2016 AD.
 */

public class RecordVoiceDialogFragment extends DialogFragment {

    @BindView(R.id.txt_timer)
    TextView txtTimer;
    @BindView(R.id.progress_time)
    CircularMusicProgressBar progressTime;
    @BindView(R.id.icon_recording)
    ImageView ivRecord;

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

    public static RecordVoiceDialogFragment newInstanc(VoiceReceiveListener voiceReceiveListener) {
        RecordVoiceDialogFragment fragment = new RecordVoiceDialogFragment();
        fragment.voiceReceiveListener = voiceReceiveListener;
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
        return inflater.inflate(R.layout.dialog_menu_record_voice, container, false);
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

        progressTime.setValue(0);
        Tools.setTypeface(context, txtTimer);
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

    @OnTouch(R.id.recroding_view)
    boolean onRecordClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AbstractLog.e(TAG, "pressed");
            if (!isRecording) {
                isRecording = true;
                startRecord();
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            AbstractLog.e(TAG, "up");
            if (isRecording) {
                isRecording = false;
                stopRecord();
            }
            return true;
        } else {
            return false;
        }
    }

    private void startRecord() {
        progressTime.setImageResource(R.color.white);
        progressTime.setBorderColor(ContextCompat.getColor(context, R.color.white));
        progressTime.setValue(0);
        try {
            ivRecord.setImageResource(R.drawable.recording);
            initialRecorder();
            recorder.prepare();
            recorder.start();
            startTimer();
        } catch (IllegalStateException | java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private void startTimer() {
        progressTime.setValue((float) (timer * 3.44));
        txtTimer.setText(String.valueOf("0:" + String.format(Locale.ENGLISH, "%02d", timer)));

        timer += 1;

        runnable = new Runnable() {
            @Override
            public void run() {
                startTimer();
                AbstractLog.e(TAG, "time : " + timer);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void stopRecord() {
        try {
            progressTime.setImageResource(R.color.gray);
            ivRecord.setImageResource(R.drawable.record_btn);
            stopTimer();
            recorder.stop();
            recorder.reset();
            if (recordFile.exists()) {
                if ((sendMessage.getDurationFileVoice(recordFile) * 1000) > 500) {
                    voiceReceiveListener.voiceReceive(tempFileName, recordFile);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dismiss();
        }
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
        timer = 0;
        progressTime.setValue(0);
    }

    private void initialRecorder() {
        String pathFolder = Environment.getExternalStorageDirectory().getAbsolutePath(); /*Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_DOWNLOADS) + File.separator;*/

        tempFileName = DateTime.getDateTime() + ".mp3";
        recorder.reset();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncodingBitRate(32);
        recorder.setAudioSamplingRate(44100);
        recordFile = new File(pathFolder + tempFileName);
        recorder.setOutputFile(recordFile.getPath());
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setMaxDuration(1000 * 30);
        recorder.setOnInfoListener(onInfoListener);
    }

    MediaRecorder.OnInfoListener onInfoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mediaRecorder, int what, int extra) {
            if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                stopRecord();
            }
        }
    };
}