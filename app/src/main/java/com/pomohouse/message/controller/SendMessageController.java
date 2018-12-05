package com.pomohouse.message.controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.google.gson.GsonBuilder;
import com.pomohouse.message.WaffleApplication;
import com.pomohouse.message.http.HttpAPI;
import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.model.SendMessageDAO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Art-ars on 9/11/2016.
 */
public class SendMessageController {

    private static final String TAG = "SendMessageController";

    @Inject
    HttpAPI service;

    private Context context;
    private Observable<Response<SendMessageDAO>> observable;
    private Subscription subscription;
    private SendMessageListener listener;

    public interface SendMessageListener {
        void onSuccess(SendMessageDAO data);

        void onError(Throwable error);
    }

    public SendMessageController(Context context) {
        ((WaffleApplication) context.getApplicationContext()).getComponent().inject(this);
        this.context = context;
    }

    public SendMessageController(Context context, SendMessageListener listener) {
        ((WaffleApplication) context.getApplicationContext()).getComponent().inject(this);
        this.context = context;
        this.listener = listener;
    }

    public void sendMessagesToServer(String imei, String roomId, String voice, String sticker, int type, int voiceLength) {
        AbstractLog.e(TAG, "file " + voice);
        observable = service.sendMessage(sticker, voice, roomId, imei, type, voiceLength);
        subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<SendMessageDAO>>() {
                    @Override
                    public void call(Response<SendMessageDAO> response) {
                        AbstractLog.e(TAG, "code " + response.code());
                        AbstractLog.e(TAG, "response send message " + new GsonBuilder()
                                .setPrettyPrinting().create().toJson(response.body()));
                        if (listener != null)
                            listener.onSuccess(response.body());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        AbstractLog.e(TAG, "error " + throwable);
                        if (listener != null)
                            listener.onError(throwable);
                    }
                });
    }

    public int getDurationFileVoice(File voiceFile) {
        return MediaPlayer.create(context, Uri.fromFile(voiceFile)).getDuration() / 1000;
    }

    public void sendVoiceFileToServer(String imei, String roomId, File voiceFile, String fileName, int type) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), voiceFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("voice", fileName, requestFile);
        HashMap<String, RequestBody> param = new HashMap<>();
        param.put("imei", RequestBody.create(MediaType.parse("text/plain"), imei));
        param.put("messageType", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(type)));
        param.put("chatRoomId", RequestBody.create(MediaType.parse("text/plain"), roomId));
        param.put("voiceLength", RequestBody.create(MediaType.parse("text/plain"), getDurationFileVoice(voiceFile) + ""));

        observable = service.sendVoice(param, body);
        subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<SendMessageDAO>>() {
                    @Override
                    public void call(Response<SendMessageDAO> response) {
                        AbstractLog.e(TAG, "response " + response.message());
                        if (response.isSuccessful()) {
                            AbstractLog.e(TAG, "response data : " + new GsonBuilder()
                                    .setPrettyPrinting().create().toJson(response.body()));
                            if (listener != null)
                                listener.onSuccess(response.body());
                        }

                        if (response.errorBody() != null) {
                            try {
                                AbstractLog.e(TAG, "response error : " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        AbstractLog.e(TAG, "error " + throwable);
                        if (listener != null)
                            listener.onError(throwable);
                    }
                });
    }

    public void unSubScribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed())
                subscription.unsubscribe();
        }
    }
}
