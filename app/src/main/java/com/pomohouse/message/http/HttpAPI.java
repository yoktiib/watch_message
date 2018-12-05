package com.pomohouse.message.http;

import com.pomohouse.message.model.AllContactDAO;
import com.pomohouse.message.model.ChatRoomDAO;
import com.pomohouse.message.model.MessagesDAO;
import com.pomohouse.message.model.SendMessageDAO;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

public interface HttpAPI {


    @FormUrlEncoded
    @POST("contact/requestAllContacts")
    Observable<Response<AllContactDAO>> getAllContactsList(@Field("imei") String imei);

    @FormUrlEncoded
    @POST("message/requestChatRoom")
    Observable<Response<ChatRoomDAO>> getChatRoomList(@Field("imei") String imei,
                                                      @Field("messageMember") String member);

    @FormUrlEncoded
    @POST("message/requestMessages")
    Observable<Response<MessagesDAO>> getMessageList(@Field("lastMessageId") int msgId,
                                                     @Field("chatRoomId") int chatRoomId,
                                                     @Field("imei") String imei);

    @FormUrlEncoded
    @POST("message/sendMessage")
    Observable<Response<SendMessageDAO>> sendMessage(@Field("stickerId") String StickerID,
                                                     @Field("voice") String voice,
                                                     @Field("chatRoomId") String chatRoomId,
                                                     @Field("imei") String imei,
                                                     @Field("messageType") int type,
                                                     @Field("voiceLength") int voiceLength);

    @Multipart
    @POST("message/sendMessage")
    Observable<Response<SendMessageDAO>> sendVoice(@PartMap HashMap<String, RequestBody> param,
                                                   @Part MultipartBody.Part voiceFile);

}
