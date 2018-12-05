package com.pomohouse.message;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;

import com.pomohouse.message.db.RealmManage;
import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.tools.DateTime;

import java.io.File;
import java.io.IOException;

import rx.Subscription;

/**
 * Created by Art-ars on 9/2/2016.
 */
public class SoundRecorder {

    private final String TAG = this.getClass().getSimpleName();

    private Context context;
    private MediaRecorder recorder;
    private MediaPlayer mediaPlayer;
    private File recordFile;
    private String tempFileName;
    private String fileContent;
    private PlayBackListener playBackListener;
    private RecordListener recordListener;
    private Subscription subscription;
    private RealmManage realmManage;
    private boolean isPlay = false;

    public interface PlayBackListener{
        void onProgressUpdate(int progressValue, int time);
        void onPlayCompleted();
    }

    public interface RecordListener {
        void onSaveFileCompleted(File fileContent, String tempFileName);
        void onSaveFileError(IOException e);
    }

    public SoundRecorder(Context context, PlayBackListener playBackListener) {
        this.context = context;
        this.playBackListener = playBackListener;
        recorder = new MediaRecorder();
        mediaPlayer = new MediaPlayer();
//        realmManage = new RealmManage(context);
    }

    public SoundRecorder(Context context, RecordListener recordListener) {
        this.context = context;
        this.recordListener = recordListener;
        recorder = new MediaRecorder();
        mediaPlayer = new MediaPlayer();
//        realmManage = new RealmManage(context);
    }

    private void initRecorder() {
        String pathFolder = Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_DOWNLOADS) + File.separator;
        tempFileName = DateTime.getDateTime() + ".mp3";
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncodingBitRate(16);
        recorder.setAudioSamplingRate(44100);
        recordFile = new File(pathFolder + tempFileName);
        recorder.setOutputFile(recordFile.getPath());
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setMaxDuration(30000);
        recorder.setOnInfoListener(onInfoListener);

        AbstractLog.e("sound record", "file name " + recordFile.getAbsolutePath());
    }

    public void startRecorder() throws IOException {
        recorder.reset();
        initRecorder();
        recorder.prepare();
        recorder.start();
    }

    public void stopRecorder() {
        recorder.stop();
        recorder.reset();
        try {
            convertFileToString(tempFileName);
        } catch (IOException e) {
            e.printStackTrace();
            recordListener.onSaveFileError(e);
        }
    }

    public void startPlayback(String url) {
        if(!mediaPlayer.isPlaying()){
            try {
                mediaPlayer.reset();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(onPreparedListener);
                mediaPlayer.setOnCompletionListener(onComleted);
                mediaPlayer.setOnErrorListener(onErrorListener);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        subscription = Observable.interval(1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        int valueUpdate = ((int) (((float) mediaPlayer.getCurrentPosition() / getDurationTime()) * 100));
//                        int time = ((int)((float)(getDurationTime() - mediaPlayer.getCurrentPosition())/1000));
//                        playBackListener.onProgressUpdate(valueUpdate, time);
//                    }
//                });
    }

//    public void startPlayBack(){
//        if(!isPlay) {
//            isPlay = true;
//            mediaPlayer.reset();
//            mediaPlayer.start();
//        }
//    }

    public void stopPlayBack() {
//        if(mediaPlayer.isPlaying())
//            mediaPlayer.stop();
        mediaPlayer.stop();
        mediaPlayer.reset();
    }
    
    public boolean isPlay(){
        if(mediaPlayer == null)
            return false;
        return mediaPlayer.isPlaying();
    }

    private void convertFileToString(String tempFileName) throws IOException {
        //fileContent = Base64.encodeToString(FileUtils.readFileToByteArray(recordFile), Base64.NO_WRAP);
        if (recordListener != null)
            recordListener.onSaveFileCompleted(recordFile, tempFileName);

//        SendVoiceMessageModel svm = new SendVoiceMessageModel();
//        svm.setName(tempFileName);
//        svm.setStatus("0");
//        realmManage.addPathFileVoiceRecord(svm);
    }

    public int getDurationTime() {
        if (mediaPlayer == null)
            return 0;
        return mediaPlayer.getDuration();
    }

    public void unSubScribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    MediaRecorder.OnInfoListener onInfoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                stopRecorder();
            }
        }
    };

    MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
//            if (playBackListener != null)
//                playBackListener.onReadyToPlay(mp);
        }
    };

    MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            AbstractLog.e(TAG, "what : " + what + " : extra : " + extra);
            return false;
        }
    };

    MediaPlayer.OnCompletionListener onComleted = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            playBackListener.onPlayCompleted();
        }
    };

}
